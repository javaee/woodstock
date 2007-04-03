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

import java.beans.Beans;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * The <code>ThemeFactory</code> interface defines methods that return
 * <code>{@link com.sun.webui.theme.Theme}</code> instances.
 * <p>A <code>ThemeFactory</code> also defines how theme data is provided to an
 * application. For example a jar file may be used to bundle the various
 * resources such image files, JavaScipt files, CSS style sheet files, 
 * <code>ResourceBundles</code> and/or <code>Properties</code> files.
 * </p>
 * <p>
 * A <code>ThemeFactory</code> implementation may also provide a default
 * theme policy, i.e. in the presence of multiple concrete theme
 * implementations, choose one to be the default source, if there is no
 * request for a specific theme.
 * </p>
 */
public interface ThemeFactory {
    
    // Private attribute names
    /**
     * @deprecated
     */
    public final static String MANIFEST = "META-INF/MANIFEST.MF";
    /**
     * @deprecated
     */
    public final static String FILENAME = "manifest-file";
    /**
     * @deprecated
     */
    public final static String COMPONENTS_SECTION = "com/sun/webui/jsf/";
    /**
     * @deprecated
     */
    public final static String THEME_SECTION = "com/sun/webui/jsf/theme/";
    /**
     * @deprecated
     */
    public final static String THEME_VERSION_REQUIRED = 
        "X-SJWUIC-Theme-Version-Required";
    /**
     * @deprecated
     */
    public final static String THEME_VERSION = "X-SJWUIC-Theme-Version";
    /**
     * @deprecated
     */
    public final static String NAME = "X-SJWUIC-Theme-Name";
    /**
     * @deprecated
     */
    public final static String PREFIX = "X-SJWUIC-Theme-Prefix";
    /**
     * @deprecated
     */
    public final static String DEFAULT = "X-SJWUIC-Theme-Default";
    /**
     * @deprecated
     */
    public final static String STYLESHEETS = "X-SJWUIC-Theme-Stylesheets";
    /**
     * @deprecated
     */
    public final static String JSFILES = "X-SJWUIC-Theme-JavaScript";
    /**
     * @deprecated
     */
    public final static String CLASSMAPPER = "X-SJWUIC-Theme-ClassMapper";
    /**
     * @deprecated
     */
    public final static String IMAGES = "X-SJWUIC-Theme-Images";
    /**
     * @deprecated
     */
    public final static String MESSAGES = "X-SJWUIC-Theme-Messages";
    /**
     * @deprecated
     */
    public final static String TEMPLATES = "X-SJWUIC-Theme-Templates";

    /**
     * Return the default <code>Theme</code> instance 
     * for <code>locale</code> in the <code>themeContext</code>
     * runtime environment.
     * @param locale the current <code>Locale</code> in effect.
     * @param themeContext the theme runtime environment
     * @return the name of the default theme.
     */
    public Theme getTheme(Locale locale, ThemeContext themeContext);

    /**
     * Return the <code>Theme</code> instance named <code>themeName</code> for
     * <code>locale</code> in the <code>themeContext</code> runtime environment.
     * @param themeName the name of the desired <code>Theme</code> instance.
     * @param locale the current <code>Locale</code> in effect.
     * @param themeContext the theme runtime environment
     * @return the name of the default theme.
     */
    public Theme getTheme(String themeName, Locale locale, 
	    ThemeContext themeContext);

    /**
     * Return the default <code>Theme</code> instance with version
     * <code>version</code> for <code>locale</code> in the
     * <code>themeContext</code> runtime environment.
     * @param locale the current <code>Locale</code> in effect.
     * @param version the version of the desired <code>Theme</code> instance.
     * @param themeContext the theme runtime environment
     * @return the name of the default theme.
     */
    public Theme getTheme(Locale locale, String version, 
	    ThemeContext themeContext);

    /**
     * Return the <code>Theme</code> instance named <code>themeName</code>
     * with version <code>version</code> for <code>locale</code> in the 
     * <code>themeContext</code> runtime environment.
     * @param themeName the name of the desired <code>Theme</code> instance.
     * @param version the version of the desired <code>Theme</code> instance.
     * @param locale the current <code>Locale</code> in effect.
     * @param themeContext the theme runtime environment
     * @return the name of the default theme.
     */
    public Theme getTheme(String themeName, String version, Locale locale,
	    ThemeContext themeContext);

    /**
     * Return the name of the default concrete theme this
     * <code>ThemeFactory</code> implementation will choose when no 
     * theme name is explicitly requested.
     * @param themeContext the theme runtime environment
     * @return the name of the default theme.
     */
    public String getDefaultThemeName(ThemeContext themeContext);

}
