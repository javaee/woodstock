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
import com.sun.webui.jsf.component.HiddenField;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.MessageUtil;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders HiddenField component.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.HiddenField",
    componentFamily="com.sun.webui.jsf.HiddenField"))
public class HiddenFieldRenderer extends RendererBase {   
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Decode the component 
     * @param context The FacesContext associated with this request
     */
    public void decode(FacesContext context, UIComponent component) {
	if (!(component instanceof EditableValueHolder)) {
	    throw new IllegalArgumentException(
                "HiddenFieldRenderer can only render EditableValueHolder components.");
        }
        String id = component.getClientId(context); 
        Map params = context.getExternalContext().getRequestParameterMap();
        Object valueObject = params.get(id);
        String value = null; 
               
        if(valueObject != null) { 
            value = (String) valueObject;        
        }
        ((EditableValueHolder) component).setSubmittedValue(value);
    }

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
            UIComponent component) throws IOException, JSONException {
	if (!(component instanceof HiddenField)) {
	    throw new IllegalArgumentException(
                "HiddenFieldRenderer can only render HiddenField components.");
        }
        HiddenField hiddenField = (HiddenField) component;
        
        if (!(component instanceof HiddenField)) {
            Object[] params = { 
                component.toString(),
                this.getClass().getName(),
                HiddenField.class.getName()
            };
            String message = MessageUtil.getMessage(
                "com.sun.webui.jsf.resources.LogMessages",
                "Renderer.component", params);
            throw new FacesException(message);
        }
        
        // Record the value that is rendered.
	// Note that getValueAsString conforms to JSF conventions
	// for NULL values, in that it returns "" if the component
	// value is NULL. This value cannot be trusted since
	// the fidelity of the data must be preserved, i.e. if the
	// value is null, it must remain null if the component is unchanged
	// by the user..
	//
	// What should be done in the case of submittedValue != null ?
	// This call to getValue may not be value is used by
	// getValueAsString, it may use the submittedValue.
	// Then should the previously set rendered value be 
	// preserved ?
	// 
	// If submittedValue is not null then the component's
	// model value or local value has not been updated
	// therefore assume that this is an immediate or premature
	// render response. Therefore just assume that if the rendered
	// value was null, the saved information is still valid.
	// 
	if (((HiddenField) component).getSubmittedValue() == null) {
	    ConversionUtilities.setRenderedValue(component,
		((HiddenField) component).getText());
	}

        JSONObject json = new JSONObject();
        json.put("value", hiddenField.getValueAsString(context))
            .put("name", hiddenField.getClientId(context))
            .put("disabled", hiddenField.isDisabled());
        
        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "hiddenField";
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
