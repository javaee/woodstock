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
 *             htmlFor: <element_identifier>}
 *            The level property controls the size and boldness
 *            of the label's text.  The htmlFor property identifies
 *            the labelled element this label is for.
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
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	props = {};
    }
    if (wargs.value && typeof wargs.value == "object") {
	this._mapProperties(props, wargs.value);
    } else {
	// No data. Define simple label.
	props.value = "Label";
	props.level = 2;
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "label";

    // Create the Woodstock label widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.label.Widget.prototype.destroy = function() {
    // Do nothing...
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
    for (name in value) {
        if (name == "errorImage") {
            var obj = {};
            for (p in value.errorImage) {
                obj[p] = value.errorImage[p];
            }   // End of inner for
            obj.id = this._wid + "_image_err";
            obj.widgetType = "image";
            props.errorImage = obj;
        } else if (name == "requiredImage") {
            var obj2 = {};
            for (p in value.requiredImage) {
                obj2[p] = value.requiredImage[p];
            }   // End of inner for
            obj2.id = this._wid + "_image_dis";
            obj2.widgetType = "image";
            props.requiredImage = obj2;
        } else {
            props[name] = value[name];
        }
    }	// End of for

};
	    

