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

dojo.provide("webui.@THEME@.widget.passwordField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.field");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.passwordField = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the setWidgetProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.passwordField.fillInTemplate = function(props, frag) {
    // Set common functions.
    webui.@THEME@.widget.passwordField.superclass.fillInTemplate.call(this, props, frag);

    // Set public functions.
    this.domNode.refresh = null; // Not supported.
    
    return true;
}

/**
 * Helper function to obtain HTML input element class names.
 */
webui.@THEME@.widget.passwordField.getInputClassName = function() {
    // Set default style.    
    return (this.disabled == true)
        ? webui.@THEME@.widget.props.passwordField.disabledClassName
        : webui.@THEME@.widget.props.passwordField.className;
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.passwordField, webui.@THEME@.widget.field);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.passwordField, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.passwordField.fillInTemplate,
    getInputClassName: webui.@THEME@.widget.passwordField.getInputClassName,

    // Set defaults.
    disabled: false,
    required: false,
    size: 20,
    templatePath: webui.@THEME@.theme.getTemplatePath("passwordField"),
    templateString: webui.@THEME@.theme.getTemplateString("passwordField"),
    valid: true,
    widgetType: "passwordField"
});
