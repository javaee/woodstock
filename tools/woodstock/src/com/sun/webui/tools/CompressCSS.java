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
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

public class CompressCSS extends CompressJavaScript {
    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param verbose Enable verbose output.
     * @param copyrightFile File path for copyright file.
     * @param destDir Directory where compressed files are written.
     */
    public CompressCSS(String sourceDir, boolean verbose, String copyrightFile,
            String destDir) {
        super(sourceDir, verbose, copyrightFile, destDir, null);
    }

    /**
     * Process file.
     *
     * @param inFile The JavaScript file to process.
     * @param outFile The JavaScript file to write output.
     */
    protected void processFile(File inFile, File outFile) throws IOException {
        if (!isFileAvailable(inFile)) {
            return;
        }
        InputStream is = new FileInputStream(inFile);

	try {
            // Create the byte array to hold the data
            byte[] _byte = new byte[1];
            byte[] bytes = new byte[(int) inFile.length()];

            // Read in the bytes
            int offset = 0;
            boolean whitespace = false;
            while (is.read(_byte) != -1) {
                if (_byte[0] == ' ' || _byte[0] == '\t') {
                    // Extra whitespace.
                    if (whitespace == true) {
                        continue;
                    }
                    bytes[offset++] = ' ';
                    whitespace = true;
                } else if (_byte[0] == '\n' || _byte[0] == '\r') {
                    // Remove new lines.
                    whitespace = true;
                    continue;
                } else if (_byte[0] == '/') {
                    // Possible comment?
                    if (is.read(_byte) != -1) {
                        // Begin comment.
                        if (_byte[0] == '*') {
                            while (is.read(_byte) != -1) {
                                // Search for end.
                                if (_byte[0] == '*') {
                                    if (is.read(_byte) != -1) {
                                        // End comment.
                                        if (_byte[0] == '/') {
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            bytes[offset++] = '/';
                            bytes[offset++] = _byte[0];
                        }
                    }
                } else {
                    bytes[offset++] = _byte[0];
                }
                whitespace = false;
            }
	    // Write output.
            writeOutFile(outFile, new String(bytes, 0, offset), false);
	} catch (IOException e){
            e.printStackTrace();
	} finally {
	    try {
	        if (is != null) {
                    is.close();
	        }
	    } catch (IOException e) {
                e.printStackTrace();
	    }
        }
        // Print status.
	if (isVerbose()) {
	    System.out.println("Compressed CSS file '" + inFile.getCanonicalPath() + "'");
	}
    }
}
