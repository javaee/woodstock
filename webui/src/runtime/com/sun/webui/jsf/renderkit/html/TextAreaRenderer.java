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
  * TextAreaRenderer.java
  */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Renderer for TextAreaRenderer {@link TextArea} component.</p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.TextArea"))
public class TextAreaRenderer extends FieldRenderer {
   

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
          
        if(!(component instanceof TextArea)) { 
            Object[] params = { component.toString(), 
                                this.getClass().getName(), 
                                TextArea.class.getName() };
            String message = MessageUtil.getMessage
                ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                 "Renderer.component", params);              //NOI18N
            throw new FacesException(message);  
        }

        super.renderField(context, (TextArea)component, "textarea", getStyles(context));
    }
    
    String[] getStyles(FacesContext context) {
        Theme theme = ThemeUtilities.getTheme(context);
        String[] styles = new String[4];
        styles[0] = theme.getStyleClass(ThemeStyles.TEXT_AREA);
        styles[1] = theme.getStyleClass(ThemeStyles.TEXT_AREA_DISABLED);        
        styles[2] = theme.getStyleClass(ThemeStyles.HIDDEN);
        styles[3] = theme.getStyleClass(ThemeStyles.LIST_ALIGN);
        return styles;
    }
}
