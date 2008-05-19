/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package radioButton;

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
 *
 * @author 
 */
public class RadioButtonServlet extends HttpServlet {
    
    // Result page.
    private static final String sendTo = "/radioButton/result.html";

    // Button IDs capable of submitting page.
    private static final String radioButtonGroup = "ww_id9_rb";

    
            
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
        String groupID;
        
        while (e.hasMoreElements()) {
            groupID = (String) e.nextElement();
            if ( groupID.equals(radioButtonGroup) ){
                message = "Element id " + groupID + 
                    " was selected and has a value of: " + 
                    request.getParameter(groupID);
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
        JSONObject label = new JSONObject();
        label.put("value", value);
        json.put("id", id)
            .put("label", label);

        return json;
    }
}
