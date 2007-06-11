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

import com.sun.webui.jsf.component.TabContainer;
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
 * This class renders the TabContent component. This renderer sends across
 * properties in the form of name value pairs to the client side. On the client 
 * side a javascript "widget" is in place - the widget reads these properties
 * and manipulates the DOM nodes that actually causes the tab and its contents
 * to be rendered. In essence, the markup is generated on the client side and 
 * not pushed across from the server.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.TabContent", 
    componentFamily="com.sun.webui.jsf.TabContent"))
public class TabContentRenderer extends RendererBase {
   

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
        TabContent content = (TabContent) component;

        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.accordionTab"));
        if (content.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                "widget.jsfx.accordionTab"));
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
        TabContent content = (TabContent) component;
        String templatePath = content.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("labelClass", content.getLabelClass())
            .put("labelStyle", content.getLabelStyle())
            .put("contentClass", content.getContentClass())
            .put("contentStyle", content.getContentStyle())
            .put("selected", content.isSelected())
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : ThemeUtilities.getTheme(context).getPathToTemplate(ThemeTemplates.ACCORDIONTAB))
            .put("visible", content.isVisible())
            .put("label", content.getLabel())
            .put("contentHeight", content.getContentHeight());

        JSONArray tabContent = new JSONArray();
        appendChildProps(content, context, tabContent);
        json.put("tabContent", tabContent);        
        
        // Add core and attribute properties.
        // addAttributeProperties(attributes, component, json);
        setCoreProperties(context, content, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("accordionTab");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    private void appendChildProps(TabContent component, FacesContext context,
        JSONArray content) throws IOException, JSONException{
    
        if (component.getTabChildCount() == 0) {
            for (UIComponent kid : component.getChildren()) {
                WidgetUtilities.addProperties(content,
                    WidgetUtilities.renderComponent(context, kid));
            }
                    
        } else {
            for (UIComponent kid : component.getChildren()) {
                if (kid instanceof TabContent) {
                    appendChildProps((TabContent)kid, context, content);
                }
            }
        }
    }
}