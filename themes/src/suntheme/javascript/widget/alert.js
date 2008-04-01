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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget.alert");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget._base.widgetBase");

/**
 * @name webui.@THEME_JS@.widget.alert
 * @extends webui.@THEME_JS@.widget._base.widgetBase
 * @class This class contains functions for the alert widget.
 * @constructor This function is used to construct an alert widget.
 */
webui.@THEME_JS@._base.dojo.declare("webui.@THEME_JS@.widget.alert",
        webui.@THEME_JS@.widget._base.widgetBase, {
    // Set defaults.
    _widgetName: "alert" // Required for theme properties.
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
webui.@THEME_JS@.widget.alert.event =
        webui.@THEME_JS@.widget.alert.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_alert_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_alert_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_alert_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_alert_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.alert.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.detail != null) { props.detail = this.detail; }
    if (this.indicators != null) { props.indicators = this.indicators; }
    if (this.summary != null) { props.summary = this.summary; }
    if (this.type != null) { props.type = this.type; }
    if (this.moreInfo != null) { props.moreInfo = this.moreInfo; }
    if (this.spacerImage != null) { props.spacerImage = this.spacerImage; }    
    return props;
};

/**
 * This function is used to process notification events with Object
 * literals.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} detail Message detail text.
 * @config {String} summary Message summary text.
 * @config {boolean} valid Flag indicating validation state.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.alert.prototype.notify = function(props) {
    if (props == null) {
        return false;
    }
    return this.setProps({
        summary: props.summary,
        detail: props.detail,
        type: "error",
        visible: !props.valid
    });
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
webui.@THEME_JS@.widget.alert.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this.bottomLeftContainer.id = this.id + "_bottomLeftContainer";
        this.bottomMiddleContainer.id = this.id + "_bottomMiddleContainer";
        this.bottomRightContainer.id = this.id + "_bottomRightContainer";
        this.detailContainer.id = this.id + "_detailContainer";
        this.imageContainer.id = this.id + "_imageContainer";
        this.leftMiddleContainer.id = this.id + "_leftMiddleContainer";
        this.rightMiddleContainer.id = this.id + "_rightMiddleContainer";
        this.summaryContainer.id = this.id + "_summaryContainer";
        this.topLeftContainer.id = this.id + "_topLeftContainer";
        this.topMiddleContainer.id = this.id + "_topMiddleContainer";
        this.topRightContainer.id = this.id + "_topRightContainer";
        this.detailContainerLink.id = this.id + "_detailContainerLink";
    }
        //create default indicators.   
        var  defaultIndicators =[{
                    "type": "error",
                    "image": this._widget._getWidgetProps("image", {
                    id: this.id + "_error",
                    icon: "ERROR_ALERT_INDICATOR"
                    })
                },
            {
                    "type": "warning",
                    "image": this._widget._getWidgetProps("image", {
                    id: this.id + "_warning",
                    icon: "WARNING_ALERT_INDICATOR"
                })
                },
            {
                    "type": "success",
                    "image": this._widget._getWidgetProps("image", {
                    id: this.id + "_success",
                    icon: "SUCCESS_ALERT_INDICATOR"
                })
                },
            {
                    "type": "information",
                    "image": this._widget._getWidgetProps("image", {
                    id: this.id + "_info",
                    icon: "INFORMATION_ALERT_INDICATOR"
                })
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
    
    //spacer image
    if (this.spacerImage == null) {
        this.spacerImage = this._widget._getWidgetProps("image", {
             icon: "DOT",
             id: this.id + "_dot"
        }); 
    }
    // moreInfo link
    if (this.moreInfo != null && this.moreInfo.id == null && this.moreInfo.widgetType == null) {
        this.moreInfo = this._widget._getWidgetProps("imageHyperlink",{
                    id: this.id + "_" + "alertLink",
                    enabledImage: this._widget._getWidgetProps("image", {
                            id: this.id + "_moreInfoLinkImg",
                            icon: "HREF_LINK"
                    }),     
                    target: this.moreInfo.target,
                    href: this.moreInfo.url,
                    contents: [this.moreInfo.value],
                    imagePosition: "right",                   
                    title: this.moreInfo.tooltip    
                }                            
            );
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
 * @config {String} className CSS selector.
 * @config {String} detail
 * @config {String} dir Specifies the directionality of text.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {Array} indicators 
 * @config {String} moreInfo 
 * @config {String} spacerImage 
 * @config {String} summary 
 * @config {String} type 
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.alert.prototype.setProps = function(props, notify) {
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
webui.@THEME_JS@.widget.alert.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.dir) { this.domNode.dir = props.dir; }
    if (props.lang) { this.domNode.lang = props.lang; }    
    
    // Set summary.
    if (props.summary) {
        this._widget._addFragment(this.summaryContainer, props.summary);
    }

    // Set detail.
    if (props.detail) {
        this._widget._addFragment(this.detailContainer, props.detail);
    }

    // Set moreInfo.
    if (props.moreInfo) {
        this._widget._addFragment(this.detailContainerLink, props.moreInfo);
    }

    // Set spacer image.
    if (props.spacerImage) {
        var containers = [
            this.bottomLeftContainer,
            this.bottomMiddleContainer,
            this.bottomRightContainer,
            this.leftMiddleContainer,
            this.rightMiddleContainer,
            this.topLeftContainer,
            this.topMiddleContainer,
            this.topRightContainer];

        // Avoid widget ID collisions.
        for (var i = 0; i < containers.length; i++) {
            if (typeof props != 'string') {
                props.spacerImage.id = this.id + "_spacerImage" + i;
            }
            // Replace container with image.
            if (!this._widget.getWidget(props.spacerImage.id)) {
                this._widget._addFragment(containers[i], props.spacerImage);
            }
        }
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
            indicator.image.tabIndex = this.tabIndex;

            // Update/add fragment.
            this._widget._updateFragment(this.imageContainer, indicator.image.id, 
                indicator.image, "last");
        }
    }

    // Do not call _setCommonProps() here. 

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
