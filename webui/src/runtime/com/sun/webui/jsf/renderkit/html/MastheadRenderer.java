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
 * MastheadRenderer.java
 *
 * Created on December 16, 2004, 3:40 PM
 */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.util.Map;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.io.IOException;
import java.text.DateFormat;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;

import com.sun.webui.jsf.component.Masthead;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.IconHyperlink;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.component.SkipHyperlink;
import com.sun.webui.jsf.util.ThemeUtilities;

import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;


/**
 * Renders a Masthead component
 *
 * @author seancc@sun.com
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Masthead"))
public class MastheadRenderer extends AbstractRenderer {

    private static final String SKIP_UTILITY = "skipUtility"; //NOI18N

    /** Creates a new instance of MastheadRenderer */
    public MastheadRenderer() {
    }
    
    /**
     * Render the current alarms info for the status area
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     * @param alarms An int[] containing the number of down, critical, major and
     *        minor alarms (in that order)
     * @param theme The current Theme
     */
    protected void renderAlarmsInfo(FacesContext context, Masthead masthead,
            ResponseWriter writer, int[] alarms, Theme theme)
            throws IOException {
        
        writer.startElement(HTMLElements.TD, masthead);
        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass( 
            ThemeStyles.MASTHEAD_ALARM_DIV), null); 
        
        UIComponent alarmsFacet = 
		masthead.getFacet("currentAlarmsInfo"); //NOI18N
        
        if (alarmsFacet != null) {
            RenderingUtilities.renderComponent(alarmsFacet, context);
        } else {
            writer.startElement(HTMLElements.SPAN, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass(
                ThemeStyles.MASTHEAD_LABEL), null); 
            writer.write(theme.getMessage("masthead.currentAlarms"));
            writer.endElement(HTMLElements.SPAN);
            writer.write("&nbsp;&nbsp;"); //NOI18N
            // output the down alarm count
            writeAlarmCount(writer, context, 
		ThemeImages.ALARM_MASTHEAD_DOWN_MEDIUM,
                "Alarm.downImageAltText", masthead, //NOI18N
                ThemeStyles.MASTHEAD_ALARM_DOWN_TEXT, alarms[0], theme);
            
            // output the critical alarm count
            writeAlarmCount(writer, context, 
		ThemeImages.ALARM_MASTHEAD_CRITICAL_MEDIUM,
                "Alarm.criticalImageAltText", masthead, //NOI18N
                ThemeStyles.MASTHEAD_ALARM_CRITICAL_TEXT, alarms[1], theme);
            
            // output the major alarm count
            writeAlarmCount(writer, context, 
		ThemeImages.ALARM_MASTHEAD_MAJOR_MEDIUM,
                "Alarm.majorImageAltText", masthead, //NOI18N
                ThemeStyles.MASTHEAD_ALARM_MAJOR_TEXT, alarms[2], theme);
            
            // output the minor alarm count
            writeAlarmCount(writer, context, 
		ThemeImages.ALARM_MASTHEAD_MINOR_MEDIUM,
                "Alarm.minorImageAltText", masthead, //NOI18N
                ThemeStyles.MASTHEAD_ALARM_MINOR_TEXT, alarms[3], theme);
        }
        
        writer.endElement(HTMLElements.TD);
    }
    
    /**
     * Render the current application info in a table divider. This typically 
     * consists of information about the current user, role (if any) and server.
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     */
    protected void renderApplicationInfo(FacesContext context, 
            Masthead masthead, ResponseWriter writer, Theme theme)
            throws IOException {

        // render the the application details in a single table divider 
        writer.startElement(HTMLElements.TD, masthead);
        writer.writeAttribute(HTMLAttributes.CLASS,
            theme.getStyleClass(ThemeStyles.MASTHEAD_TD_TITLE), null); 
        writer.writeAttribute(HTMLAttributes.WIDTH, "99%", null); //NOI18N
                
	renderUserInfo(context, masthead, writer, theme);
        
        // now render the product name info / image
        renderProductInfo(context, masthead, writer, 
	    theme.getStyleClass(ThemeStyles.MASTHEAD_DIV_TITLE));
        
        // close the app info table divider
        writer.endElement(HTMLElements.TD); //NOI18N        
    }
    
    /**
     * Render the date time information in the masthead status area
     *
     * @param context The current FacesContext
     * @param masthead The current Masthead instance
     * @param writer The ResponseWriter to use
     * @param theme The current Theme
     */
    protected void renderDateTimeInfo(FacesContext context, Masthead masthead, 
            ResponseWriter writer, Theme theme) throws IOException {
        
        writer.startElement(HTMLElements.TD, masthead); //NOI18N  
        writer.writeAttribute(HTMLAttributes.CLASS, //NOI18N
            theme.getStyleClass(ThemeStyles.MASTHEAD_TIME_DIV), null); 
        
        UIComponent timeStampFacet =
		masthead.getFacet("dateTimeInfo"); //NOI18N
        if (timeStampFacet != null) {
            RenderingUtilities.renderComponent(timeStampFacet, context);
        } else {
            String textStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_LABEL);
            
            // display the current time and date
            writer.startElement(HTMLElements.SPAN, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS, textStyle, null);
            writer.write(theme.getMessage("masthead.lastUpdate")); //NOI18N
            writer.endElement(HTMLElements.SPAN);
            writer.write("&nbsp;"); //NOI18N
            writer.startElement(HTMLElements.SPAN, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS, 
                theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT), null);

	    // FIXME: This date formatting should be in the theme.
            DateFormat dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.LONG,
                context.getViewRoot().getLocale());
            
            writer.write(dateFormat.format(new Date())); 
            writer.endElement(HTMLElements.SPAN);
            writer.write("&nbsp;"); //NOI18N
        }        
        
        writer.endElement(HTMLElements.TD); //NOI18N
    }
    
    /**
     * Render the jobs running info. If the "jobsInfo" facet was specified, 
     * this should be rendered inside of the appropriate div tag. If not the
     * standard jobs running image and "Jobs Running: x" label should be
     * displayed.
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     * @param divIsOpen If true the div tag to output the jobsInfo in is already
     *        open. If false we need to open the div before outputting anything
     * @param theme The current Theme
     */
    protected void renderJobsInfo(FacesContext context, Masthead masthead, 
            ResponseWriter writer, boolean divIsOpen, Theme theme)
            throws IOException {
        
        writer.startElement(HTMLElements.TD, masthead);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.MASTHEAD_STATUS_DIV), null);
        
        UIComponent jobsFacet = masthead.getFacet("jobsInfo"); //NOI18N        
        
        if (jobsFacet != null) {
            RenderingUtilities.renderComponent(jobsFacet, context);
	} else {

	    Icon icon = ThemeUtilities.getIcon(theme, 
		ThemeImages.MASTHEAD_STATUS_ICON);
            icon.setParent(masthead);
	    icon.setId(masthead.getId() + "_jobStatusImage"); //NOI18N 
	    icon.setAlt(
		theme.getMessage("masthead.tasksRunningAltText")); //NOI18N
	    icon.setAlign("top"); //NOI18N
	    icon.setBorder(0);
	    
	    RenderingUtilities.renderComponent(icon, context);
	    
	    writer.write("&nbsp;"); //NOI18N
	    Hyperlink hl = (Hyperlink) masthead.getJobCountLink();
	    RenderingUtilities.renderComponent(hl, context);
	}
            
        writer.endElement(HTMLElements.TD);
        
    }
    
    /**
     * Render the notification info for the given masthead component
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component instance
     * @param writer The ResponseWriter to use
     * @param leaveDivOpen If true the div enclosing the notification info
     *        should not be closed
     * @param theme The current Theme
     */
    protected void renderNotificationInfo(FacesContext context,
            Masthead masthead, ResponseWriter writer, boolean leaveDivOpen,
            Theme theme) throws IOException {        
        // display the specified notification message or facet
                
        writer.startElement(HTMLElements.TD, masthead);
        writer.writeAttribute(HTMLAttributes.CLASS,
            theme.getStyleClass(ThemeStyles.MASTHEAD_STATUS_DIV), null);
        
        UIComponent notificationFacet = 
	    masthead.getFacet("notificationInfo"); //NOI18N
        
        if (notificationFacet != null) {
            RenderingUtilities.renderComponent(notificationFacet, context);
        } else {
	    ImageComponent image = new ImageComponent();
	    image.setParent(masthead);
            image.setId(masthead.getId() + "_notificationInfo");
            image.setIcon(ThemeImages.MASTHEAD_STATUS_ICON); 
            image.setAlign("top"); //NOI18N
            image.setBorder(0);
            image.setAlt(theme.getMessage("Alert.infoImageAltText")); //NOI18N
            
            RenderingUtilities.renderComponent(image, context);
            
            writer.write("&nbsp;"); //NOI18N
            writer.startElement(HTMLElements.SPAN, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.MASTHEAD_LABEL), null); 
            writer.write(masthead.getNotificationMsg());
            writer.endElement(HTMLElements.SPAN);
        }
        writer.endElement(HTMLElements.TD);

    }
    
    /**
     * Render the product info as an image in the appropriate div tag.
     * 
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     */
    protected void renderProductInfo(FacesContext context, Masthead masthead, 
            ResponseWriter writer, String styleName) throws IOException {        
        UIComponent productImage = getProductImage(context, masthead,
		ThemeUtilities.getTheme(context));
	if (productImage == null) {
	    return;
	}

        // render the product name image
        writer.startElement(HTMLElements.DIV, masthead);
        if (styleName != null && styleName.length() > 0) {
            writer.writeAttribute(HTMLAttributes.CLASS, styleName, null);
        }
        RenderingUtilities.renderComponent(productImage, context);
        
        writer.endElement(HTMLElements.DIV); //NOI18N
    }
    
    /**
     * All of the necessary Masthead rendering is done here.
     * 
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component, 
            ResponseWriter writer) throws IOException {
        
        Masthead masthead = (Masthead) component;
        Theme theme = ThemeUtilities.getTheme(context);
        
        // check if it is a secondary masthead
	if (masthead.isSecondary()) {
	    renderSecondaryMasthead(context, masthead, theme, writer);
        } else {
	    renderPrimaryMasthead(context, masthead, theme, writer);
	}
                
    }
    
    /**
     * Render the status area in a table divider
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     */
    protected void renderStatusArea(FacesContext context, Masthead masthead, 
            ResponseWriter writer, Theme theme) throws IOException {
        
	UIComponent statusArea = masthead.getFacet("statusArea");
	if (statusArea != null) {

	    // render the start of the bottom table
	    //
	    startTable(writer, masthead, theme.getStyleClass(
		    ThemeStyles.MASTHEAD_TABLE_END));
	    writer.startElement(HTMLElements.TR, masthead); //NOI18N            
	    // get the text and label styles from the theme
	    //
	    String labelStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_LABEL);
	    String textStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT);

            RenderingUtilities.renderComponent(statusArea, context);

	    writer.endElement(HTMLElements.TR); //NOI18N
	    writer.endElement(HTMLElements.TABLE); //NOI18N
	} else {
	    renderStatusAreaComponents(context, masthead, theme, writer);
	}

    }   
    
    /**
     * Render the current user information in the appropriate div tag.
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     */
    protected void renderUserInfo(FacesContext context, Masthead masthead, 
            ResponseWriter writer, Theme theme) throws IOException {

        // retrieve the label and text styles for the current theme
	//
        String labelStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_LABEL);
        String textStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT);
        
        // render the user, role (if any) and server details in a div
	//
        writer.startElement(HTMLElements.DIV, masthead); //NOI18N
        writer.writeAttribute(HTMLAttributes.CLASS, //NOI18N
            theme.getStyleClass( ThemeStyles.MASTHEAD_DIV_USER), null); 
            
        // Create a separator for the following methods.
	//
        ImageComponent separator = new ImageComponent();
	separator.setParent(masthead);
        separator.setIcon(ThemeImages.MASTHEAD_SEPARATOR);

	renderUserInfo(context, masthead, theme, labelStyle, textStyle, 
		separator, writer);
        
	// Renders the separator if it needs to.
	//
	renderRoleInfo(context, masthead, theme, labelStyle, textStyle, 
		separator, writer);

	renderServerInfo(context, masthead, theme, labelStyle,
	    textStyle, writer);
        
        writer.endElement(HTMLElements.DIV); //NOI18N
    }
    
    /**
     * Render the utility bar in a table row.
     * Note that if there is a <code>utilityBar</code> it is
     * expected to render an opening and closing <code>tr</code>
     * element.
     *
     * @param context The current FacesContext
     * @param masthead The Masthead component
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     */
    protected void renderUtilityBar(FacesContext context, Masthead masthead, 
            ResponseWriter writer, Theme theme) throws IOException {

        UIComponent facet = masthead.getFacet("utilityBar"); //NOI18N
        if (facet != null) {
            RenderingUtilities.renderComponent(facet, context);
            return;
        }
        
        // render the the utility bar in a table row
        writer.startElement(HTMLElements.TR, masthead); //NOI18N
        
        // render the console & version facets (if necessary) in a table divider
        writer.startElement(HTMLElements.TD, masthead); //NOI18N
        writer.writeAttribute("nowrap", "nowrap", null); //NOI18N
        
        String styleName = null;
        // DO NOT HARD CODE STYLES!!!
        String buttonClassName =
		theme.getStyleClass(ThemeStyles.MASTHEAD_BUTTON);

        // render the console facet if specified
	//
        facet = masthead.getFacet("consoleLink"); //NOI18N
        boolean consoleLinkDisplayed = facet != null;
        if (consoleLinkDisplayed) {
            writer.startElement(HTMLElements.DIV, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS, buttonClassName, null);
            
            styleName = ThemeStyles.MASTHEAD_LINK;
            setAttrs(facet, "MastheadConsoleLink", masthead, //NOI18N
                theme.getMessage("masthead.consoleLabel"), //NOI18N
                theme.getStyleClass(styleName), 
                theme.getMessage("masthead.consoleTooltip"), //NOI18N
                theme.getMessage("masthead.consoleStatus")); //NOI18N           
            
            RenderingUtilities.renderComponent(facet, context);
            writer.endElement(HTMLElements.DIV);
            appendDotImage(writer, context, masthead, "_utilPad", 1, 8, theme);
        }
        
        // render the version facet if specified
        facet = masthead.getFacet("versionLink"); //NOI18N
        if (facet != null) {
            
            writer.startElement(HTMLElements.DIV, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS, buttonClassName, null);
            
            styleName = consoleLinkDisplayed ? ThemeStyles.MASTHEAD_LINK_RIGHT :
                ThemeStyles.MASTHEAD_LINK;
            String styleClass = theme.getStyleClass(styleName);
            
            setAttrs(facet, "MastheadVersionLink", masthead, //NOI18N
                theme.getMessage("masthead.versionLabel"), //NOI18N
                styleClass,
                theme.getMessage("masthead.versionTooltip"), //NOI18N
                theme.getMessage("masthead.versionStatus")); //NOI18N
            
            RenderingUtilities.renderComponent(facet, context);
            writer.endElement(HTMLElements.DIV);
        }
        
        // close the console / version table divider
        writer.endElement(HTMLElements.TD);
        
        // if specified, render the search, logout and help facets as well as
        // any other developer specified links
        writer.startElement(HTMLElements.TD, masthead);
        writer.writeAttribute(HTMLAttributes.ALIGN, "right", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.VALIGN, "bottom", null); //NOI18N       
        
        String leftLinkStyle = 
            theme.getStyleClass(ThemeStyles.MASTHEAD_LINK_LEFT);
        String centerLinkStyle =
            theme.getStyleClass(ThemeStyles.MASTHEAD_LINK_CENTER);
        String rightLinkStyle =
            theme.getStyleClass(ThemeStyles.MASTHEAD_LINK_RIGHT);
        String singleLinkStyle =
            theme.getStyleClass(ThemeStyles.MASTHEAD_LINK);
        
        // determine what optional elements are being displayed
        UIComponent logoutFacet = masthead.getFacet("logoutLink"); //NOI18N
        UIComponent helpFacet = masthead.getFacet("helpLink"); //NOI18N
        UIComponent searchFacet = masthead.getFacet("search"); //NOI18N
        Hyperlink[] extraLinks = masthead.getUtilities();
        
        boolean logoutLinkDisplayed = logoutFacet != null;
        boolean helpLinkDisplayed = helpFacet != null;        
        boolean areExtraLinks = extraLinks != null;
        
        // first render the search facet if specified
        if (searchFacet != null) {
            // render the search facet
            RenderingUtilities.renderComponent(searchFacet, context);            
            // now render the separator image if any other elements to the right
            if (logoutLinkDisplayed || helpLinkDisplayed || areExtraLinks) {
                ImageComponent separator = new ImageComponent();
                separator.setId("searchSeparator"); //NOI18N           
                separator.setIcon(ThemeImages.MASTHEAD_SEPARATOR_BUTTONS);
                RenderingUtilities.renderComponent(separator, context);
            }
        }
        
        // render any developer specified links if necessary
        if (areExtraLinks) {
            boolean extraLinksOnly =
                !(logoutLinkDisplayed || helpLinkDisplayed);
            
            
	    // Don't use appendDotImage in a loop, we can reuse the
	    // same icon component.
	    //
            StringBuilder sbId = new StringBuilder("_lp"); //NOI18N
	    int len = sbId.length();
	    Icon dot = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
	    dot.setParent(masthead);
	    dot.setWidth(8);
	    dot.setHeight(1);
	    dot.setBorder(0);
	    dot.setAlt(""); //NOI18N

            // render any developer specifed custom links
	    //
            List children = masthead.getChildren();
            for (int i = 0; i < extraLinks.length; i++) {
                Hyperlink link = extraLinks[i];
                if (link.getParent() == null) {
                    link.setParent(masthead);
                }
                writer.startElement(HTMLElements.DIV, masthead);
                writer.writeAttribute(HTMLAttributes.CLASS, 
			buttonClassName, null);
                RenderingUtilities.renderComponent(link, context);
                writer.endElement(HTMLElements.DIV);

		dot.setId(sbId.append(Integer.toString(i)).toString());
                sbId.setLength(len);

		RenderingUtilities.renderComponent(dot, context);        
            }
        }        
        
        // now render the logoutLink facet if specified
        if (logoutLinkDisplayed) {  
            writer.startElement(HTMLElements.DIV, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS, buttonClassName, null);
            String style = ThemeStyles.MASTHEAD_LINK;
            
            if (areExtraLinks && helpLinkDisplayed) {
                // extra links and help are present, use center link style
                style = ThemeStyles.MASTHEAD_LINK_CENTER;
            } else if (areExtraLinks && !helpLinkDisplayed) {
                // extra links but no help - use right link style
                style = ThemeStyles.MASTHEAD_LINK_RIGHT;
            } else if (!areExtraLinks && helpLinkDisplayed) {
                // help but no extra links - use left link style
                style = ThemeStyles.MASTHEAD_LINK_LEFT;
            }
            
            setAttrs(logoutFacet, "MastheadLogoutLink", masthead, //NOI18N
                theme.getMessage("masthead.logoutLabel"), //NOI18N
                theme.getStyleClass(style),
                theme.getMessage("masthead.logoutTooltip"), //NOI18N
                theme.getMessage("masthead.logoutStatus")); //NOI18N
           
            RenderingUtilities.renderComponent(logoutFacet, context);
            writer.endElement(HTMLElements.DIV);
            appendDotImage(writer, context, masthead,
		"_logoutPad", 1, 8, theme);//NOI18N
        }
        
        if (helpFacet != null) {
            writer.startElement(HTMLElements.DIV, masthead);
            writer.writeAttribute(HTMLAttributes.CLASS, buttonClassName, null);
            String style = ThemeStyles.MASTHEAD_LINK;
            
            if (areExtraLinks || logoutLinkDisplayed) {
                // extra and/or logout link displayed, use right link style
                style = ThemeStyles.MASTHEAD_LINK_RIGHT;
            }
            
            setAttrs(helpFacet, "MastheadHelpLink", masthead, //NOI18N
                theme.getMessage("masthead.helpLabel"), //NOI18N
		theme.getStyleClass(style),
                theme.getMessage("masthead.helpLabel"), //NOI18N
                theme.getMessage("masthead.helpLabel")); //NOI18N
            
            RenderingUtilities.renderComponent(helpFacet, context);
            writer.endElement(HTMLElements.DIV);
            appendDotImage(writer, context, masthead, "_helpPad", 1, 8, theme);
        }
        
        // close the table dividier for the logout & help links
        writer.endElement(HTMLElements.TD);
        
        // close the utility bar table row
        writer.endElement(HTMLElements.TR);
    }
    
    /**
     * Helper method to set the given id, parent, label and styleClass for the
     * given component (if they haven't already been set)
     */
    private void setAttrs(UIComponent component, String id, UIComponent parent,
            String label, String styleClass, String toolTip, String focusText) {

        Map attrs = component.getAttributes();
        StringBuffer focusBuff = new StringBuffer(64);
        focusBuff.append("window.status='") //NOI18N
            .append(focusText)
            .append("'; return true; "); //NOI18N
        focusText = focusBuff.toString();            
        
        if (component.getId() == null) {
            component.setId(id); 
        }
        
	// FIXME: it is never good to add children in a renderer.
	//
        if (component.getParent() == null) {
            parent.getChildren().add(component); 
        }
        
        if (attrs.get("text") == null) { //NOI18N
            attrs.put("text", label); //NOI18N             
        }
             
        if (attrs.get("toolTip") == null) { //NOI18N
            attrs.put("toolTip", toolTip); //NOI18N
        }
        
        if (attrs.get("onFocus") == null) { //NOI18N
            attrs.put("onFocus", focusText); //NOI18N
        }
        
        if (attrs.get("onMouseOver") == null) { //NOI18N
            attrs.put("onMouseOver", focusText); //NOI18N
        }
        
        if (attrs.get("onMouseOut") == null) { //NOI18N
            attrs.put("onMouseOut", "window.status=''; return true;"); //NOI18N
        }
        
        if (attrs.get("onBlur") == null) { //NOI18N
            attrs.put("onBlur", "window.status=''; return true;"); //NOI18N
        }
    }
    
    /**
     * Helper method to start a layout table with the given style class name
     *
     * @param writer The current ResponseWriter
     * @param masthead The current Masthead component
     * @param styleName The name of the style class to use for this table
     */
    private void startTable(ResponseWriter writer, Masthead masthead,
            String styleName) throws IOException {

        writer.startElement(HTMLElements.TABLE, masthead);
        writer.writeAttribute(HTMLAttributes.WIDTH, "100%", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.BORDER, "0", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.CELLPADDING, "0", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.CELLSPACING, "0", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.CLASS, styleName, null);
        writer.writeAttribute(HTMLAttributes.TITLE, "", null); //NOI18N
    }

    /** output the link count in the given style */
    private void writeAlarmCount(ResponseWriter writer, FacesContext context,
            String imageName, String imageAlt, Masthead masthead, String style,
            int count, Theme theme) throws IOException {
        
        Icon icon = ThemeUtilities.getIcon(theme, imageName);
        icon.setId(imageName);
        icon.setAlt(theme.getMessage(imageAlt));
        RenderingUtilities.renderComponent(icon, context);
        style = theme.getStyleClass(style);
        
        writer.startElement(HTMLElements.SPAN, masthead);
        writer.writeAttribute(HTMLAttributes.CLASS, style, null);
        writer.write("&nbsp;" + count + "&nbsp;&nbsp;&nbsp;"); //NOI18N
        writer.endElement(HTMLElements.SPAN);
    } 
    
    /**
     * Helper function to write a span tag with the given style and given text.
     *
     * @param writer The current ResponseWriter
     * @param masthead The Masthead component
     * @param styleName The style class name to use
     * @param text The text to output inside of the span
     */
    private void writeSpan(ResponseWriter writer, Masthead masthead, 
            String styleName, String text, FacesContext context, String id)
            throws IOException {

        writer.startElement(HTMLElements.SPAN, masthead); //NOI18N
        writer.writeAttribute(HTMLAttributes.ID, 
		masthead.getClientId(context) + id, HTMLAttributes.ID);
        writer.writeAttribute(HTMLAttributes.CLASS, styleName, null);
        writer.write(text != null ? text : ""); //NOI18N
        writer.endElement(HTMLElements.SPAN);
    }
    
    private void appendDotImage(ResponseWriter writer, FacesContext context,
	    Masthead masthead, String id, int ht, int wd, Theme theme)
	    throws IOException {

        Icon dot = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
	dot.setParent(masthead);
	dot.setId(id);
        dot.setWidth(wd);
        dot.setHeight(ht);
        dot.setBorder(0);
        dot.setAlt(""); //NOI18N
        RenderingUtilities.renderComponent(dot, context);        
    }
    
    private void appendSeparator(ResponseWriter writer, FacesContext context,
            Masthead masthead, String id, String align) throws IOException {

        ImageComponent separator = new ImageComponent();
        separator.setParent(masthead);
        separator.setId(masthead.getId() + id); //NOI18N           
        separator.setIcon(ThemeImages.MASTHEAD_SEPARATOR_STATUS);
        if (align!= null) {
            separator.setAlign(align);
        }
        RenderingUtilities.renderComponent(separator, context);
    }
    
    
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
    protected UIComponent getBrandImage(FacesContext context,
	    Masthead masthead, Theme theme) {

        UIComponent facet = masthead.getFacet("brandImage"); //NOI18N
        if (facet != null) {
	    return facet;
	}

	String imageAttr = masthead.getBrandImageURL();
	if (imageAttr != null && imageAttr.trim().length() != 0) {

	    ImageComponent image = new ImageComponent();

	    // use the brand image properties specified on the component
	    //
	    image.setUrl(imageAttr);

	    imageAttr = masthead.getBrandImageDescription();
	    if (imageAttr != null && imageAttr.trim().length() != 0) {
		image.setAlt(imageAttr);
	    }
	    
	    int dim = masthead.getBrandImageHeight();
	    if (dim != 0) {
		image.setHeight(masthead.getBrandImageHeight());
	    }
	    
	    dim = masthead.getBrandImageWidth();
	    if (dim != 0) {
		image.setWidth(masthead.getBrandImageWidth());
	    }
	    return image;
	}

	// no facet, no props - output the standard java brand image
	// First see if there is valid image. If there is no
	// image, return null, else retun the value from
	// ThemeUtilities.getIcon.
	//
	Icon icon = null;
	try {
	    String imagePath = 
		theme.getImagePath(ThemeImages.MASTHEAD_CORPLOGO);
	    if (imagePath == null) {
		return null;
	    }
	    icon = ThemeUtilities.getIcon(theme, ThemeImages.MASTHEAD_CORPLOGO);
	    icon.setId(masthead.getId() + "_brandImage"); //NOI18N 
	    icon.setParent(masthead);
	} catch (Exception e) {
	    // Don't care.
	}
	return icon;
    }

    /**
     * Return a UIComponent suitable to render for the product image.
     * If the <code>productInfo</code> facet exists return it, otherwise
     * if the <code>masthead.getProductImageURL()</code> exists
     * create an <code>ImageComponent</code> initialized with appropriate
     * values and return it, otherwise return <code>null</code>.
     */
    protected UIComponent getProductImage(FacesContext context, 
	    Masthead masthead, Theme theme) {

        UIComponent productFacet = masthead.getFacet("productInfo"); //NOI18N
	if (productFacet != null) {
	    return productFacet;
	}
        
	String imageUrl = masthead.getProductImageURL();
        if (imageUrl == null || imageUrl.trim().length() == 0) {
	    return null;
	}
        
	ImageComponent image = new ImageComponent();
	image.setId(masthead.getId() + "_productInfo");
	image.setParent(masthead);
	
	image.setUrl(imageUrl);
	image.setHeight(masthead.getProductImageHeight());
	image.setWidth(masthead.getProductImageWidth());
	image.setAlt(masthead.getProductImageDescription());
	
	return image;
    }

    /**
     * Render a secondary masthead.
     */
    protected void renderSecondaryMasthead(FacesContext context, 
	    Masthead masthead, Theme theme, ResponseWriter writer)
	    throws IOException {

	startTable(writer, masthead,  
	    theme.getStyleClass(ThemeStyles.MASTHEAD_SECONDARY_STYLE));
	writer.startElement(HTMLElements.TR, masthead);
	writer.startElement(HTMLElements.TD, masthead);
		  
	renderProductInfo(context, masthead, writer, null);
	writer.endElement(HTMLElements.TD);
	writer.startElement(HTMLElements.TD, masthead);
	writer.writeAttribute(HTMLAttributes.ALIGN, "right", null);
	
	UIComponent brandImage = getBrandImage(context, masthead, theme);
	if (brandImage != null) {
	    RenderingUtilities.renderComponent(brandImage, context);
	}
	writer.endElement(HTMLElements.TD);
	writer.endElement(HTMLElements.TR);
	writer.endElement(HTMLElements.TABLE);
    }

    /**
     * Render a primary masthead.
     */
    protected void renderPrimaryMasthead(FacesContext context, 
	    Masthead masthead, Theme theme, ResponseWriter writer) 
	    throws IOException {

	// start the div the entire masthead is wrapped in
	writer.startElement(HTMLElements.DIV, masthead);
	String styleClass = theme.getStyleClass(ThemeStyles.MASTHEAD_DIV);
	RenderingUtilities.renderStyleClass(context, writer, masthead,
	    styleClass);

	String style = masthead.getStyle();
	if (style != null) {
	    writer.writeAttribute(HTMLAttributes.STYLE, style, null);
	}
	writer.write("\n"); //NOI18N

	RenderingUtilities.renderSkipLink(SKIP_UTILITY,
		theme.getStyleClass(ThemeStyles.SKIP_MEDIUM_GREY1), null, 
		theme.getMessage("masthead.statusSkipTagAltText"), //NOI18N
		null, masthead, context);

	// start the table the masthead uses for layout
	startTable(writer, masthead,
	    theme.getStyleClass(ThemeStyles.MASTHEAD_TABLE_TOP));
    
	// render the utility bar
	renderUtilityBar(context, masthead, writer, theme);
    
	// close the utility bay layout table
	writer.endElement(HTMLElements.TABLE); //NOI18N
    
	// start the layout table for the app info, status area &
	// brand image
	startTable(writer, masthead,
	    theme.getStyleClass(ThemeStyles.MASTHEAD_TABLE_BOTTOM));
    
	// all these areas go in a single row
	writer.startElement(HTMLElements.TR, masthead); //NOI18N
	
	// render the app info - typically user, server and product name details
	// userinfo, role info and serverinfo appear in a single TD
	//
	renderApplicationInfo(context, masthead, writer, theme);
    
	// The product and brand appear in a single TD
	//
	renderBrandImage(context, masthead, theme, writer);

	writer.endElement(HTMLElements.TR);

	// Insert a row of space below the product name and brand image
	//
        writer.startElement(HTMLElements.TR, masthead);
        writer.startElement(HTMLElements.TD, masthead);
        writer.writeAttribute(HTMLAttributes.COLSPAN, "2", null); //NOI18N
            
        writer.startElement(HTMLElements.DIV, masthead);
        writer.writeAttribute(HTMLAttributes.CLASS,
            theme.getStyleClass(ThemeStyles.MASTHEAD_HRULE), null); 
        appendDotImage(writer, context, masthead, "_mhrule", 1, 1, theme);
        writer.endElement(HTMLElements.DIV);
        
        writer.endElement(HTMLElements.TD);
        writer.endElement(HTMLElements.TR);

	writer.endElement(HTMLElements.TABLE);

	renderStatusArea(context, masthead, writer, theme);

	// close the div that wraps the entire masthead
	writer.endElement(HTMLElements.DIV); //NOI18N
	RenderingUtilities.renderAnchor(SKIP_UTILITY, masthead, context);
    }

    /**
     * Render the status area based on the status area masthead properties.
     */
    protected void renderStatusAreaComponents(FacesContext context,
	    Masthead masthead, Theme theme, ResponseWriter writer) 
	    throws IOException {

	boolean isDateTime = masthead.isDateTime();
	String notificationMsg = masthead.getNotificationMsg();
	int[] alarmCounts = masthead.getAlarmCounts();
	int jobCount = masthead.getJobCount();

	UIComponent notificationInfo = 
	    masthead.getFacet("notificationInfo"); //NOI18N
	UIComponent jobsInfo = masthead.getFacet("jobsInfo"); //NOI18N
	UIComponent dateTimeInfo = masthead.getFacet("dateTimeInfo"); //NOI18N
	UIComponent currentAlarmsInfo = 
	    masthead.getFacet("currentAlarmsInfo"); //NOI18N

	// at least one of the default status area items is displayed
	//
	boolean showNotification = notificationInfo != null ||
	    (notificationMsg != null && notificationMsg.length() != 0);

	boolean showJobs = jobCount != -1 || jobsInfo != null;
	
	boolean havestatus = isDateTime || alarmCounts != null ||
	    showJobs || dateTimeInfo != null || currentAlarmsInfo != null ||
	    showNotification;
	
	// No status area artifacts
	//
	if (!havestatus) {
	    return;
	}

        // render the start of the bottom table
        startTable(writer, masthead, theme.getStyleClass(
                ThemeStyles.MASTHEAD_TABLE_END));
        writer.startElement(HTMLElements.TR, masthead);
        
        // get the text and label styles from the theme
        String labelStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_LABEL);
        String textStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT);

	boolean separatorFlag = false;

	if (showNotification) {                
	    // notication message needs to be displayed
	    renderNotificationInfo(context, masthead, writer, 
		showNotification && showJobs, theme);
	    separatorFlag = true;
	}
	
	if (showJobs) {
	    if (separatorFlag) {
		writer.startElement(HTMLElements.TD, masthead);
		writer.writeAttribute(HTMLAttributes.VALIGN, "middle", null);
		appendSeparator(writer, context, masthead, "_jobSep", "top"); //NOI18N
		writer.endElement(HTMLElements.TD);
		separatorFlag = false;
	    }
	    // jobs info needs to displayed
	    renderJobsInfo(context, masthead, writer, 
		showNotification && showJobs, theme);
	    separatorFlag = true;
	}
		    
	if (isDateTime || dateTimeInfo != null) {
	    if (separatorFlag) {
		writer.startElement(HTMLElements.TD, masthead);
		writer.writeAttribute(HTMLAttributes.VALIGN, "middle",
		    null);
		appendSeparator(writer, context, masthead, "_dateSep","top"); //NOI8N
		writer.endElement(HTMLElements.TD);
		separatorFlag = false;
	    }
	    // date and time stamp needs to be displayed
	    renderDateTimeInfo(context, masthead, writer, theme);
	    separatorFlag = true;
	}
	
	if ((alarmCounts != null && alarmCounts.length == 4) || 
		currentAlarmsInfo != null) {
	    if (separatorFlag) {
		writer.startElement(HTMLElements.TD, masthead);
		writer.writeAttribute(HTMLAttributes.VALIGN, "middle", null);
		appendSeparator(writer, context, masthead, "_alarmSep", "top"); //NOI18N
		writer.endElement(HTMLElements.TD);
	    }
	    // current alarms info needs to be displayed
	    renderAlarmsInfo(context, masthead, writer, alarmCounts, theme);
	}
    
	writer.endElement(HTMLElements.TR); //NOI18N
	writer.endElement(HTMLElements.TABLE); //NOI18N
    }

    // Its not clear what the policy should be regarding the
    // rendering of the user info. Before this code was refactored
    // there was an illogical dependency between the existence
    // user info and server info. 
    // Unlink roleInfo user info and server info are rendered whether
    // or not there is data. This can yield a label without
    // any data. The roleInfo is only rendered if there is "userInfo"
    // or facets.

    private void renderUserInfo(FacesContext context, Masthead masthead,
	    Theme theme, String labelStyle, String textStyle,
	    UIComponent separator, ResponseWriter writer) throws IOException {

        UIComponent facet = masthead.getFacet("userInfoLabel"); //NOI18N
        if (facet != null) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            String label = masthead.getUserInfoLabel();
            if (label == null) {
                label = theme.getMessage("masthead.userLabel"); //NOI18N
            }
            writeSpan(writer, masthead, labelStyle, label, context,  
                     "_userLabel"); //NOI18N 
        }
        
	// This should be some sort of CSS selector on the 
	// span or the previous span or the span should contain
	// the facet since the facet may not add any space.
	//
        writer.write("&nbsp;"); //NOI18N
        
        facet = masthead.getFacet("userInfo"); //NOI18N
        if (facet != null) {            
            RenderingUtilities.renderComponent(facet, context);            
        } else {
            writeSpan(writer, masthead, textStyle, masthead.getUserInfo(),
                    context, "_userInfo"); //NOI18N
        }

	// We know parent has been set.
	//
        separator.setId("_userInfoSeparator"); //NOI18N           
        RenderingUtilities.renderComponent(separator, context);
    }

    /**
     * Unlike user info and server info role info is only rendered
     * if there are role facets, either one, or if 
     * <code>masthead.getRoleInfo</code> returns non null and a 
     * non empty string.
     * <p>
     * Note that if there is a <code>roleInfoLabel</code> facet but no
     * <code>roleInfo</code> facet, the <code>roleInfoLabel</code> facet
     * will be rendered, whether or not the <code>roleInfo</code>
     * attribute has data. Likewise if there is a <code>roleInfo</code>
     * facet and not <code>roleInfoLabel</code> facet and no 
     * <code>roleInfo</code> attribute data, the <code>roleInfo</code>
     * facet is still rendered, yielding data without a label.
     */
    private void renderRoleInfo(FacesContext context, Masthead masthead, 
	    Theme theme, String labelStyle, String textStyle,
	    UIComponent separator, ResponseWriter writer) throws IOException {
        
	boolean haveRoleFacet = false;

	// If there is no roleInfo nothing is rendered unless there
	// are facets.
	//
	String roleInfo = masthead.getRoleInfo();
	boolean haveRoleInfo = 
	    roleInfo != null && roleInfo.trim().length() != 0;

	// We either have a facet or roleinfo
	// So render a label.
	//
	UIComponent roleLabelFacet = 
	    masthead.getFacet("roleInfoLabel"); //NOI18N
	if (roleLabelFacet != null) {
	    RenderingUtilities.renderComponent(roleLabelFacet, context);
	    haveRoleFacet = true;
	} else {
	    if (haveRoleInfo) {
		String label = masthead.getRoleInfoLabel();
		if (label == null) {
		    label = theme.getMessage("masthead.roleLabel"); //NOI18N
		}
		writeSpan(writer, masthead, labelStyle, label, context,  
		  "_roleLabel"); //NOI18N 
	    }
	}

	// This should be some sort of CSS selector on the 
	// span or the previous span or the span should contain
	// the facet since the facet may not add any space.
	//
	writer.write("&nbsp;"); //NOI18N

        UIComponent roleInfoFacet = masthead.getFacet("roleInfo"); //NOI18N
	if (roleInfoFacet != null) {
	    RenderingUtilities.renderComponent(roleInfoFacet, context);   
	    haveRoleFacet = true;
	} else {
	    if (haveRoleInfo) {
		writeSpan(writer, masthead, textStyle, roleInfo,
                    context, "_roleInfo"); //NOI18N
	    }
        }

	// Reuse the separator
	// We know the parent was already set.
	//
	if (haveRoleFacet || haveRoleInfo) {
	    separator.setId("_roleInfoSeparator"); //NOI18N           
	    RenderingUtilities.renderComponent(separator, context);
	}
    }

    private void renderServerInfo(FacesContext context, Masthead masthead,
	    Theme theme, String labelStyle, String textStyle,
	    ResponseWriter writer) throws IOException {

        UIComponent facet = masthead.getFacet("serverInfoLabel"); //NOI18N
        if (facet != null) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            String label = masthead.getServerInfoLabel();
            if (label == null) {
                label = theme.getMessage("masthead.serverLabel"); //NOI18N
            }
            writeSpan(writer, masthead, labelStyle, label, context, 
                    "_serverLabel"); //NOI18N 
        }

	// This should be some sort of CSS selector on the 
	// span or the previous span or the span should contain
	// the facet since the facet may not add any space.
	//
        writer.write("&nbsp;"); //NOI18N
        
        facet = masthead.getFacet("serverInfo"); //NOI18N
        if (facet != null) {            
            RenderingUtilities.renderComponent(facet, context);
        } else {
            writeSpan(writer, masthead, textStyle, masthead.getServerInfo(),
                    context, "_serverInfo"); //NOI18N
        }
    }

    /**
     * Renders the logo separator and then the logo.
     * If there is no logo a "transparent" separator is rendered
     * to maintain the height when there is a logo.
     */
    private void renderBrandImage(FacesContext context, 
	    Masthead masthead, Theme theme, ResponseWriter writer)
	    throws IOException {

        writer.startElement(HTMLElements.TD, masthead);
        writer.writeAttribute(HTMLAttributes.CLASS,
            theme.getStyleClass(ThemeStyles.MASTHEAD_TD_LOGO), null); 
        writer.writeAttribute(HTMLAttributes.WIDTH, "1%", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.ALIGN, "right", null); //NOI18N
        writer.writeAttribute(HTMLAttributes.NOWRAP, HTMLAttributes.NOWRAP,
		HTMLAttributes.NOWRAP);
        
	// If we don't have a brand image, just render a transparend
	// spacer to maintain the height of the masthead body.
	//
	UIComponent brandImage = getBrandImage(context, masthead, theme);
	if (brandImage == null) {
	    appendDotImage(writer, context, masthead, "_logoSep", 37, 2, theme);
	} else {
	    UIComponent separatorFacet = 
		masthead.getFacet("separatorImage"); //NOI18N
	    if (separatorFacet == null) {
		// Use the default separator
		//
		separatorFacet = ThemeUtilities.getIcon(theme,
		    ThemeImages.MASTHEAD_JAVA_LOGO_SEPARATOR);
		separatorFacet.setId("_logoSep"); //NOI18N
		separatorFacet.setParent(masthead);
	    }
	    RenderingUtilities.renderComponent(separatorFacet, context);
	    appendDotImage(writer, context, masthead, "_logoPad", 1, 10, theme);
	    RenderingUtilities.renderComponent(brandImage, context);
	} 
        writer.endElement(HTMLElements.TD);
    }
}
