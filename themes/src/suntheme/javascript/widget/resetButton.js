// widget/resetButton.js
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
 * @name widget/resetButton.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the resetButton 
 * widget.
 * @example The following code is used to create a resetButton widget.
 * <p><code>
 * var widget = new webui.@THEME@.widget.resetButton(props, domNode);
 * </code></p>
 */
dojo.provide("webui.@THEME@.widget.resetButton");

dojo.require("webui.@THEME@.widget.button");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.resetButton
 * @inherits webui.@THEME@.widget.button
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.resetButton", webui.@THEME@.widget.button, {
    // Set defaults.
    widgetName: "resetButton"  // Required for theme properties.
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
webui.@THEME@.widget.resetButton.prototype.event =
        webui.@THEME@.widget.resetButton.event = {
    /**
     * This closure contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: webui.@THEME@.widget.button.event.refresh.beginTopic,

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: webui.@THEME@.widget.button.event.refresh.endTopic
    },

    /**
     * This closure contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: webui.@THEME@.widget.button.event.state.beginTopic,

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: webui.@THEME@.widget.button.event.state.endTopic
    }
}
