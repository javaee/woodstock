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
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.textField.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.textField = function() {
    
    // Set defaults.
    this.disabled   = (this.disabled != null) ? this.disabled : false;
    this.required   = (this.required != null) ? this.required : false;
    this.size       = (this.size > 0) ? this.size : 20;
    this.valid      = (this.valid != null) ? this.valid : true;
    this.widgetType = "textField";
    
    
    // Register widget.
    dojo.widget.Widget.call(this);
    
    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.labelContainer.id = this.id + "_label";
            this.textFieldNode.id = this.id + "_field";
            this.textFieldNode.setAttribute("name", this.id + "_field"); 
        }
        
        // Set public functions.
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }        
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        
        // Set private functions .
        this.setProps       = webui.@THEME@.widget.textField.setProps;
        this.getProps       = webui.@THEME@.widget.textField.getProps;
        this.getClassName   = webui.@THEME@.widget.textField.getClassName;
        
        // Set events.
        
        //generate the following events ONLY when 'autoValidate'== true  
        if (this.autoValidate == true) {
            dojo.event.connect(this.textFieldNode, "onblur", 
            webui.@THEME@.widget.textField.validation.processEvent);
        }
        // Set properties.
        this.setProps();
        
        return true;
    }
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.textField.getClassName = function() {
  // Set default style.    
    var className = (this.disabled == true)
        ? webui.@THEME@.widget.props.textField.disabledClassName
        : webui.@THEME@.widget.props.textField.enabledClassName;

    return (this.className)
        ? className + " " + this.className
        : className;
    
}





/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>className</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>label</li>
 *  <li>required</li>
 *  <li>size</li>
 *  <li>valid</li>
 *  <li>value</li>
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
 *  <li>dir</li>
 *  <li>lang</li>
 *  <li>accesskey</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>visible</li> 
 *  <li>tabIndex</li>
 *  <li>type</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.textField.setProps = function(props) {
    
//    if (props == null) {
//        return false;
//    }
    
   // Save properties for later updates.
   if (props != null) {
       webui.@THEME@.widget.common.extend(this, props);
   } else {
       props = this.getProps(); // Widget is being initialized.
   } 
    
    
    // Set attributes.  
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
    webui.@THEME@.widget.common.setCommonProps(this.textFieldNode, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.textFieldNode, props);
    
    this.className = null; // Does not apply here.
    
    
    // Set text field attributes.
    props.id = props.name; // Match JSF renderer decode method.
    
    if (props.type) { this.textFieldNode.type = props.type; }    
    if (props.size) { this.textFieldNode.setAttribute("size", props.size); }
    if (props.value) { this.textFieldNode.setAttribute("value", props.value); }
    if (props.text) { this.textFieldNode.setAttribute("value", props.value); }
    
    this.textFieldNode.className = this.getClassName();
    
    if (props.disabled != null) { 
        if (props.disabled == true) {
            this.textFieldNode.setAttribute("disabled", "disabled");
        } else {
            this.textFieldNode.removeAttribute("disabled");
        }
    }    
    
  
    // Set label properties.
    if (props.label ||  (props.valid != null || props.required != null) && this.label) {
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
            webui.@THEME@.widget.common.addFragment(this.labelContainer, props.label);
        }
    }
    
    return true;
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
    if (this.name) { props.name = this.name; }
    if (this.value) { props.value = this.value; }
    if (this.text) { props.text= this.text; }
    if (this.title) { props.title = this.title; }
    if (this.size) { props.size = this.size; }
    if (this.required != null) { props.required = this.required; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.label) { props.label= this.label; }
    if (this.type) { props.type= this.type; }
    
    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getJavaScriptProps(this));
    
    return props;
}



/**
 * This closure is used to publish validation events.
 */
webui.@THEME@.widget.textField.validation = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_textField_validation_begin",
    endEventTopic: "webui_@THEME@_widget_textField_validation_end",
    
    /**
     * Process validation event.
     * This function interprets an event ( one of onXXX events, such as onBlur, etc) and
     * extracts data needed for subsequent ajax request generation - specifically 
     * <ol>
     *  <li>widget id it that has generated the event
     * </ol>
     * if widget id is not found, propagation of the event is stopped, otherwise
     * publishBeginEvent is called with extracted data.
     *
     * @param evt Event generated by the widget.
     */
    processEvent: function(evt) {
        if (evt == null) {
            return false;
        }

        // Publish event to retrieve data.
        webui.@THEME@.widget.textField.validation.publishBeginEvent({
            id: evt.currentTarget.parentNode.id,
        });
    },


    
    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param evt Event generated by scroll bar.
     */
    publishBeginEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.textField.validation.beginEventTopic, props);
        return true;
    },
    
    /**
     * Publish an event for custom AJAX implementations to listen for. For
     * example, an alert component may need to be updated when ever a text
     * field value is found to be invalid.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.textField.validation.endEventTopic, props);
        return true;
    }
}

dojo.inherits(webui.@THEME@.widget.textField, dojo.widget.HtmlWidget);

//-->
