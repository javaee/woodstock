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
import java.util.Iterator;
import java.util.TreeSet;

public class CombineCSS extends CombineJavaScript {
    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param outFile File path for combined output.
     * @param verbose Enable verbose output.
     */
    public CombineCSS(String sourceDir, String outFile, boolean verbose) {
        super(sourceDir, outFile, null, verbose);
    }

    /**
     * Combine CSS file.
     *
     * @param file The CSS file to combine.
     */
    private void combineFile(File file) throws IOException {
        if (isFileCombined(file)) {
            return;
        }

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
                    String fileName = getSourceDir() + File.separatorChar +
                        line.substring(first + 1, last);

		    combineFile(new File(fileName));
		} else {
               	    buff.append(line);
                    buff.append(System.getProperty("line.separator"));
		}
            }

	    // Write output.
	    output = new FileWriter(getOutFile(), true);
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
	    System.out.println("Combined CSS file '" + file.getCanonicalPath() + "'");
	}
    }
}
