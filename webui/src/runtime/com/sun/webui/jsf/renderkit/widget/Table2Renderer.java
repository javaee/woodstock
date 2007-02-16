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

import com.sun.webui.jsf.component.Table2;
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
 * This class renders Table2 components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Table2",
    componentFamily="com.sun.webui.jsf.Table2"))
public class Table2Renderer extends RendererBase {
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
        "dir",
        "frame",
        "lang",
        "onClick",
        "onDblClick",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseMove",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "rules",
        "summary"};

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.table2"));
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
        Table2 table = (Table2) component;
        String templatePath = table.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("templatePath", (templatePath != null)
                ? templatePath 
                : getTheme().getPathToTemplate(ThemeTemplates.TABLE2))
            .put("width", table.getWidth());

        // Add properties.
        addAttributeProperties(attributes, table, json);
        setCoreProperties(context, table, json);
        setRowGroupProperties(context, table, json);
        setActionsProperties(context, table, json);
        setTitleProperties(context, table, json);

        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain actions properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2 to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setActionsProperties(FacesContext context, Table2 component,
            JSONObject json) throws IOException, JSONException {
        // Get actions facet.
        UIComponent facet = component.getFacet(Table2.ACTIONS_TOP_FACET);
        if (facet != null && facet.isRendered()) {
            WidgetUtilities.addProperties(json, "actions",
                WidgetUtilities.renderComponent(context, facet));
        }
    }

    /** 
     * Helper method to obtain row group properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2 to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setRowGroupProperties(FacesContext context, Table2 component,
            JSONObject json) throws IOException, JSONException {
        JSONArray jArray = new JSONArray();
        json.put("rowGroups", jArray);

        // Add properties for each Table2RowGroup child.
        Iterator kids = component.getTable2RowGroupChildren();
        while (kids.hasNext()) {           
            Table2RowGroup group = (Table2RowGroup) kids.next();
            if (group.isRendered()) {
                WidgetUtilities.addProperties(jArray,
                    WidgetUtilities.renderComponent(context, group));
            }
        }
    }

    /** 
     * Helper method to obtain title properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2 to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setTitleProperties(FacesContext context, Table2 component, 
            JSONObject json) throws IOException, JSONException {
        // Get facet.
        UIComponent facet = component.getFacet(Table2.TITLE_FACET);
        if (facet != null) {
            WidgetUtilities.addProperties(json, "title",
                WidgetUtilities.renderComponent(context, facet));
            return;
        }

        // Get filter augment. 
        String filterText = (component.getFilterText() != null) 
            ? getTheme().getMessage("table.title.filterApplied",
                new String[] {component.getFilterText()}) 
            : null;

        // Append component properties.
        json.put("title", component.getTitle())
            .put("filterText", filterText);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}
