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
dojo.require("webui.@THEME@.*");
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
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.label.fillInTemplate = function() {
    // Set ids.
    if (this.id) {
        this.requiredImageContainer.id = this.id + "_requiredImageContainer";
        this.errorImageContainer.id = this.id + "_errorImageContainer";
        this.valueContainer.id = this.id + "_valueContainer";
        this.contentsContainer.id = this.id + "_contentsContainer";
    }

    // Set public functions. 
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }

    // Set properties.
    return this.setProps();
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.label.getClassName = function() {
    // Set style for default label level.
    var className = webui.@THEME@.widget.props.label.levelTwoStyleClass;

    if (this.valid == false) {
        className = webui.@THEME@.widget.props.label.errorStyleClass;
    } else if (this.level == 1) {
        className = webui.@THEME@.widget.props.label.levelOneStyleClass;
    } else if (this.level == 3) {
        className = webui.@THEME@.widget.props.label.levelThreeStyleClass;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.label.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.label.getProps = function() {
    var props = {};

    // Set properties.
    if (this.contents) { props.contents = this.contents; }
    if (this.errorImage) { props.errorImage = this.errorImage; }
    if (this.htmlFor) { props.htmlFor = this.htmlFor; }
    if (this.level != null) { props.level = this.level; }
    if (this.required != null) { props.required = this.required; }
    if (this.requiredImage) { props.requiredImage = this.requiredImage; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.value) { props.value = this.value; }

    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getJavaScriptProps());

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
 * This function is used to set widget properties with the
 * following Object literals.
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
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.label.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        // Replace contents -- do not extend.
        if (props.contents) {
            this.contents = null;
        }
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();
    
    // Set DOM node properties.
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.domNode, props);
    this.setJavaScriptProps(this.domNode, props);

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
    return props; // Return props for subclasses.
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
    validate: webui.@THEME@.widget.label.validation.processEvent,

    // Set defaults.
    level: 2,
    required: false,
    templateString: webui.@THEME@.theme.getTemplateString("label"),
    valid: true,
    widgetType: "label"
});
