jmaki.namespace("@JMAKI_NS@.text");

/*
 * jMaki wrapper for Woodstock Text widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data mapped onto the text properties.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the text widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/text".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/text".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * TBD...
 *
 * This widget publishes the following jMaki events:
 *
 * TBD...
 */
@JMAKI_NS@.text.Widget = function(wargs) {
    // Turn on jMaki debugging...
    //jmaki.debug = true;
    //jmaki.log("Entering woodstock text Widget function...");

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/text"];
    this._publish = "/@JS_NAME@/text";
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
@JMAKI_NS@.text.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock text.
    // Value must contain an array of Options objects.
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	props = {};
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "text";

    // Create the Woodstock text widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.text.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.text.Widget.prototype.postLoad = function() {
    // Do nothing...
}
