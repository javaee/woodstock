/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

@JS_NS@._dojo.provide("@JS_NS@.widget.editableField");

@JS_NS@._dojo.require("@JS_NS@.widget.textField");

/**
 * This function is used to construct a editableField widget.
 *
 * @constructor
 * @name @JS_NS@.widget.editableField
 * @extends @JS_NS@.widget.textField
 * @class This class contains functions for the editableField widget.
 * <p>
 * The editableField widget renders a borderless readonly text field that can be
 * edited inline. An initial read-only state resembles regular static text, and
 * it can be edited by using the onDblClick event or when the space key is 
 * pressed. Once the field looses the focus, it enters a read-only state again.
 * </p><p>
 * If the editable field is modified and if autoSave is set to its default 
 * value, which is true, the updated data will be automatically submitted to the
 * server by an Ajax request.
 * </p><p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is an editable field",
 *       widgetType: "editableField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * editable field is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is an editable field",
 *       widgetType: "editableField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Editable Field State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("field1"); // Get editable field
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the editable field is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is an editable field",
 *       widgetType: "editableField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Editable Field" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("field1"); // Get editable field
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can take an optional list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,..."). When no parameter is given, 
 * the refresh function acts as a reset. That is, the widget will be redrawn 
 * using values set server-side, but not updated.
 * </p><p>
 * <h3>Example 3b: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a editable field using the 
 * refresh function. The execute property of the refresh function is used to
 * define the client id which is to be submitted and updated server-side. When
 * the user clicks on the checkbox, the input value is updated server-side and 
 * the editable field is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is an editable field",
 *       widgetType: "editableField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Change Editable Field Value" },
 *       onClick="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("field1"); // Get editable field
 *       return widget.refresh("cb1"); // Asynchronously refresh while submitting checkbox value
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can optionally take a list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,...").
 * </p><p>
 * <h3>Example 4: Subscribing to event topics</h3>
 * </p><p>
 * When a widget is manipulated, some features may publish event topics for
 * custom AJAX implementations to listen for. For example, you may listen for
 * the refresh event topic using:
 * </p><pre><code>
 * &lt;script type="text/javascript">
 *    var foo = {
 *        // Process refresh event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        processRefreshEvent: function(props) {
 *            // Get the widget id.
 *            if (props.id == "field1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.editableField.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accesskey 
 * @config {boolean} autoSave
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} escape HTML escape button text (default).
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {int} maxLength 
 * @config {Array} notify
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {boolean} required 
 * @config {int} size
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {String} valid Value of input.
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.editableField",
        @JS_NS@.widget.textField, {
    // Set defaults.
    constructor: function() {
        this.edit = false;
    },
    _widgetType: "editableField" // Required for theme properties.
});
   
/**
 * Helper function to disable edit mode.
 *
 * This function will commit or rollback the changes made in the field, and
 * setup appropriate style on the field.
 *
 * @param {boolean} acceptChanges Optional parameter. 
 * <p>
 * If true, the entered data (_fieldNode value) will be commited through the Ajax
 * submit() request. 
 * </p><p>
 * If false, the entered data (_fieldNode value) will be rolled back to previos 
 * state (value is saved before field enters editable state).
 * </p><p>
 * If not specified, no changes to the field value will made, and only styles 
 * will be modified.
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.editableField.prototype._disableEdit = function(acceptChanges) {
    if (acceptChanges == true) {
        // If savedValue does not exist, we have not edited the field yet
        if (this.autoSave == true && this.savedValue && 
                this.savedValue != this._fieldNode.value) {
            this.submit();
        }
    } else if (acceptChanges == false) {
        if (this.savedValue)   {
            this._fieldNode.value = this.savedValue;
        }
    }
    this.edit = false;
    this.savedValue = null;
    this._fieldNode.className = this._getInputClassName();   
    this._fieldNode.readOnly = true;
    return true;
};

/**
 * Helper function to enable edit mode.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.editableField.prototype._enableEdit = function() {
    // Save the current value.
    this.savedValue = this._fieldNode.value;
        
    this.edit = true;
    this._fieldNode.className = this._getInputClassName();   
    this._fieldNode.readOnly = false;
    this._fieldNode.focus(); // In case function has been called programmatically, not by event.
    this._fieldNode.select();
    return true;
};

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.editableField.event =
        @JS_NS@.widget.editableField.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_editableField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_editableField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_editableField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_editableField_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_editableField_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_editableField_event_submit_end"
    }
};

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
@JS_NS@.widget.editableField.prototype._getInputClassName = function() {    
    // Set default style.
    if (this.disabled == true) {
        return  this._theme.getClassName("EDITABLE_FIELD_DISABLED","");
    }

    // Apply invalid style.
    var validStyle =  (this.valid == false) 
        ? " " + this._theme.getClassName("EDITABLE_FIELD_INVALID","")
        : ""; 

    return (this.edit == true)
        ? this._theme.getClassName("EDITABLE_FIELD_EDIT","") + validStyle
        : this._theme.getClassName("EDITABLE_FIELD_DEFAULT","") + validStyle;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.editableField.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.autoSave!= null) { props.autoSave = this.autoSave; }

    return props;
};

/**
 * Process user gesture events on the field, such as dblclick, onblur, 
 * onkeyup, etc.
 *
 * @param {Event} event The JavaScript event
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.editableField.prototype._onEditCallback = function(event) {
    if (event == null) {
        return false;
    }
    if (event.type == "dblclick") {
        this._enableEdit();
        return true;
    }
    if (event.type == "blur") {
        this._disableEdit(true);
        return true;
    }
    if (event.type == "keyup") {
        if (this.edit == false) {
            // Currently in readOnly state.
            // Allow <SPACE> key.
            if (event.keyCode == 32) {
                this._enableEdit();
            }
        } else {
            // Currently in edit state.
            if (event.keyCode == 27) this._disableEdit(false); // ESC
        }
        // Otherwise do not do anything.
        return true;
    }
    return true;    
};

/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.editableField.prototype._postCreate = function () {
    // Set Initial readOnly state.
    this._fieldNode.readOnly = true;

    // Set events.
    this._dojo.connect(this._fieldNode, "ondblclick", this, "_onEditCallback");
    this._dojo.connect(this._fieldNode, "onblur", this, "_onEditCallback");
    this._dojo.connect(this._fieldNode, "onkeyup", this, "_onEditCallback");

    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to set widget properties. Please see the constructor 
 * detail for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.editableField.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Explicitly provided readOnly property must be ignored.
    props.readOnly = null;

    // Set properties.
    if (props.autoSave != null) { this.autoSave = props.autoSave; }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
