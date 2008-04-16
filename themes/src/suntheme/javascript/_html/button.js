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

@JS_NS@._dojo.provide("@JS_NS@._html.button");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@.widget.common");

/**
 * @class This class contains functions for button components.
 * @static
 *
 * @deprecated See @JS_NS@.widget.button
 * @private
 */
@JS_NS@._html.button = {
    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.button
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize button.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var widget = @JS_NS@.widget.common.getWidget(props.id);
        if (widget == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Set functions
        widget._domNode.isSecondary = @JS_NS@._html.button.isSecondary;
        widget._domNode.setSecondary = @JS_NS@._html.button.setSecondary;
        widget._domNode.isPrimary = @JS_NS@._html.button.isPrimary;
        widget._domNode.setPrimary = @JS_NS@._html.button.setPrimary;
        widget._domNode.isMini = @JS_NS@._html.button.isMini;
        widget._domNode.setMini = @JS_NS@._html.button.setMini;
        widget._domNode.getDisabled = @JS_NS@._html.button.getDisabled;
        widget._domNode.setDisabled = @JS_NS@._html.button.setDisabled;
        widget._domNode.getVisible = @JS_NS@._html.button.getVisible;
        widget._domNode.setVisible = @JS_NS@._html.button.setVisible;
        widget._domNode.getText = @JS_NS@._html.button.getText;
        widget._domNode.setText = @JS_NS@._html.button.setText;
        widget._domNode.doClick = @JS_NS@._html.button.click;

        return true;
    },

    /**
     * Simulate a mouse click in a button. 
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).click();
     */
    click: function() {
        return this.click();
    },

    /**
     * Get the textual label of a button. 
     *
     * @return {String} The element value.
     * @deprecated Use document.getElementById(id).getProps().value;
     */
    getText: function() {
        return this.getProps().value;
    },

    /**
     * Set the textual label of a button. 
     *
     * @param {String} text The element value
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({value: "text"});
     */
    setText: function(text) {
        return this.setProps({value: text});
    },

    /**
     * Use this function to show or hide a button. 
     *
     * @param {boolean} show true to show the element, false to hide the element
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({visible: boolean});
     */
    setVisible: function(show) {
        if (show == null) {
            return null;
        }
        return this.setProps({visible: show});
    },

    /**
     * Use this function to find whether or not this is visible according to our
     * spec.
     *
     * @return {boolean} true if visible; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().visible;
     */
    getVisible: function() {
        return this.getProps().visible;
    },

    /**
     * Test if button is set as "primary".
     *
     * @return {boolean} true if primary; otherwise, false for secondary
     * @deprecated Use document.getElementById(id).getProps().primary;
     */
    isPrimary: function() {
        return this.getProps().primary;
    },

    /**
     * Set button as "primary".
     *
     * @param {boolean} primary true for primary, false for secondary
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({primary: boolean});
     */
    setPrimary: function(primary) {
        if (primary == null) {
            return null;
        }
        return this.setProps({primary: primary});
    },

    /**
     * Test if button is set as "secondary".
     *
     * @return {boolean} true if secondary; otherwise, false for primary
     * @deprecated Use !(document.getElementById(id).getProps().primary);
     */
    isSecondary: function() {
        return !(this.getProps().primary);
    },

    /**
     * Set button as "secondary".
     *
     * @param {boolean} secondary true for secondary, false for primary
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({primary: false});
     */
    setSecondary: function(secondary) {
        if (secondary == null) {
            return null;
        }
        return this.setProps({primary: !secondary});
    },

    /**
     * Test if button is set as "mini".
     *
     * @return {boolean} true if mini; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().mini;
     */
    isMini: function() {
        return this.getProps().mini;
    },

    /**
     * Set button as "mini".
     *
     * @param {boolean} mini true for mini, false for standard button
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({mini: boolean});
     */
    setMini: function(mini) {
        if (mini == null) {
            return null;
        }
        return this.setProps({mini: mini});
    },

    /**
     * Test disabled state of button.
     *
     * @return {boolean} true if disabled; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().disabled;
     */
    getDisabled: function() {
        return this.getProps().disabled;
    },

    /**
     * Test disabled state of button.
     *
     * @param {boolean} disabled true if disabled; otherwise, false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(disabled) {
        if (disabled == null) {
            return null;
        }
        return this.setProps({disabled: disabled});
    }
};

// Extend for backward compatibility with JSF based components.
@JS_NS@.button = @JS_NS@._html.button;
