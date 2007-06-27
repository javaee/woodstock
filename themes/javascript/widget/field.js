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

dojo.provide("webui.@THEME@.widget.field");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.field = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.field.fillInTemplate = function() {
    // Set ids.
    if (this.id) {
        this.labelContainer.id = this.id + "_label";
        this.fieldNode.id = this.id + "_field";
        this.fieldNode.name = this.id + "_field";
    }
    
    // Set public functions.
    this.domNode.getInputElement = function() { return dojo.widget.byId(this.id).getInputElement(); }
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
    
    // Set properties.
    return this.setProps();
}

/**
 * Helper function to obtain widget class names.
 * Implementation within this field.js returns null,
 * and it is expected that subclasses override this function.
 */
webui.@THEME@.widget.field.getClassName = function() {   
    return null;
}

/**
 * Returns the HTML input element that makes up the text field.
 *
 * @return a reference to the HTML input element. 
 */
webui.@THEME@.widget.field.getInputElement = function() {
    return this.fieldNode;
}

/**
 * This function is used to get widget properties. 
 * @see webui.@THEME@.widget.field.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.field.getProps = function() {
    var props = {};
    
    // Set properties.
    if (this.alt) { props.alt = this.alt; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.label) { props.label= this.label; }
    if (this.notify) { props.notify = this.notify; }
    if (this.text != null) { props.text = this.text; }
    if (this.title != null) { props.title = this.title; }
    if (this.type) { props.type= this.type; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }
    if (this.required != null) { props.required = this.required; }
    if (this.size > 0) { props.size = this.size; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.style != null) { props.style = this.style; }
    
    // After widget has been initialized, get user's input.
    if (this.isInitialized() == true && this.fieldNode.value != null) {
        props.value = this.fieldNode.value;
    } else if (this.value != null) {
        props.value = this.value;
    }
    
    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getJavaScriptProps());
    
    return props;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>label</li>
 *  <li>lang</li>
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
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>valid</li>
 *  <li>value</li>
 *  <li>visible</li> 
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.field.setProps = function(props) {   
    // Save properties for later updates.
    if (props != null) {
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }
    
    // Set attributes.  
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.fieldNode, props);
    this.setJavaScriptProps(this.fieldNode, props);
    
    // Set text field attributes.    
    if (props.size > 0) { this.fieldNode.size = props.size; }
    if (props.value != null) { this.fieldNode.value = props.value; }
    if (props.title != null) { this.fieldNode.title = props.title; }   
    if (props.disabled != null) { 
        this.fieldNode.disabled = new Boolean(props.disabled).valueOf();
    }
    if (props.readOnly != null) { 
        this.fieldNode.readOnly = new Boolean(props.readOnly).valueOf();
    }
    
    this.fieldNode.className = this.getClassName();
    
    // Set label properties.
    if (props.label || (props.valid != null || props.required != null) && this.label) {
        // Ensure property exists so we can call setProps just once.
        if (props.label == null) {
            props.label = {};
        }
        
        // Set valid.
        if (props.valid != null) { props.label.valid = props.valid; }
        
        // Set required.
        if (props.required != null) { props.label.required = props.required; }
        
        // Update widget/add fragment.                
        var labelWidget = dojo.widget.byId(this.label.id);
        if (labelWidget) {
            labelWidget.setProps(props.label);
        } else {
            this.addFragment(this.labelContainer, props.label);
        }
    }
    return props; // Return props for subclasses.
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.field, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.field, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.field.fillInTemplate,
    getClassName: webui.@THEME@.widget.field.getClassName,
    getInputElement: webui.@THEME@.widget.field.getInputElement,
    getProps: webui.@THEME@.widget.field.getProps,
    setProps: webui.@THEME@.widget.field.setProps,
   
    // Set defaults.
    disabled: false,
    required: false,
    size: 20,
    valid: true
});
