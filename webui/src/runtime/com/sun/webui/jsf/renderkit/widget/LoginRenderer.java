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
package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Login;
import com.sun.webui.jsf.model.login.LoginConstants;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * <p>Renderer for a {@link Login} component.</p>
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Login",
    componentFamily="com.sun.webui.jsf.Login"))
public class LoginRenderer extends RendererBase {

   /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String stringAttributes[] = {
        "style"};

    private static final String intAttributes[] = {        
        "tabIndex"
    };
    
    /** Creates a new instance of LoginRenderer */
    public LoginRenderer() {
        super();
    }
  
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @return The type of widget represented by this component. The
     *   "accordion" in this case.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
        return "login";
    }
    
    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        Login login = (Login) component;
        Theme theme = getTheme();
        String templatePath = login.getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("loginDivClassName",
                theme.getStyleClass(ThemeStyles.LOGIN_DIV))
            .put("loginState", LoginConstants.LOGINSTATE.INIT)
            .put("autoStart", login.isAutoStart())
            .put("className", login.getStyleClass())
            .put("redirectURL", login.getRedirectURL());
        
        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, login, json);
        JSONUtilities.addIntegerProperties(intAttributes, login, json);
        return json;
    }
     
}
