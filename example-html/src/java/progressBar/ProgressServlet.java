package progressBar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to retrieve progress data.
 */
public class ProgressServlet extends HttpServlet {
    
    /**
     * progress monitor will be increased by 10 on every servlet invocation
     */
    private int progress = 0;
    
    public ProgressServlet() {
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

        try {
             out.print(progress);
             progress +=10;
             if (progress > 100) {
                 progress = 0;
             }
            
        } finally {
            out.close();
        }
    }

 
}
