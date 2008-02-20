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
    public static final ClientType GECKO = new ClientType("gecko") ;
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
    public String toString() {
	return type;
    }
}
