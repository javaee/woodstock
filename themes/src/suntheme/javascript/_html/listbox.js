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

@JS_NS@._dojo.provide("@JS_NS@._html.listbox");

@JS_NS@._dojo.require("@JS_NS@.widget.common");

/**
 * @class This class contains functions for listbox components.
 * @static
 * 
 * @deprecated See @JS_NS@.widget.listbox
 * @private
 */
@JS_NS@._html.listbox = {
    /**
     * This function is invoked by the list onselect action to set the selected, 
     * and disabled styles.
     *
     * Page authors should invoke this function if they set the selection
     * using JavaScript.
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(elementId).changed();
     */
    changed: function(elementId) {         
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget._changed();
        }
        return false;
    },

    /**
     * Use this function to access the HTML select element that makes up
     * the list. 
     *
     * @param {String} elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the list).
     * @return {Node} The HTML select element.
     * @deprecated Use document.getElementById(elementId).getSelectElement()
     */
    getSelectElement: function(elementId) { 
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.getSelectElement();
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {String} The label of the selected option, or null if none is selected.
     * @deprecated Use document.getElementById(elementId).getSelectedLabel();
     */
    getSelectedLabel: function(elementId) { 
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.getSelectedLabel();
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return {String} The value of the selected option, or null if none is
     * selected.
     * @deprecated Use document.getElementById(elementId).getSelectedValue();
     */
    getSelectedValue: function(elementId) { 
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.getSelectedValue();
        }
        return null;
    },

    /**
     * Invoke this JavaScript function to set the enabled/disabled state
     * of the listbox component. In addition to disabling the list, it
     * also changes the styles used when rendering the component. 
     *
     * Page authors should invoke this function if they dynamically
     * enable or disable a list using JavaScript.
     * 
     * @param {String} elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param {boolean} disabled true or false
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated Use document.getElementById(elementId).setProps({disabled: boolean});
     */
    setDisabled: function(elementId, disabled) { 
        var widget = @JS_NS@.widget.common.getWidget(elementId);
        if (widget) {
            return widget.setProps({disabled: disabled});
        }
        return null;
    }
};

// Extend for backward compatibility with JSF based components.
@JS_NS@.listbox = @JS_NS@._html.listbox;
