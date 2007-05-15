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

import com.sun.webui.jsf.component.Alarm;
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
        String ignoreType, Theme theme, UIComponent parent, String currentType) throws IOException, JSONException {
        
        ImageComponent img = null;
        UIComponent comp = null;
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
            comp = indicator.getImageComponent(theme);
            if (comp != null) {
                
                if (comp instanceof ImageComponent) {
                    img = (ImageComponent) comp;
                }
                
                if (img != null) {
                    String url = null;
                    int height = 0;
                    int width = 0;
                    int border = 0;
                    String alt = null;
                    String align = null;
                    int hspace = 0;
                    int vspace = 0;
                    String toolTip = null;
                    String longDesc = null;
                    Object obj = parent.getAttributes().get("url");
                    if (obj != null) {
                        url = (String) obj;
                    }
                    obj = parent.getAttributes().get("height");
                    if (obj != null) {
                        height = (Integer) obj;
                    }
                    obj = parent.getAttributes().get("width");
                    if (obj != null) {
                        width = (Integer) obj;
                    }
                    obj = parent.getAttributes().get("align");
                    if (obj != null) {
                        align = (String) obj;
                    }
                    obj = parent.getAttributes().get("alt");
                    if (obj != null) {
                        alt = (String) obj;
                    }
                    obj = parent.getAttributes().get("hspace");
                    if (obj != null) {
                        hspace = (Integer) obj;
                    }
                    obj = parent.getAttributes().get("longDesc");
                    if (obj != null) {
                        longDesc = (String) obj;
                    }
                    obj = parent.getAttributes().get("toolTip");
                    if (obj != null) {
                        toolTip = (String) obj;
                    }
                    obj = parent.getAttributes().get("vspace");
                    if (obj != null) {
                        vspace = (Integer) obj;
                    }
                    obj = parent.getAttributes().get("border");
                    if (obj != null) {
                        border = (Integer) obj;
                    }
                    ImageComponent urlImg = null;
                    if (url != null && url.length() > 0) {
                        // create a new image component if url is present.
                        urlImg = new ImageComponent();
                        urlImg.setUrl(url);
                        img = urlImg;
                    }

                    if (height > 0) {
                        img.setHeight(height);
                    }

                    if (width > 0) {
                        img.setWidth(width);
                    }
                    // Set other than height and width attributes for current type only.
                    if (currentType != null && type.equals(currentType)) {
                        if (hspace > 0) {
                            img.setHspace(hspace);
                        }

                        if (vspace > 0) {
                            img.setVspace(vspace);
                        }

                        if (align != null && align.length() > 0) {
                            img.setAlign(align);
                        }

                        if (alt != null && alt.length() > 0) {
                            img.setAlt(alt);
                        }

                        if (longDesc != null && longDesc.length() > 0) {
                            img.setLongDesc(longDesc);
                        }

                        if (toolTip != null && toolTip.length() > 0) {
                            img.setToolTip(toolTip);
                        }

                        if (border >= 0) {
                            img.setBorder(border);
                        }
                    }

                }
                         
            } else {
                    // Since the image may be theme based it is not a good idea
                    // to throw exception at runtime. Using "dot" image to handle
                    // this situation.
                img = (ImageComponent) ThemeUtilities.getIcon(theme, ThemeImages.DOT); 
            }
                
            if (img != null) {
                comp = img;
            }
            
            
            //set the parent
            if (comp.getAttributes().get("parent") == null) {
                comp.getAttributes().put("parent", parent);
            }

            //set the id
            if (comp.getAttributes().get("id") == null) {
                comp.getAttributes().put("id", type);
            }
            
            indjson.put("type", type);
                               
            WidgetUtilities.addProperties(indjson, "image",
                       WidgetUtilities.renderComponent(context, comp));    
               
            indicatorArray.put(indjson);
        }
        return indicatorArray;
    } 

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}

