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
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders DropDown components.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.DropDown",
    componentFamily="com.sun.webui.jsf.DropDown"))
public class DropDownRenderer extends ListRendererBase {
   
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
        
        DropDown dropDown = (DropDown)component;
        
        JSONArray json = new JSONArray();
        json.put(JavaScriptUtilities.getModuleName("widget.dropDown"));
        
        if (dropDown.isAjaxify()) {
            json.put(JavaScriptUtilities.getModuleName(
                    "widget.jsfx.dropDown"));
        }
        
        return json;
    }

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws JSONException, IOException{
        
        DropDown dropDown = (DropDown) component;
        String templatePath = dropDown.getHtmlTemplate(); 

        if(dropDown.isForgetValue()) { 
            // Forget about what was the value selected before.
            dropDown.setValue(null); 
        } 
        
        // Get the properties from the super class ListRendererBase
        JSONObject json = super.getProperties(context, (ListSelector) dropDown);
        
        // Render the element and attributes for this component
        json.put("templatePath", (templatePath != null)
            ? templatePath
            : getTheme().getPathToTemplate(ThemeTemplates.DROPDOWN));
        json.put("visible", dropDown.isVisible());
        json.put("submitForm", dropDown.isSubmitForm() );

        // Add core and pass-through attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);

        return json;
    }
    
    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType() {
        return JavaScriptUtilities.getNamespace("dropDown");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to get Theme objects.
    private Theme getTheme() {
        return ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
    }
}
