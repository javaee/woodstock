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
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

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
	String[] fileList = null;
	String outFile = null;
        String sourceDir = null;
        boolean verbose = false;

        // Parse arguments.
        Map map = parseFileListArgs(args);
	if (null == (fileList = (String[]) map.get("fileList")) ||
	        fileList.length == 0) {
	    System.out.println("A non emtpy file list is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (outFile = (String) map.get("outFile"))) {
	    System.out.println("-outFile is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (sourceDir = (String) map.get("sourceDir"))) {
	    System.out.println("-sourceDir is required.");
	    usage();
	    System.exit(-1);
	}
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CombineCSS obj = new CombineCSS(sourceDir, outFile, verbose);
	obj.combine(fileList);
    }

    /**
     * Helper function to combine JavaScript.
     *
     * @param args Command line arguments.
     */
    public static void combineJavaScript(String[] args) throws IOException {
	String[] fileList = null;
	String modulePrefix = null;
	String outFile = null;
        String sourceDir = null;
        boolean verbose = false;

        // Parse arguments.
        Map map = parseFileListArgs(args);
	if (null == (fileList = (String[]) map.get("fileList")) ||
	        fileList.length == 0) {
	    System.out.println("A non emtpy file list is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (modulePrefix = (String) map.get("modulePrefix"))) {
	    System.out.println("-modulePrefix is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (outFile = (String) map.get("outFile"))) {
	    System.out.println("-outFile is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (sourceDir = (String) map.get("sourceDir"))) {
	    System.out.println("-sourceDir is required.");
	    usage();
	    System.exit(-1);
	}
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CombineJavaScript obj = new CombineJavaScript(sourceDir, outFile,
            modulePrefix, verbose);
	obj.combine(fileList);
    }

    /**
     * Helper function to compress JavaScript.
     *
     * @param args Command line arguments.
     */
    public static void compressJavaScript(String[] args) throws IOException {
	String destDir = null;
	String[] fileList = null;
        String rhinoJar = null;
        String sourceDir = null;
        boolean verbose = false;

        // Parse arguments.
        Map map = parseFileListArgs(args);
	if (null == (fileList = (String[]) map.get("fileList")) ||
	        fileList.length == 0) {
	    System.out.println("A non emtpy file list is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (destDir = (String) map.get("destDir"))) {
	    System.out.println("-destDir is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (rhinoJar = (String) map.get("rhinoJar"))) {
	    System.out.println("-rhinoJar is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (sourceDir = (String) map.get("sourceDir"))) {
	    System.out.println("-sourceDir is required.");
	    usage();
	    System.exit(-1);
	}
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CompressJavaScript obj = new CompressJavaScript(sourceDir, destDir,
            rhinoJar, verbose);
        obj.compress(fileList);
    }

    private final static String n2aUsage =
    "Usage: java com.sun.webui.tools.Native2ascii [-encoding <encoding>]\n" +
    "[-reverse] -sourceDir <sourceDir> -destDir <destDir>\n " +
    "-fileList <f0, ... fn>\n" +
    "UTF-8 is the default encoding";
    public static void native2ascii(String[] args) {

	String sourceDir = null;
	String destDir = null;
	String fileList[] = null;
	String encoding = null;
	boolean reverse = false;

	// Count the "native2ascii" command
	//
	if (args.length < 4) {
	    System.out.println(n2aUsage);
	    System.exit(-1);
	}

	Map map = parseFileListArgs(args);
	if (null == (sourceDir = (String)map.get("sourceDir"))) {
	    System.out.println("-sourceDir is required." +
		"\n" + n2aUsage);
	    System.exit(-1);
	}
	if (null == (destDir = (String)map.get("destDir"))) {
	    System.out.println("-destDir is required."  +
		"\n" + n2aUsage);
	    System.exit(-1);
	}
	if (null == (fileList = (String[])map.get("fileList")) ||
		fileList.length == 0) {
	    System.out.println("A non emtpy file list is required.\n" +
		n2aUsage);
	    System.exit(-1);
	}

	// Move past the native2ascii command arg[0]
	//
	for (int i = 1; i < args.length; ++i) {
	    try {
		// Already handled
		if (args[i].equals("-sourceDir") ||
			args[i].equals("-destDir") ||
			args[i].equals("-fileList")) {
		    ++i;
		    continue;
		} else
		if (args[i].equals("-reverse")) {
		    reverse = true;
		    // Don't validate
		    continue;
		} else
		if (args[i].equals("-encoding")) {
		    validString(encoding = args[i + 1].trim());
		    ++i;
		} else {
		    System.out.println("Unknown arguement " + args[i] +
			"\n" + n2aUsage);
		    System.exit(-1);
		}
	    } catch (Exception e) {
		System.out.println("Invalid argument value for " + args[i] +
		    "\n" + n2aUsage);
		System.exit(-1);
	    }
	}

	Native2ascii n2a = null;
	try {
	    n2a = new Native2ascii(encoding, reverse,
		sourceDir, destDir, fileList);
	    n2a.convertAll();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	    System.exit(-1);
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
        System.out.println("-native2ascii <args...>\t\tConvert native encoded file to unicode escapes or vice versa.");

        System.out.println("\nOptions for -combineCSS include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to compress.");
        System.out.println("-outFile <outFile>\tFile path for combined output.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
        System.out.println("-verbose\tEnable verbose output.");

        System.out.println("\nOptions for -combineJS include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to compress.");
	System.out.println("-modulePrefix <modulePrefix>\tThe JavaScript prefix for module sources.");
        System.out.println("-outFile <outFile>\tFile path for combined output.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
        System.out.println("-verbose\tEnable verbose output.");

        System.out.println("\nOptions for -compressJS include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to compress.");
	System.out.println("-destDir <destDir>\t" +
		"Directory where compressed files are written.");
        System.out.println("-rhinoJar <rhinoJar>\tJar file containing the Rhino compression tool.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
        System.out.println("-verbose\tEnable verbose output.");

	System.out.println("\nOptions for -native2ascii include:");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing files in fileList.");
	System.out.println("-destDir <destDir>\t" +
		"Directory where converted files are written.");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to convert.");
	System.out.println("[-encoding <encoding>]\t" +
		"The encoding to convert from or to." +
		"UTF-8 is the default encoding");
	System.out.println("[-reverse]\t" +
		"Convert fileList to encoding");
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
            } else if (args[0].equals("-native2ascii")) {
                native2ascii(args);
            } else {
                usage();
            }
        } else {
            usage();
        }
    }

    /**
     * Return a Map with the file list args as keys and values
     * "sourceDir", "destDir" and "fileList", where "fileList" has
     * a value of String[].
     * The returned map may have none, some or all of the desired
     * args. It is up to the caller to check.
     */
    private static Map parseFileListArgs(String[] args) {
	String destDir = null;
	String fileList = null;
	String modulePrefix = null;
	String outFile = null;
	String rhinoJar = null;
	String sourceDir = null;

	HashMap map = new HashMap();

	// cmd + sourceDir + destDir + filesList == 4
	if (args.length < 4) {
	    return map;
	}

	// Move past the command arg[0]
	//
	for (int i = 1; i < args.length; ++i) {
	    try {
		if (args[i].equals("-fileList")) {
		    validString(fileList = args[i + 1].trim());
		    ++i;
		} else
		if (args[i].equals("-destDir")) {
		    destDir = args[i + 1].trim();
		    map.put("destDir", destDir);
		    ++i;
		} else
		if (args[i].equals("-modulePrefix")) {
		    modulePrefix = args[i + 1].trim();
		    map.put("modulePrefix", modulePrefix);
		    ++i;
		} else
		if (args[i].equals("-outFile")) {
		    outFile = args[i + 1].trim();
		    map.put("outFile", outFile);
		    ++i;
		} else
		if (args[i].equals("-rhinoJar")) {
		    rhinoJar = args[i + 1].trim();
		    map.put("rhinoJar", rhinoJar);
		    ++i;
		} else
		if (args[i].equals("-sourceDir")) {
		    validString(sourceDir = args[i + 1].trim());
		    map.put("sourceDir", sourceDir);
		    ++i;

		} else
		if (args[i].equals("-verbose")) {
		    map.put("verbose", new Boolean(true));
                }
	    } catch (Exception e) {
		return map;
	    }
	}

	String[] fileListArray = null;
	try {
	    StringTokenizer fileListTokens = new StringTokenizer(fileList, ",");
	    int count = fileListTokens.countTokens();
	    if (count == 0) {
		return map;
	    }
	    fileListArray = new String[count];
	    for (int j = 0; fileListTokens.hasMoreTokens(); ++j) {
		fileListArray[j] = fileListTokens.nextToken();
	    }
	} catch (Exception e) {
	    return map;
	}

	map.put("fileList", fileListArray);
	return map;
    }

    private static void validString(String s) throws Exception {
	if (s == null || s.length() == 0) {
	    throw new Exception("Invalid argument value");
	}
    }

}
