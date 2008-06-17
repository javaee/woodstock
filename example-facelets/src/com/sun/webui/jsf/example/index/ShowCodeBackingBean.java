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

package com.sun.webui.jsf.example.index;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.example.common.MessageUtil;
import com.sun.webui.jsf.example.index.JavaHtmlConverter;

import java.util.Map;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Serializable;

import javax.servlet.ServletContext;

/**
 * Backing bean for the show code page.
 */
public class ShowCodeBackingBean implements Serializable {    
    
    // name of the file to display its content
    private String fileName; 
    
    // relative path to java and properties resources
    private static final String RELATIVE_PATH =
            "/WEB-INF/classes/com/sun/webui/jsf/example/";    
    
    /** Default constructor */
    public ShowCodeBackingBean() {       
    }
  
    /**
     * Get file name.
     */
    public String getFileName() {
        // Get hyperlink parameter      
        Map map = FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap();
        String param = (String) map.get("param");
        this.fileName = (param != null) ? param : 
                MessageUtil.getMessage("index_noFileName");
        
        return this.fileName;
    }
        
    /** 
     * Get the source code in the form of html
     *    
     */
    public String getSourceCode() {
        try {
	    boolean isJavaCode = false;
	    String sourceName = this.fileName;

	    if (sourceName.endsWith(".java")) {
		sourceName = RELATIVE_PATH + sourceName;
		isJavaCode = true;
	    } else if (sourceName.endsWith(".properties")) {
		sourceName = RELATIVE_PATH + sourceName;
		isJavaCode = false;
	    } else if (sourceName.endsWith(".jsp")
                    || sourceName.endsWith(".js")
		    || sourceName.endsWith(".xml")
                    || sourceName.endsWith(".xhtml")) {		
		isJavaCode = false;
	    } else {
		throw new Exception("Unknown file type");
	    }

	    // Get the source file input stream
            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext =                     
                     (ServletContext) context.getExternalContext().getContext();             
	    InputStream is = servletContext.getResourceAsStream(sourceName);
	    if (is == null)
		throw new Exception("Resource not found: " + sourceName);

	    InputStreamReader reader = new InputStreamReader(is);
	    StringWriter writer = new StringWriter();

	    // It turns out that the Java->HTML converter does a decent
	    // job on the JSPs as well; we just want to tell it not to
	    // highlight keywords
	    JavaHtmlConverter.convert(reader, writer, true, isJavaCode);
            
	    return writer.getBuffer().toString();
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Exception: " + e.toString();
	}        
    }    
}
