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
    },

    /**
     * Simulate a mouse click in a button. 
     *
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).click();
     */
    click: function() {
        this.click();
        return true;
    },

    /**
     * Get the textual label of a button. 
     *
     * @return The element value or null
     * @deprecated Use document.getElementById(id).getProps().contents;
     */
    getText: function() {
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.domNode.value;
    },

    /**
     * Set the textual label of a button. 
     *
     * @param text The element value
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({contents: "text"});
     */
    setText: function(text) {
        if (text == null) {
            return false;
        }
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.setProps({contents: text});
    },

    /**
     * Use this function to show or hide a button. 
     *
     * @param show true to show the element, false to hide the element
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({visible: true});
     */
    setVisible: function(show) {
        if (show == null) {
            return false;
        }
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.setProps({visible: show});
    },

    /**
     * Use this function to find whether or not this is visible according to our
     * spec.
     *
     * @return true if visible; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().visible;
     */
    getVisible: function() {
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.getProps().visible;
    },

    /**
     * Test if button is set as "primary".
     *
     * @return true if primary; otherwise, false for secondary
     * @deprecated Use document.getElementById(id).getProps().primary;
     */
    isPrimary: function() {
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.getProps().primary;
    },

    /**
     * Set button as "primary".
     *
     * @param primary true for primary, false for secondary
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({primary: true});
     */
    setPrimary: function(_primary) {
        if (_primary == null) {
            return false;
        }
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.setProps({primary: _primary});
    },

    /**
     * Test if button is set as "secondary".
     *
     * @return true if secondary; otherwise, false for primary
     * @deprecated Use !(document.getElementById(id).getProps().primary);
     */
    isSecondary: function() {
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.secondary;
    },

    /**
     * Set button as "secondary".
     *
     * @param secondary true for secondary, false for primary
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({primary: false});
     */
    setSecondary: function(secondary) {
        if (secondary == null) {
            return false;
        }
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.setProps({primary: !secondary});
    },

    /**
     * Test if button is set as "mini".
     *
     * @return true if mini; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().mini;
     */
    isMini: function() {
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.getProps().mini;
    },

    /**
     * Set button as "mini".
     *
     * @param mini true for mini, false for standard button
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({mini: true});
     */
    setMini: function(_mini) {
        if (_mini == null) {
            return false;
        }
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.setProps({mini: _mini});
    },

    /**
     * Test disabled state of button.
     *
     * @return true if disabled; otherwise, false
     * @deprecated Use document.getElementById(id).getProps().disabled;
     */
    getDisabled: function() {
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.getProps().disabled;
    },

    /**
     * Test disabled state of button.
     *
     * @param disabled true if disabled; otherwise, false
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({disabled: true});
     */
    setDisabled: function(_disabled) {
        if (_disabled == null) {
            return false;
        }
        var widget = dojo.widget.byId(this.id);
        if (widget == null) {
            return false;
        }
        return widget.setProps({disabled: _disabled});
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// checkbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.checkbox name space.
 * 
 * @deprecated
 */
webui.@THEME@.checkbox = {
    /**
     * Set the disabled state for the given checkbox element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param disabled true or false
     * @return true if successful; otherwise, false
     *
     * @deprecated Use document.getElementById(id).setProps({ disabled: boolean });     
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
     * @return true if successful; otherwise, false
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
     * @return true if successful; otherwise, false
     * 
     * @deprecated Use document.getElementById(id).setProps({ checked: boolean });     
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
 * @deprecated
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
        return false;
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
     * @return true if successful; otherwise, false
     *
     * @deprecated Use document.getElementById(elementId).change();
     */
    changed: function(elementId) {         
        var widget = dojo.widget.byId(elementId);
        if (widget) {
            if (widget.submitForm == true) {
                return widget.jumpDropDownChanged();
            } else {
                return widget.dropDownChanged();
            }
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
     * @return true if successful; otherwise, false
     *
     * @deprecated Use document.getElementById(elementId).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({ disabled: disabled});
        }
        return false;
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
        return false;
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
     * * @deprecated Use document.getElementById(elementId).getSelectedLabel();
     */
    getSelectedLabel: function(elementId) { 
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.getSelectedLabel();
        }
        return false;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// field functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.field name space.
 */
webui.@THEME@.field = {
    /**
     * Use this function to get the HTML input or textarea element
     * associated with a TextField, PasswordField, HiddenField or TextArea
     * component. 
     * @param elementId The element ID of the field 
     * @return the input or text area element associated with the field
     * component 
     * @deprecated for client side widget components
     */
    getInputElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element != null) { 
            if(element.tagName == "INPUT") { 
                return element; 
            }
            if(element.tagName == "TEXTAREA") { 
                return element; 
            } 
        } 
        return document.getElementById(elementId + "_field");
    },

    /**
     * Use this function to get the value of the HTML element 
     * corresponding to the Field component
     * @param elementId The element ID of the Field component
     * @return the value of the HTML element corresponding to the 
     * Field component 
     * @deprecated for client side widget components
     */
    getValue: function(elementId) { 
        return webui.@THEME@.field.getInputElement(elementId).value; 
    },

    /**
     * Use this function to set the value of the HTML element 
     * corresponding to the Field component
     * @param elementId The element ID of the Field component
     * @param newStyle The new value to enter into the input element
     * Field component 
     * @deprecated for client side widget components
     */
    setValue: function(elementId, newValue) { 
        webui.@THEME@.field.getInputElement(elementId).value = newValue;
    },

    /** 
     * Use this function to get the style attribute for the field. 
     * The style retrieved will be the style on the span tag that 
     * encloses the (optional) label element and the input element. 
     * @param elementId The element ID of the Field component
     * @deprecated for client side widget components
     */
    getStyle: function(elementId) { 
        return webui.@THEME@.field.getInputElement(elementId).style; 
    },

    /**
     * Use this function to set the style attribute for the field. 
     * The style will be set on the <span> tag that surrounds the field. 
     * @param elementId The element ID of the Field component
     * @param newStyle The new style to apply
     * @deprecated for client side widget components
     */
    setStyle: function(elementId, newStyle) { 
        webui.@THEME@.field.getInputElement(elementId).style = newStyle; 
    },

    /**
     * Use this function to disable or enable a field. As a side effect
     * changes the style used to render the field. 
     *
     * @param elementId The element ID of the field 
     * @param show true to disable the field, false to enable the field
     * @return true if successful; otherwise, false
     * @deprecated for client side widget components
     */
    setDisabled: function(elementId, disabled) {  
        if (elementId == null || disabled == null) {
            // must supply an elementId && state
            return false;
        }
        var textfield = webui.@THEME@.field.getInputElement(elementId); 
        if (textfield == null) {
            return false;
        }
        var newState = new Boolean(disabled).valueOf();    
        var isTextArea = textfield.className.indexOf(
            webui.@THEME@.props.field.textAreaClassName) > -1;
        if (newState) { 
            if(isTextArea) {
                textfield.className = webui.@THEME@.props.field.areaDisabledClassName;
            } else {
                textfield.className = webui.@THEME@.props.field.fieldDisabledClassName;
            }
        } else {
            if(isTextArea) {
                textfield.className = webui.@THEME@.props.field.areaClassName;
            } else {
                textfield.className = webui.@THEME@.props.field.fieldClassName;
            }
        }
        textfield.disabled = newState;
        return true;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// hyperlink functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.hyperlink name space.
 */
webui.@THEME@.hyperlink = {
    /**
     * This function is used to submit a hyperlink.
     *
     * @params hyperlink The hyperlink element
     * @params formId The form id
     * @params params Name value pairs
     */
    submit: function(hyperlink, formId, params) {
        //params are name value pairs but all one big string array
        //so params[0] and params[1] form the name and value of the first param
        var theForm = document.getElementById(formId);
	var oldTarget = theForm.target;
        var oldAction = theForm.action;
        theForm.action += "?" + hyperlink.id + "_submittedField="+hyperlink.id; 
        if (params != null) {
            for (var i = 0; i < params.length; i++) {
             theForm.action +="&" + params[i] + "=" + params[i+1]; 
                i++;
            }
        }
        if (hyperlink.target != null) {
            theForm.target = hyperlink.target;
        }
        theForm.submit();
        // Fix for CR 6469040 - Hyperlink:Does not work correctly in
        // frames environment. 
	if (hyperlink.target != null) {
	    theForm.target = oldTarget;
            theForm.action = oldAction;
        }
        return false;
    },
	
    
   /**
     * Use this function to access the HTML img element that makes up
     * the icon hyperlink. 
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the outter most tag enclosing the HTML img element).
     * @return a reference to the img element. 
     */
    getImgElement: function(elementId) {
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
 * @deprecated
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
     * @return true
     *
     * @deprecated Use document.getElementById(elementId).changed()
     */
    changed: function(elementId) {
        var widget = dojo.widget.byId(elementId);
        if (widget) {
            if (widget.submitForm == true) {
                return widget.jumpDropDownChanged();
            } else {
                return widget.dropDownChanged();
            }
        }
        return false;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// listbox functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.listbox name space.
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
     */
    getSelectElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element != null) { 
            if(element.tagName == "SELECT") { 
                return element; 
            }
        }
        return document.getElementById(elementId + "_list");
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
     * @return true if successful; otherwise, false
     */
    changed: function(elementId) { 
        var cntr = 0; 
        var listItem = webui.@THEME@.listbox.getSelectElement(elementId).options;

        //disabled items should not be selected (IE problem)
        //So setting selectedIndex = -1 for disabled selected items.
    
        if(webui.@THEME@.common.browser.is_ie) {
            for(var i = 0;i < listItem.length;++i) {
               if(listItem[i].disabled == true && 
                            listItem[i].selected == true ) {
                
                  listItem.selectedIndex = -1;
            
               }
            }
        }    
        while(cntr < listItem.length) { 
            if(listItem[cntr].selected) {
                listItem[cntr].className = webui.@THEME@.props.listbox.optionSelectedClassName;
            } else if(listItem[cntr].disabled) {
                listItem[cntr].className = webui.@THEME@.props.listbox.optionDisabledClassName;
            } else {
                // This does not work on Opera 7. There is a bug such that if 
                // you touch the option at all (even if I explicitly set
                // selected to false!), it goes back to the original
                // selection. 
                listItem[cntr].className = webui.@THEME@.props.listbox.optionClassName;
            }
            ++ cntr;
        }
        return true;
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
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {
        var listbox = webui.@THEME@.listbox.getSelectElement(elementId); 
        var regular = webui.@THEME@.props.listbox.className;
        var _disabled = webui.@THEME@.props.listbox.disabledClassName;

        if(listbox.className.indexOf(webui.@THEME@.props.listbox.monospaceClassName) > 1) {
            regular = webui.@THEME@.props.listbox.monospaceClassName;
            _disabled = webui.@THEME@.props.listbox.monospaceDisabledClassName;
        }
        if(disabled) {
            listbox.disabled = true;
            listbox.className = _disabled;
        } else {
            listbox.disabled = false;
            listbox.className = regular;
        }
        return true;
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
     */
    getSelectedValue: function(elementId) { 
        var listbox = webui.@THEME@.listbox.getSelectElement(elementId); 
        var index = listbox.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return listbox.options[index].value; 
        }
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
     */
    getSelectedLabel: function(elementId) {
        var listbox = webui.@THEME@.listbox.getSelectElement(elementId); 
        var index = listbox.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return listbox.options[index].label; 
        }
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Generic checkbox and radio button functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.rbcb name space.
 *
 * @deprecated
 */
webui.@THEME@.rbcb = {
    /**
     * @deprecated Use document.getElementById(id).setProps({ checked: boolean }); 
     */ 
    setChecked: function(elementId, _checked, type) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({ checked: _checked });
        }
        return false; 
    },

    /**
     * @deprecated Use document.getElementById(id).setProps({ disabled: boolean }); 
     */ 
    setDisabled: function(elementId, _disabled, type, enabledStyle,
            disabledStyle) {
        var domNode = document.getElementById(elementId);
        if (domNode) {
            return domNode.setProps({ disabled: _disabled });
        }
        return false; 
    },

    /** 
     * Set the disabled state for all radio buttons with the given controlName.
     * If disabled is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param formName The name of the form containing the element
     * @param disabled true or false
     * @return true if successful; otherwise, false     
     */
    setGroupDisabled: function(controlName, disabled, type, enabledStyle,
            disabledStyle) {
        // Validate params.
        if (controlName == null) {
            return false;
        }
        if (disabled == null) {
            return false;
        }
        if (type == null) {
            return false;
        }

        // Get radiobutton group elements.
        var x = document.getElementsByName(controlName)
 
        // Set disabled state.
        for (var i = 0; i < x.length; i++) {
            // Get element.
            var element = x[i];
            if (element == null || element.name != controlName) {
                continue;
            }
            // Validate element type.
            if (element.type.toLowerCase() != type) {
                return false;
            }
            // Set disabled state.
            element.disabled = new Boolean(disabled).valueOf();

            // Set class attribute.
            if (element.disabled) {
                if (disabledStyle != null) {
                    element.className = disabledStyle;
                }
            } else {
                if (enabledStyle != null) {
                    element.className = enabledStyle;
                }
            }
        }
        return true;
    }
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// radiobutton functions
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

/**
 * Define webui.@THEME@.radiobutton name space.
 */
webui.@THEME@.radiobutton = {
    /**
     * Set the disabled state for the given radiobutton element Id. If the disabled 
     * state is set to true, the element is shown with disabled styles.
     *
     * @param elementId The element Id
     * @param disabled true or false
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({ disabled: boolean });  
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
     * @return true if successful; otherwise, false
     */
    setGroupDisabled: function(controlName, disabled) {    
        return webui.@THEME@.rbcb.setGroupDisabled(controlName, 
            disabled, "radio", "Rb", "RbDis");
    },

    /**
     * Set the checked property for a radio button with the given element Id.
     *
     * @param elementId The element Id
     * @param checked true or false
     * @return true if successful; otherwise, false
     * @deprecated Use document.getElementById(id).setProps({ checked: boolean });  
     */
    setChecked: function(elementId, checked) {
        return webui.@THEME@.rbcb.setChecked(elementId, checked,
            "radio");
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
     * @return true if successful; otherwise, false
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
