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
import com.sun.webui.jsf.component.Alarm;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.model.Indicator;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeTemplates;
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
 * This class renders Alarm component.
 * Alarm component uses a set of Indicator objects that allows to
 * define the custom indicators including default indicators. Also 
 * the sortValue property of Indicator object is used to sort alarms.
 */
@Renderer(@Renderer.Renders(
rendererType="com.sun.webui.jsf.widget.Alarm",
        componentFamily="com.sun.webui.jsf.Alarm"))
public class AlarmRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "dir",
        "lang",
        "style",
        "onBlur",
        "onClick",
        "onDblClick",
        "onFocus",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception JSONException if a key/value error occurs
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.alarm"));
        
        boolean ajaxify = ((Boolean)
            component.getAttributes().get("ajaxify")).booleanValue();        
        if (ajaxify == true) {
            json.put(JavaScriptUtilities.getModuleName(
                "widget.jsfx.alarm"));
        }
        return json;
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
	if (!(component instanceof Alarm)) {
	    throw new IllegalArgumentException(
                "AlarmRenderer can only render Alarm components.");
        }
        Alarm alarm = (Alarm) component;
        Theme theme = getTheme();
        String  severity = alarm.getSeverity();
        String url = alarm.getUrl();        
        String templatePath = alarm.getHtmlTemplate(); // Get HTML template.
        JSONObject json = new JSONObject();
        json.put("text", alarm.getText() )
            .put("textPosition", alarm.getTextPosition())
            .put("visible", alarm.isVisible())
            .put("type", severity)
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : theme.getPathToTemplate(ThemeTemplates.ALARM))
            .put("className", alarm.getStyleClass());    
                       
        List<Indicator> indicators = (List<Indicator>) alarm.getIndicators();
        
        Iterator<Indicator> iter1 = indicators.iterator();
	
        // Check for the icon
        // If an icon is defined then set the ignoreType to severity
        // and render it separately with icon image. indicatorArray will
        // not have the indicator for the severity for which icon is present.
        String icon = alarm.getIcon();
        String ignoreType = null;
        ImageComponent iconImg = null;
        if (icon != null && url == null) {
            ignoreType = severity;
            iconImg = (ImageComponent) ThemeUtilities.getIcon(theme, icon); 
        }
        
        JSONArray indicatorArray = WidgetUtilities.getIndicators(context, 
                iter1, ignoreType, theme, alarm, severity);
                
        JSONObject iconjson = new JSONObject();
        
        if (ignoreType != null) {
            iconjson.put("type", ignoreType);
                WidgetUtilities.addProperties(iconjson, "image",
                       WidgetUtilities.renderComponent(context, iconImg));
                indicatorArray.put(iconjson);
        }
        
        json.put("indicators", indicatorArray);
                       
        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);

        return json;
    }
    
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("alarm");
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }   
    
}
