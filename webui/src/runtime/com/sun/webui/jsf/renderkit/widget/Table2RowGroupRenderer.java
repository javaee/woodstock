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
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

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
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        Table2RowGroup group = (Table2RowGroup) component;

        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.table2RowGroup"));

        if (group.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                "widget.jsfx.table2RowGroup"));
        }
        return json;
    }

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        Table2RowGroup group = (Table2RowGroup) component;
        String templatePath = group.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("first", group.getFirst())
            .put("maxRows", group.getRows())
            .put("totalRows", group.getRowCount())
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : getTheme().getPathToTemplate(ThemeTemplates.TABLE2ROWGROUP));

        // Add properties.
        addAttributeProperties(attributes, group, json);
        setCoreProperties(context, group, json);
        setColumnProperties(context, group, json);
        setFooterProperties(context, group, json);
        setHeaderProperties(context, group, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType() {
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
        Iterator kids = component.getTable2ColumnChildren();
        while (kids.hasNext()) {
            Table2Column col = (Table2Column) kids.next();
            if (col.isRendered()) {
                WidgetUtilities.addProperties(jArray,
                    WidgetUtilities.renderComponent(context, col));
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
        // Get footer facet.
        UIComponent facet = component.getFacet(Table2RowGroup.FOOTER_FACET);
        if (facet != null && facet.isRendered()) {
            WidgetUtilities.addProperties(json, "footerText", 
                WidgetUtilities.renderComponent(context, facet));
        } else {
            // Add footer text.
            json.put("footerText", component.getFooterText());
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
        // Get header facet.
        UIComponent facet = component.getFacet(Table2RowGroup.HEADER_FACET);
        if (facet != null && facet.isRendered()) {
            WidgetUtilities.addProperties(json, "headerText", 
                WidgetUtilities.renderComponent(context, facet));
        } else {
            // Add header text.
            json.put("headerText", component.getHeaderText());
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}
