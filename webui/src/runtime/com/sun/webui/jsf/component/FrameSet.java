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
package com.sun.webui.jsf.component;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

/**
 * The FrameSet component defines a set of frames.
 */
@Component(type="com.sun.webui.jsf.FrameSet", family="com.sun.webui.jsf.FrameSet", displayName="Frame Set", tagName="frameSet",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_frame_set",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_frame_set_props")
public class FrameSet extends UIComponentBase {

    /**
     * <p>Construct a new <code>FrameSet</code>.</p>
     */
    public FrameSet() {
        super();
        setRendererType("com.sun.webui.jsf.FrameSet");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.FrameSet";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    /**
     * <p>The width, in pixels, of the space around frames. The frameSpacing 
     * attribute and the border attribute set the same property in different 
     * browsers.  Set frameSpacing and border to the same value.</p>
     */
    @Property(name="border", displayName="border", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int border = Integer.MIN_VALUE;
    private boolean border_set = false;

    /**
     * <p>The width, in pixels, of the space around frames. The frameSpacing 
     * attribute and the border attribute set the same property in different 
     * browsers.  Set frameSpacing and border to the same value.</p>
     */
    public int getBorder() {
        if (this.border_set) {
            return this.border;
        }
        ValueExpression _vb = getValueExpression("border");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>The width, in pixels, of the space around frames. The frameSpacing 
     * attribute and the border attribute set the same property in different 
     * browsers.  Set frameSpacing and border to the same value.</p>
     * @see #getBorder()
     */
    public void setBorder(int border) {
        this.border = border;
        this.border_set = true;
    }

    /**
     * <p>The bordercolor attribute allows you to set the color of the frame 
     * borders using a hex value or a color name.</p>
     */
    @Property(name="borderColor", displayName="Border Color", category="Appearance")
    private String borderColor = null;

    /**
     * <p>The bordercolor attribute allows you to set the color of the frame 
     * borders using a hex value or a color name.</p>
     */
    public String getBorderColor() {
        if (this.borderColor != null) {
            return this.borderColor;
        }
        ValueExpression _vb = getValueExpression("borderColor");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The bordercolor attribute allows you to set the color of the frame 
     * borders using a hex value or a color name.</p>
     * @see #getBorderColor()
     */
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

     /**
     * <p>Defines the number and size of columns in a frameset. The size can be 
     * specified in pixels, percentage of the page width, or with an 
     * asterisk (*).  Specifying * causes the columns to use available space.
     * See the HTML specification for the frameset element for more details.</p>
     */
    @Property(name="cols", displayName="Number of Columns", category="Appearance", isDefault=true)
    private String cols = null;

    /**
     * <p>Defines the number and size of columns in a frameset. The size can be 
     * specified in pixels, percentage of the page width, or with an 
     * asterisk (*).  Specifying * causes the columns to use available space.
     * See the HTML specification for the frameset element for more details.</p>
     */
    public String getCols() {
        if (this.cols != null) {
            return this.cols;
        }
        ValueExpression _vb = getValueExpression("cols");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the number and size of columns in a frameset. The size can be 
     * specified in pixels, percentage of the page width, or with an 
     * asterisk (*).  Specifying * causes the columns to use available space.
     * See the HTML specification for the frameset element for more details.</p>
     * @see #getCols()
     */
    public void setCols(String cols) {
        this.cols = cols;
    }

    
    /**
     * <p>Flag indicating whether frames should have borders or not. If 
     * frameBorder is true, decorative borders are drawn. If frameBorder is  
     * false, a space between frames shows up as the background color of the
     * page.  To show no border or space between frames, you should set 
     * frameBorder to false, and set frameSpacing and border to 0.
     * The default value is true.</p>
     */
    @Property(name="frameBorder", displayName="Frame Border", category="Appearance")
    private boolean frameBorder = true;
    private boolean frameBorder_set = false;

    /**
     * <p>Flag indicating whether frames should have borders or not. If 
     * frameBorder is true, decorative borders are drawn. If frameBorder is  
     * false, a space between frames shows up as the background color of the
     * page.  To show no border or space between frames, you should set 
     * frameBorder to false, and set frameSpacing and border to 0.</p>
     */
    public boolean isFrameBorder() {
        if (this.frameBorder_set) {
            return this.frameBorder;
        }
        ValueExpression _vb = getValueExpression("frameBorder");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return this.frameBorder;
    }

    /**
     * <p>Flag indicating whether frames should have borders or not. If 
     * frameBorder is true, decorative borders are drawn. If frameBorder is  
     * false, a space between frames shows up as the background color of the
     * page.  To show no border or space between frames, you should set 
     * frameBorder to false, and set frameSpacing and border to 0.</p>
     * @see #isFrameBorder()
     */
    public void setFrameBorder(boolean frameBorder) {
        this.frameBorder = frameBorder;
        this.frameBorder_set = true;
    }

 /**
     * <p>The width, in pixels, of the space around frames. The frameSpacing attribute 
     * and the border attribute set the same property in different browsers.  
     * Set frameSpacing and border to the same value.</p>
     */
    @Property(name="frameSpacing", displayName="Frame Spacing", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int frameSpacing = Integer.MIN_VALUE;
    private boolean frameSpacing_set = false;

    /**
     * <p>The width, in pixels, of the space around frames. The frameSpacing attribute 
     * and the border attribute set the same property in different browsers.  
     * Set frameSpacing and border to the same value.</p>
     */
    public int getFrameSpacing() {
        if (this.frameSpacing_set) {
            return this.frameSpacing;
        }
        ValueExpression _vb = getValueExpression("frameSpacing");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>The width, in pixels, of the space around frames. The frameSpacing attribute 
     * and the border attribute set the same property in different browsers.  
     * Set frameSpacing and border to the same value.</p>
     * @see #getFrameSpacing()
     */
    public void setFrameSpacing(int frameSpacing) {
        this.frameSpacing = frameSpacing;
        this.frameSpacing_set = true;
    }

    /**
     * <p>Defines the number and size of rows in a frameset. The size can be 
     * specified in pixels, percentage of the page length, or with an 
     * asterisk (*).  Specifying * causes the rows to use available space.
     * See the HTML specification for the frameset element for more details.</p>
     */
    @Property(name="rows", displayName="Number of Rows", category="Appearance")
    private String rows = null;

    /**
     * <p>Defines the number and size of rows in a frameset. The size can be 
     * specified in pixels, percentage of the page length, or with an 
     * asterisk (*).  Specifying * causes the rows to use available space.
     * See the HTML specification for the frameset element for more details.</p>
     */
    public String getRows() {
        if (this.rows != null) {
            return this.rows;
        }
        ValueExpression _vb = getValueExpression("rows");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the number and size of rows in a frameset. The size can be 
     * specified in pixels, percentage of the page length, or with an 
     * asterisk (*).  Specifying * causes the rows to use available space.
     * See the HTML specification for the frameset element for more details.</p>
     * @see #getRows()
     */
    public void setRows(String rows) {
        this.rows = rows;
    }

   /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

   /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

  /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.border = ((Integer) _values[1]).intValue();
        this.border_set = ((Boolean) _values[2]).booleanValue();
        this.borderColor = (String) _values[3];
        this.cols = (String) _values[4];
        this.frameBorder = ((Boolean) _values[5]).booleanValue();
        this.frameBorder_set = ((Boolean) _values[6]).booleanValue();
        this.frameSpacing = ((Integer) _values[7]).intValue();
        this.frameSpacing_set = ((Boolean) _values[8]).booleanValue();
        this.rows = (String) _values[9];
        this.style = (String) _values[10];
        this.styleClass = (String) _values[11];
        this.toolTip = (String) _values[12];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[13];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.border);
        _values[2] = this.border_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.borderColor;
        _values[4] = this.cols;
        _values[5] = this.frameBorder ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.frameBorder_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = new Integer(this.frameSpacing);
        _values[8] = this.frameSpacing_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.rows;
        _values[10] = this.style;
        _values[11] = this.styleClass;
        _values[12] = this.toolTip;
        return _values;
    }

}
