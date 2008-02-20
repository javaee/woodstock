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
 * $Id: JarTheme.java,v 1.3 2008-02-20 16:29:23 rratta Exp $
 */

package com.sun.webui.theme;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.portlet.PortletRequest;

import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.util.ClientSniffer;
import com.sun.webui.jsf.util.ClientType;
import com.sun.webui.jsf.util.MessageUtil;

import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeJavascript;


/**
 * <p>The Sun Java Web UI Components rely on non-Java resources 
 * such a message files, image files, CSS stylesheets and JavaScript 
 * files to render correctly. These resources are collectively 
 * known as a "Theme" and are bundled together in a Jar file.
 * Themes are swappable, so you can switch from one Theme to another
 * by placing a different Theme jar in the web application's classpath.<p> 
 * 
 *<p> Themes consist of both of resources that are used directly 
 * by the Java classes at runtime (for example property files) and 
 * resources that are requested by the application users' browser 
 * (for example image files). In order to make Theme resources available
 * over HTTP, you must configure the ThemeServlet in at least one 
 * of the SJWUIC applications on the server (@see ThemeServlet).</p>
 *
 *<p>For more information on how to configure a SJWUIC web application
 * w.r.t. Themes, see the documentation for @see ThemeServlet.</p> 
 *
 *<p>The Theme class is used to access the resources associated 
 *with a particular Theme. The manifest of the Theme jar must 
 *contain a section named  
 * <code>com/sun/webui/theme</code> which contains attributes
 * whose values are used to locale the Theme resources. When the 
 * SJWUIC application is loaded on the server, the @see ThemeFactory 
 * class looks for manifest containing such sections and creates 
 * instances of the Theme class based on the information. For each Theme
 * jar, one instance is created for each locale specified in the 
 * JSF configuration file. </p>
 *
 * * <p>Note that, since the JAR files for all installed themes are loaded
 * into the same class loader, the actual resource paths for the resources
 * used by each theme <strong>MUST</strong> be unique.  This is true
 * regardless of whether the themes share a prefix or not.</p>
 *
 */

public class JarTheme implements Theme {

    static ThreadLocal themeContext = new ThreadLocal();
    
    private ResourceBundle bundle = null; 
    private ResourceBundle fallbackBundle = null; 
    private ResourceBundle classMapper = null;
    private ResourceBundle imageResources = null; 
    private ResourceBundle jsFiles = null; 
    private ResourceBundle stylesheets = null;
    private ResourceBundle templates = null; 
    private String[] globalJSFiles = null;
    private String[] globalStylesheets = null;
     
    /**
     * Attribute name used to store the user's theme name in the Session
     */
    public static final String THEME_ATTR = "com.sun.webui.jsf.Theme";
    /** The context parameter name used to specify a console path, if one is 
     * used.
     */
    public static final String RESOURCE_PATH_ATTR = 
	"com.sun.web.console.resource_path";


    private static final String HEIGHT_SUFFIX = "_HEIGHT";
    private static final String WIDTH_SUFFIX = "_WIDTH";
    private static final String ALT_SUFFIX = "_ALT";
    private static final String GLOBAL_JSFILES = ThemeJavascript.GLOBAL;
    private static final String GLOBAL_STYLESHEETS = ThemeStyles.GLOBAL;
    private static final String MASTER_STYLESHEET = ThemeStyles.MASTER;
    private String prefix = null; 
    private Locale locale = null; 
    //private boolean realServer = true;

    void setThemeContext(ThemeContext themeContext) {
	this.themeContext.set(themeContext);
    }

    ThemeContext getThemeContext() {
	return (ThemeContext)themeContext.get();
    }
    
    private static final boolean DEBUG = false;
   
    public JarTheme(Locale locale) { 
        //realServer = !java.beans.Beans.isDesignTime();   
        this.locale = locale;
    }
    
    /**
     * Use this method to retrieve a String array of URIs
     * to the JavaScript files that should be included 
     * with all pages of this application
     * @return String array of URIs to the JavaScript files
     */
    public String[] getGlobalJSFiles() {
	 
	if(DEBUG) log("getGlobalJSFiles()"); 

	if(globalJSFiles == null) { 

	    try {

		String files = jsFiles.getString(GLOBAL_JSFILES);
		StringTokenizer tokenizer = new StringTokenizer(files, " ");
		String pathKey = null;
		String path = null; 

		ArrayList fileNames = new ArrayList();

		while(tokenizer.hasMoreTokens()) {
		    pathKey = tokenizer.nextToken();
		    path = jsFiles.getString(pathKey); 
		    fileNames.add(translateURI(path));
		}
		int numFiles = fileNames.size(); 
		globalJSFiles = new String[numFiles]; 
		for(int i = 0; i < numFiles; ++i) {
                    Object fileName = fileNames.get(i);
                    if (fileName != null) {
                        globalJSFiles[i] = fileName.toString();
                    }
		}
	    } 
	    catch(MissingResourceException npe) {
		// Do nothing - there are no global javascript files
		//globalJSFiles = new String[0];
		return null;
	    }
	}       
        return globalJSFiles; 
    }

    /**
     * Use this method to retrieve a String array of URIs
     * to the CSS stylesheets files that should be included 
     * with all pages of this application
     * @return String array of URIs to the stylesheets
     */
    public String[] getGlobalStylesheets() {
	if(globalStylesheets == null) { 

	    try {
		String files = stylesheets.getString(GLOBAL_STYLESHEETS);
		StringTokenizer tokenizer = new StringTokenizer(files, " ");
		String pathKey = null; 
		String path = null;
		ArrayList fileNames = new ArrayList();

		while(tokenizer.hasMoreTokens()) {

		    pathKey = tokenizer.nextToken();
		    path = stylesheets.getString(pathKey); 
		    fileNames.add(translateURI(path));
		}
		int numFiles = fileNames.size(); 
		globalStylesheets = new String[numFiles]; 
		for(int i=0;i<numFiles; ++i) { 
		    globalStylesheets[i] = fileNames.get(i).toString(); 
		}
 
	    } catch(MissingResourceException npe) {
		// There was no "global" key
		// Do nothing
		//globalStylesheets = new String[0];
		return null;
	    }
	}
	return globalStylesheets;
    }

    /**
     * Returns a String that represents a valid path to the JavaScript
     * file corresponding to the key
     * @return Returns a String that represents a valid path to the JavaScript
     * file corresponding to the key
     * @param key Key to retrieve the javascript file
     */
    public String getPathToJSFile(String key) {
	if(DEBUG) log("getPathToJSFile()"); 
        String path = jsFiles.getString(key); 
	if(DEBUG) log("path is " + translateURI(path)); 
        return translateURI(path); 
    } 
 
    /**
     * Retrieves a String from the JavaScript ResourceBundle without the theme
     * path prefix.
     *
     * @param key The key used to retrieve the message
     * @return A localized message string
     */
    public String getJSString(String key) {
        return jsFiles.getString(key);
    }
    
    /**
     * Returns a String that represents a valid path to the CSS stylesheet
     * corresponding to the key
     * @param context FacesContext of the request
     * @return  A String that represents a valid path to the CSS stylesheet
     * corresponding to the key
     */
    private String getPathToStylesheet(FacesContext context) {
        
	if(DEBUG) log("getPathToStyleSheet()"); 

        ClientType clientType = ClientSniffer.getClientType(context);
        if(DEBUG) log("Client type is " + clientType.toString());
        try { 
            String path = stylesheets.getString(clientType.toString()); 
            if(DEBUG) { 
                log(path);
                log(translateURI(path)); 
            } 
            if (path == null || path.length() == 0) {
                return null;
            } else {
                return translateURI(path); 
            }
        }
        catch(MissingResourceException mre) { 
            StringBuffer msgBuffer = new StringBuffer("Could not find propery ");
            msgBuffer.append(clientType.toString()); 
            msgBuffer.append(" in ResourceBundle "); 
            msgBuffer.append(stylesheets.toString());
            throw new RuntimeException(msgBuffer.toString());
        }
    }
    
    /**
     * Returns a String that represents a valid path to the CSS stylesheet
     * corresponding to the key
     * @return  A String that represents a valid path to the CSS stylesheet
     * corresponding to the key
     */
    private String getPathToMasterStylesheet() {
        
        try { 
            String path = stylesheets.getString(MASTER_STYLESHEET); 
            if (path == null || path.length() == 0) {
                return null;
            } else {
                return translateURI(path); 
            }
        }
        catch(MissingResourceException mre) { 
            StringBuffer msgBuffer = new StringBuffer("Could not find master ");
            msgBuffer.append("stylesheet in ResourceBundle "); 
            msgBuffer.append(stylesheets.toString());
            throw new RuntimeException(msgBuffer.toString());
        }
    }

    public String[] getMasterStylesheets() {
	String css = getPathToMasterStylesheet();
	return css == null ? null : new String[] { css };
    }
    
    /**
     * Returns a String that represents a valid path to the CSS stylesheet
     * corresponding to the key
     * @return  A String that represents a valid path to the CSS stylesheet
     * corresponding to the key
     */
    private String getPathToStylesheet(String clientName) {
        
	if(DEBUG) log("getPathToStyleSheet()"); 

        try { 
            String path = stylesheets.getString(clientName); 
            if (path == null || path.length() == 0) {
                return null;
            } else {
                return translateURI(path); 
            }
        }
        catch(MissingResourceException mre) { 
            StringBuffer msgBuffer = new StringBuffer("Could not find propery ");
            msgBuffer.append(clientName); 
            msgBuffer.append(" in ResourceBundle "); 
            msgBuffer.append(stylesheets.toString());
            throw new RuntimeException(msgBuffer.toString());
        }
    }

    public String[] getStylesheets(String key) {
	String css = getPathToStylesheet(key);
	return css == null ? null : new String[] { css };
    }

    /**
     * Returns a String that represents a valid path to the HTML template
     * corresponding to the key
     * @return  A String that represents a valid path to the HTML template
     * corresponding to the key
     */
    public String getPathToTemplate(String clientName) {
        
	if(DEBUG) log("getPathToTemplate()"); 

        try { 
            String path = templates.getString(clientName); 
            if (path == null || path.length() == 0) {
                return null;
            } else {
                return translateURI(path); 
            }
        }
        catch(MissingResourceException mre) { 
            StringBuffer msgBuffer = new StringBuffer("Could not find propery ");
            msgBuffer.append(clientName); 
            msgBuffer.append(" in ResourceBundle "); 
            msgBuffer.append(templates.toString());
            throw new RuntimeException(msgBuffer.toString());
        }
    }

    /**
     * Returns the name of a CSS style. If the Theme includes a class
     * mapper, the method checks it for the presence of a mapping for
     * the CSS class name passed in with the argument. If there 
     * is no mapping, the name is used as is. 
     * 
     * up in the class mapper if there is one, a valid path to the CSS stylesheet
     * corresponding to the key
     * @param name The style class name to be used
     * @return the name of a CSS style.
     */  
    public String getStyleClass(String name) {
        if(classMapper == null) { 
            return name; 
        }
        String styleClass = classMapper.getString(name); 
        return (styleClass == null) ? name : styleClass; 
    }

    /**
     * Retrieves a message from the appropriate ResourceBundle.
     * If the web application specifies a bundle that overrides
     * the standard bundle, that one is tried first. If no override 
     * bundle is specified, or if the bundle does not contain the 
     * key, the key is resolved from the Theme's default bundle.
     * @param key The key used to retrieve the message
     * @return A localized message string
     */
    public String getMessage(String key) {
	String message = null; 
        try { 
            message = bundle.getString(key); 
        }
        catch(MissingResourceException mre) { 
            try { 
                message = fallbackBundle.getString(key); 
            }
            catch(NullPointerException npe) {
                throw mre;  
            }
        }
	return message;
    }

    /**
     * Retrieves a message from the appropriate ResourceBundle.
     * If the web application specifies a bundle that overrides
     * the standard bundle, that one is tried first. If no override 
     * bundle is specified, or if the bundle does not contain the 
     * key, the key is resolved from the Theme's default bundle.
     * @param key The key used to retrieve the message
     * @param params An object array specifying the parameters of
     * the message
     * @return A localized message string
     */
    public String getMessage(String key, Object[] params) {
	String message = getMessage(key);
	MessageFormat mf = new MessageFormat(message, locale); 
        return mf.format(params); 
    }

    /**
     * Return a <code>boolean</code> value for <code>key</code>.
     * If <code>key</code> is not defined, return <code>defaultValue</code>.
     * <p>
     * This method will coerce the <code>String</code> property value
     * to boolean, using <code>Boolean.valueOf(String)</code>.
     * </p>
     * @param key Defines a boolean value.
     * @param defaultValue The value to return if <code>key</code> is not
     * defined.
     * @return A boolean value for <code>key</code>
     */
    public boolean getMessageBoolean(String key, boolean defaultValue) {
	String property = key;
	try {
	    property = getMessage(key);
	} catch (MissingResourceException mre) {
	    return defaultValue;
	}
	return Boolean.valueOf(property).booleanValue();
    }

    /**
     * Return an <code>int</code> value for <code>key</code>.
     * If <code>key</code> is not defined or the value of key 
     * cannot be coerced to an <code>int</code>, 
     * return <code>defaultValue</code>.
     * <p>
     * This method will coerce the <code>String</code> property value
     * to <code>int</code>, using <code>Integer.parseInt(String)</code>.
     * </p>
     * @param key Defines an <code>int</code> value.
     * @param defaultValue The value to return if <code>key</code> is not
     * defined or cannot be converted to <code>int</code>.
     * @return An <code>int</code> value for <code>key</code>
     */
    public int getMessageInt(String key, int defaultValue) {

	String property = key;
	try {
	    property = getMessage(key);
	    if (property == null || property.trim().length() == 0) {
		return defaultValue;
	    }
	    int ivalue = Integer.parseInt(property);
	    return ivalue;
	} catch(Exception e) {
	}
        return defaultValue;
    }

    // Sets the prefix to be unconditionally prepended for any URI given out
    // by theme.
    /**
     * Sets the prefix to be prepended to the path names of the resources
     * @param p prefix for all URIs in the theme
     */
    protected void setPrefix(String p) {
        prefix = p;     
    }
    
    /**
     * Configures a resource bundle which overrides the standard keys for 
     * retrieving style class names.
     * @param classMapper A ResourceBundle that overrides the standard style
     * class keys
     */
    protected void configureClassMapper(ResourceBundle classMapper) {
        this.classMapper = classMapper;
    }
    
    /**
     * <p>Configures the message bundles. All Themes must contain a default 
     * ResourceBundle for messages, which is configured in the Theme 
     * configuration file. This bundle is passed in as the first parameter
     * (base).</p>
     * <p>Optionally, the web application developer can override 
     * the messages from all themes by specifying a resource bundle
     * in a context init parameter (if they haven't done so, the second 
     * parameter will be null). If the second parameter is non-null, 
     * Theme.getMessage tries to get the message from the override bundle first. 
     * If that fails (or if there is no override bundle), getMessage() tries 
     * the base bundle. </p>
     * @param base The message bundle specified by the Theme 
     * configuration file.
     * @param override A message bundle configured by the user
     * in a context parameter, to override messages from the base bundle.
     */
    protected void configureMessages(ResourceBundle base, ResourceBundle override) {
        if(DEBUG) log("configureMessages()"); 
        if(override == null) { 
            if(DEBUG) log("override is null, bundle is " + base.toString());
            bundle = base;
        }
        else { 
            bundle = override; 
            fallbackBundle = base;
        }
    }
    
    /**
     * <p>Configures the image resource bundle.</p>
     *
     * @param imageResources A ResourceBundle whose keys specify 
     * the available images. 
     */
    protected void configureImages(ResourceBundle imageResources) {
        this.imageResources = imageResources;
    }
    
    /**
     * <p>Configures the JS resource files.</p>
     *
     * @param jsFiles A ResourceBundle whose keys specify the available 
     * JavaScript files
     */
    protected void configureJSFiles(ResourceBundle jsFiles) {
        this.jsFiles = jsFiles;
    }

    /**
     * <p>Configures the stylesheets.</p>
     *
     * @param stylesheets A resource bundle specifying the stylesheet for
     * each @link ClientType 
     */
    protected void configureStylesheets(ResourceBundle stylesheets) {
        this.stylesheets = stylesheets;
    }

    /**
     * <p>Configures the HTML templates.</p>
     *
     * @param templates A ResourceBundle whose keys specify the available 
     * HTML template files
     */
    protected void configureTemplates(ResourceBundle templates) {
        this.templates = templates;
    }

    /**
     * <p>This method needs to be refactored. The information about what 
     * type of path to generate is available when the Theme is configured, 
     * and it does not vary from request to request. So it should be
     * fixed on startup. </p>
     * @param context FacesContext of the calling application
     * @param uri URI to be translated
     * @return translated URI String
     */
    private String translateURI(String uri) {
        if (uri == null || uri.length() == 0) {
            return null;
        }
        
	ThemeContext themeContext = getThemeContext();
	return themeContext.getResourcePath(uri);
    }    
    
    
    private void log(String s) { 
	System.out.println(getClass().getName() + "::" + s); //NOI18N
    }

    /**
     * Return a translated image path, containing the theme servlet context.
     *
     * @param key The key used to retrieve the image path
     * @return a path with the theme servlet context, or null if the
     * <code>key</code> resolves to <code>null</code> or the empty string.
     * @throws RuntimeException if <code>key</code> cannot be found.
     */
    public String getImagePath(String key) {
	String path = null;
	try {
	    path = imageResources.getString(key);
	    if (path == null || path.trim().length() == 0) {
		return null;
	    }
            path = translateURI(path);        

	    /*
	    path = (String)getProperty(
		themeReferences,
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key, locale, classLoader);
	    */
	} catch (MissingResourceException mre) {
	    Object[] params = { key }; 
	    String message = MessageUtil.getMessage
		    ("com.sun.webui.jsf.resources.LogMessages", 
		     "Theme.noIcon", params); 
	    throw new RuntimeException(message, mre);
	    /*
	    return null;
	    */
	}
	return path;
    }
    /**
     * Return a <code>ThemeImage</code> instance for an image identified
     * by <code>key</code> from the
     * <code>ThemeResourceBundle.ThemeBundle.IMAGES></code>
     * resource bundle.
     * The <code>key</code> property defines the path of the image resource.
     * If <code>key</code> is not defined <code>null</code> is returned.<br/>
     * The bundle should define additional properties
     * where the each property is defined as <code>key</code> with the 
     * following suffixes: (i.e. key == "SMALL_ALERT", SMALL_ALERT_ALT)
     * <p>
     * <ul>
     * <li>{@link com.sun.webui.jsf.theme.ThemeImage.ALT_SUFFIX}</li>
     * <li>{@link com.sun.webui.jsf.theme.ThemeImage.TITLE_SUFFIX}</li>
     * <li>{@link com.sun.webui.jsf.theme.ThemeImage.HEIGHT_SUFFIX}</li>
     * <li>{@link com.sun.webui.jsf.theme.ThemeImage.WIDTH_SUFFIX}</li>
     * <li>{@link com.sun.webui.jsf.theme.ThemeImage.UNITS_SUFFIX}</li>
     * </ul>
     * If <code>key</code> is not defined <code>key</code> is returned.
     */
    public ThemeImage getImage(String key) {
	// make sure to setIcon on parent and not the icon itself which
	// now does the theme stuff in the component
       
	String path = null;
	/*
	ThemeResources themeResources =
	    (ThemeResources)threadThemeResources.get();
	ThemeReference[] themeReferences = themeResources.getThemeReferences();
	ClassLoader classLoader = 
	    themeResources.getThemeContext().getDefaultClassLoader();
	Locale locale = themeResources.getLocale() == null ?
	    themeResources.getThemeContext().getDefaultLocale() :
		themeResources.getLocale();
	*/

	try {
	    path = imageResources.getString(key);
            path = translateURI(path);        

	    /*
	    path = (String)getProperty(
		themeReferences,
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key, locale, classLoader);
	    */
	} catch (MissingResourceException mre) {
	    Object[] params = { key }; 
	    String message = MessageUtil.getMessage
		    ("com.sun.webui.jsf.resources.LogMessages", 
		     "Theme.noIcon", params); 
	    throw new RuntimeException(message, mre);
	    /*
	    return null;
	    */
	}

	String alt = null;
	try {
	    alt = getMessage(imageResources.getString(key.concat(ALT_SUFFIX)));
	    /*
	    alt = (String)getProperty(
		themeReferences,
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key.concat(ThemeImage.ALT_SUFFIX), locale, classLoader);
	    */
	} catch (MissingResourceException mre) {
	}

	String title = null;
	/*
	try {
	    title = (String)getProperty(
		themeReferences,
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key.concat(ThemeImage.TITLE_SUFFIX), locale, classLoader);
	} catch (MissingResourceException mre) {
	}
	*/
	
	int ht = Integer.MIN_VALUE;
	try {
	    String height =
		imageResources.getString(key.concat(HEIGHT_SUFFIX));
	    /*
	    String height = (String)getProperty(
		themeReferences,
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key.concat(ThemeImage.HEIGHT_SUFFIX), locale, classLoader);
	    */
	    ht = Integer.parseInt(height); 
	} catch (MissingResourceException mre) {
	} catch (NumberFormatException nfe) {
	}

	int wt = Integer.MIN_VALUE;
	try {
	    String width =
		imageResources.getString(key.concat(WIDTH_SUFFIX));
	    /*
	    String width = (String)getProperty(
		themeReferences,
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key.concat(ThemeImage.WIDTH_SUFFIX), locale, classLoader);
	    */
	    wt = Integer.parseInt(width); 
	} catch (MissingResourceException mre) {
	} catch (NumberFormatException nfe) {
	}

	/*
	ThemeImage.UNITS units = ThemeImage.UNITS.none;
	try {
	    String unitString = (String)getProperty(
		themeReferences,
		ThemeResourceBundle.ThemeBundle.IMAGES,
		key.concat(ThemeImage.UNITS_SUFFIX), locale, classLoader);
	    units = ThemeImage.UNITS.valueOf(unitString);
	} catch (MissingResourceException mre) {
	} catch (NullPointerException npe) {
	}
	*/

	// We either need to pass themeContext or "fixUpPath".
	// It would be nice to provide both. The actual src
	// and the fixed up path
	//
	/*
	return new ThemeImage(wt, ht, null, alt, title, fixUpPath(path));
	*/
	return new ThemeImage(wt, ht, null, alt, title, path);
    }

    /**
     * Retrieves a String from the images ResourceBundle without the theme path 
     * prefix.
     *
     * @param key The key used to retrieve the message
     * @return A localized message string
     */
    public String getImageString(String key) {
        return imageResources.getString(key);
    }
}
