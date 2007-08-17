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

dojo.provide("webui.@THEME@.widget.editableField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.textField");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.editableField = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to enable edit mode.
 */
webui.@THEME@.widget.editableField.enableEdit = function() {
    if (this == null) {
        return;
    }

    // Save the current value.
    this.savedValue = this.fieldNode.value;
        
    this.edit = true;
    this.fieldNode.className = this.getInputClassName();   
    this.fieldNode.readOnly = false;
    this.fieldNode.focus(); // In case function has been called programmatically, not by event.
    this.fieldNode.select();
}
    
/**
 * Helper function to disable edit mode.
 *
 * This function will commit or rollback the changes made in the field, and
 * setup appropriate style on the field.
 *
 * @param acceptChanges - optional parameter 
 *      if true, the entered data ( fieldNode value) will be commited through the
 *          Ajax submit() request; 
 *      if false, the entered data ( fieldNode value) will be rolled back to 
 *          previos state (value is saved before field enters editable state);
 *      if not specified, no changes to the field value will made, and 
 *          only styles will be modified
 */
webui.@THEME@.widget.editableField.disableEdit = function(acceptChanges) {
    // Do not go through disable cycle if field is readOnly already.
    if (this == null) {
        return;
    }
    if (acceptChanges == true) {
        // if savedValue does not exist, we have not edited the field yet
        if (this.autoSave == true && this.savedValue 
                && this.savedValue != this.fieldNode.value) {
            this.submit();
        }
    } else if (acceptChanges == false) {
        if (this.savedValue && this.savedValue != null)   {
            this.fieldNode.value = this.savedValue;
        }
    }
    this.savedValue = null;
    this.edit = false;
    this.fieldNode.className = this.getInputClassName();   
    this.fieldNode.readOnly = true;
    return true;
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.editableField.event = {
    /** 
     * This closure is used to process edit events. 
     */
    edit: {    
        /**
         * Helper function to process user gesture events on the field,
         * such as dblclick, onblur, onfocus, etc.
         * HTML events are connected to this function in fillInTemplate.
         */
        processEvent: function(event) {
            if (event == null) {
                return false;
            }
            var widgetId = event.currentTarget.parentNode.id;
            var widget = dojo.widget.byId(widgetId);
            if (widget == null) {
                return false;
            }
            if (event.type == "dblclick") {
                widget.enableEdit();
                return true;
            }
            if (event.type == "blur") {
                widget.disableEdit(true);
                return true;
            }
            if (event.type == "keyup") {
                if (widget.edit == false) {
                    // Currently in readOnly state.
                    // Allow <SPACE> key.
                    if (event.keyCode == 32) {
                        widget.enableEdit();
                    }
                } else {
                    // Currently in edit state.
                    if (event.keyCode ==27) widget.disableEdit(false); // ESC
                }              

                // Otherwise do not do anything.
                return true;
            }
            return true;    
        }
    },

    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_editableField_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_editableField_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_editableField_event_state_begin",
        endTopic: "webui_@THEME@_widget_editableField_event_state_end"
    },

    /**
     * This closure is used to process submit events.
     */
    submit: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_editableField_event_submit_begin",
        endTopic: "webui_@THEME@_widget_editableField_event_submit_end"
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.editableField.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.editableField.superclass.fillInTemplate.call(this, props, frag);

    // Set Initial readOnly state.
    this.fieldNode.readOnly = true;

    // Set events.
    dojo.event.connect(this.fieldNode, "ondblclick", webui.@THEME@.widget.editableField.event.edit.processEvent);
    dojo.event.connect(this.fieldNode, "onblur", webui.@THEME@.widget.editableField.event.edit.processEvent);
    dojo.event.connect(this.fieldNode, "onkeyup", webui.@THEME@.widget.editableField.event.edit.processEvent);

    return true;
}

/**
 * Helper function to obtain HTML input element class names.
 */
webui.@THEME@.widget.editableField.getInputClassName = function() {    

    // Set default style.
    if (this.disabled == true) {
        return  this.widget.getClassName("EDITABLE_FIELD_DISABLED","");
    }

    //invalid style
    var validStyle =  (this.valid == false) 
        ? " " + this.widget.getClassName("EDITABLE_FIELD_INVALID","")
        : ""; 

    return (this.edit == true)
        ? this.widget.getClassName("EDITABLE_FIELD_EDIT","") + validStyle
        : this.widget.getClassName("EDITABLE_FIELD_DEFAULT","") + validStyle;    
    
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.editableField.getProps = function() {
    var props = webui.@THEME@.widget.editableField.superclass.getProps.call(this);

    // Set properties.
    if (this.autoSave!= null) { props.autoSave = this.autoSave; }

    return props;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>autoSave</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>maxLength</li>
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
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.editableField._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Explicitly provided readOnly property must be ignored.
    props.readOnly = null;

    // Set properties.
    if (props.autoSave != null) { this.autoSave = props.autoSave; }

    // Set remaining properties.
    return webui.@THEME@.widget.editableField.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.editableField, webui.@THEME@.widget.textField);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.editableField, {
    // Set private functions.
    disableEdit: webui.@THEME@.widget.editableField.disableEdit,
    enableEdit: webui.@THEME@.widget.editableField.enableEdit,
    fillInTemplate: webui.@THEME@.widget.editableField.fillInTemplate,
    getInputClassName: webui.@THEME@.widget.editableField.getInputClassName,
    getProps: webui.@THEME@.widget.editableField.getProps,
    _setProps: webui.@THEME@.widget.editableField._setProps,
    submit: webui.@THEME@.widget.widgetBase.event.submit.processEvent,

    // Set defaults.
    edit: false,
    event: webui.@THEME@.widget.editableField.event,
    widgetType: "editableField"
});
