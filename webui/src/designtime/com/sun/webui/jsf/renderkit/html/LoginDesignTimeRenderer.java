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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
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
import com.sun.webui.jsf.component.Login;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.renderkit.widget.LoginRenderer;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.component.util.DesignMessageUtil;

public class LoginDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of LoginDesignTimeRenderer */
    public LoginDesignTimeRenderer() {
        super(new LoginRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        String clientId = component.getClientId(context);
        Login login = (Login) component;
        
        StringBuffer sb = new StringBuffer();
        
        String style = (String) login.getAttributes().get("style");
        if ((style != null) && (style.length() > 0)) {
            sb.append(style);                    
        }
           
        if (style == null || (!style.contains("width") && !style.contains("height"))) {
            sb.append(";width:100px; height:75px; "); // NOI18N
        }
        login.setStyle(sb.toString());
           
        style = (String) login.getAttributes().get("style"); //NOI18N
        String styleClass = (String) login.getAttributes().get("styleClass"); //NOI18N
        
        //top level div
        writer.startElement("div", login);  //NOI18N
        writer.writeAttribute("id", clientId, null);  //NOI18N
        String loginDivClassName = theme.getStyleClass(ThemeStyles.LOGIN_DIV);
        if (styleClass != null && styleClass.length() > 0) {
            
            writer.writeAttribute("class", styleClass, "class");
        } else {
            writer.writeAttribute("class", loginDivClassName, "class");
        }
        
        if (style != null && style.length() > 0) {
            writer.writeAttribute("style", style, "style");
        } else {
            writer.writeAttribute("style", "width:100px", "style");
        }
        writer.writeText("<" + 
            DesignMessageUtil.getMessage(StaticTextDesignTimeRenderer.class, 
                "login.label") + ">", null); //NOI18N
                
        writer.endElement("div");  // login end div
        
                    
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
