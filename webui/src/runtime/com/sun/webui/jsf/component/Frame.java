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
 * The Frame component is used inside a FrameSet component to denote a frame.
 */
@Component(type="com.sun.webui.jsf.Frame", family="com.sun.webui.jsf.Frame", displayName="Frame", tagName="frame",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_frame",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_frame_props")
public class Frame extends UIComponentBase {

    /**
     * <p>Construct a new <code>Frame</code>.</p>
     */
    public Frame() {
        super();
        setRendererType("com.sun.webui.jsf.Frame");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Frame";
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
     * <p>Set the value of the frameBorder attribute to "true" when a border is 
     * needed around the frame.</p>
     */
    @Property(name="frameBorder", displayName="Frame Border", category="Appearance")
    private boolean frameBorder = false;
    private boolean frameBorder_set = false;

    /**
     * <p>Set the value of the frameBorder attribute to "true" when a border is 
     * needed around the frame.</p>
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
        return false;
    }

    /**
     * <p>Set the value of the frameBorder attribute to "true" when a border is 
     * needed around the frame.</p>
     * @see #isFrameBorder()
     */
    public void setFrameBorder(boolean frameBorder) {
        this.frameBorder = frameBorder;
        this.frameBorder_set = true;
    }

    /**
     * <p>A URL to a long description of the frame contents. Use it for browsers that do not support frames</p>
     */
    @Property(name="longDesc", displayName="Long Description", category="Appearance")
    private String longDesc = null;

    /**
     * <p>A URL to a long description of the frame contents. Use it for browsers that do not support frames</p>
     */
    public String getLongDesc() {
        if (this.longDesc != null) {
            return this.longDesc;
        }
        ValueExpression _vb = getValueExpression("longDesc");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>A URL to a long description of the frame contents. Use it for browsers that do not support frames</p>
     * @see #getLongDesc()
     */
    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    /**
     * <p>Defines the top and bottom margins in the frame</p>
     */
    @Property(name="marginHeight", displayName="Margin Height", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int marginHeight = Integer.MIN_VALUE;
    private boolean marginHeight_set = false;

    /**
     * <p>Defines the top and bottom margins in the frame</p>
     */
    public int getMarginHeight() {
        if (this.marginHeight_set) {
            return this.marginHeight;
        }
        ValueExpression _vb = getValueExpression("marginHeight");
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
     * <p>Defines the top and bottom margins in the frame</p>
     * @see #getMarginHeight()
     */
    public void setMarginHeight(int marginHeight) {
        this.marginHeight = marginHeight;
        this.marginHeight_set = true;
    }

    /**
     * <p>Defines the left and right margins in the frame</p>
     */
    @Property(name="marginWidth", displayName="Margin Width", category="Appearance", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int marginWidth = Integer.MIN_VALUE;
    private boolean marginWidth_set = false;

    /**
     * <p>Defines the left and right margins in the frame</p>
     */
    public int getMarginWidth() {
        if (this.marginWidth_set) {
            return this.marginWidth;
        }
        ValueExpression _vb = getValueExpression("marginWidth");
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
     * <p>Defines the left and right margins in the frame</p>
     * @see #getMarginWidth()
     */
    public void setMarginWidth(int marginWidth) {
        this.marginWidth = marginWidth;
        this.marginWidth_set = true;
    }

    /**
     * <p>Defines a unique name for the frame (to use in scripts)</p>
     */
    @Property(name="name", displayName="Name", category="Appearance", isDefault=true)
    private String name = null;

    /**
     * <p>Defines a unique name for the frame (to use in scripts)</p>
     */
    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        ValueExpression _vb = getValueExpression("name");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines a unique name for the frame (to use in scripts)</p>
     * @see #getName()
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Set the value of the noResize attribute to "true" when  user 
     * is not allowed to resize the frame.</p>
     */
    @Property(name="noResize", displayName="No Resize", category="Appearance", isHidden=true, isAttribute=true)
    private boolean noResize = false;
    private boolean noResize_set = false;

    /**
     * <p>Set the value of the noResize attribute to "true" when  user 
     * is not allowed to resize the frame.</p>
     */
    public boolean isNoResize() {
        if (this.noResize_set) {
            return this.noResize;
        }
        ValueExpression _vb = getValueExpression("noResize");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }

    /**
     * <p>Set the value of the noResize attribute to "true" when  user 
     * is not allowed to resize the frame.</p>
     * @see #isNoResize()
     */
    public void setNoResize(boolean noResize) {
        this.noResize = noResize;
        this.noResize_set = true;
    }

    /**
     * <p>Determines scrollbar action (valid values are: yes, no, auto)</p>
     */
    @Property(name="scrolling", displayName="Scrolling", category="Appearance")
    private String scrolling = null;

    /**
     * <p>Determines scrollbar action (valid values are: yes, no, auto)</p>
     */
    public String getScrolling() {
        if (this.scrolling != null) {
            return this.scrolling;
        }
        ValueExpression _vb = getValueExpression("scrolling");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Determines scrollbar action (valid values are: yes, no, auto)</p>
     * @see #getScrolling()
     */
    public void setScrolling(String scrolling) {
        this.scrolling = scrolling;
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
     * <p>Defines the URL of the file to show in the frame.</p>
     */
    @Property(name="url", displayName="URL", category="Appearance")
    private String url = null;

    /**
     * <p>Defines the URL of the file to show in the frame.</p>
     */
    public String getUrl() {
        if (this.url != null) {
            return this.url;
        }
        ValueExpression _vb = getValueExpression("url");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Defines the URL of the file to show in the frame.</p>
     * @see #getUrl()
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.frameBorder = ((Boolean) _values[1]).booleanValue();
        this.frameBorder_set = ((Boolean) _values[2]).booleanValue();
        this.longDesc = (String) _values[3];
        this.marginHeight = ((Integer) _values[4]).intValue();
        this.marginHeight_set = ((Boolean) _values[5]).booleanValue();
        this.marginWidth = ((Integer) _values[6]).intValue();
        this.marginWidth_set = ((Boolean) _values[7]).booleanValue();
        this.name = (String) _values[8];
        this.noResize = ((Boolean) _values[9]).booleanValue();
        this.noResize_set = ((Boolean) _values[10]).booleanValue();
        this.scrolling = (String) _values[11];
        this.style = (String) _values[12];
        this.styleClass = (String) _values[13];
        this.toolTip = (String) _values[14];
        this.url = (String) _values[15];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[16];
        _values[0] = super.saveState(_context);
        _values[1] = this.frameBorder ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.frameBorder_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.longDesc;
        _values[4] = new Integer(this.marginHeight);
        _values[5] = this.marginHeight_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = new Integer(this.marginWidth);
        _values[7] = this.marginWidth_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.name;
        _values[9] = this.noResize ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.noResize_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.scrolling;
        _values[12] = this.style;
        _values[13] = this.styleClass;
        _values[14] = this.toolTip;
        _values[15] = this.url;
        return _values;
    }

}
