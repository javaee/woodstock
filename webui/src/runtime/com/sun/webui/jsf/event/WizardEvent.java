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

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import com.sun.webui.jsf.component.Wizard;
import com.sun.webui.jsf.component.WizardStep;

/**
 * <code>WizardEvent</code> is the event class broadcast by the 
 * {@link com.sun.webui.jsf.component.Wizard Wizard} to 
 * {@link com.sun.webui.jsf.event.WizardEventListener WizardEventListeners}.
 */
public class WizardEvent extends FacesEvent {

    private static final long serialVersionUID = 6398514451074650783L;
    /**
     * The cancel button was clicked.
     */
    public static final int CANCEL = 0;
    /**
     * The close button was clicked.
     */
    public static final int CLOSE = 1;
    /**
     * The finish button was clicked.
     */
    public static final int FINISH = 2;
    /**
     * A step link was clicked.
     */
    public static final int GOTOSTEP = 3;
    /**
     * The help tab was clicked.
     */
    public static final int HELPTAB = 4;
    /**
     * The next button was clicked.
     */
    public static final int NEXT = 6;
    /**
     * The previous button was clicked.
     */
    public static final int PREVIOUS = 7;
    /**
     * The steps tab was clicked.
     */
    public static final int STEPSTAB = 8;
    /**
     * This event is broadcast by the Wizard in 
     * {@link Wizard#encodeBegin(FacesContext) encodeBegin} to indicate
     * that this wizard session is being rendered the first time.
     */
    public static final int START = 10;
    /**
     * This event is broadcast by the Wizard in 
     * {@link Wizard#encodeEnd(FacesContext) encodeEnd} to indicate
     * that this wizard session has completed.
     */
    public static final int COMPLETE = 11;
    /**
     * This is an internal Wizard event and is not broadcast
     * to application defined {@link WizardEventListener WizardEventListeners}.
     */
    public static final int NOEVENT = 12;
    /**
     * @deprecated
     */
    public static final int INVALID = 9;
    /**
     * @deprecated
     */
    public static final int DECODE = 13;
    /**
     * @deprecated
     */
    public static final int VALIDATE = 14;
    /**
     * @deprecated
     */
    public static final int UPDATE = 15;
    /**
     * @deprecated
     */
    public static final int INVOKE_APPLICATION = 16;
    /**
     * @deprecated
     */
    public static final int RENDER = 17;
    /**
     * @deprecated
     */
    public static final int STEP_ENTER = 18;
    /**
     * @deprecated
     */
    public static final int STEP_EXIT = 19;
    // package protected can't get rid of them.
    //
    /**
     * @deprecated use {@link #getStep()}
     */
     WizardStep step;
    /**
     * @deprecated use {@link #getEvent()}
     */
     int navigationEvent;
    /**
     * @deprecated use {@link #getEventSource()}
     */
     UIComponent navigationSource;
    /**
     * @deprecated use {@link #getGotoStepId()}
     */
     Object data;

    /**
     * Contruct a <code>WizardEvent</code> instance, specifying the
     * <code>Wizard</code> source.
     */
    public WizardEvent(Wizard wizard) {
        super(wizard);
    }

    /**
     * @deprecated replaced by {@link #WizardEvent(Wizard, UIComponent, int,
     * WizardStep, String)}
     */
    public WizardEvent(Wizard wizard, UIComponent navigationSource,
            int navigationEvent, Object data) {

        this(wizard, navigationSource, navigationEvent, null,
                data instanceof String ? (String) data : null);

        // deprecation support
        //
        if (!(data instanceof String)) {
            this.data = data;
        }
    }

    /** 
     * Contruct a <code>WizardEvent</code> with event data.
     * Not all data will be available with all events. In particular:
     * <ul>
     * <li><code>gotoStepId</code> will only be available if the event
     * is <code>GOTOSTEP</code>.
     * </li>
     * <li><code>eventSource</code> will be null for the <code>START</code>
     * and <code>COMPLETE</code> events.
     * </li>
     * 
     * @param wizard the wizard instance broadcasting this event
     * @param eventSource the component that generated this event
     * @param event the WizardEvent constant identifying this event
     * @param step the WizardStep generating this event
     * @param gotoStepId the id of the step the wizard will display next
     */
    public WizardEvent(Wizard wizard, UIComponent eventSource,
            int event, WizardStep step, String gotoStepId) {

        // wizard -> source
        // Should this acually be the button ?
        //
        super(wizard);
        this.navigationSource = eventSource;
        this.navigationEvent = event;
        this.data = gotoStepId;
    }

    /**
     * @deprecated replaced by {@link #getEvent()}
     */
    public int getNavigationEvent() {
        return navigationEvent;
    }

    /**
     * Return the <code>WizardEvent</code> constant identifying this event.
     */
    public int getEvent() {
        return navigationEvent;
    }

    /**
     * @deprecated replaced by {@link #setEvent(int)}
     */
    public void setNavigationEvent(int navigationEvent) {
        this.navigationEvent = navigationEvent;
    }

    /**
     * Set the event constant identifying this event.
     */
    public void setEvent(int event) {
        this.navigationEvent = event;
    }

    /**
     * @deprecated replaced by {@link #getEventSource}
     */
    public UIComponent getNavigationEventSource() {
        return navigationSource;
    }

    /**
     * Return the wizard subcomponent that generated the original 
     * <code>ActionEvent</code> that queued this event.
     */
    public UIComponent getEventSource() {
        return navigationSource;
    }

    /**
     * Return the <code>Wizard</code> broadcasting this event.
     */
    public Wizard getWizard() {
        return (Wizard) source;
    }

    /**
     * Return the <code>WizardStep</code> that generated this event.
     */
    public WizardStep getStep() {
        return (WizardStep) step;
    }

    /**
     * Set the <code>WizardStep</code> that generated this event.
     */
    public void setStep(WizardStep step) {
        this.step = step;
    }

    /**
     * Return the component id of the <code>WizardStep</code> that the
     * wizard will be proceeding to.
     */
    public String getGotoStepId() {
        return navigationEvent == GOTOSTEP ? (String) data : null;
    }


    // FacesEvent methods
    //
    /**
     * Return <code>true</code> if <code>listener</code> is an instance of
     * <code>WizardEventListener</code>.
     *
     * @param listener appropriate listener for this event.
     */
    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof WizardEventListener);
    }

    public void processListener(FacesListener listener) {

        /*
        if (!listener.handleEvent(this)) {
        throw new AbortProcessingException();
        }
         */
    }
}
