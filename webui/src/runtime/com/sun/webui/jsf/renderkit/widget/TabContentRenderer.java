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

import com.sun.webui.jsf.component.TabContent;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

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
     * Get the Dojo module required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getModule(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getModuleName("widget.accordionTab");
    }

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
	if (!(component instanceof TabContent)) {
	    throw new IllegalArgumentException(
                "TabContentRenderer can only render TabContent components.");
        }
        TabContent content = (TabContent) component;

        JSONObject json = new JSONObject();
        json.put("labelClass", content.getLabelClass())
            .put("labelStyle", content.getLabelStyle())
            .put("contentClass", content.getContentClass())
            .put("contentStyle", content.getContentStyle())
            .put("selected", content.isSelected())
            .put("visible", content.isVisible())
            .put("label", content.getLabel())
            .put("contentHeight", content.getContentHeight()); 

        JSONArray jArray = new JSONArray();
        json.put("tabContent", jArray);        
        appendChildProps(content, context, jArray);

        // Add core and attribute properties.
        setCoreProperties(context, content, json);

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
            : getTheme().getPathToTemplate(ThemeTemplates.ACCORDIONTAB);
    }

    /**
     * Get the name of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetName(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("accordionTab");
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain tab children.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONArray to assign properties to.
     */
    private void appendChildProps(TabContent component, FacesContext context,
        JSONArray content) throws IOException, JSONException{
    
        if (component.getTabChildCount() == 0) {
            for (UIComponent kid : component.getChildren()) {
                JSONUtilities.addProperty(content,
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
