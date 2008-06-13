/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.menuBase");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.menuBase
 * @extends @JS_NS@.widget._base.widgetBase
 * @class This class contains functions for widgets that extend menuBase.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accessKey 
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {String} formId 
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} onBlur Element lost focus.
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onFocus Element received focus.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {Array} options 
 * @config {boolean} primary Set button as primary if true.
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.menuBase",
        @JS_NS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this._focusPosition = 0;
    }
});

/**
 * Add the specified options to the dom element.
 *
 * @param {Node} menuNode The node to which the menu items are to be added.
 * @param {Object} props Key-Value pairs of properties.
 * @config {Array} options 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._addOptions = function(menuNode, props) {
    var groupNode, optionNode, separator, sepNode;
    for (var i = 0; i < props.options.length; i++) {
        
        // create an li node which will represent an option element.
        optionNode = this._optionContainer.cloneNode(false);   
        optionNode.id = this.id + "_" + props.options[i].label + "_container";                
        this._setOptionNodeProps(optionNode, props.options[i], i);

        // Append the li element to the menu element.        
        menuNode.appendChild(optionNode);
        if (props.options[i].group == true) {
            optionNode.group = true;
            groupNode = this._groupOptionContainer.cloneNode(false);
            menuNode.appendChild(groupNode);
            this._addOptions(groupNode, props.options[i]);
        }
        
        if (props.options[i].separator == true) {
            separator = this._menuSeparatorContainer.cloneNode(true);
            if (@JS_NS@._base.browser._isIe5up()) {
                var sep = this._menuSeparator.cloneNode(true);
                separator.appendChild(sep);
            }
            this._common._setVisibleElement(separator, true);             
            menuNode.appendChild(separator);
        }
    }
    return true;    
};

/**
 * The callback function for clicking on a menu item.
 *
 * @param {String} optionId The id of the option element that is clicked.
 * @return {Function} The callback function.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._createOnClickCallback = function(optionId) {
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
        var widget = @JS_NS@.widget.common.getWidget(_id);                
        var val = elem.selectValue;
        var dis = elem.disabled;       
        var group = elem.group;
        
        // process the action only if the menu is not disabled and the selected
        // option is not a group header.
        if (!dis && !group) {
            widget._processOnClickEvent(val);
        }         
    };
};

/**
 * The callback function for key press on a menu item.
 *
 * @param {String} nodeId The id of the option element that is clicked
 * @return {Function} The callback function.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._createOnKeyDownCallBack = function(nodeId) {
    if (nodeId == null) {
        return null;
    }
    var id = this.id;
    return function(event) {
        var elem = document.getElementById(nodeId);
        if (elem == null) {
            return false;
        }
        var common = @JS_NS@.widget.common;
        var widget = common.getWidget(id);

        // If the menu is not visible, we do not need to capture
        // key press events.
        if (!@JS_NS@._base.common._isVisibleElement(widget.domNode)) {
            return false;
        }
        event = common._getEvent(event);
        var keyCode = common._getKeyCode(event);
        
        // if onkeypress returns false, we do not traverse the menu.
        var keyPressResult = true;
        if (widget._onkeypress) {
            keyPressResult = (widget._onkeypress) ? widget._onkeypress() : true;
        }
        if (keyPressResult != false) {
            widget._traverseMenu(keyCode, event, nodeId);
        }
        return true;
    };
};

/**
 * Handles the on mouse out for each menuitem.
 *
 * @param {Node} menuItem The DOM node associated with the menu item.
 * @return {Function} The callback function.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._createOnMouseOutCallBack = function(menuItem) {
    if (menuItem == null) {
        return null;
    }
    var _id = this.id;
 
    // New literals are created every time this function is called, and it's 
    // saved by closure magic.
    return function(event) {
        var widget = @JS_NS@.widget.common.getWidget(_id);
        menuItem.className = widget._theme.getClassName("MENU_GROUP_CONTAINER");
    };
};

/**
 * Handles the on mouse over for each menuitem.
 *
 * @param {Node} menuItem The DOM node associated with the menu item.
 * @return {Function} The callback function.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._createOnMouseOverCallBack = function(menuItem) {
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
        var widget = @JS_NS@.widget.common.getWidget(_id);
        menuItem.className = menuItem.className + " " + 
            widget._theme.getClassName("MENU_ITEM_HOVER");            
        if (widget != null) {
            // Mozilla browser (not firefox/seamonkey) do not support focus/blur
            // for divs                
            if (document.getElementById(widget.menuId[widget._focusPosition]).blur) {
                document.getElementById(widget.menuId[widget._focusPosition]).blur();
            }                
            if (!(menuItem.id == widget.menuId[widget._focusPosition])) {
                document.getElementById(widget.menuId[widget._focusPosition]).className =
                    widget._theme.getClassName("MENU_GROUP_CONTAINER");
            }
        }
        if (@JS_NS@._base.browser._isIe5up() ) {
            menuItem.className = menuItem.className + " " + 
                widget._theme.getClassName("MENU_ITEM_HOVER");            
        }
    };
};

/**
 * Calculate the maximum width of menu to be set.
 * If a menu if at all contains a group, then set the containsGroup
 * attribute to true. This will help in adding an extra pixel width
 * while assigning the width of th menu. This is to account for the
 * indentation of the menu.
 *
 * @param {Array} props 
 * @return {int} The max menu width.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._getMaxWidth = function(props) {
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
            maxWidth = this._getMaxWidth(props[i].options);
         }
    }
    return maxWidth;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget._base.menuBase.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    if (this.options != null) { props.options = this.options; }
    if (this.formId != null) { props.formId = this.formId; }
    if (this.submitForm != null) { props.submitForm = this.submitForm; }  

    return props;
};

/**
 * Returns the currently selected item in the menu.
 *
 * @return {String} The selected item.
 */
@JS_NS@.widget._base.menuBase.prototype.getSelectedValue = function() {
    if (this.clickedItem) {
        return this.clickedItem;
    } else {
        return null;
    }
};

/**
 * This function is used to obtain the outermost HTML element style.
 * <p>
 * Note: Styles should be concatinated in order of precedence (e.g., the 
 * user's style property is always appended last).
 * </p>
 * @return {String} The outermost HTML element style.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._getStyle = function() {
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
};

/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._postCreate = function () {
    // Set public functions.

    /** @ignore */
    this._domNode.getSelectedValue = function(props, optionNode) { return @JS_NS@.widget.common.getWidget(this.id).getSelectedValue(); };
        
    return this._inherited("_postCreate", arguments);
};

/**
 * Process the enter key press event.Evaluvate the keyPress/keyDown (for non-IE/IE browsers)
 * and traverse through the menu. Also, if onChange is specified, evaluvate that and 
 * submit the form if submitForm is specified to true.
 *
 * @param (String) value The "value" of the selected option. 
 * @return {boolean} true The enter key press event completed successfully
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._processEnterKeyPressEvent = function(value) {
    var changeResult = true;

    // Check whether the selected value is different than the one previously selected
    var bool = (value == this.getSelectedValue());
    this._setSelectedValue(value);

    if (this._onchange && !bool) {    
        // If function returns false, we must prevent the request.       
        changeResult = (this._onchange) ? this._onchange() : true;
    }
    
    // Functions may sometime return without a value in which case the value
    // of the boolean variable may become undefined. 
    if (changeResult != false) {
        if (this.submitForm == true) {
            this._submitFormData();
        }  
    }
    return true;
};
    
/**
 * This function executes the onchange and onclick event handlers if provided by 
 * the developer. It then either submits the form if submitForm attribute is 
 * specified to true.
 *
 * @param {String} value The selected value.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._processOnClickEvent = function(value) {
    var clickResult = true;
    var changeResult = true;
    
    //Check if the selected value has changed from the previous selection.
    var bool = (value == this.getSelectedValue());
    this._setSelectedValue(value);

    if (this._onclick) {
        clickResult = (this._onclick) ? this._onclick() : true;
    }
    if (this._onchange && !bool) {    
        // If function returns false, we must prevent the request.       
        changeResult = (this._onchange) ? this._onchange() : true;
    }

    // Functions may sometime return without a value in which case the value
    // of the boolean variable may become undefined. 
    if (clickResult != false && changeResult != false) {
        if (this.submitForm == true) {
            this._submitFormData();
        }  
    }
    return true;
};

/**
 * Set the appropriate class name on the menu item container.
 *
 * @param {Node} menuItemContainer The container for the menu item.
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} disabled 
 * @config {boolean} group
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._setMenuNodeClassName = function(
        menuItemContainer, props) {        
    if (new Boolean(props.group).valueOf() == true) {
        menuItemContainer.className = this._theme.getClassName("MENU_GROUP_HEADER");
    } else if (new Boolean(props.disabled).valueOf() == true) {
        menuItemContainer.className = this._theme.getClassName("MENU_ITEM_DISABLED");
    } else {
        menuItemContainer.className = this._theme.getClassName("MENU_GROUP_CONTAINER");        

        // Whenever mouse over/out happens, focus must be set on the menu accordingly.
        // Apply an hack for IE for mouse hover on the div element since div:hover type
        // of css declarations do not seem to work. onmouseover and onmouseout events
        // are attached with the div element and a style class is applied each time a
        // mouseover happens. This style represents the "hover" class.
        // Note that the "this" in these functions represent the menuItem's "div" element
        // and not the "menu" widget element.
        this._dojo.connect(menuItemContainer, "onmouseover",
            this._createOnMouseOverCallBack(menuItemContainer));
        this._dojo.connect(menuItemContainer, "onmouseout",
            this._createOnMouseOutCallBack(menuItemContainer));
    }
    return true;
};

/**
 * Helper function to set the properties of an option item. This is invoked
 * by the _addOptions function call.
 *
 * @param optionNode The node for which the option is to be added.
 * @param {Object} props Key-Value pairs of properties.
 * @param {String} number The position of the option item in the menu.
 * @config {boolean} disabled 
 * @config {boolean} escape
 * @config {Object} image
 * @config {String} label
 * @config {String} title
 * @config {String} value
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._setOptionNodeProps = function(optionNode, props, number) {
    optionNode.id = this.id + "_" + props.value + "_container";
    var menuItemContainer = this._menuItemContainer.cloneNode(false);
    menuItemContainer.id = optionNode.id + "_label";

    // depending on the kind of node, assign the appropriate style
    // for the menu node.
    this._setMenuNodeClassName(menuItemContainer, props);
    optionNode.appendChild(menuItemContainer);
    
    // valueNode contains a div element which will hold the option.
    var valueNode = this._menuItemNode.cloneNode(false);  
    valueNode.id = this.id + "_" + props.value;

    if (!(new Boolean(props.group).valueOf() == true) && 
            !(new Boolean(props.disabled).valueOf() == true)) {
        this.menuId[this.menuItemCount++] = menuItemContainer.id;   
    }

    menuItemContainer.tabIndex = -1;
    valueNode.tabIndex = -1;

    menuItemContainer.selectValue = props.value;
    menuItemContainer.disabled = props.disabled;
    menuItemContainer.group = props.group;
    if (props.title != null) {
        menuItemContainer.title = props.title;
        valueNode.title = props.title;
    }
    
    if (valueNode.setAttributeNS) {
        valueNode.setAttributeNS(
            "http://www.w3.org/2005/07/aaa", "posinset", number);
    }

    if (valueNode.setAttributeNS) {
        valueNode.setAttributeNS(
            "http://www.w3.org/2005/07/aaa", "disabled", props.disabled);
    }
        
    // Create callback function for onkeydown event.
    this._dojo.connect(menuItemContainer, "onkeydown", 
        this._createOnKeyDownCallBack(menuItemContainer.id));         
        
    // Create callback function for onClick event.
    this._dojo.connect(menuItemContainer, "onclick",
        this._createOnClickCallback(menuItemContainer.id));
        
    // Set label value.
    if (props.label) {
        this._widget._addFragment(valueNode, props.label, "last", props.escape);
    }    

    // Set title.
    if (props.title != null) {
        optionNode.title = props.title;
    }
    
    // By default have the no image container cloned and kept
    // If an image is present, then replace that with the span
    // placeholder for the image.
    if (new Boolean(this.hasImage).valueOf() == true) {
        var imageNode = this._menuItemNoImageContainer.cloneNode(false);
        if (props.image != null) {
            // Add the widget
            imageNode = this._menuItemImageContainer.cloneNode(false);
            props.image.className = this._theme.getClassName("MENU_ITEM_IMAGE");
            this._widget._addFragment(imageNode, props.image);
        } 
        menuItemContainer.appendChild(imageNode);
    }
    
    // Append the placeholder image node.
    menuItemContainer.appendChild(this._menuItemSubMenu.cloneNode(false));

    // Append the div element to the li element.           
    menuItemContainer.appendChild(valueNode);
    return true;
};

/**
 * This function is used to set widget properties using Object literals. Please
 * see the constructor detail for a list of supported properties.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.menuBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }
    
    // Replace options -- do not extend.
    if (props.options) {
        this.options = null;
    }

    // Extend widget object for later updates.
    return this._inherited("setProps", arguments);
};

/**
 * This function is used to set widget properties. Please see the constructor 
 * detail for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._setProps = function(props){
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
        this.maxWidth = this._getMaxWidth(props.options);        
        
        // Account for image width if one exists. This property can be got from the
	// theme
        if (this.hasImage) {
            var placeHolderWidth = parseFloat(this._theme.getMessage("Menu.placeHolderImageWidth"));
            this.maxWidth += placeHolderWidth; 
        }       
   
        // If an menuGroup exists, then add one character width. Otherwise menu
        // does not scale properly
        if (this.containsGroup) {
            this.maxWidth += 1;
        }
             
        this._widget._removeChildNodes(this._outerMenuContainer);
        this.menuId = [];
        this.menuItemCount = 0;
        
        // Clone the menu node and add it to the outer container.
        var menuNode = this._groupOptionContainer.cloneNode(false);
        menuNode.className = this._theme.getClassName("MENU_CONTAINER");
        this._outerMenuContainer.appendChild(menuNode);         
        this._addOptions(menuNode, props);
    }

    // Need to redo style calculation if style or options
    // have been specified.
    if (props.style || props.options) {
        props.style = this._getStyle();
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);
    
    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * Set the selected item on the widget.
 *
 * @param {String} item The selected value.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._setSelectedValue = function(item) {
    this.clickedItem = item;
    return true;
};

/**
 * Submits the form. Appends the value of the selected item in the request url.
 *
 * @return {boolean} false to cancel the JavaScript event.
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._submitFormData = function () {
    if (this.formId == null) {
        return false;
    }
    var form = document.getElementById(this.formId);
    if (form == null) {
        return false;
    }
    var oldAction = form.action;
    var oldTarget = form.target;

    // Set new action URL.
    var prefix;
    if (form.action) {
        prefix = (form.action.indexOf("?") == -1) ? "?" : "&";
        form.action += prefix + this.id + "_submittedValue=" + this.getSelectedValue();
    }

    form.target = "_self";
    form.submit();     
    form.action = oldAction;
    form.target = oldTarget;
    return false;
};

/**
 * This function takes care of traversing through the menu items depending
 * on which key is pressed.
 *
 * @param (String) keyCode The valye of the key which was pressed
 * @param (Event) event The key press event.
 * @param (String) nodeId The id of the menu item. 
 * @return {boolean} true Propagate the javascript event
 * @private
 */
@JS_NS@.widget._base.menuBase.prototype._traverseMenu = function(keyCode, event, nodeId) {
    var arr = this.menuId;
    var elem = document.getElementById(nodeId);
    var focusElem = document.getElementById(arr[this._focusPosition]);
    
    if (focusElem.blur) {
        focusElem.blur();
    }
    // Operations to be performed if the arrow keys are pressed.
    if (keyCode >= 37 && keyCode <= 40) {
        
        // Check whether up arrow was pressed.
        if (keyCode == 38) {
            focusElem.className = this._theme.getClassName("MENU_GROUP_CONTAINER");
            this._focusPosition--;
            if (this._focusPosition < 0) {
                this._focusPosition = arr.length-1;
            }
            focusElem = document.getElementById(arr[this._focusPosition]); 

        // Check whether down arrow was pressed
        } else if (keyCode == 40) {
            focusElem.className = 
                this._theme.getClassName("MENU_GROUP_CONTAINER");
            this._focusPosition++;
            if (this._focusPosition == arr.length) {
                this._focusPosition = 0;
            }
            focusElem = document.getElementById(arr[this._focusPosition]);
        }   
        
    if (focusElem.focus) {
        focusElem.focus();
    }        
        focusElem.className = focusElem.className + " "+
            this._theme.getClassName("MENU_FOCUS");           
    // Check if enter key was pressed    
    } else if(keyCode == 13){
        focusElem.className =
            this._theme.getClassName("MENU_GROUP_CONTAINER");
        var val = elem.selectValue;
        this._processEnterKeyPressEvent(val);
       
    }    
    if (@JS_NS@._base.browser._isIe5up()) {
        window.event.cancelBubble = true;
        window.event.returnValue = false;
    } else {
        event.stopPropagation();
        event.preventDefault();
    }
    return true;
};
