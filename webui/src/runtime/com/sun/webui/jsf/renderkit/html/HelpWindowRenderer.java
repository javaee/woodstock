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

import com.sun.webui.jsf.util.LogUtil;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.HelpWindow;
        
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * <p>This class is responsible for rendering the {@link HelpWindow} component for
 * the HTML Render Kit.</p> <p> The {@link HelpWindow} component can be used as 
 * a link which when clicked opens up the a popup window that displays help data </p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.HelpWindow"))
public class HelpWindowRenderer extends HyperlinkRenderer {
    
    // -------------------------------------------------------- Static Variables
    
        
    // -------------------------------------------------------- Renderer Methods
      /**
       * This method returns the most appropriate URL under the 
       * circumstances. Insome cases viewhandler.getActionURL() needs
       * to be invoked while in other cases viewhandler.getResourceURL() 
       * needs to be invoked. The hyperlink renderer, by default, will 
       * always use latter while generating the complete URL. Subclasses
       * of this renderer can do it their own way.
       */
      protected String getCorrectURL(FacesContext context, UIComponent component,
              String url) {
          
          if (!(component instanceof HelpWindow)) {
              return null;
          }
          
          if (url == null) {
              return null;
          }
          
          StringBuffer urlBuffer = new StringBuffer();
          urlBuffer.append(context.getApplication().getViewHandler()
            .getActionURL(context, url))
            .append("?");
          
          HelpWindow hw = (HelpWindow)component;
          try {
              if (hw.getWindowTitle() != null) {
                  addParameter(urlBuffer, "windowTitle", hw.getWindowTitle());
              }

              if (hw.getHelpFile() != null) {
                  addParameter(urlBuffer, "helpFile", hw.getHelpFile());
              }
          } catch (UnsupportedEncodingException e) {
            LogUtil.warning("Encoding error: " , e);
          }
          return urlBuffer.toString();
      }

    // --------------------------------------------------------- Private Methods

    // Helper method to append encoded request parameters to the URL in the
    // appropriate character encoding
    private void addParameter(StringBuffer buffer, String name, String value)
            throws UnsupportedEncodingException {
	if (buffer == null || name == null || value == null)
	    return;
        
	buffer.append("&")
	    .append(name)            
	    .append("=")
	    .append(URLEncoder.encode(value, "UTF-8"));
    }
}
