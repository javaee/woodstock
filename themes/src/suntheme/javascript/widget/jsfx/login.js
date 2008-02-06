// widget/jsfx/login.js
//
// Copyright 2006 by Sun Microsystems, Inc. All rights reserved.
// Use is subject to license terms.
//
// This Javascript file should be included in any page 
// that uses the associated component, where JSF Extensions 
// is used as the underlying transfer protocol.
//
// Javascript function that listens to events published by the
// login widget and make server side calls as per the Dynamic
// Faces protocols. After receiving a response from the XmlHttp
// reqest it publishes an event signalling the end of the 
// XHR request/response cycle. The login widget which was
// listening on this end event uses data supplied by the server
// to update the DOM tree reflecting the state of the authentication
// process.

webui.@THEME@.dojo.provide("webui.@THEME@.widget.jsfx.login");

webui.@THEME@.dojo.require("webui.@THEME@.json");
webui.@THEME@.dojo.require("webui.@THEME@.widget.login");
webui.@THEME@.dojo.require("webui.@THEME@.widget.jsfx.dynaFaces");

/**
 * @class This class contains functions to authenticate data asynchronously 
 * using JSF Extensions as the underlying transfer protocol.
 * @static
 */
webui.@THEME@.widget.jsfx.login =  {
    /**
     * This function is used to pass on the authentication data sent from 
     * the server callback object to the client. It calls the appropriate 
     * function in the login widget that updates the DOM tree to reflect 
     * the result of the authentication process to the end user.
     *
     * @param {String} elementId The HTML element Id.
     * @param {Object} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to DynaFaces.fireAjaxTransaction.
     * @param {Object} xjson The xjson argument provided to DynaFaces.fireAjaxTransaction.
     * @return {boolean} true if successful; otherwise, false.
     */
    loginCallback : function(id, content, closure, xjson) {
        // Parse JSON text and update login widget.
        var props = webui.@THEME@.json.parse(content);

        // Publish an event for custom AJAX implementations 
	// to listen for.
        var widget = webui.@THEME@.dijit.byId(id);
        widget.setProps(props);
        
        // Publish an event for custom AJAX implementations to listen for.
        if (xjson.endTopic) {
            webui.@THEME@.dojo.publish(xjson.endTopic, props);
        }
        return true;
    },

    /**
     * This function is used to send data from the
     * client to the server process where the actual
     * JAAS authentication is taking place.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} loginState
     * @config {String} endTopic
     * @config {String} keys
     * @return {boolean} true if successful; otherwise, false.
     */
    processLoginEvent : function(props) {
        if (props == null) {
            return false;
        }
        var domNode = document.getElementById(props.id); 

        // Generate AJAX request using the JSF Extensions library.
        DynaFaces.fireAjaxTransaction(
            (domNode) ? domNode : document.forms[0], {
            execute: props.id,
            render: props.id,
            replaceElement: webui.@THEME@.widget.jsfx.login.loginCallback,
            xjson: {
                id: props.id,
                loginState: props.loginState,
                endTopic: props.endTopic,
                keys: (props.keys) ? props.keys : "none"
            }
        });
    }
}

// Listen for Dojo Widget events.
webui.@THEME@.dojo.subscribe(webui.@THEME@.widget.login.event.authenticate.beginTopic,
    webui.@THEME@.widget.jsfx.login, "processLoginEvent");
