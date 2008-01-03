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
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.Locale;
import java.util.Map;
import java.util.Iterator;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.*;
import javax.servlet.*;


/**
 * The module is the CallbackHandler implementation class for all
 * console LoginModule implementations.
 *
 */

public class LoginCallbackHandler implements CallbackHandler {

    private HttpServletRequest request = null;
    private HttpSession session = null;
    private Locale locale = null;
    private String thisVB = null;
    private String curVB = null;
    //timeout set for 1 hour
    private static final long WAIT_TIMEOUT = 60*60*1000;

    public LoginCallbackHandler(HttpServletRequest req) {
        
	this.request = req;
	this.session = req.getSession();
	this.locale = req.getLocale();
    }

    /**
     * Invoke the Ajax Callback handler method. This method checks to see
     * if data for the callback object already exists in the HTTP session
     * object. Else, it sets up a conversation with the presentation layer
     * by passing the LoginCallback object via the session. The presentation
     * logic responds by returning a map of the data requested.
     */

    public void handle(Callback[] callbacks)
	    throws IOException, UnsupportedCallbackException {
      
	if (callbacks == null) {
	    throw new IOException("Empty or null callback array");
	}

	for (int i = 0; i < callbacks.length; i++) {

	    try {
	        LoginCallback lcb = (LoginCallback)callbacks[i];
                if (lcb != null) {

		    // set the client browser locale
		    lcb.setLocale(locale);
                    lcb = waitUserData(lcb);
                }
            } catch (Exception e) {

                // callback class not of the type expected by this handler
                throw new UnsupportedCallbackException(
                    callbacks[i], "Unrecognized Callback");
            }
	}
    }
    
    /**
     * Write to the HTTP session and wait for a response.
     * There is no timeout as such, this is determined
     * by the session timeout period. If any exception 
     * is generated the session is invalidated.
     */
    private LoginCallback waitUserData(LoginCallback lcb) {

	Thread currentThread = Thread.currentThread();
	String callbackStatus = null;
	
                
	// check if client data is already available, if so
	// no need to prompt and wait for it
	synchronized (currentThread) {
	    try {
		callbackStatus =
		      (String)session.getAttribute(LoginConstants.CLIENTCONV);
		if (callbackStatus != null) {
		    lcb = (LoginCallback)session.getAttribute("dataParms");
                    // is there a need to check the LoginModule for which this
                    // callback object is meant.
                    session.removeAttribute(LoginConstants.CLIENTCONV);
		    return lcb;
		}
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
        }

	synchronized (currentThread) {
            lcb.setLoginState(LoginConstants.LOGINSTATE.CONTINUE); //"continue");
            session.setAttribute(LoginConstants.SERVERCONV, lcb);
            currentThread.notifyAll();
        }

        synchronized (currentThread) {
	    try {
		callbackStatus =
		      (String)session.getAttribute(LoginConstants.CLIENTCONV);
		long now = System.currentTimeMillis();  
		while (callbackStatus == null) {
		    currentThread.wait(WAIT_TIMEOUT);
		    callbackStatus =
		      (String)session.getAttribute(LoginConstants.CLIENTCONV);
		    lcb =(LoginCallback)session.getAttribute("dataParms");
		    if (WAIT_TIMEOUT < (System.currentTimeMillis() -now) ) {;
			break;
		    }
		}
		session.removeAttribute(LoginConstants.CLIENTCONV);
                session.removeAttribute("dataParms");
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	    
	}
	return lcb;
    }
}
