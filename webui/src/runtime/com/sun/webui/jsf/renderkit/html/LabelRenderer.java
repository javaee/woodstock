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
  * $Id: LabelRenderer.java,v 1.1 2007-02-16 01:39:45 bob_yennaco Exp $
  */

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.Label;
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
import javax.faces.component.EditableValueHolder;
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
     * @param component <code>EditableValueHolder</code> component whose
     *  submitted value is to be stored
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
      
        if (LogUtil.fineEnabled(LabelRenderer.class)) {
            LogUtil.fine(LabelRenderer.class, "Label.renderAttributes", // NOI18N
		 new Object[] { label.getId(), label.getFor() });
        }
                
        EditableValueHolder comp = label.getLabeledComponent();
        boolean requiredFlag = label.isRequiredIndicator();
        boolean errorFlag = false; 
        
        if(!label.isHideIndicators() && comp != null) {
            Object o = ((UIComponent)comp).getAttributes().get("readOnly"); 
            if(o != null && o instanceof Boolean && o.equals(Boolean.TRUE)) { 
                requiredFlag = false; 
                errorFlag = false; 
            } 
           else {
                requiredFlag = comp.isRequired();
                errorFlag = !comp.isValid();
           }
        }
        
        Theme theme = ThemeUtilities.getTheme(context);  
        String styleClass = getThemeStyleClass(label, theme, errorFlag);    
        ResponseWriter writer = context.getResponseWriter();
        
        String id = getLabeledElementId(context, label);
        startElement(context, label, styleClass, id, writer);
        if(errorFlag) { 
           writer.write("\n"); //NOI18N   
           RenderingUtilities.renderComponent
                    (label.getErrorIcon(theme, context, false), context);
        }

        // Render the label text
        String value = formatLabelText(context, label);
        if(value != null) {
            writer.write("\n"); //NOI18N   
            writer.writeText(value, "text"); //NOI18N
            writer.writeText("\n", null); //NOI18N
        }
          
        // Render the required indicator flag	
        if(requiredFlag) { 
            writer.write("\n"); //NOI18N   
            RenderingUtilities.renderComponent
                    (label.getRequiredIcon(theme, context), context);
	}    

        // Note: the for attribute has been set, so we render the end of 
        // the label tag *before* we render the children. Otherwise we 
        // will inadvertently set the font for the child components.
        writer.endElement("label"); 
         
        Iterator children = label.getChildren().iterator(); 
        while(children.hasNext()) { 
            RenderingUtilities.renderComponent((UIComponent)children.next(),
                                               context); 
            writer.writeText("\n", null); //NOI18N
        }
        
        if (LogUtil.finestEnabled(LabelRenderer.class)) {
            LogUtil.finest(LabelRenderer.class, "Label.renderEnd"); // NOI18N
        }
    }
     
    private void startElement(FacesContext context, Label label,
	    String styleClass, String forId, ResponseWriter writer)
	    throws IOException { 
        
	// There used to be a call to Label.getElement() here to either
	// render a label or a span element depending on whether the
	// "for" attribute was set or not. That has changed so that
	// a label element be rendered in all cases.
        writer.startElement("label", label);
        writer.writeAttribute("id", label.getClientId(context), "id"); //NOI18N

	if (forId != null && forId.length() != 0) {
	    writer.writeAttribute("for", forId, "for");
	}
                
        RenderingUtilities.renderStyleClass(context, writer, label, styleClass);
        
        if (label.getStyle() != null) {
            writer.writeAttribute("style", label.getStyle(), "style"); //NOI18N
        }
        // Render the "toolTip" properties
        String toolTip = label.getToolTip();
        if (toolTip != null) {
            writer.writeAttribute("title", toolTip, "toolTip"); // NOI18N
        }
        writeEvents(label, writer);
    }
    
    /**
     * Returns an id suitable for the HTML label element's "for" attribute.
     * This implementation uses the following heuristic to obtain a
     * suitable id.
     * <p>
     * <ul>
     * <li>If <code>label.getFor</code> returns null, return the value
     * of <code>getLabeledChildId</code>.
     * </li>
     * <li>If <code>label.getFor</code> is not null, and the value
     * is not an absolute id (does not contain a
     * <code>NamingContainer.SEPARATOR_CHAR</code> try to resolve the
     * id to a component instance as if it were a sibling of the label. If
     * a component is found, and it is an instance of
     * <code>ComplexComponent</code> return the value of
     * <code>component.getLabeledElementId</code> else 
     * <code>component.getClientId</code>.
     * </li>
     * <li>If <code>label.getFor</code> returns an absolute id
     * i.e. contains a <code>NamingContainer.SEPARATOR_CHAR</code> then
     * return the value of <code>RenderingUtilities.getLabeledElementId</code>.
     * </li>
     * </ul>
     *
     * @param context The faces context
     * @param label The label component.
     *
     * @return A suitable id for the label element's "for" attribute.
     */     
    protected String getLabeledElementId(FacesContext context, Label label)
	    throws IOException{

        String id = label.getFor();
        if (id == null) {
            id = getLabeledChildId(context, label);
        } else if (id.indexOf(NamingContainer.SEPARATOR_CHAR) == -1) {
	    // The id may be a relative id.
	    // This does not prove conclusively that the id is a
	    // relative id. A relative id could contain a 
	    // NamingContainer.SEPARATOR_CHAR.
	    // Assume that the component's id is given as the value of
	    // for attribute. Get the label's parent and try to find the 
	    // client id of a sibling component.
	    //
	    UIComponent comp = label.getParent();
	    if (comp != null) {
		comp = comp.findComponent(id);
		if (comp != null) {
		    if (comp instanceof ComplexComponent) {
			id = ((ComplexComponent)comp).getLabeledElementId(context);
		    } else {
			id = comp.getClientId(context);
		    }
		}
	    }
	} else {
            id = RenderingUtilities.getLabeledElementId(context, id);
	}
	return id;
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
     * Returns the client id of the first child of the label component.
     * If there are no children, <code>null</code> is returned. If the
     * first child is a <code>ComplexComponent</code> return the value of
     * the <code>getLabeledElementId</code> instance method, else the
     * value of <code>getClientId</code>.
     * <p>
     * Note that, no recursive search is made to find a suitable
     * component to label, if the child has more than one child or 
     * the child is a grouping component buy not a
     * <code>ComplexComponent</code>. In such cases, it is advisable 
     * to explicitly set the "for" attribute for the label to the 
     * desired component contained by the grouping component or non
     * first child of the <code>Label</code>.
     * 
     * @param context The faces context instance
     * @param component The label component.
     */
    private String getLabeledChildId(FacesContext context,
	    UIComponent component) {
        
        if (component.getChildCount() == 0) {
            if (LogUtil.fineEnabled(LabelRenderer.class)) {
                LogUtil.fine(LabelRenderer.class,
		    "No children available");  //NOI18N
            }
	    return null;
        }
        UIComponent child = (UIComponent)component.getChildren().get(0);
        if (child instanceof ComplexComponent) {
            return ((ComplexComponent)child).getLabeledElementId(context);
        } else {
	    return child.getClientId(context);
	}
    }
}
