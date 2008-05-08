/*
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

package checkbox;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet to retrieve and update checkbox data.
 */
public class CheckboxServlet extends HttpServlet {

    public CheckboxServlet() {
        super();
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
	    // ?cbprops={...}&label=newlabelvalue
	    //
            String label = request.getParameter("label");
	    if (label == null) {
		return;
	    }
	    String cbprops = request.getParameter("cbprops");
	    if (cbprops == null) {
		return;
	    }

	    JSONObject cb = new JSONObject(cbprops);
	    JSONObject jslabel = cb.getJSONObject("label");
	    jslabel.put("value", label);
	    cb.write(out);

        } catch (JSONException e) {
            // Log an error
        } finally {
            out.close();
        }
    }
}
