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

import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is used as a base class for renderers that render an html
 * anchor element. This outputs  JSON properties that would render an anchor
 * widget on the client side. Sub classes can override methods present in this
 * class to achieve customizable functionality.
 */
public abstract class AnchorRendererBase extends RendererBase{
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {
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
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   
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
        setContents(context, component, json);
        return json;
    } 

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "anchor";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Set the attributes of the widget element.
     * Override this method if custom properties need to be set for the 
     * component.
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json The JSON object
     */
    protected void setAttributes(FacesContext context, UIComponent component, 
            JSONObject json) throws JSONException, IOException {
        Map attrsMap = component.getAttributes();

        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);

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
     * Traverse through the children and facet components and add them to 
     * the json array as children. Note that in this case, the text
     * property of the component is also added to the contents property of
     * the widget.The UIParameters are not added as contents over here.
     * Instead they are used while generating the url attribute. The name
     * value pairs found in the UIParameter component are appended as request
     * parameters in the url attribute.      
     * @param json The json object
     * @param component The ImageHyperlink component
     * @param context The FacesContext
     */
    protected void setContents(FacesContext context, UIComponent component, 
            JSONObject json)throws JSONException, IOException{
        JSONArray jArray = new JSONArray();
        json.put("contents", jArray);
        String text = ConversionUtilities.convertValueToString(component, 
                 component.getAttributes().get("text"));
        
        if (text.length() > 0) {
            jArray.put(text);
        }
        Iterator it = component.getChildren().iterator();
        while (it.hasNext()) {
            UIComponent child = (UIComponent) it.next();
            if (!(child instanceof UIParameter)) {
                jArray.put(WidgetUtilities.renderComponent(context, child));   
            }
        }    
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
                url = WidgetUtilities.translateURL(context, component, url); //NOI18N                      
            }
            json.put("prefix", context.getExternalContext().getRequestContextPath())
                .put("href", url);
        } 
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
