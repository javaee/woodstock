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

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.label = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.label.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_label_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_label_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_label_event_state_begin",
        endTopic: "webui_@THEME@_widget_label_event_state_end"
    },

    /**
     * This closure is used to process notification events.
     */
    notification: {
        /**
         * This function is used to process notification events with the following
         * Object literals.
         *
         * <ul>
         *  <li>detail</li>
         *  <li>valid</li>
         * </ul>
         *
         * @param props Key-Value pairs of properties.
         */
        processEvent: function(props) {
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
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.label.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.label.superclass.fillInTemplate.call(this, props, frag);

    // Set ids.
    if (this.id) {
        this.contentsContainer.id = this.id + "_contentsContainer";
        this.errorImageContainer.id = this.id + "_errorImageContainer";
        this.requiredImageContainer.id = this.id + "_requiredImageContainer";
        this.valueContainer.id = this.id + "_valueContainer";
    }
    return true;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.label.getClassName = function() {
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
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.label.getProps = function() {
    var props = webui.@THEME@.widget.label.superclass.getProps.call(this);

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
 * This function is used to initialize the widget.
 *
 * Note: This is called after the fillInTemplate() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 * @param parent The parent of this widget.
 */
webui.@THEME@.widget.label.initialize = function (props, frag, parent) {
    // If errorImage or requiredImage are null, create images from the theme.
    // When the _setProps() function is called, image widgets will be
    // instantiated via the props param. 
    if (this.errorImage == null) {
	this.errorImage = this.widget.getImageProps("LABEL_INVALID_ICON", {
            id: this.id + "_error"
        });
        props.errorImage = this.errorImage; // Required for _setProps().
    }
    if (this.requiredImage == null) {
	this.requiredImage = this.widget.getImageProps("LABEL_REQUIRED_ICON", {
            id: this.id + "_required"
        });
        props.requiredImage = this.requiredImage; // Required for _setProps().
    }
    return webui.@THEME@.widget.label.superclass.initialize.call(this, props, frag, parent);    
}

/**
 * This function is used to set widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * Note: If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 *
 * @param props Key-Value pairs of properties.
 * @param notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.label.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.label.superclass.setProps.call(this, props, notify);
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>className</li>
 *  <li>contents</li>
 *  <li>dir</li>
 *  <li>errorImage</li>
 *  <li>htmlFor</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>level</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onFocus</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>required</li>
 *  <li>requiredImage</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>valid</li>
 *  <li>value</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.label._setProps = function(props) {
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
    return webui.@THEME@.widget.label.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.label, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.label, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.label.fillInTemplate,
    getClassName: webui.@THEME@.widget.label.getClassName,
    getProps: webui.@THEME@.widget.label.getProps,
    initialize: webui.@THEME@.widget.label.initialize,
    setProps: webui.@THEME@.widget.label.setProps,
    _setProps: webui.@THEME@.widget.label._setProps,
    notify: webui.@THEME@.widget.label.event.notification.processEvent,

    // Set defaults.
    event: webui.@THEME@.widget.label.event,
    level: 2,
    required: false,
    valid: true,
    widgetType: "label"
});
