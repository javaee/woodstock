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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.selectBase");

webui.@THEME@.dojo.require("webui.@THEME@.browser");
webui.@THEME@.dojo.require("webui.@THEME@.common");
webui.@THEME@.dojo.require("webui.@THEME@.widget.widgetBase");
webui.@THEME@.dojo.require("webui.@THEME@.widget.label");

/**
 * @name webui.@THEME@.widget.selectBase
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class defines functions and properties for
 * widgets based on the "select" HTML element.
 *
 * <h3>Dojo attach points</h3>
 * A <code>selectBase</code>  subclass's template must
 * define the following attach point identifiers.
 * <ul>
 * <li><code>listContainer</code> - the <code>select</code> element.</li>
 * <li><code>brNode</code> - the element that controls the label postion.
 * <li><code>optionNode</code> - the element to clone for an
 <code>option</code> element.
 * </li>
 * <li><code>optionGroupContainer</code> - the element to clone for an
 * <code>optGroup</code> element.</li>
 * <li><code>memberOptionNode</code> - the element to clone for an
 * <code>option</code> in an <code>optGroup</code> element.</li>
 * </ul>
 * <h3>The <code>label</code> object property</h3>
 * The <code>label</code> property is an object that defines the properties
 * for a widget that is rendered by the <code>selectBase</code>
 * class to represent a label. Minimally, only the <code>value</code> 
 * property of the label object property must be non null for 
 * the <code>selectBase</code> widget to render a label. If only the
 * <code>value</code> property is specified the following default
 * values will be used to create an instance of 
 * <code>webui.@THEME@.widget.label</code>.
 * <p>
 * <ul>
 * <li><code>widgetType</code> -
 * <code>webui.@THEME@.widget.label</code></li>
 * <li><code>id</code> - this.id + "_label"</li>
 * <li><code>htmlFor</code> - this.listContainer.id</li>
 * </ul>
 * <p>See <code>postCreate</code> and <code>getLabelProps</code>
 * </p>
 * <h3>The <code>options</code> array property</h3>
 * The <code>options</code> array property defines the contents of the
 * <code>select</code> HTML element. The contents of the array are 
 * translated into <code>option</code> and <code>optGroup</code> HTML
 * elements. Each element of the <code>options</code> array property
 * is considered an object with the following properties. Note that
 * "NA" in the following table means "not applicable", meaning there
 * is no HTML element property counterpart.
 * <p>
 * <table border="1px">
 * <tr><th>Property</th><th>Type</th><th>Description</th>
 * <th>HTML option or optGroup element property</th></tr>
 * <tr><td>label</td><td>String</td><td>
 * The text that appears as the choice in the rendered <code>option</code>
 * HTML element. (See note[1] below)
 * </td><td>label</td></tr>
 * <tr><td>value</td><td>String</td><td>
 * The value for this option. It is submitted in a request if this option is
 * selected.</td><td>value</td></tr>
 * <tr><td>separator</td><td>boolean</td><td>
 * If true this option represents a separator and cannot be selected.
 * </td><td>NA</td></tr>
 * <tr><td>group</td><td>boolean</td><td>
 * If true this option represents a group and cannot be selected. An
 * <code>optGroup</code> HTML element is rendered to represent this option.
 * </td><td>NA</td></tr>
 * <tr><td>escape</td><td>boolean</td><td>
 * If false the label string will be evaluated as HTML markup.
 * </td><td>NA</td></tr>
 * <tr><td>selected</td><td>boolean</td><td>
 * If true this option will be initially selected. 
 * </td><td>selected</td></tr>
 * <tr><td>disabled</td><td>boolean</td><td>
 * If true this option will be initially disabled and cannot be selected.
 * </td><td>disabled</td></tr>
 * <tr><td>title</td><td>String</td><td>
 * The HTML title attribute text.</td><td>title</td></tr>
 * </table>
 * </p>
 * <p>
 * The option object may also define the javascript event properties
 * suitable for an <code>option</code> or <code>optGroup</code> HTML
 * element and they will be assigned to the element's DOM node.
 * </p>
 * <p>
 * <ul>
 * <li>Note[1] Browser runtime behavior, contrary to the HTML and DOM
 * specifications, indicates that the DOM <code>label</code> property
 * is not rendered and only the <code>text</code> property is rendered.
 * This means that the <code>label</code> property of an
 * <code>options</code> array element will
 * be assigned to an <code>option</code> or <code>optGroup</code>
 * DOM node's <code>text</code> property.</li>
 * </ul>
 * </p>
 * @static
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.selectBase", 
	webui.@THEME@.widget.widgetBase, {
    disabled: false,
    required: false,
    valid: true,

    /** private flag to remember that last label style class.
     * @ignore
     */
    _lastLabelOnTopClassName: null, // never been set

    /** flag to prevent blank entries in the 
     *  drop down for the original empty dojo attach point nodes.
     * see "setOptions"
     * @ignore
     */
    _alreadyRemoved: false
});

/**
 * This function is called by the <code>_onChangeCallback</code>
 * function to set the option element's className attribute with appropriate
 * selected and disabled selectors.
 * <p>
 * This method calls <code>this.getOptionClassName</code> once for each
 * option. Subclasses should implement <code>getOptionClassName</code>
 * in order to assign the appropriate CSS class name, to reflect a disabled or
 * selected option and any other properties due to an onChange event.
 * <p>
 * See <code>getOptionClassName</code> for the default behavior.
 * </p>
 * <p>
 * Before this method calls <code>this.getOptionClassName</code>, it has
 * addressed an IE issue where disabled options can be selected, and has
 * deslected, selected disabled options.
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.changed = function(ev) {
    var options = this.listContainer.options;

    // IE allows disabled options to be selected. Ensure that
    // disabled options never appear as selected options.
    //
    if (webui.@THEME@.browser.isIe()) {
	for (var i = 0; i < options.length; ++i) {
	    if (options[i].disabled == true && options[i].selected == true) {
		if (this.listContainer.multiple == true) {
		    options[i].selected = false;
		} else {
		    // Don't we need to set the disabled option's
		    // selected to false too ?
		    //
		    this.listContainer.selectedIndex = -1;
		}
	    }
	    // Only set the option if its value is different
	    // than what is returned. Note that if this method
	    // returns null, the option may not visually reflect
	    // the true state.
	    //
	    var cn = this.getOptionClassName(options[i]);
	    if (cn != null && options[i].className != cn) {
		options[i].className = cn;
	    }
	} 
    } else {
	for (var i = 0; i < options.length; ++i) { 
	    // Only set the option if its value is different
	    // than what is returned. Note that if this method
	    // returns null, the option may not visually reflect
	    // the true state.
	    //
	    var cn = this.getOptionClassName(options[i]);
	    if (cn != null && options[i].className != cn) {
		options[i].className = cn;
	    }
	}
    }
    return true;
};

/**
 * This method copies option properties from <code>fromOption</code> to 
 * <code>toOption</code>. Note that <code>fromOption</code> is assumed to
 * be a DOM <code>option</code> element. There is not a one to one mapping
 * of properties by name from a DOM <code>option</code> element to a
 * widget's <code>options</code> array <code>option</code> array element.
 * The properties that are set in <code>setOptionProps</code> and
 * <code>setGroupOptionProps</code> are copied.
 * @param {Object} toOption An <code>Object</code> to receive
 * <code>selectBase</code> option properties.
 * @param {Object} fromOption An <code>option</code> or <code>optgroup</code>
 * element DOM node to copy <code>selectBase</code> option properties from.
 * @return {boolean} Returns true.
 * @private
 */
webui.@THEME@.widget.selectBase.prototype._copyOption = function(toOption, fromOption) {
    var domhandlers = [ "onblur", "onchange", "onclick", "ondblclick",
	"onfocus", "onkeydown", "onkeypress", "onkeyup", "onmousedown",
	"onmouseout", "onmouseover", "onmouseup", "onmousemove", "onresize"];

    var widgethandlers = [ "onBlur", "onChange", "onClick", "onDblClick",
	"onFocus", "onKeyDown", "onKeyPress", "onKeyUp", "onMouseDown",
	"onMouseOut", "onMouseOver", "onMouseUp", "onMouseMove", "onResize"];

    for (var i = 0; i < domhandlers.length; ++i) {
	if (fromOption[domhandlers[i]]) {
	    toOption[widgethandlers[i]] = fromOption[domhandlers[i]];
	}
    }
    toOption.label = fromOption.label;
    toOption.value = fromOption.value;
    toOption.escape = fromOption.escape;
    toOption.disabled = fromOption.disabled;
    toOption.defaultSelected = fromOption.defaultSelected;
    toOption.selected = fromOption.selected;
    toOption.title = fromOption.title;
    toOption.className = fromOption.className;
    toOption.group = fromOption.group;
    if (toOption.group == true) {
	toOption.options = this._copyOptions(fromOption);
    }
    return true;
};

/**
 * Returns an <code>options</code> array of <code>option</code> objects
 * as defined by <code>selectBase</code> (see selectBase.setOptions).
 * <code>domNode</code> is assumed to be a <code>select</code> element
 * or an <code>optgroup</code>.
 * <p>
 * There is not a one to one mapping of properties by name from a DOM
 * <code>option</code> or <code>optgroup</code> element to a
 * widget's <code>options</code> array <code>option</code> object array element.
 * The properties that are set in <code>setOptionProps</code> and 
 * <code>setGroupOptionProps</code> are copied to <code>Object</code>s
 * and returned as the elements of the returned array. The returned
 * array is equivalent to the <code>options</code> array used to create
 * a selectBase subclass, like listbox or dropdown.
 * @param {Object} domNode A <code>select</code> element DOM node or an
 * <code>optgroup</code> DOM node.
 * @return {Object} An array of <code>selectBase</code> options. If 
 * <code>domNode</code> has no child nodes, an empty array is returned.
 * @private
 */
webui.@THEME@.widget.selectBase.prototype._copyOptions = function(domNode) {
    var newoptions = [];
    if (!domNode.hasChildNodes()) {
	return newoptions;
    }
    var len = domNode.childNodes.length;
    for (var j = 0; j < len; ++j) {
	newoptions[j] = new Object();
	if (domNode.childNodes != null) {
	    this._copyOption(newoptions[j], domNode.childNodes[j]);
	}
    }
    return newoptions;
};

/**
 * This function returns the CSS class name for an HTML <code>option</code> or
 * <code>optgroup</code> element.
 * <p>
 * This implementation returns <code>option.className</code>
 * This method should be overridden by subclasses.
 * </p>
 *
 * @param {Object} option Key-Value pairs of properties.
 * @return {String} The HTML option element class name.
 */
webui.@THEME@.widget.selectBase.prototype.getOptionClassName = function(option) {
    // Make sure that if a subclass does not implement this method
    // that it causes no change to option.
    //
    return option.className;
};

/**
 * This function replaces all <code>option</code> and <code>optgroup</code>
 * elements in the HTML <code>select</code> element,
 * <code>this.listContainer</code>.
 * <p>
 * All options are replaced with the options specified in <code>props</code>.
 * If <code>props</code> is null, <code>false</code>
 * is returned and no change to the <code>select</code> element occurs. If
 * <code>props</code> contains no properties all options in the 
 * <code>select</code> element are removed.
 * </p>
 * @param {Object} props Key-Value pairs of option properties.
 * @config {String} label The text that appears as the choice in the 
 * rendered <code>option</code>.
 * @config {String} value The value for this option, which is submitted
 * in a request if this option is selected.
 * @config {boolean} separator If true this option represents a separator and
 * cannot be selected.
 * @config {boolean} group If true this option represents a group and
 * cannot be selected.
 * @config {boolean} escape If false the label string will be evaluated as
 * HTML markup.
 * @config {boolean} selected If true this option will be initially
 * selected.
 * @config {boolean} disabled If true this option will be initially 
 * disabled and cannot be selected.
 * @config {String} title The HTML title attribute text.
 * @return {boolean} true if successful; otherwise, false.
 * </p>
 * <p>
 * The option object may also define javascript event properties suitable for
 * an <code>option</code> or <code>optGroup</code> HTML element.
 * </p>
 */
webui.@THEME@.widget.selectBase.prototype.setOptions = function(props) {
    if (props == null) {
	return false;
    }

    // Remove all existing options
    //
    // Note: this has to be done. Otherwise, you'll get blank entries in the 
    // drop down for the original empty dojo attach point nodes.
    //

    if (!this._alreadyRemoved) {
        this.listContainer.removeChild(this.optionNode); 
        this.optionGroupContainer.removeChild(this.memberOptionNode);
        this.listContainer.removeChild(this.optionGroupContainer);
        this._alreadyRemoved = true;
    }

    // Cleaning up the old options
    //
    while (this.listContainer.firstChild) {
        this.listContainer.removeChild(this.listContainer.firstChild);
    }

    var thisNode;
    for (var i = 0; i < props.options.length; i++) {

	var pOption = props.options[i];

	var isie = webui.@THEME@.browser.isIe();
	if (pOption.group == null || pOption.group == false) {

	    // For some reason, ie is prone to painting problems (esp. after a 
	    // style change) when using the DOM to populate options, but 
	    // apparently not when the options are in a group
	    //
	    // There is no working "clone" on "option" nodes in IE
	    // Manually get the attributes and then the "text"
	    // DOM attribute. I don't think an option can have HTML
	    // markup in the body, at least literally in the template
	    // so this strategy should be be sufficient enough to get
	    // the template's option attributes
	    //
	    if (isie) {
		thisNode = new Option();
		var len = this.optionNode.attributes.length;
		for (var j = 0; j < len; ++j) {
		    thisNode.setAttribute(this.optionNode.attributes[j].name,
			    this.optionNode.attributes[j].value);
		}
		// Need to manually do text, although this is likely null
		// in the template.
		//
		thisNode.text = this.optionNode.text;

	    } else {
		thisNode = this.optionNode.cloneNode(true);
	    }

	    // Set the properties on this option element
	    this.setOptionProps(thisNode, pOption);
	    
	    // Would it be better to create all the nodes and then
	    // add them to the "options" array or append then ?
	    // Granted this would mean iterating twice.
	    // But it might be better if something fails
	    // and if so leave the original list alone.
	    //

	    // Append this option node to the select
	    //
	    if (isie) {
		var idx = this.listContainer.options.length;
		var isSelected = thisNode.selected;
		this.listContainer.options[idx] = thisNode;

		// VERIFY THAT WE STILL NEED THIS
		//

		// explicitly set the selected property again!
		// this is necessary to work around a defect in 
		// some versions of IE6
		//
		this.listContainer.options[idx].selected = isSelected;
	    } else {
		this.listContainer.appendChild(thisNode);
	    }
	} else {
	    // group option optgroup
	    //
	    thisNode = this.optionGroupContainer.cloneNode(true);
	    
	    // Set the properties on this optgroup element
	    //
	    this.setGroupOptionProps(thisNode, pOption);
	    
	    // Add the option elements to this group
	    // Supports only one level
	    //
	    var thisSubNode;
	    for (var ix = 0; ix < pOption.options.length; ix++) {
		thisSubNode = this.memberOptionNode.cloneNode(true);
		this.setOptionProps(thisSubNode, pOption.options[ix]);
		thisNode.appendChild(thisSubNode); 
	    }

	    // Append this optgroup node to the select element
	    // only after constructing its option children
	    //
	    this.listContainer.appendChild(thisNode);
	}
    }
    return true;
};

/**
 * This function is used to get widget properties. Please see 
 * subclass's setProps() function for a complete list of supported properties.
 * <br/>
 * This base class handles the following properties.
 * <ul>
 * <li><code>labelOnTop</code> - If true the label appears above the select
 * element.</li>
 * <li><code>disabled</code> - If true the select element is disabled.</li>
 * <li><code>label</code> - This object defines the widget and properties
 * for a label.
 * <code>selectBase</code> overview.</li>
 * <li><code>options</code> - This array object contains the select element 
 * options and their attributes. See <code>setOptions</code> and the 
 * <code>selectBase</code> * overview for details on the <code>options</code> 
 * array.</li>
 * </ul>
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.selectBase.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Get properties.
    // Should we return the default theme value for labelOnTop
    // if "this.labelOnTop" has not been set ?
    //
    if (this.labelOnTop != null) { props.labelOnTop = this.labelOnTop; }
    if (this.disabled != null) { props.disabled = this.disabled; }
    if (this.label) { props.label = this.label; }
    if (this.required != null) { props.required = this.required; }
    if (this.valid != null) { props.valid = this.valid; }

    // After widget has been initialized, get actual select element state
    // Note that the options that exist on the select element will
    // not include the original "options" props that are not
    // HTML option or HTML optgroup attributes.
    //
    if (this.isInitialized() == true && this.listContainer != null) {
	// We need to copy the options from this.listContainer
	// or else they will be destroyed if passed back to addProps.
	//
	var opts = this._copyOptions(this.listContainer);
        props.options = opts;
	// It's not clear if we should do this.
	//
	// this.options = opts;
    } else if (this.options != null) {
        props.options = this.options;
    }
    return props;
};

/**
 * This function is used to obtain the underlying HTML select element.
 *
 * @return {Node} The HTML select element.
 */
webui.@THEME@.widget.selectBase.prototype.getSelectElement = function() {
    return this.listContainer;
};

/**
 * This function is used to obtain the <code>label</code> attribute of
 * the selected option.
 * <p>
 * If no option is selected, this function returns null.
 * </p>
 * <p>
 * If the underlying select element's <code>multiple</code> attribute is true,
 * this method returns the first selected option's label.
 * </p>
 * 
 * @return The label of the selected option, or null if none is selected. 
 * @return {String} The label attribute of the selected option.
 */
webui.@THEME@.widget.selectBase.prototype.getSelectedLabel = function() { 
    var index = this.listContainer.selectedIndex; 

    if (index == -1) { 
        return null; 
    } else { 
	return this.listContainer.options[index].label;
    }
};

/**
 * This function is used to obtain the <code>value</code> attribute
 * of the selected option.
 * <p>
 * If no option is selected, this function returns null. 
 * </p>
 * <p>
 * If the underlying select element's <code>multiple</code>" attribute is true,
 * this method returns the first selected option's value.
 * </p>
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
};

/**
 * This function is invoked for the select element's <code>onchange</code>
 * event.
 * <p>
 * If <code>this.disabled</code> is true, return false.<br/>
 * This method calls the handler defined by the <code>props.onChange</code>
 * property. If that handler returns false, false is returned. If the
 * <code>props.onChanged</code> handler returns true, then
 * <code>this.changed</code> is called and its return value is returned.
 * </p>
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.selectBase.prototype._onChangeCallback = function(event) {
    if (this.disabled == true) {
        return false;
    }

    // If function returns false, we must prevent the auto-submit.
    //
    var result = (this.listContainer._onchange)
        ? this.listContainer._onchange(event) : true;
    if (result == false) {
        return false;
    }

    // Set style classes.
    return this.changed(event);
};

/**
 * This function is used to fill in remaining template properties, after the
 * <code>buildRendering</code> function has been processed.
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
	this.listContainer.name = this.listContainer.id;
    }

    // Subclasses have set this property
    // make the br node visible else hide it.
    //
    if (this.labelOnTop != null) {
	webui.@THEME@.common.setVisibleElement(this.brNode, this.labelOnTop);
	this._lastLabelOnTopClassName = this.getLabelClassName(null);
    }

    if (this.label && this.label.value != null &&
	    !this.widget.isFragment(this.label)) {

	this.label = this.widget.getWidgetProps("label", this.getLabelProps());
	this.label.id = this.id + "_label";
    }

    if (this.label != null) {
	if (this.required != null && this.required == true) {
	    this.label.required = true;
	}
	if (this.valid != null && this.valid != true) {
	    this.label.valid = false;
	}
	if (this._labelOnTopClassName != null) {
	    webui.@THEME@.common.addStyleClass(this.label, labelOnTopClassName);
	}
    }

    // Set public functions.
    this.domNode.getSelectedValue = function() { 
	return webui.@THEME@.dijit.byId(this.id).getSelectedValue(); };
    this.domNode.getSelectedLabel = function() { 
	return webui.@THEME@.dijit.byId(this.id).getSelectedLabel(); };
    this.domNode.getSelectElement = function() { 
	return webui.@THEME@.dijit.byId(this.id).getSelectElement(); };

    // Set events.
    this.dojo.connect(this.listContainer, "onchange", this, 
	"_onChangeCallback");

    return this.inherited("postCreate", arguments);
};

/**
 * Return an Object Literal of label properties desired
 * by the selectBase widget.
 * <p>
 * This implementation returns null. This method should be implemented
 * in subclasses to return label properties desired by the subclass.
 * </p>
 * @return {Object} This implementation returns null;
 */
webui.@THEME@.widget.selectBase.prototype.getLabelProps = function() {
    return null;
};

/**
 * This function is used to set the <code>option</code> properties on the
 * <code>optgroup</code> HTML DOM element, <code>element</code>.
 * <p>
 * This method assigns the <code>option.label</code>,
 * <code>option.disabled</code>, <code>option.title</code> properties
 * to <code>element</code>, non HTML properties defined in 
 * <code>option</code>, calls <code>this.setEventProps</code>
 * and then calls <code>this.getOptionClassName</code>. 
 * Subclasses should implement <code>getOptionClassName</code> if the default
 * behavior of <code>selectBase.getOptionClassName</code> is not appropriate.
 * Subclasses usually subclass this method to provide subclass specific 
 * CSS class names and properties. For all the properties defined in the 
 * option array see <code>setOptions</code>.
 * </p>
 * @param {Node} element The optgroup DOM node
 * @param {Object} option Key-Value pairs of properties for the optgroup node.
 * @config {boolean} disabled If true the optgroup is disabled.
 * @config {String} label The optgroup choice text.
 * @config {String} title The optgrp HTML title attribute value.
 * @config {boolean} escape If false the label string will be evaluated
 * as HTML markup.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.setGroupOptionProps =
	function(element, option) {
    element.label = option.label;
  
    if (option.disabled != null) {
        element.disabled = option.disabled;
    }

    if (option.title != null) {
	element.title = option.title;
    }

    // optGroup elements can have events.
    this.setEventProps(element, option);

    // Non HTML properties
    //
    if (option.escape != null) {
	element.escape = option.escape;
    }
    element.group = true;

    // Do this last so that all other properties will have been set.
    // Note that if this does return null, which it shouldn't
    // then the visual appearance may not reflect the 
    // actual state.
    //
    var cn =  this.getOptionClassName(element);
    if (cn != null) {
	element.className = cn;
    }
    return true;
};

/**
 * This function is used to set the <code>option</code> properties on the
 * <code>option</code> HTML DOM element, <code>element</code>.
 * <p>
 * This method assigns the <code>option.label</code>,
 * <code>option.disabled</code>, <code>option.title</code> properties
 * to <code>element</code>, non HTML properties defined in 
 * <code>option</code>, calls <code>this.setEventProps</code>
 * and then calls <code>this.getOptionClassName</code>. 
 * Subclasses should implement <code>getOptionClassName</code> if the default
 * behavior of <code>selectBase.getOptionClassName</code> is not appropriate.
 * Subclasses usually subclass this method to provide subclass specific CSS
 * class names and properties. For all the properties defined in the option
 * array see <code>setOptions</code>
 * </p>
 * @param {Node} element The <code>option</code> HTML element DOM node
 * @param {Object} option Key-Value pairs of properties for the option node.
 * @config {boolean} disabled If true the option is disabled.
 * @config {String} label The option choice text.
 * @config {String} title The value of the option's HTML <code>title</code>
 * attribute.
 * @config {boolean} escape If false the label string will be evaluated
 * as HTML markup.
 * @config {boolean} separator If true this option acts as a separator.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.selectBase.prototype.setOptionProps =
        function(element, option) {
    element.value = option.value;

    // If option.escape is true, we want the text to be displayed
    // literally. To achieve this behavior, do nothing.
    // If option.escape is false, we want any special sequences in the text 
    // (e.g., "&nbsp;") to be displayed as evaluated (i.e., unescaped).
    //
    // This is because it is assumed that the value of the property has 
    // already been escaped. This may be true if the value was set in the 
    // HTML response. This is why we call "unescapeHTML".
    //
    // However it may not be true if the text was set in javascript.
    //

    // Note that the HTML and ECMA JS DOM language binding indicates
    // that the "label" property should be used. However browsers do not
    // respect those specs. In fact label does not even work at all.
    // Setting the string on label results in no string being displayed.
    //
    var textToUse = option.label;
    if (option.escape != null && option.escape == false) {
	// Prototype method.
	element.text = this.prototypejs.unescapeHTML(textToUse);
    } else {
	element.text = textToUse;
    }
    // We must record the state of the original option
    //
    element.label = option.label;

    // Never allow a disabled option to be spedified as selected
    // This is only a problem if an option is initially selected
    // and disbled on most browsers since once disabled it cannot
    // be selected except for IE. For IE it is ensured that a
    // disabled option cannot be "selected" in the
    // "change" method.
    //
    if (option.disabled != null && option.disabled == true) {
	option.selected = false;
    }

    element.defaultSelected = option.selected != null ? option.selected : false;

    // When creating new options, defaultSelected should also be set.
    // Actually only "defaultSelected" should be set and set only once
    // since it is supposed to be the "initial" value.
    // However all options are always recreated in this implementation
    // when this method is called by setOptions.
    //
    element.selected = element.defaultSelected;
	
    element.disabled = option.disabled != null ? option.disabled : false;

    if (option.title != null) {
	element.title = option.title;
    }

    // option elements can have events.
    this.setEventProps(element, option);

    // In order for subclasses to return the appropriate
    // selector all the properties in the option properties array must
    // be placed on the HTML element option and optgroup instances.
    //
    // This is because "getOptionClassName" is called in two different
    // contexts. It is called here where both the HTML option or
    // optgroup element is available and the original option properties 
    // are known, and during the "onchange" event where only the 
    // HTML option element is known. Therefore if the choice of selector 
    // is based on whether or not an option is a "separator" then, 
    // the separator state must be available from the HTML option or optgroup
    // element instance.
    //
    // This issue could be mitigated by using descendant selectors
    // vs. literal selectors. In other words, if every time an option
    // became disabled, the className attribute would be set (or merged) with 
    // Dis. A selector could be defined as ".Lst option.Dis" which would
    // implicitly match. If attribute selectors worked we could use
    // that instead. Either strategy would eliminate the need for
    // methods like "getOptionClassName".
    //
    // Only set non HTML element props if we have to.
    //
    if (option.separator != null && option.separator == true) {
	element.separator = option.separator;
	element.disabled = true;
    }
    if (option.escape != null && option.escape == false) {
	element.escape = option.escape;
    }
    element.group = false;

    // Call getOptionClassName only after setting HTML props
    // and option props on element.  Note that if this method
    // returns null, the option may not visually reflect
    // the true state.
    //
    var cn = this.getOptionClassName(element);
    if (cn != null) {
	element.className = cn;
    }
    return true;
};

/**
 * This function is used to set widget properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @config {Object} label The properties for a label widget.
 * @config {Object} options An array of Objects that represent the 
 * <code>select</code> element's <code>option</code> and 
 * <code>optgroup</code> elements. @see #setOptions.
 * @config {boolean} labelOnTop If false the label will appear to the left
 * of the <code>select</code> element, aligned with the first list option. 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.selectBase.prototype.setProps = function(props) {
    // Always call inherited setProps
    //
    if (props == null) {
	return null;
    }

    // Set a flag indicating there is a label
    //
    var havelabel = this.label != null && this.label.id != null;

    // Always update the brNode state, even if there isn't a label
    // but only if it is changing.
    //
    var labelProps = null;
    if (props.labelOnTop != null && props.labelOnTop != this.labelOnTop) {

	// The ontop state is changing
	// If the state is "ontop" then make the br node 
	// visible else hide it.
	//
	webui.@THEME@.common.setVisibleElement(this.brNode, props.labelOnTop);

	// Remember the new ontop selector.
	//
	this._lastLabelOnTopClassName = this.getLabelClassName(
	    props.labelOnTop);

	// If we have a label remove the last ontop selector from the label.
	// We must strip it from the dom node.
	//
	if (havelabel) {

	    var labelnode = document.getElementById(this.label.id);
	    webui.@THEME@.common.stripStyleClass(labelnode,
		this._lastLabelOnTopClassName);

	    // If we are toggling ontop for an existing label or the
	    // application is updating or creating a label, add the
	    // correct ontop selector to props.
	    //
	    var labelProps = props.label != null 
		? props.label 
		: (props.label = {});

	} else if (props.label && this._lastLabelOnTopClassName != null) {
	    labelProps = props.label;
	    webui.@THEME@.common.addStyleClass(labelProps, 
		this._lastLabelOnTopClassName);
	}
    }

    var togglerequired = props.required != null && 
	props.required != this.required;
    var togglevalid = props.valid != null && props.valid != this.valid;

    // Update the label required and valid properties if necessary.
    // If labelProps is not null, then the labelOnTop property is changing.
    // If labelProps is null, then if there is an existing label and
    // the required or valid attributes is changing, make sure label.props
    // is updated with the new required and valid states.
    //
    if (labelProps == null && havelabel && (togglerequired || togglevalid)) {
	labelProps = props.label != null ? props.label : (props.label = {});
    }

    if (labelProps != null) {
	if (togglerequired) {
	    labelProps.required = props.required;
	}
	if (togglevalid) {
	    labelProps.valid = props.valid;
	}
    }
    return this.inherited("setProps", arguments);
};

/**
 * This function is used to set widget properties.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {Object} label The properties for a label widget.
 * @config {Object} options An array of Objects that represent the 
 * <code>select</code> element's <code>option</code> and 
 * <code>optgroup</code> elements. @see #setOptions.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.selectBase.prototype._setProps = function(props) {
    // Always call inherited _setProps
    //
    if (props == null) {
	return null;
    }

    // A web app devleoper could return false in order to cancel the 
    // auto-submit. Thus, we will handle this event via the onChange
    // call back.
    //
    if (props.onChange) {
        // Set private function scope on DOM node.
        this.listContainer._onchange = (typeof props.onChange == 'string')
	    ? new Function("event", props.onChange) 
	    : props.onChange;

        // Must be cleared before calling setEventProps() below.
        props.onChange = null;
    }

    if (props.disabled != null) {
	if (this.listContainer.disabled != props.disabled) {
	    this.listContainer.disabled = props.disabled;
	}
    }

    // Add option and optgroup elements in the select element
    //
    if (props.options) {
        this.setOptions(props);
    }

    // If _setProps is called during initializat then we will be
    // creating the label and props.label == this.label.
    // If _setProps is called from setProps, then we are updating the
    // label. The label needs to be updated if the labelOnTop or
    // or required or valid properties are changed.
    // The application may also be creating a label after the
    // selectBase widget was created.
    //
    if (props.label) {
	// Now update or create the label.
	// If we don't have an existing label, this.label.id == null
	// then call addFragment in case the application is
	// creating the label after the selectBase widget was created.
	//
	if (this.label != null && this.label.id != null) {
	    this.widget.updateFragment(this.labelContainer, this.label.id,
		props.label);
	} else {
	    this.widget.addFragment(this.labelContainer, props.label);
	}
    }

    // Set more properties.
    this.setCommonProps(this.listContainer, props);
    this.setEventProps(this.listContainer, props);

    // Set remaining properties.
    //
    return this.inherited("_setProps", arguments);
};
