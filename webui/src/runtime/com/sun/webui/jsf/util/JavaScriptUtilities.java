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
import java.util.StringTokenizer;

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
    // The key used to enable JavaScript debugging.
    private static final String DEBUG_KEY = "com_sun_webui_jsf_util_debug";

    // Key used to include JSF Extensions.
    private static final String JSFX_KEY = "com_sun_webui_jsf_util_jsfx";

    // Key used to parse HTML markup on load.
    private static final String PARSE_ONLOAD_KEY = "com_sun_webui_jsf_util_parseOnLoad";

    // Key used to include style sheets.
    private static final String STYLESHEET_KEY = "com_sun_webui_jsf_util_styleSheet";

    // Key used to include all widgets.
    private static final String WEBUI_ALL_KEY = "com_sun_webui_jsf_util_webuiAll";

    // Key used to include Ajax functionality.
    private static final String WEBUI_AJAX_KEY = "com_sun_webui_jsf_util_webuiAjax";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Global methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Test flag to enable JavaScript debugging.
     * 
     * @return true if enabled.
     */
    public static boolean isDebug() {
        return isEnabled(DEBUG_KEY, "debug");
    }

    /**
     * Set flag to enable JavaScript debugging.
     *
     * @param enable Enable JavaScript debugging.
     */
    public static void setDebug(boolean enable) {
        getRequestMap().put(DEBUG_KEY, (enable) ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Test flag to include JSF Extensions.
     * 
     * @return true if included.
     */
    public static boolean isJsfx() {
        return isEnabled(JSFX_KEY, "jsfx");
    }

    /**
     * Set flag to include JSF Extensions.
     *
     * @param include Include JSF Extensions.
     */
    public static void setJsfx(boolean include) {
        getRequestMap().put(JSFX_KEY, (include) ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Test flag indicating to parse HTML markup.
     * 
     * @return true if enabled.
     */
    public static boolean isParseOnLoad() {
        return isEnabled(PARSE_ONLOAD_KEY, "parseOnLoad");
    }

    /**
     * Set flag indicating to parse HTML markup
     *
     * @param enable indicating to parse HTML markup
     */
    public static void setParseOnLoad(boolean enable) {
        getRequestMap().put(PARSE_ONLOAD_KEY, (enable) ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Test flag to include style sheets.
     * 
     * @return true if enabled.
     */
    public static boolean isStyleSheet() {
        return isEnabled(STYLESHEET_KEY, "styleSheet");
    }

    /**
     * Set flag to include style sheets.
     *
     * @param enable Include style sheets.
     */
    public static void setStyleSheet(boolean enable) {
        getRequestMap().put(STYLESHEET_KEY, (enable) ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Test flag to include all tag library functionality.
     * 
     * @return true if included.
     */
    public static boolean isWebuiAll() {
        return isEnabled(WEBUI_ALL_KEY, "webuiAll");
    }

    /**
     * Set flag to include all widgets.
     *
     * @param include Include all widgets.
     */
    public static void setWebuiAll(boolean include) {
        getRequestMap().put(WEBUI_ALL_KEY, (include) ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Test flag to include Ajax functionality.
     * 
     * @return true if included.
     */
    public static boolean isWebuiAjax() {
        return isEnabled(WEBUI_AJAX_KEY, "webuiAjax");
    }

    /**
     * Set flag to include Ajax functionality.
     *
     * @param include Include Ajax functionality.
     */
    public static void setWebuiAjax(boolean include) {
        getRequestMap().put(WEBUI_AJAX_KEY, (include) ? Boolean.TRUE : Boolean.FALSE);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JavaScript methods
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
     * "webui.suntheme._dojo.require('webui.suntheme.widget.*')" for a theme, 
     * named "suntheme".
     *
     * @param name The JavaScript object name to append.
     */
    public static String getModule(String name) {
        StringBuffer buff = new StringBuffer(128);
        buff.append(getModuleName("_dojo"))
            .append(".require('")
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
        buff.append(getTheme().getJSString(ThemeJavascript.MODULE));
        if (name != null) {
            buff.append(".")
                .append(name);
        }
        return buff.toString();
    }

    /**
     * Returns JavaScript to obtain the widget associated with the 
     * component. Providing a component, with a client id of "form1:btn1",
     * will return "webui.@THEME@._dojo.widget.byId('form1:btn1');".
     *       
     * @param context The current FacesContext.
     * @param component The current component being rendered.
     */ 
    public static String getWidget(FacesContext context,
            UIComponent component) {
        StringBuffer buff = new StringBuffer(128);
        buff.append(getModuleName("_dojo"))
            .append(".widget.byId('")
            .append(component.getClientId(context))
            .append("')");
        return buff.toString();        
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Rendering methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render bootstrap.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderBootstrap(UIComponent component,
            ResponseWriter writer) throws IOException, JSONException {
        // Render config.
        renderJavaScript(component, writer, getBootstrapConfig());

        // Render bootstrap include.
        renderBootstrapInclude(component, writer);
        renderWebuiInclude(component, writer);

        // Render JSF Extensions include.
        if (isJsfx() && isWebuiAjax()) {
            renderPrototypeInclude(component, writer);
            renderJsfxInclude(component, writer);
        }
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
        renderJavaScript(component, writer, js, false);
    }

    /**
     * Render JavaScript in the page, including enclosing script tags.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param js The JavaScript string to render.
     * @param defer Wait until the page has loaded before executing code.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJavaScript(UIComponent component,
            ResponseWriter writer, String js, boolean defer) throws IOException {
        if (js == null) {
            return;
        }
        renderJavaScriptBegin(component, writer, defer);
        writer.write(js);
        renderJavaScriptEnd(component, writer, defer);
    }

    /**
     * Render JavaScript in the page, including enclosing script tags.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param defer Wait until the page has loaded before executing code.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJavaScriptBegin(UIComponent component,
            ResponseWriter writer, boolean defer) throws IOException {
        if (isDebug()) {
            writer.write("\n");
        }
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);

        if (defer) {
            // The webui.@THEME@._dojo.addOnLoad function starts scripts after 
            // the DOM has loaded but before all of the page elements have 
            // loaded, which means your script doesn't have to wait for images 
            // and other large resources before it manipulates page structure.
            writer.write(getModuleName("widget.common"));
            writer.write("._addOnLoad(function() {");
        }
    }

    /**
     * Render JavaScript in the page, including enclosing script tags.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param defer Wait until the page has loaded before executing code.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderJavaScriptEnd(UIComponent component,
            ResponseWriter writer, boolean defer) throws IOException {
        if (defer) {
            writer.write("});");
        }
        writer.endElement("script");
    }

    /**
     * Render onLoad JavaScript.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     * @param js The JavaScript string to render.
     *
     * @exception IOException if an input/output error occurs.
     */
    public static void renderOnLoad(UIComponent component,
            ResponseWriter writer, String js) throws IOException {
        if (js == null) {
            return;
        }
        renderJavaScript(component, writer, getModuleName("widget.common") + 
            ".addOnLoad(function() {" + js + "});");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private config methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get properties used to configure Ajax.
     */
    private static JSONObject getAjaxConfig() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("module", getTheme().getJSString(ThemeJavascript.JSFX_MODULE))
            .put("isAjax", true) // Load Ajax resources.
            .put("isJsfx", isJsfx());
        return json;
    }

    /**
     * Helper method to render config.
     */
    private static String getBootstrapConfig() throws JSONException {
        Theme theme = getTheme();
        JSONObject json = new JSONObject();

        StringBuffer buff = new StringBuffer(256);
        String configVar = getModuleName(null) + "Config";
        buff.append("if (typeof ")
            .append(configVar)
            .append(" == \"undefined\") { this.")
            .append(configVar)
            .append(" = ");

        // Add config properties.
        json.put("ajax", getAjaxConfig())
            .put("isDebug", isDebug())
            .put("modulePath", theme.getPathToJSFile((isDebug())
                ? ThemeJavascript.MODULE_PATH_UNCOMPRESSED
                : ThemeJavascript.MODULE_PATH))
            .put("parseOnLoad", isParseOnLoad())
            .put("theme", getThemeConfig(FacesContext.getCurrentInstance()))
            .put("isStyleSheet", isStyleSheet())
            .put("webuiAll", isWebuiAll())
            .put("webuiAjax", isWebuiAjax());

        buff.append(JSONUtilities.getString(json))
            .append(";}");
        return buff.toString();
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
    private static JSONObject getThemeConfig(FacesContext context) 
            throws JSONException {
        Theme theme = getTheme();

	// The theme module path prefix.
	//
	String themeModulePath = theme.getPathToJSFile((isDebug())
            ? ThemeJavascript.THEME_MODULE_PATH_UNCOMPRESSED
            : ThemeJavascript.THEME_MODULE_PATH);

	// The "bundle" parameter for 
	// webui.@THEME@._dojo.requireLocalization and 
        // webui.@THEME@._dojo.i18n.getLocalization.
	// It is the base name for the theme properties js file in the 
	// nls directories, @THEME@.js
	//
	String themeBundle = theme.getJSString(ThemeJavascript.THEME_BUNDLE);

	// While "toString" is not supposed to be guaranteed, the javadoc
	// says it returns the complete lang, country and variant
	// separated by "underbars".
	//
	// It's not clear if we want to be explicit in terms of 
	// loading an explicit locale. It may be sufficient to 
	// just allow dojo to load its notion of the "current"
	// locale.
	//
	String themeLocale = context.getViewRoot().getLocale().toString().toLowerCase().replaceAll("_", "-");

	// Get the ThemeContext for the application's theme resources
	// and the appcontext and the theme servlet context combined
	// in the "getResourcePath" call.
	//
	ThemeContext themeContext = JSFThemeContext.getInstance(context);

	// Get the application's custom theme package(s).
	//
        JSONArray customThemes = null;
	String customThemeResources[] = themeContext.getThemeResources();
        if (customThemeResources != null) {
            customThemes = new JSONArray(); 

            // Fool getResourcePath into returning the prefix.
            // by passing "", since we don't have path, we just want the
            // prefix. This will have a trailing "/", so get rid of it.
            //
            String themePrefix = themeContext.getResourcePath("");
            int lastSlash = themePrefix.lastIndexOf("/");
            if (lastSlash > 0) {
                themePrefix = themePrefix.substring(0, lastSlash);
            }

            // Parse the "THEME_RESOURCES" init param, which contains a space 
            // separated list of basenames identifying an application's 
            // javascript theme files. The last segment of this "dot" separated 
            // string, is treated as the "bundle", and the initial segments are
            // treated as the module path (e.g., "theme.testapp_theme"). 
            for (int i = 0; i < customThemeResources.length; ++i) {
                String themePackage = customThemeResources[i];
                String segments[] = (themePackage != null)
                    ? themePackage.split("\\.") : null;

                // Get theme bundle and module path.
                if (segments != null) {
                    String bundle = segments[segments.length - 1];
                    String modulePath = null;

                    // Get module path, if any.
                    if (segments.length > 1) {
                        String path = themePackage.substring(0, 
                            themePackage.length() - bundle.length());
                        modulePath = themePrefix + "/" + path.replace('.', '/');
                    }

                    // Append properties.
                    JSONObject json = new JSONObject();
                    customThemes.put(json);
                    json.put("bundle", bundle)
                        .put("modulePath", modulePath);
                }
            }
	}

        JSONObject json = new JSONObject();
        json.put("bundle", themeBundle)
            .put("custom", customThemes)
            .put("locale", themeLocale)
            .put("modulePath", themeModulePath);

        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private helper methods
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
     * Test boolean values stored in request or parameter maps.
     * 
     * Note: Values are typically enabled via the head or themeLinks tags.
     * However, "debug" may also be appended URLs as a query param. This allows
     * quick testing when the JSP page cannot be edited directly.
     * 
     * @param requestKey A key in the request map.
     * @param paramKey A key in the request parameters map.
     * @return true if enabled.
     */
    private static boolean isEnabled(String requestKey, String paramKey) {
        boolean result = false;

        // Test if value set in request parameter map.
        String s = (String) getRequestParameterMap().get(paramKey);
        if (s != null) {
            result = true; // Allow empty params (e.g., ?debug).
            StringTokenizer st = new StringTokenizer(s, "=");
            if (st.hasMoreTokens()) {
                result = new Boolean(st.nextToken()).booleanValue();
            }
        } else {
            // Test if value set in request map -- params take precedence.
            Boolean b = (Boolean) getRequestMap().get(requestKey);
            if (b != null ) {
                result = b.booleanValue();
            }
        }
        return result;
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
    private static void renderBootstrapInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        renderJavaScriptInclude(component, writer, (isDebug())
            ? ThemeJavascript.BOOTSTRAP_UNCOMPRESSED
            : ThemeJavascript.BOOTSTRAP);
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
        if (isDebug()) {
            writer.write("\n");
        }
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.writeURIAttribute("src", jsFile, null);
        writer.endElement("script");
    }

    /**
     * Helper method to render JavaScript include.
     *
     * @param component UIComponent to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private static void renderJsfxInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        // Note: JavaScript shall be included client-side, but still need to
        // register with the scripts tag.
        Map requestMap = getRequestMap();
        if (!requestMap.containsKey(ScriptsComponent.AJAX_JS_LINKED)) {
//            renderJavaScriptInclude(component, writer, (isDebug())
//                ? ThemeJavascript.JSFX_UNCOMPRESSED
//                : ThemeJavascript.JSFX);
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
    private static void renderPrototypeInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        // Note: JavaScript shall be included client-side, but still need to
        // register with the scripts tag.
        Map map = getRequestMap();
        if (!map.containsKey(ScriptsComponent.PROTOTYPE_JS_LINKED)) {
//            renderJavaScriptInclude(component, writer, (isDebug())
//                ? ThemeJavascript.PROTOTYPE_UNCOMPRESSED
//                : ThemeJavascript.PROTOTYPE);
            map.put(ScriptsComponent.PROTOTYPE_JS_LINKED, Boolean.TRUE);
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
    private static void renderWebuiInclude(UIComponent component,
            ResponseWriter writer) throws IOException {
        String key = null; 
        if (isWebuiAll()) { 
            if (isWebuiAjax()) { 
                key = isDebug() 
                    ? ThemeJavascript.WEBUI_JSFX_ALL_UNCOMPRESSED 
                    : ThemeJavascript.WEBUI_JSFX_ALL; 
            } else { 
                key = isDebug() 
                    ? ThemeJavascript.WEBUI_ALL_UNCOMPRESSED 
                    : ThemeJavascript.WEBUI_ALL; 
            } 
        } else { 
            if (isWebuiAjax()) { 
                key = isDebug() 
                    ? ThemeJavascript.WEBUI_JSFX_UNCOMPRESSED 
                    : ThemeJavascript.WEBUI_JSFX; 
            } else { 
                key = isDebug() 
                    ? ThemeJavascript.WEBUI_UNCOMPRESSED 
                    : ThemeJavascript.WEBUI; 
            } 
        } 
        renderJavaScriptInclude(component, writer, key);
    }
}
