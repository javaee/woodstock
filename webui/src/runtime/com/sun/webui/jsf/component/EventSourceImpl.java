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

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent; // For javadoc
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.el.EvaluationException;
import javax.faces.event.ActionListener; // For javadoc
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent; // For javadoc
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import com.sun.webui.jsf.event.MethodExprEventListener;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.event.EventListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELException;


/**
 * The EventSource interface defines a minimal component interface
 * to provide an application with simple GUI events. These GUI
 * events reflect "gestures" made by the user, such as selecting
 * a menu option, or any action that results in a reqeust value
 * representing that action submitted in a request either by 
 * "form.submit" or an XmlHttpRequest.
 *
 * The intention is that specific event components will implement
 * this interface and define their own subclass of FacesEvents.
 * A typical event would include the appropriate phase in which the
 * event should be broadcast, the EventSource component as the 
 * source of the event and the event payload which will be 
 * the converted request value.
 *
 * For example, a Menu component would implement EventSource 
 * and when a Menu gesture is seen in the request, will queue
 * an appropriate event, with the component instance as the source
 * and the result of calling "getConvertedValue" either implemented
 * in the renderer or the component as the Event payload.
 *
 * It is this payload that the application can inspect to indicate
 * the Menu "selection" and act accordingly.
 *
 * In addition EventSource can be implemented to support the 
 * navigational features of an ActionSource.
 */
public abstract class EventSourceImpl extends UIComponentBase implements EventSource {
    private static final Logger LOGGER =
          Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");  

    /**
     * <p>The immediate flag. If set to true, the processing of the menu's event
     * will happen ahead of processing other validators and converters present
     * in the page whose components' immediate attributes are not set to true.
     * Tihs attribute will be meaningful only if a page submit occurs (i.e. submitForm
     * attribute set to true)
     * </p>
     */
    @Property(name="immediate", displayName="Immediate", category="Behavior")    
    private boolean immediate = false;
    private boolean immediate_set = false;    
    /**
     * Return a flag indicating that this event should be
     * broadcast at the next available opportunity, ususally between
     * lifecycle phases i.e. <code>PhaseId.ANY</code>.
     */
    public boolean isImmediate() {

        if (this.immediate_set) {
            return (this.immediate);
        }
        ValueExpression ve = getValueExpression("immediate");
        if (ve != null) {
            try {
                return (Boolean.TRUE.equals(
		    ve.getValue(getFacesContext().getELContext())));
            }
            catch (ELException e) {
                throw new FacesException(e);
            }
        } else {
            return (this.immediate);
        }
    }
    
    /**
     * <p>Set the "immediate execution" flag for this {@link UIComponent}.</p>
     *
     * @param immediate The new immediate execution flag
     */
    public void setImmediate(boolean immediate) {

        // if the immediate value is changing.
        if (immediate != this.immediate) {
            this.immediate = immediate;
        }
        this.immediate_set = true;
    }    

   // -------------------------------------------------------------- Properties
    /**
     * <p>The {@link MethodExpression} that, when invoked, yields a
     * literal outcome value.</p>
     */
    @Property(name="eventExpression", isHidden=true, isAttribute=true, displayName="Menu Event Expression", category="Advanced")    
        @Property.Method(signature="java.lang.String event()")
    private MethodExpression eventExpression = null;
    

    /**
     * Return the {@link MethodExpression} pointing at the application
     * action to be invoked, if this {@link UIComponent} is activated by
     * the user, during <em>PhaseId.ANY</em> in case of <code>immediate</code>
     * or <em>PhaseId.INVOKE_APPLICATION</em> phase by default,
     * or the specific phase as set on the event of the request processing
     * lifecycle,
     */
    public MethodExpression getEventExpression() {
	return eventExpression;
    }

    /**
     * <p>Set the {@link MethodExpression} pointing at the application
     * action to be invoked, if this {@link UIComponent} is activated by
     * the user, during <em>PhaseId.ANY</em> in case of <code>immediate</code>
     * or <em>PhaseId.INVOKE_APPLICATION</em> phase by default,
     * or the specific phase as set on the event of the request processing
     * lifecycle,
     *
     * <p>Any method referenced by such an expression must be public, with
     * a return type of <code>String</code>, and accept no parameters.</p>
     *
     * @param eventExpression The new method expression
     */
    public void setEventExpression(MethodExpression eventExpression) {
	this.eventExpression = eventExpression;
    }
        // -------------------------------------------------- Event Listener Methods


    /**
     * <p>Add a new {@link EventListener} to the set of listeners interested
     * in being notified when {@link EventSource}events s occur.</p>
     *
     * @param listener The {@link EventListener} to be added
     *
     * @throws NullPointerException if <code>listener</code>
     *  is <code>null</code>
     */
    public void addEventListener(EventListener listener) {
        addFacesListener(listener);
    }

    /**
     * <p>Return the set of registered {@link EventListener}s for this
     * {@link EventSource} instance.  If there are no registered listeners,
     * a zero-length array is returned.</p>
     */
    public EventListener[] getEventListeners() {
        // return all ActionListener instances associated with this component
        EventListener listeners[] =
                (EventListener []) getFacesListeners(EventListener.class);
        return listeners;        
    }

      /**
     * <p>Used to specify a method to handle
     * an menu event that is triggered when this
     * component is activated by the user.
     * Value must be a JavaServer Faces EL expression that resolves
     * to a method in a backing bean. The method must take a single parameter
     * that is an event, and its return type must be <code>void</code>.
     * The class that defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface. </p>
     *
     */
    @Property(name="eventListenerExpression", isHidden=true, isAttribute=true, displayName="Menu Event Listener Expression", category="Advanced")
    @Property.Method(signature="void processEvent(com.sun.webui.jsf.event.ValueEvent)")
    private MethodExpression eventListenerExpression;

      /**
     * <p>Used to specify a method to handle
     * an menu event that is triggered when this
     * component is activated by the user.
     * Value must be a JavaServer Faces EL expression that resolves
     * to a method in a backing bean. The method must take a single parameter
     * that is an event, and its return type must be <code>void</code>.
     * The class that defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface. </p>
     *
     */
    public MethodExpression getEventListenerExpression() {
            return this.eventListenerExpression;
    }

    /**
     * <p>Used to specify a method to handle
     * an menu event that is triggered when this
     * component is activated by the user. 
     * Value must be a JavaServer Faces EL expression that resolves
     * to a method in a backing bean. The method must take a single parameter
     * that is an event, and its return type must be <code>void</code>.
     * The class that defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface. </p>
     *
     */
    public void setEventListenerExpression(MethodExpression me) {
        //call thru
        EventListener [] curEventListeners = getEventListeners();
        // see if we need to remove existing eventListener.
        // only need to if this.EventListenerExpression != null (since curMethodExpression won't be null)
        if (null != curEventListeners && this.eventListenerExpression != null) {
            for (int i = 0; i < curEventListeners.length; i++) {
                if (curEventListeners[i] instanceof MethodExprEventListener) {
                    MethodExprEventListener curEventListener = (MethodExprEventListener)curEventListeners[i];
                    MethodExpression curMethodExpression = curEventListener.getMethodExpression();
                    if (this.eventListenerExpression.equals(curMethodExpression)) {
                            removeEventListener(curEventListener);
                            break;
                    }
                }
            }
        }
        if (me == null) {
            this.eventListenerExpression = null;
        }
        else {
            this.eventListenerExpression = me;
            addEventListener(new MethodExprEventListener(this.eventListenerExpression));
        }
    }

    /**
     * <p>Remove an existing {@link EventListener} (if any) from the set of
     * listeners interested in being notified when {@link EventSource} events
     * occur.</p>
     *
     * @param listener The {@link EventListener} to be removed
     *
     * @throws NullPointerException if <code>listener</code>
     *  is <code>null</code>
     */
    public void removeEventListener(EventListener listener) {
        removeFacesListener(listener);
    }
    
    public void processEventExpression() {

        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();

        Object invokeResult;
        String outcome = null;
        MethodExpression me = getEventExpression();
        ELContext elContext = context.getELContext();
        if (me == null) {
	    return;
	}      
	try {
	    if (null != (invokeResult = me.invoke(elContext, null))) {
		outcome = invokeResult.toString();
	    }
	    // else, default to null, as assigned above.
	}  catch (EvaluationException e) {
	    if (LOGGER.isLoggable(Level.SEVERE)) {
		LOGGER.log(Level.SEVERE, e.getMessage(), e);
	    }
	    throw new FacesException
		  (me.getExpressionString() + ": " + e.getMessage(), e);
            }        

        // Retrieve the NavigationHandler instance..
        NavigationHandler navHandler = application.getNavigationHandler();

        // Invoke nav handling..
        navHandler.handleNavigation(context, me.getExpressionString(),
                                    outcome);

        // Trigger a switch to Render Response if needed
	//
        context.renderResponse();        
    }    

    
    /**
     * <p>In addition to to the default {@link UIComponent#broadcast}
     * processing, pass the {@link ActionEvent} being broadcast to the
     * method referenced by <code>actionListener</code> (if any),
     * and to the default {@link ActionListener} registered on the
     * {@link javax.faces.application.Application}.</p>
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

        // Perform standard superclass processing (including calling our
        // ActionListeners)
        super.broadcast(event);
	// If there is an EventExpression treat it like an
	// "ActionExpression" and call the default ApplicationAction
	// listener.
	//
	processEventExpression();
    }    
    
    /**
     * The <code>PhaseId</code> in which events should be broadcast.
     * The default is <code>PhaseId.INVOKE_APPLICATION</code> or
     * <code>PhaseId.ANY</code> if <code>isImmediate</code> returns 
     * <code>true</code>
     */    
    @Property(name="phaseId", displayName="PhaseId", category="Advanced")    
    private PhaseId phaseId = null;
      /**
     * Set the <code>PhaseId</code> in which events should be broadcast.
     * The default is <code>PhaseId.INVOKE_APPLICATION</code> or
     * <code>PhaseId.ANY</code> if <code>isImmediate</code> returns 
     * <code>true</code>
     */
    public void setPhaseId(PhaseId phaseId) {
	this.phaseId = phaseId;
    }

    /**
     * Return the <code>PhaseId</code> of the lifecycle phase in 
     * which events will be queued.
     */
    public PhaseId getPhaseId() {

        if (this.phaseId != null) {
            return (this.phaseId);
        }
        ValueExpression ve = getValueExpression("phaseId");
        if (ve != null) {
            try {
                return ((PhaseId)ve.getValue(getFacesContext().getELContext()));
            }
            catch (ELException e) {
                throw new FacesException(e);
            }
        } else {
            return isImmediate() ? PhaseId.ANY_PHASE : PhaseId.INVOKE_APPLICATION;
        }
    }

    /**
     * <p>Intercept <code>queueEvent</code> and, for {@link ActionEvent}s,
     * mark the phaseId for the event to be
     * <code>PhaseId.APPLY_REQUEST_VALUES</code> if the
     * <code>immediate</code> flag is true,
     * <code>PhaseId.INVOKE_APPLICATION</code> otherwise.</p>
     */

    public void queueEvent(FacesEvent e) {
        if (isImmediate()) {
            e.setPhaseId(PhaseId.ANY_PHASE);
        }
        else {
            e.setPhaseId(getPhaseId());
        }
	super.queueEvent(e);
    }      
    
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;        
        super.restoreState(_context, _values[0]);        
        this.immediate = ((Boolean) _values[1]).booleanValue();
        this.immediate_set = ((Boolean) _values[2]).booleanValue();
        this.eventExpression = (MethodExpression) restoreAttachedState(_context, _values[3]);
        this.eventListenerExpression =(MethodExpression) restoreAttachedState(_context, _values[4]);  
        this.phaseId = (PhaseId)_values[5];
    }    
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[6];   
        _values[0] = super.saveState(_context);        
        _values[1] = this.immediate ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.immediate_set ? Boolean.TRUE : Boolean.FALSE;  
        _values[3] = saveAttachedState(_context, eventExpression);        
        _values[4] = saveAttachedState(_context, eventListenerExpression);  
        _values[5] = this.phaseId;
        return _values;
    }    
}
