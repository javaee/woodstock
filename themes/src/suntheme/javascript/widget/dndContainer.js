/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

webui.@THEME@.dojo.provide("webui.@THEME@.widget.dndContainer");

webui.@THEME@.dojo.require("webui.@THEME@.dnd");
webui.@THEME@.dojo.require("webui.@THEME@.widget.widgetBase");

 /**
  * @name webui.@THEME@.widget.dndContainer
  * @extends webui.@THEME@.widget.widgetBase
  * @class This class contains functions for the dndContainer widget
  * @constructor This function is used to construct a dndContainer widget.
  */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.dndContainer", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    widgetName: "dndContainer" // Required for theme properties.
});

/**
 * Helper function to create callback for user's node creator function
 *
 * @param {String} name of the function to be called for node creation, with 
 * signature function(data, hint).
 * @return {Function} function to be called for node creation.
 */
webui.@THEME@.widget.dndContainer.prototype.createCreatorCallback = function(funcName) {
    var dragTypes = this.dragTypes ? this.dragTypes : "";
    var dragSource = this.dragSource;
    var func = new Function("data", "hint", "return " + funcName + "(data, hint)");

    // This function will invoke user provided creator function (onNodeCreateFunc) 
    // and will add default dnd type to the item created by it. All items 
    // created within this container will automatically be associated with the 
    // type provided by the container - a container's 'dragTypes' property.
    //
    // The function returns the same data structure as required to be returned 
    // by the user's creator function.
    //
    // {
    //  node: DOM node that will be inserted.
    //  data: Data that will be associated with newly created node.
    //  type: Drag type array that will be associated with newly created node.
    // }; 
    //
    return function(data, hint) { 
        if (func == null)
            return null;
        var ret = func(data, hint);
        
        if (ret != null) {
            // Add type to it.
            ret.type = dragTypes;
        } else {
            // User func did not create node - create one with default creator.
            ret = dragSource.defaultCreator(data, hint);
        }
        return ret;
    };
};

/**
 * Helper function to create callback for user's onDrop function.
 *
 * @return {Function} function to be called upon drop.
 */
webui.@THEME@.widget.dndContainer.prototype.createOnDndDropCallback = function() {
    var containerWidget = this;
 
    return function(source, nodes, copy){
        // Call user's onDropFunction
        if (containerWidget.onDropFunc) {
            try {
                var func = eval(containerWidget.onDropFunc);
                return func(source, nodes, copy);
            } catch (error) {           
                //do nothing        
            }
        }
        return true;
    };
};

/**
 * Helper function to obtain HTML container element class names.
 *
 * @return {String} calculated class name of the container node.
 */
webui.@THEME@.widget.dndContainer.prototype.getContainerClassName = function() {   
    // Add default style.
    return  this.dndContainer.className;
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.dndContainer.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);
    
    // Set properties.
    if (this.dropTypes != null) { props.dropTypes= this.dropTypes; }
    if (this.contents)          { props.contents = this.contents; }
    if (this.contentsDragData)  { props.contentsDragData = this.contentsDragData; }
    if (this.copyOnly != null)  { props.copyOnly = this.copyOnly; }
    if (this.onDropFunc)        { props.onDropFunc = this.onDropFunc; }
    if (this.onNodeCreateFunc)  { props.onNodeCreateFunc = this.onNodeCreateFunc; }
    if (this.dragTypes != null) { props.dragTypes = this.dragTypes; }
    if (this.style != null)     { props.style = this.style; }
    if (this.title != null)     { this.dndContainer.title = this.title;}
    
    return props;
};

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.dndContainer.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.dndContainer.id = this.id + "_container";
    }
    
    // Make things draggable.
    var params = {};
    if (this.onNodeCreateFunc) {
        params.creator = this.createCreatorCallback(this.onNodeCreateFunc);                   
    }
    if (this.copyOnly) {
        params.copyOnly = this.copyOnly;
    }
    if (this.dropTypes) {        
        params.accept = (this.dropTypes instanceof Array) 
            ? this.dropTypes : this.dropTypes.split(',');
    }
    if (this.horizontalIndicator != null) {        
        params.horizontal = this.horizontalIndicator;
    }
    if (this.dragTypes == null) {        
        params.isSource = false;
    }
    params.onDropFunction = this.createOnDndDropCallback();
    
    this.dragSource = new webui.@THEME@.dnd.Source(this.dndContainer, params);

    return this.inherited("postCreate", arguments);
};

/**
 * This function is used to set widget properties using Object literals.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @config {Array} dragTypes list of space-trimmed strings representing 
 * types of the drag element.
 * @config {String} contents children of the container.
 * @config {String} className CSS selector.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} onNodeCreateFunc Javascript code to create new item.
 * @config {Array} dropTypes list of space-trimmed strings accepted by this 
 * container as a drop.
 * @config {String} style Specify style rules inline.
 * @config {boolean} visible Hide or show element.
 * @return {Boolean} true if operation was successfull, false otherwise.
 */
webui.@THEME@.widget.dndContainer.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // Replace contents.
    if (props.contents) {
        this.contents = null;
    }
    return this.inherited("setProps", arguments);
};

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.dndContainer.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }      
    if (props.dragTypes) {        
        this.dragTypes = (props.dragTypes instanceof Array)
            ? props.dragTypes : props.dragTypes.split(',');        
    }
    if (props.dropTypes) {        
        this.dropTypes = (props.dropTypes instanceof Array)
            ? props.dropTypes : props.dropTypes.split(',');
    }
    if (props.onDropFunc) {
        this.onDropFunc =  props.onDropFunc; 
    }
    
    // Set container class name.
    this.dndContainer.className = this.getContainerClassName();
    
    // Set contents.         
    // Assert there is a dragData and id entry for each fragment
    if (props.contents && props.contentsDragData 
            && props.contents.length == props.contentsDragData.length 
            && this.dragSource) {                   
        // Remove child nodes.
        this.widget.removeChildNodes(this.dndContainer);
        
	for (var i = 0; i < props.contents.length; i++) {
            if (this.dragTypes) {
                // For each item in the contents create a span placeholder using
                // normalized creator function (this will allow for consistent 
                // internal placement of container elements within container)
                // which will be an element of the container and at the same 
                // time will contain the output of addFragment. Add the rendered
                // content into the span.             
                var node = this.dragSource.addItem([""], this.dragTypes, 
                    props.contentsDragData[i]); //empty data content
                this.widget.addFragment(node, props.contents[i], "last");
            } else {
                // Simply add data, without making it draggable
                this.widget.addFragment(this.dndContainer, props.contents[i], "last");            
            }
        }
    }
    
    // Set more properties.
    this.setCommonProps(this.dndContainer, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
};
