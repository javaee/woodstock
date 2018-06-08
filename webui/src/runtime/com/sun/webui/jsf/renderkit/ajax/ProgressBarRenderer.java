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

package com.sun.webui.jsf.renderkit.ajax;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.ProgressBar;
import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class responds to Ajax requests made to ProgressBar components.
 */
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.ajax.ProgressBar",
componentFamily = "com.sun.webui.jsf.ProgressBar"))
public class ProgressBarRenderer extends javax.faces.render.Renderer {

    /**
     * Decode the component component
     *
     * @param context The FacesContext associated with this request
     * @param component The ProgressBar component to decode
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        String id = component.getClientId(context);
        Map params = context.getExternalContext().getRequestParameterMap();

        String buttonId = id + "_" + "controlType"; //NOI18N

        Object valueObject = params.get(buttonId);
        String value = null;
        if (valueObject != null) {
            value = ((String) valueObject).trim();
            ProgressBar progressBar = (ProgressBar) component;

            if (ProgressBar.TASK_STOPPED.equals(value)) {
                progressBar.setTaskState(ProgressBar.TASK_STOPPED);
            } else if (ProgressBar.TASK_PAUSED.equals(value)) {
                progressBar.setTaskState(ProgressBar.TASK_PAUSED);
            } else if (ProgressBar.TASK_RESUMED.equals(value)) {
                progressBar.setTaskState(ProgressBar.TASK_RESUMED);
            } else if (ProgressBar.TASK_CANCELED.equals(value)) {
                progressBar.setTaskState(ProgressBar.TASK_CANCELED);
            }
        }
    }

    /**
     * Render the beginning of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        // Do nothing...
    }

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        // Do nothing...
    }

    /**
     * Render the ending of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }

        ProgressBar progressBar = (ProgressBar) component;
        int progress = progressBar.getProgress();

        String status = progressBar.getStatus();
        String topText = progressBar.getDescription();
        String logMessage = progressBar.getLogMessage();
        String failedStateText = progressBar.getFailedStateText();

        // Top text facet.
        UIComponent topTextFacet = component.getFacet(ProgressBar.TOPTEXT_FACET);
        if (topTextFacet != null) {
            topText = null;
        }
        UIComponent bottomTextFacet = component.getFacet(ProgressBar.BOTTOMTEXT_FACET);
        if (bottomTextFacet != null) {
            status = null;
        }

        String taskState = progressBar.getTaskState();

        try {
            JSONObject json = new JSONObject();
            json.put("taskState", taskState);
            json.put("progress", String.valueOf(progress));
            json.put("status", status);
            json.put("topText", topText);
            json.put("logMessage", logMessage);
            json.put("failedStateText", failedStateText);
            json.write(context.getResponseWriter());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

