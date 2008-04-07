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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget.image");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@._base.browser");
webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget._base.widgetBase");

/**
 * This function is used to construct a image widget.
 *
 * @name webui.@THEME_JS@.widget.image
 * @extends webui.@THEME_JS@.widget._base.widgetBase
 * @class This class contains functions for the image widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} alt Alternate text for image input.
 * @config {String} align Alignment of image input.
 * @config {String} border
 * @config {String} className CSS selector.
 * @config {String} prefix The application context path of image
 * @config {String} dir Specifies the directionality of text.
 * @config {String} height 
 * @config {String} hspace 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} longDesc 
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
 * @config {String} src 
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 * @config {String} vspace
 * @config {String} width
 */
webui.@THEME_JS@._base.dojo.declare("webui.@THEME_JS@.widget.image",
        webui.@THEME_JS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.border = 0;
    },
    _highContrastMode: null, // Must be available to all image widgets.
    _widgetType: "image" // Required for theme properties.
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
webui.@THEME_JS@.widget.image.event =
        webui.@THEME_JS@.widget.image.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_image_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_image_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_image_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_image_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.image.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.icon) { props.icon = this.icon; }
    if (this.align) { props.align = this.align; }
    if (this.border != null) { props.border = this.border; }
    if (this.height) { props.height = this.height; }
    if (this.hspace) { props.hspace = this.hspace; }
    if (this.longDesc) { props.longDesc = this.longDesc; }
    if (this.src) { props.src = this.src; }
    if (this.vspace) { props.vspace = this.vspace; }
    if (this.width) { props.width = this.width; }

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
webui.@THEME_JS@.widget.image.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Style must be set first (e.g., for absolute positioning) -- some style
    // properties may be overridden later by the combined image.
    this._setCommonProps(this.domNode, props);

    // Clear style properties if icon was previously set.
    if (props.src && this.icon) {
        this.domNode.style.border = "";
        this.domNode.style.backgroundImage = "";
        this.domNode.style.backgroundPosition = "";
        this.domNode.style.height = "";
        this.domNode.style.width = "";
    }

    // Set properties.
    if (props.icon) {
        // IE6 has issues with "png" images. IE6 png issue can be fixed but that
        // needs an outermost <span> tag. 
        //
        // <span style="overflow: hidden; width:13px;height:13px; padding: 0px;zoom: 1";>
        // <img src="dot.gif"
        //  style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='testImage.png',sizingMethod='crop');
        //  margin-left:-0px;
        //  margin-top:-26px; border: none; height:39px;width:13px;"/>
        // </span>
        //
        // For now, skipping the combined image approach for IE6. Also need fix 
        // for Safari.
        var iconProps = this._theme._getImage(props.icon);
        if (iconProps == null) {
            console.debug("Error: theme icon '" + props.icon + "' not found.");
        } else {
            var mapKey = iconProps['map_key'];
            if (mapKey != null && !webui.@THEME_JS@._base.browser._isIe6()
                    && !this._widget._isHighContrastMode()) {
                // Note: Comparing height/width against "actual" properties is not a
                // valid test -- DOT images don't have a default size, for example.
                if (iconProps['top'] != null && iconProps['actual_height'] != null 
                        && iconProps['actual_width'] != null) {               
                    var transImage = this._theme._getImage("DOT");
                    var combinedImage = this._theme._getImage(mapKey);

                    // Set style properties.
                    this.domNode.style.border = "0";
                    this.domNode.style.backgroundImage = "url(" + combinedImage['src'] + ")";
                    this.domNode.style.backgroundPosition = "0px " + iconProps['top'] + "px";
                    this.domNode.style.height = iconProps['actual_height'] + "px";
                    this.domNode.style.width = iconProps['actual_width'] + "px";

                    // Set transparent image.
                    iconProps.src = transImage['src'];
                }
            }
            // Assign icon properties, even if combined image is not used.
            if (iconProps.alt) { this.domNode.alt = iconProps.alt; }
            if (iconProps.height) { this.domNode.height = iconProps.height; }
            if (iconProps.src) { this.domNode.src = iconProps.src; }
            if (iconProps.width) { this.domNode.width = iconProps.width; }
        }
    } else {
        // Icon properties take precedence.
        if (props.alt) { this.domNode.alt = props.alt; }
        if (props.height) { this.domNode.height = props.height; }
        if (props.src) {
                
            // If context path is provided, then check whether the image has
            // context path already appended and if not, append it.
            if (this.prefix) {
                props.src = 
                    webui.@THEME_JS@.widget.common._appendPrefix(this.prefix, props.src);                
            }
            this.domNode.src = props.src;  
        } 
        if (props.width) { this.domNode.width = props.width; }
    }
    if (props.align) { this.domNode.align = props.align; }
    if (props.border != null) { this.domNode.border = props.border; }
    if (props.hspace) { this.domNode.hspace = props.hspace; }
    if (props.longDesc) { this.domNode.longDesc = props.longDesc; }    
    if (props.vspace) { this.domNode.vspace = props.vspace; }

    // Set more properties.
    this._setEventProps(this.domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
