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
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.Separator;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;

/**
 * Component that represents a table.
 *
 * The table component provides a layout mechanism for displaying table actions.
 * UI guidelines describe specific behavior that can applied to the rows and 
 * columns of data such as sorting, filtering, pagination, selection, and custom 
 * user actions. In addition, UI guidelines also define sections of the table 
 * that can be used for titles, row group headers, and placement of pre-defined
 * and user defined actions.
 * <p>
 * Note: Column headers and footers are rendered by TableRowGroupRenderer. Table
 * column footers are rendered by TableRenderer.
 * </p><p>
 * Only children of type TableRowGroup should be processed by renderers
 * associated with this component.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.web.ui.component.Table.level = FINE
 * </pre><p>
 * See TLD docs for more information.
 * </p>
 */
@Component(type="com.sun.webui.jsf.Table",
    family="com.sun.webui.jsf.Table",
    displayName="Table", tagName="table",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_table",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_table_props")
public class Table extends TableBase {
    /** The facet name for the bottom actions area. */
    public static final String ACTIONS_BOTTOM_FACET = "actionsBottom"; //NOI18N

    /** The value for the custom filter option. */
    public static final String CUSTOM_FILTER = "_customFilter"; //NOI18N

    /** The value for the custom filter applied option. */
    public static final String CUSTOM_FILTER_APPLIED = "_customFilterApplied"; //NOI18N

    /** The id for the embedded panels bar. */
    public static final String EMBEDDED_PANELS_BAR_ID = "_embeddedPanelsBar"; //NOI18N

    /** The component id for embedded panels. */
    public static final String EMBEDDED_PANELS_ID = "_embeddedPanels"; //NOI18N

    /** The facet name for embedded panels. */
    public static final String EMBEDDED_PANELS_FACET = "embeddedPanels"; //NOI18N    

    /** The facet name for the footer area. */
    public static final String FOOTER_FACET = "footer"; //NOI18N       

    /** The id for the table. */
    public static final String TABLE_ID = "_table"; //NOI18N

    /** The id for the bottom actions bar. */
    public static final String TABLE_ACTIONS_BOTTOM_BAR_ID = "_tableActionsBottomBar"; //NOI18N

    /** The component id for bottom actions. */
    public static final String TABLE_ACTIONS_BOTTOM_ID = "_tableActionsBottom"; //NOI18N

    /** The facet name for bottom actions. */
    public static final String TABLE_ACTIONS_BOTTOM_FACET = "tableActionsBottom"; //NOI18N

    /** The id for the top actions bar. */
    public static final String TABLE_ACTIONS_TOP_BAR_ID = "_tableActionsTopBar"; //NOI18N

    /** The component id for top actions. */
    public static final String TABLE_ACTIONS_TOP_ID = "_tableActionsTop"; //NOI18N

    /** The facet name for top actions. */
    public static final String TABLE_ACTIONS_TOP_FACET = "tableActionsTop"; //NOI18N

    /** The id for the table footer. */
    public static final String TABLE_FOOTER_BAR_ID = "_tableFooterBar"; //NOI18N

    /** The component id for the table footer. */
    public static final String TABLE_FOOTER_ID = "_tableFooter"; //NOI18N

    /** The facet name for the table footer. */
    public static final String TABLE_FOOTER_FACET = "tableFooter"; //NOI18N

    /** The id for the title bar. */
    public static final String TITLE_BAR_ID = "_titleBar"; //NOI18N

    /** The facet name for the title area. */
    public static final String TITLE_FACET = "title"; //NOI18N

    // The number of rows to be displayed per page for a paginated table.
    private int rows = -1;

    // The max number of pages.
    private int first = -1;

    // The number of rows.
    private int rowCount = -1;

    // The max number of pages.
    private int pageCount = -1;

    // The max number of columns.
    private int columnCount = -1;

    // The number of column headers.
    private int columnHeadersCount = -1;

    // The number of hidden selected rows.
    private int hiddenSelectedRowsCount = -1;

    // The number of column footers.
    private int tableColumnFootersCount = -1;

    // The number of TableRowGroup children.
    private int tableRowGroupCount = -1;

    /** Default constructor */
    public Table() {
        super();
        setRendererType("com.sun.webui.jsf.Table");
    }

    /**
     * Return the family for this component.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Table";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Clear cached properties.
     * <p>
     * Note: Properties may have been cached via the apply request values,
     * validate, and update phases and must be re-evaluated during the render
     * response phase (e.g., the underlying DataProvider may have changed).
     * </p>
     */
    public void clear() {
        rows = -1;
        first = -1;
        rowCount = -1;
        pageCount = -1;
        columnCount = -1;
        columnHeadersCount = -1;
        hiddenSelectedRowsCount = -1;     
        tableColumnFootersCount = -1;
        tableRowGroupCount = -1;
        super.clear();
    }

    /**
     * Get the number of column header bars for all TableRowGroup children.
     *
     * @return The number of column headers.
     */
    public int getColumnHeadersCount() {
        // Get column header count.
        if (columnHeadersCount == -1) {
            columnHeadersCount = 0; // Initialize min value.

            // Iterate over each TableRowGroup child to determine if each group 
            // displays its own column header or if one column header is
            // dispalyed for all row groups.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                Iterator grandkids = group.getTableColumnChildren();
                while (grandkids.hasNext()) {
                    TableColumn col = (TableColumn) grandkids.next();
                    if (col.getHeaderText() != null) {
                        columnHeadersCount++;
                        break; // Break if at least one column header is found.
                    }
                }
            }
        }
        return columnHeadersCount;
    }

    /**
     * Get the number of hidden selected rows for all TableRowGroup children.
     *
     * @return The number of hidden selected rows.
     */
    public int getHiddenSelectedRowsCount() {
        // Get hidden selected rows count.
        if (hiddenSelectedRowsCount == -1) {
            hiddenSelectedRowsCount = 0; // Initialize min value.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                hiddenSelectedRowsCount += group.getHiddenSelectedRowsCount();
            }
        }
        return hiddenSelectedRowsCount;
    }

    /**
     * Get the zero-relative row number of the first row to be displayed for
     * a paginated table for all TableRowGroup children.
     *
     * @return The first row to be displayed.
     */
    public int getFirst() {
        // Get first row.
        if (first == -1) {
            first = 0; // Initialize min value.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                first += group.getFirst();
            }
        }
        return first;
    }

    /**
     * Get the max number of pages for all TableRowGroup children.
     *
     * @return The max number of pages.
     */
    public int getPageCount() {
        // Get page count.
        if (pageCount == -1) {
            pageCount = 1; // Initialize min value.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                int pages = group.getPages();
                if (pageCount < pages) {
                    pageCount = pages;
                }
            }
        }
        return pageCount;
    }
    
    /**
     * Get the number of rows to be displayed per page for a paginated table
     * for all TableRowGroup children.
     *
     * @return The number of rows to be displayed per page for a paginated table.
     */
    public int getRows() {
        // Get rows per page.
        if (rows == -1) {
            rows = 0; // Initialize min value.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                rows += group.getRows();
            }
        }
        return rows;
    }

    /**
     * Get the number of rows in the underlying TableDataProvider for all 
     * TableRowGroup children.
     *
     * @return The number of rows.
     */
    public int getRowCount() {
        // Get row count.
        if (rowCount == -1) {
            rowCount = 0; // Initialize min value.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                rowCount += group.getRowCount();
            }
        }
        return rowCount;
    }

    /**
     * Get the max number of columns found for all TableRowGroup children.
     *
     * @return The max number of columns.
     */
    public int getColumnCount() {
        // Get column count.
        if (columnCount == -1) {
            columnCount = 1; // Initialize min value.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                int count = group.getColumnCount();
                if (columnCount < count) {
                    columnCount = count;
                }
            }
        }
        return columnCount;
    }

    /**
     * Get the number of table column footer bars for all TableRowGroup children.
     *
     * @return The number of table column footers.
     */
    public int getTableColumnFootersCount() {
        // Get table column footer count.
        if (tableColumnFootersCount == -1) {
            tableColumnFootersCount = 0; // Initialize min value.

            // Iterate over each TableRowGroup child to determine if each group 
            // displays its own table column footer or if one table column 
            // footer is dispalyed for all row groups.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                TableRowGroup group = (TableRowGroup) kids.next();
                Iterator grandkids = group.getTableColumnChildren();
                while (grandkids.hasNext()) {
                    TableColumn col = (TableColumn) grandkids.next();
                    if (col.isRendered() && col.getTableFooterText() != null) {
                        tableColumnFootersCount++;
                        break; // Break if at least one table column footer is shown.
                    }
                }
            }
        }
        return tableColumnFootersCount;
    }

    /**
     * Get the first TableRowGroup child found for the specified component that
     * have a rendered property of true.
     *
     * @return The first TableRowGroup child found.
     */
    public TableRowGroup getTableRowGroupChild() {
        TableRowGroup group = null;
        Iterator kids = getTableRowGroupChildren();
        if (kids.hasNext()) {
            group = (TableRowGroup) kids.next();
        }
        return group;
    }

    /**
     * Get the number of child TableRowGroup components found for this component
     * that have a rendered property of true.
     *
     * @return The number of TableRowGroup children.
     */
    public int getTableRowGroupCount() {
        // Get TableRowGroup children count.
        if (tableRowGroupCount == -1) {
            tableRowGroupCount = 0; // Initialize min value.
            Iterator kids = getTableRowGroupChildren();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if (kid.isRendered()) {
                    tableRowGroupCount++;
                }
            }
        }
        return tableRowGroupCount;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get bottom actions.
     *
     * @return The bottom actions.
     */
    public UIComponent getTableActionsBottom() {
        UIComponent facet = getFacet(TABLE_ACTIONS_BOTTOM_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableActions child = new TableActions();
	child.setId(TABLE_ACTIONS_BOTTOM_ID);
        child.setColSpan(getColumnCount());
        child.setExtraHtml(getExtraActionBottomHtml());
        child.setNoWrap(true);
        child.setActionsBottom(true);

        // We must determine if all TableRowGroup components are empty. Controls
        // are only hidden when all row groups are empty. Likewise, pagination
        // controls are only hidden when all groups fit on a single page.
        int totalRows = getRowCount();
        boolean emptyTable = (totalRows == 0);
        boolean singleRow = (totalRows == 1);
        boolean singlePage = (totalRows < getRows());

        // Get facets.
        UIComponent actions = getFacet(ACTIONS_BOTTOM_FACET);
        
        // Get flag indicating which facets to render.
        boolean renderActions = !emptyTable && !singleRow
            && actions != null && actions.isRendered();

        // Hide pagination controls when all rows fit on a page.
        boolean renderPaginationControls = !emptyTable && !singlePage
            && isPaginationControls();

        // Hide paginate button for a single row.
        boolean renderPaginateButton = !emptyTable && !singlePage
            && isPaginateButton();

        // Set rendered.
        if (!(renderActions || renderPaginationControls
                || renderPaginateButton)) {
            log("getTableActionsBottom", //NOI18N
                "Action bar not rendered, nothing to display"); //NOI18N
            child.setRendered(false);
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get top actions.
     *
     * @return The top actions.
     */
    public UIComponent getTableActionsTop() {
        UIComponent facet = getFacet(TABLE_ACTIONS_TOP_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableActions child = new TableActions();
	child.setId(TABLE_ACTIONS_TOP_ID);
        child.setColSpan(getColumnCount());
        child.setExtraHtml(getExtraActionTopHtml());
        child.setNoWrap(true);

        // We must determine if all TableRowGroup components are empty. Controls
        // are only hidden when all row groups are empty. Likewise, pagination
        // controls are only hidden when all groups fit on a single page.
        int totalRows = getRowCount();
        boolean emptyTable = (totalRows == 0);
        boolean singleRow = (totalRows == 1);
        boolean singlePage = (totalRows < getRows());

        // Get facets.
        UIComponent actions = getFacet(ACTIONS_TOP_FACET);
        UIComponent filter = getFacet(FILTER_FACET);
        UIComponent sort = getFacet(SORT_PANEL_FACET);
        UIComponent prefs = getFacet(PREFERENCES_PANEL_FACET);

        // Flags indicating which facets to render.
        boolean renderActions = actions != null && actions.isRendered();
        boolean renderFilter = filter != null && filter.isRendered();
        boolean renderSort = sort != null && sort.isRendered();
        boolean renderPrefs = prefs != null && prefs.isRendered();

        // Hide sorting and pagination controls for an empty table or when there
        // is only a single row.
        boolean renderSelectMultipleButton = !emptyTable
            && isSelectMultipleButton();
        boolean renderDeselectMultipleButton = !emptyTable
            && isDeselectMultipleButton();
        boolean renderDeselectSingleButton = !emptyTable
            && isDeselectSingleButton();
        boolean renderClearTableSortButton = !emptyTable && !singleRow
            && isClearSortButton();
        boolean renderTableSortPanelToggleButton = !emptyTable && !singleRow
            && (isSortPanelToggleButton() || renderSort);
        boolean renderPaginateButton = !emptyTable && !singlePage
            && isPaginateButton();

        // Return if nothing is rendered.
        if (!(renderActions || renderFilter || renderPrefs
                || renderSelectMultipleButton
                || renderDeselectMultipleButton
                || renderDeselectSingleButton
                || renderClearTableSortButton
                || renderTableSortPanelToggleButton
                || renderPaginateButton)) {
            log("getTableActionsTop", //NOI18N
                "Action bar not rendered, nothing to display"); //NOI18N
            child.setRendered(false);
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Filter methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the HTML element ID of the dropDown component used to display table 
     * filter options.
     * <p>
     * Note: This is the fully qualified ID rendered in the outter tag enclosing
     * the HTML element. Required for Javascript functions to set the dropDown
     * styles when the embedded filter panel is opened and to reset the default
     * selected value when the panel is closed.
     * </p>
     * @return The HTML element ID of the filter menu.
     */
    public String getFilterId() {
        String filterId = super.getFilterId();
        if (filterId == null) {
            log("getFilterId", "filterId is null, using facet client ID"); //NOI18N
            UIComponent filter = getFacet(FILTER_FACET);
            filterId = (filter != null)
                ? filter.getClientId(getFacesContext())
                : null;
        }
        return filterId;
    }

    /** 
     * Get the "custom filter" options used for a table filter menu.
     * <p>
     * Note: UI guidelines state that a "Custom Filter" option should be added 
     * to the filter menu, used to open the table filter panel. Thus, if the 
     * CUSTOM_FILTER option is selected, Javascript invoked via the onChange
     * event will open the table filter panel.
     * </p><p>
     * UI guidelines also state that a "Custom Filter Applied" option should be 
     * added to the filter menu, indicating that a custom filter has been 
     * applied. In this scenario, set the selected property of the filter menu 
     * as CUSTOM_FILTER_APPLIED. This selection should persist until another 
     * menu option has been selected.
     * </p>
     * @param options An array of options to append to -- may be null.
     * @param customFilterApplied Flag indicating custom filter is applied.
     * @return A new array containing appended "custom filter" options.
     */
    static public Option[] getFilterOptions(Option[] options, 
            boolean customFilterApplied) {
        FacesContext context = FacesContext.getCurrentInstance();
        Theme theme = ThemeUtilities.getTheme(context);
        ArrayList newOptions = new ArrayList();
        
        // Get old options.
        if (options != null) {
            for (int i = 0; i < options.length; i++) { 
                newOptions.add(options[i]);
            }
        }

        // Add options separator.
        newOptions.add(new Separator());
        
        // Add custom filter applied option.
        if (customFilterApplied) {
            Option option = new Option(CUSTOM_FILTER_APPLIED, 
                theme.getMessage("table.viewActions.customFilterApplied")); //NOI18N
            option.setDisabled(true);
            newOptions.add(option);
        }
        
        // Add custom filter option.
	newOptions.add(new Option(CUSTOM_FILTER, 
            theme.getMessage("table.viewActions.customFilter"))); //NOI18N

        // Return options.
	Option[] result = new Option[newOptions.size()];
        return (Option[]) newOptions.toArray(result);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Footer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get table footer.
     *
     * @return The table footer.
     */
    public UIComponent getTableFooter() {
        UIComponent facet = getFacet(TABLE_FOOTER_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableFooter child = new TableFooter();
	child.setId(TABLE_FOOTER_ID);
        child.setColSpan(getColumnCount());
        child.setExtraHtml(getExtraFooterHtml());
        child.setTableFooter(true);

        // Set rendered.
        if (!(facet != null && facet.isRendered()
                || getFooterText() != null || isHiddenSelectedRows())) {
            // Note: Footer may be initialized to force rendering. This allows
            // developers to omit the footer text property for select columns.
            log("getTableFooter", //NOI18N
                 "Table footer not rendered, nothing to display"); //NOI18N
            child.setRendered(false);
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Panel methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get embedded panels.
     *
     * @return The embedded panels.
     */
    public UIComponent getEmbeddedPanels() {
        UIComponent facet = getFacet(EMBEDDED_PANELS_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TablePanels child = new TablePanels();
	child.setId(EMBEDDED_PANELS_ID);
        child.setColSpan(getColumnCount());
        child.setExtraHtml(getExtraPanelHtml());
        child.setNoWrap(true);

        // Get facets.
        UIComponent sort = getFacet(SORT_PANEL_FACET);
        UIComponent filter = getFacet(FILTER_PANEL_FACET);
        UIComponent prefs = getFacet(PREFERENCES_PANEL_FACET);

        // Set flags indicating which facets to render.
        boolean renderFilter = filter != null && filter.isRendered();
        boolean renderPrefs = prefs != null && prefs.isRendered();
        boolean renderSort = sort != null && sort.isRendered();

        // Set type of panel to render.
        child.setFilterPanel(renderFilter);
        child.setPreferencesPanel(renderPrefs);

        // Set rendered.
        if (!(renderFilter || renderSort || renderPrefs
                || isSortPanelToggleButton())) {
            log("getEmbeddedPanels", //NOI18N
                "Embedded panels not rendered, nothing to display"); //NOI18N
            child.setRendered(false);
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that  
     * is rendered for the Action Bar (bottom). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    @Property(name="extraActionBottomHtml", displayName="Extra Action (bottom) HTML", category="Advanced")
    private String extraActionBottomHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that  
     * is rendered for the Action Bar (bottom). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    public String getExtraActionBottomHtml() {
        if (this.extraActionBottomHtml != null) {
            return this.extraActionBottomHtml;
        }
        ValueExpression _vb = getValueExpression("extraActionBottomHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that  
     * is rendered for the Action Bar (bottom). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    public void setExtraActionBottomHtml(String extraActionBottomHtml) {
        this.extraActionBottomHtml = extraActionBottomHtml;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the Action Bar (top). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    @Property(name="extraActionTopHtml", displayName="Extra Action (top) HTML", category="Advanced")
    private String extraActionTopHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the Action Bar (top). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    public String getExtraActionTopHtml() {
        if (this.extraActionTopHtml != null) {
            return this.extraActionTopHtml;
        }
        ValueExpression _vb = getValueExpression("extraActionTopHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the Action Bar (top). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    public void setExtraActionTopHtml(String extraActionTopHtml) {
        this.extraActionTopHtml = extraActionTopHtml;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the table footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    @Property(name="extraFooterHtml", displayName="Extra Footer HTML", category="Advanced")
    private String extraFooterHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the table footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public String getExtraFooterHtml() {
        if (this.extraFooterHtml != null) {
            return this.extraFooterHtml;
        }
        ValueExpression _vb = getValueExpression("extraFooterHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the table footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public void setExtraFooterHtml(String extraFooterHtml) {
        this.extraFooterHtml = extraFooterHtml;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for an embedded panel. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity.
     */
    @Property(name="extraPanelHtml", displayName="Extra Panel HTML", category="Advanced")
    private String extraPanelHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for an embedded panel. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity.
     */
    public String getExtraPanelHtml() {
        if (this.extraPanelHtml != null) {
            return this.extraPanelHtml;
        }
        ValueExpression _vb = getValueExpression("extraPanelHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for an embedded panel. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity.
     */
    public void setExtraPanelHtml(String extraPanelHtml) {
        this.extraPanelHtml = extraPanelHtml;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;caption&gt;</code> HTML element 
     * that is rendered for the table title. Use only code that is valid in an HTML 
     * <code>&lt;caption&gt;</code> element. The code you specify is inserted in the 
     * HTML element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myTitleStyle'"</code>.
     */
    @Property(name="extraTitleHtml", displayName="Extra Title HTML", category="Advanced")
    private String extraTitleHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;caption&gt;</code> HTML element 
     * that is rendered for the table title. Use only code that is valid in an HTML 
     * <code>&lt;caption&gt;</code> element. The code you specify is inserted in the 
     * HTML element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myTitleStyle'"</code>.
     */
    public String getExtraTitleHtml() {
        if (this.extraTitleHtml != null) {
            return this.extraTitleHtml;
        }
        ValueExpression _vb = getValueExpression("extraTitleHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;caption&gt;</code> HTML element 
     * that is rendered for the table title. Use only code that is valid in an HTML 
     * <code>&lt;caption&gt;</code> element. The code you specify is inserted in the 
     * HTML element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myTitleStyle'"</code>.
     */
    public void setExtraTitleHtml(String extraTitleHtml) {
        this.extraTitleHtml = extraTitleHtml;
    }


    /**
     * The text to be displayed in the table footer, which expands across the width of 
     * the table.
     */
    @Property(name="footerText", displayName="Footer Text", category="Appearance")
    private String footerText = null;

    /**
     * The text to be displayed in the table footer, which expands across the width of 
     * the table.
     */
    public String getFooterText() {
        if (this.footerText != null) {
            return this.footerText;
        }
        ValueExpression _vb = getValueExpression("footerText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The text to be displayed in the table footer, which expands across the width of 
     * the table.
     */
    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    /**
     * Flag indicating that this component should use a virtual form. A virtual form is 
     * equivalent to enclosing the table component in its own HTML form element, 
     * separate from other HTML elements on the same page. As an example, consider the 
     * case where a required text field and table appear on the same page. If the user 
     * clicks on a table sort button, while the required text field has no value, the 
     * sort action is never invoked because a value was required and validation failed. 
     * Placing the table in a virtual form allows the table sort action to complete 
     * because validation for the required text field is not processed. This is similar 
     * to using the immediate property of a button, but allows table children to be 
     * submitted so that selected checkbox values may be sorted, for example.
     */
    @Property(name="internalVirtualForm", displayName="Is Internal Virtual Form", category="Advanced", isAttribute=false)
    private boolean internalVirtualForm = false;
    private boolean internalVirtualForm_set = false;

    /**
     * Flag indicating that this component should use a virtual form. A virtual form is 
     * equivalent to enclosing the table component in its own HTML form element, 
     * separate from other HTML elements on the same page. As an example, consider the 
     * case where a required text field and table appear on the same page. If the user 
     * clicks on a table sort button, while the required text field has no value, the 
     * sort action is never invoked because a value was required and validation failed. 
     * Placing the table in a virtual form allows the table sort action to complete 
     * because validation for the required text field is not processed. This is similar 
     * to using the immediate property of a button, but allows table children to be 
     * submitted so that selected checkbox values may be sorted, for example.
     */
    public boolean isInternalVirtualForm() {
        if (this.internalVirtualForm_set) {
            return this.internalVirtualForm;
        }
        ValueExpression _vb = getValueExpression("internalVirtualForm");
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
     * Flag indicating that this component should use a virtual form. A virtual form is 
     * equivalent to enclosing the table component in its own HTML form element, 
     * separate from other HTML elements on the same page. As an example, consider the 
     * case where a required text field and table appear on the same page. If the user 
     * clicks on a table sort button, while the required text field has no value, the 
     * sort action is never invoked because a value was required and validation failed. 
     * Placing the table in a virtual form allows the table sort action to complete 
     * because validation for the required text field is not processed. This is similar 
     * to using the immediate property of a button, but allows table children to be 
     * submitted so that selected checkbox values may be sorted, for example.
     */
    public void setInternalVirtualForm(boolean internalVirtualForm) {
        this.internalVirtualForm = internalVirtualForm;
        this.internalVirtualForm_set = true;
    }
    
    /**
     * Show the table pagination controls, which allow users to change which page is 
     * displayed. The controls include an input field for specifying the page number, a 
     * Go button to go to the specified page, and buttons for going to the first, last, 
     * previous, and next page.
     */
    @Property(name="paginationControls", displayName="Show Pagination Controls", category="Appearance")
    private boolean paginationControls = false;
    private boolean paginationControls_set = false;

    /**
     * Show the table pagination controls, which allow users to change which page is 
     * displayed. The controls include an input field for specifying the page number, a 
     * Go button to go to the specified page, and buttons for going to the first, last, 
     * previous, and next page.
     */
    public boolean isPaginationControls() {
        if (this.paginationControls_set) {
            return this.paginationControls;
        }
        ValueExpression _vb = getValueExpression("paginationControls");
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
     * Show the table pagination controls, which allow users to change which page is 
     * displayed. The controls include an input field for specifying the page number, a 
     * Go button to go to the specified page, and buttons for going to the first, last, 
     * previous, and next page.
     */
    public void setPaginationControls(boolean paginationControls) {
        this.paginationControls = paginationControls;
        this.paginationControls_set = true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.extraActionBottomHtml = (String) _values[1];
        this.extraActionTopHtml = (String) _values[2];
        this.extraFooterHtml = (String) _values[3];
        this.extraPanelHtml = (String) _values[4];
        this.extraTitleHtml = (String) _values[5];
        this.footerText = (String) _values[6];
        this.internalVirtualForm = ((Boolean) _values[7]).booleanValue();
        this.internalVirtualForm_set = ((Boolean) _values[8]).booleanValue();
        this.paginationControls = ((Boolean) _values[9]).booleanValue();
        this.paginationControls_set = ((Boolean) _values[10]).booleanValue();
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[11];
        _values[0] = super.saveState(_context);
        _values[1] = this.extraActionBottomHtml;
        _values[2] = this.extraActionTopHtml;
        _values[3] = this.extraFooterHtml;
        _values[4] = this.extraPanelHtml;
        _values[5] = this.extraTitleHtml;
        _values[6] = this.footerText;
        _values[7] = this.internalVirtualForm ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.internalVirtualForm_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.paginationControls ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.paginationControls_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
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
        clear(); // Clear cached properties.

        // Initialize the internal virtual form used by this component.
        if (isInternalVirtualForm()) {
            // Get Form component.
            Form form = (Form) Util.getForm(getFacesContext(), this);
            if (form != null) {
                // Create VirtualFormDescriptor object.
                String id = getClientId(context) + "_virtualForm"; //NOI18N
                Form.VirtualFormDescriptor descriptor = 
                    new Form.VirtualFormDescriptor(id);
                String wildSuffix = String.valueOf(
                    NamingContainer.SEPARATOR_CHAR) + 
                    String.valueOf(Form.ID_WILD_CHAR);
                descriptor.setParticipatingIds(new String[]{getId() + wildSuffix});
                descriptor.setSubmittingIds(new String[]{getId() + wildSuffix});
        
                // Add virtual form.
                form.addInternalVirtualForm(descriptor);
            } else {
                log("encodeBegin", //NOI18N
                    "Internal virtual form not set, form ancestor is null"); //NOI18N
            }
        }
        super.encodeBegin(context);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
