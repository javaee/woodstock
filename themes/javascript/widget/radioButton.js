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

dojo.provide("webui.@THEME@.widget.radioButton");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.radioButton.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.radioButton = function() {    
    // Set defaults
    this.widgetType = "radioButton";    
    
    // Register widget
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids
        if (this.id) {              
            this.radioButtonNode.id = this.id + "_rb";
            this.imageContainer.id = this.id + "_imageContainer";
            this.labelContainer.id = this.id + "_labelContainer";            
        } 
    
        // Set public functions
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        this.domNode.getInputElement = function() { return dojo.widget.byId(this.id).getInputElement(); }

        // Set private functions 
        this.getClassName = webui.@THEME@.widget.radioButton.getClassName;      
        this.getProps = webui.@THEME@.widget.radioButton.getProps;
        this.setProps = webui.@THEME@.widget.radioButton.setProps;    
        this.getInputElement = webui.@THEME@.widget.radioButton.getInputElement;

        // set properties
        this.setProps(this);
        return true;    
    }    
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.radioButton.getClassName = function() {
    // Set style class for the span element.
    var spanStyleClass = webui.@THEME@.widget.props.radioButton.spanClassName;
    if (this.disabled == true) {
        spanStyleClass = webui.@THEME@.widget.props.radioButton.spanDisabledClassName;
    }
    return (this.className) 
        ? spanStyleClass + " " + this.className 
        : spanStyleClass;
}

/**
  * To get the HTML Input element.   
  *
  * @return HTML Input element. 
  */
 webui.@THEME@.widget.radioButton.getInputElement = function() {
     return this.radioButtonNode;
 }

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.radioButton.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.radioButton.getProps = function() {
    var props = {        
        checked: this.checked,
        disabled: this.disabled,
        image: this.image,
        label: this.label,
        name: this.name,                
        readOnly: this.readOnly,
        value: this.value
    };

    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getJavaScriptProps(this));

    return props;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
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
 *  <li>onBlur</li>
 *  <li>onChange</li>
 *  <li>onClick</li> 
 *  <li>onFocus</li> 
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseMove</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li> 
 *  <li>onSelect</li>
 *  <li>readOnly</li>    
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>value</li>  
 *  <li>visible</li>  
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.radioButton.setProps = function(props) {
    if (props == null) {
        return false;
    }     
    
   // After widget has been initialized, save properties for later updates.
    if (this.updateProps == true) {
        webui.@THEME@.widget.common.extend(this, props);    
    }
    // Set flag indicating properties can be updated.
    this.updateProps = true;
    
    // Set style class before calling the setCoreProps.   
    props.className = this.getClassName();
    
    // Set attributes for the outermost element i.e <span>
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);   
    
    // Set common properties for the <input> element
    webui.@THEME@.widget.common.setCommonProps(this.radioButtonNode, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.radioButtonNode, props);        
    
   
    if (props.name) { 
        this.radioButtonNode.setAttribute("name", props.name); 
    } else {
        this.radioButtonNode.setAttribute("name", this.radioButtonNode.id);
    }
    if (props.value) {
        this.radioButtonNode.setAttribute("value", props.value); 
    }
    
    // Set checked.
    if (props.checked != null) {
        if (props.checked == true) { 
            this.radioButtonNode.setAttribute("checked", "checked"); 
        } else {
            this.radioButtonNode.removeAttribute("checked");
        }
    }    

    // Set readOnly
    if (props.readOnly != null) {
        if (props.readOnly == true) { 
            this.radioButtonNode.setAttribute("readOnly", "readOnly"); 
        } else {
            this.radioButtonNode.removeAttribute("readOnly");
        }
    }    


    // Set disabled.
    if (props.disabled != null) { 
        if (props.disabled == true) {            
            this.radioButtonNode.setAttribute("disabled", "disabled"); 
        } else {             
            this.radioButtonNode.removeAttribute("disabled"); 
        }
    }
    
    // Set image properties.
    if (props.image || props.disabled != null) {
        // Ensure property exists so we can call setProps just once.
        if (props.image == null) {
            props.image = {};
        }
        
        // Setting style class.
        if (props.disabled == true) {
            props.image.className = webui.@THEME@.widget.props.radioButton.imageDisabledClassName;
        } else {
            props.image.className = webui.@THEME@.widget.props.radioButton.imageClassName; 
        }
        
        // Update widget/add fragment.
        var imageWidget = dojo.widget.byId(this.image.id);        
        if (imageWidget) {
            imageWidget.setProps(props.image);        
        } else {        
            webui.@THEME@.widget.common.addFragment(this.imageContainer, props.image);
        }
    }  
  
    // Set label propertiess.        
    if (props.label || props.disabled != null) {     
        // Ensure property exists so we can call setProps just once.
        if (props.label == null) {
            props.label = {};
        }
        
        // Setting style class.
        if (props.disabled == true) {            
            props.label.className = webui.@THEME@.widget.props.radioButton.labelDisabledClassName; 
        } else {
            props.label.className = webui.@THEME@.widget.props.radioButton.labelClassName;
        }

        // update widget/add fragment.
        var labelWidget = dojo.widget.byId(this.label.id);
        if (labelWidget) {
            labelWidget.setProps(props.label);            
        } else {            
            webui.@THEME@.widget.common.addFragment(this.labelContainer, props.label);
        }        
    }
    return true;
}

dojo.inherits(webui.@THEME@.widget.radioButton, dojo.widget.HtmlWidget);

//-->
