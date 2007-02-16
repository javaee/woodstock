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
 * MethodExprValidator.java
 *
 * Created on August 7, 2006, 1:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.validator;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * <p><strong>MethodExprValidator</strong> is a {@link Validator} that 
 * wraps a {@link MethodExpression}, and it performs validation by executing
 * a method on an object identified by the {@link MethodExpression}.</p>
 * @author mbohm
 */
public class MethodExprValidator implements Validator, StateHolder {
    
    private MethodExpression methodExpression = null;

    public MethodExprValidator() {

        super();
        
    }

    /**
     * <p>Construct a {@link Validator} that contains a {@link MethodExpression}.</p>
     */
    public MethodExprValidator(MethodExpression methodExpression) {

        super();
        this.methodExpression = methodExpression;

    }


    // ------------------------------------------------------- Validator Methods

    /**
     * @throws NullPointerException {@inheritDoc}     
     * @throws ValidatorException {@inheritDoc}     
     */ 
    public void validate(FacesContext context,
                         UIComponent  component,
                         Object       value) throws ValidatorException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        if (value != null) {
            try {
                ELContext elContext = context.getELContext();
                methodExpression.invoke(elContext, new Object[] {context, component, value});
            } catch (ELException ee) {
                Throwable e = ee.getCause();
                if (e instanceof ValidatorException) {
                    throw (ValidatorException) e;
                }
                FacesMessage message = new FacesMessage(ee.getMessage());
                throw new ValidatorException(message, ee.getCause());
            }
        }

    }

    // ----------------------------------------------------- StateHolder Methods
    

    public Object saveState(FacesContext context) {

        Object values[] = new Object[1];
        values[0] = methodExpression;
        return (values);

    }


    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        methodExpression = (MethodExpression)values[0];
    }


    private boolean transientValue = false;


    public boolean isTransient() {

        return (this.transientValue);

    }


    public void setTransient(boolean transientValue) {

        this.transientValue = transientValue;

    }
    
    public MethodExpression getMethodExpression() {
        return methodExpression;
    }
    
    public boolean equals(Object otherObject) {
        if (! (otherObject instanceof MethodExprValidator)) {
            return false;
        }
        
        MethodExprValidator other = (MethodExprValidator)otherObject;
        MethodExpression otherMe = other.getMethodExpression();
        return methodExpression.equals(otherMe);
    }
}
