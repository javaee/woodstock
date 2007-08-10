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

package com.sun.webui.jsf.event;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.StateHolder;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.faces.event.AbortProcessingException;

public class MethodExprEventListener implements EventListener,
    StateHolder {
    
    private static final Logger LOGGER =
          Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");
        
    // ------------------------------------------------------ Instance Variables

    private MethodExpression methodExpression = null;
    private boolean isTransient;
        
    public MethodExprEventListener() {
    }

    /**
     * <p>Construct a {@link ValueChangeListener} that contains a {@link MethodExpression}.</p>
     */
    public MethodExprEventListener(MethodExpression methodExpression) {

        super();
        this.methodExpression = methodExpression;

    }    
    // ------------------------------------------------------- Event Method

    /**
     * @throws NullPointerException {@inheritDoc}     
     * @throws AbortProcessingException {@inheritDoc}     
     */
    public void processEvent(ValueEvent valueEvent) throws AbortProcessingException {

        if (valueEvent == null) {
            throw new NullPointerException();
        }
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            methodExpression.invoke(elContext, new Object[] {valueEvent});
        } catch (ELException ee) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE,
                           "severe.event.exception_invoking_processaction",
                           new Object[]{
                                 ee.getCause().getClass().getName(),
                                 methodExpression.getExpressionString(),
                                 valueEvent.getComponent().getId()
                           });
                StringWriter writer = new StringWriter(1024);
                ee.getCause().printStackTrace(new PrintWriter(writer));
                LOGGER.severe(writer.toString());
            }
            throw new AbortProcessingException(ee.getMessage(), ee.getCause());
        }
    }

        // ------------------------------------------------ Methods from StateHolder


    public Object saveState(FacesContext context) {
        return new Object[] { methodExpression };
    }


    public void restoreState(FacesContext context, Object state) {
        methodExpression = (MethodExpression) ((Object[]) state)[0];
    }


    public boolean isTransient() {
        return isTransient;
    }


    public void setTransient(boolean newTransientValue) {
        isTransient = newTransientValue;
    }
    
    public MethodExpression getMethodExpression() {
        return methodExpression;
    }
    
    public boolean equals(Object otherObject) {
        if (! (otherObject instanceof MethodExprEventListener)) {
            return false;
        }
        
        MethodExprEventListener other = (MethodExprEventListener)otherObject;
        MethodExpression otherMe = other.getMethodExpression();
        //methodExpression should not be null
        return methodExpression.equals(otherMe);
    }
}
