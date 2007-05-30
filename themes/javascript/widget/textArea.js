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

dojo.provide("webui.@THEME@.widget.textArea");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.textArea = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to create callback for timer event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.textArea.createSubmitCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        //Create a submit request only if field has been modified
        if (widget.lastSaved != widget.textAreaNode.value) {
            widget.lastSaved = widget.textAreaNode.value;
            widget.submit();
        }
        return true;
    };
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.textArea.fillInTemplate = function() {
    // Set ids.
    if (this.id) {
        this.labelContainer.id = this.id + "_label";
        this.textAreaNode.id = this.id + "_field";
        this.textAreaNode.name = this.id + "_field";
    }
        
    // Set public functions.
    this.domNode.getInputElement = function() { return dojo.widget.byId(this.id).getInputElement(); }
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }

    // Set events.                
    if (this.autoSave > 0) {
        this.autoSaveTimerId = setInterval(
            webui.@THEME@.widget.textArea.createSubmitCallback(this.id), this.autoSave);  
    }

    // Set properties.
    return this.setProps();
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.textArea.getClassName = function() {
    // Set default style.    
    var className = (this.disabled == true)
        ? webui.@THEME@.widget.props.textArea.disabledClassName
        : webui.@THEME@.widget.props.textArea.className;
    
    return className;
}

/**
 * Returns the HTML input element that makes up the text field.
 *
 * @return a reference to the HTML input element. 
 */
webui.@THEME@.widget.textArea.getInputElement = function() {
    return this.textAreaNode;
}

/**
 * This function is used to get widget properties. 
 * @see webui.@THEME@.widget.textArea.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.textArea.getProps = function() {
    var props = {};
    
    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.label) { props.label= this.label; }
    if (this.text) { props.text = this.text; }
    if (this.title) { props.title = this.title; }
    if (this.type) { props.type= this.type; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.required != null) { props.required = this.required; }
    if (this.cols > 0 ) { props.cols = this.cols; }
    if (this.rows > 0) { props.rows = this.rows; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.autoSave > 0 ) { props.autoSave = this.autoSave; }
    if (this.style != null) { props.style = this.style; }
    
    // After widget has been initialized, get user's input.
    if (this.initialized == true && this.textAreaNode.value != null) {
        props.value = this.textAreaNode.value;
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
webui.@THEME@.widget.textArea.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_textArea_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_textArea_refresh_end",
    
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
            webui.@THEME@.widget.textArea.refresh.beginEventTopic, {
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
 *  <li>autoSave</li>
 *  <li>className</li>
 *  <li>cols</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>label</li>
 *  <li>lang</li>
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
webui.@THEME@.widget.textArea.setProps = function(props) {   
    // Save properties for later updates.
    if (props != null) {
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }
    
    // Set attributes.  
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.textAreaNode, props);
    this.setJavaScriptProps(this.textAreaNode, props);
    
    // Set text field attributes.    
    if (props.cols > 0 ) { this.textAreaNode.cols = props.cols; }
    if (props.rows > 0) { this.textAreaNode.rows = props.rows; }
    if (props.value != null) { this.textAreaNode.value = props.value; }
    if (props.title != null) { this.textAreaNode.title = props.title; }
    if (props.disabled != null) { 
        this.textAreaNode.disabled = new Boolean(props.disabled).valueOf();
    }
    if (props.readOnly != null) { 
        this.textAreaNode.readOnly = new Boolean(props.readOnly).valueOf();
    }
    
    this.textAreaNode.className = this.getClassName();
    
    //cancel autosave if it has been changed to <=0
    if (props.autoSave <= 0 && this.autoSaveTimerId && this.autoSaveTimerId != null ) {
        clearTimeout(this.autoSaveTimerId);
        this.autoSaveTimerId = null;
    }
    
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
        //label overwrites the span from the template and there is no way to set
        //alignment there.
        //we will push vertical alignment style onto label domNode
        labelWidget = dojo.widget.byId(this.label.id);
        if (labelWidget && labelWidget.domNode) {
            var currentClass = (labelWidget.domNode.className) 
                ? labelWidget.domNode.className + " "
                : "";
            labelWidget.domNode.className = currentClass + 
                webui.@THEME@.widget.props.textArea.labelTopAlignStyle;
        }
    }
    return props; // Return props for subclasses.
}

/**
 * This closure is used to process submit events.
 */
webui.@THEME@.widget.textArea.submit = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_textArea_submit_begin",
    endEventTopic: "webui_@THEME@_widget_textArea_submit_end",
    
    /**
     * Process submit event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.textArea.submit.beginEventTopic, {
                id: this.id,
                execute: execute
            });
        return true;
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.textArea, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.textArea, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.textArea.fillInTemplate,
    getClassName: webui.@THEME@.widget.textArea.getClassName,
    getInputElement: webui.@THEME@.widget.textArea.getInputElement,
    getProps: webui.@THEME@.widget.textArea.getProps,
    refresh: webui.@THEME@.widget.textArea.refresh.processEvent,
    setProps: webui.@THEME@.widget.textArea.setProps,
    submit: webui.@THEME@.widget.textArea.submit.processEvent,

    // Set defaults.
    autoSave: 0,
    cols: 20,
    disabled: false,
    lastSaved: null,
    required: false,
    rows: 3,
    valid: true,
    widgetType: "textArea"
});

//-->
