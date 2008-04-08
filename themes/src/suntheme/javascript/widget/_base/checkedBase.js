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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget._base.checkedBase");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@._base.browser");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.widgetBase");

/**
 * This function is used to construct a base class.
 *
 * @name webui.@THEME_JS@.widget._base.checkedBase
 * @extends webui.@THEME_JS@.widget._base.widgetBase
 * @class This class contains functions for widgets that extend checkedBase.
 * @constructor
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget._base.checkedBase",
        webui.@THEME_JS@.widget._base.widgetBase, {
    // Set defaults.
    _idSuffix: "" // Overridden by subclass
});

/**
 * Helper function to obtain image class names.
 *
 * @return {String} The HTML image element class name.
 * @private
 */
webui.@THEME_JS@.widget._base.checkedBase.prototype._getImageClassName = function() {
    return null; // Overridden by subclass.
};

/**
 * Helper function to obtain input class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
webui.@THEME_JS@.widget._base.checkedBase.prototype._getInputClassName = function() {
    return null; // Overridden by subclass.
};

/**
 * Returns the HTML input element that makes up the chekcbox.
 *
 * @return {Node} The HTML input element. 
 */
webui.@THEME_JS@.widget._base.checkedBase.prototype.getInputElement = function() {
    return this._inputNode;
};

/**
 * Helper function to obtain label class names.
 *
 * @return {String} The HTML label element class name.
 * @private
 */
webui.@THEME_JS@.widget._base.checkedBase.prototype._getLabelClassName = function() {
    return null; // Overridden by subclass.
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget._base.checkedBase.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.  
    if (this.align) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.image) { props.image = this.image; }
    if (this.label) { props.label = this.label; }
    if (this.name) { props.name = this.name; }        
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.value) { props.value = this.value; }

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
webui.@THEME_JS@.widget._base.checkedBase.prototype._onClickCallback = function(event) {
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
webui.@THEME_JS@.widget._base.checkedBase.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._inputNode.id = this.id + this._idSuffix;
        this._imageContainer.id = this.id + "_imageContainer";
        this._labelContainer.id = this.id + "_labelContainer";

        // If null, use HTML input id.
        if (this.name == null) {
            this.name = this._inputNode.id;
        }
    }

    // Set public functions.
    this._domNode.getInputElement = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).getInputElement(); }
    
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
webui.@THEME_JS@.widget._base.checkedBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.value) { 
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
    
    if (props.name) { 
        this._inputNode.name = props.name;
    }

    if (props.checked != null) {
        var checked = new Boolean(props.checked).valueOf();

        // Dynamically setting the checked attribute on IE 6 does not work until
        // the HTML input element has been added to the DOM. As a work around, 
        // we shall use a timeout to set the property during initialization.
        if (this._isInitialized() != true &&
                webui.@THEME_JS@._base.browser._isIe()) {
            var _id = this.id;
            setTimeout(function() {
                // New literals are created every time this function
                // is called, and it's saved by closure magic.
                var widget = webui.@THEME_JS@.widget.common.getWidget(_id);
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

    // Set label properties.
    if (props.label || props.disabled != null && this.label) {     
        // Ensure property exists so we can call setProps just once.
        if (props.label == null) {
            props.label = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.label.className = this._getLabelClassName();

        // Update/add fragment.
        this._widget._updateFragment(this._labelContainer, this.label.id, props.label);
    }

    // Set more properties.
    this._setCommonProps(this._inputNode, props);
    this._setEventProps(this._inputNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
