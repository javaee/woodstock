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

import java.util.Iterator;
import java.util.TreeSet;

public class CombineJavaScript extends ToolsBase {
    private StringBuffer outputBuffer = null;
    private String modulePrefix = null;

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param verbose Enable verbose output.
     * @param copyrightFile File path for copyright file.
     * @param outFile File path for combined output.
     * @param modulePrefix The JavaScript prefix for module sources.
     */
    public CombineJavaScript(String sourceDir, boolean verbose,
            String copyrightFile, String outFile, String modulePrefix) {
        super(sourceDir, verbose, copyrightFile);
        setOutFile(outFile);
        this.modulePrefix = modulePrefix;
    }

    /**
     * Exclude JavaScript file.
     *
     * @param file The JavaScript file to exclude.
     */
    protected void excludeFile(File file) throws IOException {
        if (!isFileAvailable(file)) {
            return;
        }

        BufferedReader input = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = input.readLine()) != null) {
            // Test for required module.
            if (line.indexOf(modulePrefix + "._dojo.require") == 0) {
                int first = line.indexOf("\"") + modulePrefix.length();
		int last = line.indexOf("\"", first + 1);

		// Get module file name.
                String fileName = line.substring(first + 1, last)
                    .replace('.', File.separatorChar) + ".js";

                excludeFile(new File(getSourceDir() + fileName));
            }
        }
	try {
	    if (input != null) {
                input.close();
	    }
	} catch (IOException e) {
            e.printStackTrace();
	}
    }

    /**
     * Get the output buffer.
     */
    protected StringBuffer getOutBuffer() throws IOException {
        if (outputBuffer == null) {
            outputBuffer = new StringBuffer(1024);
        }
        return outputBuffer;
    }

    /**
     * Process a list of JavaScript files found in sourceDir.
     *
     * @param fileList <f0,...,fn> A comma separated list of relative file paths to process.
     * @param excludeFileList <f0,...,fn> A comma separated list of relative file paths to exclude.
     */
    public void process(String[] fileList, String[] excludeFileList) throws IOException {
        if (fileList == null) {
            return;
        }

        // Exclude files from being added via dojo.require() statements.
        if (excludeFileList != null) {
            for (int i = 0; i < excludeFileList.length; i++) {
                excludeFile(new File(getSourceDir() + File.separator + excludeFileList[i]));
            }
        }

        // Process files in alphabetical order for consistency accross platforms.
        TreeSet ts = new TreeSet();
	for (int i = 0; i < fileList.length; i++) {
            ts.add(getSourceDir() + File.separator + fileList[i]);
        }
        try {
            Iterator files = ts.iterator();
            while (files.hasNext()) {
                processFile(new File((String) files.next()));
            }
            writeOutFile(getOutFile(), getOutBuffer().toString(), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e){
            e.printStackTrace();
	}
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
            if (line.indexOf(modulePrefix + "._dojo.require") == 0) {
                int first = line.indexOf("\"") + modulePrefix.length();
		int last = line.indexOf("\"", first + 1);

		// Get module file name.
                String fileName = line.substring(first + 1, last)
                    .replace('.', File.separatorChar) + ".js";

                omitLine = processFile(new File(getSourceDir() + fileName));
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
	    System.out.println("Combined JavaScript file '" + file.getCanonicalPath() + "'");
	}
        return true;
    }
}
