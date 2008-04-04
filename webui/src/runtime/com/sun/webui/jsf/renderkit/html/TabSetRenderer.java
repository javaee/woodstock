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

/*
 * TabSetRenderer.java
 */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.util.List;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.component.Tab;
import com.sun.webui.jsf.component.TabSet;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import javax.el.MethodExpression;

/**
 * Renders a TabSet component.
 *
 * @author  Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.TabSet"))
public class TabSetRenderer extends AbstractRenderer {
    
    private static final String SKIP_ANCHOR_NAME = "tabSetSkipAnchor"; // NOI18N
    private static final String SELECTED_TAB_ANCHOR_NAME = "selectedTabAnchor"; // NOI18N
    private static final String EMPTY_STR = ""; // NOI18N
    private static final String SPACE = " "; // NOI18N
    
    /** Default constructor */
    public TabSetRenderer() {
        super();
    }
    
    /**
     * <p>Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns <code>false</code>.</p>
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    /**
     * <p>Render the end tag for this component.</p>
     *
     * @param context The current FacesContext.
     * @param component The current TabSet component.
     * @param writer The current ResponseWriter.
     */
    public void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // render any kids of the selected tab component now
        TabSet tabSet = (TabSet) component;
        String selectedTabId = tabSet.getSelected();
        
        Theme theme = ThemeUtilities.getTheme(context);
        String lite = theme.getStyleClass(ThemeStyles.TABGROUPBOX);
        
        if (selectedTabId == null) {
            if (tabSet.isMini() && tabSet.isLite()) {
                writer.startElement("div", tabSet); //NOI18N
                writer.writeAttribute("class", lite, null); //NOI18N
                writer.endElement("div"); //NOI18N
            }
            writer.endElement("div"); //NOI18N
            
            return;
        }
        
        Tab selectedTab = tabSet.findChildTab(selectedTabId);
        if (selectedTab == null) {
            if (tabSet.isMini() && tabSet.isLite()) {
                writer.startElement("div", tabSet); //NOI18N
                writer.writeAttribute("class", lite, null); //NOI18N
                writer.endElement("div"); //NOI18N
            }
            writer.endElement("div"); //NOI18N
            return;
        }
        
        if (tabSet.isMini() && tabSet.isLite()) {
            writer.startElement("div", tabSet); //NOI18N
            writer.writeAttribute("class", lite, null); //NOI18N
        }
        
        while (selectedTab.getTabChildCount() > 0) {
            selectedTabId = selectedTab.getSelectedChildId();
            if (selectedTabId == null) {
                selectedTabId = ((Tab) selectedTab.getChildren().get(0)).getId();
            }
            selectedTab = (Tab) selectedTab.findComponent(selectedTabId);
        }
        
        int numKids = selectedTab.getChildCount();
        if (numKids > 0) {
            // render the contentHeader facet if specified
            UIComponent facet = tabSet.getFacet("contentHeader");
            if (facet != null) {
                RenderingUtilities.renderComponent(facet, context);
            }
            
            // render the children of the selected Tab component
            List kids = selectedTab.getChildren();
            for (int i = 0; i < numKids; i++) {
                UIComponent kid = (UIComponent) kids.get(i);
                RenderingUtilities.renderComponent(kid, context);
            }
            
            facet = tabSet.getFacet("contentFooter");
            if (facet != null) {
                RenderingUtilities.renderComponent(facet, context);
            }
        }
        if (tabSet.isMini() && tabSet.isLite()) {
            writer.endElement("div"); //NOI18N
        }
        writer.endElement("div"); //NOI18N
        
    }
    
    /**
     * <p>Encode the Tab children of this TabSet component.</p>
     *
     * @param context The current FacesContext
     * @param component The current TabSet component
     */
    public void encodeChildren(FacesContext context, UIComponent component)
    throws IOException {
        TabSet tabSet = (TabSet) component;
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        
        List level1 = tabSet.getChildren();
        
        if (level1.size() < 1) {
            // no tab children and hence no tabset
            if (LogUtil.infoEnabled()) {
                LogUtil.info(TabSetRenderer.class, "WEBUI0005",
                        new String[] { tabSet.getId() });
            }
            return;
        }
        
        // open the initial div containing the entire tab set
        startTabSetDiv(context, writer, tabSet, theme);
        
        // render the a11y skip link
        renderSkipLink(context, tabSet, theme);
        
        // render the first level of tabs and get the 2nd level if any
        List level2Tabs =
                renderLevel(context, tabSet, writer, 1, tabSet.getChildren());
        
        // if there are any level 2 tabs render those now
        if (level2Tabs != null) {
            List level3Tabs =
                    renderLevel(context, tabSet, writer, 2, level2Tabs);
            
            // if there are any level 3 tabs render those now
            if (level3Tabs != null) {
                renderLevel(context, tabSet, writer, 3, level3Tabs);
            }
        }
        
        // output the bookmark for the SkipHyperlink
        RenderingUtilities.renderAnchor(SKIP_ANCHOR_NAME, tabSet, context);
    }
    
    /**
     * Helper function called by encodeChildren to open the TabSet div.
     */
    private void startTabSetDiv(FacesContext context, ResponseWriter writer,
            TabSet tabSet, Theme theme) throws IOException {
        String style = tabSet.getStyle();
        String styleClass = tabSet.getStyleClass();
        
        String lite = theme.getStyleClass(ThemeStyles.TABGROUP);
        
        if (tabSet.isMini() && tabSet.isLite()) {
            if (styleClass != null) {
                styleClass = styleClass.concat(SPACE).concat(lite);
            } else {
                styleClass = lite;
            }
        }
        
        if (!tabSet.isVisible()) {
            String hiddenStyle = theme.getStyleClass(ThemeStyles.HIDDEN);
            if (styleClass == null) {
                styleClass = hiddenStyle;
            } else {
                styleClass = styleClass.concat(SPACE).concat(hiddenStyle);
            }
        }
        
        writer.startElement("div", tabSet);
        
        // use component client id for enclosing div id
        String clientId = tabSet.getClientId(context);
        
        if (clientId != null) {
            writer.writeAttribute("id", clientId, null);
        }
        
        if (style != null) {
            writer.writeAttribute("style", style, null); // NOI18N
        }
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, null); // NOI18N
        }
    }
    
    private String[] getStyles(TabSet tabSet, Theme theme, int level) {
        // get the various level specific tab styles we'll need
        String divStyle = EMPTY_STR;
        String tableStyle = EMPTY_STR;
        String linkStyle = EMPTY_STR;
        String selectedTdStyle = EMPTY_STR;
        String selectedTextStyle = EMPTY_STR;
        
        switch (level) {
            case 1: // get the level 1 tab styles
                if (tabSet.isMini()) {
                    divStyle = theme.getStyleClass(ThemeStyles.MINI_TAB_DIV);
                    tableStyle =
                            theme.getStyleClass(ThemeStyles.MINI_TAB_TABLE);
                    linkStyle = theme.getStyleClass(ThemeStyles.MINI_TAB_LINK);
                    selectedTdStyle = theme.getStyleClass(
                            ThemeStyles.MINI_TAB_TABLE_SELECTED_TD);
                    selectedTextStyle =
                            theme.getStyleClass(ThemeStyles.MINI_TAB_SELECTED_TEXT);
                } else {
                    divStyle = theme.getStyleClass(ThemeStyles.TAB1_DIV);
                    tableStyle =
                            theme.getStyleClass(ThemeStyles.TAB1_TABLE_NEW);
                    linkStyle = theme.getStyleClass(ThemeStyles.TAB1_LINK);
                    selectedTdStyle =
                            theme.getStyleClass(ThemeStyles.TAB1_TABLE_SELECTED_TD);
                    selectedTextStyle =
                            theme.getStyleClass(ThemeStyles.TAB1_SELECTED_TEXT_NEW);
                }
                break;
            case 2: // get the level 2 tab styles
                divStyle = theme.getStyleClass(ThemeStyles.TAB2_DIV);
                tableStyle = theme.getStyleClass(ThemeStyles.TAB2_TABLE_NEW);
                linkStyle = theme.getStyleClass(ThemeStyles.TAB2_LINK);
                selectedTdStyle =
                        theme.getStyleClass(ThemeStyles.TAB2_TABLE_SELECTED_TD);
                selectedTextStyle =
                        theme.getStyleClass(ThemeStyles.TAB2_SELECTED_TEXT);
                break;
            case 3: // get the level 3 tab styles
                divStyle = theme.getStyleClass(ThemeStyles.TAB3_DIV);
                tableStyle = theme.getStyleClass(ThemeStyles.TAB3_TABLE_NEW);
                linkStyle = theme.getStyleClass(ThemeStyles.TAB3_LINK);
                selectedTdStyle =
                        theme.getStyleClass(ThemeStyles.TAB3_TABLE_SELECTED_TD);
                selectedTextStyle =
                        theme.getStyleClass(ThemeStyles.TAB3_SELECTED_TEXT);
                break;
        }
        
        String[] styles = new String[] {
            divStyle, tableStyle, linkStyle, selectedTdStyle, selectedTextStyle
        };
        
        return styles;
    }
    
    /**
     * Helper function called by encodeChildren to write out the a11y skip link.
     */
    private void renderSkipLink(FacesContext context, TabSet tabSet,
            Theme theme) throws IOException {
        // need the label of currently selected tab for skip alt text
        Tab selectedTab = tabSet.findChildTab(tabSet.getSelected());
        
        String[] args = new String[] { EMPTY_STR };
        if (selectedTab != null) {
            Object obj = selectedTab.getText();
            if (obj != null) {
                args[0] = ConversionUtilities.convertValueToString(
                        selectedTab, obj);
            }
        }
        
        // render the skip link for a11y
        String toolTip = theme.getMessage("tab.skipTagAltText", args); //NOI18N
        String styleClass = theme.getStyleClass(ThemeStyles.SKIP_MEDIUM_GREY1);
        RenderingUtilities.renderSkipLink(SKIP_ANCHOR_NAME, styleClass, null,
                toolTip, null, tabSet, context);
    }
    
    private void layoutLevel(ResponseWriter writer, TabSet tabSet,
            String[] styles) throws IOException {
        writer.startElement("div", tabSet);
        writer.writeAttribute("class", styles[0], null); // NOI18N
        writer.startElement("table", tabSet); // NOI18N
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("cellspacing", "0", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeAttribute("class", styles[1], null); // NOI18N
        writer.writeAttribute("title", EMPTY_STR, null); // NOI18N
        writer.startElement("tr", tabSet); //NOI18N
    }
    
    /**
     * This method renders each of the Tab components in the given level.
     *
     * @param context The current FacesContext
     * @param tabSet The current TabSet component
     * @param writer The current ResponseWriter
     * @param level The level (1, 2 or 3) of the Tab set to be rendered
     * @param currentLevelTabs A List containing the Tab objects for the current
     *  level
     */
    protected List renderLevel(FacesContext context, TabSet tabSet,
            ResponseWriter writer, int level, List currentLevelTabs)
            throws IOException {
        int numTabs = currentLevelTabs.size();
        
        if (numTabs == 0) {
            // no tabs in given level
            return null;
        }
        
        Theme theme = ThemeUtilities.getTheme(context);
        String[] styles = getStyles(tabSet, theme, level);
        String selectedTabId = tabSet.getSelected();
        Tab currentLevelSelection = null;
        
        // need to ensure at least one tab in this level is selected
        boolean levelHasSelection = false;
        
        for (int i = 0; i < numTabs; i++) {
            try {
                currentLevelSelection = (Tab) currentLevelTabs.get(i);
            } catch (ClassCastException cce) {
                // not a Tab instance
                continue;
            }
            
            if (isSelected(currentLevelSelection, selectedTabId)) {
                // sTab is either selected or part of selection
                levelHasSelection = true;
                break;
            }
        }
        
        if (!levelHasSelection) {
            try {
                selectedTabId = ((Tab) currentLevelTabs.get(0)).getId();
                tabSet.setSelected(selectedTabId);
            } catch (ClassCastException cce) {
                // gave it a shot but failed... no tab will be selected
            }
        }
        
        if (currentLevelSelection != null &&
                (currentLevelSelection.getTabChildCount() > 0)) {
            // selected tab in this level has children - must adjust table style
            switch (level) {
                case 1:
                    styles[1] =
                            theme.getStyleClass(ThemeStyles.TAB1_TABLE2_NEW);
                    break;
                case 2:
                    styles[1] =
                            theme.getStyleClass(ThemeStyles.TAB2_TABLE3_NEW);
                    break;
                default:
                    break;
            }
        }
        
        // open the div, table and tr element for this level of tabs
        layoutLevel(writer, tabSet, styles);
        
        // get the developer specified binding for action listener
        MethodExpression actionListenerExpression = tabSet.getActionListenerExpression();
        
        // need a variable to save next level of tabs if we have one
        List nextLevelToRender = null;
        
        // render each tab in this level
        for (int i = 0; i < numTabs; i++) {
            Tab tab = null;
            
            try {
                tab = (Tab) currentLevelTabs.get(i);
            } catch (ClassCastException cce) {
                // expected if a child of current Tab is not another Tab
                continue;
            }
            
            if (!tab.isRendered())
                continue;
            
            // each tab goes in its own table cell
            writer.startElement("td", tabSet);
            
            if (!tab.isVisible()){
                writer.writeAttribute("class",theme.getStyleClass(ThemeStyles.HIDDEN), null);
            }
            String newSelectedClass = styles[3];
            
            if (selectedTabId != null && isSelected(tab, selectedTabId)) {
                // this tab or one of it's children is selected
                nextLevelToRender = renderSelectedTab(context, writer, theme,
                        tabSet, tab, styles, newSelectedClass);
            } else {
                // not part of current selection
                tab.setStyleClass(styles[2]);
                
                RenderingUtilities.renderComponent(tab, context);
            }
            
            writer.endElement("td");
        }
        
        writer.endElement("tr");
        writer.endElement("table");
        writer.endElement("div");
        
        return nextLevelToRender;
    }
    
    private List renderSelectedTab(FacesContext context, ResponseWriter writer,
            Theme theme, TabSet tabSet, Tab tab, String[] styles,
            String selectedClass) throws IOException {
        UIComponent parent = tab.getParent();
        
        if (parent != null && parent instanceof Tab) {
            if (tabSet.isLastSelectedChildSaved()) {
                // ensure that the parent tab knows this one is selected
                ((Tab) parent).setSelectedChildId(tab.getId());
            } else {
                // forget last selected child
                ((Tab) parent).setSelectedChildId(null);
            }
        }
        
        String label = EMPTY_STR;
        Object obj = tab.getText();
        if (obj != null) {
            label = ConversionUtilities.convertValueToString(tab, obj);
        }
        
        String selectionDivClass = styles[4];
        
        if (label.length() < 6) {
            // short label, apply TabPad style class to div enclosing selection
            String padClass = theme.getStyleClass(ThemeStyles.TAB_PADDING);
            selectionDivClass =
                    selectionDivClass.concat(SPACE).concat(padClass);
        }
        selectionDivClass = 
                RenderingUtilities.getStyleClasses(context, tab, selectionDivClass);
        writer.writeAttribute("class", selectedClass, null); // NOI18N
        writer.startElement("div", tab); //NOI18N
        String style = tab.getStyle();
        if (style != null && style.length() > 0) {
            writer.writeAttribute("style", style, null);
        }
        writer.writeAttribute("class", selectionDivClass, null); // NOI18N
        String titleString = theme.getMessage(
                "tabSet.selectedTab", new Object[] { label }); //NOI18N
        writer.writeAttribute("title", titleString, null); //NOI18N        

        // Write a named anchor for the selected tab, so that focus will return to 
        // the current tab after it is selected. In that way tab focus will next
        // shift to the first input component that is a child of the selected tab.
        writer.startElement("a", tab); //NOI18N
        writer.writeAttribute("id", tab.getClientId(context), "id"); // NOI18N
        writer.writeAttribute("name", SELECTED_TAB_ANCHOR_NAME, "name"); // NOI18N
        writer.endElement("a"); //NOI18N
        
        // just write the label of the selected tab
        writer.write(label);
        
        writer.endElement("div"); //NOI18N
        
        // return any children of the selected tab to render as next level
        return tab.getTabChildCount() == 0 ? null : tab.getTabChildren();
    }
    
    /**
     * Utility method that determines if the given Tab component or any one
     * of its descendants is the selected tab.
     *
     * @param tab The Tab component to check for selection
     * @param selectedTabId The id of the currently selected Tab
     */
    protected boolean isSelected(Tab tab, String selectedTabId) {
        if (selectedTabId == null)
            return false;
        if (selectedTabId.equals(tab.getId()))
            return true;
        if (tab.getTabChildCount() == 0)
            return false;
        for (Tab child : tab.getTabChildren()) {
            if (isSelected(child, selectedTabId))
                return true;
        }
        return false;
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        // TabSet does not encode or decode its input value. The input
        // value is determined by the selected and/or current child
        // tab component.
    }
    
}
