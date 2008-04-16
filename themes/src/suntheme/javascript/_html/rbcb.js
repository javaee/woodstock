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

@JS_NS@._dojo.provide("@JS_NS@._html.rbcb");

@JS_NS@._dojo.require("@JS_NS@.widget.common");

/**
 * @class This class contains functions for checkbox and radio button components.
 * @static
 *
 * @deprecated See @JS_NS@.widget.rbcbGroup
 * @private
 */
@JS_NS@._html.rbcb = {
    /**
     * 
     * @param {String} elementId The element Id.
     * @param {boolean} checked true or false
     * @param {String} type
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */ 
    setChecked: function(elementId, checked, type) {
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.setProps({checked: checked});
        }
        return null; 
    },

    /**
     *
     * @param {String} elementId The element Id.
     * @param {boolean} disabled true or false
     * @param {String} type
     * @param {String} enabledStyle
     * @param {String} disabledStyle
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */ 
    setDisabled: function(elementId, disabled, type, enabledStyle,
            disabledStyle) {
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.setProps({disabled: disabled});
        }
        return null; 
    },

    /** 
     * Set the disabled state for all radio buttons with the given controlName.
     * If disabled is set to true, the element is shown with disabled styles.
     *
     * @param {String} elementId The element Id
     * @param {String} formName The name of the form containing the element
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled, type, enabledStyle,
            disabledStyle) {
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.setProps({disabled: disabled});
        }
        return null;
    }
};

// Extend for backward compatibility with JSF based components.
@JS_NS@.rbcb = @JS_NS@._html.rbcb;
