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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.anchorBase");

@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.anchorBase
 * @extends @JS_NS@.widget._base.widgetBase
 * @class This class contains functions for widgets that extend anchorBase.
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
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.anchorBase",
        @JS_NS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.disabled = false;
    }
});

/**
 * Helper function to add children.
 *
 * @param props Key-Value pairs of properties.
 * @config {Array} contents The contents of the anchor body.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.anchorBase.prototype._addContents = function(props) {
    if (props.contents == null) {
        return false;
    }

    // Remove child nodes.
    this._widget._removeChildNodes(this._domNode);

    // Add contents.
    for (i = 0; i < props.contents.length; i++) {
        this._widget._addFragment(this._domNode, props.contents[i], "last");
    }
    return true;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget._base.anchorBase.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.hrefLang != null) { props.hrefLang = this.hrefLang; }
    if (this.target != null) { props.target = this.target; }
    if (this.type != null) { props.type = this.type; }
    if (this.rev != null) { props.rev = this.rev; }
    if (this.rel != null) { props.rel = this.rel; }
    if (this.shape != null) { props.shape = this.shape; }
    if (this.coords != null) { props.coords = this.coords; }
    if (this.charset != null) { props.charset = this.charset; }
    if (this.accessKey != null) { props.accesskey = this.accessKey; }
    if (this.href != null) { props.href = this.href; }
    if (this.name != null) { props.name = this.name; } 
    if (this.contents != null) { props.contents = this.contents; }
    if (this.disabled != null) { props.disabled = this.disabled; }

    return props;
};

/**
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.anchorBase.prototype._onClickCallback = function(event) {
    if (this.disabled == true) {
        event.preventDefault();
        return false;
    }

    // If function returns false, we must prevent the request.
    var result = (this._domNode._onclick)
        ? this._domNode._onclick(event) : true;
    if (result == false) {
        event.preventDefault();
        return false;
    }
    return true;
};

/**
 * This function is used to set widget properties using Object literals. Please
 * see the constructor detail for a list of supported properties.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.anchorBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return this._inherited("setProps", arguments);
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
@JS_NS@.widget._base.anchorBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Add contents.
    this._addContents(props);

    // Set properties.
    if (props.accessKey != null) { this._domNode.accesskey = props.accessKey; }
    if (props.charset != null) { this._domNode.charset = props.charset; }
    if (props.coords != null) { this._domNode.coords = props.coords; }
        if (props.href) {
            // If context path is provided, then check whether the image has
            // context path already appended and if not, append it.
            if (this.prefix) {
                props.href = 
                    @JS_NS@.widget.common._appendPrefix(this.prefix, props.href);
            }
            this._domNode.href = props.href; 
        }
    if (props.hrefLang != null) { this._domNode.hrefLang =  props.hrefLang; }
    if (props.name != null) { this._domNode.name = props.name; }
    if (props.rev != null) { this._domNode.rev = props.rev; }
    if (props.rel != null) { this._domNode.rel = props.rel; }
    if (props.shape != null) { this._domNode.shape = props.shape; }
    if (props.target != null) { this._domNode.target = props.target; }
    if (props.type != null) { this._domNode.type = props.type; }

    // Set id -- anchors must have the same id and name on IE.
    if (props.name != null) {
        props.id = props.name;
    }

    // A web app devleoper could return false in order to cancel the 
    // submit. Thus, we will handle this event via the onClick call back.
    if (props.onClick) {
        // Set private function scope on DOM node.
        this._domNode._onclick = (typeof props.onClick == 'string')
            ? new Function("event", props.onClick) : props.onClick;

        // Must be cleared before calling _setEventProps() below.
        props.onClick = null;
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
