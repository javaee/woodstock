package index;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to retrieve table data.
 */
public class ShowCodeServlet extends HttpServlet {
    // Token used to insert content in destination file.
    private static final String CONTENT_TOKEN = "@SOURCE_CODE@";

    // Destination file name.
    private static final String DEST_NAME = "showCode.html";

    // Relative path to java and properties resources
    private static final String RELATIVE_PATH = "/WEB-INF/classes/";

    // Source file parameter.
    private static final String SOURCE_PARAM = "source";

    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        try {            
            // Get destination and source files.
            String dest = getDestFile(request);
            String source = getSourceFile(request);

            out.write(dest.replace(CONTENT_TOKEN, source));
	} catch (Exception e) {
	    e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /** 
     * Get the destination file.   
     */
    public String getDestFile(HttpServletRequest request) {
        try {
	    // Get the file input stream             
	    InputStream is = getServletContext().getResourceAsStream(DEST_NAME);
	    if (is == null) {
		throw new Exception("Resource not found: " + DEST_NAME);
            }
	    StringWriter writer = new StringWriter();
            
            int character;
            while ((character = is.read()) != -1) {
                writer.write(character);
            }
	    return writer.getBuffer().toString();
	} catch (Exception e) {
	    e.printStackTrace();
            return "Exception: " + e.toString();
	}        
    }

    /** 
     * Get the source file.   
     */
    public String getSourceFile(HttpServletRequest request) {
        try {
	    boolean isJavaCode = false;
	    String sourceName = (String) request.getParameter(SOURCE_PARAM);

	    if (sourceName.endsWith(".java")) {
		sourceName = RELATIVE_PATH + sourceName;
		isJavaCode = true;
	    } else if (sourceName.endsWith(".properties")) {
		sourceName = RELATIVE_PATH + sourceName;
		isJavaCode = false;
	    } else if (sourceName.endsWith(".html")
                    || sourceName.endsWith(".jsp")
                    || sourceName.endsWith(".js")
		    || sourceName.endsWith(".xml")) {		
		isJavaCode = false;
	    } else {
		throw new Exception("Unknown file type");
	    }

	    // Get the file input stream             
	    InputStream is = getServletContext().getResourceAsStream(sourceName);
	    if (is == null) {
		throw new Exception("Resource not found: " + sourceName);
            }
	    InputStreamReader reader = new InputStreamReader(is);
	    StringWriter writer = new StringWriter();

	    // It turns out that the Java->HTML converter does a decent
	    // job on the JSPs as well; we just want to tell it not to
	    // highlight keywords
	    JavaHtmlConverter.convert(reader, writer, false, isJavaCode);
            
	    return writer.getBuffer().toString();
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Exception: " + e.toString();
	}        
    }
}
