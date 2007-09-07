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

dojo.provide("webui.@THEME@.widget.staticText");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.staticText = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.staticText.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_staticText_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_staticText_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_staticText_event_state_begin",
        endTopic: "webui_@THEME@_widget_staticText_event_state_end"
    }
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.staticText.getProps = function() {
    var props = webui.@THEME@.widget.staticText.superclass.getProps.call(this);

    // Set properties.
    if (this.escape) { props.escape = this.escape; }
    if (this.value) { props.value = this.value; }

    return props;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>escape</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>style</li>
 *  <li>title</li>
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
webui.@THEME@.widget.staticText._setProps = function(props) {
    if (props == null) {
        return false;
    }
      
    // Set text value.
    if (props.value) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.widget.addFragment(this.domNode, props.value, null, this.escape);
    }

    // Set more properties..
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.staticText.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.staticText, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.staticText, {
    // Set private functions.
    getProps: webui.@THEME@.widget.staticText.getProps,
    _setProps: webui.@THEME@.widget.staticText._setProps,

    // Set defaults.
    escape: true,
    event: webui.@THEME@.widget.staticText.event,
    widgetType: "staticText"
});
