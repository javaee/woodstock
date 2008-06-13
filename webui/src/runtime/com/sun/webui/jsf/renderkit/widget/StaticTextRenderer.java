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
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders StaticText component.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.StaticText",
    componentFamily="com.sun.webui.jsf.StaticText"))
public class StaticTextRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "style",
        "onClick",
        "onDblClick",
        "dir",
        "lang"                        
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
	if (!(component instanceof StaticText)) {
	    throw new IllegalArgumentException(
                "StaticTextRenderer can only render StaticText components.");
        }
        StaticText staticText = (StaticText) component;
        String message = null;                        
        String currentValue = ConversionUtilities.convertValueToString(
            component, staticText.getText());
      
        if (currentValue != null) {
            java.util.ArrayList parameterList = new ArrayList();
            
            // get UIParameter children...
            
            java.util.Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if (!(kid instanceof UIParameter)) {
                    continue;
                }
                parameterList.add(((UIParameter) kid).getValue());
            }
            
            // If at least one substitution parameter was specified,
            // use the string as a MessageFormat instance.
            
            if (parameterList.size() > 0) {
                message = MessageFormat.format(currentValue, 
                    parameterList.toArray(new Object[parameterList.size()]));
            } else {
                message = currentValue;
            }
        }
        
        String templatePath = staticText.getHtmlTemplate(); // Get HTML template.
        JSONObject json = new JSONObject();
        json.put("value", message)
            .put("title", staticText.getToolTip())
            .put("escape", staticText.isEscape())
            .put("className", staticText.getStyleClass())
            .put("visible", staticText.isVisible());
        
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        
        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "text";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
