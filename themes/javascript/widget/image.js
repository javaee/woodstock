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

dojo.provide("webui.@THEME@.widget.image");

dojo.require("dojo.widget.*");
dojo.require("dojo.uri.Uri");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.image = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to get widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
 */
webui.@THEME@.widget.image.getProps = function() {
    var props = webui.@THEME@.widget.image.superclass.getProps.call(this);

    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.align) { props.align = this.align; }
    if (this.border != null) { props.border = this.border; }
    if (this.height) { props.height = this.height; }
    if (this.hspace) { props.hspace = this.hspace; }
    if (this.longDesc) { props.longDesc = this.longDesc; }
    if (this.src) { props.src = this.src; }
    if (this.vspace) { props.vspace = this.vspace; }
    if (this.width) { props.width = this.width; }

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.image.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_image_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_image_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.image");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.image.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.image.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.image.setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Note: This widget has trouble using this.extend(), possibly due to how
    // alarm and alert provide image properties to setProps().

    // Extend widget object for later updates.
    Object.extend(this, props);

    // Set properties.
    return this.setWidgetProps(props);
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>alt</li>
 *  <li>align</li>
 *  <li>border</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>height</li>
 *  <li>hspace</li>
 *  <li>id</li>
 *  <li>lang</li>>
 *  <li>longDesc</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>src</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>visible</li>
 *  <li>vspace</li>
 *  <li>width</li>
 * </ul>
 *
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.image.setWidgetProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.alt) { this.domNode.alt = props.alt; }
    if (props.align) { this.domNode.align = props.align; }
    if (props.border != null) { this.domNode.border = props.border; }
    if (props.height) { this.domNode.height = props.height; }
    if (props.hspace) { this.domNode.hspace = props.hspace; }
    if (props.longDesc) { this.domNode.longDesc = props.longDesc; }
    if (props.src) { this.domNode.src = new dojo.uri.Uri(props.src).toString(); }
    if (props.vspace) { this.domNode.vspace = props.vspace; }
    if (props.width) { this.domNode.width = props.width; }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setJavaScriptProps(this.domNode, props);

    // Set core props.
    return webui.@THEME@.widget.image.superclass.setWidgetProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.image, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.image, {
    // Set private functions.
    getProps: webui.@THEME@.widget.image.getProps,
    refresh: webui.@THEME@.widget.image.refresh.processEvent,
    setProps: webui.@THEME@.widget.image.setProps,
    setWidgetProps: webui.@THEME@.widget.image.setWidgetProps,

    // Set defaults.
    border: 0,
    widgetType: "image"
});
