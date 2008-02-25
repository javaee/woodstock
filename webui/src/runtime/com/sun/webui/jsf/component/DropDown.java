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
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */


package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.el.DropDownMethodExpression;
import com.sun.webui.jsf.event.MethodExprActionListener;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.MethodBindingMethodExpressionAdapter;
import com.sun.webui.jsf.util.MethodExpressionMethodBindingAdapter;

import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import java.util.Iterator;
import java.util.Map;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource2;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.el.MethodBinding;

/**
 * The DropDown component is used to display a drop down menu to allow
 * users to select one or more items from a list.
 */
@Component(type="com.sun.webui.jsf.DropDown",
    family="com.sun.webui.jsf.DropDown",
    displayName="Drop Down List",
    tagName="dropDown",
    tagRendererType="com.sun.webui.jsf.widget.DropDown",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_dropdown_list",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_dropdown_list_props")
public class DropDown extends ListSelector implements ActionSource2 {

    public final static String SUBMIT = "_submitter";
    private boolean fireAction = false;

    private static final boolean DEBUG = false;

    private MethodBinding methodBindingActionListener;
    private MethodExpression actionExpression;

    /**
     * Default constructor.
     */
    public DropDown() {
        super();
        setRendererType("com.sun.webui.jsf.widget.DropDown");
    }

    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.DropDown";
    }
    
    
    public String getRendererType() {
        if (isReadOnly()) {    
            // The readonly attribute is not supported by the new "com.sun.webui.jsf.widget.DropDown"
            // Use the old html renderer to render the readonly drop down for backwards compatibility.
            return "com.sun.webui.jsf.DropDown";
        } else if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            // Ensure we have a valid Ajax request.
            return "com.sun.webui.jsf.ajax.DropDown";
        } else {
            return super.getRendererType();
        }
    }

    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    /**
     * Getter for property multiple
     * @return Value of property multiple
     */
    public boolean getMultiple() {

        return false;
    }

    /**
     * Setter for property multiple
     * @param multiple New value of property multiple
     */
    public void setMultiple(boolean multiple) {

        super.setMultiple(false);
    }

    /**
     * <p>Add a new {@link ActionListener} to the set of listeners interested
     * in being notified when {@link ActionEvent}s occur.</p>
     *
     * @param listener The {@link ActionListener} to be added
     *
     * @exception NullPointerException if <code>listener</code>
     * is <code>null</code>
     */
    public void addActionListener(ActionListener listener) {
        // add the specified action listener
        addFacesListener(listener);
    }

    /**
     * <p>Return the set of registered {@link ActionListener}s for this
     * {@link ActionSource2} instance.  If there are no registered listeners,
     * a zero-length array is returned.</p>
     */
    public ActionListener[] getActionListeners() {
        // return all ActionListener instances associated with this component
        ActionListener listeners[] =
                (ActionListener []) getFacesListeners(ActionListener.class);
        return listeners;
    }

    /**
     * <p>Remove an existing {@link ActionListener} (if any) from the set of
     * listeners interested in being notified when {@link ActionEvent}s
     * occur.</p>
     *
     * @param listener The {@link ActionListener} to be removed
     *
     * @exception NullPointerException if <code>listener</code>
     * is <code>null</code>
     */
    public void removeActionListener(ActionListener listener) {
        // remove the specified ActionListener from the list of listeners
        removeFacesListener(listener);
    }

    /**
     * <p>The DropDown needs to override the standard decoding
     * behaviour since it may also be an action source. We
     * decode the component w.r.t. the value first, and
     * validate it if the component is immediate. Then we
     * fire an action event.</p>
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh".</li>
     * </ul>
     *
     * @exception NullPointerException
     */
    public void processDecodes(FacesContext context) {
        if (DEBUG) log("processDecodes()");

        // Skip processing in case of "refresh" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "refresh")
                && !ComponentUtilities.isAjaxExecuteRequest(getFacesContext(), this)) {
            return;
        }

        // Skip processing if our rendered flag is false
        if (!isRendered()) {
            return;
        }

        // Decode the component and any children
        // Do we really want to decode any children?
        // Process all facets and children of this component
        Iterator childComponents = getFacetsAndChildren();
        while (childComponents.hasNext()) {
            UIComponent comp = (UIComponent) childComponents.next();
            comp.processDecodes(context);
        }

        // Get the value of this component
        try {
            decode(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }

        // Testing isSubmitter() alone is deceiving here.
        // The component may have been the submitter but its
        // submittedValue may still be null.
        // This true in the case of an OptionTitle list option
        // It submits but is treated as if there was no submit
        // by not setting the submittedValue. It doesn't explicitly
        // set it to null, in case the caller of the renderer decode
        // method, set it to something special, for example if the
        // DropDown is used as a sub component.
        //
        // However because this component did submit the form
        // we still need to call setLastClientElement
        // so after calling is submitter, still check submittedValue.
        // and return if it is null.
        //
        // Also not that the submittedValue check has been added to
        // validate, as in UIInput's validate method.
        //
        boolean isSubmitter = isSubmitter(context);

        // Should we fire an action?
        //
        // Check submittedValue. Let the code fall through to
        // validate for an immediate action and it will just return.
        //
        fireAction = isSubmitter && isSubmitForm() &&
                getSubmittedValue() != null;

        // If we are supposed to fire an action and navigate to the value
        // of the component, we get the submitted value now and pass
        // it to the DropDownMethodBinding.
        if(fireAction  && isNavigateToValue()) {
            if(DEBUG) log("\tHanding navigateToValue...");
            MethodExpression mb = getActionExpression();
            if(DEBUG) {
                if(mb != null) log("\tMethod binding is " + mb.toString());
                else log("\tMethod binding is null");
            }

            if(mb instanceof DropDownMethodExpression) {
                String outcome = null;
                Object values = getSubmittedValue();
                if(values instanceof String[]) {
                    String[] stringValues = (String[])values;
                    if(stringValues.length > 0) {
                        outcome = stringValues[0];
                        if(DEBUG) log("Outcome is " + outcome);
                    }
                }

                ((DropDownMethodExpression)mb).setValue(outcome);
                if(DEBUG) log("\tNavigate to " + outcome);
            }
        }

        // Next, if the component is immediate, we validate the component
        if (isImmediate()) {
            try {
                validate(context);
            } catch(RuntimeException e) {
                context.renderResponse();
                throw e;
            }
            if (!isValid()) {
                context.renderResponse();
            }
        }
    }

    /**
     * <p>In addition to to the default {@link UIComponent#broadcast}
     * processing, pass the {@link ActionEvent} being broadcast to the method
     * referenced by <code>actionListener</code> (if any), and to the default
     * {@link ActionListener} registered on the {@link javax.faces.application.Application}.</p>
     *
     * @param event {@link FacesEvent} to be broadcast
     *
     * @exception AbortProcessingException Signal the JavaServer Faces
     * implementation that no further processing on the current event should be
     * performed @exception IllegalArgumentException if the implementation class
     * of this {@link FacesEvent} is not supported by this component
     * @exception NullPointerException if <code>event</code> is
     * <code>null</code>
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException {

        // Perform standard superclass processing
        super.broadcast(event);

        if (isSubmitForm() && (event instanceof ActionEvent)) {
            FacesContext context = getFacesContext();

            // Notify the specified action expression method (if any)
            /* FIXME : Temporary hack to prevent to "actionListenerExpression"
               events.

            MethodExpression mb= getActionListenerExpression();
            if (mb != null) {
                mb.invoke(context.getELContext(), new Object[] { event });
            }
            */

            // Invoke the default ActionListener
            ActionListener listener =
              context.getApplication().getActionListener();
            if (listener != null) {
                listener.processAction((ActionEvent) event);
            }
        }
    }


    /**
     * <p>Intercept <code>queueEvent</code> and, for {@link ActionEvent}s, mark
     * the phaseId for the event to be <code>PhaseId.APPLY_REQUEST_VALUES</code>
     * if the <code>immediate</code> flag is true,
     * <code>PhaseId.INVOKE_APPLICATION</code> otherwise.</p>
     */
    public void queueEvent(FacesEvent e) {
        // If this is an action event, we set the phase according to whether
        // the component is immediate or not.
        if(isSubmitForm()) {
            if (e instanceof ActionEvent) {
                if (isImmediate()) {
                    e.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
                } else {
                    e.setPhaseId(PhaseId.INVOKE_APPLICATION);
                }
            }
        }

        super.queueEvent(e);
    }

      private boolean isSubmitter(FacesContext context) {

        if(DEBUG) log("isSubmitter()");
        String compID = getClientId(context).concat(SUBMIT);
        Map requestParameters =
            context.getExternalContext().getRequestParameterMap();

        String submitter = (String)requestParameters.get(compID);
        if(DEBUG) log("\tValue of submitter field " + submitter);
        return (submitter != null) ? submitter.equals("true") : false;
    }

    public void validate(FacesContext context) {

        // From UIInput
        //
        // Submitted value == null means "the component was not submitted
        // at all";  validation should not continue
        //
        Object submittedValue = getSubmittedValue();
        if (submittedValue == null) {
            return;
        }

        super.validate(context);

        if(isValid() && fireAction) {
            if(DEBUG) log("\tQueue the component event");
            // queue an event
            queueEvent(new ActionEvent(this));
        }
    }

    // ----------------------------------------------------- StateHolder Methods

    public Object saveState(FacesContext context) {

        Object values[] = new Object[4];
        values[0] = _saveState(context);
        values[1] = saveAttachedState(context, methodBindingActionListener);
        values[2] = saveAttachedState(context, actionExpression);
        values[3] = saveAttachedState(context, actionListenerExpression);
        return (values);

    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        _restoreState(context, values[0]);
        methodBindingActionListener = (MethodBinding)
            restoreAttachedState(context, values[1]);
        actionExpression =
            (MethodExpression) restoreAttachedState(context, values[2]);
        actionListenerExpression =(MethodExpression) restoreAttachedState(context, values[3]);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Hide onSelect
    @Property(name="onSelect", isHidden=true, isAttribute=false)
    public String getOnSelect() {
        return super.getOnSelect();
    }

    // Hide rows
    @Property(name="rows", isHidden=true, isAttribute=false)
    public int getRows() {
        return 1;
    }

    /**
     * Setter for property Rows.
     * @param DisplayRows New value of property DisplayRows.
     */
    public void setRows(int DisplayRows) {
        setRows(1);
    }

    // Hide value
    @Property(name="value", isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }

    /**
     * Return a value suitable for the CSS width property to be applied to 
     * an HTML select element or null.
     * <p>
     * If no value has been set, a default value is determined from
     * the theme property <code>dropDown.width</code> defined in the
     * <code>messages</code> theme category. If this theme
     * property is not defined, the width is determined by the
     * longest option element in the rendered select element.
     * </p>
     * @return The value used to determine the width of a select HTML element.
     */
    public String getWidth() {
	String _width = super.getWidth();
	if (_width != null) {
	    return _width;
	}
	_width = ThemeUtilities.getTheme(FacesContext.getCurrentInstance())
	    .getMessage("dropDown.width");
	if (_width != null && _width.trim().length() != 0) {
	    return _width.trim();
	}
	return null;
    }

    /**
     * <p>Used to specify the action to take when this component is 
     * activated by the user. The value of the actionExpression attribute
     * must be one of the following:
     * <ul>
     * <li>an outcome string, used to indicate which page to display next,
     * as defined by a navigation rule in the application configuration
     * resource file <code>(faces-config.xml)</code>.</li>
     * <li>a JavaServer Faces EL expression that resolves
     * to a backing bean method. The method must take no parameters
     * and return an outcome string. The class that defines the method
     * must implement the <code>java.io.Serializable</code> interface or
     * <code>javax.faces.component.StateHolder</code> interface.</li></ul>
     * <p>When you use the actionExpression attribute in the DropDown component, 
     * you must set the submitForm attribute to true.
     */
    @Property(name="actionExpression", isHidden=true, displayName="Action Expression")
    @Property.Method(signature="java.lang.String action()")
    public MethodExpression getActionExpression() {

        if(this.actionExpression == null && isNavigateToValue()) {
            setActionExpression(new DropDownMethodExpression());
        }

        return this.actionExpression;
    }

    /**
     * <p>The actionExpression attribute is used to specify the action to take when this
     * component is activated by the user. The value of the actionExpression attribute
     * must be one of the following:
     * <ul>
     * <li>an outcome string, used to indicate which page to display next,
     * as defined by a navigation rule in the application configuration
     * resource file <code>(faces-config.xml)</code>.</li>
     * <li>a JavaServer Faces EL expression that resolves
     * to a backing bean method. The method must take no parameters
     * and return an outcome string. The class that defines the method
     * must implement the <code>java.io.Serializable</code> interface or
     * <code>javax.faces.component.StateHolder</code> interface.</li></ul>
     * <p>When you use the actionExpression attribute in the DropDown component, you must also
     * set the submitForm attribute to true.
     */
    public void setActionExpression(MethodExpression me) {
        this.actionExpression = me;
    }

    /**@deprecated*/
    //emulating UICommand
    public javax.faces.el.MethodBinding getAction() {
        MethodBinding result = null;
        MethodExpression me = null;

        if (null != (me = getActionExpression())) {
            // if the MethodExpression is an instance of our private
            // wrapper class.
            if (me.getClass() == MethodExpressionMethodBindingAdapter.class) {
                result = ((MethodExpressionMethodBindingAdapter)me).getWrapped();
            }
            else {
                // otherwise, this is a real MethodExpression.  Wrap it
                // in a MethodBinding.
                result = new MethodBindingMethodExpressionAdapter(me);
            }
        }
        return result;
    }

    /**@deprecated*/
    //emulating UICommand
    public void setAction(javax.faces.el.MethodBinding action) {
        MethodExpressionMethodBindingAdapter adapter = null;
        if (null != action) {
            adapter = new MethodExpressionMethodBindingAdapter(action);
            setActionExpression(adapter);
        }
        else {
            setActionExpression(null);
        }
    }

    /**@deprecated*/
    //emulating UICommand
    public javax.faces.el.MethodBinding getActionListener() {
        return this.methodBindingActionListener;
    }

    /**@deprecated*/
    //emulating UICommand
    public void setActionListener(javax.faces.el.MethodBinding actionListener) {
        this.methodBindingActionListener = actionListener;
    }

    /**
     * <p>Used to specify a method to handle an action event that is 
     * triggered when this component is activated by the user. The 
     * value must be a JavaServer Faces EL expression that resolves
     * to a method in a backing bean. The method must take a single parameter
     * that is an ActionEvent, and its return type must be <code>void</code>.
     * The class that defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface. </p>
     *
     * <p>The actionListenerExpression method is only invoked when the submitForm attribute
     * is true.
     */
    @Property(name="actionListenerExpression", isHidden=true, isAttribute=true, displayName="Action Listener Expression", category="Advanced")
    @Property.Method(signature="void processAction(javax.faces.event.ActionEvent)")
    private MethodExpression actionListenerExpression;

    /**
     * <p>The actionListenerExpression attribute is used to specify a method to handle
     * an action event that is triggered when this
     * component is activated by the user. The actionListenerExpression attribute
     * value must be a JavaServer Faces EL expression that resolves
     * to a method in a backing bean. The method must take a single parameter
     * that is an ActionEvent, and its return type must be <code>void</code>.
     * The class that defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface. </p>
     *
     * <p>The actionListenerExpression method is only invoked when the submitForm attribute
     * is true.
     */
    public MethodExpression getActionListenerExpression() {
            return this.actionListenerExpression;
    }

    /**
     * <p>The actionListenerExpression attribute is used to specify a method to handle
     * an action event that is triggered when this
     * component is activated by the user. The actionListenerExpression attribute
     * value must be a JavaServer Faces EL expression that resolves
     * to a method in a backing bean. The method must take a single parameter
     * that is an ActionEvent, and its return type must be <code>void</code>.
     * The class that defines the method must implement the <code>java.io.Serializable</code>
     * interface or <code>javax.faces.component.StateHolder</code> interface. </p>
     *
     * <p>The actionListenerExpression method is only invoked when the submitForm attribute
     * is true.
     */
    public void setActionListenerExpression(MethodExpression me) {
        //call thru
        ActionListener [] curActionListeners = getActionListeners();
        // see if we need to remove existing actionListener.
        // only need to if this.actionListenerExpression != null (since curMethodExpression won't be null)
        if (null != curActionListeners && this.actionListenerExpression != null) {
            for (int i = 0; i < curActionListeners.length; i++) {
                if (curActionListeners[i] instanceof MethodExprActionListener) {
                    MethodExprActionListener curActionListener = (MethodExprActionListener)curActionListeners[i];
                    MethodExpression curMethodExpression = curActionListener.getMethodExpression();
                    if (this.actionListenerExpression.equals(curMethodExpression)) {
                            removeActionListener(curActionListener);
                            break;
                    }
                }
            }
        }
        if (me == null) {
            this.actionListenerExpression = null;
        }
        else {
            this.actionListenerExpression = me;
            addActionListener(new MethodExprActionListener(this.actionListenerExpression));
        }
    }

    /**
     * <p>If this flag is set to true, then the component is always
     * rendered with no initial selection. By default, the component displays
     * the selection that was made in the last submit of the page. This value
     * should be set to true when the drop down is used for navigation.</p>
     */
    @Property(name="forgetValue", displayName="Do not display selected value", category="Advanced", isHidden=true)
    private boolean forgetValue = false;
    private boolean forgetValue_set = false;

    /**
     * <p>If this flag is set to true, then the component is always
     * rendered with no initial selection. By default, the component displays
     * the selection that was made in the last submit of the page. This value
     * should be set to true when the drop down is used for navigation.</p>
     */
    public boolean isForgetValue() {
        if (this.forgetValue_set) {
            return this.forgetValue;
        }
        ValueExpression _vb = getValueExpression("forgetValue");
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
     * <p>If this flag is set to true, then the component is always
     * rendered with no initial selection. By default, the component displays
     * the selection that was made in the last submit of the page. This value
     * should be set to true when the drop down is used for navigation.</p>
     * @see #isForgetValue()
     */
    public void setForgetValue(boolean forgetValue) {
        this.forgetValue = forgetValue;
        this.forgetValue_set = true;
    }

    /**
     * <p>When this attribute is set to true, the value of the menu selection
     * is used as the action, to determine which page is displayed next
     * according to the registered navigation rules. Use this attribute
     * instead of the action attribute when the drop down is used for
     * navigation.
     * When you set navigateToValue to true, you must also set submitForm to true.</p>
     */
    @Property(name="navigateToValue", displayName="Navigate to Component Value", category="Advanced", isHidden=true)
    private boolean navigateToValue = false;
    private boolean navigateToValue_set = false;

    /**
     * <p>When this attribute is set to true, the value of the menu selection
     * is used as the action, to determine which page is displayed next
     * according to the registered navigation rules. Use this attribute
     * instead of the action attribute when the drop down is used for
     * navigation.
     * When you set navigateToValue to true, you must also set submitForm to true.</p>
     */
    public boolean isNavigateToValue() {
        if (this.navigateToValue_set) {
            return this.navigateToValue;
        }
        ValueExpression _vb = getValueExpression("navigateToValue");
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
     * <p>When this attribute is set to true, the value of the menu selection
     * is used as the action, to determine which page is displayed next
     * according to the registered navigation rules. Use this attribute
     * instead of the action attribute when the drop down is used for
     * navigation.
     * When you set navigateToValue to true, you must also set submitForm to true.</p>
     * @see #isNavigateToValue()
     */
    public void setNavigateToValue(boolean navigateToValue) {
        this.navigateToValue = navigateToValue;
        this.navigateToValue_set = true;
    }
    
    /**
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     *
     * <i>Deprecated: The attribute is deprecated starting from version 4.1</i>
     */
    @Property(name="readOnly", isHidden=true, displayName="Read-only", category="Behavior")
    
    /**
     * <p>If this attribute is set to true, the value of the component is
     * rendered as text, preceded by the label if one was defined.</p>
     *
     * @deprecated The attribute is deprected starting from version 4.1
     */
    public boolean isReadOnly() {
        return super.isReadOnly();
    }

    /**
     * <p>When the submitForm attribute is set to true,
     * the form is immediately submitted when the user changes the
     * selection in the drop down list.</p>
     */
    @Property(name="submitForm", displayName="Submit the Page on Change", isHidden=true)
    private boolean submitForm = false;
    private boolean submitForm_set = false;

    /**
     * <p>When the submitForm attribute is set to true,
     * the form is immediately submitted when the user changes the
     * selection in the drop down list.</p>
     */
    public boolean isSubmitForm() {
        if (this.submitForm_set) {
            return this.submitForm;
        }
        ValueExpression _vb = getValueExpression("submitForm");
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
     * <p>When this attribute is set to true,
     * the form is immediately submitted when the user changes the
     * selection in the drop down list.</p>
     * @see #isSubmitForm()
     */
    public void setSubmitForm(boolean submitForm) {
        this.submitForm = submitForm;
        this.submitForm_set = true;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers
     * over the HTML element.</p>
     */
    @Property(name="toolTip", displayName="Tool Tip", category="Behavior")
    private String toolTip = null;

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers
     * over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueExpression _vb = getValueExpression("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    // "labelOnTop must be overridden from ListSelector because it is
    // a themed property. The only way to support a boolean
    // themed property it to test for the existence of a value binding.
    // This is the only way to tell if the application has set a
    // value for a boolean property.
    /**
     * <p>
     * If true, the label is rendered above the
     * component. If false, the label is rendered next to the
     * component.
     * </p>
     * <p>
     * If this property is not set by the application, a themed default
     * value will be sought, using the key <code>dropDown.labelOnTop</code>
     * from the <code>messages.properties</code> file. If there is no
     * value for the key, <code>false</code> is returned.
     * </p>
     */
    @Property(name="labelOnTop", displayName="Label on Top", category="Appearance")
    private boolean labelOnTop = false;
    private boolean labelOnTop_set = false;

    /**
     * <p>
     * If true, the label is rendered above the
     * component. If false, the label is rendered next to the
     * component.
     * </p>
     * <p>
     * If this property is not set by the application, a themed default
     * value will be sought, using the key <code>dropDown.labelOnTop</code>
     * from the <code>messages.properties</code> file. If there is no
     * value for the key, <code>false</code> is returned.
     * </p>
     */
    public boolean isLabelOnTop() {
        if (this.labelOnTop_set) {
            return this.labelOnTop;
        }
        ValueExpression _vb = getValueExpression("labelOnTop");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext().getELContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        } else {
	    // Get a default value from the theme.
	    //
	    Theme theme = ThemeUtilities.getTheme(
		FacesContext.getCurrentInstance());
	    return theme.getMessageBoolean("dropDown.labelOnTop", false);
	}
    }

    /**
     * <p>
     * If true, the label is rendered above the
     * component. If false, the label is rendered next to the
     * component.
     * </p>
     * @see #isLabelOnTop()
     */
    public void setLabelOnTop(boolean labelOnTop) {
        this.labelOnTop = labelOnTop;
        this.labelOnTop_set = true;
    }

    /**
     * <p>Restore the state of this component.</p>
     */
    private void _restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.forgetValue = ((Boolean) _values[1]).booleanValue();
        this.forgetValue_set = ((Boolean) _values[2]).booleanValue();
        this.navigateToValue = ((Boolean) _values[3]).booleanValue();
        this.navigateToValue_set = ((Boolean) _values[4]).booleanValue();
        this.submitForm = ((Boolean) _values[5]).booleanValue();
        this.submitForm_set = ((Boolean) _values[6]).booleanValue();
        this.toolTip = (String) _values[7];
        this.htmlTemplate = (String) _values[8];
        this.labelOnTop = ((Boolean) _values[9]).booleanValue();
        this.labelOnTop_set = ((Boolean) _values[10]).booleanValue();
    }

    /**
     * <p>Save the state of this component.</p>
     */
    private Object _saveState(FacesContext _context) {
        Object _values[] = new Object[11];
        _values[0] = super.saveState(_context);
        _values[1] = this.forgetValue ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.forgetValue_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.navigateToValue ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.navigateToValue_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.submitForm ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.submitForm_set ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.toolTip;
        _values[8] = this.htmlTemplate;
        _values[9] = this.labelOnTop ? Boolean.TRUE : Boolean.FALSE;
        _values[10] = this.labelOnTop_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
