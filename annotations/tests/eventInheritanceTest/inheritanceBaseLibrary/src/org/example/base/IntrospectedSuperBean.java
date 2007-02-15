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
package org.example.base;

import javax.el.MethodExpression;

/**
 * This bean provides two events, introspectedAction01 and introspectedAction02,
 * the second of which is associated with the property introspectedListener2Expression.
 */
public class IntrospectedSuperBean {
    
    private MethodExpression introspectedListener2Expression;
    
    public MethodExpression getIntrospectedListener2Expression() {
        return this.introspectedListener2Expression;
    }
    
    public void setIntrospectedListener2Expression(MethodExpression expr) {
        this.introspectedListener2Expression = expr;
    }
    
    public void addIntrospectedActionListener01(IntrospectedActionListener01 actionListener) {
        // ...
    }
    
    public void removeIntrospectedActionListener01(IntrospectedActionListener01 actionListener) {
        // ...
    }
    
    public void addIntrospectedActionListener02(IntrospectedActionListener02 actionListener) {
        // ...
    }
    
    public void removeIntrospectedActionListener02(IntrospectedActionListener02 actionListener) {
        // ...
    }
}
