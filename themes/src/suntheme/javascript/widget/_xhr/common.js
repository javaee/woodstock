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

@JS_NS@._dojo.provide("@JS_NS@.widget._xhr.common");

@JS_NS@._dojo.require("@JS_NS@.json");
@JS_NS@._dojo.require("@JS_NS@.widget.common");

/**
 * @class This class contains functions to obtain data asynchronously using the
 * XMLHTTP request object (XHR) the underlying protocol.
 * @static
 * @private
 */
@JS_NS@.widget._xhr.common = {
    /**
     * This function is used to generate a request using the XMLHTTP 
     * request object (XHR) the underlying protocol.
     *
     * Note: Unlike the @JS_NS@.xhr._doRequest function, the generated request
     * is always a "GET" and form data is submitted.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @param {String} id The widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure object provided to the callback function.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @private
     */
    _doRequest: function(props) {
        if (props == null || props.id == null) {
            return false;
        }

        // Get properties for JavaScript closures.
        var _callback = props.callback ? props.callback : null;
        var _closure = props.closure ? props.closure : null;
        var _id = props.id ? props.id : null;
        var _url = @JS_NS@._base.config.ajax.url;
        var _xjson = props.xjson ? props.xjson : null;

        // Get form.
        var _form = @JS_NS@.widget.common._getForm(
            document.getElementById(props.id));
        if (_form == null) {
            _form = document.forms[0];
        }

        // Ensure URL has been provided.
        if (_url == null) {
            // Attempt to obtain default action url from form.
            if (_form && _form.action) {
                _url = _form.action;
            } else {
                console.warn("URL for Ajax transaction not provided. " +
                    "Set form action or Ajax config property");
                return false;
            }
        }

        // Generate AJAX request.
        @JS_NS@._dojo.xhrGet({
            error: function(content, ioArgs) {
                console.error("HTTP status code: ", ioArgs.xhr.status);
                return content;
            },
            form: _form,
            headers: {
                "X-JSON": @JS_NS@.json.stringify(_xjson),
                "X-Requested-With": "XMLHttpRequest",
                "X-Woodstock-Version": "@THEME_VERSION@"
            },
            load: function(content, ioArgs) {
                if (typeof _callback == "function") {
                    _callback(_id, content, _closure, _xjson);
                }
                return content;
            },
            timeout: 5000, // Time in milliseconds
            url: _url
        });
    },

    /**
     * This function is used to process refresh events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} endTopic The event topic to publish.
     * @config {String} execute The string containing a comma separated list 
     * of client ids against which the execute portion of the request 
     * processing lifecycle must be run.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processRefreshEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.id,
            callback: @JS_NS@.widget._xhr.common._refreshCallback,
            closure: {
                endTopic: props.endTopic
            },
            xjson: {
                id: props.id,
                event: "refresh",
                execute: (props.execute) ? props.execute : "none"
            }
        });
        return true;
    },

    /**
     * This function is used to process state change events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} endTopic The event topic to publish.
     * @config {Object} props Key-Value pairs of widget properties to update.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processStateEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.id,
            callback: @JS_NS@.widget._xhr.common._stateCallback,
            closure: {
                endTopic: props.endTopic
            },
            xjson: {
                id: props.id,
                event: "state",
                execute: "none",
                props: props.props // Widget properties to update.
            }
        });
        return true;
    },

    /**
     * This function is used to process submit events with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} id The HTML element Id.
     * @config {String} endTopic The event topic to publish.
     * @config {String} execute The string containing a comma separated list 
     * of client ids against which the execute portion of the request 
     * processing lifecycle must be run.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _processSubmitEvent: function(props) {
        if (props == null) {
            return false;
        }

        // Generate AJAX request.
        @JS_NS@.widget._xhr.common._doRequest({
            id: props.id,
            callback: @JS_NS@.widget._xhr.common._submitCallback,
            closure: {
                endTopic: props.endTopic
            },
            xjson: {
                id: props.id,
                event: "submit",
                execute: (props.execute) ? props.execute : props.id
            }
        });
        return true;
    }, 
   
    /**
     * This function is used to refresh widgets.
     *
     * @param {String} id The HTML widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _refreshCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);

        // Add rows.
        var widget = @JS_NS@.widget.common.getWidget(id);
        widget.setProps(props);

        // Publish an event for custom AJAX implementations to listen for.
        if (closure.endTopic) {
            @JS_NS@._dojo.publish(closure.endTopic, [props]);
        }
        return true;
    },

    /**
     * This function is a callback to respond to the end of state request.
     * It will only publish submit end event without updating the widget itself.
     *
     * @param {String} id The HTML widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _stateCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);
            
        // Publish an event for custom AJAX implementations to listen for.
        if (closure.endTopic) {
            @JS_NS@._dojo.publish(closure.endTopic, [props]);
        }
        return true;
    },

    /**
     * This function is a callback to respond to the end of submit request.
     * It will only publish submit end event without updating the widget itself.
     *
     * @param {String} id The HTML widget Id.
     * @param {String} content The content returned by the AJAX response.
     * @param {Object} closure The closure argument provided to the Ajax transaction.
     * @param {Object} xjson The xjson header provided to the Ajax transaction.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _submitCallback: function(id, content, closure, xjson) {
        if (id == null || content == null) {
            return false;
        }

        // Parse JSON text.
        var props = @JS_NS@.json.parse(content);
            
        // Publish an event for custom AJAX implementations to listen for.
        if (closure.endTopic) {
            @JS_NS@._dojo.publish(closure.endTopic, [props]);
        }
        return true;
    }
};
