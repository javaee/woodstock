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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.webui.jsf.component.Widget;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import java.util.Map;
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
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        // Do nothing...
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
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        // Do nothing... Children are rendered when obtaining component 
        // properties via the encodeBegin method.
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
    @Override
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
        String widgetType = ((Widget) component).getWidgetType();
        boolean isSubComponent = component.getParent() instanceof Widget;

        // Render for JSF facets, but not subcomponents.
        if (!isSubComponent && widgetType != null) {
            // Render temporary place holder to position widget in page -- 
            // ultimately replaced by document fragment.
            writer.startElement("span", component);
            writer.writeAttribute("id", component.getClientId(context), null);
            writer.endElement("span");

            // Render enclosing tag -- must be located after div.
            writer.write("\n");
            writer.startElement("script", component);
            writer.writeAttribute("type", "text/javascript", null);
            writer.write("\n");

            // Render JavaScript to instantiate Dojo widget.
            writer.write(JavaScriptUtilities.getModule("widget.*"));
            writer.write("\n");
            writer.write(JavaScriptUtilities.getModuleName(
                    "widget.common.createWidget"));
            writer.write("(");
        }

        try {
            // Always render properties.
            writer.write(getProperties(context, component).toString(
                    JavaScriptUtilities.INDENT_FACTOR));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Render for JSF facets, but not subcomponents.
        if (!isSubComponent && widgetType != null) {
            // Render enclosing tag.
            writer.write(");\n");
            writer.endElement("script");
            writer.write("\n");
        }
    }

    /**
     * Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns false.
     */
    @Override
    public boolean getRendersChildren() {
        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * This method may be used to add attribute name/value pairs to the given
     * JSONObject.
     *
     * @param names Array of attribute names to be passed through.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to add name/value pairs to.
     *
     * @exception IOException if an input/output error occurs
     */
    protected void addAttributeProperties(String names[], UIComponent component,
            JSONObject json) throws JSONException {
        if (names == null) {
            return;
        }
        Map attributes = component.getAttributes();
        for (int i = 0; i < names.length; i++) {
            Object value = attributes.get(names[i]);
            if (value != null && value instanceof Integer) {
                if (((Integer) value).intValue() == Integer.MIN_VALUE) {
                    continue;
                }
            }
            json.put(names[i], value);
        }
    }

    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    abstract protected JSONArray getModules(FacesContext context,
            UIComponent component) throws JSONException;

    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    abstract protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException;

    /**
     * This method may be used to set core name/value pairs for the given
     * JSONObject.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to add name/value pairs to.
     *
     * @exception IOException if an input/output error occurs
     */
    protected void setCoreProperties(FacesContext context, UIComponent component,
            JSONObject json) throws JSONException {
        json.put("id", component.getClientId(context)).put("_modules", getModules(context, component)).put("_widgetType", ((Widget) component).getWidgetType());
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}
