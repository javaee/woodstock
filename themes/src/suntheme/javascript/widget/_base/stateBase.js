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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.stateBase");

@JS_NS@._dojo.require("@JS_NS@._base.proto");
@JS_NS@._dojo.require("@JS_NS@.widget.common");

/** 
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.stateBase
 * @class This class contains functions for widgets that extend stateBase.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.stateBase");

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget._base.stateBase.event =
        @JS_NS@.widget._base.stateBase.prototype.event = {
    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * {Object} props Key-Value pairs of widget properties being updated.
         * </li></ul>
         */
        beginTopic: undefined,

        /**
         * State event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         */
        endTopic: undefined
    }
};

/**
 * Process state event.
 * <p>
 * Note: If this.event.state.beginTopic and this.event.state.endTopic are 
 * not null, an event is published for custom Ajax implementations to listen 
 * for. If event topics are not implemented for this widget, the function 
 * returns and a message is output to the console.
 * </p>
 * @param {Object} props Key-Value pairs of widget properties to update.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.stateBase.prototype._stateChanged = function(props) {
    if (this.event == null || this.event.state == null
            || this.event.state.beginTopic == null
            || this.event.state.endTopic == null) {
        console.debug("Error: State event topics not implemented for " + 
            this._widgetType); // See Firebug console.
        return false;
    }

    // Publish an event for custom AJAX implementations to listen for.
    this._publish(this.event.state.beginTopic, [{
        id: this.id,
        endTopic: this.event.state.endTopic,
        props: props
    }]);
    return true;
};
