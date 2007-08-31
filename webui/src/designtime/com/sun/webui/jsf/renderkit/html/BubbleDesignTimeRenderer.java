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
import com.sun.webui.jsf.component.Bubble;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.renderkit.widget.BubbleRenderer;
import com.sun.webui.jsf.util.RenderingUtilities;
import java.util.Iterator;


/**
 *
 * Bubble DesignTime Renderer
 */
public class BubbleDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of BubbleDesignTimeRenderer */
    public BubbleDesignTimeRenderer() {
        super(new BubbleRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        String clientId = component.getClientId(context);
        Bubble bubble = (Bubble) component;        
        //default attributes for bubble        
        //component top level div class
        String bubbleDivClassName =  theme.getStyleClass(ThemeStyles.BUBBLE_DIV);
        //bubble header div class
        String bubbleHeaderClassName = theme.getStyleClass(ThemeStyles.BUBBLE_HEADER);
        //bubble title div class
        String bubbleTitleClassName = theme.getStyleClass(ThemeStyles.BUBBLE_TITLE);
        //bubble close button div class
        String bubbleBtnClassName = theme.getStyleClass(ThemeStyles.BUBBLE_CLOSEBTN);
        //bubble no close button div class
        String bubbleNoBtnClassName = theme.getStyleClass(ThemeStyles.BUBBLE_NOCLOSEBTN);
        //bubble content div class
        String bubbleContentClassName = theme.getStyleClass(ThemeStyles.BUBBLE_CONTENT);
                
        String hiddenStyleClass = theme.
            getStyleClass(ThemeStyles.HIDDEN);
        hiddenStyleClass = " " + hiddenStyleClass;                    
        StringBuffer sb = new StringBuffer();
                
        String dtClass = " rave-uninitialized-text"; //NOI18N        
        //top level div
        writer.startElement("div", bubble);  //NOI18N
        writer.writeAttribute("id", clientId, null);  //NOI18N
        
        if (bubble.isVisible()) {
            sb.append(bubbleDivClassName);
            sb.append(dtClass);
            writer.writeAttribute("class", sb.toString(), "styleClass");  //NOI18N
            sb.setLength(0);
        } else {
            sb.append(bubbleDivClassName);
            sb.append(dtClass);
            sb.append(hiddenStyleClass);
            writer.writeAttribute("class", sb.toString()
                   , "styleClass");  //NOI18N 
            sb.setLength(0);
        }   
        
        String style = (String) component.getAttributes().get("style"); //NOI18N    
        
        if (style != null) {
            writer.writeAttribute("style", style, null); //NOI18N
        }               
        
        //bubble header div
        writer.startElement("div", component);  //NOI18N                
        //title div
        writer.startElement("div", component);  //NOI18N
        writer.writeAttribute("class", bubbleTitleClassName, null);  //NOI18N
        if (bubble.getTitle() != null ) {
            writer.write(bubble.getTitle());
        } else { 
            writer.write(DesignMessageUtil.
                    getMessage(BubbleDesignTimeRenderer.class, "bubble.title"));
        }    
        writer.endElement("div");
        writer.startElement("div", component);  //NOI18N
        if (bubble.isCloseButton()) {
            writer.writeAttribute("class", bubbleBtnClassName , null);  //NOI18N
        } else {
            writer.writeAttribute("class", bubbleNoBtnClassName , null);  //NOI18N
        }
        writer.endElement("div");
        writer.endElement("div");// bubble header
   
        //bubble content node
        writer.startElement("div", component);  //NOI18N
        writer.writeAttribute("class", bubbleContentClassName, null);  //NOI18N
        Iterator kids = bubble.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            if (kid.isRendered()) {
                RenderingUtilities.renderComponent((UIComponent)kid, context);
            }
        }
        writer.endElement("div");      
        writer.endElement("div");// bubble div                        
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
