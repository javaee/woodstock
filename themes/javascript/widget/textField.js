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

dojo.provide("webui.@THEME@.widget.textField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

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
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.textField.fillInTemplate = function() {
    // Set ids.
    if (this.id) {
        this.labelContainer.id = this.id + "_label";
        this.textFieldNode.id = this.id + "_field";
        this.textFieldNode.name = this.id + "_field";
    }
        
    // Set public functions.
    this.domNode.getInputElement = function() { return dojo.widget.byId(this.id).getInputElement(); }
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }

    // Set events.
    if (this.autoValidate == true) {
        // Generate the following event ONLY when 'autoValidate' == true.
        dojo.event.connect(this.textFieldNode, "onblur", 
            webui.@THEME@.widget.textField.validation.processEvent);
    }

    // Set properties.
    return this.setProps();
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.textField.getClassName = function() {
    // Set default style.    
    var className = (this.disabled == true)
        ? webui.@THEME@.widget.props.textField.disabledClassName
        : webui.@THEME@.widget.props.textField.className;
    
    return className;
}

/**
 * Returns the HTML input element that makes up the text field.
 *
 * @return a reference to the HTML input element. 
 */
webui.@THEME@.widget.textField.getInputElement = function() {
    return this.textFieldNode;
}

/**
 * This function is used to get widget properties. 
 * @see webui.@THEME@.widget.textField.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.textField.getProps = function() {
    var props = {};
    
    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.label) { props.label= this.label; }
    if (this.notify) { props.notify = this.notify; }
    if (this.text != null) { props.text = this.text; }
    if (this.title != null) { props.title = this.title; }
    if (this.type) { props.type= this.type; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.required != null) { props.required = this.required; }
    if (this.size > 0) { props.size = this.size; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.autoValidate != null) { props.autoValidate = this.autoValidate; }
    if (this.style != null) { props.style = this.style; }

    // After widget has been initialized, get user's input.
    if (this.isInitialized() == true && this.textFieldNode.value != null) {
        props.value = this.textFieldNode.value;
    } else if (this.value != null) {
        props.value = this.value;
    }

    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getJavaScriptProps());

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
 *  <li>accesskey</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>notify</li>
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
 *  <li>readOnly</li>
 *  <li>required</li>
 *  <li>size</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>valid</li>
 *  <li>value</li>
 *  <li>visible</li> 
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.textField.setProps = function(props) {   
    // Save properties for later updates.
    if (props != null) {
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }
    
    // Set attributes.  
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.textFieldNode, props);
    this.setJavaScriptProps(this.textFieldNode, props);
    
    // Set text field attributes.    
    if (props.size > 0) { this.textFieldNode.size = props.size; }
    if (props.value != null) { this.textFieldNode.value = props.value; }
    if (props.title != null) { this.textFieldNode.title = props.title; }   
    if (props.disabled != null) { 
        this.textFieldNode.disabled = new Boolean(props.disabled).valueOf();
    }
    if (props.readOnly != null) { 
        this.textFieldNode.readOnly = new Boolean(props.readOnly).valueOf();
    }

    this.textFieldNode.className = this.getClassName();
    
    // Set label properties.
    if (props.label || (props.valid != null || props.required != null) && this.label) {
        // Ensure property exists so we can call setProps just once.
        if (props.label == null) {
            props.label = {};
        }
        
        // Set valid.
        if (props.valid != null) { props.label.valid = props.valid; }
        
        // Set required.
        if (props.required != null) { props.label.required = props.required; }
        
        // Update widget/add fragment.                
        var labelWidget = dojo.widget.byId(this.label.id);
        if (labelWidget) {
            labelWidget.setProps(props.label);
        } else {
            this.addFragment(this.labelContainer, props.label);
        }
    }
    return props; // Return props for subclasses.
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
                execute: execute
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
dojo.inherits(webui.@THEME@.widget.textField, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.textField, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.textField.fillInTemplate,
    getClassName: webui.@THEME@.widget.textField.getClassName,
    getInputElement: webui.@THEME@.widget.textField.getInputElement,
    getProps: webui.@THEME@.widget.textField.getProps,
    refresh: webui.@THEME@.widget.textField.refresh.processEvent,
    setProps: webui.@THEME@.widget.textField.setProps,
    submit: webui.@THEME@.widget.textField.submit.processEvent,

    // Set defaults.
    disabled: false,
    required: false,
    size: 20,
    valid: true,
    widgetType: "textField"
});

//-->
