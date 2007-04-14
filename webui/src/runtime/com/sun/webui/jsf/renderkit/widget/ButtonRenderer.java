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
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
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
        "alt", // not supported by button
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
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        Button button = (Button) component;

        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.button"));

        if (button.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                "widget.jsfx.button"));
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
            UIComponent component) throws JSONException, IOException {
        Button button = (Button) component;
        String templatePath = button.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("className", button.getStyleClass())
            .put("disabled", button.isDisabled())
            .put("mini", button.isMini())
            .put("name", button.getClientId(context))
            .put("primary", button.isPrimary())
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : getTheme().getPathToTemplate(ThemeTemplates.BUTTON))
            .put("title", button.getToolTip())
            .put("type", button.isReset() ? "reset" : "submit")
            .put("visible", button.isVisible());

        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);
        setContents(context, component, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType() {
        return JavaScriptUtilities.getNamespace("button");
    }

    /** 
     * Helper method to set button contents.
     *
     * @param context FacesContext for the current request.
     * @param component Table2RowGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setContents(FacesContext context, UIComponent component,
            JSONObject json) throws IOException, JSONException {
        // An array is used because the widget accepts multiple children.
        JSONArray jArray = new JSONArray();
        json.put("contents", jArray);

        Button button = (Button) component;
        String imageURL = button.getImageURL();
        String iconKey = button.getIcon();

        // Set icon flag.
        if (iconKey != null || imageURL != null) {
            json.put("icon", true);
        }

        // Add contents.
        if (imageURL != null) {
            // Add image.
            WidgetUtilities.addProperties(jArray,
                WidgetUtilities.renderComponent(context,
                    getImage(context, button, imageURL)));
            return;
        } else if (iconKey != null) {
            // Add icon.
            WidgetUtilities.addProperties(jArray,
                WidgetUtilities.renderComponent(context,
                    getIcon(context, button, iconKey)));
            return;
        }

        // Get the textual label of the button.
        String text = ConversionUtilities.convertValueToString(component,
            button.getText());

        // Pad the text per UI guidelines.
        String padding = null;
        if (text != null && text.trim().length() > 0) {
            if (!button.isNoTextPadding()) { 
                if (text.trim().length() <= 3) {
                    padding = "&#160;&#160;";
                } else if (text.trim().length() == 4) {
                    padding = "&#160;";
                }
            }
        }

        // Add padding.
        if (padding != null) {
            WidgetUtilities.addProperties(jArray, padding);
        }

        // Add static text.
        WidgetUtilities.addProperties(jArray,
            WidgetUtilities.renderComponent(context,
                getText(context, button, text)));
        
        // Add padding.
        if (padding != null) {
            WidgetUtilities.addProperties(jArray, padding);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to get contents id.
     */
    private String getContentsId(FacesContext context, Button component) {
        return (component.getId() != null)
            ? component.getId().concat(Button.CONTENTS_ID) : null;      
    }

    /** 
     * Helper method to get icon.
     */
    private UIComponent getIcon(FacesContext context, Button component,
            String iconKey) {
        // Set properties.
        Icon icon = ThemeUtilities.getIcon(ThemeUtilities.getTheme(context), iconKey);
        icon.setId(getContentsId(context, component));
        icon.setBorder(0);
        icon.setParent(component);

        return icon;
    }

    /** 
     * Helper method to get image.
     */
    private UIComponent getImage(FacesContext context, Button component, 
            String imageURL) {
        // Set properties.
        ImageComponent image = new ImageComponent();
        image.setAlt(component.getAlt());
        image.setBorder(0);
        image.setId(getContentsId(context, component));
        image.setParent(component);
        image.setUrl(imageURL);

        return image;
    }

    /** 
     * Helper method to get text.
     */
    private UIComponent getText(FacesContext context, Button component,
            String text) {
        // Set properties.
        StaticText staticText = new StaticText();
        staticText.setEscape(component.isEscape());
        staticText.setId(getContentsId(context, component));
        staticText.setParent(component);
        staticText.setText(text);

        return staticText;
    }

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}
