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

@JS_NS@._dojo.provide("@JS_NS@.widget.alarm");

@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct an alarm widget.
 *
 * @name @JS_NS@.widget.alarm
 * @extends @JS_NS@.widget._base.widgetBase
 * @class This class contains functions for the alarm widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {Array} indicators 
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
 * @config {String} text 
 * @config {String} textPosition
 * @config {String} title Provides a title for element.
 * @config {String} type Provides a title for element.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.alarm",
        @JS_NS@.widget._base.widgetBase, {
    _widgetType: "alarm" // Required for theme properties.
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
@JS_NS@.widget.alarm.event =
        @JS_NS@.widget.alarm.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_alarm_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_alarm_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_alarm_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_alarm_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.alarm.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.text != null) { props.text = this.text; }
    if (this.indicators != null) { props.indicators = this.indicators; }
    if (this.textPosition != null) { props.textPosition = this.textPosition; }
    if (this.type != null) { props.type = this.type; }
    
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
@JS_NS@.widget.alarm.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._rightText.id = this.id + "_rightText";
        this._leftText.id = this.id + "_leftText";
        this._imageContainer.id = this.id + "_imageContainer";        
    }
    // default set of indicators
    var  defaultIndicators = [{
        "type": "down",
        "image": {
            id: this.id + "_down",
            icon: "DOWN_ALARM_INDICATOR",
            widgetType: "image"
        }
    }, {
        "type": "critical",
        "image": {
            id: this.id + "_critical",
            icon: "CRITICAL_ALARM_INDICATOR",
            widgetType: "image"
        }
    }, {
        "type": "major",
        "image": {
            id: this.id + "_major",
            icon: "MAJOR_ALARM_INDICATOR",
            widgetType: "image"
        }
    }, {
        "type": "minor",
        "image": {
            id: this.id + "_minor",
            icon: "MINOR_ALARM_INDICATOR",
            widgetType: "image"
        }
    }, {
        "type": "ok",
        "image": {
            id: this.id + "_ok",
            icon: "OK_ALARM_INDICATOR",
            widgetType: "image"
        }
    }];

    if (this.indicators == null) {
        this.indicators = defaultIndicators;    
    } else {
        for (var i = 0; i < this.indicators.length; i++) {          
            for (var j = 0; j < defaultIndicators.length; j++) {
                if (this.indicators[i].type == defaultIndicators[j].type) {
                    defaultIndicators[j].image = this.indicators[i].image;
                    this.indicators[i] = null;                  
                    break;
                }
            }
        }
      
        // merge the indicators (defaultset + custom set)
        for (var i = 0; i < this.indicators.length; i++) {   
            if (this.indicators[i] != null) {                         
                defaultIndicators = defaultIndicators.concat(this.indicators[i]);
            }
        }
        this.indicators = defaultIndicators;     
    }
    return this._inherited("_postCreate", arguments);
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
@JS_NS@.widget.alarm.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.dir) { this._domNode.dir = props.dir; }
    if (props.lang) { this._domNode.lang = props.lang; }    
    
    // Set right text.
    if (props.textPosition == "right" || props.textPosition == null && props.text != null) {
        this._common._setVisibleElement(this._leftText, false);
        this._widget._addFragment(this._rightText, props.text);
    }

    // Set left text.
    if (props.textPosition == "left" && props.text != null) {
        this._common._setVisibleElement(this._rightText, false);
        this._widget._addFragment(this._leftText, props.text);
    }    
    
    // Set indicator properties.
    if (props.indicators || props.type != null && this.indicators) {
        // Iterate over each indicator.
        for (var i = 0; i < this.indicators.length; i++) {
            // Ensure property exists so we can call setProps just once.
            var indicator = this.indicators[i]; // get current indicator.
            if (indicator == null) {
                indicator = {}; // Avoid updating all props using "this" keyword.
            }

            // Set properties.
            indicator.image.visible = (indicator.type == this.type) ? true: false;

            // Update/add fragment.
            this._widget._updateFragment(this._imageContainer, indicator.image.id, 
                indicator.image, "last");
        }
    }

    // Do not call _setCommonProps() here. 

    // Set more properties.
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
