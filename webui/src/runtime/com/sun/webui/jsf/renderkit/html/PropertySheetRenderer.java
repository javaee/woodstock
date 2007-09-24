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
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;

import com.sun.webui.jsf.component.Anchor;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.IconHyperlink;
import com.sun.webui.jsf.component.Legend;
import com.sun.webui.jsf.component.PropertySheet;
import com.sun.webui.jsf.component.PropertySheetSection;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * Renders a PropertySheet component.
 */
@com.sun.faces.annotation.Renderer(
        @com.sun.faces.annotation.Renderer.Renders(componentFamily="com.sun.webui.jsf.PropertySheet"))
public class PropertySheetRenderer extends javax.faces.render.Renderer {

    public static final String JUMPTOSECTIONTOOLTIP =
		    "propertySheet.jumpToSectionTooltip";

    public static final String JUMPTOTOPTOOLTIP =
		    "propertySheet.jumpToTopTooltip";

    public static final String JUMPTOTOP =
		    "propertySheet.jumpToTop";

    /**
     * Creates a new instance of PropertySheetRenderer.
     */
    public PropertySheetRenderer() {
    }
    
    /**
     * This renderer renders the component's children.
     * @return true
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

	PropertySheet propertySheet = (PropertySheet) component;

	// Get the theme
	//
	Theme theme = ThemeUtilities.getTheme(context);

	writer.startElement(HTMLElements.DIV, component);
	writer.writeAttribute(HTMLAttributes.ID,
	    component.getClientId(context), "id");//NOI18N

	String propValue = RenderingUtilities.getStyleClasses(context,
		component, theme.getStyleClass(ThemeStyles.PROPERTY_SHEET));
	writer.writeAttribute(HTMLAttributes.CLASS, propValue, null);

        propValue = propertySheet.getStyle();
	if (propValue != null) {
	    writer.writeAttribute(HTMLAttributes.STYLE, propValue, 
		HTMLAttributes.STYLE);
	}

	renderJumpLinks(context, propertySheet, theme, writer);
	renderRequiredFieldsLegend(context, propertySheet, theme, writer);
	renderPropertySheetSections(context, propertySheet, theme, writer);

        writer.endElement(HTMLElements.DIV);
    }    

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
    protected void renderPropertySheetSections(FacesContext context,
	    PropertySheet propertySheet, Theme theme, ResponseWriter writer)
	    throws IOException {

	// From propertysheetsection template
	// 
	// <!-- Separator before Section (except first one, unless jumplinks
	// are rendered) -->
	// This has to be done here since we know if jumpllinks were 
	// rendered, not the section. There was probably a request map
	// attribute set to convey this. We control the spacer too.
	//
	List sections = propertySheet.getVisibleSections();
	boolean haveJumpLinks = propertySheet.isJumpLinks() &&
		sections.size() > 1;
	boolean renderSpacer = false;
	for (Object section : sections) {

	    renderAnchor(context, (PropertySheetSection)section, writer);

	    // Orginally the spacer came after the section's 
	    // opening div. Let's do it before. It should be equivalent.
	    // And the PropertySheet should control separators.
	    //
	    // If there are jumplinks render a spacer if there is more
	    // than one section.
	    // If there are no jumplinks, render a spacer unless
	    // it is the first section.
	    //
	    if (haveJumpLinks || renderSpacer) {
		renderSpacer(context, (PropertySheetSection)section, theme,
		    writer);
	    } else {
		renderSpacer = true;
	    }
	    RenderingUtilities.renderComponent((UIComponent)section, context);

	    if (haveJumpLinks && sections.size() > 1) {
		renderJumpToTopLink(context, propertySheet, theme, writer);
	    }
	}
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
    protected void renderRequiredFieldsLegend(FacesContext context,
	    PropertySheet propertySheet, Theme theme, ResponseWriter writer)
	    throws IOException {

	// This should be a facet.
	//
	String requiredFields = propertySheet.getRequiredFields();
	// Why isn't this boolean ?
	//
	if (requiredFields != null && 
		requiredFields.equalsIgnoreCase("true")) {//NOI18N
	    Legend legend = new Legend();
	    legend.setParent(propertySheet);
	    legend.setId(propertySheet.getId() + "_legend"); //NOI18N
	    legend.setStyleClass(
		    theme.getStyleClass(ThemeStyles.CONTENT_REQUIRED_DIV));
	    RenderingUtilities.renderComponent(legend, context);
	} else {
	    // FIXME : Needs to be theme.
	    //
	    Icon spacer = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
	    spacer.setParent(propertySheet);
	    spacer.setId(propertySheet.getId() + "_legend");
	    spacer.setHeight(20);
	    spacer.setWidth(1);
	    RenderingUtilities.renderComponent(spacer, context);
	}
    }

    /**
     * Render a set of jump links.
     * 
     * @param context The current FacesContext
     * @param propertySheet The PropertySheet object to render
     * @param theme The Theme to reference.
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderJumpLinks(FacesContext context,
	    PropertySheet propertySheet, Theme theme, ResponseWriter writer)
	    throws IOException {

	// Don't render any jump links if they are not requested.
	//
	if (!propertySheet.isJumpLinks()) {
	    return;
	}
	// Or if there are no visible sections
	//
	List sections = propertySheet.getVisibleSections();
	int numSections = sections.size();
	if (numSections <= 1) {
	    return;
	}

	// There seems to be a distinction if there are 4, 5 to 9,
	// and greater than 9 property sheet sections. This should be a
	// theme configurable parameter.
	//
	// If there are less than 5 sections, there will be
	// 2 jump links per row.
	// If there are greater than 5 sections there will be
	// 3 junmp links per row.
	// If there are more than 10 sections, there will be
	// 4 jump links per row.
	//
	// Determine the number of sections
	//
	// Start the layout for the property sheet sections
	// jump link area
	//
	int jumpLinksPerRow = numSections < 5 ? 2 : 
		(numSections > 4 && numSections < 10 ? 3 : 4);

	// Start a div for the jump links table
	//
	writer.startElement(HTMLElements.DIV, propertySheet);
	writer.writeAttribute(HTMLAttributes.CLASS, 
	    theme.getStyleClass(ThemeStyles.CONTENT_JUMP_SECTION_DIV), null);

	writer.startElement(HTMLElements.TABLE, propertySheet);
	writer.writeAttribute(HTMLAttributes.BORDER, 0, null);
	writer.writeAttribute(HTMLAttributes.CELLSPACING, 0, null);
	writer.writeAttribute(HTMLAttributes.CELLPADDING, 0, null);
	writer.writeAttribute(HTMLAttributes.TITLE, "", null); //NOI18N

	// Optimize, just get the needed selectors once.
	//
	String jumpLinkDivStyle =
	    theme.getStyleClass(ThemeStyles.CONTENT_JUMP_LINK_DIV);
	String jumpLinkStyle =
	    theme.getStyleClass(ThemeStyles.JUMP_LINK);

	Iterator sectionIterator = sections.iterator();
	while (sectionIterator.hasNext()) {


	    writer.startElement(HTMLElements.TR, propertySheet);

	    for (int i = 0; i < jumpLinksPerRow; ++i) {

		PropertySheetSection section = 
		    (PropertySheetSection)sectionIterator.next();

		writer.startElement(HTMLElements.TD, propertySheet);
		writer.startElement(HTMLElements.SPAN, propertySheet);
		writer.writeAttribute(HTMLAttributes.CLASS, jumpLinkDivStyle,
		    null);

		IconHyperlink jumpLink = new IconHyperlink();
		jumpLink.setId(section.getId() + "_jumpLink");//NOI18N
		jumpLink.setParent(propertySheet);
		jumpLink.setIcon(ThemeImages.HREF_ANCHOR);
		jumpLink.setBorder(0);
		// Shouldn't this come from the section ?
		//
		String propValue = theme.getMessage(JUMPTOSECTIONTOOLTIP);
		if (propValue != null) {
		    jumpLink.setAlt(propValue);
		    jumpLink.setToolTip(propValue);
		}
	
		propValue = section.getLabel();
		if (propValue != null) {
		    jumpLink.setText(propValue);
		}
		jumpLink.setUrl("#_" + section.getId());
		jumpLink.setStyleClass(jumpLinkStyle);

		// Render the jump link
		// 
		RenderingUtilities.renderComponent(jumpLink, context);

		writer.endElement(HTMLElements.SPAN);
		writer.endElement(HTMLElements.TD);

		// If we haven't created enough cells, we should.
		//
		if (!sectionIterator.hasNext()) {
		    while (++i < jumpLinksPerRow) {
			writer.startElement(HTMLElements.TD, propertySheet);
			writer.startElement(HTMLElements.SPAN, propertySheet);
			writer.writeAttribute(HTMLAttributes.CLASS, 
			    jumpLinkDivStyle, null);
			writer.endElement(HTMLElements.SPAN);
			writer.endElement(HTMLElements.TD);
		    }
		    break;
		}
	    }
	    writer.endElement(HTMLElements.TR);
	}
	writer.endElement(HTMLElements.TABLE);
	writer.endElement(HTMLElements.DIV);
    }

    /**
     * Does not participate in rendering a PropertySheet.
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

    /**
     * Render an anchor to the section.
     * 
     * @param context The current FacesContext
     * @param propertySheetSection The PropertySheetSection about to be rendered.
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderAnchor(FacesContext context,
	    PropertySheetSection propertySheetSection, ResponseWriter writer)
	    throws IOException {

	Anchor anchor = new Anchor();
	anchor.setParent(propertySheetSection);
	anchor.setId("_" + propertySheetSection.getId());//NOI18N
	RenderingUtilities.renderComponent(anchor, context);
    }

    /**
     * Render a spacer before the section.
     * 
     * @param context The current FacesContext
     * @param propertySheet The PropertySheet being rendered
     * @param propertySheetSection The PropertySheetSection about to be rendered.
     * @param theme The Theme to reference.
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderSpacer(FacesContext context,
	    PropertySheetSection propertySheetSection, Theme theme,
	    ResponseWriter writer)
	    throws IOException {

	Icon spacer = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
	writer.startElement(HTMLElements.DIV, propertySheetSection);
	writer.writeAttribute(HTMLAttributes.CLASS,
		theme.getStyleClass(ThemeStyles.CONTENT_LIN), null);

	spacer.setId(propertySheetSection.getId() + "_dot1"); //NOI18N
	spacer.setParent(propertySheetSection);
	spacer.setHeight(1);
	spacer.setWidth(1);
	RenderingUtilities.renderComponent(spacer, context);
	writer.endElement(HTMLElements.DIV);
    }

    /**
     * Render the back to top link
     * 
     * @param context The current FacesContext
     * @param propertySheet The PropertySheet being rendered
     * @param propertySheetSection The PropertySheetSection about to be rendered.
     * @param theme The Theme to reference.
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderJumpToTopLink(FacesContext context,
	    PropertySheet propertySheet, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.startElement(HTMLElements.DIV, propertySheet);
	writer.writeAttribute(HTMLAttributes.CLASS,
	    theme.getStyleClass(ThemeStyles.CONTENT_JUMP_TOP_DIV), null);

	// Should be facets ?
	//
	IconHyperlink jumpLink = new IconHyperlink();
	jumpLink.setIcon(ThemeImages.HREF_TOP);
	jumpLink.setBorder(0);
	// Shouldn't this come from the section ?
	//
	String propValue = theme.getMessage(JUMPTOTOPTOOLTIP);
	if (propValue != null) {
	    jumpLink.setAlt(propValue);
	    jumpLink.setToolTip(propValue);
	}

	propValue = theme.getMessage(JUMPTOTOP);
	if (propValue != null) {
	    jumpLink.setText(propValue);
	}
	jumpLink.setUrl("#"); //NOI18N
	jumpLink.setStyleClass(theme.getStyleClass(
	    ThemeStyles.JUMP_TOP_LINK));

	// Render the jump link
	// 
	RenderingUtilities.renderComponent(jumpLink, context);

	writer.endElement(HTMLElements.DIV);
    }
}
