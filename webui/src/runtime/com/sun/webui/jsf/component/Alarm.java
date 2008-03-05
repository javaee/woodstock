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

import java.util.Comparator;
import java.io.IOException;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.model.Indicator;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

/** 
 * The Alarm component is used to display a theme-specific image to indicate  
 * the condition of an object.
 */
@Component(type="com.sun.webui.jsf.Alarm", family="com.sun.webui.jsf.Alarm",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_alarm",
    tagRendererType="com.sun.webui.jsf.widget.Alarm", 
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_alarm_props")
public class Alarm extends ImageComponent implements NamingContainer, Comparator {
    
    
    /**
     * Down alarm severity.
     */
    public static final String SEVERITY_DOWN     = "down";
    /**
     * Critical alarm severity.
     */
    public static final String SEVERITY_CRITICAL = "critical";
    /**
     * Major alarm severity.
     */
    public static final String SEVERITY_MAJOR    = "major";
    /**
     * Minor alarm severity.
     */
    public static final String SEVERITY_MINOR    = "minor";
    /**
     * Ok alarm severity.
     */
    public static final String SEVERITY_OK       = "ok";

    /**
     * Default severity, SEVERITY_OK.
     */
    public static final String DEFAULT_SEVERITY = "ok";

    
    /**
     * The default "down" <code>Indicator</code>.
     */
    private static Indicator downIndicator =
	new Indicator(ThemeImages.DOWN_ALARM_INDICATOR, SEVERITY_DOWN, 100);
    /**
     * The default "critical" <code>Indicator</code>.
     */
    private static Indicator critalIndicator =
	new Indicator(ThemeImages.CRITICAL_ALARM_INDICATOR, SEVERITY_CRITICAL, 200);
    /**
     * The default "major" <code>Indicator</code>.
     */
    private static Indicator majorIndicator =
	new Indicator(ThemeImages.MAJOR_ALARM_INDICATOR, SEVERITY_MAJOR, 300);
    /**
     * The default "minor" <code>Indicator</code>.
     */
    private static Indicator minorIndicator =
	new Indicator(ThemeImages.MINOR_ALARM_INDICATOR, SEVERITY_MINOR, 400);
    /**
     * The default "ok" <code>Indicator</code>.
     */
    private static Indicator okIndicator =
	new Indicator(ThemeImages.OK_ALARM_INDICATOR, SEVERITY_OK, 500);

    
    /**
     * The list of default <code>Indicator</code>'s.
     */
    protected static List<Indicator> indicatorList =
	 new ArrayList<Indicator>();

    static {
	indicatorList.add(downIndicator);
	indicatorList.add(critalIndicator);
	indicatorList.add(majorIndicator);
	indicatorList.add(minorIndicator);
	indicatorList.add(okIndicator);
    } 

    // Severity level of an alarm.
    private final int SEVERITY_LEVEL_DOWN     = 1;
    private final int SEVERITY_LEVEL_CRITICAL = 2;
    private final int SEVERITY_LEVEL_MAJOR    = 3;
    private final int SEVERITY_LEVEL_MINOR    = 4;
    private final int SEVERITY_LEVEL_OK       = 5;

    /** Default constructor. */
    public Alarm() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Alarm");
    }

    /** 
     * Create an instance with the given severity. 
     * @deprecated
     */
    public Alarm(String severity) {
        setSeverity(severity);
        setRendererType("com.sun.webui.jsf.widget.Alarm");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Alarm";
    }

    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Alarm";
        }
        return super.getRendererType();
    }
    
    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    /**
     * Returns a cloned list of the default indicators that can
     * be modified without affecting the default list.
     * <p>
     * Typically this method is called by an application that
     * wants to add a an application defined <code>Indicator</code>
     * or replace a default <code>Indicator</code>. An application
     * first call<br/>
     * <code>List list = Alarm.getDefaultIndicators();</code><br/>
     * and them add and/or replace an <code>Indicator</code>.<br/>
     * <code>list.add(appMostSevere); // Add an application indicator</code><br/>
     * To replace an indicator it must removed first. An indicator
     * is equal to another indicator if their "type" attributes are the
     * equal. If <code>appOkIndicator</code> has type = "ok" this call will
     * remove the default "ok" indicator.
     * <code>list.remove(appOkIndicator);// remove default</code><br/>
     * After the default indicator is removed add the replacement.
     * <code>list.add(appOkIndicator); // add the replacement</code><br/>
     * In order for the Alarm component to utilize the modified list
     * the application can have a value expression for the 
     * <code>indicators</code> property in which the application returns the
     * modified list, or calls the <code>setIndicators(list)</code>
     * method to assign the modified list.
     */
    public static List<Indicator> getDefaultIndicators() {
	List<Indicator> list = new ArrayList<Indicator>();
	list.addAll(indicatorList);
	return list;
    }

    /**
     * Return zero if the severity of <code>o1</code> equals <code>o2</code>,
     * negative 1 if the severity <code>o1</code> is less than <code>o2</code>,
     * positive 1 if the severity <code>o1</code> is greater than 
     * <code>o2</code>.
     */
    public int compare(Object o1, Object o2) {
	// Optimization. By definition if the severities or type's 
	// are equal the Alarms are equal.
	//
	String type1 = ((Alarm)o1).getSeverity();
	String type2 = ((Alarm)o2).getSeverity();
	if (type1 != null && type1.equals(type2)) {
	    return 0;
	}

	// Here we need to get the indicator from each object
	// and based on the severity level, and then call the
	// compareTo method. This method should have been from the
	// Comparable interface and not the Comparator.
	//
	List<Indicator> indList = getIndicators();
	Indicator ind1 = getIndicator(indList, type1);
	
	Indicator ind2 = getIndicator(indList, type2);

	if (ind1 != null && ind2 != null) {
	    return ind1.compareTo(ind2);
	}

	if (ind1 == null && ind2 != null) {
	    return -1;
	}
        
        if (ind1 != null && ind2 == null) {
            return 1;
        }

	// both ind1 and ind2 are null
	//
	return 0;
    }

    /**
     * Returns Indicator for the specified type.
     * If type is null return null.
     */
    private Indicator getIndicator(List<Indicator> indList, String type) {
	if (indList != null) {
            Iterator<Indicator> iter = indList.iterator();
            if (type != null) {
                while (iter.hasNext()) {
                    Indicator ind = (Indicator)iter.next();
                    if (type.equals(ind.getType())) {
                        return ind;
                    }
                }
            }
        }
	return null;
    }
    
    /**
     * Override equals()
     * returns false if object is not an instance of Alarm.
     */
    public boolean equals(Object o) {
	if (!(o instanceof Alarm)) {
	    return false;
	}
	return compare(this, o) == 0 ? true : false;
    }
    
    // Note that this component is implemented differently than
    // other components. First its renderer extends ImageRenderer
    // and second, it does not support a call like "getImageComponent"
    // which would return an appropriately initialized image component
    // representing the severity of this alarm. Once that component is
    // obtained AlarmRenderer would just call the returned component's
    // renderer.
    
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
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * <p>A developer define types of Alarm. This attribute can be set to an array of Indicators
     *  where Indicator holds
     *  the custom defined type and associated image.</p>
     */
    @Property(name="indicators", displayName="Indicators", category="Behavior", editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    // jasper compiler does not recognize generics which causes error. So, generics is not used 
    // here for indicators. 
    private List indicators;

    /**
     * Return a <code>List</code> of <code>Indicators</code> supported
     * by this <code>Alarm</code>. If <code>indicators</code> has not been
     * set explicitly by the application and if there is no value
     * expression, a list of default alarm indicators obtained by
     * calling <code>DefaultAlarmIndicators.getIndicators()</code> is
     * returned. If the application modifies this list, it must call
     * <code>setIndicators</code> or add a value expression that resolves
     * to the modified list in order to persist the change, otherwise
     * this method will continue to return a list of default
     * alarm indicators.
     */
    public List<Indicator> getIndicators() {
        if (this.indicators != null) {
            return this.indicators;
        }
        ValueExpression _vb = getValueExpression("indicators");
        if (_vb != null) {
            return (List<Indicator>) _vb.getValue(getFacesContext().getELContext());
        }
	// If we get here we know that the developer has not
	// assigned a list of indicators. Return null, client-side widget creates 
	// the default alarm indicators
	//
	return null;
    }

    /**
     * Set the list of indicators supported by this alarm. If this
     * method is called, any value expression defined for this property
     * are ignored. Call this method to persist changes to a 
     * list of alarm indicators which may contains default set plus custom 
     * alarm indicators.
     */
    public void setIndicators(List<Indicator> indicators) {
	this.indicators = indicators;
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

    // Hide value 
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     */
    @Property(name="alt", displayName="Alt Text", category="Accessibility")
    private String alt = null;

    /**
     * <p>Alternative textual description of the image rendered by this component. The alt
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
     * <p>Alternative textual description of the image rendered by this component. The alt
     * text can be used by screen readers and in tool tips, and when image display is turned off in
     * the web browser.</p>
     * @see #getAlt()
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * <p>Scripting code executed when a mouse click
     * occurs over this component.</p>
     */
    @Property(name="onClick", displayName="Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onClick = null;

    /**
     * <p>Scripting code that is executed when a mouse click
     * occurs over this component.</p>
     */
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
     * <p>Scripting code that is executed when a mouse click
     * occurs over this component.</p>
     * @see #getOnClick()
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * <p>Scripting code that is executed when a mouse double click
     * occurs over this component.</p>
     */
    @Property(name="onDblClick", displayName="Double Click Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onDblClick = null;

    /**
     * <p>Scripting code that is executed when a mouse double click
     * occurs over this component.</p>
     */
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
     * <p>Scripting code that is executed when a mouse double click
     * occurs over this component.</p>
     * @see #getOnDblClick()
     */
    public void setOnDblClick(String onDblClick) {
        this.onDblClick = onDblClick;
    }

    /**
     * <p>Scripting code that is executed when the user presses down on a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyDown", displayName="Key Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyDown = null;

    /**
     * <p>Scripting code that is executed when the user presses down on a key while the
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
     * <p>Scripting code that is executed when the user presses down on a key while the
     * component has focus.</p>
     * @see #getOnKeyDown()
     */
    public void setOnKeyDown(String onKeyDown) {
        this.onKeyDown = onKeyDown;
    }

    /**
     * <p>Scripting code that is executed when the user presses and releases a key while
     * the component has focus.</p>
     */
    @Property(name="onKeyPress", displayName="Key Press Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyPress = null;

    /**
     * <p>Scripting code that is executed when the user presses and releases a key while
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
     * <p>Scripting code that is executed when the user presses and releases a key while
     * the component has focus.</p>
     * @see #getOnKeyPress()
     */
    public void setOnKeyPress(String onKeyPress) {
        this.onKeyPress = onKeyPress;
    }

    /**
     * <p>Scripting code that is executed when the user releases a key while the
     * component has focus.</p>
     */
    @Property(name="onKeyUp", displayName="Key Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onKeyUp = null;

    /**
     * <p>Scripting code that is executed when the user releases a key while the
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
     * <p>Scripting code that is executed when the user releases a key while the
     * component has focus.</p>
     * @see #getOnKeyUp()
     */
    public void setOnKeyUp(String onKeyUp) {
        this.onKeyUp = onKeyUp;
    }

    /**
     * <p>Scripting code that is executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
    @Property(name="onMouseDown", displayName="Mouse Down Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseDown = null;

    /**
     * <p>Scripting code that is executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     */
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
     * <p>Scripting code that is executed when the user presses a mouse button while the
     * mouse pointer is on the component.</p>
     * @see #getOnMouseDown()
     */
    public void setOnMouseDown(String onMouseDown) {
        this.onMouseDown = onMouseDown;
    }

    /**
     * <p>Scripting code that is executed when the user moves the mouse pointer while
     * over the component.</p>
     */
    @Property(name="onMouseMove", displayName="Mouse Move Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseMove = null;

    /**
     * <p>Scripting code that is executed when the user moves the mouse pointer while
     * over the component.</p>
     */
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
     * <p>Scripting code that is executed when the user moves the mouse pointer while
     * over the component.</p>
     * @see #getOnMouseMove()
     */
    public void setOnMouseMove(String onMouseMove) {
        this.onMouseMove = onMouseMove;
    }

    /**
     * <p>Scripting code that is executed when a mouse out movement
     * occurs over this component.</p>
     */
    @Property(name="onMouseOut", displayName="Mouse Out Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOut = null;

    /**
     * <p>Scripting code that is executed when a mouse out movement
     * occurs over this component.</p>
     */
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
     * <p>Scripting code that is executed when a mouse out movement
     * occurs over this component.</p>
     * @see #getOnMouseOut()
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * <p>Scripting code that is executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
    @Property(name="onMouseOver", displayName="Mouse In Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseOver = null;

    /**
     * <p>Scripting code that is executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     */
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
     * <p>Scripting code that is executed when the user moves the  mouse pointer into
     * the boundary of this component.</p>
     * @see #getOnMouseOver()
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * <p>Scripting code that is executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
    @Property(name="onMouseUp", displayName="Mouse Up Script", category="Javascript", editorClassName="com.sun.rave.propertyeditors.JavaScriptPropertyEditor")
    private String onMouseUp = null;

    /**
     * <p>Scripting code that is executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     */
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
     * <p>Scripting code that is executed when the user releases a mouse button while
     * the mouse pointer is on the component.</p>
     * @see #getOnMouseUp()
     */
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
    @Property(name="severity", displayName="Severity", category="Appearance", isDefault=true)
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
        return Alarm.DEFAULT_SEVERITY; 
    }

    /**
     * <p>Specifies the severity of the alarm. default set of
     *  severity values are:
     * <ul>
     * <li>critical</li>
     * <li>major</li>
     * <li>minor</li>
     * <li>down</li>
     * <li>ok</li>
     * </ul>
     * Apart from the default set of severities, custom severities are
     * also supported. 
     * The default value is "ok", which renders no alarm icon.</p>
     * @see #getSeverity()
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
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
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;

    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this 
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
     * <p>CSS style class or classes to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * <p>The text description of the alarm.</p>
     */
    @Property(name="text", displayName="Alarm Text")
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
    @Property(name="textPosition", displayName="Text Position")
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
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * Alarm component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * Alarm component is not visible, it can still be processed on subsequent form
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
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * Alarm component is not visible, it can still be processed on subsequent form
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
        this.indicators = (List) _values[20];         
        this.htmlTemplate = (String) _values[21];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[22];
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
        _values[20] = this.indicators;
        _values[21] = this.htmlTemplate;     

        return _values;
    }
}
