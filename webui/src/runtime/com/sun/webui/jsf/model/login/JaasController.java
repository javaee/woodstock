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

import java.io.IOException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.*;
import javax.servlet.*;

import com.sun.webui.jsf.component.Login;
import com.sun.webui.jsf.model.login.LoginCallback;
import com.sun.webui.jsf.util.LogUtil;
        
/**
  * This class is the thread between presentation layer and the LoginContext
  * class. It will be instantiated by the presentation layer when authentication
  * is about to begin. Once the thread is started it instantiates a JAAS 
  * LoginContext object and starts  the authentication process. It then waits 
  * for the authentication to either complete successfully or end with an error. 
  * The final result of the JAAS authentication process is stored in the session 
  * object and this tread is terminated.
  *
  */

public class JaasController extends Thread {


    // the callbackhandle object.

    private CallbackHandler ccbh; 

    private HttpServletRequest request;
    private HttpSession session;
    private String serviceName;
    private Subject subject;

    /**
     * The constructor for the JAAS controller.
     * @param session  the HttpSession associated with the request
     * @param handler  the Jaas callback handlre class that would be
     * 		used to instantiate the LoginContext object. This handler
     * 		class will be the link between the presentation layer and
     * 		and the underlying JAAS LoginModule implementations.
     * @param loginService  the name of the service that will be used in
     * 		the JAAS configuration file to identify the list of
     * 		LoginModule implementations that are included as part of
     * 		the console authentication process.
     */

    public JaasController(HttpServletRequest req, 
			    CallbackHandler handler, 
			    String loginService,
                            Subject subject) {

	this.request = req;
	this.session = request.getSession(false);
	this.ccbh = handler;
	this.serviceName = loginService;
        this.subject = subject;
    }

    /**
     * This run method instantiates a new JAAS LoginContext
     * and invokes the login() method. The login method picks
     * up the lsit of login modules from the config file and  
     * invokes their login() methods in the order in which they
     * are listed in the config file
     */

    public void run() {

	LoginContext lc = null;

        try {

            lc = new LoginContext(serviceName, subject, ccbh);
            lc.login();

	    // Successful login
	    // Return the new created Subject object to the calling thread
	    // via the session in the proper repository.
	    // Free the wait variables

	    synchronized(this) {
		session.setAttribute(LoginConstants.SUBJECT, lc.getSubject());
                LoginCallback lcb = new LoginCallback();
                lcb.setLoginState(LoginConstants.LOGINSTATE.SUCCESS); //"success");
                
		// send a login complete message thru the pipe
		session.setAttribute(LoginConstants.SERVERCONV, lcb);
		notifyAll();
	    }

	    // remove loginThread from the session
	    session.removeAttribute(LoginConstants.LOGIN_THREAD);
	    session.removeAttribute(LoginCallback.MODULECLASS);
        } catch (Exception e) {

            // Post a message that login failed.
	    // Also post the exception thrown into the session
	    // If the failure is due to session timeout,
	    // do nothing

	    try {
		synchronized(this) {
                    LoginCallback lcb = new LoginCallback();
                    lcb.setMessage("ERROR", "login.errorSummary", 
                        e.getMessage());
                    lcb.setLoginState(LoginConstants.LOGINSTATE.FAILURE); //FAILURE);
                                        
                   
                    // send a login complete message thru the pipe
                    session.setAttribute(LoginConstants.SERVERCONV, lcb);
                    session.setAttribute(LoginConstants.LOGINEXCEPTION, e);
                    session.removeAttribute(LoginConstants.LOGIN_THREAD);
                    notifyAll();
                }
	    } catch (Exception ex) {
	        LogUtil.warning("Write failed" + ex.getMessage());
		// do nothing, session is invalid anyway
	    }
	    LogUtil.severe("AUTHENTICATION FAILED" + e.getMessage());
        }

    }
}
