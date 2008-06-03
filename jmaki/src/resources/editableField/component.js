jmaki.namespace("@JMAKI_NS@.editableField");

/*
 * jMaki wrapper for Woodstock editableField widget.
 *
 * We expect the 'value' property in the wargs object and/or
 * the 'data' property in the widget.json to be a JSON object
 * containing valid Woodstock properties.  No property conversion
 * is done when creating the Woodstock widget from the jMaki
 * value/data properties.
 */
@JMAKI_NS@.editableField.Widget = function(wargs) {

    // Turn on jMaki debugging...
    // jmaki.debug = true;
    // jmaki.log("Entering woodstock editableField Widget function...");

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
    var props;
    if (wargs.value) {
	props = wargs.value;
    } else {
	props = {};
	props.value = "";
        props.readOnly = false;
        props.required = false;
        props.submitForm = false;
        props.autoValidate = false;
    }

    // Add our widget id and type.
    props.id = id;
    props.widgetType = "editableField";

    // Create the Woodstock editableField widget.
    @JS_NS@.widget.common.createWidget(span_id, props);

};
