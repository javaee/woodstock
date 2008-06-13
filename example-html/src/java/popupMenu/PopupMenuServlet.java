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

package popupMenu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import util.AjaxUtil;
/**
 * 
 * Servlet class that handles the popupMenu requests
 */
public class PopupMenuServlet extends HttpServlet {

    private static final String popupMenuId = "ww_id10";
    private static final String textId = "ww_id12";
    
    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/x-json;charset=UTF-8"); 
        PrintWriter out = response.getWriter();
        String message = "Selected Option is :";

        String id = AjaxUtil.getAjaxString(request, "id");
        
        // If the request originated from the popup menu, then send back
        // a string containing the selected option.
        if (id.equals(popupMenuId)) {
        message = message + AjaxUtil.getAjaxString(request, "value");

            try {
                JSONObject json = new JSONObject();
                json.put("value", message);
                json.put("id", textId);
                json.write(out);
            } catch (JSONException ex) {
                Logger.getLogger(PopupMenuServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }    
    }
    
}
