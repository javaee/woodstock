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

package com.sun.webui.jsf.model.login;

// import javax.servlet.http.HttpServletRequest;
        
/**
 * This interface acts as the connector between the server side presentation
 * layer and the authentication infrastructure. A default implementation
 * for JAAS will be provided. Implementations of this interface will hide
 * the details of how the authentication service is initialized, started 
 * completed or aborted. It will also supply the state of the authentication
 * process at any given time.
 * 
 * This interface assumes the existence of the HTTPServletRequest object.
 * This means that implementations of this interface will work in a servlet environment. 
 * These implementations can be used outside of JSF too.
 */

public interface LoginController {
    
    /**
     * Initialize the authentication process. 
     */
    void initialize(Object requestObj, String serviceName);

    /**
     * Start the authentication process.
     */ 
    void login(Object requestObj, LoginCallback lcb);
    
    /**
     * One the authentication steps are over, commit it. In some implementations
     * this may be a no op. For example, in the JAAS implementation commit and 
     * abort is handled by JAAS itself.
     */
    void commit(Object requestObj);
    
    /**
     * Abort the authentication process. This may be needed in cases
     * where the login presentation supports a cancel button and the user
     * clicks on the button in the middle of the login process.
     */
    void abort(Object requestObj);
    
    /**
     * Get the callback object.
     */
    LoginCallback getCallbackObject();
    
   
    /**
     * Get the current state of the authentication process.
     */
    LoginConstants.LOGINSTATE getLoginState();
    
    /**
     * Get the object representing the final authenticated entity. For 
     * example, in the case of JAAS this would be a Subject object 
     * representing the Principals and credentials created by each of the
     * LoginModule implementations.
     */
    Object getAuthenticatedEntity();

}
