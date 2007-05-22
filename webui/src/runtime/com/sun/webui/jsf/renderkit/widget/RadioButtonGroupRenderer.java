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
import com.sun.webui.jsf.component.Selector;
import com.sun.webui.jsf.model.Option;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.RadioButton;
import com.sun.webui.jsf.component.RadioButtonGroup;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.theme.Theme;
import java.io.IOException;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.RadioButtonGroup",
    componentFamily="com.sun.webui.jsf.RadioButtonGroup"))
public class RadioButtonGroupRenderer extends SelectorGroupRenderer {    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~     
    
     protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        if (!(component instanceof RadioButtonGroup)) { 
            throw new IllegalArgumentException(
                    "RadioButtonGroupRenderer can only render RadioButtonGroup components.");
        }
        RadioButtonGroup rbGroup = (RadioButtonGroup) component;
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.rbcbGroup"));
        
        if (rbGroup.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                    "widget.jsfx.rbcbGroup"));
        }
        return json;
    }
    

    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws JSONException, IOException {
        if (!(component instanceof RadioButtonGroup)) { 
            throw new IllegalArgumentException(
                    "RadioButtonGroupRenderer can only render RadioButtonGroup components.");
        }
        RadioButtonGroup rbGroup = (RadioButtonGroup) component;
        String templatePath = rbGroup.getHtmlTemplate();
        Theme theme = ThemeUtilities.getTheme(context);        
        
        JSONObject json = super.getProperties(context, (Selector)rbGroup);       
        json.put("templatePath", (templatePath != null)
                ? templatePath
                : theme.getPathToTemplate(ThemeTemplates.RBCBGroup));
        return json;
                
    }
    
    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("rbcbGroup");
    }
    
    

    /**
     * Return a RadioButton component to render.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>RadioButtonGroup</code> component rendered     
     * @param option the <code>Option</code> being rendered.
     */
    protected UIComponent getSelectorComponent(FacesContext context,
	UIComponent component, String id, Option option) {

	RadioButtonGroup rbgrp = (RadioButtonGroup)component;        
        String componentId = rbgrp.getClientId(context);
        if (rbgrp instanceof ComplexComponent) {
            componentId = ((ComplexComponent)rbgrp).getLabeledElementId(context);
        }
        
	RadioButton rb = new RadioButton();
	rb.setId(id);
	rb.setParent(component);

	rb.setName(componentId);        
	rb.setToolTip(option.getTooltip());
	rb.setImageURL(option.getImage());
	rb.setSelectedValue(option.getValue());
	rb.setLabel(option.getLabel());
	rb.setDisabled(rbgrp.isDisabled());
	rb.setReadOnly(rbgrp.isReadOnly());
	rb.setTabIndex(rbgrp.getTabIndex());
        
	// Default to not selected
	//
	rb.setSelected(null);

	// Need to check the submittedValue for immediate condition
	// 
	String[] subValue = (String[])rbgrp.getSubmittedValue();
	if (subValue == null) {
	    if (isSelected(option, rbgrp.getSelected())) {
		rb.setSelected(rb.getSelectedValue());
	    }
	} else
	if (subValue.length != 0) {
	    Object selectedValue = rb.getSelectedValue();
	    String selectedValueAsString =
		ConversionUtilities.convertValueToString(component,
			selectedValue);
	    if (subValue[0] != null && 
			subValue[0].equals(selectedValueAsString)) {
		rb.setSelected(rb.getSelectedValue());
	    }
	}

	return rb;
    }

    /**
     * Return true if the <code>item</item> argument is the currently
     * selected radio button. Equality is determined by the <code>equals</code>
     * method of the object instance stored as the <code>value</code> of
     * <code>item</code>. Return false otherwise.
     *
     * @param item the current radio button being rendered.
     * @param currentValue the value of the current selected radio button.
     */
    private boolean isSelected(Option item, Object currentValue) {
	return currentValue != null && item.getValue() != null &&
		item.getValue().equals(currentValue);
    }    
    
}
