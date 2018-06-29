/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * The MessageGroup component is used to display a list of messages for the page
 * and all its components.
 */
@Component(type = "com.sun.webui.jsf.MessageGroup", family = "com.sun.webui.jsf.MessageGroup",
displayName = "Message Group", tagName = "messageGroup",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_message_group",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_message_group_props")
public class MessageGroup extends UIComponentBase {

    private static final boolean DEBUG = false;

    /**
     * Default constructor.
     */
    public MessageGroup() {
        super();
        setRendererType("com.sun.webui.jsf.MessageGroup");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.MessageGroup";
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
    @Property(name = "id")
    @Override
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
    @Property(name = "rendered")
    @Override
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }
    /**
     * <p>Set this attribute to true to display the detailed message.</p>
     */
    @Property(name = "showDetail", displayName = "Show Detail Message", category = "Appearance")
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
        return false;
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
     * <p>Use the showGlobalOnly attribute to display only those messages that 
     * are not associated with a component id. This attribute allows you to 
     * avoid showing a component error twice if you use <code>webuijsf:message</code> 
     * and <code>webuijsf:messageGroup</code> in the same page.</p>
     */
    @Property(name = "showGlobalOnly", displayName = "Show Global Messages Only", category = "Behavior")
    private boolean showGlobalOnly = false;
    private boolean showGlobalOnly_set = false;

    /**
     * <p>Use the showGlobalOnly attribute to display only those messages that 
     * are not associated with a component id. This attribute allows you to 
     * avoid showing a component error twice if you use <code>webuijsf:message</code> 
     * and <code>webuijsf:messageGroup</code> in the same page.</p>
     */
    public boolean isShowGlobalOnly() {
        if (this.showGlobalOnly_set) {
            return this.showGlobalOnly;
        }
        ValueExpression _vb = getValueExpression("showGlobalOnly");
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
     * <p>Use the showGlobalOnly attribute to display only those messages that 
     * are not associated with a component id. This attribute allows you to 
     * avoid showing a component error twice if you use <code>webuijsf:message</code> 
     * and <code>webuijsf:messageGroup</code> in the same page.</p>
     * @see #isShowGlobalOnly()
     */
    public void setShowGlobalOnly(boolean showGlobalOnly) {
        this.showGlobalOnly = showGlobalOnly;
        this.showGlobalOnly_set = true;
    }
    /**
     * <p>Set this attribute to true to display the summary message.</p>
     */
    @Property(name = "showSummary", displayName = "Show Summary Message", category = "Appearance")
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
        return true;
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
    @Property(name = "style", displayName = "CSS Style(s)", category = "Appearance",
    editorClassName = "com.sun.jsfcl.std.css.CssStylePropertyEditor")
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
    @Property(name = "styleClass", displayName = "CSS Style Class(es)", category = "Appearance",
    editorClassName = "com.sun.rave.propertyeditors.StyleClassPropertyEditor")
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
    @Property(name = "toolTip", displayName = "Tool Tip", category = "Behavior",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
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
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name = "visible", displayName = "Visible", category = "Behavior")
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
     * <p>Sets the title of the message group. If this attribute is not
     * specified, the default title "System Messages" will be used.</p>
     */
    @Property(name = "title", displayName = "title", category = "Behavior",
    editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String title = null;

    /**
     * <p>Sets the title of the message group. If this attribute is not
     * specified, the default title "System Messages" will be used.</p>
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        ValueExpression _vb = getValueExpression("title");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the title of the message group. If this attribute is not
     * specified, the default title "System Messages" will be used.</p>
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.showDetail = ((Boolean) _values[1]).booleanValue();
        this.showDetail_set = ((Boolean) _values[2]).booleanValue();
        this.showGlobalOnly = ((Boolean) _values[3]).booleanValue();
        this.showGlobalOnly_set = ((Boolean) _values[4]).booleanValue();
        this.showSummary = ((Boolean) _values[5]).booleanValue();
        this.showSummary_set = ((Boolean) _values[6]).booleanValue();
        this.style = (String) _values[7];
        this.styleClass = (String) _values[8];
        this.toolTip = (String) _values[9];
        this.visible = ((Boolean) _values[10]).booleanValue();
        this.visible_set = ((Boolean) _values[11]).booleanValue();
        this.title = (String) _values[12];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[13];
        _values[0] = super.saveState(_context);
        _values[1] = this.showDetail ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.showDetail_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.showGlobalOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.showGlobalOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.showSummary ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.showSummary_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.style;
        _values[8] = this.styleClass;
        _values[9] = this.toolTip;
        _values[10] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.title;
        return _values;
    }
}
