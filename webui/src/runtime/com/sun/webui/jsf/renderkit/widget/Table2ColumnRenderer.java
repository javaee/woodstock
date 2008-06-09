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
import com.sun.webui.jsf.component.Table2Column;
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
 * This class renders Table2Column components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Table2Column",
    componentFamily="com.sun.webui.jsf.Table2Column"))
public class Table2ColumnRenderer extends RendererBase {
    /**
     * The set of pass-through attributes to be rendered.
     * <p>
     * Note: The WIDTH, HEIGHT, and BGCOLOR attributes are all deprecated (in
     * the HTML 4.0 spec) in favor of style sheets. In addition, the DIR and 
     * LANG attributes are not cuurently supported.
     * </p>
     */
    private static final String stringAttributes[] = {
        "abbr",
        "axis",
        "bgColor",
        "char",
        "charOff",        
        "dir",
        "headers",        
        "lang",
        "noWrap",
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
        "rowSpan",
        "scope",
        "style",
        "valign"
        };
        
    /**
     * The set of int attributes to be rendered.
     */
    private static final String intAttributes[] = {
        "tabIndex",
        "height",
        "width",
        "colspan"        
    };
    
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
	if (!(component instanceof Table2Column)) {
	    throw new IllegalArgumentException(
                "Table2ColumnRenderer can only render Table2Column components.");
        }
        
        Table2Column col = (Table2Column) component;
        JSONObject json = new JSONObject();
        json.put("className", col.getStyleClass())
            .put("footerText", col.getFooterText())
            .put("headerText", col.getHeaderText())
            .put("title", col.getToolTip())
            .put("sort", col.getSort() != null ? true : false)
            .put("sortLevel", col.getSortLevel())
            .put("emptyCell", col.isEmptyCell())
            .put("visible", col.isVisible());
            
        // multi level column headers
        setColumnProperties(context, col, json); 
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, col, json);
        JSONUtilities.addIntegerProperties(intAttributes, col, json);
        

        return json;
    }    
    
    /**
     * Helper method to obtain column properties.
     *
     * @param context FacesContext for the current request.
     * @param component Table2RowGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setColumnProperties(FacesContext context, Table2Column component,
            JSONObject json) throws IOException, JSONException {
        JSONArray jArray = null;     
        // Add properties for each Table2Column child.
        Iterator kids = component.getChildren().iterator();
        if (kids.hasNext()) {
            jArray = new JSONArray();
            json.put("columns", jArray);
        }
        while (kids.hasNext()) {
            UIComponent spanCol = (UIComponent) kids.next();
            if (spanCol instanceof Table2Column)
             if (spanCol.isRendered()) {
                jArray.put(WidgetUtilities.renderComponent(context, spanCol));
            }
        }
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return null; // Not implemented
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
