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
import com.sun.webui.jsf.component.Table2Column;
import com.sun.webui.jsf.component.Table2RowGroup;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;

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
    private static final String attributes[] = {
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
     * Get the Dojo module required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getModule(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getModuleName("widget.table2RowGroup");
    }

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

        JSONObject json = new JSONObject();
        json.put("first", group.getFirst())
            .put("maxRows", group.getRows())
            .put("totalRows", group.getRowCount());

        // Add attributes.
        JSONUtilities.addProperties(attributes, group, json);
        setColumnProperties(context, group, json);
        setFooterProperties(context, group, json);
        setHeaderProperties(context, group, json);
        setRowProperties(context, group, json);

        return json;
    }


    /**
     * Get the name of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetName(FacesContext context, UIComponent component) {
        return JavaScriptUtilities.getNamespace("table2RowGroup");
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
     * Helper method to obtain footer properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2RowGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setFooterProperties(FacesContext context, Table2RowGroup component,
            JSONObject json) throws IOException, JSONException {
        // Add footer text.
        json.put("footerText", component.getFooterText());
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

        // Add rows.
        JSONArray jsonRows = new JSONArray();
        json.put("rows", jsonRows);

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
                    while (grandKids.hasNext()) {
                        jsonCols.put(WidgetUtilities.renderComponent(context, 
                            (UIComponent) grandKids.next()));
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
}
