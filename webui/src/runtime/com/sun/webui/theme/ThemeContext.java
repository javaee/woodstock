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

/* $Id: ThemeContext.java,v 1.1.6.1 2009-12-29 05:05:17 jyeary Exp $ */
package com.sun.webui.theme;

import java.util.Locale;
import java.util.Set;

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
            //FIXME double-checked locking
            synchronized (this) {
                if (themeFactory == null) {
                    try {
                        themeFactory = (ThemeFactory) Class.forName(getThemeFactoryClassName()).
                                newInstance();
                    } catch (Exception e) {
                        // Use JarThemeFactory as the fallback default
                        // This should come from subclasses.
                        //
                        return (ThemeFactory) new JarThemeFactory();
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
                (String) (path.startsWith("/") ? "/" + path : path);
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
        return defaultClassLoader == null ? this.getClass().getClassLoader() : defaultClassLoader;
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
    /**
     * If no version can be identified from one of the version
     * methods, this constant is returned.
     */
    public static final int NOVERSION = -1;

    /**
     * Flags for obtaining major and minor version components.
     */
    private enum Version {

        MAJOR, MINOR
    };

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
     */
    public int getDefaultThemeMajorVersion() {
        return getVersionNumber(Version.MAJOR);
    }

    /**
     * Return the minor version of the default theme version or 
     * <code>ThemeContext.NOVERSION</code>.
     */
    public int getDefaultThemeMinorVersion() {
        return getVersionNumber(Version.MINOR);
    }

    /**
     * The theme version format is "MAJOR.MINOR" where
     * MAJOR and MINOR are integers
     */
    private int getVersionNumber(Version majorOrMinor) {
        int version = NOVERSION;
        if (getDefaultThemeVersion() == null) {
            return NOVERSION;
        }
        try {
            String[] majmin = getDefaultThemeVersion().split(DOT);
            switch (majorOrMinor) {
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
        } else if (strings.length > 1) {
            locale = new Locale(strings[0], strings[1]);
        } else if (strings.length > 0) {
            locale = new Locale(strings[0]);
        }
        return locale;
    }
}
