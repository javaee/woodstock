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

import com.sun.webui.jsf.component.ListSelector;
import com.sun.webui.jsf.component.Selector;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.util.ConversionUtilities;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

/**
 * A delegating renderer for components based on {@link com.sun.webui.jsf.component.Selector}.
 *
 * <p>If the <code>items</code> property of the component is value-bound, and the
 * value returned by the binding expression is not null, and is "empty", it is an
 * indication that the user has bound the component to a bean property which InSync
 * cannot regenerate in full. In this case, the component binding is temporarily
 * set to point to dummy data, so as to give the user a visual cue that the component's
 * data is indeed bound.
 *
 * <p>This renderer also takes over when the component is read-only, since the
 * selector renderers replace the selector component with a proxy component when the
 * <code>readOnly</code> property is true, which makes the resulting HTML unselectable
 * on the designer. This delegating renderer also provides a shadow selected value
 * when the component is read-only and there is no selected value.
 *
 * @author gjmurphy
 */
public abstract class SelectorDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    final static String LABEL_FACET = "label"; //NOI18N
    
    /** Creates a new instance of SelectorDesignTimeRenderer */
    public SelectorDesignTimeRenderer(Renderer renderer) {
        super(renderer);
    }
    
    private boolean isDummyValue;
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        Selector selector = (Selector) component;
        if (selector.isReadOnly() && component instanceof ListSelector) {
            ResponseWriter writer = context.getResponseWriter();
            Object selectedValue = selector.getSelected();
            writer.startElement("span", selector); // NOI18N
            writer.writeAttribute("id", selector.getId(), "id"); //NOI18N
            String style = selector.getStyle();
            if (style != null && style.length() > 0)
                writer.writeAttribute("style", style, null); //NOI18N
            String styleClass = selector.getStyleClass();
            StringBuffer styleClassBuffer = new StringBuffer();
            if (styleClass != null)
                styleClassBuffer.append(styleClass);
            UIComponent label = ((ListSelector) selector).getLabelComponent();
            if (label != null) {
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                styleClassBuffer.setLength(0);
                RenderingUtilities.renderComponent(label, context);
                if(((ListSelector) selector).isLabelOnTop()) {
                    writer.startElement("br", selector); //NOI18N
                    writer.endElement("br");         //NOI18N
                }
                writer.startElement("span", selector); // NOI18N
                writer.writeAttribute("id", selector.getId().concat("_readOnly"), "id"); //NOI18N
            }
            if (selectedValue == null) {
                if (styleClassBuffer.length() > 0)
                    styleClassBuffer.append(' ');
                styleClassBuffer.append(UNINITITIALIZED_STYLE_CLASS);
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                writer.writeText(DesignMessageUtil.getMessage(SelectorDesignTimeRenderer.class,
                        "selector.readOnly.selectedValue"), null); // NOI18N
            } else {
                if (selectedValue instanceof Object[]) {
                    StringBuffer sb = new StringBuffer();
                    Object[] selectedValues = (Object[]) selectedValue;
                    for (int i = 0; i < selectedValues.length; i++) {
                        if (i > 0)
                            sb.append(", ");
                        sb.append(ConversionUtilities.convertValueToString(selector, selectedValues[i]));
                    }
                    writer.writeText(ConversionUtilities.convertValueToString(selector, sb.toString()), null);
                } else {
                    writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                    writer.writeText(ConversionUtilities.convertValueToString(selector, selectedValue), null);
                }
            }
            writer.endElement("span"); // NOI18N
        }
        // Add dummy options if bound value cannot be generated
        else {
            ValueBinding itemsBinding = selector.getValueBinding("items");
            if (itemsBinding != null) {
                // Fake options
                Object object = itemsBinding.getValue(context);
                if (object instanceof Option[]) {
                    Option[] itemsValue = (Option[]) object;
                    if (itemsValue != null && itemsValue.length == 0) {
                        isDummyValue = true;
                        selector.setItems(getDummyOptions());
                    }
                } else if (object == null) {
                    isDummyValue = true;
                    selector.setItems(getDummyOptions());
                }
            }
            
            if (component instanceof ListSelector) {
                // Give a place holder for the label in the designer
                ValueBinding labelBinding = selector.getValueBinding("label");
                if (labelBinding != null) {
                    selector.setLabel("abc");
                }
            }
            super.encodeBegin(context, component);
        }
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        Selector selector = (Selector) component;
        if (!(selector.isReadOnly() && component instanceof ListSelector)) {
            super.encodeEnd(context, component);
        }
        if (isDummyValue) {
            selector.setItems(null);
            isDummyValue = false;
        }
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
