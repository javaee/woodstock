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
import java.util.Locale;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.NotificationPhrase;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renders an instance of the NotificationPhrase component.</p>
 *
 * @author Sean Comerford
 * @deprecated
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.NotificationPhrase"))
public class NotificationPhraseRenderer extends HyperlinkRenderer {
    
    /** Creates a new instance of NotificationPhraseRenderer */
    public NotificationPhraseRenderer() {
    }
    
    /**
     * <p>Render the start of the NotificationPhrase component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        NotificationPhrase notificationPhrase = (NotificationPhrase) component;
        Theme theme = ThemeUtilities.getTheme(context);
        
        ImageComponent ic = notificationPhrase.getImageFacet();
        if (ic != null) {
            RenderingUtilities.renderComponent(ic, context);
        }
        
        writer.write("&nbsp;");
    }
    
    protected void finishRenderAttributes(FacesContext context,
            UIComponent component,
            ResponseWriter writer)
            throws IOException {
        NotificationPhrase notificationPhrase = (NotificationPhrase) component;
        Theme theme = ThemeUtilities.getTheme(context);       
                
	Object textObj = notificationPhrase.getText();
	// Note that ConversionUtilities.convertValueToString
	// returns "" for null value.
	//
        String text = 
	    ConversionUtilities.convertValueToString(component, textObj);
        String textStyle = theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT);
        
        writer.startElement("span", notificationPhrase);
        addCoreAttributes(context, notificationPhrase, writer, 
            theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT));
        
        writer.write(text); // NOI18N
        writer.endElement("span");
    }    
}
