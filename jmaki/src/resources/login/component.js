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

jmaki.namespace("@JMAKI_NS@.login");

/*
 * jMaki wrapper for Woodstock login widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data for a simple login authentication sequence:
 *            {
 *             loginState: "INIT|CONTINUE|SUCCESS|FAILURE",
 *             userData: [
 *                           [{<text_widget>},{input_widget}], ...
 *                       ],
 *             keys: [
 *                      ["id", "value"], ...
 *                   ],
 *             alert: {type: "<type>", summary: <msg>, detail: <msg>},
 *             redirectURL: "<url>"
 *            }
 *            The userData array contains rows of two widget definitions
 *            representing display elements in the current step of the
 *            login sequence.  The first widget is a text element lateling
 *            the row, the second widget is an input element for entering
 *            authentication data. The keys array are identifiers and values
 *            for all input elements in the login step, and is submitted
 *            in the widget's "authenticate" event.  The "id" string should
 *            match the "id" property of the corresponding userData input
 *            widget and the "value" string is its value.  The event handler
 *            validates the input values and returns a new loginState.
 *            If the state is CONTINUE, a new userData and keys array
 *            is returned for displaying the next step.  If the state is
 *            FAILURE, the same userData and keys are returned, along with
 *            an alert object containing the error messages.  If the state
 *            is SUCCESS, the login terminates and redirects to the given
 *            url (if the optional redirectURL property is set).
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the login widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/login".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/login".
 * id:        User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This wrapper subscribes to the following jMaki events:
 *
 * start      To start the login sequence and cause the INIT state
 *            authentication event to be published by the widget:
 *            "woodstock.widget.login.event.authentication.beginTopic"
 *            The application event handler for this event should
 *            generate the userData and keys array, set the loginState
 *            to "CONTINUE", and call the login widget's setProps function.
 *
 * This wrapper publishes the following jMaki events:
 *
 * authentcate  Publishes the result of the authentication:
 *            {widgetId: <wid>, topic: /woodstock/login/authentice,
 *            targetId: <wid>, value: "success|failure"}
 */
@JMAKI_NS@.login.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/login"];
    this._publish = "/@JS_NAME@/login";
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
@JMAKI_NS@.login.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock login.
    var props = {};
    if (wargs.args != null) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
	@JS_NS@._base.proto._extend(props, wargs.value);
    }
    if (props.loginState == null) {
	props.loginState = "INIT";
    }

    // Subscribe to jMaki events
    for (var i = 0; i < this._subscribe.length; i++) {
        var s1 = jmaki.subscribe(this._subscribe[i] + "/start", 
            @JS_NS@.widget.common._hitch(this, "_startCallback"));
        this._subscriptions.push(s1);
    }

    props.id = this._wid;
    props.widgetType = "login";

    // Create the Woodstock login widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

    // Set event callback handlers so we can publish jMaki events.
    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.login.event.result.successTopic,
        null, @JS_NS@.widget.common._hitch(this, "_successCallback"));
    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.login.event.result.failureTopic,
        null, @JS_NS@.widget.common._hitch(this, "_failureCallback"));

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.login.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.login.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Callback function to handle Woodstock login success event.
// Publish jMaki authenticate action event with payload:
//   {widgetId: <_wid>, topic: "/woodstock/login/authenticate",
//    type: "authenticate", targetId: <_wid>, value: "success"}
@JMAKI_NS@.login.Widget.prototype._successCallback = function() {

    jmaki.processActions({
	action: "authenticate",
	targetId: this._wid,
	topic: this._publish + "/authenticate",
	type: "authenticate",
	value: "success",
	widgetId: this._wid
    });

};

// Callback function to handle Woodstock login failure event.
// Publish jMaki authenticate action event with payload:
//   {widgetId: <_wid>, topic: "/woodstock/login/authenticate",
//    type: "authenticate", targetId: <_wid>, value: "failure"}
@JMAKI_NS@.login.Widget.prototype._failureCallback = function() {

    jmaki.processActions({
	action: "authenticate",
	targetId: this._wid,
	topic: this._publish + "/authenticate",
	type: "authenticate",
	value: "failure",
	widgetId: this._wid
    });

};

// Callback function to handle jMaki login start event.
// Reset the login state to "INIT" and start the login sequence.
@JMAKI_NS@.login.Widget.prototype._startCallback = function(payload) {

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	// XXXX - Clear keys array since there is no initial data.
	var props = {loginState: "INIT", userData: [], keys: []};
        widget.setProps(props);
	widget.authenticate();
    }

};
