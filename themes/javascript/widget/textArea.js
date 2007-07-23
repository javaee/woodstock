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

dojo.provide("webui.@THEME@.widget.textArea");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.textField");

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
        if (widget.lastSaved != widget.fieldNode.value) {
            widget.lastSaved = widget.fieldNode.value;
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
    // Set events.                
    if (this.autoSave > 0) {
        this.autoSaveTimerId = setInterval(
            webui.@THEME@.widget.textArea.createSubmitCallback(this.id), 
                this.autoSave);  
    }
    
    // Initialize template.
    return webui.@THEME@.widget.textArea.superclass.fillInTemplate.call(this);
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.textArea.getClassName = function() {
    // Set default style.    
    var className = (this.disabled == true)
        ? webui.@THEME@.widget.props.textArea.disabledClassName
        : webui.@THEME@.widget.props.textArea.className;
    
    return (this.className)
        ? className + " " + this.className
        : className;    
}

/**
 * This function is used to get widget properties. 
 * @see webui.@THEME@.widget.textArea.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.textArea.getProps = function() {
    var props = webui.@THEME@.widget.textArea.superclass.getProps.call(this);
    
    // Set properties.
    if (this.cols > 0 ) { props.cols = this.cols; }
    if (this.rows > 0) { props.rows = this.rows; }
    if (this.autoSave > 0 ) { props.autoSave = this.autoSave; }
   
    return props;
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
    if (props == null) {
        return null;
    }

    // Set label className -- must be set before calling superclass.
    if (props.label) {
        props.label.className = (props.label.className)
            ? webui.@THEME@.widget.props.textArea.labelTopAlignStyle + " " + props.label.className
            : webui.@THEME@.widget.props.textArea.labelTopAlignStyle;
    }
    
    // Set text field attributes.    
    if (props.cols > 0) { this.fieldNode.cols = props.cols; }
    if (props.rows > 0) { this.fieldNode.rows = props.rows; }
    
    // Cancel autosave if it has been changed to <=0
    if (props.autoSave <= 0 && this.autoSaveTimerId && this.autoSaveTimerId != null ) {
        clearTimeout(this.autoSaveTimerId);
        this.autoSaveTimerId = null;
    }

    // Return props for subclasses.
    return webui.@THEME@.widget.textArea.superclass.setProps.call(this, props);
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
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.textArea");
        
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
        webui.@THEME@.widget.textArea.refresh.beginEventTopic, {
            id: this.id,
            execute: execute,
            endEventTopic: webui.@THEME@.widget.textArea.refresh.endEventTopic
        });
        return true;
    }
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
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.textArea");
        
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
        webui.@THEME@.widget.textArea.submit.beginEventTopic, {
            id: this.id,
            execute: execute,
            endEventTopic: webui.@THEME@.widget.textArea.submit.endEventTopic
        });
        return true;
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.textArea, webui.@THEME@.widget.textField);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.textArea, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.textArea.fillInTemplate,
    getClassName: webui.@THEME@.widget.textArea.getClassName,
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
    templatePath: webui.@THEME@.theme.getTemplatePath("textArea"),
    templateString: webui.@THEME@.theme.getTemplateString("textArea"),
    valid: true,
    widgetType: "textArea"
});
