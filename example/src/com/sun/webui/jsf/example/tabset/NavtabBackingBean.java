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
import java.util.HashMap;

import javax.faces.event.ActionEvent;
import javax.faces.component.UIComponentBase;

import com.sun.webui.jsf.component.TabSet;
import com.sun.webui.jsf.component.Tab;
import com.sun.webui.jsf.component.Frame;
import com.sun.webui.jsf.example.index.IndexBackingBean;
import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.util.ExampleUtilities;

/**
 * Backing bean for the Navigational tabset example
 */
public class NavtabBackingBean implements Serializable {

    public final static String SHOW_TABSET_INDEX = "showTabsetIndex";
    public final static String SHOW_NAVTABS = "showTabsetNavTabs";

    private TabSet tabset;

    // ID of the tab that was clicked
    private String clickedTab;

    // Object defining content characteristics of the selected tab
    private TabContent tabContent;

    // Map of tab IDs to tab-specific content characteristics
    private HashMap<String,TabContent> contentMap;

    /**
     * Constructor
     */
    public NavtabBackingBean() {
	reset();
    } // constructor

    /** Get the tabset */
    public TabSet getTabset() {
	if (tabset != null)
	    return tabset;

	contentMap = new HashMap<String,TabContent>();

	tabset = new TabSet();
	tabset.setId("tabset");

	Tab newsTab = addTab(tabset, "News");
	Tab sportsTab = addTab(tabset, "Sports");
	Tab weatherTab = addTab(tabset, "Weather");
	Tab searchTab = addTab(tabset, "Search");

	Tab localNewsTab = addTab(newsTab, "Local");
	Tab nationalNewsTab = addTab(newsTab, "National");

	addTab(localNewsTab, "Wbz");
	addTab(localNewsTab, "Wcvb");
	addTab(localNewsTab, "Whdh");

	addTab(nationalNewsTab, "Abc");
	addTab(nationalNewsTab, "Cbs");
	addTab(nationalNewsTab, "Nbc");
	addTab(nationalNewsTab, "Cnn");

	addTab(sportsTab, "Mlb");
	addTab(sportsTab, "Nba");
	addTab(sportsTab, "Nhl");
	addTab(sportsTab, "Nfl", "http://www.nfl.com", "1200px");

	addTab(searchTab, "Yahoo", "http://www.yahoo.com", "285px");
	addTab(searchTab, "Google", "http://www.google.com", "285px");

	Class[] paramTypes = new Class[] {ActionEvent.class};
	ExampleUtilities.setMethodExpression(tabset,
            "actionListenerExpression", "#{NavtabBean.tabClicked}",
            null, paramTypes);

	// Set a selected tab for the tabset
	tabset.setSelected("Nfl");

	// Set tabContent object associated with the selected tab
	if (tabset.getSelected() != null)
	    tabContent = contentMap.get(tabset.getSelected());

	return tabset;
    }

    /** Set the tabset */
    public void setTabset(TabSet tabset) {
	this.tabset = tabset;
    }

    /** Get the content URL associated with the current tab */
    public String getContentUrl() {
	if (tabContent != null)
	    return tabContent.getUrl();
	return "";
    }

    public String getContentHeight() {
	if (tabContent != null)
	    return tabContent.getFrameHeight();
	return "200px";
    }

    /**
     * Get the message to display for the current tab's content.
     * This is simply the default content for tabs that do not have
     * a URL whose content should be rendered.
     */
    public String getMessage() {
	String message = "";
	if (clickedTab != null) {
	    message = MessageUtil.getMessage("tabset_tabClicked",
		MessageUtil.getMessage("tabset_tab" + clickedTab));
	}
	message += MessageUtil.getMessage("tabset_tabSelected",
	    MessageUtil.getMessage("tabset_tab" + tabset.getSelected()));
	return message;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action Handlers
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Action handler when navigating to the tabset navigation example */
    public String showNavTabs() {
	return SHOW_NAVTABS;
    }
         
    /** Action handler when navigating to the tabset example index. */
    public String showTabsetIndex() {
	reset();
        return SHOW_TABSET_INDEX;
    }
    
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
	reset();
        return IndexBackingBean.INDEX_ACTION;
    }

    public void tabClicked(ActionEvent ae) {
	clickedTab = ae.getComponent().getId();
	tabContent = contentMap.get(clickedTab);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** Initial all bean values to their defaults */
    private void reset() {
	tabset = null;
	clickedTab = null;
	tabContent = null;
	contentMap = null;
    }

    /**
     * Add a new tab with specified label as a child of a given parent
     * Tab content will be a default URL to render a message.
     */
    private Tab addTab(UIComponentBase parent, String labelKey) {
	return addTab(parent, labelKey, "message.jsp", "200px");
    }

    /**
     * Add a new tab with specified label as a child of a given parent.
     * Tab content will be the specified URL.
     * @param parent  parent object the new tab is a child of
     * @param labelKey resource key of the tab label sans the "tabset_tab" prefix
     * @param url defines the URL of the file to show in the frame
     * @param frameHeight the height of the webuijsf:iframe that contains
                the tab's content (the target of the URL).  Setting to a 
                large value will result in the entire page (including the 
                tabset) to be scrollable.  The content may also be scrollable.
                Setting to a smaller value that does NOT result in the entire
                page to be scrollable will result in only the content to be
                scrollable (there's enough content).
     */
    private Tab addTab(UIComponentBase parent, String labelKey,
		String url, String frameHeight) {
	String label = MessageUtil.getMessage("tabset_tab" + labelKey);
	Tab tab = new Tab(label);
	tab.setId(labelKey);
	parent.getChildren().add(tab);
	contentMap.put(labelKey, new TabContent(url, frameHeight));
	return tab;
    }

    /**
     * Convenience class for maintaining rendering characteristics for
     * tab content.
     */
    private class TabContent {
	String url;
	String frameHeight;

	public TabContent(String url, String frameHeight) {
	    this.url = url;
	    this.frameHeight = frameHeight;
	}

	public String getUrl() {
	    return url;
	}

	public String getFrameHeight() {
	    return frameHeight;
	}
    }
}
