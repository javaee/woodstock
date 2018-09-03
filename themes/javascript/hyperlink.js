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
     * This function is used to submit a hyperlink.
     *
     * @params hyperlink The hyperlink element
     * @params formId The form id
     * @params params Name value pairs
     */
    submit: function(hyperlink, formId, params) {
        //params are name value pairs but all one big string array
        //so params[0] and params[1] form the name and value of the first param
        var theForm = document.getElementById(formId);
	var oldTarget = theForm.target;
        var oldAction = theForm.action;
        theForm.action += "?" + hyperlink.id + "_submittedField="+hyperlink.id; 
        if (params != null) {
            for (var i = 0; i < params.length; i++) {
             theForm.action +="&" + params[i] + "=" + params[i+1]; 
                i++;
            }
        }
        if (hyperlink.target != null) {
            theForm.target = hyperlink.target;
        }
        theForm.submit();
        // Fix for CR 6469040 - Hyperlink:Does not work correctly in
        // frames environment. 
	if (hyperlink.target != null) {
	    theForm.target = oldTarget;
            theForm.action = oldAction;
        }
        return false;
    },
	
    
   /**
     * Use this function to access the HTML img element that makes up
     * the icon hyperlink. 
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the outter most tag enclosing the HTML img element).
     * @return a reference to the img element. 
     */
    getImgElement: function(elementId) {
        // Image hyperlink is now a naming container and the img element id 
        // includes the ImageHyperlink parent id.
        if (elementId != null) {
            var parentid = elementId;
            var colon_index = elementId.lastIndexOf(":");
            if (colon_index != -1) {
                parentid = elementId.substring(colon_index + 1);
            }
            return document.getElementById(elementId + ":" + parentid + "_image");
        }
        return document.getElementById(elementId + "_image");
    }
    };
});