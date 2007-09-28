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

package com.sun.webui.jsf.example.calendar;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionTitle;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Calendar;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;

/**
 * Backing bean for the Calendar example.  The date can be rendered under
 * one of several formats as selected by the user from a dropdown menu.
 * We show how to manage the rendered date in the calendar's output field when
 * the dropdown is immediate and when it is not.
 */
public class CalendarBackingBean implements Serializable {

    public final static String SHOW_CALENDAR = "showCalendar";

    // Holds value of selected date
    private Date date;

    // Holds value of date pattern options.
    private Option[] datePatterns = null;

    // Holds value of calendar's date format pattern
    private String dateFormatPattern;

    // Holds value of calendar's date format pattern help
    private String dateFormatPatternHelp;

    // Localized date format pattern help are key'ed via a common prefix
    // with a numeric suffix.
    private final static String PATTERN_HELP_KEY_PREFIX = "calendar_patternHelp";
    // The date format patterns shown in this example.  The order here
    // MUST match the numerical sequence of the resource keys prefixed
    // by PATTERN_HELP_KEY_PREFIX, with the ordinal position in this array
    // precisely 1 less than the numeric value appended to the key.
    private final static String[] DATE_PATTERNS = {
	"yyyy.MM.dd",
	"MM/dd/yyyy",
	"dd-MM-yyyy",
    };

    // Outcome string for dropdown menu's action handler.  The outcome is
    // dependent upon whether the dropdown menu holding the date pattern
    // selections is "immediate" (as specified in calendar.jsp).
    private String datePatternActionOutcome = null;

    // Holds value of property alertRendered.
    private boolean alertRendered = false;

    // Holds value of property alertSummary.
    private String alertSummary = null;


    /**
     * Constructor
     */
    public CalendarBackingBean() {
	_reset();
    } // constructor

    /** Get the calendar's selected date */
    public Date getDate() {
	return date;
    }

    /** Set the calendar's selected date */
    public void setDate(Date date) {
	this.date = date;
    }

    /** Get the dropdown menu's list of date format patterns */
    public Option[] getDatePatterns() {
	return datePatterns;
    }

    /** Get the calendar's date format pattern */
    public String getDateFormatPattern() {
	return dateFormatPattern;
    }

    /** Set the calendar's date format pattern */
    public void setDateFormatPattern(String dateFormatPattern) {
	this.dateFormatPattern = dateFormatPattern;
    }

    /** Get the calendar's date format pattern help */
    public String getDateFormatPatternHelp() {
	return dateFormatPatternHelp;
    }

    /** Set the calendar's date format pattern help */
    public void setDateFormatPatternHelp(String dateFormatPatternHelp) {
	this.dateFormatPatternHelp = dateFormatPatternHelp;
    }

    /** Get the value of property alertRendered. */
    public boolean getAlertRendered() {
	return alertRendered;
    }

    /** Get the value of property alertSummary. */
    public String getAlertSummary() {
	return alertSummary;
    }

    /** Return the result for the Calendar. */
    public String getCalendarResult() {                      
        Object[] args = new Object[2];
        args[0] = MessageUtil.getMessage("calendar_label");
	String value = "";
	Date date = getDate();
	if (date != null) {
	    SimpleDateFormat dateFmt = new SimpleDateFormat("EE MMM dd, yyyy");
	    value = dateFmt.format(date);
	}
        args[1] = value;
        return MessageUtil.getMessage("calendar_result", args);
    }

    /**
     * Date converter.  The default JSF date converter would typically
     * be sufficient.  However we provide our own here so we can enforce
     * an empty value to be invalid, in the case where the user manually
     * clears out the date field.
     */
    public Converter getDateConverter() {
	return new Converter() {

	    public Object getAsObject(FacesContext context,
			UIComponent component,
			String newValue) throws ConverterException {

		// Convert the submitted value from String to Date
		// using the dateFormatPattern in effect.
		SimpleDateFormat dateFmt = 
		    new SimpleDateFormat(dateFormatPattern);
		Date theDate = null;
		try {
		    theDate = dateFmt.parse(newValue);
		} catch (Exception ex) {
            	    String msgString =
	    		MessageUtil.getMessage("calendar_invalidDate");
            	    FacesMessage msg = new FacesMessage(msgString);
            	    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    alertRendered = true;
		    alertSummary = msgString;
            	    throw new ConverterException(msg);
		}
		return theDate;
	    }

	    public String getAsString(FacesContext context,
			UIComponent component,
			Object value) throws ConverterException {

		// Convert the submitted value from Date to String
		// using the dateFormatPattern in effect.
		SimpleDateFormat dateFmt = 
		    new SimpleDateFormat(dateFormatPattern);
		String theDate = null;
		try {
		    theDate = dateFmt.format((Date)value);
		} catch (Exception ex) {
            	    String msgString =
	    		MessageUtil.getMessage("calendar_invalidDate");
            	    FacesMessage msg = new FacesMessage(msgString);
            	    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    alertRendered = true;
		    alertSummary = msgString;
            	    throw new ConverterException(msg);
		}
		return theDate;
	    }
	};
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Action handler when navigating to the calendar example */
    public String showCalendar() {
	alertRendered = false;
	alertSummary = null;
	return SHOW_CALENDAR;
    }
         
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
	_reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    /** Action listener handler for the reset button. */
    public void resetActionListener(ActionEvent e) {
        // Since the action is immediate, the components won't
        // go through the Update Model phase. However, its submitted value
        // gets set in the Apply Request Value phase and this value is retained
        // when the page is redisplayed. 
        //
        // So, we need to explicitly erase the submitted values and then update
        // the model object with initial values.

	// First get a handle to the calendar object.
        FacesContext context = FacesContext.getCurrentInstance();        
	Calendar calendar = (Calendar) context.getViewRoot().findComponent(
		"form:contentPageTitle:calendar");

	// Murphy's Law!
	if (calendar == null)
	    return;

	calendar.setSubmittedValue(null);

	int n = 0;
	dateFormatPattern = DATE_PATTERNS[n];
	dateFormatPatternHelp = MessageUtil.getMessage(
		PATTERN_HELP_KEY_PREFIX + (n+1));
	calendar.setDateFormatPattern(dateFormatPattern);

	_reset();
    }

    /** Action handler for the Reset button */
    public String reset() {
	return null;
    }

    /** Action handler for the datePattern dropdown menu. */
    public String datePatternAction() {
	// The outcome string is dependent on whether or not the dropdown
	// menu is immediate, and that determination and the specific outcome
	// is made in the actionListener below.
	return datePatternActionOutcome;
    }

    /** Action listener handler for the datePattern dropdown menu. */
    public void datePatternActionListener(ActionEvent e) {

	// Get the index into DATE_PATTERNS corresponding to the pattern
	// selected from the dropdown.
        DropDown dropDown = (DropDown) e.getComponent();
        String selected = (String) dropDown.getSelected(); 
	int n = 0;
	try {
	    // The selected item is resource key with index appended,
	    // so we strip off the leading prefix used for pattern keys.
	    n = Integer.parseInt(selected.replaceAll(PATTERN_HELP_KEY_PREFIX, ""));
	    // Index is relative to the DATE_PATTERNS array
	    n--;
	} catch (Exception ex) {
	}

	if (! dropDown.isImmediate()) {

	    // Update our model for the selected 
	    // date pattern.
	    dateFormatPattern = DATE_PATTERNS[n];
	    dateFormatPatternHelp = MessageUtil.getMessage(
		PATTERN_HELP_KEY_PREFIX + (n+1));

	    // This will result in a "Navigate" to the page and let 
	    // standard EL bindings render the values.
	    datePatternActionOutcome = SHOW_CALENDAR;

	    return;
	}

	// This will cause the page to re-render
	datePatternActionOutcome = null;

	// Since the action is immediate, the component won't
	// go through the Update Model phase. However, its submitted value
	// gets set in the Apply Request Value phase and this value is retained
	// when the page is redisplayed.
	//
	// So, we need to explicitly erase the submitted value and then update
	// it to account for the new format.

	// First get a handle to the calendar object.
        FacesContext context = FacesContext.getCurrentInstance();        
	Calendar calendar = (Calendar) context.getViewRoot().findComponent(
		"form:contentPageTitle:calendar");

	// Murphy's Law!
	if (calendar == null)
	    return;

	// Get the current submitted value as a date object, using
	// the previous date format in effect.
	String submittedValue = (String) calendar.getSubmittedValue();
	Date theDate = null;
	Converter converter = calendar.getConverter();
	try {
	    theDate = (Date) converter.getAsObject(
				context, calendar, submittedValue);
	} catch (Exception ex) {
	    // We could clear out the submitted value here with reason
	    // being that it is better to have nothing than retain an
	    // invalid value.  But instead we choose to retain it and
	    // simply allow the new date format to take effect.
	}

        // Ignore conversion failure when changing date format.
        // We'll catch'em later if they try to submit the value!  ;-)
	alertRendered = false;
	alertSummary = null;

	// Update our model and the calendar to account for the selected 
	// date pattern.
	dateFormatPattern = DATE_PATTERNS[n];
	dateFormatPatternHelp = MessageUtil.getMessage(
		PATTERN_HELP_KEY_PREFIX + (n+1));
	calendar.setDateFormatPattern(dateFormatPattern);

	// If we were successful in converting the previous submitted value
	// to the new format, then set the submitted value to account for
	// the selected date pattern.
	if (theDate != null) {
	    SimpleDateFormat dateFmt = new SimpleDateFormat(dateFormatPattern);
	    submittedValue = dateFmt.format(theDate);
	    calendar.setSubmittedValue(submittedValue);
	}

    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Initial all bean values to their defaults */
    private void _reset() {
	date = null;
	alertRendered = false;
	alertSummary = null;

	// Initialize the list of date format patterns
	datePatterns = new Option[DATE_PATTERNS.length + 1];
	datePatterns[0] = new OptionTitle(
	    MessageUtil.getMessage("calendar_datePatterns"));
	for (int i = 1; i <= DATE_PATTERNS.length; i++) {
	    String key = PATTERN_HELP_KEY_PREFIX + i;
	    datePatterns[i] = new Option(key, MessageUtil.getMessage(key));
	}

	// Initialize the date format pattern and pattern help
	int n = 0;  // index of default pattern
	dateFormatPattern = DATE_PATTERNS[n];
	dateFormatPatternHelp = MessageUtil.getMessage(
		PATTERN_HELP_KEY_PREFIX + (n+1));
    }
}
