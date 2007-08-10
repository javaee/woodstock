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

import javax.faces.event.FacesListener;
import javax.faces.event.AbortProcessingException;

import javax.faces.component.StateHolder;
import javax.faces.event.ActionListener;


public interface EventListener extends FacesListener, StateHolder {


    /**
   * <p>Invoked when the action described by the specified
   * {@link ValueEvent} occurs.</p>
   * 
   * @param event The {@link MValueEvent that has occurred
   * @throws AbortProcessingException Signal the JavaServer Faces
   *  implementation that no further processing on the current event
   *  should be performed
   */
    public void processEvent(ValueEvent event)
        throws AbortProcessingException;
}

