jmaki.namespace("@JMAKI_NS@.imageHyperlink");

/*
 * jMaki wrapper for Woodstock ImageHyperlink widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data with the following properties:
 *            {formId: <form_identifier>,
 *             contents: [ <link_text> ],
 *             enabledImage: {src: <image_URL>, }}
 *            The imageHyperlink widget acts as a form submitting anchor.
 *            If formId is not specified, the form in which this wrapper
 *            is contained is submitted.  The contents text is optional.
 *            The "src" property in the enabledImage object may be replaced
 *            with the "icon" property to specify the Themed image keyword.
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
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	props = {};
    }
    if (wargs.value && typeof wargs.value == "object") {
	this._mapProperties(props, wargs.value);
    } else {
	// No data. Define simple imageHyperlink with no text.
	props.contents = [ ];
	var iid = this._wid + "_image";
	props.enabledImage = {id: iid, widgetType: "image", icon: "HREF_LINE"};
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "imageHyperlink";

    // Create the Woodstock imageHyperlink widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.imageHyperlink.Widget.prototype.destroy = function() {
    // Do nothing...
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.imageHyperlink.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object of wrapper properties to be mapped
@JMAKI_NS@.imageHyperlink.Widget.prototype._mapProperties = function(props, value) {

    // Copy the properties from value object to our props object.
    // For image properties, we must add id and widgetType.
    // Supply default icon image and empty contents if omitted.
    for (name in value) {
	if (name == "enabledImage") {
	    var obj = {};
	    for (p in value.enabledImage) {
		obj[p] = value.enabledImage[p];
	    }	// End of inner for
	    obj.id = this._wid + "_image_en";
	    obj.widgetType = "image";
	    props.enabledImage = obj;
	} else if (name == "disabledImage") {
	    var obj2 = {};
	    for (p in value.disabledImage) {
		obj2[p] = value.enabledImage[p];
	    }	// End of inner for
	    obj2.id = this._wid + "_image_dis";
	    obj2.widgetType = "image";
	    props.disabledImage = obj2;
	} else {
	    props[name] = value[name];
	}
    }	// End of outer for
    if (props.contents == "undefined" ) {
	props.contents = [ ];
    }
    if (! props.contents instanceof Array) {
	var arr = [];
	arr.push(props.content);
	props.contents = arr;
    }
    if (props.enabledImage == "undefined") {
	var nid = this._wid + "_image";
	props.enabledImage = {id: nid, widgetType: "image", icon: "HREF_LINK"};
    }

};
