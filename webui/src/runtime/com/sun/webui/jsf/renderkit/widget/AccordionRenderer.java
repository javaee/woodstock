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
import com.sun.webui.jsf.component.TabContent;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

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
    
    private static final String attributes[] = {
        "style"};

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

     
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        Accordion container = (Accordion) component;
        
        
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.accordion"));
        if (container.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                "widget.jsfx.accordion"));
        }
        return json;
    }
    
    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        Accordion container = (Accordion) component;
        Theme theme = ThemeUtilities.getTheme(context);
        String templatePath = container.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("className", container.getStyleClass())
            .put("style", container.getStyle())
            .put("multipleSelect", container.isMultipleSelect())
            .put("loadOnSelect", container.isLoadOnSelect())
            .put("toggleControls", container.isToggleControls())
            .put("visible", container.isVisible())
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : theme.getPathToTemplate(ThemeTemplates.ACCORDION));
            
        if (container.isRefreshButton()) {
            WidgetUtilities.addProperties(json, "refreshImage",
                WidgetUtilities.renderComponent(context,
                container.getRefreshIcon(theme, context)));
        }
        
        if (container.getChildCount() > 0) {
            JSONArray accordionTabs = new JSONArray();
            appendChildProps(container, context, accordionTabs);
            json.put("accordionTabs", accordionTabs);
            
            if (container.isToggleControls() && container.isMultipleSelect()) {
                
                // Append expand/collapse image properties.
                WidgetUtilities.addProperties(json, "expandAllImage",
                    WidgetUtilities.renderComponent(context,
                    container.getExpandAllIcon(theme, context)));
                
                WidgetUtilities.addProperties(json, "collapseAllImage",
                    WidgetUtilities.renderComponent(context,
                    container.getCollapseAllIcon(theme, context)));
                
            }
        } // else {
            // should add some error handling here if possible to let the
            // client know that the accordion is empty.
            // json.put("accordionTabs", "Container has no Children");
        // }
        
        // Add core and attribute properties.
        // this is commented out for now. we may support a onChange
        addAttributeProperties(attributes, container, json);
        setCoreProperties(context, container, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("accordion");
    }
   
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
    

    // The assumption is that accordions will only have accordion tabs as
    // children.
    private void appendChildProps(UIComponent component, FacesContext context,
        JSONArray accordionTabs) throws IOException, JSONException {
    
        for (UIComponent kid : component.getChildren()) {
            WidgetUtilities.addProperties(accordionTabs,
                WidgetUtilities.renderComponent(context, kid));
        }
        
    }
}