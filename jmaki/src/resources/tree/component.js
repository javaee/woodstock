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

jmaki.namespace("@JMAKI_NS@.tree");

/*
 * jMaki wrapper for Woodstock Tree widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     Initial data following the jMaki "tree" data model:
 *	      {root: {
 *	       id: "<root_identifier>",
 *	       label: "<root_label>",
 *	       image: {src: "<imageURL>", ...},
 *	       expanded: true | false,
 *	       children: [
 *			{id: "<node_identifier>", 
 *			 label: "<node_label>",
 *			 image: {src: "<imageURL>", ...},
 *			 expanded: true | false,
 *			 children: [
 *				      <child tree nodes>
 *				     ]},
 *			 ... <child nodes> ...
 *			]
 *	      }}
 *	      The children property is an array of similar tree node
 *	      definitions, down to the leaf nodes.  The image property
 *	      specifies an optional image to be displayed along with the
 *	      text (added Woodstock feature).  The leaf node
 *	      "action" property is not supported; however, the
 *	      Woodstock tree widget nodeSelection event will be propagated
 *	      to the jMaki tree onClick event listeners.
 * args:      Additional widget properties from the code snippet,
 *	      these properties are assumed to be underlying widget
 *	      properties and are passed through to the tree widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *	      default topic is "/woodstock/tree".
 * subscribe: Topic to subscribe to for data model events; if not
 *	      specified, the default topic is "/woodstock/tree".
 * id:	      User specified widget identifier; if not specified, the
 *	      jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * addNodes       Adds new children to the targeted node.
 * collapseAll    Collapse all nodes.
 * collapseNode   Collapse the targeted node.
 * expandAll      Expand all nodes.
 * expandNode     Expand the targeted node.
 * removeNode     Remove the targeted node and all its children.
 * removeChildren Remove the children of the targeted node.
 *
 * This widget publishes the following jMaki events:
 *
 * onClick    Published when a tree leaf node is selected or de-selected.
 * onCollapse Published when a tree node with children is collapsed.
 * onExpand   Published when a tree node with children is expanded.
 */
@JMAKI_NS@.tree.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/@JS_NAME@/tree"];
    this._publish = "/@JS_NAME@/tree";
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
@JMAKI_NS@.tree.Widget.prototype._create = function(wargs) {

    // Process the jMaki wrapper properties for a Woodstock tree.
    var props = {};
    if (wargs.args != null) {
	woodstock4_3._base.proto._extend(props, wargs.args);
    }
    if (wargs.value && wargs.value != null) {
	this._mapProperties(props, wargs.value);
    }
    // Make sure required properties exist.
    if (props.label == null) {
	props.label = "Tree root";
    }
    if (props.childNodes == null) {
	props.childNodes = [];
    }

    // Subscribe to jMaki events
    for (var i = 0; i < this._subscribe.length; i++) {
	var s1 = jmaki.subscribe(this._subscribe[i] + "/addNodes",
	    @JS_NS@.widget.common._hitch(this, "_addNodesCallback"));
	this._subscriptions.push(s1);
	var s2 = jmaki.subscribe(this._subscribe[i] + "/collapseAll",
	    @JS_NS@.widget.common._hitch(this, "_collapseAllCallback"));
	this._subscriptions.push(s2);
	var s3 = jmaki.subscribe(this._subscribe[i] + "/collapseNode",
	    @JS_NS@.widget.common._hitch(this, "_collapseNodeCallback"));
	this._subscriptions.push(s3);
	var s4 = jmaki.subscribe(this._subscribe[i] + "/expandAll",
	    @JS_NS@.widget.common._hitch(this, "_expandAllCallback"));
	this._subscriptions.push(s4);
	var s5 = jmaki.subscribe(this._subscribe[i] + "/expandNode",
	    @JS_NS@.widget.common._hitch(this, "_expandNodeCallback"));
	this._subscriptions.push(s5);
	var s6 = jmaki.subscribe(this._subscribe[i] + "/removeNode",
	    @JS_NS@.widget.common._hitch(this, "_removeNodeCallback"));
	this._subscriptions.push(s6);
	var s7 = jmaki.subscribe(this._subscribe[i] + "/removeChildren",
	    @JS_NS@.widget.common._hitch(this, "_removeChildrenCallback"));
	this._subscriptions.push(s7);
    }

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "tree";

    // Create the Woodstock tree widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);

    // Set event callback handlers so we can publish jMaki events.
    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.tree.event.nodeSelection.beginTopic,
	null, @JS_NS@.widget.common._hitch(this, "_nodeSelectedCallback"));
    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.tree.event.nodeToggle.endTopic,
	null, @JS_NS@.widget.common._hitch(this, "_nodeToggledCallback"));

};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.tree.Widget.prototype.destroy = function() {

    if (this._subscriptions) {
	for (var i = 0; i < this._subscriptions.length; i++) {
	    jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.tree.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Map wrapper properties to underlying widget properties.
// props -> JS object to contain underlying widget properties
// value -> JS object in tree node data model format
@JMAKI_NS@.tree.Widget.prototype._mapProperties = function(props, value) {

    // Data model has a root property, but widget does not.
    var src = value;
    if (value.root != null) {
	src = value.root;
    }
    // Note we replace root node id later with widget id.
    var obj = this._mapNode(null, 1, src);
    woodstock4_3._base.proto._extend(props, obj);

};

// Function to map a tree node object and its children.
// pid   -> Node id of parent node (null if root node).
// index -> Index of current child node in parent.
// src   -> JS object for current tree node in jMaki data model format.
// Returns a JS object for a Woodstock tree widget node.
// If node has children, they are recursively processed.
@JMAKI_NS@.tree.Widget.prototype._mapNode = function(pid, index, src) {

    var node = {};
    var carr = null;
    for (p in src) {
	if (p == "children") {
	    // Process children at end.
	    carr = src.children;
	} else {
	    node[p] = src[p];
	}
    }	// End of for
    if (node.id == null && pid != null) {
	// No id present, fake one up.
	node.id = pid + "_" + index;
    }
    if (pid != null) {
	node.parent = pid;
    }
    if (carr instanceof Array) {
	// Got children, process them....
	node.childNodes = [];
	for (var i = 0; i < carr.length; i++) {
	    var obj = this._mapNode(node.id, (i+1), carr[i]);
	    node.childNodes.push(obj);
	}	// End of for
    }
    return node;

};

// Recursively walk tree and expand or collapse child trunk nodes.
@JMAKI_NS@.tree.Widget.prototype._recurseToggle = function(widget, nodeId, bExpand) {

    var carr = widget.getChildNodes(nodeId);
    if (carr instanceof Array) {
	for (var i = 0; i < carr.length; i++) {
	    var props = widget.getNodeProps(carr[i]);
	    if (props.childNodes instanceof Array) {
		this._recurseToggle(widget, carr[i], bExpand);
	    }
	    if (props.expanded != null && props.expanded != bExpand) {
		widget.toggleNode(carr[i]);
	    }
	}	// End of for
    }

}

// Event callback handler for tree addNodes event.
// Event payload: {targetId: <nodeId>, value: {<node>} or [{node}, ...]}
// Adds the value node or array of nodes to the targeted tree node.
@JMAKI_NS@.tree.Widget.prototype._addNodesCallback = function(payload) {

    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    var nodeId = payload.targetId;
	    if (widget.getNodeProps(nodeId)) {
		var arr = [];
		if (payload.value) {
		    if (payload.value instanceof Array) {
			var obj2 = this._mapNode(nodeId, 1, 
 				{id: nodeId, children: payload.value});
			arr = obj2.childNodes;
		    } else {
			var obj = this._mapNode(nodeId, 1, payload.value);
			arr.push(obj);
		    }
		    if (arr.length > 0) {
			widget.addNodes(arr);
		    }
		}
	    }    
	}
    }

};

// Event callback handler for  tree removeNode event.
// Event payload: {targetId: <nodeId>}
// Removes the tree node and all its children from the tree.
@JMAKI_NS@.tree.Widget.prototype._removeNodeCallback = function(payload) {

    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    if (typeof payload.targetId == "string") {
		widget.deleteNode(payload.targetId);
	    }
	}
    }

};

// Event callback handler for  tree removeChildren event.
// Event payload: {targetId: <nodeId>}
// Removes the tree node and all its children from the tree.
@JMAKI_NS@.tree.Widget.prototype._removeChildrenCallback = function(payload) {

    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    if (typeof payload.targetId == "string") {
		var carr = widget.getChildNodes(payload.targetId);
		if (carr instanceof Array) {
		    for (var i = 0; i < props.childNodes.length; i++) {
			widget.deleteNode(carr[i]);
		    }	// End of for
		}
	    }
	}
    }

};

// Event callback handler for  tree collapseNode event.
// Event payload: {targetId: <nodeId>}
// If the node is expanded, will collapse it.
@JMAKI_NS@.tree.Widget.prototype._collapseNodeCallback = function(payload) {

    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    if (typeof payload.targetId == "string") {
		var props = widget.getNodeProps(payload.targetId);
		if (props.expanded != null && props.expanded == true) {
		    widget.toggleNode(payload.targetId);
		}
	    }
	}
    }

};

// Event callback handler for  tree expandNode event.
// Event payload: {targetId: <nodeId>}
// If the node is collapsed, will expand it.
@JMAKI_NS@.tree.Widget.prototype._expandNodeCallback = function(payload) {

    if (payload) {
	var widget = @JS_NS@.widget.common.getWidget(this._wid);
	if (widget) {
	    if (typeof payload.targetId == "string") {
		var props = widget.getNodeProps(payload.targetId);
		if (props.expanded != null && props.expanded == false) {
		    widget.toggleNode(payload.targetId);
		}
	    }
	}
    }

};

// Event callback handler for  tree collapseAll event.
// Event payload: none
// If the root node is expanded, will collapse it.
@JMAKI_NS@.tree.Widget.prototype._collapseAllCallback = function() {

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	this._recurseToggle(widget, this._wid, false);
    }

};

// Event callback handler for  tree expandAll event.
// Event payload: none
// Recursively expands all children of the root node.
@JMAKI_NS@.tree.Widget.prototype._expandAllCallback = function() {

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	this._recurseToggle(widget, this._wid, true);
    }

};

// Event callback handler for widget nodeSelection event.
// Woodstock event payload: [{id: <widgetId>, nodeId: <nodeId>}]
// Publish jMaki onClick event only if node is a leaf node.
// Event payload: {widgetId: <widgetId>, type: 'onClick', targetId: <nodeId>}
// XXXX - How does this relate to widget onClick property?
@JMAKI_NS@.tree.Widget.prototype._nodeSelectedCallback = function(payload) {

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	var id = null;
	if (payload instanceof Array) {
	    id = payload[0].nodeId;
	} else {
	    id = payload.nodeId;
	}
	if (typeof id == "string") {
	    var props = widget.getNodeProps(id);
	    if (props.selected && props.selected == true 
		&& props.childNodes == null) {
		jmaki.processActions({
		    action: "onClick",
		    targetId: id,
		    topic: this._publish + "/onClick",
		    type: "onClick",
		    widgetId: this._wid
		});
	    }
	}
    }

};

// Event callback handler for widget toggleNode event.
// Woodstock event payload: {id: <widgetId>, nodeId: <nodeId>, expanded: <value>}
// Publish jMaki onCollapse event if the node is not expanded,
// and the onExpanded event if the node is expanded.
// Event payload: {widgetId: <widgetId>, type: 'onCollapse', targetId: <nodeId>}
// Event payload: {widgetId: <widgetId>, type: 'onExpand', targetId: <nodeId>}
@JMAKI_NS@.tree.Widget.prototype._nodeToggledCallback = function(payload) {

    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	if (typeof payload.nodeId == "string") {
	    var etype = "onCollapse";
	    if (payload.expanded != null && payload.expanded == true) {
		etype = "onExpand";
	    }
	    jmaki.processActions({
		action: etype,
		targetId: payload.nodeId,
		topic: this._publish + "/" + etype,
		type: etype,
		widgetId: this._wid
	    });
	}
    }

};
