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
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
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
    private static final String TYPE_ICON = "icon";
    private static final String TYPE_IMAGE = "image";
    private static final String TYPE_RESET = "reset";
    private static final String TYPE_SUBMIT = "submit";
    
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {
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
        "style"
    };
    
    /**
     * The set of pass-through int attributes to be rendered.
     */
    private static final String intAttributes[] = {        
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
        // Get type.
        Button button = (Button) component;
        String type = getType(button);

        // Set properties.
        JSONObject json = new JSONObject();
        json.put("className", button.getStyleClass())
            .put("disabled", button.isDisabled())
            .put("mini", button.isMini())
            .put("primary", button.isPrimary())
            .put("title", button.getToolTip())
            .put("reset", type.equals(TYPE_RESET))
            .put("visible", button.isVisible());

        if (type.equals(TYPE_ICON)) {
            setIconProperties(button, json);
        } else if (type.equals(TYPE_IMAGE)) {
            setImageProperties(context, button, json);        
        } else {
            setTextProperties(button, json);
        }

        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        JSONUtilities.addIntegerProperties(intAttributes, component, json); 
        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "button";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get button type.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    private String getType(Button component) {
        if (component.getIcon() != null) {
            return TYPE_ICON;
        } else if (component.getImageURL() != null) {
            return TYPE_IMAGE;
        } else if (component.isReset()){
            return TYPE_RESET;
        } else {
            return TYPE_SUBMIT;
        }
    }

    /** 
     * Helper method to obtain icon properties.
     *
     * @param component Button to be rendered.
     * @param json JSONObject to assign properties to.
     */
    private void setIconProperties(Button component, JSONObject json) 
            throws JSONException {
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
        // Set properties.
        json.put("alt", component.getAlt())
            .put("prefix", context.getExternalContext().getRequestContextPath())
            .put("src", component.getImageURL());
    }

    /** 
     * Helper method to obtain text properties.
     *
     * @param component Button to be rendered.
     * @param json JSONObject to assign properties to.
     */
    private void setTextProperties(Button component, JSONObject json) 
            throws JSONException {
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
