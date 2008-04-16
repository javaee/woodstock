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

@JS_NS@._dojo.provide("@JS_NS@._html.upload");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@._base.common");

/**
 * @class This class contains functions for upload components.
 * @static
 * @private
 */
@JS_NS@._html.upload = {
    /**
     * Use this function to get the HTML input element associated with the
     * Upload component.  
     * @param {String} elementId The client id of the Upload component
     * @return {Node} the input element associated with the Upload component
     * else null if elementId is null or "".
     */
    getInputElement: function(elementId) { 
        if (elementId == null || elementId == "") {
	    return null;
	}

	// The upload component MUST always render the input element
	// with the following suffix on the id 
	// "_com.sun.webui.jsf.upload".
	// This "binds" this version of the component to this theme
	// version.
	// This will change when "field" becomes a widget.
	//
        var element = document.getElementById(elementId + 
            "_com.sun.webui.jsf.upload");
        if (element && element.tagName == "INPUT") { 
            return element; 
        } else {
	    return null;
	}
    },

    /**
     * Create a hidden field with id "preservePathId" and add a listener
     * to the upload's input element, "uploadId". The listener is
     * is added for the onchange event of the upload's input field,
     * see preservePathListener.
     *
     * @param {String} uploadId The client id of the upload component.
     * @param {String} preservePathId
     * @return {boolean} true if the hidden element is created and a listener is
     * added, else false.
     * @private
     */
    _preservePath: function(uploadId, preservePathId) {
	if (uploadId == null || uploadId == "" ||
		preservePathId == null || preservePathId == "") {
	    return false;
	}

	// If there is no upload component, don't do anything.
	// I'm not sure if there is a timing issue here.
	//
	var uploadElement = @JS_NS@._html.upload.getInputElement(uploadId);
	if (uploadElement == null) {
	    return false;
	}
	var theForm = uploadElement.form;

	// Create the change listener.
	// The event target/srcElement is the upload input element
	// its value is the changed value, save it in the 
	// preservePath hidden field.
	//
	var onChangeListener = function(evt) {
	    // Is IE
	    if (document.attachEvent) {
		node = evt.srcElement;
	    } else {
		node = evt.target;
	    }
	    // node is the upload input element
	    //
	    var preservePath = null;
	    try {
		preservePath = theForm.elements[preservePathId];
	    } catch (e) {
	    }

	    // If the hidden field isn't there create it and assign
	    // the node's value
	    //
	    if (preservePath != null) {
		preservePath.value = node.value;
	    } else {
		@JS_NS@._base.common._insertHiddenField(preservePathId, 
                    node.value, theForm);
	    }
	    return true;
	};

	if (uploadElement.addEventListener) {
	    uploadElement.addEventListener('change', onChangeListener, true);
	} else {
	    uploadElement.attachEvent('onchange', onChangeListener);
	}
	return true;
    },

    /**
     * Use this function to disable or enable a upload. As a side effect
     * changes the style used to render the upload. 
     *
     * @param {String} elementId The client id of the upload component.
     * @param {boolean} disabled true to disable the upload, false to enable the upload
     * @return {boolean} true if successful; otherwise, false.
     */
    setDisabled: function(elementId, disabled) {
        if (elementId == null || elementId == "" || 
		disabled == null || disabled == "") {
            // must supply an elementId && state
            return false;
        }
        var input = @JS_NS@._html.upload.getInputElement(elementId); 
        if (input == null) {
            // specified elementId not found
            return false;
        }
        input.disabled = disabled;
	return true;
    },

    /**
     * Set the encoding type of the form to "multipart/form-data".
     * 
     * @param {String} elementId The client id of the upload component.
     * @return {boolean} true if encoding type can be set, else false.
     * @private
     */
    _setEncodingType: function(elementId) { 
	if (elementId == null || elementId == "") {
	    return false;
	}

        var upload = @JS_NS@._html.upload.getInputElement(elementId); 
        var form = upload != null ? upload.form : null;
	if (form != null) {
            // form.enctype does not work for IE, but works Safari
            // form.encoding works on both IE and Firefox
	    //
            if (@JS_NS@._base.browser._isSafari()) {
                form.enctype = "multipart/form-data";
            } else {
                form.encoding = "multipart/form-data";
            }
	    return true;
        }
	return false;
    }
};

// Extend for backward compatibility with JSF based components.
@JS_NS@.upload = @JS_NS@._html.upload;
