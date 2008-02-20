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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.checkedGroupBase");

webui.@THEME@.dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.checkedGroupBase
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for widgets that extend checkedGroupBase.
 * @static
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.checkedGroupBase", webui.@THEME@.widget.widgetBase);

/**
 * Helper function to add elements with Object literals.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {boolean} disabled 
 * @config {Array} columns 
 * @config {Array} contents 
 * @config {Object} label
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.checkedGroupBase.prototype.addContents = function(props) {   
    if (props == null) {
        return false;
    }
    
    if (props.contents) {
        this.widget.removeChildNodes(this.tbodyContainer);
        var rowContainerCloneNode = this.rowContainer.cloneNode(false);
        this.tbodyContainer.appendChild(rowContainerCloneNode);
        if (props.label) {
            var rowNodeClone = this.rowNode.cloneNode(false);
            rowContainerCloneNode.appendChild(rowNodeClone);
            var labelContainerClone = this.labelContainer.cloneNode(false);
            rowNodeClone.appendChild(labelContainerClone);
            this.widget.addFragment(labelContainerClone, props.label, "last");            
              
        }
        var itemN = 0;
        var length = props.contents.length;
        var columns = (props.columns <= 0) ? 1 : props.columns;
        var rows = (length + (columns-1))/columns;
        var propsdisabledvalue = props.disabled == null ? false : props.disabled;
        for (var row = 0; row <= rows; row++) {
            for (var column = 0; column < columns; column++) {
                if (itemN < length) {
                    // Clone < td> node.
                    var contentsRowNodeClone = this.contentsRowNode.cloneNode(false);
                    rowContainerCloneNode.appendChild(contentsRowNodeClone);
                    // Set disabled.                   
                    props.contents[itemN].disabled = propsdisabledvalue;
                   
                    // Add child to the group.
                    this.widget.addFragment(contentsRowNodeClone, props.contents[itemN], "last");
                    itemN++;
                }
            }
            if (row + 1 <= rows) {
                rowContainerCloneNode = this.rowContainer.cloneNode(false);
                this.tbodyContainer.appendChild(rowContainerCloneNode);
                // This check is required here. Else the elements won't be
                // aligned properly when there is no label.
                if (props.label != null) {
                    var contentsRowNodeClone = this.contentsRowNode.cloneNode(false);
                    rowContainerCloneNode.appendChild(contentsRowNodeClone);
                }
              
            }
        }
    } else {
        // Update the disabled property client side
        if (props.disabled != null && this.contents) {
            for (var i = 0; i < this.contents.length; i++) {
                var contentWidget = webui.@THEME@.dijit.byId(this.contents[i].id);
                if (contentWidget) {
                    contentWidget.setProps({disabled: props.disabled});
                }
            }
        }
    }
    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.checkedGroupBase.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.     
    if (this.columns) { props.columns = this.columns; }
    if (this.contents) { props.contents = this.contents; }    
    if (this.disabled != null) { props.disabled = this.disabled; }   
    if (this.id) { props.id = this.id; }
    if (this.label) { props.label = this.label; }    
    if (this.name) { props.name = this.name; }
    if (this.readOnly != null) { props.readOnly = this.readOnly; }  

    return props;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.checkedGroupBase.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {                    
        this.contentsRowNode.id = this.id + "_contentsRowNode";
        this.divContainer.id = this.id + "_divContainer";
        this.labelContainer.id = this.id + "_labelContainer";                            
        this.rowContainer.id = this.id + "_rowContainer";
        this.rowNode.id = this.id + "_rowNode";
        this.tableContainer.id = this.id + "_tableContainer";   
        this.tbodyContainer.id = this.id + "_tbodyContainer";     
    }

    // Show label.
    if (this.label) {
        this.common.setVisibleElement(this.rowNode, true);
    }
    return this.inherited("postCreate", arguments);
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
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.checkedGroupBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.contents) {
        this.contents = null;
    }

    // Extend widget object for later updates.
    return this.inherited("setProps", arguments);
}

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
webui.@THEME@.widget.checkedGroupBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set label properties.
    if (props.label) {
        // Update/add fragment.
        this.widget.updateFragment(this.labelContainer, this.label.id, props.label);
    }

    // Set contents.    
    if (props.contents || props.disabled != null) {              
        this.addContents(props);   
    }

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}
