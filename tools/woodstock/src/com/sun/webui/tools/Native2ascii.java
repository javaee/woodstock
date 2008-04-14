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

import java.io.File;
//import java.util.StringTokenizer;

import com.sun.webui.tools.ExecProcess;

public class Native2ascii {

    private File sourceDir;
    private File destDir;
    private String[] fileList;
    private boolean reverse;
    private String encoding;

    /**
     * Constructor.
     *
     * @param encoding The encoding to convert to or from.
     * @param reverse If false convert from <code>encoding</code>, if true
     * convert to <code>encoding</code>. The default value is false.
     * @param sourceDir The parent directory of the files in
     * <code>fileList</code>
     * @param destDir The parent directory in which to place the converted
     * files in <code>fileList</code>.
     * @param fileList A list of files originating in <code>sourceDir</code>
     * to convert.
     */
    public Native2ascii(String encoding, boolean reverse,
	    String sourceDir, String destDir, String[] fileList) 
	    throws Exception {

        this.sourceDir = new File(sourceDir);
	validateDirectory(this.sourceDir, true);

        this.destDir = new File(destDir);
	validateDirectory(this.destDir, false);

	this.fileList = fileList;
	if (this.fileList == null || this.fileList.length == 0) {
	    throw new Exception("Empty file list.");
	}
	this.encoding = encoding == null || encoding.trim().length() == 0 ?
	    "UTF-8" : encoding.trim();
	this.reverse = reverse;
    }

    protected void validateDirectory(File dir, boolean readOnly) 
	    throws Exception {

	if (!dir.isDirectory()) {
	    throw new Exception(dir.getCanonicalPath() + " is not a directory.");
	}
	if (readOnly && !dir.canRead()) {
	    throw new Exception(dir.getCanonicalPath() + " is not readable.");
	}
	if (!readOnly && !dir.canWrite()) {
	    throw new Exception(dir.getCanonicalPath() + " is not writable.");
	}
    }

    /**
     * Call native2ascii on a single file.
     *
     * @param sourceFile The file to convert
     * @param destFile The output file
     */
    protected void convert(String sourceFile, String destFile) 
	    throws Exception {

	String bindir = System.getProperty("jbin");
	String cmd = (String)(bindir == null ? "" : bindir + File.separator) +
	    "native2ascii -encoding " +  encoding + " " +
	    (reverse ? "-reverse " : "") + " " + 
	    sourceFile + " " +
	    destFile + " ";

	ExecProcess ep = new ExecProcess(cmd);
	int returnVal = ep.exec();
	if (returnVal != 0) {
	    System.exit(returnVal);
	}
    }

    /**
     * Convert the complete list of files.
     */
    public void convertAll() throws Exception {

	String absSourceDir = sourceDir.getCanonicalPath();
	String absDestDir = destDir.getCanonicalPath();
	for (int i = 0; i < fileList.length; ++i) {
	    try {
		convert(absSourceDir + File.separator + fileList[i],
		    absDestDir + File.separator + fileList[i]);
	    } catch (Exception e) {
		throw new Exception("Cannot covert " +
		    absSourceDir + File.separator + fileList[i] + " to " +
		    absDestDir + File.separator + fileList[i], e);
	    }
	}

    }

    /*
    private final static String usage =
    "Usage: java com.sun.webui.tools.Native2ascii [-encoding <encoding>]\n" +
    "[-reverse] -sourceDir <sourceDir> -destDir <destDir>\n " +
    "-fileList <f0, ... fn>\n" +
    "UTF-8 is the default encoding";

    public static final void main(String[] args) {

	String sourceDir = null;
	String destDir = null;
	String fileList = null;
	String encoding = null;
	boolean reverse = false;

	if (args.length < 3) {
	    System.out.println(usage);
	    System.exit(-1);
	}

	for (int i = 0; i < args.length; ++i) {
	    try {
		if (args[i].equals("-sourceDir")) {
		    validString(sourceDir = args[i + 1].trim());
		    ++i;
		} else
		if (args[i].equals("-destDir")) {
		    destDir = args[i + 1].trim();
		    ++i;
		} else
		if (args[i].equals("-fileList")) {
		    validString(fileList = args[i + 1].trim());
		    ++i;
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
			"\n" + usage);
		    System.exit(-1);
		}
	    } catch (Exception e) {
		System.out.println("Invalid argument value for " + args[i] +
		    "\n" + usage);
		System.exit(-1);
	    }
	}

	String[] fileListArray = null;
	StringTokenizer fileListTokens = new StringTokenizer(fileList, ",");
	try {
	    int count = fileListTokens.countTokens();
	    if (count == 0) {
		System.out.println("Emtpy file list.\n" + usage);
		System.exit(-1);
	    }
	    fileListArray = new String[count];
	    for (int j = 0; fileListTokens.hasMoreTokens(); ++j) {
		fileListArray[j] = fileListTokens.nextToken();
	    }
	} catch (Exception e) {
	    System.out.println(usage);
	    e.printStackTrace();
	    System.exit(-1);
	}

	Native2ascii n2a = null;
	try {
	    n2a = new Native2ascii(encoding, reverse,
		sourceDir, destDir, fileListArray);
	    n2a.convertAll();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	    System.exit(-1);
	}
    }

    private static void validString(String s) throws Exception {
	if (s == null || s.length() == 0) {
	    throw new Exception("Invalid argument value");
	}
    }
    */
}
