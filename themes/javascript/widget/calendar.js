//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.widget.calendar");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.calendar = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);    
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
 webui.@THEME@.widget.calendar.fillInTemplate = function() {
     // Set ids.
     if (this.id) {
         this.labelNode.id = this.id + "_labelNode";
         this.fieldNode.id = this.id + "_fieldNode";         
         this.inlineHelpNode.id = this.id + "_pattern";
         this.linkContainer.id = this.id + "_linkContainer";
         this.linkNode.id = this.id + "_linkNode";         
         this.calendarMonthContainer.id = this.id + "_calendarMonthContainer";
         this.calendarMonthNode.id = this.id + "_calendarMonthNode";         
     }

     // Set public functions.        
     this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
     this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }    
     this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }     
       
     // Set properties.
     return this.setProps();        
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.calendar.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_calendar_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_calendar_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.calendar");
        
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.calendar.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.calendar.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>align</li>
 *  <li>calendarMonth</li>
 *  <li>className</li>
 *  <li>dateFormat</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>field</li>
 *  <li>id</li> 
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>link</li>
 *  <li>patternHelp</li>
 *  <li>readOnly</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.calendar.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();

    // Set DOM node properties.    
    this.setCoreProps(this.domNode, props);               
    this.setCommonProps(this.domNode, props);
        
    // Set label properties.    
    if (props.label) {                       
        // Update widget/add fragment.                
        var labelWidget = dojo.widget.byId(this.label.id);
        if (labelWidget) {
            labelWidget.setProps(props.label);          
        } else {
            this.addFragment(this.labelNode, props.label);            
            // Show the column that holds the label widget.
            webui.@THEME@.common.setVisibleElement(this.labelColumn, true);
        }    
    }        
    
    // Set field properties.    
    if (props.field || (props.disabled != null || props.readOnly != null
            || props.tabIndex != null) && this.field) {
        // Ensure property exists so we can call setProps just once.
        if (props.field == null) {
            props.field = {};
        }
                
        if (props.disabled != null) {
            props.field.disabled = new Boolean(props.disabled).valueOf();
        }
        if (props.tabIndex > -1 && props.tabIndex < 32767) {
            props.field.tabIndex = props.tabIndex;            
        }
        if (props.readOnly != null) {
            props.field.readOnly = new Boolean(props.readOnly).valueOf();
        }
        
        // Update widget/add fragment.                
        var fieldWidget = dojo.widget.byId(this.field.id);
        if (fieldWidget) {
            fieldWidget.setProps(props.field);
        } else {
            this.addFragment(this.fieldNode, props.field);
        }
    }
    
    // Set link properties.
    if (props.link || props.disabled != null && this.link) {
        // Ensure property exists so we can call setProps just once.
        if (props.link == null) {
            props.link = {};
        }               
             
        if (props.disabled != null) {
            props.link.disabled = new Boolean(props.disabled).valueOf();
        }
        
        // Update widget/add fragment.                
        var linkWidget = dojo.widget.byId(this.link.id);
        if (linkWidget) {
            linkWidget.setProps(props.link);
        } else {
            this.addFragment(this.linkNode, props.link);
        }
    }    
    
    // Set date picker properties.    
    if (props.calendarMonth) {            
        // Update widget/add fragment.                
        var calendarMonthWidget = dojo.widget.byId(this.calendarMonth.id);
        if (calendarMonthWidget) {
            calendarMonthWidget.setProps(props.calendarMonth);
        } else {
            this.addFragment(this.calendarMonthNode, props.calendarMonth);
        }
    }
    
    // If disabled, hide the div that contains the date format pattern help
    // and the column that holds the calendar button.
    if (props.disabled != null) {
        var disabled = new Boolean(props.disabled).valueOf();
        webui.@THEME@.common.setVisibleElement(this.inlineHelpNode, !disabled);
        webui.@THEME@.common.setVisibleElement(this.linkContainer, !disabled);
    }   
    
    // Set date format pattern help.
    if (props.patternHelp) {
        this.inlineHelpNode.innerHTML = props.patternHelp;        
    }    
    // Set date format
    if (props.dateFormat) {
        this.dateFormat = props.dateFormat;
    }       
        
    return props; // Return props for subclasses.    
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.calendar.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.calendar.getProps = function() {
    var props = {};

    // Set properties.  
    if (this.align) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.label) { props.label = this.label; }
    if (this.field) { props.field = this.field; }    
    if (this.link) { props.link = this.link; }    
    if (this.calendarMonth) { props.calendarMonth = this.calendarMonth; }  
    if (this.patternHelp) { props.patternHelp = this.patternHelp; }   
    if (this.dateFormat) { props.dateFormat = this.dateFormat; }   

    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());

    return props;
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.calendar.getClassName = function() {
    // Set style for the outermost table element.
    var className = webui.@THEME@.widget.props.calendar.className;   
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to toggle the calendar month.
 */
webui.@THEME@.widget.calendar.toggleCalendarMonth = function() {   
    if(this.calendarMonthContainer.style.display == "block") {
        // Hide the calendar popup
        this.calendarMonthContainer.style.display = "none";
    } else {
        this.setCurrentValue();                
        this.updateCalendarMonth(true);                
        // Display the calendar popup
        this.calendarMonthContainer.style.display = "block";        
        // Place focus on the month menu
        this.setInitialFocus();
    }
}

/**
 * This function is used to set the current value by parsing the field value.
 */
webui.@THEME@.widget.calendar.setCurrentValue = function() {   
    var field = document.getElementById(this.field.id).getInputElement();    
    var curDate = field.value;
    if (curDate == "") {
        this.currentValue = null;
        return;
    }

    var pattern = new String(this.dateFormat);
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    var dayIndex = pattern.indexOf("dd");

    // If the format is invalid, set the current value to null
    if (yearIndex < 0 || monthIndex < 0 || dayIndex < 0) {
        this.currentValue = null;
        return;
    } 
    
    var counter = 0;
    var number;
    var selectedDate = new Date();
    var found = 0;
    var dateString;

    while (counter < curDate.length) {
        if (counter == yearIndex) {
            try {
                number = parseInt(curDate.substr(counter, 4));
                if (isNaN(number)) {
                    this.currentValue = null;
                    return;
                }                
                var index = 0;
                var foundYear = false;
                yearMenu = document.getElementById(this.calendarMonth.yearMenu.id).getSelectElement();
                while (index < yearMenu.length) {
                    if (number == yearMenu.options[index].value) {
                        selectedDate.setFullYear(number);
                        ++found;
                        foundYear = true;
                        break;
                    }
                    index++;
                }
                if (!foundYear) {
                    break;
                }                
            } catch(e) {}
        } else if (counter == monthIndex) {
            try {    
                dateString = curDate.substr(counter, 2);
                // This is a workaround for Firefox! 
                // parseInt() returns 0 for values 08 and 09
                // while all other leading zeros work.
                if (dateString.charAt(0) == '0') {
                    dateString = dateString.substr(1, 1);
                }
                number = parseInt(dateString);
                if (isNaN(number)) {
                    this.currentValue = null;
                    return;
                }
                selectedDate.setMonth(number-1);
                ++found;
            } catch(e) {}
        } else if (counter == dayIndex) {
            try {
                dateString = curDate.substr(counter, 2);
                // This is a workaround for Firefox! 
                // parseInt() returns 0 for values 08 and 09
                // while all other leading zeros work.
                if (dateString.charAt(0) == '0') {
                    dateString = dateString.substr(1, 1);
                }
                number = parseInt(dateString);
                if (isNaN(number)) {
                    this.currentValue = null;
                    return;
                }
                selectedDate.setDate(number);
                ++found;
            } catch(e) {}
        }
        ++counter;
    }

    if (found == 3) {
        this.currentValue = selectedDate;
    } else {
        this.currentValue = null;
    }    
    return;       
}

/**
 * This function is used to update the calendar month display.
 *
 * @param initialize Flag indicating to initialze the year and month menus
 * with the current value. The value is true only when the calendar is opened. 
 */
webui.@THEME@.widget.calendar.updateCalendarMonth = function(initialize) {
    // Call updateMonth on the calendar month widget to update the display.
    var widget = dojo.widget.byId(this.calendarMonth.id);
    widget.updateMonth(this.currentValue, initialize);     
}

/**
 * Helper function to set the initial focus on the menus.
 */
webui.@THEME@.widget.calendar.setInitialFocus = function() {    
    var pattern = new String(this.dateFormat);
    var yearIndex = pattern.indexOf("yyyy");
    var monthIndex = pattern.indexOf("MM");
    
    // Moving the year menu around based on the date format is not supported yet.
    // So, the code for setting focus on the year menu has been commented out.
    // if(yearIndex < monthIndex) {        
    //    var yearMenu = document.getElementById(this.calendarMonth.yearMenu.id).getSelectElement();
    //    yearMenu.focus();                 
    // } else {
        var monthMenu = document.getElementById(this.calendarMonth.monthMenu.id).getSelectElement();
        monthMenu.focus();
    // }
}

/**
 * This function is used to increase the month by one.
 */
webui.@THEME@.widget.calendar.increaseMonth = function() {            
    var monthMenu = document.getElementById(this.calendarMonth.monthMenu.id).getSelectElement();
    
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE. 
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 12) {
        var yearMenu = document.getElementById(this.calendarMonth.yearMenu.id).getSelectElement();
        var numOptions = yearMenu.options.length;
        if (yearMenu.value == null) {
            // If the yearMenu has no value, set it to the first available year            
            // (that's what it will have appeared like in the browser). Can happen on IE.
            yearMenu.value = yearMenu.options[0].value;
        } else if (yearMenu.value == yearMenu.options[numOptions-1].value) {
            // No need to update the calendar in this case,
            // we don't change anything.
            return;            
        } else {
            // Increase the year by one and set the month to January.
            var year = parseInt(yearMenu.value);
            year++;
            yearMenu.value = year;
            month = 1;
        }
    } else {
        month++;
    }
    monthMenu.value = month;
    
    this.updateCalendarMonth(false);    
}

/**
 * This function is used to decrease the month by one.
 */
webui.@THEME@.widget.calendar.decreaseMonth = function() {
    var monthMenu = document.getElementById(this.calendarMonth.monthMenu.id).getSelectElement();
    // If the monthMenu has no value, set it to January (that's what
    // it will have appeared like in the browser). Can happen on IE.  
    if (monthMenu.value == null) {
        monthMenu.value = monthMenu.options[0].value;
    }
    
    var month = parseInt(monthMenu.value);
    if (month == 1) {
        var yearMenu = document.getElementById(this.calendarMonth.yearMenu.id).getSelectElement();        
         if (yearMenu.value == null) {
             // If the yearMenu has no value, set it to the first available year            
             // (that's what it will have appeared like in the browser). Can happen on IE.
             yearMenu.value = yearMenu.options[0].value;
         } else if (yearMenu.value == yearMenu.options[0].value) {
             // No need to update the calendar in this case,
             // we don't change anything.
             return;           
         } else {
             // Decrease the year by one and set the month to December
             var year = parseInt(yearMenu.value);
             year--;
             yearMenu.value = year;
             month = 12;
         }
    } else {
        month--;
    }
    monthMenu.value = month;
    
    this.updateCalendarMonth(false);
}

/**
 * This function is called when a day link is selected from the calendar month.
 */
webui.@THEME@.widget.calendar.dayClicked = function(date) {
    // Set the selected date on the field.
    var field = document.getElementById(this.field.id).getInputElement();   
    field.value = date;
    this.toggleCalendarMonth();
    
    return false;
}
    
// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.calendar, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.calendar, {
    // Set private functions.    
    fillInTemplate: webui.@THEME@.widget.calendar.fillInTemplate,
    getClassName: webui.@THEME@.widget.calendar.getClassName,
    getProps: webui.@THEME@.widget.calendar.getProps,    
    setProps: webui.@THEME@.widget.calendar.setProps,
    refresh: webui.@THEME@.widget.calendar.refresh.processEvent,
    toggleCalendarMonth: webui.@THEME@.widget.calendar.toggleCalendarMonth,
    decreaseMonth: webui.@THEME@.widget.calendar.decreaseMonth,
    increaseMonth: webui.@THEME@.widget.calendar.increaseMonth,
    updateCalendarMonth: webui.@THEME@.widget.calendar.updateCalendarMonth,    
    setInitialFocus: webui.@THEME@.widget.calendar.setInitialFocus,
    dayClicked: webui.@THEME@.widget.calendar.dayClicked,
    setCurrentValue: webui.@THEME@.widget.calendar.setCurrentValue,    

    // Set defaults.
    templateString: webui.@THEME@.theme.getTemplateString("calendar"),
    widgetType: "calendar"
});
