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

dojo.provide("webui.@THEME@.widget.radioButton");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.checkbox");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.radioButton = function() {    
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.radioButton.getClassName = function() {
    // Set default style.
    var className = (this.disabled == true)
        ? webui.@THEME@.widget.props.radioButton.disabledClassName
        : webui.@THEME@.widget.props.radioButton.className;

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * Helper function to obtain image class names.
 */
webui.@THEME@.widget.radioButton.getImageClassName = function() {
    return (this.disabled == true)
        ? webui.@THEME@.widget.props.radioButton.imageDisabledClassName
        : webui.@THEME@.widget.props.radioButton.imageClassName;  
}

/**
 * Helper function to obtain label class names.
 */
webui.@THEME@.widget.radioButton.getLabelClassName = function() {
    return (this.disabled == true)
        ? webui.@THEME@.widget.props.radioButton.labelDisabledClassName
        : webui.@THEME@.widget.props.radioButton.labelClassName;  
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.radioButton.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_radioButton_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_radioButton_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.radioButton");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.radioButton.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.radioButton.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accesskey</li> 
 *  <li>align</li>
 *  <li>checked</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>image</li>
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>name</li>
 *  <li>onBlur</li>
 *  <li>onChange</li>
 *  <li>onClick</li> 
 *  <li>onFocus</li> 
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseMove</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li> 
 *  <li>onSelect</li>
 *  <li>readOnly</li>    
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>value</li>  
 *  <li>visible</li>  
 * </ul>
 *
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.radioButton._setProps = function(props) {
    if (props == null) {
        return false;
    }

    if (props.name) {
        // IE does not support the name attribute being set dynamically as 
        // documented at:
        //
        // http://msdn.microsoft.com/workshop/author/dhtml/reference/properties/name_2.asp
        //
        // In order to create an HTML element with a name attribute, the name
        // and value must be provided when using the inner HTML property or the
        // document.createElement() function. As a work around, we shall set the
        // attribute via the HTML template using name="${this.name}". In order
        // to obtain the correct value, the name property must be provided to 
        // the widget. Although we're resetting the name below, as the default,
        // this has no affect on IE. 
        this.inputNode.name = props.name;
    }

    // Set remaining properties.
    return webui.@THEME@.widget.radioButton.superclass._setProps.call(this, props);
}

/**
 * This closure is used to process submit events.
 */
webui.@THEME@.widget.radioButton.submit = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_radioButton_submit_begin",
    endEventTopic: "webui_@THEME@_widget_radioButton_submit_end",
    
    /**
     * Process submit event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.radioButton");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.radioButton.submit.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.radioButton.submit.endEventTopic
            });
        return true;
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.radioButton, webui.@THEME@.widget.checkbox);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.radioButton, {
    // Set private functions.
    getClassName: webui.@THEME@.widget.radioButton.getClassName,
    getImageClassName: webui.@THEME@.widget.radioButton.getImageClassName,
    getLabelClassName: webui.@THEME@.widget.radioButton.getLabelClassName,
    refresh: webui.@THEME@.widget.radioButton.refresh.processEvent,
    _setProps: webui.@THEME@.widget.radioButton._setProps,
    submit: webui.@THEME@.widget.radioButton.submit.processEvent,

    // Set defaults.
    idSuffix: "_rb",
    widgetType: "radioButton"
});
