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
import javax.faces.event.ValueChangeListener;

/**
 * <p><strong>MethodExprActionListener</strong> is an {@link ActionListener} that
 * wraps a {@link MethodExpression}. When it receives a {@link ActionEvent}, it executes
 * a method on an object identified by the {@link MethodExpression}.</p>
 */
 //FIXME add hashcode
public class MethodExprActionListener implements ActionListener,
        StateHolder {

    private static final Logger LOGGER =
            Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");
    // ------------------------------------------------------ Instance Variables
    private MethodExpression methodExpression = null;
    private boolean isTransient;

    public MethodExprActionListener() {
    }

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
            methodExpression.invoke(elContext, new Object[]{actionEvent});
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

        return new Object[]{methodExpression};

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


    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof MethodExprActionListener)) {
            return false;
        }

        MethodExprActionListener other = (MethodExprActionListener) otherObject;
        MethodExpression otherMe = other.getMethodExpression();
        //methodExpression should not be null
        return methodExpression.equals(otherMe);
    }
}
