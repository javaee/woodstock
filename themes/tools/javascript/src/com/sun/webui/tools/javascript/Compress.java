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
package com.sun.webui.tools.javascript;

import java.io.*;
import com.sun.webui.tools.ExecProcess;

public class Compress {
    private String rhinoJar = null;
    private boolean verbose = false;

    /**
     * Constructor.
     *
     * @param rhinoJar The jar containing the Rhino compress tool.
     * @param verbose Enable verbose output.
     */
    public Compress(String rhinoJar, boolean verbose) {
        this.rhinoJar = rhinoJar;
        this.verbose = verbose;
    }

    /**
     * Compress JavaScript directory or file.
     *
     * @param sourcePath Path to JavaScript directory or file.
     */
    public void reduce(String sourcePath) throws IOException {
        iterate(sourcePath);
    }

    /**
     * Compress JavaScript file.
     *
     * Note: Calling org.mozilla.javascript.tools.shell.Main.main
     * directly does not work well with large file sets. After only a few files, 
     * Java runs out of memory when redirecting stdout to a file. And when the
     * -o option is used in this scenario, Rhino compresses the same files. For
     * example:
     * 
     * Compressed file stored in 'scheduler.js'
     * Compressed file stored in 'image.js'
     * Compressed file stored in 'image.js'
     * Compressed file stored in 'textArea.js'
     * Compressed file stored in 'textArea.js'
     * Compressed file stored in 'textArea.js'
     * ...
     * 
     * Therefore, we need to run the Rhino program in a separate JVM and 
     * compress individual files.
     *
     * @param file The JavaScript file to compress.
     */
    private void reduceFile(File file) throws IOException {
        // The command line equivalent of the exec command is:
        //
	// java -jar <rhinoJar> -strict -opt -1 -o <outputfile>
	// 	-c <filetocompress> 
	//
	String cmd = 
            "java -jar " +  rhinoJar + 
	    " -strict -opt -1 -o " +
            file.getAbsolutePath() +
            " -c " +
            file.getAbsolutePath();
	ExecProcess ep = new ExecProcess(cmd);
	int returnVal = ep.exec();
	if (returnVal != 0) {
	    System.exit(returnVal);
	}
    }

    /**
     * Iterate over JavaScript directory or file.
     *
     * @param sourcePath Path to JavaScript directory or file.
     */
    private void iterate(String sourcePath) throws IOException {
        File file = new File(sourcePath);
        if (file.isDirectory()) {
            String[] fileNames = file.list();
            for (int i = 0; i < fileNames.length; i++) {
                String fileName = file.getAbsolutePath() + 
                    file.separator + fileNames[i];
                iterate(fileName);
            }
        } else {
            reduceFile(file);
        }
    }
}
