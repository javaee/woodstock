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
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ClientSniffer;
import com.sun.webui.jsf.util.WidgetUtilities;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders the Anchor component
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Anchor", 
    componentFamily="com.sun.webui.jsf.Anchor"))
public class AnchorRenderer extends AnchorRendererBase {
    /**
     * Id of the transparent image to be rendered for IE browsers
     */    
    private static String ANCHOR_IMAGE = "_img";

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Add the component id to the json attribute list.
     * This will be used as the client id for the dom node.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json The JSON object
     */
    protected void setAttributes(FacesContext context, UIComponent component, 
            JSONObject json) throws JSONException, IOException {
        super.setAttributes(context, component, json);
        String name = component.getId();
        json.put("name", name);
    }

    /**
     * This  renders a spacer image if there are no children or
     * text provided for the anchor. This is a work around for IE browsers
     * which does not locate the anchor if nothing is specified in the body
     * of the anchor.      
     * @param json The json object
     * @param component The ImageHyperlink component
     * @param context The FacesContext
     */
    protected void setContents(FacesContext context, UIComponent component, 
            JSONObject json)throws JSONException, IOException{
        super.setContents(context, component, json);
        JSONArray jArray = (JSONArray) json.get("contents");

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
                jArray.put(WidgetUtilities.renderComponent(context, icon));   
            }
        }        
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
