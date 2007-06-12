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
import com.sun.webui.jsf.component.CheckboxGroup;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Selector;
import com.sun.webui.jsf.model.Option;
import java.util.Collection;
import java.util.Map;
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
    rendererType ="com.sun.webui.jsf.widget.CheckboxGroup",
    componentFamily="com.sun.webui.jsf.CheckboxGroup"))
public class CheckboxGroupRenderer extends SelectorGroupRenderer {
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    protected JSONArray getModules(FacesContext context, UIComponent component)
             throws JSONException {   
        if (!(component instanceof CheckboxGroup)) { 
            throw new IllegalArgumentException(
                    "CheckboxGroupRenderer can only render CheckboxGroup components.");
        } 
        CheckboxGroup cbGroup = (CheckboxGroup) component;
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.checkboxGroup"));
        
        if (cbGroup.isAjaxify()) {
              json.put(JavaScriptUtilities.getModuleName(
                      "widget.jsfx.checkboxGroup"));
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
        if (!(component instanceof CheckboxGroup)) { 
            throw new IllegalArgumentException(
                    "CheckboxGroupRenderer can only render CheckboxGroup components.");
        } 
        CheckboxGroup cbGroup = (CheckboxGroup) component;
        String templatePath = cbGroup.getHtmlTemplate();
        Theme theme = ThemeUtilities.getTheme(context);
        
        JSONObject json = super.getProperties(context, (Selector)cbGroup);
        json.put("templatePath", (templatePath != null)
                ? templatePath
                : theme.getPathToTemplate(ThemeTemplates.CHECKBOXGROUP));
        json.put("columns", cbGroup.getColumns());
        return json;
        
    }
    
    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("checkboxGroup");
    }
    
    
    
    
    /**
     * Return a Checkbox component to render.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>CheckboxGroup</code> component rendered
     * @param theme <code>Theme</code> for the component
     * @param option the <code>Option</code> being rendered.
     */
    protected UIComponent getSelectorComponent(FacesContext context,
            UIComponent component, String id, Option option) {
        
        CheckboxGroup cbgrp = (CheckboxGroup)component;
        String componentId = cbgrp.getClientId(context);
        if (cbgrp instanceof ComplexComponent) {
            componentId = ((ComplexComponent)cbgrp).getLabeledElementId(context);
        }
        
        Checkbox cb = new Checkbox();
        cb.setId(id);
        cb.setParent(cbgrp);
        
        cb.setName(componentId);
        cb.setToolTip(option.getTooltip());
        cb.setImageURL(option.getImage());
        cb.setSelectedValue(option.getValue());
        cb.setLabel(option.getLabel());
        cb.setDisabled(cbgrp.isDisabled());
        cb.setReadOnly(cbgrp.isReadOnly());
        cb.setTabIndex(cbgrp.getTabIndex());     
        
        // Default to not selected
        //
        cb.setSelected(null);
        
        // Need to check the submittedValue for immediate condition
        //
        String[] subValue = (String[])cbgrp.getSubmittedValue();
        if (subValue == null) {
            if (isSelected(option, cbgrp.getSelected())) {
                cb.setSelected(cb.getSelectedValue());
            }
        } else
            if (subValue.length != 0) {
            Object selectedValue = cb.getSelectedValue();
            String selectedValueAsString =
                    ConversionUtilities.convertValueToString(component,
                    selectedValue);
            for (int i = 0; i < subValue.length; ++i) {
                if (subValue[i] != null &&
                        subValue[i].equals(selectedValueAsString)) {
                    cb.setSelected(cb.getSelectedValue());
                    break;
                }
            }
            }
        
        return cb;
    }
    
    /**
     * Return true if the <code>item</item> argument is the currently
     * selected checkbox. Equality is determined by the <code>equals</code>
     * method of the object instance stored as the <code>value</code> of
     * <code>item</code>. Return false otherwise.
     *
     * @param item the current checkbox being rendered.
     * @param currentValue the value of the current selected checkbox.
     */
    private boolean isSelected(Option item, Object currentValue) {
        // How is the selected value determined ?
        // Is it the Selection value on CheckboxGroup or
        // the boolean value on the current Selection being processed ?
        //
        Object value = item.getValue();
        if (value == null || currentValue == null) {
            return false;
        }
        if (currentValue instanceof Map) {
            return ((Map)currentValue).containsValue(value);
        } else
            if (currentValue instanceof Collection) {
            return ((Collection)currentValue).contains(value);
            } else
                if (currentValue instanceof Object[]) {
            Object[] selectedValues = (Object[])currentValue;
            for (int i = 0; i < selectedValues.length; ++i) {
                if (value.equals(selectedValues[i])) {
                    return true;
                }
            }
                }
        return false;
        
    }
    
}
