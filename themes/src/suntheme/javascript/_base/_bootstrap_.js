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
         * Get a parameter from the request.
         * 
         * @param {String} name The name of the parameter to retrieve.
         * @param {boolean} defaultValue The value to return if "name" is not defined.
         * @return {boolean} The parameter value.
         * @private
         */
        _getBooleanParameter: function(name, defaultValue) {
            var results = @JS_NS@._base.bootstrap._getParameter(name, defaultValue);
            if (typeof results == "string") {
                results = (results.toLowerCase() == "true") ? true : false;
            }
            return results;
        },

        /**
         * Get a parameter from the request.
         * 
         * @param {String} name The name of the parameter to retrieve.
         * @param {Object} defaultValue The value to return if "name" is not defined.
         * @return {Object} The parameter value.
         * @private
         */
        _getParameter: function(name, defaultValue) {
            var _defaultValue = (defaultValue != null) ? defaultValue : null;
            if (name == null) {
                return _defaultValue;
            }
            var _name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
            var regexS = "[\\?&]" + _name + "=([^&#]*)";
            var regex = new RegExp(regexS);
            var results = regex.exec(window.location.href);
            return (results != null) ? unescape(results[1]) : _defaultValue;
        },
        
        /**
         * This function is used to initialize the environment with Object
         * literals. In particular, register the @JS_NS@ name space.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {Object} ajax Key-Value pairs of Ajax config properties
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

            // Register Ajax module path.
            if (props.ajax && props.ajax.module && props.ajax.modulePath) {
                @JS_NS@._dojo.registerModulePath(props.ajax.module,
                    props.ajax.modulePath);
            }

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

            // For backward compatibility, map old name space used by VWP.
            if (typeof webui == "undefined") {
                webui = {};
            }
            if (typeof webui.@THEME@ == "undefined") {
                webui.@THEME@ = @JS_NS@;
            }
            return true;
        },

        /**
         * This function is used to initialize the environment with Object
         * literals. In particular, @JS_NS@ and dojo resource paths.
         *
         * @param {Object} props Key-Value pairs of properties.
         * @config {Object} _djConfig Dojo config properties.
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

            // Set Dojo debug mode.
            config._djConfig.isDebug = new Boolean(config.isDebug).valueOf();

            // Find script tag and set base url.            
            if (props._djConfig.baseUrl == null) {
                var scripts = document.getElementsByTagName("script");
                for (var i = 0; i < scripts.length; i++) {
                    var src = scripts[i].getAttribute("src");
                    if (!src) { 
                        continue;
                    }
                    var path = "@THEME_PATH@/javascript";
                    var index = src.indexOf(path);
                    if (index != -1) {
                        // Set base url.
                        config._djConfig.baseUrl = src.substring(0, index + path.length);

                        // Set debug path.
                        if (new Boolean(props.isDebug).valueOf() == true) {
                            config._djConfig.baseUrl += "_uncompressed";
                        }
                        break;
                    }
                }
            }

            // Set theme prefix.
            if (props.theme.prefix) {
                config.theme.prefix = props.theme.prefix;
            } else {
                index = config._djConfig.baseUrl.indexOf("@THEME_PATH@");
                if (index != -1) {
                    config.theme.prefix = config._djConfig.baseUrl.substring(0, index);
                }
            }

            // Adjust base url, if needed.
            var prefix = config.theme.prefix;
            if (config._djConfig.baseUrl.charAt(0) == "/" 
                    && config._djConfig.baseUrl.indexOf(prefix + "/") == -1) {
                config._djConfig.baseUrl = prefix + config._djConfig.baseUrl;
            }
            // Adjust module path, if needed.
            if (config.modulePath && config.modulePath.charAt(0) == "/" 
                    && config.modulePath.indexOf(prefix + "/") == -1) {
                config.modulePath = prefix + config.modulePath;
            }
            // Adjust ajax module path, if needed.
            if (config.ajax.modulePath && config.ajax.modulePath.charAt(0) == "/" 
                    && config.ajax.modulePath.indexOf(prefix + "/") == -1) {
                config.ajax.modulePath = prefix + config.ajax.modulePath;
            }
            // Adjust theme module path, if needed.
            if (config.theme.modulePath && config.theme.modulePath.charAt(0) == "/" 
                    && config.theme.modulePath.indexOf(prefix + "/") == -1) {
                config.theme.modulePath = prefix + config.theme.modulePath;
            }
            // Adjust custom theme module paths, if needed.
            if (config.theme.custom instanceof Array) {
                for (var i = 0; i < config.theme.custom.length; ++i) {
                    var modulePath = config.theme.custom[i].modulePath;
                    if (modulePath == null) {
                        continue;
                    }
                    if (modulePath.charAt(0) == "/"
                            && modulePath.indexOf(prefix + "/") == -1) {
                        config.theme.custom[i].modulePath = prefix + modulePath;
                    }
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
         * @config {boolean} webuiAjax Include Ajax functionality.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _initResources: function(props) {
            if (props == null) {
                console.debug("Cannot initialize resources."); // See Firebug console.
                return false;
            }
            var bootstrap = @JS_NS@._base.bootstrap;
// See issue #1299
//            var config = @JS_NS@._base.config;
            var theme = @JS_NS@.theme.common;
//            var isDebug = new Boolean(props.isDebug).valueOf();
//            var webuiAll = new Boolean(props.webuiAll).valueOf();
//            var webuiAjax = new Boolean(props.webuiAjax).valueOf();
//            var jsfxModule = theme.getProperty("javascript", "jsfxModule");
//            var xhrModule = theme.getProperty("javascript", "xhrModule");

            // Get webui file.
//            var file;            
//            if (webuiAjax && config.ajax.module == jsfxModule) {
//                file = (webuiAll) ? "webuiJsfxAll" : "webuiJsfx";
//            } else if (webuiAjax && config.ajax.module == xhrModule) {
//                file = (webuiAll) ? "webuiXhrAll" : "webuiXhr";
//            } else {
//                file = (webuiAll) ? "webuiAll" : "webui";
//            }

            // Load webui file.
//            bootstrap._writeScript(theme._getJavaScript((isDebug)
//                ? file + "Uncompressed" : file));

            // Load global scripts.
            var files = theme._getJavaScripts("Theme.javascript");
            if (files != null) {
                for (i = 0; i < files.length; i++) {
                    bootstrap._writeScript(files[i]);
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
         * Load JavaScript resource.
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
         * Write JavaScript resource.
         *
         * @param {String} url The script url to load.
         * @return {boolean} true if successful; otherwise, false.
         * @private
         */
        _writeScript: function(url) {
            if (url == null || url.length == 0) {
                console.debug("Error: Cannot write script: " + url);
                return false;
            }
            // Note: Safari evaluates dynamic script after evaluating the
            // document body, which creates timing issues. Therefore, the 
            // following statement is used instead of appending a DOM node.
            document.write('<script type="text/javascript" src="' + url + '"></script>' );
            return true;
        }
    };

@JS_NS@._dojo.require("@JS_NS@._base._config_"); // Replaced by build.

    // Initialize paths.
    @JS_NS@._base.bootstrap._initPaths(@JS_NS@._base.config);

@JS_NS@._dojo.require("@JS_NS@._dojo.dojo"); // Replaced by build.

    // Initialize modules.
    @JS_NS@._base.bootstrap._initModules(@JS_NS@._base.config);

@JS_NS@._dojo.require("@JS_NS@._base.browser"); // Replaced by build.
@JS_NS@._dojo.require("@JS_NS@.theme.common"); // Replaced by build.

    // Initialize javascript and style sheet resources.
    @JS_NS@._base.bootstrap._initResources(@JS_NS@._base.config);
}
