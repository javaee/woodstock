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

package button;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import util.AjaxUtil;

/**
 * Servlet to retrieve table data.
 */
public class ButtonServlet extends HttpServlet {
    // Result page.
    private static final String sendTo = "/button/result.jsp";

    // Button IDs capable of submitting page.
    private static final String imageButtonId = "imageBtn";
    private static final String primaryButtonId = "primaryBtn";
    private static final String secondaryButtonId = "secondaryBtn";

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
            if (AjaxUtil.isAjaxEvent(request, "refresh")) {
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
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "Element ID not found.";
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String id = (String) e.nextElement();
            if (id.equals(imageButtonId + ".x")
                    || id.equals(imageButtonId + ".y")) {
                message = "Element id " + imageButtonId + " was selected";
                break;
            } else if (id.equals(primaryButtonId) 
                    || id.equals(secondaryButtonId)) {
                message = "Element id " + id + 
                    " was selected and has a value of: " + 
                    request.getParameter(id);
                break;
            }
        }
        String url = response.encodeURL(request.getContextPath() + sendTo + 
            "?detail=" + message);
        response.sendRedirect(url);
    }

    /** 
     * Process refresh events.
     * 
     * @param request servlet request
     */
    protected JSONObject processRefreshEvent(HttpServletRequest request) 
            throws JSONException {
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

        // Update button label with given value.
        JSONObject json = new JSONObject();
        json.put("id", id)
            .put("value", value);

        return json;
    }
}
