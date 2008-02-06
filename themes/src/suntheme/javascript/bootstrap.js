// bootstrap.js
//
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

webui.@THEME@.dojo.provide("webui.@THEME@.bootstrap");

webui.@THEME@.dojo.require("webui.@THEME@.theme.common");
webui.@THEME@.dojo.require("webui.@THEME@.widget.common");

/**
 * @class This class contains functions to initialize the environment.
 * @static
 * @private
 */
webui.@THEME@.bootstrap = {
    /**
     * This function is used to initialize the environment with Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {boolean} isDebug Flag indicating debug mode is enabled.
     * @config {String} module The Woodtsock module.
     * @config {String} modulePath The module path.
     * @config {Object} theme Key-Value pairs of theme properties.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    init: function(props) {
        if (props == null) {
            return false;
        }

        // Register module path.
        if (props.module && props.modulePath) {
            webui.@THEME@.dojo.registerModulePath(props.module, props.modulePath);
        } else {
            return false;
        }

        // Initialize theme.
        if (props.theme) {
            webui.@THEME@.theme.common.init(props.theme);
        } else {
            return false;
        }

        // Dojo inserts a div into body for HTML template rendering; therefore,
        // we must wait until the window.onLoad event before creating widgets.
        // Otherwise, IE will throw a security exception.
        webui.@THEME@.dojo.addOnLoad(function() {
            webui.@THEME@.widget.common.replaceElements(webui.@THEME@.dojo.body());
        });
        return true;
    }
}

// Initialize the environment.
webui.@THEME@.bootstrap.init(webui.@THEME@.config);
