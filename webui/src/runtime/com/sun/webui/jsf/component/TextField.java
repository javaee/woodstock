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

/**
 * The TextField component renders input HTML element.
 * <br>
 * TextField Component class represents text input element.
 * <br>
 * As part of the dynamic behavior, TextField supports auto-validation, where
 * entered data is automatically validated through the ajax call to the server.
 * When validating data through the ajax-based mechanism, the UPDATE_MODEL_VALUES
 * stage of the lifecycle is skipped ( see processUpdates).
 * <br>
 * @see com.sun.webui.jsf.renderkit.ajax.TextFieldRenderer
 * @see com.sun.webui.jsf.renderkit.widget.TextFieldRenderer
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
        this.notify = (String) _values[3];
    }

    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[4];
        _values[0] = super.saveState(_context);
        _values[1] = this.autoValidate ? Boolean.TRUE : Boolean.FALSE;
        _values[2] = this.autoValidate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.notify;
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
