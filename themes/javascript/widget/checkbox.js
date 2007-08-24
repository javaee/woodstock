//
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

dojo.provide("webui.@THEME@.widget.checkbox");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.checkbox = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to create callback for onClick event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.checkbox.createOnClickCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) { 
        var widget = dojo.widget.byId(id);
        if (widget == null || widget.readOnly == true) {
            event.preventDefault();
            return false;
        }

        // If function returns false, we must prevent the request.
        var result = (widget.domNode._onclick)
            ? widget.domNode._onclick(event) : true;
        if (result == false) {
            event.preventDefault();
            return false;
        }
    };
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.checkbox.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_checkbox_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_checkbox_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_checkbox_event_state_begin",
        endTopic: "webui_@THEME@_widget_checkbox_event_state_end"
    },

    /**
     * This closure is used to process submit events.
     */
    submit: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_checkbox_event_submit_begin",
        endTopic: "webui_@THEME@_widget_checkbox_event_submit_end"
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.checkbox.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.checkbox.superclass.fillInTemplate.call(this, props, frag);

    // Set ids.
    if (this.id) {
        this.inputNode.id = this.id + this.idSuffix;
        this.imageContainer.id = this.id + "_imageContainer";
        this.labelContainer.id = this.id + "_labelContainer";

        // If null, use HTML input id.
        if (this.name == null) {
            this.name = this.inputNode.id;
        }
    }

    // Set public functions.        
    this.domNode.getInputElement = function() { return dojo.widget.byId(this.id).getInputElement(); }
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); }
    
    // Create callback function for onclick event.
    dojo.event.connect(this.domNode, "onclick",
        webui.@THEME@.widget.checkbox.createOnClickCallback(this.id));

    return true;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.checkbox.getClassName = function() {
    // Set default style.
    var className = (this.disabled == true)
        ? webui.@THEME@.widget.props.checkbox.disabledClassName
        : webui.@THEME@.widget.props.checkbox.className; 

    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * Helper function to obtain image class names.
 */
webui.@THEME@.widget.checkbox.getImageClassName = function() {
    return (this.disabled == true)
        ? webui.@THEME@.widget.props.checkbox.imageDisabledClassName
        : webui.@THEME@.widget.props.checkbox.imageClassName;  
}

/**
 * Helper function to obtain input class names.
 */
webui.@THEME@.widget.checkbox.getInputClassName = function() {
    // readOnly style.
    if (this.readOnly == true)
        return this.theme.getClassName("CHECKBOX_READONLY");        
    
    // disabled style.
    return (this.disabled == true)
        ? this.theme.getClassName("CHECKBOX_DISABLED")
        : this.theme.getClassName("CHECKBOX");  
}

/**
 * Returns the HTML input element that makes up the chekcbox.
 *
 * @return a reference to the HTML input element. 
 */
webui.@THEME@.widget.checkbox.getInputElement = function() {
    return this.inputNode;
}

/**
 * Helper function to obtain label class names.
 */
webui.@THEME@.widget.checkbox.getLabelClassName = function() {
    return (this.disabled == true)
        ? webui.@THEME@.widget.props.checkbox.labelDisabledClassName
        : webui.@THEME@.widget.props.checkbox.labelClassName;  
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.checkbox.getProps = function() {
    var props = webui.@THEME@.widget.checkbox.superclass.getProps.call(this);

    // Set properties.  
    if (this.align) { props.align = this.align; }
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.image) { props.image = this.image; }
    if (this.label) { props.label = this.label; }
    if (this.name) { props.name = this.name; }        
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.value) { props.value = this.value; }

    // After widget has been initialized, get user's input.
    if (this.isInitialized() == true && this.inputNode.checked != null) {
        props.checked = this.inputNode.checked;
    } else if (this.checked != null) {
        props.checked = this.checked;
    }
    return props;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>align</li>
 *  <li>checked</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>image</li>
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>name</li>
 *  <li>onFocus</li>
 *  <li>onBlur</li>
 *  <li>onChange</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>onSelect</li>
 *  <li>readOnly</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>value</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.checkbox._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.value) { 
        this.inputNode.value = props.value;
    }
    if (props.readOnly != null) { 
        this.inputNode.readOnly = new Boolean(props.readOnly).valueOf();       
    }
    if (props.disabled != null) {
        this.inputNode.disabled = new Boolean(props.disabled).valueOf();        
    }
    
    // Set HTML input element class name.
    this.inputNode.className = this.getInputClassName();
    
    if (props.name) { 
        this.inputNode.name = props.name;
    }
    
    // A web app devleoper could return false in order to cancel the 
    // submit. Thus, we will handle this event via the onClick call back.
    if (props.onClick) {
        // Set private function scope on DOM node.
        this.domNode._onclick = (typeof props.onClick == 'string')
            ? new Function("event", props.onClick) : props.onClick;

        // Must be cleared before calling setEventProps() below.
        props.onClick = null;
    }
    	
    if (props.checked != null) {
        var checked = new Boolean(props.checked).valueOf();

        // Dynamically setting the checked attribute on IE 6 does not work until
        // the HTML input element has been added to the DOM. As a work around, 
        // we shall use a timeout to set the property during initialization.
        if (this.isInitialized() != true
                && webui.@THEME@.common.browser.is_ie6 == true) {
            var id = this.id;
            setTimeout(
                function() {
                    // New literals are created every time this function
                    // is called, and it's saved by closure magic.
                    var widget = dojo.widget.byId(id);
                    widget.inputNode.checked = checked;
                }, 0); // (n) milliseconds delay.
        } else {
            this.inputNode.checked = checked;
        }
    }

    // Set image properties.
    if (props.image || props.disabled != null && this.image) {     
        // Ensure property exists so we can call setProps just once.
        if (props.image == null) {
            props.image = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.image.id = this.image.id; // Required for updateWidget.
        props.image.className = this.getImageClassName();

        // Update/add fragment.
        this.widget.updateFragment(this.imageContainer, props.image);
    } 

    // Set label properties.
    if (props.label || props.disabled != null && this.label) {     
        // Ensure property exists so we can call setProps just once.
        if (props.label == null) {
            props.label = {}; // Avoid updating all props using "this" keyword.
        }

        // Set properties.
        props.label.id = this.label.id; // Required for updateFragment().
        props.label.className = this.getLabelClassName();

        // Update/add fragment.
        this.widget.updateFragment(this.labelContainer, props.label);
    }

    // Set more properties.
    this.setCommonProps(this.inputNode, props);
    this.setEventProps(this.inputNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.checkbox.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.checkbox, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.checkbox, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.checkbox.fillInTemplate,
    getClassName: webui.@THEME@.widget.checkbox.getClassName,
    getInputClassName: webui.@THEME@.widget.checkbox.getInputClassName,
    getImageClassName: webui.@THEME@.widget.checkbox.getImageClassName,
    getInputElement: webui.@THEME@.widget.checkbox.getInputElement,
    getLabelClassName: webui.@THEME@.widget.checkbox.getLabelClassName,
    getProps: webui.@THEME@.widget.checkbox.getProps,
    _setProps: webui.@THEME@.widget.checkbox._setProps,
    submit: webui.@THEME@.widget.widgetBase.event.submit.processEvent,

    // Set defaults.
    event: webui.@THEME@.widget.checkbox.event,
    idSuffix: "_cb",
    widgetType: "checkbox"
});
