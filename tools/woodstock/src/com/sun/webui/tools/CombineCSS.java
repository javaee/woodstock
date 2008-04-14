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
import java.io.FileReader;

public class CombineCSS extends CombineJavaScript {
    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param verbose Enable verbose output.
     * @param copyrightFile File path for copyright file.
     * @param outFile File path for combined output.
     */
    public CombineCSS(String sourceDir, boolean verbose, String copyrightFile,
            String outFile) {
        super(sourceDir, verbose, copyrightFile, outFile, null);
    }

    /**
     * Process file.
     *
     * @param file The file to process.
     */
    protected boolean processFile(File file) throws IOException {
        if (!isFileAvailable(file)) {
            return false;
        }

        BufferedReader input = new BufferedReader(new FileReader(file));
        StringBuffer buff = getOutBuffer();
        String line = null;

        // readLine is a bit quirky:
        // it returns the content of a line MINUS the newline.	    
        while ((line = input.readLine()) != null) {
            boolean omitLine = false;

            // Test for required module.
            if (line.indexOf("@import url") == 0) {
                // Append required module.
		int first = line.indexOf("(");
		int last = line.indexOf(")", first + 1);

		// Get module file name.
                String fileName = getSourceDir() + File.separatorChar +
                    line.substring(first + 1, last);

                omitLine = processFile(new File(fileName));
            }
 
            // Omit line if and only if file was included sucessfully.
            if (!omitLine) {
                buff.append(line);
                buff.append(System.getProperty("line.separator"));
            }
        }

        try {
	    if (input != null) {
                input.close();
	    }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Print status.
	if (isVerbose()) {
	    System.out.println("Combined CSS file '" + file.getCanonicalPath() + "'");
	}
        return true;
    }
}
