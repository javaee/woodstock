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
import com.sun.data.provider.SortCriteria;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableColumn;
import com.sun.webui.jsf.component.TableHeader;
import com.sun.webui.jsf.component.TableRowGroup;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.FocusManager;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * This class renders TableHeader components.
 * <p>
 * Note: To see the messages logged by this class, set the following global
 * defaults in your JDK's "jre/lib/logging.properties" file.
 * </p><p><pre>
 * java.util.logging.ConsoleHandler.level = FINE
 * com.sun.webui.jsf.renderkit.html.TableHeaderRenderer.level = FINE
 * </pre></p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.TableHeader"))
public class TableHeaderRenderer extends javax.faces.render.Renderer {
    /**
     * The set of String pass-through attributes to be rendered.
     * <p>
     * Note: The WIDTH, HEIGHT, and BGCOLOR attributes are all deprecated (in
     * the HTML 4.0 spec) in favor of style sheets. In addition, the DIR and 
     * LANG attributes are not cuurently supported.
     * </p>
     */
    private static final String stringAttributes[] = {
        "abbr", //NOI18N
        "align", //NOI18N
        "axis", //NOI18N
        "bgColor", //NOI18N
        "char", //NOI18N
        "charOff", //NOI18N
        "dir", //NOI18N
        "headers", //NOI18N
        "height", //NOI18N
        "lang", //NOI18N
        "onClick", //NOI18N
        "onDblClick", //NOI18N
        "onKeyDown", //NOI18N
        "onKeyPress", //NOI18N
        "onKeyUp", //NOI18N
        "onMouseDown", //NOI18N
        "onMouseUp", //NOI18N
        "onMouseMove", //NOI18N
        "onMouseOut", //NOI18N
        "onMouseOver", //NOI18N
        "scope", //NOI18N
        "style", //NOI18N
        "valign", //NOI18N
        "width"}; //NOI18N

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

        TableHeader header = (TableHeader) component;
        ResponseWriter writer = context.getResponseWriter();
        renderEnclosingTagStart(context, header, writer);
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

        TableHeader header = (TableHeader) component;
        ResponseWriter writer = context.getResponseWriter();

        // Render headers.
        if (header.isGroupHeader()) {
            renderGroupHeader(context, header, writer);
        } else if (header.isSelectHeader()) {
            renderSelectHeader(context, header, writer);
        } else if (header.isSortHeader()) {
            renderSortHeader(context, header, writer);
        } else {
            renderColumnHeader(context, header, writer);
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

        TableHeader header = (TableHeader) component;
        ResponseWriter writer = context.getResponseWriter();
        renderEnclosingTagEnd(context, header, writer);
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
    // Header methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render column headers for TableHeader components.
     *
     * @param context FacesContext for the current request.
     * @param component TableHeader to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderColumnHeader(FacesContext context,
            TableHeader component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderColumnHeader", //NOI18N
                "Cannot render column header, TableHeader is null"); //NOI18N
            return;
        }

        // Get facet.
        TableColumn col = component.getTableColumnAncestor();
        UIComponent facet = (col != null)
            ? col.getFacet(TableColumn.HEADER_FACET) : null;
        if (facet != null && facet.isRendered()) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            // Get style for nested column headers.
            TableColumn parent = (col != null) ? col.getTableColumnAncestor() : null;
            Iterator kids = (col != null) ? col.getTableColumnChildren() : null;
            String styleClass = (kids != null && kids.hasNext())
                ? ThemeStyles.TABLE_MULTIPLE_HEADER_TEXT
                : ThemeStyles.TABLE_HEADER_TEXT;

            //  If TableColumn has a parent, then it is a leaf node.
            if (parent != null) {
                writer.writeText("\n", null); //NOI18N
                writer.startElement("table", component); //NOI18N
                writer.writeAttribute("class", //NOI18N
                    getTheme().getStyleClass(ThemeStyles.TABLE_HEADER_TABLE), null);
                writer.writeAttribute("border", "0", null); //NOI18N
                writer.writeAttribute("cellpadding", "0", "cellPadding"); //NOI18N
                writer.writeAttribute("cellspacing", "0", "cellSpacing"); //NOI18N
                writer.writeText("\n", null); //NOI18N
                writer.startElement("tr", component); //NOI18N
                writer.writeText("\n", null); //NOI18N
                writer.startElement("td", component); //NOI18N
                writer.startElement("span", component); //NOI18N
                writer.writeAttribute("class", //NOI18N
                    getTheme().getStyleClass(styleClass), null);

                // Render header text.
                if (col != null && col.getHeaderText() != null) {
                    writer.writeText(col.getHeaderText(), null); //NOI18N
                }
                writer.endElement("span"); //NOI18N
                writer.endElement("td"); //NOI18N
                writer.endElement("tr"); //NOI18N
                writer.endElement("table"); //NOI18N
            } else {
                writer.startElement("span", component); //NOI18N
                writer.writeAttribute("class", //NOI18N
                    getTheme().getStyleClass(styleClass), null);

                // Render header text.
                if (col != null && col.getHeaderText() != null) {
                    writer.writeText(col.getHeaderText(), null); //NOI18N
                }
                writer.endElement("span"); //NOI18N
            }
        }
    }

    /**
     * Render select column headers for TableHeader components.
     *
     * @param context FacesContext for the current request.
     * @param component TableHeader to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderSelectHeader(FacesContext context,
            TableHeader component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderSelectHeader", //NOI18N
                "Cannot render select header, TableHeader is null"); //NOI18N
            return;
        }

        // Get facet.
        TableColumn col = component.getTableColumnAncestor();
        UIComponent facet = (col != null)
            ? col.getFacet(TableColumn.HEADER_FACET) : null;
        if (facet != null && facet.isRendered()) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            // Render layout table.
            writer.writeText("\n", null); //NOI18N
            writer.startElement("table", component); //NOI18N
            writer.writeAttribute("class", //NOI18N
                getTheme().getStyleClass(ThemeStyles.TABLE_HEADER_TABLE), null);
            writer.writeAttribute("border", "0", null); //NOI18N
            writer.writeAttribute("cellpadding", "0", "cellPadding"); //NOI18N
            writer.writeAttribute("cellspacing", "0", "cellSpacing"); //NOI18N
            writer.writeText("\n", null); //NOI18N
            writer.startElement("tr", component); //NOI18N

            // Render sort title.
            writer.writeText("\n", null); //NOI18N
            writer.startElement("td", component); //NOI18N
            writer.writeAttribute("align", component.getAlign(), null); //NOI18N
            RenderingUtilities.renderComponent(
                component.getSelectSortButton(), context);
            writer.endElement("td"); //NOI18N

            // Get sort button.
            UIComponent child = null;
            if (component.getSortLevel() > 0) {
                child = component.getToggleSortButton();
            } else if (component.getSortCount() > 0) {
                // Render add button when any sort has been applied.
                child = component.getAddSortButton();
            }

            // Render sort button.
            if (child != null) {
                writer.writeText("\n", null); //NOI18N
                writer.startElement("td", component); //NOI18N
                RenderingUtilities.renderComponent(child, context);
                writer.endElement("td"); //NOI18N

		// Set the focus to the current sort control.
		//
		setSortFocus(context, component, child);
            }
            writer.endElement("tr"); //NOI18N
            writer.endElement("table"); //NOI18N

        }
    }

    /**
     * Render sort column headers for TableHeader components.
     *
     * @param context FacesContext for the current request.
     * @param component TableHeader to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderSortHeader(FacesContext context, TableHeader component,
            ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderSortHeader", //NOI18N
                "Cannot render sort header, TableHeader is null"); //NOI18N
            return;
        }

        // Get facet.
        TableColumn col = component.getTableColumnAncestor();
        UIComponent facet = (col != null)
            ? col.getFacet(TableColumn.HEADER_FACET) : null;
        if (facet != null && facet.isRendered()) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            // Render layout table so sort link/button may wrap properly.
            writer.writeText("\n", null); //NOI18N
            writer.startElement("table", component); //NOI18N
            writer.writeAttribute("class", //NOI18N
                getTheme().getStyleClass(ThemeStyles.TABLE_HEADER_TABLE), null);
            writer.writeAttribute("border", "0", null); //NOI18N
            writer.writeAttribute("cellpadding", "0", "cellPadding"); //NOI18N
            writer.writeAttribute("cellspacing", "0", "cellSpacing"); //NOI18N
            writer.writeText("\n", null); //NOI18N
            writer.startElement("tr", component); //NOI18N

            // Render sort link.
            if (col != null && col.getHeaderText() != null) {
                writer.writeText("\n", null); //NOI18N
                writer.startElement("td", component); //NOI18N
                writer.writeAttribute("align", //NOI18N
                    component.getAlign(), null); //NOI18N
                RenderingUtilities.renderComponent(
                    component.getPrimarySortLink(), context);
                writer.endElement("td"); //NOI18N
            }

            // Get sort button.
            UIComponent child = null;
            if (component.getSortLevel() > 0) {
                child = component.getToggleSortButton();
            } else if (component.getSortCount() > 0) {
                // Render add button when any sort has been applied.
                child = component.getAddSortButton();
            } else {
                // Render primary sort button when no sorting has been applied.
                child = component.getPrimarySortButton();
            }
	    if (child != null) {
		// Set the focus to the current sort control.
		//
		setSortFocus(context, component, child);
	    }

            // Render sort button.
            writer.writeText("\n", null); //NOI18N
            writer.startElement("td", component); //NOI18N
            RenderingUtilities.renderComponent(child, context);
            writer.endElement("td"); //NOI18N
            writer.endElement("tr"); //NOI18N
            writer.endElement("table"); //NOI18N
        }
    }

    /**
     * Render group header for TableHeader components.
     *
     * @param context FacesContext for the current request.
     * @param component TableHeader to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderGroupHeader(FacesContext context,
            TableHeader component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderGroupHeader", //NOI18N
                "Cannot render group header, TableHeader is null"); //NOI18N
            return;
        }

        TableRowGroup group = component.getTableRowGroupAncestor();
        if (group == null) {
            log("renderGroupHeader", //NOI18N
                "Cannot render group header, TableRowGroup is null"); //NOI18N
            return;
        }

        // Header facet should override header text.
        UIComponent facet = group.getFacet(TableRowGroup.HEADER_FACET);
        boolean renderHeader = facet != null && facet.isRendered();
        boolean renderText = group.getHeaderText() != null && !renderHeader;
        
        // Don't render controls for an empty group.        
        boolean renderTableRowGroupControls = !isEmptyGroup(component)
            && (group != null && group.isSelectMultipleToggleButton()
            || group.isGroupToggleButton());

        if (renderText || renderTableRowGroupControls) {
            // Render alignment span.
            Theme theme = getTheme();
            writer.writeText("\n", null); //NOI18N
            writer.startElement("span", component); //NOI18N
            writer.writeAttribute("class", //NOI18N
                theme.getStyleClass(ThemeStyles.TABLE_GROUP_HEADER_LEFT), null);

            // Render table row group controls.
            if (renderTableRowGroupControls) {
                // Render select multiple toggle button.
                if (group.isSelectMultipleToggleButton()) {
                    writer.writeText("\n", null); //NOI18N
                    RenderingUtilities.renderComponent(
                        component.getSelectMultipleToggleButton(), context);
                }

                // Do not render warning icon unless selectId is used.
                if (getSelectId(component) != null) {
                    writer.writeText("\n", null); //NOI18N
                    writer.startElement("span", component); //NOI18N
                    writer.writeAttribute("class", //NOI18N
                        theme.getStyleClass(ThemeStyles.TABLE_GROUP_HEADER_IMAGE),
                        null);
                    writer.writeText("\n", null); //NOI18N
                    RenderingUtilities.renderComponent(
                        component.getWarningIcon(), context);
                    writer.endElement("span"); //NOI18N
                }

                // Render group toggle button.
                if (group.isGroupToggleButton()) {
                    writer.writeText("\n", null); //NOI18N
                    RenderingUtilities.renderComponent(
                        component.getGroupPanelToggleButton(), context);

                    // Render hidden field for collapsed property.
                    RenderingUtilities.renderComponent(
                        component.getCollapsedHiddenField(), context);
                }

                // Add space between controls and facet text.
                if (!renderText) {
                    writer.write("&nbsp;"); //NOI18N
                }
            }

            // Render text.
            if (renderText) {
                writer.writeText("\n", null); //NOI18N
                writer.startElement("span", component); //NOI18N
                writer.writeAttribute("class", //NOI18N
                    theme.getStyleClass(ThemeStyles.TABLE_GROUP_HEADER_TEXT),
                    null);
                writer.writeText(group.getHeaderText(), null);
                writer.endElement("span"); //NOI18N
            }
            writer.endElement("span"); //NOI18N
        } else {
            log("renderGroupHeader", //NOI18N
                "Group controls not rendered, empty group found"); //NOI18N
        }

        // Render facet.
        if (renderHeader) {
            RenderingUtilities.renderComponent(facet, context);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Enclosing tag methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Render enclosing tag for TableHeader components.
     *
     * @param context FacesContext for the current request.
     * @param component TableHeader to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagStart(FacesContext context,
            TableHeader component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderEnclosingTagStart", //NOI18N
                "Cannot render enclosing tag, TableHeader is null"); //NOI18N
            return;
        }

        writer.writeText("\n", null); //NOI18N
        writer.startElement("th", component); //NOI18N
        writer.writeAttribute("id", component.getClientId(context), null); //NOI18N

        // Render style class.
        String extraHtml = RenderingUtilities.renderStyleClass(context, writer,
            component, getStyleClass(component), component.getExtraHtml());

        // Render colspan.
        if (component.getColSpan() > -1
                && (extraHtml == null || extraHtml.indexOf("colspan=") == -1)) { //NOI18N
            writer.writeAttribute("colspan", //NOI18N
                Integer.toString(component.getColSpan()), null); //NOI18N
        }

        // Render rowspan.
        if (component.getRowSpan() > -1
                && (extraHtml == null || extraHtml.indexOf("rowspan=") == -1)) { //NOI18N
            writer.writeAttribute("rowspan", //NOI18N
                Integer.toString(component.getRowSpan()), null); //NOI18N
        }

        // Render nowrap.
        if (component.isNoWrap()
                && (extraHtml == null || extraHtml.indexOf("nowrap=") == -1)) { //NOI18N
            writer.writeAttribute("nowrap", "nowrap", null); //NOI18N
        }

        // Render tooltip.
        if (component.getToolTip() != null
                && (extraHtml == null || extraHtml.indexOf("title=") == -1)) { //NOI18N
            writer.writeAttribute("title", component.getToolTip(), "toolTip"); //NOI18N
        }

        // Render pass through attributes.
        RenderingUtilities.writeStringAttributes(component, writer, 
            stringAttributes, extraHtml);
    }

    /**
     * Render enclosing tag for TableHeader components.
     *
     * @param context FacesContext for the current request.
     * @param component TableHeader to be rendered.
     * @param writer ResponseWriter to which the component should be rendered.
     *
     * @exception IOException if an input/output error occurs.
     */
    protected void renderEnclosingTagEnd(FacesContext context,
            TableHeader component, ResponseWriter writer) throws IOException {
        if (component == null) {
            log("renderEnclosingTagEnd", //NOI18N
                "Cannot render enclosing tag, TableHeader is null"); //NOI18N
            return;
        }
        writer.endElement("th"); //NOI18N
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Helper method to get the selectId from TableRowGroup components.
     *
     * @param component The TableHeader component to be rendered.
     * @return The first selectId property found.
     */
    private String getSelectId(TableHeader component) {
        String selectId = null;
        TableRowGroup group = (component != null)
            ? component.getTableRowGroupAncestor() : null;
        if (group == null) {
            log("getSelectId", //NOI18N
                "Cannot obtain select Id, TableRowGroup is null"); //NOI18N
            return selectId;
        }

        Iterator kids = group.getTableColumnChildren();
        while (kids.hasNext()) {
            TableColumn col = (TableColumn) kids.next();
            if (!col.isRendered()) {
                continue;
            }
            selectId = getSelectId(col);
            if (selectId != null) {
                break;
            }
        }
        return selectId;
    }

    /**
     * Get the selectId from nested TableColumn components.
     *
     * @param component TableColumn to be rendered.
     * @return The first selectId property found.
     */
    private String getSelectId(TableColumn component) {
        if (component == null) {
            log("getSelectId", //NOI18N
                "Cannot obtain select Id, TableColumn is null"); //NOI18N
            return null;
        }
    
        // Render nested TableColumn children.
        Iterator kids = component.getTableColumnChildren();
        if (kids.hasNext()) {
            while (kids.hasNext()) {
                TableColumn col = (TableColumn) kids.next();
                if (!col.isRendered()) {
                    continue;
                }
                String selectId = getSelectId(col);
                if (selectId != null) {
                    return selectId;
                }
            }
        }
        return component.getSelectId();
    }

    /**
     * Helper method to get style class for TableHeader components.
     *
     * @param component TableHeader to be rendered.
     * @return The style class.
     */
    private String getStyleClass(TableHeader component) {
        String styleClass = null;
        if (component == null) {
            log("getStyleClass", //NOI18N
                "Cannot obtain style class, TableHeader is null"); //NOI18N
            return styleClass;
        }

        // Get style class.
        if (component.isGroupHeader()) {
            styleClass = ThemeStyles.TABLE_GROUP_HEADER;
        } else {
            TableColumn col = component.getTableColumnAncestor();
            if (col != null && col.isSpacerColumn()) {
                styleClass = ThemeStyles.TABLE_TD_SPACER;
            } else {
                // Test for nested column headers.
                TableColumn parent = (col != null) ? col.getTableColumnAncestor() : null;
                Iterator kids = (col != null) ? col.getTableColumnChildren() : null;
                if (kids != null && kids.hasNext()) {
                    // If TableColumn has kids, then it is a root node.
                    styleClass = ThemeStyles.TABLE_MULTIPLE_HEADER_ROOT;
                } else if (component.getSortLevel() == 1) {
                    if (parent != null) {
                        // If TableColumn has a parent, then it is a leaf node.
                        styleClass = ThemeStyles.TABLE_MULTIPLE_HEADER_SORT;
                    } else if (component.isSelectHeader()) {
                        styleClass = ThemeStyles.TABLE_HEADER_SELECTCOL_SORT;
                    } else {
                        styleClass = ThemeStyles.TABLE_HEADER_SORT;
                    }
                } else {
                    if (parent != null) {
                        // If TableColumn has a parent, then it is a leaf node.
                        styleClass = ThemeStyles.TABLE_MULTIPLE_HEADER;
                    } else if (component.isSelectHeader()) {
                        styleClass = ThemeStyles.TABLE_HEADER_SELECTCOL;
                    } else {
                        styleClass = ThemeStyles.TABLE_HEADER;
                    }
                }
            }
        }
        return getTheme().getStyleClass(styleClass);
    }

    /** Helper method to get Theme objects. */
    private Theme getTheme() {
	return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

    /**
     * Helper method to determine if group is empty.
     *
     * @return true if empty, else false.
     */
    private boolean isEmptyGroup(TableHeader component) {
        boolean result = false;
        if (component == null) {
            log("isEmptyGroup", //NOI18N
                "Cannot determine if group is empty, TableHeader is null"); //NOI18N
            return result;
        }

        TableRowGroup group = component.getTableRowGroupAncestor();
        if (group != null) {
            result = (group.getRowCount() == 0);
        } else {
            log("isEmptyGroup", //NOI18N
                "Cannot determine if group is empty, TableRowGroup is null"); //NOI18N
        }
        return result;
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
     * Set focus when sort buttons are displayed.
     */
    private void setSortFocus(FacesContext context, TableHeader tableHeader,
	    UIComponent sortControl) {

        // Get the client ID of the last component to have focus.
	// If it's null do nothing.
	//
        String id = FocusManager.getRequestFocusElementId(context);
        if (id == null) {
            return;
        }

        // Get prefix for all IDs.
        String prefix = tableHeader.getClientId(context) + 
		NamingContainer.SEPARATOR_CHAR;

        // Set component focus if any match was found. Don't include select
        // sort button here as that component does not change.
	//
        if (id.equals(prefix + TableHeader.ADD_SORT_BUTTON_ID)
                || id.equals(prefix + TableHeader.PRIMARY_SORT_BUTTON_ID)
                || id.equals(prefix + TableHeader.TOGGLE_SORT_BUTTON_ID)) {
            FocusManager.setRequestFocusElementId(context, 
		RenderingUtilities.getFocusElementId(context, sortControl));
        }        
    }
}
