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

import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTask;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.renderkit.html.CommonTasksGroupRenderer;
import java.io.IOException;
import javax.faces.context.ResponseWriter;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
/**
 *
 * @author vm157347
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.CommonTasksGroup"))
public class CommonTasksGroupDesignTimeRenderer extends AbstractDesignTimeRenderer{
    
    protected static String STYLE_CLASS_PROP = "styleClass"; //NOI18N
    /** Creates a new instance of CommonTasksGroupDesignTimeRenderer */
    public CommonTasksGroupDesignTimeRenderer() {
        super(new CommonTasksGroupRenderer());
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        StringBuffer sb = new StringBuffer();
        
        if (component instanceof CommonTasksGroup) {
           String style = (String) component.getAttributes().get("style");
            if ((style != null) && (style.length() > 0)) {
                sb.append(style);                    
            }
           
           if (style == null || (!style.contains("width") && !style.contains("height"))) {
                sb.append(";width:300px; height:200px; "); // NOI18N
           }
           
           
            
           ((CommonTasksGroup)component).setStyle(sb.toString());
           sb = new StringBuffer();
           
           String styleClass = (String) component.getAttributes().get("styleClass");
                if ((styleClass != null) && (styleClass.length() > 0)) {
                    sb.append(styleClass);
                    sb.append(" ");
                }
            sb.append(BORDER_STYLE_CLASS);         
            ((CommonTasksGroup)component).setStyleClass(sb.toString());
            super.encodeBegin(context, component);
        }
         
    }
    
}
