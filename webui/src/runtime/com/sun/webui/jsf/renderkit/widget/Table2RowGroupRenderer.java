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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.Table2Column;
import com.sun.webui.jsf.component.Table2RowGroup;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
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
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.widget.Table2RowGroup",
componentFamily = "com.sun.webui.jsf.Table2RowGroup"))
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
        json.put("first", group.getFirst()).put("maxRows", group.getRows()).put("totalRows", group.getRowCount()).put("templatePath", (templatePath != null)
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
