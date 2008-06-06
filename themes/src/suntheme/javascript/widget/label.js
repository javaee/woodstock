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

@JS_NS@._dojo.provide("@JS_NS@.widget.label");

@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a label widget.
 *
 * @name @JS_NS@.widget.label
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the label widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accesskey
 * @config {String} className CSS selector.
 * @config {String} contents 
 * @config {String} dir Specifies the directionality of text.
 * @config {Object} errorImage 
 * @config {String} htmlFor 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {int} level 
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {boolean} primary Set button as primary if true.
 * @config {boolean} required
 * @config {Object} requiredImage
 * @config {String} style Specify style rules inline.
 * @config {String} title Provides a title for element.
 * @config {boolean} valid
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.label", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        this.level = this._theme.getMessage("label.level", null, 2);
        this.required = false;
        this.valid = true;
    },
    _widgetType: "label" // Required for theme properties.
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
@JS_NS@.widget.label.event =
        @JS_NS@.widget.label.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_label_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_label_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_label_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_label_event_state_end"
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
@JS_NS@.widget.label.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);
    var key = "LABEL_LEVEL_TWO_TEXT";

    if (this.valid == false) {
        key = "CONTENT_ERROR_LABEL_TEXT";
    } else if (this.level == 1) {
        key = "LABEL_LEVEL_ONE_TEXT";
    } else if (this.level == 3) {
        key = "LABEL_LEVEL_THREE_TEXT";
    }

    // Get theme property.
    var newClassName = this._theme.getClassName(key, "");

    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * This function is used to process notification events with Object
 * literals.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} detail Message detail text.
 * @config {boolean} valid Flag indicating validation state.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.label.prototype._notify = function(props) {
    if (props == null) {
        return false;
    }
    return this.setProps({
        valid: props.valid,
        errorImage: {
            title: props.detail
        }
    });
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.label.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.contents != null) { props.contents = this.contents; }
    if (this.errorImage != null) { props.errorImage = this.errorImage; }
    if (this.htmlFor != null) { props.htmlFor = this.htmlFor; }
    if (this.level != null) { props.level = this.level; }
    if (this.required != null) { props.required = this.required; }
    if (this.requiredImage) { props.requiredImage = this.requiredImage; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.value != null) { props.value = this.value; }

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
@JS_NS@.widget.label.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._contentsContainer.id = this.id + "_contentsContainer";
        this._errorImageContainer.id = this.id + "_errorImageContainer";
        this._requiredImageContainer.id = this.id + "_requiredImageContainer";
        this._valueContainer.id = this.id + "_valueContainer";
    }

    // If errorImage or requiredImage are null, create images from the theme.
    // When the _setProps() function is called, image widgets will be
    // instantiated via the props param. 
    if (this.errorImage == null) {
	this.errorImage = {
            icon: "LABEL_INVALID_ICON",
            id: this.id + "_error",
	    className: this._theme.getClassName("LABEL_INVALID_IMAGE", null),
            widgetType: "image"
        };
    }
    if (this.requiredImage == null) {
        this.requiredImage = {
            icon: "LABEL_REQUIRED_ICON",
            id: this.id + "_required",
	    className: this._theme.getClassName("LABEL_REQUIRED_IMAGE", null),
            widgetType: "image"
        };
    }
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
@JS_NS@.widget.label.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
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
@JS_NS@.widget.label.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.htmlFor != null) {
	this._domNode.htmlFor = props.htmlFor;
    }
    if (props.valid != null) {
	this.valid = new Boolean(props.valid).valueOf();
    }
    if (props.required != null) {
	this.required = new Boolean(props.required).valueOf();
    }
    if (props.value != null) {
        // An empty string is valid.
	this._widget._addFragment(this._valueContainer, props.value);
    }

    // Set error image properties.
    if (props.errorImage || props.valid != null) {
        // Ensure property exists so we can call setProps just once.
        if (props.errorImage == null) {
            props.errorImage = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.errorImage.visible = !this.valid;

        // Update/add fragment.
        this._widget._updateFragment(this._errorImageContainer, this.errorImage.id, 
            props.errorImage);
    }

    // Set required image properties.
    if (props.requiredImage || props.required != null) {
        // Ensure property exists so we can call setProps just once.
        if (props.requiredImage == null) {
            props.requiredImage = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.requiredImage.visible = this.required;

        // Update/add fragment.
        this._widget._updateFragment(this._requiredImageContainer, 
            this.requiredImage.id, props.requiredImage);
    }

    // Set contents.
    if (props.contents) {
        // Remove child nodes.
        this._widget._removeChildNodes(this._contentsContainer);

	for (var i = 0; i < props.contents.length; i++) {
            this._widget._addFragment(this._contentsContainer, props.contents[i], "last");
        }
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
