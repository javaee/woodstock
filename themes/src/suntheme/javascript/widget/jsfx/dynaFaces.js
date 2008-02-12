// widget/jsfx/dynaFaces.js
//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

webui.@THEME@.dojo.provide("webui.@THEME@.widget.jsfx.dynaFaces");

webui.@THEME@.dojo.require("webui.@THEME@.browser");
webui.@THEME@.dojo.require("webui.@THEME@.theme.common");
webui.@THEME@.dojo.require("webui.@THEME@.widget.eventBase");

/**
 * @class This class contains functions to obtain JSF Extensions resources.
 * @static
 * @private
 */
webui.@THEME@.widget.jsfx.dynaFaces = {
    /**
     * This function is used to initialize the environment with Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config (Object) ajax Key-Value pairs of Ajax properties.     
     * @config (boolean) ajax.jsfx Flag indicating to include JSF Extensions.
     * @config (boolean) webuiJsfx Flag indicating to include default Ajax functionality.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    init: function(props) {
        if (props == null) {
            return false;
        }

        // Don't load JSF Extensions.
        if (props.ajax && props.ajax.jsfx != null) {
            if (new Boolean(props.ajax.jsfx).valueOf() == false) {
                return false;
            }
        }
        
        // Load JSF Extensions immediately.
        if (new Boolean(props.webuiJsfx).valueOf() == true) {
            webui.@THEME@.widget.jsfx.dynaFaces.loadJsfx();
        } else {
            // Override default publish functionality to lazily load JSFX.
            webui.@THEME@.widget.eventBase.prototype._publish = 
                function(topic, props) {
                    // Load JSF Extensions and publish event via a callback, 
                    // ensuring all resources have been loaded.
                    webui.@THEME@.widget.jsfx.dynaFaces.loadJsfx(function() {

                    // Publish an event for custom AJAX implementations to listen for.
                    webui.@THEME@.dojo.publish(topic, props);
                    return true;
                });
            }
        }
        return true;
    },

    /**
     * Load JSF Extensions.
     *
     * @param {Function} callback The JavaScript function to invoke on load.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    loadJsfx: function(callback) {
        if (typeof DynaFaces != "undefined") {
            return callback();
        }

        // Get script URLs.
        var common = webui.@THEME@.theme.common;
        var pUrl = common.getJavaScript((webui.@THEME@.config.isDebug == true)
            ? "prototypeUncompressed" : "prototype");
        var jUrl = common.getJavaScript((webui.@THEME@.config.isDebug == true)
            ? "jsfxUncompressed" : "jsfx");

        // Ensure Prototype is loaded first using a callback.
        var jsfxCallback = function() {
            webui.@THEME@.widget.jsfx.dynaFaces.loadScript(jUrl, callback);
        };

        // Load Prototype and DynaFaces.
        if (typeof Prototype == "undefined") {
            webui.@THEME@.widget.jsfx.dynaFaces.loadScript(pUrl, jsfxCallback);
        } else {
            jsfxCallback();
        }
        return true;
    },

    /**
     * Load JavaScript via script tag.
     *
     * Note: Prototype must be added to the global scope. Therefore,
     * dojo._loadPath() and/or dojo._loadUri() will not work here.
     *
     * @param {String} url The script url to load.
     * @param {Function} callback The JavaScript function to invoke on load.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    loadScript: function(url, callback) {
        if (url == null) {
            console.debug("Error: Cannot load " + url);
            return false;
        }

        // Get HTML head tag for use as parent node.
        var domNode = document.getElementsByTagName("head")[0];
        if (domNode == null) {
            console.debug("Error: No HTML head tag found.");
            return false;
        }

        // Create script tag.
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = url;
        if (callback) {
            if (webui.@THEME@.browser.isIe()) {
                script.onreadystatechange = function () {
                    // IE 7 won't return 'complete', but 'loaded' seems to work.
                    if (script.readyState == 'loaded' 
                            || script.readyState == 'complete') {
                        callback();
                    }
                }
            } else {
                script.onload = callback;
            }
        }
        domNode.appendChild(script);
        return true;
    }
}

// Initialize the environment.
webui.@THEME@.widget.jsfx.dynaFaces.init(webui.@THEME@.config);
