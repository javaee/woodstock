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
import com.sun.webui.jsf.component.Legend;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for an {@link Legend} component.</p>
 * 
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.Legend"))
public class LegendRenderer extends AbstractRenderer {

    // Default position.
    private static final String DEFAULT_POSITION = "right";

    /** Creates a new instance of LegendRenderer */
    public LegendRenderer() {
        // default constructor
    }

    /**
     * Renders the legend.
     * 
     * @param context The current FacesContext
     * @param component The Legend object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        if (context == null || component == null || writer == null) {
            throw new NullPointerException();
        }

        Legend legend = (Legend) component;

        if (!legend.isRendered()) {
            return;
        }

        // Render the outer div
        renderOuterDiv(context, legend, writer);
        // Render the legend image
        RenderingUtilities.renderComponent(legend.getLegendImage(), context);
        writer.write("&nbsp;"); // NOI18N
        // Render the legend text
        String text = (legend.getText() != null) ? legend.getText() : getTheme().getMessage("Legend.requiredField"); //NOI18N
        writer.writeText(text, null);
        // Close the outer div
        writer.endElement("div"); //NOI18N
    }

    /** 
     * Renders the outer div which contains the legend.
     * 
     * @param context The current FacesContext
     * @param alert The Legend object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderOuterDiv(FacesContext context,
            Legend legend, ResponseWriter writer) throws IOException {

        String style = legend.getStyle();
        String id = legend.getClientId(context);
        String divStyleClass = getTheme().getStyleClass(
                ThemeStyles.LABEL_REQUIRED_DIV);
        String align = (legend.getPosition() != null) ? legend.getPosition() : DEFAULT_POSITION;

        writer.startElement("div", legend); //NOI18N
        if (id != null) {
            writer.writeAttribute("id", id, null);  //NOI18N
        }
        writer.writeAttribute("align", align, null); //NOI18N
        if (style != null) {
            writer.writeAttribute("style", style, "style");  //NOI18N
        }
        RenderingUtilities.renderStyleClass(context, writer,
                (UIComponent) legend, divStyleClass);
    }

    /*
     * Utility to get theme.
     */
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}

