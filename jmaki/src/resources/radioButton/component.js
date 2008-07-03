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

jmaki.namespace("@JMAKI_NS@.radioButton");

/*
 * jMaki wrapper for Woodstock radioButton widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data for a simple radioButton model:
 *               {label: {value: <label_text>},
 *                image: {src: <path_to_image>},
 *                value: <radioButton_value>,
 *                name: <radioButton_name>}
 *            The label or image is optional, but at least one
 *            must be specified.  A name must be specified.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the radioButton widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/radioButton".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/radioButton".
 * args.id:   User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * setValues  To reset the "value" data in the widget.
 *
 * This widget publishes the following jMaki events:
 *
 * onSelect   To indicate the option that was selected in the widget.
 */
@JMAKI_NS@.radioButton.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/radioButton"];
    this._publish = "/@JS_NAME@/radioButton";
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

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.radioButton.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock radioButton.
    var props = {};
    if (wargs.args != null) {
	this._mapProperties(props, wargs.args);
    }
    if (wargs.value != null) {
	this._mapProperties(props, wargs.value);
    }

    // Subscribe to jMaki events
    for (var i = 0; i < this._subscribe.length; i++) {
        var s1 = jmaki.subscribe(this._subscribe[i] + "/setValues", 
            @JS_NS@.widget.common._hitch(this, "_valuesCallback"));
        this._subscriptions.push(s1);
    }

    // If application sets onChange, stack function for handling later.
    if (props.onChange) {
        this._onChange = (typeof props.onChange == 'string')
            ? new Function(props.onChange) : props.onChange;
    }

    props.id = this._wid;
    props.widgetType = "radioButton";

    // Hook the radioButton widget onChange UI event so we can
    // publish the jMaki onSelect topic.
    props.onChange = @JS_NS@.widget.common._hitch(this, "_selectedCallback");

    // Create the Woodstock radioButton widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
};

// Destroy...
// Unsubscribe from jMaki events
@JMAKI_NS@.radioButton.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
        for (var i = 0; i < this._subscriptions.length; i++) {
            jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.radioButton.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object of wrapper properties to be mapped
@JMAKI_NS@.radioButton.Widget.prototype._mapProperties = function(props, value) {

    // We expect to get a "label", "image", or both in format:
    //   label: "string" or label: {value: <text_label>}
    //   image: {src: <image_path>, border: <n>}
    // If an image, fix it up to be a compliant image object value.
    // If neither present, add a default label.
    // Make sure widget has a name property.
    // Other properties are passed through unchanged.
    @JS_NS@._base.proto._extend(props, value);
    if (props.image) {
        props.image.id = this._wid + "_image";
        props.image.widgetType = "image";
    }
    if (typeof props.label == "string") {
        props.label = {value: props.label};
    }
    if (typeof props.label == "undefined" &&
        typeof props.image == "undefined") {
        props.label = {value: "RadioButton"};
    }
    if (typeof props.name == "undefined") {
	props.name = props.id + "_rb";
    }

}

// Callback function to handle Woodstock radioButton onSelect event.
// Handle any stacked application onChange event function first.
// Publish jMaki onSelect event with payload:
//   {widgetId: <_wid>,
//    topic: {type: "onSelect", targetId: <value>, value: <checked>}
@JMAKI_NS@.radioButton.Widget.prototype._selectedCallback = function() {

    var result = true;
    if (typeof this._onChange == "function") {
        result = this._onChange();
    }
    if (result != null && result == false) {
        return result;
    }

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
        var props = widget.getProps();
        var val = props.value;
        var ckd = props.checked;
        jmaki.processActions({
            action: "onSelect",
            targetId: val,
            topic: this._publish + "/onSelect",
            type: "onSelect",
            value: ckd,
            widgetId: this._wid
        });
    }

};

// Callback function to handle jMaki setValues topic.
// Event payload contains:
//    {value: {<widget_properties>}}
// Update radioButton widget to replace data properties.
@JMAKI_NS@.radioButton.Widget.prototype._valuesCallback = function(payload) {
    if (payload) {
        var widget = @JS_NS@.widget.common.getWidget(this._wid);
        if (widget) {
            if (payload.value != null) {
		var props = {};
		this._mapProperties(props, payload.value);
                widget.setProps(props);
            }
	}
    }
};
