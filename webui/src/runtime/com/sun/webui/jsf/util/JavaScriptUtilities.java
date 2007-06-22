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

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeJavascript;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeTemplates;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.faces.extensions.avatar.components.ScriptsComponent;

/**
 * This class provides common methods for rendering JavaScript includes, default
 * properties, etc.
 */
public class JavaScriptUtilities {
    // The number of spaces to add to each level of indentation.
    public static final int INDENT_FACTOR = 4;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JavaScript config methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get JavaScript used to configure Dojo.
     *
     * Note: Must be rendered before including dojo.js in page.
     *
     * @param debug Enable JavaScript debugging.
     * @param parseWidgets Enable searching of dojoType widget tags.
     */
    public static String getDojoConfig(boolean debug, boolean parseWidgets) {
        Theme theme = getTheme();
        StringBuffer buff = new StringBuffer(256);

        try {
            JSONObject json = new JSONObject();
            json.put("isDebug", debug)
                .put("debugAtAllCosts", debug)
                .put("parseWidgets", parseWidgets);

            // Append djConfig properties.
            buff.append("var djConfig = ")
                .append(json.toString(INDENT_FACTOR))
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
     *
     * @param debug Flag indicating debugging is turned on.
     * @param ajaxify Flag indicating to include default Ajax implementation.
     */
    public static String getModuleConfig(boolean debug, boolean ajaxify) {
        Theme theme = getTheme();
        StringBuffer buff = new StringBuffer(256);

        // Append JavaScript.
        buff.append("dojo.hostenv.setModulePrefix(\"")
            .append(getTheme().getJSString(ThemeJavascript.MODULE_PREFIX))
            .append("\", \"")
            .append(theme.getPathToJSFile((debug)
                ? ThemeJavascript.UNCOMPRESSED_MODULE_PATH
                : ThemeJavascript.MODULE_PATH))
            .append("\");\n")
            .append(getModule("*"))
            .append("\n")
            .append(getModule("widget.*"))
            .append("\n");

        // Include default Ajax implementation.
        if (ajaxify) {
            buff.append(getModule("widget.jsfx.*"))
            .append("\n");
        }

        // Output includes for debugging. This will ensure that JavaScript
        // files are accessible to JavaScript debuggers.
        if (debug) {
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
        renderJavaScriptInclude(component, writer, ThemeJavascript.DOJO);
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
        Map requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        if (!requestMap.containsKey(ScriptsComponent.AJAX_JS_LINKED)) {
            renderJavaScriptInclude(component, writer, ThemeJavascript.JSFX);
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
        renderJavaScriptInclude(component, writer, ThemeJavascript.JSON);
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
        Map requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        if (!requestMap.containsKey(ScriptsComponent.PROTOTYPE_JS_LINKED)) {
            renderJavaScriptInclude(component, writer, ThemeJavascript.PROTOTYPE);
            requestMap.put(ScriptsComponent.PROTOTYPE_JS_LINKED, Boolean.TRUE);
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
     * @param name The JavaScript object name to append.
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
}
