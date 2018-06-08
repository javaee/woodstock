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

package com.sun.webui.jsf.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.FactoryFinder;
import javax.faces.render.Renderer;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.RenderKit;
import javax.servlet.ServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class provides common methods for widget renderers.
 */
public class WidgetUtilities {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // JSON methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to add component properties.
     *
     * @param json The JSONArray to append value to.
     * @param value A string containing JSON or HTML text.
     */
    public static void addProperties(JSONArray json, String value) 
            throws JSONException {
        if (value != null) {
            try {
                // If JSON text is given, append a new JSONObject.
                json.put(new JSONObject(value));
            } catch (JSONException e) {
                // Append HTML string.
                json.put(value);
            }
        }
    }

    /**
     * Helper method to add component properties.
     *
     * @param json The JSONObject to append value to.
     * @param key A key string.
     * @param value A string containing JSON or HTML text.
     */
    public static void addProperties(JSONObject json, String key,
            String value) throws JSONException {
        if (value != null) {
            try {
                // If JSON text is given, append a new JSONObject.
                json.put(key, new JSONObject(value));
            } catch (JSONException e) {
                // Append HTML string.
                json.put(key, value);
            }
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to capture rendered component properties for client-side
     * rendering. Based on the component renderer, either JSON or HTML text may 
     * be returned.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @returns An HTML or JSON string.
     */
    public static String renderComponent(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        if (component == null || !component.isRendered()) {
            return null;
        }

        // Initialize Writer to buffer rendered output.
        ResponseWriter oldWriter = context.getResponseWriter();
        Writer strWriter = initStringWriter(context);

        // Render component and restore writer.
        RenderingUtilities.renderComponent(component, context);
        context.setResponseWriter(oldWriter);

        return strWriter.toString(); // Return buffered output.
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Writer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to initialize a writer used to buffer rendered output.
     * 
     * Note: Be certain to save the old writer pior to invoking this method. The
     * writer in the given context is replaced with a new writer.
     *
     * @param context FacesContext for the current request.
     *
     * @returns The Writer used to buffer rendered output.
     */
    protected static Writer initStringWriter(FacesContext context) {
        if (context == null) {
            return null;
        }

        // Get render kit.
        RenderKitFactory renderFactory = (RenderKitFactory)
        FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = renderFactory.getRenderKit(context,
            context.getViewRoot().getRenderKitId());

        // Get writers.
        ResponseWriter oldWriter = context.getResponseWriter();
        Writer strWriter = new FastStringWriter(1024);
        ResponseWriter newWriter = null;

        // Initialize new writer.
        if (null != oldWriter) {
            newWriter = oldWriter.cloneWithWriter(strWriter);
        } else {
            ExternalContext extContext = context.getExternalContext();
            ServletRequest request = (ServletRequest) extContext.getRequest();
            newWriter = renderKit.createResponseWriter(strWriter, null,
                request.getCharacterEncoding());
        }
        // Set new writer in context.
        context.setResponseWriter(newWriter);
        return strWriter;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
