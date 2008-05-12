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

import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders ProgressBar components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.ProgressBar",
    componentFamily="com.sun.webui.jsf.ProgressBar"))
public class ProgressBarRenderer extends RendererBase {
    /**
     * The set of pass-through html attributes to be rendered.
     */
    private static final String stringAttributes[] = {
        "style"};

    /**
     * The set of int attributes to be rendered.
     */
    private static final String intAttributes[] = {
         "progress",
         "width",
         "height",
         "refreshRate"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
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
	if (!(component instanceof ProgressBar)) {
	    throw new IllegalArgumentException(
                "ProgressBarRenderer can only render ProgressBar components.");
        }
        ProgressBar progressBar = (ProgressBar) component;

        JSONObject json = new JSONObject();
        // Send the context path to the client side widget.
        // This will be appended to the url of the image.
        String prefix = context.getExternalContext().getRequestContextPath();    
        
            json.put("failedStateText", progressBar.getFailedStateText())
            .put("logMessage",progressBar.getLogMessage())
            .put("overlayAnimation",progressBar.isOverlayAnimation())
            .put("percentChar", getTheme().getMessage("ProgressBar.percentChar"))
            .put("progressImageUrl", progressBar.getProgressImageUrl())
            .put("taskState", progressBar.getTaskState())
            .put("toolTip", (progressBar.getToolTip() != null)
                ? progressBar.getToolTip()
                : getTheme().getMessage("ProgressBar.toolTip"))
            .put("type", progressBar.getType())
            .put("className", progressBar.getStyleClass())
            .put("prefix", prefix)
            .put("refreshRate", progressBar.getRefreshRate())
            .put("autoStart", progressBar.isAutoStart())        
            .put("visible", progressBar.isVisible());
       
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
        JSONUtilities.addIntegerProperties(intAttributes, component, json);
        setFacetProperties(context, progressBar, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "progressBar";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to set facet properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param json JSONObject to append to.
     */
    private void setFacetProperties(FacesContext context, ProgressBar component, 
            JSONObject json) throws IOException, JSONException {
        // Button facets.
        UIComponent rightButtonCon = component.getFacet(
            ProgressBar.RIGHTTASK_CONTROL_FACET);
        UIComponent bottomButtonCon = component.getFacet(
            ProgressBar.BOTTOMTASK_CONTROL_FACET);
        JSONArray jArray = null;    
        if (rightButtonCon != null) {
            jArray = new JSONArray();
            json.put("progressControlRight", jArray);
            jArray.put(WidgetUtilities.renderComponent(context, rightButtonCon));
        }
        if (bottomButtonCon != null) {
            jArray = new JSONArray();
            json.put("progressControlBottom", jArray);
            jArray.put(WidgetUtilities.renderComponent(context, bottomButtonCon));
        }
                
        ProgressBar pb = (ProgressBar) component;
        if (pb.getLogMessage() != null) {
            // TextArea for running log
            UIComponent textArea = (TextArea) pb.getLogMsgComponent(component);
            
            json.put("log", WidgetUtilities.renderComponent(context, textArea)); 
            json.put("logId", textArea.getClientId(context));
        }

        // Bottom text facet.
        UIComponent bottomTextFacet = component.getFacet(
            ProgressBar.BOTTOMTEXT_FACET);
        if (bottomTextFacet != null) {            
            json.put("bottomText", WidgetUtilities.renderComponent(context, 
                bottomTextFacet));
        } else {
            json.put("bottomText", pb.getStatus());
        }
        
        // Top Text facet.
        UIComponent topTextFacet = component.getFacet(ProgressBar.TOPTEXT_FACET);
        if (topTextFacet != null) {
            json.put("topText", WidgetUtilities.renderComponent(context, 
                topTextFacet));
        } else {
            json.put("topText", pb.getDescription());
        }
    } 
}
