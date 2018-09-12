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
     /**
     * Use this function to get the HTML input or textarea element
     * associated with a TextField, PasswordField, HiddenField or TextArea
     * component. 
     * @param elementId The element ID of the field 
     * @return the input or text area element associated with the field
     * component 
     */
    getInputElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element != null) { 
            if(element.tagName == "INPUT") { 
                return element; 
            }
            if(element.tagName == "TEXTAREA") { 
                return element; 
            } 
        } 
        return document.getElementById(elementId + "_field");
    },

    /**
     * Use this function to get the value of the HTML element 
     * corresponding to the Field component
     * @param elementId The element ID of the Field component
     * @return the value of the HTML element corresponding to the 
     * Field component 
     */
    getValue: function(elementId) { 
        return this.getInputElement(elementId).value; 
    },

    /**
     * Use this function to set the value of the HTML element 
     * corresponding to the Field component
     * @param elementId The element ID of the Field component
     * @param newStyle The new value to enter into the input element
     * Field component 
     */
    setValue: function(elementId, newValue) { 
        this.getInputElement(elementId).value = newValue;
    },

    /** 
     * Use this function to get the style attribute for the field. 
     * The style retrieved will be the style on the span tag that 
     * encloses the (optional) label element and the input element. 
     * @param elementId The element ID of the Field component
     */
    getStyle: function(elementId) { 
        return this.getInputElement(elementId).style; 
    },

    /**
     * Use this function to set the style attribute for the field. 
     * The style will be set on the <span> tag that surrounds the field. 
     * @param elementId The element ID of the Field component
     * @param newStyle The new style to apply
     */
    setStyle: function(elementId, newStyle) { 
        this.getInputElement(elementId).style = newStyle; 
    },

    /**
     * Use this function to disable or enable a field. As a side effect
     * changes the style used to render the field. 
     *
     * @param elementId The element ID of the field 
     * @param show true to disable the field, false to enable the field
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {  
        if (elementId == null || disabled == null) {
            // must supply an elementId && state
            return false;
        }
        var textfield = this.getInputElement(elementId); 
        if (textfield == null) {
            return false;
        }
        var newState = new Boolean(disabled).valueOf();    
        var isTextArea = textfield.className.indexOf("TxtAra_sun4") > -1;
        if (newState) { 
            if(isTextArea) {
                textfield.className = "TxtAraDis_sun4";
            } else {
                textfield.className = "TxtFldDis_sun4";
            }
        } else {
            if(isTextArea) {
                textfield.className = "TxtAra_sun4";
            } else {
                textfield.className = "TxtFld_sun4";
            }
        }
        textfield.disabled = newState;
        return true;
    }   
    };
});