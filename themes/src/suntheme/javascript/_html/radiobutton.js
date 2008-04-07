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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@._html.radiobutton");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@._html.rbcb");

/**
 * @class This class contains functions for radioButton components.
 * @static
 *
 * @deprecated See webui.@THEME_JS@.widget.radioButton
 * @private
 */
webui.@THEME_JS@._html.radiobutton = {
    /**
     * Set the disabled state for the given radiobutton element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param {String} elementId The element Id.
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) {    
        return webui.@THEME_JS@._html.rbcb.setDisabled(elementId, disabled, 
            "radio", "Rb", "RbDis");
    },

    /**
     * Set the disabled state for all the radio buttons in the radio button
     * group identified by controlName. If disabled
     * is set to true, the check boxes are displayed with disabled styles.
     *
     * @param {String} controlName The radio button group control name
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.@THEME_JS@._html.rbcb.setGroupDisabled(controlName, disabled, 
            "radio", "Rb", "RbDis");
    },

    /**
     * Set the checked property for a radio button with the given element Id.
     *
     * @param {String} elementId The element Id
     * @param {boolean} checked true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */
    setChecked: function(elementId, checked) {
        return webui.@THEME_JS@._html.rbcb.setChecked(elementId, checked, "radio");
    }
};

// Extend for backward compatibility.
webui.@THEME_JS@.radiobutton = webui.@THEME_JS@._html.radiobutton;
