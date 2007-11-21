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
/* $Id: ThemeContext.java,v 1.4 2007-11-21 19:20:00 danl Exp $ */

package com.sun.webui.theme;

import java.util.Locale;
import java.util.Set;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * <code>ThemeContext</code> encapsulates the runtime environment for theme.
 * The runtime environment and dictates how information, required to locate
 * theme resources, and make those resources available to an application, 
 * is obtained.
 * For example:
 * <p>
 * <ul>
 * <li>In a <code>javax.servlet.Servlet</code> application environment
 * information about a theme and its resources is specified by the
 * application in its web.xml file
 * </li>
 * <li>In a Creator design time environment
 * there is no need to prefix a theme resource reference with a theme
 * servlet prefix
 * </li>
 * <li>In a Sun Management Web Console environment that is sharing
 * resources via a specific servlet and class loader, the console determines
 * the class loader and theme servlet prefix.
 * </li>
 * <li>In a JSF environment information must be obtained from the
 * <code>FacesContext</code> which encapsulates servlet and portlet contexts.
 * </li>
 * </ul>
 * </p>
 */
public abstract class ThemeContext {

    /**
     * Identifies a theme messages bundle to override the 
     * message bundle in a theme.
     * @deprecated
     */
    public final static String THEME_MESSAGES =
		"com.sun.webui.theme.THEME_MESSAGES"; //NOI18N

    /**
     * @deprecated
     */
    private String messages;
    /**
     * @deprecated
     */
    public String getMessages() {
	return messages;
    }
    /**
     * @deprecated
     */
    public void setMessages(String messages) {
	this.messages = messages;
    }

    /**
     * Identifies a theme messages bundle to override the 
     * message bundle in a theme.
     * @deprecated
     */
    public final static String SUPPORTED_LOCALES =
		"com.sun.webui.theme.SUPPORTED_LOCALES"; //NOI18N
    /**
     * The separator for the supported locales list.
     */
    protected static final String LOCALE_SEPARATOR = ",";//NOI18N

    /**
     * @deprecated
     */
    private Set supportedLocales;
    /**
     * @deprecated
     */
    public Set getSupportedLocales() {
	return supportedLocales;
    }
    /**
     * @deprecated
     */
    public void setSupportedLocales(Set supportedLocales) {
	this.supportedLocales = supportedLocales;
    }
    
    /**
     * Intended as a key identifying a <code>ThemeContext</code> instance.
     */
    protected final static String THEME_CONTEXT =
	"com.sun.webui.theme.THEME_CONTEXT";

    /**
     * Identifies the context's default locale.
     */
    protected final static String DEFAULT_LOCALE =
	"com.sun.webui.theme.DEFAULT_LOCALE"; //NOI18N

    /**
     * Identifies the context's default theme.
     */
    protected final static String DEFAULT_THEME =
	"com.sun.webui.theme.DEFAULT_THEME"; //NOI18N

    /**
     * Identifies the context's desired theme version.
     */
    protected final static String DEFAULT_THEME_VERSION =
	"com.sun.webui.theme.DEFAULT_THEME_VERSION"; //NOI18N

    /**
     * Identifies additional theme bundles.
     */
    protected final static String THEME_RESOURCES =
	"com.sun.webui.theme.THEME_RESOURCES"; //NOI18N

    /**
     * Identifies the <code>ThemeFactory</code> class name.
     */
    protected final static String THEME_FACTORY_CLASS_NAME =
	"com.sun.webui.theme.THEME_FACTORY_CLASS_NAME"; //NOI18N

    /**
     * Identifies the <code>ThemeServlet</code> context for obtaining
     * resources via HTTP.
     */
    protected final static String THEME_SERVLET_CONTEXT =
	"com.sun.webui.theme.THEME_SERVLET_CONTEXT"; //NOI18N

    /**
     * CAN'T SUPPORT THIS YET.
     * Used to determine if a 
     * <code>{@link com.sun.webui.theme.ThemeConfigurationException}</code>
     * should be thrown when no themes are present. If set to
     * <code>true</code> then a <code>ThemeConfigurationException exception
     * will be thrown if no themes are present. The default is 
     * <code>false</code>
    protected final static String THROW_EXCEPTION_ON_NO_THEMES =
	"com.sun.webui.theme.THROW_EXCEPTION_ON_NO_THEMES"; //NOI18N
     */

    /**
     * The default locale for the default theme in this
     * <code>ThemeContext</code>.
     */
    private Locale defaultLocale = Locale.getDefault();

    // Should be configurable in the environment like a system property
    // but not only as a compile time constant.
    //
    /**
     * The name of the default theme for this <code>ThemeContext</code>.
     * If a requested resource cannot be found in a specified theme
     * then the default theme will be used to obtain that resource.
     */
    private String defaultTheme = null;

    // Need to firm up versioning semantics. Does specification version
    // and implementation make more sense than Major/Minor ?
    //
    // Should also be configurable from the runtime environment
    // as system property, versus just a compiled default.
    //
    /**
     * If more than one version of the default theme exists, the
     * theme instance with version equal to <code>defaultThemeVersion</code>
     * will be used to obtain theme resources.
     */
    private String defaultThemeVersion = null;
    /**
     * The absolute portion of the theme servlet's servlet-mapping
     * url-pattern element. For example if the theme servlet is configured as:
     * <p>
     * <code>
     * <pre>
     *     &lt;servlet-mapping&gt;
     *         &lt;servlet-name&gt;FacesServlet&lt;/servlet-name&gt;
     *         &lt;url-pattern&gt;faces/*&lt;/url-pattern&gt;
     *     &lt;/servlet-mapping&gt;
     * </pre>
     * </code>
     * </p>
     * <p>
     * Then the value of <code>themeServletContext</code> would be
     * <code>theme</code>.
     * </p>
     * <p>
     * When trying to locate a specifc theme resource this prefix is
     * prepending to the theme resource reference to locate the resource.
     * </p>
     */
    private String themeServletContext;

    /* CAN'T SUPPORT THIS YET
     * If <code>true</code> <code>ThemeConfigurationException</code>
     * should be thrown if no themes are found; the default value is
     * <code>false</code>
    private boolean throwExceptionOnNoThemes = false;
     */

    // Probably want a different name so as not to get confused
    // with theme.ThemeResources.
    //
    /**
     * Bundle names of theme resources that augment a core theme.
     * These resources typically contain theme overrides and are referenced
     * first before a default or core theme.
     */
    private String[] themeResources;

    /**
     * The class name of a <code>ThemeFactory</code> implementation.
     * This class will be used to instantiate an instance of a
     * <code>ThemeFactory</code>.
     */
    private String themeFactoryClassName;

    /**
     * The <code>ClassLoader</code> that this <code>ThemeContext</code>
     * should use when obtaining resources.
     */
    private ClassLoader defaultClassLoader;

    /**
     * The application context path as a path prefix that is prepended to 
     * theme resource path references.
     */
    private String requestContextPath;

    // Should be an interface.
    //
    private ThemeFactory themeFactory;

    /**
     * Construct a <code>ThemeContext</code>.
     */
    ThemeContext() {
    }

    public ThemeFactory getThemeFactory() {
	if (themeFactory == null) {
	    synchronized(this) {
		if (themeFactory == null) {
		    try {
			themeFactory = (ThemeFactory)
			    Class.forName(getThemeFactoryClassName()).
				newInstance();
		    } catch (Exception e) {
			return (ThemeFactory)new SPIThemeFactory();
		    }
		}
	    }
	}
	return themeFactory;
    }

    /**
     * Return bundle names of theme resources that augment a core theme.
     * These resources typically contain theme overrides and are referenced
     * first before a default or core theme.
     */
    public String[] getThemeResources() {
	return themeResources;
    }
    /**
     * Set the bundle names of theme resources that augment a core theme.
     * These resources typically contain theme overrides and are referenced
     * first before a default or core theme.
     */
    public void setThemeResources(String[] themeResources) {
	this.themeResources = themeResources;
    }

    // Could this be a "strategy" object instance vs. just a value ?
    // 
    /**
     * Return the  application context path as a prefix that is prepended to 
     * theme resource path references.
     */
    public String getRequestContextPath() {
	return requestContextPath;
    }

    /**
     * Set the application context path prefix that is prepended to 
     * theme resource path references.
     */
    public void setRequestContextPath(String requestContextPath) {
	this.requestContextPath = requestContextPath;
    }

    /**
     * Return a path used as a prefix that is prepended to a
     * a theme resource path reference.
     * This implementation returns
     * <code>getRequestContextPath() + getThemeServletContext()</code>.
     */
    public String getResourcePath(String path) {
	return getRequestContextPath() + getThemeServletContext() + 
	    (String)(path.startsWith("/") ? "/" + path : path);
    }

    /**
     * Set the default locale for the themes in this <code>ThemeContext</code>.
     */
    public void setDefaultLocale(Locale defaultLocale) {
	this.defaultLocale = defaultLocale;
    }
    /**
     * Set the default locale for the themes in this <code>ThemeContext</code>.
     */
    public void setDefaultLocale(String defaultLocale) {
	this.defaultLocale = getLocale(defaultLocale);
    }

    /**
     * Return the default locale for thee themes in this
     * <code>ThemeContext</code>.
     */
    public Locale getDefaultLocale() {
	return defaultLocale;
    }

    /**
     * Return the <code>ClassLoader</code> that this <code>ThemeContext</code>
     * should use when obtaining resources.
     */
    public ClassLoader getDefaultClassLoader() {
	return defaultClassLoader == null ? this.getClass().getClassLoader() :
		defaultClassLoader;
    }

    /**
     * Set the <code>ClassLoader</code> that this <code>ThemeContext</code>
     * should use when obtaining resources.
     */
    public void setDefaultClassLoader(ClassLoader defaultClassLoader) {
	this.defaultClassLoader = defaultClassLoader;
    }

    /**
     * Return the class name of a <code>ThemeFactory</code> implementation.
     * This class will be used to instantiate an instance of a
     * <code>ThemeFactory</code>.
     */
    public String getThemeFactoryClassName() {
	return themeFactoryClassName;
    }

    /**
     * Set the class name of a <code>ThemeFactory</code> implementation.
     * This class will be used to instantiate an instance of a
     * <code>ThemeFactory</code>.
     */
    public void setThemeFactoryClassName(String themeFactoryClassName) {
	this.themeFactoryClassName = themeFactoryClassName;
    }

    /**
     * Return the the name of the default theme.
     * If a requested resource cannot be found in a specified theme
     * then the default theme will be used to obtain that resource.
     */
    public String getDefaultTheme() {
	return defaultTheme;
    }

    /**
     * Set the default theme name for this <code>ThemeContext</code>.
     * If a requested resource cannot be found in a specified theme
     * then the default theme will be used to obtain that resource.
     */
    public void setDefaultTheme(String defaultTheme) {
	this.defaultTheme = defaultTheme;
    }

    /**
     * Return the version of the default theme.
     * If more than one version of the default theme exists, the
     * theme instance with version equal to <code>defaultThemeVersion</code>
     * will be used to obtain theme resources.
     */
    public String getDefaultThemeVersion() {
	return defaultThemeVersion;
    }

    /**
     * Set the version of the default theme.
     * If more than one version of the default theme exists, the
     * <code>defaultThemeVersion</code> will be used to obtain 
     * <code>Theme</code> resources.
     */
    public void setDefaultThemeVersion(String defaultThemeVersion) {
	this.defaultThemeVersion = defaultThemeVersion;
    }
	
    /**
     * Return a path prefix of a theme resource.
     * When trying to locate a specifc theme resource this prefix is
     * prepending to the theme resource identifier to locate the resource.
     * It is the same as the ThemeServlet's url-pattern, less any
     * "/*" specification.
     */
    public String getThemeServletContext() {
	return themeServletContext;
    }

    /**
     * Set a path prefix of a theme resource.
     * When trying to locate a specifc theme resource this prefix is
     * prepending to the theme resource identifier to locate the resource.
     * It is the same as the ThemeServlet's url-pattern, less any
     * "/*" specification.
     */
    public void setThemeServletContext(String themeServletContext) {
	this.themeServletContext = themeServletContext;
    }

    /* CAN'T SUPPORT THIS YET
     * Returns <code>true</code> if configured to throw
     * <code>{@link com.sun.webui.theme.ThemeConfigurationException}</code>
     * when no themes are found; default value is false.
     * If <code>true</code> then an exception should be thrown if:
     * <ul>
     * <li>No themes are found.</li>
     * <li>An explicit requested theme or theme version is not found</li>
     * </ul>
     * <p>
     * Note that typically a
     * <code>{@link com.sun.webui.theme.ThemeFactory}</code>
     * implementation will be responsible for implementing this policy.
     * </p>
    public boolean isThrowExceptionOnNoThemes() {
	return throwExceptionOnNoThemes;
    }
     */

    /* CAN'T SUPPORT THIS YET
     * If <code>throwExceptionOnNoThemes</code> is <code>true</code> then
     * <code>{@link com.sun.webui.theme.ThemeConfigurationException}</code>
     * should be thrown when no themes are found; default value is false.
     * If <code>true</code> then an exception should be thrown if:
     * <ul>
     * <li>No themes are found.</li>
     * <li>An explicit requested theme or theme version is not found</li>
     * </ul>
     * <p>
     * Note that typically a
     * <code>{@link com.sun.webui.theme.ThemeFactory}</code>
     * implementation will be responsible for implementing this policy.
     * </p>
    public void setThrowExceptionOnNoThemes(boolean throwExceptionOnNoThemes) {
	this.throwExceptionOnNoThemes = throwExceptionOnNoThemes;
    }
     */

    /**
     * If no version can be identified from one of the version
     * methods, this constant is returned.
     */
    public static final int NOVERSION = -1;

    /**
     * Flags for obtaining major and minor version components.
     */
    private enum Version { MAJOR, MINOR };

    // Need to escape "." because the string is used as a regular expression.
    /**
     * Version separator.
     */
    private static String DOT = "\\.";

    // This also doesn't make sense in the presence of more than
    // one theme. Consider this an implementation detail for an
    // application, theme validation feature.
    // Or one of these is needed for every theme that exists in the
    // context. Is there one theme context for each theme ?
    //

    /**
     * Return the major version of the default theme version or 
     * <code>ThemeContext.NOVERSION</code>.
     * @deprecated
     */
    public int getDefaultThemeMajorVersion() {
	return getVersionNumber(Version.MAJOR);
    }

    /**
     * Return the minor version of the default theme version or 
     * <code>ThemeContext.NOVERSION</code>.
     * @deprecated
     */
    public int getDefaultThemeMinorVersion() {
	return getVersionNumber(Version.MINOR);
    }

    /**
     * The theme version format is "MAJOR.MINOR" where
     * MAJOR and MINOR are integers
     * @deprecated
     */
    private int getVersionNumber(Version majorOrMinor) {
	int version = NOVERSION;
	if (getDefaultThemeVersion() == null) {
	    return NOVERSION;
	}
	try {
	    String[] majmin = getDefaultThemeVersion().split(DOT);
	    switch(majorOrMinor) {
	    case MAJOR:
		version = Integer.parseInt(majmin[0]);
	    break;
	    case MINOR:
		version = Integer.parseInt(majmin[1]);
	    break;
	    }
	} catch (Exception e) {
	    //Log error
	}
	return version;
    }

    /**
     * Return a <code>Locale</code> from a <code>String</code>
     * of the form <code>language[_country[_variant]]</code>.
     */
    private Locale getLocale(String localeString) {
	if (localeString == null) {
	    return null;
	}
	localeString = localeString.trim();
	if (localeString.length() == 0) {
	    return null;
	}
	Locale locale = null;
	String[] strings = localeString.split("_"); 
	if (strings.length > 2) { 
	    locale = new Locale(strings[0], strings[1], strings[2]); 
	} 
	else if (strings.length > 1) { 
	    locale = new Locale(strings[0], strings[1]); 
	} 
	else if (strings.length > 0) { 
	    locale = new Locale(strings[0]); 
	}
	return locale;
    }
}
