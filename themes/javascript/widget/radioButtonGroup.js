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

dojo.provide("webui.@THEME@.widget.radioButtonGroup");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.checkboxGroup");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.radioButtonGroup = function() {    
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.radioButtonGroup.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_radioButtonGroup_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_radioButtonGroup_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_radioButtonGroup_event_state_begin",
        endTopic: "webui_@THEME@_widget_radioButtonGroup_event_state_end"
    }
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.radioButtonGroup.getClassName = function() {
    // Set default style.
    var className = (this.columns > 1)
        ? this.widget.getClassName("RBGRP_HORIZ", "")
        : this.widget.getClassName("RBGRP_VERT", "");

    return (this.className)
        ? className + " " + this.className
        : className;
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.radioButtonGroup, webui.@THEME@.widget.checkboxGroup);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.radioButtonGroup, {
    // Set private functions.    
    getClassName: webui.@THEME@.widget.radioButtonGroup.getClassName,

    // Set defaults.
    event: webui.@THEME@.widget.radioButtonGroup.event,
    widgetType: "radioButtonGroup"
});
