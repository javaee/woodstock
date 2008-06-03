jmaki.namespace("@JMAKI_NS@.dropDown");

/*
 * jMaki wrapper for Woodstock DropDown widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data following the jMaki ComboBox data model,
 *            mapped onto the dropDown "options" property.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the dropDown widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/dropDown".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/dropDown".
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
@JMAKI_NS@.dropDown.Widget = function(wargs) {

    // Turn on jMaki debugging (development only)
    // jmaki.debug = true;
    // jmaki.debugGlue = true;
    // jmaki.log("Entering woodstock dropDown Widget function...");

    // Initialize basic wrapper properties.
    var _widget = this;
    var subscribe = ["/@JS_NAME@/dropDown"];
    var publish = "/@JS_NAME@/dropDown";
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

    // Get the jMaki wrapper properties for a Woodstock dropDown.
    var props;
    if (wargs.args) {
	// Properties in the "args" property must be dropDown properties!
	props = wargs.args;
    } else {
	// No data. Define minimalist dropDown.
	props = {};
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
    props.widgetType = "dropDown";

    // =============================================================
    // Event topic callback functions...

    // Callback function to handle jMaki select topic.
    // Event payload contains:
    //    {value: <item_value>}
    // Update dropDown widget to select option with matching value.
    this._selectCallback = function(payload) {

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
    // Update dropDown widget to replace options array.
    this._valuesCallback = function(payload) {

	if (payload) {
	    var widget = @JS_NS@.widget.common.getWidget(wid);
	    if (widget) {
		if (payload.value && payload.value instanceof Array) {
		    widget.setProps({options: payload.value});
		}
	    }
	}

    };

    // Callback function to handle Woodstock dropDown onSelect event.
    // Event payload contains:
    //    dropDown widget identifier
    // Publish jMaki onSelect event.
    this._selectedCallback = function() {

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
	    var s1 = jmaki.subscribe(subscribe + "/select",
		_widget._selectCallback);
	    _widget.subscriptions.push(s1);
	    var s2 = jmaki.subscribe(subscribe + "/setValues",
		_widget._valuesCallback);
	    _widget.subscriptions.push(s1);
	}		// End of for

    };

    // Destroy...
    // Unsubscribe from jMaki events
    this.destroy = function() {

	if (_widget.subscriptions) {
	    for (var i = 0; i < _widget.subscriptions.length; i++) {
		jmaki.unsubscribe(_widget.subscriptions[i]);
	    }		// End of for
	}

    };

    // ============================================================
    // Create the Woodstock widget...

    // Hook the dropDown widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    props.onChange = function(event) {
	    _widget._selectedCallback();
        }

    // Create the Woodstock dropDown widget.
    @JS_NS@.widget.common.createWidget(span_id, props);

};
