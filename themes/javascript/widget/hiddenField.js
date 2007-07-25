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
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the setWidgetProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.hiddenField.fillInTemplate = function(props, frag) {
    // Set public functions. 
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }

    // Set common functions.
    return webui.@THEME@.widget.hiddenField.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to get widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
 */
webui.@THEME@.widget.hiddenField.getProps = function() {
    var props = webui.@THEME@.widget.hiddenField.superclass.getProps.call(this);

    // Set properties.
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.name) { props.name = this.name; }
    if (this.value) { props.value = this.value; }

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
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.hiddenField");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.hiddenField.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.hiddenField.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>name</li>
 *  <li>value</li>
 * </ul>
 *
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.hiddenField.setWidgetProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.name) { this.domNode.name = props.name; }
    if (props.value) { this.domNode.value = props.value; }
    if (props.disabled != null) { 
        this.domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set core props.
    return webui.@THEME@.widget.hiddenField.superclass.setWidgetProps.call(this, props);
}

/**
 * This closure is used to process submit events.
 */
webui.@THEME@.widget.hiddenField.submit = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_hiddenField_submit_begin",
    endEventTopic: "webui_@THEME@_widget_hiddenField_submit_end",
    
    /**
     * Process submit event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.hiddenField");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.hiddenField.submit.beginEventTopic, {
                id: this.id,
                execute: execute
            });
        return true;
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.hiddenField, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.hiddenField, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.hiddenField.fillInTemplate,
    getProps: webui.@THEME@.widget.hiddenField.getProps,
    refresh: webui.@THEME@.widget.hiddenField.refresh.processEvent,
    setWidgetProps: webui.@THEME@.widget.hiddenField.setWidgetProps,
    submit: webui.@THEME@.widget.hiddenField.submit.processEvent,

    // Set defaults.
    disabled: false,
    templatePath: webui.@THEME@.theme.getTemplatePath("hiddenField"),
    templateString: webui.@THEME@.theme.getTemplateString("hiddenField"),
    widgetType: "hiddenField"
});
