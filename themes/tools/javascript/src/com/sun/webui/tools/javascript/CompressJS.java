package com.sun.webui.tools.javascript;

import java.io.*;

public class CompressJS {
    private String rhinoJar = null;
    private boolean verbose = false;

    /**
     * Constructor.
     *
     * @param rhinoJar The jar containing the Rhino compress tool.
     * @param verbose Enable verbose output.
     */
    public CompressJS(String rhinoJar, boolean verbose) {
        this.rhinoJar = rhinoJar;
        this.verbose = verbose;
    }

    /**
     * Compress JavaScript directory or file.
     *
     * @param sourcePath Path to JavaScript directory or file.
     */
    public void compress(String sourcePath) throws IOException {
        iterate(sourcePath);
    }

    /**
     * Compress JavaScript file.
     *
     * Note: Calling org.mozilla.javascript.tools.shell.Main.main
     * directly does not work well for large file sets. After only a
     * few files, Java runs out of memory. Therefore, we need to run
     * the Rhino program on individual files.
     *
     * @param file The JavaScript file to compress.
     */
    private void compressFile(File file) throws IOException {
        // The command line equivelant of the exec command is:
        //
        // java -jar custom_rhino.jar -c infile.js > outfile.js
        //
	Process p = Runtime.getRuntime().exec(new String[] {
            "java",
            "-jar",
            rhinoJar,
            "-strict",
            "-opt",
            "-1",
            "-c",
            file.getAbsolutePath()
        });

        // Temp file to save output.
        File tmpFile = new File(file.getAbsolutePath() + ".tmp");
        FileOutputStream streamOut = new FileOutputStream(tmpFile);

        // Write output to file.
        InputStream streamIn = p.getInputStream();

        int c;
        while ((c = streamIn.read()) != -1) {
           streamOut.write(c);
        }

        streamIn.close();
        streamOut.close();

        // Rename file.
        file.delete();
        tmpFile.renameTo(file);
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
            if (verbose) {
                System.out.println("Compressing: " + sourcePath);
            }
            compressFile(file);
        }
    }
}
