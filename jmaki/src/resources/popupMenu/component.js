jmaki.namespace("@JMAKI_NS@.popupMenu");

/*
 * jMaki wrapper for Woodstock popupMenu widget.
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
 * /woodstock/popupMenu/setValues
 *
 * This widget publishes the following jMaki events:
 * 
 * /woodstock/popupMenu/onClick- to allow subscriber retrieve newly entered date
 * none
 */
@JMAKI_NS@.popupMenu.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/popupMenu"];
    this._publish = "/woodstock/popupMenu";
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
        woodstock4_3.widget.common._hitch(this, "_eventCallback"));
        this._subscriptions.push(s1);

    }
    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.popupMenu.Widget.prototype._create = function(wargs) {
    // Get the jMaki wrapper properties for a Woodstock popupMenu.
    var props = {};
    if (wargs.args) {
	// Properties in the "args" property must be popupMenu properties!
        woodstock4_3._base.proto._extend(props, wargs.args);
        
    } else {
	// No data. Define minimalist popupMenu.
    }
    if (wargs.value) {
        woodstock4_3._base.proto._extend(props, wargs.value);
    } else {
	// No data. Define single dummy options list.
    }

    // Add our widget id and type.
    props.id = this._wid;
     
    props.widgetType = "popupMenu";

    this._convertModel(props);

    // ============================================================
    // Create the Woodstock widget...
  
    // add event handling
    
    props.onChange = "@JMAKI_NS@.popupMenu.Widget.prototype._onClickCallback('" + props.id + "'); return false;";
  
    // Create the Woodstock popupMenu widget.
    var span_id = wargs.uuid + "_span";
    woodstock4_3.widget.common.createWidget(span_id, props);
       
    
    
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.popupMenu.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};



// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.popupMenu.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// 
// Callback function to handle all of jMaki popupMenu topics.
// Event payload contains:
//    {event: browser event}
@JMAKI_NS@.popupMenu.Widget.prototype._eventCallback = function(payload) {
    //get the event type
    var topic = payload.topic;
    var subtopic = topic.substr(topic.lastIndexOf("/")+1);
    
    if (subtopic == "open" || subtopic =="close") {
        this._display(subtopic, payload);
    }
    
};
// display /hide the widget

@JMAKI_NS@.popupMenu.Widget.prototype._display = function(subtopic, payload) {
    //we expect only payload that contains event
    if (!payload || !payload.event) {
        return false;
    }
    var event = payload.event;
      
    var widget = woodstock4_3.widget.common.getWidget(this._wid);
    if (widget) {
        if (subtopic == "open") {
            widget.open(event);
        } else {
            widget.close();
        }

    }
};



@JMAKI_NS@.popupMenu.Widget.prototype.processAction = function(action, widgetId ) {
    if (action && !(action instanceof Array)) {
        var _m = {widgetId : widgetId};        
        if (action.message && action.message.value) _m.value = action.message.value;

        var _topic = action.topic;
        if (!_topic) _topic = _topic + "/defaultAction" ;
       
        if (action && action.message) _m.message = action.message;               
        jmaki.publish(_topic,_m);
    }
};

@JMAKI_NS@.popupMenu.Widget.prototype.clone = function(t) {
    var obj = {};
    for (var i in t) {
        obj[i] = t[i];
    }
    return obj;
};

@JMAKI_NS@.popupMenu.Widget.prototype._onClickCallback = function(widgetId){
    var widget = woodstock4_3.widget.common.getWidget(widgetId);
 	
    if (widget) {
        var val = widget.getSelectedValue();
        if (!val) {
            return false;
        }
        if (typeof val == "object") {
            //process action
            @JMAKI_NS@.popupMenu.Widget.prototype.processAction(val);
           
        } else {
        
        
            var sep = val.indexOf (':');
            if (sep <= 0) {
                return false;
            }
            var command = val.substring(0, sep);
            var commandValue = val.substring(sep + 1);
         
            if (command == "href") {
                window.location.href = commandValue;
            } else if (command == "topic") {
                // Format a jMaki event topic payload
                // and publish the jMaki event.
                var payload = {widgetId: widgetId, topic:
                        {type: "onClick", targetId: widgetId, value: commandValue}};
                var selectedTopic = "/" + commandValue;
                jmaki.publish(selectedTopic, payload);
            } 
        }
    }
    /*
    if (href) {
        
    }else if (menu[sp[0]].menu[sp[1]].action ) {
        
    }
     */
};

// Converts jmaki model to props digestable by woodstock popupMenu widget
@JMAKI_NS@.popupMenu.Widget.prototype._convertModel = function(props){
    
    // make visible by default
    if (typeof props.visible == "undefined") {
        props.visible = true;
    }

    
    // convert menu items into 'options'
    this._createOptions(props.menu, props);


};


// Recursive function to convert menu items into woodstock 'options'
// menuRef - reference to the menu object
// optionsPlacement - reference to the object that will contain newly created 'options'
@JMAKI_NS@.popupMenu.Widget.prototype._createOptions = function(menuRef, optionsPlacement){    
    if (!menuRef || !optionsPlacement) {
        return false;
    }
    optionsPlacement['options']  = [];
    
    if (this._isArray(menuRef)) {
        //typeof array returns object. see http://javascript.crockford.com/remedial.html
        for (i = 0; i < menuRef.length; i += 1) {
            var option  = {};
           
            //defaults
            option.group = false; 
            option.separator = true;
            option.escape = true;
           
            option.label = (menuRef[i].label)? menuRef[i].label : "specify value";

           
            if (typeof menuRef[i].menu != "undefined") {
                option.group = true; 
                this._createOptions(menuRef[i].menu, option);
            } else {

                // use option.value to store href, action, topic - they are considered to be a 
                // mutually exclusive options
              
                if (typeof menuRef[i].href != "undefined") {
                    option.value = "href:" + menuRef[i].href;
                } else if (typeof menuRef[i].topic != "undefined") {
                    option.value = "topic:" + menuRef[i].topic;
                } else if (typeof menuRef[i].action != "undefined") {
                    option.value = menuRef[i].action; // object, not string
                }
            }
            optionsPlacement["options"].push(option);        
        }
    }
    
};
// util method to check whether JSON object is an array
@JMAKI_NS@.popupMenu.Widget.prototype._isArray = function(testObject){    
    //   return testObject && !(testObject.propertyIsEnumerable('length')) && typeof testObject === 'object' && typeof testObject.length === 'number';
    return (testObject && typeof testObject == "object" && typeof testObject.length != "undefined");
}

