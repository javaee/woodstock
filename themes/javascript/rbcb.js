/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

define( function() {
    
    return {

    setChecked: function(elementId, checked, type) {
        if (elementId == null || type == null) {
            return false;
        }
        var rbcb = document.getElementById(elementId);
        if (rbcb == null) {
            return false;
        }
        // wrong type
        if (rbcb.type != type.toLowerCase()) {
            return false;
        }
        // Get boolean value to ensure correct data type.
        rbcb.checked = new Boolean(checked).valueOf();
        return true;
    },

    setDisabled: function(elementId, disabled, type, enabledStyle,
            disabledStyle) {
        if (elementId == null || disabled == null || type == null) {
            // must supply an elementId && state && type
            return false;
        }
        var rbcb = document.getElementById(elementId);
        if (rbcb == null) {
            // specified elementId not found
            return false;
        }
        // wrong type
        if (rbcb.type != type.toLowerCase()) {
            return false;
        }
        rbcb.disabled = new Boolean(disabled).valueOf();
        if (rbcb.disabled) {
            if (disabledStyle != null) {
                rbcb.className = disabledStyle;
            }
        } else {
            if (enabledStyle != null) {
                rbcb.className = enabledStyle;
            }
        }
        return true;
    },

    /** 
     * Set the disabled state for all radio buttons with the given controlName.
     * If disabled is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param formName The name of the form containing the element
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setGroupDisabled: function(controlName, disabled, type, enabledStyle,
            disabledStyle) {
        // Validate params.
        if (controlName == null) {
            return false;
        }
        if (disabled == null) {
            return false;
        }
        if (type == null) {
            return false;
        }

        // Get radiobutton group elements.
        var x = document.getElementsByName(controlName)
 
        // Set disabled state.
        for (var i = 0; i < x.length; i++) {
            // Get element.
            var element = x[i];
            if (element == null || element.name != controlName) {
                continue;
            }
            // Validate element type.
            if (element.type.toLowerCase() != type) {
                return false;
            }
            // Set disabled state.
            element.disabled = new Boolean(disabled).valueOf();

            // Set class attribute.
            if (element.disabled) {
                if (disabledStyle != null) {
                    element.className = disabledStyle;
                }
            } else {
                if (enabledStyle != null) {
                    element.className = enabledStyle;
                }
            }
        }
        return true;
    }
};
});