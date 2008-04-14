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
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;

public class ToolsBase {
    private File copyrightFile = null;
    private File destDir = null;
    private File sourceDir = null;
    private File outFile = null;
    private boolean verbose = false;
    private ArrayList processedFiles = new ArrayList();

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param verbose Enable verbose output.
     * @param copyrightFile File path for copyright file.
     */
    public ToolsBase(String sourceDir, boolean verbose, String copyrightFile) {
        setSourceDir(sourceDir);
        setCopyrightFile(copyrightFile);
        setVerbose(verbose);
    }

    /**
     * Get copyright file.
     */
    protected File getCopyrightFile() {
        return copyrightFile;
    }

    /**
     * Get copyright text.
     */
    protected String getCopyright() throws IOException {
        File file = getCopyrightFile();
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
     * Get destination directory.
     */
    protected String getDestDir() throws IOException {
        return destDir.getCanonicalPath();
    }

    /**
     * Get out file.
     */
    protected File getOutFile() {
        return outFile;
    }

    /**
     * Get source directory.
     */
    protected String getSourceDir() throws IOException {
        return sourceDir.getCanonicalPath();
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
     * Test verbose.
     */
    protected boolean isVerbose() {
        return verbose;
    }

    /**
     * Set copyright file.
     * 
     * @param copyrightFile File path for copyright file.
     */
    protected void setCopyrightFile(String copyrightFile) {
        if (copyrightFile == null) {
            return;
        }
        this.copyrightFile = new File(copyrightFile);
    }
    
    /**
     * Set destination directory.
     * 
     * @param destDir Directory where compressed files are written.
     */
    protected void setDestDir(String destDir) {
        if (destDir == null) {
            return;
        }
        try {
            this.destDir = new File(destDir);
            validateDirectory(this.destDir, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set out file.
     * 
     * @param outFile File path for combined output.
     */
    protected void setOutFile(String outFile) {
        if (outFile == null) {
            return;
        }
        this.outFile = new File(outFile);
    }

    /**
     * Set source directory.
     * 
     * @param sourceDir Directory containing the files in fileList.
     */
    protected void setSourceDir(String sourceDir) {
        if (sourceDir == null) {
            return;
        }
        try {
            this.sourceDir = new File(sourceDir);
            validateDirectory(this.sourceDir, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set verbose.
     * 
     * @param verbose Enable verbose output.
     */
    protected void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    protected void validateDirectory(File dir, boolean readOnly) 
	    throws IOException {
	if (!dir.isDirectory()) {
	    throw new IOException(dir.getAbsolutePath() + " is not a directory.");
	}
	if (readOnly && !dir.canRead()) {
	    throw new IOException(dir.getAbsolutePath() + " is not readable.");
	}
	if (!readOnly && !dir.canWrite()) {
	    throw new IOException(dir.getAbsolutePath() + " is not writable.");
	}
    }

    /**
     * Write output file.
     *
     * @param file The output file to write.
     * @param str The string to output.
     * @param append Append output to end of file.
     */
    protected void writeOutFile(File file, String str, boolean append)
            throws IOException {
        if (file == null) {
            return;
        }
	FileWriter output = new FileWriter(file, append);
        String copyright = getCopyright();

        // Append copyright.
        if (copyright != null) {
            output.write(copyright); // Leave trailing whitespace.

            // Remove duplicate copyright text before writing output.
            output.write(str.replace(copyright.trim(), ""));
        } else {
            output.write(str);
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
