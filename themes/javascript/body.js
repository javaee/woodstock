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

//<!--
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.body");

dojo.require("webui.@THEME@.common");
dojo.require("webui.@THEME@.cookie");

/**
 * Construct a javascript body object.
 * <p>
 * Them body maintains scroll position and focus.
 * There can be an initial focus element and a default focus element. The
 * initial focus element is identifed by the "focusElementId" argument.
 * This argument is typically null on the first display of the page. If
 * the Body component is not preserving the focus then it may also be null,
 * at other times, since it represents the element id that last received
 * the focus before the request.
 * </p>
 * <p>
 * Whenever the page is displayed and "focusElementId" is null
 * "defaultFocusElementId" will receive the focus if it is not null. This id is
 * defined by the application using the Body "focus" attribute. If the
 * application wants to control the focus in all cases then it should set
 * the Body component "preserveFocus" attribute to "false". The application then
 * explicitly sets the Body "focus" attribute to the element id to receive
 * focus on every request/response.
 * </p>
 * <p>
 * In order to preserve focus across requests, the "focusElementFieldId"
 * element is used to preserve the id of the element that receives the focus
 * last. It is a hidden field that is submitted in the
 * request. See the "com.sun.webui.jsf.util.FocusManager" class for
 * information on how the focus is managed. This field exists in all
 * forms, since that it is the only way to guarantee that the last
 * element that received the focus is sent to the server. Which form
 * is submitted can never be known.
 * </p>
 * @param viewId used to name the scroll cookie
 * @param path a member of the scroll cookie
 * @param defaultFocusElementId the HTML element id that will receive focus.
 * @param focusElementId the id of the element to receive the initial focus.
 * @param focusElementFieldId the id of a hidden field to maintain
 * the id of the last element to have the focus.
 */
webui.@THEME@.body = function(viewId, path, defaultFocusElementId, 
	focusElementId, focusElementFieldId)  {

    /**
     * The id of the HTML element to receive the focus, if the
     * element identified in focusElementFieldId cannot receive the focus.
     */
    this.defaultFocusId = defaultFocusElementId;

    /**
     * The id of a hidden input element whose value is the id
     * of the HTML element to receive the focus. It is set by
     * the focusListener and calls to setFocusBy{Id,Element}.
     */
    this.focusElementFieldId = focusElementFieldId;

    /**
     * The element id to receive the preserved, or initial focus.
     * This member should not be referenced once the onload listener
     * has been invoked. After that point the hidden field will have
     * have the element with the focus. We do this so that two locations
     * do not have to be kept in sync. Developers can just set the focus
     * to the element itself and the focus handler will manage the
     * focus persisitence.
     */
    this.focusElementId = focusElementId;

    /**
     * Create the scroll cookie object.
     */
    this.scrollCookie = new webui.@THEME@.scrollCookie(viewId, path);

    /**
     * @deprecated
     * This funtionality is taken care of by the body onload listener,
     * body.onLoadListener.
     */
    this.setInitialFocus = function() {
	// In old code this method was called as an onload handler.
	// therefore do nothing. We use an onload listener to
	// initialize the focus now.
	// Assume that it was never called by developers.
	// Actually, we really only have to be concerned with
	// early adopters of woodstock, and there aren't many.
	//
        return true; 
    };

    /**
     * Set the focus on element with id "fid".
     * If focus can be set returns true else false.
     */
    this.setFocusById = function(fid) {

	if (fid == null || fid.length == 0) {
	    return false;
	}
	return this.setFocusByElement(window.document.getElementById(fid));
    }

    /**
     * Set the focus on "focusElement".
     * If focus can be set returns true else false.
     */
    this.setFocusByElement = function(focusElement) {

	if (focusElement == null || !this.canAcceptFocus(focusElement)) {
	    return false;
	}

	// canAcceptFocus tests the existence of the "focus" handler.
	// So it is safe to call it outside of a try catch.
	// This should trigger the focus listener.
	//
	focusElement.focus();
	// Assume that this update is performed by the 
	// focus listener. This policy was changed in order to 
	// call "setDefaultFocus" using "setTimeout" in order for
	// javascript to have time to be evaluated, probably for
	// on load processing to complete.
	//
	//this.updateFocusElementField(focusElement);

	return true;
    };

    /**
     * Set the default focus by restoring the focus from a previous
     * request or to the application's chosen default focus element.
     * This method should only  be called once to prevent recursive
     * calls since it calls "focus()" on the focus element.
     * Called currently from the onload listener.
     *
     * If "this.focusElementId" is not null it will receive the focus.
     * If that element can't receive the focus
     * then the application defined "this.defaultFocusId" receives the focus.
     * If that element cannot receive the focus, no focus is set.
     * Returns false if a default focus cannot be established, else true.
     */
    this.setDefaultFocus = function() {

	// Try to set focus to "this.focusElementId". If this fails
	// fallback to the app defined default 
	// "this.defaultFocusElementId", if there is one.
	//
	if (this.setFocusById(this.focusElementId)) {
	    return true;
	}

	// Focus not set try the default.
	//
	if (this.setFocusById(this.defaultFocusId)) {
	    return true;
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
		if (this.setFocusByElement(e)) {
		    return true;
		}
	    }
	}
	// If there is no form, set on the first available link
	//
	for each (var l in window.document.links) {
	    if (this.setFocusByElement(l)) {
		return true;
	    }
	}

	*/

	return false;
    };

    /**
     * According to HTML spec only these elements have
     * "onfocus" which we will equate to "focus".
     * A, AREA, BUTTON, INPUT, LABEL, SELECT, TEXTAREA
     * However just check for a non null "focus" or 
     * catch the exception when trying to reference it.
     * Returns true if the element has a "focus" method, is not
     * disabled, and isVisible, else false.
     */
    this.canAcceptFocus = function(element) {
	var result = false;
	try {
	    result = element != null && element.focus && !element.disabled &&
		element.type != "hidden" &&
		webui.@THEME@.common.isVisible(element.id);
	} catch (e) {
	}
	return result;
    };

    /** 
     * Update the hidden field that maintains the last element to 
     * receive the focus. If the body has multiple forms every form's
     * hidden field is updated with the "focusElement".
     */
    this.updateFocusElementField = function(focusElement) {

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
	    var fform = document.forms[i];

	    // Get the hidden field that maintains the focus element id.
	    // If it exists return it. We know its name is the same
	    // as its id.
	    //
	    try {
		var ffield = fform.elements[this.focusElementFieldId];
		if (ffield != null) {
		    ffield.value = focusElement.id;
		    continue;
		}
	    } catch (e) {
		// the array access of a non existent element
		// probably threw exception so create the field.
	    }
		
	    // If it doesn't exist create it.
	    // and add it to the form.
	    //
	    ffield = document.createElement('input');
	    ffield.type = 'hidden';
	    ffield.id = this.focusElementFieldId;
	    ffield.name = this.focusElementFieldId;
	    ffield.value = focusElement.id;
	    fform.appendChild(ffield);
	}

	return true;
    };

    /**
     * This method is invoked in the onunload event listener
     * body.onUnloadListener
     */
    this.storeScrollPosition = function() {
	try {
	    this.scrollCookie.set(); 
	} catch (e) {
	}
        return true; 
    };

    /**
     * This method is invoked in the onload listener, body.onLoadListener.
     */
    this.setDefaultScrollPosition = function() {

	// # char found, anchor being used. forego scrolling.
	// CR 6342635. 
	//
        if (window.location.href.indexOf('#') != -1) {
	    this.scrollCookie.restore(); 
	}
    };

    this.showAlert = true;

    /**
     * Record the id of the element that has just receivied focus.
     * This is called whenever an element receives the focus.
     * This is set on the document so that the cursor entering the
     * window does not trigger this listener.
     */
    this.focusListener = function(evt) {

	// If it's not an element node just return
	//
	var node = null;
	var isElementNode = false;
	
	// is IE 
	//
	if (document.attachEvent) {
	    node = evt.srcElement;
	
	    // We have to hard code "1" as the Node.ELEMENT_NODE in
	    // ie, because ie does not make the constant accessible.
	    //
	    isElementNode = (node == null ? false : node.nodeType == 1);
	} else {
	    node = evt.target;
	    isElementNode = node.nodeType == Node.ELEMENT_NODE;
	}

	if (isElementNode) {
	    // Note that there is no reliable way to set
	    // focus to some other element if the event element
	    // deemed to receive the focus can't accept the focus.
	    //
	    webui.@THEME@.common.body.updateFocusElementField(node);
	}
	return true;
    };

    /**
     * Set the initial focus and the scroll position.
     */
    this.onLoadListener = function(evt) {

	// register the focus listener first.
	// Then call "setDefaultFocus" using "setTimeout" to
	// allow javascript processing to complete, probably
	// to allow "onload" to complete. The focus listener
	// will update the hidden focus fields as a result
	// of the call to "focus" in setDefaultFocus.
	//

	// Add the focus listener, in the onload to prevent
	// recursive calls from calling setDefaultFocus.
	//
	if (window.document.addEventListener) {
	    window.document.addEventListener('focus', 
		webui.@THEME@.common.body.focusListener, true);
	} else {
	    document.attachEvent('onfocusin', 
		webui.@THEME@.common.body.focusListener);
	}

        // use settimeout to delay setting focus on the default
        // element so that it has been instantiated in the page.
        // Also rely on the focus listener to update the focus
        // hidden fields by catching the 'element.focus()' in
        // setDefaultFocus
        //
	window.setTimeout('webui.@THEME@.common.body.setDefaultFocus()',
	    10);

	// Set up the scroll position after the focus has been
	// restored. Need to make sure that this takes into
	// account the default focus that was just set.
	//
	webui.@THEME@.common.body.setDefaultScrollPosition();
    };

    /**
     * Update the page's scroll position
     */
    this.onUnloadListener = function(evt) {
	webui.@THEME@.common.body.storeScrollPosition();
    };

    /*
     * The focus listener is set on the document so that the cursor 
     * entering the window does not trigger this listener. Unfortunately
     * it is only on the window in IE. IE does not support
     * "addEventListener".
     */
    if (window.document.addEventListener) {
	window.addEventListener('load', this.onLoadListener, true);
	window.addEventListener('unload', this.onUnloadListener, true);
    } else {
	window.attachEvent('onload', this.onLoadListener);
	window.attachEvent('onunload', this.onUnloadListener);
    }
}

//-->
