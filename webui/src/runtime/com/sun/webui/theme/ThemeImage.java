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
/* $Id: ThemeImage.java,v 1.2 2007-04-03 00:25:48 rratta Exp $ */

package com.sun.webui.theme;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <code>ThemeImage</code> encapsulates the properties of a theme image
 * resource. If a theme provides an image resource it is desirable that the
 * theme also be configured with the details of that image in addition
 * to the path location of the resource, such as the
 * height, width, the units that the dimensions are expressed in,
 * alt text if the browser cannot load the image, and a title
 * that acts as a tooltip in a browser environment.
 */
public class ThemeImage {

    /**
     * The suffix applied to the theme property key that defines an image
     * for the height of the image expressed in the units defined for the units
     * member.
     */
    public static final String HEIGHT_SUFFIX = "_HEIGHT";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the width of the image expressed in the units defined for the units
     * member.
     */
    public static final String WIDTH_SUFFIX = "_WIDTH";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the units to expressed by the width and height properties.
     */
    public static final String UNITS_SUFFIX = "_UNITS";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the alt text for the image if the resource cannot be loaded 
     * in the client.
     */
    public static final String ALT_SUFFIX = "_ALT";
    /**
     * The suffix applied to the theme property key that defines an image
     * for the title attribute for the image and used for a tooltip
     * in the client.
     */
    public static final String TITLE_SUFFIX = "_TITLE";

    private int width;
    private int height;
    private String units;
    private String alt;
    private String title;
    private String path;

    /**
     * Construct a <code>ThemeImage</code> from the passed arguments.
     */
    public ThemeImage(int width, int height, String units,
	String alt, String title, String path) {

	this.width = width;
	this.height = height;
	this.units = units;
	this.alt = alt;
	this.title = title;
	this.path = path;
    }

    /**
     * Returns the width of the image in units defined in the 
     * <code>units</code> member.
     * @see #getUnits()
     */
    public int getWidth() {
	return width;
    }
    /**
     * Returns the height of the image in units defined in the 
     * <code>units</code> member.
     * @see #getUnits()
     */
    public int getHeight() {
	return height;
    }
    /**
     * Returns the units expressed in the <code>height</code>
     * and <code>width</code>  members.
     * @see #getHeight()
     * @see #setHeight(int)
     * @see #getWidth()
     * @see #setWidth(int)
     */
    public String getUnits() {
	return units;
    }
    /**
     * Returns the <code>alt</code> member or alternate text to display if the
     * client cannot render the image.
     */
    public String getAlt() {
	return alt;
    }
    /**
     * Returns the <code>title</code> member sometimes used to display a
     * tooltip on the client.
     */
    public String getTitle() {
	return title;
    }
    /**
     * Returns the application or jar relative path of this image resource.
     * The path is relative to location that can be found on the application
     * class path.
     */
    public String getPath() {
	return path;
    }
    /**
     * Set the width of the image in units defined by the <code>units</code>
     * member.
     */
    public void setWidth(int width) {
	this.width = width;
    }
    /**
     * Set the height of the image in units defined by the <code>units</code>
     * member.
     */
    public void setHeight(int height) {
	this.height = height;
    }
    /**
     * Set the units used by the <code>height</code> and <code>width</code>
     * members.
     */
    public void setUnits(String units) {
	this.units = units;
    }
    /**
     * Set the text that will appear on the client if the image cannot be
     * loaded.
     */
    public void setAlt(String alt) {
	this.alt = alt;
    }
    /**
     * Set the text that may appear as a tooltip on the client.
     */
    public void setTitle(String title) {
	this.title = title;
    }
    /**
     * Set the application or jar relative path of the image. 
     * The path is relative to location that can be found on the application
     * class path. This path must begin with a "/".
     */
    public void setPath(String path) {
	this.path = path;
    }
}
