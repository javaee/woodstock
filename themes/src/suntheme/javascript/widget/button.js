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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.button");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.widgetBase");
 
/**
 * This function is used to construct a button widget.
 *
 * @name webui.@THEME_JS@.widget.button
 * @extends webui.@THEME_JS@.widget._base.widgetBase
 * @class This class contains functions for the button widget.
 * @constructor
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
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.button", 
        webui.@THEME_JS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.disabled = false;
        this.escape = true;
        this.mini = false;
        this.primary = true;
    },
    _widgetType: "button" // Required for theme properties.
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
webui.@THEME_JS@.widget.button.event =
        webui.@THEME_JS@.widget.button.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * 
         * @id webui.@THEME_JS@.widget.button.event.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "webui_@THEME_JS@_widget_button_event_refresh_begin",

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * 
         * @id webui.@THEME_JS@.widget.button.event.endTopic
         * @property {String} endTopic
         */
        endTopic: "webui_@THEME_JS@_widget_button_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_button_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_button_event_state_end"
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
webui.@THEME_JS@.widget.button.prototype._getClassName = function() {
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

    var className = this._theme.getClassName(key, "");
    return (this.className)
        ? className + " " + this.className
        : className;
};

/**
 * This function is used to obtain the outermost HTML element class name during
 * an onFocus or onMouseOver event.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
webui.@THEME_JS@.widget.button.prototype._getHoverClassName = function() {
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

    var className = this._theme.getClassName(key, "");
    return (this.className)
        ? className + " " + this.className
        : className;
};
    
/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @id webui.@THEME_JS@.widget.button.getProps
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.button.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.align) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.escape != null) { props.escape = this.escape; }
    if (this.mini != null) { props.mini = this.mini; }
    if (this.primary != null) { props.primary = this.primary; }
    if (this.value) { props.value = this.value; }

    return props;
};

/**
 * Helper function to create callback for onBlur event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.button.prototype._onBlurCallback = function(event) {
    if (this.disabled == true) {
        return true;
    }
    // Prevent errors during page submit, when modules have not been loaded.
    try {
        // Set style class.
        this._domNode.className = this._getClassName();
    } catch (err) {}
    return true;
};

/**
 * Helper function to create callback for onFocus event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.button.prototype._onFocusCallback = function(event) {
    if (this.disabled == true) {
        return true;
    }
    // Prevent errors during page submit, when modules have not been loaded.
    try {
        // Set style class.
        this._domNode.className = this._getHoverClassName();
    } catch (err) {}
    return true;
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
webui.@THEME_JS@.widget.button.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._domNode.name = this.id;
    }

    // Initialize deprecated public functions. 
    //
    // Note: Although we now have a setProps function to update properties,
    // these functions were previously added to the DOM node; thus, we must
    // continue to be backward compatible.
    
    /** @ignore */
    this._domNode.isSecondary = function() { return !(webui.@THEME_JS@.widget.common.getWidget(this.id).getProps().primary); };
    /** @ignore */
    this._domNode.setSecondary = function(secondary) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProps({primary: !secondary}); };
    /** @ignore */
    this._domNode.isPrimary = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).getProps().primary; };
    /** @ignore */
    this._domNode.setPrimary = function(primary) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProps({primary: primary}); };
    /** @ignore */
    this._domNode.isMini = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).getProps().mini; };
    /** @ignore */
    this._domNode.setMini = function(mini) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProps({mini: mini}); };
    /** @ignore */
    this._domNode.getDisabled = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).getProps().disabled; };
    /** @ignore */
    this._domNode.setDisabled = function(disabled) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProps({disabled: disabled}); };
    /** @ignore */
    this._domNode.getVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).getProps().visible; };
    /** @ignore */
    this._domNode.setVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProps({visible: show}); };
    /** @ignore */
    this._domNode.getText = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).getProps().value; };
    /** @ignore */
    this._domNode.setText = function(text) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProps({value: text}); };
    this._domNode.doClick = this._domNode.click;

    // Set events.
    this._dojo.connect(this._domNode, "onblur", this, "_onBlurCallback");
    this._dojo.connect(this._domNode, "onfocus", this, "_onFocusCallback");
    this._dojo.connect(this._domNode, "onmouseout", this, "_onBlurCallback");
    this._dojo.connect(this._domNode, "onmouseover", this, "_onFocusCallback");

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
webui.@THEME_JS@.widget.button.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.alt) { this._domNode.alt = props.alt; }
    if (props.align) { this._domNode.align = props.align; }

    // Set disabled.
    if (props.disabled != null) { 
        this._domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set value (i.e., button text).
    if (props.value) {
        // If escape is true, we want the text to be displayed literally. To 
        // achieve this behavior, do nothing.
        //
        // If escape is false, we want any special sequences in the text 
        // (e.g., "&nbsp;") to be displayed as evaluated (i.e., unescaped).
        this._domNode.value = (new Boolean(this.escape).valueOf() == false)
            ? this._proto._unescapeHTML(props.value)
            : props.value;
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
