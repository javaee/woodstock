// widget/passwordField.js
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

/**
 * @name widget/passwordField.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the passwordField widget.
 * @example The following code is used to create a passwordField widget.
 * <p><code>
 * var widget = new webui.@THEME@.widget.passwordField(props, domNode);
 * </code></p>
 */
dojo.provide("webui.@THEME@.widget.passwordField");

dojo.require("webui.@THEME@.widget.fieldBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.passwordField
 * @inherits webui.@THEME@.widget.fieldBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.passwordField", webui.@THEME@.widget.fieldBase, {
    // Set defaults.
    widgetName: "passwordField" // Required for theme properties.
});

/**
 * Helper function to obtain HTML input element class names.
 */
webui.@THEME@.widget.passwordField.prototype.getInputClassName = function() {
    if (this.fieldNode.readOnly) {
        return this.widget.getClassName("PASSWORD_FIELD_READONLY", "");
    }

    //invalid style
    var validStyle =  (this.valid == false) 
        ? " " + this.widget.getClassName("PASSWORD_FIELD_INVALID", "")
        : " " + this.widget.getClassName("PASSWORD_FIELD_VALID", "");
    
    // Set default style.    
    return (this.disabled == true)
        ? this.widget.getClassName("PASSWORD_FIELD_DISABLED", "") 
        : this.widget.getClassName("PASSWORD_FIELD", "") + validStyle;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 */
webui.@THEME@.widget.passwordField.prototype.postCreate = function () {
    // Set public functions.
    this.refresh = this.domNode.refresh = null; // Not supported.
    this.submit = this.domNode.submit = null; // Not supported.

    return this.inherited("postCreate", arguments);
}

/**
 * This function is used to set widget properties using Object literals.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} [accesskey] 
 * @config {String} [className] CSS selector.
 * @config {String} [dir] Specifies the directionality of text.
 * @config {boolean} [disabled] Disable element.
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {String} [label]
 * @config {String} [lang] Specifies the language of attribute values and content.
 * @config {int} [maxLength] 
 * @config {Array} [notify] 
 * @config {String} [onBlur] Element lost focus.
 * @config {String} [onClick] Mouse button is clicked on element.
 * @config {String} [onDblClick] Mouse button is double-clicked on element.
 * @config {String} [onFocus] Element received focus.
 * @config {String} [onKeyDown] Key is pressed down over element.
 * @config {String} [onKeyPress] Key is pressed and released over element.
 * @config {String} [onKeyUp] Key is released over element.
 * @config {String} [onMouseDown] Mouse button is pressed over element.
 * @config {String} [onMouseOut] Mouse is moved away from element.
 * @config {String} [onMouseOver] Mouse is moved onto element.
 * @config {String} [onMouseUp] Mouse button is released over element.
 * @config {String} [onMouseMove] Mouse is moved while over element.
 * @config {boolean} [readOnly] 
 * @config {boolean} [required] 
 * @config {int} [size] 
 * @config {String} [style] Specify style rules inline.
 * @config {boolean} [submitForm]
 * @config {int} [tabIndex] Position in tabbing order.
 * @config {String} [title] Provides a title for element.
 * @config {boolean} [valid]
 * @config {String} [value] Value of input.
 * @config {boolean} [visible] Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.passwordField.prototype.setProps = function(props, notify) {
    // Note: This function is overridden for JsDoc.
    return this.inherited("setProps", arguments);
}
