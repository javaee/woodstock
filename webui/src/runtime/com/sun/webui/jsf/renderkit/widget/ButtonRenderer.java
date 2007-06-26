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

import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Button", 
    componentFamily="com.sun.webui.jsf.Button"))
public class ButtonRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "accessKey",
        "alt",
        "align",
        "dir",
        "lang",
        "onBlur",
        "onClick",
        "onDblClick",
        "onFocus",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "style",
        "tabIndex"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Determine if this was the component that submitted the form.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     */
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
	if (!(component instanceof Button)) {
	    throw new IllegalArgumentException(
                "ButtonRenderer can only decode Button components.");
        }
        Button button = (Button) component;

        // Do not process disabled or reset components.
        if (button.isReset()) {
            return;
        }

        // Was our command the one that caused this submission?
        String clientId = button.getClientId(context);
        Map map = context.getExternalContext().getRequestParameterMap();
        
        if (map.containsKey(clientId) || (map.containsKey(clientId + ".x") 
                && map.containsKey(clientId + ".y"))) {
            button.queueEvent(new ActionEvent(button));
        }
    }

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
        return JavaScriptUtilities.getModuleName("widget.button");
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
            UIComponent component) throws JSONException, IOException {
	if (!(component instanceof Button)) {
	    throw new IllegalArgumentException(
                "ButtonRenderer can only render Button components.");
        }
        Button button = (Button) component;
        String imageUrl = button.getImageURL();
        String icon = button.getIcon();

        // Set properties.
        JSONObject json = new JSONObject();
        json.put("className", button.getStyleClass())
            .put("disabled", button.isDisabled())
            .put("mini", button.isMini())
            .put("primary", button.isPrimary())
            .put("title", button.getToolTip())
            .put("visible", button.isVisible());

        if (imageUrl != null) {
            setImageProperties(context, button, json);
        } else if (icon != null){
            setIconProperties(context, button, json);
        } else {
            setTextProperties(context, button, json);
        }

        // Add attributes.
        JSONUtilities.addAttributes(attributes, component, json);

        return json;
    }

    /**
     * Get the template path for this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getTemplatePath(FacesContext context, UIComponent component) {
	if (!(component instanceof Button)) {
	    throw new IllegalArgumentException(
                "ButtonRenderer can only render Button components.");
        }
        Button button = (Button) component;
        String imageUrl = button.getImageURL();
        String icon = button.getIcon();
        Theme theme = getTheme();
        
        // Get template.
        String templatePath = button.getHtmlTemplate();
        if (templatePath == null) {
            if (imageUrl != null || icon != null) {
                templatePath = theme.getPathToTemplate(ThemeTemplates.IMAGEBUTTON);
            } else if (button.isReset()) {
                templatePath = theme.getPathToTemplate(ThemeTemplates.RESETBUTTON);
            } else {
                templatePath = theme.getPathToTemplate(ThemeTemplates.BUTTON);
            }
        }
        return templatePath;
    }

    /**
     * Get the name of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetName(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("button");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain icon properties.
     *
     * @param context FacesContext for the current request.
     * @param component Button to be rendered.
     * @param json JSONObject to assign properties to.
     */
    private void setIconProperties(FacesContext context, Button component,
            JSONObject json) throws JSONException {
        // Get themed icon.
        Icon icon = ThemeUtilities.getIcon(getTheme(), component.getIcon());

        // Set properties.
        json.put("alt", icon.getAlt())
            .put("src", icon.getUrl());
    }

    /** 
     * Helper method to obtain image properties.
     *
     * @param context FacesContext for the current request.
     * @param component Button to be rendered.
     * @param json JSONObject to assign properties to.
     */
    private void setImageProperties(FacesContext context, Button component,
            JSONObject json) throws JSONException {
        // Append context path to relative URLs.
        String url = context.getApplication().getViewHandler().
            getResourceURL(context, component.getImageURL()); 

        // Set properties.
        json.put("alt", component.getAlt())
            .put("src", url);        
    }

    /** 
     * Helper method to obtain text properties.
     *
     * @param context FacesContext for the current request.
     * @param component Button to be rendered.
     * @param json JSONObject to assign properties to.
     */
    private void setTextProperties(FacesContext context, Button component,
            JSONObject json) throws JSONException {
        // Get the textual label of the button.
        String text = ConversionUtilities.convertValueToString(component,
            component.getText());

        // Pad the text per UI guidelines.
        if (text != null && text.trim().length() > 0) {
            if (!component.isNoTextPadding()) { 
                if (text.trim().length() <= 3) {
                    text = "  " + text + "  ";
                } else if (text.trim().length() == 4) {
                    text = " " + text + " ";
                }
            }
        }

        // Set properties.
        json.put("value", text)
            .put("escape", component.isEscape());
    }
}
