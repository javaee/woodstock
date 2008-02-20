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
    private File copyrightFile = null;
    private String modulePrefix = null;
    private StringBuffer outputBuffer = null;
    private ArrayList processedFiles = new ArrayList();

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param outFile File path for combined output.
     * @param copyrightFile File path for copyright file.
     * @param modulePrefix The JavaScript prefix for module sources.
     * @param verbose Enable verbose output.
     */
    public CombineJavaScript(String sourceDir, String outFile, String copyrightFile,
            String modulePrefix, boolean verbose) {
        super(sourceDir, null, outFile, verbose);
        this.modulePrefix = modulePrefix;
        if (copyrightFile != null) {
            this.copyrightFile = new File(copyrightFile);
        }
    }

    /**
     * Combine a list of JavaScript files found in sourceDir.
     *
     * @param fileList <f0,...,fn> A comma separated list of relative file paths to combine.
     * @param excludeFileList <f0,...,fn> A comma separated list of relative file paths to exclude.
     */
    public void combine(String[] fileList, String[] excludeFileList) throws IOException {
        if (fileList == null) {
            return;
        }

        // Exclude files from being added via dojo.require() statements.
        if (excludeFileList != null) {
            for (int i = 0; i < excludeFileList.length; i++) {
                excludeFile(new File(getSourceDir() + File.separator + excludeFileList[i]));
            }
        }

        // Combine files in alphabetical order for consistency accross platforms.
        TreeSet ts = new TreeSet();
	for (int i = 0; i < fileList.length; i++) {
            ts.add(getSourceDir() + File.separator + fileList[i]);
        }
        try {
            Iterator files = ts.iterator();
            while (files.hasNext()) {
                combineFile(new File((String) files.next()));
            }
            writeFile(getOutFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e){
            e.printStackTrace();
	}
    }

    /**
     * Combine JavaScript file.
     *
     * @param file The JavaScript file to combine.
     */
    protected boolean combineFile(File file) throws IOException {
        if (!isFileAvailable(file)) {
            return false;
        }

        BufferedReader input = new BufferedReader(new FileReader(file));
        StringBuffer buff = getOutputBuffer();
        String line = null;

        // readLine is a bit quirky:
        // it returns the content of a line MINUS the newline.
        while ((line = input.readLine()) != null) {
            boolean omitLine = false;

            // Test for required module.
            if (line.indexOf(modulePrefix + ".dojo.require") == 0) {
                int first = line.indexOf("\"") + modulePrefix.length();
		int last = line.indexOf("\"", first + 1);

		// Get module file name.
                String fileName = line.substring(first + 1, last)
                    .replace('.', File.separatorChar) + ".js";

                omitLine = combineFile(new File(getSourceDir() + fileName));
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
            if (line.indexOf(modulePrefix + ".dojo.require") == 0) {
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
    protected StringBuffer getOutputBuffer() throws IOException {
        if (outputBuffer == null) {
            outputBuffer = new StringBuffer(1024);
        }
        return outputBuffer;
    }

    /**
     * Get copyright text.
     *
     * @param file The copyright file.
     */
    protected String getCopyright(File file) throws IOException {
        if (file == null || !file.canRead()) {
            return null;
        }

	// Create buffer to hold file contents.
        BufferedReader input = new BufferedReader(new FileReader(file));
        StringBuffer buff = new StringBuffer();
        String line = null;

        // readLine is a bit quirky:
        // it returns the content of a line MINUS the newline.
        while ((line = input.readLine()) != null) {
            buff.append(line);
            buff.append(System.getProperty("line.separator"));
        }

        try {
	    if (input != null) {
                input.close();
	    }
	} catch (IOException e) {
            e.printStackTrace();
	}
        return buff.toString();
    }

    /**
     * Get copyright file.
     */
    protected File getCopyrightFile() {
        return copyrightFile;
    }

    /**
     * Test if JavaScript file is available.
     *
     * @param file The JavaScript file.
     */
    protected boolean isFileAvailable(File file) {
        boolean result = true;
        if (file == null || !file.canRead()) {
            return false;
        }

        try {
            if (processedFiles.contains(file.getCanonicalPath())) {
	        result = false;
            }
            processedFiles.add(file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Write combined file.
     *
     * @param file The combined file to write.
     */
    protected void writeFile(File file) throws IOException {
        if (file == null) {
            return;
        }

	FileWriter output = new FileWriter(file, true);
        String copyright = getCopyright(getCopyrightFile());
        StringBuffer buff = getOutputBuffer();

        // Write output.
        if (copyright != null) {
            output.write(copyright);

            // Remove duplicate copyright text before writing output.
            output.write(buff.toString().replace(copyright.trim(), ""));
        } else {
            output.write(buff.toString());
        }

	try {
            if (output != null) {
                output.close();
            }
	} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
