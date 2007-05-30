//<!--
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

dojo.provide("webui.@THEME@.widget.hiddenField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.hiddenField = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.hiddenField.fillInTemplate = function() {
    // Set public functions. 
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }

    // Set properties.
    return this.setProps();
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.hiddenField.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.hiddenField.getProps = function() {
    var props = {};

    // Set properties.
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.name) { props.name = this.name; }
    if (this.value) { props.value = this.value; }

    // Add DOM node properties.
    Object.extend(props, this.getCoreProps());

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.hiddenField.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_hiddenField_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_hiddenField_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.hiddenField.refresh.beginEventTopic, {
                id: this.id,
                execute: execute
            });
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>name</li>
 *  <li>value</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.hiddenField.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set attributes.
    this.setCoreProps(this.domNode, props);

    if (props.name) { this.domNode.name = props.name; }
    if (props.value) { this.domNode.value = props.value; }
    if (props.disabled != null) { 
        this.domNode.disabled = new Boolean(props.disabled).valueOf();
    }
    return props; // Return props for subclasses.
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.hiddenField, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.hiddenField, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.hiddenField.fillInTemplate,
    getProps: webui.@THEME@.widget.hiddenField.getProps,
    refresh: webui.@THEME@.widget.hiddenField.refresh.processEvent,
    setProps: webui.@THEME@.widget.hiddenField.setProps,

    // Set defaults.
    disabled: false,
    widgetType: "hiddenField"
});

//-->
