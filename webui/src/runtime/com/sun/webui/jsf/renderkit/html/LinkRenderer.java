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
import com.sun.webui.jsf.component.Link;
//import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.RenderingUtilities;
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
 * <p>This class is responsible for rendering the link component for the
 * HTML Render Kit.</p> <p> The link component can be used as an Link</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Link"))
public class LinkRenderer extends AbstractRenderer {
    
    // -------------------------------------------------------- Static Variables
        
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] =
    { "charset", "media", "rel", "type"}; //NOI18N

      
      // -------------------------------------------------------- Renderer Methods
         
      
      /**
       * <p>Render the start of an Link (Link) tag.</p>
       * @param context <code>FacesContext</code> for the current request
       * @param component <code>UIComponent</code> to be rendered
       * @param writer <code>ResponseWriter</code> to which the element
       * start should be rendered
       * @exception IOException if an input/output error occurs
       */
      protected void renderStart(FacesContext context, UIComponent component,
      ResponseWriter writer) throws IOException {      
          //intentionally empty
      }
      
    /**
     * <p>Render the attributes for an Link tag.  The onclick attribute will contain
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

        //intentionally empty
    }
      
    /**
     * <p>Close off the Link tag.</p>
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * end should be rendered
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
        ResponseWriter writer) throws IOException {
        // End the appropriate element

         Link link = (Link) component;
         
         if (!RenderingUtilities.isPortlet(context)) {
            writer.startElement("link", link);
            addCoreAttributes(context, component, writer, null);
            addStringAttributes(context, component, writer, stringAttributes);
	    String lang = link.getUrlLang();
            if (null != lang) {
                writer.writeAttribute("hreflang", lang, "lang"); //NOI18N
            }
            // the URL is the tough thing because it needs to be encoded:
            
            String url = link.getUrl();
            
            if (url != null) {          
                writer.writeAttribute("href", //NOI18N
                    context.getApplication().getViewHandler()
                        .getResourceURL(context, url), 
                    "url"); //NOI18N
            }

            writer.endElement("link"); //NOI18N
            writer.write("\n"); //NOI18N
        }

    }
            
      // --------------------------------------------------------- Private Methods
      
}
