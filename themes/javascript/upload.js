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

define(["webui/suntheme/field"], function(field) {
    
    return {    
    /**
     * Use this function to get the HTML input element associated with the
     * Upload component.  
     * @param elementId The element ID of the Upload
     * @return the input element associated with the Upload component 
     */
    getInputElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element.tagName == "INPUT") { 
            return element; 
        } 
        return document.getElementById(elementId + "_com.sun.webui.jsf.upload");
    },

    /**
     * Use this function to disable or enable a upload. As a side effect
     * changes the style used to render the upload. 
     *
     * @param elementId The element ID of the upload 
     * @param show true to disable the upload, false to enable the upload
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {  
        if (elementId == null || disabled == null) {
            // must supply an elementId && state
            return false;
        }
        var input = this.getInputElement(elementId); 
        if (input == null) {
            // specified elementId not found
            return false;
        }
        // Disable field using setDisabled function -- do not hard code styles here.
        return field.setDisabled(input.id, disabled);
    },

    setEncodingType: function(elementId) { 
        var upload = this.getInputElement(elementId); 
        var form = upload; 
        while(form != null) { 
            form = form.parentNode; 
            if(form.tagName == "FORM") { 
                break; 
            } 
        }
        if(form != null) {
            // form.enctype does not work for IE, but works Safari
            // form.encoding works on both IE and Firefox, but does not work for Safari
            // form.enctype = "multipart/form-data";

            // convert all characters to lowercase to simplify testing
            var agent = navigator.userAgent.toLowerCase();
       
            if( agent.indexOf('safari') != -1) {
                // form.enctype works for Safari
                // form.encoding does not work for Safari
                form.enctype = "multipart/form-data"
            } else {
                // form.encoding works for IE, FireFox
                form.encoding = "multipart/form-data"
            }
        }
        return false;
    }
};
});