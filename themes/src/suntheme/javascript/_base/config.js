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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@._base.config");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@._base.proto");

/**
 * This optional variable contains config properties to initialize the 
 * environment. If this is not defined, webui_@THEME@ will be used (without the 
 * version number) for backward compatibility.
 * <p><code><pre>
 *
 * webui.@THEME_JS@ = {
 *    // Flag to enable webui debug mode.
 *    isDebug: false,
 *    // Flag to inlcude style sheet(s).
 *    isStyleSheet: true,
 *    // Custom name space to map as webui.@THEME_JS@.
 *    namespace: "foo",
 *    // Theme config properties.
 *    theme: {
 *      // Theme locale.
 *      locale: "en"
 *    },
 *    // Flag to include all webui functionality.
 *    webuiAll: false,
 *    // Flag to include all Ajax functionality based on JSF Extensions.
 *    webuiJsfx: false
 * };
 *
 * </pre></code></p>
 * @name webui_@THEME_JS@
 */

/**
 * @class This class contains config properties to initialize the environment.
 * Properties shall be ovrridden by the global webui.@THEME_JS@ variable. If 
 * this variable does not exist, webui_@THEME@ will be used (without the version
 * number) for backward compatibility.
 * @static
 * @private
 */
webui.@THEME_JS@._base.config = {
    /** Ajax config properties. */
    ajax: {
        /** Flag allowing Ajax resources to be loaded in page. */
        isAjax: false,
        /** Flag allowing JSF Extensions to be loaded in page. */
        isJsfx: true,
        /** Ajax module. */
        module: "webui.@THEME_JS@.widget._jsfx"
    },
    /** Flag to enable webui debug mode. */
    isDebug: false,
    /** Flag to enable high contrast mode. */
    _isHighContrastMode: undefined,
    /** Dojo config properties. */
    _djConfig: {
        /** Dojo module path. */
        baseUrl: undefined,
        /** Flag to enable dojo debug mode. */
        isDebug: false
    },
    /** Webui module path. */
    modulePath: undefined,
    /** Flag to inlcude style sheet(s). */
    isStyleSheet: true,
    /** Flag to parse HTML markup onLoad. */
    parseOnLoad: false,
    /** Custom name space to map as webui.@THEME_JS@. */
    namespace: "webui.@THEME@",
    /** Theme config properties. */
    theme: {
        /** Theme bundle name. */
        bundle: "@THEME@",
        /** Theme locale. */
        locale: "en",
        /** Theme module path. */
        modulePath: undefined,
        /** App context. */
        prefix: undefined
    },
    /** Flag to include all webui functionality. */
    webuiAll: false,
    /** Flag to include all Ajax functionality based on JSF Extensions. */
    webuiJsfx: false
};

// Override default config properties.
webui.@THEME_JS@._base.proto._extend(webui.@THEME_JS@._base.config, webui_@THEME_JS@);
