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
 * AccordionTabDesignTimeRenderer.java
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
import com.sun.webui.jsf.component.AccordionTab;
import com.sun.webui.jsf.component.Accordion;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.renderkit.widget.AccordionTabRenderer;
import com.sun.webui.jsf.util.RenderingUtilities;

public class AccordionTabDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of AccordionTabDesignTimeRenderer */
    public AccordionTabDesignTimeRenderer() {
        super(new AccordionTabRenderer());
    }
    
    /*
     * Render the accordionTab by genrating the HTML markup corresponding
     * to a simple accordionTab.
     */
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        String clientId = component.getClientId(context);
        AccordionTab accordionTab = (AccordionTab) component;
        
        //default attributes for accordion
        
        String tabTitle = accordionTab.getTitle();
        String style = (String) accordionTab.getAttributes().get("style"); //NOI18N
        String styleClass = (String) accordionTab.getAttributes().get("styleClass"); //NOI18N
        
        //component top level div class
        String tabContentClass = theme.getStyleClass("ACCORDION_TABCONTENT");
        String tabTitleClass = theme.getStyleClass("ACCORDION_TABTITLE");
        String tabExpandedClass = theme.getStyleClass("ACCORDION_TABEXPANDED");
        String tabCollapsedClass = theme.getStyleClass("ACCORDION_TABCOLLAPSED");
        String rightTurnerClass = theme.getStyleClass("ACCORDION_RIGHTTURNER");
        String downTurnerClass = theme.getStyleClass("ACCORDION_DOWNTURNER");
        
        if (!accordionTab.isVisible()) {
            return;
        }
        
        //top level div for each tab
        writer.startElement("div", accordionTab);  //NOI18N
        writer.writeAttribute("id", clientId, null);  //NOI18N
        
        
        if (styleClass != null && styleClass.length() > 0) {
            
            writer.writeAttribute("class", styleClass, "class");
        } 
        
        if (style != null && style.length() > 0) {
            writer.writeAttribute("style", style, "style");
        } 
        
        // expanded accordionTab
        if (accordionTab.isSelected()) {
            writer.startElement("div", accordionTab);  //NOI18N
            writer.writeAttribute("id", clientId + "_titleContainer", null);  //NOI18N
            writer.writeAttribute("class", tabExpandedClass, null);  //NOI18N
            
            
            // add the turner
            writer.startElement("div", accordionTab);  //NOI18N
            writer.writeAttribute("id", clientId + "_tabTurner", null);  //NOI18N
            writer.writeAttribute("class", downTurnerClass, null);  //NOI18N
            writer.endElement("div");
            
        } else {
            writer.startElement("div", accordionTab);  //NOI18N
            writer.writeAttribute("id", clientId + "_titleContainer", null);  //NOI18N
            writer.writeAttribute("class", tabCollapsedClass, null);  //NOI18N
                        
            // add the turner
            writer.startElement("div", accordionTab);  //NOI18N
            writer.writeAttribute("id", clientId + "_tabTurner", null);  //NOI18N
            writer.writeAttribute("class", rightTurnerClass, null);  //NOI18N
            writer.endElement("div"); 
        }
        
        writer.startElement("div", accordionTab);  //NOI18N
        writer.writeAttribute("id", clientId + "_tabTitle", null);  //NOI18N
        writer.writeAttribute("class", tabTitleClass, null);  //NOI18N
        writer.startElement("a", accordionTab);
        char[] chars = tabTitle.toCharArray();
        writer.writeText(chars, 0, chars.length);
        
        writer.endElement("a");
        writer.endElement("div");  // title end div
        writer.endElement("div");  // title container end div
        
        if (accordionTab.isSelected()) {
            writer.startElement("div", accordionTab);  //NOI18N
            writer.writeAttribute("id", clientId + "_tabContent", null);  //NOI18N
            writer.writeAttribute("class", tabContentClass, null);  //NOI18N
            writer.writeAttribute("style", "display:block", "style");
            for (UIComponent kid : accordionTab.getChildren()) {
                RenderingUtilities.renderComponent(kid, context);
            }
            writer.endElement("div");  // content end div
        }
        
        writer.endElement("div");  // accordiontab end div
                    
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

