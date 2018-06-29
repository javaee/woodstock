/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2018 Oracle and/or its affiliates. All rights reserved.
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
import javax.servlet.http.Cookie;


/**
 *  <p>	Methods for working with cookies.</p>
 *
 *  @author Ken Paulsen (kenapaulsen@gmail.com)
 */
public class CookieUtils {

    /**
     *	<p> Gets the requested cookie name.  This method will ensure invalid
     *	    characters are not used in the name.  The cooresponding
     *	    <code>setCookieValue</code> call should be used to ensure proper
     *	    setting / retrieval of your cookie. (NOTE: there is a JS version
     *	    of these methods as well.)</p>
     */
    public static Cookie getCookieValue(FacesContext context, String name) {
	name = CookieUtils.getValidCookieName(name);
        return (Cookie) context.getExternalContext().getRequestCookieMap().get(name);
    }

    /**
     *	<p> Sets the specified cookie name / value.  This method will ensure
     *	    invalid characters are not used in the name.  The cooresponding
     *	    <code>getCookieValue</code> call should be used to ensure proper
     *	    setting / retrieval of your cookie. (NOTE: there is a JS version
     *	    of these methods as well.)</p>
     */
    public static void setCookieValue(FacesContext context, String name, String value) {
	// FIXME: not quite implemented...
	name = CookieUtils.getValidCookieName(name);
        context.getExternalContext().getRequestCookieMap().put(name, value);
    }

    /**
     *	<p> Ensure we use a RFC 2109 compliant cookie name.</p>
     */
    public static String getValidCookieName(String name) {
	for (char ch : badCookieChars) {
	    name = name.replace(ch, '_');
	}
	return name;
    }

    // Characters not allowed to be part of a cookie name (RFC 2109)
    private static final char badCookieChars[] = {
	    '(', ')', '<', '>', '@', ',', ';', ':', '\\',
	    '\'', '/', '[', ']', '?', '=', '{', '}', ' ', '\t'};
}
