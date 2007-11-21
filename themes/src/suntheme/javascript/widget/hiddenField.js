// widget/hiddenField.js
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

dojo.provide("webui.@THEME@.widget.hiddenField");

dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.hiddenField
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for the hiddenField widget.
 * @constructor This function is used to construct a hiddenField widget.
 */
dojo.declare("webui.@THEME@.widget.hiddenField", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    disabled: false,
    widgetName: "hiddenField" // Required for theme properties.
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
webui.@THEME@.widget.hiddenField.event =
        webui.@THEME@.widget.hiddenField.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_hiddenField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_hiddenField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_hiddenField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_hiddenField_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_hiddenField_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_hiddenField_event_submit_end"
    }
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.hiddenField.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.name) { props.name = this.name; }
    if (this.value) { props.value = this.value; }

    return props;
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
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} name
 * @config {String} value Value of input.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.hiddenField.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
    return this.inherited("setProps", arguments);
}

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.hiddenField.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.name) { this.domNode.name = props.name; }
    if (props.value) { this.domNode.value = props.value; }
    if (props.disabled != null) { 
        this.domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
