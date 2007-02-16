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

public class WizardStepListItemBase implements WizardStepListItem {
    
    String stepNumberString;
    boolean substep;
    boolean branch;
    String placeholderText;
    boolean currentStep;
    boolean canGotoStep;
    WizardStep step;

    WizardStepListItemBase(WizardStep step, String stepNumberString,
	    boolean currentStep, boolean substep, boolean branch,
	    String placeholderText, boolean canGotoStep) {
	this.step = step;
	this.stepNumberString= stepNumberString;
	this.currentStep = currentStep;
	this.substep = substep;
	this.branch = branch;
	this.placeholderText = placeholderText;
	this.canGotoStep = canGotoStep;
    }
    public boolean isSubstep() {
	return substep;
    }
    public boolean isBranch() {
	return branch;
    }
    public String getPlaceholderText() {
	return placeholderText;
    }
    public boolean isCurrentStep() {
	return currentStep;
    }
    public void setCurrentStep(boolean currentStep) {
	this.currentStep = currentStep;
    }
    public boolean canGotoStep() {
	return canGotoStep;
    }
    public void setCanGotoStep(boolean canGotoStep) {
	this.canGotoStep = canGotoStep;
    }
    public WizardStep getStep() {
	return step;
    }
    public void setStep(WizardStep step) {
	this.step = step;
    }
    public String getStepNumberString() {
	return stepNumberString;
    }
}
