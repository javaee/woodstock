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

webui.@THEME@.dojo.provide("webui.@THEME@.config");

webui.@THEME@.dojo.require("webui.@THEME@.prototypejs");

/**
 * @class This class contains config properties to initialize the environment.
 * Properties shall be ovrridden by the global webui_@THEME@_config variable.
 * @static
 */
webui.@THEME@.config = {
    /** Ajax config properties. */
    ajax: {
        /** Flag allowing JSF Extensions to be loaded in page. */
        isJsfx: true,
        /** Ajax module. */
        module: "webui.@THEME@.widget.jsfx"
    },
    /** Flag to enable webui debug mode. */
    isDebug: false,
    /** Dojo config properties. */
    djConfig: {
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
}

// Override default config properties.
webui.@THEME@.prototypejs.extend(webui.@THEME@.config, webui_@THEME@_config);
