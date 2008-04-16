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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.textArea");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.textField");

/**
 * This function is used to construct a textArea widget.
 *
 * @name webui.@THEME_JS@.widget.textArea
 * @extends webui.@THEME_JS@.widget.textField
 * @class This class contains functions for the textArea widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accesskey 
 * @config {boolean} autoSave 
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label
 * @config {String} lang Specifies the language of attribute values and content.
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
 * @config {int} rows 
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} valid
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.textArea",
        webui.@THEME_JS@.widget.textField, {
    // Set defaults.
    constructor: function() {
        this.autoSave = 0;
        this.cols = 20;
        this.rows = 3;
    },
    _widgetType: "textArea" // Required for theme properties.
});

/**
 * Helper function to create callback for timer event.
 *
 * @return {Function} The callback function.
 * @private
 */
webui.@THEME_JS@.widget.textArea.prototype._createSubmitCallback = function() {
    var _id = this.id;

    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) { 
        var widget = webui.@THEME_JS@.widget.common.getWidget(_id);
        if (widget == null) {
            return false;
        }
        //Create a submit request only if field has been modified
        if (widget.lastSaved != widget._fieldNode.value) {
            widget.lastSaved = widget._fieldNode.value;
            widget.submit();
        }
        return true;
    };
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
webui.@THEME_JS@.widget.textArea.event =
        webui.@THEME_JS@.widget.textArea.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textArea_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textArea_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textArea_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textArea_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textArea_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textArea_event_submit_end"
    }
};

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
webui.@THEME_JS@.widget.textArea.prototype._getInputClassName = function() {
    // Set readOnly style
    if (this._fieldNode.readOnly) {
        return this._theme.getClassName("TEXT_AREA_READONLY", "");
    }

    // Apply invalid style.
    var validStyle =  (this.valid == false) 
        ? " " + this._theme.getClassName("TEXT_AREA_INVALID", "")
        : " " + this._theme.getClassName("TEXT_AREA_VALID", "");

    // Set default style.    
    return (this.disabled == true)
        ? this._theme.getClassName("TEXT_AREA_DISABLED", "") 
        : this._theme.getClassName("TEXT_AREA", "") + validStyle;    
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.textArea.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);
    
    // Set properties.
    if (this.cols > 0 ) { props.cols = this.cols; }
    if (this.rows > 0) { props.rows = this.rows; }
    if (this.autoSave > 0 ) { props.autoSave = this.autoSave; }
   
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
webui.@THEME_JS@.widget.textArea.prototype._postCreate = function () {
    // Set events.                
    if (this.autoSave > 0) {
        this.autoSaveTimerId = setInterval(this._createSubmitCallback(), 
            this.autoSave);  
    }
    return this._inherited("_postCreate", arguments);
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
webui.@THEME_JS@.widget.textArea.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.   
    if (props.cols > 0) { this._fieldNode.cols = props.cols; }
    if (props.rows > 0) { this._fieldNode.rows = props.rows; }
    
    // Cancel autosave if it has been changed to <=0
    if (props.autoSave <= 0 && this.autoSaveTimerId && this.autoSaveTimerId != null ) {
        clearTimeout(this.autoSaveTimerId);
        this.autoSaveTimerId = null;
    }

    // Set label className -- must be set before calling superclass.
    if (props.label) {
        props.label.className = (props.label.className)
            ? this._theme.getClassName("TEXT_AREA_TOPLABELALIGN", "")  + " " + props.label.className
            : this._theme.getClassName("TEXT_AREA_TOPLABELALIGN", "") ;
    }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
