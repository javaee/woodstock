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
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The JobStatus component is used to show the number of jobs currently running.
 * 
 * @deprecated See tld docs for more information on how to create a jobStatus
 * without using the jobStatus component
 */
@Component(type="com.sun.webui.jsf.JobStatus", 
    family="com.sun.webui.jsf.JobStatus", displayName="Job Status", tagName="jobStatus",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_job_status",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_job_status_props")
public class JobStatus extends ImageHyperlink {
    /**
     * Default constructor.
     */
    public JobStatus() {
        super();
        setRendererType("com.sun.webui.jsf.JobStatus");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.JobStatus";
    }

    public String getIcon() {
        String icon = super.getIcon();
        
        if (icon == null) {
            icon = ThemeImages.MASTHEAD_STATUS_ICON;
        }
        
        return icon;
    }
    
    public String getStyleClass() {
        String styleClass = super.getStyleClass();
        
        if (styleClass == null) {
            styleClass = ThemeStyles.MASTHEAD_PROGRESS_LINK;
        }
        Theme theme = ThemeUtilities.getTheme(
			      FacesContext.getCurrentInstance());        
        return theme.getStyleClass(styleClass);
    }
    
    public boolean isDisabled() {
        if (getNumJobs() == 0) {
            // always disable the hyperlink when 0 jobs
            return true;
        }
        
        return super.isDisabled();
    }
    
    public int getHeight() {
        return 17;
    }
    
    public int getWidth() {
        return 17;
    }
    
    public int getBorder() {
        return 0;
    }
    
    public String getAlign() {
        return "middle";        
    }
    
    public String getAlt() {
        String alt = super.getAlt();
        
        if (alt == null) {
            Theme theme = ThemeUtilities.getTheme(FacesContext.getCurrentInstance());
            alt = theme.getMessage("masthead.tasksRunningAltText");
        }
        return alt;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Specifies the position of the image with respect to its context.
     * Valid values are: bottom (the default); middle; top; left; right.</p>
     */
    @Property(name="onDblClick", isHidden=false, isAttribute=true)
    public String getOnDblClick() {
        return super.getOnDblClick();
    }
    
    // Hide textPosition
    @Property(name="textPosition", isHidden=true, isAttribute=false)
    public String getTextPosition() {
        return super.getTextPosition();
    }

    /**
     * <p>The number of currently executing jobs, displayed next to the job label.</p>
     */
    @Property(name="numJobs", displayName="Number of Jobs")
    private int numJobs = Integer.MIN_VALUE;
    private boolean numJobs_set = false;

    public int getNumJobs() {
        if (this.numJobs_set) {
            return this.numJobs;
        }
        ValueExpression _vb = getValueExpression("numJobs");
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
     * <p>The number of currently executing jobs, displayed next to the job label.</p>
     * @see #getNumJobs()
     */
    public void setNumJobs(int numJobs) {
        this.numJobs = numJobs;
        this.numJobs_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.numJobs = ((Integer) _values[1]).intValue();
        this.numJobs_set = ((Boolean) _values[2]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.numJobs);
        _values[2] = this.numJobs_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
