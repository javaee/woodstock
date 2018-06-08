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

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.SkipHyperlink;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>This class is responsible for rendering the {@link SkipHyperlink}
 * component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.SkipHyperlink"))
public class SkipHyperlinkRenderer extends javax.faces.render.Renderer {

    /** Creates a new instance of AlertRenderer */
    public SkipHyperlinkRenderer() {
        // default constructor
    }

    // We don't render our own children - defer to the default behaviour,
    // which allows for interweaving compoenents with non-components.
    @Override
    public boolean getRendersChildren() {
        return false;
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        SkipHyperlink link = (SkipHyperlink) component;
        if (!link.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();

        // Get the theme
        Theme theme = ThemeUtilities.getTheme(context);

        // Components must have a top-level element with the component ID
        Integer index = null;
        int tabIndex = link.getTabIndex();
        if (tabIndex != Integer.MIN_VALUE) {
            index = new Integer(tabIndex);
        }

        String styleClass = (link.getStyleClass() != null)
                ? link.getStyleClass()
                : theme.getStyleClass(ThemeStyles.SKIP_WHITE);

        RenderingUtilities.renderSkipLink("", styleClass, link.getStyle(),
                link.getDescription(), index, link, context);
        writer.write("\n"); //NOI18N        
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        SkipHyperlink link = (SkipHyperlink) component;
        if (!link.isRendered()) {
            return;
        }
        RenderingUtilities.renderAnchor("", component, context);
    }
}
