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
/* $Id: ServletThemeContext.java,v 1.2 2007-04-03 00:25:46 rratta Exp $ */

package com.sun.webui.theme;

import java.util.Locale;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import com.sun.webui.theme.ThemeContext;

/**
 * The Servlet implementation of <code>ThemeContext</code>.
 * This implementation uses the Servlet runtime environment to persist an
 * instance of a <code>ThemeContext/code> as a <code>ServletContext</code>
 * attribute.
 * <p>
 * A <code>ServletThemeContext</code> instance is created based on the 
 * context-param values, defined as <code>ThemeContext</code> constants.
 * </p>
 * <p>
 * <ul>
 * <li><code>ThemeContext.DEFAULT_LOCALE</code> - defines the
 * default locale for this application.(optional)</li>
 * <li><code>ThemeContext.DEFAULT_THEME</code> - define the theme name
 * for the theme that this application may depend on.(optional)</li>
 * <li><code>ThemeContext.DEFAULT_THEME_VERSION</code> - define the theme
 * version this application may depend on.(optional)</li>
 * <li><code>ThemeContext.THEME_RESOURECES</code> - a space separated list
 * of resources bundles containing theme resources that augment any theme
 * referenced by this application.</li>
 * <li><code>ThemeContext.THEME_FACTORY_CLASS_NAME</code> - the name
 * of the class that implements <code>com.sun.webui.theme.ThemeFactory</code>
 * to instantiate to obtain theme instances for this application.</li>
 * <li><code>ThemeContext.THEME_SERVLET_CONTEXT</code> - the value of
 * the url-pattern element of the theme servlet's servlet-mapping, less the
 * terminating "/*". The default value is "/theme" corresponding to 
 * a url pattern of "/theme/*".</li>
 * </ul>
 * </p>
 */
public class ServletThemeContext extends ThemeContext {

    /**
     * The default <code>ThemeContext.THEME_SERVLET_CONTEXT</code>.
     */
    protected static final String DEFAULT_THEME_SERVLET_CONTEXT = "/theme";

    /**
     * An object to synchronize with.
     */
    private static Object synchObj = new Object();

    /**
     * Contstuctor controlled in <code>getInstance</code> and
     * for subclasses.
     * In non servlet environments like JSF, which could be 
     * servlet or portlet, accept a <code>Map</code> containing
     * initialization parameters.
     */
    protected ServletThemeContext(Map initParamMap) {
	super();
	// deprecated
	String value = (String)initParamMap.get(THEME_MESSAGES);
	if (value != null) {
	    setMessages(value);
	}
	// deprecated
	value = (String)initParamMap.get(SUPPORTED_LOCALES);
	if (value != null) {
	    setSupportedLocales(getLocales(value));
	}
	value = (String)initParamMap.get(DEFAULT_THEME);
	if (value != null) {
	    setDefaultTheme(value);
	}
	value = (String)initParamMap.get(DEFAULT_THEME_VERSION);
	if (value != null) {
	    setDefaultThemeVersion(value);
	}
	value = (String)initParamMap.get(THEME_RESOURCES);
	if (value != null) {
	    setThemeResources(value.trim().split(" "));
	}
	value = (String)initParamMap.get(DEFAULT_LOCALE);
	if (value != null) {
	    setDefaultLocale(value);
	}
	value = (String)initParamMap.get(THEME_FACTORY_CLASS_NAME);
	if (value != null) {
	    setThemeFactoryClassName(value);
	}
	// This must be the same as the ThemeServlet's context
	//
	value = (String)initParamMap.get(THEME_SERVLET_CONTEXT);
	if (value == null) {
	    value = DEFAULT_THEME_SERVLET_CONTEXT;
	}
	setThemeServletContext(value);
    }

    /**
     * Constructor controlled in <code>getInstance</code>.
     */
    protected ServletThemeContext(ServletContext context) {
	super();
	// deprecated
	String value = (String)context.getInitParameter(THEME_MESSAGES);
	if (value != null) {
	    setMessages(value);
	}
	// deprecated
	value = (String)context.getInitParameter(SUPPORTED_LOCALES);
	if (value != null) {
	    setSupportedLocales(getLocales(value));
	}
	value = (String)context.getInitParameter(DEFAULT_THEME);
	if (value != null) {
	    setDefaultTheme(value);
	}
	value = (String)context.getInitParameter(DEFAULT_THEME_VERSION);
	if (value != null) {
	    setDefaultThemeVersion(value);
	}
	value = (String)context.getInitParameter(THEME_RESOURCES);
	if (value != null) {
	    setThemeResources(value.trim().split(" "));
	}
	value = (String)context.getInitParameter(DEFAULT_LOCALE);
	if (value != null) {
	    setDefaultLocale(value);
	}
	value = (String)context.getInitParameter(THEME_FACTORY_CLASS_NAME);
	if (value != null) {
	    setThemeFactoryClassName(value);
	}
	// Not sure why this is needed.
	//
	value = (String)context.getInitParameter(THEME_SERVLET_CONTEXT);
	if (value == null) {
	    value = DEFAULT_THEME_SERVLET_CONTEXT;
	}
	setThemeServletContext(value);
    }

    /**
     * Return an instance of <code>ThemeContext</code>.
     */
    public static ThemeContext getInstance(ServletContext context) {

	// The JSF ApplicationMap is the ServletContext and therefore
	// objects set in the JSF ApplicationMap use "getAttribute" and
	// "setAttribute" on ServletContext.
	//
	// Should there be a JSFThemeServlet to complement the 
	// JSFThemeContext's use of "ApplicationMap" ?
	//
	// I think we need synchronization here.
	//
	ThemeContext themeContext =
		(ThemeContext)context.getAttribute(THEME_CONTEXT);
	if (themeContext == null) {
	    synchronized(synchObj) {
		// Need to make sure another thread didn't just finish
		//
		themeContext = (ThemeContext)
			context.getAttribute(THEME_CONTEXT);
		if (themeContext == null) {
		    themeContext = new ServletThemeContext(context);
		    context.setAttribute(THEME_CONTEXT, themeContext);
		}
	    }
	}
	return themeContext;
    }

    /** 
     * Determines the set of supported locales.
     * @return set containing the support locales.
     */
    private Set getLocales(String locales) {
    
        String[] localeArray = locales.split(LOCALE_SEPARATOR);
        Set localeSet = new HashSet(); 

        
        for(int counter = 0; counter < localeArray.length; ++counter) { 

            String localeString = localeArray[counter].trim();
            if (localeString.length() == 0) { 
                continue; 
            } 

	    Locale locale = null; 
       
	    // The basename cannot have underscore in it.
            String[] strings = localeString.split("_"); //NOI18N
            if(strings.length > 2) { 
                locale = new Locale(strings[0], strings[1], strings[2]); 
            } 
            else if (strings.length > 1) { 
                locale = new Locale(strings[0], strings[1]); 
            } 
            else if(strings.length > 0) { 
                locale = new Locale(strings[0]); 
            } 
            localeSet.add(locale);
        }        
        return localeSet;
    }  
}
