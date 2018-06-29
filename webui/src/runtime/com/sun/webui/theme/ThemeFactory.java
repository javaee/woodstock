/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
