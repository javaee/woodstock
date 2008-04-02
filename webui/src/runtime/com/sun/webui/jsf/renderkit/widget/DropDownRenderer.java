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
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders DropDown components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.DropDown",
    componentFamily="com.sun.webui.jsf.DropDown"))
public class DropDownRenderer extends ListRendererBase {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws JSONException, IOException{
	if (!(component instanceof DropDown)) {
	    throw new IllegalArgumentException(
                "DropDownRenderer can only render DropDown components.");
        }
        DropDown dropDown = (DropDown) component;

        // Forget about what was the value selected before.
        if (dropDown.isForgetValue()) {
            dropDown.setValue(null); 
        } 
        
        // Get the properties from the super class ListRendererBase
        JSONObject json = super.getProperties(context, (ListSelector) dropDown);
        
        // Render the element and attributes for this component
        json.put("visible", dropDown.isVisible())
            .put("submitForm", dropDown.isSubmitForm() );

        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "dropDown";
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
