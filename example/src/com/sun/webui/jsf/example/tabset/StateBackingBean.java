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

package com.sun.webui.jsf.example.tabset;

import java.io.Serializable;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.faces.event.ActionEvent;

import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.common.UserData;
import com.sun.webui.jsf.component.FileChooser;
import com.sun.webui.jsf.example.util.ExampleUtilities;
import com.sun.webui.jsf.model.Option;

/**
 * Backing bean for the Tabset with state example.
 */
public class StateBackingBean implements Serializable {

    public final static String SHOW_TABSET_INDEX = "showTabsetIndex";
    public final static String SHOW_STATE_TABS = "showTabsetStateTabs";

    // ID of the selected tab
    private String selectedTab;
    
    // Values of properties in the Textfield tab
    private String userId;
    private String password;
    
    // Values of properties in the FileChooser tab
    // Since the jsp creates a binding to a chooser object that we maintain
    // in this beacking bean, we don't need to maintain data members for
    // every field of the chooser whose values we are interested in.  Instead,
    // we simply manage those values thru the object.
    private FileChooser chooser = null;

    // Values of properties in the OrderableList tab
    private String[] listItems;       

    // Values of properties in the AddRemove tab
    private Option[] availableOptions;
    private String[] selectedOptions;

    // Values of properties in the Calendar tab
    private Date date;


    /**
     * Constructor
     */
    public StateBackingBean() {
	_reset();
    } // constructor

    /** Get the id of the selected tab */
    public String getSelectedTab() {
	return selectedTab;
    }

    /** Set the id of the selected tab */
    public void setSelectedTab(String selectedTab) {
	this.selectedTab = selectedTab;
    }

    /** Get the value of the user ID. */
    public String getUserId() {
        return userId;
    }

    /** Set the value of the user ID. */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** Return the state result for the user ID. */
    public String getUserIdResult() {                      
        Object[] args = new Object[2];
        args[0] = MessageUtil.getMessage("tabset_userIdLabel");
        args[1] = getUserId();
        return MessageUtil.getMessage("tabset_userIdResult", args);       
    }

    /** Get the value of the password field. */
    public String getPassword() {
        return password;
    }

    /** Set the value of the password field. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Return the state result for the password. */
    public String getPasswordResult() {                      
	String key;
	if (getPassword().length() != 0)
	    key = "tabset_passwordSetResult";
	else
	    key = "tabset_passwordNotSetResult";
        return MessageUtil.getMessage(key,
		MessageUtil.getMessage("tabset_passwordLabel"));
    }

    /** Return the fileChooser component object */
    public FileChooser getChooser() {
	return chooser;
    }

    /** Set the fileChooser component object */
    public void setChooser(FileChooser chooser) {
	this.chooser = chooser;
    }

    /** Get the value of the FileChooser's lookin field */
    public File getLookin() {
	return new File(chooser.getCurrentFolder());
    }

    /** Set the value of the FileChooser's lookin field */
    public void setLookin(File lookin) {
        chooser.setLookin(lookin);
    }

    /** Get the values of the FileChooser's selected field */
    public File[] getSelectedFiles() {
	return (File[])chooser.getSelected();
    }

    /** Set the values of the FileChooser's selected field */
    public void setSelectedFiles(File[] selectedFiles) {
	chooser.setSelected(selectedFiles);
    }

    /** Return the state result for the FileChooser. */
    public UserData getChooserResult() {                      
	File[] files = getSelectedFiles();
	if (files == null)
	    return null;

	StringValue[] values = new StringValue[files.length];
	for (int i = 0; i < files.length; i++)
	    values[i] = new StringValue(files[i].toString());

	return new UserData(values);
    }

    /** Get the value of property listItems. */
    public String[] getListItems() {
        return this.listItems;
    } 

    /** Set the value of property listItems. */
    public void setListItems(String[] listItems) {
        this.listItems = listItems;
    }

    /** Return the state result for the Orderable List. */
    public UserData getOrderableListResult() {                      
	String[] items = getListItems();
	if (items == null)
	    return null;

	StringValue[] value = new StringValue[items.length];
	for (int i = 0; i < items.length; i++)
	    value[i] = new StringValue(items[i]);

	return new UserData(value);
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

    /** Return the state of the AddRemove selected list */
    public UserData getSelectedOptionsResult() {

	// These are values (keys) that represent the actual selected labels
	String[] selValues = getSelectedOptions();
	if (selValues == null)
	    return null;

	Option[] availOpts = getAvailableOptions();
	if (availOpts == null)
	    return null;

	// For each selected value, append it's Option object from the
	// available list to an array.
	Option[] selOpts = new Option[selValues.length];
	for (int i = 0; i < selValues.length; i++) {
	    for (int j = 0; j < availOpts.length; j++) {
		if (selValues[i].equals(availOpts[j].getValue())) {
		    selOpts[i] = availOpts[j];
		}
	    }
	}
	return new UserData(selOpts);
    }

    /** Get the value of property date. */
    public Date getDate() {
	return date;
    }

    /** Set the value of property date. */
    public void setDate(Date date) {
	this.date = date;
    }

    /** Return the state result for the Calendar. */
    public String getCalendarResult() {                      
        Object[] args = new Object[2];
        args[0] = MessageUtil.getMessage("tabset_tabCalendarLabel");
	String value = "";
	Date date = getDate();
	if (date != null) {
	    SimpleDateFormat dateFmt = new SimpleDateFormat("EE MMM dd, yyyy");
	    value = dateFmt.format(date);
	}
        args[1] = value;
        return MessageUtil.getMessage("tabset_calendarResult", args);
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Action handler when navigating to the state tabs example */
    public String showStateTabs() {
	return SHOW_STATE_TABS;
    }
         
    /** Action handler when navigating to the tabset example index. */
    public String showTabsetIndex() {
	_reset();
        return SHOW_TABSET_INDEX;
    }
    
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
	_reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    /** Action handler for the Reset button */
    public String reset() {
	_reset();

	// Normally we would return null to redisplay the page.  However
	// fileChooser behavior makes resetting state in the chooser
	// problematic.  So we do a full page submit.
	return SHOW_STATE_TABS;
    }

    public void tabClicked(ActionEvent ae) {
	setSelectedTab(ae.getComponent().getId());
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Initial all bean values to their defaults */
    private void _reset() {
	// Tabset
	setSelectedTab("tabTextfield");

	// Textfield tab
	setUserId("");
	setPassword("");

	// Filechooser tab
	chooser = new FileChooser();
	chooser.setMultiple(true);
	chooser.setFolderChooser(false);
	setLookin(defaultLookin());
	setSelectedFiles(null);

	// Orderable List tab
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

	// AddRemove tab
	selectedOptions = null;
	availableOptions = new Option[10];
	availableOptions[0] = new Option("addremove_author1",
	    MessageUtil.getMessage("addremove_author1"));
	availableOptions[1] = new Option("addremove_author2",
	    MessageUtil.getMessage("addremove_author2"));
	availableOptions[2] = new Option("addremove_author3",
	    MessageUtil.getMessage("addremove_author3"));
	availableOptions[3] = new Option("addremove_author4",
	    MessageUtil.getMessage("addremove_author4"));
	availableOptions[4] = new Option("addremove_author5",
	    MessageUtil.getMessage("addremove_author5"));
	availableOptions[5] = new Option("addremove_author6",
	    MessageUtil.getMessage("addremove_author6"));
	availableOptions[6] = new Option("addremove_author7",
	    MessageUtil.getMessage("addremove_author7"));
	availableOptions[7] = new Option("addremove_author8",
	    MessageUtil.getMessage("addremove_author8"));
	availableOptions[8] = new Option("addremove_author9",
	    MessageUtil.getMessage("addremove_author9"));
	availableOptions[9] = new Option("addremove_author10",
	    MessageUtil.getMessage("addremove_author10"));

	// Calendar tab
	date = null;
    }

    /** Return File object for the default lookin directory */
    private File defaultLookin() {
        String osName = System.getProperty("os.name").toUpperCase();
        if (osName.startsWith("WINDOW")) {
            return new File("c:\\\\windows");
        } else {
            return new File("/usr");
        }
    }
}
