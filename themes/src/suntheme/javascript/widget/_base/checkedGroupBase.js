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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.checkedGroupBase");

@JS_NS@._dojo.require("@JS_NS@.widget._base.labeledBase");

/**
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.checkedGroupBase
 * @extends @JS_NS@.widget._base.labeledBase
 * @class This class contains functions for widgets that extend checkedGroupBase.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} align Alignment of image input.
 * @config {String} className CSS selector.
 * @config {int} columns 
 * @config {Array} contents 
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {String} name 
 * @config {boolean} readOnly Set button as primary if true.
 * @config {String} style Specify style rules inline.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.checkedGroupBase",
    @JS_NS@.widget._base.labeledBase);

/**
 * Helper function to add elements with Object literals.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} disabled 
 * @config {Array} columns 
 * @config {Array} contents 
 * @config {Object} label
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.checkedGroupBase.prototype._addContents = function(props) {   

    // The label needs special handling because the labeledBase super
    // class is handling the update and creation of the label. The dependence
    // is on "this._labelContainer". We do not want to delete it and
    // create or clone a new one. We need to save the current 
    // "_labelContainer" instance and add it to the new contents.
    //
    // Obviously the whole table layout of rb's and cb's is fragile,
    // and inefficient, especially with respect to the label which
    // resides in the first cell of the first column.
    // Use the "rowspan" attribute to simplify the problem.
    // 
    // This implementation is inefficient if the only properties 
    // of one of the content objects is changing. Therefore 
    // individual rb's and cb's  should be manipulated directly and 
    // not by a
    //
    // "{contents : [ ...,{ ...checked: false...},...]"
    //
    if (props != null && props.contents != null) {

	// Get rid of the current contents
	// An empty "props.content", "{ content : [] }" this will remove
	// all the contents.
	//
	this._widget._removeChildNodes(this._tbodyContainer);

	// In case we are updating, use the columns on the group
	// instance, it may not be specified in props in a call
	// to "setProps".
	//
	var columns = this.columns != null && this.columns > 0
		? this.columns
		: 1;
	if (props.columns != null && props.columns > 0) {
	    columns = props.columns;
	}
	    
        var length = props.contents.length;
        var rows = (length + (columns-1))/columns;

	// Remember if the group is disabled or not.
	// We need to check "this" in case we are being called from
	// "setProps".
	//
        var disabled = this.disabled == null ? false : this.disabled;
	if (props.disabled != null) {
	    disabled = props.disabled;
	}
        var itemN = 0;
	var row = 0;

	// If we have content and there is a label, create the first
	// row with the label. This will set "row" to 1, so the
	// following loop over rows and columns starts at the second
	// row.
	//
	// Note that the label will not exist as a widget the first time
	// through when the group widget is being created.
	//
	var labelNode = null;
	if (rows > 0 && this.label) {
	    var rowContainer = this._rowContainer.cloneNode(false);
	    labelNode = this._labelNode.cloneNode(false);
	    labelNode.id = "label";
	    rowContainer.appendChild(labelNode);
	    labelNode.setAttribute("rowSpan", rows);
	    labelNode.appendChild(this._labelContainer);

	    // Special case for first row, when we have a label.
	    //
	    this._tbodyContainer.appendChild(rowContainer);
	    for (var i = 0; i < columns; ++i, ++itemN) {
		var content = this._contentNode.cloneNode(false);
		rowContainer.appendChild(content);
		// Set disabled.                   
		//
		props.contents[itemN].disabled = disabled;;
		// Add child to the group.
		//
		this._widget._addFragment(
		    content, props.contents[itemN], "last");
	    }
	    row = 1;
	}

        for (; row < rows; row++) {
	    var rowContainer = this._rowContainer.cloneNode(false);
	    this._tbodyContainer.appendChild(rowContainer);
            for (var column = 0; column < columns; column++, itemN++) {
		// Clone <td> node.
		// There could be issues here by not doing a deep clone.
		// There could be "decorative" elements in the td node
		// in the template, but the shallow clone, ignores that.
		//
		var content = this._contentNode.cloneNode(false);
		// In case we have a sparse last row
		//
                if (itemN < length) {
                    rowContainer.appendChild(content);
                    // Set disabled.                   
		    //
                    props.contents[itemN].disabled = disabled;
                   
                    // Add child to the group.
		    //
                    this._widget._addFragment(
			content, props.contents[itemN], "last");
                } else {
		    // We could try using "colspan" on a sparse last row.
		    //
                    rowContainer.appendChild(content);
		}
            }
        }
    }
    return true;
};

/**
 * Return an Object Literal of label properties desired
 * by the checkedGroupBase widget.
 * <p>
 * This implementation adds the <code>htmlFor</code> property assigning
 * it the input element in the first row and the first column of the group.
 * </p>
 * @return {Object} object with the <code>htmlFor</code>
 * property set.
 * @private
 */
@JS_NS@.widget._base.checkedGroupBase.prototype._getLabelProps = function() {
    // Let the super class contribute
    //
    var props = this.inherited("_getLabelProps", arguments);
    // Subclasses can override this value
    //
    props.level = 2;
    return props;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget._base.checkedGroupBase.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.     
    if (this.columns != null) { props.columns = this.columns; }
    if (this.contents != null) { props.contents = this.contents; }    
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.id != null) { props.id = this.id; }
    if (this.name != null) { props.name = this.name; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }  

    return props;
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
@JS_NS@.widget._base.checkedGroupBase.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {                    
        this._contentNode.id = this.id + "_contentNode";
        this._divContainer.id = this.id + "_divContainer";
        this._rowContainer.id = this.id + "_rowContainer";
        this._labelNode.id = this.id + "_labelNode";
        this._tableContainer.id = this.id + "_tableContainer";   
        this._tbodyContainer.id = this.id + "_tbodyContainer";     
    }

    // Show label.
    if (this.label) {
        this._common._setVisibleElement(this._labelNode, true);
    }
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
@JS_NS@.widget._base.checkedGroupBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
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
@JS_NS@.widget._base.checkedGroupBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set contents.    
    if (props.contents) { // || props.disabled != null) {              
        this._addContents(props);

	// We have a control in the first cell and
	// the label's htmlFor value is not being updated explicityly and
	// we have or are creating a label, update the htmlFor 
	// label property.
	//
	if (props.contents.length != 0 && props.contents[0].id != null &&
		(props.label == null || props.label.htmlFor == null) &&
		this.label != null && this.label.id != null) {
	    if (props.label == null) {
		props.label = {};
	    }
	    var widget = this._widget.getWidget(props.contents[0].id);
	    if (widget != null && widget.getInputElement) {
		props.label.htmlFor = widget.getInputElement().id;
	    }
	}
    } else 
    if (props.disabled != null && this.contents != null) {

	// If props.contents is not null, then disabled will
	// be handled when the contents are updated.
	// Otherwise iterate over the contents disabling them
	//
	for (var i = 0; i < this.contents.length; i++) {
	    var contentWidget = this._widget.getWidget(this.contents[i].id);
	    if (contentWidget) {
		contentWidget.setProps({disabled: props.disabled});
	    }
        }
    }

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};
