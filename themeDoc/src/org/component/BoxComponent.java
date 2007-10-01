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

package org.component;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 *
 */
public class BoxComponent extends UIOutput {

    /**
     * <p>Construct a new <code>MetaBase</code>.</p>
     */
    public BoxComponent() {
        super();
        setRendererType("org.BoxComponent");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "org.BoxComponent";
    }

    private int width = Integer.MIN_VALUE;
    private boolean width_set = false;
    /**
     */
    public int getWidth() {
	return getIntAttribute(width_set, "width", width);
    }
    public void setWidth(int width) {
	this.width = width;
	this.width_set = true;
    }

    private int height = Integer.MIN_VALUE;
    private boolean height_set = false;
    /**
     */
    public int getHeight() {
	return getIntAttribute(height_set, "height", height);
    }
    public void setHeight(int height) {
	this.height_set = true;
	this.height = height;
    }

    private String color;
    /**
     */
    public String getColor() {
	return getStringAttribute("color", color);
    }
    public void setColor(String color) {
	this.color = color;
    }

    private String style;
    /**
     */
    public String getStyle() {
	return getStringAttribute("style", style);
    }
    public void setStyle(String style) {
	this.style = style;
    }

    private String styleClass;
    /**
     */
    public String getStyleClass() {
	return getStringAttribute("styleClass", styleClass);
    }
    public void setStyleClass(String styleClass) {
	this.styleClass = styleClass;
    }

    protected int getIntAttribute(boolean setFlag, String attribute,
	    int value) {
        if (setFlag) {
            return value;
        }
        ValueBinding _vb = getValueBinding(attribute);
        if (_vb != null) {
	    Object _result = _vb.getValue(getFacesContext());
	    if (_result == null) {
		return Integer.MIN_VALUE;
	    } else {
		return ((Integer)_result).intValue();
	    }
        }
        return Integer.MIN_VALUE;
    }

    protected String getStringAttribute(String attribute, String value) {
        if (value != null) {
            return value;
        }
        ValueBinding _vb = getValueBinding(attribute);
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
	int i = 0;
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[i]);
        this.width = ((Integer)_values[++i]).intValue();
        this.width_set = ((Boolean) _values[++i]).booleanValue();
        this.height = ((Integer)_values[++i]).intValue();
        this.width_set = ((Boolean) _values[++i]).booleanValue();
        this.color = (String) _values[++i];
        this.style = (String) _values[++i];
        this.styleClass = (String) _values[++i];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
	int i = 0;
        Object _values[] = new Object[8];
        _values[i] = super.saveState(_context);
        _values[++i] = Integer.valueOf(this.width);
        _values[++i] = this.width_set ? Boolean.TRUE :Boolean.FALSE;
        _values[++i] = Integer.valueOf(this.height);
        _values[++i] = this.height_set ? Boolean.TRUE :Boolean.FALSE;
        _values[++i] = this.color;
        _values[++i] = this.style;
        _values[++i] = this.styleClass;
        return _values;
    }
}
