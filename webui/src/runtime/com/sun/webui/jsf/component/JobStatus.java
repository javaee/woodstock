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
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ThemeUtilities;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * The JobStatus component is used to show the number of jobs currently running.
 */
@Component(type = "com.sun.webui.jsf.JobStatus", family = "com.sun.webui.jsf.JobStatus",
displayName = "Job Status", tagName = "jobStatus",
helpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_job_status",
propertiesHelpKey = "projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_job_status_props")
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
    @Override
    public String getFamily() {
        return "com.sun.webui.jsf.JobStatus";
    }

    @Override
    public String getIcon() {
        String icon = super.getIcon();

        if (icon == null) {
            icon = ThemeImages.MASTHEAD_STATUS_ICON;
        }

        return icon;
    }

    @Override
    public String getStyleClass() {
        String styleClass = super.getStyleClass();

        if (styleClass == null) {
            styleClass = ThemeStyles.MASTHEAD_PROGRESS_LINK;
        }
        Theme theme = ThemeUtilities.getTheme(
                FacesContext.getCurrentInstance());
        return theme.getStyleClass(styleClass);
    }

    @Override
    public boolean isDisabled() {
        if (getNumJobs() == 0) {
            // always disable the hyperlink when 0 jobs
            return true;
        }

        return super.isDisabled();
    }

    @Override
    public int getHeight() {
        return 17;
    }

    @Override
    public int getWidth() {
        return 17;
    }

    @Override
    public int getBorder() {
        return 0;
    }

    @Override
    public String getAlign() {
        return "middle";
    }

    @Override
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
    @Property(name = "onDblClick", isHidden = false, isAttribute = true)
    @Override
    public String getOnDblClick() {
        return super.getOnDblClick();
    }

    // Hide textPosition
    @Property(name = "textPosition", isHidden = true, isAttribute = false)
    @Override
    public String getTextPosition() {
        return super.getTextPosition();
    }
    /**
     * <p>The number of currently executing jobs, displayed next to the job label.</p>
     */
    @Property(name = "numJobs", displayName = "Number of Jobs")
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
    @Override
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.numJobs = ((Integer) _values[1]).intValue();
        this.numJobs_set = ((Boolean) _values[2]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    @Override
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = new Integer(this.numJobs);
        _values[2] = this.numJobs_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
