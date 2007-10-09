// widget/menuBase.js
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

/**
 * @name widget/menuBase.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the menuBase widget.
 */
dojo.provide("webui.@THEME@.widget.menuBase");

dojo.require("webui.@THEME@.browser");
dojo.require("webui.@THEME@.common");
dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.menuBase
 * @inherits webui.@THEME@.widget.widgetBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.menuBase", webui.@THEME@.widget.widgetBase);

/**
 * Add the specified options to the dom element.
 *
 * @param {Node} menuNode The node to which the menu items are to be added.
 * @param {Object} props Key-Value pairs of properties.
 * @config {Array} [options] 
 */
webui.@THEME@.widget.menuBase.prototype.addOptions = function(menuNode, props) {
    var groupNode, optionNode, separator, sepNode;
    for (var i = 0; i < props.options.length; i++) {
        
        // create an li node which will represent an option element.
        optionNode = this.optionContainer.cloneNode(false);   
        optionNode.id = this.id + "_" + props.options[i].label + "_container";                
        this.setOptionNodeProps(optionNode, props.options[i]);

        // Append the li element to the menu element.        
        menuNode.appendChild(optionNode);
        if (props.options[i].group == true) {
            optionNode.group = true;
            groupNode = this.groupOptionContainer.cloneNode(false);
            menuNode.appendChild(groupNode);
            this.addOptions(groupNode, props.options[i]);
        }
         
        // Create callback function for onClick event.
        dojo.connect(optionNode, "onclick", this.createOnClickCallback(
            optionNode.id));
        
        if (props.options[i].separator == true) {
            separator = this.menuSeparatorContainer.cloneNode(true);
            if (webui.@THEME@.browser.isIe5up()) {
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
 * @param {String} optionId The id of the option element that is clicked.
 */
webui.@THEME@.widget.menuBase.prototype.createOnClickCallback = function(optionId) {
    var _id = this.id;

    /**
     * New literals are created every time this function is called, and it's 
     * saved by closure magic.
     *
     * @param {Event} event The JavaScript event.
     */
    return function(event) {
        // Get hold of the particular option element.
        var elem = document.getElementById(optionId);
        if (elem == null) {
            return;
        }        

        // Get hold of the menu element.
        var widget = dijit.byId(_id);                
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
 * Handles the on mouse out for each menuitem.
 *
 * @param {Node} menuItem The DOM node associated with the menu item.
 */
webui.@THEME@.widget.menuBase.prototype.createOnMouseOutCallBack = function(menuItem) {
    if (menuItem == null) {
        return null;
    }
    var _id = this.id;
 
    /**
     * New literals are created every time this function is called, and it's 
     * saved by closure magic.
     *
     * @param {Event} event The JavaScript event.
     */
    return function(event) {
        var widget = dijit.byId(_id);
        menuItem.className = widget.theme.getClassName("MENU_GROUP_CONTAINER");
    }
}

/**
 * Handles the on mouse over for each menuitem.
 *
 * @param {Node} menuItem The DOM node associated with the menu item.
 */
webui.@THEME@.widget.menuBase.prototype.createOnMouseOverCallBack = function(menuItem) {
    if (menuItem == null) {
        return null;
    }
    var _id = this.id;

    /**
     * New literals are created every time this function is called, and it's 
     * saved by closure magic.
     *
     * @param {Event} event The JavaScript event.
     */
    return function(event) {
        var widget = dijit.byId(_id);
        menuItem.className = menuItem.className + " " + 
            widget.theme.getClassName("MENU_ITEM_HOVER");            
    }
}

/**
 * Calculate the maximum width of menu to be set.
 * If a menu if at all contains a group, then set the containsGroup
 * attribute to true. This will help in adding an extra pixel width
 * while assigning the width of th menu. This is to account for the
 * indentation of the menu.
 *
 * @param {Array} props 
 */
webui.@THEME@.widget.menuBase.prototype.getMaxWidth = function(props) {
    var menuWidth = 0;
    var maxWidth = 0;
    for (var i = 0; i < props.length; i++) {
         menuWidth = props[i].label.length;
         if (menuWidth > maxWidth) {
            maxWidth = menuWidth;
         }
         if (props[i].image != null) {
            this.hasImage = true;
         }
         if (props[i].group) {
            this.containsGroup = true;
            maxWidth = this.getMaxWidth(props[i].options);
         }
    }
    return maxWidth;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.menuBase.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    if (this.options) { props.options = this.options; }
    if (this.formId) { props.formId = this.formId; }
    if (this.submitForm) { props.submitForm = this.submitForm; }  

    return props;
}

/**
 * Returns the currently selected item in the menu
 */
webui.@THEME@.widget.menuBase.prototype.getSelectedValue = function() {
    if (this.clickedItem) {
        return this.clickedItem;
    } else {
        return null;
    }
}

/**
 * This function is used to obtain the outermost HTML element style.
 * <p>
 * Note: Styles should be concatinated in order of precedence (e.g., the 
 * user's style property is always appended last).
 * </p>
 */
webui.@THEME@.widget.menuBase.prototype.getStyle = function() {
    var style = "width:" + this.maxWidth + "em;";

    // Since the style has to be recalculated each and every time the options
    // are changed, you cannot rely on the the existing style on the dom element
    // to see whether style was already defined. Hence use a separate variable
    // which sets itself to true if the widget sets the width/
    if (!this.widthSet) {
    	var st = this.style;
	var reg = ";?[\\t]*width[\\t]*:";
	if (st != null) {
	    var res = st.match(reg);

            // Return user's style if width is set already.
            if (this.style && res != null) {
                return this.style;
            }
	}
    }
    this.widthSet = true;
    return (this.style)
        ? style + this.style
        : style;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 */
webui.@THEME@.widget.menuBase.prototype.postCreate = function () {
    // Set public functions.
    this.domNode.getSelectedValue = function(props, optionNode) { return dijit.byId(this.id).getSelectedValue(); }
        
    return this.inherited("postCreate", arguments);
}

/**
 * This function executes the onchange and onclick event handlers if provided by 
 * the developer. It then either submits the form if submitForm attribute is 
 * specified to true.
 *
 * @param {String} value The selected value.
 */
webui.@THEME@.widget.menuBase.prototype.processOnClickEvent = function(value) {
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
            this.submitFormData();
        }  
    }
    return true;
}

/**
 * Set the appropriate class name on the menu item container.
 *
 * @param {Node} menuItemContainer The container for the menu item.
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} [disabled] 
 * @config {boolean} [group] 
 */
webui.@THEME@.widget.menuBase.prototype.setMenuNodeClassName = function(
        menuItemContainer, props) {
    if (new Boolean(props.group).valueOf() == true) {
        menuItemContainer.className = this.theme.getClassName("MENU_GROUP_HEADER");
    } else if (new Boolean(props.disabled).valueOf() == true) {
        menuItemContainer.className = this.theme.getClassName("MENU_ITEM_DISABLED");
    } else {
        menuItemContainer.className = this.theme.getClassName("MENU_GROUP_CONTAINER");        

        // Apply an hack for IE for mouse hover on the div element since div:hover type
        // of css declarations do not seem to work. onmouseover and onmouseout events
        // are attached with the div element and a style class is applied each time a
        // mouseover happens. This style represents the "hover" class.
        // Note that the "this" in these functions represent the menuItem's "div" element
        // and not the "menu" widget element.
        if (webui.@THEME@.browser.isIe5up()) {
            dojo.connect(menuItemContainer, "onmouseover",
                this.createOnMouseOverCallBack(menuItemContainer));
            dojo.connect(menuItemContainer, "onmouseout",
                this.createOnMouseOutCallBack(menuItemContainer));
        }
    }    
}

/**
 * Helper function to set the properties of an option item. This is invoked
 * by the addOptions function call.
 *
 * @param optionNode The node for which the option is to be added.
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} [disabled] 
 * @config {boolean} [escape]
 * @config {Object} [image]
 * @config {String} [label]
 * @config {String} [title]
 * @config {String} [value]
 */
webui.@THEME@.widget.menuBase.prototype.setOptionNodeProps = function(optionNode, props) {
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
    if (new Boolean(this.hasImage).valueOf() == true) {
        var imageNode = this.menuItemNoImageContainer.cloneNode(false);
        if (props.image != null) {
            // Add the widget
            imageNode = this.menuItemImageContainer.cloneNode(false);
            props.image.className = this.theme.getClassName("MENU_ITEM_IMAGE");
            this.widget.addFragment(imageNode, props.image);
        } 
        menuItemContainer.appendChild(imageNode);
    }
    
    // Append the placeholder image node.
    menuItemContainer.appendChild(this.menuItemSubMenu.cloneNode(false));

    // Append the div element to the li element.           
    menuItemContainer.appendChild(valueNode);
    return true;
}

/**
 * This function is used to set widget properties using Object literals.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} [accessKey] 
 * @config {String} [className] CSS selector.
 * @config {String} [dir] Specifies the directionality of text.
 * @config {String} [formId] 
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {String} [onBlur] Element lost focus.
 * @config {String} [onClick] Mouse button is clicked on element.
 * @config {String} [onDblClick] Mouse button is double-clicked on element.
 * @config {String} [onFocus] Element received focus.
 * @config {String} [onKeyDown] Key is pressed down over element.
 * @config {String} [onKeyPress] Key is pressed and released over element.
 * @config {String} [onKeyUp] Key is released over element.
 * @config {String} [onMouseDown] Mouse button is pressed over element.
 * @config {String} [onMouseOut] Mouse is moved away from element.
 * @config {String} [onMouseOver] Mouse is moved onto element.
 * @config {String} [onMouseUp] Mouse button is released over element.
 * @config {String} [onMouseMove] Mouse is moved while over element.
 * @config {Array} [options] 
 * @config {boolean} [primary] Set button as primary if true.
 * @config {String} [style] Specify style rules inline.
 * @config {int} [tabIndex] Position in tabbing order.
 * @config {String} [title] Provides a title for element.
 * @config {boolean} [visible] Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.menuBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // Replace options -- do not extend.
    if (props.options) {
        this.options = null;
    }

    // Extend widget object for later updates.
    return this.inherited("setProps", arguments);
}

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This is considered a private API, do not use. This function should only
 * be invoked via setProps().
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @private
 */
webui.@THEME@.widget.menuBase.prototype._setProps = function(props){
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
    
    // Add options
    if (props.options) {
        
        // Try to set the width of the menu here.
        this.maxWidth = this.getMaxWidth(props.options);        
        
        // Account for image width if one exists. This property can be got from the
	// theme
        if (this.hasImage) {
            var placeHolderWidth = parseFloat(this.theme.getMessage("Menu.placeHolderImageWidth"));
            this.maxWidth += placeHolderWidth; 
        }       
   
        this.widget.removeChildNodes(this.outerMenuContainer);
        
        // Clone the menu node and add it to the outer container.
        var menuNode = this.groupOptionContainer.cloneNode(false);
        menuNode.className = this.theme.getClassName("MENU_CONTAINER");
        this.outerMenuContainer.appendChild(menuNode);         
        this.addOptions(menuNode, props);
    }

    // Need to redo style calculation if style or options
    // have been specified.
    if (props.style || props.options) {
        props.style = this.getStyle();
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);
    
    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}

/**
 * Set the selected item on the widget.
 *
 * @param {String} item The selected value.
 */
webui.@THEME@.widget.menuBase.prototype.setSelectedValue = function(item) {
    this.clickedItem = item;
    return true;
}

/**
 * Submits the form. Appends the value of the selected item in the request url.
 */
webui.@THEME@.widget.menuBase.prototype.submitFormData = function () {
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
