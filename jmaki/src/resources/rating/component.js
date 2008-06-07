jmaki.namespace("@JMAKI_NS@.rating");

/*
 * jMaki wrapper for Woodstock rating widget.
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
 * /@JS_NAME@/rating/select
 *
 * This widget publishes the following jMaki events:
 * 
 * /@JS_NAME@/rating/onSelect - to allow subscriber retrieve newly entered grade
 * none
 */
@JMAKI_NS@.rating.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/rating"];
    this._publish = "/@JS_NAME@/rating";
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
        var s = jmaki.subscribe(this._subscribe + "/select", 
        @JS_NS@.widget.common._hitch(this, "_valuesCallback"));
        this._subscriptions.push(s);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.rating.Widget.prototype._create = function(wargs) {
    // Get the jMaki wrapper properties for a Woodstock rating.
    var props = {};
    if (wargs.args) {
	// Properties in the "args" property must be rating properties!
        @JS_NS@._base.proto._extend(props, wargs.args);
        
    } else {
	// No data. Define minimalist rating.
    }
    if (wargs.value) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    } else {
	// No data. Define single dummy options list.
    }

    // Add our widget id and type.
    props.id = this._wid;
     
    props.widgetType = "rating";

    // ============================================================
    // Create the Woodstock widget...


    // Create the Woodstock rating widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

    // connect events
    //  =currently disabled . pending issue 1264 fix
    
    //@JS_NS@.widget.common.subscribe(@JS_NS@.widget.rating.event.submit.beginTopic, this, "_selectCallback");

};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.rating.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};


// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.rating.Widget.prototype.postLoad = function() {
    // Do nothing...
}
// 
// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: [<data model>]}
// Update rating widget to replace options array.
@JMAKI_NS@.rating.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value ) {
                var grd = (typeof payload.value == "number") ?
                    payload.value : parseInt(payload.value);
                widget.setProps({grade: grd});
            }
	}
    }
};


// Callback function to handle Woodstock listbox onSelect event.
// Event payload contains:
//    Listbox widget identifier
// Publish jMaki onSelect event.
@JMAKI_NS@.rating.Widget.prototype._selectCallback = function() {
    if (this._wid) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
            var val = widget.getProps().grade;
            if (val) {
                // Format a jMaki event topic payload
                // and publish the jMaki event.
                var payload = {widgetId: this._wid, topic:
                    {type: "onSelect", targetId: this._wid, value: val}};
		var selectedTopic = this._publish + "/onSelect";
		jmaki.publish(selectedTopic, payload);
            }
	}
    }
};