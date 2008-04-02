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
import com.sun.webui.jsf.component.RbCbSelector;
import com.sun.webui.jsf.component.Selector;
import com.sun.webui.jsf.model.Option;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;

@Renderer(@Renderer.Renders(
    rendererType ="com.sun.webui.jsf.widget.CheckboxGroup",
    componentFamily="com.sun.webui.jsf.CheckboxGroup"))
public class CheckboxGroupRenderer extends SelectorGroupRenderer {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
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

        JSONObject json = super.getProperties(context, (Selector) cbGroup);
        json.put("columns", cbGroup.getColumns());
        return json;
        
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "checkboxGroup";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Instantiate and return a <code>Checkbox</code> component.
     */
    protected RbCbSelector createSelectorComponent() {
        return new Checkbox();
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
    protected boolean isSelected(Option item, Object currentValue) {
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
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
