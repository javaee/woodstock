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

@JS_NS@._dojo.provide("@JS_NS@.widget.listbox");

@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.selectBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.submitBase");
@JS_NS@._dojo.require("@JS_NS@.widget.textField");

/**
 * This function is used to construct a listbox widget.
 *
 * @name @JS_NS@.widget.listbox
 * @extends @JS_NS@.widget._base.selectBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.submitBase
 * @class This class contains functions for the listbox widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {Object} label Defines the widget and its properties to use for a
 * label.
 * @config {String} labelOnTop If true the label appears above the listbox.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {boolean} multiple If true allow multiple selections
 * @config {boolean} monospace If true use monospace font styling.
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
 * @config {Array} options See selectBase._setOptions or selectBase overview
 * for details on the elements of this array.
 * @config {int} size The number of option rows to display.
 * @config {String} style Specify style rules inline. 
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.listbox", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.submitBase,
        @JS_NS@.widget._base.selectBase ], {
    // Set defaults.
    constructor : function() {
	this.labelOnTop = this._theme._getMessageBoolean("listbox.labelOnTop", false);
	this.monospace = this._theme._getMessageBoolean("listbox.monospace", false);
	this.multiple = this._theme._getMessageBoolean("listbox.multiple", false);
	this.size = this._theme.getMessage("listbox.size", null, 12);
	this.width = this._theme.getMessage("listbox.width", null);
    },
    _widgetType: "listbox" // Required for theme properties.
});

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.listbox.event =
        @JS_NS@.widget.listbox.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for.*/
        beginTopic: "@JS_NS@_widget_listbox_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for.*/
        endTopic: "@JS_NS@_widget_listbox_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_listbox_event_state_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_listbox_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_listbox_event_submit_begin",

        /** event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_listbox_event_submit_end"
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
@JS_NS@.widget.listbox.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);
    var newClassName = this._theme.getClassName("LISTBOX", "");
    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * Return an Object Literal of label properties desired by this widget.
 * <p>
 * This implementation sets
 * the <code>listbox.labelLevel</code> and <code>listbox.labelAlign</code>
 * theme values from the <code>messages</code> and <code>styles</code>
 * theme categories to the
 * label's <code>level</code> and <code>className</code> properties 
 * respectively. It sets the <code>htmlFor</code> attribute to 
 * <code>_listContainer.id</code>
 * </p>
 * <p>
 * These properties are extended with <code>this.label</code> and the
 * resulting properties are returned.
 * </p>
 * @return {Object} Key-Value pairs of label properties.
 * @private
 */
@JS_NS@.widget.listbox.prototype._getLabelProps = function() {

    var props = this._inherited("_getLabelProps", arguments);
    props.level = this._theme.getMessage("listbox.labelLevel", null, 2);
    return props;
};

/**
 * Return a CSS selector for the listbox label.
 * The chosen CSS selector is a function of 
 * the <code>labelOnTop</code> property. If <code>labelOnTop</code>
 * property is <code>false</code> the value of the theme key 
 * <code>LISTBOX_LABEL_ALIGN</code> from the 
 * <code>styles</code> category is returned, else <code>null</code>.
 * @param {boolean} ontop The label on top state. In some contexts this state
 * must be passed to the method, for example when called from the 
 * <code>selectBase.setProps</code> method.
 * @return {String} A CSS selector for the listbox label.
 * @private
 */
@JS_NS@.widget.listbox.prototype._getLabelOnTopClassName = function(ontop) {
    //var labelontop = ontop != null ? ontop : this.labelOnTop;
    //return labelontop == true
    return ontop != null && ontop == true
	? null
	: this._theme.getClassName("LISTBOX_LABEL_ALIGN", null);
};

/**
 * Return a CSS selector for _listContainer HTML element.
 * The chosen CSS selector is a function of 
 * the <code>disabled</code> and <code>monospace</code>properties.
 * The returned selector is the value of a property defined in the
 * <code>styles</code> category, as shown in the following table.
 * <table border="1px">
 * <tr><th>key</th><th>disabled</th><th>monospace</th></tr>
 * <tr>
 * <td>LIST</td>
 * <td>false</td>
 * <td>false</td>
 * </tr>
 * <tr>
 * <td>LIST_DISABLED</td>
 * <td>true</td>
 * <td>false</td>
 * </tr>
 * <tr>
 * <td>LIST_MONOSPACE</td>
 * <td>false</td>
 * <td>true</td>
 * </tr>
 * <tr>
 * <td>LIST_MONOSPACE_DISABLED</td>
 * <td>true</td>
 * <td>true</td>
 * </tr>
 * </table>
 * @return {String} A CSS selector for the _listContainer HTML element.
 * @private
 */
@JS_NS@.widget.listbox.prototype._getListContainerClassName =
	function(disabled, monospace) {
    var key = "LIST";
    if (disabled == true) {
	if (monospace == true) {
	    key = "LIST_MONOSPACE_DISABLED";
	} else {
	    key = "LIST_DISABLED";
	}
    } else if (monospace == true) {
	key = "LIST_MONOSPACE";
    }
    return this._theme.getClassName(key, null);
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.listbox.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Get properties.
    //
    if (this.size != null) {
	props.size = this.size;
    }
    if (this.multiple != null) {
	props.multiple = this.multiple;
    }
    if (this.monospace != null) {
	props.monospace = this.monospace;
    }
    return props;
};

/**
 * This function returns the CSS class name for an HTML option element,
 * based on the state of <code>element</code>. The CSS class name for 
 * the following theme keys is returned for the given states, based on 
 * the properties
 * in "option".
 * <p>
 * <table border="1px">
 * <tr><th>state</th><th>key</th></tr>
 * <tr><td>
 * separator == true</td><td>LIST_OPTION_SEPARATOR</td>
 * </tr><tr><td>
 * group == true</td><td>LIST_OPTION_GROUP</td>
 * </tr><tr><td>
 * disabled == true</td><td>LIST_OPTION_DISABLED</td>
 * </tr><tr><td>
 * selected == true and disabled == false</td><td>LIST_OPTION_SELECTED</td>
 * </tr><tr><td>
 * separator == false and group false<br/> 
 * 	and disabled == false<br/>
 *	and selected == false</td><td>LIST_OPTION</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * If <code>element</code> is null, the CSS class name for the theme
 * key "LIST_OPTION" is returned.
 * </p>
 * @param {Object} element An HTML option DOM node instance.
 * @config {boolean} separator If true, element is a option separator.
 * @config {boolean} group If true the element is a optGroup instance.
 * @config {boolean} disabled If true the element is disabled.
 * @config {boolean} selected If true the element is selected.
 * @return {String} The HTML option element CSS class name.
 * @private
 */
@JS_NS@.widget.listbox.prototype._getOptionClassName = function(element) {
    var key = "LIST_OPTION";
    if (element == null) {
	return this._theme.getClassName(key, null);
    }

    if (element.separator == true) {
        key = "LIST_OPTION_SEPARATOR";
    } else
    if (element.group == true) {
        key = "LIST_OPTION_GROUP";
    } else
    if (element.selected == true) {
        key = "LIST_OPTION_SELECTED";
    }

    // disabled wins
    //
    if (element.disabled == true) {
	key = "LIST_OPTION_DISABLED";
    }
    return this._theme.getClassName(key, null);
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
@JS_NS@.widget.listbox.prototype._postCreate = function () {
    // Don't trash the template.
    this._common._addStyleClass(this._listContainer,
	this._getListContainerClassName(
          this.disabled == true, this.monospace == true));

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
@JS_NS@.widget.listbox.prototype.setProps = function(props, notify) {
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
    var ismonospace = this.monospace == true;
    var toggleMonospace = props.monospace != null && 
	props.monospace != ismonospace;

    var isdisabled = this.disabled == true;
    var toggleDisabled = props.disabled != null && 
	props.disabled != isdisabled;

    // Get current state of the classsname, and strip it 
    // and then add the classname for the new state.
    //
    if (toggleMonospace || toggleDisabled) {
	var cn = this._getListContainerClassName(isdisabled, ismonospace);
	this._common._stripStyleClass(this._listContainer, cn);

	cn = this._getListContainerClassName(
	    toggleDisabled ? props.disabled == true : isdisabled,
	    toggleMonospace ? props.monospace == true : ismonospace);
	this._common._addStyleClass(this._listContainer, cn);
    }
    return this._inherited("setProps", arguments);
};

/**
 * This function sets properties specific to the listbox, the superclass
 * will set properties specific to the <code>select</code> HTML element
 * and handle <code>labelOnTop</code> and the label subcomponent.
 * <p>
 * If the <code>size</code>, <code>multiple</code>, or <code>monospace</code>
 * properties are not set in <code>props</code> or <code>this</code>,
 * the values are obtained from the theme using the keys 
 * <code>listbox.size</code>, <code>listbox.multiple</code>,
 * <code>listbox.monospace</code>, and <code>listbox.labelOnTop</code>
 * respectively, from the theme "messages" category. If the theme does 
 * not define these values then the values <code>12</code>,
 * <code>false</code>, <code>false</code>, and <code>false</code>
 * are used, respectively.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} multiple If true allow multiple selections
 * @config {boolean} monospace  If true the <code>LIST_MONOSPACE</code> and
 * <code>LIST_MONOSPACE_DISABLED</code>CSS class names are used, else
 * <code>LIST</code> and <code>LIST_DISABLED</code>.
 * @config {int} size The number of rows to display.
 * @config {boolean} labelOnTop If true render the label on top of the
 * list box, else to the upper left.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.listbox.prototype._setProps = function(props) {
    // If props == null create one so that the superclass can 
    // handle "labelOnTop" or anything else that needs to be
    // set in props and handled in the superclass
    //
    if (props == null) {
	return this._inherited("_setProps", arguments);
	//props = {};
    }

    if (props.size != null) {
	var size = props.size;
	if (size < 1) {
	    size = this._theme.getMessage("listbox.size", null, 12);
	}
	if (this._listContainer.size != size) {
	    this._listContainer.size = size;
	}
    }

    if (props.multiple != null && 
	    this._listContainer.multiple != props.multiple) {
	this._listContainer.multiple = props.multiple;
    }
    return this._inherited("_setProps", arguments);
};
