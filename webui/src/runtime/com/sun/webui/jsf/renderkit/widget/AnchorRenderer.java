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

import com.sun.webui.jsf.component.Anchor;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.util.ClientSniffer;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeJavascript;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeTemplates;

import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.faces.annotation.Renderer;

/**
 * This class renders the Anchor component
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Anchor", 
    componentFamily="com.sun.webui.jsf.Anchor"))
public class AnchorRenderer extends RendererBase{
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "accessKey",
        "charset",
        "coords",
        "onBlur",
        "onClick",
        "onDblClick",
        "onFocus",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "rel",
        "rev",
        "shape",
        "style",
        "target",
        "type"
    };
    
     /**
      * Id of the transparent image to be rendered for IE browsers
      */    
     private static String ANCHOR_IMAGE = "_img";   //NOI18N    
        
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
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.anchor"));
        boolean ajaxify = ((Boolean)
            component.getAttributes().get("ajaxify")).booleanValue();        
        if (ajaxify == true) {
            json.put(JavaScriptUtilities.getModuleName(
                "widget.jsfx.anchor"));
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
            UIComponent component) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        setAttributes(context, component, json);
        setHTMLTemplate(context, component, json);
        setContents(context, component, json);
        return json;
    }
    
    /**
     * Set the html template to be used by the widget renderer. Override 
     * this method if different templates are to be used.
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json The JSON object
     */
    protected void setHTMLTemplate (FacesContext context, UIComponent component,
            JSONObject json) throws JSONException, IOException {
        Theme theme = ThemeUtilities.getTheme(context);        
        String templatePath = (String) component.getAttributes().get("templatePath");
        json.put("templatePath",
            (templatePath != null && templatePath.length() > 0)
                ? templatePath 
                : theme.getPathToTemplate(ThemeTemplates.ANCHOR));        
    }
    
    /**
     * Set the attributes of the anchor element.
     * Override this method if custom properties need to be set for the 
     * component.
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json The JSON object
     */
    protected void setAttributes(FacesContext context, UIComponent component, 
            JSONObject json) throws JSONException, IOException {
        Map attrsMap = component.getAttributes();

        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);

        String tmp = null;
        tmp = (String) attrsMap.get("style");
        if (tmp != null && tmp.length() > 0) {
            json.put("style", tmp);
        }         
        tmp = (String) attrsMap.get("styleClass");
        if (tmp != null && tmp.length() > 0) {
            json.put("className", tmp);
        }
        tmp = (String) attrsMap.get("toolTip");
        if (tmp != null && tmp.length() > 0) {
            json.put("title", tmp);
        }
        tmp = (String) attrsMap.get("urlLang");
        if (tmp != null && tmp.length() > 0) {
            json.put("hrefLang", tmp);
        }
        tmp = (String) attrsMap.get("name");
        if (tmp != null && tmp.length() > 0) { 
            json.put("name", tmp);
        } else {
          json.put("name", component.getId());
        }

        json.put("disabled",((Boolean) attrsMap.get("disabled")).booleanValue());        
        json.put("visible", ((Boolean) attrsMap.get("visible")).booleanValue());

        String url = (String) attrsMap.get("url");
        setURL(context, component, json, url);

        int tabIndex = ((Integer) attrsMap.get("tabIndex")).intValue();  
        if (!(tabIndex == Integer.MIN_VALUE)) {
            json.put("tabIndex", tabIndex);
        }         
    }
    
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("anchor");    
    }
    
    /**
     * Assign the url property.
     * Override this method if the url is to be set in a different way.
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json The JSON object
     * @param url The url string to be output.
     */
    protected void setURL(FacesContext context, UIComponent component,
            JSONObject json, String url) throws JSONException {
        if (url != null && url.length() > 0) {
            if (!(url.startsWith("#"))) { //NOI18N
                url = context.getApplication().getViewHandler().getResourceURL(
                    context, url);
                url = WidgetUtilities.translateURL(context, component, url); //NOI18N                      
            }
            json.put("href", url);
        } 
    }
    
    /**
     * Traverse through the children and facet components and add them to 
     * the json array as children. Note that in this case, the text
     * property of the component is also added to the contents property of
     * the widget.The UIParameters are not added as contents over here.
     * Instead they are used while generating the url attribute. The name
     * value pairs found in the UIParameter component are appended as request
     * parameters in the url attribute.
     * This also renders a spacer image if there are no children or
     * text provided for the anchor. This is a work around for IE browsers
     * which does not locate the anchor if nothing is specified in the body
     * of the anchor.      
     * @param json The json object
     * @param component The ImageHyperlink component
     * @param context The FacesContext
     */
    protected void setContents(FacesContext context, UIComponent component, 
            JSONObject json)throws JSONException, IOException{
        JSONArray children = new JSONArray();
        json.put("contents", children);

        String text = (String)component.getAttributes().get("text");
        if (text != null && text.length() > 0) {
            text = ConversionUtilities.convertValueToString(component, text);
        }

        WidgetUtilities.addProperties(children, text);
        Iterator it = component.getChildren().iterator();
        while (it.hasNext()) {
            UIComponent child = (UIComponent) it.next();
            if (!(child instanceof UIParameter)) {
                WidgetUtilities.addProperties(children,
                    WidgetUtilities.renderComponent(context, child));   
            }
        }

        // Fix for IE. If no text or image is specified, render a placeholder image
        // so that the browser can identify the anchor element.
        if (component.getChildCount() == 0 || 
            component.getAttributes().get("text") == null) {        
            ClientSniffer sniffer = ClientSniffer.getInstance(context);
            if (sniffer.isIe6up() || sniffer.isIe7() || sniffer.isIe7up()) {                      
                Icon icon = new Icon();
                icon.setParent(component);
                icon.setIcon(ThemeImages.DOT);    
                icon.setId(component.getId() + ANCHOR_IMAGE);
                WidgetUtilities.addProperties(children,
                    WidgetUtilities.renderComponent(context, icon));   
            }
        }        
     }                    
}
