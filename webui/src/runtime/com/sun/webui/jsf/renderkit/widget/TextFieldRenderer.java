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
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.StringTokenizer;

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
    private static final String attributes[] = {
        "align",
        "dir",
        "lang",
        "style",
        "readOnly",
        "maxLength",
        "accessKey",
        "tabIndex",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "onClick",
        "onFocus",
        "onBlur",
        "onDblClick",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp"
    };
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo module required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getModule(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getModuleName("widget.textField");
    }

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
        Theme theme = ThemeUtilities.getTheme(context);
        
        // Set rendered value.
        if (field.getSubmittedValue() == null) {
            ConversionUtilities.setRenderedValue(component, field.getText());
        }
        
        String templatePath = field.getHtmlTemplate(); // Get HTML template.
        
        if (templatePath == null)
            templatePath  = theme.getPathToTemplate(ThemeTemplates.TEXTFIELD);
        
        String className = field.getStyleClass();
        
        JSONObject json = new JSONObject();
        json.put("disabled", field.isDisabled())
        .put("value", field.getValueAsString(context))
        .put("required", field.isRequired())
        .put("valid", field.isValid())
        .put("className", className )
        .put("templatePath", templatePath)
        .put("size", field.getColumns())
        .put("visible", field.isVisible())
        .put("title", field.getToolTip())
        .put("autoValidate", field.isAutoValidate());
        
        // Append label properties.
        WidgetUtilities.addProperties(json, "label",
            WidgetUtilities.renderComponent(context, field.getLabelComponent(
                context, null)));
        
        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);
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
        return JavaScriptUtilities.getNamespace("textField");
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
}
