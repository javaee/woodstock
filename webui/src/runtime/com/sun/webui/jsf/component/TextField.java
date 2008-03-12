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

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.util.ComponentUtilities;

import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.el.MethodExpression;

/**
 * The TextField component renders input HTML element.
 * <br>
 * TextField Component class represents text input element.
 * <br>
 * As part of the dynamic behavior, TextField supports auto-validation, where
 * entered data is automatically validated through the ajax call to the server.
 * When validating data through the ajax-based mechanism, the UPDATE_MODEL_VALUES
 * stage of the lifecycle is skipped ( see processUpdates).
 * <p>
 * 
 * Another dynamic feature of TextField is an autoComplete. If autoComplete attribute is 
 * specified as <code>true</code>, this list will be rendered under the text input to allow user to pick 
 * a selection from the list instead of ( or in addition to ) typing it. Once autoComplete is activated,
 * every time the content of the TextField is changed, the updated text field value is used to fetch 
 * autocomplete list with Ajax call. Developer must provide bean method ( see attribute autoCompleteExpression) 
 * to filter and retrieve options on every Ajax call. Such bean must implement <code>AutoComplete</code> interface.</p>
 * 
 * Thus the following pattern is followed for autoComplete:
 * <ul>
 * <li>set autoComplete = true
 * <li>set autoCompleteExpression to be bound to a bean method that filters and returns a list of options
 * <li>every time the textField value is changed, the autoComplete method is called
 * <li>new set of autoCompleteExpression is retrieved by the component after such filtering
 * </ul>
 * 
 */
@Component(type="com.sun.webui.jsf.TextField", 
    family="com.sun.webui.jsf.TextField", displayName="Text Field",
    instanceName="textField", tagName="textField",
    tagRendererType="com.sun.webui.jsf.widget.TextField",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1.2_text_field",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1.2_propsheets_text_field_props")
public class TextField extends Field {    
    /**
     * <p>Construct a new <code>TextField</code>.</p>
     */
    public TextField() {
        super();
        setRendererType("com.sun.webui.jsf.widget.TextField");
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.TextField";
    }
    
    /**
     * Returns the renderer type for the component.
     *
     * Depending on the type of the request, this method will return the default
     * renderer type of "com.sun.webui.jsf.widget.TextField" in case of regular
     * render request, or "com.sun.webui.jsf.ajax.TextField" in case of ajax
     * request. Ajax request represents a special case of request, when partial
     * data is rendered back to the client.
     */
    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.TextField";
        }
        return super.getRendererType();
    }

    /**
     * <p>Return the value to be rendered, as a String (converted
     * if necessary), or <code>null</code> if the value is null.</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getValueAsString(FacesContext context) {
       
        String submittedValue = (String)getSubmittedValue();
        return (submittedValue == null)?
            super.getValueAsString(context):
            submittedValue;
        
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Use the autoCompleteExpression to specify the method that will filter
     * an autoCompleteExpression list, i.e. to produce new set of options to be rendered as
     * autoComplete prompts ( see autoCompleteExpression) . When autoComplete mechanism is enabled
     * ( by specifying autoComplete attribute), autoCompleteExpression method will be called using the 
     * Ajax mechanism in the background every time user changes the content of the 
     * field. Note that this requires autoCompleteExpression method to perform well. 
     * Also, it is recommended to limit number of 
     * options available to user as the return of this method - both for usability and in order 
     * to increase download speed.
     * <br>
     * The value of autoCompleteExpression must be an EL expression and it must evaluate to the
     * name of a public method that is defined by <code>com.sun.webui.jsf.model.AutoComplete</code>, something like: 
     * <code>
     *     public Options[] getOptions(String filter) {
     *      ...
     *     }      
     * </code>
     * In this example, expression would look like this:
     * <code>
     *         &lt; webuijsf:textField
                    autoComplete = "true"
                    autoCompleteExpression ="#{AutoCompleteBean.getOptions}"
                    text="#{AutoCompleteBean.text}"
                    label = "AutoComplete" 
                    id = "tf"  
                    
                /&gt;         
     * </code>
     * </br>
     */
    @Property(name="autoCompleteExpression", displayName="Auto Complete List Filter Expression", category="Advanced", editorClassName="com.sun.rave.propertyeditors.MethodBindingPropertyEditor")
    @Property.Method(signature="void processAction(java.lang.String)")
    private MethodExpression autoCompleteExpression;
    
    /**
     * <p>Returns the stored <code>autoCompleteExpression</code>.
     */
    public MethodExpression getAutoCompleteExpression() {
       return this.autoCompleteExpression;
    }
    
    /**
     * <p>Stores the <code>autoCompleteExpression</code>.
     */
    public void setAutoCompleteExpression(MethodExpression me) {
        this.autoCompleteExpression = me;
    }
    
    
    /**
     * Attribute indicating to turn on/off the autocomplete functionality of the TextField.
     * Autocomplete would trigger the AJAX request to the component.
     * <br>
     * Autocomplete will submit the content of the text field for server side processing that
     * will be processed using JSFX partial lifecycle cycle. Providing of autoComplete options remains
     * responsibility of the developer. Specifically, autoCompleteExpression needs to be set
     * <br>
     * By default autocomplete is off.
     */
    @Property(name="autoComplete", displayName="AutoComplete", category="Behavior")
    private boolean autoComplete = false;
    private boolean autoComplete_set = false;
    
    /**
     * Test if default Ajax functionality should be turned off.
     */
    public boolean isAutoComplete() {
        if (this.autoComplete_set) {
            return this.autoComplete;
        }
        ValueExpression _vb = getValueExpression("autoComplete");
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
     * Set attribute indicating to turn on/off default Ajax functionality.
     * When on, autocomplete options will be displayed as user types in data. <br>
     * AutoComplete requires autoCompleteExpression to be set.
     * <pre>
     *                    &lt; webuijsf:textField
     *                        id="textFieldA"
     *                        size="20"
     *                        required="true"
     *                        autoComplete="true"
     *                        autoCompleteExpression="#{MyBean.autoCompleteExpression}"
     *                    / &gt;
     * </pre>
     */
    public void setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
        this.autoComplete_set = true;        
    }
          
    
    
    /**
     * Attribute indicating to turn on/off the autovalidate functionality of the TextField.
     * Autovalidate would trigger the AJAX request to the validator on the component.
     * Setting autoValidate neccessitates that component is ajaxified, and so it will
     * be accomplished automatically when autoValidate is set to <code>true</code>.
     * <br>
     * Autovalidate will submit the content of the text field for server side processing that
     * will be processed using JSFX partial lifecycle cycle. Validation of the data remains
     * responsibility of the developer. For example, validatorExpression still needs to be set
     * <br>
     * By default autovalidate is off.
     */
    @Property(name="autoValidate", displayName="AutoValidate", category="Behavior")
    private boolean autoValidate = false;
    private boolean autoValidate_set = false;
    
    /**
     * Test if default Ajax functionality should be turned off.
     */
    public boolean isAutoValidate() {
        if (this.autoValidate_set) {
            return this.autoValidate;
        }
        ValueExpression _vb = getValueExpression("autoValidate");
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
     * Set attribute indicating to turn on/off default Ajax functionality.
     * When on, TextField's onBlur event will generate ajax-based validation request,
     * where content of the TextField's input will be validated  ( but not committed
     * during the processUpdates stage). Validation information is sent back to the
     * browser for user information in form of success or standard FacesMesssages in
     * case of failure. <br>
     * AutoValidate requires validator to be set ( via validatorExpression)
     * <pre>
     *                    &lt; webuijsf:textField
     *                        id="textFieldA"
     *                        text="4111 1111 1111 1111"
     *                        label="Enter Credit Card Number"
     *                        size="20"
     *                        required="true"
     *                        autoValidate="true"
     *                        validatorExpression="#{Payment.cardValidator}"
     *                    / &gt;
     * </pre>
     */
    public void setAutoValidate(boolean autoValidate) {
        this.autoValidate = autoValidate;
        this.autoValidate_set = true;        
    }
    
    
    /**
     * The comma separated list of absolute client IDs to notify during
     * text field events.
     * <p>
     * Currently, this feature is only supported by label and alert components. 
     * For example, when the label attribute of the textField tag is not used. 
     * Or, when an alert is used in the page to display validation messages.
     * </p><p>
     * During auto-validation, the text field will notify the label and alert 
     * associated with the given client IDs. If the user's input is found to be 
     * invalid, the label will change text color and display an error indicator.
     * Likewise, if there are any messages associated with the event, the alert
     * will display the assocaited summary, detail, and error indicator.
     * </p>
     */
    @Property(name="notify", isHidden=true, isAttribute=true, displayName="Components", category="Javascript")
    private String notify = null;

    /**
     * Get the comma separated list of absolute client IDs to notify during
     * text field events.
     */
    public String getNotify() {
        if (this.notify != null) {
            return this.notify;
        }
        ValueExpression _vb = getValueExpression("notify");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set the comma separated list of absolute client IDs to notify during
     * text field events.
     */
    public void setNotify(String notify) {
        this.notify = notify;
    }

    /**
     * <p>Flag indicating whether pressing enter in this text field would allow
     * browser to submit the enclosing form ( for all input fields with the exception of TextArea
     * which uses enter key to open a new line) <br>     
     * If set to false, the browser will be prevented from submitting the form on enter in all circumstances.
     * If set to true, the form will be submitted on enter in all circumstances.
     * The default value for this attribute is "false", i.e.
     * default browser auto submit feature will be disabled. 
     * 
     * </p>
     */
    @Property(name="submitForm", isHidden=true, displayName="Submit Form", category="Behavior")
    protected boolean submitForm = false;
    protected boolean submitForm_set = false;
    
    /**
     * <p>Flag indicating whether pressing enter in this text field would allow
     * browser to submit the enclosing form.</p>
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
     * <p>Flag indicating whether pressing enter in this text field would allow
     * browser to submit the enclosing form.</p>
     * @see #isSubmitForm()
     */
    public void setSubmitForm(boolean submitForm) {
        this.submitForm = submitForm;
        this.submitForm_set = true;
    }
    
    /**
     * <p>Returns true if submitForm attribute has been explicitely set,
     * either through binding, value expression, or programmatically.
     * Returns false otherwise.</p>
     */
    public boolean isSubmitFormSet() {
        if (this.submitForm_set) {
            return true;
        }
        ValueExpression _vb = getValueExpression("submitForm");
        if (_vb != null && _vb.getValue(getFacesContext().getELContext()) != null)
            return true;
        
        return false;
        
    }    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Lifecycle methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * <p>Specialized model update behavior on top of that provided by the
     * superclass.
     *
     * <ul>
     *  <li>This method will skip decoding for Ajax requests of type "refresh"
     * and "validate.</li>
     * </ul>
     *
     * @param context <code>FacesContext</code> for this request.
     */
    public void processUpdates(FacesContext context) {
        if (context == null) {
            return;
        }
        // Skip processing in case of "validate" ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "validate")) {
            return; 
        }
        super.processUpdates(context);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // State methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Restore the state of this component.
     */
    public void restoreState(FacesContext _context, Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.autoValidate =     ((Boolean) _values[1]).booleanValue();
        this.autoValidate_set = ((Boolean) _values[2]).booleanValue();
        this.autoComplete =     ((Boolean) _values[3]).booleanValue();
        this.autoComplete_set = ((Boolean) _values[4]).booleanValue();
        this.notify = (String) _values[5];
        this.submitForm = ((Boolean) _values[6]).booleanValue();
        this.submitForm_set = ((Boolean) _values[7]).booleanValue();
        this.autoCompleteExpression = (javax.el.MethodExpression) restoreAttachedState(_context, _values[8]);
        
 }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[9];
        _values[0] = super.saveState(_context);
        _values[1] = this.autoValidate ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.autoValidate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.autoComplete ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.autoComplete_set ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.notify;
        _values[6] = this.submitForm ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.submitForm_set ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = saveAttachedState(_context, autoCompleteExpression);
        return _values;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Create a Label component every time unless labelString is null
     * or the empty string.
     */
    private UIComponent createLabel(String labelString, String style,
            FacesContext context) {
        if (labelString == null || labelString.length() < 1) {
            // Remove any previously created facet.
            ComponentUtilities.removePrivateFacet(this, LABEL_FACET);
            return null;
        }
        
        Label label = new Label();
        label.setId(ComponentUtilities.createPrivateFacetId(this, LABEL_FACET));
        label.setLabelLevel(getLabelLevel());
        label.setStyleClass(style);
        label.setText(labelString);
        label.setLabeledComponent(this);
        label.setIndicatorComponent(this);
        
        ComponentUtilities.putPrivateFacet(this, LABEL_FACET, label);
        
        return label;
    }
}
