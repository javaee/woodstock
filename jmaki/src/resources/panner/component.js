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

jmaki.namespace("@JMAKI_NS@.panner");

/*
 * jMaki wrapper for Woodstock panner widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     No data values are required for this widget.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the panner widget.
 *            The canvasId and viewportId properties should be set
 *            to the HTML elements for the canvas to be panned.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/panner".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/panner".
 * id:        User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 *            No events are subscribed to by this wrapper.
 *
 * This widget publishes the following jMaki events:
 *
 *            No events are published by this wrapper.
 */
@JMAKI_NS@.panner.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/panner"];
    this._publish = "/@JS_NAME@/panner";
    this._subscriptions = [];
    this._wid = wargs.uuid;
    if (wargs.id) {
	this._wid = wargs.id;
    }
    if (wargs.publish) {
	// User supplied a specific topic to publish to.
	this._publish = wargs.publish;
    }
    if (wargs.subscribe) {
	// User supplied one or more specific topics to subscribe to.
        if (typeof wargs.subscribe == "string") {
            this._subscribe = [];
            this._subscribe.push(wargs.subscribe);
        } else {
            this._subscribe = wargs.subscribe;
        }
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.panner.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock panner.
    var props = {};
    if (wargs.args != null) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
	@JS_NS@._base.proto._extend(props, wargs.value);
    }

    props.id = this._wid;
    props.widgetType = "panner";

    // Hook the panner widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    props.onChange = @JS_NS@.widget.common._hitch(this, "_selectedCallback");

    // Create the Woodstock panner widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

    // Set event callback handlers so we can publish jMaki events.
    // @JS_NS@.widget.common.subscribe(@JS_NS@.widget.panner.event.pan.start,
    //    null, @JS_NS@.widget.common._hitch(this, "_startCallback"));
    // @JS_NS@.widget.common.subscribe(@JS_NS@.widget.panner.event.pan.stop,
    //    null, @JS_NS@.widget.common._hitch(this, "_failureCallback"));

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.panner.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.panner.Widget.prototype.postLoad = function() {
    // Do nothing...
};
