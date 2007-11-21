// widget/anchorBase.js
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

dojo.provide("webui.@THEME@.widget.anchorBase");

dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.anchorBase
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for widgets that extend anchorBase.
 * @static
 */
dojo.declare("webui.@THEME@.widget.anchorBase", webui.@THEME@.widget.widgetBase);

/**
 * Helper function to add children.
 *
 * @param props Key-Value pairs of properties.
 * @config {Array} contents The contents of the anchor body.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.anchorBase.prototype.addContents = function(props) {
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
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.anchorBase.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

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
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.anchorBase.prototype.onClickCallback = function(event) {
    if (this.disabled == true) {
        event.preventDefault();
        return false;
    }

    // If function returns false, we must prevent the request.
    var result = (this.domNode._onclick)
        ? this.domNode._onclick(event) : true;
    if (result == false) {
        event.preventDefault();
        return false;
    }
    return true;
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
 * @config {String} accessKey
 * @config {String} charset
 * @config {String} className CSS selector.
 * @config {Array} contents
 * @config {String} coords
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} href
 * @config {String} hrefLang
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} name 
 * @config {String} onBlur Element lost focus.
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
 * @config {String} rel
 * @config {String} rev
 * @config {String} shape
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.anchorBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
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
webui.@THEME@.widget.anchorBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Add contents.
    this.addContents(props);

    // Set properties.
    if (props.accessKey) { this.domNode.accesskey = props.accessKey; }
    if (props.charset) { this.domNode.charset = props.charset; }
    if (props.coords) { this.domNode.coords = props.coords; }
    if (props.href) { this.domNode.href = props.href }
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
    return this.inherited("_setProps", arguments);
}
