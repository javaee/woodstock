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
import com.sun.webui.jsf.component.TextArea;
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
 * TextAreaRenderer is basic CSR for TextArea component.
 * It renders basic set of TextArea properties in the form of json object
 * @see TextArea
 */
@Renderer(@Renderer.Renders(
rendererType="com.sun.webui.jsf.widget.TextArea",
        componentFamily="com.sun.webui.jsf.TextArea"))
        public class TextAreaRenderer extends FieldRendererBase {
    
    /** Creates a new instance of TextAreaRenderer */
    public TextAreaRenderer() {
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
        if (!(component instanceof TextArea)) {
            throw new IllegalArgumentException(
                    "TextAreaRenderer can only render TextArea components.");
        }
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.textArea"));
        
        TextArea field = (TextArea) component;
        if (field.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                    "widget.jsfx.textArea"));
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
        if (!(component instanceof TextArea)) {
            throw new IllegalArgumentException(
                    "TextAreaRenderer can only render TextArea components.");
        }
        TextArea field = (TextArea) component;
        Theme theme = ThemeUtilities.getTheme(context);
        
        // Set rendered value.
        if (field.getSubmittedValue() == null) {
            ConversionUtilities.setRenderedValue(component, field.getText());
        }
        
        String templatePath = field.getHtmlTemplate(); // Get HTML template.
        
        if (templatePath == null)
                templatePath = theme.getPathToTemplate(ThemeTemplates.TEXTAREA);
        
        long as = field.getAutoSave();
        String autoSave = (as > 0) ?
            Long.toString(as) : 
            "0";
        
        String className = field.getStyleClass();
        
        JSONObject json = new JSONObject();
        json.put("disabled", field.isDisabled())
            .put("value", field.getValueAsString(context))
            .put("required", field.isRequired())
            .put("valid", field.isValid())
            .put("className", className )
            .put("templatePath", templatePath)
            .put("cols", field.getColumns())
            .put("visible", field.isVisible())
            .put("rows", field.getRows())
            .put("title", field.getToolTip())        
            .put("autoSave", autoSave);
        
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
        return JavaScriptUtilities.getNamespace("textArea");
    }
    
 
}
