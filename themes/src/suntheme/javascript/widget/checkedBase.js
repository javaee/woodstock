// widget/checkedBase.js
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

dojo.provide("webui.@THEME@.widget.checkedBase");

dojo.require("webui.@THEME@.browser");
dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.checkedBase
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for widgets that extend checkedBase.
 * @static
 */
dojo.declare("webui.@THEME@.widget.checkedBase", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    idSuffix: "" // Overridden by subclass
});

/**
 * Helper function to obtain image class names.
 *
 * @return {String} The HTML image element class name.
 */
webui.@THEME@.widget.checkedBase.prototype.getImageClassName = function() {
    return null; // Overridden by subclass.
}

/**
 * Helper function to obtain input class names.
 *
 * @return {String} The HTML input element class name.
 */
webui.@THEME@.widget.checkedBase.prototype.getInputClassName = function() {
    return null; // Overridden by subclass.
}

/**
 * Returns the HTML input element that makes up the chekcbox.
 *
 * @return {Node} The HTML input element. 
 */
webui.@THEME@.widget.checkedBase.prototype.getInputElement = function() {
    return this.inputNode;
}

/**
 * Helper function to obtain label class names.
 *
 * @return {String} The HTML label element class name.
 */
webui.@THEME@.widget.checkedBase.prototype.getLabelClassName = function() {
    return null; // Overridden by subclass.
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.checkedBase.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.  
    if (this.align) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.image) { props.image = this.image; }
    if (this.label) { props.label = this.label; }
    if (this.name) { props.name = this.name; }        
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.value) { props.value = this.value; }

    // After widget has been initialized, get user's input.
    if (this.isInitialized() == true && this.inputNode.checked != null) {
        props.checked = this.inputNode.checked;
    } else if (this.checked != null) {
        props.checked = this.checked;
    }
    return props;
}

/**
 * Helper function to create callback for onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.checkedBase.prototype.onClickCallback = function(event) {
    if (this.readOnly == true) {
        event.preventDefault();
        return false;
    }

    // If function returns false, we must prevent the request.
    var result = (this.domNode._onclick)
        ? this.domNode._onclick(event) : true;
    if (result == false) {
        event.preventDefault();
        return false;
    }
    return true;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.checkedBase.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.inputNode.id = this.id + this.idSuffix;
        this.imageContainer.id = this.id + "_imageContainer";
        this.labelContainer.id = this.id + "_labelContainer";

        // If null, use HTML input id.
        if (this.name == null) {
            this.name = this.inputNode.id;
        }
    }

    // Set public functions.
    this.domNode.getInputElement = function() { return dijit.byId(this.id).getInputElement(); }
    
    // Create callback function for onclick event.
    dojo.connect(this.domNode, "onclick", this, "onClickCallback");

    return this.inherited("postCreate", arguments);
}

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.checkedBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.value) { 
        this.inputNode.value = props.value;
    }
    if (props.readOnly != null) { 
        this.inputNode.readOnly = new Boolean(props.readOnly).valueOf();       
    }
    if (props.disabled != null) {
        this.inputNode.disabled = new Boolean(props.disabled).valueOf();        
    }
    
    // Set HTML input element class name.
    this.inputNode.className = this.getInputClassName();
    
    if (props.name) { 
        this.inputNode.name = props.name;
    }

    if (props.checked != null) {
        var checked = new Boolean(props.checked).valueOf();

        // Dynamically setting the checked attribute on IE 6 does not work until
        // the HTML input element has been added to the DOM. As a work around, 
        // we shall use a timeout to set the property during initialization.
        if (this.isInitialized() != true &&
                webui.@THEME@.browser.isIe()) {
            var _id = this.id;
            setTimeout(function() {
                // New literals are created every time this function
                // is called, and it's saved by closure magic.
                var widget = dijit.byId(_id);
                widget.inputNode.checked = checked;
            }, 0); // (n) milliseconds delay.
        } else {
            this.inputNode.checked = checked;
        }
    }

    // Set image properties.
    if (props.image || props.disabled != null && this.image) {     
        // Ensure property exists so we can call setProps just once.
        if (props.image == null) {
            props.image = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.image.className = this.getImageClassName();

        // Update/add fragment.
        this.widget.updateFragment(this.imageContainer, this.image.id, props.image);
    } 

    // Set label properties.
    if (props.label || props.disabled != null && this.label) {     
        // Ensure property exists so we can call setProps just once.
        if (props.label == null) {
            props.label = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.label.className = this.getLabelClassName();

        // Update/add fragment.
        this.widget.updateFragment(this.labelContainer, this.label.id, props.label);
    }

    // Set more properties.
    this.setCommonProps(this.inputNode, props);
    this.setEventProps(this.inputNode, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
