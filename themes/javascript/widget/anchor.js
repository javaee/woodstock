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
dojo.require("webui.@THEME@.*");
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
    this.removeChildNodes(this.domNode);

    // Add contents.
    for (i = 0; i < props.contents.length; i++) {
        this.addFragment(this.domNode, props.contents[i], "last");
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
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.anchor.fillInTemplate = function() {
    // Since the id and name must be the same on IE, we cannot obtain the
    // widget using the DOM node ID via the public functions below.
    var _this = this; // Available in public functions via closure magic.

    // Set public functions.
    this.domNode.getProps = function() { return dojo.widget.byId(_this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(_this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(_this.id).setProps(props); }

    // Create callback function for onclick event.
    dojo.event.connect(this.domNode, "onclick",
        webui.@THEME@.widget.anchor.createOnClickCallback(this.id));

    // Set properties.
    return this.setProps(this.getProps());
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.anchor.getClassName = function() {
    var className = null;
    if (this.href && this.disabled == false) {
        className = webui.@THEME@.widget.props.anchor.className;
    } else {
        className = webui.@THEME@.widget.props.anchor.disabledClassName;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.anchor.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.anchor.getProps = function() {
    var props = {}; 

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
 
    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getJavaScriptProps());

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.anchor.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_anchor_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_anchor_refresh_end",

    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.anchor");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.anchor.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.anchor.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to update widget properties with the
 * following Object literals.
 *
 * <ul>
 * <li>accessKey</li>
 * <li>charset</li>
 * <li>className</li>
 * <li>contents</li>
 * <li>coords</li>
 * <li>dir</li>
 * <li>disabled</li>
 * <li>href</li>
 * <li>hrefLang</li>
 * <li>id</li>
 * <li>lang</li>
 * <li>name</li>
 * <li>onBlur</li>
 * <li>onClick</li>
 * <li>onDblClick</li>
 * <li>onFocus</li>
 * <li>onKeyDown</li>
 * <li>onKeyPress</li>
 * <li>onKeyUp</li>
 * <li>onMouseDown</li>
 * <li>onMouseOut</li>
 * <li>onMouseOver</li>
 * <li>onMouseUp</li>
 * <li>onMouseMove</li>
 * <li>rel</li>
 * <li>rev</li>
 * <li>shape</li>
 * <li>style</li>
 * <li>tabIndex</li>
 * <li>title</li>
 * <li>visible</li>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.anchor.setProps = function(props) {
    if (props == null) {
        return null;
    }

    // Save properties for later updates.
    if (this.isInitialized() == true) {
        // Replace contents -- do not extend.
        if (props.contents) {
            this.contents = null;
        }
        this.extend(this, props);
    }

    // Set id -- anchors must have the same id and name on IE.
    if (props.name) {
        props.id = props.name;
    }

    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();

    // A web app devleoper could return false in order to cancel the 
    // submit. Thus, we will handle this event via the onClick call back.
    if (props.onClick) {
        // Set private function scope on DOM node.
        this.domNode._onclick = (typeof props.onClick == 'string')
            ? new Function("event", props.onClick) : props.onClick;

        // Must be cleared before calling setJavaScriptProps() below.
        props.onClick = null;
    }

    // Set DOM node properties.
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.domNode, props);
    this.setJavaScriptProps(this.domNode, props);

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

    // Add contents.
    this.addContents(props);

    return props; // Return props for subclasses.
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
    refresh: webui.@THEME@.widget.anchor.refresh.processEvent,
    setProps: webui.@THEME@.widget.anchor.setProps,

    // Set defaults.
    templatePath: webui.@THEME@.theme.getTemplatePath("anchor"),
    templateString: webui.@THEME@.theme.getTemplateString("anchor"),
    widgetType: "anchor"
});
