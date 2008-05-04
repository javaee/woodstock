package button;

import java.util.Enumeration;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to retrieve table data.
 */
public class ButtonServlet extends HttpServlet {
    // Result page.
    private static final String sendTo = "/button/result.html";

    // Button IDs capable of submitting page.
    private static final String imageButtonId = "ww_id11";
    private static final String primaryButtonId = "ww_id13";
    private static final String secondaryButtonId = "ww_id16";

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
}
