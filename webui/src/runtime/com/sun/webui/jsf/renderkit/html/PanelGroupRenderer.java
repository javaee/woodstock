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
import com.sun.webui.jsf.component.PanelGroup;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Renderer for a {@link com.sun.webui.jsf.component.PanelGroup} component.
 *
 * @author gjmurphy
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.PanelGroup"))
public class PanelGroupRenderer extends AbstractRenderer {
    
    private String elementName;
    
    protected void renderStart(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {
        PanelGroup panelGroup = (PanelGroup) component;
        if (panelGroup.isBlock())
            elementName = "div"; //NOI18N
        else
            elementName = "span"; //NOI18N
        writer.startElement(elementName, component);
    }
    
    protected void renderAttributes(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {
        addCoreAttributes(context, component, writer, null);
    }
    
    public boolean getRendersChildren() {
        return true;
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        PanelGroup panelGroup = (PanelGroup) component;
        List children = panelGroup.getChildren();
        ResponseWriter writer = context.getResponseWriter();
        UIComponent separatorFacet = panelGroup.getFacet(PanelGroup.SEPARATOR_FACET);
        if (separatorFacet != null) {
            for (int i = 0; i < children.size(); i++) {
                if (i > 0)
                    RenderingUtilities.renderComponent(separatorFacet, context);
                RenderingUtilities.renderComponent((UIComponent) children.get(i), context);
            }
        } else {
            String separator = panelGroup.getSeparator();
            if (separator == null)
                separator = "\n";
            for (int i = 0; i < children.size(); i++) {
                if (i > 0)
                    writer.write(separator);
                RenderingUtilities.renderComponent((UIComponent) children.get(i), context);
            }
        }
    }
    
    protected void renderEnd(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {
        writer.endElement(elementName);
    }
    
}
