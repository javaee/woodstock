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

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The AlarmStatus component is used to display alarm information in the 
 * Masthead component.
 * 
 * @deprecated See tld documentation for more information on how to create 
 * alarmStatus without using the alarmStatus component.
 */
@Component(type="com.sun.webui.jsf.AlarmStatus", 
    family="com.sun.webui.jsf.AlarmStatus", displayName="Alarm Status",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_alarm_status",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_alarm_status_props")
public class AlarmStatus extends ImageHyperlink {
    
    /** Creates a new instance of AlarmStatus */
    public AlarmStatus() {
        super();
        setRendererType("com.sun.webui.jsf.AlarmStatus");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.AlarmStatus";
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Scripting code that is executed when a mouse double-click occurs over this 
     * component.
     */
    @Property(name="onDblClick", isHidden=false, isAttribute=true)
    public String getOnDblClick() {
        return super.getOnDblClick();
    }
    
    /**
     * The current value of this component.
     */
    @Property(name="value", isHidden=false, isAttribute=true)
    public Object getValue() {
        return super.getValue();
    }
    
    // Hide textPosition 
    @Property(name="textPosition", isHidden=true, isAttribute=false)
    public String getTextPosition() {
        return super.getTextPosition();
    }
    
    // Hide icon 
    @Property(name="icon", isHidden=true, isAttribute=false)
    public String getIcon(){
        return super.getIcon();
    }

    /**
     * <p>Specifies if the critical alarm count should be displayed. Is false to 
     * prevent display of critical alarm count and icon.</p>
     */
    @Property(name="criticalAlarms", displayName="Critical Alarms")
    private boolean criticalAlarms = false;
    private boolean criticalAlarms_set = false;

    /**
     * <p>Specifies if the critical alarm count should be displayed. Is false to 
     * prevent display of critical alarm count and icon.</p>
     */
    public boolean isCriticalAlarms() {
        if (this.criticalAlarms_set) {
            return this.criticalAlarms;
        }
        ValueExpression _vb = getValueExpression("criticalAlarms");
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
     * <p>Specifies if the critical alarm count should be displayed. Is false to 
     * prevent display of critical alarm count and icon.</p>
     * @see #isCriticalAlarms()
     */
    public void setCriticalAlarms(boolean criticalAlarms) {
        this.criticalAlarms = criticalAlarms;
        this.criticalAlarms_set = true;
    }

    /**
     * <p>The key identifier for the theme image to use for the critical alarms icon.</p>
     */
    @Property(name="criticalIcon", displayName="Critical Icon")
    private String criticalIcon = null;

    /**
     * <p>The key identifier for the theme image to use for the critical alarms icon.</p>
     */
    public String getCriticalIcon() {
        if (this.criticalIcon != null) {
            return this.criticalIcon;
        }
        ValueExpression _vb = getValueExpression("criticalIcon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The key identifier for the theme image to use for the critical alarms icon.</p>
     * @see #getCriticalIcon()
     */
    public void setCriticalIcon(String criticalIcon) {
        this.criticalIcon = criticalIcon;
    }

    /**
     * <p>Specifies if the down alarm count should be displayed.  
     * Is false to prevent display of down alarm count and icon.</p>
     */
    @Property(name="downAlarms", displayName="Down Alarms")
    private boolean downAlarms = false;
    private boolean downAlarms_set = false;

    /**
     * <p>Specifies if the down alarm count should be displayed.  
     * Is false to prevent display of down alarm count and icon.</p>
     */
    public boolean isDownAlarms() {
        if (this.downAlarms_set) {
            return this.downAlarms;
        }
        ValueExpression _vb = getValueExpression("downAlarms");
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
     * <p>Specifies if the down alarm count should be displayed.  
     * Is false to prevent display of down alarm count and icon.</p>
     * @see #isDownAlarms()
     */
    public void setDownAlarms(boolean downAlarms) {
        this.downAlarms = downAlarms;
        this.downAlarms_set = true;
    }

    /**
     * <p>The key identifier for the theme image to use for the down alarms icon.</p>
     */
    @Property(name="downIcon", displayName="Down Icon")
    private String downIcon = null;

    /**
     * <p>The key identifier for the theme image to use for the down alarms icon.</p>
     */
    public String getDownIcon() {
        if (this.downIcon != null) {
            return this.downIcon;
        }
        ValueExpression _vb = getValueExpression("downIcon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The key identifier for the theme image to use for the down alarms icon.</p>
     * @see #getDownIcon()
     */
    public void setDownIcon(String downIcon) {
        this.downIcon = downIcon;
    }

    /**
     * <p>Specify if the major alarm count should be displayed. Set to false to 
     * prevent display of minor alarm count and icon.</p>
     */
    @Property(name="majorAlarms", displayName="Major Alarms")
    private boolean majorAlarms = false;
    private boolean majorAlarms_set = false;

    /**
     * <p>Specify if the major alarm count should be displayed. Set to false to 
     * prevent display of minor alarm count and icon.</p>
     */
    public boolean isMajorAlarms() {
        if (this.majorAlarms_set) {
            return this.majorAlarms;
        }
        ValueExpression _vb = getValueExpression("majorAlarms");
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
     * <p>Specify if the major alarm count should be displayed. Set to false to 
     * prevent display of minor alarm count and icon.</p>
     * @see #isMajorAlarms()
     */
    public void setMajorAlarms(boolean majorAlarms) {
        this.majorAlarms = majorAlarms;
        this.majorAlarms_set = true;
    }

    /**
     * <p>The key identifier for the theme image to use for the major alarms icon.</p>
     */
    @Property(name="majorIcon", displayName="Major Icon")
    private String majorIcon = null;

    /**
     * <p>The key identifier for the theme image to use for the major alarms icon.</p>
     */
    public String getMajorIcon() {
        if (this.majorIcon != null) {
            return this.majorIcon;
        }
        ValueExpression _vb = getValueExpression("majorIcon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The key identifier for the theme image to use for the major alarms icon.</p>
     * @see #getMajorIcon()
     */
    public void setMajorIcon(String majorIcon) {
        this.majorIcon = majorIcon;
    }

    /**
     * <p>Specifies if the minor alarm count should be displayed. Is false to 
     * prevent display of minor alarm count and icon.</p>
     */
    @Property(name="minorAlarms", displayName="Minor Alarms")
    private boolean minorAlarms = false;
    private boolean minorAlarms_set = false;

    /**
     * <p>Specifies if the minor alarm count should be displayed. Is false to 
     * prevent display of minor alarm count and icon.</p>
     */
    public boolean isMinorAlarms() {
        if (this.minorAlarms_set) {
            return this.minorAlarms;
        }
        ValueExpression _vb = getValueExpression("minorAlarms");
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
     * <p>Specifies if the minor alarm count should be displayed. Is false to 
     * prevent display of minor alarm count and icon.</p>
     * @see #isMinorAlarms()
     */
    public void setMinorAlarms(boolean minorAlarms) {
        this.minorAlarms = minorAlarms;
        this.minorAlarms_set = true;
    }

    /**
     * <p>The key identifier for the theme image to use for the minor alarms icon.</p>
     */
    @Property(name="minorIcon", displayName="Minor Icon")
    private String minorIcon = null;

    /**
     * <p>The key identifier for the theme image to use for the minor alarms icon.</p>
     */
    public String getMinorIcon() {
        if (this.minorIcon != null) {
            return this.minorIcon;
        }
        ValueExpression _vb = getValueExpression("minorIcon");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>The key identifier for the theme image to use for the minor alarms icon.</p>
     * @see #getMinorIcon()
     */
    public void setMinorIcon(String minorIcon) {
        this.minorIcon = minorIcon;
    }

    /**
     * <p>The number of critical alarms, to display next to the appropriate icon.</p>
     */
    @Property(name="numCriticalAlarms", displayName="Number of Critical Alarms")
    private int numCriticalAlarms = Integer.MIN_VALUE;
    private boolean numCriticalAlarms_set = false;

    /**
     * <p>The number of critical alarms, to display next to the appropriate icon.</p>
     */
    public int getNumCriticalAlarms() {
        if (this.numCriticalAlarms_set) {
            return this.numCriticalAlarms;
        }
        ValueExpression _vb = getValueExpression("numCriticalAlarms");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 0;
    }

    /**
     * <p>The number of critical alarms, to display next to the appropriate icon.</p>
     * @see #getNumCriticalAlarms()
     */
    public void setNumCriticalAlarms(int numCriticalAlarms) {
        this.numCriticalAlarms = numCriticalAlarms;
        this.numCriticalAlarms_set = true;
    }

    /**
     * <p>The number of down alarms, to display next to the appropriate icon.</p>
     */
    @Property(name="numDownAlarms", displayName="Number of Down Alarms")
    private int numDownAlarms = Integer.MIN_VALUE;
    private boolean numDownAlarms_set = false;

    /**
     * <p>The number of down alarms, to display next to the appropriate icon.</p>
     */
    public int getNumDownAlarms() {
        if (this.numDownAlarms_set) {
            return this.numDownAlarms;
        }
        ValueExpression _vb = getValueExpression("numDownAlarms");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 0;
    }

    /**
     * <p>The number of down alarms, to display next to the appropriate icon.</p>
     * @see #getNumDownAlarms()
     */
    public void setNumDownAlarms(int numDownAlarms) {
        this.numDownAlarms = numDownAlarms;
        this.numDownAlarms_set = true;
    }

    /**
     * <p>The number of major alarms, can be displayed next to the appropriate icon.</p>
     */
    @Property(name="numMajorAlarms", displayName="Number of Major Alarms")
    private int numMajorAlarms = Integer.MIN_VALUE;
    private boolean numMajorAlarms_set = false;

    /**
     * <p>The number of major alarms, can be displayed next to the appropriate icon.</p>
     */
    public int getNumMajorAlarms() {
        if (this.numMajorAlarms_set) {
            return this.numMajorAlarms;
        }
        ValueExpression _vb = getValueExpression("numMajorAlarms");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 0;
    }

    /**
     * <p>The number of major alarms, can be displayed next to the appropriate icon.</p>
     * @see #getNumMajorAlarms()
     */
    public void setNumMajorAlarms(int numMajorAlarms) {
        this.numMajorAlarms = numMajorAlarms;
        this.numMajorAlarms_set = true;
    }

    /**
     * <p>The number of minor alarms that can be displayed next to the appropriate icon.</p>
     */
    @Property(name="numMinorAlarms", displayName="Number of Minor Alarms")
    private int numMinorAlarms = Integer.MIN_VALUE;
    private boolean numMinorAlarms_set = false;

    /**
     * <p>The number of minor alarms that can be displayed next to the appropriate icon.</p>
     */
    public int getNumMinorAlarms() {
        if (this.numMinorAlarms_set) {
            return this.numMinorAlarms;
        }
        ValueExpression _vb = getValueExpression("numMinorAlarms");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return 0;
    }

    /**
     * <p>The number of minor alarms that can be displayed next to the appropriate icon./p>
     * @see #getNumMinorAlarms()
     */
    public void setNumMinorAlarms(int numMinorAlarms) {
        this.numMinorAlarms = numMinorAlarms;
        this.numMinorAlarms_set = true;
    }

    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)")
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
    @Property(name="styleClass", displayName="CSS Style Class(es)")
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
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * AlarmStatus component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Indicates whether the component should be
     * viewable by the user in the rendered HTML page. If false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * AlarmStatus component is not visible, it can still be processed on subsequent form
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
     * viewable by the user in the rendered HTML page. If false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, this setting is true, so
     * HTML for the component HTML is included and visible to the user. If the
     * AlarmStatus component is not visible, it can still be processed on subsequent form
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
        this.criticalAlarms = ((Boolean) _values[1]).booleanValue();
        this.criticalAlarms_set = ((Boolean) _values[2]).booleanValue();
        this.criticalIcon = (String) _values[3];
        this.downAlarms = ((Boolean) _values[4]).booleanValue();
        this.downAlarms_set = ((Boolean) _values[5]).booleanValue();
        this.downIcon = (String) _values[6];
        this.majorAlarms = ((Boolean) _values[7]).booleanValue();
        this.majorAlarms_set = ((Boolean) _values[8]).booleanValue();
        this.majorIcon = (String) _values[9];
        this.minorAlarms = ((Boolean) _values[10]).booleanValue();
        this.minorAlarms_set = ((Boolean) _values[11]).booleanValue();
        this.minorIcon = (String) _values[12];
        this.numCriticalAlarms = ((Integer) _values[13]).intValue();
        this.numCriticalAlarms_set = ((Boolean) _values[14]).booleanValue();
        this.numDownAlarms = ((Integer) _values[15]).intValue();
        this.numDownAlarms_set = ((Boolean) _values[16]).booleanValue();
        this.numMajorAlarms = ((Integer) _values[17]).intValue();
        this.numMajorAlarms_set = ((Boolean) _values[18]).booleanValue();
        this.numMinorAlarms = ((Integer) _values[19]).intValue();
        this.numMinorAlarms_set = ((Boolean) _values[20]).booleanValue();
        this.style = (String) _values[21];
        this.styleClass = (String) _values[22];
        this.visible = ((Boolean) _values[23]).booleanValue();
        this.visible_set = ((Boolean) _values[24]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[25];
        _values[0] = super.saveState(_context);
        _values[1] = this.criticalAlarms ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.criticalAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.criticalIcon;
        _values[4] = this.downAlarms ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.downAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.downIcon;
        _values[7] = this.majorAlarms ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.majorAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = this.majorIcon;
        _values[10] = this.minorAlarms ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.minorAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[12] = this.minorIcon;
        _values[13] = new Integer(this.numCriticalAlarms);
        _values[14] = this.numCriticalAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = new Integer(this.numDownAlarms);
        _values[16] = this.numDownAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = new Integer(this.numMajorAlarms);
        _values[18] = this.numMajorAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = new Integer(this.numMinorAlarms);
        _values[20] = this.numMinorAlarms_set ? Boolean.TRUE : Boolean.FALSE;
        _values[21] = this.style;
        _values[22] = this.styleClass;
        _values[23] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[24] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
