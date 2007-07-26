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

dojo.provide("webui.@THEME@.widget.resetButton");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.button");

// NOTE: Having a separate widget for each template allows the templateString
// property to be cached. Otherwise, this would have to be cleared for each new
// widget instance.

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.resetButton = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.resetButton, webui.@THEME@.widget.button);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.resetButton, {
    // Set private functions.

    // Set defaults.
    widgetType: "resetButton"
});
