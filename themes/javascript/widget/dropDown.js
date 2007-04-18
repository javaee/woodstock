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

dojo.provide("webui.@THEME@.widget.dropDown");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.dropDown.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.dropDown = function() {
    // Set defaults
    this.widgetType = "dropDown";

    // Register widget
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.labelContainer.id = this.id + "_label";
            this.listContainer.id = this.id + "_list"; 
            this.submitterHiddenNode.id = this.id + "_submitter";
        }

        // Set public functions.
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.getSelectedValue = function() { return dojo.widget.byId(this.id).getSelectedValue(); }
        this.domNode.getSelectedLabel = function() { return dojo.widget.byId(this.id).getSelectedLabel(); }
        this.domNode.getSelectElement = function() { return dojo.widget.byId(this.id).getSelectElement(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }

        // Set private functions.
        this.setProps = webui.@THEME@.widget.dropDown.setProps;
        this.getProps = webui.@THEME@.widget.dropDown.getProps;
        this.getSelectClassName = webui.@THEME@.widget.dropDown.getSelectClassName;
        this.setSelectProps = webui.@THEME@.widget.dropDown.setSelectProps;
        this.addOptions = webui.@THEME@.widget.dropDown.addOptions;
        this.getOptionClassName = webui.@THEME@.widget.dropDown.getOptionClassName; 
        this.setOptionProps = webui.@THEME@.widget.dropDown.setOptionProps;
        this.setGroupOptionProps = webui.@THEME@.widget.dropDown.setGroupOptionProps;
        this.dropDownChanged = webui.@THEME@.widget.dropDown.dropDownChanged;
        this.jumpDropDownChanged = webui.@THEME@.widget.dropDown.jumpDropDownChanged;
        this.getSelectedValue = webui.@THEME@.widget.dropDown.getSelectedValue;
        this.getSelectedLabel = webui.@THEME@.widget.dropDown.getSelectedLabel;
        this.initStyleClasses = webui.@THEME@.widget.dropDown.initStyleClasses;
        this.getSelectElement = webui.@THEME@.widget.dropDown.getSelectElement;
        this.refresh = webui.@THEME@.widget.dropDown.refresh.processEvent;

        // Set events.
        dojo.event.connect(this.domNode, "onchange",
            webui.@THEME@.widget.dropDown.createOnChangeCallback(this.id));

        // Set properties.
        this.setProps();
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>id</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>label</li>
 *  <li>labelOnTop</li>
 *  <li>lang</li>
 *  <li>multiple</li>
 *  <li>onBlur</li>
 *  <li>onChange</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onFocus</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onSelect</li>
 *  <li>options</li>
 *  <li>size</li>
 *  <li>submitForm</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.dropDown.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Initialize the proper style classes based on whether this is a jump drop down or not
    this.initStyleClasses(props.submitForm);
        
    // Set core properties on the <span> element. It takes care the following properties:
    // className, id, style, visible
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);

    // Set the common properties, such as, accesskey, dir, lang, tabindex, title, on the <select> element
    webui.@THEME@.widget.common.setCommonProps(this.listContainer, props);

    // Set JavaScript properties, such as, onBlur, onClick, onDblClick, onFocus, onKeyDown, onKeyPress, onKeyup
    // onMouseDown, onMouseOut, onMouseOver, onMouseUp, onMouseMove, onChange, onSelect on the <select> element
    webui.@THEME@.widget.common.setJavaScriptProps(this.listContainer, props);

    // Set the properties specific to the <select> element
    this.setSelectProps(this.listContainer, props);

    // Add <option> and <optgroup> elements in the <select> element
    if (props.options) {
        this.addOptions(props);
    }

    // Add attributes to the hidden input for jump drop down
    if ( props.submitForm != null && props.submitForm == true) {
        this.submitterHiddenNode.name = this.submitterHiddenNode.id;
        this.submitterHiddenNode.value = "false";
    }

    // Set label if there is one
    if (props.label) {
        var labelWidget = dojo.widget.byId(this.label.id);
        
        if (labelWidget) {
            // Update the existing one
            labelWidget.setProps(props.label);
         } else {
            // Create a new one
            webui.@THEME@.widget.common.addFragment(this.labelContainer, props.label);
         }
    }

    // Have a <br/> if the labelOnTop is true
    if (this.label && props.labelOnTop != null) { 
        webui.@THEME@.common.setVisibleElement(this.brNode, props.labelOnTop);
    }
    return true;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.dropDown.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.dropDown.getProps = function() {
    var props = {};

    // Get properties.
    if (this.size) { props.size = this.size; }
    if (this.multiple) { props.multiple = this.multipe; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.submitForm != null) { props.submitForm = this.submitForm; }
    if (this.label ) { props.label = this.label; }
    if (this.labelOnTop != null) { props.labelOnTop = this.labelOnTop; }
    if (this.options ) { props.options = this.options; }

    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getJavaScriptProps(this));

    return props;
}

/**
 * Helper function to set properties specific to the <select> element
 *
 * @param selectNode The <select> DOM node  
 */
webui.@THEME@.widget.dropDown.setSelectProps = function(selectNode, props) {
    selectNode.name = selectNode.id;

    if (props.size) {
        selectNode.size = (props.size < 1) ? 12 : props.size;  
    }
    if (props.multiple != null) {
        selectNode.multiple = new Boolean(props.multiple).valueOf();
    }
    if (props.disabled != null) {
        selectNode.disabled = new Boolean(props.disabled).valueOf();
    }
    if (props.disabled != null) {
        selectNode.className = this.getSelectClassName(props.disabled);
    }
    return true;
}

/**
 * Helper function to add <option> and <optgroup> elements to the <select> element
 */
webui.@THEME@.widget.dropDown.addOptions = function(props) {
    var numChildNodes = this.listContainer.options.length;

    // Start with a clean slate.
    // Note: this has to be done. Otherwise, you'll get blank entries in the drop down for the 
    // original empty dojo attach point nodes.
    if( !this.alreadyRemoved ) {
        this.listContainer.removeChild(this.optionNode); 
        this.optionGroupContainer.removeChild(this.memberOptionNode);
        this.listContainer.removeChild(this.optionGroupContainer);
        this.alreadyRemoved = true;
    }

    // Cleaning up the old options
    this.listContainer.options.length = 0;

    var thisNode;
    for (var i = 0; i < props.options.length; i++) {
        if (props.options[i].group == false) {
            thisNode = this.optionNode.cloneNode(true);

            // Set the properties on this <option> element
            this.setOptionProps(thisNode, props.options[i]);

            // Append this <option> node to the <select>
            this.listContainer.appendChild(thisNode); 

        } else { // group option <optgroup>

            thisNode = this.optionGroupContainer.cloneNode(true);

            // Set the properties on this <optgroup> element
            this.setGroupOptionProps(thisNode, props.options[i]);

            // Append this <optgroup> node to the <select>
            this.listContainer.appendChild(thisNode);

            // Add the <option> elements to this group
            var thisSubNode;
            for (var ix = 0; ix < props.options[i].options.length; ix++) {
                thisSubNode = this.memberOptionNode.cloneNode(true);
                this.setOptionProps(thisSubNode, props.options[i].options[ix]);
                thisNode.appendChild(thisSubNode); 
            }
        }
    }
    return true;
}

/**
 * Helpper function to set properties on the <option> element
 *
 * @param element The <option> DOM node
 * @param option Key-Value pairs of properties for the <option> node
 */
webui.@THEME@.widget.dropDown.setOptionProps = function(element, option) {
    element.value = option.value;
    element.className = this.getOptionClassName(option);

    if (option.isTitle == true) {
       // Prepend and append long dashes with the title label
       element.innerHTML = webui.@THEME@.widget.props.dropDown.titleOptionPreppender 
            + option.label 
            + webui.@THEME@.widget.props.dropDown.titleOptionAppender;
    } else {
       element.innerHTML = option.label;
    }

    if (option.selected != null) {
        element.selected = new Boolean(option.selected).valueOf();
    }
    if (option.disabled != null) {
        element.disabled = new Boolean(option.disabled).valueOf();
    }
    return true;
}

/**
 * Helper function to set properties on <optgroup> element
 *
 * @param element The <optgroup> DOM node
 * @param option Key-Value pairs of properties for the <optgroup> node
 */
webui.@THEME@.widget.dropDown.setGroupOptionProps = function(element, option) {
    element.className = this.getOptionClassName(option);
    element.label = option.label;
  
    if (option.disabled != null) {
        element.disabled = new Boolean(option.disabled).valueOf();
    }
    return true;
}

/**
 * Helper function to initialize the proper style classes based on 
 * whether the drop is a jump drop down or not
 */
webui.@THEME@.widget.dropDown.initStyleClasses = function(submitForm) {
    if (submitForm == null) {
        return;
    }

    if (submitForm == true) {
        this.selectClassName = webui.@THEME@.widget.props.jumpDropDown.className;
        this.selectDisabledClassName = webui.@THEME@.widget.props.jumpDropDown.disabledClassName;
        this.optionClassName = webui.@THEME@.widget.props.jumpDropDown.optionClassName;
        this.optionSeparatorClassName = webui.@THEME@.widget.props.jumpDropDown.optionSeparatorClassName;
        this.optionGroupClassName = webui.@THEME@.widget.props.jumpDropDown.optionGroupClassName;
        this.optionDisabledClassName = webui.@THEME@.widget.props.jumpDropDown.optionDisabledClassName;
        this.optionSelectedClassName = webui.@THEME@.widget.props.jumpDropDown.optionSelectedClassName;
    } else {
        this.selectClassName = webui.@THEME@.widget.props.dropDown.className;
        this.selectDisabledClassName = webui.@THEME@.widget.props.dropDown.disabledClassName;
        this.optionClassName = webui.@THEME@.widget.props.dropDown.optionClassName;
        this.optionSeparatorClassName = webui.@THEME@.widget.props.dropDown.optionSeparatorClassName;
        this.optionGroupClassName = webui.@THEME@.widget.props.dropDown.optionGroupClassName;
        this.optionDisabledClassName = webui.@THEME@.widget.props.dropDown.optionDisabledClassName;
        this.optionSelectedClassName = webui.@THEME@.widget.props.dropDown.optionSelectedClassName;
    }
    return true;
}

/**
 * Helper function to obtain class name for the <select> element
 */
webui.@THEME@.widget.dropDown.getSelectClassName = function(disabled) {
    return (disabled == true)
        ? this.selectDisabledClassName
        : this.selectClassName;
}

/**
 * Helper function to obtain class name for the <option> element
 *
 * @param option Key-Value pairs of properties.
 */
webui.@THEME@.widget.dropDown.getOptionClassName = function(option) {
    if (option.separator && option.separator == true) {
        return this.optionSeparatorClassName;
    } else if (option.group && option.group == true) {
        return this.optionGroupClassName;
    } else if (option.disabled && option.disabled == true) {
        return this.optionDisabledClassName;
    } else if (option.selected && option.selected == true) {
        return this.optionSelectedClassName;
    } else {
        return this.optionClassName;
    }
}

/**
 * Helper function to create callback for onChange event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.dropDown.createOnChangeCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        if (widget.disabled == true) {
            return true;
        }

        // Call the proper changed function
        if (widget.submitForm == true) {
            widget.jumpDropDownChanged();
        } else {
            widget.dropDownChanged();
        }
        return true;
    };
}

/**
 * Helper function called by onchange event to set the proper
 * selected, and disabled styles.
 */
webui.@THEME@.widget.dropDown.dropDownChanged = function() { 
    var options = this.listContainer.options;

    if (webui.@THEME@.common.browser.is_ie) { 
        for (var i = 0;i < options.length;++i) {
            if (options[i].disabled == true && options[i].selected == true) {
                widget.listContainer.selectedIndex = -1;
            }
        }  
    }        

    for (var i=0; i < options.length; ++i) { 
        options[i].className = this.getOptionClassName(options[i]);
    }
    return true; 
}

/**
 * Helper function called by jump drop down onchange event to 
 * set the form action and then submit the form.
 */
webui.@THEME@.widget.dropDown.jumpDropDownChanged = function() {
    var jumpDropdown = this.listContainer; 

    // Find the <form> for this drop down
    var form = jumpDropdown; 
    while (form != null) { 
        form = form.parentNode; 
        if (form.tagName == "FORM") { 
            break;
        }
    }

    if (form != null) { 
        this.submitterHiddenNode.value = "true";

        var options = jumpDropdown.options;
        for (var i=0; i < options.length; ++i) { 
            options[i].className = this.getOptionClassName(options[i]);
        }
        form.submit();
    }
    return true; 
}

/**
 * To get the value of the first selected option on the dropDown. 
 * If no option is selected, this function returns null. 
 *
 * @return The value of the selected option, or null if none is selected. 
 */
webui.@THEME@.widget.dropDown.getSelectedValue = function() { 
    var index = this.listContainer.selectedIndex; 
    if (index == -1) { 
        return null; 
    } else { 
        return this.options[index].value; 
    }
}

/**
 * To get the label of the first selected option on the dropDown. 
 * If no option is selected, this function returns null.
 * 
 * @return The label of the selected option, or null if none is selected. 
 */
webui.@THEME@.widget.dropDown.getSelectedLabel = function() { 
    var index = this.listContainer.selectedIndex; 

    if (index == -1) { 
        return null; 
    } else { 
        return this.options[index].label;
    }
}

/**
 * Returns the HTML select element that makes up the dropDown.
 */
webui.@THEME@.widget.dropDown.getSelectElement = function() {
    return this.listContainer;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.dropDown.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_dropDown_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_dropDown_refresh_end",

    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish event.
        webui.@THEME@.widget.dropDown.refresh.publishBeginEvent({
            id: this.id,
            execute: execute
        });
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishBeginEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.dropDown.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.dropDown.refresh.endEventTopic, props);
        return true;
    }
}

dojo.inherits(webui.@THEME@.widget.dropDown, dojo.widget.HtmlWidget);

//-->
