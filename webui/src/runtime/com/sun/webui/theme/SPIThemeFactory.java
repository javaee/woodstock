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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;

import sun.misc.Service;

/**
 * <code>SPIThemeFactory</code> is an implementation of
 * <code>com.sun.webui.theme.ThemeFactory</code> that manages
 * theme resources for themes implementing the
 * <code>com.sun.webui.theme.ThemeService</code> SPI interface.
 * It provides instances of 
 * <code>{@link com.sun.webui.theme.ResourceBundleTheme}</code>.
 * <p>
 * It manages <code>ThemeReference</code> instances that encapsulate
 * theme resources for a given theme.
 * </p>
 * <p>
 * This implementation relies on the existence of implementations
 * of <code>com.sun.webui.theme.ThemeService</code>. When the first
 * theme is requested, all services are located and all theme
 * references are loaded. A new instace of this implementation of
 * <code>ThemeFactory</code> must be created in order to cause
 * a reload of theme resources.
 * </p>
 * <p>
 * The implementations of <code>ThemeService</code> are expected
 * to return an array of <code>String</code>, where each element
 * is a basename identifying a <code>ResourceBundle</code>, typically
 * a properties file. This properties files must have the
 * following properties defined.<br/>
 * <br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeProperty#NAME}<br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeProperty#VERSION}<br/>
 * <br/>
 * Additionally the following properties are also normally defined.<br/>
 * <br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle#STYLESHEET}<br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle#CLASSMAPPER}<br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle#JAVASCRIPT}<br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle#MESSAGES}<br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle#TEMPLATE}<br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle#PROPERTIES}<br/>
 * {@link com.sun.webui.theme.ThemeResourceBundle.ThemeBundle#IMAGES}<br/>
 * <br/>
 * but these properties are not sought by this 
 * <code>ThemeFactory</code> implementation
 * but referenced by <code>ResourceBundleTheme</code> methods.
 */
public class SPIThemeFactory implements ThemeFactory {
    
    /**
     * This implementation determines a default
     * theme name by recording the first theme name seen from
     * themes obtained from a <code>ThemeService</code> provider.
     */
    private String defaultThemeName;
    /**
     * This implementation determines a default
     * theme version by recording the first theme version seen from
     * themes obtained from a <code>ThemeService</code> provider.
     */
    private String defaultThemeVersion;

    /**
     * This constant is used to identify theme resources supplied byu
     * the application when the properties file it has defined does not
     * contain theme name or theme version properties, meaning that
     * the values should override any theme.
     */
    private static final String DEFAULT_THEME_KEY = "APPLICATION_THEME";

    /**
     * Create <code>ThemeFactory</code> instance.
     */
    public SPIThemeFactory() {
	super();
    }

    /**
     * Return the theme name that this <code>ThemeFactory</code>
     * will use when the desired theme cannot be found.
     * <p>
     * If <code>themeContext.getDefaultTheme()</code> returns null
     * then this implementation of returned a derived default theme name.
     * The default theme name is the first theme name encountered
     * from the first available <code>com.sun.webui.theme.ThemeService</code>
     * provider. The first service provider is dependent on the 
     * classLoader and the order of jars along the classpath.
     */
    public String getDefaultThemeName(ThemeContext themeContext) {
	return themeContext.getDefaultTheme() == null ? 
		defaultThemeName : themeContext.getDefaultTheme();
    }

    // Do we want an interface that allows a class loader argument ?
    // getTheme(String themeName, Locale locale,
    //		 ThemeContext themeContext, ClassLoader classLoader) ?
    // My assumption is that the themeContext instance
    // would be tailored for this purpose.
    //

    /**
     * Return the default <code>Theme</code> for <code>locale</code>
     * within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(Locale locale, ThemeContext themeContext) {
	return getTheme(locale, null, themeContext);
    }
    /**
     * Return the default <code>Theme</code> for <code>locale</code>
     * and <code>version</code> within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(Locale locale, String version, 
		ThemeContext themeContext) {
	return getTheme(null, version, locale, themeContext);
    }

    /**
     * Return the <code>themeName</code> <code>Theme</code> for
     * <code>locale</code>
     * within the theme runtime environment of <code>themeContext</code>.
     */
    public Theme getTheme(String themeName, Locale locale, 
	    ThemeContext themeContext) {
	return getTheme(themeName, null, locale, themeContext);
    }
    /**
     * Return the <code>themeName</code> <code>Theme</code> for
     * <code>locale</code> and <code>version</code>
     * within the theme runtime environment of <code>themeContext</code>.
     */
    public Theme getTheme(String themeName, String version, Locale locale, 
	    ThemeContext themeContext) {

	ThemeResources themeResources = 
	    getThemeResources(themeName, version, locale, themeContext);

	return ResourceBundleTheme.getInstance(themeResources);
    }

    /** 
     * <code>{@link com.sun.webui.theme.ThemeReference}</code>'s mapped by
     * theme name and version, and just theme name and just version.
     */
    private Map<String,ArrayList<ThemeReference>> themeReferencesMap = 
	new HashMap<String,ArrayList<ThemeReference>>();

    /**
     * Return an array of 
     * <code>{@link com.sun.webui.theme.ThemeReference}</code> instances.
     * Each element in the array encapsulates the location of theme resources.
     * The array's order is significant.
     * This means that theme properties defined in resources at higher
     * positions in the array can be overridden by property definitions 
     * in theme resources at lower positions in the array. It also means that
     * all theme resources can contribute at runtime if a theme property
     * is defined as such. The order in which those resources are 
     * returned is reflected by the order of the returned array.
     * <p>
     * The <code>ThemeReference</code> instances are mapped based on 
     * <code>themeName</code> and <code>version</code> in the following
     * combinations.
     * </p>
     * <p>
     * <code>&lt;DEFAULT_THEME_KEY&gt;_&lt;DEFAULT_THEME_KEY&gt;</code><br/>
     * <code>&lt;themeName&gt;_&lt;DEFAULT_THEME_KEY&gt;</code><br/>
     * <code>&lt;DEFAULT_THEME_KEY&gt;_&lt;version&gt;</code><br/>
     * <code>&lt;themName&gt;_&lt;version&gt;</code><br/>
     * <code>themeName</code><br/>
     * <code>version</code><br/>
     * </p>
     * <p>
     * The <code>&lt;DEFAULT_THEME_KEY&gt;_&lt;DEFAULT_THEME_KEY&gt;</code> 
     * key indicates application defined theme resources that are not
     * specific to any theme and should override the properties in any
     * theme. Similarly for
     * <code>&lt;themeName&gt;_&lt;DEFAULT_THEME_KEY&gt;</code> and
     * <code>&lt;DEFAULT_THEME_KEY&gt;_&lt;version&gt;</code>. These are
     * application defined theme resources that take precedence over the
     * named theme or specific version respectively.
     * </p>
     * <p>
     * That means that if both <code>themeName</code> and <code>version</code>
     * are available, then only <code>ThemeReference</code> instances mapped to 
     * <code>&lt;themName&gt;_&lt;version&gt;</code><br/> are returned.<br/>
     * If only <code>themeName</code> is available then only
     * <code>ThemeReference</code> instances mapped to <code>themeName</code>
     * are returned.<br/>
     * If only <code>version</code> is available then only
     * <code>ThemeReference</code> instances mapped to <code>version</code> are
     * returned.<br/>
     * </p>
     * <p>
     * If no <code>ThemeReference</code> can be found
     * <code>ThemeConfigurationException</code> is thrown.
     */

     /* CAN'T SUPPORT THIS YET
     * If no <code>ThemeReference</code> can be found and
     * <code>themeContext.isThrowExceptionOnNoThemes() == true</code>
     * <code>ThemeConfigurationException</code> is thrown.
     */
    protected ThemeReference[] getThemeReferences(String themeName,
	    String version, ThemeContext themeContext) {

	// Optimization return null if there are no entries in the
	// map. This will be true for the firt time getTheme is
	// called and if there are no themes at all.
	//
	if (themeReferencesMap.size() == 0) {
	    return null;
	}
	// The assumption is that if the same class loader is being
	// used then the results will always be the same.
	// And we expect that given a ThemeContext it maintains
	// the same ClassLoader. If the ClassLoader changes
	// the ThemeFactory reference should be nulled out. 
	// 
	// Should this be implemented behavior in ThemeContext ?
	// Probably need a "clearThemes" in ThemeFactory or ThemeContext.
	//
	// Look for the following sets of keys in this order
	//
	// <DEFAULT_THEME_KEY>_<DEFAULT_THEME_KEY> resources. These are
	//     application resources that did not define a specific
	//     theme name or version. Then look for 
	// <themeName>_<DEFAULT_THEME_KEY>. These are application
	//     resources that defined a theme name but no version.
	// <DEFAULT_THEME_KEY>_<version>. These are application
	//     resources that defined a version but no theme name.
	// <themename>_<version> 
	// <themename>
	// <version>
	//
	StringBuilder sb = new StringBuilder();
	int i = 0;
	String[] keys = new String[4];
	// We always look for these references.
	//
	String USCORE = "_";
	sb.append(DEFAULT_THEME_KEY).append(USCORE).append(DEFAULT_THEME_KEY);
	keys[i++] = sb.toString();
	sb.setLength(0);
	if (themeName != null) {
	    sb.append(themeName).append(USCORE).append(DEFAULT_THEME_KEY);
	    keys[i++] = sb.toString();
	    sb.setLength(0);
	}
	if (version != null) {
	    sb.append(DEFAULT_THEME_KEY).append(USCORE).append(version);
	    keys[i++] = sb.toString();
	    sb.setLength(0);
	}
	// We only look for the most specific with information given.
	// Most specific
	//
	if (themeName != null && version != null) {
	    sb.append(themeName).append(USCORE).append(version);
	    keys[i++] = sb.toString();
	    sb.setLength(0);
	} else // next specific, only theme name.
	if (version == null) {
	    keys[i++] = themeName;
	} else // least specific, only theme version
	if (themeName == null) {
	    keys[i++] = version;
	}

	// Accumulate all the the ThemeReference's we have into 
	// a fixed length ThemeReference[]. This array is prioritized
	// as expected by subsequent Theme interfaces.
	// 
	ArrayList<ThemeReference> allrefs = new ArrayList<ThemeReference>();
	for (String key : keys) {
	    if (key == null) {
		continue;
	    }
	    // These are the ThemeReference's collected in ArrayLists
	    // that were mapped to he keys.
	    //
	    ArrayList<ThemeReference> trefs = themeReferencesMap.get(key);
	    if (trefs != null) {
		allrefs.addAll((Collection)trefs);
	    }
	}
	if (allrefs.size() == 0) {
	    // We really need to create a situation where this never happens.
	    //
	    ThemeLogger.log(Level.SEVERE, "NO_THEME_NAME_VERSION_OR_DEFAULT",
		null, null);
	    // CAN'T SUPPORT THIS YET
	    //if (themeContext.isThrowExceptionOnNoThemes()) {
		ThemeConfigurationException tfe = 
			new ThemeConfigurationException(
			    "No theme name or version specified " +
			    "and no default theme resource available.");
		throw tfe;
	    //}
	}
	ThemeReference[] refs = (ThemeReference[])
	    allrefs.toArray(new ThemeReference[allrefs.size()]);

	return refs;
    }

    /**
     * Associate an <code>ArrayList</code> of <code>ThemeReference</code>
     * instances with <code>key</code>.
     * @see #getThemeReferences
     */
    protected void putThemeReferences(String key,
		ArrayList<ThemeReference> themeReferences) {
	themeReferencesMap.put(key, themeReferences);
    }

    /**
     * Locate the <code>themeName</code> resources for <code>locale</code>
     * and <code>version</code> based on information in the runtime theme 
     * environment defined by
     * <code>{@link com.sun.webui.theme.ThemeContext}</code>.
     * Theme resources are defined in 
     * <code>{@link java.util.ResourceBundle}</code>'s in the
     * form of compiled <code>{@link java.util.PropertyResourceBundle}</code>'s
     * or <code>{@link java.util.Properties}</code> files. They must be
     * available on the application's class path.
     * </p>
     * <p>
     * The returned <code>ThemeResources</code> includes a prioritized
     * list of <code>ThemeReferences</code>. Theme resources defined by
     * the application have the highest priority.
     * </p>
     * <p>
     * If no explicit theme name and version are requested and the
     * <code>themeContext</code> does not define a default theme name
     * or version, then this implementation will use the first theme
     * found as the default theme and version for all non specific
     * theme requests, otherwise the requested theme will be returned
     * or the theme defined by the <code>themeContext</code> will be
     * returned if it exists.
     * </p>
     * <p>
     * If <code>version</code> is not null then theme resources identified
     * by the combination of a theme name and version.
     * If only <code>version</code> is non null then all theme resources
     * that can be identified just by the version are returned. If
     * <code>themeName</code> and <code>version</code> are undefined or
     * no theme resources can be found
     * <code>ThemeConfigurationException</code> is thrown.
     * </p>
     * <p>
     * There are assumptions made about the application and class loader
     * used to find the resources. For example if the resources are in
     * jars on the application's class path then search order is determined
     * by the class loader. In order for the precendence ordering of 
     * multiple theme resources to function as expected, the class loader
     * must find theme resources on the application's class path that
     * <em>are not in jar files, first</em>. This is the behavior of 
     * <code>javax.servlet.Servlet</code> class loaders in that they
     * are required to find class loadable objects in the 
     * <code>WEB_INF/classes</code> directory first.
     * </p>
     * <p>
     * Therefore if an application wishes to override theme properties
     * that are also defined in a theme or components jar, then the 
     * resource bundle class or properties file should not be in a jar
     * within the application, but "flattened" in the package hierarchy
     * of the application.
     * </p>
     */
    protected ThemeResources getThemeResources(String themeName,
	    String version, Locale locale, ThemeContext themeContext) {

	// It would be ideal to never have a ThemeContext instance
	// without a default theme name.
	//
	if (themeName == null) {
	    themeName = themeContext.getDefaultTheme();
	    if (themeName == null) {
		themeName = defaultThemeName;
	    }
	}
	if (version == null) {
	    version = themeContext.getDefaultThemeVersion();
	    if (version == null) {
		version = defaultThemeVersion;
	    }
	}

	// We might want to experiment to find out how much
	// overhead is invlolved in performing the collection
	// of the resource bundles. If we do the look up
	// every time the system will be very dynamic.
	//

	// First see if we have cached a ThemeReference for this themeName
	// version and ThemeContext.
	//
	ThemeReference[] themeReferences = null;
	try {
	    themeReferences =
		getThemeReferences(themeName, version, themeContext);
	} catch (Exception e) {
	    ThemeLogger.log(Level.FINEST,
	       "Loading themes for the first time.", null, null); 
	}
	if (themeReferences != null) {

	    // We construct a new ThemeResources every time because
	    // the locale could be different each time.
	    // The theme name and version too, for that matter,
	    // but not likely.
	    //
	    return new ThemeResources(themeReferences, themeName,
		version, locale, themeContext);
	}

	// First time find all theme resources.
	// Get application defined theme resources
	//
	try {
	    findThemeReferences(themeContext);
	} catch(Exception e) {
	    // Log
	    // and try legacy jar theme
	    findJarThemeResources(themeContext);
	}

	// try again, there must be themes this time.
	// Check the themeName, if it is null,
	// use the defaultThemeName, since it may have been set after
	// loading the themes, getThemeReferences won't replace a
	// null themeName with the defaultThemeName.
	//
	if (themeName == null) {
	    themeName = defaultThemeName;
	}
	if (version == null) {
	    version = defaultThemeVersion;
	}
	themeReferences =
	    getThemeReferences(themeName, version, themeContext);
	// Throw exception if we still don't have any.
	//
	if (themeReferences == null) {
	    ThemeLogger.log(Level.SEVERE, "NO_THEMES_FOUND", null, null);
	    // CAN'T SUPPORT THIS YET
	    //if (themeContext.isThrowExceptionOnNoThemes()) {
		ThemeConfigurationException e =
		    new ThemeConfigurationException("No themes found.");
		throw e;
	    //}
	}

	return new ThemeResources(themeReferences, themeName,
		version, locale, themeContext);
    }

    /**
     * Locate all theme resources and add them as <code>ThemeReference</code>
     * arrays to <code>themeReferencesMap</code>, mapped by theme name and
     * version (&lt;themeName&gt;_&lt;version&gt;>), by theme name and by
     * version.
     * Theme resources can be specified by the application and passed
     * in <code>basenames</code> and by a <code>sun.misc.Service</code>
     * provider called <code>com.sun.webui.theme.ThemeService</code>.
     * <p>
     * An application does not have to implement a ThemeService provider
     * but can identify theme resoureces by specifying basenames to 
     * such resource bundles in the ThemeContext.THEME_RESOURCES context param
     * in the web.xml file.
     * </p>
     */
    // An application will probably be need to create an SPI to
    // support more that one theme.
    // We probably also need a way to allow an application to specify
    // properties files that are not specific to a theme that will
    // override all themes.
    //
    protected void findThemeReferences(ThemeContext themeContext) {

	// Get application defined theme resources
	//
	String[] basenames = themeContext.getThemeResources();

	// Find all the application defined theme resources
	// Ideally these are unique to the application. The order
	// must be respected.
	// If these basenames do not define the theme name or 
	// theme version properties, use the DEFAULT_THEME_KEY
	// to make them have the highest priority over all themes.
	//
	if (basenames != null) {
	    getThemes(basenames, themeContext.getDefaultClassLoader(),
		DEFAULT_THEME_KEY);
	}

	// Now find all the theme bundles in theme jars and
	// component jars. Calls "getThemes" with defaultThemeKey == null.
	// 
	try {
	    findThemeServiceReferences(themeContext.getDefaultClassLoader());
	} catch (Exception e) {
	    // findThemeServiceReferences will log any necessary
	    // messages.
	}

	// The map now contains a set of ArrayLists. Each 
	// ArrayList contains a number of ThemeReference instances
	// for a given themeName/version combination.
	//
	// Convert the ArrayLists into fixed length arrays.
	// Why ? It would be better performance wise to 
	// leave them as ArrayLists and convert to fixed
	// length arrays when the match is found ?
	//
	// If we haven't collected any theme resources throw 
	// configuration exception, probably should just "punt".
	//
	// We probably want to try the "JarThemeFactory" approach
	// in case only this legacy themes are present.
	//
	if (themeReferencesMap.size() == 0) {
	    ThemeLogger.log(Level.SEVERE, "NO_THEMES_FOUND", null, null);
	    // CAN'T SUPPORT THIS YET
	    //if (themeContext.isThrowExceptionOnNoThemes()) {
		ThemeConfigurationException e =
		    new ThemeConfigurationException("No themes found.");
		throw e;
	    //}
	}
    }
    
    /**
     * Collect all theme resource bundle basenames from all
     * available <code>com.sun.theme.webui.ThemeService</code>
     * providers.
     * ClassNotFoundException or Exception may be throws if the
     * service is not available.
     */
    // Need to harden this in case there are no SPI's. revert to the
    // old JarTheme mechanism.
    //
    private void findThemeServiceReferences(ClassLoader classLoader)
	    throws Exception {

	Class themeService;
	try {
	    themeService =
		classLoader.loadClass("com.sun.webui.theme.ThemeService");
	} catch (ClassNotFoundException cnfe) {
	    ThemeLogger.log(Level.WARNING, "NO_SUCH_SERVICE",
		new Object[] {"com.sun.webui.theme.ThemeService"}, cnfe);
	    throw cnfe;
	} catch (Exception e) {
	    ThemeLogger.log(Level.WARNING, "NO_SUCH_SERVICE",
		new Object[] {"com.sun.webui.theme.ThemeService"}, e);
	    throw e;
	}

	Iterator iterator = null;
	try {
	    iterator = Service.providers(themeService);
	} catch (Exception e) {
	    ThemeLogger.log(Level.SEVERE, e.getMessage(), null, e);
	    throw e;
	}
	while (iterator.hasNext()) {
	    ThemeService spi = (ThemeService)iterator.next();
	    // get all available themes from all services
	    //
	    String[] basenames = spi.getThemeBundles();
	    getThemes(basenames, classLoader, null);
	}
    }

    /**
     * Collect the themes keyed by
     * <code>&lt;themename&gt;_&lt;version&gt;</code> in <code>map</code>.
     * The resource bundle represented by the basename contains
     * the equivalent of the Theme jar's manifest attributes. We
     * are only interested in the NAME and VERSION.
     * <p>
     * ThemeResourceBundle.ThemeProperty.NAME.getKey()<br/>
     * ThemeResourceBundle.ThemeProperty.VERSION.getKey()<br/>
     * </p>
     * If these properties do not exist the theme is ignored unless
     * <code>defaultThemeKey</code> is not null. This means that this
     * call is made to obtain the application's theme resources and
     * "tags" these resources with <code>defaultThemeKey</code> if
     * the application's resource bundles do not define the theme name
     * or version.
     */
    private void getThemes(String[] basenames, ClassLoader classLoader,
	    String defaultThemeKey) {

	ResourceBundle bundle;
	StringBuilder sb = new StringBuilder();
	for (String basename : basenames) {
	    try {
		bundle = ResourceBundle.getBundle(basename,
		    Locale.getDefault(), classLoader);
	    } catch (MissingResourceException mre) {
		ThemeLogger.log(Level.WARNING, "NO_SUCH_THEME",
		    new Object[] { basename }, mre);
		continue;
	    } catch (NullPointerException e) {
		continue;
	    }
	    String name = null;
	    try {
		name = bundle.getString(
		    ThemeResourceBundle.ThemeProperty.NAME.getKey());
	    } catch (MissingResourceException mre) {
		if (defaultThemeKey != null) {
		    name = defaultThemeKey;
		}
	    }
	    if (name == null || (name = name.trim()).length() == 0) {
		ThemeLogger.log(Level.WARNING, "NO_THEME_PROPERTY",
		    new Object[] { basename,
			ThemeResourceBundle.ThemeProperty.NAME.getKey() },
		    null);
		continue;
	    }
	    String version = null;
	    try {
		version = bundle.getString(
		    ThemeResourceBundle.ThemeProperty.VERSION.getKey());
		version = version.trim();
	    } catch (MissingResourceException mre) {
		if (defaultThemeKey != null) {
		    version = defaultThemeKey;
		}
	    }
	    if (version == null || (version = version.trim()).length() == 0) {
		ThemeLogger.log(Level.WARNING, "NO_THEME_PROPERTY",
		    new Object[] { basename,
			ThemeResourceBundle.ThemeProperty.VERSION.getKey() },
		    null);
		continue;
	    }
	    // The defaultTheme is the first theme found, regardless 
	    // of version.
	    // We don't want to make "DEFAULT_THEME_KEY" the default theme.
	    // We know that defaultThemeKey will be null, when obtaining
	    // SPI resources, from which we want to obtain a default theme.
	    // We will constrain an application to defining a default
	    // theme via the context param and not a theme resource bundle.
	    //
	    if (defaultThemeKey == null) {
		if (defaultThemeName == null) {
		    defaultThemeName = name;
		    defaultThemeVersion = version;
		}
	    }

	    // Create a key <themename>_<version>
	    //
	    // For an SPI theme name and version cannot be null.
	    // And will always map resources ot 
	    //
	    // name + "_" + version
	    //
	    // and "name" and "version".
	    //
	    // For application theme resources we may have the following
	    //
	    // name + "_" + version
	    // defaultThemeKey + "_" + defaultThemeKye
	    // name + "_" + defaultThemeKye
	    // defaultThemeKey + "_" + version
	    //
	    // But we don't want to map resourcse to 
	    // just "defaultThemeKey" for name or version.
	    //
	    sb.setLength(0);
	    sb.append(name).append("_").append(version);

	    // See if we've seen one of these theme references
	    // before, and if not we need to create the list for 
	    // themes of "<name>_<version>".
	    // 
	    //ArrayList trefs = (ArrayList)map.get(sb.toString());
	    ArrayList<ThemeReference> trefs = (ArrayList<ThemeReference>)
		themeReferencesMap.get(sb.toString());
	    if (trefs == null) {
		trefs = new ArrayList();
		// Map the list to <name>_<version>
		//
		//map.put(sb.toString(), trefs);
		putThemeReferences(sb.toString(), trefs);
		// If not application resources map the 
		// trefs to name and version individually.
		//
		if (defaultThemeKey == null) {
		    if (name != null) {
			putThemeReferences(name, trefs);
		    }
		    if (version != null) {
			putThemeReferences(version, trefs);
		    }
		}
	    }
	    trefs.add(new ThemeReference(basename, classLoader));
	}
    }

    
    // Legacy theme jar support
    //
    private void findJarThemeResources(ThemeContext themeContext) {
        
	ClassLoader classLoader = themeContext.getDefaultClassLoader();
        Enumeration manifests = null;
        try {
            manifests = classLoader.getResources(MANIFEST);
        } catch(IOException ioex) {
	    // Log
	    return;
        }
        
        if (!manifests.hasMoreElements()) {
            // Log
	    return;
        }
        
        InputStream in = null;
	StringBuilder sb = new StringBuilder(32);

	String SLASH = "/";
	String USCORE = "_";
        
        while (manifests.hasMoreElements()) {
            
            URL url = (URL)(manifests.nextElement());
            
            try {
                in = url.openConnection().getInputStream();
                Manifest manifest = new Manifest(in);

		// See if we have a theme jar
		//
		Attributes themeAttributes = 
			manifest.getAttributes(THEME_SECTION);
		if (themeAttributes == null) {
		    continue;
		}

		String themeName = readAttribute(themeAttributes, NAME);
		String version = readAttribute(themeAttributes, THEME_VERSION);
		// Require both attributes.
		//
		if (themeName == null || version == null) {
		    // Log 
		    continue;
		}

		// record the default theme, i.e. the first one found.
		//
		if (defaultThemeName == null) {
		    defaultThemeName = themeName;
		    defaultThemeVersion = version;
		}

		// Need to ignore this or should we ?
		//
		if (themeContext.getThemeServletContext() == null) {
		    String prefix = readAttribute(themeAttributes, PREFIX);
		    if (prefix != null) {
			if (!prefix.startsWith(SLASH)) {
			    prefix = sb.append(SLASH)
				.append(prefix).toString();
			    sb.setLength(0);
			}
			themeContext.setThemeServletContext(prefix);
		    }
		}

		sb.append(themeName).append(USCORE).append(version);
		// See if we already have resources for this 
		// themeName and version
		//
		ArrayList<ThemeReference> trefs = 
			themeReferencesMap.get(sb.toString());
		if (trefs == null) {
		    trefs = new ArrayList<ThemeReference>();
		    putThemeReferences(sb.toString(), trefs);
		    // Now map the trefs to themeName and version.
		    // We know that if the combination does not exist
		    // then neither will the individual keys.
		    //
		    putThemeReferences(themeName, trefs);
		    putThemeReferences(version, trefs);
		}
		sb.setLength(0);

		addThemeReference(trefs, themeAttributes, MESSAGES,
			classLoader);
		addThemeReference(trefs, themeAttributes, IMAGES,
			classLoader);
		addThemeReference(trefs, themeAttributes, JSFILES,
			classLoader);
		addThemeReference(trefs, themeAttributes, STYLESHEETS,
			classLoader);
		addThemeReference(trefs, themeAttributes, TEMPLATES,
			classLoader);
		addThemeReference(trefs, themeAttributes, CLASSMAPPER,
			classLoader);

            } catch(Exception e) {
                // Log
            } finally {
                try { in.close(); } catch(Throwable t){}
            }
        }
    }

    private void addThemeReference(ArrayList<ThemeReference> trefs,
	    Attributes themeAttributes, String reference,
	    ClassLoader classLoader) {

	String basename = readAttribute(themeAttributes, reference);
	if (basename != null) {
	    trefs.add(new ThemeReference(basename, classLoader));
	}
    }

    private String readAttribute(Attributes themeAttributes, String propName) {
        String name = themeAttributes.getValue(propName);
        
        if (name == null || (name = name.trim()).length() == 0) {
	    return null;
	} else {
	    return name;
	}
    }
}
