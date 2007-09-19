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

dojo.provide("webui.@THEME@.widget.calendarField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.calendar");
dojo.require("webui.@THEME@.widget.textField");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.calendarField = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);    
}

/**
 * This function is called when a day link is selected from the calendar 
 * It updates the field with the value of the clicked date.
 *
 * The following Object literals are supported.
 *
 * <ul>
 *  <li>id</li>
 *  <li>date</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.calendarField.dayClicked = function(props) {
    // Check whether the calendar associated with this particular calendarField
    // broadcasted the event.
    if (props.date != null && props.id == this.calendar.id) {
        // Set the selected date on the field.
        this.domNode.setProps({value: props.date});
    }
    return false;
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.calendarField.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_calendarField_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_calendarField_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_calendarField_event_state_begin",
        endTopic: "webui_@THEME@_widget_calendarField_event_state_end"
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.calendarField.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.calendarField.superclass.fillInTemplate.call(this, props, frag);

    // Set ids.
    if (this.id) {
        this.inlineHelpNode.id = this.id + "_pattern";
        this.linkContainer.id = this.id + "_linkContainer";
        this.calendarContainer.id = this.id + "_calendarContainer";
    }     

    // Set events.

    // Subscribe to the "dayClicked" event present in the calendar widget.
    dojo.event.topic.subscribe(webui.@THEME@.widget.calendar.event.day.dayPickedTopic,
        this, "dayClicked");
    // Subscribe to the "toggle" event that occurs whenever the calendar is opened.
    dojo.event.topic.subscribe(webui.@THEME@.widget.calendar.event.toggle.openTopic,
        this, "toggleCalendar");

    return true;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.calendarField.getClassName = function() {
    // Set default style.
    var className = this.widget.getClassName("CALENDAR_ROOT_TABLE","");

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.calendarField.getProps = function() {
    var props = webui.@THEME@.widget.calendarField.superclass.getProps.call(this);

    // Set properties.  
    if (this.align) { props.align = this.align; }
    if (this.calendar) { props.calendar = this.calendar; }  
    if (this.patternHelp) { props.patternHelp = this.patternHelp; }   

    return props;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>align</li>
 *  <li>calendar</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li> 
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>notify</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onFocus</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>patternHelp</li>
 *  <li>readOnly</li>
 *  <li>required</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>valid</li>
 *  <li>value</li>
 *  <li>visible</li> 
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.calendarField._setProps = function(props) {
    if (props == null) {
        return false;
    }

    if (props.disabled != null) { this.disabled = new Boolean(props.disabled).valueOf(); }

    if (props.calendar || props.disabled != null) {
        // Ensure property exists so we can call setProps just once.
        if (props.calendar == null) {
            props.calendar = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.calendar.id = this.calendar.id; // Required for updateFragment().
        props.calendar.disabled = this.disabled;
        
        // Update/add fragment.
        this.widget.updateFragment(this.calendarContainer, props.calendar); 
    }
    
    // Set date format pattern help.
    if (props.patternHelp) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.widget.addFragment(this.inlineHelpNode, props.patternHelp);
    }

    // Set remaining properties.
    return webui.@THEME@.widget.calendarField.superclass._setProps.call(this, props);
}

/**
 * This function subscribes to the toggleCalendar function of the calendar widget.
 * Whenever the calendar is opened, it updates the value of the calendar with
 * the value present in the field.
 * 
 * The following Object literals are supported.
 *
 * <ul>
 *  <li>id</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.calendarField.toggleCalendar = function(props) {   
    if (props.id != null && props.id == this.calendar.id) {
        var widget = dojo.widget.byId(props.id);
        widget.setProps({date: this.getProps().value});
    }
    return true;
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.calendarField, webui.@THEME@.widget.textField);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.calendarField, {
    // Set private functions.    
    dayClicked: webui.@THEME@.widget.calendarField.dayClicked,
    fillInTemplate: webui.@THEME@.widget.calendarField.fillInTemplate,
    getClassName: webui.@THEME@.widget.calendarField.getClassName,
    getProps: webui.@THEME@.widget.calendarField.getProps,
    _setProps: webui.@THEME@.widget.calendarField._setProps,
    toggleCalendar: webui.@THEME@.widget.calendarField.toggleCalendar,

    // Set defaults.
    event: webui.@THEME@.widget.calendarField.event,
    widgetType: "calendarField"
});
