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

/*
 * ThemeUtilities.java
 *
 * Created on January 11, 2005, 2:15 PM
 */

package com.sun.webui.jsf.util;

import java.util.Locale;
import java.util.Map;
//import java.util.MissingResourceException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.theme.JSFThemeContext;

import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeContext;
import com.sun.webui.theme.ThemeFactory;
import com.sun.webui.theme.ThemeImage;

/**
 * Utilities needed by Sun Web Components to 
 * retrieve an appropriate Theme.
 * @author avk
 */
public class ThemeUtilities {
    
    /**
     * Defines the attribute in the <code>RequestMap</code> for
     * caching the theme.
     */
    private static final String JSFTHEME = "com.sun.webui.jsf.theme.THEME";

    /**
     * Return the default <code>Theme</code> instance.
     * <code>getTheme</code> first looks in the request map
     * for an instance. If an instance does not exist in the
     * request map, obtain an instance from the
     * <code>ThemeFactory</code>. If the theme is obtained from
     * the <code>ThemeFactory</code> place it in the request map.
     */
    public static Theme getTheme(FacesContext context) { 

	// optimization
	// Assumptions include that the locale is not changing
	// within a request for the default theme.
	// It is also assumed that there is a single thread of 
	// execution during a request especially code that 
	// might be calling "getTheme".
	//
	Theme theme = (Theme)
	    context.getExternalContext().getRequestMap().get(JSFTHEME);
	if (theme != null) {
	    return theme;
	}
	Locale locale = context.getViewRoot().getLocale();

	/*
	long mem = Runtime.getRuntime().freeMemory();
	long elapsed = System.currentTimeMillis();
	*/

	// Restore support for "THEME_ATTR" session theme name.
	// Note that there really is no official support for
	// changing a theme during a user session, but this code
	// was in the original implementation, so provide the 
	// same feature now. But this has to be formalized.
	// The framework should be able to control the lifecycle 
	// and scope of a ThemeContext and manipulate it to reflect
	// user Session scoped information and servlet scoped information.
	// hardcoded reference to the defined constant in creator
	// ./designer/src/com/sun/rave/designer/RefreshServiceProvider.java
	//
	String themeName = null;
	Map sessionAttributes = 
	    context.getExternalContext().getSessionMap();
	Object themeObject = sessionAttributes.get(Theme.THEME_ATTR);
	if (themeObject != null) {
	    themeName = themeObject.toString().trim();
	}
    
	ThemeContext themeContext = JSFThemeContext.getInstance(context);

	// We must ensure that a theme instance is always returned.
	//
	ThemeFactory themeFactory = themeContext.getThemeFactory();
	theme = themeFactory.getTheme(themeName, locale, themeContext);

	// Now see if this call to getTheme, set a default theme
	// as a result of not theme set in ThemeContext and the
	// decision was left to ThemeFactory.
	// if THEME_ATTR was null
	//
	if (themeName == null) {
	    // Hack - this will go away.
	    // 
	    themeName = themeFactory.getDefaultThemeName(themeContext);
	    if (themeName != null) {
		sessionAttributes.put(Theme.THEME_ATTR, themeName);
	    }
	}

	/*
	elapsed = System.currentTimeMillis() - elapsed;
	mem  = mem - Runtime.getRuntime().freeMemory();
	*/

	context.getExternalContext().getRequestMap().put(JSFTHEME, theme);

	return theme;
    }

    /**
     * Return an <code>Icon</code> component for the
     * <code>iconKey</code>.
     */
    public static Icon getIcon(Theme theme, String iconKey) {

	Icon icon = new Icon(); 
	icon.setIcon(iconKey);  
	if (iconKey == null) {
	    return icon;
	}
           
	ThemeImage themeImage = null;
	// Original behavior let the RuntimeException thru
	//try {

	    themeImage = theme.getImage(iconKey);

	//} catch (MissingResourceException mre) {
	//    return icon;
	//}

	// make sure to setIcon on parent and not the icon itself (which
	// now does the theme stuff in the component
       
	icon.setUrl(themeImage.getPath());
	icon.setAlt(themeImage.getAlt());
	icon.setHeight(themeImage.getHeight());
	icon.setWidth(themeImage.getWidth());

        return icon;
    }
}
