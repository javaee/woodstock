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
import com.sun.webui.theme.Theme;
import com.sun.webui.theme.ThemeImage;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

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
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "style"};

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
	if (!(component instanceof ProgressBar)) {
	    throw new IllegalArgumentException(
                "ProgressBarRenderer can only render ProgressBar components.");
        }
        ProgressBar progressBar = (ProgressBar) component;

        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.progressBar"));

        if (progressBar.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                "widget.jsfx.progressBar"));
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
	if (!(component instanceof ProgressBar)) {
	    throw new IllegalArgumentException(
                "ProgressBarRenderer can only render ProgressBar components.");
        }
        ProgressBar progressBar = (ProgressBar) component;
        Theme theme = ThemeUtilities.getTheme(context);
        String templatePath = progressBar.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("barHeight", progressBar.getHeight())
            .put("barWidth", progressBar.getWidth())
            .put("failedStateText", progressBar.getFailedStateText())
            .put("logMessage",progressBar.getLogMessage())
            .put("overlayAnimation",progressBar.isOverlayAnimation())
            .put("percentChar", theme.getMessage("ProgressBar.percentChar"))
            .put("progress", String.valueOf(progressBar.getProgress()))
            .put("progressImageUrl", progressBar.getProgressImageUrl())
            .put("refreshRate", progressBar.getRefreshRate())
            .put("taskState", progressBar.getTaskState())
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : theme.getPathToTemplate(ThemeTemplates.PROGRESSBAR))
            .put("toolTip", (progressBar.getToolTip() != null)
                ? progressBar.getToolTip()
                : theme.getMessage("ProgressBar.toolTip"))
            .put("type", progressBar.getType())
            .put("visible", progressBar.isVisible());

        // Add busy icon.
        WidgetUtilities.addProperties(json, "busyImage",
                WidgetUtilities.renderComponent(context, 
                    progressBar.getBusyIcon()));

        // Append properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);
        setFacetProperties(context, progressBar, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType() {
        return JavaScriptUtilities.getNamespace("progressBar");
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

        if (rightButtonCon != null) {
            WidgetUtilities.addProperties(json, "progressControlRight",
                WidgetUtilities.renderComponent(context, rightButtonCon));
        }
        if (bottomButtonCon != null) {
            WidgetUtilities.addProperties(json, "progressControlBottom",
                WidgetUtilities.renderComponent(context, bottomButtonCon));
        }
                
        ProgressBar pb = (ProgressBar) component;
        if (pb.getLogMessage() != null) {
            // TextArea for running log
            UIComponent textArea = (TextArea) pb.getLogMsgComponent(component);
            
            WidgetUtilities.addProperties(json, "log",
                WidgetUtilities.renderComponent(context, textArea)); 
            json.put("logId", textArea.getClientId(context));
        }

        // Bottom text facet.
        UIComponent bottomTextFacet = component.getFacet(
            ProgressBar.BOTTOMTEXT_FACET);
        if (bottomTextFacet != null) {            
            WidgetUtilities.addProperties(json, "bottomText",
                    WidgetUtilities.renderComponent(context, bottomTextFacet));
        } else {
            json.put("bottomText", pb.getStatus());
        }
        
        // Top Text facet.
        UIComponent topTextFacet = component.getFacet(ProgressBar.TOPTEXT_FACET);
        if (topTextFacet != null) {
            WidgetUtilities.addProperties(json, "topText",
                WidgetUtilities.renderComponent(context, topTextFacet));
        } else {
            json.put("topText", pb.getDescription());
        }
    } 
}
