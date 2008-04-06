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
package com.sun.webui.theme;

import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Properties;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.logging.Level;


/**
 * The <code>ResourceBundleTheme</code> class is an implementation of 
 * <code>{@link com.sun.webui.theme.Theme}</code> and implemented as
 * a singleton instance that obtains theme resources from 
 * <code>ResourceBundle</code>'s typically provided in the form of 
 * <code>Properties</code> files.
 * <p>
 * The <code>getInstance</code> method is used to obtain the 
 * <code>Theme</code> instance. The <code>
 * {@link com.sun.webui.theme.ThemeResources}</code> argument identifies the
 * <code>ResourceBundle</code>'s from which to retrieve the property
 * values.
 * <p>
 * Currently there are essentially two types of theme properties that
 * are defined in a theme <code>ResourceBundle</code>.
 * </p>
 * <p>
 * <ul>
 * <li>properties that define literal or data values
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeProperty}.</li>
 * <li>properties whose values are basenames that define other theme
 * <code>ResourceBundle</code>'s
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle}.</li>
 * </ul>
 * </p>
 * <p>
 * A theme is defined by one or more <code>ResourceBundle</code>'s. They
 * can be a compiled class that extends <code>PropertyResourceBundle</code> or a
 * <code>Properties</code> file. Theme resource bundles can take one
 * of the following forms
 * </p>
 * <p>
 * <ul>
 * <li>A complete theme. An example of a complete theme is the "suntheme".
 * This includes all properties and resources that apply to all
 * Web UI components.</li>
 * <li>A sparse theme. An example of a sparse theme would be
 * <code>ResourceBundle</code>'s that define
 * properties and provide resources for one or 
 * more components that are not part of the original Web UI component
 * set, but conform to the look of a known theme, like "suntheme".<br/>
 * Another example of a sparse theme is a theme <code>ResourceBundle</code>
 * provided by an application to override a subset of theme properties and
 * resources for a specific theme or any theme for a subset 
 * theme properties, i.e. provide an alternate "skin".</li>
 * </ul>
 * </p>
 * <p>
 * An application can define a theme or a sparse theme by providing
 * one or more <code>ResourceBundle</code>'s or <code>Properties</code>
 * files in the application's classpath. There are constraints on 
 * where the bundles can be placed and named.<br/>
 * In a servlet environement the servlet 
 * container must present an application's resources under
 * <code>WEB-INF/classes</code> first on the classpath. This allows
 * those resources to be searched first in preference to theme 
 * resources that appear in jars, like the "suntheme" jar. This allows an
 * application to override theme resources defined in jars.
 * The result is that an application that is providing theme resources
 * must not place those resources in a jar in <code>WEB-INF/lib</code>
 * otherwise there is no way to guarantee that those definitions and
 * resources will take precedence over theme resources in other jars.<br/>
 * The basenames that identify <code>ResourceBundles</code> or
 * <code>Properties</code> files or paths to resources must not conflict
 * with basenames that might appear in a jar that contains theme resources,
 * otherwise the resources defined in the conflicting jar may never be
 * referenced since position on the classpath determines how a resource
 * is found. Therefore, since application resources appear first on the 
 * classpath, identical basenames in a jar will never be referenced and
 * unexpected behavior may result.<br/>
 * </p>
 * <p>
 * An ordered search is conducted whenever a theme property is requested.
 * Some methods return the values of all occurences of a given property
 * or only the first occurence. A search is first performed for the theme
 * property itself in a <code>ResourceBundle</code>. If the explicit property
 * is not found, and the key is associated with a bundle property
 * then a search is made for the property defining the bundle. If
 * the bundle property is found, then the <code>ResourceBundle</code>'s
 * identified by the bundle property are searched for the original property.
 * <p>
 * For example, assume that an application wants to obtain the value of
 * the <code>BUTTON1</code> property. This property defines a CSS
 * selector. For each <code>ResourceBundle</code> available, search
 * for the property <code>BUTTON1</code>. If the property is not found
 * in a given <code>ResourceBundle</code> then a search for the
 * <code>ThemeProperty.STYLESHEET_CLASSMAPPER</code> property is made. This
 * property defines one or more <code>ResourceBundle</code> basenames
 * that contains properties that define CSS selectors. If this property
 * is defined in the given <code>ResourceBundle</code> currently being searched
 * then each of the <code>ResourceBundle</code>'s identified is searched
 * for <code>BUTTON1</code>. Note that the search is a depth first search
 * but the depth is only one level. If niether the <code>BUTTON1</code> or 
 * <code>ThemeProperty.STYLESHEET_CLASSMAPPER</code> property is defined
 * the next available <code>ResourceBundle</code> is searched in a
 * similar manner. In this case the first occurrence of the
 * <code>BUTTON1</code> property is returned. Note that <code>BUTTON1</code>
 * and <code>ThemeProperty.STYLESHEET_CLASSMAPPER</code> are the logical
 * names of these properties. See
 * <code>com.sun.webui.jsf.theme.ThemeStyles.BUTTON1</code> for the literal 
 * value and <code>{@link com.sun.webui.theme.ThemeResourceBundle.ThemeProperty#STYLESHEET_CLASSMAPPER}</code> 
 * for its literal value.
 * </p>
 * <p>
 * Property values that are paths to resources such as images, style sheets
 * and JavaScript files are returned such that they include a prefix
 * as part of the path as defined by the 
 * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>
 * method, unless otherwise noted.
 * </p>
 * <p>
 * Because <code>ResourceBundle</code>'s are used to maintain theme properties
 * the standard Java locale variant mechanism can be used to provide
 * locale variants for all theme resources.
 * </p>
 */
public class ResourceBundleTheme implements Theme {

    /**
     * The singleton <code>Theme</code> instance.
     */
    protected static ResourceBundleTheme theme = new ResourceBundleTheme();
    /**
     * A singleton instance of <code>ThemeResourceBundle</code> which
     * encapsulates the access to properties in a theme
     * <code>ResourceBundle</code>.
     */
    protected static ThemeResourceBundle themeResourceBundle =
	new ThemeResourceBundle();

    /**
     * The instance of <code>ThemeResources</code> relative to the
     * current thread referencing the <code>Theme</code> singleton.
     */
    static ThreadLocal threadThemeResources = new ThreadLocal();

    private ThemeResources getThemeResources() {
	return (ThemeResources)threadThemeResources.get();
    }

    /**
     * Protected since it is a singleton instance.
     */
    protected ResourceBundleTheme() {
	super();
    }

    /**
     * Return the <code>Theme</code> singleton instance.
     * The <code>themeResources</code> argument is assigned to the
     * <code>ThreadLocal</code> variable <code>threadThemeResources</code>.
     */
    public static Theme getInstance(ThemeResources themeResources) {
	theme.threadThemeResources.set(themeResources);
	return theme;
    }

    // Was defined in MANIFEST.MF
    // X-SJWUIC-Theme-Messages
    // com.sun.webui.jsf.@THEME_NAME@.messages.messages
    //
    // Now defined in properties file as
    // ThemeResourceBundle.ThemeBundle.MESSAGES and
    // ThemeResourceBunlde.ThemeProperty.MESSAGE_BUNDLE or
    // Theme.bundle.messages
    //
    /**
     * Returns a literal message value defined by <code>key</code>
     * from the <code>ThemeResourceBundle.ThemeBundle.MESSAGES</code>
     * <code>ResourceBundle</code>.
     * If the <code>key</code> is not defined, <code>null</code>
     * is returned.
     * @param key Defines a theme message or string.
     * @return A message string or null
     */
    public String getMessage(String key) {

	String property = key;
	try {
	    property = (String)getProperty(key,
		ThemeResourceBundle.ThemeBundle.MESSAGES);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	    return null;
	}
	return property;
    }

    /**
     * Return a message that has been formatted using
     * <code>MessageFormat</code> to substitute <code>params</code>
     * for placeholders in the literal value of <code>key</code>,
     * from the <code>ThemeResourceBundle.ThemeBundle.MESSAGES</code>
     * <code>ResourceBundle</code>.
     * If the <code>key</code> is not defined <code>key</code>
     * is returned. If the message cannot be formatted 
     * the unformatted message string is returned.
     * <p>
     * <code>{@link java.text.MessageFormat}</code> is used to perform parameter
     * substitution of <code>params</code> in the value of <code>key</code>.
     * </p>
     * @param key Defines a theme message or string.
     * @param params Substitution parameters suitable for use by a
     * <code>MessageFormat.format</code> call.
     * @return A formatted message string or <code>key</code>
     */
    public String getMessage(String key, Object[] params) {
	String property = key;
	try {
	    property = (String)getProperty(key,
		ThemeResourceBundle.ThemeBundle.MESSAGES);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	    return key;
	}
	ThemeResources themeResources = getThemeResources();
	Locale locale = themeResources.getLocale() == null ?
		themeResources.getThemeContext().getDefaultLocale() :
		    themeResources.getLocale();
	try {
	    MessageFormat mf = new MessageFormat(property, locale);
	    return mf.format(params);
	} catch (Exception e) {
	    ThemeLogger.log(Level.FINEST, "Could not format message {0}.",
		new Object[] { property }, e);
	}
	return property;
    }

    /**
     * Return a <code>boolean</code> value for <code>key</code>.
     * If <code>key</code> is not defined, return <code>defaultValue</code>.
     * <p>
     * This method will coerce the <code>String</code> property value
     * to <code>boolean</code>, using <code>Boolean.valueOf(String)</code>.
     * </p>
     * @param key Defines a <code>boolean</code> value.
     * @param defaultValue The value to return if <code>key</code> is not
     * defined.
     * @return A <code>boolean</code> value for <code>key</code>
     */
    public boolean getMessageBoolean(String key, boolean defaultValue) {
	String property = key;
	try {
	    property = (String)getProperty(key,
		ThemeResourceBundle.ThemeBundle.MESSAGES);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	    return defaultValue;
	}
	return Boolean.valueOf(property).booleanValue();
    }

    /**
     * Return an <code>int</code> value for <code>key</code>.
     * If <code>key</code> is not defined or the value of key 
     * cannot be coerced to an <code>int</code>, 
     * return <code>defaultValue</code>.
     * <p>
     * This method will coerce the <code>String</code> property value
     * to <code>int</code>, using <code>Integer.parseInt(String)</code>.
     * </p>
     * @param key Defines an <code>int</code> value.
     * @param defaultValue The value to return if <code>key</code> is not
     * defined or cannot be converted to <code>int</code>.
     * @return An <code>int</code> value for <code>key</code>
     */
    public int getMessageInt(String key, int defaultValue) {

	String property = key;
	try {
	    property = (String)getProperty(key,
		ThemeResourceBundle.ThemeBundle.MESSAGES);
	    if (property == null || property.trim().length() == 0) {
		return defaultValue;
	    }
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	    return defaultValue;
	}
	try {
	    int ivalue = Integer.parseInt(property);
	    return ivalue;
	} catch (Exception mre) {
	    ThemeLogger.log(Level.FINEST, "NOT_INT_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	    return defaultValue;
	}
    }

    // Was defined in the MANIFEST.MF as
    // X-SJWUIC-Theme-JavaScript
    // com.sun.webui.jsf.@THEME_NAME@.properties.javascript
    //
    // Now defined in properties file as
    // ThemeResourceBundle.ThemeBundle.JAVASCRIPT as
    // "Theme.javascript" and
    // ThemeResourceBundle.ThemeProperty.JAVASCRIPT_BUNDLE as
    // "Theme.bundle.javascript".
    //
    /**
     * Returns a String array of URIs to the JavaScript files to be
     * included in every application page; the value(s) of all definitions of
     * <code>ThemeResourceBundle.ThemeProperty.JAVASCIPT</code> 
     * defined in <code>ThemeResourceBundle.ThemeBundle.JAVASCRIPT</code>.
     * If no global javascript files are defined <code>null</code> is returned.
     * <p>
     * The returned URIs include a prefix as part of the path as defined by 
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * </p>
     */
    public String[] getGlobalJSFiles() {
	ThemeResources themeResources = getThemeResources();
	try {
	    String[] jsfiles = (String[])getGlobalArrayProperty(
		themeResources.getThemeReferenceIterator(true),
		ThemeResourceBundle.ThemeBundle.JAVASCRIPT,
		ThemeResourceBundle.ThemeProperty.JAVASCRIPT,
		themeResources.getLocale(),
		themeResources.getThemeContext().getDefaultClassLoader());
	    return jsfiles == null ? null : getPathArray(jsfiles);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { 
		    ThemeResourceBundle.ThemeProperty.JAVASCRIPT.getKey(), 
		    themeResources.getName() }, mre);
	    return null;
	}
    }

    // Was defined in the MANIFEST.MF as
    // X-SJWUIC-Theme-Stylesheets
    // com.sun.webui.jsf.@THEME_NAME@.properties.stylesheets
    // 
    // The keys "global" and "master" were recognized.
    //

    // The distinction between Global, Master and StyleSheet is that
    // Master style sheets are intended to be loaded first.
    // Actually this could be one method that 
    // "getGlobalStylesheets" that actually returns "master" sheets
    // listed first and ThemeProperty.STYLESHEET listed after.
    // The client style sheet by convention, would appear next
    // but loaded by the "infrastructure".
    //

    /**
     * Returns a String array of URIs to the CSS style sheet files to be
     * included in every application page.
     * The values of all definitions of
     * <code>ThemeResourceBundle.ThemeProperty.STYLESHEET</code> 
     * defined in <code>ThemeResourceBundle.ThemeBundle.STYLESHEET</code>
     * bundles are retuned.<br/>
     * This method should be called only after <code>getMasterStylesheets</code>
     * or if called out of order, the values returned from 
     * <code>getMasterStylesheets</code> should be loaded first.<br/>
     * If no global style sheets are defined <code>null</code> is returned.
     * <p>
     * The returned URIs include a prefix as part of the path as defined by 
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * </p>
     */
    public String[] getGlobalStylesheets() {
	ThemeResources themeResources = getThemeResources();
	try {
	    String[] cssfiles = (String[])getGlobalArrayProperty(
		themeResources.getThemeReferenceIterator(true),
		ThemeResourceBundle.ThemeBundle.STYLESHEET,
		ThemeResourceBundle.ThemeProperty.STYLESHEET,
		themeResources.getLocale(),
		themeResources.getThemeContext().getDefaultClassLoader());
	    return cssfiles == null ? null : getPathArray(cssfiles);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { 
		    ThemeResourceBundle.ThemeProperty.STYLESHEET.getKey(), 
		    themeResources.getName() }, mre);
	    return null;
	}
    }

    /**
     * Returns a String array of URIs to the CSS style sheet files to
     * be included in every application page.
     * The values of all definitions of
     * <code>ThemeResourceBundle.ThemeProperty.STYLESHEET_MASTER</code> 
     * defined in <code>ThemeResourceBundle.ThemeBundle.STYLESHEET</code>
     * bundles are retuned.<br/>
     * This method should be called before <code>getGlobalStylesheets</code>
     * or if called out of order, the values returned from 
     * <code>getMasterStylesheets</code> should be loaded first.<br/>
     * If no master style sheets are defined <code>null</code> is returned.
     * <p>
     * The returned URIs include a prefix as part of the path as defined by 
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * </p>
     */
    public String[] getMasterStylesheets() {
	try {
	    ThemeResources themeResources = getThemeResources();
	    String[] cssfiles = (String[])getGlobalArrayProperty(
		themeResources.getThemeReferenceIterator(true),
		ThemeResourceBundle.ThemeBundle.STYLESHEET,
		ThemeResourceBundle.ThemeProperty.STYLESHEET_MASTER,
		themeResources.getLocale(),
		themeResources.getThemeContext().getDefaultClassLoader());
	    return cssfiles == null ? null : getPathArray(cssfiles);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY", new Object[] { 
		ThemeResourceBundle.ThemeProperty.STYLESHEET_MASTER.getKey(),
		    getThemeResources().getName() }, mre);
	    return null;
	}
    }

    /**
     * Returns a String array of URIs to the CSS style sheet files to be
     * included in every application page, defined by <code>key</code>.
     * The values of all definitions of <code>key</code>
     * defined in <code>ThemeResourceBundle.ThemeBundle.STYLESHEET</code>
     * bundles are retuned.<br/>
     * Style sheets returned by this method should be loaded after
     * style sheets returned by <code>getGlobalStylesheets</code>.<br/>
     * If <code>key</code> is not defined then <code>null</code> is returned.
     * <p>
     * The returned URIs include a prefix as part of the path as defined by 
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * </p>
     * @param key Defines a style sheet file path.
     * @return String array of URIs to the style sheets or <code>null</code>.
     */
    public String[] getStylesheets(String key) {
	try {
	    ThemeResources themeResources = getThemeResources();
	    String[] cssFiles = (String[])getGlobalArrayProperty(
		themeResources.getThemeReferenceIterator(true),
		ThemeResourceBundle.ThemeBundle.STYLESHEET,
		key, themeResources.getLocale(),
		themeResources.getThemeContext().getDefaultClassLoader());
	    return cssFiles == null ? null : getPathArray(cssFiles);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	    return null;
	}
    }

    // This is typically used for the individual component javascript
    // files but do we want all occurences in case of "overloading" ?
    // Right now the assumption is that there is only one.
    // This can become a "getGlobalArrayProperty".
    //
    /**
     * Returns a String URI to a JavaScript file defined by <code>key</code>,
     * from the <code>ThemeResourceBundle.ThemeBundle.JAVASCRIPT></code>
     * resource bundle.<br/>
     * If <code>key</code> is not defined <code>null</code> is returned.
     * <p>
     * The returned URIs include a prefix as part of the path as defined by 
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * </p>
     * @param key Defines a JavaScript file path.
     * @return a String URI to a JavaScript file or <code>null</code>.
     */
    public String getPathToJSFile(String key) {
	String jsfile = null;
	try {
	    jsfile = (String)getProperty(key, 
		ThemeResourceBundle.ThemeBundle.JAVASCRIPT);
	    jsfile = fixUpPath(jsfile); 
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	}
	return jsfile;
    }

    /**
     * Returns the literal String value of the <code>key</code> JavaScript
     * theme property, from the 
     * <code>ThemeResourceBundle.ThemeBundle.JAVASCRIPT></code>
     * resource bundle.
     * @param key The key used to retrieve the message
     * @return the value of <code>key</code> or <code>null</code>.
     */
    public String getJSString(String key) {
	String jsprop = null;
	try {
	    jsprop = (String)getProperty(key, 
		ThemeResourceBundle.ThemeBundle.JAVASCRIPT);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	}
        return jsprop;
    }

    /**
     * Returns a String URI that represents a path to the HTML template
     * defined by <code>key</code> from the
     * <code>ThemeResourceBundle.ThemeBundle.TEMPLATE</code> resource
     * bundle.
     * @return  A String that represents a valid path to the HTML template
     * corresponding to the key
     * @param key Defines an HTML template file path.
     * @return  A String URI defined by <code>key</code> or <code>null</code>.
     */
    public String getPathToTemplate(String key) {
        
        try { 
	    String templatefile = (String)getProperty(key, 
		ThemeResourceBundle.ThemeBundle.TEMPLATE);
	    return fixUpPath(templatefile); 
        } catch(MissingResourceException mre) { 
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
        }
	return null;
    }

    /**
     * Returns a CSS selector defined by <code>key</code>, else 
     * <code>key</code>, from the
     * <code>ThemeResourceBundle.ThemeBundle.CLASSMAPPER></code>
     * resource bundle. The value is suitable for the HTML <code>class</code>
     * attribute.<br/>
     * @param key Defines a CSS selector.
     * @return the a CSS selector or <code>key</code>.
     */
    public String getStyleClass(String key) {
	String styleClass = key;
	try {
	    styleClass = (String)getProperty(key,
		ThemeResourceBundle.ThemeBundle.CLASSMAPPER);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST, "NO_SUCH_PROPERTY",
		new Object[] { key, getThemeResources().getName() }, mre);
	}
	return styleClass;
    }

    private static final String[] suffixes = {
	ThemeImage.ALT_SUFFIX,
	ThemeImage.TITLE_SUFFIX,
	ThemeImage.HEIGHT_SUFFIX,
	ThemeImage.WIDTH_SUFFIX,
	ThemeImage.UNITS_SUFFIX
    };
    private static int KEY_PROP = 0;
    private static int ALT_PROP = 1;
    private static int TITLE_PROP = 2;
    private static int HEIGHT_PROP = 3;
    private static int WIDTH_PROP = 4;
    private static int UNITS_PROP = 5;

    /**
     * Return a <code>{@link com.sun.webui.theme.ThemeImage}</code>
     * instance for an image defined by <code>key</code>, from the
     * <code>ThemeResourceBundle.ThemeBundle.IMAGES></code>
     * resource bundle.
     * <p>
     * The <code>key</code> property defines the path of the image resource.
     * The bundle should define additional properties
     * where each property is defined as <code>key</code> with the 
     * following suffixes: (i.e. key == "SMALL_ALERT",
     * "SMALL_ALERT".concat(ThemeImage.ALT_SUFFIX)).
     * </p>
     * <p>
     * <ul>
     * <li>com.sun.webui.theme.ThemeImage.ALT_SUFFIX</li>
     * <li>com.sun.webui.theme.ThemeImage.TITLE_SUFFIX</li>
     * <li>com.sun.webui.theme.ThemeImage.HEIGHT_SUFFIX</li>
     * <li>com.sun.webui.theme.ThemeImage.WIDTH_SUFFIX</li>
     * <li>com.sun.webui.theme.ThemeImage.UNITS_SUFFIX</li>
     * </ul>
     * </p>
     * <p>
     * The returned image path includes a prefix as part of the path
     * as defined by 
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * </p>
     * @param key Defines an image resource.
     * @return an instance of <code>ThemeImage</code> or <code>null</code>.
     */
    public ThemeImage getImage(String key) {
       
	StringBuilder sb = new StringBuilder(16);
	String[] keys = new String[suffixes.length + 1];
	int len = key.length();
	// No suffix for the image path, which is defined by "key".
	//
	int i = 0;
	keys[i++] = key;
	sb.append(key);
	for (String suffix : suffixes) {
	    sb.append(suffix);
	    keys[i++] = sb.toString();
	    sb.setLength(len);
	}
	Object[] properties = null;
	try {
	    properties = getProperties(keys,
		ThemeResourceBundle.ThemeBundle.IMAGES);
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.FINEST,
		"No such image {0}.", new Object[] { key }, mre);
	    return null;
	}
	// Path is the first entry since "key" without the suffix
	// was the zeroth entry in "keys".
	// If there is no path, we're done, although we might consider
	// letting the caller deal with that problem.
	//
	// Not sure how properties can be null, without throwing
	// exception.
	//
	if (properties == null || properties[0] == null || 
		((String)properties[KEY_PROP]).trim().length() == 0) {
	    ThemeLogger.log(Level.FINEST, 
		"No path found for image {0}.", new Object[] { key }, null);
	    return null;
	}
	int ht = verifyImageDimension(key, (String)properties[WIDTH_PROP]);
	int wt = verifyImageDimension(key, (String)properties[HEIGHT_PROP]);
	String units = properties[UNITS_PROP] == null || 
	    ((String)properties[UNITS_PROP]).trim().length() == 0 ? 
		null : (String)properties[UNITS_PROP];

	// The value of (String)properties[ALT_PROP] and
	// (String)properties[TITLE_PROP] may be theme keys.
	// If they are not null, call "getMessage". If getMessage
	// returns null, set the values to (String)properties[ALT_PROP]
	// and (String)properties[TITLE_PROP].
	//
	String alt = (String)properties[ALT_PROP];
	if (alt != null) {
	    alt = getMessage(alt);
	}
	String title = (String)properties[TITLE_PROP];
	if (title != null) {
	    title = getMessage(title);
	}
	    
	return new ThemeImage(ht, wt, units,
	    alt == null ? (String)properties[ALT_PROP] : alt,
	    title == null ? (String)properties[TITLE_PROP] : title,
	    fixUpPath((String)properties[KEY_PROP]));
    }

    /**
     * Return a String URI that can be used to access the physical
     * image resource.
     * <p>
     * The returned path includes a prefix as part of the path as defined by 
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * </p>
     * @param key Defines an image path
     * @return a path that can be used to access the physical resource.
     * @throws RuntimeException if <code>key</code> cannot be found.
     */
    public String getImagePath(String key) {
	String path = null;
	try {
	    path = getImageString(key);
	    if (path == null || path.trim().length() == 0) {
		return null;
	    }
            path = fixUpPath(path);        
	} catch (MissingResourceException mre) {
	    ThemeLogger.log(Level.SEVERE, 
		"No such image {0}.", new Object[] { key }, mre);
	    throw new RuntimeException(mre.getLocalizedMessage(), mre);
	    /*
	    return null;
	    */
	}
	return path;
    }

    /**
     * Return <code>dim</code> as <code>int</code>.
     * If <code>dim</code> cannot be converted, return 
     * <code>Integer.MIN_VALUE</code>
     */
    private int verifyImageDimension(String imageKey, String dim) {
	int result = Integer.MIN_VALUE;
	if (dim == null || dim.trim().length() == 0) {
	    return result;
	}
	try {
	    result = Integer.parseInt(dim);
	} catch(Exception e) {
	    ThemeLogger.log(Level.FINEST, 
		"Could not convert dimension {0} for image {1}.",
		new Object[] { dim, imageKey }, null);
	}
	return result;
    }

    // It's not clear that this should throw exception vs. null.
    // I believe it was implemented this way because of code that
    // it replaced, was expecting exceptions.
    /**
     * Returns the literal String value of the <code>key</code> image
     * theme property.
     *
     * @param key The key used to retrieve the message
     * @return The literal value of <code>key</code>.
     * @throws RuntimeException if <code>key</code> cannot be found.
     */
    public String getImageString(String key) {

	ThemeResources themeResources = getThemeResources();
	ClassLoader classLoader = 
	    themeResources.getThemeContext().getDefaultClassLoader();
	Locale locale = themeResources.getLocale();
	if (locale == null) {
	    locale = themeResources.getThemeContext().getDefaultLocale();
	}
	String string = (String)getProperty(
		themeResources.getThemeReferenceIterator(),
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key, locale, classLoader);

	return string;
    }

    /**
     * For each element in <code>files</code> call <code>fixUpPath</code>
     * and return an array of the transformed paths. The order of
     * <code>files</code> is maintained in the returned array.
     */
    protected String[] getPathArray(String[] files) {

	String[] paths = new String[files.length];

	for (int i = 0; i < files.length; ++i) {
	    paths[i] = fixUpPath(files[i]);
	}
	return paths;
    }

    /**
     * Returns <code>uri</code> transformed by calling
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>.
     * @param uri a relative path to a theme file resource.
     * @return transformed <code>uri</code> else <code>uri</code> or
     * <code>null</code> if <code>uri</code> is <code>null</code> or the
     * empty string.
     */
    protected String fixUpPath(String uri) {

	if (uri == null || uri.length() == 0) {
	    return null;
	}
	String path = uri;
	ThemeContext themeContext = getThemeResources().getThemeContext();
	try {
	    path = themeContext.getResourcePath(uri);
        } catch (Exception e) {
	    path = uri;
	}
        return path;
    }    

    /** 
     * Return the values for all the instances of <code>themeProperty</code>.
     * If <code>themeProperty</code> cannot be found explicitly in a 
     * a <code>ThemeReference</code> then search for the 
     * <code>themeBundle</code> property. If it is defined, search the
     * identified resource bundles for <code>themeProperty</code>.
     *
     * @param themeReferenceIterator an iterator of available 
     * <code>ThemeResource</code>'s.
     * @param themeBundle the resource bundle that <code>themeProperty</code> is
     * associated with.
     * @param themeProperty the property to find
     * @param locale the current <code>Locale</code>
     * @param classLoader the <code>ClassLoader</code> to use when loading the
     * <code>ResourceBundle</code>.
     * @throws MissingResourceException if <code>themeProperty</code>
     * cannot be found.
     */
    protected Object getGlobalArrayProperty(
	    Iterator<ThemeReference> themeReferenceIterator,
	    ThemeResourceBundle.ThemeBundle themeBundle,
	    ThemeResourceBundle.ThemeProperty themeProperty, Locale locale,
	    ClassLoader classLoader) 
	    throws MissingResourceException {

	// So far we don't have any special processing based on
	// ThemeProperty so just get its key and get it from
	// the ThemeBundle.
	//
	return getGlobalArrayProperty(//themeReferences,
	    themeReferenceIterator,
		themeBundle, themeProperty.getKey(), locale, classLoader);
    }

    /** 
     * Return the values for all the instances of <code>themeProperty</code>.
     * If <code>themeProperty</code> cannot be found explicitly in a 
     * a <code>ThemeReference</code> then search for the 
     * <code>themeBundle</code> property. If it is defined, search the
     * identified resource bundles for <code>themeProperty</code>.
     *
     * @param themeReferenceIterator an iterator of available 
     * <code>ThemeResource</code>'s.
     * @param themeBundle the resource bundle that <code>themeProperty</code> is
     * associated with.
     * @param key the property to find
     * @param locale the current <code>Locale</code>
     * @param classLoader the <code>ClassLoader</code> to use when loading the
     * <code>ResourceBundle</code>.
     * @throws MissingResourceException if <code>themeProperty</code>
     * cannot be found.
     */
    protected Object getGlobalArrayProperty(
	    Iterator<ThemeReference> themeReferenceIterator,
	    ThemeResourceBundle.ThemeBundle themeBundle,
	    String key, Locale locale,
	    ClassLoader classLoader) 
	    throws MissingResourceException {

	// Save the first exception thrown
	//
	ArrayList properties = new ArrayList();
	MissingResourceException mre = null;
	Object property = null;
	while (themeReferenceIterator.hasNext()) {
	    ThemeReference ref = themeReferenceIterator.next();
	    try {
		// Pass the Properties which contain what was
		// in the Manifest attributes. If the application
		// defines one of these Properties bundles then it
		// may also have other data in addition to the
		// Manifest attributes.
		//
		themeResourceBundle.getGlobalArrayProperty(
		    ref.getBasename(),
		    themeBundle, key, locale,
		    ref.getClassLoader() == null ? classLoader : 
			ref.getClassLoader(),
		    properties);
	    } catch (MissingResourceException e) {
		if (mre == null) {
		    mre = e;
		}
		continue;
	    }
	}
	// There's really no way to do this safely if we
	// support compiled resource bundles.
	//
	if (properties.size() != 0) {
	    return properties.toArray(new String[properties.size()]);
	} else if (mre != null) {
	    throw mre;
	} else {
	    return null;
	}
    }

    /** 
     * Return the value of the first instance of <code>key</code>
     * from <code>themeBundle</code>.
     * @param key the property to find.
     * @param themeBundle the resource bundle that key is associated with.
     * @throws MissingResourceException if <code>key</code> cannot be found.
     */
    public Object getProperty(String key,
	    ThemeResourceBundle.ThemeBundle themeBundle)
	    throws MissingResourceException {

	ThemeResources themeResources = getThemeResources();
	ThemeContext themeContext = themeResources.getThemeContext();
	Locale locale = themeResources.getLocale();
	if (locale == null) {
	    locale = themeContext.getDefaultLocale();
	}
	ClassLoader classLoader = themeContext.getDefaultClassLoader();

	return getProperty(themeResources.getThemeReferenceIterator(),
		themeBundle, key, locale, classLoader);
    }

    /**
     * Return the first value of <code>key</code> that is found from
     * available <code>ThemeReference</code>'s in <code>themeIterator</code>.
     * @throws MissingResourceException if key cannot be found.
     */
    protected Object getProperty(
	    Iterator<ThemeReference> themeReferenceIterator,
	    ThemeResourceBundle.ThemeBundle themeBundle,
	    String key, Locale locale, ClassLoader classLoader) 
	    throws MissingResourceException {
		
	Object property = null;
	MissingResourceException mre = null;
	while (themeReferenceIterator.hasNext()) {
	    ThemeReference ref = themeReferenceIterator.next();
	    try {
		property = themeResourceBundle.getProperty(
		    ref.getBasename(),
		    themeBundle, key, locale,
		    ref.getClassLoader() == null ? classLoader :
			ref.getClassLoader());
	    } catch (MissingResourceException e) {
		if (mre != null) {
		    mre = e;
		}
		continue;
	    }
	    if (property != null) {
		break;
	    }
	}
	if (property == null && mre != null) {
	    throw mre;
	}
	return property;
    }

    /**
     * Returns an array of values for <code>keys</code>. The order of the
     * values in the returned array is matched to the order of
     * <code>keys</code>, i.e. values[0] is the value of keys[0].
     * 
     * @param keys the properties to find.
     * @param themeBundle the resource bundle that <code>keys</code> are
     * associated with.
     * @throws MissingResourceException if any of <code>keys</code> cannot be
     * found.
     */
    public Object[] getProperties(String keys[],
	    ThemeResourceBundle.ThemeBundle themeBundle) 
	    throws MissingResourceException {

	ThemeResources themeResources = getThemeResources();
	ThemeContext themeContext = themeResources.getThemeContext();
	Locale locale = themeResources.getLocale();
	if (locale == null) {
	    locale = themeContext.getDefaultLocale();
	}
	ClassLoader classLoader = themeContext.getDefaultClassLoader();

	return getProperties(themeResources.getThemeReferenceIterator(),
		themeBundle, keys, locale, classLoader);
    }

    /**
     * Returns an array of values for <code>keys</code>. The order of the
     * values in the returned array is matched to the order of
     * <code>keys</code>, i.e. values[0] is the value of keys[0].
     * 
     * @param themeReferenceIterator an iterator of available 
     * <code>ThemeResource</code>'s.
     * @param themeBundle the resource bundle that <code>themeProperty</code> is
     * associated with.
     * @param keys the properties to find.
     * @param locale the current <code>Locale</code>
     * @param classLoader the <code>ClassLoader</code> to use when loading the
     * <code>ResourceBundle</code>.
     * @throws MissingResourceException if <code>themeProperty</code>
     * cannot be found.
     */
    protected Object[] getProperties(
	    Iterator<ThemeReference> themeReferenceIterator,
	    ThemeResourceBundle.ThemeBundle themeBundle,
	    String[] keys, Locale locale, ClassLoader classLoader) 
	    throws MissingResourceException {
		
	Object[] properties = null;
	MissingResourceException mre = null;
	while (themeReferenceIterator.hasNext()) {
	    ThemeReference ref = themeReferenceIterator.next();
	    try {
		properties = themeResourceBundle.getProperties(
		    ref.getBasename(),
		    themeBundle, keys, locale,
		    ref.getClassLoader() == null ? classLoader :
			ref.getClassLoader());
	    } catch (MissingResourceException e) {
		if (mre != null) {
		    mre = e;
		}
		continue;
	    }
	    if (properties != null) {
		break;
	    }
	}
	if (properties == null && mre != null) {
	    throw mre;
	}
	return properties;
    }

    interface JavaScriptTransform {
	public char DOT = '.';
	public char USCORE = '_';
	public String OPENBRACERE = "{";
	public String OPENPARAMSUB = "${";
	public String DQ = "\"";
	public String SQ = "'";
	public String SQRE = "''";
	public String processKey(String key);
	public String processValue(String key, String value);
    }

    /**
     * Generate Javascript files from all the theme properties
     * Need to watch out for properties that are Javascript keywords.
     * Currently there is on one hit "default" in the stylesheets.properties
     * file.
     */
    public static final void main(String[] args) {

	String UNKNOWN_OPTION = 
	    "Unknown argument: {0}\n{1}";
	String CANT_GET_THEME_FACTORY_CLASS =
	    "Exception obtaining Class instance for {0}.";
	String CANT_GET_THEME =
	    "Exception obtaining Theme instance ''{0}''.";
	String CANT_CREATE_FILE =
	    "Exception creating file {0}{1}{2}.";
	String CANT_WRITE_COPYRIGHT =
	    "Exception writing copyright to {0}{1}{2}.";
	String UNKNOWN_THEME_BUNDLE =
	    "Unrecognized ThemeBundle ''{0}''.";
	String CANT_CONVER_BUNDLE_TO_JS =
	    "themeBundleToJavaScript failed to convert bundle ''{0}''.";
	String CANT_WRITE_BUNDLE =
	    "Exception writing bundle converted ''{0}''.";
	String CANT_CLOSE_FILE = 
	    "Exception closing file {0}{1}{2}.";
	String CANT_JSON_PUT =
	    "JSONException in 'put' of bundle ''{0}''.";

	String usage =
"Usage: java com.sun.webui.jsf.theme.ResourceBundleTheme \n" +
"[-bundle messages|stylesheets|templates|properties|javascript|images|" +
"styles]\n" +
"[-factory <ThemeFactory implementation class name>]\n" +
"[-dir destination-directory]\n" +
"[-outfile destination-javascript-theme-file]\n" +
"[-theme <theme name>]\n" +
"[-version <theme version>]\n" +
"[-locale <lang>[_<country>[_<variant>]]]\n" +
"[-l10nJar \"jar:file:<fullpath>/<jarname>_<lang>[_<country>[_<variant>]].jar!/\"\n" +
"[-prettyprint <indent>]\n" +
"\n" +
"[-help]\n" +
"'-bundle' - the properties file to process. If not specified, all " +
"properties\n" +
"    bundles are processed. If 'bundle' is specifed the file name is\n" +
"    '<bundle>.js'.\n" +
"'-factory' - the ThemeFactory implementation to use, to obtain a Theme\n" +
"    instance. If not specified the 'com.sun.webui.theme.SPIThemeFactory'\n" +
"    implementation is used.\n" +
"'-dir' - the destination directory for the theme javascript file.\n" +
"    If not specified the file is created in the current directory.\n" +
"'-outfile - the javascript theme file name created in '-dir'. If not \n" +
"    specified the file name created is named from the " +
"    'ThemeJavascript.THEME_BUNDLE' theme property \n" +
"    with an extension of '.js'. If the theme property is not set \n" +
"    '<theme name>.js' is used.\n" +
"-l10nJar - the URI includling the full path to l10n jar file. This value\n" +
"    is used to create a URLClassLoader restricting the class path to this jar.\n" +
"If '-theme' is not specified 'suntheme' is used. This theme is used as the\n" +
"    source of the properties files.\n" +
"If '-version' is not specified then '4.3' is used.\n" +
"\n" +
"The webui-jsf.jar and webui-jsf-suntheme.jar or equivalent theme jar\n" +
"    must appear in the classpath.\n" +
"\n" +
"Note that if the '-bundle' option is specified,\n" +
"    javascript must be written to load that file. The default 'Head' " +
"component\n" +
"    behavior loads theme files based on the " +
"'ThemeJavascript.THEME_BUNDLE',\n" +
"    'ThemeJavascript.THEME_MODULE', and " +
"'ThemeJavascript.THEME_MODULE_PATH'\n" +
"    (or 'ThemeJavascript.THEME_MODULE_PATH_UNCOMPRESSED') theme " +
"properties.\n";

	String copyright = "//\n" +
	    "// The contents of this file are subject to the terms\n" +
	    "// of the Common Development and Distribution License\n" +
	    "// (the License).  You may not use this file except in\n" +
	    "// compliance with the License.\n" +
	    "//\n" +
	    "// You can obtain a copy of the license at\n" +
	    "// https://woodstock.dev.java.net/public/CDDLv1.0.html.\n" +
	    "// See the License for the specific language governing\n" +
	    "// permissions and limitations under the License.\n" +
	    "//\n" +
	    "// When distributing Covered Code, include this CDDL\n" +
	    "// Header Notice in each file and include the License file\n" +
	    "// at https://woodstock.dev.java.net/public/CDDLv1.0.html.\n" +
	    "// If applicable, add the following below the CDDL Header,\n" +
	    "// with the fields enclosed by brackets [] replaced by\n" +
	    "// you own identifying information:\n" +
	    "// \"Portions Copyrighted [year] [name of copyright owner]\"\n" +
	    "//\n" +
	    "// Copyright 2007 Sun Microsystems, Inc. All rights reserved.\n" +
	    "//\n";

	// This list came from
	// http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Reserved_Words

	String DOT = ".";
	String EXT = ".js";
	String FILE_SEPARATOR = System.getProperty("file.separator");

	String properties = null;
	String outfile = null;
	String dir = ".";
	String factoryClass = null;
	String themeName = "suntheme";
	String version = "4.3";
	String locale = null;
	String l10nJar = null;
	int indent = 0;

	// Set to -1 on error
	//
	int retval = 0;

	boolean debug = false;
	for (int i = 0; i < args.length; ++i) {
	    if (args[i].equals("-bundle")) {
		properties = args[++i];
	    } else 
	    if (args[i].equals("-factory")) {
		factoryClass = args[++i];
	    } else
	    if (args[i].equals("-dir")) {
		dir = args[++i];
	    } else 
	    if (args[i].equals("-theme")) {
		themeName = args[++i];
	    } else
	    if (args[i].equals("-version")) {
		version = args[++i];
	    } else
	    if (args[i].equals("-debug")) {
		debug = true;
	    } else
	    if (args[i].equals("-locale")) {
		locale = args[++i];
	    } else 
	    if (args[i].equals("-l10nJar")) {
		l10nJar = args[++i];
	    } else
	    if (args[i].equals("-prettyprint")) {
		indent = Integer.parseInt(args[++i]);
	    } else
	    if (args[i].equals("-outfile")) {
		outfile = args[++i];
	    } else
	    if (args[i].equals("-help")) {
		System.out.println(usage);
		System.exit(0);
	    } else {
		System.err.println(java.text.MessageFormat.format(
		    UNKNOWN_OPTION, args[i], usage));
		System.exit(-1);
	    }
	}
	while (debug);
	// Both must be set.
	//
	if (!(locale == null ^ l10nJar == null)) {
	    ;
	} else {
	    System.err.println(usage);
	    System.exit(-1);
	}

	ThemeFactory factory = null;
	if (factoryClass == null) {
	    factory = new SPIThemeFactory();
	} else {
	    try {
		factory = (ThemeFactory)
		    Class.forName(factoryClass).newInstance();
	    } catch (Exception e) {
		System.err.println(
		    java.text.MessageFormat.format(CANT_GET_THEME_FACTORY_CLASS,
			factoryClass) + "\n" + e.getMessage());
		System.exit(-1);
	    }
	}

	// Use the existing theme jar that must exist on the class path
	// There must be only one, for a given theme.
	//
	class DefaultThemeContext extends ThemeContext {
	    DefaultThemeContext() {
		super();
	    }
	}
	ThemeContext themeContext = new DefaultThemeContext();
	Theme theme = null;
	try {
	    theme = factory.getTheme(themeName, version, null, themeContext);
	} catch (Exception te) {
	    System.err.println(
		java.text.MessageFormat.format(CANT_GET_THEME, themeName,
		    version) + "\n" + te.getMessage());
	    System.exit(-1);
	}

	// Generating a single bundle.
	//
	if (properties != null && properties.length() != 0) {
	    outfile = properties;
	}

	// We assume that the directories for the locales
	// have already been created.
	//
	if (outfile == null) {
	    outfile = theme.getJSString(
		    com.sun.webui.jsf.theme.ThemeJavascript.THEME_BUNDLE);
	    if (outfile == null || outfile.length() == 0) {
		outfile = themeName;
	    }
	    outfile = outfile.concat(EXT);
	}

	java.io.BufferedWriter out = null;
	try {
	    out = new java.io.BufferedWriter(
		new java.io.FileWriter(dir + FILE_SEPARATOR + outfile));
	} catch (java.io.IOException ex) {
	    System.err.println(
		java.text.MessageFormat.format(CANT_CREATE_FILE, dir,
		    FILE_SEPARATOR, outfile)
		+ "\n" + ex.getMessage());
	    System.exit(-1);
	}

	StringBuilder sb = new StringBuilder(10240);

	// Add the copyright and disclaimer
	//
	sb.append(copyright)
	    .append("//This is a generated file. Do not edit.\n\n");

	try {
	    out.write(sb.toString());
	} catch (java.io.IOException ex) {
	    System.err.println(
		java.text.MessageFormat.format(CANT_WRITE_COPYRIGHT, dir,
		    FILE_SEPARATOR, outfile)
		+ "\n" + ex.getMessage());
	    try { out.close(); } catch(Exception ec) {}
	    System.exit(-1);
	}
	sb.setLength(0);

	// Define the Transforms
	//
	JavaScriptTransform noop = new JavaScriptTransform() {
	    public String processKey(String key) { return key.trim(); }
	    public String processValue(String key, String value) {
		return value.trim();
	    }
	};

	JavaScriptTransform messageTransform = new JavaScriptTransform() {

	    public String processKey(String key) {
		return key.trim();
	    }
	    public String processValue(String key, String value) {
		// Fix quotes. 
		// Change " to ' (JSON handles this)
		// Change '' to '
		//
		value = value.replace(SQRE, SQ);
		// Fix parameter substitution syntax.
		// {0} to ${0}
		//
		value = value.replace(OPENBRACERE, OPENPARAMSUB);
		return value.trim();
	    }
	};

	JavaScriptTransform templateTransform = new JavaScriptTransform() {

	    String CANT_GET_RESOURCE = 
		"templateTransform: Can''t ''getResource'' key = {0}, " +
		    "value =  {1}";
	    String CANT_READ_TEMPLATE_FILE = 
		"Exception reading template file {0}.";
	    String CANT_CLOSE_READER =
		"Exception closing file {0}.";

	    public String processKey(String key) { return key.trim(); }

	    public String processValue(String key, String value) {

		String path = value.charAt(0) == '/' ? 
		    value.substring(1) : value;
		URL templateFile = 
		    this.getClass().getClassLoader().getResource(path);
		if (templateFile == null) {
		    // This should probably throw and/or exit.
		    //
		    System.err.println(
			java.text.MessageFormat.format(
			    CANT_GET_RESOURCE, key, value));
		    return value.trim();
		}
		java.io.InputStream streamIn = null;
		java.io.InputStreamReader inreader = null;
		java.io.BufferedReader reader = null;
		try {
		    StringBuilder sb = new StringBuilder(512);
		    streamIn = templateFile.openStream();
		    inreader = new java.io.InputStreamReader(streamIn);
		    reader = new java.io.BufferedReader(inreader);

		    String s = null;
		    while ((s = reader.readLine()) != null) {
			// Remove whitespace.
			//
			String sr = s.replaceAll("[ \t]*<[ \t]*", "<").
			    replaceAll("[ \t]*>[ \t]*", ">");
		       sb.append(sr);
		    }
		    value = sb.toString();
		} catch(Exception e) {
		    System.err.println(
			java.text.MessageFormat.format(CANT_READ_TEMPLATE_FILE,
			    path) + "\n" + e.getMessage());
		} finally {
		    try {
			// Don't know if I need to close each or
			// if a close on the outer reader is sufficient.
			//
			streamIn.close();
			inreader.close();
			reader.close();
		    } catch (Exception et) {
			System.err.println(
			    java.text.MessageFormat.format(CANT_CLOSE_READER,
				path) + "\n" + et.getMessage());
		    }
		}
		return value.trim();
	    }
	};

	JavaScriptTransform transform = noop;

	// These are actual "Theme" implementation specific since the
	// enums are defined in ResourceBundleTheme and not Theme.
	//
	ThemeResourceBundle.ThemeBundle[] bundles =
	    ThemeResourceBundle.ThemeBundle.values();

	// We don't want to change the previously created
	// themeContext, since it is stored in the theme instance
	// and is for the "base" theme. Create another ThemeContext
	// to pass to ResourceBundle.getBundle specific to the
	// locale specific resources
	//
	ThemeContext bundleContext = themeContext;

	// Set up the themeContext for locale variants
	//
	if (locale != null) {
	    bundleContext = new DefaultThemeContext();
	    try {
		// Split up the locale
		String[] localesegments = locale.split("_");
		Locale localeInstance = null;
		if (localesegments.length == 1) {
		    localeInstance = new Locale(localesegments[0]);
		} else
		if (localesegments.length == 2) {
		    localeInstance = new Locale(localesegments[0],
			localesegments[1]);
		} else 
		if (localesegments.length == 3) {
		    localeInstance = new Locale(localesegments[0],
			localesegments[1], localesegments[2]);
		}
		ClassLoader cl = new java.net.URLClassLoader(
		    new java.net.URL[] { new java.net.URL(l10nJar) }, null);
		bundleContext.setDefaultClassLoader(cl);
		bundleContext.setDefaultLocale(localeInstance);
	    } catch (Exception e) {
		System.err.println(e.getMessage());
		System.exit(-1);
	    }
	}
	org.json.JSONObject jsonBundles = new org.json.JSONObject();

	for (int i = 0; i < bundles.length; ++i) {

	    ThemeResourceBundle.ThemeBundle tb = bundles[i];

	    // For all known bundles
	    // Get the "well known" bundle name. Locale variants
	    // have the same package basename with the locale variant
	    // suffix. The themeContext contains the appropriate
	    // locale and the classloader is restricted to only
	    // the l10n jar or directory.
	    //
	    String basename = (String)
		((ResourceBundleTheme)theme).getProperty(tb.getKey(), tb);
	    if (basename == null) {
		continue;
	    }

	    // Assumes only one of these bundles is ever declared.
	    // There could be an array of basenames.
	    // TODO
	    //
	    ResourceBundle rb = null;
	    try {
		rb = ResourceBundle.getBundle(basename, 
		    bundleContext.getDefaultLocale(),
		    bundleContext.getDefaultClassLoader());
	    } catch(Exception e) {
		// If we are processing a locale, it may not
		// have any specializations
		//
		if (locale != null) {
		    continue;
		}
		e.printStackTrace();
		retval = -1;
		break;
	    }

	    // If null, they are just "theme" properties.
	    // Shouldn't happen here. We will need to get all
	    // the properties in the "Root" bundle and these
	    // will be theme properties.
	    //
	    String themeCategory = null;
	    switch(tb) {
	    case MESSAGES:
		themeCategory = "messages";
		transform = messageTransform;
	    break;
	    case STYLESHEET:
		themeCategory = "stylesheets";
		transform = noop;
	    break;
	    case TEMPLATE:
		themeCategory = "templates";
		transform = templateTransform;
	    break;
	    case PROPERTIES:
		themeCategory = "properties";
		transform = noop;
	    break;
	    case JAVASCRIPT:
		themeCategory = "javascript";
		transform = noop;
	    break;
	    case IMAGES:
		themeCategory = "images";
		transform = noop;;
	    break;
	    case CLASSMAPPER:
		themeCategory = "styles";
		transform = noop;
	    break;
	    }

	    // No category means just properties
	    // TODO
	    //
	    if (themeCategory == null) {
		System.err.println(
		    java.text.MessageFormat.format(UNKNOWN_THEME_BUNDLE, tb));
		continue;
	    }

	    // Dump only "properties"
	    // 
	    if (properties != null && !properties.equals(themeCategory)) {
		continue;
	    }

	    org.json.JSONObject jsonBundleProps = 
		((ResourceBundleTheme)theme).
		    themeBundleToJavaScript(transform, rb, themeCategory);
	    if (jsonBundleProps == null) {
		System.err.println(
		    java.text.MessageFormat.format(CANT_CONVER_BUNDLE_TO_JS, 
			themeCategory));
		retval = -1;
		break;
	    }

	    try {
		jsonBundles.put(themeCategory, jsonBundleProps);
	    } catch (Exception je) {
		System.err.println(
		    java.text.MessageFormat.format(CANT_JSON_PUT, 
			themeCategory)
		    + "\n" + je.getMessage());
		retval = -1;
		break;
	    }
	}

	try {
	    // Need to change all "\\" to "\" that json escapes.
	    //
	    String outString = null;
	    if (indent != 0) {
		outString = jsonBundles.toString(indent).
			replaceAll("\\\\u", "\\u") + "\n";
	    } else {
		outString = jsonBundles.toString().
			replaceAll("\\\\u", "\\u") + "\n";
	    }
	    out.write(outString);
	    out.close();
	} catch (Exception ex) {
	    System.err.println(
		java.text.MessageFormat.format(CANT_CLOSE_FILE,
		    dir, FILE_SEPARATOR, outfile)
		+ "\n" + ex.getMessage());
	    retval = -1;
	}
	System.exit(retval);
    }

    org.json.JSONObject themeBundleToJavaScript(JavaScriptTransform transform,
	    ResourceBundle rb, String bundle) {

	String CANT_JSON_PUT =
	    "JSONException in 'put' for key ''{0}'' from ''{1}''.";
	String CANT_JSON_TOSTRING =
	    "Exception JSONObject.toString() for key ''{0}'' from " +
	    "bundle ''{1}''.";
	org.json.JSONObject json = new org.json.JSONObject();

	java.util.Enumeration keys = rb.getKeys();
	while (keys.hasMoreElements()) {

	    String key = (String)keys.nextElement();
	    String value = rb.getString(key);

	    key = transform.processKey(key);
	    key = saveConvert(key);
	    value = transform.processValue(key, value);
	    value = saveConvert(value);

	    try {
		json.put(key, value);
	    } catch (org.json.JSONException je) {
		System.err.println(
		    java.text.MessageFormat.format(CANT_JSON_PUT, key,
			bundle)
		    + "\n" + je.getMessage());
		return null;
	    }
	}

	return json;
    }

    // Taken from the Properties source
    //
    /**
     * Converts unicodes to encoded &#92;uxxxx and escapes
     * special characters with a preceding slash
     */
    private String saveConvert(String theString) {
        int len = theString.length();
        int bufLen = len * 2;
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below

	    /* Only want the UTF conversion
	     *
	     * Convert control characters to fool JSON
	     */
	    if ((aChar < 0x0020) || (aChar > 0x007e)) {
		outBuffer.append('\\');
		outBuffer.append('u');
		outBuffer.append(toHex((aChar >> 12) & 0xF));
		outBuffer.append(toHex((aChar >>  8) & 0xF));
		outBuffer.append(toHex((aChar >>  4) & 0xF));
		outBuffer.append(toHex( aChar        & 0xF));
	    } else {
		outBuffer.append(aChar);
	    }
        }
        return outBuffer.toString();
    }

    /**
     * Convert a nibble to a hex character
     * @param   nibble  the nibble to convert.
     */
    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };


}

/**
 * The <code>ThemeResourceBundle</code> class provides a set of methods
 * for obtaining theme resources from <code>ResourceBundle</code>'s typically
 * provided as <code>Properties</code> files.
 * <p>
 * When a property is requested the following strategy is used to obtain
 * the value.
 * <ul>
 * <li>look for the property in the <code>ResourceBundle</code> identified
 * by the <code>basename</code> argument and return the value if the property
 * is found, else</li>
 * <li>if the property is a <code>ThemeBundle</code> property obtain the
 * value of the <code>ThemeBundle</code> property from the basename
 * <code>ResourceBundle</code>. The value is one or more space separated
 * <code>ResourceBundle</code> basenames. Search for the the desired property
 * from the <code>ResourceBundle</code>'s identified by 
 * the basename(s) in the order they are listed, and return the value if found.
 * <li>if the property is not found or an associated <code>ThemeBundle</code>
 * <code>ResourceBundle</code> property is not defined, throw 
 * <code>MissingResourceException</code>.</li>
 * <li>if a the property is not a <code>ThemeBundle</code> or a 
 * <code>ThemeProperty</code> and is not associated with a
 * <code>ThemeBundle</code>, search for the property in the
 * <code>ResourceBundle</code> identified by the basename argument
 * and if not found, search for the <code>ThemeBundle.PROPERTIES</code>
 * property and if it is defined, search the <code>ResourceBundle</code>'s
 * listed in its value.</li>
 * </ul>
 * </p>
 * <p>
 * Example:
 * <code>
 * ThemeResourceBundle themeResourceBundle = new ThemeResourceBundle();
 * // We don't have a specific ClassLoader or Locale.
 * Object value = themeResourceBundle.getProperty("org.application.Theme",<br/>
 *	ThemeBundle.MESSAGES,<br/>
 *	"FileChooser.openFolder", null, null);
 * </code>
 * <p>
 * An attempt is first made to find the <code>FileChooser.openFolder</code>
 * property in the <code>ResourceBundle</code> 
 * identified by <code>org.application.Theme</code> and if it is found, 
 * the value is returned.<br/>If it is not found, the 
 * <code>ThemeBundle.MESSAGES</code> property (the value of
 * <code>ThemeBundle.MESSAGES.getKey()</code>) is retrieved
 * from the <code>org.application.Theme</code> <code>ResourceBundle</code>.
 * This property should be defined in this resource bundle, if it is a
 * complete theme's resource bundle but may not be defined in an application's 
 * <code>ResourceBundle</code> which just overrides some theme properties.<br/>
 * If the property exists, its value is one or more
 * <code>ResourceBundle</code> basenames that defines
 * <code>ThemeBundle.MESSAGES</code> properties. 
 * <code>FileChooser.openFolder</code> is such a property. If there is more
 * than one basename, the <code>ResourceBundle</code> for each basename is
 * loaded and queried for <code>FileChooser.openFolder</code>. Each
 * <code>ResourceBundle</code> is queried in the order the basenames
 * are listed. If the property exists its value is returned, else
 * <code>MissingResourceException</code> is thrown if
 * <code>FileChooser.openFolder<code> or 
 * <code>ThemeBundle.MESSAGES</code> is not defined.
 * </p>
 * <p>
 * Note that the default locale and classloader will be used as defined
 * by <code>ResourceBundle</code> since both arguments are null.
 * </p>
 * <p>
 * A <code>ResoureceBundle</code> is loaded lazily. They are not cached
 * and are obtained from <code>ResourceBundle.getBundle()</code> for 
 * every property request.
 * </p>
 * <p>
 * <code>getObject</code> is used to obtain the value of requested properties.
 * </p>
 */
class ThemeResourceBundle {

    private static final String CANT_FIND_PROPERTY = "Can't find property";

    // Need a property that MUST be present to make it a 
    // ThemeResourceBundle properties file, I think.
    //
    // There are a couple of conventions that could be
    // established to allow an application to indicate in the
    // properties file, those application defined properties
    // that are of a specific type. For example parsing out the
    // "*.bundle.*" to indicate that a application defined property
    // is a ResourceBundle, etc.
    // Or create a property "Theme.bundles" that list keys that are
    // to be treated as bundles.
    // or "<prop>.objectClass" where any property that is found
    // may have an associated "<prop>.objectClass", in which case
    // "getObject" returns an instance of this class passing the
    // property value as an object parameter to its constructor.
    //
    //
    static final String VALUE_SEPARATOR = " ";

    /**
     * Return all values of <code>key</code> in <code>properties</code>
     * from the <code>basename</code> <code>ResourceBundle</code> and all
     * <code>themeBundle</code> <code>ResourceBundle</code>'s defined
     * in <code>basename</code>.
     * @throws MissingResourceException if <code>basename</code> cannot
     * be loaded or <code>key</code> is not defined.
     */
    protected void getGlobalArrayProperty(String basename, 
	    ThemeBundle themeBundle,
	    String key, Locale locale,
	    ClassLoader classLoader, ArrayList properties)
		throws MissingResourceException {

	// This search should come after looking in
	// bundles that may define this key, since the top level
	// bundle should override, nested bundle definitions,
	// depending how the caller wants the ordering.
	//
	getGlobalArrayPropertyFromBundle(basename, 
		key, locale, classLoader, properties);

	// Get the basenames defined by the themeBundle property.
	// ThemeBundles are returned as String[] containing the basenames
	// If this method throws, and property is null, throw a
	// new MissingResourceException
	//
	String[] basenames = null;
	try {
	    basenames = (String[])getProperty(basename, themeBundle,
		locale, classLoader);
	} catch (MissingResourceException mre) {
	    // Handle this in the if clause below.
	}
	if (basenames != null && basenames.length > 0) {
	    for (String bname : basenames) {
		getGlobalArrayPropertyFromBundle(bname, key, locale,
		    classLoader, properties);
	    }
	} else if (properties.size() == 0) {
	    throw new MissingResourceException(
		CANT_FIND_PROPERTY,
		basename, key);
	}
    }

    /**
     * Return the value for the <code>key</code> property that is defined
     * in a <code>themeBundle</code> <code>ResourceBundle</code>.
     * <p>
     * Return the value of <code>key</code> if it is defined in
     * the <code>basename</code> <code>ResourceBundle</code>.
     * If <code>key</code> is not found,
     * search for <code>key</code> in <code>themBundle</code> bundles
     * defined in the <code>basename</code> <code>ResourceBundle</code>.
     * </p>
     * If <code>key</code> is a multi valued property an array may not
     * be returned.
     * @throws <code>MissingResourceException</code> if <code>key</code>
     * is not defined or <code>basename</code> cannot be loaded.
     */
    protected Object getProperty(String basename,
	    ThemeBundle themeBundle, 
	    String key, Locale locale, ClassLoader classLoader) 
	    throws MissingResourceException {

	// Search basename for the property.
	//
	Object property = null;
	try {
	    property = getPropertyFromBundle(basename, key,
		locale, classLoader, null);
	} catch (MissingResourceException mre) {
	    // basename doesn't exist
	    throw mre;
	}
	if (property != null) {
	    return property;
	}

	// Get the basenames defined by the themeBundle property.
	// Will throw if themeBundle is not defined.
	//
	String[] basenames = (String[])getProperty(basename, themeBundle,
		locale, classLoader);
	if (basenames == null || basenames.length == 0) {
	    throw new MissingResourceException(
		CANT_FIND_PROPERTY,
		basename, key);
	}
	// Return the first definition of themeProperty from the
	// basenames ResourceBundles.
	//
	return getProperty(basenames, key, locale, classLoader);
    }

    /**
     * Essentially the same as <code>getProperty</code> but operates 
     * on an array of property keys and returns an array of values
     * in the the order of <code>keys</code>.
     */
    protected Object[] getProperties(String basename, 
	    ThemeBundle themeBundle, String[] keys,
	    Locale locale, ClassLoader classLoader) 
	    throws MissingResourceException {

	if (keys == null || keys.length == 0) {
	    return null;
	}
	// Search basename for the properties
	//
	Object[] properties = null;
	MissingResourceException lastMre = null;
	try {
	    properties = getPropertiesFromBundle(basename, keys,
		locale, classLoader);
	} catch (MissingResourceException mre) {
	    lastMre = mre;
	}
	if (properties != null) {
	    return properties;
	}

	// Get the basenames defined by the themeBundle property.
	// Will throw if themeBundle is not defined.
	//
	String[] basenames = (String[])getProperty(basename, themeBundle,
		locale, classLoader);
	if (basenames == null || basenames.length == 0) {
	    if (lastMre != null) {
		throw lastMre;
	    } else {
		throw new MissingResourceException(
		    CANT_FIND_PROPERTY,
		    basename, "");
	    }
	}
	for (String bname : basenames) {
	    try {
		properties = getPropertiesFromBundle(bname, keys,
		    locale, classLoader);
	    } catch (MissingResourceException mre) {
		lastMre = mre;
	    }
	}
	if (properties == null) {
	    if (lastMre != null) {
		throw lastMre;
	    } else {
		throw new MissingResourceException(
		    CANT_FIND_PROPERTY,
		    basename, "");
	    }
	}
	return properties;
    }

    // Currently there is no special functionality for getting
    // a ThemeBundle property, other than returning a StringArray
    // in case there is more than one value.
    //
    /**
     * Return one or more <code>ResourceBundle</code> basenames that define
     * the properties of a <code>themeBundle</code> <code>ResourceBundle</code>.
     * This implemetation returns a <code>String[]</code> of
     * <code>ResourceBundle</code>basenames.
     * @throws MissingResourceException if the
     * <code>themeBundle.getKey()</code> property cannot be found or
     * <code>basename</code> cannot be loaded.
     */
    private Object getProperty(String basename, ThemeBundle themeBundle,
	    Locale locale, ClassLoader classLoader)
	    throws MissingResourceException {

	String basenames = (String)getPropertyFromBundle(basename, 
		themeBundle.getKey(), locale, classLoader);
	if (basenames == null || basenames.trim().length() == 0) {
	    throw new MissingResourceException(
		CANT_FIND_PROPERTY,
		basename,
		themeBundle.getKey());
	}
	return basenames.trim().split(VALUE_SEPARATOR);
    }

    /**
     * Convenience routine for obtaining a property from an ordered list of 
     * of <code>ResourceBundle</code> basenames.
     * Return the first definition of <code>key</code> that is found.
     * <code>key</code> may be a multi-valued property but an array may not
     * be returned.
     * @throws MissingResourceException if <code>key</code> is not defined
     * in any of the <code>basenames</code>
     */
    private Object getProperty(String[] basenames, String key,
	    Locale locale, ClassLoader classLoader)
	    throws MissingResourceException {

	Object property = null;
	for (int i = 0; i < basenames.length && property == null; ++i) {
	    // Only throw if none of the basenames defines key.
	    //
	    try {
		property = getPropertyFromBundle(basenames[i], key,
		    locale, classLoader);
	    } catch (MissingResourceException mre) {
	    }
	}
	if (property == null) {
	    // probably should list all basenames searched.
	    //
	    throw new MissingResourceException(
		CANT_FIND_PROPERTY,
		basenames[0],
		key);
	}
	return property;
    }

    /**
     * Return array values of <code>key</code> in
     * <code>properties</code> if defined in <code>basename</code>.
     * @throws MissingResourceException if <code>basename</code>cannot be
     * loaded.
     */
    private void getGlobalArrayPropertyFromBundle(String basename, 
	    String key, Locale locale, ClassLoader classLoader,
	    ArrayList properties) 
	    throws MissingResourceException {

	// Throws only if basename cannot be loaded.
	//
	Object property = getArrayPropertyFromBundle(basename,
		key, locale, classLoader);
	if (property == null) {
	    return;
	}
	if (property instanceof Object[]) {
	    for (Object obj : (Object[])property) {
		properties.add(obj);
	    }
	} else {
	    properties.add(property);
	}
    }

    // Maybe this should be called "getMultValuePropertyFromBundle" since
    // we don't ensure it returns an Array.
    //
    /**
     * Return the array value of <code>key</code> if defined in
     * <code>basename</code>.
     * @throws MissingResourceException if <code>basename</code>cannot be
     * loaded.
     */
    private Object getArrayPropertyFromBundle(String basename, 
	    String key, Locale locale, ClassLoader classLoader)
	    throws MissingResourceException {

	// Throws only if basename can't be loaded.
	//
	Object property = getPropertyFromBundle(basename, key,
	    locale, classLoader, null);

	// "array" properties are actually a String using VALUE_SEPARATOR 
	// to separate the elements. If it's not a String assume it is
	// in the appropriate form.
	//
	if (property != null && property instanceof String) {
	    String value = ((String)property).trim();
	    if (value.length() > 0) {
		property = value.split(VALUE_SEPARATOR);
	    } else {
		property = null;
	    }
	}
	return property;
    }

    /**
     * Return the value of <code>key</code> or <code>defaultValue</code> if
     * <code>key</code> is not defined.
     * @throws MissingResourceException if <code>basename</code>
     * cannot be loaded.
     */
    private Object getPropertyFromBundle(String basename, String key,
	    Locale locale, ClassLoader classLoader, String defaultValue) 
	    throws MissingResourceException {

	ResourceBundle bundle = null;
	Object property = defaultValue;
	try {
	    // Need to distinguish the cause of a MissingResourceException.
	    // Either the bundle was not found or the property was not 
	    // found.
	    // 
	    bundle = getResourceBundle(basename, locale, classLoader);
	    property = bundle.getObject(key);
	} catch (MissingResourceException mre) {
	    // Throw only if the bundle could not be loaded.
	    //
	    if (bundle == null) {
		throw mre;
	    }
	}
	return property;
    }

    /**
     * Get the property <code>key</code> from <code>ResourceBundle</code>
     * identified by <code>basename</code>.
     * @throws MissingResourceException if <code>key</code> is not
     * found or <code>basename</code> cannot be loaded.
     */
    private Object getPropertyFromBundle(String basename, String key,
	    Locale locale, ClassLoader classLoader) 
	    throws MissingResourceException {

	ResourceBundle bundle = getResourceBundle(basename, locale,
		classLoader);
	return bundle.getObject(key);
    }

    /**
     * Return the values for <code>keys</code>.
     * @throws MissingResourceException if none of the <code>keys</code>
     * can be found, or if <code>basename</code> can't be loaded.
     */
    // We know keys has entries.
    //
    private Object[] getPropertiesFromBundle(String basename, String[] keys,
	    Locale locale, ClassLoader classLoader)
	    throws MissingResourceException {

	ResourceBundle bundle = null;
	bundle = getResourceBundle(basename, locale, classLoader);

	int failures = 0;
	String badkey = null;
	Object[] properties = new Object[keys.length];
	for (int i = 0; i < keys.length; ++i) {
	    try {
		properties[i] = bundle.getObject(keys[i]);
	    } catch (Exception e) {
		// The ith properties element should be null on
		// exception and that is what we want.
		// But count the failures.
		//
		++failures;
		if (badkey != null) {
		    badkey = keys[i];
		}
	    }
	}
	// If the number of failures is equal to the number of entries
	// we got nothing, return null;
	// Note that this means when overriding an image theme
	// property you must override them all.
	//
	if (failures == keys.length) {
	    throw new MissingResourceException(
		CANT_FIND_PROPERTY, basename, badkey);
	}
	return properties;
    }

    /**
     * Convenience method for obtaining a <code>ResourceBundle</code>.
     */
    private ResourceBundle getResourceBundle(String basename,
	    Locale locale, ClassLoader classLoader)
	    throws MissingResourceException {

	if (locale != null) {
	    if (classLoader != null) {
		return ResourceBundle.getBundle(basename, locale, classLoader);
	    } else {
		return ResourceBundle.getBundle(basename, locale);
	    }
	} else {
	    return ResourceBundle.getBundle(basename);
	}
    }

    /**
     * Properties that <code>ThemeResourceBundle</code> defines.
     * All <code>ThemeResourceBundle</code> instances must be able to
     * return values for these properties. The value property may not
     * have a value though, depending on the instance.
     */
    enum ThemeProperty {
	/**
	 * The value of this property is a name that identifies
	 * a theme. A resource bundle that defines this property
	 * will only be referenced if a resource for that theme is
	 * requested.
	 */
	NAME("Theme.name"),
	/**
	 * The value of this property indcates the theme implementation
	 * version. This version identifes one of possibly many implementations
	 * of the theme called <code>NAME</code> based on a specific
	 * specification version. In other words different implementation
	 * versions could imply different brands of the same theme.
	 */
	VERSION("Theme.version"),

	// It's not clear if PREFIX, PROTOCOL, or PORT are
	// absolutely necessary.
	//
	/*
	 * The value of this property indicates a prefix that must be
	 * prepended to any path reference. For example a prefix is
	 * used to conform to a known servlet mapping that was specifically
	 * designed to deliver these resources.
	 * <p>
	 * The specification of this property affects many other
	 * properties defined here. A property value that refers to a 
	 * resource by a path that is expected to 
	 * to <em>be available from the application's class path</em>
	 * is influenced by this property. If this property is specified
	 * then those path resources will be referenced relative to 
	 * the web application or servlet context identified by the
	 * <code>PREFIX</code> property.
	 * </p>
	 */
	//PREFIX("Theme.prefix"),
	/*
	 * The value of this property indicates the protocol to use when
	 * creating a remote reference to a resource, like https, file, etc.
	 */
	//PROTOCOL("Theme.protocol"),
	/*
	 * The value of this property indicates the port to use when
	 * creating a remote reference to a resource.
	 */
	//PORT("Theme.port"),
	/**
	 * The value of this property is a space separated list of paths
	 * to CSS style sheets. The paths are expected to be available
	 * from the application's class path. These style sheets are always
	 * loaded first for every application page. 
	 * <p>
	 * References to these stylesheets are rendered in the reverse
	 * order in which they are found. In other words as theme resource
	 * bundles are found that define this property, the style sheets
	 * that are found first are rendered to the page last.<br/>
	 * For properties that define more than one style sheet, the
	 * list is rendered in the reverse order of the list; last style sheet
	 * first, first style sheet last.
	 * </p>
	 */
	//STYLESHEET_MASTER("Theme.stylesheet.master", true),
	STYLESHEET_MASTER("master", true),
	/**
	 * The value of this property is a space separated list of paths
	 * to CSS style sheets. The paths are expected to be available
	 * from the application's class path. These style sheets are always
	 * loaded after the <code>STYLESHEET_MASTER</code> style sheets.
	 * All definitions of this property are considered in the order
	 * found and all stylesheets defined are loaded.
	 */
	STYLESHEET("Theme.stylesheet", true),
	/**
	 * A <code>ResourceBundle</code> that contains style sheet
	 * related properties. This property is typically used when
	 * style sheets have locale variants, which is not common.
	 * It is usually sufficient to specify the following properties
	 * in the main theme resource bundle.
	 * <ul>
	 * <li><code>ThemeProperty.STYLESHEET_CLASSMAPPER</code></li>
	 * <li><code>ThemeProperty.STYLESHEET_MASTER</code></li>
	 * <li><code>ThemeProperty.STYLESHEET</code></li>
	 * </ul>
	 */
	STYLESHEET_BUNDLE("Theme.bundle.stylesheet"),
	/**
	 * A <code>ResourceBundle</code> that contains 
	 * properties whose values are CSS selectors.
	 * The properties defined in these bundles
	 * are referenced when application pages are rendered.
	 */
	STYLESHEET_CLASSMAPPER("Theme.bundle.stylesheet.classMapper"),
	/**
	 * The value of this property is a space separated list of
	 * paths to javascript files. These files are loaded for every
	 * application page. The paths are expected to be visible from
	 * the application's class path.
	 */
	JAVASCRIPT("Theme.javascript", true),
	/**
	 * A <code>ResourceBundle</code> that contains javascript
	 * related properties. This property is typically used when
	 * javascript files have locale variants, which is not common.
	 * It is usually sufficient to specify the following properties
	 * in the main theme resource bundle.
	 * <ul>
	 * <li><code>ThemeProperty.JAVASCRIPT</code></li>
	 * </ul>
	 */
	JAVASCRIPT_BUNDLE("Theme.bundle.javascript"),
	/**
	 * A <code>ResourceBundle</code> that contains HTML markup
	 * used for a "template" for clientside rendered components.
	 */
	TEMPLATE_BUNDLE("Theme.bundle.template"),
	/**
	 * A <code>ResourceBundle</code> that contains properties whose
	 * values are text strings, possibly formatted for use by
	 * <code>MessageFormat</code>. There are typically locale variants
	 * for this bundle.
	 */
	MESSAGES_BUNDLE("Theme.bundle.messages"),
	/**
	 * A <code>ResourceBundle</code> that contains properties whose
	 * values are text strings, defined by components or an application.
	 * There are typically no locale variants for this bundle, but
	 * locale variants are not prohibited.
	 */
	PROPERTIES_BUNDLE("Theme.bundle.properties"),
	/**
	 * A <code>ResourceBundle</code> that contains properties whose
	 * values are paths to image files. The paths are expected to be
	 * visible on the application's class path.
	 * There are typically no locale variants for this bundle, but
	 * locale variants are not prohibited.
	 */
	IMAGES_BUNDLE("Theme.bundle.images", false);

	/**
	 * The literal ResourceBundle property.
	 */
	private final String key;
	private final boolean multiValue;

	/**
	 * Create the enum and establish relationship between the
	 * enum and the literal property.
	 */
	ThemeProperty(String key, boolean multiValue) {
	    this.key = key;
	    this.multiValue = multiValue;
	}

	/**
	 * Create the enum and establish relationship between the
	 * enum and the literal property.
	 */
	ThemeProperty(String key) {
	    this(key, false);
	}

	/**
	 * Return the <code>ThemeProperty</code> that is equivalent to
	 * <code>key</code> or null if <code>key</code> is not equivalent.
	 */
	public static ThemeProperty toThemeProperty(String key) {
	    for (ThemeProperty p : values()) {
		if (p.getKey().equals(key)) {
		    return p;
		}
	    }
	    return null;
	}

	/**
	 * Return the literal <code>String</code> for this
	 * <code>ThemeProperty</code>, as it would appear in a
	 * <code>Properties</code> file.
	 */
	public String getKey() {
	    return key;
	}
	/**
	 * If this property is a global property then return
	 * true indicating that all definitions of this property
	 * should be obtained, recursively in nested bundles.
	 */
	public boolean isMultiValue() {
	    return multiValue;
	}
    }

    /**
     * A <code>ThemeProperty</code> that is <code>ThemeResourceBundle</code>.
     */
    enum ThemeBundle {
	/**
	 * A <code>ResourceBundle</code> that contains style sheet
	 * related properties. This property is typically used when
	 * style sheets have locale variants, which is not common.
	 * It is usually sufficient to specify the following properties
	 * in the main theme resource bundle.
	 * <ul>
	 * <li><code>ThemeProperty.STYLESHEET_CLASSMAPPER</code></li>
	 * <li><code>ThemeProperty.STYLESHEET_MASTER</code></li>
	 * <li><code>ThemeProperty.STYLESHEET</code></li>
	 * </ul>
	 */
	STYLESHEET(ThemeProperty.STYLESHEET_BUNDLE),
	/**
	 * A <code>ResourceBundle</code> that contains 
	 * properties whose values are CSS selectors. Theme properties
	 * are referenced when application pages are rendered.
	 */
	CLASSMAPPER(ThemeProperty.STYLESHEET_CLASSMAPPER),
	/**
	 * A <code>ResourceBundle</code> that contains javascript
	 * related properties. This property is typically used when
	 * javascript files have locale variants, which is not common.
	 * It is usually sufficient to specify the following properties
	 * in the main theme resource bundle.
	 * <ul>
	 * <li><code>ThemeProperty.JAVASCRIPT</code></li>
	 * </ul>
	 */
	JAVASCRIPT(ThemeProperty.JAVASCRIPT_BUNDLE),
	/**
	 * A <code>ResourceBundle</code> that contains properties whose
	 * values are text strings, possibly formatted for use by
	 * <code>MessageFormat</code>. There are typically locale variants
	 * for this bundle.
	 */
	MESSAGES(ThemeProperty.MESSAGES_BUNDLE),
	/**
	 * A <code>ResourceBundle</code> that contains properties whose
	 * values are paths to HTML files that contain markup for clientside
	 * rendered components.
	 */
	TEMPLATE(ThemeProperty.TEMPLATE_BUNDLE),
	/**
	 * A <code>ResourceBundle</code> that contains properties whose
	 * values are text strings, defined by components or an application.
	 * There are typically no locale variants for this bundle, but
	 * locale variants are not prohibited.
	 */
	PROPERTIES(ThemeProperty.PROPERTIES_BUNDLE),
	/**
	 * A <code>ResourceBundle</code> that contains properties whose
	 * values are paths to image files. The paths are expected to be
	 * visible from the application's class path.
	 * There are typically no locale variants for this bundle, but
	 * locale variants are not prohibited.
	 */
	IMAGES(ThemeProperty.IMAGES_BUNDLE);

	/**
	 * Crossreference <code>ThemeProperty</code> to
	 * <code>ThemeBundle</code>.
	 */
	/**
	 * The <code>ThemeProperty</code> that identifies this
	 * <code>ThemeBundle</code>
	 */
	private final ThemeProperty property;

	/**
	 * Construct a ThemeBundle</code> and associate it to 
	 * <code>property</code>
	 */
	ThemeBundle(ThemeProperty property) {
	    this.property = property;
	}

	/**
	 * Return the literal property as specified in a
	 * <code>Properties</code> file.
	 */
	public String getKey() {
	    return property.getKey();
	}

	/**
	 * Return the <code>ThemeProperty</code> property for this
	 * <code>ThemeBundle</code>.
	 */
	public ThemeProperty getProperty() {
	    return property;
	}
	/**
	 * Return the <code>ThemeBundle</code> for <code>key</code>.
	 */
	public static ThemeBundle toThemeBundle(String key) {
	    for (ThemeBundle b : values()) {
		if (b.property.getKey().equals(key)) {
		    return b;
		}
	    }
	    return null;
	}

    }

    /**
     * Define all the resource components of a <code>ThemeResourceBundle</code>.
     */
    enum ThemeResource {
	THEME_PROPERTIES(ThemeProperty.values()),
	THEME_BUNDLES(ThemeBundle.values());

	private ThemeProperty[] properties;
	private ThemeBundle[] bundles;

	ThemeResource(ThemeProperty[] properties) {
	    this.properties = properties;
	}
	ThemeResource(ThemeBundle[] bundles) {
	    this.bundles = bundles;
	}
    }

}

/**
 * The <code>ThemeResources</code> class encapsulates
 * <code>ThemeReference</code>'s and associates a
 * <code>ThemeContext</code>, a <code>Locale</code>, a theme name and
 * theme version with those <code>ThemeReferences</code>.
 * <p>
 * The <code>ResourceBundleTheme</code> implementation obtains theme property 
 * values from the basenames identified by the <code>ThemeReferences</code>
 * for the associated <code>Locale</code>. 
 * </p>
 * @see com.sun.webui.theme.ThemeContext
 * @see com.sun.webui.theme.ThemeReference
 */
class ThemeResources {

    /**
     * Provides the data to obtain resource bundles for a particular theme.
     * @see com.sun.webui.theme.ThemeReference
     */
    private ThemeReference[] themeReferences;
    /**
     * The locale to use when obtaining a theme <code>ResourceBundle</code>
     * identified in <code>themeReferences</code>.
     * @see java.util.ResourceBundle
     * @see com.sun.webui.theme.ThemeReference
     */
    private Locale locale;
    /**
     * The theme version implemented by these theme resources.
     */
    private String version;
    /**
     * The theme name of these theme resources.
     */
    private String name;
    /**
     * The runtime theme context.
     * @see com.sun.webui.theme.ThemeContext
     */
    private ThemeContext themeContext;

    /**
     * Create a <code>ThemeResources</code> instance.
     * @see com.sun.webui.theme.ThemeContext
     * @see com.sun.webui.theme.ThemeReference
     * 
     * @param themeReferences identifies the available resource bundles.
     * @param locale the preferred <code>Locale</code> to use when
     * obtaining a theme <code>ResourceBundle</code>.
     * @param themeContext the runtime theme context.
     */
    ThemeResources(ThemeReference[] themeReferences,
	    String themeName, String version, Locale locale,
	    ThemeContext themeContext) {
	this.themeReferences = themeReferences;
	this.themeContext = themeContext;
	this.locale = locale;
	this.version = version;
	this.name = themeName;
    }

    /**
     * Returns the <code>themeReferences</code> member.
     * @see com.sun.webui.theme.ThemeReference
     */
    ThemeReference[] getThemeReferences() {
       return themeReferences;
    }

    /**
     * Returns the <code>themeContext</code> member.
     * @see com.sun.webui.theme.ThemeContext
     */
    ThemeContext getThemeContext() {
       return themeContext;
    }

    /**
     * Returns the <code>locale</code> member.
     * The preferred <code>Locale</code> to use when obtaining
     * a theme <code>{@link java.util.ResourceBundle}</code>.
     */
    Locale getLocale() {
       return locale;
    }

    /**
     * Returns the theme <code>name</code> member.
     */
    String getName() {
       return name;
    }

    /**
     * Returns the theme <code>version</code> member.
     */
    String getVersion() {
       return version;
    }

    /**
     * Return a <code>ThemeReference</code> iterator.
     * If <code>reverse</code> is true iterate from lowest
     * precedence to highest precedence, otherwise iterate in the
     * order of precedence; low index to higheer indices.<br/>
     * ThemeReferences are stored in the order of precedence. But that
     * may not be optimal for some theme requests. For cases where
     * only the first instance of a theme property is required an
     * iterator that iterates in precedence ordering is required.
     * In cases where an array of all instances of a theme property
     * is desired then it may be the case where you want to deliver
     * the property values in the reverse order of precedence.<br/>
     * For exmaple when obtaining all style sheets, you want the 
     * style sheet with the highest precedence to be loaded last in
     * the page, so a list of style sheets ordered in that fashion
     * is more convenient.
     */
    Iterator<ThemeReference> getThemeReferenceIterator(boolean reverse) {
	if (themeReferences == null) {
	    return getEmptyIterator();
	}
	if (!reverse) {
	    return getThemeReferenceIterator();
	}
	final int tempindex = themeReferences.length - 1;
	return new Iterator<ThemeReference>() {
	    int index = tempindex;
	    public boolean hasNext() {
		return index >= 0;
	    }
	    public ThemeReference next() {
		try {
		    return themeReferences[index--];
		} catch (Exception e) {
		    throw new NoSuchElementException();
		}
	    }
	    public void remove() {
		throw new UnsupportedOperationException();
	    }
	};
    }

    /**
     * Return a <code>ThemeReference</code> iterator.
     * Iterate over ThemeReferences in order of precedence.
     */
    Iterator<ThemeReference> getThemeReferenceIterator() {
	if (themeReferences == null) {
	    return getEmptyIterator();
	}
	return new Iterator<ThemeReference>() {
	    int index = 0;
	    public boolean hasNext() {
		return index < themeReferences.length;
	    }
	    public ThemeReference next() {
		try {
		    return themeReferences[index++];
		} catch (Exception e) {
		    throw new NoSuchElementException();
		}
	    }
	    public void remove() {
		throw new UnsupportedOperationException();
	    }
	};
    }

    private Iterator<ThemeReference> getEmptyIterator() {
	return new Iterator<ThemeReference>() {
	    public boolean hasNext() {
		return false;
	    }
	    public ThemeReference next() {
		throw new NoSuchElementException();
	    }
	    public void remove() {
		throw new UnsupportedOperationException();
	    }
	};
    }
}

/**
 * The <code>ThemeReference</code> class associates a 
 * <code>ResourceBundle</code> basename with a particular 
 * <code>ClassLoader</code>. This may be necessary because the 
 * <code>ResourceBundle</code> identified by <code>basename</code>
 * may exist in a jar or may exist in many jars. This association is used to
 * ensure that a given <code>ResourceBundle</code> is obtained in a specific
 * order and not by its appearance on the application's classpath.
 * <p>
 * Note that in a servlet environment this feature may not be that useful
 * or even possible. The motivation for associating a ThemeReference with a
 * specific classloader attempted to provide a mechanism to defeat the
 * constraint in <code>ResourceBundle</code> that restricts obtaining
 * more than one bundle with the same basename. This can happen if two
 * or more jars on the classpath have a ResourceBundle or properties file with
 * the same basename. The idea was to create a URLClassLoader that contained
 * only a specific jar in its classpath. This would allow 
 * <code>ResourceBundle.getBundle()</code>
 * to be used to obtain both <code>ResourceBundle</code> class instances
 * or <code>Properties</code> files using
 * separate calls to ResourceBundle.getBundle, passing the associated
 * classloader.
 * </p>
 */
class ThemeReference {

    /**
     * Identifies a theme <code>ResourceBundle</code>.
     */
    String basename;
    /**
     * The <code>ClassLoader</code> to use when obtaining 
     * the <code>ResourceBundle</code> identified by <code>basename</code>.
     * If this member is null, the application or <code>ThemeContext</code>
     * may be used.
     */
    ClassLoader classLoader;

    /**
     * Create a <code>ThemeReference</code> instance.
     * 
     * @param basename identifies a theme <code>ResourceBundle</code>.
     * @param classLoader the <code>ClassLoader</code> to use when obtaining
     * the <code>ResourceBundle</code> identified by <code>basename</code>.
     */
    ThemeReference(String basename, ClassLoader classLoader) {
	this.basename = basename;
	this.classLoader = classLoader;
    }

    /**
     * Create a <code>ThemeReference</code> instance.
     * 
     * @param basename identifies a theme <code>ResourceBundle</code>.
     */
    ThemeReference(String basename) {
	this.basename = basename;
    }

    /**
     * Returns the <code>basename</code> member, identifying a theme
     * <code>ResourceBundle</code>.
     */
    String getBasename() {
	return basename;
    }

    /**
     * Returns the <code>classLoader</code> member, identifying the
     * <code>ClassLoader</code> to use when obtaining properties
     * from the <code>basename</code> <code>ResourceBundle</code>.
     */
    ClassLoader getClassLoader() {
	return classLoader;
    }
}

