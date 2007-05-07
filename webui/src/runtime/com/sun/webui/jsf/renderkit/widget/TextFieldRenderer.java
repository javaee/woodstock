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
import com.sun.webui.jsf.component.Field;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
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
        public class TextFieldRenderer extends RendererBase {
    
    /** Creates a new instance of TextFieldRenderer */
    public TextFieldRenderer() {
    }
    
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
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Decode the TextField component
     *
     * @param context The FacesContext associated with this request
     * @param component The TextField2 component to decode
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!(component instanceof Field)) {
            throw new IllegalArgumentException(
                    "TextFieldRenderer can only decode Field components.");
        }
        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException(
                    "TextFieldRenderer can only decode EditableValueHolder components.");
        }
        Field field = (Field)component;
        if (field.isDisabled() || field.isReadOnly()) {
            return;
        }
                
       String id = field.getClientId(context); 
        if (field instanceof ComplexComponent) {
            // This must be the id of the submitted element.
            // For now it is the same as the labeled element
            //
            id = field.getLabeledElementId(context);
        }
        if (id == null)
            return;
       
        String value = null;
        Map params = context.getExternalContext().getRequestParameterMap();
        Object valueObject = params.get(id);
        if (valueObject != null) { 
            value = (String)valueObject;
            if (field.isTrim()) {
                value = value.toString().trim();
            }
        }
        field.setSubmittedValue(value);
    
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception JSONException if a key/value error occurs
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
    throws JSONException {
        if (!(component instanceof TextField)) {
            throw new IllegalArgumentException(
                    "TextFieldRenderer can only render TextField components.");
        }
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.textField"));
        
        TextField field = (TextField) component;
        if (field.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                    "widget.jsfx.textField"));
        }
        return json;
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
            templatePath =   field.isPassword() ?
                theme.getPathToTemplate(ThemeTemplates.PASSWORDFIELD):
                theme.getPathToTemplate(ThemeTemplates.TEXTFIELD);
        
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
                WidgetUtilities.renderComponent(context, field.getLabelComponent(context, null)));
        
        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);
        
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
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}
