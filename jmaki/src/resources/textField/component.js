jmaki.namespace("@JMAKI_NS@.textField");

/*
 * jMaki wrapper for Woodstock Button widget.
 *
 * We expect the 'value' property in the wargs object and/or
 * the 'data' property in the widget.json to be a JSON object
 * containing valid Woodstock properties.  No property conversion
 * is done when creating the Woodstock widget from the jMaki
 * value/data properties.
 *
 * This wrapper subscribes to the "/woodstock/textField" jMaki event.
 * The event callback will refresh the widget properties from the
 * value/data property in the event payload JSON object.
 *
 * This wrapper assumes the Woodstock library bootstrap has been
 * included in the page containing this textField wrapper widget.
 */
@JMAKI_NS@.textField.Widget = function(wargs) {

    // Turn on jMaki debugging...
    jmaki.debug = true;
    jmaki.log("Entering woodstock textField Widget function...");

    var self = this;
    var refreshTopic = "/@JS_NAME@/textField/refresh";
    var publishTopic;
    var id = wargs.id;
    if (typeof id == "undefined") {
	id = wargs.uuid;
    }
    var span_id = wargs.uuid + "_span";
    if (wargs.publish) {
	// User supplied a specific topic to publish.
	publishTopic = wargs.publish;
    }

    // Get the jMaki properties for a Woodstock text field.
    // If necessary, make an XHR call to read widget.json.
    var props;
    if (wargs.value) {
	props = wargs.value;
    } else {
	var url = wargs.widgetDir + "/widget.json";
	var callback = function(response) {
	    var obj = @JS_NS@.json.parse(response.responseText);
	    props = obj.data;
	}
	@JS_NS@.xhr.get({url: url, onReady: callback});
    }

    // If all else fails, default to very simple text field.
    if (props == null) {
	props = {};
	props.value = "";
        props.readOnly = false;
        props.required = false;
        props.submitForm = false;
        props.autoValidate = false;
        props.autoComplete = false;
    }

    // Add our widget id and type.
    props.id = id;
    props.widgetType = "textField";

    // Create the Woodstock textField widget.
    @JS_NS@.widget.common.createWidget(span_id, props);

    // Subscribe to our refresh event.  This allows jMaki glue
    // events to refresh our underlying Woodstock widget.
//    jmaki.subscribe(refreshTopic, refreshHandler);

    // ============================================================

    // Refresh event handler for jMaki wrapper of Woodstock textField.
    //
    // The payload argument must be a JS object containing:
    //     {id: "widget_id", value: { ... data ...}}
    // The data is a JS object literal containing widget properties
    // and their updated values suitable for a setProps method.
    //
    // Note: This is very generic!  This will work for all widgets.

    refreshHandler: function(payload) {

        var id = payload.id;
	if (id) {
            var props = payload.value;
	    if (props) {
        	var widget = @JS_NS@.widget.common.getWidget(id);
        	if (widget) {
		    widget.setProps(props);
		}
	    }
        }

    };
};
