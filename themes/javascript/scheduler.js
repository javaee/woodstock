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

//<!--
// The contents of this file are subject to the terms
// of the Common Development and Distribution License
// (the License).  You may not use this file except in
// compliance with the License.
// 
// You can obtain a copy of the license at
// https://woodstock.dev.java.net/public/CDDLv1.0.html.
// See the License for the specific language governing
// permissions and limitations under the License.
// 
// When distributing Covered Code, include this CDDL
// Header Notice in each file and include the License file
// at https://woodstock.dev.java.net/public/CDDLv1.0.html.
// If applicable, add the following below the CDDL Header,
// with the fields enclosed by brackets [] replaced by
// you own identifying information:
// "Portions Copyrighted [year] [name of copyright owner]"
// 
// Copyright 2007 Sun Microsystems, Inc. All rights reserved.
//

dojo.provide("webui.@THEME@.scheduler");

dojo.require("webui.@THEME@.formElements");

/** 
 * Define webui.@THEME@.scheduler name space. 
 */ 
webui.@THEME@.scheduler = {
    /**
     * This function is used to initialize HTML element properties with the
     * following Object literals.
     *
     * <ul>
     *  <li>id</li>
     *  <li>datePickerId</li>
     *  <li>dateFieldId</li>
     *  <li>dateClass</li>
     *  <li>selectedClass</li>
     *  <li>edgeClass</li>
     *  <li>edgeSelectedClass</li>
     *  <li>todayClass</li>
     *  <li>dateFormat</li>
     * </ul>
     *
     * Note: This is considered a private API, do not use.
     *
     * @param props Key-Value pairs of properties.
     */
    init: function(props) {
        if (props == null || props.id == null) {
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            return false;
        }

        // Set given properties on domNode.
        Object.extend(domNode, props);
        domNode.dateLinkId = props.datePickerId + ":dateLink"; 

        // Set functions.
        domNode.setSelected = webui.@THEME@.scheduler.setSelected;
        domNode.setDateValue = webui.@THEME@.scheduler.setDateValue; 
        domNode.isToday = webui.@THEME@.scheduler.isToday;	
    },

    setDateValue: function(value, link) {
        webui.@THEME@.field.setValue(this.dateFieldId, value); 
        this.setSelected(link);	
    },

    setSelected: function(link) {
        if (link == null) {
            return;
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
        } else if (link.className = this.edgeClass) {
            link.className = this.edgeSelectedClass;
        }
        this.currentSelection = link;	
    },

    // Find out if date is today's date
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
}

//-->
