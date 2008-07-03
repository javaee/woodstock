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

jmaki.namespace("@JMAKI_NS@.bubble");

/*
 * jMaki wrapper for Woodstock bubble widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data for a simple bubble:
 *            {title: <title text>, contents: [<contents text>]}
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/bubble".
 * subscribe: Topic to subscribe to for events; if not specified, the
 *            default topic is "/woodstock/bubble".
 * id:        User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * open       Opens the bubble with UI event as its payload.
 * close      Closes the bubble with UI event as its payload.
 *
 * This widget publishes the following jMaki events:
 * 
 *            No events are published.
 */
@JMAKI_NS@.bubble.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/bubble"];
    this._publish = "/woodstock/bubble";
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

    // Subscribe to jMaki events
    for (var i = 0; i < this._subscribe.length; i++) {
        var s1 = jmaki.subscribe(this._subscribe[i] + "/open", 
        @JS_NS@.widget.common._hitch(this, "_openCallback"));
        this._subscriptions.push(s1);
        var s2 = jmaki.subscribe(this._subscribe[i] + "/close", 
        @JS_NS@.widget.common._hitch(this, "_closeCallback"));
        this._subscriptions.push(s2);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.bubble.Widget.prototype._create = function(wargs) {

    // Get the jMaki wrapper properties for a Woodstock bubble.
    var props = {};
    if (wargs.args != null) {
        @JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "bubble";

    // Create the Woodstock bubble widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

    // connect events
    //  =currently disabled . pending issue 1264 fix
    //@JS_NS@.widget.common.subscribe(@JS_NS@.widget.bubble.event.submit.beginTopic, this, "_selectCallback");

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.bubble.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.bubble.Widget.prototype.postLoad = function() {
    // Do nothing...
}
 
// Callback function to handle jMaki open event.
// Event payload contains: {targetId: <bubble_id>, event: {<window_event>}}
@JMAKI_NS@.bubble.Widget.prototype._openCallback = function(payload) {
        this._display("open", payload);
};

// Callback function to handle jMaki close event.
// Event payload contains: {targetId: <bubble_id>, event: {<window_event>}}
@JMAKI_NS@.bubble.Widget.prototype._closeCallback = function(payload) {
        this._display("close", payload);
};

// display or hide the widget
@JMAKI_NS@.bubble.Widget.prototype._display = function(command, payload) {
      //we expect the window event in the payload
      if (!payload || !payload.event) {
          return false;
      }
      var event = payload.event;
      var widget = @JS_NS@.widget.common.getWidget(this._wid);
      if (widget) {
          if (command == "open") {
              widget.open(event);
          } else {
              widget.close();
          }
    }
};
