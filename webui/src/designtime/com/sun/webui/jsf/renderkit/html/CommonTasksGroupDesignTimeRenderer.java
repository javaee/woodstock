/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
