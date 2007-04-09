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
 */
webui.@THEME@.button = {
    /**
     * This function is used to initialize HTML element properties with the
     * following Object literals.
     *
     * <ul>
     *  <li>disabled</li>
     *  <li>icon</li>
     *  <li>id</li>
     *  <li>mini</li>
     *  <li>secondary</li>
     * </ul>
     *
     * Note: This is considered a private API, do not use.
     *
     * @param props Key-Value pairs of properties.
     */
    init: function(props) {
        if (props == null || props.id == null) {
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
                return false;
        }

        // Save given properties with the DOM node for later updates.
        Object.extend(domNode, {
            icon: new Boolean(props.icon).valueOf(),
            id: props.id,
            isOneOfOurButtons: true,
            mini: new Boolean(props.mini).valueOf(),
            mydisabled: new Boolean(props.disabled).valueOf(),
            secondary: new Boolean(props.secondary).valueOf()
        });

        // Set style classes
        if (domNode.icon == true) {
            domNode.classNamePrimary = webui.@THEME@.props.button.imageClassName;
            domNode.classNamePrimaryDisabled = webui.@THEME@.props.button.imageDisabledClassName;
            domNode.classNamePrimaryHov = webui.@THEME@.props.button.imageHovClassName;

            // Currently not used in theme.
            domNode.classNamePrimaryMini = "";
            domNode.classNamePrimaryMiniDisabled = "";
            domNode.classNamePrimaryMiniHov = "";
            domNode.classNameSecondary = "";
            domNode.classNameSecondaryDisabled = "";
            domNode.classNameSecondaryHov = "";
            domNode.classNameSecondaryMini = "";
            domNode.classNameSecondaryMiniDisabled = "";
            domNode.classNameSecondaryMiniHov = "";
        } else {
            domNode.classNamePrimary = webui.@THEME@.props.button.primaryClassName;
            domNode.classNamePrimaryDisabled = webui.@THEME@.props.button.primaryDisabledClassName;
            domNode.classNamePrimaryHov = webui.@THEME@.props.button.primaryHovClassName;
            domNode.classNamePrimaryMini = webui.@THEME@.props.button.primaryMiniClassName;
            domNode.classNamePrimaryMiniDisabled = webui.@THEME@.props.button.primaryMiniDisabledClassName;
            domNode.classNamePrimaryMiniHov = webui.@THEME@.props.button.primaryMiniHovClassName;
            domNode.classNameSecondary = webui.@THEME@.props.button.secondaryClassName;
            domNode.classNameSecondaryDisabled = webui.@THEME@.props.button.secondaryDisabledClassName;
            domNode.classNameSecondaryHov = webui.@THEME@.props.button.secondaryHovClassName;
            domNode.classNameSecondaryMini = webui.@THEME@.props.button.secondaryMiniClassName;
            domNode.classNameSecondaryMiniDisabled = webui.@THEME@.props.button.secondaryMiniDisabledClassName;
            domNode.classNameSecondaryMiniHov = webui.@THEME@.props.button.secondaryMiniHovClassName;
        }

        // Set functions
        domNode.isSecondary = webui.@THEME@.button.isSecondary;
        domNode.setSecondary = webui.@THEME@.button.setSecondary;
        domNode.isPrimary = webui.@THEME@.button.isPrimary;
        domNode.setPrimary = webui.@THEME@.button.setPrimary;
        domNode.isMini = webui.@THEME@.button.isMini;
        domNode.setMini = webui.@THEME@.button.setMini;
        domNode.getDisabled = webui.@THEME@.button.getDisabled;
        domNode.setDisabled = webui.@THEME@.button.setDisabled;
        domNode.getVisible = webui.@THEME@.button.getVisible;
        domNode.setVisible = webui.@THEME@.button.setVisible;
        domNode.getText = webui.@THEME@.button.getText;
        domNode.setText = webui.@THEME@.button.setText;
        domNode.doClick = webui.@THEME@.button.click;
        domNode.myonblur = webui.@THEME@.button.onblur;
        domNode.myonfocus = webui.@THEME@.button.onfocus;
        domNode.myonmouseover = webui.@THEME@.button.onmouseover;
        domNode.myonmouseout = webui.@THEME@.button.onmouseout;

        // Set button state.
        domNode.setDisabled(domNode.mydisabled);
        domNode.setSecondary(domNode.secondary);
        domNode.setMini(domNode.mini);
    },

    /**
     * Simulate a mouse click in a button. 
     *
     * @return true if successful; otherwise, false
     */
    click: function() {
        this.click()
        return true;
    },

    /**
     * Get the textual label of a button. 
     *
     * @return The element value or null
     */
    getText: function() {
        return this.value;
    },

    /**
     * Set the textual label of a button. 
     *
     * @param text The element value
     * @return true if successful; otherwise, false
     */
    setText: function(text) {
        if (text == null) {
            return false;
        }

        this.value = text;
        return true;
    },

    /**
     * Use this function to show or hide a button. 
     *
     * @param show true to show the element, false to hide the element
     * @return true if successful; otherwise, false
     */
    setVisible: function(show) {
        if (show == null) {
            return false;
        }
        // Get element.
        webui.@THEME@.common.setVisibleElement(this, show);

        return true;
    },

    /**
     * Use this function to find whether or not this is visible according to our
     * spec.
     *
     * @return true if visible; otherwise, false
     */
    getVisible: function() {
         // Get element.
        styles = webui.@THEME@.common.splitStyleClasses(this);
        if (styles == null) {
            return true;
        }
        return !webui.@THEME@.common.checkStyleClasses(styles,
            webui.@THEME@.props.hiddenClassName);
    },

    /**
     * Test if button is set as "primary".
     *
     * @return true if primary; otherwise, false for secondary
     */
    isPrimary: function() {
        return !this.isSecondary();
    },

    /**
     * Set button as "primary".
     *
     * @param primary true for primary, false for secondary
     * @return true if successful; otherwise, false
     */
    setPrimary: function(primary) {
        return this.setSecondary(!primary);
    },

    /**
     * Test if button is set as "secondary".
     *
     * @return true if secondary; otherwise, false for primary
     */
    isSecondary: function() {
        return this.secondary;
    },

    /**
     * Set button as "secondary".
     *
     * @param secondary true for secondary, false for primary
     * @return true if successful; otherwise, false
     */
    setSecondary: function(secondary) {
        if (secondary == null || this.mydisabled) {
            return false;
        }
        var stripType;
        var stripTypeHov;
        var newType;

        if (this.secondary == false && secondary == true) {
            //change from primary to secondary
            if (this.mini) {
                stripTypeHov = this.classNamePrimaryMiniHov;
                stripType = this.classNamePrimaryMini;
                newType = this.classNameSecondaryMini;
            } else {
                stripTypeHov = this.classNamePrimaryHov;
                stripType = this.classNamePrimary;
                hovType = this.classNameSecondaryHov;
                newType = this.classNameSecondary;
            }
        } else if (this.secondary == true && secondary == false) {
            //change from secondary to primary
            if (this.mini) {
                //this is currently a mini button
                stripTypeHov = this.classNameSecondaryMiniHov;
                stripType = this.classNameSecondaryMini;
                newType = this.classNamePrimaryMini;
            } else {
                stripTypeHov = this.classNameSecondaryHov;
                stripType = this.classNameSecondary;
                newType = this.classNamePrimary;
            }
        } else {
            // don't need to do anything
            return false;
        }
        webui.@THEME@.common.stripStyleClass(this, stripTypeHov);
        webui.@THEME@.common.stripStyleClass(this, stripType);
        webui.@THEME@.common.addStyleClass(this, newType);
        this.secondary=secondary;
        return this.secondary;
    },

    /**
     * Test if button is set as "mini".
     *
     * @return true if mini; otherwise, false
     */
    isMini: function() {
        return this.mini;
    },

    /**
     * Set button as "mini".
     *
     * @param mini true for mini, false for standard button
     * @return true if successful; otherwise, false
     */
    setMini: function(mini) {
        if (mini == null || this.mydisabled) {
            return false;
        }
        var stripType;
        var stripTypeHov;
        var newType;
        if (this.mini == true && mini == false) {
            //change from mini to nonmini
            if (!this.secondary) {
                //this is currently a primary button
                stripTypeHov = this.classNamePrimaryMiniHov;
                stripType = this.classNamePrimaryMini;
                newType = this.classNamePrimary;
            } else {
                stripTypeHov = this.classNameSecondaryMiniHov;
                stripType = this.classNameSecondaryMini;
                newType = this.classNameSecondary;
            }
        } else if (this.mini == false && mini == true) {
            if (!this.secondary) {
                //this is currently a primary button
                stripTypeHov = this.classNamePrimaryHov;
                stripType = this.classNamePrimary;
                newType = this.classNamePrimaryMini;
            } else {
                stripTypeHov = this.classNameSecondaryHov;
                stripType = this.classNameSecondary;
                newType = this.classNameSecondaryMini;
            }
        } else {
            // don't need to do anything
            return false;
        }
        webui.@THEME@.common.stripStyleClass(this, stripTypeHov);
        webui.@THEME@.common.stripStyleClass(this, stripType);
        webui.@THEME@.common.addStyleClass(this, newType);
        this.mini = mini;
        return this.mini;
    },

    /**
     * Test disabled state of button.
     *
     * @return true if disabled; otherwise, false
     */
    getDisabled: function() {
        return this.mydisabled;
    },

    /**
     * Test disabled state of button.
     *
     * @param disabled true if disabled; otherwise, false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(disabled) {
        if (disabled == null || this.mydisabled == disabled) {
            return false;
        }
        var stripType;
        var stripHovType;
        var newType;
        if (!this.secondary) {
            //this is currently a primary button
            if (this.mini) {
                if (disabled == false) {
                    stripType = this.classNamePrimaryMiniDisabled;
                    stripHovType = this.classNamePrimaryMiniDisabled;
                    newType = this.classNamePrimaryMini;
                } else {
                    stripType = this.classNamePrimaryMini;
                    stripHovType = this.classNamePrimaryMiniHov;
                    newType = this.classNamePrimaryMiniDisabled;
                }
            } else { // not mini
                if (disabled == false) {
                    stripType = this.classNamePrimaryDisabled;
                    stripHovType = this.classNamePrimaryDisabled;
                    newType = this.classNamePrimary;
                } else {
                    stripType = this.classNamePrimary;
                    stripHovType = this.classNamePrimaryHov;
                    newType = this.classNamePrimaryDisabled;
                }
            }
        } else {
            //this is currently a secondary button
            if (this.mini) {
                if (disabled == false) {
                    stripType = this.classNameSecondaryMiniDisabled;
                    stripHovType = this.classNameSecondaryMiniDisabled;
                    newType = this.classNameSecondaryMini;
                } else {
                    stripType = this.classNameSecondaryMini;
                    stripHovType = this.classNameSecondaryMiniHov;
                    newType = this.classNameSecondaryMiniDisabled;
                }
            } else { // not mini
                if (disabled == false) {
                    stripType = this.classNameSecondaryDisabled;
                    stripHovType = this.classNameSecondaryDisabled;
                    newType = this.classNameSecondary;
                } else {
                    stripType = this.classNameSecondary;
                    stripHovType = this.classNameSecondaryHov;
                    newType = this.classNameSecondaryDisabled;
                }
            }
        }
        webui.@THEME@.common.stripStyleClass(this, stripHovType);
        webui.@THEME@.common.stripStyleClass(this, stripType);
        webui.@THEME@.common.addStyleClass(this, newType);
        this.mydisabled = disabled;
        this.disabled = disabled;
        return true;
    },

    /**
     * Set CSS styles for onblur event.
     *
     * @return true if successful; otherwise, false
     */
    onblur: function() {
        if (this.mydisabled == true) {
            return true;
        }
        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMiniHov;
                newType = this.classNamePrimaryMini;        
            } else {
                stripType = this.classNamePrimaryHov;
                newType = this.classNamePrimary;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMiniHov;
                newType = this.classNameSecondaryMini;        
            } else {
                stripType = this.classNameSecondaryHov;
                newType = this.classNameSecondary;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.@THEME@.common.stripStyleClass(this, stripType);
            webui.@THEME@.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
    },

    /**
     * Set CSS styles for onmouseout event.
     *
     * @return true if successful; otherwise, false
     */
    onmouseout: function() {
        if (this.mydisabled == true) {
            return true;
        }

        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMiniHov;
                newType = this.classNamePrimaryMini;        
            } else {
                stripType = this.classNamePrimaryHov;
                newType = this.classNamePrimary;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMiniHov;
                newType = this.classNameSecondaryMini;        
            } else {
                stripType = this.classNameSecondaryHov;
                newType = this.classNameSecondary;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.@THEME@.common.stripStyleClass(this, stripType);
            webui.@THEME@.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
    },

    /**
     * Set CSS styles for onfocus event.
     *
     * @return true if successful; otherwise, false
     */
    onfocus: function() {
        if (this.mydisabled == true) {
            return true;
        }
        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMini;
                newType = this.classNamePrimaryMiniHov;        
            } else {
                stripType = this.classNamePrimary;
                newType = this.classNamePrimaryHov;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMini;
                newType = this.classNameSecondaryMiniHov;        
            } else {
                stripType = this.classNameSecondary;
                newType = this.classNameSecondaryHov;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.@THEME@.common.stripStyleClass(this, stripType);
            webui.@THEME@.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
    },

    /**
     * Set CSS styles for onmouseover event.
     *
     * @return true if successful; otherwise, false
     */
    onmouseover: function() {
        if (this.mydisabled == true) {
            return false;
        }
        var stripType;
        var newType;
        if (!this.secondary) {
            if (this.mini) {
                stripType = this.classNamePrimaryMini;
                newType = this.classNamePrimaryMiniHov;        
            } else {
                stripType = this.classNamePrimary;
                newType = this.classNamePrimaryHov;        
            }
        } else { //is secondary 
            if (this.mini) {
                stripType = this.classNameSecondaryMini;
                newType = this.classNameSecondaryMiniHov;        
            } else {
                stripType = this.classNameSecondary;
                newType = this.classNameSecondaryHov;        
            }
        }
        // This code can generate a JavaScript error if the cursor quickly moves
        // off the button while the page is being refreshed.
        try {
            webui.@THEME@.common.stripStyleClass(this, stripType);
            webui.@THEME@.common.addStyleClass(this, newType);
        } catch (e) {}
        return true;
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
     */
    changed: function(elementId) {         
        var listItem = webui.@THEME@.dropDown.getSelectElement(elementId).options;

        //disabled items should not be selected (IE problem)
        //So setting selectedIndex = -1 for disabled items.
        
        if (webui.@THEME@.common.browser.is_ie) { 
          for(var i = 0;i < listItem.length;++i) {
              if(listItem[i].disabled == true &&
                           listItem[i].selected == true) {

               listItem.selectedIndex = -1;
              }
          }  
        }        
  
        for (var cntr=0; cntr < listItem.length; ++cntr) { 
            if (listItem[cntr].className == webui.@THEME@.props.dropDown.optionSeparatorClassName
                    || listItem[cntr].className == webui.@THEME@.props.dropDown.optionGroupClassName) {
                continue;	
            } else if (listItem[cntr].disabled) {
                // Regardless if the option is currently selected or not,
                // the disabled option style should be used when the option
                // is disabled. So, check for the disabled item first.
                // See CR 6317842.
                listItem[cntr].className = webui.@THEME@.props.dropDown.optionDisabledClassName;
            } else if (listItem[cntr].selected) {
                listItem[cntr].className = webui.@THEME@.props.dropDown.optionSelectedClassName;
            } else {
                // This does not work on Opera 7. There is a bug such that if 
                // you touch the option at all (even if I explicitly set
                // selected to false!), it goes back to the original
                // selection. 
                listItem[cntr].className = webui.@THEME@.props.dropDown.optionClassName;
            }
        }
        return true;
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
     */
    setDisabled: function(elementId, disabled) { 
        var choice = webui.@THEME@.dropDown.getSelectElement(elementId); 
        if(disabled) {
            choice.disabled = true;
            choice.className = webui.@THEME@.props.dropDown.disabledClassName;
        } else { 
            choice.disabled = false;
            choice.className = webui.@THEME@.props.dropDown.className;
        }
        return true;
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
     */
    getSelectedValue: function(elementId) { 
        var dropDown = webui.@THEME@.dropDown.getSelectElement(elementId); 
        var index = dropDown.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return dropDown.options[index].value; 
        }
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
     */
    getSelectedLabel: function(elementId) { 
        var dropDown = webui.@THEME@.dropDown.getSelectElement(elementId); 
        var index = dropDown.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return dropDown.options[index].label; 
        }
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
     */
    setValue: function(elementId, newValue) { 
        webui.@THEME@.field.getInputElement(elementId).value = newValue;
    },

    /** 
     * Use this function to get the style attribute for the field. 
     * The style retrieved will be the style on the span tag that 
     * encloses the (optional) label element and the input element. 
     * @param elementId The element ID of the Field component
     */
    getStyle: function(elementId) { 
        return webui.@THEME@.field.getInputElement(elementId).style; 
    },

    /**
     * Use this function to set the style attribute for the field. 
     * The style will be set on the <span> tag that surrounds the field. 
     * @param elementId The element ID of the Field component
     * @param newStyle The new style to apply
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
     */
    changed: function(elementId) {
        var jumpDropdown = webui.@THEME@.dropDown.getSelectElement(elementId); 
        var form = jumpDropdown; 
        while(form != null) { 
            form = form.parentNode; 
            if(form.tagName == "FORM") { 
                break;
            }
        }
        if(form != null) { 
            var submitterFieldId = elementId + "_submitter"; 
            document.getElementById(submitterFieldId).value = "true"; 

            var listItem = jumpDropdown.options;
            for (var cntr=0; cntr < listItem.length; ++cntr) { 
                if (listItem[cntr].className ==
                            webui.@THEME@.props.jumpDropDown.optionSeparatorClassName
                        || listItem[cntr].className == 
                            webui.@THEME@.props.jumpDropDown.optionGroupClassName) {
                    continue;		
                } else if (listItem[cntr].disabled) {
                    // Regardless if the option is currently selected or not,
                    // the disabled option style should be used when the option
                    // is disabled. So, check for the disabled item first.
                    // See CR 6317842.
                    listItem[cntr].className = webui.@THEME@.props.jumpDropDown.optionDisabledClassName;
                } else if (listItem[cntr].selected) {
                    listItem[cntr].className = webui.@THEME@.props.jumpDropDown.optionSelectedClassName;
                } else { 
                    listItem[cntr].className = webui.@THEME@.props.jumpDropDown.optionClassName;
                }
            }
            form.submit();
        }
        return true; 
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
        if(element.tagName == "INPUT") { 
            return element; 
        } 
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
