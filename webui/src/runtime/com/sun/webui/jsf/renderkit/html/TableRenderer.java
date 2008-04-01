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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.data.provider.RowKey;
import com.sun.data.provider.SortCriteria;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableActions;
import com.sun.webui.jsf.component.TableColumn;
import com.sun.webui.jsf.component.TableHeader;
import com.sun.webui.jsf.component.TablePanels;
import com.sun.webui.jsf.component.TableRowGroup;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeImages;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table components.
 * <p>
 * The table component provides a layout mechanism for displaying table actions.
 * UI guidelines describe specific behavior that can applied to the rows and 
 * columns of data such as sorting, filtering, pagination, selection, and custom 
 * user actions. In addition, UI guidelines also define sections of the table 
 * that can be used for titles, row group headers, and placement of pre-defined
 * and user defined actions.
 * </p><p>
 * Note: Column headers and footers are rendered by TableRowGroupRenderer. Table
 * column footers are rendered by TableRenderer.
 * </p><p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.renderkit.html.TableRenderer.level = FINE
 * </pre></p><p>
 * See TLD docs for more information.
 * </p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Table"))
public class TableRenderer extends javax.faces.render.Renderer {
    // Javascript object name.
    private static final String JAVASCRIPT_OBJECT_CLASS = "Table"; //NOI18N

    /**
     * The set of String pass-through attributes to be rendered.
     * <p>
     * Note: The BGCOLOR attribute is deprecated (in the HTML 4.0 spec) in favor
     * of style sheets. In addition, the DIR and LANG attributes are not
     * cuurently supported.
     * </p>
     */
    private static final String stringAttributes[] = {
        "align", //NOI18N
        "bgColor", //NOI18N
        "dir", //NOI18N
        "frame", //NOI18N
        "lang", //NOI18N
        "onClick", //NOI18N
        "onDblClick", //NOI18N
        "onKeyDown", //NOI18N
        "onKeyPress", //NOI18N
        "onKeyUp", //NOI18N
        "onMouseDown", //NOI18N
        "onMouseMove", //NOI18N
        "onMouseOut", //NOI18N
        "onMouseOver", //NOI18N
        "onMouseUp", //NOI18N
        "rules", //NOI18N
        "summary"}; //NOI18N

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render the beginning of the specified UIComponent to the output stream or 
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            log("encodeBegin", //NOI18N
                "Cannot render, FacesContext or UIComponent is null"); //NOI18N
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            log("encodeBegin", "Component not rendered, nothing to display"); //NOI18N
            return;
        }

        Table table = (Table) component;
        ResponseWriter writer = context.getResponseWriter();
        renderEnclosingTagStart(context, table, writer);
        renderTitle(context, table, writer);
        renderActionsTop(context, table, writer);
        renderEmbeddedPanels(context, table, writer);
    }

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be decoded.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            log("encodeChildren", //NOI18N
                "Cannot render, FacesContext or UIComponent is null"); //NOI18N
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            log("encodeChildren", "Component not rendered, nothing to display"); //NOI18N
            return;
        }

        Table table = (Table) component;
        ResponseWriter writer = context.getResponseWriter();

        // Render TableRowGroup children.
        Iterator kids = table.getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroup group = (TableRowGroup) kids.next();
            RenderingUtilities.renderComponent(group, context);
        }
    }

    /**
     * Render the ending of the specified UIComponent to the output stream or 
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            log("encodeEnd", //NOI18N
                "Cannot render, FacesContext or UIComponent is null"); //NOI18N
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            log("encodeEnd", "Component not rendered, nothing to display"); //NOI18N
            return;
        }

        Table table = (Table) component;
        ResponseWriter writer = context.getResponseWriter();
        renderActionsBottom(context, table, writer);
        renderTableFooter(context, table, writer);
        renderEnclosingTagEnd(writer);
        renderJavascript(context, table, writer);
    }

    /**
     * Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns false.
     */
    public boolean getRendersChildren() {
        return true;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Action bar methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render the bottom actions for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderActionsBottom(FacesContext context,
            Table component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderActionsBottom", //NOI18N
                "Cannot render actions bar, Table is null"); //NOI18N
            return;
        }

        // Get panel component.
        UIComponent actions = component.getTableActionsBottom();
        if (!(actions != null && actions.isRendered())) {
            log("renderActionsBottom", //NOI18N
                "Actions bar not rendered, nothing to display"); //NOI18N
            return;
        }

        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", component); //NOI18N
        writer.writeAttribute("id", getId(component, //NOI18N
            Table.TABLE_ACTIONS_BOTTOM_BAR_ID), null);

        // Render embedded panels.
        RenderingUtilities.renderComponent(actions, context);
        writer.endElement("tr"); //NOI18N
    }

    /**
     * Render the top actions for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderActionsTop(FacesContext context,
            Table component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderActionsTop", //NOI18N
                "Cannot render actions bar, Table is null"); //NOI18N
            return;
        }

        // Get panel component.
        UIComponent actions = component.getTableActionsTop();
        if (!(actions != null && actions.isRendered())) {
            log("renderActionsTop", //NOI18N
                "Actions bar not rendered, nothing to display"); //NOI18N
            return;
        }

        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", component); //NOI18N
        writer.writeAttribute("id", getId(component, //NOI18N
            Table.TABLE_ACTIONS_TOP_BAR_ID), null);

        // Render embedded panels.
        RenderingUtilities.renderComponent(actions, context);
        writer.endElement("tr"); //NOI18N
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Embedded panel methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render embedded panels for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEmbeddedPanels(FacesContext context,
            Table component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderEmbeddedPanels", //NOI18N
                "Cannot render embedded panels, Table is null"); //NOI18N
            return;
        }

        // Get panel component.
        UIComponent panels = component.getEmbeddedPanels();
        if (!(panels != null && panels.isRendered())) {
            log("renderEmbeddedPanels", //NOI18N
                "Embedded panels not rendered, nothing to display"); //NOI18N
            return;
        }

        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", component); //NOI18N
        writer.writeAttribute("id", getId(component, //NOI18N
            Table.EMBEDDED_PANELS_BAR_ID), null);

        // Render embedded panels.
        RenderingUtilities.renderComponent(panels, context);
        writer.endElement("tr"); //NOI18N
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Footer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render table footer for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderTableFooter(FacesContext context, Table component,
            ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderTableFooter", //NOI18N
                "Cannot render table foter, Table is null"); //NOI18N
            return;
        }

        // Get footer.
        UIComponent footer = component.getTableFooter();
        if (!(footer != null && footer.isRendered())) {
            log("renderTableFooter", //NOI18N
                "Table footer not rendered, nothing to display"); //NOI18N
            return;
        }

        Theme theme = getTheme();
        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", component); //NOI18N
        writer.writeAttribute("id", getId(component, Table.TABLE_FOOTER_BAR_ID), //NOI18N
            null);

        // Render footer.
        RenderingUtilities.renderComponent(footer, context);
        writer.endElement("tr"); //NOI18N
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Title methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render title for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderTitle(FacesContext context, Table component,
            ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderTitle", "Cannot render title, Table is null"); //NOI18N
            return;
        }

        // Render facet.
        UIComponent facet = component.getFacet(Table.TITLE_FACET);
        if (facet != null) {
            renderTitleStart(context, component, writer);
            RenderingUtilities.renderComponent(facet, context);
            renderTitleEnd(context, writer);
            return;
        }

        // Render default title.
        if (component.getTitle() == null) {
            log("renderTitle", "Title is null, nothing to display"); //NOI18N
            return;
        }

        // Get filter augment.
        Theme theme = getTheme();
        String filterText = component.getFilterText();
        filterText = (filterText != null && filterText.length() > 0)
            ? theme.getMessage("table.title.filterApplied", //NOI18N
                new String[] {filterText})
            : ""; //NOI18N

        // Get TableRowGroup component.
        TableRowGroup group = component.getTableRowGroupChild();
        boolean paginated = (group != null) ? group.isPaginated() : false;

        // Initialize values.
        int totalRows = component.getRowCount();
        boolean emptyTable = (totalRows == 0);
        boolean singlePage = (totalRows < component.getRows());

        // Render title (e.g., "Title (25 - 50 of 1000): [Filter]").
        String title = component.getTitle();
        if (component.isAugmentTitle()) {
            if (!emptyTable && !singlePage && paginated) {
                // Get max values for paginated group table.
                int maxRows = component.getRows();
                int maxFirst = component.getFirst();

                // Get first and last rows augment.
                String first = Integer.toString(maxFirst + 1);
                String last = Integer.toString(Math.min(maxFirst + maxRows,
                    totalRows));

                if (component.getItemsText() != null) {
                    title = theme.getMessage("table.title.paginatedItems", //NOI18N
                        new String[] {component.getTitle(), first, last,
                            Integer.toString(totalRows), component.getItemsText(), filterText});
                } else {
                    title = theme.getMessage("table.title.paginated", //NOI18N
                        new String[] {component.getTitle(), first, last,
                            Integer.toString(totalRows), filterText});
                }
            } else {
                if (component.getItemsText() != null) {
                    title = theme.getMessage("table.title.scrollItems", //NOI18N
                        new String[] {component.getTitle(), Integer.toString(totalRows),
                            component.getItemsText(), filterText});
                } else {
                    title = theme.getMessage("table.title.scroll", //NOI18N
                        new String[] {component.getTitle(), Integer.toString(totalRows),
                            filterText});
                }
            }
        } else {
            log("renderTitle", //NOI18N
                "Title not augmented, itemsText & filterText not displayed"); //NOI18N
        }

        renderTitleStart(context, component, writer);

        // Render title and hidden rows text.
        if (component.isHiddenSelectedRows()) {
            writer.startElement("span", component); //NOI18N
            writer.writeAttribute("class", //NOI18N
                theme.getStyleClass(ThemeStyles.TABLE_TITLE_TEXT_SPAN), null);
            writer.writeText(title, null);
            writer.endElement("span"); //NOI18N
            writer.startElement("span", component); //NOI18N
            writer.writeAttribute("class", //NOI18N
                theme.getStyleClass(ThemeStyles.TABLE_TITLE_MESSAGE_SPAN), null);
            writer.writeText(theme.getMessage("table.hiddenSelections", //NOI18N
                new String[] {Integer.toString(
                    component.getHiddenSelectedRowsCount())}), null);
            writer.endElement("span"); //NOI18N
        } else {
            // Render default title text.
            writer.writeText(title, null);
        }
        renderTitleEnd(context, writer);
    }

    /**
     * Render title for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component The table component being rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void renderTitleStart(FacesContext context, Table component,
            ResponseWriter writer)throws IOException {
        writer.writeText("\n", null); //NOI18N
        writer.startElement("caption", component); //NOI18N
        writer.writeAttribute("id", getId(component, Table.TITLE_BAR_ID), //NOI18N
            null);
        writer.writeAttribute("class", //NOI18N
            getTheme().getStyleClass(ThemeStyles.TABLE_TITLE_TEXT), null);

        // Render extra HTML.
        if (component.getExtraTitleHtml() != null) {
            RenderingUtilities.renderExtraHtmlAttributes(writer, 
                component.getExtraTitleHtml());
        }
    }

    /**
     * Render title for Table components.
     *
     * @param context FacesContext for the current request.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void renderTitleEnd(FacesContext context, ResponseWriter writer)
            throws IOException {
        writer.endElement("caption"); //NOI18N
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Enclosing tag methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render enclosing tag for Table components.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagStart(FacesContext context,
            Table component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderEnclosingTagStart", //NOI18N
                "Cannot render enclosing tag, Table is null"); //NOI18N
            return;
        }

        Theme theme = getTheme();

        // Render div used to set style and class properties -- bugtraq #6316179.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N
        writer.writeAttribute("id", component.getClientId(context), null); //NOI18N

        // Render style.
	String style = component.getStyle();
        if (style != null) {
            writer.writeAttribute("style", style, null); //NOI18N
        }

        // Render style class.
        RenderingUtilities.renderStyleClass(context, writer, component, null);

        // Render div used to set width.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("div", component); //NOI18N

        // Render width.
	String width = component.getWidth();
        if (width != null) {
            // If not a percentage, units are in pixels.
            if (width.indexOf("%") == -1) { //NOI18N
                width += "px"; //NOI18N
            }
            writer.writeAttribute("style", "width:" + width, null); //NOI18N
        } else {
            writer.writeAttribute("style", "width:100%", null); //NOI18N
        }

        // Render table.
        writer.writeText("\n", null); //NOI18N
        writer.startElement("table", component); //NOI18N
        writer.writeAttribute("id", getId(component, Table.TABLE_ID), null); //NOI18N

        // Get style class.
        String styleClass = theme.getStyleClass(ThemeStyles.TABLE);
        if (component.isLite()) {
            styleClass += " " + //NOI18N
		theme.getStyleClass(ThemeStyles.TABLE_LITE);
        }

        // Render style class.
        writer.writeAttribute("class", styleClass, null); //NOI18N

        // Render width using 100% to ensure consistent right margins.
        writer.writeAttribute("width", "100%", null); //NOI18N

        // Render height for Creator which sets property via style.
	if (style != null) {
	    int first = style.indexOf("height:");
	    if (first > -1) {
		int last = style.indexOf(";", first);
		if (last > -1) {
		    writer.writeAttribute("style", style.substring(first, last + 1), null); //NOI18N
		} else {
		    writer.writeAttribute("style", style.substring(first), null); //NOI18N
		}
	    }
	}
	renderTableAttributes(context, component, writer);
    }

    /**
     * This implementation renders border, cellpadding, cellspacing,
     * tooltip and string attributes.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderTableAttributes(FacesContext context, Table component,
	   ResponseWriter writer) throws IOException {

        // Render border.
	int border = component.getBorder();
        if (border > -1) {
            writer.writeAttribute("border", //NOI18N
                Integer.toString(border), null); //NOI18N
        } else {
            writer.writeAttribute("border", "0", null); //NOI18N
        }

        // Render cellpadding.
	String value = component.getCellPadding();
        if (value != null) {
            writer.writeAttribute("cellpadding", value, null); //NOI18N
        } else {
            writer.writeAttribute("cellpadding", "0", null); //NOI18N
        }

        // Render cellspacing.
	value = component.getCellSpacing();
        if (value != null) {
            writer.writeAttribute("cellspacing", value, null); //NOI18N
        } else {
            writer.writeAttribute("cellspacing", "0", null); //NOI18N
        }

        // Render tooltip.
	value = component.getToolTip();
        if (value != null) {
            writer.writeAttribute("title", value, "toolTip"); //NOI18N
        }

        // Render pass through attributes.
        RenderingUtilities.writeStringAttributes(component, writer,
            stringAttributes);
    }

    /**
     * Render enclosing tag for Table components.
     *
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagEnd(ResponseWriter writer)
            throws IOException {
        writer.endElement("table"); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.endElement("div"); //NOI18N
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get component id.
     *
     * @param component The parent UIComponent component.
     * @param id The id of the the component to be rendered.
     */
    private String getId(UIComponent component, String id) {
        String clientId = component.getClientId(FacesContext.getCurrentInstance());
        return clientId + NamingContainer.SEPARATOR_CHAR + id;
    }

    /**
     * Helper method to get the column ID and selectId from nested TableColumn 
     * components, used in Javascript functions (e.g., de/select all button
     * functionality).
     *
     * @param context FacesContext for the current request.
     * @param component TableColumn to be rendered.
     * @return The first selectId property found.
     */
    private String getSelectId(FacesContext context, TableColumn component) {
        String selectId = null;
        if (component == null) {
            log("getSelectId", "Cannot obtain select Id, TableColumn is null"); //NOI18N
            return selectId;
        }
    
        // Render nested TableColumn children.
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                selectId = getSelectId(context, col);
                if (selectId != null) {
                    break;
                }
            }
        } else {
            // Get selectId for possible nested TableColumn components.
            if (component.getSelectId() != null) {
                // Get TableRowGroup ancestor.
                TableRowGroup group = component.getTableRowGroupAncestor();
                if (group != null) {
                    // Get column and group id.
                    String colId = component.getClientId(context);
                    String groupId = group.getClientId(context) + 
                        NamingContainer.SEPARATOR_CHAR;
                    try {
                        selectId = colId.substring(groupId.length(), 
                            colId.length()) + NamingContainer.SEPARATOR_CHAR +
                            component.getSelectId();
                    } catch (IndexOutOfBoundsException e) {
                        // Do nothing.
                    }
                }
            }
        }
        return selectId;
    }

    /**
     * Helper method to get the sort menu option value for the select column.
     *
     * @param component Table to be rendered.
     *
     * @return The select option value.
     */
    private String getSelectSortMenuOptionValue(Table component) {
        TableRowGroup group = component.getTableRowGroupChild();

        // Get first select column found.
        if (group != null) {
            Iterator kids = group.getTableColumnChildren();
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered() || col.getSelectId() == null) {
                    continue;
                }
                String value = getSelectSortMenuOptionValue(col);
                if (value != null) {
                    return value;
                }
            }
        } else {
            log("getSelectSortMenuOptionValue", //NOI18N
                "Cannot obtain select sort menu option value, TableRowGroup is null"); //NOI18N
        }
        return null;
    }

    /**
     * Helper method to get the sort menu option value for the select column.
     *
     * @param component TableColumn to be rendered.
     *
     * @return The select option value.
     */
    private String getSelectSortMenuOptionValue(TableColumn component) {
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered() || col.getSelectId() == null) {
                    continue;
                }
                String value = getSelectSortMenuOptionValue(col);
                if (value != null) {
                    return value;
                }
            }
        }

        // Return sort criteria key.
        SortCriteria criteria = component.getSortCriteria();
        return (criteria != null) ? criteria.getCriteriaKey() : null;
    }

    /**
     * Helper method to get Javascript array containing tool tips used for sort 
     * order menus.
     *
     * @param component Table to be rendered.
     * @param boolean Flag indicating descending tooltips.
     * @return A Javascript array containing tool tips.
     */
    private JSONArray getSortToolTipJavascript(Table component,
            boolean descending) {
        // Get undetermined tooltip.
        String tooltip = (descending)
            ? "table.sort.augment.undeterminedDescending" //NOI18N
            : "table.sort.augment.undeterminedAscending"; //NOI18N

        // Append array of ascending sort order tooltips.
        JSONArray json = new JSONArray();
        json.put(getTheme().getMessage(tooltip));

        // Use the first TableRowGroup child to obtain sort tool tip.
        TableRowGroup group = component.getTableRowGroupChild();
        if (group != null) {
            // For each TableColumn component, get the sort tool tip augment
            // based on the value for the align property of TableColumn.
            Iterator kids = group.getTableColumnChildren();
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                // Get tool tip augment.
                json.put(col.getSortToolTipAugment(descending));
            }
        } else {
            log("getSortToolTipJavascript", //NOI18N
                "Cannot obtain Javascript array of sort tool tips, TableRowGroup is null"); //NOI18N
        }
        return json;
    }

    /**
     * Helper method to get table column footer style class for TableColumn 
     * components.
     *
     * @param component TableColumn to be rendered.
     * @param level The current sort level.
     * @return The style class for the table column footer.
     */
    private String getTableColumnFooterStyleClass(TableColumn component,
            int level) {
        String styleClass = null;

        // Get appropriate style class.
        if (component.isSpacerColumn()) {
            styleClass = ThemeStyles.TABLE_COL_FOOTER_SPACER;
        } else if (level == 1) {
            styleClass = ThemeStyles.TABLE_COL_FOOTER_SORT;
        } else {
            styleClass = ThemeStyles.TABLE_COL_FOOTER;
        }
        return getTheme().getStyleClass(styleClass);
    }

    /** Helper method to get Theme objects. */
    private Theme getTheme() {
	return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
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

    /**
     * Helper method to render Javascript to Table components.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void renderJavascript(FacesContext context, Table component,
            ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderJavascript", "Cannot render Javascript, Table is null"); //NOI18N
            return;
        }

        try {
            // Append properties.
            StringBuffer buff = new StringBuffer(1024);
            JSONObject json = new JSONObject();
            json.put("id", component.getClientId(context));
            
            appendPanelProperties(context, component, json);
            appendFilterProperties(context, component, json);
            appendSortPanelProperties(context, component, json);
            appendGroupProperties(context, component, json);
            appendGroupPanelProperties(context, component, json);

            // Append JavaScript.
            buff.append(JavaScriptUtilities.getModule("_html.table"))
                .append("\n") // NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.table._init")) // NOI18N
                .append("(") //NOI18N
                .append(JSONUtilities.getString(json))
                .append(");"); //NOI18N

            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                buff.toString(), JavaScriptUtilities.isParseOnLoad());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to append panel properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void appendPanelProperties(FacesContext context, 
            Table component, JSONObject json) throws IOException, JSONException {
        if (component == null) {
            log("appendPanelProperties", //NOI18N
                "Cannot obtain properties, Table is null"); //NOI18N
            return;
        }

        // Don't invoke component.getEmbeddedPanels() here because it will
        // create new component instances which do not work with the action
        // listeners assigned to the rendered components.
        UIComponent panels = component.getFacet(Table.EMBEDDED_PANELS_ID);
        if (panels == null) {
            log("appendPanelProperties", //NOI18N
                "Cannot obtain panel properties, embedded panels facet is null"); //NOI18N
            return;
        }

        Theme theme = getTheme();
        String prefix = panels.getClientId(context) + NamingContainer.SEPARATOR_CHAR;
        
        // Append array of panel Ids.
        JSONArray ary1 = new JSONArray();
        ary1.put(prefix + TablePanels.SORT_PANEL_ID)
            .put(prefix + TablePanels.PREFERENCES_PANEL_ID)
            .put(prefix + TablePanels.FILTER_PANEL_ID);
        json.put("panelIds", ary1);

        // Don't invoke component.getTableActionsTop() here because it will
        // create new component instances which do not work with the action
        // listeners assigned to the rendered components.
        UIComponent actions = component.getFacet(Table.TABLE_ACTIONS_TOP_ID);
        if (actions == null) {
            log("appendPanelProperties", //NOI18N
                "Cannot obtain properties, facet is null"); //NOI18N
            return;
        }

        // Append array of focus Ids.
        JSONArray ary2 = new JSONArray();
        ary2.put((component.getSortPanelFocusId() != null)
                ? component.getSortPanelFocusId()
                : prefix + TablePanels.PRIMARY_SORT_COLUMN_MENU_ID)
            .put((component.getPreferencesPanelFocusId() != null)
                ? component.getPreferencesPanelFocusId() //NOI18N
                : JSONObject.NULL) //NOI18N
            .put((component.getFilterPanelFocusId() != null)
                ? component.getFilterPanelFocusId() //NOI18N
                : JSONObject.NULL); //NOI18N
        json.put("panelFocusIds", ary2);

        prefix = actions.getClientId(context) + NamingContainer.SEPARATOR_CHAR;

        // Append array of panel toggle Ids.
        JSONArray ary3 = new JSONArray();
        ary3.put(prefix + TableActions.SORT_PANEL_TOGGLE_BUTTON_ID)
            .put(prefix + TableActions.PREFERENCES_PANEL_TOGGLE_BUTTON_ID)
            .put((component.getFilterId() != null)
                ? component.getFilterId() : JSONObject.NULL); //NOI18N
        json.put("panelToggleIds", ary3);

        // Append array of toggle icons for open panels.
        JSONArray ary4 = new JSONArray();
        ary4.put(theme.getImagePath(ThemeImages.TABLE_SORT_PANEL_FLIP))
            .put(theme.getImagePath(ThemeImages.TABLE_PREFERENCES_PANEL_FLIP))
            .put(JSONObject.NULL);
        json.put("panelToggleIconsOpen", ary4);

        // Append array of toggle icons for closed panels.
        JSONArray ary5 = new JSONArray();
        ary5.put(theme.getImagePath(ThemeImages.TABLE_SORT_PANEL))
            .put(theme.getImagePath(ThemeImages.TABLE_PREFERENCES_PANEL))
            .put(JSONObject.NULL); //NOI18N
        json.put("panelToggleIconsClose", ary5);
    }

    /**
     * Helper method to append filter properties
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void appendFilterProperties(FacesContext context, 
            Table component, JSONObject json) throws IOException, JSONException {
        if (component == null) {
            log("apppendFilterProperties", //NOI18N
                "Cannot obtain properties, Table is null"); //NOI18N
            return;
        }

        Theme theme = getTheme();
        json.put("basicFilterStyleClass", theme.getStyleClass(ThemeStyles.MENU_JUMP))
            .put("customFilterStyleClass", theme.getStyleClass(ThemeStyles.TABLE_CUSTOM_FILTER_MENU))
            .put("customFilterOptionValue", Table.CUSTOM_FILTER)
            .put("customFilterAppliedOptionValue", Table.CUSTOM_FILTER_APPLIED);
    }

    /**
     * Helper method to append group properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void appendGroupProperties(FacesContext context, 
            Table component, JSONObject json) throws IOException, JSONException {
        if (component == null) {
            log("appendGroupProperties", //NOI18N
                "Cannot obtain properties, Table is null"); //NOI18N
            return;
        }

        Theme theme = getTheme();
        json.put("selectRowStyleClass", theme.getStyleClass(ThemeStyles.TABLE_SELECT_ROW));

        // Append array of select IDs.
        JSONArray ary1 = new JSONArray();
        Iterator kids = component.getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroup group = (TableRowGroup) kids.next();
            
            // Iterate over each TableColumn chlid to find selectId.
            String selectId = null;
            Iterator grandkids = group.getTableColumnChildren();
            while (grandkids.hasNext()) {
                TableColumn col = (TableColumn) grandkids.next();
                if (!col.isRendered()) {
                    continue;
                }
                selectId = getSelectId(context, col);
                if (selectId != null) {
                    break;
                }
            }
            // Append selectId, if applicable.
            ary1.put((selectId != null) ? selectId : JSONObject.NULL); //NOI18N
        }
        json.put("selectIds", ary1);

        // Append array of TableRowGroup IDs.
        JSONArray ary2 = new JSONArray();
        kids = component.getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroup group = (TableRowGroup) kids.next();
            ary2.put(group.getClientId(context));
        }
        json.put("groupIds", ary2);

        // Append array of row IDs.
        JSONArray ary3 = new JSONArray();
        kids = component.getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroup group = (TableRowGroup) kids.next();
            RowKey[] rowKeys = group.getRenderedRowKeys(); // Only rendered rows.

            // Append an array of row ids for each TableRowGroup child.
            JSONArray tmp = new JSONArray();
            ary3.put(tmp);
            if (rowKeys != null) {
                for (int i = 0; i < rowKeys.length; i++) {
                    tmp.put(rowKeys[i].getRowId());
                }
            } else {
                tmp.put(JSONObject.NULL); // TableRowGroup may have been empty. //NOI18N
            }
        }
        json.put("rowIds", ary3);

        // Append array of hidden selected row counts.
        JSONArray ary4 = new JSONArray();
        kids = component.getTableRowGroupChildren();
        while (kids.hasNext()) {
            TableRowGroup group = (TableRowGroup) kids.next();

            // Don't bother with calculations if this property is not set.
            if (component.isHiddenSelectedRows()) {
                ary4.put(group.getHiddenSelectedRowsCount());
            } else {
                ary4.put(0); //NOI18N
            }
        }
        json.put("hiddenSelectedRowCounts", ary4)
            .put("hiddenSelectionsMsg", theme.getMessage("table.confirm.hiddenSelections"))
            .put("totalSelectionsMsg", theme.getMessage("table.confirm.totalSelections"))
            .put("deleteSelectionsMsg", theme.getMessage("table.confirm.deleteSelections"));
    }

    /**
     * Helper method to append group panel properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void appendGroupPanelProperties(FacesContext context, 
            Table component, JSONObject json) throws IOException, JSONException {
        if (component == null) {
            log("appendGroupPanelProperties", //NOI18N
                "Cannot obtain properties, Table is null"); //NOI18N
            return;
        }

        // Get ID prefix for TableHeader components.
        String prefix = TableRowGroup.GROUP_HEADER_ID +
            NamingContainer.SEPARATOR_CHAR; 

        Theme theme = getTheme();
        json.put("columnFooterId", TableRowGroup.COLUMN_FOOTER_BAR_ID)
            .put("columnHeaderId", TableRowGroup.COLUMN_HEADER_BAR_ID)
            .put("tableColumnFooterId", TableRowGroup.TABLE_COLUMN_FOOTER_BAR_ID)
            .put("groupFooterId", TableRowGroup.GROUP_FOOTER_BAR_ID)
            .put("groupPanelToggleButtonId", prefix + TableHeader.GROUP_PANEL_TOGGLE_BUTTON_ID)
            .put("groupPanelToggleButtonToolTipOpen", theme.getMessage("table.group.collapse"))
            .put("groupPanelToggleButtonToolTipClose", theme.getMessage("table.group.expand"))
            .put("groupPanelToggleIconOpen", theme.getImagePath(
		ThemeImages.TABLE_GROUP_PANEL_FLIP))
            .put("groupPanelToggleIconClose", theme.getImagePath(
		ThemeImages.TABLE_GROUP_PANEL))
            .put("warningIconId", prefix + TableHeader.WARNING_ICON_ID)
            .put("warningIconOpen", theme.getImagePath(ThemeImages.DOT))
            .put("warningIconClose", theme.getImagePath(
		ThemeImages.ALERT_WARNING_SMALL))
            .put("warningIconToolTipOpen", JSONObject.NULL) // No tooltip for place holder icon.
            .put("warningIconToolTipClose", theme.getMessage("table.group.warning"))     
            .put("collapsedHiddenFieldId", prefix + TableHeader.COLLAPSED_HIDDEN_FIELD_ID)
            .put("selectMultipleToggleButtonId", prefix + TableHeader.SELECT_MULTIPLE_TOGGLE_BUTTON_ID)
            .put("selectMultipleToggleButtonToolTip", theme.getMessage("table.group.selectMultiple"))
            .put("selectMultipleToggleButtonToolTipSelected", theme.getMessage("table.group.deselectMultiple"));
    }

    /**
     * Helper method to append sort panel properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    private void appendSortPanelProperties(FacesContext context, 
            Table component, JSONObject json) throws IOException, JSONException {
        if (component == null) {
            log("appendSortPanelProperties", //NOI18N
                "Cannot obtain properties, Table is null"); //NOI18N
            return;
        }

        // Don't invoke component.getEmbeddedPanels() here because it will
        // create new component instances which do not work with the action
        // listeners assigned to the rendered components.
        UIComponent panels = component.getFacet(Table.EMBEDDED_PANELS_ID);
        if (panels == null) {
            log("appendSortPanelProperties", //NOI18N
                "Cannot obtain properties, Embedded panels facet is null"); //NOI18N
            return;
        }

        Theme theme = getTheme();
        String prefix = panels.getClientId(context) + NamingContainer.SEPARATOR_CHAR;

        // Append array of sort column menu Ids.
        JSONArray ary1 = new JSONArray();
        ary1.put(prefix + TablePanels.PRIMARY_SORT_COLUMN_MENU_ID)
            .put(prefix + TablePanels.SECONDARY_SORT_COLUMN_MENU_ID)
            .put(prefix + TablePanels.TERTIARY_SORT_COLUMN_MENU_ID);
        json.put("sortColumnMenuIds", ary1);

        // Append array of sort order menu Ids.
        JSONArray ary2 = new JSONArray();
        ary2.put(prefix + TablePanels.PRIMARY_SORT_ORDER_MENU_ID)
            .put(prefix + TablePanels.SECONDARY_SORT_ORDER_MENU_ID)
            .put(prefix + TablePanels.TERTIARY_SORT_ORDER_MENU_ID);
        json.put("sortOrderMenuIds", ary2);

        // Append array of sort order tooltips.
        JSONArray ary3 = new JSONArray();
        ary3.put(theme.getMessage("table.panel.primarySortOrder")) //NOI18N
            .put(theme.getMessage("table.panel.secondarySortOrder")) //NOI18N
            .put(theme.getMessage("table.panel.tertiarySortOrder")); //NOI18N
        json.put("sortOrderToolTips", ary3);
        
        // Append sort menu option value for select column and paginated flag.
        String value = getSelectSortMenuOptionValue(component);
        TableRowGroup group = component.getTableRowGroupChild();
        json.put("sortOrderToolTipsAscending", getSortToolTipJavascript(component, false))
            .put("sortOrderToolTipsDescending", getSortToolTipJavascript(component, true))
            .put("duplicateSelectionMsg", theme.getMessage(
                "table.panel.duplicateSelectionError"))
            .put("missingSelectionMsg", theme.getMessage(
                "table.panel.missingSelectionError"))
            .put("selectSortMenuOptionValue", (value != null) ? value : null)
            .put("hiddenSelectedRows", component.isHiddenSelectedRows())
            .put("paginated", group != null ? group.isPaginated() : false);
    }
}
