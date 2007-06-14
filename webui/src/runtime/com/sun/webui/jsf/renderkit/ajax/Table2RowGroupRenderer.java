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
package com.sun.webui.jsf.renderkit.ajax;

import com.sun.faces.annotation.Renderer;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.webui.jsf.component.Table2RowGroup;
import com.sun.webui.jsf.util.ComponentUtilities;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table2RowGroup components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.ajax.Table2RowGroup",
    componentFamily="com.sun.webui.jsf.Table2RowGroup"))
public class Table2RowGroupRenderer
        extends com.sun.webui.jsf.renderkit.widget.Table2RowGroupRenderer{
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
    public void encodeBegin(FacesContext context, UIComponent component) {
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
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Output component properties if Ajax request and is refresh event.
        if (ComponentUtilities.isAjaxRequest(context, component, "refresh")) {
            super.encodeChildren(context, component);
        }

        // Return if Ajax request and is not scroll event.
        if (!ComponentUtilities.isAjaxRequest(context, component, "scroll")) {
            return;
        }

        try {
            // Get XJSON header.
            Map map = context.getExternalContext().getRequestHeaderMap();
            JSONObject xjson = new JSONObject((String)
                map.get(AsyncResponse.XJSON_HEADER));

            // Set first row.
            Table2RowGroup group = (Table2RowGroup) component;
            group.setFirst(xjson.getInt("first")); // To do: move to decode method?

            // Get properties.
            JSONObject json = new JSONObject();
            json.put("id", group.getClientId(context)); // For publishEndEvent.
            setRowProperties(context, group, json);

            json.write(context.getResponseWriter());
        } catch(JSONException e) {
            e.printStackTrace();
        }
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
    public void encodeEnd(FacesContext context, UIComponent component) {
        // Do nothing...
    }
}
