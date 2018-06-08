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

package com.sun.webui.jsf.example.propertysheet;

import javax.faces.event.ValueChangeEvent;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.model.UploadedFile;
import java.io.Serializable;

/**
 * Backing Bean for property sheet example.
 */
public class PropertySheetBackingBean implements Serializable {
    
    /** Creates a new instance of PropertySheetBackingBean */
    public PropertySheetBackingBean() {
    }
 
    // Holds value for jumpLinkChkBox property.
    private boolean jumpLinkChkBox = true;
    
    // Holds value for requiredLabelChkBox property.
    private boolean requiredLabelChkBox = true;
    
    // Holds value for jumpLink property.
    private boolean jumpLink = true;
    
    // Holds value for requiredlabel.
    private String requiredLabel = "true";
    
    /** 
     *  this method assigns value to jumpLinks 
     *  property of property sheet tag. 
     */
    public boolean getJumpLink() {
        
        return jumpLink;
        
    }
    
    /** 
     *  this method assigns value to requiredFields 
     *  property of property sheet tag. 
     */
    public String getRequiredLabel() {
        
        return requiredLabel;
    }
    
    /** Getter method for jumpLinkChkBox property. */
    public boolean getJumpLinkChkBox() {
             return jumpLinkChkBox;    
    }
    
    /** Getter method for requiredLabelChkBox property. */
    public boolean getRequiredLabelChkBox() {
           return requiredLabelChkBox;    
    }
    
    /** Setter method for jumpLinkChkBox property. */
    public void setJumpLinkChkBox(boolean jumpChkBox) {
           this.jumpLinkChkBox = jumpChkBox;    
    }
    
    /** Setter method for requiredLabelChkBox property. */
    public void setRequiredLabelChkBox(boolean requiredChkBox) {
           this.requiredLabelChkBox = requiredChkBox;    
    }
    
    /** valueChangelistener for checkbox that sets jumpLink property. */
    public void jumpMenulistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            jumpLink = true;
        } else {
            jumpLink = false;
        }
        
    }
    
    /** valueChangelistener for checkbox that sets requiredLabel property. */
    public void requiredValuelistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            requiredLabel = "true";
        } else {
            requiredLabel = "false";
        }
        
    }
 
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        jumpLinkChkBox = true;
        requiredLabelChkBox = true;
        jumpLink = true;
        requiredLabel = "true";
        return IndexBackingBean.INDEX_ACTION;
    }
}
