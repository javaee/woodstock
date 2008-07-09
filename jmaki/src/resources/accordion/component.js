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

jmaki.namespace("@JMAKI_NS@.accordion");

/*
 * jMaki wrapper for Woodstock accordion widget.
 *
 * This widget wrapper looks for the following properties in
 * the "wargs" parameter:
 *
 * value:     The data value following the jMaki multi-view container
 *            data model.  Each object in the "items" array defines a
 *            tab instance whose contents is specified as an escaped html
 *            string ("content"), an included source file ("include"),
 *            or a reference to another widget on the page ("refId").
 *            Each example is shown:
 *            {items: [
 *              {id: <tabid>, label: <title>, content: "<html string>"},
 *              {id: <tabid>, label: <title>, include: "<source_url>"},
 *              {id: <tabid>, label: <title>, refId: "<widget_id>"}
 *              ]}
 *            The "selected" property can be set to true, which causes
 *            that tab to be rendered open in the accordion.  If the "id"
 *            property is omitted, it is set to the accordion id plus
 *            "_tab<index>".  For refId, the referenced widget must be
 *            rendered on the page before the accordion widget is rendered.
 *            The lazyload property is not supported on the tab entries,
 *            but a "loadOnSelect" property can be set on the tabset itself,
 *            effectively lazy loading all the tabs in the set.
 * args:      Additional widget properties from the code snippet,
 *            these properties are assumed to be underlying widget
 *            properties and are passed through to the widget.
 * publish:   Topic to publish jMaki events to; if not specified, the
 *            default topic is "/woodstock/accordion".
 * subscribe: Topic to subscribe to for data model events; if not
 *            specified, the default topic is "/woodstock/accordion".
 * id:        User specified widget identifier; if not specified, the
 *            jMaki auto-generated identifier is used.
 * 
 * This widget subscribes to the following jMaki events:
 *
 * select     Select a tab:
 *            {targetId: "<tab_id>"}
 * setContent Reset the content of a tab to an escaped html string value:
 *            {targetId: "<tab_id>", value: "<escaped_html_string>"}
 * setInclude Reset the content of a tab with the contents included
 *            from a source file (containing html and/or widgets):
 *            {targetId: "<tab_id>", value: "<source_url>"}
 * setRefid   Reset the content of a tab to reference another widget:
 *            {targetId: "<tab_id>", value: "<widget_identifier>"}
 *
 * This widget publishes the following jMaki events:
 * 
 * onSelect   Notify subscribers that a tab has been selected
 *            {widgetId: <wid>, type: "onSelect", targetId: "<tab_id>",
 *             topic: "/woodstock/accordion/select"}
 * 
 */
@JMAKI_NS@.accordion.Widget = function(wargs) {

    // Initialize basic wrapper properties.
    this._subscribe = ["/woodstock/accordion"];
    this._publish = "/woodstock/accordion";
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

    // Subscribe to jMaki events
    for (var i = 0; i < this._subscribe.length; i++) {
	var s1 = jmaki.subscribe(this._subscribe[i] + "/select", 
	@JS_NS@.widget.common._hitch(this, "_selectCallback"));
	this._subscriptions.push(s1);
	var s2 = jmaki.subscribe(this._subscribe[i] + "/setContent", 
	@JS_NS@.widget.common._hitch(this, "_setContentCallback"));
	this._subscriptions.push(s2);
	var s3 = jmaki.subscribe(this._subscribe[i] + "/setInclude", 
	@JS_NS@.widget.common._hitch(this, "_setIncludeCallback"));
	this._subscriptions.push(s3);
	var s4 = jmaki.subscribe(this._subscribe[i] + "/setRefid", 
	@JS_NS@.widget.common._hitch(this, "_setRefidCallback"));
	this._subscriptions.push(s4);
    }

    // Create Woodstock widget.
    this._create(wargs);
};

// Create Woodstock widget.
@JMAKI_NS@.accordion.Widget.prototype._create = function(wargs) {

    // Get the jMaki wrapper properties for a Woodstock accordion.
    var props = {};
    if (wargs.args != null) {
	@JS_NS@._base.proto._extend(props, wargs.args);
    }
    if (wargs.value != null) {
	@JS_NS@._base.proto._extend(props, wargs.value);
    }
    this._convertModel(props);

    // Add our widget id and type.
    props.id = this._wid;
    props.widgetType = "accordion";

    // Create the Woodstock accordion widget.
    var span_id = wargs.uuid + "_span";
    @JS_NS@.widget.common.createWidget(span_id, props);
       
    // add event handling
    @JS_NS@.widget.common.subscribe(@JS_NS@.widget.accordionTab.event.title.selectedTopic,
	this, "_onTabSelected");    
    
};

// Unsubscribe from jMaki events and destroy the Woodstock widget.
@JMAKI_NS@.accordion.Widget.prototype.destroy = function() {
    if (this._subscriptions) {
	for (var i = 0; i < this._subscriptions.length; i++) {
	    jmaki.unsubscribe(this._subscriptions[i]);
	} // End of for
    }
    @JS_NS@.widget.common.destroyWidget(this._wid);
};

// Warning: jMaki calls this function using a global scope. In order to
// access variables and functions in "this" object, closures must be used.
@JMAKI_NS@.accordion.Widget.prototype.postLoad = function() {
    // Do nothing...
};

// Callback function to handle tab selection event.
// Event payload: {targetId: "<tab_id>"}
@JMAKI_NS@.accordion.Widget.prototype._selectCallback = function(payload) {
    if (payload && typeof payload.targetId == "string") {
	this._select(payload.targetId);
    }
};

// Callback function to handle resetting tab content via escaped html string..
// Event payload: {targetId: "<tab_id>", value: "<escaped_html_string>"}
@JMAKI_NS@.accordion.Widget.prototype._setContentCallback = function(payload) {
    if (payload && typeof payload.targetId == "string") {
	this._setContent(payload.targetId, payload.value);
    }
};

// Callback function to handle resetting tab contents via include.
// Event payload: {targetId: "<tab_id>", value: "<source_url>"}
@JMAKI_NS@.accordion.Widget.prototype._setIncludeCallback = function(payload) {
    if (payload && typeof payload.targetId == "string") {
	this._setInclude(payload.targetId, payload.value);
    }
};

// Callback function to handle resetting tab contents via widge reference.
// Event payload: {targetId: "<tab_id>", value: "<widget_id>"}
@JMAKI_NS@.accordion.Widget.prototype._setRefidCallback = function(payload) {
    if (payload && typeof payload.targetId == "string") {
	this._setRefid(payload.targetId, payload.value);
    }
};

// select a tab
@JMAKI_NS@.accordion.Widget.prototype._select = function(tabId) {
    var widget = @JS_NS@.widget.common.getWidget(this._wid);
    if (widget) {
	widget.tabSelected({id: tabId});
    }
};

// Set the content of the tab with escaped html string.
@JMAKI_NS@.accordion.Widget.prototype._setContent = function(tabId, content) {
    var widgetTab = @JS_NS@.widget.common.getWidget(tabId);
    if (widgetTab && typeof content == "string") {
	var props = {};
	var tabContentElement = {html: content};	       
	props.tabContent = [];
	props.tabContent.push(tabContentElement);
	widgetTab.setProps(props);
    }
};

// Set the content of the tab with include source.
@JMAKI_NS@.accordion.Widget.prototype._setInclude = function(tabId, include) {
    var widgetTab = @JS_NS@.widget.common.getWidget(tabId);
    if (widgetTab && typeof include == "string") {
	var props = {};
	var tabContentElement = {include: include};	       
	props.tabContent = [];
	props.tabContent.push(tabContentElement);
	widgetTab.setProps(props);
    }
};

// Set the content of the tab with referenced widget.
@JMAKI_NS@.accordion.Widget.prototype._setRefid = function(tabId, refid) {
    var widgetTab = @JS_NS@.widget.common.getWidget(tabId);
    if (widgetTab && typeof refid == "string") {
	var props = {};
	var tabContentElement = {refId: refid};	       
	props.tabContent = [];
	props.tabContent.push(tabContentElement);
	widgetTab.setProps(props);
    }
};

// A tab was selected, publish jMaki onSelect event.
// Event payload: {widgetId: <wid>, type: "onSelect", targetId: "<tab_id>",
//		   topic: "/woodstock/accordion/select"}
@JMAKI_NS@.accordion.Widget.prototype._onTabSelected = function(props) {
    if (props && typeof props.id == "string") {
	jmaki.processActions({
	    action: "onSelect",
	    targetId: props.id,
	    topic: this._publish + "/onSelect",
	    type: "onSelect",
	    widgetId: this._wid
	});
    }
};

// Converts jmaki model to props digestable by woodstock accordion widget
// Each "items" array object is mapped to an accordionTab widget.
// Within each items object, the property mapping:
//   id       -> id           (default: widgetId + "_tab" + <index>)
//   label    -> title        (default: "Tab <index>"
//   content  -> tabContents: [{content: <content_value>}]
//   include  -> tabContents: [{include: <include_url>}]
//   refId    -> tabContents: [{refId: <refid_value>}] (Woodstock only)
//   selected -> selected
//   lazyLoad -> Not supported; see loadOnSelect
@JMAKI_NS@.accordion.Widget.prototype._convertModel = function(props){
    
    props.tabs = [];
    props.toggleControls = true;
    if (!props.items) {
	return false;
    }

    if (props.items instanceof Array) {
	for (var i = 0; i < props.items.length; i += 1) {
	    var item = props.items[i];
	    var tab  = {};
	    tab.widgetType = "accordionTab";
	    tab.id = (item.id != null) ? item.id : this._wid + "_tab" + i;
	    tab.title = (item.label != null) ? item.label : "Tab " + i;
	    tab.tabContent = [];
	    if (item.include != null) {
		var tabContentElement = {};
		tabContentElement.include = item.include;
		tab.tabContent.push(tabContentElement);
	    }
	    if (item.content != null) {
		var tabContentElement = {};
		tabContentElement.html = item.content;
		tab.tabContent.push(tabContentElement);
	    } 
	    if (typeof item.refId == "string") {
		var tabContentElement = {};
		tabContentElement.refId = item.refId;
		tab.tabContent.push(tabContentElement);
	    } 
	    if (item.selected && item.selected == true) {
		tab.selected = true;
	    }
	    props.tabs.push(tab);	
	}	// End of for
    }
};
