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
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.textArea.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_textArea_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_textArea_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_textArea_event_state_begin",
        endTopic: "webui_@THEME@_widget_textArea_event_state_end"
    },

    /**
     * This closure is used to process submit events.
     */
    submit: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_textArea_event_submit_begin",
        endTopic: "webui_@THEME@_widget_textArea_event_submit_end"
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
webui.@THEME@.widget.textArea.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.textArea.superclass.fillInTemplate.call(this, props, frag);

    // Set events.                
    if (this.autoSave > 0) {
        this.autoSaveTimerId = setInterval(
            webui.@THEME@.widget.textArea.createSubmitCallback(this.id), 
                this.autoSave);  
    }
    return true;
}

/**
 * Helper function to obtain HTML input element class names.
 */
webui.@THEME@.widget.textArea.getInputClassName = function() {
    // Set default style.    
    return (this.disabled == true)
        ? webui.@THEME@.widget.props.textArea.disabledClassName
        : webui.@THEME@.widget.props.textArea.className;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
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
 * This function is used to set widget properties with the following 
 * Object literals.
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
 *  <li>rows</li>
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
webui.@THEME@.widget.textArea._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.   
    if (props.cols > 0) { this.fieldNode.cols = props.cols; }
    if (props.rows > 0) { this.fieldNode.rows = props.rows; }
    
    // Cancel autosave if it has been changed to <=0
    if (props.autoSave <= 0 && this.autoSaveTimerId && this.autoSaveTimerId != null ) {
        clearTimeout(this.autoSaveTimerId);
        this.autoSaveTimerId = null;
    }

    // Set label className -- must be set before calling superclass.
    if (props.label) {
        props.label.className = (props.label.className)
            ? webui.@THEME@.widget.props.textArea.labelTopAlignStyle + " " + props.label.className
            : webui.@THEME@.widget.props.textArea.labelTopAlignStyle;
    }

    // Set remaining properties.
    return webui.@THEME@.widget.textArea.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.textArea, webui.@THEME@.widget.textField);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.textArea, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.textArea.fillInTemplate,
    getInputClassName: webui.@THEME@.widget.textArea.getInputClassName,
    getProps: webui.@THEME@.widget.textArea.getProps,
    _setProps: webui.@THEME@.widget.textArea._setProps,
    submit: webui.@THEME@.widget.widgetBase.event.submit.processEvent,
    
    // Set defaults.
    autoSave: 0,
    cols: 20,
    event: webui.@THEME@.widget.textArea.event,
    rows: 3,
    widgetType: "textArea"
});
