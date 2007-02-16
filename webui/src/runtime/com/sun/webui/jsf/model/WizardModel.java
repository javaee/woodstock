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

import java.util.Iterator;
import javax.faces.component.StateHolder;

import com.sun.webui.jsf.event.WizardEvent;
import com.sun.webui.jsf.event.WizardEventListener;
import com.sun.webui.jsf.component.Wizard;
import com.sun.webui.jsf.component.WizardStep;

/**
 * Defines an interface for control a sequence of WizardStep components
 * through a Wizard component.
 * <p>
 * A Wizard component delegates to a WizardModel instance for control
 * and navigation through a set WizardStep instances.
 * </p>
 */
public interface WizardModel extends StateHolder {

    /**
     * This method is called when the wizard instance has
     * completed assembling any child components.
     *
     * @param wizard The Wizard instance owning this model instance.
     */
    public void initialize(Wizard wizard);

    // It's not clear how the model should be allowed to abort
    // the wizard at this point. It could throw an exception
    // that is caught by the wizard broadcasting this event
    // fires a cancel or fatal event, allowing the model
    // to "clean" up. Then using a message in the exception
    // the wizard propagates that messsage to a log or the user, how ?
    //
    /**
     * Handle the following
     * <code>WizardEvent</code> events.
     * <ul>
     * <li>WizardEvent.CANCEL</li>
     * <li>WizardEvent.CLOSE</li>
     * <li>WizardEvent.FINISH</li>
     * <li>WizardEvent.GOTOSTEP</li>
     * <li>WizardEvent.HELPTAB</li>
     * <li>WizardEvent.NEXT</li>
     * <li>WizardEvent.PREVIOUS</li>
     * <li>WizardEvent.STEPSTAB</li>
     * </ul>
     */
    public boolean handleEvent(WizardEvent event);

    /**
     * Based on the current state of the Wizard, return an Iterator
     * of the current sequence of WizardSteps.
     */
    public Iterator getWizardStepIterator();

    /**
     * Return a WizardStepList of WizardStepListItem instances.
     */
    public WizardStepList getWizardStepList();

    /**
     * Return the first WizardStep instance of the sequence.
     */
    public WizardStep getFirstStep();

    /**
     * Return the WizardStep instance preceding the specified step.
     * If there is no previous step return null.
     *
     * @param step The step following the returned WizardStep.
     */
    public WizardStep getPreviousStep(WizardStep step);

    /**
     * Return the WizardStep instance following the specified step.
     *
     * @param step The step preceding the returned WizardStep.
     */
    public WizardStep getNextStep(WizardStep step);

    /**
     * Return the step currently being preformed.
     */
    public WizardStep getCurrentStep();

    /**
     * Return true if any of the steps have step help.
     * If any of the steps have step help, this method should return
     * true, unless no step help should be shown for the wizard.
     * This method may only be called once.
     */
    public boolean hasStepHelp();

    /**
     * Return true if step is the current step, else false.
     *
     * @param step The step to check.
     */
    public boolean isCurrentStep(WizardStep step);

    /**
     * Return true if step is the finish step, else false.
     * The finish step has a button called "Finish" instead
     * of "Next". The finish step performs the task based on
     * the data collected in previous steps.
     *
     * @param step The step to check.
     */
    public boolean isFinishStep(WizardStep step);

    /**
     * Return true if step is the results step, else false.
     * The Results step follows the Finish step and displays
     * only  a "Close" button. It displayed results of the task
     * performed in the Finish step.
     *
     * @param step The step to check.
     */
    public boolean isResultsStep(WizardStep step);

    /**
     * Return true if step is a baranching step, else false.
     * A branching step acts as a step "placeholder" and informs
     * the user that the steps following this step are determined by
     * the data entered in this or previous steps. Text should be
     * provided from the <code>getPlaceholderText</code> for this
     * step, describing the branch.
     *
     * @param step The step to check.
     */
    public boolean isBranch(WizardStep step);

    /**
     * Return a description of this branch step.
     *
     * @param step A branching step.
     */
    public String getPlaceholderText(WizardStep step);

    /**
     * Return true if step is a substep step, else false.
     * A substep is a step or one of a series of substeps,
     * that is the same in every instance of this wizard.
     * Unlike the branch step, substep sequences are always the same
     * but may or may not be performed based on previous steps.
     *
     * @param step The step to check.
     */
    public boolean isSubstep(WizardStep step);

    /**
     * Return true if the user can navigate to this step out of seqence, else
     * false.
     * Typically this method is called to determine if a previous
     * step should be rendered such that the user can select it 
     * and navigate back to that step. Its possible that some
     * wizards may also allow forward navigation.
     *
     * @param step The step to check.
     */
    public boolean canGotoStep(WizardStep step);

    /**
     * Return true if the previous button should be disabled
     * for this step, else false. Typically the first step of a
     * sequence should return true, since there usually isn't a
     * step before the first step.
     *
     * @param step The step to check.
     */
    public boolean isPreviousDisabled(WizardStep step);

    /**
     * Return true if the next button should be disabled
     * for this step, else false.
     *
     * @param step The step to check.
     */
    public boolean isNextDisabled(WizardStep step);

    /**
     * Return true if the finish button should be disabled
     * for this step, else false.
     *
     * @param step The step to check.
     */
    public boolean isFinishDisabled(WizardStep step);

    /**
     * Return true if the cancel button should be disabled
     * for this step, else false.
     *
     * @param step The step to check.
     */
    public boolean isCancelDisabled(WizardStep step);

    /**
     * Return true if the close button should be disabled
     * for this step, else false.
     *
     * @param step The step to check.
     */
    public boolean isCloseDisabled(WizardStep step);

    /**
     * Return true if the previous button should be rendered
     * for this step, else false. Typically this method returns
     * true for all steps except for steps that are results steps.
     *
     * @param step The step to check.
     */
    public boolean hasPrevious(WizardStep step);

    /**
     * Return true if the next button should be rendered
     * for this step, else false. Typically this method returns
     * true for all steps except for steps that are finish or
     * results steps.
     *
     * @param step The step to check.
     */
    public boolean hasNext(WizardStep step);

    /**
     * Return true if the cancel button should be rendered
     * for this step, else false.  Typically this method returns
     * true for all steps except for steps that are results steps.
     *
     * @param step The step to check.
     */
    public boolean hasCancel(WizardStep step);

    /**
     * Return true if the close button should be rendered
     * for this step, else false. Typically this method returns
     * true only for the results step.
     *
     * @param step The step to check.
     */
    public boolean hasClose(WizardStep step);

    /**
     * Return true if the finish button should be rendered
     * for this step, else false. Typically this method returns
     * true only for the finish step.
     *
     * @param step The step to check.
     */
    public boolean hasFinish(WizardStep step);

    /**
     * Return true if <code>processValidators()</code> should be called
     * for the current step. The <code>event</code> argument is the event 
     * that precipitated this validate call, one of:
     * <ul>
     * <li>WizardEvent.CANCEL</li>
     * <li>WizardEvent.CLOSE</li>
     * <li>WizardEvent.FINISH</li>
     * <li>WizardEvent.GOTOSTEP</li>
     * <li>WizardEvent.HELPTAB</li>
     * <li>WizardEvent.NEXT</li>
     * <li>WizardEvent.PREVIOUS</li>
     * <li>WizardEvent.STEPSTAB</li>
     * <li>WizardEvent.NOEVENT</li>
     * </ul>
     *
     * @param event The event that precipitated this call.
     * @param prematureRender Is true if rendering is occuring before
     * RENDER_RESPONSE phase.
     */
    public boolean validate(int event, boolean prematureRender);

    /**
     * Return true if <code>processUpdates()</code> should be called
     * for the current step. The <code>event</code> argument is the event 
     * that precipitated this update call, one of:
     * <ul>
     * <li>WizardEvent.CANCEL</li>
     * <li>WizardEvent.CLOSE</li>
     * <li>WizardEvent.FINISH</li>
     * <li>WizardEvent.GOTOSTEP</li>
     * <li>WizardEvent.HELPTAB</li>
     * <li>WizardEvent.NEXT</li>
     * <li>WizardEvent.PREVIOUS</li>
     * <li>WizardEvent.STEPSTAB</li>
     * <li>WizardEvent.NOEVENT</li>
     * </ul>
     *
     * @param event The event that precipitated this call.
     * @param prematureRender Is true if rendering is occuring before
     * RENDER_RESPONSE phase.
     */
    public boolean update(int event, boolean prematureRender);

    /**
     * Return true if <code>processDecodes()</code> should be called
     * for the current step. The <code>event</code> argument is the event 
     * that precipitated this decode call, one of:
     * <ul>
     * <li>WizardEvent.CANCEL</li>
     * <li>WizardEvent.CLOSE</li>
     * <li>WizardEvent.FINISH</li>
     * <li>WizardEvent.GOTOSTEP</li>
     * <li>WizardEvent.HELPTAB</li>
     * <li>WizardEvent.NEXT</li>
     * <li>WizardEvent.PREVIOUS</li>
     * <li>WizardEvent.STEPSTAB</li>
     * <li>WizardEvent.NOEVENT</li>
     * </ul>
     *
     * @param event The event that precipitated this call.
     * @param prematureRender Is true if rendering is occuring before
     * RENDER_RESPONSE phase.
     */
    public boolean decode(int event, boolean prematureRender);

    /**
     * Return true if the wizard has completed and there are no
     * more steps for the user to complete, else false. Typically
     * this informs the wizard that there is nothing more to render.
     * This may cause a popup wizard to be dismissed or an inline
     * wizard to navigate to some other page.
     */
    public boolean isComplete();

    /**
     * Called to inform the model that no more references will be made to this
     * model instance.
     */
    public void complete();

}
