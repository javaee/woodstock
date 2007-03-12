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

dojo.provide("webui.@THEME@.widget.label");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.label.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.label = function() {
    this.widgetType = "label";
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.requiredImageContainer.id = this.id + "_requiredImageContainer";
            this.errorImageContainer.id = this.id + "_errorImageContainer";
            this.valueContainer.id = this.id + "_valueContainer";
        }

        // Set public functions.
        this.domNode.setProps = webui.@THEME@.widget.label.setProps;

        // Set private functions (private functions/props prefixed with "_").
        this.domNode._getClassName = webui.@THEME@.widget.label.getClassName;

        // Set properties.
        this.domNode.setProps({
            errorImage: this.errorImage,
            htmlFor: this.htmlFor,
            id: this.id,
            level: (this.level > 0) ? this.level : 2,
            className: this.className, 
            required: (this.required != null) ? this.required : false,
            requiredImage: this.requiredImage,
            valid: (this.valid != null) ? this.valid : true,
            value: this.value,
            onClick: this.onClick,
            onDblClick: this.onDblClick,
            onFocus: this.onFocus,
            onKeyDown: this.onKeyDown,
            onKeyPress: this.onKeyPress,
            onKeyUp: this.onKeyUp,
            onMouseDown: this.onMouseDown,
            onMouseOut: this.onMouseOut,
            onMouseOver: this.onMouseOver,
            onMouseUp: this.onMouseUp,
            onMouseMove: this.onMouseMove,
            dir: this.dir,
            lang: this.lang,
            accesskey: this.accesskey,
            style: this.style,
            title: this.title,
            visible: this.visible

        });
        return true;
    }
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.label.getClassName = function() {
    // Set style for default label level.
    var classNameLabel = webui.@THEME@.widget.props.label.levelTwoStyleClass;

    if (this._props.valid == false) {
        classNameLabel = webui.@THEME@.widget.props.label.errorStyleClass;
    } else if (this._props.level == 1) {
        classNameLabel = webui.@THEME@.widget.props.label.levelOneStyleClass;
    } else if (this._props.level == 3) {
        classNameLabel = webui.@THEME@.widget.props.label.levelThreeStyleClass;
    }

    if (classNameLabel == this._props.className) {
        this._props.className = "";
    } 
    return (this._props.className)
           ? classNameLabel + " " + this._props.className
           : classNameLabel;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>errorImage</li>
 *  <li>htmlFor</li>
 *  <li>id</li>
 *  <li>level</li>
 *  <li>styleClass</li>
 *  <li>required</li>
 *  <li>requiredImage</li>
 *  <li>valid</li>
 *  <li>value</li>
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
 *  <li>dir</li>
 *  <li>lang</li>
 *  <li>accesskey</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.label.setProps = function(props) {
    if (props == null) {
        return false;
    }
         
    // Save properties for later updates.
    if (this._props) {
        Object.extend(this._props, props); // Override existing values, if any.
    } else {
        this._props = props;
    }
     
    props.className = this._getClassName();
    
    // Set attributes.
    webui.@THEME@.widget.common.setCoreProperties(this, props);    
    webui.@THEME@.widget.common.setJavaScriptProperties(this, this._props);

    if (this._props.htmlFor) { this.setAttribute("for", this._props.htmlFor); }
    if (this._props.value) { this.setAttribute("value", this._props.value); }
        
    // Set widget properties.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Set label value.
    if (this._props.value) {
        this._props.value = dojo.string.escape("html", this._props.value);
        webui.@THEME@.widget.common.addFragment(widget.valueContainer, this._props.value);
    }

    // Show required image.
    var widgetRequiredImage = dojo.widget.byId(this._props.requiredImage.id);
    
    // Set required image.
    if (this._props.requiredImage && this._props.required && !widgetRequiredImage) {
        webui.@THEME@.widget.common.addFragment(widget.requiredImageContainer, this._props.requiredImage);
    }
    
    // Error image widget.
    
    var widgetErrorImage = dojo.widget.byId(this._props.errorImage.id); 
    
    // Set error image.
    if (this._props.errorImage && !this._props.valid && !widgetErrorImage) {
        webui.@THEME@.widget.common.addFragment(widget.errorImageContainer, this._props.errorImage);
    }
    
    
    // Show error image
    if (widgetErrorImage) {
        widgetErrorImage.domNode.setProps({visible: !this._props.valid });
    }

    // Show required image
    if (widgetRequiredImage) {
        widgetRequiredImage.domNode.setProps({visible: this._props.required });
    }
    return true;
}

dojo.inherits(webui.@THEME@.widget.label, dojo.widget.HtmlWidget);

//-->
