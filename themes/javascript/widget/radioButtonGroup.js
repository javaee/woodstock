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

dojo.provide("webui.@THEME@.widget.radioButtonGroup");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.checkboxGroup");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.radioButtonGroup = function() {    
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.radioButtonGroup.getClassName = function() {    
    var className = webui.@THEME@.widget.props.radioButtonGroup.vertClassName;
    if (this.columns > 1) {
        className = webui.@THEME@.widget.props.radioButtonGroup.horizClassName;    
    }    
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.radioButtonGroup.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_radioButtonGroup_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_radioButtonGroup_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.radioButtonGroup");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.radioButtonGroup.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.radioButtonGroup.refresh.endEventTopic
            });
        return true;
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.radioButtonGroup, webui.@THEME@.widget.checkboxGroup);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.radioButtonGroup, {
    // Set private functions.    
    getClassName: webui.@THEME@.widget.radioButtonGroup.getClassName,    
    refresh: webui.@THEME@.widget.radioButtonGroup.refresh.processEvent,                     

    // Set defaults
    templatePath: webui.@THEME@.theme.getTemplatePath("radioButtonGroup"),
    templateString: webui.@THEME@.theme.getTemplateString("radioButtonGroup"),
    widgetType: "radioButtonGroup"
});
