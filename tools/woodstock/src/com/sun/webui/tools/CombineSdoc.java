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

public class CombineSdoc extends CombineJavaScript {
    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param verbose Enable verbose output.
     * @param copyrightFile File path for copyright file.
     * @param outFile File path for combined output.
     */
    public CombineSdoc(String sourceDir, boolean verbose,
            String copyrightFile, String outFile) {
        super(sourceDir, verbose, copyrightFile, outFile, null); 
    }

    /**
     * Process file.
     *
     * @param file The JavaScript file to process.
     */
    protected boolean processFile(File file) throws IOException {
        if (!isFileAvailable(file)) {
            return false;
        }
        InputStream is = new FileInputStream(file);

	try {
            // Create the byte array to hold the data
            byte[] _byte = new byte[1];
            byte[] bytes = new byte[(int) file.length()];

            // Read in the bytes
            int offset = 0;
            int start = 0;
            boolean whitespace = false;
            while (is.read(_byte) != -1) {
                if (_byte[0] == '/') {
                    // Possible comment?
                    if (is.read(_byte) != -1) {

                        // Begin comment.
                        if (_byte[0] == '*') {
                            bytes[offset++] = '/';
                            bytes[offset++] = _byte[0];

                            while (is.read(_byte) != -1) {
                                if (_byte[0] == ' ' || _byte[0] == '\t') {
                                    // Strip extra whitespace.
                                    if (whitespace == true) {
                                        continue;
                                    }
                                    bytes[offset++] = ' ';
                                    whitespace = true;
                                } else if (_byte[0] == '*') {
                                    bytes[offset++] = _byte[0];
                                    whitespace = false;

                                    // Search for comment end.
                                    if (is.read(_byte) != -1) {
                                        if (_byte[0] == ' ' || _byte[0] == '\t') {
                                            // Strip extra whitespace.
                                            if (whitespace == true) {
                                                continue;
                                            }
                                            bytes[offset++] = ' ';
                                            whitespace = true;
                                        } else if (_byte[0] == '/') {
                                            // Found comment end.
                                            bytes[offset++] = _byte[0];
                                            bytes[offset++] = '\n';
                                            whitespace = false;
                                            break;
                                        } else {
                                            bytes[offset++] = _byte[0];
                                            whitespace = false;
                                        }
                                    }
                                } else if (_byte[0] == '@') {
                                    bytes[offset++] = _byte[0];
                                    whitespace = false;

                                    // Strip @private and @ignore tags.
                                    if (is.read(_byte) != -1) {
                                        if (_byte[0] == ' ' || _byte[0] == '\t') {
                                            // Strip extra whitespace.
                                            if (whitespace == true) {
                                                continue;
                                            }
                                            bytes[offset++] = ' ';
                                            whitespace = true;
                                        } else if (_byte[0] == 'p') {
                                            bytes[offset++] = _byte[0];
                                            whitespace = false;

                                            if (is.read(_byte) != -1) {
                                                bytes[offset++] = _byte[0];
                                                if (_byte[0] == 'r') {
                                                    if (is.read(_byte) != -1) {
                                                        bytes[offset++] = _byte[0];

                                                        // Found @private tag.
                                                        if (_byte[0] == 'i') {
                                                            // Clear comment.
	                                                    for (int i = start; i < offset; i++) {
                                                                bytes[i] = '\0';
                                                            }
                                                            offset = start;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (_byte[0] == 'i') {
                                            bytes[offset++] = _byte[0];
                                            whitespace = false;

                                            if (is.read(_byte) != -1) {
                                                bytes[offset++] = _byte[0];

                                                // Found @ignore tag.
                                                if (_byte[0] == 'g') {
                                                    // Clear comment.
						    for (int i = start; i < offset; i++) {
                                                        bytes[i] = '\0';
                                                    }
                                                    offset = start;
                                                    break;
                                                }
                                            }
                                        } else {
                                            bytes[offset++] = _byte[0];
                                            whitespace = false;
                                        }
                                    }
                                } else {
                                    bytes[offset++] = _byte[0];
                                    whitespace = false;
                                }
                            }
                        }
                    }
                }
                start = offset;
            }
	    // Write output.
            StringBuffer buff = getOutBuffer();
            buff.append(new String(bytes, 0, offset));
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
	    System.out.println("Combined sdoc file '" + file.getCanonicalPath() + "'");
	}
        return true;
    }
}
