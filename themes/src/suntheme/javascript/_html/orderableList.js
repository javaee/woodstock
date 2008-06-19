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

@JS_NS@._dojo.provide("@JS_NS@._html.orderableList");

@JS_NS@._dojo.require("@JS_NS@._base.proto");

/** 
 * @class This class contains functions for orderableList components.
 * @static
 * @private
 */
@JS_NS@._html.orderableList = {
    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @config {String} moveMessage
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize orderableList.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Not a facet does not have "extra" editable list id.

        // The select element from which selections are made 
        domNode.list = document.getElementById(props.id + "_list");

        // Bug 6338492 -
        //     ALL: If a component supports facets or children is must be a
        //      NamingContainer
        // Since OrderableList has become a NamingContainer the id's for
        // the facet children are prefixed with the OrderableList id
        // in addition to their own id, which also has the 
        // OrderableList id, as has been the convention for facets. This introduces
        // a redundancy in the facet id so the moveUp button now looks like
        //
        // "formid:orderablelistid:orderablelistid:orderablelistid_moveUpButton"
        //
        // It used to be "formid:orderablelistid_moveUpButton"
        // It would be better to encapsulate that knowledge in the
        // OrderableList renderer as does FileChooser which has the
        // same problem but because the select elements are not
        // facets in OrderableList they really do only have id's of the
        // form "formid:orderablelistid_list". Note that 
        // in these examples the "id" parameter is "formid:orderablelistid"
        //
        // Therefore for now, locate the additional prefix here as the
        // "facet" id. Assume that id never ends in ":" and if there is
        // no colon, id is the same as the component id.
        //
        var componentid = props.id;
        var colon_index = componentid.lastIndexOf(':');
        if (colon_index != -1) {
            componentid = props.id.substring(colon_index + 1);
        }
        var facetid = props.id + ":" + componentid;

        domNode.moveUpButton = document.getElementById(facetid + "_moveUpButton");
        domNode.moveDownButton = document.getElementById(facetid + "_moveDownButton");
        domNode.moveTopButton = document.getElementById(facetid + "_moveTopButton");
        domNode.moveBottomButton = document.getElementById(facetid + "_moveBottomButton");

        // Not a facet
        domNode.values = document.getElementById(props.id + "_list_value");

        // HTML elements may not have been created, yet.

        // Set given properties on domNode.
        @JS_NS@._base.proto._extend(domNode, props, false);

        // The options of the select element from which selections are made 
        domNode.options = domNode.list.options;

        // The messages
        if (domNode.moveMessage == null) {
            "Select at least one item to remove";
        }

        // Set functions.
        domNode.moveUp = @JS_NS@._html.orderableList.moveUp;
        domNode.moveDown = @JS_NS@._html.orderableList.moveDown;
        domNode.moveTop = @JS_NS@._html.orderableList.moveTop;
        domNode.moveBottom = @JS_NS@._html.orderableList.moveBottom;
        domNode.updateButtons = @JS_NS@._html.orderableList.updateButtons;
        domNode.updateValue = @JS_NS@._html.orderableList.updateValue;
        domNode.onChange = @JS_NS@._html.orderableList.updateButtons;

        // Initialize buttons.
        domNode.updateButtons();
        return true;
    },

    /**
     * The original allowed items to be moved on both lists. Surely we
     * only sort items on the selected list? 
     * This does not work on Mozilla
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    moveUp: function() {
        var numOptions = this.options.length;
    
        // If there aren't at least two more selected items, then there is
        // nothing to move 
        if (numOptions < 2) {
            return false;
        }

        // Start by examining the first item 
        var index = 0;

        // We're not going to move the first item. Instead, we will start
        // on the first selected item that is below an unselected
        // item. We identify the first unselected item on the list, and 
        // then we will start on next item after that
        while (this.options[index].selected) {
            ++index;
            if (index == numOptions) {
                // We've reached the last item - no more items below it so
                // we return
                return false;
            }
        }

        // Start on the item below this one 
        ++index;

        for (index; index < numOptions; ++index) {
            if (this.options[index].selected == true) {
                var curOption = this.options[index];
                if (this.options.remove == null) {
                    // For Mozilla
                    this.options[index] = null;
                    this.list.add(curOption, this.options[index - 1]);
                } else {
                    // Windows and Opera do
                    this.options.remove(index);
                    this.options.add(curOption, index - 1);
                }
                // This is needed for Opera only
                this.options[index].selected = false;
                this.options[index - 1].selected = true;
            }
        }
        this.updateValue();
        return this.updateButtons();
    },

    /**
     * The original allowed items to be moved on both lists. Surely we
     * only sort items on the selected list? 
     * This does not work on Mozilla
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    moveTop: function() {
        var numOptions = this.options.length;
        // If there aren't at least two items, there is nothing to move  
        if (numOptions < 2) {
            return false;
        }

        // Find the first open spot 
        var openSpot = 0;
        while (this.options[openSpot].selected) {
            openSpot++;
        }

        // Find the first selected item below it
        var index = openSpot+1;

        for (index; index < numOptions; ++index) {
            if (this.options[index].selected == true) {
                var curOption = this.options[index];
                if (this.options.remove == null) {
                    // For Mozilla
                    this.options[index] = null;
                    this.list.add(curOption, this.options[openSpot]);
                } else {
                    // Windows and Opera do
                    this.options.remove(index);
                    this.options.add(curOption, openSpot);
                }

                // This is needed for Opera only
                this.options[index].selected = false;
                this.options[openSpot].selected = true;
                openSpot++;
            }
        }
        this.updateValue();
        return this.updateButtons();
    },

    /** 
     * The original allowed items to be moved on both lists. Surely we
     * only sort items on the selected list? 
     * This does not work on Mozilla
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    moveDown: function() {
        // Get the last item
        var index = this.options.length - 1;
    
        // If this number is less than zero, there was nothing on the list
        // and we return
        if (index < 0) {
            return false;
        }

        for (var i = index - 1; i >= 0; i--) {
            if (this.options[i].selected) {          
                var next = i + 1;
                if (this.options[next].selected == false) {
                    tmpText = this.options[i].text;
                    tmpValue = this.options[i].value;
                    this.options[i].text = this.options[next].text;
                    this.options[i].value = this.options[next].value;
                    this.options[i].selected = false;
                    this.options[next].text = tmpText;
                    this.options[next].value = tmpValue;
                    this.options[next].selected = true;
                }
            }
        }

        this.updateValue();
        return this.updateButtons();
    },

    /**
     * Move options to bottom.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    moveBottom: function() {
        var numOptions = this.options.length - 1;

        // If there aren't at least two items, there is nothing to move  
        if (numOptions < 1) {
            return false;
        }

        // Find the last open spot 
        var openSpot = numOptions;
        while (this.options[openSpot].selected) {
            openSpot--;
        }

        // Find the first selected item above it
        var index = openSpot-1;

        for (index; index > -1; --index) {
            if (this.options[index].selected == true) {
                var curOption = this.options[index];
	        if (this.options.remove == null) {
                    // For Mozilla
                    this.options[index] = null;
                    this.list.add(curOption, this.options[openSpot+1]);
                } else {
                    // Windows and Opera do
                    this.options.remove(index);
                    this.options.add(curOption, openSpot);
                }

                // This is needed for Opera only
                this.options[index].selected = false;
                this.options[openSpot].selected = true;
                openSpot--;
            }
        }
        this.updateValue();
        return this.updateButtons();
    },

    /**
     * Update buttons.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    updateButtons: function() {
        var numOptions = this.options.length;
        var selectedIndex = this.options.selectedIndex;
        var disabled = true;
        var index;

        // First, check if move down and move to bottom should be
        // enabled. These buttons should be enabled if and only if at
        // least one of the items are selected and there is at least one
        // open spot below a selected item. 
        if (selectedIndex > -1 && selectedIndex < numOptions -1) {
            index = selectedIndex+1;
            while (index < numOptions) {
                if (this.options[index].selected == false) {
                    disabled = false;
                    break;
                }
                index++;
            }
        }

        if (this.moveDownButton != null) {
            if (this.moveDownButton.setDisabled != null) {
                this.moveDownButton.setDisabled(disabled);
            } else {
                this.moveDownButton.disabled = disabled;
            }
        }

        if (this.moveBottomButton != null) {
            if (this.moveBottomButton.setDisabled != null) {
                this.moveBottomButton.setDisabled(disabled);
            } else {
                this.moveBottomButton.disabled = disabled;
            }
        }

        // First, check if move up and move to top should be
        // enabled. These buttons should be enabled if and only if at
        // least one of the items is selected and there is at least one
        // open spot above a selected item. 
        disabled = true;

        if (selectedIndex > -1) {
            index = numOptions - 1;
            while (index > 0) {
                if (this.options[index].selected) {
                    break;
                }
                index--;
            }
            index--;
            while (index > -1) {
                if (this.options[index].selected == false) {
                    disabled = false;
                    break;
                }
                index--;
            }
        }

        if (this.moveUpButton != null) {
            if (this.moveUpButton.setDisabled != null) {
                this.moveUpButton.setDisabled(disabled);
            } else {
                this.moveUpButton.disabled = disabled;
            }
        }

        if (this.moveTopButton != null) {
            if (this.moveTopButton.setDisabled != null) {
                this.moveTopButton.setDisabled(disabled);
            } else {
                this.moveTopButton.disabled = disabled;
            }
        }
        return true;
    },

    /**
     * Update value.
     *
     * @return {boolean} true if successful; otherwise, false.
     */
    updateValue: function() {
        // Remove the options from the select that holds the actual
        // selected values
        while (this.values.length > 0) {
            this.values.remove(0);
        }

        // Create a new array consisting of the options marked as selected
        // on the official list
        var newOptions = new Array();
        var cntr = 0;
        var newOption;

        while (cntr < this.options.length) {
            newOption = document.createElement("option");
            if (this.options[cntr].text != null) {
                newOption.text = this.options[cntr].text;
            }
            if (this.options[cntr].value != null) {
                newOption.value = this.options[cntr].value;
            }
            newOption.selected = true;
            newOptions[newOptions.length] = newOption;
            ++ cntr;
        }
        cntr = 0;
        if (this.options.remove == null) {
            // For Mozilla
            while (cntr < newOptions.length) {
                this.values.add(newOptions[cntr], null);
                ++cntr;
            }
        } else {
            // Windows and Opera do
            while (cntr < newOptions.length) {
                this.values.add(newOptions[cntr], cntr);
                ++cntr;
            }
        }
        return true;
    }
};
