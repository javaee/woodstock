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

package com.sun.webui.jsf.component;

import javax.el.MethodExpression;
import javax.faces.component.UICommand;
import com.sun.faces.annotation.Property;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;

/**
 *
 * @author mbohm
 */
public class WebuiCommand extends UICommand {

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name = "id")
    @Override
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Flag indicating that event handling for this component should be handled
     * immediately (in Apply Request Values phase) rather than waiting until 
     * Invoke Application phase.
     */
    @Property(name = "immediate")
    @Override
    public void setImmediate(boolean immediate) {
        super.setImmediate(immediate);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name = "rendered")
    @Override
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    /**
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden = true, isAttribute = false)
    @Override
    public MethodBinding getAction() {
        return super.getAction();
    }

    /**
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden = true, isAttribute = false)
    @Override
    public MethodBinding getActionListener() {
        return super.getActionListener();
    }

    /**
     * MethodExpression representing the application action to invoke when this
     * component is activated by the user. The expression must evaluate to a 
     * either a String or a public method that takes no parameters, and returns 
     * a String (the logical outcome) which is passed to the NavigationHandler 
     * for this application.
     */
    @Property(name = "actionExpression", isHidden = true, displayName = "Action Expression")
    @Property.Method(signature = "java.lang.String action()")
    @Override
    public MethodExpression getActionExpression() {
        return super.getActionExpression();
    }
    /**
     * Use the actionListenerExpression attribute to cause the component to fire
     * an event. The value must be an EL expression and it must evaluate to the
     * name of a public method that takes an ActionEvent parameter and returns
     * void.
     */
    @Property(name = "actionListenerExpression", displayName = "Action Listener Expression",
    category = "Advanced", editorClassName = "com.sun.rave.propertyeditors.MethodBindingPropertyEditor")
    @Property.Method(signature = "void processAction(javax.faces.event.ActionEvent)")
    private MethodExpression actionListenerExpression;

    /**
     * <p>Simply return the stored <code>actionListenerExpression</code>.
     * The <code>broadcast</code> method is overridden in 
     * <code>WebuiCommand</code> to invoke the 
     * <code>actionListenerExpression</code>.</p>
     */
    public MethodExpression getActionListenerExpression() {
        return this.actionListenerExpression;
    }

    /**
     * <p>Simply store the <code>actionListenerExpression</code>.
     * The <code>broadcast</code> method is overridden in 
     * <code>WebuiCommand</code> to invoke the 
     * <code>actionListenerExpression</code>.</p>
     */
    public void setActionListenerExpression(MethodExpression me) {
        this.actionListenerExpression = me;
    }

    /**
     * <p>Before calling <code>super.broadcast</code>,
     * pass the {@link ActionEvent} being broadcast to the
     * method referenced by <code>actionListenerExpression</code> (if any).</p>
     *
     * @param event {@link FacesEvent} to be broadcast
     *
     * @throws AbortProcessingException Signal the JavaServer Faces
     *  implementation that no further processing on the current event
     *  should be performed
     * @throws IllegalArgumentException if the implementation class
     *  of this {@link FacesEvent} is not supported by this component
     * @throws NullPointerException if <code>event</code> is
     * <code>null</code>
     */
    @Override
    public void broadcast(FacesEvent event) throws AbortProcessingException {

        if (event == null) {
            throw new NullPointerException();
        }

        if (event instanceof ActionEvent) {
            MethodExpression ale = getActionListenerExpression();
            if (ale != null) {
                ale.invoke(getFacesContext().getELContext(), new Object[]{event});
            }
        }

        super.broadcast(event);
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;

        super.restoreState(context, values[0]);
        actionListenerExpression = (MethodExpression) restoreAttachedState(context, values[1]);

    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, actionListenerExpression);

        return values;
    }
}
