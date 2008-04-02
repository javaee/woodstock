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
import com.sun.webui.jsf.component.Menu;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.event.ValueEvent;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionGroup;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders an instance of the Menu component.
 * It traverses through the <code>com.sun.webui.jsf.mode.Option</code>
 * array got from <code>com.sun.webui.jsf.component.Menu</code>
 * component and puts it in a JSON object. 
 * 
 * The decode method takes a look at the request parameter map for a particular
 * name value pair. If it exists, then that value is set as the component's
 * value. Note that a separate decode method exists for ajax requests.
 * This can be foind in the <code>com.sun.webui.jsf.renderkit.ajax.MenuRenderer</code>
 * class.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Menu", 
    componentFamily="com.sun.webui.jsf.Menu"))
public class MenuRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    protected static final String[] stringAttributes = {
        "onChange",
        "onClick",
        "onDblClick", 
        "onMouseDown",
        "onMouseUp",
        "onMouseOver",
        "onMouseMove",
        "onMouseOut",
        "onKeyPress",
        "onChange",
        "onKeyDown",
        "onKeyUp",
        "style", 
        "dir",
        "lang"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This method looks for a particular name value pair in the request 
     * parameter map. The name has to be the client id of the component appended
     * with the suffix "_submittedValue" and the value is the selected option
     * of the  menu. If such a name value pair exists, then the component's
     * submitted value is set to the value present in the name-value pair.
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null) {
            throw new NullPointerException();
        }
	if (!(component instanceof Menu)) {
	    throw new IllegalArgumentException(
                "MenuRenderer can only render Menu components.");
        }
        Menu menu = (Menu) component;

        // If this  menu has been clicked, find out which value has been clicked.
        String paramId = component.getClientId(context) + "_submittedValue";    
        String value = (String) 
            context.getExternalContext().getRequestParameterMap().get(paramId);   
        if (value == null) {
            return;
        }
        if (checkSubmittedOption(value, menu.getOptionsArray())) {
            ValueEvent me = new ValueEvent(component);
            me.setSelectedOption(value);
            menu.queueEvent(me);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
   
    /** 
     * Helper method to obtain component properties.
     * Get the list of options that the user has specified. Traverse
     * through the list and add them to the JSON property list. This function
     * invokes the <code> getOptionProperties</code> for achieving this.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */    
    protected JSONObject getProperties(FacesContext context,     
            UIComponent component) throws JSONException, IOException {
	if (!(component instanceof Menu)) {
	    throw new IllegalArgumentException(
                "MenuRenderer can only render Menu components.");
        }  
        Menu menu = (Menu) component;
        JSONObject json = new JSONObject();      

        // The form id is required if the  menu is to be submitted.
        UIComponent form = Util.getForm(context, component);
        if (form != null) {
            String formClientId = form.getClientId(context);
            json.put("formId", formClientId);
        }        

        JSONArray optionsArray = WidgetUtilities.getOptions(context, component, 
            menu.getOptionsArray());        
        json.put("options", optionsArray)
            .put("visible", menu.isVisible())
            .put("submitForm", menu.isSubmitForm());

        // Add core and pass-through attribute properties.
        JSONUtilities.addStringProperties(stringAttributes, menu, json);

        return json;
    }               

    /**
     * Get the type of widget represented by this component.
     * This method returns "popupMenu" as the widget type.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "popupMenu";
    }        
    
    /**
     * Checks whether the submitted value is present in the list of options
     * that have been given as input
     * @param value The submitted value
     * @param optionArray The array of options given as input
     */
    protected boolean checkSubmittedOption(String value, Option[] optionArray) {
        boolean optionMatch = false;
        for (int i=0; i<optionArray.length; i++) {
             if (optionArray[i] instanceof OptionGroup) {
                 optionMatch = checkSubmittedOption(value, ((OptionGroup)optionArray[i]).getOptions());
                 if (optionMatch) {
                   return optionMatch;
                 }
             }
             if (optionArray[i].getValue().equals(value)) {
                return true;
             }
        }
        return false;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
