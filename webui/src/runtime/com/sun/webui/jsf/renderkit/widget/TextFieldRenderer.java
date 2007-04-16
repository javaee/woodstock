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
        "title",
        "readOnly",
        "size",
        "maxLength",
        "accesskey",
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

        String id = component.getClientId(context);
        Map params = context.getExternalContext().getRequestParameterMap();
        Object valueObject = params.get(id);
        String value = null;

        if (valueObject == null && component instanceof Field) {
            id = getInputContainerId(context, component);
            valueObject = params.get(id);
        }

        if (valueObject != null) {
            value = (String) valueObject;       
            if(component instanceof Field && ((Field)component).isTrim()) {
                value = value.toString().trim();
            }
        }
        ((EditableValueHolder) component).setSubmittedValue(value);
    }

    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        
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
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        TextField field = (TextField) component;
        Theme theme = ThemeUtilities.getTheme(context);
        
        // Set rendered value.
        if (field.getSubmittedValue() == null) {
            ConversionUtilities.setRenderedValue(component, field.getValue());
        }
        
        String templatePath = field.getHtmlTemplate(); // Get HTML template.
        // TODO is getTheme() always non-null ?
        if (templatePath == null)
            templatePath = getTheme().getPathToTemplate(ThemeTemplates.TEXTFIELD);
        
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
            .put("type", field.isPasswordMode() ? "password" : "text") //NOI18N
            .put("autoValidate", field.isAutoValidate());
             
        // Append label properties.
        WidgetUtilities.addProperties(json, "label",
                WidgetUtilities.renderComponent(context, field.getLabelComponent(context, "")));
        
        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);
                
        return json;
    }
    
 
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    public static final String INPUT_CONTAINER_NAME = Field.INPUT_ID; //"_inputContainer";
    private String getInputContainerId(FacesContext context, UIComponent component) {
        TextField field = (TextField) component;
        String id = field.getClientId(context);
        return id.concat(INPUT_CONTAINER_NAME); 
    }
    
    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    /**
     * Returns the type of the widget to be used.
     */
    protected String getWidgetType() {
         return JavaScriptUtilities.getNamespace("textField");
    }
 
}
