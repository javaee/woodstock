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
 * Helper function to add elements with the following 
 * Object literals.
 *
 * <ul> 
 *  <li>disabled</li> 
 *  <li>contents</li>
 * </ul>
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
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.checkboxGroup.fillInTemplate = function(props, frag) {
    // Set ids.
    if (this.id) {                    
        this.contentsRowNode.id = this.id + "_contentsRowNode";
        this.divContainer.id = this.id + "_divContainer";
        this.labelContainer.id = this.id + "_labelContainer";                    
        this.liNode.id = this.id + "_liNode";
        this.rowContainer.id = this.id + "_rowContainer";
        this.rowNode.id = this.id + "_rowNode";
        this.tableContainer.id = this.id + "_tableContainer";
        this.ulContainer.id = this.id + "_ulContainer";         
    }

    // Show label.
    if (this.label) {
        webui.@THEME@.common.setVisibleElement(this.rowNode, true);
    }

    // Set common functions.
    return webui.@THEME@.widget.checkboxGroup.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * @param classNames Optional array of selectors to concatinate with user's 
 * (this.className) property. Items are output in reverse order for precedence.
 */
webui.@THEME@.widget.checkboxGroup.getClassName = function(classNames) {
    if (!(classNames instanceof Array)) {
        classNames = new Array();
    }

    // Set default style.
    classNames[classNames.length] = (this.columns > 1)
        ? webui.@THEME@.widget.props.checkboxGroup.horizClassName
        : webui.@THEME@.widget.props.checkboxGroup.vertClassName;

    return webui.@THEME@.widget.checkboxGroup.superclass.getClassName.call(this, classNames);
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.checkboxGroup.getProps = function() {
    var props = webui.@THEME@.widget.checkboxGroup.superclass.getProps.call(this);

    // Set properties.     
    if (this.columns) { props.columns = this.columns; }
    if (this.contents) { props.contents = this.contents; }    
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.id) { props.id = this.id; }
    if (this.label) { props.label = this.label; }    
    if (this.name) { props.name = this.name; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }  

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
 * This function is used to set widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.checkboxGroup.setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.checkboxGroup.superclass.setProps.call(this, props);
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
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
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.checkboxGroup._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set label properties.
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
    if (props.contents || props.disabled != null) {              
        this.addContents(props);   
    }

    // Set remaining properties.
    return webui.@THEME@.widget.checkboxGroup.superclass._setProps.call(this, props);
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
    _setProps: webui.@THEME@.widget.checkboxGroup._setProps,

    // Set defaults.
    widgetType: "checkboxGroup"
});
