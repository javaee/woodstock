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
 * AccordionDesignTimeRenderer.java
 *
 * Created on August 14, 2007, 4:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.renderkit.html;

/**
 *
 * @author Deep Bhattacharjee
 */
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.sun.webui.jsf.component.Accordion;
import com.sun.webui.jsf.component.AccordionTab;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.renderkit.widget.AccordionRenderer;
import com.sun.webui.jsf.util.RenderingUtilities;

public class AccordionDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of AccordionDesignTimeRenderer */
    public AccordionDesignTimeRenderer() {
        super(new AccordionRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        String clientId = component.getClientId(context);
        Accordion accordion = (Accordion) component;
        
        StringBuffer sb = new StringBuffer();
        
        String style = (String) accordion.getAttributes().get("style");
        if ((style != null) && (style.length() > 0)) {
            sb.append(style);                    
        }
           
        if (style == null || (!style.contains("width") && !style.contains("height"))) {
            sb.append(";width:100px; height:200px; "); // NOI18N
        }
        accordion.setStyle(sb.toString());
           
        //default attributes for accordion
        
        ImageComponent refreshIcon = ThemeUtilities.getIcon(theme,
	    ThemeImages.ACCORDION_REFRESH);
	
        ImageComponent collapseAllIcon = ThemeUtilities.getIcon(theme,
	    ThemeImages.ACCORDION_COLLAPSE_ALL);
	
        ImageComponent expandAllIcon = ThemeUtilities.getIcon(theme,
	    ThemeImages.ACCORDION_EXPAND_ALL);
	                
        boolean multipleSelect = accordion.isMultipleSelect();
        boolean isRefreshButton = accordion.isRefreshIcon();
        boolean isToggleControls = accordion.isToggleControls();
        
        style = (String) accordion.getAttributes().get("style"); //NOI18N
        String styleClass = (String) accordion.getAttributes().get("styleClass"); //NOI18N
        
        //component top level div class
        String accDivClassName = theme.getStyleClass(ThemeStyles.ACCORDION_DIV);
        String accHdrClassName = theme.getStyleClass(ThemeStyles.ACCORDION_HDR);
        String accHdrRefreshClassName = theme.getStyleClass(ThemeStyles.ACCORDION_HDR_REFRESH);
        String accHdrOpenAllClassName = theme.getStyleClass(ThemeStyles.ACCORDION_HDR_OPENALL);
        String accHdrCloseAllClassName = theme.getStyleClass(ThemeStyles.ACCORDION_HDR_CLOSEALL);
        String accHdrDividerClassName = theme.getStyleClass(ThemeStyles.ACCORDION_HDR_DIVIDER);
        String accHdrCollapsedClassName = theme.getStyleClass(ThemeStyles.ACCORDION_COLLAPSED);
     
        if (!accordion.isVisible()) {
            return;
        }
        
        //top level div
        writer.startElement("div", accordion);  //NOI18N
        writer.writeAttribute("id", clientId, null);  //NOI18N
        
        if (styleClass != null && styleClass.length() > 0) {
            
            writer.writeAttribute("class", styleClass, "class");
        } else {
            writer.writeAttribute("class", accDivClassName, "class");
        }
        
        if (style != null && style.length() > 0) {
            writer.writeAttribute("style", style, "style");
        } else {
            writer.writeAttribute("style", "width:100px", "style");
        }
        
        //Accordion's header
        writer.startElement("div", accordion);  //NOI18N
        writer.writeAttribute("id", clientId + "_accHeader", null);  //NOI18N
        writer.writeAttribute("class", accHdrClassName, null);  //NOI18N
        
            
        if (multipleSelect) {
            //Accordion's expandAll Node
            writer.startElement("div", accordion);  //NOI18N
            writer.writeAttribute("id", clientId + "_expandAllNode", null);  //NOI18N
            writer.writeAttribute("class", accHdrOpenAllClassName, null);  //NOI18N

            writer.startElement("span", accordion);  //NOI18N
            writer.writeAttribute("id", clientId + "_expandAllNodeImage", null);  //NOI18N
            RenderingUtilities.renderComponent(expandAllIcon, context);
            writer.endElement("span");  // 
            writer.endElement("div");  // 
            
            //Accordion's collapseAll Node
            writer.startElement("div", accordion);  //NOI18N
            writer.writeAttribute("id", clientId + "_collapseAllNode", null);  //NOI18N
            writer.writeAttribute("class", accHdrCloseAllClassName, null);  //NOI18N

            writer.startElement("span", accordion);  //NOI18N
            writer.writeAttribute("id", clientId + "_collapseAllNodeImage", null);  //NOI18N
            RenderingUtilities.renderComponent(collapseAllIcon, context);
            writer.endElement("span");  // 
            writer.endElement("div");  // 
            
            //Accordion's divider Node
            writer.startElement("div", accordion);  //NOI18N
            writer.writeAttribute("id", clientId + "_dividerNode", null);  //NOI18N
            writer.writeAttribute("class", accHdrDividerClassName, null);  //NOI18N
            writer.endElement("div");  //  end div
            
        }
        if (isRefreshButton) {
            //Accordion's refresh Node
            writer.startElement("div", accordion);  //NOI18N
            writer.writeAttribute("id", clientId + "_RefreshNode", null);  //NOI18N
            writer.writeAttribute("class", accHdrRefreshClassName, null);  //NOI18N

            writer.startElement("span", accordion);  //NOI18N
            writer.writeAttribute("id", clientId + "_refreshNodeImage", null);  //NOI18N
            RenderingUtilities.renderComponent(refreshIcon, context);
            writer.endElement("span");  // 
            writer.endElement("div");  //
        }
        writer.endElement("div");  // accordion header end div
        
        
        for (UIComponent kid : accordion.getChildren()) {
            if (kid instanceof AccordionTab) {
                AccordionTab accTab = (AccordionTab)kid;
                RenderingUtilities.renderComponent(accTab, context);
            }
        }
        writer.endElement("div");  // accordion end div
                    
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) 
        throws IOException {
        // don't do anything
    }
    
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        //don't do anything.
    }
            
}
