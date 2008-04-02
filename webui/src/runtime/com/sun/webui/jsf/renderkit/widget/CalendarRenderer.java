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

import com.sun.webui.jsf.component.Calendar;
import com.sun.webui.jsf.component.CalendarMonth;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.util.ConversionUtilities;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The <code>Calendar</code> Component is rendered as a TextField and an icon
 * button to its right. When the user clicks on the button a Calendar is displayed
 * allowing the user to select a single date. See <code>CalendarMonth</code>
 * for details on the actual calendar. When the date is selected it appears
 * as the value of the text field.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Calendar",
    componentFamily="com.sun.webui.jsf.Calendar"))
public class CalendarRenderer extends TextFieldRenderer {
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
	if (!(component instanceof Calendar)) {
	    throw new IllegalArgumentException(
                "CalendarRenderer can only render Calendar components.");
        }             
        
        Calendar calendar = (Calendar) component;
        Theme theme = getTheme();
                                
        JSONObject json = super.getProperties(context, component);
        
        // Get date format pattern help.
        String patternHelp = calendar.getDateFormatPatternHelp();        
        
        if(patternHelp != null) {
            json.put("patternHelp", patternHelp);            
        }                             
                    
        // Append date picker properties.        
        CalendarMonth calendarMonth = calendar.getDatePicker();
        Object value = calendar.getSubmittedValue();
        if(value != null) {
            try {
                Object dO = ConversionUtilities.convertValueToObject(calendar,
                        (String)value, context);
                calendarMonth.setValue(dO);
            } catch(Exception ex) {
                // do nothing
            }
        } else if(calendar.getValue() != null) {
            calendarMonth.setValue(calendar.getValue());
        }                  
        calendarMonth.initCalendarControls(calendar.getJavaScriptObjectName(context));
        
        JSONObject jsonCal = (JSONObject) WidgetUtilities.renderComponent(
            context, calendarMonth);                
        json.put("calendar", jsonCal);
        
        // Search for the private facet DATE_PICKER_LINK_FACET.
        // If it exists, then use the facet.
        // If facet does not exist, create an ImageHyperlnk and use that.
        // The call to getDatePickerLink has been removed and the creation
        // of an imageHyperlnk if one did not exist in the facet map happens
        // here. The imageHyperlnk created here is not put into the facet
        // map.
        UIComponent comp = calendar.getFacet(Calendar.DATE_PICKER_LINK_FACET); 
        if (comp instanceof ImageHyperlink) {
            jsonCal.put("toggleLink", WidgetUtilities.renderComponent(context, comp));
        }
        
        return json;
    }    
    
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "calendarField";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
