/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.renderkit.html;

import com.sun.webui.jsf.component.Anchor;
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
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
    }

    public void encodeChildren(FacesContext context, UIComponent component) {
    }
    
}
