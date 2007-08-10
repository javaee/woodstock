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

import com.sun.webui.jsf.model.Option;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.component.UIComponent;
/**
 * Event which holds the value for the option that is selected for an Menu.
 */
public class ValueEvent extends FacesEvent {

     private Object selectedOption = null;

    /**
     * <p>Construct a new event object from the specified source component
     * and action command.</p>
     *
     * @param component Source {@link UIComponent} for this event
     *
     * @throws IllegalArgumentException if <code>component</code> is
     *  <code>null</code>
     */
    public ValueEvent(UIComponent component) {

        super(component);

    }
    
    public Object getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Object selectedOption) {
        this.selectedOption = selectedOption;
    }
    

    @Override

    // ------------------------------------------------- Event Broadcast Methods


    public  boolean isAppropriateListener(FacesListener listener) {

        return (listener instanceof EventListener);

    }

    /**
     * @throws AbortProcessingException {@inheritDoc}
     */ 
    public void processListener(FacesListener listener) {

        ((EventListener) listener).processEvent(this);

    }    

}
