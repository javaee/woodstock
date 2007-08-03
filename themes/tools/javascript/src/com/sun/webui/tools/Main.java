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

import com.sun.webui.tools.javascript.Compress;

import java.io.*;

/**
 * Main class to invoke build tools.
 */
public class Main {
    /**
     * Helper function to invoke compression tool.
     *
     * @param args Command line arguments.
     */
    public static void compress(String[] args) throws IOException {
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
            Compress compress = new Compress(rhinoJar, verbose);
            compress.reduce(sourcePath);
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
        System.out.println("-compress <args...>\t\tCompress JavaScript.");

        System.out.println("\nOptions for -compress include:");
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
            if (args[0].equals("-compress")) {
                compress(args);
            } else {
                usage();
            }
        } else {
            usage();
        }
    }
}
