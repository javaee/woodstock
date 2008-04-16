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

@JS_NS@._dojo.provide("@JS_NS@._base.cookie");

/**
 * @class This class contains functions to manipulate cookies.
 * @static
 * @private
 */
@JS_NS@._base.cookie = {
    /**
     * This function will get the cookie value.
     *
     * @return {String} The cookie value.
     * @private
     */
    _get: function() {
        // Get document cookie.
        var cookie = document.cookie;

        // Parse cookie value.
        var pos = cookie.indexOf(this._cookieName + "=");
        if (pos == -1) {
            return null;
        }

        var start = pos + this._cookieName.length + 1;
        var end = cookie.indexOf(";", start);
        if (end == -1) {
            end = cookie.length;
        }

        // return cookie value
        return cookie.substring(start, end);
    },

    /**
     * This function will load the cookie value.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _load: function() {
        // Get document cookie.
        var cookieVal = this._get();
        if (cookieVal == null) {
            return false;
        }

        // Break cookie into names and values.
        var a = cookieVal.split('&');

        // Break each pair into an array.
        for (var i = 0; i < a.length; i++) {
            a[i] = a[i].split(':');
        }

        // Set name and values for this object.
        for (i = 0; i < a.length; i++) {
            this[a[i][0]] = unescape(a[i][1]);
        }
        return true;
    },

    /**
     * This function will reset the cookie value.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _reset: function() {
        // Clear cookie value.
        document.cookie = this._cookieName + "=";
        return true;
    },

    /**
     * This function will store the cookie value.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _store: function() {
        // Create cookie value by looping through object properties
        var cookieVal = "";

        // Since cookies use the equals and semicolon signs as separators,
        // we'll use colons and ampersands for each variable we store.
        for (var prop in this) {
            // Ignore properties that begin with '$' and methods.
            if (prop.charAt(0) == '$' || typeof this[prop] == 'function') {
                continue;
            }
            if (cookieVal != "") {
                cookieVal += '&';
            }
            cookieVal += prop + ':' + escape(this[prop]);
        }
        var cookieString = this._cookieName + "=" + cookieVal;
        if (this._path != null) {
            cookieString += ";path=" + this._path;
        }
        // Store cookie value.
        document.cookie = cookieString;
        return true;
    }
};

/**
 * @class This class contains functionality for scrolling.
 * @constructor This function is used to construct a javascript object for
 * maintaining scroll position via cookie.
 * @param {String} viewId
 * @param {String} path
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@._base.scrollCookie = function(viewId, path) {    
    // All predefined properties of this object begin with '$' because
    // we don't want to store these values in the cookie.
    this._cookieName = viewId;
    this._path = path;

    // Default properties.
    this._left = "0";
    this._top  = "0";

    /**
     * This function will load the cookie and restore scroll position.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._restore = function() {
        // Load cookie value.
        this._load();
        scrollTo(this._left, this._top);
        return true;
    };

    /**
     * This function will set the cookie value.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._set = function() {
        var documentElement = window.document.documentElement;
        if (documentElement && documentElement.scrollTop) {
            this._left = documentElement.scrollLeft;
            this._top  = documentElement.scrollTop;
        } else {
            this._left = window.document.body.scrollLeft;
            this._top  = window.document.body.scrollTop;
        }
        // if the left and top scroll values are still null
        // try to extract it assuming the browser is IE.
        if (this._left == null && this._top == null) {
            this._left = window.pageXOffset;
            this._top = window.pageYOffset;
        }
        // Store cookie value.
        this._store();
        return true;
    };
    return true;
};

// Inherit cookie properties.
@JS_NS@._base.scrollCookie.prototype = @JS_NS@._base.cookie;
