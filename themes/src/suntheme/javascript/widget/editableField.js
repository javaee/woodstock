// widget/editableField.js
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

dojo.require("webui.@THEME@.widget.textField");

/**
 * @name webui.@THEME@.widget.editableField
 * @extends webui.@THEME@.widget.textField
 * @class This class contains functions for the editableField widget.
 * @constructor This function is used to construct a editableField widget.
 */
dojo.declare("webui.@THEME@.widget.editableField", webui.@THEME@.widget.textField, {
    // Set defaults.
    edit: false,
    widgetName: "editableField" // Required for theme properties.
});
   
/**
 * Helper function to disable edit mode.
 *
 * This function will commit or rollback the changes made in the field, and
 * setup appropriate style on the field.
 *
 * @param {boolean} acceptChanges Optional parameter. 
 * <p>
 * If true, the entered data (fieldNode value) will be commited through the Ajax
 * submit() request. 
 * </p><p>
 * If false, the entered data (fieldNode value) will be rolled back to previos 
 * state (value is saved before field enters editable state).
 * </p><p>
 * If not specified, no changes to the field value will made, and only styles 
 * will be modified.
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.editableField.prototype.disableEdit = function(acceptChanges) {
    if (acceptChanges == true) {
        // If savedValue does not exist, we have not edited the field yet
        if (this.autoSave == true && this.savedValue && 
                this.savedValue != this.fieldNode.value) {
            this.submit();
        }
    } else if (acceptChanges == false) {
        if (this.savedValue)   {
            this.fieldNode.value = this.savedValue;
        }
    }
    this.edit = false;
    this.savedValue = null;
    this.fieldNode.className = this.getInputClassName();   
    this.fieldNode.readOnly = true;
    return true;
}

/**
 * Helper function to enable edit mode.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.editableField.prototype.enableEdit = function() {
    // Save the current value.
    this.savedValue = this.fieldNode.value;
        
    this.edit = true;
    this.fieldNode.className = this.getInputClassName();   
    this.fieldNode.readOnly = false;
    this.fieldNode.focus(); // In case function has been called programmatically, not by event.
    this.fieldNode.select();
    return true;
}

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME@.widget.editableField.event =
        webui.@THEME@.widget.editableField.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_editableField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_editableField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_editableField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_editableField_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_editableField_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_editableField_event_submit_end"
    }
}

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 */
webui.@THEME@.widget.editableField.prototype.getInputClassName = function() {    
    // Set default style.
    if (this.disabled == true) {
        return  this.widget.getClassName("EDITABLE_FIELD_DISABLED","");
    }

    // Apply invalid style.
    var validStyle =  (this.valid == false) 
        ? " " + this.widget.getClassName("EDITABLE_FIELD_INVALID","")
        : ""; 

    return (this.edit == true)
        ? this.widget.getClassName("EDITABLE_FIELD_EDIT","") + validStyle
        : this.widget.getClassName("EDITABLE_FIELD_DEFAULT","") + validStyle;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.editableField.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.autoSave!= null) { props.autoSave = this.autoSave; }

    return props;
}

/**
 * Process user gesture events on the field, such as dblclick, onblur, 
 * onkeyup, etc.
 *
 * @param {Event} event The JavaScript event
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.editableField.prototype.onEditCallback = function(event) {
    if (event == null) {
        return false;
    }
    if (event.type == "dblclick") {
        this.enableEdit();
        return true;
    }
    if (event.type == "blur") {
        this.disableEdit(true);
        return true;
    }
    if (event.type == "keyup") {
        if (this.edit == false) {
            // Currently in readOnly state.
            // Allow <SPACE> key.
            if (event.keyCode == 32) {
                this.enableEdit();
            }
        } else {
            // Currently in edit state.
            if (event.keyCode == 27) this.disableEdit(false); // ESC
        }
        // Otherwise do not do anything.
        return true;
    }
    return true;    
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.editableField.prototype.postCreate = function () {
    // Set Initial readOnly state.
    this.fieldNode.readOnly = true;

    // Set events.
    dojo.connect(this.fieldNode, "ondblclick", this, "onEditCallback");
    dojo.connect(this.fieldNode, "onblur", this, "onEditCallback");
    dojo.connect(this.fieldNode, "onkeyup", this, "onEditCallback");

    return this.inherited("postCreate", arguments);
}

/**
 * This function is used to set widget properties using Object literals.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} [accesskey] 
 * @config {boolean} [autoSave]
 * @config {String} [className] CSS selector.
 * @config {String} [dir] Specifies the directionality of text.
 * @config {boolean} [disabled] Disable element.
 * @config {String} [escape] HTML escape button text (default).
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {String} [label]
 * @config {String} [lang] Specifies the language of attribute values and content.
 * @config {int} [maxLength] 
 * @config {Array} [notify]
 * @config {String} [onClick] Mouse button is clicked on element.
 * @config {String} [onDblClick] Mouse button is double-clicked on element.
 * @config {String} [onKeyDown] Key is pressed down over element.
 * @config {String} [onKeyPress] Key is pressed and released over element.
 * @config {String} [onKeyUp] Key is released over element.
 * @config {String} [onMouseDown] Mouse button is pressed over element.
 * @config {String} [onMouseOut] Mouse is moved away from element.
 * @config {String} [onMouseOver] Mouse is moved onto element.
 * @config {String} [onMouseUp] Mouse button is released over element.
 * @config {String} [onMouseMove] Mouse is moved while over element.
 * @config {boolean} [required] 
 * @config {int} [size]
 * @config {String} [style] Specify style rules inline.
 * @config {int} [tabIndex] Position in tabbing order.
 * @config {String} [title] Provides a title for element.
 * @config {String} [valid] Value of input.
 * @config {String} [value] Value of input.
 * @config {boolean} [visible] Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.editableField.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
    return this.inherited("setProps", arguments);
}

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.editableField.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Explicitly provided readOnly property must be ignored.
    props.readOnly = null;

    // Set properties.
    if (props.autoSave != null) { this.autoSave = props.autoSave; }

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
