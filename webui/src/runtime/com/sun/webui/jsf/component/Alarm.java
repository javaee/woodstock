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

import java.util.Comparator;
import java.io.IOException;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/** 
 * The Alarm component is used to display a theme-specific image to indicate  
 * the condition of an object.
 */
@Component(type = "com.sun.webui.jsf.Alarm", family = "com.sun.webui.jsf.Alarm",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_alarm",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_alarm_props")
public class Alarm extends ImageComponent implements Comparator {

    /**
     * Down alarm severity.
     */
    public static final String SEVERITY_DOWN = "down";
    /**
     * Critical alarm severity.
     */
    public static final String SEVERITY_CRITICAL = "critical";
    /**
     * Major alarm severity.
     */
    public static final String SEVERITY_MAJOR = "major";
    /**
     * Minor alarm severity.
     */
    public static final String SEVERITY_MINOR = "minor";
    /**
     * Ok alarm severity.
     */
    public static final String SEVERITY_OK = "ok";
    /**
     * Default severity, SEVERITY_OK.
     */
    public static final String DEFAULT_SEVERITY = SEVERITY_OK;

    // Severity level of an alarm.
    private final int SEVERITY_LEVEL_DOWN = 1;
    private final int SEVERITY_LEVEL_CRITICAL = 2;
    private final int SEVERITY_LEVEL_MAJOR = 3;
    private final int SEVERITY_LEVEL_MINOR = 4;
    private final int SEVERITY_LEVEL_OK = 5;

    /** Default constructor. */
    public Alarm() {
    }

    /** Create an instance with the given severity. */
    public Alarm(String severity) {
        setSeverity(severity);
        setRendererType("com.sun.webui.jsf.Alarm");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.Alarm";
    }

    /**
     * Compare the given objects for severity order.
     */
    public int compare(Object o1, Object o2) throws ClassCastException {
        int s1 = getSeverityLevel((Alarm) o1);
        int s2 = getSeverityLevel((Alarm) o2);
        return (s1 > s2) ? -1 : s1 == s2 ? 0 : 1;
    }

    /**
     * Indicates whether some other object is "equal to" this Comparator.
     */
    //TODO implement hashcode
    @Override
    public boolean equals(Object o) throws ClassCastException {
        if (o == null) {
            return false;
        }

        if (o instanceof Alarm) {
            return getSeverityLevel(this) == getSeverityLevel((Alarm) o);
        } else {
            return false;
        }
    }

    /**
     * Helper method to get the severity level of an alarm.
     */
    private int getSeverityLevel(Alarm alarm) {
        int severity = SEVERITY_LEVEL_OK;
        String alarmSeverity = alarm.getSeverity();
        if (alarmSeverity == null) {
            return severity;
        }
        if (alarmSeverity.equals(SEVERITY_DOWN)) {
            severity = SEVERITY_LEVEL_DOWN;
        } else if (alarmSeverity.equals(SEVERITY_CRITICAL)) {
            severity = SEVERITY_LEVEL_CRITICAL;
        } else if (alarmSeverity.equals(SEVERITY_MAJOR)) {
            severity = SEVERITY_LEVEL_MAJOR;
        } else if (alarmSeverity.equals(SEVERITY_MINOR)) {
            severity = SEVERITY_LEVEL_MINOR;
        }
        return severity;
    }

    // Note that this component is implemented differently than
    // other components. First its renderer extends ImageRenderer
    // and second, it does not support a call like "getImageComponent"
    // which would return an appropriately initialized image component
    // representing the severity of this alarm. Once that component is
    // obtained AlarmRenderer would just call the returned component's
    // renderer.
    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        if (context == null) {
            throw new NullPointerException();
        }

        if (!isRendered()) {
            return;
        }
        String rendererType = getRendererType();
        if (rendererType != null) {
            getRenderer(context).encodeBegin(context, this);
        }
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

    // Hide value 
    @Property(name = "value", isHidden = true, isAttribute = false)
    @Override
    public Object getValue() {
        return super.getValue();
    }
    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    @Property(name = "alt", displayName = "Alt Text", category = "Accessibility", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String alt = null;

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    @Override
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
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     * @see #getAlt()
     */
    @Override
    public void setAlt(String alt) {
        this.alt = alt;
    }
    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    @Property(name = "onClick", displayName = "Click Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    @Override
    public String getOnClick() {
        if (this.onClick != null) {
            return this.onClick;
        }
        ValueExpression _vb = getValueExpression("onClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     * @see #getOnClick()
     */
    @Override
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }
    /**
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     */
    @Property(name = "onDblClick", displayName = "Double Click Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDblClick = null;

    /**
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     */
    @Override
    public String getOnDblClick() {
        if (this.onDblClick != null) {
            return this.onDblClick;
        }
        ValueExpression _vb = getValueExpression("onDblClick");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse double click
     * occurs over this component.</p>
     * @see #getOnDblClick()
     */
    @Override
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }
    /**
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     */
    @Property(name = "onKeyDown", displayName = "Key Down Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyDown = null;

    /**
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     */
    public String getOnKeyDown() {
        if (this.onKeyDown != null) {
            return this.onKeyDown;
        }
        ValueExpression _vb = getValueExpression("onKeyDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user presses down on a key while the
     * component has focus.</p>
     * @see #getOnKeyDown()
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }
    /**
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     */
    @Property(name = "onKeyPress", displayName = "Key Press Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyPress = null;

    /**
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     */
    public String getOnKeyPress() {
        if (this.onKeyPress != null) {
            return this.onKeyPress;
        }
        ValueExpression _vb = getValueExpression("onKeyPress");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user presses and releases a key while
     * the component has focus.</p>
     * @see #getOnKeyPress()
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }
    /**
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     */
    @Property(name = "onKeyUp", displayName = "Key Up Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyUp = null;

    /**
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     */
    public String getOnKeyUp() {
        if (this.onKeyUp != null) {
            return this.onKeyUp;
        }
        ValueExpression _vb = getValueExpression("onKeyUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user releases a key while the
     * component has focus.</p>
     * @see #getOnKeyUp()
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }
    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Property(name = "onMouseDown", displayName = "Mouse Down Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Override
    public String getOnMouseDown() {
        if (this.onMouseDown != null) {
            return this.onMouseDown;
        }
        ValueExpression _vb = getValueExpression("onMouseDown");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     * @see #getOnMouseDown()
     */
    @Override
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }
    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Property(name = "onMouseMove", displayName = "Mouse Move Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Override
    public String getOnMouseMove() {
        if (this.onMouseMove != null) {
            return this.onMouseMove;
        }
        ValueExpression _vb = getValueExpression("onMouseMove");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user moves the mouse pointer while
     * over the component.</p>
     * @see #getOnMouseMove()
     */
    @Override
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }
    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     */
    @Property(name = "onMouseOut", displayName = "Mouse Out Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     */
    @Override
    public String getOnMouseOut() {
        if (this.onMouseOut != null) {
            return this.onMouseOut;
        }
        ValueExpression _vb = getValueExpression("onMouseOut");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when a mouse out movement
     * occurs over this component.</p>
     * @see #getOnMouseOut()
     */
    @Override
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }
    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Property(name = "onMouseOver", displayName = "Mouse In Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Override
    public String getOnMouseOver() {
        if (this.onMouseOver != null) {
            return this.onMouseOver;
        }
        ValueExpression _vb = getValueExpression("onMouseOver");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     * @see #getOnMouseOver()
     */
    @Override
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }
    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    @Property(name = "onMouseUp", displayName = "Mouse Up Script", category = "Javascript", editorClassName = "com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    @Override
    public String getOnMouseUp() {
        if (this.onMouseUp != null) {
            return this.onMouseUp;
        }
        ValueExpression _vb = getValueExpression("onMouseUp");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Scripting code executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     * @see #getOnMouseUp()
     */
    @Override
    public void setOnMouseUp(String onMouseUp) {
        this.onMouseUp = onMouseUp;
    }
    /**
     * <p>Specifies the severity of the alarm. Valid values are:
     * <ul>
     * <li>critical</li>
     * <li>major</li>
     * <li>minor</li>
     * <li>down</li>
     * <li>ok</li>
     * </ul>
     * The default value is "ok", which renders no alarm icon.</p>
     */
    @Property(name = "severity", displayName = "Severity", category = "Appearance", isDefault = true, editorClassName = "com.sun.webui.jsf.component.propertyeditors.AlertTypesEditor")
    private String severity = null;

    /**
     * <p>Specifies the severity of the alarm. Valid values are:
     * <ul>
     * <li>critical</li>
     * <li>major</li>
     * <li>minor</li>
     * <li>down</li>
     * <li>ok</li>
     * </ul>
     * The default value is "ok", which renders no alarm icon.</p>
     */
    public String getSeverity() {
        if (this.severity != null) {
            return this.severity;
        }
        ValueExpression _vb = getValueExpression("severity");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Specifies the severity of the alarm. Valid values are:
     * <ul>
     * <li>critical</li>
     * <li>major</li>
     * <li>minor</li>
     * <li>down</li>
     * <li>ok</li>
     * </ul>
     * The default value is "ok", which renders no alarm icon.</p>
     * @see #getSeverity()
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name = "style", displayName = "CSS Style(s)", category = "Appearance", editorClassName = "com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Override
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
    @Override
    public void setStyle(String style) {
        this.style = style;
    }
    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name = "styleClass", displayName = "CSS Style Class(es)", category = "Appearance", editorClassName = "com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Override
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
    @Override
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    /**
     * <p>The text description of the alarm.</p>
     */
    @Property(name = "text", displayName = "Alarm Text")
    private String text = null;

    /**
     * <p>The text description of the alarm.</p>
     */
    public String getText() {
        if (this.text != null) {
            return this.text;
        }
        ValueExpression _vb = getValueExpression("text");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The text description of the alarm.</p>
     * @see #getText()
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * <p>Specifies where the text will be placed relative to the image. The valid values
     * currently are "right" or "left". By default, text is placed to the right of the image.</p>
     */
    @Property(name = "textPosition", displayName = "Text Position")
    private String textPosition = null;

    /**
     * <p>Specifies where the text will be placed relative to the image. The valid values
     * currently are "right" or "left". By default, text is placed to the right of the image.</p>
     */
    public String getTextPosition() {
        if (this.textPosition != null) {
            return this.textPosition;
        }
        ValueExpression _vb = getValueExpression("textPosition");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return "right";
    }

    /**
     * <p>Specifies where the text will be placed relative to the image. The valid values
     * currently are "right" or "left". By default, text is placed to the right of the image.</p>
     * @see #getTextPosition()
     */
    public void setTextPosition(String textPosition) {
        this.textPosition = textPosition;
    }
    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name = "toolTip", displayName = "Tool Tip", category = "Behavior", editorClassName = "com.sun.rave.propertyeditors.StringPropertyEditor")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.alt = (String) _values[1];
        this.onClick = (String) _values[2];
        this.onDblClick = (String) _values[3];
        this.onKeyDown = (String) _values[4];
        this.onKeyPress = (String) _values[5];
        this.onKeyUp = (String) _values[6];
        this.onMouseDown = (String) _values[7];
        this.onMouseMove = (String) _values[8];
        this.onMouseOut = (String) _values[9];
        this.onMouseOver = (String) _values[10];
        this.onMouseUp = (String) _values[11];
        this.severity = (String) _values[12];
        this.style = (String) _values[13];
        this.styleClass = (String) _values[14];
        this.text = (String) _values[15];
        this.textPosition = (String) _values[16];
        this.toolTip = (String) _values[17];
        this.visible = ((Boolean) _values[18]).booleanValue();
        this.visible_set = ((Boolean) _values[19]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[20];
        _values[0] = super.saveState(_context);
        _values[1] = this.alt;
        _values[2] = this.onClick;
        _values[3] = this.onDblClick;
        _values[4] = this.onKeyDown;
        _values[5] = this.onKeyPress;
        _values[6] = this.onKeyUp;
        _values[7] = this.onMouseDown;
        _values[8] = this.onMouseMove;
        _values[9] = this.onMouseOut;
        _values[10] = this.onMouseOver;
        _values[11] = this.onMouseUp;
        _values[12] = this.severity;
        _values[13] = this.style;
        _values[14] = this.styleClass;
        _values[15] = this.text;
        _values[16] = this.textPosition;
        _values[17] = this.toolTip;
        _values[18] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
