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

@JS_NS@._dojo.provide("@JS_NS@.widget.image");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a image widget.
 *
 * @name @JS_NS@.widget.image
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the image widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} alt Alternate text for image input.
 * @config {String} align Alignment of image input.
 * @config {String} border
 * @config {String} className CSS selector.
 * @config {String} prefix The application context path of image.
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
@JS_NS@._dojo.declare("@JS_NS@.widget.image", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
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
@JS_NS@.widget.image.event =
        @JS_NS@.widget.image.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_image_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_image_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_image_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_image_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.image.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.alt != null) { props.alt = this.alt; }
    if (this.icon != null) { props.icon = this.icon; }
    if (this.align != null) { props.align = this.align; }
    if (this.border != null) { props.border = this.border; }
    if (this.height != null) { props.height = this.height; }
    if (this.hspace != null) { props.hspace = this.hspace; }
    if (this.longDesc != null) { props.longDesc = this.longDesc; }
    if (this.src != null) { props.src = this.src; }
    if (this.vspace != null) { props.vspace = this.vspace; }
    if (this.width != null) { props.width = this.width; }

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
@JS_NS@.widget.image.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Style must be set first (e.g., for absolute positioning) -- some style
    // properties may be overridden later by the combined image.
    this._setCommonProps(this._domNode, props);

    // Clear style properties if icon was previously set.
    if (props.src && this.icon) {
        this._domNode.style.border = "";
        this._domNode.style.backgroundImage = "";
        this._domNode.style.backgroundPosition = "";
        this._domNode.style.height = "";
        this._domNode.style.width = "";
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
        var iconProps = this._theme.getImage(props.icon);
        if (iconProps == null) {
            console.debug("Error: theme icon '" + props.icon + "' not found.");
        } else {
            var mapKey = iconProps['map_key'];
            if (mapKey != null && !@JS_NS@._base.browser._isIe6()
                    && !this._widget._isHighContrastMode()) {
                // Note: Comparing height/width against "actual" properties is not a
                // valid test -- DOT images don't have a default size, for example.
                if (iconProps['top'] != null && iconProps['actual_height'] != null 
                        && iconProps['actual_width'] != null) {               
                    var transImage = this._theme.getImage("DOT");
                    var combinedImage = this._theme.getImage(mapKey);

                    // Set style properties.
                    this._domNode.style.border = "0";
                    this._domNode.style.backgroundImage = "url(" + combinedImage['src'] + ")";
                    this._domNode.style.backgroundPosition = "0px " + iconProps['top'] + "px";
                    this._domNode.style.height = iconProps['actual_height'] + "px";
                    this._domNode.style.width = iconProps['actual_width'] + "px";

                    // Set transparent image.
                    iconProps.src = transImage['src'];
                }
            }
            
            // Assign icon properties, even if combined image is not used.
            if (iconProps.alt != null) { this._domNode.alt = iconProps.alt; }
            if (iconProps.height != null) { this._domNode.height = iconProps.height; }
            if (iconProps.width != null) { this._domNode.width = iconProps.width; }            
            if (iconProps.src) {
                
                // Apply IE6 specific fix for png images if needed and obtain 
                // the modified style.
                props.style = this._setSrcProperty(iconProps);                                                
            }
        }
    } else {
        // Icon properties take precedence.
        if (props.alt != null) { this._domNode.alt = props.alt; }
        if (props.height != null) { this._domNode.height = props.height; }
        if (props.width != null) { this._domNode.width = props.width; }
        if (props.src) {
            
            // If context path is provided, then check whether the image has
            // context path already appended and if not, append it.            
            if (this.prefix) {
                props.src = 
                    @JS_NS@.widget.common._appendPrefix(this.prefix, props.src);                
            }                
            
            // Apply IE6 specific fix for png images if needed and obtain the 
            // modified style.
            props.style = this._setSrcProperty(props);
        }
    }
    if (props.align != null) { this._domNode.align = props.align; }
    if (props.border != null) { this._domNode.border = props.border; }
    if (props.hspace != null) { this._domNode.hspace = props.hspace; }
    if (props.longDesc != null) { this._domNode.longDesc = props.longDesc; }    
    if (props.vspace != null) { this._domNode.vspace = props.vspace; }

    // Set more properties.
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * Apply the src property on the dom element. If the image is an png image and 
 * the browser is IE 6 an IE6 specific fix needs to be applied and the style 
 * property has to be modified. If the png and IE6 combination is not true, then
 * no modification is done for the src property of the image and the style 
 * property.
 * 
 * @param {Object} props Key-Value pairs of properties.
 * @return (String} styleProp Modified value of the style property if the image
 * is a png image rendered on IE6.
 * @private
 */
@JS_NS@.widget.image.prototype._setSrcProperty = function(props) {    
    var styleProp = null;

    //IE6 PNG fix to handle transparent PNGs.
    if (props.src.indexOf(".png") > 0 && @JS_NS@._base.browser._isIe6()) {
        var width = (props.width > 0)
            ? props.width : this._theme.getMessage("Image.defaultWidth");
        var height =(props.height > 0)
            ? props.height : this._theme.getMessage("Image.defaultHeight"); 
        var styleMsg = this._theme.getMessage("Image.IEPngCSSStyleQuirk", 
          [width, height, props.src]);

        this._domNode.src = this._theme.getImage("DOT").src;

        if (props.style != null) {
            styleProp = props.style +";"+ styleMsg;
        } else {
            styleProp = (this.style != null) 
                ? this.style + ";" + styleMsg : styleMsg;                                          
        }
    } else {
        // While toggling between png and non-png type images need to adjust the 
        // style set on the dom element, since that is the property that 
        // contains the source for png images.
        if (@JS_NS@._base.browser._isIe6() 
                && this._domNode.style.cssText.indexOf(".png")>0) {
            styleProp = this.style;
        }
        this._domNode.src = props.src;  
    }
    return styleProp;
};
