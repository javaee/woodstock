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

/*
 * ToggleActionListener.java
 *
 * Created on August 23, 2006, 3:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.sun.webui.jsf.event;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.Tree;
import java.io.Serializable;

/**
 *
 * @author deep, John Yeary
 */
public class ToggleActionListener implements ActionListener, Serializable {

    private static final long serialVersionUID = -6635913171312578091L;

    public void processAction(ActionEvent event) {

        UIComponent comp = event.getComponent();
        /*LogUtil.info("CLICK ACTION FROM: " + 
        comp.getClass().getName() + " with id " + comp.getId());
         */
        boolean flag = false;
        if (!comp.getId().endsWith("turner")) {
            flag = true;
        }
        while (comp != null && !(comp instanceof TreeNode)) {
            comp = comp.getParent();
        }

        if (comp != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            TreeNode node = (TreeNode) comp;
            Tree root = TreeNode.getAbsoluteRoot(comp);
            if (flag) {
                root.setSelected(node.getId());
            }
            // Queue the TreeNodeToggleEvent. This will
            // enable control to flow thru tree nodes's
            // broadcast method where it can be checked
            // if the tree node has an actionListenerExpression
            // associated with it and if so invoke it.
            // This also gives the correct impression of the 
            // actionEvent being fired from the treeNode as
            // opposed to the hyperlink representing the
            // toggle icon.
            node.queueEvent(new TreeNodeToggleEvent(node));
        }

    }
}
