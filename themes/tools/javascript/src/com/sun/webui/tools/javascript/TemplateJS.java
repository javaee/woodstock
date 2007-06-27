package com.sun.webui.tools.javascript;

import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

public class TemplateJS {
    private boolean verbose = false;
    private JSONObject json = new JSONObject();
    private static final int INDENT_FACTOR = 4;

    /**
     * Constructor.
     *
     * @param verbose Enable verbose output.
     */
    public TemplateJS(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Embed template directory or file.
     *
     * @param sourcePath Path to template directory or file.
     * @param destPath Path to JavaScript directory or file.
     * @param nameSpace JavaScript name space to hold JSON properties.
     */
    public void embed(String sourcePath, String destPath, String nameSpace)
            throws IOException {
        FileOutputStream streamOut = new FileOutputStream(destPath, true);

        try {
            iterate(sourcePath);

            StringBuffer buff = new StringBuffer();
            buff.append(nameSpace)
                .append("=")
                .append(json.toString(INDENT_FACTOR));
            
            streamOut.write(buff.toString().getBytes());
        } catch(JSONException e) {
            System.out.println("Error: Could not embed templates.");
        }
	streamOut.close();
    }

    /**
     * Embed template file.
     *
     * @param file The template file to embed.
     */
    private void embedTemplate(File file) throws IOException, JSONException {
        BufferedReader streamIn = new BufferedReader(new FileReader(file));
        StringBuffer buff = new StringBuffer();

        // Append input to buffer.
        String s;
        while ((s = streamIn.readLine()) != null) {
           buff.append(s);
        }

        // Remove whitespace.
        s = buff.toString().replaceAll("  *<", "<").replaceAll(">  *", ">");
        
        json.put(getPropertyName(file), s);
        streamIn.close();
    }

    /**
     * Iterate over template directory or file.
     *
     * @param sourcePath Path to template directory or file.
     */
    private void iterate(String sourcePath) throws IOException, JSONException {
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
                System.out.println("Embedding template: " + sourcePath);
            }
            embedTemplate(file);
        }
    }

    /**
     * Get property name.
     *
     * @param file The File to base the property name.
     */
    private String getPropertyName(File file) {
        String name = file.getName();
        return name.substring(0, name.lastIndexOf("."));
    }
}
