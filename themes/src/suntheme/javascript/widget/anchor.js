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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.anchor");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.anchorBase");

/**
 * This function is used to construct an anchor widget.
 *
 * @name webui.@THEME_JS@.widget.anchor
 * @extends webui.@THEME_JS@.widget._base.anchorBase
 * @class This class contains functions for the anchor widget.
 * @constructor
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
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.anchor",
        webui.@THEME_JS@.widget._base.anchorBase, {
    // Set defaults.
    _widgetType: "anchor" // Required for theme properties.
});

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME_JS@.widget.anchor.event =
        webui.@THEME_JS@.widget.anchor.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_anchor_event_refresh_begin",
        
        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_anchor_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_anchor_event_state_begin",
        
        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_anchor_event_state_end"
    }
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
webui.@THEME_JS@.widget.anchor.prototype._getClassName = function() {
    // Set default style.
    var className = (this.href && this.disabled == false)
        ? this._theme._getClassName("ANCHOR","")
        : this._theme._getClassName("ANCHOR_DISABLED","");

    return (this.className)
        ? className + " " + this.className
        : className;
};

/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.anchor.prototype._postCreate = function () {
    // Create callback function for onclick event.
    this._dojo.connect(this.domNode, "onclick", this, "_onClickCallback");

    return this._inherited("_postCreate", arguments);
};
