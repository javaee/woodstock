/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

define(["webui/suntheme/props","webui/suntheme/common"], function(props, common) {
    
    return {

/**
     * Use this function to access the HTML select element that makes up
     * the list. 
     *
     * @param elementId The component id of the JSF component (this id is
     * assigned to the span tag enclosing the HTML elements that make up
     * the list).
     * @return a reference to the select element. 
     */
    getSelectElement: function(elementId) { 
        var element = document.getElementById(elementId); 
        if(element != null) { 
            if(element.tagName == "SELECT") { 
                return element; 
            }
        }
        return document.getElementById(elementId + "_list");
    },

    /**
     * This function is invoked by the list onselect action to set the selected, 
     * and disabled styles.
     *
     * Page authors should invoke this function if they set the selection
     * using JavaScript.
     *
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return true if successful; otherwise, false
     */
    changed: function(elementId) { 
        var cntr = 0; 
        var listItem = this.getSelectElement(elementId).options;

        //disabled items should not be selected (IE problem)
        //So setting selectedIndex = -1 for disabled selected items.
    
        if(common.browser.is_ie) {
            for(var i = 0;i < listItem.length;++i) {
               if(listItem[i].disabled == true && 
                            listItem[i].selected == true ) {
                
                  listItem.selectedIndex = -1;
            
               }
            }
        }    
        while(cntr < listItem.length) { 
            if(listItem[cntr].selected) {
                listItem[cntr].className = props.listbox.optionSelectedClassName;
            } else if(listItem[cntr].disabled) {
                listItem[cntr].className = props.listbox.optionDisabledClassName;
            } else {
                // This does not work on Opera 7. There is a bug such that if 
                // you touch the option at all (even if I explicitly set
                // selected to false!), it goes back to the original
                // selection. 
                listItem[cntr].className = props.listbox.optionClassName;
            }
            ++ cntr;
        }
        return true;
    },

    /**
     * Invoke this JavaScript function to set the enabled/disabled state
     * of the listbox component. In addition to disabling the list, it
     * also changes the styles used when rendering the component. 
     *
     * Page authors should invoke this function if they dynamically
     * enable or disable a list using JavaScript.
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @param disabled true or false
     * @return true if successful; otherwise, false
     */
    setDisabled: function(elementId, disabled) {
        var listbox = this.getSelectElement(elementId); 
        var regular = props.listbox.className;
        var _disabled = props.listbox.disabledClassName;

        if(listbox.className.indexOf(props.listbox.monospaceClassName) > 1) {
            regular = props.listbox.monospaceClassName;
            _disabled = props.listbox.monospaceDisabledClassName;
        }
        if(disabled) {
            listbox.disabled = true;
            listbox.className = _disabled;
        } else {
            listbox.disabled = false;
            listbox.className = regular;
        }
        return true;
    },

    /**
     * Invoke this JavaScript function to get the value of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The value of the selected option, or null if none is
     * selected. 
     */
    getSelectedValue: function(elementId) { 
        var listbox = this.getSelectElement(elementId); 
        var index = listbox.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return listbox.options[index].value; 
        }
    },

    /**
     * Invoke this JavaScript function to get the label of the first
     * selected option on the listbox. If no option is selected, this
     * function returns null. 
     * 
     * @param elementId The component id of the JSF component (this id is
     * rendered in the div tag enclosing the HTML elements that make up
     * the list).
     * @return The label of the selected option, or null if none is selected. 
     */
    getSelectedLabel: function(elementId) {
        var listbox = this.getSelectElement(elementId); 
        var index = this.selectedIndex; 
        if(index == -1) { 
            return null; 
        } else { 
            return listbox.options[index].label; 
        }
    }
};
});