/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

jmaki.namespace("@JMAKI_NS@.popupMenu");

/*
 * jMaki wrapper for Woodstock popupMenu widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     The data value following the jMaki menu data model:
 *            {menu: [
 *              {label: <label>, value: <string>}
 *              {label: <label>, href: <url>},
 *              {label: <label>, action: {topic: <topic>, message: <msg>}},
 *              {label: <label>, menu: [sub_menus]}
 *            ]}
 *            The menu item can be used for navigation (href), to
 *            publish some event (action), or include a sub-menu (menu).
 *            A menu item can be disabled (disable).  If a simple string
 *            value is defined, a default action event is published.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/popupMenu".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/popupMenu".
 * id:        User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * open       Opens the popupMenu with UI event as its payload.
 * close      Closes the popupMenu with UI event as its payload.
 *
 * This widget publishes the following jMaki events:
 * 
 * onClick    Notify subscribers that a menu item has been selected:
 *            Uses action property if specified; otherwise, default:
 *            {widgetId: <wid>, type: "onClick", targetId: "<menu_value>",
 *             topic: "/woodstock/popupMenu/onClick"}
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
	var s1 = jmaki.subscribe(this._subscribe[i] + "/open", 
	@JS_NS@.widget.common._hitch(this, "_openCallback"));
	this._subscriptions.push(s1);
	var s2 = jmaki.subscribe(this._subscribe[i] + "/close",
	@JS_NS@.widget.common._hitch(this, "_closeCallback"));
	this._subscriptions.push(s2);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.popupMenu.Widget.prototype._create = function(wargs) {

    // Get the jMaki wrapper properties for a Woodstock popupMenu.
    var props = {};
    if (wargs.args) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value) {
	@JS_NS@._base.proto._extend(props, wargs.value);
    }
    this._convertModel(props);

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "popupMenu";
  
    // Stack any existing onChange handler.
    if (props.onChange) {
	this._onChange = (typeof props.onChange == 'string')
	    ? new Function(props.onChange) : props.onChange;
    }

    // add event handling
    props.onChange = @JS_NS@.widget.common._hitch(this, "_onChangeCallback");
  
    // Create the Woodstock popupMenu widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.popupMenu.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
	for (var i = 0; i < this._subscriptions.length; i++) {
	    jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};



// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.popupMenu.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Callback function to handle jMaki open event.
// Event payload contains: {targetId: <menu_id>, event: {<window_event>}}
@JMAKI_NS@.popupMenu.Widget.prototype._openCallback = function(payload) {
	this._display("open", payload);
};

// Callback function to handle jMaki close event.
// Event payload contains: {targetId: <menu_id>, event: {<window_event>}}
@JMAKI_NS@.popupMenu.Widget.prototype._closeCallback = function(payload) {
	this._display("close", payload);
};

// display or hide the widget
@JMAKI_NS@.popupMenu.Widget.prototype._display = function(command, payload) {
      //we expect the window event in the payload
      if (!payload || !payload.event) {
	  return false;
      }
      var event = payload.event;
      var widget = @JS_NS@.widget.common.getWidget(this._wid);
      if (widget) {
	  if (command == "open") {
	      widget.open(event);
	  } else {
	      widget.close();
	  }
    }
};

// Handle the event that selects a menu item option.
// Processing depends on the string contents of the value property:
// <string>	 - Publish default event
// href: <url>      - Execute the link
// action: {...}    - Publish action event (object is stringified)
// Note: Should not get here if a group menu item is clicked.
@JMAKI_NS@.popupMenu.Widget.prototype._onChangeCallback = function() {

    var result = true;
    if (typeof this._onChange == "function") {
	result = this._onChange();
    }
    if (result != null && result == false) {
	return result;
    }

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	var val = widget.getSelectedValue();
	if (val != null) {
	    var sep = val.indexOf(':');
	    if (sep <= 0) {
		jmaki.processActions({
		    action: "onClick",
		    targetId: val,
		    topic: this._publish + "/onClick",
		    type: "onClick",
		    widgetId: this._wid
		});
		return result;
	    }
	    var command = val.substring(0, sep);
	    var commandValue = val.substring(sep + 1);
	    if (command == "href") {
		// Bye bye!
		window.location.href = commandValue;
	    } else if (command == "action") {
		var obj = @JS_NS@.json.parse(commandValue);
		if (obj) {
		    var top = (obj.topic != null) ?
			obj.topic : this._publish + "/onClick";
		    var msg = (obj.message != null) ? obj.message : null;
		    jmaki.processActions({
			action: "onClick",
			targetId: null,
			topic: top,
			message: msg,
			type: "onClick",
			widgetId: this._wid
		    });
		}
	    }
	}
    }
    return result;

};

// Convert data model to woodstock popupMenu widget options properties.
@JMAKI_NS@.popupMenu.Widget.prototype._convertModel = function(props){
    
    // convert menu items into 'options' and force top level visible.
    if (props.visible == null) {
	props.visible = false;
    }
    this._createOptions(props.menu, props);

};


// Recursive function to convert menu items into woodstock 'options'
// menuRef - reference to the wrapper "menu" object
// optionsPlacement - reference to the object to contain new 'options'
// Menu item type mapping to widget option value property:
// href: <url>     -> "href:<url>"
// action: {...}   -> "action:<stringified_JSON_object>"
// value: <string> -> <string>
// menu: [submenu] -> <group_option>  (recurse over submenu items)
// <nothing>       -> <label>
@JMAKI_NS@.popupMenu.Widget.prototype._createOptions = function(menuRef, optionsPlacement){    

    optionsPlacement.options = [];
    if (menuRef instanceof Array) {
	for (var i = 0; i < menuRef.length; i += 1) {
	    var item = menuRef[i];
	    var option  = {};
	    option.group = false; 
	    option.escape = true;
	    option.label = (item.label != null)? item.label : "Menu item";
	    if (item.menu != null) {
		option.group = true; 
		this._createOptions(item.menu, option);
	    } else if (item.href != null) {
		option.value = "href:" + item.href;
	    } else if (item.action != null) {
		    option.value = "action:" + 
		    @JS_NS@.json.stringify(item.action);
	    } else if (item.value != null) {
		option.value = item.value;
	    } else {
		option.value = item.label;
	    }
	    optionsPlacement.options.push(option);	
	}
    }
    
};
