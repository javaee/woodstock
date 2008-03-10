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
import com.sun.webui.jsf.util.ComponentUtilities;
import java.beans.Beans;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

/**
 * The Bubble component is used to create a popup help window.
 */
@Component(type="com.sun.webui.jsf.Bubble", family="com.sun.webui.jsf.Bubble", displayName="Bubble Help", tagName="bubble",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_bubble_help",
    tagRendererType="com.sun.webui.jsf.widget.Bubble",    
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_bubble_help_props")
public class Bubble extends WebuiOutput {
    
    /**
     * Creates a new instance of Bubble
     */
    public Bubble() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Bubble");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Bubble";
    }
    
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Bubble";
        }
        return super.getRendererType();
    }
        
    /**
     * Alternative HTML template to be used by bubble component.
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
   
    // Hide Value
    @Property(isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }
    
    // Hide converter
    @Property(isHidden=true, isAttribute=false)
    public Converter getConverter() {
        return super.getConverter();
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
     * <p>Use to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to false, so
     * HTML for the component HTML is included and not visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * <p>Use to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to false, so
     * HTML for the component HTML is included and not visible to the user. If the
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
        if (Beans.isDesignTime()) {
            return true;
        } 
        return false;
    }

    /**
     * <p>Use to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to false, so
     * HTML for the component HTML is included and not visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
     * <p>Use to close the bubble help automatically </p>
     */
    @Property(name="autoClose", displayName="Auto Close", category="Behavior")
    private boolean autoClose = false;
    private boolean autoClose_set = false;

    /**
     * <p>Use to close the bubble help automatically </p>
     */
    public boolean isAutoClose() {
        if (this.autoClose_set) {
            return this.autoClose;
        }
        ValueExpression _vb = getValueExpression("autoClose");
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
     * <p>Use to close the bubble help automatically </p>
     * @see #isVisible()
     */
    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
        this.autoClose_set = true;
    }
    
    /**
     * <p>Use to display close button for bubble. </p>
     */
    @Property(name="closeButton", displayName="Close Button", category="Behavior")
    private boolean closeButton = false;
    private boolean closeButton_set = false;

    /**
     * <p>Use to display close button for bubble. </p>
     */
    public boolean isCloseButton() {
        if (this.closeButton_set) {
            return this.closeButton;
        }
        ValueExpression _vb = getValueExpression("closeButton");
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
     * <p>Use to display close button for bubble. </p>
     * @see #isVisible()
     */
    public void setCloseButton(boolean closeButton) {
        this.closeButton = closeButton;
        this.closeButton_set = true;
    }
    
    /**
     * <p>Use to put a delay in ms before opening the bubble.</p>
     */
    @Property(name="openDelay", displayName="Open Delay", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int openDelay = Integer.MIN_VALUE;;
    private boolean openDelay_set = false;

    /**
     * <p>Use to put a delay in ms before opening the bubble.</p>
     */
    public int getOpenDelay() {
        if (this.openDelay_set) {
            return this.openDelay;
        }
        ValueBinding _vb = getValueBinding("openDelay");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * <p>Use to put a delay in ms before opening the bubble. </p>
     * 
     */
    public void setOpenDelay(int openDelay) {
        this.openDelay = openDelay;
        this.openDelay_set = true;
    }
      
    /**
     * <p>Number of pixels for the width of the bubble help window. The default
     * is 200px.</p>
     */
    @Property(name="width", isHidden=true, displayName="Buuble Width", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int width = Integer.MIN_VALUE;
    private boolean width_set = false;
    
    /**
     * <p>Number of pixels for the width of the bubble help window. 
     * </p>
     */
    public int getWidth() {
        if (this.width_set) {
            return this.width;
        }
        ValueBinding _vb = getValueBinding("width");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }
    
    /**
     * <p>Number of pixels for the width of the popup help window. 
     * </p>
     * @see #getWidth()
     */
    public void setWidth(int width) {
        this.width = width;
        this.width_set = true;
    }
        
    /**
     * <p>Number of milli-seconds after which pop-up help window should close.
     * The default is 2000 milliseconds. </p>
     */
    @Property(name="duration", displayName="Duration for AutoClose", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int duration = Integer.MIN_VALUE;
    private boolean duration_set = false;
    
    /**
     * <p>Number of milli-seconds after which pop-up help window should close.
     * The default is 2000 milliseconds. </p>
     */
    public int getDuration() {
        if (this.duration_set) {
            return this.duration;
        }
        ValueBinding _vb = getValueBinding("duration");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }
    
    /**
     * <p>Number of milli-seconds after which pop-up help window should close.
     * The default is 2000 milliseconds. </p>
     * @see #getDuration()
     */
    public void setDuration(int duration) {
        this.duration = duration;
        this.duration_set = true;
    }
    
    /**
     * <p>Text for popup help title. </p>
     */
    @Property(name="title", displayName="Title", category="Appearance")
    private String title = null;
    
    /**
     * <p>Title for the pop-up help window. </p>
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        ValueBinding _vb = getValueBinding("title");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>Title for the bubble help window. </p>
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * <p>focusId is use to set the focus to the one of the elements inside bubble. 
     *  By default focus is set to bubble header.</p>
     */
    @Property(name="focusId", displayName="focus Id", category="Appearance")
    private String focusId = null;
    
    /**
     * <p>focusId is use to set the focus to the one of the elements inside bubble. 
     *  By default focus is set to bubble header.</p>
     */
    public String getFocusId() {
        if (this.focusId != null) {
            return this.focusId;
        }
        ValueBinding _vb = getValueBinding("focusId");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>focusId is use to set the focus to the one of the elements inside bubble. 
     *  By default focus is set to bubble header.</p>
     * @see #getFocusId()
     */
    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }
    
    /**
     * <p>This tabIndex value is used for making bubble component accessible. The value must be one higher than the
     * last tabIndex value provided to the bubble body element. </p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>This tabIndex value is used for making bubble component accessible. The value must be one higher than the
     * last tabIndex value provided to the bubble body element. </p>
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
     * <p>This tabIndex value is used for making bubble component accessible. The value must be one higher than the
     * last tabIndex value provided to the bubble body element. </p>
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);  
        this.title = (String) _values[1];          
        this.style = (String) _values[2];
        this.styleClass = (String) _values[3];        
        this.visible = ((Boolean) _values[4]).booleanValue();
        this.visible_set = ((Boolean) _values[5]).booleanValue();
        this.autoClose = ((Boolean) _values[6]).booleanValue();
        this.autoClose_set = ((Boolean) _values[7]).booleanValue();
        this.width = ((Integer) _values[8]).intValue();
        this.width_set = ((Boolean) _values[9]).booleanValue();
        this.duration = ((Integer) _values[10]).intValue();
        this.duration_set = ((Boolean) _values[11]).booleanValue();
        this.htmlTemplate = (String) _values[12];
        this.closeButton = ((Boolean) _values[13]).booleanValue();
        this.closeButton_set = ((Boolean) _values[14]).booleanValue();
        this.openDelay = ((Integer) _values[15]).intValue();
        this.openDelay_set = ((Boolean) _values[16]).booleanValue();
        this.focusId = (String) _values[17];
        this.tabIndex = ((Integer) _values[18]).intValue();
        this.tabIndex_set = ((Boolean) _values[19]).booleanValue();
    }
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[20];
        _values[0] = super.saveState(_context);        
        _values[1] = this.title;                
        _values[2] = this.style;
        _values[3] = this.styleClass;
        _values[4] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.autoClose ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.autoClose_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = new Integer(this.width);
        _values[9] = this.width_set ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = new Integer(this.duration);
        _values[11] = this.duration_set ? Boolean.TRUE : Boolean.FALSE;        
        _values[12] = this.htmlTemplate;
        _values[13] = this.closeButton ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.closeButton_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = new Integer(this.openDelay);
        _values[16] = this.openDelay_set ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = this.focusId;
        _values[18] = new Integer(this.tabIndex);
        _values[19] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
