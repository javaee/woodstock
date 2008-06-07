jmaki.namespace("@JMAKI_NS@.passwordField");

/*
 * jMaki wrapper for Woodstock passwordField widget.
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
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * /@JS_NAME@/passwordField/setValues
 *
 * This widget publishes the following jMaki events:
 *
 * none
 */
@JMAKI_NS@.passwordField.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/passwordField"];
    this._publish = "/@JS_NAME@/passwordField";
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
@JMAKI_NS@.passwordField.Widget.prototype._create = function(wargs) {
    // Get the jMaki wrapper properties for a Woodstock passwordField.
    var props = {};
    if (wargs.args) {
	// Properties in the "args" property must be passwordField properties!
        @JS_NS@._base.proto._extend(props, wargs.args);
        
    } else {
	// No data. Define minimalist passwordField.
    }
    if (wargs.value) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    } else {
	// No data. Define single dummy options list.
    }

    // Add our widget id and type.
    props.id = this._wid;

    props.widgetType = "passwordField";

    // ============================================================
    // Create the Woodstock widget...

    // connect events
    // 
    //props.onChange = @JS_NS@.widget.common._hitch(this, "_functionName");

    // Create the Woodstock passwordField widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.passwordField.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};



// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.passwordField.Widget.prototype.postLoad = function() {
    // Do nothing...
}
// 
// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: [<data model>]}
// Update passwordField widget to replace options array.
@JMAKI_NS@.passwordField.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value ) {
                widget.setProps({value: payload.value});
            }
	}
    }
};
