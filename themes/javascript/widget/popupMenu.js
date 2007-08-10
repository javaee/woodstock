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

dojo.provide("webui.@THEME@.widget.popupMenu");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.menuBase");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.popupMenu = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Close the menu. Sets the visibility to false.
 */
webui.@THEME@.widget.popupMenu.close = function() {
    if (webui.@THEME@.common.isVisibleElement(this.domNode)) {
        this.domNode.setProps({visible: false});
    }
    return false;    
}

/**
 * Helper function to create callback to close menu.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.popupMenu.createCloseMenuCallBack = function(id) {
    if (id == null) {
        return null;
    }
    
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) { 
        var menu = document.getElementById(id);
        var widget = dojo.widget.byId(id);
        if (menu == null) {
            return false;
        }

        // Capture the click and see whether it falls within the boundary of the menu
        // if so do not close the menu.
        evt = (event) ? event : ((window.event) ? window.event : null);
        var target = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
        
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
        var menuLeft = menu.offsetLeft;        
        var menuTop = menu.offsetTop;        
        var tmp;

        var menuRight = menuLeft + menu.offsetWidth - widget.rightShadow;
        var menuBottom = menuTop + menu.offsetHeight - widget.bottomShadow;

        // Having problems with document.body.scrollTop/scrollLeft in firefox.
        // It always seems to return 0. But window.pageXOffset works fine.
        if (window.pageXOffset||window.pageYOffset) {
            var eventX = evt.clientX + window.pageXOffset;
            var eventY = evt.clientY + window.pageYOffset;
        } else if(document.documentElement.scrollLeft||document.documentElement.scrollTop){
             var eventX = evt.clientX + document.documentElement.scrollLeft;
             var eventY = evt.clientY + document.documentElement.scrollTop;
        } else {
             var eventX = evt.clientX + document.body.scrollLeft;
             var eventY = evt.clientY + document.body.scrollTop;
        }
        if ((eventX >= menuLeft) && (eventX <= menuRight) &&
           (eventY >= menuTop) && (eventY <= menuBottom)) {
           return;
        }
        if ((evt.type == "keydown" && evt.keyCode == 27)
                || evt.type == "click") {
            widget.close();
        }
        return true;
    };
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.popupMenu.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_popupMenu_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_popupMenu_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_popupMenu_event_state_begin",
        endTopic: "webui_@THEME@_widget_popupMenu_event_state_end"
    },

    /**
     * This closure is used to process submit events.
     */
    submit: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_popupMenu_event_submit_begin",
        endTopic: "webui_@THEME@_widget_popupMenu_event_submit_end",

        /**
         * Process submit event.
         *
         * @param execute Comma separated string containing a list of client ids 
         * against which the execute portion of the request processing lifecycle
         * must be run.
         */
        processEvent: function(execute) {
            // Include default AJAX implementation.
            this.ajaxify();

            // Publish an event for custom AJAX implementations to listen for.
            dojo.event.topic.publish(
                webui.@THEME@.widget.popupMenu.event.submit.beginTopic, {
                    id: this.id,
                    execute: execute,
                    value: this.getSelectedValue(),
                    endTopic: webui.@THEME@.widget.popupMenu.event.submit.endTopic
                });
            return true;
        }
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.popupMenu.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.popupMenu.superclass.fillInTemplate.call(this, props, frag);
    
    // Set public functions.
    this.domNode.open = function(event) { return dojo.widget.byId(this.id).open(event); }
    this.domNode.close = function() { return dojo.widget.byId(this.id).close(); }

    // Set events.s
    dojo.event.connect(document, "onclick", 
        webui.@THEME@.widget.popupMenu.createCloseMenuCallBack(this.id)); 
            
    // escape key should also close menu.
    dojo.event.connect(document, "onkeydown", 
        webui.@THEME@.widget.popupMenu.createCloseMenuCallBack(this.id));               

    return true;
}

/**
 * This function is used to initialize the widget.
 *
 * Note: This is called after the fillInTemplate() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 * @param parent The parent of this widget.
 */
webui.@THEME@.widget.popupMenu.initialize = function(props, frag, parent) {
    webui.@THEME@.widget.popupMenu.superclass.initialize.call(this, props, frag, parent);

    // Default widths of the drop shadow on each side of the menu.  These MUST 
    // be in pixel units and MUST match the absolute values of the left/top 
    // styles of the "Menu" style class in the CSS.
    this.rightShadow = parseFloat(this.theme.getMessage("Menu.rightShadow"));
    this.bottomShadow = parseFloat(this.theme.getMessage("Menu.bottomShadow"));
    this.shadowContainer.className = this.theme.getClassName("MENU_SHADOW_CONTAINER");    

    return true;
}

/**
 * Use this function to make the menu visible. It takes an event parameter
 * as an argument.It calculates the position where the menu is to be displayed
 * at if one is not already provided by the developer.
 */
webui.@THEME@.widget.popupMenu.open = function(evt) {
    // Only one menu can be open at a time. Hence, close the previous menu
    // that was open in the form.
    var form = document.getElementById(this.formId);
    if (form != null) {
        if (form.menu != null) {
            dojo.widget.byId(form.menu).close();
        }
        form.menu = this.id;
    }
    evt.cancelBubble = true;

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
            
    // Render the menu.  Must do this here, else target properties referenced
    // below will not be valid.
    this.domNode.setProps({visible:true});
      
    // If specific positioning specified, then simply use it.  This means
    // no provisions are made to guarantee the menu renders in the viewable area.
    if ((this.top != null) && (this.left != null)) {
        this.domNode.style.left = this.left;
        this.domNode.style.top = this.top;
    } else {
        // No positioning specified, so we calculate the optimal position to guarantee
        // menu is fully viewable.
        // Get the absolute position of the target.
        this.target = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);
        var absPos = webui.@THEME@.widget.common.getPosition(this.target);
        var targetLeft = absPos[0];
        var targetTop = absPos[1];

        // Assume default horizontal position is to align the left edge of the menu with the
        // left edge of the target.
        var menuLeft = targetLeft + this.rightShadow;

        // But can be overridden to align right edges.
        // Check if right edge of menu exceeds page boundary.
        var rightEdge = menuLeft + this.domNode.offsetWidth;
        var pageWidth = webui.@THEME@.widget.common.getPageWidth();
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
        if (menuLeft < 0)
            menuLeft = 0;

        // Assume default vertical position is to position menu below target.
        var menuTop = targetTop + this.target.offsetHeight + this.bottomShadow;
        
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
        if (bottomEdge > webui.@THEME@.widget.common.getPageHeight()) {
            // Shift menu to top of target.
            menuTop = targetTop - this.domNode.offsetHeight;

            // If top edge of menu cross top page boundary, then
            // reposition menu back to below target.
            // User will need to use scrollbars to position menu into view.
            if (menuTop <= 0) {
                menuTop = targetTop + this.target.offsetHeight - this.bottomShadow;
            }
        }

        // Set new menu position.
        this.domNode.style.left = menuLeft + "px";
        this.domNode.style.top = menuTop + "px";
        return true;
    }
}

/**
 * Override the "super class" processOnClickEvent functionality and close the menu.
 */
webui.@THEME@.widget.popupMenu.processOnClickEvent = function(value) {
    webui.@THEME@.widget.popupMenu.superclass.processOnClickEvent.call(this, value);
    this.close();
    return true;
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.popupMenu, webui.@THEME@.widget.menuBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.popupMenu, {
    // Set private functions.
    close: webui.@THEME@.widget.popupMenu.close,
    fillInTemplate: webui.@THEME@.widget.popupMenu.fillInTemplate,
    initialize: webui.@THEME@.widget.popupMenu.initialize,
    open: webui.@THEME@.widget.popupMenu.open,
    processOnClickEvent: webui.@THEME@.widget.popupMenu.processOnClickEvent,
    submit: webui.@THEME@.widget.popupMenu.event.submit.processEvent,

    // Set defaults.
    event: webui.@THEME@.widget.popupMenu.event,
    widgetType: "popupMenu"    
});
