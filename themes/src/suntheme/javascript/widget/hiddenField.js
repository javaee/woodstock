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

@JS_NS@._dojo.provide("@JS_NS@.widget.hiddenField");

@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a hiddenField widget.
 *
 * @name @JS_NS@.widget.hiddenField
 * @extends @JS_NS@.widget._base.widgetBase
 * @class This class contains functions for the hiddenField widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} name
 * @config {String} value Value of input.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.hiddenField",
        @JS_NS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.disabled = false;
    },
    _widgetType: "hiddenField" // Required for theme properties.
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
@JS_NS@.widget.hiddenField.event =
        @JS_NS@.widget.hiddenField.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_hiddenField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_hiddenField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_hiddenField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_hiddenField_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_hiddenField_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_hiddenField_event_submit_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.hiddenField.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.name != null) { props.name = this.name; }
    if (this.value != null) { props.value = this.value; }

    return props;
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
@JS_NS@.widget.hiddenField.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.name != null) { this._domNode.name = props.name; }
    if (props.value != null) {
        // An empty string is valid.
        this._domNode.value = props.value;
    }
    if (props.disabled != null) { 
        this._domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
