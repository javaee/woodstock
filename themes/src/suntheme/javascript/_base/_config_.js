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

/**
 * This optional variable contains config properties to initialize the 
 * environment. For example:
 * <p><code><pre>
 * @JS_NS@Config = {
 *   // Ajax config properties.
 *   ajax: {
 *       // Flag allowing Ajax resources to be lazily loaded when the webuiAjax
 *       // property is false.
 *       isAjax: true,
 *       // Flag allowing JSF Extensions to be loaded in page. If the webuiAjax 
 *       // property is true, resources are loaded immediately. Otherwise, 
 *       // resources are lazily loaded.
 *       isJsfx: true,
 *       // Ajax module.
 *       module: "@JS_NS@.widget._xhr",
 *       // Ajax module path.
 *       module: "/example/resources/@JS_NS@/@THEME@/javascript/widget/_xhr",
 *       // URL used for Ajax transactions. Note: Not used by JSF Extensions.
 *       url: "/example/ExampleServlet"
 *   },
 *   // Flag to enable debug mode.
 *   isDebug: false,
 *   // Flag to inlcude style sheet(s).
 *   isStyleSheet: true,
 *   // Woodstock module path -- absolute or relative to javascript directory.
 *   modulePath: "/example/resources/@JS_NS/@THEME@/javascript",
 *   // Custom name space to map as @JS_NS@.
 *   namespace: "webui.@THEME@",
 *   // Flag to parse HTML markup onLoad.
 *   parseOnLoad: true,
 *   // Theme config properties.
 *   theme: {
 *       // Theme bundle name.
 *       bundle: "@THEME@",
 *       // An array of custom theme config properties.
 *       custom: [{
 *           /** Custom theme bundle name.
 *           bundle: "myTheme",
 *           /** Custom theme module path -- absolute or relative to javascript directory.
 *           modulePath: "/example/resources/custom"
 *       }],
 *       // Theme locale.
 *       locale: "en",
 *       // Theme module path -- absolute or relative to javascript directory.
 *       modulePath: "/example/resources/@JS_NS@/@THEME@/javascript/theme",
 *       // App context.
 *       prefix: "/example/resources"
 *   }
 * };
 * </pre></code></p><p>
 * If this variable is not availble, @JS_NAME@Config will be used. However, in a
 * portal environment, the version number must be used in order to support
 * multiple versions of Woodstock in the same page. For example, portlet 'A'
 * still uses an older version of Woodstock while portlet 'B' takes advantage of
 * new features in the latest release. In this case, the same cofig properties
 * may not apply to both Woodstock JavaScript libraries.
 * </p>
 * @name @JS_NS@Config
 */

// Initialize the @JS_NS@Config variable.
if (typeof @JS_NS@Config == "undefined") {
    this.@JS_NS@Config = (typeof @JS_NAME@Config != "undefined")
        ? @JS_NAME@Config : {};
}

/**
 * @class This class contains config properties to initialize the environment.
 * Properties shall be ovrridden by the global @JS_NS@Config variable.
 * @static
 * @private
 */
@JS_NS@._base.config = {
    /** Ajax config properties. */
    ajax: {
        /**
         * Flag allowing Ajax resources to be lazily loaded when the webuiAjax
         * property is false.
         */
        isAjax: (@JS_NS@Config.ajax && @JS_NS@Config.ajax.isAjax != null)
            ? @JS_NS@Config.ajax.isAjax : true,
        /**
         * Flag allowing JSF Extensions to be loaded in page. If the webuiAjax 
         * property is true, resources are loaded immediately. Otherwise, 
         * resources are lazily loaded.
         */
        isJsfx: @JS_NS@._base.bootstrap._getBooleanParameter("jsfx",
            (@JS_NS@Config.ajax && @JS_NS@Config.ajax.isJsfx != null) 
                ? @JS_NS@Config.ajax.isJsfx : true),
        /** Ajax module. */
        module: (@JS_NS@Config.ajax && @JS_NS@Config.ajax.module)
            ? @JS_NS@Config.ajax.module : "@JS_NS@.widget._xhr",
        /** Ajax module path -- absolute or relative to javascript directory. */
        modulePath: (@JS_NS@Config.ajax && @JS_NS@Config.ajax.modulePath)
            ? @JS_NS@Config.ajax.modulePath : undefined,
        /** URL used for Ajax transactions. Note: Not used by JSF Extensions. */
        url: (@JS_NS@Config.ajax && @JS_NS@Config.ajax.url)
            ? @JS_NS@Config.ajax.url : undefined
    },
    /** Dojo config properties. */
    _djConfig: {
        /** Dojo module path. */
        baseUrl: undefined,
        /** Flag to enable dojo debug mode. */
        isDebug: false
    },
    /** 
     * The current global context. This ensures code runs correctly in contexts
     * other than web browsers (e.g., rhino).
     */
    _global: this,
    /** Flag to enable debug mode. */
    isDebug: @JS_NS@._base.bootstrap._getBooleanParameter("debug",
        (@JS_NS@Config.isDebug != null) ? @JS_NS@Config.isDebug : false),
    /** Flag to enable high contrast mode. */
    _isHighContrastMode: undefined,
    /** Flag to inlcude style sheet(s). */
    isStyleSheet: @JS_NS@._base.bootstrap._getBooleanParameter("styleSheet",
        (@JS_NS@Config.isStyleSheet != null) ? @JS_NS@Config.isStyleSheet : true),
    /** Woodstock module path -- absolute or relative to javascript directory. */
    modulePath: (@JS_NS@Config.modulePath) ? @JS_NS@Config.modulePath : ".",
    /** Custom name space to map as @JS_NS@. */
    namespace: (@JS_NS@Config.namespace) ? @JS_NS@Config.namespace : "woodstock",
    /** Flag to parse HTML markup onLoad. */
    parseOnLoad: @JS_NS@._base.bootstrap._getBooleanParameter("parseOnLoad",
        (@JS_NS@Config.parseOnLoad != null) ? @JS_NS@Config.parseOnLoad : false),
    /** Theme config properties. */
    theme: {
        /** Theme bundle name. */
        bundle: (@JS_NS@Config.theme && @JS_NS@Config.theme.bundle)
            ? @JS_NS@Config.theme.bundle : "@THEME@",
        /** An array of custom theme config properties. See @JS_NS@Config. */
        custom: (@JS_NS@Config.theme && @JS_NS@Config.theme.custom)
            ? @JS_NS@Config.theme.custom : undefined,
        /** Theme locale. */
        locale: (@JS_NS@Config.theme && @JS_NS@Config.theme.locale)
            ? @JS_NS@Config.theme.locale
            : (navigator.userLanguage 
                ? navigator.userLanguage : navigator.language).toLowerCase(),
        /** Theme module path -- absolute or relative to javascript directory. */
        modulePath: (@JS_NS@Config.theme && @JS_NS@Config.theme.modulePath)
            ? @JS_NS@Config.theme.modulePath : "theme",
        /** App context. */
        prefix: (@JS_NS@Config.theme && @JS_NS@Config.theme.prefix)
            ? @JS_NS@Config.theme.prefix : undefined
    },
    /** Flag to include all widgets. NOT IMPLEMENTED */
    webuiAll: @JS_NS@._base.bootstrap._getBooleanParameter("webuiAll",
        (@JS_NS@Config.webuiAll != null) ? @JS_NS@Config.webuiAll : false),
    /**
     * Flag to include Ajax functionality. Used in conjunction with webuiAll. 
     * Note: Not used with custom Ajax implementations. NOT IMPLEMENTED
     */
    webuiAjax: @JS_NS@._base.bootstrap._getBooleanParameter("webuiAjax", 
        (@JS_NS@Config.webuiAjax != null) ? @JS_NS@Config.webuiAjax : false)
};
