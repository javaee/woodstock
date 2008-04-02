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
import com.sun.webui.jsf.component.Bubble;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Bubble component.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Bubble",
        componentFamily="com.sun.webui.jsf.Bubble"))
public class BubbleRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {        
        "style"                
    };
    
    /**
     * The set of pass-through int attributes to be rendered.
     */
    private static final String intAttributes[] = {        
        "duration",
        "openDelay",
        "width"
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
	if (!(component instanceof Bubble)) {
	    throw new IllegalArgumentException(
                "BubbleHelpRenderer can only render Bubble components.");
        }
        Bubble bubble = (Bubble) component;
                        
        String templatePath = bubble.getHtmlTemplate(); // Get HTML template.
        JSONObject json = new JSONObject();
        json.put("title", bubble.getTitle())
            .put("visible", bubble.isVisible()) //bubble help should not be visible initially.
            .put("autoClose", bubble.isAutoClose())
            .put("closeButton", bubble.isCloseButton())
            .put("focusId", bubble.getFocusId())
            .put("tabIndex", bubble.getTabIndex())
            .put("className", bubble.getStyleClass());
        
        JSONArray jArray = new JSONArray();
        json.put("contents", jArray);
        
        // Bubble help children components.
        Iterator kids = bubble.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            if (kid.isRendered()) {
                jArray.put(WidgetUtilities.renderComponent(context, kid));
            }
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
        return "bubble";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
