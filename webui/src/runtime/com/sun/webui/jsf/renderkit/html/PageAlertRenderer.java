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
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIParameter;

import com.sun.webui.jsf.component.PageAlert;
import com.sun.webui.jsf.component.PageSeparator;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;


/**
 * <p>Renders a full page alert.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.PageAlert"))
public class PageAlertRenderer extends AbstractRenderer {

    /**
     * Content Page Title Button facet
     */
    public static final String PAGETITLE_BUTTON_FACET = "pageButtons"; //NOI18N

    /** Creates a new instance of MastheadRenderer */
    public PageAlertRenderer() {
    }
    
    /**
     * Render the full page alert.
     * 
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        if (context == null || component == null || writer == null) {
            throw new NullPointerException();
        }

	PageAlert pagealert = (PageAlert) component;

        writer.startElement("div", component); //NO18N
        
        addCoreAttributes(context, component, writer, null);
        
	renderAlert(context, component, writer);
	renderSeparator(context, component, writer);
	renderButtons(context, component, writer);
        
        writer.endElement("div"); //NOI18N
    }    

    /**
     * Renders alert summary message
     * 
     * @param context The current FacesContext
     * @param component The Alert object to use
     * @param theme The Theme to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlertSummary(FacesContext context, UIComponent component, 
	    Theme theme, ResponseWriter writer) throws IOException {

	PageAlert pagealert = (PageAlert) component;
	String summary = pagealert.getSummary();
        // If a summary message is not specified, nothing to render.
        if (summary == null || summary.trim().length() == 0)
            return;

        writer.startElement("div", pagealert); //NOI18N
        // Set the containing div style based on the theme
        String style = theme.getStyleClass(ThemeStyles.ALERT_HEADER_DIV);
        RenderingUtilities.renderStyleClass(context, writer, component, style);
	writer.startElement("span", pagealert); //NOI18N
	style = theme.getStyleClass(ThemeStyles.ALERT_HEADER_TXT);
	writer.writeAttribute("class", style, null); //NOI18N
	renderFormattedMessage(writer, component, context, summary);
        // Close the span, div
	writer.endElement("span"); //NOI18N	
	writer.endElement("div"); //NOI18N

        writer.writeText("\n", null); //NOI18N     
    }

    /**
     * Renders detsil summary message
     * 
     * @param context The current FacesContext
     * @param component The Alert object to use
     * @param theme The Theme to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlertDetail(FacesContext context, 
	    UIComponent component, Theme theme, 
	    ResponseWriter writer) throws IOException {

	PageAlert pagealert = (PageAlert) component;
	String detail = pagealert.getDetail();
        // If a detail message is not specified, nothing to render.
        if (detail == null || detail.trim().length() == 0)
            return;

        writer.startElement("div", pagealert); //NOI18N
        // Set the containing div style based on the theme
        String style = theme.getStyleClass(ThemeStyles.ALERT_MESSAGE_DIV);
	writer.writeAttribute("class", style, null); //NOI18N
	writer.startElement("span", pagealert); //NOI18N
	style = theme.getStyleClass(ThemeStyles.ALERT_MESSAGE_TEXT);
	writer.writeAttribute("class", style, null); //NOI18N	
        renderFormattedMessage(writer, component, context, detail);
        // Close the span, div
	writer.endElement("span"); //NOI18N	
	writer.endElement("div"); //NOI18N

        writer.writeText("\n", null); //NOI18N     
    }

    /**
     * Renders PageAlert Icon
     * 
     * @param context The current FacesContext
     * @param component The Alert object to use
     * @param theme The Theme to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlertIcon(FacesContext context, 
	    UIComponent component, Theme theme, 
	    ResponseWriter writer) throws IOException {

	PageAlert pagealert = (PageAlert) component;

        writer.startElement("table", component); //NOI18N
        writer.writeAttribute("title", "", null); //NOI18N
        writer.writeAttribute("border", "0", null); //NOI18N
        writer.writeAttribute("cellpadding", "0", null); //NOI18N
        writer.writeAttribute("cellspacing", "0", null); //NOI18N
        writer.writeAttribute("width", "100%", null); //NOI18N
        writer.startElement("tr", component); //NOI18N
        writer.startElement("td", component); //NOI18N
        writer.startElement("div", component); //NOI18N
        
        UIComponent titleFacet = pagealert.getFacet(pagealert.PAGEALERT_TITLE_FACET);            
        if (titleFacet == null) {                          
            String style;
            style = theme.getStyleClass(ThemeStyles.TITLE_TEXT_DIV);
            writer.writeAttribute("class", style, null); //NOI18N           
            
            writer.startElement("span", component); //NOI18N
            style = theme.getStyleClass(ThemeStyles.TITLE_TEXT);
            writer.writeAttribute("class", style, null); //NOI18N                 
            
            // Get the image specified via the type attribute or the image facet.
            UIComponent image = pagealert.getPageAlertImage();                      
            RenderingUtilities.renderComponent(image, context); 
            
            writer.write(pagealert.getSafeTitle());        
            writer.endElement("span");                
        } else {                                                      
            // Render the title facet
            RenderingUtilities.renderComponent(titleFacet, context);            
        }
        
        writer.endElement("div"); //NOI18N
        writer.endElement("td"); //NOI18N
        writer.endElement("tr"); //NOI18N
        writer.endElement("table"); //NOI18N
 
    }    
    
    /**
     * Renders alert - summary message, detail message and any input 
     * components contained in the facet.
     * 
     * @param context The current FacesContext
     * @param component The Alert object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderAlert(FacesContext context, 
            UIComponent component, ResponseWriter writer) throws IOException {

	PageAlert pagealert = (PageAlert) component;
        // Get the theme
        Theme theme = ThemeUtilities.getTheme(context);
	// Render the Alert summary and detail message
        renderAlertIcon(context, component, theme, writer);
	renderAlertSummary(context, component, theme, writer);
	renderAlertDetail(context, component, theme, writer);
	
	// Render any input component specified in a facet.
	UIComponent inputComponent = pagealert.getPageAlertInput();

	if (inputComponent != null) {
	    writer.startElement("div", pagealert); //NOI18N
	    // Set the containing div style based on the theme
	    String style = theme.getStyleClass(ThemeStyles.ALERT_FORM_DIV);
	    RenderingUtilities.renderStyleClass(context, writer, component, style);
	    RenderingUtilities.renderComponent(inputComponent, context);
	    writer.endElement("div"); //NOI18N
	    writer.writeText("\n", null); //NOI18N     
	}
    }

     private void renderSeparator(FacesContext context, 
            UIComponent component, ResponseWriter writer) throws IOException {
            
            PageAlert pageAlert = (PageAlert) component;
            UIComponent separator = pageAlert.getPageAlertSeparator();
            RenderingUtilities.renderComponent(separator, context);
    }

    private void renderButtons(FacesContext context, 
            UIComponent component, ResponseWriter writer) throws IOException {

	PageAlert pagealert = (PageAlert) component;
	UIComponent buttonFacet = pagealert.getPageAlertButtons();

	if (buttonFacet == null) {
	    return;
	}

        // Get the theme
        Theme theme = ThemeUtilities.getTheme(context);

	writer.startElement("table", pagealert);
	writer.writeAttribute("border", "0", null); // NOI18N
	writer.writeAttribute("width", "100%", null); // NOI18N
	writer.writeAttribute("cellpadding", "0", null); // NOI18N
	writer.writeAttribute("cellspacing", "0", null); // NOI18N
	
	writer.startElement("tr", pagealert); // NOI18N
	String style = theme.getStyleClass(ThemeStyles.TITLE_BUTTON_BOTTOM_DIV); 

        writer.startElement("td", pagealert); // NOI18N
        writer.writeAttribute("align", "right", null); // NOI18N
        writer.writeAttribute("nowrap",  "nowrap", null); // NOI18N
        
        writer.startElement("div", pagealert); // NOI18N
        writer.writeAttribute("class", style, null); // NOI18N

        RenderingUtilities.renderComponent(buttonFacet, context);        

        writer.endElement("div"); // NOI18N
        writer.endElement("td"); // NOI18N

	writer.endElement("tr"); // NOI18N
	writer.endElement("table"); // NOI18N
    }
    
     private void renderFormattedMessage(ResponseWriter writer, 
            UIComponent component, FacesContext context, 
            String msg) throws IOException {
        java.util.ArrayList parameterList = new ArrayList();

            // get UIParameter children...
        java.util.Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
               UIComponent kid = (UIComponent) kids.next();

                //PENDING(rogerk) ignore if child is not UIParameter?
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
             PageAlert pa = (PageAlert)component;   
             
             // Check if it the message be HTML escaped (true by default).
                if(!pa.isEscape()) {
                    writer.write(message);
                } else {
                    writer.writeText(message,"message");
                }
                
        }
     }
}
