jmaki.namespace("@JMAKI_NS@.image");

/*
 * jMaki wrapper for Woodstock Image widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            Source image: {src: <image_url>}
 *            Icon image:   {icon: <theme_keyword>}
 *            The image widget can obtain its image from either a
 *            source file or from the theme images via a Theme keyword.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the image widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/image".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/image".
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
@JMAKI_NS@.image.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/image"];
    this._publish = "/@JS_NAME@/image";
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
@JMAKI_NS@.image.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock image.
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
	// No data. Define simple image.
	props.icon = "DOT";
	props.title = "No image specified"
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "image";

    // Create the Woodstock image widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.image.Widget.prototype.destroy = function() {
    // Do nothing...
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.image.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object of wrapper properties to be mapped
@JMAKI_NS@.image.Widget.prototype._mapProperties = function(props, value) {

    // Copy all value properties into our props object.
    for (name in value) {
        props[name] = value[name];
    }	// End of for

};
