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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.context.FacesContext;

import com.sun.webui.jsf.event.WizardEvent;
import com.sun.webui.jsf.event.WizardEventListener;
import com.sun.webui.jsf.component.Wizard;
import com.sun.webui.jsf.component.WizardBranch;
import com.sun.webui.jsf.component.WizardBranchSteps;
import com.sun.webui.jsf.component.WizardStep;
import com.sun.webui.jsf.component.WizardSubstepBranch;

/**
 * <code>WizardModelBase</code> is the default 
 * {@link WizardModel WizardModel} instance used by the
 * {@link Wizard Wizard}.
 * This class's behavior supports wizard functionality as defined
 * by "Web Application Guidelines - Version 3.0". The support for a
 * step list as defined by the guidelines is encapsulated in 
 * {@link WizardStepListBase WizardStepListBase}. 
 * This class maintains state, controls navigation, and expects the
 * existence of <code>finish</code> and <code>results</code> wizard steps.
 * In addition this class can control whether or not steps should participate
 * in JSF lifecycle phases, see 
 * {@link WizardModelBase#decode(int, boolean) decode}, 
 * {@link WizardModelBase#validate(int, boolean) validate}, and
 * {@link WizardModelBase#update(int, boolean) update}.
 * <p>
 * <code>WizardModelBase</code> expects a hierachy of
 * {@link WizardStep WizardStep}, {@link WizardBranch WizardBranch}, 
 * {@link WizardBranchSteps WizardBranchSteps}, and
 * {@link WizardSubstepBranch WizardSubstepBranch}
 * components. This hierarchy can be specified as children of the
 * {@link Wizard Wizard} component or as a list returned by
 * {@link Wizard#getSteps() Wizard.getSteps()}. If
 * {@link Wizard#getSteps() getSteps} does not
 * return null, the steps represented by that value takes precedence over the
 * existence of {@link Wizard Wizard} child components.
 * </p>
 * <p>
 * The {@link Wizard Wizard} component calls the {@link #initialize(Wizard)
 * initialize} method twice during the request lifecycle. Once during 
 * the RESTORE_VIEW phase, and once during the RENDER_RESPONSE phase.
 * This is necessary
 * so the model can re-evaluate the steps if changes have occured during
 * request processing or in the INVOKE_APPLICATION phase, which occur before
 * the RENDER_RESPONSE phase. On the first display of a wizard, the 
 * {@link #initialize(Wizard) initialize} method is only called once
 * during the RENDER_RESPONSE phase.</br>
 * Note that a call to initialize during RESTORE_VIEW phase is only relevant
 * when the application is configured in client side state saving mode.
 * </p>
 * <p>
 * When the wizard receives events due to a user clicking one of the navigation
 * components, it calls {@link WizardModelBase#handleEvent(WizardEvent)
 * handleEvent} with the corresponding event and data.
 * </p>
 * <p>
 * The wizard will call the {@link WizardModelBase#complete()
 * complete} method to indicate
 * to the model that it considers the wizard session to be over and will no
 * longer reference it during this wizard session, 
 * and that it is free to deallocate any resources.
 * </p>
 */
public class WizardModelBase implements WizardModel {


    /**
     * The current step sequence of {@link WizardStep WizardStep} instances.
     */
    private ArrayList wizardSteps;
    /**
     * The current model state.
     */
    private WizardState wizardState;
    /**
     * The current step list of
     * {@link WizardStepListItem WizardStepListItem} instances.
     */
    private WizardStepList wizardStepList;

    /**
     * Construct a <code>WizardModelBase</code> instance.
     * The <code>initialize</code> method must be called with the
     * <code>wizard</code> instance when the step children are
     * available. The <code>Wizard</code> instance will call this method
     * at the appropriate time.
     */
    public WizardModelBase() {
	super();
	wizardState = new WizardState();
    }

    /**
     * Initialize the current set of WizardSteps.
     * This method is called by the {@link Wizard Wizard} twice. Once
     * during RETORE_VIEW phase, and once during RENDER_RESPONSE phase.
     * This sequence affords the model the opportunity to modify its
     * state due to changes that may have occured during request processing.
     * </br>
     * Note that a call during RESTORE_VIEW phase is only relevant
     * when the application is configured in client side state saving mode.
     *
     * @param wizard The Wizard component.
     */
    public void initialize(Wizard wizard) {


	wizardSteps = new ArrayList();

	// Steps can be obtained from both "getSteps" or 
	// as children of Wizard. This model uses one or the
	// other. If wizard.getSteps() does not return null
	// it uses that list, if it returns null it checks for 
	// wizard children and uses that list.
	//
	List steps = (List)wizard.getSteps();
	if (steps == null) {
	    steps = (List)wizard.getChildren();
	}
	ListIterator childIterator = steps.listIterator();
	buildStepList(childIterator, wizardSteps);
	wizardStepList = new WizardStepListBase(this);
    }

    // Call recursively to collect substep and branch children
    //
    /**
     * This method builds the current step sequence based on the state
     * of the wizard.
     * The current sequence is determined by the values returned from calls to
     * the <code>isTaken<code> method on {@link WizardBranch WizardBranch},
     * {@link WizardBranchSteps WizardBranchSteps} and
     * {@link WizardSubstepBranch WizardSubstepBranch} instances in the
     * step hierarchy.</br>
     * The current step sequence is constructed according to the 
     * "Web Application Guidelines - Version 3.0".</br>
     * For example if a {@link WizardBranch WizardBranch} is encountered,
     * the step sequence will end with this step. According to the
     * guidelines, a branch indicates that proceeding steps cannot be
     * determined until data is collected before this branch step is
     * reached. When the step hierarchy is evaluated again and the branch
     * isTaken method returns true, then the steps for that particular branch
     * are added to the sequence.
     * <p>
     * This method also determines if there is step help available.</br>
     * </p>
     *
     * @param childIterator contains all the steps of the wizard.
     * @param wizardSteps ArrayList to contain the steps that are in the
     * current sequence.
     */
    protected void buildStepList(ListIterator childIterator,
	ArrayList wizardSteps) {

	    Object step = null;
	    while (childIterator.hasNext()) {
		step = childIterator.next();
		if (step instanceof WizardBranch) {
		    WizardBranch branch = (WizardBranch)step;
		    if (branch.isTaken()) {
			buildStepList(branch.getChildren().listIterator(),
			    wizardSteps);
		    } else {
			// If the branch hasn't been taken then
			// this is the last step until it is taken.
			//
			wizardSteps.add(step);
			break;
		    }
		} else
		if (step instanceof WizardBranchSteps) {
		    WizardBranchSteps branchSteps = (WizardBranchSteps)step;
		    if (branchSteps.isTaken()) {
			buildStepList(branchSteps.getChildren().listIterator(),
			    wizardSteps);
		    }
		} else
		if (step instanceof WizardSubstepBranch) {
		    WizardSubstepBranch substep = (WizardSubstepBranch)step;
		    if (substep.isTaken()) {
			buildStepList(substep.getChildren().listIterator(),
			    wizardSteps);
		    }
		} else
		if (step instanceof WizardStep) {
		    // If even one WizardStep has help, show help
		    //
		    if (wizardState.getHasStepHelp() == null) {
			if (((WizardStep)step).getHelp() != null) {
			    wizardState.setHasStepHelp(Boolean.TRUE);
			}
		    }
		    wizardSteps.add(step);
		}
	    }
    }

    /**
     * Based on the current state of the Wizard, return an Iterator
     * of the current sequence of {@link WizardStep WizardStep} instances.
     */
    public Iterator getWizardStepIterator() {
	return wizardSteps.iterator();
    }

    /**
     * Return a {@link WizardStepList WizardStepList} of
     * {@link WizardStepListItem WizardStepListItem} instances.
     */
    public WizardStepList getWizardStepList() {
	return wizardStepList;
    }

    /**
     * Return the first {@link WizardStep WizardStep} instance.
     */
    public WizardStep getFirstStep() {
	return (WizardStep)wizardSteps.get(0);
    }

    /**
     * Return the last {@link WizardStep WizardStep} instance.
     * The step that is returned is dependent on the state of the
     * wizard. It may only be the last step at this point in time.
     * At a later point in time, it may be a different step, due to
     * the existence of branch steps.
     */
    public WizardStep getLastStep() {
	return (WizardStep)wizardSteps.get(wizardSteps.size() - 1);
    }

    /**
     * Return the index into an <code>ArrayList</code> of
     * {@link WizardStep WizardStep} instances, for the step with
     * <code>id</code>.
     *
     * @param id The id of a {@link WizardStep WizardStep} instance.
     */
    protected int getStepIndex(String id) {

	for (int i = 0; i < wizardSteps.size(); ++i) {
	    WizardStep step = (WizardStep)wizardSteps.get(i);
	    if (id.equals(step.getId())) {
		return i;
	    }
	}
	// What else ? 
	// Need to implement errors to component
	// alerts ? FacesMessage ?
	return wizardState.getCurrentStep();
    }

    /**
     * Return the {@link WizardStep WizardStep} instance following
     * <code>step</code>.
     * If there are no more steps return null.
     *
     * @param step The step preceding the returned step.
     */
    public WizardStep getNextStep(WizardStep step) {
	WizardStep next = null;
	try {
	    int i = wizardSteps.indexOf(step);
	    if (i != -1) {
		next = (WizardStep)wizardSteps.get(++i);
	    }
	} catch (Exception e) {
	}
	return next;
    }

    /**
     * Return the {@link WizardStep WizardStep} instance preceding
     * <code>step</code>
     * If there is no previous step return null.
     *
     * @param step The step following the returned step.
     */
    public WizardStep getPreviousStep(WizardStep step) {
	WizardStep previous = null;
	try {
	    int i = wizardSteps.indexOf(step);
	    if (i != -1) {
		previous = (WizardStep)wizardSteps.get(--i);
	    }
	} catch (Exception e) {
	}
	return previous;
    }

    /**
     * Return the current {@link WizardStep WizardStep} instance.
     */
    public WizardStep getCurrentStep() {
	WizardStep step = null;
	try {
	    step = (WizardStep)wizardSteps.get(wizardState.getCurrentStep());
	}
	catch (Exception e) {
	}
	return step;
    }

    /**
     * Return true if <code>step</code> is the current step, else false.
     *
     * @param step The step to test.
     */
    public boolean isCurrentStep(WizardStep step) {
	boolean result = false;
	try {
	    int i = wizardSteps.indexOf(step);
	    result = wizardState.getCurrentStep() == i;
	} catch (Exception e) {
	}
	return result;
    }

    /**
     * Return true if <code>step</code> is the step the should
     * contain a Finish button. This is the step that performs the
     * wizard task with the data collected from previous steps.
     *
     * @param step The step to identify.
     */
    public boolean isFinishStep(WizardStep step) {
	return step == null ? false : step.isFinish();
    }

    /**
     * Return true if <code>step<code> is the results step, else false.
     * The Results step follows the Finish step and displays
     * only  a "Close" button. It displays results of the task
     * performed in the Finish step.
     *
     * @param step The step to identify.
     */
    public boolean isResultsStep(WizardStep step) {
	return step == null ? false : step.isResults();
    }

    /**
     * Return true if <code>step<code> is a branching step, else false.
     * A branching step acts as a step "placeholder" and informs
     * the user that the steps following this step are determined by
     * the data entered in this or previous steps. Text should be
     * provided from the {@link #getPlaceholderText(WizardStep) 
     * getPlaceHolderText} method for this step, describing the branch.
     *
     * @param step The step to identify.
     */
    public boolean isBranch(WizardStep step) {
	return step instanceof WizardBranch;
    }

    /**
     * Return a description of this {@link WizardBranch WizardBranch} step.
     * 
     * @param step A branching step. It must be a 
     * {@link WizardBranch WizardBranch} instance.
     */
    public String getPlaceholderText(WizardStep step) {
	return step == null ? null : ((WizardBranch)step).getPlaceholderText();
    }

    /**
     * Return true if <code>step<code> is a substep step, else false.
     * A substep is a step or one of a series of substeps,
     * that occurs in every instance of this wizard.
     * Unlike the branch step, substep sequences are always the same
     * but may or may not be performed based on previous steps.
     *
     * @param step The step to check.
     */
    public boolean isSubstep(WizardStep step) {
	return step == null ? false :
		step.getParent() instanceof WizardSubstepBranch;
    }

    /**
     * Return true if the user can navigate to <code>step<code> out
     * of sequence, else false.
     * Typically this method is called to determine if a previous
     * step should be rendered such that the user can select it
     * and navigate back to that step. Its possible that some
     * wizards may also allow forward navigation.
     * <p><em>
     * Note this method only supports navigating to steps occuring
     * before the current step.
     * </em></p>
     *
     * @param step The step to check.
     */
    public boolean canGotoStep(WizardStep step) {
	try {
	    int i = wizardSteps.indexOf(step);
	    return wizardState.getCurrentStep() > i;
	} catch (Exception e) {
	    return false;
	}
    }

    /**
     * Return true if the previous button should be disabled
     * for this step, else false. Typically the first step of a
     * sequence should return true, since there usually isn't a
     * step before the first step.
     *
     * @param step The step to check.
     */
    public boolean isPreviousDisabled(WizardStep step) {
	WizardStep first = getFirstStep();
	return first == null ? false : first.getId().equals(step.getId());
    }

    /**
     * Return true if the next button should be disabled
     * for this step, else false.
     * This method always returns false;
     *
     * @param step The step to check.
     */
    public boolean isNextDisabled(WizardStep step) {
	return false;
    }

    /**
     * Return true if the finish button should be disabled
     * for this step, else false.
     * This method always returns false;
     *
     * @param step The step to check.
     */
    public boolean isFinishDisabled(WizardStep step) {
	return false;
    }

    /**
     * Return true if the cancel button should be disabled
     * for this step, else false.
     * This method always returns false;
     *
     * @param step The step to check.
     */
    public boolean isCancelDisabled(WizardStep step) {
	return false;
    }

    /**
     * Return true if the close button should be disabled
     * for this step, else false.
     * This method always returns false;
     *
     * @param step The step to check.
     */
    public boolean isCloseDisabled(WizardStep step) {
	return false;
    }

    /**
     * Return true if the previous button should be rendered
     * for this step, else false. Typically this method returns
     * true for all steps except for steps that are results steps.
     *
     * @param step The step to check.
     */
    public boolean hasPrevious(WizardStep step) {
	return step == null ? false : !isResultsStep(step);
    }

    /**
     * Return true if the next button should be rendered
     * for this step, else false. Typically this method returns
     * true for all steps except for steps that are finish or
     * results steps.
     *
     * @param step The step to check.
     */
    public boolean hasNext(WizardStep step) {
	return step == null ? false :
		!(isFinishStep(step) || isResultsStep(step));
    }

    /**
     * Return true if the cancel button should be rendered
     * for this step, else false.  Typically this method returns
     * true for all steps except for steps that are results steps.
     *
     * @param step The step to check.
     */
    public boolean hasCancel(WizardStep step) {
	return step == null ? false : !isResultsStep(step);
    }

    /**
     * Return true if the close button should be rendered
     * for this step, else false. Typically this method returns
     * true only for the results step.
     *
     * @param step The step to check.
     */
    public boolean hasClose(WizardStep step) {
	return step == null ? false : isResultsStep(step);
    }

    /**
     * Return true if the finish button should be rendered
     * for this step, else false. Typically this method returns
     * true only for the finish step.
     *
     * @param step The step to check.
     */
    public boolean hasFinish(WizardStep step) {
	// For now we only support simple linear wizards with no
	// results page.
	//
	return step == null ? false : isFinishStep(step);
    }

    /**
     * Return true if any of the steps have step help.
     * If any of the steps have step help, this method should return
     * true, unless no step help should be shown for the wizard.
     * If the determination had not been made when this method is
     * called, since the step list must be built at least once,
     * false is returned.
     */
    public boolean hasStepHelp() {
	return wizardState.getHasStepHelp() == null ? false :
		wizardState.getHasStepHelp().booleanValue();
    }

    /**
     * Return true if the wizard has completed and there are no
     * more steps for the user to complete, else false. Typically
     * this informs the wizard that there is nothing more to render.
     * This may cause a popup wizard to be dismissed or an inline
     * wizard to navigate to some other page.
     */
    public boolean isComplete() {
	return wizardState.isComplete();
    }

    /**
     * Called to inform the model that this instance will no longer be
     * referenced.
     */
    public void complete() {
	wizardSteps = null;
	wizardState.reset();
    }

    /**
     * Returns false if prematureRender is true, else true if
     * the step should participate in the APPLY_REQUEST_VALUES phase.
     *
     * @param event The event that precipitated this call.
     * @param prematureRender true if rendering is occuring before
     * RENDER_RESPONSE phase was normally expected.
     */
    public boolean decode(int event, boolean prematureRender) {
	return prematureRender ? false : wizardState.decode(event);
    }

    /**
     * Return true if the current step should participate in the
     * PROCESS_VALIDATIONS phase.
     * Returns true if event is WizardEvent.NEXT or WizardEvent.FINISH
     * and prematureRender is false.
     *
     * @param event The event that precipitated this call.
     * @param prematureRender Is true if rendering is occuring before
     * RENDER_RESPONSE phase was normally expected.
     */
    public boolean validate(int event, boolean prematureRender) {
	return prematureRender ? false : wizardState.validate(event);
    }

    /**
     * Return true if the current step should participate in the 
     * UPDATE_MODEL_VALUES phase.
     * Returns true if event is WizardEvent.NEXT or WizardEvent.FINISH and
     * prematureRender is false.
     *
     * @param event The event that precipitated this call.
     * @param prematureRender true if rendering is occuring before
     * RENDER_RESPONSE phase was normally expected.
     */
    public boolean update(int event, boolean prematureRender) {
	return prematureRender ? false : wizardState.update(event);
    }

    /**
     * Handle the following {@link WizardEvent WizardEvent} events
     * and adjust the state accordingly.
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
    public boolean handleEvent(WizardEvent event) {

	boolean returnValue = true;

	switch (event.getNavigationEvent()) {

	case WizardEvent.NEXT:
	    wizardState.nextStep();
	break;

	case WizardEvent.PREVIOUS:
	    wizardState.previousStep();
	break;

	case WizardEvent.FINISH:
	    int i = wizardState.getCurrentStep() + 1;
	    if (i == wizardSteps.size()) {
		wizardState.close();
	    } else {
		wizardState.finishStep();
	    }
	break;

	case WizardEvent.GOTOSTEP:
	    String gotoStepId = event.getGotoStepId();
	    if (gotoStepId != null) {
		int index = getStepIndex(gotoStepId);
		wizardState.gotoStep(index);
	    }
	break;

	case WizardEvent.CANCEL:
	    wizardState.cancel();
	break;

	case WizardEvent.CLOSE:
	    wizardState.close();
	break;

	case WizardEvent.HELPTAB:
	break;

	case WizardEvent.STEPSTAB:
	break;

	case WizardEvent.INVALID:
	break;

	case WizardEvent.NOEVENT:
	break;
	}

	return returnValue;
    }

    /**
     * This class maintains the current step and most recent
     * navigation event received from the Wizard component.
     * Is stores a simple index into the wizardSteps ArrayList
     *
     * It defines the state that controls the decode, validate and
     * update methods of the WizardModel.
     */
    class WizardState {

	static final int START= -1;
	static final int NEXT= 0;
	static final int PREVIOUS= 1;
	static final int CANCEL= 2;
	static final int FINISH= 3;
	static final int CLOSE= 4;
	static final int GOTOSTEP= 7;

	int state;
	int currentStep;
	Boolean hasStepHelp;

	WizardState() {
	    super();
	    this.state = START;
	    this.currentStep = 0;
	}

	int getState() {
	    return state;
	}

	void setState(int state) {
	    this.state = state;
	}

	Boolean getHasStepHelp() {
	    return hasStepHelp;
	}

	void setHasStepHelp(Boolean hasStepHelp) {
	    this.hasStepHelp = hasStepHelp;
	}

	boolean isComplete() {
	    return state == CLOSE || state == CANCEL;
	}

	boolean decode(int event) {
	    return true;
	}

	boolean validate(int event) {
	    return event == WizardEvent.FINISH || 
		event == WizardEvent.NEXT ||
		event == WizardEvent.NOEVENT;
	}

	// Its not clear if another state is needed for
	// updating on the previous click. We want the
	// state but not the commit.
	//
	boolean update(int event) {
	    return event == WizardEvent.FINISH || 
		event == WizardEvent.NEXT ||
		event == WizardEvent.NOEVENT;
	}

	void nextStep() {
	    state = NEXT;
	    ++currentStep;
	}

	void finishStep() {
	    state = FINISH;
	    ++currentStep;
	}

	void previousStep() {
	    state = PREVIOUS;
	    --currentStep;
	}

	void gotoStep(int step) {
	    state = GOTOSTEP;
	    currentStep = step;
	}

	int getCurrentStep() {
	    return currentStep;
	}

	void setCurrentStep(int currentStep) {
	    this.currentStep = currentStep;
	}

	void reset() {
	    state = START;
	    currentStep = 0;
	}

	void cancel() {
	    this.state = CANCEL;
	}
	void close() {
	    this.state = CLOSE;
	}
    };

    /**
     * <code>StateHolder</code> method called to save the state the model's
     * state.
     * The saved state consists of the values of <code>state<code>,
     * <code>currentStep</code>, and <code>hasStepHelp</code> of the internal
     * <code>WizardState</code> instance.
     */
    public Object saveState(FacesContext context) {
	 Object[] state = new Object[3];
	 int i = 0;
	 state[i++] = new Integer(wizardState.getState());
	 state[i++] = new Integer(wizardState.getCurrentStep());
	 state[i++] = wizardState.getHasStepHelp();
	 return state;
    }

    /**
     * <code>StateHolder</code> method called to restore the model's
     * current state based on the values in <code>state</code>.
     * The restored state consists of values for <code>state<code>,
     * <code>currentStep</code>, and <code>hasStepHelp</code> of the internal
     * <code>WizardState</code> instance.
     */
    public void restoreState(FacesContext context, Object state) {
	 Object[] _state = (Object[])state;
	 int i = 0;
	 wizardState.setState(((Integer)_state[i++]).intValue());
	 wizardState.setCurrentStep(((Integer)_state[i++]).intValue());
	 wizardState.setHasStepHelp((Boolean)_state[i++]);
    }

    /**
     * <code>StateHolder</code> method to set the persistent state of this
     * class. This call does not modify this class.
     */
    public void setTransient(boolean transientFlag) {
	// ignore this
    }

    /**
     * <code>StateHolder</code> method indicating the this class is 
     * persistent. This method returns <code>false</code>
     */
    public boolean isTransient() {
	return false;
    }
}
