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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.event;

import com.sun.webui.jsf.component.TreeNode2;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.component.UIComponent;
/**
 * Event which holds the type of action associated with a node and the id of
 * that node.
 */
public class TreeNode2Event extends FacesEvent {

     public static final int LOADCHILDREN_EVENT = 1;
     public static final int NODESELECTED_EVENT = 2;
     
     private TreeNode2 eventNode = null;
     private int eventType = 0;
     
    /**
     * <p>Construct a new event object from the specified source component
     * and action command.</p>
     *
     * @param component Source {@link UIComponent} for this event
     *
     * @throws IllegalArgumentException if <code>component</code> is
     *  <code>null</code>
     */
    public TreeNode2Event(UIComponent component) {

        super(component);
        this.eventNode = (TreeNode2)component;

    }
    
    public TreeNode2 getEventNode() {
        return this.eventNode;
    }

    public void setEventNode(TreeNode2 node) {
        this.eventNode = node;
    }
    
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return this.eventType;
    }
    @Override

    // ------------------------------------------------- Event Broadcast Methods


    public  boolean isAppropriateListener(FacesListener listener) {

        return (listener instanceof FacesListener);

    }

    /**
     * @throws AbortProcessingException {@inheritDoc}
     */ 
    public void processListener(FacesListener listener) {

        // no op

    }    

}