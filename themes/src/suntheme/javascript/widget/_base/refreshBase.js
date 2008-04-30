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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.refreshBase");

@JS_NS@._dojo.require("@JS_NS@._base.proto");
@JS_NS@._dojo.require("@JS_NS@.widget.common");

/** 
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.refreshBase
 * @class This class contains functions for widgets that extend refreshBase.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.refreshBase");

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget._base.refreshBase.event =
        @JS_NS@.widget._base.refreshBase.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} execute Comma separated list of IDs to be processed server
         * side along with this widget.
         * </li><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         */
        beginTopic: undefined,

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         */
        endTopic: undefined
    }
};

/**
 * Initialize public functions.
 * <p>
 * Note: If this.event.refresh.beginTopic and this.event.refresh.endTopic are
 * null, a public function shall be added to the DOM node.
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.refreshBase.prototype._initRefreshFunc = function () {
    if (this.event == null || this.event.refresh == null
            || this.event.refresh.beginTopic == null
            || this.event.refresh.endTopic == null) {
        return false;
    }
    // Since the anchor id and name must be the same on IE, we cannot obtain the
    // widget using the DOM node ID via the public functions below. Therefore, 
    // we need to set the widget id via closure magic.
    var _id = this.id;

    // Set public functions.
    /** @ignore */
    this._domNode.refresh = function(execute) {
        return @JS_NS@.widget.common.getWidget(_id).refresh(execute);
    };
    return true;
};

/**
 * Process refresh event.
 * <p>
 * Note: If this.event.refresh.beginTopic and this.event.refresh.endTopic are 
 * not null, an event is published for custom Ajax implementations to listen 
 * for. If event topics are not implemented for this widget, the function 
 * returns and a message is output to the console.
 * </p>
 * @param {String} execute The string containing a comma separated list 
 * of client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.refreshBase.prototype.refresh = function(execute) {
    if (this.event == null || this.event.refresh == null
            || this.event.refresh.beginTopic == null
            || this.event.refresh.endTopic == null) {
        console.debug("Error: Refresh event topics not implemented for " + 
            this._widgetType); // See Firebug console.
        return false;
    }

    // Publish an event for custom AJAX implementations to listen for.
    this._publish(this.event.refresh.beginTopic, [{
        id: this.id,
        execute: execute,
        endTopic: this.event.refresh.endTopic
    }]);
    return true;
};
