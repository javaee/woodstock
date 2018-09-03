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

define(["webui/suntheme/rbcb"], function(rbcb) {
    
    return {
/**
     * Set the disabled state for the given checkbox element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {
        return rbcb.setDisabled(elementId, disabled,
            "checkbox", "Cb", "CbDis");
    },

    /** 
     * Set the disabled state for all the checkboxes in the check box
     * group identified by controlName. If disabled
     * is set to true, the check boxes are shown with disabled styles.
     *
     * @param controlName The checkbox group control name
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setGroupDisabled: function(controlName, disabled) {    
        return rbcb.setGroupDisabled(controlName,
            disabled, "checkbox", "Cb", "CbDis");
    },

    /**
     * Set the checked property for a checkbox with the given element Id.
     *
     * @param elementId The element Id
     * @param checked true or false
     * @return true if successful; otherwise, false
     */
    setChecked: function(elementId, checked) {
        return rbcb.setChecked(elementId, checked,
            "checkbox");
    }
    };
});