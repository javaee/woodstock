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

        // We really don't want interfaces like this. They must be
	// more formal. In order to have this functionality the
	// framework should implement ThemeContext which allows
	// it to control the Theme name and ThemeFactory.
	//

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

	// Need to be clever here.
	// This version is only useful for this jar of components.
	// ThemeUtilities since it is embedded with this set of components
	// should not be used by external components sets, since the 
	// theme version may not be the same. 
	// Although we have avoided passing the component instance
	// in the getTheme call, this may be the only sure way to 
	// determine the theme required by this component, and it is
	// actually more dependent on the renderer than the component.
	// Therefore the parameter passed must be of type "Themed" or
	// "ThemeAble" and asked for its version.
	// Since all components and renderers are "Themed" it is probably
	// better to just pass the version to the "getTheme" call,
	// since it is the caller that knows.
	//
	// In reality, the version really is a fruitless attempt to
	// do something smart but at this point in the runtime
	// environment, there isn't much one can do smartly or stupidly.
	// The theme name should be sufficient.
	//
	theme = themeFactory.getTheme(null, "4.3", locale, themeContext);

	// Now see if this call to getTheme, set a default theme
	// as a result of not theme set in ThemeContext and the
	// decision was left to ThemeFactory.
	// if THEME_ATTR was null
	//
	if (themeName == null) {
	    // Hack -  we want this will go away.
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
       
	if (themeImage != null) {
	    icon.setUrl(themeImage.getPath());
	    icon.setAlt(themeImage.getAlt());
	    icon.setHeight(themeImage.getHeight());
	    icon.setWidth(themeImage.getWidth());
	}

        return icon;
    }
}
