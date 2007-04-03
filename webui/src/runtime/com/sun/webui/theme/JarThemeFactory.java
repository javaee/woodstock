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

import javax.servlet.ServletContext;
import javax.portlet.PortletContext;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * <p>Factory class responsible for setting up the Sun Web Component
 * application's ThemeManager.</p>
 */
public class JarThemeFactory implements ThemeFactory {
    
    private ThemeManager themeManager;

    public JarThemeFactory() {
    }

    // Private message variables
    private final static String WARNING_LOAD =
            "WARNING: the Sun Web Components could not load any themes.";
    private final static String WARNING_BADFILE =
            "WARNING: the Sun Web Components detected a corrupted theme configuration file:\n\t";
    
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

    private static final boolean DEBUG = false;
    
    private Iterator getThemeAttributes(ClassLoader classLoader) {
        
        if (DEBUG) log("getThemeAttributes()");
        
        Enumeration manifests = getManifests(classLoader); 
        
        if(!manifests.hasMoreElements()) {
            String msg = "No Themes in the classpath!";
            throw new ThemeConfigurationException(msg);
        }
        
        URL url = null;
        URLConnection conn = null;
        InputStream in = null;
        Manifest manifest = null;
        Attributes themeAttributes = null; 
        ArrayList themeProps = new ArrayList(); 
        
        while (manifests.hasMoreElements()) {
            
            url = (URL)(manifests.nextElement());
            
            try {
                if(DEBUG) log("\tExamine " + url.toString());
                conn = url.openConnection();
                in = conn.getInputStream();
                manifest = new Manifest(in);
                themeAttributes = manifest.getAttributes(THEME_SECTION);
                if(themeAttributes != null) {
                    if(DEBUG) log("\tFound a theme section");
                    themeAttributes.putValue(FILENAME, url.toString());
                    themeProps.add(themeAttributes);
                }
            } catch(IOException ioex) {
                // do nothing
            } finally {
                try { in.close(); } catch(Throwable t){}
            }
        }
        return themeProps.iterator();
    }
    
    
    private String readAttribute(Attributes themeAttributes, String propName) {
        String name = themeAttributes.getValue(propName);
        
        if (name == null || name.length() == 0) {
            
            String propFile = themeAttributes.getValue(FILENAME);
            
            StringBuffer msgBuffer = new StringBuffer(300);
            msgBuffer.append("ThemeConfiguration file "); //NOI18N
            if(propFile != null) {
                msgBuffer.append(propFile);
                msgBuffer.append(" "); //NOI18N
            }
            msgBuffer.append("does not contain required property \""); //NOI18N
            msgBuffer.append(propName);
            msgBuffer.append("\".");
            throw new ThemeConfigurationException(msgBuffer.toString());
        }
        return name;
    }
    
    private void throwVersionException(String name, String version,
            String requiredThemeVersion) {
        
        StringBuffer msgBuffer =
                new StringBuffer(300); //NOI18N
        msgBuffer.append("\n\nTheme \"");
        msgBuffer.append(name);
        msgBuffer.append("\" is not up to date with the component library.\n");
        msgBuffer.append("Its version is ");
        msgBuffer.append(version);
        msgBuffer.append(". Version ");
        msgBuffer.append(requiredThemeVersion);
        msgBuffer.append(" or higher required.\n");
        throw new ThemeConfigurationException(msgBuffer.toString());
    }
    
    private static Application getApplication() {
        ApplicationFactory factory = (ApplicationFactory)
        FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        if(factory == null) {
            return null;
        }
        return factory.getApplication();
    }
    
    private static Set getLocales(Application application) {
        
        if(DEBUG) log("getLocales()");
        
        HashSet localeSet = new HashSet();
        
        Locale locale = application.getDefaultLocale();
        // According to the JSF spec, getDefaultLocale never returns
        // null, but in the Creator simulated environment it does,
        // so we add belt and braces.
        if(locale != null) {
            if(DEBUG) log("\tDefault locale is " + locale.toString());
            localeSet.add(locale);
        } else if(DEBUG) log("\tNo default locale!");
        
        Iterator localeIterator = application.getSupportedLocales();
        // Again, this should not be null, but we need to account for
        // Creator...
        if(localeIterator != null) {
            while(localeIterator.hasNext()) {
                Object localeObject = localeIterator.next();
                if(DEBUG) {
                    log("\tAdding locale " + localeObject.toString());
                }
                localeSet.add(localeObject);
            }
        } else if(DEBUG) log("\tNo supported locales!");
        
        // If things went wrong (= we're in Creator), add the default
        // locale now
        if(localeSet.isEmpty()) {
            if(DEBUG) {
                log("\tAdding default locale which is " + //NOI18N
                        Locale.getDefault().toString());
            }
            localeSet.add(Locale.getDefault());
        }
        return localeSet;
    }
    
    private String missingResourceBundleMessage(Attributes themeAttributes,
            String bundleName) {
        
        String propFile = themeAttributes.getValue(FILENAME);
        StringBuffer msgBuffer =
                new StringBuffer("Invalid theme configuration file for theme ");
        msgBuffer.append(themeAttributes.getValue(NAME));
        
        if(propFile != null) {
            msgBuffer.append(" configured by property file ");
            msgBuffer.append(propFile);
            msgBuffer.append(".");
        }
        msgBuffer.append
	    ("JarThemeFactory could not locate resource bundle at ");
        msgBuffer.append(bundleName);
        msgBuffer.append(".");
        return msgBuffer.toString();
    }
    
    private static String processInitParameter(Object object) {
        
        if(object == null) {
            return null;
        }
        String string = object.toString();
        // Unfortunately, the Creator simulated environment returns an
        // empty string instead of null when the init parameter does
        // not exist
        if(string.length() == 0) {
            return null;
        }
        return string;
    }
    
    private String getRequiredThemeVersion(ClassLoader classLoader) {
        
        if(DEBUG) log("getRequiredThemeVersion()");
        
        Enumeration manifests = getManifests(classLoader);
               
        if (!manifests.hasMoreElements()) {
            if(DEBUG) log("\tNo manifests in the classpath!");
            return null;
        }
        
        URL url = null;
        InputStream in = null;
        Manifest manifest = null;
        String themeVersion = null;
        
        while(themeVersion == null && manifests.hasMoreElements()) {
            
            url = (URL)(manifests.nextElement());
            if(url.toString().indexOf("webui") == -1) {
                continue;
            }
            
            if(DEBUG) log("\tNow processing " + url.toString());
            
            try {
                in = url.openConnection().getInputStream();
                manifest = new Manifest(in);
                Attributes attr = manifest.getAttributes(COMPONENTS_SECTION);
                if(attr != null) {
                    themeVersion = attr.getValue(THEME_VERSION_REQUIRED);
                    if(DEBUG) log("\tFound attribute " + themeVersion);
                }
            } catch(IOException ioex) {
                ioex.printStackTrace();
                // do nothing
            } finally {
                try { in.close(); } catch(Throwable t){}
            }
        }
        return themeVersion;
    }
    
    private Enumeration getManifests(ClassLoader classLoader) {
        
        Enumeration manifests = null;

        // Temporary workaround for a Creator issue; direct questions on this
        // to tor.norbye@sun.com
        if (Beans.isDesignTime() && classLoader instanceof URLClassLoader) {
            // This is a temporary hack to limit our manifest search for themes
            // to the URLs in the ClassLoader, if it's a URLClassLoader.
            // This is necessary to get theme switching to work when there
            // are multiple simultaneous open projects in Creator.
            Vector v = new Vector();
            URL[] urls = ((URLClassLoader)classLoader).getURLs();
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                try {
                    URL manifest = new URL(url, MANIFEST);
                    // See if the manifest file exists
                    InputStream is = manifest.openStream();
                    v.addElement(manifest);
                    is.close();
                } catch (IOException ioe) {
                    // No such manifest, so don't add one to the vector
                }
            }
            return v.elements();
        }

        try {
            manifests = classLoader.getResources(MANIFEST);
        } catch(IOException ioex) {
            if(DEBUG) {
                log("\tIOException using the Context ClassLoader");
            }
        }
        if(!manifests.hasMoreElements()) {
            try {
                manifests = classLoader.getResources(MANIFEST);
            } catch(IOException ioex) {
                if(DEBUG) {
                    log("\tIOException using JarThemeFactory's ClassLoader");
                }
            }
        }
        return manifests; 
    }
    
    private static void log(String s) {
        System.out.println("JarThemeFactory::" + s);
    }

    /**
     * Return the default <code>Theme</code> for
     * <code>locale</code> within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(Locale locale, ThemeContext themeContext) {
	return getTheme(locale, null, themeContext);
    }
    // Not sure how useful version really is.
    //
    public Theme getTheme(Locale locale, String version,
          ThemeContext themeContext) {
        return getTheme(null, version, locale, themeContext);
    }

    /**
     * Return the <code>themeName</code> <code>Theme</code> for
     * <code>locale</code> within the theme runtime environment of
     * <code>themeContext</code>.
     */
    public Theme getTheme(String themeName, Locale locale, 
	    ThemeContext themeContext) {
	return getTheme(themeName, null, locale, themeContext);
    }

    // Not sure how useful version really is.
    public Theme getTheme(String themeName, String version, Locale locale, 
	    ThemeContext themeContext) {

        // First, get the ThemeManager
	if (themeManager == null) {
	    themeManager = createThemeManager(themeContext);
	}
	JarTheme theme = themeManager.getTheme(themeName, locale);
	theme.setThemeContext(themeContext);
        return theme;
    }

    /**
     * Hack - this will go away
     */
    public String getDefaultThemeName(ThemeContext themeContext) {
	if (themeManager == null) {
	    themeManager = createThemeManager(themeContext);
	}
	return themeManager.getDefaultThemeName();
    }

    private ThemeManager createThemeManager(ThemeContext themeContext) {
        
	// If the context doesn't have a default theme name
	// let the first theme jar that says default win.
	//
	String defaultThemeName = themeContext.getDefaultTheme();

        // The name value found in the current manifest attributes.
        String name = null;

	ClassLoader classLoader = themeContext.getDefaultClassLoader();
	// From the components jar
	//
        String requiredThemeVersion = getRequiredThemeVersion(classLoader);
	// Get all attributes from all theme jars.
	//
        Iterator themeAttributesIterator = getThemeAttributes(classLoader);
        
        if (!themeAttributesIterator.hasNext()) {
            throw new ThemeConfigurationException(WARNING_LOAD);
        }
        
	ThemeManager themeManager = new ThemeManager();
        while(themeAttributesIterator.hasNext()) {
            
            Attributes themeAttributes = (Attributes)
		themeAttributesIterator.next();
            
	    // Better not see the same theme name twice
	    //
	    name = readAttribute(themeAttributes, NAME);

	    // Probably should see if the there are more themes.
	    //
	    String version = readAttribute(themeAttributes, THEME_VERSION);
            if(requiredThemeVersion != null &&
                    requiredThemeVersion.compareTo(version) > 0) {
                throwVersionException(name, version, requiredThemeVersion);
            }
            
            Map map = new HashMap();
	    Set localeSet = themeContext.getSupportedLocales();
	    // No explicitly supported themes ?
	    // just add the default theme, assume it is one
	    // of the supported themes if localeSet is not null
	    // May need to do this in ThemeContext.
	    //
	    if (localeSet == null) {
		localeSet = new HashSet();
		localeSet.add(themeContext.getDefaultLocale());
	    }
	    Iterator locales = localeSet.iterator();
            while(locales.hasNext()) {
                Locale locale = (Locale)(locales.next());
                // createTheme throws a ThemeConfigurationException if
                // it fails, in which case we abort
		//
                map.put(locale, createTheme(themeAttributes, locale,
			themeContext));
            }
            themeManager.addThemeMap(name, map);

	    // If the theme context does not define a default theme name
	    // make the first default theme jar the default theme.
	    //
            if (defaultThemeName == null) {
                String isDefault = themeAttributes.getValue(DEFAULT);
                if (isDefault != null && 
			isDefault.toLowerCase().equals("true")) {
		    defaultThemeName = name;
                }
            }
        }
        themeManager.setDefaultThemeName(defaultThemeName);
        return themeManager;
    }

    private Theme createTheme(Attributes themeAttributes, Locale locale,
	    ThemeContext themeContext) throws ThemeConfigurationException {
        
        String prefix = readAttribute(themeAttributes, PREFIX); //NOI18N
        if (!prefix.startsWith("/")) { //NOI18N
            prefix = "/".concat(prefix); //NOI18N
        }

	if (themeContext.getThemeServletContext() == null) {
	    themeContext.setThemeServletContext(prefix);
	}

	// Need to use themeContext for "translateURI" semantics.
	//
        JarTheme theme = new JarTheme(locale);

	ResourceBundle override = null;
	ClassLoader classLoader = themeContext.getDefaultClassLoader();
	ResourceBundle bundle = 
		createResourceBundle(themeAttributes, MESSAGES, locale,
		classLoader);
	String messageOverride = themeContext.getMessages();
        if (messageOverride != null) {
            try {
		override = ResourceBundle.getBundle(messageOverride, locale);
            } catch(MissingResourceException mre) {
		/* Don't do anything just use the theme's bundle
                StringBuffer errorMessage =
                        new StringBuffer("The message resource file ");
                errorMessage.append(messageOverride);
                errorMessage.append(" specified by context parameter ");
                errorMessage.append(MESSAGES_PARAM);
                errorMessage.append(" does not exist.");
                throw new ThemeConfigurationException(errorMessage.toString());
		*/
            }
        }
        theme.configureMessages(bundle, override);
        
        // Configure the images
        bundle = createResourceBundle(themeAttributes, IMAGES, locale,
		classLoader); 
        theme.configureImages(bundle);
        
        // Configure the javascript files
        bundle = createResourceBundle(themeAttributes, JSFILES, locale,
		classLoader); 
        String jsFiles = readAttribute(themeAttributes, JSFILES);
        theme.configureJSFiles(bundle);
        
        // Configure the style sheets
        bundle = createResourceBundle(themeAttributes, STYLESHEETS, locale,
		classLoader); 
        theme.configureStylesheets(bundle);

        // Configure the template files
        bundle = createResourceBundle(themeAttributes, TEMPLATES, locale,
		classLoader); 
        theme.configureTemplates(bundle);
        
        // Configure the classmapper
        String classMapper = themeAttributes.getValue(CLASSMAPPER);
        if (classMapper != null && classMapper.length() > 0) {     
            bundle = createResourceBundle(themeAttributes, CLASSMAPPER, locale,
		classLoader);
            theme.configureClassMapper(bundle);
        }
        return theme;
    }

    private ResourceBundle createResourceBundle(Attributes themeAttributes, 
		String propName, Locale locale, ClassLoader classLoader) {

        String bundleName = readAttribute(themeAttributes, propName);
        try {
            return ResourceBundle.getBundle(bundleName, locale, classLoader);
        } 
        catch(MissingResourceException mre) {           
            StringBuffer msgBuffer = new StringBuffer(300);
            msgBuffer.append("Invalid theme configuration file for theme ");
            msgBuffer.append(themeAttributes.getValue(NAME));
            msgBuffer.append(
		".\nJarThemeFactory could not locate resource bundle at ");
            msgBuffer.append(bundleName);
            msgBuffer.append(".");
            throw new ThemeConfigurationException(msgBuffer.toString());
        }      
    }
}
