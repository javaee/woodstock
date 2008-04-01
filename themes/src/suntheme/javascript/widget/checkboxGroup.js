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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget.checkboxGroup");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget._base.checkedGroupBase");

/**
 * @name webui.@THEME_JS@.widget.checkboxGroup
 * @extends webui.@THEME_JS@.widget._base.checkedGroupBase
 * @class This class contains functions for the checkboxGroup widget.
 * @constructor This function is used to construct a checkboxGroup widget.
 */
webui.@THEME_JS@._base.dojo.declare("webui.@THEME_JS@.widget.checkboxGroup",
        webui.@THEME_JS@.widget._base.checkedGroupBase, {
    // Set defaults.
    _widgetName: "checkboxGroup" // Required for theme properties.
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
webui.@THEME_JS@.widget.checkboxGroup.event =
        webui.@THEME_JS@.widget.checkboxGroup.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_checkboxGroup_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_checkboxGroup_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_checkboxGroup_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_checkboxGroup_event_state_end"
    }
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
webui.@THEME_JS@.widget.checkboxGroup.prototype._getClassName = function() {
    // Set default style.
    var className = (this.columns > 1)
        ? this._theme._getClassName("CBGRP_HORIZ", "")
        : this._theme._getClassName("CBGRP_VERT", "");

    return (this.className)
        ? className + " " + this.className
        : className;
};
