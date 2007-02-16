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

package com.sun.webui.jsf.bean;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.help.HelpSet;
import javax.help.TreeItem;
import javax.help.SearchTOCItem;
import javax.help.ServletHelpBroker;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.tree.*;

import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.Markup;
import com.sun.webui.jsf.component.PanelGroup;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Tab;
import com.sun.webui.jsf.component.TabSet;
import com.sun.webui.jsf.component.Tree;
import com.sun.webui.jsf.component.TreeNode;
import com.sun.webui.jsf.component.HelpWindow;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.HelpUtils;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ClientSniffer;

/**
 * This class defines a backing bean required for use by the HelpWindow
 * component.
 *
 * @author Sean Comerford
 */
public class HelpBackingBean {
    public static final String HELP_CONTENTS_TREE_ID = "helpContentsTree";
    public static final String HELP_INDEX_TREE_ID    = "helpIndexTree";
    public static final String CONTENT_FRAME_NAME = "contentFrame";
    
    private Theme theme = null;
    private Tree contentsTree = null;
    private Tree indexTree = null;
    private PanelGroup searchPanel = null;
    private PanelGroup searchResultsPanel = null;
    private TextField searchField = null;
    private Button searchButton = null;
    
    private HelpUtils helpUtils = null;
    private String helpSetPath = "";
    private String jspPath = null;
    private int httpPort = -1;
    
    private String requestScheme = null;
    
    private String navigatorUrl = null;
    private String bottomFrameUrl = null;
    private String buttonFrameUrl = null;

    private String buttonClassName = null;
    private String inlineHelpClassName = null;
    private String searchClassName = null;
    private String stepTabClassName = null;
    private String titleClassName = null;
    private String bodyClassName= null;
    private String tipsHeadTitle = null;
    
    private String tipsTitle = null;
    private String tipsImprove = null;
    private String tipsImprove1 = null;
    private String tipsImprove2 = null;
    private String tipsImprove3 = null;
    private String tipsImprove4 = null;
    private String tipsNote = null;
    private String tipsNoteDetails = null;
    private String tipsSearch = null;
    private String tipsSearch1 = null;
    private String tipsSearch2 = null;
    private String tipsSearch3 = null;
    private String tipsSearch4 = null;
    private String backButtonText = null;
    private String forwardButtonText = null;
    private String printButtonText = null;
    
    private String contentsText = null;
    private String indexText = null;
    private String searchText = null;
    private String closeLabel = null;
    private String searchLabel = null;
    private String navFrameTitle = null;
    private String buttonFrameTitle = null;
    private String contentFrameTitle = null;
    private String noFrames = null;
    private String buttonNavHeadTitle = null;
    private String navigatorHeadTitle = null;
    private String buttonBodyClassName = null;
    /**
     * Creates a new instance of HelpBackingBean.
     */
    public HelpBackingBean() {
    
    }
    
    public String getNavigatorUrl() {
        
        if (navigatorUrl != null) {
            return navigatorUrl;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuffer urlBuffer = new StringBuffer();
                
        // set navigatorUrl
        String jspPath = getJspPath();
        
        urlBuffer.append(jspPath != null ? "/".concat(jspPath) : ""); 
        int length = urlBuffer.length();
        urlBuffer.append(HelpWindow.DEFAULT_JSP_PATH)
            .append(HelpWindow.DEFAULT_NAVIGATION_JSP);
        navigatorUrl = context.getApplication().getViewHandler()
            .getActionURL(context, urlBuffer.toString());
        return navigatorUrl;
    }

    public void setNavigatorUrl(String url) {
        navigatorUrl = url;
    }
    
    public String getBottomFrameUrl() {
        if (bottomFrameUrl != null) {
            return bottomFrameUrl;
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuffer urlBuffer = new StringBuffer();
        String jspPath = getJspPath();
        urlBuffer.append(jspPath != null ? "/".concat(jspPath) : ""); 
        urlBuffer.append(HelpWindow.DEFAULT_JSP_PATH)
            .append(HelpWindow.DEFAULT_BUTTONFRAME_JSP);
        bottomFrameUrl = context.getApplication().getViewHandler()
            .getActionURL(context, urlBuffer.toString());  
        return bottomFrameUrl;
    }

    public void setBottomFrameUrl(String url) {
        bottomFrameUrl = url;
        
        
    }

    public String getButtonFrameUrl() {
        if (buttonFrameUrl != null) {
            return buttonFrameUrl;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        StringBuffer urlBuffer = new StringBuffer();
        String jspPath = getJspPath();
        urlBuffer.append(jspPath != null ? "/".concat(jspPath) : ""); 
        urlBuffer.append(HelpWindow.DEFAULT_JSP_PATH)
            .append(HelpWindow.DEFAULT_BUTTONNAV_JSP);
        buttonFrameUrl = context.getApplication().getViewHandler()
            .getActionURL(context, urlBuffer.toString());
        return buttonFrameUrl;
    }

    public void setButtonFrameUrl(String url) {
        buttonFrameUrl = url;
    }
    
    /**
     * <p>Get the scheme that will be used for help set requests.</p>
     *
     * <p>The default is "http".</p>
     *
     * @return The request scheme used for JavaHelp requests.
     */
    public String getRequestScheme() {
        return requestScheme;
    }
    
    /**
     * <p>Set the scheme that will be used for help set requests.</p>
     *
     * @param scheme The request scheme to use for JavaHelp requests
     *  (i.e. "https").
     */
    public void setRequestScheme(String scheme) {
        requestScheme = scheme;
    }
    
    /**
     * Get the value of the helpSetPath managed bean property.
     *
     * @return The value of the helpSetPath managed bean property.
     */
    public String getHelpSetPath() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        String realPath = ec.getRequestContextPath();
        
        if (helpSetPath != null && helpSetPath.length() > 0) {
            realPath = realPath.concat(HelpUtils.URL_SEPARATOR)
            .concat(helpSetPath).concat(HelpUtils.URL_SEPARATOR);
        }
        
        return realPath;
    }
    
    /**
     * Set the value of the helpSetPath managed bean property.
     *
     * @param path The value of the helpSetPath managed bean property.
     */
    public void setHelpSetPath(String path) {
        helpSetPath = path;
    }
    
    /**
     * Get the value of the jspPath managed bean property.
     *
     * @return The value of the jspPath managed bean property.
     */
    public String getJspPath() {
        return jspPath;
    }
    
    /**
     * Set the value of the jspPath managed bean property.
     *
     * @param path The value of the jspPath managed bean property.
     */
    public void setJspPath(String path) {
        jspPath = path;
    }
    
    /**
     * Convenience method to get the current instance of HelpUtils, initalizing
     * a new instance if necessary.
     *
     * @return The current instance of HelpUtils.
     */
    private HelpUtils getHelpUtils() {
        if (helpUtils == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest)
            facesContext.getExternalContext().getRequest();
            
            String requestHelpSetPath = request.getParameter("helpSetPath");
            
            if (requestHelpSetPath != null) {
                setHelpSetPath(requestHelpSetPath);
            }
            
            helpUtils = new HelpUtils(request, getHelpSetPath(), httpPort,
                    getRequestScheme());
        }
        
        return helpUtils;
    }
    
    /**
     * This method will initialize the Tree component and populate it with the
     * nodes returned by the help system.
     */
    protected void initTree(Tree tree, ArrayList treeList) {
        
        // create a HashMap to store the nodes we create
        HashMap uiNodeMap = new HashMap();
        
        // JavaHelp will return swing tree nodes, we must convert to ui nodes
        DefaultMutableTreeNode javaNode = null;
        javax.swing.tree.TreeNode parentJavaNode = null;
        TreeNode uiNode = null;
        TreeNode uiParent = null;
        TreeItem item = null;
        int numUiNodes = -1;
        HashMap nodeIDMap = new HashMap();
        boolean tocTree = tree.getId().equals(HELP_CONTENTS_TREE_ID);
        
        int nTreeNodes = treeList.size();
        
        for (int i = 0; i < nTreeNodes; i++) {
            // get the jtree node from the list
            javaNode = (DefaultMutableTreeNode) treeList.get(i);
            if (javaNode == null) {
                // TODO - debug
                continue;
            }
            
            // get the help data associated with this help jtree node
            item = (TreeItem) javaNode.getUserObject();
            if (item == null) {
                // TODO - debug
                continue;
            }
            
            // need to add a TreeNode for this help item
            numUiNodes++;
            
            // assign a unique id to use for the JSF TreeNode component id
            String jsfId = "node" + Integer.toString(numUiNodes);
                        
            // get the java help assigned id
            String javaNodeId = getHelpUtils().getID(javaNode);
            
            // Save the javaNodeId to jsfID mapping so we can retrieve parent
            // help tree nodes later. Nodes are added to parent objects below
            // based on the parent ID indicated in the node object.
            
            // store a mapping of the java help node id to our jsf node id
            nodeIDMap.put(javaNodeId, jsfId);
            
            // Get the parent ID for this tree node. This is used below to get
            // the parent node object from the tree model. The current
            // node will then be added to the existing parent node.
            parentJavaNode = javaNode.getParent();
            String parentJavaNodeId = getHelpUtils().getID(parentJavaNode);
            
            String label = item.getName();
            uiNode = new com.sun.webui.jsf.component.TreeNode();
            uiNode.setTarget(CONTENT_FRAME_NAME);
            
            uiNode.setId(jsfId);
            uiNode.setText(label);
            
            String url = getHelpUtils().getContentURL(item);
            
            if (getHttpPort() != -1) {
                int portStartIndex = url.indexOf(':', url.indexOf(':') + 1) + 1;
                int portEndIndex = url.indexOf('/', portStartIndex);
                String port = url.substring(portStartIndex, portEndIndex);
                String httpPort = String.valueOf(getHttpPort());
                
                url = url.replaceFirst(port, httpPort);
            }
            
            uiNode.setUrl(url);
            
            uiParent = (com.sun.webui.jsf.component.TreeNode)
            uiNodeMap.get(nodeIDMap.get(parentJavaNodeId));
            
            if (uiParent != null) {
                uiParent.getChildren().add(uiNode);
                uiParent.setExpanded(true);
            } else {
                tree.getChildren().add(uiNode);
            }
            
            // the JSF ui node has been constructed and added to the tree... now
            // save a mapping of the uiNodeId to the actual
            // com.sun.webui.jsf.component.TreeNode itself for retrieving parent
            // nodes later
            uiNodeMap.put(uiNode.getId(), uiNode);
            
            // If this is the helpFile param passed into the helpWindow tag,
            // set it as the selected node.
            /*
            if (treeName.equals(CHILD_TOCTREE)
                    && loadURL != null
                    && navNode.getValue().equals(loadURL)) {
                loadID = id;
            }
             */
        }
    }
    
    /** Initialize the search results panel group */
    private void initSearchResultsPanel() {
        searchResultsPanel = new PanelGroup();
        searchResultsPanel.setId("searchResultsPanel");
    }
    
    /** Convenience method to return the current Theme. */
    private Theme getTheme() {
        if (theme == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            theme = ThemeUtilities.getTheme(context);
        }
        
        return theme;
    }
    
    /** Initialize the search panel that lays out the search tab. */
    private void initSearchPanel() {
        searchPanel = new PanelGroup();
        searchPanel.setId("searchPanel");
        List panelKids = searchPanel.getChildren();
        
        searchPanel.setRendered(false);
    }
    
    // ACTION METHODS
    
    /**
     * The action method invoked when the contents tab is clicked.
     */
    public void contentsTabClicked() {
        getContentsTree().setRendered(true);
        getIndexTree().setRendered(false);
        getSearchPanel().setRendered(false);
    }
    
    /**
     * The action method invoked when the index tab is clicked.
     */
    public void indexTabClicked() {
        getContentsTree().setRendered(false);
        getIndexTree().setRendered(true);
        getSearchPanel().setRendered(false);
    }
    
    /**
     * The action method invoked when the search tab is clicked.
     */
    public void searchTabClicked() {
        getContentsTree().setRendered(false);
        getIndexTree().setRendered(false);
        getSearchPanel().setRendered(true);
    }
    
    /**
     * The action method invoked when the search button is clicked.
     */
    public void doSearch() {
        // get the string the user wants to search for
        Markup mu = (Markup) searchPanel.getChildren().get(0);
        TextField f = (TextField) mu.getChildren().get(0);
        String searchText =
                ConversionUtilities.convertValueToString(f, f.getValue());
        
        // get the search results panel child list and clear it
        List resultsKids = getSearchResultsPanel().getChildren();
        resultsKids.clear();
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        Enumeration searchResults = getHelpUtils().doSearch(searchText);
        
        if (searchResults == null || !searchResults.hasMoreElements()) {
            // search text produced no results
            StaticText text = new StaticText();
            text.setId("noResults");
            text.setStyleClass(
                    theme.getStyleClass(ThemeStyles.HELP_RESULT_DIV));
            text.setText(theme.getMessage("help.noResultsFound"));
            resultsKids.add(text);
            return;
        }
        
        int linkId = 0;
        Hyperlink resultLink = null;
        Markup markup = null;
        SearchTOCItem item = null;
        
        while (searchResults.hasMoreElements()) {
            item = (SearchTOCItem) searchResults.nextElement();
            
            Markup div = new Markup();
            div.setId("div" + linkId);
            div.setTag("div");
            div.setStyle("padding-top:6px; white-space: nowrap");
            resultLink = new Hyperlink();
            resultLink.setId("searchLink" + linkId++);
            resultLink.setUrl(item.getURL().toString());
            resultLink.setText(item.getName());
            resultLink.setTarget(CONTENT_FRAME_NAME);
            resultLink.setStyleClass(
                    theme.getStyleClass(ThemeStyles.HELP_RESULT_DIV));
            div.getChildren().add(resultLink);
            resultsKids.add(div);
        }
    }
    
    // COMPONENT BINDING METHODS
    
    /**
     * Returns the index tab tree.
     *
     * @return The Tree component for the index tree.
     */
    public Tree getIndexTree() {
        if (indexTree == null) {
            indexTree = new Tree();
            indexTree.setId(HELP_INDEX_TREE_ID);
            ClientSniffer cs = 
                ClientSniffer.getInstance(FacesContext.getCurrentInstance());
            if (cs.isIe6up()) {
                contentsTree.setStyle("width:40em;");
            }
            initTree(indexTree, getHelpUtils().getIndexTreeList());
            
            indexTree.setRendered(false);
        }
        
        return indexTree;
    }
    
    /**
     * Sets the index tab tree.
     *
     * @param tree The Tree to use for the index tab tree.
     */
    public void setIndexTree(Tree tree) {
        indexTree = tree;
    }
    
    /**
     * Returns the contents tab tree.
     *
     * @return The Tree component for the contents tab.
     */
    public Tree getContentsTree() {
        if (contentsTree == null) {
            contentsTree = new Tree();
            contentsTree.setId(HELP_CONTENTS_TREE_ID);
            ClientSniffer cs = 
                ClientSniffer.getInstance(FacesContext.getCurrentInstance());
            if (cs.isIe6up()) {
                contentsTree.setStyle("width:40em;");
            }
            initTree(contentsTree, getHelpUtils().getTOCTreeList());
        }
        
        return contentsTree;
    }
    
    /**
     * Sets the contents tab tree.
     *
     * @param tree The Tree to use for the contents tab tree.
     */
    public void setContentsTree(Tree tree) {
        contentsTree = tree;
    }
    
    /**
     * Get the PanelGroup to use for the search tab content.
     *
     * @return The PanelGroup to use for the search tab content.
     */
    public PanelGroup getSearchPanel() {
        if (searchPanel == null) {
            initSearchPanel();
        }
        
        return searchPanel;
    }
    
    /**
     * Set the PanelGroup to use for the search tab content.
     *
     * @param panel The PanelGroup to use for the search tab content.
     */
    public void setSearchPanel(PanelGroup panel) {
        searchPanel = panel;
    }
    
    /**
     * Get the PanelGroup to use for the search results.
     *
     * @return The PanelGroup to use for the search results.
     */
    public PanelGroup getSearchResultsPanel() {
        if (searchResultsPanel == null) {
            initSearchResultsPanel();
        }
        
        return searchResultsPanel;
    }
    
    /**
     * Set the PanelGroup to use for the search results.
     *
     * @param panel The PanelGroup to use for the search results.
     */
    public void setSearchResultsPanel(PanelGroup panel) {
        searchResultsPanel = panel;
    }
    
    Hyperlink tipsLink = null;
    
    public Hyperlink getTipsLink() {
        if (tipsLink == null) {
            // init the tips on searching link
            tipsLink = new Hyperlink();
            tipsLink.setId("searchTipsLink");
            Theme theme = 
                ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            tipsLink.setText(theme.getMessage("help.tips"));
            StringBuffer tipsUrlBuffer = new StringBuffer();
            tipsUrlBuffer.append(getJspPath() != null ? "/".concat(getJspPath()) : "")
                .append(HelpWindow.DEFAULT_JSP_PATH)
                .append(HelpWindow.DEFAULT_TIPS_FILE);
            FacesContext context = FacesContext.getCurrentInstance();
            tipsLink.setUrl(context.getApplication().getViewHandler()
                .getActionURL(context, tipsUrlBuffer.toString()));
            tipsLink.setTarget(CONTENT_FRAME_NAME);
        }
        
        return tipsLink;
    }
    
    public void setTipsLink(Hyperlink link) {
        tipsLink = link;
    }
    
    // RESOURCE BINDINGS
    

    public String getTipsTitle() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsTitle = theme.getMessage("help.tips");
        return tipsTitle;
    }
    
    public String getTipsImprove() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsImprove = theme.getMessage("help.tipsImprove");
        return tipsImprove;
    }
    
    public String getTipsImprove1() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsImprove1 = theme.getMessage("help.tipsImprove1");
        return tipsImprove1;
    }
    
    public String getTipsImprove2() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsImprove2 = theme.getMessage("help.tipsImprove2");
        return tipsImprove2;
    }
    
    public String getTipsImprove3() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsImprove3 = theme.getMessage("help.tipsImprove3");
        return tipsImprove3;
    }
    
    public String getTipsImprove4() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsImprove4 = theme.getMessage("help.tipsImprove4");
        return tipsImprove4;
    }
    
    public String getTipsNote() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsNote = theme.getMessage("help.tipsNote");
        return tipsNote;
    }
    
    public String getTipsNoteDetails() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsNoteDetails = theme.getMessage("help.tipsNoteDetails");
        return tipsNoteDetails;
    }
    
    public String getTipsSearch() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsSearch = theme.getMessage("help.tipsSearch");
        return tipsSearch;
    }
    
    public String getTipsSearch1() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsSearch1 = theme.getMessage("help.tipsSearch1");
        return tipsSearch1;
    }
    
    public String getTipsSearch2() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsSearch2 = theme.getMessage("help.tipsSearch2");
        return tipsSearch2;
    }
    
    public String getTipsSearch3() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsSearch3 = theme.getMessage("help.tipsSearch3");
        return tipsSearch3;
    }
    
    public String getTipsSearch4() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        tipsSearch4 = theme.getMessage("help.tipsSearch4");
        return tipsSearch4;
    }
    
    public String getBackButtonText() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        backButtonText = theme.getMessage("help.backButtonTitle");
        return backButtonText;
    }
    
    public String getForwardButtonText() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        forwardButtonText = theme.getMessage("help.forwardButtonTitle");
        return forwardButtonText;
    }
    
    public String getPrintButtonText() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        printButtonText = theme.getMessage("help.printButtonTitle");
        return printButtonText;
    }
    
    public String getContentsText() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        contentsText = theme.getMessage("help.contentsTab");
        return contentsText;
    }
    
    public String getIndexText() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        indexText = theme.getMessage("help.indexTab");
        return indexText;
    }
    
    public String getSearchText() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        searchText = theme.getMessage("help.searchTab");
        return searchText;
    }
    
    String localizedHelpPath = null;
    
    public String getLocalizedHelpPath() {        
        return getHelpUtils().getLocalizedHelpPath();        
    }
    
    public void setLocalizedHelpPath(String s) {
        localizedHelpPath = s;
    }
    
    public String getBackButtonIcon() {
        return ThemeImages.HELP_BACK;
    }
    
    public String getForwardButtonIcon() {
        return ThemeImages.HELP_FORWARD;
    }
    
    public String getPrintButtonIcon() {
        return ThemeImages.HELP_PRINT;
    }
    
    public int getHttpPort() {
        return httpPort;
    }
    
    public void setHttpPort(int port) {
        httpPort = port;
    }
    
    public String getSearchLabel() {
        if (searchLabel == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            searchLabel = theme.getMessage("help.searchButton");
        }
        return searchLabel;
    }
    
    public void setSearchLabel(String s) {
        searchLabel = s;
    }
    
    public String getNavFrameTitle() {
        if (navFrameTitle == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            navFrameTitle = theme.getMessage("help.navFrameTitle");
        }
        return navFrameTitle;
    }
    
    public void setNavFrameTitle(String s) {
        navFrameTitle = s;
    }
    
    public String getButtonFrameTitle() {
        if (buttonFrameTitle == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            buttonFrameTitle = theme.getMessage("help.buttonFrameTitle");
        }
        return buttonFrameTitle;
    }
    
    public void setButtonFrameTitle(String s) {
        buttonFrameTitle = s;
    }
    
    public String getContentFrameTitle() {
        if (contentFrameTitle == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            contentFrameTitle = theme.getMessage("help.contentFrameTitle");
        }
        return contentFrameTitle;
    }
    
    public void setContentFrameTitle(String s) {
        contentFrameTitle = s;
    }
    
    public String getNoFrames() {
        if (noFrames == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            noFrames = theme.getMessage("help.noframes");
        }
        return noFrames;
    }
    
    public void setNoFrames(String s) {
        noFrames = s;
    }

    public String getButtonNavHeadTitle() {
        if (buttonNavHeadTitle == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            buttonNavHeadTitle = theme.getMessage("help.buttonNavHeadTitle");
        }
        return buttonNavHeadTitle;
    }
    
    public void setButtonNavHeadTitle(String s) {
        buttonNavHeadTitle = s;
    }
    
    public String getNavigatorHeadTitle() {
        if (navigatorHeadTitle == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            navigatorHeadTitle = theme.getMessage("help.navigatorHeadTitle");
        }
        return navigatorHeadTitle;
    }
    
    public void setNavigatorHeadTitle(String s) {
        navigatorHeadTitle = s;
    }
    
    public String getTipsHeadTitle() {
        if (tipsHeadTitle == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            tipsHeadTitle = theme.getMessage("help.tips");
        }
        return tipsHeadTitle;
    }
    
    public void setTipsHeadTitle(String s) {
        tipsHeadTitle = s;
    }
    
    private int checkParam(String paramName) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map parms = context.getExternalContext().getRequestParameterMap();
        
        String paramValue = (String) parms.get(paramName);
        try {
            if (paramValue != null && Integer.parseInt(paramValue) != -1) {
                return Integer.parseInt(paramValue);
            }
        } catch (NumberFormatException nfe) {
            if (LogUtil.infoEnabled()) {
                LogUtil.info(HelpBackingBean.class, "WEBUI0007",
                        new String[] { paramName });
            }
        }
        
        return -1;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Style selectors used in JSP pages
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Used in navigator.jsp and tips.jsp.
    
    
    public String getBodyClassName() {
        return bodyClassName;
    }

    public String getButtonClassName() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        buttonClassName = theme.getStyleClass(ThemeStyles.HELP_BUTTON_DIV);
        return buttonClassName;
    }

    public String getinlineHelpClassName() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        inlineHelpClassName = theme.getStyleClass(ThemeStyles.HELP_FIELD_TEXT);
        return inlineHelpClassName;
    }
    
    public String getSearchClassName() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        searchClassName = theme.getStyleClass(ThemeStyles.HELP_SEARCH_DIV);
        return searchClassName;
    }
    
    public String getStepTabClassName() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        stepTabClassName = theme.getStyleClass(ThemeStyles.HELP_STEP_TAB);
        return stepTabClassName;
    }
    
    public String getTitleClassName() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        titleClassName = theme.getStyleClass(ThemeStyles.TITLE_LINE);
        return titleClassName;
    }

    public String getbuttonBodyClassName() {
        Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
        bodyClassName = theme.getStyleClass(ThemeStyles.HELP_BODY);
        return buttonBodyClassName;
    }

}
