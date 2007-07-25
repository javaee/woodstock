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
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the setWidgetProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.calendarField.fillInTemplate = function(props, frag) {
    // Set ids.
    if (this.id) {
        this.inlineHelpNode.id = this.id + "_pattern";
        this.linkContainer.id = this.id + "_linkContainer";
        this.calendarContainer.id = this.id + "_calendarContainer";
    }     

    // Set events.

    // Subscribe to the "dayClicked" event present in the calendar widget.
    dojo.event.topic.subscribe(webui.@THEME@.widget.calendar.day.dayPickedEvent,
        this, "dayClicked");
    // Subscribe to the "toggle" event that occurs whenever the calendar is opened.
    dojo.event.topic.subscribe(webui.@THEME@.widget.calendar.toggleCalendar.calendarOpenTopic,
        this, "toggleCalendar");

    // Set common functions.
    return webui.@THEME@.widget.calendarField.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to obtain the outermost HTML element class name.
 */
webui.@THEME@.widget.calendarField.getClassName = function() {
    // Set style for the outermost table element.
    var className = webui.@THEME@.widget.props.calendar.className;   
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
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
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.calendarField.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_calendarField_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_calendarField_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.calendarField");
        
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.calendarField.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.calendarField.refresh.endEventTopic
            });
        return true;
    }
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
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.calendarField.setWidgetProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set calendar properties.
    if (props.calendar) {            
        // Update widget/add fragment.                
        var calendarWidget = dojo.widget.byId(this.calendar.id);
        if (calendarWidget) {
            calendarWidget.setProps(props.calendar);
        } else {
            this.addFragment(this.calendarContainer, props.calendar);
        }
    }
    
    // If disabled, hide the div that contains the date format pattern help
    // and the column that holds the calendarField button.
    if (props.disabled != null) {
        var disabled = new Boolean(props.disabled).valueOf();
        webui.@THEME@.common.setVisibleElement(this.inlineHelpNode, !disabled);
        webui.@THEME@.common.setVisibleElement(this.linkContainer, !disabled);
    }   
    
    // Set date format pattern help.
    if (props.patternHelp) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.addFragment(this.inlineHelpNode, props.patternHelp);
    }

    // Set core props.
    return webui.@THEME@.widget.calendarField.superclass.setWidgetProps.call(this, props);
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
    refresh: webui.@THEME@.widget.calendarField.refresh.processEvent,
    setWidgetProps: webui.@THEME@.widget.calendarField.setWidgetProps,
    toggleCalendar: webui.@THEME@.widget.calendarField.toggleCalendar,

    // Set defaults.
    templatePath: webui.@THEME@.theme.getTemplatePath("calendarField"),
    templateString: webui.@THEME@.theme.getTemplateString("calendarField"),
    widgetType: "calendarField"
});
