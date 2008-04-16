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
 * environment.
 * <p><code><pre>
 *
 * @JS_NS@Config = {
 *    // Flag to enable debug mode.
 *    isDebug: false,
 *    // Flag to inlcude style sheet(s).
 *    isStyleSheet: true,
 *    // Custom name space to map as @JS_NS@.
 *    namespace: "foo",
 *    // Flag to parse HTML markup onLoad.
 *    parseOnLoad: true,
 *    // Theme config properties.
 *    theme: {
 *      // Theme locale.
 *      locale: "en"
 *    },
 *    // Flag to include all widgets.
 *    webuiAll: false,
 *    // Flag to include Ajax functionality based on JSF Extensions.
 *    webuiJsfx: false
 * };
 *
 * </pre></code></p>
 * @name @JS_NS@Config
 */

/**
 * @class This class contains config properties to initialize the environment.
 * Properties shall be ovrridden by the global @JS_NS@Config variable.
 * @static
 * @private
 */
@JS_NS@._base.config = {
    /** Ajax config properties. */
    ajax: {
        /** Flag allowing Ajax resources to be loaded in page. */
        isAjax: (@JS_NS@Config.ajax && @JS_NS@Config.ajax.isAjax != null)
            ? @JS_NS@Config.ajax.isAjax : false,
        /** Flag allowing JSF Extensions to be loaded in page. */
        isJsfx: (@JS_NS@Config.ajax && @JS_NS@Config.ajax.isJsfx != null)
            ? @JS_NS@Config.ajax.isJsfx : true,
        /** Ajax module. */
        module: (@JS_NS@Config.ajax && @JS_NS@Config.ajax.module)
            ? @JS_NS@Config.ajax.module : "@JS_NS@.widget._jsfx"
    },
    /** Flag to enable debug mode. */
    isDebug: (@JS_NS@Config.isDebug != null) ? @JS_NS@Config.isDebug : false,
    /** Flag to enable high contrast mode. */
    _isHighContrastMode: undefined,
    /** Dojo config properties. */
    _djConfig: {
        /** Dojo module path. */
        baseUrl: undefined,
        /** Flag to enable dojo debug mode. */
        isDebug: false
    },
    /** Woodstock module path. */
    modulePath: (@JS_NS@Config.modulePath) ? @JS_NS@Config.modulePath : undefined,
    /** Flag to inlcude style sheet(s). */
    isStyleSheet: (@JS_NS@Config.isStyleSheet != null) ? @JS_NS@Config.isStyleSheet : true,
    /** Flag to parse HTML markup onLoad. */
    parseOnLoad: (@JS_NS@Config.parseOnLoad != null) ? @JS_NS@Config.parseOnLoad : true,
    /** Custom name space to map as @JS_NS@. */
    namespace: (@JS_NS@Config.namespace) ? @JS_NS@Config.namespace : "webui.@THEME@",
    /** Theme config properties. */
    theme: {
        /** Theme bundle name. */
        bundle: (@JS_NS@Config.theme && @JS_NS@Config.theme.bundle)
            ? @JS_NS@Config.theme.bundle : "@THEME@",
        /** Theme locale. */
        locale: (@JS_NS@Config.theme && @JS_NS@Config.theme.locale)
            ? @JS_NS@Config.theme.locale : "en",
        /** Theme module path. */
        modulePath: (@JS_NS@Config.theme && @JS_NS@Config.theme.modulePath)
            ? @JS_NS@Config.theme.modulePath : undefined,
        /** App context. */
        prefix: (@JS_NS@Config.theme && @JS_NS@Config.theme.prefix)
            ? @JS_NS@Config.theme.prefix : undefined
    },
    /** Flag to include all widgets. */
    webuiAll: (@JS_NS@Config.webuiAll != null) ? @JS_NS@Config.webuiAll : false,
    /** Flag to include Ajax functionality based on JSF Extensions. */
    webuiJsfx: (@JS_NS@Config.webuiJsfx != null) ? @JS_NS@Config.webuiJsfx : false
};
