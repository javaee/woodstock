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
 

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import com.sun.webui.jsf.event.TablePaginationActionListener;
import com.sun.webui.jsf.event.TableSortActionListener;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ClientSniffer;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

/**
 * Component that represents a table action bar.
 * <p>
 * Note: Column headers and footers are rendered by TableRowGroupRenderer. Table
 * column footers are rendered by TableRenderer.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.component.TableActions.level = FINE
 * </pre></p>
 */
@Component(type="com.sun.webui.jsf.TableActions", 
    family="com.sun.webui.jsf.TableActions", displayName="Actions", isTag=false)
public class TableActions extends UIComponentBase implements NamingContainer {
    /** The component id for the actions separator icon. */
    public static final String ACTIONS_SEPARATOR_ICON_ID = "_actionsSeparatorIcon"; //NOI18N

    /** The facet name for the actions separator icon. */
    public static final String ACTIONS_SEPARATOR_ICON_FACET = "actionsSeparatorIcon"; //NOI18N

    /** The component id for the clear sort button. */
    public static final String CLEAR_SORT_BUTTON_ID = "_clearSortButton"; //NOI18N

    /** The facet name for the clear sort button. */
    public static final String CLEAR_SORT_BUTTON_FACET = "clearSortButton"; //NOI18N

    /** The component id for the deselect multiple button. */
    public static final String DESELECT_MULTIPLE_BUTTON_ID = "_deselectMultipleButton"; //NOI18N

    /** The facet name for the deselect multiple button. */
    public static final String DESELECT_MULTIPLE_BUTTON_FACET = "deselectMultipleButton"; //NOI18N

    /** The component id for the deselect single button. */
    public static final String DESELECT_SINGLE_BUTTON_ID = "_deselectSingleButton"; //NOI18N

    /** The facet name for the deselect single button. */
    public static final String DESELECT_SINGLE_BUTTON_FACET = "deselectSingleButton"; //NOI18N

    /** The component id for the filter label. */
    public static final String FILTER_LABEL_ID = "_filterLabel"; //NOI18N

    /** The facet name for the filter label. */
    public static final String FILTER_LABEL_FACET = "filterLabel"; //NOI18N

    /** The component id for the filter separator icon. */
    public static final String FILTER_SEPARATOR_ICON_ID = "_filterSeparatorIcon"; //NOI18N

    /** The facet name for the filter separator icon. */
    public static final String FILTER_SEPARATOR_ICON_FACET = "filterSeparatorIcon"; //NOI18N

    /** The component id for the paginate button. */
    public static final String PAGINATE_BUTTON_ID = "_paginateButton"; //NOI18N

    /** The facet name for the paginate button. */
    public static final String PAGINATE_BUTTON_FACET = "paginateButton"; //NOI18N

    /** The component id for the paginate separator icon. */
    public static final String PAGINATE_SEPARATOR_ICON_ID = "_paginateSeparatorIcon"; //NOI18N

    /** The facet name for the paginate separator icon. */
    public static final String PAGINATE_SEPARATOR_ICON_FACET = "paginateSeparatorIcon"; //NOI18N

    /** The component id for the pagination first button. */
    public static final String PAGINATION_FIRST_BUTTON_ID = "_paginationFirstButton"; //NOI18N

    /** The facet name for the pagination first button. */
    public static final String PAGINATION_FIRST_BUTTON_FACET = "paginationFirstButton"; //NOI18N

    /** The component id for the pagination last button. */
    public static final String PAGINATION_LAST_BUTTON_ID = "_paginationLastButton"; //NOI18N

    /** The facet name for the pagination last button. */
    public static final String PAGINATION_LAST_BUTTON_FACET = "paginationLastButton"; //NOI18N

    /** The component id for the pagination next button. */
    public static final String PAGINATION_NEXT_BUTTON_ID = "_paginationNextButton"; //NOI18N

    /** The facet name for the pagination next button. */
    public static final String PAGINATION_NEXT_BUTTON_FACET = "paginationNextButton"; //NOI18N

    /** The component id for the pagination page field. */
    public static final String PAGINATION_PAGE_FIELD_ID = "_paginationPageField"; //NOI18N

    /** The facet name for the pagination page field. */
    public static final String PAGINATION_PAGE_FIELD_FACET = "paginationPageField"; //NOI18N

    /** The component id for the pagination pages text. */
    public static final String PAGINATION_PAGES_TEXT_ID = "_paginationPagesText"; //NOI18N

    /** The facet name for the pagination pages text. */
    public static final String PAGINATION_PAGES_TEXT_FACET = "paginationPagesText"; //NOI18N

    /** The component id for the pagination previous button. */
    public static final String PAGINATION_PREV_BUTTON_ID = "_paginationPrevButton"; //NOI18N

    /** The facet name for the pagination previous button. */
    public static final String PAGINATION_PREV_BUTTON_FACET = "paginationPrevButton"; //NOI18N

    /** The component id for the pagination submit button. */
    public static final String PAGINATION_SUBMIT_BUTTON_ID = "_paginationSubmitButton"; //NOI18N

    /** The facet name for the pagination submit button. */
    public static final String PAGINATION_SUBMIT_BUTTON_FACET = "paginationSubmitButton"; //NOI18N

    /** The component id for the preferences panel button. */
    public static final String PREFERENCES_PANEL_TOGGLE_BUTTON_ID = "_preferencesPanelToggleButton"; //NOI18N

    /** The facet name for the preferences panel button. */
    public static final String PREFERENCES_PANEL_TOGGLE_BUTTON_FACET = "preferencesPanelToggleButton"; //NOI18N

    /** The component id for the select multiple button. */
    public static final String SELECT_MULTIPLE_BUTTON_ID = "_selectMultipleButton"; //NOI18N

    /** The facet name for the select multiple button. */
    public static final String SELECT_MULTIPLE_BUTTON_FACET = "selectMultipleButton"; //NOI18N

    /** The component id for the sort panel toggle button. */
    public static final String SORT_PANEL_TOGGLE_BUTTON_ID = "_sortPanelToggleButton"; //NOI18N

    /** The facet name for the sort panel toggle button. */
    public static final String SORT_PANEL_TOGGLE_BUTTON_FACET = "sortPanelToggleButton"; //NOI18N

    /** The component id for the view actions separator icon. */
    public static final String VIEW_ACTIONS_SEPARATOR_ICON_ID = "_viewActionsSeparatorIcon"; //NOI18N

    /** The facet name for the view actions separator icon. */
    public static final String VIEW_ACTIONS_SEPARATOR_ICON_FACET = "viewActionsSeparatorIcon"; //NOI18N

    // The Table ancestor enclosing this component.
    private Table table = null;

    /** Default constructor */
    public TableActions() {
        super();
        setRendererType("com.sun.webui.jsf.TableActions");
    }

    /**
     * Return the family for this component.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TableActions";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the closest Table ancestor that encloses this component.
     *
     * @return The Table ancestor.
     */
    public Table getTableAncestor() {
        if (table == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof Table) {
                    table = (Table) component;
                    break;
                }
            }
        }
        return table;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Pagination methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get first page button for pagination controls.
     *
     * @return The first page button.
     */
    public UIComponent getPaginationFirstButton() {
        UIComponent facet = getFacet(PAGINATION_FIRST_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get disabled state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        boolean disabled = (group != null) ? group.getFirst() <= 0 : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
	child.setId(PAGINATION_FIRST_BUTTON_ID);
        child.setIcon(disabled
            ? ThemeImages.TABLE_PAGINATION_FIRST_DISABLED
            : ThemeImages.TABLE_PAGINATION_FIRST);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        child.setDisabled(disabled);
        child.addActionListener(new TablePaginationActionListener());

        // Set tool tip.
        String toolTip = getTheme().getMessage("table.pagination.first"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Set tab index.
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPaginationFirstButton", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get pagination submit button for pagination controls.
     *
     * @return The pagination submit button.
     */
    public UIComponent getPaginationSubmitButton() {
        UIComponent facet = getFacet(PAGINATION_SUBMIT_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Button child = new Button();
        child.setId(PAGINATION_SUBMIT_BUTTON_ID);
        child.setText(getTheme().getMessage("table.pagination.submit")); //NOI18N
        child.setToolTip(getTheme().getMessage("table.pagination.submitPage")); //NOI18N
        child.addActionListener(new TablePaginationActionListener());
        
        // Set tab index.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPaginationSubmitButton", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child);        
        return child;
    }

    /**
     * Get last page button for pagination controls.
     *
     * @return The last page button.
     */
    public UIComponent getPaginationLastButton() {
        UIComponent facet = getFacet(PAGINATION_LAST_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get disabled state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        boolean disabled = (group != null) 
            ? group.getFirst() >= group.getLast() : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
        child.setId(PAGINATION_LAST_BUTTON_ID);
        child.setIcon(disabled
            ? ThemeImages.TABLE_PAGINATION_LAST_DISABLED
            : ThemeImages.TABLE_PAGINATION_LAST);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        child.setDisabled(disabled);
        child.addActionListener(new TablePaginationActionListener());

        // Set tool tip.
        String toolTip = getTheme().getMessage("table.pagination.last"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Set tab index.
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPaginationLastButton", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get next page button for pagination controls.
     *
     * @return The next page button.
     */
    public UIComponent getPaginationNextButton() {
        UIComponent facet = getFacet(PAGINATION_NEXT_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get disabled state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        boolean disabled = (group != null) 
            ? group.getFirst() >= group.getLast() : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
        child.setId(PAGINATION_NEXT_BUTTON_ID);
        child.setIcon(disabled
            ? ThemeImages.TABLE_PAGINATION_NEXT_DISABLED
            : ThemeImages.TABLE_PAGINATION_NEXT);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        child.setDisabled(disabled);
        child.addActionListener(new TablePaginationActionListener());

        // Set tool tip.
        String toolTip = getTheme().getMessage("table.pagination.next"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Set tab index.
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPaginationNextButton", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get page field for pagination controls.
     *
     * @return The page field.
     */
    public UIComponent getPaginationPageField() {
        UIComponent facet = getFacet(PAGINATION_PAGE_FIELD_FACET);
        if (facet != null) {
            return facet;
        }

        // Get current page.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        int page = (group != null) ? group.getPage() : 1;

        // Get child.
        TextField child = new TextField();
        child.setId(PAGINATION_PAGE_FIELD_ID);
        child.setText(Integer.toString(page));
        child.setOnKeyPress(getPaginationJavascript());
        child.setColumns(3); //NOI18N
        child.setLabelLevel(2);
        child.setLabel(getTheme().getMessage("table.pagination.page")); //NOI18N       

        // Set tab index.
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPaginationPageField", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get pages text for pagination controls.
     *
     * @return The pages text.
     */
    public UIComponent getPaginationPagesText() {
        UIComponent facet = getFacet(PAGINATION_PAGES_TEXT_FACET);
        if (facet != null) {
            return facet;
        }       

        Theme theme = getTheme();

        // Get child.
        StaticText child = new StaticText();
        child.setId(PAGINATION_PAGES_TEXT_ID);
        child.setStyleClass(theme.getStyleClass(
            ThemeStyles.TABLE_PAGINATION_TEXT));

        // Set page text.
        Table table = getTableAncestor();
        if (table != null) {
            child.setText(theme.getMessage("table.pagination.pages", //NOI18N
                new String[] {Integer.toString(table.getPageCount())}));
        } else {
            log("getPaginationPagesText", "Pages text not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get paginate button of pagination controls.
     *
     * @return The paginate button.
     */
    public UIComponent getPaginateButton() {
        UIComponent facet = getFacet(PAGINATE_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get paginated state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null) 
            ? table.getTableRowGroupChild() : null;
        boolean paginated = (group != null) ? group.isPaginated() : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
        child.setId(PAGINATE_BUTTON_ID);
        child.setIcon(paginated
            ? ThemeImages.TABLE_SCROLL_PAGE : ThemeImages.TABLE_PAGINATE);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        child.addActionListener(new TablePaginationActionListener());

        // Set i18n tool tip.
        String toolTip = paginated
            ? getTheme().getMessage("table.pagination.scroll") //NOI18N
            : getTheme().getMessage("table.pagination.paginated"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Set tab index.
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPaginateButton", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get previous page button for pagination controls.
     *
     * @return The previous page button.
     */
    public UIComponent getPaginationPrevButton() {
        UIComponent facet = getFacet(PAGINATION_PREV_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get disabled state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        boolean disabled = (group != null) ? group.getFirst() <= 0 : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
        child.setId(PAGINATION_PREV_BUTTON_ID);
        child.setIcon(disabled
            ? ThemeImages.TABLE_PAGINATION_PREV_DISABLED
            : ThemeImages.TABLE_PAGINATION_PREV);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        child.setDisabled(disabled);
        child.addActionListener(new TablePaginationActionListener());

        // Set tool tip.
        String toolTip = getTheme().getMessage("table.pagination.previous"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Set tab index.
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPaginationPrevButton", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Select methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get deselect multiple button.
     *
     * @return The deselect multiple button.
     */
    public UIComponent getDeselectMultipleButton() {
        UIComponent facet = getFacet(DESELECT_MULTIPLE_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get paginated state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        boolean paginated = (group != null) ? group.isPaginated() : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
	child.setId(DESELECT_MULTIPLE_BUTTON_ID);
        child.setIcon(ThemeImages.TABLE_DESELECT_MULTIPLE);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N

        // Set onClick and tab index.
        if (table != null) {
            child.setOnClick(getSelectJavascript(
                table.getDeselectMultipleButtonOnClick(), false));
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getDeselectMultipleButton", //NOI18N
                "Tab index & onClick not set, Table is null"); //NOI18N
        }

        // Get tool tip.
        String toolTip = getTheme().getMessage(paginated
            ? "table.select.deselectMultiplePaginated" //NOI18N
            : "table.select.deselectMultiple"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get deselect single button.
     *
     * @return The deselect single button.
     */
    public UIComponent getDeselectSingleButton() {
        UIComponent facet = getFacet(DESELECT_SINGLE_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get paginated state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        boolean paginated = (group != null) ? group.isPaginated() : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
	child.setId(DESELECT_SINGLE_BUTTON_ID);
        child.setIcon(ThemeImages.TABLE_DESELECT_SINGLE);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N

        // Set onClick and tab index.
        if (table != null) {
            child.setOnClick(getSelectJavascript(
                table.getDeselectSingleButtonOnClick(), false));
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getDeselectSingleButton", //NOI18N
                "Tab index & onClick not set, Table is null"); //NOI18N
        }

        // Set tool tip.
        String toolTip = getTheme().getMessage(paginated
            ? "table.select.deselectSinglePaginated" //NOI18N
            : "table.select.deselectSingle"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get select multiple button.
     *
     * @return The select multiple button.
     */
    public UIComponent getSelectMultipleButton() {
        UIComponent facet = getFacet(SELECT_MULTIPLE_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get paginated state.
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;
        boolean paginated = (group != null) ? group.isPaginated() : false;

        // Get child.
        IconHyperlink child = new IconHyperlink();
	child.setId(SELECT_MULTIPLE_BUTTON_ID);
        child.setIcon(ThemeImages.TABLE_SELECT_MULTIPLE);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N

        // Set onClick and tab index.
        if (table != null) {
            child.setOnClick(getSelectJavascript(
                table.getSelectMultipleButtonOnClick(), true));
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getSelectMultipleButton", //NOI18N
                "Tab index & onClick not set, Table is null"); //NOI18N
        }

        // Set tool tip.
        String toolTip = getTheme().getMessage(paginated
            ? "table.select.selectMultiplePaginated" //NOI18N
            : "table.select.selectMultiple"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Separator methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the actions separator icon.
     *
     * @return The top actions separator icon.
     */
    public UIComponent getActionsSeparatorIcon() {
        return getSeparatorIcon(ACTIONS_SEPARATOR_ICON_ID,
            ACTIONS_SEPARATOR_ICON_FACET);
    }

    /**
     * Get the filter separator icon.
     *
     * @return The filter separator icon.
     */
    public UIComponent getFilterSeparatorIcon() {
        return getSeparatorIcon(FILTER_SEPARATOR_ICON_ID, 
            FILTER_SEPARATOR_ICON_FACET);
    }

    /**
     * Get the paginate separator icon.
     *
     * @return The paginate separator icon.
     */
    public UIComponent getPaginateSeparatorIcon() {
        return getSeparatorIcon(PAGINATE_SEPARATOR_ICON_ID, 
            PAGINATE_SEPARATOR_ICON_FACET);
    }

    /**
     * Get the view actions separator icon.
     *
     * @return The view actions separator icon.
     */
    public UIComponent getViewActionsSeparatorIcon() {
        return getSeparatorIcon(VIEW_ACTIONS_SEPARATOR_ICON_ID, 
            VIEW_ACTIONS_SEPARATOR_ICON_FACET);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // View-changing action methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get clear sort button.
     *
     * @return The clear sort button.
     */
    public UIComponent getClearSortButton() {
        UIComponent facet = getFacet(CLEAR_SORT_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        IconHyperlink child = new IconHyperlink();
	child.setId(CLEAR_SORT_BUTTON_ID);
        child.setIcon(ThemeImages.TABLE_SORT_CLEAR);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        child.addActionListener(new TableSortActionListener());

        // Set tool tip.
        String toolTip = getTheme().getMessage(
            "table.viewActions.clearSort"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Set tab index.
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getClearSortButton", "Tab index not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get filter label.
     *
     * @return The filter label.
     */
    public UIComponent getFilterLabel() {
        UIComponent facet = getFacet(FILTER_LABEL_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Label child = new Label();
	child.setId(FILTER_LABEL_ID);
        child.setText(getTheme().getMessage("table.viewActions.filter")); //NOI18N        
        child.setLabelLevel(2);

        Table table = getTableAncestor();
        if (table != null) {
	    UIComponent tableFilterFacet = table.getFacet(Table.FILTER_FACET);
	    if (tableFilterFacet != null) {
		child.setLabeledComponent(tableFilterFacet);
	    }
        } else {
            log("getFilterLabel", "Labeled component not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get preferences panel toggle button.
     *
     * @return The preferences panel toggle button.
     */
    public UIComponent getPreferencesPanelToggleButton() {
        UIComponent facet = getFacet(PREFERENCES_PANEL_TOGGLE_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        IconHyperlink child = new IconHyperlink();
        child.setId(PREFERENCES_PANEL_TOGGLE_BUTTON_ID);
        child.setIcon(ThemeImages.TABLE_PREFERENCES_PANEL);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N

        // Set JS to display table preferences panel.
        Table table = getTableAncestor();
        if (table != null) {
            StringBuffer buff = new StringBuffer(128)
                .append("document.getElementById('") //NOI18N
                .append(table.getClientId(getFacesContext()))
                .append("').togglePreferencesPanel(); return false"); //NOI18N
            child.setOnClick(buff.toString());
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getPreferencesPanelToggleButton", //NOI18N
                "Tab index & onClick not set, Table is null"); //NOI18N
        }

        // Get tool tip.
        String toolTip = getTheme().getMessage("table.viewActions.preferences"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get sort panel toggle button.
     *
     * @return The sort panel toggle button.
     */
    public UIComponent getSortPanelToggleButton() {
        UIComponent facet = getFacet(SORT_PANEL_TOGGLE_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        IconHyperlink child = new IconHyperlink();
	child.setId(SORT_PANEL_TOGGLE_BUTTON_ID);
        child.setIcon(ThemeImages.TABLE_SORT_PANEL);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N

        // Set JS to display table preferences panel.
        Table table = getTableAncestor();
        if (table != null) {
            StringBuffer buff = new StringBuffer(128)
                .append("document.getElementById('") //NOI18N
                .append(table.getClientId(getFacesContext()))
                .append("')._toggleSortPanel(); return false"); //NOI18N
            child.setOnClick(buff.toString());
            child.setTabIndex(table.getTabIndex());
        } else {
            log("getSortPanelToggleButton", //NOI18N
                "Tab index & onClick not set, Table is null"); //NOI18N
        }

        // Set tool tip.
        Theme theme = getTheme();
        String toolTip = theme.getMessage("table.viewActions.sort"); //NOI18N
        child.setAlt(toolTip);
        child.setToolTip(toolTip);

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // UIComponent methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * If the rendered property is true, render the begining of the current
     * state of this UIComponent to the response contained in the specified
     * FacesContext.
     *
     * If a Renderer is associated with this UIComponent, the actual encoding 
     * will be delegated to Renderer.encodeBegin(FacesContext, UIComponent).
     *
     * @param context FacesContext for the current request.
     *
     * @exception IOException if an input/output error occurs while rendering.
     * @exception NullPointerException if FacesContext is null.
     */
    public void encodeBegin(FacesContext context) throws IOException {
        // Clear cached variables -- bugtraq #6300020.
        table = null;
        super.encodeBegin(context);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * ABBR gives an abbreviated version of the cell's content. This allows
     * visual browsers to use the short form if space is limited, and
     * non-visual browsers can give a cell's header information in an
     * abbreviated form before rendering each cell.
     */
    @Property(name="abbr", displayName="Abbreviation for Header Cell")
    private String abbr = null;

    /**
     * ABBR gives an abbreviated version of the cell's content. This allows
     * visual browsers to use the short form if space is limited, and
     * non-visual browsers can give a cell's header information in an
     * abbreviated form before rendering each cell.
     */
    public String getAbbr() {
        if (this.abbr != null) {
            return this.abbr;
        }
        ValueExpression _vb = getValueExpression("abbr");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * ABBR gives an abbreviated version of the cell's content. This allows
     * visual browsers to use the short form if space is limited, and
     * non-visual browsers can give a cell's header information in an
     * abbreviated form before rendering each cell.
     */
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    /**
     * Flag indicating this component should render actions at the bottom of 
     * the table. The default renders action for the top of the table.
     */
    @Property(name="actionsBottom", displayName="Is Actions Bottom", isAttribute=false)
    private boolean actionsBottom = false;
    private boolean actionsBottom_set = false;

    /**
     * Flag indicating this component should render actions at the bottom of 
     * the table. The default renders action for the top of the table.
     */
    public boolean isActionsBottom() {
        if (this.actionsBottom_set) {
            return this.actionsBottom;
        }
        ValueExpression _vb = getValueExpression("actionsBottom");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * Flag indicating this component should render actions at the bottom of 
     * the table. The default renders action for the top of the table.
     */
    public void setActionsBottom(boolean actionsBottom) {
        this.actionsBottom = actionsBottom;
        this.actionsBottom_set = true;
    }


    /**
     * Use the <code>align</code> attribute to specify the horizontal alignment for 
     * the content of each cell in the column. Valid values are <code>left</code>, 
     * <code>center</code>, <code>right</code>, <code>justify</code>, and 
     * <code>char</code>. The default alignment is <code>left</code>. Setting the 
     * <code>align</code> attribute to <code>char</code> causes the cell's contents 
     * to be aligned on the character that you specify with the <code>char</code> 
     * attribute. For example, to align cell contents on colons, set 
     * <code>align="char"</code> and <code>char=":" </code>Some browsers do not 
     * support aligning on the character.
     */
    @Property(name="align", displayName="Horizontal Alignment")
    private String align = null;

    /**
     * Use the <code>align</code> attribute to specify the horizontal alignment for 
     * the content of each cell in the column. Valid values are <code>left</code>, 
     * <code>center</code>, <code>right</code>, <code>justify</code>, and 
     * <code>char</code>. The default alignment is <code>left</code>. Setting the 
     * <code>align</code> attribute to <code>char</code> causes the cell's contents 
     * to be aligned on the character that you specify with the <code>char</code> 
     * attribute. For example, to align cell contents on colons, set 
     * <code>align="char"</code> and <code>char=":" </code>Some browsers do not 
     * support aligning on the character.
     */
    public String getAlign() {
        if (this.align != null) {
            return this.align;
        }
        ValueExpression _vb = getValueExpression("align");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>align</code> attribute to specify the horizontal alignment for 
     * the content of each cell in the column. Valid values are <code>left</code>, 
     * <code>center</code>, <code>right</code>, <code>justify</code>, and 
     * <code>char</code>. The default alignment is <code>left</code>. Setting the 
     * <code>align</code> attribute to <code>char</code> causes the cell's contents 
     * to be aligned on the character that you specify with the <code>char</code> 
     * attribute. For example, to align cell contents on colons, set 
     * <code>align="char"</code> and <code>char=":" </code>Some browsers do not 
     * support aligning on the character.
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * The AXIS attribute provides a method of categorizing cells. The
     * attribute's value is a comma-separated list of category names. See the
     * HTML 4.0 Recommendation's section on categorizing cells for an
     * application of AXIS.
     */
    @Property(name="axis", displayName="Category of Header Cell")
    private String axis = null;

    /**
     * The AXIS attribute provides a method of categorizing cells. The
     * attribute's value is a comma-separated list of category names. See the
     * HTML 4.0 Recommendation's section on categorizing cells for an
     * application of AXIS.
     */
    public String getAxis() {
        if (this.axis != null) {
            return this.axis;
        }
        ValueExpression _vb = getValueExpression("axis");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The AXIS attribute provides a method of categorizing cells. The
     * attribute's value is a comma-separated list of category names. See the
     * HTML 4.0 Recommendation's section on categorizing cells for an
     * application of AXIS.
     */
    public void setAxis(String axis) {
        this.axis = axis;
    }

    /**
     * The BGCOLOR attribute suggests a background color for the cell. The
     * combination of this attribute with <FONT COLOR=...> can leave
     * invisible or unreadable text on Netscape Navigator 2.x, which does not
     * support BGCOLOR on table elements. BGCOLOR is dangerous even on
     * supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color. This
     * attribute is deprecated (in HTML 4.0) in favor of style sheets.
     */
    @Property(name="bgColor", displayName="Cell Background Color")
    private String bgColor = null;

    /**
     * The BGCOLOR attribute suggests a background color for the cell. The
     * combination of this attribute with <FONT COLOR=...> can leave
     * invisible or unreadable text on Netscape Navigator 2.x, which does not
     * support BGCOLOR on table elements. BGCOLOR is dangerous even on
     * supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color. This
     * attribute is deprecated (in HTML 4.0) in favor of style sheets.
     */
    public String getBgColor() {
        if (this.bgColor != null) {
            return this.bgColor;
        }
        ValueExpression _vb = getValueExpression("bgColor");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The BGCOLOR attribute suggests a background color for the cell. The
     * combination of this attribute with <FONT COLOR=...> can leave
     * invisible or unreadable text on Netscape Navigator 2.x, which does not
     * support BGCOLOR on table elements. BGCOLOR is dangerous even on
     * supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color. This
     * attribute is deprecated (in HTML 4.0) in favor of style sheets.
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * Use the <code>char </code>attribute to specify a character to use for 
     * horizontal alignment in each cell in the row. You must also set the 
     * <code>align</code> attribute to <code>char</code> to enable character alignment 
     * to be used. The default value for the <code>char</code> attribute is the 
     * decimal point of the current language, such as a period in English. The 
     * <code>char</code> HTML property is not supported by all browsers.
     */
    @Property(name="char", displayName="Alignment Character")
    private String _char = null;

    /**
     * Use the <code>char </code>attribute to specify a character to use for 
     * horizontal alignment in each cell in the row. You must also set the 
     * <code>align</code> attribute to <code>char</code> to enable character alignment 
     * to be used. The default value for the <code>char</code> attribute is the 
     * decimal point of the current language, such as a period in English. The 
     * <code>char</code> HTML property is not supported by all browsers.
     */
    public String getChar() {
        if (this._char != null) {
            return this._char;
        }
        ValueExpression _vb = getValueExpression("char");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>char </code>attribute to specify a character to use for 
     * horizontal alignment in each cell in the row. You must also set the 
     * <code>align</code> attribute to <code>char</code> to enable character alignment 
     * to be used. The default value for the <code>char</code> attribute is the 
     * decimal point of the current language, such as a period in English. The 
     * <code>char</code> HTML property is not supported by all browsers.
     */
    public void setChar(String _char) {
        this._char = _char;
    }

    /**
     * Use the <code>charOff </code>attribute to specify the offset of the first 
     * occurrence of the alignment character that is specified with the 
     * <code>char</code> attribute. The offset is the distance from the left cell 
     * border, in locales that read from left to right. The <code>charOff</code> 
     * attribute's value can be a number of pixels or a percentage of the cell's 
     * width. For example, <code>charOff="50%"</code> centers the alignment character 
     * horizontally in a cell. If <code>charOff="25%"</code>, the first instance 
     * of the alignment character is placed at one fourth of the width of the cell.
     */
    @Property(name="charOff", displayName="Alignment Character Offset")
    private String charOff = null;

    /**
     * Use the <code>charOff </code>attribute to specify the offset of the first 
     * occurrence of the alignment character that is specified with the 
     * <code>char</code> attribute. The offset is the distance from the left cell 
     * border, in locales that read from left to right. The <code>charOff</code> 
     * attribute's value can be a number of pixels or a percentage of the cell's 
     * width. For example, <code>charOff="50%"</code> centers the alignment character 
     * horizontally in a cell. If <code>charOff="25%"</code>, the first instance 
     * of the alignment character is placed at one fourth of the width of the cell.
     */
    public String getCharOff() {
        if (this.charOff != null) {
            return this.charOff;
        }
        ValueExpression _vb = getValueExpression("charOff");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>charOff </code>attribute to specify the offset of the first 
     * occurrence of the alignment character that is specified with the 
     * <code>char</code> attribute. The offset is the distance from the left cell 
     * border, in locales that read from left to right. The <code>charOff</code> 
     * attribute's value can be a number of pixels or a percentage of the cell's 
     * width. For example, <code>charOff="50%"</code> centers the alignment character 
     * horizontally in a cell. If <code>charOff="25%"</code>, the first instance 
     * of the alignment character is placed at one fourth of the width of the cell.
     */
    public void setCharOff(String charOff) {
        this.charOff = charOff;
    }

    /**
     * The COLSPAN attribute of TD specifies the number of columns that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all columns to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    @Property(name="colSpan", displayName="Columns Spanned By the Cell")
    private int colSpan = Integer.MIN_VALUE;
    private boolean colSpan_set = false;

    /**
     * The COLSPAN attribute of TD specifies the number of columns that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all columns to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public int getColSpan() {
        if (this.colSpan_set) {
            return this.colSpan;
        }
        ValueExpression _vb = getValueExpression("colSpan");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * The COLSPAN attribute of TD specifies the number of columns that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all columns to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
        this.colSpan_set = true;
    }

    /**
     * Extra HTML to be appended to the tag output by this renderer.
     */
    @Property(name="extraHtml", displayName="Extra HTML")
    private String extraHtml = null;

    /**
     * Extra HTML to be appended to the tag output by this renderer.
     */
    public String getExtraHtml() {
        if (this.extraHtml != null) {
            return this.extraHtml;
        }
        ValueExpression _vb = getValueExpression("extraHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML to be appended to the tag output by this renderer.
     */
    public void setExtraHtml(String extraHtml) {
        this.extraHtml = extraHtml;
    }

    /**
     * The HEADERS attribute specifies the header cells that apply to the
     * TD. The value is a space-separated list of the header cells' ID
     * attribute values. The HEADERS attribute allows non-visual browsers to
     * render the header information for a given cell.
     */
    @Property(name="headers", displayName="List of Header Cells for Current Cell")
    private String headers = null;

    /**
     * The HEADERS attribute specifies the header cells that apply to the
     * TD. The value is a space-separated list of the header cells' ID
     * attribute values. The HEADERS attribute allows non-visual browsers to
     * render the header information for a given cell.
     */
    public String getHeaders() {
        if (this.headers != null) {
            return this.headers;
        }
        ValueExpression _vb = getValueExpression("headers");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The HEADERS attribute specifies the header cells that apply to the
     * TD. The value is a space-separated list of the header cells' ID
     * attribute values. The HEADERS attribute allows non-visual browsers to
     * render the header information for a given cell.
     */
    public void setHeaders(String headers) {
        this.headers = headers;
    }

    /**
     * The number of pixels for the cell's height. Styles should be used to specify 
     * cell height when possible because the height attribute is deprecated in HTML 4.0.
     */
    @Property(name="height", displayName="Height")
    private String height = null;

    /**
     * The number of pixels for the cell's height. Styles should be used to specify 
     * cell height when possible because the height attribute is deprecated in HTML 4.0.
     */
    public String getHeight() {
        if (this.height != null) {
            return this.height;
        }
        ValueExpression _vb = getValueExpression("height");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The number of pixels for the cell's height. Styles should be used to specify 
     * cell height when possible because the height attribute is deprecated in HTML 4.0.
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * Use the <code>noWrap</code> attribute to disable word wrapping of this column's 
     * cells in visual browsers. Word wrap can cause unnecessary horizontal scrolling 
     * when the browser window is small in relation to the font size. Styles 
     * should be used to disable word wrap when possible because the nowrap attribute 
     * is deprecated in HTML 4.0.
     */
    @Property(name="noWrap", displayName="Suppress Word Wrap")
    private boolean noWrap = false;
    private boolean noWrap_set = false;

    /**
     * Use the <code>noWrap</code> attribute to disable word wrapping of this column's 
     * cells in visual browsers. Word wrap can cause unnecessary horizontal scrolling 
     * when the browser window is small in relation to the font size. Styles 
     * should be used to disable word wrap when possible because the nowrap attribute 
     * is deprecated in HTML 4.0.
     */
    public boolean isNoWrap() {
        if (this.noWrap_set) {
            return this.noWrap;
        }
        ValueExpression _vb = getValueExpression("noWrap");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * Use the <code>noWrap</code> attribute to disable word wrapping of this column's 
     * cells in visual browsers. Word wrap can cause unnecessary horizontal scrolling 
     * when the browser window is small in relation to the font size. Styles 
     * should be used to disable word wrap when possible because the nowrap attribute 
     * is deprecated in HTML 4.0.
     */
    public void setNoWrap(boolean noWrap) {
        this.noWrap = noWrap;
        this.noWrap_set = true;
    }

    /**
     * Scripting code executed when a mouse click
     * occurs over this component.
     */
    @Property(name="onClick", displayName="Click Script")
    private String onClick = null;

    /**
     * Scripting code executed when a mouse click
     * occurs over this component.
     */
    public String getOnClick() {
        if (this.onClick != null) {
            return this.onClick;
        }
        ValueExpression _vb = getValueExpression("onClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when a mouse click
     * occurs over this component.
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * Scripting code executed when a mouse double click
     * occurs over this component.
     */
    @Property(name="onDblClick", displayName="Double Click Script")
    private String onDblClick = null;

    /**
     * Scripting code executed when a mouse double click
     * occurs over this component.
     */
    public String getOnDblClick() {
        if (this.onDblClick != null) {
            return this.onDblClick;
        }
        ValueExpression _vb = getValueExpression("onDblClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when a mouse double click
     * occurs over this component.
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }

    /**
     * Scripting code executed when the user presses down on a key while the
     * component has focus.
     */
    @Property(name="onKeyDown", displayName="Key Down Script")
    private String onKeyDown = null;

    /**
     * Scripting code executed when the user presses down on a key while the
     * component has focus.
     */
    public String getOnKeyDown() {
        if (this.onKeyDown != null) {
            return this.onKeyDown;
        }
        ValueExpression _vb = getValueExpression("onKeyDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user presses down on a key while the
     * component has focus.
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }

    /**
     * Scripting code executed when the user presses and releases a key while
     * the component has focus.
     */
    @Property(name="onKeyPress", displayName="Key Press Script")
    private String onKeyPress = null;

    /**
     * Scripting code executed when the user presses and releases a key while
     * the component has focus.
     */
    public String getOnKeyPress() {
        if (this.onKeyPress != null) {
            return this.onKeyPress;
        }
        ValueExpression _vb = getValueExpression("onKeyPress");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user presses and releases a key while
     * the component has focus.
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }

    /**
     * Scripting code executed when the user releases a key while the
     * component has focus.
     */
    @Property(name="onKeyUp", displayName="Key Up Script")
    private String onKeyUp = null;

    /**
     * Scripting code executed when the user releases a key while the
     * component has focus.
     */
    public String getOnKeyUp() {
        if (this.onKeyUp != null) {
            return this.onKeyUp;
        }
        ValueExpression _vb = getValueExpression("onKeyUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user releases a key while the
     * component has focus.
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }

    /**
     * Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.
     */
    @Property(name="onMouseDown", displayName="Mouse Down Script")
    private String onMouseDown = null;

    /**
     * Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.
     */
    public String getOnMouseDown() {
        if (this.onMouseDown != null) {
            return this.onMouseDown;
        }
        ValueExpression _vb = getValueExpression("onMouseDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    /**
     * Scripting code executed when the user moves the mouse pointer while
     * over the component.
     */
    @Property(name="onMouseMove", displayName="Mouse Move Script")
    private String onMouseMove = null;

    /**
     * Scripting code executed when the user moves the mouse pointer while
     * over the component.
     */
    public String getOnMouseMove() {
        if (this.onMouseMove != null) {
            return this.onMouseMove;
        }
        ValueExpression _vb = getValueExpression("onMouseMove");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user moves the mouse pointer while
     * over the component.
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * Scripting code executed when a mouse out movement
     * occurs over this component.
     */
    @Property(name="onMouseOut", displayName="Mouse Out Script")
    private String onMouseOut = null;

    /**
     * Scripting code executed when a mouse out movement
     * occurs over this component.
     */
    public String getOnMouseOut() {
        if (this.onMouseOut != null) {
            return this.onMouseOut;
        }
        ValueExpression _vb = getValueExpression("onMouseOut");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when a mouse out movement
     * occurs over this component.
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.
     */
    @Property(name="onMouseOver", displayName="Mouse In Script")
    private String onMouseOver = null;

    /**
     * Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.
     */
    public String getOnMouseOver() {
        if (this.onMouseOver != null) {
            return this.onMouseOver;
        }
        ValueExpression _vb = getValueExpression("onMouseOver");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.
     */
    @Property(name="onMouseUp", displayName="Mouse Up Script")
    private String onMouseUp = null;

    /**
     * Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.
     */
    public String getOnMouseUp() {
        if (this.onMouseUp != null) {
            return this.onMouseUp;
        }
        ValueExpression _vb = getValueExpression("onMouseUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.
     */
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }

    /**
     * The ROWSPAN attribute of TD specifies the number of rows that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all rows to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    @Property(name="rowSpan", displayName="Rows Spanned By the Cell")
    private int rowSpan = Integer.MIN_VALUE;
    private boolean rowSpan_set = false;

    /**
     * The ROWSPAN attribute of TD specifies the number of rows that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all rows to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public int getRowSpan() {
        if (this.rowSpan_set) {
            return this.rowSpan;
        }
        ValueExpression _vb = getValueExpression("rowSpan");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * The ROWSPAN attribute of TD specifies the number of rows that are
     * spanned by the cell. The default value is 1. The special value 0
     * indicates that the cell spans all rows to the end of the table. The
     * value 0 is ignored by most browsers, so authors may wish to calculate
     * the exact number of rows or columns spanned and use that value.
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
        this.rowSpan_set = true;
    }

    /**
     * Use the <code>scope</code> attribute to specify that the data cells of the 
     * column are also acting as headers for rows or other columns of the table. 
     * This attribute supports assistive technologies by enabling them to determine 
     * the order in which to read the cells. Valid values include:
     * <ul>
     * <li><code>row</code>, when the cells provide header information for the row</li>
     * <li><code>col</code>, when the cells provide header information for the column</li>
     * <li><code>rowgroup</code>, when the cells provide header information for the row group</li>
     * <li><code>colgroup</code>, when the cells provide header information for the column group</li>
     * </ul>
     */
    @Property(name="scope", displayName="Cells Covered By Header Cell")
    private String scope = null;

    /**
     * Use the <code>scope</code> attribute to specify that the data cells of the 
     * column are also acting as headers for rows or other columns of the table. 
     * This attribute supports assistive technologies by enabling them to determine 
     * the order in which to read the cells. Valid values include:
     * <ul>
     * <li><code>row</code>, when the cells provide header information for the row</li>
     * <li><code>col</code>, when the cells provide header information for the column</li>
     * <li><code>rowgroup</code>, when the cells provide header information for the row group</li>
     * <li><code>colgroup</code>, when the cells provide header information for the column group</li>
     * </ul>
     */
    public String getScope() {
        if (this.scope != null) {
            return this.scope;
        }
        ValueExpression _vb = getValueExpression("scope");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>scope</code> attribute to specify that the data cells of the 
     * column are also acting as headers for rows or other columns of the table. 
     * This attribute supports assistive technologies by enabling them to determine 
     * the order in which to read the cells. Valid values include: 
     * <ul>
     * <li><code>row</code>, when the cells provide header information for the row</li>
     * <li><code>col</code>, when the cells provide header information for the column</li>
     * <li><code>rowgroup</code>, when the cells provide header information for the row group</li>
     * <li><code>colgroup</code>, when the cells provide header information for the column group</li>
     * </ul>
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name="style", displayName="CSS Style(s)")
    private String style = null;

    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)")
    private String styleClass = null;

    /**
     * CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
     */
    @Property(name="toolTip", displayName="Tool Tip")
    private String toolTip = null;

    /**
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    /**
     * Use the <code>valign</code> attribute to specify the vertical alignment for the 
     * content of each cell in the column. Valid values are <code>top</code>, 
     * <code>middle</code>, <code>bottom</code>, and <code>baseline</code>. The 
     * default vertical alignment is <code>middle</code>. Setting the 
     * <code>valign</code> attribute to <code>baseline </code>causes the first line of 
     * each cell's content to be aligned on the text baseline, the invisible line on 
     * which text characters rest.
     */
    @Property(name="valign", displayName="Vertical Position")
    private String valign = null;

    /**
     * Use the <code>valign</code> attribute to specify the vertical alignment for the 
     * content of each cell in the column. Valid values are <code>top</code>, 
     * <code>middle</code>, <code>bottom</code>, and <code>baseline</code>. The 
     * default vertical alignment is <code>middle</code>. Setting the 
     * <code>valign</code> attribute to <code>baseline </code>causes the first line of 
     * each cell's content to be aligned on the text baseline, the invisible line on 
     * which text characters rest.
     */
    public String getValign() {
        if (this.valign != null) {
            return this.valign;
        }
        ValueExpression _vb = getValueExpression("valign");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>valign</code> attribute to specify the vertical alignment for the 
     * content of each cell in the column. Valid values are <code>top</code>, 
     * <code>middle</code>, <code>bottom</code>, and <code>baseline</code>. The 
     * default vertical alignment is <code>middle</code>. Setting the 
     * <code>valign</code> attribute to <code>baseline </code>causes the first line of 
     * each cell's content to be aligned on the text baseline, the invisible line on 
     * which text characters rest.
     */
    public void setValign(String valign) {
        this.valign = valign;
    }

    /**
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
     */
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * Use the <code>width</code> attribute to specify the width of the cells of the 
     * column. The width can be specified as the number of pixels or the percentage of 
     * the table width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    @Property(name="width", displayName="Width")
    private String width = null;

    /**
     * Use the <code>width</code> attribute to specify the width of the cells of the 
     * column. The width can be specified as the number of pixels or the percentage of 
     * the table width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    public String getWidth() {
        if (this.width != null) {
            return this.width;
        }
        ValueExpression _vb = getValueExpression("width");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the <code>width</code> attribute to specify the width of the cells of the 
     * column. The width can be specified as the number of pixels or the percentage of 
     * the table width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.abbr = (String) _values[1];
        this.actionsBottom = ((Boolean) _values[2]).booleanValue();
        this.actionsBottom_set = ((Boolean) _values[3]).booleanValue();
        this.align = (String) _values[4];
        this.axis = (String) _values[5];
        this.bgColor = (String) _values[6];
        this._char = (String) _values[7];
        this.charOff = (String) _values[8];
        this.colSpan = ((Integer) _values[9]).intValue();
        this.colSpan_set = ((Boolean) _values[10]).booleanValue();
        this.extraHtml = (String) _values[11];
        this.headers = (String) _values[12];
        this.height = (String) _values[13];
        this.noWrap = ((Boolean) _values[14]).booleanValue();
        this.noWrap_set = ((Boolean) _values[15]).booleanValue();
        this.onClick = (String) _values[16];
        this.onDblClick = (String) _values[17];
        this.onKeyDown = (String) _values[18];
        this.onKeyPress = (String) _values[19];
        this.onKeyUp = (String) _values[20];
        this.onMouseDown = (String) _values[21];
        this.onMouseMove = (String) _values[22];
        this.onMouseOut = (String) _values[23];
        this.onMouseOver = (String) _values[24];
        this.onMouseUp = (String) _values[25];
        this.rowSpan = ((Integer) _values[26]).intValue();
        this.rowSpan_set = ((Boolean) _values[27]).booleanValue();
        this.scope = (String) _values[28];
        this.style = (String) _values[29];
        this.styleClass = (String) _values[30];
        this.toolTip = (String) _values[31];
        this.valign = (String) _values[32];
        this.visible = ((Boolean) _values[33]).booleanValue();
        this.visible_set = ((Boolean) _values[34]).booleanValue();
        this.width = (String) _values[35];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[36];
        _values[0] = super.saveState(_context);
        _values[1] = this.abbr;
        _values[2] = this.actionsBottom ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.actionsBottom_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.align;
        _values[5] = this.axis;
        _values[6] = this.bgColor;
        _values[7] = this._char;
        _values[8] = this.charOff;
        _values[9] = new Integer(this.colSpan);
        _values[10] = this.colSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.extraHtml;
        _values[12] = this.headers;
        _values[13] = this.height;
        _values[14] = this.noWrap ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.noWrap_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.onClick;
        _values[17] = this.onDblClick;
        _values[18] = this.onKeyDown;
        _values[19] = this.onKeyPress;
        _values[20] = this.onKeyUp;
        _values[21] = this.onMouseDown;
        _values[22] = this.onMouseMove;
        _values[23] = this.onMouseOut;
        _values[24] = this.onMouseOver;
        _values[25] = this.onMouseUp;
        _values[26] = new Integer(this.rowSpan);
        _values[27] = this.rowSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[28] = this.scope;
        _values[29] = this.style;
        _values[30] = this.styleClass;
        _values[31] = this.toolTip;
        _values[32] = this.valign;
        _values[33] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[35] = this.width;
        return _values;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get Javascript for the de/select all buttons.
     *
     * @param script The Javascript to be prepended, if any.
     * @param checked true if components used for row selection should be 
     * checked; otherwise, false.
     *
     * @return The Javascript for the de/select buttons.
     */
    private String getSelectJavascript(String script, boolean checked) {
        // Get JS to de/select all components in table.
        StringBuffer buff = new StringBuffer(1024);

        // Developer may have added onClick Javascript for de/select all button.
        if (script != null) {
            buff.append(script).append(";"); //NOI18N
        } 

        // Append Javascript to de/select all select components.
        Table table = getTableAncestor();
        if (table != null) {
            buff.append("document.getElementById('") //NOI18N
                .append(table.getClientId(getFacesContext()))
                .append("')._selectAllRows(") //NOI18N
                .append(checked)
                .append("); return false"); //NOI18N
        } else {
            log("getSelectJavascript", //NOI18N
                "Cannot obtain select Javascript, Table is null"); //NOI18N
        }
        return buff.toString();
    }

    /**
     * Helper method to get separator icons used for top and bottom actions, 
     * filter, view actions, and paginate button.
     *
     * @param id The identifier for the component.
     * @param name The facet name used to override the component.
     *
     * @return The separator icon.
     */
    private UIComponent getSeparatorIcon(String id, String name) {
        UIComponent facet = getFacet(name);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Icon child = ThemeUtilities.getIcon(getTheme(), ThemeImages.TABLE_ACTIONS_SEPARATOR);
	child.setId(id);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        
        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Helper method to get Javascript to submit the "go" button when the user
     * clicks enter in the page field.
     *
     * @return The Javascript used to submit the "go" button.
     */
    private String getPaginationJavascript() {
        ClientSniffer cs = ClientSniffer.getInstance(getFacesContext());

        // Get key code.
        String keyCode = cs.isNav() ? "event.which" : "event.keyCode"; //NOI18N

        // Append JS to capture the event.
        StringBuffer buff = new StringBuffer(128)
            .append("if (event && ") //NOI18N
            .append(keyCode)
            .append("==13) {"); //NOI18N

        // To prevent an auto-submit, Netscape 6.x and netscape 7.0 require 
        // setting the cancelBubble property. However, Netscape 7.1, 
        // Mozilla 1.x, IE 5.x for SunOS/Windows do not use this property.
        if (cs.isNav6() || cs.isNav70()) {
            buff.append("event.cancelBubble = true;"); //NOI18N
        }
        
        // Append JS to submit the button.
        buff.append("var e=document.getElementById('") //NOI18N
            .append(getClientId(getFacesContext()) + 
                NamingContainer.SEPARATOR_CHAR + 
                TableActions.PAGINATION_SUBMIT_BUTTON_ID)
            .append("'); if (e != null) e.click(); return false}"); //NOI18N
        return buff.toString();
    }

    /**
     * Helper method to get Theme objects.
     *
     * @return The current theme.
     */
    private Theme getTheme() {
	return ThemeUtilities.getTheme(getFacesContext());
    }

    /**
     * Helper method to determine if table is empty.
     *
     * @return true if table contains no rows.
     */
    private boolean isEmptyTable() {
        int totalRows = table.getRowCount();
        return (totalRows == 0);
    }

    /**
     * Helper method to determine if all rows fit on a single page.
     * <p>
     * Note: Pagination controls are only hidden when all groups fit on a single
     * page.
     * </p>
     * @return true if all rows fit on a single page.
     */
    private boolean isSinglePage() {
        int totalRows = table.getRowCount();
        return (totalRows < table.getRows());
    }

    /**
     * Helper method to determine if table contains a single row.
     *
     * @return true if all rows fit on a single page.
     */
    private boolean isSingleRow() {
        int totalRows = table.getRowCount();
        return (totalRows == 1);
    }

    /**
     * Log fine messages.
     */
    private void log(String method, String message) {
        // Get class.
        Class clazz = this.getClass();
	if (LogUtil.fineEnabled(clazz)) {
            // Log method name and message.
            LogUtil.fine(clazz, clazz.getName() + "." + method + ": " + message); //NOI18N
        }
    }

}
