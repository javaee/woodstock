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

package com.sun.webui.jsf.example.orderablelist;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.common.UserData;

import com.sun.webui.jsf.component.OrderableList;

import java.io.Serializable;

/**
 * Backing bean for Orderable List example. 
 */
public class OrderableListBackingBean implements Serializable {     
    
    /** Holds value of property listItems. */
    private String[] listItems = null;       
    
    /** The original list. */
    private String[] originalList = null;       

    /** UserData. */
    private UserData userData = null;             
    
    /** Default constructor. */
    public OrderableListBackingBean() { 
        // List items.        
        listItems = new String[17];
        listItems[0] = MessageUtil.getMessage("orderablelist_flavor1");
        listItems[1] = MessageUtil.getMessage("orderablelist_flavor2");
        listItems[2] = MessageUtil.getMessage("orderablelist_flavor3");
        listItems[3] = MessageUtil.getMessage("orderablelist_flavor4");
        listItems[4] = MessageUtil.getMessage("orderablelist_flavor5");
        listItems[5] = MessageUtil.getMessage("orderablelist_flavor6");
        listItems[6] = MessageUtil.getMessage("orderablelist_flavor7");
        listItems[7] = MessageUtil.getMessage("orderablelist_flavor8");
        listItems[8] = MessageUtil.getMessage("orderablelist_flavor9");
        listItems[9] = MessageUtil.getMessage("orderablelist_flavor10");
        listItems[10] = MessageUtil.getMessage("orderablelist_flavor11");
        listItems[11] = MessageUtil.getMessage("orderablelist_flavor12");
        listItems[12] = MessageUtil.getMessage("orderablelist_flavor13");
        listItems[13] = MessageUtil.getMessage("orderablelist_flavor14");
        listItems[14] = MessageUtil.getMessage("orderablelist_flavor15");
        listItems[15] = MessageUtil.getMessage("orderablelist_flavor16");
        listItems[16] = MessageUtil.getMessage("orderablelist_flavor17");          
        
        originalList = listItems;
    }    
    
    /** Get the value of property listItems. */
    public String[] getListItems() {
        return this.listItems;
    } 
    
    /** Set the value of property listItems. */
    public void setListItems(String[] listItems) {
        this.listItems = listItems;
    }
    
    /** Get UserData created with an array containing user's ordered list. */
    public UserData getUserData() {
        if (userData == null) {
            Flavor[] flavor = new Flavor[listItems.length];
            for (int i = 0; i < listItems.length; i++) {
                flavor[i] = new Flavor(listItems[i]);
            }        
        
            userData = new UserData(flavor);        
        }
        return userData;
    }              
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Listeners
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /** Action listener for the reset button. */
    public void resetOrder(ActionEvent e) {
        // Sine the action is immediate, the orderable list component won't
        // go through the Update Model phase. However, its submitted value
        // gets set in the Apply Request Value phase and this value is retained
        // when the page is redisplayed. 
        //
        // So, we need to explicitly erase the submitted value and then update
        // the model object with the original list.        
        FacesContext context = FacesContext.getCurrentInstance();        
        OrderableList list =
                (OrderableList) context.getViewRoot().findComponent(
                        "form:contentPageTitle:orderableList");                        
        list.setSubmittedValue(null);
        
        // Set the model object.
        listItems = originalList;  
    } 
    
    /** Action listener for the breadcrumbs link. */
    public void processLinkAction(ActionEvent e) {
        // All apps should revert to their initial state
        // when they are re-visitted from the Example index.       
        // So, set the model object to the original list.
        listItems = originalList;          
    }
    
    /** Action listener for the ShowItems button. */
    public void resetDataProvider(ActionEvent e) {
        // reset data provider;
        userData = null;
    }
}
