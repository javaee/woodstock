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

@JS_NS@._dojo.provide("@JS_NS@._base.body");

@JS_NS@._dojo.require("@JS_NS@._base.cookie");
@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@._base.common");

/**
 * @class This class contains functions used to maintain focus and scroll position.
 * <p>
 * There can be an initial focus element and a default focus element. The
 * initial focus element is identifed by the "focusElementId" argument.
 * This argument is typically null on the first display of the page. If
 * the Body component is not preserving the focus then it may also be null,
 * at other times, since it represents the element id that last received
 * the focus before the request.
 * </p><p>
 * Whenever the page is displayed and "focusElementId" is null
 * "defaultFocusElementId" will receive the focus if it is not null. This id is
 * defined by the application using the Body "focus" attribute. If the
 * application wants to control the focus in all cases then it should set
 * the Body component "preserveFocus" attribute to "false". The application then
 * explicitly sets the Body "focus" attribute to the element id to receive
 * focus on every request/response.
 * </p><p>
 * In order to preserve focus across requests, the "focusElementFieldId"
 * element is used to preserve the id of the element that receives the focus
 * last. It is a hidden field that is submitted in the
 * request. See the "com.sun.webui.jsf.util.FocusManager" class for
 * information on how the focus is managed. This field exists in all
 * forms, since that it is the only way to guarantee that the last
 * element that received the focus is sent to the server. Which form
 * is submitted can never be known.
 * </p>
 * @constructor This function is used to construct a body object.
 * @param {String} viewId Used to name the scroll cookie
 * @param {String} path A member of the scroll cookie
 * @param {String} defaultFocusElementId The HTML element id that will receive focus.
 * @param {String} focusElementId The id of the element to receive the initial focus.
 * @param {String} focusElementFieldId The id of a hidden field to maintain
 * the id of the last element to have the focus.
 * @param {boolean} preserveScroll if true or not defined the scroll position is 
 * maintained else scroll is not maintained.
 * @private
 */
@JS_NS@._base.body = function(viewId, path, defaultFocusElementId, 
	focusElementId, focusElementFieldId, preserveScroll)  {
    /**
     * The id of the HTML element to receive the focus, if the
     * element identified in focusElementFieldId cannot receive the focus.
     * @private
     */
    this._defaultFocusId = defaultFocusElementId;

    /**
     * The id of a hidden input element whose value is the id
     * of the HTML element to receive the focus. It is set by
     * the _focusListener and calls to setFocusBy{Id,Element}.
     * @private
     */
    this._focusElementFieldId = focusElementFieldId;

    /**
     * The element id to receive the preserved, or initial focus.
     * This member should not be referenced once the onload listener
     * has been invoked. After that point the hidden field will have
     * have the element with the focus. We do this so that two locations
     * do not have to be kept in sync. Developers can just set the focus
     * to the element itself and the focus handler will manage the
     * focus persisitence.
     * @private
     */
    this._focusElementId = focusElementId;

    /**
     * Flag indicating to preserve scroll.
     * @private
     */
    this._preserveScroll = (preserveScroll == null)
        ? true : new Boolean(preserveScroll).valueOf();

    /**
     * Create the scroll cookie object.
     */
    if (this._preserveScroll == true) {
	this._scrollCookie = new @JS_NS@._base.scrollCookie(viewId, path);
    }

    /**
     * According to HTML spec only these elements have
     * "onfocus" which we will equate to "focus".
     * A, AREA, BUTTON, INPUT, LABEL, SELECT, TEXTAREA
     * However just check for a non null "focus" or 
     * catch the exception when trying to reference it.
     * Returns true if the element has a "focus" method, is not
     * disabled, and is visible, else false.
     *
     * @param {Node} element The DOM node to have focus.
     * @return {boolean} true if DOM Node can have focus.
     * @private
     */
    this._canAcceptFocus = function(element) {
	var result = false;
	try {
	    result = element != null && element.focus && !element.disabled
		&& element.type != "hidden"
		&& @JS_NS@._base.common._isVisible(element.id);
	} catch(err) {}
	return result;
    };

    /**
     * Record the id of the element that has just receivied focus.
     * This is called whenever an element receives the focus.
     * This is set on the document so that the cursor entering the
     * window does not trigger this listener.
     *
     * @param {Event} event The object generated by the focus event.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._focusListener = function(event) {
	// If it's not an element node just return
	//
	var node = null;
	var isElementNode = false;
	
	// is IE 
	//
	if (document.attachEvent) {
	    node = event.srcElement;
	
	    // We have to hard code "1" as the Node.ELEMENT_NODE in
	    // ie, because ie does not make the constant accessible.
	    //
	    isElementNode = (node == null ? false : node.nodeType == 1);
	} else {
	    node = event.target;
	    isElementNode = node.nodeType == Node.ELEMENT_NODE;
	}

	if (isElementNode) {
	    // Note that there is no reliable way to set
	    // focus to some other element if the event element
	    // deemed to receive the focus can't accept the focus.
	    //
	    this._updateFocusElementField(node);
	}
	return true;
    };

    /**
     * Set the initial focus and the scroll position.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._onLoadListener = function() {
	// register the focus listener first.
	// Then call "_setDefaultFocus" using "setTimeout" to
	// allow javascript processing to complete, probably
	// to allow "onload" to complete. The focus listener
	// will update the hidden focus fields as a result
	// of the call to "focus" in _setDefaultFocus.
	//

	// Add the focus listener, in the onload to prevent
	// recursive calls from calling _setDefaultFocus.
	//
        if (@JS_NS@._base.browser._isIe()) {
            @JS_NS@._dojo.connect(document, "onfocusin", this, "_focusListener");
        } else {
            @JS_NS@._dojo.connect(window, "onfocus", this, "_focusListener");
        }

        // Rely on the focus listener to update the focus
        // hidden fields by catching the 'element.focus()' in
        // _setDefaultFocus
        //
        this._setInitialFocus();

	// Set up the scroll position after the focus has been
	// restored. Need to make sure that this takes into
	// account the default focus that was just set.
	//
	return this._setDefaultScrollPosition();
    };

    /**
     * Update the page's scroll position.
     *
     * @param {Event} event The object generated by the onUnload event.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._onUnloadListener = function(event) {
	return this._storeScrollPosition();
    };

    /**
     * Set the default focus to the application's chosen default focus element.
     * This method should only be called once to prevent recursive
     * calls since it calls "focus()" on the focus element.
     * Called currently from the onload listener.
     * <p>
     * If "this._defaultFocusId" is not null it will receive the focus; 
     * otherwise, no focus is set.
     * </p>
     * @return {boolean} false if a default focus cannot be established, else true.
     * @private
     */
    this._setDefaultFocus = function() {
        // HTML elements may not have been added, yet.
        if (this._defaultFocusId != null) {
            var domNode = document.getElementById(this._defaultFocusId);
            if (domNode == null) {
                var _this = this; // Closure magic.
                return setTimeout(function() { _this._setDefaultFocus(); }, 10);
            }

            // Focus not set try the default.
            //
            if (this._setFocusById(this._defaultFocusId)) {
                return true;
            }
        }

	/* For now it doesn't sound like a good idea to ever set
	 * a "heuristic" default focus. It is better for screen readers to start
	 * from the top of the page which we will assume that that
	 * browser starts from there when focus is not set explicitly.
	 * This code can be removed, but left it in case we want to
	 * for some reason.

	// No previously set focus element and no default.
	// Find the first focusable element in the first available form.
	//
	for each (var f in window.document.forms) {
	    for each (var e in f.elements) {
		if (this._setFocusByElement(e)) {
		    return true;
		}
	    }
	}
	// If there is no form, set on the first available link
	//
	for each (var l in window.document.links) {
	    if (this._setFocusByElement(l)) {
		return true;
	    }
	}
	*/
	return false;
    };

    /**
     * This method is invoked in the onload listener, body._onLoadListener.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._setDefaultScrollPosition = function() {
	if (!this._preserveScroll) {
	    return false;
	}
	// # char found, anchor being used. forego scrolling.
	// CR 6342635. 
	//
        if (window.location.href.indexOf('#') == -1) {
	    this._scrollCookie._restore(); 
	} else {
	    // destroy the recent scroll data
	    //
	    this._scrollCookie._reset();
	}
        return true;
    };

    /**
     * Set the initial focus by restoring the focus from a previous
     * request or to the application's chosen default focus element.
     * This method should only be called once to prevent recursive
     * calls since it calls "focus()" on the focus element.
     * Called currently from the onload listener.
     * <p>
     * If "this._focusElementId" is not null it will receive the focus.
     * If that element can't receive the focus then the application defined 
     * "this._defaultFocusId" receives the focus. If that element cannot receive 
     * the focus, no focus is set.
     * </p>
     * @return {boolean} false if focus cannot be established, else true.
     * @private
     */
    this._setInitialFocus = function() {
        // HTML elements may not have been added, yet.
        if (this._focusElementId != null) {
            var domNode = document.getElementById(this._focusElementId);
            if (domNode == null) {
                var _this = this; // Closure magic.
                return setTimeout(function() { _this._setInitialFocus(); }, 10);
            }

            // Try to set focus to "this._focusElementId". If this fails
            // fallback to the app defined default 
            // "this.defaultFocusElementId", if there is one.
            //
            if (this._setFocusById(this._focusElementId)) {
                return true;
            }
        }
        return this._setDefaultFocus();
    };

    /**
     * Set the focus on "focusElement".
     * If focus can be set returns true else false.
     *
     * @param {Node} focusElement The DOM node to have focus.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._setFocusByElement = function(focusElement) {
	if (focusElement == null || !this._canAcceptFocus(focusElement)) {
	    return false;
	}

	// _canAcceptFocus tests the existence of the "focus" handler.
	// So it is safe to call it outside of a try/catch statement.
	// This should trigger the focus listener.
        try {
            // Still need try/catch because _canAcceptFocus doesn't account for 
            // when parent is invisible. For example, the table's sort panel 
            // closes during page submit making focus element invisible.
            focusElement.focus();
        } catch(err) {}

	// Assume that this update is performed by the 
	// focus listener. This policy was changed in order to 
	// call "_setDefaultFocus" using "setTimeout" in order for
	// javascript to have time to be evaluated, probably for
	// on load processing to complete.
	//this._updateFocusElementField(focusElement);
	return true;
    };

    /**
     * Set the focus on element with id "fid".
     * If focus can be set returns true else false.
     *
     * @param {String} fid The id of the DOM node to have focus.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._setFocusById = function(fid) {
	if (fid == null || fid.length == 0) {
	    return false;
	}
	return this._setFocusByElement(document.getElementById(fid));
    };

    /**
     * This method is invoked in the onunload event listener
     * body._onUnloadListener
     *
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._storeScrollPosition = function() {
	if (!this._preserveScroll) {
	    return false;
	}
	try {
	    this._scrollCookie._set(); 
	} catch (e) {
	}
        return true; 
    };

    /** 
     * Update the hidden field that maintains the last element to 
     * receive the focus. If the body has multiple forms every form's
     * hidden field is updated with the "focusElement".
     *
     * @param {Node} focusElement The DOM node to have focus.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    this._updateFocusElementField = function(focusElement) {
	// Don't know if we'll have issues if multiple forms contain
	// an element with the same id. I know getElementById gets
	// confused.
	//

        if (focusElement == null) {
	    return false;
	}
	// Get the form that contains the focus element.
	//
	for (var i = 0;  i < document.forms.length; ++i) {
	    var form = document.forms[i];
            var field = null;

	    // Get the hidden field that maintains the focus element id.
	    // If it exists return it. We know its name is the same
	    // as its id.
	    //
	    try {
		field = form.elements[this._focusElementFieldId];
		if (field != null) {
		    field.value = focusElement.id;
		    continue;
		}
	    } catch (e) {
		// the array access of a non existent element
		// probably threw exception so create the field.
	    }
		
	    // If it doesn't exist create it.
	    // and add it to the form.
	    //
	    field = document.createElement('input');
	    field.type = 'hidden';
	    field.id = this._focusElementFieldId;
	    field.name = this._focusElementFieldId;
	    field.value = focusElement.id;
	    form.appendChild(field);
	}
	return true;
    };

    // The focus listener is set on the document so that the cursor 
    // entering the window does not trigger this listener. 
    this._onLoadListener();

    // If we are not preserving scroll don't add the unload listener.
    if (this._preserveScroll == true) {
        @JS_NS@._dojo.addOnUnload(this, "_onUnloadListener");
    }
};
