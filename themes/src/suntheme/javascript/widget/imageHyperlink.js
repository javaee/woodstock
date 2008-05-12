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

@JS_NS@._dojo.provide("@JS_NS@.widget.imageHyperlink");

@JS_NS@._dojo.require("@JS_NS@.widget.hyperlink");

/**
 * This function is used to construct a imageHyperlink widget.
 *
 * @constructor
 * @name @JS_NS@.widget.imageHyperlink
 * @extends @JS_NS@.widget.hyperlink
 * @class This class contains functions for the imageHyperlink widget.
 * <p>
 * The imageHyperlink widget creates an image surrounded by an HTML anchor that
 * submits form data. If the disabled attribute of the hyperlink is set to true, 
 * clicking the on the image will not generate a request and hence the form will
 * not be submitted or the page will not navigate to the specified url.
 * </p><p>
 * <h3>Example 1a: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       disabledImage: {
 *         id: "img1",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/disabled.gif"
 *         widgetType: "image"
 *       },
 *       enabledImage: {
 *         id: "img2",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/enabled.gif"
 *         widgetType: "image"
 *       },
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "imageHyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * image hyperlink is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       disabledImage: {
 *         id: "img1",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/disabled.gif"
 *         widgetType: "image"
 *       },
 *       enabledImage: {
 *         id: "img2",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/enabled.gif"
 *         widgetType: "image"
 *       },
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "imageHyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Image Hyperlink State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("hyp1"); // Get image hyperlink
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the image hyperlink is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       disabledImage: {
 *         id: "img1",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/disabled.gif"
 *         widgetType: "image"
 *       },
 *       enabledImage: {
 *         id: "img2",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/enabled.gif"
 *         widgetType: "image"
 *       },
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "imageHyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Image Hyperlink" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("hyp1"); // Get image hyperlink
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
 * This example shows how to asynchronously update an image hyperlink using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the image hyperlink text
 * is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "hyp1",
 *       contents: ["Click me"],
 *       disabledImage: {
 *         id: "img1",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/disabled.gif"
 *         widgetType: "image"
 *       },
 *       enabledImage: {
 *         id: "img2",
 *         height: 13,
 *         width: 13,
 *         src: "/myApp/images/enabled.gif"
 *         widgetType: "image"
 *       },
 *       href: "http://sun.com",
 *       target: "_blank",
 *       widgetType: "imageHyperlink"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Image Hyperlink Text" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("hyp1"); // Get image hyperlink
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
 *            if (props.id == "hyp1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.imageHyperlink.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
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
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.imageHyperlink",
        @JS_NS@.widget.hyperlink, {
    // Set defaults.
    _widgetType: "imageHyperlink" // Required for theme properties.
});

/**
 * Helper function to add children.
 *
 * @param props Key-Value pairs of properties.
 * @config {Array} contents The contents of the anchor body.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.imageHyperlink.prototype._addContents = function(props) {
    if (props.contents == null) {
        return false;
    }

    // Remove child nodes.
    this._widget._removeChildNodes(this._leftContentsContainer);
    this._widget._removeChildNodes(this._rightContentsContainer);

    // Add contents.
    for (i = 0; i <props.contents.length; i++) {
        this._widget._addFragment(this._leftContentsContainer, props.contents[i], "last");
        this._widget._addFragment(this._rightContentsContainer, props.contents[i], "last");
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
@JS_NS@.widget.imageHyperlink.event =
        @JS_NS@.widget.imageHyperlink.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_imageHyperlink_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_imageHyperlink_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_imageHyperlink_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_imageHyperlink_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.imageHyperlink.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.enabledImage != null) { props.enabledImage = this.enabledImage; }
    if (this.disabledImage != null) { props.disabledImage = this.disabledImage; }
    if (this.imagePosition != null) { props.imagePosition = this.imagePosition; }

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
@JS_NS@.widget.imageHyperlink.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._enabledImageContainer.id = this.id + "_enabled";
        this._disabledImageContainer.id = this.id + "_disabled";
        this._leftContentsContainer.id = this.id + "_leftContents";
        this._rightContentsContainer.id = this.id + "_rightContents";
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
@JS_NS@.widget.imageHyperlink.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Show en/disabled images.
    if (props.disabled != null) {
        var disabled = new Boolean(props.disabled).valueOf();

        // We need to hide/show images only when the disabed image is specified.
        if (this.disabledImage) { 
            this._common._setVisibleElement(this._enabledImageContainer, !disabled);
            this._common._setVisibleElement(this._disabledImageContainer, disabled);
        }
    }

    // Add enabled image.
    if (props.enabledImage) {
        // Update/add fragment.
        this._widget._updateFragment(this._enabledImageContainer, this.enabledImage.id, 
            props.enabledImage);
    }

    // Add disabled image.
    if (props.disabledImage) {
        // Update/add fragment.
        this._widget._updateFragment(this._disabledImageContainer, this.disabledImage.id, 
            props.disabledImage);
    }

    // Set image position.
    if (props.imagePosition) {
        var left = (props.imagePosition == "left");
        this._common._setVisibleElement(this._leftContentsContainer, !left);
        this._common._setVisibleElement(this._rightContentsContainer, left);    
    }

    // Add contents.
    this._addContents(props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
