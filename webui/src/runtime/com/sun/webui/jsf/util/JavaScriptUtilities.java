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
package com.sun.webui.jsf.util;

import com.sun.faces.extensions.avatar.components.ScriptsComponent;
import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeContext;
import com.sun.webui.jsf.theme.JSFThemeContext;
import com.sun.webui.jsf.theme.ThemeJavascript;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * This class provides common methods for rendering JavaScript includes, default
 * properties, etc.
 */
public class JavaScriptUtilities {
    // The key used to enable default Ajax implementation.
    private static final String AJAXIFY_KEY = "com_sun_webui_jsf_util_ajaxify";

    // The key used to enable JavaScript debugging.
    private static final String DEBUG_KEY = "com_sun_webui_jsf_util_debug";

    // parseWidgets Enable searching of dojoType widget tags.
    private static final String PARSEWIDGETS_KEY = "com_sun_webui_jsf_util_parseWidgets";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Global methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Test flag to enable default Ajax implementation.
     * 
     * @return true if enabled.
     */
    public static boolean isAjaxify() {
        return getRequestMap().containsKey(AJAXIFY_KEY) ;
    }

    /**
     * Set flag to enable default Ajax implementation.
     *
     * @param enable Enable default Ajax implementation.
     */
    public static void setAjaxify(boolean enable) {
        getRequestMap().put(AJAXIFY_KEY, (enable) ? Boolean.TRUE : null);
    }

    /**
     * Test flag to enable JavaScript debugging.
     * 
     * @return true if enabled.
     */
    public static boolean isDebug() {
        // Debugging is typically enabled via the head or themeLinks tags.
        // However, "debug" may also be appended URLs as a query param. This
        // allows quick testing when the JSP page cannot be edited directly.
        return (getRequestMap().containsKey(DEBUG_KEY) 
            || getRequestParameterMap().containsKey("debug"));
    }

    /**
     * Set flag to enable JavaScript debugging.
     *
     * @param enable Enable JavaScript debugging.
     */
    public static void setDebug(boolean enable) {
        getRequestMap().put(DEBUG_KEY, (enable) ? Boolean.TRUE : null);
    }

    /**
     * Test flag to enable searching of dojoType widget tags.
     * 
     * @return true if enabled.
     */
    public static boolean isParseWidgets() {
        return getRequestMap().containsKey(PARSEWIDGETS_KEY);
    }

    /**
     * Set flag to enable searching of dojoType widget tags.
     *
     * @param enable Enable searching of dojoType widget tags.
     */
    public static void setParseWidgets(boolean enable) {
        getRequestMap().put(PARSEWIDGETS_KEY, (enable) ? Boolean.TRUE : null);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JavaScript config methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get JavaScript used to configure Dojo.
     *
     * Note: Must be rendered before including dojo.js in page.
     */
    public static String getDojoConfig() {
        StringBuffer buff = new StringBuffer(256);

        try {
            JSONObject json = new JSONObject();
            json.put("isDebug", isDebug())
                .put("debugAtAllCosts", isDebug())
                .put("parseWidgets", isParseWidgets())
                .put(getModuleName("theme"), getThemeJavaScript(
                    FacesContext.getCurrentInstance()));

            // Append djConfig properties.
            buff.append("var djConfig = ")
                .append(JSONUtilities.getString(json))
                .append(";\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }    
        return buff.toString();
    }

    /**
     * Get JavaScript used to configure module resources.
     *
     * Note: Must be rendered before including widget.js in page, but after
     * formElements.js.
     */
    public static String getModuleConfig() {
        StringBuffer buff = new StringBuffer(256);

        // Append JavaScript.
        buff.append("dojo.hostenv.setModulePrefix(\"")
            .append(getTheme().getJSString(ThemeJavascript.MODULE_PREFIX))
            .append("\", \"")
            .append(getTheme().getPathToJSFile((isDebug())
                ? ThemeJavascript.MODULE_PATH_UNCOMPRESSED
                : ThemeJavascript.MODULE_PATH))
            .append("\");\n")
            .append(getModule("*"))
            .append("\n")
            .append(getModule("theme.*"))
            .append("\n")	
            .append(getModule("widget.*"))
            .append("\n");

        // Include default Ajax implementation.
        if (isAjaxify()) {
            buff.append(getModule("widget.jsfx.*"))
            .append("\n");
        }

        // Output includes for debugging. This will ensure that JavaScript
        // files are accessible to JavaScript debuggers.
        if (isDebug()) {
            buff.append("dojo.hostenv.writeIncludes();")
                .append("\n");
        }
        return buff.toString();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JavaScript include methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderDojoInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        renderJavaScriptInclude(component, writer, (isDebug())
            ? ThemeJavascript.DOJO_UNCOMPRESSED
            : ThemeJavascript.DOJO);
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     */
    public static void renderGlobalInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        String javascripts[] = getTheme().getGlobalJSFiles();
        if (javascripts == null) {
            return;
        }
        for (int i = 0; i < javascripts.length; i++) {
            Object file = javascripts[i];
            if (file == null) {
                continue;
            }
            writer.startElement("script", component);
            writer.writeAttribute("type", "text/javascript", null);
            writer.writeURIAttribute("src", file.toString(), null);
            writer.endElement("script");
            writer.write("\n");
        }
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJsfxInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        Map requestMap = getRequestMap();
        if (!requestMap.containsKey(ScriptsComponent.AJAX_JS_LINKED)) {
            renderJavaScriptInclude(component, writer, (isDebug())
                ? ThemeJavascript.JSFX_UNCOMPRESSED
                : ThemeJavascript.JSFX);
            requestMap.put(ScriptsComponent.AJAX_JS_LINKED, Boolean.TRUE);
        }
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJsonInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        renderJavaScriptInclude(component, writer, (isDebug())
                ? ThemeJavascript.JSON_UNCOMPRESSED
                : ThemeJavascript.JSON);
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderPrototypeInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        Map map = getRequestMap();
        if (!map.containsKey(ScriptsComponent.PROTOTYPE_JS_LINKED)) {
            renderJavaScriptInclude(component, writer, (isDebug())
                ? ThemeJavascript.PROTOTYPE_UNCOMPRESSED
                : ThemeJavascript.PROTOTYPE);
            map.put(ScriptsComponent.PROTOTYPE_JS_LINKED, Boolean.TRUE);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Rendering methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Returns JavaScript to obtain the DOM node associated with the given
     * component.
     * 
     * When complex components are rendered, a DOM object corresponding to the
     * component is created. To manipulate the component on the client side, we
     * will invoke functions on the DOM object.
     * 
     * Providing a component, with a client id of "form1:btn1", will return
     * "document.getElementById('form1:btn1')". This JavaScript obtains the
     * DOM object associated the HTML element. Thus, we can disable a button 
     * using "document.getElementById('form1:btn1').disable(true);"
     *
     * @param context The current FacesContext.
     * @param component The current component being rendered.
     */
    public static String getDomNode(FacesContext context,
            UIComponent component) {
        StringBuffer buff = new StringBuffer(128);
        buff.append("document.getElementById('")
            .append(component.getClientId(context))
            .append("')");
        return buff.toString();
    }

    /**
     * Returns JavaScript used to require a Dojo module. For example, a value of
     * For example, a value of "widget.*" will return
     * "dojo.require('webui.suntheme.widget.*')" for a theme, named 
     * "suntheme".
     *
     * @param name The JavaScript object name to append.
     */
    public static String getModule(String name) {
        StringBuffer buff = new StringBuffer(128);
        buff.append("dojo.require('")
            .append(getModuleName(name))
            .append("');");
        return buff.toString();
    }

    /**
     * Returns a string comprised of a theme prifix and the given module name.
     * For example, a value of "widget.button" will return 
     * "webui.suntheme.widget.button" for a theme, named "suntheme".
     *
     * @param name The module to append to the theme prefix.
     */
    public static String getModuleName(String name) {
        StringBuffer buff = new StringBuffer(128);
        buff.append(getTheme().getJSString(ThemeJavascript.MODULE_PREFIX))
            .append(".")
            .append(name);
        return buff.toString();
    }

    /**
     * Returns a string comprised of a theme prifix and the given widget name.
     * For example, a value of "button" will return "webui.suntheme:button" for
     * a theme, named "suntheme".
     *
     * @param name The widget name to append to the namespace prefix.
     */
    public static String getNamespace(String name) {
        StringBuffer buff = new StringBuffer(128);
        buff.append(getTheme().getJSString(ThemeJavascript.MODULE_PREFIX))
            .append(":")
            .append(name);
        return buff.toString();
    }

    /**
     * Returns JavaScript to obtain the widget associated with the 
     * component. Providing a component, with a client id of "form1:btn1",
     * will return "dojo.widget.byId('form1:btn1');".
     *       
     * @param context The current FacesContext.
     * @param component The current component being rendered.
     */ 
    public static String getWidget(FacesContext context,
            UIComponent component) {
        StringBuffer buff = new StringBuffer(128);
        buff.append("dojo.widget.byId('")
            .append(component.getClientId(context))
            .append("')");
        return buff.toString();        
    }

    /**
     * Render JavaScript in the page, including enclosing script tags.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param js The JavaScript string to render.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJavaScript(UIComponent component,
            ResponseWriter writer, String js) throws IOException {
        if (js == null) {
            return;
        }
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.write("\n");
        writer.write(js);
        writer.endElement("script");
        writer.write("\n");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private static Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    // Helper method to get request map.
    private static Map getRequestMap() {
        return FacesContext.getCurrentInstance().getExternalContext().
            getRequestMap();
    }

    // Helper method to get request parameter map.
    private static Map getRequestParameterMap() {
        return FacesContext.getCurrentInstance().getExternalContext().
            getRequestParameterMap();
    }

    /**
     * Render given JavaScript file in page, including script tags.
     *
     * Note: JavaScript includes must be output in page prior to instantiating
     * widgets. This can be done via the head, themeLinks, or portalTheme tags,
     * but not via any other component renderer. Thus, this method is declared
     * private to ovoid misuse.
     * 
     * If JavaScript includes are output by renderers, timing issues can occur 
     * when client-side widgets and server-side components are rendered in the 
     * same page. For example, button HTML may be rendered as a JSON property
     * (the child of a widget), which also contains a JavaScript include. In 
     * this scenario, ther buttons in the page may not initialize correctly
     * because the widget has not added the JavaScript include, yet. See CR 6517246.
     *
     * @param component The current component being rendered.
     * @param writer The current ResponseWriter.
     * @param file The JavaScript file to include.
     */
    private static void renderJavaScriptInclude(UIComponent component,
            ResponseWriter writer, String file) throws  IOException {
        if (file == null) {
	    return;
	}

	String jsFile = getTheme().getPathToJSFile(file);
	if (jsFile == null) {
	    return;
	}
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.writeURIAttribute("src", jsFile, null);
        writer.endElement("script");
        writer.write("\n");
    }

    /**
     * Return JSONObject containing the following properties in order
     * to initialize the JavaScript theme.
     * <ul>
     * <li>prefix - the application contex plust the theme servlet prefix</li>
     * <li>module - the value of ThemeJavascript.THEME_MODULE</li>
     * <li>modulePath - the value of ThemeJavascript.THEME_MODULE_PATH or
     * ThemeJavascript.THEME_MODULE_PATH_UNCOMPRESSED if in debug mode.</li>
     * <li>bundle - the value of ThemeJavascript.THEME_BUNDLE</li>
     * <li>custom - a JSONArray of the application's theme javascript
     * bundles. @see com.sun.webui.theme.ThemeContext#THEME_RESOURCES </li>
     * </ul>
     */
    private static JSONObject getThemeJavaScript(FacesContext context) 
            throws JSONException {

	// This is the namespace for the js theme.
	// It is webui.@THEME@.theme. It is the "module" parameter for
	// dojo.requireLocalization and dojo.i18n.getLocalization
	//
	String themeModule =
	    getTheme().getJSString(ThemeJavascript.THEME_MODULE);

	// The theme module path prefix.
	//
	String themeModulePath = isDebug() ?
	    getTheme().getJSString(
		ThemeJavascript.THEME_MODULE_PATH_UNCOMPRESSED) :
	    getTheme().getJSString(ThemeJavascript.THEME_MODULE_PATH);

	// The "bundle" parameter for 
	// dojo.requireLocalization and dojo.i18n.getLocalization.
	// It is the base name for the theme properties js file in the 
	// nls directories, @THEME@.js
	//
	String themeBundle =
	    getTheme().getJSString(ThemeJavascript.THEME_BUNDLE);


	// While "toString" is not supposed to be guaranteed, the javadoc
	// says it returns the complete lang, country and variant
	// separated by "underbars".
	//
	// It's not clear if we want to be explicit in terms of 
	// loading an explicit locale. It may be sufficient to 
	// just allow dojo to load its notion of the "current"
	// locale.
	//
	String dojoLocale = 
	    context.getViewRoot().getLocale().toString().replaceAll("_", "-");

	// Get the ThemeContext for the application's theme resources
	// and the appcontext and the theme servlet context combined
	// in the "getResourcePath" call.
	//
	ThemeContext themeContext = JSFThemeContext.getInstance(context);

	// Fool getResourcePath into returning the prefix.
	// by passing "", since we don't have path, we just want the
	// prefix. This will have a trailing "/", so get rid of it.
	//
	String themePrefix = themeContext.getResourcePath("");
	int lastSlash = themePrefix.lastIndexOf("/");
	if (lastSlash > 0) {
	    themePrefix = themePrefix.substring(0, lastSlash);
	}

	// Get the application's custom theme package(s).
	//
	JSONArray appthemes = null;
	String appThemeResources[] = themeContext.getThemeResources();
	if (appThemeResources != null) {
	    // Format this as a javascript array
	    //
	    appthemes = new JSONArray();
	    for (int i = 0; i < appThemeResources.length; ++i) {
		appthemes.put(i, appThemeResources[i]);
	    }
	}
        JSONObject json = new JSONObject();
        json.put("prefix", themePrefix)
            .put("module", themeModule)
            .put("modulePath", themeModulePath)
            .put("bundle", themeBundle)
            .put("locale", dojoLocale)
            .put("custom", appthemes);
        return json;
    }
}
