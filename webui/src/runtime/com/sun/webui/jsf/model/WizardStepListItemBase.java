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
        this.stepNumberString = stepNumberString;
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
