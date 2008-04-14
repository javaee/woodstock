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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.imageio.ImageIO;

/**
 * Create an Image map file by combining all the images and clip the requested image client side 
 * using theme api.
 * Read the image properties file,  create a big rectangle with max width (max (width of the images))
 * and the sum of all images heights and then  write the images  one by one to  the single file.
 * Image map will look like a image rectangle with all images vertically aligned.
 * As of now writing all types of images in "png" format seems to work fine. However there are some issues 
 * with "png" images and IE6 browser.
 * In order to clip the image client side we need to have top and left attribute too.
 * A new image file needs to be created with all the existing image properties + new image properties 
 * required for clipping the image.
 * 
 * @author as199886
 */
public class CombineImages extends ToolsBase {

    private String imagePropertyFile;
    private String imageFile;
    
    /**
     * Constructor.
     * 
     * @param sourceDir path prefix for images.
     * @param imagePropertyFile File path for images.properties file.
     * @param imageFile The path to combined image.    
     * @param outFile property file to write with the additional images properties.
     */
    public CombineImages(String sourceDir, String imagePropertyFile,
		String imageFile, String outFile) {
        super(sourceDir, false, null);
        setOutFile(outFile);
        this.imagePropertyFile = imagePropertyFile;
        this.imageFile = imageFile;
        
    }
    
    /**
     * Process all the images and creates a single image file.
     * Also creates an updated image properties file.          
     */
    public void process() {
	Properties imageProps = readImageProperties(imagePropertyFile);
	Map imagePaths = getImagePaths(imageProps);
	combineImages(imageProps, imagePaths);
	writeProperties(imageProps, getOutFile());
    }

    /**
     * Read image.properties file.
     *
     * @param imagePropsFile path to image.properties file.       
     */
    private Properties readImageProperties(String imageProperties) {
        
	File fl = null;
	FileInputStream fi = null;
	Properties imageProps = null;
        try {
	    fl = new File(imageProperties);
	    fi = new FileInputStream(fl);
            imageProps = new Properties();
            imageProps.load(fi);
        } catch(Exception e) {
            e.printStackTrace();
	    System.exit(-1);
        } finally {
	    if (fi != null) {
		try { fi.close(); } catch (Exception e) {}
	    }
	}
	return imageProps;
    }
    /**
     * Read image.properties file for images paths.
     *
     * @param properties image.properties file.       
     */
    private Map getImagePaths(Properties properties) {
	TreeMap imagePaths = new TreeMap();
        try {
            java.util.Enumeration keys = properties.keys();
            int i = 0;
            String key = null;
            String value = null;
            while (keys.hasMoreElements()) {
                key = (String)keys.nextElement();
                value = properties.getProperty(key);
		if (value != null && value.trim().length() == 0) {
		    System.out.println("CombineImages: WARNING: " +
			"key == " + key + " has no path value.");
		    continue;
		}
                if (key != null) {
                    if (!(key.endsWith("_HEIGHT") || key.endsWith("_WIDTH") 
                           || key.endsWith("_MAP") || key.endsWith("_ALT") 
                           || key.endsWith("_TOP") || key.endsWith("_TITLE"))) {
                       imagePaths.put(key, value);
		    }
		}
	    }
        } catch(Exception e) {
            e.printStackTrace();
	    System.exit(-1);
        }       
	return imagePaths;
    }
    /**
     * Reads the images path and combine all images in a single
     * image file. Also creates the updated image property file
     * with entries required to clip the images client-side from
     * the combined image. 
     *
     * @param properties image.properties file.
     * @param imagePaths        
     */
    private void combineImages(Properties imageProps, Map imagePaths) {

	BufferedImage images[] = new BufferedImage[imagePaths.size() + 1];
	int maxWidth = 0;
	int maxHeight = 0;
	int top[] = new int[imagePaths.size() + 1];
	
	int i = 0;
	Iterator iterator = imagePaths.entrySet().iterator();
	while(iterator.hasNext()) {  

	    Map.Entry entry = (Map.Entry)iterator.next(); 
	    String key = (String) entry.getKey(); 
	    String value = (String) entry.getValue();
	    if (value != null && value.trim().length() == 0) {
		// We already gave a warning.
		continue;
	    }
	     
	    File fl = null;
	    try {
		fl = new File(getSourceDir() + File.separator + value);
		images[i] = ImageIO.read(fl);
	    } catch(Exception e) { 
		System.out.println("CombineImages: FATAL ERROR: " +
		    "Image " + value + " does not exist.");
		e.printStackTrace();
		System.exit(-1);
	    }
	    int height = images[i].getHeight();
	    int width = images[i].getWidth();   
	    
	    maxHeight = maxHeight + height;
	    if (i > 0) {
		top[i] = top[i-1] + images[i-1].getHeight();
	    } else {
		top[0] = 0;
	    }
	    maxWidth = width > maxWidth ? width : maxWidth;
	    
	    imageProps.setProperty(key + "_MAP_HEIGHT", 
		    Integer.toString(height));
	    imageProps.setProperty(key + "_MAP_WIDTH",
		    Integer.toString(width));
	    imageProps.setProperty(key + "_MAP_TOP",
		    Integer.toString(-top[i]));
	    i++;
	}    
	
	// Create the bundled image.
	//
	BufferedImage cmbImage = new BufferedImage(maxWidth, maxHeight, 
	    BufferedImage.TYPE_INT_ARGB_PRE);
	Graphics2D createImage = cmbImage.createGraphics();
	
	for (int j = 0; j < i; j++) {            
	    createImage.drawImage(images[j], 0, top[j], null);
	}
	createImage.dispose();

	try {
	    File fl = new File(imageFile);
	    ImageIO.write(cmbImage, "png", fl);
        } catch(IOException e) {
            e.printStackTrace();
	    System.exit(-1);
        }
    }
    /**
     * Write properties file for images.
     *
     * @param imageProperties 
     * @param outFile for the updated image properties file. 
     */
    private void writeProperties(Properties imageProperties,
		File outFile) {

        //create updated images.properties file.
	FileOutputStream out = null;
        try {
	    //File fl = new File(combinedImageProperties);
	    out = new FileOutputStream(outFile.getCanonicalPath());
            imageProperties.store(out, null /*copyright*/);
        } catch(Exception e) {
            e.printStackTrace();
	    System.exit(-1);
        } finally {
	    if (out != null) {
		try {out.close();} catch (Exception e) {}
	    }		
        }
    } 
}
