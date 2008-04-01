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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget.imageHyperlink");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget.hyperlink");

/**
 * @name webui.@THEME_JS@.widget.imageHyperlink
 * @extends webui.@THEME_JS@.widget.hyperlink
 * @class This class contains functions for the imageHyperlink widget.
 * @constructor This function is used to construct a imageHyperlink widget.
 */
webui.@THEME_JS@._base.dojo.declare("webui.@THEME_JS@.widget.imageHyperlink",
        webui.@THEME_JS@.widget.hyperlink, {
    // Set defaults.
    _widgetName: "imageHyperlink" // Required for theme properties.
});

/**
 * Helper function to add children.
 *
 * @param props Key-Value pairs of properties.
 * @config {Array} contents The contents of the anchor body.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.imageHyperlink.prototype._addContents = function(props) {
    if (props.contents == null) {
        return false;
    }

    // Remove child nodes.
    this._widget._removeChildNodes(this.leftContentsContainer);
    this._widget._removeChildNodes(this.rightContentsContainer);

    // Add contents.
    for (i = 0; i <props.contents.length; i++) {
        this._widget._addFragment(this.leftContentsContainer, props.contents[i], "last");
        this._widget._addFragment(this.rightContentsContainer, props.contents[i], "last");
    }
    return true;
};

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME_JS@.widget.imageHyperlink.event =
        webui.@THEME_JS@.widget.imageHyperlink.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_imageHyperlink_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_imageHyperlink_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_imageHyperlink_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_imageHyperlink_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.imageHyperlink.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.enabledImage) { props.enabledImage = this.enabledImage; }
    if (this.disabledImage) { props.disabledImage = this.disabledImage; }
    if (this.imagePosition) { props.imagePosition = this.imagePosition; }

    return props;
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
webui.@THEME_JS@.widget.imageHyperlink.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this.enabledImageContainer.id = this.id + "_enabled";
        this.disabledImageContainer.id = this.id + "_disabled";
        this.leftContentsContainer.id = this.id + "_leftContents";
        this.rightContentsContainer.id = this.id + "_rightContents";
    }
    return this._inherited("_postCreate", arguments);
};

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
 * @config {Object} disabledImage
 * @config {Object} enabledImage
 * @config {String} href
 * @config {String} hrefLang
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} imagePosition
 * @config {String} lang Specifies the language of attribute values and content.
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
webui.@THEME_JS@.widget.imageHyperlink.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
    return this._inherited("setProps", arguments);
};

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
webui.@THEME_JS@.widget.imageHyperlink.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Show en/disabled images.
    if (props.disabled != null) {
        var disabled = new Boolean(props.disabled).valueOf();

        // We need to hide/show images only when the disabed image is specified.
        if (this.disabledImage) { 
            this._common.setVisibleElement(this.enabledImageContainer, !disabled);
            this._common.setVisibleElement(this.disabledImageContainer, disabled);
        }
    }

    // Add enabled image.
    if (props.enabledImage) {
        // Update/add fragment.
        this._widget._updateFragment(this.enabledImageContainer, this.enabledImage.id, 
            props.enabledImage);
    }

    // Add disabled image.
    if (props.disabledImage) {
        // Update/add fragment.
        this._widget._updateFragment(this.disabledImageContainer, this.disabledImage.id, 
            props.disabledImage);
    }

    // Set image position.
    if (props.imagePosition) {
        var left = (props.imagePosition == "left");
        this._common.setVisibleElement(this.leftContentsContainer, !left);
        this._common.setVisibleElement(this.rightContentsContainer, left);    
    }

    // Add contents.
    this._addContents(props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
