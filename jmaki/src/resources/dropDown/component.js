jmaki.namespace("@JMAKI_NS@.dropDown");

/*
 * jMaki wrapper for Woodstock dropDown widget.
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

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/dropDown"];
    this._publish = "/@JS_NAME@/dropDown";
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
@JMAKI_NS@.dropDown.Widget.prototype._create = function(wargs) {
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
	this._wid = props.id;
    }
    props.widgetType = "dropDown";

    // ============================================================
    // Create the Woodstock widget...

    // Hook the dropDown widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    props.onChange = this.hitch(this, "_selectedCallback");

    // Create the Woodstock dropDown widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.dropDown.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};

// Call a function in given scope.
@JMAKI_NS@.dropDown.Widget.prototype.hitch = function(scope, method) {
    return function() {
        return scope[method].apply(scope, arguments || []);
    };
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.dropDown.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// Callback function to handle jMaki select topic.
// Event payload contains:
//    {value: <item_value>}
// Update dropDown widget to select option with matching value.
@JMAKI_NS@.dropDown.Widget.prototype._selectCallback = function(payload) {
    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
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

// Callback function to handle Woodstock dropDown onSelect event.
// Event payload contains:
//    dropDown widget identifier
// Publish jMaki onSelect event.
@JMAKI_NS@.dropDown.Widget.prototype._selectedCallback = function() {
    if (this._wid) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
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
// Update dropDown widget to replace options array.
@JMAKI_NS@.dropDown.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value && payload.value instanceof Array) {
                widget.setProps({options: payload.value});
            }
	}
    }
};
