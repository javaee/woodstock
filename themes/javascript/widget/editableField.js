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

dojo.provide("webui.@THEME@.widget.editableField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.textField");
/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.editableField.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.editableField = function() {
    // Set defaults.
    this.disabled   = false;
    this.required   = false;
    this.size       = 20;
    this.valid      = true;
    this.widgetType = "editableField";
    this.edit   = false;
    
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
            this.textFieldNode.name = this.id + "_field";
        }
        
        // Set public functions.
        this.domNode.getInputElement = function() { return dojo.widget.byId(this.id).getInputElement(); }
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        
        // Set private functions.
        this.getClassName = webui.@THEME@.widget.editableField.getClassName;
        this.getProps = webui.@THEME@.widget.editableField.getProps;
        this.setProps = webui.@THEME@.widget.editableField.setProps;
        this.enableEdit = webui.@THEME@.widget.editableField.enableEdit;
        this.disableEdit = webui.@THEME@.widget.editableField.disableEdit;
        
        // use the textField implementations for these functions
        this.getInputElement = webui.@THEME@.widget.textField.getInputElement;
        this.refresh = webui.@THEME@.widget.textField.refresh.processEvent;
        this.submit = webui.@THEME@.widget.textField.submit.processEvent;
        
        this.getSuperProps = webui.@THEME@.widget.textField.getProps;
        this.setSuperProps = webui.@THEME@.widget.textField.setProps;
        
        
        // Set events.
        
        //the following events are used for enabling or disabling edit mode
        dojo.event.connect(this.textFieldNode, "ondblclick", webui.@THEME@.widget.editableField.processEvent);
        dojo.event.connect(this.textFieldNode, "onblur", webui.@THEME@.widget.editableField.processEvent);
        dojo.event.connect(this.textFieldNode, "onkeyup", webui.@THEME@.widget.editableField.processEvent);
        
        if (this.autoValidate == true) {
            // Generate the following event ONLY when 'autoValidate' == true.
            dojo.event.connect(this.textFieldNode, "onblur", webui.@THEME@.widget.textField.validation.processEvent);
        }
        
        
        // Set properties.
        return this.setProps();
    }
}

/**
 * This function is used to get widget properties. 
 * @see webui.@THEME@.widget.editableField.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.editableField.getProps = function() {
    
    var props = this.getSuperProps();   
    
    // add own properties, if any
    
    return props;
}


/**
 * This function is used to set widget properties with the
 * following Object literals.
 * @see webui.@THEME@.widget.textField.setProps for details
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.editableField.setProps = function(props) {   
    props = this.setSuperProps(props);
    
    //set own properties, if any
    
    if (props.edit == true) this.enableEdit();
    else if (props.edit == false) 
        this.disableEdit(false); 
    else
        this.disableEdit(); // if props.edit is undefined, do not request data rollback
    
    return true;
}


/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.editableField.getClassName = function() {
    // Set default style.    
    var className;
    if (this.disabled == true) 
        className = webui.@THEME@.widget.props.editableField.disabledClassName;
    
    className = (this.edit == true)
    ? webui.@THEME@.widget.props.editableField.editableClassName
    : webui.@THEME@.widget.props.editableField.className;
    
    return className;    
}


/**
 * Helper function to enable edit mode.
 */
webui.@THEME@.widget.editableField.enableEdit = function() {
    if (this == null) 
        return;
    //save the current value
    this.savedValue = this.textFieldNode.value;
    
    this.edit = true;
    this.textFieldNode.className = this.getClassName();   
    this.textFieldNode.readOnly = false;
    this.textFieldNode.focus(); //in case function has been called programmatically, not by event
    this.textFieldNode.select();
}
/**
 * Private helper function to disable edit mode.
 * This function will commit or rollback the changes made in the field, and
 * setup appropriate style on the field.
 *
 * @param acceptChanges - optional parameter 
 *      if true, the entered data ( textFieldNode value) will be commited through the
 *          Ajax submit() request; 
 *      if false, the entered data ( textFieldNode value) will be rolled back to 
 *          previos state (value is saved before field enters editable state);
 *      if not specified, no changes to the field value will made, and 
 *          only styles will be modified
 */
webui.@THEME@.widget.editableField.disableEdit = function(acceptChanges) {
    //do not go through disable cycle if field is readOnly already
    if (this == null || this.edit == false) 
        return;
    if (acceptChanges == true) {
        // if savedValue does not exist, we have not edited the field yet
        if (this.savedValue && this.savedValue != this.textFieldNode.value) {
            this.submit();
        }
    } else if (acceptChanges == false) {
        if (this.savedValue && this.savedValue != null)   {
            this.textFieldNode.value = this.savedValue;
        }
    }
    this.savedValue = null;
    this.edit = false;
    this.textFieldNode.className = this.getClassName();   
    this.textFieldNode.readOnly = true;
    return true;
}
/**
 * Helper function to process user gesture events on the field,
 * such as dblclick, onblur, onfocus, etc.
 * HTML events are connected to this function in fillInTemplate
 */
webui.@THEME@.widget.editableField.processEvent = function(event) {
    
    if (event == null) {
        return false;
    }
    var widgetId = event.currentTarget.parentNode.id;
    var widget = dojo.widget.byId(widgetId);
    if (widget == null) {
        return false;
    }
    if (event.type =="dblclick") {
        widget.enableEdit();
        return true;
    }
    if (event.type =="blur") {
        widget.disableEdit(true);
        return true;
    }
    if (event.type =="keyup") {
        if (widget.edit == false) {
            // currently in readOnly state
            //allow  <SPACE> key
            if (event.keyCode == 32) {
                widget.enableEdit();
            }
        } else {
            // currently in edit state
            if (event.keyCode ==27) widget.disableEdit(false);//ESC
        }              
        
        //otherwise do not do anything
        return true;
    }
    
    return true;    
}


dojo.inherits(webui.@THEME@.widget.editableField, dojo.widget.HtmlWidget);

//-->
