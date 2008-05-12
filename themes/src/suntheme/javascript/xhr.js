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

@JS_NS@._dojo.require("@JS_NS@._base.proto");

/**
 * @class This class contains functions for XMLHTTP requests.
 * @static
 */
@JS_NS@.xhr = {
    /**
     * This function is used to generate a "GET" request using the XMLHTTP 
     * request object (XHR) the underlying protocol.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {Object} content Key-Value pairs of sring properties to be 
     * serialized as name1=value2 and passed in the request.
     * @config {Function} error The callback function called in an error case. 
     * @config {Node} form DOM node for a form. Used to extract the form values 
     * and send to the server.
     * @config {Object} headers Key-Value pairs of header properties.
     * @config {Function} load The callback function called on a successful response.
     * @config timeout Milliseconds to wait for the response (defaults to 0, 
     * which means "wait forever").
     * @config url The URL to server end point.
     * @return {boolean} true if successful; otherwise, false.
     */
    get: function(props) {
        if (props == null) {
            return false;
        }
        @JS_NS@._base.proto._extend(props.headers, {
            "X-Requested-With": "XMLHttpRequest",
            "X-Woodstock-Version": "@THEME_VERSION@"
        });
        @JS_NS@._dojo.xhrGet(props);
        return true;
    },

    /**
     * This function is used to generate a "POST" request using the XMLHTTP 
     * request object (XHR) the underlying protocol.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {Object} content Key-Value pairs of sring properties to be
     * serialized as name1=value2 and passed in the request.
     * @config {Function} error The callback function called in an error case. 
     * @config {Node} form DOM node for a form. Used to extract the form values 
     * and send to the server.
     * @config {Object} headers Key-Value pairs of header properties.
     * @config {Function} load The callback function called on a successful response.
     * @config timeout Milliseconds to wait for the response (defaults to 0, 
     * which means "wait forever").
     * @config url The URL to server end point.
     * @return {boolean} true if successful; otherwise, false.
     */
    post: function(props) {
        if (props == null) {
            return false;
        }
        @JS_NS@._base.proto._extend(props.headers, {
            "X-Requested-With": "XMLHttpRequest",
            "X-Woodstock-Version": "@THEME_VERSION@"
        });
        @JS_NS@._dojo.xhrPost(props);
        return true;
    }
};
