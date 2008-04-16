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

@JS_NS@._dojo.provide("@JS_NS@._html.field");

@JS_NS@._dojo.require("@JS_NS@.widget.common");

/**
 * @class This class contains functions for field components.
 * @static
 *
 * @deprecated See @JS_NS@.widget.field
 * @private
 */
@JS_NS@._html.field = {
    /**
     * Use this function to get the HTML input or textarea element
     * associated with a TextField, PasswordField, HiddenField or TextArea
     * component.
     *
     * @param {String} elementId The element ID of the field 
     * @return {Node} the input or text area element associated with the field component
     * @deprecated Use document.getElementById(elementId).getInputElement()
     */
    getInputElement: function(elementId) {
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.getInputElement();
        }
        return null;
    },

    /**
     * Use this function to get the value of the HTML element 
     * corresponding to the Field component.
     *
     * @param {String} elementId The element ID of the Field component
     * @return {String} the value of the HTML element corresponding to the Field component 
     * @deprecated Use document.getElementById(id).getProps().value;
     */
    getValue: function(elementId) {
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.getProps().value;
        }
        return null;
    },

    /**
     * Use this function to set the value of the HTML element 
     * corresponding to the Field component
     *
     * @param {String} elementId The element ID of the Field component
     * @param {String} newValue The new value to enter into the input element Field component 
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({value: "text"});
     */
    setValue: function(elementId, newValue) {
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.setProps({value: newValue});
        }
        return null;
    },

    /** 
     * Use this function to get the style attribute for the field. 
     * The style retrieved will be the style on the span tag that 
     * encloses the (optional) label element and the input element.
     *
     * @param {String} elementId The element ID of the Field component
     * @return {String} The style property of the field.
     * @deprecated Use document.getElementById(id).getProps().style;
     */
    getStyle: function(elementId) {
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.getProps().style;
        }
        return null;
    },

    /**
     * Use this function to set the style attribute for the field. 
     * The style will be set on the <span> tag that surrounds the field.
     *
     * @param {String} elementId The element ID of the Field component
     * @param {String} newStyle The new style to apply
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({style: newStyle});
     */
    setStyle: function(elementId, newStyle) { 
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.setProps({style: newStyle});
        }
        return null;
    },

    /**
     * Use this function to disable or enable a field. As a side effect
     * changes the style used to render the field. 
     *
     * @param {String} elementId The element ID of the field 
     * @param {boolean} newDisabled true to disable the field, false to enable the field
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, newDisabled) {  
        if (newDisabled == null) {
            return null;
        }
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.setProps({disabled: newDisabled});
        }
        return null;
    }
};

// Extend for backward compatibility with JSF based components.
@JS_NS@.field = @JS_NS@._html.field;
