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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.util.Locale;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.JobStatus;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renders an instance of the JobStatus component.</p>
 *
 * @author Sean Comerford
 * @ deprecated
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.JobStatus"))
public class JobStatusRenderer extends HyperlinkRenderer {
    
    /** Creates a new instance of JobStatusRenderer */
    public JobStatusRenderer() {
    }
    
    /**
     * <p>Render the start of the JobStatus component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        JobStatus jobStatus = (JobStatus) component;        
        
        ImageComponent image = jobStatus.getImageFacet();
        if (image != null) {
            RenderingUtilities.renderComponent(image, context);
        }
        
        writer.write("&nbsp;");
    }
    
    protected void finishRenderAttributes(FacesContext context,
            UIComponent component, ResponseWriter writer) throws IOException {
        JobStatus jobStatus = (JobStatus) component;
        Theme theme = ThemeUtilities.getTheme(context);       
                
	Object textObj = jobStatus.getText();
        String text = textObj != null ? 
	    ConversionUtilities.convertValueToString(component, textObj) :
            theme.getMessage("masthead.tasksRunning");        
        
        writer.startElement("span", jobStatus);
        addCoreAttributes(context, jobStatus, writer, 
            theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT));
        writer.write(text);
        writer.write("&nbsp;" + jobStatus.getNumJobs()); // NOI18N
        writer.endElement("span");        
    }
}
