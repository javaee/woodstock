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
import java.util.ArrayList;

public class CombineCSS {
    private String combinedFile = null;
    private ArrayList combinedFiles = new ArrayList();
    private String modulePath = null;
    private boolean verbose = false;

    /**
     * Constructor.
     *
     * @param combinedFile File path for combined output.
     * @param modulePath The path to locate module sources.
     * @param verbose Enable verbose output.
     */
    public CombineCSS(String combinedFile, String modulePath, boolean verbose) {
        try {
            this.combinedFile = combinedFile;
            this.modulePath = new File(modulePath).getCanonicalPath();
            this.verbose = verbose;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Combine JavaScript directory or file.
     *
     * @param sourcePath Path to JavaScript directory or file.
     */
    public void combine(String sourcePath) throws IOException {
        combineDir(sourcePath);
    }

    /**
     * Combine JavaScript file.
     *
     * @param file The JavaScript file to combine.
     */
    private void combineFile(File file) throws IOException {
	if (combinedFiles.contains(file.getCanonicalPath())) {
	    return;
	}
	combinedFiles.add(file.getCanonicalPath());

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
		// Strip copyright, it will be added later.
		if (line.indexOf("/* CSS Document */") == 0) {
		    buff.setLength(0);
		}
		// Append required module.
		if (line.indexOf("@import url") == 0) {
		    buff.setLength(0);

                    // Append required module.
		    int first = line.indexOf("(");
		    int last = line.indexOf(")", first + 1);

		    // Get module file name.
                    String fileName = modulePath + File.separatorChar +
                        line.substring(first + 1, last);

		    combineFile(new File(fileName));
		} else {
               	    buff.append(line);
                    buff.append(System.getProperty("line.separator"));
		}
            }

	    // Write output.
	    output = new FileWriter(combinedFile, true);
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
	if (verbose) {
	    System.out.println("Combined CSS file '" + file.getCanonicalPath() + "'");
	}
    }

    /**
     * Combine JavaScript directory or file.
     *
     * @param sourcePath Path to JavaScript directory or file.
     */
    private void combineDir(String sourcePath) throws IOException {
        File file = new File(sourcePath);
        if (file.isDirectory()) {
            String[] fileNames = file.list();
            for (int i = 0; i < fileNames.length; i++) {
                String fileName = file.getAbsolutePath() + 
                    File.separator + fileNames[i];
                combineDir(fileName);
            }
        } else {
            combineFile(file);
        }
    }
}
