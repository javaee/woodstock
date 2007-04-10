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

 /*
  * $Id: LabelRenderer.java,v 1.2 2007-04-10 19:43:39 rratta Exp $
  */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.Property;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;


/**
 * <p>Renderer for a {@link Label} component.</p>
 */

@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Label"))
public class LabelRenderer extends javax.faces.render.Renderer {


    // ======================================================== Static Variables

    /**
     * <p>The set of additional String pass-through attributes to be rendered
     * if we actually create a <code>&lt;label&gt;</code> element.</p>
     */
    private static final String[] EVENT_NAMES = {
        "onClick",  "onMouseDown", "onMouseUp", // NOI18N
        "onMouseOver", "onMouseMove",  "onMouseOut" // NOI18N
    };
    
    public boolean getRendersChildren() {
         return true;
    }
    
    public void encodeChildren(FacesContext context, UIComponent component) 
        throws IOException { 
        return;
    }
   
    /**
     * <p>Render the appropriate element attributes, followed by the
     * label content, depending on whether the <code>for</code> property
     * is set or not.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>Label</code> component to render
     * @exception IOException if an input/output error occurs
     */
     public void encodeEnd(FacesContext context, UIComponent component) 
         throws IOException {
         
         if(!(component instanceof Label)) { 
            Object[] params = { component.toString(), 
                                this.getClass().getName(), 
                                Label.class.getName() }; 
            String message = MessageUtil.getMessage
                ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                 "Label.renderer", params);              //NOI18N
            throw new FacesException(message);  
        }  
     
        Label label = (Label)component;
        
        Theme theme = ThemeUtilities.getTheme(context);  
        ResponseWriter writer = context.getResponseWriter();

        writer.startElement(HTMLAttributes.LABEL, label);
        writer.writeAttribute(HTMLAttributes.ID, label.getClientId(context), 
                HTMLAttributes.ID);

        // For now, since we don't have independent attributes for
        // the required component and the validated component
        // use the component identified by the for attribute or 
        // use the same algorithm to find a component.
        //
        // Ideally this strategy would be the fall back case when the 
        // developer or subcomponent owner has not set the
        // requiredComponentId or validationComponentId explicitly.
        // Don't abstract this now, so we don't run
        // findComponent too many times.
        //
        // Since we are getting the component and not the id
        // we need to manually check for ComplexComponent which
        // would have been done in getLabeledElementId, to obtain
        // the attribute for the HTML "for" attribute.
        //
        // If the component is a ComplexComponent then we still 
        // may not have the component instance for the validation
        // and required check. We get the labeled element id and
        // then find that component instance.
        //
        // But that is not sufficient either since it is defined
        // to be an HTML element and not a component id.
        //
        // We need the "IndicatorComponent".
        //
        String forId = label.getFor();
        UIComponent labeledComponent = 
                label.getLabeledComponent(context, forId);
        if (labeledComponent instanceof ComplexComponent) {
            forId = ((ComplexComponent)
                labeledComponent).getLabeledElementId(context);

            // Since the value of "forId" is an HTML element id
            // and therefore possibly not a component id we
            // still need the component instance that is labeled.
            // In all cases but the Property component, the ComplexComponent
            // that was found, IS the labeled component instance.
            // The Property has to find the instance according to 
            // its rules. Unfortunately for Property this results
            // in two calls to UIComponent.findComponent, but at this
            // time it can't be helped.
            //
            if (labeledComponent instanceof Property) {
                labeledComponent = 
                    ((Property)labeledComponent).getIndicatorComponent(
                        context, label);
            }
        }
        if (forId != null && forId.length() != 0) {
            writer.writeAttribute(HTMLAttributes.FOR, forId, 
                HTMLAttributes.FOR);
        }
                
        if (label.getStyle() != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, label.getStyle(), 
                HTMLAttributes.STYLE);
        }

        // Render the "toolTip" properties
        //
        String toolTip = label.getToolTip();
        if (toolTip != null) {
            writer.writeAttribute(HTMLAttributes.TITLE, toolTip, 
                "toolTip"); // NOI18N
        }
        writeEvents(label, writer);

        boolean errorFlag = false;
        boolean isHideIndicators = label.isHideIndicators();

        // isRequiredIndicator was defined in case the labeledComponent could
        // not be determined, as was the case when the labeled component
        // appeared after the label in JSF 1.1. This property
        // may no longer be needed.
        // If the labeled component cannot be determined and this flag
        // is true and isHideIndicators is false 
        // then the indicator is shown.
        //
        boolean requiredFlag = label.isRequiredIndicator() &&
                !isHideIndicators;

        // If hideIndicators is true and the labeled component is 
        // readonly or null don't show any indicators 
        //
        if (!isHideIndicators && labeledComponent != null) {
            if (isProperty(labeledComponent, "readOnly", false)) { //NOI18N
                requiredFlag = false;
            } else {
                requiredFlag =
                    isProperty(labeledComponent, "required", false);//NOI18N
                errorFlag = 
                    !isProperty(labeledComponent, "valid", true); //NOI18N
           }
        }
        // If the error indicator must be shown an error selector is returned
        // else a selector just based on label level.
        //
        RenderingUtilities.renderStyleClass(context, writer, label, 
            getThemeStyleClass(label, theme, errorFlag));

        // The current format is <errorIndicator><labeltext><required>
        // This should be modifyable in a subclass but it is
        // not partitioned for that currently.
        //

        if (errorFlag) { 
           RenderingUtilities.renderComponent
                    (label.getErrorIcon(theme, context, false), context);
        }

        // Render the label text
        String value = formatLabelText(context, label);
        if (value != null) {
            writer.writeText(value, HTMLAttributes.TEXT);
        }
          
        // Render the required indicator flag   
        if (requiredFlag) { 
            RenderingUtilities.renderComponent
                    (label.getRequiredIcon(theme, context), context);
        }    
         
        // Note: the for attribute has been set, so we render the end of 
        // the label tag *before* we render the children. Otherwise we 
        // will inadvertently set the font for the child components.
        writer.endElement(HTMLAttributes.LABEL); 

        Iterator children = label.getChildren().iterator(); 
        while(children.hasNext()) { 
            RenderingUtilities.renderComponent((UIComponent)children.next(),
                                               context); 
        }
    }
     
  
    /**
     * <p>Return the text to be rendered for this label.  This will be either
     * the literal value of the <code>text</code> property, or the use of
     * that value as a <code>MessageFormat</code> string, using nested
     * <code>UIParameter</code> children as the source of replacement values.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param label <code>Label</code> we are rendering
     */
    private String formatLabelText(FacesContext context, Label label) {
        String text = ConversionUtilities.convertValueToString
                (label, label.getValue());
        text = text.concat(" ");
        if (label.getChildCount() == 0) {
            return text;
        }
        List list = new ArrayList();
        Iterator kids = label.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            if (kid instanceof UIParameter) {
                list.add(((UIParameter) kid).getValue());
            }
        }
        if (list.size() == 0) {
            return text;
        }
        return MessageFormat.format(text, list.toArray(new Object[list.size()]));
    }
    
    /**
     * Return a CSS selector from <code>theme</code> based on the
     * the <code>label.getLabelLevel</code> value or the 
     * state of <code>errorFlag</code>. The assumption is that the
     * appropriate label level is part of the selector when 
     * <code>errorFlag</code> is true.
     */
    private String getThemeStyleClass(Label label, 
                                      Theme theme,
                                      boolean errorFlag) { 

        String style = null;
        int level = label.getLabelLevel();
        
        if (errorFlag) {
            style = theme.getStyleClass(ThemeStyles.CONTENT_ERROR_LABEL_TEXT);
        } else if (level == 1) {
            style = theme.getStyleClass(ThemeStyles.LABEL_LEVEL_ONE_TEXT);
        } else if (level == 2) {
            style = theme.getStyleClass(ThemeStyles.LABEL_LEVEL_TWO_TEXT);
        } else if (level == 3) {
            style = theme.getStyleClass(ThemeStyles.LABEL_LEVEL_THREE_TEXT);
        }
        return style;
    }

    /**
     * Given the events in <code>EVENTS</code> iterate over the
     * <code>label</code> attributes and for each element of 
     * <code>EVENTS</code> that exists in the attribute map 
     * render the attribute and its value.
     */
    private void writeEvents(Label label, ResponseWriter writer)       
           throws IOException {
      
        Map attributes = label.getAttributes();
        Object value;
        int length = EVENT_NAMES.length;
        for(int i = 0; i < length; i++) {
            value = attributes.get(EVENT_NAMES[i]);
            if(value != null) {
                if(value instanceof String) {
                    writer.writeAttribute(EVENT_NAMES[i].toLowerCase(),
                            (String) value, EVENT_NAMES[i]);
                } 
                else {
                    writer.writeAttribute(EVENT_NAMES[i].toLowerCase(),
                            value.toString(), EVENT_NAMES[i]);
                }
            }
        }       
    }

    /**
     * Return <code>true</code> if <code>property</code> is a 
     * <code>Boolean</code> property and its value is <code>true</code>
     * else <code>false</code>. If the property does not exist 
     * return <code>doesNotExist</code>.
     */
    private boolean isProperty(UIComponent component, String property,
            boolean doesNotExist) {

        Object o = ((UIComponent)component).getAttributes().get(property);
        if (o == null) {
            return doesNotExist;
        } else {
            return o != null && o instanceof Boolean  ?
                ((Boolean)o).booleanValue() : false;
        }
    }
}
