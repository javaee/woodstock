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

@JS_NS@._dojo.provide("@JS_NS@.widget.checkbox");

@JS_NS@._dojo.require("@JS_NS@.widget._base.checkedBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.submitBase");

/**
 * This function is used to construct a checkbox widget.
 *
 * @constructor
 * @name @JS_NS@.widget.checkbox
 * @extends @JS_NS@.widget._base.checkedBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.submitBase
 * @class This class contains functions for the checkbox widget.
 * <p>
 * The widget can be used as a single checkbox or as one checkbox among a group
 * of checkboxes. A group of checkboxes represents a multiple selection list 
 * which can have any number of checkboxes selected, or none selected.
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
 *       id: "cb1",
 *       label: { value: "This is a checkbox" },
 *       widgetType: "checkbox"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the radio button, the
 * checkbox is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "b1",
 *       label: { value: "This is a checkbox" },
 *       widgetType: "checkbox"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "rb1",
 *       label: { value: "Toggle Checkbox State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "radioButton"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("cb1"); // Get checkbox
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the radio button, the checkbox is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "cb1",
 *       label: { value: "This is a checkbox" },
 *       widgetType: "checkbox"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "rb1",
 *       label: { value: "Refresh Checkbox" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "radioButton"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("cb1"); // Get checkbox
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
 * This example shows how to asynchronously update a checkbox using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the checkbox 
 * label is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "cb1",
 *       label: { value: "This is a checkbox" },
 *       widgetType: "checkbox"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Checkbox Label" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("cb1"); // Get checkbox
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
 *            if (props.id == "cb1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.checkbox.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} alt Alternate text for image input.
 * @config {String} align Alignment of image input.
 * @config {boolean} checked 
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {Object} image 
 * @config {String} label 
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} name 
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
 * @config {String} onSelect 
 * @config {boolean} readOnly 
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.checkbox", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.submitBase,
        @JS_NS@.widget._base.checkedBase ], {
    // Set defaults.
    _idSuffix: "_cb",
    _widgetType: "checkbox" // Required for theme properties.
});

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.checkbox.event =
        @JS_NS@.widget.checkbox.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_checkbox_event_refresh_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_checkbox_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_checkbox_event_state_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_checkbox_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_checkbox_event_submit_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_checkbox_event_submit_end"
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
@JS_NS@.widget.checkbox.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);

    // Set default style.
    var newClassName = (this.disabled == true)
        ? this._theme.getClassName("CHECKBOX_SPAN_DISABLED", "")
        : this._theme.getClassName("CHECKBOX_SPAN", ""); 

    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * Helper function to obtain image class names.
 *
 * @return {String} The HTML image element class name.
 * @private
 */
@JS_NS@.widget.checkbox.prototype._getImageClassName = function() {
    return (this.disabled == true)
        ? this._theme.getClassName("CHECKBOX_IMAGE_DISABLED", "")
        : this._theme.getClassName("CHECKBOX_IMAGE", "");  
};

/**
 * Helper function to obtain input class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
@JS_NS@.widget.checkbox.prototype._getInputClassName = function() {
    // readOnly style.
    if (this.readOnly == true) {
        return this._theme.getClassName("CHECKBOX_READONLY", "");        
    }

    // disabled style.
    return (this.disabled == true)
        ? this._theme.getClassName("CHECKBOX_DISABLED", "")
        : this._theme.getClassName("CHECKBOX", "");  
};

/**
 * Helper function to obtain label class names.
 *
 * @return {String} The HTML label element class name.
 * @private
 */
@JS_NS@.widget.checkbox.prototype._getLabelDisabledClassName = function(disabled) {
    return (disabled == true)
        ? this._theme.getClassName("CHECKBOX_LABEL_DISABLED", "")
        : this._theme.getClassName("CHECKBOX_LABEL", "");  
};

/**
 * Return an Object Literal of label properties desired
 * by the checkbox widget.
 * <p>
 * This implementation sets
 * the <code>checkbox.labelLevel</code> 
 * theme values from the <code>messages</code> and <code>styles</code>
 * theme categories to the
 * label's <code>level</code> and <code>className</code> properties 
 * respectively.
 * </p>
 * <p>
 * These properties are extended with <code>this.label</code> and the
 * resulting properties are returned.
 * </p>
 * @return {Object} label properties.
 * @private
 */
@JS_NS@.widget.checkbox.prototype._getLabelProps = function() {

    // First see if the super class wants to contribute to the props.
    // Let selectBase add the htmlFor property
    //
    var props = this._inherited("_getLabelProps", arguments);
    props.level = this._theme.getMessage("checkbox.labelLevel", null, 3);
    return props;
};
