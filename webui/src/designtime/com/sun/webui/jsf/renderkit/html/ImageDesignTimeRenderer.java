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

import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;


/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.ImageComponent}.
 * This delegating renderer takes over when the component has no image or icon
 * property set, outputting the component's display name.
 *
 * @author gjmurphy
 */
public class ImageDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    public ImageDesignTimeRenderer() {
        super(new ImageRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ImageComponent image = (ImageComponent) component;
        if (image.getUrl() == null && image.getIcon() == null) {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("span", image); // NOI18N
            writer.writeAttribute("id", image.getId(), "id"); //NOI18N
            writer.writeAttribute("style", image.getStyle(), "style"); //NOI18N
            writer.writeText("<" + DesignMessageUtil.getMessage(StaticTextDesignTimeRenderer.class, "image.label") + ">", null); //NOI18N
            writer.endElement("span"); // NOI18N
        } else {
            super.encodeBegin(context, component);
        }
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ImageComponent image = (ImageComponent) component;
        if (image.getUrl() != null || image.getIcon() != null) {
            super.encodeEnd(context, component);
        }
    }
    
}
