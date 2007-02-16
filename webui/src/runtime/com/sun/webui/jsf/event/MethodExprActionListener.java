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
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * <p><strong>MethodExprActionListener</strong> is an {@link ActionListener} that
 * wraps a {@link MethodExpression}. When it receives a {@link ActionEvent}, it executes
 * a method on an object identified by the {@link MethodExpression}.</p>
 */

public class MethodExprActionListener implements ActionListener,
    StateHolder {

    private static final Logger LOGGER =
          Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");
    

    // ------------------------------------------------------ Instance Variables

    private MethodExpression methodExpression = null;
    private boolean isTransient;

    public MethodExprActionListener() { }

    /**
     * <p>Construct a {@link ValueChangeListener} that contains a {@link MethodExpression}.</p>
     */
    public MethodExprActionListener(MethodExpression methodExpression) {

        super();
        this.methodExpression = methodExpression;

    }


    // ------------------------------------------------------- Event Method

    /**
     * @throws NullPointerException {@inheritDoc}     
     * @throws AbortProcessingException {@inheritDoc}     
     */
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {

        if (actionEvent == null) {
            throw new NullPointerException();
        }
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            methodExpression.invoke(elContext, new Object[] {actionEvent});
        } catch (ELException ee) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE,
                           "severe.event.exception_invoking_processaction",
                           new Object[]{
                                 ee.getCause().getClass().getName(),
                                 methodExpression.getExpressionString(),
                                 actionEvent.getComponent().getId()
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
        if (! (otherObject instanceof MethodExprActionListener)) {
            return false;
        }
        
        MethodExprActionListener other = (MethodExprActionListener)otherObject;
        MethodExpression otherMe = other.getMethodExpression();
        //methodExpression should not be null
        return methodExpression.equals(otherMe);
    }

}
