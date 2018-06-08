/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
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
