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

dojo.provide("webui.@THEME@.widget.menuBase");
dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.menuBase = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Add the specified options to the dom element.
 *
 * @param props The JSON properties of the menu
 * @param menuNode The node to which the menu items are to be added
 * @param formId The id of the form.
 */
webui.@THEME@.widget.menuBase.addOptions = function(props, menuNode, formId) {
    var groupNode, optionNode, separator, sepNode;
    for (var i = 0; i < props.options.length; i++) {
        
        // create an li node which will represent an option element.
        optionNode = this.optionContainer.cloneNode(false);   
        optionNode.id = this.id+"_"+props.options[i].label+"_container";                
        this.setOptionNodeProps(props.options[i], optionNode);

        // Append the li element to the menu element.        
        menuNode.appendChild(optionNode);
        if (props.options[i].group == true) {
            optionNode.group = true;
            groupNode = this.groupOptionContainer.cloneNode(false);     
            webui.@THEME@.common.setVisibleElement(groupNode, true); 
            menuNode.appendChild(groupNode);
            this.addOptions(props.options[i], groupNode, formId);
        }
         
        // Create callback function for onClick event.
        dojo.event.connect(optionNode, "onclick",
            webui.@THEME@.widget.menuBase.createOnClickCallback
            (this.id, optionNode.id, formId));
        
        if (props.options[i].separator == true) {
            separator = this.menuSeparatorContainer.cloneNode(true);
            if (webui.@THEME@.common.browser.is_ie5up) {
                var sep = this.menuSeparator.cloneNode(true);
                separator.appendChild(sep);
            }
            webui.@THEME@.common.setVisibleElement(separator, true);             
            menuNode.appendChild(separator);
        }
    }
    return true;    
}

/**
 * The callback function for clicking on a menu item.
 *
 * @param menuId The id of th menu widget.
 * @param optionId The id of the option element that is clicked
 * @param formId The id of the form element.
 */
webui.@THEME@.widget.menuBase.createOnClickCallback = function(menuId, optionId, formId) {
    // Return if the menu id or the option id is null.
    if (menuId == null || optionId == null) {
        return null;
    }
    
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(event) {
        // Get hold of the particular option element.
        var elem = document.getElementById(optionId);
        if (elem == null) {
            return;
        }        

        // Get hold of the menu element.
        var widget = dojo.widget.byId(menuId);                
        var val = elem.selectValue;
        var dis = elem.disabled;       
        var group = elem.group;
        
        // process the action only if the menu is not disabled and the selected
        // option is not a group header.
        if (!dis && !group) {
            widget.processOnClickEvent(val);
        }         
    }        
}

/**
 * Handles the on mouse out for each menuitem on IE
 */
webui.@THEME@.widget.menuBase.createOnMouseOutCallBack = function(menuItem) {
    if (menuItem == null) {
        return;
    }
    return function() {
        menuItem.className = webui.@THEME@.widget.props.menu.menuItemClassName;
    }
}

/**
 * Handles the on mouse over for each menuitem on IE
 */
webui.@THEME@.widget.menuBase.createOnMouseOverCallBack = function(menuItem) {
    if (menuItem == null) {
        return;
    }
    return function() {
        menuItem.className = menuItem.className + " " + 
            webui.@THEME@.widget.props.menu.ieHoverClassName;            
    }
}

/**
 * Calculate the maximum width of menu to be set.
 * If a menu if at all contains a group, then set the containsGroup
 * attribute to true. This will help in adding an extra pixel width
 * while assigning the width of th menu. This is to account for the
 * indentation of the menu.
 */
webui.@THEME@.widget.menuBase.getMaxWidth = function(props) {
    var menuWidth = 0;
    var maxWidth = 0;
    for (var i=0; i<props.length; i++) {
         menuWidth = props[i].label.length;
         if (menuWidth > maxWidth) {
            maxWidth = menuWidth;
         }
         if (props[i].group) {
            this.containsGroup = true;
            maxWidth = this.getMaxWidth(props[i].options);
         }
    }
    return maxWidth;
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
webui.@THEME@.widget.menuBase.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.menuBase.superclass.fillInTemplate.call(this, props, frag);

    // Set public functions.
    this.domNode.getSelectedValue = function(props, optionNode) { return dojo.widget.byId(this.id).getSelectedValue(); }
    this.domNode.submit = function(execute) { return dojo.widget.byId(this.id).submit(execute); } 
        
    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.menuBase.getProps = function() {
    var props = webui.@THEME@.widget.menuBase.superclass.getProps.call(this);
    if (this.options ) { props.options = this.options; }
    if (this.formId ) {props.formId = this.formId; }
    if (this.submitForm) {props.submitForm = this.submitForm; }  
    return props;
}

/**
 * Returns the currently selected item in the menu
 */
webui.@THEME@.widget.menuBase.getSelectedValue = function() {
    if (this.clickedItem) {
        return this.clickedItem;
    } else {
        return null;
    }
}

/**
 * This function is used to initialize the widget.
 *
 * Note: This is called after the fillInTemplate() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 * @param parent The parent of this widget.
 */
webui.@THEME@.widget.menuBase.initialize = function(props, frag, parent) {
    webui.@THEME@.widget.menuBase.superclass.initialize.call(this, props, frag, parent);

    // Try to set the width of the menu here.
    this.maxWidth = this.getMaxWidth(props.options);
     
    if (this.containsGroup) {
        this.maxWidth+=1;
    }

    // Account for images.
    this.maxWidth += 1;

    return true;
}

/**
 * This function executes the onchange and onclick event handlers if provided by the developer.
 * It then either submits the form if submitForm attribute is specified to true
 */
webui.@THEME@.widget.menuBase.processOnClickEvent = function(value) {
    var clickResult = true;
    var changeResult = true;
    if (this._onclick) {
        clickResult = (this._onclick) ? this._onclick() : true;
    }

    var x = this.getSelectedValue();
    var bool = (value == this.getSelectedValue());
    if (this._onchange && !bool) {    
        // If function returns false, we must prevent the request.       
        changeResult = (this._onchange) ? this._onchange() : true;
    }
    this.setSelectedValue(value);

    // Functions may sometime return without a value in which case the value
    // of the boolean variable may become undefined. 
    if (clickResult != false && changeResult != false) {
        if (this.submitForm) {
            this.submitMenuForm();
        }  
    }
    return true;
}

/**
 * Set the appropriate class name on the menu item container.
 * @param menuItemContainer The container for the menu item
 * @param props The properties of the particular option
 */
webui.@THEME@.widget.menuBase.setMenuNodeClassName = function(menuItemContainer, props) {
    if (new Boolean(props.group).valueOf() == true) {
        menuItemContainer.className = webui.@THEME@.widget.props.menu.menuHeaderClassName;
    } else if(new Boolean(props.disabled).valueOf() == true) {
        menuItemContainer.className = webui.@THEME@.widget.props.menu.disabledMenuItemClassName;
    } else {
        menuItemContainer.className = webui.@THEME@.widget.props.menu.menuItemClassName;        

        // Apply an hack for IE for mouse hover on the div element since div:hover type
        // of css declarations do not seem to work. onmouseover and onmouseout events
        // are attached with the div element and a style class is applied each time a
        // mouseover happens. This style represents the "hover" class.
        // Note that the "this" in these functions represent the menuItem's "div" element
        // and not the "menu" widget element.
        if (webui.@THEME@.common.browser.is_ie5up) {
            dojo.debug("Inside assignee functions");
            dojo.event.connect(menuItemContainer, "onmouseover",
                webui.@THEME@.widget.menuBase.createOnMouseOverCallBack(menuItemContainer));

            dojo.event.connect(menuItemContainer, "onmouseout",
                webui.@THEME@.widget.menuBase.createOnMouseOutCallBack(menuItemContainer));
        }
    }    
}

/**
 * Helper function to set the properties of an option item. This is invoked
 * by the addOptions function call.
 *
 * @param props The property of the particular option.
 * @param optionNode The node for which the option is to be added
 */
webui.@THEME@.widget.menuBase.setOptionNodeProps = function(props, optionNode) {
    optionNode.id = this.id + "_" + props.value + "_container";
    optionNode.selectValue = props.value;
    optionNode.disabled = props.disabled;
    var menuItemContainer = this.menuItemContainer.cloneNode(false);
    menuItemContainer.id = optionNode.id + "_label";

    // depending on the kind of node, assign the appropriate style
    // for the menu node.
    this.setMenuNodeClassName(menuItemContainer, props);
    optionNode.appendChild(menuItemContainer);

    // valueNode contains a div element which will hold the option.
    var valueNode = this.menuItemNode.cloneNode(false);
    valueNode.id = this.id + "_" + props.value;
        
    // Set label value.
    if (props.label) {
        this.widget.addFragment(valueNode, props.label, "last", props.escape);
    }    

    // Set title.
    if (props.title) {
        optionNode.title = props.title;
    }
    
    // By default have the no image container cloned and kept
    // If an image is present, then replace that with the span
    // placeholder for the image.
    var imageNode = this.menuItemNoImageContainer.cloneNode(false);
    if (props.image != null) {
        // Add the widget
        imageNode = this.menuItemImageContainer.cloneNode(false);
        props.image.className = webui.@THEME@.widget.props.menu.menuItemImageClassName;
        this.widget.addFragment(imageNode, props.image);
    } 
    menuItemContainer.appendChild(imageNode);

    // Append the placeholder image node.
    menuItemContainer.appendChild(this.menuItemSubMenu.cloneNode(false));

    // Append the div element to the li element.           
    menuItemContainer.appendChild(valueNode);
    return true;
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
webui.@THEME@.widget.menuBase.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // Replace options -- do not extend.
    if (props.options) {
        this.options = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.menuBase.superclass.setProps.call(this, props, notify);
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>accessKey</li>
 *  <li>className</li>
 *  <li>dir</li>
 *  <li>formId</li>
 *  <li>id</li>
 *  <li>onBlur</li>
 *  <li>options</li>
 *  <li>onChange</li>
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
 *  <li>style</li>
 * <li>submitForm</li>
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
webui.@THEME@.widget.menuBase._setProps = function(props){
    if (props == null) {
        return false;
    }
    
    // A web app devleoper could return false in order to cancel the 
    // submit. Thus, we will handle this event via the onClick call back.
    if (props.onChange) {
        
        // Set private function scope on widget.
        this._onchange = (typeof props.onChange == 'string')
            ? new Function(props.onChange) : props.onChange;

        // Must be cleared before calling setJavaScriptProps() below.
        props.onChange = null;
    }

    // A web app devleoper could return false in order to cancel the 
    // submit. Thus, we will handle this event via the onClick call back.
    if (props.onClick) {
        
        // Set private function scope on widget.
        this._onclick = (typeof props.onClick == 'string')
            ? new Function(props.onClick) : props.onClick;

        // Must be cleared before calling setJavaScriptProps() below.
        props.onClick = null;
    }

    // Need to account for the existing styles that are set.
    if(!props.style || (props.style.indexOf("width")) == -1) {
        if (!props.style) {
            props.style="width:"+this.maxWidth+"em;";
        } else {
            props.style+=";width:"+this.maxWidth+"em;";
        }        
    }

    // Add options
    if (props.options) {
        this.widget.removeChildNodes(this.outerMenuContainer);
        // Clone the menu node and add it to the outer container.
        var menuNode = this.groupOptionContainer.cloneNode(false);  
        webui.@THEME@.common.setVisibleElement(menuNode, true); 
        menuNode.className = webui.@THEME@.widget.props.menu.menuItemsClassName;
        this.outerMenuContainer.appendChild(menuNode);         
        this.addOptions(props, menuNode, props.formId);
    }
    
    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);
    
    // Set remaining properties.
    return webui.@THEME@.widget.menuBase.superclass._setProps.call(this, props);        
}

/**
 * Set the selected item on the widget.
 */
webui.@THEME@.widget.menuBase.setSelectedValue = function(item) {
    this.clickedItem = item;
    return true;
}

/**
 * Submits the form. Appends the value of the selected item in the request url.
 */
webui.@THEME@.widget.menuBase.submitMenuForm = function () {
    var theForm = document.getElementById(this.formId);
    var oldAction = theForm.action;
    var oldTarget = theForm.target;
       
    // Specify which option in the menu was clicked.
    theForm.action += "?" + this.id + "_submittedValue=" + this.getSelectedValue();
    theForm.target = "_self";
    theForm.submit();     
    theForm.action = oldAction;
    theForm.target = oldTarget;
    return false;
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.menuBase, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.menuBase, {
    // Set private functions.
    addOptions: webui.@THEME@.widget.menuBase.addOptions,
    fillInTemplate: webui.@THEME@.widget.menuBase.fillInTemplate,
    getMaxWidth: webui.@THEME@.widget.menuBase.getMaxWidth,
    getProps: webui.@THEME@.widget.menuBase.getProps,
    getSelectedValue:webui.@THEME@.widget.menuBase.getSelectedValue,
    initialize: webui.@THEME@.widget.menuBase.initialize,
    processOnClickEvent: webui.@THEME@.widget.menuBase.processOnClickEvent,
    setMenuNodeClassName: webui.@THEME@.widget.menuBase.setMenuNodeClassName,
    setOptionNodeProps:webui.@THEME@.widget.menuBase.setOptionNodeProps,
    setProps: webui.@THEME@.widget.menuBase.setProps,
    _setProps: webui.@THEME@.widget.menuBase._setProps,
    setSelectedValue:webui.@THEME@.widget.menuBase.setSelectedValue,
    submitMenuForm:webui.@THEME@.widget.menuBase.submitMenuForm

    // Set defaults.
});
