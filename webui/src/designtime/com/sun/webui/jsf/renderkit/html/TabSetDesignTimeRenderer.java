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

import com.sun.webui.jsf.component.TabSet;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.TabSet} that renders
 * a minimal block of markup when the there are no tab children.
 *
 * @author gjmurphy
 */
public class TabSetDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of TabSetDesignTimeRenderer */
    public TabSetDesignTimeRenderer() {
        super(new TabSetRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component instanceof TabSet && component.getChildCount() == 0) {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("div", component);
            String style = ((TabSet) component).getStyle();
            writer.writeAttribute("style", style, "style");
            writer.startElement("span", component);
            writer.writeAttribute("class", super.UNINITITIALIZED_STYLE_CLASS, "class");
            String label = DesignMessageUtil.getMessage(TabSetDesignTimeRenderer.class, "tabSet.label");
            char[] chars = label.toCharArray();
            writer.writeText(chars, 0, chars.length);
            writer.endElement("span");
            writer.endElement("div");
        } else {
            super.encodeBegin(context, component);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (!(component instanceof TabSet && component.getChildCount() == 0))
            super.encodeEnd(context, component);
    }
    
}
