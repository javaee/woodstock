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

/*
 * PagetitleRenderer.java
 */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.component.ContentPageTitle;
import com.sun.webui.jsf.component.PanelGroup;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * Renders a Pagetitle component.
 *
 * @author  Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.ContentPageTitle"))
public class ContentPageTitleRenderer extends javax.faces.render.Renderer {
    
    public final static String BOTTOM_ID = "_bottom";
    
    /** Creates a new instance of PagetitleRenderer. */
    public ContentPageTitleRenderer() {
        // default constructor
    }
    
    /**
     * <p>Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns <code>false</code>.</p>
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    /**
     * <p>Render the Pagetitle component start.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        ContentPageTitle pagetitle = (ContentPageTitle) component;
        Theme theme = ThemeUtilities.getTheme(context);
        ResponseWriter writer = context.getResponseWriter();
        String style = pagetitle.getStyle();
                
        writer.startElement("div", pagetitle); //NOI18N
        writer.writeAttribute("id",
                pagetitle.getClientId(context), "id"); //NOI18N
        
        if (style != null) {
            writer.writeAttribute("style", style, null); // NOI18N
        }
        
        String styleClass = RenderingUtilities.getStyleClasses(context,
		component, null);
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, null); // NOI18N
        }
                
        startLayoutTable(writer, pagetitle, "bottom", true, null, "bottom");
        
        // render the page title itself
        renderPageTitle(context, pagetitle, writer, theme);
        
        // check for pageButtonsTop facet to use
        UIComponent facet = pagetitle.getFacet("pageButtonsTop");
        
        if (facet != null && facet.isRendered()) {
            renderPageButtons(context, pagetitle, writer,
                    theme.getStyleClass(ThemeStyles.TITLE_BUTTON_DIV), facet);
        }
        
        endLayoutTable(writer);
    }
    
    /**
     * Any facets related to the top of the page title will be rendered along
     * with the children found in the body content.
     *
     * @param context The current FacesContext
     * @param component The Pagetitle component
     */
    public void encodeChildren(FacesContext context, UIComponent component)
    throws IOException {
        
        ContentPageTitle pagetitle = (ContentPageTitle) component;
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        
        // render the page help if any
        if (pagetitle.getHelpText() != null ||
                pagetitle.getFacet("pageHelp") != null) {
            renderPageHelp(context, pagetitle, theme, writer);
        }
        
        boolean showActions = pagetitle.getFacet("pageActions") != null;
        boolean showViews = pagetitle.getFacet("pageViews") != null;
        
        // render the page actions and views if any
        if (showActions || showViews) {
            String tdAlign = null;
            
            if (!showActions) {
                // actions not shown, first td align must be right for views
                tdAlign = "right";
            }
            
            startLayoutTable(writer, pagetitle, "bottom", true, tdAlign,
                    "bottom");
            
            if (showActions) {
                renderPageActions(context, pagetitle, theme, writer);
            }
            
            if (showViews) {
                if (showActions) {
                    // actions are displayed, start new td for views
                    writer.startElement("td", pagetitle);
                    writer.writeAttribute("align", "right", null); // NOI18N
                    writer.writeAttribute("valign", "bottom", null); // NOI18N
                    writer.writeAttribute("nowrap", "nowrap", null); // NOI18N
                }
                
                renderPageViews(context, pagetitle, theme, writer);
            }
            writer.endElement("td");
            endLayoutTable(writer);
        }
        
        // perform the normal encoding of all other children
        super.encodeChildren(context, component);
    }
    
    /**
     * Render the pageActions facet.
     *
     * @param context The current FacesContext
     * @param pagetitle The Pagetitle component
     * @param writer The current ResponseWriter
     */
    protected void renderPageActions(FacesContext context,
            ContentPageTitle pagetitle,
            Theme theme,
            ResponseWriter writer)
            throws IOException {
        
        UIComponent pageActions = pagetitle.getFacet("pageActions");
        
        if (pageActions == null) {
            return;
            //throw new NullPointerException("pageActions facet null");
        }
        
        writer.startElement("div", pagetitle);
        writer.writeAttribute("class", //NOI18N
                theme.getStyleClass(ThemeStyles.TITLE_ACTION_DIV),
                null);
        
        RenderingUtilities.renderComponent(pageActions, context);
        
        writer.endElement("div");
    }
    
    /**
     * Render the pageViews facet.
     *
     * @param context The current FacesContext
     * @param pagetitle The Pagetitle component
     * @param writer The current ResponseWriter
     */
    protected void renderPageViews(FacesContext context,
            ContentPageTitle pagetitle,
            Theme theme,
            ResponseWriter writer)
            throws IOException {
        
        UIComponent pageViews = pagetitle.getFacet("pageViews");
        
        if (pageViews == null) {
            //throw new NullPointerException("pageViews facet null");
            return;
        }
        
        writer.startElement("div", pagetitle);
        writer.writeAttribute("class", theme.getStyleClass(
                ThemeStyles.TITLE_VIEW_DIV), null);
        
        RenderingUtilities.renderComponent(pageViews, context);
        
        writer.endElement("div");
    }
    
    /**
     * Convenience method to output the start of a layout table.
     *
     * @param writer The current ResponseWriter
     * @param pagetitle The Pagetitle component
     * @param firstRowValign Value to use for the first table row's valign
     * @param firstTdAlign Value to use for the first table div's align
     * @param firstTdValign Value to use for the first table div's valign
     */
    private void startLayoutTable(ResponseWriter writer,
            ContentPageTitle pagetitle, String firstRowValign, boolean noWrapTd,
            String firstTdAlign, String firstTdValign) throws IOException {
        writer.startElement("table", pagetitle);
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("width", "100%", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeAttribute("cellspacing", "0", null); // NOI18N
        
        writer.startElement("tr", pagetitle);
        if (firstRowValign != null) {
            writer.writeAttribute("valign", firstRowValign, null); // NOI18N
        }
        
        writer.startElement("td", pagetitle);
        
        if (noWrapTd) {
            writer.writeAttribute("nowrap", "nowrap", null);
        }
        
        if (firstTdAlign != null) {
            writer.writeAttribute("align", firstTdAlign, null);
        }
        
        if (firstTdValign != null) {
            writer.writeAttribute("valign", firstTdValign, null);
        }
        
    }
    
    /**
     * Convenience method to output the end of a layout table.
     *
     * @param writer The current ResponseWriter
     */
    private void endLayoutTable(ResponseWriter writer) throws IOException {
        writer.endElement("tr");
        writer.endElement("table");
    }
    
    /**
     * Render the page title.
     *
     * @param context The current FacesContext
     * @param pagetitle The Pagetitle component
     * @param writer The current ResponseWriter
     * @param theme The current Theme object
     */
    protected void renderPageTitle(FacesContext context,
            ContentPageTitle pagetitle, ResponseWriter writer, Theme theme)
            throws IOException {
        
        writer.startElement("div", pagetitle);
        String style = theme.getStyleClass(ThemeStyles.TITLE_TEXT_DIV);
        writer.writeAttribute("class", style, null);
        
        if (pagetitle.getFacet("pageTitle") != null) {
            // if the developer spec'd a pageTitle facet, render it
            RenderingUtilities.renderComponent(pagetitle.getFacet("pageTitle"),
                    context);
        } else {
            writer.startElement("h1", pagetitle);
            style = theme.getStyleClass(ThemeStyles.TITLE_TEXT);
            writer.writeAttribute("class", style, null); //NOI18N
            
            String title = pagetitle.getTitle();
            writer.write(title != null ? title : "");
            writer.endElement("h1");
        }
        
        writer.endElement("div");
        writer.endElement("td");
    }
    
    /**
     * Render the page help facet.
     *
     * @param context The current FacesContext
     * @param pagetitle The Pagetitle component
     * @param writer The current ResponseWriter
     */
    protected void renderPageHelp(FacesContext context,
            ContentPageTitle pagetitle,
            Theme theme,
            ResponseWriter writer)
            throws IOException {
        
        startLayoutTable(writer, pagetitle, null, false, null, null);
        
        writer.startElement("div", pagetitle);
        String style = theme.getStyleClass(ThemeStyles.TITLE_HELP_DIV);
        writer.writeAttribute("class", style, null);
        
        UIComponent helpFacet = pagetitle.getFacet("pageHelp");
        
        if (helpFacet != null) {
            RenderingUtilities.renderComponent(helpFacet, context);
        } else {
            writer.startElement("div", pagetitle);
            style = theme.getStyleClass(ThemeStyles.HELP_PAGE_TEXT);
            writer.writeAttribute("class", style, null);
            
            writer.write(pagetitle.getHelpText());
            
            writer.endElement("div");
        }
        
        writer.endElement("div");
        writer.endElement("td");
        
        endLayoutTable(writer);
        
    }
    
    /**
     * Render the pageButtons facet.
     * @param context The current FacesContext
     * @param pagetitle The Pagetitle component
     * @param writer The current ResponseWriter
     * @param divStyle The style class name to use for the div enclosing the
     * button facet
     */
    protected void renderPageButtons(FacesContext context,
            ContentPageTitle pagetitle, ResponseWriter writer, String divStyle,
            UIComponent buttonFacet) throws IOException {
        
        if (buttonFacet == null) {
            // throw new NullPointerException("pageButtons facet null");
            return;
        }
        
        writer.startElement("td", pagetitle);
        writer.writeAttribute("align", "right", null);
        writer.writeAttribute("nowrap",  "nowrap", null);
        writer.writeAttribute("valign", "bottom", null);
        
        writer.startElement("div", pagetitle);
        writer.writeAttribute("class", divStyle, null);
        
        RenderingUtilities.renderComponent(buttonFacet, context);
        
        writer.endElement("div");
        writer.endElement("td");
    }
    
    /**
     * <p>Render the Pagetitle component end.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException {
        ContentPageTitle pagetitle = (ContentPageTitle) component;
        Theme theme = ThemeUtilities.getTheme(context);
        ResponseWriter writer = context.getResponseWriter();
        // check for bottom page buttons facet
        UIComponent facet = pagetitle.getFacet("pageButtonsBottom");
        
        // test if the bottom page buttons need to be rendered
        if (facet != null && facet.isRendered()) {
            if (pagetitle.isSeparator()) {
                renderPageSeparator(context, pagetitle, theme, writer);
            }
            
            writer.startElement("table", pagetitle);
            writer.writeAttribute("border", "0", null); // NOI18N
            writer.writeAttribute("width", "100%", null); // NOI18N
            writer.writeAttribute("cellpadding", "0", null); // NOI18N
            writer.writeAttribute("cellspacing", "0", null); // NOI18N
            
            writer.startElement("tr", pagetitle);
            
            String style =
                    theme.getStyleClass(ThemeStyles.TITLE_BUTTON_BOTTOM_DIV);
            
            renderPageButtons(context, pagetitle, writer, style, facet);
            
            endLayoutTable(writer);
            
        }
        writer.endElement("div");
    }
    
    /**
     * Render the page separator.
     *
     * @param context The current FacesContext
     * @param pagetitle The Pagetitle component
     * @param writer The current ResponseWriter
     */
    protected void renderPageSeparator(FacesContext context,
            ContentPageTitle pagetitle,
            Theme theme,
            ResponseWriter writer)
            throws IOException {
        UIComponent separatorFacet = pagetitle.getBottomPageSeparator();
        
        if (separatorFacet != null) {
            RenderingUtilities.renderComponent(separatorFacet, context);
        }
        
    }
    
    /**
     * Helper function to render a transparent spacer image.
     *
     * TO DO - move to AbstractRenderer
     *
     * @param writer The current ResponseWriter
     * @param pagetitle The Pagetitle component
     * @param src The value to use for image src attribute
     * @param height The value to use for the image height attribute
     * @param width The value to use for the image width attribute
     */
    private void writeDotImage(ResponseWriter writer,
            ContentPageTitle pagetitle, String src,  int height, int width)
            throws IOException {
        writer.startElement("img", pagetitle);
        writer.writeAttribute("src", src, null); // NOI18N
        writer.writeAttribute("alt", "", null); // NOI18N
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("height", new Integer(height), null); // NOI18N
        writer.writeAttribute("width", new Integer(width), null); // NOI18N
        writer.endElement("img");
    }
    
}
