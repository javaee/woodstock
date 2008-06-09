jmaki.namespace("@JMAKI_NS@.anchor");

/*
 * jMaki wrapper for Woodstock Anchor widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {name: <anchor_name>,
 *             contents: [<anchor_text>]}
 *            If the name property is omitted, the widget identifier
 *            is used.
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
	// No data. Define simple anchor.
	props.name = this._wid;
	props.contents = [ "Anchor: " + this._wid ];
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "anchor";

    // Create the Woodstock anchor widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.anchor.Widget.prototype.destroy = function() {
    // Do nothing...
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.anchor.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object of wrapper properties to be mapped
@JMAKI_NS@.anchor.Widget.prototype._mapProperties = function(props, value) {

    // Copy all value properties into our props object.
    for (name in value) {
	props[name] = value[name];
    }	// End of for
    // If no name property, add one set to widget identifier.
    if (props.name == "undefined") {
	props.name = this._wid;
    }
    if (props.name == null || props.name == "") {
	props.name = this._wid;
    }
    // If no contents property, add a dummy text contents.
    if (props.contents == "undefined") {
	props.contents = [ "Anchor: " + this._wid ];
    }
    // If contents not an array, make it one.
    if (! props.contents instanceof Array) {
	var arr = [];
	arr.push(props.contents);
	props.contents = arr;
    }

};
	    

