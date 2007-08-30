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
import com.sun.data.provider.SortCriteria;
import com.sun.data.provider.FieldKey;
import com.sun.data.provider.impl.FieldIdSortCriteria;
import com.sun.webui.jsf.faces.ValueBindingSortCriteria;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.util.Date;
import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Component that represents a table column.
 * <p>
 * The tableColumn component provides a layout mechanism for displaying columns 
 * of data. UI guidelines describe specific behavior that can applied to the 
 * rows and columns of data such as sorting, filtering, pagination, selection, 
 * and custom user actions. In addition, UI guidelines also define sections of 
 * the table that can be used for titles, row group headers, and placement of 
 * pre-defined and user defined actions.
 * </p><p>
 * Note: Column headers and footers are rendered by TableRowGroupRenderer. Table
 * column footers are rendered by TableRenderer.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.web.ui.component.TableColumn.level = FINE
 * </pre></p><p>
 * See TLD docs for more information.
 * </p>
 */
@Component(type="com.sun.webui.jsf.TableColumn",
    family="com.sun.webui.jsf.TableColumn",
    displayName="Column", tagName="tableColumn",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_column",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_column_props")
public class TableColumn extends TableColumnBase {
    /** The component id for the column footer. */
    public static final String COLUMN_FOOTER_ID = "_columnFooter"; //NOI18N

    /** The facet name for the column footer. */
    public static final String COLUMN_FOOTER_FACET = "columnFooter"; //NOI18N

    /** The component id for the column header. */
    public static final String COLUMN_HEADER_ID = "_columnHeader"; //NOI18N

    /** The facet name for the column header. */
    public static final String COLUMN_HEADER_FACET = "columnHeader"; //NOI18N

    /** The component id for the embedded action separator icon. */
    public static final String EMBEDDED_ACTION_SEPARATOR_ICON_ID =
        "_embeddedActionSeparatorIcon"; //NOI18N

    /** The facet name for the embedded action separator icon. */
    public static final String EMBEDDED_ACTION_SEPARATOR_ICON_FACET =
        "embeddedActionSeparatorIcon"; //NOI18N

    /** The component id for the empty cell icon. */
    public static final String EMPTY_CELL_ICON_ID = "_emptyCellIcon"; //NOI18N

    /** The facet name for the empty cell icon. */
    public static final String EMPTY_CELL_ICON_FACET = "emptyCellIcon"; //NOI18N

    /** The facet name for the footer area. */
    public static final String FOOTER_FACET = "footer"; //NOI18N

    /** The facet name for the header area. */
    public static final String HEADER_FACET = "header"; //NOI18N

    /** The component id for the table column footer. */
    public static final String TABLE_COLUMN_FOOTER_ID = "_tableColumnFooter"; //NOI18N

    /** The facet name for the table column footer. */
    public static final String TABLE_COLUMN_FOOTER_FACET = "tableColumnFooter"; //NOI18N

    /** The facet name for the table footer area. */
    public static final String TABLE_FOOTER_FACET = "tableFooter"; //NOI18N

    // The Table ancestor enclosing this component.
    private Table tableAncestor = null;

    // The TableColumn ancestor enclosing this component.
    private TableColumn tableColumnAncestor = null;

    // The TableRowGroup ancestor enclosing this component.
    private TableRowGroup tableRowGroupAncestor = null;

    // The number of columns to be rendered.
    private int columnCount = -1;

    // The number of rows to be rendered for headers and footers.
    private int rowCount = -1;

    /** Default constructor */
    public TableColumn() {
        super();
        setRendererType("com.sun.webui.jsf.TableColumn");
    }

    /**
     * Return the family for this component.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TableColumn";
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
        tableColumnAncestor = null;
        tableRowGroupAncestor = null;
        columnCount = -1;
        rowCount = -1;
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
     * Get the closest TableColumn ancestor that encloses this component.
     *
     * @return The TableColumn ancestor.
     */
    public TableColumn getTableColumnAncestor() {
        // Get TableColumn ancestor.
        if (tableColumnAncestor == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof TableColumn) {
                    tableColumnAncestor = (TableColumn) component;
                    break;
                }
            }
        }
        return tableColumnAncestor;
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
            columnCount = getColumnCount(this);
        }
        return columnCount;
    }

    /**
     * Get the closest TableRowGroup ancestor that encloses this component.
     *
     * @return The TableRowGroup ancestor.
     */
    public TableRowGroup getTableRowGroupAncestor() {
        // Get TableRowGroup ancestor.
        if (tableRowGroupAncestor == null) {
            UIComponent component = this;
            while (component != null) {
                component = component.getParent();
                if (component instanceof TableRowGroup) {
                    tableRowGroupAncestor = (TableRowGroup) component;
                    break;
                }
            }
        }
        return tableRowGroupAncestor;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Column methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the horizontal alignment for the cell.
     * <p>
     * Note: If the align property is specified, it is returned as is. However, 
     * if the alignKey property is provided, alignment is based on the object 
     * type of the data element. For example, Date and Number objects are
     * aligned using "right", Character and String objects are aligned using 
     * "left", and Boolean objects are aligned using "center". Note that select 
     * columns are aligned using "center" by default.
     * </p>
     * @return The horizontal alignment for the cell. If the align property is 
     * null or the object type cannot be determined, "left" is returned by 
     * default.
     */
    public String getAlign() {
        // Note: The align property overrides alignKey.
        String result = super.getAlign();
        if (result != null) {
            return result;
        }

        // Get alignment based on object type.
        Class type = getType();
        if (type != null && (Character.class.isAssignableFrom(type) 
                || String.class.isAssignableFrom(type))) {
            result = "left"; //NOI18N
        } else if (type != null && (Date.class.isAssignableFrom(type) 
                || Number.class.isAssignableFrom(type))) {
            result = "right"; //NOI18N
        } else if (type != null && Boolean.class.isAssignableFrom(type)) {
            result = "center"; //NOI18N
        } else {
            // Note: Select columns also default to "left".
            result = "left"; //NOI18N
        }
        return result;
    }

    /**
     * Get column footer.
     *
     * @return The column footer.
     */
    public UIComponent getColumnFooter() {
        UIComponent facet = getFacet(COLUMN_FOOTER_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableFooter child = new TableFooter();
	child.setId(COLUMN_FOOTER_ID);
        child.setAlign(getAlign());
        child.setExtraHtml(getExtraFooterHtml());
        child.setVisible(isVisible());

        // Set rendered.
        if (!(facet != null && facet.isRendered()
                || isColumnFooterRendered())) {
            // Note: Footer may be initialized to force rendering. This allows
            // developers to omit the footerText property for select columns.
            child.setRendered(false);
        } else {
            log("getColumnFooter", //NOI18N
                "Column footer not rendered, nothing to display"); //NOI18N
        }

        // If only showing one level, don't set colspan or rowspan.
        TableRowGroup group = getTableRowGroupAncestor();
        if (group != null && group.isMultipleColumnFooters()) {
            // Set colspan for nested TableColumn children, else rowspan.
            Iterator kids = getTableColumnChildren();
            if (kids.hasNext()) {
                int colspan = getColumnCount();
                if (colspan > 1) {
                    child.setColSpan(colspan);
                }
            } else {
                int rowspan = getRowCount();
                if (rowspan > 1) {
                    child.setRowSpan(rowspan);
                }
            }
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get column header.
     *
     * @return The column header.
     */
    public UIComponent getColumnHeader() {
        UIComponent facet = getFacet(COLUMN_HEADER_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableHeader child = new TableHeader();
	child.setId(COLUMN_HEADER_ID);
        child.setScope("col"); //NOI18N
        child.setAlign(getAlign());
        child.setWidth((getSelectId() != null) ? "3%" : null); //NOI18N
        child.setNoWrap((getSelectId() != null) ? true : false);
        child.setExtraHtml(getExtraHeaderHtml());
        child.setVisible(isVisible());

        // Set type of header to render.
        boolean emptyTable = isEmptyTable();
        SortCriteria criteria = getSortCriteria();
        if (criteria != null && getSelectId() != null && !emptyTable) {
            child.setSelectHeader(true);
        } else if (criteria != null && getHeaderText() != null && !emptyTable) {
            child.setSortHeader(true);
        } else {
            log("getColumnHeader", //NOI18N
                "Render default column header, no SortCriteria or selectId"); //NOI18N
        }

        // Set rendered.
        if (!(facet != null && facet.isRendered()
                || isColumnHeaderRendered())) {
            // Note: Footer may be initialized to force rendering. This allows
            // developers to omit the headerText property for select columns.
            log("getColumnHeader", //NOI18N
                "Column header not rendered, nothing to display"); //NOI18N
            child.setRendered(false);            
        }

        // Set colspan for nested TableColumn children, else rowspan.
        Iterator kids = getTableColumnChildren();
        if (kids.hasNext()) {
            int colspan = getColumnCount();
            if (colspan > 1) {
                child.setColSpan(colspan);
            }
        } else {
            int rowspan = getRowCount();
            if (rowspan > 1) {
                child.setRowSpan(rowspan);
            }
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get table column footer.
     *
     * @return The table column footer.
     */
    public UIComponent getTableColumnFooter() {
        UIComponent facet = getFacet(TABLE_COLUMN_FOOTER_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        TableFooter child = new TableFooter();
	child.setId(TABLE_COLUMN_FOOTER_ID);
        child.setAlign(getAlign());
        child.setExtraHtml(getExtraTableFooterHtml());
        child.setTableColumnFooter(true);
        child.setVisible(isVisible());

        // Set rendered.
        if (!(facet != null && facet.isRendered()
                || isTableColumnFooterRendered())) {
            // Note: Footer may be initialized to force rendering. This allows
            // developers to omit the tableFooterText property for select columns.
            child.setRendered(false);
        } else {
            log("getTableColumnFooter", //NOI18N
                "Table column footer not rendered, nothing to display"); //NOI18N
        }

        // If only showing one level, don't set colspan or rowspan.
        TableRowGroup group = getTableRowGroupAncestor();
        if (group != null && group.isMultipleTableColumnFooters()) {
            // Set colspan for nested TableColumn children, else rowspan.
            Iterator kids = getTableColumnChildren();
            if (kids.hasNext()) {
                int colspan = getColumnCount();
                if (colspan > 1) {
                    child.setColSpan(colspan);
                }
            } else {
                int rowspan = getRowCount();
                if (rowspan > 1) {
                    child.setRowSpan(rowspan);
                }
            }
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Empty cell methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the icon used to display inapplicable or unexpectedly empty cells.
     * <p>
     * Note: UI guidelines suggest not to use this for a value that is truly
     * null, such as an empty alarm cell or a comment field which is blank,
     * neither of which should have the dash image. Further, it is recomended
     * not to use the dash image for cells that contain user interface elements
     * such as checkboxes or drop-down lists when these elements are not 
     * applicable. Instead, simply do not display the user interface element.
     * </p>
     * @return The icon used to display empty cells.
     */
    public UIComponent getEmptyCellIcon() {       
        UIComponent facet = getFacet(EMPTY_CELL_ICON_FACET);
        if (facet != null) {
            return facet;
        }

        Theme theme = getTheme();

        // Get child.
        Icon child = ThemeUtilities.getIcon(theme,
		ThemeImages.TABLE_EMPTY_CELL);
	child.setId(EMPTY_CELL_ICON_ID);
        child.setBorder(0);

        // Set tool tip.
        String toolTip = theme.getMessage("table.emptyTableCell"); //NOI18N
        child.setToolTip(toolTip);
        child.setAlt(toolTip);
        
        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Separator methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get separator icon for embedded actions.
     *
     * @return The separator icon for embedded actions.
     */
    public UIComponent getEmbeddedActionSeparatorIcon() {
        UIComponent facet = getFacet(EMBEDDED_ACTION_SEPARATOR_ICON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Icon child = ThemeUtilities.getIcon(getTheme(),
		ThemeImages.TABLE_EMBEDDED_ACTIONS_SEPARATOR);
	child.setId(EMBEDDED_ACTION_SEPARATOR_ICON_ID);
        child.setBorder(0);
        child.setAlign("top"); //NOI18N
        
        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Sort methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get SortCriteria used for sorting the contents of a TableDataProvider.
     * <p>
     * Note: If the sortKey attribute resolves to a SortCriteria object, it is
     * returned as is. However, if there is a value binding, and it's not null,
     * a ValueBindingSortCriteria object is created. If there is no value 
     * binding, a FieldIdSortCriteria object is created.
     * </p>
     * @return The SortCriteria used for sorting.
     */
    public SortCriteria getSortCriteria() {
        // Return if value binding resolves to a SortCriteria object.
        Object key = getSort();
        if (key instanceof SortCriteria) {
            return (SortCriteria) key;
        }

        SortCriteria result = null;
        ValueExpression vb = getValueExpression("sort"); //NOI18N
        if (vb != null) {
            ValueBindingSortCriteria vbsc = new ValueBindingSortCriteria(vb, 
                !isDescending()); // Note: Constructor accepts ascending param.
            TableRowGroup group = getTableRowGroupAncestor();
            if (group != null) {
                vbsc.setRequestMapKey(group.getSourceVar());
            }
            result = vbsc;
        } else if (key != null) {
            result = new FieldIdSortCriteria(key.toString(), !isDescending());
        }
        return result;
    }

    /**
     * Get sort tool tip augment based on the value given to the align 
     * property of the tableColumn component.
     *
     * @param descending Flag indicating descending sort.
     * @return The sort tool tip augment.
     */
    public String getSortToolTipAugment(boolean descending) {
        String result = null;

        // To do: Test for toolTip property? The alarm or other custom 
        // components may need to set the tooltip. If so, do we need both
        // ascending and descending tooltips?
       
        // Get object type.
        Class type = getType();

        // Get tooltip.
        ValueExpression vb = getValueExpression("severity"); //NOI18N
        if (getSeverity() != null || vb != null) {
            result = (descending)
                ? "table.sort.augment.alarmDescending" //NOI18N
                : "table.sort.augment.alarmAscending"; //NOI18N
        } else if (getSelectId() != null
                || (type != null && type.equals(Boolean.class))) {
            result = (descending)
                ? "table.sort.augment.booleanDescending" //NOI18N
                : "table.sort.augment.booleanAscending"; //NOI18N
        } else if (type != null && type.equals(String.class)) {
            result = (descending)
                ? "table.sort.augment.stringDescending" //NOI18N
                : "table.sort.augment.stringAscending"; //NOI18N
        } else if (type != null && type.equals(Character.class)) {
            result = (descending)
                ? "table.sort.augment.charDescending" //NOI18N
                : "table.sort.augment.charAscending"; //NOI18N
        } else if (type != null && type.equals(Date.class)) {
            result = (descending)
                ? "table.sort.augment.dateDescending" //NOI18N
                : "table.sort.augment.dateAscending"; //NOI18N
        } else if (type != null && type.equals(Number.class)) {
            result = (descending)
                ? "table.sort.augment.numericDescending" //NOI18N
                : "table.sort.augment.numericAscending"; //NOI18N    
        } else {
            result = (descending)
                ? "table.sort.augment.undeterminedDescending" //NOI18N
                : "table.sort.augment.undeterminedAscending"; //NOI18N
        }
        return getTheme().getMessage(result);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get the number of columns found for this component that 
     * have a rendered property of true.
     *
     * @param component TableColumn to be rendered.
     * @return The first selectId property found.
     */
    private int getColumnCount(TableColumn component) {       
        int count = 0;
        if (component == null) {
            log("getColumnCount", //NOI18N
                "Cannot obtain column count, TableColumn is null"); //NOI18N
            return count;
        }
    
        // Get column count for nested TableColumn children.
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                count += getColumnCount(col);
            }
        } else {
            // Do not include root TableColumn nodes in count.
            if (component.isRendered()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Helper method to get the number of rows found for this component that
     * have a rendered property of true.
     *
     * @return The number of rendered rows.
     */
    private int getRowCount() {
        if (rowCount == -1) {
            rowCount = 0; // Initialize min value.

            // Get all TableColumn children at the same level of the tree.
            Iterator kids = null;
            TableColumn col = getTableColumnAncestor();
            if (col != null) {
                kids = col.getTableColumnChildren();
            } else {
                TableRowGroup group = getTableRowGroupAncestor();
                kids = (group != null) ? group.getTableColumnChildren() : null;
            }

            // Get max row count for this level of the tree.
            if (kids != null) {
                while (kids.hasNext()) {
                    int result = getRowCount((TableColumn) kids.next());
                    if (rowCount < result) {
                        rowCount = result;
                    }
                }
            }
        }
        return rowCount;
    }

    /**
     * Helper method to get the number of rows found for this component that
     * have a rendered property of true.
     *
     * @param component TableColumn to be rendered.
     * @return The first selectId property found.
     */
    private int getRowCount(TableColumn component) {
        int count = 0;
        if (component == null) {
            log("getRowCount", "Cannot obtain row count, TableColumn is null"); //NOI18N
            return count;
        }

        // Get max row count for nested TableColumn children.
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                int result = getRowCount(col);
                if (count < result) {
                    count = result;
                }
            }
        }
        // Include root TableColumn component in count.
        return ++count;
    }

    /**
     * Helper method to get the data type of the data element referenced by the
     * alignKey property.
     *
     * @return The data type of the data element.
     */
    private Class getType() {
        // Note: Avoid using getSourceData when possible. If developers do not
        // cache their TableDataProvider objects, this may cause providers to be
        // recreated, for each reference, which affects performance. Instead, 
        // get the type cached in TableRowGroup.
        TableRowGroup group = getTableRowGroupAncestor();
        if (group == null) {
            log("getType", "Cannot obtain data type, TableRowGroup is null"); //NOI18N
            return null;
        }

        // Get FieldKey.
        FieldKey key = null;
        if (getAlignKey() instanceof FieldKey) {
            // If value binding resolves to FieldKey, use as is.
            key = (FieldKey) getAlignKey();
        } else if (getAlignKey() != null) {
            try {
                key = group.getFieldKey(getAlignKey().toString());
            } catch (IllegalArgumentException e) {
                log("getType", "Cannot obtain data type, object type may not be set"); //NOI18N
            }
        }
        return (key != null) ? group.getType(key) : String.class;
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
     * Helper method to test if column footers should be rendered.
     * <p>
     * Note: Since headers and footers are optional, we do not render them by 
     * default. However, if any of the properties above are set, they must be
     * set for all columns, including nested columns. Otherwise, we may end up
     * with no header or footer and columns shift left. Alternatively, 
     * developers could add an empty string for each property.
     * </p>
     */
    private boolean isColumnFooterRendered() {
        boolean result = false; // Assume no headers or footers are used.
        TableRowGroup group = getTableRowGroupAncestor();
        if (group == null) {
            log("isColumnFooterRendered", //NOI18N
                "Cannot determine if column footer is rendered, TableRowGroup is null"); //NOI18N
            return result;
        }

        // Test the footerText property for all TableColumn components.
        Iterator kids = group.getTableColumnChildren();
        while (kids.hasNext()) {
            TableColumn col = (TableColumn) kids.next();
            if (isColumnFooterRendered(col)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Helper method to test the footerText property for nested TableColumn
     * components.
     *
     * @param component TableColumn component to render.
     */
    private boolean isColumnFooterRendered(TableColumn component) {
        boolean rendered = false;
        if (component == null) {
            log("isColumnFooterRendered", //NOI18N
                "Cannot determine if column footer is rendered, TableColumn is null"); //NOI18N
            return rendered;
        }

        // Test the footerText property for all TableColumn components.
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (isColumnFooterRendered(col)) {
                    // When footer text is found, don't go any further.
                    return true;
                }
            }
        }

        // If either a facet or text are defined, set rendered property.
        UIComponent facet = component.getFacet(COLUMN_FOOTER_FACET);            
        if (facet != null && facet.isRendered() 
                || component.getFooterText() != null) {
            rendered = true;
        }
        return rendered;
    }

    /**
     * Helper method to test if column headers should be rendered.
     * <p>
     * Note: Since headers and footers are optional, we do not render them by 
     * default. However, if any of the properties above are set, they must be
     * set for all columns, including nested columns. Otherwise, we may end up
     * with no header or footer and columns shift left. Alternatively, 
     * developers could add an empty string for each property.
     * </p>
     */
    private boolean isColumnHeaderRendered() {
        boolean result = false; // Assume no headers or footers are used.
        TableRowGroup group = getTableRowGroupAncestor();
        if (group == null) {
            log("isColumnHeaderRendered", //NOI18N
                "Cannot determine if column header is rendered, TableRowGroup is null"); //NOI18N
            return result;
        }

        // Test the headerText property for all TableColumn components.
        Iterator kids = group.getTableColumnChildren();
        while (kids.hasNext()) {
            TableColumn col = (TableColumn) kids.next();
            if (isColumnHeaderRendered(col)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Helper method to test the headerText property for nested TableColumn
     * components.
     *
     * @param component TableColumn component to render.
     */
    private boolean isColumnHeaderRendered(TableColumn component) {
        boolean rendered = false;
        if (component == null) {
            log("isColumnHeaderRendered", //NOI18N
                "Cannot determine if column header is rendered, TableColumn is null"); //NOI18N
            return rendered;
        }

        // Test the headerText property for all TableColumn components.
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (isColumnHeaderRendered(col)) {
                    // When header text is found, don't go any further.
                    return true;
                }
            }
        }

        // If either a facet or text are defined, set rendered property.
        UIComponent facet = component.getFacet(COLUMN_HEADER_FACET);            
        if (facet != null && facet.isRendered() 
                || component.getHeaderText() != null) {
            rendered = true;
        }
        return rendered;
    }

    /**
     * Helper method to determine if table is empty.
     * <p>
     * Note: We must determine if column headers are available for all or
     * individual TableRowGroup components. That is, there could be a single
     * column header for all row groups or one for each group. If there is more
     * than one column header, we must test the row count of all groups. If
     * there is only one column header and other groups have more than one row,
     * we want to make sorting available. Thus, sorting is available only there
     * is more than on row for all row groups.
     * </p>
     * @return true if sorting should be available, else false.
     */
    private boolean isEmptyTable() {
        boolean result = false;
        Table table = getTableAncestor();
        TableRowGroup group = getTableRowGroupAncestor();
        if (table != null && group != null) {
            // Get total rows and headers for all TableRowGroup components.
            int rows = table.getRowCount();
            int headers = table.getColumnHeadersCount();
            result = (headers > 1)
                ? !(group.getRowCount() > 1) // Test individual groups.
                : rows == 0 || rows == 1; // No sorting for single row.
        }
        return result;
    }

    /**
     * Helper method to test if table column footers should be rendered.
     * <p>
     * Note: Since headers and footers are optional, we do not render them by 
     * default. However, if any of the properties above are set, they must be
     * set for all columns, including nested columns. Otherwise, we may end up
     * with no header or footer and columns shift left. Alternatively, 
     * developers could add an empty string for each property.
     * </p>
     */
    private boolean isTableColumnFooterRendered() {
        boolean result = false; // Assume no headers or footers are used.
        TableRowGroup group = getTableRowGroupAncestor();
        if (group == null) {
            log("isTableColumnFooterRendered", //NOI18N
                "Cannot determine if table column footer is rendered, TableRowGroup is null"); //NOI18N
            return result;
        }

        // Test the tableFooterText property for all TableColumn components.
        Iterator kids = group.getTableColumnChildren();
        while (kids.hasNext()) {
            TableColumn col = (TableColumn) kids.next();
            if (isTableColumnFooterRendered(col)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Helper method to test the tableFooterText property for nested TableColumn
     * components.
     *
     * @param component TableColumn component to render.
     */
    private boolean isTableColumnFooterRendered(TableColumn component) {
        boolean rendered = false;
        if (component == null) {
            log("isTableColumnFooterRendered", //NOI18N
                "Cannot determine if table column footer is rendered, TableColumn is null"); //NOI18N
            return rendered;
        }

        // Test the tableFooterText property for all TableColumn components.
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (isTableColumnFooterRendered(col)) {
                    // When footer text is found, don't go any further.
                    return true;
                }
            }
        }

        // If either a facet or text are defined, set rendered property.
        UIComponent facet = component.getFacet(TABLE_FOOTER_FACET);            
        if (facet != null && facet.isRendered() 
                || component.getTableFooterText() != null) {
            rendered = true;
        }
        return rendered;
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


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt;</code> HTML element that 
     * is rendered for the column footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    @Property(name="extraFooterHtml", displayName="Extra Footer HTML", category="Advanced")
    private String extraFooterHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt;</code> HTML element that 
     * is rendered for the column footer. Use only code that is valid in an HTML 
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
     * Extra HTML code to be appended to the <code>&lt;td&gt;</code> HTML element that 
     * is rendered for the column footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public void setExtraFooterHtml(String extraFooterHtml) {
        this.extraFooterHtml = extraFooterHtml;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;th&gt;</code> HTML element that 
     * is rendered for the column header. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    @Property(name="extraHeaderHtml", displayName="Extra Header HTML", category="Advanced")
    private String extraHeaderHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;th&gt;</code> HTML element that 
     * is rendered for the column header. Use only code that is valid in an HTML 
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
     * Extra HTML code to be appended to the <code>&lt;th&gt;</code> HTML element that 
     * is rendered for the column header. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public void setExtraHeaderHtml(String extraHeaderHtml) {
        this.extraHeaderHtml = extraHeaderHtml;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt;</code> HTML element that 
     * is rendered for the table column footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    @Property(name="extraTableFooterHtml", displayName="Extra Table Footer HTML", category="Advanced")
    private String extraTableFooterHtml = null;

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt;</code> HTML element that 
     * is rendered for the table column footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public String getExtraTableFooterHtml() {
        if (this.extraTableFooterHtml != null) {
            return this.extraTableFooterHtml;
        }
        ValueExpression _vb = getValueExpression("extraTableFooterHtml");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt;</code> HTML element that 
     * is rendered for the table column footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    public void setExtraTableFooterHtml(String extraTableFooterHtml) {
        this.extraTableFooterHtml = extraTableFooterHtml;
    }


    /**
     * The text to be displayed in the table column footer. The table column footer is 
     * displayed once per table, and is especially useful in tables with multiple 
     * groups of rows.
     */
    @Property(name="tableFooterText", displayName="Table Footer Text", category="Appearance")
    private String tableFooterText = null;

    /**
     * The text to be displayed in the table column footer. The table column footer is 
     * displayed once per table, and is especially useful in tables with multiple 
     * groups of rows.
     */
    public String getTableFooterText() {
        if (this.tableFooterText != null) {
            return this.tableFooterText;
        }
        ValueExpression _vb = getValueExpression("tableFooterText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The text to be displayed in the table column footer. The table column footer is 
     * displayed once per table, and is especially useful in tables with multiple 
     * groups of rows.
     */
    public void setTableFooterText(String tableFooterText) {
        this.tableFooterText = tableFooterText;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.extraFooterHtml = (String) _values[1];
        this.extraHeaderHtml = (String) _values[2];
        this.extraTableFooterHtml = (String) _values[3];
        this.tableFooterText = (String) _values[4];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[5];
        _values[0] = super.saveState(_context);
        _values[1] = this.extraFooterHtml;
        _values[2] = this.extraHeaderHtml;
        _values[3] = this.extraTableFooterHtml;
        _values[4] = this.tableFooterText;
        return _values;
    }
}
