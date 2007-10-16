/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */
package com.sun.webui.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class CompressJavaScript extends ToolsBase {
    private String rhinoJar = null;

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param destDir Directory where compressed files are written.
     * @param rhinoJar The jar containing the Rhino compress tool.
     * @param verbose Enable verbose output.
     */
    public CompressJavaScript(String sourceDir, String destDir, String rhinoJar, 
            boolean verbose) {
        super(sourceDir, destDir, null, verbose);
        this.rhinoJar = rhinoJar;
    }

    /**
     * Compress a list of JavaScript files found in sourceDir
     * and write the outpout to destDir.
     *
     * @param fileList <f0,...,fn> A comma separated list of relative file paths to compress.
     */
    public void compress(String[] fileList) throws IOException {
	for (int i = 0; i < fileList.length; ++i) {
	    try {
		File infile = new File(getSourceDir() + File.separator +
		    fileList[i]);
		File outfile = new File(getDestDir() + File.separator +
		    fileList[i]);
		compressFile(infile, outfile);
		pruneFile(outfile);
	    } catch (Exception e) {
		throw new IOException("Cannot compress or prune " +
		    getSourceDir() + File.separator + fileList[i] + " to " +
		    getDestDir() + File.separator + fileList[i]);
	    }
	}
    }

    /**
     * Compress JavaScript file.
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
     * @param file The JavaScript file to compress.
     */
    private void compressFile(File infile, File outfile) throws IOException {
        // The command line equivalent of the exec command is:
        //
	// java -jar <rhinoJar> -strict -opt -1 -o <outputfile>
	// 	-c <filetocompress> 
	//
	String cmd = 
            "java -jar " +  rhinoJar + 
	    " -strict -opt -1 -o " +
            outfile.getAbsolutePath() +
            " -c " +
            infile.getAbsolutePath();
	ExecProcess ep = new ExecProcess(cmd);
	int returnVal = ep.exec();
	if (returnVal != 0) {
	    System.exit(returnVal);
	}
    }

    /**
     * Prune new lines from JavaScript file.
     *
     * @param file The JavaScript file to compress.
     */
    private void pruneFile(File file) throws IOException {
	// Create buffer to hold file contents.
        StringBuffer buff = new StringBuffer();
        BufferedReader input = null;
	FileWriter output = null;

	try {
            input = new BufferedReader(new FileReader(file));
            String line = null;

            // readLine is a bit quirky:
            // it returns the content of a line MINUS the newline.
            while ((line = input.readLine()) != null) {
                // Alternatively, we could use line.replace(/[\r\n]/g, "")
                buff.append(line);
            }
            if (input != null) {
                input.close();
            }

	    // Write output.
	    output = new FileWriter(file);
	    output.write(buff.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e){
            e.printStackTrace();
	} finally {
	    try {
	        if (input != null) {
                    input.close();
	        }
		if (output != null) {
		    output.close();
		}
	    } catch (IOException e) {
                e.printStackTrace();
	    }
        }
    }
}
