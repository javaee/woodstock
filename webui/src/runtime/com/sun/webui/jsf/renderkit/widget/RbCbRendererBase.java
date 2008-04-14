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

import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.RbCbSelector;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base class for rendering RadioButton and Checkbox components.
 */
abstract class RbCbRendererBase extends RendererBase {  
    /**
     * The set of pass-through attributes to be rendered for this component.
     */
    private static final String stringAttributes[] = {
        "alt",
        "lang",
        "dir",
        "align",
        "accessKey",
        "style",
        "onFocus",
        "onBlur",
        "onClick",
        "onDblClick",
        "onChange",
        "onMouseDown",
        "onMouseUp",
        "onMouseOver",
        "onMouseMove",
        "onMouseOut",
        "onKeyPress",
        "onKeyDown",
        "onKeyUp",
        "onSelect"
    };
    
    /**
     * The set of int attributes to be rendered.
     */
    private static final String intAttributes[] = {
        "tabIndex"
    };
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>
     * Attempt to convert previously stored state information into an
     * object of the type required for this component (optionally using the
     * registered {@link javax.faces.convert.Converter} for this component,
     * if there is one).  If conversion is successful, the new value
     * is returned and if not, a
     * {@link javax.faces.convert.ConverterException} is thrown.
     * </p>
     * 
     * @param context {@link FacesContext} for the request we are processing
     * @param component component being renderer.
     * @param submittedValue a value stored on the component during <code>decode</code>.
     * 
     * @exception ConverterException if the submitted value
     *   cannot be converted successfully.
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is <code>null</code>
     */
    public Object getConvertedValue(FacesContext context, UIComponent component,
            Object submittedValue) throws ConverterException {

	// I know this looks odd but it gives an opportunity
	// for an alternative renderer for Checkbox and RadioButton
	// to provide a converter.
	//
	return ((RbCbSelector) component).getConvertedValue(context,
            (RbCbSelector) component, submittedValue);
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
        RbCbSelector rbcbSelector = (RbCbSelector) component;        
        String componentId;      
        
        // Get HTML input element id.
        if (component instanceof ComplexComponent) {
            componentId = ((ComplexComponent) component).getLabeledElementId(context);
        } else {
            componentId = component.getClientId(context);
        }
	
        // If name is not set, use HTML input element's id.
	boolean inGroup = true;
	String name = rbcbSelector.getName();
	if (name == null) {
	    name = componentId;
	    inGroup = false;
	}
        
        JSONObject json = new JSONObject();                
        json.put("name", name)
            .put("readOnly", rbcbSelector.isReadOnly())
            .put("disabled", rbcbSelector.isDisabled())	               
	    .put("title", rbcbSelector.getToolTip())
            .put("visible", rbcbSelector.isVisible())
            .put("className", rbcbSelector.getStyleClass());                    	
        
        // If not ingroup, "value" has String version of selectedValue.
	//
	Object selectedValue = rbcbSelector.getSelectedValue();
	String value = ConversionUtilities.convertValueToString(component,
            selectedValue);

	// Need to check immediate conditions
	// submittedValue will be non null if immediate is true on
	// some action component or a component on the page was invalid
	//
	String[] subValue = (String[]) rbcbSelector.getSubmittedValue();
        if (subValue == null) {
            Object selected = rbcbSelector.getSelected();
            if (isSelected(context, component)) {
                json.put("checked", true);
            }
            // A component can't be selected if "getSelected" returns null
            //
            // Remember that the rendered value was null.
            //
            ConversionUtilities.setRenderedValue(component, selected);
        } else if (subValue.length != 0 && subValue[0].length() != 0) {
            // if the submittedValue is a 0 length array or the
            // first element is "" then the control is unchecked.
            //
            // The submitted value has the String value of the
            // selectedValue property. Just compare the submittedValue
            // to it to determine if it is checked.
            //
            // Assume that the RENDERED_VALUE_STATE is the same
            // as the last rendering.
            //
            if (value != null && value.equals(subValue[0])) {
                json.put("checked", true);
            }
        }
        
        // If the selectedValue is Boolean and the component is part
	// of a group, "name != null", then set the value of the value
	// attribute to HTML input element's id.
	// 
	boolean booleanControl = selectedValue instanceof Boolean;
	if (inGroup && booleanControl) {
	    value = componentId;
	}        
        json.put("value", value);                       
        
        // Append image properties.
        json.put("image", WidgetUtilities.renderComponent(context, 
            rbcbSelector.getImageComponent()));
        
	JSONObject jlabel = getLabel(context, rbcbSelector);
	if (jlabel != null) {
	    // Append label properties.
	    json.put("label", jlabel);
	}
        
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        JSONUtilities.addIntegerProperties(intAttributes, component, json);
        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Implemented in the subclass to determine if the <code>item</code>
     * is the currently selected control.
     *
     * @param Object selectedValue contol value.
     * @param currentValue the value of the currently selected control.
     */
    protected abstract boolean isSelected(FacesContext context,
	UIComponent component);
    
    /**
     * Helper method to return a <code>JSONObject</code> of label properties.
     * If a <code>RbCbSelector.LABEL_FACET</code> exists return the
     * <code>JSONObject</code> representing that facet. If there is no facet
     * and <code>rbcb.getLabel()</code> returns non null, return a
     * <code>JSONObject</code> containing <code>value</code>
     * and <code>level</code> properties.
     * If there is no label return <code>null</code>.
     * 
     * @param context FacesContext for the current request.
     * @param rbcb RbCbSelector component to be rendered.
     */
    protected JSONObject getLabel(FacesContext context, 
            RbCbSelector rbcb) throws IOException, JSONException {
        
        if (rbcb == null) {
            throw new RuntimeException("rbcb must not be null"); //NOI18N
        }

        UIComponent labelFacet = rbcb.getFacet(rbcb.LABEL_FACET);
        if (labelFacet != null) {
	    return WidgetUtilities.renderComponent(context, labelFacet);
        } else {
	    // "" is a valid label.
	    //
	    String lvalue = rbcb.getLabel();
            if (lvalue != null) {
		JSONObject json = new JSONObject();
		// allow client-side to render widget by providing
		// required values to it
		//
		json.put("value", lvalue);
		json.put("level", rbcb.getLabelLevel());
		return json;
	    }
        }                
        return null;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
