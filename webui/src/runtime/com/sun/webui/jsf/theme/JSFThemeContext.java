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
 * $Id: JSFThemeContext.java,v 1.5 2007-10-10 16:37:43 dcao Exp $
 */

package com.sun.webui.jsf.theme;

import java.beans.Beans;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import com.sun.webui.theme.ServletThemeContext;
import com.sun.webui.theme.ThemeContext;

/**
 * <code>JSFThemeContext</code> encapsulates the theme JSF runtime
 * environment. It is different from other potential runtime environments
 * in that JSF encapsulates runtime contexts in a <code>FacesContext</code>.
 * This context could encapsulate a servlet context or a portlet context.
 * As such application information affecting a theme may be obtained
 * differently. This class encapsulates that behavior.
 */
public class JSFThemeContext extends ServletThemeContext {

    /**
     * An object to synchronize with.
     */
    private static Object synchObj = new Object();

    /**
     * Construction is controlled by <code>getInstance</code>.
     */
    protected JSFThemeContext(FacesContext context) {
	super(context.getExternalContext().getInitParameterMap());
    }

    // Note that since a ThemeServlet MUST be defined then 
    // getInstance should never have to create a ThemeContext instance.
    //
    /**
     * Return an instance of <code>ThemeContext</code> creating one
     * if necessary and persisting it in the <code>ApplicationMap</code>.
     */
    public static ThemeContext getInstance(FacesContext context) {

	// Does it make sense call a "super.getInstance()" ?
	//
	// In a servlet environment the JSF ApplicationMap is the 
	// ServletContext and entires in the map are references by
	// ServletContext.{get/set}Attribute().
	// But a PortletContext is not a direct descendant of 
	// ServletContext and there is not "interface compatible".
	//
	// We need synchronization here because there is one
	// ThemeContext per application servlet.
	//
	Map map = context.getExternalContext().getApplicationMap();
	ThemeContext themeContext = (ThemeContext)map.get(THEME_CONTEXT);
	if (themeContext == null) {
	    synchronized(synchObj) {
		// try again in case another thread created it.
		//
		themeContext = (ThemeContext)map.get(THEME_CONTEXT);
		if (themeContext == null) {
		    themeContext = new JSFThemeContext(context);
		    map.put(THEME_CONTEXT, themeContext);
		}
	    }
	}
	// It's not clear if this is necessary. Since this is a
	// JSFThemeContext, it would not be unreasonable to just
	// call "FacesContext.getCurrentInstance()" whenever 
	// a value from that context is required like the 
	// request context path, or referenced when calling
	// "ThemeContext.getResourcePath()". 
	//
	//String path = context.getExternalContext().getRequestContextPath();
	//themeContext.setRequestContextPath(path);

	return themeContext;
    }

    /**
     * Return the default ClassLoader.
     */
    public ClassLoader getDefaultClassLoader() {
	return(Thread.currentThread().getContextClassLoader());
    }
    /**
     * This implementation is a no-op.
     */
    public void setDefaultClassLoader(ClassLoader classLoader) {
    }

    /**
     * This implementation always returns 
     * <code>FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()</code>.
     * This depends on the implementation of
     * <code>{@link com.sun.webui.theme.ThemeContext#getResourcePath}</code>
     */
    public String getRequestContextPath() {
	 return FacesContext.getCurrentInstance().getExternalContext().
		getRequestContextPath();
    }
    /**
     * This implementation is a no-op.
     * @see #getRequestContextPath()
     */
    public void setRequestContextPath(String path) {
    }

    /**
     * Return an appropriate resource path within this theme context.
     * Use is Beans.isDesignTime to prevent ThemeServlet prefix from
     * being appended. Creator handles doing the right thing with 
     * getRequestContextPath()
     */
    public String getResourcePath(String path) {
        String resourcePath = path;
	if (Beans.isDesignTime()) {
            if (path != null && !path.equals("")) {
                ClassLoader cl = getDefaultClassLoader();
                // NB6 gives warnings if the path has a leading "/". So, strip it off if it has one
                URL url = cl.getResource(path.startsWith("/") ? path.substring(1) : path);
                if (url != null) {
                    resourcePath = url.toExternalForm();
                }
            }
	} else if (path != null) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    String servletContext = getThemeServletContext();
	    StringBuilder sb = new StringBuilder(128);
	    // Just to make sure 
	    //
	    if (!servletContext.startsWith("/")) {
		sb.append("/");
	    }
	    sb.append(servletContext);
	    if (!path.startsWith("/") && !servletContext.endsWith("/")) {
		sb.append("/");
	    }
	    sb.append(path);
	    resourcePath = context.getApplication().getViewHandler().
		getResourceURL(context, sb.toString());
	}
        return resourcePath;
    }
}
