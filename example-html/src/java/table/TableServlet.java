package table;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet to retrieve table data.
 */
public class TableServlet extends HttpServlet {
    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        try {
            JSONObject json = new JSONObject();
            json.write(out);
        } catch (JSONException e) {
            // Log an error
        } finally {
            out.close();
        }
    }
}
