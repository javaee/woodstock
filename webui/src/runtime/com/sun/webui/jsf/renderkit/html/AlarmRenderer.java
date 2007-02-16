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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.jsf.component.Alarm;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for an {@link Alarm} component.</p>
 * 
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Alarm"))
public class AlarmRenderer extends ImageRenderer {
    // Label position.
    private static final String LABEL_LEFT = "left"; //NOI8N
    private static final String LABEL_RIGHT = "right"; //NOI8N
    private static final String WHITE_SPACE = "&nbsp;"; //NOI8N

    private static final String CRITICAL_ALT_TEXT_KEY =
	"Alarm.criticalImageAltText"; //NOI18N
    private static final String MAJOR_ALT_TEXT_KEY =
	"Alarm.majorImageAltText"; //NOI18N
    private static final String MINOR_ALT_TEXT_KEY =
	"Alarm.minorImageAltText"; //NOI18N
    private static final String DOWN_ALT_TEXT_KEY =
	"Alarm.downImageAltText"; //NOI18N
    /** Creates a new instance of AlarmRenderer */
    public AlarmRenderer() {
        // default constructor
    }

    /**
     * Render the start of the image element
     *
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        if (context == null || component == null) {
            throw new NullPointerException(
		"FacesContext or UIComponent is null"); //NOI18N
        }

        Alarm alarm = (Alarm) component;
	String label = alarm.getText();
	String severity = getSeverity(alarm);
	boolean severityOk = isSeverityOk(severity);

	// If the severity is "ok" and there is no url just render
	// the label, there's no image.
	//
	boolean showImage = !(severityOk && alarm.getUrl() == null);

	if (showImage) {
	    Theme theme = ThemeUtilities.getTheme(context);
	    if (label == null) {
                renderImage(context, alarm, severity, writer);
	    } else {
		// Since we have a label and an image always add the space
		//
		if (LABEL_LEFT.equalsIgnoreCase(alarm.getTextPosition())) {
		    renderLabel(context, alarm, label, true, true, writer);
                    renderImage(context, alarm, severity, writer);
		} else {
                    renderImage(context, alarm, severity, writer);
		    renderLabel(context, alarm, label, false, true, writer);
		}
	    }
	} else if (label != null) {
	    // Just a label no additional space.
	    //
	    renderLabel(context, alarm, label, false, false, writer);
	}
    }

    /**
     * Render the label with additional white space if addSpace is true.
     * Render the space first if addSpace is true and labelLeft is true
     * or render the label first if labelLeft is false.
     */
    private void renderLabel(FacesContext context, Alarm alarm,
	    String label, boolean labelLeft, boolean addSpace,
	    ResponseWriter writer) throws IOException {

	if (labelLeft) {
	    writer.writeText(label, null);
	    if (addSpace) {
		writer.write(WHITE_SPACE);
	    }
	} else {
	    if (addSpace) {
		writer.write(WHITE_SPACE);
	    }
	    writer.writeText(label, null);
	}
    }

    private void renderImage(FacesContext context, Alarm alarm, String severity,
           ResponseWriter writer) throws IOException {

       super.renderStart(context, alarm, writer);
       ImageComponent sevImage = getImage(context, alarm, severity);
       super.renderAttributes(context, sevImage, writer);

       String integerAttributes[] = { "border", "hspace", "vspace" }; //NOI18N
       addIntegerAttributes(context, alarm, writer, integerAttributes);
       String stringAttributes[] = { "align",  "onClick", "onDblClick"}; //NO18N
       addStringAttributes(context, alarm, writer, stringAttributes);
  } 
    private ImageComponent getImage(FacesContext context, Alarm alarm,
               String severity) {

       Theme theme = ThemeUtilities.getTheme(context);

       String sevIcon = null;
       String sevAlt = null;
       String sevToolTip = null;
       String sevUrl = null;

       // If the data for the alarm icons existed in Themes then you could obtain
       // the necessary data from "theme.getIcon()" but you would have to transfer the Icon's
       // data from the returned Icon to the ImageComponent to ensure that developer values
       // are respected.

       if (severity.equalsIgnoreCase(alarm.SEVERITY_CRITICAL)) {
           sevIcon = ThemeImages.ALARM_CRITICAL_MEDIUM;
           sevAlt = theme.getMessage(CRITICAL_ALT_TEXT_KEY);
           sevToolTip = theme.getMessage(CRITICAL_ALT_TEXT_KEY);
       } else
       if (severity.equalsIgnoreCase(alarm.SEVERITY_MAJOR)) {
           sevIcon = ThemeImages.ALARM_MAJOR_MEDIUM;
           sevAlt = theme.getMessage(MAJOR_ALT_TEXT_KEY);
           sevToolTip = theme.getMessage(MAJOR_ALT_TEXT_KEY);
           
       } else
       if (severity.equalsIgnoreCase(alarm.SEVERITY_MINOR)) {
           sevIcon = ThemeImages.ALARM_MINOR_MEDIUM;
           sevAlt = theme.getMessage(MINOR_ALT_TEXT_KEY);
           sevToolTip = theme.getMessage(MINOR_ALT_TEXT_KEY);
       } else
       if (severity.equalsIgnoreCase(alarm.SEVERITY_DOWN)) {
           sevIcon = ThemeImages.ALARM_DOWN_MEDIUM;
           sevAlt = theme.getMessage(DOWN_ALT_TEXT_KEY);
           sevToolTip = theme.getMessage(DOWN_ALT_TEXT_KEY);
       }

       // If the developer specified an URL it takes precendence
       // over the icon in ImageRenderer.
       // See if the developer overrode the severity based icon.
       //
       String icon = alarm.getIcon();
       if (icon != null) {
            sevIcon = icon;
       }
       String alt = alarm.getAlt();
       if (alt != null) {
           sevAlt = alt;
       }
       String toolTip = alarm.getToolTip();
       if (toolTip != null) {
           sevToolTip = toolTip;
       }
       
       String url  = alarm.getUrl();
       if(url != null) {
           sevUrl = url;
       }

       // We don't want to pass an Icon to the ImageRenderer
       // because if it sees an Icon, it ignores too much
       //
       ImageComponent sevImage = new ImageComponent();
       sevImage.setIcon(sevIcon);
       sevImage.setUrl(url);
       sevImage.setAlt(sevAlt);
       sevImage.setToolTip(sevToolTip);
       int dim = alarm.getHeight();
       if (dim >= 0) {
           sevImage.setHeight(dim);
           //height = dim;
       }
       // width
       dim = alarm.getWidth();
       if (dim >= 0) {
           sevImage.setWidth(dim);
           //image.width = dim;
       }

       return sevImage;
   }

    /**
     * Render the end of the image element
     *
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

	String severity = getSeverity((Alarm)component);
	boolean severityOk = isSeverityOk(severity);

	// If the severity is "ok" and there is no url just render
	// or severity was not "ok", close the img element.
	//
	if (!(severityOk && ((Alarm)component).getUrl() == null)) {
	    super.renderEnd(context, component, writer);
	}
    }

    /**
     * Render the image element's attributes
     * Does nothing. super.renderAttributes is called in
     * other private methods to render the image.
     * But we don't want AbstractRenderer's call to this method
     * to mess up the sequencing of the rendering.
     *
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderAttributes(FacesContext context,
	    UIComponent component, ResponseWriter writer) throws IOException {

    }

    /**
     * Get alarm severity. 
     * Return the severity or DEFAULT_SEVERITY if severity is not set
     * or if the severity is a custom severity
     *
     * @param alarm The Alarm component.
     * @param theme The Theme object to use.
     */
    private String getSeverity(Alarm alarm) {
	String severity = alarm.getSeverity();
        if (severity == null || !(severity.equals(alarm.SEVERITY_CRITICAL) || 
                severity.equals(alarm.SEVERITY_DOWN) ||
                severity.equals(alarm.SEVERITY_MAJOR) ||
                severity.equals(alarm.SEVERITY_MINOR))) {
             severity = alarm.DEFAULT_SEVERITY;
        }
	return severity.toLowerCase();
    }

    /**
     * If the severity is ok, but url is not null, show an image
     * even though the severity is "ok". The quidelines say "ok"
     * has no image but if the developer wants it show it.
     */
    private boolean isSeverityOk(String severity) {
	return Alarm.DEFAULT_SEVERITY.equalsIgnoreCase(severity);
    }

}
