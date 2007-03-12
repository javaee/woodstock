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
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.Widget;
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeTemplates;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class renders Label component.
 */
@Renderer(@Renderer.Renders(
    rendererType="com.sun.webui.jsf.widget.Label", 
    componentFamily="com.sun.webui.jsf.Label"))
public class LabelRenderer extends RendererBase {
    
     
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
        "onFocus",
        "onBlur",
        "onDblClick",
        "onKeyDown",
        "onKeyPress",
        "onKeyUp",
        "dir",
        "lang",
        "title",
        "accesskey"
        
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
        json.put(JavaScriptUtilities.getModuleName("widget.label"));
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
        Label label = (Label) component;
        String templatePath = ((Widget) label).getHtmlTemplate(); // Get HTML template.
        
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
        
        JSONObject json = new JSONObject();
        json.put("level", label.getLabelLevel())
            .put("value", formatLabelText(context, label))
            .put("htmlFor", getLabeledElementId(context, label))
            .put("required", requiredFlag)
            .put("valid", !errorFlag)
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : getTheme().getPathToTemplate(ThemeTemplates.LABEL))
            .put("className", label.getStyleClass());    
            
        // Append required image properties.
        WidgetUtilities.addProperties(json, "requiredImage",
            WidgetUtilities.renderComponent(context, label.getRequiredIcon(getTheme(),context)));

        // Append error image properties.
        // passing valid=false so that it can output error icon (irrespective of valid attribute value). 
        WidgetUtilities.addProperties(json, "errorImage",
            WidgetUtilities.renderComponent(context, label.getErrorIcon(getTheme(),context,false)));

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
