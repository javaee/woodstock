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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.LogUtil;

import java.beans.Beans;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.el.ValueBinding;

/**
 * The Rating component presents a row of stars indicating a rating assigned to an
 * item. When the user assigns a new rating to an item, an Ajax request transmits 
 * the rating to the server.  The rows of stars can also indicate the average
 * rating of all users.
 */
@Component(type="com.sun.webui.jsf.Rating", family="com.sun.webui.jsf.Rating", displayName="Rating", tagName="rating",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_rating_help",
    tagRendererType="com.sun.webui.jsf.widget.Rating",    
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_rating_help_props")
public class Rating extends WebuiInput {
    
    // Class constants - must have different values and must be <= 0
    // Value for NOT_INTERESTED_GRADE must match CODE_NOTINTERESTED in widget/rating.js
    // Value for CLEAR_GRADE must match CODE_CLEAR in widget/rating.js

    /** The value of a grade when it is marked "not interested". */
    public static int NOT_INTERESTED_GRADE = -1;
    
    // Grade is clear.  Need not be 0, but must match CODE_CLEAR in widget/rating.js
    /** The value of a grade when it is marked "cleared". */
    public static int CLEAR_GRADE = 0;

    
    /**
     * Creates a new instance of Rating
     */
    public Rating() {
        super();
        setRendererType("com.sun.webui.jsf.widget.Rating");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.Rating";
    }
    
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.Rating";
        }
        return super.getRendererType();
    }
    
    /**
     * <p>Return the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property aliases.</p>
     *
     * @param name Name of value binding expression to retrieve
     */
    public ValueExpression getValueExpression(String name) {
	// "grade" is an alias for UIInput "value"
        if (name.equals("grade")) {
            return super.getValueExpression("value");
        }
        return super.getValueExpression(name);
    }
    
    /**
     * <p>Set the <code>ValueExpression</code> stored for the
     * specified name (if any), respecting any property
     * aliases.</p>
     *
     * @param name    Name of value binding to set
     * @param binding ValueExpression to set, or null to remove
     */
    public void setValueExpression(String name,ValueExpression binding) {
	// "grade" is an alias for UIInput "value"
        if (name.equals("grade")) {
            super.setValueExpression("value", binding);
            return;
        }
        super.setValueExpression(name, binding);
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    // Hide value, since we use "grade" as an alias for UIInput value.
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
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
     * <p>Indicates whether the grade is automatically submitted to the
     * server via an Ajax request immediately after the grade is selected.
     * The default is false - it is NOT automatically submitted.</p>
     */
    @Property(name="autoSubmit", displayName="Auto Submit", category="Behavior")
    private boolean autoSubmit = false;
    private boolean autoSubmit_set = false;

    /**
     * <p>Return true if the grade is automatically submitted to the
     * server via an Ajax request immediately after the grade is selected,
     * false if not.</p>
     */
    public boolean isAutoSubmit() {
        if (this.autoSubmit_set) {
            return this.autoSubmit;
        }
        ValueExpression _vb = getValueExpression("autoSubmit");
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
     * <p>Set whether the grade is automatically submitted to the
     * server via an Ajax request immediately after the grade is selected.
     * The default is false - it is NOT automatically submitted.</p>
     */
    public void setAutoSubmit(boolean autoSubmit) {
        this.autoSubmit = autoSubmit;
        this.autoSubmit_set = true;
    }
    
    /**
     * <p>The average grade the general user population has assigned to the item.  
     * Must be between 0.0 and the maximum grade.   This attribute should be 
     * set to a value binding expression that corresponds to a property of a 
     * managed bean so it will be updated whenever a grade is clicked
     * and the grade property is updated asynchronously.</p>
     */
    @Property(name="averageGrade", displayName="Average Grade", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.DoublePropertyEditor")
    private double averageGrade = 0.0;
    private boolean averageGrade_set = false;

    /**
     * <p>Get the average grade the general user population has assigned to the item.  
     * Will be between 0.0 and the maximum grade and rounded to the nearest 1/2 grade.</p>
     */
    public double getAverageGrade() {
        if (this.averageGrade_set) {
            return this.averageGrade;
        }
        ValueBinding _vb = getValueBinding("averageGrade");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Double.MIN_VALUE;
            } else {
                return ((Double) _result).doubleValue();
            }
        }
	return 0.0;
    }

    /**
     * <p>Set the average grade the general user population has assigned to the 
     * item.  Must be between 0.0 and the maximum grade.  The default is 0.</p>
     * 
     */
    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
        this.averageGrade_set = true;
    }

    /**
     * <p>The acknowledged text for the clear control.  There is no default.</p>
     */
    @Property(name="clearAcknowledgedText", displayName="Clear Acknowledged Text", category="Appearance")
    private String clearAcknowledgedText = null;
    private boolean clearAcknowledgedText_set = false;

    /**
     * <p>Returns the acknowledged text for the clear control.  Returns null if not set.</p>
     */
    public String getClearAcknowledgedText() {
        if (this.clearAcknowledgedText_set) {
	    return this.clearAcknowledgedText;
	}
        ValueExpression _vb = getValueExpression("clearAcknowledgedText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the acknowledged text for the clear control.
     */
    public void setClearAcknowledgedText(String clearAcknowledgedText) {
        this.clearAcknowledgedText = clearAcknowledgedText;
        this.clearAcknowledgedText_set = true;
    }

    /**
     * <p>The hover text for the clear control.  There is no default.</p>
     */
    @Property(name="clearHoverText", displayName="Clear Hover Text", category="Appearance")
    private String clearHoverText = "Hello World";
    private boolean clearHoverText_set = false;

    /**
     * <p>Returns the hover text for the clear control.  Returns null if not set.</p>
     */
    public String getClearHoverText() {
        if (this.clearHoverText_set) {
	    return this.clearHoverText;
	}
        ValueExpression _vb = getValueExpression("clearHoverText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the hover text for the clear control.
     */
    public void setClearHoverText(String clearHoverText) {
        this.clearHoverText = clearHoverText;
        this.clearHoverText_set = true;
    }
    
    /**
     * <p>The grade (number of "stars") the user has assigned the item.
     * This attribute should be set to a value binding expression that corresponds 
     * to a property of a managed bean so it will be updated whenever a grade 
     * is clicked.  A managed bean can set the value to <code>Rating.NOT_INTERESTED_GRADE</code>
     * for a not interested grade and <code>Rating.CLEAR_GRADE</code> for a cleared (effectively, 
     * set it to 0) grade.</p>
     */
    @Property(name="grade", displayName="Grade", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")

    /**
     * <p>Return the grade (number of "stars") the user has assigned the item. 
     * For "not interested", the value returned is <code>Rating.NOT_INTERESTED_GRADE</code>, 
     * and for a "clear" grade the value returned is <code>Rating.CLEAR_GRADE</code>.</p>
     */
    public int getGrade() {
	Integer v = (Integer)getValue();
	if (v != null)
	    return v.intValue();

	return CLEAR_GRADE;
    }

    /**
     * <p>Set the grade (number of "stars") the user has assigned the item. 
     * Use <code>Rating.NOT_INTERESTED_GRADE</code> for "not interested" and 
     * <code>Rating.CLEAR_GRADE</code> for a "clear" grade (effectively, set it to 0).
     * The default is <code>Rating.CLEAR_GRADE</code>.</p>
     * 
     */
    public void setGrade(int grade) {
	setValue(new Integer(grade));
    }

    /**
     * <p>The acknowledged text for the grade controls.  There is no default.</p>
     */
    @Property(name="gradeAcknowledgedText", displayName="Grade Acknowledged Text", category="Appearance")
    private String gradeAcknowledgedText = null;
    private boolean gradeAcknowledgedText_set = false;

    /**
     * <p>Returns the acknowledged text for the grade controls.  Returns null if not set.</p>
     */
    public String getGradeAcknowledgedText() {
        if (this.gradeAcknowledgedText_set) {
	    return this.gradeAcknowledgedText;
	}
        ValueExpression _vb = getValueExpression("gradeAcknowledgedText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the acknowledged text for the grade controls.
     */
    public void setGradeAcknowledgedText(String gradeAcknowledgedText) {
        this.gradeAcknowledgedText = gradeAcknowledgedText;
        this.gradeAcknowledgedText_set = true;
    }
    
    /**
     * <p>The hover texts that will be used for the grade controls, ordered from 
     * lowest to highest rating.  The attribute's value must be a JavaServer Faces 
     * EL expression that evaluates to an array of <code>java.util.String</code>.  
     * The first element will be the hover text associated with the lowest rating;  
     * the last element with the highest rating.  Null can be specified as a member 
     * of the array.  There are no defaults.</p>
     */
    @Property(name="gradeHoverTexts", displayName="Grade Hover Texts", category="Appearance")
    private String[] gradeHoverTexts = null;
    private boolean gradeHoverTexts_set = false;

    /**
     * <p>Returns the hover texts that are used for the grade controls, ordered 
     * from lowest to highest rating.  That is, the first element of the returned 
     * array will be the hover text associated with the lowest rating;  the last 
     * element with the highest rating.  Returns null if no texts have been set.</p>
     */
    public String[] getGradeHoverTexts() {
        if (this.gradeHoverTexts_set) {
            return this.gradeHoverTexts;
        }
        ValueExpression _vb = getValueExpression("gradeHoverTexts");
        if (_vb != null) {
            return (String[]) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the hover texts that will be used for the grade controls, ordered 
     * from lowest to highest rating.  That is, hoverTexts[0] will be the hover 
     * text associated with the lowest rating;  hoverTexts[hoverTexts.length-1] 
     * with the highest rating.  Null can be specified as a member of the array.  
     * There are no defaults.</p>
     */
    public void setGradeHoverTexts(String[] gradeHoverTexts) {
        this.gradeHoverTexts = gradeHoverTexts;
        this.gradeHoverTexts_set = true;
    }    
    
    /**
     * <p>Indicates whether the grade of this rating component can be changed by the user.  
     * The default is false - it is NOT read-only, and therefore can be changed by the user.</p>
     */
    @Property(name="gradeReadOnly", displayName="Grade Read Only", category="Appearance")
    private boolean gradeReadOnly = false;
    private boolean gradeReadOnly_set = false;

    /**
     * <p>Return true if the grade of this rating component can be changed by the user.</p>
     */
    public boolean isGradeReadOnly() {
        if (this.gradeReadOnly_set) {
            return this.gradeReadOnly;
        }
        ValueExpression _vb = getValueExpression("gradeReadOnly");
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
     * <p>Set whether the grade of this rating component can be changed by the user.  
     * The default is false - it is NOT read-only, and therefore can be changed by the user.</p>
     */
    public void setGradeReadOnly(boolean gradeReadOnly) {
        this.gradeReadOnly = gradeReadOnly;
        this.gradeReadOnly_set = true;
    }
    
    /**
     * <p>Indicates whether the component will be rendered displaying the average grade.  
     * The default is false, the component will be rendered showing the user's rating 
     * (normal mode).</p>
     */
    @Property(name="inAverageMode", displayName="Average Mode", category="Appearance")
    private boolean inAverageMode = false;
    private boolean inAverageMode_set = false;

    /**
     * <p>Return true if the component is rendered displaying the average grade, 
     * false if component is rendered displaying the user's grade (normal mode).</p>
     */
    public boolean isInAverageMode() {
        if (this.inAverageMode_set) {
            return this.inAverageMode;
        }
        ValueExpression _vb = getValueExpression("inAverageMode");
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
     * <p>Set whether the component will be rendered displaying the average grade.  
     * The default is false, the component will be rendered showing the user's 
     * rating (normal mode).</p>
     */
    public void setInAverageMode(boolean inAverageMode) {
        this.inAverageMode = inAverageMode;
        this.inAverageMode_set = true;
    }
    
    /**
     * <p>Indicates whether a control to clear the user's rating should be displayed.
     * The default is true.</p>
     */
    @Property(name="includeClear", displayName="Include Clear", category="Appearance")
    private boolean includeClear = true;
    private boolean includeClear_set = true;

    /**
     * <p>Return true if a control to clear the user's rating is rendered, false if not.</p>
     */
    public boolean isIncludeClear() {
        if (this.includeClear_set) {
            return this.includeClear;
        }
        ValueExpression _vb = getValueExpression("includeClear");
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
     * <p>Set whether a control to clear the user's rating should be displayed.
     * The default is true.</p>
     */
    public void setIncludeClear(boolean includeClear) {
        this.includeClear = includeClear;
        this.includeClear_set = true;
    }
    
    /**
     * <p>Indicates whether a control to toggle the mode (to show the average rating or
     * the user's rating) should be rendered.  The default is false.</p>
     */
    @Property(name="includeModeToggle", displayName="Include ModeToggle", category="Appearance")
    private boolean includeModeToggle = false;
    private boolean includeModeToggle_set = false;

    /**
     * <p>Return true if a control to toggle the mode (to show the average rating or 
     * the user's rating) is rendered, false if not.</p>
     */
    public boolean isIncludeModeToggle() {
        if (this.includeModeToggle_set) {
            return this.includeModeToggle;
        }
        ValueExpression _vb = getValueExpression("includeModeToggle");
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
     * <p>Set whether a control to toggle the mode (to show the average rating or 
     * the user's rating) should be rendered.  The default is false.</p>
     */
    public void setIncludeModeToggle(boolean includeModeToggle) {
        this.includeModeToggle = includeModeToggle;
        this.includeModeToggle_set = true;
    }
    
    /**
     * <p>Indicates whether a control to allow the user to assign a "not interested" 
     * rating should be rendered.  The default is true.</p>
     */
    @Property(name="includeNotInterested", displayName="Include NotInterested", category="Appearance")
    private boolean includeNotInterested = true;
    private boolean includeNotInterested_set = true;

    /**
     * <p>Return true if a control to allow the user to assign a "not interested" 
     * rating is rendered, false if not.</p>
     */
    public boolean isIncludeNotInterested() {
        if (this.includeNotInterested_set) {
            return this.includeNotInterested;
        }
        ValueExpression _vb = getValueExpression("includeNotInterested");
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
     * <p>Set whether a control to allow the user to assign a "not interested" 
     * rating should be rendered.  The default is true.</p>
     */
    public void setIncludeNotInterested(boolean includeNotInterested) {
        this.includeNotInterested = includeNotInterested;
        this.includeNotInterested_set = true;
    }
    
    /**
     * <p>Indicates whether an area for hover or post-click acknowledeged text 
     * should be rendered.  The default is true.</p>
     */
    @Property(name="includeText", displayName="Include Text", category="Appearance")
    private boolean includeText = true;
    private boolean includeText_set = true;

    /**
     * <p>Return true if an area to show hover text or post-click acknowledged text 
     * is rendered, false if not.</p>
     */
    public boolean isIncludeText() {
        if (this.includeText_set) {
            return this.includeText;
        }
        ValueExpression _vb = getValueExpression("includeText");
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
     * <p>Set whether an area for hover or post-click acknowledged text 
     * should be rendered.  The default is true.</p>
     */
    public void setIncludeText(boolean includeText) {
        this.includeText = includeText;
        this.includeText_set = true;
    }    
    
    /**
     * <p>The maximum grade (number of "stars") this rating instance allows.  
     * There is no default, and so must be set.</p>
     */
    @Property(name="maxGrade", displayName="Maximum Grade", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int maxGrade = 0;
    private boolean maxGrade_set = false;

    /**
     * <p>Return the maximum grade (number of "stars") this rating instance allows.</p>
     */
    public int getMaxGrade() {
        if (this.maxGrade_set) {
            return this.maxGrade;
        }
        ValueBinding _vb = getValueBinding("maxGrade");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
	return 0;
    }

    /**
     * <p>Set the maximum grade (number of "stars") this rating instance allows.  
     * There is no default, and so must be set.</p>
     * 
     */
    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
        this.maxGrade_set = true;
    }
    
    /**
     * <p>Indicates whether the mode of this rating component can be changed by the user.
     * The default is false - it is NOT read-only, and therefore can be changed by the user.</p>
     */
    @Property(name="modeReadOnly", displayName="Mode Read Only", category="Appearance")
    private boolean modeReadOnly = false;
    private boolean modeReadOnly_set = false;

    /**
     * <p>Return true if the mode of this rating component can be changed by the user 
     * (via the mode toggle control), false if not</p>
     */
    public boolean isModeReadOnly() {
        if (this.modeReadOnly_set) {
            return this.modeReadOnly;
        }
        ValueExpression _vb = getValueExpression("modeReadOnly");
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
     * <p>Set whether the mode of this rating component can be changed by the user 
     * (via the mode toggle control).  The default is false - it is NOT read-only, 
     * and therefore can be changed by the user.</p>
     */
    public void setModeReadOnly(boolean modeReadOnly) {
        this.modeReadOnly = modeReadOnly;
        this.modeReadOnly_set = true;
    }
    
    /**
     * <p>The acknowledged texts to be used for the mode toggle control.  
     * The attribute's value must be a JavaServer Faces EL expression that 
     * evaluates to an array of <code>java.util.String</code>.  The first 
     * element of the returned array is the acknowledged text displayed after 
     * clicking the mode toggle control to preview the user's rating 
     * (normal mode).  The second element is the text displayed after clicking 
     * to preview the average rating (average mode).    Null can be specified 
     * as a member of the array.  There are no defaults.</p>
     */
    @Property(name="modeToggleAcknowledgedTexts", displayName="Mode Toggle Acknowledged Texts", category="Appearance")
    private String[] modeToggleAcknowledgedTexts = null;
    private boolean modeToggleAcknowledgedTexts_set = false;

    /**
     * <p>Returns the acknowledged texts that are used for the mode toggle 
     * control.  The first element of the returned array is the acknowledged 
     * text displayed after clicking on the mode toggle control to preview the 
     * user's rating (nomal mode).  The second element is the text displayed 
     * after clicking on the mode toggle control to preview the average 
     * rating (average mode).  If at least one of the texts has been specified 
     * but one has not, then null is returned in the array element for the 
     * missing text.  Returns null if no acknowledged texts have been 
     * specified.</p>
     */
    public String[] getModeToggleAcknowledgedTexts() {
        if (this.modeToggleAcknowledgedTexts_set) {
            return this.modeToggleAcknowledgedTexts;
        }
        ValueExpression _vb = getValueExpression("modeToggleAcknowledgedTexts");
        if (_vb != null) {
            return (String[]) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the acknowledged texts to be used for the mode toggle control.  
     * The first element of the array is the acknowledged text displayed after 
     * clicking on the mode toggle control to preview the user's rating (normal 
     * mode).  The second element is the text displayed after clicking to 
     * preview the average rating (average mode).    Null can be specified 
     * as a member of the array.</p>
     */
    public void setModeToggleAcknowledgedTexts(String[] modeToggleAcknowledgedTexts) {
        this.modeToggleAcknowledgedTexts = modeToggleAcknowledgedTexts;
        this.modeToggleAcknowledgedTexts_set = true;
    }    
    
    /**
     * <p>The hover texts to be used for the mode toggle control.  The 
     * attribute's value must be a JavaServer Faces EL expression that 
     * evaluates to an array of <code>java.util.String</code>.  The first 
     * element of the returned array is the hover text displayed when hovering 
     * over the mode toggle control to preview the user's rating (normal mode).
     * The second element is the text displayed when hovering to preview the 
     * average rating (average mode).    Null can be specified as a member of 
     * the array.  There are no defaults.</p>
     */
    @Property(name="modeToggleHoverTexts", displayName="Mode Toggle Hover Texts", category="Appearance")
    private String[] modeToggleHoverTexts = null;
    private boolean modeToggleHoverTexts_set = false;

    /**
     * <p>Returns the hover texts that are used for the mode toggle control.  
     * The first element of the returned array is the hover text displayed 
     * when hovering over the mode toggle control to preview the user's rating 
     * (nomal mode).  The second element is the text displayed when hovering 
     * to preview the average rating (average mode).  If at least one of the 
     * texts has been specified but one has not, then null is returned in the 
     * array element for the missing text.  Returns null if no hover texts 
     * have been specified.</p>
     */
    public String[] getModeToggleHoverTexts() {
        if (this.modeToggleHoverTexts_set) {
            return this.modeToggleHoverTexts;
        }
        ValueExpression _vb = getValueExpression("modeToggleHoverTexts");
        if (_vb != null) {
            return (String[]) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the hover texts to be used for the mode toggle control.  The 
     * first element of the array is the hover text displayed when hovering 
     * over the mode toggle control to preview the user's rating (normal mode).  
     * The second element is the text displayed when hovering to preview the 
     * average rating (normal mode).    Null can be specified as a member of 
     * the array.</p>
     */
    public void setModeToggleHoverTexts(String[] modeToggleHoverTexts) {
        this.modeToggleHoverTexts = modeToggleHoverTexts;
        this.modeToggleHoverTexts_set = true;
    }    

    /**
     * <p>The acknowledged text for the "not interested" control.  There is no default.</p>
     */
    @Property(name="notInterestedAcknowledgedText", displayName="Not Interested Acknowledged Text", category="Appearance")
    private String notInterestedAcknowledgedText = null;
    private boolean notInterestedAcknowledgedText_set = false;

    /**
     * <p>Returns the acknowledged text for the "not interested" control.  Returns null if not set.</p>
     */
    public String getNotInterestedAcknowledgedText() {
        if (this.notInterestedAcknowledgedText_set) {
	    return this.notInterestedAcknowledgedText;
	}
        ValueExpression _vb = getValueExpression("notInterestedAcknowledgedText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the acknowledged text for the "not interested" control.
     */
    public void setNotInterestedAcknowledgedText(String notInterestedAcknowledgedText) {
        this.notInterestedAcknowledgedText = notInterestedAcknowledgedText;
        this.notInterestedAcknowledgedText_set = true;
    }

    /**
     * <p>The hover text for the "not interested" control.  There is no default.</p>
     */
    @Property(name="notInterestedHoverText", displayName="Not Interested Hover Text", category="Appearance")
    private String notInterestedHoverText = null;
    private boolean notInterestedHoverText_set = false;

    /**
     * <p>Returns the hover text for the "not interested" control.  Returns null if not set.</p>
     */
    public String getNotInterestedHoverText() {
        if (this.notInterestedHoverText_set) {
	    return this.notInterestedHoverText;
	}
        ValueExpression _vb = getValueExpression("notInterestedHoverText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the hover text for the "not interested" control.
     */
    public void setNotInterestedHoverText(String notInterestedHoverText) {
        this.notInterestedHoverText = notInterestedHoverText;
        this.notInterestedHoverText_set = true;
    }
    
    /**
     * <p>Position of this element in the tabbing order of the current document. 
     * Tabbing order determines the sequence in which elements receive focus when 
     * the tab key is pressed. The value must be an integer between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;

    /**
     * <p>Return the position of this element in the tabbing order.</p>
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
     * <p>Set the position of this element in the tabbing order.</p>
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
        this.style = (String) _values[1];
        this.styleClass = (String) _values[2];        
        this.visible = ((Boolean) _values[3]).booleanValue();
        this.visible_set = ((Boolean) _values[4]).booleanValue();
        this.averageGrade = ((Double) _values[5]).doubleValue();
        this.averageGrade_set = ((Boolean) _values[6]).booleanValue();    
	this.clearAcknowledgedText = (String) _values[7];
        this.clearAcknowledgedText_set = ((Boolean) _values[8]).booleanValue();
	this.clearHoverText = (String) _values[9];
        this.clearHoverText_set = ((Boolean) _values[10]).booleanValue();
	this.gradeAcknowledgedText = (String) _values[11];
        this.gradeAcknowledgedText_set = ((Boolean) _values[12]).booleanValue();
	this.gradeHoverTexts = (String[]) _values[13];
        this.gradeHoverTexts_set = ((Boolean) _values[14]).booleanValue();
        this.gradeReadOnly = ((Boolean) _values[15]).booleanValue();
        this.gradeReadOnly_set = ((Boolean) _values[16]).booleanValue();
        this.inAverageMode = ((Boolean) _values[17]).booleanValue();
        this.inAverageMode_set = ((Boolean) _values[18]).booleanValue();
        this.includeClear = ((Boolean) _values[19]).booleanValue();
        this.includeClear_set = ((Boolean) _values[20]).booleanValue();
        this.includeModeToggle = ((Boolean) _values[21]).booleanValue();
        this.includeModeToggle_set = ((Boolean) _values[22]).booleanValue();
        this.includeNotInterested = ((Boolean) _values[23]).booleanValue();
        this.includeNotInterested_set = ((Boolean) _values[24]).booleanValue();
        this.includeText = ((Boolean) _values[25]).booleanValue();
        this.includeText_set = ((Boolean) _values[26]).booleanValue();
        this.maxGrade = ((Integer) _values[27]).intValue();
        this.maxGrade_set = ((Boolean) _values[28]).booleanValue();
        this.modeReadOnly = ((Boolean) _values[29]).booleanValue();
        this.modeReadOnly_set = ((Boolean) _values[30]).booleanValue();
	this.modeToggleAcknowledgedTexts = (String[]) _values[31];
        this.modeToggleAcknowledgedTexts_set = ((Boolean) _values[32]).booleanValue();
	this.modeToggleHoverTexts = (String[]) _values[33];
        this.modeToggleHoverTexts_set = ((Boolean) _values[34]).booleanValue();
	this.notInterestedAcknowledgedText = (String) _values[35];
        this.notInterestedAcknowledgedText_set = ((Boolean) _values[36]).booleanValue();
	this.notInterestedHoverText = (String) _values[37];
        this.notInterestedHoverText_set = ((Boolean) _values[38]).booleanValue();
        this.tabIndex = ((Integer) _values[39]).intValue();
        this.tabIndex_set = ((Boolean) _values[40]).booleanValue();
        this.autoSubmit = ((Boolean) _values[41]).booleanValue();
        this.autoSubmit_set = ((Boolean) _values[42]).booleanValue();
    }
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[43];
        _values[0] = super.saveState(_context);        
        _values[1] = this.style;
        _values[2] = this.styleClass;
        _values[3] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
	_values[5] = new Double(this.averageGrade);
        _values[6] = this.averageGrade_set ? Boolean.TRUE : Boolean.FALSE;
	_values[7] = this.clearAcknowledgedText;
        _values[8] = this.clearAcknowledgedText_set ? Boolean.TRUE : Boolean.FALSE;
	_values[9] = this.clearHoverText;
        _values[10] = this.clearHoverText_set ? Boolean.TRUE : Boolean.FALSE;
	_values[11] = this.gradeAcknowledgedText;
        _values[12] = this.gradeAcknowledgedText_set ? Boolean.TRUE : Boolean.FALSE;
	_values[13] = this.gradeHoverTexts;
        _values[14] = this.gradeHoverTexts_set ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.gradeReadOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.gradeReadOnly_set ? Boolean.TRUE : Boolean.FALSE;
        _values[17] = this.inAverageMode ? Boolean.TRUE : Boolean.FALSE;
        _values[18] = this.inAverageMode_set ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.includeClear ? Boolean.TRUE : Boolean.FALSE;
        _values[20] = this.includeClear_set ? Boolean.TRUE : Boolean.FALSE;
        _values[21] = this.includeModeToggle ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.includeModeToggle_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = this.includeNotInterested ? Boolean.TRUE : Boolean.FALSE;
        _values[24] = this.includeNotInterested_set ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = this.includeText ? Boolean.TRUE : Boolean.FALSE;
        _values[26] = this.includeText_set ? Boolean.TRUE : Boolean.FALSE;
	_values[27] = new Integer(this.maxGrade);
        _values[28] = this.maxGrade_set ? Boolean.TRUE : Boolean.FALSE;
        _values[29] = this.modeReadOnly ? Boolean.TRUE : Boolean.FALSE;
        _values[30] = this.modeReadOnly_set ? Boolean.TRUE : Boolean.FALSE;
	_values[31] = this.modeToggleAcknowledgedTexts;
        _values[32] = this.modeToggleAcknowledgedTexts_set ? Boolean.TRUE : Boolean.FALSE;
	_values[33] = this.modeToggleHoverTexts;
        _values[34] = this.modeToggleHoverTexts_set ? Boolean.TRUE : Boolean.FALSE;
	_values[35] = this.notInterestedAcknowledgedText;
        _values[36] = this.notInterestedAcknowledgedText_set ? Boolean.TRUE : Boolean.FALSE;
	_values[37] = this.notInterestedHoverText;
        _values[38] = this.notInterestedHoverText_set ? Boolean.TRUE : Boolean.FALSE;
	_values[39] = new Integer(this.tabIndex);
        _values[40] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[41] = this.autoSubmit ? Boolean.TRUE : Boolean.FALSE;
        _values[42] = this.autoSubmit_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
