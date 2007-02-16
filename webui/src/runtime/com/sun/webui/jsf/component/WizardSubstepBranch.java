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
 * $Id: WizardSubstepBranch.java,v 1.1 2007-02-16 01:25:26 bob_yennaco Exp $
 */

package com.sun.webui.jsf.component;

import javax.el.ValueExpression;

import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import com.sun.webui.jsf.event.WizardEventListener;

/**
 * The WizardSubstepBranch component represents a conditional branch in a
 * wizard step sequence. The steps that comprise a substep branch are always
 * followed by the same sequence of steps, in the main sequence, whether
 * the substep branch is taken or not. The steps in a substep branch
 * are numbered differently that the steps in the main sequence.
 * For example if the substep branch occurs at step 2, then the
 * substeps are numbered as 2.1, 2.2, etc..
 */
@Component(type="com.sun.webui.jsf.WizardSubstepBranch", 
    family="com.sun.webui.jsf.WizardSubstepBranch", 
    displayName="WizardSubstepBranch", tagName="wizardSubstepBranch",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_wizard_substep_branch",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_wizard_substep_branch_props")
public class WizardSubstepBranch extends WizardStep implements NamingContainer {

    /**
     * Construct a new <code>WizardSubstepBranchBase</code>.
     */
    public WizardSubstepBranch() {
        super();
    }

    /**
     * Return the family for this component, <code>
     * com.sun.webui.jsf.WizardSubstepBranch</code>.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.WizardSubstepBranch";
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

    // taken
    /**
     * The taken attribute is used to evaluate whether the
     * steps of this branch sequence are displayed. If taken is true, 
     * the child components included in the <code>webuijsf:wizardStep</code> 
     * tags within this <code>webuijsf:wizardSubstepBranch</code>
     * tag are rendered. The taken attribute should be
     * a JavaServer Faces EL expression that uses the user's response in
     * a previous step to determine whether the branch sequence should be
     * rendered.
     */
    @Property(name="taken")
    private boolean taken = false;
    private boolean taken_set = false;

    /**
     * The taken attribute is used to evaluate whether the
     * steps of this branch sequence are displayed. If taken is true, 
     * the child components included in the <code>webuijsf:wizardStep</code> 
     * tags within this <code>webuijsf:wizardSubstepBranch</code>
     * tag are rendered. The taken attribute should be
     * a JavaServer Faces EL expression that uses the user's response in
     * a previous step to determine whether the branch sequence should be
     * rendered.
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
     * The taken attribute is used to evaluate whether the
     * steps of this branch sequence are displayed. If taken is true, 
     * the child components included in the <code>webuijsf:wizardStep</code> 
     * tags within this <code>webuijsf:wizardSubstepBranch</code>
     * tag are rendered. The taken attribute should be
     * a JavaServer Faces EL expression that uses the user's response in
     * a previous step to determine whether the branch sequence should be
     * rendered.
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
        this.taken = ((Boolean) _values[1]).booleanValue();
        this.taken_set = ((Boolean) _values[2]).booleanValue();
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[3];
        _values[0] = super.saveState(_context);
        _values[1] = this.taken ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.taken_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
