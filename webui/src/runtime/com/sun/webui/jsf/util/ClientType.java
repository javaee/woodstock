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

/*
 * ClientType.java
 *
 * Created on December 16, 2004, 8:19 AM
 */

package com.sun.webui.jsf.util;

/**
 * This class provides a typesafe enumeration of value types (see also 
 * ClientTypeEvaluator). The ClientTypeEvaluator and the
 * ClientTypes are helper classes for UIComponents which accept
 * value bindings that can be either single objects or a collection of 
 * objects (for example, an array). Typically, these components have
 * to process input differently depending on the type of the value 
 * object.
 *@see com.sun.webui.jsf.util.ClientSniffer
 *
 */
public class ClientType {

    private String type;

    /** Client type is Mozilla 6 or higher */
    public static final ClientType GECKO = new ClientType("gecko");
    /** Client type is IE7 or higher */
    public static final ClientType IE7 = new ClientType("ie7");
    /** Client type is IE6 */
    public static final ClientType IE6 = new ClientType("ie6");
    /** Client type is IE 5, version 5.5 or higher */
    public static final ClientType IE5_5 = new ClientType("ie5.5");
     /** Client type is safari */
    public static final ClientType SAFARI = new ClientType("safari"); 
    /** Client type is not IE 5.5+ or gecko. */
    public static final ClientType OTHER = new ClientType("default");

    private ClientType(String s) {
	type = s;
    }

    /**
     * Get a String representation of the action
     * @return A String representation of the value type.
     */
    @Override
    public String toString() {
	return type;
    }
}
