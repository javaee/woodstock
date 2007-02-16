/*
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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */
 

package com.sun.webui.jsf.util;

import javax.faces.context.FacesContext;

/**
 * Methods to manage focus during a request and response.
 * The actual component that receives the focus in the rendered
 * page may not be the component identified by these interfaces.
 * The <code>com.sun.webui.component.Body</code> component can be used
 * explicitly set the component that will receive the focus in the
 * rendered page.
 */
public class FocusManager {

    /**
     * Request parameter element id that maintains
     * the last element that received the focus.
     */
    public static final String FOCUS_FIELD_ID =
	"com_sun_webui_util_FocusManager_focusElementId";

    /**
     * Return the client id of the component that is considered to have
     * the focus during this request. This may not be the component
     * that has the focus when the response is rendered.
     * <p>
     * This interface expects <code>setRequestFocusElementId</code>
     * was called the actual client id of the component
     * that is to receive the focus. This means that the caller was responsible
     * for calling <code>ComplexComponent.getFocusElementId</code> if
     * necessary.
     * </p>
     */
    public static String getRequestFocusElementId(FacesContext context) {
	return (String)
	    context.getExternalContext().getRequestMap().get(FOCUS_FIELD_ID);
    }

    /**
     * Set the client id of the component that is considered to have
     * the focus in this request. This may not be the component
     * that has the focus when the response is rendered.<br/>
     * Returns the last client id that was set in the request map.
     * <p>
     * If <code>clientId</code> is <code>null</code> then the default
     * focus component id, defined on the <code>Body</code> component
     * is used to establish focus.
     * </p>
     * <p>
     * This interface expects the actual <code>clientId</code> of the component
     * that is to receive the focus. This means that the caller is responsible
     * for calling <code>ComplexComponent.getFocusElementId</code> if
     * necessary.
     * </p>
     */
    public static String setRequestFocusElementId(FacesContext context,
	    String clientId) {
	String result = getRequestFocusElementId(context);
	context.getExternalContext().getRequestMap().put(FOCUS_FIELD_ID,
	    clientId);
	return result;
    }
}
