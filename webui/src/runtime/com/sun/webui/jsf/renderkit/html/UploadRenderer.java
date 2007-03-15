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
  * $Id: UploadRenderer.java,v 1.2 2007-03-15 12:35:25 rratta Exp $
  */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import com.sun.webui.jsf.component.Upload;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for a {@link Upload} component.</p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Upload"))
public class UploadRenderer extends FieldRenderer {
    
    private static final boolean DEBUG = false;
     
    /**
     * <p>Override the default implementation to conditionally trim the
     * leading and trailing spaces from the submitted value.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>Upload</code> component being processed
     */
    public void decode(FacesContext context, UIComponent component) {

        if(DEBUG) log("decode()"); 
        Upload upload = (Upload)component; 
        String id = component.getClientId(context).concat(upload.INPUT_ID); 
        if(DEBUG) log("\tLooking for id " + id); 
        Map map = context.getExternalContext().getRequestMap(); 
        
        if(map.containsKey(id)) { 
           if(DEBUG) log("\tFound id " + id); 
           upload.setSubmittedValue(id);
        }
         
        return;
    }

    public void encodeEnd(FacesContext context, UIComponent component) 
	    throws IOException {
          
        if(!(component instanceof Upload)) { 
            Object[] params = { component.toString(), 
                                this.getClass().getName(), 
                                Upload.class.getName() };
            String message = MessageUtil.getMessage
                ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                 "Upload.renderer", params);              //NOI18N
            throw new FacesException(message);  
        }

        Theme theme = ThemeUtilities.getTheme(context);
        Map map = context.getExternalContext().getRequestMap(); 
        Object error =  map.get(Upload.UPLOAD_ERROR_KEY);
        if (error != null) {
            if (error instanceof Throwable) {
                if (error instanceof org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException ) {
                    // Caused by the file size is too big
                    String maxSize = (String)map.get(Upload.FILE_SIZE_KEY);
                    String [] detailArgs = {maxSize};
                    String summaryMsg = theme.getMessage("FileUpload.noFile");
                    String detailMsg = 
			theme.getMessage("Upload.error", detailArgs);
                    FacesMessage fmsg = new FacesMessage(summaryMsg, detailMsg);
                    context.addMessage(
			((Upload)component).getClientId(context), fmsg);
                } else {   
                    String summaryMsg = theme.getMessage("FileUpload.noFile");
                    FacesException fe = new FacesException(summaryMsg);
                    fe.initCause((Throwable)error);
                    throw fe;
                }
            }
        }
        
        boolean spanRendered = super.renderField(context, (Upload)component, 
            "file", getStyles(context));

	String id = component.getClientId(context);

        StringBuilder jsString = new StringBuilder(256);
        jsString.append(JavaScriptUtilities.getModuleName(
            "upload.setEncodingType")); //NOI18N
        jsString.append("(\'"); //NOI18N
        jsString.append(id);
        jsString.append("\');\n"); //NOI18N

        // Render JavaScript.
        ResponseWriter writer = context.getResponseWriter();
        JavaScriptUtilities.renderJavaScript(component, writer,
            jsString.toString());

	if (!spanRendered) {
	    String param = id.concat(Upload.INPUT_PARAM_ID);
	    RenderingUtilities.renderHiddenField(component, writer, param, id);
	}
    }
}
