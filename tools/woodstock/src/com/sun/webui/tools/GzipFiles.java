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
import java.util.zip.GZIPOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
        
public class GzipFiles extends ToolsBase {
    private ArrayList combinedFiles = new ArrayList();

    public String GZ_EXT =".gz";

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param verbose Enable verbose output.
     * @param destDir Directory path for gzip'd files
     */
    public GzipFiles(String sourceDir, boolean verbose, String destDir) {
        super(sourceDir, verbose, null);
        setDestDir(destDir);
    }

    /**
     * Process a list of files found in sourceDir
     * and write the outpout to destDir.
     *
     * @param fileList <f0,...,fn> A comma separated list of relative file paths to process.
     */
    public void process(String[] fileList) throws IOException {
        String srcFilePath = "";
        String destFilePath = "";
        for (int i = 0; i < fileList.length; i++) {
            // build a path for the gzipp'd file
            srcFilePath = getSourceDir() + File.separator + fileList[i];
            destFilePath = getDestDir() + File.separator + fileList[i] + GZ_EXT;
            processFile(srcFilePath, destFilePath);            
        }
    }

    /**
     * Process file.
     *
     * @param file The file to process.
     */
    protected void processFile(String src, String dest) throws IOException {
        // create file objects for input and output streams
        File srcFile = new File (src);
        File destFile = new File(dest);
        OutputStreamWriter output = null;

	// Create buffer to hold source file contents.
        StringBuffer buff = new StringBuffer();
        BufferedReader input = null;

        // the GZip output stream
        FileOutputStream destOut = new FileOutputStream(destFile);
        GZIPOutputStream gzOut = new GZIPOutputStream(destOut);

	try {
            input = new BufferedReader(new FileReader(srcFile));
            String line = null;

            // readLine is a bit quirky:
            // it returns the content of a line MINUS the newline.
            while ((line = input.readLine()) != null) {
               	    buff.append(line);
                    buff.append(System.getProperty("line.separator"));
            }

	    // Write output.
            output = new OutputStreamWriter(gzOut);
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
	if (isVerbose()) {
	    System.out.println("GZIP'ed file '" + destFile.getCanonicalPath() + "'");
	}
    }
}
