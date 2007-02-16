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
import java.util.List;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIParameter;

import javax.servlet.http.Cookie;        
import javax.servlet.http.HttpServletResponse;

import com.sun.webui.jsf.component.Alert;
import com.sun.webui.jsf.component.IconHyperlink;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for an {@link Alert} component.</p>
 * 
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Alert"))
public class AlertRenderer extends AbstractRenderer {
    
    /** Creates a new instance of AlertRenderer */
    public AlertRenderer() {
        // default constructor
    }    

    public boolean getRendersChildren() {
        return true;
    }
    
    public  void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {
         //purposefully don't want to do anything here!
    }
         
    /** 
     * Renders the outer div which contains the alert.
     * 
     * @param context The current FacesContext
     * @param alert The Alert object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     * @deprecated replaced by {@link #renderOuterDiv(context,Alert,String,ResponseWriter)}
     */
    protected void renderOuterDiv(FacesContext context, 
            Alert alert, ResponseWriter writer) throws IOException {

	Theme theme = ThemeUtilities.getTheme(context);
	String defaultStyleClass = theme.getStyleClass(ThemeStyles.ALERT_DIV);
	renderOuterDiv(context, alert, defaultStyleClass, writer);
    }

    /** 
     * Renders the outer div which contains the alert.
     * 
     * @param context The current FacesContext
     * @param alert The Alert object to use
     * @param defaultStyleClass The styleClass to use if not set on alert
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderOuterDiv(FacesContext context, 
            Alert alert, String defaultStyleClass,
	    ResponseWriter writer) throws IOException {

        String style = alert.getStyle();
	String id = alert.getClientId(context);

        writer.startElement("div", alert); //NOI18N

	// Write a id only if a style/class was specified?
	if (id != null) {
	    writer.writeAttribute("id", id, null);  //NOI18N
	}

        if (style != null) {
            writer.writeAttribute("style", style, null);  //NOI18N
        }

	// Even though renderStyleClass obtains the component's
	// styleClass attribute, look for it here too. If it exists
	// do not pass the defaultStyleClass.
	//
	String styleClass = alert.getStyleClass();
	if (styleClass == null || styleClass.length() == 0) { //NOI18N
	    styleClass = defaultStyleClass;
	} else {
	    // Don't pass it or else it will get added twice
	    styleClass = null;
	}
	RenderingUtilities.renderStyleClass(context, writer, (UIComponent)
                                            alert, styleClass);
    }

    /**
     * Renders the attributes for the outer table containing the inline alert.
     * TODO: Use div's instead of tables for layout as soon as I can find a 
     * solution that works for IE.
     * 
     * @param alert The Alert object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderOpeningTable(Alert alert, ResponseWriter writer)
		throws IOException {

        writer.startElement("table", alert); //NOI18N
        writer.writeAttribute("border", "0", null); //NOI18N
        writer.writeAttribute("cellspacing", "0", null); //NOI18N
        writer.writeAttribute("cellpadding", "0", null); //NTOI18N
        writer.writeAttribute("title", "", null); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    /**
     * Renders the outer table top row containing three spacer columns.
     * 
     * @param alert The Alert object to use
     * @param spacerPath The path to the spacer image
     * @param styles The array of styles
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderTopRow(Alert alert, String spacerPath,
	    String[] styles, ResponseWriter writer) throws IOException {

	writer.startElement("tr", alert); //NOI18N
        writer.writeText("\n", null); //NOI18N
	renderSpacerCell(alert, styles[0], spacerPath, writer);
	renderSpacerCell(alert, styles[1], spacerPath, writer);
	renderSpacerCell(alert, styles[2], spacerPath, writer);
	writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    /**
     * Renders the outer table middle row containing two spacer columns and
     * a column that holds the alert.
     * 
     * @param alert The Alert object to use
     * @param theme The Theme to use
     * @param spacerPath The path to the spacer image
     * @param styles The array of styles
     * @param context The current FacesContext
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
     protected void renderMiddleRow(Alert alert, Theme theme,
	    String spacerPath, String[] styles,
	    FacesContext context,
            ResponseWriter writer) throws IOException {

	writer.startElement("tr", alert); //NOI18N
        writer.writeAttribute("class", styles[3], null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
	
	// render a spacer in the left column
	writer.startElement("td", alert); //NOI18N
        writer.writeAttribute("class", styles[4], null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
	renderSpacerImage(alert, spacerPath, writer);
	writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N

	// render middle column
	renderMiddleCell(context, alert, theme, writer, styles); 
		
	// render a spacer in the right column
	writer.startElement("td", alert); //NOI18N
        writer.writeAttribute("class", styles[9], null);  //NOI18N
        writer.writeText("\n", null); //NOI18N
	renderSpacerImage(alert, spacerPath, writer);
	writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N

	// close the middle row 
	writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

   /**
     * Renders the outer table bottom row containing three spacer columns.
     * 
     * @param alert The Alert object to use
     * @param theme The Theme to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */ 
    protected void renderBottomRow(Alert alert, Theme theme,
		String spacerPath, String[] styles,
		ResponseWriter writer) throws IOException {

	writer.startElement("tr", alert); //NOI18N
        writer.writeText("\n", null); //NOI18N
	renderSpacerCell(alert, styles[10], spacerPath, writer);
	renderSpacerCell(alert, styles[11], spacerPath, writer);
	renderSpacerCell(alert, styles[12], spacerPath, writer);
	writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    } 
    
    /**
     * Renders a spacer coulmn.
     * 
     * @param alert The Alert object to use
     * @param styles The array of styles
     * @param spacerPath The path to the spacer image
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */ 
    protected void renderSpacerCell(Alert alert, String styleClass, 
		String spacerPath, ResponseWriter writer)
	        throws IOException {

	writer.startElement("td", alert); //NOI18N
        writer.writeText("\n", null); //NOI18N
	writer.startElement("div", alert);
        writer.writeAttribute("class", styleClass, null);  //NOI18N
	renderSpacerImage(alert, spacerPath, writer);
	writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
	writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    /**
     * Renders the middle column  containing the alert icon, summary,
     * detail message and the optional link.
     * 
     * @param context The current FacesContext
     * @param alert The Alert object to use
     * @param theme The Theme to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderMiddleCell(FacesContext context, 
            Alert alert, Theme theme, 
            ResponseWriter writer, String[] styles)
	    throws IOException {

	writer.startElement("td", alert); //NOI18N
        writer.writeText("\n", null); //NOI18N

	// open the outer div containing the summary and detial areas
        writer.startElement("div", alert); //NOI18N
	writer.writeAttribute("class", styles[5], null); //NOI18N

	// open the alert header div
        writer.startElement("div", alert); //NOI18N
	writer.writeAttribute("class", styles[6], null); //NOI18N

	// Render the alert icon
	renderAlertIcon(context, alert, theme, writer);
	// Render the summary text
	renderAlertSummaryText(alert, styles, writer, context);

	// close the alert header div
	writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N

	// Render the detailed text and the optional link
	renderAlertDetailArea(context, alert, theme, styles, writer);

	// Close the outer div
	writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N

	// Close the cell
	writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    /**
     * Renders the icon associated with an inline alert message.
     * 
     * @param context The current FacesContext
     * @param alert The Alert object to use
     * @param theme The theme to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlertIcon(FacesContext context, 
            Alert alert, Theme theme,
            ResponseWriter writer) throws IOException {
 	UIComponent alertIcon = alert.getAlertIcon();
	RenderingUtilities.renderComponent(alertIcon, context);
    }

    /**
     * Renders the summary message of the inline alert.
     * 
     * @param alert The Alert object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlertSummaryText(Alert alert, String[] styles,
	    ResponseWriter writer) throws IOException {
        // Render the summary text
        String summary = alert.getSummary();

	// Check if it should be HTML escaped (true by default).
	writer.startElement("span", alert); 
	writer.writeAttribute("class", styles[7], null); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.writeText(summary, null);
	writer.endElement("span"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

    protected void renderAlertSummaryText(Alert alert, String[] styles,
	    ResponseWriter writer, FacesContext context) throws IOException {
        // Render the summary text
        String summary = alert.getSummary();
       
        if (summary != null) {
	    writer.startElement("span", alert); 
	    writer.writeAttribute("class", styles[7], null); //NOI18N
            writer.writeText("\n", null); //NOI18N	
            renderFormattedMessage(writer, alert, context, summary);       
	    writer.endElement("span"); //NOI18N
            writer.writeText("\n", null); //NOI18N
        }
    }
    
    /**
     * Renders the optional detail message of the inline alert.
     * Also renders the optional link.
     * 
     * @param alert The Alert object to use 
     * @param theme The theme to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlertDetailArea(FacesContext context, 
            Alert alert, Theme theme, String[] styles,
	    ResponseWriter writer) throws IOException {
	// Get the detail text
	String detail = alert.getDetail();        
        
        // Get the children, if any.
        List children = alert.getChildren();                
	if ((detail == null || detail.trim().length() == 0)
                && children.size() <= 0)
            return;

        // Set the style
        writer.startElement("div", alert); //NOI18N
	writer.writeAttribute("class", styles[8], null); //NOI18N
        
	// Check if it should be HTML escaped (true by default).
        if (detail != null) {
	    writer.startElement("span", alert); 
	    writer.writeAttribute("class", styles[7], null); //NOI18N
            writer.writeText("\n", null); //NOI18N	
            renderFormattedMessage(writer, alert, context, detail);
	    writer.endElement("span"); //NOI18N
            writer.writeText("\n", null); //NOI18N
        }
    
         // render any children
        super.encodeChildren(context, alert);   

	// Render the optional link, if specified
	renderAlertLink(context, alert, theme, writer);

        // Close the div
	writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    /**
     * Renders the optional link at the end of the alert.
     * 
     * @param context The current FacesContext
     * @param alert The Alert object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlertLink(FacesContext context, 
            Alert alert, Theme theme, ResponseWriter writer) 
	    throws IOException {
            UIComponent link = alert.getAlertLink();
            if (link != null) {                 
                RenderingUtilities.renderComponent(link, context);
            }
    }

    /**
     * Renders the optional detail message of the inline alert.
     * 
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderClosingTags(ResponseWriter writer) 
	    throws IOException {
        writer.endElement("table"); //NOI18N
	writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

    /**
     * Renders the inline alert component.
     * 
     * @param context The current FacesContext
     * @param component The Alert object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // Render end of alert      
        Alert alert = (Alert) component;
        String summary = alert.getSummary();
        
        // If a summary message is not specified, nothing to render.
        if (summary == null || summary.trim().length() == 0)
            return;

        // Get the theme
        Theme theme = ThemeUtilities.getTheme(context);
	String spacerPath = theme.getImagePath(ThemeImages.DOT);
	String[] styles = getStyles(theme);

	// Neither Portlet nor design time supports HttpServletResponse.
	//
	// In the portler case, we need to understand if 
	// ExternalContext does support the cookie interfaces.
	// Then only the isDesignTime condition must be suppoted.
	// If the ExternalContext instance is specific to design time
	// and behaves appropriately, then the 
	// isDesignTime test will not be needed either.
	//

	if (!RenderingUtilities.isPortlet(context) &&
		!Beans.isDesignTime()) {
	    // Reset the scroll position by creating a new cookie
	    // and setting its value to null. This would override
	    // the cookie that the javascript code creates. CR 6251724. 
	    HttpServletResponse response =
		(HttpServletResponse)context.getExternalContext().getResponse();
	    String viewId = context.getViewRoot().getViewId();       
	    String urlString = context.getApplication().getViewHandler().
		getActionURL(context, viewId);
	    Cookie cookie =  new Cookie(viewId, "");
	    cookie.setPath(urlString);        
	    response.addCookie(cookie);
	}
                
	// Render the outer div that wraps the alert
	// Get the default style for the alert
	//
	String defaultStyleClass = theme.getStyleClass(ThemeStyles.ALERT_DIV);
	renderOuterDiv(context, alert, defaultStyleClass, writer);

	// Render the opening table
        renderOpeningTable(alert, writer);

	// Render the top row
	renderTopRow(alert, spacerPath, styles, writer);
	
	// Render the middle row
	renderMiddleRow(alert, theme, spacerPath, styles, context, writer);

	// Render the bottom row
	renderBottomRow(alert, theme, spacerPath, styles, writer);
	
	// Render the closing tags
	renderClosingTags(writer);
    }

    
    // Private helper methods.
     
    private void renderFormattedMessage(ResponseWriter writer, 
            UIComponent component, FacesContext context, 
            String msg) throws IOException {
        
        java.util.ArrayList parameterList = new ArrayList();

        // get UIParameter children...
        java.util.Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
             UIComponent kid = (UIComponent) kids.next();
             if (!(kid instanceof UIParameter)) {
                    continue;
                }
             parameterList.add(((UIParameter) kid).getValue());
        }

        // If at least one substitution parameter was specified,
        // use the string as a MessageFormat instance.
        String message = null;
        if (parameterList.size() > 0) {
            message = MessageFormat.format
                (msg, parameterList.toArray
                               (new Object[parameterList.size()]));
        } else {
            message = msg;
        }

        if (message != null) {
            writer.write(message);
        }
    }
    
    // Renders a spacer image.
    private void renderSpacerImage(Alert alert, String spacerPath,
	    ResponseWriter writer) throws IOException {

        writer.startElement("img", alert); //NOI18N
        writer.writeAttribute("src", spacerPath, null); //NOI18N
        writer.writeAttribute("alt", "", null); //NOI18N
        writer.endElement("img");      //NOI18N
    }    

    private String[] getStyles(Theme theme) {
        
        String[] styles = new String[13];
        styles[0] = theme.getStyleClass(ThemeStyles.ALERT_TOP_LEFT_CORNER);
        styles[1] = theme.getStyleClass(ThemeStyles.ALERT_TOP_MIDDLE);     
        styles[2] = theme.getStyleClass(ThemeStyles.ALERT_TOP_RIGHT_CORNER);
        styles[3] = theme.getStyleClass(ThemeStyles.ALERT_MIDDLE_ROW); 
        styles[4] = theme.getStyleClass(ThemeStyles.ALERT_LEFT_MIDDLE); 
        styles[5] = theme.getStyleClass(ThemeStyles.ALERT_MIDDLE); 
        styles[6] = theme.getStyleClass(ThemeStyles.ALERT_HEADER); 
        styles[7] = theme.getStyleClass(ThemeStyles.ALERT_TEXT); 
        styles[8] = theme.getStyleClass(ThemeStyles.ALERT_DETAILS); 
        styles[9] = theme.getStyleClass(ThemeStyles.ALERT_RIGHT_MIDDLE); 
        styles[10] = theme.getStyleClass(ThemeStyles.ALERT_BOTTOM_LEFT_CORNER); 
        styles[11] = theme.getStyleClass(ThemeStyles.ALERT_BOTTOM_MIDDLE); 
        styles[12] = theme.getStyleClass(ThemeStyles.ALERT_BOTTOM_RIGHT_CORNER); 

        return styles;
    }
}
