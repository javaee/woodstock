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

webui.@THEME_JS@._base.dojo.provide("webui.@THEME_JS@.widget.passwordField");

webui.@THEME_JS@._base.dojo.require("webui.@THEME_JS@.widget._base.fieldBase");

/**
 * This function is used to construct a passwordField widget.
 *
 * @name webui.@THEME_JS@.widget.passwordField
 * @extends webui.@THEME_JS@.widget._base.fieldBase
 * @class This class contains functions for the passwordField widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accesskey 
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {int} maxLength 
 * @config {Array} notify 
 * @config {String} onBlur Element lost focus.
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onFocus Element received focus.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {boolean} readOnly 
 * @config {boolean} required 
 * @config {int} size 
 * @config {String} style Specify style rules inline.
 * @config {boolean} submitForm
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} valid
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
webui.@THEME_JS@._base.dojo.declare("webui.@THEME_JS@.widget.passwordField",
        webui.@THEME_JS@.widget._base.fieldBase, {
    // Set defaults.
    _widgetName: "passwordField" // Required for theme properties.
});

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
webui.@THEME_JS@.widget.passwordField.prototype._getInputClassName = function() {
    if (this.fieldNode.readOnly) {
        return this._theme._getClassName("PASSWORD_FIELD_READONLY", "");
    }

    //invalid style
    var validStyle =  (this.valid == false) 
        ? " " + this._theme._getClassName("PASSWORD_FIELD_INVALID", "")
        : " " + this._theme._getClassName("PASSWORD_FIELD_VALID", "");
    
    // Set default style.    
    return (this.disabled == true)
        ? this._theme._getClassName("PASSWORD_FIELD_DISABLED", "") 
        : this._theme._getClassName("PASSWORD_FIELD", "") + validStyle;
};
