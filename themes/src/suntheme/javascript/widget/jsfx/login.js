/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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

webui.@THEME_JS@.dojo.provide("webui.@THEME_JS@.widget.jsfx.login");

webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.json");
webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.login");
webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.jsfx.dynaFaces");

/**
 * @class This class contains functions to authenticate data asynchronously 
 * using JSF Extensions as the underlying transfer protocol.
 * @static
 */
webui.@THEME_JS@.widget.jsfx.login =  {
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
        var props = webui.@THEME_JS@.json.parse(content);

        // Publish an event for custom AJAX implementations 
	// to listen for.
        var widget = webui.@THEME_JS@.dijit.byId(id);
        widget.setProps(props);
        
        // Publish an event for custom AJAX implementations to listen for.
        if (xjson.endTopic) {
            webui.@THEME_JS@.dojo.publish(xjson.endTopic, props);
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
            replaceElement: webui.@THEME_JS@.widget.jsfx.login.loginCallback,
            xjson: {
                id: props.id,
                loginState: props.loginState,
                endTopic: props.endTopic,
                keys: (props.keys) ? props.keys : "none"
            }
        });
        return true;
    }
};

// Listen for Dojo Widget events.
webui.@THEME_JS@.dojo.subscribe(webui.@THEME_JS@.widget.login.event.authenticate.beginTopic,
    webui.@THEME_JS@.widget.jsfx.login, "processLoginEvent");
