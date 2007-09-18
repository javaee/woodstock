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

dojo.provide("webui.@THEME@.widget.anchor");

dojo.require("dojo.uri.Uri");
dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.anchor = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to add children.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.anchor.addContents = function(props) {
    if (props.contents == null) {
        return false;
    }

    // Remove child nodes.
    this.widget.removeChildNodes(this.domNode);

    // Add contents.
    for (i = 0; i < props.contents.length; i++) {
        this.widget.addFragment(this.domNode, props.contents[i], "last");
    }
    return true;
}

/**
 * Helper function to create callback for onClick event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.anchor.createOnClickCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) { 
        var widget = dojo.widget.byId(id);
        if (widget == null || widget.disabled == true) {
            event.preventDefault();
            return false;
        }

        // If function returns false, we must prevent the request.
        var result = (widget.domNode._onclick)
            ? widget.domNode._onclick(event) : true;
        if (result == false) {
            event.preventDefault();
            return false;
        }
    };
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.anchor.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_anchor_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_anchor_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_anchor_event_state_begin",
        endTopic: "webui_@THEME@_widget_anchor_event_state_end"
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
webui.@THEME@.widget.anchor.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.anchor.superclass.fillInTemplate.call(this, props, frag);

    // Create callback function for onclick event.
    dojo.event.connect(this.domNode, "onclick",
        webui.@THEME@.widget.anchor.createOnClickCallback(this.id));

    return true;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.anchor.getClassName = function() {
    // Set default style.
    var className = (this.href && this.disabled == false)
        ? this.theme.getClassName("ANCHOR")
        : this.theme.getClassName("ANCHOR_DISABLED");

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.anchor.getProps = function() {
    var props = webui.@THEME@.widget.anchor.superclass.getProps.call(this);

    // Set properties.
    if (this.hrefLang) { props.hrefLang = this.hrefLang; }
    if (this.target) { props.target = this.target; }
    if (this.type) { props.type = this.type; }
    if (this.rev) { props.rev = this.rev; }
    if (this.rel) { props.rel = this.rel; }
    if (this.shape) { props.shape = this.shape; }
    if (this.coords) { props.coords = this.coords; }
    if (this.charset) { props.charset = this.charset; }
    if (this.accessKey) { props.accesskey = this.accessKey; }
    if (this.href) { props.href = this.href; }
    if (this.name) { props.name = this.name; } 
    if (this.contents) { props.contents = this.contents; }
    if (this.disabled != null) { props.disabled = this.disabled; }

    return props;
}

/**
 * This function is used to set widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * Note: If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 *
 * @param props Key-Value pairs of properties.
 * @param notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.anchor.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.anchor.superclass.setProps.call(this, props, notify);
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accessKey</li>
 *  <li>charset</li>
 *  <li>className</li>
 *  <li>contents</li>
 *  <li>coords</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>href</li>
 *  <li>hrefLang</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>name</li>
 *  <li>onBlur</li>
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
 *  <li>rel</li>
 *  <li>rev</li>
 *  <li>shape</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.anchor._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Add contents.
    this.addContents(props);

    // Set properties.
    if (props.accessKey) { this.domNode.accesskey = props.accessKey; }
    if (props.charset) { this.domNode.charset = props.charset; }
    if (props.coords) { this.domNode.coords = props.coords; }
    if (props.href) { this.domNode.href = new dojo.uri.Uri(this.href).toString(); }
    if (props.hrefLang) { this.domNode.hrefLang =  props.hrefLang; }
    if (props.name) { this.domNode.name = props.name; }
    if (props.rev) { this.domNode.rev = props.rev; }
    if (props.rel) { this.domNode.rel = props.rel; }
    if (props.shape) { this.domNode.shape = props.shape; }
    if (props.target) { this.domNode.target = props.target; }
    if (props.type) { this.domNode.type = props.type; }

    // Set id -- anchors must have the same id and name on IE.
    if (props.name) {
        props.id = props.name;
    }

    // A web app devleoper could return false in order to cancel the 
    // submit. Thus, we will handle this event via the onClick call back.
    if (props.onClick) {
        // Set private function scope on DOM node.
        this.domNode._onclick = (typeof props.onClick == 'string')
            ? new Function("event", props.onClick) : props.onClick;

        // Must be cleared before calling setEventProps() below.
        props.onClick = null;
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.anchor.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.anchor, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.anchor, {
    // Set private functions.
    addContents: webui.@THEME@.widget.anchor.addContents,
    fillInTemplate: webui.@THEME@.widget.anchor.fillInTemplate,
    getClassName: webui.@THEME@.widget.anchor.getClassName,
    getProps: webui.@THEME@.widget.anchor.getProps,
    setProps: webui.@THEME@.widget.anchor.setProps,
    _setProps: webui.@THEME@.widget.anchor._setProps,

    // Set defaults.
    event: webui.@THEME@.widget.anchor.event,
    widgetType: "anchor"
});
