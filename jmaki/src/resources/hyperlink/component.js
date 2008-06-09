jmaki.namespace("@JMAKI_NS@.hyperlink");

/*
 * jMaki wrapper for Woodstock Hyperlink widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {href: <hyperlink_URL>,
 *             text: <hyperlink_text>}
 *            If the href property is omitted, the hyperlink will
 *            submit the page; that is, it acts as a hyperlink button.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the hyperlink widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/hyperlink".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/hyperlink".
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
@JMAKI_NS@.hyperlink.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/hyperlink"];
    this._publish = "/@JS_NAME@/hyperlink";
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
@JMAKI_NS@.hyperlink.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock hyperlink.
    // Value must contain an array of Options objects.
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	props = {};
    }
    if (wargs.value && typeof wargs.value == "object") {
	this._mapProperties(props, wargs.value);
    } else {
	// No data. Define simple hyperlink.
	props.name = this._wid;
	props.contents = [ "Hyperlink: " + this._wid ];
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "hyperlink";

    // Create the Woodstock hyperlink widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.hyperlink.Widget.prototype.destroy = function() {
    // Do nothing...
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.hyperlink.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object of wrapper properties to be mapped
@JMAKI_NS@.hyperlink.Widget.prototype._mapProperties = function(props, value) {

    for (name in value) {
	props[name] = value[name];
    }	// End of for
    if (props.contents == "undefined" ) {
	props.contents = [ "Hyperlink: " + this._wid ];
    }
    if (! props.contents instanceof Array) {
	var arr = [];
	arr.push(props.content);
	props.contents = arr;
    }

};
	    

