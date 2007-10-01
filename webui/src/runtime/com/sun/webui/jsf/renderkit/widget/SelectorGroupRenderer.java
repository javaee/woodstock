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
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.RadioButton;
import com.sun.webui.jsf.component.RbCbSelector;
import com.sun.webui.jsf.component.Selector;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;

import java.io.IOException;
import java.util.Map;
import java.util.Collection;

import javax.faces.component.UIInput;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The SelectorGroupRenderer is the base class for
 * radio button group and checkbox group renderers.  
 *
 */
abstract class SelectorGroupRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        //not style!

        //alt
        //align
        
        "accessKey",
        "dir",
        "lang",
        "tabIndex",
        "title",
        "onBlur",
        "onClick",
        "onChange",
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
        "onSelect"
    };
    
    /**
     * Return a RadioButton or Checkbox component to render.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>RadioButtonGroup</code> or 
     * <code>CheckboxGroup</code> component rendered     
     * @param option the <code>Option</code> being rendered.
     */
    protected UIComponent getSelectorComponent(FacesContext context,
	UIComponent component, String id, Option option) {

	Selector rbcbGrp = (Selector)component;        
        String componentId = rbcbGrp.getClientId(context);
        if (rbcbGrp instanceof ComplexComponent) {
            componentId = ((ComplexComponent) rbcbGrp).getLabeledElementId(context);
        }
        
	RbCbSelector rbcb = createSelectorComponent();
	rbcb.setId(id);
	rbcb.setParent(component);

	rbcb.setName(componentId);        
	rbcb.setToolTip(option.getTooltip());
	rbcb.setImageURL(option.getImage());
	rbcb.setSelectedValue(option.getValue());
	rbcb.setLabel(option.getLabel());
	rbcb.setDisabled(rbcbGrp.isDisabled());
	rbcb.setReadOnly(rbcbGrp.isReadOnly());
        
        String[] names = SelectorGroupRenderer.attributes;
        
        Map attributes = component.getAttributes();
        Map rbAttributes = rbcb.getAttributes();
        for (int i = 0; i < names.length; i++) {
            Object value = attributes.get(names[i]);
            if (value == null) {
                continue;
            }
            else if (value instanceof Integer) {
                if (((Integer) value).intValue() == Integer.MIN_VALUE) {
                    continue;
                }
            }
            rbAttributes.put(names[i], value);
        }
        
        // Default to not selected
        //
        rbcb.setSelected(null);
        
        // Need to check the submittedValue for immediate condition
        //
        String[] subValue = (String[])rbcbGrp.getSubmittedValue();
        if (subValue == null) {
            if (isSelected(option, rbcbGrp.getSelected())) {
                rbcb.setSelected(rbcb.getSelectedValue());
            }
        } else if (subValue.length != 0) {
            Object selectedValue = rbcb.getSelectedValue();
            String selectedValueAsString =
                ConversionUtilities.convertValueToString(component,
                    selectedValue);
            for (int i = 0; i < subValue.length; ++i) {
                if (subValue[i] != null
                        && subValue[i].equals(selectedValueAsString)) {
                    rbcb.setSelected(selectedValue);
                    break;
                }
            }
        }
        return rbcb;
    }
    
    /**
     * Instantiate and return a <code>RadioButton</code> or 
     * <code>Checkbox</code> component.
     */
    protected abstract RbCbSelector createSelectorComponent();
    
    /**
     * Return true if the <code>item</item> argument is the currently
     * selected radio button/checkbox. Equality is determined by the 
     * <code>equals</code> method of the object instance stored as the 
     * <code>value</code> of <code>item</code>. Return false otherwise.
     *
     * @param item the current radio button/checkbox being rendered.
     * @param currentValue the value of the current selected 
     * radio button/checkbox.
     */
    protected abstract boolean isSelected(Option item, Object currentValue);


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Decode the <code>RadioButtonGroup</code> or
     * <code>CheckboxGroup</code> selection.
     * If the component clientId is found as a request parameter, which is
     * rendered as the value of the <code>name</code> attribute of
     * the INPUT elements of type radio or checkbox, the <code>String[]</code>
     * value is assigned as the submitted value on the component.
     * <p>
     * In the case of a <code>CheckboxGroup</code> component the array may
     * have zero or more elements. In the case of <code>RadioButtonGroup</code>
     * there is always only one element.
     * </p>
     * <p>
     * If the component clientId is not found as a request parameter a
     * <code>String[0]</code> is assigned as the submitted value,
     * meaning that this is a <code>CheckboxGroup</code> component with no
     * check boxes selected.
     *
     * @param context FacesContext for the request we are processing.
     * @param component The RadioButtonGroup component to be decoded.
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (ComponentUtilities.isDisabled(component)|| ComponentUtilities.isReadOnly(component)) {
            return;
        }
        setSubmittedValues(context, component);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to obtain component properties
     * 
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            Selector component) throws IOException, JSONException {
        JSONObject json = new JSONObject();
        
        // Append label properties. 
        json.put("label", WidgetUtilities.renderComponent(context, 
            getLabelComponent(context, component)));
        
        // Set StyleClass
        json.put("className", component.getStyleClass());
        json.put("disabled", component.isDisabled());
        json.put("multiple", component.isMultiple());
        json.put("visible", component.isVisible());
        json.put("style", component.getStyle());
        
        Selector selector = (Selector)component;
        Object selected = selector.getSelected();
        
        // If the submittedValue is null record the rendered value
        // If the submittedValue is not null then it contains the
        // String values of the selected controls.
        // If the submittedValue is not null but zero in length
        // then nothing is selected. Assume that the component still
        // has the appropriate rendered state.
        //
        if (selector.getSubmittedValue() == null) {
            ConversionUtilities.setRenderedValue(component, selected);
        }
        
        // No other attributes to add, so no need to call JSONUtilities.addProperties

        setContents(context, component, json);

        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to obtain group children.
     * 
     * @param context FacesContext for the current request.
     * @param component RadioButtonGroup/CheckboxGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setContents(FacesContext context, UIComponent component,
            JSONObject json) throws IOException, JSONException {
        
        JSONArray jArray = new JSONArray();
        json.put("contents", jArray);
        
        Option[] items = getItems((Selector) component);
        int length = items.length;
        int itemN = 0;

        for (int i = 0; i < length; i++) {
            UIComponent child = getChildComponent(context, component, itemN);
            jArray.put(WidgetUtilities.renderComponent(context, child));
            ++itemN;            
        }          
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private UIComponent getChildComponent(FacesContext context,
            UIComponent component,
            int itemN) throws IOException {
        Option[] items = getItems((Selector)component);
        if (itemN >= items.length) {
            return null;
        }
        
        String id = component.getId().concat("_") + itemN; //NOI18N
        UIComponent child = getSelectorComponent(context, component,
                id, items[itemN]);
        return child;
    }

    // Should be in component
    //
    private Option[] getItems(Selector selector) {
        Object items = selector.getItems();
        if (items == null) {
            return null;
        } else
            if (items instanceof Option[]) {
            return (Option[])items;
            } else
                if (items instanceof Map) {
            int size = ((Map)items).size();
            return (Option[])((Map)items).values().toArray(new Option[size]);
                } else
                    if (items instanceof Collection) {
            int size = ((Collection)items).size();
            return (Option[])((Collection)items).toArray(new Option[size]);
                    } else {
            throw new IllegalArgumentException(
                    "Selector.items is not Option[], Map, or Collection");
                    }
    }

    private UIComponent getLabelComponent(FacesContext context,
            UIComponent component) throws IOException {
        
        // Check if the page author has defined a label facet
        //
        // What if the component is readonly ? Do we need to modify
        // the facet to be readonly, disabled, or required ?
        // What about styles, etc.
        //
        UIComponent labelComponent = component.getFacet("label"); //NOI18N
        if (labelComponent != null) {
            return labelComponent;
        }
        
        // If we find a label, define a label component
        //
        String attrvalue =
                (String)component.getAttributes().get("label"); //NOI18N
        if (attrvalue == null || attrvalue.length() <= 0) {
            return null;
        }
        
        // This code should be in the component.
        // But it is more complicated than that since the argument is
        // "UIComponent" and not RadioButtonGroup or CheckboxGroup.
        // Too much needs to be done so leave this way for now until
        // we fix all renderers with similar problems.
        //
        Label label= (Label)ComponentUtilities.getPrivateFacet(component,
                "label", true); //NOI18N
        if (label == null) {
            label= new Label();
            label.setId(ComponentUtilities.createPrivateFacetId(
                    component, "label")); //NOI18N
            ComponentUtilities.putPrivateFacet(component, "label", //NOI18N
                    label);
        }
        
        label.setText(attrvalue);
        
        // Set the labeledComponent. This will eventually resolve to the 
	// the first control.
	// And the indicatorComponent.
	//
	label.setLabeledComponent(component); 
	label.setIndicatorComponent(component); 
        
        // Give the group's tooltip to the group label
        //
        attrvalue =
                (String)component.getAttributes().get("toolTip"); //NOI18N
        if (attrvalue != null) {
            label.setToolTip(attrvalue);
        }
        
        Integer lblLvl = (Integer)
        component.getAttributes().get("labelLevel"); //NOI18N
        
        // Need to synch up defaults
        //
        if (lblLvl == null) {
            lblLvl = new Integer(2);
        }
        
        label.setLabelLevel(lblLvl == null ? 2 : lblLvl.intValue());
        
        return label;
    }

    private void setSubmittedValues(FacesContext context,
            UIComponent component) {
        
        String clientId = component.getClientId(context);
        if (component instanceof ComplexComponent) {
            clientId = (((ComplexComponent)component).getLabeledElementId(context));
        }
        
        Map requestParameterValuesMap = context.getExternalContext().
                getRequestParameterValuesMap();
        
        // If the clientId is found some controls are checked
        //
        if (requestParameterValuesMap.containsKey(clientId)) {
            String[] newValues = (String[])
            requestParameterValuesMap.get(clientId);
            
            ((UIInput) component).setSubmittedValue(newValues);
            return;
        }
        // Return if there are no disabledCheckedValues and there
        // were no controls checked
        //
        ((UIInput) component).setSubmittedValue(new String[0]);
        return;
    }
}
