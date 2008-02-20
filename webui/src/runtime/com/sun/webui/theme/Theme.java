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
/*
 * $Id: Theme.java,v 1.3 2008-02-20 16:29:23 rratta Exp $
 */

package com.sun.webui.theme;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <p>
 * The <code>Theme</code> interface defines a set of methods that 
 * return resources that define a theme; for example a set of name value
 * pairs defined in properties files representing strings and data,
 * such as messages, image, CSS style sheet and JavaScript file paths.
 * These resources are collectively known as a "Theme" and are typically
 * bundled together in a Jar file. Themes based on the same set of name 
 * value pairs should be swappable allowing an application to switch 
 * from one Theme to another.
 * </p> 
 * <p>
 * Themes may consist of resources that are used directly 
 * by the Java classes at runtime (for example <code>ResourceBundles</code>
 * and <code>Properties</code> files)
 * and resources that are requested by the application users' browser 
 * (for example image files). In order to make Theme resources available
 * over HTTP, you can configure the {@link com.sun.webui.theme.ThemeServlet}
 * in the Web UI web application on the server.
 * </p>
 * <p>
 * For more information on how to configure a Web UI web application
 * w.r.t. Themes, see the documentation for see
 * {@link com.sun.webui.theme.ThemeServlet}.
 * </p> 
 * <p>
 * A <code>Theme</code> implementation instance is used to obtain
 * the resources defined by a concrete Theme, like the "suntheme".
 * A <code>Theme</code> instance is obtained from a <code>ThemeFactory</code>
 * implementation. A web application can define the the
 * <code>{@link com.sun.webui.theme.ThemeFactory}</code> class that should 
 * be used by Web UI components to obtain a <code>Theme</code> instance.
 * </p>
 * <p>
 * Typically implementations will provide support for <code>Locale</code>
 * variants for all resources that it provides, as appropriate.
 * </p>
 */

public interface Theme  {

    /**
     * Attribute name used to store the user's theme name in the Session
     * @deprecated
     */
    public static final String THEME_ATTR = "com.sun.webui.jsf.Theme";

    /*
     * The context parameter name used to specify a console path,
     * if one is used.
    public static final String RESOURCE_PATH_ATTR = 
	"com.sun.web.console.resource_path";
     */

    /**
     * Returns a String array of URIs to the JavaScript files to be
     * included in every application page.
     * <p>
     * <em>Note that an implementation may return a value that is not
     * the literal value defined in a theme resource bundle. For
     * example an implementation may return the literal value prepended
     * with a web application context.</em>
     * </p>
     * @return String array of URIs to JavaScript files or <code>null</code>.
     */
    public String[] getGlobalJSFiles();

    /**
     * Returns a String URI to a JavaScript file defined by <code>key</code>.
     * <p>
     * <em>Note that an implementation may return a value that is not
     * the literal value defined in a theme resource bundle. For
     * example an implementation may return the literal value prepended
     * with a web application context.</em>
     * </p>
     * @param key Defines a JavaScript file path.
     * @return a String URI to a JavaScript file or <code>null</code>.
     */
    public String getPathToJSFile(String key);
 
    /**
     * Returns the literal String value of the <code>key</code> JavaScript
     * theme property.
     *
     * @param key Defines a JavaScript theme property.
     * @return the value of <code>key</code> or <code>null</code>.
     */
    public String getJSString(String key);
    
    /**
     * Returns a String array of URIs to the CSS style sheet files to 
     * be included in every application page.
     * <p>
     * The implicatation of a <code>Master</code> style sheet is that
     * these style sheets should be loaded first, before any other
     * style sheets.
     * </p>
     * <p>
     * <em>Note that an implementation may return a value that is not
     * the literal value defined in a theme resource bundle. For
     * example an implementation may return the literal value prepended
     * with a web application context.</em>
     * </p>
     * @return String array of URIs to master style sheets
     */
    public String[] getMasterStylesheets();

    /**
     * Returns a String array of URIs to the CSS style sheet files to be
     * included in every application page.
     * <p>
     * <em>Note that an implementation may return a value that is not
     * the literal value defined in a theme resource bundle. For
     * example an implementation may return the literal value prepended
     * with a web application context.</em>
     * </p>
     * @return String array of URIs to the style sheets
     */
    public String[] getGlobalStylesheets();

    /**
     * Returns a String array of URIs to the CSS style sheet files to be
     * included in every application page, defined by <code>key</code>.
     * <p>
     * Note that these style sheets should be loaded after the 
     * <code>Master</code> and <code>Global</code> style sheets.
     * </p>
     * <p>
     * <em>Note that an implementation may return a value that is not
     * the literal value defined in a theme resource bundle. For
     * example an implementation may return the literal value prepended
     * with a web application context.</em>
     * </p>
     * @param key Defines a style sheet file path.
     * @return String array of URIs to the style sheets or <code>null</code>.
     */
    public String[] getStylesheets(String key);

     /**
     * Returns a String URI that represents a path to the HTML template
     * defined by <code>key</code>.
     * <em>Note that an implementation may return a value that is not
     * the literal value defined in a theme resource bundle. For
     * example an implementation may return the literal value prepended
     * with a web application context.</em>
     * </p>
     * @param key Defines an HTML template file path.
     * @return  A String URI defined by <code>key</code>.
     */
    public String getPathToTemplate(String key);

    /**
     * Returns a CSS selector defined by <code>key</code>, 
     * unless <code>key</code> does not exist, in which case <code>key</code>
     * is returned.
     * 
     * @param key Defines a CSS selector.
     * @return the name of a CSS selector or <code>key</code>.
     */  
    public String getStyleClass(String key);

    /**
     * Returns a literal message value defined by <code>key</code>.
     * @param key Defines a theme message or string.
     * @return A message string
     */
    public String getMessage(String key);

    /**
     * Return a message that has been formatted using
     * <code>MessageFormat</code> to substitute <code>params</code>
     * for placeholders in the literal value of <code>key</code>.
     * @param key Defines a theme message or string.
     * @param params Substitution parameters suitable for use by a
     * <code>MessageFormat.format</code> call.
     * @return A formatted message string
     */
    public String getMessage(String key, Object[] params);

    /**
     * Return a <code>boolean</code> value for <code>key</code>.
     * If <code>key</code> is not defined, return <code>defaultValue</code>.
     * If <code>defaultValue</code> is <code>null</code> and <code>key</code>
     * is not defined <code>RuntimeException</code> is thrown.
     * @param key Defines a boolean value.
     * @param defaultValue The value to return if <code>key</code> is not
     * defined.
     * @return A boolean value for <code>key</code>
     */
    public boolean getMessageBoolean(String key, boolean defaultValue);

    /**
     * Return an <code>int</code> value for <code>key</code>.
     * If <code>key</code> is not defined, return <code>defaultValue</code>.
     * @param key Defines an <code>int</code> value.
     * @param defaultValue The value to return if <code>key</code> is not
     * defined.
     * @return A <code>int</code> value for <code>key</code>
     */
    public int getMessageInt(String key, int defaultValue);

    /**
     * Return a String URI that can be used to access the physical
     * image resource.
     * <p>
     * For example an implementation used by a web application
     * might prepend a servlet context path suitable for delivery by
     * <code>{@link com.sun.webui.theme.ThemeServlet}</code>
     *
     * @param key Defines an image path
     * @return a path that can be used to access the physical resource.
     * @throws RuntimeException if <code>key</code> cannot be found.
     */
    public String getImagePath(String key);

    /**
     * Return a <code>{@link com.sun.webui.theme.ThemeImage}</code>
     * instance for an image identified by <code>key</code>.
     * @param key Defines an image resource.
     * @return an instance of <code>ThemeImage</code> or <code>null if
     * <code>key</code> cannot be found.
     */
    public ThemeImage getImage(String key);

    /**
     * Returns the literal String value of the <code>key</code> image
     * theme property.
     *
     * @param key Defines an image resource.
     * @return The literal value of <code>key</code>.
     * @throws RuntimeException if <code>key</code> cannot be found.
     */
    public String getImageString(String key);
}
