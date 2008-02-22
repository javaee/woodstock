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

webui.@THEME@.dojo.provide("webui.@THEME@.theme.common");

webui.@THEME@.dojo.require("webui.@THEME@.config");
webui.@THEME@.dojo.require("webui.@THEME@.dojo.i18n");
webui.@THEME@.dojo.require("webui.@THEME@.prototypejs");

/**
 * @class This class contains common functions to obtain theme properties.
 * It is also the base of the Dojo nls localized theme namespace.
 * <p>
 * The client theme is composed of the following theme categories.
 * </p>
 * <ul>
 * <li>messages</li>
 * <li>styles</li>
 * <li>images</li>
 * <li>templates</li>
 * </ul>
 * <p>
 * Each category has a set of properties. See the methods in
 * webui.@THEME@.theme.common for obtaining the theme property values.
 * </p><p>
 * webui.@THEME@.dojo.requireLocalization is reimplemented here in order to perform
 * hierarchical extension of the theme for application theme overrides.
 * </p>
 * @static
 */
webui.@THEME@.theme.common = {
    /**
     * This function is used to set widget properties with Object literals.
     *
     * @param props Key-Value pairs of properties.
     * @config {String} bundle The javascript theme basename "suntheme" for @THEME@.js
     * @config {String} locale The theme locale formatted as <lang>-<country>-<variant>
     * @config {String} modulePath A relative URL defining the root directory of the nls directory
     * @config {String} custom An array of basenames identifying an application's 
     * javascript theme files. The last segment of this "dot" separated 
     * string, is treated as the "bundle", and the initial segments are
     * treated as the module path.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        if (props == null || props.bundle == null || props.locale == null 
                || props.modulePath == null) {
            console.debug("Cannot initialize theme."); // See Firebug console.
            return false;
        }
        var module = "webui.@THEME@.theme";
        var theme = webui.@THEME@.theme.common;

        // Register module path.
	if (props.modulePath) {
	    webui.@THEME@.dojo.registerModulePath(module, props.modulePath);
	}

        // Load the javascript theme.
        //
        // Note: Default to "ROOT" for base locales so multiple requests are not
        // generated.
        theme.requireLocalization(module, props.bundle, 
            (props.locale == "en" || props.locale == "en-us") ? "ROOT" : props.locale);

        theme.baseTheme = webui.@THEME@.dojo.i18n.getLocalization(module,
            props.bundle, props.locale);

        if (props.custom instanceof Array) {
            for (var i = 0; i < props.custom.length; ++i) {
                theme.extendBaseTheme(props.custom[i]);
            }
        } else if (typeof(props.custom) == "string") {
            theme.extendBaseTheme(props.custom);
        }
        return true;
    },

    /**
     * Return the theme prefix, this is the application context
     * concatenated with the theme servlet context.
     *
     * @return {String} The theme prefix.
     */
    getPrefix: function() {
	return webui.@THEME@.config.theme.prefix;
    },

    /**
     * Returns a theme property "theme[category][key]" or null, never "".
     *
     * @param {String} category
     * @param {String} key
     * @return {String} The theme property.
     */
    getProperty: function(category, key) {
        try {
            var p = webui.@THEME@.theme.common.baseTheme[category][key];
            return p == null || p == "" ? null : p;
        } catch (e) {
            return null;
        }
    },

    /**
     * Returns the theme properties for a theme category or null.
     *
     * @param {String} category
     * @return {Object} Key-Value pairs of properties.
     */
    getProperties: function(category) {
        try {
            var p = webui.@THEME@.theme.common.baseTheme[category];
            return p == null || p == "" ? null : p;
        } catch (e) {
            return null;
        }
    },

    /**
     * Returns a formatted message if "params" is not null, else
     * the literal value of "getProperty("messages", prop) or
     * null if there is no value for key.
     *
     * @param {String} key
     * @param {Array} params
     * @return {String} A formatted message.
     */
    getMessage: function(key, params) {
	var msg = webui.@THEME@.theme.common.getProperty("messages", key);
	if (msg == null) {
	    return null;
	}
	if (params != null) {
            return msg.replace(/\$\{(\w+)\}/g, function(match, key){
                if (typeof(params[key]) != "undefined" && params[key] != null) {
                    return params[key];
                }
            });
	} else {
	    return msg;
	}
    },

    /**
     * @private
     * @return {Object} Key-Value pairs of properties.
     */
    _getImageProp: function(prop, isText) {
        var theme = webui.@THEME@.theme.common;
	var value = theme.getProperty("images", prop);
	if (value == null || value.length == 0) {
	    return null;
	}
	if (isText) {
	    var msg = theme.getMessage(value, null);
	    if (msg != null && msg.length != 0) {
		value = msg;
	    }
	}
	return value;
    },

    /**
     * Returns the following Object literals or null.
     * <ul>
     * <li>src - the image path with the theme prefix</li>
     * <li>width - image width</li>
     * <li>height - image height</li>
     * <li>title - value for the "title" attribute</li>
     * <li>alt - value for the "alt" attribute</li>
     * </ul>
     * If "alt" or "title" are message properties and not a literal value
     * the property is resolved to its message value.
     * This method should be called with the actual message property
     * and not one of its variants like "ALARM_CRITICAL_ALT". Use
     * "webui.@THEME@.theme.common.getProperty("images", "ALARM_CRITICAL_ALT")"
     * to get individual values if desired.
     * If the literal path is desired, without the prefix, use
     * "webui.@THEME@.theme.common.getProperty("images", imageprop)"
     * where imageprop is the actual image property like "ALARM_CRITICAL".
     *
     * @param {String} srcProperty the image theme key, the image key without any suffix.
     * @return {Object} Key-Value pairs of properties.
     */
    getImage: function(srcProperty) {
	if (srcProperty == null || srcProperty.length == 0) {
	    return null;
	}
	// Enforce srcProperty as the one without the extension
	//
	var imageSuffixes = [ "ALT", "TITLE", "WIDTH", "HEIGHT" ];
	var srcPropertyParts = srcProperty.split("_");
	if (srcPropertyParts.length > 1) {
	    for (var i = 0; i < imageSuffixes.length; ++i) {
		if (srcPropertyParts[srcPropertyParts.length - 1] ==
			imageSuffixes[i]) {
		    return null;
		}
	    }
	}
	     
	// If this key does not have a value the image is not defined
	// in the theme
	//
        var theme = webui.@THEME@.theme.common;
	var src = theme._getImageProp(srcProperty, false);
	if (src == null) {
	    return null;
	}
	var imageObj = {};
	imageObj["src"] = theme.getPrefix() + src;

	var value = theme._getImageProp(srcProperty + "_WIDTH", false);
	if (value != null) {
	    imageObj["width"] = value;
	}
	value = theme._getImageProp(srcProperty + "_HEIGHT", false);
	if (value != null) {
	    imageObj["height"] = value;
	}
        var value = theme._getImageProp(srcProperty + "_MAP", false);
        if (value != null) {
            imageObj["map_key"] = value;
            var value = theme._getImageProp(srcProperty + "_MAP_WIDTH", false);
            if (value != null) {
                imageObj["actual_width"] = value;
            }
            value = theme._getImageProp(srcProperty + "_MAP_HEIGHT", false);
            if (value != null) {
                imageObj["actual_height"] = value;
            }
            value = theme._getImageProp(srcProperty + "_MAP_TOP", false);
            if (value != null) {
                imageObj["top"] = value;
            }
        }
	value = theme._getImageProp(srcProperty + "_ALT", true);
	if (value != null) {
	    imageObj["alt"] = value;
	}
	value = theme._getImageProp(srcProperty + "_TITLE", true);
	if (value != null) {
	    imageObj["title"] = value;
	}
	return imageObj;
    },

    /**
     * This function is used to obtain a the literal "javascript"
     * theme value for "key"
     *
     * @param {String} key A key defining a theme "javascript" property.
     * @return {String} The javascript property.
     */
    getJavaScript: function(key) {
        var theme = webui.@THEME@.theme.common;
	var url = theme.getProperty("javascript", key);
	if (url == null || url.length == 0) {
	    return null;
	}
	return theme.getPrefix() + url;
    },

    /**
     * This function is used to obtain a the literal "javascript"
     * theme value for "key"
     *
     * @param {String} key A key defining a theme "javascript" property.
     * @return {String} An array of javascript properties.
     */
    getJavaScripts: function(key) {
        var theme = webui.@THEME@.theme.common;
	var url = theme.getProperty("javascript", key);
	if (url == null || url.length == 0) {
	    return null;
	}
        var files = url.split(" ");
        for (i = 0; i < files.length; i++) {
            files[i] = theme.getPrefix() + files[i];
        }
	return files;
    },

    /**
     * This function is used to obtain a the literal "stylesheets"
     * theme value for "key"
     *
     * @param {String} key A key defining a theme "stylesheets" property.
     * @return {String} An array of style sheet properties.
     */
    getStyleSheets: function(key) {
        var theme = webui.@THEME@.theme.common;
	var url = theme.getProperty("stylesheets", key);
	if (url == null || url.length == 0) {
	    return null;
	}
        var files = url.split(" ");
        for (i = 0; i < files.length; i++) {
            files[i] = theme.getPrefix() + files[i];
        }
	return files;
    },

    /**
     * Return the selector from the "styles" theme category for key
     * else null.
     *
     * @param {String} key A key defining a theme "styles" property.
     * @return {String} The selector property.
     */
    getClassName: function(key) {
	return webui.@THEME@.theme.common.getProperty("styles", key);
    },

    /**
     * This function is used to obtain a the literal "templates"
     * theme value for "key"
     *
     * @param {String} key A key defining a theme "templates" property.
     * @return {String} The template property.
     */
    getTemplate: function(key) {
        return webui.@THEME@.theme.common.getProperty("templates", key);
    },

    /**
     * Merge the baseTheme with an application's theme overrides
     * "themePackage" is a "dot" separated string. The "bundle"
     * is the last segment and the prefix segments are the module.
     * Return true if the base theme was extended else false.
     *
     * @param {String} themePackage
     * @return {boolean} true if successful; otherwise, false.
     */
    extendBaseTheme: function(themePackage) {
        if (themePackage == null) {
            return false;
        }
        var config = webui.@THEME@.config;
        var theme = webui.@THEME@.theme.common;
        var segments = themePackage.split(".");
        var bundle = segments[segments.length - 1];
        var module = segments.slice(0, segments.length - 1).join(".");

        try {
            // If there is no module, i.e. just a bundle segment
            // create a module name in the theme namespace.
            //
            if (module == null || module == "") {
                theme.custom = {};
                module = "webui.@THEME@.theme.common.custom";
            }
            var re = new RegExp("\\.", "g");
            var modulePath = module.replace(re, "/");
            webui.@THEME@.dojo.registerModulePath(module, modulePath);
            theme.requireLocalization(module, bundle, config.theme.locale);
        } catch(e) {
	    return false;
        }
        var newTheme = null;
        try {
            newTheme = webui.@THEME@.dojo.i18n.getLocalization(module, bundle, 
                config.theme.locale);
        } catch(e) {
	    return false;
        }
        // Not sure if we should do the "prototype" magic like
        // dojo, vs. just replacing the orginal baseTheme values.
        //
        if (newTheme != null) {
            webui.@THEME@.prototypejs.extend(theme.baseTheme, newTheme);
        }
        return true;
    },

    /**
     * Declares translated resources and loads them if necessary, in the same 
     * style as webui.@THEME@.dojo.require. Contents of the resource bundle are typically 
     * strings, but may be any name/value pair, represented in JSON format. 
     * See also webui.@THEME@.dojo.i18n.getLocalization.
     * <p>
     * Load translated resource bundles provided underneath the "nls" directory
     * within a package. Translated resources may be located in different
     * packages throughout the source tree.  For example, a particular widget
     * may define one or more resource bundles, structured in a program as
     * follows, where moduleName is mycode.mywidget and bundleNames available
     * include bundleone and bundletwo:
     * </p><p><pre>
     * ...
     * mycode/
     *  mywidget/
     *   nls/
     *    bundleone.js (the fallback translation, English in this example)
     *    bundletwo.js (also a fallback translation)
     *    de/
     *     bundleone.js
     *     bundletwo.js
     *    de-at/
     *     bundleone.js
     *    en/
     *     (empty; use the fallback translation)
     *    en-us/
     *     bundleone.js
     *    en-gb/
     *     bundleone.js
     *    es/
     *     bundleone.js
     *     bundletwo.js
     *   ...etc
     * ...
     * </pre></p><p>
     * Each directory is named for a locale as specified by RFC 3066, 
     * (http://www.ietf.org/rfc/rfc3066.txt), normalized in lowercase. Note that
     * the two bundles in the example do not define all the same variants. For a
     * given locale, bundles will be loaded for that locale and all more general
     * locales above it, including a fallback at the root directory. For 
     * example, a declaration for the "de-at" locale will first load 
     * nls/de-at/bundleone.js, then nls/de/bundleone.js and finally 
     * nls/bundleone.js. The data will be flattened into a single Object so that
     * lookups will follow this cascading pattern.  An optional build step can 
     * preload the bundles to avoid data redundancy and the multiple network 
     * hits normally required to load these resources.
     * </p><p>
     * NOTE: When loading these resources, the packaging does not match what is 
     * on disk. This is an implementation detail, as this is just a private 
     * data structure to hold the loaded resources. For example, 
     * tests/hello/nls/en-us/salutations.js is loaded as the object 
     * tests.hello.nls.salutations.en_us={...} The structure on disk is intended
     * to be most convenient for developers and translators, but in memory it is
     * more logical and efficient to store in a different order. Locales cannot 
     * use dashes, since the resulting path will not evaluate as valid JS, so we 
     * translate them to underscores.
     * </p>
     * @param {String} moduleName Name of the package containing the "nls" 
     * directory in which the bundle is found.
     * @param {String} bundleName The bundle name, i.e. the filename without the
     * '.js' suffix locale: the locale to load (optional). By default, the 
     * browser's user locale as defined by webui.@THEME@.dojo.locale
     * @param {String} locale The current locale.
     * @param {String} availableFlatLocales A comma-separated list of the 
     * available, flattened locales for this bundle.
     * @return {boolean} true if successful; otherwise, false.
     */
    requireLocalization: function(moduleName, bundleName, locale, 
            availableFlatLocales) {
        // Taken from webui.@THEME@.dojo.js in order to override the callback function that is 
        // passed to loadPath, in to perform hierarchical "extension" of properties.

        var targetLocale = webui.@THEME@.dojo.i18n.normalizeLocale(locale);
        var bundlePackage = [moduleName, "nls", bundleName].join(".");
        
        // Find the best-match locale to load if we have available flat locales.
        var bestLocale = "";
        if (availableFlatLocales) {
            var flatLocales = availableFlatLocales.split(",");
            for (var i = 0; i < flatLocales.length; i++) {
                // Locale must match from start of string.
                if (targetLocale.indexOf(flatLocales[i]) == 0) {
                    if (flatLocales[i].length > bestLocale.length) {
                        bestLocale = flatLocales[i];
                    }
                }
            }
            if (!bestLocale) {
                bestLocale = "ROOT";
            }               
        }

        // See if the desired locale is already loaded.
        var tempLocale = availableFlatLocales ? bestLocale : targetLocale;
        var bundle = webui.@THEME@.dojo._loadedModules[bundlePackage];
        var localizedBundle = null;
        if (bundle) {
            if (webui_@THEME@_config.djConfig.localizationComplete && bundle._built) {
                return false;    
            }
            var jsLoc = tempLocale.replace(/-/g, '_');
            var translationPackage = bundlePackage+"."+jsLoc;
            localizedBundle = webui.@THEME@.dojo._loadedModules[translationPackage];
        }

        if (!localizedBundle) {
            bundle = webui.@THEME@.dojo["provide"](bundlePackage);
            var syms = webui.@THEME@.dojo._getModuleSymbols(moduleName);
            var modpath = syms.concat("nls").join("/");
            var parent;

            webui.@THEME@.dojo.i18n._searchLocalePath(tempLocale, availableFlatLocales, function(loc) {
                var jsLoc = loc.replace(/-/g, '_');
                var translationPackage = bundlePackage + "." + jsLoc;
                var loaded = false;
                if (!webui.@THEME@.dojo._loadedModules[translationPackage]) {
                    // Mark loaded whether it's found or not, 
                    // so that further load attempts will not 
                    // be made
                    webui.@THEME@.dojo["provide"](translationPackage);
                    var module = [modpath];
                    if (loc != "ROOT") {
                        module.push(loc);    
                    }
                    module.push(bundleName);
                    var filespec = module.join("/") + '.js';
                    loaded = webui.@THEME@.dojo._loadPath(filespec, null, function(hash) {
                        // Use singleton with prototype to point to parent
                        // bundle, then mix-in result from loadPath
                        var clazz = function() {};
                        clazz.prototype = parent;
                        bundle[jsLoc] = new clazz();
                        // Use "hierarchical" extend.
                        // for (var j in hash){ bundle[jsLoc][j] = hash[j]; }
                        webui.@THEME@.prototypejs.extend(bundle[jsLoc], hash);
                    });
                } else {
                    loaded = true;
                }
                if (loaded && bundle[jsLoc]) {
                    parent = bundle[jsLoc];
                } else {
                    bundle[jsLoc] = parent;
                }
                if (availableFlatLocales) {
                    // Stop the locale path searching if we 
                    // know the availableFlatLocales, since
                    // the first call to this function will 
                    // load the only bundle that is needed.
                    return true;
                }
            });
        }

        // Save the best locale bundle as the target locale bundle 
        // when we know the
        // the available bundles.
        //
        if (availableFlatLocales && targetLocale != bestLocale) {
            bundle[targetLocale.replace(/-/g, '_')] = 
                bundle[bestLocale.replace(/-/g, '_')];
        }
        return true;
    }
}

// Initialize the theme.
webui.@THEME@.theme.common._init(webui.@THEME@.config.theme);
