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

jmaki.namespace("@JMAKI_NS@.alert");

/*
 * jMaki wrapper for Woodstock Alert widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {type: <type>,
 *             summary: <summary_msg>,
 *             detail: <detailmsg>}
 *            The detail property is optional.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the alert widget.
 *            The "visible" property can be set to make the widget
 *            render as hidden.  Sending the alert command event to
 *            the widget makes it visible (and can reset the type and
 *            messages).
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/alert".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/alert".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * alert      To force the alert to render its messages.  The payload
 *            may contain the type, summary, and detail messages.
 *            If the alert is hidden, it is made visible.  The format
 *            of the payload is ...
 *            {type: '<type>', summary: '<sum_msg>', detail: '<det_msg>'}
 *            Properties are optional; if omitted, the current property
 *            values in the widget will be used.
 *
 * This widget publishes the following jMaki events:
 *
 *            No events are published.
 */
@JMAKI_NS@.alert.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/alert"];
    this._publish = "/@JS_NAME@/alert";
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
        var s1 = jmaki.subscribe(this._subscribe[i] + "/alert",
            @JS_NS@.widget.common._hitch(this, "_alertCallback"));
        this._subscriptions.push(s1);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.alert.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock alert.
    // Value must contain an array of Options objects.
    var props = {};
    if (wargs.args != null) {
        @JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    }
    if (props.type == null) {
	props.type = "information";
	if (props.summary == null) {
	    props.summary = "Summary...";
	}
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "alert";

    // Create the Woodstock alert widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.alert.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.alert.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Callback function to handle jMaki alert topic.
// Event payload contains:
//    {value: {type: <type>, summary: <summary>, detail: <detail>}}
// Update alert widget and make it visible.
@JMAKI_NS@.alert.Widget.prototype._alertCallback = function(payload) {

    if (payload && payload.value != null) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
            var props = {visible: true};
            @JS_NS@._base.proto._extend(props, payload.value);
            widget.setProps(props);
	}
    }
};
