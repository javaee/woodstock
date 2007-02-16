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

import com.sun.webui.jsf.component.Alert;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.renderkit.html.AlertRenderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * A delegating renderer for {@link com.sun.webui.jsf.component.Alert} that 
 * sets a default summary property when the property is null.
 *
 * @author gjmurphy
 */
public class AlertDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    boolean isTextDefaulted;
    
    public AlertDesignTimeRenderer() {
        super(new AlertRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component instanceof Alert) {
            Alert alert = (Alert)component;
            if (alert.getSummary() == null || alert.getSummary().length() == 0) {
                alert.setSummary(DesignMessageUtil.getMessage(AlertDesignTimeRenderer.class, "alert.summary"));
                alert.setStyleClass(addStyleClass(alert.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
                isTextDefaulted = true;
            }
        }
        super.encodeBegin(context, component);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        super.encodeEnd(context, component);
        if (component instanceof Alert && isTextDefaulted) {
            Alert alert = (Alert)component;
            alert.setSummary(null);
            alert.setStyleClass(removeStyleClass(alert.getStyleClass(), UNINITITIALIZED_STYLE_CLASS));
            isTextDefaulted = false;
        }
    }
    
}
