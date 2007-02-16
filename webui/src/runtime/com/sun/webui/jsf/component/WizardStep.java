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
 * $Id: WizardStep.java,v 1.1 2007-02-16 01:25:19 bob_yennaco Exp $
 */

package com.sun.webui.jsf.component;

import javax.el.ValueExpression;

import javax.faces.component.UIComponentBase;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;

import com.sun.webui.jsf.event.WizardEventListener;

/**
 * The WizardStep component represents a single step in a Wizard component
 * step sequence. The components to obtain user data also known collectively
 * as the step task, are specified as children of the WizardStep component.
 */
@Component(type="com.sun.webui.jsf.WizardStep",
    family="com.sun.webui.jsf.WizardStep", displayName="Wizard Step",
    tagName="wizardStep",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_wizard_step",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_wizard_step_props")
public class WizardStep extends UIComponentBase implements NamingContainer {

    /**
     * Construct a new <code>WizardStep</code>.
     */
    public WizardStep() {
        super();
    }

    /**
     * Return the family for this component, <code>
     * com.sun.webui.jsf.WizardStep</code>.
     */
    public String getFamily() {
        return "com.sun.webui.jsf.WizardStep";
    }

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    // Hide rendered
    @Property(isHidden=true, isAttribute=false)
    public boolean isRendered() {
        return super.isRendered();
    }

    // detail
    /**
     * The detail attribute supplies the text that is to be
     * displayed in the Step Instructions area, before the input components
     * of the Step Content pane. Typically you would provide one or two 
     * sentences that describe what the step does, or tell the user how 
     * to interact with the step.
     */
    @Property(name="detail")
    private String detail = null;

    /**
     * The detail attribute supplies the text that is to be
     * displayed in the Step Instructions area, before the input components
     * of the Step Content pane. Typically you would provide one or two 
     * sentences that describe what the step does, or tell the user how 
     * to interact with the step.
     */
    public String getDetail() {
        if (this.detail != null) {
            return this.detail;
        }
        ValueExpression _vb = getValueExpression("detail");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * The detail attribute supplies the text that is to be
     * displayed in the Step Instructions area, before the input components
     * of the Step Content pane. Typically you would provide one or two 
     * sentences that describe what the step does, or tell the user how to 
     * interact with the step.
     * @see #getDetail()
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    // eventListener
    /**
     * The eventListener attribute is used to specify an
     * object to handle an event that is triggered when a user activates a 
     * component in the step.
     * The eventListener attribute value must be a 
     * JavaServer Faces EL expression that resolves to an instance
     * of <code>com.sun.webui.jsf.event.WizardEventListener</code>.
     * <p>
     * The return value of the wizard component's call to the
     * event listener's <code>handleEvent()</code> method controls the 
     * processing of the current step, and determines whether the next step 
     * or a previous step, etc. can be navigated to.
     * </p>
     * <p>See the <a href="wizard.html#EventListeners">Event
     * Listeners</a> section in the <code>webuijsf:wizard</code> tag
     * documentation for more information.
     * </p>
     */
    @Property(name="eventListener", displayName="Wizard Event Listener")
    private WizardEventListener eventListener = null;

    /**
     * The eventListener attribute is used to specify an
     * object to handle an event that is triggered when a user activates a 
     * component in the step.
     * The eventListener attribute value must be a 
     * JavaServer Faces EL expression that resolves to an instance
     * of <code>com.sun.webui.jsf.event.WizardEventListener</code>.
     * <p>
     * The return value of the wizard component's call to the
     * event listener's <code>handleEvent()</code> method controls the 
     * processing of the current step, and determines whether the next step 
     * or a previous step, etc. can be navigated to.
     * </p>
     * <p>See the <a href="wizard.html#EventListeners">Event
     * Listeners</a> section in the <code>webuijsf:wizard</code> tag
     * documentation for more information.
     * </p>
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
     * The eventListener attribute is used to specify an
     * object to handle an event that is triggered when a user activates a 
     * component in the step.
     * The eventListener attribute value must be a 
     * JavaServer Faces EL expression that resolves to an instance
     * of <code>com.sun.webui.jsf.event.WizardEventListener</code>.
     * <p>
     * The return value of the wizard component's call to the
     * event listener's <code>handleEvent()</code> method controls the 
     * processing of the current step, and determines whether the next step 
     * or a previous step, etc. can be navigated to.
     * </p>
     * <p>
     * See the <a href="wizard.html#EventListeners">Event
     * Listeners</a> section in the <code>webuijsf:wizard</code>
     * tag documentation
     * for more information.
     * </p>
     * @see #getEventListener()
     */
    public void setEventListener(WizardEventListener eventListener) {
        this.eventListener = eventListener;
    }

    // finish
    /**
     * Set the finish attribute to true when the wizard step
     * represents the Finish step. For wizards with three or more steps, the
     * Finish step should be the Review Selections page. The finish attribute
     * causes the Finish button to be displayed. The Finish step performs the
     * wizard task when the user clicks the Finish button.
     */
    @Property(name="finish")
    private boolean finish = false;
    private boolean finish_set = false;

    /**
     * Set the finish attribute to true when the wizard step
     * represents the Finish step. For wizards with three or more steps, the
     * Finish step should be the Review Selections page. The finish attribute
     * causes the Finish button to be displayed. The Finish step performs the
     * wizard task when the user clicks the Finish button.
     */
    public boolean isFinish() {
        if (this.finish_set) {
            return this.finish;
        }
        ValueExpression _vb = getValueExpression("finish");
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
     * Set the finish attribute to true when the wizard step
     * represents the Finish step. For wizards with three or more steps, the
     * Finish step should be the Review Selections page. The finish attribute
     * causes the Finish button to be displayed. The Finish step performs the
     * wizard task when the user clicks the Finish button.
     * @see #isFinish()
     */
    public void setFinish(boolean finish) {
        this.finish = finish;
        this.finish_set = true;
    }

    // help
    /**
     * Descriptive text that provides detailed help to the user
     * for this step. The amount of text specified is unlimited but is typically
     * only a few short paragraphs. The content can contain HTML markup for
     * formatting. Note that you must use the character entity
     * references <code>&amp;lt;</code> and <code>&amp;gt;</code>
     * to create the &lt; and &gt; characters for HTML elements
     * in the help text.
     */
    @Property(name="help")
    private String help = null;

    /**
     * Descriptive text that provides detailed help to the user
     * for this step. The amount of text specified is unlimited but is typically
     * only a few short paragraphs. The content can contain HTML markup for
     * formatting. Note that you must use the character entity
     * references <code>&amp;lt;</code> and <code>&amp;gt;</code>
     * to create the &lt; and &gt; characters for HTML elements
     * in the help text.
     */
    public String getHelp() {
        if (this.help != null) {
            return this.help;
        }
        ValueExpression _vb = getValueExpression("help");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Descriptive text that provides detailed help to the user
     * for this step. The amount of text specified is unlimited but is typically
     * only a few short paragraphs. The content can contain HTML markup for
     * formatting. Note that you must use the character entity
     * references <code>&amp;lt;</code> and <code>&amp;gt;</code>
     * to create the &lt; and &gt; characters for HTML elements
     * in the help text.
     * @see #getHelp()
     */
    public void setHelp(String help) {
        this.help = help;
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

    // onPrevious
    /**
     * Scripting code executed when the Next button is clicked.
     */
    @Property(name="onPrevious", displayName="Previous Script")
    private String onPrevious = null;

    /**
     * Scripting code executed when the Next button is clicked.
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
     * Scripting code executed when the Next button is clicked.
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

    // results
    /**
     * Set the results attribute to true when the wizard step
     * represents the View Results page. This page should be used after the
     * wizard task is completed, to display information related to the task,
     * including failure information if appropriate. This attribute causes the
     * Close button to be displayed on the View Results page.
     */
    @Property(name="results")
    private boolean results = false;
    private boolean results_set = false;

    /**
     * Set the results attribute to true when the wizard step
     * represents the View Results page. This page should be used after the
     * wizard task is completed, to display information related to the task,
     * including failure information if appropriate. This attribute causes the
     * Close button to be displayed on the View Results page.
     */
    public boolean isResults() {
        if (this.results_set) {
            return this.results;
        }
        ValueExpression _vb = getValueExpression("results");
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
     * Set the results attribute to true when the wizard step
     * represents the View Results page. This page should be used after the
     * wizard task is completed, to display information related to the task,
     * including failure information if appropriate. This attribute causes the
     * Close button to be displayed on the View Results page.
     * @see #isResults()
     */
    public void setResults(boolean results) {
        this.results = results;
        this.results_set = true;
    }

    // summary
    /**
     * A brief description of this step, to be used in the numbered
     * list of steps in the Steps pane.
     */
    @Property(name="summary")
    private String summary = null;

    /**
     * A brief description of this step, to be used in the numbered
     * list of steps in the Steps pane.
     */
    public String getSummary() {
        if (this.summary != null) {
            return this.summary;
        }
        ValueExpression _vb = getValueExpression("summary");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * A brief description of this step, to be used in the numbered
     * list of steps in the Steps pane.
     * @see #getSummary()
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    // title
    /**
     * A descriptive title to be displayed as the Step Title
     * in the Step Content pane. The Step Title consists of the step number
     * followed by the value of the title attribute. The value of the title
     * attribute could be the same as the value of the summary attribute, or
     * could provide a more detailed description.
     */
    @Property(name="title")
    private String title = null;

    /**
     * A descriptive title to be displayed as the Step Title
     * in the Step Content pane. The Step Title consists of the step number
     * followed by the value of the title attribute. The value of the title
     * attribute could be the same as the value of the summary attribute, or
     * could provide a more detailed description.
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
     * A descriptive title to be displayed as the Step Title
     * in the Step Content pane. The Step Title consists of the step number
     * followed by the value of the title attribute. The value of the title
     * attribute could be the same as the value of the summary attribute, or
     * could provide a more detailed description.
     * @see #getTitle()
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.detail = (String) _values[1];
        this.eventListener = (WizardEventListener) _values[2];
        this.finish = ((Boolean) _values[3]).booleanValue();
        this.finish_set = ((Boolean) _values[4]).booleanValue();
        this.help = (String) _values[5];
        this.onCancel = (String) _values[6];
        this.onClose = (String) _values[7];
        this.onFinish = (String) _values[8];
        this.onHelpTab = (String) _values[9];
        this.onNext = (String) _values[10];
        this.onPrevious = (String) _values[11];
        this.onStepLink = (String) _values[12];
        this.onStepsTab = (String) _values[13];
        this.results = ((Boolean) _values[14]).booleanValue();
        this.results_set = ((Boolean) _values[15]).booleanValue();
        this.summary = (String) _values[16];
        this.title = (String) _values[17];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[18];
        _values[0] = super.saveState(_context);
        _values[1] = this.detail;
        _values[2] = this.eventListener;
        _values[3] = this.finish ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.finish_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.help;
        _values[6] = this.onCancel;
        _values[7] = this.onClose;
        _values[8] = this.onFinish;
        _values[9] = this.onHelpTab;
        _values[10] = this.onNext;
        _values[11] = this.onPrevious;
        _values[12] = this.onStepLink;
        _values[13] = this.onStepsTab;
        _values[14] = this.results ? Boolean.TRUE : Boolean.FALSE;
        _values[15] = this.results_set ? Boolean.TRUE : Boolean.FALSE;
        _values[16] = this.summary;
        _values[17] = this.title;
        return _values;
    }
}
