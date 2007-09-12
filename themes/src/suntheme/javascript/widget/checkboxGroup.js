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
        this.widget.removeChildNodes(this.tbodyContainer);
        var rowContainerCloneNode = this.rowContainer.cloneNode(false);
        this.tbodyContainer.appendChild(rowContainerCloneNode);
        if (props.label) {
            var rowNodeClone = this.rowNode.cloneNode(false);
            rowContainerCloneNode.appendChild(rowNodeClone);
            var labelContainerClone = this.labelContainer.cloneNode(false);
            rowNodeClone.appendChild(labelContainerClone);
            this.widget.addFragment(labelContainerClone, props.label, "last");            
              
        }
        var itemN = 0;
        var length = props.contents.length;
        var columns = (props.columns <= 0) ? 1 : props.columns;
        var rows = (length + (columns-1))/columns;
        var propsdisabledvalue = props.disabled == null ? false : props.disabled;
        for (var row = 0; row <= rows; row++) {
            for (var column = 0; column < columns; column++) {
                if (itemN < length) {
                    // Clone < td> node.
                    var contentsRowNodeClone = this.contentsRowNode.cloneNode(false);
                    rowContainerCloneNode.appendChild(contentsRowNodeClone);
                    // Set disabled.                   
                    props.contents[itemN].disabled = propsdisabledvalue;
                   
                    // Add child to the group.
                    this.widget.addFragment(contentsRowNodeClone, props.contents[itemN], "last");
                    itemN++;
                }
            }
            if (row + 1 <= rows) {
                rowContainerCloneNode = this.rowContainer.cloneNode(false);
                this.tbodyContainer.appendChild(rowContainerCloneNode);
                // This check is required here. Else the elements won't be
                // aligned properly when there is no label.
                if (props.label != null) {
                    var contentsRowNodeClone = this.contentsRowNode.cloneNode(false);
                    rowContainerCloneNode.appendChild(contentsRowNodeClone);
                }
              
            }
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
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.checkboxGroup.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_checkboxGroup_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_checkboxGroup_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_checkboxGroup_event_state_begin",
        endTopic: "webui_@THEME@_widget_checkboxGroup_event_state_end"
    }
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
    webui.@THEME@.widget.checkboxGroup.superclass.fillInTemplate.call(this, props, frag);

    // Set ids.
    if (this.id) {                    
        this.contentsRowNode.id = this.id + "_contentsRowNode";
        this.divContainer.id = this.id + "_divContainer";
        this.labelContainer.id = this.id + "_labelContainer";                            
        this.rowContainer.id = this.id + "_rowContainer";
        this.rowNode.id = this.id + "_rowNode";
        this.tableContainer.id = this.id + "_tableContainer";   
        this.tbodyContainer.id = this.id + "_tbodyContainer";     
    }

    // Show label.
    if (this.label) {
        webui.@THEME@.common.setVisibleElement(this.rowNode, true);
    }
    return true;
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 */
webui.@THEME@.widget.checkboxGroup.getClassName = function() {
    // Set default style.
    var className = (this.columns > 1)
        ? this.widget.getClassName("CBGRP_HORIZ", "")
        : this.widget.getClassName("CBGRP_VERT", "");

    return (this.className)
        ? className + " " + this.className
        : className;
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
 * This function is used to set widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * Note: If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 *
 * @param props Key-Value pairs of properties.
 * @param notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.checkboxGroup.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.checkboxGroup.superclass.setProps.call(this, props, notify);
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
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.checkboxGroup._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set label properties.
    if (props.label) {     
        // Set properties.
        props.label.id = this.label.id; // Required for updateFragment().

        // Update/add fragment.
        this.widget.updateFragment(this.labelContainer, props.label);
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
    setProps: webui.@THEME@.widget.checkboxGroup.setProps,
    _setProps: webui.@THEME@.widget.checkboxGroup._setProps,

    // Set defaults.
    event: webui.@THEME@.widget.checkboxGroup.event,
    widgetType: "checkboxGroup"
});
