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

@JS_NS@._dojo.provide("@JS_NS@.widget.popupMenu");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.menuBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.submitBase");

/**
 * This function is used to construct a popupMenu widget.
 *
 * @name @JS_NS@.widget.popupMenu
 * @extends @JS_NS@.widget._base.menuBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.submitBase
 * @class This class contains functions for the popupMenu widget.
 * @constructor
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.popupMenu", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.submitBase,
        @JS_NS@.widget._base.menuBase ], {
    // Set defaults.
    _widgetType: "popupMenu" // Required for theme properties.
});

/**
 * Close the menu. Sets the visibility to false.
 *
 * @return {boolean} false to cancel the JavaScript event.
 */
@JS_NS@.widget.popupMenu.prototype.close = function() {
    if (this._common._isVisibleElement(this._domNode)) {
        if (@JS_NS@.widget.popupMenu.activeMenuId) {
            @JS_NS@.widget.popupMenu.activeMenuId = null;
        }
        if (this.target != null) {
            if (this.target.focus) {
                this.target.focus();
            }   
        }        
        return this.setProps({visible: false});
    }
    return false;    
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
@JS_NS@.widget.popupMenu.event =
        @JS_NS@.widget.popupMenu.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_popupMenu_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_popupMenu_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_popupMenu_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_popupMenu_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_popupMenu_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_popupMenu_event_submit_end"
    }
};

/**
 * Helper function to create callback to close menu.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.popupMenu.prototype._onCloseMenuCallBack = function(event) {
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
        return false;
    }
        
    // If the event occured on the menu, do NOT cancel.
    // Instead we let the event propagate to the MenuItem handler.
    // Cannot use 
    while (target != null) {
        if (target.className == "Menu@THEME_CSS@") {
            return false;
        }
        target = target.parentNode;
    }

    // The above will not catch events on IE which occur on menuitem seperators
    // or empty space between menuitems.
    var menuLeft = this._domNode.offsetLeft;        
    var menuTop = this._domNode.offsetTop;        
    var tmp;

    var menuRight = menuLeft + this._domNode.offsetWidth - this.rightShadow;
    var menuBottom = menuTop + this._domNode.offsetHeight - this.bottomShadow;

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
        return false;
    }
    if ((evt.type == "keydown" && evt.keyCode == 27) || evt.type == "click") {
        this.close();
    }
    return true;
};

/**
 * Use this function to make the menu visible. It takes an event parameter
 * as an argument.It calculates the position where the menu is to be displayed
 * at if one is not already provided by the developer.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.popupMenu.prototype.open = function(event) {
    var evt = this._widget._getEvent(event);
    var keyCode = this._widget._getKeyCode(evt);
    if (evt.type == "keydown" || evt.type == "keypress") {
        if (!(evt.shiftKey && keyCode == 121)) {
            return false;
        }
        if (@JS_NS@._base.browser._isIe5up()) {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        } else {
            evt.stopPropagation();
            evt.preventDefault();
        }        
    }
    // Only one menu can be open at a time. Hence, close the previous menu.
    var widget = this._widget.getWidget(@JS_NS@.widget.popupMenu.activeMenuId);
    if (widget) {
        widget.close();
    }
    @JS_NS@.widget.popupMenu.activeMenuId = this.id;

    // Note: Must test if event is null. Otherwise, pressing enter key while
    // link has focus generates an error on IE.
    if (evt) {
        evt.cancelBubble = true;
    }

    // If menu already rendered, do nothing.
    if (this._common._isVisibleElement(this._domNode)) {
        return false;
    }
        
    // Check if developer defined styles are set on the widget.
    if (this.style != null && this.style.length > 0) {
        // Mozilla browsers will tell us which styles are set.  If they're not
        // in the list, then the styles appear to be undefined.
        if (this._domNode.style.length != null) {
            for (var i = 0; i < this._domNode.style.length; i++) {
                var x = this._domNode.style[i];
                if (this._domNode.style[i] == "top") {
                    this.top = this._domNode.style.top;
                }                    
                if (this._domNode.style[i] == "left") {
                    this.left = this._domNode.style.left;
                }
            }
        } else {
            // For IE, simply query the style attributes.
            if (this._domNode.style.top != "") {
                this.top = this._domNode.style.top;
            }
            if (this._domNode.style.left != "") {
                this.left = this._domNode.style.left;
            }
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
        this._domNode.style.left = this.left;
        this._domNode.style.top = this.top;
    } else {
        // No positioning specified, so we calculate the optimal position to
        // guarantee menu is fully viewable.
        if (evt == null) {
            return false;
        }
        // Note: It appears that the D.Edwards packer tool cannot resolve the
        // target object below. (See the example-html app -- only debug mode 
        // works.) Thus, we must rewrite the code using "if" statments.
        // 
        // var target = (evt.target) 
        //    ? evt.target 
        //    : (evt.srcElement)
        //        ? evt.srcElement : null;

        // Get the absolute position of the target.
        var target = null;
        if (evt.target) {
            target = evt.target;
        } else if (evt.srcElement) {
            target = evt.srcElement;
        }

        var absPos = this._widget._getPosition(target);
        var targetLeft = absPos[0];
        var targetTop = absPos[1];

        // Fix: Menu not positioned b/c target is null -- see example-html app.
        // Possible issue with packer tool encoding?
        var targetHeight = (target) ? target.offsetHeight : 0;

        // Assume default horizontal position is to align the left edge of the 
        // menu with the left edge of the target.
        var menuLeft = targetLeft + this.rightShadow;

        // But can be overridden to align right edges.
        // Check if right edge of menu exceeds page boundary.
        var rightEdge = menuLeft + this._domNode.offsetWidth;
        var pageWidth = this._widget._getPageWidth();
        if (rightEdge > pageWidth) {
            // Shift menu left just enough to bring it into view.
            menuLeft -= (rightEdge - pageWidth);
        }
        
        // If left edge of menu crosses left page boundary, then
        // shift menu right just enough to bring it into view.
        if (menuLeft < 0) {
            menuLeft = 0;
        }

        // Assume default vertical position is to position menu below target.
        var menuTop = targetTop + targetHeight + this.bottomShadow;
        
        // Check if bottom edge of menu exceeds page boundary.
        var bottomEdge = menuTop + this._domNode.offsetHeight - this.bottomShadow;
        if (bottomEdge > this._widget._getPageHeight()) {
            // Shift menu to top of target.
            menuTop = targetTop - this._domNode.offsetHeight;

            // If top edge of menu cross top page boundary, then
            // reposition menu back to below target.
            // User will need to use scrollbars to position menu into view.
            if (menuTop <= 0) {
                menuTop = targetTop + targetHeight - this.bottomShadow;
            }
        }
        // Adjust to account for parent container.
        var parentPos = this._widget._getPosition(this._domNode.offsetParent);
        menuLeft -= parentPos[0];
        menuTop -= parentPos[1]; 
        // Set new menu position.
        this._domNode.style.left = menuLeft + "px";
        this._domNode.style.top = menuTop + "px";
    }

    // Keep track of the element that opened the popup menu.
    // When the menu is closed, the focus is set back on this element.
    if (evt.target) {
        this.target = evt.target;
    } else if (evt.srcElement) {
        this.target = evt.srcElement;
    }
    if (this.target && this.target.blur) {
        this.target.blur();
    }
    
    // Always set the focus on the first element of the menu.
    if (this._focusPosition > 0) {
        var menuNode = document.getElementById(this.menuId[this._focusPosition]); 
        if (menuNode) {
            menuNode.className = this._theme.getClassName("MENU_GROUP_CONTAINER");
        }
    }
    this._focusPosition = 0;
    menuNode = document.getElementById(this.menuId[0]);
    menuNode.className = menuNode.className + " " + 
        this._theme.getClassName("MENU_FOCUS");  
        
    if (menuNode.focus) {
        menuNode.focus();
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
@JS_NS@.widget.popupMenu.prototype._postCreate = function () {
    // Set public functions.

    /** @ignore */
    this._domNode.open = function(event) { return @JS_NS@.widget.common.getWidget(this.id).open(event); };
    /** @ignore */
    this._domNode.close = function() { return @JS_NS@.widget.common.getWidget(this.id).close(); };

    // Set events.
    this._dojo.connect(document, "onclick", this, "_onCloseMenuCallBack"); 
            
    // escape key should also close menu.
    this._dojo.connect(document, "onkeydown", this, "_onCloseMenuCallBack");  

    // Default widths of the drop shadow on each side of the menu.  These MUST 
    // be in pixel units and MUST match the absolute values of the left/top 
    // styles of the "Menu" style class in the CSS.
    this.rightShadow = parseFloat(this._theme.getMessage("Menu.rightShadow"));
    this.bottomShadow = parseFloat(this._theme.getMessage("Menu.bottomShadow"));
    this._shadowContainer.className = this._theme.getClassName("MENU_SHADOW_CONTAINER"); 

    return this._inherited("_postCreate", arguments);
};

/**
 * Override the "super class" processKeyPressEvent functionality and close the menu.
 *
 * @param (String) value The "value" of the selected option.  
 * @return {boolean} true The enter key press event completed successfully
 * @private
 */
@JS_NS@.widget.popupMenu.prototype._processEnterKeyPressEvent = function(value) {
    this._inherited("_processEnterKeyPressEvent", arguments);
    this.close();
    return true;
};

/**
 * Override the "super class" _processOnClickEvent functionality and close the menu.
 *
 * @param {String} value The selected value.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.popupMenu.prototype._processOnClickEvent = function(value) {
    this._inherited("_processOnClickEvent", arguments);
    this.close();
    return true;
};

/**
 * Traverse through the menu items. This overrides the superclass implementation
 * and handles escape/tab/page up/page down/home/end key press events.
 *
 * @param (String) keyCode The valye of the key which was pressed
 * @param (Event) event The key press event.
 * @param (String) nodeId The id of the menu item. 
 * @return {boolean} true Propagate the javascript event
 * @private
 */
@JS_NS@.widget.popupMenu.prototype._traverseMenu = function(keyCode, event, nodeId) {
    // Handle the escape key and tab key press
    if (keyCode == 27 || keyCode == 9) {
        var focusElem = document.getElementById(this.menuId[this._focusPosition]);
        focusElem.className = this._theme.getClassName("MENU_GROUP_CONTAINER");        
        this.close();
        return true;
    } else if(keyCode >= 33 && keyCode <= 36) {
        focusElem = document.getElementById(this.menuId[this._focusPosition]);        
        focusElem.className = this._theme.getClassName("MENU_GROUP_CONTAINER");
        
        // Handle the home and page Up keys. Focus is set on the first element.
        if (keyCode == 33 || keyCode == 36) {
            this._focusPosition = 0;
            focusElem = document.getElementById(this.menuId[this._focusPosition]);        
        }
        
        // Handle Page Down and End keys. Focus is set on the last element.
        if (keyCode == 34 || keyCode == 35) {
            this._focusPosition = this.menuId.length - 1;
            focusElem = document.getElementById(this.menuId[this._focusPosition]);        
        }
        if (focusElem.focus) {
            focusElem.focus();
        }                        
        focusElem.className = focusElem.className + " " +
            this._theme.getClassName("MENU_FOCUS"); 
        if (@JS_NS@._base.browser._isIe5up()) {
            window. event.cancelBubble = true;
            window.event.returnValue = false;
        } else {
            event.stopPropagation();
            event.preventDefault();
        }   
        return true;                 
    }    
    this._inherited("_traverseMenu", arguments);
    return true;
};

/**
 * Process submit event.
 *
 * @param {String} execute The string containing a comma separated list 
 * of client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.popupMenu.prototype.submit = function(execute) {
    // Publish an event for custom AJAX implementations to listen for.
    this._publish(@JS_NS@.widget.popupMenu.event.submit.beginTopic, [{
        id: this.id,
        execute: execute,
        value: this.getSelectedValue(),
        endTopic: @JS_NS@.widget.popupMenu.event.submit.endTopic
    }]);
    return true;
};
