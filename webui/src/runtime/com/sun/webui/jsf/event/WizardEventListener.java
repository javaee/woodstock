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
