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

/*
 * TreeNodeToggledEvent.java
 *
 * Created on November 8, 2006, 1:44 PM
 *
 */
package com.sun.webui.jsf.event;

import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;
import com.sun.webui.jsf.component.TreeNode;

/*
 * This is an event instance that queued by a TreeNode
 * object's associated toggle listener. Queuing this
 * even causes TreeNode's broadcast method to fire
 * during which time actionListenerExpressions, if
 * associated with the TreeNode, can be handled 
 * appropriately. The default phaseId of 
 * "ANY_PHASE" is being used.
 */
public class TreeNodeToggleEvent extends ActionEvent {

    private static final long serialVersionUID = 6421867968165307677L;
    private TreeNode node = null;

    public TreeNodeToggleEvent(TreeNode node) {
        super(node);
        this.node = node;
    }

    /* 
     * Return true if this FacesListener is an instance of a listener 
     * class that this event supports. Typically, this will be accomplished
     * by an "instanceof" check on the listener class.
     */
    @Override
    public boolean isAppropriateListener(FacesListener listener) {
        if (listener instanceof ToggleActionListener) {
            return true;
        } else {
            return false;
        }
    }

    /* 
     * This should be a no op as all we want to do
     * is invoke the actionlistenerExpressions associated
     * with the node.
     */
    @Override
    public void processListener(FacesListener listener) {
        // This should be a no op as all we want to do
        // is invoke the actionlistenerExpressions associated
        // with the node.
    }
}
