jmaki.namespace("@JMAKI_NS@.radioButton");

/*
 * jMaki wrapper for Woodstock radioButton widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data for a simple radioButton model:
 *               {label: {value: <label_text>, level: <n>},
 *                image: {src: <path_to_image>, border: 0},
 *                value: <radioButton_value>,
 *                checked: true,
 *                name: <radioButton_name>}
 *            The label or image is optional, but at least one
 *            must be specified. If checked is omitted, the radioButton
 *            is rendered unchecked.  A name must be specified.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the radioButton widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/radioButton".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/radioButton".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * setValues  To reset the "value" data in the widget.
 *
 * This widget publishes the following jMaki events:
 *
 * onSelect   To indicate the option that was selected in the widget.
 */
@JMAKI_NS@.radioButton.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/radioButton"];
    this._publish = "/@JS_NAME@/radioButton";
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
        var s1 = jmaki.subscribe(this._subscribe + "/setValues", 
            @JS_NS@.widget.common._hitch(this, "_valuesCallback"));
        this._subscriptions.push(s1);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.radioButton.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock radioButton.
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	// No data. Define minimalist radioButton.
	props = {};
    }
    if (wargs.value && wargs.value instanceof Object) {
	this._mapProperties(props, wargs.value);
    } else {
	// No data. Define simple radioButton without label.
	props.value = this._wid + "_rb";
	props.name = this._wid + "_rb";
    }
    props.id = this._wid;
    props.widgetType = "radioButton";

    // Hook the radioButton widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    props.onChange = @JS_NS@.widget.common._hitch(this, "_selectedCallback");

    // Create the Woodstock radioButton widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.radioButton.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.radioButton.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object of wrapper properties to be mapped
@JMAKI_NS@.radioButton.Widget.prototype._mapProperties = function(props, value) {

    // We expect to get a "label", "image", or both in format:
    //   label: {value: <text_label>, level: <n>}
    //   image: {src: <image_path>, border: <n>}
    // If an image, fix it up to be a compliant image object value.
    // If neither present, add a default label.
    // Make sure widget has a name property.
    // Other properties are passed through unchanged.
    for (name in value) {
	if (name == "image") {
	    var obj = {};
	    for (p1 in value.image) {
		obj[p1] = value[p1];
	    }			// End of for
	    obj.id = this._wid + "_image";
	    obj.widgetType = "image";
	    props.image = obj;
	} else if (name == "label") {
	    if (typeof value.label == "string") {
		var obj2 = {};
		obj2.value = value.label;
		obj2.level = 3;
		props.label = obj2;
	    } else {
		props.label = value.label;
	    }
	} else {
	    props[name] = value[name];
	}
    }				// End of for
    if (typeof props.label == "undefined" &&
	 typeof props.image == "undefined") {
	// Neither image or label supplied.  Fake up a label.
	props.label = {value: "RadioButton", level: 3};
    }
    if (typeof props.name == "undefined") {
	props.name = props.id + "_rb";
    }

}

// Callback function to handle Woodstock radioButton onSelect event.
// Publish jMaki onSelect event with payload:
//   {widgetId: <_wid>,
//    topic: {type: "onSelect", targetId: <value>, value: <checked>}
@JMAKI_NS@.radioButton.Widget.prototype._selectedCallback = function() {

    if (this._wid) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
            var props = widget.getProps();
	    var val = props.value;
	    var ckd = props.checked;
            // Format a jMaki onSelect event topic payload
            // and publish the jMaki event.
            var payload = {widgetId: this._wid, topic:
                    {type: "onSelect", targetId: val, value: ckd}};
	    var selectedTopic = this._publish + "/onSelect";
	    jmaki.publish(selectedTopic, payload);
	}
    }
};

// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: {<data_model>}}
// Update radioButton widget to replace data properties.
@JMAKI_NS@.radioButton.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value && payload.value instanceof Object) {
		var props = {};
		this._mapProperties(props, payload.value);
                widget.setProps(props);
            }
	}
    }
};
