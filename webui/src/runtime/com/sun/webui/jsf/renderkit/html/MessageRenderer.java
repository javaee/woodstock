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
import com.sun.webui.jsf.util.MessageUtil;
import java.beans.Beans;
import java.io.IOException;
import java.lang.NullPointerException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.jsf.component.Message;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.FacesMessageUtils;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>This class is responsible for rendering the Message component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Message"))
public class MessageRenderer extends AbstractRenderer {

    protected String MESSAGE_START_ELEMENT = "div"; //NOI18N

    /**
     * Renders the Message component.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * end should be rendered
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
        ResponseWriter writer) throws IOException {
        // End the appropriate element
	Message message = (Message) component;
	
	String forComponentId = message.getFor();
	FacesMessage msg = null;
	Iterator msgIt = null;
	
	if (Beans.isDesignTime()) {
            // At design-time, prepare a default message
      
            if (forComponentId == null || forComponentId.length() == 0) {
		String summary = MessageUtil.getMessage(context, 
			"com.sun.webui.jsf.renderkit.html.Bundle", //NOI18N
			"Message.default.summary"); //NOI18N
                renderMessage(context, component, writer, 
			new FacesMessage(summary));
            } else {
                String summary = MessageUtil.getMessage(context,
			"com.sun.webui.jsf.renderkit.html.Bundle", //NOI18N
			"Message.for.summary", //NOI18N
			new String[] {forComponentId});
                String detail = MessageUtil.getMessage(context,
                    "com.sun.webui.jsf.renderkit.html.Bundle", //NOI18n
			"Message.for.detail", //NOI18N
			new String[] {forComponentId});
                
                renderMessage(context, component, writer, 
                        new FacesMessage(summary, detail));
            }   
        } else if (forComponentId != null) {
	    // Get the run-time messages for this component, if any
	    msgIt = FacesMessageUtils.getMessageIterator(context, 
			forComponentId, message);
	    if (msgIt.hasNext()) {
		msg = (FacesMessage) msgIt.next();
		renderMessage(context, component, writer, msg);
	    }
	}
    }
            
    /**
     * Renders the Message text
     *     
     * @param context The current FacesContext
     * @param component The Message object to use
     * @param writer The current ResponseWriter
     * @param fMsg The FacesMessage message
     *
     * @exception IOException if an input/output error occurs
     */
    public void renderMessage(FacesContext context, 
            UIComponent component, ResponseWriter writer,
	    FacesMessage fMsg) throws IOException {

	Message message = (Message) component;
	String summary = null;
	String detail = null;

	// Check if there is both summary and detail messages to show
	if (message.isShowSummary()) {
	    summary = fMsg.getSummary();
	    if ((summary != null) && (summary.length() <= 0)) {
		summary = null;
	    }
	}
	if (message.isShowDetail()) {
	    detail = fMsg.getDetail();
	    if ((detail != null) && (detail.length() <= 0)) {
		detail = null;
	    }
	}

	// No message text, don't write anything
	//
	if (summary == null && detail == null) 
	    return;

        // Get the theme
        Theme theme = ThemeUtilities.getTheme(context);
	boolean haveSummaryAndDetail = summary != null && detail != null;

	// Creator and the components not have the same behavior
	// A div is always rendered, thereby giving the message 
	// block behavior, vs inline behavior, and always renders
	// the div.

	// Originally
	// Creator always renders an opening div, followed by a span
	// that encloses the message. If both summary and detail are to
	// be written, then two spans will be written, one for each message.
	// <div> <span>...</span><span>...</span></div>
	//
	// Braveheart writes an enclosing span if both detail and summary
	// are to be written. If only one or the other is to be written
	// then only one span is written for the message. But this span
	// must have the component id and the hidden styleClass.
	//
	// Since creator always renders a div, that div contains the id
	// and the hidden style.

	// Creator always writes a "div" and always returns true.
	// Braveheart only writes a "span" when both summary and
	// detail are to be written and then returns true, otherwise
	// return false
	//

	boolean wroteStartElement = renderMessageStart(context, message,
	    null, haveSummaryAndDetail, writer);

	String severityStyleClass = getSeverityStyleClass(fMsg, theme);

	// For Braveheart if a start element wasn't written, (only
	// summary or detail is to be written) write one now. This element
	// will contain the id and the hidden style. It will also enclose
	// the message.
	//
	if (summary != null) {
	    String styleClass = 
		severityStyleClass != null ? severityStyleClass :
		    theme.getStyleClass(ThemeStyles.MESSAGE_FIELD_SUMMARY_TEXT);
	    renderMessageText(context, message, writer, 
			  summary, styleClass, wroteStartElement);
	}

	if (detail != null) {
	    String styleClass =
		severityStyleClass != null ? severityStyleClass :
		    theme.getStyleClass(ThemeStyles.MESSAGE_FIELD_TEXT);
	    if (summary != null) 
		detail = " " + detail; //NOI18N
	    renderMessageText(context, message, writer, 
			  detail, styleClass, wroteStartElement);
	}

	// If the start element was written, close it 
	// else close the message start element written in this
	// method.
	//
	if (wroteStartElement) {
	    renderMessageEnd(context, message, wroteStartElement, writer);
	}
    }

    /**
     * Render the message text. If wroteStartElement is false
     * render an enclosing span element and associate the message's id
     * with that element and render the hidden style class if appropriate.
     * If wroteStartElement is true, render an enclosing span element
     * with just the class attribute with value textStyle.
     *
     * @param context The current FacesContext
     * @param message The Message object being rendered
     * @param writer The current ResponseWriter
     * @param msgText The message text
     * @param textStyle The text style
     * @param wroteOpeningSpanId Flag to indicate whether opening 
     *        element with id was rendered
     * 
     * @exception IOException if an input/output error occurs
     */
    private void renderMessageText(FacesContext context, Message message, 
		ResponseWriter writer, String msgText, String textStyle,
		boolean wroteStartElement) throws IOException {

	// Render an opening span with the id attribute and hidden style
	// class.
	//
	if (!wroteStartElement) {
	    renderMessageIdElement(context, message, textStyle,
		"span", writer); //NOI18N
	} else {
	    writer.startElement("span", message); //NOI18N		
	    writer.writeAttribute("class", textStyle, "class"); //NOI18N
	}
	writer.writeText(msgText, null);

	// Always write the end span element, even for the span rendered by
	// renderMessageIdElement
	//
	writer.endElement("span"); // NOI18N
    }

    /**
     * Method to encapsulate different rendering implementations for 
     * the element associated with the Message id that encloses the message
     * or messages. Returns true if a start element is written, false
     * otherwise.
     *
     * This implementation only writes an overall enclosing element, a span,
     * if haveSummaryAndDetail is true.
     *
     * @param context The current FacesContext
     * @param message The Message object being rendered
     * @param writer The current ResponseWriter
     * @param styleClass The message style class
     * @param haveSummaryAndDetail True if rendering both the summary and
     * detail messages.
     *
     * @exception IOException if an input/output error occurs
     */
    protected boolean renderMessageStart(FacesContext context, 
            Message message, String styleClass, boolean haveSummaryAndDetail, 
	    ResponseWriter writer) throws IOException {

	renderMessageIdElement(context, message, styleClass, 
		MESSAGE_START_ELEMENT, writer);

	return true;
    }

    /**
     * The complementary method to renderMessageStart.
     *
     * @param context The current FacesContext
     * @param message The Message object being rendered.
     * @param wroteStartElement True if renderMessageStart returned true.
     * @param writer The current ResponseWriter
     */
    protected void renderMessageEnd(FacesContext context, Message message,
		boolean wroteStartElement,
		ResponseWriter writer) throws IOException {
	writer.endElement(MESSAGE_START_ELEMENT);
    }

    /**
     * Render the element that is associated with the
     * component id.
     *
     * @param context The current FacesContext
     * @param message The Message object being rendered.
     * @param styleClass The message style class
     * @param startElement The HTML element to render
     * @param writer The current ResponseWriter
     */
    protected void renderMessageIdElement(FacesContext context,
		Message message, String styleClass,
		String startElement,
		ResponseWriter writer) throws IOException {

        String id = message.getClientId(context); 
	
	writer.startElement(startElement, message);
	writer.writeAttribute("id", id, "id"); //NOI18N
	    
	String style = message.getStyle();
	if (style != null && style.length() > 0) {
	    writer.writeAttribute("style", style, "style"); //NOI18N
	}
	RenderingUtilities.renderStyleClass(context, writer,
					    message, styleClass);
    }

    /**
     * Return a style class based on the FacesMesssage severity.
     * If there is no style for a given severity return null.
     *
     * @param facesMessage The FacesMessage
     * @param theme The current theme
     */
    protected String getSeverityStyleClass(FacesMessage facesMessage,
		Theme theme) {

	// Obtain a style based on message severity
	//
	String severityStyleClass = null;
	Severity severity = facesMessage.getSeverity();
	if (severity == FacesMessage.SEVERITY_INFO) {
	    severityStyleClass =
		    theme.getStyleClass(ThemeStyles.MESSAGE_INFO);
	} else if (severity == FacesMessage.SEVERITY_WARN) {
	    severityStyleClass =
		    theme.getStyleClass(ThemeStyles.MESSAGE_WARN);
	} else if (severity == FacesMessage.SEVERITY_ERROR) {
	    severityStyleClass =
		    theme.getStyleClass(ThemeStyles.MESSAGE_ERROR);
	} else if (severity == FacesMessage.SEVERITY_FATAL) {
	    severityStyleClass =
		    theme.getStyleClass(ThemeStyles.MESSAGE_FATAL);
	}
	return severityStyleClass == null || severityStyleClass.length() == 0 ?
		null: severityStyleClass;
    }

}
