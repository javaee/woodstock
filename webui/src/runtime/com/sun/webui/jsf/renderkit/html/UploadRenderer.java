/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * $Id: UploadRenderer.java,v 1.1.4.1.2.1 2009-12-29 04:52:45 jyeary Exp $
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
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Upload"))
public class UploadRenderer extends FieldRenderer {

    private static final boolean DEBUG = false;

    /**
     * <p>Override the default implementation to conditionally trim the
     * leading and trailing spaces from the submitted value.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>Upload</code> component being processed
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {

        if (DEBUG) {
            log("decode()");
        }
        Upload upload = (Upload) component;
        String id = component.getClientId(context).concat(upload.INPUT_ID);
        if (DEBUG) {
            log("\tLooking for id " + id);
        }
        Map map = context.getExternalContext().getRequestMap();

        if (map.containsKey(id)) {
            if (DEBUG) {
                log("\tFound id " + id);
            }
            upload.setSubmittedValue(id);
        }

        return;
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        if (!(component instanceof Upload)) {
            Object[] params = {component.toString(),
                this.getClass().getName(),
                Upload.class.getName()};
            String message = MessageUtil.getMessage("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "Upload.renderer", params);              //NOI18N
            throw new FacesException(message);
        }

        Theme theme = ThemeUtilities.getTheme(context);
        Map map = context.getExternalContext().getRequestMap();
        Object error = map.get(Upload.UPLOAD_ERROR_KEY);
        if (error != null) {
            if (error instanceof Throwable) {
                if (error instanceof org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException) {
                    // Caused by the file size is too big
                    String maxSize = (String) map.get(Upload.FILE_SIZE_KEY);
                    String[] detailArgs = {maxSize};
                    String summaryMsg = theme.getMessage("FileUpload.noFile");
                    String detailMsg =
                            theme.getMessage("Upload.error", detailArgs);
                    FacesMessage fmsg = new FacesMessage(summaryMsg, detailMsg);
                    context.addMessage(
                            ((Upload) component).getClientId(context), fmsg);
                } else {
                    String summaryMsg = theme.getMessage("FileUpload.noFile");
                    FacesException fe = new FacesException(summaryMsg);
                    fe.initCause((Throwable) error);
                    throw fe;
                }
            }
        }

        boolean spanRendered = super.renderField(context, (Upload) component,
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
