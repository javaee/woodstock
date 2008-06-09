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

import com.sun.faces.annotation.Property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.NamingContainer;

import javax.faces.component.UIComponent;

/**
 * Base class for table components.
 */
public abstract class TableBase extends WebuiComponent
        implements NamingContainer {
    /** The facet name for top actions area. */
    public static final String ACTIONS_TOP_FACET = "actionsTop"; //NOI18N
    /** The facet name for the filter area. */
    public static final String FILTER_FACET = "filter"; //NOI18N
    /** The facet name for the filter panel. */
    public static final String FILTER_PANEL_FACET = "filterPanel"; //NOI18N
    /** The facet name for the preferences panel. */
    public static final String PREFERENCES_PANEL_FACET = "preferencesPanel"; //NOI18N
     /** The facet name for the sort panel. */
    public static final String SORT_PANEL_FACET = "sortPanel"; //NOI18N
    // A List containing Table2RowGroup children. 
    private List tableRowGroupChildren = null;

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
        tableRowGroupChildren = null;

        // Clear properties of TableRowGroupBase children.
        Iterator kids = getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroupBase kid = (TableRowGroupBase) kids.next();
            kid.clear(); // Clear cached properties.
        }
    }

    /** 
     * Get an Iterator over the TableRowGroupBase children found for this
     * component. 
     * 
     * @return An Iterator over the TableRowGroupBase children. 
     */ 
    public Iterator getTableRowGroupChildren() { 
        // Get children. 
        if (tableRowGroupChildren == null) { 
            tableRowGroupChildren = new ArrayList(); 
            Iterator kids = getChildren().iterator(); 
            while (kids.hasNext()) { 
                UIComponent kid = (UIComponent) kids.next(); 
                if ((kid instanceof TableRowGroupBase)) { 
                    tableRowGroupChildren.add(kid); 
                } 
            } 
        }
        return tableRowGroupChildren.iterator();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>The deprecated ALIGN attribute suggests the horizontal alignment of
     * the table on visual browsers. Possible values are left, right, and
     * center. Browsers generally present left- or right-aligned tables as
     * floating tables, with the content following the TABLE flowing around
     * it. To prevent content from flowing around the table, use <BR
     * CLEAR=all> after the end of the TABLE.
     * </p><p>
     * Since many browsers do not support ALIGN=center with TABLE, authors
     * may wish to place the TABLE within a CENTER element.
     * </p><p>
     * Style sheets provide more flexibility in suggesting table alignment
     * but with less browser support than the ALIGN attribute.</p>
     */
    @Property(name="align", displayName="Table Alignment", category="Appearance", isHidden=true, isAttribute=false)
    private String align = null;

    /**
     * <p>The deprecated ALIGN attribute suggests the horizontal alignment of
     * the table on visual browsers. Possible values are left, right, and
     * center. Browsers generally present left- or right-aligned tables as
     * floating tables, with the content following the TABLE flowing around
     * it. To prevent content from flowing around the table, use <BR
     * CLEAR=all> after the end of the TABLE.
     * </p><p>
     * Since many browsers do not support ALIGN=center with TABLE, authors
     * may wish to place the TABLE within a CENTER element.
     * </p><p>
     * Style sheets provide more flexibility in suggesting table alignment
     * but with less browser support than the ALIGN attribute.</p>
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
     * <p>The deprecated ALIGN attribute suggests the horizontal alignment of
     * the table on visual browsers. Possible values are left, right, and
     * center. Browsers generally present left- or right-aligned tables as
     * floating tables, with the content following the TABLE flowing around
     * it. To prevent content from flowing around the table, use <BR
     * CLEAR=all> after the end of the TABLE.
     * </p><p>
     * Since many browsers do not support ALIGN=center with TABLE, authors
     * may wish to place the TABLE within a CENTER element.
     * </p><p>
     * Style sheets provide more flexibility in suggesting table alignment
     * but with less browser support than the ALIGN attribute.</p>
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * Flag indicating that the table title should be augmented with the range of items 
     * currently displayed and the total number of items in the table. For example, 
     * "(1 - 25 of 200)". If the table is not currently paginated, the title is 
     * augmented with the number of displayed items. For example, "(18)". When set to 
     * false, any values set for <code>itemsText</code> and <code>filterText</code> 
     * are overridden.
     */
    @Property(name="augmentTitle", displayName="Show Augmented Title", category="Appearance")
    private boolean augmentTitle = false;
    private boolean augmentTitle_set = false;

    /**
     * Flag indicating that the table title should be augmented with the range of items 
     * currently displayed and the total number of items in the table. For example, 
     * "(1 - 25 of 200)". If the table is not currently paginated, the title is 
     * augmented with the number of displayed items. For example, "(18)". When set to 
     * false, any values set for <code>itemsText</code> and <code>filterText</code> 
     * are overridden.
     */
    public boolean isAugmentTitle() {
        if (this.augmentTitle_set) {
            return this.augmentTitle;
        }
        ValueExpression _vb = getValueExpression("augmentTitle");
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
     * Flag indicating that the table title should be augmented with the range of items 
     * currently displayed and the total number of items in the table. For example, 
     * "(1 - 25 of 200)". If the table is not currently paginated, the title is 
     * augmented with the number of displayed items. For example, "(18)". When set to 
     * false, any values set for <code>itemsText</code> and <code>filterText</code> 
     * are overridden.
     */
    public void setAugmentTitle(boolean augmentTitle) {
        this.augmentTitle = augmentTitle;
        this.augmentTitle_set = true;
    }

    /**
     * The deprecated BGCOLOR attribute suggests a background color for the
     * table. The combination of this attribute with <FONT COLOR=...> can
     * leave invisible or unreadable text on Netscape Navigator 2.x, which
     * does not support BGCOLOR on table elements. BGCOLOR is dangerous even
     * on supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color.
     */
    @Property(name="bgColor", displayName="Table Background Color", category="Appearance", isHidden=true, isAttribute=false)
    private String bgColor = null;

    /**
     * The deprecated BGCOLOR attribute suggests a background color for the
     * table. The combination of this attribute with <FONT COLOR=...> can
     * leave invisible or unreadable text on Netscape Navigator 2.x, which
     * does not support BGCOLOR on table elements. BGCOLOR is dangerous even
     * on supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color.
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
     * The deprecated BGCOLOR attribute suggests a background color for the
     * table. The combination of this attribute with <FONT COLOR=...> can
     * leave invisible or unreadable text on Netscape Navigator 2.x, which
     * does not support BGCOLOR on table elements. BGCOLOR is dangerous even
     * on supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color.
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * The BORDER attribute specifies the width in pixels of the border around a table.
     */
    @Property(name="border", displayName="Border Width", category="Appearance", editorClassName="com.sun.rave.propertyeditors.LengthPropertyEditor", isHidden=true, isAttribute=false)
    private int border = Integer.MIN_VALUE;
    private boolean border_set = false;

    /**
     * The BORDER attribute specifies the width in pixels of the border around a table.
     */
    public int getBorder() {
        if (this.border_set) {
            return this.border;
        }
        ValueExpression _vb = getValueExpression("border");
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
     * The BORDER attribute specifies the width in pixels of the border around a table.
     */
    public void setBorder(int border) {
        this.border = border;
        this.border_set = true;
    }

    /**
     * <p>The amount of whitespace that should be placed between the cell contents and the 
     * cell borders, on all four sides of the cell. The default value is 0, which 
     * causes a default amount of space to be used.
     * </p><p>
     * All browsers support specifying the number of pixels to use for cell padding, so 
     * you should specify a number of pixels to achieve consistency across platforms. 
     * Some browsers also support specifying the cell padding as a percentage of the 
     * space available for padding, and the calculated space is split evenly between 
     * the sides. Most browsers that do not support percentages treat 
     * <code>cellpadding="20%"</code> as if it were <code>cellpadding="20"</code>.</p>
     */
    @Property(name="cellPadding", displayName="Spacing Within Cells", category="Appearance")
    private String cellPadding = null;

    /**
     * <p>The amount of whitespace that should be placed between the cell contents and the 
     * cell borders, on all four sides of the cell. The default value is 0, which 
     * causes a default amount of space to be used.
     * </p><p>
     * All browsers support specifying the number of pixels to use for cell padding, so 
     * you should specify a number of pixels to achieve consistency across platforms. 
     * Some browsers also support specifying the cell padding as a percentage of the 
     * space available for padding, and the calculated space is split evenly between 
     * the sides. Most browsers that do not support percentages treat 
     * <code>cellpadding="20%"</code> as if it were <code>cellpadding="20"</code>.</p>
     */
    public String getCellPadding() {
        if (this.cellPadding != null) {
            return this.cellPadding;
        }
        ValueExpression _vb = getValueExpression("cellPadding");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The amount of whitespace that should be placed between the cell contents and the 
     * cell borders, on all four sides of the cell. The default value is 0, which 
     * causes a default amount of space to be used.
     * </p><p>
     * All browsers support specifying the number of pixels to use for cell padding, so 
     * you should specify a number of pixels to achieve consistency across platforms. 
     * Some browsers also support specifying the cell padding as a percentage of the 
     * space available for padding, and the calculated space is split evenly between 
     * the sides. Most browsers that do not support percentages treat 
     * <code>cellpadding="20%"</code> as if it were <code>cellpadding="20"</code>.</p>
     */
    public void setCellPadding(String cellPadding) {
        this.cellPadding = cellPadding;
    }

    /**
     * <p>The amount of whitespace that should be placed between cells, and between the 
     * edges of the table content area and the sides of the table. The default 
     * value is 0, which causes a default amount of space to be used.
     * </p><p>
     * All browsers support specifying the number of pixels to use for cell spacing, so 
     * you should specify a number of pixels to achieve consistency across platforms. 
     * Some browsers also support specifying the cell spacing as a percentage of the 
     * space available for spacing, and the calculated space is split evenly between 
     * the sides. Most browsers that do not support percentages treat 
     * <code>cellspacing="20%"</code> as if it were <code>cellspacing="20"</code>.</p>
     */
    @Property(name="cellSpacing", displayName="Spacing Between Cells", category="Appearance")
    private String cellSpacing = null;

    /**
     * <p>The amount of whitespace that should be placed between cells, and between the 
     * edges of the table content area and the sides of the table. The default 
     * value is 0, which causes a default amount of space to be used.
     * </p><p>
     * All browsers support specifying the number of pixels to use for cell spacing, so 
     * you should specify a number of pixels to achieve consistency across platforms. 
     * Some browsers also support specifying the cell spacing as a percentage of the 
     * space available for spacing, and the calculated space is split evenly between 
     * the sides. Most browsers that do not support percentages treat 
     * <code>cellspacing="20%"</code> as if it were <code>cellspacing="20"</code>.</p>
     */
    public String getCellSpacing() {
        if (this.cellSpacing != null) {
            return this.cellSpacing;
        }
        ValueExpression _vb = getValueExpression("cellSpacing");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The amount of whitespace that should be placed between cells, and between the 
     * edges of the table content area and the sides of the table. The default 
     * value is 0, which causes a default amount of space to be used.
     * </p><p>
     * All browsers support specifying the number of pixels to use for cell spacing, so 
     * you should specify a number of pixels to achieve consistency across platforms. 
     * Some browsers also support specifying the cell spacing as a percentage of the 
     * space available for spacing, and the calculated space is split evenly between 
     * the sides. Most browsers that do not support percentages treat 
     * <code>cellspacing="20%"</code> as if it were <code>cellspacing="20"</code>.</p>
     */
    public void setCellSpacing(String cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    /**
     * In the View-Changing Controls area of the Action Bar, display a button that 
     * clears any sorting of the table. When the button is clicked, the table items 
     * return to the order they were in when the page was initially rendered.
     */
    @Property(name="clearSortButton", displayName="Show Clear Sort Button", category="Appearance")
    private boolean clearSortButton = false;
    private boolean clearSortButton_set = false;

    /**
     * In the View-Changing Controls area of the Action Bar, display a button that 
     * clears any sorting of the table. When the button is clicked, the table items 
     * return to the order they were in when the page was initially rendered.
     */
    public boolean isClearSortButton() {
        if (this.clearSortButton_set) {
            return this.clearSortButton;
        }
        ValueExpression _vb = getValueExpression("clearSortButton");
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
     * In the View-Changing Controls area of the Action Bar, display a button that 
     * clears any sorting of the table. When the button is clicked, the table items 
     * return to the order they were in when the page was initially rendered.
     */
    public void setClearSortButton(boolean clearSortButton) {
        this.clearSortButton = clearSortButton;
        this.clearSortButton_set = true;
    }

    /**
     * In the Action Bar, display a deselect button for tables in which multiple rows 
     * can be selected, to allow users to deselect all table rows that are currently 
     * displayed. This button is used to deselect a column of checkboxes using the id 
     * that was given to the selectId attribute of the <code>webuijsf:tableColumn</code>
     * and <code>webuijsf:table2Column</code> tags.
     */
    @Property(name="deselectMultipleButton", displayName="Show Deselect Multiple Button", category="Appearance")
    private boolean deselectMultipleButton = false;
    private boolean deselectMultipleButton_set = false;

    /**
     * In the Action Bar, display a deselect button for tables in which multiple rows 
     * can be selected, to allow users to deselect all table rows that are currently 
     * displayed. This button is used to deselect a column of checkboxes using the id 
     * that was given to the selectId attribute of the <code>webuijsf:tableColumn</code>
     * and <code>webuijsf:table2Column</code> tags.
     */
    public boolean isDeselectMultipleButton() {
        if (this.deselectMultipleButton_set) {
            return this.deselectMultipleButton;
        }
        ValueExpression _vb = getValueExpression("deselectMultipleButton");
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
     * In the Action Bar, display a deselect button for tables in which multiple rows 
     * can be selected, to allow users to deselect all table rows that are currently 
     * displayed. This button is used to deselect a column of checkboxes using the id 
     * that was given to the selectId attribute of the <code>webuijsf:tableColumn</code>
     * and <code>webuijsf:table2Column</code> tags.
     */
    public void setDeselectMultipleButton(boolean deselectMultipleButton) {
        this.deselectMultipleButton = deselectMultipleButton;
        this.deselectMultipleButton_set = true;
    }

    /**
     * Scripting code that is executed when the user clicks the deselect multiple 
     * button. You should use the JavaScript <code>setTimeout()</code> function to 
     * invoke the script to ensure that checkboxes are deselected immediately, instead 
     * of waiting for the script to complete.
     */
    @Property(name="deselectMultipleButtonOnClick", displayName="Deselect Multiple Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String deselectMultipleButtonOnClick = null;   

    /**
     * Scripting code that is executed when the user clicks the deselect multiple 
     * button. You should use the JavaScript <code>setTimeout()</code> function to 
     * invoke the script to ensure that checkboxes are deselected immediately, instead 
     * of waiting for the script to complete.
     */
    public String getDeselectMultipleButtonOnClick() {
        if (this.deselectMultipleButtonOnClick != null) {
            return this.deselectMultipleButtonOnClick;
        }
        ValueExpression _vb = getValueExpression("deselectMultipleButtonOnClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code that is executed when the user clicks the deselect multiple 
     * button. You should use the JavaScript <code>setTimeout()</code> function to 
     * invoke the script to ensure that checkboxes are deselected immediately, instead 
     * of waiting for the script to complete.
     */
    public void setDeselectMultipleButtonOnClick(String deselectMultipleButtonOnClick) {
        this.deselectMultipleButtonOnClick = deselectMultipleButtonOnClick;
    }

    /**
     * In the Action Bar, display a deselect button for tables in which only a single 
     * table row can be selected at a time. This button is used to deselect a column of 
     * radio buttons using the id that was given to the selectId attribute of the 
     * <code>webuijsf:tableColumn</code> and <code>webuijsf:table2Column</code> tags.
     */
    @Property(name="deselectSingleButton", displayName="Show Deselect Single Button", category="Appearance")
    private boolean deselectSingleButton = false;
    private boolean deselectSingleButton_set = false;

    /**
     * In the Action Bar, display a deselect button for tables in which only a single 
     * table row can be selected at a time. This button is used to deselect a column of 
     * radio buttons using the id that was given to the selectId attribute of the 
     * <code>webuijsf:tableColumn</code> and <code>webuijsf:table2Column</code> tags.
     */
    public boolean isDeselectSingleButton() {
        if (this.deselectSingleButton_set) {
            return this.deselectSingleButton;
        }
        ValueExpression _vb = getValueExpression("deselectSingleButton");
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
     * In the Action Bar, display a deselect button for tables in which only a single 
     * table row can be selected at a time. This button is used to deselect a column of 
     * radio buttons using the id that was given to the selectId attribute of the 
     * <code>webuijsf:tableColumn</code> and <code>webuijsf:table2Column</code> tags.
     */
    public void setDeselectSingleButton(boolean deselectSingleButton) {
        this.deselectSingleButton = deselectSingleButton;
        this.deselectSingleButton_set = true;
    }

    /**
     * Scripting code that is executed when the user clicks the deselect single button.
     * You should use the JavaScript <code>setTimeout()</code> function to invoke the 
     * script to ensure that the radio button is deselected immediately, instead of 
     * waiting for the script to complete.
     */
    @Property(name="deselectSingleButtonOnClick", displayName="Deselect Single Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String deselectSingleButtonOnClick = null;

    /**
     * Scripting code that is executed when the user clicks the deselect single button.
     * You should use the JavaScript <code>setTimeout()</code> function to invoke the 
     * script to ensure that the radio button is deselected immediately, instead of 
     * waiting for the script to complete.
     */
    public String getDeselectSingleButtonOnClick() {
        if (this.deselectSingleButtonOnClick != null) {
            return this.deselectSingleButtonOnClick;
        }
        ValueExpression _vb = getValueExpression("deselectSingleButtonOnClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code that is executed when the user clicks the deselect single button.
     * You should use the JavaScript <code>setTimeout()</code> function to invoke the 
     * script to ensure that the radio button is deselected immediately, instead of 
     * waiting for the script to complete.
     */
    public void setDeselectSingleButtonOnClick(String deselectSingleButtonOnClick) {
        this.deselectSingleButtonOnClick = deselectSingleButtonOnClick;
    }

    /**
     * The element id to be applied to the outermost HTML element that is rendered 
     * for the dropDown component used to display filter options. The id must be 
     * fully qualified. This id is required for JavaScript functions to set the 
     * dropDown styles when the embedded filter panel is opened, and to reset the 
     * default selected value when the panel is closed. Note that if you use the 
     * <code>webuijsf:dropDown</code> tag as the only component in the <code>filter</code> 
     * facet, the <code>filterId</code> is optional. If you use a custom component, or 
     * use the <code>webuijsf:dropDown</code> as a child component, you must specify a 
     * filterID.
     */
    @Property(name="filterId", displayName="Filter Component Id", category="Appearance", isHidden=true)
    private String filterId = null;

    /**
     * The element id to be applied to the outermost HTML element that is rendered 
     * for the dropDown component used to display filter options. The id must be 
     * fully qualified. This id is required for JavaScript functions to set the 
     * dropDown styles when the embedded filter panel is opened, and to reset the 
     * default selected value when the panel is closed. Note that if you use the 
     * <code>webuijsf:dropDown</code> tag as the only component in the <code>filter</code> 
     * facet, the <code>filterId</code> is optional. If you use a custom component, or 
     * use the <code>webuijsf:dropDown</code> as a child component, you must specify a 
     * filterID.
     */
    public String getFilterId() {
        if (this.filterId != null) {
            return this.filterId;
        }
        ValueExpression _vb = getValueExpression("filterId");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The element id to be applied to the outermost HTML element that is rendered 
     * for the dropDown component used to display filter options. The id must be 
     * fully qualified. This id is required for JavaScript functions to set the 
     * dropDown styles when the embedded filter panel is opened, and to reset the 
     * default selected value when the panel is closed. Note that if you use the 
     * <code>webuijsf:dropDown</code> tag as the only component in the <code>filter</code> 
     * facet, the <code>filterId</code> is optional. If you use a custom component, or 
     * use the <code>webuijsf:dropDown</code> as a child component, you must specify a 
     * filterID.
     */
    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    /**
     * The element id used to set focus when the filter panel is open.
     */
    @Property(name="filterPanelFocusId", displayName="Filter Panel Focus ID", category="Advanced", isHidden=true)
    private String filterPanelFocusId = null;

    /**
     * The element id used to set focus when the filter panel is open.
     */
    public String getFilterPanelFocusId() {
        if (this.filterPanelFocusId != null) {
            return this.filterPanelFocusId;
        }
        ValueExpression _vb = getValueExpression("filterPanelFocusId");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The element id used to set focus when the filter panel is open.
     */
    public void setFilterPanelFocusId(String filterPanelFocusId) {
        this.filterPanelFocusId = filterPanelFocusId;
    }

    /**
     * Text to be inserted into the table title bar when a filter is applied. This text 
     * is expected to be the name of the filter that the user has selected. The 
     * attribute value should be a JavaServer Faces EL expression that resolves to a 
     * backing bean property whose value is set in your filter code. The value of the 
     * filterText attribute is inserted into the table title, as follows: Your Table's 
     * Title <span style="font-style: italic;">filterText</span> Filter Applied.
     */
    @Property(name="filterText", displayName="Filter Text", category="Appearance", isHidden=true)
    private String filterText = null;

    /**
     * Text to be inserted into the table title bar when a filter is applied. This text 
     * is expected to be the name of the filter that the user has selected. The 
     * attribute value should be a JavaServer Faces EL expression that resolves to a 
     * backing bean property whose value is set in your filter code. The value of the 
     * filterText attribute is inserted into the table title, as follows: Your Table's 
     * Title <span style="font-style: italic;">filterText</span> Filter Applied.
     */
    public String getFilterText() {
        if (this.filterText != null) {
            return this.filterText;
        }
        ValueExpression _vb = getValueExpression("filterText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Text to be inserted into the table title bar when a filter is applied. This text 
     * is expected to be the name of the filter that the user has selected. The 
     * attribute value should be a JavaServer Faces EL expression that resolves to a 
     * backing bean property whose value is set in your filter code. The value of the 
     * filterText attribute is inserted into the table title, as follows: Your Table's 
     * Title <span style="font-style: italic;">filterText</span> Filter Applied.
     */
    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    /**
     * The BORDER attribute specifies the width in pixels of the border
     * around a table.
     */
    @Property(name="frame", displayName="Outer Border", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor", isHidden=true, isAttribute=false)
    private String frame = null;

    /**
     * The BORDER attribute specifies the width in pixels of the border
     * around a table.
     */
    public String getFrame() {
        if (this.frame != null) {
            return this.frame;
        }
        ValueExpression _vb = getValueExpression("frame");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The BORDER attribute specifies the width in pixels of the border
     * around a table.
     */
    public void setFrame(String frame) {
        this.frame = frame;
    }

    /**
     * Flag indicating that selected rows might be currently hidden from view. UI 
     * guidelines recommend that rows that are not in view are deselected. For example, 
     * when users select rows of the table and navigate to another page, the selected 
     * rows should be deselected automatically. Or, when a user applies a filter or 
     * sort that hides previously selected rows from view, those selected rows should 
     * be deselected. By deselecting hidden rows, you prevent the user from 
     * inadvertantly invoking an action on rows that are not displayed.
     * </p><p>
     * However, sometimes state must be maintained aross table pages. If your table 
     * must maintain state, you must set the hiddenSelectedRows attribute to true. The 
     * attribute causes text to be displayed in the table title and footer to indicate 
     * the number of selected rows that are currently hidden from view. This title and 
     * footer text is also displayed with a count of 0 when there are no hidden 
     * selections, to make the user aware of the possibility of hidden selections.
     * </p><p>
     * Note: When hiddenSelectedRows is false, the descending sort button for the 
     * select column is disabled when the table is paginated. Disabling this button 
     * prevents a sort from placing selected rows on a page other than the current 
     * page.</p>
     */
    @Property(name="hiddenSelectedRows", displayName="Is Hidden Selected Rows", category="Advanced") 
    private boolean hiddenSelectedRows = false;
    private boolean hiddenSelectedRows_set = false;

    /**
     * Flag indicating that selected rows might be currently hidden from view. UI 
     * guidelines recommend that rows that are not in view are deselected. For example, 
     * when users select rows of the table and navigate to another page, the selected 
     * rows should be deselected automatically. Or, when a user applies a filter or 
     * sort that hides previously selected rows from view, those selected rows should 
     * be deselected. By deselecting hidden rows, you prevent the user from 
     * inadvertantly invoking an action on rows that are not displayed.
     * </p><p>
     * However, sometimes state must be maintained aross table pages. If your table 
     * must maintain state, you must set the hiddenSelectedRows attribute to true. The 
     * attribute causes text to be displayed in the table title and footer to indicate 
     * the number of selected rows that are currently hidden from view. This title and 
     * footer text is also displayed with a count of 0 when there are no hidden 
     * selections, to make the user aware of the possibility of hidden selections.
     * </p><p>
     * Note: When hiddenSelectedRows is false, the descending sort button for the 
     * select column is disabled when the table is paginated. Disabling this button 
     * prevents a sort from placing selected rows on a page other than the current 
     * page.</p>
     */
    public boolean isHiddenSelectedRows() {
        if (this.hiddenSelectedRows_set) {
            return this.hiddenSelectedRows;
        }
        ValueExpression _vb = getValueExpression("hiddenSelectedRows");
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
     * <p>Flag indicating that selected rows might be currently hidden from view. UI 
     * guidelines recommend that rows that are not in view are deselected. For example, 
     * when users select rows of the table and navigate to another page, the selected 
     * rows should be deselected automatically. Or, when a user applies a filter or 
     * sort that hides previously selected rows from view, those selected rows should 
     * be deselected. By deselecting hidden rows, you prevent the user from 
     * inadvertantly invoking an action on rows that are not displayed.
     * </p><p>
     * However, sometimes state must be maintained aross table pages. If your table 
     * must maintain state, you must set the hiddenSelectedRows attribute to true. The 
     * attribute causes text to be displayed in the table title and footer to indicate 
     * the number of selected rows that are currently hidden from view. This title and 
     * footer text is also displayed with a count of 0 when there are no hidden 
     * selections, to make the user aware of the possibility of hidden selections.
     * </p><p>
     * Note: When hiddenSelectedRows is false, the descending sort button for the 
     * select column is disabled when the table is paginated. Disabling this button 
     * prevents a sort from placing selected rows on a page other than the current 
     * page.</p>
     */
    public void setHiddenSelectedRows(boolean hiddenSelectedRows) {
        this.hiddenSelectedRows = hiddenSelectedRows;
        this.hiddenSelectedRows_set = true;
    }

    /**
     * Text to add to the title of an unpaginated table. For example, if your table 
     * title is "Critical" and there are 20 items in the table, the default unpaginated 
     * table title would be Critical (20). If you specify itemsText="alerts", the title 
     * would be Critical (20 alerts).
     */
    @Property(name="itemsText", displayName="Items Text", category="Appearance")
    private String itemsText = null;

    /**
     * Text to add to the title of an unpaginated table. For example, if your table 
     * title is "Critical" and there are 20 items in the table, the default unpaginated 
     * table title would be Critical (20). If you specify itemsText="alerts", the title 
     * would be Critical (20 alerts).
     */
    public String getItemsText() {
        if (this.itemsText != null) {
            return this.itemsText;
        }
        ValueExpression _vb = getValueExpression("itemsText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Text to add to the title of an unpaginated table. For example, if your table 
     * title is "Critical" and there are 20 items in the table, the default unpaginated 
     * table title would be Critical (20). If you specify itemsText="alerts", the title 
     * would be Critical (20 alerts).
     */
    public void setItemsText(String itemsText) {
        this.itemsText = itemsText;
    }

    /**
     * Renders the table in a style that makes the table look lighter weight, generally 
     * by omitting the shading around the table and in the title bar.
     */
    @Property(name="lite", displayName="Light Weight Table", category="Appearance")
    private boolean lite = false;
    private boolean lite_set = false;

    /**
     * Renders the table in a style that makes the table look lighter weight, generally 
     * by omitting the shading around the table and in the title bar.
     */
    public boolean isLite() {
        if (this.lite_set) {
            return this.lite;
        }
        ValueExpression _vb = getValueExpression("lite");
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
     * Renders the table in a style that makes the table look lighter weight, generally 
     * by omitting the shading around the table and in the title bar.
     */
    public void setLite(boolean lite) {
        this.lite = lite;
        this.lite_set = true;
    }

    /**
     * Scripting code executed when a mouse click
     * occurs over this component.
     */
    @Property(name="onClick", displayName="Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onDblClick", displayName="Double Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onKeyDown", displayName="Key Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onKeyPress", displayName="Key Press Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onKeyUp", displayName="Key Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseDown", displayName="Mouse Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseMove", displayName="Mouse Move Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseOut", displayName="Mouse Out Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseOver", displayName="Mouse In Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
    @Property(name="onMouseUp", displayName="Mouse Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
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
     * Show table paginate button to allow users to switch between viewing all data on 
     * a single page (unpaginated) or to see data in multiple pages (paginated).
     */
    @Property(name="paginateButton", displayName="Show Paginate Button", category="Appearance")
    private boolean paginateButton = false;
    private boolean paginateButton_set = false;

    /**
     * Show table paginate button to allow users to switch between viewing all data on 
     * a single page (unpaginated) or to see data in multiple pages (paginated).
     */
    public boolean isPaginateButton() {
        if (this.paginateButton_set) {
            return this.paginateButton;
        }
        ValueExpression _vb = getValueExpression("paginateButton");
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
     * Show table paginate button to allow users to switch between viewing all data on 
     * a single page (unpaginated) or to see data in multiple pages (paginated).
     */
    public void setPaginateButton(boolean paginateButton) {
        this.paginateButton = paginateButton;
        this.paginateButton_set = true;
    }    

    /**
     * The element id used to set focus when the preferences panel is open.
     */
    @Property(name="preferencesPanelFocusId", displayName="Preferences Panel Focus ID", category="Advanced", isHidden=true)
    private String preferencesPanelFocusId = null;

    /**
     * The element id used to set focus when the preferences panel is open.
     */
    public String getPreferencesPanelFocusId() {
        if (this.preferencesPanelFocusId != null) {
            return this.preferencesPanelFocusId;
        }
        ValueExpression _vb = getValueExpression("preferencesPanelFocusId");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The element id used to set focus when the preferences panel is open.
     */
    public void setPreferencesPanelFocusId(String preferencesPanelFocusId) {
        this.preferencesPanelFocusId = preferencesPanelFocusId;
    }

    /**
     * The RULES attribute, poorly supported by browsers, specifies the
     * borders between table cells. Possible values are none for no inner
     * borders, groups for borders between row groups and column groups only,
     * rows for borders between rows only, cols for borders between columns
     * only, and all for borders between all cells. None is the default value
     * if BORDER=0 is used or if no BORDER attribute is given. All is the
     * default value for any other use of BORDER.
     */
    @Property(name="rules", displayName="Inner Borders", category="Appearance", isHidden=true, isAttribute=false)
    private String rules = null;

    /**
     * The RULES attribute, poorly supported by browsers, specifies the
     * borders between table cells. Possible values are none for no inner
     * borders, groups for borders between row groups and column groups only,
     * rows for borders between rows only, cols for borders between columns
     * only, and all for borders between all cells. None is the default value
     * if BORDER=0 is used or if no BORDER attribute is given. All is the
     * default value for any other use of BORDER.
     */
    public String getRules() {
        if (this.rules != null) {
            return this.rules;
        }
        ValueExpression _vb = getValueExpression("rules");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The RULES attribute, poorly supported by browsers, specifies the
     * borders between table cells. Possible values are none for no inner
     * borders, groups for borders between row groups and column groups only,
     * rows for borders between rows only, cols for borders between columns
     * only, and all for borders between all cells. None is the default value
     * if BORDER=0 is used or if no BORDER attribute is given. All is the
     * default value for any other use of BORDER.
     */
    public void setRules(String rules) {
        this.rules = rules;
    }

    /**
     * Show the button that is used for selecting multiple rows. The button is 
     * displayed in the Action Bar (top), and allows users to select all rows currently 
     * displayed. The button selects a column of checkboxes using the id specified in 
     * the selectId attribute of the <code>webuijsf:tableColumn</code>
     * and <code>webuijsf:table2Column</code> tags.
     */
    @Property(name="selectMultipleButton", displayName="Show Select Multiple Button", category="Appearance")
    private boolean selectMultipleButton = false;
    private boolean selectMultipleButton_set = false;

    /**
     * Show the button that is used for selecting multiple rows. The button is 
     * displayed in the Action Bar (top), and allows users to select all rows currently 
     * displayed. The button selects a column of checkboxes using the id specified in 
     * the selectId attribute of the <code>webuijsf:tableColumn</code>
     * and <code>webuijsf:table2Column</code> tags.
     */
    public boolean isSelectMultipleButton() {
        if (this.selectMultipleButton_set) {
            return this.selectMultipleButton;
        }
        ValueExpression _vb = getValueExpression("selectMultipleButton");
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
     * Show the button that is used for selecting multiple rows. The button is 
     * displayed in the Action Bar (top), and allows users to select all rows currently 
     * displayed. The button selects a column of checkboxes using the id specified in 
     * the selectId attribute of the <code>webuijsf:tableColumn</code>
     * and <code>webuijsf:table2Column</code> tags.
     */
    public void setSelectMultipleButton(boolean selectMultipleButton) {
        this.selectMultipleButton = selectMultipleButton;
        this.selectMultipleButton_set = true;
    }

    /**
     * Scripting code executed when the user clicks the mouse on the select multiple 
     * button.
     */
    @Property(name="selectMultipleButtonOnClick", displayName="Select Multiple Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String selectMultipleButtonOnClick = null;

    /**
     * Scripting code executed when the user clicks the mouse on the select multiple 
     * button.
     */
    public String getSelectMultipleButtonOnClick() {
        if (this.selectMultipleButtonOnClick != null) {
            return this.selectMultipleButtonOnClick;
        }
        ValueExpression _vb = getValueExpression("selectMultipleButtonOnClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the user clicks the mouse on the select multiple 
     * button.
     */
    public void setSelectMultipleButtonOnClick(String selectMultipleButtonOnClick) {
        this.selectMultipleButtonOnClick = selectMultipleButtonOnClick;
    }

    /**
     * The element id used to set focus when the sort panel is open.
     */
    @Property(name="sortPanelFocusId", displayName="Sort Panel Focus ID", category="Advanced", isHidden=true)
    private String sortPanelFocusId = null;

    /**
     * The element id used to set focus when the sort panel is open.
     */
    public String getSortPanelFocusId() {
        if (this.sortPanelFocusId != null) {
            return this.sortPanelFocusId;
        }
        ValueExpression _vb = getValueExpression("sortPanelFocusId");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The element id used to set focus when the sort panel is open.
     */
    public void setSortPanelFocusId(String sortPanelFocusId) {
        this.sortPanelFocusId = sortPanelFocusId;
    }

    /**
     * Show the button that is used to open and close the sort panel.
     */
    @Property(name="sortPanelToggleButton", displayName="Show Sort Panel Toggle Button", category="Appearance")
    private boolean sortPanelToggleButton = false;
    private boolean sortPanelToggleButton_set = false;

    /**
     * Show the button that is used to open and close the sort panel.
     */
    public boolean isSortPanelToggleButton() {
        if (this.sortPanelToggleButton_set) {
            return this.sortPanelToggleButton;
        }
        ValueExpression _vb = getValueExpression("sortPanelToggleButton");
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
     * Show the button that is used to open and close the sort panel.
     */
    public void setSortPanelToggleButton(boolean sortPanelToggleButton) {
        this.sortPanelToggleButton = sortPanelToggleButton;
        this.sortPanelToggleButton_set = true;
    }

    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
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
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
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
     * Text that describes this table's purpose and structure, for user agents 
     * rendering to non-visual media such as speech and Braille.
     */
    @Property(name="summary", displayName="Purpose of Table", category="Appearance")
    private String summary = null;

    /**
     * Text that describes this table's purpose and structure, for user agents 
     * rendering to non-visual media such as speech and Braille.
     */
    public String getSummary() {
        if (this.summary != null) {
            return this.summary;
        }
        ValueExpression _vb = getValueExpression("summary");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Text that describes this table's purpose and structure, for user agents 
     * rendering to non-visual media such as speech and Braille.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
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
     * Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }

    /**
     * The text displayed for the table title.
     */
    @Property(name="title", displayName="Table Title", category="Appearance")
    private String title = null;

    /**
     * The text displayed for the table title.
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        ValueExpression _vb = getValueExpression("title");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The text displayed for the table title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
     */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
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
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
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
     * Use the <code>width</code> attribute to specify the width of the table. The 
     * width can be specified as the number of pixels or the percentage of the page 
     * width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    @Property(name="width", displayName="Table Width", category="Appearance")
    private String width = null;

    /**
     * Use the <code>width</code> attribute to specify the width of the table. The 
     * width can be specified as the number of pixels or the percentage of the page 
     * width, and is especially useful for spacer columns. This attribute is 
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
     * Use the <code>width</code> attribute to specify the width of the table. The 
     * width can be specified as the number of pixels or the percentage of the page 
     * width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    public void setWidth(String width) {
        this.width = width;
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
        this.align = (String) _values[1];
        this.augmentTitle = ((Boolean) _values[2]).booleanValue();
        this.augmentTitle_set = ((Boolean) _values[3]).booleanValue();
        this.bgColor = (String) _values[4];
        this.border = ((Integer) _values[5]).intValue();
        this.border_set = ((Boolean) _values[6]).booleanValue();
        this.cellPadding = (String) _values[7];
        this.cellSpacing = (String) _values[8];
        this.clearSortButton = ((Boolean) _values[9]).booleanValue();
        this.clearSortButton_set = ((Boolean) _values[10]).booleanValue();
        this.deselectMultipleButton = ((Boolean) _values[11]).booleanValue();
        this.deselectMultipleButton_set = ((Boolean) _values[12]).booleanValue();
        this.deselectMultipleButtonOnClick = (String) _values[13];
        this.deselectSingleButton = ((Boolean) _values[14]).booleanValue();
        this.deselectSingleButton_set = ((Boolean) _values[15]).booleanValue();
        this.deselectSingleButtonOnClick = (String) _values[16];
        this.filterId = (String) _values[17];
        this.filterPanelFocusId = (String) _values[18];
        this.filterText = (String) _values[19];
        this.frame = (String) _values[20];
        this.hiddenSelectedRows = ((Boolean) _values[21]).booleanValue();
        this.hiddenSelectedRows_set = ((Boolean) _values[22]).booleanValue();
        this.itemsText = (String) _values[23];
        this.lite = ((Boolean) _values[24]).booleanValue();
        this.lite_set = ((Boolean) _values[25]).booleanValue();
        this.onClick = (String) _values[26];
        this.onDblClick = (String) _values[27];
        this.onKeyDown = (String) _values[28];
        this.onKeyPress = (String) _values[29];
        this.onKeyUp = (String) _values[30];
        this.onMouseDown = (String) _values[31];
        this.onMouseMove = (String) _values[32];
        this.onMouseOut = (String) _values[33];
        this.onMouseOver = (String) _values[34];
        this.onMouseUp = (String) _values[35];
        this.paginateButton = ((Boolean) _values[36]).booleanValue();
        this.paginateButton_set = ((Boolean) _values[37]).booleanValue();        
        this.preferencesPanelFocusId = (String) _values[38];
        this.rules = (String) _values[39];
        this.selectMultipleButton = ((Boolean) _values[40]).booleanValue();
        this.selectMultipleButton_set = ((Boolean) _values[41]).booleanValue();
        this.selectMultipleButtonOnClick = (String) _values[42];
        this.sortPanelFocusId = (String) _values[43];
        this.sortPanelToggleButton = ((Boolean) _values[44]).booleanValue();
        this.sortPanelToggleButton_set = ((Boolean) _values[45]).booleanValue();
        this.style = (String) _values[46];
        this.styleClass = (String) _values[47];
        this.summary = (String) _values[48];
        this.tabIndex = ((Integer) _values[49]).intValue();
        this.tabIndex_set = ((Boolean) _values[50]).booleanValue();
        this.title = (String) _values[51];
        this.toolTip = (String) _values[52];
        this.visible = ((Boolean) _values[53]).booleanValue();
        this.visible_set = ((Boolean) _values[54]).booleanValue();
        this.width = (String) _values[55];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[56];
        _values[0] = super.saveState(_context);
        _values[1] = this.align;
        _values[2] = this.augmentTitle ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.augmentTitle_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.bgColor;
        _values[5] = new Integer(this.border);
        _values[6] = this.border_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.cellPadding;
        _values[8] = this.cellSpacing;
        _values[9] = this.clearSortButton ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.clearSortButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.deselectMultipleButton ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.deselectMultipleButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[13] = this.deselectMultipleButtonOnClick;
        _values[14] = this.deselectSingleButton ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.deselectSingleButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.deselectSingleButtonOnClick;
        _values[17] = this.filterId;
        _values[18] = this.filterPanelFocusId;
        _values[19] = this.filterText;
        _values[20] = this.frame;
        _values[21] = this.hiddenSelectedRows ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.hiddenSelectedRows_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = this.itemsText;
        _values[24] = this.lite ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = this.lite_set ? Boolean.TRUE : Boolean.FALSE;
        _values[26] = this.onClick;
        _values[27] = this.onDblClick;
        _values[28] = this.onKeyDown;
        _values[29] = this.onKeyPress;
        _values[30] = this.onKeyUp;
        _values[31] = this.onMouseDown;
        _values[32] = this.onMouseMove;
        _values[33] = this.onMouseOut;
        _values[34] = this.onMouseOver;
        _values[35] = this.onMouseUp;
        _values[36] = this.paginateButton ? Boolean.TRUE : Boolean.FALSE;
        _values[37] = this.paginateButton_set ? Boolean.TRUE : Boolean.FALSE;       
        _values[38] = this.preferencesPanelFocusId;
        _values[39] = this.rules;
        _values[40] = this.selectMultipleButton ? Boolean.TRUE : Boolean.FALSE;
        _values[41] = this.selectMultipleButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[42] = this.selectMultipleButtonOnClick;
        _values[43] = this.sortPanelFocusId;
        _values[44] = this.sortPanelToggleButton ? Boolean.TRUE : Boolean.FALSE;
        _values[45] = this.sortPanelToggleButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[46] = this.style;
        _values[47] = this.styleClass;
        _values[48] = this.summary;
        _values[49] = new Integer(this.tabIndex);
        _values[50] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[51] = this.title;
        _values[52] = this.toolTip;
        _values[53] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[54] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[55] = this.width;
        return _values;
    }
}
