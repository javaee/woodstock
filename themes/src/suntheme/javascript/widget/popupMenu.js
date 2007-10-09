// widget/popupMenu.js
//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
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

/**
 * @name widget/popupMenu.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the popupMenu widget.
 * @example The following code is used to create a popupMenu widget.
 * <p><code>
 * var widget = new webui.@THEME@.widget.popupMenu(props, domNode);
 * </code></p>
 */
dojo.provide("webui.@THEME@.widget.popupMenu");

dojo.require("webui.@THEME@.common");
dojo.require("webui.@THEME@.widget.menuBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.popupMenu
 * @inherits webui.@THEME@.widget.menuBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.popupMenu", webui.@THEME@.widget.menuBase, {
    // Set defaults.
    widgetName: "popupMenu" // Required for theme properties.
});

/**
 * Close the menu. Sets the visibility to false.
 */
webui.@THEME@.widget.popupMenu.prototype.close = function() {
    if (webui.@THEME@.common.isVisibleElement(this.domNode)) {
        return this.setProps({visible: false});
    }
    return false;    
}

/**
 * This closure contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 *
 * @ignore
 */
webui.@THEME@.widget.popupMenu.prototype.event =
        webui.@THEME@.widget.popupMenu.event = {
    /**
     * This closure contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_popupMenu_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_popupMenu_event_refresh_end"
    },

    /**
     * This closure contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_popupMenu_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_popupMenu_event_state_end"
    },

    /**
     * This closure contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_popupMenu_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_popupMenu_event_submit_end"
    }
}

/**
 * Helper function to create callback to close menu.
 *
 * @param {Event} event The JavaScript event.
 */
webui.@THEME@.widget.popupMenu.prototype.onCloseMenuCallBack = function(event) {
    // Capture the click and see whether it falls within the boundary of the menu
    // if so do not close the menu.
    var evt = (event) 
        ? event : ((window.event) 
            ? window.event : null);

    var target = (evt.target) 
        ? evt.target 
        : ((evt.srcElement) 
            ? evt.srcElement : null);
        
    // If key pressed and it's NOT the escape key, do NOT cancel.
    if ((evt.type == "keydown") && (evt.keyCode != 27)) {
        return;
    }
        
    // If the event occured on the menu, do NOT cancel.
    // Instead we let the event propagate to the MenuItem handler.
    // Cannot use 
    while (target != null) {
        if (target.className == "Menu@THEME_CSS@") {
            return;
        }
        target = target.parentNode;
    }

    // The above will not catch events on IE which occur on menuitem seperators
    // or empty space between menuitems.
    var menuLeft = this.domNode.offsetLeft;        
    var menuTop = this.domNode.offsetTop;        
    var tmp;

    var menuRight = menuLeft + this.domNode.offsetWidth - this.rightShadow;
    var menuBottom = menuTop + this.domNode.offsetHeight - this.bottomShadow;

    // Having problems with document.body.scrollTop/scrollLeft in firefox.
    // It always seems to return 0. But window.pageXOffset works fine.
    if (window.pageXOffset || window.pageYOffset) {
        var eventX = evt.clientX + window.pageXOffset;
        var eventY = evt.clientY + window.pageYOffset;
    } else if (document.documentElement.scrollLeft ||
            document.documentElement.scrollTop){
        var eventX = evt.clientX + document.documentElement.scrollLeft;
        var eventY = evt.clientY + document.documentElement.scrollTop;
    } else {
        var eventX = evt.clientX + document.body.scrollLeft;
        var eventY = evt.clientY + document.body.scrollTop;
    }
    if ((eventX >= menuLeft) && (eventX <= menuRight) && (eventY >= menuTop) && 
            (eventY <= menuBottom)) {
        return;
    }
    if ((evt.type == "keydown" && evt.keyCode == 27) || evt.type == "click") {
        this.close();
    }
    return true;
}

/**
 * Use this function to make the menu visible. It takes an event parameter
 * as an argument.It calculates the position where the menu is to be displayed
 * at if one is not already provided by the developer.
 *
 * @param {Event} event The JavaScript event.
 */
webui.@THEME@.widget.popupMenu.prototype.open = function(event) {
    // Only one menu can be open at a time. Hence, close the previous menu.
    var widget = dijit.byId(webui.@THEME@.widget.popupMenu.activeMenuId);
    if (widget) {
        widget.close();
    }
    webui.@THEME@.widget.popupMenu.activeMenuId = this.id;

    var evt = (event) 
        ? event : ((window.event) 
            ? window.event : null);

    // Note: Must test if event is null. Otherwise, pressing enter key while
    // link has focus generates an error on IE.
    if (evt) {
        evt.cancelBubble = true;
    }

    // If menu already rendered, do nothing.
    if (webui.@THEME@.common.isVisibleElement(this.domNode)) {
        return false;
    }
        
    // Check if developer defined styles are set on the widget.
    if (this.style != null) {
        // Mozilla browsers will tell us which styles are set.  If they're not
        // in the list, then the styles appear to be undefined.
        if (this.domNode.style.length != null) {
            for (var i = 0; i < this.domNode.style.length; i++) {
                    var x = this.domNode.style[i];
                if (this.domNode.style[i] == "top")
                    this.top = this.domNode.style.top;
                if (this.domNode.style[i] == "left")
                    this.left = this.domNode.style.left;
            }
        } else {
            // For IE, simply query the style attributes.
            if (this.style.top != "")
                this.top = this.domNode.style.top;
            if (this.domNode.style.left != "")
                this.left = this.domNode.style.left;
        }    
    }

    // Fix: Setting the menu visible here causes flashing. The menu is shown in
    // an old location until moved to the new location in the page.

    // Show the menu. Must do this here, else target properties referenced
    // below will not be valid.
    this.setProps({visible: true});
      
    // If specific positioning specified, then simply use it.  This means
    // no provisions are made to guarantee the menu renders in the viewable area.
    if ((this.top != null) && (this.left != null)) {
        this.domNode.style.left = this.left;
        this.domNode.style.top = this.top;
    } else {
        if (evt == null) {
            return false;
        }
        // No positioning specified, so we calculate the optimal position to guarantee
        // menu is fully viewable.
        // Get the absolute position of the target.
        var target = (evt.target) 
            ? evt.target 
            : ((evt.srcElement) 
                ? evt.srcElement : null);
        var absPos = this.widget.getPosition(target);
        var targetLeft = absPos[0];
        var targetTop = absPos[1];

        // Assume default horizontal position is to align the left edge of the menu with the
        // left edge of the target.
        var menuLeft = targetLeft + this.rightShadow;

        // But can be overridden to align right edges.
        // Check if right edge of menu exceeds page boundary.
        var rightEdge = menuLeft + this.domNode.offsetWidth;
        var pageWidth = this.widget.getPageWidth();
        if (rightEdge > pageWidth) {
            // Shift menu left just enough to bring it into view.
            menuLeft -= (rightEdge - pageWidth);
        }
        
        // Shift menu to account for horizontal scrolling.
        if ((window.pageXOffset != null) && (window.pageXOffset > 0)) {
            menuLeft += window.pageXOffset;
        }
        if ((document.body.scrollLeft != null) && (document.body.scrollLeft > 0)) {
            menuLeft += document.body.scrollLeft;
        }
        if ((document.documentElement.scrollLeft != null) && 
            (document.documentElement.scrollLeft > 0)) {
            menuLeft += document.documentElement.scrollLeft;        
        }

        // If left edge of menu crosses left page boundary, then
        // shift menu right just enough to bring it into view.
        if (menuLeft < 0) {
            menuLeft = 0;
        }

        // Assume default vertical position is to position menu below target.
        var menuTop = targetTop + target.offsetHeight + this.bottomShadow;
        
        // Shift menu to account for vertical scrolling.
        if ((window.pageYOffset != null) && (window.pageYOffset > 0)) {
            menuTop += window.pageYOffset;
        }
        if ((document.body.scrollTop != null) && (document.body.scrollTop > 0)) {
            menuTop += document.body.scrollTop;
        }
        if ((document.documentElement.scrollTop != null) &&
        (document.documentElement.scrollTop > 0)) {
            menuTop += document.documentElement.scrollTop;
        }

        // Check if bottom edge of menu exceeds page boundary.
        var bottomEdge = menuTop + this.domNode.offsetHeight - this.bottomShadow;
        if (bottomEdge > this.widget.getPageHeight()) {
            // Shift menu to top of target.
            menuTop = targetTop - this.domNode.offsetHeight;

            // If top edge of menu cross top page boundary, then
            // reposition menu back to below target.
            // User will need to use scrollbars to position menu into view.
            if (menuTop <= 0) {
                menuTop = targetTop + target.offsetHeight - this.bottomShadow;
            }
        }

        // Set new menu position.
        this.domNode.style.left = menuLeft + "px";
        this.domNode.style.top = menuTop + "px";
    }
    return true;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 */
webui.@THEME@.widget.popupMenu.prototype.postCreate = function () {
    // Set public functions.
    this.domNode.open = function(event) { return dijit.byId(this.id).open(event); }
    this.domNode.close = function() { return dijit.byId(this.id).close(); }

    // Set events.s
    dojo.connect(document, "onclick", this, "onCloseMenuCallBack"); 
            
    // escape key should also close menu.
    dojo.connect(document, "onkeydown", this, "onCloseMenuCallBack");  

    // Default widths of the drop shadow on each side of the menu.  These MUST 
    // be in pixel units and MUST match the absolute values of the left/top 
    // styles of the "Menu" style class in the CSS.
    this.rightShadow = parseFloat(this.theme.getMessage("Menu.rightShadow"));
    this.bottomShadow = parseFloat(this.theme.getMessage("Menu.bottomShadow"));
    this.shadowContainer.className = this.theme.getClassName("MENU_SHADOW_CONTAINER"); 

    return this.inherited("postCreate", arguments);
}

/**
 * Override the "super class" processOnClickEvent functionality and close the menu.
 *
 * @param {String} value The selected value.
 */
webui.@THEME@.widget.popupMenu.prototype.processOnClickEvent = function(value) {
    this.inherited("processOnClickEvent", arguments);
    this.close();
    return true;
}

/**
 * Process submit event.
 *
 * @param {String} execute The string containing a comma separated list 
 * of client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 */
webui.@THEME@.widget.popupMenu.prototype.submit = function(execute) {
    // Include default AJAX implementation.
    this.ajaxify();

    // Publish an event for custom AJAX implementations to listen for.
    dojo.publish(webui.@THEME@.widget.popupMenu.event.submit.beginTopic, [{
        id: this.id,
        execute: execute,
        value: this.getSelectedValue(),
        endTopic: webui.@THEME@.widget.popupMenu.event.submit.endTopic
    }]);
    return true;
}
