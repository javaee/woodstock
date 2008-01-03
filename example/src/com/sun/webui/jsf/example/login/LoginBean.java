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

import javax.security.auth.Subject;
import com.sun.webui.jsf.example.index.IndexBackingBean;
/**
 *
 * @author Deep Bhattacharjee
 */
public class LoginBean {

    private Subject subject = null;
    public final static String SHOW_LOGIN = "showLoginIndex";
    
    /** Creates a new instance of LoginBean */
    public LoginBean() {
        
    }
    
    /** Action handler when navigating to the login example */
    public String showLogin() {
	return SHOW_LOGIN;
    }
    
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
	_reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    public Subject getValue() {
        return subject;
    }
    
    public void setValue(Subject subject) {
        this.subject = subject;
    }
    
    /** Initial all bean values to their defaults */
    private void _reset() {
	// nothing to add here for now.
    }
}
