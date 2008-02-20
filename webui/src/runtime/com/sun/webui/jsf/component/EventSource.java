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
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.event.EventListener;


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
public interface EventSource {
  /**
     * Return a flag indicating that this event should be
     * broadcast at the next available opportunity, ususally between
     * lifecycle phases i.e. <code>PhaseId.ANY</code>.
     */
    public boolean isImmediate();


    /**
     * <p>Set the "immediate execution" flag for this {@link UIComponent}.</p>
     *
     * @param immediate The new immediate execution flag
     */
    public void setImmediate(boolean immediate);


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
    public void addEventListener(EventListener listener);


    /**
     * <p>Return the set of registered {@link EventListener}s for this
     * {@link EventSource} instance.  If there are no registered listeners,
     * a zero-length array is returned.</p>
     */
    public abstract EventListener[] getEventListeners();


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
    public void removeEventListener(EventListener listener);


    // -------------------------------------------------------------- Properties

    /**
     * Return the {@link MethodExpression} pointing at the application
     * action to be invoked, if this {@link UIComponent} is activated by
     * the user, during <em>PhaseId.ANY</em> in case of <code>immediate</code>
     * or <em>PhaseId.INVOKE_APPLICATION</em> phase by default,
     * or the specific phase as set on the event of the request processing
     * lifecycle,
     */
    public MethodExpression getEventExpression();

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
    public void setEventExpression(MethodExpression eventExpression);
    

}
