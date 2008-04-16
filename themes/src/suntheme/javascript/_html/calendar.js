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

@JS_NS@._dojo.provide("@JS_NS@._html.calendar");

@JS_NS@._dojo.require("@JS_NS@._base.common");
@JS_NS@._dojo.require("@JS_NS@._base.proto");

/** 
 * @class This class contains functions for calendar components.
 * @deprecated See @JS_NS@.widget.calendar
 * @static
 * @private
 * @deprecated See @JS_NS@.widget.calendar
 */ 
@JS_NS@._html.calendar = {
    /**
     * This function is used to initialize HTML element properties with Object
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The element id.
     * @config {String} firstDay
     * @config {String} fieldId
     * @config {String} patternId
     * @config {String} calendarToggleId
     * @config {String} datePickerId
     * @config {String} monthMenuId
     * @config {String} yearMenuId
     * @config {String} rowId
     * @config {String} showButtonSrc
     * @config {String} hideButtonSrc
     * @config {String} dateFormat
     * @config {String} dateClass
     * @config {String} edgeClass
     * @config {String} selectedClass
     * @config {String} edgeSelectedClass
     * @config {String} todayClass
     * @config {String} hiddenClass
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize calendar.";
        if (props == null || props.id == null) {
            console.debug(message); // See Firebug console.
            return false;
        }
        var domNode = document.getElementById(props.id);
        if (domNode == null) {
            console.debug(message); // See Firebug console.
            return false;
        }

        // Get HTML elements.
        domNode.field = document.getElementById(props.fieldId);
        domNode.pattern = document.getElementById(props.patternId);
        domNode.calendarToggle = document.getElementById(props.calendarToggleId);
        domNode.lastRow = document.getElementById(props.rowId);

        var widget = @JS_NS@.widget.common.getWidget(props.monthMenuId);
        if (widget) {
            domNode.monthMenu = widget.getSelectElement();
        }
        widget = @JS_NS@.widget.common.getWidget(props.yearMenuId);
        if (widget) {
            domNode.yearMenu = widget.getSelectElement();
        }

        // HTML elements may not have been created, yet.
        if ((props.fieldId && domNode.field == null)
                || (props.patternId && domNode.pattern == null)
                || (props.calendarToggleId && domNode.calendarToggle == null)
                || (props.rowId && domNode.lastRow == null)
                || (props.monthMenuId && domNode.monthMenu == null)
                || (props.yearMenuId && domNode.yearMenu == null)) {
            return setTimeout(function() {
                @JS_NS@._html.calendar._init(props);
            }, 10);
        }

        // Set given properties on domNode.
        @JS_NS@._base.proto._extend(domNode, props, false);

        domNode.datePickerId = props.datePickerId;
        domNode.dateLinkId = props.datePickerId + ":dateLink";

        // Set functions.
        domNode.toggle = @JS_NS@._html.calendar.toggleCalendar;
        domNode.dayClicked = @JS_NS@._html.calendar.dayClicked;
        domNode.decreaseMonth = @JS_NS@._html.calendar.decreaseMonth;
        domNode.increaseMonth = @JS_NS@._html.calendar.increaseMonth;
        domNode.redrawCalendar = @JS_NS@._html.calendar.redrawCalendar;
        domNode.setCurrentValue = @JS_NS@._html.calendar.setCurrentValue;
        domNode.setDisabled = @JS_NS@._html.calendar.setDisabled;
        domNode.setInitialFocus = @JS_NS@._html.calendar.setInitialFocus;
        domNode.formatDate = @JS_NS@._html.calendar.formatDate;
        domNode.ieStackingContextFix = @JS_NS@._html.calendar.ieStackingContextFix;
        domNode.ieGetShim = @JS_NS@._html.calendar.ieGetShim;
        domNode.ieShowShim = @JS_NS@._html.calendar.ieShowShim;
        domNode.ieHideShim = @JS_NS@._html.calendar.ieHideShim;
        domNode.setSelectedValue = @JS_NS@._html.calendar.setSelectedValue;
        domNode.setLimitedSelectedValue = @JS_NS@._html.calendar.setLimitedSelectedValue;
        domNode.redrawPopup = @JS_NS@._html.calendar.redrawPopup;

        return true;
    },

    /**
     * This function is used by the day links in the calendar display to
     * set the date in the textfield. The day argument is the day as an
     * int, the monthFix is -1, 0, +1 and is used by the last days of the 
     * previous month and the next days of the next month.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    dayClicked: function(link) {
        //store old value
        var oldFieldValue = this.field.value;

        // get the current year
        var year = parseInt(this.yearMenu.options[this.yearMenu.selectedIndex].value);

        // get the current month
        var month = parseInt(this.monthMenu.options[this.monthMenu.selectedIndex].value);
        var day = parseInt(link.innerHTML);
        var monthFix = 0;
        var className = link.className;
        if(className == this.edgeClass) {
            if(day > 20) {
                monthFix = -1;
            } else if(day < 7) {
                monthFix = 1;
            }
        }

        if(monthFix != 0) {
            if(monthFix == -1) {
                if(month == 1) {
                    month = 13;
                    year--;
                }
            } else {
                if(month == 12) {
                    month = 0;
                    year++;
                }
            }
            month = month + monthFix;
        }
        this.field.value = this.formatDate(month,day,year);
        this.toggle();

        //manually call onchange if appropriate
        if (this.field.value != oldFieldValue && (typeof this.field.onchange == 'function')) {
             this.field.onchange();
        }
        return true;
    },

    /**
     * Decrease month.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    decreaseMonth: function() {
        // If the monthMenu has a zero value, set it to January
        // (that's what it will have appeared like in the browser).
        // This can happen on IE. 
        if(this.monthMenu.value == null) {
            this.monthMenu.value = this.monthMenu.options[0].value;
        }
        var month = parseInt(this.monthMenu.value);
        if(month == 1) {
            // If the yearMenu has no value (can happen on IE) 
            // set it to the first available year 
            // (that's what it will have appeared like in the browser).
            if(this.yearMenu.value == null) {
                this.yearMenu.value = this.yearMenu.options[0].value;
            } else if(this.yearMenu.value == this.yearMenu.options[0].value) {
                // No need to redraw the calendar in this case, we don't
                // change anything.
            } else {
                // Decrease the year by one and set the month to December
                var year = parseInt(this.yearMenu.value);
                year--;
                this.yearMenu.value = year;
                month = 12;
            }
        } else {
            month--;
        }
        this.monthMenu.value = month;
        return this.redrawCalendar(false);
    },

    /**
     * Increase month.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    increaseMonth: function() {
        // If the monthMenu has a zero value, set it to January
        // (that's what it will have appeared like in the browser).
        // This can happen on IE. 
        if(this.monthMenu.value == null) {
            this.monthMenu.value = this.monthMenu.options[0].value;
        }
        var month = parseInt(this.monthMenu.value);
        if(month == 12) {
            // If the yearMenu has no value (can happen on IE) 
            // set it to the first available year 
            // (that's what it will have appeared like in the browser).

            var numOptions = this.yearMenu.options.length;
            if(this.yearMenu.value == null) {
                this.yearMenu.value = this.yearMenu.options[0].value;
            } else if(this.yearMenu.value == 
                this.yearMenu.options[numOptions-1].value) {
                // No need to redraw the calendar in this case, we don't
                // change anything.
            } else {
                // Increase the year by one and set the month to January
                var year = parseInt(this.yearMenu.value);
                year++;
                this.yearMenu.value = year;
                month = 1;
            }
        } else {
            month++;
        }
        this.monthMenu.value = month;
        return this.redrawCalendar(false);
    },

    /**
     * Redraw calendar.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    redrawCalendar: function(initialize) {
        var selected = 0;   //if 1 - 31, will show that day as highlighted
	var today = 0;

        //selectedYear, selectedMonth, selectedDay:
	//the date to show as highlighted (this.currentValue or today's date)
        //provided that the user is viewing that month and year
        var selectedYear = null;
	var selectedMonth = null;
	var selectedDay = null;
        if (this.currentValue != null) {
        	selectedYear = this.currentValue.getFullYear();
        	selectedMonth = this.currentValue.getMonth() + 1;
        	selectedDay = this.currentValue.getDate();
	}

	var todayDate = new Date();
        var todayYear = todayDate.getFullYear();
        var todayMonth = todayDate.getMonth() + 1;
        var todayDay = todayDate.getDate();

        if(initialize) {
	    //set showMonth as selected in the monthMenu
	    //set showYear as selected in the yearMenu
            //use todayMonth and todayYear as "backups" (in case this.currentValue is null)
	    var showMonth = todayMonth;
	    var showYear = todayYear;
	    if (this.currentValue != null) {
                //we have a currentValue, so use that for showMonth and showYear
                showMonth = selectedMonth;
	        showYear = selectedYear;
            }
            this.setLimitedSelectedValue(this.monthMenu, showMonth);
            this.setLimitedSelectedValue(this.yearMenu, showYear);
 
       } else {
            //mbohm: preserving the following logic, but to my knowledge, it should not occur.
            if(this.yearMenu.value == null || this.monthMenu.value == null) {
                this.yearMenu.value = this.yearMenu.options[0].value;
                this.monthMenu.value = this.monthMenu.options[0].value;
            }
        }

        //set selected
        var yearMenuValue = parseInt(this.yearMenu.value);
        var monthMenuValue = parseInt(this.monthMenu.value);
        if(this.currentValue != null && selectedYear == yearMenuValue &&
            selectedMonth == monthMenuValue) {
            selected = selectedDay;
        }

	//set today
        if(todayYear == yearMenuValue &&
            todayMonth == monthMenuValue) {
            today = todayDay;
        }

        var month = parseInt(this.monthMenu.value);
        month--;
        var year = parseInt(this.yearMenu.value);

        // construct a date object for the newly displayed month
        var first = new Date(year, month, 1);
        var numDays = 31;
        var last = new Date(year, month, numDays + 1);

        while(last.getDate() != 1) {
            numDays--;
            last = new Date(year, month, numDays + 1);
        }

        // determine what day of the week the 1st of the new month is
        var linkNum = 0;
        var link;

        // Fill in any number of days before the first day of the month
        // On Firefox at least, JavaScript treats Sunday as the first day 
        // of the week regardless of the browser's locale. 
        // We have to compensate for the fact that Sunday is not the first
        // day of the week everywhere.
        var firstDay = first.getDay();

        // In JavaScript (unlike java.util.Calendar), Sunday is 0. 
        if(firstDay != this.firstDay) {
            var backDays = (firstDay - this.firstDay + 7) % 7;
            var oneDayInMs = 86400000; // 1000 * 60 * 60 * 24;
            var day = new Date(first.getTime() - backDays * oneDayInMs);
            // assert day == first day of week of previous month
            while (day.getMonth() !=  month) {
                link = document.getElementById(this.dateLinkId + linkNum);
                link.title = this.formatDate(day.getMonth() + 1, day.getDate(), 
                    day.getFullYear());
                link.className = this.edgeClass; 
                link.innerHTML = day.getDate();
                day.setTime(day.getTime() + oneDayInMs);
                linkNum++;
            }
        }
      
        var counter = 0;
        while(counter < numDays) {
            link = document.getElementById(this.dateLinkId + linkNum);
            link.innerHTML = ++counter;
            if(counter == selected) {
		link.className = this.selectedClass;
	    } else if (counter == today) {
		link.className = this.todayClass;
            } else {
                link.className = this.dateClass; 
            }
            link.title = this.formatDate(first.getMonth() + 1, counter,
                first.getFullYear());
            linkNum++;   
        }

        if(linkNum < 35) {
            counter = 1;
            while(linkNum < 35) {
                link = document.getElementById(this.dateLinkId + linkNum);
                link.className = this.edgeClass; 
                link.innerHTML = counter;
                link.title = this.formatDate(first.getMonth() + 2, counter, 
                    first.getFullYear());
                linkNum++;
                counter++;
            }
            this.lastRow.style.display = "none";
        } else if(linkNum == 35) {
            this.lastRow.style.display = "none";
        } else {
            counter = 1;
            while(linkNum < 42) {
                link = document.getElementById(this.dateLinkId + linkNum);
                link.className = this.edgeClass; 
                link.innerHTML = counter;
                link.title = this.formatDate(first.getMonth() + 2, counter, 
                    first.getFullYear());
                linkNum++;
                counter++;
            }
            this.lastRow.style.display = "";
        }
        return true;
    },

    /**
     * Set current value.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    setCurrentValue: function() {
        var curDate = this.field.value;
        var matches = true;
        if(curDate == "") {
            this.currentValue = null;
            return false;
        }

        var pattern = new String(this.dateFormat);
        var yearIndex = pattern.indexOf("yyyy");
        var monthIndex = pattern.indexOf("MM");
        var dayIndex = pattern.indexOf("dd");

        // If the format is invalid, set the current value to null
        if (yearIndex < 0 || monthIndex < 0 || dayIndex < 0) {
            this.currentValue = null;
            return false;
        }

        var counter = 0;
        var number;
        var selectedDate = new Date();
        var found = 0;
        var dateString;

        while(counter < curDate.length) {
            if(counter == yearIndex) {
                try {
                    number = parseInt(curDate.substr(counter, 4));
                    if (isNaN(number)) {
                        this.currentValue = null;
                        return false;
                    }
                    var index = 0;
                    var foundYear = false;
                    while(index < this.yearMenu.length) {
                        if(number == this.yearMenu.options[index].value) {
                            selectedDate.setFullYear(number);
                            ++found;
                            foundYear;
                        }
                        index++;
                    }
                    if(!foundYear) {
                        break;
                    }
                } catch(e) {}
            } else if(counter == monthIndex) {
                try {    
                    dateString = curDate.substr(counter, 2);
                    // This is a workaround for Firefox! 
                    // parseInt() returns 0 for values 08 and 09
                    // while for example 07 works! 
                    if(dateString.charAt(0) == '0') {
                        dateString = dateString.substr(1, 1);
                    }
                    number = parseInt(dateString);
                    if (isNaN(number)) {
                        this.currentValue = null;
                        return false;
                    }
                    selectedDate.setMonth(number-1);
                    ++found;
                } catch(e) {}
            } else if(counter == dayIndex) {
                try {
                    dateString = curDate.substr(counter, 2);
                    // This is a workaround for Firefox! 
                    // parseInt() returns 0 for values 08 and 09
                    // while all other leading zeros work
                    if(dateString.charAt(0) == '0') {
                        dateString = dateString.substr(1, 1);
                    }
                    number = parseInt(dateString);
                    if (isNaN(number)) {
                        this.currentValue = null;
                        return false;
                    }
                    selectedDate.setDate(number);
                    ++found;
                } catch(e) {}
            }
            ++counter;
        }

        if(found == 3) {
            this.currentValue = selectedDate;
        } else {
            this.currentValue = null;
        }
        return true;
    },

    /**
     * Toggle calendar open/closed.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    toggleCalendar: function() {
        var div = document.getElementById(this.datePickerId);
        div.style.position = "absolute";
        div.style.left = "5px";
        div.style.top = "24px";

        if(div.style.display == "block") {
            // hide the calendar popup
            div.style.display = "none";
            this.calendarToggle.src = this.showButtonSrc;
            this.ieStackingContextFix(div);
        } else {
            this.setCurrentValue();
            this.redrawCalendar(true);

            // display the calendar popup
            div.style.display = "block";
            this.calendarToggle.src = this.hideButtonSrc;

            // place focus on the month menu
            this.setInitialFocus();

            // workaround for initial display problem on mozilla
            // the problem manifests itself as follows: 
            // click the link to make the calendar show
            // click one of the triangular buttons - the display
            // "contracts"
            // ...except it doesn't work!
            //var actualClass = link.className;
            //link.className = "DatBldLnk";
            //link.className = actualClass;

            this.ieStackingContextFix(div);
            this.redrawPopup();
        }
        return true;
    },

    /**
     * Get left offset position.
     *
     * @return {int} Left offset position.
     * @private
     * @deprecated See @JS_NS@.widget.calendar
     */
    _findPosX: function(obj) {
        var curleft = 0;
        if (obj.offsetParent) {
            while (obj.offsetParent) {
                curleft += obj.offsetLeft;
                obj = obj.offsetParent;
            }
        } else if (obj.x) {
            curleft += obj.x;
        }
        return curleft;
    },

    /**
     * Get top offset position.
     *
     * @return {int} Top offset position.
     * @private
     * @deprecated See @JS_NS@.widget.calendar
     */
    _findPosY: function(obj) {
        var curtop = 0;
        if (obj.offsetParent) {
            while (obj.offsetParent) {
                curtop += obj.offsetTop;
                obj = obj.offsetParent;
            }
        } else if (obj.y) {
            curtop += obj.y;
        }
        return curtop;
    },

    /**
     * Set disabled.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    setDisabled: function(disabled) {
        var widget = @JS_NS@.widget.common.getWidget(this.field.id);
        if (widget) {
            return widget.setProps({disabled: disabled});
        }
        var span = this.calendarToggle.parentNode;
        if(disabled) {
            span.style.display = "none";
        } else {
            span.style.display = "block";
        }
        if(disabled) {
            @JS_NS@._base.common._addStyleClass(this.pattern, this.hiddenClass);
        } else {
            @JS_NS@._base.common._stripStyleClass(this.pattern, this.hiddenClass);
        }
        return true;
    },

    /**
     * Set initial focus.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    setInitialFocus: function() {
        var pattern = new String(this.dateFormat);
        var yearIndex = pattern.indexOf("yyyy");
        var monthIndex = pattern.indexOf("MM");

        if(yearIndex < monthIndex) {
            this.yearMenu.focus();
        } else {
            this.monthMenu.focus();
        }
        return true;
    },

    /**
     * Get date format.
     *
     * @return {String} The date format.
     * @deprecated See @JS_NS@.widget.calendar
     */
    formatDate: function(month, day, year) {
        var date = new String(this.dateFormat);
        if(month > 12) {
            month = month % 12;
            if(month == 1) {
                year++;
            }
        }
        date = date.replace("yyyy", new String(year));
        if(month < 10) {
            date = date.replace("MM", "0" + new String(month));
        } else {
            date = date.replace("MM", new String(month));
        }
        if(day < 10) {
            date = date.replace("dd", "0" + new String(day));
        } else {
            date = date.replace("dd", new String(day));
        }
        return date;
    },

    // <RAVE> Function worksaround IE bug where popup calendar appears under
    // other components (eeg 2005-11-11)

    /**
     * div = Main popup div with class="CalPopShdDiv"
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    ieStackingContextFix: function(div) {
        // Test for IE and return if not
        if (!document.all) {
            return false;
        }

        var tag;
        var position;
        if (div.style.display == "block") {
            // This popup should be displayed

            // Get the current zIndex for the div
            var divZIndex = div.currentStyle.zIndex;

            // Propogate the zIndex up the offsetParent tree
            tag = div.offsetParent;
            while (tag != null) {
                position = tag.currentStyle.position;
                if (position == "relative" || position == "absolute") {
                    // Save any zIndex so it can be restored
                    tag.raveOldZIndex = tag.style.zIndex;
                    // Change the zIndex
                    tag.style.zIndex = divZIndex;
                }
                tag = tag.offsetParent;
            }

            // Hide controls unaffected by z-index
            this.ieShowShim(div);
        } else {
            // This popup should be hidden so restore zIndex-s
            tag = div.offsetParent;
            while (tag != null) {
                position = tag.currentStyle.position;
                if (position == "relative" || position == "absolute") {
                    if (tag.raveOldZIndex != null) {
                        tag.style.zIndex = tag.raveOldZIndex;
                    }
                }
                tag = tag.offsetParent;
            }
            this.ieHideShim(div);
        }
        return true;
    },

    /**
     * Gets or creates an iframe shim for popup used to hide windowed
     * components in IE 5.5 and above. Assumes popup has id.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    ieGetShim: function(popup) {
        var shimId = popup.id + "_shim";
        var shim = document.getElementById(shimId);
        if (shim == null) {
            shim = document.createElement(
                '<iframe style="display: none;" src="javascript:false;"' +
                ' frameBorder="0" scrolling="no"></iframe>');
            shim.id = shimId;
            if (popup.offsetParent == null) {
                document.body.appendChild(shim);
            } else {
                popup.offsetParent.appendChild(shim);
            }
        }
        return shim;
    },

    /**
     * Show IE shim.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    ieShowShim: function(popup) {
        var shim = this.ieGetShim(popup);
        shim.style.position = "absolute";
        shim.style.left = popup.style.left;
        shim.style.top = popup.style.top;
        shim.style.width = popup.offsetWidth;
        shim.style.height = popup.offsetHeight;
        shim.style.zIndex = popup.currentStyle.zIndex - 1;
        shim.style.display = "block";
        return true;
    },

    /**
     * Hide IE shim.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    ieHideShim: function(popup) {
        var shim = this.ieGetShim(popup);
        shim.style.display = "none";
        return true;
    },

    /**
     * Set selected value.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    setSelectedValue: function(select, val) {
        for (var i = 0;i < select.length;i++) {
            if (select.options[i].value == val) {
                select.selectedIndex = i;
                return false;
            }
        }
        select.selectedIndex = -1;
        return true;
    },

    /**
     * Set the value of a SELECT, but limit value to min and max.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    setLimitedSelectedValue: function(select, value) {
        var min = select.options[0].value;
        var max = select.options[select.length - 1].value;
        if (value < min) {
            select.selectedIndex = 0;
        } else if ( value > max) {
            select.selectedIndex = select.length - 1;
        } else {
            this.setSelectedValue(select, value);
        }
        return true;
    },

    /**
     * Workaround gecko scrunched table bug and force a redraw.
     *
     * @return {boolean} true if successful; otherwise, false.
     * @deprecated See @JS_NS@.widget.calendar
     */
    redrawPopup: function() {
        // Force a redraw of the popup header controls by changing the selected
        // month which will call the onChange handler to redraw.
        var oldIndex = this.monthMenu.selectedIndex;
        this.monthMenu.selectedIndex = 0;
        this.monthMenu.selectedIndex = oldIndex;

        // Redraw the popup grid with the date numbers
        return this.redrawCalendar(false);
    }
//    </RAVE>
};
