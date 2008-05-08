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

package calendar;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Example web application servlet to return the date for a specified
 * holiday.  Holiday is specified as an option value in the GET request
 * parameters.  Return a serialized JSON object containing the date.
 * 
 * @author bedwards
 */
public class HolidayServlet extends HttpServlet {
   
    // List of holiday selection options.  Must match options from the
    // drop down selection list.
    private static final String[] OPTIONS = {"newyear", "king", "washington",
        "memorial", "independence", "labor", "columbus", "veteran", 
        "thanksgiving", "christmas"};
    
    // Matching list of holiday dates; position dependent.
    private static final String[] DATES = {"01/01", "01/21", "02/18", "05/26", 
        "07/04", "09/01", "10/13", "11/11", "11/27", "12/25"};
    
    // Some defaults.
    private static final String YEAR = "2008";
    private static final String DFLT = "01/01";
    private static final String DATE_PROP = "date";
    private static final String TODAY_PROP = "today";
    
    public HolidayServlet() {
        super();
    }
    
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

        // Get the holiday parameter from the GET request.
        // Should be one of our ten holiday selection options.
        // Not a localized date format in this sample.
        try {
            JSONObject json = new JSONObject();
            String holiday = request.getParameter("holiday");
            String date = DFLT;
            if (holiday != null) {
                for (int i = 0; i < OPTIONS.length; i++) {
                    if (holiday.equals(OPTIONS[i])) {
                        date = DATES[i];
                        break;
                    }
                }
            }
            date = date + "/" + YEAR;
            json.put(DATE_PROP, date);
            
            // Return the current date.   
            // Not a localized date format in this sample.
            Date today = new Date();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String curdate = df.format(today);
            json.put(TODAY_PROP, curdate);
            
            // Write the JSON object into the response buffer.
            if (json != null) {
                json.write(out);
            }
        } catch (JSONException e) {
            // Log an error
        } finally {
            out.close();
        }
    }

}
