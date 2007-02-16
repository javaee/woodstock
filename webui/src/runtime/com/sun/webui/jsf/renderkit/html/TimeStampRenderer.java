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
import java.util.Date;
import java.util.Locale;
import java.io.IOException;
import java.text.DateFormat;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.component.TimeStamp;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;

/**
 * <p>Renders an instance of the TimeStamp component.</p>
 *
 * @author Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.TimeStamp"))
public class TimeStampRenderer extends AbstractRenderer {
    
    /** Creates a new instance of TimeStampRenderer */
    public TimeStampRenderer() {
    }
    
    // Core attributes that are simple pass throughs
    private static final String coreAttributes[] =
    { "style", "title" }; // NOI18N
    
    /**
     * <p>Render the end element for the TimeStamp.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
     * @param writer <code>ResponseWriter</code> to which the element
     *  start should be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        TimeStamp timeStamp = (TimeStamp) component;
        Theme theme = ThemeUtilities.getTheme(context);
        
        if (!timeStamp.isRendered()) {
            return;
        }        
        
        String textStyle = theme.getStyleClass(ThemeStyles.TIMESTAMP_TEXT);
        
        StringBuffer sb = new StringBuffer(timeStamp.getClientId(context));
        int idlen = sb.length(); 
        
        writer.startElement("span", timeStamp); // NOI18N
        writer.writeAttribute("id", sb.toString(), "id"); // NOI18N
        writer.startElement("span", timeStamp); // NOI18N
        
        writer.writeAttribute("id", sb.append("_span1"), "id"); // NOI18N
        //Reset the length
        sb.setLength(idlen); 
        
        RenderingUtilities.renderStyleClass(context, writer, component, textStyle);
        addStringAttributes(context, component, writer, coreAttributes);
        
        String message = timeStamp.getText();        
        if (message == null) {
            // use the default "Last updated:" message
            message = theme.getMessage("TimeStamp.lastUpdate"); // NOI18N
        }
        
        writer.write(message);
        writer.endElement("span"); // NOI18N
        writer.write("&nbsp;"); // NOI18N
        writer.startElement("span", timeStamp); // NOI18N
        
        writer.writeAttribute("id", sb.append("_span2"), "id"); // NOI18N
        
        RenderingUtilities.renderStyleClass(context, writer, component, textStyle);
        addStringAttributes(context, component, writer, coreAttributes);
        
        Locale locale =                
            FacesContext.getCurrentInstance().getViewRoot().getLocale();        
        
        DateFormat dateFormat = DateFormat.getDateTimeInstance(
            Integer.parseInt(theme.getMessage("TimeStamp.dateStyle")), // NOI18N
                Integer.parseInt(theme.getMessage("TimeStamp.timeStyle")), locale); // NOI18N
        
        writer.write(dateFormat.format(new Date())); // NOI18N
        
        writer.endElement("span"); // NOI18N
        writer.endElement("span"); // NOI18N
    }
}
