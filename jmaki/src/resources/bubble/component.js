jmaki.namespace("@JMAKI_NS@.bubble");

/*
 * jMaki wrapper for Woodstock bubble widget.
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
 * /woodstock/bubble/<id>/open - opens the bubble
 * /woodstock/bubble/<id>/close - closes the bubble
 *
 * This widget publishes the following jMaki events:
 * 
 * none
 */
@JMAKI_NS@.bubble.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/bubble"];
    this._publish = "/woodstock/bubble";
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
        var s1 = jmaki.subscribe(this._subscribe + "/*", 
        @JS_NS@.widget.common._hitch(this, "_eventCallback"));
        this._subscriptions.push(s1);

    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.bubble.Widget.prototype._create = function(wargs) {
    // Get the jMaki wrapper properties for a Woodstock bubble.
    var props = {};
    if (wargs.args) {
	// Properties in the "args" property must be bubble properties!
        @JS_NS@._base.proto._extend(props, wargs.args);
        
    } else {
	// No data. Define minimalist bubble.
    }
    if (wargs.value) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    } else {
	// No data. Define single dummy options list.
    }

    // Add our widget id and type.
    props.id = this._wid;
     
    props.widgetType = "bubble";

    // ============================================================
    // Create the Woodstock widget...


    // Create the Woodstock bubble widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

    // connect events
    //  =currently disabled . pending issue 1264 fix
    
    //@JS_NS@.widget.common.subscribe(@JS_NS@.widget.bubble.event.submit.beginTopic, this, "_selectCallback");

};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.bubble.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};


// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.bubble.Widget.prototype.postLoad = function() {
    // Do nothing...
}
// 
// Callback function to handle all of jMaki bubble topics.
// Event payload contains:
//    {event: browser event}
@JMAKI_NS@.bubble.Widget.prototype._eventCallback = function(payload) {
    //get the event type
    var topic = payload.topic;
    var subtopic = topic.substr(topic.lastIndexOf("/")+1);
    
    if (subtopic == "open" || subtopic =="close") {
        this._display(subtopic, payload);
    }
    
};
// display /hide the widget

@JMAKI_NS@.bubble.Widget.prototype._display = function(subtopic, payload) {
      //we expect only payload that contains event
      if (!payload || !payload.event) {
          return false;
      }
      var event = payload.event;
      
      var widget = @JS_NS@.widget.common.getWidget(this._wid);
      if (widget) {
          if (subtopic == "open") {
              widget.open(event);
          } else {
              widget.close();
          }

    }
};

