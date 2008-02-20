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

// Ensure the webui name space exits.
if (typeof webui == "undefined") {
    this.webui = {};
}

// Ensure the webui.@THEME@ name space exits.
if (typeof webui.@THEME@ == "undefined") {
    this.webui.@THEME@ = {};
}

// Ensure the webui_@THEME@_config property exists.
if (typeof webui_@THEME@_config == "undefined") {
    this.webui_@THEME@_config = {};
}

/**
 * @class This class contains functions to initialize the environment.
 * @static
 */
webui.@THEME@.bootstrap = {
    /**
     * This function is used to initialize the environment with Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {boolean} debug Flag indicating debug mode is enabled.
     * @config {boolean} styleSheet Include style sheets.
     * @config {boolean} webuiAll Include all webui functionality.
     * @config {boolean} webuiJsfx Include all Ajax functionality based on JSF Extensions.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initFiles: function(props) {
        if (props == null) {
            console.debug("Cannot initialize files."); // See Firebug console.
            return false;
        }
        var bootstrap = webui.@THEME@.bootstrap;
        var theme = webui.@THEME@.theme.common;
        var debug = new Boolean(props.debug).valueOf();
        var webuiAll = new Boolean(props.webuiAll).valueOf();
        var webuiJsfx = new Boolean(props.webuiJsfx).valueOf();

        // Get webui file.
        var file;
        if (webuiJsfx) {
            file = (webuiAll) ? "webuiJsfxAll" : "webuiJsfx";
        } else {
            file = (webuiAll) ? "webuiAll" : "webui";
        }
        if (debug) {
            file += "Uncompressed";
        }

        // Load webui file.
        bootstrap.loadScript(theme.getJavaScript(file));

        // Load style sheets.
        if (new Boolean(props.styleSheet).valueOf() == true) {
            bootstrap._loadStyleSheets();
        }

        // Load global scripts.
        webui.@THEME@.bootstrap._loadGlobalScripts();     
        return true;
    },

    /**
     * This function is used to initialize the environment with Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {Object} djConfig Dojo config properties.
     * @config {boolean} debug Flag indicating debug mode is enabled.
     * @config {String} modulePath The webui module path.
     * @config {Object} theme Key-Value pairs of theme properties.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initPaths: function(props) {
        if (props == null) {
            // Cannot call console.debug before dojo has been loaded.
            return false;
        }

        // Find script tag and set default module path.
        if (props.modulePath == null) {
            var scripts = document.getElementsByTagName("script");
            for (var i = 0; i < scripts.length; i++) {
                var src = scripts[i].getAttribute("src");
                if (!src) { 
                    continue;
                }
                var path = "@THEME_PATH@/javascript";
                var index = src.indexOf(path);
                if (index != -1) {
                    // Set webui module path.
                    props.modulePath = src.substring(0, index + path.length);

                    // Set debug path.
                    if (new Boolean(props.debug).valueOf() == true) {
                        props.modulePath += "_uncompressed"
                    }
                    break;
                }
            }
        }

        // Set Dojo base URL.
        if (props.djConfig == null) { props.djConfig = {}; }
        if (props.djConfig.baseUrl == null) {
            props.djConfig.baseUrl = props.modulePath + "/dojo";
        }

        // Set Dojo debug mode.
        if (props.djConfig.isDebug == null) {
            props.djConfig.isDebug = new Boolean(props.debug).valueOf();
        }

        // Set theme module path.
        if (props.theme == null) { props.theme = {}; }
        if (props.theme.modulePath == null) {
            props.theme.modulePath = props.modulePath + "/theme";
        }

        // Set application context.
        if (props.theme.prefix == null) {
            index = props.modulePath.indexOf("@THEME_PATH@");
            if (index != -1) {
                props.theme.prefix = props.modulePath.substring(0, index);
            }
        }
        return true;
    },

    /**
     * Load global scripts.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _loadGlobalScripts: function() {
        var bootstrap = webui.@THEME@.bootstrap;
        var theme = webui.@THEME@.theme.common;

        // Get the global list of keys for the style sheet files that should be
        // included on every page (separated by spaces).
        var global = theme.getProperty("javascript", "global");
        if (global == null || global == "") {
            return false;
        }
        // Get files array.
        var files = global.split(" ");
        if (files == null) {
            return false;
        }
        // Load global links.
        for (i = 0; i < files.length; i++) {
            bootstrap.loadScript(theme.getJavaScript(files[i]));
        }
        return true;
    },

    /**
     * Load style sheets.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _loadStyleSheets: function() {
        var bootstrap = webui.@THEME@.bootstrap;
        var browser = webui.@THEME@.browser;
        var theme = webui.@THEME@.theme.common;

        // Load master style sheet.
        bootstrap.loadLink(theme.getStyleSheet("master"));

        // Load browser specific style sheets.
        var file = theme.getStyleSheet("default");
        if (browser.isGecko()) {
            file = theme.getStyleSheet("gecko");
        } else if (browser.isIe7()) {
            file = theme.getStyleSheet("ie7");
        } else if (browser.isIe6()) {
            file = theme.getStyleSheet("ie6");
        } else if (browser.isSafari()) {
            file = theme.getStyleSheet("safari");
        }
        if (file != null && file != "") {
            bootstrap.loadLink(theme.getStyleSheet(file));
        }

        // Get the global list of keys for the style sheet files that should be
        // included on every page (separated by spaces).
        var global = theme.getProperty("stylesheets", "global");
        if (global == null || global == "") {
            return false;
        }
        // Get files array.
        var files = global.split(" ");
        if (files == null) {
            return false;
        }
        // Load global links.
        for (i = 0; i < files.length; i++) {
            bootstrap.loadLink(theme.getStyleSheet(files[i]));
        }
        return true;
    },

    /**
     * This function is used to initialize the environment with Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} modulePath The webui module path.
     * @config {boolean} webuiOnLoad Initialize webui functionality on load.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _initWebui: function(props) {
        if (props == null || props.modulePath == null) {
            console.debug("Cannot initialize webui."); // See Firebug console.
            return false;
        }

        // Register module path.
        webui.@THEME@.dojo.registerModulePath("webui.@THEME@", props.modulePath);

        // Dojo inserts a div into body for HTML template rendering; therefore, 
        // we must wait until the window.onLoad event before creating widgets. 
        // Otherwise, IE will throw a security exception.
        if (new Boolean(props.webuiOnLoad).valueOf() == true) {
            webui.@THEME@.dojo.addOnLoad(function() {
                webui.@THEME@.widget.common._replaceElements(webui.@THEME@.dojo.body()); 
            });
        }
        return true;
    },

    /**
     * Load CSS via link tag.
     *
     * @param {String} url The link url to load.
     * @return {boolean} true if successful; otherwise, false.
     */
    loadLink: function(url) {
        if (url == null || url == "") {
            console.debug("Error: Cannot load link: " + url);
            return false;
        }

        // Get HTML head tag for use as parent node.
        var domNode = document.getElementsByTagName("head")[0];
        if (domNode == null) {
            console.debug("Error: No HTML head tag found.");
            return false;
        }

        // Create link tag.
        var link = document.createElement("link");
        link.type = "text/css";
        link.rel="stylesheet";
        link.href = url;

        domNode.appendChild(link);
        return true;
    },

    /**
     * Load JavaScript via script tag.
     *
     * @param {String} url The script url to load.
     * @param {Function} callback The JavaScript function to invoke on load.
     * @return {boolean} true if successful; otherwise, false.
     */
    loadScript: function(url, callback) {
        if (url == null || url == "") {
            console.debug("Error: Cannot load script: " + url);
            return false;
        }

        // Get HTML head tag for use as parent node.
        var domNode = document.getElementsByTagName("head")[0];
        if (domNode == null) {
            console.debug("Error: No HTML head tag found.");
            return false;
        }

        // Create script tag.
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.src = url;

        // Set callback.
        if (callback) {
            if (webui.@THEME@.browser.isIe()) {
                script.onreadystatechange = function () {
                    // IE 7 won't return 'complete', but 'loaded' seems to work.
                    if (script.readyState == "loaded"
                            || script.readyState == "complete") {
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

// Initialize paths.
webui.@THEME@.bootstrap._initPaths(webui_@THEME@_config);

webui.@THEME@.dojo.require("webui.@THEME@.dojo.dojo"); // Replaced by build.

// Initialize webui.
webui.@THEME@.bootstrap._initWebui(webui_@THEME@_config);

webui.@THEME@.dojo.require("webui.@THEME@.browser"); // Replaced by build.
webui.@THEME@.dojo.require("webui.@THEME@.config"); // Replaced by build.
webui.@THEME@.dojo.require("webui.@THEME@.theme.common"); // Replaced by build.
webui.@THEME@.dojo.require("webui.@THEME@.widget.common"); // Replaced by build.

// Initialize files.
webui.@THEME@.bootstrap._initFiles(webui.@THEME@.config);
