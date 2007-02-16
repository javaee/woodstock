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

package com.sun.webui.jsf.model;

import com.sun.webui.jsf.component.WizardStep;

/**
 * Defines the interface of an object used to represent a WizardStep
 * for use in a WizardStepList, and for rendering a step list in the
 * Steps pane of a rendered Wizard component.
 */
public interface WizardStepListItem {

    /**
     * Returns true if this step is a substep.
     */
    public boolean isSubstep();

    /**
     * Returns true if this step is a branch step.
     */
    public boolean isBranch();

    /**
     * Returns the text that should be displayed describing this branch step.
     */
    public String getPlaceholderText();

    /**
     * Returns the number of this step in the list of steps.
     */
    public String getStepNumberString();

    /**
     * Returns true if this step is the current step.
     */
    public boolean isCurrentStep();

    /**
     * If <code>currentStep</code> is true this WizardStepListItem represents
     * the current step in the step list. If false, it is not the current
     * step.
     *
     * @param currentStep true if this WizardStepListItem is the current step.
     */
    public void setCurrentStep(boolean currentStep);

    /**
     * Returns true if this step can be navigated to out of sequence.
     */
    public boolean canGotoStep();

    /**
     * If <code>canGotoStep</code> is true, this step can be navigated to
     * out of sequence.
     *
     * @param canGotoStep If true this step can be reached out of sequence.
     */
    public void setCanGotoStep(boolean canGotoStep);

    /**
     * Return the WizardStep instance that this WizardStepListItem represents.
     */
    public WizardStep getStep();

    /**
     * Set the WizardStep instance this WizardStepListItem represents.
     *
     * @param step The WizardStep instance this WizardStepListItem represents.
     */
    public void setStep(WizardStep step);
}
