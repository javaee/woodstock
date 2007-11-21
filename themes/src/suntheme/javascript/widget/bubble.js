// widget/bubble.js
//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except inf
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.widget.bubble");

dojo.require("webui.@THEME@.browser");
dojo.require("webui.@THEME@.common");
dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.bubble
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for the bubble widget.
 * @constructor This function is used to construct a bubble widget.
 */
dojo.declare("webui.@THEME@.widget.bubble", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    defaultTime: 2000,
    openDelayTime: 500,
    bubbleLeftConst: 5,
    topConst: 2,    
    widgetName: "bubble" // Required for theme properties.
});

/**
 * This function is used to close bubble help.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.close = function() {
    if (this.openTimerId != null) {
        clearTimeout(this.openTimerId);
    }
    if (this.getProps().visible == false) {
        return false;
    }
     
    var _id = this.id;
    this.timerId = setTimeout(function() {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        dijit.byId(_id).setProps({visible: false});
    }, this.defaultTime);

    return true;
}

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME@.widget.bubble.event =
        webui.@THEME@.widget.bubble.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_bubble_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_bubble_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_bubble_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_bubble_event_state_end"
    }
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.bubble.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.title != null) { props.title = this.title; }
    if (this.contents != null) { props.contents = this.contents; }
    if (this.height != null) { props.height = this.height; }
    if (this.width != null) { props.width = this.width; }
    if (this.autoClose != null) { props.autoClose = this.autoClose; }
    if (this.duration != null) { props.duration = this.duration; }
    if (this.closeButton != null) {props.closeButton = this.closeButton;}
    if (this.openDelay != null) {props.openDelay = this.openDelay;}
    
    return props;
}

/**
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.onClickCallback = function(event) {
    // Close the popup if close button is clicked.
    event = this.widget.getEvent(event);

    var target = (event.target)
        ? event.target 
        : ((event.srcElement) 
            ? event.srcElement : null);

    if (webui.@THEME@.browser.isIe5up()) {
        if (window.event != null) {
            window.event.cancelBubble = true;
        }
    } else {
        event.stopPropagation();
    }
    if (this.closeBtn == target) {
        clearTimeout(this.timerId);
        this.setProps({visible: false});
    }
    return true;
}

/**
 * Helper function to create callback for close event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.onCloseCallback = function(event) {
    if (event == null) {
        return false;
    }
    if ((event.type == "keydown" && event.keyCode == 27)
            || event.type == "click") {
        clearTimeout(this.timerId); 
        this.setProps({visible: false});  
    }
    return true;
}

/**
 * Helper function to create callback for onMouseOver event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.onMouseOverCallback = function(event) {
    clearTimeout(this.timerId);
    return true;
}

/**
 * Helper function to create callback for onMouseOut event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.onMouseOutCallback = function(event) {
    if (this.autoClose == true) {
        clearTimeout(this.timerId);            
        this.close();            
    }
    return true;
}

/**
 * This function is use to invoke buuble help.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.open = function(event) {
    // Get the absolute position of the target.
    var evt = this.widget.getEvent(event);

    this.target = (evt.target) 
        ? evt.target : ((evt.srcElement) 
            ? evt.srcElement : null);

    var absPos = this.widget.getPosition(this.target);
    this.target.targetLeft = absPos[0];
    this.target.targetTop = absPos[1];
   
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
        // Store the active bubble id to form element.
        // Check for the id if its available then close the pending bubble.
        if (webui.@THEME@.widget.bubble.activeBubbleId && webui.@THEME@.widget.bubble.activeBubbleId != id) {                
            clearTimeout(dijit.byId(webui.@THEME@.widget.bubble.activeBubbleId).timerId);
            dijit.byId(webui.@THEME@.widget.bubble.activeBubbleId).setProps({visible: false});
            webui.@THEME@.widget.bubble.activeBubbleId = null;                
        }     
        webui.@THEME@.widget.bubble.activeBubbleId = id;            
        dijit.byId(id).setProps({visible: true});
        dijit.byId(id).setPosition();
    }, this.openDelayTime);           
    
    if (this.duration != null && this.duration >= 0) {
        this.defaultTime = duration;
    } 
    return true;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.bottomLeftArrow.id = this.id + "_bottomLeftArrow";
        this.bottomRightArrow.id = this.id + "_bottomRightArrow";
        this.topLeftArrow.id = this.id + "_topLeftArrow";
        this.topRightArrow.id = this.id + "_topRightArrow";
    }

    // Set public functions.
    this.domNode.close = function() { return dijit.byId(this.id).close(); }
    this.domNode.open = function(event) { return dijit.byId(this.id).open(event); }

    // Set events.

    // The onClick on window should close bubble.
    dojo.connect(document, "onclick", this, "onCloseCallback");

    // The escape key should also close bubble.
    dojo.connect(document, "onkeydown", this, "onCloseCallback");

    // The onClick event for component body. Closes the bubble only when
    // close button is clicked.
    dojo.connect(this.domNode, "onclick", this, "onClickCallback");

    // Do not close the popup if mouseover on bubble if mouseover on bubble 
    // component then clear the timer and do not close bubble.
    dojo.connect(this.domNode, "onmouseover", this, "onMouseOverCallback");

    // Close the popup if mouseout and autoClose is true if onmouseout and 
    // autoClose is true then close the bubble.
    dojo.connect(this.domNode, "onmouseout", this, "onMouseOutCallback");

    // Initialize the BubbleTitle width as a percentage of the bubble header.
        
    if (this.bubbleTitle != null) {
        this.bubbleTitle.style.width = this.theme.getProperty("styles", 
            "BUBBLE_TITLEWIDTH") + "%";
    }
    return this.inherited("postCreate", arguments);
}

/**
 * This function is used to position the bubble.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.setPosition = function() {
    
    // THIS CODE BLOCK IS NECESSARY WHEN THE PAGE FONT IS VERY SMALL,
    // AND WHICH OTHERWISE CAUSES THE PERCENTAGE OF THE HEADER WIDTH
    // ALLOCATED TO THE BUBBLE TITLE TO BE TOO LARGE SUCH THAT IT
    // ENCROACHES ON THE SPACE ALLOCATED FOR THE CLOSE BUTTON ICON,
    // RESULTING IN LAYOUT MISALIGNMENT IN THE HEADER.

    // Assume BubbleTitle width max percentage of the bubble header.
    var maxPercent = this.theme.getProperty("styles", "BUBBLE_TITLEWIDTH");

    // Sum of widths of all elements in the header BUT the title.  This includes
    // the width of the close button icon, and the margins around the button and
    // the title.  This should be a themeable parameter that matches the left/right
    // margins specified in the stylesheet for "BubbleTitle" and "BubbleCloseBtn".
    var nonTitleWidth = this.theme.getProperty("styles", "BUBBLE_NONTITLEWIDTH");

    // Get the widths (in pixels) of the bubble header and title
    var headerWidth = this.bubbleHeader.offsetWidth;
    var titleWidth = this.bubbleTitle.offsetWidth;

    // Revise the aforementioned percentage downward until the title no longer
    // encroaches on the space allocated for the close button.  We decrement by
    // 5% each time because doing so in smaller chunks when the font gets very small so 
    // only results in unnecessary extra loop interations.
    //
    if (headerWidth > nonTitleWidth) {
        while ((maxPercent > 5) && (titleWidth > (headerWidth - nonTitleWidth))) {
            maxPercent -= 5;
            this.bubbleTitle.style.width = maxPercent + "%";
            titleWidth = this.bubbleTitle.offsetWidth;
        }
    }

    // Get DOM bubble object associated with this Bubble instance.
    var bubble = this.domNode;

    // If this.style is not null that means developer has specified positioning
    // for component. 
    if (this.domNode != null && this.style != null) {
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

        var topLeftArrow = document.getElementById(this.topLeftArrow.id);
        var topRightArrow = document.getElementById(this.topRightArrow.id);
        var bottomLeftArrow = document.getElementById(this.bottomLeftArrow.id);
        var bottomRightArrow = document.getElementById(this.bottomRightArrow.id);
        // hide all callout arrows.
        webui.@THEME@.common.setVisible(bottomLeftArrow, false);
        webui.@THEME@.common.setVisible(bottomRightArrow, false);
        webui.@THEME@.common.setVisible(topLeftArrow, false);
        webui.@THEME@.common.setVisible(topRightArrow, false);

        bottomLeftArrow.style.display = "none";
        bottomRightArrow.style.display = "none";
        topLeftArrow.style.display = "none";
        topRightArrow.style.display = "none";

        var slidLeft = false;

        // Assume default bubble position northeast of target, which implies a 
        // bottomLeft callout arrow
        this.arrow = bottomLeftArrow;

        // Try to position bubble to right of target.
        var bubbleLeft = this.target.targetLeft + this.target.offsetWidth + this.bubbleLeftConst;

        // Check if right edge of bubble exceeds page boundary.
        var rightEdge = bubbleLeft + bubble.offsetWidth;
        if (rightEdge > this.widget.getPageWidth()) {

            // Shift bubble to left side of target;  implies a bottomRight arrow.
            bubbleLeft = this.target.targetLeft - bubble.offsetWidth;
            this.arrow = bottomRightArrow;
            slidLeft = true;

            // If left edge of bubble crosses left page boundary, then
            // reposition bubble back to right of target and implies to go
            // back to bottomLeft arrow.  User will need to use scrollbars
            // to position bubble into view.
            if (bubbleLeft <= 0) {
                bubbleLeft = this.target.targetLeft + this.target.offsetWidth + this.bubbleLeftConst;
                this.arrow = bottomLeftArrow;
                slidLeft = false;
            }
        }

        // Try to position bubble above target
        var bubbleTop = this.target.targetTop - bubble.offsetHeight;

        // Check if top edge of bubble crosses top page boundary
        if (bubbleTop <= 0) {
            // Shift bubble to bottom of target.  User may need to use scrollbars
            // to position bubble into view.
            bubbleTop = this.target.targetTop + this.target.offsetHeight + this.bubbleLeftConst;

            // Use appropriate top arrow depending on left/right position.
            if (slidLeft == true)
                this.arrow = topRightArrow;
            else
                this.arrow = topLeftArrow;
        }

        // Set new bubble position.
        bubble.style.left = bubbleLeft + "px";
        bubble.style.top = bubbleTop + "px";

        // If rendering a callout arrow, set it's position relative to the bubble.
        if (this.arrow != null) {
           this.arrow.style.display = "block";
           webui.@THEME@.common.setVisible(this.arrow, true);

           if (this.arrow == topLeftArrow) {
               this.arrow.style.top = -(bubble.offsetHeight - this.topConst) + "px";               
           }
           if (this.arrow == topRightArrow) {
               this.arrow.style.top = -(bubble.offsetHeight - this.topConst) + "px";               
           }
        }
    }
    return true;
}

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
 * @config {boolean} autoClose 
 * @config {Object} closeButton 
 * @config {Array} contents 
 * @config {int} duration 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {int} openDelay 
 * @config {String} title Provides a title for element.
 * @config {int} width 
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.bubble.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return this.inherited("setProps", arguments);
}

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
webui.@THEME@.widget.bubble.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }
        
    // Set title.
    if (props.title) {
        this.widget.addFragment(this.titleNode, props.title);
    }

    // hide/display close button
    if (props.closeButton != null) {
        var classNames = this.closeBtn.className.split(" ");
        var closeButtonClass = this.theme.getClassName("BUBBLE_CLOSEBTN");
        var noCloseButtonClass = this.theme.getClassName("BUBBLE_NOCLOSEBTN");

        if (props.closeButton == false) {
            webui.@THEME@.common.stripStyleClass(this.closeBtn, closeButtonClass);
            if (!webui.@THEME@.common.checkStyleClasses(classNames, noCloseButtonClass))
             webui.@THEME@.common.addStyleClass(this.closeBtn, noCloseButtonClass);
        } else {          
          if (!webui.@THEME@.common.checkStyleClasses(classNames, closeButtonClass))
             webui.@THEME@.common.addStyleClass(this.closeBtn, closeButtonClass);
        }
    }

    // Set width.
    if (props.width > 0) {                    
        this.domNode.style.width = props.width;        
    }

    // Set contents.
    if (props.contents) {
        // Remove child nodes.
        this.widget.removeChildNodes(this.childNode);

        for (var i = 0; i < props.contents.length; i++) {
            this.widget.addFragment(this.childNode, props.contents[i], "last");
        }
    }

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
