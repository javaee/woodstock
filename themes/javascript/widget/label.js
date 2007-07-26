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
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.label.fillInTemplate = function(props, frag) {
    // Set ids.
    if (this.id) {
        this.contentsContainer.id = this.id + "_contentsContainer";
        this.errorImageContainer.id = this.id + "_errorImageContainer";
        this.requiredImageContainer.id = this.id + "_requiredImageContainer";
        this.valueContainer.id = this.id + "_valueContainer";
    }

    // Set common functions.
    return webui.@THEME@.widget.label.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.label.getClassName = function() {
    var className = null;

    // Set default style.
    if (this.valid == false) {
        className = webui.@THEME@.widget.props.label.errorStyleClass;
    } else if (this.level == 1) {
        className = webui.@THEME@.widget.props.label.levelOneStyleClass;
    } else if (this.level == 3) {
        className = webui.@THEME@.widget.props.label.levelThreeStyleClass;
    } else {
        className = webui.@THEME@.widget.props.label.levelTwoStyleClass;
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
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.label.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_label_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_label_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.label");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.label.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.label.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.label.setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.label.superclass.setProps.call(this, props);
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
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.label._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.htmlFor) { this.domNode.htmlFor = props.htmlFor; }

    // Set label value.
    if (props.value) {
        this.addFragment(this.valueContainer, props.value);
    }
  
    // Set error image properties.
    if (props.errorImage || props.valid != null && this.errorImage) {
        // Ensure property exists so we can call setProps just once.
        if (props.errorImage == null) {
            props.errorImage = {};
        }

        // Show error image.
        props.errorImage.visible = (this.valid != null)
            ? !this.valid : false;

        // Update widget/add fragment.
        var errorImageWidget = dojo.widget.byId(this.errorImage.id); 
        if (errorImageWidget) {
            errorImageWidget.setProps(props.errorImage);
        } else {
            this.addFragment(this.errorImageContainer, props.errorImage);
        }
    }

    // Set required image properties.
    if (props.requiredImage || props.required != null && this.requiredImage) {       
        // Ensure property exists so we can call setProps just once.
        if (props.requiredImage == null) {
            props.requiredImage = {};
        }

        // Show required image.
        props.requiredImage.visible = (this.required != null)
            ? this.required : false;

        // Update widget/add fragment.
        var requiredImageWidget = dojo.widget.byId(this.requiredImage.id);
        if (requiredImageWidget) {
            requiredImageWidget.setProps(props.requiredImage);
        } else {
            this.addFragment(this.requiredImageContainer, props.requiredImage);
        }
    }

    // Set contents.
    if (props.contents) {
        // Remove child nodes.
        this.removeChildNodes(this.contentsContainer);

	for (var i = 0; i < props.contents.length; i++) {
            this.addFragment(this.contentsContainer, props.contents[i], "last");
        }
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.label.superclass._setProps.call(this, props);
}

/**
 * This closure is used to process validation events.
 */
webui.@THEME@.widget.label.validation = {
    /**
     * This function is used to process validation events with the following
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

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.label, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.label, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.label.fillInTemplate,
    getClassName: webui.@THEME@.widget.label.getClassName,
    getProps: webui.@THEME@.widget.label.getProps,
    refresh: webui.@THEME@.widget.label.refresh.processEvent,
    setProps: webui.@THEME@.widget.label.setProps,
    _setProps: webui.@THEME@.widget.label._setProps,
    validate: webui.@THEME@.widget.label.validation.processEvent,

    // Set defaults.
    level: 2,
    required: false,
    valid: true,
    widgetType: "label"
});
