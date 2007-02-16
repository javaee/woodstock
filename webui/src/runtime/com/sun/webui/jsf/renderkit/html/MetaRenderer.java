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
import com.sun.webui.jsf.component.Meta;
import com.sun.webui.jsf.component.util.Util;
import java.beans.Beans;

import java.io.IOException;
import java.lang.NullPointerException;
import java.lang.StringBuffer;
import java.net.URL;
import java.util.Map;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>This class is responsible for rendering the meta component for the
 * HTML Render Kit.</p> <p> The meta component can be used as an Meta</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Meta"))
public class MetaRenderer extends AbstractRenderer {
    
    // -------------------------------------------------------- Static Variables
        
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] =
    { "name", "content", "scheme"}; //NOI18N

      
      // -------------------------------------------------------- Renderer Methods
         
      
      /**
       * <p>Render the start of an Meta (Meta) tag.</p>
       * @param context <code>FacesContext</code> for the current request
       * @param component <code>UIComponent</code> to be rendered
       * @param writer <code>ResponseWriter</code> to which the element
       * start should be rendered
       * @exception IOException if an input/output error occurs
       */
      protected void renderStart(FacesContext context, UIComponent component,
      ResponseWriter writer) throws IOException {

       //intentionally left blank
      
      }
      
    /**
     * <p>Render the attributes for an Meta tag.  The onclick attribute will contain
     * extra javascript that will appropriately submit the form if the URL field is
     * not set.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * attributes should be rendered
     * @exception IOException if an input/output error occurs
     */
    protected void renderAttributes(FacesContext context, UIComponent component,
    ResponseWriter writer) throws IOException {
        //intentionally left blank
    }
      
    /**
     * <p>Close off the Meta tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * end should be rendered
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
        ResponseWriter writer) throws IOException {
        // End the appropriate element
        
        Meta meta = (Meta) component;
        
        writer.startElement("meta", meta);
        addCoreAttributes(context, component, writer, null);
        
        String header = meta.getHttpEquiv();
        if (header != null) {
            writer.writeAttribute("http-equiv", header, null);
        }
        addStringAttributes(context, component, writer, stringAttributes);
        writer.endElement("meta"); //NOI18N
        writer.write("\n"); //NOI18N

    }
            
      // --------------------------------------------------------- Private Methods
      
}
