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

jmaki.namespace("@JMAKI_NS@.rating");

/*
 * jMaki wrapper for Woodstock rating widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     required (true/false) 
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the widget.
 * publish:   none
 * subscribe: none
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * select     Set the rating value from the event payload:
 *            {value: <rating_number>}
 *
 * This widget publishes the following jMaki events:
 * 
 * onSelect   Provides the rating just set by the user with payload:
 *            {widgetId: <wid>, type: 'onSelect', targetId: <wid>,
 *             value: <rating_number>}
 */
@JMAKI_NS@.rating.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/rating"];
    this._publish = "/@JS_NAME@/rating";
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
	var s = jmaki.subscribe(this._subscribe[i] + "/select", 
	@JS_NS@.widget.common._hitch(this, "_valuesCallback"));
	this._subscriptions.push(s);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.rating.Widget.prototype._create = function(wargs) {
    // Get the jMaki wrapper properties for a Woodstock rating.
    var props = {};
    if (wargs.args != null) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
	@JS_NS@._base.proto._extend(props, wargs.value);
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "rating";

    // Create the Woodstock rating widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

    // connect events
    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.rating.event.grade.selectedTopic, this, "_selectCallback");

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.rating.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
	for (var i = 0; i < this._subscriptions.length; i++) {
	    jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};


// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.rating.Widget.prototype.postLoad = function() {
    // Do nothing...
}
 
// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: <rating value>}
// Update rating widget to replace options array.
@JMAKI_NS@.rating.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    if (payload.value) {
		var grd = (typeof payload.value == "number") ?
		    payload.value : parseInt(payload.value);
		widget.setProps({grade: grd});
	    }
	}
    }
};

// Callback function to handle Woodstock rating grade selected event.
// Publish jMaki onSelect event with payload:
// {widgetId: <wid>, type: 'onSelect', targetId: <wid>, value: <rating>}
@JMAKI_NS@.rating.Widget.prototype._selectCallback = function() {
    if (this._wid) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    var val = widget.getProps().grade;
	    if (val) {
		jmaki.processActions({
		    action: "onSelect",
		    targetId: this._wid,
		    topic: this._publish + "/onSelect",
		    type: "onSelect",
		    value: val,
		    widgetId: this._wid
		});
	    }
	}
    }
};
