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
import com.sun.webui.jsf.util.WidgetUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.FacesMessageUtils;
import com.sun.webui.jsf.util.JSONUtilities;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private static final String stringAttributes[] = {
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
        "accessKey"
    };

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // RendererBase methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /** 
     * Helper method to obtain component properties.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     * 
     * @exception IOException if an input/output error occurs 
     * @exception JSONException if a key/value error occurs 
     */
    protected JSONObject getProperties(FacesContext context,
            UIComponent component) throws IOException, JSONException {

	if (!(component instanceof Label)) { 
	    throw new IllegalArgumentException( 
		"LabelRenderer can only render Label components."); 
	} 

        Label label = (Label) component;
	Theme theme = getTheme();
        
	// To optimize, implement the "fallback" to labeledComponent
	// here. This prevents having to "find" the component twice.
	// Get the labeledComponent instance and check for
	// ComplexComponent here.
	//
	String forId = label.getFor();
	UIComponent labeledComponent = label.getLabeledComponent(context);
	UIComponent fallbackIndicatorComponent = labeledComponent;
	if (labeledComponent != null) {
	    if (labeledComponent instanceof ComplexComponent) {
		forId = ((ComplexComponent)labeledComponent).
		    getLabeledElementId(context);
		// We don't want test instanceof later on
		//
		fallbackIndicatorComponent = ((ComplexComponent)
		    labeledComponent).getIndicatorComponent(context, label);

	    } else {
		forId = labeledComponent.getClientId(context);
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
        boolean requiredFlag = label.isRequiredIndicator() && !isHideIndicators;
        boolean errorFlag = false;
	String errorMsg = null;

	// If hideIndicators is true we don't care about an 
	// indicator component
	//
	// If hideIndicators is true, don't show any indicators.
	// If hideIndicators is false and the indicator component is 
	// readonly or null don't show any indicators
	//
	if (!isHideIndicators) {

	    // Now get the indicator component
	    // Pass false because we want to optimize and not find the
	    // component more than once, so pass false, and we'll
	    // perfofm the fallback if necessary.
	    //
	    UIComponent indicatorComponent = 
		label.getIndicatorComponent(context, false);

	    // Fallback to the labeledComponent
	    //
	    if (indicatorComponent == null) {
		indicatorComponent = fallbackIndicatorComponent;
	    }
	    if (indicatorComponent != null) {
		// Use the attributes so that we don't have to test
		// for EditableValueHolder.
		//
		if (isProperty(indicatorComponent, "readOnly", false)) {
		    requiredFlag = false;
		} else {
		    requiredFlag =
			isProperty(indicatorComponent, "required", false);
		    // We want error flag to be true if the valid 
		    // attribute exists and is false.
		    // Otherwise we want errorFlag to be false.
		    //
		    errorFlag = 
			!isProperty(indicatorComponent, "valid", true);
		}
		if (errorFlag) {
		    // See if the indicator component has any error
		    // messages in the queue
		    //
		    errorMsg = FacesMessageUtils.getDetailMessages(context,
			indicatorComponent.getClientId(context), true, " ");
		}
	    }
	}
        
        JSONObject json = new JSONObject();
        json.put("level", label.getLabelLevel())
            .put("value", formatLabelText(context, label))
            .put("htmlFor", forId)
            .put("required", requiredFlag)
            .put("valid", !errorFlag)
	    .put("className", label.getStyleClass())
            .put("title", label.getToolTip())
	    .put("visible", label.isVisible());
            
        // Append required image properties.
	//
	UIComponent facet = component.getFacet(Label.REQUIRED_FACET);
	if (facet != null) {
	    json.put("requiredImage", WidgetUtilities.renderComponent(context,
		facet));
	}

        // Append error image properties.
        // passing valid=false so that it can output error icon 
	// (irrespective of valid attribute value). 
	facet = component.getFacet(Label.ERROR_FACET);
	if (facet != null) {
	    json.put("errorImage", WidgetUtilities.renderComponent(context, 
		facet));
	}

        // Add attributes.
        JSONUtilities.addStringProperties(stringAttributes, component, json);
	setContents(context, component, json);

        return json;
    }

    /**
     * Get the type of widget represented by this component.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     */
    protected String getWidgetType(FacesContext context, UIComponent component) {
	return "label";
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
     * @param component UIComponent to be rendered.
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
                jArray.put(WidgetUtilities.renderComponent(context, child));
            }
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
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
