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

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ComponentUtilities;

import javax.el.MethodExpression;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;

/**
 * Base class for components which need to extend UICommand.
 */
public class WebuiCommand extends UICommand {
    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Flag indicating that event handling for this component should be handled
     * immediately (in Apply Request Values phase) rather than waiting until 
     * Invoke Application phase.
     */
    @Property(name="immediate") 
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
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    /**
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden=true, isAttribute=false)
    public MethodBinding getAction() {
        return super.getAction();
    }
    
    /**
     * {@inheritDoc}
     **/
    //Override to annotate
    @Property(isHidden=true, isAttribute=false)
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
    @Property(name="actionExpression", isHidden=true, displayName="Action Expression")
    @Property.Method(signature="java.lang.String action()")
    public MethodExpression getActionExpression() {
       return super.getActionExpression();
    }

    /**
     * Use the actionListenerExpression attribute to cause the component to fire
     * an event. The value must be an EL expression and it must evaluate to the
     * name of a public method that takes an ActionEvent parameter and returns
     * void.
     */
    @Property(name="actionListenerExpression", displayName="Action Listener Expression", category="Advanced", editorClassName="com.sun.rave.propertyeditors.MethodBindingPropertyEditor")
    @Property.Method(signature="void processAction(javax.faces.event.ActionEvent)")
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
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        
        if (event == null) {
            throw new NullPointerException();
        }

        if (event instanceof ActionEvent) {
            MethodExpression ale = getActionListenerExpression();
            if (ale != null) {
                ale.invoke(getFacesContext().getELContext(), new Object[] { event });
            }
        }
        
        super.broadcast(event);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Lifecycle methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Specialized decode behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processDecodes(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }
        super.processDecodes(context);
    }

    /**
     * <p>Specialized validation behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processValidators(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip procesing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return; // Skip processing for ajax based validation events.
        }
        super.processValidators(context);
    }
   
    /**
     * <p>Specialized model update behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processUpdates(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }
        super.processUpdates(context);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * {@inheritDoc}
     **/
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;

        super.restoreState(context, values[0]);
        actionListenerExpression =(MethodExpression) restoreAttachedState(context, values[1]);
        
    }
    
    /**
     * {@inheritDoc}
     **/
    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, actionListenerExpression);
        
        return values;
    }
    
}
