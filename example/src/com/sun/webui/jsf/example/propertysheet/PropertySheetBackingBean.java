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
