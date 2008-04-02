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
import com.sun.webui.jsf.component.Selector;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.component.RadioButton;
import com.sun.webui.jsf.component.RadioButtonGroup;
import com.sun.webui.jsf.component.RbCbSelector;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;

@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.RadioButtonGroup",
    componentFamily="com.sun.webui.jsf.RadioButtonGroup"))
public class RadioButtonGroupRenderer extends SelectorGroupRenderer {   
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
            UIComponent component) throws JSONException, IOException {
        if (!(component instanceof RadioButtonGroup)) { 
            throw new IllegalArgumentException(
                "RadioButtonGroupRenderer can only render RadioButtonGroup components.");
        }
        RadioButtonGroup rbGroup = (RadioButtonGroup) component;      
        
        JSONObject json = super.getProperties(context, (Selector) rbGroup);       
        json.put("columns", rbGroup.getColumns());
        return json;     
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "radioButtonGroup";
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~       

    /**
     * Instantiate and return a <code>RadioButton</code> component.
     */
    protected RbCbSelector createSelectorComponent() {
        return new RadioButton();
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Return true if the <code>item</item> argument is the currently
     * selected radio button. Equality is determined by the <code>equals</code>
     * method of the object instance stored as the <code>value</code> of
     * <code>item</code>. Return false otherwise.
     *
     * @param item the current radio button being rendered.
     * @param currentValue the value of the current selected radio button.
     */
    protected boolean isSelected(Option item, Object currentValue) {
	return currentValue != null && item.getValue() != null &&
            item.getValue().equals(currentValue);
    }
}
