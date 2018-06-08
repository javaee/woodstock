/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.ProgressBar;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders ProgressBar components.
 */
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.widget.ProgressBar",
componentFamily = "com.sun.webui.jsf.ProgressBar"))
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
        ProgressBar progressBar = (ProgressBar) component;
        Theme theme = ThemeUtilities.getTheme(context);
        String templatePath = progressBar.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("barHeight", progressBar.getHeight()).put("barWidth", progressBar.getWidth()).put("failedStateText", progressBar.getFailedStateText()).put("logMessage", progressBar.getLogMessage()).put("overlayAnimation", progressBar.isOverlayAnimation()).put("percentChar", theme.getMessage("ProgressBar.percentChar")).put("progress", String.valueOf(progressBar.getProgress())).put("progressImageUrl", progressBar.getProgressImageUrl()).put("refreshRate", progressBar.getRefreshRate()).put("taskState", progressBar.getTaskState()).put("templatePath", (templatePath != null)
                ? templatePath
                : theme.getPathToTemplate(ThemeTemplates.PROGRESSBAR)).put("toolTip", (progressBar.getToolTip() != null)
                ? progressBar.getToolTip()
                : theme.getMessage("ProgressBar.toolTip")).put("type", progressBar.getType()).put("visible", progressBar.isVisible());

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
