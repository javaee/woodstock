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
import com.sun.webui.jsf.converter.DateConverter; 
import com.sun.webui.jsf.event.IntervalListener;
import com.sun.webui.jsf.event.MethodExprValueChangeListener;
import com.sun.webui.jsf.event.SchedulerPreviewListener;
import com.sun.webui.jsf.model.ClockTime;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.ScheduledEvent;
import com.sun.webui.jsf.model.scheduler.*;
import com.sun.webui.jsf.model.scheduler.RepeatUnitConverter; 
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.validator.DateInRangeValidator; 
import com.sun.webui.jsf.validator.MethodExprValidator;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Iterator;
import java.util.Date;
import java.text.DateFormat;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.Validator;


/**
 * The Scheduler component is used to display a calendar and the input controls
 * that are used for selecting a date and time.
 */
@Component(type="com.sun.webui.jsf.Scheduler", family="com.sun.webui.jsf.Scheduler", displayName="Scheduler", tagName="scheduler",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_scheduler",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_scheduler_props")
public class Scheduler extends WebuiInput
        implements ComplexComponent, DateManager, NamingContainer {
    
    /**
     * The date picker facet name.
     */
    public static final String DATE_PICKER_FACET = "datePicker"; //NOI18N
    
    /**
     * The date facet name.
     */
    public static final String DATE_FACET = "date"; //NOI18N
    /**
     * The date label facet name.
     */
    public static final String DATE_LABEL_FACET = "dateLabel"; //NOI18N

    /**
     * The start date label text.
     */
    public final static String START_DATE_TEXT_KEY =
	"Scheduler.startDate"; //NOI18N
    
    /**
     * The start time facet name.
     */
    public static final String START_TIME_FACET = "startTime"; //NOI18N
    /**
     * The start time facet name.
     */
    public static final String START_TIME_LABEL_FACET =
	"startTimeLabel"; //NOI18N
    /**
     * The start time label text.
     */
    public final static String START_TIME_TEXT_KEY =
	"Scheduler.startTime"; //NOI18N
    
    /**
     * The end time facet name.
     */
    public static final String END_TIME_FACET = "endTime"; //NOI18N
    /**
     * The end time facet name.
     */
    public static final String END_TIME_LABEL_FACET = "endTimeLabel"; //NOI18N
    /**
     * The end time label text.
     */
    public final static String END_TIME_TEXT_KEY = "Scheduler.endTime"; //NOI18N
    
    /**
     * The repeat unit facet name.
     */
    public static final String REPEAT_LIMIT_UNIT_FACET =
	"repeatLimitUnit"; //NOI18N
    /**
     * The repeat unit descriptions text key.
     */
    public final static String REPEAT_UNIT_DESCRIPTION_TEXT_KEY =
		"Scheduler.repeatUnitDesc"; //NOI18N

    /**
     * The repeat limit facet name.
     */
    public static final String REPEAT_LIMIT_FACET = "repeatLimit"; //NOI18N
    /**
     * The repeat limit label facet name.
     */
    public static final String REPEAT_LIMIT_LABEL_FACET =
	"repeatLimitLabel"; //NOI18N
    /**
     * The repeat limit label text.
     */
    public final static String REPEAT_LIMIT_TEXT_KEY =
	"Scheduler.repeatLimit"; //NOI18N
    
    /**
     * The repeat interval facet name.
     */
    public static final String REPEAT_INTERVAL_FACET =
	"repeatInterval"; //NOI18N
    /**
     * The repeat interval label facet name.
     */
    public static final String REPEAT_INTERVAL_LABEL_FACET =
	"repeatIntervalLabel"; //NOI18N
    /**
     * The repeat interval label text.
     */
    public final static String REPEAT_INTERVAL_TEXT_KEY =
	"Scheduler.repeatInterval"; //NOI18N
    /**
     * The repeat interval descriptions text key.
     */
    public final static String REPEAT_INTERVAL_DESCRIPTION_TEXT_KEY =
		"Scheduler.repeatIntervalDesc"; //NOI18N

    /**
     * The preview button facet name.
     */
    public static final String PREVIEW_BUTTON_FACET = "previewButton"; //NOI18N
    /**
     * The preview button text key.
     */
    public static final String PREVIEW_BUTTON_TEXT_KEY =
			"Scheduler.preview"; //NOI18N

    /**
     * The start hour title text key
     */
    public static final String START_HOUR_TITLE_TEXT_KEY =
			"Scheduler.startHourTitle"; //NOI18N
    /**
     * The start minute title text key
     */
    public static final String START_MINUTE_TITLE_TEXT_KEY =
			 "Scheduler.startMinuteTitle"; //NOI18N

    /**
     * The end hour title text key
     */
    public static final String END_HOUR_TITLE_TEXT_KEY =
			"Scheduler.endHourTitle"; //NOI18N
    /**
     * The end minute title text key
     */
    public static final String END_MINUTE_TITLE_TEXT_KEY =
			 "Scheduler.endMinuteTitle"; //NOI18N
    /**
     * The end before start error text key
     */
    private final String END_BEFORE_START_TEXT_KEY =
		    "Scheduler.endBeforeStart"; //NOI18N
    /**
     * The invalid input text key
     */
    private static final String INVALID_INPUT_TEXT_KEY =
                        "Scheduler.invalidInput";  //NOI18N
    /**
     * The invalid date text key
     */
    private static final String INVALID_DATE_TEXT_KEY =
		"Scheduler.invalidDate"; //NOI18N
    /**
     * The invalid start time text key
     */
    private static final String INVALID_START_TIME_TEXT_KEY =
		"Scheduler.invalidStartTime"; //NOI18N
    /**
     * The invalid end time text key
     */
    private static final String INVALID_END_TIME_TEXT_KEY =
		"Scheduler.invalidEndTime"; //NOI18N

    public static final String ICON_ID = "_icon"; //NOI18N
    
    private static final String VALUE_SUBMITTED =
            "com.sun.webui.jsf.SchedulerSubmitted"; //NOI18N
    private static final String FIRST_AVAILABLE_DATE = 
            "com.sun.webui.jsf.FirstAvailable"; //NOI18N
     private static final String LAST_AVAILABLE_DATE = 
            "com.sun.webui.jsf.LastAvailable"; //NOI18N
    
    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public Scheduler() {
        super();
        setRendererType("com.sun.webui.jsf.Scheduler");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Scheduler";
    }

    /**
     * Return a <code>CalendarMonth</code> component that implements
     * the calendar for the Scheduler.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     *
     * @return a CalendarMonth component.
     */
    public CalendarMonth getDatePicker() {
        
        CalendarMonth dp = (CalendarMonth)
	    ComponentUtilities.getPrivateFacet(this, DATE_PICKER_FACET, true);
        if (dp == null) {
            dp = new CalendarMonth();
            // Scheduler should continue using the HTML renderer.
            dp.setRendererType("com.sun.webui.jsf.CalendarMonth");        
            dp.setPopup(false);
            dp.setId(ComponentUtilities.createPrivateFacetId(this,
			DATE_PICKER_FACET));
	    ComponentUtilities.putPrivateFacet(this, DATE_PICKER_FACET, dp);
        }
        
        dp.setJavaScriptObjectName(getJavaScriptObjectName(
		    FacesContext.getCurrentInstance()));

        return dp;
    }
    
    /**
     * Return a component that implements a label for the date component.
     * If a facet named <code>dateLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_dateLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a date label facet component
     */
    // Passing Theme is unlike most all other "getXXComponent" methods
    // defined in  other components with facets
    //
    public UIComponent getDateLabelComponent(Theme theme) {
        
	// This criteria is different than other label facets
	// Other label facets check for "" as well
	// as null and in those cases, uses the default.
	//
        String label = getDateLabel(); 
        if (label == null) { 
          label = theme.getMessage(START_DATE_TEXT_KEY);
        } 

	// Really need to optimize calling "getXXXComponent" methods.
	// Many times these reinitialize the component. Depending
	// on the lifecycle phase, this may or may not be appropriate.
	//
	UIComponent comp = getDateComponent();
        return getLabelFacet(DATE_LABEL_FACET, label, comp);
    }
        
    /**
     * Return a component that implements  a date input field.
     * If a facet named <code>date</code> is found
     * that component is returned. Otherwise a <code>TextField</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_date"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>TextField</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a date input field facet component
     */
    public UIComponent getDateComponent() {
        
        if(DEBUG) log("getDateComponent()"); //NOI18N
        
	// Check if the page author has defined the facet
	//
	UIComponent fieldComponent = getFacet(DATE_FACET); 
	if (fieldComponent != null) {
	    if (DEBUG) { 
		log("\tFound facet"); //NOI18N
	    } 
	    return fieldComponent;
	}

	// Return the private facet or create one, but initialize
	// it every time
	//
	// We know it's a TextField
	//
	TextField field = (TextField)ComponentUtilities.getPrivateFacet(this,
	    DATE_FACET, true);
	if (field == null) {
	    if (DEBUG) log("create Field"); //NOI18N
	    field = new TextField(); 
	    field.setId(ComponentUtilities.createPrivateFacetId(this,
		DATE_FACET));
            ((EditableValueHolder)field).setConverter(new DateConverter());
            ((EditableValueHolder)field).addValidator(
		new DateInRangeValidator());           
	    ComponentUtilities.putPrivateFacet(this, DATE_FACET, field);
	}
	// FIXME: 12 should be part of Theme.
	//
	field.setDisabled(isDisabled());
	initFieldFacet(field, 12, isRequired());

	return field; 
    }  
      
    /**
     * Return a component that implements a label for the start time component.
     * If a facet named <code>startTimeLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_startTimeLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a start time label facet component
     */
    // Passing Theme is unlike most all other "getXXComponent" methods
    // defined in  other components with facets
    //
    public UIComponent getStartTimeLabelComponent(Theme theme) {
        
	// This criteria is different that other label facets
	// Other label facets have checked for "" as well
	// as null and in those cases, uses the default.
	//
        String label = getStartTimeLabel();
        if (label == null) {
            label = theme.getMessage(START_TIME_TEXT_KEY);
        }
	// Really need to optimize calling "getXXXComponent" methods.
	// Many times these reinitialize the component. Depending
	// on the lifecycle phase, this may or may not be appropriate.
	//
	UIComponent comp = getStartTimeComponent();
        return getLabelFacet(START_TIME_LABEL_FACET, label, comp);
    }
    
    /**
     * Return a Time component that implements the start time.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>startTime</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     * Otherwise a <code>Time</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_startTime"</code> and added to the facets map
     * as a private facet.</br>
     *
     * @return a start time Time component.
     */
    public Time getStartTimeComponent() {

	// Note that tooltip keys are passed here.
	// This is unlike many other facets. The keys are
	// resolved in Time.endcodeEnd.
	//
        return getTimeFacet(START_TIME_FACET, isRequired(),
		START_HOUR_TITLE_TEXT_KEY,
		START_MINUTE_TITLE_TEXT_KEY);
    }

    /**
     * Return a component that implements a label for end time component.
     * If a facet named <code>endTimeLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_endTimeLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a end time label facet component
     */
    // Passing Theme is unlike most all other "getXXComponent" methods
    // defined in  other components with facets
    //
    public UIComponent getEndTimeLabelComponent(Theme theme) {
        
	// This criteria is different that other label facets
	// Other label facets have checked for "" as well
	// as null and in those cases, uses the default.
	//
        String label = getEndTimeLabel();
        if (label == null) {
	    label = theme.getMessage(END_TIME_TEXT_KEY);
        }
	// Really need to optimize calling "getXXXComponent" methods.
	// Many times these reinitialize the component. Depending
	// on the lifecycle phase, this may or may not be appropriate.
	//
	UIComponent comp = getEndTimeComponent();
        return getLabelFacet(END_TIME_LABEL_FACET, label, comp);
        
    }
    
    /**
     * Return a Time component that implements the end time.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>endTime</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     * Otherwise a <code>Time</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_endTime"</code> and added to the facets map
     * as a private facet.</br>
     *
     * @return an end time Time component.
     */
    public Time getEndTimeComponent() {
	// Note that tooltip keys are passed here.
	// This is unlike many other facets. The keys are
	// resolved in Time.endcodeEnd.
	//
        return getTimeFacet(END_TIME_FACET, false,
		END_HOUR_TITLE_TEXT_KEY,
		END_MINUTE_TITLE_TEXT_KEY);
    }
   
    /**
     * Return a component that implements a label for repeat interval component.
     * If a facet named <code>repeatIntervalLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_repeatIntervalLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a repeat interval label facet component
     */
    public UIComponent getRepeatIntervalLabelComponent() {

	// This criteria is different that other components with
	// label facets. Other label facets check for "" as well
	// as null and then, uses the default.
	//
        String label = getRepeatIntervalLabel();
        if (label == null) {
	    FacesContext context = FacesContext.getCurrentInstance();
            label = ThemeUtilities.getTheme(context).getMessage(
		REPEAT_INTERVAL_TEXT_KEY);
        }
	// Really need to optimize calling "getXXXComponent" methods.
	// Many times these reinitialize the component. Depending
	// on the lifecycle phase, this may or may not be appropriate.
	//
	UIComponent comp = getRepeatIntervalComponent();
        return getLabelFacet(REPEAT_INTERVAL_LABEL_FACET, label, comp);
    }
    
    /**
     * Return a DropDown component that implements a repeat interval menu.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>repeatInterval</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     * Otherwise a <code>DropDown</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_repeatInterval"</code> and added to the facets map
     * as a private facet.</br>
     *
     * @return a repeat interval DropDown component.
     */
    public DropDown getRepeatIntervalComponent() {
        
        if(DEBUG) log("getRepeatIntervalComponent"); //NOI18N
        
	// Support only a private facet.
	//
        DropDown dropDown = (DropDown)
	    ComponentUtilities.getPrivateFacet(this, REPEAT_INTERVAL_FACET,
		    true);
        if (dropDown == null) {
	    if(DEBUG) log("createDropDown"); //NOI18N
        
	    dropDown = new DropDown();
	    dropDown.setId(ComponentUtilities.createPrivateFacetId(this,
		REPEAT_INTERVAL_FACET));
            dropDown.setSubmitForm(true);
            dropDown.setConverter(new RepeatIntervalConverter());
            dropDown.setImmediate(true);
            dropDown.addActionListener(new IntervalListener());

	    ComponentUtilities.putPrivateFacet(this, REPEAT_INTERVAL_FACET,
		dropDown);
	}

	dropDown.setItems(getRepeatIntervalItems());
	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	dropDown.setToolTip(
		theme.getMessage(REPEAT_INTERVAL_DESCRIPTION_TEXT_KEY));

        return dropDown;
    }
    
    /**
     * Return a component that implements a label for the repeat limit
     * component.
     * If a facet named <code>repeatLimitLabel</code> is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_repeatLimitLabel"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a repeat limit label facet component
     */
    public UIComponent getRepeatLimitLabelComponent() {
        
	// This criteria is different that other label facets
	// Other label facets have checked for "" as well
	// as null and in those cases, uses the default.
	//
        String label = getRepeatLimitLabel();
        if (label == null) {
	    FacesContext context = FacesContext.getCurrentInstance();
            label = ThemeUtilities.getTheme(context).getMessage(
		REPEAT_LIMIT_TEXT_KEY);
        }
	// Really need to optimize calling "getXXXComponent" methods.
	// Many times these reinitialize the component. Depending
	// on the lifecycle phase, this may or may not be appropriate.
	//
	UIComponent comp = getRepeatingFieldComponent();
        return getLabelFacet(REPEAT_LIMIT_LABEL_FACET, label, comp);
    }
    
    /**
     * Return a component that implements  a repeating limit input field.
     * If a facet named <code>repeatLimit</code> is found
     * that component is returned. Otherwise a <code>TextField</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_repeatLimit"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>TextField</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a repeat limit input field facet component
     */
    public UIComponent getRepeatingFieldComponent() {
        
        if(DEBUG) log("getRepeatingFieldComponent()"); //NOI18N
        
	// Check if the page author has defined the facet
	//
	UIComponent fieldComponent = getFacet(REPEAT_LIMIT_FACET); 
	if (fieldComponent != null) {
	    if (DEBUG) { 
		log("\tFound facet"); //NOI18N
	    } 
	    return fieldComponent;
	}

	// Return the private facet or create one, but initialize
	// it every time
	//
	// We know it's a TextField
	//
	TextField field = (TextField)ComponentUtilities.getPrivateFacet(this,
	    REPEAT_LIMIT_FACET, true);
	if (field == null) {
	    if (DEBUG) log("create Field"); //NOI18N
	    field = new TextField(); 
	    field.setId(ComponentUtilities.createPrivateFacetId(this,
		REPEAT_LIMIT_FACET));
            IntegerConverter converter = new IntegerConverter();
            field.setConverter(converter);
            DoubleRangeValidator drv = new DoubleRangeValidator(); 
            drv.setMinimum(1);
            field.addValidator(drv);
	    ComponentUtilities.putPrivateFacet(this, REPEAT_LIMIT_FACET, field);
	}

	// Ideally this facet should be disabled or enabled here
	// based on the state of the Scheduler. Currently it is 
	// disabled/enabled in disableRepeatLimitComponents
	// and enableRepeatLimitComponents, which are called from
	// updateRepeatUnitMenu, which is called from the IntervalListener.
	// initializeValues also explicitly enables/disables this 
	// facet. It is called during encodeEnd if the validation
	// phase is run, i.e. Scheduler.submittedValue == null.
	// 

	// FIXME: 3 should be part of Theme.
	//
	initFieldFacet(field, 3, false);

	return field;
    }

    /**
     * Return a DropDown component that implements a repeat unit menu.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>repeatLimitUnit</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     * Otherwise a <code>DropDown</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_repeatLimitUnit"</code> and added to the facets map
     * as a private facet.</br>
     *
     * @return a repeat unit DropDown component.
     */
    public DropDown getRepeatUnitComponent() {
        
	if (DEBUG) log("getRepeatUnitComponent()"); //NOI18N
	// Support only a private facet.
	//
        DropDown dropDown = (DropDown)
	    ComponentUtilities.getPrivateFacet(this, REPEAT_LIMIT_UNIT_FACET,
		    true);
        if (dropDown == null) {
	    if (DEBUG) log("createDropDown"); //NOI18N
        
	    dropDown = new DropDown();
	    dropDown.setId(ComponentUtilities.createPrivateFacetId(this,
		REPEAT_LIMIT_UNIT_FACET));
            dropDown.setConverter(new RepeatUnitConverter());
	    ComponentUtilities.putPrivateFacet(this, REPEAT_LIMIT_UNIT_FACET,
		dropDown);
	}

	// Ideally this facet should be disabled or enabled here
	// based on the state of the Scheduler. Currently it is 
	// disabled/enabled in disableRepeatLimitComponents
	// and enableRepeatLimitComponents, which are called from
	// updateRepeatUnitMenu, which is called from the IntervalListener.
	// initializeValues also explicitly enables/disables this 
	// facet. It is called during encodeEnd if the validation
	// phase is run, i.e. Scheduler.submittedValue == null.
	// 

	dropDown.setItems(getRepeatUnitItems());
	Theme theme =
	    ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
	dropDown.setToolTip(theme.getMessage(REPEAT_UNIT_DESCRIPTION_TEXT_KEY));

	return dropDown;
    }   
      
    /**
     * Return a component that implements a preview button facet.
     * If a facet named <code>previewButton</code> is found
     * that component is returned. Otherwise a <code>Button</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_previewButton"</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Button</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @return a preview button facet component
     */
    public UIComponent getPreviewButtonComponent() {

	if (DEBUG) log("getPreviewButtonComponent()");  //NOI18N

	// Check if the page author has defined the facet
	//
	UIComponent buttonComponent = getFacet(PREVIEW_BUTTON_FACET); 
	if (buttonComponent != null) {
	    if (DEBUG) { 
		log("\tFound facet"); //NOI18N
	    } 
	    return buttonComponent;
	}

	// Return the private facet or create one, but initialize
	// it every time
	//
	// We know it's a Button
	//
	Button button = (Button)ComponentUtilities.getPrivateFacet(this,
		PREVIEW_BUTTON_FACET, true);
	if (button == null) {
	    if (DEBUG) log("create Button");  //NOI18N
	    button = new Button(); 
	    button.setId(ComponentUtilities.createPrivateFacetId(this,
		PREVIEW_BUTTON_FACET));

            button.setMini(true);
            button.setPrimary(false);
            button.setImmediate(true); 
            button.addActionListener(new SchedulerPreviewListener()); 

	    ComponentUtilities.putPrivateFacet(this, PREVIEW_BUTTON_FACET,
		button);
	}

	button.setText(ThemeUtilities.getTheme(
		FacesContext.getCurrentInstance()).getMessage(
			PREVIEW_BUTTON_TEXT_KEY));

	return button; 
    }
    
    
    // Called in IntervalListener

    /**
     * Called from IntervalListener, enable or disable dependent facets.
     * If the REPEAT_INTERVAL_FACET exists, has a non null value that
     * is an instance of <code>RepeatInterval</code> and is 
     * </code>RepeatInterval.ONETIME</code> then disable
     * the REPEAT_LIMIT_FACET and REPEAT_INTERVAL_FACET facets. This 
     * includes setting the values of the facets to null.</br>
     * Otherwise enable both facets.</br>
     * If the value is null or not an instance of <code>RepeatInterval</code>
     * then disable the REPEAT_LIMIT_FACET and REPEAT_INTERVAL_FACET 
     * facets as described above.
     */
    public void updateRepeatUnitMenu() {
        
        if (DEBUG) log("updateRepeatUnitMenu"); //NOI18N

        // We only need to do something if the fields that limit
        // repetition are shown
        if (!isLimitRepeating()) {
            if (DEBUG) log("repeat limit fields not shown - return"); //NOI18N
            return;
        }
	// Get the private facets directly, don't initialize here.
	// unless it doesn't exist but it should exist.
	//
        DropDown repeatIntervalComp = 
		(DropDown)getPrivateFacet(REPEAT_INTERVAL_FACET);

        Object value = repeatIntervalComp.getValue();

        if (value == null || !(value instanceof RepeatInterval)) {
            if (DEBUG) log("value is null - disable everything"); //NOI18N
            disableRepeatLimitComponents();
        } else {
            RepeatInterval ri = (RepeatInterval)value;
            if (DEBUG) log("RI value is " + ri.getKey()); //NOI18N
            if (ri.getRepresentation().equals(RepeatInterval.ONETIME)) {
                if (DEBUG) {
		    log("value is ONE TIME - disable everything"); //NOI18N
		}
                disableRepeatLimitComponents();
                
            } else {
		if (DEBUG) {
		    log("Repeat specified, enable everything: " + //NOI18N
			((RepeatInterval)value).getKey());
		}
		enableRepeatLimitComponents(ri);
            }
        }
    }
    
    /**
     * Implement this method so that it returns the DOM ID of the 
     * HTML element which should receive focus when the component 
     * receives focus, and to which a component label should apply. 
     * Usually, this is the first element that accepts input. 
     * 
     * @param context The FacesContext for the request
     * @return The client id, also the JavaScript element id
     *
     * @deprecated
     * @see #getLabeledElementId
     * @see #getFocusElementId
     */
    public String getPrimaryElementID(FacesContext context) {
	return getLabeledElementId(context);
    }
    
    /**
     * Returns the absolute ID of an HTML element suitable for use as
     * the value of an HTML LABEL element's <code>for</code> attribute.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is the target of a label, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getLabeledElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     * <p>
     * This implementation returns the id of the component returned by
     * <code>getDateComponent</code>. If that method returns null
     * <code>null</code> is returned.
     * </p>
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context) {
        UIComponent comp = getDateComponent();
	if (comp == null) {
	    return null;
	}
        if (comp instanceof ComplexComponent) {
            return ((ComplexComponent)comp).getLabeledElementId(context);
        } else {
            return comp.getClientId(context);
        }        
    }

    /**
     * Returns the id of an HTML element suitable to
     * receive the focus.
     * If the <code>ComplexComponent</code> has sub-compoents, and one of 
     * the sub-components is to reveive the focus, if that sub-component
     * is a <code>ComplexComponent</code>, then
     * <code>getFocusElementId</code> must called on the sub-component and
     * the value returned. The value returned by this 
     * method call may or may not resolve to a component instance.
     * <p>
     * This implementation returns the value of
     * <code>getLabeledElementId</code>.
     *
     * @param context The FacesContext used for the request
     */
    public String getFocusElementId(FacesContext context) {
	// For return the labeled component
	//
	return getLabeledElementId(context);
    }

    /**
     * Return a component instance that can be referenced
     * by a <code>Label</code> in order to evaluate the <code>required</code>
     * and <code>valid</code> states of this component.
     *
     * @param context The current <code>FacesContext</code> instance
     * @param label The <code>Label</code> that labels this component.
     * @return a <code>UIComponent</code> in order to evaluate the
     * required and valid states.
     */
    public UIComponent getIndicatorComponent(FacesContext context,
            Label label) {
	return this;
    }

    /**
     * If the developer has not provided repeat interval items, return
     * an <code>Options</code> array of <code>RepeatIntervalOption</code>
     * elements representing the following intervals.
     * <p>
     * <ul>
     * <li>one time</li>
     * <li>hourly</li>
     * <li>daily</li>
     * <li>weekly</li>
     * <li>monthly</li>
     * </ul>
     * </p>
     */
    public Object getRepeatIntervalItems() {
        
        Object optionsObject = _getRepeatIntervalItems();
        if (optionsObject == null) {
            Option[] options = new Option[5];
            options[0] = new RepeatIntervalOption(
		RepeatInterval.getInstance(RepeatInterval.ONETIME));
            options[1] = new RepeatIntervalOption(
		RepeatInterval.getInstance(RepeatInterval.HOURLY));
            options[2] = new RepeatIntervalOption(
		RepeatInterval.getInstance(RepeatInterval.DAILY));
            options[3] = new RepeatIntervalOption(
		RepeatInterval.getInstance(RepeatInterval.WEEKLY));
            options[4] = new RepeatIntervalOption(
		RepeatInterval.getInstance(RepeatInterval.MONTHLY));
            
            optionsObject = options;           
            
	    // This should not be done. 
	    // This changes a developers bound value if there
	    // is a setter. Unlikely, but should check for a value binding.
	    //
            setRepeatIntervalItems(options);
        }
        return optionsObject;
    }
    
    /**
     * If the developer has not provided repeat unit items, return
     * an <code>Options</code> array of <code>RepeatUnitOption</code>
     * elements representing the following units.
     * <p>
     * <ul>
     * <li>hours</li>
     * <li>days</li>
     * <li>weeks</li>
     * <li>months</li>
     * </ul>
     * </p>
     */
    public Object getRepeatUnitItems() {
        
        Object optionsObject = _getRepeatUnitItems();
        if (optionsObject == null) { 
            Option[] options = new Option[4];        
            options[0] = new RepeatUnitOption(
		RepeatUnit.getInstance(RepeatUnit.HOURS));
            options[1] = new RepeatUnitOption(
		RepeatUnit.getInstance(RepeatUnit.DAYS));
            options[2] = new RepeatUnitOption(
		RepeatUnit.getInstance(RepeatUnit.WEEKS));
            options[3] = new RepeatUnitOption(
		RepeatUnit.getInstance(RepeatUnit.MONTHS));
            optionsObject = options;

	    // This should not be done. 
	    // This changes a developers bound value if there
	    // is a setter. Unlikely, but should check for a value binding.
	    //
            setRepeatUnitItems(options); 
        } 
        return optionsObject;
      }

    public DateFormat getDateFormat() {
        return getDatePicker().getDateFormat();
    }
    
    public String getJavaScriptObjectName(FacesContext context) {
        return JavaScriptUtilities.getDomNode(getFacesContext(), this);
    }
    
    /**
     * <p>Specialized decode behavior on top of that provided by the
     * superclass.  In addition to the standard
     * <code>processDecodes</code> behavior inherited from {@link
     * UIComponentBase}, calls <code>validate()</code> if the the
     * <code>immediate</code> property is true; if the component is
     * invalid afterwards or a <code>RuntimeException</code> is thrown,
     * calls {@link FacesContext#renderResponse}.  </p>
     * @exception NullPointerException
     */
    public void processDecodes(FacesContext context) {
        
        if (DEBUG) log("processDecodes()"); //NOI18N
        if (context == null) {
            throw new NullPointerException();
        }
        
        // Skip processing if our rendered flag is false
        if (!isRendered()) {
            return;
        }
        
        setValid(true);
        
	// Obtain facets with the state that they had when the
	// facet was rendered. For the private facets which must
	// exist since component was rendered, use getPrivateFacet.
	// If that returns null, call the getXXXComponent method.
	// Only the DATE_FACET, and PREVIEW_BUTTON_FACET
	// facet have public alternatives. For these call the
	// getXXXComponent methods.
	//
	UIComponent facet = getPrivateFacet(DATE_PICKER_FACET);
	facet.processDecodes(context);

	// never returns null;
	// might be developer defined facets.
	//
	getPreviewButtonComponent().processDecodes(context);
	getDateComponent().processDecodes(context);

        if (isStartTime()) {
	    facet = getPrivateFacet(START_TIME_FACET);
	    facet.processDecodes(context);
        }
        if (isEndTime()) {
	    facet = getPrivateFacet(END_TIME_FACET);
	    facet.processDecodes(context);
        }
        if (isRepeating()) { 
	    facet = getPrivateFacet(REPEAT_INTERVAL_FACET);
	    facet.processDecodes(context);
            if (isLimitRepeating()) { 
		facet = getPrivateFacet(REPEAT_LIMIT_FACET);
		facet.processDecodes(context);
		facet = getPrivateFacet(REPEAT_LIMIT_UNIT_FACET);
		facet.processDecodes(context);
            } 
        }
        
        setSubmittedValue(VALUE_SUBMITTED);
        
        // There is nothing to decode other than the facets
        
        if (isImmediate()) {
            if(DEBUG) log("Scheduler is immediate"); //NOI18N
            runValidation(context);
        }
    }

    
    /**
     * <p>Perform the following algorithm to validate the local value of
     * this {@link UIInput}.</p>
     * <ul>
     * <li>Retrieve the submitted value with <code>getSubmittedValue()</code>.
     * If this returns null, exit without further processing.  (This
     * indicates that no value was submitted for this component.)</li>
     *
     * <li> Convert the submitted value into a "local value" of the
     * appropriate data type by calling {@link #getConvertedValue}.</li>
     *
     * <li>Validate the property by calling {@link #validateValue}.</li>
     *
     * <li>If the <code>valid</code> property of this component is still
     * <code>true</code>, retrieve the previous value of the component
     * (with <code>getValue()</code>), store the new local value using
     * <code>setValue()</code>, and reset the submitted value to
     * null.  If the local value is different from
     * the previous value of this component, fire a
     * {@link ValueChangeEvent} to be broadcast to all interested
     * listeners.</li>
     * </ul>
     *
     * <p>Application components implementing {@link UIInput} that wish to
     * perform validation with logic embedded in the component should perform
     * their own correctness checks, and then call the
     * <code>super.validate()</code> method to perform the standard
     * processing described above.</p>
     *
     * @param context The {@link FacesContext} for the current request
     */

    // Note that the getXXCompoent methods are called here. The problem is 
    // that getConvertedValue is called in encodeEnd when an immediate
    // request action occurs. It may not be appropriate to use the
    // component in its last rendered state, but it current
    // reinitialized state. But it's not clear which is best.
    // Ideally getConvertedValue is relegated to being called
    // onlyc during request processing and not during rendering
    // but that requires that facets and Scheduler always have the
    // apporpiate state by the time rendering occurs.
    // That is not the case currently.
    //
    public Object getConvertedValue(FacesContext context, Object submittedValue) 
        throws ConverterException {
        
        if(DEBUG) log("getConvertedValue()");
        
        if (context == null) {
            throw new NullPointerException();
        } 
        
        // Process all the facets and children of this component
        Iterator kids = getFacetsAndChildren();
        UIComponent kid = null;
 
        while (kids.hasNext()) {
            kid = (UIComponent) kids.next();
            if(kid instanceof EditableValueHolder &&
		    !(((EditableValueHolder)kid).isValid())) {
                return null;
            } 
        }
        
        // We ran the ordinary validation process in processValidators, so 
        // we use getValue() to get the value from the children - they 
        // will have been processed at this point. 
        
        Object dateValue = ((EditableValueHolder)getDateComponent()).getValue();
        Object startTimeValue = null;
        Object endTimeValue = null; 
        
        if(DEBUG) log("Date value is " + String.valueOf(dateValue));
        
        if(isStartTime()) {
            startTimeValue = getStartTimeComponent().getValue();
            if(DEBUG) log("Date value is " + String.valueOf(startTimeValue));
        }
        
        if(isEndTime()) {
            endTimeValue = getEndTimeComponent().getValue();
            if(DEBUG) log("Date value is " + String.valueOf(endTimeValue));
        }
        
        ScheduledEvent newValue = createScheduledEvent(dateValue, startTimeValue,
                                                       endTimeValue, context);
        if(isRepeating()) {    
            boolean valueRepeat = false;
            
            Object repeatFrequency = getRepeatIntervalComponent().getValue();
            if(repeatFrequency != null && repeatFrequency instanceof RepeatInterval) {
                RepeatInterval freq = (RepeatInterval)repeatFrequency;
                if(freq.getCalendarField().intValue() > -1) { 
                    valueRepeat = true; 
                } 
            } 
            if(valueRepeat) {

                newValue.setRepeatingEvent(true);
             
                try {
                    
                    newValue.setRepeatInterval((RepeatInterval)repeatFrequency);
                    
                    if(DEBUG) log("Repeat frequency value is " + 
                                   String.valueOf(((RepeatInterval)repeatFrequency).getCalendarField()));
                    
                    if(isLimitRepeating()) {
                        Object repeatUnit = getRepeatUnitComponent().getValue();
                        if(DEBUG) log("Repeat unit is " + String.valueOf(repeatUnit));
                        if(repeatUnit instanceof RepeatUnit) {
                            newValue.setDurationUnit((RepeatUnit)repeatUnit);
                        } else { 
                            newValue.setDurationUnit(null);
                        }
                        Object repeatLimit = ((EditableValueHolder)getRepeatingFieldComponent()).getValue();
                        if(DEBUG) log("Repeat unit is " + String.valueOf(repeatLimit));
                        if(repeatLimit instanceof Integer) {
                            newValue.setDuration((Integer)repeatLimit);
                        } else {
                            newValue.setDuration(null);
                        }
                    } else {
                        newValue.setDurationUnit(null);
                        newValue.setDuration(null);
                    }
                } catch(Exception ce) {
                    throw new ConverterException();
                }
            }
            
            else {
                newValue.setRepeatingEvent(false);
                newValue.setDurationUnit(null);
                newValue.setDuration(null);
            }
        } 
      else {
            newValue.setRepeatingEvent(false);
            newValue.setDurationUnit(null);
            newValue.setDuration(null);
      }
      getDatePicker().setValue(newValue); 
      return newValue;
    }
    
    /**
     * Return a component that implements a label for the facetName role.
     * If a facet named facetName is found
     * that component is returned. Otherwise a <code>Label</code> component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_"  + facetName</code></br>
     * <p>
     * If the facet is not defined then the returned <code>Label</code>
     * component is re-intialized every time this method is called.
     * </p>
     *
     * @param facetName the name of the facet to return or create
     * @param labelText the text for the label
     * @param labeledComponentId the absolute id to use for the label's
     * <code>for</code> attribute.
     *
     * @return a label facet component
     */
    private UIComponent getLabelFacet(String facetName, String labelText,
	UIComponent labeledComponent) {
        
        if(DEBUG) log("getLabelFacet() " + facetName); //NOI18N

        // Check if the page author has defined a label facet
	//
        UIComponent labelFacet = getFacet(facetName);
	if (labelFacet != null) {
	    return labelFacet;
	}

	// No longer modify a developer defined facet. Not even for the
	// "for" attribute, even though it may not be straightforward
	// to know what the labeled component is. Err on the side
	// of consistently not modifying a developer defined facet.
	//

	// Return the private facet or create one, but initialize
	// it every time
	//
	// We know it's a Label
	//
	Label label = (Label)ComponentUtilities.getPrivateFacet(this,
		facetName, true);
	if (label == null) {
	    label = new Label();
	    label.setId(ComponentUtilities.createPrivateFacetId(this,
		facetName));

	    // Note that in the original code labels were not put
	    // into the facet map. I'm not sure why. It's not clear
	    // if the label will always be able find its "for"
	    // component otherwise. Also the parent wasn't set either
	    // which means the label will not have a clientId
	    // renedered. Adding it to the facet map here.
	    //
	    ComponentUtilities.putPrivateFacet(this, facetName, label);
	}

	label.setText(labelText);
	label.setLabeledComponent(labeledComponent);
	label.setIndicatorComponent(labeledComponent);

	// FIXME: Should be part of Theme
	//
	label.setLabelLevel(2);

        return label;
    }
    
    /**
     * Initialize a field facet
     *
     * @param field the TextField instance
     * @param columns the number of characters
     * @param required if true the field is required.
     */
    private void initFieldFacet(TextField field, int columns,
	    boolean required) {
        
        if(DEBUG) log("initFieldFacet()"); //NOI18N
        
        field.setColumns(columns);
        field.setTrim(true);
        field.setRequired(required);
	int tindex = getTabIndex();
        if(tindex > 0) {
            field.setTabIndex(tindex);
        }
    }
        
    /**
     * Return a Time facet component.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>facetName</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     * Otherwise a <code>Time</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_" + facetName</code> and added to the facets map
     * as a private facet.</br>
     *
     * @return a Time component.
     */
    private Time getTimeFacet(String facetName, boolean required,
            String hourKey, String minutesKey) {
        
        if(DEBUG) log("getTimeFacet() " + facetName); //NOI18N

        // Not a public facet
        Time time = (Time)ComponentUtilities.getPrivateFacet(this,
		facetName, true);
        if (time == null) {
            if(DEBUG) log("Create new Time Component"); //NOI18N
            time = new Time();
            time.setId(ComponentUtilities.createPrivateFacetId(this,
		facetName));
            ComponentUtilities.putPrivateFacet(this, facetName, time);
        }

	time.setRequired(required);
	time.setTimeZone(getTimeZone());
	time.setHourTooltipKey(hourKey);
	time.setMinutesTooltipKey(minutesKey);

        return time;
    }
    
    private void runValidation(FacesContext context) {
        try {
            validate(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
        
        if (!isValid()) {
            context.renderResponse();
        }
    }
    
    // Note that this method can throw exception when getConvertedValue
    // is called during updateDatePicker in encodeEnd when immediate
    // request actions occur and some of the facet's have null values.
    // As a side effect the method can also set the isValid(false) on the
    // getEndTimeComponent();
    //
    private ScheduledEvent createScheduledEvent(Object dateObject,
            Object startTimeObject,
            Object endTimeObject,
            FacesContext context) {
        
        ScheduledEvent event = null;
        String messageKey = null;
        
        if (!(dateObject instanceof Date)) {
            messageKey = INVALID_DATE_TEXT_KEY;
        }
        if (!(startTimeObject instanceof ClockTime)) {
            messageKey = INVALID_START_TIME_TEXT_KEY;
        }
        if (endTimeObject != null && !(endTimeObject instanceof ClockTime)) {
            messageKey = INVALID_END_TIME_TEXT_KEY;
        }
        if (messageKey == null) {
            
            event = new ScheduledEvent();
            
            Date date = (Date)dateObject;
            ClockTime startTime = (ClockTime)startTimeObject;
            
            if (DEBUG) log("Base date is " + date.toString()); //NOI18N
            event.setStartTime(calculateDate(date, startTime));
            
            if (endTimeObject != null) {
                ClockTime endTime = (ClockTime)endTimeObject;
                event.setEndTime(calculateDate(date, endTime));
                if(event.getEndTime().before(event.getStartTime())) {
                    messageKey = END_BEFORE_START_TEXT_KEY;
		    // This should not be happening here.
		    // This should be done during validation.
		    // Currently this can occur during a call to 
		    // getConvertedValue within normal request processing
		    // or during encodeEnd in the event of an immediate
		    // request action, in updateDatePicker.
		    //
                    getEndTimeComponent().setValid(false);
                }
            }
        }
        
        if(messageKey != null) {
            String message =
                    ThemeUtilities.getTheme(context).getMessage(messageKey);
            throw new ConverterException(new FacesMessage(message));
        }
        return event;
    }

    private Date calculateDate(Date date, ClockTime time) {
        
	java.util.Calendar calendar = (java.util.Calendar)
		(getDatePicker().getCalendar().clone());

        calendar.setTime(date);
        calendar.set(java.util.Calendar.HOUR_OF_DAY, time.getHour().intValue());
        calendar.set(java.util.Calendar.MINUTE, time.getMinute().intValue());
        return calendar.getTime();
    }
    
    
    private void disableRepeatLimitComponents() {
        DropDown dd = getRepeatUnitComponent();
        UIComponent comp = getRepeatingFieldComponent();
        dd.setDisabled(true);
        dd.setSubmittedValue(null);
        dd.setValue(null);
        comp.getAttributes().put("disabled", Boolean.TRUE);
        ((EditableValueHolder)comp).setValue(null);
        ((EditableValueHolder)comp).setSubmittedValue(null);
    }
    
    private void enableRepeatLimitComponents(RepeatInterval ri) {
        DropDown dd = getRepeatUnitComponent();
        UIComponent comp = getRepeatingFieldComponent();
        dd.setValue(ri.getDefaultRepeatUnit());
        dd.setSubmittedValue(null);
        dd.setDisabled(false);
        comp.getAttributes().put("disabled", Boolean.FALSE);
    }

    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s);
    }   

    /**
     * Return a private facet.
     * This method is used during request
     * processing to obtain a private facet in its last rendered state.
     * The facet should exist, since the idea is that
     * it was at least rendered once. If it doesn't exist return the
     * facet from the associated getXXXComponent method.
     * This method only supports the private facets DATE_PICKER_FACET,
     * START_TIME_FACET, END_TIME_FACET, REPEAT_LIMIT_UNIT_FACET,
     * REPEAT_INTERVAL_FACET.
     */
    private UIComponent getPrivateFacet(String facetName) {

	// Return the private facet if it exists
	//
	UIComponent facet = ComponentUtilities.getPrivateFacet(this,
		facetName, false);
	if (facet != null) {
	    return facet;
	}

	if (facetName.equals(DATE_PICKER_FACET)) {
	    facet = getDatePicker();
	} else
	if (facetName.equals(START_TIME_FACET)) {
	    facet = getStartTimeComponent();
	} else
	if (facetName.equals(END_TIME_FACET)) {
	    facet = getEndTimeComponent();
	} else
	if (facetName.equals(REPEAT_LIMIT_UNIT_FACET)) {
	    facet = getRepeatUnitComponent();
	} else
	if (facetName.equals(REPEAT_INTERVAL_FACET)) {
	    facet = getRepeatIntervalComponent();
	}

	return facet;
    }

    // Take this from SchedulerRenderer. A Renderer shouldn't be
    // initializing or updating a component. Ideally the state should
    // be current before this point is reached thereby giving the
    // application an opportunity to affect the state in
    // INVOKE_APPLICATION_PHASE. But until these state changes
    // occur during request processing exlusively, the application
    // still does not have exclusive rights to affect the state
    // in INVOKE_APPLICATION_PHASE.
    // 
    /**
     * @exception NullPointerException
     */ 
    public void encodeEnd(FacesContext context) throws IOException {

        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }
        
        if (getSubmittedValue() != null) {
            if(DEBUG) log("Found submitted value"); //NOI18N
            updateDatePicker(context);  
        } 
        else { 
            if (DEBUG) log("No submitted value"); //NOI18N
            initializeValues(context);     
        }
        
        String rendererType = getRendererType();
        if (rendererType != null) {
            getRenderer(context).encodeEnd(context, this);
        }
    }

    // Most fields manage themselves when the component re-renders itself
    // with a submitted value. There are a couple of exceptions however, 
    // where the value of one field affects another... 

    // This method is called if Scheduler.getSubmittedValue returns
    // non null. If Scheduler's submitted value is not null then 
    // on of the following is true.
    //
    // - An immediate action via, preview, or the repeat interval facet
    // has occured
    // - Validation has failed for Scheduler or one of the 
    // subcomponents.
    //
    private void updateDatePicker(FacesContext context) {
        
        if (DEBUG) log("updateDatePicker()"); //NOI18N
        try { 
            Object value = getConvertedValue(context, null); 

	    // This happens IN getConvertedValue
	    // when there is no exception
	    //
            getDatePicker().setValue(value); 
        } 
        catch(Exception ex) {
           
	    // Why doesn't getConvertedValue do this ?
	    //
	    UIComponent comp = getDateComponent();
	    Object value = ((EditableValueHolder)comp).getSubmittedValue();
	    if (value != null) {
		try {
		    Object dO = ConversionUtilities.convertValueToObject(comp,
			       (String)value, context);
		    getDatePicker().setValue(dO);
		} catch(Exception ex2) {
		       // do nothing
		}
	    }
        }     
    }

    // Need to figure out if this is the right thing to do? These components
    // do not retain their values unless managed this way.
    // For the time component the problem is that the submitted value can't
    // be used to set the value, so to speak.

    private void initializeValues(FacesContext context) {
        
        if(DEBUG) log("initializeValue()"); //NOI18N

	CalendarMonth dp = getDatePicker();
        
	// Note that the repeating field component's disabled state
	// will NOT be modfied if 
	//
	// value == null || value ! instanceof ScheduledEvent &&
	// isRepeating == false || (isRepeating == true && 
	// 	isLimitRepeating == false)
	//
	// Or
	//
	// value != null && value instanceof ScheduledEvent &&
	// isRepeating == false || (isRepeating == true && 
	// 	isLimitRepeating == false)
	//
	// So what does this mean ? Similarly getRepeatUnitComponent's
	// disabled state may also not be modfied under the same
	// conditions.

        Object value = getValue();
        if (value != null && value instanceof ScheduledEvent) {

            if(DEBUG) log("Found scheduled event"); //NOI18N
            ScheduledEvent event = (ScheduledEvent)value;

            // Set the value of the date picker
            dp.setValue(event); 
            dp.displayValue(); 
            
            // Initialize the start date field
            ((EditableValueHolder)
		getDateComponent()).setValue(event.getStartTime());

	    // Isn't this redundant ?
	    //
            //dp.setValue(event); 

            if (isStartTime()) {
                if(DEBUG) log("Setting the start time"); //NOI8N
                getStartTimeComponent().setValue(
                        getClockTime(dp, event.getStartTime()));
            }
            
            // Initialize the end date field
            if (isEndTime()) {
                getEndTimeComponent().setValue(
                        getClockTime(dp, event.getEndTime()));
            }
            
            // Initialize the fields that configure repetition
            if (isRepeating()) {
                RepeatInterval ri = event.getRepeatInterval();
                getRepeatIntervalComponent().setValue(ri);
                if (isLimitRepeating()) {
                    DropDown unitDropDown = getRepeatUnitComponent();
                    UIComponent comp = getRepeatingFieldComponent(); 
                    if (ri == null || ri.getRepresentation().equals(
						RepeatInterval.ONETIME)) {
                        unitDropDown.setDisabled(true);
                        unitDropDown.setValue(null);
			// Will trash a developer facet
			//
                        ((EditableValueHolder)comp).setValue(null);
                        comp.getAttributes().put("disabled", //NOI18N
				Boolean.TRUE);
                    } else {
                        unitDropDown.setDisabled(false);
                        unitDropDown.setValue(event.getDurationUnit());

			// Will trash a developer defined facet
			//
                        ((EditableValueHolder)comp).setValue(
				event.getDuration());
                        comp.getAttributes().put("disabled", //NOI18N
				Boolean.FALSE);
                    }
                }             
            }

        } else {
            
            // Set the value of the date picker
            dp.setValue(null); 
            
            ((EditableValueHolder)getDateComponent()).setValue(null);

            if (isStartTime()) {
                getStartTimeComponent().setValue(null);
            }
            if (isEndTime()) {
                getEndTimeComponent().setValue(null);
            }
            if (isRepeating()) {
                // This one handles itself - it's immediate...
                getRepeatIntervalComponent().setValue(RepeatInterval.ONETIME);
                if (isLimitRepeating()) {

		    DropDown ru = getRepeatUnitComponent();
                    ru.setValue(null);
                    ru.setDisabled(true); 

		    EditableValueHolder rf = (EditableValueHolder)
			getRepeatingFieldComponent();

		    // Will trash a developer defined facet
		    //
		    rf.setValue(null);
                    ((UIComponent)rf).getAttributes().put("disabled", //NOI18N
			Boolean.TRUE);
                }
            }
        }
    }

    // Reimplement this as a converter for Time, and have time take
    // Date too...
    private ClockTime getClockTime(CalendarMonth datePicker, Date date) {
        if (DEBUG) log("getClockTime()"); //NOI18N
        if (date == null) {
            if (DEBUG) log("\tdate is null"); //NOI18N
            return null;
        }
        if (DEBUG) log("date is " + date.toString()); //NOI18N
        java.util.Calendar calendar = (java.util.Calendar)
		(datePicker.getCalendar());
        calendar.setTime(date);

        ClockTime clockTime = new ClockTime();
        clockTime.setHour(new Integer(calendar.get(
		java.util.Calendar.HOUR_OF_DAY)));
        clockTime.setMinute(new Integer(calendar.get(
		java.util.Calendar.MINUTE)));

        if (DEBUG) log("Hour is " + clockTime.getHour().toString()); //NOI18N
        if (DEBUG) log("Hour is " + clockTime.getMinute().toString()); //NOI18N

        return clockTime;
    }

    
      // Since the value of the minDate attribute could change, we can't
    // cache this in an attribute.
    public Date getFirstAvailableDate() {
        Date minDate = getMinDate();
        if(minDate == null) {
            java.util.Calendar calendar = getDatePicker().getCalendar(); 
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 0); 
            calendar.set(java.util.Calendar.MINUTE, 0); 
            calendar.set(java.util.Calendar.SECOND, 0); 
            calendar.set(java.util.Calendar.MILLISECOND, 0); 
            minDate = calendar.getTime();
        }
        return minDate;
    }
    
    public Date getLastAvailableDate() {
        Date maxDate = getMaxDate();
        if(maxDate == null) {
            Date minDate = getFirstAvailableDate();
            java.util.Calendar calendar = getDatePicker().getCalendar();
            calendar.setTime(minDate);
            calendar.add(java.util.Calendar.YEAR, 4);
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 23); 
            calendar.set(java.util.Calendar.MINUTE, 59); 
            calendar.set(java.util.Calendar.SECOND, 59); 
            calendar.set(java.util.Calendar.MILLISECOND, 999); 
            maxDate = calendar.getTime();
        }
        return maxDate;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The current value of this component.
     */
    @Property(name="value")
    public void setValue(Object value) {
        super.setValue(value);
    }

    // Hide converter
    @Property(name="converter", isHidden=true, isAttribute=false)
    public Converter getConverter() {
        return super.getConverter();
    }

    /**
     * <p>The date format pattern to use (i.e. yyyy-MM-dd). The
     * component uses an instance of
     * <code>java.text.SimpleDateFormat</code> and you may specify 
     * a pattern to be used by this component, with the following
     * restriction: the format pattern must include <code>yyyy</code> (not
     * <code>yy</code>), <code>MM</code>, and <code>dd</code>; and no
     * other parts of time may be displayed. If a pattern is not
     * specified, a locale-specific default is used.</p> 
     * <p> 
     * If you change the date format pattern, you may also need to
     * change the <code>dateFormatPatternHelp</code> attribute. See the
     * documentation for that attribute. 
     * </p>
     */
    @Property(name="dateFormatPattern", displayName="Date Format Pattern", category="Appearance", shortDescription="The date format pattern to use (e.g., yyyy-MM-dd).")
    private String dateFormatPattern = null;

    /**
     * <p>The date format pattern to use (i.e. yyyy-MM-dd). The
     * component uses an instance of
     * <code>java.text.SimpleDateFormat</code> and you may specify 
     * a pattern to be used by this component, with the following
     * restriction: the format pattern must include <code>yyyy</code> (not
     * <code>yy</code>), <code>MM</code>, and <code>dd</code>; and no
     * other parts of time may be displayed. If a pattern is not
     * specified, a locale-specific default is used.</p> 
     * <p> 
     * If you change the date format pattern, you may also need to
     * change the <code>dateFormatPatternHelp</code> attribute. See the
     * documentation for that attribute. 
     * </p>
     */
    public String getDateFormatPattern() {
        if (this.dateFormatPattern != null) {
            return this.dateFormatPattern;
        }
        ValueExpression _vb = getValueExpression("dateFormatPattern");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The date format pattern to use (i.e. yyyy-MM-dd). The
     * component uses an instance of
     * <code>java.text.SimpleDateFormat</code> and you may specify 
     * a pattern to be used by this component, with the following
     * restriction: the format pattern must include <code>yyyy</code> (not
     * <code>yy</code>), <code>MM</code>, and <code>dd</code>; and no
     * other parts of time may be displayed. If a pattern is not
     * specified, a locale-specific default is used.</p> 
     * <p> 
     * If you change the date format pattern, you may also need to
     * change the <code>dateFormatPatternHelp</code> attribute. See the
     * documentation for that attribute. 
     * </p>
     * @see #getDateFormatPattern()
     */
    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    /**
     * <p>A message below the text field for the date, indicating the
     * string format to use when entering a date as text into the
     * Start Date text field.</p>  
     * 
     * <p>The component internally relies on an instance of
     * <code>java.text.SimpleDateFormat</code> to produce the hint. 
     * The default hint is constructed by invoking the
     * <code>toLocalizedPattern()</code> method on the
     * <code>SimpleDateFormat</code> instance and converting this 
     * String to lower case.</p> 
     * 
     * <p>Due to a bug in
     * <code>SimpleDateFormat</code>,
     * <code>toLocalizedPattern()</code> does not actually produce
     * locale-appropriate strings for most locales (it works for
     * German, but not for other locales). If the default value for
     * the <code>dateFormtPattern</code> is used, the
     * component takes care of the localization itself, but if the default 
     * is overridden, you may need to override the hint on a
     * per-locale basis too. </p>
     */
    @Property(name="dateFormatPatternHelp", displayName="Date Format Pattern Help", category="Appearance")
    private String dateFormatPatternHelp = null;

    /**
     * <p>A message below the text field for the date, indicating the
     * string format to use when entering a date as text into the
     * Start Date text field.</p>  
     * 
     * <p>The component internally relies on an instance of
     * <code>java.text.SimpleDateFormat</code> to produce the hint. 
     * The default hint is constructed by invoking the
     * <code>toLocalizedPattern()</code> method on the
     * <code>SimpleDateFormat</code> instance and converting this 
     * String to lower case.</p> 
     * 
     * <p>Due to a bug in
     * <code>SimpleDateFormat</code>,
     * <code>toLocalizedPattern()</code> does not actually produce
     * locale-appropriate strings for most locales (it works for
     * German, but not for other locales). If the default value for
     * the <code>dateFormtPattern</code> is used, the
     * component takes care of the localization itself, but if the default 
     * is overridden, you may need to override the hint on a
     * per-locale basis too. </p>
     */
    public String getDateFormatPatternHelp() {
        if (this.dateFormatPatternHelp != null) {
            return this.dateFormatPatternHelp;
        }
        ValueExpression _vb = getValueExpression("dateFormatPatternHelp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A message below the text field for the date, indicating the
     * string format to use when entering a date as text into the
     * Start Date text field.</p>  
     * 
     * <p>The component internally relies on an instance of
     * <code>java.text.SimpleDateFormat</code> to produce the hint. 
     * The default hint is constructed by invoking the
     * <code>toLocalizedPattern()</code> method on the
     * <code>SimpleDateFormat</code> instance and converting this 
     * String to lower case.</p> 
     * 
     * <p>Due to a bug in
     * <code>SimpleDateFormat</code>,
     * <code>toLocalizedPattern()</code> does not actually produce
     * locale-appropriate strings for most locales (it works for
     * German, but not for other locales). If the default value for
     * the <code>dateFormtPattern</code> is used, the
     * component takes care of the localization itself, but if the default 
     * is overridden, you may need to override the hint on a
     * per-locale basis too. </p>
     * @see #getDateFormatPatternHelp()
     */
    public void setDateFormatPatternHelp(String dateFormatPatternHelp) {
        this.dateFormatPatternHelp = dateFormatPatternHelp;
    }

    /**
     * <p>This text replaces the "Start Date" label.</p>
     */
    @Property(name="dateLabel", displayName="Date Label", category="Appearance")
    private String dateLabel = null;

    /**
     * <p>This text replaces the "Start Date" label.</p>
     */
    public String getDateLabel() {
        if (this.dateLabel != null) {
            return this.dateLabel;
        }
        ValueExpression _vb = getValueExpression("dateLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>This text replaces the "Start Date" label.</p>
     * @see #getDateLabel()
     */
    public void setDateLabel(String dateLabel) {
        this.dateLabel = dateLabel;
    }

    /**
     * <p>Standard HTML attribute which determines whether the web
     * application user can change the the value of this component.
     * NOT YET IMPLEMENTED.</p>
     */
    @Property(name="disabled", displayName="Disabled", isHidden=true, isAttribute=false)
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * <p>Standard HTML attribute which determines whether the web
     * application user can change the the value of this component.
     * NOT YET IMPLEMENTED.</p>
     */
    public boolean isDisabled() {
        if (this.disabled_set) {
            return this.disabled;
        }
        ValueExpression _vb = getValueExpression("disabled");
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
     * <p>Standard HTML attribute which determines whether the web
     * application user can change the the value of this component.
     * NOT YET IMPLEMENTED.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    /**
     * <p>Flag indicating that an input field for the end time should be
     * shown. The default value is true.</p>
     */
    @Property(name="endTime", displayName="Show End Time", category="Appearance")
    private boolean endTime = false;
    private boolean endTime_set = false;

    /**
     * <p>Flag indicating that an input field for the end time should be
     * shown. The default value is true.</p>
     */
    public boolean isEndTime() {
        if (this.endTime_set) {
            return this.endTime;
        }
        ValueExpression _vb = getValueExpression("endTime");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Flag indicating that an input field for the end time should be
     * shown. The default value is true.</p>
     * @see #isEndTime()
     */
    public void setEndTime(boolean endTime) {
        this.endTime = endTime;
        this.endTime_set = true;
    }

    /**
     * <p>This text replaces the "End Time" label.</p>
     */
    @Property(name="endTimeLabel", displayName="End Time Label", category="Appearance")
    private String endTimeLabel = null;

    /**
     * <p>This text replaces the "End Time" label.</p>
     */
    public String getEndTimeLabel() {
        if (this.endTimeLabel != null) {
            return this.endTimeLabel;
        }
        ValueExpression _vb = getValueExpression("endTimeLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>This text replaces the "End Time" label.</p>
     * @see #getEndTimeLabel()
     */
    public void setEndTimeLabel(String endTimeLabel) {
        this.endTimeLabel = endTimeLabel;
    }

    /**
     * <p>Flag indicating that a control for setting a limit for repeating
     * events should be shown. The default value is true.</p>
     */
    @Property(name="limitRepeating", displayName="Limit Repeating Events", category="Appearance")
    private boolean limitRepeating = false;
    private boolean limitRepeating_set = false;

    /**
     * <p>Flag indicating that a control for setting a limit for repeating
     * events should be shown. The default value is true.</p>
     */
    public boolean isLimitRepeating() {
        if (this.limitRepeating_set) {
            return this.limitRepeating;
        }
        ValueExpression _vb = getValueExpression("limitRepeating");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Flag indicating that a control for setting a limit for repeating
     * events should be shown. The default value is true.</p>
     * @see #isLimitRepeating()
     */
    public void setLimitRepeating(boolean limitRepeating) {
        this.limitRepeating = limitRepeating;
        this.limitRepeating_set = true;
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the last
     * selectable day. The default value is four years after the
     * <code>minDate</code> (which is evaluated first).</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months after this date, or select days that
     * follow this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    @Property(name="maxDate", displayName="Last selectable date", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor", shortDescription="The last selectable date.")
    private java.util.Date maxDate = null;

    /**
     * <p>A <code>java.util.Date</code> object representing the last
     * selectable day. The default value is four years after the
     * <code>minDate</code> (which is evaluated first).</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months after this date, or select days that
     * follow this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    public java.util.Date getMaxDate() {
        if (this.maxDate != null) {
            return this.maxDate;
        }
        ValueExpression _vb = getValueExpression("maxDate");
        if (_vb != null) {
            return (java.util.Date) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the last
     * selectable day. The default value is four years after the
     * <code>minDate</code> (which is evaluated first).</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months after this date, or select days that
     * follow this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     * @see #getMaxDate()
     */
    public void setMaxDate(java.util.Date maxDate) {
        this.maxDate = maxDate;
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the first
     * selectable day. The default value is today's date.</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months before this date, or select days that
     * precede this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    @Property(name="minDate", displayName="First selectable date", category="Data", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor", shortDescription="The first selectable date.")
    private java.util.Date minDate = null;

    /**
     * <p>A <code>java.util.Date</code> object representing the first
     * selectable day. The default value is today's date.</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months before this date, or select days that
     * precede this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     */
    public java.util.Date getMinDate() {
        if (this.minDate != null) {
            return this.minDate;
        }
        ValueExpression _vb = getValueExpression("minDate");
        if (_vb != null) {
            return (java.util.Date) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A <code>java.util.Date</code> object representing the first
     * selectable day. The default value is today's date.</p> 
     * <p>The value of this attribute is reflected in the years that
     * are available for selection in the month display. In future
     * releases of this component, web application users will also not
     * be able to view months before this date, or select days that
     * precede this date. At present such dates can be selected, but
     * will not be validated when the form is submitted.</p>
     * @see #getMinDate()
     */
    public void setMinDate(java.util.Date minDate) {
        this.minDate = minDate;
    }

    /**
     * <p>Flag indicating that the "Preview in Browser" button should be
     * displayed - default value is true.</p>
     */
    @Property(name="previewButton", displayName="Preview Button", category="Appearance")
    private boolean previewButton = false;
    private boolean previewButton_set = false;

    /**
     * <p>Flag indicating that the "Preview in Browser" button should be
     * displayed - default value is true.</p>
     */
    public boolean isPreviewButton() {
        if (this.previewButton_set) {
            return this.previewButton;
        }
        ValueExpression _vb = getValueExpression("previewButton");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Flag indicating that the "Preview in Browser" button should be
     * displayed - default value is true.</p>
     * @see #isPreviewButton()
     */
    public void setPreviewButton(boolean previewButton) {
        this.previewButton = previewButton;
        this.previewButton_set = true;
    }

    /**
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined. NOT
     * YET IMPLEMENTED.</p>
     */
    @Property(name="readOnly", displayName="Read-only", category="Behavior")
    private boolean readOnly = false;
    private boolean readOnly_set = false;

    /**
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined. NOT
     * YET IMPLEMENTED.</p>
     */
    public boolean isReadOnly() {
        if (this.readOnly_set) {
            return this.readOnly;
        }
        ValueExpression _vb = getValueExpression("readOnly");
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
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined. NOT
     * YET IMPLEMENTED.</p>
     * @see #isReadOnly()
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        this.readOnly_set = true;
    }

    /**
     * <p>Override the items that appear on the repeat interval menu. 
     * The value must be one of an array, Map or Collection
     * whose members are all subclasses of
     * <code>com.sun.webui.jsf.model.scheduler.RepeatIntervalOption</code>,
     * whose values must be one of the member classes of 
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval</code>, 
     * for example
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval.ONETIME</code>
     * or 
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval.HOURLY</code>.
     * If this attribute is not specified, default options of "One Time", 
     * "Hourly", "Weekly", "Monthtly" will be shown.</p>
     */
    @Property(name="repeatIntervalItems", displayName="Repeat Interval Menu Items", category="Data", editorClassName="com.sun.webui.jsf.component.propertyeditors.RepeatIntervalEditor")
    private Object repeatIntervalItems = null;

    /**
     * <p>Override the items that appear on the repeat interval menu. 
     * The value must be one of an array, Map or Collection
     * whose members are all subclasses of
     * <code>com.sun.webui.jsf.model.scheduler.RepeatIntervalOption</code>,
     * whose values must be one of the member classes of 
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval</code>, 
     * for example
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval.ONETIME</code>
     * or 
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval.HOURLY</code>.
     * If this attribute is not specified, default options of "One Time", 
     * "Hourly", "Weekly", "Monthtly" will be shown.</p>
     */
    private Object _getRepeatIntervalItems() {
        if (this.repeatIntervalItems != null) {
            return this.repeatIntervalItems;
        }
        ValueExpression _vb = getValueExpression("repeatIntervalItems");
        if (_vb != null) {
            return (Object) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Override the items that appear on the repeat interval menu. 
     * The value must be one of an array, Map or Collection
     * whose members are all subclasses of
     * <code>com.sun.webui.jsf.model.scheduler.RepeatIntervalOption</code>,
     * whose values must be one of the member classes of 
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval</code>, 
     * for example
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval.ONETIME</code>
     * or 
     * <code>com.sun.webui.jsf.model.scheduler.RepeatInterval.HOURLY</code>.
     * If this attribute is not specified, default options of "One Time", 
     * "Hourly", "Weekly", "Monthtly" will be shown.</p>
     * @see #getRepeatIntervalItems()
     */
    public void setRepeatIntervalItems(Object repeatIntervalItems) {
        this.repeatIntervalItems = repeatIntervalItems;
    }

    /**
     * <p>Override the default value of the label for the repeat
     * interval menu.</p>
     */
    @Property(name="repeatIntervalLabel", displayName="Repeat Interval Label", category="Appearance")
    private String repeatIntervalLabel = null;

    /**
     * <p>Override the default value of the label for the repeat
     * interval menu.</p>
     */
    public String getRepeatIntervalLabel() {
        if (this.repeatIntervalLabel != null) {
            return this.repeatIntervalLabel;
        }
        ValueExpression _vb = getValueExpression("repeatIntervalLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Override the default value of the label for the repeat
     * interval menu.</p>
     * @see #getRepeatIntervalLabel()
     */
    public void setRepeatIntervalLabel(String repeatIntervalLabel) {
        this.repeatIntervalLabel = repeatIntervalLabel;
    }

    /**
     * <p>Override the default value of the label for the repeat
     * limit menu.</p>
     */
    @Property(name="repeatLimitLabel", displayName="Repeat Limit Label", category="Appearance")
    private String repeatLimitLabel = null;

    /**
     * <p>Override the default value of the label for the repeat
     * limit menu.</p>
     */
    public String getRepeatLimitLabel() {
        if (this.repeatLimitLabel != null) {
            return this.repeatLimitLabel;
        }
        ValueExpression _vb = getValueExpression("repeatLimitLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Override the default value of the label for the repeat
     * limit menu.</p>
     * @see #getRepeatLimitLabel()
     */
    public void setRepeatLimitLabel(String repeatLimitLabel) {
        this.repeatLimitLabel = repeatLimitLabel;
    }

    /**
     * <p>Override the items that appear on the repeat limit unit menu. 
     * The value must be one of an array, Map or Collection
     * whose members are all subclasses of
     * <code>com.sun.webui.jsf.model.Option</code>, and the value of 
     * the options must implement the
     * <code>com.sun.webui.jsf.model.RepeatUnit</code> interface. 
     * The default value is to show a menu with values "Hours", 
     * "Days", "Weeks", "Months".</p>
     */
    @Property(name="repeatUnitItems", displayName="Repeat Limit Unit Items", category="Data", editorClassName="com.sun.webui.jsf.component.propertyeditors.RepeatUnitEditor")
    private Object repeatUnitItems = null;

    /**
     * <p>Override the items that appear on the repeat limit unit menu. 
     * The value must be one of an array, Map or Collection
     * whose members are all subclasses of
     * <code>com.sun.webui.jsf.model.Option</code>, and the value of 
     * the options must implement the
     * <code>com.sun.webui.jsf.model.RepeatUnit</code> interface. 
     * The default value is to show a menu with values "Hours", 
     * "Days", "Weeks", "Months".</p>
     */
    private Object _getRepeatUnitItems() {
        if (this.repeatUnitItems != null) {
            return this.repeatUnitItems;
        }
        ValueExpression _vb = getValueExpression("repeatUnitItems");
        if (_vb != null) {
            return (Object) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Override the items that appear on the repeat limit unit menu. 
     * The value must be one of an array, Map or Collection
     * whose members are all subclasses of
     * <code>com.sun.webui.jsf.model.Option</code>, and the value of 
     * the options must implement the
     * <code>com.sun.webui.jsf.model.RepeatUnit</code> interface. 
     * The default value is to show a menu with values "Hours", 
     * "Days", "Weeks", "Months".</p>
     * @see #getRepeatUnitItems()
     */
    public void setRepeatUnitItems(Object repeatUnitItems) {
        this.repeatUnitItems = repeatUnitItems;
    }

    /**
     * <p>Flag indicating that a control for scheduling a repeated event
     * should be shown. The default value is true.</p>
     */
    @Property(name="repeating", displayName="Repeating Events", category="Appearance")
    private boolean repeating = false;
    private boolean repeating_set = false;

    /**
     * <p>Flag indicating that a control for scheduling a repeated event
     * should be shown. The default value is true.</p>
     */
    public boolean isRepeating() {
        if (this.repeating_set) {
            return this.repeating;
        }
        ValueExpression _vb = getValueExpression("repeating");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Flag indicating that a control for scheduling a repeated event
     * should be shown. The default value is true.</p>
     * @see #isRepeating()
     */
    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
        this.repeating_set = true;
    }

    /**
     * <p>Flag indicating that the user must enter a value for this Scheduler.
     * Default value is true.</p>
     */
    @Property(name="required", displayName="Required", isHidden=true, isAttribute=false)
    private boolean required = false;
    private boolean required_set = false;

    /**
     * <p>Flag indicating that the user must enter a value for this Scheduler.
     * Default value is true.</p>
     */
    public boolean isRequired() {
        if (this.required_set) {
            return this.required;
        }
        ValueExpression _vb = getValueExpression("required");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Flag indicating that the user must enter a value for this Scheduler.
     * Default value is true.</p>
     * @see #isRequired()
     */
    public void setRequired(boolean required) {
        this.required = required;
        this.required_set = true;
    }

    /**
     * <p>Flag indicating if the "* indicates required field" message should be
     * displayed - default value is true.</p>
     */
    @Property(name="requiredLegend", displayName="Required Legend", category="Appearance")
    private boolean requiredLegend = false;
    private boolean requiredLegend_set = false;

    /**
     * <p>Flag indicating if the "* indicates required field" message should be
     * displayed - default value is true.</p>
     */
    public boolean isRequiredLegend() {
        if (this.requiredLegend_set) {
            return this.requiredLegend;
        }
        ValueExpression _vb = getValueExpression("requiredLegend");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Flag indicating if the "* indicates required field" message should be
     * displayed - default value is true.</p>
     * @see #isRequiredLegend()
     */
    public void setRequiredLegend(boolean requiredLegend) {
        this.requiredLegend = requiredLegend;
        this.requiredLegend_set = true;
    }

    /**
     * <p>Flag indicating that an input field for the start time should be
     * shown. The default value is true.</p>
     */
    @Property(name="startTime", displayName="Show Start Time", category="Appearance")
    private boolean startTime = false;
    private boolean startTime_set = false;

    /**
     * <p>Flag indicating that an input field for the start time should be
     * shown. The default value is true.</p>
     */
    public boolean isStartTime() {
        if (this.startTime_set) {
            return this.startTime;
        }
        ValueExpression _vb = getValueExpression("startTime");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Flag indicating that an input field for the start time should be
     * shown. The default value is true.</p>
     * @see #isStartTime()
     */
    public void setStartTime(boolean startTime) {
        this.startTime = startTime;
        this.startTime_set = true;
    }

    /**
     * <p>This text replaces the "Start Time" label.</p>
     */
    @Property(name="startTimeLabel", displayName="Start Time Label", category="Appearance")
    private String startTimeLabel = null;

    /**
     * <p>This text replaces the "Start Time" label.</p>
     */
    public String getStartTimeLabel() {
        if (this.startTimeLabel != null) {
            return this.startTimeLabel;
        }
        ValueExpression _vb = getValueExpression("startTimeLabel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>This text replaces the "Start Time" label.</p>
     * @see #getStartTimeLabel()
     */
    public void setStartTimeLabel(String startTimeLabel) {
        this.startTimeLabel = startTimeLabel;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }

    /**
     * <p>The <code>java.util.TimeZone</code> used with this
     * component. Unless set, the default TimeZone for the locale in  
     * <code>javax.faces.component.UIViewRoot</code> is used.</p>
     */
    @Property(name="timeZone", displayName="Time Zone", isHidden=true)
    private java.util.TimeZone timeZone = null;

    /**
     * <p>The <code>java.util.TimeZone</code> used with this
     * component. Unless set, the default TimeZone for the locale in  
     * <code>javax.faces.component.UIViewRoot</code> is used.</p>
     */
    public java.util.TimeZone getTimeZone() {
        if (this.timeZone != null) {
            return this.timeZone;
        }
        ValueExpression _vb = getValueExpression("timeZone");
        if (_vb != null) {
            return (java.util.TimeZone) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The <code>java.util.TimeZone</code> used with this
     * component. Unless set, the default TimeZone for the locale in  
     * <code>javax.faces.component.UIViewRoot</code> is used.</p>
     * @see #getTimeZone()
     */
    public void setTimeZone(java.util.TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.dateFormatPattern = (String) _values[1];
        this.dateFormatPatternHelp = (String) _values[2];
        this.dateLabel = (String) _values[3];
        this.disabled = ((Boolean) _values[4]).booleanValue();
        this.disabled_set = ((Boolean) _values[5]).booleanValue();
        this.endTime = ((Boolean) _values[6]).booleanValue();
        this.endTime_set = ((Boolean) _values[7]).booleanValue();
        this.endTimeLabel = (String) _values[8];
        this.limitRepeating = ((Boolean) _values[9]).booleanValue();
        this.limitRepeating_set = ((Boolean) _values[10]).booleanValue();
        this.maxDate = (java.util.Date) _values[11];
        this.minDate = (java.util.Date) _values[12];
        this.previewButton = ((Boolean) _values[13]).booleanValue();
        this.previewButton_set = ((Boolean) _values[14]).booleanValue();
        this.readOnly = ((Boolean) _values[15]).booleanValue();
        this.readOnly_set = ((Boolean) _values[16]).booleanValue();
        this.repeatIntervalItems = (Object) _values[17];
        this.repeatIntervalLabel = (String) _values[18];
        this.repeatLimitLabel = (String) _values[19];
        this.repeatUnitItems = (Object) _values[20];
        this.repeating = ((Boolean) _values[21]).booleanValue();
        this.repeating_set = ((Boolean) _values[22]).booleanValue();
        this.required = ((Boolean) _values[23]).booleanValue();
        this.required_set = ((Boolean) _values[24]).booleanValue();
        this.requiredLegend = ((Boolean) _values[25]).booleanValue();
        this.requiredLegend_set = ((Boolean) _values[26]).booleanValue();
        this.startTime = ((Boolean) _values[27]).booleanValue();
        this.startTime_set = ((Boolean) _values[28]).booleanValue();
        this.startTimeLabel = (String) _values[29];
        this.style = (String) _values[30];
        this.styleClass = (String) _values[31];
        this.tabIndex = ((Integer) _values[32]).intValue();
        this.tabIndex_set = ((Boolean) _values[33]).booleanValue();
        this.timeZone = (java.util.TimeZone) _values[34];
        this.visible = ((Boolean) _values[35]).booleanValue();
        this.visible_set = ((Boolean) _values[36]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[37];
        _values[0] = super.saveState(_context);
        _values[1] = this.dateFormatPattern;
        _values[2] = this.dateFormatPatternHelp;
        _values[3] = this.dateLabel;
        _values[4] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.endTime ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.endTime_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.endTimeLabel;
        _values[9] = this.limitRepeating ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.limitRepeating_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.maxDate;
        _values[12] = this.minDate;
        _values[13] = this.previewButton ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.previewButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.readOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.readOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = this.repeatIntervalItems;
        _values[18] = this.repeatIntervalLabel;
        _values[19] = this.repeatLimitLabel;
        _values[20] = this.repeatUnitItems;
        _values[21] = this.repeating ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.repeating_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = this.required ? Boolean.TRUE : Boolean.FALSE;
        _values[24] = this.required_set ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = this.requiredLegend ? Boolean.TRUE : Boolean.FALSE;
        _values[26] = this.requiredLegend_set ? Boolean.TRUE : Boolean.FALSE;
        _values[27] = this.startTime ? Boolean.TRUE : Boolean.FALSE;
        _values[28] = this.startTime_set ? Boolean.TRUE : Boolean.FALSE;
        _values[29] = this.startTimeLabel;
        _values[30] = this.style;
        _values[31] = this.styleClass;
        _values[32] = new Integer(this.tabIndex);
        _values[33] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = this.timeZone;
        _values[35] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[36] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
