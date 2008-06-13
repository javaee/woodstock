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

@JS_NS@._dojo.provide("@JS_NS@.widget.dropDown");

@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.selectBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.submitBase");

/**
 * This function is used to construct a dropDown widget.
 *
 * @constructor
 * @name @JS_NS@.widget.dropDown
 * @extends @JS_NS@.widget._base.selectBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.submitBase
 * @class This class contains functions for the dropDown widget.
 * <p>
 * A dropDown widget can be configured as a jump menu. Jump menus immediately 
 * perform an action, such as opening a window or navigating to another page, 
 * when the user selects an item. This action is accomplished by submitting the 
 * form immediately when the user changes the selection. By default, the 
 * dropDown widget is not a jump menu. The submission of the form does not 
 * occur until the user activates another component such as a button in order 
 * to submit the form. To configure the dropDown widget to render a jump menu, 
 * set the submitForm property to true.
 * </p><p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "mnu1",
 *       label: { value: "This is a menu" },
 *       options: [{
 *         value: "BOS",
 *         label: "Boston"
 *       }, {
 *         value: "SFO",
 *         label: "San Francisco"
 *       }],
 *       widgetType: "dropDown"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * menu is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "mnu1",
 *       label: { value: "This is a menu" },
 *       options: [{
 *         value: "BOS",
 *         label: "Boston"
 *       }, {
 *         value: "SFO",
 *         label: "San Francisco"
 *       }],
 *       widgetType: "dropDown"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Menu State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("mnu1"); // Get menu
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the menu is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "mnu1",
 *       label: { value: "This is a menu" },
 *       options: [{
 *         value: "BOS",
 *         label: "Boston"
 *       }, {
 *         value: "SFO",
 *         label: "San Francisco"
 *       }],
 *       widgetType: "dropDown"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Menu" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("mnu1"); // Get menu
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can take an optional list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,..."). When no parameter is given, 
 * the refresh function acts as a reset. That is, the widget will be redrawn 
 * using values set server-side, but not updated.
 * </p><p>
 * <h3>Example 3b: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a menu using the refresh
 * function. The execute property of the refresh function is used to define the
 * client id which is to be submitted and updated server-side. As the user types
 * in the text field, the input value is updated server-side and the menu
 * label is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "mnu1",
 *       label: { value: "This is a menu" },
 *       options: [{
 *         value: "BOS",
 *         label: "Boston"
 *       }, {
 *         value: "SFO",
 *         label: "San Francisco"
 *       }],
 *       widgetType: "dropDown"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "field1",
 *       label: { value: "Change Menu Label" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "textField"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("mnu1"); // Get menu
 *       return widget.refresh("field1"); // Asynchronously refresh while submitting field value
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can optionally take a list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,...").
 * </p><p>
 * <h3>Example 4: Subscribing to event topics</h3>
 * </p><p>
 * When a widget is manipulated, some features may publish event topics for
 * custom AJAX implementations to listen for. For example, you may listen for
 * the refresh event topic using:
 * </p><pre><code>
 * &lt;script type="text/javascript">
 *    var foo = {
 *        // Process refresh event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        processRefreshEvent: function(props) {
 *            // Get the widget id.
 *            if (props.id == "mnu1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.dropDown.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
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
@JS_NS@._dojo.declare("@JS_NS@.widget.dropDown", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.submitBase,
        @JS_NS@.widget._base.selectBase ], {
    // Set defaults.
    constructor : function() {
	this.labelOnTop = this._theme._getMessageBoolean("dropDown.labelOnTop", false);
	this.submitForm =  false;
	this.width = this._theme.getMessage("dropDown.width", null);
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
@JS_NS@.widget.dropDown.prototype._changed = function() {

    // A null submitForm is the same as a standard menu.
    //
    if (this.submitForm != true) {
        // Drop down changed.
        return this._inherited("_changed", arguments);
    } else {

        // Jump drop down changed.
        var jumpDropdown = this._listContainer; 

        // Find the <form> for this drop down
        var form = jumpDropdown.form;
        if (form != null) { 
            this._submitterHiddenNode.value = "true";

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
@JS_NS@.widget.dropDown.event =
        @JS_NS@.widget.dropDown.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for.*/
        beginTopic: "@JS_NS@_widget_dropDown_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for.*/
        endTopic: "@JS_NS@_widget_dropDown_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_dropDown_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_dropDown_event_state_end"
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
        beginTopic: "@JS_NS@_widget_dropDown_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for.
	 * This topic is only valid if the <code>submitForm</code>
	 * property is <code>true</code>
	 */
        endTopic: "@JS_NS@_widget_dropDown_event_submit_end"
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
@JS_NS@.widget.dropDown.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);
    var newClassName = this._theme.getClassName("DROPDOWN", "");
    return (className)
        ? newClassName + " " + className
        : newClassName;
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
 * <code>_listContainer.id</code>.
 * </p>
 * <p>
 * These properties are extended with <code>this.label</code> and the
 * resulting properties are returned.
 * </p>
 * @return {Object} Key-Value pairs of properties.
 * @private
 */
@JS_NS@.widget.dropDown.prototype._getLabelProps = function() {

    var props = this._inherited("_getLabelProps", arguments);
    props.level = this._theme.getMessage("dropDown.labelLevel", null, 2);
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
@JS_NS@.widget.dropDown.prototype._getLabelOnTopClassName = function(ontop) {
    if (ontop != null && ontop == true) {
	return null;
    }
    // Jumpmenu ?
    //
    return (this.submitForm != null && this.submitForm == true)
	? this._theme.getClassName("MENU_JUMP_LABEL_ALIGN", null)
        : this._theme.getClassName("MENU_STANDARD_LABEL_ALIGN", null);
};

/**
 * Return a CSS selector for _listContainer HTML element.
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
 * @return {String} A CSS selector for the _listContainer HTML element.
 * @private
 */
@JS_NS@.widget.dropDown.prototype._getListContainerClassName =
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
    return this._theme.getClassName(key, null);
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.dropDown.prototype.getProps = function() {
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
@JS_NS@.widget.dropDown.prototype._getOptionClassName = function(element) {

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
    return this._theme.getClassName(key);
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
@JS_NS@.widget.dropDown.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._submitterHiddenNode.id = this.id + "_submitter";
    }

    // Set the CSS class name for the select element.
    //
    var jumpmenu = this.submitForm != null && this.submitForm == true;
    var disabled = this.disabled != null && this.disabled == true;

    this._common._addStyleClass(this._listContainer, 
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
@JS_NS@.widget.dropDown.prototype.setProps = function(props, notify) {
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
    // We need to clear the selector of the _listContainer
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
	this._common._stripStyleClass(this._listContainer, cn);

	cn = this._getListContainerClassName(
	    toggleDisabled ? props.disabled == true : isdisabled,
	    toggleJumpmenu ? props.jumpmenu == true : isjumpmenu);
	this._common._addStyleClass(this._listContainer, cn);
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
@JS_NS@.widget.dropDown.prototype._setProps = function(props) {
    // If props is null, create one to pass default theme values
    // to the superClass via props.
    //
    if (props == null) {
	return this._inherited("_setProps", arguments);
    }

    var jumpmenu = props.submitForm != null && props.submitForm == true;

    // Add attributes to the hidden input for jump drop down.
    //
    if ((jumpmenu && this._submitterHiddenNode.name == null) || (jumpmenu &&
	    this._submitterHiddenNode.name != this._submitterHiddenNode.id)) {

	this._submitterHiddenNode.name = this._submitterHiddenNode.id;
	this._submitterHiddenNode.value = "false";
    }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
