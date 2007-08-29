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


package com.sun.webui.jsf.example.popupMenu;

import com.sun.webui.jsf.component.Menu;
import com.sun.webui.jsf.event.ValueEvent;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionGroup;
import com.sun.webui.jsf.example.common.MessageUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Backing bean for the popup menu example.  
 */ 
public class PopupMenuBackingBean {
    //Options for two menus
    private Option OptionItems[], OptionItems2[]; 
    
    // Group option used along with OptionItems
    private Option groupOptions[];
    
    // Used to contain the options present in teh group.
    private OptionGroup group;
    
    // Stores the selected menu item and returns this for page navigation.
    private String menuAct = null;
    
    // Stores the value of the text field.
    private String text="";
    
    // Constructor for the PopupMenuBackingBean class
    public PopupMenuBackingBean() {
      
      // Initialize the options of the first menu
        OptionItems = new Option[5];
        OptionItems[0] = new Option("Copy",
                        MessageUtil.getMessage("popupMenu_menu1Option1"));
        OptionItems[0].setSeparator(true);
        OptionItems[1] = new Option("Paste", 
                MessageUtil.getMessage("popupMenu_menu1Option2"));
        OptionItems[1].setSeparator(true);
        OptionItems[2] = new Option("Undo", 
                MessageUtil.getMessage("popupMenu_menu1Option3"));
        OptionItems[2].setSeparator(true);
        OptionItems[4] = new Option("Print",
                MessageUtil.getMessage("popupMenu_menu1Option7"));
        OptionItems[4].setSeparator(false);
        OptionItems[4].setDisabled(true);
        groupOptions = new Option[2];
        groupOptions[0] = new Option("Ascending", 
                MessageUtil.getMessage("popupMenu_menu1Option5"));
        groupOptions[1] = new Option("Descending",
                MessageUtil.getMessage("popupMenu_menu1Option6"));        
        group = new OptionGroup();
        group.setValue("Sort");
        group.setLabel(MessageUtil.getMessage("popupMenu_menu1Option4"));
        group.setOptions(groupOptions);
        OptionItems[3] = group;
        OptionItems[3].setSeparator(true);
        
        // Initialize the options of the second menu
        OptionItems2 = new Option[4];        
        OptionItems2[0] = new Option(IndexBackingBean.INDEX_ACTION, 
                MessageUtil.getMessage("popupMenu_menu2Option1"));
        OptionItems2[1] = new Option("showHyperlink", 
                MessageUtil.getMessage("popupMenu_menu2Option2"));
        OptionItems2[2] = new Option("showTreeIndex", 
                MessageUtil.getMessage("popupMenu_menu2Option3"));
        OptionItems2[3] = new Option("showMasthead", 
                MessageUtil.getMessage("popupMenu_menu2Option4"));        
    }

    /**
     * Get the options for the menu.
     */     
    public Option[] getOptionItems() {
        return this.OptionItems;
    }

    /**
     * Set the options for the menu
     * @param OptionItems The options for the menu
     */    
    public void setOptionItems(Option[] OptionItems) {
        this.OptionItems = OptionItems;
    }
    
    /**
     * Get the options for the menu.
     */ 
    public Option[] getOptionItems2() {
        return this.OptionItems2;
    }

    /**
     * Set the options for the menu
     * @param OptionItems The options for the menu
     */
    public void setOptionItems2(Option[] OptionItems) {
        this.OptionItems2 = OptionItems;
    }    
        
    /**
     * Get the value of the text field.
     */ 
    public String getSelectedItem() {
        return this.text;
    }
    
    /**
     * Sets the value of the text field.
     * @param text The text to be set for the textfield
     */
    public void setSelectedItem(String text) {
        this.text = text;
    }
    
    /**
     * Returns the selected value option for the page navigation to happen.
     */
    public String selectedAction() {
      return this.menuAct;
    }
    
    /**
     * Event listener for the second menu which gets the selected option value
     * and stores it.
     * @param ve The valuEvent
     */
    public void menuSelection(ValueEvent ve) {
      this.menuAct = ve.getSelectedOption().toString();      
    }
    
    // Action handler when navigating to the main example index.
    public String showExampleIndex() {
        return IndexBackingBean.INDEX_ACTION;
    }
    
    /**
     * EventListener which goes through the selected value and updates
     * the text field with the selected option.
     *
     * @param ve The ValueEvent
     */
    public void selectOption(com.sun.webui.jsf.event.ValueEvent ve) {
        String outcome = ve.getSelectedOption().toString();
        matchOption(outcome, getOptionItems());
    }
    
    /**
     * Match the outcome of the selected item to the matching option.
     * set the textfield's value to this option's label.
     * @param outcome The selected menu item.
     * @param options The options in the menu
     */
    private void matchOption(String outcome, Option[] options) {
        for (int i=0; i<options.length; i++) {
          if (options[i].getValue().equals(outcome)) {            
              this.text = options[i].getLabel();
              break;
          }
          if (options[i] instanceof OptionGroup) {
              matchOption(outcome, ((OptionGroup)options[i]).getOptions());
          }
          
        }              
    }
}
