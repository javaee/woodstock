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

dojo.provide("webui.@THEME@.widget.checkboxGroup");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.checkboxGroup = function() {    
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to add elements
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.checkboxGroup.addContents = function(props) {   
    if (props == null) {
        return false;
    }

    if (props.contents) {    
        this.removeChildNodes(this.ulContainer);
        for (var i = 0; i < props.contents.length; i++) { 
           // Clone <li> node.
           var liNodeClone = this.liNode.cloneNode(false);

           // Append the child element to <ul>
             this.ulContainer.appendChild(liNodeClone);
        
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
webui.@THEME@.widget.checkboxGroup.fillInTemplate = function() {
    // Set ids.
    if (this.id) {                    
        this.contentsRowNode.id = this.id + "_contentsRowNode";
        this.contentsContainer.id = this.id + "_contentsContainer";
        this.divContainer.id = this.id + "_divContainer";
        this.labelContainer.id = this.id + "_labelContainer";                    
        this.liNode.id = this.id + "_liNode";
        this.rowContainer.id = this.id + "_rowContainer";
        this.rowNode.id = this.id + "_rowNode";
        this.tableContainer.id = this.id + "_tableContainer";
        this.ulContainer.id = this.id + "_ulContainer";         
    } 
    
    // Set public functions
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
    
    if (this.label == null) {
        webui.@THEME@.common.setVisibleElement(this.rowNode, false);
    }

    // Set properties.
    return this.setProps(this.getProps());
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.checkboxGroup.getClassName = function() {    
    var className = webui.@THEME@.widget.props.checkboxGroup.vertClassName;
    if (this.columns > 1) {
        className = webui.@THEME@.widget.props.checkboxGroup.horizClassName;    
    }    
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.checkboxGroup.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.checkboxGroup.getProps = function() {
    var props = {};

    // Set properties.     
    if (this.columns) { props.columns = this.columns; }
    if (this.contents) { props.contents = this.contents; }    
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.id) { props.id = this.id; }
    if (this.label) { props.label = this.label; }    
    if (this.name) { props.name = this.name; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }  

    // Add DOM node properties.
    Object.extend(props, this.getCoreProps());

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.checkboxGroup.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_checkboxGroup_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_checkboxGroup_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.checkboxGroup");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.checkboxGroup.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.checkboxGroup.refresh.endEventTopic
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
 *  <li>columns</li>
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
webui.@THEME@.widget.checkboxGroup.setProps = function(props) {
    if (props == null) {
        return null;
    }

    // Save properties for later updates.
    if (this.isInitialized() == true) {
        this.extend(this, props);
    } 
    
    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();

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
        } else {
            this.addFragment(this.labelContainer, props.label);
        }         
    }       

    // Set contents.    
    if (props.contents || props.disabled !=null) {              
        this.addContents(props);   
    }
    return props; // Return props for subclasses.
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.checkboxGroup, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.checkboxGroup, {
    // Set private functions.
    addContents: webui.@THEME@.widget.checkboxGroup.addContents,      
    fillInTemplate: webui.@THEME@.widget.checkboxGroup.fillInTemplate,
    getClassName: webui.@THEME@.widget.checkboxGroup.getClassName,
    getProps: webui.@THEME@.widget.checkboxGroup.getProps,                
    refresh: webui.@THEME@.widget.checkboxGroup.refresh.processEvent,                 
    setProps: webui.@THEME@.widget.checkboxGroup.setProps,

    // Set defaults
    templatePath: webui.@THEME@.theme.getTemplatePath("checkboxGroup"),
    templateString: webui.@THEME@.theme.getTemplateString("checkboxGroup"),
    widgetType: "checkboxGroup"
});
