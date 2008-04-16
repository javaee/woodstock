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

@JS_NS@._dojo.provide("@JS_NS@._html.scheduler");
@JS_NS@._dojo.require("@JS_NS@._base.proto");

/** 
 * @class This class contains functions for scheduler components.
 * @static
 * @private
 */
@JS_NS@._html.scheduler = {
    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @config {String} datePickerId
     * @config {String} dateFieldId
     * @config {String} dateClass
     * @config {String} selectedClass
     * @config {String} edgeClass
     * @config {String} edgeSelectedClass
     * @config {String} todayClass
     * @config {String} dateFormat
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize scheduler.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Set given properties on domNode.
        @JS_NS@._base.proto._extend(domNode, props, false);
        domNode.dateLinkId = props.datePickerId + ":dateLink"; 

        // Set functions.
        domNode.setSelected = @JS_NS@._html.scheduler.setSelected;
        domNode.setDateValue = @JS_NS@._html.scheduler.setDateValue; 
        domNode.isToday = @JS_NS@._html.scheduler.isToday;

        return true;
    },

    /**
     * Set selected link.
     *
     * @param {String} value
     * @param {Node} link
     * @return {boolean} true if successful; otherwise, false.
     */
    setDateValue: function(value, link) {
        var widget = @JS_NS@.widget.common.getWidget(this.dateFieldId);
        if (widget) {
            widget.setProps({value: value});
        }
        return this.setSelected(link);	
    },

    /**
     * Set selected.
     *
     * @param {Node} link
     * @return {boolean} true if successful; otherwise, false.
     */
    setSelected: function(link) {
        if (link == null) {
            return false;
        }

        var dateLink;	
        var linkNum = 0;	

        // Remove any prior highlight 
        while (linkNum < 42) {
            dateLink = document.getElementById(this.dateLinkId + linkNum);  
            if (dateLink == null) {
                break;    
            }

            if (dateLink.className == this.edgeSelectedClass) {
                dateLink.className = this.edgeClass;
            } else if (dateLink.className == this.selectedClass) {
                if (this.isToday(dateLink.title)) {
                    dateLink.className = this.todayClass;
                } else {
                    dateLink.className = this.dateClass;
                }
            }
            linkNum++;
        }

        // apply the selected style to highlight the selected link
        if (link.className == this.dateClass || 
            link.className == this.todayClass) {	
            link.className = this.selectedClass;
        } else if (link.className == this.edgeClass) {
            link.className = this.edgeSelectedClass;
        }
        this.currentSelection = link;
        return true;
    },

    /**
     * Find out if date is today's date.
     *
     * @param {Object} date
     * @return {boolean} true if date is today.
     */
    isToday: function(date) {
        var todaysDate = new Date();
        var pattern = new String(this.dateFormat); 
        var yearIndex = pattern.indexOf("yyyy"); 
        var monthIndex = pattern.indexOf("MM"); 
        var dayIndex = pattern.indexOf("dd"); 
        var currYear = todaysDate.getFullYear(); 
        var currMonth = todaysDate.getMonth() + 1; 
        var currDay = todaysDate.getDate(); 

        if (currYear == parseInt(date.substr(yearIndex, 4))
            && currMonth == parseInt(date.substr(monthIndex, 2))
                && currDay == parseInt(date.substr(dayIndex, 2))) {
            return true;
        }
        return false;
    }
};
