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
  * $Id: PageRenderer.java,v 1.1 2007-02-16 01:41:37 bob_yennaco Exp $
  */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for a {@link Page} component.</p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Page"))
public class PageRenderer extends javax.faces.render.Renderer {

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

       return; 
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        
        if(!(component instanceof Page)) {
            Object[] params = { component.toString(),
                    this.getClass().getName(),
                    Page.class.getName() };
                    String message = MessageUtil.getMessage
                            ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                            "Renderer.component", params);              //NOI18N
                    throw new FacesException(message);
        }
        
        Page page = (Page)component;
        
        ResponseWriter writer = context.getResponseWriter();
        
        if (!RenderingUtilities.isPortlet(context)) {
            //write the doctype stuff
            if (page.isXhtml()) {
                if (page.isFrame()) {
                    //xhtml transitional frames
                    writer.write("<!DOCTYPE html PUBLIC \"" +
                            "-//W3C//DTD XHTML 1.0 Frameset//EN\" " +
                            "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">");
                } else {
                    //html transitional
                    writer.write("<!DOCTYPE html PUBLIC \"" +
                            "-//W3C//DTD XHTML 1.0 Transitional//EN\" " +
                            "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
                }
            } else {
                if (page.isFrame()) {
                    //html transitional frames
                    writer.write("<!DOCTYPE html PUBLIC \"" +
                            "-//W3C//DTD HTML 4.01 Frameset//EN\" " +
                            "\"http://www.w3.org/TR/html4/frameset.dtd\">");
                } else {
                    //html transitional
                    writer.write("<!DOCTYPE html PUBLIC \"" +
                            "-//W3C//DTD HTML 4.01 Transitional//EN\" " +
                            "\"http://www.w3.org/TR/html4/loose.dtd\">");
                }
            }
            writer.write("\n");           
        }      
    }

    public boolean getRendersChildren() {
        return false; 
    }
}
