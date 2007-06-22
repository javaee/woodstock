package com.sun.webui.theme.javascript;

import java.io.*;

public class Compress {
    private String rhinoJar = null;
    private boolean verbose = false;

    /**
     * Constructor.
     *
     * @param rhinoJar The jar containing the Rhino compress tool.
     */
    public Compress(String rhinoJar, boolean verbose) {
        this.rhinoJar = rhinoJar;
        this.verbose = verbose;
    }

    /**
     * Compress file.
     *
     * Note: Calling org.mozilla.javascript.tools.shell.Main.main
     * directly does not work well for large file sets. After only a
     * few files, Java runs out of memory. Therefore, we need to run
     * the Rhino program on individual files.
     *
     * @param file The File to compress.
     */
    public void compressFile(File file) throws IOException {
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

        // Create file to save output.
        File tmpFile = new File(file.getAbsolutePath() + ".tmp");

        // Write output to file.
        InputStream streamIn = p.getInputStream();
        FileOutputStream streamOut = new FileOutputStream(tmpFile);

        int c;
        while ((c = streamIn.read()) != -1) {
           streamOut.write(c);
        }

        streamIn.close();
        streamOut.close();

        // Rename file.
        tmpFile.renameTo(file);
    }

    /**
     * Helper method to iterate over files and directories.
     *
     * @param pathName Path to source.
     */
    private void iterate(String pathName)
            throws IOException {
        File file = new File(pathName);
        if (file.isDirectory()) {
            String[] fileNames = file.list();
            for (int i = 0; i < fileNames.length; i++) {
                String fileName = file.getAbsolutePath() + 
                    file.separator + fileNames[i];
                iterate(fileName);
            }
        } else {
            if (verbose) {
                System.out.println("Compressing: " + pathName);
            }
            compressFile(file);
        }
    }

    /**
     * Compress JavaScript files.
     *
     * @param args 
     */
    public static void main(String[] args) throws IOException {
        String pathName = null;
        String rhinoJar = null;
        boolean verbose = false;

        // Parse arguments.
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-verbose")) {
                verbose = true;
            } else if (i + 1 < args.length) {
                if (args[i].equals("-pathName")) {
                    pathName = args[++i];
                } else if (args[i].equals("-rhinoJar")) {
                    rhinoJar = args[++i];
                }
            }
	}

        if (pathName != null && rhinoJar != null) {        
            Compress comp = new Compress(rhinoJar, verbose);
            comp.iterate(pathName);
        } else {
            System.out.println("java -jar jarFile <args...>");
            System.out.println("\nwhere options include:");
            System.out.println("-rhinoJar\tJar file containing the Rhino compression tool.");
            System.out.println("-pathName\tJavaScript directory or file to compress.");
            System.out.println("-verbose\tEnable verbose output.");
        }
    }
}
