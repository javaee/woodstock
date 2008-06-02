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
import com.sun.webui.jsf.component.ThemeLinks;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.beans.Beans;
import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renderer for a {@link Theme} component.</p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.ThemeLinks"))
public class ThemeLinksRenderer extends javax.faces.render.Renderer {
    public void encodeEnd(FacesContext context, UIComponent component) 
            throws IOException {
        return; 
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (!(component instanceof ThemeLinks)) {
            Object[] params = { component.toString(),
                this.getClass().getName(),
                ThemeLinks.class.getName() 
            };
            String message = MessageUtil.getMessage
                ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                "Renderer.component", params);              //NOI18N
            throw new FacesException(message);
        }
        
        ThemeLinks themeLinks = (ThemeLinks) component;
        ResponseWriter writer = context.getResponseWriter();

        // Get global flags.
        JavaScriptUtilities.setDebug(themeLinks.isDebug());
        JavaScriptUtilities.setJsfx(themeLinks.isJsfx());
        JavaScriptUtilities.setParseOnLoad(themeLinks.isParseOnLoad());
        JavaScriptUtilities.setStyleSheet(themeLinks.isStyleSheet());
        JavaScriptUtilities.setWebuiAll(themeLinks.isWebuiAll());
        JavaScriptUtilities.setWebuiAjax(themeLinks.isWebuiAjax());

        // Master stylesheet link.
        Theme theme = ThemeUtilities.getTheme(context);
        if (themeLinks.isStyleSheet() && Beans.isDesignTime()) {
            RenderingUtilities.renderStyleSheetLink(themeLinks, theme, context, writer);
            JavaScriptUtilities.setStyleSheet(false);
        }

        // Render bootstrap.
        if (themeLinks.isJavaScript()) {
            try {
                JavaScriptUtilities.renderBootstrap(component, writer);
                JavaScriptUtilities.renderOnLoad(component, writer, themeLinks.getOnLoad());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getRendersChildren() {
        return true; 
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        return;
    }
}
