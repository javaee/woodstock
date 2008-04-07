/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.checkbox");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.checkedBase");

/**
 * This function is used to construct a checkbox widget.
 *
 * @name webui.@THEME_JS@.widget.checkbox
 * @extends webui.@THEME_JS@.widget._base.checkedBase
 * @class This class contains functions for the checkbox widget.
 * @constructor
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
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.checkbox",
        webui.@THEME_JS@.widget._base.checkedBase, {
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
webui.@THEME_JS@.widget.checkbox.event =
        webui.@THEME_JS@.widget.checkbox.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_checkbox_event_refresh_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_checkbox_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_checkbox_event_state_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_checkbox_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_checkbox_event_submit_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_checkbox_event_submit_end"
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
webui.@THEME_JS@.widget.checkbox.prototype._getClassName = function() {
    // Set default style.
    var className = (this.disabled == true)
        ? this._theme._getClassName("CHECKBOX_SPAN_DISABLED", "")
        : this._theme._getClassName("CHECKBOX_SPAN", ""); 

    return (this.className)
        ? className + " " + this.className
        : className;
};

/**
 * Helper function to obtain image class names.
 *
 * @return {String} The HTML image element class name.
 * @private
 */
webui.@THEME_JS@.widget.checkbox.prototype._getImageClassName = function() {
    return (this.disabled == true)
        ? this._theme._getClassName("CHECKBOX_IMAGE_DISABLED", "")
        : this._theme._getClassName("CHECKBOX_IMAGE", "");  
};

/**
 * Helper function to obtain input class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
webui.@THEME_JS@.widget.checkbox.prototype._getInputClassName = function() {
    // readOnly style.
    if (this.readOnly == true) {
        return this._theme._getClassName("CHECKBOX_READONLY", "");        
    }

    // disabled style.
    return (this.disabled == true)
        ? this._theme._getClassName("CHECKBOX_DISABLED", "")
        : this._theme._getClassName("CHECKBOX", "");  
};

/**
 * Helper function to obtain label class names.
 *
 * @return {String} The HTML label element class name.
 * @private
 */
webui.@THEME_JS@.widget.checkbox.prototype._getLabelClassName = function() {
    return (this.disabled == true)
        ? this._theme._getClassName("CHECKBOX_LABEL_DISABLED", "")
        : this._theme._getClassName("CHECKBOX_LABEL", "");  
};
