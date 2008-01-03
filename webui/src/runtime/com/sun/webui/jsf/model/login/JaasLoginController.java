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
import java.util.Map;
import java.util.HashMap;
import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

// should remove references to the component here and move all constants to
// model.login
import com.sun.webui.jsf.model.login.LoginConstants;
import com.sun.webui.jsf.util.LogUtil;


/**
 * A JAAS implementation of the <code>LoginController</code> interface.
 * This implementation also assumes a servlet environment. For portlets
 * a different implementaion must be used.
 * This implementation instantiates a LoginContext object and fires up
 * the authentication process in its init method. This results in either
 * a failure of some sort (in which case an exception is thrown by the 
 * JAAS) or a new LoginCallback object gets created. The callback object
 * contains prompts that need be displayed on the client side for user
 * data entry. At this point the JAAS thread is waiting for user response.
 * The component should invoke the login() method again once the 
 * user has responded by entering security information on the browser.
 * This causes the JAAS thread waiting for a callback object to continue
 * with the auth process. This continues until the authentication completes
 * cuccessfully or in failure. Upon sucess the Subject object that was initially
 * handed down empty to LoginContext contains the authenticated Principals and
 * Credentials. Upon failure the Subject object is empty. The component can call
 * commit or abort methods to perform clean up operations.
 *
 * @author Deep Bhattacharjee
 */

public class JaasLoginController implements LoginController {
    
  
    private LoginCallbackHandler acbh = null;
    private LoginCallback lcb = null;
    private Subject subject = null;
    private String serviceName = null;
    
    /** Creates a new instance of JaasLoginController */
    public JaasLoginController() {
    }
    
    /**
     * Initialize the authentication process. 
     */
    public void initialize(Object requestObj, String serviceName) {
    
        HttpServletRequest request = (HttpServletRequest)requestObj;
        this.serviceName = serviceName;
        acbh = new LoginCallbackHandler(request);
        subject = new Subject();
        JaasController jc = new JaasController(request, acbh, serviceName, subject);
        Thread loginThread = new Thread(jc);
        loginThread.start();
        request.getSession().setAttribute(LoginConstants.LOGIN_THREAD, 
            loginThread);
        request.getSession().removeAttribute(LoginCallback.MODULECLASS);
        try {
            handleCommunication(request, loginThread);
        } catch (IOException e) {
           abort(request);
        }
    }
    
    /**
     * Start the authentication process.
     */ 
    public void login(Object requestObj, LoginCallback lcb) {
        
        HttpServletRequest request = (HttpServletRequest)requestObj;
        LoginConstants.LOGINSTATE state = lcb.getLoginState();
       
        if (state.equals(LoginConstants.LOGINSTATE.INIT)) {
            acbh = new LoginCallbackHandler(request);
            subject = new Subject();
            JaasController jc = new JaasController(request,
                acbh, serviceName, subject);
            Thread loginThread = new Thread(jc);
            loginThread.start();
            request.getSession().setAttribute(LoginConstants.LOGIN_THREAD, loginThread);
            request.getSession().removeAttribute(LoginCallback.MODULECLASS);
            try {
                handleCommunication(request, loginThread);
            } catch (IOException e) {
                abort(request);                
            }
            
        } else if (state.equals(LoginConstants.LOGINSTATE.CONTINUE)) {
		
            Thread loginThread = (Thread) request.getSession()
                .getAttribute(LoginConstants.LOGIN_THREAD);
            // String loginClass = this.getCallbackObject().getModuleClassName();
            
            if (loginThread != null) {
	        synchronized(loginThread) {
		    request.getSession().setAttribute(LoginConstants.CLIENTCONV,
		        "dataEntryComplete");
		    loginThread.notifyAll();
		    request.getSession().setAttribute("dataParms", lcb);
                    request.getSession().setAttribute(LoginConstants.CLIENTCONV,
		        "dataEntryComplete");
		    }

            } else {
                    
                acbh = new LoginCallbackHandler(request);
                subject = new Subject();
                JaasController jc =
                    new JaasController(request, 
		    acbh, serviceName, subject);
                request.getSession().setAttribute("dataParms", lcb);
                request.getSession().setAttribute(LoginConstants.CLIENTCONV,
                    "dataEntryComplete");
                loginThread = new Thread(jc);
                loginThread.start();
                request.getSession().setAttribute(LoginConstants.LOGIN_THREAD,
                    loginThread);
            }
            try {
                handleCommunication(request, loginThread);
            } catch (IOException e) {
                abort(request);
            }
            
        } else  {
            LogUtil.severe("Login component has reached an invalid state: " + 
                    state);
        }
        
    }
    
    /**
     * One the authentication steps are over, commit it. In some implementations
     * this may be a no op. For example, in the JAAS implementation commit and 
     * abort is handled by JAAS itself.
     */
    public void commit(Object requestObj) {
        HttpServletRequest request = (HttpServletRequest)requestObj;
    }
    
    /**
     * Abort the authentication process. This may be needed in cases
     * where the login presentation supports a cancel button and the user
     * clicks on the button in the middle of the login process.
     */
    public void abort(Object requestObj) {
        HttpServletRequest request = (HttpServletRequest)requestObj;
    }
    
    /**
     * Get the callback object.
     */
    
    public LoginCallback getCallbackObject() {
        return lcb;
    }
    
    /*public void setCallbackObject(LoginCallback callback) {
        this.lcb = callback;
    }*/
    
    /**
     * Get the current state of the authentication process.
     */
    public LoginConstants.LOGINSTATE getLoginState() {
        return lcb.getLoginState();
    }
    
    /**
     * Get the object representing the final authenticated entity. For 
     * example, in the case of JAAS this would be a Subject object 
     * representing the Principals and credentials created by each of the
     * LoginModule implementations.
     */
    public Object getAuthenticatedEntity() {
        if (getLoginState().equals(LoginConstants.LOGINSTATE.SUCCESS)) { //.SUCCESS)) {
            return subject;
        } else {
            return null;
        }
    }

    /*
     * This method waits for a response from the authentication service.
     * The response has to be in the form of a LoginCallback object. 
     * The callback object consists of the following data:
     * a) login id
     * b) login state
     * c) An array of arrays. Each array is of length atmost 2
     *    possibly representing a name and a value or just a value.
     * d) Data to be displayed as alert
     * e) A set of keys that will be used as keys while sending user data
     *    back to the server for authentication.
     * Depending on the state of the authentication process either set the
     * component state as being valid or simply go to the render response
     * phase.
     */
    private void handleCommunication(HttpServletRequest request, 
            Thread loginThread) throws IOException{

        HttpSession session = request.getSession();
        session = (HttpSession)request.getSession(true);
        lcb = getCallbackResponse(loginThread, session);
        
        // If callback returns success extract invoke the steps for
        // successful authentication and then go through the updateModel
        // phase. Otherwise, set the callback object and render the
        // component.
	if (lcb != null) {
            LoginConstants.LOGINSTATE loginState = lcb.getLoginState();
            if (loginState.equals(LoginConstants.LOGINSTATE.FAILURE)) {
                 Exception ex = 
                     (Exception)(session.getAttribute(LoginConstants.LOGINEXCEPTION));
                 if (ex != null) {
                     lcb.setMessage("ERROR", "login.errorSummary", "login.errorDetail");
                 }
                 // set the instance variables to null
                 subject = null;
                 acbh = null;
                 
            } else if (loginState.equals(LoginConstants.LOGINSTATE.SUCCESS)) {
                acbh = null;
                
            } else {  // set the login state to continue 
                lcb.setLoginState(LoginConstants.LOGINSTATE.CONTINUE);
            }
        } else {
            LogUtil.warning("null callback object");
        }        
    }

    /**
     * This method waits for input from Callback objects when
     * the LognModules are executing.
     * It looks for the "serverconv" attribute in the session to
     * see if the Loginmodules have succeeded or need some more data
     * from the user.
     */
    private LoginCallback getCallbackResponse(Thread loginThread,
                                         HttpSession session) {
        try {
            synchronized (loginThread) {
                LoginCallback callbackStatus =
                    (LoginCallback) session.getAttribute(LoginConstants.SERVERCONV);

                while (callbackStatus == null) {
                    loginThread.wait();
                    callbackStatus =
                        (LoginCallback) session.getAttribute(LoginConstants.SERVERCONV);
                }
                session.removeAttribute(LoginConstants.SERVERCONV);
                return callbackStatus;
            }
        } catch (Exception ex) {
            LogUtil.severe("Exception iwaiting for server", ex);
            return null;
        }
    }
 }
