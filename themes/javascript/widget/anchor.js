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

dojo.provide("webui.@THEME@.widget.anchor");

dojo.require("dojo.widget.*");
dojo.require("dojo.uri.Uri");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.anchor.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.anchor = function() {
    // Set defaults.
    this.widgetType = "anchor";    
    // Register widget.
    dojo.widget.Widget.call(this);
    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set public functions. 
        if (this.name) {
            this.domNode._id = this.id; // store this for dojo.widget.byId. 
            this.id = this.name; 
        } else {
            this.domNode._id = this.id;
        }
        this.domNode.getProps = function() {              
            return dojo.widget.byId(this._id).getProps(); 
        } 
        this.domNode.setProps = function(props) {
            dojo.widget.byId(this._id).setProps(props); 
        }        
        // Set private functions.
        this.setProps = webui.@THEME@.widget.anchor.setProps;
        this.setAnchorProps = webui.@THEME@.widget.anchor.setAnchorProps;
        this.addChildren = webui.@THEME@.widget.anchor.addChildren;
        this.getProps = webui.@THEME@.widget.anchor.getProps;
        this.getClassName = webui.@THEME@.widget.anchor.getClassName; 
	//Create loop back function for onclick event.
        dojo.event.connect(this.domNode, "onclick",
        webui.@THEME@.widget.anchor.createOnClickCallback(this.domNode._id));
	// Initialize properties
	return this.setProps();
    }
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.anchor.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.anchor.getProps = function() {
    var props = {}; 

    // Set properties.
    if (this.hrefLang) {props.hrefLang = this.hrefLang; }
    if (this.target) {props.target = this.target; }
    if (this.type) {props.type = this.type; }
    if (this.rev) {props.rev = this.rev; }
    if (this.rel) {props.rel = this.rel; }
    if (this.shape) {props.shape = this.shape; }
    if (this.coords) {props.coords = this.coords; }
    if (this.charset) {props.charset = this.charset; }
    if (this.accessKey) {props.accesskey = this.accessKey; }
    if (this.href) {props.href = this.href; }
    if (this.name) {props.name = this.name; }
    if (this.contents) {props.contents = this.contents; }
    if (this.disabled != null) {props.disabled = this.disabled; }    
    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getJavaScriptProps(this));
    // Override the default id provided by the developer.
    return props;

}

/**
 * This function is used to update widget properties with the
 * following Object literals. Not all properties are required.
 * <ul>
 * <li>id</li>
 * <li>className</li>
 * <li>coords</li>
 * <li>dir</li>
 * <li>disabled</li>
 * <li>href</li>
 * <li>hrefLang</li>
 * <li>lang</li>
 * <li>name</li>
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
 * <li>rev</li>
 * <li>rel</li>
 * <li>shape</li>
 * <li>style</li>
 * <li>tabIndex</li>
 * <li>title</li>
 * <li>visible</li>
 * <li>charset</li>
 * <li>accessKey</li>
 */
webui.@THEME@.widget.anchor.setProps = function(props){
    // Save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set properties.
    props.className = this.getClassName();
    this.setAnchorProps(props);
    this.addChildren(props);
}

/**
 * Set the properties for the anchor widget. 
 * This has been made a separate function so that
 * the hyperlink javascript can use this.
 */
webui.@THEME@.widget.anchor.setAnchorProps = function(props) {
    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
    webui.@THEME@.widget.common.setCommonProps(this.domNode, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.domNode, props);
    if (props.hrefLang) {this.domNode.hrefLang =  props.hrefLang;}
    if (props.target) {this.domNode.target = props.target;}
    if (props.type) {this.domNode.type = props.type;}
    if (props.rev) {this.domNode.rev = props.rev;}
    if (props.rel) {this.domNode.rel = props.rel;}
    if (props.shape) {this.domNode.shape = props.shape;}
    if (props.coords) {this.domNode.coords = props.coords;}
    if (props.charset) {this.domNode.charset = props.charset;}
    if (props.accessKey) {this.domNode.accesskey = props.accessKey;}
    if (props.href) {this.domNode.href = new dojo.uri.Uri(this.href).toString();}
    if (props.name) {this.domNode.name = props.name;}
    return true;          
}

webui.@THEME@.widget.anchor.addChildren = function(props) {    
    var contentElem;
    if (props.contents && props.contents.length > 0) {
         for(i=0; i<props.contents.length; i++) {
             if (props.contents[i].id) {
                 contentElem = document.getElementById(props.contents[i].id) 
                 if (contentElem != null) {
                     contentElem.setProps(props.contents[i]);
                 } else {
                     webui.@THEME@.widget.common.addFragment(this.domNode, props.contents[i],"last");
                 }
             } else {
                 webui.@THEME@.widget.common.addFragment(this.domNode, props.contents[i],"last");
             }
         }
     }
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.anchor.getClassName = function() {
    var className = null;
    if (this.href && this.disabled == false) {
        className = webui.@THEME@.widget.props.anchor.className;
    } else {
        className = webui.@THEME@.widget.props.anchor.disabledClassName;
    }
    return (this.className)
        ? className + " " + this.className
        : className;

}

 /**
  * Helper function to create callback for onClick event.
  *
  * @param id The HTML element id used to invoke the callback.
  *
  */
 webui.@THEME@.widget.anchor.createOnClickCallback = 
      function(id) {
     if (id != null) {
         // New literals are created every time this function
         // is called, and it's saved by closure magic.
         return function(event) { 
          // Cannot use this.id over here as when the 
          // anchor is clicked, the "this" will point
          // to the window and not the widget.
            var widget = dojo.widget.byId(id); 
           if (widget.disabled) {
               event.preventDefault();
             }
             return false;
         };
     }
 }

dojo.inherits(webui.@THEME@.widget.anchor, dojo.widget.HtmlWidget);

//-->

