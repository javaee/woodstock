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

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.component.HelpInline;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;


/**
 * Renders an instance of the {@link HelpInline} component.
 *
 * @author Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.HelpInline"))
public class HelpInlineRenderer extends AbstractRenderer {
    
    /** Creates a new instance of HelpInlineRenderer */
    public HelpInlineRenderer() {
    }
    
    /**
     * Render the start of the HelpInline component.
     * 
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderStart(FacesContext context, UIComponent component, 
            ResponseWriter writer) throws IOException {
        // render start of HelpInline
        HelpInline help = (HelpInline) component;
        Theme theme = ThemeUtilities.getTheme(context);
        
        writer.startElement("div", help);
        
        String style = null;
        
        if (help.getType().equals("page")) {
            style = theme.getStyleClass(ThemeStyles.HELP_PAGE_TEXT);
        } else {
            style = theme.getStyleClass(ThemeStyles.HELP_FIELD_TEXT);
        }
        
        addCoreAttributes(context, help, writer, style);
        
	String text = ConversionUtilities.convertValueToString(help,
		help.getText());
        if (text != null) {
            writer.write(text);
            writer.write("&nbsp;&nbsp;");
        }
    }
    
    /**
     * Render the end of the HelpInline component.
     * 
     * @param context The current FacesContext
     * @param component The ImageComponent object to use
     * @param writer The current ResponseWriter
     *
     * @exception IOException if an input/output error occurss
     */
    protected void renderEnd(FacesContext context, UIComponent component, 
            ResponseWriter writer) throws IOException {
        writer.endElement("div");
    }
}
