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

import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.Menu;
import com.sun.webui.jsf.renderkit.widget.MenuRenderer;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.OptionGroup;
import com.sun.webui.jsf.renderkit.widget.MenuRenderer;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.theme.Theme;
import java.io.IOException;

import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *The renderer class used for designtime rendering of the menu component. 
 * <p>If the <code>items</code> property of the component is value-bound, and the
 * value returned by the binding expression is not null, and is "empty", it is an
 * indication that the user has bound the component to a bean property which InSync
 * cannot regenerate in full. In this case, the component binding is temporarily
 * set to point to dummy data, so as to give the user a visual cue that the component's
 * data is indeed bound.
 */
public class MenuDesignTimeRenderer extends AbstractDesignTimeRenderer{
    
    private boolean isDummyValue;
    
    public MenuDesignTimeRenderer() {
        super(new MenuRenderer());
    }

    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        
        if (!(component instanceof Menu)) {
            throw new IllegalArgumentException(
                "MenuRenderer can only render Menu components.");
        }
        
        Menu menu = (Menu)component;
        
        if (!menu.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        String clientId = component.getClientId(context);
        
        // Start the outer div for the mnu
        writer.startElement("div", component); 
        writer.writeAttribute("id",clientId, "id");
        
        String styles = RenderingUtilities.getStyleClasses(context, menu, 
                theme.getStyleClass(ThemeStyles.MENU_OUTER_DIV));
        writer.writeAttribute("class",styles,
                "class");  
        styles = menu.getStyle();
        if (styles != null || styles.length() > 0) {
            writer.writeAttribute("style", styles, "styles");
        }
        
        //Start rendering the div for the shadow container
        writer.startElement("div", component);
        writer.writeAttribute("class", 
            theme.getStyleClass(ThemeStyles.MENU_SHADOW_CONTAINER), "class");
        
        // Start rendering the div for the menu
        writer.startElement("div", component);
        writer.writeAttribute("class",
            theme.getStyleClass(ThemeStyles.MENU), "class");     
        
        ValueBinding itemsBinding = menu.getValueBinding("items");
        if (itemsBinding != null) {
            Object object = itemsBinding.getValue(context);
            if (object instanceof Option[]) {
                Option[] itemsValue = (Option[]) object;
                if (itemsValue != null && itemsValue.length == 0) {
                    isDummyValue = true;
                    menu.setItems(getDummyOptions());
                }
            } else if (object == null) {
                isDummyValue = true;
                menu.setItems(getDummyOptions());
            }
        }             
   }

    public void encodeChildren(FacesContext context, UIComponent component) 
        throws IOException {
        // don't do anything
    }
    
    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        
        // Start rendering the menu .
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        
        if (!(component instanceof Menu)) {
            throw new IllegalArgumentException(
                "MenuRenderer can only render Menu components.");
        }
        
        Menu menu = (Menu)component;
        
        if (!menu.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();        
        Option[] options = menu.getOptionsArray();

        renderOptions(options, component, context, writer);

        writer.endElement("div"); // div for menu.
        writer.endElement("div"); // div for shadow menu
        writer.endElement("div"); // outermost div
        
        if (isDummyValue) {
            menu.setItems(null);
            isDummyValue = false;
        }        
    }

    protected void renderOptions(Option[] options, UIComponent component, 
        FacesContext context, ResponseWriter writer) throws IOException {
        
        String id = component.getClientId(context);
        Theme theme = ThemeUtilities.getTheme(context);
        Option option = null;
        StringBuffer elementId = new StringBuffer();
        writer.startElement("ul", component);
        writer.writeAttribute("class",
            theme.getStyleClass(ThemeStyles.MENU_CONTAINER), "class");
        
        for (int i=0; i<options.length; i++) {
            option = options[i];
            writer.startElement("li", component);
            elementId.setLength(0);
            elementId.append("_")
                     .append(option.getValue().toString())
                     .append("_container");
            writer.writeAttribute("id",elementId.toString() , "id"); 
          
            writer.startElement("div", component);
            writer.writeAttribute("class",
                    theme.getStyleClass(ThemeStyles.MENU_ITEM_LABEL), "class");
            
            elementId.setLength(0);
            elementId.append(id)
                    .append("_")
                    .append(option.getValue().toString());
            writer.writeAttribute("id", elementId.toString(), "id");
            writer.writeText(option.getLabel(), null);
            writer.endElement("div");
            writer.endElement("li");          
            writer.startElement("br", component);
            writer.endElement("br");
            if (option instanceof OptionGroup) {
                renderOptions (((OptionGroup)option).getOptions(), component, 
                    context, writer);
            }
        }
        writer.endElement("ul"); // end menu.
    }
    
    static Option[] dummyOptions;
    
    static Option[] getDummyOptions() {
        if (dummyOptions == null) {
            Object dummyValue = getDummyData(String.class);
            Option dummyOption = new Option(dummyValue, dummyValue.toString());
            dummyOptions = new Option[] {
                dummyOption, dummyOption, dummyOption
            };
        }
        return dummyOptions;  
    }
}
