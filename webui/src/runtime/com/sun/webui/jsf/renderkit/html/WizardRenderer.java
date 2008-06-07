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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.Wizard;
import com.sun.webui.jsf.component.WizardStep;
import com.sun.webui.jsf.model.WizardStepListItem;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.util.Iterator;
import java.text.MessageFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Render a Wizard component. This renderer is responsible for rendering
 * children and directly renders the current WizardStep component.
 * The wizard is comprised of several layout areas:
 * <ul>
 * <li>Wizard title</li>
 * <li>A step list and step help pane appearing to the left of the main
 * body.</li>
 * <li>A step title appearing To the right of the steps and help pane and
 * above the main body</li>
 * <li>A step detail appearing below the step title.</li>
 * <li>The step body appearing below the step detail and to the right
 * of the step list and help pane containg the elements for the current
 * step.</li>
 * <li>Sequencing controls appearing below the main body.</li>
 * </ul>
 * <p>
 * There are also some decorative rules that
 * separate the different areas. Scrollbars are provided as
 * determined by the size of the content in each area.
 * </p>
 * <p>
 * Rendering the Wizard is abstracted into the following call
 * structure. Any method can be implemented to override
 * default implementation. It is important to understand the
 * layout context into which the new implementation is inserting
 * its markup. See the javadoc for default implementation for
 * rendered markup.
 * <p>
 *
 * encodeBegin(..)
 *    renderStart(...)
 *        renderWizardBegin(...)
 *            renderSkipLink(...) // private
 *
 * encodeChildren(...)
 *    // Currently does nothing.
 *
 * encodeEnd(...)
 *   renderEnd(...)
 *     renderWizard(...)
 *     
 *        renderTitle(...)
 *     	    renderTitleBegin(...)
 *     	    renderTitleText(...)
 *     	    renderTitleEnd(...)
 *
 *	  renderStepsBar(..)
 *              renderTabsBar(...) 
 *
 *		or
 *		
 *		renderEmptyBar(..)
 *
 *        renderStepsPane(...)
 *     		renderStepListBegin(...)
 *     		renderStepList(...)
 *     		    renderSubstep(...) // For each step
 *     		        renderStepNumber(...)
 *     			renderStepSummary(...)
 *
 *		    or
 *
 *		    renderStep(...)
 *     		        renderStepNumber(...)
 *     		        renderStepSummary(...)
 *		    or
 *
 *     		    renderCurrentStep(...)
 * 		        renderCurrentStepIndicator(...)
 *			renderStep(...)
 *     			    renderStepNumber(...)
 *     			    renderStepSummary(...)
 *		    or
 *
 *     		    renderBranchStep(...)
 *			renderPlaceholderText(...)
 *     		renderStepListEnd(...)
 *
 *     		or
 *
 *     		renderStepHelp(...)
 *     		    renderStepHelpBegin(...)
 *     		    renderStepHelpText(...)
 *     		    renderStepHelpEnd(...)
 *
 *        renderTask(context, component, theme, writer);
 *     	    renderTaskBegin(...)
 *     		renderSkipAnchor(...)
 *     	    renderTaskHeader(...)
 *     		renderStepTitle(...)
 *     		    renderStepTitleBegin(...)
 *     	 	    renderStepTitleLabelText(...)
 *     		    renderStepTitleText(...)
 *     		    renderStepTitleEnd(...)
 *     		renderStepDetail(...)
 *     		    renderStepDetailBegin(...)
 *     		    renderStepDetailText(...)
 *     		    renderStepDetailEnd(...)
 *     	    renderStepTask(...)
 *     		renderStepTaskBegin(...)
 *     		// dispatch subview
 *     		renderStepTaskEnd(...)
 *     	    renderTaskEnd(...)
 *
 *        renderControlBar(...)
 *     	    renderControlBarBegin(...)
 *          renderControlBarSpacer(...)  	    
 *     	    renderLeftControlBar(...)
 *     		renderLeftControlBarBegin(...)
 *     		renderPreviousControl(...)
 *     		renderNextControl(...)
 *     		or
 *     		renderFinishControl(...)
 *     		renderLeftControlBarEnd(...)
 *     	    renderRightControlBar(...)
 *     		renderRightControlBarBegin(...)
 *     		    renderCancelControl(...)
 *     		    or
 *     		    renderCloseControl(...)
 *     		renderRightControlBarEnd(...)
 *     	    renderControlBarEnd(...)
 *
 *      renderWizardEnd(context, component, theme, writer);
 *
 * <em>refer to HCI wizard guidelines for details.</em>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Wizard"))
public class WizardRenderer extends AbstractRenderer {


    /**
     * Construct a <code>WizardRenderer</code>.
     */
    public WizardRenderer() {
    }

    /**
     * The <code>WizardRenderer</code> is responsible for rendering the
     * <code>WizardStep</code> children.
     */
    public boolean getRendersChildren() {
	return true;
    }

    /**
     * <p>A wizard can be rendered in a popup or inline within a primary
     * application page.
     * </p>
     * <p>
     * When the wizard is in a popup it must be closed. This is accomplished
     * by rendering some javascript to close the popup and if necessary
     * forwarding to another application page.
     * When the wizard is not in a popup then only forwarding is performed
     * if necessary.
     * </p>
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
/*
    protected void renderWizardClose(FacesContext context,
	    UIComponent component, Theme theme,
	    ResponseWriter writer) throws IOException {

	// Assumes that the wizard javascript has been included
	// and a wizard js object has been created
	//
	writer.startElement(SCRIPT, component);
	writer.writeAttribute(TYPE, TEXT_JAVASCRIPT, null);

	renderJsClose(context, component, writer);

	writer.endElement(SCRIPT);
    }
*/
    /**
     * Render javascript to close the popup window. Output javascript
     * specified in the <code>onPopupDismiss</code> property.
     * If the property is empty render an call to the Wizard javascript
     * object closePopup() function.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected String getWizardCloseJavaScript(FacesContext context,
	    UIComponent component) throws IOException {
	String onPopupDismiss = (String) component.getAttributes().get(
            ONPOPUPDISMISS);

	if (onPopupDismiss == null || onPopupDismiss.length() == 0) {
            Object[] args = new Object[] { 
                JavaScriptUtilities.getModuleName("_html.wizard")
            };
	    onPopupDismiss = MessageFormat.format(CLOSEPOPUPJS, args);
	}
	return onPopupDismiss;
    }

    /**
     * If the component is not a <code>Wizard</code> component throw
     * <code>IllegalArgumentException</code> otherwise begin rendering
     * the component. Call the wizard component's isComplete() method to
     * determine if the wizard has completed and proceed appropriately if the
     * wizard was rendered in a popup or inline one browser page.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @throws IllegalArgumentException
     */
    protected void renderStart(FacesContext context, UIComponent component,
		    ResponseWriter writer) throws IOException {

	// Is component "isa" Wizard ?
	//
	if (!(component instanceof Wizard)) {
	    throw new IllegalArgumentException(
		getMessage(MSG_COMPONENT_NOT_WIZARD));
	}

	Theme theme = ThemeUtilities.getTheme(context);

        // Always render outer div tags for HTML element functions to be valid.
        // User may need to inokve document.getElementById(id).closeAndForward(...)
	renderWizardBegin(context, component, theme, writer);
    }

    // We can use this to indicate that all body content has beed read
    // and use this point logically to set up Wizard rendering.
    // It is equivalent to encodeEnd and is called immediately before.
    //
    // We could consider this the Wizard Body and treat Step's as
    // children here.
    //
    /**
     *  Does nothing.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     */
    public void encodeChildren(FacesContext context, UIComponent component) {

	/*
	if (((Wizard)component).isComplete()) {
	    return;
	}
	*/
    }

    /**
     * Render the majority of the wizard component.
     * Return immediately if isComplete() returns true.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
	Theme theme = ThemeUtilities.getTheme(context);
	if (!((Wizard) component).isComplete()) {
	    renderWizard(context, component, theme, writer);
	}
	renderWizardEnd(context, component, theme, writer);
    }

    /**
     * Render the beginning of the layout container for the entire Wizard.
     * 
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderWizardBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	String wizId = component.getClientId(context);

	// Enclose the whole wizard in a div
	//
	// Not in Lockhart Wizard
	//
	writer.startElement(DIV, component);
	writer.writeAttribute(ID, wizId, ID);
	
	String style = (String)component.getAttributes().get(STYLE);
	if (style != null) {
	    writer.writeAttribute(STYLE, style, STYLE);
	}
	
	String styles =  RenderingUtilities.getStyleClasses(context,
		component, theme.getStyleClass(ThemeStyles.WIZARD));
	writer.writeAttribute(CLASS, styles, null);

	// Create a "skip" link that identifies the top
	// of the wizard. There will be a target anchor to the
	// main wizard body.
	//
	// Use clientId for the anchor text, so it should be
	// unique if there is  more than one wizard on a page.
	//
	String toolTip = (String)component.getAttributes().get(TOOLTIP);
	if (toolTip == null) {
	    toolTip = theme.getMessage(WIZARD_SKIP_LINK_ALT);
	}
	renderSkipLink(context, component, theme, writer,
		component.getClientId(context), toolTip);
    }

    /**
     * Render the five main areas of the wizard.
     *
     * <ul>
     * <li>Wizard Title</li>
     * <li>Wizard Steps Bar</li>
     * <li>Steps Pane</li>
     * <li>Task</li>
     * <li>Navigation Buttons</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderWizard(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	renderTitle(context, component, theme, writer);
	renderStepsBar(context, component, theme, writer);
	renderStepsPane(context, component, theme, writer);
	renderTask(context, component, theme, writer);
	renderControlBar(context, component, theme, writer);
    }


    /**
     * Render the end of the layout container for the entire Wizard.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderWizardEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	// Enclose the whole wizard in a div
	//
	writer.endElement(DIV);

        try {
            // Append properties.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", component.getClientId(context));
            json.put("facesViewState", 
		javax.faces.render.ResponseStateManager.VIEW_STATE_PARAM);

            // Append JavaScript.
            buff.append(JavaScriptUtilities.getModule("_html.wizard"))
                .append("\n") // NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.wizard._init")) // NOI18N
                .append("(") //NOI18N
                .append(JSONUtilities.getString(json))
                .append(");"); //NOI18N

            // Render JavaScript to close wizard after init function is called.
            if (((Wizard) component).isComplete()) {
                buff.append(getWizardCloseJavaScript(context, component));
            }
            
            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(component, writer,
                buff.toString(), JavaScriptUtilities.isParseOnLoad());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Render the Wizard title and structural layout that surrounds the
     * title text.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTitle(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	// Don't output anything if there is no title
	//
	if (!show(component, SHOWTITLE)) {
	    return;
	}

	renderTitleBegin(context, component, theme, writer);
	renderTitleText(context, component, writer);
	renderTitleEnd(context, component, theme, writer);
    }

    /**
     * Render the beginning of the layout container for the Wizard title.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTitleBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_TITLE_BAR), null);
    }

    /**
     * Render the actual Wizard title text
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTitleText(FacesContext context,
	    UIComponent component, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent title = wizard.getTitleComponent();
	if (title != null) {
	    RenderingUtilities.renderComponent(title, context);
	}
    }

    /**
     * Render the end of the layout container for the Wizard title.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTitleEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.endElement(DIV);
    }
   
    /**
     * Render the content of the steps bar. 
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderStepsBar(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;

	// See if the steps bar is owned by the application
	//
	UIComponent stepsBar = wizard.getStepsBarComponent();
	if (stepsBar != null) {
	    RenderingUtilities.renderComponent(stepsBar, context);
	    return;
	}

	// We own it. Therefore it's composed of a TabSet component
	// with possibly two tabs, Steps and Help. If the wizard
	// does not support Help then it's just Steps and therefore
	// there is no TabSet component.
	//
	// See if step help is turned off for the wizard
	// If no step help don't render any tabs, just an empty bar.
	//
	if (wizard.hasStepHelp() && show(component, SHOWSTEPHELP)) {
	    renderTabsBar(context, component, theme, writer);
	} else {
	    renderEmptyBar(component, theme, writer);
	}
    }	
    
    /**
     * Render the content of the steps pane layout container.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderStepsPane(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	if (!show(component, SHOWSTEPSPANE)) {
	    return;
	}

	Wizard wizard = (Wizard)component;

	// See if the steps pane is owned by the application
	//
	UIComponent stepsPane = wizard.getStepsPaneComponent();
	if (stepsPane != null) {
	    RenderingUtilities.renderComponent(stepsPane, context);
	    return;
	}

	// Step tab always active if there is no help
	// Always returns true if no help is available
	//
	if (wizard.isStepsTabActive()) {
	    renderStepListBegin(context, component, theme, writer);
	    // If no step help, include a steps pane title before rendering the
	    // steps list. 
 	    if (!wizard.hasStepHelp()) {
	        renderStepsPaneTitle(context, component, theme, writer);
	    }
	    renderStepList(context, component, theme, writer);
	    renderStepListEnd(context, component, theme, writer);
	} else {
	    renderStepHelp(context, component, theme, writer);
	}
    }

    /**
     * Render the beginning of the layout container for the Wizard
     * Steps list.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderStepListBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;

	String paneId =
		component.getClientId(context).concat(STEPS_PANE_SUFFIX);

	writer.startElement(DIV, component); // Steps Pane DIV

	writer.writeAttribute(ID, paneId, null);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP), null);
    }

    /**
     * Render the step list and help tabs in a bar.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTabsBar(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent tabs = wizard.getTabsComponent();
	if (tabs == null) {
	    return;
	}
	
	writer.startElement(DIV, component); // Tabs DIV
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_TAB), null);

	// Looks like tabs is outputting extra div.
	//
	RenderingUtilities.renderComponent(tabs, context);

	writer.endElement(DIV); // Tabs DIV
    }

    /**
     * Render an empty bar for the wizard with no help.
     *
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     */	
    protected void renderEmptyBar(UIComponent component, Theme theme,
	    ResponseWriter writer) throws IOException {

	writer.startElement(DIV, component); 
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_BAR), null);
	writer.endElement(DIV); 
    }

    /**
     * Render the steps pane title.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderStepsPaneTitle(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent stepsPaneTitle = wizard.getStepsPaneTitleComponent();
	if (stepsPaneTitle == null) {
	    return;
	}

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_TITLE), null);

	RenderingUtilities.renderComponent(stepsPaneTitle, context);

	writer.endElement(DIV);
    }

    /**
     * Render the step list for the Wizard.
     * If a UIComponent exists that represents the step list
     * render it. Other wise obtain a <code>Wizard.StepList</code>
     * iterator from the wizard. The iterator returns instances of
     * <code>WizardStepListItem</code>. It can be determined from
     * this object whether the step is the current step, a substep
     * or a branch. A component to render for each step can be
     * obtained from the wizard component.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderStepList(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;

	// If there is a component responsible for the complete
	// list render it.
	//
	UIComponent stepList = wizard.getStepListComponent();
	if (stepList != null) {
	    RenderingUtilities.renderComponent(stepList, context);
	    return;
	}

	writer.startElement(TABLE, component);
	writer.writeAttribute(BORDER, Integer.toString(0), null);
	writer.writeAttribute(CELLSPACING, Integer.toString(0), null);
	writer.writeAttribute(CELLPADDING, Integer.toString(0), null);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_TABLE), null);

	Iterator stepListIterator = wizard.getStepListIterator();
	while (stepListIterator.hasNext()) {

	    WizardStepListItem stepItem=
		(WizardStepListItem)stepListIterator.next();

	    if (stepItem.isSubstep()) {
		renderSubstep(context, component, theme, writer, stepItem);
	    } else 
	    if (stepItem.isCurrentStep()) {
		renderCurrentStep(context, component, theme, writer, stepItem);
	    } else
	    if (stepItem.isBranch()) {
		renderBranchStep(context, component, theme, writer, stepItem);
	    } else {
		renderStep(context, component, theme, writer, stepItem);
	    }
	}
	writer.endElement(TABLE);
    }

    /**
     * Render the indicator that identifies a step as being the current step.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderCurrentStepIndicator(FacesContext context,
		UIComponent component, Theme theme, ResponseWriter writer)
		throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent stepIndicator = wizard.getStepIndicatorComponent();
	if (stepIndicator != null) {
	    RenderingUtilities.renderComponent(stepIndicator, context);
	}

    }

    /**
     * Render a substep. Typically this has a step number of the form
     * "n.m" where "n" is the previous step number and "m" is the substep.
     * It is indented from the normal step number.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> information about this
     * substep.
     */
    protected void renderSubstep(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step) throws IOException {

	boolean isCurrentStep = step.isCurrentStep();

	String stepTextStyle = isCurrentStep ?
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_CURRENT_TEXT) :
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_LINK);

	writer.startElement(TR, component);

	// The first cell may have the current step indicator.
	// It is different from a normal step in that the 
	// step number is not part of the same cell.
	//
	if (isCurrentStep) {
	    writer.startElement(TD, component);
	    writer.writeAttribute(VALIGN, TOP, null);
	    writer.writeAttribute(ALIGN, RIGHT, null);
	    writer.writeAttribute(NOWRAP, NOWRAP, null);

	    writer.startElement(DIV, component);
	    writer.writeAttribute(CLASS, 
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_ARROW_DIV), null);

	    renderCurrentStepIndicator(context, component, theme, writer);

	    writer.endElement(DIV);
	    writer.endElement(TD);
	} else {
	    // An empty cell if its not the current step.
	    //
	    writer.startElement(TD, component);
	    writer.writeAttribute(VALIGN, TOP, null);
	    writer.endElement(TD);
	}

	// The cell for the step nummber. It has a different
	// style if it is the current step.
	//
	writer.startElement(TD, component);
	writer.writeAttribute(VALIGN, TOP, null);

	writer.startElement(TABLE, component);
	writer.writeAttribute(BORDER, Integer.toString(0), null);
	writer.writeAttribute(CELLSPACING, Integer.toString(0), null);
	writer.writeAttribute(CELLPADDING, Integer.toString(0), null);

	writer.startElement(TR, component);
	writer.startElement(TD, component);
	writer.writeAttribute(VALIGN, TOP, null);

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_STEP_TEXT_DIV), null);

	renderStepNumber(context, component, theme, writer, step,
	    stepTextStyle);

	writer.endElement(DIV);
	writer.endElement(TD);

	// Cell for the step summary
	//
	writer.startElement(TD, component);
	writer.writeAttribute(VALIGN, TOP, null);

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_STEP_TEXT_DIV), null);

	renderStepSummary(context, component, theme, writer, step,
		stepTextStyle);

	writer.endElement(DIV);

	writer.endElement(TD);
	writer.endElement(TR);
	writer.endElement(TABLE);

	writer.endElement(TD);
	writer.endElement(TR);
    }

    /**
     * Render a Branch step with place holder text.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> information about this
     * branch step.
     */
    protected void renderBranchStep(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step) throws IOException {

	// Start a branch with an emtpy cell
	//
	writer.startElement(TR, component);
	writer.startElement(TD, component);

	writer.writeAttribute(VALIGN, TOP, null);
	writer.writeAttribute(NOWRAP, NOWRAP, null);

	writer.write(NBSP);

	writer.endElement(TD);

	writer.startElement(TD, component);
	writer.writeAttribute(VALIGN, TOP, null);

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_STEP_TEXT_DIV), null);

	// This will be placeholder text for a branch step
	//
	renderStepPlaceholderText(context, component, theme, writer, step);

	writer.endElement(DIV);
	writer.endElement(TD);
	writer.endElement(TR);
    }

    /**
     * Render the current step in the step list. This step typically appears
     * with an icon to the left of the step number, indicating the current step.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> information about this 
     * branch step.
     */
    protected void renderCurrentStep(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step) throws IOException {

	// Begin step row
	//
	writer.startElement(TR, component);

	// Current Step Indicator Cell
	// Should this markup be part of renderCurrentStepIndicator ?
	// But WIZARD_STEP_ARROW_DIV needs to include the number
	//
	writer.startElement(TD, component);
	writer.writeAttribute(VALIGN, TOP, null);
	writer.writeAttribute(NOWRAP, NOWRAP, null);

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_STEP_ARROW_DIV), null);

	renderCurrentStepIndicator(context, component, theme, writer);

	// How about formatter methods, stepNumberFormat(stepNumber)
	// How about WizardStepPane.renderStep(stepNumber, WizardStep.text)
	// How about WizardStepPane.renderBranch(WizardStep.branch)
	// COMPONENT TEXT : stepNumber "1."
	//
	renderStepNumber(context, component, theme, writer, step,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_CURRENT_TEXT));

	writer.endElement(DIV);
	writer.endElement(TD);

	// Should this markup be part of renderStepSummary ?
	//
	writer.startElement(TD, component);
	writer.writeAttribute(VALIGN, TOP, null);

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_STEP_TEXT_DIV), null);

	// APPLICATION TEXT : "Type number of users");
	//
	renderStepSummary(context, component, theme, writer, step,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_CURRENT_TEXT));

	writer.endElement(DIV);
	writer.endElement(TD);
	writer.endElement(TR);
    }

    /**
     * Render a step's sequence number in the step list.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> the information about this
     * step.
     * @param styleClass The styleClass for this component
     */
    protected void renderStepNumber(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step, String styleClass) throws IOException {

	UIComponent number = ((Wizard)component).getStepNumberComponent(
		step.getStep(), step.getStepNumberString());
	if (number == null) {
	    // Should log or throw something here.
	    return;
	}
	number.getAttributes().put(STYLE_CLASS, styleClass);
	RenderingUtilities.renderComponent(number, context);
    }

    /**
     * Render a step's summary in the step list.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> information about this
     * step.
     */
    protected void renderStepSummary(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step, String styleClass)
	throws IOException {

	UIComponent text = ((Wizard)component).getStepSummaryComponent(
		step.getStep());
	if (text == null) {
	    return;
	}
	text.getAttributes().put(STYLE_CLASS, styleClass);
	RenderingUtilities.renderComponent(text, context);
    }

    /**
     * Render a baranch step's placeholder text in the step list.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> information about this
     * step.
     */
    protected void renderStepPlaceholderText(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step)
	throws IOException {

	UIComponent text = ((Wizard)component).getStepPlaceholderTextComponent(
		step.getStep());
	if (text == null) {
	    return;
	}
	text.getAttributes().put(STYLE_CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_TEXT));
	RenderingUtilities.renderComponent(text, context);
    }

    /**
     * Render a step in the step list.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> information about this
     * step.
     */
    protected void renderStep(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step)
	throws IOException {

	writer.startElement(TR, component);
	writer.startElement(TD, component);

	writer.writeAttribute(VALIGN, TOP, null);

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_STEP_NUMBER_DIV), null);

	renderStepNumber(context, component, theme, writer, step,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_LINK));

	writer.endElement(DIV);
	writer.endElement(TD);

	writer.startElement(TD, component);
	writer.writeAttribute(VALIGN, TOP, null);

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_STEP_TEXT_DIV), null);

	// This will be placeholder text for a branch step
	//
	renderStepSummary(context, component, theme, writer, step,
		theme.getStyleClass(ThemeStyles.WIZARD_STEP_LINK));

	writer.endElement(DIV);
	writer.endElement(TD);
	writer.endElement(TR);
    }

    /**
     * Render the help for the current Step in the Wizard.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderStepHelp(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	UIComponent stepHelp = ((Wizard)component).getStepHelpComponent(); 
	if (stepHelp != null) {
	    RenderingUtilities.renderComponent(stepHelp, context);
	}

	WizardStep step = ((Wizard)component).getCurrentStep();

	renderStepHelpBegin(context, component, theme, writer, step);
	renderStepHelpText(context, component, theme, writer, step);
	renderStepHelpEnd(context, component, theme, writer, step);
    }

    /**
     * Render the beginning of the layout for the step help.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> the current step.
     */
    protected void renderStepHelpBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step) throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_HELP_DIV), null);

	// Probably shouldn't include the para
	//
	writer.startElement(PARA, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_HELP_TEXT), null);
    }

    /**
     * Render the step help text.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> the current step.
     */
    protected void renderStepHelpText(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	// Assumes current step.
	//
	UIComponent stepHelp = ((Wizard)component).getStepHelpTextComponent();
	if (stepHelp != null) {
	    RenderingUtilities.renderComponent(stepHelp, context);
	}
    }

    /**
     * Render the end of the layout for the step help.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> the current step.
     */
    protected void renderStepHelpEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	writer.endElement(PARA);
	writer.endElement(DIV);
    }

    /**
     * Render the end of the layout container for the Wizard
     * Steps list.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderStepListEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.endElement(DIV); // Steps Pane DIV
    }

    /**
     * Render the step task. This includes a task header of title and
     * step detail or instruction, and the components that make up the
     * current wizard step.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTask(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	UIComponent task = ((Wizard)component).getTaskComponent();
	if (task != null) {
	    RenderingUtilities.renderComponent(task, context);
	    return;
	}
	WizardStep step = ((Wizard)component).getCurrentStep();
	if (step == null) {
	    // Should log
	    return;
	}

	renderTaskBegin(context, component, theme, writer);
	renderTaskHeader(context, component, theme, writer, step);
	renderStepTask(context, component, theme, writer, step);
	renderTaskEnd(context, component, theme, writer);
    }

    /**
     * Render the beginning of the layout container step task area.
     * This includes an anchor from the beginning of the steps pane.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTaskBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.startElement(DIV, component); // WizBdy DIV
	writer.writeAttribute(ID, 
	    component.getClientId(context).concat(USCORE).concat("WizBdy"),
	    null);
	writer.writeAttribute(CLASS, 
	    theme.getStyleClass(ThemeStyles.WIZARD_BODY), null);

	// The skip link anchor
	//
	renderSkipAnchor(context, component, theme, writer,
		component.getClientId(context));
    }

    /**
     * Render the end of the layout container for the Wizard step task area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderTaskEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	    writer.endElement(DIV); // WizBdy DIV
    }

    /**
     * Render the step title and step detail for the current step.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderTaskHeader(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	UIComponent taskHeader = ((Wizard)component).getTaskHeaderComponent();
	if (taskHeader != null) {
	    RenderingUtilities.renderComponent(taskHeader, context);
	    return;
	}
	renderStepTitle(context, component, theme, writer, step);
	renderStepDetail(context, component, theme, writer, step);
    }

    /**
     * Render the step title label.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTitleLabelText(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	UIComponent titleLabel =
	    ((Wizard)component).getStepTitleLabelTextComponent();
	if (titleLabel != null) {
	    titleLabel.getAttributes().put(STYLE_CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_SUB_TITLE_TEXT));
	    RenderingUtilities.renderComponent(titleLabel, context);
	}
    }

    /**
     * Render the step title.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTitle(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	UIComponent stepTitle = ((Wizard)component).getStepTitleComponent();
	if (stepTitle != null) {
	    RenderingUtilities.renderComponent(stepTitle, context);
	    return;
	}

	renderStepTitleBegin(context, component, theme, writer, step);
	renderStepTitleLabelText(context, component, theme, writer, step);
	renderStepTitleText(context, component, theme, writer, step);
	renderStepTitleEnd(context, component, theme, writer, step);
    }

    /**
     * Render the beginning of the layout for the step title.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTitleBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_SUB_TITLE_DIV), null);
    }

    /**
     * Render the step title text.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTitleText(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	UIComponent stepTitle = ((Wizard)component).getStepTitleTextComponent();
	if (stepTitle != null) {
	    stepTitle.getAttributes().put(STYLE_CLASS, 
		theme.getStyleClass(ThemeStyles.WIZARD_SUB_TITLE_TEXT));
	    RenderingUtilities.renderComponent(stepTitle, context);
	}
    }

    /**
     * Render the end of the layout for the step title.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTitleEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	writer.endElement(DIV);
    }

    /**
     * Render the step detail for the current step.
     * Each step may have optional detail text that appears
     * below the current step title.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepDetail(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	UIComponent stepDetail = ((Wizard)component).getStepDetailComponent();
	if (stepDetail != null) {
	    RenderingUtilities.renderComponent(stepDetail, context);
	    return;
	}
	renderStepDetailBegin(context, component, theme, writer, step);
	renderStepDetailText(context, component, theme, writer, step);
	renderStepDetailEnd(context, component, theme, writer, step);
    }

    /**
     * Render the beginning of the layout container for the step detail.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepDetailBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
	    theme.getStyleClass(ThemeStyles.WIZARD_CONTENT_HELP_TEXT), null);
    }

    /**
     * Render the step detail text.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepDetailText(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	UIComponent stepDetail =
		((Wizard)component).getStepDetailTextComponent();
	if (stepDetail != null) {
	    RenderingUtilities.renderComponent(stepDetail, context);
	}
    }

    /**
     * Render the end of the layout container for the step detail.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepDetailEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	writer.endElement(DIV);
    }

    /**
     * Render the beginning of the layout container for the step components.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTaskBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS, 
		theme.getStyleClass(ThemeStyles.WIZARD_TASK), null);
    }

    /**
     * Render the step components.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTask(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	// This probably over kill ?
	// Wrapper for a WizardStep ?
	//
	UIComponent task = ((Wizard)component).getTaskStepComponent();
	if (task != null) {
	    RenderingUtilities.renderComponent(task, context);
	    return;
	}

	renderStepTaskBegin(context, component, theme, writer, step);
	renderStepTaskComponents(context, component, theme, writer, step);
	renderStepTaskEnd(context, component, theme, writer, step);
    }

    /**
     * Render the end of the layout container for the step components
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTaskEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step)
	    throws IOException {

	writer.endElement(DIV);
    }

    /**
     * Render the components and layout for the navigation control area.
     * This area contains the Previous, Next, Finish, Cancel and Close
     * buttons.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderControlBar(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	if (!show(component, SHOWCONTROLS)) {
	    return;
	}

	UIComponent controlBar = ((Wizard)component).getControlBarComponent();
	if (controlBar != null) {
	    RenderingUtilities.renderComponent(controlBar, context);
	    return;
	}
	renderControlBarBegin(context, component, theme, writer);
	renderControlBarSpacer(context, component, theme, writer);

	// layout container for the left and right sections in the control area.
	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_BUTTON_BOTTOM), null);
	
	renderLeftControlBar(context, component, theme, writer);
	renderRightControlBar(context, component, theme, writer);
	
	writer.endElement(DIV);

	renderControlBarEnd(context, component, theme, writer);
    }

    /**
     * Render the beginning of the layout container for the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderControlBarBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_BOTTOM), null);
    }

    /**
     * Render the end of the layout container for the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderControlBarEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.endElement(DIV);
    }

    /**
     * Render spacer for the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderControlBarSpacer(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_BOTTOM_SPACER), null);
	writer.endElement(DIV);
    }
    
    /**
     * Render the beginning of the layout container for the left side of
     * the control area. The area containing the sequencing controls is
     * split into two sections: Left and Right.</br>
     * The left side of the control area contains the Previous,
     * Next or Finish controls. The right area contains the
     * Cancel or Close control.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderLeftControlBar(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;

	UIComponent leftControlBar = wizard.getLeftControlBarComponent();
	if (leftControlBar != null) {
	    RenderingUtilities.renderComponent(leftControlBar, context);
	    return;
	}

	renderLeftControlBarBegin(context, component, theme, writer);

	if (wizard.hasPrevious()) {
	    renderPreviousControl(context, component, theme, writer);
	}

	if (wizard.hasNext()) {
	    renderNextControl(context, component, theme, writer);
	} else
	if (wizard.hasFinish()) {
	    renderFinishControl(context, component, theme, writer);
	}

	renderLeftControlBarEnd(context, component, theme, writer);
    }

    /**
     * Render the beginning of the layout container for the
     * left side of the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderLeftControlBarBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_LEFT), null);
    }

    /**
     * Render the control that directs the Wizard to the previous step.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderPreviousControl(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent control = wizard.getPreviousComponent();
	if (control == null) {
	    // Log this
	    return;
	}
	renderControl(context, theme, control);
    }

    /**
     * Render the control that directs the Wizard to the next step.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderNextControl(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent nextControl = wizard.getNextComponent();
	if (nextControl == null) {
	    // Log this
	    return;
	}
	renderControl(context, theme, nextControl);
    }

    /**
     * Render the control that directs the Wizard to the perform task.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderFinishControl(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent control = wizard.getFinishComponent();
	if (control == null) {
	    // Log this
	    return;
	}
	renderControl(context, theme, control);
    }

    /**
     * Render the end of the layout container for the 
     * left side of the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderLeftControlBarEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.endElement(DIV);
    }

    /**
     * Render the beginning of the layout container for the
     * right side of the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderRightControlBar(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;

	UIComponent rightControlBar = wizard.getRightControlBarComponent();
	if (rightControlBar != null) {
	    RenderingUtilities.renderComponent(rightControlBar, context);
	    return;
	}

	renderRightControlBarBegin(context, component, theme, writer);

	if (wizard.hasCancel()) {
	    renderCancelControl(context, component, theme, writer);
	} else
	if (wizard.hasClose()) {
	    renderCloseControl(context, component, theme, writer);
	}

	renderRightControlBarEnd(context, component, theme, writer);
    }

    /**
     * Render the beginning of the layout container for the
     * right side of the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderRightControlBarBegin(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.WIZARD_RIGHT), null);
    }

    /**
     * Render the control that cancels the Wizard task.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderCancelControl(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent control = wizard.getCancelComponent();
	if (control == null) {
	    // Log this
	    return;
	}
	renderControl(context, theme, control);
    }

    /**
     * Render the control that directs the Wizard to the end the
     * Wizard task.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderCloseControl(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	Wizard wizard = (Wizard)component;
	UIComponent control = wizard.getCloseComponent();
	if (control == null) {
	    // Log this
	    return;
	}
	renderControl(context, theme, control);
    }

    /**
     * Render the end of the layout container for the
     * right side of the control area.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     */
    protected void renderRightControlBarEnd(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer)
	    throws IOException {

	writer.endElement(DIV);
    }


    /**
     * Create an invisible "skip" link to an anchor as an accessibility feature
     * to navigate to the main task area in a Wizard from the steps pane.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param alt the text that will appear to screen readers.
     */
    private void renderSkipLink(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    String link, String alt)
	    throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.SKIP_WHITE), null);
	writer.startElement(ANCHOR, component);
	writer.writeAttribute(HREF, "#" + link, null);

	// Can this be done with a style selector ?
	// This is an invisible rule, used mainly for the alt
	//
	renderRule(context, component, theme, writer, 1, 1, alt);

	writer.endElement(ANCHOR);
	writer.endElement(DIV);
    }

    /**
     * The anchor target of the "skip" link.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param anchor the text that will appear to screen readers, specified as
     * <code>link</code> in a previous <code>renderSkipLink</code> call.
     */
    private void renderSkipAnchor(FacesContext context,
		UIComponent component, Theme theme,
		ResponseWriter writer, String anchor)
		throws IOException {

	writer.startElement(DIV, component);
	writer.writeAttribute(CLASS,
		theme.getStyleClass(ThemeStyles.SKIP_WHITE), null);
	writer.startElement(ANCHOR, component);
	writer.writeAttribute(NAME, anchor, null);
	writer.writeAttribute(ID, anchor, null);
	writer.endElement(ANCHOR);
	writer.endElement(DIV);
    }

    /**
     * Render a rule as separator line.
     * 
     * NOTE: Need to determine if CSS can be used for this purpose.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param width the width of the rule.
     * @param height the height of the rule.
     * @param alt the text that will appear if the dot image cannot be
     * displayed.
     */
    private void renderRule(FacesContext context, UIComponent component,
	    Theme theme, ResponseWriter writer,
	    int width, int height, String alt)
	    throws IOException {

	Icon icon = ThemeUtilities.getIcon(theme, ThemeImages.DOT);

	// Originally alt was written out if it was "", does this
	// still make sense ?
	//
	if (alt != null) {
	    icon.setAlt(alt);
	}
	icon.setHeight(height);
	icon.setWidth(width);
	RenderingUtilities.renderComponent(icon, context);
    }

    /**
     * Return true if the feature has been configured to be displayed,
     * false otherwise.
     * 
     * @param component the <code>UIComponent</code> being rendered.
     * @param feature the attribute that controls a feature.
     */
    private boolean show(UIComponent component, String feature) {

	// Don't show controls if turned off
	// Default is true
	//
	/* Not supported yet
	Boolean showIt = (Boolean)component.getAttributes().get(feature);
	return showIt == null || showIt.booleanValue();
	*/
	return true;
    }

    /**
     * Render the current WizardStep component.
     *
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStep</code> current step.
     */
    protected void renderStepTaskComponents(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStep step) throws IOException {

	// This could be a component tree
	// Children of the step, an iterator, a jsp page, ...
	//
	/* 
	Object task = step.getStepTaskComponents();
	if (task == null) {
	    task = ((Wizard)component).getStepTaskComponents();
	}
	if (task == null) {
	    // fake it
	    return;
	}
	// URL to a jsp page
	//
	if (task instanceof String) {
	    RenderingUtilities.includeJsp(context, writer, task);
	} else 
	// component tree
	//
	if (task instanceof UIComponent) {
	    RenderingUtilities.renderComponent(task, context);
	} else
	// Array, List, or Map
	//
	if (task instanceof List) {
	} else
	if (task instanceof Array) {
	} else
	if (task instanceof Map) {
	} else
	if (step.getChildren() != null) {
	}

	String jspPath = step.getStepURL();

	// Checks for null
	//
	RenderingUtilities.includeJsp(context, writer, jspPath);
	*/

	RenderingUtilities.renderComponent(step, context);
    }

    // This isn't perfect
    //
    private String mergeStyle(String styles, String newStyle) {
	
	if (newStyle == null) {
	    return styles;
	}
	if (styles == null) {
	    return newStyle;
	}
	StringBuffer sb = new StringBuffer(styles);
	String[] splitStyles = styles.split(SPACE);
	for (int i = 0; i < splitStyles.length; ++i) {
	    if (splitStyles[i].equals(newStyle)) {
		return sb.toString();
	    }
	}
	sb.append(SPACE).append(newStyle);
	return sb.toString();
    }

    private void renderControl(FacesContext context, Theme theme,
	    UIComponent control) throws IOException {

	RenderingUtilities.renderComponent(control, context);
    }

    // Nothing so far
    //
    public void decode(FacesContext context, UIComponent component) {
        // Enforce NPE requirements in the Javadocs
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

    // Helpful constants.
    // Elements
    private static final String ANCHOR = "a"; //NOI18N
    private static final String DIV = "div"; //NOI18N
    private static final String IMG = "img"; //NOI18N
    private static final String INPUT = "input"; //NOI18N
    private static final String PARA = "p"; //NOI18N
    private static final String SCRIPT = "script"; //NOI18N
    private static final String SPAN = "span"; //NOI18N
    private static final String TABLE = "table"; //NOI18N
    private static final String TD = "td"; //NOI18N
    private static final String TR = "tr"; //NOI18N

    // Attributes
    private static final String ALIGN = "align"; //NOI18N
    private static final String ALT = "alt"; //NOI18N
    private static final String BORDER = "border"; //NOI18N
    private static final String CELLPADDING = "cellpadding"; //NOI18N
    private static final String CELLSPACING = "cellspacing"; //NOI18N
    private static final String CLASS = "class"; //NOI18N
    private static final String STYLE_CLASS = "styleClass"; //NOI18N
    private static final String DISABLED = "disabled"; //NOI18N
    private static final String HEIGHT = "height"; //NOI18N
    private static final String HREF = "href"; //NOI18N
    private static final String HSPACE = "hspace"; //NOI18N
    private static final String ID = "id"; //NOI18N
    private static final String NAME = "name"; //NOI18N
    private static final String NOWRAP = "nowrap"; //NOI18N
    private static final String ONBLUR = "onblur"; //NOI18N
    private static final String ONCLICK = "onclick"; //NOI18N
    private static final String ONFOCUS = "onfocus"; //NOI18N
    private static final String ONMOUSEOVER = "onmouseover"; //NOI18N
    private static final String ONMOUSEOUT = "onmouseout"; //NOI18N
    private static final String SRC = "src"; //NOI18N
    private static final String STYLE = "style"; //NOI18N
    private static final String TITLE = "title"; //NOI18N
    private static final String TYPE = "type"; //NOI18N
    private static final String VALIGN = "valign"; //NOI18N
    private static final String VALUE = "valign"; //NOI18N
    private static final String WIDTH = "width"; //NOI18N

    // Values
    private static final String _1_PERCENT = "1%"; //NOI18N
    private static final String _90_PERCENT = "90%"; //NOI18N
    private static final String _99_PERCENT = "99%"; //NOI18N
    private static final String _100_PERCENT = "100%"; //NOI18N
    private static final String BOTTOM = "bottom"; //NOI18N
    private static final String LEFT = "left"; //NOI18N
    private static final String RIGHT = "right"; //NOI18N
    private static final String SUBMIT = "submit"; //NOI18N
    private static final String TOP = "top"; //NOI18N
    private static final String TEXT_JAVASCRIPT = "text/javascript"; //NOI18N
    private static final String NBSP = "&nbsp;"; //NOI18N


    // Constants
    private static final String STEPS_PANE_SUFFIX = "_stepspane"; //NOI18N
    private static final String SPACE = " "; //NOI18N
    private static final String EMPTY_STRING = ""; //NOI18N
    private static final String USCORE = "_"; //NOI18N
    private static final String WIZARD_JSOBJECT_CLASS = "Wizard"; //NOI18N
    private static final String SQUOTE = "'"; //NOI18N
    private static final String CLOSEPOPUPJS = "{0}.closePopup();"; //NOI18N


    // Wizard attributes
    private static final String TOOLTIP = "toolTip"; //NOI18N
    private static final String SHOWCONTROLS = "showControls"; //NOI18N
    private static final String SHOWSTEPSPANE = "showStepsPane"; //NOI18N
    private static final String SHOWSTEPHELP = "showStepHelp"; //NOI18N
    private static final String SHOWTITLE = "showTitle"; //NOI18N
    private static final String ONPOPUPDISMISS = "onPopupDismiss"; //NOI18N

    private static final String WIZARD_SKIP_LINK_ALT = "Wizard.skipLinkAlt"; //NOI18N

    private static final String MSG_COMPONENT_NOT_WIZARD =
	"WizardLayoutRenderer only renders Wizard components."; //NOI18N

    private String getMessage(String key) {
	return key;
    }

    // Deprecations, which we may decide can just be removed.
    // However, if not, these deprecated methods should probably provide the
    // redundant spans to maintain the original behavior.
    //
    // Deprecate these method since we were adding redundant spans
    // around static text. The problem is that using these methods
    // will yield NO wizard style for the span.
    //
    /**
     * Render a step's sequence number in the step list.
     *
     * @deprecated See {@link renderStepNumber(FacesContext,UIComponent,Theme,ResponseWriter,WizardStepListItem, String styleClass)}
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> the information about this
     * step.
     */
    protected void renderStepNumber(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step) throws IOException {

	UIComponent number = ((Wizard)component).getStepNumberComponent(
		step.getStep(), step.getStepNumberString());
	if (number == null) {
	    // Should log or throw something here.
	    return;
	}
	RenderingUtilities.renderComponent(number, context);
    }

    /**
     * Render a step's summary in the step list.
     *
     * @deprecated See {@link renderStepSummary(FacesContext,UIComponent,Theme,ResponseWriter,WizardStepListItem, String styleClass)}
     * @param context <code>FacesContext</code> for the current request.
     * @param component <code>UIComponent</code> a Wizard or Wizard subclass.
     * @param theme <code>Theme</code> to use for style, images, and text.
     * @param writer <code>ResponseWriter</code> write the response using this
     * writer.
     * @param step <code>WizardStepListItem</code> information about this
     * step.
     */
    protected void renderStepSummary(FacesContext context,
	    UIComponent component, Theme theme, ResponseWriter writer,
	    WizardStepListItem step)
	throws IOException {

	UIComponent text = ((Wizard)component).getStepSummaryComponent(
		step.getStep());
	if (text == null) {
	    return;
	}
	RenderingUtilities.renderComponent(text, context);
    }

}
