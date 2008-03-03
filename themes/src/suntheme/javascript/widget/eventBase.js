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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.eventBase");

webui.@THEME@.dojo.require("webui.@THEME@.config");
webui.@THEME@.dojo.require("webui.@THEME@.dijit._Widget"); 
webui.@THEME@.dojo.require("webui.@THEME@.dijit._Templated");

/**
 * @name webui.@THEME@.widget.eventBase
 * @extends webui.@THEME@.dijit._Widget, webui.@THEME@.dijit._Templated
 * @class This class contains functions for widgets that extend eventBase.
 * @static
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.eventBase", [
    webui.@THEME@.dijit._Widget, webui.@THEME@.dijit._Templated]);

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME@.widget.eventBase.event =
        webui.@THEME@.widget.eventBase.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: null,

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: null
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: null,

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: null
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: null,

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: null
    }
};

/**
 * Initialize public events and functions.
 * <p>
 * Note: If this.event.<eventName> is not null, a public function shall be added
 * to the DOM node. To avoid name clashes, do not create private functions with
 * the names; refresh, stateChanged, or submit.
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.eventBase.prototype.initEvents = function () {
    if (this.event == null) {
        return false;
    }

    // Since the anchor id and name must be the same on IE, we cannot obtain the
    // widget using the DOM node ID via the public functions below. Therefore, 
    // we need to set the widget id via closure magic.
    var _id = this.id;

    // Flag to add subscribe function.
    var subscribe = false;

    // Add event topics.
    this.domNode.event = this.event;

    // Refresh.
    if (this.event.refresh != null) {
        // Set public function.
        this.domNode.refresh = function(execute) {
            return webui.@THEME@.dijit.byId(_id).refresh(execute);
        };
        subscribe = true;
    } else {
        this.domNode.event.refresh = null; // clean.
    }

    // Submit.
    if (this.event.submit != null) {
        // Set public function.
        this.domNode.submit = function(execute) {
            return webui.@THEME@.dijit.byId(_id).submit(execute);    
        };
        subscribe = true;
    } else {
        this.domNode.event.submit = null; // clean.
    }

    // State.
    if (this.event.state == null) {
        // Remove prototyped function.
        this.stateChanged = null;
        this.domNode.event.state = null; // clean.
    }
    
    // Subscribe.
    if (subscribe == true) {
        this.domNode.subscribe = function(topic, obj, func) {
            return webui.@THEME@.dijit.byId(_id).subscribe(topic, obj, func);
        };
    }
    return true;
};

/**
 * Publish an event topic.
 *
 * @param {String} topic The event topic to publish.
 * @param {Object} props Key-Value pairs of properties. This will be applied
 * to each topic subscriber.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.eventBase.prototype.publish = function(topic, props) {
    // Obtain the Ajax module associated with this widget.
    var config = webui.@THEME@.config;
    if (config.ajax.module) {
        webui.@THEME@.dojo.require(config.ajax.module + "." + this.widgetName);
    }
    return webui.@THEME@.widget.eventBase.prototype._publish(topic, props);
};

/**
 * Publish an event topic.
 * <p>
 * Note: In order to obtain Ajax modules dynamically, this function shall be 
 * overridden by a custom AJAX implementation.
 * </p>
 * @param {String} topic The event topic to publish.
 * @param {Object} props Key-Value pairs of properties. This will be applied
 * to each topic subscriber.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.eventBase.prototype._publish = function(topic, props) {
    // Publish an event for custom AJAX implementations to listen for.
    webui.@THEME@.dojo.publish(topic, props);
    return true;
};

/**
 * Subscribe to an event topic.
 *
 * @param {String} topic The event topic to subscribe to.
 * @param {Object} obj The object in which a function will be invoked, or
 * null for default scope.
 * @param {String|Function} func The name of a function in context, or a 
 * function reference to invoke when topic is published. 
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.eventBase.prototype.subscribe = function(topic, obj, func) {
    webui.@THEME@.dojo.subscribe(topic, obj, func);
    return true;
};

/**
 * Process refresh event.
 * <p>
 * Note: If this.event.refresh is not null, an event is published for custom
 * Ajax implementations to listen for. If event topics are not implemented for 
 * this widget, the function returns and a message is output to the console.
 * </p>
 * @param {String} execute The string containing a comma separated list 
 * of client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.eventBase.prototype.refresh = function(execute) {
    if (this.event.refresh == null) {
        console.debug("Error: Refresh event topics not implemented for " + 
            this.widgetName); // See Firebug console.
        return false;
    }

    // Publish an event for custom AJAX implementations to listen for.
    this.publish(this.event.refresh.beginTopic, [{
        id: this.id,
        execute: execute,
        endTopic: this.event.refresh.endTopic
    }]);
    return true;
};

/**
 * Process state event.
 * <p>
 * Note: If this.event.state is not null, an event is published for custom
 * Ajax implementations to listen for. If event topics are not implemented for 
 * this widget, the function returns and a message is output to the console.
 * </p>
 * @param {Object} props Key-Value pairs of widget properties to update.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.eventBase.prototype.stateChanged = function(props) {
    if (this.event.state == null) {
        console.debug("Error: State event topics not implemented for " + 
            this.widgetName); // See Firebug console.
        return false;
    }

    // Publish an event for custom AJAX implementations to listen for.
    this.publish(this.event.state.beginTopic, [{
        id: this.id,
        endTopic: this.event.state.endTopic,
        props: props
    }]);
    return true;
};

/**
 * Process submit event.
 * <p>
 * Note: If this.event.submit is not null, an event is published for custom
 * Ajax implementations to listen for. If event topics are not implemented for 
 * this widget, the function returns and a message is output to the console.
 * </p>
 * @param {String} execute Comma separated string containing a list of 
 * client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.eventBase.prototype.submit = function(execute) {
    if (this.event.submit == null) {
        console.debug("Error: Submit event topics not implemented for " + 
            this.widgetName); // See Firebug console.
        return false;
    }

    // Publish an event for custom AJAX implementations to listen for.
    this.publish(this.event.submit.beginTopic, [{
        id: this.id,
        execute: execute,
        endTopic: this.event.submit.endTopic
    }]);
    return true;
};
