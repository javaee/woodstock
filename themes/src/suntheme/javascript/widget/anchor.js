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

webui.@THEME_JS@.dojo.provide("webui.@THEME_JS@.widget.anchor");

webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.anchorBase");

/**
 * @name webui.@THEME_JS@.widget.anchor
 * @extends webui.@THEME_JS@.widget.anchorBase
 * @class This class contains functions for the anchor widget.
 * @constructor This function is used to construct an anchor widget.
 */
webui.@THEME_JS@.dojo.declare("webui.@THEME_JS@.widget.anchor", webui.@THEME_JS@.widget.anchorBase, {
    // Set defaults.
    widgetName: "anchor" // Required for theme properties.
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
webui.@THEME_JS@.widget.anchor.event =
        webui.@THEME_JS@.widget.anchor.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_anchor_event_refresh_begin",
        
        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_anchor_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_anchor_event_state_begin",
        
        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_anchor_event_state_end"
    }
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME_JS@.widget.anchor.prototype.getClassName = function() {
    // Set default style.
    var className = (this.href && this.disabled == false)
        ? this.widget.getClassName("ANCHOR","")
        : this.widget.getClassName("ANCHOR_DISABLED","");

    return (this.className)
        ? className + " " + this.className
        : className;
};

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.anchor.prototype.postCreate = function () {
    // Create callback function for onclick event.
    this.dojo.connect(this.domNode, "onclick", this, "onClickCallback");

    return this.inherited("postCreate", arguments);
};
