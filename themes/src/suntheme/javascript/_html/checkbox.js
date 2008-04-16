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

@JS_NS@._dojo.provide("@JS_NS@._html.checkbox");

@JS_NS@._dojo.require("@JS_NS@._html.rbcb");

/**
 * @class This class contains functions for checkbox components.
 * @static
 * 
 * @deprecated See @JS_NS@.widget.checkbox
 * @private
 */
@JS_NS@._html.checkbox = {
    /**
     * Set the disabled state for the given checkbox element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param {String} elementId The element Id
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) {
        return @JS_NS@._html.rbcb.setDisabled(elementId, disabled,
            "checkbox", "Cb", "CbDis");
    },

    /** 
     * Set the disabled state for all the checkboxes in the check box
     * group identified by controlName. If disabled
     * is set to true, the check boxes are shown with disabled styles.
     *
     * @param {String} controlName The checkbox group control name
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled) {    
        return @JS_NS@._html.rbcb.setGroupDisabled(controlName,
            disabled, "checkbox", "Cb", "CbDis");
    },

    /**
     * Set the checked property for a checkbox with the given element Id.
     *
     * @param {String} elementId The element Id
     * @param checked true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */
    setChecked: function(elementId, checked) {
        return @JS_NS@._html.rbcb.setChecked(elementId, checked,
            "checkbox");
    }
};

// Extend for backward compatibility with JSF based components.
@JS_NS@.checkbox = @JS_NS@._html.checkbox;
