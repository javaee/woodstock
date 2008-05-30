/*
	Copyright (c) 2004-2007, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/book/dojo-book-0-9/introduction/licensing
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

if(typeof @JS_NS@._dojo == "undefined"){

// TODOC: HOW TO DOC THE BELOW?
// @global: djConfig
// summary:
//		Application code can set the global 'djConfig' prior to loading
//		the library to override certain global settings for how dojo works.
// description:  The variables that can be set are as follows:
//			- isDebug: false
//			- libraryScriptUri: ""
//			- locale: undefined
//			- extraLocale: undefined
//			- preventBackButtonFix: true
// note:
//		'djConfig' does not exist under '@JS_NS@._dojo.*' so that it can be set before the
//		'dojo' variable exists.
// note:
//		Setting any of these variables *after* the library has loaded does
//		nothing at all.

(function(){
	// make sure djConfig is defined
	if(typeof this.@JS_NS@._base.config._djConfig == "undefined"){ // Woodstock: Modified for custom name space.
		this.@JS_NS@._base.config._djConfig = {};
	}

	// firebug stubs
	if((!this["console"])||(!console["firebug"])){
		this.console = {};
	}

	var cn = [
		"assert", "count", "debug", "dir", "dirxml", "error", "group",
		"groupEnd", "info", "log", "profile", "profileEnd", "time",
		"timeEnd", "trace", "warn"
	];
	var i=0, tn;
	while((tn=cn[i++])){
		if(!console[tn]){
			console[tn] = function(){};
		}
	}

	//TODOC:  HOW TO DOC THIS?
	// dojo is the root variable of (almost all) our public symbols -- make sure it is defined.	
	if(typeof this.@JS_NS@._dojo == "undefined"){ // Woodstock: Modified for custom name space.
		this.@JS_NS@._dojo = {};
	}

	var d = @JS_NS@._dojo;

	// summary:
	//		return the current global context object
	//		(e.g., the window object in a browser).
	// description:
	//		Refer to '@JS_NS@._dojo.global' rather than referring to window to ensure your
	//		code runs correctly in contexts other than web browsers (eg: Rhino on a server).
	@JS_NS@._dojo.global = this;

	var _config =/*===== @JS_NS@._base.config._djConfig = =====*/{
		isDebug: false,
		libraryScriptUri: "",
		preventBackButtonFix: true,
		delayMozLoadingFix: false
	};

	for(var option in _config){
		if(typeof @JS_NS@._base.config._djConfig[option] == "undefined"){
			@JS_NS@._base.config._djConfig[option] = _config[option];
		}
	}

	var _platforms = ["Browser", "Rhino", "Spidermonkey", "Mobile"];
	var t;
	while(t=_platforms.shift()){
		d["is"+t] = false;
	}

	// Override locale setting, if specified
	@JS_NS@._dojo.locale = @JS_NS@._base.config._djConfig.locale;

	//TODOC:  HOW TO DOC THIS?
	@JS_NS@._dojo.version = {
		// summary: version number of this instance of @JS_NS@._dojo.
		major: 1, minor: 0, patch: 2, flag: "",
		revision: Number("$Rev: 11832 $".match(/[0-9]+/)[0]),
		toString: function(){
			with(d.version){
				return major + "." + minor + "." + patch + flag + " (" + revision + ")";	// String
			}
		}
	}

	// Register with the OpenAjax hub
	if(typeof OpenAjax != "undefined"){
		OpenAjax.hub.registerLibrary("@JS_NS@._dojo", "http://dojotoolkit.org", d.version.toString());
	}

	@JS_NS@._dojo._mixin = function(/*Object*/ obj, /*Object*/ props){
		// summary:
		//		Adds all properties and methods of props to obj. This addition is
		//		"prototype extension safe", so that instances of objects will not
		//		pass along prototype defaults.
		var tobj = {};
		for(var x in props){
			// the "tobj" condition avoid copying properties in "props"
			// inherited from Object.prototype.  For example, if obj has a custom
			// toString() method, don't overwrite it with the toString() method
			// that props inherited from Object.prototype
			if(tobj[x] === undefined || tobj[x] != props[x]){
				obj[x] = props[x];
			}
		}
		// IE doesn't recognize custom toStrings in for..in
		if(d["isIE"] && props){
			var p = props.toString;
			if(typeof p == "function" && p != obj.toString && p != tobj.toString &&
				p != "\nfunction toString() {\n    [native code]\n}\n"){
					obj.toString = props.toString;
			}
		}
		return obj; // Object
	}

	@JS_NS@._dojo.mixin = function(/*Object*/obj, /*Object...*/props){
		// summary:	Adds all properties and methods of props to obj. 
		for(var i=1, l=arguments.length; i<l; i++){
			d._mixin(obj, arguments[i]);
		}
		return obj; // Object
	}

	@JS_NS@._dojo._getProp = function(/*Array*/parts, /*Boolean*/create, /*Object*/context){
		var obj=context||d.global;
		for(var i=0, p; obj&&(p=parts[i]); i++){
			obj = (p in obj ? obj[p] : (create ? obj[p]={} : undefined));
		}
		return obj; // mixed
	}

	@JS_NS@._dojo.setObject = function(/*String*/name, /*mixed*/value, /*Object*/context){
		// summary: 
		//		Set a property from a dot-separated string, such as "A.B.C"
		//	description: 
		//		Useful for longer api chains where you have to test each object in
		//		the chain, or when you have an object reference in string format.
		//		Objects are created as needed along 'path'.
		//	name: 	
		//		Path to a property, in the form "A.B.C".
		//	context:
		//		Optional. Object to use as root of path. Defaults to
		//		'@JS_NS@._dojo.global'. Null may be passed.
		var parts=name.split("."), p=parts.pop(), obj=d._getProp(parts, true, context);
		return (obj && p ? (obj[p]=value) : undefined); // mixed
	}

	@JS_NS@._dojo.getObject = function(/*String*/name, /*Boolean*/create, /*Object*/context){
		// summary: 
		//		Get a property from a dot-separated string, such as "A.B.C"
		//	description: 
		//		Useful for longer api chains where you have to test each object in
		//		the chain, or when you have an object reference in string format.
		//	name: 	
		//		Path to an property, in the form "A.B.C".
		//	context:
		//		Optional. Object to use as root of path. Defaults to
		//		'@JS_NS@._dojo.global'. Null may be passed.
		//	create: 
		//		Optional. If true, Objects will be created at any point along the
		//		'path' that is undefined.
		return d._getProp(name.split("."), create, context); // mixed
	}

	@JS_NS@._dojo.exists = function(/*String*/name, /*Object?*/obj){
		// summary: 
		//		determine if an object supports a given method
		// description: 
		//		useful for longer api chains where you have to test each object in
		//		the chain
		// name: 	
		//		Path to an object, in the form "A.B.C".
		// obj:
		//		Object to use as root of path. Defaults to
		//		'@JS_NS@._dojo.global'. Null may be passed.
		return !!d.getObject(name, false, obj); // Boolean
	}


	@JS_NS@._dojo["eval"] = function(/*String*/ scriptFragment){
		// summary: 
		//		Perform an evaluation in the global scope.  Use this rather than
		//		calling 'eval()' directly.
		// description: 
		//		Placed in a separate function to minimize size of trapped
		//		evaluation context.
		// note:
		//	 - JSC eval() takes an optional second argument which can be 'unsafe'.
		//	 - Mozilla/SpiderMonkey eval() takes an optional second argument which is the
		//  	 scope object for new symbols.

		// FIXME: investigate Joseph Smarr's technique for IE:
		//		http://josephsmarr.com/2007/01/31/fixing-eval-to-use-global-scope-in-ie/
		//	see also:
		// 		http://trac.dojotoolkit.org/ticket/744
		return d.global.eval ? d.global.eval(scriptFragment) : eval(scriptFragment); 	// mixed
	}

	/*=====
		@JS_NS@._dojo.deprecated = function(behaviour, extra, removal){
			//	summary: 
			//		Log a debug message to indicate that a behavior has been
			//		deprecated.
			//	behaviour: String
			//		The API or behavior being deprecated. Usually in the form
			//		of "myApp.someFunction()".
			//	extra: String?
			//		Text to append to the message. Often provides advice on a
			//		new function or facility to achieve the same goal during
			//		the deprecation period.
			//	removal: String?
			//		Text to indicate when in the future the behavior will be
			//		removed. Usually a version number.
			//	example:
			//	|	@JS_NS@._dojo.deprecated("myApp.getTemp()", "use myApp.getLocaleTemp() instead", "1.0");
		}

		@JS_NS@._dojo.experimental = function(moduleName, extra){
			//	summary: Marks code as experimental.
			//	description: 
			//	 	This can be used to mark a function, file, or module as
			//	 	experimental.  Experimental code is not ready to be used, and the
			//	 	APIs are subject to change without notice.  Experimental code may be
			//	 	completed deleted without going through the normal deprecation
			//	 	process.
			//	moduleName: String
			//	 	The name of a module, or the name of a module file or a specific
			//	 	function
			//	extra: String?
			//	 	some additional message for the user
			//	example:
			//	|	@JS_NS@._dojo.experimental("@JS_NS@._dojo.data.Result");
			//	example:
			//	|	@JS_NS@._dojo.experimental("@JS_NS@._dojo.weather.toKelvin()", "PENDING approval from NOAA");
		}
	=====*/

	//Real functions declared in @JS_NS@._dojo._firebug.firebug.
	d.deprecated = d.experimental = function(){};

})();
// vim:ai:ts=4:noet

/*
 * loader.js - A bootstrap module.  Runs before the hostenv_*.js file. Contains
 * all of the package loading methods.
 */

(function(){
	var d = @JS_NS@._dojo;

        // Woodstock: Path must not be modified by custom build.
        var prefix = "_do" + "jo";

	@JS_NS@._dojo.mixin(@JS_NS@._dojo, {
		_loadedModules: {},
		_inFlightCount: 0,
		_hasResource: {},

		// FIXME: it should be possible to pull module prefixes in from djConfig               
		_modulePrefixes: {
			// Woodstock: Added quotes around keys to support dot syntax.
			"@JS_NS@._dojo": {name: "@JS_NS@._dojo", value: prefix},
			"doh": {name: "doh", value: prefix + "/util/doh"},
			"tests": {name: "tests", value: prefix + "/tests"}
		},

		_moduleHasPrefix: function(/*String*/module){
			// summary: checks to see if module has been established
			var mp = this._modulePrefixes;
			return !!(mp[module] && mp[module].value); // Boolean
		},

		_getModulePrefix: function(/*String*/module){
			// summary: gets the prefix associated with module
			var mp = this._modulePrefixes;
			if(this._moduleHasPrefix(module)){
				return mp[module].value; // String
			}
			return module; // String
		},

		_loadedUrls: [],

		//WARNING: 
		//		This variable is referenced by packages outside of bootstrap:
		//		FloatingPane.js and undo/browser.js
		_postLoad: false,
		
		//Egad! Lots of test files push on this directly instead of using @JS_NS@._dojo.addOnLoad.
		_loaders: [],
		_unloaders: [],
		_loadNotifying: false
	});


		@JS_NS@._dojo._loadPath = function(/*String*/relpath, /*String?*/module, /*Function?*/cb){
		// 	summary:
		//		Load a Javascript module given a relative path
		//
		//	description:
		//		Loads and interprets the script located at relpath, which is
		//		relative to the script root directory.  If the script is found but
		//		its interpretation causes a runtime exception, that exception is
		//		not caught by us, so the caller will see it.  We return a true
		//		value if and only if the script is found.
		//
		// relpath: 
		//		A relative path to a script (no leading '/', and typically ending
		//		in '.js').
		// module: 
		//		A module whose existance to check for after loading a path.  Can be
		//		used to determine success or failure of the load.
		// cb: 
		//		a callback function to pass the result of evaluating the script

		var uri = (((relpath.charAt(0) == '/' || relpath.match(/^\w+:/))) ? "" : this.baseUrl) + relpath;
		if(@JS_NS@._base.config._djConfig.cacheBust && d.isBrowser){
			uri += "?" + String(@JS_NS@._base.config._djConfig.cacheBust).replace(/\W+/g,"");
		}
		try{
			return !module ? this._loadUri(uri, cb) : this._loadUriAndCheck(uri, module, cb); // Boolean
		}catch(e){
			console.debug(e);
			return false; // Boolean
		}
	}

	@JS_NS@._dojo._loadUri = function(/*String (URL)*/uri, /*Function?*/cb){
		//	summary:
		//		Loads JavaScript from a URI
		//	description:
		//		Reads the contents of the URI, and evaluates the contents.  This is
		//		used to load modules as well as resource bundles. Returns true if
		//		it succeeded. Returns false if the URI reading failed.  Throws if
		//		the evaluation throws.
		//	uri: a uri which points at the script to be loaded
		//	cb: 
		//		a callback function to process the result of evaluating the script
		//		as an expression, typically used by the resource bundle loader to
		//		load JSON-style resources

		if(this._loadedUrls[uri]){
			return true; // Boolean
		}
		var contents = this._getText(uri, true);
		if(!contents){ return false; } // Boolean
		this._loadedUrls[uri] = true;
		this._loadedUrls.push(uri);
		if(cb){ contents = '('+contents+')'; }
		var value = d["eval"](contents+"\r\n//@ sourceURL="+uri);
		if(cb){ cb(value); }
		return true; // Boolean
	}
	
	// FIXME: probably need to add logging to this method
	@JS_NS@._dojo._loadUriAndCheck = function(/*String (URL)*/uri, /*String*/moduleName, /*Function?*/cb){
		// summary: calls loadUri then findModule and returns true if both succeed
		var ok = false;
		try{
			ok = this._loadUri(uri, cb);
		}catch(e){
			console.debug("failed loading " + uri + " with error: " + e);
		}
		return Boolean(ok && this._loadedModules[moduleName]); // Boolean
	}

	@JS_NS@._dojo.loaded = function(){
		// summary:
		//		signal fired when initial environment and package loading is
		//		complete. You may use @JS_NS@._dojo.addOnLoad() or @JS_NS@._dojo.connect() to
		//		this method in order to handle initialization tasks that
		//		require the environment to be initialized. In a browser host,
		//		declarative widgets will be constructed when this function
		//		finishes runing.
		this._loadNotifying = true;
		this._postLoad = true;
		var mll = this._loaders;
		
		//Clear listeners so new ones can be added
		//For other xdomain package loads after the initial load.
		this._loaders = [];

		for(var x=0; x<mll.length; x++){
			mll[x]();
		}

		this._loadNotifying = false;
		
		//Make sure nothing else got added to the onload queue
		//after this first run. If something did, and we are not waiting for any
		//more inflight resources, run again.
		if(d._postLoad && d._inFlightCount == 0 && this._loaders.length > 0){
			d._callLoaded();
		}
	}

	@JS_NS@._dojo.unloaded = function(){
		// summary:
		//		signal fired by impending environment destruction. You may use
		//		@JS_NS@._dojo.addOnUnload() or @JS_NS@._dojo.connect() to this method to perform
		//		page/application cleanup methods.
		var mll = this._unloaders;
		while(mll.length){
			(mll.pop())();
		}
	}

	@JS_NS@._dojo.addOnLoad = function(/*Object?*/obj, /*String|Function*/functionName){
		// summary:
		//		Registers a function to be triggered after the DOM has finished
		//		loading and widgets declared in markup have been instantiated.
		//		Images and CSS files may or may not have finished downloading when
		//		the specified function is called.  (Note that widgets' CSS and HTML
		//		code is guaranteed to be downloaded before said widgets are
		//		instantiated.)
		// example:
		//	|	@JS_NS@._dojo.addOnLoad(functionPointer);
		//	|	@JS_NS@._dojo.addOnLoad(object, "functionName");
		if(arguments.length == 1){
			d._loaders.push(obj);
		}else if(arguments.length > 1){
			d._loaders.push(function(){
				obj[functionName]();
			});
		}

		//Added for xdomain loading. @JS_NS@._dojo.addOnLoad is used to
		//indicate callbacks after doing some @JS_NS@._dojo.require() statements.
		//In the xdomain case, if all the requires are loaded (after initial
		//page load), then immediately call any listeners.
		if(d._postLoad && d._inFlightCount == 0 && !d._loadNotifying){
			d._callLoaded();
		}
	}

	@JS_NS@._dojo.addOnUnload = function(/*Object?*/obj, /*String|Function?*/functionName){
		// summary: registers a function to be triggered when the page unloads
		// example:
		//	|	@JS_NS@._dojo.addOnUnload(functionPointer)
		//	|	@JS_NS@._dojo.addOnUnload(object, "functionName")
		if(arguments.length == 1){
			d._unloaders.push(obj);
		}else if(arguments.length > 1){
			d._unloaders.push(function(){
				obj[functionName]();
			});
		}
	}

	@JS_NS@._dojo._modulesLoaded = function(){
		if(d._postLoad){ return; }
		if(d._inFlightCount > 0){ 
			console.debug("files still in flight!");
			return;
		}
		d._callLoaded();
	}

	@JS_NS@._dojo._callLoaded = function(){
		//The "object" check is for IE, and the other opera check fixes an issue
		//in Opera where it could not find the body element in some widget test cases.
		//For 0.9, maybe route all browsers through the setTimeout (need protection
		//still for non-browser environments though). This might also help the issue with
		//FF 2.0 and freezing issues where we try to do sync xhr while background css images
		//are being loaded (trac #2572)? Consider for 0.9.
		if(typeof setTimeout == "object" || (@JS_NS@._base.config._djConfig["useXDomain"] && d.isOpera)){
			setTimeout("@JS_NS@._dojo.loaded();", 0);
		}else{
			d.loaded();
		}
	}

	@JS_NS@._dojo._getModuleSymbols = function(/*String*/modulename){
		// summary:
		//		Converts a module name in dotted JS notation to an array
		//		representing the path in the source tree
		var syms = modulename.split(".");
		for(var i = syms.length; i>0; i--){
			var parentModule = syms.slice(0, i).join(".");
			if((i==1) && !this._moduleHasPrefix(parentModule)){		
				// Support default module directory (sibling of @JS_NS@._dojo) for top-level modules 
				syms[0] = "../" + syms[0];
			}else{
				var parentModulePath = this._getModulePrefix(parentModule);
				if(parentModulePath != parentModule){
					syms.splice(0, i, parentModulePath);
					break;
				}
			}
		}
		// console.debug(syms);
		return syms; // Array
	}

	@JS_NS@._dojo._global_omit_module_check = false;

	@JS_NS@._dojo._loadModule = @JS_NS@._dojo.require = function(/*String*/moduleName, /*Boolean?*/omitModuleCheck){
		//	summary:
		//		loads a Javascript module from the appropriate URI
		//	moduleName: String
		//	omitModuleCheck: Boolean?
		//	description:
		//		_loadModule("A.B") first checks to see if symbol A.B is defined. If
		//		it is, it is simply returned (nothing to do).
		//	
		//		If it is not defined, it will look for "A/B.js" in the script root
		//		directory.
		//	
		//		It throws if it cannot find a file to load, or if the symbol A.B is
		//		not defined after loading.
		//	
		//		It returns the object A.B.
		//	
		//		This does nothing about importing symbols into the current package.
		//		It is presumed that the caller will take care of that. For example,
		//		to import all symbols:
		//	
		//		|	with (@JS_NS@._dojo._loadModule("A.B")) {
		//		|		...
		//		|	}
		//	
		//		And to import just the leaf symbol:
		//	
		//		|	var B = @JS_NS@._dojo._loadModule("A.B");
		//	   	|	...
		//	returns: the required namespace object
		omitModuleCheck = this._global_omit_module_check || omitModuleCheck;
		var module = this._loadedModules[moduleName];
		if(module){
			return module;
		}

		// convert periods to slashes
		var relpath = this._getModuleSymbols(moduleName).join("/") + '.js';

		var modArg = (!omitModuleCheck) ? moduleName : null;
		var ok = this._loadPath(relpath, modArg);

		if((!ok)&&(!omitModuleCheck)){
			throw new Error("Could not load '" + moduleName + "'; last tried '" + relpath + "'");
		}

		// check that the symbol was defined
		// Don't bother if we're doing xdomain (asynchronous) loading.
		if((!omitModuleCheck)&&(!this["_isXDomain"])){
			// pass in false so we can give better error
			module = this._loadedModules[moduleName];
			if(!module){
				throw new Error("symbol '" + moduleName + "' is not defined after loading '" + relpath + "'"); 
			}
		}

		return module;
	}

	@JS_NS@._dojo.provide = function(/*String*/ resourceName){
		//	summary:
		//		Each javascript source file must have (exactly) one @JS_NS@._dojo.provide()
		//		call at the top of the file, corresponding to the file name.
		//		For example, js/dojo/foo.js must have @JS_NS@._dojo.provide("@JS_NS@._dojo.foo"); at the
		//		top of the file.
		//	description:
		//		Each javascript source file is called a resource.  When a resource
		//		is loaded by the browser, @JS_NS@._dojo.provide() registers that it has been
		//		loaded.
		//	
		//		For backwards compatibility reasons, in addition to registering the
		//		resource, @JS_NS@._dojo.provide() also ensures that the javascript object
		//		for the module exists.  For example,
		//		@JS_NS@._dojo.provide("@JS_NS@._dojo.io.cometd"), in addition to registering that
		//		cometd.js is a resource for the @JS_NS@._dojo.iomodule, will ensure that
		//		the @JS_NS@._dojo.io javascript object exists, so that calls like
		//		@JS_NS@._dojo.io.foo = function(){ ... } don't fail.
		//
		//		In the case of a build (or in the future, a rollup), where multiple
		//		javascript source files are combined into one bigger file (similar
		//		to a .lib or .jar file), that file will contain multiple
		//		@JS_NS@._dojo.provide() calls, to note that it includes multiple resources.

		//Make sure we have a string.
		resourceName = resourceName + "";
		return (d._loadedModules[resourceName] = d.getObject(resourceName, true)); // Object
	}

	//Start of old bootstrap2:

	@JS_NS@._dojo.platformRequire = function(/*Object containing Arrays*/modMap){
		//	description:
		//		This method taks a "map" of arrays which one can use to optionally
		//		load dojo modules. The map is indexed by the possible
		//		@JS_NS@._dojo.name_ values, with two additional values: "default"
		//		and "common". The items in the "default" array will be loaded if
		//		none of the other items have been choosen based on the
		//		hostenv.name_ item. The items in the "common" array will _always_
		//		be loaded, regardless of which list is chosen.  Here's how it's
		//		normally called:
		//	
		//		|	@JS_NS@._dojo.platformRequire({
		//		|		// an example that passes multiple args to _loadModule()
		//		|		browser: [
		//		|			["foo.bar.baz", true, true], 
		//		|			"foo.sample",
		//		|			"foo.test,
		//		|		],
		//		|		default: [ "foo.sample.*" ],
		//		|		common: [ "really.important.module.*" ]
		//		|	});

		// FIXME: @JS_NS@._dojo.name_ no longer works!!

		var common = modMap["common"]||[];
		var result = common.concat(modMap[d._name]||modMap["default"]||[]);

		for(var x=0; x<result.length; x++){
			var curr = result[x];
			if(curr.constructor == Array){
				d._loadModule.apply(d, curr);
			}else{
				d._loadModule(curr);
			}
		}
	}


	@JS_NS@._dojo.requireIf = function(/*Boolean*/ condition, /*String*/ resourceName){
		// summary:
		//		If the condition is true then call @JS_NS@._dojo.require() for the specified
		//		resource
		if(condition === true){
			// FIXME: why do we support chained require()'s here? does the build system?
			var args = [];
			for(var i = 1; i < arguments.length; i++){ 
				args.push(arguments[i]);
			}
			d.require.apply(d, args);
		}
	}

	@JS_NS@._dojo.requireAfterIf = d.requireIf;

	@JS_NS@._dojo.registerModulePath = function(/*String*/module, /*String*/prefix){
		//	summary: 
		//		maps a module name to a path
		//	description: 
		//		An unregistered module is given the default path of ../<module>,
		//		relative to Dojo root. For example, module acme is mapped to
		//		../acme.  If you want to use a different module name, use
		//		@JS_NS@._dojo.registerModulePath. 
		d._modulePrefixes[module] = { name: module, value: prefix };
	}

	@JS_NS@._dojo.requireLocalization = function(/*String*/moduleName, /*String*/bundleName, /*String?*/locale, /*String?*/availableFlatLocales){
		// summary:
		//		Declares translated resources and loads them if necessary, in the
		//		same style as @JS_NS@._dojo.require.  Contents of the resource bundle are
		//		typically strings, but may be any name/value pair, represented in
		//		JSON format.  See also @JS_NS@._dojo.i18n.getLocalization.
		// moduleName: 
		//		name of the package containing the "nls" directory in which the
		//		bundle is found
		// bundleName: 
		//		bundle name, i.e. the filename without the '.js' suffix
		// locale: 
		//		the locale to load (optional)  By default, the browser's user
		//		locale as defined by @JS_NS@._dojo.locale
		// availableFlatLocales: 
		//		A comma-separated list of the available, flattened locales for this
		//		bundle. This argument should only be set by the build process.
		// description:
		//		Load translated resource bundles provided underneath the "nls"
		//		directory within a package.  Translated resources may be located in
		//		different packages throughout the source tree.  For example, a
		//		particular widget may define one or more resource bundles,
		//		structured in a program as follows, where moduleName is
		//		mycode.mywidget and bundleNames available include bundleone and
		//		bundletwo:
		//
		//			...
		//			mycode/
		//			 mywidget/
		//			  nls/
		//			   bundleone.js (the fallback translation, English in this example)
		//			   bundletwo.js (also a fallback translation)
		//			   de/
		//			    bundleone.js
		//			    bundletwo.js
		//			   de-at/
		//			    bundleone.js
		//			   en/
		//			    (empty; use the fallback translation)
		//			   en-us/
		//			    bundleone.js
		//			   en-gb/
		//			    bundleone.js
		//			   es/
		//			    bundleone.js
		//			    bundletwo.js
		//			  ...etc
		//			...
		//
		//		Each directory is named for a locale as specified by RFC 3066,
		//		(http://www.ietf.org/rfc/rfc3066.txt), normalized in lowercase.
		//		Note that the two bundles in the example do not define all the same
		//		variants.  For a given locale, bundles will be loaded for that
		//		locale and all more general locales above it, including a fallback
		//		at the root directory.  For example, a declaration for the "de-at"
		//		locale will first load nls/de-at/bundleone.js, then
		//		nls/de/bundleone.js and finally nls/bundleone.js.  The data will be
		//		flattened into a single Object so that lookups will follow this
		//		cascading pattern.  An optional build step can preload the bundles
		//		to avoid data redundancy and the multiple network hits normally
		//		required to load these resources.

		d.require("@JS_NS@._dojo.i18n");
		d.i18n._requireLocalization.apply(d.hostenv, arguments);
	};


	var ore = new RegExp("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$");
	var ire = new RegExp("^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$");

	@JS_NS@._dojo._Url = function(/*@JS_NS@._dojo._Url||String...*/){
		// summary: 
		//		Constructor to create an object representing a URL.
		//		It is marked as private, since we might consider removing
		//		or simplifying it.
		// description: 
		//		Each argument is evaluated in order relative to the next until
		//		a canonical uri is produced. To get an absolute Uri relative to
		//		the current document use:
		//      	new @JS_NS@._dojo._Url(document.baseURI, url)

		var n = null;

		// TODO: support for IPv6, see RFC 2732
		var _a = arguments;
		var uri = _a[0];
		// resolve uri components relative to each other
		for(var i = 1; i<_a.length; i++){
			if(!_a[i]){ continue; }

			// Safari doesn't support this.constructor so we have to be explicit
			// FIXME: Tracked (and fixed) in Webkit bug 3537.
			//		http://bugs.webkit.org/show_bug.cgi?id=3537
			var relobj = new d._Url(_a[i]+"");
			var uriobj = new d._Url(uri+"");

			if(
				(relobj.path=="")	&&
				(!relobj.scheme)	&&
				(!relobj.authority)	&&
				(!relobj.query)
			){
				if(relobj.fragment != n){
					uriobj.fragment = relobj.fragment;
				}
				relobj = uriobj;
			}else if(!relobj.scheme){
				relobj.scheme = uriobj.scheme;

				if(!relobj.authority){
					relobj.authority = uriobj.authority;

					if(relobj.path.charAt(0) != "/"){
						var path = uriobj.path.substring(0,
							uriobj.path.lastIndexOf("/") + 1) + relobj.path;

						var segs = path.split("/");
						for(var j = 0; j < segs.length; j++){
							if(segs[j] == "."){
								if(j == segs.length - 1){
									segs[j] = "";
								}else{
									segs.splice(j, 1);
									j--;
								}
							}else if(j > 0 && !(j == 1 && segs[0] == "") &&
								segs[j] == ".." && segs[j-1] != ".."){

								if(j == (segs.length - 1)){
									segs.splice(j, 1);
									segs[j - 1] = "";
								}else{
									segs.splice(j - 1, 2);
									j -= 2;
								}
							}
						}
						relobj.path = segs.join("/");
					}
				}
			}

			uri = "";
			if(relobj.scheme){ 
				uri += relobj.scheme + ":";
			}
			if(relobj.authority){
				uri += "//" + relobj.authority;
			}
			uri += relobj.path;
			if(relobj.query){
				uri += "?" + relobj.query;
			}
			if(relobj.fragment){
				uri += "#" + relobj.fragment;
			}
		}

		this.uri = uri.toString();

		// break the uri into its main components
		var r = this.uri.match(ore);

		this.scheme = r[2] || (r[1] ? "" : n);
		this.authority = r[4] || (r[3] ? "" : n);
		this.path = r[5]; // can never be undefined
		this.query = r[7] || (r[6] ? "" : n);
		this.fragment  = r[9] || (r[8] ? "" : n);

		if(this.authority != n){
			// server based naming authority
			r = this.authority.match(ire);

			this.user = r[3] || n;
			this.password = r[4] || n;
			this.host = r[5];
			this.port = r[7] || n;
		}
	}

	@JS_NS@._dojo._Url.prototype.toString = function(){ return this.uri; };

	@JS_NS@._dojo.moduleUrl = function(/*String*/module, /*@JS_NS@._dojo._Url||String*/url){
		// summary: 
		//		Returns a Url object relative to a module
		//		
		// example: 
		//	|	@JS_NS@._dojo.moduleUrl("@JS_NS@._dojo.widget","templates/template.html");
		// example:
		//	|	@JS_NS@._dojo.moduleUrl("acme","images/small.png")

		var loc = @JS_NS@._dojo._getModuleSymbols(module).join('/');
		if(!loc){ return null; }
		if(loc.lastIndexOf("/") != loc.length-1){
			loc += "/";
		}
		
		//If the path is an absolute path (starts with a / or is on another
		//domain/xdomain) then don't add the baseUrl.
		var colonIndex = loc.indexOf(":");
		if(loc.charAt(0) != "/" && (colonIndex == -1 || colonIndex > loc.indexOf("/"))){
			loc = d.baseUrl + loc;
		}

		return new d._Url(loc, url); // String
	}
})();

if(typeof window != 'undefined'){
	@JS_NS@._dojo.isBrowser = true;
	@JS_NS@._dojo._name = "browser";


	// attempt to figure out the path to dojo if it isn't set in the config
	(function(){
		var d = @JS_NS@._dojo;
		// this is a scope protection closure. We set browser versions and grab
		// the URL we were loaded from here.
// Woodstock: The baseUrl property is used instead script tag.
//		// grab the node we were loaded from
//		if(document && document.getElementsByTagName){
//			var scripts = document.getElementsByTagName("script");
//			var rePkg = /dojo(\.xd)?\.js([\?\.]|$)/i;
//			for(var i = 0; i < scripts.length; i++){
//				var src = scripts[i].getAttribute("src");
//				if(!src){ continue; }
//				var m = src.match(rePkg);
//				if(m){
//					// find out where we came from
//					if(!@JS_NS@._base.config._djConfig["baseUrl"]){
//						@JS_NS@._base.config._djConfig["baseUrl"] = src.substring(0, m.index);
//					}
//					// and find out if we need to modify our behavior
//					var cfg = scripts[i].getAttribute("djConfig");
//					if(cfg){
//						var cfgo = eval("({ "+cfg+" })");
//						for(var x in cfgo){
//							@JS_NS@._base.config._djConfig[x] = cfgo[x];
//						}
//					}
//					break; // "first Dojo wins"
//				}
//			}
//		}

		// Woodstock: Append slash typically left by parsing script tag.
                if (@JS_NS@._base.config._djConfig["baseUrl"].charAt(@JS_NS@._base.config._djConfig["baseUrl"].length) != '/') {
			@JS_NS@._base.config._djConfig["baseUrl"] += "/";
		}
		d.baseUrl = @JS_NS@._base.config._djConfig["baseUrl"];

		// fill in the rendering support information in @JS_NS@._dojo.render.*
		var n = navigator;
		var dua = n.userAgent;
		var dav = n.appVersion;
		var tv = parseFloat(dav);

		d.isOpera = (dua.indexOf("Opera") >= 0) ? tv : 0;
		d.isKhtml = (dav.indexOf("Konqueror") >= 0)||(dav.indexOf("Safari") >= 0) ? tv : 0;
		if(dav.indexOf("Safari") >= 0){
			d.isSafari = parseFloat(dav.split("Version/")[1]) || 2;
		}
		var geckoPos = dua.indexOf("Gecko");
		d.isMozilla = d.isMoz = ((geckoPos >= 0)&&(!d.isKhtml)) ? tv : 0;
		d.isFF = 0;
		d.isIE = 0;
		try{
			if(d.isMoz){
				d.isFF = parseFloat(dua.split("Firefox/")[1].split(" ")[0]);
			}
			if((document.all)&&(!d.isOpera)){
				d.isIE = parseFloat(dav.split("MSIE ")[1].split(";")[0]);
			}
		}catch(e){}

		//Workaround to get local file loads of dojo to work on IE 7
		//by forcing to not use native xhr.
		if(@JS_NS@._dojo.isIE && (window.location.protocol === "file:")){
			@JS_NS@._base.config._djConfig.ieForceActiveXXhr=true;
		}

		var cm = document["compatMode"];
		d.isQuirks = (cm == "BackCompat")||(cm == "QuirksMode")||(d.isIE < 6);

		// TODO: is the HTML LANG attribute relevant?
		d.locale = @JS_NS@._base.config._djConfig.locale || (d.isIE ? n.userLanguage : n.language).toLowerCase();

		d._println = console.debug;

		// These are in order of decreasing likelihood; this will change in time.
		d._XMLHTTP_PROGIDS = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.4.0'];

		d._xhrObj= function(){
			// summary: 
			//		does the work of portably generating a new XMLHTTPRequest
			//		object.
			var http = null;
			var last_e = null;
			if(!@JS_NS@._dojo.isIE || !@JS_NS@._base.config._djConfig.ieForceActiveXXhr){
				try{ http = new XMLHttpRequest(); }catch(e){}
			}
			if(!http){
				for(var i=0; i<3; ++i){
					var progid = @JS_NS@._dojo._XMLHTTP_PROGIDS[i];
					try{
						http = new ActiveXObject(progid);
					}catch(e){
						last_e = e;
					}

					if(http){
						@JS_NS@._dojo._XMLHTTP_PROGIDS = [progid];  // so faster next time
						break;
					}
				}
			}

			if(!http){
				throw new Error("XMLHTTP not available: "+last_e);
			}

			return http; // XMLHTTPRequest instance
		}

		d._isDocumentOk = function(http){
			var stat = http.status || 0;
			return ( (stat>=200)&&(stat<300))|| 	// Boolean
				(stat==304)|| 						// allow any 2XX response code
				(stat==1223)|| 						// get it out of the cache
				(!stat && (location.protocol=="file:" || location.protocol=="chrome:") ); // Internet Explorer mangled the status code
		}

		//See if base tag is in use.
		//This is to fix http://trac.dojotoolkit.org/ticket/3973,
		//but really, we need to find out how to get rid of the @JS_NS@._dojo._Url reference
		//below and still have DOH work with the @JS_NS@._dojo.i18n test following some other
		//test that uses the test frame to load a document (trac #2757).
		//Opera still has problems, but perhaps a larger issue of base tag support
		//with XHR requests (hasBase is true, but the request is still made to document
		//path, not base path).
		var owloc = window.location+"";
		var base = document.getElementsByTagName("base");
		var hasBase = (base && base.length > 0);

		d._getText = function(/*URI*/ uri, /*Boolean*/ fail_ok){
			// summary: Read the contents of the specified uri and return those contents.
			// uri:
			//		A relative or absolute uri. If absolute, it still must be in
			//		the same "domain" as we are.
			// fail_ok:
			//		Default false. If fail_ok and loading fails, return null
			//		instead of throwing.
			// returns: The response text. null is returned when there is a
			//		failure and failure is okay (an exception otherwise)

			// alert("_getText: " + uri);

			// NOTE: must be declared before scope switches ie. this._xhrObj()
			var http = this._xhrObj();

			if(!hasBase && @JS_NS@._dojo._Url){
				uri = (new @JS_NS@._dojo._Url(owloc, uri)).toString();
			}
			/*
			console.debug("_getText:", uri);
			console.debug(window.location+"");
			alert(uri);
			*/

			http.open('GET', uri, false);
			try{
				http.send(null);
				// alert(http);
				if(!d._isDocumentOk(http)){
					var err = Error("Unable to load "+uri+" status:"+ http.status);
					err.status = http.status;
					err.responseText = http.responseText;
					throw err;
				}
			}catch(e){
				if(fail_ok){ return null; } // null
				// rethrow the exception
				throw e;
			}
			return http.responseText; // String
		}
	})();

	@JS_NS@._dojo._initFired = false;
	//	BEGIN DOMContentLoaded, from Dean Edwards (http://dean.edwards.name/weblog/2006/06/again/)
	@JS_NS@._dojo._loadInit = function(e){
		@JS_NS@._dojo._initFired = true;
		// allow multiple calls, only first one will take effect
		// A bug in khtml calls events callbacks for document for event which isnt supported
		// for example a created contextmenu event calls DOMContentLoaded, workaround
		var type = (e && e.type) ? e.type.toLowerCase() : "load";
		if(arguments.callee.initialized || (type!="domcontentloaded" && type!="load")){ return; }
		arguments.callee.initialized = true;
		if(typeof @JS_NS@._dojo["_khtmlTimer"] != 'undefined'){
			clearInterval(@JS_NS@._dojo._khtmlTimer);
			delete @JS_NS@._dojo._khtmlTimer;
		}

		if(@JS_NS@._dojo._inFlightCount == 0){
			@JS_NS@._dojo._modulesLoaded();
		}
	}

	//	START DOMContentLoaded
	// Mozilla and Opera 9 expose the event we could use
	if(document.addEventListener){
		// NOTE: 
		//		due to a threading issue in Firefox 2.0, we can't enable
		//		DOMContentLoaded on that platform. For more information, see:
		//		http://trac.dojotoolkit.org/ticket/1704
		if(@JS_NS@._dojo.isOpera|| (@JS_NS@._dojo.isMoz && (@JS_NS@._base.config._djConfig["enableMozDomContentLoaded"] === true))){
			document.addEventListener("DOMContentLoaded", @JS_NS@._dojo._loadInit, null);
		}

		//	mainly for Opera 8.5, won't be fired if DOMContentLoaded fired already.
		//  also used for Mozilla because of trac #1640
		window.addEventListener("load", @JS_NS@._dojo._loadInit, null);
	}

	if(/(WebKit|khtml)/i.test(navigator.userAgent)){ // sniff
		@JS_NS@._dojo._khtmlTimer = setInterval(function(){
			if(/loaded|complete/.test(document.readyState)){
				@JS_NS@._dojo._loadInit(); // call the onload handler
			}
		}, 10);
	}
	//	END DOMContentLoaded

	(function(){

		var _w = window;
		var _handleNodeEvent = function(/*String*/evtName, /*Function*/fp){
			// summary:
			//		non-destructively adds the specified function to the node's
			//		evtName handler.
			// evtName: should be in the form "onclick" for "onclick" handlers.
			// Make sure you pass in the "on" part.
			var oldHandler = _w[evtName] || function(){};
			_w[evtName] = function(){
				fp.apply(_w, arguments);
				oldHandler.apply(_w, arguments);
			}
		}

		if(@JS_NS@._dojo.isIE){
			// 	for Internet Explorer. readyState will not be achieved on init
			// 	call, but dojo doesn't need it however, we'll include it
			// 	because we don't know if there are other functions added that
			// 	might.  Note that this has changed because the build process
			// 	strips all comments -- including conditional ones.

			document.write('<scr'+'ipt defer src="//:" '
				+ 'onreadystatechange="if(this.readyState==\'complete\'){@JS_NS@._dojo._loadInit();}">'
				+ '</scr'+'ipt>'
			);

			// IE WebControl hosted in an application can fire "beforeunload" and "unload"
			// events when control visibility changes, causing Dojo to unload too soon. The
			// following code fixes the problem
			// Reference: http://support.microsoft.com/default.aspx?scid=kb;en-us;199155
			var _unloading = true;
			_handleNodeEvent("onbeforeunload", function(){
				_w.setTimeout(function(){ _unloading = false; }, 0);
			});
			_handleNodeEvent("onunload", function(){
				if(_unloading){ @JS_NS@._dojo.unloaded(); }
			});

			try{
				document.namespaces.add("v","urn:schemas-microsoft-com:vml");
				document.createStyleSheet().addRule("v\\:*", "behavior:url(#default#VML)");
			}catch(e){}
		}else{
			// FIXME: @JS_NS@._dojo.unloaded requires dojo scope, so using anon function wrapper.
			_handleNodeEvent("onbeforeunload", function() { @JS_NS@._dojo.unloaded(); });
		}

	})();

	/*
	OpenAjax.subscribe("OpenAjax", "onload", function(){
		if(@JS_NS@._dojo._inFlightCount == 0){
			@JS_NS@._dojo._modulesLoaded();
		}
	});

	OpenAjax.subscribe("OpenAjax", "onunload", function(){
		@JS_NS@._dojo.unloaded();
	});
	*/
} //if (typeof window != 'undefined')

//Load debug code if necessary.
// @JS_NS@._dojo.requireIf((@JS_NS@._base.config._djConfig["isDebug"] || @JS_NS@._base.config._djConfig["debugAtAllCosts"]), "@JS_NS@._dojo.debug");

//window.widget is for Dashboard detection
//The full conditionals are spelled out to avoid issues during builds.
//Builds may be looking for require/requireIf statements and processing them.
// @JS_NS@._dojo.requireIf(@JS_NS@._base.config._djConfig["debugAtAllCosts"] && !window.widget && !@JS_NS@._base.config._djConfig["useXDomain"], "@JS_NS@._dojo.browser_debug");
// @JS_NS@._dojo.requireIf(@JS_NS@._base.config._djConfig["debugAtAllCosts"] && !window.widget && @JS_NS@._base.config._djConfig["useXDomain"], "@JS_NS@._dojo.browser_debug_xd");

if(@JS_NS@._base.config._djConfig.isDebug){
		@JS_NS@._dojo.require("@JS_NS@._dojo._firebug.firebug");
}

if(@JS_NS@._base.config._djConfig.debugAtAllCosts){
	@JS_NS@._base.config._djConfig.useXDomain = true;
	@JS_NS@._dojo.require("@JS_NS@._dojo._base._loader.loader_xd");
	@JS_NS@._dojo.require("@JS_NS@._dojo._base._loader.loader_debug");
	
}

};

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.lang"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.lang"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.lang");

// Crockford (ish) functions

@JS_NS@._dojo.isString = function(/*anything*/ it){
	// summary:	Return true if it is a String
	return typeof it == "string" || it instanceof String; // Boolean
}

@JS_NS@._dojo.isArray = function(/*anything*/ it){
	// summary: Return true if it is an Array
	return it && it instanceof Array || typeof it == "array"; // Boolean
}

/*=====
@JS_NS@._dojo.isFunction = function(it){
	// summary: Return true if it is a Function
	// it: anything
}
=====*/

@JS_NS@._dojo.isFunction = (function(){
	var _isFunction = function(/*anything*/ it){
		return typeof it == "function" || it instanceof Function; // Boolean
	};

	return @JS_NS@._dojo.isSafari ?
		// only slow this down w/ gratuitious casting in Safari since it's what's b0rken
		function(/*anything*/ it){
			if(typeof it == "function" && it == "[object NodeList]"){ return false; }
			return _isFunction(it); // Boolean
		} : _isFunction;
})();

@JS_NS@._dojo.isObject = function(/*anything*/ it){
	// summary: 
	//		Returns true if it is a JavaScript object (or an Array, a Function or null)
	return it !== undefined &&
		(it === null || typeof it == "object" || @JS_NS@._dojo.isArray(it) || @JS_NS@._dojo.isFunction(it)); // Boolean
}

@JS_NS@._dojo.isArrayLike = function(/*anything*/ it){
	//	summary:
	//		similar to @JS_NS@._dojo.isArray() but more permissive
	//	description:
	//		Doesn't strongly test for "arrayness".  Instead, settles for "isn't
	//		a string or number and has a length property". Arguments objects
	//		and DOM collections will return true when passed to
	//		@JS_NS@._dojo.isArrayLike(), but will return false when passed to
	//		@JS_NS@._dojo.isArray().
	//	return:
	//		If it walks like a duck and quicks like a duck, return true
	var d = @JS_NS@._dojo;
	return it && it !== undefined &&
		// keep out built-in constructors (Number, String, ...) which have length
		// properties
		!d.isString(it) && !d.isFunction(it) &&
		!(it.tagName && it.tagName.toLowerCase() == 'form') &&
		(d.isArray(it) || isFinite(it.length)); // Boolean
}

@JS_NS@._dojo.isAlien = function(/*anything*/ it){
	// summary: 
	//		Returns true if it is a built-in function or some other kind of
	//		oddball that *should* report as a function but doesn't
	return it && !@JS_NS@._dojo.isFunction(it) && /\{\s*\[native code\]\s*\}/.test(String(it)); // Boolean
}

@JS_NS@._dojo.extend = function(/*Object*/ constructor, /*Object...*/ props){
	// summary:
	//		Adds all properties and methods of props to constructor's
	//		prototype, making them available to all instances created with
	//		constructor.
	for(var i=1, l=arguments.length; i<l; i++){
		@JS_NS@._dojo._mixin(constructor.prototype, arguments[i]);
	}
	return constructor; // Object
}

@JS_NS@._dojo._hitchArgs = function(scope, method /*,...*/){
	var pre = @JS_NS@._dojo._toArray(arguments, 2);
	var named = @JS_NS@._dojo.isString(method);
	return function(){
		// arrayify arguments
		var args = @JS_NS@._dojo._toArray(arguments);
		// locate our method
		var f = named ? (scope||@JS_NS@._dojo.global)[method] : method;
		// invoke with collected args
		return f && f.apply(scope || this, pre.concat(args)); // mixed
 	} // Function
}

@JS_NS@._dojo.hitch = function(/*Object*/scope, /*Function|String*/method /*,...*/){
	// summary: 
	//		Returns a function that will only ever execute in the a given scope. 
	//		This allows for easy use of object member functions
	//		in callbacks and other places in which the "this" keyword may
	//		otherwise not reference the expected scope. 
	//		Any number of default positional arguments may be passed as parameters 
	//		beyond "method".
	//		Each of these values will be used to "placehold" (similar to curry)
	//		for the hitched function. 
	// scope: 
	//		The scope to use when method executes. If method is a string, 
	//		scope is also the object containing method.
	// method:
	//		A function to be hitched to scope, or the name of the method in
	//		scope to be hitched.
	// example:
	//	|	@JS_NS@._dojo.hitch(foo, "bar")(); 
	//		runs foo.bar() in the scope of foo
	// example:
	//	|	@JS_NS@._dojo.hitch(foo, myFunction);
	//		returns a function that runs myFunction in the scope of foo
	if(arguments.length > 2){
		return @JS_NS@._dojo._hitchArgs.apply(@JS_NS@._dojo, arguments); // Function
	}
	if(!method){
		method = scope;
		scope = null;
	}
	if(@JS_NS@._dojo.isString(method)){
		scope = scope || @JS_NS@._dojo.global;
		if(!scope[method]){ throw(['@JS_NS@._dojo.hitch: scope["', method, '"] is null (scope="', scope, '")'].join('')); }
		return function(){ return scope[method].apply(scope, arguments || []); }; // Function
	}
	return !scope ? method : function(){ return method.apply(scope, arguments || []); }; // Function
}

/*=====
@JS_NS@._dojo.delegate = function(obj, props){
	//	summary:
	//		returns a new object which "looks" to obj for properties which it
	//		does not have a value for. Optionally takes a bag of properties to
	//		seed the returned object with initially. 
	//	description:
	//		This is a small implementaton of the Boodman/Crockford delegation
	//		pattern in JavaScript. An intermediate object constructor mediates
	//		the prototype chain for the returned object, using it to delegate
	//		down to obj for property lookup when object-local lookup fails.
	//		This can be thought of similarly to ES4's "wrap", save that it does
	//		not act on types but rather on pure objects.
	//	obj:
	//		The object to delegate to for properties not found directly on the
	//		return object or in props.
	//	props:
	//		an object containing properties to assign to the returned object
	//	returns:
	//		an Object of anonymous type
	//	example:
	//	|	var foo = { bar: "baz" };
	//	|	var thinger = @JS_NS@._dojo.delegate(foo, { thud: "xyzzy"});
	//	|	thinger.bar == "baz"; // delegated to foo
	//	|	foo.xyzzy == undefined; // by definition
	//	|	thinger.xyzzy == "xyzzy"; // mixed in from props
	//	|	foo.bar = "thonk";
	//	|	thinger.bar == "thonk"; // still delegated to foo's bar
}
=====*/


@JS_NS@._dojo.delegate = @JS_NS@._dojo._delegate = function(obj, props){

	// boodman/crockford delegation
	function TMP(){};
	TMP.prototype = obj;
	var tmp = new TMP();
	if(props){
		@JS_NS@._dojo.mixin(tmp, props);
	}
	return tmp; // Object
}

@JS_NS@._dojo.partial = function(/*Function|String*/method /*, ...*/){
	// summary:
	//		similar to hitch() except that the scope object is left to be
	//		whatever the execution context eventually becomes.
	//	description:
	//		Calling @JS_NS@._dojo.partial is the functional equivalent of calling:
	//		|	@JS_NS@._dojo.hitch(null, funcName, ...);
	var arr = [ null ];
	return @JS_NS@._dojo.hitch.apply(@JS_NS@._dojo, arr.concat(@JS_NS@._dojo._toArray(arguments))); // Function
}

@JS_NS@._dojo._toArray = function(/*Object*/obj, /*Number?*/offset, /*Array?*/ startWith){
	// summary:
	//		Converts an array-like object (i.e. arguments, DOMCollection)
	//		to an array. Returns a new Array object.
	// obj:
	//		the object to "arrayify". We expect the object to have, at a
	//		minimum, a length property which corresponds to integer-indexed
	//		properties.
	// offset:
	//		the location in obj to start iterating from. Defaults to 0. Optional.
	// startWith:
	//		An array to pack with the properties of obj. If provided,
	//		properties in obj are appended at the end of startWith and
	//		startWith is the returned array.
	var arr = startWith||[];
	for(var x = offset || 0; x < obj.length; x++){
		arr.push(obj[x]);
	}
	return arr; // Array
}

@JS_NS@._dojo.clone = function(/*anything*/ o){
	// summary:
	//		Clones objects (including DOM nodes) and all children.
	//		Warning: do not clone cyclic structures.
	if(!o){ return o; }
	if(@JS_NS@._dojo.isArray(o)){
		var r = [];
		for(var i = 0; i < o.length; ++i){
			r.push(@JS_NS@._dojo.clone(o[i]));
		}
		return r; // Array
	}
	if(!@JS_NS@._dojo.isObject(o)){
		return o;	/*anything*/
	}
	if(o.nodeType && o.cloneNode){ // isNode
		return o.cloneNode(true); // Node
	}
	if(o instanceof Date){
		return new Date(o.getTime());	// Date
	}
	// Generic objects
	var r = new o.constructor(); // specific to @JS_NS@._dojo.declare()'d classes!
	for(var i in o){
		if(!(i in r) || r[i] != o[i]){
			r[i] = @JS_NS@._dojo.clone(o[i]);
		}
	}
	return r; // Object
}

@JS_NS@._dojo.trim = function(/*String*/ str){
	// summary: 
	//		trims whitespaces from both sides of the string
	// description:
	//		This version of trim() was selected for inclusion into the base due
	//		to its compact size and relatively good performance (see Steven
	//		Levithan's blog:
	//		http://blog.stevenlevithan.com/archives/faster-trim-javascript).
	//		The fastest but longest version of this function is located at
	//		@JS_NS@._dojo.string.trim()
	return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');	// String
}

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.declare"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.declare"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.declare");


// this file courtesy of the TurboAjax group, licensed under a Dojo CLA

@JS_NS@._dojo.declare = function(/*String*/ className, /*Function|Function[]*/ superclass, /*Object*/ props){
	//	summary: 
	//		Create a feature-rich constructor from compact notation
	//	className:
	//		The name of the constructor (loosely, a "class")
	//		stored in the "declaredClass" property in the created prototype
	//	superclass:
	//		May be null, a Function, or an Array of Functions. If an array, 
	//		the first element is used as the prototypical ancestor and
	//		any following Functions become mixin ancestors.
	//	props:
	//		An object whose properties are copied to the
	//		created prototype.
	//		Add an instance-initialization function by making it a property 
	//		named "constructor".
	//	description:
	//		Create a constructor using a compact notation for inheritance and
	//		prototype extension. 
	//
	//		All superclasses (including mixins) must be Functions (not simple Objects).
	//
	//		Mixin ancestors provide a type of multiple inheritance. Prototypes of mixin 
	//		ancestors are copied to the new class: changes to mixin prototypes will
	//		not affect classes to which they have been mixed in.
	//
	//		"className" is cached in "declaredClass" property of the new class.
	//
	//	example:
	//	|	@JS_NS@._dojo.declare("my.classes.bar", my.classes.foo, {
	//	|		// properties to be added to the class prototype
	//	|		someValue: 2,
	//	|		// initialization function
	//	|		constructor: function(){
	//	|			this.myComplicatedObject = new ReallyComplicatedObject(); 
	//	|		},
	//	|		// other functions
	//	|		someMethod: function(){ 
	//	|			doStuff(); 
	//	|		}
	//	|	);

	// argument juggling (deprecated)
	if(@JS_NS@._dojo.isFunction(props)||(arguments.length>3)){ 
		@JS_NS@._dojo.deprecated("@JS_NS@._dojo.declare: for class '" + className + "' pass initializer function as 'constructor' property instead of as a separate argument.", "", "1.0");
		var c = props;
		props = arguments[3] || {};
		props.constructor = c;
	}
	// process superclass argument
	// var dd=@JS_NS@._dojo.declare, mixins=null;
	var dd=arguments.callee, mixins=null;
	if(@JS_NS@._dojo.isArray(superclass)){
		mixins = superclass;
		superclass = mixins.shift();
	}
	// construct intermediate classes for mixins
	if(mixins){
		for(var i=0, m; i<mixins.length; i++){
			m = mixins[i];
			if(!m){throw("Mixin #" + i + " to declaration of " + className + " is null. It's likely a required module is not loaded.")};
			superclass = dd._delegate(superclass, m);
		}
	}
	// prepare values
	var init=(props||0).constructor, ctor=dd._delegate(superclass), fn;
	// name methods (experimental)
	for(var i in props){if(@JS_NS@._dojo.isFunction(fn=props[i])&&(!0[i])){fn.nom=i;}}
	// decorate prototype
	@JS_NS@._dojo.extend(ctor, {declaredClass: className, _constructor: init, preamble: null}, props||0); 
	// special help for IE
	ctor.prototype.constructor = ctor;
	// create named reference
	return @JS_NS@._dojo.setObject(className, ctor); // Function
}

@JS_NS@._dojo.mixin(@JS_NS@._dojo.declare, {
	_delegate: function(base, mixin){
		var bp = (base||0).prototype, mp = (mixin||0).prototype;
		// fresh constructor, fresh prototype
		var ctor = @JS_NS@._dojo.declare._makeCtor();
		// cache ancestry
		@JS_NS@._dojo.mixin(ctor, {superclass: bp, mixin: mp, extend: @JS_NS@._dojo.declare._extend});
		// chain prototypes
		if(base){ctor.prototype = @JS_NS@._dojo._delegate(bp);}
		// add mixin and core
		@JS_NS@._dojo.extend(ctor, @JS_NS@._dojo.declare._core, mp||0, {_constructor: null, preamble: null});
		// special help for IE
		ctor.prototype.constructor = ctor;
		// name this class for debugging
		ctor.prototype.declaredClass = (bp||0).declaredClass + '_' + (mp||0).declaredClass;
		return ctor;
	},
	_extend: function(props){
		for(var i in props){if(@JS_NS@._dojo.isFunction(fn=props[i])&&(!0[i])){fn.nom=i;}}
		@JS_NS@._dojo.extend(this, props);
	},
	_makeCtor: function(){
		// we have to make a function, but don't want to close over anything
		return function(){ this._construct(arguments); }
	},
	_core: { 
		_construct: function(args){
			var c=args.callee, s=c.superclass, ct=s&&s.constructor, m=c.mixin, mct=m&&m.constructor, a=args, ii, fn;
			// side-effect of = used on purpose here, lint may complain, don't try this at home
			if(a[0]){ 
				// FIXME: preambles for each mixin should be allowed
				// FIXME: 
				//		should we allow the preamble here NOT to modify the
				//		default args, but instead to act on each mixin
				//		independently of the class instance being constructed
				//		(for impdedence matching)?

				// allow any first argument w/ a "preamble" property to act as a
				// class preamble (not exclusive of the prototype preamble)
				if(/*@JS_NS@._dojo.isFunction*/(fn = a[0]["preamble"])){ 
					a = fn.apply(this, a) || a; 
				}
			} 
			// prototype preamble
			if(fn=c.prototype.preamble){a = fn.apply(this, a) || a;}
			// FIXME: 
			//		need to provide an optional prototype-settable
			//		"_explicitSuper" property which disables this
			// initialize superclass
			if(ct&&ct.apply){ct.apply(this, a);}
			// initialize mixin
			if(mct&&mct.apply){mct.apply(this, a);}
			// initialize self
			if(ii=c.prototype._constructor){ii.apply(this, args);}
			// post construction
			if(this.constructor.prototype==c.prototype && (ct=this.postscript)){ct.apply(this, args)};
		},
		_findMixin: function(mixin){
			var c = this.constructor, p, m;
			while(c){
				p = c.superclass;
				m = c.mixin;
				if(m==mixin || (m instanceof mixin.constructor)){return p;}
				if(m && (m=m._findMixin(mixin))){return m;}
				c = p && p.constructor;
			}
		},
		_findMethod: function(name, method, ptype, has){
			// consciously trading readability for bytes and speed in this low-level method
			var p=ptype, c, m, f;
			do{
				c = p.constructor;
				m = c.mixin;
				// find method by name in our mixin ancestor
				if(m && (m=this._findMethod(name, method, m, has))){return m};
				// if we found a named method that either exactly-is or exactly-is-not 'method'
				if((f=p[name])&&(has==(f==method))){return p};
				// ascend chain
				p = c.superclass;
			}while(p);
			// if we couldn't find an ancestor in our primary chain, try a mixin chain
			return !has && (p=this._findMixin(ptype)) && this._findMethod(name, method, p, has);
		},
		inherited: function(name, args, newArgs){
			// optionalize name argument (experimental)
			var a = arguments;
			if(!@JS_NS@._dojo.isString(a[0])){newArgs=args; args=name; name=args.callee.nom;}
			var c=args.callee, p=this.constructor.prototype, a=newArgs||args, fn, mp;
			// if not an instance override 
			if(this[name]!=c || p[name]==c){
				mp = this._findMethod(name, c, p, true);
				if(!mp){throw(this.declaredClass + ': name argument ("' + name + '") to inherited must match callee (declare.js)');}
				p = this._findMethod(name, c, mp, false);
			}
			fn = p && p[name];
			// FIXME: perhaps we should throw here? 
			if(!fn){console.debug(mp.declaredClass + ': no inherited "' + name + '" was found (declare.js)'); return;}
			// if the function exists, invoke it in our scope
			return fn.apply(this, a);
		}
	}
});

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.connect"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.connect"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.connect");


// this file courtesy of the TurboAjax Group, licensed under a Dojo CLA

// low-level delegation machinery
@JS_NS@._dojo._listener = {
	// create a dispatcher function
	getDispatcher: function(){
		// following comments pulled out-of-line to prevent cloning them 
		// in the returned function.
		// - indices (i) that are really in the array of listeners (ls) will 
		//   not be in Array.prototype. This is the 'sparse array' trick
		//   that keeps us safe from libs that take liberties with built-in 
		//   objects
		// - listener is invoked with current scope (this)
		return function(){
			var ap=Array.prototype, c=arguments.callee, ls=c._listeners, t=c.target;
			// return value comes from original target function
			var r=t && t.apply(this, arguments);
			// invoke listeners after target function
			for(var i in ls){
				if(!(i in ap)){
					ls[i].apply(this, arguments);
				}
			}
			// return value comes from original target function
			return r;
		}
	},
	// add a listener to an object
	add: function(/*Object*/ source, /*String*/ method, /*Function*/ listener){
		// Whenever 'method' is invoked, 'listener' will have the same scope.
		// Trying to supporting a context object for the listener led to 
		// complexity. 
		// Non trivial to provide 'once' functionality here
		// because listener could be the result of a @JS_NS@._dojo.hitch call,
		// in which case two references to the same hitch target would not
		// be equivalent. 
		source = source || @JS_NS@._dojo.global;
		// The source method is either null, a dispatcher, or some other function
		var f = source[method];
		// Ensure a dispatcher
		if(!f||!f._listeners){
			var d = @JS_NS@._dojo._listener.getDispatcher();
			// original target function is special
			d.target = f;
			// dispatcher holds a list of listeners
			d._listeners = []; 
			// redirect source to dispatcher
			f = source[method] = d;
		}
		// The contract is that a handle is returned that can 
		// identify this listener for disconnect. 
		//
		// The type of the handle is private. Here is it implemented as Integer. 
		// DOM event code has this same contract but handle is Function 
		// in non-IE browsers.
		//
		// We could have separate lists of before and after listeners.
		return f._listeners.push(listener) ; /*Handle*/
	},
	// remove a listener from an object
	remove: function(/*Object*/ source, /*String*/ method, /*Handle*/ handle){
		var f = (source||@JS_NS@._dojo.global)[method];
		// remember that handle is the index+1 (0 is not a valid handle)
		if(f && f._listeners && handle--){
			delete f._listeners[handle]; 
		}
	}
};

// Multiple delegation for arbitrary methods.

// This unit knows nothing about DOM, 
// but we include DOM aware 
// documentation and dontFix
// argument here to help the autodocs.
// Actual DOM aware code is in event.js.

@JS_NS@._dojo.connect = function(/*Object|null*/ obj, 
						/*String*/ event, 
						/*Object|null*/ context, 
						/*String|Function*/ method,
						/*Boolean*/ dontFix){
	// summary:
	//		Create a link that calls one function when another executes. 
	//
	// description:
	//		Connects method to event, so that after event fires, method
	//		does too. All connected functions are passed the same arguments as
	//		the event function was initially called with. You may connect as
	//		many methods to event as needed.
	//
	//		event must be a string. If obj is null, @JS_NS@._dojo.global is used.
	//
	//		null arguments may simply be omitted.
	//
	//		obj[event] can resolve to a function or undefined (null). 
	//		If obj[event] is null, it is assigned a function.
	//
	//		The return value is a handle that is needed to 
	//		remove this connection with @JS_NS@._dojo.disconnect.
	//
	// obj: 
	//		The source object for the event function. 
	//		Defaults to @JS_NS@._dojo.global if null.
	//		If obj is a DOM node, the connection is delegated 
	//		to the DOM event manager (unless dontFix is true).
	//
	// event:
	//		String name of the event function in obj. 
	//		I.e. identifies a property obj[event].
	//
	// context: 
	//		The object that method will receive as "this".
	//
	//		If context is null and method is a function, then method
	//		inherits the context of event.
	//	
	//		If method is a string then context must be the source 
	//		object object for method (context[method]). If context is null,
	//		@JS_NS@._dojo.global is used.
	//
	// method:
	//		A function reference, or name of a function in context. 
	//		The function identified by method fires after event does. 
	//		method receives the same arguments as the event.
	//		See context argument comments for information on method's scope.
	//
	// dontFix:
	//		If obj is a DOM node, set dontFix to true to prevent delegation 
	//		of this connection to the DOM event manager. 
	//
	// example:
	//		When obj.onchange(), do ui.update():
	//	|	@JS_NS@._dojo.connect(obj, "onchange", ui, "update");
	//	|	@JS_NS@._dojo.connect(obj, "onchange", ui, ui.update); // same
	//
	// example:
	//		Using return value for disconnect:
	//	|	var link = @JS_NS@._dojo.connect(obj, "onchange", ui, "update");
	//	|	...
	//	|	@JS_NS@._dojo.disconnect(link);
	//
	// example:
	//		When onglobalevent executes, watcher.handler is invoked:
	//	|	@JS_NS@._dojo.connect(null, "onglobalevent", watcher, "handler");
	//
	// example:
	//		When ob.onCustomEvent executes, customEventHandler is invoked:
	//	|	@JS_NS@._dojo.connect(ob, "onCustomEvent", null, "customEventHandler");
	//	|	@JS_NS@._dojo.connect(ob, "onCustomEvent", "customEventHandler"); // same
	//
	// example:
	//		When ob.onCustomEvent executes, customEventHandler is invoked
	//		with the same scope (this):
	//	|	@JS_NS@._dojo.connect(ob, "onCustomEvent", null, customEventHandler);
	//	|	@JS_NS@._dojo.connect(ob, "onCustomEvent", customEventHandler); // same
	//
	// example:
	//		When globalEvent executes, globalHandler is invoked
	//		with the same scope (this):
	//	|	@JS_NS@._dojo.connect(null, "globalEvent", null, globalHandler);
	//	|	@JS_NS@._dojo.connect("globalEvent", globalHandler); // same

	// normalize arguments
	var a=arguments, args=[], i=0;
	// if a[0] is a String, obj was ommited
	args.push(@JS_NS@._dojo.isString(a[0]) ? null : a[i++], a[i++]);
	// if the arg-after-next is a String or Function, context was NOT omitted
	var a1 = a[i+1];
	args.push(@JS_NS@._dojo.isString(a1)||@JS_NS@._dojo.isFunction(a1) ? a[i++] : null, a[i++]);
	// absorb any additional arguments
	for(var l=a.length; i<l; i++){	args.push(a[i]); }
	// do the actual work
	return @JS_NS@._dojo._connect.apply(this, args); /*Handle*/
}

// used by non-browser hostenvs. always overriden by event.js
@JS_NS@._dojo._connect = function(obj, event, context, method){
	var l=@JS_NS@._dojo._listener, h=l.add(obj, event, @JS_NS@._dojo.hitch(context, method)); 
	return [obj, event, h, l]; // Handle
}

@JS_NS@._dojo.disconnect = function(/*Handle*/ handle){
	// summary:
	//		Remove a link created by @JS_NS@._dojo.connect.
	// description:
	//		Removes the connection between event and the method referenced by handle.
	// handle:
	//		the return value of the @JS_NS@._dojo.connect call that created the connection.
	if(handle && handle[0] !== undefined){
		@JS_NS@._dojo._disconnect.apply(this, handle);
		// let's not keep this reference
		delete handle[0];
	}
}

@JS_NS@._dojo._disconnect = function(obj, event, handle, listener){
	listener.remove(obj, event, handle);
}

// topic publish/subscribe

@JS_NS@._dojo._topics = {};

@JS_NS@._dojo.subscribe = function(/*String*/ topic, /*Object|null*/ context, /*String|Function*/ method){
	//	summary:
	//		Attach a listener to a named topic. The listener function is invoked whenever the
	//		named topic is published (see: @JS_NS@._dojo.publish).
	//		Returns a handle which is needed to unsubscribe this listener.
	//	context:
	//		Scope in which method will be invoked, or null for default scope.
	//	method:
	//		The name of a function in context, or a function reference. This is the function that
	//		is invoked when topic is published.
	//	example:
	//	|	@JS_NS@._dojo.subscribe("alerts", null, function(caption, message){ alert(caption + "\n" + message); };
	//	|	@JS_NS@._dojo.publish("alerts", [ "read this", "hello world" ]);																	

	// support for 2 argument invocation (omitting context) depends on hitch
	return [topic, @JS_NS@._dojo._listener.add(@JS_NS@._dojo._topics, topic, @JS_NS@._dojo.hitch(context, method))]; /*Handle*/
}

@JS_NS@._dojo.unsubscribe = function(/*Handle*/ handle){
	//	summary:
	//	 	Remove a topic listener. 
	//	handle:
	//	 	The handle returned from a call to subscribe.
	//	example:
	//	|	var alerter = @JS_NS@._dojo.subscribe("alerts", null, function(caption, message){ alert(caption + "\n" + message); };
	//	|	...
	//	|	@JS_NS@._dojo.unsubscribe(alerter);
	if(handle){
		@JS_NS@._dojo._listener.remove(@JS_NS@._dojo._topics, handle[0], handle[1]);
	}
}

@JS_NS@._dojo.publish = function(/*String*/ topic, /*Array*/ args){
	//	summary:
	//	 	Invoke all listener method subscribed to topic.
	//	topic:
	//	 	The name of the topic to publish.
	//	args:
	//	 	An array of arguments. The arguments will be applied 
	//	 	to each topic subscriber (as first class parameters, via apply).
	//	example:
	//	|	@JS_NS@._dojo.subscribe("alerts", null, function(caption, message){ alert(caption + "\n" + message); };
	//	|	@JS_NS@._dojo.publish("alerts", [ "read this", "hello world" ]);	

	// Note that args is an array, which is more efficient vs variable length
	// argument list.  Ideally, var args would be implemented via Array
	// throughout the APIs.
	var f = @JS_NS@._dojo._topics[topic];
	if(f){
		f.apply(this, args||[]);
	}
}

@JS_NS@._dojo.connectPublisher = function(	/*String*/ topic, 
									/*Object|null*/ obj, 
									/*String*/ event){
	//	summary:
	//	 	Ensure that everytime obj.event() is called, a message is published
	//	 	on the topic. Returns a handle which can be passed to
	//	 	@JS_NS@._dojo.disconnect() to disable subsequent automatic publication on
	//	 	the topic.
	//	topic:
	//	 	The name of the topic to publish.
	//	obj: 
	//	 	The source object for the event function. Defaults to @JS_NS@._dojo.global
	//	 	if null.
	//	event:
	//	 	The name of the event function in obj. 
	//	 	I.e. identifies a property obj[event].
	//	example:
	//	|	@JS_NS@._dojo.connectPublisher("/ajax/start", @JS_NS@._dojo, "xhrGet"};
	var pf = function(){ @JS_NS@._dojo.publish(topic, arguments); }
	return (event) ? @JS_NS@._dojo.connect(obj, event, pf) : @JS_NS@._dojo.connect(obj, pf); //Handle
};

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.array"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.array"] = true;

@JS_NS@._dojo.provide("@JS_NS@._dojo._base.array");

(function(){
	var _getParts = function(arr, obj, cb){
		return [ 
			(@JS_NS@._dojo.isString(arr) ? arr.split("") : arr), 
			(obj||@JS_NS@._dojo.global),
			// FIXME: cache the anonymous functions we create here?
			(@JS_NS@._dojo.isString(cb) ? (new Function("item", "index", "array", cb)) : cb)
		];
	}

	@JS_NS@._dojo.mixin(@JS_NS@._dojo, {
		indexOf: function(	/*Array*/		array, 
							/*Object*/		value,
							/*Integer?*/	fromIndex,
							/*Boolean?*/	findLast){
			// summary:
			//		locates the first index of the provided value in the
			//		passed array. If the value is not found, -1 is returned.
			// description:
			//		For details on this method, see:
			// 			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:indexOf

			var i = 0, step = 1, end = array.length;
			if(findLast){
				i = end - 1;
				step = end = -1;
			}
			for(i = fromIndex || i; i != end; i += step){
				if(array[i] == value){ return i; }
			}
			return -1;	// Number
		},

		lastIndexOf: function(/*Array*/array, /*Object*/value, /*Integer?*/fromIndex){
			// summary:
			//		locates the last index of the provided value in the passed array. 
			//		If the value is not found, -1 is returned.
			// description:
			//		For details on this method, see:
			// 			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:lastIndexOf
			return @JS_NS@._dojo.indexOf(array, value, fromIndex, true); // Number
		},

		forEach: function(/*Array*/arr, /*Function*/callback, /*Object?*/obj){
			// summary:
			//		for every item in arr, call callback with that item as its
			//		only parameter.
			// description:
			//		Return values are ignored. This function
			//		corresponds (and wraps) the JavaScript 1.6 forEach method. For
			//		more details, see:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:forEach

			// match the behavior of the built-in forEach WRT empty arrs
			if(!arr || !arr.length){ return; }

			// FIXME: there are several ways of handilng thisObject. Is
			// @JS_NS@._dojo.global always the default context?
			var _p = _getParts(arr, obj, callback); arr = _p[0];
			for(var i=0,l=_p[0].length; i<l; i++){ 
				_p[2].call(_p[1], arr[i], i, arr);
			}
		},

		_everyOrSome: function(/*Boolean*/every, /*Array*/arr, /*Function*/callback, /*Object?*/obj){
			var _p = _getParts(arr, obj, callback); arr = _p[0];
			for(var i = 0, l = arr.length; i < l; i++){
				var result = !!_p[2].call(_p[1], arr[i], i, arr);
				if(every ^ result){
					return result; // Boolean
				}
			}
			return every; // Boolean
		},

		every: function(/*Array*/arr, /*Function*/callback, /*Object?*/thisObject){
			// summary:
			//		Determines whether or not every item in the array satisfies the
			//		condition implemented by callback.
			// description:
			//		The parameter thisObject may be used to
			//		scope the call to callback. The function signature is derived
			//		from the JavaScript 1.6 Array.every() function. More
			//		information on this can be found here:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:every
			// example:
			//	|	@JS_NS@._dojo.every([1, 2, 3, 4], function(item){ return item>1; });
			//		returns false
			// example:
			//	|	@JS_NS@._dojo.every([1, 2, 3, 4], function(item){ return item>0; });
			//		returns true 
			return this._everyOrSome(true, arr, callback, thisObject); // Boolean
		},

		some: function(/*Array*/arr, /*Function*/callback, /*Object?*/thisObject){
			// summary:
			//		Determines whether or not any item in the array satisfies the
			//		condition implemented by callback.
			// description:
			//		The parameter thisObject may be used to
			//		scope the call to callback. The function signature is derived
			//		from the JavaScript 1.6 Array.some() function. More
			//		information on this can be found here:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:some
			// example:
			//	|	@JS_NS@._dojo.some([1, 2, 3, 4], function(item){ return item>1; });
			//		returns true
			// example:
			//	|	@JS_NS@._dojo.some([1, 2, 3, 4], function(item){ return item<1; });
			//		returns false
			return this._everyOrSome(false, arr, callback, thisObject); // Boolean
		},

		map: function(/*Array*/arr, /*Function*/func, /*Function?*/obj){
			// summary:
			//		applies a function to each element of an Array and creates
			//		an Array with the results
			// description:
			//		Returns a new array constituted from the return values of
			//		passing each element of arr into unary_func. The obj parameter
			//		may be passed to enable the passed function to be called in
			//		that scope.  In environments that support JavaScript 1.6, this
			//		function is a passthrough to the built-in map() function
			//		provided by Array instances. For details on this, see:
			// 			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:map
			// example:
			//	|	@JS_NS@._dojo.map([1, 2, 3, 4], function(item){ return item+1 });
			//		returns [2, 3, 4, 5]
			var _p = _getParts(arr, obj, func); arr = _p[0];
			var outArr = ((arguments[3]) ? (new arguments[3]()) : []);
			for(var i=0;i<arr.length;++i){
				outArr.push(_p[2].call(_p[1], arr[i], i, arr));
			}
			return outArr; // Array
		},

		filter: function(/*Array*/arr, /*Function*/callback, /*Object?*/obj){
			// summary:
			//		Returns a new Array with those items from arr that match the
			//		condition implemented by callback. ob may be used to
			//		scope the call to callback. The function signature is derived
			//		from the JavaScript 1.6 Array.filter() function.
			//
			//		More information on the JS 1.6 API can be found here:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:filter
			// example:
			//	|	@JS_NS@._dojo.filter([1, 2, 3, 4], function(item){ return item>1; });
			//		returns [2, 3, 4]

			var _p = _getParts(arr, obj, callback); arr = _p[0];
			var outArr = [];
			for(var i = 0; i < arr.length; i++){
				if(_p[2].call(_p[1], arr[i], i, arr)){
					outArr.push(arr[i]);
				}
			}
			return outArr; // Array
		}
	});
})();

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base");
 // Woodstock: Unused.


// // Woodstock: Unused.
// // Woodstock: Unused.
 // Woodstock: Unused.
//@JS_NS@._dojo.require("@JS_NS@._dojo._base.Color"); // Woodstock: Unused.



// // Woodstock: Unused.
 // Woodstock: Unused.
 // Woodstock: Unused.
//@JS_NS@._dojo.requireIf(@JS_NS@._dojo.isBrowser, "@JS_NS@._dojo._base.fx"); // Woodstock: Unused.

(function(){
	if(@JS_NS@._base.config._djConfig.require){
		for(var x=0; x<@JS_NS@._base.config._djConfig.require.length; x++){
			@JS_NS@._dojo["require"](@JS_NS@._base.config._djConfig.require[x]);
		}
	}
})();

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.window"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.window"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.window");

// Woodstock: IE throws security exception if "factory" isn't removed.
// This allows widgets to be created before the window onLoad event.
//@JS_NS@._dojo._gearsObject = function(){
//	// summary: 
//	//		factory method to get a Google Gears plugin instance to
//	//		expose in the browser runtime environment, if present
//	var factory;
//	var results;
//	
//	var gearsObj = @JS_NS@._dojo.getObject("google.gears");
//	if(gearsObj){ return gearsObj; } // already defined elsewhere
//	
//	if(typeof GearsFactory != "undefined"){ // Firefox
//		factory = new GearsFactory();
//	}else{
//		if(@JS_NS@._dojo.isIE){
//			// IE
//			try{
//				factory = new ActiveXObject("Gears.Factory");
//			}catch(e){
//				// ok to squelch; there's no gears factory.  move on.
//			}
//		}else if(navigator.mimeTypes["application/x-googlegears"]){
//			// Safari?
//			factory = document.createElement("object");
//			factory.setAttribute("type", "application/x-googlegears");
//			factory.setAttribute("width", 0);
//			factory.setAttribute("height", 0);
//			factory.style.display = "none";
//			document.documentElement.appendChild(factory);
//		}
//	}
//
//	// still nothing?
//	if(!factory){ return null; }
//	
//	// define the global objects now; don't overwrite them though if they
//	// were somehow set internally by the Gears plugin, which is on their
//	// dev roadmap for the future
//	@JS_NS@._dojo.setObject("google.gears.factory", factory);
//	return @JS_NS@._dojo.getObject("google.gears");
//};

// see if we have Google Gears installed, and if
// so, make it available in the runtime environment
// and in the Google standard 'google.gears' global object
//@JS_NS@._dojo.isGears = (!!@JS_NS@._dojo._gearsObject())||0;

// @global: @JS_NS@._dojo.doc

// summary:
//		Current document object. '@JS_NS@._dojo.doc' can be modified
//		for temporary context shifting. Also see @JS_NS@._dojo.withDoc().
// description:
//    Refer to @JS_NS@._dojo.doc rather
//    than referring to 'window.document' to ensure your code runs
//    correctly in managed contexts.
@JS_NS@._dojo.doc = window["document"] || null;

@JS_NS@._dojo.body = function(){
	// summary:
	//		return the body object associated with @JS_NS@._dojo.doc

	// Note: document.body is not defined for a strict xhtml document
	// Would like to memoize this, but @JS_NS@._dojo.doc can change vi @JS_NS@._dojo.withDoc().
	return @JS_NS@._dojo.doc.body || @JS_NS@._dojo.doc.getElementsByTagName("body")[0];
}

@JS_NS@._dojo.setContext = function(/*Object*/globalObject, /*DocumentElement*/globalDocument){
	// summary:
	//		changes the behavior of many core Dojo functions that deal with
	//		namespace and DOM lookup, changing them to work in a new global
	//		context. The varibles @JS_NS@._dojo.global and @JS_NS@._dojo.doc
	//		are modified as a result of calling this function.
	@JS_NS@._dojo.global = globalObject;
	@JS_NS@._dojo.doc = globalDocument;
};

@JS_NS@._dojo._fireCallback = function(callback, context, cbArguments){
	// FIXME: should migrate to using "@JS_NS@._dojo.isString"!
	if(context && @JS_NS@._dojo.isString(callback)){
		callback = context[callback];
	}
	return (context ? callback.apply(context, cbArguments || [ ]) : callback());
}

@JS_NS@._dojo.withGlobal = function(	/*Object*/globalObject, 
							/*Function*/callback, 
							/*Object?*/thisObject, 
							/*Array?*/cbArguments){
	// summary:
	//		Call callback with globalObject as @JS_NS@._dojo.global and
	//		globalObject.document as @JS_NS@._dojo.doc. If provided, globalObject
	//		will be executed in the context of object thisObject
	// description:
	//		When callback() returns or throws an error, the @JS_NS@._dojo.global
	//		and @JS_NS@._dojo.doc will be restored to its previous state.
	var rval;
	var oldGlob = @JS_NS@._dojo.global;
	var oldDoc = @JS_NS@._dojo.doc;
	try{
		@JS_NS@._dojo.setContext(globalObject, globalObject.document);
		rval = @JS_NS@._dojo._fireCallback(callback, thisObject, cbArguments);
	}finally{
		@JS_NS@._dojo.setContext(oldGlob, oldDoc);
	}
	return rval;
}

@JS_NS@._dojo.withDoc = function(	/*Object*/documentObject, 
							/*Function*/callback, 
							/*Object?*/thisObject, 
							/*Array?*/cbArguments){
	// summary:
	//		Call callback with documentObject as @JS_NS@._dojo.doc. If provided,
	//		callback will be executed in the context of object thisObject
	// description:
	//		When callback() returns or throws an error, the @JS_NS@._dojo.doc will
	//		be restored to its previous state.
	var rval;
	var oldDoc = @JS_NS@._dojo.doc;
	try{
		@JS_NS@._dojo.doc = documentObject;
		rval = @JS_NS@._dojo._fireCallback(callback, thisObject, cbArguments);
	}finally{
		@JS_NS@._dojo.doc = oldDoc;
	}
	return rval;
};

//Register any module paths set up in @JS_NS@._base.config._djConfig. Need to do this
//in the hostenvs since hostenv_browser can read djConfig from a
//script tag's attribute.
(function(){
	var mp = @JS_NS@._base.config._djConfig["modulePaths"];
	if(mp){
		for(var param in mp){
			@JS_NS@._dojo.registerModulePath(param, mp[param]);
		}
	}
})();

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.event"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.event"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.event");


// this file courtesy of the TurboAjax Group, licensed under a Dojo CLA

(function(){
	// DOM event listener machinery
	var del = @JS_NS@._dojo._event_listener = {
		add: function(/*DOMNode*/node, /*String*/name, /*Function*/fp){
			if(!node){return;} 
			name = del._normalizeEventName(name);

			fp = del._fixCallback(name, fp);

			var oname = name;
			if((!@JS_NS@._dojo.isIE)&&((name == "mouseenter")||(name == "mouseleave"))){
				var oname = name;
				var ofp = fp;
				name = (name == "mouseenter") ? "mouseover" : "mouseout";
				fp = function(e){
					// thanks ben!
					var id = @JS_NS@._dojo.isDescendant(e.relatedTarget, node);
					if(id == false){
						// e.type = oname; // FIXME: doesn't take?
						return ofp.call(this, e);
					}
				}
			}

			node.addEventListener(name, fp, false);
			return fp; /*Handle*/
		},
		remove: function(/*DOMNode*/node, /*String*/event, /*Handle*/handle){
			// summary:
			//		clobbers the listener from the node
			// node:
			//		DOM node to attach the event to
			// event:
			//		the name of the handler to remove the function from
			// handle:
			//		the handle returned from add
			(node)&&(node.removeEventListener(del._normalizeEventName(event), handle, false));
		},
		_normalizeEventName: function(/*String*/name){
			// Generally, name should be lower case, unless it is special
			// somehow (e.g. a Mozilla DOM event).
			// Remove 'on'.
			return (name.slice(0,2)=="on" ? name.slice(2) : name);
		},
		_fixCallback: function(/*String*/name, fp){
			// By default, we only invoke _fixEvent for 'keypress'
			// If code is added to _fixEvent for other events, we have
			// to revisit this optimization.
			// This also applies to _fixEvent overrides for Safari and Opera
			// below.
			return (name!="keypress" ? fp : function(e){ return fp.call(this, del._fixEvent(e, this)); });	
		},
		_fixEvent: function(evt, sender){
			// _fixCallback only attaches us to keypress.
			// Switch on evt.type anyway because we might 
			// be called directly from @JS_NS@._dojo.fixEvent.
			switch(evt.type){
				case "keypress":
					del._setKeyChar(evt);
					break;
			}
			return evt;
		},
		_setKeyChar: function(evt){
			evt.keyChar = (evt.charCode ? String.fromCharCode(evt.charCode) : '');
		}
	};

	// DOM events
	
	@JS_NS@._dojo.fixEvent = function(/*Event*/evt, /*DOMNode*/sender){
		// summary:
		//		normalizes properties on the event object including event
		//		bubbling methods, keystroke normalization, and x/y positions
		// evt: Event
		//		native event object
		// sender: DOMNode
		//		node to treat as "currentTarget"
		return del._fixEvent(evt, sender);
	}

	@JS_NS@._dojo.stopEvent = function(/*Event*/evt){
		// summary:
		//		prevents propagation and clobbers the default action of the
		//		passed event
		// evt: Event
		//		The event object. If omitted, window.event is used on IE.
		evt.preventDefault();
		evt.stopPropagation();
		// NOTE: below, this method is overridden for IE
	}

	// the default listener to use on dontFix nodes, overriden for IE
	var node_listener = @JS_NS@._dojo._listener;
	
	// Unify connect and event listeners
	@JS_NS@._dojo._connect = function(obj, event, context, method, dontFix){
		// FIXME: need a more strict test
		var isNode = obj && (obj.nodeType||obj.attachEvent||obj.addEventListener);
		// choose one of three listener options: raw (connect.js), DOM event on a Node, custom event on a Node
		// we need the third option to provide leak prevention on broken browsers (IE)
		var lid = !isNode ? 0 : (!dontFix ? 1 : 2), l = [@JS_NS@._dojo._listener, del, node_listener][lid];
		// create a listener
		var h = l.add(obj, event, @JS_NS@._dojo.hitch(context, method));
		// formerly, the disconnect package contained "l" directly, but if client code
		// leaks the disconnect package (by connecting it to a node), referencing "l" 
		// compounds the problem.
		// instead we return a listener id, which requires custom _disconnect below.
		// return disconnect package
		return [ obj, event, h, lid ];
	}

	@JS_NS@._dojo._disconnect = function(obj, event, handle, listener){
		([@JS_NS@._dojo._listener, del, node_listener][listener]).remove(obj, event, handle);
	}

	// Constants

	// Public: client code should test
	// keyCode against these named constants, as the
	// actual codes can vary by browser.
	@JS_NS@._dojo.keys = {
		BACKSPACE: 8,
		TAB: 9,
		CLEAR: 12,
		ENTER: 13,
		SHIFT: 16,
		CTRL: 17,
		ALT: 18,
		PAUSE: 19,
		CAPS_LOCK: 20,
		ESCAPE: 27,
		SPACE: 32,
		PAGE_UP: 33,
		PAGE_DOWN: 34,
		END: 35,
		HOME: 36,
		LEFT_ARROW: 37,
		UP_ARROW: 38,
		RIGHT_ARROW: 39,
		DOWN_ARROW: 40,
		INSERT: 45,
		DELETE: 46,
		HELP: 47,
		LEFT_WINDOW: 91,
		RIGHT_WINDOW: 92,
		SELECT: 93,
		NUMPAD_0: 96,
		NUMPAD_1: 97,
		NUMPAD_2: 98,
		NUMPAD_3: 99,
		NUMPAD_4: 100,
		NUMPAD_5: 101,
		NUMPAD_6: 102,
		NUMPAD_7: 103,
		NUMPAD_8: 104,
		NUMPAD_9: 105,
		NUMPAD_MULTIPLY: 106,
		NUMPAD_PLUS: 107,
		NUMPAD_ENTER: 108,
		NUMPAD_MINUS: 109,
		NUMPAD_PERIOD: 110,
		NUMPAD_DIVIDE: 111,
		F1: 112,
		F2: 113,
		F3: 114,
		F4: 115,
		F5: 116,
		F6: 117,
		F7: 118,
		F8: 119,
		F9: 120,
		F10: 121,
		F11: 122,
		F12: 123,
		F13: 124,
		F14: 125,
		F15: 126,
		NUM_LOCK: 144,
		SCROLL_LOCK: 145
	};
	
	// IE event normalization
	if(@JS_NS@._dojo.isIE){ 
		var _trySetKeyCode = function(e, code){
			try{
				// squelch errors when keyCode is read-only
				// (e.g. if keyCode is ctrl or shift)
				return (e.keyCode = code);
			}catch(e){
				return 0;
			}
		}

		// by default, use the standard listener
		var iel = @JS_NS@._dojo._listener;
		// dispatcher tracking property
		if(!@JS_NS@._base.config._djConfig._allow_leaks){
			// custom listener that handles leak protection for DOM events
			node_listener = iel = @JS_NS@._dojo._ie_listener = {
				// support handler indirection: event handler functions are 
				// referenced here. Event dispatchers hold only indices.
				handlers: [],
				// add a listener to an object
				add: function(/*Object*/ source, /*String*/ method, /*Function*/ listener){
					source = source || @JS_NS@._dojo.global;
					var f = source[method];
					if(!f||!f._listeners){
						var d = @JS_NS@._dojo._getIeDispatcher();
						// original target function is special
						d.target = f && (ieh.push(f) - 1);
						// dispatcher holds a list of indices into handlers table
						d._listeners = [];
						// redirect source to dispatcher
						f = source[method] = d;
					}
					return f._listeners.push(ieh.push(listener) - 1) ; /*Handle*/
				},
				// remove a listener from an object
				remove: function(/*Object*/ source, /*String*/ method, /*Handle*/ handle){
					var f = (source||@JS_NS@._dojo.global)[method], l = f&&f._listeners;
					if(f && l && handle--){
						delete ieh[l[handle]];
						delete l[handle]; 
					}
				}
			};
			// alias used above
			var ieh = iel.handlers;
		}

		@JS_NS@._dojo.mixin(del, {
			add: function(/*DOMNode*/node, /*String*/event, /*Function*/fp){
				if(!node){return;} // undefined
				event = del._normalizeEventName(event);
				if(event=="onkeypress"){
					// we need to listen to onkeydown to synthesize 
					// keypress events that otherwise won't fire
					// on IE
					var kd = node.onkeydown;
					if(!kd||!kd._listeners||!kd._stealthKeydown){
						// we simply ignore this connection when disconnecting
						// because it's side-effects are harmless 
						del.add(node, "onkeydown", del._stealthKeyDown);
						// we only want one stealth listener per node
						node.onkeydown._stealthKeydown = true;
					} 
				}
				return iel.add(node, event, del._fixCallback(fp));
			},
			remove: function(/*DOMNode*/node, /*String*/event, /*Handle*/handle){
				iel.remove(node, del._normalizeEventName(event), handle); 
			},
			_normalizeEventName: function(/*String*/eventName){
				// Generally, eventName should be lower case, unless it is
				// special somehow (e.g. a Mozilla event)
				// ensure 'on'
				return (eventName.slice(0,2)!="on" ? "on"+eventName : eventName);
			},
			_nop: function(){},
			_fixEvent: function(/*Event*/evt, /*DOMNode*/sender){
				// summary:
				//		normalizes properties on the event object including event
				//		bubbling methods, keystroke normalization, and x/y positions
				// evt: native event object
				// sender: node to treat as "currentTarget"
				if(!evt){
					var w = (sender)&&((sender.ownerDocument || sender.document || sender).parentWindow)||window;
					evt = w.event; 
				}
				if(!evt){return(evt);}
				evt.target = evt.srcElement; 
				evt.currentTarget = (sender || evt.srcElement); 
				evt.layerX = evt.offsetX;
				evt.layerY = evt.offsetY;
				// FIXME: scroll position query is duped from @JS_NS@._dojo.html to
				// avoid dependency on that entire module. Now that HTML is in
				// Base, we should convert back to something similar there.
				var se = evt.srcElement, doc = (se && se.ownerDocument) || document;
				// DO NOT replace the following to use @JS_NS@._dojo.body(), in IE, document.documentElement should be used
				// here rather than document.body
				var docBody = ((@JS_NS@._dojo.isIE<6)||(doc["compatMode"]=="BackCompat")) ? doc.body : doc.documentElement;
				var offset = @JS_NS@._dojo._getIeDocumentElementOffset();
				evt.pageX = evt.clientX + @JS_NS@._dojo._fixIeBiDiScrollLeft(docBody.scrollLeft || 0) - offset.x;
				evt.pageY = evt.clientY + (docBody.scrollTop || 0) - offset.y;
				if(evt.type == "mouseover"){ 
					evt.relatedTarget = evt.fromElement;
				}
				if(evt.type == "mouseout"){ 
					evt.relatedTarget = evt.toElement;
				}
				evt.stopPropagation = del._stopPropagation;
				evt.preventDefault = del._preventDefault;
				return del._fixKeys(evt);
			},
			_fixKeys: function(evt){
				switch(evt.type){
					case "keypress":
						var c = ("charCode" in evt ? evt.charCode : evt.keyCode);
						if (c==10){
							// CTRL-ENTER is CTRL-ASCII(10) on IE, but CTRL-ENTER on Mozilla
							c=0;
							evt.keyCode = 13;
						}else if(c==13||c==27){
							c=0; // Mozilla considers ENTER and ESC non-printable
						}else if(c==3){
							c=99; // Mozilla maps CTRL-BREAK to CTRL-c
						}
						// Mozilla sets keyCode to 0 when there is a charCode
						// but that stops the event on IE.
						evt.charCode = c;
						del._setKeyChar(evt);
						break;
				}
				return evt;
			},
			// some ctrl-key combinations (mostly w/punctuation) do not emit a char code in IE
			// we map those virtual key codes to ascii here
			// not valid for all (non-US) keyboards, so maybe we shouldn't bother
			_punctMap: { 
				106:42, 
				111:47, 
				186:59, 
				187:43, 
				188:44, 
				189:45, 
				190:46, 
				191:47, 
				192:96, 
				219:91, 
				220:92, 
				221:93, 
				222:39 
			},
			_stealthKeyDown: function(evt){
				// IE doesn't fire keypress for most non-printable characters.
				// other browsers do, we simulate it here.
				var kp=evt.currentTarget.onkeypress;
				// only works if kp exists and is a dispatcher
				if(!kp||!kp._listeners)return;
				// munge key/charCode
				var k=evt.keyCode;
				// These are Windows Virtual Key Codes
				// http://msdn.microsoft.com/library/default.asp?url=/library/en-us/winui/WinUI/WindowsUserInterface/UserInput/VirtualKeyCodes.asp
				var unprintable = (k!=13)&&(k!=32)&&(k!=27)&&(k<48||k>90)&&(k<96||k>111)&&(k<186||k>192)&&(k<219||k>222);
				// synthesize keypress for most unprintables and CTRL-keys
				if(unprintable||evt.ctrlKey){
					var c = (unprintable ? 0 : k);
					if(evt.ctrlKey){
						if(k==3 || k==13){
							return; // IE will post CTRL-BREAK, CTRL-ENTER as keypress natively 
						}else if(c>95 && c<106){ 
							c -= 48; // map CTRL-[numpad 0-9] to ASCII
						}else if((!evt.shiftKey)&&(c>=65&&c<=90)){ 
							c += 32; // map CTRL-[A-Z] to lowercase
						}else{ 
							c = del._punctMap[c] || c; // map other problematic CTRL combinations to ASCII
						}
					}
					// simulate a keypress event
					var faux = del._synthesizeEvent(evt, {type: 'keypress', faux: true, charCode: c});
					kp.call(evt.currentTarget, faux);
					evt.cancelBubble = faux.cancelBubble;
					evt.returnValue = faux.returnValue;
					_trySetKeyCode(evt, faux.keyCode);
				}
			},
			// Called in Event scope
			_stopPropagation: function(){
				this.cancelBubble = true; 
			},
			_preventDefault: function(){
				// Setting keyCode to 0 is the only way to prevent certain keypresses (namely
				// ctrl-combinations that correspond to menu accelerator keys).
				// Otoh, it prevents upstream listeners from getting this information
				// Try to split the difference here by clobbering keyCode only for ctrl 
				// combinations. If you still need to access the key upstream, bubbledKeyCode is
				// provided as a workaround.
				this.bubbledKeyCode = this.keyCode;
				if(this.ctrlKey){_trySetKeyCode(this, 0);}
				this.returnValue = false;
			}
		});
				
		// override stopEvent for IE
		@JS_NS@._dojo.stopEvent = function(evt){
			evt = evt || window.event;
			del._stopPropagation.call(evt);
			del._preventDefault.call(evt);
		}
	}

	del._synthesizeEvent = function(evt, props){
			var faux = @JS_NS@._dojo.mixin({}, evt, props);
			del._setKeyChar(faux);
			// FIXME: would prefer to use @JS_NS@._dojo.hitch: @JS_NS@._dojo.hitch(evt, evt.preventDefault); 
			// but it throws an error when preventDefault is invoked on Safari
			// does Event.preventDefault not support "apply" on Safari?
			faux.preventDefault = function(){ evt.preventDefault(); }; 
			faux.stopPropagation = function(){ evt.stopPropagation(); }; 
			return faux;
	}
	
	// Opera event normalization
	if(@JS_NS@._dojo.isOpera){
		@JS_NS@._dojo.mixin(del, {
			_fixEvent: function(evt, sender){
				switch(evt.type){
					case "keypress":
						var c = evt.which;
						if(c==3){
							c=99; // Mozilla maps CTRL-BREAK to CTRL-c
						}
						// can't trap some keys at all, like INSERT and DELETE
						// there is no differentiating info between DELETE and ".", or INSERT and "-"
						c = ((c<41)&&(!evt.shiftKey) ? 0 : c);
						if((evt.ctrlKey)&&(!evt.shiftKey)&&(c>=65)&&(c<=90)){
							// lowercase CTRL-[A-Z] keys
							c += 32;
						}
						return del._synthesizeEvent(evt, { charCode: c });
				}
				return evt;
			}
		});
	}

	// Safari event normalization
	if(@JS_NS@._dojo.isSafari){
		@JS_NS@._dojo.mixin(del, {
			_fixEvent: function(evt, sender){
				switch(evt.type){
					case "keypress":
						var c = evt.charCode, s = evt.shiftKey, k = evt.keyCode;
						// FIXME: This is a hack, suggest we rethink keyboard strategy.
						// Arrow and page keys have 0 "keyCode" in keypress events.on Safari for Windows
						k = k || identifierMap[evt.keyIdentifier] || 0;
						if(evt.keyIdentifier=="Enter"){
							c = 0; // differentiate Enter from CTRL-m (both code 13)
						}else if((evt.ctrlKey)&&(c>0)&&(c<27)){
							c += 96; // map CTRL-[A-Z] codes to ASCII
						} else if (c==@JS_NS@._dojo.keys.SHIFT_TAB) {
							c = @JS_NS@._dojo.keys.TAB; // morph SHIFT_TAB into TAB + shiftKey: true
							s = true;
						} else {
							c = (c>=32 && c<63232 ? c : 0); // avoid generating keyChar for non-printables
						}
						return del._synthesizeEvent(evt, {charCode: c, shiftKey: s, keyCode: k});
				}
				return evt;
			}
		});
		
		@JS_NS@._dojo.mixin(@JS_NS@._dojo.keys, {
			SHIFT_TAB: 25,
			UP_ARROW: 63232,
			DOWN_ARROW: 63233,
			LEFT_ARROW: 63234,
			RIGHT_ARROW: 63235,
			F1: 63236,
			F2: 63237,
			F3: 63238,
			F4: 63239,
			F5: 63240,
			F6: 63241,
			F7: 63242,
			F8: 63243,
			F9: 63244,
			F10: 63245,
			F11: 63246,
			F12: 63247,
			PAUSE: 63250,
			DELETE: 63272,
			HOME: 63273,
			END: 63275,
			PAGE_UP: 63276,
			PAGE_DOWN: 63277,
			INSERT: 63302,
			PRINT_SCREEN: 63248,
			SCROLL_LOCK: 63249,
			NUM_LOCK: 63289
		});
		var dk = @JS_NS@._dojo.keys, identifierMap = { "Up": dk.UP_ARROW, "Down": dk.DOWN_ARROW, "Left": dk.LEFT_ARROW, "Right": dk.RIGHT_ARROW, "PageUp": dk.PAGE_UP, "PageDown": dk.PAGE_DOWN }; 
	}
})();

if(@JS_NS@._dojo.isIE){
	// keep this out of the closure
	// closing over 'iel' or 'ieh' b0rks leak prevention
	// ls[i] is an index into the master handler array
	@JS_NS@._dojo._getIeDispatcher = function(){
		return function(){
			var ap=Array.prototype, h=@JS_NS@._dojo._ie_listener.handlers, c=arguments.callee, ls=c._listeners, t=h[c.target];
			// return value comes from original target function
			var r = t && t.apply(this, arguments);
			// invoke listeners after target function
			for(var i in ls){
				if(!(i in ap)){
					h[ls[i]].apply(this, arguments);
				}
			}
			return r;
		}
	}
	// keep this out of the closure to reduce RAM allocation
	@JS_NS@._dojo._event_listener._fixCallback = function(fp){
		var f = @JS_NS@._dojo._event_listener._fixEvent;
		return function(e){ return fp.call(this, f(e, this)); };
	}
}

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.html"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.html"] = true;

@JS_NS@._dojo.provide("@JS_NS@._dojo._base.html");

// FIXME: need to add unit tests for all the semi-public methods

try{
	document.execCommand("BackgroundImageCache", false, true);
}catch(e){
	// sane browsers don't have cache "issues"
}

// =============================
// DOM Functions
// =============================

/*=====
@JS_NS@._dojo.byId = function(id, doc){
	//	summary:
	//		similar to other library's "$" function, takes a
	//		string representing a DOM id or a DomNode
	//		and returns the corresponding DomNode. If a Node is
	//		passed, this function is a no-op. Returns a
	//		single DOM node or null, working around several
	//		browser-specific bugs to do so.
	//	id: String|DOMNode
	//	 	DOM id or DOM Node
	//	doc: DocumentElement
	//		Document to work in. Defaults to the current value of
	//		@JS_NS@._dojo.doc.  Can be used to retreive
	//		node references from other documents.
=====*/
if(@JS_NS@._dojo.isIE || @JS_NS@._dojo.isOpera){
	@JS_NS@._dojo.byId = function(id, doc){
		if(@JS_NS@._dojo.isString(id)){
			var _d = doc || @JS_NS@._dojo.doc;
			var te = _d.getElementById(id);
			// attributes.id.value is better than just id in case the 
			// user has a name=id inside a form
			if(te && te.attributes.id.value == id){
				return te;
			}else{
				var eles = _d.all[id];
				if(!eles){ return; }
				if(!eles.length){ return eles; }
				// if more than 1, choose first with the correct id
				var i=0;
				while((te=eles[i++])){
					if(te.attributes.id.value == id){ return te; }
				}
			}
		}else{
			return id; // DomNode
		}
	}
}else{
	@JS_NS@._dojo.byId = function(id, doc){
		if(@JS_NS@._dojo.isString(id)){
			return (doc || @JS_NS@._dojo.doc).getElementById(id);
		}else{
			return id; // DomNode
		}
	}
}
/*=====
}
=====*/

(function(){
	/*
	@JS_NS@._dojo.createElement = function(obj, parent, position){
		// TODO: need to finish this!
	}
	*/

	var _destroyContainer = null;
	@JS_NS@._dojo._destroyElement = function(/*String||DomNode*/node){
		// summary:
		//		removes node from its parent, clobbers it and all of its
		//		children.
		//	node:
		//		the element to be destroyed, either as an ID or a reference

		node = @JS_NS@._dojo.byId(node);
		try{
			if(!_destroyContainer){
				_destroyContainer = document.createElement("div");
			}
			_destroyContainer.appendChild(node.parentNode ? node.parentNode.removeChild(node) : node);
			// NOTE: see http://trac.dojotoolkit.org/ticket/2931. This may be a bug and not a feature
			_destroyContainer.innerHTML = ""; 
		}catch(e){
			/* squelch */
		}
	};

	@JS_NS@._dojo.isDescendant = function(/*DomNode|String*/node, /*DomNode|String*/ancestor){
		//	summary:
		//		Returns true if node is a descendant of ancestor
		//	node: id or node reference to test
		//	ancestor: id or node reference of potential parent to test against
		try{
			node = @JS_NS@._dojo.byId(node);
			ancestor = @JS_NS@._dojo.byId(ancestor);
			while(node){
				if(node === ancestor){
					return true; // Boolean
				}
				node = node.parentNode;
			}
		}catch(e){ return -1; /* squelch */ }
		return false; // Boolean
	};

	@JS_NS@._dojo.setSelectable = function(/*DomNode|String*/node, /*Boolean*/selectable){
		//	summary: enable or disable selection on a node
		//	node:
		//		id or reference to node
		//	selectable:
		node = @JS_NS@._dojo.byId(node);
		if(@JS_NS@._dojo.isMozilla){
			node.style.MozUserSelect = selectable ? "" : "none";
		}else if(@JS_NS@._dojo.isKhtml){
			node.style.KhtmlUserSelect = selectable ? "auto" : "none";
		}else if(@JS_NS@._dojo.isIE){
			node.unselectable = selectable ? "" : "on";
			@JS_NS@._dojo.query("*", node).forEach(function(descendant){
				descendant.unselectable = selectable ? "" : "on";
			});
		}
		//FIXME: else?  Opera?
	};

	var _insertBefore = function(/*Node*/node, /*Node*/ref){
		ref.parentNode.insertBefore(node, ref);
		return true;	//	boolean
	}

	var _insertAfter = function(/*Node*/node, /*Node*/ref){
		//	summary:
		//		Try to insert node after ref
		var pn = ref.parentNode;
		if(ref == pn.lastChild){
			pn.appendChild(node);
		}else{
			return _insertBefore(node, ref.nextSibling);	//	boolean
		}
		return true;	//	boolean
	}

	@JS_NS@._dojo.place = function(/*String|DomNode*/node, /*String|DomNode*/refNode, /*String|Number*/position){
		//	summary:
		//		attempt to insert node in relation to ref based on position
		//	node: 
		//		id or reference to node to place relative to refNode
		//	refNode: 
		//		id or reference of node to use as basis for placement
		//	position:
		//		string noting the position of node relative to refNode or a
		//		number indicating the location in the childNodes collection of
		//		refNode. Accepted string values are:
		//			* before
		//			* after
		//			* first
		//			* last
		//		"first" and "last" indicate positions as children of refNode.

		// FIXME: need to write tests for this!!!!
		if(!node || !refNode || position === undefined){ 
			return false;	//	boolean 
		}
		node = @JS_NS@._dojo.byId(node);
		refNode = @JS_NS@._dojo.byId(refNode);
		if(typeof position == "number"){
			var cn = refNode.childNodes;
			if((position == 0 && cn.length == 0) ||
				cn.length == position){
				refNode.appendChild(node); return true;
			}
			if(position == 0){
				return _insertBefore(node, refNode.firstChild);
			}
			return _insertAfter(node, cn[position-1]);
		}
		switch(position.toLowerCase()){
			case "before":
				return _insertBefore(node, refNode);	//	boolean
			case "after":
				return _insertAfter(node, refNode);		//	boolean
			case "first":
				if(refNode.firstChild){
					return _insertBefore(node, refNode.firstChild);	//	boolean
				}else{
					refNode.appendChild(node);
					return true;	//	boolean
				}
				break;
			default: // aka: last
				refNode.appendChild(node);
				return true;	//	boolean
		}
	}

	// Box functions will assume this model.
	// On IE/Opera, BORDER_BOX will be set if the primary document is in quirks mode.
	// Can be set to change behavior of box setters.
	
	// can be either:
	//	"border-box"
	//	"content-box" (default)
	@JS_NS@._dojo.boxModel = "content-box";
	
	// We punt per-node box mode testing completely.
	// If anybody cares, we can provide an additional (optional) unit 
	// that overrides existing code to include per-node box sensitivity.

	// Opera documentation claims that Opera 9 uses border-box in BackCompat mode.
	// but experiments (Opera 9.10.8679 on Windows Vista) indicate that it actually continues to use content-box.
	// IIRC, earlier versions of Opera did in fact use border-box.
	// Opera guys, this is really confusing. Opera being broken in quirks mode is not our fault.

	if(@JS_NS@._dojo.isIE /*|| @JS_NS@._dojo.isOpera*/){
		var _dcm = document.compatMode;
		// client code may have to adjust if compatMode varies across iframes
		@JS_NS@._dojo.boxModel = (_dcm=="BackCompat")||(_dcm=="QuirksMode")||(@JS_NS@._dojo.isIE<6) ? "border-box" : "content-box";
	}

	// =============================
	// Style Functions
	// =============================
	
	// getComputedStyle drives most of the style code.
	// Wherever possible, reuse the returned object.
	//
	// API functions below that need to access computed styles accept an 
	// optional computedStyle parameter.
	//
	// If this parameter is omitted, the functions will call getComputedStyle themselves.
	//
	// This way, calling code can access computedStyle once, and then pass the reference to 
	// multiple API functions. 
	//
	// This is a faux declaration to take pity on the doc tool

/*=====
	@JS_NS@._dojo.getComputedStyle = function(node){
		//	summary:
		//		Returns a "computed style" object.
		//	description:
		//		get "computed style" object which can be used to gather
		//		information about the current state of the rendered node. 
		//
		//		Note that this may behave differently on different browsers.
		//		Values may have different formats and value encodings across
		//		browsers. 
		//
		//		Use the @JS_NS@._dojo.style() method for more consistent (pixelized)
		//		return values.
		//	node: DOMNode
		//		a reference to a DOM node. Does NOT support taking an
		//		ID string for speed reasons.
		//	example:
		//	|	@JS_NS@._dojo.getComputedStyle(@JS_NS@._dojo.byId('foo')).borderWidth;
		return; // CSS2Properties
	}
=====*/

	var gcs, dv = document.defaultView;
	if(@JS_NS@._dojo.isSafari){
		gcs = function(/*DomNode*/node){
			var s = dv.getComputedStyle(node, null);
			if(!s && node.style){ 
				node.style.display = ""; 
				s = dv.getComputedStyle(node, null);
			}
			return s || {};
		}; 
	}else if(@JS_NS@._dojo.isIE){
		gcs = function(node){
			return node.currentStyle;
		};
	}else{
		gcs = function(node){
			return dv.getComputedStyle(node, null);
		};
	}
	@JS_NS@._dojo.getComputedStyle = gcs;

	if(!@JS_NS@._dojo.isIE){
		@JS_NS@._dojo._toPixelValue = function(element, value){
			// style values can be floats, client code may want
			// to round for integer pixels.
			return parseFloat(value) || 0; 
		}
	}else{
		@JS_NS@._dojo._toPixelValue = function(element, avalue){
			if(!avalue){ return 0; }
			// on IE7, medium is usually 4 pixels
			if(avalue=="medium"){ return 4; }
			// style values can be floats, client code may
			// want to round this value for integer pixels.
			if(avalue.slice && (avalue.slice(-2)=='px')){ return parseFloat(avalue); }
			with(element){
				var sLeft = style.left;
				var rsLeft = runtimeStyle.left;
				runtimeStyle.left = currentStyle.left;
				try{
					// 'avalue' may be incompatible with style.left, which can cause IE to throw
					// this has been observed for border widths using "thin", "medium", "thick" constants
					// those particular constants could be trapped by a lookup
					// but perhaps there are more
					style.left = avalue;
					avalue = style.pixelLeft;
				}catch(e){
					avalue = 0;
				}
				style.left = sLeft;
				runtimeStyle.left = rsLeft;
			}
			return avalue;
		}
	}

	// FIXME: there opacity quirks on FF that we haven't ported over. Hrm.
	/*=====
	@JS_NS@._dojo._getOpacity = function(node){
			//	summary:
			//		Returns the current opacity of the passed node as a
			//		floating-point value between 0 and 1.
			//	node: DomNode
			//		a reference to a DOM node. Does NOT support taking an
			//		ID string for speed reasons.
			//	return: Number between 0 and 1
	}
	=====*/

	@JS_NS@._dojo._getOpacity = (@JS_NS@._dojo.isIE ? function(node){
			try{
				return (node.filters.alpha.opacity / 100); // Number
			}catch(e){
				return 1; // Number
			}
		} : function(node){
			return @JS_NS@._dojo.getComputedStyle(node).opacity;
		}
	);

	/*=====
	@JS_NS@._dojo._setOpacity = function(node, opacity){
			//	summary:
			//		set the opacity of the passed node portably. Returns the
			//		new opacity of the node.
			//	node: DOMNode
			//		a reference to a DOM node. Does NOT support taking an
			//		ID string for performance reasons.
			//	opacity: Number
			//		A Number between 0 and 1. 0 specifies transparent.
			//	return: Number between 0 and 1
	}
	=====*/

	@JS_NS@._dojo._setOpacity = (@JS_NS@._dojo.isIE ? function(/*DomNode*/node, /*Number*/opacity){
			if(opacity == 1){
				// on IE7 Alpha(Filter opacity=100) makes text look fuzzy so remove it altogether (bug #2661)
				node.style.cssText = node.style.cssText.replace(/FILTER:[^;]*;/i, "");
				if(node.nodeName.toLowerCase() == "tr"){
					@JS_NS@._dojo.query("> td", node).forEach(function(i){
						i.style.cssText = i.style.cssText.replace(/FILTER:[^;]*;/i, "");
					});
				}
			}else{
				var o = "Alpha(Opacity="+(opacity*100)+")";
				node.style.filter = o;
			}
			if(node.nodeName.toLowerCase() == "tr"){
				@JS_NS@._dojo.query("> td", node).forEach(function(i){
					i.style.filter = o;
				});
			}
			return opacity;
		} : function(node, opacity){
			return node.style.opacity = opacity;
		}
	);

	var _pixelNamesCache = {
		width: true, height: true, left: true, top: true
	};
	var _toStyleValue = function(node, type, value){
		type = type.toLowerCase();
		if(_pixelNamesCache[type] === true){
			return @JS_NS@._dojo._toPixelValue(node, value)
		}else if(_pixelNamesCache[type] === false){
			return value;
		}else{
			if(@JS_NS@._dojo.isOpera && type == "cssText"){
				// FIXME: add workaround for #2855 here
			}
			if(
				(type.indexOf("margin") >= 0) ||
				// (type.indexOf("border") >= 0) ||
				(type.indexOf("padding") >= 0) ||
				(type.indexOf("width") >= 0) ||
				(type.indexOf("height") >= 0) ||
				(type.indexOf("max") >= 0) ||
				(type.indexOf("min") >= 0) ||
				(type.indexOf("offset") >= 0)
			){
				_pixelNamesCache[type] = true;
				return @JS_NS@._dojo._toPixelValue(node, value)
			}else{
				_pixelNamesCache[type] = false;
				return value;
			}
		}
	}

	// public API
	
	@JS_NS@._dojo.style = function(/*DomNode|String*/ node, /*String*/style, /*String?*/value){
		//	summary:
		//		gets or sets a style property on node. If 2 arguments are
		//		passed, acts as a getter. If value is passed, acts as a setter
		//		for the property.
		//	node:
		//		id or reference to node to get/set style for
		//	style:
		//		the style property to set in DOM-accessor format
		//		("borderWidth", not "border-width").
		//	value:
		//		optional. If passed, sets value on the node for style, handling
		//		cross-browser concerns.
		var n=@JS_NS@._dojo.byId(node), args=arguments.length, op=(style=="opacity");
		if(args==3){
			return op ? @JS_NS@._dojo._setOpacity(n, value) : n.style[style] = value; /*Number*/
		}
		if(args==2 && op){
			return @JS_NS@._dojo._getOpacity(n);
		}
		var s = @JS_NS@._dojo.getComputedStyle(n);
		return (args == 1) ? s : _toStyleValue(n, style, s[style]); /* CSS2Properties||String||Number */
	}

	// =============================
	// Box Functions
	// =============================

	@JS_NS@._dojo._getPadExtents = function(/*DomNode*/n, /*Object*/computedStyle){
		//	summary:
		// 		Returns object with special values specifically useful for node
		// 		fitting.
		// 			l/t = left/top padding (respectively)
		// 			w = the total of the left and right padding 
		// 			h = the total of the top and bottom padding
		//		If 'node' has position, l/t forms the origin for child nodes. 
		//		The w/h are used for calculating boxes.
		//		Normally application code will not need to invoke this
		//		directly, and will use the ...box... functions instead.
		var 
			s=computedStyle||gcs(n), 
			px=@JS_NS@._dojo._toPixelValue,
			l=px(n, s.paddingLeft), 
			t=px(n, s.paddingTop);
		return { 
			l: l,
			t: t,
			w: l+px(n, s.paddingRight),
			h: t+px(n, s.paddingBottom)
		};
	}

	@JS_NS@._dojo._getBorderExtents = function(/*DomNode*/n, /*Object*/computedStyle){
		//	summary:
		//		returns an object with properties useful for noting the border
		//		dimensions.
		// 			l/t = the sum of left/top border (respectively)
		//			w = the sum of the left and right border
		//			h = the sum of the top and bottom border
		//		The w/h are used for calculating boxes.
		//		Normally application code will not need to invoke this
		//		directly, and will use the ...box... functions instead.
		var 
			ne='none',
			px=@JS_NS@._dojo._toPixelValue, 
			s=computedStyle||gcs(n), 
			bl=(s.borderLeftStyle!=ne ? px(n, s.borderLeftWidth) : 0),
			bt=(s.borderTopStyle!=ne ? px(n, s.borderTopWidth) : 0);
		return { 
			l: bl,
			t: bt,
			w: bl + (s.borderRightStyle!=ne ? px(n, s.borderRightWidth) : 0),
			h: bt + (s.borderBottomStyle!=ne ? px(n, s.borderBottomWidth) : 0)
		};
	}

	@JS_NS@._dojo._getPadBorderExtents = function(/*DomNode*/n, /*Object*/computedStyle){
		//	summary:
		//		returns object with properties useful for box fitting with
		//		regards to padding.
		//			l/t = the sum of left/top padding and left/top border (respectively)
		//			w = the sum of the left and right padding and border
		//			h = the sum of the top and bottom padding and border
		//		The w/h are used for calculating boxes.
		//		Normally application code will not need to invoke this
		//		directly, and will use the ...box... functions instead.
		var 
			s=computedStyle||gcs(n), 
			p=@JS_NS@._dojo._getPadExtents(n, s),
			b=@JS_NS@._dojo._getBorderExtents(n, s);
		return { 
			l: p.l + b.l,
			t: p.t + b.t,
			w: p.w + b.w,
			h: p.h + b.h
		};
	}

	@JS_NS@._dojo._getMarginExtents = function(n, computedStyle){
		//	summary:
		//		returns object with properties useful for box fitting with
		//		regards to box margins (i.e., the outer-box).
		//			l/t = marginLeft, marginTop, respectively
		//			w = total width, margin inclusive
		//			h = total height, margin inclusive
		//		The w/h are used for calculating boxes.
		//		Normally application code will not need to invoke this
		//		directly, and will use the ...box... functions instead.
		var 
			s=computedStyle||gcs(n), 
			px=@JS_NS@._dojo._toPixelValue,
			l=px(n, s.marginLeft),
			t=px(n, s.marginTop),
			r=px(n, s.marginRight),
			b=px(n, s.marginBottom);
		if (@JS_NS@._dojo.isSafari && (s.position != "absolute")){
			// FIXME: Safari's version of the computed right margin
			// is the space between our right edge and the right edge 
			// of our offsetParent. 
			// What we are looking for is the actual margin value as 
			// determined by CSS.
			// Hack solution is to assume left/right margins are the same.
			r = l;
		}
		return { 
			l: l,
			t: t,
			w: l+r,
			h: t+b
		};
	}

	// Box getters work in any box context because offsetWidth/clientWidth
	// are invariant wrt box context
	//
	// They do *not* work for display: inline objects that have padding styles
	// because the user agent ignores padding (it's bogus styling in any case)
	//
	// Be careful with IMGs because they are inline or block depending on 
	// browser and browser mode.

	// Although it would be easier to read, there are not separate versions of 
	// _getMarginBox for each browser because:
	// 1. the branching is not expensive
	// 2. factoring the shared code wastes cycles (function call overhead)
	// 3. duplicating the shared code wastes bytes
	
	@JS_NS@._dojo._getMarginBox = function(/*DomNode*/node, /*Object*/computedStyle){
		// summary:
		//		returns an object that encodes the width, height, left and top
		//		positions of the node's margin box.
		var s = computedStyle||gcs(node), me = @JS_NS@._dojo._getMarginExtents(node, s);
		var	l = node.offsetLeft - me.l,	t = node.offsetTop - me.t;
		if(@JS_NS@._dojo.isMoz){
			// Mozilla:
			// If offsetParent has a computed overflow != visible, the offsetLeft is decreased
			// by the parent's border.
			// We don't want to compute the parent's style, so instead we examine node's
			// computed left/top which is more stable.
			var sl = parseFloat(s.left), st = parseFloat(s.top);
			if(!isNaN(sl) && !isNaN(st)){
				l = sl, t = st;
			}else{
				// If child's computed left/top are not parseable as a number (e.g. "auto"), we
				// have no choice but to examine the parent's computed style.
				var p = node.parentNode;
				if(p && p.style){
					var pcs = gcs(p);
					if(pcs.overflow != "visible"){
						var be = @JS_NS@._dojo._getBorderExtents(p, pcs);
						l += be.l, t += be.t;
					}
				}
			}
		}else if(@JS_NS@._dojo.isOpera){
			// On Opera, offsetLeft includes the parent's border
			var p = node.parentNode;
			if(p){
				var be = @JS_NS@._dojo._getBorderExtents(p);
				l -= be.l, t -= be.t;
			}
		}
		return { 
			l: l, 
			t: t, 
			w: node.offsetWidth + me.w, 
			h: node.offsetHeight + me.h 
		};
	}
	
	@JS_NS@._dojo._getContentBox = function(node, computedStyle){
		// summary:
		//		Returns an object that encodes the width, height, left and top
		//		positions of the node's content box, irrespective of the
		//		current box model.

		// clientWidth/Height are important since the automatically account for scrollbars
		// fallback to offsetWidth/Height for special cases (see #3378)
		var s=computedStyle||gcs(node), pe=@JS_NS@._dojo._getPadExtents(node, s), be=@JS_NS@._dojo._getBorderExtents(node, s), w=node.clientWidth, h;
		if(!w){
			w=node.offsetWidth, h=node.offsetHeight;
		}else{
			h=node.clientHeight, be.w = be.h = 0; 
		}
		// On Opera, offsetLeft includes the parent's border
		if(@JS_NS@._dojo.isOpera){ pe.l += be.l; pe.t += be.t; };
		return { 
			l: pe.l, 
			t: pe.t, 
			w: w - pe.w - be.w, 
			h: h - pe.h - be.h
		};
	}

	@JS_NS@._dojo._getBorderBox = function(node, computedStyle){
		var s=computedStyle||gcs(node), pe=@JS_NS@._dojo._getPadExtents(node, s), cb=@JS_NS@._dojo._getContentBox(node, s);
		return { 
			l: cb.l - pe.l, 
			t: cb.t - pe.t, 
			w: cb.w + pe.w, 
			h: cb.h + pe.h
		};
	}

	// Box setters depend on box context because interpretation of width/height styles
	// vary wrt box context.
	//
	// The value of @JS_NS@._dojo.boxModel is used to determine box context.
	// @JS_NS@._dojo.boxModel can be set directly to change behavior.
	//
	// Beware of display: inline objects that have padding styles
	// because the user agent ignores padding (it's a bogus setup anyway)
	//
	// Be careful with IMGs because they are inline or block depending on 
	// browser and browser mode.
	// 
	// Elements other than DIV may have special quirks, like built-in
	// margins or padding, or values not detectable via computedStyle.
	// In particular, margins on TABLE do not seems to appear 
	// at all in computedStyle on Mozilla.
	
	@JS_NS@._dojo._setBox = function(/*DomNode*/node, /*Number?*/l, /*Number?*/t, /*Number?*/w, /*Number?*/h, /*String?*/u){
		//	summary:
		//		sets width/height/left/top in the current (native) box-model
		//		dimentions. Uses the unit passed in u.
		//	node: DOM Node reference. Id string not supported for performance reasons.
		//	l: optional. left offset from parent.
		//	t: optional. top offset from parent.
		//	w: optional. width in current box model.
		//	h: optional. width in current box model.
		//	u: optional. unit measure to use for other measures. Defaults to "px".
		u = u || "px";
		with(node.style){
			if(!isNaN(l)){ left = l+u; }
			if(!isNaN(t)){ top = t+u; }
			if(w>=0){ width = w+u; }
			if(h>=0){ height = h+u; }
		}
	}

	@JS_NS@._dojo._usesBorderBox = function(/*DomNode*/node){
		//	summary: 
		//		True if the node uses border-box layout.

		// We could test the computed style of node to see if a particular box
		// has been specified, but there are details and we choose not to bother.
		var n = node.tagName;
		// For whatever reason, TABLE and BUTTON are always border-box by default.
		// If you have assigned a different box to either one via CSS then
		// box functions will break.
		return @JS_NS@._dojo.boxModel=="border-box" || n=="TABLE" || n=="BUTTON"; // boolean
	}

	@JS_NS@._dojo._setContentSize = function(/*DomNode*/node, /*Number*/widthPx, /*Number*/heightPx, /*Object*/computedStyle){
		//	summary:
		//		Sets the size of the node's contents, irrespective of margins,
		//		padding, or borders.
		var bb = @JS_NS@._dojo._usesBorderBox(node);
		if(bb){
			var pb = @JS_NS@._dojo._getPadBorderExtents(node, computedStyle);
			if(widthPx>=0){ widthPx += pb.w; }
			if(heightPx>=0){ heightPx += pb.h; }
		}
		@JS_NS@._dojo._setBox(node, NaN, NaN, widthPx, heightPx);
	}

	@JS_NS@._dojo._setMarginBox = function(/*DomNode*/node, 	/*Number?*/leftPx, /*Number?*/topPx, 
													/*Number?*/widthPx, /*Number?*/heightPx, 
													/*Object*/computedStyle){
		//	summary:
		//		sets the size of the node's margin box and palcement
		//		(left/top), irrespective of box model. Think of it as a
		//		passthrough to @JS_NS@._dojo._setBox that handles box-model vagaries for
		//		you.

		var s = computedStyle || @JS_NS@._dojo.getComputedStyle(node);
		// Some elements have special padding, margin, and box-model settings. 
		// To use box functions you may need to set padding, margin explicitly.
		// Controlling box-model is harder, in a pinch you might set @JS_NS@._dojo.boxModel.
		var bb=@JS_NS@._dojo._usesBorderBox(node),
				pb=bb ? _nilExtents : @JS_NS@._dojo._getPadBorderExtents(node, s),
				mb=@JS_NS@._dojo._getMarginExtents(node, s);
		if(widthPx>=0){	widthPx = Math.max(widthPx - pb.w - mb.w, 0); }
		if(heightPx>=0){ heightPx = Math.max(heightPx - pb.h - mb.h, 0); }
		@JS_NS@._dojo._setBox(node, leftPx, topPx, widthPx, heightPx);
	}
	
	var _nilExtents = { l:0, t:0, w:0, h:0 };

	// public API
	
	@JS_NS@._dojo.marginBox = function(/*DomNode|String*/node, /*Object?*/box){
		//	summary:
		//		getter/setter for the margin-box of node.
		//	description: 
		//		Returns an object in the expected format of box (regardless
		//		if box is passed). The object might look like:
		//			{ l: 50, t: 200, w: 300: h: 150 }
		//		for a node offset from its parent 50px to the left, 200px from
		//		the top with a margin width of 300px and a margin-height of
		//		150px.
		//	node:
		//		id or reference to DOM Node to get/set box for
		//	box:
		//		optional. If passed, denotes that @JS_NS@._dojo.marginBox() should
		//		update/set the margin box for node. Box is an object in the
		//		above format. All properties are optional if passed.
		var n=@JS_NS@._dojo.byId(node), s=gcs(n), b=box;
		return !b ? @JS_NS@._dojo._getMarginBox(n, s) : @JS_NS@._dojo._setMarginBox(n, b.l, b.t, b.w, b.h, s); // Object
	}

	@JS_NS@._dojo.contentBox = function(/*DomNode|String*/node, /*Object?*/box){
		//	summary:
		//		getter/setter for the content-box of node.
		//	description:
		//		Returns an object in the expected format of box (regardless if box is passed).
		//		The object might look like:
		//			{ l: 50, t: 200, w: 300: h: 150 }
		//		for a node offset from its parent 50px to the left, 200px from
		//		the top with a content width of 300px and a content-height of
		//		150px. Note that the content box may have a much larger border
		//		or margin box, depending on the box model currently in use and
		//		CSS values set/inherited for node.
		//	node:
		//		id or reference to DOM Node to get/set box for
		//	box:
		//		optional. If passed, denotes that @JS_NS@._dojo.contentBox() should
		//		update/set the content box for node. Box is an object in the
		//		above format. All properties are optional if passed.
		var n=@JS_NS@._dojo.byId(node), s=gcs(n), b=box;
		return !b ? @JS_NS@._dojo._getContentBox(n, s) : @JS_NS@._dojo._setContentSize(n, b.w, b.h, s); // Object
	}
	
	// =============================
	// Positioning 
	// =============================
	
	var _sumAncestorProperties = function(node, prop){
		if(!(node = (node||0).parentNode)){return 0};
		var val, retVal = 0, _b = @JS_NS@._dojo.body();
		while(node && node.style){
			if(gcs(node).position == "fixed"){
				return 0;
			}
			val = node[prop];
			if(val){
				retVal += val - 0;
				// opera and khtml #body & #html has the same values, we only
				// need one value
				if(node == _b){ break; }
			}
			node = node.parentNode;
		}
		return retVal;	//	integer
	}

	@JS_NS@._dojo._docScroll = function(){
		var _b = @JS_NS@._dojo.body();
		var _w = @JS_NS@._dojo.global;
		var de = @JS_NS@._dojo.doc.documentElement;
		return {
			y: (_w.pageYOffset || de.scrollTop || _b.scrollTop || 0),
			x: (_w.pageXOffset || @JS_NS@._dojo._fixIeBiDiScrollLeft(de.scrollLeft) || _b.scrollLeft || 0)
		};
	};
	
	@JS_NS@._dojo._isBodyLtr = function(){
		//FIXME: could check html and body tags directly instead of computed style?  need to ignore case, accept empty values
		return !("_bodyLtr" in @JS_NS@._dojo) ? 
			@JS_NS@._dojo._bodyLtr = @JS_NS@._dojo.getComputedStyle(@JS_NS@._dojo.body()).direction == "ltr" :
			@JS_NS@._dojo._bodyLtr; // Boolean 
	}
	
	@JS_NS@._dojo._getIeDocumentElementOffset = function(){
		// summary
		// The following values in IE contain an offset:
		//     event.clientX 
		//     event.clientY 
		//     node.getBoundingClientRect().left
		//     node.getBoundingClientRect().top
		// But other position related values do not contain this offset, such as
		// node.offsetLeft, node.offsetTop, node.style.left and node.style.top.
		// The offset is always (2, 2) in LTR direction. When the body is in RTL
		// direction, the offset counts the width of left scroll bar's width.
		// This function computes the actual offset.

		//NOTE: assumes we're being called in an IE browser

		var de = @JS_NS@._dojo.doc.documentElement;
		if(@JS_NS@._dojo.isIE >= 7){
			return {x: de.getBoundingClientRect().left, y: de.getBoundingClientRect().top}; // Object
		}else{
			// IE 6.0
			return {x: @JS_NS@._dojo._isBodyLtr() || window.parent == window ?
				de.clientLeft : de.offsetWidth - de.clientWidth - de.clientLeft, 
				y: de.clientTop}; // Object
		}
	};
	
	@JS_NS@._dojo._fixIeBiDiScrollLeft = function(/*Integer*/ scrollLeft){
		// In RTL direction, scrollLeft should be a negative value, but IE 
		// returns a positive one. All codes using documentElement.scrollLeft
		// must call this function to fix this error, otherwise the position
		// will offset to right when there is a horizonal scrollbar.
		if(@JS_NS@._dojo.isIE && !@JS_NS@._dojo._isBodyLtr()){
			var de = @JS_NS@._dojo.doc.documentElement;
			return scrollLeft + de.clientWidth - de.scrollWidth; // Integer
		}
		return scrollLeft; // Integer
	}
	
	@JS_NS@._dojo._abs = function(/*DomNode*/node, /*Boolean?*/includeScroll){
		//	summary:
		//		Gets the absolute position of the passed element based on the
		//		document itself. Returns an object of the form:
		//			{ x: 100, y: 300 }
		//		if includeScroll is passed, the x and y values will include any
		//		document offsets that may affect the position relative to the
		//		viewport.

		// FIXME: need to decide in the brave-new-world if we're going to be
		// margin-box or border-box.
		var ownerDocument = node.ownerDocument;
		var ret = {
			x: 0,
			y: 0
		};
		var hasScroll = false;

		// targetBoxType == "border-box"
		var db = @JS_NS@._dojo.body();
		if(@JS_NS@._dojo.isIE){
			var client = node.getBoundingClientRect();
			var offset = @JS_NS@._dojo._getIeDocumentElementOffset();
			ret.x = client.left - offset.x;
			ret.y = client.top - offset.y;
		}else if(ownerDocument["getBoxObjectFor"]){
			// mozilla
			var bo = ownerDocument.getBoxObjectFor(node);
			ret.x = bo.x - _sumAncestorProperties(node, "scrollLeft");
			ret.y = bo.y - _sumAncestorProperties(node, "scrollTop");
		}else{
			if(node["offsetParent"]){
				hasScroll = true;
				var endNode;
				// in Safari, if the node is an absolutely positioned child of
				// the body and the body has a margin the offset of the child
				// and the body contain the body's margins, so we need to end
				// at the body
				// FIXME: getting contrary results to the above in latest WebKit.
				if(@JS_NS@._dojo.isSafari &&
					//(node.style.getPropertyValue("position") == "absolute") &&
					(gcs(node).position == "absolute") &&
					(node.parentNode == db)){
					endNode = db;
				}else{
					endNode = db.parentNode;
				}
				if(node.parentNode != db){
					var nd = node;
					if(@JS_NS@._dojo.isOpera || (@JS_NS@._dojo.isSafari >= 3)){ nd = db; }
					ret.x -= _sumAncestorProperties(nd, "scrollLeft");
					ret.y -= _sumAncestorProperties(nd, "scrollTop");
				}
				var curnode = node;
				do{
					var n = curnode["offsetLeft"];
					//FIXME: ugly hack to workaround the submenu in 
					//popupmenu2 does not shown up correctly in opera. 
					//Someone have a better workaround?
					if(!@JS_NS@._dojo.isOpera || n>0){
						ret.x += isNaN(n) ? 0 : n;
					}
					var m = curnode["offsetTop"];
					ret.y += isNaN(m) ? 0 : m;
					curnode = curnode.offsetParent;
				}while((curnode != endNode)&&curnode);
			}else if(node["x"]&&node["y"]){
				ret.x += isNaN(node.x) ? 0 : node.x;
				ret.y += isNaN(node.y) ? 0 : node.y;
			}
		}
		// account for document scrolling
		// if offsetParent is used, ret value already includes scroll position
		// so we may have to actually remove that value if !includeScroll
		if(hasScroll || includeScroll){
			var scroll = @JS_NS@._dojo._docScroll();
			var m = hasScroll ? (!includeScroll ? -1 : 0) : 1;
			ret.y += m*scroll.y;
			ret.x += m*scroll.x;
		}

		return ret; // object
	}

	// FIXME: need a setter for coords or a moveTo!!
	@JS_NS@._dojo.coords = function(/*DomNode|String*/node, /*Boolean?*/includeScroll){
		//	summary:
		//		Returns an object that measures margin box width/height and
		//		absolute positioning data from @JS_NS@._dojo._abs(). Return value will
		//		be in the form:
		//			{ l: 50, t: 200, w: 300: h: 150, x: 100, y: 300 }
		//		does not act as a setter. If includeScroll is passed, the x and
		//		y params are affected as one would expect in @JS_NS@._dojo._abs().
		var n=@JS_NS@._dojo.byId(node), s=gcs(n), mb=@JS_NS@._dojo._getMarginBox(n, s);
		var abs = @JS_NS@._dojo._abs(n, includeScroll);
		mb.x = abs.x;
		mb.y = abs.y;
		return mb;
	}
})();

// =============================
// (CSS) Class Functions
// =============================

@JS_NS@._dojo.hasClass = function(/*DomNode|String*/node, /*String*/classStr){
	//	summary:
	//		Returns whether or not the specified classes are a portion of the
	//		class list currently applied to the node. 
	return ((" "+@JS_NS@._dojo.byId(node).className+" ").indexOf(" "+classStr+" ") >= 0);  // Boolean
};

@JS_NS@._dojo.addClass = function(/*DomNode|String*/node, /*String*/classStr){
	//	summary:
	//		Adds the specified classes to the end of the class list on the
	//		passed node.
	node = @JS_NS@._dojo.byId(node);
	var cls = node.className;
	if((" "+cls+" ").indexOf(" "+classStr+" ") < 0){
		node.className = cls + (cls ? ' ' : '') + classStr;
	}
};

@JS_NS@._dojo.removeClass = function(/*DomNode|String*/node, /*String*/classStr){
	// summary: Removes the specified classes from node.
	node = @JS_NS@._dojo.byId(node);
	var t = @JS_NS@._dojo.trim((" " + node.className + " ").replace(" " + classStr + " ", " "));
	if(node.className != t){ node.className = t; }
};

@JS_NS@._dojo.toggleClass = function(/*DomNode|String*/node, /*String*/classStr, /*Boolean?*/condition){
	//	summary: 	
	//		Adds a class to node if not present, or removes if present.
	//		Pass a boolean condition if you want to explicitly add or remove.
	//	condition:
	//		If passed, true means to add the class, false means to remove.
	if(condition === undefined){
		condition = !@JS_NS@._dojo.hasClass(node, classStr);
	}
	@JS_NS@._dojo[condition ? "addClass" : "removeClass"](node, classStr);
};

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.NodeList"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.NodeList"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.NodeList");



(function(){

	var d = @JS_NS@._dojo;

	var tnl = function(arr){
		arr.constructor = @JS_NS@._dojo.NodeList;
		@JS_NS@._dojo._mixin(arr, @JS_NS@._dojo.NodeList.prototype);
		return arr;
	}

	@JS_NS@._dojo.NodeList = function(){
		//	summary:
		//		@JS_NS@._dojo.NodeList is as subclass of Array which adds syntactic 
		//		sugar for chaining, common iteration operations, animation, 
		//		and node manipulation. NodeLists are most often returned as
		//		the result of @JS_NS@._dojo.query() calls.
		//	example:
		//		create a node list from a node
		//		|	new @JS_NS@._dojo.NodeList(@JS_NS@._dojo.byId("foo"));

		return tnl(Array.apply(null, arguments));
	}

	@JS_NS@._dojo.NodeList._wrap = tnl;

	@JS_NS@._dojo.extend(@JS_NS@._dojo.NodeList, {
		// http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array#Methods

		// FIXME: handle return values for #3244
		//		http://trac.dojotoolkit.org/ticket/3244
		
		// FIXME:
		//		need to wrap or implement:
		//			join (perhaps w/ innerHTML/outerHTML overload for toString() of items?)
		//			reduce
		//			reduceRight

		slice: function(/*===== begin, end =====*/){
			// summary:
			//		Returns a new NodeList, maintaining this one in place
			// description:
			//		This method behaves exactly like the Array.slice method
			//		with the caveat that it returns a @JS_NS@._dojo.NodeList and not a
			//		raw Array. For more details, see:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:slice
			// begin: Integer
			//		Can be a positive or negative integer, with positive
			//		integers noting the offset to begin at, and negative
			//		integers denoting an offset from the end (i.e., to the left
			//		of the end)
			// end: Integer?
			//		Optional parameter to describe what position relative to
			//		the NodeList's zero index to end the slice at. Like begin,
			//		can be positive or negative.
			var a = @JS_NS@._dojo._toArray(arguments);
			return tnl(a.slice.apply(this, a));
		},

		splice: function(/*===== index, howmany, item =====*/){
			// summary:
			//		Returns a new NodeList, manipulating this NodeList based on
			//		the arguments passed, potentially splicing in new elements
			//		at an offset, optionally deleting elements
			// description:
			//		This method behaves exactly like the Array.splice method
			//		with the caveat that it returns a @JS_NS@._dojo.NodeList and not a
			//		raw Array. For more details, see:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:splice
			// index: Integer
			//		begin can be a positive or negative integer, with positive
			//		integers noting the offset to begin at, and negative
			//		integers denoting an offset from the end (i.e., to the left
			//		of the end)
			// howmany: Integer?
			//		Optional parameter to describe what position relative to
			//		the NodeList's zero index to end the slice at. Like begin,
			//		can be positive or negative.
			// item: Object...?
			//		Any number of optional parameters may be passed in to be
			//		spliced into the NodeList
			// returns:
			//		@JS_NS@._dojo.NodeList
			var a = @JS_NS@._dojo._toArray(arguments);
			return tnl(a.splice.apply(this, a));
		},

		concat: function(/*===== item =====*/){
			// summary:
			//		Returns a new NodeList comprised of items in this NodeList
			//		as well as items passed in as parameters
			// description:
			//		This method behaves exactly like the Array.concat method
			//		with the caveat that it returns a @JS_NS@._dojo.NodeList and not a
			//		raw Array. For more details, see:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:concat
			// item: Object...?
			//		Any number of optional parameters may be passed in to be
			//		spliced into the NodeList
			// returns:
			//		@JS_NS@._dojo.NodeList
			var a = @JS_NS@._dojo._toArray(arguments, 0, [this]);
			return tnl(a.concat.apply([], a));
		},
		
		indexOf: function(/*Object*/ value, /*Integer?*/ fromIndex){
			//	summary:
			//		see @JS_NS@._dojo.indexOf(). The primary difference is that the acted-on 
			//		array is implicitly this NodeList
			// value:
			//		The value to search for.
			// fromIndex:
			//		The loction to start searching from. Optional. Defaults to 0.
			//	description:
			//		For more details on the behavior of indexOf, see:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:indexOf
			//	returns:
			//		Positive Integer or 0 for a match, -1 of not found.
			return d.indexOf(this, value, fromIndex); // Integer
		},

		lastIndexOf: function(/*===== value, fromIndex =====*/){
			// summary:
			//		see @JS_NS@._dojo.lastIndexOf(). The primary difference is that the
			//		acted-on array is implicitly this NodeList
			//	description:
			//		For more details on the behavior of lastIndexOf, see:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:lastIndexOf
			// value: Object
			//		The value to search for.
			// fromIndex: Integer?
			//		The loction to start searching from. Optional. Defaults to 0.
			// returns:
			//		Positive Integer or 0 for a match, -1 of not found.
			return d.lastIndexOf.apply(d, d._toArray(arguments, 0, [this])); // Integer
		},

		every: function(/*Function*/callback, /*Object?*/thisObject){
			//	summary:
			//		see @JS_NS@._dojo.every() and:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:every
			//		Takes the same structure of arguments and returns as
			//		@JS_NS@._dojo.every() with the caveat that the passed array is
			//		implicitly this NodeList
			return d.every(this, callback, thisObject); // Boolean
		},

		some: function(/*Function*/callback, /*Object?*/thisObject){
			//	summary:
			//		see @JS_NS@._dojo.some() and:
			//			http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Array:some
			//		Takes the same structure of arguments and returns as
			//		@JS_NS@._dojo.some() with the caveat that the passed array is
			//		implicitly this NodeList
			return d.some(this, callback, thisObject); // Boolean
		},

		map: function(/*Function*/ func, /*Function?*/ obj){
			//	summary:
			//		see @JS_NS@._dojo.map(). The primary difference is that the acted-on
			//		array is implicitly this NodeList and the return is a
			//		@JS_NS@._dojo.NodeList (a subclass of Array)

			return d.map(this, func, obj, d.NodeList); // @JS_NS@._dojo.NodeList
		},

		forEach: function(callback, thisObj){
			//	summary:
			//		see @JS_NS@._dojo.forEach(). The primary difference is that the acted-on 
			//		array is implicitly this NodeList

			d.forEach(this, callback, thisObj);
			return this; // @JS_NS@._dojo.NodeList non-standard return to allow easier chaining
		},

		// custom methods
		
		coords: function(){
			//	summary:
			// 		Returns the box objects all elements in a node list as
			// 		an Array (*not* a NodeList)
			
			return d.map(this, d.coords);
		},

		style: function(/*===== property, value =====*/){
			//	summary:
			//		gets or sets the CSS property for every element in the NodeList
			//	property: String
			//		the CSS property to get/set, in JavaScript notation
			//		("lineHieght" instead of "line-height") 
			//	value: String?
			//		optional. The value to set the property to
			//	return:
			//		if no value is passed, the result is an array of strings.
			//		If a value is passed, the return is this NodeList
			var aa = d._toArray(arguments, 0, [null]);
			var s = this.map(function(i){
				aa[0] = i;
				return d.style.apply(d, aa);
			});
			return (arguments.length > 1) ? this : s; // String||@JS_NS@._dojo.NodeList
		},

		styles: function(/*===== property, value =====*/){
			//	summary:
			//		Deprecated. Use NodeList.style instead. Will be removed in
			//		Dojo 1.1. Gets or sets the CSS property for every element
			//		in the NodeList
			//	property: String
			//		the CSS property to get/set, in JavaScript notation
			//		("lineHieght" instead of "line-height") 
			//	value: String?
			//		optional. The value to set the property to
			//	return:
			//		if no value is passed, the result is an array of strings.
			//		If a value is passed, the return is this NodeList
			d.deprecated("NodeList.styles", "use NodeList.style instead", "1.1");
			return this.style.apply(this, arguments);
		},

		addClass: function(/*String*/ className){
			// summary:
			//		adds the specified class to every node in the list
			//
			this.forEach(function(i){ d.addClass(i, className); });
			return this;
		},

		removeClass: function(/*String*/ className){
			this.forEach(function(i){ d.removeClass(i, className); });
			return this;
		},

		// FIXME: toggleClass()? connectPublisher()? connectRunOnce()?

		place: function(/*String||Node*/ queryOrNode, /*String*/ position){
			//	summary:
			//		places elements of this node list relative to the first element matched
			//		by queryOrNode. Returns the original NodeList.
			//	queryOrNode:
			//		may be a string representing any valid CSS3 selector or a DOM node.
			//		In the selector case, only the first matching element will be used 
			//		for relative positioning.
			//	position:
			//		can be one of:
			//			"last"||"end" (default)
			//			"first||"start"
			//			"before"
			//			"after"
			// 		or an offset in the childNodes property
			var item = d.query(queryOrNode)[0];
			position = position||"last";

			for(var x=0; x<this.length; x++){
				d.place(this[x], item, position);
			}
			return this; // @JS_NS@._dojo.NodeList
		},

		connect: function(/*String*/ methodName, /*Object||Function||String*/ objOrFunc, /*String?*/ funcName){
			//	summary:
			//		attach event handlers to every item of the NodeList. Uses @JS_NS@._dojo.connect()
			//		so event properties are normalized
			//	methodName:
			//		the name of the method to attach to. For DOM events, this should be
			//		the lower-case name of the event
			//	objOrFunc:
			//		if 2 arguments are passed (methodName, objOrFunc), objOrFunc should
			//		reference a function or be the name of the function in the global
			//		namespace to attach. If 3 arguments are provided
			//		(methodName, objOrFunc, funcName), objOrFunc must be the scope to 
			//		locate the bound function in
			//	funcName:
			//		optional. A string naming the function in objOrFunc to bind to the
			//		event. May also be a function reference.
			//	example:
			//		add an onclick handler to every button on the page
			//		|	@JS_NS@._dojo.query("onclick", function(e){
			//		|		console.debug("clicked!");
			//		|	});
			// example:
			//		attach foo.bar() to every odd div's onmouseover
			//		|	@JS_NS@._dojo.query("div:nth-child(odd)").onclick("onmouseover", foo, "bar");
			this.forEach(function(item){
				d.connect(item, methodName, objOrFunc, funcName);
			});
			return this; // @JS_NS@._dojo.NodeList
		},

		orphan: function(/*String?*/ simpleFilter){
			//	summary:
			//		removes elements in this list that match the simple
			//		filter from their parents and returns them as a new
			//		NodeList.
			//	simpleFilter: single-expression CSS filter
			//	return: a @JS_NS@._dojo.NodeList of all of the elements orpahned
			var orphans = (simpleFilter) ? d._filterQueryResult(this, simpleFilter) : this;
			orphans.forEach(function(item){
				if(item["parentNode"]){
					item.parentNode.removeChild(item);
				}
			});
			return orphans; // @JS_NS@._dojo.NodeList
		},

		adopt: function(/*String||Array||DomNode*/ queryOrListOrNode, /*String?*/ position){
			//	summary:
			//		places any/all elements in queryOrListOrNode at a
			//		position relative to the first element in this list.
			//		Returns a @JS_NS@._dojo.NodeList of the adopted elements.
			//	queryOrListOrNode:
			//		a DOM node or a query string or a query result.
			//		Represents the nodes to be adopted relative to the
			//		first element of this NodeList.
			//	position:
			//		optional. One of:
			//			"last"||"end" (default)
			//			"first||"start"
			//			"before"
			//			"after"
			// 		or an offset in the childNodes property
			var item = this[0];
			return d.query(queryOrListOrNode).forEach(function(ai){ d.place(ai, item, (position||"last")); }); // @JS_NS@._dojo.NodeList
		},

		// FIXME: do we need this?
		query: function(/*String*/ queryStr){
			//	summary:
			//		Returns a new, flattened NodeList. Elements of the new list
			//		satisfy the passed query but use elements of the
			//		current NodeList as query roots.

			queryStr = queryStr||"";

			// FIXME: probably slow
			var ret = d.NodeList();
			this.forEach(function(item){
				d.query(queryStr, item).forEach(function(subItem){
					if(typeof subItem != "undefined"){
						ret.push(subItem);
					}
				});
			});
			return ret; // @JS_NS@._dojo.NodeList
		},

		filter: function(/*String*/ simpleQuery){
			//	summary:
			// 		"masks" the built-in javascript filter() method to support
			//		passing a simple string filter in addition to supporting
			//		filtering function objects.
			//	example:
			//		"regular" JS filter syntax as exposed in @JS_NS@._dojo.filter:
			//		|	@JS_NS@._dojo.query("*").filter(function(item){
			//		|		// highlight every paragraph
			//		|		return (item.nodeName == "p");
			//		|	}).styles("backgroundColor", "yellow");
			// example:
			//		the same filtering using a CSS selector
			//		|	@JS_NS@._dojo.query("*").filter("p").styles("backgroundColor", "yellow");

			var items = this;
			var _a = arguments;
			var r = d.NodeList();
			var rp = function(t){ 
				if(typeof t != "undefined"){
					r.push(t); 
				}
			}
			if(d.isString(simpleQuery)){
				items = d._filterQueryResult(this, _a[0]);
				if(_a.length == 1){
					// if we only got a string query, pass back the filtered results
					return items; // @JS_NS@._dojo.NodeList
				}
				// if we got a callback, run it over the filtered items
				d.forEach(d.filter(items, _a[1], _a[2]), rp);
				return r; // @JS_NS@._dojo.NodeList
			}
			// handle the (callback, [thisObject]) case
			d.forEach(d.filter(items, _a[0], _a[1]), rp);
			return r; // @JS_NS@._dojo.NodeList

		},
		
		/*
		// FIXME: should this be "copyTo" and include parenting info?
		clone: function(){
			// summary:
			//		creates node clones of each element of this list
			//		and returns a new list containing the clones
		},
		*/

		addContent: function(/*String*/ content, /*String||Integer?*/ position){
			//	summary:
			//		add a node or some HTML as a string to every item in the list. 
			//		Returns the original list.
			//	content:
			//		the HTML in string format to add at position to every item
			//	position:
			//		One of:
			//			"last"||"end" (default)
			//			"first||"start"
			//			"before"
			//			"after"
			//		or an integer offset in the childNodes property
			var ta = d.doc.createElement("span");
			if(d.isString(content)){
				ta.innerHTML = content;
			}else{
				ta.appendChild(content);
			}
			var ct = ((position == "first")||(position == "after")) ? "lastChild" : "firstChild";
			this.forEach(function(item){
				var tn = ta.cloneNode(true);
				while(tn[ct]){
					d.place(tn[ct], item, position);
				}
			});
			return this; // @JS_NS@._dojo.NodeList
		}
	});

	// syntactic sugar for DOM events
	d.forEach([
		"blur", "click", "keydown", "keypress", "keyup", "mousedown", "mouseenter",
		"mouseleave", "mousemove", "mouseout", "mouseover", "mouseup"
		], function(evt){
			var _oe = "on"+evt;
			@JS_NS@._dojo.NodeList.prototype[_oe] = function(a, b){
				return this.connect(_oe, a, b);
			}
				// FIXME: should these events trigger publishes?
				/*
				return (a ? this.connect(_oe, a, b) : 
							this.forEach(function(n){  
								// FIXME:
								//		listeners get buried by
								//		addEventListener and can't be dug back
								//		out to be triggered externally.
								// see:
								//		http://developer.mozilla.org/en/docs/DOM:element

								console.debug(n, evt, _oe);

								// FIXME: need synthetic event support!
								var _e = { target: n, faux: true, type: evt };
								// @JS_NS@._dojo._event_listener._synthesizeEvent({}, { target: n, faux: true, type: evt });
								try{ n[evt](_e); }catch(e){ console.debug(e); }
								try{ n[_oe](_e); }catch(e){ console.debug(e); }
							})
				);
			}
			*/
		}
	);

})();

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.query"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.query"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.query");


/*
	@JS_NS@._dojo.query() architectural overview:

		@JS_NS@._dojo.query is a relatively full-featured CSS3 query library. It is
		designed to take any valid CSS3 selector and return the nodes matching
		the selector. To do this quickly, it processes queries in several
		steps, applying caching where profitable.
		
		The steps (roughly in reverse order of the way they appear in the code):
			1.) check to see if we already have a "query dispatcher"
				- if so, use that with the given parameterization. Skip to step 4.
			2.) attempt to determine which branch to dispatch the query to:
				- JS (optimized DOM iteration)
				- xpath (for browsers that support it and where it's fast)
				- native (not available in any browser yet)
			3.) tokenize and convert to executable "query dispatcher"
				- this is where the lion's share of the complexity in the
				  system lies. In the DOM version, the query dispatcher is
				  assembled as a chain of "yes/no" test functions pertaining to
				  a section of a simple query statement (".blah:nth-child(odd)"
				  but not "div div", which is 2 simple statements). Individual
				  statement dispatchers are cached (to prevent re-definition)
				  as are entire dispatch chains (to make re-execution of the
				  same query fast)
				- in the xpath path, tokenization yeilds a concatenation of
				  parameterized xpath selectors. As with the DOM version, both
				  simple selector blocks and overall evaluators are cached to
				  prevent re-defintion
			4.) the resulting query dispatcher is called in the passed scope (by default the top-level document)
				- for DOM queries, this results in a recursive, top-down
				  evaluation of nodes based on each simple query section
				- xpath queries can, thankfully, be executed in one shot
			5.) matched nodes are pruned to ensure they are unique
*/

;(function(){
	// define everything in a closure for compressability reasons. "d" is an
	// alias to "@JS_NS@._dojo" since it's so frequently used. This seems a
	// transformation that the build system could perform on a per-file basis.

	////////////////////////////////////////////////////////////////////////
	// Utility code
	////////////////////////////////////////////////////////////////////////

	var d = @JS_NS@._dojo;
	var childNodesName = @JS_NS@._dojo.isIE ? "children" : "childNodes";

	var getQueryParts = function(query){
		// summary: state machine for query tokenization
		if(query.charAt(query.length-1) == ">"){
			query += " *"
		}
		query += " "; // ensure that we terminate the state machine

		var ts = function(s, e){
			return d.trim(query.slice(s, e));
		}

		// the overall data graph of the full query, as represented by queryPart objects
		var qparts = []; 
		// state keeping vars
		var inBrackets = -1;
		var inParens = -1;
		var inMatchFor = -1;
		var inPseudo = -1;
		var inClass = -1;
		var inId = -1;
		var inTag = -1;
		var lc = ""; // the last character
		var cc = ""; // the current character
		var pStart;
		// iteration vars
		var x = 0; // index in the query
		var ql = query.length;
		var currentPart = null; // data structure representing the entire clause
		var _cp = null; // the current pseudo or attr matcher

		var endTag = function(){
			if(inTag >= 0){
				var tv = (inTag == x) ? null : ts(inTag, x).toLowerCase();
				currentPart[ (">~+".indexOf(tv) < 0)? "tag" : "oper" ] = tv;
				inTag = -1;
			}
		}

		var endId = function(){
			if(inId >= 0){
				currentPart.id = ts(inId, x).replace(/\\/g, "");
				inId = -1;
			}
		}

		var endClass = function(){
			if(inClass >= 0){
				currentPart.classes.push(ts(inClass+1, x).replace(/\\/g, ""));
				inClass = -1;
			}
		}

		var endAll = function(){
			endId(); endTag(); endClass();
		}

		for(; x<ql, lc=cc, cc=query.charAt(x); x++){
			if(lc == "\\"){ continue; }
			if(!currentPart){
				// NOTE: I hate all this alloc, but it's shorter than writing tons of if's
				pStart = x;
				currentPart = {
					query: null,
					pseudos: [],
					attrs: [],
					classes: [],
					tag: null,
					oper: null,
					id: null
				};
				inTag = x;
			}

			if(inBrackets >= 0){
				// look for a the close first
				if(cc == "]"){
					if(!_cp.attr){
						_cp.attr = ts(inBrackets+1, x);
					}else{
						_cp.matchFor = ts((inMatchFor||inBrackets+1), x);
					}
					var cmf = _cp.matchFor;
					if(cmf){
						if(	(cmf.charAt(0) == '"') || (cmf.charAt(0)  == "'") ){
							_cp.matchFor = cmf.substring(1, cmf.length-1);
						}
					}
					currentPart.attrs.push(_cp);
					_cp = null; // necessaray?
					inBrackets = inMatchFor = -1;
				}else if(cc == "="){
					var addToCc = ("|~^$*".indexOf(lc) >=0 ) ? lc : "";
					_cp.type = addToCc+cc;
					_cp.attr = ts(inBrackets+1, x-addToCc.length);
					inMatchFor = x+1;
				}
				// now look for other clause parts
			}else if(inParens >= 0){
				if(cc == ")"){
					if(inPseudo >= 0){
						_cp.value = ts(inParens+1, x);
					}
					inPseudo = inParens = -1;
				}
			}else if(cc == "#"){
				endAll();
				inId = x+1;
			}else if(cc == "."){
				endAll();
				inClass = x;
			}else if(cc == ":"){
				endAll();
				inPseudo = x;
			}else if(cc == "["){
				endAll();
				inBrackets = x;
				_cp = {
					/*=====
					attr: null, type: null, matchFor: null
					=====*/
				};
			}else if(cc == "("){
				if(inPseudo >= 0){
					_cp = { 
						name: ts(inPseudo+1, x), 
						value: null
					}
					currentPart.pseudos.push(_cp);
				}
				inParens = x;
			}else if(cc == " " && lc != cc){
				// note that we expect the string to be " " terminated
				endAll();
				if(inPseudo >= 0){
					currentPart.pseudos.push({ name: ts(inPseudo+1, x) });
				}
				currentPart.hasLoops = (	
						currentPart.pseudos.length || 
						currentPart.attrs.length || 
						currentPart.classes.length	);
				currentPart.query = ts(pStart, x);
				currentPart.tag = (currentPart["oper"]) ? null : (currentPart.tag || "*");
				qparts.push(currentPart);
				currentPart = null;
			}
		}
		return qparts;
	};
	

	////////////////////////////////////////////////////////////////////////
	// XPath query code
	////////////////////////////////////////////////////////////////////////

	// this array is a lookup used to generate an attribute matching function.
	// There is a similar lookup/generator list for the DOM branch with similar
	// calling semantics.
	var xPathAttrs = {
		"*=": function(attr, value){
			return "[contains(@"+attr+", '"+ value +"')]";
		},
		"^=": function(attr, value){
			return "[starts-with(@"+attr+", '"+ value +"')]";
		},
		"$=": function(attr, value){
			return "[substring(@"+attr+", string-length(@"+attr+")-"+(value.length-1)+")='"+value+"']";
		},
		"~=": function(attr, value){
			return "[contains(concat(' ',@"+attr+",' '), ' "+ value +" ')]";
		},
		"|=": function(attr, value){
			return "[contains(concat(' ',@"+attr+",' '), ' "+ value +"-')]";
		},
		"=": function(attr, value){
			return "[@"+attr+"='"+ value +"']";
		}
	};

	// takes a list of attribute searches, the overall query, a function to
	// generate a default matcher, and a closure-bound method for providing a
	// matching function that generates whatever type of yes/no distinguisher
	// the query method needs. The method is a bit tortured and hard to read
	// because it needs to be used in both the XPath and DOM branches.
	var handleAttrs = function(	attrList, 
								query, 
								getDefault, 
								handleMatch){
		d.forEach(query.attrs, function(attr){
			var matcher;
			// type, attr, matchFor
			if(attr.type && attrList[attr.type]){
				matcher = attrList[attr.type](attr.attr, attr.matchFor);
			}else if(attr.attr.length){
				matcher = getDefault(attr.attr);
			}
			if(matcher){ handleMatch(matcher); }
		});
	}

	var buildPath = function(query){
		var xpath = ".";
		var qparts = getQueryParts(d.trim(query));
		while(qparts.length){
			var tqp = qparts.shift();
			var prefix;
			// FIXME: need to add support for ~ and +
			if(tqp.oper == ">"){
				prefix = "/";
				// prefix = "/child::node()";
				tqp = qparts.shift();
			}else{
				prefix = "//";
				// prefix = "/descendant::node()"
			}

			// get the tag name (if any)

			xpath += prefix + tqp.tag;
			
			// check to see if it's got an id. Needs to come first in xpath.
			if(tqp.id){
				xpath += "[@id='"+tqp.id+"'][1]";
			}

			d.forEach(tqp.classes, function(cn){
				var cnl = cn.length;
				var padding = " ";
				if(cn.charAt(cnl-1) == "*"){
					padding = ""; cn = cn.substr(0, cnl-1);
				}
				xpath += 
					"[contains(concat(' ',@class,' '), ' "+
					cn + padding + "')]";
			});

			handleAttrs(xPathAttrs, tqp, 
				function(condition){
						return "[@"+condition+"]";
				},
				function(matcher){
					xpath += matcher;
				}
			);

			// FIXME: need to implement pseudo-class checks!!
		};
		return xpath;
	};

	var _xpathFuncCache = {};
	var getXPathFunc = function(path){
		if(_xpathFuncCache[path]){
			return _xpathFuncCache[path];
		}

		var doc = d.doc;
		// var parent = d.body(); // FIXME
		// FIXME: don't need to memoize. The closure scope handles it for us.
		var xpath = buildPath(path);

		var tf = function(parent){
			// XPath query strings are memoized.
			var ret = [];
			var xpathResult;
			try{
				xpathResult = doc.evaluate(xpath, parent, null, 
												// XPathResult.UNORDERED_NODE_ITERATOR_TYPE, null);
												XPathResult.ANY_TYPE, null);
			}catch(e){
				console.debug("failure in exprssion:", xpath, "under:", parent);
				console.debug(e);
			}
			var result = xpathResult.iterateNext();
			while(result){
				ret.push(result);
				result = xpathResult.iterateNext();
			}
			return ret;
		}
		return _xpathFuncCache[path] = tf;
	};

	/*
	d.xPathMatch = function(query){
		// XPath based DOM query system. Handles a small subset of CSS
		// selectors, subset is identical to the non-XPath version of this
		// function. 

		// FIXME: need to add support for alternate roots
		return getXPathFunc(query)();
	}
	*/

	////////////////////////////////////////////////////////////////////////
	// DOM query code
	////////////////////////////////////////////////////////////////////////

	var _filtersCache = {};
	var _simpleFiltersCache = {};

	// the basic building block of the yes/no chaining system. agree(f1, f2)
	// generates a new function which returns the boolean results of both of
	// the passed functions to a single logical-anded result.
	var agree = function(first, second){
		if(!first){ return second; }
		if(!second){ return first; }

		return function(){
			return first.apply(window, arguments) && second.apply(window, arguments);
		}
	}

	var _filterDown = function(element, queryParts, matchArr, idx){
		var nidx = idx+1;
		var isFinal = (queryParts.length == nidx);
		var tqp = queryParts[idx];

		// see if we can constrain our next level to direct children
		if(tqp.oper == ">"){
			var ecn = element[childNodesName];
			if(!ecn || !ecn.length){
				return;
			}
			nidx++;
			isFinal = (queryParts.length == nidx);
			// kinda janky, too much array alloc
			var tf = getFilterFunc(queryParts[idx+1]);
			// for(var x=ecn.length-1, te; x>=0, te=ecn[x]; x--){
			for(var x=0, ecnl=ecn.length, te; x<ecnl, te=ecn[x]; x++){
				if(tf(te)){
					if(isFinal){
						matchArr.push(te);
					}else{
						_filterDown(te, queryParts, matchArr, nidx);
					}
				}
				/*
				if(x==0){
					break;
				}
				*/
			}
		}

		// otherwise, keep going down, unless we'er at the end
		var candidates = getElementsFunc(tqp)(element);
		if(isFinal){
			while(candidates.length){
				matchArr.push(candidates.shift());
			}
			/*
			candidates.unshift(0, matchArr.length-1);
			matchArr.splice.apply(matchArr, candidates);
			*/
		}else{
			// if we're not yet at the bottom, keep going!
			while(candidates.length){
				_filterDown(candidates.shift(), queryParts, matchArr, nidx);
			}
		}
	}

	var filterDown = function(elements, queryParts){
		var ret = [];

		// for every root, get the elements that match the descendant selector
		// for(var x=elements.length-1, te; x>=0, te=elements[x]; x--){
		var x = elements.length - 1, te;
		while(te = elements[x--]){
			_filterDown(te, queryParts, ret, 0);
		}
		return ret;
	}

	var getFilterFunc = function(q){
		// note: query can't have spaces!
		if(_filtersCache[q.query]){
			return _filtersCache[q.query];
		}
		var ff = null;

		// does it have a tagName component?
		if(q.tag){
			if(q.tag == "*"){
				ff = agree(ff, 
					function(elem){
						return (elem.nodeType == 1);
					}
				);
			}else{
				// tag name match
				ff = agree(ff, 
					function(elem){
						return (
							(elem.nodeType == 1) &&
							(q.tag == elem.tagName.toLowerCase())
						);
						// return isTn;
					}
				);
			}
		}

		// does the node have an ID?
		if(q.id){
			ff = agree(ff, 
				function(elem){
					return (
						(elem.nodeType == 1) &&
						(elem.id == q.id)
					);
				}
			);
		}

		if(q.hasLoops){
			// if we have other query param parts, make sure we add them to the
			// filter chain
			ff = agree(ff, getSimpleFilterFunc(q));
		}

		return _filtersCache[q.query] = ff;
	}

	var getNodeIndex = function(node){
		// NOTE: 
		//		we could have a more accurate caching mechanism by invalidating
		//		caches after the query has finished, but I think that'd lead to
		//		significantly more cache churn than the cache would provide
		//		value for in the common case. Generally, we're more
		//		conservative (and therefore, more accurate) than jQuery and
		//		DomQuery WRT node node indexes, but there may be corner cases
		//		in which we fall down.  How much we care about them is TBD.

		var pn = node.parentNode;
		var pnc = pn.childNodes;

		// check to see if we can trust the cache. If not, re-key the whole
		// thing and return our node match from that.

		var nidx = -1;
		var child = pn.firstChild;
		if(!child){
			return nidx;
		}

		var ci = node["__cachedIndex"];
		var cl = pn["__cachedLength"];

		// only handle cache building if we've gone out of sync
		if(((typeof cl == "number")&&(cl != pnc.length))||(typeof ci != "number")){
			// rip though the whole set, building cache indexes as we go
			pn["__cachedLength"] = pnc.length;
			var idx = 1;
			do{
				// we only assign indexes for nodes with nodeType == 1, as per:
				//		http://www.w3.org/TR/css3-selectors/#nth-child-pseudo
				// only elements are counted in the search order, and they
				// begin at 1 for the first child's index

				if(child === node){
					nidx = idx;
				}
				if(child.nodeType == 1){
					child["__cachedIndex"] = idx;
					idx++;
				}
				child = child.nextSibling;
			}while(child);
		}else{
			// NOTE: 
			//		could be incorrect in some cases (node swaps involving the
			//		passed node, etc.), but we ignore those due to the relative
			//		unlikelihood of that occuring
			nidx = ci;
		}
		return nidx;
	}

	var firedCount = 0;

	var blank = "";
	var _getAttr = function(elem, attr){
		if(attr == "class"){
			return elem.className || blank;
		}
		if(attr == "for"){
			return elem.htmlFor || blank;
		}
		return elem.getAttribute(attr, 2) || blank;
	}

	var attrs = {
		"*=": function(attr, value){
			return function(elem){
				// E[foo*="bar"]
				//		an E element whose "foo" attribute value contains
				//		the substring "bar"
				return (_getAttr(elem, attr).indexOf(value)>=0);
			}
		},
		"^=": function(attr, value){
			// E[foo^="bar"]
			//		an E element whose "foo" attribute value begins exactly
			//		with the string "bar"
			return function(elem){
				return (_getAttr(elem, attr).indexOf(value)==0);
			}
		},
		"$=": function(attr, value){
			// E[foo$="bar"]	
			//		an E element whose "foo" attribute value ends exactly
			//		with the string "bar"
			var tval = " "+value;
			return function(elem){
				var ea = " "+_getAttr(elem, attr);
				return (ea.lastIndexOf(value)==(ea.length-value.length));
			}
		},
		"~=": function(attr, value){
			// E[foo~="bar"]	
			//		an E element whose "foo" attribute value is a list of
			//		space-separated values, one of which is exactly equal
			//		to "bar"

			// return "[contains(concat(' ',@"+attr+",' '), ' "+ value +" ')]";
			var tval = " "+value+" ";
			return function(elem){
				var ea = " "+_getAttr(elem, attr)+" ";
				return (ea.indexOf(tval)>=0);
			}
		},
		"|=": function(attr, value){
			// E[hreflang|="en"]
			//		an E element whose "hreflang" attribute has a
			//		hyphen-separated list of values beginning (from the
			//		left) with "en"
			var valueDash = " "+value+"-";
			return function(elem){
				var ea = " "+(elem.getAttribute(attr, 2) || "");
				return (
					(ea == value) ||
					(ea.indexOf(valueDash)==0)
				);
			}
		},
		"=": function(attr, value){
			return function(elem){
				return (_getAttr(elem, attr) == value);
			}
		}
	};

	var pseudos = {
		"first-child": function(name, condition){
			return function(elem){
				if(elem.nodeType != 1){ return false; }
				// check to see if any of the previous siblings are elements
				var fc = elem.previousSibling;
				while(fc && (fc.nodeType != 1)){
					fc = fc.previousSibling;
				}
				return (!fc);
			}
		},
		"last-child": function(name, condition){
			return function(elem){
				if(elem.nodeType != 1){ return false; }
				// check to see if any of the next siblings are elements
				var nc = elem.nextSibling;
				while(nc && (nc.nodeType != 1)){
					nc = nc.nextSibling;
				}
				return (!nc);
			}
		},
		"empty": function(name, condition){
			return function(elem){
				// DomQuery and jQuery get this wrong, oddly enough.
				// The CSS 3 selectors spec is pretty explicit about
				// it, too.
				var cn = elem.childNodes;
				var cnl = elem.childNodes.length;
				// if(!cnl){ return true; }
				for(var x=cnl-1; x >= 0; x--){
					var nt = cn[x].nodeType;
					if((nt == 1)||(nt == 3)){ return false; }
				}
				return true;
			}
		},
		/* non standard!
		"contains": function(name, condition){
			return function(elem){
				// FIXME: I dislike this version of "contains", as
				// whimsical attribute could set it off. An inner-text
				// based version might be more accurate, but since
				// jQuery and DomQuery also potentially get this wrong,
				// I'm leaving it for now.
				return (elem.innerHTML.indexOf(condition) >= 0);
			}
		},
		*/
		"not": function(name, condition){
			var ntf = getFilterFunc(getQueryParts(condition)[0]);
			return function(elem){
				return (!ntf(elem));
			}
		},
		"nth-child": function(name, condition){
			var pi = parseInt;
			if(condition == "odd"){
				return function(elem){
					return (
						((getNodeIndex(elem)) % 2) == 1
					);
				}
			}else if((condition == "2n")||
				(condition == "even")){
				return function(elem){
					return ((getNodeIndex(elem) % 2) == 0);
				}
			}else if(condition.indexOf("0n+") == 0){
				var ncount = pi(condition.substr(3));
				return function(elem){
					return (elem.parentNode[childNodesName][ncount-1] === elem);
				}
			}else if(	(condition.indexOf("n+") > 0) &&
						(condition.length > 3) ){
				var tparts = condition.split("n+", 2);
				var pred = pi(tparts[0]);
				var idx = pi(tparts[1]);
				return function(elem){
					return ((getNodeIndex(elem) % pred) == idx);
				}
			}else if(condition.indexOf("n") == -1){
				var ncount = pi(condition);
				return function(elem){
					return (getNodeIndex(elem) == ncount);
				}
			}
		}
	};

	var defaultGetter = (d.isIE) ? function(cond){
		var clc = cond.toLowerCase();
		return function(elem){
			return elem[cond]||elem[clc];
		}
	} : function(cond){
		return function(elem){
			return (elem && elem.getAttribute && elem.hasAttribute(cond));
		}
	};

	var getSimpleFilterFunc = function(query){

		var fcHit = (_simpleFiltersCache[query.query]||_filtersCache[query.query]);
		if(fcHit){ return fcHit; }

		var ff = null;

		// the only case where we'll need the tag name is if we came from an ID query
		if(query.id){ // do we have an ID component?
			if(query.tag != "*"){
				ff = agree(ff, function(elem){
					return (elem.tagName.toLowerCase() == query.tag);
				});
			}
		}

		// if there's a class in our query, generate a match function for it
		d.forEach(query.classes, function(cname, idx, arr){
			// get the class name
			var isWildcard = cname.charAt(cname.length-1) == "*";
			if(isWildcard){
				cname = cname.substr(0, cname.length-1);
			}
			// I dislike the regex thing, even if memozied in a cache, but it's VERY short
			var re = new RegExp("(?:^|\\s)" + cname + (isWildcard ? ".*" : "") + "(?:\\s|$)");
			ff = agree(ff, function(elem){
				return re.test(elem.className);
			});
			ff.count = idx;
		});

		d.forEach(query.pseudos, function(pseudo){
			if(pseudos[pseudo.name]){
				ff = agree(ff, pseudos[pseudo.name](pseudo.name, pseudo.value));
			}
		});

		handleAttrs(attrs, query, defaultGetter,
			function(tmatcher){ ff = agree(ff, tmatcher); }
		);
		if(!ff){
			ff = function(){ return true; };
		}
		return _simpleFiltersCache[query.query] = ff;
	}

	var _getElementsFuncCache = { };

	var getElementsFunc = function(query, root){
		var fHit = _getElementsFuncCache[query.query];
		if(fHit){ return fHit; }

		// NOTE: this function is in the fast path! not memoized!!!

		// the query doesn't contain any spaces, so there's only so many
		// things it could be

		if(query.id && !query.hasLoops && !query.tag){
			// ID-only query. Easy.
			return _getElementsFuncCache[query.query] = function(root){
				// FIXME: if root != document, check for parenting!
				return [ d.byId(query.id) ];
			}
		}

		var filterFunc = getSimpleFilterFunc(query);

		var retFunc;
		if(query.tag && query.id && !query.hasLoops){
			// we got a filtered ID search (e.g., "h4#thinger")
			retFunc = function(root){
				var te = d.byId(query.id);
				if(filterFunc(te)){
					return [ te ];
				}
			}
		}else{
			var tret;

			if(!query.hasLoops){
				// it's just a plain-ol elements-by-tag-name query from the root
				retFunc = function(root){
					var ret = [];
					var te, x=0, tret = root.getElementsByTagName(query.tag);
					while(te=tret[x++]){
						ret.push(te);
					}
					return ret;
				}
			}else{
				retFunc = function(root){
					var ret = [];
					var te, x=0, tret = root.getElementsByTagName(query.tag);
					while(te=tret[x++]){
						if(filterFunc(te)){
							ret.push(te);
						}
					}
					return ret;
				}
			}
		}
		return _getElementsFuncCache[query.query] = retFunc;
	}

	var _partsCache = {};

	////////////////////////////////////////////////////////////////////////
	// the query runner
	////////////////////////////////////////////////////////////////////////

	// this is the second level of spliting, from full-length queries (e.g.,
	// "div.foo .bar") into simple query expressions (e.g., ["div.foo",
	// ".bar"])
	var _queryFuncCache = {
		"*": d.isIE ? 
			function(root){ 
					return root.all;
			} : 
			function(root){
				 return root.getElementsByTagName("*");
			},
		">": function(root){
			var ret = [];
			var te, x=0, tret = root[childNodesName];
			while(te=tret[x++]){
				if(te.nodeType == 1){ ret.push(te); }
			}
			return ret;
		}
	};

	var getStepQueryFunc = function(query){
		// if it's trivial, get a fast-path dispatcher
		var qparts = getQueryParts(d.trim(query));
		// if(query[query.length-1] == ">"){ query += " *"; }
		if(qparts.length == 1){
			var tt = getElementsFunc(qparts[0]);
			tt.nozip = true;
			return tt;
		}

		// otherwise, break it up and return a runner that iterates over the parts recursively
		var sqf = function(root){
			var localQueryParts = qparts.slice(0); // clone the src arr
			var candidates;
			if(localQueryParts[0].oper == ">"){
				candidates = [ root ];
				// root = document;
			}else{
				candidates = getElementsFunc(localQueryParts.shift())(root);
			}
			return filterDown(candidates, localQueryParts);
		}
		return sqf;
	}

	// a specialized method that implements our primoridal "query optimizer".
	// This allows us to dispatch queries to the fastest subsystem we can get.
	var _getQueryFunc = (
		// NOTE: 
		//		XPath on the Webkit nighlies is slower than it's DOM iteration
		//		for most test cases
		// FIXME: 
		//		we should try to capture some runtime speed data for each query
		//		function to determine on the fly if we should stick w/ the
		//		potentially optimized variant or if we should try something
		//		new.
		(document["evaluate"] && !d.isSafari) ? 
		function(query){
			// has xpath support that's faster than DOM
			var qparts = query.split(" ");
			// can we handle it?
			if(	(document["evaluate"])&&
				(query.indexOf(":") == -1)&&
				(
					(true) // ||
					// (query.indexOf("[") == -1) ||
					// (query.indexOf("=") == -1)
				)
			){
				// @JS_NS@._dojo.debug(query);
				// should we handle it?

				// kind of a lame heuristic, but it works
				if(	
					// a "div div div" style query
					((qparts.length > 2)&&(query.indexOf(">") == -1))||
					// or something else with moderate complexity. kinda janky
					(qparts.length > 3)||
					(query.indexOf("[")>=0)||
					// or if it's a ".thinger" query
					((1 == qparts.length)&&(0 <= query.indexOf(".")))

				){
					// use get and cache a xpath runner for this selector
					return getXPathFunc(query);
				}
			}

			// fallthrough
			return getStepQueryFunc(query);
		} : getStepQueryFunc
	);
	// uncomment to disable XPath for testing and tuning the DOM path
	// _getQueryFunc = getStepQueryFunc;

	// FIXME: we've got problems w/ the NodeList query()/filter() functions if we go XPath for everything

	// uncomment to disable DOM queries for testing and tuning XPath
	// _getQueryFunc = getXPathFunc;

	// this is the primary caching for full-query results. The query dispatcher
	// functions are generated here and then pickled for hash lookup in the
	// future
	var getQueryFunc = function(query){
		// return a cached version if one is available
		if(_queryFuncCache[query]){ return _queryFuncCache[query]; }
		if(0 > query.indexOf(",")){
			// if it's not a compound query (e.g., ".foo, .bar"), cache and return a dispatcher
			return _queryFuncCache[query] = _getQueryFunc(query);
		}else{
			// if it's a complex query, break it up into it's constituent parts
			// and return a dispatcher that will merge the parts when run

			// var parts = query.split(", ");
			var parts = query.split(/\s*,\s*/);
			var tf = function(root){
				var pindex = 0; // avoid array alloc for every invocation
				var ret = [];
				var tp;
				while(tp = parts[pindex++]){
					ret = ret.concat(_getQueryFunc(tp, tp.indexOf(" "))(root));
				}
				return ret;
			}
			// ...cache and return
			return _queryFuncCache[query] = tf;
		}
	}

	// FIXME: 
	//		Dean's new Base2 uses a system whereby queries themselves note if
	//		they'll need duplicate filtering. We need to get on that plan!!

	// attempt to efficiently determine if an item in a list is a dupe,
	// returning a list of "uniques", hopefully in doucment order
	var _zipIdx = 0;
	var _zip = function(arr){
		if(arr && arr.nozip){ return d.NodeList._wrap(arr); }
		var ret = new d.NodeList();
		if(!arr){ return ret; }
		if(arr[0]){
			ret.push(arr[0]);
		}
		if(arr.length < 2){ return ret; }
		_zipIdx++;
		arr[0]["_zipIdx"] = _zipIdx;
		for(var x=1, te; te = arr[x]; x++){
			if(arr[x]["_zipIdx"] != _zipIdx){ 
				ret.push(te);
			}
			te["_zipIdx"] = _zipIdx;
		}
		// FIXME: should we consider stripping these properties?
		return ret;
	}

	// the main exectuor
	d.query = function(query, root){
		//	summary:
		//		returns nodes which match the given CSS3 selector, searching the
		//		entire document by default but optionally taking a node to scope
		//		the search by. Returns an instance of @JS_NS@._dojo.NodeList.
		//	description:
		//		@JS_NS@._dojo.query() is the swiss army knife of DOM node manipulation in
		//		Dojo. Much like Prototype's "$$" (bling-bling) function or JQuery's
		//		"$" function, @JS_NS@._dojo.query provides robust, high-performance
		//		CSS-based node selector support with the option of scoping searches
		//		to a particular sub-tree of a document.
		//
		//		Supported Selectors:
		//		--------------------
		//
		//		@JS_NS@._dojo.query() supports a rich set of CSS3 selectors, including:
		//
		//			* class selectors (e.g., ".foo")
		//			* node type selectors like "span"
		//			* " " descendant selectors
		//			* ">" child element selectors 
		//			* "#foo" style ID selectors
		//			* "*" universal selector
		//			* attribute queries:
		//				* "[foo]" attribute presence selector
		//				* "[foo='bar']" attribute value exact match
		//				* "[foo~='bar']" attribute value list item match
		//				* "[foo^='bar']" attribute start match
		//				* "[foo$='bar']" attribute end match
		//				* "[foo*='bar']" attribute substring match
		//			* ":first-child", ":last-child" positional selectors
		//			* ":nth-child(n)", ":nth-child(2n+1)" style positional calculations
		//			* ":nth-child(even)", ":nth-child(odd)" positional selectors
		//			* ":not(...)" negation pseudo selectors
		//
		//		Any legal combination of those selector types as per the CSS 3 sepc
		//		will work with @JS_NS@._dojo.query(), including compound selectors (","
		//		delimited). Very complex and useful searches can be constructed
		//		with this palette of selectors and when combined with functions for
		//		maniplation presented by @JS_NS@._dojo.NodeList, many types of DOM
		//		manipulation operations become very straightforward.
		//		
		//		Unsupported Selectors:
		//		--------------------
		//
		//		While @JS_NS@._dojo.query handles many CSS3 selectors, some fall outside of
		//		what's resaonable for a programmatic node querying engine to
		//		handle. Currently unsupported selectors include:
		//		
		//			* namespace-differentiated selectors of any form
		//			* "~", the immediately preceeded-by sibling selector
		//			* "+", the preceeded-by sibling selector
		//			* all "::" pseduo-element selectors
		//			* certain pseduo-selectors which don't get a lot of day-to-day use:
		//				* :root, :lang(), :target, :focus
		//			* all visual and state selectors:
		//				* :root, :active, :hover, :visisted, :link, :enabled, :disabled, :checked
		//			* :*-of-type pseudo selectors
		//		
		//		@JS_NS@._dojo.query and XML Documents:
		//		-----------------------------
		//		FIXME
		//		
		//	query: String
		//		The CSS3 expression to match against. For details on the syntax of
		//		CSS3 selectors, see:
		//			http://www.w3.org/TR/css3-selectors/#selectors
		//	root: String|DOMNode?
		//		A node (or string ID of a node) to scope the search from. Optional.
		//	returns:
		//		An instance of @JS_NS@._dojo.NodeList. Many methods are available on
		//		NodeLists for searching, iterating, manipulating, and handling
		//		events on the matched nodes in the returned list.

		// return is always an array
		// NOTE: elementsById is not currently supported
		// NOTE: ignores xpath-ish queries for now
		if(query.constructor == d.NodeList){
			return query;
		}
		if(!d.isString(query)){
			return new d.NodeList(query); // @JS_NS@._dojo.NodeList
		}
		if(d.isString(root)){
			root = d.byId(root);
		}

		// FIXME: should support more methods on the return than the stock array.
		return _zip(getQueryFunc(query)(root||d.doc));
	}

	/*
	// exposing these was a mistake
	d.query.attrs = attrs;
	d.query.pseudos = pseudos;
	*/

	// one-off function for filtering a NodeList based on a simple selector
	d._filterQueryResult = function(nodeList, simpleFilter){
		var tnl = new d.NodeList();
		var ff = (simpleFilter) ? getFilterFunc(getQueryParts(simpleFilter)[0]) : function(){ return true; };
		for(var x=0, te; te = nodeList[x]; x++){
			if(ff(te)){ tnl.push(te); }
		}
		return tnl;
	}
})();

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.Deferred"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.Deferred"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.Deferred");


@JS_NS@._dojo.Deferred = function(/*Function?*/ canceller){
	// summary:
	//		Encapsulates a sequence of callbacks in response to a value that
	//		may not yet be available.  This is modeled after the Deferred class
	//		from Twisted <http://twistedmatrix.com>.
	// description:
	//		JavaScript has no threads, and even if it did, threads are hard.
	//		Deferreds are a way of abstracting non-blocking events, such as the
	//		final response to an XMLHttpRequest. Deferreds create a promise to
	//		return a response a some point in the future and an easy way to
	//		register your interest in receiving that response.
	//
	//		The most important methods for Deffered users are:
	//
	//			* addCallback(handler)
	//			* addErrback(handler)
	//			* callback(result)
	//			* errback(result)
	//
	//		In general, when a function returns a Deferred, users then "fill
	//		in" the second half of the contract by registering callbacks and
	//		error handlers. You may register as many callback and errback
	//		handlers as you like and they will be executed in the order
	//		registered when a result is provided. Usually this result is
	//		provided as the result of an asynchronous operation. The code
	//		"managing" the Deferred (the code that made the promise to provide
	//		an answer later) will use the callback() and errback() methods to
	//		communicate with registered listeners about the result of the
	//		operation. At this time, all registered result handlers are called
	//		*with the most recent result value*.
	//
	//		Deferred callback handlers are treated as a chain, and each item in
	//		the chain is required to return a value that will be fed into
	//		successive handlers. The most minimal callback may be registered
	//		like this:
	//
	//		|	var d = new @JS_NS@._dojo.Deferred();
	//		|	d.addCallback(function(result){ return result; });
	//
	//		Perhaps the most common mistake when first using Deferreds is to
	//		forget to return a value (in most cases, the value you were
	//		passed).
	//
	//		The sequence of callbacks is internally represented as a list of
	//		2-tuples containing the callback/errback pair.  For example, the
	//		following call sequence:
	//		
	//		|	var d = new @JS_NS@._dojo.Deferred();
	//		|	d.addCallback(myCallback);
	//		|	d.addErrback(myErrback);
	//		|	d.addBoth(myBoth);
	//		|	d.addCallbacks(myCallback, myErrback);
	//
	//		is translated into a Deferred with the following internal
	//		representation:
	//
	//		|	[
	//		|		[myCallback, null],
	//		|		[null, myErrback],
	//		|		[myBoth, myBoth],
	//		|		[myCallback, myErrback]
	//		|	]
	//
	//		The Deferred also keeps track of its current status (fired).  Its
	//		status may be one of three things:
	//
	//			* -1: no value yet (initial condition)
	//			* 0: success
	//			* 1: error
	//	
	//		A Deferred will be in the error state if one of the following three
	//		conditions are met:
	//
	//			1. The result given to callback or errback is "instanceof" Error
	//			2. The previous callback or errback raised an exception while
	//			   executing
	//			3. The previous callback or errback returned a value
	//			   "instanceof" Error
	//
	//		Otherwise, the Deferred will be in the success state. The state of
	//		the Deferred determines the next element in the callback sequence
	//		to run.
	//
	//		When a callback or errback occurs with the example deferred chain,
	//		something equivalent to the following will happen (imagine
	//		that exceptions are caught and returned):
	//
	//		|	// d.callback(result) or d.errback(result)
	//		|	if(!(result instanceof Error)){
	//		|		result = myCallback(result);
	//		|	}
	//		|	if(result instanceof Error){
	//		|		result = myErrback(result);
	//		|	}
	//		|	result = myBoth(result);
	//		|	if(result instanceof Error){
	//		|		result = myErrback(result);
	//		|	}else{
	//		|		result = myCallback(result);
	//		|	}
	//
	//		The result is then stored away in case another step is added to the
	//		callback sequence.	Since the Deferred already has a value
	//		available, any new callbacks added will be called immediately.
	//
	//		There are two other "advanced" details about this implementation
	//		that are useful:
	//
	//		Callbacks are allowed to return Deferred instances themselves, so
	//		you can build complicated sequences of events with ease.
	//
	//		The creator of the Deferred may specify a canceller.  The canceller
	//		is a function that will be called if Deferred.cancel is called
	//		before the Deferred fires. You can use this to implement clean
	//		aborting of an XMLHttpRequest, etc. Note that cancel will fire the
	//		deferred with a CancelledError (unless your canceller returns
	//		another kind of error), so the errbacks should be prepared to
	//		handle that error for cancellable Deferreds.
	// example:
	//	|	var deferred = new @JS_NS@._dojo.Deferred();
	//	|	setTimeout(function(){ deferred.callback({success: true}); }, 1000);
	//	|	return deferred;
	// example:
	//		Deferred objects are often used when making code asynchronous. It
	//		may be easiest to write functions in a synchronous manner and then
	//		split code using a deferred to trigger a response to a long-lived
	//		operation. For example, instead of register a callback function to
	//		denote when a rendering operation completes, the function can
	//		simply return a deferred:
	//
	//		|	// callback style:
	//		|	function renderLotsOfData(data, callback){
	//		|		var success = false
	//		|		try{
	//		|			for(var x in data){
	//		|				renderDataitem(data[x]);
	//		|			}
	//		|			success = true;
	//		|		}catch(e){ }
	//		|		if(callback){
	//		|			callback(success);
	//		|		}
	//		|	}
	//
	//		|	// using callback style
	//		|	renderLotsOfData(someDataObj, function(success){
	//		|		// handles success or failure
	//		|		if(!success){
	//		|			promptUserToRecover();
	//		|		}
	//		|	});
	//		|	// NOTE: no way to add another callback here!!
	// example:
	//		Using a Deferred doesn't simplify the sending code any, but it
	//		provides a standard interface for callers and senders alike,
	//		providing both with a simple way to service multiple callbacks for
	//		an operation and freeing both sides from worrying about details
	//		such as "did this get called already?". With Deferreds, new
	//		callbacks can be added at any time.
	//
	//		|	// Deferred style:
	//		|	function renderLotsOfData(data){
	//		|		var d = new @JS_NS@._dojo.Deferred();
	//		|		try{
	//		|			for(var x in data){
	//		|				renderDataitem(data[x]);
	//		|			}
	//		|			d.callback(true);
	//		|		}catch(e){ 
	//		|			d.errback(new Error("rendering failed"));
	//		|		}
	//		|		return d;
	//		|	}
	//
	//		|	// using Deferred style
	//		|	renderLotsOfData(someDataObj).addErrback(function(){
	//		|		promptUserToRecover();
	//		|	});
	//		|	// NOTE: addErrback and addCallback both return the Deferred
	//		|	// again, so we could chain adding callbacks or save the
	//		|	// deferred for later should we need to be notified again.
	// example:
	//		In this example, renderLotsOfData is syncrhonous and so both
	//		versions are pretty artificial. Putting the data display on a
	//		timeout helps show why Deferreds rock:
	//
	//		|	// Deferred style and async func
	//		|	function renderLotsOfData(data){
	//		|		var d = new @JS_NS@._dojo.Deferred();
	//		|		setTimeout(function(){
	//		|			try{
	//		|				for(var x in data){
	//		|					renderDataitem(data[x]);
	//		|				}
	//		|				d.callback(true);
	//		|			}catch(e){ 
	//		|				d.errback(new Error("rendering failed"));
	//		|			}
	//		|		}, 100);
	//		|		return d;
	//		|	}
	//
	//		|	// using Deferred style
	//		|	renderLotsOfData(someDataObj).addErrback(function(){
	//		|		promptUserToRecover();
	//		|	});
	//
	//		Note that the caller doesn't have to change his code at all to
	//		handle the asynchronous case.

	this.chain = [];
	this.id = this._nextId();
	this.fired = -1;
	this.paused = 0;
	this.results = [null, null];
	this.canceller = canceller;
	this.silentlyCancelled = false;
};

@JS_NS@._dojo.extend(@JS_NS@._dojo.Deferred, {
	/*
	makeCalled: function(){
		// summary:
		//		returns a new, empty deferred, which is already in the called
		//		state. Calling callback() or errback() on this deferred will
		//		yeild an error and adding new handlers to it will result in
		//		them being called immediately.
		var deferred = new @JS_NS@._dojo.Deferred();
		deferred.callback();
		return deferred;
	},

	toString: function(){
		var state;
		if(this.fired == -1){
			state = 'unfired';
		}else{
			state = this.fired ? 'success' : 'error';
		}
		return 'Deferred(' + this.id + ', ' + state + ')';
	},
	*/

	_nextId: (function(){
		var n = 1;
		return function(){ return n++; };
	})(),

	cancel: function(){
		// summary:	
		//		Cancels a Deferred that has not yet received a value, or is
		//		waiting on another Deferred as its value.
		// description:
		//		If a canceller is defined, the canceller is called. If the
		//		canceller did not return an error, or there was no canceller,
		//		then the errback chain is started.
		var err;
		if(this.fired == -1){
			if(this.canceller){
				err = this.canceller(this);
			}else{
				this.silentlyCancelled = true;
			}
			if(this.fired == -1){
				if(!(err instanceof Error)){
					var res = err;
					err = new Error("Deferred Cancelled");
					err.dojoType = "cancel";
					err.cancelResult = res;
				}
				this.errback(err);
			}
		}else if(	(this.fired == 0) &&
					(this.results[0] instanceof @JS_NS@._dojo.Deferred)
		){
			this.results[0].cancel();
		}
	},
			

	_resback: function(res){
		// summary:
		//		The private primitive that means either callback or errback
		this.fired = ((res instanceof Error) ? 1 : 0);
		this.results[this.fired] = res;
		this._fire();
	},

	_check: function(){
		if(this.fired != -1){
			if(!this.silentlyCancelled){
				throw new Error("already called!");
			}
			this.silentlyCancelled = false;
			return;
		}
	},

	callback: function(res){
		// summary:	Begin the callback sequence with a non-error value.
		
		/*
		callback or errback should only be called once on a given
		Deferred.
		*/
		this._check();
		this._resback(res);
	},

	errback: function(/*Error*/res){
		// summary: 
		//		Begin the callback sequence with an error result.
		this._check();
		if(!(res instanceof Error)){
			res = new Error(res);
		}
		this._resback(res);
	},

	addBoth: function(/*Function||Object*/cb, /*Optional, String*/cbfn){
		// summary:
		//		Add the same function as both a callback and an errback as the
		//		next element on the callback sequence.	This is useful for code
		//		that you want to guarantee to run, e.g. a finalizer.
		var enclosed = @JS_NS@._dojo.hitch(cb, cbfn);
		if(arguments.length > 2){
			enclosed = @JS_NS@._dojo.partial(enclosed, arguments, 2);
		}
		return this.addCallbacks(enclosed, enclosed);
	},

	addCallback: function(cb, cbfn){
		// summary: 
		//		Add a single callback to the end of the callback sequence.
		var enclosed = @JS_NS@._dojo.hitch(cb, cbfn);
		if(arguments.length > 2){
			enclosed = @JS_NS@._dojo.partial(enclosed, arguments, 2);
		}
		return this.addCallbacks(enclosed, null);
	},

	addErrback: function(cb, cbfn){
		// summary: 
		//		Add a single callback to the end of the callback sequence.
		var enclosed = @JS_NS@._dojo.hitch(cb, cbfn);
		if(arguments.length > 2){
			enclosed = @JS_NS@._dojo.partial(enclosed, arguments, 2);
		}
		return this.addCallbacks(null, enclosed);
	},

	addCallbacks: function(cb, eb){
		// summary: 
		//		Add separate callback and errback to the end of the callback
		//		sequence.
		this.chain.push([cb, eb])
		if(this.fired >= 0){
			this._fire();
		}
		return this;
	},

	_fire: function(){
		// summary: 
		//		Used internally to exhaust the callback sequence when a result
		//		is available.
		var chain = this.chain;
		var fired = this.fired;
		var res = this.results[fired];
		var self = this;
		var cb = null;
		while(
			(chain.length > 0) &&
			(this.paused == 0)
		){
			// Array
			var f = chain.shift()[fired];
			if(!f){ continue; }
			try{
				res = f(res);
				fired = ((res instanceof Error) ? 1 : 0);
				if(res instanceof @JS_NS@._dojo.Deferred){
					cb = function(res){
						self._resback(res);
						// inlined from _pause()
						self.paused--;
						if(
							(self.paused == 0) && 
							(self.fired >= 0)
						){
							self._fire();
						}
					}
					// inlined from _unpause
					this.paused++;
				}
			}catch(err){
				console.debug(err);
				fired = 1;
				res = err;
			}
		}
		this.fired = fired;
		this.results[fired] = res;
		if((cb)&&(this.paused)){
			// this is for "tail recursion" in case the dependent
			// deferred is already fired
			res.addBoth(cb);
		}
	}
});

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.json"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.json"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.json");

@JS_NS@._dojo.fromJson = function(/*String*/ json){
	// summary:
	// 		evaluates the passed string-form of a JSON object
	// json: 
	//		a string literal of a JSON item, for instance:
	//			'{ "foo": [ "bar", 1, { "baz": "thud" } ] }'
	// return:
	//		An object, the result of the evaluation

	// FIXME: should this accept mozilla's optional second arg?
	try {
		return eval("(" + json + ")");
	}catch(e){
		console.debug(e);
		return json;
	}
}

@JS_NS@._dojo._escapeString = function(/*String*/str){
	//summary:
	//		Adds escape sequences for non-visual characters, double quote and
	//		backslash and surrounds with double quotes to form a valid string
	//		literal.
	return ('"' + str.replace(/(["\\])/g, '\\$1') + '"'
		).replace(/[\f]/g, "\\f"
		).replace(/[\b]/g, "\\b"
		).replace(/[\n]/g, "\\n"
		).replace(/[\t]/g, "\\t"
		).replace(/[\r]/g, "\\r"); // string
}

@JS_NS@._dojo.toJsonIndentStr = "\t";
@JS_NS@._dojo.toJson = function(/*Object*/ it, /*Boolean?*/ prettyPrint, /*String?*/ _indentStr){
	// summary:
	//		Create a JSON serialization of an object. 
	//		Note that this doesn't check for infinite recursion, so don't do that!
	//
	// it:
	//		an object to be serialized. Objects may define their own
	//		serialization via a special "__json__" or "json" function
	//		property. If a specialized serializer has been defined, it will
	//		be used as a fallback.
	//
	// prettyPrint:
	//		if true, we indent objects and arrays to make the output prettier.
	//		The variable @JS_NS@._dojo.toJsonIndentStr is used as the indent string 
	//		-- to use something other than the default (tab), 
	//		change that variable before calling @JS_NS@._dojo.toJson().
	//
	// _indentStr:
	//		private variable for recursive calls when pretty printing, do not use.
	//		
	// return:
	//		a String representing the serialized version of the passed object.

	_indentStr = _indentStr || "";
	var nextIndent = (prettyPrint ? _indentStr + @JS_NS@._dojo.toJsonIndentStr : "");
	var newLine = (prettyPrint ? "\n" : "");
	var objtype = typeof(it);
	if(objtype == "undefined"){
		return "undefined";
	}else if((objtype == "number")||(objtype == "boolean")){
		return it + "";
	}else if(it === null){
		return "null";
	}
	if(@JS_NS@._dojo.isString(it)){ 
		return @JS_NS@._dojo._escapeString(it); 
	}
	if(it.nodeType && it.cloneNode){ // isNode
		return ""; // FIXME: would something like outerHTML be better here?
	}
	// recurse
	var recurse = arguments.callee;
	// short-circuit for objects that support "json" serialization
	// if they return "self" then just pass-through...
	var newObj;
	if(typeof it.__json__ == "function"){
		newObj = it.__json__();
		if(it !== newObj){
			return recurse(newObj, prettyPrint, nextIndent);
		}
	}
	if(typeof it.json == "function"){
		newObj = it.json();
		if(it !== newObj){
			return recurse(newObj, prettyPrint, nextIndent);
		}
	}
	// array
	if(@JS_NS@._dojo.isArray(it)){
		var res = [];
		for(var i = 0; i < it.length; i++){
			var val = recurse(it[i], prettyPrint, nextIndent);
			if(typeof(val) != "string"){
				val = "undefined";
			}
			res.push(newLine + nextIndent + val);
		}
		return "[" + res.join(", ") + newLine + _indentStr + "]";
	}
	/*
	// look in the registry
	try {
		window.o = it;
		newObj = @JS_NS@._dojo.json.jsonRegistry.match(it);
		return recurse(newObj, prettyPrint, nextIndent);
	}catch(e){
		// console.debug(e);
	}
	// it's a function with no adapter, skip it
	*/
	if(objtype == "function"){
		return null;
	}
	// generic object code path
	var output = [];
	for(var key in it){
		var keyStr;
		if(typeof(key) == "number"){
			keyStr = '"' + key + '"';
		}else if(typeof(key) == "string"){
			keyStr = @JS_NS@._dojo._escapeString(key);
		}else{
			// skip non-string or number keys
			continue;
		}
		val = recurse(it[key], prettyPrint, nextIndent);
		if(typeof(val) != "string"){
			// skip non-serializable values
			continue;
		}
		// FIXME: use += on Moz!!
		//	 MOW NOTE: using += is a pain because you have to account for the dangling comma...
		output.push(newLine + nextIndent + keyStr + ": " + val);
	}
	return "{" + output.join(", ") + newLine + _indentStr + "}";
}

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.xhr"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo._base.xhr"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo._base.xhr");





(function(){
	var _d = @JS_NS@._dojo;
	function setValue(/*Object*/obj, /*String*/name, /*String*/value){
		//summary:
		//		For the nameed property in object, set the value. If a value
		//		already exists and it is a string, convert the value to be an
		//		array of values.
		var val = obj[name];
		if(_d.isString(val)){
			obj[name] = [val, value];
		}else if(_d.isArray(val)){
			val.push(value);
		}else{
			obj[name] = value;
		}
	}

	@JS_NS@._dojo.formToObject = function(/*DOMNode||String*/ formNode){
		// summary:
		//		@JS_NS@._dojo.formToObject returns the values encoded in an HTML form as
		//		string properties in an object which it then returns. Disabled form
		//		elements, buttons, and other non-value form elements are skipped.
		//		Multi-select elements are returned as an array of string values.
		// description:
		//		This form:
		//
		//			<form id="test_form">
		//				<input type="text" name="blah" value="blah">
		//				<input type="text" name="no_value" value="blah" disabled>
		//				<input type="button" name="no_value2" value="blah">
		//				<select type="select" multiple name="multi" size="5">
		//					<option value="blah">blah</option>
		//					<option value="thud" selected>thud</option>
		//					<option value="thonk" selected>thonk</option>
		//				</select>
		//			</form>
		//
		//		yields this object structure as the result of a call to
		//		formToObject():
		//
		//			{ 
		//				blah: "blah",
		//				multi: [
		//					"thud",
		//					"thonk"
		//				]
		//			};
	
		var ret = {};
		var iq = "input:not([type=file]):not([type=submit]):not([type=image]):not([type=reset]):not([type=button]), select, textarea";
		_d.query(iq, formNode).filter(function(node){
			return (!node.disabled);
		}).forEach(function(item){
			var _in = item.name;
			var type = (item.type||"").toLowerCase();
			if(type == "radio" || type == "checkbox"){
				if(item.checked){ setValue(ret, _in, item.value); }
			}else if(item.multiple){
				ret[_in] = [];
				_d.query("option", item).forEach(function(opt){
					if(opt.selected){
						setValue(ret, _in, opt.value);
					}
				});
			}else{ 
				setValue(ret, _in, item.value);
				if(type == "image"){
					ret[_in+".x"] = ret[_in+".y"] = ret[_in].x = ret[_in].y = 0;
				}
			}
		});
		return ret; // Object
	}

	@JS_NS@._dojo.objectToQuery = function(/*Object*/ map){
		//	summary:
		//		takes a key/value mapping object and returns a string representing
		//		a URL-encoded version of that object.
		//	example:
		//		this object:
		//
		//		|	{ 
		//		|		blah: "blah",
		//		|		multi: [
		//		|			"thud",
		//		|			"thonk"
		//		|		]
		//		|	};
		//
		//	yeilds the following query string:
		//	
		//	|	"blah=blah&multi=thud&multi=thonk"


		// FIXME: need to implement encodeAscii!!
		var ec = encodeURIComponent;
		var ret = "";
		var backstop = {};
		for(var x in map){
			if(map[x] != backstop[x]){
				if(_d.isArray(map[x])){
					for(var y=0; y<map[x].length; y++){
						ret += ec(x) + "=" + ec(map[x][y]) + "&";
					}
				}else{
					ret += ec(x) + "=" + ec(map[x]) + "&";
				}
			}
		}
		if(ret.length && ret.charAt(ret.length-1) == "&"){
			ret = ret.substr(0, ret.length-1);
		}
		return ret; // String
	}

	@JS_NS@._dojo.formToQuery = function(/*DOMNode||String*/ formNode){
		// summary:
		//		return URL-encoded string representing the form passed as either a
		//		node or string ID identifying the form to serialize
		return _d.objectToQuery(_d.formToObject(formNode)); // String
	}

	@JS_NS@._dojo.formToJson = function(/*DOMNode||String*/ formNode, /*Boolean?*/prettyPrint){
		// summary:
		//		return a serialized JSON string from a form node or string
		//		ID identifying the form to serialize
		return _d.toJson(_d.formToObject(formNode), prettyPrint); // String
	}

	@JS_NS@._dojo.queryToObject = function(/*String*/ str){
		// summary:
		//		returns an object representing a de-serialized query section of a
		//		URL. Query keys with multiple values are returned in an array.
		// description:
		//		This string:
		//
		//			"foo=bar&foo=baz&thinger=%20spaces%20=blah&zonk=blarg&"
		//		
		//		returns this object structure:
		//
		//			{
		//				foo: [ "bar", "baz" ],
		//				thinger: " spaces =blah",
		//				zonk: "blarg"
		//			}
		//	
		//		Note that spaces and other urlencoded entities are correctly
		//		handled.

		// FIXME: should we grab the URL string if we're not passed one?
		var ret = {};
		var qp = str.split("&");
		var dc = decodeURIComponent;
		_d.forEach(qp, function(item){
			if(item.length){
				var parts = item.split("=");
				var name = dc(parts.shift());
				var val = dc(parts.join("="));
				if(_d.isString(ret[name])){
					ret[name] = [ret[name]];
				}
				if(_d.isArray(ret[name])){
					ret[name].push(val);
				}else{
					ret[name] = val;
				}
			}
		});
		return ret; // Object
	}

	/*
		from refactor.txt:

		all bind() replacement APIs take the following argument structure:

			{
				url: "blah.html",

				// all below are optional, but must be supported in some form by
				// every IO API
				timeout: 1000, // milliseconds
				handleAs: "text", // replaces the always-wrong "mimetype"
				content: { 
					key: "value"
				},

				// browser-specific, MAY be unsupported
				sync: true, // defaults to false
				form: @JS_NS@._dojo.byId("someForm") 
			}
	*/

	// need to block async callbacks from snatching this thread as the result
	// of an async callback might call another sync XHR, this hangs khtml forever
	// must checked by watchInFlight()

	@JS_NS@._dojo._blockAsync = false;

	@JS_NS@._dojo._contentHandlers = {
		"text": function(xhr){ return xhr.responseText; },
		"json": function(xhr){
			if(!@JS_NS@._base.config._djConfig.usePlainJson){
				console.debug("Consider using mimetype:text/json-comment-filtered"
					+ " to avoid potential security issues with JSON endpoints"
					+ " (use @JS_NS@._base.config._djConfig.usePlainJson=true to turn off this message)");
			}
			return _d.fromJson(xhr.responseText);
		},
		"json-comment-filtered": function(xhr){ 
			// NOTE: we provide the json-comment-filtered option as one solution to
			// the "JavaScript Hijacking" issue noted by Fortify and others. It is
			// not appropriate for all circumstances.

			var value = xhr.responseText;
			var cStartIdx = value.indexOf("\/*");
			var cEndIdx = value.lastIndexOf("*\/");
			if(cStartIdx == -1 || cEndIdx == -1){
				throw new Error("JSON was not comment filtered");
			}
			return _d.fromJson(value.substring(cStartIdx+2, cEndIdx));
		},
		"javascript": function(xhr){ 
			// FIXME: try Moz and IE specific eval variants?
			return _d.eval(xhr.responseText);
		},
		"xml": function(xhr){ 
			if(_d.isIE && !xhr.responseXML){
				_d.forEach(["MSXML2", "Microsoft", "MSXML", "MSXML3"], function(i){
					try{
						var doc = new ActiveXObject(prefixes[i]+".XMLDOM");
						doc.async = false;
						doc.loadXML(xhr.responseText);
						return doc;	//	DOMDocument
					}catch(e){ /* squelch */ };
				});
			}else{
				return xhr.responseXML;
			}
		}
	};

	@JS_NS@._dojo._contentHandlers["json-comment-optional"] = function(xhr){
		var handlers = _d._contentHandlers;
		try{
			return handlers["json-comment-filtered"](xhr);
		}catch(e){
			return handlers["json"](xhr);
		}
	};

	/*=====
	@JS_NS@._dojo.__ioArgs = function(kwArgs){
		//	url: String
		//		URL to server endpoint.
		//	content: Object?
		//		Contains properties with string values. These
		//		properties will be serialized as name1=value2 and
		//		passed in the request.
		//	timeout: Integer?
		//		Milliseconds to wait for the response. If this time
		//		passes, the then error callbacks are called.
		//	form: DOMNode?
		//		DOM node for a form. Used to extract the form values
		//		and send to the server.
		//	preventCache: Boolean?
		//		Default is false. If true, then a
		//		"@JS_NS@._dojo.preventCache" parameter is sent in the request
		//		with a value that changes with each request
		//		(timestamp). Useful only with GET-type requests.
		//	handleAs: String?
		//		Acceptable values depend on the type of IO
		//		transport (see specific IO calls for more information).
		//	load: Function?
		//		function(response, ioArgs){}. response is an Object, ioArgs
		//		is of type @JS_NS@._dojo.__ioCallbackArgs. The load function will be
		//		called on a successful response.
		//	error: Function?
		//		function(response, ioArgs){}. response is an Object, ioArgs
		//		is of type @JS_NS@._dojo.__ioCallbackArgs. The error function will
		//		be called in an error case. 
		//	handle: Function
		//		function(response, ioArgs){}. response is an Object, ioArgs
		//		is of type @JS_NS@._dojo.__ioCallbackArgs. The handle function will
		//		be called in either the successful or error case.  For
		//		the load, error and handle functions, the ioArgs object
		//		will contain the following properties: 
	}
	=====*/

	/*=====
	@JS_NS@._dojo.__ioCallbackArgs = function(kwArgs){
		//	args: Object
		//		the original object argument to the IO call.
		//	xhr: XMLHttpRequest
		//		For XMLHttpRequest calls only, the
		//		XMLHttpRequest object that was used for the
		//		request.
		//	url: String
		//		The final URL used for the call. Many times it
		//		will be different than the original args.url
		//		value.
		//	query: String
		//		For non-GET requests, the
		//		name1=value1&name2=value2 parameters sent up in
		//		the request.
		//	handleAs: String
		//		The final indicator on how the response will be
		//		handled.
		//	id: String
		//		For @JS_NS@._dojo.io.script calls only, the internal
		//		script ID used for the request.
		//	canDelete: Boolean
		//		For @JS_NS@._dojo.io.script calls only, indicates
		//		whether the script tag that represents the
		//		request can be deleted after callbacks have
		//		been called. Used internally to know when
		//		cleanup can happen on JSONP-type requests.
		//	json: Object
		//		For @JS_NS@._dojo.io.script calls only: holds the JSON
		//		response for JSONP-type requests. Used
		//		internally to hold on to the JSON responses.
		//		You should not need to access it directly --
		//		the same object should be passed to the success
		//		callbacks directly.
	}
	=====*/



	@JS_NS@._dojo._ioSetArgs = function(/*@JS_NS@._dojo.__ioArgs*/args,
			/*Function*/canceller,
			/*Function*/okHandler,
			/*Function*/errHandler){
		//	summary: 
		//		sets up the Deferred and ioArgs property on the Deferred so it
		//		can be used in an io call.
		//	args:
		//		The args object passed into the public io call. Recognized properties on
		//		the args object are:
		//	canceller:
		//		The canceller function used for the Deferred object. The function
		//		will receive one argument, the Deferred object that is related to the
		//		canceller.
		//	okHandler:
		//		The first OK callback to be registered with Deferred. It has the opportunity
		//		to transform the OK response. It will receive one argument -- the Deferred
		//		object returned from this function.
		//	errHandler:
		//		The first error callback to be registered with Deferred. It has the opportunity
		//		to do cleanup on an error. It will receive two arguments: error (the 
		//		Error object) and dfd, the Deferred object returned from this function.

		var ioArgs = {args: args, url: args.url};

		//Get values from form if requestd.
		var formObject = null;
		if(args.form){ 
			var form = _d.byId(args.form);
			//IE requires going through getAttributeNode instead of just getAttribute in some form cases, 
			//so use it for all.  See #2844
			var actnNode = form.getAttributeNode("action");
			ioArgs.url = ioArgs.url || (actnNode ? actnNode.value : null); 
			formObject = _d.formToObject(form);
		}

		// set up the query params
		var miArgs = [{}];
	
		if(formObject){
			// potentially over-ride url-provided params w/ form values
			miArgs.push(formObject);
		}
		if(args.content){
			// stuff in content over-rides what's set by form
			miArgs.push(args.content);
		}
		if(args.preventCache){
			miArgs.push({"@JS_NS@._dojo.preventCache": new Date().valueOf()});
		}
		ioArgs.query = _d.objectToQuery(_d.mixin.apply(null, miArgs));
	
		// .. and the real work of getting the deferred in order, etc.
		ioArgs.handleAs = args.handleAs || "text";
		var d = new _d.Deferred(canceller);
		d.addCallbacks(okHandler, function(error){
			return errHandler(error, d);
		});

		//Support specifying load, error and handle callback functions from the args.
		//For those callbacks, the "this" object will be the args object.
		//The callbacks will get the deferred result value as the
		//first argument and the ioArgs object as the second argument.
		var ld = args.load;
		if(ld && _d.isFunction(ld)){
			d.addCallback(function(value){
				return ld.call(args, value, ioArgs);
			});
		}
		var err = args.error;
		if(err && _d.isFunction(err)){
			d.addErrback(function(value){
				return err.call(args, value, ioArgs);
			});
		}
		var handle = args.handle;
		if(handle && _d.isFunction(handle)){
			d.addBoth(function(value){
				return handle.call(args, value, ioArgs);
			});
		}
		
		d.ioArgs = ioArgs;
	
		// FIXME: need to wire up the xhr object's abort method to something
		// analagous in the Deferred
		return d;
	}

	var _deferredCancel = function(/*Deferred*/dfd){
		//summary: canceller function for @JS_NS@._dojo._ioSetArgs call.
		
		dfd.canceled = true;
		var xhr = dfd.ioArgs.xhr;
		var _at = (typeof xhr.abort);
		if((_at == "function")||(_at == "unknown")){
			xhr.abort();
		}
		var err = new Error("xhr cancelled");
		err.dojoType = "cancel";
		return err;
	}
	var _deferredOk = function(/*Deferred*/dfd){
		//summary: okHandler function for @JS_NS@._dojo._ioSetArgs call.

		return _d._contentHandlers[dfd.ioArgs.handleAs](dfd.ioArgs.xhr);
	}
	var _deferError = function(/*Error*/error, /*Deferred*/dfd){
		//summary: errHandler function for @JS_NS@._dojo._ioSetArgs call.
		
		// console.debug("xhr error in:", dfd.ioArgs.xhr);
		console.debug(error);
		return error;
	}

	var _makeXhrDeferred = function(/*@JS_NS@._dojo.__xhrArgs*/args){
		//summary: makes the Deferred object for this xhr request.
		var dfd = _d._ioSetArgs(args, _deferredCancel, _deferredOk, _deferError);
		//Pass the args to _xhrObj, to allow xhr iframe proxy interceptions.
		dfd.ioArgs.xhr = _d._xhrObj(dfd.ioArgs.args);
		return dfd;
	}

	// avoid setting a timer per request. It degrades performance on IE
	// something fierece if we don't use unified loops.
	var _inFlightIntvl = null;
	var _inFlight = [];
	var _watchInFlight = function(){
		//summary: 
		//		internal method that checks each inflight XMLHttpRequest to see
		//		if it has completed or if the timeout situation applies.
		
		var now = (new Date()).getTime();
		// make sure sync calls stay thread safe, if this callback is called
		// during a sync call and this results in another sync call before the
		// first sync call ends the browser hangs
		if(!_d._blockAsync){
			// we need manual loop because we often modify _inFlight (and therefore 'i') while iterating
			// note: the second clause is an assigment on purpose, lint may complain
			for(var i=0, tif; (i<_inFlight.length)&&(tif=_inFlight[i]); i++){
				var dfd = tif.dfd;
				try{
					if(!dfd || dfd.canceled || !tif.validCheck(dfd)){
						_inFlight.splice(i--, 1); 
					}else if(tif.ioCheck(dfd)){
						_inFlight.splice(i--, 1);
						tif.resHandle(dfd);
					}else if(dfd.startTime){
						//did we timeout?
						if(dfd.startTime + (dfd.ioArgs.args.timeout||0) < now){
							_inFlight.splice(i--, 1);
							var err = new Error("timeout exceeded");
							err.dojoType = "timeout";
							dfd.errback(err);
							//Cancel the request so the io module can do appropriate cleanup.
							dfd.cancel();
						}
					}
				}catch(e){
					// FIXME: make sure we errback!
					console.debug(e);
					dfd.errback(new Error("_watchInFlightError!"));
				}
			}
		}

		if(!_inFlight.length){
			clearInterval(_inFlightIntvl);
			_inFlightIntvl = null;
			return;
		}

	}

	@JS_NS@._dojo._ioCancelAll = function(){
		//summary: Cancels all pending IO requests, regardless of IO type
		//(xhr, script, iframe).
		try{
			_d.forEach(_inFlight, function(i){
				i.dfd.cancel();
			});
		}catch(e){/*squelch*/}
	}

	//Automatically call cancel all io calls on unload
	//in IE for trac issue #2357.
	if(_d.isIE){
		_d.addOnUnload(_d._ioCancelAll);
	}

	_d._ioWatch = function(/*Deferred*/dfd,
		/*Function*/validCheck,
		/*Function*/ioCheck,
		/*Function*/resHandle){
		//summary: watches the io request represented by dfd to see if it completes.
		//dfd:
		//		The Deferred object to watch.
		//validCheck:
		//		Function used to check if the IO request is still valid. Gets the dfd
		//		object as its only argument.
		//ioCheck:
		//		Function used to check if basic IO call worked. Gets the dfd
		//		object as its only argument.
		//resHandle:
		//		Function used to process response. Gets the dfd
		//		object as its only argument.
		if(dfd.ioArgs.args.timeout){
			dfd.startTime = (new Date()).getTime();
		}
		_inFlight.push({dfd: dfd, validCheck: validCheck, ioCheck: ioCheck, resHandle: resHandle});
		if(!_inFlightIntvl){
			_inFlightIntvl = setInterval(_watchInFlight, 50);
		}
		_watchInFlight(); // handle sync requests
	}

	var _defaultContentType = "application/x-www-form-urlencoded";

	var _validCheck = function(/*Deferred*/dfd){
		return dfd.ioArgs.xhr.readyState; //boolean
	}
	var _ioCheck = function(/*Deferred*/dfd){
		return 4 == dfd.ioArgs.xhr.readyState; //boolean
	}
	var _resHandle = function(/*Deferred*/dfd){
		if(_d._isDocumentOk(dfd.ioArgs.xhr)){
			dfd.callback(dfd);
		}else{
			dfd.errback(new Error("bad http response code:" + dfd.ioArgs.xhr.status));
		}
	}

	var _doIt = function(/*String*/type, /*Deferred*/dfd){
		// IE 6 is a steaming pile. It won't let you call apply() on the native function (xhr.open).
		// workaround for IE6's apply() "issues"
		var ioArgs = dfd.ioArgs;
		var args = ioArgs.args;
		ioArgs.xhr.open(type, ioArgs.url, args.sync !== true, args.user || undefined, args.password || undefined);
		if(args.headers){
			for(var hdr in args.headers){
				if(hdr.toLowerCase() === "content-type" && !args.contentType){
					args.contentType = args.headers[hdr];
				}else{
					ioArgs.xhr.setRequestHeader(hdr, args.headers[hdr]);
				}
			}
		}
		// FIXME: is this appropriate for all content types?
		ioArgs.xhr.setRequestHeader("Content-Type", (args.contentType||_defaultContentType));
		// FIXME: set other headers here!
		try{
			ioArgs.xhr.send(ioArgs.query);
		}catch(e){
			dfd.cancel();
		}
		_d._ioWatch(dfd, _validCheck, _ioCheck, _resHandle);
		return dfd; //Deferred
	}

	@JS_NS@._dojo._ioAddQueryToUrl = function(/*@JS_NS@._dojo.__ioCallbackArgs*/ioArgs){
		//summary: Adds query params discovered by the io deferred construction to the URL.
		//Only use this for operations which are fundamentally GET-type operations.
		if(ioArgs.query.length){
			ioArgs.url += (ioArgs.url.indexOf("?") == -1 ? "?" : "&") + ioArgs.query;
			ioArgs.query = null;
		}		
	}

	/*=====
	@JS_NS@._dojo.__xhrArgs = function(kwArgs){
		//	summary:
		//		In addition to the properties listed for the @JS_NS@._dojo.__ioArgs type,
		//		the following properties are allowed for @JS_NS@._dojo.xhr* methods.
		//	handleAs: 
		//		String. Acceptable values are:
		//			"text" (default)
		//			"json"
		//			"json-comment-optional"
		//			"json-comment-filtered"
		//			"javascript"
		//			"xml"
		//	sync:
		//		Boolean. false is default. Indicates whether the request should
		//		be a synchronous (blocking) request.
		//	headers:
		//		Object. Additional HTTP headers to send in the request.
	}
	=====*/

	@JS_NS@._dojo.xhrGet = function(/*@JS_NS@._dojo.__xhrArgs*/ args){
		//	summary: 
		//		Sends an HTTP GET request to the server.
		var dfd = _makeXhrDeferred(args);
		_d._ioAddQueryToUrl(dfd.ioArgs);
		return _doIt("GET", dfd); // @JS_NS@._dojo.Deferred
	}

	@JS_NS@._dojo.xhrPost = function(/*@JS_NS@._dojo.__xhrArgs*/ args){
		//summary: 
		//		Sends an HTTP POST request to the server.
		return _doIt("POST", _makeXhrDeferred(args)); // @JS_NS@._dojo.Deferred
	}

	@JS_NS@._dojo.rawXhrPost = function(/*@JS_NS@._dojo.__xhrArgs*/ args){
		//	summary:
		//		Sends an HTTP POST request to the server. In addtion to the properties
		//		listed for the @JS_NS@._dojo.__xhrArgs type, the following property is allowed:
		//	postData:
		//		String. The raw data to send in the body of the POST request.
		var dfd = _makeXhrDeferred(args);
		dfd.ioArgs.query = args.postData;
		return _doIt("POST", dfd); // @JS_NS@._dojo.Deferred
	}

	@JS_NS@._dojo.xhrPut = function(/*@JS_NS@._dojo.__xhrArgs*/ args){
		//	summary:
		//		Sends an HTTP PUT request to the server.
		return _doIt("PUT", _makeXhrDeferred(args)); // @JS_NS@._dojo.Deferred
	}

	@JS_NS@._dojo.rawXhrPut = function(/*@JS_NS@._dojo.__xhrArgs*/ args){
		//	summary:
		//		Sends an HTTP PUT request to the server. In addtion to the properties
		//		listed for the @JS_NS@._dojo.__xhrArgs type, the following property is allowed:
		//	putData:
		//		String. The raw data to send in the body of the PUT request.
		var dfd = _makeXhrDeferred(args);
		var ioArgs = dfd.ioArgs;
		if(args["putData"]){
			ioArgs.query = args.putData;
			args.putData = null;
		}
		return _doIt("PUT", dfd); // @JS_NS@._dojo.Deferred
	}

	@JS_NS@._dojo.xhrDelete = function(/*@JS_NS@._dojo.__xhrArgs*/ args){
		//	summary:
		//		Sends an HTTP DELETE request to the server.
		var dfd = _makeXhrDeferred(args);
		_d._ioAddQueryToUrl(dfd.ioArgs);
		return _doIt("DELETE", dfd); // @JS_NS@._dojo.Deferred
	}

	/*
	@JS_NS@._dojo.wrapForm = function(formNode){
		//summary:
		//		A replacement for FormBind, but not implemented yet.

		// FIXME: need to think harder about what extensions to this we might
		// want. What should we allow folks to do w/ this? What events to
		// set/send?
		throw new Error("@JS_NS@._dojo.wrapForm not yet implemented");
	}
	*/
})();

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo.i18n"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo.i18n"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo.i18n");

@JS_NS@._dojo.i18n.getLocalization = function(/*String*/packageName, /*String*/bundleName, /*String?*/locale){
	//	summary:
	//		Returns an Object containing the localization for a given resource
	//		bundle in a package, matching the specified locale.
	//	description:
	//		Returns a hash containing name/value pairs in its prototypesuch
	//		that values can be easily overridden.  Throws an exception if the
	//		bundle is not found.  Bundle must have already been loaded by
	//		@JS_NS@._dojo.requireLocalization() or by a build optimization step.  NOTE:
	//		try not to call this method as part of an object property
	//		definition (var foo = { bar: @JS_NS@._dojo.i18n.getLocalization() }).  In
	//		some loading situations, the bundle may not be available in time
	//		for the object definition.  Instead, call this method inside a
	//		function that is run after all modules load or the page loads (like
	//		in @JS_NS@._dojo.adOnLoad()), or in a widget lifecycle method.
	//	packageName:
	//		package which is associated with this resource
	//	bundleName:
	//		the base filename of the resource bundle (without the ".js" suffix)
	//	locale:
	//		the variant to load (optional).  By default, the locale defined by
	//		the host environment: @JS_NS@._dojo.locale

	locale = @JS_NS@._dojo.i18n.normalizeLocale(locale);

	// look for nearest locale match
	var elements = locale.split('-');
	var module = [packageName,"nls",bundleName].join('.');
	var bundle = @JS_NS@._dojo._loadedModules[module];
	if(bundle){
		var localization;
		for(var i = elements.length; i > 0; i--){
			var loc = elements.slice(0, i).join('_');
			if(bundle[loc]){
				localization = bundle[loc];
				break;
			}
		}
		if(!localization){
			localization = bundle.ROOT;
		}

		// make a singleton prototype so that the caller won't accidentally change the values globally
		if(localization){
			var clazz = function(){};
			clazz.prototype = localization;
			return new clazz(); // Object
		}
	}

	throw new Error("Bundle not found: " + bundleName + " in " + packageName+" , locale=" + locale);
};

@JS_NS@._dojo.i18n.normalizeLocale = function(/*String?*/locale){
	//	summary:
	//		Returns canonical form of locale, as used by Dojo.
	//
	//  description:
	//		All variants are case-insensitive and are separated by '-' as specified in RFC 3066.
	//		If no locale is specified, the @JS_NS@._dojo.locale is returned.  @JS_NS@._dojo.locale is defined by
	//		the user agent's locale unless overridden by @JS_NS@._base.config._djConfig.

	var result = locale ? locale.toLowerCase() : @JS_NS@._dojo.locale;
	if(result == "root"){
		result = "ROOT";
	}
	return result; // String
};

@JS_NS@._dojo.i18n._requireLocalization = function(/*String*/moduleName, /*String*/bundleName, /*String?*/locale, /*String?*/availableFlatLocales){
	//	summary:
	//		See @JS_NS@._dojo.requireLocalization()
	//	description:
	// 		Called by the bootstrap, but factored out so that it is only
	// 		included in the build when needed.

	var targetLocale = @JS_NS@._dojo.i18n.normalizeLocale(locale);
 	var bundlePackage = [moduleName, "nls", bundleName].join(".");
	// NOTE: 
	//		When loading these resources, the packaging does not match what is
	//		on disk.  This is an implementation detail, as this is just a
	//		private data structure to hold the loaded resources.  e.g.
	//		tests/hello/nls/en-us/salutations.js is loaded as the object
	//		tests.hello.nls.salutations.en_us={...} The structure on disk is
	//		intended to be most convenient for developers and translators, but
	//		in memory it is more logical and efficient to store in a different
	//		order.  Locales cannot use dashes, since the resulting path will
	//		not evaluate as valid JS, so we translate them to underscores.
	
	//Find the best-match locale to load if we have available flat locales.
	var bestLocale = "";
	if(availableFlatLocales){
		var flatLocales = availableFlatLocales.split(",");
		for(var i = 0; i < flatLocales.length; i++){
			//Locale must match from start of string.
			if(targetLocale.indexOf(flatLocales[i]) == 0){
				if(flatLocales[i].length > bestLocale.length){
					bestLocale = flatLocales[i];
				}
			}
		}
		if(!bestLocale){
			bestLocale = "ROOT";
		}		
	}

	//See if the desired locale is already loaded.
	var tempLocale = availableFlatLocales ? bestLocale : targetLocale;
	var bundle = @JS_NS@._dojo._loadedModules[bundlePackage];
	var localizedBundle = null;
	if(bundle){
		if(@JS_NS@._base.config._djConfig.localizationComplete && bundle._built){return;}
		var jsLoc = tempLocale.replace(/-/g, '_');
		var translationPackage = bundlePackage+"."+jsLoc;
		localizedBundle = @JS_NS@._dojo._loadedModules[translationPackage];
	}

	if(!localizedBundle){
		bundle = @JS_NS@._dojo["provide"](bundlePackage);
		var syms = @JS_NS@._dojo._getModuleSymbols(moduleName);
		var modpath = syms.concat("nls").join("/");
		var parent;

		@JS_NS@._dojo.i18n._searchLocalePath(tempLocale, availableFlatLocales, function(loc){
			var jsLoc = loc.replace(/-/g, '_');
			var translationPackage = bundlePackage + "." + jsLoc;
			var loaded = false;
			if(!@JS_NS@._dojo._loadedModules[translationPackage]){
				// Mark loaded whether it's found or not, so that further load attempts will not be made
				@JS_NS@._dojo["provide"](translationPackage);
				var module = [modpath];
				if(loc != "ROOT"){module.push(loc);}
				module.push(bundleName);
				var filespec = module.join("/") + '.js';
				loaded = @JS_NS@._dojo._loadPath(filespec, null, function(hash){
					// Use singleton with prototype to point to parent bundle, then mix-in result from loadPath
					var clazz = function(){};
					clazz.prototype = parent;
					bundle[jsLoc] = new clazz();
					for(var j in hash){ bundle[jsLoc][j] = hash[j]; }
				});
			}else{
				loaded = true;
			}
			if(loaded && bundle[jsLoc]){
				parent = bundle[jsLoc];
			}else{
				bundle[jsLoc] = parent;
			}
			
			if(availableFlatLocales){
				//Stop the locale path searching if we know the availableFlatLocales, since
				//the first call to this function will load the only bundle that is needed.
				return true;
			}
		});
	}

	//Save the best locale bundle as the target locale bundle when we know the
	//the available bundles.
	if(availableFlatLocales && targetLocale != bestLocale){
		bundle[targetLocale.replace(/-/g, '_')] = bundle[bestLocale.replace(/-/g, '_')];
	}
};

(function(){
	// If other locales are used, @JS_NS@._dojo.requireLocalization should load them as
	// well, by default. 
	// 
	// Override @JS_NS@._dojo.requireLocalization to do load the default bundle, then
	// iterate through the extraLocale list and load those translations as
	// well, unless a particular locale was requested.

	var extra = @JS_NS@._base.config._djConfig.extraLocale;
	if(extra){
		if(!extra instanceof Array){
			extra = [extra];
		}

		var req = @JS_NS@._dojo.i18n._requireLocalization;
		@JS_NS@._dojo.i18n._requireLocalization = function(m, b, locale, availableFlatLocales){
			req(m,b,locale, availableFlatLocales);
			if(locale){return;}
			for(var i=0; i<extra.length; i++){
				req(m,b,extra[i], availableFlatLocales);
			}
		};
	}
})();

@JS_NS@._dojo.i18n._searchLocalePath = function(/*String*/locale, /*Boolean*/down, /*Function*/searchFunc){
	//	summary:
	//		A helper method to assist in searching for locale-based resources.
	//		Will iterate through the variants of a particular locale, either up
	//		or down, executing a callback function.  For example, "en-us" and
	//		true will try "en-us" followed by "en" and finally "ROOT".

	locale = @JS_NS@._dojo.i18n.normalizeLocale(locale);

	var elements = locale.split('-');
	var searchlist = [];
	for(var i = elements.length; i > 0; i--){
		searchlist.push(elements.slice(0, i).join('-'));
	}
	searchlist.push(false);
	if(down){searchlist.reverse();}

	for(var j = searchlist.length - 1; j >= 0; j--){
		var loc = searchlist[j] || "ROOT";
		var stop = searchFunc(loc);
		if(stop){ break; }
	}
};

@JS_NS@._dojo.i18n._preloadLocalizations = function(/*String*/bundlePrefix, /*Array*/localesGenerated){
	//	summary:
	//		Load built, flattened resource bundles, if available for all
	//		locales used in the page. Only called by built layer files.

	function preload(locale){
		locale = @JS_NS@._dojo.i18n.normalizeLocale(locale);
		@JS_NS@._dojo.i18n._searchLocalePath(locale, true, function(loc){
			for(var i=0; i<localesGenerated.length;i++){
				if(localesGenerated[i] == loc){
					@JS_NS@._dojo["require"](bundlePrefix+"_"+loc);
					return true; // Boolean
				}
			}
			return false; // Boolean
		});
	}
	preload();
	var extra = @JS_NS@._base.config._djConfig.extraLocale||[];
	for(var i=0; i<extra.length; i++){
		preload(extra[i]);
	}
};

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dijit._base.manager"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dijit._base.manager"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dijit._base.manager");

@JS_NS@._dojo.declare("@JS_NS@._dijit.WidgetSet", null, {
	constructor: function(){
		// summary:
		//	A set of widgets indexed by id
		this._hash={};
	},

	add: function(/*Widget*/ widget){
		if(this._hash[widget.id]){
			throw new Error("Tried to register widget with id==" + widget.id + " but that id is already registered");
		}
		this._hash[widget.id]=widget;
	},

	remove: function(/*String*/ id){
		delete this._hash[id];
	},

	forEach: function(/*Function*/ func){
		for(var id in this._hash){
			func(this._hash[id]);
		}
	},

	filter: function(/*Function*/ filter){
		var res = new @JS_NS@._dijit.WidgetSet();
		this.forEach(function(widget){
			if(filter(widget)){ res.add(widget); }
		});
		return res;		// @JS_NS@._dijit.WidgetSet
	},

	byId: function(/*String*/ id){
		return this._hash[id];
	},

	byClass: function(/*String*/ cls){
		return this.filter(function(widget){ return widget.declaredClass==cls; });	// @JS_NS@._dijit.WidgetSet
	}
	});

// registry: list of all widgets on page
@JS_NS@._dijit.registry = new @JS_NS@._dijit.WidgetSet();

@JS_NS@._dijit._widgetTypeCtr = {};

@JS_NS@._dijit.getUniqueId = function(/*String*/widgetType){
	// summary
	//	Generates a unique id for a given widgetType

	var id;
	do{
		id = widgetType + "_" +
			(@JS_NS@._dijit._widgetTypeCtr[widgetType] !== undefined ?
				++@JS_NS@._dijit._widgetTypeCtr[widgetType] : @JS_NS@._dijit._widgetTypeCtr[widgetType] = 0);
	}while(@JS_NS@._dijit.byId(id));
	return id; // String
};


if(@JS_NS@._dojo.isIE){
	// Only run this for IE because we think it's only necessary in that case,
	// and because it causes problems on FF.  See bug #3531 for details.
	@JS_NS@._dojo.addOnUnload(function(){
		@JS_NS@._dijit.registry.forEach(function(widget){ widget.destroy(); });
	});
}

@JS_NS@._dijit.byId = function(/*String|Widget*/id){
	// summary:
	//		Returns a widget by its id, or if passed a widget, no-op (like @JS_NS@._dojo.byId())
	return (@JS_NS@._dojo.isString(id)) ? @JS_NS@._dijit.registry.byId(id) : id; // Widget
};

@JS_NS@._dijit.byNode = function(/* DOMNode */ node){
	// summary:
	//		Returns the widget as referenced by node
	return @JS_NS@._dijit.registry.byId(node.getAttribute("widgetId")); // Widget
};

@JS_NS@._dijit.getEnclosingWidget = function(/* DOMNode */ node){
	// summary:
	//		Returns the widget whose dom tree contains node or null if
	//		the node is not contained within the dom tree of any widget
	while(node){
		if(node.getAttribute && node.getAttribute("widgetId")){
			return @JS_NS@._dijit.registry.byId(node.getAttribute("widgetId"));
		}
		node = node.parentNode;
	}
	return null;
};

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dijit._base.sniff"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dijit._base.sniff"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dijit._base.sniff");

// ported from @JS_NS@._dojo.html.applyBrowserClass (style.js)

//	summary:
//		Applies pre-set class names based on browser & version to the
//		top-level HTML node.  Simply doing a require on this module will
//		establish this CSS.  Modified version of Morris' CSS hack.
(function(){
	var d = @JS_NS@._dojo;
	var ie = d.isIE;
	var opera = d.isOpera;
	var maj = Math.floor;
	var classes = {
		dj_ie: ie,
//		dj_ie55: ie == 5.5,
		dj_ie6: maj(ie) == 6,
		dj_ie7: maj(ie) == 7,
		dj_iequirks: ie && d.isQuirks,
// NOTE: Opera not supported by dijit
		dj_opera: opera,
		dj_opera8: maj(opera) == 8,
		dj_opera9: maj(opera) == 9,
		dj_khtml: d.isKhtml,
		dj_safari: d.isSafari,
		dj_gecko: d.isMozilla
	}; // no dojo unsupported browsers

	for(var p in classes){
		if(classes[p]){
			var html = @JS_NS@._dojo.doc.documentElement; //TODO browser-specific DOM magic needed?
			if(html.className){
				html.className += " " + p;
			}else{
				html.className = p;
			}
		}
	}
})();

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dijit._base"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dijit._base"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dijit._base");

//@JS_NS@._dojo.require("@JS_NS@._dijit._base.focus"); // Woodstock: Unused.

//@JS_NS@._dojo.require("@JS_NS@._dijit._base.place"); // Woodstock: Unused.
//@JS_NS@._dojo.require("@JS_NS@._dijit._base.popup"); // Woodstock: Unused.
//@JS_NS@._dojo.require("@JS_NS@._dijit._base.scroll"); // Woodstock: Unused.

//@JS_NS@._dojo.require("@JS_NS@._dijit._base.bidi"); // Woodstock: Unused.
//@JS_NS@._dojo.require("@JS_NS@._dijit._base.typematic"); // Woodstock: Unused.
//@JS_NS@._dojo.require("@JS_NS@._dijit._base.wai");
//@JS_NS@._dojo.require("@JS_NS@._dijit._base.window"); // Woodstock: Unused.

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dijit._Widget"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dijit._Widget"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dijit._Widget");



@JS_NS@._dojo.declare("@JS_NS@._dijit._Widget", null, {
	// summary:
	//		The foundation of dijit widgets. 	
	//
	// id: String
	//		a unique, opaque ID string that can be assigned by users or by the
	//		system. If the developer passes an ID which is known not to be
	//		unique, the specified ID is ignored and the system-generated ID is
	//		used instead.
	id: null, // Woodstock: Set null for issue #975

	// lang: String
	//	Language to display this widget in (like en-us).
	//	Defaults to brower's specified preferred language (typically the language of the OS)
	lang: null, // Woodstock: Set null for issue #975

	// dir: String
	//  Bi-directional support, as defined by the HTML DIR attribute. Either left-to-right "ltr" or right-to-left "rtl".
	dir: null, // Woodstock: Set null for issue #975

	// class: String
	// HTML class attribute
	"class": null, // Woodstock: Set null for issue #975

	// style: String
	// HTML style attribute
	style: null, // Woodstock: Set null for issue #975

	// title: String
	// HTML title attribute
	title: null, // Woodstock: Set null for issue #975

	// srcNodeRef: DomNode
	//		pointer to original dom node
	srcNodeRef: null,

	// domNode: DomNode
	//		this is our visible representation of the widget! Other DOM
	//		Nodes may by assigned to other properties, usually through the
	//		template system's dojoAttachPonit syntax, but the domNode
	//		property is the canonical "top level" node in widget UI.
	domNode: null,

	// attributeMap: Object
	//		A map of attributes and attachpoints -- typically standard HTML attributes -- to set
	//		on the widget's dom, at the "domNode" attach point, by default.
	//		Other node references can be specified as properties of 'this'
	attributeMap: {id:"", dir:"", lang:"", "class":"", style:"", title:""},  // TODO: add on* handlers?

	//////////// INITIALIZATION METHODS ///////////////////////////////////////

	postscript: function(params, srcNodeRef){
		this.create(params, srcNodeRef);
	},

	create: function(params, srcNodeRef){
		// summary:
		//		To understand the process by which widgets are instantiated, it
		//		is critical to understand what other methods create calls and
		//		which of them you'll want to override. Of course, adventurous
		//		developers could override create entirely, but this should
		//		only be done as a last resort.
		//
		//		Below is a list of the methods that are called, in the order
		//		they are fired, along with notes about what they do and if/when
		//		you should over-ride them in your widget:
		//			
		//			postMixInProperties:
		//				a stub function that you can over-ride to modify
		//				variables that may have been naively assigned by
		//				mixInProperties
		//			# widget is added to manager object here
		//			buildRendering
		//				Subclasses use this method to handle all UI initialization
		//				Sets this.domNode.  Templated widgets do this automatically
		//				and otherwise it just uses the source dom node.
		//			postCreate
		//				a stub function that you can over-ride to modify take
		//				actions once the widget has been placed in the UI

		// store pointer to original dom tree
		this.srcNodeRef = @JS_NS@._dojo.byId(srcNodeRef);

		// For garbage collection.  An array of handles returned by Widget.connect()
		// Each handle returned from Widget.connect() is an array of handles from @JS_NS@._dojo.connect()
		this._connects=[];

		// _attaches: String[]
		// 		names of all our dojoAttachPoint variables
		this._attaches=[];

		//mixin our passed parameters
		if(this.srcNodeRef && (typeof this.srcNodeRef.id == "string")){ this.id = this.srcNodeRef.id; }
		if(params){
			@JS_NS@._dojo.mixin(this,params);
		}
		this.postMixInProperties();

		// generate an id for the widget if one wasn't specified
		// (be sure to do this before buildRendering() because that function might
		// expect the id to be there.
		if(!this.id){
			this.id=@JS_NS@._dijit.getUniqueId(this.declaredClass.replace(/\./g,"_"));
		}
		@JS_NS@._dijit.registry.add(this);

		this.buildRendering();

		// Copy attributes listed in attributeMap into the [newly created] DOM for the widget.
		// The placement of these attributes is according to the property mapping in attributeMap.
		// Note special handling for 'style' and 'class' attributes which are lists and can
		// have elements from both old and new structures, and some attributes like "type"
		// cannot be processed this way as they are not mutable.
		if(this.domNode){
			for(var attr in this.attributeMap){
				var mapNode = this[this.attributeMap[attr] || "domNode"];
				var value = this[attr];
				if(typeof value != "object" && (value !== "" || (params && params[attr]))){
					switch(attr){
					case "class":
						@JS_NS@._dojo.addClass(mapNode, value);
						break;
					case "style":
						if(mapNode.style.cssText){
							mapNode.style.cssText += "; " + value;// FIXME: Opera
						}else{
							mapNode.style.cssText = value;
						}
						break;
					default:
						mapNode.setAttribute(attr, value);
					}
				}
			}
		}

		if(this.domNode){
			this.domNode.setAttribute("widgetId", this.id);
		}
		this.postCreate();

		// If srcNodeRef has been processed and removed from the DOM (e.g. TemplatedWidget) then delete it to allow GC.
		if(this.srcNodeRef && !this.srcNodeRef.parentNode){
			delete this.srcNodeRef;
		}	
	},

	postMixInProperties: function(){
		// summary
		//	Called after the parameters to the widget have been read-in,
		//	but before the widget template is instantiated.
		//	Especially useful to set properties that are referenced in the widget template.
	},

	buildRendering: function(){
		// summary:
		//		Construct the UI for this widget, setting this.domNode.
		//		Most widgets will mixin TemplatedWidget, which overrides this method.
		this.domNode = this.srcNodeRef || @JS_NS@._dojo.doc.createElement('div');
	},

	postCreate: function(){
		// summary:
		//		Called after a widget's dom has been setup
	},

	startup: function(){
		// summary:
		//		Called after a widget's children, and other widgets on the page, have been created.
		//		Provides an opportunity to manipulate any children before they are displayed
		//		This is useful for composite widgets that need to control or layout sub-widgets
		//		Many layout widgets can use this as a wiring phase
	},

	//////////// DESTROY FUNCTIONS ////////////////////////////////

	destroyRecursive: function(/*Boolean*/ finalize){
		// summary:
		// 		Destroy this widget and it's descendants. This is the generic
		// 		"destructor" function that all widget users should call to
		// 		cleanly discard with a widget. Once a widget is destroyed, it's
		// 		removed from the manager object.
		// finalize: Boolean
		//		is this function being called part of global environment
		//		tear-down?

		this.destroyDescendants();
		this.destroy();
	},

	destroy: function(/*Boolean*/ finalize){
		// summary:
		// 		Destroy this widget, but not its descendants
		// finalize: Boolean
		//		is this function being called part of global environment
		//		tear-down?
		this.uninitialize();
		@JS_NS@._dojo.forEach(this._connects, function(array){
			@JS_NS@._dojo.forEach(array, @JS_NS@._dojo.disconnect);
		});
		this.destroyRendering(finalize);
		@JS_NS@._dijit.registry.remove(this.id);
	},

	destroyRendering: function(/*Boolean*/ finalize){
		// summary:
		//		Destroys the DOM nodes associated with this widget
		// finalize: Boolean
		//		is this function being called part of global environment
		//		tear-down?

		if(this.bgIframe){
			this.bgIframe.destroy();
			delete this.bgIframe;
		}

		if(this.domNode){
			@JS_NS@._dojo._destroyElement(this.domNode);
			delete this.domNode;
		}

		if(this.srcNodeRef){
			@JS_NS@._dojo._destroyElement(this.srcNodeRef);
			delete this.srcNodeRef;
		}
	},

	destroyDescendants: function(){
		// summary:
		//		Recursively destroy the children of this widget and their
		//		descendants.

		// TODO: should I destroy in the reverse order, to go bottom up?
		@JS_NS@._dojo.forEach(this.getDescendants(), function(widget){ widget.destroy(); });
	},

	uninitialize: function(){
		// summary:
		//		stub function. Over-ride to implement custom widget tear-down
		//		behavior.
		return false;
	},

	////////////////// MISCELLANEOUS METHODS ///////////////////

	toString: function(){
		// summary:
		//		returns a string that represents the widget. When a widget is
		//		cast to a string, this method will be used to generate the
		//		output. Currently, it does not implement any sort of reversable
		//		serialization.
		return '[Widget ' + this.declaredClass + ', ' + (this.id || 'NO ID') + ']'; // String
	},

	getDescendants: function(){
		// summary:
		//	return all the descendant widgets
		var list = @JS_NS@._dojo.query('[widgetId]', this.domNode);
		return list.map(@JS_NS@._dijit.byNode);		// Array
	},

	nodesWithKeyClick : ["input", "button"],

	connect: function(
			/*Object|null*/ obj,
			/*String*/ event,
			/*String|Function*/ method){

		// summary:
		//		Connects specified obj/event to specified method of this object
		//		and registers for disconnect() on widget destroy.
		//		Special event: "ondijitclick" triggers on a click or enter-down or space-up
		//		Similar to @JS_NS@._dojo.connect() but takes three arguments rather than four.
		var handles =[];
		if(event == "ondijitclick"){
			var w = this;
			// add key based click activation for unsupported nodes.
			if(!this.nodesWithKeyClick[obj.nodeName]){
				handles.push(@JS_NS@._dojo.connect(obj, "onkeydown", this,
					function(e){
						if(e.keyCode == @JS_NS@._dojo.keys.ENTER){
							return (@JS_NS@._dojo.isString(method))?
								w[method](e) : method.call(w, e);
						}else if(e.keyCode == @JS_NS@._dojo.keys.SPACE){
							// stop space down as it causes IE to scroll
							// the browser window
							@JS_NS@._dojo.stopEvent(e);
						}
			 		}));
				handles.push(@JS_NS@._dojo.connect(obj, "onkeyup", this,
					function(e){
						if(e.keyCode == @JS_NS@._dojo.keys.SPACE){
							return @JS_NS@._dojo.isString(method) ?
								w[method](e) : method.call(w, e);
						}
			 		}));
			}
			event = "onclick";
		}
		handles.push(@JS_NS@._dojo.connect(obj, event, this, method));

		// return handles for FormElement and ComboBox
		this._connects.push(handles);
		return handles;
	},

	disconnect: function(/*Object*/ handles){
		// summary:
		//		Disconnects handle created by this.connect.
		//		Also removes handle from this widget's list of connects
		for(var i=0; i<this._connects.length; i++){
			if(this._connects[i]==handles){
				@JS_NS@._dojo.forEach(handles, @JS_NS@._dojo.disconnect);
				this._connects.splice(i, 1);
				return;
			}
		}
	},

	isLeftToRight: function(){
		// summary:
		//		Checks the DOM to for the text direction for bi-directional support
		// description:
		//		This method cannot be used during widget construction because the widget
		//		must first be connected to the DOM tree.  Parent nodes are searched for the
		//		'dir' attribute until one is found, otherwise left to right mode is assumed.
		//		See HTML spec, DIR attribute for more information.

		if(typeof this._ltr == "undefined"){
			this._ltr = @JS_NS@._dojo.getComputedStyle(this.domNode).direction != "rtl";
		}
		return this._ltr; //Boolean
	},

	isFocusable: function(){
		// summary:
		//		Return true if this widget can currently be focused
		//		and false if not
		return this.focus && (@JS_NS@._dojo.style(this.domNode, "display") != "none");
	}
});

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dojo.string"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dojo.string"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dojo.string");

@JS_NS@._dojo.string.pad = function(/*String*/text, /*int*/size, /*String?*/ch, /*boolean?*/end){
	// summary:
	//		Pad a string to guarantee that it is at least 'size' length by
	//		filling with the character 'c' at either the start or end of the
	//		string. Pads at the start, by default.
	// text: the string to pad
	// size: length to provide padding
	// ch: character to pad, defaults to '0'
	// end: adds padding at the end if true, otherwise pads at start

	var out = String(text);
	if(!ch){
		ch = '0';
	}
	while(out.length < size){
		if(end){
			out += ch;
		}else{
			out = ch + out;
		}
	}
	return out;	// String
};

@JS_NS@._dojo.string.substitute = function(	/*String*/template, 
									/*Object or Array*/map, 
									/*Function?*/transform, 
									/*Object?*/thisObject){
	// summary:
	//		Performs parameterized substitutions on a string. Throws an
	//		exception if any parameter is unmatched.
	// description:
	//		For example,
	//		|	@JS_NS@._dojo.string.substitute("File '${0}' is not found in directory '${1}'.",["foo.html","/temp"]);
	//		|	@JS_NS@._dojo.string.substitute("File '${name}' is not found in directory '${info.dir}'.",{name: "foo.html", info: {dir: "/temp"}});
	//		both return
	//			"File 'foo.html' is not found in directory '/temp'."
	// template: 
	//		a string with expressions in the form ${key} to be replaced or
	//		${key:format} which specifies a format function.  NOTE syntax has
	//		changed from %{key}
	// map: where to look for substitutions
	// transform: 
	//		a function to process all parameters before substitution takes
	//		place, e.g. @JS_NS@._dojo.string.encodeXML
	// thisObject: 
	//		where to look for optional format function; default to the global
	//		namespace

	return template.replace(/\$\{([^\s\:\}]+)(?:\:([^\s\:\}]+))?\}/g, function(match, key, format){
		var value = @JS_NS@._dojo.getObject(key,false,map);
		if(format){ value = @JS_NS@._dojo.getObject(format,false,thisObject)(value);}
		if(transform){ value = transform(value, key); }
		return value.toString();
	}); // string
};

@JS_NS@._dojo.string.trim = function(/*String*/ str){
	// summary: trims whitespaces from both sides of the string
	// description:
	//	This version of trim() was taken from Steven Levithan's blog: 
	//	http://blog.stevenlevithan.com/archives/faster-trim-javascript.
	//	The short yet good-performing version of this function is 
	//	@JS_NS@._dojo.trim(), which is part of the base.
	str = str.replace(/^\s+/, '');
	for(var i = str.length - 1; i > 0; i--){
		if(/\S/.test(str.charAt(i))){
			str = str.substring(0, i + 1);
			break;
		}
	}
	return str;	// String
};

}

if(!@JS_NS@._dojo._hasResource["@JS_NS@._dijit._Templated"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
@JS_NS@._dojo._hasResource["@JS_NS@._dijit._Templated"] = true;
@JS_NS@._dojo.provide("@JS_NS@._dijit._Templated");




//@JS_NS@._dojo.require("@JS_NS@._dojo.parser"); // Woodstock: Unused.

@JS_NS@._dojo.declare("@JS_NS@._dijit._Templated",
	null,
	{
		// summary:
		//		mixin for widgets that are instantiated from a template

		// templateNode: DomNode
		//		a node that represents the widget template. Pre-empts both templateString and templatePath.
		templateNode: null,

		// templateString String:
		//		a string that represents the widget template. Pre-empts the
		//		templatePath. In builds that have their strings "interned", the
		//		templatePath is converted to an inline templateString, thereby
		//		preventing a synchronous network call.
		templateString: null,

		// templatePath: String
		//	Path to template (HTML file) for this widget
		templatePath: null,

		// widgetsInTemplate Boolean:
		//		should we parse the template to find widgets that might be
		//		declared in markup inside it? false by default.
		widgetsInTemplate: false,

		// containerNode DomNode:
		//		holds child elements. "containerNode" is generally set via a
		//		dojoAttachPoint assignment and it designates where children of
		//		the src dom node will be placed
		containerNode: null,

		// skipNodeCache Boolean:
		//		if using a cached widget template node poses issues for a
		//		particular widget class, it can set this property to ensure
		//		that its template is always re-built from a string
		_skipNodeCache: false,

		// method over-ride
		buildRendering: function(){
			// summary:
			//		Construct the UI for this widget from a template, setting this.domNode.

			// Lookup cached version of template, and download to cache if it
			// isn't there already.  Returns either a DomNode or a string, depending on
			// whether or not the template contains ${foo} replacement parameters.
			var cached = @JS_NS@._dijit._Templated.getCachedTemplate(this.templatePath, this.templateString, this._skipNodeCache);

			var node;
			if(@JS_NS@._dojo.isString(cached)){
				var className = this.declaredClass, _this = this;
				// Cache contains a string because we need to do property replacement
				// do the property replacement
				var tstr = @JS_NS@._dojo.string.substitute(cached, this, function(value, key){
					if(key.charAt(0) == '!'){ value = _this[key.substr(1)]; }
					if(typeof value == "undefined"){ throw new Error(className+" template:"+key); } // a debugging aide
					if(!value){ return ""; }

					// Substitution keys beginning with ! will skip the transform step,
					// in case a user wishes to insert unescaped markup, e.g. ${!foo}
					return key.charAt(0) == "!" ? value :
						// Safer substitution, see heading "Attribute values" in
						// http://www.w3.org/TR/REC-html40/appendix/notes.html#h-B.3.2
						value.toString().replace(/"/g,"&quot;"); //TODO: add &amp? use encodeXML method?
				}, this);

				node = @JS_NS@._dijit._Templated._createNodesFromText(tstr)[0];
			}else{
				// if it's a node, all we have to do is clone it
				node = cached.cloneNode(true);
			}

			// recurse through the node, looking for, and attaching to, our
			// attachment points which should be defined on the template node.
			this._attachTemplateNodes(node);

			var source = this.srcNodeRef;
			if(source && source.parentNode){
				source.parentNode.replaceChild(node, source);
			}

			this.domNode = node;
			if(this.widgetsInTemplate){
				var childWidgets = @JS_NS@._dojo.parser.parse(node);
				this._attachTemplateNodes(childWidgets, function(n,p){
					return n[p];
				});
			}

			this._fillContent(source);
		},

		_fillContent: function(/*DomNode*/ source){
			// summary:
			//		relocate source contents to templated container node
			//		this.containerNode must be able to receive children, or exceptions will be thrown
			var dest = this.containerNode;
			if(source && dest){
				while(source.hasChildNodes()){
					dest.appendChild(source.firstChild);
				}
			}
		},

		_attachTemplateNodes: function(rootNode, getAttrFunc){
			// summary:
			//		map widget properties and functions to the handlers specified in
			//		the dom node and it's descendants. This function iterates over all
			//		nodes and looks for these properties:
			//			* dojoAttachPoint
			//			* dojoAttachEvent	
			//			* waiRole
			//			* waiState
			// rootNode: DomNode|Array[Widgets]
			//		the node to search for properties. All children will be searched.
			// getAttrFunc: function?
			//		a function which will be used to obtain property for a given
			//		DomNode/Widget

			getAttrFunc = getAttrFunc || function(n,p){ return n.getAttribute(p); };

			var nodes = @JS_NS@._dojo.isArray(rootNode) ? rootNode : (rootNode.all || rootNode.getElementsByTagName("*"));
			var x=@JS_NS@._dojo.isArray(rootNode)?0:-1;
			for(; x<nodes.length; x++){
				var baseNode = (x == -1) ? rootNode : nodes[x];
				if(this.widgetsInTemplate && getAttrFunc(baseNode,'dojoType')){
					continue;
				}
				// Process dojoAttachPoint
				var attachPoint = getAttrFunc(baseNode, "dojoAttachPoint");
				if(attachPoint){
					var point, points = attachPoint.split(/\s*,\s*/);
					while(point=points.shift()){
						if(@JS_NS@._dojo.isArray(this[point])){
							this[point].push(baseNode);
						}else{
							this[point]=baseNode;
						}
					}
				}

				// Process dojoAttachEvent
				var attachEvent = getAttrFunc(baseNode, "dojoAttachEvent");
				if(attachEvent){
					// NOTE: we want to support attributes that have the form
					// "domEvent: nativeEvent; ..."
					var event, events = attachEvent.split(/\s*,\s*/);
					var trim = @JS_NS@._dojo.trim;
					while(event=events.shift()){
						if(event){
							var thisFunc = null;
							if(event.indexOf(":") != -1){
								// oh, if only JS had tuple assignment
								var funcNameArr = event.split(":");
								event = trim(funcNameArr[0]);
								thisFunc = trim(funcNameArr[1]);
							}else{
								event = trim(event);
							}
							if(!thisFunc){
								thisFunc = event;
							}
							this.connect(baseNode, event, thisFunc);
						}
					}
				}

				// waiRole, waiState
				var role = getAttrFunc(baseNode, "waiRole");
				if(role){
					@JS_NS@._dijit.setWaiRole(baseNode, role);
				}
				var values = getAttrFunc(baseNode, "waiState");
				if(values){
					@JS_NS@._dojo.forEach(values.split(/\s*,\s*/), function(stateValue){
						if(stateValue.indexOf('-') != -1){
							var pair = stateValue.split('-');
							@JS_NS@._dijit.setWaiState(baseNode, pair[0], pair[1]);
						}
					});
				}

			}
		}
	}
);

// key is either templatePath or templateString; object is either string or DOM tree
@JS_NS@._dijit._Templated._templateCache = {};

@JS_NS@._dijit._Templated.getCachedTemplate = function(templatePath, templateString, alwaysUseString){
	// summary:
	//		static method to get a template based on the templatePath or
	//		templateString key
	// templatePath: String
	//		the URL to get the template from. @JS_NS@._dojo.uri.Uri is often passed as well.
	// templateString: String?
	//		a string to use in lieu of fetching the template from a URL
	// Returns:
	//	Either string (if there are ${} variables that need to be replaced) or just
	//	a DOM tree (if the node can be cloned directly)

	// is it already cached?
	var tmplts = @JS_NS@._dijit._Templated._templateCache;
	var key = templateString || templatePath;
	var cached = tmplts[key];
	if(cached){
		return cached;
	}

	// If necessary, load template string from template path
	if(!templateString){
		templateString = @JS_NS@._dijit._Templated._sanitizeTemplateString(@JS_NS@._dojo._getText(templatePath));
	}

	templateString = @JS_NS@._dojo.string.trim(templateString);

	if(templateString.match(/\$\{([^\}]+)\}/g) || alwaysUseString){
		// there are variables in the template so all we can do is cache the string
		return (tmplts[key] = templateString); //String
	}else{
		// there are no variables in the template so we can cache the DOM tree
		return (tmplts[key] = @JS_NS@._dijit._Templated._createNodesFromText(templateString)[0]); //Node
	}
};

@JS_NS@._dijit._Templated._sanitizeTemplateString = function(/*String*/tString){
	// summary: 
	//		Strips <?xml ...?> declarations so that external SVG and XML
	// 		documents can be added to a document without worry. Also, if the string
	//		is an HTML document, only the part inside the body tag is returned.
	if(tString){
		tString = tString.replace(/^\s*<\?xml(\s)+version=[\'\"](\d)*.(\d)*[\'\"](\s)*\?>/im, "");
		var matches = tString.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
		if(matches){
			tString = matches[1];
		}
	}else{
		tString = "";
	}
	return tString; //String
};


if(@JS_NS@._dojo.isIE){
	@JS_NS@._dojo.addOnUnload(function(){
		var cache = @JS_NS@._dijit._Templated._templateCache;
		for(var key in cache){
			var value = cache[key];
			if(!isNaN(value.nodeType)){ // isNode equivalent
				@JS_NS@._dojo._destroyElement(value);
			}
			delete cache[key];
		}
	});
}

(function(){
	var tagMap = {
		cell: {re: /^<t[dh][\s\r\n>]/i, pre: "<table><tbody><tr>", post: "</tr></tbody></table>"},
		row: {re: /^<tr[\s\r\n>]/i, pre: "<table><tbody>", post: "</tbody></table>"},
		section: {re: /^<(thead|tbody|tfoot)[\s\r\n>]/i, pre: "<table>", post: "</table>"}
	};

	// dummy container node used temporarily to hold nodes being created
	var tn;

	@JS_NS@._dijit._Templated._createNodesFromText = function(/*String*/text){
		// summary:
		//	Attempts to create a set of nodes based on the structure of the passed text.

		if(!tn){
			tn = @JS_NS@._dojo.doc.createElement("div");
			tn.style.display="none";

                        // Woodstock: IE throws security exception here.
			//@JS_NS@._dojo.body().appendChild(tn);
		}
		var tableType = "none";
		var rtext = text.replace(/^\s+/, "");
		for(var type in tagMap){
			var map = tagMap[type];
			if(map.re.test(rtext)){
				tableType = type;
				text = map.pre + text + map.post;
				break;
			}
		}

		tn.innerHTML = text;
		if(tn.normalize){
			tn.normalize();
		}

                // Woodstock: If "tn" is appended before text is added, IE
                // throws security exception.
                @JS_NS@._dojo.body().appendChild(tn);

		var tag = { cell: "tr", row: "tbody", section: "table" }[tableType];
		var _parent = (typeof tag != "undefined") ?
						tn.getElementsByTagName(tag)[0] :
						tn;

		var nodes = [];
		while(_parent.firstChild){
			nodes.push(_parent.removeChild(_parent.firstChild));
		}
		tn.innerHTML="";

                // Woodstock: IE throws security exception if "tn" isn't removed.
                // This allows widgets to be created before the window onLoad event.
                @JS_NS@._dojo.body().removeChild(tn);

		return nodes;	//	Array
	}
})();

// These arguments can be specified for widgets which are used in templates.
// Since any widget can be specified as sub widgets in template, mix it
// into the base widget class.  (This is a hack, but it's effective.)
@JS_NS@._dojo.extend(@JS_NS@._dijit._Widget,{
	dojoAttachEvent: "",
	dojoAttachPoint: "",
	waiRole: "",
	waiState:""
})

}

