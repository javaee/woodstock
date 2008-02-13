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

public class CombineJavaScript extends ToolsBase {
    private ArrayList combinedFiles = new ArrayList();
    private String modulePrefix = null;

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param outFile File path for combined output.
     * @param modulePrefix The JavaScript prefix for module sources.
     * @param verbose Enable verbose output.
     */
    public CombineJavaScript(String sourceDir, String outFile,
            String modulePrefix, boolean verbose) {
        super(sourceDir, null, outFile, verbose);
        this.modulePrefix = modulePrefix;
    }

    /**
     * Combine a list of JavaScript files found in sourceDir.
     *
     * @param fileList <f0,...,fn> A comma separated list of relative file paths to combine.
     */
    public void combine(String[] fileList) throws IOException {
        // Combine files in alphabetical order for consistency accross platforms.
        TreeSet ts = new TreeSet();
	for (int i = 0; i < fileList.length; i++) {
            ts.add(getSourceDir() + File.separator + fileList[i]);
        }
        Iterator files = ts.iterator();
        while (files.hasNext()) {
            combineFile(new File((String) files.next()));
        }
    }

    /**
     * Combine JavaScript file.
     *
     * @param file The JavaScript file to combine.
     */
    protected void combineFile(File file) throws IOException {
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
                if (line.indexOf(modulePrefix + ".dojo.provide") == 0) {
		    buff.setLength(0);
		}
		// Append required module.
		if (line.indexOf(modulePrefix + ".dojo.require") == 0) {
                    int first = line.indexOf("\"") + modulePrefix.length();
		    int last = line.indexOf("\"", first + 1);

		    // Get module file name.
                    String fileName = line.substring(first + 1, last)
                        .replace('.', File.separatorChar) + ".js";

		    // Skip Dojo resources.
		    if (fileName.indexOf("dojo") != -1 || fileName.indexOf("dijit") != -1) {
			continue;
		    }
		    combineFile(new File(getSourceDir() + fileName));
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
	    System.out.println("Combined JavaScript file '" + file.getCanonicalPath() + "'");
	}
    }

    /**
     * Helper method to determine if JavaScript file should be combined.
     *
     * @param file The JavaScript file to combine.
     */
    protected boolean isFileCombined(File file) {
        boolean result = false;

        try {
            if (combinedFiles.contains(file.getCanonicalPath())) {
	        result = true;
            }
            combinedFiles.add(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
