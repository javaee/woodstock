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
    // Set defaults.
    this.level = 2;
    this.required = false;
    this.valid = true;
    this.widgetType = "label";

    // Register widget.
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
        this.domNode.setProps = function(props) { dojo.widget.byId(this.id).setProps(props); }

        // Set private functions.
        this.getClassName = webui.@THEME@.widget.label.getClassName;
        this.setProps = webui.@THEME@.widget.label.setProps;

        // Set properties.
        this.setProps(this);
        return true;
    }
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.label.getClassName = function() {
    // Set style for default label level.
    var classNameLabel = webui.@THEME@.widget.props.label.levelTwoStyleClass;

    if (this.valid == false) {
        classNameLabel = webui.@THEME@.widget.props.label.errorStyleClass;
    } else if (this.level == 1) {
        classNameLabel = webui.@THEME@.widget.props.label.levelOneStyleClass;
    } else if (this.level == 3) {
        classNameLabel = webui.@THEME@.widget.props.label.levelThreeStyleClass;
    }
    return (this.className)
        ? classNameLabel + " " + this.className
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

    // After widget has been initialized, save properties for later updates.
    if (this.updateProps == true) {
        webui.@THEME@.widget.common.extend(this, props);    
    }
    // Set flag indicating properties can be updated.
    this.updateProps = true;

    // Set style class before calling setCoreProps.
    props.className = this.getClassName();
    
    // Set attributes.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
    webui.@THEME@.widget.common.setCommonProps(this.domNode, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.domNode, props);

    if (props.htmlFor) { this.domNode.setAttribute("for", props.htmlFor); }
    if (props.value) { this.domNode.setAttribute("value", props.value); }

    // Set label value.
    if (props.value) {
        webui.@THEME@.widget.common.addFragment(this.valueContainer,
            dojo.string.escape("html", props.value)); 
    }
  
    // Set error image properties.
    if (props.errorImage || props.valid != null) {
        // Ensure property exists so we can call setProps just once.
        if (props.errorImage == null) {
            props.errorImage = {};
        }

        // Show error image.
        props.errorImage.visible = (this.valid != null)
            ? !this.valid : false;

        // Update widget/add fragment.
        var errorImageWidget = dojo.widget.byId(this.errorImage.id); 
        if (errorImageWidget) {
            errorImageWidget.setProps(props.errorImage);
        } else {
            webui.@THEME@.widget.common.addFragment(this.errorImageContainer,
                props.errorImage);
        }
    }

    // Set required image properties.
    if (props.requiredImage || props.required != null) {       
        // Ensure property exists so we can call setProps just once.
        if (props.requiredImage == null) {
            props.requiredImage = {};
        }

        // Show required image.
        props.requiredImage.visible = (this.required != null)
            ? this.required : false;

        // Update widget/add fragment.
        var requiredImageWidget = dojo.widget.byId(this.requiredImage.id);
        if (requiredImageWidget) {
            requiredImageWidget.setProps(props.requiredImage);
        } else {
            webui.@THEME@.widget.common.addFragment(this.requiredImageContainer,
                props.requiredImage);
        }
    }
    return true;
}

dojo.inherits(webui.@THEME@.widget.label, dojo.widget.HtmlWidget);

//-->
