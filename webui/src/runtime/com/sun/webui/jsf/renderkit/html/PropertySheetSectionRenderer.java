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

import java.beans.Beans;
import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.sun.webui.html.HTMLElements;
import com.sun.webui.html.HTMLAttributes;

import com.sun.webui.jsf.component.Anchor;
import com.sun.webui.jsf.component.Legend;
import com.sun.webui.jsf.component.Property;
import com.sun.webui.jsf.component.PropertySheetSection;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renders a version page.</p>
 */
@com.sun.faces.annotation.Renderer(
        @com.sun.faces.annotation.Renderer.Renders(componentFamily="com.sun.webui.jsf.PropertySheetSection"))
public class PropertySheetSectionRenderer extends Renderer {

    /**
     * Creates a new instance of PropertySheetSectionRenderer.
     */
    public PropertySheetSectionRenderer() {
    }
    
    /**
     * This renderer renders the component's children.
     */
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

	// Render the style attribute
	propValue = propertySheetSection.getStyle();
	if (propValue != null && propValue.length() != 0) {
	    writer.writeAttribute(HTMLAttributes.STYLE, propValue, 
		HTMLAttributes.STYLE);
	}

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

	// Unfortunately the PropertyRenderer needs to render
	// a TR and TD since we are opening a table context here.
	// This can't be changed easily unless a strategy like the
	// radio button and checkbox group renderer is used, where there is 
	// a table layout renderer. I'm not sure if that is sufficiently
	// robust to handle "properties".
	//

	for (Object property : properties) {
	    RenderingUtilities.renderComponent((UIComponent)property, context);
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
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }    
}
