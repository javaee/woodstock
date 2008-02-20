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
import com.sun.webui.jsf.event.TableSortActionListener;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

/**
 * Component that represents an embedded panel.
 * <p>
 * Note: Column headers and footers are rendered by TableRowGroupRenderer. Table
 * column footers are rendered by TableRenderer.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.component.TablePanels.level = FINE
 * </pre></p>
 */
@Component(type="com.sun.webui.jsf.TablePanels",
    family="com.sun.webui.jsf.TablePanels", displayName="Panels", isTag=false)
public class TablePanels extends UIComponentBase implements NamingContainer {
    /** The facet name for the filter panel. */
    public static final String FILTER_PANEL_ID = "_filterPanel"; //NOI18N

    /** The facet name for the preferences panel. */
    public static final String PREFERENCES_PANEL_ID = "_preferencesPanel"; //NOI18N

    /** The component id for the primary sort column menu. */
    public static final String PRIMARY_SORT_COLUMN_MENU_ID = "_primarySortColumnMenu"; //NOI18N

    /** The facet name for the primary sort column menu. */
    public static final String PRIMARY_SORT_COLUMN_MENU_FACET = "primarySortColumnMenu"; //NOI18N

    /** The component id for the primary sort column menu label. */
    public static final String PRIMARY_SORT_COLUMN_MENU_LABEL_ID = "_primarySortColumnMenuLabel"; //NOI18N

    /** The facet name for the primary sort column menu label. */
    public static final String PRIMARY_SORT_COLUMN_MENU_LABEL_FACET = "primarySortColumnMenuLabel"; //NOI18N

    /** The component id for the primary sort order menu. */
    public static final String PRIMARY_SORT_ORDER_MENU_ID = "_primarySortOrderMenu"; //NOI18N

    /** The facet name for the primary sort order menu. */
    public static final String PRIMARY_SORT_ORDER_MENU_FACET = "primarySortOrderMenu"; //NOI18N

    /** The component id for the secondary sort column menu. */
    public static final String SECONDARY_SORT_COLUMN_MENU_ID = "_secondarySortColumnMenu"; //NOI18N

    /** The facet name for the secondary sort column menu. */
    public static final String SECONDARY_SORT_COLUMN_MENU_FACET = "secondarySortColumnMenu"; //NOI18N

    /** The component id for the secondary sort column menu label. */
    public static final String SECONDARY_SORT_COLUMN_MENU_LABEL_ID = "_secondarySortColumnMenuLabel"; //NOI18N

    /** The facet name for the secondary sort column menu label. */
    public static final String SECONDARY_SORT_COLUMN_MENU_LABEL_FACET = "secondarySortColumnMenuLabel"; //NOI18N

    /** The component id for the secondary sort order menu. */
    public static final String SECONDARY_SORT_ORDER_MENU_ID = "_secondarySortOrderMenu"; //NOI18N

    /** The facet name for the secondary sort order menu. */
    public static final String SECONDARY_SORT_ORDER_MENU_FACET = "secondarySortOrderMenu"; //NOI18N

    /** The facet name for the sort panel. */
    public static final String SORT_PANEL_ID = "_sortPanel"; //NOI18N

    /** The component id for the sort panel cancel button. */
    public static final String SORT_PANEL_CANCEL_BUTTON_ID = "_sortPanelCancelButton"; //NOI18N

    /** The facet name for the sort panel cancel button. */
    public static final String SORT_PANEL_CANCEL_BUTTON_FACET = "sortPanelCancelButton"; //NOI18N

    /** The component id for the sort panel submit button. */
    public static final String SORT_PANEL_SUBMIT_BUTTON_ID = "_sortPanelSubmitButton"; //NOI18N

    /** The facet name for the sort panel submit button. */
    public static final String SORT_PANEL_SUBMIT_BUTTON_FACET = "sortPanelSubmitButton"; //NOI18N

    /** The component id for the tertiary sort column menu. */
    public static final String TERTIARY_SORT_COLUMN_MENU_ID = "_tertiarySortColumnMenu"; //NOI18N

    /** The facet name for the tertiary sort column menu. */
    public static final String TERTIARY_SORT_COLUMN_MENU_FACET = "tertiarySortColumnMenu"; //NOI18N

    /** The component id for the tertiary sort column menu label. */
    public static final String TERTIARY_SORT_COLUMN_MENU_LABEL_ID = "_tertiarySortColumnMenuLabel"; //NOI18N

    /** The facet name for the tertiary sort column menu label. */
    public static final String TERTIARY_SORT_COLUMN_MENU_LABEL_FACET = "tertiarySortColumnMenuLabel"; //NOI18N

    /** The component id for the tertiary sort order menu. */
    public static final String TERTIARY_SORT_ORDER_MENU_ID = "_tertiarySortOrderMenu"; //NOI18N

    /** The facet name for the tertiary sort order menu. */
    public static final String TERTIARY_SORT_ORDER_MENU_FACET = "tertiarySortOrderMenu"; //NOI18N

    // The Table ancestor enclosing this component.
    private Table table = null;

    /**
     * Use the <code>width</code> attribute to specify the width of the cells of the 
     * column. The width can be specified as the number of pixels or the percentage of 
     * the table width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    @Property(name="width", displayName="Width")
    private String width = null;

    /** Default constructor */
    public TablePanels() {
        super();
        setRendererType("com.sun.webui.jsf.TablePanels");
    }

    /**
     * Return the family for this component.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TablePanels";
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
    // Sort panel methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get primary sort column menu used in the sort panel.
     *
     * @return The primary sort column menu.
     */
    public UIComponent getPrimarySortColumnMenu() {
        UIComponent facet = getFacet(PRIMARY_SORT_COLUMN_MENU_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        DropDown child = new DropDown();
	child.setId(PRIMARY_SORT_COLUMN_MENU_ID);
        child.setItems(getSortColumnMenuOptions());
        child.setSelected(getSelectedSortColumnMenuOption(1));

        // Set JS to initialize the sort column menu.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnChange("document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) + "')._initPrimarySortOrderMenu()"); //NOI18N
        } else {
            log("getPrimarySortColumnMenu", //NOI18N
                "Tab index & onChange not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get primary sort column menu label used in the sort panel.
     *
     * @return The primary sort column menu label.
     */
    public UIComponent getPrimarySortColumnMenuLabel() {
        UIComponent facet = getFacet(PRIMARY_SORT_COLUMN_MENU_LABEL_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Label child = new Label();
        child.setId(PRIMARY_SORT_COLUMN_MENU_LABEL_ID);
        child.setText(getTheme().getMessage("table.panel.primarySortColumn")); //NOI18N
        child.setLabelLevel(2);
        
        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get primary sort order menu used in the sort panel.
     *
     * @return The primary sort order menu.
     */
    public UIComponent getPrimarySortOrderMenu() {
        UIComponent facet = getFacet(PRIMARY_SORT_ORDER_MENU_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Theme theme = getTheme();
        DropDown child = new DropDown();
	child.setId(PRIMARY_SORT_ORDER_MENU_ID);
        child.setItems(getSortOrderMenuOptions());
        child.setSelected(getSelectedSortOrderMenuOption(1));

        // Set JS to initialize the sort order menu.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnChange("document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) + 
                "')._initPrimarySortOrderMenuToolTip()"); //NOI18N
        } else {
            log("getPrimarySortOrderMenu", //NOI18N
                "Tab index & onChange not set, Table is null"); //NOI18N
        }

        // Note: The tooltip is only set here for 508 compliance tools. The 
        // actual tooltip is set dynamically, when the embedded panel is open.
        child.setToolTip(theme.getMessage("table.panel.primarySortOrder", //NOI18N
            new String[] {theme.getMessage("table.sort.augment.undeterminedAscending")})); //NOI18N

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get secondary sort column menu used in the sort panel.
     *
     * @return The secondary sort column menu.
     */
    public UIComponent getSecondarySortColumnMenu() {
        UIComponent facet = getFacet(SECONDARY_SORT_COLUMN_MENU_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        DropDown child = new DropDown();
	child.setId(SECONDARY_SORT_COLUMN_MENU_ID);
        child.setItems(getSortColumnMenuOptions());
        child.setSelected(getSelectedSortColumnMenuOption(2));

        // Set JS to initialize the sort column menu.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnChange("document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) + 
                "')._initSecondarySortOrderMenu()"); //NOI18N
        } else {
            log("getSecondarySortColumnMenu", //NOI18N
                "Tab index & onChange not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get secondary sort column menu label used in the sort panel.
     *
     * @return The secondary sort column menu label.
     */
    public UIComponent getSecondarySortColumnMenuLabel() {
        UIComponent facet = getFacet(SECONDARY_SORT_COLUMN_MENU_LABEL_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Label child = new Label();
        child.setId(SECONDARY_SORT_COLUMN_MENU_LABEL_ID);
        child.setText(getTheme().getMessage("table.panel.secondarySortColumn")); //NOI18N
        child.setLabelLevel(2);
        
        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get secondary sort order menu used in the sort panel.
     *
     * @return The secondary sort order menu.
     */
    public UIComponent getSecondarySortOrderMenu() {
        UIComponent facet = getFacet(SECONDARY_SORT_ORDER_MENU_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Theme theme = getTheme();
        DropDown child = new DropDown();
	child.setId(SECONDARY_SORT_ORDER_MENU_ID);
        child.setItems(getSortOrderMenuOptions());
        child.setSelected(getSelectedSortOrderMenuOption(2));

        // Set JS to initialize the sort order menu.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnChange("document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) + 
                "')._initSecondarySortOrderMenuToolTip()"); //NOI18N
        } else {
            log("getSecondarySortOrderMenu", //NOI18N
                "Tab index & onChange not set, Table is null"); //NOI18N
        }

        // Note: The tooltip is only set here for 508 compliance tools. The 
        // actual tooltip is set dynamically, when the embedded panel is open.
        child.setToolTip(theme.getMessage("table.panel.secondarySortOrder", //NOI18N
            new String[] {theme.getMessage("table.sort.augment.undeterminedAscending")})); //NOI18N

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get sort panel cancel button.
     *
     * @return The sort panel cancel button.
     */
    public UIComponent getSortPanelCancelButton() {
        UIComponent facet = getFacet(SORT_PANEL_CANCEL_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Button child = new Button();
        child.setId(SORT_PANEL_CANCEL_BUTTON_ID);
        child.setMini(true);
        child.setText(getTheme().getMessage("table.panel.cancel")); //NOI18N
        child.setToolTip(getTheme().getMessage("table.panel.cancelChanges")); //NOI18N

        // Set JS to close the sort panel.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnClick("document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) +
                "')._toggleSortPanel(); return false"); //NOI18N
        } else {
            log("getSortPanelCancelButton", //NOI18N
                "Tab index & onClick not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get sort panel submit button.
     *
     * @return The sort panel submit button.
     */
    public UIComponent getSortPanelSubmitButton() {
        UIComponent facet = getFacet(SORT_PANEL_SUBMIT_BUTTON_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Button child = new Button();
        child.setId(SORT_PANEL_SUBMIT_BUTTON_ID);
        child.setMini(true);
        child.setPrimary(true);
        child.setText(getTheme().getMessage("table.panel.submit")); //NOI18N
        child.setToolTip(getTheme().getMessage("table.panel.applyChanges")); //NOI18N
        child.addActionListener(new TableSortActionListener());

        // Set JS to validate user selections.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnClick("return document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) + 
                "')._validateSortPanel()"); //NOI18N
        } else {
            log("getSortPanelSubmitButton", //NOI18N
                "Tab index & onClick not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child);
        return child;
    }

    /**
     * Get tertiary sort column menu used in the sort panel.
     *
     * @return The tertiary sort column menu.
     */
    public UIComponent getTertiarySortColumnMenu() {
        UIComponent facet = getFacet(TERTIARY_SORT_COLUMN_MENU_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        DropDown child = new DropDown();
	child.setId(TERTIARY_SORT_COLUMN_MENU_ID);
        child.setItems(getSortColumnMenuOptions());
        child.setSelected(getSelectedSortColumnMenuOption(3));

        // Set JS to initialize the sort column menu.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnChange("document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) + 
                "')._initTertiarySortOrderMenu()"); //NOI18N
        } else {
            log("getTertiarySortColumnMenu", //NOI18N
                "Tab index & onChange not set, Table is null"); //NOI18N
        }

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get tertiary sort column menu label used in the sort panel.
     *
     * @return The tertiary sort column menu label.
     */
    public UIComponent getTertiarySortColumnMenuLabel() {
        UIComponent facet = getFacet(TERTIARY_SORT_COLUMN_MENU_LABEL_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Label child = new Label();
        child.setId(TERTIARY_SORT_COLUMN_MENU_LABEL_ID);
        child.setText(getTheme().getMessage("table.panel.tertiarySortColumn")); //NOI18N
        child.setLabelLevel(2);

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    /**
     * Get tertiary sort order menu used in the sort panel.
     *
     * @return The tertiary sort order menu.
     */
    public UIComponent getTertiarySortOrderMenu() {
        UIComponent facet = getFacet(TERTIARY_SORT_ORDER_MENU_FACET);
        if (facet != null) {
            return facet;
        }

        // Get child.
        Theme theme = getTheme();
        DropDown child = new DropDown();
	child.setId(TERTIARY_SORT_ORDER_MENU_ID);
        child.setItems(getSortOrderMenuOptions());
        child.setSelected(getSelectedSortOrderMenuOption(3));

        // Set JS to initialize the sort order menu.
        Table table = getTableAncestor();
        if (table != null) {
            child.setTabIndex(table.getTabIndex());
            child.setOnChange("document.getElementById('" + //NOI18N
                table.getClientId(getFacesContext()) + 
                "')._initTertiarySortOrderMenuToolTip()"); //NOI18N
        } else {
            log("getTertiarySortOrderMenu", //NOI18N
                "Tab index & onChange not set, Table is null"); //NOI18N
        }

        // Note: The tooltip is only set here for 508 compliance tools. The 
        // actual tooltip is set dynamically, when the embedded panel is open.
        child.setToolTip(theme.getMessage("table.panel.tertiarySortOrder", //NOI18N
            new String[] {theme.getMessage("table.sort.augment.undeterminedAscending")})); //NOI18N

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
     * Flag indicating this component should also render a filter panel, in addition to
     * the sort and preferences panels. The default renders a sort panel.
     */
    @Property(name="filterPanel", displayName="Is Filter Panel", isAttribute=false)
    private boolean filterPanel = false;
    private boolean filterPanel_set = false;

    /**
     * Flag indicating this component should also render a filter panel, in addition to
     * the sort and preferences panels. The default renders a sort panel.
     */
    public boolean isFilterPanel() {
        if (this.filterPanel_set) {
            return this.filterPanel;
        }
        ValueExpression _vb = getValueExpression("filterPanel");
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
     * Flag indicating this component should also render a filter panel, in addition to
     * the sort and preferences panels. The default renders a sort panel.
     */
    public void setFilterPanel(boolean filterPanel) {
        this.filterPanel = filterPanel;
        this.filterPanel_set = true;
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
    @Property(name="onClick", category="Javascript")
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
     * Flag indicating this component should also render a preferences panel, in 
     * addition to the sort and filter panels. The default renders a sort panel.
     */
    @Property(name="preferencesPanel", displayName="Is Preferences Panel", isAttribute=false)
    private boolean preferencesPanel = false;
    private boolean preferencesPanel_set = false;

    /**
     * Flag indicating this component should also render a preferences panel, in 
     * addition to the sort and filter panels. The default renders a sort panel.
     */
    public boolean isPreferencesPanel() {
        if (this.preferencesPanel_set) {
            return this.preferencesPanel;
        }
        ValueExpression _vb = getValueExpression("preferencesPanel");
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
     * Flag indicating this component should also render a preferences panel, in 
     * addition to the sort and filter panels. The default renders a sort panel.
     */
    public void setPreferencesPanel(boolean preferencesPanel) {
        this.preferencesPanel = preferencesPanel;
        this.preferencesPanel_set = true;
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
     * viewable by the user in the rendered HTML page.
     */
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * Use the visible attribute to indicate whether the component should be 
     * viewable by the user in the rendered HTML page.
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
     * viewable by the user in the rendered HTML page.
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
        this.align = (String) _values[2];
        this.axis = (String) _values[3];
        this.bgColor = (String) _values[4];
        this._char = (String) _values[5];
        this.charOff = (String) _values[6];
        this.colSpan = ((Integer) _values[7]).intValue();
        this.colSpan_set = ((Boolean) _values[8]).booleanValue();
        this.extraHtml = (String) _values[9];
        this.filterPanel = ((Boolean) _values[10]).booleanValue();
        this.filterPanel_set = ((Boolean) _values[11]).booleanValue();
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
        this.preferencesPanel = ((Boolean) _values[26]).booleanValue();
        this.preferencesPanel_set = ((Boolean) _values[27]).booleanValue();
        this.rowSpan = ((Integer) _values[28]).intValue();
        this.rowSpan_set = ((Boolean) _values[29]).booleanValue();
        this.scope = (String) _values[30];
        this.style = (String) _values[31];
        this.styleClass = (String) _values[32];
        this.toolTip = (String) _values[33];
        this.valign = (String) _values[34];
        this.visible = ((Boolean) _values[35]).booleanValue();
        this.visible_set = ((Boolean) _values[36]).booleanValue();
        this.width = (String) _values[37];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[38];
        _values[0] = super.saveState(_context);
        _values[1] = this.abbr;
        _values[2] = this.align;
        _values[3] = this.axis;
        _values[4] = this.bgColor;
        _values[5] = this._char;
        _values[6] = this.charOff;
        _values[7] = new Integer(this.colSpan);
        _values[8] = this.colSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.extraHtml;
        _values[10] = this.filterPanel ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.filterPanel_set ? Boolean.TRUE : Boolean.FALSE;
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
        _values[26] = this.preferencesPanel ? Boolean.TRUE : Boolean.FALSE;
        _values[27] = this.preferencesPanel_set ? Boolean.TRUE : Boolean.FALSE;
        _values[28] = new Integer(this.rowSpan);
        _values[29] = this.rowSpan_set ? Boolean.TRUE : Boolean.FALSE;
        _values[30] = this.scope;
        _values[31] = this.style;
        _values[32] = this.styleClass;
        _values[33] = this.toolTip;
        _values[34] = this.valign;
        _values[35] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[36] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[37] = this.width;
        return _values;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to get selected option value used by the sort column menu
     * of the table sort panel.
     *
     * @param level The sort level.
     * @return The selected menu option value.
     */
    private String getSelectedSortColumnMenuOption(int level) {
        String result = null;
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;

        // Find the column that matches the given sort level and return the
        // SortCriteria key value.
        if (group != null) {
            Iterator kids = group.getTableColumnChildren();
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                result = getSelectedSortColumnMenuOption(col, level);
                if (result != null) {
                    break;
                }
            }
        } else {
            log("getSelectedSortColumnMenuOption", //NOI18N
                "Cannot obtain select sort column menu option, TableRowGroup is null"); //NOI18N
        }
        return result;
    }

    /**
     * Helper method to get selected option value for nested TableColumn 
     * components, used by the sort column menu of the table sort panel.
     *
     * @param level The sort level.
     * @param component The TableColumn component to render.
     * @return The selected menu option value.
     */
    private String getSelectedSortColumnMenuOption(TableColumn component,
            int level) {
        String result = null;
        if (component == null) {
            log("getSelectedSortColumnMenuOption", //NOI18N
                "Cannot obtain select sort column menu option, TableColumn is null"); //NOI18N
            return result;
        }

        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn kid = (TableColumn) kids.next();
                result = getSelectedSortColumnMenuOption(kid, level);
                if (result != null) {
                    return result;
                }
            }
        }

        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;

        // Get SortCriteria.
        if (group != null) {
            SortCriteria criteria = component.getSortCriteria();
            if (criteria != null) {
                // Get initial selected option value.
                int sortLevel = group.getSortLevel(criteria);
                if (sortLevel == level) {
                    result = criteria.getCriteriaKey();
                }
            }
        } else {
            log("getSelectedSortColumnMenuOption", //NOI18N
                "Cannot obtain select sort column menu option, TableRowGroup is null"); //NOI18N
        }
        return result;
    }

    /** 
     * Helper method to get selected option value used by the sort order menu of
     * the table sort panel.
     *
     * @param level The sort level.
     * @return The selected menu option value.
     */
    private String getSelectedSortOrderMenuOption(int level) {
        String result = null;
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;

        // Find the column that matches the given sort level and return the
        // sort order.
        if (group != null) {
            Iterator kids = group.getTableColumnChildren();
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                result = getSelectedSortOrderMenuOption(col, level);
                if (result != null) {
                    break;
                }
            }
        } else {
            log("getSelectedSortOrderMenuOption", //NOI18N
                "Cannot obtain select sort order menu option, TableRowGroup is null"); //NOI18N
        }
        return result;
    }

    /** 
     * Helper method to get selected option value for nested TableColumn 
     * components, used by the sort order menu of the table sort panel.
     *
     * @param level The sort level.
     * @param component The TableColumn component to render.
     * @return The selected menu option value.
     */
    private String getSelectedSortOrderMenuOption(TableColumn component,
            int level) {
        String result = null;
        if (component == null) {
            log("getSelectedSortOrderMenuOption", //NOI18N
                "Cannot obtain select sort column order option, TableColumn is null"); //NOI18N
            return result;            
        }

        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn kid = (TableColumn) kids.next();
                result = getSelectedSortColumnMenuOption(kid, level);
                if (result != null) {
                    return result;
                }
            }
        }

        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;

        // Get SortCriteria.
        if (group != null) {
            SortCriteria criteria = component.getSortCriteria();
            if (criteria != null) {
                // Get initial selected option value.
                int sortLevel = group.getSortLevel(criteria);
                if (sortLevel == level) {
                    result = Boolean.toString(group.isDescendingSort(criteria));
                }
            }
        } else {
            log("getSelectedSortOrderMenuOption", //NOI18N
                "Cannot obtain select sort order menu option, TableRowGroup is null"); //NOI18N
        }
        return result;
    }

    /**
     * Helper method to get options used by the sort column menu of the table
     * sort panel.
     *
     * @return An array of menu options.
     */
    private Option[] getSortColumnMenuOptions() {
        ArrayList list = new ArrayList();
        Table table = getTableAncestor();
        TableRowGroup group = (table != null)
            ? table.getTableRowGroupChild() : null;

        // Add default "None" option -- an empty string represents no sort.
        list.add(new Option("", getTheme().getMessage("table.panel.none"))); //NOI18N

        // For each sortable TableColumn, use the header text for each label and
        // the SortCriteria key as the value.
        if (group != null) {
            Iterator kids = group.getTableColumnChildren();
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                // Get header text and sort value binding expression string.
                initSortColumnMenuOptions(col, list);
            }
        } else {
            log("getSortColumnMenuOptions", //NOI18N
                "Cannot obtain sort column menu options, TableRowGroup is null"); //NOI18N
        }
        // Set options.
        Option[] options = new Option[list.size()];
        return (Option[]) list.toArray(options);
    }

    /** 
     * Helper method to get options for the sort order menu used in the table
     * sort panel.
     *
     * @return An array of menu options.
     */
    private Option[] getSortOrderMenuOptions() {
        ArrayList results = new ArrayList();

        // Add default option.
        results.add(new Option("false", getTheme().getMessage( //NOI18N
            "table.sort.augment.undeterminedAscending"))); //NOI18N
        results.add(new Option("true", getTheme().getMessage( //NOI18N
            "table.sort.augment.undeterminedDescending"))); //NOI18N

        // Set default options. Other options will be added client-side when
        // menu is initialized.
        Option[] options = new Option[results.size()];
        return (Option[]) results.toArray(options);
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
     * Helper method to get options for nested TableColumn components, used by 
     * the sort column menu of the table sort panel.
     *
     * @param component The TableColumn component to render.
     * @param list The array used to store menu options.
     * @return An array of menu options.
     */
    private void initSortColumnMenuOptions(TableColumn component, List list) {
        if (component == null) {
            return;
        }

        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn kid = (TableColumn) kids.next();
                initSortColumnMenuOptions(kid, list);
            }
        }

        // Get header text and sort value binding expression string.
        SortCriteria criteria = component.getSortCriteria(); //NOI18N
        if (criteria == null) {
            log("initSortColumnMenuOptions", //NOI18N
                "Cannot initialize sort column menu options, SortCriteria is null"); //NOI18N
            return;
        }

        // Get label.
        String label = (component.getSelectId() != null)
            ? getTheme().getMessage("table.select.selectedItems") //NOI18N
            : component.getHeaderText();

        // Add option.
        list.add(new Option(criteria.getCriteriaKey(), 
            (label != null) ? label : "")); //NOI18N
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
