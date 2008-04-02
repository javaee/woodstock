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
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Checkbox component.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Checkbox",
    componentFamily="com.sun.webui.jsf.Checkbox"))
public class CheckboxRenderer extends RbCbRendererBase {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * <p>Decode the <code>Checkbox</code> selection.</p>
     *
     * @param context FacesContext for the request we are processing.
     * @param component The <code>Checkbox</code> component to be decoded.
     */
    public void decode(FacesContext context, UIComponent component) {
	if (!(component instanceof Checkbox)) {
	    throw new IllegalArgumentException(
                "CheckboxRenderer can only decode Checkbox components.");
        }
        // We need to know if the last state of the component before decoding
	// this checkbox. This disabled check is not to determine
	// if the checkbox was disabled on the client.
	// We assume that the disabled state is in the same state as it was
	// when this checkbox was last rendered.
	// If the checkbox was disabled then it can not have changed on
	// the client. We ignore the case that it might have been
	// enabled in javascript on the client.
	// This allows us to distinguish that no checkbox was selected.
	// No checkboxes are selected when "isDisabled || isReadOnly -> false
	// and no request parameters match the name attribute if part of a
	// group or the clientId, if a single checkbox.
	//
        if (ComponentUtilities.isDisabled(component) || 
                ComponentUtilities.isReadOnly(component)) {
            return;
        }
        
        // If there is a request parameter that matches the
        // name property, this component is one of the possible
        // selections. We need to match the value of the parameter to
        // the component's value to see if this is the selected component,
        // unless it is a group of Boolean checkboxes.
        //
        Checkbox checkbox = (Checkbox) component;
        String name = checkbox.getName();
        boolean inGroup = name != null;
        
        // Get client id.
        String componentId = component.getClientId(context);
        if (component instanceof ComplexComponent) {
            componentId = ((ComplexComponent)component).getLabeledElementId(context);
        }
        
        // If name not set look for clientId.
        // Boolean checkboxes decode correctly when they are not
        // in a group, since the submitted attribute
        // value in the clientId and is unique for each check box.
        //
        if (name == null) {
            name = componentId;
        }
        
        Map requestParameterValuesMap = context.getExternalContext().
                getRequestParameterValuesMap();
        
        // If a parameter with key == name does not exist, the component
        // was not submitted. This only means that it is
        // unchecked, since we already know that is it not in the
        // map because is was readonly or disabled. (based on the
        // server side state)
        //
        if (requestParameterValuesMap.containsKey(name)) {
            
            String[] newValues = (String[])
            requestParameterValuesMap.get(name);
            
            if (newValues != null || newValues.length != 0) {
                
                String selectedValueAsString = null;
                Object selectedValue = checkbox.getSelectedValue();
                
                // We need to discern the case where the checkbox
                // is part of a group and it is a boolean checkbox.
                // If the checkbox is part of a group and it is a
                // boolean checkbox then the submitted value contains the
                // value of the HTML input element's id. If
                // the value was not a unique value within the group
                // of boolean checkboxes, then all will appear selected,
                // since name will be the same for all the checkboxes
                // and the submitted value would always be "true" and then
                // every checkbox component in the group would decode
                // as selected.
                //
                if (inGroup && selectedValue instanceof Boolean) {
                    selectedValueAsString = componentId;
                    // See if one of the values of the attribute
                    // is equal to the component id.
                    //
                    // Use the toString value of selectedValue even if
                    // Boolean in case it is FALSE and the application
                    // wants checked to be "FALSE == FALSE"
                    //
                    for (int i = 0; i < newValues.length; ++i) {
                        if (selectedValueAsString.equals(newValues[i])) {
                            ((UIInput)component).setSubmittedValue(
                                    new String[] { selectedValue.toString() });
                            
                            return;
                        }
                    }
                } else {
                    selectedValueAsString =
                            ConversionUtilities.convertValueToString(
                            component, selectedValue);
                    
                    for (int i = 0; i < newValues.length; ++i) {
                        if (selectedValueAsString.equals(newValues[i])) {
                            ((UIInput)component).setSubmittedValue(
                                    new String[] { newValues[i] });
                            
                            return;
                        }
                    }
                }
                // Not selected.
                // But this results in an update to the model object
                // of every checkbox, even if the value is the same.
                // However only those that experience a state change issue
                // a ValueChangeEvent.
                //
                ((UIInput)component).setSubmittedValue(new String[0]);
                return;
            }
        }
        // Not disabled and this checkbox is not selected.
        //
        ((UIInput)component).setSubmittedValue(new String[0]);
        
        return;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "checkbox";
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RbCbRendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Return true if the <code>component</code> is selected, false
     * otherwise.
     *
     * @param context FacesContext for the request we are processing.
     * @param component UIComponent to test for selected
     */
    protected boolean isSelected(FacesContext context, UIComponent component) {
        return ((Checkbox)component).isChecked();
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
