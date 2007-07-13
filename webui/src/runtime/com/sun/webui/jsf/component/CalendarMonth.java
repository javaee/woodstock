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
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.ScheduledEvent;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.beans.Beans;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.NamingContainer;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * <h3>NOT FOR DEVELOPER USE - base renderer class for ui:calendar and ui:scheduler</h3>
 * <p>Do not release as API.</p>
 */
@Component(type="com.sun.webui.jsf.CalendarMonth", 
    family="com.sun.webui.jsf.CalendarMonth", displayName="Calendar Month", 
    isTag=false,
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_calendar_month",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_calendar_month_props")
public class CalendarMonth extends UIOutput implements NamingContainer {
    public static final String MONTH_MENU_ID = "monthMenu"; //NOI18N
    public static final String YEAR_MENU_ID = "yearMenu"; //NOI18N
    public static final String PREVIOUS_MONTH_LINK_ID =
        "previousMonthLink"; //NOI18N
    public static final String NEXT_MONTH_LINK_ID = "nextMonthLink";//NOI18N
    public static final String DATE_LINK_ID = "dateLink";//NOI18N
    public static final String DATE_FIELD_ID = "dateField";//NOI18N
    public static final String DATE_FORMAT_ATTR = "dateFormatAttr"; //NOI18N
    public static final String DATE_FORMAT_PATTERN_ATTR =
        "dateFormatPatternAttr"; //NOI18N
    private static final String TIME_ZONE_ATTR = "timeZoneAttr"; //NOI18N
    private static final String CLOSE_BUTTON_LINK_ID = "closeButtonLink"; //NOI18N
        
    /**
     * <p>The java.util.Calendar object to use for this CalendarMonth component.</p>
     */
    private java.util.Calendar calendar = null;
    
    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public CalendarMonth() {
        super();
        setRendererType("com.sun.webui.jsf.widget.CalendarMonth");        
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.CalendarMonth";
    }
  
    public boolean isDateSelected(java.util.Calendar current, 
                                  java.util.Calendar endDate) {
        
        if(DEBUG) log("isDateSelected()"); //NOI18N
        
        Object value = getValue(); 
        if(value == null) { 
            if(DEBUG) log("Value is null"); //NOI18N
            return false; 
        }
        else if(value instanceof Date) {
            if(DEBUG) log("Value is date"); //NOI18N
            Calendar calendar = getCalendar();
            calendar.setTime((Date)value);
            return compareDate(calendar, current);
        }
        else if(value instanceof ScheduledEvent) {        
            if(DEBUG) log("Value is ScheduledEvent");//NOI18N
            if(DEBUG) log("Checking dates before " + endDate.getTime().toString()); //NOI18N
            Iterator dates = ((ScheduledEvent)value).getDates(endDate);
            Calendar calendar = null;
            
            while(dates.hasNext()) {
                calendar = (Calendar)(dates.next());
                if(compareDate(calendar, current)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    public boolean compareDate(java.util.Calendar selected, 
                                java.util.Calendar current) { 
       
        if(DEBUG) log("Rendered data is " + current.getTime().toString()); //NOI18N
        if(DEBUG) log("Compare to " + selected.getTime().toString()); //NOI18N
        if(selected.get(Calendar.YEAR) == current.get(Calendar.YEAR) &&
           selected.get(Calendar.MONTH) == current.get(Calendar.MONTH) &&
           selected.get(Calendar.DAY_OF_MONTH) == current.get(Calendar.DAY_OF_MONTH)) {
            if(DEBUG) log("Found match");//NOI18N
            return true;
        } 
        return false;
    } 
    
    /**
     * <p>Convenience function to return the locale of the current context.</p>
     */
    protected Locale getLocale() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getViewRoot().getLocale();
    }
    
    /**
     * <p>Returns a new Calendar instance.</p>
     *
     * @return java.util.Calendar A new Calendar instance with the correct
     * locale and time zone.
     */
    public java.util.Calendar getCalendar() {

        if(DEBUG) log("getCalendar()"); //NOI18N
        if(calendar == null) { 
            if(DEBUG) log("...Initializing...."); //NOI18N
            initializeCalendar(); 
        }
        return (java.util.Calendar)(calendar.clone());
    }
    
    /**
     * <p>Returns a new Calendar instance.</p>
     *
     * @return java.util.Calendar A new Calendar instance with the correct
     * locale and time zone.
     */
    private void initializeCalendar() {

        if(DEBUG) log("initializeCalendar()"); //NOI18N
        Locale locale = getLocale();
        TimeZone tz = getTimeZone();
        if (tz == null) {
            calendar = java.util.Calendar.getInstance(locale);
        } else {
            calendar = java.util.Calendar.getInstance(tz, locale);
        }
        if(DEBUG) log("Initial date is " + calendar.getTime().toString()); //NOI18N
        
        if(DEBUG) log("initializeCalendar() - END"); //NOI18N
    }

    /** <p>Return the DateFormat object for this CalendarMonth.</p> */
    public DateFormat getDateFormat() {
        
        if(DEBUG) log("getDateFormat()");  //NOI18N
        
        Object o = getAttributes().get(DATE_FORMAT_ATTR);
        DateFormat dateFormat = null; 
        if(o != null && o instanceof DateFormat) {
            if(DEBUG) log("DateFormat was already set");  //NOI18N
            dateFormat = (DateFormat)o; 
            return dateFormat;
        }

        // Derive it

        if(DEBUG) log("Dateformat not calculated"); //NOI18N

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)
            SimpleDateFormat.getDateInstance(DateFormat.SHORT, getLocale());

        // We need to set the locale and the timeZone of the dateFormat. 
        // I can't tell from the spec whether just setting the Calendar 
        // does this correctly (the Calendar does know both). 
        
        simpleDateFormat.setCalendar(getCalendar());
        ((SimpleDateFormat)simpleDateFormat).applyPattern(
                getDateFormatPattern(simpleDateFormat));
        
        // For creator don't store the value, always derive it.
        // It's not clear if storing the value prevents responding
        // to a dymnamic locale change.
        //
        if (!Beans.isDesignTime()) {
            getAttributes().put(DATE_FORMAT_ATTR, simpleDateFormat);
        }

        return simpleDateFormat;
    }
    
     /**
      * <p>Return the TimeZone object for this CalendarMonth.</p>
      * If the parent is a DateManager, return its TimeZone
      * property, else return the time zone from a java.util.Calendar
      * based on the locale.
      */
    @Property(name="timeZone", category="Advanced")
    public TimeZone getTimeZone() {
        
        if(DEBUG) log("getTimeZone()");  //NOI18N
        
        Object tzo = getAttributes().get(TIME_ZONE_ATTR);
        if(tzo != null && tzo instanceof TimeZone) {
            return (TimeZone)tzo;
        } 

        UIComponent parent = getParent();
        TimeZone tz = null;
        try {
            tz = ((DateManager)parent).getTimeZone();
            if(DEBUG) log("Parent is date manager"); //NOI18N
        } catch (Exception e) {
            if(DEBUG) log("Parent is not date manager or is null"); //NOI18N
        }
        if (tz == null) {
            tz = java.util.Calendar.getInstance(getLocale()).getTimeZone();
        }
        
        // For creator don't store this.
        //
        if (!Beans.isDesignTime()) {
            getAttributes().put(TIME_ZONE_ATTR, tz);
        }
        return tz == null ? TimeZone.getDefault() : tz;
    }

    /**
     * Clear the cached timezone.
     */
    public void setTimeZone(TimeZone tz) {
        getAttributes().remove(TIME_ZONE_ATTR);
        // Make sure the calendar gets re-initialized.
        //
        this.calendar = null;
    }

    /**
     * <p>Get the DropDown menu instance to use for this CalendarMonths's year
     * menu.</p>
     * 
     * @return The DropDown instance to use for the year menu
     */
    public DropDown getMonthMenu() {    
         
        if(DEBUG) log("getMonthMenu()");//NOI18N
         
        UIComponent comp = getFacet(MONTH_MENU_ID);
        DropDown monthMenu = null; 
        
        if(comp == null || !(comp instanceof DropDown)) {
            
            monthMenu = new DropDown();
            monthMenu.setSubmitForm(true);
            
            monthMenu.setConverter(new IntegerConverter());
            monthMenu.setId(MONTH_MENU_ID);
            
            // The year menu is controlled by JavaScript when
            // this component is shown in popup mode. When used
            // in the Scheduler, we need to do the following
            // to control the behaviour on submit
            if(!isPopup()) {
                monthMenu.setImmediate(true);
                //yearMenu.addValueChangeListener(new MonthListener());
            }
            
            // add the year menu to the facet list
            getFacets().put(MONTH_MENU_ID, monthMenu);
        } else {
            monthMenu = (DropDown)comp;
        }
        
        if(DEBUG) log("getMonthMenu() - END");//NOI18N
        return monthMenu;
    }
    
        /**
     * <p>Get the JumpDropDown menu instance to use for thie
     * CalendarMonth's year menu.</p>
     *
     * @return The JumpDropDown instance to use for the year menu
     */
    public DropDown getYearMenu() {
         
        if(DEBUG) log("getYearMenu()");//NOI18N
         
        DropDown yearMenu = (DropDown) getFacets().get(YEAR_MENU_ID);
        
        if(yearMenu == null) {
            yearMenu = new DropDown();
            yearMenu.setSubmitForm(true);
            yearMenu.setId(YEAR_MENU_ID);
            yearMenu.setConverter(new IntegerConverter());          
    
            // The year menu is controlled by JavaScript when
            // this component is shown in popup mode. When used
            // in the Scheduler, we need to do the following 
            // to control the behaviour on submit
            if(!isPopup()) {
                yearMenu.setImmediate(true);
                //yearMenu.addValueChangeListener(new YearListener());
            }
            
            // add the year menu to the facet list            
            getFacets().put(YEAR_MENU_ID, yearMenu);
        }
        
        return yearMenu;
    }


    /**
     * <p>Get the IconHyperlink instance to use for the previous year
     * link.</p> 
     * @return The IconHyperlink instance to use for the previous year link
     */
    public IconHyperlink getPreviousMonthLink() {
        IconHyperlink link = (IconHyperlink)
            getFacets().get(PREVIOUS_MONTH_LINK_ID);
        
        if (link == null) {
            link = new IconHyperlink();
            link.setId(PREVIOUS_MONTH_LINK_ID);
            link.setIcon(ThemeImages.SCHEDULER_BACKWARD);
            link.setBorder(0);
            link.setToolTip(getTheme().getMessage("CalendarMonth.goBack"));
            
            // The link is controlled by JavaScript when
            // this component is shown in popup mode. When used
            // in the Scheduler, we need to do the following
            // to control the behaviour on submit
            if(!isPopup()) {
                link.setImmediate(true);         
                link.addActionListener(new PreviousMonthListener());
            }

            getFacets().put(PREVIOUS_MONTH_LINK_ID, link);
        }
        
        return (IconHyperlink) link;
    }

    
    /**
     * <p>Get the IconHyperlink instance to use for the next year
     * link.</p> 
     * 
     * @return The IconHyperlink instance to use for the next year link
     */
    public IconHyperlink getNextMonthLink() {
        IconHyperlink link = (IconHyperlink)
            getFacets().get(NEXT_MONTH_LINK_ID);
        
        if (link == null) {
            link = new IconHyperlink();
            link.setId(NEXT_MONTH_LINK_ID);
            
            link.setIcon(ThemeImages.SCHEDULER_FORWARD);
            link.setBorder(0);
            link.setToolTip(getTheme().getMessage("CalendarMonth.goForward"));
            
            // The link is controlled by JavaScript when
            // this component is shown in popup mode. When used
            // in the Scheduler, we need to do the following
            // to control the behaviour on submit
            if(!isPopup()) {
                link.addActionListener(new NextMonthListener());
                link.setImmediate(true);
            }

            getFacets().put(NEXT_MONTH_LINK_ID, link);
        }
        
        return link;
    }
    
    /**
     * This method returns the ImageHyperlink that serves as the
     * button to hide the calendar date picker display.
     * 
     * @return The ImageHyperlink to hide the calendar date picker.
     */
    public ImageHyperlink getCloseButtonLink() {        
        ImageHyperlink link = new ImageHyperlink();
        link.setId(CLOSE_BUTTON_LINK_ID);
        link.setParent(this);
        link.setIcon(ThemeImages.CALENDAR_CLOSE_BUTTON);
        link.setToolTip(getTheme().getMessage("CalendarMonth.close"));
        link.setStyleClass(getTheme().getStyleClass(ThemeStyles.CALENDAR_CLOSE_BUTTON));                                
        return link;
    }
            
    /** <p>Convience function to get the current Theme.</p> */
    protected Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    public void initCalendarControls(String jsName) {
        
        if(DEBUG) log("initCalendarControls()"); //NOI18N
        
        ImageHyperlink link = getPreviousMonthLink();
        link.setIcon(ThemeImages.CALENDAR_BACKWARD);

        link = getNextMonthLink();
        link.setIcon(ThemeImages.CALENDAR_FORWARD);
    }

    public void showNextMonth() {
        
        if(DEBUG) log("showNextMonth"); //NOI18N
        Integer month = getCurrentMonth();
        if(DEBUG) log("Current month is " + month.toString()); //NOI18N
        DropDown monthMenu = getMonthMenu();
        
        if(month.intValue() < 12) {
            if(DEBUG) log("Month is not December"); //NOI18N
            int newMonth = month.intValue() + 1;     
            monthMenu.setSubmittedValue(new String[]{ String.valueOf(newMonth)});
        } else if(showNextYear()) {
            if(DEBUG) log("Month is December"); //NOI18N
            monthMenu.setSubmittedValue(new String[]{"1"});//NOI18N
        }
        // otherwise we do nothing
    }
    
    public void showPreviousMonth() {
       
        Integer month = getCurrentMonth();
        DropDown monthMenu = getMonthMenu();
        
        if(month.intValue() > 1) {
            int newMonth = month.intValue()-1; 
            monthMenu.setSubmittedValue(new String[]{String.valueOf(newMonth)});
        } 
        else if(showPreviousYear()) {
            monthMenu.setSubmittedValue(new String[]{"12"});//NOI18N
        }
        // otherwise we do nothing
    }
   
    private boolean showNextYear() {
        
        if(DEBUG) log("showNextYear"); //NOI18N
        
        DropDown yearMenu = getYearMenu();
        int year = getCurrentYear().intValue();
        year++;
        Option[] options = yearMenu.getOptions();
        Integer lastYear = (Integer)(options[options.length -1].getValue());
        if(lastYear.intValue() >= year) {
            yearMenu.setSubmittedValue(new String[]{String.valueOf(year)});
            return true;
        }
        return false;
    }
    
    private boolean showPreviousYear() {
        
        DropDown yearMenu = getYearMenu();
        int year = getCurrentYear().intValue();
        year--;
        
        Option[] options = yearMenu.getOptions();
        Integer firstYear = (Integer)(options[0].getValue());
        if(firstYear.intValue() <= year) {
            yearMenu.setSubmittedValue(new String[]{String.valueOf(year)});
            return true;
        }
        return false;
    }

    public Integer getCurrentMonth() {
        DropDown monthMenu = getMonthMenu();
        Object value = monthMenu.getSubmittedValue();
        Integer month = null;
        if(value != null) {
            try {
                String[] vals = (String[])value;
                month = Integer.decode(vals[0]);
            } catch(Exception ex) {
                // do nothing
            }
        } else {
            value = monthMenu.getValue();
            if(value != null && value instanceof Integer) {
                month = ((Integer)value);
            }
        }
        return month;
    }
    
    public Integer getCurrentYear() {
        DropDown yearMenu = getYearMenu();
        Object value = yearMenu.getSubmittedValue();
        Integer year = null;
        if(value != null) {
            try {
                String[] vals = (String[])value;
                year = Integer.decode(vals[0]);
            } 
            catch(NumberFormatException ex) {
                // do nothing
            }
        } else {
            value = yearMenu.getValue();
            if(value != null && value instanceof Integer) {
                year = ((Integer)value);
            }
        }
        return year;      
     } 
     
    /**
     * Holds value of property javaScriptObject.
     */
    private String javaScriptObjectName = null;

    /**
     * Getter for property javaScriptObject.
     * @return Value of property javaScriptObject.
     * @deprecated
     */
    public String getJavaScriptObjectName() {
        return this.javaScriptObjectName;
    }

    /**
     * Setter for property javaScriptObject.
     * @param javaScriptObject New value of property javaScriptObject.
     * @deprecated
     */
    public void setJavaScriptObjectName(String javaScriptObjectName) {
        this.javaScriptObjectName = javaScriptObjectName;
    }
    
    private void log(String s) { 
        System.out.println(this.getClass().getName() + "::" + s); //NOI18N
    }

    /**
     * Return the locale dependent date format pattern.
     * If the parent DateManager has a non null dateFormatPattern
     * attribute, return that value.<br/>
     * If not, derive the date format pattern from a SimpleDateFormat
     * instance using the "SHORT" pattern. The returned pattern will
     * have the form of "MM/dd/yyyy" where the order of the tokens and the
     * separators will be locale dependent.<br/>
     * Note that the characterts in the pattern are not localized.
     */
    @Property(name="dateFormatPattern", category="Appearance")
    public String getDateFormatPattern() { 
        return getDateFormatPattern(null);
    }

    /**
     * Return the locale dependent date format pattern.
     * If dateFormat is not null, use that DateFormat to obtain
     * the date format pattern.
     * If the parent DateManager has a non null dateFormatPattern
     * attribute, return that value.<br/>
     * If not, derive the date format pattern from the SimpleDateFormat
     * parameter. The returned pattern will
     * have the form of "MM/dd/yyyy" where the order of the tokens and the
     * separators will be locale dependent.<br/>
     * Note that the characterts in the pattern are not localized.
     */
    public String getDateFormatPattern(SimpleDateFormat dateFormat) {
        
        if(DEBUG) log("getDateFormatPattern()"); //NOI18N

        // If dateFormat is null, always derive the dateFormatPattern
        //
        if (dateFormat == null) {
            // It's not clear if storing the derived date format pattern
            // will prevent the date format pattern changing dynamically
            // due to locale changes.
            //
            // For creator, don't store the derived date format pattern,
            // i.e. "Beans.isDesignTime == true"
            //
            Object dfp = getAttributes().get(DATE_FORMAT_PATTERN_ATTR); 
            String pattern = null; 
            if(dfp != null && dfp instanceof String) { 
                return (String)dfp;
            }
            dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(
                    DateFormat.SHORT, getLocale());
        }

        // Derive the date format pattern.

        String pattern = null;
        UIComponent parent = getParent(); 
        if(parent != null && parent instanceof DateManager) {
            pattern = ((DateManager)parent).getDateFormatPattern();
        }
        
        if (pattern == null) {

            pattern = dateFormat.toPattern();

            if(DEBUG) log("Default pattern " + pattern); //NOI18N

            if(pattern.indexOf("yyyy") == -1) {//NOI18N
                pattern = pattern.replaceFirst("yy", "yyyy");//NOI18N
            }
            if(pattern.indexOf("MM") == -1) {//NOI18N
                pattern = pattern.replaceFirst("M", "MM");//NOI18N
            }
            if(pattern.indexOf("dd") == -1) {//NOI18N
                pattern = pattern.replaceFirst("d", "dd");//NOI18N
            }
        }

        dateFormat.applyPattern(pattern);
        pattern = dateFormat.toPattern();

        if (!Beans.isDesignTime()) {
            getAttributes().put(DATE_FORMAT_PATTERN_ATTR, pattern);
        }

        return pattern;
    }

    /**
     * Clear the cached dateFormatPattern.
     */
    public void setDateFormatPattern(String dateFormatPattern) {
        // Clear the cached dateFormatPattern
        // and the DATE_FORMAT_ATTR
        //
        getAttributes().remove(DATE_FORMAT_PATTERN_ATTR);
        getAttributes().remove(DATE_FORMAT_ATTR);
    }
    
    // Cause the month display to move to the current value, not what the 
    // use was looking at last time... 
    public void displayValue() {
         
        if(DEBUG) log("displayValue()"); //NOI18N
        DropDown monthMenu = getMonthMenu(); 
        DropDown yearMenu = getYearMenu(); 
        Object value = getValue(); 
        if(value == null) { 
           if(DEBUG) log("Value is null"); //NOI18N
           monthMenu.setValue(null);
           yearMenu.setValue(null);    
        }
        else if(value instanceof Date) {
            if(DEBUG) log("Value is date"); //NOI18N
            Calendar calendar = getCalendar();
            calendar.setTime((Date)value);
            int newMonth = calendar.get(Calendar.MONTH) + 1; 
            if(DEBUG) log("new month value " + String.valueOf(newMonth));          //NOI18N
            monthMenu.setValue(new Integer(newMonth)); 
            
            int newYear = calendar.get(Calendar.YEAR);
            if(DEBUG) log("new year value " + String.valueOf(newYear));//NOI18N
            yearMenu.setValue(new Integer(newYear)); 
        }
       else if(value instanceof ScheduledEvent) {
            if(DEBUG) log("Value is ScheduledEvent");//NOI18N
            Date date = ((ScheduledEvent)value).getStartTime();
            if(date != null) {
                Calendar calendar = getCalendar();
                calendar.setTime(date);
                int newMonth = calendar.get(Calendar.MONTH) + 1;
                if(DEBUG) log("new month value " + String.valueOf(newMonth));//NOI18N
                monthMenu.setValue(new Integer(newMonth));
                
                int newYear = calendar.get(Calendar.YEAR);
                if(DEBUG) log("new year value " + String.valueOf(newYear));//NOI18N
                yearMenu.setValue(new Integer(newYear));
            } else {
                if(DEBUG) log("Value is null");//NOI18N
                monthMenu.setValue(null);
                yearMenu.setValue(null);
            }           
       }
        monthMenu.setSubmittedValue(null);   
        yearMenu.setSubmittedValue(null);          
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    /**
     * <p>Flag determining whether the component should be rendered in its
     * popup version (as used by Calendar), or in the
     * inline version used by Scheduler.</p>
     */
    @Property(name="popup", displayName="Popup Version", category="Behavior")
    private boolean popup = false;
    private boolean popup_set = false;

    /**
     * <p>Flag determining whether the component should be rendered in its
     * popup version (as used by Calendar), or in the
     * inline version used by Scheduler.</p>
     */
    public boolean isPopup() {
        if (this.popup_set) {
            return this.popup;
        }
        ValueExpression _vb = getValueExpression("popup");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>Flag determining whether the component should be rendered in its
     * popup version (as used by Calendar), or in the
     * inline version used by Scheduler.</p>
     * @see #isPopup()
     */
    public void setPopup(boolean popup) {
        this.popup = popup;
        this.popup_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.popup = ((Boolean) _values[1]).booleanValue();
        this.popup_set = ((Boolean) _values[2]).booleanValue();
        this.htmlTemplate = (String) _values[3];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[4];
        _values[0] = super.saveState(_context);
        _values[1] = this.popup ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.popup_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.htmlTemplate;
        return _values;
    }
}

class PreviousMonthListener implements ActionListener, Serializable {
    
    public void processAction(ActionEvent event) {
        
        FacesContext.getCurrentInstance().renderResponse();
        UIComponent comp = event.getComponent();
        comp = comp.getParent();
        if(comp instanceof CalendarMonth) {
            ((CalendarMonth)comp).showPreviousMonth(); 
        }
    }   
}

class NextMonthListener implements ActionListener, Serializable {
    
    public void processAction(ActionEvent event) {
        
        FacesContext.getCurrentInstance().renderResponse();
        UIComponent comp = event.getComponent();
        comp = comp.getParent();
        if(comp instanceof CalendarMonth) {
            ((CalendarMonth)comp).showNextMonth();
        }
    }   
}

/*
class MonthListener implements ValueChangeListener, Serializable {
    
    public void processValueChange(ValueChangeEvent event) {
           
        FacesContext.getCurrentInstance().renderResponse();
        Object value = event.getNewValue();
        System.out.println(value.toString());
        if(value != null && value instanceof Integer) {
            int newvalue = ((Integer)value).intValue() -1;
            UIComponent comp = event.getComponent();
            comp = comp.getParent();
            if(comp instanceof CalendarMonth) {
                ((CalendarMonth)comp).setDisplayDateField(Calendar.MONTH, newvalue);
            }
        }
    }
} 

class YearListener implements ValueChangeListener, Serializable {
    
    public void processValueChange(ValueChangeEvent event) {
        
        FacesContext.getCurrentInstance().renderResponse();
        Object value = event.getNewValue();
        System.out.println(value.toString());
        if(value != null && value instanceof Integer) {
            int newvalue = ((Integer)value).intValue();
            UIComponent comp = event.getComponent();
            comp = comp.getParent();
            if(comp instanceof CalendarMonth) {
                ((CalendarMonth)comp).setDisplayDateField(Calendar.YEAR, newvalue);
            }
        }
    }   
}

 */
