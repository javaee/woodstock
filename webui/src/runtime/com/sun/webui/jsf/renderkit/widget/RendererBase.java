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
package com.sun.webui.jsf.renderkit.widget;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class provides common methods for widget renderers.
 *
 * Renderers extending this class are expected to output JSON containing 
 * key-value pairs of component properties. The JSON object given to the widget
 * may contain further key-value pairs of properties or an HTML string. This 
 * base class shall ensure that all properties are obtained and rendered at the
 * appropriate time.
 */
abstract public class RendererBase extends Renderer {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render the beginning of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * Note: This method is used to gather component properties and JavaScript
     * includes. The actual rendering is defered to the encodeEnd method. This
     * helps ensure that default properties and JavaScript includes have been
     * gathered from all subcomponents. Thus, default properties are rendered
     * using a single script tag and JavaScript includes are rendered before
     * any widgets are instantiated. 
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeBegin(FacesContext context, UIComponent component) 
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }

        // Get writer.
        ResponseWriter writer = context.getResponseWriter();

        // As both an optimization, and to update client-side properties,
        // JavaScript is not output to instantiate widget children. Widget
        // children shall output JSON properties only.
        boolean isWidgetChild = isWidgetChild(context, component);

        // Not all components need to render JavaScript and instantiate a
        // client-side widget. Therefore, if getWidgetName() returns null, only
        // JSON properties are output.
        if (isWidgetChild || getWidgetName(context, component) == null) {
            return;
        }

        // Render temporary place holder to position widget in page -- 
        // ultimately replaced by document fragment.
        writer.startElement("span", component);
        writer.writeAttribute("id", component.getClientId(context), null);
        writer.endElement("span");

        // Note: Leading \n char causes grief with CSS float.

        // Render enclosing tag -- must be located after div.
        writer.startElement("script", component);
        writer.writeAttribute("type", "text/javascript", null);
        writer.write("\n");

        // Render JavaScript to instantiate Dojo widget.
        writer.write(JavaScriptUtilities.getModuleName(
            "widget.common.replaceFragment"));
        writer.write("(");
    }

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }

        try {
            // Get properties.
            JSONObject json = getProperties(context, component);
            setCoreProperties(context, component, json);
                        
            // Note: Component and child properties are always output together.
            ResponseWriter writer = context.getResponseWriter();
            writer.write(JSONUtilities.getString(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Render the ending of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * Note: This method is used to render JSON properties. If the component
     * does not have a parent of type Widget, JavaScript is also rendered to
     * instantiate a Dojo widget. JavaScript includes and default properties
     * are only rendered if and only if the component does not have an ancestor
     * of type Widget. Thus, this ensures that default properties are rendered
     * using a single script tag and JavaScript includes are rendered before
     * any widgets are instantiated.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }

        // Get writer.
        ResponseWriter writer = context.getResponseWriter();

        // As both an optimization, and to update client-side properties,
        // JavaScript is not output to instantiate widget children. Widget
        // children shall output JSON properties only.
        boolean isWidgetChild = isWidgetChild(context, component);

        // Not all components need to render JavaScript and instantiate a
        // client-side widget. Therefore, if getWidgetName() returns null, only
        // JSON properties are output.
        if (isWidgetChild || getWidgetName(context, component) == null) {
            return;
        }

        // Render enclosing tag.
        writer.write(");\n");
        writer.endElement("script");

        // Note: Trailing \n char causes grief with CSS float.
    }

    /**
     * Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns false.
     */
    public boolean getRendersChildren() {
        return true; 
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Abstract methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     */
    abstract protected String getModule(FacesContext context,
        UIComponent component) throws IOException;

    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    abstract protected JSONObject getProperties(FacesContext context,
        UIComponent component) throws IOException, JSONException;

    /**
     * Get the template path for this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     */
    protected String getTemplatePath(FacesContext context,
	    UIComponent component) throws IOException {
	return (String)component.getAttributes().get("templatePath");
    }

    /**
     * Get the name of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     */
    abstract protected String getWidgetName(FacesContext context,
        UIComponent component) throws IOException;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This method may be used to set core name/value pairs for the given
     * JSONObject.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to add name/value pairs to.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    protected void setCoreProperties(FacesContext context, UIComponent component, 
            JSONObject json) throws IOException, JSONException {
        // Set properties.
        json.put("id", component.getClientId(context))
            .put("module", getModule(context, component))
            .put("widgetName", getWidgetName(context, component))
            .put("templatePath", getTemplatePath(context, component));
    }

    // Helper method to get Theme objects.
    protected Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to test if the given component is a widget child. If the
     * component's parent renderer is an instance of RendererBase, it means that
     * the parent is also widget; thus, true is returned.
     */
    private boolean isWidgetChild(FacesContext context, UIComponent component) {
        // Get component parent.
        UIComponent parent = component.getParent();
        if (parent == null) {
            return false;
        }

        // Get family and renderer type.
        String family = parent.getFamily();
        String type = parent.getRendererType();
        if (family == null || type == null) {
            return false;
        }

        // Get renderer.
        Renderer renderer = context.getRenderKit().getRenderer(family, type);

        return (renderer instanceof RendererBase);
    }
}
