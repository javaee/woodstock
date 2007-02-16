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
  * $Id: IFrameRenderer.java,v 1.1 2007-02-16 01:39:00 bob_yennaco Exp $
  */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.IFrame;
import com.sun.webui.jsf.util.RenderingUtilities;

/**
 * <p>Renderer for a {@link IFrameRenderer} component.</p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.IFrame"))
public class IFrameRenderer extends FrameRenderer {
    
    
    // -------------------------------------------------------- Renderer Methods
    
    
    /**
     * <p>Render the appropriate element start for the outermost
     * element.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        IFrame frame = (IFrame) component;
        
        // I don't think this is the correct way to write the XML
        // header /avk
        
        if (!RenderingUtilities.isPortlet(context)) {
            writer.startElement("iframe", component);
       }
        
    }
    
    
    
    /**
     * <p>Render the appropriate element attributes, followed by the
     * nested <code>&lt;head&gt;</code> element, plus the beginning
     * of a nested <code>&lt;body&gt;</code> element.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        
        IFrame frame = (IFrame) component;
        
        super.renderAttributes(context, component, writer);
        
        // Render a nested "head" element
        if (!RenderingUtilities.isPortlet(context)) {
            //align
            String align = frame.getAlign();
            if (align != null) {
                writer.writeAttribute("align", align, null); //NOI18N
            }
            
           //marginWidth
            String  width = frame.getWidth();
            if (width != null) {
                writer.writeAttribute("width", width.toString(), null); //NOI18N
            }
            //marginHeight
            String height = frame.getHeight();
            if (height != null) {
                writer.writeAttribute("height", height.toString(), null); //NOI18N
            }
          }
    }
    /**
     * <p>Render the appropriate element end.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component component to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
              
        // End the outermost "html" element
        if (!RenderingUtilities.isPortlet(context)) {
            writer.endElement("iframe"); //NOI18N
            writer.write("\n"); //NOI18N
        }
                
    }
    
    
    // ------------------------------------------------------- Protected Methods
    
    protected void renderResizeAttribute(ResponseWriter writer, UIComponent comp) 
            throws IOException {
            //intentionally blank
    }

}
