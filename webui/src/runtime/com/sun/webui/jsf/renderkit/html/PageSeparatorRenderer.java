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

/*
 * $Id: PageSeparatorRenderer.java,v 1.1.20.1 2009-12-29 04:52:46 jyeary Exp $
 */
package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.PageSeparator;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * <p>Renderer for a {@link PageSeparator} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.PageSeparator"))
public class PageSeparatorRenderer extends AbstractRenderer {


    // ======================================================== Static Variables
    /**
     * <p>The set of String pass-through attributes to be rendered.</p>
     */
    private static final String stringAttributes[] = {"onClick", "onDblClick", "onMouseUp", //NOI18N
        "onMouseDown", "onMouseMove", "onMouseOut", "onMouseOver"}; //NOI18N


    // -------------------------------------------------------- Renderer Methods
    /**
     * <p>Render the appropriate element start, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
    }

    /**
     * <p>Render the appropriate element attributes, followed by the
     * label content, depending on whether the <code>for</code> property
     * is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component StaticText component
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderAttributes(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
    }

    /**
     * <p>Render the appropriate element end, depending on whether the
     * <code>for</code> property is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        PageSeparator pageSep = (PageSeparator) component;

        writer.startElement("table", component);
        String style = pageSep.getStyle();
        if (style != null) {
            writer.writeAttribute("style", style, null); // NOI18N
        }
        RenderingUtilities.renderStyleClass(context, writer, component, null);
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("width", "100%", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeAttribute("cellspacing", "0", null); // NOI18N        
        writer.startElement("tr", component);
        writer.startElement("td", component);
        writer.writeAttribute("colspan", "3", null); // NOI18N

        RenderingUtilities.renderSpacer(context, writer, component, 30, 1);
        writer.endElement("td");
        writer.endElement("tr");
        writer.startElement("tr", component);
        writer.startElement("td", component);
        RenderingUtilities.renderSpacer(context, writer, component, 1, 10);
        writer.endElement("td");
        writer.startElement("td", component);
        Theme theme = ThemeUtilities.getTheme(context);

        writer.writeAttribute("class", theme.getStyleClass(
                ThemeStyles.TITLE_LINE), null); // NOI18N
        writer.writeAttribute("width", "100%", null); // NOI18N
        RenderingUtilities.renderSpacer(context, writer, component, 1, 1);
        writer.endElement("td");
        writer.startElement("td", component);
        RenderingUtilities.renderSpacer(context, writer, component, 1, 10);
        writer.endElement("td");
        writer.endElement("tr");
        writer.endElement("table");
    }
    // --------------------------------------------------------- Private Methods
}
