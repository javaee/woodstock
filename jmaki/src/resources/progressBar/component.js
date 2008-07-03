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

jmaki.namespace("@JMAKI_NS@.progressBar");

/*
 * jMaki wrapper for Woodstock ProgressBar widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {
 *             type: <progressBar_type>,
 *             refreshRate: <milliseconds>,
 *             topText: <task_description>,
 *             bottomText: <initial_task_status>,
 *             processControl: "bottom|right"
 *            }
 *            The processControl property is used to add the
 *            three control buttons (Pause, Resume, Cancel) to
 *            a control area to the bottom or right of the rendered
 *            progress animation bar, and is optional.  The widget
 *            publishes a "progress" event at intervals specified
 *            by the refreshRate.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the progressBar widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/progressBar".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/progressBar".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events which can be used
 * in lieu of specifying the processControl option.
 *
 * control    Process the control value to start, stop, pause, resume, or
 *            cancel the progress.  The payload contains the following:
 *              {value: "<command>"} 
 *            where <command> is one of cancel|pause|resume|start|stop.
 *
 * This widget publishes the following jMaki events:
 *
 *            No specific events are published.
 */
@JMAKI_NS@.progressBar.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/progressBar"];
    this._publish = "/@JS_NAME@/progressBar";
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
@JMAKI_NS@.progressBar.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock progressBar.
    var props = {};
    if (wargs.args != null) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
	@JS_NS@._base.proto._extend(props, wargs.value);
    }
    if (props.type == null) {
	props.type = "INDETERMINATE";
	props.refreshRate = 3000;
    }
    // Added-value: define control buttons.
    if (props.progressControl != null && props.type != "BUSY") {
	// Add control buttons...
	if (props.progressControl == "bottom") {
	    props.progressControlBottom = this._addControlButtons();
	} else if (props.progressControl == "right") {
	    props.progressControlRight = this._addControlButtons();
	}
    }
    // If log specified, make sure its a widget.
    if (props.log != null) {
	if (props.log.id == null) {
	    props.log.id = this._wid + "_log";
	}
	props.log.widgetType = "textArea";
	// Work around a progressBar problem to make log render.
	if (props.overlayAnimation == null) {
	    props.overlayAnimation = false;
	}
    }

    // Subscribe to jMaki events
    for (var i = 0; i < this._subscribe.length; i++) {
        var s1 = jmaki.subscribe(this._subscribe[i] + "/control",
            @JS_NS@.widget.common._hitch(this, "_controlCallback"));
        this._subscriptions.push(s1);
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "progressBar";

    // Create the Woodstock progressBar widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.progressBar.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
        } // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.progressBar.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Create the three control buttons and return in a JS array.
// Give them well-known identifiers based on widget id; that is,
// <wid>_pause, <wid>_resume, <wid>_cancel.
@JMAKI_NS@.progressBar.Widget.prototype._addControlButtons = function() {

    var arr = [];

    // The Pause button...
    var p1 = {};
    p1.id = this._wid + "_pause";
    p1.widgetType = "button";
    p1.value = "Pause";
    p1.primary = false;
    p1.onClick = "document.getElementById('" + this._wid + "').pause();return false;";
    arr.push(p1);

    // The Resume button...
    var p2 = {};
    p2.id = this._wid + "_resume";
    p2.widgetType = "button";
    p2.value = "Resume";
    p2.primary = false;
    p2.onClick = "document.getElementById('" + this._wid + "').resume();return false;";
    arr.push(p2);

    // The Cancel button...
    var p3 = {};
    p3.id = this._wid + "_cancel";
    p3.widgetType = "button";
    p3.value = "Cancel";
    p3.primary = false;
    p3.onClick = "document.getElementById('" + this._wid + "').cancel();return false;";
    arr.push(p3);

    return arr;

};

// Event callback handler for progressBar control event.
// The event payload: {value: "cancel|pause|resume|start|stop"}
// Results in the widget publishing a Woodstock "progress" event
// with a hidden input field (id is <wid>_controlType) whose value
// property is the taskState setting.
@JMAKI_NS@.progressBar.Widget.prototype._controlCallback = function(payload)
{
    if (typeof payload.value == "string") {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value == "cancel") {
		widget.cancel();
	    } else if (payload.value == "pause") {
		widget.pause();
	    } else if (payload.value == "resume") {
		widget.resume();
	    } else if (payload.value == "start") {
		widget.startProgress();
	    } else if (payload.value == "stop") {
		widget.stop();
	    }
	}
    }

};
