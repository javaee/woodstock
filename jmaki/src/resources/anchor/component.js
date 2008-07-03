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

jmaki.namespace("@JMAKI_NS@.anchor");

/*
 * jMaki wrapper for Woodstock Anchor widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {name: <anchor_name>,
 *             href: <link_url>,
 *             contents: [<anchor_text>]}
 *            If the name property is omitted, the widget identifier
 *            is used.  If the href property exists, the anchor acts
 *            as a link; if its omitted, the anchor acts as an anchor
 *            (a location to link to on the page identified by the
 *            name property).
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the anchor widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/anchor".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/anchor".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 *            No specific events are subscribed to.
 *
 * This widget publishes the following jMaki events:
 *
 *            No specific events are published.
 */
@JMAKI_NS@.anchor.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/anchor"];
    this._publish = "/@JS_NAME@/anchor";
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
@JMAKI_NS@.anchor.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock anchor.
    var props = {};
    if (wargs.args != null) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
	@JS_NS@._base.proto._extend(props, wargs.value);
    }
    if (props.name == null) {
	props.name = this._wid;
    }
    if (props.contents == null) {
	props.contents = [ "Anchor: " + this._wid ];
    }
    // If contents not an array, make it one.
    if (! props.contents instanceof Array) {
	var arr = [];
	arr.push(props.contents);
	props.contents = arr;
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "anchor";

    // Create the Woodstock anchor widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.anchor.Widget.prototype.destroy = function() {
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.anchor.Widget.prototype.postLoad = function() {
    // Do nothing...
};
