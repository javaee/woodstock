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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.dropDown");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.selectBase");

/**
 * This function is used to construct a dropDown widget.
 *
 * @name webui.@THEME_JS@.widget.dropDown
 * @extends webui.@THEME_JS@.widget._base.selectBase
 * @class This class contains functions for the dropDown widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {Object} label Defines the widget and its properties to use for
 * a label.
 * @config {boolean} labelOnTop If true the label appears above the dropdown. 
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} onBlur Element lost focus.
 * @config {String} onChange Option selection is changed.
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
 * @config {String} onSelect Option text is highlighted.
 * @config {Array} options See <code>selectBase._setOptions</code> or
 * <code>selectBase</code> overview for details on the elements of this array.
 * @config {int} size The number of option rows to display.
 * @config {String} style Specify style rules inline.
 * @config {boolean} submitForm If true the dropDown performs like a
 * "jump menu" and submits the entire form when an option is selected.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title The a HTML title attribue for the <code>select</code> element.
 * @config {boolean} visible Hide or show element.
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.dropDown",
        webui.@THEME_JS@.widget._base.selectBase, {
    // Set defaults.
    //
    constructor : function() {
	this.labelOnTop = this._theme._getMessageBoolean("dropDown.labelOnTop", false);
	this.submitForm =  false;
	this.width = this._theme._getMessage("dropDown.width", null);
    },
    _widgetType: "dropDown" // Required for theme properties.
});

/**
 * This function is called by the <code>selectBase::onChangeCallback</code>
 * method in respsonse to an onchange event.
 * <p>
 * If this instance is configured as a "jump" menu,
 * <code>submitForm</code> is true, the containing form is submitted.
 * </p>
 * <p>
 * If this instance is configured as a standard "dropdown" menu, then
 * the superclass's <code>_changed</code> method is called.
 * See <code>selectBase._changed</code>.<br/>
 * See <code>selectBase.onChangeCallback</code>
 * </p>
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.dropDown.prototype._changed = function() {

    // A null submitForm is the same as a standard menu.
    //
    if (this.submitForm != true) {
        // Drop down changed.
        return this._inherited("_changed", arguments);
    } else {

        // Jump drop down changed.
        var jumpDropdown = this.listContainer; 

        // Find the <form> for this drop down
        var form = jumpDropdown.form; 

        if (typeof form != "undefined" && form != null) { 
            this.submitterHiddenNode.value = "true";

            // Set style classes.
            var options = jumpDropdown.options;
            for (var i = 0; i < options.length; i++) { 
                options[i].className = this._getOptionClassName(options[i]);
            }
            form.submit();
        }
    }
    return true; 
};

/**
 * The dropDown defines the following event topics. The submit topics
 * are only valid if the <code>submitForm</code> property is true.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME_JS@.widget.dropDown.event =
        webui.@THEME_JS@.widget.dropDown.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for.*/
        beginTopic: "webui_@THEME_JS@_widget_dropDown_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for.*/
        endTopic: "webui_@THEME_JS@_widget_dropDown_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_dropDown_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_dropDown_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for.
	 * This topic is only valid if the <code>submitForm</code>
	 * property is <code>true</code>
	 */
        beginTopic: "webui_@THEME_JS@_widget_dropDown_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for.
	 * This topic is only valid if the <code>submitForm</code>
	 * property is <code>true</code>
	 */
        endTopic: "webui_@THEME_JS@_widget_dropDown_event_submit_end"
    }
};

/**
 * This function is used to obtain the outermost HTML element CSS class name.
 * <p>
 * Note: Selectors should be concatenated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
webui.@THEME_JS@.widget.dropDown.prototype._getClassName = function() {
    var cn = this._theme._getClassName("DROPDOWN", "");
    return (this.className) ? cn + " " + this.className : cn;
};

/**
 * Return an Object Literal of label properties desired by this widget.
 * <p>
 * This implementation sets
 * the <code>dropDown.labelLevel</code> and <code>dropDown.labelAlign</code>
 * theme values from the <code>messages</code> and <code>styles</code>
 * theme categories to the
 * label's <code>level</code> and <code>className</code> properties 
 * respectively. It sets the <code>htmlFor</code> attribute to 
 * <code>listContainer.id</code>.
 * </p>
 * <p>
 * These properties are extended with <code>this.label</code> and the
 * resulting properties are returned.
 * </p>
 * @return {Object} Key-Value pairs of properties.
 * @private
 */
webui.@THEME_JS@.widget.dropDown.prototype._getLabelProps = function() {
    var props = this._inherited("_getLabelProps", arguments);

    var cn = this._getLabelClassName(null);
    if (cn != null) {
       props.className = cn;
    }
    props.labelLevel = this._theme._getMessage("dropDown.labelLevel", null, 2);
    return props;
};

/**
 * Return a CSS selector for the dropDown label.
 * The chosen CSS selector is a function of 
 * the <code>labelOnTop</code> property and the whether or not the
 * dropdown is a "jump" dropDown. If <code>labelOnTop</code>
 * property is <code>true</code> null is returned. If the 
 * <code>labelOnTop</code> property is true, then the value of the
 * the of the theme keys, <code>MENU_JUMP_LABEL_ALIGN</code> or 
 * <code>MENU_STANDARD_LABEL_ALIGN</code> from the 
 * <code>styles</code> category are returned if the drop down is
 * a "jump" dropDown or plain dropDown, respectively.
 * @param {boolean} ontop The label on top state. In some contexts this state
 * must be passed to the method, for example when called from the 
 * <code>selectBase.setProps</code> method.
 * @return {String} A CSS selector for the dropDown label.
 * @private
 */
webui.@THEME_JS@.widget.dropDown.prototype._getLabelClassName = function(ontop) {
    if (ontop != null && ontop == true) {
	return null;
    }
    // Jumpmenu ?
    //
    return (this.submitForm != null && this.submitForm == true)
	? this._theme._getClassName("MENU_JUMP_LABEL_ALIGN", null)
        : this._theme._getClassName("MENU_STANDARD_LABEL_ALIGN", null);
};

/**
 * Return a CSS selector for listContainer HTML element.
 * The chosen CSS selector is a function of 
 * the <code>disabled</code> property and whether or not the dropDown
 * is a "jump" dropDown. The returned selector is the value of a 
 * property defined in the <code>styles</code> category, as shown 
 * in the following table.
 * <table border="1px">
 * <tr><th>key</th><th>disabled</th><th>jump dropDown</th></tr>
 * <tr>
 * <td>MENU_STANDARD</td>
 * <td>false</td>
 * <td>false</td>
 * </tr>
 * <tr>
 * <td>MENU_STANDARD_DISABLED</td>
 * <td>true</td>
 * <td>false</td>
 * </tr>
 * <tr>
 * <td>MENU_JUMP</td>
 * <td>false</td>
 * <td>true</td>
 * </tr>
 * <tr>
 * <td>MENU_JUMP_DISABLED</td>
 * <td>true</td>
 * <td>true</td>
 * </tr>
 * </table>
 * @return {String} A CSS selector for the listContainer HTML element.
 * @private
 */
webui.@THEME_JS@.widget.dropDown.prototype._getListContainerClassName =
        function(disabled, jumpmenu) {
    var key = "MENU_STANDARD";
    if (disabled == true) {
	if (jumpmenu == true) {
	    key = "MENU_JUMP_DISABLED";
	} else {
	    key = "MENU_STANDARD_DISABLED";
	}
    } else if (jumpmenu == true) {
	key = "MENU_JUMP";
    }
    return this._theme._getClassName(key, null);
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.dropDown.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Get properties.
    if (this.submitForm != null) { props.submitForm = this.submitForm; }

    return props;
};

/**
 * This function returns the CSS class name for an HTML option element,
 * based on the state of <code>element</code>. The CSS class name defined by 
 * the following theme keys from the <code>styles</code> theme category
 * is returned for the given states, based on 
 * the properties in <code>element</code>.
 * <p>
 * <table border="1px">
 * <tr><th>state</th><th>key</th></tr>
 * <tr><td>
 * separator == true</td><td>*_OPTION_SEPARATOR</td>
 * </tr><tr><td>
 * group == true</td><td>*_OPTION_GROUP</td>
 * </tr><tr><td>
 * disabled == true</td><td>*_OPTION_DISABLED</td>
 * </tr><tr><td>
 * selected == true and disabled == false</td>
 * <td>*_OPTION_SELECTED</td>
 * </tr><tr><td>
 * separator == false and group false<br/> 
 * 	and disabled == false<br/>
 *	and selected == false</td><td>*_OPTION</td>
 * </tr>
 * </table><br/>
 * Note:  * is <code>MENU_JUMP</code> for a jump menu and
 * <code>MENU_STANDARD</code> if not.
 * </p>
 * <p>
 * <p>
 * If <code>element</code> is null, the CSS class name for the theme
 * key "MENU_STANDARD_OPTION" is returned.
 * </p>
 * @param {Object} element An HTML option DOM node instance.
 * @config {boolean} separator If true, element is a option separator.
 * @config {boolean} group If true the element is a optGroup instance.
 * @config {boolean} disabled If true the element is disabled.
 * @config {boolean} selected If true the element is selected.
 * @return {String} The HTML option element CSS class name.
 * @private
 */
webui.@THEME_JS@.widget.dropDown.prototype._getOptionClassName = function(element) {

    var jumpmenu = this.submitForm != null && this.submitForm == true;

    var key = null;
    if (jumpmenu) {
	key = "MENU_JUMP_OPTION";
	if (element != null) {
	    if (element.separator == true) {
		key = "MENU_JUMP_OPTION_SEPARATOR";
	    } else
	    if (element.group == true) {
		key = "MENU_JUMP_OPTION_GROUP";
	    } else
	    if (element.selected == true) {
		key = "MENU_JUMP_OPTION_SELECTED";
	    }

	    // Disabled wins
	    //
	    if (element.disabled == true) {
		key = "MENU_JUMP_OPTION_DISABLED";
	    }
	}
    } else {
	key = "MENU_STANDARD_OPTION";
	if (element != null) {
	    if (element.separator == true) {
		key = "MENU_STANDARD_OPTION_SEPARATOR";
	    } else
	    if (element.group == true) {
		key = "MENU_STANDARD_OPTION_GROUP";
	    } else
	    if (element.selected == true) {
		key = "MENU_STANDARD_OPTION_SELECTED";
	    }

	    // Disabled wins
	    //
	    if (element.disabled == true) {
		key = "MENU_STANDARD_OPTION_DISABLED";
	    }
	}
    }
    return this._theme._getClassName(key);
};


/*
 * This function is used to fill in remaining template properties, after the
 * <code>_buildRendering</code> function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.dropDown.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this.submitterHiddenNode.id = this.id + "_submitter";
    }

    // Set the CSS class name for the select element.
    //
    var jumpmenu = this.submitForm != null && this.submitForm == true;
    var disabled = this.disabled != null && this.disabled == true;

    webui.@THEME_JS@._base.common._addStyleClass(this.listContainer, 
	this._getListContainerClassName(disabled, jumpmenu));
    
    return this._inherited("_postCreate", arguments);
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
webui.@THEME_JS@.widget.dropDown.prototype.setProps = function(props, notify) {
    if (props == null) {
	return this._inherited("setProps", arguments);
    }
	
    // We are trying to deal with a state change which requires
    // knowing what the current state of the widget is and the
    // state as defined in props. In order to compare the states
    // it must be done in setProps before the "props" are extended
    // onto the widget. At that point there is no difference between
    // "props" and "this".
    //
    // We need to clear the selector of the listContainer
    // when state changes from disabled to non disabled state
    // or from submitForm to standard dropdown.
    //
    var isjumpmenu = this.submitForm == true;
    var toggleJumpmenu = props.submitForm != null
        && props.submitForm != isjumpmenu;

    var isdisabled = this.disabled == true;
    var toggleDisabled = props.disabled != null
        && props.disabled != isdisabled;

    // Get current state of the classsname, and strip it 
    // and then add the classname for the new state.
    //
    if (toggleJumpmenu || toggleDisabled) {
	var cn = this._getListContainerClassName(isdisabled, isjumpmenu);
	webui.@THEME_JS@._base.common._stripStyleClass(this.listContainer, cn);

	cn = this._getListContainerClassName(
	    toggleDisabled ? props.disabled == true : isdisabled,
	    toggleJumpmenu ? props.jumpmenu == true : isjumpmenu);
	webui.@THEME_JS@._base.common._addStyleClass(this.listContainer, cn);
    }
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
webui.@THEME_JS@.widget.dropDown.prototype._setProps = function(props) {
    // If props is null, create one to pass default theme values
    // to the superClass via props.
    //
    if (props == null) {
	return this._inherited("_setProps", arguments);
    }

    var jumpmenu = props.submitForm != null && props.submitForm == true;

    // Add attributes to the hidden input for jump drop down.
    //
    if ((jumpmenu && this.submitterHiddenNode.name == null) || (jumpmenu &&
	    this.submitterHiddenNode.name != this.submitterHiddenNode.id)) {

	this.submitterHiddenNode.name = this.submitterHiddenNode.id;
	this.submitterHiddenNode.value = "false";
    }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
