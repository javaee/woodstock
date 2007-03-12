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
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Widget;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class renders StaticText component.
 */
@Renderer(@Renderer.Renders(
rendererType="com.sun.webui.jsf.widget.StaticText",
        componentFamily="com.sun.webui.jsf.StaticText"))
public class StaticTextRenderer extends RendererBase {

    /**
     * The set of pass-through attributes to be rendered.
     */
    private static final String attributes[] = {
        "onMouseDown",
        "onMouseOut",
        "onMouseOver",
        "onMouseUp",
        "onMouseMove",
        "style",
        "onClick",
        "onDblClick",
        "dir",
        "lang"                        
    };
    
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
        json.put(JavaScriptUtilities.getModuleName("widget.staticText"));
        return json;
    }
    
    /**
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {
        StaticText staticText = (StaticText) component;
                        
        String currentValue =
                ConversionUtilities.convertValueToString(component,
                staticText.getText());
        String message = null;        
        if (currentValue != null) {
            java.util.ArrayList parameterList = new ArrayList();
            
            // get UIParameter children...
            
            java.util.Iterator kids = component.getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                
                //PENDING(rogerk) ignore if child is not UIParameter?
                
                if (!(kid instanceof UIParameter)) {
                    continue;
                }
                
                parameterList.add(((UIParameter) kid).getValue());
            }
            
            // If at least one substitution parameter was specified,
            // use the string as a MessageFormat instance.
            
            if (parameterList.size() > 0) {
                message = MessageFormat.format
                        (currentValue, parameterList.toArray
                        (new Object[parameterList.size()]));
            } else {
                message = currentValue;
            }
                        
        }
        
        String templatePath = ((Widget) staticText).getHtmlTemplate(); // Get HTML template.
        JSONObject json = new JSONObject();
        json.put("value", message)
            .put("title", staticText.getToolTip())
            .put("escape", staticText.isEscape())
            .put("templatePath", (templatePath != null)
                ? templatePath
                : getTheme().getPathToTemplate(ThemeTemplates.STATICTEXT))
            .put("className", staticText.getStyleClass());
        
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
