/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

/*
 * $Id: ThemeServlet.java,v 1.1.4.2.2.2 2009-12-29 05:05:17 jyeary Exp $
 */
package com.sun.webui.theme;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>ThemeServlet</code> is required by the <code>com.sun.webui</code>
 * components to resolve references to resources that exist in a jar. 
 * This servlet implementation is needed because a JSF FacesServlet
 * cannot be extended and because a
 * <code>javax.servlet.ServletContext.getResourceAsStream,</code>
 * does not search for a resource within jars that are on the application's
 * class path.
 * <p>
 * Not all theme resources that are referenced by a component are located in
 * jars. An application may override theme resources by defining those
 * resources in the application via a theme resourece bundle.
 * <p>
 * Themes consist of both resources that are used directly 
 * by the Java classes at runtime (for example property files) and 
 * resources that are requested by the application users' browser 
 * (for example image files). The <code>ThemeServlet</code> class makes the 
 * resources in the Theme jar available over HTTP. This requires that the
 * the classpath of this servlet be the same as the class path of the
 * application. The situation should not exist where a different jar
 * is used during the server side page assembly to acquire a theme 
 * resource reference and then a different jar is used by this servlet
 * to obtain the actual bits of that resource when the page is rendering
 * in a browser.
 * </p>
 * 
 * <p><b>How to configure the ThemeServlet</b></p> 
 * 
 * <p>
 * Define an entry for the servlet in the web application's 
 * configuration file (web.xml). Configure one instance of this servlet
 * as follows:
 * <pre>
 *     &lt;servlet&gt;
 *         &lt;servlet-name&gt;ThemeServlet&lt;/servlet-name&gt;
 *         &lt;servlet-class&gt;com.sun.webui.jsf.theme.ThemeServlet&lt;/servlet-class&gt;
 *      &lt;/servlet&gt;
 * 
 *     &lt;servlet-mapping&gt;
 *         &lt;servlet-name&gt;ThemeServlet&lt;/servlet-name&gt;
 *         &lt;url-pattern&gt;/theme/*&lt;/url-pattern&gt;
 *     &lt;/servlet-mapping&gt;
 *   </pre>
 * </p>
 * <p>
 * Note that the <code>url-pattern</code> must be specifed in a slightly
 * different manner for the <code>ThemeContext</code> <code>context-param</code>
 * <code>com.sun.webui.theme.THEME_SERVLET_CONTEXT</code>
 * <pre>
 *     &lt;context-param&gt;
 *	&lt;param-name&gt;com.sun.webui.theme.THEME_SERVLET_CONTEXT&lt;param-name&gt;
 *	&lt;param-value&gt;theme&lt;param-value&gt;
 *    &lt;context-param&gt;
 * </pre>
 * The actual value of the url-pattern is does not have to be 
 * <code>/theme/*</code> it just must be the same for the servlet-mapping
 * and the <code>THEME_SERVLET_CONTEXT</code> <code>context-param</code>.
 * </p>
 * @see com.sun.webui.theme.ThemeContext
 * @see com.sun.webui.theme.Theme
 * @see com.sun.webui.theme.ThemeFactory
 */
public class ThemeServlet extends HttpServlet {

    private static final long serialVersionUID = -8112024913542109274L;
    /**
     * For advanced use only
     */
    private final static boolean DEBUG = false;
    private final static Map respType = new HashMap();

    // Some mime-types... by extension


    static {
        // There is no IANA registered type for JS files. See
        // http://annevankesteren.nl/archives/2005/02/javascript-mime-type
        // for a discussion. I picked text/javascript because that's
        // what we use in the script tag. Apache defaults to
        // application/x-javascript
        respType.put("js", "text/javascript");
        respType.put("css", "text/css");
        respType.put("htm", "text/html");
        respType.put("html", "text/html");
        respType.put("wml", "text/wml");
        respType.put("txt", "text/plain");
        respType.put("xml", "text/xml");
        respType.put("jpeg", "image/jpeg");
        respType.put("jpe", "image/jpeg");
        respType.put("jpg", "image/jpeg");
        respType.put("png", "image/png");
        respType.put("tif", "image/tiff");
        respType.put("tiff", "image/tiff");
        respType.put("bmp", "image/bmp");
        respType.put("xbm", "image/xbm");
        respType.put("ico", "image/x-icon");
        respType.put("gif", "image/gif");
        respType.put("pdf", "application/pdf");
        respType.put("ps", "application/postscript");
        respType.put("mim", "application/mime");
        respType.put("mime", "application/mime");
        respType.put("mid", "application/midi");
        respType.put("midi", "application/midi");
        respType.put("wav", "audio/wav");
        respType.put("bwf", "audio/wav");
        respType.put("cpr", "image/cpr");
        respType.put("avi", "video/x-msvideo");
        respType.put("mpeg", "video/mpeg");
        respType.put("mpg", "video/mpeg");
        respType.put("mpm", "video/mpeg");
        respType.put("mpv", "video/mpeg");
        respType.put("mpa", "video/mpeg");
        respType.put("au", "audio/basic");
        respType.put("snd", "audio/basic");
        respType.put("ulw", "audio/basic");
        respType.put("aiff", "audio/x-aiff");
        respType.put("aif", "audio/x-aiff");
        respType.put("aifc", "audio/x-aiff");
        respType.put("cdda", "audio/x-aiff");
        respType.put("pict", "image/x-pict");
        respType.put("pic", "image/x-pict");
        respType.put("pct", "image/x-pict");
        respType.put("mov", "video/quicktime");
        respType.put("qt", "video/quicktime");
        respType.put("pdf", "application/pdf");
        respType.put("pdf", "application/pdf");
        respType.put("ssm", "application/smil");
        respType.put("rsml", "application/vnd.rn-rsml");
        respType.put("ra", "application/vnd.rn-realaudio");
        respType.put("rm", "application/vnd.rn-realmedia");
        respType.put("rv", "application/vnd.rn-realvideo");
        respType.put("rf", "application/vnd.rn-realflash");
        respType.put("rf", "application/vnd.rn-realflash");
        respType.put("asf", "application/x-ms-asf");
        respType.put("asx", "application/x-ms-asf");
        respType.put("wm", "application/x-ms-wm");
        respType.put("wma", "application/x-ms-wma");
        respType.put("wax", "application/x-ms-wax");
        respType.put("wmw", "application/x-ms-wmw");
        respType.put("wvx", "application/x-ms-wvx");
        respType.put("swf", "application/x-shockwave-flash");
        respType.put("spl", "application/futuresplash");
        respType.put("avi", "video/msvideo");
        respType.put("flc", "video/flc");
        respType.put("mp4", "video/mpeg4");
    }

    /**
     * This method handles the requests for the Theme files.
     * @param request The Servlet Request for the theme file
     * @param response The Servlet Response
     * @throws ServletException If the Servlet fails to serve the resource file
     * @throws IOException If the Servlet cannot locate and read a requested ThemeFile
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (DEBUG) {
            log("doGet()");
        }
        String resourceName = request.getPathInfo();
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            // The issue here is, do we try and get the resource
            // from the jar that defined this resource ?
            // Or hope that it is unique enough to come from the
            // jar it was defined in.
            //
            // Get InputStream
            inStream = this.getClass().getResourceAsStream(resourceName);
            if (inStream == null) {
                //Send 404 (without the original URI for XSS security reasons)
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            inStream = new BufferedInputStream(inStream, 4096);

            // Ask the container to resolve the MIME type if possible
            String type = getServletContext().getMimeType(resourceName);
            if (type == null) {
                // Otherwise, use our own hard coded list
                int lastDot = resourceName.lastIndexOf('.');
                if (lastDot != -1) {
                    String suffix = resourceName.substring(lastDot + 1);
                    type = (String) respType.get(suffix.toLowerCase());
                }
            }
            // Set the content type of this response
            if (type != null) {
                response.setContentType(type);
            }

            // Set the timestamp of the response to enable caching
            response.setDateHeader("Last-Modified", getLastModified(request));

            // Get the OutputStream
            outStream = response.getOutputStream();
            outStream = new BufferedOutputStream(outStream, 4096);

            int character;
            while ((character = inStream.read()) != -1) {
                outStream.write(character);
            }
        } catch (IOException ioex) {
            //Log an error
        } finally {
            try {
                inStream.close();
            } catch (Throwable t) {
            }
            try {
                outStream.close();
            } catch (Throwable t) {
            }
        }
        return;
    }

    /**
     * Returns a short description of the servlet.
     * @return A String that names the Servlet
     */
    @Override
    public String getServletInfo() {
        return "Theme Servlet for Sun Web Components";
    }

    /**
     * Initializes the ThemeServlet
     * @param config The ServletConfig object
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    // Note that there is no control exerted here to direct a
    // request to a particular theme or override a theme.
    // The assumption is that the rendered output has exerted that
    // control by obtaining a reference to the appropriate resource
    // and rendering that reference. This servlet just has the
    // opportunity of setting up the theme ASAP, before any
    // pages are rendered, and for sharing resources. The context
    // of this servlet is known by several applications or possibly
    // given to the Theme subsystem by the console. Then the
    // theme subsystem returns actual references to the appropriate
    // resource based on the "theme context", i.e. overrides etc.
    //
    // Consider the Console as implementing ThemeContext. by the
    // time this servlet's init method has been called the
    // Console will have "installed" an appropriate ThemeContext
    // instance. The call to getInstance will return that ThemeContext
    // instance and configure the ThemeFactory with it.
    //
    // Actually there is no need to set up the ThemeContext here.
    // The idea is that the application framework will have
    // implemented an "XXXThemeContext" which will have been
    // created to set up the Theme environment as necesary.
    // If fact doing this here in a JSF environment may not
    // provide enough information like the context URL which
    // doesn't appear to be available at this point.
    //
    // Forget this now and assume that JSFServletContext is
    // available.
    //
    // ThemeContext themeContext = ServletThemeContext.getInstance(
    // 	getServletContext());

    }
    /**
     * <p>The "last modified" timestamp we should broadcast for all resources
     * provided by this servlet.  This will enable browsers that cache static
     * resources to send an "If-Modified-Since" header, which will allow us to
     * return a "Not Modified" response.</p>
     */
    private long lastModified = (new Date()).getTime();

    /**
     * <p>Return the timestamp for when resources provided by this servlet
     * were last modified.  By default, this will be the timestamp when this
     * servlet was first loaded at the deployment of the containing webapp,
     * so that any changes in the resources will be automatically sent to
     * the clients who might have cached earlier versions.</p>
     * @param request The HttpServletRequest being processed
     * @return The date when the resource was last modified
     */
    @Override
    public long getLastModified(HttpServletRequest request) {
        return this.lastModified;
    }
}
