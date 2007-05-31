//<!--
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.formElements");

dojo.require("webui.@THEME@.common");

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// button functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.button name space.
 *
 * @deprecated See webui.@THEME@.widget.button
 */
webui.@THEME@.button = {
    /**
     * This function is used to initialize HTML element properties with the
     * following Object literals.
     *
     * <ul>
     *  <li>id</li>
     * </ul>
     *
     * Note: This is considered a private API, do not use.
     *
     * @param props Key-Value pairs of properties.
     * @deprecated See webui.@THEME@.widget.button
     */
    init: function(props) {
        if (props == null || props.id == null) {
            return false;
        }
        var widget = dojo.widget.byId(props.id);
        if (widget == null) {
            return false;
        }

        // Set functions
        widget.domNode.isSecondary = webui.@THEME@.button.isSecondary;
        widget.domNode.setSecondary = webui.@THEME@.button.setSecondary;
        widget.domNode.isPrimary = webui.@THEME@.button.isPrimary;
        widget.domNode.setPrimary = webui.@THEME@.button.setPrimary;
        widget.domNode.isMini = webui.@THEME@.button.isMini;
        widget.domNode.setMini = webui.@THEME@.button.setMini;
        widget.domNode.getDisabled = webui.@THEME@.button.getDisabled;
        widget.domNode.setDisabled = webui.@THEME@.button.setDisabled;
        widget.domNode.getVisible = webui.@THEME@.button.getVisible;
        widget.domNode.setVisible = webui.@THEME@.button.setVisible;
        widget.domNode.getText = webui.@THEME@.button.getText;
        widget.domNode.setText = webui.@THEME@.button.setText;
        widget.domNode.doClick = webui.@THEME@.button.click;

        return true;
    },

    /**
     * Simulate a mouse click in a button. 
     *
     * @deprecated Use document.getElementById(id).click();
     */
    click: function() {
        return this.click();
    },

    /**
     * Get the textual label of a button. 
     *
     * @return The element value.
     * @deprecated Use document.getElementById(id).getProps().value;
     */
    getText: function() {
        return this.getProps().value;
    },

    /**
     * Set the textual label of a button. 
     *
     * @param text The element value
     * @deprecated Use document.getElementById(id).setProps({value: "text"});
     */
    setText: function(text) {
        return this.setProps({value: text});
    },

    /**
     * Use this function to show or hide a button. 
     *
     * @param show true to show the element, false to hide the element
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
     * @return true if visible; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().visible;
     */
    getVisible: function() {
        return this.getProps().visible;
    },

    /**
     * Test if button is set as "primary".
     *
     * @return true if primary; otherwise, false for secondary
     * @deprecated Use document.getElementById(id).getProps().primary;
     */
    isPrimary: function() {
        return this.getProps().primary;
    },

    /**
     * Set button as "primary".
     *
     * @param primary true for primary, false for secondary
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
     * @return true if secondary; otherwise, false for primary
     * @deprecated Use !(document.getElementById(id).getProps().primary);
     */
    isSecondary: function() {
        return !(this.getProps().primary);
    },

    /**
     * Set button as "secondary".
     *
     * @param secondary true for secondary, false for primary
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
     * @return true if mini; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().mini;
     */
    isMini: function() {
        return this.getProps().mini;
    },

    /**
     * Set button as "mini".
     *
     * @param mini true for mini, false for standard button
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
     * @return true if disabled; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().disabled;
     */
    getDisabled: function() {
        return this.getProps().disabled;
    },

    /**
     * Test disabled state of button.
     *
     * @param disabled true if disabled; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(disabled) {
        if (disabled == null) {
            return null;
        }
        return this.setProps({disabled: disabled});
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// checkbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.checkbox name space.
 * 
 * @deprecated See webui.@THEME@.widget.checkbox
 */
webui.@THEME@.checkbox = {
    /**
     * Set the disabled state for the given checkbox element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param disabled true or false
     *
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) {
        return webui.@THEME@.rbcb.setDisabled(elementId, disabled,
            "checkbox", "Cb", "CbDis");
    },

    /** 
     * Set the disabled state for all the checkboxes in the check box
     * group identified by controlName. If disabled
     * is set to true, the check boxes are shown with disabled styles.
     *
     * @param controlName The checkbox group control name
     * @param disabled true or false
     *
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.@THEME@.rbcb.setGroupDisabled(controlName,
            disabled, "checkbox", "Cb", "CbDis");
    },

    /**
     * Set the checked property for a checkbox with the given element Id.
     *
     * @param elementId The element Id
     * @param checked true or false
     *
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */
    setChecked: function(elementId, checked) {
        return webui.@THEME@.rbcb.setChecked(elementId, checked,
            "checkbox");
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// dropdown functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.dropdown name space.
 * 
 * @deprecated See webui.@THEME@.widget.dropDown
 */
webui.@THEME@.dropDown = {
    /**
     * Use this function to access the HTML select element that makes up
     * the dropDown.
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the dropDown).
     * @return a reference to the select element. 
     *
     * @deprecated Use document.getElementById(elementId).setSelectElement()
     */
    getSelectElement: function(elementId) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getSelectElement();
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
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     *
     * @deprecated Use document.getElementById(elementId).changed();
     */
    changed: function(elementId) {         
        var widget = dojo.widget.byId(elementId);
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
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param disabled true or false
     *
     * @deprecated Use document.getElementById(elementId).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({ disabled: disabled});
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the dropDown. If no option is selected, this
     * function returns null. 
     *
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The value of the selected option, or null if none is
     * selected. 
     *
     * @deprecated Use document.getElementById(elementId).getSelectedValue();
     */
    getSelectedValue: function(elementId) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getSelectedValue();
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the dropDown. If no option is selected, this
     * function returns null.
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The label of the selected option, or null if none is
     * selected. 
     *
     * @deprecated Use document.getElementById(elementId).getSelectedLabel();
     */
    getSelectedLabel: function(elementId) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getSelectedLabel();
        }
        return null;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// field functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.field name space.
 *
 * @deprecated See webui.@THEME@.widget.field
 */
webui.@THEME@.field = {
    /**
     * Use this function to get the HTML input or textarea element
     * associated with a TextField, PasswordField, HiddenField or TextArea
     * component.
     *
     * @param elementId The element ID of the field 
     * @return the input or text area element associated with the field component
     *
     * @deprecated Use document.getElementById(elementId).getInputElement()
     */
    getInputElement: function(elementId) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getInputElement();
        }
        return null;
    },

    /**
     * Use this function to get the value of the HTML element 
     * corresponding to the Field component.
     *
     * @param elementId The element ID of the Field component
     * @return the value of the HTML element corresponding to the Field component 
     *
     * @deprecated Use document.getElementById(id).getProps().value;
     */
    getValue: function(elementId) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getProps().value;
        }
        return null;
    },

    /**
     * Use this function to set the value of the HTML element 
     * corresponding to the Field component
     *
     * @param elementId The element ID of the Field component
     * @param newValue The new value to enter into the input element Field component 
     *
     * @deprecated Use document.getElementById(id).setProps({value: "text"});
     */
    setValue: function(elementId, newValue) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({value: newValue});
        }
        return null;
    },

    /** 
     * Use this function to get the style attribute for the field. 
     * The style retrieved will be the style on the span tag that 
     * encloses the (optional) label element and the input element.
     *
     * @param elementId The element ID of the Field component
     *
     * @deprecated Use document.getElementById(id).getProps().style;
     */
    getStyle: function(elementId) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getProps().style;
        }
        return null;
    },

    /**
     * Use this function to set the style attribute for the field. 
     * The style will be set on the <span> tag that surrounds the field.
     *
     * @param elementId The element ID of the Field component
     * @param newStyle The new style to apply
     *
     * @deprecated Use document.getElementById(id).setProps({style: newStyle});
     */
    setStyle: function(elementId, newStyle) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({style: newStyle});
        }
        return null;
    },

    /**
     * Use this function to disable or enable a field. As a side effect
     * changes the style used to render the field. 
     *
     * @param elementId The element ID of the field 
     * @param newDisabled true to disable the field, false to enable the field
     *
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, newDisabled) {  
        if (newDisabled == null) {
            return null;
        }
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({disabled: newDisabled});
        }
        return null;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// hyperlink functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.hyperlink name space.
 *
 * @deprecated See webui.@THEME@.widget.hyperlink
 */
webui.@THEME@.hyperlink = {
    /**
     * This function is used to submit a hyperlink.
     *
     * Note: Params are name value pairs but all one big string array so 
     * params[0] and params[1] form the name and value of the first param.
     *
     * @params hyperlink The hyperlink element
     * @params formId The form id
     * @params params Name value pairs
     *
     * @deprecated  See webui.@THEME@.widget.hyperlink    
     */
    submit: function(hyperlink, formId, params) {
        // Need to test widget for tab and common task. If a widget does not
        // exist, fall back to the old code.
	var widget = dojo.widget.byId(hyperlink.id);
	if (widget) {
	    return widget.submit(formId, params);
	}

        // If a widget does not exist, we shall call the submit function 
        // directly.
        //
        // Warning: Do not use dojo.require() here. The webui.@THEME@.widget
        // namespace must be defined prior to retrieving the hyperlink module.
        //
        // Dojo appears to parse for dojo.require() statments when
        // djConfig.debugAtAllCosts is true. At this time, "modules" is 
        // undefined and an exception is thrown.
        dojo.require.apply(dojo, ["webui.@THEME@.widget.hyperlink"]);

        return webui.@THEME@.widget.hyperlink.submit(formId, params, hyperlink.id);
    },

    /**
     * Use this function to access the HTML img element that makes up
     * the icon hyperlink.
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the outter most tag enclosing the HTML img element).
     * @return a reference to the img element.
     *
     * @deprecated Use document.getElementById(elementId).getProps().enabledImage;
     */
    getImgElement: function(elementId) {
        // Need to test widget for alarmStatus, jobstatus and notification phrase
        // components. If a widget does not exist, fall back to the old code.
        var widget = dojo.widget.byId(elementId);
        if (widget) {
            return widget.getProps().enabledImage;
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
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// jumpDropDown functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.jumpdropdown name space.
 *
 * @deprecated See webui.@THEME@.widget.dropDown
 */
webui.@THEME@.jumpDropDown = {
    /**
     * This function is invoked by the jumpdropdown onchange action to set the
     * form action and then submit the form.
     *
     * Page authors should invoke this function if they set the selection using 
     * JavaScript.
     *
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     *
     * @deprecated Use document.getElementById(elementId).changed()
     */
    changed: function(elementId) {
        var widget = dojo.widget.byId(elementId);
        if (widget) {
            return widget.changed();
        }
        return false;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// listbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.listbox name space.
 * 
 * @deprecated See webui.@THEME@.widget.listbox
 */
webui.@THEME@.listbox = {
    /**
     * Use this function to access the HTML select element that makes up
     * the list. 
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the list).
     * @return a reference to the select element.
     *
     * @deprecated Use document.getElementById(elementId).getSelectElement()
     */
    getSelectElement: function(elementId) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getSelectElement();
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
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     *
     * @deprecated Use document.getElementById(elementId).changed();
     */
    changed: function(elementId) {         
        var widget = dojo.widget.byId(elementId);
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
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param disabled true or false
     *
     * @deprecated Use document.getElementById(elementId).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({disabled: disabled});
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The value of the selected option, or null if none is
     * selected.
     *
     * @deprecated Use document.getElementById(elementId).getSelectedValue();
     */
    getSelectedValue: function(elementId) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getSelectedValue();
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The label of the selected option, or null if none is selected.
     *
     * @deprecated Use document.getElementById(elementId).getSelectedLabel();
     */
    getSelectedLabel: function(elementId) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getSelectedLabel();
        }
        return null;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Generic checkbox and radio button functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.rbcb name space.
 *
 * @deprecated See webui.@THEME@.widget.rbcbGroup
 */
webui.@THEME@.rbcb = {
    /**
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});
     */ 
    setChecked: function(elementId, checked, type) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({ checked: checked });
        }
        return null; 
    },

    /**
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean}); 
     */ 
    setDisabled: function(elementId, disabled, type, enabledStyle,
            disabledStyle) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({ disabled: disabled });
        }
        return null; 
    },

    /** 
     * Set the disabled state for all radio buttons with the given controlName.
     * If disabled is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param formName The name of the form containing the element
     * @param disabled true or false
     *
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled, type, enabledStyle,
            disabledStyle) {
        var domNode = document.getElementById(controlName);
        if (domNode) {
            return domNode.setProps({ disabled: disabled });
        }
        return null;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// radiobutton functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.radiobutton name space.
 *
 * @deprecated See webui.@THEME@.widget.radioButton
 */
webui.@THEME@.radiobutton = {
    /**
     * Set the disabled state for the given radiobutton element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param disabled true or false
     *
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});  
     */
    setDisabled: function(elementId, disabled) {    
        return webui.@THEME@.rbcb.setDisabled(elementId, disabled, 
            "radio", "Rb", "RbDis");
    },

    /**
     * Set the disabled state for all the radio buttons in the radio button
     * group identified by controlName. If disabled
     * is set to true, the check boxes are displayed with disabled styles.
     *
     * @param controlName The radio button group control name
     * @param disabled true or false
     *
     * @deprecated Use document.getElementById(id).setProps({disabled: boolean});
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.@THEME@.rbcb.setGroupDisabled(controlName, disabled, 
            "radio", "Rb", "RbDis");
    },

    /**
     * Set the checked property for a radio button with the given element Id.
     *
     * @param elementId The element Id
     * @param checked true or false
     *
     * @deprecated Use document.getElementById(id).setProps({checked: boolean});  
     */
    setChecked: function(elementId, checked) {
        return webui.@THEME@.rbcb.setChecked(elementId, checked, "radio");
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// upload functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.upload name space.
 */
webui.@THEME@.upload = {
    /**
     * Use this function to get the HTML input element associated with the
     * Upload component.  
     * @param elementId The element ID of the Upload
     * @return the input element associated with the Upload component 
     */
    getInputElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if (element && element.tagName == "INPUT") { 
            return element; 
        }
        // Return an HTML input element of type "file".
        return document.getElementById(elementId + "_com.sun.webui.jsf.upload");
    },

    /**
     * Use this function to disable or enable a upload. As a side effect
     * changes the style used to render the upload. 
     *
     * @param elementId The element ID of the upload 
     * @param show true to disable the upload, false to enable the upload
     */
    setDisabled: function(elementId, disabled) {  
        if (elementId == null || disabled == null) {
            // must supply an elementId && state
            return false;
        }
        var input = webui.@THEME@.upload.getInputElement(elementId); 
        if (input == null) {
            // specified elementId not found
            return false;
        }
        // Disable field using setDisabled function -- do not hard code styles here.
        return webui.@THEME@.field.setDisabled(input.id, disabled);
    },

    setEncodingType: function(elementId) { 
        var upload = webui.@THEME@.upload.getInputElement(elementId); 
        var form = upload; 
        while(form != null) { 
            form = form.parentNode; 
            if(form.tagName == "FORM") { 
                break; 
            } 
        }
        if(form != null) {
            // form.enctype does not work for IE, but works Safari
            // form.encoding works on both IE and Firefox, but does not work for Safari
            // form.enctype = "multipart/form-data";

            // convert all characters to lowercase to simplify testing
            var agent = navigator.userAgent.toLowerCase();
       
            if( agent.indexOf('safari') != -1) {
                // form.enctype works for Safari
                // form.encoding does not work for Safari
                form.enctype = "multipart/form-data"
            } else {
                // form.encoding works for IE, FireFox
                form.encoding = "multipart/form-data"
            }
        }
        return false;
    }
}

//-->
