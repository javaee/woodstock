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

import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.component.CalendarMonth;
import com.sun.webui.jsf.component.DateManager;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Renders a <code>CalendarMonth</code> component.
 * The calendar month is comprised of the following layout areas:
 * <ul>
 * <li>A header which displays today's date and an icon for closing the popup calnedar.</li>
 * <li>Calendar contorls which includes the year and month menus as well as the previous
 * and next icon hyperlinks.</li>
 * <li>Week days header</li>
 * <li>Table rows for days in the month.</li>
 * </ul>
 * 
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.CalendarMonth",
    componentFamily="com.sun.webui.jsf.CalendarMonth"))
public class CalendarMonthRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {        
        "style"
    };           

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
	if (!(component instanceof CalendarMonth)) {
	    throw new IllegalArgumentException(
                "CalendarMonthRenderer can only render CalendarMonth components.");
        }             
        
        CalendarMonth calendarMonth = (CalendarMonth)component; 
        
        // Get a calendar instance with the correct timezone and locale 
        // from the CalendarMonth component. This calendar is initialized
        // with today's date.
        java.util.Calendar calendar = calendarMonth.getCalendar();               
        Theme theme = getTheme();        
        DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM,
                context.getViewRoot().getLocale());
        df.setTimeZone((TimeZone)(calendarMonth.getTimeZone())); 
        
        // Get today's date.
        Date today = calendar.getTime(); 
        String[] param = {df.format(today)};
        String todayDateMsg = theme.getMessage("CalendarMonth.todayIs", param);                
        
        // Get first day of week, Sunday = 1, Monday = 2,...
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        
        // Initialize children -- must be called after "today" and
        // "firstDayOfWeek" variables are set since "calendar"
        // is modified in initializeChildren().
        initializeChildren(calendarMonth, context, calendar);
        
        JSONObject json = new JSONObject();           
        json.put("todayDateMsg", todayDateMsg)  
            .put("dateFormat", calendarMonth.getDateFormatPattern()) 
            .put("firstDayOfWeek", firstDayOfWeek);
        
        Icon icon = null;
        icon = getIcon(theme, "DOT", calendarMonth, ThemeImages.DOT);
        json.put("spacerImage", WidgetUtilities.renderComponent(context, icon));
        
        icon = getIcon(theme, "topLeft", calendarMonth, ThemeImages.SCHEDULER_TOP_LEFT);
        json.put("topLeftImage", WidgetUtilities.renderComponent(context, icon));
        
        icon = getIcon(theme, "topRight", calendarMonth, ThemeImages.SCHEDULER_TOP_RIGHT);
        json.put("topRightImage", WidgetUtilities.renderComponent(context, icon));
                
        json.put("closeButtonLink", WidgetUtilities.renderComponent(context, 
            calendarMonth.getCloseButtonLink()));
        
        String[] weekDays = new String[8];
        weekDays[Calendar.SUNDAY] = theme.getMessage("CalendarMonth.weekdaySun");
        weekDays[Calendar.MONDAY] = theme.getMessage("CalendarMonth.weekdayMon");
        weekDays[Calendar.TUESDAY] = theme.getMessage("CalendarMonth.weekdayTue");
        weekDays[Calendar.WEDNESDAY] = theme.getMessage("CalendarMonth.weekdayWed");
        weekDays[Calendar.THURSDAY] = theme.getMessage("CalendarMonth.weekdayThu");
        weekDays[Calendar.FRIDAY] = theme.getMessage("CalendarMonth.weekdayFri");
        weekDays[Calendar.SATURDAY] = theme.getMessage("CalendarMonth.weekdaySat");
               
        JSONArray jsonArray = new JSONArray();
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            jsonArray.put(weekDays[i]);
        }
        json.put("weekDays", jsonArray);            
        
        // Add calendar controls properties.
        setControlsProperties(context, calendarMonth, json, theme);
            
        // Add attributes.
        JSONUtilities.addProperties(attributes, component, json);
        
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
            : getTheme().getPathToTemplate(ThemeTemplates.CALENDARMONTH);
    }

    /**
     * Get the name of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetName(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("calendar");
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Helper function to set calendar controls name/value paris for the given JSONObject.
     * 
     * @param context FacesContext for the current request.
     * @param calendar CalendarMonth to be rendered.
     * @param json JSONObject to add name/value pairs to.
     * @param theme Theme object to use.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    private void setControlsProperties(FacesContext context, CalendarMonth calendarMonth,
            JSONObject json, Theme theme) throws IOException, JSONException {
        
        json.put("decreaseLink", WidgetUtilities.renderComponent(context, 
            calendarMonth.getPreviousMonthLink()));
        
        json.put("increaseLink", WidgetUtilities.renderComponent(context,
            calendarMonth.getNextMonthLink()));
        
        DropDown monthMenu = calendarMonth.getMonthMenu();
        monthMenu.setToolTip(theme.getMessage("CalendarMonth.selectMonth"));
        json.put("monthMenu", WidgetUtilities.renderComponent(context, monthMenu));
        
        DropDown yearMenu = calendarMonth.getYearMenu();
        yearMenu.setToolTip(theme.getMessage("CalendarMonth.selectYear"));
        json.put("yearMenu", WidgetUtilities.renderComponent(context, yearMenu));        
    }      
    
    // Helper method to get themed icon.
    private Icon getIcon(Theme theme, String id, CalendarMonth parent, String key) {                
        Icon icon = ThemeUtilities.getIcon(theme, key);
        icon.setId(id);
        icon.setParent(parent);
        
        return icon;
    }
    
    /**
     * Initialize children (month and year menus).
     * 
     * Note that the "calendar" instance that is passed to this method gets modified.
     * If you are working with the same instance of "calendar" be cautious as to when
     * initializeChildren() should be called.
     */ 
    private void initializeChildren(CalendarMonth cm, FacesContext context, java.util.Calendar calendar) {
        SimpleDateFormat dateFormat =
		(SimpleDateFormat)cm.getDateFormat();
        
        // This variable is used to track whether the calendar 
        // controls have to be updated based on the calculations
        // performed in this method.
        boolean updateCalendarControls = false;                 
        
        // The displayDate reflects the month that will be displayed 
        // (we only use the year and month component of the date).
        // We start by assuming that this will be the today's date
        Date displayDate = calendar.getTime(); 
                     
        // Find out what the current year and month settings are of the 
        // CalendarMonth component (this is whatever the user has 
        // selected using the menus and the buttons on the control row). 
        // Update the calendar with this data - it will be used when 
        // calculating the dates on the display.
        // If the user hasn't made any selections yet, the values 
        // will be updated later, and will be based on today's date. 
        Integer year = cm.getCurrentYear(); 
        Integer month = cm.getCurrentMonth();         
        if(year != null && month != null) {            
            calendar.set(Calendar.YEAR, year.intValue());
            // Adjust for the fact that we display the months as 1 - 12,
            // but java.util.Calendar has them as 0 to 11.  
            calendar.set(Calendar.MONTH, month.intValue() -1);            
        }
        else { 
            updateCalendarControls = true; 
        } 
        
        // Calculate which years should be displayed, based on the 
        // settings of the of the CalendarMonth component.
        // We should probably store these options as an attribute, 
        // instead of calculating them every time. 
        
        // Calculate min and max dates
        Date minDate = null; 
        Date maxDate = null;   
          
        UIComponent parent = cm.getParent(); 
        if(parent instanceof DateManager) {
            minDate = ((DateManager)parent).getFirstAvailableDate();
            maxDate = ((DateManager)parent).getLastAvailableDate();
        }   
               
        if(displayDate.before(minDate)) {            
            displayDate = minDate;
            updateCalendarControls = true;
        }
        if(maxDate.before(displayDate)) {            
            displayDate = maxDate;
            updateCalendarControls = true;
        }
        
        DropDown yearMenu = cm.getYearMenu();
        DropDown monthMenu = cm.getMonthMenu();
        
        if(updateCalendarControls) {
            calendar.setTime(displayDate);           
            String yearValue = String.valueOf(calendar.get(Calendar.YEAR));
            yearMenu.setSubmittedValue(new String[]{yearValue});            
            
            String monthValue = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            monthMenu.setSubmittedValue(new String[]{monthValue});            
        }
       
        // Calculate the years to show on the menu.
        calendar.setTime(minDate);
        int firstYear = calendar.get(Calendar.YEAR);  
        calendar.setTime(maxDate); 
        int lastYear = calendar.get(Calendar.YEAR);  
        
        int numYears = lastYear - firstYear + 1;
        Integer yearInteger = null;
        Option[] yearOptions = new Option[numYears];
        for(int i=0; i < numYears; ++i) {
            yearInteger = new Integer(firstYear + i);
            yearOptions[i] = new Option(yearInteger, yearInteger.toString());
        }
        yearMenu.setItems(yearOptions);  
        
        // Set the items of the month component
        // construct an option[] for the locale specific months       
        String[] monthNames = dateFormat.getDateFormatSymbols().getMonths();
        Option[] months = new Option[12];
   
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        int monthInt;
        for (int i = 0; i < 12; i++) {
            monthInt = calendar.get(Calendar.MONTH);
            months[i] = new Option(new Integer(monthInt+1), monthNames[i]);
            calendar.add(Calendar.MONTH, 1);
        }        
        monthMenu.setItems(months);                
    }
}
