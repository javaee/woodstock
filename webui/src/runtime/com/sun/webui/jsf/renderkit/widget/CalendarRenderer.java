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
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.component.Field;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.MissingResourceException;

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
public class CalendarRenderer extends RendererBase {
    
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "accessKey",
        "align",
        "dir",
        "lang",
        "readOnly",
        "style",        
        "tabIndex"           
    };      
    
    /**
      * Decode the component.
      * 
      * @param context The FacesContext of this request
      * @param component The component associated with the renderer
      */
     public void decode(FacesContext context, UIComponent component) {        
         if (context == null || component == null) {
             throw new NullPointerException();
         }
         if (!(component instanceof Calendar)) {
             return;
         }
         if (!(component instanceof Field)) {
             return;
         }
                  
         // Get the child text field.              
         TextField textField = getTextField((Calendar) component);
         
         Field field = (Field) component;
         // Since the child text field has already been decoded,
         // use its submitted value.
         field.setSubmittedValue(textField.getSubmittedValue());           
     }
    
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
        return JavaScriptUtilities.getModuleName("widget.calendar");
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
	if (!(component instanceof Calendar)) {
	    throw new IllegalArgumentException(
                "CalendarRenderer can only render Calendar components.");
        }             
        
        Calendar calendar = (Calendar) component;
        Theme theme = ThemeUtilities.getTheme(context);
        String templatePath = calendar.getHtmlTemplate(); // Get HTML template.      
        
        // Get date format pattern help.
        String patternHelp = calendar.getDateFormatPatternHelp();
        String dateFormat = calendar.getDatePicker().getDateFormatPattern(); 
        if(patternHelp == null) {
            try {                                  
                patternHelp = theme.getMessage("calendar.".concat(dateFormat)); 
            }
            catch(MissingResourceException mre) { 
                patternHelp = ((SimpleDateFormat)(calendar.getDateFormat())).toLocalizedPattern().toLowerCase();
            }
        }                     
        
        JSONObject json = new JSONObject();        
        json.put("className", calendar.getStyleClass())
            .put("visible", calendar.isVisible())           
            .put("disabled", calendar.isDisabled())
            .put("patternHelp", patternHelp)
            .put("dateFormat", dateFormat)               
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : theme.getPathToTemplate(ThemeTemplates.CALENDAR));
        
        // Append label properties.
        WidgetUtilities.addProperties(json, "label",
            WidgetUtilities.renderComponent(context, calendar.getLabelComponent(context, null)));
     
        // Append text field properties.
        setTextFieldProperties(json, context, calendar);        
        
        // Append image hyperlink properties that serves as 
        // the button to show or hide the calendar date picker.
        ImageHyperlink link = calendar.getDatePickerLink(context);        
        link.setIcon(ThemeImages.CALENDAR_BUTTON);	
        link.setToolTip(theme.getMessage("calendar.popupImageAlt"));
        WidgetUtilities.addProperties(json, "link",
                WidgetUtilities.renderComponent(context, link));
        
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
        
        WidgetUtilities.addProperties(json, "calendarMonth", 
            WidgetUtilities.renderComponent(context, calendarMonth));        
        
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
        return JavaScriptUtilities.getNamespace("calendar");
    }
        
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Helper method to obtain text field's properties.
     * 
     * @param json JSONObject to add name/value pairs to.
     * @param context FacesContext for the current request.
     * @param calendar The Calendar component to be rendered.
     * 
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */ 
    private void setTextFieldProperties(JSONObject json, FacesContext context,
            Calendar calendar) throws IOException, JSONException {
        TextField textField = getTextField(calendar);
        populateTextField(textField, context, calendar);        
        WidgetUtilities.addProperties(json, "field",
            WidgetUtilities.renderComponent(context, textField));
    }
    
    /**
     * Helper method to get the child text field.
     * 
     * @param calendar The parent component.
     * @return Child text field.
     */ 
    private TextField getTextField(Calendar calendar) {
        TextField textField = (TextField)
            ComponentUtilities.getPrivateFacet(calendar, "textField", true);
        if (textField == null) {
            textField = new TextField();
            textField.setId(ComponentUtilities.createPrivateFacetId(calendar, "textField"));            
            ComponentUtilities.putPrivateFacet(calendar, "textField", textField);
            textField.setParent(calendar);
        }
        return textField;
    }    
    
    /*
     * Helper method to set the child text field properties. Get the value of each
     * property from the calendar component and set it on the child text field.
     * 
     * @param textField The child text field component.
     * @param context FacesContext for the current request
     * @param calendar The parent component.
     */ 
    private void populateTextField(TextField textField, FacesContext context,
            Calendar calendar) {        
        textField.setTabIndex(calendar.getTabIndex());
        textField.setColumns(calendar.getColumns());
        textField.setToolTip(calendar.getToolTip());
        textField.setMaxLength(calendar.getMaxLength());
        textField.setDisabled(calendar.isDisabled());        
        textField.setOnMouseDown(calendar.getOnMouseDown());
        textField.setOnMouseOut(calendar.getOnMouseOut());
        textField.setOnMouseMove(calendar.getOnMouseMove());
        textField.setOnMouseOver(calendar.getOnMouseOver());
        textField.setOnMouseUp(calendar.getOnMouseUp());    
        textField.setOnChange(calendar.getOnChange());               
        textField.setOnClick(calendar.getOnClick());
        textField.setOnFocus(calendar.getOnFocus());    
        textField.setOnBlur(calendar.getOnBlur());    
        textField.setOnDblClick(calendar.getOnDblClick());
        textField.setOnKeyDown(calendar.getOnKeyDown());
        textField.setOnKeyPress(calendar.getOnKeyPress());
        textField.setOnKeyUp(calendar.getOnKeyUp());    
        textField.setOnSelect(calendar.getOnSelect());
        textField.setText(calendar.getValueAsString(context));                        
    }
}
