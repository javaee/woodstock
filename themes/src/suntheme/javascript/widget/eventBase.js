// widget/eventBase.js
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
 * @name widget/eventBase.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the eventBase object.
 */
dojo.provide("webui.@THEME@.widget.eventBase");

dojo.require("dijit._Widget"); 
dojo.require("dijit._Templated"); 

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.eventBase
 * @inherits dijit._Widget, dijit._Templated
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.eventBase", [dijit._Widget, dijit._Templated]);

/**
 * This function is used to include default Ajax functionality. Before the given
 * module is included in the page, a test is performed to ensure that the 
 * default Ajax implementation is being used.
 */
webui.@THEME@.widget.eventBase.prototype.ajaxify = function() {
    // To do: Get default module from the theme.
    if (new Boolean(webui.@THEME@.bootstrap.ajaxify).valueOf() == true) {
        dojo.require("webui.@THEME@.widget.jsfx." + this.widgetName);
        return true;
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
webui.@THEME@.widget.eventBase.prototype.event = 
        webui.@THEME@.widget.eventBase.event = {
    /**
     * This closure contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: null,

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: null
    },

    /**
     * This closure contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: null,

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: null
    },

    /**
     * This closure contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: null,

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: null
    }
}

/**
 * Initialize functions.
 * <p>
 * Note: If this.event.<eventName> is not null, a public function shall be added
 * to the DOM node, if applicable. If the event topic is null, the function
 * prototyped by this object is removed. To avoid name clashes, do not create
 * private functions with the names; refresh, stateChanged, or submit.
 * </p>
 */
webui.@THEME@.widget.eventBase.prototype.initFunctions = function () {
    if (this.event == null) {
        return false;
    }

    // Since the anchor id and name must be the same on IE, we cannot obtain the
    // widget using the DOM node ID via the public functions below. Therefore, 
    // we need to set the widget id via closure magic.
    var _id = this.id;

    // Refresh.
    if (this.event.refresh != null) {
        // Set public function.
        this.domNode.refresh = function(execute) {
            return dijit.byId(_id).refresh(execute);
        };
    } else {
        // Remove prototyped function.
        this.refresh = null;
    }

    // Submit.
    if (this.event.submit != null) {
        // Set public function.
        this.domNode.submit = function(execute) {
            return dijit.byId(_id).submit(execute);    
        };
    } else {
        // Remove prototyped function.
        this.submit = null;
    }

    // State.
    if (this.event.state != null) {
        // Remove prototyped function.
        this.stateChanged = null;
    }
    return true;
}

/**
 * Process refresh event.
 *
 * @param {String} execute The string containing a comma separated list 
 * of client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 */
webui.@THEME@.widget.eventBase.prototype.refresh = function(execute) {
    // Include default AJAX implementation.
    this.ajaxify();

    // Publish an event for custom AJAX implementations to listen for.
    dojo.publish(this.event.refresh.beginTopic, [{
        id: this.id,
        execute: execute,
        endTopic: this.event.refresh.endTopic
    }]);
    return true;
}

/**
 * Process state event.
 *
 * @param {Object} props Key-Value pairs of widget properties to update.
 */
webui.@THEME@.widget.eventBase.prototype.stateChanged = function(props) {
    // Include default AJAX implementation.
    this.ajaxify();

    // Publish an event for custom AJAX implementations to listen for.
    dojo.publish(this.event.state.beginTopic, [{
        id: this.id,
        endTopic: this.event.state.endTopic,
        props: props
    }]);
    return true;
}

/**
 * Process submit event.
 *
 * @param {String} execute Comma separated string containing a list of 
 * client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 */
webui.@THEME@.widget.eventBase.prototype.submit = function(execute) {
    // Include default AJAX implementation.
    this.ajaxify();

    // Publish an event for custom AJAX implementations to listen for.
    dojo.publish(this.event.submit.beginTopic, [{
        id: this.id,
        execute: execute,
        endTopic: this.event.submit.endTopic
    }]);
    return true;
}
