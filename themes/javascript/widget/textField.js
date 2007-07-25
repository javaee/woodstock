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

dojo.provide("webui.@THEME@.widget.textField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.fieldBase");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.textField = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the setWidgetProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.textField.fillInTemplate = function(props, frag) {
    // Set public functions.
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }
    
    // Set events.
    if (this.autoValidate == true) {
        // Generate the following event ONLY when 'autoValidate' == true.
        dojo.event.connect(this.fieldNode, "onblur", 
            webui.@THEME@.widget.textField.validation.processEvent);
    }

    // Set common functions.
    return webui.@THEME@.widget.textField.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * Helper function to obtain HTML input element class names.
 */
webui.@THEME@.widget.textField.getInputClassName = function() {   
    // Set default style.    
    return (this.disabled == true)
        ? webui.@THEME@.widget.props.textField.disabledClassName
        : webui.@THEME@.widget.props.textField.className;
}

/**
 * This function is used to get widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
 */
webui.@THEME@.widget.textField.getProps = function() {
    var props = webui.@THEME@.widget.textField.superclass.getProps.call(this);

    // Set properties.
    if (this.autoValidate != null) { props.autoValidate = this.autoValidate; }
    
    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.textField.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_textField_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_textField_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.textField");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.textField.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.textField.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This closure is used to process submit events.
 */
webui.@THEME@.widget.textField.submit = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_textField_submit_begin",
    endEventTopic: "webui_@THEME@_widget_textField_submit_end",
    
    /**
     * Process submit event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.textField");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.textField.submit.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.textField.submit.endEventTopic
            });
        return true;
    }
}

/**
 * This closure is used to process validation events.
 */
webui.@THEME@.widget.textField.validation = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_textField_validation_begin",
    endEventTopic: "webui_@THEME@_widget_textField_validation_end",
    
    /**
     * Process validation event.
     *
     * This function interprets an event (one of onXXX events, such as onBlur,
     * etc) and extracts data needed for subsequent Ajax request generation. 
     * Specifically, the widget id that has generated the event. If widget id is
     * found, publishBeginEvent is called with extracted data.
     *
     * @param event Event generated by the widget.
     */
    processEvent: function(event) {
        if (event == null) {
            return false;
        }

        var widget = dojo.widget.byId(event.currentTarget.parentNode.id);
        if (widget) {
            // Include default AJAX implementation.
            widget.ajaxify("webui.@THEME@.widget.jsfx.textField");
        }

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.textField.validation.beginEventTopic, {
                id: event.currentTarget.parentNode.id
            });
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.textField, webui.@THEME@.widget.fieldBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.textField, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.textField.fillInTemplate,
    getInputClassName: webui.@THEME@.widget.textField.getInputClassName,
    getProps: webui.@THEME@.widget.textField.getProps,
    refresh: webui.@THEME@.widget.textField.refresh.processEvent,
    submit: webui.@THEME@.widget.textField.submit.processEvent,

    // Set defaults.
    widgetType: "textField"
});
