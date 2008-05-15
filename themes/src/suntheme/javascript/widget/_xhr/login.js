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

@JS_NS@._dojo.provide("@JS_NS@.widget._xhr.login");

@JS_NS@._dojo.require("@JS_NS@.json");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget.login");
@JS_NS@._dojo.require("@JS_NS@.widget._xhr.common");

/**
 * @class This class contains functions to authenticate data asynchronously 
 * using JSF Extensions as the underlying transfer protocol.
 * @static
 * @private
 */
@JS_NS@.widget._xhr.login =  {
    /**
     * This function is used to pass on the authentication data sent from 
     * the server callback object to the client. It calls the appropriate 
     * function in the login widget that updates the DOM tree to reflect 
     * the result of the authentication process to the end user.
     *
     * @param {String} id The HTML widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _loginCallback : function(id, content, closure, xjson) {
        // Parse JSON text and update login widget.
        var props = @JS_NS@.json.parse(content);

        // Publish an event for custom AJAX implementations 
	// to listen for.
        var widget = @JS_NS@.widget.common.getWidget(id);
        widget.setProps(props);
        
        // Publish an event for custom AJAX implementations to listen for.
        if (closure.endTopic) {
            @JS_NS@._dojo.publish(closure.endTopic, props);
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
     * @private
     */
    _processLoginEvent : function(props) {
        if (props == null) {
            return false;
        }

        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.id,
            callback: @JS_NS@.widget._xhr.login._loginCallback,
            closure: {
                endTopic: props.endTopic
            },
            xjson: {
                id: props.id,
                loginState: props.loginState,
                execute: props.id
            }
        });
        return true;
    }
};

// Listen for Widget events.
@JS_NS@._dojo.subscribe(@JS_NS@.widget.login.event.authenticate.beginTopic,
    @JS_NS@.widget._xhr.login, "_processLoginEvent");
