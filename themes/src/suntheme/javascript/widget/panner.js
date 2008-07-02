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

@JS_NS@._dojo.provide("@JS_NS@.widget.panner");

@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct an panner widget.
 *
 * @constructor
 * @name @JS_NS@.widget.panner
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @class This class contains functions for the panner widget.
 * <p>
 * The <code>panner</code> widget is a control that is used to reposition the  
 * viewport on an image or other media that is too large to view in its
 * entirety. In the following discusssion this media will be referred to as
 * the "canvas". A "canvas" and "viewport" can be represented by the
 * following HTML.
 * </p><p><pre><code>
 * &lt;div id="viewport0" class="mediaviewer" &gt;
 *  &lt;img id="canvas0" class="content" src="img.jpg"/&gt;
 * &lt;/div&gt;
 * </code></pre></p>
 * <p>
 * The dimensions and borders of the <code>div</code> and <code>img</code>
 * elements are assumed to be defined in the <code>mediaviewer</code>
 * and <code>content</code> selectors.
 * <p>
 * The <code>panner</code> renders a rectangular area that is
 * a scaled down and proportional representation of the actual canvas and
 * viewport. The outer rectangular area represents the actual size of the
 * canvas and the inner rectangular, called the "puck", represents the viewport
 * on the canvas.
 * </p>
 * <p>
 * As the user drags the "puck" the viewport on the canvas is repositioned,
 * revealing other areas of the canvas. For example if the canvas is 
 * 5000x5000 pixels and the viewport is only 750x750 px, and the panner is
 * created 1% of that size, then it would render as
 * <p>
 * <div style="padding-top:20px;padding-left:20px">
 * <div style="position:relative;border:3px double black;width:50px;height:50px">
 * <div style="position:absolute;border:2px dashed black;width:8px;height:8px;top:9px;left:9px">
 * </div>
 * </div>
 * </div>
 * </p><p>
 * This example shows that the user has dragged the puck and moved the
 * viewport. The area of the canvas defined by the rectangle
 * "[x,y,width,height]", [900,900,1700,1700] will be visible in the 
 * actual viewport.
 * </p><p>
 * The actual canvas and viewport are defined by the application. 
 * The <code>id</code>'s of the HTML elements that represent the canvas
 * and viewport are passed to the <code>panner</code> in order for it
 * determine the dimensions and to change the canvas's <cocde>top</code> and
 * <code>left</code> coordinates. Changing these style properties effectively
 * repositions the viewport. The HTML element representing the canvas can
 * must support the <code>top</code> and <code>left</code> style
 * properties. The canvas and viewport dimensions are obtained
 * via the <code>clientWidth</code> and <code>clientHeight</code> javascript
 * properties. If the application chooses elements that do not support 
 * these properties, the <code>canvasRect</code> and <code>viewportRect</code>
 * properties can be used to specify the dimensions to the panner in pixels.
 * If the canvas HTML element does not support the <code>top</code> and
 * <code>left</code> CSS properties, then the application must reposition
 * the viewport by subscribing to the panner's
 * <code>event.pan.moveBegin</code> and <code>event.pan.moveEnd</code> 
 * event topics. The application can obtain the current and new position,
 * respectively, by referencing the widget's <code>canvasX</code> and
 * <code>canvasY</code> properties.
 * </p><p>
 * The default behavior of the <code>panner</code> is to size the panner
 * outer rectangle and puck based on the <code>scale</code> property; the
 * default is <code>10%</code>.
 * If the application wants to explicitly control the size of these
 * rectangles then it can specify the <code>pannerRect</code> and 
 * <code>puckRect</code> properties.
 * </p><p>
 * The appearance of the panner outer rectangle and the puck rectangle
 * can be controlled by defining the CSS class selectors <code>panner</code>
 * and <code>pannerpuck</code>. The panner outer rectangle HTML element's
 * id is the same as the widget's <code>id</code> property and the
 * puck HTML element's id is "_puck".
 * </p><p>
 * The <code>panner</code> can publish events on pan start,
 * pan move (begin and end), and pan stop. For performance reasons
 * events are not published by default. Set the <code>publishEvents</code>
 * property to <code>true</code> when subscribing to events.
 * </p>
 * <h3>Example 1: Create a panner widget</h3>
 * <p>
 * This example shows how to create a panner widget using a span tag
 * as a place holder in the document. Minimally, the createWidget()
 * function needs an id and widgetType properties. If these are the 
 * only properties, then the panner outer rectangle and puck will be 
 * rendered, but they will not control anything.
 * </p><p>
 * However, the panner internally calculates a canvas and viewport
 * that are 10 times the size of the panner and puck.
 * The <code>canvasX</code> and <code>canvasY</code> properties are set
 * assuming this scale. That means if <code>publishEvents</code> property
 * was set to <code>true</code> and the application subscribed to the
 * <code>@JS_NS@.widget.panner.event.pan.moveEnd</code> event, the 
 * application could use the control as a "2 dimensional" controller.
 * </p><p><pre><code>
 * &lt;span id="sp1"&gt;
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "panner1",
 *       widgetType: "panner",
 *     });
 *   &lt;/script&gt;
 * &lt;/span&gt;
 * </code></pre></p>
 * <h3>Example 2: Define a canvas and viewport and panner widget</h3>
 * <p>
 * This example defines a canvas and viewport using an <code>img</code>
 * and <code>div</code> element. The element id's are passed to the
 * panner so that it can size the panner and puck and reposition the
 * viewport.
 * </p><p><pre><code>
 * &lt;div id="viewport0" class="mediaviewer" &gt;
 *  &lt;img id="canvas0" class="content" src="img.jpg"/&gt;
 * &lt;/div&gt;
 * &lt;span id="sp1"&gt;
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "panner1",
 *       widgetType: "panner",
 *       canvasId: "canvas0",
 *       viewportId: "viewport0",
 *       scale: 7
 *     });
 *   &lt;/script&gt;
 * &lt;/span&gt;
 * </code></pre></p>
 * <h3>Example 3: Define a panner with explicit panner and puck dimensions</h3>
 * <p>
 * This example is the same as Example 2 but this example defines
 * the panner and puck rectangles explicitly.
 * </p><pre><code>
 * &lt;span id="sp1"&gt;
 *   &lt;script type="text/javascript"&gt;
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "panner1",
 *       widgetType: "panner",
 *       canvasId: "img1",
 *       viewportId: "vp1",
 *       pannerRect: { width: 75, height: 15 };
 *       puckRect: { width: 3, height 3 };
 *     });
 *   &lt;/script&gt;
 * &lt;/span&gt;
 * </code></pre>
 * <p>
 * <h3>Example 4: Define a panner and control it with javascript</h3>
 * <p>
 * This example demonstrates using <code>getProps</code> and
 * <code>setProps</code> to toggle the visibility of the panner.
 * </p><pre><code>
 * &lt;span id="sp1"&gt;
 *   &lt;script type="text/javascript"&gt;
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "panner1",
 *       widgetType: "panner",
 *       canvasId: "img1",
 *       viewportId: "vp1",
 *     });
 *   &lt;/script&gt;
 * &lt;/span&gt;
 * &lt;span id="sp2"&gt;
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "b1",
 *       value: "Hide",
 *       onClick: "hideWidget(); return false;",
 *       widgetType: "button"
 *     });
 *     function hideWidget() {
 *       // Get the widget instance
 *       var widget = @JS_NS@.widget.common.getWidget("panner1");
 *       var visible = widget.getProps().visible;
 *       // visible will be "undefined" the first time
 *       if (visible == undefined) {
 *           visible = true;
 *       }
 *       // toggle button text
 *       @JS_NS@.widget.common.getWidget("b1").
 *           setProps(value: visible ? "Show" : "Hide");
 *       // toggle the visibility
 *       return widget.setProps({visible: !visible});
 *     }
 *   &lt;/script&gt;
 * &lt;/span&gt;
 * </code></pre>
 * <p>
 * <h3>Example 5: Subscribe to the move event topics</h3>
 * </p><p>
 * This example shows how to subscribe to the move event topics.
 * </p><p><pre><code>
 * &lt;div id="viewport0" class="mediaviewer" &gt;
 *  &lt;img id="canvas0" class="content" src="img.jpg"/&gt;
 * &lt;/div&gt;
 * &lt;span id="sp1"&gt;
 *   &lt;script type="text/javascript"&gt;
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "panner1",
 *       widgetType: "panner",
 *       canvasId: "canvas0",
 *       viewportId: "viewport0",
 *       scale: 7
 *     });
 *   &lt;/script&gt;
 * &lt;/span&gt;
 * &lt;script type="text/javascript"&gt;
 *    var moveSubscriber = {
 *        // Process move event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        begin: function(props) {
 *            // Get the widget id.
 *            if (props.id == "panner1") { // Do something... }
 *        },
 *        end: function(props) {
 *            if (props.id == "panner1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.panner.event.pan.movebegin, 
 *      moveSubscriber, "begin");
 *    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.panner.event.pan.moveEnd, 
 *      moveSubscriber, "end");
 * &lt;/script&gt;
 * </code></pre></p>
 * <p>
 * Note that the event properties will be applied to the top level
 * HTML element of the panner. Events generated by the puck can be
 * handled as the event "bubbles up" to the top level element.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} canvasId The HTML id of the canvas.
 * @config {String} viewportId The HTML id of the viewport.
 * @config {int} scale The size of the panner in percent relative to the
 * size of the canvas and viewport. An integer value greater than 0. The
 * default value is 10.
 * @config {int} canvasX The current "left" postion of the canvas relative to 
 * the viewport origin.
 * @config {int} canvasY The current "top" postion of the canvas relative to 
 * the viewport origin.
 * @config {Object} canvasRect An Object literal defining <code>width</code>
 * and <code>height</code> properties, of the canvas HTML element.
 * @config {Object} viewportRect An Object literal defining <code>width</code>
 * and <code>height</code> properties, of the viewport HTML element.
 * @config {Object} pannerRect An Object literal defining <code>width</code>
 * and <code>height</code> properties, for the panner outer rectangle.
 * @config {Object} puckRect An Object literal defining <code>width</code>
 * and <code>height</code> properties, for the puck of the panner.
 * @config {boolean} publishEvents Set value to true to publish start,
 * stop, and move events. The default value is false.
 * @config {String} className CSS selector.
 * @config {String} id Uniquely identifies an element within a document.
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
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.panner", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    _widgetType: "panner", // Required for theme properties.
    constructor : function() {
	this.canvasX = 0;
	this.canvasY = 0;
	this.scale = 10;
	this.publishEvents = false;
	this._ispanning = false;
	this._units = "px"; // May become public but need to generalize
			    // to no rely on "px".
    }
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
@JS_NS@.widget.panner.event =
        @JS_NS@.widget.panner.prototype.event = {
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
         * @id @JS_NS@.widget.panner.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_panner_event_refresh_begin",

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         *
         * @id @JS_NS@.widget.panner.event.refresh.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_panner_event_refresh_end"
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
         * @id @JS_NS@.widget.panner.event.submit.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_panner_event_state_begin",

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.panner.event.submit.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_panner_event_state_end"
    },

    /**
     * This object contains puck move event topics.
     * @ignore
     */
    pan: {
        /**
         * Panner move begin event topic.
	 * Inspect the <code>canvasX</code> and <code>canvasY</code>
	 * properties for the current canvas position.<br/>
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.panner.event.pan.moveBegin
         * @property {String} beginTopic
         */
        moveBegin: "@JS_NS@_widget_panner_event_pan_moveBegin",

        /**
         * Panner move end event topic.
	 * Inspect the <code>canvasX</code> and <code>canvasY</code>
	 * properties for the new canvas position.<br/>
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.panner.event.pan.moveEnd
         * @property {String} endTopic
         */
        moveEnd: "@JS_NS@_widget_panner_event_pan_moveEnd",

        /**
         * Panner event signifying that the user has clicked within the puck.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.panner.event.pan.start
         * @property {String} beginTopic
         */
        start: "@JS_NS@_widget_panner_event_pan_start",

        /**
         * State event topic for custom AJAX implementations to listen for.
	 * Inspect the <code>canvasX</code> and <code>canvasY</code>
	 * properties for the new canvas position.<br/>
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.panner.event.submit.stop
         * @property {String} endTopic
         */
        stop: "@JS_NS@_widget_panner_event_pan_stop"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.panner.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    
    if (this.canvasId != null) props.canvasId = this.canvasId;
    if (this.viewportId != null) props.viewportId = this.viewportId;

    // Note that if no width or height or scale properties have been
    // defined the CSS selector definition defines the size and 
    // there will be no property values returned.
    //
    if (this.canvasRect != null) props.canvasRect = this.canvasRect;
    if (this.viewportRect != null) props.viewportRect = this.viewportRect;
    if (this.pannerRect != null) props.pannerRect = this.pannerRect;
    if (this.puckRect != null) props.puckRect = this.puckRect;
    if (this.scale != null) props.scale = this.scale;

    // Always return these. They always exist.
    //
    props.canvasX = this.canvasX;
    props.canvasY = this.canvasY;
    props.publishEvents = this.publishEvents == true ? true : false;

    return props;
};

/**
 * Initial the dimensions and runtime constants used to reposition
 * the canvas viewport and control the panner puck. This method
 * must be called whenever a property that influences the canvas,
 * viewport, panner or puck changes.
 *
 * @return {boolean} true if successful; otherwise false.
 * @private
 */
@JS_NS@.widget.panner.prototype._initCanvasViewport = function() {

    var cvRect = null;
    try {
	// We need this reference to set the top and left properties
	//
	this._canvas = document.getElementById(this.canvasId);
    } catch(err) {
	// Just don't want to fail if application does not set canvasId
    }
    if (this.canvasRect == null && this._canvas != null) {
	cvRect = {
	    width: this._canvas.clientWidth,
	    height: this._canvas.clientHeight
	};
    } else {
	cvRect = this.canvasRect;
    }

    // Get the node for the viewport to get its "padding box"
    // We don't need to persist a reference to the viewport
    //
    var vp = null;
    var vpRect = null;
    try {
	vp = document.getElementById(this.viewportId);
    } catch (err) {
	// Just don't want to fail if application does not set viewportId
    }
    if (this.viewportRect == null && vp != null) {
	vpRect = {
	    width: vp.clientWidth,
	    height: vp.clientHeight
	};
    } else {
	vpRect = this.viewportRect;
    }

    var scalepercent = 0.1;
    try {
	scalepercent = this.scale/100;
	if (scalepercent <= 0) {
	    scalepercent = 0.1;
	    this.scale = 10;
	}
    } catch (err) {
	// in case "this.scale" is not a number
    }

    // If the pannerRect and/or puckRect are specified the scale
    // may not be the same for both dimensions. They are calculated later
    //
    var scaleX = scalepercent;
    var scaleY = scalepercent;

    var pnRect = null;
    if (this.pannerRect == null) {
	if (cvRect != null) {
	    pnRect = {
		width: Math.round(cvRect.width * scalepercent),
		height: Math.round(cvRect.height * scalepercent)
	    };
	} else {
	    // degenerative case only panner is rendered but we want
	    // "real" dimensions for the canvas so we don't have to 
	    // special case the "no canvas" scenario.
	    //
	    pnRect = {
		width: this._domNode.clientWidth,
		height: this._domNode.clientHeight
	    };
	    cvRect = {
		width: pnRect.width * this.scale,
		height: pnRect.height * this.scale
	    };
	}
    } else {
	// Need to calculate scaleX and scaleY since the pannerRect
	// was specified by the application. We probably should protect
	// agains NaN
	//
	pnRect = this.pannerRect;
	var pW = pnRect.width > 0 ? pnRect.width : this._domNode.clientWidth;
	var pH = pnRect.height > 0 ? pnRect.height : this._domNode.clientHeight;

	if (cvRect != null) {
	    // This could result in undefined behavior with respect to 
	    // how much of the image is going to be visible or
	    // if anything is visible, if one or both of the
	    // canvas's dimensions is really 0. One might consider
	    // the degenerative case to result in a "slider" ;)
	    //
	    var cW = cvRect.width > 0 ? cvRect.width : pW * scale;
	    var cH = cvRect.height > 0 ? cvRect.height : pH * scale;

	    scaleX = pW/cW;
	    scaleY = pH/cH;
	} else {
	    // Degenerative case, no canvas
	    //
	    cvRect = {
		width: pW * this.scale,
		height: pH * this.scale
	    };
	}
    }
    var pkRect = null;
    if (this.puckRect == null) {
	if (vpRect != null) {
	    pkRect = {
		width: Math.round(vpRect.width * scalepercent),
		height: Math.round(vpRect.height * scalepercent)
	    };
	} else {
	    // degenerative case only panner is rendered.
	    //
	    pkRect = {
		width: this._puck.clientWidth,
		height: this._puck.clientHeight
	    };
	    vpRect = {
		width: this._puck.clientWidth * this.scale,
		height: this._puck.clientHeight * this.scale
	    };
	}
    } else {
	pkRect = this.puckRect;
	var vW = pkRect.width > 0 ? pkRect.width : this._puck.clientWidth;
	var vH = pkRect.height > 0 ? pkRect.height : this._puck.clientHeight;
	if (vpRect == null) {
	    vpRect = {
		width: vW * this.scale,
		height: vH * this.scale
	    };
	}
    }

    // Create the panner's canvas representation
    //
    this._domNode.style.width = pnRect.width + this._units;
    this._domNode.style.height = pnRect.height + this._units;

    // The "puck", the canvas viewport representation
    //
    this._puck.style.width = pkRect.width + this._units;
    this._puck.style.height = pkRect.height + this._units;

    // replicate the canvas image in the panner
    // Doesn't seem to work in IE6, needs some work, but "looks cool" ;)
    // The image seems to interfere with mouse events, z-index
    // doesn't help.
    //
    /*
    var pimg = document.getElementById("pannerimg");
    pimg.width = this.pannerRect.width;
    pimg.height = this.pannerRect.height;
    */

    // Calculate the horizontal position of the canvas
    // The puck can only move "pannerRect.width - puckRect.width" pixels
    // left or right.
    // And when the puck is at the left edge, the canvas is at
    // "left == 0". When the puck is at the right edge the canvas
    // "left == -(canvasRect.width - viewportRect.widht)".
    // Since the "puck" can only move "pannerRect.width - puckRect.width"
    // pixels, each time the puck moves by a pixel the canvas moves by
    // "(canvasRect.width - viewportRect.width)/(pannerRect.width - puckRect.width)" 
    // where the special cases are the left and right edges, the
    // "puck" at "0" and "pannerRect.width - puckRect.width"
    //    
    this._xInc = (cvRect.width - vpRect.width)/
	(pnRect.width - pkRect.width);

    // Similarly for the vertical increment
    //
    this._yInc = (cvRect.height - vpRect.height)/
	(pnRect.height - pkRect.height);

    // Get the puck's border
    // Assume it is the same for all borders
    //
    this._border = (this._puck.offsetWidth - this._puck.clientWidth)/2;

    // Create the extents for the X and Y position of the puck
    //
    this._vpXmax = pnRect.width - pkRect.width - this._border;
    this._vpYmax = pnRect.height - pkRect.height - this._border;

    // Create the extents for the X and Y position of the real canvas
    //
    this._cXmax = cvRect.width - vpRect.width;
    this._cYmax = cvRect.height - vpRect.height;

    // Set an initial position if one was given.
    // And update the canvas position if the puck changes size
    //
    if (this.canvasX != 0) {
	var puckleft = Math.abs(Math.round((this.canvasX * scaleX) - 
		this._border));
	if (puckleft > 0) {
	    if (puckleft + pkRect.width + 
		    (2 * this._border) >= this._vpXmax) {
		this._puck.style.left = this._vpXmax + this._units;
		if (this._canvas != null) {
		    this._canvas.style.left = -this._cXmax + this._units;
		}
	    } else {
		this._puck.style.left = puckleft + this._units;
		if (this._canvas != null) {
		    this._canvas.style.left = -this.canvasX + this._units;
		}
	    }
	}
    }

    if (this.canvasY != 0) {
	var top = Math.abs(Math.round((this.canvasY * scaleY) - this._border));
	if (top > 0) {
	    if (top + pkRect.height +
		    (2 * this._border) >= this._vpYmax) {
		this._puck.style.top = this._vpYmax + this._units;
		if (this._canvas != null) {
		    this._canvas.style.top = -this._cYmax + this._units;
		}
	    } else { 
		this._puck.style.top = top + this._units;
		if (this._canvas != null) {
		    this._canvas.style.top = -this.canvasY + this._units;
		}
	    }
	}
    }
};

/**
 * Connects the mouse event handlers to control the puck, and initializes
 * the current mouse position inside the puck. If <code>publishEvents</code>
 * is true, publish the <code>event.pan.start</code> topic.
 *
 * @param {Object} ev The current event object.
 * @return {boolean} true if successful; otherwise false.
 * @private
 */
@JS_NS@.widget.panner.prototype._panstart = function(ev) {

    if (this._ispanning) {
	return true;
    }
    if (this.publishEvents == true) {
	this._publish(this.event.pan.start, [{ id : this.id }]);
    }

    this._onmouseupHandle = 
	this._dojo.connect(this._puck, "onmouseup", this, "_panstop");
    this._onmouseoutHandle = 
	this._dojo.connect(this._puck, "onmouseout", this, "_panstop");
    this._onmousemoveHandle = 
	this._dojo.connect(this._puck, "onmousemove", this, "_pan");

    // browser quirk, MS only knows "srcElement"
    //
    if (ev.target == null) {
	vp = ev.srcElement;
	this._mX = ev.offsetX;
	this._mY = ev.offsetY;
    } else {
	this._mX = ev.layerX;	// mouse X
	this._mY = ev.layerY;	// moust Y
    }

    this._ispanning = true;
    return true;
};

/**
 * Disconnects the mouse event handlers that control the puck.
 * If <code>publishEvents</code> is true, publish the
 * <code>event.pan.stop</code> topic.
 *
 * @param {Object} ev The current event object.
 * @return {boolean} true if successful; otherwise false.
 * @private
 */
@JS_NS@.widget.panner.prototype._panstop = function() {

    if (this.publishEvents == true) {
	this._publish(this.event.pan.stop, [{ id : this.id }]);
    }
    if (this._ispanning) {
	this._dojo.disconnect(this._onmouseupHandle);
	this._dojo.disconnect(this._onmousedownHandle);
	this._dojo.disconnect(this._onmousemoveHandle);

	this._onmouseupHandle = null;
	this._onmousedownHandle = null;
	this._onmousemoveHandle = null;
    }

    this._ispanning = false;

    return true;
};

/**
 * Control the panner puck position and the viewport on the canvas.
 * Always sets the <code>canvasX</code> and <code>canvasY</code>
 * properties even when there is no canvas or viewport.
 * If <code>publishEvents</code> is true, publish the
 * <code>event.pan.moveBegin</code> topic on entering this method
 * and the <code>event.pan.moveBegin</code> topic on exit.
 *
 * @param {Object} ev The current event object.
 * @return {boolean} true if successful; otherwise false.
 * @private
 */
@JS_NS@.widget.panner.prototype._pan = function(ev) {

    if (!this._ispanning) {
	return true;
    }
    if (this.publishEvents == true) {
	this._publish(this.event.pan.moveBegin, [{ id : this.id }]);
    }
    var vp = ev.target; // The "puck"
    var x = ev.layerX;  // Mouse X
    var y = ev.layerY;  // Mouse Y

    // browser quirk MS only knows srcElement
    //
    if (!vp) {
	vp = ev.srcElement;
	x = ev.offsetX;
	y = ev.offsetY;
    }

    // Canvas top and left
    //
    var canvasX = 0;
    var canvasY = 0;

    // In order to consider the "offsetLeft" from the "virtual origin"
    // add the "panner.border" to the "offsetLeft" to 
    // account for the double panner border. As long as the 
    // puck's border is equal to this number, that's all that is 
    // needed.
    //
    // Get the top left corner of the puck
    // Note that offsetWidth is defined by MS and not w3c
    // but use it anyway since Firefox and Safari support it.
    // It is part of the new W3C "View" DOM cor
    //
    // x - this._mX is the amount the mouse has moved.
    //
    x = (x - this._mX) + (vp.offsetLeft + this._border);
    if (x < 0) {
	x = -this._border;
	canvasX = 0;
    } else {
	if (x >= this._vpXmax) {
	    x = this._vpXmax;
	    canvasX = -(this._cXmax);
	} else {
	    canvasX = -((x + this._border) * this._xInc);
	}
    }

    y = (y - this._mY) + vp.offsetTop + this._border;
    if (y < 0) {
	y = -this._border;
	canvasY = 0;
    } else {
	if (y >= this._vpYmax) {
	    y = this._vpYmax;
	    canvasY = -(this._cYmax);
	} else {
	    canvasY = -((y + this._border) * this._yInc);
	}
    }
    vp.style.top = y + this._units;
    vp.style.left = x + this._units;

    // Also need to publish event here.
    //
    if (this._canvas != null) {
	this._canvas.style.left = canvasX + this._units;
	this._canvas.style.top = canvasY + this._units;
    }
    this.canvasX = Math.abs(Math.round(canvasX));
    this.canvasY = Math.abs(Math.round(canvasY));

    if (this.publishEvents == true) {
	this._publish(this.event.pan.moveEnd, [{ id : this.id }]);
    }
    return true;
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
@JS_NS@.widget.panner.prototype._postCreate = function () {

    // Set ids.
    if (this.id) {
	this._domNode.id = this.id;
    }

    this._dojo.connect(this._puck, "onmousedown", this, "_panstart");

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
@JS_NS@.widget.panner.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // If we are initializing don't do this. It will be done in startup
    //
    if (this.initialized && (props.scale != null ||
	    props.canvasId != null || props.viewportId != null || 
	    props.canvasRect != null || props.viewportRect != null ||
	    props.pannerRect != null || props.puckRect != null)) {

	// Make sure we recalculate. Not sure if it makes sense to 
	// allow the dimensions of the canvasId or viewportId elements
	// to be different than the canvasRect and viewportRect.
	// 
	if (props.canvasId != null) {
	    delete this.canvasRect;
	}
	if (props.viewportId != null) {
	    delete this.viewportRect;
	}

	this._initCanvasViewport();
    }

    // Set more properties.
    this._setEventProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.panner.prototype._startup = function () {

    if (this._started == true) {
        return false;
    }

    // This method must be called at a point where the CSS for the
    // panner and puck has been applied.
    //
    this._initCanvasViewport();

    return this._inherited("_startup", arguments);
};
