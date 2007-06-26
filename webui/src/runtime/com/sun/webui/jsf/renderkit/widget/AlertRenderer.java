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
import com.sun.webui.jsf.component.Alert;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.model.Indicator;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Alert component.
 * Alert component uses a set of Indicator objects that allows to
 * define the custom indicators including default indicators. Also 
 * the sortValue property of Indicator object is used to sort indicators.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Alert",
    componentFamily="com.sun.webui.jsf.Alert"))
public class AlertRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "dir",
        "lang",
        "style",
        "tabIndex"   
    };
    
    /**
     * Facet name for alert image
     */
    public static final String ALERT_IMAGE_FACET = "alertImage"; //NOI18N
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo module required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getModule(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getModuleName("widget.alert");
    }
    
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
	if (!(component instanceof Alert)) {
	    throw new IllegalArgumentException(
                "AlertRenderer can only render Alert components.");
        }
        Alert alert = (Alert) component;
        String type = alert.getType();
        Theme theme = getTheme();

        JSONObject json = new JSONObject();
        json.put("summary", RenderingUtilities.formattedMessage(context, alert, 
                alert.getSummary()))
            .put("detail", RenderingUtilities.formattedMessage(context, alert, 
                alert.getDetail()))
            .put("visible", alert.isVisible())
            .put("type", type)
            .put("className", alert.getStyleClass());    
                       
        List<Indicator> indicators = (List<Indicator>) alert.getIndicators();        
        Iterator<Indicator> iter1 = indicators.iterator();

        //Check for the facet
        UIComponent facetImage = alert.getFacet(ALERT_IMAGE_FACET);
        String ignoreType = null;
        if (facetImage != null) {
            ignoreType = type;
        }
       
        JSONArray indicatorArray = WidgetUtilities.getIndicators(context, 
                iter1, ignoreType, theme, alert);
                
        // if ignoreType is not null then add facet image
        JSONObject facetjson = new JSONObject();
        
        if (ignoreType != null) {
            facetjson.put("type", ignoreType);
                JSONUtilities.addProperty(facetjson, "image",
                       WidgetUtilities.renderComponent(context, facetImage));
                indicatorArray.put(facetjson);
        }
        
        json.put("indicators", indicatorArray);
                
        // Append moreInfo image properties.
        // Adding it separately as it is not the part of indicator.
        JSONUtilities.addProperty(json, "moreInfo",
            WidgetUtilities.renderComponent(context, 
                alert.getAlertLink()));
        
        ImageComponent dotImg = null;
        dotImg = (ImageComponent) ThemeUtilities.getIcon(theme, ThemeImages.DOT);

        //set Id for dot image
        if (dotImg.getId() == null) {
            dotImg.setId("DOT");
        }

        //set parent for dot image
        if (dotImg.getParent() == null) {
            dotImg.setParent(alert);
        }
            
        // Append spacerImage image properties.        
        JSONUtilities.addProperty(json, "spacerImage",
            WidgetUtilities.renderComponent(context, dotImg));
        
        // Add attributes.
        JSONUtilities.addAttributes(attributes, component, json);

        return json;
    }
    
    /**
     * Get the template path for this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getTemplatePath(FacesContext context, UIComponent component) {
        String templatePath = (String) component.getAttributes().get("templatePath");
        return (templatePath != null)
            ? templatePath 
            : getTheme().getPathToTemplate(ThemeTemplates.ALERT);
    }

    /**
     * Get the name of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetName(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("alert");
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
