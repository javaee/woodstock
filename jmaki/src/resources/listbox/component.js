jmaki.namespace("@JMAKI_NS@.listbox");

/*
 * jMaki wrapper for Woodstock Listbox widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data following the jMaki ComboBox data model,
 *            mapped onto the listbox "options" property.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the listbox widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/listbox".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/listbox".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * select     To indicate the option to be selected in the widget.
 * setValues  To reset the options property in the widget.
 *
 * This widget publishes the following jMaki events:
 *
 * onSelect   To indicate the option that was selected in the widget.
 *            Be aware that this widget can define multiple selected
 *            items in its onSelect event!
 */
@JMAKI_NS@.listbox.Widget = function(wargs) {

    // Turn on jMaki debugging (development only)
    // jmaki.debug = true;
    // jmaki.log("Entering woodstock listbox Widget function...");

    // Initialize basic wrapper properties.
    var _widget = this;
    var subscribe = ["/@JS_NAME@/listbox"];
    var publish = "/@JS_NAME@/listbox";
    var wid = wargs.uuid;
    var span_id = wargs.uuid + "_span";
    if (wargs.publish) {
	// User supplied a specific topic to publish to.
	publish = wargs.publish;
    }
    if (wargs.subscribe) {
	// User supplied one or more specific topics to subscribe to.
        if (typeof wargs.subscribe == "string") {
            subscribe = [];
            subscribe.push(wargs.subscribe);
        } else {
            subscribe = wargs.subscribe;
        }
    }

    // Get the jMaki wrapper properties for a Woodstock listbox.
    var props;
    if (wargs.args) {
	// Properties in the "args" property must be listbox properties!
	props = wargs.args;
    } else {
	// No data. Define minimalist listbox.
	props = {};
	props.multiple = false;
	props.size = 6;
    }
    if (wargs.value && wargs.value instanceof Array) {
	props.options = wargs.value;
    } else {
	// No data. Define single dummy options list.
	props.options = [{label: "Option 1", value: "opt1", selected: true}];
    }

    // Add our widget id and type.
    if (typeof props.id == "undefined") {
	props.id = wargs.uuid;
    } else {
	wid = props.id;
    }
    props.widgetType = "listbox";

    // =============================================================
    // Event topic callback functions...

    // Callback function to handle jMaki select topic.
    // Event payload contains:
    //    {value: <item_value>}
    // Update listbox widget to select option with matching value.
    _selectCallback = function(payload) {

	if (payload) {
	    var widget = @JS_NS@.widget.common.getWidget(wid);
	    if (widget) {
		var val = "";
		if (typeof payload.value == "string") {
		    val = payload.value;
		}
		if (typeof payload.value == "object") {
		    val = payload.value.targetId;
		}
		var props = widget.getProps();
		for (var i = 0; i < props.options.length; i++) {
		    var opt = props.options[i];
		    if (opt.group == null || opt.group == false) {
			if (opt.value == val) {
			    widget.setSelectedIndex(i);
			    break;
			}
		    }
		}		// End of for
	    }
	}
			    
    };

    // Callback function to handle jMaki setValues topic.
    // Event payload contains:
    //    {value: [<data model>]}
    // Update listbox widget to replace options array.
    _valuesCallback = function(payload) {

	if (payload) {
	    var widget = @JS_NS@.widget.common.getWidget(wid);
	    if (widget) {
		if (payload.value && payload.value instanceof Array) {
		    widget.setProps({options: payload.value});
		}
	    }
	}

    };

    // Callback function to handle Woodstock listbox onSelect event.
    // Event payload contains:
    //    Listbox widget identifier
    // Publish jMaki onSelect event.
    _selectedCallback = function() {

	if (wid) {
	    var widget = @JS_NS@.widget.common.getWidget(wid);
	    if (widget) {
		var val = widget.getSelectedValue();
		if (val) {
		    // Format a jMaki onSelect event topic payload
		    // and publish the jMaki event.
		    var payload = {widgetId: wid, topic:
			{type: "onSelect", targetId: wid, value: val}};
		    var selectedTopic = publish + "/onSelect";
		    jmaki.publish(selectedTopic, payload);
		}
	    }
	}

    };

    // ============================================================
    // jMaki lifecycle functions...

    // PostLoad...
    // Subscribe to jMaki events
    this.postLoad = function() {

	_widget.subscriptions = [];
	for (var i = 0; i < subscribe.length; i++) {
	    var s1 = jmaki.subscribe(subscribe + "/select", _selectCallback);
	    _widget.subscriptions.push(s1);
	    var s2 = jmaki.subscribe(subscribe + "/setValues", _valuesCallback);
	    _widget.subscriptions.push(s1);
	}		// End of for

    };

    // Destroy...
    // Unsubscribe from jMaki events
    this.destroy = function() {

	if (_widgets.subscriptions) {
	    for (var i = 0; i < _widgets.subscriptions.length; i++) {
		jmaki.unsubscribe(_widgets.subscriptions[i]);
	    }		// End of for
	}

    };

    // ============================================================
    // Create the Woodstock widget...

    // Hook the listbox widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    props.onChange = function(event) {
	    _selectedCallback();
        }

    // Create the Woodstock listbox widget.
    @JS_NS@.widget.common.createWidget(span_id, props);

};
