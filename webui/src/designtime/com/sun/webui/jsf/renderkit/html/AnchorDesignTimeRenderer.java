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

import com.sun.webui.jsf.component.Anchor;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.PropertySheetSection;
import com.sun.webui.jsf.component.SkipHyperlink;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.io.IOException;
import java.net.URL;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.Anchor} that 
 * outputs an HTML named anchor, and an image with the anchor icon, only if
 * the anchor is not being used as an unparented helper component and it is
 * not a child of the utility SkipHyperlink.
 *
 * @author gjmurphy
 */
public class AnchorDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    static final String ANCHOR_ICON = 
            "/com/sun/webui/jsf/component/Anchor_C16.png"; //NOI18N
    
    boolean isTextDefaulted;
    
    public AnchorDesignTimeRenderer() {
        super(new AnchorRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        UIComponent parent = component.getParent();
        if (component instanceof Anchor && parent != null && !SkipHyperlink.class.isAssignableFrom(parent.getClass())
                && !PropertySheetSection.class.isAssignableFrom(parent.getClass())) { 
            Anchor anchor = (Anchor) component; 
            ResponseWriter writer = context.getResponseWriter();

            // No need to display a placeholder image if some other stuff already exists for anchor            
            if (component.getChildCount() == 0 &&
                    (anchor.getText() == null)) {            
            writer.startElement("a", anchor); //NOI18N
            String id = anchor.getId();
            writer.writeAttribute("id", id, "id"); //NOI18N
            String style = anchor.getStyle();
            if (style != null)
                writer.writeAttribute("style", style, null); //NOI18N
            String styleClass = anchor.getStyleClass();
            if (styleClass != null)
                RenderingUtilities.renderStyleClass(context, writer, component, null);
            writer.writeAttribute("name", id, null); //NOI18N
            writer.startElement("img", anchor); //NOI18N
            URL url = this.getClass().getResource(ANCHOR_ICON); // NOI18N
            writer.writeURIAttribute("src", url, null); //NOI18N
            writer.endElement("img"); // NOI18N
            writer.endElement("a"); //NOI18N
            } else {
                super.encodeBegin(context, component);
            }            
        }
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException{
                    Anchor anchor = (Anchor) component; 
    // No need to display a placeholder image if some other stuff already exists for anchor                                
        if (component.getChildCount() == 0 &&
        (anchor.getText() == null)) {  
            return;
        } else {
            super.encodeEnd(context, component);
        }

    }
}
