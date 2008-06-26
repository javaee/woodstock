jmaki.namespace("@JMAKI_NS@.imageHyperlink");

/*
 * jMaki wrapper for Woodstock ImageHyperlink widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {formId: <form_identifier>,
 *             text: [ <link_text> ],
 *             enabledImage: {src: <image_URL>, }}
 *            The imageHyperlink widget acts as a form submitting anchor.
 *            If formId is not specified, the form in which this wrapper
 *            is contained is submitted.  The text property is optional.
 *            Additional Image widget properties may be specified for the
 *            enabledImage object.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the imageHyperlink widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/imageHyperlink".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/imageHyperlink".
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
@JMAKI_NS@.imageHyperlink.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/imageHyperlink"];
    this._publish = "/@JS_NAME@/imageHyperlink";
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
@JMAKI_NS@.imageHyperlink.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock imageHyperlink.
    // Value must contain an array of Options objects.
    var props = {};
    if (wargs.args != null) {
        @JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    }
    if (props.enabledImage == null) {
	props.enabledImage = {icon: "HREF_LINE"};
    }
    if (props.enabledImage != null) {
	if (props.enabledImage.id == null) {
	    props.enabledImage.id = this._wid + "_image_en";
	}
	props.enabledImage.widgetType = "image";
    }
    if (props.disabledImage != null) {
	if (props.disabledImage.id == null) {
	    props.disabledImage.id = this._wid + "_image_dis";
	}
	props.disabledImage.widgetType = "image";
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "imageHyperlink";

    // Create the Woodstock imageHyperlink widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.imageHyperlink.Widget.prototype.destroy = function() {
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.imageHyperlink.Widget.prototype.postLoad = function() {
    // Do nothing...
};
