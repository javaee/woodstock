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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.jsf.component.MessageGroup;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.FacesMessageUtils;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>This class is responsible for rendering the Message component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.MessageGroup"))
public class MessageGroupRenderer extends AbstractRenderer {

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
	MessageGroup msgGrp = (MessageGroup) component;
	Iterator msgIt = null;
	String forComponentId = null;

        if (Beans.isDesignTime() && (msgGrp.isShowDetail() || msgGrp.isShowSummary())) {
            StringBuffer resourceNameBuffer = new StringBuffer();
            resourceNameBuffer.append("MessageGroup."); //NOI18N
            if (msgGrp.isShowGlobalOnly())
                resourceNameBuffer.append("global."); //NOI18N
            else
                resourceNameBuffer.append("default."); //NOI18N
            if (msgGrp.isShowDetail() && msgGrp.isShowSummary())
                resourceNameBuffer.append("both"); //NOI18N
            else if (msgGrp.isShowDetail())
                resourceNameBuffer.append("detail"); //NOI18N
            else if (msgGrp.isShowSummary())
                resourceNameBuffer.append("summary"); //NOI18N
            String summary = MessageUtil.getMessage(context,
                    "com.sun.webui.jsf.renderkit.html.Bundle", //NOI18N
                    resourceNameBuffer.toString());
            FacesMessage defaultMessage = new FacesMessage();
            defaultMessage.setSummary(summary);
            msgIt = Collections.singletonList(defaultMessage).iterator();
        } else {
            if (msgGrp.isShowGlobalOnly()) {
                forComponentId = ""; // for only global messages
            }
            msgIt = FacesMessageUtils.getMessageIterator(context,
			 forComponentId, msgGrp);
        }
        if (msgIt.hasNext()) {
            renderMessageGroup(context, msgGrp, writer, msgIt);
        }
    }

    /**
     * Renders the Message text
     *
     * @param context The current FacesContext
     * @param component The VersionPage object to use
     * @param writer The current ResponseWriter
     * @param msgIt The message
     *
     * @exception IOException if an input/output error occurs
     */
    public void renderMessageGroup(FacesContext context,
            UIComponent component, ResponseWriter writer,
	    Iterator msgIt) throws IOException {

	MessageGroup msgGrp = (MessageGroup) component;

        // Get the theme
        Theme theme = ThemeUtilities.getTheme(context);

	// Render the style/styleClass attributes in a surrounding div
	renderMessageGroupIdElement(context, msgGrp, writer);

	// Render the opening table
	renderOpeningTable(msgGrp, writer, theme);

	FacesMessage fMsg = null;
	boolean showSummary = msgGrp.isShowSummary();
	boolean showDetail = msgGrp.isShowDetail();
	String summaryStyle = theme.getStyleClass(
			ThemeStyles.MESSAGE_GROUP_SUMMARY_TEXT);
	String detailStyle = theme.getStyleClass(
			ThemeStyles.MESSAGE_GROUP_TEXT);

	String summary = null;
	String detail = null;

	// Optimization to reduce compiler construction of StringBuffer
	// for constant text within the loop.
	//
	StringBuffer detailBuf = new StringBuffer(64).append(" ");

	while (msgIt.hasNext()) {

	    fMsg = (FacesMessage) msgIt.next();
	    // Check if we should show detail or summary
	    if (showSummary) {
		summary = fMsg.getSummary();
		if ((summary != null) && (summary.length() <= 0)) {
		    summary = null;
		}
	    }
	    if (showDetail) {
		detail = fMsg.getDetail();
		if ((detail != null) && (detail.length() <= 0)) {
		    detail = null;
		}
	    }

	    if (summary == null && detail == null)
		continue;

	    // Null these variables when a severity style is found.
	    // Severity styles override the default styles.
	    //
	    String summaryStyleTmp = summaryStyle;
	    String detailStyleTmp = detailStyle;

	    // Why is there a div with a list with only a single
	    // bullet for each message ? Why not a bullet for each
	    // messages and one list and one div ?
	    //
	    writer.startElement("div", msgGrp); //NOI18N
	    writer.writeAttribute("class", //NOI18N
		      theme.getStyleClass(ThemeStyles.MESSAGE_GROUP_DIV),
				  null); //NOI18N
	    writer.startElement("ul", msgGrp); //NOI18N
	    writer.startElement("li", msgGrp); //NOI18N

            // render theme based style based on severity.
	    String severityStyleClass = getSeverityStyleClass(fMsg, theme);

	    // This renders the selector on the "li" element.
	    // The default styles appear on the text's "span" element.
	    // Severity styles override default styles
	    //
            if (severityStyleClass != null) {
                writer.writeAttribute("class", severityStyleClass, //NOI18N
			"styleClass"); //NOI18N
		summaryStyleTmp = null;
		detailStyleTmp = null;
            }

	    if (summary != null) {
		renderMessageText(msgGrp, writer, summary, summaryStyleTmp);
            }

	    if (detail != null) {
                // renderMessageText(msgGrp, writer, detail, detailStyle);
                // if severity based style  is set, don't use theme based
                // default styles.
		// 
		// Places a space between the summary message and
		// the detail message. This should be part of the theme.
		// A style for the detail message when preceded by the
		// summary message.
		//
                if (summary != null) {
		    detail = detailBuf.append(detail).toString();
                }
		renderMessageText(msgGrp, writer, detail, detailStyleTmp);

		// Rewind the buffer so only the " " exists.
		//
		detailBuf.setLength(1);
	    }

	    writer.endElement("li"); //NOI18N
	    writer.endElement("ul"); //NOI18N
	    writer.endElement("div"); //NOI18N
	}

	// Close tags
	renderClosingTable(writer);
	// Close the surrounding div
	writer.endElement("div"); // NOI18N
    }

    /**
     * Helper method to render opening tags for the layout table.
     *
     * @param msgGrp The MessageGroup object to use
     * @param writer The current ResponseWriter
     * @param theme The theme to use
     *
     * @exception IOException if an input/output error occurs
     */
    public void renderOpeningTable(MessageGroup msgGrp, ResponseWriter writer,
				    Theme theme) throws IOException {
	// Render the layout table
	writer.startElement("table", msgGrp); //NOI18N
        writer.writeAttribute("class", //NOI18N
	    theme.getStyleClass(ThemeStyles.MESSAGE_GROUP_TABLE), null);
        writer.writeAttribute("border", "0", null); //NOI18N
        writer.writeAttribute("cellpadding", "0", null); //NTOI18N
        writer.writeAttribute("cellspacing", "0", null); //NOI18N
	if (msgGrp.getToolTip() != null ) {
	    writer.writeAttribute("title", msgGrp.getToolTip(), null); //NOI18N
	} else {
	    // Required for A11Y
	    //
	    writer.writeAttribute("title", "", null); //NOI18N
	}
        writer.writeText("\n", null); //NOI18N

	// Add the heading
	writer.startElement("tr", msgGrp); //NOI18N
	writer.startElement("th", msgGrp); //NOI18N
        writer.writeAttribute("class",
	    theme.getStyleClass(ThemeStyles.MESSAGE_GROUP_TABLE_TITLE), null);
        String title = msgGrp.getTitle();
        if (title != null) {
            writer.writeText(title, null);
        } else {
            writer.writeText(theme.getMessage("messageGroup.heading"), null);
        }
	writer.endElement("th");
	writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null); //NOI18N

	// We know there is at least one message
	writer.startElement("tr", msgGrp); //NOI18N
	writer.startElement("td", msgGrp); //NOI18N
    }

    /**
     * Helper method to render closing tags for the layout table.
     *
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    public void renderClosingTable(ResponseWriter writer) throws IOException {
	writer.endElement("td"); //NOI18N
	writer.endElement("tr"); //NOI18N
	writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }

    /**
     * Helper method to write message text.
     *
     * @param msgGrp The MessageGroup object to use
     * @param writer The current ResponseWriter
     * @param msgText The message text
     * @param textStyle The text style
     *
     * @exception IOException if an input/output error occurs
     */
    public void renderMessageText(MessageGroup msgGrp, ResponseWriter writer,
	    String msgText, String textStyle) throws IOException {

	writer.startElement("span", msgGrp); //NOI18N
	if (textStyle != null && textStyle.length() > 0) {
	    writer.writeAttribute("class", textStyle, "class"); //NOI18N
	}
	writer.writeText(msgText, null);
	writer.endElement("span"); // NOI18N
    }

    /**
     * Render the enclosing element for the MesssageGroup messages that
     * is associated with the component's id.
     *
     * @param context The current FacesContext
     * @param msgGrp The MessageGroup object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderMessageGroupIdElement(FacesContext context,
            MessageGroup msgGrp, ResponseWriter writer) throws IOException {

	String userStyle = msgGrp.getStyle();
	String userStyleClass = msgGrp.getStyleClass();
        String id = msgGrp.getClientId(context);

	writer.startElement("div", msgGrp); //NO18N
	writer.writeAttribute("id", id, "id"); //NOI18N

	if (userStyle != null && userStyle.length() > 0) {
	    writer.writeAttribute("style", userStyle, "style"); //NOI18N
	}
	RenderingUtilities.renderStyleClass(context, writer, msgGrp, null);
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
		    theme.getStyleClass(ThemeStyles.MESSAGE_GROUP_INFO);
	} else if (severity == FacesMessage.SEVERITY_WARN) {
	    severityStyleClass =
		    theme.getStyleClass(ThemeStyles.MESSAGE_GROUP_WARN);
	} else if (severity == FacesMessage.SEVERITY_ERROR) {
	    severityStyleClass =
		    theme.getStyleClass(ThemeStyles.MESSAGE_GROUP_ERROR);
	} else if (severity == FacesMessage.SEVERITY_FATAL) {
	    severityStyleClass =
		    theme.getStyleClass(ThemeStyles.MESSAGE_GROUP_FATAL);
	}
	return severityStyleClass == null || severityStyleClass.length() == 0 ?
		null: severityStyleClass;
    }

}
