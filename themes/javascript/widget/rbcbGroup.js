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
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.rbcbGroup = function() {    
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to add elements
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.rbcbGroup.addContents = function(props) {   
    if (props == null) {
        return false;
    }

    if (props.contents) {
        // Remove all the child nodes of <ul> node before adding.
        this.removeChildNodes(this.ulContainer);

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
                    this.addFragment(labelContainerClone, props.label, "last");
                    this.alreadyAdded = true;
                }
           } 

           // Set disabled.
           if (props.disabled != null) {
                props.contents[i].disabled = props.disabled;
           }

           // Add child to the group.
           this.addFragment(liNodeClone, props.contents[i], "last");
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
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.rbcbGroup.fillInTemplate = function() {
    // Set ids.
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

    // Set properties.
    return this.setProps();
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
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());

    return props;
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
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.rbcbGroup.refresh.beginEventTopic, {
                id: this.id,
                execute: execute
            });
        return true;
    }
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
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }  

    // Set DOM node properties.
    this.setCoreProps(this.domNode, props);   
   
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
    
    return props; // Return props for subclasses.
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.rbcbGroup, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.rbcbGroup, {
    // Set private functions.
    addContents: webui.@THEME@.widget.rbcbGroup.addContents,      
    fillInTemplate: webui.@THEME@.widget.rbcbGroup.fillInTemplate,
    getProps: webui.@THEME@.widget.rbcbGroup.getProps,                
    refresh: webui.@THEME@.widget.rbcbGroup.refresh.processEvent,                 
    setProps: webui.@THEME@.widget.rbcbGroup.setProps,

    // Set defaults
    widgetType: "rbcbGroup"
});

//-->
