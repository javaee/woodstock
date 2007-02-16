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
import com.sun.webui.jsf.component.PanelLayout;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Renderer for a {@link com.sun.webui.jsf.component.PanelLayout} component.
 *
 * @author gjmurphy
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.PanelLayout"))
public class PanelLayoutRenderer extends AbstractRenderer {
    
    protected void renderStart(FacesContext context, UIComponent component, ResponseWriter writer) 
    throws IOException {
        writer.startElement("div", component); //NOI18N
    }

    protected void renderAttributes(FacesContext context, UIComponent component, ResponseWriter writer) 
    throws IOException {
        PanelLayout panelLayout = (PanelLayout)component;
        StringBuffer buffer = new StringBuffer();
        
        // Write id attribute
        String id = component.getId();
        writer.writeAttribute("id", panelLayout.getClientId(context), "id");
        
        // Write style attribute
        if (PanelLayout.GRID_LAYOUT.equals(panelLayout.getPanelLayout()))
	    buffer.append("position: relative; -rave-layout: grid;"); //NOI18N
        String style = panelLayout.getStyle();
        if (style != null && style.length() > 0) {
            buffer.append(" ");
            buffer.append(style);
        }
        writer.writeAttribute("style", buffer.toString(), "style");
        
        // Write style class attribute
        RenderingUtilities.renderStyleClass(context, writer, component, null);
    }

    protected void renderEnd(FacesContext context, UIComponent component, ResponseWriter writer) 
    throws IOException {
        writer.endElement("div"); //NOI18N
    }

    public boolean getRendersChildren() {
        return true;
    }
    
}
