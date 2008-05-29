jmaki.namespace("@JMAKI_NS@.textArea");

/*
 * jMaki wrapper for Woodstock textArea widget.
 *
 * We expect the 'value' property in the wargs object and/or
 * the 'data' property in the widget.json to be a JSON object
 * containing valid Woodstock properties.  No property conversion
 * is done when creating the Woodstock widget from the jMaki
 * value/data properties.
 */
@JMAKI_NS@.textArea.Widget = function(wargs) {

    // Turn on jMaki debugging...
    // jmaki.debug = true;
    // jmaki.log("Entering woodstock textArea Widget function...");

    var self = this;
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
	props = {};
	props.value = "";
        props.readOnly = false;
        props.required = false;
	props.cols=4;
	props.rows=32;
	props.autoSave=0;
    }

    // Add our widget id and type.
    props.id = id;
    props.widgetType = "textArea";

    // Create the Woodstock textArea widget.
    @JS_NS@.widget.common.createWidget(span_id, props);

};
