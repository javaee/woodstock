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

dojo.provide("webui.@THEME@.widget.listbox");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.listbox.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.listbox = function() {
    // Set defaults
    this.labelOnTop = false;
    this.monospace = false;
    this.widgetType = "listbox";

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
        }

        // Set public functions.
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.getSelectedValue = function() { return dojo.widget.byId(this.id).getSelectedValue(); }
        this.domNode.getSelectedLabel = function() { return dojo.widget.byId(this.id).getSelectedLabel(); }
        this.domNode.getSelectElement = function() { return dojo.widget.byId(this.id).getSelectElement(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }

        // Set private functions.
        this.setProps = webui.@THEME@.widget.listbox.setProps;
        this.getProps = webui.@THEME@.widget.listbox.getProps;
        this.getSelectClassName = webui.@THEME@.widget.listbox.getSelectClassName;
        this.setSelectProps = webui.@THEME@.widget.listbox.setSelectProps;
        this.addOptions = webui.@THEME@.widget.listbox.addOptions;
        this.getOptionClassName = webui.@THEME@.widget.listbox.getOptionClassName; 
        this.setOptionProps = webui.@THEME@.widget.listbox.setOptionProps;
        this.setGroupOptionProps = webui.@THEME@.widget.listbox.setGroupOptionProps;
        this.listboxChanged = webui.@THEME@.widget.listbox.listboxChanged;
        this.getSelectedValue = webui.@THEME@.widget.listbox.getSelectedValue;
        this.getSelectedLabel = webui.@THEME@.widget.listbox.getSelectedLabel;
        this.initStyleClasses = webui.@THEME@.widget.listbox.initStyleClasses;
        this.getSelectElement = webui.@THEME@.widget.listbox.getSelectElement;
        this.refresh = webui.@THEME@.widget.listbox.refresh.processEvent;

        // Set events.
        dojo.event.connect(this.listContainer, "onchange",
            webui.@THEME@.widget.listbox.createOnChangeCallback(this.id));

        // Remove line break -- required for IE & cannot be updated.
        if (this.label != null
                && new Boolean(this.labelOnTop).valueOf() == true) {
            webui.@THEME@.common.setVisibleElement(this.brContainer, true);
        }

        // Set properties.
        return this.setProps();
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
 *  <li>lang</li>
 *  <li>multiple</li>
 *  <li>monospace</li>
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
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.listbox.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // A web app devleoper could return false in order to cancel the 
    // auto-submit. Thus, we will handle this event via the onChange call back.
    if (props.onChange) {
        // Set private function scope on DOM node.
        this.listContainer._onchange = (typeof props.onChange == 'string')
            ? new Function(props.onChange) : props.onChange;

        // Must be cleared before calling setJavaScriptProps() below.
        props.onChange = null;
    }

    // Initialize the proper style classes
    this.initStyleClasses();

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
    webui.@THEME@.widget.common.setCommonProps(this.listContainer, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.listContainer, props);

    // Set the properties specific to the <select> element
    this.setSelectProps(this.listContainer, props);

    // Add <option> and <optgroup> elements in the <select> element
    if (props.options) {
        this.addOptions(props);
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
    return true;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.listbox.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.listbox.getProps = function() {
    var props = {};

    // Get properties.
    if (this.size) { props.size = this.size; }
    if (this.multiple) { props.multiple = this.multiple; }
    if (this.monospace) { props.monospace = this.monospace; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.label ) { props.label = this.label; }
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
webui.@THEME@.widget.listbox.setSelectProps = function(selectNode, props) {
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
        selectNode.className = this.getSelectClassName(props.disabled, props.monospace);
    }
    return true;
}

/**
 * Helper function to add <option> and <optgroup> elements to the <select> element
 */
webui.@THEME@.widget.listbox.addOptions = function(props) {
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
webui.@THEME@.widget.listbox.setOptionProps = function(element, option) {
    element.value = option.value;
    element.className = this.getOptionClassName(option);

    if (option.isTitle == true) {
       // Prepend and append long dashes with the title label
       element.innerHTML = webui.@THEME@.widget.props.listbox.titleOptionPreppender 
            + option.label 
            + webui.@THEME@.widget.props.listbox.titleOptionAppender;
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
webui.@THEME@.widget.listbox.setGroupOptionProps = function(element, option) {
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
webui.@THEME@.widget.listbox.initStyleClasses = function() {
    this.selectClassName = webui.@THEME@.widget.props.listbox.className;
    this.selectDisabledClassName = webui.@THEME@.widget.props.listbox.disabledClassName;
    this.selectMonospaceClassName = webui.@THEME@.widget.props.listbox.monospaceClassName;
    this.selectMonospaceDisabledClassName = webui.@THEME@.widget.props.listbox.monospaceDisabledClassName;
    this.optionClassName = webui.@THEME@.widget.props.listbox.optionClassName;
    this.optionSeparatorClassName = webui.@THEME@.widget.props.listbox.optionSeparatorClassName;
    this.optionGroupClassName = webui.@THEME@.widget.props.listbox.optionGroupClassName;
    this.optionDisabledClassName = webui.@THEME@.widget.props.listbox.optionDisabledClassName;
    this.optionSelectedClassName = webui.@THEME@.widget.props.listbox.optionSelectedClassName;

    return true;
}

/**
 * Helper function to obtain class name for the <select> element
 */
webui.@THEME@.widget.listbox.getSelectClassName = function(disabled, monospace) {
    if (monospace == true) {
        return (disabled == true)
            ? this.selectMonospaceDisabledClassName
            : this.selectMonospaceClassName;
    }
    return (disabled == true)
        ? this.selectDisabledClassName
        : this.selectClassName;
}

/**
 * Helper function to obtain class name for the <option> element
 *
 * @param option Key-Value pairs of properties.
 */
webui.@THEME@.widget.listbox.getOptionClassName = function(option) {
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
webui.@THEME@.widget.listbox.createOnChangeCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) { 
        var widget = dojo.widget.byId(id);
        if (widget == null || widget.disabled == true) {
            return false;
        }

        // If function returns false, we must prevent the auto-submit.
        var result = (widget.listContainer._onchange)
            ? widget.listContainer._onchange(event) : true;
        if (result == false) {
            return false;
        }

        // Call the proper changed function
        widget.listboxChanged();

        return true;
    };
}

/**
 * Helper function called by onchange event to set the proper
 * selected, and disabled styles.
 */
webui.@THEME@.widget.listbox.listboxChanged = function() { 
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
 * To get the value of the first selected option on the listbox. 
 * If no option is selected, this function returns null. 
 *
 * @return The value of the selected option, or null if none is selected. 
 */
webui.@THEME@.widget.listbox.getSelectedValue = function() { 
    var index = this.listContainer.selectedIndex; 
    if (index == -1) { 
        return null; 
    } else { 
        return this.listContainer.options[index].value; 
    }
}

/**
 * To get the label of the first selected option on the listbox. 
 * If no option is selected, this function returns null.
 * 
 * @return The label of the selected option, or null if none is selected. 
 */
webui.@THEME@.widget.listbox.getSelectedLabel = function() { 
    var index = this.listContainer.selectedIndex; 

    if (index == -1) { 
        return null; 
    } else { 
        return this.options[index].label;
    }
}

/**
 * Returns the HTML select element that makes up the listbox.
 */
webui.@THEME@.widget.listbox.getSelectElement = function() {
    return this.listContainer;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.listbox.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_listbox_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_listbox_refresh_end",

    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish event.
        webui.@THEME@.widget.listbox.refresh.publishBeginEvent({
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
        dojo.event.topic.publish(webui.@THEME@.widget.listbox.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.listbox.refresh.endEventTopic, props);
        return true;
    }
}

dojo.inherits(webui.@THEME@.widget.listbox, dojo.widget.HtmlWidget);

//-->
