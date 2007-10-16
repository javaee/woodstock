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

import java.io.IOException;
import java.io.File;

public class ToolsBase {
    private File destDir = null;
    private File sourceDir = null;
    private File outFile = null;
    private boolean verbose = false;

    /**
     * Constructor.
     *
     * @param sourceDir Directory containing the files in fileList.
     * @param destDir Directory where compressed files are written.
     * @param outFile File path for combined output.
     * @param verbose Enable verbose output.
     */
    public ToolsBase(String sourceDir, String destDir, String outFile, boolean verbose) {
        try {
            if (destDir != null) {
                this.destDir = new File(destDir);
                validateDirectory(this.destDir, false);
            }
            if (sourceDir != null) {
                this.sourceDir = new File(sourceDir);
                validateDirectory(this.sourceDir, true);
            }
            if (outFile != null) {
                this.outFile = new File(outFile);
            }
            this.verbose = verbose;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getDestDir() throws IOException {
        return destDir.getCanonicalPath();
    }

    protected String getSourceDir() throws IOException {
        return sourceDir.getCanonicalPath();
    }

    protected File getOutFile() {
        return outFile;
    }

    protected boolean isVerbose() {
        return verbose;
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
}
