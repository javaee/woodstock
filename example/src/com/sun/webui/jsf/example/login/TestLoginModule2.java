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

package com.sun.webui.jsf.example.login;

/**
 *
 * @author Deep Bhattacharjee
 */

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.security.auth.Subject;
import javax.security.auth.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;

import com.sun.webui.jsf.model.login.LoginCallback;

/**
 * This class implements a JAAS LoginModule interface that 
 * collects user information via a textField and a drop down
 * component. The data is collected and fed to another LoginModule 
 * implementation for verification.
 */

public class TestLoginModule2 implements LoginModule {

    // initial state
    private Subject subject;
    private Map sharedState;

    // the authentication status
    private boolean succeeded;
    private boolean commitSucceeded;

    private CallbackHandler ccbh;

    private String name;
    private String question;
    private String answer;
    
    private TestPrincipal principal = null;
    
    /**
     * Implementation of JAAS LoginModule interface init method.
     *<p>
     * Check the JAAS options for the command path to the PAM verifier
     * executable  and the class name of the presentation class to be
     * used to obtain security information from the end user.
     * In this implementation, we force "use_first_pass" semantics.
     *
     * @param subject the Subject to be authenticated.
     * @param callbackHandler the CallbackHandler from the LoginContext.
     * @param sharedState shared JAAS login module state
     * @param options JAAS options specified in the login
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
	    Map sharedState, Map options) {
 
	this.subject = subject;
	this.ccbh = callbackHandler;
	this.sharedState = sharedState;

	succeeded = false;
	commitSucceeded = false;

    } // initialize

    /**
     * Implementation of JAAS LoginModule interface login method.
     * @return true if the user is authenticated; false if the user
     *              is unknown and PAM login should be ignored.
     *
     * @exception LoginException if the authentication fails.
     *
     * @exception FailedLoginException if this login module
     *          is unable to perform the authentication.
     */
    public boolean login() throws LoginException {

	succeeded = false;

	Callback[] callbacks = new Callback[1];
	
        try {

	    LoginCallback lcb = new LoginCallback();
	    callbacks[0] = lcb;

	    lcb.addCallbackData("name", LoginCallback.TEXT, "Name: ", null); 
            String[] qList = {"Which city were you born?", "What is your favorite color?",
              "Which is your favorite car?", "What is your favority travel destination?",
              "What is your favorite season"};
            lcb.addCallbackListData("questions", LoginCallback.DROPDOWN, "Question: ", qList);
            lcb.addCallbackData("answer", LoginCallback.PASSWORD, "Answer: ", null); 
                        
            
  	    // send it to the handler 
	    ccbh.handle(callbacks);
		
            String name = lcb.getCallbackDataValue("name");
            String question = lcb.getCallbackDataValue("questions");
            String answer = lcb.getCallbackDataValue("answer");
            if (name == null || name.length() == 0 || question == null || 
                answer == null || answer.length() == 0) {
                throw new Exception("Enter Complete Information.");
            }
            
            principal = new TestPrincipal(name);
            sharedState.put("name", name);
            sharedState.put("question", question);
            sharedState.put("answer", answer);
            succeeded = true;
	    
	} catch (Exception ex) {
            ex.printStackTrace();
	    throw new LoginException("TestLoginModule2 :" + ex.getMessage());
	}


	// Return result
	return (succeeded);

    } // login

    /**
     * Implementation of JAAS LoginModule interface commit method.
     *<p>
     * If the login method succeeded, create a new UserRolePrincipal
     * object and new UserRolePassword object and add to the Subject
     * instance.  If the login method did not succeed, just return
     * true (PAM authentication will be ignored).
     *
     * @exception LoginException if the commit fails.
     *
     * @return true if this commit succeeds
     */

    public boolean commit() throws LoginException {

	// Our principal and credential instance was set by the login
	// method if we succeeded.
	if (succeeded) {
            
            if (subject != null) {
	    	if ((principal != null) && (! subject.getPrincipals().contains(principal))) {
		    subject.getPrincipals().add(principal);
		}
	    	commitSucceeded = true;
	    } else {
	        throw new LoginException("TestLoginModule2: commit failed");
	    }
        }
	return (true);
        
    } // commit

    /**
     * Implementation of JAAS LoginModule interface abort method.
     *<p>
     * If our own commit succeeded, we must cleanup the Subject
     * object by calling our logout method.  Reset commit state.
     *
     * @exception LoginException if the abort fails.
     *
     * @return true in all cases
     */
    public boolean abort() throws LoginException {

	if (commitSucceeded) {
	    // overall authentication succeeded and our commit succeeded,
	    // but someone else's commit failed
	    logout();
	}
	
	succeeded = false;
	commitSucceeded = false;
        return (true);

    } // abort

    /**
     * Implementation of JAAS LoginModule interface logout method.
     *<p>
     * Remove the principals and credentials from the Subject object.
     * We remove all principals of type TestPrincipal
     *
     * @exception LoginException if the logout fails.
     *
     * @return true in all cases
     */
    public boolean logout() throws LoginException {

	if (subject != null) {
	    Object [] list = subject.getPrincipals().toArray();
	    for (int i = 0; i < list.length; i++)  {
		if (list[i] instanceof TestPrincipal) {
		    subject.getPrincipals().remove(list[i]);
		    break;
		}
	    }
        }
	return (true);
    }
    
    private class TestPrincipal implements java.security.Principal, 
        java.io.Serializable { 		
        
        private String name = null;
        
        public TestPrincipal(String name) {
            this.name = name;
        }
        
        // Compares this principal to the specified object.
	public boolean equals(Object another) {
            if (another instanceof TestPrincipal) {
                TestPrincipal anotherStr = (TestPrincipal)another;
                if (anotherStr.getName().equals(this.name)) {
                    return true;
                }
            }
            return false;
        }
        
        // Returns the name of this principal.
        public String getName() {
            return this.name;
        }
        
        // Returns a hashcode for this principal.
        public int hashCode() {
            return this.name.hashCode();
        }
          
        // Returns a string representation of this principal.
        public String toString() {
            return this.name;
        }
          
                        
	}
}