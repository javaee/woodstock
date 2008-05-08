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

package rating;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet to retrieve rating data.
 */
public class RatingServlet extends HttpServlet {
    private HashMap<String,Rating> ratingMap;
    
    public RatingServlet() {
        ratingMap = new HashMap<String,Rating>();
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            JSONObject json = null;
            String event = request.getParameter("event");
            if (event.equals("submit")) {
                json = processSubmitEvent(request);
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
     * Process submit events.
     * 
     * @param request servler request
     */
    protected JSONObject processSubmitEvent(HttpServletRequest request)
            throws JSONException {
        String id = request.getParameter("id");
        
        // If no data object for this widget exists yet, then create one.
        Rating r = (Rating)ratingMap.get(id);
        if (r == null)
            ratingMap.put(id, r = new Rating());
        
        JSONObject json = new JSONObject();
        json.put("id", id);
        
        // Submitted value is in a hidden field
        String hiddenField = id + "_submitValue";
        String value = request.getParameter(hiddenField);
        try {
            Integer v = Integer.valueOf(value);
            r.setGrade(v.intValue());
            json.put("averageGrade", r.getAverageGrade());
        } catch (Exception e) {
            // Do nothing
        }
        return json;
    }

}
