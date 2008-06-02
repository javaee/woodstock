jmaki.namespace("@JMAKI_NS@.button");

/*
 * jMaki wrapper for Woodstock Button widget.
 *
 * We expect the 'value' property in the wargs object and/or
 * the 'data' property in the widget.json to be a JSON object
 * containing valid Woodstock properties.  No property conversion
 * is done when creating the Woodstock widget from the jMaki
 * value/data properties.
 */
@JMAKI_NS@.button.Widget = function(wargs) {

    // Turn on jMaki debugging...
    // jmaki.debug = true;
    // jmaki.log("Entering woodstock button Widget function...");

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

    // Get the jMaki properties for a Woodstock button.
    var props;
    if (wargs.value) {
	props = wargs.value;
    } else {
	props = {};
	props.value = "Button";
	props.widgetType = "button";
	props.primary = true;
	props.submitForm = false;
    }

    // Add our widget id and type, adjust type for form submit.
    props.id = id;
    props.widgetType = "button";
    if (! props.submitForm) {
	props.widgetType="resetButton";
	if (typeof props.onClick == "undefined") {
	    props.onClick="return false;";
	}
    }

    // Create the Woodstock button widget.
    @JS_NS@.widget.common.createWidget(span_id, props);

};
