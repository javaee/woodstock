// widget/calendarField.js
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

dojo.require("webui.@THEME@.widget.calendar");
dojo.require("webui.@THEME@.widget.textField");

/**
 * @name webui.@THEME@.widget.calendarField
 * @extends webui.@THEME@.widget.textField
 * @class This class contains functions for the calendarField widget.
 * @constructor This function is used to construct a calendarField widget.
 */
dojo.declare("webui.@THEME@.widget.calendarField", webui.@THEME@.widget.textField, {
    // Set defaults.
    widgetName: "calendarField" // Required for theme properties.
});

/**
 * This function is called when a day link is selected from the calendar.
 * It updates the field with the value of the clicked date.
 *
 * @param props Key-Value pairs of properties.
 * @config {String} [id] 
 * @config {String} [date]
 * @return {boolean} false to cancel JavaScript event.
 */
webui.@THEME@.widget.calendarField.prototype.dayClicked = function(props) {
    // Check whether the calendar associated with this particular calendarField
    // broadcasted the event.
    if (props.date != null && props.id == this.calendar.id) {
        // Set the selected date on the field.
        this.domNode.setProps({value: props.date});
    }
    return false;
}

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME@.widget.calendarField.event =
        webui.@THEME@.widget.calendarField.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_calendarField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_calendarField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_calendarField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_calendarField_event_state_end"
    }
}

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME@.widget.calendarField.prototype.getClassName = function() {
    // Set default style.
    var className = this.widget.getClassName("CALENDAR_ROOT_TABLE","");

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.calendarField.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.  
    if (this.align) { props.align = this.align; }
    if (this.calendar) { props.calendar = this.calendar; }  
    if (this.patternHelp) { props.patternHelp = this.patternHelp; }   

    return props;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.calendarField.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.inlineHelpNode.id = this.id + "_pattern";
        this.linkContainer.id = this.id + "_linkContainer";
        this.calendarContainer.id = this.id + "_calendarContainer";
    }     

    // Set events.

    // Subscribe to the "dayClicked" event present in the calendar widget.
    dojo.subscribe(webui.@THEME@.widget.calendar.event.day.selectedTopic,
        this, "dayClicked");
    // Subscribe to the "toggle" event that occurs whenever the calendar is opened.
    dojo.subscribe(webui.@THEME@.widget.calendar.event.toggle.openTopic,
        this, "toggleCalendar");

    return this.inherited("postCreate", arguments);
}

/**
 * This function is used to set widget properties using Object literals.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} [alt] Alternate text for image input.
 * @config {String} [align] Alignment of image input.
 * @config {Object} [calendar] 
 * @config {String} [className] CSS selector.
 * @config {String} [dir] Specifies the directionality of text.
 * @config {boolean} [disabled] Disable element.
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {String} [label]
 * @config {String} [lang] Specifies the language of attribute values and content.
 * @config {Array} [notify] 
 * @config {String} [onBlur] Element lost focus.
 * @config {String} [onClick] Mouse button is clicked on element.
 * @config {String} [onDblClick] Mouse button is double-clicked on element.
 * @config {String} [onFocus] Element received focus.
 * @config {String} [onKeyDown] Key is pressed down over element.
 * @config {String} [onKeyPress] Key is pressed and released over element.
 * @config {String} [onKeyUp] Key is released over element.
 * @config {String} [onMouseDown] Mouse button is pressed over element.
 * @config {String} [onMouseOut] Mouse is moved away from element.
 * @config {String} [onMouseOver] Mouse is moved onto element.
 * @config {String} [onMouseUp] Mouse button is released over element.
 * @config {String} [onMouseMove] Mouse is moved while over element.
 * @config {String} [patternHelp] 
 * @config {boolean} [readOnly] 
 * @config {boolean} [required] 
 * @config {String} [style] Specify style rules inline.
 * @config {int} [tabIndex] Position in tabbing order.
 * @config {String} [title] Provides a title for element.
 * @config {boolean} [valid] 
 * @config {String} [value] Value of input.
 * @config {boolean} [visible] Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.calendarField.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // If the popup calendar is visible, prevent disabling of the calendar.
    // The widget can only be disabled if the popup calendar is not visible.
    if (props.disabled != null) { 
        var widget = dijit.byId(this.calendar.id); 
        if (widget != null && !(widget.calendarContainer.style.display != "block")) {
            props.disabled = this.disabled;
        }        
    }
    
    // Set remaining properties.
    return this.inherited("setProps", arguments);
}

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This is considered a private API, do not use. This function should only
 * be invoked via setProps().
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.calendarField.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set disabled.
    if (props.disabled != null) { this.disabled = new Boolean(props.disabled).valueOf(); }

    // Set calendar.
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
    return this.inherited("_setProps", arguments);
}

/**
 * This function subscribes to the toggleCalendar function of the calendar widget.
 * Whenever the calendar is opened, it updates the value of the calendar with
 * the value present in the field.
 * 
 * @param props Key-Value pairs of properties.
 * @config {String} [id]
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.calendarField.prototype.toggleCalendar = function(props) {   
    if (props.id != null && props.id == this.calendar.id) {
        var widget = dijit.byId(props.id);
        widget.setProps({date: this.getProps().value});
    }
    return true;
}
