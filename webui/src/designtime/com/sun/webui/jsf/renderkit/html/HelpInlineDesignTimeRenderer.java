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

import com.sun.webui.jsf.component.HelpInline;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.renderkit.html.HelpInlineRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.HelpInline} that 
 * sets a default text property when the property is null.
 *
 * @author gjmurphy
 */
public class HelpInlineDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    boolean isTextDefaulted;
    
    public HelpInlineDesignTimeRenderer() {
        super(new HelpInlineRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component instanceof HelpInline && ((HelpInline)component).getText() == null) {
            HelpInline HelpInline = (HelpInline)component;
            HelpInline.setText(DesignMessageUtil.getMessage(HelpInlineDesignTimeRenderer.class, "helpInline.label"));
            HelpInline.setStyleClass(addStyleClass(HelpInline.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
            isTextDefaulted = true;
        }
        super.encodeBegin(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
        if (isTextDefaulted) {
            HelpInline HelpInline = (HelpInline)component;
            HelpInline.setText(null);
            HelpInline.setStyleClass(removeStyleClass(HelpInline.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
            isTextDefaulted = false;
        }
    }
    
}
