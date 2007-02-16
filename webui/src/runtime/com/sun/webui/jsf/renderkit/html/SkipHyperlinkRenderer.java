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
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.SkipHyperlink;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;




/**
 * <p>This class is responsible for rendering the {@link SkipHyperlink}
 * component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.SkipHyperlink"))
public class SkipHyperlinkRenderer extends javax.faces.render.Renderer {
        
    /** Creates a new instance of AlertRenderer */
    public SkipHyperlinkRenderer() {
        // default constructor
    }
    
    // We don't render our own children - defer to the default behaviour,
    // which allows for interweaving compoenents with non-components.
    public boolean getRendersChildren() {
        return false;
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        
        SkipHyperlink link = (SkipHyperlink) component;
        if(!link.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        
        // Get the theme
        Theme theme = ThemeUtilities.getTheme(context);
        
        // Components must have a top-level element with the component ID
        Integer index = null; 
        int tabIndex = link.getTabIndex(); 
        if(tabIndex != Integer.MIN_VALUE) { 
            index = new Integer(tabIndex); 
        }        
        
        String styleClass = (link.getStyleClass() != null)
            ? link.getStyleClass()
            : theme.getStyleClass(ThemeStyles.SKIP_WHITE);
        
        RenderingUtilities.renderSkipLink("", styleClass, link.getStyle(), 
                       link.getDescription(), index, link, context);
        writer.write("\n"); //NOI18N        
    }
    
    
    public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException {
        
        SkipHyperlink link = (SkipHyperlink) component;
        if(!link.isRendered()) {
            return;
        }
        RenderingUtilities.renderAnchor("", component, context);   
    }
}
