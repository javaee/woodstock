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

import com.sun.webui.jsf.component.Field;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.util.DesignMessageUtil;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;


/**
 * A delegating renderer for components based on {@link com.sun.webui.jsf.component.Field}.
 * This delegating renderer takes over when the component is read-only, since the
 * field renderers replace the field component with a proxy component when the
 * <code>readOnly</code> property is true, which makes the resulting HTML unselectable
 * on the designer. This delegating renderer also provides a shadow text value
 * when the component is read-only and there is no text value.
 *
 * @author gjmurphy
 */
public class FieldDesignTimeRenderer extends AbstractDesignTimeRenderer {
    
    /** Creates a new instance of FieldDesignTimeRenderer */
    public FieldDesignTimeRenderer(Renderer renderer) {
        super(renderer);
    }
    
    protected String getShadowText(FacesContext context, Field field) {
        return DesignMessageUtil.getMessage(FieldDesignTimeRenderer.class, "field.readOnly.value");
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      
        Field field = (Field) component;        
        if (field.isReadOnly()) {
            ResponseWriter writer = context.getResponseWriter();
            Object value = field.getText();
            writer.startElement("span", field); // NOI18N
            writer.writeAttribute("id", field.getId(), "id"); //NOI18N
            String style = field.getStyle();
            if (style != null && style.length() > 0)
                writer.writeAttribute("style", style, null); //NOI18N
            String styleClass = field.getStyleClass();
            StringBuffer styleClassBuffer = new StringBuffer();
            if (styleClass != null)
                styleClassBuffer.append(styleClass);
            UIComponent label = field.getLabelComponent(context, "");
            if (label != null) {
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                styleClassBuffer.setLength(0);
                RenderingUtilities.renderComponent(label, context);
                Theme theme = ThemeUtilities.getTheme(context);
                Icon icon = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
                icon.setId(component.getId().concat("_spacer")); //NOI18N
                icon.setHeight(1);
                icon.setWidth(10);
                RenderingUtilities.renderComponent(icon, context);
                writer.startElement("span", field); // NOI18N
                writer.writeAttribute("id", field.getId().concat("_readOnly"), "id"); //NOI18N
            }
            if (value == null) {
                if (styleClassBuffer.length() > 0)
                    styleClassBuffer.append(' ');
                styleClassBuffer.append(UNINITITIALIZED_STYLE_CLASS);
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                writer.writeText(getShadowText(context, field), null); // NOI18N
            } else {
                String stringValue = getTextValue(field, value);
                writer.writeAttribute("class", styleClassBuffer.toString(), null); // NOI18N
                writer.writeText(stringValue, null);
            }
            writer.endElement("span"); // NOI18N
        } else {
            super.encodeBegin(context, component);
        }
    }
    
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        Field field = (Field) component;
        if (!field.isReadOnly())
            super.encodeEnd(context, component);
    }
    
    protected String getTextValue(Field field, Object value) {
        String stringValue = ConversionUtilities.convertValueToString
            (field, value);
        return stringValue;
    }
    
}
