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
 * $Id: LabelRenderer.java,v 1.4 2007-04-19 03:39:27 danl Exp $
 */
package com.sun.webui.jsf.renderkit.widget;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.ComplexComponent;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.Property;
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
	if (!(component instanceof Label)) {
	    throw new IllegalArgumentException(
                "LabelRenderer can only render Label components.");
        }
        Label label = (Label) component;
	Theme theme = ThemeUtilities.getTheme(context);

	// Get HTML template.
        String templatePath = label.getHtmlTemplate();
        
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


        // isRequiredIndicator was defined in case the labeledComponent could
        // not be determined, as was the case when the labeled component
        // appeared after the label in JSF 1.1. This property
        // may no longer be needed.
        // If the labeled component cannot be determined and this flag
        // is true and isHideIndicators is false 
        // then the required indicator is shown.
        //
        boolean isHideIndicators = label.isHideIndicators();
        boolean requiredFlag = label.isRequiredIndicator() &&
                !isHideIndicators;

        boolean errorFlag = false;

	// Use the attributes so that we don't have to test
	// for EditableValueHolder.
	//
	// If hideIndicators is true, don't shoul any indicators.
        // If hideIndicators is false and the labeled component is 
        // readonly or null don't show any required indicator 
        //
        if (!isHideIndicators && labeledComponent != null) {
            if (isProperty(labeledComponent, "readOnly", false)) { //NOI18N
                requiredFlag = false;
            } else {
                requiredFlag =
                    isProperty(labeledComponent, "required", false);//NOI18N
		// We want error flag to be true if the valid 
		// attribute exists and is false.
		// Otherwise we want errorFlag to be false.
		//
                errorFlag = 
                    !isProperty(labeledComponent, "valid", true); //NOI18N
           }
        }
        
        JSONObject json = new JSONObject();
        json.put("level", label.getLabelLevel())
            .put("value", formatLabelText(context, label))
            .put("htmlFor", forId)
            .put("required", requiredFlag)
            .put("valid", !errorFlag)
            .put("templatePath", (templatePath != null)
                ? templatePath 
                : theme.getPathToTemplate(ThemeTemplates.LABEL))
            .put("className", label.getStyleClass());    
            
        // Append required image properties.
        WidgetUtilities.addProperties(json, "requiredImage",
            WidgetUtilities.renderComponent(context,
		label.getRequiredIcon(theme, context)));

        // Append error image properties.
        // passing valid=false so that it can output error icon 
	// (irrespective of valid attribute value). 
        WidgetUtilities.addProperties(json, "errorImage",
            WidgetUtilities.renderComponent(context, 
		label.getErrorIcon(theme, context,false)));

        // Add core and attribute properties.
        addAttributeProperties(attributes, component, json);
        setCoreProperties(context, component, json);
	setContents(context, component, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @return The type of widget represented by this component.
     */
    public String getWidgetType() {
        return JavaScriptUtilities.getNamespace("label");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Property methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Return the text to be rendered for this label.  This will be either
     * the literal value of the <code>text</code> property, or the use of
     * that value as a <code>MessageFormat</code> string, using nested
     * <code>UIParameter</code> children as the source of replacement values.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param label <code>Label</code> we are rendering
     */
    protected String formatLabelText(FacesContext context, Label label) {
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
     * Helper method to obtain label children.
     *
     * @param context FacesContext for the current request.
     * @param component Table2RowGroup to be rendered.
     * @param json JSONObject to assign properties to.
     */
    protected void setContents(FacesContext context,
	    UIComponent component, JSONObject json) 
	    throws IOException, JSONException {

        Iterator kids = component.getChildren().iterator();

	if (!kids.hasNext()) {
	    return;
	}

        JSONArray jArray = new JSONArray();
        json.put("contents", jArray);

        while (kids.hasNext()) {
            UIComponent child = (UIComponent) kids.next();
            if (child.isRendered()) {
                WidgetUtilities.addProperties(jArray,
                    WidgetUtilities.renderComponent(context, child));
            }
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
            return o instanceof Boolean ? ((Boolean)o).booleanValue() : false;
        }
    }
}
