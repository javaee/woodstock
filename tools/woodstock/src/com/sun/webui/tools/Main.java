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
        String copyrightFile = null;
        String[] excludeFileList = null;
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
        copyrightFile = (String) map.get("copyrightFile");
        excludeFileList = (String[]) map.get("excludeFileList");
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CombineCSS obj = new CombineCSS(sourceDir, verbose, copyrightFile, outFile);
	obj.process(fileList, excludeFileList);
    }

    /**
     * Helper function to compress CSS.
     *
     * @param args Command line arguments.
     */
    public static void compressCSS(String[] args) throws IOException {
        String copyrightFile = null;
        String destDir = null;
	String[] fileList = null;
        String sourceDir = null;
        boolean verbose = false;

        // Parse arguments.
        Map map = parseFileListArgs(args);
	if (null == (destDir = (String) map.get("destDir"))) {
	    System.out.println("-destDir is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (fileList = (String[]) map.get("fileList")) ||
	        fileList.length == 0) {
	    System.out.println("A non emtpy file list is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (sourceDir = (String) map.get("sourceDir"))) {
	    System.out.println("-sourceDir is required.");
	    usage();
	    System.exit(-1);
	}
        copyrightFile = (String) map.get("copyrightFile");
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CompressCSS obj = new CompressCSS(sourceDir, verbose, copyrightFile, destDir);
	obj.process(fileList);
    }

    /**
     * Helper function to combine JavaScript.
     *
     * @param args Command line arguments.
     */
    public static void combineJavaScript(String[] args) throws IOException {
        String copyrightFile = null;
        String[] excludeFileList = null;
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
        copyrightFile = (String) map.get("copyrightFile");
        excludeFileList = (String[]) map.get("excludeFileList");
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CombineJavaScript obj = new CombineJavaScript(sourceDir, verbose, 
            copyrightFile, outFile, modulePrefix);
	obj.process(fileList, excludeFileList);
    }

    /**
     * Helper function to combine sdoc files.
     *
     * @param args Command line arguments.
     */
    public static void combineSdoc(String[] args) throws IOException {
        String copyrightFile = null;
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
        copyrightFile = (String) map.get("copyrightFile");
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CombineSdoc obj = new CombineSdoc(sourceDir, verbose, 
            copyrightFile, outFile);
	obj.process(fileList, null);
    }

    /**
     * Helper function to compress JavaScript.
     *
     * @param args Command line arguments.
     */
    public static void compressJavaScript(String[] args) throws IOException {
        String copyrightFile = null;
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
        copyrightFile = (String) map.get("copyrightFile");
	verbose = ((Boolean) map.get("verbose")).booleanValue();
	CompressJavaScript obj = new CompressJavaScript(sourceDir, verbose,
            copyrightFile, destDir, rhinoJar);
        obj.process(fileList);
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

    private static void combineImages(String[] args) {
	String sourceDir = null;
	String imageFile = null;
	String imagePropertyFile = null;
	String outFile = null;

	for (int i = 1; i < args.length; ++i) {
	    try {
		if (args[i].equals("-sourceDir")) {
		    validString(sourceDir = args[i + 1].trim());
		    ++i;
		} else
		if (args[i].equals("-imageFile")) {
		    validString(imageFile = args[i + 1].trim());
		    ++i;
		} else
		if (args[i].equals("-imagePropertyFile")) {
		    validString(imagePropertyFile = args[i + 1].trim());
		    ++i;
		} else
		if (args[i].equals("-outFile")) {
		    validString(outFile = args[i + 1].trim());
		    ++i;
		} else {
		    System.out.println("Unknown arguement " + args[i]);
		    usage();
		    System.exit(-1);
		}
	    } catch (Exception e) {
		System.out.println("Invalid argument value for " + args[i]);
		usage();
		System.exit(-1);
	    }
	}
	CombineImages ci = new CombineImages(sourceDir, imagePropertyFile,
            imageFile, outFile);
	ci.process();
    }

    private static void gzipFiles(String[] args) {
	String[] fileList = null;
        String sourceDir = null;
        String destDir = null;
        boolean verbose = false;

        // Parse arguments.
        Map map = parseFileListArgs(args);
	if (null == (fileList = (String[]) map.get("fileList")) ||
	        fileList.length == 0) {
	    System.out.println("A non emtpy file list is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (sourceDir = (String) map.get("sourceDir"))) {
	    System.out.println("-sourceDir is required.");
	    usage();
	    System.exit(-1);
	}
	if (null == (destDir = (String) map.get("destDir"))) {
	    System.out.println("-destDir is required.");
	    usage();
	    System.exit(-1);
	}
	verbose = ((Boolean) map.get("verbose")).booleanValue();

        try {
            GzipFiles obj = new GzipFiles(sourceDir, verbose, destDir);
            obj.process(fileList);
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
        System.out.println("-compressCSS <args...>\t\tCompress CSS directory or file.");
        System.out.println("-combineJavaScript <args...>\t\tCombine JavaScript directory or file.");
        System.out.println("-compressJavaScript <args...>\t\tCompress JavaScript directory or file.");
        System.out.println("-native2ascii <args...>\t\tConvert native encoded file to unicode escapes or vice versa.");
        System.out.println("-gzipFiles <args...>\t\tCompress a list of files using the gzip compression format.");

        System.out.println("\nOptions for -combineCSS include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to combine.");
	System.out.println("-excludeFileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to exclude.");
        System.out.println("-outFile <outFile>\tFile path for combined output.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
        System.out.println("-copyrightFile <copyrightFile>\tFile path for copyright file. " +
                "Duplicate text is omitted from the combined file.");
        System.out.println("-verbose\tEnable verbose output.");

        System.out.println("\nOptions for -compressCSS include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to combine.");
	System.out.println("-excludeFileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to exclude.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
        System.out.println("-copyrightFile <copyrightFile>\tFile path for copyright file. " +
                "Duplicate text is omitted from the combined file.");
        System.out.println("-verbose\tEnable verbose output.");

        System.out.println("\nOptions for -combineJS include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to combine.");
	System.out.println("-excludeFileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to exclude.");
	System.out.println("-modulePrefix <modulePrefix>\tThe JavaScript prefix for module sources.");
        System.out.println("-outFile <outFile>\tFile path for combined output.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
        System.out.println("-copyrightFile <copyrightFile>\tFile path for copyright file. " +
                "Duplicate text is omitted from the combined file.");
        System.out.println("-verbose\tEnable verbose output.");

        System.out.println("\nOptions for -combineSdoc include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to combine.");
        System.out.println("-outFile <outFile>\tFile path for combined output.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
        System.out.println("-copyrightFile <copyrightFile>\tFile path for copyright file. " +
                "Duplicate text is omitted from the combined file.");
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

        System.out.println("\nOptions for -combineImages include:");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the image files.");
        System.out.println("-imageFile\tFile path for combined image output.");
	System.out.println("-imagePropertyFile\t" +
		"File path for image properties file.");
        System.out.println("-outFile\tFile path for updated image properties file.");

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

        // gzip usage
        System.out.println("\nOptions for -gzipFiles include:");
	System.out.println("-fileList <f0,...,fn>\t" +
		"A comma separated list of relative file paths to compress.");
        System.out.println("-destDir <outFile> <destinationDir>\t" 
                + "Directory where compressed files will created.");
	System.out.println("-sourceDir <sourceDir>\t" +
		"Directory containing the files in fileList.");
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
            } else if (args[0].equals("-compressCSS")) {
                compressCSS(args);
            } else if (args[0].equals("-combineJS")) {
                combineJavaScript(args);
            } else if (args[0].equals("-combineSdoc")) {
                combineSdoc(args);
            } else if (args[0].equals("-compressJS")) {
                compressJavaScript(args);
            } else if (args[0].equals("-native2ascii")) {
                native2ascii(args);
            } else if (args[0].equals("-combineImages")) {
                combineImages(args);
            } else if (args[0].equals("-gzipFiles")) {
                gzipFiles(args);
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
        String copyrightFile = null;
	String destDir = null;
        String excludeFileList = null;
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
		    validString(destDir = args[i + 1].trim());
		    map.put("destDir", destDir);
		    ++i;
                } else
		if (args[i].equals("-excludeFileList")) {
		    validString(excludeFileList = args[i + 1].trim());
		    ++i;
                } else
		if (args[i].equals("-modulePrefix")) {
		    validString(modulePrefix = args[i + 1].trim());
		    map.put("modulePrefix", modulePrefix);
		    ++i;
                } else
		if (args[i].equals("-outFile")) {
		    validString(outFile = args[i + 1].trim());
		    map.put("outFile", outFile);
		    ++i;
                } else
                if (args[i].equals("-copyrightFile")) {
		    validString(copyrightFile = args[i + 1].trim());
		    map.put("copyrightFile", copyrightFile);
                    ++i;
		} else
		if (args[i].equals("-rhinoJar")) {
		    validString(rhinoJar = args[i + 1].trim());
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

        // Get file lists.
	map.put("fileList", getFileListArray(fileList));
        map.put("excludeFileList", getFileListArray(excludeFileList));
	return map;
    }

    private static String[] getFileListArray(String fileList) {
        if (fileList == null) {
            return null;
        }

	String[] fileListArray = null;
	try {
	    StringTokenizer fileListTokens = new StringTokenizer(fileList, ",");
	    int count = fileListTokens.countTokens();
	    if (count == 0) {
		return null;
	    }
	    fileListArray = new String[count];
	    for (int j = 0; fileListTokens.hasMoreTokens(); ++j) {
		fileListArray[j] = fileListTokens.nextToken();
	    }
	} catch (Exception e) {
            return null;
        }
        return fileListArray;
    }

    private static void validString(String s) throws Exception {
	if (s == null || s.length() == 0) {
	    throw new Exception("Invalid argument value");
	}
    }
}
