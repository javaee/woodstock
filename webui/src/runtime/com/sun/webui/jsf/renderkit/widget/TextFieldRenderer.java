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
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * TextFieldRenderer is basic CSR for TextField component.
 * It renders basic set of TextField properties in the form of json object
 * @see TextField
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.TextField",
    componentFamily="com.sun.webui.jsf.TextField"))
public class TextFieldRenderer extends FieldRendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {
        "align",
        "dir",
        "lang",
        "style",        
        "accessKey",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "onClick",
        "onChange",
        "onSelect",
        "onFocus",
        "onBlur",
        "onDblClick",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp"
    };
    
    /**
     * The set of int attributes to be rendered.
     */
    private static final String intAttributes[] = {
        "maxLength",
        "tabIndex"
    };
    
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
        if (!(component instanceof TextField)) {
            throw new IllegalArgumentException(
                "TextFieldRenderer can only render TextField components.");
        }
        TextField field = (TextField) component;
        
        // Set rendered value.
        if (field.getSubmittedValue() == null) {
            ConversionUtilities.setRenderedValue(component, field.getText());
        }

        String className = field.getStyleClass();
        
        JSONObject json = new JSONObject();
        json.put("disabled", field.isDisabled())
        .put("value", field.getValueAsString(context))
        .put("required", field.isRequired())
        .put("readOnly", field.isReadOnly())        
        .put("valid", field.isValid())
        .put("className", className )
        .put("size", field.getColumns())
        .put("visible", field.isVisible())
        .put("title", field.getToolTip())
        .put("autoComplete", field.isAutoComplete())
        .put("autoValidate", field.isAutoValidate());

        if (field.isSubmitFormSet()) {
            json.put("submitForm", field.isSubmitForm());
	}
        
        // Append label properties.
	//
	JSONObject jlabel = getLabel(context, field);
	if (jlabel != null) {
	    json.put("label", jlabel);
	}
        
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        JSONUtilities.addIntegerProperties(intAttributes, component, json);
        setNotifyProperties(context, component, json);
        
        return json;
    }
    
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "textField";
    }

    /** 
     * Helper function to return an iterator over options as provided from autoCompleteExpression.
     * This method will return normalized iterator that contains elements
     * of underlying array, Collection, or Map(values).
     * 
     * If getAutoCompleteExpression returns an object of something other than 
     * array, Collection, or Map(values), this method will return null
     * 
     * @return iterator over options elements, or null otherwise
     */
    protected Option[] getListItems(TextField field, String value, FacesContext context) { 
       
         if (field == null || context == null)
             return null;
        
        Object optionsObject = null;
        
        MethodExpression filterMethod = field.getAutoCompleteExpression();
        if (filterMethod != null) {
            optionsObject = filterMethod.invoke(context.getELContext(), new Object[] { value });
        } else {
            throw new RuntimeException("autoCompleteExpression must not be null"); 
        }

        
        if (optionsObject == null)
            return null;


        if(optionsObject instanceof Option[]) { 
            return (Option[])optionsObject;            
        } 
        
        
        if(optionsObject instanceof Collection) { 
            try {
                return (Option[]) ((Collection)optionsObject).toArray(new Option[0]);
            } catch ( Exception e) {
                return null;
            }
        } 
        
        if(optionsObject instanceof Map) { 
            try {
                return (Option[]) ((Map)optionsObject).values().toArray(new Option[0]);
            } catch ( Exception e) {
                return null;
            }

        } 

        return null;
    } 
  
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**  
     * Helper method to obtain client IDs to update during text field events.
     *
     * @param context FacesContext for the current request.
     * @param component Table2RowGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setNotifyProperties(FacesContext context, UIComponent component, 
            JSONObject json) throws IOException, JSONException {
        TextField field = (TextField) component;
        String notify = field.getNotify();
        if (notify == null) {
            return;
        }

        JSONArray jArray = new JSONArray();
        json.put("notify", jArray);

        StringTokenizer st = new StringTokenizer(notify, ",");
        while (st.hasMoreTokens()) {
            jArray.put(st.nextToken());
        }
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
