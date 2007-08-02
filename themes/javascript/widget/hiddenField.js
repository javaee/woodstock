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

dojo.provide("webui.@THEME@.widget.hiddenField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.hiddenField = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.hiddenField.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_hiddenField_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_hiddenField_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_hiddenField_event_state_begin",
        endTopic: "webui_@THEME@_widget_hiddenField_event_state_end"
    },

    /**
     * This closure is used to process submit events.
     */
    submit: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_hiddenField_event_submit_begin",
        endTopic: "webui_@THEME@_widget_hiddenField_event_submit_end"
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
webui.@THEME@.widget.hiddenField.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.hiddenField.superclass.fillInTemplate.call(this, props, frag);

    // Set public functions. 
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }

    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.hiddenField.getProps = function() {
    var props = webui.@THEME@.widget.hiddenField.superclass.getProps.call(this);

    // Set properties.
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.name) { props.name = this.name; }
    if (this.value) { props.value = this.value; }

    return props;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>name</li>
 *  <li>value</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.hiddenField._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.name) { this.domNode.name = props.name; }
    if (props.value) { this.domNode.value = props.value; }
    if (props.disabled != null) { 
        this.domNode.disabled = new Boolean(props.disabled).valueOf();
    }

    // Set remaining properties.
    return webui.@THEME@.widget.hiddenField.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.hiddenField, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.hiddenField, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.hiddenField.fillInTemplate,
    getProps: webui.@THEME@.widget.hiddenField.getProps,
    _setProps: webui.@THEME@.widget.hiddenField._setProps,
    submit: webui.@THEME@.widget.widgetBase.event.submit.processEvent,

    // Set defaults.
    disabled: false,
    event: webui.@THEME@.widget.hiddenField.event,
    widgetType: "hiddenField"
});
