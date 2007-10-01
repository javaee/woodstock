/*
 The contents of this file are subject to the terms
 of the Common Development and Distribution License
 (the License).  You may not use this file except in
 compliance with the License.

 You can obtain a copy of the license at
 https://woodstock.dev.java.net/public/CDDLv1.0.html.
 See the License for the specific language governing
 permissions and limitations under the License.

 When distributing Covered Code, include this CDDL
 Header Notice in each file and include the License file
 at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 If applicable, add the following below the CDDL Header,
 with the fields enclosed by brackets [] replaced by
 you own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 Copyright 2007 Sun Microsystems, Inc. All rights reserved.

*/
package org.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 */
public class BoxComponentTag extends UIComponentTag {

    /**
     * <p>Return the requested component type.</p>
     */
    public String getComponentType() {
        return "org.BoxComponent";
    }

    /**
     * <p>Return the requested renderer type.</p>
     */
    public String getRendererType() {
        return "org.BoxComponent";
    }

    /**
     * <p>Release any allocated tag handler attributes.</p>
     */
    public void release() {
        super.release();
        width = null;
        height = null;
	color = null;
	style = null;
	styleClass = null;
    }

    private void persistStringAttribute(UIComponent component,
	    Application application, String attributeName, String attribute) {

	if (isValueReference(attribute)) {
	    ValueBinding vb = application.createValueBinding(attribute);
	    component.setValueBinding(attributeName, vb);
	} else {
	    component.getAttributes().put(attributeName, attribute);
	}
    }

    private void persistIntAttribute(UIComponent component,
	    Application application, String attributeName, String attribute) {

	if (isValueReference(attribute)) {
	    ValueBinding vb = application.createValueBinding(attribute);
	    component.setValueBinding(attributeName, vb);
	} else {
	    component.getAttributes().put(attributeName, 
		Integer.valueOf(attribute));
	}
    }
    /**
     * <p>Transfer tag attributes to component properties.</p>
     */
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
	Application application = getFacesContext().getApplication();
	if (width != null) {
	    persistIntAttribute(component, application, "width", width);
	}
	if (height != null) {
	    persistIntAttribute(component, application, "height", height);
	}
	if (color != null) {
	    persistStringAttribute(component, application, "color", color);
	}
	if (style != null) {
	    persistStringAttribute(component, application, "style", style);
	}
	if (styleClass != null) {
	    persistStringAttribute(component, application, "styleClass",
		styleClass);
	}
    }

    // width
    private String width = null;
    public void setWidth(String width) {
        this.width = width;
    }

    // height
    private String height;
    public void setHeight(String height) {
        this.height = height;
    }

    // name
    private String color = null;
    public void setColor(String color) {
        this.color = color;
    }
    // style
    private String style = null;
    public void setStyle(String style) {
        this.style = style;
    }
    // name
    private String styleClass = null;
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}
