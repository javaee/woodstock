/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.dropDown");

webui.@THEME@.dojo.require("webui.@THEME@.widget.selectBase");

/**
 * @name webui.@THEME@.widget.dropDown
 * @extends webui.@THEME@.widget.selectBase
 * @class This class contains functions for the dropDown widget.
 * @constructor This function is used to construct a dropDown widget.
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.dropDown", webui.@THEME@.widget.selectBase, {
    // Set defaults.
    width: webui.@THEME@.theme.common.getMessage("dropDown.width", null),
    submitForm: false,
    titleOptionLabel: "DropDown.titleOptionLabel",
    widgetName: "dropDown" // Required for theme properties.
});

/**
 * Helper function called by onChange event to set the proper
 * selected, and disabled styles.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.dropDown.prototype.changed = function() {
    if (this.submitForm != true) {
        // Drop down changed.
        return this.inherited("changed", arguments);
    } else {
        // Jump drop down changed.
        var jumpDropdown = this.listContainer; 

        // Find the <form> for this drop down
        var form = jumpDropdown.form; 

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
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME@.widget.dropDown.event =
        webui.@THEME@.widget.dropDown.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_dropDown_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_dropDown_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_dropDown_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_dropDown_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_dropDown_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_dropDown_event_submit_end"
    }
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.dropDown.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Get properties.
    if (this.submitForm != null) { props.submitForm = this.submitForm; }

    return props;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.dropDown.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.submitterHiddenNode.id = this.id + "_submitter";
    }
    
    // Set style classes
    if (this.submitForm) {
        if (new Boolean(this.submitForm).valueOf() == true) {
            this.selectClassName = this.widget.getClassName("MENU_JUMP");
            this.selectDisabledClassName = this.widget.getClassName("MENU_JUMP_DISABLED");
            this.optionClassName = this.widget.getClassName("MENU_JUMP_OPTION");
            this.optionDisabledClassName = this.widget.getClassName("MENU_JUMPOPTION_DISABLED");
            this.optionGroupClassName = this.widget.getClassName("MENU_JUMP_OPTION_GROUP");
            this.optionSelectedClassName = this.widget.getClassName("MENU_JUMP_OPTION_SELECTED");
            this.optionSeparatorClassName = this.widget.getClassName("MENU_JUMP_OPTION_SEPARATOR");
        } else {
            this.selectClassName = this.widget.getClassName("MENU_STANDARD");
            this.selectDisabledClassName = this.widget.getClassName("MENU_STANDARD_DISABLED");
            this.optionClassName = this.widget.getClassName("MENU_STANDARD_OPTION");
            this.optionDisabledClassName = this.widget.getClassName("MENU_STANDARD_DISABLED");
            this.optionGroupClassName = this.widget.getClassName("MENU_STANDARD_OPTION_GROUP");
            this.optionSelectedClassName = this.widget.getClassName("MENU_STANDARD_OPTION_SELECTED");
            this.optionSeparatorClassName = this.widget.getClassName("MENU_STANDARD_OPTION_SEPARATOR");
        }
    }
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
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label 
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {boolean} multiple 
 * @config {String} onBlur Element lost focus.
 * @config {String} onChange 
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onFocus Element received focus.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {String} onSelect
 * @config {Array} options 
 * @config {int} size 
 * @config {String} style Specify style rules inline.
 * @config {boolean} submitForm 
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.dropDown.prototype.setProps = function(props, notify) {
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
webui.@THEME@.widget.dropDown.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Add attributes to the hidden input for jump drop down.
    if (props.submitForm != null && props.submitForm == true) {
        this.submitterHiddenNode.name = this.submitterHiddenNode.id;
        this.submitterHiddenNode.value = "false";
    }

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
