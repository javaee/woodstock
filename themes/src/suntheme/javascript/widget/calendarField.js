/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

@JS_NS@._dojo.provide("@JS_NS@.widget.calendarField");

@JS_NS@._dojo.require("@JS_NS@.widget.calendar");
@JS_NS@._dojo.require("@JS_NS@.widget.textField");

/**
 * This function is used to construct a calendarField widget.
 *
 * @constructor
 * @name @JS_NS@.widget.calendarField
 * @extends @JS_NS@.widget.textField
 * @class This class contains functions for the calendarField widget.
 * <p>
 * The calendarField widget displays a text field that expects a date as input, 
 * together with an icon that displays a small calendar when clicked. The user
 * can either type directly into the text field or select a date from the 
 * calendar display.
 * </p><p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "calField1",
 *       label: { value: "Start Date: " },
 *       size: 20,
 *       widgetType: "calendarField",
 *       calendar: {
 *         dateFormat: "MM/dd/yyyy",
 *         id: "calField1_cal",
 *         maxDate: "05/12/2108",
 *         minDate: "05/12/1908",
 *         todayDateMsg: "Today: May 12, 2008",
 *         widgetType: "calendar"
 *       }
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * calendar field is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "calField1",
 *       label: { value: "Start Date: " },
 *       size: 20,
 *       widgetType: "calendarField",
 *       calendar: {
 *         dateFormat: "MM/dd/yyyy",
 *         id: "calField1_cal",
 *         maxDate: "05/12/2108",
 *         minDate: "05/12/1908",
 *         todayDateMsg: "Today: May 12, 2008",
 *         widgetType: "calendar"
 *       }
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Calendar State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("calField1"); // Get calendar field
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the calendar field is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "calField1",
 *       label: { value: "Start Date: " },
 *       size: 20,
 *       widgetType: "calendarField",
 *       calendar: {
 *         dateFormat: "MM/dd/yyyy",
 *         id: "calField1_cal",
 *         maxDate: "05/12/2108",
 *         minDate: "05/12/1908",
 *         todayDateMsg: "Today: May 12, 2008",
 *         widgetType: "calendar"
 *       }
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Calendar" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("calField1"); // Get calendar field
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can take an optional list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,..."). When no parameter is given, 
 * the refresh function acts as a reset. That is, the widget will be redrawn 
 * using values set server-side, but not updated.
 * </p><p>
 * <h3>Example 3b: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a calendar field using the 
 * refresh function. The execute property of the refresh function is used to 
 * define the client id which is to be submitted and updated server-side. As the
 * user types in the text field, the input value is updated server-side and the 
 * calendar field label is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "calField1",
 *       label: { value: "Start Date: " },
 *       size: 20,
 *       widgetType: "calendarField",
 *       calendar: {
 *         dateFormat: "MM/dd/yyyy",
 *         id: "calField1_cal",
 *         maxDate: "05/12/2108",
 *         minDate: "05/12/1908",
 *         todayDateMsg: "Today: May 12, 2008",
 *         widgetType: "calendar"
 *       }
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Calendar Label" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("calField1"); // Get calendar field
 *       return widget.refresh("field1"); // Asynchronously refresh while submitting field value
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can optionally take a list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,...").
 * </p><p>
 * <h3>Example 4: Subscribing to event topics</h3>
 * </p><p>
 * When a widget is manipulated, some features may publish event topics for
 * custom AJAX implementations to listen for. For example, you may listen for
 * the refresh event topic using:
 * </p><pre><code>
 * &lt;script type="text/javascript">
 *    var foo = {
 *        // Process refresh event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        processRefreshEvent: function(props) {
 *            // Get the widget id.
 *            if (props.id == "calField1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.calendarField.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} alt Alternate text for image input.
 * @config {String} align Alignment of image input.
 * @config {Object} calendar 
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {Array} notify 
 * @config {String} onBlur Element lost focus.
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onFocus Element received focus.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {String} patternHelp 
 * @config {boolean} readOnly 
 * @config {boolean} required 
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} valid 
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.calendarField",
        @JS_NS@.widget.textField, {
    // Set defaults.
    _widgetType: "calendarField" // Required for theme properties.
});

/**
 * This function is called when a day link is selected from the calendar.
 * It updates the field with the value of the clicked date.
 *
 * @param props Key-Value pairs of properties.
 * @config {String} id 
 * @config {String} date
 * @return {boolean} false to cancel JavaScript event.
 * @private
 */
@JS_NS@.widget.calendarField.prototype._dayClicked = function(props) {
    // Check whether the calendar associated with this particular calendarField
    // broadcasted the event.
    if (props.date != null && props.id == this.calendar.id) {
        // Set the selected date on the field.
        this._domNode.setProps({value: props.date});
    }
    return false;
};

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.calendarField.event =
        @JS_NS@.widget.calendarField.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_calendarField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_calendarField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_calendarField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_calendarField_event_state_end"
    }
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
@JS_NS@.widget.calendarField.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);

    // Set default style.
    var newClassName = this._theme.getClassName("CALENDAR_ROOT","");

    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.calendarField.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.  
    if (this.align != null) { props.align = this.align; }
    if (this.calendar != null) { props.calendar = this.calendar; }  
    if (this.patternHelp != null) { props.patternHelp = this.patternHelp; }   

    return props;
};

/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendarField.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._inlineHelpNode.id = this.id + "_pattern";
        this._linkContainer.id = this.id + "_linkContainer";
        this._calendarContainer.id = this.id + "_calendarContainer";
    }     

    // If a patternHelp is not specified by the developer
    // try to get one from the themes. 
    if (this.patternHelp == null) {
        
        var pattern;
        if (this.calendar != null && this.calendar.dateFormat != null) {
            pattern = this.calendar.dateFormat;
        } else {        
            pattern = this._theme.getMessage("calendar.dateFormat");
        }
        var help = this._theme.getMessage("calendar." + pattern);
        if (help != null) {
            this.patternHelp = help;
        } 
    }
    // Set events.

    // Subscribe to the "day clicked" event present in the calendar widget.
    this._widget.subscribe(@JS_NS@.widget.calendar.event.day.selectedTopic,
        this, "_dayClicked");
    // Subscribe to the "toggle" event that occurs whenever the calendar is opened.
    this._widget.subscribe(@JS_NS@.widget.calendar.event.toggle.openTopic,
        this, "_toggleCalendar");
        
    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to set widget properties using Object literals. Please
 * see the constructor detail for a list of supported properties.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.calendarField.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // If the popup calendar is visible, prevent disabling of the calendar.
    // The widget can only be disabled if the popup calendar is not visible.
    if (props.disabled != null) { 
        var widget = this._widget.getWidget(this.calendar.id); 
        if (widget != null && !(widget._calendarContainer.style.display != "block")) {
            props.disabled = this.disabled;
        }        
    }
    
    // Set remaining properties.
    return this._inherited("setProps", arguments);
};

/**
 * This function is used to set widget properties. Please see the constructor 
 * detail for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendarField.prototype._setProps = function(props) {
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
        props.calendar.disabled = this.disabled;
        
        // Update/add fragment.
        this._widget._updateFragment(this._calendarContainer, this.calendar.id,
            props.calendar); 
    }
    
    // Set date format pattern help.
    if (props.patternHelp) {                            
        // NOTE: If you set this value manually, text must be HTML escaped.
        this._widget._addFragment(this._inlineHelpNode, props.patternHelp);
    }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendarField.prototype._startup = function () {
    if (this._started == true) {
        return false;
    }

    // Adjust the size of the inline help text so that it fits to the
    // size of the text field.
    //
    // Note: Cannot do this in the _postCreate or setProps as nodes have not
    // been added to the DOM. So, offsetWidth would return zero. 
    var width = this._fieldNode.offsetWidth;
    this._inlineHelpNode.style.cssText = "width:" + width + "px;";

    return this._inherited("_startup", arguments);
};

/**
 * This function is used with the _toggleCalendar function of the calendar widget.
 * Whenever the calendar is opened, it updates the value of the calendar with
 * the value present in the field.
 * 
 * @param props Key-Value pairs of properties.
 * @config {String} id
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.calendarField.prototype._toggleCalendar = function(props) {   
    if (props.id != null && props.id == this.calendar.id) {
        var widget = this._widget.getWidget(props.id);
        widget.setProps({date: this.getProps().value});
    }
    return true;
};
