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
 *             detail: <detailmsg>,
 *             visible: <true|false>}
 *            The visible property is optional and if omitted,
 *            the widget is rendered hidden.  Sending the alert
 *            command event to the widget makes it visible.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the alert widget.
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
 *            {type: <type>, summary: <summary_msg>, detail: <detail>msg}
 *            Properties are optional; if omitted, the current property
 *            values in the widget will be used.
 *
 * This widget publishes the following jMaki events:
 *
 *            No specific events are published.
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
        var s1 = jmaki.subscribe(this._subscribe + "/alert",
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
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	props = {};
    }
    if (wargs.value && typeof wargs.value == "object") {
	for (p in wargs.value) {
	    props[p] = wargs.value[p];
	}			// End of for
    } else {
	// No data. Define simple dummy hidden alert.
	props.type = "information";
	props.summary = "Summary...";
	props.detail = "Details...";
	props.visible = false;
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "alert";

    // Create the Woodstock alert widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.alert.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.alert.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// Callback function to handle jMaki alert topic.
// Event payload contains:
//    {value: {type: <type>, summary: <summary>, detail: <detail>}}
// Update alert widget and make it visible.
@JMAKI_NS@.alert.Widget.prototype._alertCallback = function(payload) {

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	if (typeof payload.value == "object") {
	    for (p in payload.value) {
		props[p] = payload.value[p];
	    }	// End of for
        }
	var props = {visible: true};
	widget.setProps(props);
    }
};
