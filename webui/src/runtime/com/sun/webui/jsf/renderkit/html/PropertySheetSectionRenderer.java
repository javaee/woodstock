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

import java.io.IOException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.jsf.component.PropertySheetSection;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renders a version page.</p>
 */
@com.sun.faces.annotation.Renderer(@com.sun.faces.annotation.Renderer.Renders(componentFamily = "com.sun.webui.jsf.PropertySheetSection"))
public class PropertySheetSectionRenderer extends Renderer {

    /**
     * Creates a new instance of PropertySheetSectionRenderer.
     */
    public PropertySheetSectionRenderer() {
    }

    /**
     * This renderer renders the component's children.
     */
    @Override
    public boolean getRendersChildren() {
        return true;
    }

    /**
     * Render a property sheet.
     * 
     * @param context The current FacesContext
     * @param component The PropertySheet object to render
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        if (!component.isRendered()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();

        PropertySheetSection propertySheetSection =
                (PropertySheetSection) component;

        // Get the theme
        //
        Theme theme = ThemeUtilities.getTheme(context);

        renderPropertySheetSection(context, propertySheetSection, theme,
                writer);
    }

    // There is an extensive use of the request map by the
    // template renderer.
    // The setAttribute handler places the key/value pair in the
    // request map.
    //
    /**
     * Render the property sheet sections.
     * 
     * @param context The current FacesContext
     * @param propertySheet The PropertySheet object to render
     * @param theme The Theme to reference.
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderPropertySheetSection(FacesContext context,
            PropertySheetSection propertySheetSection, Theme theme,
            ResponseWriter writer) throws IOException {

        int numChildren = propertySheetSection.getSectionChildrenCount();
        if (numChildren <= 0) {
            return;
        }

        writer.startElement(HTMLElements.DIV, propertySheetSection);
        writer.writeAttribute(HTMLAttributes.ID,
                propertySheetSection.getClientId(context), "id");//NOI18N
        String propValue = RenderingUtilities.getStyleClasses(context,
                propertySheetSection,
                theme.getStyleClass(ThemeStyles.CONTENT_FIELDSET));
        writer.writeAttribute(HTMLAttributes.CLASS, propValue, null);

        // There was a distinction made between ie and other browsers.
        // If the browser was ie, fieldsets were used, and if not
        // divs were used. Why ? Just use divs here.
        //
        writer.startElement(HTMLElements.DIV, propertySheetSection);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CONTENT_FIELDSET_DIV), null);

        // Render the section label
        // Why isn't this a label facet on PropertySheetSection, too ?
        //
        propValue = propertySheetSection.getLabel();
        if (propValue != null) {
            writer.startElement(HTMLElements.DIV, propertySheetSection);
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.CONTENT_FIELDSET_LEGEND_DIV),
                    null);
            writer.writeText(propValue, null);
            writer.endElement(HTMLElements.DIV);
        }

        renderProperties(context, propertySheetSection, theme, writer);

        writer.endElement(HTMLElements.DIV);
        writer.endElement(HTMLElements.DIV);
    }

    /**
     * Render a required fields legend.
     * If <code>propertySheet.getRequiredFields</code> returns null
     * a spacer is rendered.
     * 
     * @param context The current FacesContext
     * @param propertySheet The PropertySheet object to render
     * @param theme The Theme to reference.
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderProperties(FacesContext context,
            PropertySheetSection propertySheetSection, Theme theme,
            ResponseWriter writer) throws IOException {


        List properties = propertySheetSection.getVisibleSectionChildren();

        writer.startElement(HTMLElements.TABLE, propertySheetSection);
        writer.writeAttribute(HTMLAttributes.BORDER, 0, null);
        writer.writeAttribute(HTMLAttributes.CELLSPACING, 0, null);
        writer.writeAttribute(HTMLAttributes.CELLPADDING, 0, null);
        writer.writeAttribute(HTMLAttributes.TITLE, "", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.ROLE, HTMLAttributes.ROLE_PRESENTATION, null);

        // Unfortunately the PropertyRenderer needs to render
        // a TR and TD since we are opening a table context here.
        // This can't be changed easily unless a strategy like the
        // radio button and checkbox group renderer is used, where there is
        // a table layout renderer. I'm not sure if that is sufficiently
        // robust to handle "properties".
        //

        for (Object property : properties) {
            RenderingUtilities.renderComponent((UIComponent) property, context);
        }

        writer.endElement(HTMLElements.TABLE);
    }

    /**
     * Render a property sheet.
     * 
     * @param context The current FacesContext
     * @param component The PropertySheet object to render
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }
}
