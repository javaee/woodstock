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

@JS_NS@._dojo.provide("@JS_NS@.widget.alert");

@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct an alert widget.
 *
 * @constructor
 * @name @JS_NS@.widget.alert
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the alert widget.
 * <p>
 * The alert widget is comprised of an image, summary message, and an optional 
 * detail message. The image shown is determined by the type property, which 
 * must be set to "information", "success", "warning", "error" or a custom type.
 * The alert widget also supports a set of indicators which allows custom types
 * and associated images. The summary message is specified with the summary 
 * property and is displayed prominently next to the image. The optional detail
 * message is specified with the detail property, and is displayed in less 
 * prominent text following the summary message. The detail messge provides more
 * information about the alert. You can also include a link to more information
 * or another window using the moreInfo property. The link is displayed below 
 * the detail message.
 * </p><p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "alert1",
 *       detail: "Detailed message",
 *       summary: "Summary message",
 *       type: "error",
 *       widgetType: "alert"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * alert is either hidden or shown.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "alert1",
 *       detail: "Detailed message",
 *       summary: "Summary message",
 *       type: "error",
 *       widgetType: "alert"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Alert State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("alert1"); // Get alert
 *       return widget.setProps({visible: !domNode.getProps().visible}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the alert is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "alert1",
 *       detail: "Detailed message",
 *       summary: "Summary message",
 *       type: "error",
 *       widgetType: "alert"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Alert" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("alert1"); // Get alert
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can take an optional list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,..."). When no parameter is given, 
 * the refresh function acts as a reset. That is, the widget will be redrawn 
 * using values set server-side, but not updated.
 * </p><p>
 * <h3>Example 3b: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update an alert using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the alert text
 * is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "alert1",
 *       detail: "Detailed message",
 *       summary: "Summary message",
 *       type: "error",
 *       widgetType: "alert"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Alert Text" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("alert1"); // Get alert
 *       return widget.refresh("field1"); // Asynchronously refresh while submitting field value
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can optionally take a list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,...").
 * </p><p>
 * <h3>Example 4: Subscribing to event topics</h3>
 * </p><p>
 * When a widget is manipulated, some features may publish event topics for
 * custom AJAX implementations to listen for. For example, you may listen for
 * the refresh event topic using:
 * </p><pre><code>
 * &lt;script type="text/javascript">
 *    var foo = {
 *        // Process refresh event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        processRefreshEvent: function(props) {
 *            // Get the widget id.
 *            if (props.id == "btn1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.alert.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} detail The alert detail message.
 * @config {String} dir Specifies the directionality of text.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {Array} indicators Array of Key-Value pairs, containing type and 
 * image properties.
 * @config {Object} moreInfo Key-Value pairs of properties for more info link.
 * @config {String} spacerImage 
 * @config {String} summary The alert summary message.
 * @config {String} type The type of alert to display.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.alert", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    _widgetType: "alert" // Required for theme properties.
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
@JS_NS@.widget.alert.event =
        @JS_NS@.widget.alert.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} execute Comma separated list of IDs to be processed server
         * side along with this widget.
         * </li><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.alert.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_alert_event_refresh_begin",

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         *
         * @id @JS_NS@.widget.alert.event.refresh.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_alert_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * {Object} props Key-Value pairs of widget properties being updated.
         * </li></ul>
         *
         * @id @JS_NS@.widget.alert.event.submit.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_alert_event_state_begin",

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.alert.event.submit.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_alert_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.alert.prototype.getProps = function() {
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
 * @private
 */
@JS_NS@.widget.alert.prototype._notify = function(props) {
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
@JS_NS@.widget.alert.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._bottomLeftContainer.id = this.id + "_bottomLeftContainer";
        this._bottomMiddleContainer.id = this.id + "_bottomMiddleContainer";
        this._bottomRightContainer.id = this.id + "_bottomRightContainer";
        this._detailContainer.id = this.id + "_detailContainer";
        this._imageContainer.id = this.id + "_imageContainer";
        this._leftMiddleContainer.id = this.id + "_leftMiddleContainer";
        this._rightMiddleContainer.id = this.id + "_rightMiddleContainer";
        this._summaryContainer.id = this.id + "_summaryContainer";
        this._topLeftContainer.id = this.id + "_topLeftContainer";
        this._topMiddleContainer.id = this.id + "_topMiddleContainer";
        this._topRightContainer.id = this.id + "_topRightContainer";
        this._detailContainerLink.id = this.id + "_detailContainerLink";
    }

    // Create default indicators.   
    var  defaultIndicators = [{
        "type": "error",
        "image": {
            id: this.id + "_error",
            icon: "ERROR_ALERT_INDICATOR",
            widgetType: "image"
        }
    }, {
        "type": "warning",
        "image": {
            id: this.id + "_warning",
            icon: "WARNING_ALERT_INDICATOR",
            widgetType: "image"
        }
    }, {
        "type": "success",
        "image": {
            id: this.id + "_success",
            icon: "SUCCESS_ALERT_INDICATOR",
            widgetType: "image"
        }
    }, {
        "type": "information",
        "image": {
            id: this.id + "_info",
            icon: "INFORMATION_ALERT_INDICATOR",
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
    
    // spacer image
    if (this.spacerImage == null) {
        this.spacerImage = {
             icon: "DOT",
             id: this.id + "_dot",
             widgetType: "image"
        };
    }
    // moreInfo link
    // To do: Test isFragment instead.
    if (this.moreInfo != null && this.moreInfo.id == null 
            && this.moreInfo.widgetType == null) {
        this.moreInfo = {
            id: this.id + "_" + "alertLink",
            enabledImage:  {
                id: this.id + "_moreInfoLinkImg",
                icon: "HREF_LINK",
                widgetType: "image"
            },     
            target: this.moreInfo.target,
            href: this.moreInfo.url,
            contents: [this.moreInfo.value],
            imagePosition: "right",                   
            title: this.moreInfo.tooltip,
            widgetType: "imageHyperlink"
        };
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
@JS_NS@.widget.alert.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.dir != null) { this._domNode.dir = props.dir; }
    if (props.lang != null) { this._domNode.lang = props.lang; }    
    
    // Set summary.
    if (props.summary != null) {
        this._widget._addFragment(this._summaryContainer, props.summary);
    }

    // Set detail.
    if (props.detail != null) {
        this._widget._addFragment(this._detailContainer, props.detail);
    }

    // Set moreInfo.
    if (props.moreInfo) {
        this._widget._addFragment(this._detailContainerLink, props.moreInfo);
    }

    // Set spacer image.
    if (props.spacerImage) {
        var containers = [
            this._bottomLeftContainer,
            this._bottomMiddleContainer,
            this._bottomRightContainer,
            this._leftMiddleContainer,
            this._rightMiddleContainer,
            this._topLeftContainer,
            this._topMiddleContainer,
            this._topRightContainer];

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
            this._widget._updateFragment(this._imageContainer, indicator.image.id, 
                indicator.image, "last");
        }
    }

    // Do not call _setCommonProps() here. 

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
