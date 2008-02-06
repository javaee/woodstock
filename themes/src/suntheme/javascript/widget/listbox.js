// widget/listbox.js
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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.listbox");

webui.@THEME@.dojo.require("webui.@THEME@.widget.selectBase");

/**
 * @name webui.@THEME@.widget.listbox
 * @extends webui.@THEME@.widget.selectBase
 * @class This class contains functions for the listbox widget.
 * @constructor This function is used to construct a listbox widget.
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.listbox", webui.@THEME@.widget.selectBase, {
    // Set defaults.
    monospace: false,
    multiple: false,
    size: 12,
    titleOptionLabel: "ListSelector.titleOptionLabel",
    widgetName: "listbox" // Required for theme properties.
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
webui.@THEME@.widget.listbox.event =
        webui.@THEME@.widget.listbox.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_listbox_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_listbox_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_listbox_event_state_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_listbox_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_listbox_event_submit_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_listbox_event_submit_end"
    }
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.listbox.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Get properties.
    if (this.size != null) { props.size = this.size; }
    if (this.multiple != null) { props.multiple = this.multiple; }
    if (this.monospace != null) { props.monospace = this.monospace; }

    return props;
}

/**
 * Helper function to obtain class name for the HTML select element.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} disabled
 * @config {boolean} monospace
 * @return {String} The HTML select element class name.
 */
webui.@THEME@.widget.listbox.prototype.getSelectClassName = function(props) {    
    // Set default style.
    if (props.monospace == true) {
        return (props.disabled == true)
            ? this.selectMonospaceDisabledClassName
            : this.selectMonospaceClassName;
    }
    return this.inherited("getSelectClassName", arguments);
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.listbox.prototype.postCreate = function () {
    // Set style classes.
    this.selectClassName = this.widget.getClassName("LIST");
    this.selectDisabledClassName = this.widget.getClassName("LIST_DISABLED");
    this.selectMonospaceClassName = this.widget.getClassName("LIST_MONOSPACE");
    this.selectMonospaceDisabledClassName = this.widget.getClassName("LIST_MONOSPACE_DISABLED");
    this.optionClassName = this.widget.getClassName("LIST_OPTION");
    this.optionDisabledClassName = this.widget.getClassName("LIST_OPTION_DISABLED");
    this.optionGroupClassName = this.widget.getClassName("LIST_OPTION_GROUP");
    this.optionSelectedClassName = this.widget.getClassName("LIST_OPTION_SELECTED");
    this.optionSeparatorClassName = this.widget.getClassName("LIST_OPTION_SEPARATOR");

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
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label 
 * @config {String} labelOnTop
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {boolean} multiple 
 * @config {boolean} monospace 
 * @config {String} onBlur Element lost focus.
 * @config {String} onChange 
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
 * @config {Array} options 
 * @config {int} size 
 * @config {String} style Specify style rules inline. 
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.listbox.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
    return this.inherited("setProps", arguments);
}

/**
 * Helper function to set properties specific to the HTML select element
 *
 * @param {Node} selectNode The HTML select element.
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} disabled
 * @config {boolean} monospace  
 * @config {boolean} multiple 
 * @config {boolean} size
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.listbox.prototype.setSelectProps = function(selectNode, props) {
    if (props.size != null) {
        selectNode.size = (props.size < 1) ? 12 : props.size;  
    }
    if (props.multiple != null) {
        selectNode.multiple = new Boolean(props.multiple).valueOf();
    }
    return this.inherited("setSelectProps", arguments);
}
