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

dojo.provide("webui.@THEME@.widget.hyperlink");

dojo.require("dojo.widget.*");
dojo.require("dojo.uri.Uri");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.anchor");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.hyperlink.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.hyperlink = function() {

    // Set defaults.
    this.widgetType = "hyperlink";   
    this.href = "#";
    // Register widget.
    dojo.widget.Widget.call(this);
    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {

        // Set public functions. 
        this.domNode.setProps = function(props) {
            return dojo.widget.byId(this.id).setProps(props); 
        }
        this.domNode.getProps = function() { 
            return dojo.widget.byId(this.id).getProps(); 
        } 

        // Set private functions.
        this.setProps = webui.@THEME@.widget.hyperlink.setProps;
        this.setAnchorProps = webui.@THEME@.widget.anchor.setAnchorProps;
        this.getProps = webui.@THEME@.widget.anchor.getProps;
        this.addChildren = webui.@THEME@.widget.anchor.addChildren;
        this.getProps = webui.@THEME@.widget.anchor.getProps;
        this.submit = webui.@THEME@.widget.hyperlink.submit; 
        this.getClassName = webui.@THEME@.widget.hyperlink.getClassName; 
        // Create loop back function for onClick event.
        dojo.event.connect(this.domNode, "onclick",
        webui.@THEME@.widget.hyperlink.createOnClickCallback(this.id, 
        this.formId, this.params));
	//Initialize properties
	return this.setProps();
    }
}

/**
 * This function is used to update widget properties with the
 * following Object literals. Not all properties are required.
 * <ul>
 * <li>id</li>
 * <li>className</li>
 * <li>dir</li>
 * <li>disabled</li>
 * <li>href</li>
 * <li>hrefLang</li>
 * <li>lang</li>
 * <li>onFocus</li>
 * <li>onBlur</li>
 * <li>onClick</li>
 * <li>onDblClick</li>
 * <li>onKeyDown</li>
 * <li>onKeyPress</li>
 * <li>onKeyUp</li>
 * <li>onMouseDown</li>
 * <li>onMouseOut</li>
 * <li>onMouseOver</li>
 * <li>onMouseUp</li>
 * <li>onMouseMove</li>
 * <li>style</li>
 * <li>tabIndex</li>
 * <li>title</li>
 * <li>visible</li>
 *
 * This function first calls the setAnchorProps present in
 * anchor widget to set all the properties common to the anchor
 * widget.
 */
webui.@THEME@.widget.hyperlink.setProps = function(props){
    // Save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }
    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();
    // Set properties that are common to the anchor element.
    this.setAnchorProps(props); 
    this.addChildren(props);
}

 /**
  * Helper function to create callback for onClick event.
  *
  * @param id The HTML element id used to invoke the callback.
  * @param linkId The id of the html anchor element
  * @param formId The id of the form element
  * @param params The parameters to be passed on when the hyperlink is clicked
  *
  */
 webui.@THEME@.widget.hyperlink.createOnClickCallback = 
      function(id, formId, params) {
     if (id != null) {
         // New literals are created every time this function
         // is called, and it's saved by closure magic.
         return function(event) { 
          // Cannot use this.id over here as when the 
          // hyperlink is clicked, the "this" will point
          // to the window and not the widget.
            var widget = dojo.widget.byId(id); 
            if (!widget.disabled) {
                widget.submit(formId, params);
            }
            event.preventDefault();
            return false;
         };
     }
 }


// This function submits the hyperlink if the hyperlink is enabled
 webui.@THEME@.widget.hyperlink.submit = function (formId, arr) {
     var theForm = document.getElementById(formId);
     var oldTarget = theForm.target;
     var oldAction = theForm.action;
     var link = this.domNode;
     theForm.action += "?" + this.id + "_submittedLink="+this.id;               
     if (link.target && link.target.length > 0) {// The default value is ""
         theForm.target = link.target;   
     } else {
         theForm.target = "_self";
     }
     if (arr != null) {
         var x;
         for (var i = 0; i < arr.length; i++) {
              x = arr[i];
          theForm.action +="&" + arr[i] + "=" + arr[i+1]; 
             i++;
         }
     }
     theForm.submit(); 

     if (link.target != null) {
         theForm.target = oldTarget;
         theForm.action = oldAction;
     }
     return false;        
 }


/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.hyperlink.getClassName = function() {
    var className = null;
    if (this.disabled == true) {
            className = webui.@THEME@.widget.props.hyperlink.disabledClassName;
    } else {
            className = webui.@THEME@.widget.props.hyperlink.className;
    }
    return (this.className)
        ? className + " " + this.className
        : className;

}
dojo.inherits(webui.@THEME@.widget.hyperlink, dojo.widget.HtmlWidget);

//-->

