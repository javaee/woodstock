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

import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.Accordion;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders the Accordion component. This renderer sends across
 * properties in the form of name value pairs to the client side. On the client 
 * side an accordion "widget" is in place - the widget reads these properties
 * and manipulates the DOM nodes that actually causes the accordion
 * to be rendered. In essence, the markup is generated on the client side and 
 * not pushed across from the server.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Accordion", 
    componentFamily="com.sun.webui.jsf.Accordion"))
public class AccordionRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "style"};

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getModule(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getModuleName("widget.accordion");
    }
    
    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
	if (!(component instanceof Accordion)) {
	    throw new IllegalArgumentException(
                "AccordionRenderer can only render Accordion components.");
        }
        Accordion container = (Accordion) component;
        Theme theme = getTheme();

        boolean isMultipleSelect = container.isMultipleSelect();
        JSONObject json = new JSONObject();
        json.put("className", container.getStyleClass())
            .put("style", container.getStyle())
            .put("multipleSelect", isMultipleSelect)
            .put("loadOnSelect", container.isLoadOnSelect())
            .put("toggleControls", container.isToggleControls())
            .put("visible", container.isVisible());
            
        if (container.isRefreshButton()) {
            json.put("refreshImage", WidgetUtilities.renderComponent(context,
                container.getRefreshIcon(theme, context)));
        }
        
        if (container.getChildCount() > 0) {
            setContents(context, container, json);
            if (container.isToggleControls() && isMultipleSelect) {
                // Append expand/collapse image properties.
                json.put("expandAllImage", 
                    WidgetUtilities.renderComponent(context, 
                        container.getExpandAllIcon(theme, context)));
                json.put("collapseAllImage",
                    WidgetUtilities.renderComponent(context,
                        container.getCollapseAllIcon(theme, context)));                
            }
        }
        
        // Add attributes.
        JSONUtilities.addProperties(attributes, container, json);

        return json;
    }

    /**
     * Get the template path for this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getTemplatePath(FacesContext context, UIComponent component) {
        String templatePath = (String) component.getAttributes().get("templatePath");
        return (templatePath != null)
            ? templatePath 
            : getTheme().getPathToTemplate(ThemeTemplates.ACCORDION);
    }

    /**
     * Get the name of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @return The type of widget represented by this component. The
     *   "accordion" in this case.
     */
    protected String getWidgetName(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("accordion");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain tab children.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setContents(FacesContext context,
        UIComponent component, JSONObject json) 
	    throws IOException, JSONException {

        JSONArray jArray = new JSONArray();
        json.put("tabs", jArray);

        for (UIComponent kid : component.getChildren()) {
            jArray.put(WidgetUtilities.renderComponent(context, kid));
        }
    }
}
