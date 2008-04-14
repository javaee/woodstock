/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */
package com.sun.webui.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CompressJavaScript extends ToolsBase {
    private String rhinoJar = null;

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param verbose Enable verbose output.
     * @param copyrightFile File path for copyright file.
     * @param destDir Directory where compressed files are written.
     * @param rhinoJar The jar containing the Rhino compress tool.
     */
    public CompressJavaScript(String sourceDir, boolean verbose, 
            String copyrightFile, String destDir, String rhinoJar) {
        super(sourceDir, verbose, copyrightFile);
        setDestDir(destDir);
        this.rhinoJar = rhinoJar;
    }

    /**
     * Process a list of JavaScript files found in sourceDir
     * and write the outpout to destDir.
     *
     * @param fileList <f0,...,fn> A comma separated list of relative file paths to process.
     */
    public void process(String[] fileList) throws IOException {
	for (int i = 0; i < fileList.length; ++i) {
	    try {
		File inFile = new File(getSourceDir() + File.separator +
		    fileList[i]);
		File outFile = new File(getDestDir() + File.separator +
		    fileList[i]);
		processFile(inFile, outFile);
	    } catch (Exception e) {
		throw new IOException("Cannot compress " +
		    getSourceDir() + File.separator + fileList[i] + " to " +
		    getDestDir() + File.separator + fileList[i]);
	    }
	}
    }

    /**
     * Process file.
     *
     * Note: Calling org.mozilla.javascript.tools.shell.Main.main
     * directly does not work well with large file sets. After only a few files, 
     * Java runs out of memory when redirecting stdout to a file. And when the
     * -o option is used in this scenario, Rhino compresses the same files. For
     * example:
     * 
     * Compressed file stored in 'scheduler.js'
     * Compressed file stored in 'image.js'
     * Compressed file stored in 'image.js'
     * Compressed file stored in 'textArea.js'
     * Compressed file stored in 'textArea.js'
     * Compressed file stored in 'textArea.js'
     * ...
     * 
     * Therefore, we need to run the Rhino program in a separate JVM and 
     * compress individual files.
     *
     * @param inFile The JavaScript file to process.
     * @param outFile The JavaScript file to write output.
     */
    protected void processFile(File inFile, File outFile) throws IOException {
        if (!isFileAvailable(inFile)) {
            return;
        }

        // The command line equivalent of the exec command is:
        //
	// java -jar <rhinoJar> -strict -opt -1 -o <outputfile>
	// 	-c <filetocompress> 
	//
	String cmd = 
            "java -jar " +  rhinoJar + 
	    " -strict -opt -1 -o " +
            outFile.getAbsolutePath() +
            " -c " +
            inFile.getAbsolutePath();
	ExecProcess ep = new ExecProcess(cmd);
	int returnVal = ep.exec();
	if (returnVal != 0) {
	    System.exit(returnVal);
	}
        pruneFile(outFile);
    }

    /**
     * Prune new lines from JavaScript file.
     *
     * @param file The JavaScript file to prune.
     */
    protected void pruneFile(File file) throws IOException {
	// Create buffer to hold file contents.
        StringBuffer buff = new StringBuffer(1024);
        BufferedReader input = null;

	try {
            input = new BufferedReader(new FileReader(file));
            String line = null;

            // readLine is a bit quirky:
            // it returns the content of a line MINUS the newline.
            while ((line = input.readLine()) != null) {
                // Alternatively, we could use line.replaceAll("\n|\r", "")
                buff.append(line);
            }
	    // Write output.
            writeOutFile(file, buff.toString(), false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e){
            e.printStackTrace();
	} finally {
	    try {
	        if (input != null) {
                    input.close();
	        }
	    } catch (IOException e) {
                e.printStackTrace();
	    }
        }
    }
}
