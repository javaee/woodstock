/**
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

webui.@THEME_JS@.dojo.provide("webui.@THEME_JS@.formElements");

webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.browser");
webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.common");

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// button functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for button components.
 * @static
 *
 * @deprecated See webui.@THEME_JS@.widget.button
 */
webui.@THEME_JS@.button = {
    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See webui.@THEME_JS@.widget.button
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize button.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var widget = webui.@THEME_JS@.dijit.byId(props.id);
        if (widget == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Set functions
        widget.domNode.isSecondary = webui.@THEME_JS@.button.isSecondary;
        widget.domNode.setSecondary = webui.@THEME_JS@.button.setSecondary;
        widget.domNode.isPrimary = webui.@THEME_JS@.button.isPrimary;
        widget.domNode.setPrimary = webui.@THEME_JS@.button.setPrimary;
        widget.domNode.isMini = webui.@THEME_JS@.button.isMini;
        widget.domNode.setMini = webui.@THEME_JS@.button.setMini;
        widget.domNode.getDisabled = webui.@THEME_JS@.button.getDisabled;
        widget.domNode.setDisabled = webui.@THEME_JS@.button.setDisabled;
        widget.domNode.getVisible = webui.@THEME_JS@.button.getVisible;
        widget.domNode.setVisible = webui.@THEME_JS@.button.setVisible;
        widget.domNode.getText = webui.@THEME_JS@.button.getText;
        widget.domNode.setText = webui.@THEME_JS@.button.setText;
        widget.domNode.doClick = webui.@THEME_JS@.button.click;

        return true;
    },

    /**
     * Simulate a mouse click in a button. 
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).click();
     */
    click: function() {
        return this.click();
    },

    /**
     * Get the textual label of a button. 
     *
     * @return {String} The element value.
     * @deprecated Use document.getElementById(id).getProps().value;
     */
    getText: function() {
        return this.getProps().value;
    },

    /**
     * Set the textual label of a button. 
     *
     * @param {String} text The element value
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({value: "text"});
     */
    setText: function(text) {
        return this.setProps({value: text});
    },

    /**
     * Use this function to show or hide a button. 
     *
     * @param {boolean} show true to show the element, false to hide the element
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({visible: boolean});
     */
    setVisible: function(show) {
        if (show == null) {
            return null;
        }
        return this.setProps({visible: show});
    },

    /**
     * Use this function to find whether or not this is visible according to our
     * spec.
     *
     * @return {boolean} true if visible; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().visible;
     */
    getVisible: function() {
        return this.getProps().visible;
    },

    /**
     * Test if button is set as "primary".
     *
     * @return {boolean} true if primary; otherwise, false for secondary
     * @deprecated Use document.getElementById(id).getProps().primary;
     */
    isPrimary: function() {
        return this.getProps().primary;
    },

    /**
     * Set button as "primary".
     *
     * @param {boolean} primary true for primary, false for secondary
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({primary: boolean});
     */
    setPrimary: function(primary) {
        if (primary == null) {
            return null;
        }
        return this.setProps({primary: primary});
    },

    /**
     * Test if button is set as "secondary".
     *
     * @return {boolean} true if secondary; otherwise, false for primary
     * @deprecated Use !(document.getElementById(id).getProps().primary);
     */
    isSecondary: function() {
        return !(this.getProps().primary);
    },

    /**
     * Set button as "secondary".
     *
     * @param {boolean} secondary true for secondary, false for primary
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({primary: false});
     */
    setSecondary: function(secondary) {
        if (secondary == null) {
            return null;
        }
        return this.setProps({primary: !secondary});
    },

    /**
     * Test if button is set as "mini".
     *
     * @return {boolean} true if mini; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().mini;
     */
    isMini: function() {
        return this.getProps().mini;
    },

    /**
     * Set button as "mini".
     *
     * @param {boolean} mini true for mini, false for standard button
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({mini: boolean});
     */
    setMini: function(mini) {
        if (mini == null) {
            return null;
        }
        return this.setProps({mini: mini});
    },

    /**
     * Test disabled state of button.
     *
     * @return {boolean} true if disabled; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().disabled;
     */
    getDisabled: function() {
        return this.getProps().disabled;
    },

    /**
     * Test disabled state of button.
     *
     * @param {boolean} disabled true if disabled; otherwise, false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(disabled) {
        if (disabled == null) {
            return null;
        }
        return this.setProps({disabled: disabled});
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// checkbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for checkbox components.
 * @static
 * 
 * @deprecated See webui.@THEME_JS@.widget.checkbox
 */
webui.@THEME_JS@.checkbox = {
    /**
     * Set the disabled state for the given checkbox element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param {String} elementId The element Id
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) {
        return webui.@THEME_JS@.rbcb.setDisabled(elementId, disabled,
            "checkbox", "Cb", "CbDis");
    },

    /** 
     * Set the disabled state for all the checkboxes in the check box
     * group identified by controlName. If disabled
     * is set to true, the check boxes are shown with disabled styles.
     *
     * @param {String} controlName The checkbox group control name
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.@THEME_JS@.rbcb.setGroupDisabled(controlName,
            disabled, "checkbox", "Cb", "CbDis");
    },

    /**
     * Set the checked property for a checkbox with the given element Id.
     *
     * @param {String} elementId The element Id
     * @param checked true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */
    setChecked: function(elementId, checked) {
        return webui.@THEME_JS@.rbcb.setChecked(elementId, checked,
            "checkbox");
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// dropdown functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for dropDown components.
 * @static
 * 
 * @deprecated See webui.@THEME_JS@.widget.dropDown
 */
webui.@THEME_JS@.dropDown = {
    /**
     * Use this function to access the HTML select element that makes up
     * the dropDown.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the dropDown).
     * @return {Node} a reference to the select element. 
     * @deprecated Use document.getElementById(elementId).setSelectElement()
     */
    getSelectElement: function(elementId) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getSelectElement();
        }
        return null;
    },

    /**
     * This function is invoked by the choice onselect action to set the
     * selected, and disabled styles.
     *
     * Page authors should invoke this function if they set the 
     * selection using JavaScript.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(elementId).changed();
     */
    changed: function(elementId) {         
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.changed();
        }
        return false;
    },

    /**
     * Set the disabled state for given dropdown element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * Page authors should invoke this function if they dynamically
     * enable or disable a dropdown using JavaScript.
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(elementId).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({ disabled: disabled});
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the dropDown. If no option is selected, this
     * function returns null. 
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {String} The value of the selected option, or null if none is
     * selected. 
     * @deprecated Use document.getElementById(elementId).getSelectedValue();
     */
    getSelectedValue: function(elementId) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getSelectedValue();
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the dropDown. If no option is selected, this
     * function returns null.
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {String} The label of the selected option, or null if none is
     * selected. 
     * @deprecated Use document.getElementById(elementId).getSelectedLabel();
     */
    getSelectedLabel: function(elementId) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getSelectedLabel();
        }
        return null;
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// field functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for field components.
 * @static
 *
 * @deprecated See webui.@THEME_JS@.widget.field
 */
webui.@THEME_JS@.field = {
    /**
     * Use this function to get the HTML input or textarea element
     * associated with a TextField, PasswordField, HiddenField or TextArea
     * component.
     *
     * @param {String} elementId The element ID of the field 
     * @return {Node} the input or text area element associated with the field component
     * @deprecated Use document.getElementById(elementId).getInputElement()
     */
    getInputElement: function(elementId) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getInputElement();
        }
        return null;
    },

    /**
     * Use this function to get the value of the HTML element 
     * corresponding to the Field component.
     *
     * @param {String} elementId The element ID of the Field component
     * @return {String} the value of the HTML element corresponding to the Field component 
     * @deprecated Use document.getElementById(id).getProps().value;
     */
    getValue: function(elementId) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getProps().value;
        }
        return null;
    },

    /**
     * Use this function to set the value of the HTML element 
     * corresponding to the Field component
     *
     * @param {String} elementId The element ID of the Field component
     * @param {String} newValue The new value to enter into the input element Field component 
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({value: "text"});
     */
    setValue: function(elementId, newValue) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({value: newValue});
        }
        return null;
    },

    /** 
     * Use this function to get the style attribute for the field. 
     * The style retrieved will be the style on the span tag that 
     * encloses the (optional) label element and the input element.
     *
     * @param {String} elementId The element ID of the Field component
     * @return {String} The style property of the field.
     * @deprecated Use document.getElementById(id).getProps().style;
     */
    getStyle: function(elementId) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getProps().style;
        }
        return null;
    },

    /**
     * Use this function to set the style attribute for the field. 
     * The style will be set on the <span> tag that surrounds the field.
     *
     * @param {String} elementId The element ID of the Field component
     * @param {String} newStyle The new style to apply
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({style: newStyle});
     */
    setStyle: function(elementId, newStyle) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({style: newStyle});
        }
        return null;
    },

    /**
     * Use this function to disable or enable a field. As a side effect
     * changes the style used to render the field. 
     *
     * @param {String} elementId The element ID of the field 
     * @param {boolean} newDisabled true to disable the field, false to enable the field
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, newDisabled) {  
        if (newDisabled == null) {
            return null;
        }
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({disabled: newDisabled});
        }
        return null;
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// hyperlink functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for hyperlink components.
 * @static
 *
 * @deprecated See webui.@THEME_JS@.widget.hyperlink
 */
webui.@THEME_JS@.hyperlink = {
    /**
     * This function is used to submit a hyperlink.
     * <p>
     * Note: Params are name value pairs but all one big string array so 
     * params[0] and params[1] form the name and value of the first param.
     * </p>
     *
     * @params {Object} hyperlink The hyperlink element
     * @params {String} formId The form id
     * @params {Object} params Name value pairs
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See webui.@THEME_JS@.widget.hyperlink
     */
    submit: function(hyperlink, formId, params) {
        // Need to test widget for tab and common task components. If a widget 
        // does not exist, fall back to the old code.
	var widget = webui.@THEME_JS@.dijit.byId(hyperlink.id);
	if (widget == null) {
            // If a widget does not exist, we shall create one in order to call
            // the submit function directly.
            webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.hyperlink");
            widget = new webui.@THEME_JS@.widget.hyperlink({id: hyperlink.id});
	}
        return widget.submitFormData(formId, params);
    },

    /**
     * Use this function to access the HTML img element that makes up
     * the icon hyperlink.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * assigned to the outter most tag enclosing the HTML img element).
     * @return {Node} The HTML image element.
     * @deprecated Use document.getElementById(elementId).getProps().enabledImage;
     */
    getImgElement: function(elementId) {
        // Need to test widget for alarmStatus, jobstatus, and notification phrase
        // components. If a widget does not exist, fall back to the old code.
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        var props = (widget) ? widget.getProps() : null;
        if (props && props.enabledImage) {
            var imgWidget = webui.@THEME_JS@.dijit.byId(props.enabledImage.id);
            if (imgWidget != null) {
                return imgWidget.domNode;    
            }
        }

        // Image hyperlink is now a naming container and the img element id 
        // includes the ImageHyperlink parent id.
        if (elementId != null) {
            var parentid = elementId;
            var colon_index = elementId.lastIndexOf(":");
            if (colon_index != -1) {
                parentid = elementId.substring(colon_index + 1);
            }
            return document.getElementById(elementId + ":" + parentid + "_image");
        }
        return document.getElementById(elementId + "_image");
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// jumpDropDown functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for jumpDropDown components.
 * @static
 *
 * @deprecated See webui.@THEME_JS@.widget.dropDown
 */
webui.@THEME_JS@.jumpDropDown = {
    /**
     * This function is invoked by the jumpdropdown onchange action to set the
     * form action and then submit the form.
     *
     * Page authors should invoke this function if they set the selection using 
     * JavaScript.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(elementId).changed()
     */
    changed: function(elementId) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.changed();
        }
        return false;
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// listbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for listbox components.
 * @static
 * 
 * @deprecated See webui.@THEME_JS@.widget.listbox
 */
webui.@THEME_JS@.listbox = {
    /**
     * Use this function to access the HTML select element that makes up
     * the list. 
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the list).
     * @return {Node} The HTML select element.
     * @deprecated Use document.getElementById(elementId).getSelectElement()
     */
    getSelectElement: function(elementId) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getSelectElement();
        }
        return null;
    },

    /**
     * This function is invoked by the list onselect action to set the selected, 
     * and disabled styles.
     *
     * Page authors should invoke this function if they set the selection
     * using JavaScript.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(elementId).changed();
     */
    changed: function(elementId) {         
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.changed();
        }
        return false;
    },

    /**
     * Invoke this JavaScript function to set the enabled/disabled state
     * of the listbox component. In addition to disabling the list, it
     * also changes the styles used when rendering the component. 
     *
     * Page authors should invoke this function if they dynamically
     * enable or disable a list using JavaScript.
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(elementId).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({disabled: disabled});
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {String} The value of the selected option, or null if none is
     * selected.
     * @deprecated Use document.getElementById(elementId).getSelectedValue();
     */
    getSelectedValue: function(elementId) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getSelectedValue();
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {String} The label of the selected option, or null if none is selected.
     * @deprecated Use document.getElementById(elementId).getSelectedLabel();
     */
    getSelectedLabel: function(elementId) { 
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.getSelectedLabel();
        }
        return null;
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Generic checkbox and radio button functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for rbcbGroup components.
 * @static
 *
 * @deprecated See webui.@THEME_JS@.widget.rbcbGroup
 */
webui.@THEME_JS@.rbcb = {
    /**
     * 
     * @param {String} elementId The element Id.
     * @param {boolean} checked true or false
     * @param {String} type
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */ 
    setChecked: function(elementId, checked, type) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({checked: checked});
        }
        return null; 
    },

    /**
     *
     * @param {String} elementId The element Id.
     * @param {boolean} disabled true or false
     * @param {String} type
     * @param {String} enabledStyle
     * @param {String} disabledStyle
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */ 
    setDisabled: function(elementId, disabled, type, enabledStyle,
            disabledStyle) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({disabled: disabled});
        }
        return null; 
    },

    /** 
     * Set the disabled state for all radio buttons with the given controlName.
     * If disabled is set to true, the element is shown with disabled styles.
     *
     * @param {String} elementId The element Id
     * @param {String} formName The name of the form containing the element
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled, type, enabledStyle,
            disabledStyle) {
        var widget = webui.@THEME_JS@.dijit.byId(elementId);
        if (widget) {
            return widget.setProps({disabled: disabled});
        }
        return null;
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// radiobutton functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for radioButton components.
 * @static
 *
 * @deprecated See webui.@THEME_JS@.widget.radioButton
 */
webui.@THEME_JS@.radiobutton = {
    /**
     * Set the disabled state for the given radiobutton element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param {String} elementId The element Id.
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) {    
        return webui.@THEME_JS@.rbcb.setDisabled(elementId, disabled, 
            "radio", "Rb", "RbDis");
    },

    /**
     * Set the disabled state for all the radio buttons in the radio button
     * group identified by controlName. If disabled
     * is set to true, the check boxes are displayed with disabled styles.
     *
     * @param {String} controlName The radio button group control name
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.@THEME_JS@.rbcb.setGroupDisabled(controlName, disabled, 
            "radio", "Rb", "RbDis");
    },

    /**
     * Set the checked property for a radio button with the given element Id.
     *
     * @param {String} elementId The element Id
     * @param {boolean} checked true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */
    setChecked: function(elementId, checked) {
        return webui.@THEME_JS@.rbcb.setChecked(elementId, checked, "radio");
    }
};

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// upload functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * @class This class contains functions for upload components.
 * @static
 */
webui.@THEME_JS@.upload = {
    /**
     * Use this function to get the HTML input element associated with the
     * Upload component.  
     * @param {String} elementId The client id of the Upload component
     * @return {Node} the input element associated with the Upload component
     * else null if elementId is null or "".
     */
    getInputElement: function(elementId) { 
        if (elementId == null || elementId == "") {
	    return null;
	}

	// The upload component MUST always render the input element
	// with the following suffix on the id 
	// "_com.sun.webui.jsf.upload".
	// This "binds" this version of the component to this theme
	// version.
	// This will change when "field" becomes a widget.
	//
        var element = document.getElementById(elementId + 
            "_com.sun.webui.jsf.upload");
        if (element && element.tagName == "INPUT") { 
            return element; 
        } else {
	    return null;
	}
    },

    /**
     * Use this function to disable or enable a upload. As a side effect
     * changes the style used to render the upload. 
     *
     * @param {String} elementId The client id of the upload component.
     * @param {boolean} disabled true to disable the upload, false to enable the upload
     * @return {boolean} true if successful; otherwise, false.
     */
    setDisabled: function(elementId, disabled) {  

        if (elementId == null || elementId == "" || 
		disabled == null || disabled == "") {
            // must supply an elementId && state
            return false;
        }
        var input = webui.@THEME_JS@.upload.getInputElement(elementId); 
        if (input == null) {
            // specified elementId not found
            return false;
        }
        input.disabled = disabled;
	return true;
    },

    /**
     * Set the encoding type of the form to "multipart/form-data".
     * 
     *
     * @param {String} elementId The client id of the upload component.
     * @return {boolean} true if encoding type can be set, else false.
     */
    setEncodingType: function(elementId) { 
	if (elementId == null || elementId == "") {
	    return false;
	}

        var upload = webui.@THEME_JS@.upload.getInputElement(elementId); 
        var form = upload != null ? upload.form : null;
	if (form != null) {

            // form.enctype does not work for IE, but works Safari
            // form.encoding works on both IE and Firefox
	    //
            if (webui.@THEME_JS@.browser.isSafari()) {
                form.enctype = "multipart/form-data";
            } else {
                form.encoding = "multipart/form-data";
            }
	    return true;
        }
	return false;
    },

    /**
     * Create a hidden field with id "preservePathId" and add a listener
     * to the upload's input element, "uploadId". The listener is
     * is added for the onchange event of the upload's input field,
     * see preservePathListener.
     *
     * @param {String} uploadId The client id of the upload component.
     * @param {String} preservePathId
     * @return {boolean} true if the hidden element is created and a listener is
     * added, else false.
     */
    preservePath: function(uploadId, preservePathId) {
	if (uploadId == null || uploadId == "" ||
		preservePathId == null || preservePathId == "") {
	    return false;
	}

	// If there is no upload component, don't do anything.
	// I'm not sure if there is a timing issue here.
	//
	var uploadElement = webui.@THEME_JS@.upload.getInputElement(uploadId);
	if (uploadElement == null) {
	    return false;
	}
	var theForm = uploadElement.form;

	// Create the change listener.
	// The event target/srcElement is the upload input element
	// its value is the changed value, save it in the 
	// preservePath hidden field.
	//
	var onChangeListener = function(evt) {

	    // Is IE
	    if (document.attachEvent) {
		node = evt.srcElement;
	    } else {
		node = evt.target;
	    }
	    // node is the upload input element
	    //
	    var preservePath = null;
	    try {
		preservePath = theForm.elements[preservePathId];
	    } catch (e) {
	    }

	    // If the hidden field isn't there create it and assign
	    // the node's value
	    //
	    if (preservePath != null) {
		preservePath.value = node.value;
	    } else {
		webui.@THEME_JS@.common.insertHiddenField(preservePathId, 
			node.value, theForm);
	    }
	    return true;
	};

	if (uploadElement.addEventListener) {
	    uploadElement.addEventListener('change', onChangeListener, true);
	} else {
	    uploadElement.attachEvent('onchange', onChangeListener);
	}
	return true;
    }
};
