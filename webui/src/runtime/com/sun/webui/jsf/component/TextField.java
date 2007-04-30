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
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
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
 * TextField also supports password mode, where data entered will be masked with 
 * asterisks. Password Mode is enabled through the attribute passwordMode. 
 * Note that when passwordMode is on, no TextField content data is sent back (rendered)
 * to the client, even though such data is available through getText(). 
 * This is done to prevent password sniffing on the wire or by viewing browser source.
 * <br>
 * @see com.sun.webui.jsf.renderkit.ajax.TextFieldRenderer
 * @see com.sun.webui.jsf.renderkit.widget.TextFieldRenderer
 */
// TODO use constants in annotations?

@Component(
type="com.sun.webui.jsf.TextField", family="com.sun.webui.jsf.TextField",
        displayName="Text Field",
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ajaxify attribute definition
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Attribute indicating to turn off default Ajax functionality. Set ajaxify to
     * false when providing a different Ajax implementation.
     * If false, default ajax javascript libraries will not be rendered to the client.
     */
    @Property(name="ajaxify", isHidden=true, isAttribute=true, displayName="Ajaxify", category="Javascript")
    private boolean ajaxify = false;
    private boolean ajaxify_set = false;
    
    
    /**
     * Test if default Ajax functionality should be turned off.
     */
    public boolean isAjaxify() {
        if (this.ajaxify_set) {
            return this.ajaxify;
        }
        ValueExpression _vb = getValueExpression("ajaxify");
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
     * Set attribute indicating to turn off default Ajax functionality.
     * <p>
     * Ajaxify attribute is used to optimize delivery of the component to the browser 
     * by rendering or not rendering ajax based libraries.
     * Ajaxify attribute set true only means that ajax javascript modules will be 
     * rendered, enabling ( but not activating) dynamic ajax features on the client side.
     * Once ajax-based modules are rendered, developer can use them directly for custom 
     * validation, or use predesigned autoValidate feature that is part of this implementation.
     * <br>
     * Note that autoValidate=true automatically turn ajaxify attribute on.
     * </p>
     */
    public void setAjaxify(boolean ajaxify) {
        this.ajaxify = ajaxify;
        this.ajaxify_set = true;
    }
    
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// autoValidate attribute definition
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Attribute indicating to turn on/off the autovalidate functionality of the TextField.
     * Autovalidate would trigger the AJAX request to the validator on the component.
     * Setting autoValidate neccessitates that component is ajaxified, and so it will
     * be accomplished automatically when autoValidate is set to <code>true</code>.
     * Thus, if autoValidate is set to true, it will trigger ajaxify= true. Setting
     * autoValidate to false will NOT reset ajaxify attribute. Further note that ajaxify attribute by itself
     * does not lead to autoValidate functionality being enabled - it only allows for an ajax related
     * scripts to be rendered to the client.
     * <br>
     * Autovalidate will submit the content of the text field for server side processing that
     * will be processed using JSFX partial lifecycle cycle. Validation of the data remains
     * responsibility of the developer. For example, validatorExpression still needs to be set
     * <br>
     * By default autovalidate is off.
     *
     *
     *
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
     * <br>
     * When set to true, will also force ajaxify attribute setting to true.
     */
    public void setAutoValidate(boolean autoValidate) {
        this.autoValidate = autoValidate;
        this.autoValidate_set = true;
        if (autoValidate)
            setAjaxify(true);
        
    }
    
    
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// htmlTemplate attribute definition
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Alternative HTML template to be used by this component.
     */
    // Hide value
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;
    private boolean htmlTemplate_set = false;
    /**
     * Get alternative HTML template to be used by this component.
     */
    
    public String getHtmlTemplate() {
        if (htmlTemplate_set && this.htmlTemplate != null) {
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
    
    
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// password attribute definition
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * The default textField template renders input element of type "text"
     * Use this attribute to render "password" style of the textfield that
     * echoes entered characters as "*"
     */
    
    @Property(name="passwordMode", isHidden=true, displayName="Password Mode", category="Appearance")
    private boolean passwordMode = false;
    private boolean passwordMode_set = false;
    
    /**
     * Test if passwordMode mode is be turned on.
     */
    public boolean isPasswordMode() {
        if (this.passwordMode_set) {
            return this.passwordMode;
        }
        ValueExpression _vb = getValueExpression("passwordMode");
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
     * Set attribute indicating to turn on passwordMode mode.
     */
    public void setPasswordMode(boolean passwordModeMode) {
        this.passwordMode = passwordModeMode;
        this.passwordMode_set = true;
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
        this.htmlTemplate = (String) _values[1];
        this.ajaxify =          ((Boolean) _values[2]).booleanValue();
        this.ajaxify_set  =     ((Boolean) _values[3]).booleanValue();
        this.autoValidate =     ((Boolean) _values[4]).booleanValue();
        this.autoValidate_set = ((Boolean) _values[5]).booleanValue();
        this.passwordMode =     ((Boolean) _values[6]).booleanValue();
        this.passwordMode_set = ((Boolean) _values[7]).booleanValue();
    }
    
    /**
     * Save the state of this component.
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[8];
        _values[0] = super.saveState(_context);
        _values[1] = this.htmlTemplate;
        _values[2] = this.ajaxify ? Boolean.TRUE : Boolean.FALSE;
        _values[3] = this.ajaxify_set ? Boolean.TRUE : Boolean.FALSE;
        _values[4] = this.autoValidate ? Boolean.TRUE : Boolean.FALSE;
        _values[5] = this.autoValidate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.passwordMode ? Boolean.TRUE : Boolean.FALSE;
        _values[7] = this.passwordMode_set ? Boolean.TRUE : Boolean.FALSE;
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
    
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Lifecycle management
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    
    /**
     *	<p> Perform the component tree processing required by the <em>Update
     *	    Model Values</em> phase of the request processing lifecycle for
     *	    the input area of the text field ( the component itself), as follows.</p>
     *
     *	    <ul>
     *          <li>If the <code>isValid()</code> property of this
     *		    <code>UIComponent</code> is <code>false</code>, skip
     *		    further processing.</li>
     *          <li>If call happens to be part of async request ( ajax validation)
     *		    , do not update the model and skip further processing.</li>
     *		<li>Proceed with the normal course of  <code>processUpdates()</code> method of all
     *		    facets and children of this {@link UIComponent}</li>
     *      </ul>
     *
     *	@param	context	<code>FacesContext</code> for this request
     */
    
    public void processUpdates(FacesContext context) {
        if (context == null)
            return;
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this, "validate")) {
            return; // Skip processing for ajax based validation events.
        }        
        super.processUpdates(context);
    }
    
    /**
     * <p>Return the value to be rendered, as a String (converted
     * if necessary), or <code>null</code> if the value is null.</p>
     * <p>If password mode has been activated for this textfield,
     * the empty string will be returned instead. This is done in order to avoid
     * sending secret password back to the client where it can be sniffed by viewing the
     * source code of the page. Sending masked string
     * such as set of asterisks would have confused the issue further as it could create an impression that
     * password is saved on the client in the field in some meaningful state, which in reality it will be not.
     * Thus, the password field will be rendered after each page submit,
     * prompting user ( or browser if such functionality is enabled ) to reenter password</p>
     * @param context FacesContext for the current request
     * @return A String value of the component
     */
    public String getValueAsString(FacesContext context) {   
        if (isPasswordMode())
            return new String();
        
        String submittedValue = (String)getSubmittedValue();
        return (submittedValue == null)?
            super.getValueAsString(context):
            submittedValue;
        
    }
}
