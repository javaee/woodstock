// widget/anchor.js
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
 * @name widget/anchor.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the anchor widget.
 * @example The following code is used to create a anchor widget.
 * <p><code>
 * var widget = new webui.@THEME@.widget.anchor(props, domNode);
 * </code></p>
 */
dojo.provide("webui.@THEME@.widget.anchor");

dojo.require("webui.@THEME@.widget.anchorBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.anchor
 * @inherits webui.@THEME@.widget.anchorBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.anchor", webui.@THEME@.widget.anchorBase, {
    // Set defaults.
    widgetName: "anchor" // Required for theme properties.
});

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
webui.@THEME@.widget.anchor.prototype.event =
        webui.@THEME@.widget.anchor.event = {
    /**
     * This closure contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_anchor_event_refresh_begin",
        
        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_anchor_event_refresh_end"
    },

    /**
     * This closure contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_anchor_event_state_begin",
        
        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_anchor_event_state_end"
    }
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.anchor.prototype.getClassName = function() {
    // Set default style.
    var className = (this.href && this.disabled == false)
        ? this.widget.getClassName("ANCHOR","")
        : this.widget.getClassName("ANCHOR_DISABLED","");

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't yet exist. 
 * </p>
 */
webui.@THEME@.widget.anchor.prototype.postCreate = function () {
    // Create callback function for onclick event.
    dojo.connect(this.domNode, "onclick", this, "onClickCallback");

    return this.inherited("postCreate", arguments);
}
