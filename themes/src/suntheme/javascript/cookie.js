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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.cookie");

/**
 * @class This class contains functions to manipulate cookies.
 * @static
 */
webui.@THEME_JS@.cookie = {
    /**
     * This function will get the cookie value.
     *
     * @return {String} The cookie value.
     */
    get: function() {
        // Get document cookie.
        var cookie = document.cookie;

        // Parse cookie value.
        var pos = cookie.indexOf(this.$cookieName + "=");
        if (pos == -1) {
            return null;
        }

        var start = pos + this.$cookieName.length + 1;
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
     */
    load: function() {
        // Get document cookie.
        var cookieVal = this.get();
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
     */
    reset: function() {
        // Clear cookie value.
        document.cookie = this.$cookieName + "=";
        return true;
    },

    /**
     * This function will store the cookie value.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    store: function() {
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
        var cookieString = this.$cookieName + "=" + cookieVal;
        if (this.$path != null) {
            cookieString += ";path=" + this.$path;
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
 */
webui.@THEME_JS@.scrollCookie = function(viewId, path) {    
    // All predefined properties of this object begin with '$' because
    // we don't want to store these values in the cookie.
    this.$cookieName = viewId;
    this.$path = path;

    // Default properties.
    this.left = "0";
    this.top  = "0";

    // This function will load the cookie and restore scroll position.
    this.restore = function() {
        // Load cookie value.
        this.load();
        scrollTo(this.left, this.top);
        return true;
    };

    // This function will set the cookie value.
    this.set = function() {
        var documentElement = window.document.documentElement;
        if (documentElement && documentElement.scrollTop) {
            this.left = documentElement.scrollLeft;
            this.top  = documentElement.scrollTop;
        } else {
            this.left = window.document.body.scrollLeft;
            this.top  = window.document.body.scrollTop;
        }
        // if the left and top scroll values are still null
        // try to extract it assuming the browser is IE
        if (this.left == null && this.top == null) {
            this.left = window.pageXOffset;
            this.top = window.pageYOffset;
        }
        // Store cookie value.
        this.store();
        return true;
    };
    return true;
};

// Inherit cookie properties.
webui.@THEME_JS@.scrollCookie.prototype = webui.@THEME_JS@.cookie;
