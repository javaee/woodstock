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

import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.renderkit.html.CommonTasksSectionRenderer;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
/**
 *
 * @author vm157347
 */
public class CommonTasksSectionDesignTimeRenderer extends AbstractDesignTimeRenderer{
    
    /**
     * Creates a new instance of CommonTasksSectionDesignTimeRenderer
     */
    public CommonTasksSectionDesignTimeRenderer() {
        super(new CommonTasksSectionRenderer());
    }
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        StringBuffer sb = new StringBuffer();
        int tmp = -1;
        if (component instanceof CommonTasksSection) {
           String style = (String) component.getAttributes().get("style");
            if ((style != null) && (style.length() > 0)) {
                    sb.append(style);                                    
            }
            
           if (style == null) {
                sb.append(" width:800px"); // NOI18N
           }else if ((style != null) && !style.contains("width")) {
                sb.append("; width:800px"); // NOI18N
           }
      
  
            ((CommonTasksSection)component).setStyle(sb.toString());
            super.encodeBegin(context, component);
        }
         
    }
}
