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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.staticText");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.widgetBase");

/**
 * This function is used to construct a staticText widget.
 *
 * @name webui.@THEME_JS@.widget.staticText
 * @extends webui.@THEME_JS@.widget._base.widgetBase
 * @class This class contains functions for the staticText widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {String} escape HTML escape value (default).
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {String} style Specify style rules inline.
 * @config {String} title Provides a title for element.
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.staticText",
        webui.@THEME_JS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.escape = true;
    },
    _widgetType: "staticText" // Required for theme properties.
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
webui.@THEME_JS@.widget.staticText.event =
        webui.@THEME_JS@.widget.staticText.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_staticText_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_staticText_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_staticText_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_staticText_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.staticText.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.escape) { props.escape = this.escape; }
    if (this.value) { props.value = this.value; }

    return props;
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
webui.@THEME_JS@.widget.staticText.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }
      
    // Set text value.
    if (props.value) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this._widget._addFragment(this.domNode, props.value, null, this.escape);
    }

    // Set more properties.
    this._setCommonProps(this.domNode, props);
    this._setEventProps(this.domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
