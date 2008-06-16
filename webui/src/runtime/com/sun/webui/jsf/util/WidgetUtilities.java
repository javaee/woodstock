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
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.model.Indicator;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionGroup;
import com.sun.webui.jsf.model.Separator;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class provides common methods for widget renderers.
 */
public class WidgetUtilities {
    /**
     * Helper method to return a <code>JSONArray</code> of 
     * <code>Indicators</code>. If <code>ignoreType</code> matches
     * an indicator's type, that indicator is not returned in the array.
     * Each <code>UIComponent</code> returned by
     * <code>Indicator.getImageComponent</code> has its parent set to 
     * <code>parent</code> and its id set to the indicator's type, only
     * component's parent or id are null.
     * 
     * @param context FacesContext for the current request.
     * @param iterator<Indicators> for the indicators.
     * @param ignoreType do not return an indicator if it matches this type.
     * @param theme used to obtain an indicator's image component.
     * @param parent for the indicator image components.
     */
    public static JSONArray getIndicators(FacesContext context, 
	    Iterator<Indicator> indicators, String ignoreType, Theme theme,
	    UIComponent parent) throws IOException, JSONException {
        if (indicators == null) {
            return null;
        }
	
        JSONArray jArray = new JSONArray();      
        while (indicators.hasNext()) {

            Indicator indicator = (Indicator)indicators.next();                 
            String type = (String) indicator.getType();

            // Don't do anything if we don't have to.
            //
            if (type.equals(ignoreType)) {
                    continue;
            }

            JSONObject json = new JSONObject();
	    UIComponent img = indicator.getImageComponent(theme);
            if (img == null) {
		// Why are we doing this ?
		// Since the image may be theme based it is not a good idea
		// to throw exception at runtime. Using "dot" image to handle
		// this situation.
                img = (UIComponent)ThemeUtilities.getIcon(theme, 
			ThemeImages.DOT); 
            }
	    //set the id
	    if (img.getId() == null) {
		img.setId(type);
	    }
	    //set the parent
	    if (img.getParent() == null) {
		img.setParent(parent);
	    }
	    json.put("type", type);
	    json.put("image", WidgetUtilities.renderComponent(context, img));
	    jArray.put(json);
	}
	return jArray;
    }

    /**
     * Helper method to return a <code>JSONArray</code> of <code>Options</code>.
     * This method traverses through the given array and creates JSON objects 
     * for each option present in the options array. Additionally, a "group" 
     * property is added indicating that particular JSON object is contains a
     * group of options.
     * 
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * @param options An array of Option objects.
     */
    public static JSONArray getOptions(FacesContext context, 
            UIComponent component, Option[] options) throws IOException, JSONException {
        if (options == null) {
            return null;
        }

        String tmp = null;
        boolean separatorCreated = false;
        int imgProps = -1;
        JSONObject optionElement = null;
        JSONArray json = new JSONArray();
        
        for (int i = 0; i < options.length; i++) {
            optionElement = new JSONObject();
            json.put(optionElement);

            // Special handling required for objects that are instanceof
            // separator.
            if (options[i] instanceof Separator) {
                JSONObject separatorObject = getSeparatorProperties(component);                
                if (separatorObject != null) {
                    json.put(separatorObject);                    
                }                                                
                continue;
            }
            
            optionElement.put("value", ConversionUtilities.
                    convertValueToString(component, options[i].getValue()))
                .put("label", (String)options[i].getLabel())
                .put("escape", options[i].isEscape())
                .put("disabled", options[i].isDisabled());
                         
            tmp = options[i].getTooltip();
            if (tmp != null) {
                optionElement.put("title", tmp);
            }
            
            // If an image exists to be rendered, then add that to the set
            // of properties.
            JSONObject image = getImageOptionProperties(context, component, options[i]);
            optionElement.put("image", image);

            if (options[i] instanceof OptionGroup) {
                // Call this function again, with the Option elements in the
                // OptionGroup as the parameter 
                JSONArray groupOptions = getOptions(context, component,
                        ((OptionGroup) options[i]).getOptions());
                
                optionElement.put("group", true);
                optionElement.put("options", groupOptions);

                // Lists have a "Separators" attribute which needs to be set to true
                // If this is set, each group needs to have a Separator at the end.
                // If the group happens to be the last group in the option set, then
                // no need to output a Separator for that optionGroup.
                if (component instanceof ListSelector && i < options.length-1 ) {
                    if (((ListSelector) component).isSeparators()) {
                        
                        // New functionality. Just set the separator to true
                        optionElement.put("separator", true);
                        
                        // Need to maintain existing old functionality.
                        // Create a separator object and add it so that the
                        // existing list widget can recognize it.
                         
                        JSONObject separatorObject = getSeparatorProperties(component);                
                        if (separatorObject != null) {
                            json.put(separatorObject);                    
                        }                                                
                    }
                }
            } else {
                optionElement.put("group", false);
                optionElement.put("separator", options[i].getSeparator());
            }
        }        
        return json;
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
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to capture rendered component properties for client-side
     * rendering.
     * 
     * If the component is a widget, the rendered output should be a string 
     * beginning with { and ending with }. In this case, the string shall be 
     * used to create a JSONObject contain properties such as widgetName, 
     * module, etc. Otherwise, the rendered HTML string is assigned to the 
     * JSONObject via a "html" property. In this case, an escape property is
     * also set to false -- HTML should not be escaped.
     *
     * In either case, it is important to use a JavaScript object to distinguish
     * between widgets and static strings so HTML escaping can be
     * applied properly.
     * 
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @returns JSONObject containing component properties or markup.
     */
    public static JSONObject renderComponent(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        JSONObject json = null;
        if (component == null || !component.isRendered()) {
            return json;
        }

        // Initialize Writer to buffer rendered output.
        ResponseWriter oldWriter = context.getResponseWriter();
        Writer strWriter = initStringWriter(context);

        // Render component and restore writer.
        RenderingUtilities.renderComponent(component, context);
        context.setResponseWriter(oldWriter);

        // Get buffered output.
        String s = strWriter.toString();

        try {
            // String beginning with { and ending with }.
            json = new JSONObject(s);
        } catch (JSONException e) {
            json = new JSONObject();
            json.put("html", s);
        }
        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       
    /**
     * Adds the properties for the separator to the passed-in json object
     *
     * @param json The JSONObject for adding the properties
     * @param component The List component
     */
    private static JSONObject getSeparatorProperties(UIComponent component) 
            throws JSONException {
       if(!(component instanceof ListSelector)) {
            return null;
        }
        ListSelector selector = (ListSelector)component;
        JSONObject json = new JSONObject();
        json.put("separator", true) // Indicates this is a separator
            .put("group", false)
            .put("disabled", true); // Always disabled for separator
        
        // The label for the separator. It is a series of dashes (-----------)
        int numEms = selector.getSeparatorLength();
        StringBuffer labelBuffer = new StringBuffer();
        for(int em = 0; em < numEms; ++em) {
            labelBuffer.append("-");                        
        }
        json.put("label", labelBuffer.toString());
        return json;
    }        
    
    /**
     * This function checks if an image exists to be rendered for the  menu.
     * If so, it creates a new JSON object  for the image and adds it to the
     * properties list of the Option element's JSON object. 
     * 
     * @param json The JSON object of the  particular option element
     * @param option The Option element object
     * @param context The FacesContext instance.
     * @param component The menu component.
     */
    private static JSONObject getImageOptionProperties(FacesContext context, 
            UIComponent component, Option option) throws JSONException, IOException {
        String tmp;
        Theme theme = ThemeUtilities.getTheme(context);
        int imgProps;
        if (option.getImage() == null) {
            return null;
        }

        ImageComponent image = new ImageComponent();
        image.setId(component.getId()+ option.getValue() + "_image");
        image.setParent(component);
        image.setUrl(option.getImage());
        image.setAlt(option.getImageAlt());
        imgProps = option.getImageHeight();

        if (imgProps > 0) {
            image.setHeight(imgProps);
        }

        imgProps = option.getImageWidth();
        if (imgProps > 0) {
            image.setWidth(imgProps);
        }
        return WidgetUtilities.renderComponent(context, image);
    }

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
    private static Writer initStringWriter(FacesContext context) {
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
            newWriter = context.getRenderKit().createResponseWriter(
                strWriter, null, extContext.getRequestCharacterEncoding());
        }
        // Set new writer in context.
        context.setResponseWriter(newWriter);
        return strWriter;
    }
}
