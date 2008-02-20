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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.button");

webui.@THEME@.dojo.require("webui.@THEME@.formElements");
webui.@THEME@.dojo.require("webui.@THEME@.widget.widgetBase");
 
/**
 * @name webui.@THEME@.widget.button
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for the button widget.
 * @constructor This function is used to construct a button widget.
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.button", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    disabled: false,
    escape: true,
    mini: false,
    primary: true,
    widgetName: "button" // Required for theme properties.
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
webui.@THEME@.widget.button.event =
        webui.@THEME@.widget.button.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_button_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_button_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_button_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_button_event_state_end"
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
webui.@THEME@.widget.button.prototype.getClassName = function() {
    var key = null;

    if (this.mini == true && this.primary == true) {
        key = (this.disabled == true)
            ? "BUTTON1_MINI_DISABLED" // primaryMiniDisabledClassName
            : "BUTTON1_MINI";         // primaryMiniClassName;
    } else if (this.mini == true) {
        key = (this.disabled == true)
            ? "BUTTON2_MINI_DISABLED" // secondaryMiniDisabledClassName
            : "BUTTON2_MINI";         // secondaryMiniClassName;
    } else if (this.primary == true) {
        key = (this.disabled == true)
            ? "BUTTON1_DISABLED"      // primaryDisabledClassName
            : "BUTTON1";              // primaryClassName
    } else {
        key = (this.disabled == true)
            ? "BUTTON2_DISABLED"      // secondaryDisabledClassName
            : "BUTTON2";	      // secondaryClassName
    }

    var className = this.widget.getClassName(key, "");
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to obtain the outermost HTML element class name during
 * an onFocus or onMouseOver event.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME@.widget.button.prototype.getHoverClassName = function() {
    var key = null;

    if (this.mini == true && this.primary == true) {
        key = "BUTTON1_MINI_HOVER"; 	// primaryMiniHovClassName;
    } else if (this.mini == true) {
        key = "BUTTON2_MINI_HOVER"; 	// secondaryMiniHovClassName;
    } else if (this.primary == true) {
        key = "BUTTON1_HOVER"; 		// primaryHovClassName;
    } else {
        key = "BUTTON2_HOVER";		// secondaryHovClassName;
    }

    var className = this.widget.getClassName(key, "");
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
webui.@THEME@.widget.button.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.align) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.escape != null) { props.escape = this.escape; }
    if (this.mini != null) { props.mini = this.mini; }
    if (this.primary != null) { props.primary = this.primary; }
    if (this.value) { props.value = this.value; }

    return props;
}

/**
 * Helper function to create callback for onBlur event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.button.prototype.onBlurCallback = function(event) {
    if (this.disabled == true) {
        return true;
    }
    // Prevent errors during page submit, when modules have not been loaded.
    try {
        // Set style class.
        this.domNode.className = this.getClassName();
    } catch (err) {}
    return true;
}

/**
 * Helper function to create callback for onFocus event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.button.prototype.onFocusCallback = function(event) {
    if (this.disabled == true) {
        return true;
    }
    // Prevent errors during page submit, when modules have not been loaded.
    try {
        // Set style class.
        this.domNode.className = this.getHoverClassName();
    } catch (err) {}
    return true;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.button.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.domNode.name = this.id;
    }

    // Initialize the deprecated functions in formElements.js. 
    // 
    // Note: Although we now have a setProps function to update properties,
    // these functions were previously added to the DOM node; thus, we must
    // continue to be backward compatible.
    webui.@THEME@.button._init({id: this.id});

    // Set events.
    this.dojo.connect(this.domNode, "onblur", this, "onBlurCallback");
    this.dojo.connect(this.domNode, "onfocus", this, "onFocusCallback");
    this.dojo.connect(this.domNode, "onmouseout", this, "onBlurCallback");
    this.dojo.connect(this.domNode, "onmouseover", this, "onFocusCallback");

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
 * @config {String} alt Alternate text for image input.
 * @config {String} align Alignment of image input.
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} escape HTML escape value (default).
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {boolean} mini Set button as mini if true.
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
 * @config {boolean} primary Set button as primary if true.
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.button.prototype.setProps = function(props, notify) {
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
webui.@THEME@.widget.button.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.alt) { this.domNode.alt = props.alt; }
    if (props.align) { this.domNode.align = props.align; }

    // Set disabled.
    if (props.disabled != null) { 
        this.domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set value (i.e., button text).
    if (props.value) {
        // If escape is true, we want the text to be displayed literally. To 
        // achieve this behavior, do nothing.
        //
        // If escape is false, we want any special sequences in the text 
        // (e.g., "&nbsp;") to be displayed as evaluated (i.e., unescaped).
        this.domNode.value = (new Boolean(this.escape).valueOf() == false)
            ? this.prototypejs.unescapeHTML(props.value)
            : props.value;
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
