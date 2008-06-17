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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;

import com.sun.data.provider.RowKey;
import com.sun.webui.jsf.component.Table2;
import com.sun.webui.jsf.component.Table2Column;
import com.sun.webui.jsf.component.Table2RowGroup;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table2RowGroup components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Table2RowGroup",
    componentFamily="com.sun.webui.jsf.Table2RowGroup"))
public class Table2RowGroupRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     * <p>
     * Note: The BGCOLOR attribute is deprecated (in the HTML 4.0 spec) in favor
     * of style sheets. In addition, the DIR and LANG attributes are not
     * cuurently supported.
     * </p>
     */
    private static final String stringAttributes[] = {
        "align",
        "bgColor",
        "char",
        "charOff",
        "dir",
        "lang",
        "onClick",
        "onDblClick",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseUp",
        "onMouseMove",
        "onMouseOut",
        "onMouseOver",
        "style",
        "valign"};

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs
     * @exception JSONException if a key/value error occurs
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
	if (!(component instanceof Table2RowGroup)) {
	    throw new IllegalArgumentException(
                "Table2RowGroupRenderer can only render Table2RowGroup components.");
        }
        Table2RowGroup group = (Table2RowGroup) component;
        String templatePath = group.getHtmlTemplate(); // Get HTML template.
        String filterText = null;
        if (group.getParent() instanceof Table2) {
            filterText = ((Table2) (group.getParent())).getFilterText();
        }
        JSONObject json = new JSONObject();
        json.put("className", group.getStyleClasses())
            .put("first", group.getFirst())
            .put("page", group.getPage())
            .put("maxRows", group.getRows())
            .put("title", group.getToolTip())
            .put("totalRows", group.getRowCount())
            .put("paginationControls",group.isPaginationControls())
            .put("filterText", filterText)
            .put("visible", group.isVisible());
        
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, group, json);
        setColumnProperties(context, group, json);
        setHeaderProperties(context, group, json);
        setRowProperties(context, group, json);

        return json;
    }


    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "tableRowGroup"; 
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain column properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2RowGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setColumnProperties(FacesContext context, Table2RowGroup component,
            JSONObject json) throws IOException, JSONException {
        JSONArray jArray = new JSONArray();
        json.put("columns", jArray);

        // Add properties for each Table2Column child.
        Iterator kids = component.getTableColumnChildren();
        while (kids.hasNext()) {
            Table2Column col = (Table2Column) kids.next();
            if (col.isRendered()) {
                jArray.put(WidgetUtilities.renderComponent(context, col));
            }
        }
    }

    /** 
     * Helper method to obtain header properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2RowGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setHeaderProperties(FacesContext context, Table2RowGroup component,
            JSONObject json) throws IOException, JSONException {
        // Add header text.
        json.put("headerText", component.getHeaderText());
    }

    /**
     * Helper method to render rows.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected void setRowProperties(FacesContext context, Table2RowGroup component,
            JSONObject json) throws IOException, JSONException {
        // Add rows.
        JSONArray jsonRows = new JSONArray();
        json.put("rows", jsonRows);
        // Render empty data message.
        if (component.getRowCount() == 0) {
            return;
        }

        // To do: Need algorithm to retrieve n*2 rows?
        int maxRows = component.getRows();
        component.setRows(maxRows * 2);

        // Get rendered row keys.
        RowKey[] rowKeys = component.getRenderedRowKeys();
        if (rowKeys == null) {
            return;
        }        

        try {
            // Iterate over the rendered RowKey objects.
            for (int i = 0; i < rowKeys.length; i++) {
                component.setRowKey(rowKeys[i]);
                if (!component.isRowAvailable()) {
                    break;
                }

                // Render Table2Column components.
                JSONArray jsonCols = new JSONArray();
                Iterator kids = component.getTableColumnChildren();
                while (kids.hasNext()) {
                    Table2Column col = (Table2Column) kids.next();
                    if (!col.isRendered()) {
                        continue;
                    }
                    // Render Table2Column children.
                    Iterator grandKids = col.getChildren().iterator();
                    UIComponent comp = null;                    
                    while (grandKids.hasNext()) {
                        comp = (UIComponent) grandKids.next(); 
                        if (comp instanceof Table2Column) {
                            getColumnChildren(context, comp, jsonCols);                            
                        } else {                            
                            jsonCols.put(WidgetUtilities.renderComponent(context,
                            comp));                            
                        }    
                    }
                }
                jsonRows.put(jsonCols);
            }
            component.setRowKey(null); // Clean up.
        } catch(JSONException e) {
            e.printStackTrace();
        }

        // To do: Need algorithm to retrieve n*2 rows?
        component.setRows(maxRows);
    }
        
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Helper method to render rows.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    private void getColumnChildren(FacesContext context, UIComponent comp,
            JSONArray json) throws IOException, JSONException {
        Iterator grandColKids = comp.getChildren().iterator();        
        while (grandColKids.hasNext()) {
            UIComponent col = (UIComponent)grandColKids.next();
            if (col instanceof Table2Column) {
                getColumnChildren(context, col, json);
            } else {                  
                json.put(WidgetUtilities.renderComponent(context, col));                
            }
        }
    }
}
