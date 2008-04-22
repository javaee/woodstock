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

@JS_NS@._dojo.provide("@JS_NS@.theme.common");

@JS_NS@._dojo.require("@JS_NS@._base.proto");
@JS_NS@._dojo.require("@JS_NS@._dojo.i18n");

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
 * @JS_NS@.theme.common for obtaining the theme property values.
 * </p>
 * @static
 */
@JS_NS@.theme.common = {
    /**
     * Object containing base theme properties.
     * @private
     */
    _baseTheme: {},

    /**
     * This function is used to set widget properties with Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} bundle The javascript theme basename "suntheme" for @THEME_JS@.js
     * @config {String} locale The theme locale formatted as <lang>-<country>-<variant>
     * @config {String} modulePath A relative URL defining the root directory of
     * the nls directory.
     * @config {Object} custom Key-Value pairs of custom theme properties.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        if (props == null || props.bundle == null || props.locale == null 
                || props.modulePath == null) {
            console.debug("Cannot initialize theme."); // See Firebug console.
            return false;
        }
        var module = "@JS_NS@.theme";
        var theme = @JS_NS@.theme.common;

        // Register module path.
	if (props.modulePath) {
	    @JS_NS@._dojo.registerModulePath(module, props.modulePath);
	}

        // Load the javascript theme.
        theme._requireLocalization(module, props.bundle, props.locale);

        theme._baseTheme = @JS_NS@._dojo.i18n.getLocalization(module,
            props.bundle, props.locale);

        if (props.custom instanceof Array) {
            for (var i = 0; i < props.custom.length; ++i) {
                theme._extendBaseTheme(props.custom[i]);
            }
        }
        return true;
    },

    /**
     * Merge the _baseTheme with an application's theme overrides.
     * 
     * @config {Object} props Key-Value pairs of properties.
     * @config {String} bundle The javascript theme basename.
     * @config {String} modulePath A relative URL defining the root directory of
     * the nls directory.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _extendBaseTheme: function(props) {
        if (props == null || props.bundle == null) {
            console.debug("Cannot extend theme."); // See Firebug console.
            return false;
        }
        var config = @JS_NS@._base.config;
        var theme = @JS_NS@.theme.common;
        var module = "@JS_NS@.theme.common.custom";
        var modulePath = (props.modulePath != null && props.modulePath != "")
            ? props.modulePath : theme.getPrefix();

        // Initialize the @JS_NS@.theme.common.custom name space.
        theme.custom = {};

        // Get custom resource.
        @JS_NS@._dojo.registerModulePath(module, modulePath);
        theme._requireLocalization(module, props.bundle, config.theme.locale);

        try {
            var newTheme = null;
            newTheme = @JS_NS@._dojo.i18n.getLocalization(module, props.bundle, 
                config.theme.locale);

            // Not sure if we should do the "prototype" magic like
            // dojo, vs. just replacing the orginal _baseTheme values.
            //
            if (newTheme != null) {
                @JS_NS@._base.proto._extend(theme._baseTheme, newTheme);
            }
        } catch(e) {
	    return false;
        }
        return true;
    },

    /**
     * Return the selector from the "styles" theme category for key
     * else null.
     * <p>
     * Note: If the given key doesn't exist in the theme, the method returns the
     * defaultValue param or null.
     * </p>
     * @param {String} key A key defining a theme "styles" property.
     * @param {Object} defaultValue Value returned if specified key is not found.
     * @return {String} The selector property.
     */
    getClassName: function(key, defaultValue) {
        var className = @JS_NS@.theme.common.getProperty("styles", key);
        return (className != null) 
            ? className
            : (defaultValue) 
                ? defaultValue
                : null;                
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
     * "@JS_NS@.theme.common.getProperty("images", "ALARM_CRITICAL_ALT")"
     * to get individual values if desired.
     * If the literal path is desired, without the prefix, use
     * "@JS_NS@.theme.common.getProperty("images", imageprop)"
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
        var theme = @JS_NS@.theme.common;
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
        value = theme._getImageProp(srcProperty + "_MAP", false);
        if (value != null) {
            imageObj["map_key"] = value;
            value = theme._getImageProp(srcProperty + "_MAP_WIDTH", false);
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
     * Get a specific image property.
     *
     * @param {String} prop The image theme key with suffix (e.g., "_WIDTH").
     * @param {boolean} isText Flag indicating property is a message key.
     * @return {Object} Key-Value pairs of properties.
     * @private
     */
    _getImageProp: function(prop, isText) {
        var theme = @JS_NS@.theme.common;
	var value = theme.getProperty("images", prop);
	if (value == null || value.length == 0) {
	    return null;
	}
	if (isText == true) {
	    var msg = theme.getMessage(value, null);
	    if (msg != null && msg.length != 0) {
		value = msg;
	    }
	}
	return value;
    },

    /**
     * This function is used to obtain a the literal "javascript"
     * theme value for "key"
     *
     * @param {String} key A key defining a theme "javascript" property.
     * @return {String} The javascript property.
     * @private
     */
    _getJavaScript: function(key) {
        var theme = @JS_NS@.theme.common;
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
     * @private
     */
    _getJavaScripts: function(key) {
        var theme = @JS_NS@.theme.common;
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
     * This function returns the theme value defined by <code>key</code>
     * from the <code>messages</code> theme categpory. If the theme does 
     * not define a value for <code>key</code>, <code>defaultValue</code>
     * is returned. If <code>defaultValue</code> is not specified and
     * <code>key</code> is not defined in the theme <code>null</code>
     * is returned. The <code>args</code> if not null is assumed to be
     * an array of strings used to format the theme message value.
     * <p>
     * Since the "messages" theme category can contain data
     * as well as text messages, it is sometimes useful to provide a 
     * default in case key is not defined.
     * </p>
     *
     * @param {String} key A key defining a theme message property.
     * @param {Array} Message format arguments
     * @param {Object} defaultValue The value to return if "key" is not defined.
     * @return {String} The theme message defined by "key" or "defaultValue".
     */
    getMessage: function(key, args, defaultValue) {
	var msg = @JS_NS@.theme.common.getProperty("messages", key);
	if (msg != null && args != null) {
            msg = msg.replace(/\$\{(\w+)\}/g, function(match, key) {
                if (typeof(args[key]) != "undefined" && args[key] != null) {
                    return args[key];
                }
            });
        }
        return (msg != null) 
	    ? msg 
	    : (defaultValue)
		? defaultValue
		: null;                
    },

    /**
     * This function returns a boolean value for the theme value defined
     * by <code>key</code> from the <code>messages</code> theme categpory.
     * If the theme does not define a value for <code>key</code>,
     * <code>defaultValue</code> (which is assumed to be a boolean)
     * is returned. If <code>defaultValue</code> is not specified
     * and <code>key</code> is not defined in the theme, <code>false</code>
     * is returned.
     * <p>
     * This method converts the theme value, if one exists to all lower
     * case and then coerces the string to a boolean value as follows.<br/>
     * "false" == <code>false</code><br/>
     * "true" == <code>true</code><br/>
     * All other strings == <code>defaultValue</code> or <code>false</code><br/>
     * </p>
     *
     * @param {String} key A key defining a theme message property.
     * @param {boolean} defaultValue The value to return if <code>key</code> is 
     * not defined.
     * @return {boolean} A theme value defined by <code>key</code>,
     * <code>defaultValue</code>, or <code>false</code> if <code>key</code>
     * is not defined.
     * @private
     */
    _getMessageBoolean: function(key, defaultValue) {
	var result = defaultValue != null ? defaultValue : false;
        var msg =  @JS_NS@.theme.common.getMessage(key, null);
	if (msg == null || msg == "") {
	    return result;
	}
	var msg = msg.toLowerCase();
	if (msg == "false") {
	    return false;
	} else 
	if (msg == "true") {
	    return true;
	} else {
	    return result;
	}
    },

    /**
     * Return the theme prefix, this is the application context
     * concatenated with the theme servlet context.
     *
     * @return {String} The theme prefix.
     */
    getPrefix: function() {
	return @JS_NS@._base.config.theme.prefix;
    },

    /**
     * Returns the theme properties for a theme category or null.
     *
     * @param {String} category
     * @return {Object} Key-Value pairs of properties.
     */
    getProperties: function(category) {
        try {
            var props = @JS_NS@.theme.common._baseTheme[category];
            return props == null || props == "" ? null : props;
        } catch (e) {
            console.debug("Cannot find theme category: " + category); // See Firebug console.
        }
        return null;
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
            var props = @JS_NS@.theme.common.getProperties(category);
            if (props) {
                var props = props[key];
                return props == null || props == "" ? null : props;
            }
        } catch (e) {
            console.debug("Cannot find theme key: " + category + 
                "[" + key + "]"); // See Firebug console.
        }
        return null;
    },

    /**
     * This function is used to obtain a the literal "stylesheets"
     * theme value for "key"
     *
     * @param {String} key A key defining a theme "stylesheets" property.
     * @return {String} An array of style sheet properties.
     * @private
     */
    _getStyleSheets: function(key) {
        var theme = @JS_NS@.theme.common;
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
     * This function is used to obtain a the literal "templates"
     * theme value for "key"
     *
     * @param {String} key A key defining a theme "templates" property.
     * @return {String} The template property.
     * @private
     */
    _getTemplate: function(key) {
        return @JS_NS@.theme.common.getProperty("templates", key);
    },

    /**
     * This function is used to obtain a template path, or returns null
     * if key is not found or is not a path, i.e. begins with "<".
     * 
     * @param {String} key A key defining a theme "templates" property.
     * @return {String} The template path.
     * @private
     */
    _getTemplatePath: function(key) {
        var theme = @JS_NS@.theme.common;
        var template = theme._getTemplate(key);
        if (theme._isTemplatePath(template)) {
            return theme.getPrefix() + "/" + template;
        } else {
            return null;
        }
    },

    /**
     * This function is used to obtain a template string, or returns null
     * if key is not found or is not a string, i.e. does not begin with "<".
     *
     * @param {String} key A key defining a theme "templates" property.
     * @return {String} The template string.
     * @private
     */
    _getTemplateString: function(key) {
        var theme = @JS_NS@.theme.common;
        var template = theme._getTemplate(key);
        if (!theme._isTemplatePath(template)) {
            return template;
        } else {
            return null;
        }
    },

    /**
     * This function is used to test HTML template strings. 
     * <p>
     * Note: This function returns true if the "template" is a template path, 
     * and false if it is a template String. False is also returned if the value
     * is null or the empty string.
     * </p>
     * @param {String} template The template string to test.
     * @return {boolean} true if string is an HTML template.
     * @private
     */
    _isTemplatePath: function(template) {
        return (template != null && template.charAt(0) != '<');
    },

    /**
     * Declares translated resources and loads them if necessary, in the same 
     * style as @JS_NS@._dojo.require. Contents of the resource bundle are typically 
     * strings, but may be any name/value pair, represented in JSON format. 
     * See also @JS_NS@._dojo.i18n.getLocalization.
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
     * browser's user locale as defined by @JS_NS@._dojo.locale
     * @param {String} locale The current locale.
     * @param {String} availableFlatLocales A comma-separated list of the 
     * available, flattened locales for this bundle.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _requireLocalization: function(moduleName, bundleName, locale, 
            availableFlatLocales) {
        // Taken from @JS_NS@._dojo.js in order to override the callback function that is 
        // passed to loadPath, in to perform hierarchical "extension" of properties.

        // Default to "ROOT" for base locales to eliminate extra requests.
        var targetLocale = @JS_NS@._dojo.i18n.normalizeLocale(
            (locale == "en" || locale == "en-us") ? "ROOT" : locale);
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
        var bundle = @JS_NS@._dojo._loadedModules[bundlePackage];
        var localizedBundle = null;
        if (bundle) {
            if (@JS_NS@._base.config.djConfig.localizationComplete && bundle._built) {
                return false;    
            }
            var jsLoc = tempLocale.replace(/-/g, '_');
            var translationPackage = bundlePackage+"."+jsLoc;
            localizedBundle = @JS_NS@._dojo._loadedModules[translationPackage];
        }

        if (!localizedBundle) {
            bundle = @JS_NS@._dojo["provide"](bundlePackage);
            var syms = @JS_NS@._dojo._getModuleSymbols(moduleName);
            var modpath = syms.concat("nls").join("/");
            var parent;

            @JS_NS@._dojo.i18n._searchLocalePath(tempLocale, availableFlatLocales, function(loc) {
                var jsLoc = loc.replace(/-/g, '_');
                var translationPackage = bundlePackage + "." + jsLoc;
                var loaded = false;
                if (!@JS_NS@._dojo._loadedModules[translationPackage]) {
                    // Mark loaded whether it's found or not, 
                    // so that further load attempts will not 
                    // be made
                    @JS_NS@._dojo["provide"](translationPackage);
                    var module = [modpath];
                    if (loc != "ROOT") {
                        module.push(loc);    
                    }
                    module.push(bundleName);
                    var filespec = module.join("/") + '.js';
                    loaded = @JS_NS@._dojo._loadPath(filespec, null, function(hash) {
                        // Use singleton with prototype to point to parent
                        // bundle, then mix-in result from loadPath
                        var clazz = function() {};
                        clazz.prototype = parent;
                        bundle[jsLoc] = new clazz();
                        // Use "hierarchical" extend.
                        // for (var j in hash){ bundle[jsLoc][j] = hash[j]; }
                        @JS_NS@._base.proto._extend(bundle[jsLoc], hash);
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
};

// Initialize the theme.
@JS_NS@.theme.common._init(@JS_NS@._base.config.theme);
