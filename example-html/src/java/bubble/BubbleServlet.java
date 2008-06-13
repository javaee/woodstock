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

package bubble;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class BubbleServlet extends HttpServlet {
    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            JSONObject json = new JSONObject();
            String event = request.getParameter("event");
            String id = request.getParameter("id");            
            if (event.equals("refresh")) {
                String params = request.getParameter("params");                
                json.put("id",id);
                // to update bubble contents
                JSONObject contents = new JSONObject();
                if (params.equals("ww_id12")) {
                    json.put("title","For first hyperlink");                    
                contents.put("id", "content1")
                    .put("value", "Updated bubble content for First hyperlink.")
                    .put("widgetType", "text");
                json.put("contents",contents);                
                } else if (params.equals("ww_id13")) {
                    json.put("title","For second hyperlink");
                    contents.put("id", "content2")
                    .put("value", "Updated bubble content for Second hyperlink.")
                    .put("widgetType", "text");
                json.put("contents",contents);
                }
                if (json != null) {
                    json.write(out);
                }
            }
        } catch (JSONException e) {
            // Log an error
        } finally {
            out.close();
        }
    }
}
