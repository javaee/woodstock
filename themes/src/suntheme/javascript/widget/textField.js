// widget/textField.js
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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.textField");

webui.@THEME@.dojo.require("webui.@THEME@.widget.fieldBase");

/**
 * @name webui.@THEME@.widget.textField
 * @extends webui.@THEME@.widget.fieldBase
 * @class This class contains functions for the textField widget.
 * @constructor This function is used to construct a textField widget.
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.textField", webui.@THEME@.widget.fieldBase, {
    // Set defaults.
    widgetName: "textField" // Required for theme properties.
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
webui.@THEME@.widget.textField.event =
        webui.@THEME@.widget.textField.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_textField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_textField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_textField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_textField_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_textField_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_textField_event_submit_end"
    },

    /**
     * This object contains validation event topics.
     * @ignore
     */
    validation: {
        /** Validation event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_textField_event_validation_begin",

        /** Validation event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_textField_event_validation_end"
    }
}

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 */
webui.@THEME@.widget.textField.prototype.getInputClassName = function() {          
    // Set readOnly style.
    if (this.fieldNode.readOnly) {
        return this.widget.getClassName("TEXT_FIELD_READONLY", "");
    }

    // Apply invalid style.
    var validStyle =  (this.valid == false) 
        ? " " + this.widget.getClassName("TEXT_FIELD_INVALID", "")
        : " " + this.widget.getClassName("TEXT_FIELD_VALID", "");
    
    // Set default style.    
    return (this.disabled == true)
        ? this.widget.getClassName("TEXT_FIELD_DISABLED", "") 
        : this.widget.getClassName("TEXT_FIELD", "") + validStyle;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.textField.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.autoValidate != null) { props.autoValidate = this.autoValidate; }
    
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
webui.@THEME@.widget.textField.prototype.postCreate = function () {
    // Set events.
    if (this.autoValidate == true) {
        // Generate the following event ONLY when 'autoValidate' == true.
        this.dojo.connect(this.fieldNode, "onblur", this, "validate");
    }
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
 * @config {String} accesskey 
 * @config {boolean} autoValidate
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {int} maxLength 
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
 * @config {boolean} readOnly 
 * @config {boolean} required 
 * @config {int} size 
 * @config {String} style Specify style rules inline.
 * @config {boolean} submitForm
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} valid
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.textField.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
    return this.inherited("setProps", arguments);
}

/**
 * Process validation event.
 * <p>
 * This function interprets an event (one of onXXX events, such as onBlur,
 * etc) and extracts data needed for subsequent Ajax request generation. 
 * Specifically, the widget id that has generated the event. If widget
 * id is found, publishBeginEvent is called with extracted data.
 * </p>
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.textField.prototype.validate = function(event) {
    if (event == null) {
        return false;
    }
    // Publish an event for custom AJAX implementations to listen for.
    this.publish(webui.@THEME@.widget.textField.event.validation.beginTopic, [{
        id: this.id
    }]);
    return true;
}
