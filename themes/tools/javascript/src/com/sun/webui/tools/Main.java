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

import java.io.*;

/**
 * Main class to invoke build tools.
 */
public class Main {
    /**
     * Helper function to combine CSS.
     *
     * @param args Command line arguments.
     */
    public static void combineCSS(String[] args) throws IOException {
	String combinedFile = null;
        String modulePath = null;
        String sourcePath = null;
        boolean verbose = false;

        // Parse arguments.
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-verbose")) {
                verbose = true;
            } else if (i + 1 < args.length) {
                if (args[i].equals("-combinedFile")) {
                    combinedFile = args[++i];
                }
                if (args[i].equals("-modulePath")) {
                    modulePath = args[++i];
                }
                if (args[i].equals("-sourcePath")) {
                    sourcePath = args[++i];
                }
            }
	}

        if (combinedFile != null && modulePath != null && sourcePath != null) {
            CombineCSS obj = new CombineCSS(combinedFile, modulePath, verbose);
            obj.combine(sourcePath);
        } else {
            usage();
        }
    }

    /**
     * Helper function to combine JavaScript.
     *
     * @param args Command line arguments.
     */
    public static void combineJavaScript(String[] args) throws IOException {
	String combinedFile = null;
	String modulePath = null;
	String modulePrefix = null;
        String sourcePath = null;
        boolean verbose = false;

        // Parse arguments.
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-verbose")) {
                verbose = true;
            } else if (i + 1 < args.length) {
                if (args[i].equals("-combinedFile")) {
                    combinedFile = args[++i];
                }
                if (args[i].equals("-modulePath")) {
                    modulePath = args[++i];
                }
                if (args[i].equals("-modulePrefix")) {
                    modulePrefix = args[++i];
                }
                if (args[i].equals("-sourcePath")) {
                    sourcePath = args[++i];
                }
            }
	}

        if (combinedFile != null && modulePath != null && modulePrefix != null 
                && sourcePath != null) {
            CombineJavaScript obj = new CombineJavaScript(combinedFile,
                modulePath, modulePrefix, verbose);
            obj.combine(sourcePath);
        } else {
            usage();
        }
    }

    /**
     * Helper function to compress JavaScript.
     *
     * @param args Command line arguments.
     */
    public static void compressJavaScript(String[] args) throws IOException {
        String sourcePath = null;
        String rhinoJar = null;
        boolean verbose = false;

        // Parse arguments.
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-verbose")) {
                verbose = true;
            } else if (i + 1 < args.length) {
                if (args[i].equals("-sourcePath")) {
                    sourcePath = args[++i];
                } else if (args[i].equals("-rhinoJar")) {
                    rhinoJar = args[++i];
                }
            }
	}

        if (sourcePath != null && rhinoJar != null) {
            CompressJavaScript obj = new CompressJavaScript(rhinoJar, verbose);
            obj.compress(sourcePath);
        } else {
            usage();
        }
    }

    /**
     * Helper function to show usage.
     *
     * @param args Command line arguments.
     */
    public static void usage() {
        System.out.println("java -jar jarFile <args...>");

        System.out.println("\nwhere options include:");
        System.out.println("-combineCSS <args...>\t\tCombine CSS directory or file.");
        System.out.println("-combineJavaScript <args...>\t\tCombine JavaScript directory or file.");
        System.out.println("-compressJavaScript <args...>\t\tCompress JavaScript directory or file.");

        System.out.println("\nOptions for -combineCSS include:");
        System.out.println("-combinedFile\tFile path for combined output.");
        System.out.println("-modulePath\tThe path to locate module sources.");
	System.out.println("-sourcePath\tJavaScript directory or file to combine.");
        System.out.println("-verbose\tEnable verbose output.");

        System.out.println("\nOptions for -combineJS include:");
        System.out.println("-combinedFile\tFile path for combined output.");
        System.out.println("-modulePath\tThe path to locate module sources.");
	System.out.println("-modulePrefix\tThe JavaScript prefix for module sources.");
	System.out.println("-sourcePath\tJavaScript directory or file to combine.");
        System.out.println("-verbose\tEnable verbose output.");

        System.out.println("\nOptions for -compressJS include:");
        System.out.println("-rhinoJar\tJar file containing the Rhino compression tool.");
        System.out.println("-sourcePath\tJavaScript directory or file to compress.");
        System.out.println("-verbose\tEnable verbose output.");
    }

    /**
     * Parse arguments and invoke tools.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            if (args[0].equals("-combineCSS")) {
                combineCSS(args);
            } else if (args[0].equals("-combineJS")) {
                combineJavaScript(args);
            } else if (args[0].equals("-compressJS")) {
                compressJavaScript(args);
            } else {
                usage();
            }
        } else {
            usage();
        }
    }
}
