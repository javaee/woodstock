/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bubble;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author as199886
 */
public class BubbleServlet extends HttpServlet {
   
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
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
                    .put("widgetType", "staticText");
                json.put("contents",contents);                
                } else if (params.equals("ww_id13")) {
                    json.put("title","For second hyperlink");
                    contents.put("id", "content2")
                    .put("value", "Updated bubble content for Second hyperlink.")
                    .put("widgetType", "staticText");
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
   
    // </editor-fold>
}
