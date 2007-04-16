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
package com.sun.webui.jsf.util;

import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.model.Indicator;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
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

   /**
    * Helper method to translate a typical URL. It takes a given url and 
    * appends parameters if the component has any and returns back the string
    * after calling <code>ExternalContext.encodeResourceURL()</code>.
    * <p>
    * Note: Path must be a valid absolute URL or full path URI.
    * </p>
    *
    * @param context The faces context    
    * @param component The component that may contain parameters to be appended
    * along with the url.
    * @param url The value passed in by the developer for the url
    */      
    public static String translateURL(FacesContext context, 
            UIComponent component, String url) {
        String name = null;
        StringBuffer sb = new StringBuffer(url);
        RenderingUtilities.Param[] paramList = RenderingUtilities.
            getParamList(context, component); 
        int len = paramList.length;        
        if (len > 0) {
            sb.append("?");
        }        
        for (int i = 0 ; i < len ; i++) {
            if (0 != i) {
                sb.append("&");
            }
            name = paramList[i].getName();
            if (name == null) {
                continue;
            }
            sb.append(name);
            sb.append("=");
            sb.append(paramList[i].getValue());            
        }
        return context.getExternalContext().encodeResourceURL(sb.toString());
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
            newWriter = context.getRenderKit().createResponseWriter(
                strWriter, null, request.getCharacterEncoding());
        }
        // Set new writer in context.
        context.setResponseWriter(newWriter);
        return strWriter;
    } 
    
    
    /**
     * Helper method to obtain a list of indicators for Alert and Alarm components.
     * 
     * @param context FacesContext for the current request.
     * @param Iterator<Indicators> for the indicators.
     * @param String ignoreType for the indicators type.
     * @param Theme theme for the indicators.
     * @param UIComponent parent for the indicators.
     *
     * @returns JSONArray of Indicators.
     */
    public static JSONArray getIndicators( FacesContext context, Iterator<Indicator> indicators,  
        String ignoreType, Theme theme, UIComponent parent) throws IOException, JSONException {
        ImageComponent img = null;
        if (indicators == null) {
            return null;
        }
        JSONArray indicatorArray = new JSONArray();      
        while (indicators.hasNext()) {

            Indicator indicator = (Indicator)indicators.next();                 
            String type = (String) indicator.getType();

            // Don't do anything if we don't have to.
            //
            if (type.equals(ignoreType)) {
                    continue;
            }

            JSONObject indjson = new JSONObject();
            if (indicator.getImageComponent(theme) != null) {
                    img = (ImageComponent) indicator.getImageComponent(theme);
                         
            } else {
                    // Since the image may be theme based it is not a good idea
                    // to throw exception at runtime. Using "dot" image to handle
                    // this situation.
                img = (ImageComponent) ThemeUtilities.getIcon(theme, ThemeImages.DOT); 
            }
                //set the id
                if (img.getId() == null) {
                    img.setId(type);
                }
                //set the parent
                if (img.getParent() == null) {
                    img.setParent(parent);
                }
                indjson.put("type", type);
                WidgetUtilities.addProperties(indjson, "image",
                       WidgetUtilities.renderComponent(context, img));
                indicatorArray.put(indjson);
            }
            return indicatorArray;
    } 

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}

