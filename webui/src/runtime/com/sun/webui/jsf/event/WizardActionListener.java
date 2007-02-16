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

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.AbortProcessingException;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.component.Wizard;

/**
 * The WizardActionListener is an internal listener specified on the
 * navigation and tab components of the {@link Wizard Wizard} component.
 */
public class WizardActionListener implements ActionListener, StateHolder {
    
    private String wizardId;
    private int event;
    private boolean _transient = false;

    /**
     * Create a new instance of <code>WizardActionListener</code>
     * identifying the wizard and event this listener is registered for.
     * This class is used internally to set the internal event state
     * of the wizard.
     */
    public WizardActionListener(String wizardId, int event) {
	super();
	this.wizardId = wizardId;
	this.event = event;
    }

    /**
     * Default constructor for restoring and saving state
     */
    public WizardActionListener() {
	super();
    }
    
    /**
     * This method locates the Wizard listening for this event and 
     * sets the event state, for eventual broadcast to 
     * {@link WizardEventListeners WizardListeners}.
     *
     * @param actionEvent The ActionEvent generated
     */
    public void processAction(ActionEvent actionEvent) 
	    throws AbortProcessingException {

	Wizard wizard = Wizard.getWizard((UIComponent)
	    actionEvent.getSource(), wizardId);
	if (wizard == null) {
	    // Log  and throw
            if (LogUtil.infoEnabled()) {
                LogUtil.info(WizardActionListener.class, "WEBUI0006",
                    new String[] { wizardId });
            }
	    throw new AbortProcessingException(
		"Wizard parent not found processing " +
		event);
	}
	wizard.broadcastEvent((UIComponent)actionEvent.getSource(), event);
    }

    /**
     * Save the state of this listener.
     */
    public Object saveState(FacesContext context) {
	Object _values[] = new Object[3];
	_values[0] = new Integer(event);
	_values[1] = new String(wizardId);
	_values[2] = new Boolean(_transient);
	return _values;
    }

    /**
     * Restor the state of this listener.
     */
    public void restoreState(FacesContext context, Object state) {
	Object _values[] = (Object[])state;
	this.event = ((Integer)_values[0]).intValue();
	this.wizardId = (String)_values[1];
	this._transient = ((Boolean)_values[2]).booleanValue();
    }

    public boolean isTransient() {
	return _transient;
    }

    /**
     * Set the transient nature of this StateHolder.
     */
    public void setTransient(boolean newTransientValue) {
	// Don't honor this.
    }

}
