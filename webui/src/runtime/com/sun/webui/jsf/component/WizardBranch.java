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
/*
 * $Id: WizardBranch.java,v 1.1 2007-02-16 01:24:52 bob_yennaco Exp $
 */

package com.sun.webui.jsf.component;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import com.sun.webui.jsf.event.WizardEventListener;

/*
 * The WizardBranch represents a step in a Wizard sequence that indicates
 * that the steps following the branch, cannot be determined until the branch is
 * reached. Until that time, place holder text appears in the step list
 * at the location of the WizardBranch, that describes the conditions
 * that determine the steps that follow.
 */
@Component(type="com.sun.webui.jsf.WizardBranch",
    family="com.sun.webui.jsf.WizardBranch", displayName="WizardBranch",
    tagName="wizardBranch",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_wizard_branch",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_wizard_branch_props")
public class WizardBranch extends WizardStep implements NamingContainer {

    /**
     * Construct a new <code>WizardBranchBase</code>.
     */
    public WizardBranch() {
        super();
	// No renderertype
        //setRendererType("com.sun.webui.jsf.WizardBranch");
    }

    /**
     * Return the family for this component, <code>
     * com.sun.webui.jsf.WizardBranch</code>.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.WizardBranch";
    }

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    // Hide detail
    @Property(isHidden=true, isAttribute=false)
    public String getDetail() {
        return super.getDetail();
    }
    
    // Hide eventListener
    @Property(isHidden=true, isAttribute=false)
    public WizardEventListener getEventListener() {
        return super.getEventListener();
    }
    
    // Hide finish
    @Property(isHidden=true, isAttribute=false)
    public boolean isFinish() {
        return super.isFinish();
    }
    
    // Hide help
    @Property(isHidden=true, isAttribute=false)
    public String getHelp() {
        return super.getHelp();
    }
    
    // Hide onCancel
    @Property(isHidden=true, isAttribute=false)
    public String getOnCancel() {
        return super.getOnCancel();
    }
    
    // Hide onClose
    @Property(isHidden=true, isAttribute=false)
    public String getOnClose() {
        return super.getOnClose();
    }
    
    // Hide onFinish
    @Property(isHidden=true, isAttribute=false)
    public String getOnFinish() {
        return super.getOnFinish();
    }
    
    // Hide onHelpTab
    @Property(isHidden=true, isAttribute=false)
    public String getOnHelpTab() {
        return super.getOnHelpTab();
    }
    
    // Hide onNext
    @Property(isHidden=true, isAttribute=false)
    public String getOnNext() {
        return super.getOnNext();
    }
    
    // Hide onPrevious
    @Property(isHidden=true, isAttribute=false)
    public String getOnPrevious() {
        return super.getOnPrevious();
    }
    
    // Hide onStepLink
    @Property(isHidden=true, isAttribute=false)
    public String getOnStepLink() {
        return super.getOnStepLink();
    }
    
    // Hide onStepsTab
    @Property(isHidden=true, isAttribute=false)
    public String getOnStepsTab() {
        return super.getOnStepsTab();
    }
    
    // Hide results
    @Property(isHidden=true, isAttribute=false)
    public boolean isResults() {
        return super.isResults();
    }
    
    // Hide summary
    @Property(isHidden=true, isAttribute=false)
    public String getSummary() {
        return super.getSummary();
    }
    
    // Hide title
    @Property(isHidden=true, isAttribute=false)
    public String getTitle() {
        return super.getTitle();
    }

    // placeholderText
    /**
     * Text that describes to users what happens when they make a selection in 
     * the step that sets up the branch.  This text is displayed in the Steps 
     * pane when that step is initially displayed, before the user proceeds 
     * through the step.
     */
    @Property(name="placeholderText")
    private String placeholderText = null;

    /**
     * Text that describes to users what happens when they make a selection in 
     * the step that sets up the branch.  This text is displayed in the Steps 
     * pane when that step is initially displayed, before the user proceeds 
     * through the step.
     */
    public String getPlaceholderText() {
        if (this.placeholderText != null) {
            return this.placeholderText;
        }
        ValueExpression _vb = getValueExpression("placeholderText");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Text that describes to users what happens when they make a selection in 
     * the step that sets up the branch.  This text is displayed in the Steps 
     * pane when that step is initially displayed, before the user proceeds 
     * through the step.
     * @see #getPlaceholderText()
     */
    public void setPlaceholderText(String placeholderText) {
        this.placeholderText = placeholderText;
    }

    // taken
    /**
     * The taken attribute is used to evaluate whether the steps of
     * the branch are displayed. If taken is true, the branch is followed, and 
     * the child <code>webuijsf:wizardBranchSteps</code> tags are evaluated.
     * The taken attribute should be a JavaServer Faces EL expression that
     * could use the user's response in a previous step to determine whether
     * the branch should be followed.
     */
    @Property(name="taken")
    private boolean taken = false;
    private boolean taken_set = false;

    /**
     * The taken attribute is used to evaluate whether the steps of
     * the branch are displayed. If taken is true, the branch is followed, and 
     * the child <code>webuijsf:wizardBranchSteps</code> tags are evaluated.
     * The taken attribute should be a JavaServer Faces EL expression that
     * could use the user's response in a previous step to determine whether
     * the branch should be followed.
     */
    public boolean isTaken() {
        if (this.taken_set) {
            return this.taken;
        }
        ValueExpression _vb = getValueExpression("taken");
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
     * The taken attribute is used to evaluate whether the steps of
     * the branch are displayed. If taken is true, the branch is followed, and 
     * the child <code>webuijsf:wizardBranchSteps</code> tags are evaluated.
     * The taken attribute should be a JavaServer Faces EL expression that 
     * could use the user's response in a previous step to determine whether 
     * the branch should be followed.
     * @see #isTaken()
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
        this.taken_set = true;
    }

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.placeholderText = (String) _values[1];
        this.taken = ((Boolean) _values[2]).booleanValue();
        this.taken_set = ((Boolean) _values[3]).booleanValue();
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[4];
        _values[0] = super.saveState(_context);
        _values[1] = this.placeholderText;
        _values[2] = this.taken ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.taken_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
