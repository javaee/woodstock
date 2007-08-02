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

dojo.require("dojo.uri.Uri");
dojo.require("dojo.widget.*");
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
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.image.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_image_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_image_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_image_event_state_begin",
        endTopic: "webui_@THEME@_widget_image_event_state_end"
    }
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
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
webui.@THEME@.widget.image.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Note: This widget has trouble using this.extend(), possibly due to how
    // alarm and alert provide image properties to setProps(). For now, we need
    // to bypass the "superclass" function.

    // Extend widget object for later updates.
    Object.extend(this, props);

    // Set properties.
    this._setProps(props);

    // Notify listeners state has changed.
    if (new Boolean(notify).valueOf() == true) {
        this.stateChanged(props);
    }
    return true;
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
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.image._setProps = function(props) {
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
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.image.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.image, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.image, {
    // Set private functions.
    getProps: webui.@THEME@.widget.image.getProps,
    setProps: webui.@THEME@.widget.image.setProps,
    _setProps: webui.@THEME@.widget.image._setProps,

    // Set defaults.
    border: 0,
    event: webui.@THEME@.widget.image.event,
    widgetType: "image"
});
