/**
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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

/*
 This code is based on Dojo build scripts.

 Copyright (c) 2004-2007, The Dojo Foundation
 All Rights Reserved.

 Licensed under the Academic Free License version 2.1 or above OR the
 modified BSD license. For more information on Dojo licensing, see:

 http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/

//
// "The most efficient way to serve compressed JavaScript is to first run
// your code through a JavaScript compressor that shrinks variable and
// argument names, and then serve the resulting code using gzip
// compression."
//
// "If you can't use gzip compression for some reason, then Packer with
// base62 encoding turned on, is the next best thing. Scripts compressed
// with Packer are unpacked using one pass of a RegExp on most
// platforms. However, this still introduces an overhead for larger
// scripts -- typically 200 milliseconds."
// 
// See "Notes on JavaScript Compression" at:
// http://dean.edwards.name/weblog 
//

// Init Packer library.
initPacker = function(packerDir) {
    if (packerDir == null) {
        return false;
    }
    load(packerDir + "/base2.js");
    load(packerDir + "/Packer.js");
    load(packerDir + "/Words.js");
    return true;
}

// Optimize JavaScript.
optimizeJs = function(fileName, fileContents, copyright, optimizeType, base62, shrink) {
    // Use rhino to help do minifying/compressing.
    // Even when using Dean Edwards' Packer, run it through the custom rhino so
    // that the source is formatted nicely for Packer's consumption (in particular get
    // commas after function definitions).
    var context = Packages.org.mozilla.javascript.Context.enter();
    try {
        // Use the interpreter for interactive input (copied this from Main rhino class).
        context.setOptimizationLevel(-1);

        var script = context.compileString(fileContents, fileName, 1, null);
        if (optimizeType.indexOf("shrinksafe") == 0){
            // Apply compression using custom compression call in Dojo-modified rhino.
            fileContents = new String(context.compressScript(script, 0, fileContents, 1));
        } else if (optimizeType == "packer") {
            // Strip comments
            fileContents = new String(context.decompileScript(script, 0));
            var packer = new Packer();
            fileContents = packer.pack(fileContents, base62, shrink);

            // If this is an nls bundle, make sure it does not end in a ;
            // Otherwise, bad things happen.
            if (fileName.match(/\/nls\//)) {
                fileContents = fileContents.replace(/;\s*$/, "");
            }
        }
    } finally{
        Packages.org.mozilla.javascript.Context.exit();
    }
    return copyright + fileContents;
};

// Reads a file and returns a string.
readFile = function(path) {
    var file = new java.io.File(path);
    var input = new java.io.BufferedReader(new java.io.InputStreamReader(
        new java.io.FileInputStream(file)));

    try {
        var stringBuffer = new java.lang.StringBuffer();
        var line = input.readLine();
        while (line !== null) {
            stringBuffer.append(line);
            stringBuffer.append(java.lang.System.getProperty("line.separator"));
            line = input.readLine(); 
        } 
        // Make sure we return a JavaScript string and not a Java string. 
        return new String(stringBuffer.toString());
    } catch (err) {
        java.lang.System.out.println("Error: Cannot read file '" + fileName + "'");
        return null;
    } finally {
        input.close();
    }
    return null;
};

// Saves a file.  
saveFile = function(fileName, fileContents) {
    var outFile = new java.io.File(fileName);
    var outWriter = new java.io.OutputStreamWriter(new java.io.FileOutputStream(outFile));
    var os = new java.io.BufferedWriter(outWriter);
    try {
        os.write(fileContents);
    } catch (err) {
        java.lang.System.out.println("Error: Cannot save file '" + fileName + "'");
        return false;
    } finally {
        os.close();
    }
    return true;
};

// Print usage.
usage = function() {
    java.lang.System.out.println("Combine JavaScript files.");
    java.lang.System.out.println("\njava -jar rhinoJar jsFile <args...>");
    java.lang.System.out.println("\nwhere options include:");
    java.lang.System.out.println("-copyrightFile <copyrightFile>\tPath for copyright file. " +
        "Duplicate text is omitted from the combined file.");
    java.lang.System.out.println("-fileList <f0,...,fn>\t" +
        "A comma separated list of relative file paths to compress.");
    java.lang.System.out.println("-destDir <destDir>\t" +
        "Directory where compressed files are written.");
    java.lang.System.out.println("-packerDir <packerDir>\t" +
        "Directory where packer resources are located.");
    java.lang.System.out.println("-shrink\tShorten long variable names.") +
    java.lang.System.out.println("-sourceDir <sourceDir>\t" +
        "Directory containing the files in fileList.");
    java.lang.System.out.println("-base62\tPack files using base62 encoding. " +
        "Not necessary when using gzip.");
    java.lang.System.out.println("-verbose\tEnable verbose output.");
    return true;
};

// Parse arguments.
parseArgs = function(args) {
    for (var i = 0; i < args.length; i++) {
        if (args[i].length == 0) {
            continue;
        } else if (args[i] == "-base62") {
            this.base62 = true;
        } else if (args[i] == "-copyrightFile") {
            if (++i < args.length) {
                this.copyright = readFile(args[i]);
            } else {
                usage();
                return false;
            }
        } else if (args[i] == "-destDir") {
            if (++i < args.length) {
                this.destDir = args[i];
            } else {
                usage();
                return false;
            }
        } else if (args[i] == "-fileList") {
            if (++i < args.length) {
                this.fileList = args[i].split(",");
            } else {
                usage();
                return false;
            }
        } else if (args[i] == "-packerDir") {
            if (++i < args.length) {
                this.packerDir = args[i];
            } else {
                usage();
                return false;
            }
        } else if (args[i] == "-shrink") {
            this.shrink = true;
        } else if (args[i] == "-sourceDir") {
            if (++i < args.length) {
                this.sourceDir = args[i];
            } else {
                usage();
                return false;
            }
        } else if (args[i] == "-verbose") {
            this.verbose = true;
        } else {
            usage();
            return false;
        }
    }
    if (typeof destDir == "undefined") {
        java.lang.System.out.println("\nError: destDir undefined.");
    } else if (typeof fileList == "undefined") {
        java.lang.System.out.println("\nError: fileList undefined.");
    } else if (typeof sourceDir == "undefined") {
        java.lang.System.out.println("\nError: sourceDir undefined.");
    } else {
        return true;
    }
    usage();
    return false;
};

// Parse args.
if (parseArgs(arguments)) {
    // Initialize properties.
    this.base62 = (typeof base62 != "undefined") ? base62 : false;
    this.copyright = (typeof copyright != "undefined") ? copyright : "";
    this.optimizeType = (packerDir) ? "packer" : "shrinksafe";
    this.shrink = (typeof shrink != "undefined") ? shrink : false;

    // Initialize verbose message.
    var message = (base62 == true)
        ? (shrink == true) 
            ? "Compressed (base62/shrink) file stored in '"
            : "Compressed (base62) file stored in '"
        : "Compressed file stored in '";

    // Initialize packer.
    initPacker(packerDir);

    // Compress each file in list.
    for (var i = 0; i < fileList.length; i++) {
        var fileName = fileList[i];

        // Read in the file. Make sure we have a JS string.
        var sourceFile = sourceDir + java.io.File.separator + fileName;
        var fileContents = readFile(sourceFile);

        // Optimize JavaScript.
        fileContents = optimizeJs(sourceFile, fileContents, copyright,
            optimizeType, base62, shrink);

        // Write out the file with appropriate copyright.
        var destFile = destDir + java.io.File.separator + fileName;
        saveFile(destFile, fileContents);

        // Print message
        if (verbose) {
            java.lang.System.out.println(message + destFile + "'");
        }
    }
}
