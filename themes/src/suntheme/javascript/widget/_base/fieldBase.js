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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.fieldBase");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.fieldBase
 * @extends @JS_NS@.widget._base.widgetBase
 * @class This class contains functions for widgets that extend fieldBase.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.fieldBase",
        @JS_NS@.widget._base.labeledBase, {
    // Set defaults.
    constructor: function() {
	this.disabled = false;
	this.size = 20;
    }
});

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
@JS_NS@.widget._base.fieldBase.prototype._getInputClassName = function() {   
    return null; // Overridden by subclass.
};

/**
 * Returns the HTML input element that makes up the text field.
 *
 * @return {Node} The HTML input element.
 */
@JS_NS@.widget._base.fieldBase.prototype.getInputElement = function() {
    return this._fieldNode;
};

/**
 * Return an Object Literal of label properties desired by this widget.
 * <p>
 * This implementation adds the <code>htmlFor</code> property with
 * <code>this._fieldNode.id</code>.
 * </p>
 * @return {Object} Key-Value pairs of properties.
 * @private
 */
@JS_NS@.widget._base.fieldBase.prototype._getLabelProps = function() {
    var props = this._inherited("_getLabelProps", arguments);

    props.htmlFor = this._fieldNode.id;
    return props;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget._base.fieldBase.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);
    
    // Set properties.
    if (this.alt != null) { props.alt = this.alt; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.maxLength > 0) { props.maxLength = this.maxLength; }    
    if (this.notify != null) { props.notify = this.notify; }
    if (this.submitForm != null) { props.submitForm = this.submitForm; }
    if (this.text != null) { props.text = this.text; }
    if (this.title != null) { props.title = this.title; }
    if (this.type != null) { props.type= this.type; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.size > 0) { props.size = this.size; }
    if (this.style != null) { props.style = this.style; }
    
    // After widget has been initialized, get user's input.
    if (this._isInitialized() == true && this._fieldNode.value != null) {
        props.value = this._fieldNode.value;
    } else if (this.value != null) {
        props.value = this.value;
    }
    return props;
};

/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.fieldBase.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._fieldNode.id = this.id + "_field";
        this._fieldNode.name = this.id + "_field";
    }
    
    // Set public functions.

    /** @ignore */
    this._domNode.getInputElement = function() { return @JS_NS@.widget.common.getWidget(this.id).getInputElement(); };
    
    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to set widget properties. Please see the constructor 
 * detail for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.fieldBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.submitForm == false || props.submitForm == true) { 
        // connect the keyPress event
        this._dojo.connect(this._fieldNode, "onkeypress", this, "_submitFormData");
    }
    if (props.maxLength > 0) { this._fieldNode.maxLength = props.maxLength; }
    if (props.size > 0) { this._fieldNode.size = props.size;  }
    if (props.value != null) { this._fieldNode.value = props.value; }
    if (props.title != null) { this._fieldNode.title = props.title; }   
    if (props.disabled != null) { 
        this._fieldNode.disabled = new Boolean(props.disabled).valueOf();
    }
    if (props.readOnly != null) { 
        this._fieldNode.readOnly = new Boolean(props.readOnly).valueOf();
    }
    
    // Set HTML input element class name.
    this._fieldNode.className = this._getInputClassName();
    
    // Set more properties.
    this._setCommonProps(this._fieldNode, props);
    this._setEventProps(this._fieldNode, props);
    
    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * Process keyPress events on the field, which enforces/disables 
 * submitForm behavior.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.fieldBase.prototype._submitFormData = function(event) {
    if (event == null) {
        return false;
    }
    if (event.keyCode == event.KEY_ENTER) {               
        if (this.submitForm == false) {
            // Disable form submission.
            if (window.event) {
                event.cancelBubble = true;
                event.returnValue = false;
            } else{
                event.preventDefault();
                event.stopPropagation();
            }
            return false;
        } else {
            // Submit the form                    
            if (event.currentTarget.form) {
                event.currentTarget.form.submit();
            }
        }
    }
    return true;    
};
