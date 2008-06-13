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
        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.write(getSourceFile(request));
        out.close();
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
