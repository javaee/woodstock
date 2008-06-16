jmaki.namespace("@JMAKI_NS@.accordion");

/*
 * jMaki wrapper for Woodstock accordion widget.
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
 * /woodstock/accordion/select
 * /woodstock/accordion/setContent
 * /woodstock/accordion/setInclude
 *
 * This widget publishes the following jMaki events:
 * 
 * /woodstock/accordion/onSelect- to notify subscriber of selected tab
 * 
 */
@JMAKI_NS@.accordion.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/accordion/select", "/woodstock/accordion/setContent", "/woodstock/accordion/setInclude"];
    this._publish = "/woodstock/accordion/onSelect";
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
        woodstock4_3.widget.common._hitch(this, "_eventCallback"));
        this._subscriptions.push(s1);

    }
    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.accordion.Widget.prototype._create = function(wargs) {
    // Get the jMaki wrapper properties for a Woodstock accordion.
    var props = {};
    if (wargs.args) {
	// Properties in the "args" property must be accordion properties!
        woodstock4_3._base.proto._extend(props, wargs.args);
        
    } else {
	// No data. Define minimalist accordion.
    }
    if (wargs.value) {
        woodstock4_3._base.proto._extend(props, wargs.value);
    } else {
	// No data. Define single dummy items list.
    }

    // Add our widget id and type.
    props.id = this._wid;
     
    props.widgetType = "accordion";

    this._convertModel(props);

    // ============================================================
    // Create the Woodstock widget...
  
      
    // Create the Woodstock accordion widget.
    var span_id = wargs.uuid + "_span";
    woodstock4_3.widget.common.createWidget(span_id, props);
       
    // add event handling
    woodstock4_3.widget.common.subscribe(woodstock4_3.widget.accordionTab.event.title.selectedTopic,
        this, "_onTabSelected");    
    
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.accordion.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};



// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.accordion.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// 
// Callback function to handle all of jMaki accordion topics.
// Event payload contains:
//    {event: browser event}
@JMAKI_NS@.accordion.Widget.prototype._eventCallback = function(payload) {
    //get the event type
    var topic = payload.topic;
    var command = topic.substr(topic.lastIndexOf("/")+1);
    
    if (command == "select") {
        this._select(payload);
    } else if (command == "setContent") {
        this._setContent(payload);
    } else if (command == "setInclude") {
        this._setInclude(payload);
    }
    
};
// select a tab
@JMAKI_NS@.accordion.Widget.prototype._select = function(payload) {
    if (!payload || !payload.targetId) {
        return false;
    }
    var widget = woodstock4_3.widget.common.getWidget(this._wid);
    if (widget) {
        //widget._collapseAllTabs();
        widget.tabSelected({id: payload.targetId});
    }

};

// set a content of the tab
@JMAKI_NS@.accordion.Widget.prototype._setContent = function(payload) {
    if (!payload || !payload.targetId) {
        return false;
    }
      
    var widgetTab = woodstock4_3.widget.common.getWidget(payload.targetId);
    if (widgetTab) {
        var props = {};
        
        props.tabContent = [];               

          var tabContentElement = {};
          tabContentElement.html  = payload.value;
          props.tabContent.push(tabContentElement);

        //ISSUE: this will append the content, not replace it.
        widgetTab.setProps(props);
    }
};
// setInclude content of the tab
@JMAKI_NS@.accordion.Widget.prototype._setInclude = function(payload) {
    //we expect only payload that contains event
    if (!payload) {
        return false;
    }
      
    var widget = woodstock4_3.widget.common.getWidget(this._wid);
    if (widget) {
        //
    }
};


// select a tab
@JMAKI_NS@.accordion.Widget.prototype._onTabSelected = function(props) {
    if (!props || !props.id) {
        return false;
    }

    var payload = {};
    payload.targetId = props.id;
    jmaki.publish(this._publish, payload );
};


// Converts jmaki model to props digestable by woodstock accordion widget
@JMAKI_NS@.accordion.Widget.prototype._convertModel = function(props){
    
    
    props.toggleControls = true;

    if (!props.items) {
        return false;
    }
    
    props['tabs']  = [];
    
    if (this._isArray(props.items)) {
        //typeof array returns object. see http://javascript.crockford.com/remedial.html
        for (i = 0; i < props.items.length; i += 1) {
            var tab  = {};
           
            //defaults
            //tab.contentHeight = "100px"; 
            tab.widgetType = "accordionTab";
            tab.id = (typeof props.items[i].id != "undefined")? props.items[i].id : this._wid + "_tab" + i;
            tab.title = (typeof props.items[i].label != "undefined")? props.items[i].label : "Tab " + i;
            
            tab.tabContent = [];
                
            if (typeof props.items[i].include != "undefined") {
                //mark tab selected
                
                //todo
                props.items[i].content  = "TODO:" + props.items[i].include + " to be included";
            }
            if (typeof props.items[i].content != "undefined") {
                var tabContentElement = {};
                tabContentElement.html = props.items[i].content;
                tab.tabContent.push(tabContentElement);
            } 
            
            if (typeof props.items[i].selected != "undefined") {
                //mark tab selected
                tab.selected = props.items[i].selected;
            }
            
            if (typeof props.items[i].lazyLoad != "undefined") {
                //if set to true then the content is loaded when the pane is selected.
                props.loadOnSelect = props.items[i].lazyLoad;
            }
            
           
            
            props["tabs"].push(tab);        

        }
    }


};


// util method to check whether JSON object is an array
@JMAKI_NS@.accordion.Widget.prototype._isArray = function(testObject){    
    // return testObject && !(testObject.propertyIsEnumerable('length')) && typeof testObject === 'object' && typeof testObject.length === 'number';
    // return (testObject && typeof testObject == "object" && typeof testObject.length != "undefined");
    return (testObject && testObject instanceof Array);
}

