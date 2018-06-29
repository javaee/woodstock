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

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.sun.webui.jsf.component.WizardStep;

public class WizardStepListBase implements WizardStepList {

    private WizardModel wModel;
    private int currentStep;
    private String currentStepNumberString;

    public WizardStepListBase(WizardModel wModel) {
        this.wModel = wModel;
        iterate();
    }

    public String getCurrentStepNumberString() {
        return currentStepNumberString;
    }
    private static final String DOT = "."; //NOI18N
    private static final String SWBRACKET_OPEN = "["; //NOI18N
    private static final String SWBRACKET_CLOSE = "["; //NOI18N

    protected String formatStepNumber(int stepNumber) {
        return String.valueOf(stepNumber);
    }

    protected String formatSubstepNumber(int stepNumber, int substep) {
        return String.valueOf(stepNumber).concat(DOT).
                concat(String.valueOf(substep));
    }

    protected String formatBranch(String placeholderText) {
        return SWBRACKET_OPEN.concat(placeholderText).concat(SWBRACKET_CLOSE);
    }

    // Do this to set up the state of the list,
    // basically set up currentStep details.
    //
    private void iterate() {
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    public Iterator iterator() {

        return new Iterator() {

            private int stepNumber = 0;
            private int substep = 0;
            private String stepNumberString;
            private String placeholderText;
            private boolean isBranch;
            private Iterator wizardStepIterator = wModel.getWizardStepIterator();

            public boolean hasNext() {
                return wizardStepIterator.hasNext();
            }

            public Object next() throws NoSuchElementException {

                if (isBranch) {
                    // Log too
                    throw new NoSuchElementException(
                            "No more steps after branch"); //NOI18N
                }

                boolean isCurrentStep = false;

                try {
                    WizardStep step = (WizardStep) wizardStepIterator.next();
                    boolean isSubstep = wModel.isSubstep(step);
                    isBranch = wModel.isBranch(step);
                    placeholderText = null;
                    // A substep cannot be the first step.
                    //
                    if (isSubstep) {
                        ++substep;
                        stepNumberString =
                                formatSubstepNumber(stepNumber, substep);
                    } else if (isBranch) {
                        ++stepNumber;
                        substep = 0;
                        /*
                        placeholderText =
                        formatBranch(wModel.getPlaceholderText(step));
                         */
                        stepNumberString = null;
                    } else {
                        ++stepNumber;
                        substep = 0;
                        stepNumberString = formatStepNumber(stepNumber);
                    }
                    isCurrentStep = wModel.isCurrentStep(step);
                    if (isCurrentStep) {
                        currentStep = stepNumber;
                        currentStepNumberString = stepNumberString;
                    }
                    return new WizardStepListItemBase(step,
                            stepNumberString, isCurrentStep,
                            isSubstep, isBranch,
                            placeholderText,
                            wModel.canGotoStep(step));


                } catch (Exception e) {
                    NoSuchElementException nse = new NoSuchElementException();
                    nse.initCause(e);
                    throw nse;
                }
            }

            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }
        };
    }
}
