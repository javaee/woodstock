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

// Initialize the @JS_NS@ name space.
if (typeof @JS_NS@ == "undefined") {
    this.@JS_NS@ = {};
    this.@JS_NS@._base = {};

    /**
     * @class This class contains functions to initialize the environment.
     * @static
     * @private
     */
    @JS_NS@._base.bootstrap = {
        /**
         * This function is used to initialize the environment with Object
         * literals. In particular, register the @JS_NS@ name space.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {String} modulePath The @JS_NS@ module path.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _initModules: function(props) {
            if (props == null || props.modulePath == null) {
                console.debug("Cannot initialize @JS_NS@ module."); // See Firebug console.
                return false;
            }

            // Register module path.
            @JS_NS@._dojo.registerModulePath("@JS_NS@", props.modulePath);

            // Map custom name space to @JS_NS@.
            var config = @JS_NS@._base.config;
            if (config.namespace && config.namespace != "") {
                var ns = config.namespace.split(".");
                var module;

                // Ensure modules exist.
                for (var i = 0; i < ns.length - 1; i++) {
                    if (i == 0) {
                        module = ns[i];
                    } else {
                        module += "." + ns[i];
                    }
                    eval("if (typeof " + module + " == 'undefined') {" + 
                        module + "={};}");
                }
                eval(config.namespace + "=@JS_NS@");
            }
            return true;
        },

        /**
         * This function is used to initialize the environment with Object
         * literals. In particular, the widget parseOnLoad functionality.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {boolean} parseOnLoad Initialize widgets on load.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _initOnLoad: function(props) {
            if (props == null || props.parseOnLoad == null) {
                console.debug("Cannot initialize widget module."); // See Firebug console.
                return false;
            }

            // Defer widget creation until the window.onLoad event.
            @JS_NS@._dojo.addOnLoad(function() {
                if (new Boolean(props.parseOnLoad).valueOf() == true) {
                    @JS_NS@.widget.common._parseMarkup(
                        @JS_NS@._dojo.body());
                }
                // After the page has been parsed, there is no need to 
                // perform this task again. Setting the parseOnLoad flag
                // to false will allow the ajaxZone tag of JSF Extensions 
                // to re-render widgets properly. That is, considering 
                // there will only ever be one window.onLoad event.
                @JS_NS@._base.bootstrap._onWidgetReady(function() {
                    @JS_NS@._base.config.parseOnLoad = false;
                });
            });
            return true;
        },

        /**
         * This function is used to initialize the environment with Object
         * literals. In particular, @JS_NS@ and dojo resource paths.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {boolean} isDebug Flag indicating debug mode is enabled.
         * @config {String} modulePath The @JS_NS@ module path.
         * @config {Object} theme Key-Value pairs of theme properties.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _initPaths: function(props) {
            if (props == null) {
                // Cannot call console.debug before dojo has been loaded.
                return false;
            }
            var config = @JS_NS@._base.config;

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
                        // Set @JS_NS@ module path.
                        config.modulePath = src.substring(0, index + path.length);

                        // Set debug path.
                        if (new Boolean(props.isDebug).valueOf() == true) {
                            config.modulePath += "_uncompressed";
                        }
                        break;
                    }
                }
            }

            // Set Dojo debug mode.
            config._djConfig.isDebug = new Boolean(config.isDebug).valueOf();

            // Set Dojo base URL.
            if (config._djConfig.baseUrl == null) {
                config._djConfig.baseUrl = config.modulePath + "/_dojo";
            }

            // Set theme module path.
            if (props.theme.modulePath == null) {
                config.theme.modulePath = config.modulePath + "/theme";
            }

            // Set application context.
            if (props.theme.prefix == null) {
                index = props.modulePath.indexOf("@THEME_PATH@");
                if (index != -1) {
                    config.theme.prefix = config.modulePath.substring(0, index);
                }
            }
            return true;
        },

        /**
         * This function is used to initialize the environment with Object
         * literals. In particular, JavaScript and style sheet resources.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {boolean} isDebug Flag indicating debug mode is enabled.
         * @config {boolean} isStyleSheets Include style sheets.
         * @config {boolean} webuiAll Include all widgets.
         * @config {boolean} webuiJsfx Include Ajax functionality based on JSF Extensions.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _initResources: function(props) {
            if (props == null) {
                console.debug("Cannot initialize resources."); // See Firebug console.
                return false;
            }
            var bootstrap = @JS_NS@._base.bootstrap;
            var theme = @JS_NS@.theme.common;
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
                if (@JS_NS@._base.browser._isIe()) {
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
            var bootstrap = @JS_NS@._base.bootstrap;
            var browser = @JS_NS@._base.browser;
            var isDebug = new Boolean(props.isDebug).valueOf();
            var theme = @JS_NS@.theme.common;

            // Load master style sheet(s).
            var files = theme._getStyleSheets((isDebug) ? "masterUncompressed" : "master");
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
         * Note: Currently, this function is only useful with the parseOnLoad 
         * feature where we can count how many widgets have yet to be created.
         * </p>
         * @param {Function} func The function to execute after widgets are ready.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _onWidgetReady: function(func) {        
            var props = @JS_NS@.widget.common._props;
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
                    @JS_NS@._base.bootstrap._onWidgetReady(func);
                }, 100);
                return false;
            } else {
                // All widgets have been created.
                func();
            }
            return true;
        }
    };

@JS_NS@._dojo.require("@JS_NS@._base._config_"); // Replaced by build.

    // Initialize paths.
    @JS_NS@._base.bootstrap._initPaths(@JS_NS@._base.config);

@JS_NS@._dojo.require("@JS_NS@._dojo.dojo"); // Replaced by build.

    // Initialize modules.
    @JS_NS@._base.bootstrap._initModules(@JS_NS@._base.config);

@JS_NS@._dojo.require("@JS_NS@._base.body"); // Replaced by build.
@JS_NS@._dojo.require("@JS_NS@._base.browser"); // Replaced by build.
@JS_NS@._dojo.require("@JS_NS@.theme.common"); // Replaced by build.
@JS_NS@._dojo.require("@JS_NS@.widget.common"); // Replaced by build.

    // Initialize javascript and style sheet resources.
    @JS_NS@._base.bootstrap._initResources(@JS_NS@._base.config);

    // Initialize on load.
    @JS_NS@._base.bootstrap._initOnLoad(@JS_NS@._base.config);
}
