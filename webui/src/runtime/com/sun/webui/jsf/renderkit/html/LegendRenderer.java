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
import java.util.Properties;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.jsf.component.Legend;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for an {@link Legend} component.</p>
 * 
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Legend"))
public class LegendRenderer extends AbstractRenderer {

    // Default position.
    private static final String DEFAULT_POSITION = "right";

    /** Creates a new instance of LegendRenderer */
    public LegendRenderer() {
        // default constructor
    }    

    /**
     * Renders the legend.
     * 
     * @param context The current FacesContext
     * @param component The Legend object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        if (context == null || component == null || writer == null) {
            throw new NullPointerException();
        }
        
	Legend legend = (Legend) component;

        if (!legend.isRendered()) {
            return;
        }

	// Render the outer div
	renderOuterDiv(context, legend, writer);
	// Render the legend image
	RenderingUtilities.renderComponent(legend.getLegendImage(), context);
	writer.write("&nbsp;"); // NOI18N
	// Render the legend text
	String text = (legend.getText() != null) ? legend.getText() : 
	    getTheme().getMessage("Legend.requiredField"); //NOI18N
	writer.writeText(text, null);
	// Close the outer div
	writer.endElement("div"); //NOI18N
    }	

    /** 
     * Renders the outer div which contains the legend.
     * 
     * @param context The current FacesContext
     * @param alert The Legend object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderOuterDiv(FacesContext context, 
            Legend legend, ResponseWriter writer) throws IOException {

        String style = legend.getStyle();
	String id = legend.getClientId(context);
	String divStyleClass = getTheme().getStyleClass(
			   ThemeStyles.LABEL_REQUIRED_DIV);
	String align = (legend.getPosition() != null) ?
	    legend.getPosition() : DEFAULT_POSITION;

        writer.startElement("div", legend); //NOI18N
	if (id != null) {
	    writer.writeAttribute("id", id, null);  //NOI18N
	}
	writer.writeAttribute("align", align, null); //NOI18N
        if (style != null) {
            writer.writeAttribute("style", style, "style");  //NOI18N
        }
	RenderingUtilities.renderStyleClass(context, writer,
	    (UIComponent) legend, divStyleClass);
    }

    /*
     * Utility to get theme.
     */
    private Theme getTheme() {
	return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }

}

