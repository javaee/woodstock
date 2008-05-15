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

@JS_NS@._dojo.provide("@JS_NS@.xhr");

/**
 * @class This class contains functions for XMLHTTP requests.
 * @static
 */
@JS_NS@.xhr = {
    /**
     * This function is used to generate a request using the XMLHTTP 
     * request object (XHR) the underlying protocol.
     *
     * @param {String} method Use "GET" on operations that are primarily data
     * retrieval requests (default). Use "POST" on operations that send data to
     * the server.
     * @param {Object} props Key-Value pairs of properties.
     * @config {boolean} async Flag indicating request should asynchronous (default).
     * @config {String} content Optional postable string or DOM object data.
     * @config {Object} headers Key-Value pairs of request header properties.
     * @config {Function} onError The callback function called in an error case.
     * Note: XMLHttpRequest or ActiveXObject will be provided as an argument.
     * @config {Function} onReady The callback function called on a successful response.
     * Note: XMLHttpRequest or ActiveXObject will be provided as an argument.
     * @config {String} url The URL may be either a complete or relative URL.
     * @private
     */
    _doRequest: function(method, props) {
        if (props == null || props.url == null) {
            console.debug("Error: _send has null props"); // See Firebug console.
            return false;
        }
        var xhr = @JS_NS@.xhr._getXhr();
        if (xhr == null) {
            return false;
        }

        // Set callback functions.
        var _onError = props.onError;
        var _onReady = props.onReady;
        xhr.onreadystatechange = function() {
            // State shows "loaded".
            if (xhr.readyState == 4) {
                // Status shows "OK".
                if (xhr.status == 200) {
                    if (typeof _onReady == "function") {
                        _onReady(xhr);
                    }
                } else {
                    if (typeof _onError == "function") {
                        _onError(xhr);
                    }
                }
            }
        }
        // Open XHR.
        xhr.open((method) ? method : "GET", props.url,
            (props.async != null) ? props.async : true);

        // Set default headers -- must set after XHR is open.
        xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        xhr.setRequestHeader("X-Woodstock-Version", "@THEME_VERSION@");

        // Set headers.
        if (props.headers) {
            for (var property in props.headers) {
                xhr.setRequestHeader(property, props.headers[property]);
            }
        }
        // Send request.
        xhr.send((props.content) ? props.content : null);
        return true;
    },

    /**
     * This function is used to generate a "GET" request using the XMLHTTP 
     * request object (XHR) the underlying protocol.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {boolean} async Flag indicating request should asynchronous (default).
     * @config {String} content Optional postable string or DOM object data.
     * @config {Object} headers Key-Value pairs of request header properties.
     * @config {Function} onError The callback function called in an error case.
     * Note: XMLHttpRequest or ActiveXObject will be provided as an argument.
     * @config {Function} onReady The callback function called on a successful response.
     * Note: XMLHttpRequest or ActiveXObject will be provided as an argument.
     * @config {String} url The URL may be either a complete or relative URL.
     * @return {boolean} true if successful; otherwise, false.
     */
    get: function(props) {
        return @JS_NS@.xhr._doRequest("GET", props);
    },

    /**
     * Get either an XMLHttpRequest or ActiveXObject object.
     *
     * @private
     */
    _getXhr: function() {
        var xhr = null;
    
        // Use native XMLHttpRequest object, if possible.
        if (window.XMLHttpRequest && !(window.ActiveXObject)) {
            try {
                xhr = new XMLHttpRequest();
            } catch(e) {
                console.debug("Error: XMLHttpRequest not available"); // See Firebug console.
            }
        } else if (window.ActiveXObject) {
            // Use ActiveXObject for IE.
            try {
                xhr = new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e) {
        	try {
                    xhr = new ActiveXObject("Microsoft.XMLHTTP");
        	} catch(e) {
                    console.debug("Error: ActiveXObject not available"); // See Firebug console.
        	}
            }
        }
        return xhr;
    },

    /**
     * This function is used to generate a "POST" request using the XMLHTTP 
     * request object (XHR) the underlying protocol.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {boolean} async Flag indicating request should asynchronous (default).
     * @config {String} content Optional postable string or DOM object data.
     * @config {Object} headers Key-Value pairs of request header properties.
     * @config {Function} onError The callback function called in an error case.
     * Note: XMLHttpRequest or ActiveXObject will be provided as an argument.
     * @config {Function} onReady The callback function called on a successful response.
     * Note: XMLHttpRequest or ActiveXObject will be provided as an argument.
     * @config {String} url The URL may be either a complete or relative URL.
     * @return {boolean} true if successful; otherwise, false.
     */
    post: function(props) {
        return @JS_NS@.xhr._doRequest("POST", props);
    }
};
