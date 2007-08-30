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

import com.sun.data.provider.RowKey;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.util.ArrayList;
import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

/**
 * Component that represents a group of table rows.
 * <p>
 * The TableRowGroup component provides a layout mechanism for displaying rows 
 * of data. UI guidelines describe specific behavior that can applied to the 
 * rows and columns of data such as sorting, filtering, pagination, selection, 
 * and custom user actions. In addition, UI guidelines also define sections of 
 * the table that can be used for titles, row group headers, and placement of 
 * pre-defined and user defined actions.
 * </p><p>
 * The TableRowGroup component supports a data binding to a collection of data 
 * objects represented by a TableDataProvider instance, which is the 
 * current value of this component itself. During iterative processing over the
 * rows of data in the data provider, the TableDataProvider for the current row 
 * is exposed as a request attribute under the key specified by the 
 * var property.
 * </p><p>
 * Only children of type TableColumn should be processed by renderers associated
 * with this component.
 * </p><p>
 * Note: Column headers and footers are rendered by TableRowGroupRenderer. Table
 * column footers are rendered by TableRenderer.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.web.ui.component.TableRowGroup.level = FINE
 * </pre></p><p>
 * See TLD docs for more information.
 * </p>
 */
@Component(type="com.sun.webui.jsf.TableRowGroup",
    family="com.sun.webui.jsf.TableRowGroup",
    displayName="Row Group", tagName="tableRowGroup",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_row_group",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_row_group_props")
public class TableRowGroup extends TableRowGroupBase {
    /** The id for the column footer bar. */
    public static final String COLUMN_FOOTER_BAR_ID = "_columnFooterBar"; //NOI18N

    /** The id for the column header bar. */
    public static final String COLUMN_HEADER_BAR_ID = "_columnHeaderBar"; //NOI18N

    /** The component id for the empty data column. */
    public static final String EMPTY_DATA_COLUMN_ID = "_emptyDataColumn"; //NOI18N

    /** The facet name for the empty data column. */
    public static final String EMPTY_DATA_COLUMN_FACET = "emptyDataColumn"; //NOI18N

    /** The component id for the empty data text. */
    public static final String EMPTY_DATA_TEXT_ID = "_emptyDataText"; //NOI18N

    /** The facet name for the empty data text. */
    public static final String EMPTY_DATA_TEXT_FACET = "emptyDataText"; //NOI18N

    /** The facet name for the group footer area. */
    public static final String FOOTER_FACET = "footer"; //NOI18N

    /** The id for the group footer bar. */
    public static final String GROUP_FOOTER_BAR_ID = "_groupFooterBar"; //NOI18N

    /** The component id for the group footer. */
    public static final String GROUP_FOOTER_ID = "_groupFooter"; //NOI18N

    /** The facet name for the group footer. */
    public static final String GROUP_FOOTER_FACET = "groupFooter"; //NOI18N
  
    /** The id for the table row group header bar. */
    public static final String GROUP_HEADER_BAR_ID = "_groupHeaderBar"; //NOI18N

    /** The component id for the table row group header. */
    public static final String GROUP_HEADER_ID = "_groupHeader"; //NOI18N

    /** The facet name for the table row group header. */
    public static final String GROUP_HEADER_FACET = "groupHeader"; //NOI18N

    /** The facet name for the group header area. */
    public static final String HEADER_FACET = "header"; //NOI18N

    /** The id for the table column footers bar. */
    public static final String TABLE_COLUMN_FOOTER_BAR_ID = "_tableColumnFooterBar"; //NOI18N

    // The number of columns to be rendered.
    private int columnCount = -1;

    // Flag indicating paginated state.
    private boolean paginated = false;
    private boolean paginated_set = false;
    
    // The Table ancestor enclosing this component.
    private Table tableAncestor = null;

    /** Default constructor */
    public TableRowGroup() {
        super();
        setRendererType("com.sun.webui.jsf.TableRowGroup");
    }

    /**
     * Return the family for this component.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TableRowGroup";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Child methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Clear cached properties.
     * <p>
     * Note: Properties may have been cached via the apply request values,
     * validate, and update phases and must be re-evaluated during the render
     * response phase (e.g., the underlying DataProvider may have changed). This
     * cannot always be done via encodeBegin because the component's parent may
     * need to obtain updated properties before this component is rendered.
     * </p>
     */
    public void clear() {
        tableAncestor = null;       
        columnCount = -1;
        super.clear();
    }

    /**
     * Get the closest Table ancestor that encloses this component.
     *
     * @return The Table ancestor.
     */
    public Table getTableAncestor() {
        if (tableAncestor == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof Table) {
                    tableAncestor = (Table) component;
                    break;
                }
            }
        }
        return tableAncestor;
    }

    /**
     * Get the number of columns found for this component that have a rendered
     * property of true.
     *
     * @return The number of rendered columns.
     */
    public int getColumnCount() {
        // Get column count.
        if (columnCount == -1) {
            columnCount = 0; // Initialize min value.
            Iterator kids = getTableColumnChildren();
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                columnCount += col.getColumnCount();
            }
        }
        return columnCount;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Component methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get empty data column.
     *
     * @return The empty data column.
     */
    public UIComponent getEmptyDataColumn() {
        UIComponent facet = getFacet(EMPTY_DATA_COLUMN_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableColumn child = new TableColumn();
	child.setId(EMPTY_DATA_COLUMN_ID);
        child.setColSpan(getColumnCount());
        child.getChildren().add(getEmptyDataText());

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get empty data text.
     *
     * @return The empty data text.
     */
    public UIComponent getEmptyDataText() {
        UIComponent facet = getFacet(EMPTY_DATA_TEXT_FACET);
        if (facet != null) {
            return facet;
        }

        Theme theme = getTheme();

        // Get message.
        String msg = null;
        if (getEmptyDataMsg() != null) {
            msg = getEmptyDataMsg();
        } else {
            // Get unfiltered row keys.
            RowKey[] rowKeys = getRowKeys();
            if (rowKeys != null && rowKeys.length > 0) {
                msg = theme.getMessage("table.filteredData"); //NOI18N
            } else {
                msg = theme.getMessage("table.emptyData"); //NOI18N
            }
        }

        // Get child.
        StaticText child = new StaticText();
        child.setId(EMPTY_DATA_TEXT_ID);        
        child.setStyleClass(theme.getStyleClass(ThemeStyles.TABLE_MESSAGE_TEXT));
        child.setText(msg);

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get group footer.
     *
     * @return The group footer.
     */
    public UIComponent getGroupFooter() {
        UIComponent facet = getFacet(GROUP_FOOTER_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableFooter child = new TableFooter();
	child.setId(GROUP_FOOTER_ID);
        child.setColSpan(getColumnCount());
        child.setExtraHtml(getExtraFooterHtml());
        child.setGroupFooter(true);

        // Set rendered.
        facet = getFacet(FOOTER_FACET);
        if (!(facet != null && facet.isRendered() || getFooterText() != null)) {
            child.setRendered(false);
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get group header.
     *
     * @return The group header.
     */
    public UIComponent getGroupHeader() {
        UIComponent facet = getFacet(GROUP_HEADER_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableHeader child = new TableHeader();
	child.setId(GROUP_HEADER_ID);
        child.setScope("colgroup"); //NOI18N
        child.setColSpan(getColumnCount());
        child.setExtraHtml(getExtraHeaderHtml());
        child.setGroupHeader(true);

        // Don't render for an empty table.
        boolean emptyTable = getRowCount() == 0;
        boolean renderControls = !emptyTable
            && (isSelectMultipleToggleButton() || isGroupToggleButton());

        // Set rendered.
        facet = getFacet(HEADER_FACET);
        if (!(facet != null && facet.isRendered()
                || getHeaderText() != null || renderControls)) {
            child.setRendered(false);
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Pagination methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the zero-relative row number of the first row to be displayed for
     * a paginated table.
     * <p>
     * Note: If rows have been removed from the underlying DataProvider, there
     * is a chance that the first row could be greater than the total number of
     * rows. In this case, the zero-relative row number of the last page to be 
     * displayed is returned.
     * </p>
     * @return The zero-relative row number of the first row to be displayed.
     */
    public int getFirst() {
        // Ensure the first row is less than the row number of the last page.
        int last = getLast();
        int first = isPaginated() ? super.getFirst() : 0;
        return (first < last) ? first : last;
    }

    /**
     * Get current page number to be displayed.
     * <p>
     * Note: The default is 1 when the table is not paginated.
     * </p>
     * @return The current page number to be displayed.
     */
    public int getPage() {
        if (!isPaginated()) { // Rows is zero when not paginated.
            return 1;
        }
        return super.getPage();
    }

    /**
     * Test the paginated state of this component.
     * <p>
     * Note: If the paginationControls property of the Table component is true,
     * this property will be initialized as true.
     * </p>
     * @return true for paginate mode, false for scroll mode.
     */
    public boolean isPaginated() {
        if (!paginated_set) {
            Table table = getTableAncestor();
            if (table != null) {
                setPaginated(table.isPaginationControls());
            }
        }
        return paginated;
    }

    /**
     * Set the paginated state of this component.
     * <p>
     * Note: When pagination controls are used, a value of true allows both
     * pagination controls and paginate buttons to be displayed. A value of
     * false allows only paginate buttons to be displayed. However, when all
     * data fits on one page, neither pagination controls or paginate buttons
     * are displayed.
     * </p><p>
     * Note: To properly maintain the paginated state of the table per UI
     * guidelines, the paginated property is cached. If the paginationControls 
     * property of the table component changes (e.g., in an application builder 
     * environment), use this method to set the paginated property accordingly.
     * </p>
     * @param paginated The paginated state of this component.
     */
    public void setPaginated(boolean paginated) {
        this.paginated = paginated;
        paginated_set = true;
    }

    /**
     * Get the number of rows to be displayed for a paginated table.
     * <p>
     * Note: UI guidelines recommend a default value of 25 rows per page.
     * </p>
     * @return The number of rows to be displayed for a paginated table.
     */
    public int getRows() {
        return isPaginated() ? super.getRows() : 0;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Row methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get an array of hidden RowKey objects from the underlying 
     * TableDataProvider taking filtering, sorting, and pagination into account.
     * <p>
     * Note: The returned RowKey objects depend on the FilterCriteria and 
     * SortCriteria objects provided to the TableDataFilter and TableDataSorter
     * instances used by this component. If TableDataFilter and TableDataSorter
     * are modified directly, invoke the clearSort and clearFilter method to
     * clear the previous sort and filter.
     * </p>
     * @return An array of RowKey objects.
     */
    public RowKey[] getHiddenRowKeys() {
        if (!isPaginated()) {
            return null; // No rows are hidden during scroll mode.
        }
        return super.getHiddenRowKeys();
    }

    /**
     * Get an array of rendered RowKey objects from the underlying 
     * TableDataProvider taking filtering, sorting, and pagination into account.
     * <p>
     * Note: The returned RowKey objects depend on the FilterCriteria and 
     * SortCriteria objects provided to the TableDataFilter and TableDataSorter
     * instances used by this component. If TableDataFilter and TableDataSorter
     * are modified directly, invoke the clearSort and clearFilter method to
     * clear the previous sort and filter.
     * </p>
     * @return An array of RowKey objects.
     */
    public RowKey[] getRenderedRowKeys() {
        // Get sorted RowKey objects.
        RowKey[] rowKeys = getSortedRowKeys();
        if (rowKeys == null) {
            return rowKeys;
        }  

        // Find the number of selected rows hidden from view.
        ArrayList list = new ArrayList();
        int first = getFirst();
        int rows = getRows();
        for (int i = first; i < rowKeys.length; i++) {
            // Have we displayed the paginated number of rows?
            if (isPaginated() && i >= first + rows) {
                break;
            }
            list.add(rowKeys[i]);
        }
        rowKeys = new RowKey[list.size()];
        return (RowKey[]) list.toArray(rowKeys);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // UIComponent methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Override the default UIComponentBase.processUpdates() processing to
     * perform the following steps.
     *
     * <ul>
     * <li>If the rendered property of this UIComponent is false, skip further
     *     processing.</li>
     * <li>Set the current RowKey to null.</li>
     * <li>Call the processUpdates() method of all facets of this TableRowGroup,
     *     in the order determined by a call to getFacets().keySet().iterator().</li>
     * <li>Call the processUpdates() method of all facets of the TableColumn
     *     children of this TableRowGroup.</li>
     * <li>Iterate over the set of rows that were included when this component
     *     was rendered (i.e. those defined by the first and rows properties),
     *     performing the following processing for each row:</li>
     * <li>Set the current RowKey to the appropriate value for this row.</li>
     * <li>If isRowAvailable() returns true, iterate over the children
     *     components of each TableColumn child of this TableRowGroup component,
     *     calling the processUpdates() method for each such child.</li>
     * <li>Set the current RowKey to null.</li>
     * </ul>
     *
     * @param context FacesContext for the current request.
     *
     * @exception NullPointerException if FacesContext is null.
     */
    public void processUpdates(FacesContext context) {
        if (!isRendered()) {
            return;
        }
        super.processUpdates(context);

        // Set collapsed property applied client-side.
        UIComponent header = getFacet(GROUP_HEADER_ID);
        UIComponent field = (header != null)
            ? (UIComponent) header.getFacets().get(
                TableHeader.COLLAPSED_HIDDEN_FIELD_ID)
            : null;
        if (field instanceof HiddenField) {
            Boolean value = (field != null) 
                ? (Boolean) ((HiddenField) field).getText() : null;
            setCollapsed(value.booleanValue());
        }
        // This is not a EditableValueHolder, so no further processing is required
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get Theme objects.
     *
     * @return The current theme.
     */
    private Theme getTheme() {
	return ThemeUtilities.getTheme(getFacesContext());
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Set the <code>aboveColumnFooter</code> attribute to true to display the group 
     * footer bar above the column footers bar. The default is to display the group 
     * footer below the column footers.
     */
    @Property(name="aboveColumnFooter", displayName="Is Above Column Footer", category="Appearance")
    private boolean aboveColumnFooter = false;
    private boolean aboveColumnFooter_set = false;

    /**
     * Set the <code>aboveColumnFooter</code> attribute to true to display the group 
     * footer bar above the column footers bar. The default is to display the group 
     * footer below the column footers.
     */
    public boolean isAboveColumnFooter() {
        if (this.aboveColumnFooter_set) {
            return this.aboveColumnFooter;
        }
        ValueExpression _vb = getValueExpression("aboveColumnFooter");
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
     * Set the <code>aboveColumnFooter</code> attribute to true to display the group 
     * footer bar above the column footers bar. The default is to display the group 
     * footer below the column footers.
     */
    public void setAboveColumnFooter(boolean aboveColumnFooter) {
        this.aboveColumnFooter = aboveColumnFooter;
        this.aboveColumnFooter_set = true;
    }

    /**
     * Set the <code>aboveColumnHeader</code> attribute to true to display the group 
     * header bar above the column headers bar. The default is to display the group 
     * header below the column headers.
     */
    @Property(name="aboveColumnHeader", displayName="Is Above Column Header", category="Appearance")
    private boolean aboveColumnHeader = false;
    private boolean aboveColumnHeader_set = false;

    /**
     * Set the <code>aboveColumnHeader</code> attribute to true to display the group 
     * header bar above the column headers bar. The default is to display the group 
     * header below the column headers.
     */
    public boolean isAboveColumnHeader() {
        if (this.aboveColumnHeader_set) {
            return this.aboveColumnHeader;
        }
        ValueExpression _vb = getValueExpression("aboveColumnHeader");
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
     * Set the <code>aboveColumnHeader</code> attribute to true to display the group 
     * header bar above the column headers bar. The default is to display the group 
     * header below the column headers.
     */
    public void setAboveColumnHeader(boolean aboveColumnHeader) {
        this.aboveColumnHeader = aboveColumnHeader;
        this.aboveColumnHeader_set = true;
    }


    /**
     * Extra HTML code to be appended to the <code>&lt;tr&gt;</code> HTML element that 
     * is rendered for the group footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    @Property(name="extraFooterHtml", displayName="Extra Footer HTML", category="Advanced")
    private String extraFooterHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;tr&gt;</code> HTML element that 
     * is rendered for the group footer. Use only code that is valid in an HTML 
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
     * Extra HTML code to be appended to the <code>&lt;tr&gt;</code> HTML element that 
     * is rendered for the group footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public void setExtraFooterHtml(String extraFooterHtml) {
        this.extraFooterHtml = extraFooterHtml;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;tr&gt;</code> HTML element that 
     * is rendered for the group header. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    @Property(name="extraHeaderHtml", displayName="Extra Header HTML", category="Advanced")
    private String extraHeaderHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;tr&gt;</code> HTML element that 
     * is rendered for the group header. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public String getExtraHeaderHtml() {
        if (this.extraHeaderHtml != null) {
            return this.extraHeaderHtml;
        }
        ValueExpression _vb = getValueExpression("extraHeaderHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;tr&gt;</code> HTML element that 
     * is rendered for the group header. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public void setExtraHeaderHtml(String extraHeaderHtml) {
        this.extraHeaderHtml = extraHeaderHtml;
    }

    /**
     * The text to be displayed in the group footer.
     */
    @Property(name="footerText", displayName="Footer Text", category="Appearance")
    private String footerText = null;

    /**
     * The text to be displayed in the group footer.
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
     * The text to be displayed in the group footer.
     */
    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    /**
     * Use the <code>multipleColumnFooters</code> attribute when the 
     * <code>webuijsf:tableRowGroup</code> contains nested <code>webuijsf:tableColumn</code> tags, 
     * and you want the footers of all the <code>webuijsf:tableColumn</code> tags to be 
     * shown. The default is to show the footers of only the innermost level of nested 
     * <code>webuijsf:tableColumn</code> tags.
     */
    @Property(name="multipleColumnFooters", displayName="Show Multiple Column Footers", category="Layout")
    private boolean multipleColumnFooters = false;
    private boolean multipleColumnFooters_set = false;

    /**
     * Use the <code>multipleColumnFooters</code> attribute when the 
     * <code>webuijsf:tableRowGroup</code> contains nested <code>webuijsf:tableColumn</code> tags, 
     * and you want the footers of all the <code>webuijsf:tableColumn</code> tags to be 
     * shown. The default is to show the footers of only the innermost level of nested 
     * <code>webuijsf:tableColumn</code> tags.
     */
    public boolean isMultipleColumnFooters() {
        if (this.multipleColumnFooters_set) {
            return this.multipleColumnFooters;
        }
        ValueExpression _vb = getValueExpression("multipleColumnFooters");
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
     * Use the <code>multipleColumnFooters</code> attribute when the 
     * <code>webuijsf:tableRowGroup</code> contains nested <code>webuijsf:tableColumn</code> tags, 
     * and you want the footers of all the <code>webuijsf:tableColumn</code> tags to be 
     * shown. The default is to show the footers of only the innermost level of nested 
     * <code>webuijsf:tableColumn</code> tags.
     */
    public void setMultipleColumnFooters(boolean multipleColumnFooters) {
        this.multipleColumnFooters = multipleColumnFooters;
        this.multipleColumnFooters_set = true;
    }

    /**
     * Use the <code>multipleTableColumnFooters</code> attribute when the 
     * <code>webuijsf:tableRowGroup</code> contains nested <code>webuijsf:tableColumn</code> tags, 
     * and you want the table footers of all the <code>webuijsf:tableColumn</code> tags to 
     * be shown. The default is to show the table footers of only the innermost level 
     * of nested <code>webuijsf:tableColumn</code> tags.
     */
    @Property(name="multipleTableColumnFooters", displayName="Show Nested Table Column Footers", category="Layout")
    private boolean multipleTableColumnFooters = false;
    private boolean multipleTableColumnFooters_set = false;

    /**
     * Use the <code>multipleTableColumnFooters</code> attribute when the 
     * <code>webuijsf:tableRowGroup</code> contains nested <code>webuijsf:tableColumn</code> tags, 
     * and you want the table footers of all the <code>webuijsf:tableColumn</code> tags to 
     * be shown. The default is to show the table footers of only the innermost level 
     * of nested <code>webuijsf:tableColumn</code> tags.
     */
    public boolean isMultipleTableColumnFooters() {
        if (this.multipleTableColumnFooters_set) {
            return this.multipleTableColumnFooters;
        }
        ValueExpression _vb = getValueExpression("multipleTableColumnFooters");
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
     * Use the <code>multipleTableColumnFooters</code> attribute when the 
     * <code>webuijsf:tableRowGroup</code> contains nested <code>webuijsf:tableColumn</code> tags, 
     * and you want the table footers of all the <code>webuijsf:tableColumn</code> tags to 
     * be shown. The default is to show the table footers of only the innermost level 
     * of nested <code>webuijsf:tableColumn</code> tags.
     */
    public void setMultipleTableColumnFooters(boolean multipleTableColumnFooters) {
        this.multipleTableColumnFooters = multipleTableColumnFooters;
        this.multipleTableColumnFooters_set = true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext context, Object state) {
        Object _values[] = (Object[]) state;
        super.restoreState(context, _values[0]);
        this.aboveColumnFooter = ((Boolean) _values[1]).booleanValue();
        this.aboveColumnFooter_set = ((Boolean) _values[2]).booleanValue();
        this.aboveColumnHeader = ((Boolean) _values[3]).booleanValue();
        this.aboveColumnHeader_set = ((Boolean) _values[4]).booleanValue();
        this.extraFooterHtml = (String) _values[5];
        this.extraHeaderHtml = (String) _values[6];
        this.footerText = (String) _values[7];
        this.multipleColumnFooters = ((Boolean) _values[8]).booleanValue();
        this.multipleColumnFooters_set = ((Boolean) _values[9]).booleanValue();
        this.multipleTableColumnFooters = ((Boolean) _values[10]).booleanValue();
        this.multipleTableColumnFooters_set = ((Boolean) _values[11]).booleanValue();
        setPaginated(((Boolean) _values[12]).booleanValue());
    }

    /**
     * Save the state of this component.
     *
     * @return An array of Object values.
     */
    public Object saveState(FacesContext context) {
        Object _values[] = new Object[13];
        _values[0] = super.saveState(context);
        _values[1] = this.aboveColumnFooter ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.aboveColumnFooter_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.aboveColumnHeader ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.aboveColumnHeader_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.extraFooterHtml;
        _values[6] = this.extraHeaderHtml;
        _values[7] = this.footerText;
        _values[8] = this.multipleColumnFooters ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.multipleColumnFooters_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.multipleTableColumnFooters ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.multipleTableColumnFooters_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = isPaginated() ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
