// widget/selectBase.js
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

dojo.provide("webui.@THEME@.widget.selectBase");

dojo.require("webui.@THEME@.browser");
dojo.require("webui.@THEME@.common");

/**
 * @name webui.@THEME@.widget.selectBase
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for widgets that extend selectBase.
 * @static
 */
dojo.declare("webui.@THEME@.widget.selectBase", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    labelOnTop: false,
    titleOptionLabel: "" // Overridden by subclass.
});

/**
 * Helper function to add option and optgroup elements to the HTML select element.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {Array} [options]
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.addOptions = function(props) {
    var numChildNodes = this.listContainer.options.length;

    // Start with a clean slate.
    //
    // Note: this has to be done. Otherwise, you'll get blank entries in the 
    // drop down for the original empty dojo attach point nodes.
    if (!this.alreadyRemoved) {
        this.listContainer.removeChild(this.optionNode); 
        this.optionGroupContainer.removeChild(this.memberOptionNode);
        this.listContainer.removeChild(this.optionGroupContainer);
        this.alreadyRemoved = true;
    }

    // Cleaning up the old options
    while (this.listContainer.firstChild) {
        this.listContainer.removeChild(this.listContainer.firstChild);
    }

    var thisNode;
    for (var i = 0; i < props.options.length; i++) {
        var pOption = props.options[i];
        if (pOption.group == false) {
            // For some reason, ie is prone to painting problems (esp. after a 
            // style change) when using the DOM to populate options, but 
            // apparently not when the options are in a group
            
            if (webui.@THEME@.browser.isIe()) {
                thisNode = new Option();
            } else {
                thisNode = this.optionNode.cloneNode(true);
            }
            
            // Set the properties on this option element
            this.setOptionProps(thisNode, pOption);
            
            // Append this option node to the select
            if (webui.@THEME@.browser.isIe()) {
                var idx = this.listContainer.options.length;
                var isSelected = thisNode.selected;
                this.listContainer.options[idx] = thisNode;
                //explicitly set the selected property again!
                //this is necessary to work around a defect in some versions of IE6
                this.listContainer.options[idx].selected = isSelected;
            } else {
                this.listContainer.appendChild(thisNode);
            }
        } else { // group option optgroup
            thisNode = this.optionGroupContainer.cloneNode(true);
            
            // Set the properties on this optgroup element
            this.setGroupOptionProps(thisNode, pOption);
            
            // Append this optgroup node to the select
            this.listContainer.appendChild(thisNode);

            // Add the option elements to this group
            var thisSubNode;
            for (var ix = 0; ix < pOption.options.length; ix++) {
                thisSubNode = this.memberOptionNode.cloneNode(true);
                this.setOptionProps(thisSubNode, pOption.options[ix]);
                thisNode.appendChild(thisSubNode); 
            }
        }
    }
    return true;
}

/**
 * Helper function called by onChange event to set the selected and disabled
 * styles.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.changed = function() { 
    var options = this.listContainer.options;

    if (webui.@THEME@.browser.isIe()) { 
        for (var i = 0; i < options.length; ++i) {
            if (options[i].disabled == true && options[i].selected == true) {
                if (this.listContainer.multiple == true) {
                    options[i].selected = false;
                } else {
                    this.listContainer.selectedIndex = -1;
                }
            }
        }  
    }        
    for (var i = 0; i < options.length; ++i) { 
        options[i].className = this.getOptionClassName(options[i]);
    }
    return true; 
}

/**
 * Helper function to obtain class name for the option element
 *
 * @param {Object} option Key-Value pairs of properties.
 * @return {String} The HTML option element class name.
 */
webui.@THEME@.widget.selectBase.prototype.getOptionClassName = function(option) {
    // Set default style.
    if (option.separator && option.separator == true) {
        return this.optionSeparatorClassName;
    } else if (option.group && option.group == true) {
        return this.optionGroupClassName;
    } else if (option.disabled && option.disabled == true) {
        return this.optionDisabledClassName;
    } else if (option.selected && option.selected == true) {
        return this.optionSelectedClassName;
    } else {
        return this.optionClassName;
    }
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.selectBase.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Get properties.
    if (this.labelOnTop != null) { props.labelOnTop = this.labelOnTop; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.label ) { props.label = this.label; }
    if (this.options ) { props.options = this.options; }

    return props;
}

/**
 * Helper function to obtain class name for the HTML select element.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} [disabled] true if disabled.
 * @return {String} The HTML select element class name.
 */
webui.@THEME@.widget.selectBase.prototype.getSelectClassName = function(props) {    
    return (props.disabled == true)
        ? this.selectDisabledClassName
        : this.selectClassName;
}

/**
 * Returns the HTML select element that makes up the listbox.
 *
 * @return {Node} The HTML select element.
 */
webui.@THEME@.widget.selectBase.prototype.getSelectElement = function() {
    return this.listContainer;
}

/**
 * To get the label of the first selected option on the listbox. 
 * If no option is selected, this function returns null.
 * 
 * @return The label of the selected option, or null if none is selected. 
 * @return {String} The label of the selected option.
 */
webui.@THEME@.widget.selectBase.prototype.getSelectedLabel = function() { 
    var index = this.listContainer.selectedIndex; 

    if (index == -1) { 
        return null; 
    } else { 
        return this.options[index].label;
    }
}

/**
 * To get the value of the first selected option on the listbox. 
 * If no option is selected, this function returns null. 
 *
 * @return {String} The selected option value or null if none is selected. 
 */
webui.@THEME@.widget.selectBase.prototype.getSelectedValue = function() { 
    var index = this.listContainer.selectedIndex; 
    if (index == -1) { 
        return null; 
    } else { 
        return this.listContainer.options[index].value; 
    }
}


/**
 * Helper function to create callback for onChange event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.onChangeCallback = function(event) {
    if (this.disabled == true) {
        return false;
    }

    // If function returns false, we must prevent the auto-submit.
    var result = (this.listContainer._onchange)
        ? this.listContainer._onchange(event) : true;
    if (result == false) {
        return false;
    }

    // Set style classes.
    return this.changed();
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.labelContainer.id = this.id + "_label";
        this.listContainer.id = this.id + "_list";
    }

    // Set public functions.
    this.domNode.getSelectedValue = function() { return dijit.byId(this.id).getSelectedValue(); }
    this.domNode.getSelectedLabel = function() { return dijit.byId(this.id).getSelectedLabel(); }
    this.domNode.getSelectElement = function() { return dijit.byId(this.id).getSelectElement(); }

    // Set events.
    dojo.connect(this.listContainer, "onchange", this, "onChangeCallback");

    return this.inherited("postCreate", arguments);
}

/**
 * Helper function to set properties on optgroup element.
 *
 * @param {Node} element The optgroup DOM node
 * @param {Object} option Key-Value pairs of properties for the optgroup node.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.setGroupOptionProps = function(element, option) {
    element.className = this.getOptionClassName(option);
    element.label = option.label;
  
    if (option.disabled != null) {
        element.disabled = new Boolean(option.disabled).valueOf();
    }
    return true;
}

/**
 * Helpper function to set properties on the option element.
 *
 * @param {Node} element The option DOM node
 * @param {Object} option Key-Value pairs of properties for the option node.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.setOptionProps = function(element, option) {
    element.value = option.value;
    element.className = this.getOptionClassName(option);

    var textToUse = null;
    if (option.isTitle == true) {
       // Prepend and append long dashes with the title label
       textToUse = this.theme.getMessage(this.titleOptionLabel,
           [option.label]).unescapeHTML(); // Prototype method.
    } else {
       textToUse = option.label;
    }

    // If option.escape is true, we want the text to be displayed literally. To 
    // achieve this behavior, do nothing.
    //
    // If option.escape is false, we want any special sequences in the text 
    // (e.g., "&nbsp;") to be displayed as evaluated (i.e., unescaped).
    if (new Boolean(option.escape).valueOf() == false) {
        element.text = textToUse.unescapeHTML(); // Prototype method.
    } else {
        element.text = textToUse;
    }

    if (option.selected != null) {
        element.selected = new Boolean(option.selected).valueOf();
    }
    if (option.disabled != null) {
        element.disabled = new Boolean(option.disabled).valueOf();
    }
    return true;
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
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.selectBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // A web app devleoper could return false in order to cancel the 
    // auto-submit. Thus, we will handle this event via the onChange call back.
    if (props.onChange) {
        // Set private function scope on DOM node.
        this.listContainer._onchange = (typeof props.onChange == 'string')            
            ? new Function("event", props.onChange) : props.onChange;

        // Must be cleared before calling setEventProps() below.
        props.onChange = null;
    }

    // Set the properties specific to the select element
    this.setSelectProps(this.listContainer, props);

    // Add option and optgroup elements in the select element
    if (props.options) {
        this.addOptions(props);
    }

    // Set label properties.
    if (props.label) {
        // Set properties.
        props.label.id = this.label.id; // Required for updateFragment().

        // Update/add fragment.
        this.widget.updateFragment(this.labelContainer, props.label);

        // Remove line break -- required for IE & cannot be updated once set.
        if (new Boolean(this.labelOnTop).valueOf() == true) {
            webui.@THEME@.common.setVisibleElement(this.brContainer, true);
        }
    }

    // Set more properties.
    this.setCommonProps(this.listContainer, props);
    this.setEventProps(this.listContainer, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}

/**
 * Helper function to set properties specific to the HTML select element
 *
 * @param {Node} selectNode The HTML select element.
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} [disabled]
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.setSelectProps = function(selectNode, props) {
    selectNode.name = selectNode.id;
    if (props.disabled != null) {
        selectNode.disabled = new Boolean(props.disabled).valueOf();
        selectNode.className = this.getSelectClassName(props);
    }
    return true;
}
