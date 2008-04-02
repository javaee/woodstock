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

import java.io.IOException;
import java.util.Map;

import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.AccordionTab;
import com.sun.webui.jsf.component.Accordion;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders the AccordionTab component. This renderer sends across
 * properties in the form of name value pairs to the client side. The renderer
 * iterates through its list of children and appends them to the JSON object
 * being sent across to the client. 
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.AccordionTab", 
    componentFamily="com.sun.webui.jsf.AccordionTab"))
public class AccordionTabRenderer extends RendererBase {
    /**
     * The set of int attributes to be rendered.
     */
    private static final String intAttributes[] = {
        "contentHeight"
    };

    /**
     * Decode the AccordionTab component. The basic purpose is to 
     * extract the value of a client side hiddenField to check
     * if the AccordionTab was selected or not and invoke 
     * AccordionTab.setSelected() method accordingly. 
     *
     * @param context The FacesContext associated with this request
     * @param component The AccordionTab component to decode
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        
        String id = component.getClientId(context);
        Map params = context.getExternalContext().getRequestParameterMap();
        
        String hiddenFieldId = id + ":selectedState";
        
        Object valueObject = params.get(hiddenFieldId);
        String value = null;
        if (valueObject != null) {
            value = ((String)valueObject).trim();
            AccordionTab accTab = (AccordionTab) component;
            boolean selectedState = Boolean.parseBoolean(value);
            accTab.setSelected(selectedState);
            
            if (selectedState) {
                // get the parent and invoke its setSelectedTab()
                // method to set the selected state of all the child
                // AccordionTabs of this container. This should only 
                // be done if multipleSelect is set to false.
                UIComponent parent = accTab.getParent();
                if (parent instanceof AccordionTab) {
                    AccordionTab pTab = (AccordionTab)parent;
                    if (!pTab.isMultipleSelect()) {
                        pTab.setSelectedTab(accTab);
                    }
                } else if (parent instanceof Accordion) {
                    Accordion pAcc = (Accordion)parent;
                    if (!pAcc.isMultipleSelect()) {
                        pAcc.setSelectedTab(accTab);
                    }
                }
            }
        }
    }
    
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
            UIComponent component) throws IOException, JSONException {
        AccordionTab content = (AccordionTab) component;

        JSONObject json = new JSONObject();
        json.put("className", content.getStyleClass())
            .put("style", content.getStyle())
            .put("selected", content.isSelected())
            .put("visible", content.isVisible())
            .put("focusId", content.getFocusId())
            .put("title", content.getTitle());            

        JSONArray tabContent = new JSONArray();
        appendChildProps(content, context, tabContent);
        json.put("tabContent", tabContent);
        JSONUtilities.addIntegerProperties(intAttributes, component, json);

        return json;
    }

    /**
     * Get the type of the widget represented by this component.
     *
     * @return The name of widget represented by this component. The
     * "accordionTab" in this case.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "accordionTab";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Render the children of the AccordionTab to the JSON array.
     * Each AccordionTab should either have only AccordionTabs
     * as children or be a "child" AccordionTab with other components 
     * as its contents. All AccordionTabs would have a hiddenField
     * as it child - this field stores the "selected" state of the accordionTab.
     * The JSON object for this hiddenField is not rendered here. 
     */
    private void appendChildProps(AccordionTab component, FacesContext context,
            JSONArray jArray) throws IOException, JSONException{
    
        if (component.getTabChildCount() == 0) {
            // exclude the hiddenField used to maintain the selected state
            // as this field is being set on the clien side separately. We
            // do not want to make this part of the application's tabContent
            // payload.
            for (UIComponent kid : component.getChildren()) {
                if (!kid.getId().equals("selectedState")) {
                    jArray.put(WidgetUtilities.renderComponent(context, kid));
                }
            } 
        } else {
            for (UIComponent kid : component.getChildren()) {
                if (kid instanceof AccordionTab) {
                    appendChildProps((AccordionTab) kid, context, jArray);
                }
            }
        }
    }
}
