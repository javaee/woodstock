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

jmaki.namespace("@JMAKI_NS@.button");

/*
 * jMaki wrapper for Woodstock Button widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data mapped onto the button properties.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the button widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/button".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/button".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * TBD...
 *
 * This widget publishes the following jMaki events:
 *
 * onClick   To indicate the underlying HTML input element was selected.
 */
@JMAKI_NS@.button.Widget = function(wargs) {
    // Turn on jMaki debugging...
    //jmaki.debug = true;
    //jmaki.log("Entering woodstock button Widget function...");

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/button"];
    this._publish = "/@JS_NAME@/button";
    this._subscriptions = [];
    this._wid = wargs.uuid;
    this._value = wargs.value;

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
@JMAKI_NS@.button.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock button.
    // Value must contain an array of Options objects.
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	props = {};
    }

    // A web app devleoper could return false in order to cancel the 
    // submit. Thus, we will handle this event via the onClick call back.
    if (props.onClick) {
        // Set private function scope on widget.
        this._onClick = (typeof props.onClick == 'string')
            ? new Function(props.onClick) : props.onClick;
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "button";

    // Hook the button widget onChange UI event so we can
    // publish the jMaki onClick topic.
    props.onClick = @JS_NS@.widget.common._hitch(this, "_onClickCallback");

    // Create the Woodstock button widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.button.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.button.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// Callback function to handle Woodstock button onClick event.
// Event payload contains:
//    button widget identifier
// Publish jMaki onClick event.
@JMAKI_NS@.button.Widget.prototype._onClickCallback = function() {
    // Note: A web app devleoper can return false in order to cancel the submit.
    var result = true;
    if (typeof this._onClick == "function") {
        result = this._onClick();
    }
    if (result != null && result == false) {
        return result;
    }

    // Process jMaki action.
    jmaki.processActions({
        action : (this._value) ? this._value.action : null,
        targetId : (this._value && this._value.id) ? this._value.id : null,
        topic : this._publish,
        type : "onClick",
        value : this._value,
        widgetId : this._wid
    });
};
