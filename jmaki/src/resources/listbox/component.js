jmaki.namespace("jmaki.widgets.woodstock.listbox");

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
jmaki.widgets.woodstock.listbox.Widget = function(wargs) {
    // Turn on jMaki debugging (development only)
    // jmaki.debug = true;
    // jmaki.log("Entering woodstock listbox Widget function...");

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/listbox"];
    this._publish = "/woodstock/listbox";
    this._subscriptions = [];
    this._wid = wargs.uuid;

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
        var s1 = jmaki.subscribe(this._subscribe + "/select",
            this.hitch(this, "_selectCallback"));
        this._subscriptions.push(s1);
        var s2 = jmaki.subscribe(this._subscribe + "/setValues", 
            this.hitch(this, "_valuesCallback"));
        this._subscriptions.push(s2);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
jmaki.widgets.woodstock.listbox.Widget.prototype._create = function(wargs) {
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
	this._wid = props.id;
    }
    props.widgetType = "listbox";

    // ============================================================
    // Create the Woodstock widget...

    // Hook the listbox widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    var wid = this._wid;
    props.onChange = this.hitch(this, "_selectedCallback");

    // Create the Woodstock listbox widget.
    var span_id = wargs.uuid + "_span";
    woodstock4_3.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
jmaki.widgets.woodstock.listbox.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};

// Call a function in given scope.
jmaki.widgets.woodstock.listbox.Widget.prototype.hitch = function(scope, method) {
    return function() {
        return scope[method].apply(scope, arguments || []);
    };
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
jmaki.widgets.woodstock.listbox.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// Callback function to handle jMaki select topic.
// Event payload contains:
//    {value: <item_value>}
// Update listbox widget to select option with matching value.
jmaki.widgets.woodstock.listbox.Widget.prototype._selectCallback = function(payload) {
    if (payload) {
	var widget = woodstock4_3.widget.common.getWidget(this._wid);
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
            } // End of for
        }
    }
};

// Callback function to handle Woodstock listbox onSelect event.
// Event payload contains:
//    Listbox widget identifier
// Publish jMaki onSelect event.
jmaki.widgets.woodstock.listbox.Widget.prototype._selectedCallback = function() {
    if (this._wid) {
        var widget = woodstock4_3.widget.common.getWidget(this._wid);
	if (widget) {
            var val = widget.getSelectedValue();
            if (val) {
                // Format a jMaki onSelect event topic payload
                // and publish the jMaki event.
                var payload = {widgetId: this._wid, topic:
                    {type: "onSelect", targetId: this._wid, value: val}};
		var selectedTopic = this._publish + "/onSelect";
		jmaki.publish(selectedTopic, payload);
            }
	}
    }
};

// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: [<data model>]}
// Update listbox widget to replace options array.
jmaki.widgets.woodstock.listbox.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = woodstock4_3.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value && payload.value instanceof Array) {
                widget.setProps({options: payload.value});
            }
	}
    }
};
