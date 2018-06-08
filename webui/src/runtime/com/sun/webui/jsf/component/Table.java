/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;

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
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.web.ui.component.Table.level = FINE
 * </pre><p>
 * See TLD docs for more information.
 * </p>
 */
@Component(type = "com.sun.webui.jsf.Table", family = "com.sun.webui.jsf.Table",
displayName = "Table", tagName = "table",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_table")
public class Table extends UIComponentBase implements NamingContainer {

    /** The facet name for the bottom actions area. */
    public static final String ACTIONS_BOTTOM_FACET = "actionsBottom"; //NOI18N
    /** The facet name for top actions area. */
    public static final String ACTIONS_TOP_FACET = "actionsTop"; //NOI18N
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
    /** The facet name for the filter area. */
    public static final String FILTER_FACET = "filter"; //NOI18N
    /** The facet name for the filter panel. */
    public static final String FILTER_PANEL_FACET = "filterPanel"; //NOI18N
    /** The facet name for the footer area. */
    public static final String FOOTER_FACET = "footer"; //NOI18N
    /** The facet name for the preferences panel. */
    public static final String PREFERENCES_PANEL_FACET = "preferencesPanel"; //NOI18N
    /** The facet name for the sort panel. */
    public static final String SORT_PANEL_FACET = "sortPanel"; //NOI18N
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

    // A List containing TableRowGroup children.
    private List tableRowGroupChildren = null;

    // The number of TableRowGroup children.
    private int tableRowGroupCount = -1;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attributes
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
    @Property(name = "align", displayName = "Table Alignment", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor", isHidden = true, isAttribute = false)
    private String align = null;
    /**
     * Flag indicating that the table title should be augmented with the range of items 
     * currently displayed and the total number of items in the table. For example, 
     * "(1 - 25 of 200)". If the table is not currently paginated, the title is 
     * augmented with the number of displayed items. For example, "(18)". When set to 
     * false, any values set for <code>itemsText</code> and <code>filterText</code> 
     * are overridden.
     */
    @Property(name = "augmentTitle", displayName = "Show Augmented Title", category = "Appearance")
    private boolean augmentTitle = false;
    private boolean augmentTitle_set = false;
    /**
     * The deprecated BGCOLOR attribute suggests a background color for the
     * table. The combination of this attribute with <FONT COLOR=...> can
     * leave invisible or unreadable text on Netscape Navigator 2.x, which
     * does not support BGCOLOR on table elements. BGCOLOR is dangerous even
     * on supporting browsers, since most fail to override it when overriding
     * other author-specified colors. Style sheets provide a safer, more
     * flexible method of specifying a table's background color.
     */
    @Property(name = "bgColor", displayName = "Table Background Color", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor", isHidden = true, isAttribute = false)
    private String bgColor = null;
    /**
     * The BORDER attribute specifies the width in pixels of the border around a table.
     */
    @Property(name = "border", displayName = "Border Width", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.LengthPropertyEditor", isHidden = true, isAttribute = false)
    private int border = Integer.MIN_VALUE;
    private boolean border_set = false;
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
    @Property(name = "cellPadding", displayName = "Spacing Within Cells", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String cellPadding = null;
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
    @Property(name = "cellSpacing", displayName = "Spacing Between Cells", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String cellSpacing = null;
    /**
     * In the View-Changing Controls area of the Action Bar, display a button that 
     * clears any sorting of the table. When the button is clicked, the table items 
     * return to the order they were in when the page was initially rendered.
     */
    @Property(name = "clearSortButton", displayName = "Show Clear Sort Button", category = "Appearance")
    private boolean clearSortButton = false;
    private boolean clearSortButton_set = false;
    /**
     * In the Action Bar, display a deselect button for tables in which multiple rows 
     * can be selected, to allow users to deselect all table rows that are currently 
     * displayed. This button is used to deselect a column of checkboxes using the id 
     * that was given to the selectId attribute of the <code>webuijsf:tableColumn</code> tag.
     */
    @Property(name = "deselectMultipleButton", displayName = "Show Deselect Multiple Button", category = "Appearance")
    private boolean deselectMultipleButton = false;
    private boolean deselectMultipleButton_set = false;
    /**
     * Scripting code that is executed when the user clicks the deselect multiple 
     * button. You should use the JavaScript <code>setTimeout()</code> function to 
     * invoke the script to ensure that checkboxes are deselected immediately, instead 
     * of waiting for the script to complete.
     */
    @Property(name = "deselectMultipleButtonOnClick", displayName = "Deselect Multiple Click Script",
    category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String deselectMultipleButtonOnClick = null;
    /**
     * In the Action Bar, display a deselect button for tables in which only a single 
     * table row can be selected at a time. This button is used to deselect a column of 
     * radio buttons using the id that was given to the selectId attribute of the 
     * <code>webuijsf:tableColumn</code> tag.
     */
    @Property(name = "deselectSingleButton", displayName = "Show Deselect Single Button", category = "Appearance")
    private boolean deselectSingleButton = false;
    private boolean deselectSingleButton_set = false;
    /**
     * Scripting code that is executed when the user clicks the deselect single button.
     * You should use the JavaScript <code>setTimeout()</code> function to invoke the 
     * script to ensure that the radio button is deselected immediately, instead of 
     * waiting for the script to complete.
     */
    @Property(name = "deselectSingleButtonOnClick", displayName = "Deselect Single Click Script",
    category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String deselectSingleButtonOnClick = null;
    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that  
     * is rendered for the Action Bar (bottom). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    @Property(name = "extraActionBottomHtml", displayName = "Extra Action (bottom) HTML",
    category = "Advanced", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String extraActionBottomHtml = null;
    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the Action Bar (top). Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myActionBarStyle'"</code>.
     */
    @Property(name = "extraActionTopHtml", displayName = "Extra Action (top) HTML",
    category = "Advanced", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String extraActionTopHtml = null;

    // filterId
    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for the table footer. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"nowrap=`nowrap'"</code>.
     */
    @Property(name = "extraFooterHtml", displayName = "Extra Footer HTML",
    category = "Advanced", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String extraFooterHtml = null;
    /**
     * Extra HTML code to be appended to the <code>&lt;td&gt; </code>HTML element that 
     * is rendered for an embedded panel. Use only code that is valid in an HTML 
     * <code>&lt;td&gt;</code> element. The code you specify is inserted in the HTML 
     * element, and is not checked for validity.
     */
    @Property(name = "extraPanelHtml", displayName = "Extra Panel HTML",
    category = "Advanced", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String extraPanelHtml = null;
    /**
     * Extra HTML code to be appended to the <code>&lt;caption&gt;</code> HTML element 
     * that is rendered for the table title. Use only code that is valid in an HTML 
     * <code>&lt;caption&gt;</code> element. The code you specify is inserted in the 
     * HTML element, and is not checked for validity. For example, you might set this 
     * attribute to <code>"style=`myTitleStyle'"</code>.
     */
    @Property(name = "extraTitleHtml", displayName = "Extra Title HTML",
    category = "Advanced", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String extraTitleHtml = null;
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
    @Property(name = "filterId", displayName = "Filter Component Id", category = "Appearance",
    isHidden = true, editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String filterId = null;
    /**
     * The element id used to set focus when the filter panel is open.
     */
    @Property(name = "filterPanelFocusId", displayName = "Filter Panel Focus ID", category = "Advanced", isHidden = true)
    private String filterPanelFocusId = null;
    /**
     * Text to be inserted into the table title bar when a filter is applied. This text 
     * is expected to be the name of the filter that the user has selected. The 
     * attribute value should be a JavaServer Faces EL expression that resolves to a 
     * backing bean property whose value is set in your filter code. The value of the 
     * filterText attribute is inserted into the table title, as follows: Your Table's 
     * Title <span style="font-style: italic;">filterText</span> Filter Applied.
     */
    @Property(name = "filterText", displayName = "Filter Text", category = "Appearance",
    isHidden = true, editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String filterText = null;
    /**
     * The text to be displayed in the table footer, which expands across the width of 
     * the table.
     */
    @Property(name = "footerText", displayName = "Footer Text", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String footerText = null;
    /**
     * The BORDER attribute specifies the width in pixels of the border
     * around a table.
     */
    @Property(name = "frame", displayName = "Outer Border", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor", isHidden = true, isAttribute = false)
    private String frame = null;
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
    @Property(name = "hiddenSelectedRows", displayName = "Is Hidden Selected Rows", category = "Advanced")
    private boolean hiddenSelectedRows = false;
    private boolean hiddenSelectedRows_set = false;
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
    @Property(name = "internalVirtualForm", displayName = "Is Internal Virtual Form", category = "Advanced", isAttribute = false)
    private boolean internalVirtualForm = false;
    private boolean internalVirtualForm_set = false;
    /**
     * Text to add to the title of an unpaginated table. For example, if your table 
     * title is "Critical" and there are 20 items in the table, the default unpaginated 
     * table title would be Critical (20). If you specify itemsText="alerts", the title 
     * would be Critical (20 alerts).
     */
    @Property(name = "itemsText", displayName = "Items Text", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String itemsText = null;
    /**
     * Renders the table in a style that makes the table look lighter weight, generally 
     * by omitting the shading around the table and in the title bar.
     */
    @Property(name = "lite", displayName = "Light Weight Table", category = "Appearance")
    private boolean lite = false;
    private boolean lite_set = false;
    /**
     * Scripting code executed when a mouse click
     * occurs over this component.
     */
    @Property(name = "onClick", displayName = "Click Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;
    /**
     * Scripting code executed when a mouse double click
     * occurs over this component.
     */
    @Property(name = "onDblClick", displayName = "Double Click Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDblClick = null;
    /**
     * Scripting code executed when the user presses down on a key while the
     * component has focus.
     */
    @Property(name = "onKeyDown", displayName = "Key Down Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyDown = null;
    /**
     * Scripting code executed when the user presses and releases a key while
     * the component has focus.
     */
    @Property(name = "onKeyPress", displayName = "Key Press Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyPress = null;
    /**
     * Scripting code executed when the user releases a key while the
     * component has focus.
     */
    @Property(name = "onKeyUp", displayName = "Key Up Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyUp = null;
    /**
     * Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.
     */
    @Property(name = "onMouseDown", displayName = "Mouse Down Script",
    category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;
    /**
     * Scripting code executed when the user moves the mouse pointer while
     * over the component.
     */
    @Property(name = "onMouseMove", displayName = "Mouse Move Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;
    /**
     * Scripting code executed when a mouse out movement
     * occurs over this component.
     */
    @Property(name = "onMouseOut", displayName = "Mouse Out Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;
    /**
     * Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.
     */
    @Property(name = "onMouseOver", displayName = "Mouse In Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;
    /**
     * Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.
     */
    @Property(name = "onMouseUp", displayName = "Mouse Up Script", category = "Javascript",
    editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;
    /**
     * Show table paginate button to allow users to switch between viewing all data on 
     * a single page (unpaginated) or to see data in multiple pages (paginated).
     */
    @Property(name = "paginateButton", displayName = "Show Paginate Button", category = "Appearance")
    private boolean paginateButton = false;
    private boolean paginateButton_set = false;
    /**
     * Show the table pagination controls, which allow users to change which page is 
     * displayed. The controls include an input field for specifying the page number, a 
     * Go button to go to the specified page, and buttons for going to the first, last, 
     * previous, and next page.
     */
    @Property(name = "paginationControls", displayName = "Show Pagination Controls", category = "Appearance")
    private boolean paginationControls = false;
    private boolean paginationControls_set = false;
    /**
     * The element id used to set focus when the preferences panel is open.
     */
    @Property(name = "preferencesPanelFocusId", displayName = "Preferences Panel Focus ID",
    category = "Advanced", isHidden = true)
    private String preferencesPanelFocusId = null;
    /**
     * The RULES attribute, poorly supported by browsers, specifies the
     * borders between table cells. Possible values are none for no inner
     * borders, groups for borders between row groups and column groups only,
     * rows for borders between rows only, cols for borders between columns
     * only, and all for borders between all cells. None is the default value
     * if BORDER=0 is used or if no BORDER attribute is given. All is the
     * default value for any other use of BORDER.
     */
    @Property(name = "rules", displayName = "Inner Borders", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor", isHidden = true, isAttribute = false)
    private String rules = null;
    /**
     * Show the button that is used for selecting multiple rows. The button is 
     * displayed in the Action Bar (top), and allows users to select all rows currently 
     * displayed. The button selects a column of checkboxes using the id specified in 
     * the selectId attribute of the <code>webuijsf:tableColumn</code> tag.
     */
    @Property(name = "selectMultipleButton", displayName = "Show Select Multiple Button", category = "Appearance")
    private boolean selectMultipleButton = false;
    private boolean selectMultipleButton_set = false;
    /**
     * Scripting code executed when the user clicks the mouse on the select multiple 
     * button.
     */
    @Property(name = "selectMultipleButtonOnClick", displayName = "Select Multiple Click Script",
    category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String selectMultipleButtonOnClick = null;
    /**
     * The element id used to set focus when the sort panel is open.
     */
    @Property(name = "sortPanelFocusId", displayName = "Sort Panel Focus ID", category = "Advanced", isHidden = true)
    private String sortPanelFocusId = null;
    /**
     * Show the button that is used to open and close the sort panel.
     */
    @Property(name = "sortPanelToggleButton", displayName = "Show Sort Panel Toggle Button", category = "Appearance")
    private boolean sortPanelToggleButton = false;
    private boolean sortPanelToggleButton_set = false;
    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name = "style", displayName = "CSS Style(s)", category = "Appearance",
    editorClassName = "com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;
    /**
     * CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    @Property(name = "styleClass", displayName = "CSS Style Class(es)", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    /**
     * Text that describes this table's purpose and structure, for user agents 
     * rendering to non-visual media such as speech and Braille.
     */
    @Property(name = "summary", displayName = "Purpose of Table", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String summary = null;
    /**
     * Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.
     */
    @Property(name = "tabIndex", displayName = "Tab Index", category = "Accessibility",
    editorClassName = "com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;
    /**
     * The text displayed for the table title.
     */
    @Property(name = "title", displayName = "Table Title", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String title = null;
    /**
     * Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.
     */
    @Property(name = "toolTip", displayName = "Tool Tip", category = "Behavior",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String toolTip = null;
    /**
     * Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.
     */
    @Property(name = "visible", displayName = "Visible", category = "Behavior")
    private boolean visible = false;
    private boolean visible_set = false;
    /**
     * Use the <code>width</code> attribute to specify the width of the table. The 
     * width can be specified as the number of pixels or the percentage of the page 
     * width, and is especially useful for spacer columns. This attribute is 
     * deprecated in HTML 4.0 in favor of style sheets.
     */
    @Property(name = "width", displayName = "Table Width", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String width = null;

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
     * response phase. For example, the underlying DataProvider may have changed
     * and TableRenderer may need new calculations for the title and action bar.
     * </p><p>
     * Note: Properties of TableRowGroup and TableColumn children shall be 
     * cleared as well.
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
        tableRowGroupChildren = null;
        tableRowGroupCount = -1;

        // Clear properties of TableRowGroup children.
        Iterator kids = getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroup kid = (TableRowGroup) kids.next();
            kid.clear(); // Clear cached properties.
        }
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
     * Get an Iterator over the TableRowGroup children found for this component.
     *
     * @return An Iterator over the TableRowGroup children.
     */
    public Iterator getTableRowGroupChildren() {
        // Get TableRowGroup children.
        if (tableRowGroupChildren == null) {
            tableRowGroupChildren = new ArrayList();
            Iterator kids = getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if ((kid instanceof TableRowGroup)) {
                    tableRowGroupChildren.add(kid);
                }
            }
        }
        return tableRowGroupChildren.iterator();
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
        boolean renderActions = !emptyTable && !singleRow && actions != null && actions.isRendered();

        // Hide pagination controls when all rows fit on a page.
        boolean renderPaginationControls = !emptyTable && !singlePage && isPaginationControls();

        // Hide paginate button for a single row.
        boolean renderPaginateButton = !emptyTable && !singlePage && isPaginateButton();

        // Set rendered.
        if (!(renderActions || renderPaginationControls || renderPaginateButton)) {
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
        boolean renderSelectMultipleButton = !emptyTable && isSelectMultipleButton();
        boolean renderDeselectMultipleButton = !emptyTable && isDeselectMultipleButton();
        boolean renderDeselectSingleButton = !emptyTable && isDeselectSingleButton();
        boolean renderClearTableSortButton = !emptyTable && !singleRow && isClearSortButton();
        boolean renderTableSortPanelToggleButton = !emptyTable && !singleRow && (isSortPanelToggleButton() || renderSort);
        boolean renderPaginateButton = !emptyTable && !singlePage && isPaginateButton();

        // Return if nothing is rendered.
        if (!(renderActions || renderFilter || renderPrefs || renderSelectMultipleButton || renderDeselectMultipleButton || renderDeselectSingleButton || renderClearTableSortButton || renderTableSortPanelToggleButton || renderPaginateButton)) {
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
        String filterId = _getFilterId();
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
        if (!(facet != null && facet.isRendered() || getFooterText() != null || isHiddenSelectedRows())) {
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
        if (!(renderFilter || renderSort || renderPrefs || isSortPanelToggleButton())) {
            log("getEmbeddedPanels", //NOI18N
                    "Embedded panels not rendered, nothing to display"); //NOI18N
            child.setRendered(false);
        }

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
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        // Clear cached properties.
        clear();

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
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name = "id")
    @Override
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name = "rendered")
    @Override
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
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
     * that was given to the selectId attribute of the <code>webuijsf:tableColumn</code> tag.
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
     * that was given to the selectId attribute of the <code>webuijsf:tableColumn</code> tag.
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
     * <code>webuijsf:tableColumn</code> tag.
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
     * <code>webuijsf:tableColumn</code> tag.
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
    private String _getFilterId() { // To do: Merge gen code with public method?
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
     * the selectId attribute of the <code>webuijsf:tableColumn</code> tag.
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
     * the selectId attribute of the <code>webuijsf:tableColumn</code> tag.
     */
    public void setSelectMultipleButton(boolean selectMultipleButton) {
        this.selectMultipleButton = selectMultipleButton;
        this.selectMultipleButton_set = true;
    }

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

    /**
     * Restore the state of this component.
     */
    @Override
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
        this.extraActionBottomHtml = (String) _values[17];
        this.extraActionTopHtml = (String) _values[18];
        this.extraFooterHtml = (String) _values[19];
        this.extraPanelHtml = (String) _values[20];
        this.extraTitleHtml = (String) _values[21];
        this.filterId = (String) _values[22];
        this.filterPanelFocusId = (String) _values[23];
        this.filterText = (String) _values[24];
        this.footerText = (String) _values[25];
        this.frame = (String) _values[26];
        this.hiddenSelectedRows = ((Boolean) _values[27]).booleanValue();
        this.hiddenSelectedRows_set = ((Boolean) _values[28]).booleanValue();
        this.internalVirtualForm = ((Boolean) _values[29]).booleanValue();
        this.internalVirtualForm_set = ((Boolean) _values[30]).booleanValue();
        this.itemsText = (String) _values[31];
        this.lite = ((Boolean) _values[32]).booleanValue();
        this.lite_set = ((Boolean) _values[33]).booleanValue();
        this.onClick = (String) _values[34];
        this.onDblClick = (String) _values[35];
        this.onKeyDown = (String) _values[36];
        this.onKeyPress = (String) _values[37];
        this.onKeyUp = (String) _values[38];
        this.onMouseDown = (String) _values[39];
        this.onMouseMove = (String) _values[40];
        this.onMouseOut = (String) _values[41];
        this.onMouseOver = (String) _values[42];
        this.onMouseUp = (String) _values[43];
        this.paginateButton = ((Boolean) _values[44]).booleanValue();
        this.paginateButton_set = ((Boolean) _values[45]).booleanValue();
        this.paginationControls = ((Boolean) _values[46]).booleanValue();
        this.paginationControls_set = ((Boolean) _values[47]).booleanValue();
        this.preferencesPanelFocusId = (String) _values[48];
        this.rules = (String) _values[49];
        this.selectMultipleButton = ((Boolean) _values[50]).booleanValue();
        this.selectMultipleButton_set = ((Boolean) _values[51]).booleanValue();
        this.selectMultipleButtonOnClick = (String) _values[52];
        this.sortPanelFocusId = (String) _values[53];
        this.sortPanelToggleButton = ((Boolean) _values[54]).booleanValue();
        this.sortPanelToggleButton_set = ((Boolean) _values[55]).booleanValue();
        this.style = (String) _values[56];
        this.styleClass = (String) _values[57];
        this.summary = (String) _values[58];
        this.tabIndex = ((Integer) _values[59]).intValue();
        this.tabIndex_set = ((Boolean) _values[60]).booleanValue();
        this.title = (String) _values[61];
        this.toolTip = (String) _values[62];
        this.visible = ((Boolean) _values[63]).booleanValue();
        this.visible_set = ((Boolean) _values[64]).booleanValue();
        this.width = (String) _values[65];
    }

    /**
     * Save the state of this component.
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[66];
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
        _values[17] = this.extraActionBottomHtml;
        _values[18] = this.extraActionTopHtml;
        _values[19] = this.extraFooterHtml;
        _values[20] = this.extraPanelHtml;
        _values[21] = this.extraTitleHtml;
        _values[22] = this.filterId;
        _values[23] = this.filterPanelFocusId;
        _values[24] = this.filterText;
        _values[25] = this.footerText;
        _values[26] = this.frame;
        _values[27] = this.hiddenSelectedRows ? Boolean.TRUE : Boolean.FALSE;
        _values[28] = this.hiddenSelectedRows_set ? Boolean.TRUE : Boolean.FALSE;
        _values[29] = this.internalVirtualForm ? Boolean.TRUE : Boolean.FALSE;
        _values[30] = this.internalVirtualForm_set ? Boolean.TRUE : Boolean.FALSE;
        _values[31] = this.itemsText;
        _values[32] = this.lite ? Boolean.TRUE : Boolean.FALSE;
        _values[33] = this.lite_set ? Boolean.TRUE : Boolean.FALSE;
        _values[34] = this.onClick;
        _values[35] = this.onDblClick;
        _values[36] = this.onKeyDown;
        _values[37] = this.onKeyPress;
        _values[38] = this.onKeyUp;
        _values[39] = this.onMouseDown;
        _values[40] = this.onMouseMove;
        _values[41] = this.onMouseOut;
        _values[42] = this.onMouseOver;
        _values[43] = this.onMouseUp;
        _values[44] = this.paginateButton ? Boolean.TRUE : Boolean.FALSE;
        _values[45] = this.paginateButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[46] = this.paginationControls ? Boolean.TRUE : Boolean.FALSE;
        _values[47] = this.paginationControls_set ? Boolean.TRUE : Boolean.FALSE;
        _values[48] = this.preferencesPanelFocusId;
        _values[49] = this.rules;
        _values[50] = this.selectMultipleButton ? Boolean.TRUE : Boolean.FALSE;
        _values[51] = this.selectMultipleButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[52] = this.selectMultipleButtonOnClick;
        _values[53] = this.sortPanelFocusId;
        _values[54] = this.sortPanelToggleButton ? Boolean.TRUE : Boolean.FALSE;
        _values[55] = this.sortPanelToggleButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[56] = this.style;
        _values[57] = this.styleClass;
        _values[58] = this.summary;
        _values[59] = new Integer(this.tabIndex);
        _values[60] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[61] = this.title;
        _values[62] = this.toolTip;
        _values[63] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[64] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[65] = this.width;
        return _values;
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
