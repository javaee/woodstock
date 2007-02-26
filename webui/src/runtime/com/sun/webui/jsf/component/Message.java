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

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.LogUtil;
import com.sun.webui.theme.Theme;

import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * The Message component is used to display error and warning messages for 
 * another component.
 */
@Component(type="com.sun.webui.jsf.Message", family="com.sun.webui.jsf.Message", displayName="Message", tagName="message",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_message",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_message_props")
public class Message extends UIComponentBase {
    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public Message() {
        super();
        setRendererType("com.sun.webui.jsf.Message");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Message";
    }

    private void log(String s) { 
        System.out.println(getClass().getName() + "::" + s);
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
     * <p>Description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    @Property(name="alt", displayName="Alt Text", category="Accessibility", isHidden=true, isAttribute=false)
    private String alt = null;

    /**
     * <p>Description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    public String getAlt() {
        if (this.alt != null) {
            return this.alt;
        }
        ValueExpression _vb = getValueExpression("alt");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     * @see #getAlt()
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * <p>Identifier for the component associated with this message component.</p>
     */
    @Property(name="for", displayName="Input Component", category="Behavior")
    private String _for = null;

    /**
     * <p>Identifier for the component associated with this message component.</p>
     */
    public String getFor() {
        if (this._for != null) {
            return this._for;
        }
        ValueExpression _vb = getValueExpression("for");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Identifier for the component associated with this message component.</p>
     * @see #getFor()
     */
    public void setFor(String _for) {
        this._for = _for;
    }

    /**
     * <p>Set this attribute to true to display the detailed message.</p>
     */
    @Property(name="showDetail", displayName="Show Detail Message", category="Appearance")
    private boolean showDetail = false;
    private boolean showDetail_set = false;

    /**
     * <p>Set this attribute to true to display the detailed message.</p>
     */
    public boolean isShowDetail() {
        if (this.showDetail_set) {
            return this.showDetail;
        }
        ValueExpression _vb = getValueExpression("showDetail");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Set this attribute to true to display the detailed message.</p>
     * @see #isShowDetail()
     */
    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
        this.showDetail_set = true;
    }

    /**
     * <p>Set this attribute to true to display the summary message.</p>
     */
    @Property(name="showSummary", displayName="Show Summary Message", category="Appearance")
    private boolean showSummary = false;
    private boolean showSummary_set = false;

    /**
     * <p>Set this attribute to true to display the summary message.</p>
     */
    public boolean isShowSummary() {
        if (this.showSummary_set) {
            return this.showSummary;
        }
        ValueExpression _vb = getValueExpression("showSummary");
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
     * <p>Set this attribute to true to display the summary message.</p>
     * @see #isShowSummary()
     */
    public void setShowSummary(boolean showSummary) {
        this.showSummary = showSummary;
        this.showSummary_set = true;
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
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor", isHidden=true, isAttribute=false)
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueExpression _vb = getValueExpression("tabIndex");
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
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive 
     * focus when the tab key is pressed. The value must be an integer 
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.alt = (String) _values[1];
        this._for = (String) _values[2];
        this.showDetail = ((Boolean) _values[3]).booleanValue();
        this.showDetail_set = ((Boolean) _values[4]).booleanValue();
        this.showSummary = ((Boolean) _values[5]).booleanValue();
        this.showSummary_set = ((Boolean) _values[6]).booleanValue();
        this.style = (String) _values[7];
        this.styleClass = (String) _values[8];
        this.tabIndex = ((Integer) _values[9]).intValue();
        this.tabIndex_set = ((Boolean) _values[10]).booleanValue();
        this.visible = ((Boolean) _values[11]).booleanValue();
        this.visible_set = ((Boolean) _values[12]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[13];
        _values[0] = super.saveState(_context);
        _values[1] = this.alt;
        _values[2] = this._for;
        _values[3] = this.showDetail ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.showDetail_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.showSummary ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.showSummary_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.style;
        _values[8] = this.styleClass;
        _values[9] = new Integer(this.tabIndex);
        _values[10] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
