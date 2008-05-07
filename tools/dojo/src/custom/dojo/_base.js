dojo.provide("dojo._base");
dojo.require("dojo._base.lang"); // Woodstock: Unused.
dojo.require("dojo._base.declare");
dojo.require("dojo._base.connect");
//dojo.require("dojo._base.Deferred"); // Woodstock: Unused.
//dojo.require("dojo._base.json"); // Woodstock: Unused.
dojo.require("dojo._base.array"); // Woodstock: Unused.
//dojo.require("dojo._base.Color"); // Woodstock: Unused.
dojo.requireIf(dojo.isBrowser, "dojo._base.window");
dojo.requireIf(dojo.isBrowser, "dojo._base.event");
dojo.requireIf(dojo.isBrowser, "dojo._base.html");
//dojo.requireIf(dojo.isBrowser, "dojo._base.NodeList"); // Woodstock: Unused.
dojo.requireIf(dojo.isBrowser, "dojo._base.query"); // Woodstock: Unused.
dojo.requireIf(dojo.isBrowser, "dojo._base.xhr"); // Woodstock: Unused.
//dojo.requireIf(dojo.isBrowser, "dojo._base.fx"); // Woodstock: Unused.

(function(){
	if(djConfig.require){
		for(var x=0; x<djConfig.require.length; x++){
			dojo["require"](djConfig.require[x]);
		}
	}
})();
