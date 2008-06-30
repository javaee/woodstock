jmaki.namespace("@JMAKI_NS@.calendarField");

/*
 * jMaki wrapper for Woodstock calendarField widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     required (true/false) 
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the widget.
 * publish:   none
 * subscribe: none
 * id:        User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * /@JS_NAME@/calendarField/setValues
 *
 * This widget publishes the following jMaki events:
 * 
 * /@JS_NAME@/calendarField/onBlur - to allow subscriber retrieve newly entered date
 * none
 */
@JMAKI_NS@.calendarField.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/calendarField"];
    this._publish = "/@JS_NAME@/calendarField";
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
        var s = jmaki.subscribe(this._subscribe + "/setValues", 
        @JS_NS@.widget.common._hitch(this, "_valuesCallback"));
        this._subscriptions.push(s);
    }

    // Create Woodstock widget.
    this._create(wargs);

};

// Create Woodstock widget.
@JMAKI_NS@.calendarField.Widget.prototype._create = function(wargs) {

    // Get the jMaki wrapper properties for a Woodstock calendarField.
    var props = {};
    if (wargs.args != null) {
        @JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    }
    // If a calendar subwidget is included, make sure its a valid widget.
    if (props.calendar != null) {
	if (props.calendar.id == null) {
	    props.calendar.id = this._wid + "_cal";
	}
	props.calendar.widgetType = "calendar";
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "calendarField";

    // If application sets onBlur, stack function for handling later.
    if (props.onBlur) {
        this._onBlur = (typeof props.onBlur == 'string')
            ? new Function(props.onBlur) : props.onBlur;
    }

    // Hook the onBlur event.
    props.onBlur = @JS_NS@.widget.common._hitch(this, "_blurCallBack");

    // Create the Woodstock calendarField widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.calendarField.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.calendarField.Widget.prototype.postLoad = function() {
    // Do nothing...
}
 
// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: { <widget properties> }}
// Update calendarField widget to replace options array.
@JMAKI_NS@.calendarField.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value != null) {
                widget.setProps({valule: payload.value});
            }
	}
    }
};

// Callback function to handle Woodstock calendarField onSelect event.
// Event payload contains:
//    {widgetId: <wid>, type: 'onSelect', targetId: <wid>, value: <value>}
// Publish jMaki onSelect event.
@JMAKI_NS@.calendarField.Widget.prototype._blurCallBack = function() {

    var result = true;
    if (typeof this._onBlur == "function") {
	result = this._onBlur();
    }
    if (result != null && result == false) {
	return result;
    }

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	var val = widget.getProps().value;
	if (val) {
	    jmaki.processActions({
		action: "onSelect",
		targetId: this._wid,
		topic: this._publish + "/onSelect",
		type: "onSelect",
		value: val,
		widgetId: this._wid
	    });
	}
    }
};

