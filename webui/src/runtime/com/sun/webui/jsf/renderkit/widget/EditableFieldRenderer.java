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
import com.sun.webui.jsf.component.EditableField;
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
 * EditableFieldRenderer is basic CSR for EditableField component.
 * It renders basic set of EditableField properties in the form of json object
 * @see EditableField
 */
@Renderer(@Renderer.Renders(
rendererType="com.sun.webui.jsf.widget.EditableField",
        componentFamily="com.sun.webui.jsf.EditableField"))
        public class EditableFieldRenderer extends TextFieldRenderer {
        
    
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
        if (!(component instanceof EditableField)) {
            throw new IllegalArgumentException(
                    "EditableFieldRenderer can only render EditableField components.");
        }
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.editableField"));
       
        EditableField field = (EditableField) component;
        if (field.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                    "widget.jsfx.editableField")); //textfield script is used for ajax functionality
        }
        return json;
    }
    
    /**
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("editableField");
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
        if (!(component instanceof EditableField)) {
            throw new IllegalArgumentException(
                    "EditableFieldRenderer can only render EditableField components.");
        }
        EditableField field = (EditableField) component;
        JSONObject json = super.getProperties(context, component);
        
        json.put("autoSave", field.isAutoSave());
        return json;
    }
    
}
