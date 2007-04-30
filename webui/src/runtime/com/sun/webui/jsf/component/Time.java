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
import com.sun.webui.jsf.event.MethodExprValueChangeListener;
import com.sun.webui.jsf.model.ClockTime;
import com.sun.webui.jsf.model.Option; 
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.validator.MethodExprValidator;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Locale; 
import java.util.TimeZone;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.ValueChangeEvent; 
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

/**
 * This component is for internal use only
 */
@Component(type="com.sun.webui.jsf.Time", family="com.sun.webui.jsf.Time", displayName="Time", isTag=false,
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_time",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_time_props")
public class Time extends WebuiInput implements NamingContainer,
	ComplexComponent {
    /**
     * The hour menu facet name.
     */
    public static final String HOUR_FACET = "hour"; //NOI18N

    /**
     * The minutes menu facet name.
     */
    public static final String MINUTES_FACET = "minutes"; //NOI18N
    
    private static final String TIME_SUBMITTED =
	"com.sun.webui.jsf.TimeSubmitted"; //NOI18N

    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public Time() {
        super();
        setRendererType("com.sun.webui.jsf.Time");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Time";
    }

    /**
     * Return a DropDown component that implements an hour menu.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>hour</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     * Otherwise a <code>DropDown</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_hour"</code> and added to the facets map
     * as a private facet.</br>
     *
     * @return an hour menu DropDown component.
     */
    public DropDown getHourMenu() {
        return getMenu(HOUR_FACET, getHourItems());
    }
    
    /**
     * Return a DropDown component that implements a minutes menu.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>minutes</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This is a private facet.</em>
     * </p>
     * Otherwise a <code>DropDown</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_minutes"</code> and added to the facets map
     * as a private facet.</br>
     *
     * @return a minutes menu DropDown component.
     */
    public DropDown getMinutesMenu() {
        return getMenu(MINUTES_FACET, getMinuteItems());
    }
    
    /**
     * Return a DropDown component for a menu.
     * If <code>ComponentUtilities.getPrivateFacet()</code>
     * returns a facet named <code>facet</code>
     * that component is initialized every time this
     * method is called and returned.
     * <p>
     * <em>This method returns a private facet.</em>
     * </p>
     * Otherwise a <code>DropDown</code> component
     * is created and initialized. It is assigned the id</br>
     * <code>getId() + "_" + facet</code> and added to the facets map
     * as a private facet.</br>
     *
     * @param facet the facet name
     * @param options the menu options
     *
     * @return a DropDown menu component.
     */
    private DropDown getMenu(String facet, Option[] options) {
        
        if (DEBUG) log("getMenu() for facet " + facet); //NOI18N
        
	// Support only a private facet.
	//
        DropDown menu = (DropDown)
		ComponentUtilities.getPrivateFacet(this, facet, true);
        if (menu == null) {
	    if(DEBUG) log("createDropDown() for facet " + facet); //NOI18N
        
	    menu = new DropDown();
	    menu.setId(ComponentUtilities.createPrivateFacetId(this, facet));
	    // Doesn't change
	    menu.setItems(options);
	    menu.setConverter(new IntegerConverter());
	    ComponentUtilities.putPrivateFacet(this, facet, menu);
	}

	// Probably should be doing tooltips here as well.
	// However, I was reluctant to change the logic 
	// regarding setting tooltips at this time but
	// it should be revisited. See encodeEnd in this file and
	// Scheduler.
        
	int tindex = getTabIndex();
	if (tindex > 0) {
            menu.setTabIndex(tindex);
        }
        menu.setDisabled(isDisabled());
        menu.setRequired(isRequired());

        return menu;
    }
    
    /**
     * <p>Convenience method to return at Option[] with all of the hours
     * defined in 24 hourObject format.</p>
     * 
     * @return An Option[] containing all the hours
     */
    private Option[] getHourItems() {
        Option[] hours = new Option[25];
        
        hours[0] = new Option(new Integer(-1), " ");//NOI18N
        
        int counter = 0;
        while(counter < 10) { 
            hours[counter + 1] = new Option(new Integer(counter), "0" + counter);//NOI18N
            ++counter;
        }
        while(counter < 24) {
            hours[counter + 1] = new Option(new Integer(counter), 
                                            String.valueOf(counter));
             ++counter;
        }
        return hours;
    }
    
    /**
     * <p>Convenience method to return at Option[] with all of the mintes (in
     * 5 minuteObject increments) for an hourObject.</p>
     * 
     * @return An Option[] containing all the minutes
     */
    private Option[] getMinuteItems() {
        Option[] minutes = new Option[13];
        
        minutes[0] = new Option(new Integer(-1), " ");//NOI18N
        minutes[1] = new Option(new Integer(0), "00");//NOI18N
        minutes[2] = new Option(new Integer(5), "05");//NOI18N
        
        for (int i = 2; i < 12; i++) {         
            minutes[i + 1] = new Option(new Integer(5 * i), String.valueOf(5*i));
        }
        
        return minutes;
    }
    
    /**
     * <p>Get the time-zone as a string.</p>
     */
    public String getOffset() {
        
        java.util.Calendar calendar = getCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        
        StringBuffer gmtTimeZone = new StringBuffer(8); 
        
        int value = calendar.get(java.util.Calendar.ZONE_OFFSET) +
                calendar.get(java.util.Calendar.DST_OFFSET);
        
        if (value < 0) {
            // GMT - hh:mm
            gmtTimeZone.append('-');//NOI18N
            value = -value;
        } else {
            // GMT + hh:mm
            gmtTimeZone.append('+');//NOI18N
        }
        
        // determine the offset hours
        int num = value / (1000 * 60 * 60);
        
        if (num < 10) {
            // display offset as GMT + 0h:mm
            gmtTimeZone.append("0");//NOI18N
        }
        
        // add the hh: part
        gmtTimeZone.append(num)
        .append(":");
        
        // determine the offset minutes
        num = (value % (1000 * 60 * 60)) / (1000 * 60);
        if (num < 10) {
            // display as hh:0m
            gmtTimeZone.append("0");//NOI18N
        }
        
        // append the minutes
        gmtTimeZone.append(num);
        
        return gmtTimeZone.toString(); 
    }
    
    /**
     * <p>Returns a new Calendar instance corresponding to the user's current
     * locale and the developer specified time zone (if any).</p>
     *
     * @return java.util.Calendar A new Calendar instance with the correct
     * locale and time zone.
     */
    public java.util.Calendar getCalendar() {
        java.util.Calendar calendar = null;
        Locale locale =
                FacesContext.getCurrentInstance().getViewRoot().getLocale();
        if(locale == null) {
            locale = locale.getDefault();
        }
        
        
        TimeZone timeZone = getTimeZone();
        
        if(timeZone == null) {
            calendar = java.util.Calendar.getInstance(locale);
        } else {
            calendar = java.util.Calendar.getInstance(timeZone, locale);
        }
        return calendar;
    }

    /**
     * Holds value of property hourTooltipKey.
     */
    private String hourTooltipKey;

    /**
     * Getter for property hourTooltipKey.
     * @return Value of property hourTooltipKey.
     */
    public String getHourTooltipKey() {

        return this.hourTooltipKey;
    }

    /**
     * Setter for property hourTooltipKey.
     * @param hourTooltipKey New value of property hourTooltipKey.
     */
    public void setHourTooltipKey(String hourTooltipKey) {

        this.hourTooltipKey = hourTooltipKey;
    }

    /**
     * Holds value of property minutesTooltipKey.
     */
    private String minutesTooltipKey;

    /**
     * Getter for property minutesTooltipKey.
     * @return Value of property minutesTooltipKey.
     */
    public String getMinutesTooltipKey() {

        return this.minutesTooltipKey;
    }

    /**
     * Setter for property minutesTooltipKey.
     * @param minutesTooltipKey New value of property minutesTooltipKey.
     */
    public void setMinutesTooltipKey(String minutesTooltipKey) {

        this.minutesTooltipKey = minutesTooltipKey;
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

        if(DEBUG) log("processDecodes"); //NOI18N
        
        if (context == null) {
            throw new NullPointerException();
        }

        // Skip processing if our rendered flag is false
        if (!isRendered()) {
            return;
        }

        setValid(true);

	// There is no public facet. Only the private facet.
	// Get the private facet but don't validate the id since
	// we want the facet that was rendered.
	//
	// The assumption is that the facets will already exist since the
	// component was rendered. And if it wasn't rendered  
	// this method should be executed.
	//
        ComponentUtilities.getPrivateFacet(this, HOUR_FACET, false).
		processDecodes(context);
	ComponentUtilities.getPrivateFacet(this, MINUTES_FACET, false).
		processDecodes(context); 
        setSubmittedValue(TIME_SUBMITTED);
         
        // There is nothing to decode other than the facets
        
        if (isImmediate()) {
            if(DEBUG) log("Time is immediate"); //NOI18N
            runValidation(context);           
        }
    }

    
     /**
     * <p>Perform the following algorithm to validate the local value of
     * this {@link UIInput}.</p>
     * 
     * @param context The {@link FacesContext} for the current request
     *
     */
    public void validate(FacesContext context) {
        
        if(DEBUG) log("validate()"); //NOI18N
        
        if (context == null) {
            throw new NullPointerException();
        }

	// There is no public facet. Only the private facet.
	// Get the private facet but don't validate the id since
	// we want the facet that was rendered. And don't call
	// getHourMenu or getMinutesMenu since we don't want to reinitialize.
	//
	// The assumption is that the facets will already exist since the
	// component was rendered. And if it wasn't rendered  
	// this method should be executed.
	//
        Object hourValue = ((EditableValueHolder)ComponentUtilities.
		getPrivateFacet(this, HOUR_FACET, false)).getValue();
        if(DEBUG) log("Hour value is " + String.valueOf(hourValue)); //NOI18N
        
        Object minuteValue = ((EditableValueHolder)ComponentUtilities.
		getPrivateFacet(this, MINUTES_FACET, false)).getValue();
        if (DEBUG) log("Minute value is " + String.valueOf(minuteValue)); //NOI18N
        
        ClockTime newValue = null;
        
	try {
	    newValue = createClockTime(hourValue, minuteValue, context); 
            if(DEBUG) log("Created ClockTime"); 
	}
	catch(ConverterException ce) {
            FacesMessage message = ce.getFacesMessage();  
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(getClientId(context), message);
            setValid(false);
            context.renderResponse();
        }	
        catch(Exception ex) {
            // TODO - message
            FacesMessage message = new FacesMessage("Invalid input");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(getClientId(context), message);
            setValid(false);
            context.renderResponse();
        }	

	// If our value is valid, store the new value, erase the
        // "submitted" value, and emit a ValueChangeEvent if appropriate
	if (isValid()) {
            if(DEBUG) log("\tComponent is valid"); //NOI18N
	    Object previous = getValue();
            setValue(newValue);
            if(DEBUG) log("\tNew value: " + String.valueOf(newValue)); //NOI18N
            setSubmittedValue(null);
            if (compareValues(previous, newValue)) {
                queueEvent(new ValueChangeEvent(this, previous, newValue));
            }
        }
    }
    
    private void runValidation(FacesContext context) {
        
        if(DEBUG) log("runValidation()"); //NOI18N
        
        try {
            validate(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
        
        if (!isValid()) {
            if(DEBUG) log("\tnot valid"); //NOI18N
            context.renderResponse();
        }
    }
    
    private ClockTime createClockTime(Object hourObject, Object minuteObject,
            FacesContext context) {
        
        if(DEBUG) log("CreateClockTime()");//NOI18N
        
        String messageKey = null;
        ClockTime time = null;
        
        if(hourObject instanceof Integer && minuteObject instanceof Integer) {
            
            if(DEBUG) log("Found integers");//NOI18N
            
            int hour = ((Integer)hourObject).intValue();
            int minute = ((Integer)minuteObject).intValue();
            
            if(hour == -1 && minute == -1) {
                if(DEBUG) log("No selections made");//NOI18N
                if(isRequired()) {
                    messageKey = "Time.required";//NOI18N
                } else {
                    return null;
                }
            }
            
            else if(hour == -1) {
                messageKey = "Time.enterHour";//NOI18N
            } else if(minute == -1) {
                messageKey = "Time.enterMinute";//NOI18N
            } else {
                time = new ClockTime();
                try {
                    if(DEBUG) log("Hour is " + hour);//NOI18N
                    if(DEBUG) log("Minute is " + minute);//NOI18N
                    time.setHour(new Integer(hour));
                    time.setMinute(new Integer(minute));
                } catch(Exception ex) {
                    if(DEBUG) {
                        ex.printStackTrace();
                    }
                    messageKey = "Time.invalidData";//NOI18N
                }
            }
        } else {
            if(isRequired()) {
                messageKey = "Time.required";//NOI18N
            } else {
                return null;
            }
            
        }
        
        if(messageKey != null) {
            if(DEBUG) log("Invalid input");//NOI18N
            String message =
                    ThemeUtilities.getTheme(context).getMessage(messageKey);
            throw new ConverterException(new FacesMessage(message));
        }
        return time;
    }
    
    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s);//NOI18N
    }

    /*
    public void setValue(Object value) {
        if(DEBUG) log("setValue(" + String.valueOf(value) + ")");//NOI18N
        Thread.dumpStack();
        super.setValue(value); 
    }

    public Object getValue() {
        Object value = super.getValue(); 
         if(DEBUG) log("getValue() ->" + String.valueOf(value));//NOI18N
        return value;
    }
     */

    // Take this from TimeRenderer. A Renderer shouldn't be
    // initializing or updating a component. Ideally the state should
    // be current before this point is reached thereby giving the
    // application an opportunity to set the state in
    // INVOKE_APPLICATION_PHASE.
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

	DropDown hourMenu = getHourMenu();
	DropDown minuteMenu = getMinutesMenu();

        // If there is no submitted value, set the values of 
        // the DropDowns to the actual value... If we have 
        // a submitted value, the DropDown will remember it 
        // so we do nothing in that case. 
        // FIXME: have to round this to the nearest five minutes!       

        if (getSubmittedValue() == null) {
            
            if (DEBUG) log("No submitted value"); //NOI18N
            Object object = getValue();
            if (DEBUG) log("Got the ClockTime");  //NOI18N
            
            ClockTime value = null;
            if (object != null && object instanceof ClockTime) {
                value = (ClockTime)object;
                if (DEBUG) log("\tValue is " + String.valueOf(value));//NOI18N
            }           
            if (value != null) {
                hourMenu.setValue(value.getHour());
            } else {
                hourMenu.setValue(new Integer(-1));
            }
            
            if (value != null) {
                minuteMenu.setValue(value.getMinute());
            } else {
                minuteMenu.setValue(new Integer(-1));
            }
        }
        else if (DEBUG) log("Found submitted value");  //NOI18N

	Theme theme = ThemeUtilities.getTheme(context);


        String key = getHourTooltipKey();
        if (key != null) {
            hourMenu.setToolTip(theme.getMessage(key));
        }    
        
        key = getMinutesTooltipKey();
        if (key != null) {
            minuteMenu.setToolTip(theme.getMessage(key));
        }

        String rendererType = getRendererType();
        if (rendererType != null) {
            getRenderer(context).encodeEnd(context, this);
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
     * <code>getHourMenu</code>. If that method returns null
     * <code>null</code> is returned.
     * </p>
     *
     * @param context The FacesContext used for the request
     * @return An abolute id suitable for the value of an HTML LABEL element's
     * <code>for</code> attribute.
     */
    public String getLabeledElementId(FacesContext context) {
        UIComponent comp = getHourMenu();
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // disabled
    @Property(name="disabled", displayName="Disabled")
    private boolean disabled = false;
    private boolean disabled_set = false;

    /**
     * <p>Standard HTML attribute which determines whether the web
     * application user can change the the value of this component.</p>
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
     * application user can change the the value of this component.</p>
     * @see #isDisabled()
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
        this.disabled_set = true;
    }

    // readOnly
    @Property(name="readOnly", displayName="Read-only")
    private boolean readOnly = false;
    private boolean readOnly_set = false;

    /**
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
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
     * rendered as text, preceded by the label if one was defined.</p>
     * @see #isReadOnly()
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        this.readOnly_set = true;
    }

    // style
    @Property(name="style", displayName="CSS Style(s)")
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

    // styleClass
    @Property(name="styleClass", displayName="CSS Style Class(es)")
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

    // tabIndex
    @Property(name="tabIndex", displayName="Tab Index")
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

    // timeZone
    @Property(name="timeZone", displayName="Time Zone")
    private java.util.TimeZone timeZone = null;

    /**
     * <p>A binding to a Time Zone instance to use for this Scheduler. If none is
     * 	specified, the Scheduler uses the default TimeZone from the Schedulers
     * 	locale.</p>
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
     * <p>A binding to a Time Zone instance to use for this Scheduler. If none is
     * 	specified, the Scheduler uses the default TimeZone from the Schedulers
     * 	locale.</p>
     * @see #getTimeZone()
     */
    public void setTimeZone(java.util.TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    // visible
    @Property(name="visible", displayName="Visible")
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
        this.disabled = ((Boolean) _values[1]).booleanValue();
        this.disabled_set = ((Boolean) _values[2]).booleanValue();
        this.readOnly = ((Boolean) _values[3]).booleanValue();
        this.readOnly_set = ((Boolean) _values[4]).booleanValue();
        this.style = (String) _values[5];
        this.styleClass = (String) _values[6];
        this.tabIndex = ((Integer) _values[7]).intValue();
        this.tabIndex_set = ((Boolean) _values[8]).booleanValue();
        this.timeZone = (java.util.TimeZone) _values[9];
        this.visible = ((Boolean) _values[10]).booleanValue();
        this.visible_set = ((Boolean) _values[11]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[12];
        _values[0] = super.saveState(_context);
        _values[1] = this.disabled ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.disabled_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.readOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.readOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.style;
        _values[6] = this.styleClass;
        _values[7] = new Integer(this.tabIndex);
        _values[8] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.timeZone;
        _values[10] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
