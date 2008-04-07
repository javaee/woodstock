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

// Initialize the webui name space.
if (typeof webui == "undefined") {
    this.webui = {};
}

// Initialize the webui.@THEME_JS@ name space.
if (typeof webui.@THEME_JS@ == "undefined") {
    this.webui.@THEME_JS@ = {};
    this.webui.@THEME_JS@._base = {};

    // Initialize the webui.@THEME_JS@ config variable. If this is not defined, 
    // webui_@THEME@ will be used for backward compatibility without the 
    // version number.
    if (typeof webui_@THEME_JS@ == "undefined") {
        this.webui_@THEME_JS@ = (typeof webui_@THEME@ != "undefined")
          ? webui_@THEME@ : {};
    }

    /**
     * @class This class contains functions to initialize the environment.
     * @static
     * @private
     */
    webui.@THEME_JS@._base.bootstrap = {
        /**
         * This function is used to initialize the environment with Object literals.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {boolean} isDebug Flag indicating debug mode is enabled.
         * @config {boolean} isStyleSheets Include style sheets.
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
            var bootstrap = webui.@THEME_JS@._base.bootstrap;
            var theme = webui.@THEME_JS@._theme.common;
            var isDebug = new Boolean(props.isDebug).valueOf();
            var webuiAll = new Boolean(props.webuiAll).valueOf();
            var webuiJsfx = new Boolean(props.webuiJsfx).valueOf();

            // Get webui file.
            var file;
            if (webuiJsfx) {
                file = (webuiAll) ? "webuiJsfxAll" : "webuiJsfx";
            } else {
                file = (webuiAll) ? "webuiAll" : "webui";
            }

            // Load webui file.
            bootstrap._loadScript(theme._getJavaScript((isDebug)
                ? file + "Uncompressed" : file));

            // Load global scripts.
            var files = theme._getJavaScripts("Theme.javascript");
            if (files != null) {
                for (i = 0; i < files.length; i++) {
                    bootstrap._loadScript(files[i]);
                }
            }

            // Load style sheets.
            //
            // Note: There appears to be a small performance gain loading after
            // the JavaScript. It may be due to asyncronous loading?
            if (new Boolean(props.isStyleSheet).valueOf() == true) {
                bootstrap._loadStyleSheets(props);
            }
            return true;
        },

        /**
         * This function is used to initialize the environment with Object
         * literals.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {Object} _djConfig Dojo config properties.
         * @config {boolean} isDebug Flag indicating debug mode is enabled.
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
                        if (new Boolean(props.isDebug).valueOf() == true) {
                            props.modulePath += "_uncompressed";
                        }
                        break;
                    }
                }
            }

            // Set Dojo base URL.
            if (props._djConfig == null) { props._djConfig = {}; }
            if (props._djConfig.baseUrl == null) {
                props._djConfig.baseUrl = props.modulePath + "/_dojo";
            }

            // Set Dojo debug mode.
            if (props._djConfig.isDebug == null) {
                props._djConfig.isDebug = new Boolean(props.isDebug).valueOf();
            }

            // Set theme module path.
            if (props.theme == null) { props.theme = {}; }
            if (props.theme.modulePath == null) {
                props.theme.modulePath = props.modulePath + "/_theme";
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
         * This function is used to initialize the environment with Object literals.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {String} modulePath The webui module path.
         * @config {boolean} parseOnLoad Initialize webui functionality on load.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _initWebui: function(props) {
            if (props == null || props.modulePath == null) {
                console.debug("Cannot initialize webui."); // See Firebug console.
                return false;
            }

            // Register module path.
            webui.@THEME_JS@._dojo.registerModulePath("webui.@THEME_JS@", props.modulePath);

            // Dojo inserts a div into body for HTML template rendering; therefore, 
            // we must wait until the window.onLoad event before creating widgets. 
            // Otherwise, IE will throw a security exception.
            if (new Boolean(props.parseOnLoad).valueOf() == true) {
                webui.@THEME_JS@._dojo.addOnLoad(function() {
                    webui.@THEME_JS@.widget.common._parseMarkup(webui.@THEME_JS@._dojo.body());
                    webui.@THEME_JS@._base.bootstrap._onWidgetReady(function() {
                        // After the page has been parsed, there is no need to 
                        // perform this task again. Setting the parseOnLoad flag
                        // to false will allow the ajaxZone tag of JSF Extensions 
                        // to re-render widgets properly. That is, considering 
                        // there will only ever be one window.onLoad event.
                        webui.@THEME_JS@._base.config.parseOnLoad = false;
                    });
                });
            }
            return true;
        },

        /**
         * Load CSS via link tag.
         *
         * @param {String} url The link url to load.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _loadLink: function(url) {
            if (url == null || url.length == 0) {
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
            link.rel ="stylesheet";
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
         * @private
         */
        _loadScript: function(url, callback) {
            if (url == null || url.length == 0) {
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
                if (webui.@THEME_JS@._base.browser._isIe()) {
                    script.onreadystatechange = function () {
                        // IE 7 won't return 'complete', but 'loaded' works.
                        if (script.readyState == "loaded"
                                || script.readyState == "complete") {
                            callback();
                        }
                    };
                } else {
                    script.onload = callback;
                }
            }
            domNode.appendChild(script);
            return true;
        },

        /**
         * Load style sheets.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {boolean} isDebug Flag indicating debug mode is enabled.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _loadStyleSheets: function(props) {
            if (props == null) {
                console.debug("Cannot initialize stylesheets."); // See Firebug console.
                return false;
            }
            var bootstrap = webui.@THEME_JS@._base.bootstrap;
            var browser = webui.@THEME_JS@._base.browser;
            var isDebug = new Boolean(props.isDebug).valueOf();
            var theme = webui.@THEME_JS@._theme.common;

            // Load master style sheet(s).
            files = theme._getStyleSheets((isDebug) ? "masterUncompressed" : "master");
            if (files != null) {
                for (i = 0; i < files.length; i++) {
                    bootstrap._loadLink(files[i]);
                }
            }
            // Load global style sheets.
            files = theme._getStyleSheets("Theme.stylesheet");
            if (files != null) {
                for (i = 0; i < files.length; i++) {
                    bootstrap._loadLink(files[i]);
                }
            }
            // Load browser specific style sheet(s).
            if (browser._isIe7()) {
                files = theme._getStyleSheets((isDebug) ? "ie7Uncompressed" : "ie7");
            } else if (browser._isIe6()) {
                files = theme._getStyleSheets((isDebug) ? "ie6Uncompressed" : "ie6");
            } else if (browser._isSafari()) {
                files = theme._getStyleSheets((isDebug) ? "safariUncompressed" : "safari");
            } else if (browser._isGecko()) {
                files = theme._getStyleSheets((isDebug) ? "geckoUncompressed" : "gecko");
            } else {
                files = theme._getStyleSheets((isDebug) ? "defaultUncompressed" : "default");
            }
            if (files != null) {
                for (i = 0; i < files.length; i++) {
                    bootstrap._loadLink(files[i]);
                }
            }
            return true;
        },

        /**
         * This function is used to determine when all widgets have been created.
         * <p>
         * Note: Currently, this function is only useful when the parseOnLoad 
         * feature is used.
         * </p>
         * @param {Function} func The function to execute after widgets are ready.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _onWidgetReady: function(func) {        
            var props = webui.@THEME_JS@.widget.common._props;
            var count = 0;

            // Count stored widget properties. Object literals are deleted as 
            // widgets are created.
            for (var property in props) {
                count++;
                break; // At least one widget has not been created.
            }
            if (count > 0) {
                // Wait for widgets to complete.
                setTimeout(function() {
                    webui.@THEME_JS@._base.bootstrap._onWidgetReady(func);
                }, 100);
                return false;
            } else {
                // All widgets have been created.
                func();
            }
            return true;
        }
    };

    // Initialize paths.
    webui.@THEME_JS@._base.bootstrap._initPaths(webui_@THEME_JS@);

// Note: The following require statements must be located at the beginning of 
// each line in order to be replaced by the build.
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@._dojo.dojo"); // Replaced by build.

    // Initialize webui.
    webui.@THEME_JS@._base.bootstrap._initWebui(webui_@THEME_JS@);

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@._base.browser"); // Replaced by build.
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@._base.config"); // Replaced by build.
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@._theme.common"); // Replaced by build.
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common"); // Replaced by build.

    // Initialize files.
    webui.@THEME_JS@._base.bootstrap._initFiles(webui.@THEME_JS@._base.config);

    // Map custom name space to webui.@THEME_JS@.
    if (webui.@THEME_JS@._base.config.namespace) {
        eval("this." + webui.@THEME_JS@._base.config.namespace + "=webui.@THEME_JS@");
    }
}
