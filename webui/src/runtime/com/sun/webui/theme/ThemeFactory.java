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

import java.util.Locale;

/**
 * <p>Factory class responsible for setting up the Sun Web Component
 * application's ThemeManager.</p>
 */
public interface ThemeFactory {

    // Private attribute names
    public final static String MANIFEST = "META-INF/MANIFEST.MF"; //NOI18N
    public final static String FILENAME = "manifest-file"; //NOI18N
    public final static String COMPONENTS_SECTION = "com/sun/webui/jsf/"; //NOI18N
    public final static String THEME_SECTION = "com/sun/webui/jsf/theme/"; //NOI18N
    public final static String THEME_VERSION_REQUIRED =
            "X-SJWUIC-Theme-Version-Required"; //NOI18N
    public final static String THEME_VERSION = "X-SJWUIC-Theme-Version"; //NOI18N
    public final static String NAME = "X-SJWUIC-Theme-Name"; //NOI18N
    public final static String PREFIX = "X-SJWUIC-Theme-Prefix"; //NOI18N
    public final static String DEFAULT = "X-SJWUIC-Theme-Default"; //NOI18N
    public final static String STYLESHEETS = "X-SJWUIC-Theme-Stylesheets"; //NOI18N
    public final static String JSFILES = "X-SJWUIC-Theme-JavaScript"; //NOI18N
    public final static String CLASSMAPPER = "X-SJWUIC-Theme-ClassMapper"; //NOI18N
    public final static String IMAGES = "X-SJWUIC-Theme-Images"; //NOI18N
    public final static String MESSAGES = "X-SJWUIC-Theme-Messages"; //NOI18N
    public final static String TEMPLATES = "X-SJWUIC-Theme-Templates"; //NOI18N

    /**
     * Return the default <code>Theme</code> for
     * <code>locale</code> within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(Locale locale, ThemeContext themeContext);

    /**
     * Return the <code>themeName</code> <code>Theme</code> for
     * <code>locale</code> within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(String themeName, Locale locale,
            ThemeContext themeContext);

    /**
     * Hack - this will go away
     */
    public String getDefaultThemeName(ThemeContext themeContext);
}
