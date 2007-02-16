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

import com.sun.faces.annotation.Renderer;
import java.beans.Beans;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.html.HTMLElements;
import com.sun.webui.html.HTMLAttributes;

import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.IFrame;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.VersionPage;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.Bundle;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;


/**
 * <p>Renders a version page.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.VersionPage"))
public class VersionPageRenderer extends AbstractRenderer {

    /**
     * Default height and width for the image
     */
    private static final String DEFAULT_VERSIONINFO_HEIGHT = "330";

    /** Creates a new instance of VersionPageRenderer */
    public VersionPageRenderer() {
    }
    
    /**
     * Render a version page.
     * 
     * @param context The current FacesContext
     * @param component The VersionPage object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        if (context == null || component == null || writer == null) {
            throw new NullPointerException();
        }

	VersionPage versionPage = (VersionPage) component;
	// Get the theme
	Theme theme = ThemeUtilities.getTheme(context);

        String style = versionPage.getStyle(); 
	 writer.startElement(HTMLElements.DIV, versionPage);
	 writer.writeAttribute(HTMLAttributes.ID,
                versionPage.getClientId(context), HTMLAttributes.ID);
        
        if (style != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, style, null);
        }
        String versionBodyStyle = theme.getStyleClass(ThemeStyles.VERSION_BODY);
        String styleClass = RenderingUtilities.getStyleClasses(context,
		component, versionBodyStyle);
        if (styleClass != null) {
            writer.writeAttribute(HTMLAttributes.CLASS, styleClass, null);
        }
        
	// Render the masthead
	renderVersionMasthead(context, versionPage, writer, theme);
        
	 style = theme.getStyleClass(ThemeStyles.VERSION_MARGIN); 
	writer.startElement(HTMLElements.DIV, versionPage);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);

	// Render the version number, legal notice, close button
	renderVersionInformation(context, versionPage, writer, theme);
        writer.endElement(HTMLElements.DIV);	
	renderCloseButton(context, versionPage, writer, theme);
        writer.endElement(HTMLElements.DIV);	
    }    

    /**
     * Renders the version masthead at the top of the version page
     *     
     * @param context The current FacesContext
     * @param versionPage The VersionPage object to use
     * @param writer The current ResponseWriter
     * @param theme The current theme
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderVersionMasthead(FacesContext context, 
            VersionPage versionPage, ResponseWriter writer,
	    Theme theme) throws IOException {

	// Get the facet with the product name image, logo, etc.
	UIComponent mhFacet = versionPage.getFacet("identityContent");

	if (mhFacet != null) {
	    // Render the contents of the facet
	    RenderingUtilities.renderComponent(mhFacet, context);
	} else {
	    renderVersionMastheadImages(context, versionPage, theme, writer);
	}
    }

    /**
     * Renders the images in the version page masthead
     *     
     * @param context The current FacesContext
     * @param versionPage The VersionPage object to use
     * @param corplogo The java logo icon to render
     * @param writer The current ResponseWriter
     * @param theme The current theme
     * @deprecated
     * @see #renderVersionMastheadImages(FacesContext, VersionPage,
     * Theme, ResponseWriter)
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderVersionMastheadImages(FacesContext context, 
	    VersionPage versionPage, Icon corplogo, ResponseWriter writer, 
	    Theme theme) throws IOException {

	renderVersionMastheadImages(context, versionPage, theme, writer);
    }

    /**
     * Render the images in the verion page masthead.
     * Calls <code>getProductImage</code> and <code>getCorporateLogo</code>
     * to obtain images for the masthead. This method lays out the
     * surround HTML even if there is no product image. However if there
     * is no corporate logo, the surrounding HTML is not rendered.
     *
     * @param context The current FacesContext
     * @param versionPage The VersionPage object to use
     * @param theme The current theme
     * @param writer The current ResponseWriter
     */
    protected void renderVersionMastheadImages(FacesContext context,
	    VersionPage versionPage, Theme theme, ResponseWriter writer)
	    throws IOException {

	// Render the version brand image 
	String style = theme.getStyleClass(ThemeStyles.VERSION_MASTHEAD_BODY);
	writer.startElement(HTMLElements.DIV, versionPage);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);
        writer.writeText("\n", null); //NOI18N

	// Render the product name image
	style = theme.getStyleClass(ThemeStyles.VERSION_PRODUCT_TD);
	writer.startElement(HTMLElements.DIV, versionPage);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);

	style = theme.getStyleClass(ThemeStyles.VERSION_PRODUCT_DIV);
	writer.startElement(HTMLElements.DIV, versionPage);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);
	
	// Instantiate the productname image and render it
	String id = versionPage.getId();
	int size = 0;
	String altText = null;

	UIComponent image = getProductImage(context, versionPage, theme);
	if (image != null) {
	    RenderingUtilities.renderComponent(image, context);
	}

	writer.endElement(HTMLElements.DIV); //NOI18N
	writer.endElement(HTMLElements.DIV); //NOI18N

	// Render the Corporate logo
	//
	image = getCorporateLogo(context, versionPage, theme);
	if (image != null) {

	    style = theme.getStyleClass(ThemeStyles.VERSION_LOGO_TD);
	    writer.startElement(HTMLElements.DIV, versionPage);
  	    writer.writeAttribute(HTMLAttributes.CLASS, style, null);

	    style = theme.getStyleClass(ThemeStyles.VERSION_LOGO_DIV);
	    writer.startElement(HTMLElements.DIV, versionPage);
  	    writer.writeAttribute(HTMLAttributes.CLASS, style, null);

	    RenderingUtilities.renderComponent(image, context);

	    writer.endElement(HTMLElements.DIV); //NOI18N
	    writer.endElement(HTMLElements.DIV); //NOI18N
	}

	writer.endElement(HTMLElements.DIV); //NOI18N
    }

    /**
     * Renders the version information based on the attributes specified
     *     
     * @param context The current FacesContext
     * @param versoinpage The VersionPage object to use
     * @param writer The current ResponseWriter
     * @param theme The current theme
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderVersionInformation(FacesContext context, 
            VersionPage versionPage, 
	    ResponseWriter writer, Theme theme) throws IOException {

	String file = versionPage.getVersionInformationFile();
	if (file != null) {
	    renderVersionInformationFile(context, versionPage, writer);
	} else {
	    renderVersionInformationInline(context, versionPage, 
					   writer, theme);
	}
    }

    /**
     * Renders the Version information contained within an html file.
     *     
     * @param context The current FacesContext
     * @param versionPage The VersionPage object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderVersionInformationFile(FacesContext context, 
            VersionPage versionPage, 
	    ResponseWriter writer) throws IOException {

	IFrame iframe = new IFrame();

	iframe.setId(versionPage.getId() + 
		     "_versionInformationIFrame"); //NOI18N
	iframe.setAlign("left"); //NOI18N
	iframe.setHeight(DEFAULT_VERSIONINFO_HEIGHT);
	iframe.setWidth("100%"); //NOI18N
	iframe.setScrolling("auto"); //NOI18N
	iframe.setUrl(versionPage.getVersionInformationFile());
	
        RenderingUtilities.renderComponent(iframe, context);
    }

    /**
     * Renders the Version information inline in the version page
     *     
     * @param context The current FacesContext
     * @param versionPage The VersionPage object to use
     * @param writer The current ResponseWriter
     * @param theme The current theme
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderVersionInformationInline(FacesContext context, 
            VersionPage versionPage, 
	    ResponseWriter writer, Theme theme) throws IOException {

	String text = null;

	// Render the version number
	String style = theme.getStyleClass(ThemeStyles.VERSION_HEADER_TEXT); 
	writer.startElement(HTMLElements.DIV, versionPage);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);
	if ((text = versionPage.getVersionString()) != null)
	    writer.writeText(text, "versionString"); //NOI18N
	writer.endElement(HTMLElements.DIV);

	// Render the legal notice
	style = theme.getStyleClass(ThemeStyles.VERSION_TEXT); 
	writer.startElement(HTMLElements.DIV, versionPage);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);
	if ((text = versionPage.getCopyrightString()) != null)
	    writer.writeText(text, "copyrightString"); //NOI18N
	writer.endElement(HTMLElements.DIV);

    }

    /**
     * Renders the Close button at the bottom of the version page
     *     
     * @param context The current FacesContext
     * @param versionPage The VersionPage object to use
     * @param writer The current ResponseWriter
     * @param theme The current theme
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderCloseButton(FacesContext context, 
            VersionPage versionPage, 
	    ResponseWriter writer, Theme theme) throws IOException {

        // Instantiate a button
	Button button = new Button();

	button.setId(versionPage.getId() + 
		     "_versionPageCloseButton"); //NOI18N
	button.setText(ThemeUtilities.getTheme(context).
		       getMessage("Version.closeButton")); //NOI18N
	button.setPrimary(true);
	button.setOnClick("javascript: parent.close(); return false;"); //NOI18N

	String style = 
		theme.getStyleClass(ThemeStyles.VERSION_BUTTON_MARGIN_DIV); 
	writer.startElement(HTMLElements.DIV, versionPage);
	writer.writeAttribute(HTMLAttributes.CLASS, style, null);
	
        RenderingUtilities.renderComponent(button, context);

	writer.endElement(HTMLElements.DIV); //NOI18N
        
	// FIXME: This must not be done here. When the focus changes
	// are put back this must be changed to use
	// RenderingUtilities.setRequestFocusElementId()
	//

        writer.startElement(HTMLElements.SCRIPT, versionPage);
        writer.writeAttribute(HTMLAttributes.TYPE,
		"text/javascript", null); //NOI18N
        writer.write("document.forms[0]." + button.getClientId(context) + 
		".focus()"); //NOI18N
        writer.endElement(HTMLElements.SCRIPT);
    }
    
    /**
     * Returns a component suitable for the corporate logo.
     * This implementation returns an ImageComponent as defined 
     * in the theme by <code>ThemeImages.VERSION_CORPLOGO</code>
     * by calling <code>ThemeUtilities.getImage</code>. 
     * If this theme property is not defined, null is returned.
     */
    /**
     * Return a UIComponent suitable to render for the brand image.
     * If the <code>brandImage</code> facet exists return it, otherwise
     * if the <code>masthead.getBrandImageURL()</code> exists
     * create a compoennt initialized with appropriate
     * values and return it.
     * <p>In this implementation, if a value for 
     * <code>getBrandImageUrl</code> is not specified it returns an
     * <code>Icon</code> component, by calling
     * <code>ThemeUtilities.getIcon</code> with the
     * <code>ThemeImages.MASTHEAD_CORPLOGO</code> key. If there is
     * no image for this key, return <code>null</code>
     * </p>
     */
    protected UIComponent getCorporateLogo(FacesContext context,
		VersionPage versionPage, Theme theme) {

	// Check if the javalogo image is defined in the theme
	// If not, assume were using the defaulttheme...
	Icon corpLogo = null;
	try {
	    // If there is no path then there is no default.
	    //
	    String imagePath = theme.getImagePath(ThemeImages.VERSION_CORPLOGO);
	    if (imagePath == null) {
		return null;
	    }
	    corpLogo = ThemeUtilities.getIcon(theme,
		ThemeImages.VERSION_CORPLOGO);
	    if (corpLogo != null) {
		corpLogo.setId(versionPage.getId() + 
		    "_versionPageJavaLogo"); //NOI18N 
		corpLogo.setParent(versionPage);
	    }
	} catch (Exception e) {
	    // Don't care.
	}
	return corpLogo;
    }

    /**
     * Returns a component suitable for the product image.
     * This implementations returnd an <code>ImageComponent</code>
     * created from the <code>versionPage</code> properties.
     * If <code>versionPage.getProductImageURL</code> returns <code>null</code>
     * or an empty string, null is returned.
     */
    protected UIComponent getProductImage(FacesContext context,
	    VersionPage versionPage, Theme theme) {

	String imageAttr = versionPage.getProductImageURL();
	if (imageAttr == null || imageAttr.trim().length() == 0) {
	    return null;
	}

	ImageComponent image = new ImageComponent();
	image.setId(versionPage.getId() + "_versionPageProductImage"); //NOI18N
	image.setUrl(imageAttr);
	imageAttr = versionPage.getProductImageDescription();
	if (imageAttr != null && imageAttr.trim().length() != 0) {
	    image.setAlt(imageAttr);
	}
	int dim = versionPage.getProductImageHeight();
	if (dim > 0) {
	    image.setHeight(dim);
	}
	dim = versionPage.getProductImageWidth();
	if (dim > 0) {
	    image.setWidth(dim);
	}
	image.setBorder(0);

	return image;
    }
}
