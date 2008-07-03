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

jmaki.namespace("@JMAKI_NS@.listbox");

/*
 * jMaki wrapper for Woodstock Listbox widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data following the jMaki ComboBox data model,
 *            an array of one or more objects as follows:
 *            [{label: <option_label>,
 *              value: <option_value>, 
 *              selected: <true | false>},
 *             ...,]
 *            The array is mapped directly to the listbox widget
 *            "options" property.  The "selected" property is optional
 *            and the option is rendered unselected by default.
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
 * select     To indicate the option to be selected in the widget's list.
 * setValues  To reset the list of options in the widget.
 *
 * This widget publishes the following jMaki events:
 *
 * onSelect   To indicate the option that was selected in the widget.
 *            Be aware that this widget can define multiple selected
 *            items in its onSelect event!
 */
@JMAKI_NS@.listbox.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/listbox"];
    this._publish = "/@JS_NAME@/listbox";
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
	var s1 = jmaki.subscribe(this._subscribe[i] + "/select",
	    @JS_NS@.widget.common._hitch(this, "_selectCallback"));
	this._subscriptions.push(s1);
	var s2 = jmaki.subscribe(this._subscribe[i] + "/setValues", 
	    @JS_NS@.widget.common._hitch(this, "_valuesCallback"));
	this._subscriptions.push(s2);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.listbox.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock listbox.
    // Value must contain an array of Options objects.
    var props = {};
    if (wargs.args != null) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value instanceof Array) {
	props.options = wargs.value;
    }
    if (props.options == null) {
	props.options = [{label: "Option 1", value: "opt1", selected: true}];
    }

    // If application sets onChange, stack function for handling later.
    if (props.onChange) {
	this._onChange = (typeof props.onChange == 'string')
	    ? new Function(props.onChange) : props.onChange;
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "listbox";

    // Hook the listbox widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    props.onChange = @JS_NS@.widget.common._hitch(this, "_selectedCallback");

    // Create the Woodstock listbox widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.listbox.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
	for (var i = 0; i < this._subscriptions.length; i++) {
	    jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.listbox.Widget.prototype.postLoad = function() {
    // Do nothing...
}

// Callback function to handle jMaki select topic.
// Event payload contains:
//    {value: <item_value>}
// Update listbox widget to select option with matching value.
@JMAKI_NS@.listbox.Widget.prototype._selectCallback = function(payload) {
    if (payload && payload.value != null) {
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

// Callback function to handle Woodstock listbox onSelect event.
// Handle any stacked application onChange event function first.
// Event payload contains:
//    Listbox widget identifier
// Publish jMaki onSelect event.
@JMAKI_NS@.listbox.Widget.prototype._selectedCallback = function() {

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
	    jmaki.processActions({
		action: "onSelect",
		targetId: val,
		topic: this._publish + "/onSelect",
		type: "onSelect",
		value: true,
		widgetId: this._wid
	    });
	}
    }
};

// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: [<data model>]}
// Update listbox widget to replace options array.
@JMAKI_NS@.listbox.Widget.prototype._valuesCallback = function(payload) {
    if (payload && payload.value instanceof Array) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    widget.setProps({options: payload.value});
	}
    }
};
