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

dojo.provide("webui.@THEME@.widget.fieldBase");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.fieldBase = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
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
webui.@THEME@.widget.fieldBase.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.fieldBase.superclass.fillInTemplate.call(this, props, frag);
    
    // Set ids.
    if (this.id) {
        this.fieldNode.id = this.id + "_field";
        this.fieldNode.name = this.id + "_field";
        this.labelContainer.id = this.id + "_label";
    }
    
    // Set public functions.
    this.domNode.getInputElement = function() { return dojo.widget.byId(this.id).getInputElement(); }
    
    return true;
}

/**
 * Helper function to obtain HTML input element class names.
 */
webui.@THEME@.widget.fieldBase.getInputClassName = function() {   
    return null;
}

/**
 * Returns the HTML input element that makes up the text field.
 *
 * @return a reference to the HTML input element. 
 */
webui.@THEME@.widget.fieldBase.getInputElement = function() {
    return this.fieldNode;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.fieldBase.getProps = function() {
    var props = webui.@THEME@.widget.fieldBase.superclass.getProps.call(this);
    
    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.label) { props.label= this.label; }
    if (this.maxLength > 0) { props.maxLength = this.maxLength; }    
    if (this.notify) { props.notify = this.notify; }
    if (this.submitForm != null) { props.submitForm = this.submitForm; }
    if (this.text != null) { props.text = this.text; }
    if (this.title != null) { props.title = this.title; }
    if (this.type) { props.type= this.type; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.required != null) { props.required = this.required; }
    if (this.size > 0) { props.size = this.size; }
    if (this.style != null) { props.style = this.style; }
    if (this.valid != null) { props.valid = this.valid; }
    
    // After widget has been initialized, get user's input.
    if (this.isInitialized() == true && this.fieldNode.value != null) {
        props.value = this.fieldNode.value;
    } else if (this.value != null) {
        props.value = this.value;
    }
    return props;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>maxLength</li>
 *  <li>notify</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onFocus</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>readOnly</li>
 *  <li>required</li>
 *  <li>size</li>
 *  <li>style</li>
 *  <li>submitForm</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>valid</li>
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
webui.@THEME@.widget.fieldBase._setProps = function(props) {
    if (props == null) {
        return false;
    }
    
    // Set properties.
    if (props.submitForm == false || props.submitForm == true ) { 
        // connect the keyPress event
        dojo.event.connect(this.fieldNode, "onkeypress", webui.@THEME@.widget.fieldBase.event.submitForm.processEvent);
    }
    if (props.maxLength > 0) { this.fieldNode.maxLength = props.maxLength; }
    if (props.size > 0) { this.fieldNode.size = props.size;  }
    if (props.value != null) { this.fieldNode.value = props.value; }
    if (props.title != null) { this.fieldNode.title = props.title; }   
    if (props.disabled != null) { 
        this.fieldNode.disabled = new Boolean(props.disabled).valueOf();
    }
    if (props.readOnly != null) { 
        this.fieldNode.readOnly = new Boolean(props.readOnly).valueOf();
    }
    
    // Set label properties.
    if (props.label || (props.valid != null || props.required != null) && this.label) {
        // Ensure property exists so we can call setProps just once.
        if (props.label == null) {
            props.label = {}; // Avoid updating all props using "this" keyword.
        }
        
        // Set properties.
        props.label.id = this.label.id; // Required for updateFragment().
        props.label.required = this.required;
        props.label.valid = this.valid;
        
        // Update/add fragment.
        this.widget.updateFragment(this.labelContainer, props.label);
    }
    
    // Set HTML input element class name.
    this.fieldNode.className = this.getInputClassName();
    
    // Set more properties..
    this.setCommonProps(this.fieldNode, props);
    this.setEventProps(this.fieldNode, props);
    
    // Set remaining properties.
    return webui.@THEME@.widget.fieldBase.superclass._setProps.call(this, props);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.fieldBase.event = {
    /** 
     * This closure is used to process keyPress events. 
     */
    submitForm: {    
        /**
         * Helper function to process keyPress events on the field, which
         * enforces/disables submitForm behavior .
         * HTML events are connected to this function in fillInTemplate.
         */
        processEvent: function(event) {
            if (event == null) {
                return false;
            }
            
            if (event.keyCode == event.KEY_ENTER) {
                
                var widget = dojo.widget.byId(event.currentTarget.parentNode.id);
                if (!widget) 
                    return false;
                
                if (widget.submitForm == false) {
                    //disable form submission
                    if(window.event){
                        event.cancelBubble = true;
                        event.returnValue = false;
                    }else{
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    
                    return false;
                } else {
                    //submit the form                    
                    if (event.currentTarget.form)
                        event.currentTarget.form.submit();
                }
            }
            return true;    
        }
    }
}




// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.fieldBase, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.fieldBase, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.fieldBase.fillInTemplate,
    getInputClassName: webui.@THEME@.widget.fieldBase.getInputClassName,
    getInputElement: webui.@THEME@.widget.fieldBase.getInputElement,
    getProps: webui.@THEME@.widget.fieldBase.getProps,
    _setProps: webui.@THEME@.widget.fieldBase._setProps,
    
    // Set defaults.
    disabled: false,
    required: false,
    size: 20,
    valid: true
});
