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

import java.io.IOException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;

import javax.faces.component.NamingContainer;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Tab;
import com.sun.webui.jsf.component.TabSet;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.ComponentUtilities;

import com.sun.webui.jsf.event.WizardEvent;
import com.sun.webui.jsf.event.WizardActionListener;
import com.sun.webui.jsf.event.WizardEventListener;

import com.sun.webui.jsf.model.WizardModel;
import com.sun.webui.jsf.model.WizardModelBase;
import com.sun.webui.jsf.model.WizardStepList;
import com.sun.webui.jsf.model.WizardStepListBase;
import com.sun.webui.jsf.model.WizardStepListItem;
import com.sun.webui.jsf.model.WizardStepListItemBase;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ThemeUtilities;

// On Sub Components
//
// There are many sub components used by the wizard.
// Facets will eventually be supported for many at various levels.
// For example a facet for the complete StepsPane could
// be specified and rendered as a whole. Or just the
// steps/help tabset or individual tabs may be facets.
// It's not clear on exactly how much of this flexibility
// will be useful. When this is supported it would also be useful to have
// a "WizardStepsPane" component and renderer, vs. implicit code that
// creates the pane and the WizardRenderer renders it.
//
// For example having a facet for a button seems like a good
// idea, but what does the wizard do, does it register its 
// action listener as well or expect any existing action listeners
// to propagate events and how does the wizard deal with this
// situation ? Does it remove action listeners, subsitute its own
// and forward to other registered listeners ?
// The common use for providing facets for buttons will be to 
// specify custom javascript for client side behavior but providing
// facets for this seems very cumbersome to the average user that
// just wants to add some simple javascript to the next button.
// These details need to be addressed.
//
// As for other subcomponents, many are reused and may not be
// offered as facets. These reused sub components are mainly for
// use in the steps list, assuming a facet is not provided for
// the list. For example the static text visual features all resuse
// a single static text component.
// However the step links are not resused.
//
// Speaking of the step links, these need some serious management.
// In general sub components that need decoding are added as 
// children to the wizard and filtered out to obtain the WizardStep
// children as necessary.
// Links need to be pruned from the facet map on a regular basis
// since some may only be relevant once and then never used again.
//
// Currently the typical use of the wizard tag is to specify all
// the wizard steps in the jsp page. This is a real drag on performance
// since the tag handler is generated, it just reads in all steps whether
// or not they are needed or not. Serious pruning could be accomplished
// if branches that are not taken, are not read as children.
// It would also be beneficial to have a renderer for the WizardStep
// and not the WizardRenderer itself, but this is not as straightforward
// as it might appear. Mainly because the steps list must be drawn before
// the actual step, so there needs to be a renderer that understands
// how to render a WizardStep for use by the StepsPane and a renderer
// to render the step in the body of the wizard.
//

/*
 * The Wizard component manages a sequence of 
 * {@link WizardStep WizardStep} components.
 * <p>
 * The component delegates to a {@link WizardModel WizardModel} instance,
 * {@link WizardModelBase WizardModelBase} by default, to control the
 * lifecycle and the navigation through the sequence of
 * {@link WizardStep WizardStep} components.
 * </p>
 * <p>
 * The <code>Wizard</code> component {@link #broadcast(FacesEvent) broadcasts}
 * {@link WizardEvent WizardEvents} to 
 * {@link WizardEventListener WizardEventListeners} existing on the 
 * current {@link WizardStep WizardStep} and the <code>Wizard</code> instance.
 * The events are then forwarded to the model based on the listener's response
 * to the event.
 * </p>
 * <h3>Wizard events</h3>
 * The following events are broadcast by the wizard. There are two events
 * that are only broadcast to the
 * {@link wizardEventListener WizardEventListener} specified
 * with the <code>eventListener</code> property on the <code>Wizard</code>
 * component:
 * <p>
 * <ul>
 * <li><code>WizardEvent.START</code> is broadcast once, when the
 * wizard is first rendered during a wizard session. It is broadcast during
 * {@link #encodeBegin(FacesContext) encodeBegin} before any rendering has
 * begun.
 * </li>
 * <li><code>WizardEvent.COMPLETE</code> is broadcast once when the wizard
 * session has completed. It
 * is broadcast during {@link #encodeEnd(FacesContext) encodeEnd} after the
 * last response for this wizard session has been written and the wizard
 * model {@link WizardModelBase#complete complete} method has been 
 * called to inform the model it will no longer be referenced during this
 * wizard session. The next time this <code>Wizard</code> instance conducts
 * a wizard session, the <code>WizardEvent.START</code> event is broadcast.
 * </li>
 * </ul>
 * <p>
 * The following events may be broadcast during the INVOKE_APPLICATION phase to
 * the {@link WizardEventListener eventListener} specified by the
 * <code>eventListener</code> property of the <code>Wizard</code>
 * component and the current {@link WizardStep WizardStep} in which the
 * event has occured, and to the wizard model.
 * </p>
 * <ul>
 * <li><code>WizardEvent.NEXT</code> is broadcast when the user clicks the
 * next button.
 * </li>
 * <li><code>WizardEvent.PREVIOUS</code> is broadcast when the user clicks
 * the previous button.
 * </li>
 * <li><code>WizardEvent.GOTOSTEP</code> is broadcast when the user clicks
 * a previous step link in the step list.
 * </li>
 * <li><code>WizardEvent.HELPTAB</code> is broadcast when the user clicks the
 * help tab, when there is step help available.
 * </li>
 * <li><code>WizardEvent.STEPSTAB</code> is broadcst when the user clicks the
 * steps tab, when there is step help available.
 * </li>
 * <li><code>WizardEvent.CANCEL</code> is broadcast when the user clicks the
 * cancel button.
 * </li>
 * <li><code>WizardEvent.CLOSE</code> is broadcast when the user clicks the 
 * close button of a results page.
 * </li>
 * <li><code>WizardEvent.FINISH</code> is broadcast when the user clicks the
 * finish button on the last page of the wizard.
 * </li>
 * </ul>
 * <p>
 * When the application's <code>WizardEventListener</code> is invoked it may
 * return <code>true</code> to continue the current wizard session or
 * <code>false</code> to prevent the wizard from proceeding, thereby
 * remaining on the current step. If the listener returns <code>true</code>
 * the event is forwarded to the wizard model.</br>
 * If the listener throws an exception the
 * <code>WizardEvent.CANCEL</code> event is forwarded to the wizard model
 * and the wizard will proceed to complete the wizard session
 * eventually broadcasting the <code>WizardEvent.COMPLETE</code> event.
 */
@Component(type="com.sun.webui.jsf.Wizard",
    family="com.sun.webui.jsf.Wizard", displayName="Wizard", tagName="wizard",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_wizard",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_wizard_props")
public class Wizard extends UIComponentBase implements NamingContainer {

    // One of
    // WizardEvent.NOEVENT
    // WizardEvent.CANCEL
    // WizardEvent.CLOSE
    // WizardEvent.FINISH
    // WizardEvent.GOTOSTEP
    // WizardEvent.HELPTAB
    // WizardEvent.NEXT
    // WizardEvent.PREVIOUS
    // WizardEvent.STEPSTAB

    // We want to begin with the START event.
    // Ensure that this state will only be START
    // at the first rendering. Every other cycle, this
    // must be set to a different event, especially NOEVENT
    // when the request is from a component with a wizard step.
    //
    // This member is not transient in case this is server side
    // state saving but it is not preserved during save/restore state.
    // Not sure if this matters for server side state saving, since
    // this component is not necessarily serializable.
    /**
     * Initially <code>WizardEvent.START<code> and subsequently the
     * event related to a user action.
     */
    private int event = WizardEvent.START;

    // The actual button instance
    /**
     * Maintains the component instance that triggers an event
     * during the request processing. Not saved or restored.
     */
    transient private UIComponent eventSource;
    /**
     * Maintains the id of the {@link WizardStep WizardStep} represented
     * by a previous step link, during request processing. Not saved or
     * restored.
     */
    transient private String gotoStepId;

    /**
     * Maintains the state of the tabs in the steps pane. This memeber
     * is saved and restored.
     */
    private boolean stepTabActive = true;

    /**
     * Construct a <code>Wizard</code> instance.
     * Sets renderer type to <code>com.sun.webui.jsf.Wizard</code>.
     */
    public Wizard() {
	super();
	setRendererType("com.sun.webui.jsf.Wizard");
    }

    /**
     * Return the family for this component,
     * <code>com.sun.webui.jsf.Wizard</code>.
     */
    public String getFamily() {
	return "com.sun.webui.jsf.Wizard";
    }


    // Need to ensure that wizardModel methods are not
    // called before WizardModelBase is initialized.
    // Need to work out what this means if the model
    // is supplied by the developer.
    // Looks like "isComplete" is the first call made into 
    // the wizard from the renderer, since it wants to know
    // if the wizard is closing in the encodeBegin.
    //
    /**
     * Overrides <codde>super.getModel()</code>
     * to provide a default {@link WizardModel WizardModel} instance.
     * If <code>super.getModel()</code> returns null, create an instance of 
     * {@link WizardModelBase WizardModelBase} and call 
     * <code>super.setModel()</code> with the instance.
     */
    public WizardModel getModel() {
	WizardModel wm = _getModel();
	if (wm == null) {
	    wm = new WizardModelBase();
	    setModel(wm);
	}
	return wm;
    }

    /**
     * The <code>model</code> property is a value binding that
     * resolves to an instance of <code>WizardModel</code>. This instance
     * is an alternative to the default <code>WizardModelBase</code>
     * instance that controls the flow of steps in the wizard.
     */
    private WizardModel _getModel() {
        if (this.model != null) {
            return this.model;
        }
        ValueExpression _vb = getValueExpression("model");
        if (_vb != null) {
            return (WizardModel) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Convenience routine to determine if a subcomponent or other
     * JSF event caused the lifecycle to proceed to the RENDER_RESPONSE
     * phase prematurely.
     */
    private boolean prematureRender() {
	return getFacesContext().getRenderResponse();
    }

    // This actually should call the renderer's decode method
    // to be JSF compliant and allow an alternate WizardRenderer
    // to decode something that it expects.
    //
    /**
     * Called by {@link #processDecodes(FacesContext) processDecodes}
     * during APPLY_REQUEST_VALUES phase.
     * Unlike many components, this method does not defer to the
     * renderer since there is nothing in the response to decode
     * by the <code>Wizard</code> component directly.
     */
    public void decode(FacesContext context) {

	// In case the renderer has a decode method
	// The current renderer doesn't decode anything.
	//
	super.decode(context);

	// Always queue an event so that broadcast is 
	// always called.
	// I think we have to do this in case the form is 
	// submitted from a component on the step.
	//
	WizardEvent wizardEvent = new WizardEvent(this);
	wizardEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
	queueEvent(wizardEvent);
    }

    /**
     * The wizard controls the decoding of the current wizard step.
     * The wizard must call <code>processDecodes()</code> for
     * its controls before the current step, to ensure that any control
     * events are issued before processing the step.
     *
     * @exception NullPointerException
     * @param context This FacesContext for this request.
     */
    public void processDecodes(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }

	if (!isRendered()) {
	    return;
	}
	
        // Currently there is nothing for a Wizard to decode.
	// Eventually there may be some hidden fields 
	// associated with the Wizard, like an identifier that
	// the Wizard uses to cache its state in the Session
	// etc.
	// 
	try {
	    decode(context);
	} catch (RuntimeException e) {
	    context.renderResponse();
	    throw e;
	}
	decodeControls(context);
	decodeStep(context);
    }

    /**
     * Invoke the <code>processDecodes()</code>method on
     * the wizard's controls. In effect, all children that are not
     * instances of {@link WizardStep WizardStep}.
     *
     * @param context This FacesContext for this requset.
     */
    protected void decodeControls(FacesContext context) {

        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
	    WizardStep currentStep = null;
            UIComponent kid = (UIComponent) kids.next();
	    if (kid instanceof WizardStep) {
		continue;
	    }
            kid.processDecodes(context);
        }
    }

    /**
     * Invoke the wizard model's {@link WizardModelBase#decode(int,boolean)
     * decode} method to determine if the wizard should invoke 
     * <code>processDecodes()</code> on the current {@link WizardStep
     * WizardStep}.
     *
     * @param context This FacesContext for this requset.
     */
    protected void decodeStep(FacesContext context) {

	WizardModel wizardModel = getModel();
	if (wizardModel.decode(event, prematureRender())) {
	    wizardModel.getCurrentStep().processDecodes(context);
	}
    }

    /**
     * The wizard controls the validating of the current wizard step.
     * The wizard must call <code>processValidators()</code> for
     * its controls before the current step, to ensure that any control
     * events are issued before processing the step.
     * Call the {@link #validateControls(FacesContext) validateControls}
     * method, and then {@link #validateStep(FacesContext) validateStep}.
     *
     * @exception NullPointerException
     * @param context This FacesContext for this request.
     */
    public void processValidators(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }

	validateControls(context);
	validateStep(context);
    }

    /**
     * Invoke the <code>processValidators()</code>method on
     * the wizard's controls. In effect, all children that are not
     * instances of {@link WizardStep WizardStep}.
     *
     * @param context This FacesContext for this requset.
     */
    protected void validateControls(FacesContext context) {

        // Process all the facets and children of this component
        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
	    if (kid instanceof WizardStep) {
		continue;
	    }
            kid.processValidators(context);
        }
    }

    /**
     * Invoke the wizard model's {@link WizardModelBase#validate(int,boolean)
     * validate} method to determine if the wizard should invoke 
     * <code>processValidators()</code> on the current step.
     * If a component in a step does not validate, it will be
     * assumed that <code>FacesContext.renderResponse</code>has
     * been called which in turn will cause
     * {@link #prematureRender() prematureRender}
     * to return <code>true</code>.
     *
     * @param context This FacesContext for this requset.
     */
    protected void validateStep(FacesContext context) {

	WizardModel wizardModel = getModel();
	if (wizardModel.validate(event, prematureRender())) {
	    wizardModel.getCurrentStep().processValidators(context);
	}
    }

    /**
     * The wizard controls the updating of the current wizard step.
     * The wizard must call <code>processUpdates()</code> for
     * its controls before the current step, to ensure that any control
     * events are issued before processing the step.
     * Call the {@link #updateControls(FacesContext) updateControls} method,
     * and then {@link #updateStep(FacesContext) updateStep}.
     *
     * @exception NullPointerException
     * @param context This FacesContext for this request.
     */
    public void processUpdates(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }

	updateControls(context);
	updateStep(context);
    }
    
    /**
     * Invoke the <code>processUpdates()</code>method on
     * the wizard's controls. In effect, all children that are not
     * instances of {@link WizardStep WizardStep}
     *
     * @param context This FacesContext for this requset.
     */
    protected void updateControls(FacesContext context) {

        // Process all facets and children of this component
        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
	    if (kid instanceof WizardStep) {
		continue;
	    }
            kid.processUpdates(context);
        }
    }

    /**
     * Invoke the wizard model's {@link WizardModelBase#update(int,boolean)
     * update} method to determine if the wizard should invoke 
     * <code>processUpdates()</code> on the current step..
     *
     * @param context This FacesContext for this requset.
     */
    protected void updateStep(FacesContext context) {

	WizardModel wizardModel = getModel();
	if (wizardModel.update(event, prematureRender())) {
	    wizardModel.getCurrentStep().processUpdates(context);
	}
    }

    /**
     * Begin the rendering of the wizard.
     * If the wizard is rendering for the first time, the <code>event</code>
     * member is <code>WizardEvent.START</code>, call
     * {@link #broadcastStartEvent() broadcastStartEvent} and then
     * set <code>event</code> to <code>WizardEvent.NOEVENT</code> to indicate
     * that the wizard has rendered at least once.</br>
     * Then call the renderer's <code>encodeBegin</code> method.
     *
     * @param context This FacesContext for this requset.
     */ 
    public void encodeBegin(FacesContext context) throws IOException {

        if (context == null) {
            throw new NullPointerException();
        }

        if (!isRendered()) {
            return;
        }

	// Let the application know, that the wizard is about to 
	// render, only the first time.
	// The application cannot abort the event.
	// Set the event to NOEVENT in case it is 
	// server side state saving, so that the next cycle
	// won't broadcst the START event again.
	//
	// In client side state saving this is ensured during 
	// processRestoreState
	//
	if (event == WizardEvent.START) {
	    broadcastStartEvent();
	    event = WizardEvent.NOEVENT;
	}

	String rendererType = getRendererType();
	if (rendererType != null) {
	    getRenderer(context).encodeBegin(context, this);
        }
    }

    /**
     * Begin the rendering of the wizard child components.
     * This really means that the WizardStep children are
     * available. Call the 
     * {@link WizardModelBase#initialize(Wizard) initialize)}
     * method. </br>
     * Then call the renderer's <code>encodeChildren</code> method.
     *
     * @param context This FacesContext for this requset.
     */ 
    public void encodeChildren(FacesContext context) throws IOException {

        if (context == null) {
            throw new NullPointerException();
        }

	// This is the only point where we can truly initialize
	// the WizardModelBase model when wizard steps are child tags.
	// However, this is called twice in client state saving.
	// It is called in restoreState to establish the state
	// before the wizard was rendered to the client.
	//
	// In fact is must be processed twice.
	// Once to restore, and once to reconstruct the
	// the tree based on any changes that might have 
	// occured during the request processing, and 
	// invoke application phase i.e. branches taken,
	// steps removed, etc.
	//
	getModel().initialize(this);

        if (!isRendered()) {
            return;
        }

	String rendererType = getRendererType();
	if (rendererType != null) {
	    getRenderer(context).encodeChildren(context, this);
        }

    }

    /**
     * Complete the rendering of the wizard.
     * Call the renderer's <code>encodeChildren</code> method.
     * If the wizard model's 
     * {@link WizardModelBase#isComplete() isComplete} method
     * returns true, call the model's {@link WizardModelBase#complete()
     * complete} method to inform the model this wizard instance
     * will no longer invoke methods on that model instance during
     * this wizard session.
     *
     * @param context This FacesContext for this requset.
     */ 
    public void encodeEnd(FacesContext context) throws IOException {

        if (context == null) {
            throw new NullPointerException();
        }

        if (!isRendered()) {
            return;
        }

	String rendererType = getRendererType();
	if (rendererType != null) {
	    getRenderer(context).encodeEnd(context, this);
        }

	// If in server side state saving, this wizard instance is not
	// destroyed evertime the wizard ends, since the view roots
	// are cached.
	//
	// The event must be reset to START if this session
	// is completing, so that the next cycle, which will be the "first"
	// for that wizard session, sends the START event. Otherwise
	// set the NOEVENT state.
	//
	// In client side state saving this is ensured by 
	// processRestoreState.
	//
	if (isComplete()) {
	    getModel().complete();
	    // Let the application know that the wizard has completed
	    // Should we delete the children here ? CR 6333872
	    //
	    broadcastCompleteEvent();
	    event = WizardEvent.START;
	} else {
	    event = WizardEvent.NOEVENT;
	}
    }

    /**
     * Returns an iterator of {@link WizardStepListItem WizardStepListItem}
     * instances that represent the steps for this wizard. 
     * {@link WizardStepListItem WizardStepListItem}
     * instances encapsulate information about a step that is 
     * useful for rendering a list of steps.
     */
    public Iterator getStepListIterator() {
	return getModel().getWizardStepList().iterator();
    }

    /**
     * Return true if the wizard has completed all its steps.
     */
    public boolean isComplete() {
	return getModel().isComplete();
    }

    /**
     * Return true if step is the current step, else false.
     *
     * @param step The step to check.
     */
    public boolean isCurrentStep(WizardStep step) {
	return getModel().isCurrentStep(step);
    }

    /**
     * Return the step currently being performed.
     */
    public WizardStep getCurrentStep() {
	return getModel().getCurrentStep();
    }

    /**
     * Return true if the wizard has help for steps.
     * Typically, if false is returned the wizard steps pane does not
     * display Steps and Help tabs, just the step list.
     */
    public boolean hasStepHelp() {
	return getModel().hasStepHelp();
    }


    /**
     * Return true if the close button should be rendered
     * for the current step, else false. Typically this method returns
     * true only for the results step.
     */
    public boolean hasClose() {
	WizardModel wm = getModel();
	return wm.hasClose(wm.getCurrentStep());
    }

    /**
     * Return true if the next button should be rendered
     * for the current step, else false. Typically this method returns
     * true for all steps except for the finish or
     * results steps.
     */
    public boolean hasNext() {
	WizardModel wm = getModel();
	return wm.hasNext(wm.getCurrentStep());
    }

    /**
     * Return true if the previous button should be rendered
     * for the current step, else false. Typically this method returns
     * true for all steps except for the results steps.
     */
    public boolean hasPrevious() {
	WizardModel wm = getModel();
	return wm.hasPrevious(wm.getCurrentStep());
    }

    /**
     * Return true if the finish button should be rendered
     * for the current step, else false. Typically this method returns
     * true only for the finish step.
     */
    public boolean hasFinish() {
	WizardModel wm = getModel();
	return wm.hasFinish(wm.getCurrentStep());
    }

    /**
     * Return true if the cancel button should be rendered
     * for the current step, else false.  Typically this method returns
     * true for all steps except for the results step.
     */
    public boolean hasCancel() {
	WizardModel wm = getModel();
	return wm.hasCancel(wm.getCurrentStep());
    }

    /**
     * Return true if the close button should be disabled
     * for the current step, else false.
     */
    public boolean isCloseDisabled() {
	WizardModel wm = getModel();
	return wm.isCloseDisabled(wm.getCurrentStep());
    }

    /**
     * Return true if the next button should be disabled
     * for the current step, else false.
     */
    public boolean isNextDisabled() {
	WizardModel wm = getModel();
	return wm.isNextDisabled(wm.getCurrentStep());
    }

    /**
     * Return true if the previous button should be disabled
     * for the current step, else false. Typically the first step of a
     * sequence should return true, since there usually isn't a
     * step before the first step.
     */
    public boolean isPreviousDisabled() {
	WizardModel wm = getModel();
	return wm.isPreviousDisabled(wm.getCurrentStep());
    }

    /**
     * Return true if the finish button should be disabled
     * for the current step, else false.
     */
    public boolean isFinishDisabled() {
	WizardModel wm = getModel();
	return wm.isFinishDisabled(wm.getCurrentStep());
    }

    /**
     * Return true if the cancel button should be disabled
     * for the current step, else false.
     */
    public boolean isCancelDisabled() {
	WizardModel wm = getModel();
	return wm.isCancelDisabled(wm.getCurrentStep());
    }

    /**
     * Returns true if the steps pane has Steps and Help tabs and
     * the Steps tab is selected. If the wizard has no step help
     * return true.
     */
    public boolean isStepsTabActive() {
	return stepTabActive || !hasStepHelp();
    }

    /**
     * Return the number of the current Step, to be used in a
     * steps list.
     */
    public String getCurrentStepNumberString() {
	// Due to the implementation of WizardStepListBase
	// this value is only valid if the WizardStepListBase
	// instance that is returned has been iterated over at 
	// least once. Need to fix this.
	//
	return getModel().getWizardStepList().
		getCurrentStepNumberString();
    }

    // Sub components and action listeners.
    //
    // While it may appear that facets are supported, they 
    // are not yet documented in the tld doc. More work
    // needs to be done to understand the handling of these
    // facets.

    /**
     * Return the <code>UIComponent</code> that will be used for the 
     * Cancel button.
     */
    public UIComponent getCancelComponent() {
	return getButtonComponent(CANCEL_FACET, WIZARD_CANCEL,
		isCancelDisabled(),
		WizardEvent.CANCEL, true,
		getJavaScript(WizardEvent.CANCEL), false);
    }

    /**
     * Return the <code>UIComponent</code> that will be used for the Close
     * button.
     */
    public UIComponent getCloseComponent() {
	return getButtonComponent(CLOSE_FACET, WIZARD_CLOSE,
		isCloseDisabled(),
		WizardEvent.CLOSE, true,
		getJavaScript(WizardEvent.CLOSE), true);
    }

    /**
     * Return the <code>UIComponent</code> that will be used for the 
     * Finish button.
     */
    public UIComponent getFinishComponent() {
	return getButtonComponent(FINISH_FACET, WIZARD_FINISH,
		isFinishDisabled(),
		WizardEvent.FINISH, true,
		getJavaScript(WizardEvent.FINISH), true);
    }

    /**
     * Return the <code>UIComponent</code> that will be used for the Next
     * button.
     */
    public UIComponent getNextComponent() {
	return getButtonComponent(NEXT_FACET, WIZARD_NEXT, 
		isNextDisabled(),
		WizardEvent.NEXT, true,
		getJavaScript(WizardEvent.NEXT), true);
    }

    /**
     * Return the <code>UIComponent</code> that will be used for the
     * Previous button.
     */
    public UIComponent getPreviousComponent() {
	return getButtonComponent(PREVIOUS_FACET, WIZARD_PREVIOUS,
		isPreviousDisabled(),
		WizardEvent.PREVIOUS, true,
		getJavaScript(WizardEvent.PREVIOUS), false);
    }

    /**
     * If a facet by the name of <code>facetName</code> is returned
     * by <code>ComponentUtilities.getPrivateFacet</code>, return it.
     * If not return an initialized {@link Button Button} as appropriate
     * for the specified <code>facetName</code.
     * <p>
     * <code>facetName</code> is one of <code>previous</code>,
     * <code>next</code>, finish</code>, <code>cancel</code> or
     * <code>close</code>.
     * </p>
     * <p>
     * The button's id:</br>
     * <code>this.getId() + "_" + facetName</code>
     * <p>
     * The <code>actionListener</code> is also registered with the
     * facet component if found and sets its disabled state as deemed
     * by the wizard.
     * </p>
     * This is a private facet.
     *
     * @deprecated See
     * {@link
     *	#getButtonComponent(String,String,boolean,int,boolean,String,boolean) 
     *   getButtonComponent(String,String,boolean,int,boolean,String,boolean)}
     *
     * @param facetName the role for the button to return.
     * @param textKey the theme text for the button text.
     * @param disabled true if this button should be disabled, else false.
     * @param wizardEvent the WizardEvent to broadcast.
     * @param immediate the button immediate value.
     * @paran javascript the onclick JavaScript routine name for this button
     */
    protected UIComponent getButtonComponent(String facetName,
	    String textKey, boolean disabled, 
	    int wizardEvent, boolean immediate,
	    String javascript) {

	// Primary button only.
	return getButtonComponent(facetName,
	    textKey, disabled, wizardEvent, immediate, javascript, true);
    }

    // Need to create this method because the previous original method
    // cannot be changed and buttons must be created either primary
    // or not. The primary buttons are the Next, Finish, and Close
    // buttons.
    //
    //
    /**
     * If a facet by the name of <code>facetName</code> is returned
     * by <code>ComponentUtilities.getPrivateFacet</code>, re-initialize it
     * and return it.</br>
     * If not create and return an initialized {@link Button Button} as
     * appropriate for the specified <code>facetName</code.
     * <p>
     * <code>facetName</code> is one of <code>previous</code>,
     * <code>next</code>, finish</code>, <code>cancel</code> or
     * <code>close</code>.
     * </p>
     * <p>
     * The button's id:</br>
     * <code>this.getId() + "_" + facetName</code>
     * <p>
     * A {@link WizardActionListener WizardActionListener} is also registered
     * with the facet component if created and sets its disabled state as deemed
     * by the wizard.
     * </p>
     * This is a private facet.
     *
     * @param facetName the role for the button to return.
     * @param textKey the theme text for the button text.
     * @param disabled true if this button should be disabled, else false.
     * @param wizardEvent the WizardEvent for the listener to process.
     * @param immediate the button immediate value.
     * @paran javascript the onclick JavaScript routine name for this button
     * @param primary make the button a primary button, if true.
     */
    protected UIComponent getButtonComponent(String facetName,
	    String textKey, boolean disabled, 
	    int wizardEvent, boolean immediate,
	    String javascript, boolean primary) {

	/* NOT IMPLEMENTED
	UICommand child = getFacet(facetName);
	if (child != null) {
	    return child;
	}
	*/

	// We know its a Button
	//
	Button button = (Button)
		ComponentUtilities.getPrivateFacet(this, facetName, true);
	if (button == null) {

	    button = new Button();
	    button.setId(
		ComponentUtilities.createPrivateFacetId(this, facetName));
	    button.setImmediate(immediate);
	    button.setPrimary(primary);
	    button.addActionListener(
		    new WizardActionListener(getId(), wizardEvent));

	    ComponentUtilities.putPrivateFacet(this, facetName, button);
	}

	Theme theme = ThemeUtilities.getTheme(getFacesContext());

	// Set every time for locale changes
	//
	button.setText(theme.getMessage(textKey));
	button.setDisabled(disabled);

	if (javascript != null) {
	    button.setOnClick(javascript);
	}

	return button;
    }

    // FIXME: Need to return the component unconditionally
    // and let the caller determine if "hasStepHelp" has
    // any influence.
    //
    /**
     * Return a component that is rendered in the Steps pane.
     * If the wizard steps do not provide help, null is returned.
     */
    public UIComponent getTabsComponent() {

	// need to determine if there will be a tab component
	// If there is not help support there will be just 
	// a steps list.
	// There is no help support if no step has help, or
	// the help has been turned off for the wizard.
	//
	return getModel().hasStepHelp() ? getTabsComponent(TABS_FACET) :
		null;
    }

    /**
     * If a facet by the name of <code>facetName</code> is returned
     * by <code>ComponentUtilities.getPrivateFacet</code>, re-initialize it
     * and return it.</br>
     * If not, create and return an initialized {@link TabSet TabSet} as
     * appropriate for the tabs in the steps pane.
     * <p>
     * The tabset's id is:</br>
     * <code>this.getId() + "_" + facetName</code>
     * <p>
     * This is a private facet.
     * </p>
     *
     * @param facetName the tabset facet to return.
     */
    protected UIComponent getTabsComponent(String facetName) {


	/* NOT IMPLEMENTED
	UIComponent child = getFacet(facetName);
	if (child != null) {
	    return child;
	}
	*/

	Theme theme = ThemeUtilities.getTheme(getFacesContext());

	// We know it's a TabSet
	//
	TabSet tabSet = (TabSet)
		ComponentUtilities.getPrivateFacet(this, facetName, true);
	if (tabSet == null) {

	    tabSet = new TabSet();
	    // Does this have any effect ?
	    tabSet.setImmediate(true);
	    tabSet.setId(
		ComponentUtilities.createPrivateFacetId(this, facetName));
	    // Mini tabs
	    tabSet.setMini(true);

	    List tabs = tabSet.getChildren();

	    Tab steptab = getTab(theme, STEP_TAB, WIZARD_STEP_TAB,
		    WIZARD_TAB_TOOLTIP, 
		    WizardEvent.STEPSTAB, stepTabActive, true,
		    getJavaScript(WizardEvent.STEPSTAB));
	    tabs.add(steptab);

	    Tab helptab = getTab(theme, HELP_TAB, WIZARD_HELP_TAB,
		    WIZARD_TAB_TOOLTIP, 
		    WizardEvent.HELPTAB, !stepTabActive, true,
		    getJavaScript(WizardEvent.HELPTAB));

	    tabs.add(helptab);

	    tabSet.setSelected(stepTabActive ? steptab.getId() :
		helptab.getId());

	    ComponentUtilities.putPrivateFacet(this, facetName, tabSet);

	    return tabSet;
	}

	// Update the existing TabSet private facet

	List tabs = tabSet.getChildren();
	Iterator itabs = tabs.iterator();
	String stepsId = ComponentUtilities.createPrivateFacetId(this,
		STEP_TAB);
	String helpId = ComponentUtilities.createPrivateFacetId(this,
		HELP_TAB);
	while (itabs.hasNext()) {
	    UIComponent tab = (UIComponent)itabs.next();
	    if (!(tab instanceof Tab)) {
		continue;
	    }
	    String tabid = tab.getId();
	    if (stepsId.equals(tabid)) {
		initTab((Tab)tab, theme, WIZARD_STEP_TAB,
		    WIZARD_TAB_TOOLTIP, stepTabActive, true,
		    getJavaScript(WizardEvent.STEPSTAB));
	    } else 
	    if (helpId.equals(tabid)) {
		initTab((Tab)tab,theme, WIZARD_HELP_TAB,
		    WIZARD_TAB_TOOLTIP, !stepTabActive, true,
		    getJavaScript(WizardEvent.HELPTAB));
	    }
	}
	tabSet.setSelected(stepTabActive ? stepsId : helpId);

	return tabSet;
    }

    /**
     * Return a {@link Tab Tab} component initialized with the parameters.
     * <p>
     * The id is formed from <code>this.getId() + "_" + facetName</code>.</br>
     * Facet name is <code>stptb</code> or <code>hlptb</code>.
     * </p>
     * <p>
     * A {@link WizardActionListener WizardActionListener} is registered
     * with the component.
     * </p>
     *
     * @param theme Theme instance to obtain text for labels.
     * @param facetName Append this to the tab's id.
     * @param text The tab's text.
     * @param toolTip The tab's tooltip.
     * @param wizardEvent the WizardEvent for the listener to process.
     * @param selected The value is true if this tab is initially selected.
     * @param immediate the tab immediate value.
     * @paran javascript the onclick JavaScript routine name for this tab
     */
    protected Tab getTab(Theme theme, String facetName, String text,
	    String toolTip, int wizardEvent, boolean selected,
	    boolean immediate, String javascript) {

	Tab tab = new Tab();
	tab.setId(ComponentUtilities.createPrivateFacetId(this, facetName));
	tab.addActionListener(new WizardActionListener(getId(), wizardEvent));

	initTab(tab, theme, text, toolTip, selected,
	    immediate, javascript);

	return tab;
    }

    /**
     * Initialize a tab.
     *
     * @param tab The Tab instance to initialize.
     * @param theme Theme instance to obtain text for labels.
     * @param text The tab's text.
     * @param toolTip The tab's tooltip.
     * @param selected The value is true if this tab is initially selected.
     * @param immediate the tab immediate value.
     * @paran javascript the onclick JavaScript routine name for this tab
     */
    private void initTab(Tab tab, Theme theme, String text,
	    String toolTip, boolean selected,
	    boolean immediate, String javascript) {

	text = theme.getMessage(text);
	tab.setText(text);
	tab.setToolTip(selected ?
	    theme.getMessage(toolTip, new Object[] { text }) : text);
	tab.setImmediate(immediate);
	if (javascript != null) {
	    tab.setOnClick(javascript);
	}
    }

    // Not documented
    /**
     * Return a component that implements the current step indicator icon.
     * If a facet named <code>stepIndicator</code> is found
     * that component is returned. Otherwise an {@link Icon Icon} component
     * is returned. It is assigned the id</br>
     * <code>getId() + "_stepIndicator"</code></br>
     * <p>
     * If the facet is not defined then the returned {@link Icon Icon}
     * component is re-intialized every time this method is called.</br>
     * The icon image is <code>ThemeImages.WIZARD_ARROW</code>
     * </p>
     *
     * @return a current step indicator icon
     */
    public UIComponent getStepIndicatorComponent() {

	UIComponent child = (UIComponent)getFacet(INDICATOR_FACET);
	if (child != null) {
	    return child;
	}

	Theme theme = ThemeUtilities.getTheme(getFacesContext());

	// It needs to be created every time in case it
	// is localized.
	//
	Icon icon = ThemeUtilities.getIcon(theme, ThemeImages.WIZARD_ARROW);
	String id = 
		ComponentUtilities.createPrivateFacetId(this, INDICATOR_FACET);
	icon.setId(id);
	icon.setParent(this);

	// Should be part of Theme
	//
	icon.setHspace(4);

	String toolTip = theme.getMessage(WIZARD_CURRENT_STEP_ALT); 
	icon.setToolTip(toolTip);
	icon.setAlt(toolTip);

	return icon;
    }

    // This component is a child and not a facet.
    // I'm not exactly sure why, but I was reluctant to change it
    // during the "private" facet edits.
    /**
     * Return an existing or create  a {@link Hyperlink Hyperlink} component
     * to be used as a step link in the steps list.  The id for the created
     * component is:</br>
     * <code>step.getId() + "_stplnk" + "_" + stepId().</code></br>
     * The appended step id is used to identify the step the wizard should
     * navigate to.</br>
     * An {@link WizardActionListener WizardActionListener} is added to the
     * component if it is created.
     *
     * @param id The component id as described above.
     * @param text The Hylerlink text, typically WizardStep.getSummaryText.
     * @param wizardEvent the WizardEvent for the listener to process.
     * @param immediate the component's immediate value.
     * @param javascript the onclick JavaScript routine name for this component
     */
    public UIComponent getSteplinkComponent(String id, String text,
	int wizardEvent, boolean immediate, String javascript) {

	// Need a way to prune these hyperlink chidren when the
	// step list changes drastically
	//
	// We know it's a hyperlink
	//
	Hyperlink hlink = (Hyperlink)Util.findChild(this, id, null);
	if (hlink == null) {

	    hlink = new Hyperlink();
	    hlink.setId(id);
	    hlink.addActionListener(new WizardActionListener(
		    getId(), wizardEvent));
	    hlink.setImmediate(immediate);
	    getChildren().add(hlink);
	}

	hlink.setText(text);
	if (javascript != null) {
	    hlink.setOnClick(javascript);
	}

	return hlink;
    }


    // These components are potential facet candidates.
    // Not currently documented, the WizardRenderer does
    // attempt to aquire them and render them. But its not
    // clear if these are really necessary or useful.
    //

    /**
     * Return a component to represent the left pane in the wizard
     * that typically contains the steps list.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getStepsPaneComponent() {
	return null;
    }

   /**
     * Return a component to represent the wizards's top bar
     * that typically contains the Steps and Help tabs.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getStepsBarComponent() {
	return null;
    }

    /**
     * Return a that represents the steps list, rendered in the Steps tab.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getStepListComponent() {
	return null;
    }

    /**
     * Return a component that represents the step help for the current step,
     * rendered in the Help tab.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getStepHelpComponent() {
	return null;
    }

    /**
     * Return a component that represents the step title.
     * The component is rendered in the wizard above the step's components.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getStepTitleComponent() {
	return null;
    }

    /**
     * Return a component that represents the step detail or step
     * instruction, rendered below the step title.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getStepDetailComponent() {
	return null;
    }

   /**
     * Return a component that represents the components that comprise
     * the current wizard step.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getTaskStepComponent() {
	return null;
    }

    /**
     * Return a component that represents the entire right pane including
     * the step title, detail and components.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getTaskComponent() {
	return null;
    }
    /**
     * Return a component that represents the step title and detail for the
     * current step.</br>
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getTaskHeaderComponent() {
	return null;
    }

    /**
     * Return a component that represents the control bar or 
     * navigation controls, rendered after the step's components.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getControlBarComponent() {
	return null;
    }

    /**
     * Return a component that is rendered for the controls justified to
     * the left below the step's components. Typically these controls are the
     * previous and next or finish buttons.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getLeftControlBarComponent() {
	return null;
    }

    /**
     * Return a component that is rendered for the controls justified to
     * the right, below the step's components. Typically the control is the
     * cancel or close buttons.
     * This <code>Wizard</code> implementation returns null.
     */
    public UIComponent getRightControlBarComponent() {
	return null;
    }

    // Having facets for some of these StaticText elements
    // would be problematic. 
    // Do we just accept the facet component as it is with
    // the expectation that the content is correct ?
    // Or should we overwrite the content ?
    // For example using a facet for the static text step
    // number. We would look for only one facet and replace
    // the content with a particular step.
    // Or the component that represents the previous step link.
    // Its content would change, like id and content.
    // 

    /**
     * Return the component to render the step title label.
     * The id of this component is:</br>
     * <code>getCurrentStep().getId() + "_stpttllbl"</code>.
     * This component also includes the step number. Typically
     * renders "Step 1:".
     * <p>
     * This <code>Wizard</code> implementation returns a
     * {@link StaticText StaticText} component that is created and
     * initialized every time this method is called.
     * </p>
     */
    public UIComponent getStepTitleLabelTextComponent() {

	Theme theme = ThemeUtilities.getTheme(getFacesContext());
	return getStepStaticTextComponent(getCurrentStep(), STEP_TITLE_LABEL,
	    theme.getMessage(WIZARD_STEP_TITLE_LABEL,
		new Object[] {getCurrentStepNumberString()}));
    }

    /**
     * Return the component to render the step title text.
     * The id of this component is:</br>
     * <code>getCurrentStep().getId() + "_stpttl"</code>.</br>
     * This component typically renders after the step title label.
     * <p>
     * This <code>Wizard</code> implementation returns a
     * {@link StaticText StaticText} component that is created and
     * initialized every time this method is called.
     * </p>
     */
    public UIComponent getStepTitleTextComponent() {

	WizardStep step = getCurrentStep();
	return getStepStaticTextComponent(step, STEP_TITLE, step.getTitle());
    }

    /**
     * Return the component to render the step detail text.
     * The id of this component is:</br>
     * <code>getCurrentStep().getId() + "_stpdtl"</code>.</br>
     * This component typically renders below the step title.
     * <p>
     * This <code>Wizard</code> implementation returns a
     * {@link StaticText StaticText} component that is created and
     * initialized every time this method is called.
     * </p>
     */
    public UIComponent getStepDetailTextComponent() {

	WizardStep step = getCurrentStep();
	return getStepStaticTextComponent(step, STEP_DETAIL, step.getDetail());
    }

    /**
     * Return the component to render for the step summary text that
     * appears in the step list.
     * <p>
     * This <code>Wizard</code> implementation returns either:
     * <p>
     * <ul>
     * <li>a {@link StaticText StaticText} component for the current
     * and future steps, created and initialized every time this method.
     * The component id is:</br>
     * <code>step.getId() + "_stpsmmy"</code>.
     * </li>
     * <li>a {@link Hyperlink Hyperlink} component for visited steps.
     * The component is re-initialized every time this method is called.
     * The component id is:</br>
     * <code>step.getId() + "_stplnk" + "_" + step.getId()</code>.</br>
     * The right most "step.getId()" text is interpreted in event handlers to 
     * identify the step to navigate to.
     * </li>
     * </ul>
     * </p>
     * @param step The step being rendered.
     */
    public UIComponent getStepSummaryComponent(WizardStep step) {

	String stepId = step.getId();
	if (getModel().canGotoStep(step)) {
	    String id = ComponentUtilities.createPrivateFacetId(this,
		STEP_LINK);
	    id = id.concat(USCORE).concat(stepId);
	    return getSteplinkComponent(id, step.getSummary(),
		WizardEvent.GOTOSTEP, true,
		getJavaScript(WizardEvent.GOTOSTEP));
	} else {
	    return getStepStaticTextComponent(step, STEP_SUMMARY,
		step.getSummary());
	}
    }

    /**
     * Return the component to render for a branch step's placeholder text that
     * appears in the step list.
     * The component id is:</br>
     * <code>step.getId() + "_stpplhld"</code>.
     * <p>
     * This <code>Wizard</code> implementation returns a 
     * {@link StaticText StaticText} component that is created and 
     * initialized every time this method is called.
     * </p>
     *
     * @param step The step being rendered.
     */
    public UIComponent getStepPlaceholderTextComponent(WizardStep step) {

	Theme theme = ThemeUtilities.getTheme(getFacesContext());
	String placeholderText = ((WizardBranch)step).getPlaceholderText();

	return getStepStaticTextComponent(step, STEP_PLACEHLDR,
	    theme.getMessage(WIZARD_PLACEHOLDER_TEXT,
		new Object[] { placeholderText }));
    }

    /**
     * Return the component to render for the step number that
     * appears in the step list.
     * <p>
     * This <code>Wizard</code> implementation returns either:
     * <p>
     * <ul>
     * <li>a {@link StaticText StaticText} component for the current and
     * future steps, created and initialized every time this method is called.
     * The id of this component is:</br>
     * <code>step.getId() + "_stpnum"</code>.</br>
     * </li>
     * <li>a {@link Hyperlink Hyperlink} component for visited steps.
     * The component is re-initialized every time this method is called.
     * The component id is:</br>
     * <code>step.getId() + "_num" + "_stplnk" + "_" + step.getId()</code>.</br>
     * The right most "step.getId()" text is interpreted in event handlers to 
     * identify the step to navigate to.
     * </li>
     * </ul>
     * </p>
     *
     * @param step The step being rendered.
     * @param numberString The step number.
     */
    public UIComponent getStepNumberComponent(WizardStep step,
	    String numberString) {
	String stepId = step.getId();
	if (getModel().canGotoStep(step)) {
	    String numStpLnk = 	NUM.concat(USCORE).concat(STEP_LINK);
	    String id = ComponentUtilities.createPrivateFacetId(this,
		numStpLnk);  
	    id = id.concat(USCORE).concat(stepId);
	    return getSteplinkComponent(id, numberString,
		WizardEvent.GOTOSTEP, true,
		getJavaScript(WizardEvent.GOTOSTEP));
	} else {    
	    return getStepStaticTextComponent(step, STEP_NUM, numberString);
	}
    }

    /**
     * Return the component to render for the step help text for the
     * current step in the steps pane help tab.
     * The id of this component is:</br>
     * <code>step.getId() + "_stphlp"</code>.
     * <p>
     * This <code>Wizard</code> implementation returns a 
     * {@link StaticText StaticText} component that is created and 
     * initialized every time this method is called.
     * </p>
     */
    public UIComponent getStepHelpTextComponent() {

	WizardStep step = getCurrentStep();
	UIComponent stepHelp = getStepStaticTextComponent(getCurrentStep(),
	    STEP_HELP, step.getHelp());

	// Allow HTML markup
	//
	if (stepHelp != null) {
	    stepHelp.getAttributes().put(ESCAPE_ATTR, WIZARD_FALSE);
	}
	return stepHelp;
    }

    /**
     * Return the component to render for the wizard title.
     * The id of this component is:</br>
     * <code>this.getId() + "_title"</code>.
     * <p>
     * This <code>Wizard</code> implementation returns a 
     * {@link StaticText StaticText} component that is created and 
     * initialized every time this method is called.
     * </p>
     */
    public UIComponent getTitleComponent() {
	
	return getStaticTextComponent(TITLE_FACET, getTitle());
    }

    /**
     * Return the component to render for the steps pane title text
     * when there is no step help and therefore no tabs.
     * The id of this component is:</br>
     * <code>step().getId() + "_stepsPaneTitle"</code>.
     * <p>
     * This <code>Wizard</code> implementation returns a 
     * {@link StaticText StaticText} component that is created and 
     * initialized every time this method is called.
     * </p>
     */
    public UIComponent getStepsPaneTitleComponent() {

	Theme theme = ThemeUtilities.getTheme(getFacesContext());
	String title = theme.getMessage(WIZARD_STEPS_PANE_TITLE);
	UIComponent stepsPaneTitle = getStaticTextComponent(
	    STEPS_PANE_TITLE, title);

	return stepsPaneTitle;
    }

    /**
     * Return a component for various wizard text features.
     * If a facet exists by the name of <code>facetName</code> return it.
     * Otherwise create, initialize and return a {@link StaticText StaticText}
     * component every time this method is called.
     * The component id is:</br>
     * <code>this.getId() + "_" + facetName</code>.
     *
     * <em>The facet names are not publicly documented facets.</em>
     *
     * @param facetName The name of a facet to return.
     * @param text The text to assign to the text property of a created
     * component.
     */
    protected UIComponent getStaticTextComponent(String facetName,
	    String text) {

	UIComponent child = (UIComponent)getFacet(facetName);
	if (child != null) {
	    return child;
	}

	StaticText txt = new StaticText();
	String id = 
	    ComponentUtilities.createPrivateFacetId(this, facetName);
	txt.setId(id);
	txt.setText(text);
	txt.setParent(this);

	return txt;
    }

    /**
     * Return a component for rendering text associated with a step.
     * The component id is:</br>
     * <code>step.getId() + "_" + facetName</code>
     * <p>
     * This <code>Wizard</code> implementation returns a 
     * {@link StaticText StaticText} component that is created and 
     * initialized every time this method is called.
     * </p>
     * <em>The facet names are not publicly documented facets.</em>
     *
     * @param step the WizardStep
     * @param facetName The facetName of the component to return.
     * @param stepText The text to assign to the text property of a created
     * component.
     */
    protected UIComponent getStepStaticTextComponent(
		WizardStep step, String facetName, String stepText) {

	StaticText text = new StaticText();
	text.setId(ComponentUtilities.createPrivateFacetId(step, facetName));
	text.setParent(this);
	text.setText(stepText);
	return text;
    }

    /**
     * Return a StaticText component for rendering various wizard text.
     * The default implementation returns a new StaticText component
     * instance, each time this method is called.
     *
     * @deprecated
     */
    protected UIComponent getStaticTextComponent() {
	return new StaticText();
    }

    /**
     * Return a component for rendering text associated with a step.
     * Create, initialize and return a StaticText component
     * every time this method is called.
     *
     * @param id The id of the component to return.
     * @param stepText The text to assign to the text property of a created
     * component.
     * 
     * @deprecated replaced by {@link #getStepStaticTextComponent(WizardStep,
     * String) getStaticTextComponent}
     */
    protected UIComponent getStepStaticTextComponent(String id,
	String stepText) {

	StaticText text = new StaticText();
	text.setId(id);
	text.setParent(this);
	text.setText(stepText);
	return text;
    }

    // Event handling

    /**
     * Return the <code>Wizard</code> instance ancestor of <code>child</code>
     * identified by <code>wizardId</code>.
     *
     * @param child A descendant of the desired Wizard.
     * @param wizardId The id of a Wizard ancestor.
     */
    public static Wizard getWizard(UIComponent child, String wizardId) {
	return (Wizard)findAncestor(child, wizardId);
    }

    /**
     * Return the <code>UIComponent</code> instance ancestor of
     * <code>descendant</code> identified by <code>ancestorId</code>.
     *
     * @param descendant A descendant of the desired UIComponent.
     * @param ancestorId The id of a Wizard ancestor.
     */
    private static UIComponent findAncestor(UIComponent descendant,
	    String ancestorId) {

	if (ancestorId == null || descendant == null) {
	    return null;
	}
	UIComponent parent = descendant.getParent();
	while (parent != null) {
	    if (ancestorId.equals(parent.getId())) {
		return parent;
	    }
	    parent = parent.getParent();
	}
	return null;
    }

    /**
     * Called from {@link WizardActionListener WizardActionListener}
     * to establish the event state of the wizard.
     *
     * @param source The UIComponent originating the even.
     * @param event One of:</br>
     * <ul>
     * <li>{@link WizardEvent#CANCEL WizardEvent.CANCEL}</li>
     * <li>{@link WizardEvent#CLOSE WizardEvent.CLOSE}</li>
     * <li>{@link WizardEvent#FINISH WizardEvent.FINISH}</li>
     * <li>{@link WizardEvent#GOTOSTEP WizardEvent.GOTOSTEP}</li>
     * <li>{@link WizardEvent#HELPTAB WizardEvent.HELPTAB}</li>
     * <li>{@link WizardEvent#NEXT WizardEvent.NEXT}</li>
     * <li>{@link WizardEvent#PREVIOUS WizardEvent.PREVIOUS}</li>
     * <li>{@link WizardEvent#STEPSTAB WizardEvent.STEPSTAB}</li>
     * </ul>
     */
    public void broadcastEvent(UIComponent source, int event) 
	throws AbortProcessingException {

	this.event = event;
	this.eventSource = source;

	gotoStepId = null;

	switch (event) {
	case WizardEvent.NEXT:
	case WizardEvent.PREVIOUS:
	case WizardEvent.CANCEL:
	case WizardEvent.CLOSE:
	case WizardEvent.FINISH:
	    // Nothing special
	break;
	case WizardEvent.GOTOSTEP:
	    // Need to identify the component id suffix by capturing
	    // the text after "STEP_LINK + USCORE"
	    //
	    String id = source.getId();
	    String suffix = STEP_LINK.concat(USCORE);
	    gotoStepId = id.substring(id.lastIndexOf(suffix) + 
		suffix.length());
	break;
	case WizardEvent.HELPTAB:
	    stepTabActive = false;
	break;
	case WizardEvent.STEPSTAB:
	    stepTabActive = true;
	break;
	}

	// Don't want the application's action listener to fire here
	// so we defeat the render complete of an immediate action.
	//
	throw new AbortProcessingException();
    }

    /**
     * Send a {@link WizardEvent#START WizardEvent.START} event
     * to the component's {@link WizardEventListener WizardEventListener}.
     * This event will be sent in the
     * encodeBegin method, before rendering is begun.</br>
     * The return value from the listener's handleEvent and exceptions
     * are ignored.
     */
    protected void broadcastStartEvent() {
	WizardEventListener listener = getEventListener();
	if (listener != null) {
	    WizardEvent wizardEvent = new WizardEvent(this,
		null, WizardEvent.START, null, null);
	    try {
		listener.handleEvent(wizardEvent);
	    } catch (Exception e) {
		// ignore, nothing to do
		// FIXME: Log the exception.
	    }
	}
	
    }

    /**
     * Send a {@link WizardEvent#COMPLETE WizardEvent.COMPLETE} event
     * to the component's {@link WizardEventListener WizardEventListener}.
     * This event will be sent in the
     * encodeEnd method, after the renderer's encodeEnd method returns.</br>
     * The return value from the listener's handleEvent and exceptions
     * are ignored.
     */
    protected void broadcastCompleteEvent() {
	WizardEventListener listener = getEventListener();
	if (listener != null) {
	    WizardEvent wizardEvent = new WizardEvent(this,
		null, WizardEvent.COMPLETE, null, null);
	    try {
		listener.handleEvent(wizardEvent);
	    } catch (Exception e) {
		// ignore, nothing to do
		// FIXME: Log the exception.
	    }
	}
	
    }

    /**
     * This method broadcasts a
     * {@link WizardEvent WizardEvent} event that is queued as a result
     * of an immediate <code>ActionEvent</code> from one of the wizard's
     * navigation components. If <code>facesEvent</code> is not an instance of
     * {@link WizardEvent WizardEvent} the method just returns.
     * <p>
     * If <code>facesEvent</code> is an instance of
     * {@link WizardEvent WizardEvent} a new {@link WizardEvent WizardEvent}
     * is constructed with addtional information based on the navigation
     * event and the wizard state, including:
     * </p>
     * <p>
     * <ul>
     * <li>the wizard instance as the source of the event
     * {@link WizardEvent#getSource() WizardEvent.getSource}</li>
     * <li>the current step, which may be null
     * {@link WizardEvent#getStep() WizardEvent.getStep}</li>
     * <li>the button or link component instance that triggered the
     * original immediate action event
     * {@link WizardEvent#getEventSource() WizardEvent.getEventSource}</li>
     * <li>the {@link WizardEvent WizardEvent} constant that identifies the
     * event {@link WizardEvent#getEvent() WizardEvent.getEvent}.</li>
     * <li>the next {@link WizardStep WizardStep} component id if the event is
     * <code>WizardEvent.GOTOSTEP</code>
     * {@link WizardEvent#getGotoStepId() WizardEvent.getgetGotoStepId},
     * otherwise null.</li>
     * </ul>
     * </p>
     * <p>
     * The event is then broadcast to
     * {@link WizardEventListener WizardEventListeners} configured on the
     * current step and the wizard, and then forwarded to the wizard model
     * in the following manner:
     * <p>
     * <ol>
     * <li>If the current step is not null and its
     * {@link WizardStep#getEventListener() getEventListener} method does
     * not return null, the
     * {@link WizardEventListener#handleEvent(WizardEvent) handleEvent}
     * method is called on the returned instance.
     * </li>
     * <li>If the step listener's handleEvent method does not throw an
     * exception the wizard's
     * {@link Wizard#getEventListener() getEventListner} is called
     * and if it returns a non null instance, its 
     * {@link WizardEventListener#handleEvent(WizardEvent) handleEvent}
     * method is called.
     * </li>
     * <li>If no exception occurs and the last listener called returns
     * <code>true</code>, the event is forwarded to the wizard model.</br>
     * Note that if both listeners exist both listeners are sent the
     * event, even if the step's listener returns false. This means that
     * if the wizard's listener returns <code>true</code>, since it is
     * called last, the event is forwarded to the wizard model.</br>
     * If the last listener called returns <code>false</code> the event
     * is not forwarded to the model and the current step will be
     * redisplayed.
     * </li>
     * </ol>
     * If either listener's
     * {@link WizardEventListener#handleEvent(WizardEvent) handleEvent}
     * method throws an exception the event is changed to
     * {@link WizardEvent#CANCEL WizardEvent.CANCEL} and forwarded to the
     * wizard model, effectively ending the wizard session, as if the
     * user had clicked the cancel button.
     *
     * @param facesEvent must be an instance of {@link WizardEvent WizardEvent}
     */
    public void broadcast(FacesEvent facesEvent)
        throws AbortProcessingException {

        // Perform standard superclass processing
	// Iterates over "listeners".
	//
        if (facesEvent instanceof WizardEvent) {

	    boolean result = true;
	    WizardStep step = getCurrentStep();
	    // Check for null step.
	    // Shouldn't happen.
	    // FIXME: Should log this situation
	    //
	    WizardEventListener listener = step == null ? null :
		step.getEventListener();

	    WizardEvent wizardEvent = new WizardEvent(this,
		eventSource, event, step, gotoStepId);

	    try {
		if (listener != null) {
		    result = listener.handleEvent(wizardEvent);
		}
		listener = getEventListener();
		if (listener != null) {
		    result = listener.handleEvent(wizardEvent);
		}
	    } catch (Exception e) {
		result = true;
		// Make sure we update the wizard's idea of the event.
		//
		event = WizardEvent.CANCEL;
		wizardEvent.setEvent(event);
	    }
	    // If there are no listeners, always let the model
	    // handle the event.
	    //
	    if (result) {
		getModel().handleEvent(wizardEvent);
	    }
        }

    }

    // This should be in the renderer
    // WizardStep js takes precedence over Wizard js.
    // This makes sense if Wizard js is an alternative to 
    // assigning js for every step. If the developer wants
    // the Wizard js also, then they can incorporate it into the
    // step js.
    //
    /**
     * Return the javascript method for the <code>javaScriptEvent</code>.
     * This is effectively the "onclick" handler for the buttons and
     * hyperlinks. The wizard step's javascript takes precedence
     * ove the wizard's javascript.
     */
    private String getJavaScript(int javaScriptEvent) {

	String js = null;
	WizardStep step = getCurrentStep();

	switch (javaScriptEvent) {
	case WizardEvent.NEXT:
	    js = step.getOnNext();
	    if (js == null) {
		js = getOnNext();
	    }
	break;
	case WizardEvent.PREVIOUS:
	    js = step.getOnPrevious();
	    if (js == null) {
		js = getOnPrevious();
	    }
	break;
	case WizardEvent.CANCEL:
	    js = step.getOnCancel();
	    if (js == null) {
		js = getOnCancel();
	    }
	break;
	case WizardEvent.CLOSE:
	    js = step.getOnClose();
	    if (js == null) {
		js = getOnClose();
	    }
	break;
	case WizardEvent.FINISH:
	    js = step.getOnFinish();
	    if (js == null) {
		js = getOnFinish();
	    }
	break;
	case WizardEvent.GOTOSTEP:
	    js = step.getOnStepLink();
	    if (js == null) {
		js = getOnStepLink();
	    }
	break;
	case WizardEvent.HELPTAB:
	    js = step.getOnHelpTab();
	    if (js == null) {
		js = getOnHelpTab();
	    }
	break;
	case WizardEvent.STEPSTAB:
	    js = step.getOnStepsTab();
	    if (js == null) {
		js = getOnStepsTab();
	    }
	break;
	}

	return js;
    }

    // Constants

    private static final String MODEL = "model";
    private static final Boolean WIZARD_TRUE = Boolean.TRUE;
    private static final Boolean WIZARD_FALSE = Boolean.FALSE;

    // Facets
    //
    private static final String RESULTS_FACET = "results"; //NOI18N

    private static final String NEXT_FACET = "next"; //NOI18N
    private static final String PREVIOUS_FACET = "previous"; //NOI18N
    private static final String FINISH_FACET = "finish"; //NOI18N
    private static final String CANCEL_FACET = "cancel"; //NOI18N
    private static final String CLOSE_FACET = "close"; //NOI18N
    
    private static final String TABS_FACET = "tabs"; //NOI18N
    private static final String TITLE_FACET = "title"; //NOI18N
    private static final String INDICATOR_FACET = "stepIndicator"; //NOI18N
    private static final String STEPS_PANE_TITLE = "stepsPaneTitle"; //NOI18N

    // Other Constants
    //
    private static final String USCORE = "_"; //NOI18N

    private static final String STEP_TEXT = "stptxt"; //NOI18N
    private static final String STEP_NUM = "stpnum"; //NOI18N
    private static final String STEP_LINK = "stplnk"; //NOI18N
    private static final String STEP_HELP = "stphlp"; //NOI18N
    private static final String STEP_TITLE = "stpttl"; //NOI18N
    private static final String STEP_TITLE_LABEL = "stpttllbl"; //NOI18N
    private static final String STEP_DETAIL = "stpdtl"; //NOI18N
    private static final String STEP_SUMMARY = "stpsmmy"; //NOI18N
    private static final String STEP_PLACEHLDR = "stpplhld"; //NOI18N
    private static final String STEP_TAB = "stptb"; //NOI18N
    private static final String HELP_TAB = "hlptb"; //NOI18N
    private static final String NUM 	 = "num"; //NOI18N

    // Attributes
    //
    private static final String DISABLED_ATTR = "disabled"; //NOI18N
    private static final String IMMEDIATE_ATTR = "immediate"; //NOI18N
    private static final String MINI_ATTR = "mini"; //NOI18N
    private static final String ESCAPE_ATTR = "escape"; //NOI18N
    private static final String ONCLICK_ATTR = "onClick"; //NOI18N

    // Wizard Theme Text
    //
    private static final String WIZARD_CANCEL = "Wizard.cancel"; //NOI18N
    private static final String WIZARD_CLOSE = "Wizard.close"; //NOI18N
    private static final String WIZARD_FINISH = "Wizard.finish"; //NOI18N
    private static final String WIZARD_NEXT = "Wizard.next"; //NOI18N
    private static final String WIZARD_PREVIOUS = "Wizard.previous"; //NOI18N

    private static final String WIZARD_STEP_TITLE_LABEL =
	"Wizard.stepTitleLabel"; //NOI18N
    private static final String WIZARD_STEP_TAB = "Wizard.stepTab"; //NOI18N
    private static final String WIZARD_HELP_TAB = "Wizard.helpTab"; //NOI18N
    private static final String WIZARD_SKIP_LINK_ALT =
	"Wizard.skipLinkAlt"; //NOI18N
    private static final String WIZARD_CURRENT_STEP_ALT =
	"Wizard.currentStepAlt"; //NOI18N
    private static final String WIZARD_TAB_TOOLTIP =
	"Wizard.tabToolTip"; //NOI18N
    private static final String WIZARD_PLACEHOLDER_TEXT =
	"Wizard.placeholderText";
    private static final String WIZARD_STEPS_PANE_TITLE =
	"Wizard.stepsPaneTitle";

    // ############################
    //
    // From WizardBase
    //
    // ############################

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    // eventListener
    /**
     * The eventListener attribute is used to specify an object to 
     * handle an event that is triggered when a user activates a 
     * component in the wizard.
     * The eventListener attribute value must be a JavaServer Faces EL
     * expression that resolves to an instance of 
     * <code>com.sun.webui.jsf.event.WizardEventListener</code>.
     *  <p>
     *  The return value of the wizard component's call to the event listener's 
     *  handleEvent() method controls the processing of the current step 
     *  that is being performed, and determines whether the next step or a 
     *  previous step, etc. can be navigated to.
     * </p>
     *  See the <a href="#EventListeners">Event Listeners</a> section also.
     */
    @Property(name="eventListener", displayName="Wizard Event Listener")
    private WizardEventListener eventListener = null;

    /**
     * The eventListener attribute is used to specify an object to 
     * handle an event that is triggered when a user activates a 
     * component in the wizard.
     * The eventListener attribute value must be a JavaServer Faces EL
     * expression that resolves to an instance of 
     * <code>com.sun.webui.jsf.event.WizardEventListener</code>.
     *  <p>
     *  The return value of the wizard component's call to the event listener's 
     *  handleEvent() method controls the processing of the current step 
     *  that is being performed, and determines whether the next step or a 
     *  previous step, etc. can be navigated to.
     * </p>
     *  See the <a href="#EventListeners">Event Listeners</a> section also.
     */
    public WizardEventListener getEventListener() {
	if (this.eventListener != null) {
            return this.eventListener;
        }
        ValueExpression _vb = getValueExpression("eventListener");
        if (_vb != null) {
            return (WizardEventListener)
		_vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The eventListener attribute is used to specify an object to 
     * handle an event that is triggered when a user activates a 
     * component in the wizard. The eventListener attribute value 
     * must be a JavaServer Faces EL expression that resolves to 
     * an instance of <code>com.sun.webui.jsf.event.WizardEventListener</code>.
     * <p>
     * The return value of the wizard component's call to the event listener's 
     * handleEvent() method controls the processing of the current step 
     * that is being performed, and determines whether the next step or a 
     * previous step, etc. can be navigated to.
     * </p>
     * See the <a href="#EventListeners">Event Listeners</a> section also.
     * @see #getEventListener()
     */
    public void setEventListener(WizardEventListener eventListener) {
        this.eventListener = eventListener;
    }

    // isPopup
    /**
     * The wizard is being targeted to a popup window.
     * Default is <code>true</code>. Set this property to <code>false</code>
     * if the wizard is to appear within a main browser window.
     */
    @Property(name="isPopup", displayName="isPopup",
	isHidden=true, isAttribute=false)
    private boolean isPopup = false;
    private boolean isPopup_set = false;

    /**
     * The wizard is being targeted to a popup window.
     * Default is <code>true</code>. Set this property to <code>false</code>
     * if the wizard is to appear within a main browser window.
     */
    public boolean isIsPopup() {
        if (this.isPopup_set) {
            return this.isPopup;
        }
        ValueExpression _vb = getValueExpression("isPopup");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * The wizard is being targeted to a popup window.
     * Default is <code>true</code>. Set this property to <code>false</code>
     * if the wizard is to appear within a main browser window.
     * @see #isIsPopup()
     */
    public void setIsPopup(boolean isPopup) {
        this.isPopup = isPopup;
        this.isPopup_set = true;
    }

    // model
    /**
     * The <code>model</code> property is a value binding that
     * resolves to an instance of <code>WizardModel</code>. This instance
     * is an alternative to the default <code>WizardModelBase</code>
     * instance that controls the flow of steps in the wizard.
     */
    @Property(name="model", displayName="Wizard Model", isHidden=true)
    private com.sun.webui.jsf.model.WizardModel model = null;

    /**
     * The <code>model</code> property is a value binding that
     * resolves to an instance of <code>WizardModel</code>. This instance
     * is an alternative to the default <code>WizardModelBase</code>
     * instance that controls the flow of steps in the wizard.
     * @see #getModel()
     */
    public void setModel(WizardModel model) {
        this.model = model;
    }

    // onCancel
    /**
     * Scripting code executed when the Cancel button is clicked.
     */
    @Property(name="onCancel", displayName="Cancel Script")
    private String onCancel = null;

    /**
     * Scripting code executed when the Cancel button is clicked.
     */
    public String getOnCancel() {
        if (this.onCancel != null) {
            return this.onCancel;
        }
        ValueExpression _vb = getValueExpression("onCancel");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the Cancel button is clicked.
     * @see #getOnCancel()
     */
    public void setOnCancel(String onCancel) {
        this.onCancel = onCancel;
    }

    // onClose
    /**
     * Scripting code executed when the Close button is clicked.
     */
    @Property(name="onClose", displayName="Close Script")
    private String onClose = null;

    /**
     * Scripting code executed when the Close button is clicked.
     */
    public String getOnClose() {
        if (this.onClose != null) {
            return this.onClose;
        }
        ValueExpression _vb = getValueExpression("onClose");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the Close button is clicked.
     * @see #getOnClose()
     */
    public void setOnClose(String onClose) {
        this.onClose = onClose;
    }

    // onFinish
    /**
     * Scripting code executed when the Finish button is clicked.
     */
    @Property(name="onFinish", displayName="Finish Script")
    private String onFinish = null;

    /**
     * Scripting code executed when the Finish button is clicked.
     */
    public String getOnFinish() {
        if (this.onFinish != null) {
            return this.onFinish;
        }
        ValueExpression _vb = getValueExpression("onFinish");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the Finish button is clicked.
     * @see #getOnFinish()
     */
    public void setOnFinish(String onFinish) {
        this.onFinish = onFinish;
    }

    // onHelpTab
    /**
     * Scripting code executed when the Help tab is clicked.
     */
    @Property(name="onHelpTab", displayName="Help Tab Script")
    private String onHelpTab = null;

    /**
     * Scripting code executed when the Help tab is clicked.
     */
    public String getOnHelpTab() {
        if (this.onHelpTab != null) {
            return this.onHelpTab;
        }
        ValueExpression _vb = getValueExpression("onHelpTab");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the Help tab is clicked.
     * @see #getOnHelpTab()
     */
    public void setOnHelpTab(String onHelpTab) {
        this.onHelpTab = onHelpTab;
    }

    // onNext
    /**
     * Scripting code executed when the Next button is clicked.
     */
    @Property(name="onNext", displayName="Next Script")
    private String onNext = null;

    /**
     * Scripting code executed when the Next button is clicked.
     */
    public String getOnNext() {
        if (this.onNext != null) {
            return this.onNext;
        }
        ValueExpression _vb = getValueExpression("onNext");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the Next button is clicked.
     * @see #getOnNext()
     */
    public void setOnNext(String onNext) {
        this.onNext = onNext;
    }

    // onPopupDismiss
    /**
     * Scripting code that is invoked when the wizard popup is dismissed. 
     * If the wizard is not in a popup, the onPopupDismiss attribute is 
     * ignored. The scripting code must specify what happens in the browser 
     * when the window is closed. For example, the form of the parent window
     * that opened the popup should be submitted, and the browser might be 
     * redirected, or the display refreshed to reflect the task completed 
     * by the user. These activities provide feedback to the user.
     */
    @Property(name="onPopupDismiss", displayName="onPopupDismiss")
    private String onPopupDismiss = null;

    /**
     * Scripting code that is invoked when the wizard popup is dismissed. 
     * If the wizard is not in a popup, the onPopupDismiss attribute is 
     * ignored. The scripting code must specify what happens in the browser 
     * when the window is closed. For example, the form of the parent window
     * that opened the popup should be submitted, and the browser might be 
     * redirected, or the display refreshed to reflect the task completed 
     * by the user. These activities provide feedback to the user.
     */
    public String getOnPopupDismiss() {
        if (this.onPopupDismiss != null) {
            return this.onPopupDismiss;
        }
        ValueExpression _vb = getValueExpression("onPopupDismiss");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code that is invoked when the wizard popup is dismissed. 
     * If the wizard is not in a popup, the onPopupDismiss attribute is 
     * ignored. The scripting code must specify what happens in the browser 
     * when the window is closed. For example, the form of the parent window
     * that opened the popup should be submitted, and the browser might be 
     * redirected, or the display refreshed to reflect the task completed 
     * by the user. These activities provide feedback to the user.
     * @see #getOnPopupDismiss()
     */
    public void setOnPopupDismiss(String onPopupDismiss) {
        this.onPopupDismiss = onPopupDismiss;
    }

    // onPrevious
    /**
     * Scripting code executed when the Previous button is clicked.
     */
    @Property(name="onPrevious", displayName="Previous Script")
    private String onPrevious = null;

    /**
     * Scripting code executed when the Previous button is clicked.
     */
    public String getOnPrevious() {
        if (this.onPrevious != null) {
            return this.onPrevious;
        }
        ValueExpression _vb = getValueExpression("onPrevious");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the Previous button is clicked.
     * @see #getOnPrevious()
     */
    public void setOnPrevious(String onPrevious) {
        this.onPrevious = onPrevious;
    }

    // onStepLink
    /**
     * Scripting code executed when a Step link is clicked.
     */
    @Property(name="onStepLink", displayName="Step Link Script")
    private String onStepLink = null;

    /**
     * Scripting code executed when a Step link is clicked.
     */
    public String getOnStepLink() {
        if (this.onStepLink != null) {
            return this.onStepLink;
        }
        ValueExpression _vb = getValueExpression("onStepLink");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when a Step link is clicked.
     * @see #getOnStepLink()
     */
    public void setOnStepLink(String onStepLink) {
        this.onStepLink = onStepLink;
    }

    // onStepsTab
    /**
     * Scripting code executed when the Steps tab is clicked.
     */
    @Property(name="onStepsTab", displayName="Steps Tab Script")
    private String onStepsTab = null;

    /**
     * Scripting code executed when the Steps tab is clicked.
     */
    public String getOnStepsTab() {
        if (this.onStepsTab != null) {
            return this.onStepsTab;
        }
        ValueExpression _vb = getValueExpression("onStepsTab");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Scripting code executed when the Steps tab is clicked.
     * @see #getOnStepsTab()
     */
    public void setOnStepsTab(String onStepsTab) {
        this.onStepsTab = onStepsTab;
    }

    // steps
    /**
     * Use the steps attribute to specify the wizard steps programmatically,
     * instead of using the <code>webuijsf:wizardStep</code> tags in the JSP.
     * The steps attribute must be specified as an <code>ArrayList</code> or 
     * <code>List</code> of <code>WizardStep</code>, <code>WizardBranch</code>,
     * <code>WizardBranchSteps</code>, or <code>WizardSubstepBranch</code>
     * components.
     */
    @Property(name="steps", displayName="Wizard Steps", category="Data", 
              editorClassName="com.sun.rave.propertyeditors.binding.ValueBindingPropertyEditor")
    private Object steps = null;

    /**
     * Use the steps attribute to specify the wizard steps programmatically,
     * instead of using the <code>webuijsf:wizardStep</code> tags in the JSP.
     * The steps attribute must be specified as an <code>ArrayList</code> or 
     * <code>List</code> of <code>WizardStep</code>, <code>WizardBranch</code>,
     * <code>WizardBranchSteps</code>, or <code>WizardSubstepBranch</code>
     * components.
     */
    public Object getSteps() {
        if (this.steps != null) {
            return this.steps;
        }
        ValueExpression _vb = getValueExpression("steps");
        if (_vb != null) {
            return (Object) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Use the steps attribute to specify the wizard steps programmatically,
     * instead of using the <code>webuijsf:wizardStep</code> tags in the JSP.
     * The steps attribute must be specified as an <code>ArrayList</code> or 
     * <code>List</code> of <code>WizardStep</code>, <code>WizardBranch</code>,
     * <code>WizardBranchSteps</code>, or <code>WizardSubstepBranch</code>
     * components.
     * @see #getSteps()
     */
    public void setSteps(Object steps) {
        this.steps = steps;
    }

    // style
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)")
    private String style = null;

    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression _vb = getValueExpression("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    // styleClass
    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element
     * when this component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)")
    private String styleClass = null;

    /**
     * CSS style class(es) to be applied to the outermost HTML element
     * when this component is rendered.
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression _vb = getValueExpression("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * CSS style class(es) to be applied to the outermost HTML element
     * when this component is rendered.
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    // title
    /**
     * The text to be displayed as the title for the wizard.
     * The title is displayed in the top line, and extends the full width of
     * the wizard window.
     * @see #getTitle()
     */
    @Property(name="title", displayName="Wizard Title")
    private String title = null;

    /**
     * The text to be displayed as the title for the wizard.
     * The title is displayed in the top line, and extends the full width of
     * the wizard window.
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        ValueExpression _vb = getValueExpression("title");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The text to be displayed as the title for the wizard.
     * The title is displayed in the top line, and extends the full width of
     * the wizard window.
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
    }

    // visible
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible")
    private boolean visible = false;
    private boolean visible_set = false;

    /**
     * Indicates whether the component is viewable
     * by the user in the rendered HTML page.
     * Returns <code>true</code> by default and the HTML markup
     * for the component HTML is rendered in the page and the 
     * component is visible to the user.
     * If <code>false</code> is returned the the HTML markup for the
     * component is present in the rendered page, but the component
     * is not visible to the user.
     * @see #setVisible
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueExpression _vb = getValueExpression("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }

    /**
     * Indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to <code>false
     * </code>, the HTML markup for the component is present in the 
     * rendered page, but the component is viewable to the user.
     * By default, the HTML markup for the component is included in the
     * rendered page and is visible to the user. If <code>visible</code>
     * is <code>false</code>, the component is not viewable to the user,
     * but the HTML markup is included in the rendered
     * page and can still be processed on subsequent form
     * submissions because the HTML is present.
     * @see #isVisible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }

    /**
     * This implementation calls super.processRestortState and then
     * calls getModel().initialize().
     * This is needed because the order of state restoration
     * does not restore Wizard children until after the model is
     * restored. The model must rebuild the step children when they
     * are actual children of the Wizard, since they are new instances.
     */
    public void processRestoreState(FacesContext context, Object state) {

	super.processRestoreState(context, state);

	// Need to do this because all children now have
	// new id's. Children are restored in UIComponentBase
	// processRestoreState.
	//
	getModel().initialize(this);

	// Always set event to NOEVENT here
	// in case we are in a request from a component
	// within the wizard step page. Doing this here ensures that
	// the START event is never set except the first 
	// render cycle. In server side state saving, the 
	// event member is preserved.
	//
	event = WizardEvent.NOEVENT;
    }

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);

        this.eventListener = (WizardEventListener)
	    restoreAttachedState(_context, _values[1]);

        this.isPopup = ((Boolean) _values[2]).booleanValue();
        this.isPopup_set = ((Boolean) _values[3]).booleanValue();

        this.model = (WizardModel)restoreAttachedState(_context, _values[4]);

        this.onCancel = (String) _values[5];
        this.onClose = (String) _values[6];
        this.onFinish = (String) _values[7];
        this.onHelpTab = (String) _values[8];
        this.onNext = (String) _values[9];
        this.onPopupDismiss = (String) _values[10];
        this.onPrevious = (String) _values[11];
        this.onStepLink = (String) _values[12];
        this.onStepsTab = (String) _values[13];
        this.steps = (Object)restoreAttachedState(_context, _values[14]);
        this.style = (String) _values[15];
        this.styleClass = (String) _values[16];
        this.title = (String) _values[17];
        this.visible = ((Boolean) _values[18]).booleanValue();
        this.visible_set = ((Boolean) _values[19]).booleanValue();
	this.stepTabActive = ((Boolean) _values[20]).booleanValue();

    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[21];
        _values[0] = super.saveState(_context);

	_values[1] = saveAttachedState(_context, this.eventListener);

        _values[2] = this.isPopup ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.isPopup_set ? Boolean.TRUE : Boolean.FALSE;

	// It must implement WizardModel which is a StateHolder if
	// there is no valuebinding.
	//
	_values[4] = saveAttachedState(_context, this.model);

        _values[5] = this.onCancel;
        _values[6] = this.onClose;
        _values[7] = this.onFinish;
        _values[8] = this.onHelpTab;
        _values[9] = this.onNext;
        _values[10] = this.onPopupDismiss;
        _values[11] = this.onPrevious;
        _values[12] = this.onStepLink;
        _values[13] = this.onStepsTab;
        _values[14] = saveAttachedState(_context, this.steps);
        _values[15] = this.style;
        _values[16] = this.styleClass;
        _values[17] = this.title;
        _values[18] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;

	_values[20] = this.stepTabActive ? Boolean.TRUE : Boolean.FALSE;

        return _values;
    }
}
