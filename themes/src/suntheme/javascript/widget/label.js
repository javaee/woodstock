// widget/label.js
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

dojo.provide("webui.@THEME@.widget.label");

dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.label
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for the label widget.
 * @constructor This function is used to construct a label widget.
 */
dojo.declare("webui.@THEME@.widget.label", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    level: 2,
    required: false,
    valid: true,
    widgetName: "label" // Required for theme properties.
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
webui.@THEME@.widget.label.event =
        webui.@THEME@.widget.label.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_label_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_label_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_label_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_label_event_state_end"
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
webui.@THEME@.widget.label.prototype.getClassName = function() {
    var key = "LABEL_LEVEL_TWO_TEXT";

    if (this.valid == false) {
        key = "CONTENT_ERROR_LABEL_TEXT";
    } else if (this.level == 1) {
        key = "LABEL_LEVEL_ONE_TEXT";
    } else if (this.level == 3) {
        key = "LABEL_LEVEL_THREE_TEXT";
    }

    // Get theme property.
    var className = this.theme.getClassName(key);
    if (className == null || className.length == 0) {
	return this.className;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to process notification events with Object
 * literals.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} detail Message detail text.
 * @config {boolean} valid Flag indicating validation state.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.label.prototype.notify = function(props) {
    if (props == null) {
        return false;
    }
    return this.setProps({
        valid: props.valid,
        errorImage: {
            title: props.detail
        }
    });
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.label.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.contents) { props.contents = this.contents; }
    if (this.errorImage) { props.errorImage = this.errorImage; }
    if (this.htmlFor) { props.htmlFor = this.htmlFor; }
    if (this.level != null) { props.level = this.level; }
    if (this.required != null) { props.required = this.required; }
    if (this.requiredImage) { props.requiredImage = this.requiredImage; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.value) { props.value = this.value; }

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
webui.@THEME@.widget.label.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.contentsContainer.id = this.id + "_contentsContainer";
        this.errorImageContainer.id = this.id + "_errorImageContainer";
        this.requiredImageContainer.id = this.id + "_requiredImageContainer";
        this.valueContainer.id = this.id + "_valueContainer";
    }

    // If errorImage or requiredImage are null, create images from the theme.
    // When the _setProps() function is called, image widgets will be
    // instantiated via the props param. 
    if (this.errorImage == null) {
	this.errorImage = this.widget.getImageProps("LABEL_INVALID_ICON", {
            id: this.id + "_error"
        });
    }
    if (this.requiredImage == null) {
	this.requiredImage = this.widget.getImageProps("LABEL_REQUIRED_ICON", {
            id: this.id + "_required"
        });
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
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.label.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
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
webui.@THEME@.widget.label.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.htmlFor) { this.domNode.htmlFor = props.htmlFor; }
    if (props.valid != null) { this.valid = new Boolean(props.valid).valueOf(); }
    if (props.required != null) { this.required = new Boolean(props.required).valueOf(); }
    if (props.value) { this.widget.addFragment(this.valueContainer, props.value); }

    // Set error image properties.
    if (props.errorImage || props.valid != null) {
        // Ensure property exists so we can call setProps just once.
        if (props.errorImage == null) {
            props.errorImage = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.errorImage.id = this.errorImage.id; // Required for updateFragment().
        props.errorImage.visible = !this.valid;

        // Update/add fragment.
        this.widget.updateFragment(this.errorImageContainer, props.errorImage);
    }

    // Set required image properties.
    if (props.requiredImage || props.required != null) {
        // Ensure property exists so we can call setProps just once.
        if (props.requiredImage == null) {
            props.requiredImage = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.requiredImage.id = this.requiredImage.id; // Required for updateFragment().
        props.requiredImage.visible = this.required;

        // Update/add fragment.
        this.widget.updateFragment(this.requiredImageContainer, props.requiredImage);
    }

    // Set contents.
    if (props.contents) {
        // Remove child nodes.
        this.widget.removeChildNodes(this.contentsContainer);

	for (var i = 0; i < props.contents.length; i++) {
            this.widget.addFragment(this.contentsContainer, props.contents[i], "last");
        }
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
