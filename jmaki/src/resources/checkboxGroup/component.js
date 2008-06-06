jmaki.namespace("@JMAKI_NS@.checkboxGroup");

/*
 * jMaki wrapper for Woodstock Checkbox Group widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data containing an array of checkbox widgets.
 *            The array is assigned to the checkboxGroup "contents" property.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the checkboxGroup widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/checkboxGroup".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/checkboxGroup".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * select     To indicate one or more checkboxes and their checked state.
 *            Can be used to check/uncheck the checkboxes in the group.
 * setValues  To reset the set of checkbox values in the group contents.
 *
 * This widget publishes the following jMaki events:
 *
 * onSelect   To indicate a checkbox in the group has changed its checked
 *            state.  Multiple checkboxes in the group can be in the
 *            checked (true) state at any one time.
 */
@JMAKI_NS@.checkboxGroup.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/checkboxGroup"];
    this._publish = "/@JS_NAME@/checkboxGroup";
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
@JMAKI_NS@.checkboxGroup.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock checkboxGroup.
    // Assign properties from "args" object, set id and type, and
    // process "value" object properties.
    var props;
    if (wargs.args) {
	props = wargs.args;
    } else {
	props = {};
    }
    if (typeof props.name == "undefined") {
	props.name = this._wid;
    }
    if (wargs.value && wargs.value instanceof Array) {
	props.contents = this._mapContents(name, wargs.value);
    } else {
	// No data, so add a single checkbox in group's contents.
	var cb_id = this._wid + "_cb";
	props.contents = [{id: cb_id, widgetType: "checkbox", label: {value: "Checkbox 1", level: 3}, value: "cb1", name: props.name, checked: false}];
    }
    props.id = this._wid;
    props.widgetType = "checkboxGroup";

    // Create the Woodstock checkboxGroup widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.checkboxGroup.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
};

// Call a function in given scope.
@JMAKI_NS@.checkboxGroup.Widget.prototype.hitch = function(scope, method) {
    return function() {
        return scope[method].apply(scope, arguments || []);
    };
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.checkboxGroup.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map the value array of checkboxes to the checkbox group contents array.
// Must add the id, the name property from the group, and the widgetType
// to each checkbox definition in the array.  Add on onChange property
// to call this group wrapper handler so we can publish an onSelect event.
// Returns new array of checkbox objects.
@JMAKI_NS@.checkboxGroup.Widget.prototype._mapContents = function(name, value) {

    parr = [];
    if (value && value instanceof Array) {
	for (var i = 0; i < value.length; i++) {
	    if (typeof value[i] == "object") {
		var src = value[i];
		var obj = {};
		for (p in src) {
		    obj[p] = src[p];
		}			// End of inner for
		var id = this._wid + "_cb" + i;
		obj.id = id;
		obj.widgetType = "checkbox";
		obj.name =  name;
		obj.onChange = this.hitch(this, "_selectedCallback");
		parr.push(obj);
	    }
	}				// End of outer for
    }
    return (parr);

};

// Callback function to handle Woodstock checkbox onChange event.
// Publish jMaki onSelect event with payload:
//   {widgetId: <group_wid>,
//    topic: {type: "onSelect", targetId: <checkbox_value>, value: <checked>}}
@JMAKI_NS@.checkboxGroup.Widget.prototype._selectedCallback = function(evt) {

    if (evt) {
	var domid = evt.target.id;
 	var cbid = domid.substring(0, (domid.length - 3));
        var widget = @JS_NS@.widget.common.getWidget(cbid);
        if (widget) {
            var props = widget.getProps();
            var val = props.value;
            var ckd = props.checked;
            // Format a jMaki onSelect event topic payload
            // and publish the jMaki event.
            var payload = {widgetId: this._wid, topic:
                    {type: "onSelect", targetId: val, value: ckd}};
            var selectedTopic = this._publish + "/onSelect";
            jmaki.publish(selectedTopic, payload);
        }
    }
};

// Callback function to handle jMaki select topic.
// Event payload contains an object specifying the checkbox value in
// the group which should be checked (true).
//    {value: <checkbox_value>}
// Update checkboxGroup widget to reset checked state for the given checkbox.
@JMAKI_NS@.checkboxGroup.Widget.prototype._selectCallback = function(payload) {
    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
            if (payload.value) {
                var props = widget.getProps();
		// Iterate over values in the group contents...
                for (var i = 0; i < props.contents.length; i++) {
                    if (props.contents[i].value == payload.value) {
			var cb_id = props.contents[i].id;
			var cb_wid = @JS_NS@.widget.common.getWidget(cb_id);
			if (cb_wid) {
			    cb_wid.setProps({checked: true});
			}
			break;
                    }
		}			// End of for
            }
        }
    }
};

// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: [<checkbox_data model>]}
// Update checkboxGroup widget to replace contents array.
@JMAKI_NS@.checkboxGroup.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value && payload.value instanceof Array) {
		var props = widget.getProps();
		var name = props.name;
		var cts = this._mapContents(name, payload.value);
                widget.setProps({contents: cts});
            }
	}
    }
};
