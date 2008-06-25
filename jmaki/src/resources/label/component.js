jmaki.namespace("@JMAKI_NS@.label");

/*
 * jMaki wrapper for Woodstock Label widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {value: <label_text>,
 *             level: <1 | 2 | 3>,
 *             htmlFor: <identifier>}
 *            The level property controls the size and boldness
 *            of the label's text.  The htmlFor property identifies
 *            the labelled DOM element this label is for.  Only the
 *            value property is required.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the label widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/label".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/label".
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
@JMAKI_NS@.label.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/label"];
    this._publish = "/@JS_NAME@/label";
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
@JMAKI_NS@.label.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock label.
    // Value must contain an array of Options objects.
    var props = {};
    if (wargs.args != null) {
	this._mapProperties(props, wargs.args);
    }
    if (wargs.value != null) {
	this._mapProperties(props, wargs.value);
    }
    if (props.value == null) {
	props.value = "Label";
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "label";

    // Create the Woodstock label widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.label.Widget.prototype.destroy = function() {
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.label.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object of wrapper properties to be mapped
@JMAKI_NS@.label.Widget.prototype._mapProperties = function(props, value) {

    // Copy all value properties into our props object.
    // For image properties, we must add id and widgetType.
    @JS_NS@._base.proto._extend(props, value);
    if (props.errorImage != null) {
        props.errorImage.id = this._wid + "_image_err";
        props.errorImage.widgetType = "image";
    }
    if (props.requiredImage != null) {
        props.requiredImage.id = this._wid + "_image_dis";
        props.requiredImage.widgetType = "image";
    }

};
	    

