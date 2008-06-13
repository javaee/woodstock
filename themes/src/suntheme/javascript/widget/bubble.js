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

@JS_NS@._dojo.provide("@JS_NS@.widget.bubble");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a bubble widget.
 *
 * @constructor
 * @name @JS_NS@.widget.bubble
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @class This class contains functions for the bubble widget.
 * <p>
 * The bubble widget displays a pop up window which appears on screen in 
 * response to certain mouse interactions. The purpose of the bubble widget is
 * to provide detailed information to the end user when a mouse hovers on a 
 * particular HTML element. The bubble widget exposes two main JavaScript 
 * functions; "open()" and "close()". These two functions are invoked by the 
 * element that requires bubble help.
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
 *       id: "bub1",
 *       contents: [{
 *         id: "st1",
 *         value: "Bubble Help Text",
 *         widgetType: "text",
 *       }],
 *       title: "Bubble Title",
 *       widgetType: "bubble"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 1b: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using relative positioning.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "bub1",
 *       contents: [{
 *         id: "st1",
 *         value: "Bubble Help Text",
 *         widgetType: "text",
 *       }],
 *       style: "position:relative;top:200px;left:480px;width:20em;z-index:99;",
 *       title: "Bubble Title",
 *       widgetType: "bubble"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 1c: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget which opens and closes the bubble
 * window.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "bub1",
 *       contents: [{
 *         id: "st1",
 *         value: "Bubble Help Text",
 *         widgetType: "text",
 *       }],
 *       title: "Bubble Title",
 *       widgetType: "bubble"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Show Bubble" },
 *       onMouseOver: "@JS_NS@.widget.common.getWidget('bub1').open(event);"
 *       onMouseOut: "@JS_NS@.widget.common.getWidget('bub1').close();"
 *       widgetType: "checkbox"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to set the title of a widget using the getProps and 
 * setProps functions. When the user clicks the checkbox, the bubble title is
 * updated.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "bub1",
 *       contents: [{
 *         id: "st1",
 *         value: "Bubble Help Text",
 *         widgetType: "text",
 *       }],
 *       title: "Bubble Title",
 *       widgetType: "bubble"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Change Bubble Text" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("bub1"); // Get bubble
 *       var newTitle = "New " + widget.getProps().title;
 *       return widget.setProps({title: newTitle}); // Update bubble title
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the bubble is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "bub1",
 *       contents: [{
 *         id: "st1",
 *         value: "Bubble Help Text",
 *         widgetType: "text",
 *       }],
 *       title: "Bubble Title",
 *       widgetType: "bubble"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh bubble" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("bub1"); // Get bubble
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
 * This example shows how to asynchronously update an bubble using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the bubble text
 * is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "bub1",
 *       contents: [{
 *         id: "st1",
 *         value: "Bubble Help Text",
 *         widgetType: "text",
 *       }],
 *       title: "Bubble Title",
 *       widgetType: "bubble"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change bubble Title" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("bub1"); // Get bubble
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
 *            if (props.id == "bub1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.bubble.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} autoClose 
 * @config {Object} closeButton 
 * @config {Array} contents 
 * @config {int} duration 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {int} openDelay 
 * @config {String} title Provides a title for element.
 * @config {int} width 
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.bubble", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        this.defaultTime = 2000;
        this.openDelayTime = 500;
        this.bubbleLeftConst = 5;
        this.topConst = 2;
    },   
    _widgetType: "bubble" // Required for theme properties.
});

/**
 * This function is used to close bubble help.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.bubble.prototype.close = function() {
    if (this.openTimerId != null) {
        clearTimeout(this.openTimerId);
    }
    if (this.visible == false) {
        return false;
    }
     
    var _id = this.id;
    this.timerId = setTimeout(function() {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        var getWidget = @JS_NS@.widget.common.getWidget;
        getWidget(_id).setProps({visible: false});
        getWidget(_id).srcElm.focus();
    }, this.defaultTime);

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
@JS_NS@.widget.bubble.event =
        @JS_NS@.widget.bubble.prototype.event = {
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
         * @id @JS_NS@.widget.bubble.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_bubble_event_refresh_begin",

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         *
         * @id @JS_NS@.widget.bubble.event.refresh.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_bubble_event_refresh_end"
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
         * @id @JS_NS@.widget.bubble.event.submit.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_bubble_event_state_begin",

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.bubble.event.submit.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_bubble_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.bubble.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.title != null) { props.title = this.title; }
    if (this.contents != null) { props.contents = this.contents; }
    if (this.height != null) { props.height = this.height; }
    if (this.width != null) { props.width = this.width; }
    if (this.autoClose != null) { props.autoClose = this.autoClose; }
    if (this.duration != null) { props.duration = this.duration; }
    if (this.closeButton != null) {props.closeButton = this.closeButton;}
    if (this.openDelay != null) {props.openDelay = this.openDelay;}
    if (this.focusId != null) {props.focusId = this.focusId;}
    if (this.tabIndex != null) {props.tabIndex = this.tabIndex;}
    
    return props;
};

/**
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.bubble.prototype._onClickCallback = function(event) {
    // Close the popup if close button is clicked.
    event = this._widget._getEvent(event);

    var target = (event.target)
        ? event.target 
        : ((event.srcElement) 
            ? event.srcElement : null);

    if (@JS_NS@._base.browser._isIe5up()) {
        if (window.event != null) {
            window.event.cancelBubble = true;
        }
    } else {
        event.stopPropagation();
    }
    if (this._closeBtn == target) {
        clearTimeout(this.timerId);
        this.setProps({visible: false});
        this.srcElm.focus();
    }
    return true;
};

/**
 * Helper function to create callback for close event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.bubble.prototype._onCloseCallback = function(event) {
    if (event == null) {
        return false;
    }
    
    if ((event.type == "keydown" && event.keyCode == 27)
            || event.type == "click") {
        clearTimeout(this.timerId); 
        
        if (this.srcElm != null && this.visible) {
            if (this.srcElm.focus) {
                this.srcElm.focus();
            }
        }      
        this.setProps({visible: false});
    }
    return true;
};

/**
 * Helper function to create callback for shift + tab event.
 * Shift+Tab should not allow user to tab out of bubble component.
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.bubble.prototype._onShiftTabCallback = function(event) {
    if (event == null) {
        return false;
    }
    event = this._widget._getEvent(event);

    var target = (event.target)
        ? event.target 
        : ((event.srcElement) 
            ? event.srcElement : null);
    if (target == this._bubbleHeader) {                    
        if (@JS_NS@._base.browser._isFirefox() && (event.shiftKey && (event.keyCode == 9))) {
            if (this.focusId != null) {
                document.getElementById(this.focusId).focus();        
            } else {                
                 this._bubbleHeader.focus();
            }
            event.stopPropagation();
            event.preventDefault(); 
        }
     }
     return true;
};

/**
 * Helper function to create callback for tab event.
 * Cyclic tabbing behavior is implemented for bubble to prevent tab out of bubble component. 
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.bubble.prototype._onTabCallback = function(event) {
    if (event == null) {
        return false;
    }
    event = this._widget._getEvent(event);

    var target = (event.target)
        ? event.target 
        : ((event.srcElement) 
            ? event.srcElement : null);
    if (@JS_NS@._base.browser._isFirefox()) {        
        if (this._contentEnd == target) {
            this._bubbleHeader.focus();
        } else if (this._bubbleHeader == target && this.focusId != null && (event.keyCode == 9)) {
            document.getElementById(this.focusId).focus();            
        }
        event.stopPropagation();
        event.preventDefault(); 
    }
    return true;
};
    
/**
 * Helper function to create callback for onMouseOver event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.bubble.prototype._onMouseOverCallback = function(event) {
    clearTimeout(this.timerId);
    return true;
};

/**
 * Helper function to create callback for onMouseOut event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.bubble.prototype._onMouseOutCallback = function(event) {
    if (this.autoClose == true) {
        clearTimeout(this.timerId);            
        this.close();            
    }
    return true;
};

/**
 * This function is use to invoke buuble help.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.bubble.prototype.open = function(event) {
    // Get the absolute position of the target.
    var evt = this._widget._getEvent(event);
    // A11Y - open the bubble if its Ctrl key + F1
    if (evt.type == "keydown") {
        if (!(evt.ctrlKey && (evt.keyCode == 112))) {
            return false;
        }
        evt.stopPropagation();
        evt.preventDefault();  
    }
    this.srcElm = (evt.target) 
        ? evt.target : ((evt.srcElement) 
            ? evt.srcElement : null);

    var absPos = this._widget._getPosition(this.srcElm);
    this.srcElm.targetLeft = absPos[0];
    this.srcElm.targetTop = absPos[1];
   
    if (this.timerId != null) {
        clearTimeout(this.timerId);
        this.timerId = null;
    }
    
    if (this.openDelay != null && this.openDelay >= 0) {
        this.openDelayTime = this.openDelay;
    }

    // There should be delay before opening the bubble if open delay is specified.
    // If openDelay is less than zero then there will be dafault 0.5 sec delay.  
    
    var id = this.id; // Closure magic.
    this.openTimerId = setTimeout(function() {
        var getWidget = @JS_NS@.widget.common.getWidget;

        // Store the active bubble id to form element.
        // Check for the id if its available then close the pending bubble.
        if (@JS_NS@.widget.bubble.activeBubbleId && @JS_NS@.widget.bubble.activeBubbleId != id) {                
            clearTimeout(getWidget(@JS_NS@.widget.bubble.activeBubbleId).timerId);
            getWidget(@JS_NS@.widget.bubble.activeBubbleId).setProps({visible: false});
            @JS_NS@.widget.bubble.activeBubbleId = null;                
        }     
        @JS_NS@.widget.bubble.activeBubbleId = id;            
        getWidget(id).setProps({visible: true});
        getWidget(id)._setPosition();
    }, this.openDelayTime);           
    
    if (this.duration != null && this.duration >= 0) {
        this.defaultTime = this.duration;
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
@JS_NS@.widget.bubble.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._bottomLeftArrow.id = this.id + "_bottomLeftArrow";
        this._bottomRightArrow.id = this.id + "_bottomRightArrow";
        this._topLeftArrow.id = this.id + "_topLeftArrow";
        this._topRightArrow.id = this.id + "_topRightArrow";
    }

    // Set public functions.

    /** @ignore */
    this._domNode.close = function() { return @JS_NS@.widget.common.getWidget(this.id).close(); };
    /** @ignore */
    this._domNode.open = function(event) { return @JS_NS@.widget.common.getWidget(this.id).open(event); };

    // Set events.

    // The onClick on window should close bubble.
    this._dojo.connect(document, "onclick", this, "_onCloseCallback");

    // The escape key should also close bubble.
    this._dojo.connect(document, "onkeydown", this, "_onCloseCallback");

    // The onClick event for component body. Closes the bubble only when
    // close button is clicked.
    this._dojo.connect(this._domNode, "onclick", this, "_onClickCallback");

    // Do not close the popup if mouseover on bubble if mouseover on bubble 
    // component then clear the timer and do not close bubble.
    this._dojo.connect(this._domNode, "onmouseover", this, "_onMouseOverCallback");

    // Close the popup if mouseout and autoClose is true if onmouseout and 
    // autoClose is true then close the bubble.
    this._dojo.connect(this._domNode, "onmouseout", this, "_onMouseOutCallback");
    
    // The onfocus event for _contentEnd. This is needed to handle tab event. 
    this._dojo.connect(this._contentEnd, "onfocus", this, "_onTabCallback");
    
    // The onkeydown event for _bubbleHeader. This is needed to handle tab event. 
    this._dojo.connect(this._bubbleHeader, "onkeydown", this, "_onTabCallback");
    
    // The onkeydown event for component body. This is needed to handle shift+tab event.
    this._dojo.connect(this._domNode, "onkeydown", this, "_onShiftTabCallback");
    
    // Initialize the bubble title width as a percentage of the bubble header.    
    if (this._bubbleTitle != null) {
        this._bubbleTitle.style.width = this._theme.getProperty("styles", 
            "BUBBLE_TITLEWIDTH") + "%";
    }
    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to position the bubble.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.bubble.prototype._setPosition = function() {
    // THIS CODE BLOCK IS NECESSARY WHEN THE PAGE FONT IS VERY SMALL,
    // AND WHICH OTHERWISE CAUSES THE PERCENTAGE OF THE HEADER WIDTH
    // ALLOCATED TO THE BUBBLE TITLE TO BE TOO LARGE SUCH THAT IT
    // ENCROACHES ON THE SPACE ALLOCATED FOR THE CLOSE BUTTON ICON,
    // RESULTING IN LAYOUT MISALIGNMENT IN THE HEADER.

    // Assume bubble title width max percentage of the bubble header.
    var maxPercent = this._theme.getProperty("styles", "BUBBLE_TITLEWIDTH");

    // Sum of widths of all elements in the header BUT the title.  This includes
    // the width of the close button icon, and the margins around the button and
    // the title.  This should be a themeable parameter that matches the left/right
    // margins specified in the stylesheet for "BubbleTitle" and "BubbleCloseBtn".
    var nonTitleWidth = this._theme.getProperty("styles", "BUBBLE_NONTITLEWIDTH");

    // Get the widths (in pixels) of the bubble header and title
    var headerWidth = this._bubbleHeader.offsetWidth;
    var titleWidth = this._bubbleTitle.offsetWidth;

    // Revise the aforementioned percentage downward until the title no longer
    // encroaches on the space allocated for the close button.  We decrement by
    // 5% each time because doing so in smaller chunks when the font gets very small so 
    // only results in unnecessary extra loop interations.
    //
    if (headerWidth > nonTitleWidth) {
        while ((maxPercent > 5) && (titleWidth > (headerWidth - nonTitleWidth))) {
            maxPercent -= 5;
            this._bubbleTitle.style.width = maxPercent + "%";
            titleWidth = this._bubbleTitle.offsetWidth;
        }
    }

    // Get DOM bubble object associated with this Bubble instance.
    var bubble = this._domNode;

    // If this.style is not null that means developer has specified positioning
    // for component. 
    if (this._domNode != null && this.style != null && this.style.length > 0) {
        if (bubble.style.length != null) {
            for (var i = 0; i < bubble.style.length; i++) {
                if (bubble.style[i] == "top") {
                    this.top = bubble.style.top;
                }
                if (bubble.style[i] == "left") {
                    this.left = bubble.style.left;
                }
            }
        } else {
            // For IE, simply query the style attributes.
            if (bubble.style.top != "") {
                this.top = bubble.style.top;
            }
            if (bubble.style.left != "") {
                this.left = bubble.style.left;
            }
        }
    }

    if ((this.top != null) && (this.left != null)) {
        bubble.style.left = this.left;
        bubble.style.top = this.top;    
    } else {
        // Why are we calling getElementById here instead of using the dojoAttachPoint?
        var topLeftArrow = document.getElementById(this._topLeftArrow.id);
        var topRightArrow = document.getElementById(this._topRightArrow.id);
        var bottomLeftArrow = document.getElementById(this._bottomLeftArrow.id);
        var bottomRightArrow = document.getElementById(this._bottomRightArrow.id);

        // hide all callout arrows.
        this._common._setVisible(bottomLeftArrow, false);
        this._common._setVisible(bottomRightArrow, false);
        this._common._setVisible(topLeftArrow, false);
        this._common._setVisible(topRightArrow, false);

        bottomLeftArrow.style.display = "none";
        bottomRightArrow.style.display = "none";
        topLeftArrow.style.display = "none";
        topRightArrow.style.display = "none";

        var slidLeft = false;

        // Assume default bubble position northeast of target, which implies a 
        // bottomLeft callout arrow
        this.arrow = bottomLeftArrow;

        // Try to position bubble to right of srcElm.
        var bubbleLeft = this.srcElm.targetLeft + this.srcElm.offsetWidth + this.bubbleLeftConst;

        // Check if right edge of bubble exceeds page boundary.
        var rightEdge = bubbleLeft + bubble.offsetWidth;
        if (rightEdge > this._widget._getPageWidth()) {

            // Shift bubble to left side of target;  implies a bottomRight arrow.
            bubbleLeft = this.srcElm.targetLeft - bubble.offsetWidth;
            this.arrow = bottomRightArrow;
            slidLeft = true;

            // If left edge of bubble crosses left page boundary, then
            // reposition bubble back to right of target and implies to go
            // back to bottomLeft arrow.  User will need to use scrollbars
            // to position bubble into view.
            if (bubbleLeft <= 0) {
                bubbleLeft = this.srcElm.targetLeft + this.srcElm.offsetWidth + this.bubbleLeftConst;
                this.arrow = bottomLeftArrow;
                slidLeft = false;
            }
        }

        // Try to position bubble above source element
        var bubbleTop = this.srcElm.targetTop - bubble.offsetHeight;

        // Check if top edge of bubble crosses top page boundary
        if (bubbleTop <= 0) {
            // Shift bubble to bottom of target.  User may need to use scrollbars
            // to position bubble into view.
            bubbleTop = this.srcElm.targetTop + this.srcElm.offsetHeight + this.bubbleLeftConst;

            // Use appropriate top arrow depending on left/right position.
            if (slidLeft == true)
                this.arrow = topRightArrow;
            else
                this.arrow = topLeftArrow;
        }
        // Adjust to account for parent container.
        var parentPos = this._widget._getPosition(this._domNode.offsetParent);
        bubbleLeft -= parentPos[0];
        bubbleTop -= parentPos[1]; 
        // Set new bubble position.
        bubble.style.left = bubbleLeft + "px";
        bubble.style.top = bubbleTop + "px";

        // If rendering a callout arrow, set it's position relative to the bubble.
        if (this.arrow != null) {
           this.arrow.style.display = "block";
           this._common._setVisible(this.arrow, true);

           if (this.arrow == topLeftArrow) {
               this.arrow.style.top = -(bubble.offsetHeight - this.topConst) + "px";               
           }
           if (this.arrow == topRightArrow) {
               this.arrow.style.top = -(bubble.offsetHeight - this.topConst) + "px";               
           }
        }
    }
    if (this.focusId != null) {
        document.getElementById(this.focusId).focus();        
    } else {
        if (@JS_NS@._base.browser._isFirefox()) {
            this._bubbleHeader.focus();
        }
    }
    return true;
};

/**
 * This function is used to set widget properties using Object literals. Please
 * see the constructor detail for a list of supported properties.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.bubble.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return this._inherited("setProps", arguments);
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
@JS_NS@.widget.bubble.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }
    //Cyclic focus behavior is supported for firefox browser only.
    //If tabIndex values are provided for elements inside bubble then developer needs to set a valid tabIndex 
    //value for bubble component to achieve cyclic focus behavior. 
    if (@JS_NS@._base.browser._isFirefox()) {
        if (this.tabIndex >= 0) {
            this._contentEnd.tabIndex = this.tabIndex;
        } else {
            this._contentEnd.tabIndex = 0;
        }   
    }
    // Set title.
    if (props.title) {
        this._widget._addFragment(this._titleNode, props.title);
    }

    // hide/display close button
    if (props.closeButton != null) {
        var classNames = this._closeBtn.className.split(" ");
        var closeButtonClass = this._theme.getClassName("BUBBLE_CLOSEBTN");
        var noCloseButtonClass = this._theme.getClassName("BUBBLE_NOCLOSEBTN");

        if (props.closeButton == false) {
            this._common._stripStyleClass(this._closeBtn, closeButtonClass);
            if (!this._common._checkStyleClasses(classNames, noCloseButtonClass))
             this._common._addStyleClass(this._closeBtn, noCloseButtonClass);
        } else {          
          if (!this._common._checkStyleClasses(classNames, closeButtonClass))
             this._common._addStyleClass(this._closeBtn, closeButtonClass);
        }
    }

    // Set width.
    if (props.width > 0) {                    
        this._domNode.style.width = props.width + "px";        
    }

    // Set contents.
    if (props.contents) {
        // Remove child nodes.
        this._widget._removeChildNodes(this._childNode);

        for (var i = 0; i < props.contents.length; i++) {
            this._widget._addFragment(this._childNode, props.contents[i], "last");
        }
    }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
