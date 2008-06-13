/*
 * These are some predefined glue listeners that you can
 *  modify to fit your application.
 *
 * This file should not placed in the /resources directory of your application
 * as that directory is for jmaki specific resources.
 */

// uncomment to turn on the logger
jmaki.debug = false;
// uncomment to show publish/subscribe messages
jmaki.debugGlue = false;

// map topic dojo/fisheye to fisheye handler
jmaki.subscribe("/dojo/fisheye*", function(args) {
    jmaki.log("glue.js : fisheye event");
 });


// map topics ending with  /onSave to the handler
jmaki.subscribe("*onSave", function(args) {
    jmaki.log("glue.js : onSave request from: " + args.id + " value=" + args.value);
});

// map topics ending with  /onSave to the handler
jmaki.subscribe("*onSelect", function(args) {
    jmaki.log("glue.js : onSelect request from: " + args.widgetId);
});

// map topics ending with  /onSave to the handler
jmaki.subscribe("*onClick", function(args) {
    jmaki.log("glue.js : onClick request from: " + args.widgetId);
});

