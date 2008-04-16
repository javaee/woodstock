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

@JS_NS@._dojo.provide("@JS_NS@._html.hyperlink");

@JS_NS@._dojo.require("@JS_NS@.widget.common");

/**
 * @class This class contains functions for hyperlink components.
 * @static
 *
 * @deprecated See @JS_NS@.widget.hyperlink
 * @private
 */
@JS_NS@._html.hyperlink = {
    /**
     * This function is used to submit a hyperlink.
     * <p>
     * Note: Params are name value pairs but all one big string array so 
     * params[0] and params[1] form the name and value of the first param.
     * </p>
     *
     * @params {Object} hyperlink The hyperlink element
     * @params {String} formId The form id
     * @params {Object} params Name value pairs
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.hyperlink
     */
    submit: function(hyperlink, formId, params) {
        // Need to test widget for tab and common task components. If a widget 
        // does not exist, fall back to the old code.
	var widget = @JS_NS@.widget.common.getWidget(hyperlink.id);
	if (widget == null) {
            // If a widget does not exist, we shall create one in order to call
            // the submit function directly.
            @JS_NS@._dojo.require("@JS_NS@.widget.hyperlink");
            widget = new @JS_NS@.widget.hyperlink({id: hyperlink.id});
	}
        return widget._submitFormData(formId, params);
    },

    /**
     * Use this function to access the HTML img element that makes up
     * the icon hyperlink.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * assigned to the outter most tag enclosing the HTML img element).
     * @return {Node} The HTML image element.
     * @deprecated Use document.getElementById(elementId).getProps().enabledImage;
     */
    getImgElement: function(elementId) {
        // Need to test widget for alarmStatus, jobstatus, and notification phrase
        // components. If a widget does not exist, fall back to the old code.
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        var props = (widget) ? widget.getProps() : null;
        if (props && props.enabledImage) {
            var imgWidget = @JS_NS@.widget.common.getWidget(props.enabledImage.id);
            if (imgWidget != null) {
                return imgWidget._domNode;    
            }
        }

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

// Extend for backward compatibility with JSF based components.
@JS_NS@.hyperlink = @JS_NS@._html.hyperlink;
