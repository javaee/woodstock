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

dojo.provide("webui.@THEME@.widget.dropDown");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.listbox");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.dropDown = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.dropDown.fillInTemplate = function() {
    // Set ids.
    if (this.id) {
        this.submitterHiddenNode.id = this.id + "_submitter";
    }

    // Initialize template.
    return webui.@THEME@.widget.dropDown.superclass.fillInTemplate.call(this);
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.dropDown.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.dropDown.getProps = function() {
    var props = webui.@THEME@.widget.dropDown.superclass.getProps.call(this);

    // Get properties.
    if (this.submitForm != null) { props.submitForm = this.submitForm; }

    return props;
}

/**
 * Helper function to initialize the proper style classes.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.dropDown.initClassNames = function(props) {
    // Set style classes
    if (this.submitForm == null) {
        return;
    }

    if (new Boolean(this.submitForm).valueOf() == true) {
        this.selectClassName = webui.@THEME@.widget.props.jumpDropDown.className;
        this.selectDisabledClassName = webui.@THEME@.widget.props.jumpDropDown.disabledClassName;
        this.optionClassName = webui.@THEME@.widget.props.jumpDropDown.optionClassName;
        this.optionSeparatorClassName = webui.@THEME@.widget.props.jumpDropDown.optionSeparatorClassName;
        this.optionGroupClassName = webui.@THEME@.widget.props.jumpDropDown.optionGroupClassName;
        this.optionDisabledClassName = webui.@THEME@.widget.props.jumpDropDown.optionDisabledClassName;
        this.optionSelectedClassName = webui.@THEME@.widget.props.jumpDropDown.optionSelectedClassName;
    } else {
        this.selectClassName = webui.@THEME@.widget.props.dropDown.className;
        this.selectDisabledClassName = webui.@THEME@.widget.props.dropDown.disabledClassName;
        this.optionClassName = webui.@THEME@.widget.props.dropDown.optionClassName;
        this.optionSeparatorClassName = webui.@THEME@.widget.props.dropDown.optionSeparatorClassName;
        this.optionGroupClassName = webui.@THEME@.widget.props.dropDown.optionGroupClassName;
        this.optionDisabledClassName = webui.@THEME@.widget.props.dropDown.optionDisabledClassName;
        this.optionSelectedClassName = webui.@THEME@.widget.props.dropDown.optionSelectedClassName;
    }
    return true;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.dropDown.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_dropDown_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_dropDown_refresh_end",

    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.dropDown");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.dropDown.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.dropDown.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * Helper function called by onChange event to set the proper
 * selected, and disabled styles.
 */
webui.@THEME@.widget.dropDown.changed = function() {
    if (this.submitForm != true) {
        // Drop down changed.
        return webui.@THEME@.widget.dropDown.superclass.changed.call(this);
    } else {
        // Jump drop down changed.
        var jumpDropdown = this.listContainer; 

        // Find the <form> for this drop down
        var form = jumpDropdown; 
        while (form != null) { 
            form = form.parentNode; 
            if (form.tagName == "FORM") { 
                break;
            }
        }

        if (form != null) { 
            this.submitterHiddenNode.value = "true";

            // Set style classes.
            var options = jumpDropdown.options;
            for (var i = 0; i < options.length; i++) { 
                options[i].className = this.getOptionClassName(options[i]);
            }
            form.submit();
        }
    }
    return true; 
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>id</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>multiple</li>
 *  <li>onBlur</li>
 *  <li>onChange</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onFocus</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onSelect</li>
 *  <li>options</li>
 *  <li>size</li>
 *  <li>style</li>
 *  <li>submitForm</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.dropDown.setProps = function(props) {
    if (props == null) {
        return null;
    }

    // Add attributes to the hidden input for jump drop down.
    if (props.submitForm != null && props.submitForm == true) {
        this.submitterHiddenNode.name = this.submitterHiddenNode.id;
        this.submitterHiddenNode.value = "false";
    }

    // Return props for subclasses.
    return webui.@THEME@.widget.dropDown.superclass.setProps.call(this, props);
}

/**
 * This closure is used to process submit events.
 */
webui.@THEME@.widget.dropDown.submit = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_dropDown_submit_begin",
    endEventTopic: "webui_@THEME@_widget_dropDown_submit_end",

    /**
     * Process submit event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.dropDown");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.dropDown.submit.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.dropDown.submit.endEventTopic
            });
        return true;
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.dropDown, webui.@THEME@.widget.listbox);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.dropDown, {
    // Set private functions.
    changed: webui.@THEME@.widget.dropDown.changed,
    fillInTemplate: webui.@THEME@.widget.dropDown.fillInTemplate,
    getProps: webui.@THEME@.widget.dropDown.getProps,
    initClassNames: webui.@THEME@.widget.dropDown.initClassNames,
    refresh: webui.@THEME@.widget.dropDown.refresh.processEvent,
    setProps: webui.@THEME@.widget.dropDown.setProps,
    submit: webui.@THEME@.widget.dropDown.submit.processEvent,

    // Set defaults
    submitForm: false,
    templatePath: webui.@THEME@.theme.getTemplatePath("dropDown"),
    templateString: webui.@THEME@.theme.getTemplateString("dropDown"),
    widgetType: "dropDown"
});
