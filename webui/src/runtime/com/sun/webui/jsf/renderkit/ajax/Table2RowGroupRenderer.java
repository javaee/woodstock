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
package com.sun.webui.jsf.renderkit.ajax;

import com.sun.data.provider.SortCriteria;
import com.sun.faces.annotation.Renderer;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.webui.jsf.component.Table2Column;
import com.sun.webui.jsf.component.Table2RowGroup;
import com.sun.webui.jsf.util.ComponentUtilities;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table2RowGroup components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.ajax.Table2RowGroup",
    componentFamily="com.sun.webui.jsf.Table2RowGroup"))
public class Table2RowGroupRenderer
        extends com.sun.webui.jsf.renderkit.widget.Table2RowGroupRenderer{
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
    public void encodeBegin(FacesContext context, UIComponent component) {
        // Do nothing...
    }

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Output component properties if Ajax request and is refresh event.
        if (ComponentUtilities.isAjaxRequest(context, component, "refresh")) {
            super.encodeChildren(context, component);
        }       

        try {
            if (ComponentUtilities.isAjaxRequest(context, component, "scroll") ||
                    ComponentUtilities.isAjaxRequest(context, component, "sort")) {
            // Get XJSON header.
            Map map = context.getExternalContext().getRequestHeaderMap();
            JSONObject xjson = new JSONObject((String)
                map.get(AsyncResponse.XJSON_HEADER));
            Table2RowGroup group = (Table2RowGroup) component;
            JSONObject json = new JSONObject();
            if (ComponentUtilities.isAjaxRequest(context, component, "scroll")) {            // Set first row.
            
            group.setFirst(xjson.getInt("first")); // To do: move to decode method?
            // Get properties.            
                 
            } else if (ComponentUtilities.isAjaxRequest(context, component, "sort")) {
                String colid = xjson.getString("colId");
                String sortOrder = xjson.getString("sortOrder");                
                setSort(component, colid, sortOrder);
                
                setColumnProperties(context, group, json);
                // sortCount 
                json.put("sortCount", group.getSortCount());
            }
             json.put("id", group.getClientId(context)); // For publishEndEvent.      
             setRowProperties(context, group, json);
            json.write(context.getResponseWriter());
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Helper method to handle sorting.
     *
     * @param component UIComponent to be rendered.
     * @param id String column id.
     * @param value String value for sorting option.
     * 
     */
    public void setSort(UIComponent component, String id, String value) {
        UIComponent col = component.findComponent(":"+id);
        if (col != null) {
            Table2RowGroup table2RowGroup = (Table2RowGroup) component;            
            Table2Column table2col = (Table2Column) col;
            SortCriteria criteria = table2col.getSortCriteria();  
            if (criteria != null) {
                // if not the primary sort, add the sort criteria to existing criteria
                if (value.equals("ascending") || value.equals("descending")) {
                    table2RowGroup.addSort(criteria);
                } else {                    
                    if (value.equals("primaryAscending")) {
                        table2RowGroup.clearSort();
                        criteria.setAscending(true);
                    } else if (value.equals("primaryDescending")) {
                        table2RowGroup.clearSort();
                        criteria.setAscending(false);
                    } 
                    table2RowGroup.addSort(criteria);
                    if (value.equals("clear")) {           
                        table2RowGroup.clearSort(); 
                    }
                    
                } 
            }
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
    public void encodeEnd(FacesContext context, UIComponent component) {
        // Do nothing...
    }
}
