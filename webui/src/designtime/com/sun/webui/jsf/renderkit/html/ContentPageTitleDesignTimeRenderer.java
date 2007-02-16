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

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.ContentPageTitle;

import com.sun.webui.jsf.component.util.DesignMessageUtil;

/**
 * DesignTime renderer class to render ContentPageTitle component at design time.
 * 
 */
public class ContentPageTitleDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of ContentPageTitleDesignTimeRenderer */
    public ContentPageTitleDesignTimeRenderer() {
        
        super(new ContentPageTitleRenderer());
    }
    
    /**
     * returns a display text for the contentPageTitle component.
     */
    protected String getShadowText(UIComponent component) {
        return DesignMessageUtil.getMessage(ContentPageTitleDesignTimeRenderer.class, "contentPage.label");
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        
        ContentPageTitle cpt = null;
        StringBuffer sb = new StringBuffer();
        
        if (component instanceof ContentPageTitle) {
            
            cpt = (ContentPageTitle) component;
            
            // If component doesn't have any children and there is no property set for component then
            // do the designtime rendering.
            if (component.getChildCount() == 0 &&
                    (cpt.getHelpText() == null || cpt.getHelpText().length() == 0) &&
                    (cpt.getTitle() == null || cpt.getTitle().length() == 0) ) {
                
                ResponseWriter writer = context.getResponseWriter();
                writer.startElement("div", component);
                String id = component.getId();
                writer.writeAttribute("id", id, "id"); //NOI18N
                
                // Calculate CSS height and width style settings
                
                
                String style = (String) component.getAttributes().get("style");
                if ((style != null) && (style.length() > 0)) {
                    sb.append(style);
                    
                }
                if (style == null || style.indexOf("width:") == -1) {
                    sb.append("width: 128px; "); // NOI18N
                    
                }
                if (style == null || style.indexOf("height:") == -1) {
                    
                    sb.append("height: 128px; "); // NOI18N
                }
                
                sb.append(style);
                sb.append("; -rave-layout: grid"); // NOI18N
                
                writer.writeAttribute("style", sb.toString(), null); //NOI18N
                sb.setLength(0);
                
                // Calculate CSS style classes
                String styleClass = (String) component.getAttributes().get("styleClass");
                if ((styleClass != null) && (styleClass.length() > 0)) {
                    sb.append(styleClass);
                    sb.append(" ");
                }
                
                sb.append(UNINITITIALIZED_STYLE_CLASS);
                sb.append(" ");
                sb.append(BORDER_STYLE_CLASS);
                writer.writeAttribute("class", sb.toString(), null); // NOI18
                
                writer.writeText(getShadowText(component), null);
                writer.endElement("div");
                
                return;
            }
        }
        super.encodeBegin(context, component);
                    
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        
        ContentPageTitle cpt = null;
        if (component instanceof ContentPageTitle) {
            cpt = (ContentPageTitle) component;
            if (component.getChildCount() == 0 &&
                    (cpt.getHelpText() == null || cpt.getHelpText().length() == 0) &&
                    (cpt.getTitle() == null || cpt.getTitle().length() == 0) ) {

                return;
            }
        }
        super.encodeEnd(context, component);
            
    }
}
