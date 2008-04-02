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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget._jsfx.dynaFaces");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@._base.config");
webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@._base.theme.common");
webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget._base.eventBase");

/**
 * @class This class contains functions to obtain JSF Extensions resources.
 * @static
 * @private
 */
webui.@THEME_JS@.widget._jsfx.dynaFaces = {
    /**
     * This function is used to initialize the environment with Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {Object} ajax Key-Value pairs of Ajax properties.     
     * @config {boolean} ajax.isJsfx Flag indicating to include JSF Extensions.
     * @config {boolean} webuiJsfx Flag indicating to include default Ajax functionality.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        if (props == null) {
            console.debug("Cannot initialize JSF Extensions."); // See Firebug console.
            return false;
        }

        // Don't load JSF Extensions.
        if (props.ajax && props.ajax.isJsfx != null) {
            if (new Boolean(props.ajax.isJsfx).valueOf() == false) {
                return false;
            }
        }
        
        // Load JSF Extensions immediately.
        if (new Boolean(props.webuiJsfx).valueOf() == true) {
            webui.@THEME_JS@.widget._jsfx.dynaFaces._loadJsfx();
        } else {
            // Override default publish functionality to lazily load JSFX.
            webui.@THEME_JS@.widget.common.publish = function(topic, props) {
                // Load JSF Extensions and publish event via a callback, 
                // ensuring all resources have been loaded.
                webui.@THEME_JS@.widget._jsfx.dynaFaces._loadJsfx(function() {
                    // Publish an event for custom AJAX implementations to listen for.
                    return webui.@THEME_JS@._base.dojo.publish(topic, props);
                });
            };
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
    _loadJsfx: function(callback) {
        if (typeof DynaFaces != "undefined") {
            return callback();
        }
        var bootstrap = webui.@THEME_JS@._base.bootstrap;
        var theme = webui.@THEME_JS@._base.theme.common;
        var isDebug = new Boolean(webui.@THEME_JS@._base.config.isDebug).valueOf();

        // Get script URLs.
        var pUrl = theme._getJavaScript((isDebug == true)
            ? "prototypeUncompressed" : "prototype");
        var jUrl = theme._getJavaScript((isDebug == true)
            ? "jsfxUncompressed" : "jsfx");

        // Ensure Prototype is loaded first using a callback.
        var jsfxCallback = function() {
            bootstrap._loadScript(jUrl, callback);
        };

        // Load Prototype and DynaFaces.
        //
        // Note: Prototype must be added to the global scope. Therefore,
        // dojo._loadPath() and/or dojo._loadUri() will not work here.
        if (typeof Prototype == "undefined") {
            bootstrap._loadScript(pUrl, jsfxCallback);
        } else {
            jsfxCallback();
        }
        return true;
    }
};

// Initialize the environment.
webui.@THEME_JS@.widget._jsfx.dynaFaces._init(webui.@THEME_JS@._base.config);
