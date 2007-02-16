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

import java.util.Iterator;

import javax.faces.event.FacesListener;
import javax.faces.event.AbortProcessingException;

import javax.faces.component.StateHolder;


/**
 * The WizardEventListener is an event listener defined on
 * {@link com.sun.webui.jsf.component.WizardStep WizardStep} and
 * {@link com.sun.webui.jsf.component.Wizard Wizard}
 * components to receive {@link WizardEvent WizardEvent} notifications.
 * <p>
 * Typically the {@link WizardEventListener WizardEventListener} instance
 * is defined on a {@link com.sun.webui.jsf.component.Wizard Wizard} using the 
 * eventListener attribute on the &lt;ui:wizard&gt; or the 
 * &lt;wizardStep&gt; tags.
 *<p/>
 * <p>
 * The listener can expect to receive the following events.
 * </p>
 * <ul>
 * <li>{@link WizardEvent#CANCEL WizardEvent.CANCEL}</li>
 * <li>{@link WizardEvent#CLOSE WizardEvent.CLOSE}</li>
 * <li>{@link WizardEvent#FINISH WizardEvent.FINISH}</li>
 * <li>{@link WizardEvent#GOTOSTEP WizardEvent.GOTOSTEP}</li>
 * <li>{@link WizardEvent#HELPTAB WizardEvent.HELPTAB}</li>
 * <li>{@link WizardEvent#NEXT WizardEvent.NEXT}</li>
 * <li>{@link WizardEvent#PREVIOUS WizardEvent.PREVIOUS}</li>
 * <li>{@link WizardEvent#STEPSTAB WizardEvent.STEPSTAB}</li>
 * <li>{@link WizardEvent#START WizardEvent.START}</li>
 * <li>{@link WizardEvent#COMPLETE WizardEvent.COMPLETE}</li>
 * </ul>
 */
public interface WizardEventListener extends FacesListener, StateHolder {
    
    /**
     * Perform functionality suitable for the specified <code>event</code>.
     *
     * @param event The WizardEvent being broadcast
     */
    public boolean handleEvent(WizardEvent event) 
	    throws AbortProcessingException;
}
