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

package com.sun.webui.jsf.example.editablelist;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent; 
import javax.faces.context.FacesContext; 
import javax.faces.validator.Validator; 
import javax.faces.validator.ValidatorException;
import javax.faces.event.ValueChangeEvent;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import java.io.Serializable;

/**
 * Backing Bean for editable list example.
 */
public class EditableListBackingBean implements Serializable {
    
    // Holds value for property roles.
    private String[] roles;
    
    // Holds value for listTopChkBox property.
    private boolean listTopChkBox = true;
    
    // Holds value for sortedChkBox property.
    private boolean sortedChkBox = true;
    
    // Holds value for listTopBottom.
    private boolean listTopBottom = true;
    
    // Holds value for sortedList.
    private boolean sortedList = true;
    
    
    
    /** Creates a new instance of EfitableListBackingBean */
    public EditableListBackingBean() {
        roles = new String[5];
        
        roles[0] = "Cron Management";
        roles[1] = "DHCP Management";
        roles[2] = "Log Management";
        roles[3] = "Mail Management";
        roles[4] = "Network Management";
        
    }
 
    /** 
     *  this method assigns value to listonTop 
     *  property of editable list tag. 
     */
    public boolean getListTopBottom() {
        
        return listTopBottom;
        
    }
    
    /** 
     *  this method assigns value to sorted 
     *  property of editablelist tag. 
     */
    public boolean getSortedList() {
        
        return sortedList;
    }
    
    /** valueChangelistener for checkbox that sets list on top property. */
    public void listOnToplistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            listTopBottom = true;
        } else {
            listTopBottom = false;
        }
        
    }
    
    /** valueChangelistener for checkbox that sets sorted property. */
    public void sortedlistener(ValueChangeEvent event) {
        
        Boolean newValue = (Boolean) event.getNewValue();
        if (newValue != null 
                          && newValue.booleanValue()) {
            sortedList = true;
        } else {
            sortedList = false;
        }
        
    }
    
    /** getter method for property roles. */
    public String[] getRoles() {
        
        return this.roles;
    }
        
    /** Setter method for property roles. */
    public void setRoles(String[] roles) {
        
        this.roles = roles;
    } 
    
    /** Getter method for listTopChkBox property. */
    public boolean getListTopChkBox() {
             return listTopChkBox;    
    }
    
    /** Getter method for sortedChkBox property. */
    public boolean getSortedChkBox() {
           return sortedChkBox;    
    }
    
    /** Setter method for listTopChkBox property. */
    public void setListTopChkBox(boolean listTopChkBox) {
           this.listTopChkBox = listTopChkBox;    
    }
    
    /** Setter method for sortedChkBox property. */
    public void setSortedChkBox(boolean sortedChkBox) {
           this.sortedChkBox = sortedChkBox;    
    }
    
    /** validate method for role validation. */
    public void validate(FacesContext context, UIComponent component,
	Object value) throws ValidatorException {
        
        String msgString = null;
        FacesMessage msg = null;
        String string = value.toString(); 
              
            char[] characters = string.toCharArray(); 
            for (int counter = 0; counter < characters.length; ++counter) { 
                if (!Character.isLetter(characters[counter]) && 
                                        characters[counter] != ' ') { 
                    msgString = MessageUtil.
                                            getMessage("editablelist_string");
                          msg = new FacesMessage(msgString); 
                    throw new ValidatorException(msg); 
                } 
            } 

    }
        
    /** list validator that checks for number of list items. */
    public void validateList(FacesContext context, UIComponent component,
	                     Object value) throws ValidatorException {
        
          if (value instanceof String[]) {
              String[] list = (String[]) value;
              if (list.length < 5) {
                  String msgString = MessageUtil.
                                     getMessage("editablelist_listvalidate");
                  FacesMessage msg = new FacesMessage(msgString);
                  throw new ValidatorException(msg);
              }
          }
    }
    
    /**
     * Summary message for Validator exception.
     */
    public String getSummaryMsg() {
        return MessageUtil.getMessage("editablelist_summary");
    }
    
    /**
     * Checks for errors on page.
     */
    public boolean isErrorsOnPage() {
        
        FacesMessage.Severity severity =
                FacesContext.getCurrentInstance().getMaximumSeverity();
        if (severity == null) {
            return false;
        }
        if (severity.compareTo(FacesMessage.SEVERITY_ERROR) >= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * method to get edited roles for resultpage.
     **/
    public String getEditedRoles() {
        
        if (roles == null) 
            return ""; 
        int i = 0;
        StringBuffer editedRole = new StringBuffer(); 
        for (i = 0; i < roles.length - 1; ++i) { 
            editedRole.append(roles[i]); 
            editedRole.append(", "); 
        } 
        editedRole.append(roles[i]); 
        return editedRole.toString();
    }
    
    /**
     * Action handler when navigating to the main example index.
     */
    public String showExampleIndex() {
        
        listTopChkBox = true;
        sortedChkBox = true;
        listTopBottom = true;
        sortedList = true;
        return IndexBackingBean.INDEX_ACTION;
    }
}

