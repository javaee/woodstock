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
        /** Allow JSF Extensions to be loaded in page. */
        jsfx: true,
        /** Ajax module. */
        module: "webui.@THEME@.widget.jsfx"
    },
    /** Webui debug flag. */
    debug: false,
    /** Dojo config properties. */
    djConfig: {
        /** Dojo module path. */
        baseUrl: undefined,
        /** Dojo debug flag. */
        isDebug: false
    },
    /** Webui module path. */
    modulePath: undefined,
    /** Inlcude style sheets. */
    styleSheet: true,
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
    /** Include all webui functionality. */
    webuiAll: false,
    /** Include all Ajax functionality based on JSF Extensions. */
    webuiJsfx: false,
    /** Initialize webui functionality on load. */
    webuiOnLoad: false
}

// Override default config properties.
webui.@THEME@.prototypejs.extend(webui.@THEME@.config, webui_@THEME@_config);
