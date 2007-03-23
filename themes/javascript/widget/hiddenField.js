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

dojo.provide("webui.@THEME@.widget.hiddenField");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.hiddenField.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.hiddenField = function() {
    // Set defaults.
    this.disabled = false;
    this.widgetType = "hiddenField";

    // Register widget.
    dojo.widget.Widget.call(this);
    
    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set public functions. 
        this.domNode.setProps = function(props) { dojo.widget.byId(this.id).setProps(props); }

        // Set private functions.
        this.setProps = webui.@THEME@.widget.hiddenField.setProps;

        // Set properties.
        this.setProps(this);
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>id</li>
 *  <li>name</li>
 *  <li>disabled</li>
 *  <li>value</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.hiddenField.setProps = function(props) {
    if (props == null) {
        return false;
    }
    
    // After widget has been initialized, save properties for later updates.
    if (this.updateProps == true) {
        webui.@THEME@.widget.common.extend(this, props);    
    }
    // Set flag indicating properties can be updated.
    this.updateProps = true;
            
    // Set attributes.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
                
    if (props.name) { this.domNode.setAttribute("name", props.name); }
    if (props.value) { this.domNode.setAttribute("value", props.value); }
    if (props.disabled == true) { 
        this.domNode.setAttribute("disabled", "disabled");
    } else { 
        this.domNode.removeAttribute('disabled');
    }
   
    return true;
}

dojo.inherits(webui.@THEME@.widget.hiddenField, dojo.widget.HtmlWidget);

//-->
