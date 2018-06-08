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

package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;

import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.Widget;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Table components.
 */
@Renderer(@Renderer.Renders(rendererType = "com.sun.webui.jsf.widget.Button",
componentFamily = "com.sun.webui.jsf.Button"))
public class ButtonRenderer extends RendererBase {

    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "alt",
        "align",
        "dir",
        "lang",
        "onBlur",
        "onClick",
        "onDblClick",
        "onFocus",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "style",
        "tabIndex"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Determine if this was the component that submitted the form.
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be decoded
     *
     * @exception NullPointerException if <code>context</code> or
     *  <code>component</code> is <code>null</code>
     */
    @Override
    public void decode(FacesContext context, UIComponent component) {
        // Enforce NPE requirements in the Javadocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        Button button = (Button) component;

        // Do not process disabled or reset components.
        if (button.isReset()) {
            return;
        }

        // Was our command the one that caused this submission?
        String clientId = button.getClientId(context);
        Map map = context.getExternalContext().getRequestParameterMap();

        if (map.containsKey(clientId) ||
                (map.containsKey(clientId + ".x") && map.containsKey(clientId + ".y"))) {
            button.queueEvent(new ActionEvent(button));
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Get the Dojo modules required to instantiate the widget.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONArray getModules(FacesContext context, UIComponent component)
            throws JSONException {
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.button"));
        return json;
    }

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws JSONException {
        Button button = (Button) component;
        String templatePath = ((Widget) button).getHtmlTemplate(); // Get HTML template.

        JSONObject json = new JSONObject();
        json.put("className", button.getStyleClass()).put("disabled", button.isDisabled()).put("mini", button.isMini()).put("name", button.getClientId(context)).put("primary", button.isPrimary()).put("templatePath", (templatePath != null)
                ? templatePath
                : getTheme().getPathToTemplate(ThemeTemplates.BUTTON)).put("title", button.getToolTip()).put("type", button.isReset() ? "reset" : "submit").put("visible", button.isVisible());

        // Get the textual label of the button.
        String text = ConversionUtilities.convertValueToString(button,
                button.getText());

        // Pad the text, if needed.
        if (text != null && text.trim().length() > 0) {
            // Note: This code appears in the UI guidelines, but it may have been
            // for Netscape 4.x. We may be able to do this with styles instead.
            if (!button.isNoTextPadding()) {
                if (text.trim().length() <= 3) {
                    text = "  " + text + "  "; //NOI18N
                } else if (text.trim().length() == 4) {
                    text = " " + text + " "; //NOI18N
                }
            }
            json.put("contents", text); // This is button label.
        }

        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);

        return json;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}
