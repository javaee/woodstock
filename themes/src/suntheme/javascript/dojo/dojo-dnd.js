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

if(!webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.common"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.common"] = true;
webui.@THEME@.dojo.provide("webui.@THEME@.dojo.dnd.common");

webui.@THEME@.dojo.dnd._copyKey = navigator.appVersion.indexOf("Macintosh") < 0 ? "ctrlKey" : "metaKey";

webui.@THEME@.dojo.dnd.getCopyKeyState = function(e) {
	// summary: abstracts away the difference between selection on Mac and PC,
	//	and returns the state of the "copy" key to be pressed.
	// e: Event: mouse event
	return e[webui.@THEME@.dojo.dnd._copyKey];	// Boolean
};

webui.@THEME@.dojo.dnd._uniqueId = 0;
webui.@THEME@.dojo.dnd.getUniqueId = function(){
	// summary: returns a unique string for use with any DOM element
	var id;
	do{
		id = "dojoUnique" + (++webui.@THEME@.dojo.dnd._uniqueId);
	}while(webui.@THEME@.dojo.byId(id));
	return id;
};

webui.@THEME@.dojo.dnd._empty = {};

webui.@THEME@.dojo.dnd.isFormElement = function(/*Event*/ e){
	// summary: returns true, if user clicked on a form element
	var t = e.target;
	if(t.nodeType == 3 /*TEXT_NODE*/){
		t = t.parentNode;
	}
	return " button textarea input select option ".indexOf(" " + t.tagName.toLowerCase() + " ") >= 0;	// Boolean
};

}

if(!webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.autoscroll"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.autoscroll"] = true;
webui.@THEME@.dojo.provide("webui.@THEME@.dojo.dnd.autoscroll");

webui.@THEME@.dojo.dnd.getViewport = function(){
	// summary: returns a viewport size (visible part of the window)

	// FIXME: need more docs!!
	var d = webui.@THEME@.dojo.doc, dd = d.documentElement, w = window, b = webui.@THEME@.dojo.body();
	if(webui.@THEME@.dojo.isMozilla){
		return {w: dd.clientWidth, h: w.innerHeight};	// Object
	}else if(!webui.@THEME@.dojo.isOpera && w.innerWidth){
		return {w: w.innerWidth, h: w.innerHeight};		// Object
	}else if (!webui.@THEME@.dojo.isOpera && dd && dd.clientWidth){
		return {w: dd.clientWidth, h: dd.clientHeight};	// Object
	}else if (b.clientWidth){
		return {w: b.clientWidth, h: b.clientHeight};	// Object
	}
	return null;	// Object
};

webui.@THEME@.dojo.dnd.V_TRIGGER_AUTOSCROLL = 32;
webui.@THEME@.dojo.dnd.H_TRIGGER_AUTOSCROLL = 32;

webui.@THEME@.dojo.dnd.V_AUTOSCROLL_VALUE = 16;
webui.@THEME@.dojo.dnd.H_AUTOSCROLL_VALUE = 16;

webui.@THEME@.dojo.dnd.autoScroll = function(e){
	// summary:
	//		a handler for onmousemove event, which scrolls the window, if
	//		necesary
	// e: Event:
	//		onmousemove event

	// FIXME: needs more docs!
	var v = webui.@THEME@.dojo.dnd.getViewport(), dx = 0, dy = 0;
	if(e.clientX < webui.@THEME@.dojo.dnd.H_TRIGGER_AUTOSCROLL){
		dx = -webui.@THEME@.dojo.dnd.H_AUTOSCROLL_VALUE;
	}else if(e.clientX > v.w - webui.@THEME@.dojo.dnd.H_TRIGGER_AUTOSCROLL){
		dx = webui.@THEME@.dojo.dnd.H_AUTOSCROLL_VALUE;
	}
	if(e.clientY < webui.@THEME@.dojo.dnd.V_TRIGGER_AUTOSCROLL){
		dy = -webui.@THEME@.dojo.dnd.V_AUTOSCROLL_VALUE;
	}else if(e.clientY > v.h - webui.@THEME@.dojo.dnd.V_TRIGGER_AUTOSCROLL){
		dy = webui.@THEME@.dojo.dnd.V_AUTOSCROLL_VALUE;
	}
	window.scrollBy(dx, dy);
};

webui.@THEME@.dojo.dnd._validNodes = {"div": 1, "p": 1, "td": 1};
webui.@THEME@.dojo.dnd._validOverflow = {"auto": 1, "scroll": 1};

webui.@THEME@.dojo.dnd.autoScrollNodes = function(e){
	// summary:
	//		a handler for onmousemove event, which scrolls the first avaialble
	//		Dom element, it falls back to webui.@THEME@.dojo.dnd.autoScroll()
	// e: Event:
	//		onmousemove event

	// FIXME: needs more docs!
	for(var n = e.target; n;){
		if(n.nodeType == 1 && (n.tagName.toLowerCase() in webui.@THEME@.dojo.dnd._validNodes)){
			var s = webui.@THEME@.dojo.getComputedStyle(n);
			if(s.overflow.toLowerCase() in webui.@THEME@.dojo.dnd._validOverflow){
				var b = webui.@THEME@.dojo._getContentBox(n, s), t = webui.@THEME@.dojo._abs(n, true);
				// console.debug(b.l, b.t, t.x, t.y, n.scrollLeft, n.scrollTop);
				b.l += t.x + n.scrollLeft;
				b.t += t.y + n.scrollTop;
				var w = Math.min(webui.@THEME@.dojo.dnd.H_TRIGGER_AUTOSCROLL, b.w / 2), 
					h = Math.min(webui.@THEME@.dojo.dnd.V_TRIGGER_AUTOSCROLL, b.h / 2),
					rx = e.pageX - b.l, ry = e.pageY - b.t, dx = 0, dy = 0;
				if(rx > 0 && rx < b.w){
					if(rx < w){
						dx = -webui.@THEME@.dojo.dnd.H_AUTOSCROLL_VALUE;
					}else if(rx > b.w - w){
						dx = webui.@THEME@.dojo.dnd.H_AUTOSCROLL_VALUE;
					}
				}
				//console.debug("ry =", ry, "b.h =", b.h, "h =", h);
				if(ry > 0 && ry < b.h){
					if(ry < h){
						dy = -webui.@THEME@.dojo.dnd.V_AUTOSCROLL_VALUE;
					}else if(ry > b.h - h){
						dy = webui.@THEME@.dojo.dnd.V_AUTOSCROLL_VALUE;
					}
				}
				var oldLeft = n.scrollLeft, oldTop = n.scrollTop;
				n.scrollLeft = n.scrollLeft + dx;
				n.scrollTop  = n.scrollTop  + dy;
				// if(dx || dy){ console.debug(oldLeft + ", " + oldTop + "\n" + dx + ", " + dy + "\n" + n.scrollLeft + ", " + n.scrollTop); }
				if(oldLeft != n.scrollLeft || oldTop != n.scrollTop){ return; }
			}
		}
		try{
			n = n.parentNode;
		}catch(x){
			n = null;
		}
	}
	webui.@THEME@.dojo.dnd.autoScroll(e);
};

}

if(!webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Avatar"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Avatar"] = true;
webui.@THEME@.dojo.provide("webui.@THEME@.dojo.dnd.Avatar");



webui.@THEME@.dojo.dnd.Avatar = function(manager){
	// summary: an object, which represents transferred DnD items visually
	// manager: Object: a DnD manager object
	this.manager = manager;
	this.construct();
};

webui.@THEME@.dojo.extend(webui.@THEME@.dojo.dnd.Avatar, {
	construct: function(){
		// summary: a constructor function;
		//	it is separate so it can be (dynamically) overwritten in case of need
		var a = webui.@THEME@.dojo.doc.createElement("table");
		a.className = "dojoDndAvatar";
		a.style.position = "absolute";
		a.style.zIndex = 1999;
		a.style.margin = "0px"; // to avoid webui.@THEME@.dojo.marginBox() problems with table's margins
		var b = webui.@THEME@.dojo.doc.createElement("tbody");
		var tr = webui.@THEME@.dojo.doc.createElement("tr");
		tr.className = "dojoDndAvatarHeader";
		var td = webui.@THEME@.dojo.doc.createElement("td");
		td.innerHTML = this._generateText();
		tr.appendChild(td);
		webui.@THEME@.dojo.style(tr, "opacity", 0.9);
		b.appendChild(tr);
		var k = Math.min(5, this.manager.nodes.length);
		var source = this.manager.source;
		for(var i = 0; i < k; ++i){
			tr = webui.@THEME@.dojo.doc.createElement("tr");
			tr.className = "dojoDndAvatarItem";
			td = webui.@THEME@.dojo.doc.createElement("td");
			var node = source.creator ?
				// create an avatar representation of the node
				node = source._normalizedCreator(source.getItem(this.manager.nodes[i].id).data, "avatar").node :
				// or just clone the node and hope it works
				node = this.manager.nodes[i].cloneNode(true);
			node.id = "";
			td.appendChild(node);
			tr.appendChild(td);
			webui.@THEME@.dojo.style(tr, "opacity", (9 - i) / 10);
			b.appendChild(tr);
		}
		a.appendChild(b);
		this.node = a;
	},
	destroy: function(){
		// summary: a desctructor for the avatar, called to remove all references so it can be garbage-collected
		webui.@THEME@.dojo._destroyElement(this.node);
		this.node = false;
	},
	update: function(){
		// summary: updates the avatar to reflect the current DnD state
		webui.@THEME@.dojo[(this.manager.canDropFlag ? "add" : "remove") + "Class"](this.node, "dojoDndAvatarCanDrop");
		// replace text
		var t = this.node.getElementsByTagName("td");
		for(var i = 0; i < t.length; ++i){
			var n = t[i];
			if(webui.@THEME@.dojo.hasClass(n.parentNode, "dojoDndAvatarHeader")){
				n.innerHTML = this._generateText();
				break;
			}
		}
	},
	_generateText: function(){
		// summary: generates a proper text to reflect copying or moving of items
		return this.manager.nodes.length.toString();
	}
});

}

if(!webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Manager"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Manager"] = true;
webui.@THEME@.dojo.provide("webui.@THEME@.dojo.dnd.Manager");





webui.@THEME@.dojo.dnd.Manager = function(){
	// summary: the manager of DnD operations (usually a singleton)
	this.avatar  = null;
	this.source = null;
	this.nodes = [];
	this.copy  = true;
	this.target = null;
	this.canDropFlag = false;
	this.events = [];
};

webui.@THEME@.dojo.extend(webui.@THEME@.dojo.dnd.Manager, {
	// avatar's offset from the mouse
	OFFSET_X: 16,
	OFFSET_Y: 16,
	// methods
	overSource: function(source){
		// summary: called when a source detected a mouse-over conditiion
		// source: Object: the reporter
		if(this.avatar){
			this.target = (source && source.targetState != "Disabled") ? source : null;
			this.avatar.update();
		}
		webui.@THEME@.dojo.publish("/dnd/source/over", [source]);
	},
	outSource: function(source){
		// summary: called when a source detected a mouse-out conditiion
		// source: Object: the reporter
		if(this.avatar){
			if(this.target == source){
				this.target = null;
				this.canDropFlag = false;
				this.avatar.update();
				webui.@THEME@.dojo.publish("/dnd/source/over", [null]);
			}
		}else{
			webui.@THEME@.dojo.publish("/dnd/source/over", [null]);
		}
	},
	startDrag: function(source, nodes, copy){
		// summary: called to initiate the DnD operation
		// source: Object: the source which provides items
		// nodes: Array: the list of transferred items
		// copy: Boolean: copy items, if true, move items otherwise
		this.source = source;
		this.nodes  = nodes;
		this.copy   = Boolean(copy); // normalizing to true boolean
		this.avatar = this.makeAvatar();
		webui.@THEME@.dojo.body().appendChild(this.avatar.node);
		webui.@THEME@.dojo.publish("/dnd/start", [source, nodes, this.copy]);
		this.events = [
			webui.@THEME@.dojo.connect(webui.@THEME@.dojo.doc, "onmousemove", this, "onMouseMove"),
			webui.@THEME@.dojo.connect(webui.@THEME@.dojo.doc, "onmouseup",   this, "onMouseUp"),
			webui.@THEME@.dojo.connect(webui.@THEME@.dojo.doc, "onkeydown",   this, "onKeyDown"),
			webui.@THEME@.dojo.connect(webui.@THEME@.dojo.doc, "onkeyup",     this, "onKeyUp")
		];
		var c = "dojoDnd" + (copy ? "Copy" : "Move");
		webui.@THEME@.dojo.addClass(webui.@THEME@.dojo.body(), c); 
	},
	canDrop: function(flag){
		// summary: called to notify if the current target can accept items
		var canDropFlag = this.target && flag;
		if(this.canDropFlag != canDropFlag){
			this.canDropFlag = canDropFlag;
			this.avatar.update();
		}
	},
	stopDrag: function(){
		// summary: stop the DnD in progress
		webui.@THEME@.dojo.removeClass(webui.@THEME@.dojo.body(), "dojoDndCopy");
		webui.@THEME@.dojo.removeClass(webui.@THEME@.dojo.body(), "dojoDndMove");
		webui.@THEME@.dojo.forEach(this.events, webui.@THEME@.dojo.disconnect);
		this.events = [];
		this.avatar.destroy();
		this.avatar = null;
		this.source = null;
		this.nodes = [];
	},
	makeAvatar: function(){
		// summary: makes the avatar, it is separate to be overwritten dynamically, if needed
		return new webui.@THEME@.dojo.dnd.Avatar(this);
	},
	updateAvatar: function(){
		// summary: updates the avatar, it is separate to be overwritten dynamically, if needed
		this.avatar.update();
	},
	// mouse event processors
	onMouseMove: function(e){
		// summary: event processor for onmousemove
		// e: Event: mouse event
		var a = this.avatar;
		if(a){
			//webui.@THEME@.dojo.dnd.autoScrollNodes(e);
			webui.@THEME@.dojo.dnd.autoScroll(e);
			webui.@THEME@.dojo.marginBox(a.node, {l: e.pageX + this.OFFSET_X, t: e.pageY + this.OFFSET_Y});
			var copy = Boolean(this.source.copyState(webui.@THEME@.dojo.dnd.getCopyKeyState(e)));
			if(this.copy != copy){ 
				this._setCopyStatus(copy);
			}
		}
	},
	onMouseUp: function(e){
		// summary: event processor for onmouseup
		// e: Event: mouse event
		if(this.avatar && (!("mouseButton" in this.source) || this.source.mouseButton == e.button)){
			if(this.target && this.canDropFlag){
				var params = [this.source, this.nodes, Boolean(this.source.copyState(webui.@THEME@.dojo.dnd.getCopyKeyState(e))), this.target];
				webui.@THEME@.dojo.publish("/dnd/drop/before", params);
				webui.@THEME@.dojo.publish("/dnd/drop", params);
			}else{
				webui.@THEME@.dojo.publish("/dnd/cancel");
			}
			this.stopDrag();
		}
	},
	// keyboard event processors
	onKeyDown: function(e){
		// summary: event processor for onkeydown:
		//	watching for CTRL for copy/move status, watching for ESCAPE to cancel the drag
		// e: Event: keyboard event
		if(this.avatar){
			switch(e.keyCode){
				case webui.@THEME@.dojo.keys.CTRL:
					var copy = Boolean(this.source.copyState(true));
					if(this.copy != copy){ 
						this._setCopyStatus(copy);
					}
					break;
				case webui.@THEME@.dojo.keys.ESCAPE:
					webui.@THEME@.dojo.publish("/dnd/cancel");
					this.stopDrag();
					break;
			}
		}
	},
	onKeyUp: function(e){
		// summary: event processor for onkeyup, watching for CTRL for copy/move status
		// e: Event: keyboard event
		if(this.avatar && e.keyCode == webui.@THEME@.dojo.keys.CTRL){
			var copy = Boolean(this.source.copyState(false));
			if(this.copy != copy){ 
				this._setCopyStatus(copy);
			}
		}
	},
	// utilities
	_setCopyStatus: function(copy){
		// summary: changes the copy status
		// copy: Boolean: the copy status
		this.copy = copy;
		this.source._markDndStatus(this.copy);
		this.updateAvatar();
		webui.@THEME@.dojo.removeClass(webui.@THEME@.dojo.body(), "dojoDnd" + (this.copy ? "Move" : "Copy"));
		webui.@THEME@.dojo.addClass(webui.@THEME@.dojo.body(), "dojoDnd" + (this.copy ? "Copy" : "Move"));
	}
});

// summary: the manager singleton variable, can be overwritten, if needed
webui.@THEME@.dojo.dnd._manager = null;

webui.@THEME@.dojo.dnd.manager = function(){
	// summary: returns the current DnD manager, creates one if it is not created yet
	if(!webui.@THEME@.dojo.dnd._manager){
		webui.@THEME@.dojo.dnd._manager = new webui.@THEME@.dojo.dnd.Manager();
	}
	return webui.@THEME@.dojo.dnd._manager;	// Object
};

}

if(!webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Container"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Container"] = true;
webui.@THEME@.dojo.provide("webui.@THEME@.dojo.dnd.Container");




/*
	Container states:
		""		- normal state
		"Over"	- mouse over a container
	Container item states:
		""		- normal state
		"Over"	- mouse over a container item
*/

webui.@THEME@.dojo.declare("webui.@THEME@.dojo.dnd.Container", null, {
	// summary: a Container object, which knows when mouse hovers over it, 
	//	and know over which element it hovers
	
	// object attributes (for markup)
	skipForm: false,
	
	constructor: function(node, params){
		// summary: a constructor of the Container
		// node: Node: node or node's id to build the container on
		// params: Object: a dict of parameters, recognized parameters are:
		//	creator: Function: a creator function, which takes a data item, and returns an object like that:
		//		{node: newNode, data: usedData, type: arrayOfStrings}
		//	skipForm: Boolean: don't start the drag operation, if clicked on form elements
		//	_skipStartup: Boolean: skip startup(), which collects children, for deferred initialization
		//		(this is used in the markup mode)
		this.node = webui.@THEME@.dojo.byId(node);
		if(!params){ params = {}; }
		this.creator = params.creator || null;
		this.skipForm = params.skipForm;
		this.defaultCreator = webui.@THEME@.dojo.dnd._defaultCreator(this.node);

		// class-specific variables
		this.map = {};
		this.current = null;

		// states
		this.containerState = "";
		webui.@THEME@.dojo.addClass(this.node, "dojoDndContainer");
		
		// mark up children
		if(!(params && params._skipStartup)){
			this.startup();
		}

		// set up events
		this.events = [
			webui.@THEME@.dojo.connect(this.node, "onmouseover", this, "onMouseOver"),
			webui.@THEME@.dojo.connect(this.node, "onmouseout",  this, "onMouseOut"),
			// cancel text selection and text dragging
			webui.@THEME@.dojo.connect(this.node, "ondragstart",   this, "onSelectStart"),
			webui.@THEME@.dojo.connect(this.node, "onselectstart", this, "onSelectStart")
		];
	},
	
	// object attributes (for markup)
	creator: function(){},	// creator function, dummy at the moment
	
	// abstract access to the map
	getItem: function(/*String*/ key){
		// summary: returns a data item by its key (id)
		return this.map[key];	// Object
	},
	setItem: function(/*String*/ key, /*Object*/ data){
		// summary: associates a data item with its key (id)
		this.map[key] = data;
	},
	delItem: function(/*String*/ key){
		// summary: removes a data item from the map by its key (id)
		delete this.map[key];
	},
	forInItems: function(/*Function*/ f, /*Object?*/ o){
		// summary: iterates over a data map skipping members, which 
		//	are present in the empty object (IE and/or 3rd-party libraries).
		o = o || webui.@THEME@.dojo.global;
		var m = this.map, e = webui.@THEME@.dojo.dnd._empty;
		for(var i in this.map){
			if(i in e){ continue; }
			f.call(o, m[i], i, m);
		}
	},
	clearItems: function(){
		// summary: removes all data items from the map
		this.map = {};
	},
	
	// methods
	getAllNodes: function(){
		// summary: returns a list (an array) of all valid child nodes
		return webui.@THEME@.dojo.query("> .dojoDndItem", this.parent);	// NodeList
	},
	insertNodes: function(data, before, anchor){
		// summary: inserts an array of new nodes before/after an anchor node
		// data: Array: a list of data items, which should be processed by the creator function
		// before: Boolean: insert before the anchor, if true, and after the anchor otherwise
		// anchor: Node: the anchor node to be used as a point of insertion
		if(!this.parent.firstChild){
			anchor = null;
		}else if(before){
			if(!anchor){
				anchor = this.parent.firstChild;
			}
		}else{
			if(anchor){
				anchor = anchor.nextSibling;
			}
		}
		if(anchor){
			for(var i = 0; i < data.length; ++i){
				var t = this._normalizedCreator(data[i]);
				this.setItem(t.node.id, {data: t.data, type: t.type});
				this.parent.insertBefore(t.node, anchor);
			}
		}else{
			for(var i = 0; i < data.length; ++i){
				var t = this._normalizedCreator(data[i]);
				this.setItem(t.node.id, {data: t.data, type: t.type});
				this.parent.appendChild(t.node);
			}
		}
		return this;	// self
	},
	destroy: function(){
		// summary: prepares the object to be garbage-collected
		webui.@THEME@.dojo.forEach(this.events, webui.@THEME@.dojo.disconnect);
		this.clearItems();
		this.node = this.parent = this.current;
	},

	// markup methods
	markupFactory: function(params, node){
		params._skipStartup = true;
		return new webui.@THEME@.dojo.dnd.Container(node, params);
	},
	startup: function(){
		// summary: collects valid child items and populate the map
		
		// set up the real parent node
		this.parent = this.node;
		if(this.parent.tagName.toLowerCase() == "table"){
			var c = this.parent.getElementsByTagName("tbody");
			if(c && c.length){ this.parent = c[0]; }
		}

		// process specially marked children
		webui.@THEME@.dojo.query("> .dojoDndItem", this.parent).forEach(function(node){
			if(!node.id){ node.id = webui.@THEME@.dojo.dnd.getUniqueId(); }
			var type = node.getAttribute("dndType"),
				data = node.getAttribute("dndData");
			this.setItem(node.id, {
				data: data ? data : node.innerHTML,
				type: type ? type.split(/\s*,\s*/) : ["text"]
			});
		}, this);
	},

	// mouse events
	onMouseOver: function(e){
		// summary: event processor for onmouseover
		// e: Event: mouse event
		var n = e.relatedTarget;
		while(n){
			if(n == this.node){ break; }
			try{
				n = n.parentNode;
			}catch(x){
				n = null;
			}
		}
		if(!n){
			this._changeState("Container", "Over");
			this.onOverEvent();
		}
		n = this._getChildByEvent(e);
		if(this.current == n){ return; }
		if(this.current){ this._removeItemClass(this.current, "Over"); }
		if(n){ this._addItemClass(n, "Over"); }
		this.current = n;
	},
	onMouseOut: function(e){
		// summary: event processor for onmouseout
		// e: Event: mouse event
		for(var n = e.relatedTarget; n;){
			if(n == this.node){ return; }
			try{
				n = n.parentNode;
			}catch(x){
				n = null;
			}
		}
		if(this.current){
			this._removeItemClass(this.current, "Over");
			this.current = null;
		}
		this._changeState("Container", "");
		this.onOutEvent();
	},
	onSelectStart: function(e){
		// summary: event processor for onselectevent and ondragevent
		// e: Event: mouse event
		if(!this.skipForm || !webui.@THEME@.dojo.dnd.isFormElement(e)){
			webui.@THEME@.dojo.stopEvent(e);
		}
	},
	
	// utilities
	onOverEvent: function(){
		// summary: this function is called once, when mouse is over our container
	},
	onOutEvent: function(){
		// summary: this function is called once, when mouse is out of our container
	},
	_changeState: function(type, newState){
		// summary: changes a named state to new state value
		// type: String: a name of the state to change
		// newState: String: new state
		var prefix = "dojoDnd" + type;
		var state  = type.toLowerCase() + "State";
		//webui.@THEME@.dojo.replaceClass(this.node, prefix + newState, prefix + this[state]);
		webui.@THEME@.dojo.removeClass(this.node, prefix + this[state]);
		webui.@THEME@.dojo.addClass(this.node, prefix + newState);
		this[state] = newState;
	},
	_addItemClass: function(node, type){
		// summary: adds a class with prefix "dojoDndItem"
		// node: Node: a node
		// type: String: a variable suffix for a class name
		webui.@THEME@.dojo.addClass(node, "dojoDndItem" + type);
	},
	_removeItemClass: function(node, type){
		// summary: removes a class with prefix "dojoDndItem"
		// node: Node: a node
		// type: String: a variable suffix for a class name
		webui.@THEME@.dojo.removeClass(node, "dojoDndItem" + type);
	},
	_getChildByEvent: function(e){
		// summary: gets a child, which is under the mouse at the moment, or null
		// e: Event: a mouse event
		var node = e.target;
		if(node){
			for(var parent = node.parentNode; parent; node = parent, parent = node.parentNode){
				if(parent == this.parent && webui.@THEME@.dojo.hasClass(node, "dojoDndItem")){ return node; }
			}
		}
		return null;
	},
	_normalizedCreator: function(item, hint){
		// summary: adds all necessary data to the output of the user-supplied creator function
		var t = (this.creator ? this.creator : this.defaultCreator)(item, hint);
		if(!webui.@THEME@.dojo.isArray(t.type)){ t.type = ["text"]; }
		if(!t.node.id){ t.node.id = webui.@THEME@.dojo.dnd.getUniqueId(); }
		webui.@THEME@.dojo.addClass(t.node, "dojoDndItem");
		return t;
	}
});

webui.@THEME@.dojo.dnd._createNode = function(tag){
	// summary: returns a function, which creates an element of given tag 
	//	(SPAN by default) and sets its innerHTML to given text
	// tag: String: a tag name or empty for SPAN
	if(!tag){ return webui.@THEME@.dojo.dnd._createSpan; }
	return function(text){	// Function
		var n = webui.@THEME@.dojo.doc.createElement(tag);
		n.innerHTML = text;
		return n;
	};
};

webui.@THEME@.dojo.dnd._createTrTd = function(text){
	// summary: creates a TR/TD structure with given text as an innerHTML of TD
	// text: String: a text for TD
	var tr = webui.@THEME@.dojo.doc.createElement("tr");
	var td = webui.@THEME@.dojo.doc.createElement("td");
	td.innerHTML = text;
	tr.appendChild(td);
	return tr;	// Node
};

webui.@THEME@.dojo.dnd._createSpan = function(text){
	// summary: creates a SPAN element with given text as its innerHTML
	// text: String: a text for SPAN
	var n = webui.@THEME@.dojo.doc.createElement("span");
	n.innerHTML = text;
	return n;	// Node
};

// webui.@THEME@.dojo.dnd._defaultCreatorNodes: Object: a dicitionary, which maps container tag names to child tag names
webui.@THEME@.dojo.dnd._defaultCreatorNodes = {ul: "li", ol: "li", div: "div", p: "div"};

webui.@THEME@.dojo.dnd._defaultCreator = function(node){
	// summary: takes a container node, and returns an appropriate creator function
	// node: Node: a container node
	var tag = node.tagName.toLowerCase();
	var c = tag == "table" ? webui.@THEME@.dojo.dnd._createTrTd : webui.@THEME@.dojo.dnd._createNode(webui.@THEME@.dojo.dnd._defaultCreatorNodes[tag]);
	return function(item, hint){	// Function
		var isObj = webui.@THEME@.dojo.isObject(item) && item;
		var data = (isObj && item.data) ? item.data : item;
		var type = (isObj && item.type) ? item.type : ["text"];
		var t = String(data), n = (hint == "avatar" ? webui.@THEME@.dojo.dnd._createSpan : c)(t);
		n.id = webui.@THEME@.dojo.dnd.getUniqueId();
		return {node: n, data: data, type: type};
	};
};

}

if(!webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Selector"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Selector"] = true;
webui.@THEME@.dojo.provide("webui.@THEME@.dojo.dnd.Selector");




/*
	Container item states:
		""			- an item is not selected
		"Selected"	- an item is selected
		"Anchor"	- an item is selected, and is an anchor for a "shift" selection
*/

webui.@THEME@.dojo.declare("webui.@THEME@.dojo.dnd.Selector", webui.@THEME@.dojo.dnd.Container, {
	// summary: a Selector object, which knows how to select its children
	
	constructor: function(node, params){
		// summary: a constructor of the Selector
		// node: Node: node or node's id to build the selector on
		// params: Object: a dict of parameters, recognized parameters are:
		//	singular: Boolean: allows selection of only one element, if true
		//	the rest of parameters are passed to the container
		if(!params){ params = {}; }
		this.singular = params.singular;
		// class-specific variables
		this.selection = {};
		this.anchor = null;
		this.simpleSelection = false;
		// set up events
		this.events.push(
			webui.@THEME@.dojo.connect(this.node, "onmousedown", this, "onMouseDown"),
			webui.@THEME@.dojo.connect(this.node, "onmouseup",   this, "onMouseUp"));
	},
	
	// object attributes (for markup)
	singular: false,	// is singular property
	
	// methods
	getSelectedNodes: function(){
		// summary: returns a list (an array) of selected nodes
		var t = new webui.@THEME@.dojo.NodeList();
		var e = webui.@THEME@.dojo.dnd._empty;
		for(var i in this.selection){
			if(i in e){ continue; }
			t.push(webui.@THEME@.dojo.byId(i));
		}
		return t;	// Array
	},
	selectNone: function(){
		// summary: unselects all items
		return this._removeSelection()._removeAnchor();	// self
	},
	selectAll: function(){
		// summary: selects all items
		this.forInItems(function(data, id){
			this._addItemClass(webui.@THEME@.dojo.byId(id), "Selected");
			this.selection[id] = 1;
		}, this);
		return this._removeAnchor();	// self
	},
	deleteSelectedNodes: function(){
		// summary: deletes all selected items
		var e = webui.@THEME@.dojo.dnd._empty;
		for(var i in this.selection){
			if(i in e){ continue; }
			var n = webui.@THEME@.dojo.byId(i);
			this.delItem(i);
			webui.@THEME@.dojo._destroyElement(n);
		}
		this.anchor = null;
		this.selection = {};
		return this;	// self
	},
	insertNodes: function(addSelected, data, before, anchor){
		// summary: inserts new data items (see Container's insertNodes method for details)
		// addSelected: Boolean: all new nodes will be added to selected items, if true, no selection change otherwise
		// data: Array: a list of data items, which should be processed by the creator function
		// before: Boolean: insert before the anchor, if true, and after the anchor otherwise
		// anchor: Node: the anchor node to be used as a point of insertion
		var oldCreator = this._normalizedCreator;
		this._normalizedCreator = function(item, hint){
			var t = oldCreator.call(this, item, hint);
			if(addSelected){
				if(!this.anchor){
					this.anchor = t.node;
					this._removeItemClass(t.node, "Selected");
					this._addItemClass(this.anchor, "Anchor");
				}else if(this.anchor != t.node){
					this._removeItemClass(t.node, "Anchor");
					this._addItemClass(t.node, "Selected");
				}
				this.selection[t.node.id] = 1;
			}else{
				this._removeItemClass(t.node, "Selected");
				this._removeItemClass(t.node, "Anchor");
			}
			return t;
		};
		webui.@THEME@.dojo.dnd.Selector.superclass.insertNodes.call(this, data, before, anchor);
		this._normalizedCreator = oldCreator;
		return this;	// self
	},
	destroy: function(){
		// summary: prepares the object to be garbage-collected
		webui.@THEME@.dojo.dnd.Selector.superclass.destroy.call(this);
		this.selection = this.anchor = null;
	},

	// markup methods
	markupFactory: function(params, node){
		params._skipStartup = true;
		return new webui.@THEME@.dojo.dnd.Selector(node, params);
	},

	// mouse events
	onMouseDown: function(e){
		// summary: event processor for onmousedown
		// e: Event: mouse event
		if(!this.current){ return; }
		if(!this.singular && !webui.@THEME@.dojo.dnd.getCopyKeyState(e) && !e.shiftKey && (this.current.id in this.selection)){
			this.simpleSelection = true;
			webui.@THEME@.dojo.stopEvent(e);
			return;
		}
		if(!this.singular && e.shiftKey){
			if(!webui.@THEME@.dojo.dnd.getCopyKeyState(e)){
				this._removeSelection();
			}
			var c = webui.@THEME@.dojo.query("> .dojoDndItem", this.parent);
			if(c.length){
				if(!this.anchor){
					this.anchor = c[0];
					this._addItemClass(this.anchor, "Anchor");
				}
				this.selection[this.anchor.id] = 1;
				if(this.anchor != this.current){
					var i = 0;
					for(; i < c.length; ++i){
						var node = c[i];
						if(node == this.anchor || node == this.current){ break; }
					}
					for(++i; i < c.length; ++i){
						var node = c[i];
						if(node == this.anchor || node == this.current){ break; }
						this._addItemClass(node, "Selected");
						this.selection[node.id] = 1;
					}
					this._addItemClass(this.current, "Selected");
					this.selection[this.current.id] = 1;
				}
			}
		}else{
			if(this.singular){
				if(this.anchor == this.current){
					if(webui.@THEME@.dojo.dnd.getCopyKeyState(e)){
						this.selectNone();
					}
				}else{
					this.selectNone();
					this.anchor = this.current;
					this._addItemClass(this.anchor, "Anchor");
					this.selection[this.current.id] = 1;
				}
			}else{
				if(webui.@THEME@.dojo.dnd.getCopyKeyState(e)){
					if(this.anchor == this.current){
						delete this.selection[this.anchor.id];
						this._removeAnchor();
					}else{
						if(this.current.id in this.selection){
							this._removeItemClass(this.current, "Selected");
							delete this.selection[this.current.id];
						}else{
							if(this.anchor){
								this._removeItemClass(this.anchor, "Anchor");
								this._addItemClass(this.anchor, "Selected");
							}
							this.anchor = this.current;
							this._addItemClass(this.current, "Anchor");
							this.selection[this.current.id] = 1;
						}
					}
				}else{
					if(!(this.current.id in this.selection)){
						this.selectNone();
						this.anchor = this.current;
						this._addItemClass(this.current, "Anchor");
						this.selection[this.current.id] = 1;
					}
				}
			}
		}
		webui.@THEME@.dojo.stopEvent(e);
	},
	onMouseUp: function(e){
		// summary: event processor for onmouseup
		// e: Event: mouse event
		if(!this.simpleSelection){ return; }
		this.simpleSelection = false;
		this.selectNone();
		if(this.current){
			this.anchor = this.current;
			this._addItemClass(this.anchor, "Anchor");
			this.selection[this.current.id] = 1;
		}
	},
	onMouseMove: function(e){
		// summary: event processor for onmousemove
		// e: Event: mouse event
		this.simpleSelection = false;
	},
	
	// utilities
	onOverEvent: function(){
		// summary: this function is called once, when mouse is over our container
		this.onmousemoveEvent = webui.@THEME@.dojo.connect(this.node, "onmousemove", this, "onMouseMove");
	},
	onOutEvent: function(){
		// summary: this function is called once, when mouse is out of our container
		webui.@THEME@.dojo.disconnect(this.onmousemoveEvent);
		delete this.onmousemoveEvent;
	},
	_removeSelection: function(){
		// summary: unselects all items
		var e = webui.@THEME@.dojo.dnd._empty;
		for(var i in this.selection){
			if(i in e){ continue; }
			var node = webui.@THEME@.dojo.byId(i);
			if(node){ this._removeItemClass(node, "Selected"); }
		}
		this.selection = {};
		return this;	// self
	},
	_removeAnchor: function(){
		if(this.anchor){
			this._removeItemClass(this.anchor, "Anchor");
			this.anchor = null;
		}
		return this;	// self
	}
});

}

if(!webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Source"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
webui.@THEME@.dojo._hasResource["webui.@THEME@.dojo.dnd.Source"] = true;
webui.@THEME@.dojo.provide("webui.@THEME@.dojo.dnd.Source");




/*
	Container property:
		"Horizontal"- if this is the horizontal container
	Source states:
		""			- normal state
		"Moved"		- this source is being moved
		"Copied"	- this source is being copied
	Target states:
		""			- normal state
		"Disabled"	- the target cannot accept an avatar
	Target anchor state:
		""			- item is not selected
		"Before"	- insert point is before the anchor
		"After"		- insert point is after the anchor
*/

webui.@THEME@.dojo.declare("webui.@THEME@.dojo.dnd.Source", webui.@THEME@.dojo.dnd.Selector, {
	// summary: a Source object, which can be used as a DnD source, or a DnD target
	
	// object attributes (for markup)
	isSource: true,
	horizontal: false,
	copyOnly: false,
	skipForm: false,
	withHandles: false,
	accept: ["text"],
	
	constructor: function(node, params){
		// summary: a constructor of the Source
		// node: Node: node or node's id to build the source on
		// params: Object: a dict of parameters, recognized parameters are:
		//	isSource: Boolean: can be used as a DnD source, if true; assumed to be "true" if omitted
		//	accept: Array: list of accepted types (text strings) for a target; assumed to be ["text"] if omitted
		//	horizontal: Boolean: a horizontal container, if true, vertical otherwise or when omitted
		//	copyOnly: Boolean: always copy items, if true, use a state of Ctrl key otherwise
		//	withHandles: Boolean: allows dragging only by handles
		//	the rest of parameters are passed to the selector
		if(!params){ params = {}; }
		this.isSource = typeof params.isSource == "undefined" ? true : params.isSource;
		var type = params.accept instanceof Array ? params.accept : ["text"];
		this.accept = null;
		if(type.length){
			this.accept = {};
			for(var i = 0; i < type.length; ++i){
				this.accept[type[i]] = 1;
			}
		}
		this.horizontal = params.horizontal;
		this.copyOnly = params.copyOnly;
		this.withHandles = params.withHandles;
		// class-specific variables
		this.isDragging = false;
		this.mouseDown = false;
		this.targetAnchor = null;
		this.targetBox = null;
		this.before = true;
		// states
		this.sourceState  = "";
		if(this.isSource){
			webui.@THEME@.dojo.addClass(this.node, "dojoDndSource");
		}
		this.targetState  = "";
		if(this.accept){
			webui.@THEME@.dojo.addClass(this.node, "dojoDndTarget");
		}
		if(this.horizontal){
			webui.@THEME@.dojo.addClass(this.node, "dojoDndHorizontal");
		}
		// set up events
		this.topics = [
			webui.@THEME@.dojo.subscribe("/dnd/source/over", this, "onDndSourceOver"),
			webui.@THEME@.dojo.subscribe("/dnd/start",  this, "onDndStart"),
			webui.@THEME@.dojo.subscribe("/dnd/drop",   this, "onDndDrop"),
			webui.@THEME@.dojo.subscribe("/dnd/cancel", this, "onDndCancel")
		];
	},
	
	// methods
	checkAcceptance: function(source, nodes){
		// summary: checks, if the target can accept nodes from this source
		// source: Object: the source which provides items
		// nodes: Array: the list of transferred items
		if(this == source){ return true; }
		for(var i = 0; i < nodes.length; ++i){
			var type = source.getItem(nodes[i].id).type;
			// type instanceof Array
			var flag = false;
			for(var j = 0; j < type.length; ++j){
				if(type[j] in this.accept){
					flag = true;
					break;
				}
			}
			if(!flag){
				return false;	// Boolean
			}
		}
		return true;	// Boolean
	},
	copyState: function(keyPressed){
		// summary: Returns true, if we need to copy items, false to move.
		//		It is separated to be overwritten dynamically, if needed.
		// keyPressed: Boolean: the "copy" was pressed
		return this.copyOnly || keyPressed;	// Boolean
	},
	destroy: function(){
		// summary: prepares the object to be garbage-collected
		webui.@THEME@.dojo.dnd.Source.superclass.destroy.call(this);
		webui.@THEME@.dojo.forEach(this.topics, webui.@THEME@.dojo.unsubscribe);
		this.targetAnchor = null;
	},

	// markup methods
	markupFactory: function(params, node){
		params._skipStartup = true;
		return new webui.@THEME@.dojo.dnd.Source(node, params);
	},

	// mouse event processors
	onMouseMove: function(e){
		// summary: event processor for onmousemove
		// e: Event: mouse event
		if(this.isDragging && this.targetState == "Disabled"){ return; }
		webui.@THEME@.dojo.dnd.Source.superclass.onMouseMove.call(this, e);
		var m = webui.@THEME@.dojo.dnd.manager();
		if(this.isDragging){
			// calculate before/after
			var before = false;
			if(this.current){
				if(!this.targetBox || this.targetAnchor != this.current){
					this.targetBox = {
						xy: webui.@THEME@.dojo.coords(this.current, true),
						w: this.current.offsetWidth,
						h: this.current.offsetHeight
					};
				}
				if(this.horizontal){
					before = (e.pageX - this.targetBox.xy.x) < (this.targetBox.w / 2);
				}else{
					before = (e.pageY - this.targetBox.xy.y) < (this.targetBox.h / 2);
				}
			}
			if(this.current != this.targetAnchor || before != this.before){
				this._markTargetAnchor(before);
				m.canDrop(!this.current || m.source != this || !(this.current.id in this.selection));
			}
		}else{
			if(this.mouseDown && this.isSource){
				var nodes = this.getSelectedNodes();
				if(nodes.length){
					m.startDrag(this, nodes, this.copyState(webui.@THEME@.dojo.dnd.getCopyKeyState(e)));
				}
			}
		}
	},
	onMouseDown: function(e){
		// summary: event processor for onmousedown
		// e: Event: mouse event
		if(this._legalMouseDown(e) && (!this.skipForm || !webui.@THEME@.dojo.dnd.isFormElement(e))){
			this.mouseDown = true;
			this.mouseButton = e.button;
			webui.@THEME@.dojo.dnd.Source.superclass.onMouseDown.call(this, e);
		}
	},
	onMouseUp: function(e){
		// summary: event processor for onmouseup
		// e: Event: mouse event
		if(this.mouseDown){
			this.mouseDown = false;
			webui.@THEME@.dojo.dnd.Source.superclass.onMouseUp.call(this, e);
		}
	},
	
	// topic event processors
	onDndSourceOver: function(source){
		// summary: topic event processor for /dnd/source/over, called when detected a current source
		// source: Object: the source which has the mouse over it
		if(this != source){
			this.mouseDown = false;
			if(this.targetAnchor){
				this._unmarkTargetAnchor();
			}
		}else if(this.isDragging){
			var m = webui.@THEME@.dojo.dnd.manager();
			m.canDrop(this.targetState != "Disabled" && (!this.current || m.source != this || !(this.current.id in this.selection)));
		}
	},
	onDndStart: function(source, nodes, copy){
		// summary: topic event processor for /dnd/start, called to initiate the DnD operation
		// source: Object: the source which provides items
		// nodes: Array: the list of transferred items
		// copy: Boolean: copy items, if true, move items otherwise
		if(this.isSource){
			this._changeState("Source", this == source ? (copy ? "Copied" : "Moved") : "");
		}
		var accepted = this.accept && this.checkAcceptance(source, nodes);
		this._changeState("Target", accepted ? "" : "Disabled");
		if(accepted && this == source){
			webui.@THEME@.dojo.dnd.manager().overSource(this);
		}
		this.isDragging = true;
	},
	onDndDrop: function(source, nodes, copy){
		// summary: topic event processor for /dnd/drop, called to finish the DnD operation
		// source: Object: the source which provides items
		// nodes: Array: the list of transferred items
		// copy: Boolean: copy items, if true, move items otherwise
		do{ //break box
			if(this.containerState != "Over"){ break; }
			var oldCreator = this._normalizedCreator;
			if(this != source){
				// transferring nodes from the source to the target
				if(this.creator){
					// use defined creator
					this._normalizedCreator = function(node, hint){
						return oldCreator.call(this, source.getItem(node.id).data, hint);
					};
				}else{
					// we have no creator defined => move/clone nodes
					if(copy){
						// clone nodes
						this._normalizedCreator = function(node, hint){
							var t = source.getItem(node.id);
							var n = node.cloneNode(true);
							n.id = webui.@THEME@.dojo.dnd.getUniqueId();
							return {node: n, data: t.data, type: t.type};
						};
					}else{
						// move nodes
						this._normalizedCreator = function(node, hint){
							var t = source.getItem(node.id);
							source.delItem(node.id);
							return {node: node, data: t.data, type: t.type};
						};
					}
				}
			}else{
				// transferring nodes within the single source
				if(this.current && this.current.id in this.selection){ break; }
				if(this.creator){
					// use defined creator
					if(copy){
						// create new copies of data items
						this._normalizedCreator = function(node, hint){
							return oldCreator.call(this, source.getItem(node.id).data, hint);
						};
					}else{
						// move nodes
						if(!this.current){ break; }
						this._normalizedCreator = function(node, hint){
							var t = source.getItem(node.id);
							return {node: node, data: t.data, type: t.type};
						};
					}
				}else{
					// we have no creator defined => move/clone nodes
					if(copy){
						// clone nodes
						this._normalizedCreator = function(node, hint){
							var t = source.getItem(node.id);
							var n = node.cloneNode(true);
							n.id = webui.@THEME@.dojo.dnd.getUniqueId();
							return {node: n, data: t.data, type: t.type};
						};
					}else{
						// move nodes
						if(!this.current){ break; }
						this._normalizedCreator = function(node, hint){
							var t = source.getItem(node.id);
							return {node: node, data: t.data, type: t.type};
						};
					}
				}
			}
			this._removeSelection();
			if(this != source){
				this._removeAnchor();
			}
			if(this != source && !copy && !this.creator){
				source.selectNone();
			}
			this.insertNodes(true, nodes, this.before, this.current);
			if(this != source && !copy && this.creator){
				source.deleteSelectedNodes();
			}
			this._normalizedCreator = oldCreator;
		}while(false);
		this.onDndCancel();
	},
	onDndCancel: function(){
		// summary: topic event processor for /dnd/cancel, called to cancel the DnD operation
		if(this.targetAnchor){
			this._unmarkTargetAnchor();
			this.targetAnchor = null;
		}
		this.before = true;
		this.isDragging = false;
		this.mouseDown = false;
		delete this.mouseButton;
		this._changeState("Source", "");
		this._changeState("Target", "");
	},
	
	// utilities
	onOverEvent: function(){
		// summary: this function is called once, when mouse is over our container
		webui.@THEME@.dojo.dnd.Source.superclass.onOverEvent.call(this);
		webui.@THEME@.dojo.dnd.manager().overSource(this);
	},
	onOutEvent: function(){
		// summary: this function is called once, when mouse is out of our container
		webui.@THEME@.dojo.dnd.Source.superclass.onOutEvent.call(this);
		webui.@THEME@.dojo.dnd.manager().outSource(this);
	},
	_markTargetAnchor: function(before){
		// summary: assigns a class to the current target anchor based on "before" status
		// before: Boolean: insert before, if true, after otherwise
		if(this.current == this.targetAnchor && this.before == before){ return; }
		if(this.targetAnchor){
			this._removeItemClass(this.targetAnchor, this.before ? "Before" : "After");
		}
		this.targetAnchor = this.current;
		this.targetBox = null;
		this.before = before;
		if(this.targetAnchor){
			this._addItemClass(this.targetAnchor, this.before ? "Before" : "After");
		}
	},
	_unmarkTargetAnchor: function(){
		// summary: removes a class of the current target anchor based on "before" status
		if(!this.targetAnchor){ return; }
		this._removeItemClass(this.targetAnchor, this.before ? "Before" : "After");
		this.targetAnchor = null;
		this.targetBox = null;
		this.before = true;
	},
	_markDndStatus: function(copy){
		// summary: changes source's state based on "copy" status
		this._changeState("Source", copy ? "Copied" : "Moved");
	},
	_legalMouseDown: function(e){
		// summary: checks if user clicked on "approved" items
		// e: Event: mouse event
		if(!this.withHandles){ return true; }
		for(var node = e.target; node && !webui.@THEME@.dojo.hasClass(node, "dojoDndItem"); node = node.parentNode){
			if(webui.@THEME@.dojo.hasClass(node, "dojoDndHandle")){ return true; }
		}
		return false;	// Boolean
	}
});

webui.@THEME@.dojo.declare("webui.@THEME@.dojo.dnd.Target", webui.@THEME@.dojo.dnd.Source, {
	// summary: a Target object, which can be used as a DnD target
	
	constructor: function(node, params){
		// summary: a constructor of the Target --- see the Source constructor for details
		this.isSource = false;
		webui.@THEME@.dojo.removeClass(this.node, "dojoDndSource");
	},

	// markup methods
	markupFactory: function(params, node){
		params._skipStartup = true;
		return new webui.@THEME@.dojo.dnd.Target(node, params);
	}
});

}

