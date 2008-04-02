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
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    private static final String stringAttributes[] = {
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
        "onMouseMove"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
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

        JSONObject json = new JSONObject();
        json.put("text", alarm.getText() )
            .put("textPosition", alarm.getTextPosition())
            .put("visible", alarm.isVisible())
            .put("type", severity)
            .put("className", alarm.getStyleClass());    
                       
        // Get all the attributes that might need to be changed
	// in an icon or url image or indicator.
	//
	int height = alarm.getHeight();
	int width = alarm.getWidth();
	int border = alarm.getBorder();
	int hspace = alarm.getHspace();
	int vspace = alarm.getVspace();
	String align = alarm.getAlign();
	String alt = alarm.getAlt();
	String longDesc = alarm.getLongDesc();
	String toolTip = alarm.getToolTip();

        // Check for the icon
        // If an icon is defined then set the ignoreType to severity
        // and render it separately with icon image. jArray will
        // not have the indicator for the severity if an icon or
	// url is present.
	//

        String icon = alarm.getIcon();
        String ignoreType = null;
        ImageComponent alarmImage = null;
        if (icon != null && url == null) {
            ignoreType = severity;
            alarmImage = (ImageComponent)ThemeUtilities.getIcon(theme, icon); 
	    initAlarmImage(alarm, severity, null, icon, height, width, hspace,
		vspace, align, alt, longDesc, toolTip, border, alarmImage);
        } else 
	if (url != null && url.length() > 0) {
            ignoreType = severity;
	    alarmImage = new ImageComponent();
	    initAlarmImage(alarm, severity, url, null, height, width, 
		hspace, vspace, align, alt, longDesc, toolTip, border,
		alarmImage);
	}
        JSONObject jsonIcon = new JSONObject();
            if (alarmImage != null) {
                jsonIcon.put("type", severity);
                jsonIcon.put("image", WidgetUtilities.renderComponent(context, 
                    alarmImage));                
            }
	if (alarm.getIndicators() != null) {    
            List<Indicator> indicators = (List<Indicator>) alarm.getIndicators();
            Iterator<Indicator> iterator = indicators.iterator();

            // Use an different method than WidgetUtilities.getIndicators
            // to optimize a little. This method may only be useful for
            // alarms so it is private to this class.
            //
            JSONArray jArray = getAndEditIndicators(context, iterator, ignoreType, 
                theme, alarm, severity, height, width, hspace, vspace, border, 
                toolTip, longDesc, alt, align);
            
            if (alarmImage != null) {                
                jArray.put(jsonIcon);
            }

            json.put("indicators", jArray);
        } else if (alarmImage != null) {
            JSONArray jsonFacetArr = new JSONArray();
            jsonFacetArr.put(jsonIcon);
            json.put("indicators", jsonFacetArr);
        }
                       
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "alarm";
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
    
    /**
     * Helper method to obtain a list of indicators for an Alarm
     * and edit the Indicators based on properties set on the Alarm.
     * If an Indicator's type matches ignoreType, do not return
     * that indicator.<br/>
     * If editTextType matches an Indicator's type then edit that indicator's
     * text properties as well as the dimensional properties.<br/>
     * 
     * @param context FacesContext for the current request.
     * @param iterator<Indicators> for the indicators.
     * @param string ignoreType do not return an Indicator of this type.
     * @param theme for obtaining indicator components.
     * @param parent for the indicator components.
     *
     * @returns JSONArray of Indicators.
     */
    private JSONArray getAndEditIndicators(FacesContext context, 
	    Iterator<Indicator> indicators,  String ignoreType,
	    Theme theme, Alarm parent, String editTextType, 
	    int height, int width, int hspace, int vspace, int border, 
	    String toolTip, String longDesc, String alt, String align) 
            throws IOException, JSONException {

        ImageComponent img = null;
        if (indicators == null) {
            return null;
        }

        JSONArray jArray = new JSONArray();      
        while (indicators.hasNext()) {
            Indicator indicator = (Indicator) indicators.next();                 
            String type = (String) indicator.getType();

            // Don't do anything if we don't have to.
            //
            if (type.equals(ignoreType)) {
                    continue;
            }

	    // Get the image as a UIComponent
	    //
	    UIComponent comp = indicator.getImageComponent(theme);
	    // If getImageKey returns null, then the getImageComponent
	    // was set by the developer and there is no way to safely
	    // edit it. If it is not null, then the ImageComponent will
	    // come newly created from the theme.
	    // Note that this is only safe if the Indicator component
	    // has not been drastically subclassed or the theme
	    // image components cached.
	    //
	    if (indicator.getImageKey() != null && comp != null) {
		// We only edit the text properties if the 
		// Alarm severity is the same as the Indicator type.
		//
		editImage(comp, type.equals(editTextType),
		    height, width, hspace, vspace, border, 
		    toolTip, longDesc, alt, align);
	    }
                         
	    // Why do we need to do this ?
	    //
	    if (comp == null) {
		// Since the image may be theme based it is not a good idea
                // to throw exception at runtime. Using "dot" image to handle
                // this situation.
                comp = (UIComponent)ThemeUtilities.getIcon(theme, 
                    ThemeImages.DOT); 
                continue;
            }

	    // In case comp is a developer defined component we don't
	    // want to overwrite their values
	    //
	    if (comp.getId() == null) {
		comp.setId(type);
	    }
	    if (comp.getParent() == null) {
		comp.setParent(parent);
	    }

            JSONObject jsonIcon = new JSONObject();
	    jsonIcon.put("type", type);
	    jsonIcon.put("image", WidgetUtilities.renderComponent(context,
                comp));
	    jArray.put(jsonIcon);
	}
	return jArray;
    }
    
    /**
     * Helper method to edit the attributes of an Indicator image.
     * If editText is true, edit the text properties as well. Sometimes
     * this is not appropriate.
     */
    private void editImage(UIComponent alarmImage, boolean editText,
	    int height, int width, int hspace, int vspace, int border, 
	    String toolTip, String longDesc, String alt, String align) {

	Map attributes = alarmImage.getAttributes();
	if (height >= 0) {
	    attributes.put("height", height);
	}
	if (width >= 0) {
	    attributes.put("width", width);
	}
	if (hspace >= 0) {
	    attributes.put("hspace", hspace);
	}
	if (vspace >= 0) {
	    attributes.put("vspace", vspace);
	}
	if (border >= 0) {
	    attributes.put("border", border);
	}
	if (align != null && align.length() != 0) {
	    attributes.put("align", align);
	}
	if (editText) {
	    if (toolTip != null && toolTip.length() != 0) {
		attributes.put("toolTip", toolTip);
	    }
	    if (alt != null && alt.length() != 0) {
		attributes.put("alt", alt);
	    }
	    if (longDesc != null && longDesc.length() != 0) {
		attributes.put("longDesc", longDesc);
	    }
	}
    }

    /**
     * Helper to initialize an ImageComponent
     * This should be an ImageComponent constructor.
     */
    private void initAlarmImage(Alarm alarm,
	String id, String url, String icon, int height, int width,
	int hspace, int vspace, String align, String alt, String longDesc,
	String toolTip, int border, ImageComponent alarmImage) {

	if (height >= 0) {
	    alarmImage.setHeight(height);
	}
	if (width >= 0) {
	    alarmImage.setWidth(width);
	}
	if (hspace >= 0) {
	    alarmImage.setHspace(hspace);
	}
	if (vspace >= 0) {
	    alarmImage.setVspace(vspace);
	}
	if (border >= 0) {
	    alarmImage.setBorder(border);
	}
	if (align != null && align.length() != 0) {
	    alarmImage.setAlign(align);
	}
	if (toolTip != null && toolTip.length() != 0) {
	    alarmImage.setToolTip(toolTip);
	}
	if (alt != null && alt.length() != 0) {
	    alarmImage.setAlt(alt);
	}
	if (longDesc != null && longDesc.length() != 0) {
	    alarmImage.setLongDesc(longDesc);
	}
	if (url != null && url.length() != 0) {
	    alarmImage.setUrl(url);
	}
	if (icon != null && icon.length() != 0) {
	    alarmImage.setIcon(icon);
	}
    }
}
