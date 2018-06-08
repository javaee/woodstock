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
 * @author dc151887, John Yeary
 */
public class DropDownMethodExpression extends MethodExpression implements StateHolder {

    private static final long serialVersionUID = 3229154216397152494L;

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

    //FIXME this does not comply with the contract for equals.
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
