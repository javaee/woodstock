/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package table;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.AjaxUtil;

/**
 * Servlet to retrieve table data.
 */
public class TableServlet extends HttpServlet {
    private TableData data;
    private int totalRows;
    
    public TableServlet() {
        super();
        data = new TableData();
        totalRows = data.getNames().length;
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!AjaxUtil.isAjaxRequest(request)) {
            return;
        }
        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            JSONObject json = null;
            if (AjaxUtil.isAjaxEvent(request, "scroll")) {
                json = processScrollEvent(request);
            } else if (AjaxUtil.isAjaxEvent(request, "refresh")) {
                json = processRefreshEvent(request);
            }
            if (json != null) {
                json.write(out);
            }
        } catch (JSONException e) {
            // Log an error
        } finally {
            out.close();
        }
    }

    /**
     * Get column data.
     * 
     * @param id The widget id
     * @param row The current table data row.
     */
    protected JSONArray getColumns(String id, int row)
            throws JSONException {
        Name[] names = data.getNames();

        JSONObject lastName = new JSONObject();
        lastName.put("id", id + "-" + row + "-col0")
            .put("value", names[row].getLast())
            .put("widgetType", "text");

        JSONObject firstName = new JSONObject();
        firstName.put("id", id + "-" + row + "-col1")
            .put("value", names[row].getFirst())
            .put("widgetType", "text");

        JSONArray json = new JSONArray();
        json.put(lastName);
        json.put(firstName);

        return json;
    }

    /** 
     * Process refresh events.
     * 
     * @param request servlet request
     */
    protected JSONObject processRefreshEvent(HttpServletRequest request) 
            throws JSONException {
        Name[] names = data.getNames();
        String id = AjaxUtil.getAjaxString(request, "id");
        String execute = AjaxUtil.getAjaxString(request, "execute");
        String value = null;
        if (execute != null) {
            // Only one ID is used for this example.
            String[] params = execute.split(",");

            // Find input element ID for text field -- id + "_field".
            Enumeration e = request.getParameterNames();
            while (e.hasMoreElements()) {
                String param = (String) e.nextElement();
                if (param.indexOf(params[0]) != -1) {
                    value = request.getParameter(param);
                    break;
                }
            }
        }

        // Filter all rows that do not match given value.
        JSONArray rows = new JSONArray();
        for (int i = 0; i < 10 && i < totalRows; i++) {
            if (value != null && names[i].getLast().indexOf(value) != -1) {
                rows.put(getColumns(id, i));
            }
        }
            
        JSONObject json = new JSONObject();
        json.put("id", id)
            .put("rows", rows);

        return json;
    }

    /** 
     * Process scroll events.
     * 
     * @param request servlet request
     */
    protected JSONObject processScrollEvent(HttpServletRequest request)
            throws JSONException {
        String id = AjaxUtil.getAjaxString(request, "id");
        int first = AjaxUtil.getAjaxInt(request, "first");
        int maxRows = first + 10;

        JSONArray rows = new JSONArray();
        for (int i = first; i < maxRows && i < totalRows; i++) {
            rows.put(getColumns(id, i));
        }
        
        JSONObject json = new JSONObject();
        json.put("id", id)
            .put("rows", rows)
            .put("totalRows", totalRows);

        return json;
    }
}
