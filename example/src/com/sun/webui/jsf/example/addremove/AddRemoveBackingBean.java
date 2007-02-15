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

package com.sun.webui.jsf.example.addremove;

import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.component.AddRemove;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.common.UserData;
import com.sun.webui.jsf.example.index.IndexBackingBean;

import java.io.Serializable;

/**
 * Backing bean for Add Remove example.
 */
public class AddRemoveBackingBean implements Serializable {
        
    /** Holds value of property availableOptions. */
    private Option[] availableOptions = null;
    
    /** Holds value of property selectedOptions. */
    private String[] selectedOptions = null;
         
    /** Holds value of property alertDetail. */
    private String alertDetail = null;
    
    /** Holds value of property alertRendered. */
    private boolean alertRendered = false;
            
    /** Holds value of property vertiaclLayout. */
    private boolean verticalLayout = false;
    
    /** Holds value of property linkText. */
    private String linkText = null;
    
    /** Holds value of property labelText. */
    private String labelText = null;
        
    /** Options used in the available listbox for horizontal layout. */
    private Option[] horizontalOptions = null;
            
    /** Options used in the available listbox for vertical layout. */
    private Option[] verticalOptions = null;                                           
    
    /** UserData. */
    private UserData userData = null;        
        
    /** Outcome strings used for navigation handling in the faces config file. */
    private static final String SHOW_ADDREMOVE_RESULTS = "showAddRemoveResults";
    private static final String SHOW_ADDREMOVE_EXAMPLE = "showAddRemove";    
    
    /** Default constructor. */
    public AddRemoveBackingBean() {                
        // Options used to populate the available listbox for horizontal layout.
        horizontalOptions = new Option[10];
        horizontalOptions[0] = new Option("addremove_author1",
                MessageUtil.getMessage("addremove_author1"));                        
        horizontalOptions[1] = new Option("addremove_author2",
                MessageUtil.getMessage("addremove_author2"));        
        horizontalOptions[2] = new Option("addremove_author3",
                MessageUtil.getMessage("addremove_author3"));        
        horizontalOptions[3] = new Option("addremove_author4",
                MessageUtil.getMessage("addremove_author4"));
        horizontalOptions[4] = new Option("addremove_author5",
                MessageUtil.getMessage("addremove_author5"));        
        horizontalOptions[5] = new Option("addremove_author6",
                MessageUtil.getMessage("addremove_author6"));        
        horizontalOptions[6] = new Option("addremove_author7",
                MessageUtil.getMessage("addremove_author7"));        
        horizontalOptions[7] = new Option("addremove_author8",
                MessageUtil.getMessage("addremove_author8"));        
        horizontalOptions[8] = new Option("addremove_author9",
                MessageUtil.getMessage("addremove_author9"));
         horizontalOptions[9] = new Option("addremove_author10",
                MessageUtil.getMessage("addremove_author10"));        
         
        // Options used to populate the available listbox for vertical layout.
        verticalOptions = new Option[10];
        verticalOptions[0] = new Option("addremove_book1v",
                MessageUtil.getMessage("addremove_book1v"));                        
        verticalOptions[1] = new Option("addremove_book2v",
                MessageUtil.getMessage("addremove_book2v"));        
        verticalOptions[2] = new Option("addremove_book3v",
                MessageUtil.getMessage("addremove_book3v"));        
        verticalOptions[3] = new Option("addremove_book4v",
                MessageUtil.getMessage("addremove_book4v"));
        verticalOptions[4] = new Option("addremove_book5v",
                MessageUtil.getMessage("addremove_book5v"));        
        verticalOptions[5] = new Option("addremove_book6v",
                MessageUtil.getMessage("addremove_book6v"));        
        verticalOptions[6] = new Option("addremove_book7",
                MessageUtil.getMessage("addremove_book7v"));        
        verticalOptions[7] = new Option("addremove_book8v",
                MessageUtil.getMessage("addremove_book8v"));        
        verticalOptions[8] = new Option("addremove_book9v",
                MessageUtil.getMessage("addremove_book9v"));
         verticalOptions[9] = new Option("addremove_book10v",
                MessageUtil.getMessage("addremove_book10v"));   
         
         // Set the availableOptions property with the         
         // options for the horizontal layout.
         availableOptions = horizontalOptions;                                             
    }
    
    /** Get the value of property availableOptions. */
    public Option[] getAvailableOptions() {
        return this.availableOptions;
    } 
    
    /** Set the value of property availableOptions. */
    public void setAvailableOptions(Option[] availableOptions) {
        this.availableOptions = availableOptions;
    }
    
    /** Get the value of property selectedOptions. */
    public String[] getSelectedOptions() {
        return this.selectedOptions;
    }
    
    /** Set the value of property selectedOptions. */
    public void setSelectedOptions(String[] selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
    
    /** Return the alert detail message. */
    public String getAlertDetail() {
        alertDetail = (verticalLayout) 
                    ? MessageUtil.getMessage("addremove_noBookSelection")
                    : MessageUtil.getMessage("addremove_noAuthorSelection");
        return alertDetail;
    }
    
    /** Return the render state of the alert. */
    public boolean getAlertRendered() { 
        // Inline alert should show up when the addremove
        // component on the page becomes invalid.
        FacesMessage.Severity severity = 
            FacesContext.getCurrentInstance().getMaximumSeverity();
        if(severity == null) { 
            alertRendered = false; 
        } else {
            if(severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0) { 
                alertRendered = true; 
            } 
        }
        return alertRendered;
    } 
    
    /** Get the value of property verticalLayout. */
    public boolean getVerticalLayout() {
        return verticalLayout;
    }
    
    /** Return the text description of the hyperlink. */
    public String getLinkText() {        
        linkText = (verticalLayout) 
                ? MessageUtil.getMessage("addremove_showHorizontal")
                : MessageUtil.getMessage("addremove_showVertical");
        
        return linkText;
    }        
    
    /** Return the text for the addremove's label. */
    public String getLabelText() {
        return ((verticalLayout) 
                ? MessageUtil.getMessage("addremove_selectBooks")
                : MessageUtil.getMessage("addremove_selectAuthors"));
    }

    /** Get UserData created with an option array containing user selections. */
    public UserData getUserData() {
        if (userData == null) {
            Option[] selected = new Option[selectedOptions.length];
            for (int i = 0; i < selectedOptions.length; i++) {
                for (int j = 0; j < availableOptions.length; j++) {
                    if (selectedOptions[i].equals(availableOptions[j].getValue())) {
                        selected[i] = availableOptions[j];
                    }
                }
            }                        
            userData = new UserData(selected);            
        }        
        
        return userData;
    }        
        
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                
    /** Action handler for the orientation hyperlink. */
    public String orientationLinkActionHandler() {        
        // Change the orientation of the add-remove componenet,
        // so that the page is re-displayed with the correct layout.
        verticalLayout = (verticalLayout) ? false : true;
  
        // Set the appropriate options in the available listbox
        // based on the new layout.
        if (verticalLayout) {            
            availableOptions = verticalOptions;
        } else {
            availableOptions = horizontalOptions;
        } 
                
        // We don't want the addremove model object to get updated with
        // its submitted value each time the orientation of the component
        // is changed. So, we need to null out the submitted value.        
        FacesContext context = FacesContext.getCurrentInstance();        
        AddRemove addremove =
                (AddRemove) context.getViewRoot().findComponent(
                        "form:contentPageTitle:addRemove");                        
        addremove.setSubmittedValue(null);
        selectedOptions = null;        
        
        // Return null to cause the page to re-render.
        return null;
    }

    /** Action handler for the "ShowItems" button. */
    public String showItemsButtonActionHandler() {
        return SHOW_ADDREMOVE_RESULTS;
    }

    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {        
        reset();        
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /** Action handler when navigating to the addremove example. */
    public String showAddRemoveExample() {
        return SHOW_ADDREMOVE_EXAMPLE;
    }
            
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Listeners
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Action listener for the preset button. */
    public void presetList(ActionEvent e) {
        // Sine the action is immediate, the add remove component won't
        // go through the Update Model phase. However, its submitted value
        // gets set in the Apply Request Value phase and this value is retained
        // when the page is redisplayed. 
        //
        // So, we need to explicitly erase the submitted value and then update
        // the model object with the initial state.        
        FacesContext context = FacesContext.getCurrentInstance();        
        AddRemove addremove =
                (AddRemove) context.getViewRoot().findComponent(
                        "form:contentPageTitle:addRemove");                        
        addremove.setSubmittedValue(null);
        preset();                
    }      
    
    /** Action listener for the ShowItems button. */
    public void resetDataProvider(ActionEvent e) {
        // reset data provider;
        userData = null;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Helper method to preset the listboxes with initial values. */
    private void preset() {           
         // Set options 9-10 in selected listbox.                         
         if (verticalLayout) {            
             this.selectedOptions = new String[] {"addremove_book9v",
                     "addremove_book10v"};
         } else {                    
             this.selectedOptions = new String[] {"addremove_author9",
                     "addremove_author10"};
         }                
    }   
 
    /** Reset. */
    private void reset() {
        verticalLayout = false;
        selectedOptions = null;
        availableOptions = horizontalOptions;
    }
}
