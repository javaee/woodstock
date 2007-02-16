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
 * DropDownMethodExpression.java
 *
 * Created on June 29, 2006, 10:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.el;

import javax.faces.component.StateHolder;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.ELContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author dc151887
 */
public class DropDownMethodExpression extends MethodExpression  implements StateHolder{
    
    /** Creates a new instance of DropDownMethodExpression */
    public DropDownMethodExpression() {
    }
    
    private transient String value = null;
    
    public void setValue(String value) { 
        this.value = value; 
    } 
    
    public MethodInfo getMethodInfo(ELContext context) {
        return null;
    }
    
    public Object invoke(ELContext context, Object[] params) {
        return value;
    }
    
    public String getExpressionString() {
        return value;
    }
    
    public boolean isLiteralText() {
        return true;
    }
    
    public boolean equals(Object obj) {
        return true;
    }

    public int hashCode() {
        return value.hashCode();
    }
    
    private boolean transientFlag = false;


    public boolean isTransient() {
        return this.transientFlag;
    }


    public void setTransient(boolean transientFlag) {
        this.transientFlag = transientFlag;
    }


    public void restoreState(FacesContext context, Object state) {
        this.value = (String) state;
    }


    public Object saveState(FacesContext context) {
        return this.value;
    }
}
