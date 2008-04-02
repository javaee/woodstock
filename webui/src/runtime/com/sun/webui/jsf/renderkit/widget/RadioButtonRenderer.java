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
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.RadioButton;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.RadioButton",
    componentFamily="com.sun.webui.jsf.RadioButton"))
public class RadioButtonRenderer extends RbCbRendererBase {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Decode the <code>RadioButton</code> selection.</p>
     * <p>
     * If the value of the component's <code>name</code> property
     * has been set, the value is used to match a request parameter.
     * If it has not been set the component clientId is used to match
     * a request parameter. If a match is found, and the value of the
     * of the request parameter matches the <code>String</code> value of the
     * component's <code>selectedValue</code> property, the
     * radio button is selected. The component's submitted value is
     * assigned a <code>String[1]</code> array where the single array
     * element is the matching parameter value.
     * </p>
     * <p>
     * If no matching request parameter or value is found, an instance of
     * <code>String[0]</code> is assigned as the submitted value,
     * meaning that this is a component was not selected.
     * </p>
     * <p>
     * If the component was encoded as a boolean control the
     * value of the matching request attribute will be the component's
     * <code>clientId</code> property if selected. If selected the
     * submitted value is <code>new String[] { "true" }</code>
     * and <code>new String[] { "false" }</code> if not selected.
     * </p>
     * <p>
     * It is the developer's responsibility to ensure the validity of the
     * <code>name</code> property (the name attribute on the
     * INPUT element) by ensuring that it is unique to the radio buttons
     * for a given group within a form.
     * </p>
     *
     * @param context FacesContext for the request we are processing.
     * @param component The <code>RadioButton</code>
     * component to be decoded.
     */
    public void decode(FacesContext context, UIComponent component) {
	if (!(component instanceof RadioButton)) {
	    throw new IllegalArgumentException(
                "RadioButtonRenderer can only decode RadioButton components.");
        }
        // We need to know the last state of the component before decoding
        // this radio button. This disabled check is not to determine
        // if the radio button was disabled on the client.
        // We assume that the disabled state is in the same state as it was
        // when this radio button was last rendered.
        // If the radio button was disabled then it can not have changed on
        // the client. We ignore the case that it might have been
        // enabled in javascript on the client.
        // This allows us to distinguish that no radio button was selected.
        // No radio buttons are selected when "isDisabled || isReadOnly -> false
        // and no request parameters match the name attribute if part of a
        // group or the clientId if a single radio button.
        //
        if (ComponentUtilities.isDisabled(component) || ComponentUtilities.isReadOnly(component)) {
            return;
        }
        // If there is a request parameter that that matches the
        // name property, this component is one of the possible
        // selections. We need to match the value of the parameter to the
        // the component's value to see if this is the selected component.
        //
        RadioButton radioButton = (RadioButton) component;
        String name = radioButton.getName();
        boolean inGroup = name != null;
        
        String componentId;
        if (component instanceof ComplexComponent) {
            componentId = ((ComplexComponent)component).getLabeledElementId(context);
        } else {
            componentId = component.getClientId(context);
        }
        
        // If name is null use the clientId.
        //
        if (name == null) {
            name = componentId;
        }
        
        Map requestParameterMap = context.getExternalContext().
                getRequestParameterMap();
        
        // The request parameter map contains the INPUT element
        // name attribute value as a parameter. The value is the
        // the "selectedValue" value of the RadioButton component.
        //
        if (requestParameterMap.containsKey(name)) {
            
            String newValue = (String)requestParameterMap.get(name);
            
            // We need to discern the case where the radio button
            // is part of a group and it is a boolean radio button.
            // If the radio button is part of a group and it is a
            // boolean radio button then the submitted value contains the
            // value of "component.getClientId()". If
            // the value was not a unique value within the group
            // of boolean radio buttons, then all will appear selected,
            // since name will be the same for all the radio buttons
            // and the submitted value would always be "true" and then
            // every radio button component in the group would decode
            // as selected. Due to the HTML implementation of radio
            // buttons, only the last radio button will appear selected.
            //
            Object selectedValue = radioButton.getSelectedValue();
            String selectedValueAsString = null;
            
            if (inGroup && selectedValue instanceof Boolean) {
                selectedValueAsString = componentId;
                // Use the toString value of selectedValue even if
                // it is a Boolean control, in case the application
                // wants "FALSE == FALSE" to mean checked.
                //
                if (selectedValueAsString.equals(newValue)) {
                    ((UIInput)component).setSubmittedValue(
                            new String[] { selectedValue.toString() });
                    return;
                }
            } else {
                selectedValueAsString =
                        ConversionUtilities.convertValueToString(component,
                        selectedValue);
                if (selectedValueAsString.equals(newValue)) {
                    ((UIInput)component).setSubmittedValue(
                            new String[] { newValue });
                    return;
                }
            }
            // Not selected possibly deselected.
            //
            ((UIInput) component).setSubmittedValue(new String[0]);
        }
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
        return "radioButton";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Return true if the <code>component</code> is selected, false
     * otherwise.
     *
     * @param context FacesContext for the request we are processing.
     * @param component UIComponent to test for selected.
     */
    protected boolean isSelected(FacesContext context, UIComponent component) {
        return ((RadioButton)component).isChecked();
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    
}
