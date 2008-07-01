jmaki.namespace("@JMAKI_NS@.modalWindow");

/*
 * jMaki wrapper for Woodstock modalWindow widget.
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
 * /woodstock/modalWindow/open
 * /woodstock/modalWindow/close
 * /woodstock/modalWindow/setContent
 * /woodstock/modalWindow/setInclude
 *
 * This widget publishes the following jMaki events:
 * 
 * /woodstock/modalWindow/onClose - to notify subscriber of closing window
 * 
 */
@JMAKI_NS@.modalWindow.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/modalWindow"];
    this._publish = "/woodstock/modalWindow/onClose";
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
        jmaki.log('subscribing to ' + this._subscribe[i]);
        var s1 = jmaki.subscribe(this._subscribe[i] , 
        @JS_NS@.widget.common._hitch(this, "_eventCallback"));
        this._subscriptions.push(s1);

    }
    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.modalWindow.Widget.prototype._create = function(wargs) {
    // Get the jMaki wrapper properties for a Woodstock modalWindow.
    var props = {};
    if (wargs.args) {
	// Properties in the "args" property must be modalWindow properties!
        @JS_NS@._base.proto._extend(props, wargs.args);
        
    } else {
	// No data. Define minimalist modalWindow.
    }
    if (wargs.value) {
        @JS_NS@._base.proto._extend(props, wargs.value);
    } else {
	// No data. Define single dummy items list.
    }

    // Add our widget id and type.
    props.id = this._wid;
     
    props.widgetType = "modalWindow";

 
    // ============================================================
    // Create the Woodstock widget...
  
      
    // Create the Woodstock modalWindow widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
       
    // add event handling
    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.modalWindow.event.visible.endTopic,
        this, "_onWindowClose");    
    
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.modalWindow.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};



// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.modalWindow.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// 
// Callback function to handle all of jMaki modalWindow topics.
// Event payload contains:
//    {event: browser event}
@JMAKI_NS@.modalWindow.Widget.prototype._eventCallback = function(payload) {
    //get the event type
    var topic = payload.topic;
    var command = topic.substr(topic.lastIndexOf("/")+1);
    
    if (command == "open") {
        this._open(payload);
    } else if (command == "close") {
        this._close(payload);
    } else if (command == "setContent") {
        this._setContent(payload);
    } else if (command == "setInclude") {
        this._setInclude(payload);
    }
    
};

// open a window
@JMAKI_NS@.modalWindow.Widget.prototype._open = function(payload) {
    if (!payload || !payload.targetId) {
        return false;
    }
    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
        widget.open();
    }

};

// close window
@JMAKI_NS@.modalWindow.Widget.prototype._close = function(payload) {
    if (!payload || !payload.targetId) {
        return false;
    }
    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
        widget.close();
    }

};

// set a content of the tab
@JMAKI_NS@.modalWindow.Widget.prototype._setContent = function(payload) {
    if (!payload ) {
        return false;
    }
      
    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
        var props = {};
        
        props.contents = [];               

          var contentsElement = {};
          contentsElement.html  = payload.value;
          props.contents.push(contentsElement);

        widget.setProps(props);
    }
};

// setInclude content of the tab
@JMAKI_NS@.modalWindow.Widget.prototype._setInclude = function(payload) {
    //we expect only payload that contains event
    if (!payload) {
        return false;
    }
      
    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
        var props = {};
        
        props.contents = [];               

          var contentsElement = {};
          contentsElement.include  = payload.value;
          props.contents.push(contentsElement);

        widget.setProps(props);
    }
};


// select a tab
@JMAKI_NS@.modalWindow.Widget.prototype._onWindowClose = function(props) {
    var payload = {};
    payload.targetId = this._wid;
    jmaki.publish(this._publish, payload );
};


