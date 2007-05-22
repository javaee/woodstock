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

dojo.provide("webui.@THEME@.widget.rbcbGroup");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.rbcbGroup.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.rbcbGroup = function() {    
    // Set defaults
    this.widgetType = "rbcbGroup";    
    
    // Register widget
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids
        if (this.id) {                    
            this.divContainer.id = this.id + "_divContainer";
            this.labelContainer.id = this.id + "_labelContainer";                    
            this.liNode.id = this.id + "_liNode";
            this.ulContainer.id = this.id + "_ulContainer";         
        } 
    
        // Set public functions
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }   
        

        // Set private functions 
        this.addContents = webui.@THEME@.widget.rbcbGroup.addContents;      
        this.destroy = webui.@THEME@.widget.rbcbGroup.destroy;
        this.getProps = webui.@THEME@.widget.rbcbGroup.getProps;                
        this.refresh = webui.@THEME@.widget.rbcbGroup.refresh.processEvent;                 
        this.setProps = webui.@THEME@.widget.rbcbGroup.setProps;       

        // Initialize properties.
        return this.setProps();
    }    
}

/**
 * Helper function to add elements
 *
 */

webui.@THEME@.widget.rbcbGroup.addContents = function(props) {   
    if (props == null) {
        return false;
    }
    
    if (props.contents) {  
      
            // Remove all the child nodes of <ul> node before adding. 
        webui.@THEME@.widget.common.removeChildNodes(this.ulContainer);  
   
        this.alreadyAdded = false;
        
        for (var i = 0; i < props.contents.length; i++) {                                                   
            
              // Clone <li> node.           
           var liNodeClone = this.liNode.cloneNode(false);        

           // Append the child element to <ul>
           this.ulContainer.appendChild(liNodeClone);     
           
           // Add label to the group only once. 
           if (props.label) {            
                if (!this.alreadyAdded) {                    
                    var labelContainerClone = this.labelContainer.cloneNode(false);
                    liNodeClone.appendChild(labelContainerClone);
                    webui.@THEME@.widget.common.addFragment(labelContainerClone, props.label, "last");
                    this.alreadyAdded = true;
                } 
           } 
           
           // Set disabled.
           if (props.disabled != null) {
                props.contents[i].disabled = props.disabled;
           }
           
           // Add child to the group.                              
           webui.@THEME@.widget.common.addFragment(liNodeClone, props.contents[i], "last");              
          
        }
    } else {
        // Update the disabled property client side
        if (props.disabled != null && this.contents) {
            for (var i = 0; i < this.contents.length; i++) {
                var contentWidget = dojo.widget.byId(this.contents[i].id);
                if (contentWidget) {                                  
                    contentWidget.setProps({disabled: props.disabled});                               
                } 
            }
        }        
    }    
    
    return true;
}  

/**
 * Helper function to remove all the existing widgets.
 *
 */
webui.@THEME@.widget.rbcbGroup.destroy = function() {
    // Remove label widget.
    if (this.label != null) {
        var labelWidget = dojo.widget.byId(this.label.id);
        if (labelWidget) {
            labelWidget.destroy();
        }
    }
    
    // Remove content widgets.
    if (this.contents != null) {
        for (var i = 0; i < this.contents.length; i++) {
            var contentWidget = dojo.widget.byId(this.contents[i].id);
            contentWidget.destroy();
        }
    }
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.rbcbGroup.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.rbcbGroup.getProps = function() {
    var props = {};

    // Set properties. 
    if (this.contents) { props.contents = this.contents; }    
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.id) { props.id = this.id; }
    if (this.label) { props.label = this.label; }    
    if (this.name) { props.name = this.name; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }  

    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    

    return props;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul> 
 *  <li>align</li> 
 *  <li>className</li>
 *  <li>contents</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>label</li>
 *  <li>lang</li>
 *  <li>name</li>
 *  <li>readOnly</li>    
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>visible</li>  
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.rbcbGroup.setProps = function(props) {
    // Save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }  

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);   
   
     // Update label properties.
     if (props.label || props.disabled != null && this.label) {
        if (props.label == null) {
            props.label = {};
        }
        var labelWidget = dojo.widget.byId(this.label.id);
        
        if (labelWidget) {
            labelWidget.setProps(props.label);
        }         
     }       

     // Set contents.    
     if (props.contents || props.disabled !=null) {              
        this.addContents(props);   
     }
    
    return true;
}

/**
  * This closure is used to process refresh events.
  */
 webui.@THEME@.widget.rbcbGroup.refresh = {
     /**
      * Event topics for custom AJAX implementations to listen for.
      */
     beginEventTopic: "webui_@THEME@_widget_rbcbGroup_refresh_begin",
     endEventTopic: "webui_@THEME@_widget_rbcbGroup_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
      * against which the execute portion of the request processing lifecycle
      * must be run.
      */
     processEvent: function(execute) {
         // Publish event.
         webui.@THEME@.widget.rbcbGroup.refresh.publishBeginEvent({
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
         dojo.event.topic.publish(webui.@THEME@.widget.rbcbGroup.refresh.beginEventTopic, props);
         return true;
     },
 
     /**
      * Publish an event for custom AJAX implementations to listen for.
      *
      * @param props Key-Value pairs of properties of the widget.
      */
     publishEndEvent: function(props) {
         dojo.event.topic.publish(webui.@THEME@.widget.rbcbGroup.refresh.endEventTopic, props);
         return true;
     }
 }

dojo.inherits(webui.@THEME@.widget.rbcbGroup, dojo.widget.HtmlWidget);

//-->
