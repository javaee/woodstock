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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.checkedBase");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.labeledBase");

/**
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.checkedBase
 * @extends @JS_NS@.widget._base.labeledBase
 * @class This class contains functions for widgets that extend checkedBase.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.checkedBase",
        @JS_NS@.widget._base.labeledBase, {
    // Set defaults.
    _idSuffix: "" // Overridden by subclass
});

/**
 * Helper function to obtain image class names.
 *
 * @return {String} The HTML image element class name.
 * @private
 */
@JS_NS@.widget._base.checkedBase.prototype._getImageClassName = function() {
    return null; // Overridden by subclass.
};

/**
 * Helper function to obtain input class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
@JS_NS@.widget._base.checkedBase.prototype._getInputClassName = function() {
    return null; // Overridden by subclass.
};

/**
 * Returns the HTML input element that makes up the chekcbox.
 *
 * @return {Node} The HTML input element. 
 */
@JS_NS@.widget._base.checkedBase.prototype.getInputElement = function() {
    return this._inputNode;
};

/**
 * Return an Object Literal of label properties desired
 * by the checkedBase widget.
 * <p>
 * This implementation adds the <code>htmlFor</code> property with
 * <code>this._inputNode.id</code>.
 * </p>
 * @return {Object} object with the <code>htmlFor</code>
 * property set.
 * @private
 */
@JS_NS@.widget._base.checkedBase.prototype._getLabelProps = function() {
    // Let the super class contribute
    //
    var props = this.inherited("_getLabelProps", arguments);
    // Subclasses can override this value
    //
    props.level = 3;
    props.htmlFor = this._inputNode.id;
    return props;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget._base.checkedBase.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.  
    if (this.align != null) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.image != null) { props.image = this.image; }
    if (this.name != null) { props.name = this.name; }        
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.value != null) { props.value = this.value; }

    // After widget has been initialized, get user's input.
    if (this._isInitialized() == true && this._inputNode.checked != null) {
        props.checked = this._inputNode.checked;
    } else if (this.checked != null) {
        props.checked = this.checked;
    }
    return props;
};

/**
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.checkedBase.prototype._onClickCallback = function(event) {
    if (this.readOnly == true) {
        event.preventDefault();
        return false;
    }

    // If function returns false, we must prevent the request.
    var result = (this._domNode._onclick)
        ? this._domNode._onclick(event) : true;
    if (result == false) {
        event.preventDefault();
        return false;
    }
    return true;
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
@JS_NS@.widget._base.checkedBase.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._inputNode.id = this.id + this._idSuffix;
        this._imageContainer.id = this.id + "_imageContainer";

        // If null, use HTML input id.
        if (this.name == null) {
            this.name = this._inputNode.id;
        }
    }

    // Set public functions.

    /** @ignore */
    this._domNode.getInputElement = function() {
	var widget = @JS_NS@.widget;
	return widget.common.getWidget(this.id).getInputElement();
    }
    
    // Create callback function for onclick event.
    this._dojo.connect(this._domNode, "onclick", this, "_onClickCallback");

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
@JS_NS@.widget._base.checkedBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.value != null) { 
        this._inputNode.value = props.value;
    }
    if (props.readOnly != null) { 
        this._inputNode.readOnly = new Boolean(props.readOnly).valueOf();       
    }
    if (props.disabled != null) {
        this._inputNode.disabled = new Boolean(props.disabled).valueOf();        
    }
    
    // Set HTML input element class name.
    this._inputNode.className = this._getInputClassName();
    
    if (props.name != null) { 
        // IE does not support the name attribute being set dynamically as 
        // documented at:
        //
        // http://msdn.microsoft.com/workshop/author/dhtml/reference/properties/name_2.asp
        //
        // In order to create an HTML element with a name attribute, the name
        // and value must be provided when using the inner HTML property or the
        // document.createElement() function. As a work around, we shall set the
        // attribute via the HTML template using name="${this.name}". In order
        // to obtain the correct value, the name property must be provided to 
        // the widget. Although we're resetting the name below, as the default,
        // this has no affect on IE. 
        this._inputNode.name = props.name;
    }

    if (props.checked != null) {
        var checked = new Boolean(props.checked).valueOf();

        // Dynamically setting the checked attribute on IE 6 does not work until
        // the HTML input element has been added to the DOM. As a work around, 
        // we shall use a timeout to set the property during initialization.
        if (this._isInitialized() != true &&
                @JS_NS@._base.browser._isIe()) {
            var _id = this.id;
            setTimeout(function() {
                // New literals are created every time this function
                // is called, and it's saved by closure magic.
                var widget = @JS_NS@.widget.common.getWidget(_id);
                widget._inputNode.checked = checked;
            }, 0); // (n) milliseconds delay.
        } else {
            this._inputNode.checked = checked;
        }
    }

    // Set image properties.
    if (props.image || props.disabled != null && this.image) {     
        // Ensure property exists so we can call setProps just once.
        if (props.image == null) {
            props.image = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.image.className = this._getImageClassName();

        // Update/add fragment.
        this._widget._updateFragment(this._imageContainer, this.image.id, props.image);
    } 

    // Set more properties.
    this._setCommonProps(this._inputNode, props);
    this._setEventProps(this._inputNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
