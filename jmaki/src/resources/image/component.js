jmaki.namespace("@JMAKI_NS@.image");

/*
 * jMaki wrapper for Woodstock Image widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     No initial data value is required.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the image widget.
 *            The image widget can obtain its image from either a
 *            source file or from the theme images via a Theme keyword.
 *            Source image: {src: <image_url>}
 *            Icon image:   {icon: <theme_keyword>}
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
    var props = {};
    if (wargs.args != null) {
        @JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    }
    // If no icon or src specified, use dummy image.
    if (props.icon == null && props.src == null) {
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

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.image.Widget.prototype.destroy = function() {
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.image.Widget.prototype.postLoad = function() {
    // Do nothing...
};
