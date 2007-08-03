//
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

dojo.provide("webui.@THEME@.commonTasksSection");

dojo.require("webui.@THEME@.common");

/** 
 * Define webui.@THEME@.commonTasksSection name space. 
 */
webui.@THEME@.commonTasksSection = {
    /**
     * This function is used to initialize HTML element properties with the
     * following Object literals.
     *
     * <ul>
     *  <li>id: The HTML element ID for the component.</li>
     *  <li>pic1URL: Selected image.</li>
     *  <li>pic2URL: Hover image.</li>
     *  <li>pic3URL: Normal image.</li>
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

        // Set functions.
	domNode.captureCloseKey = webui.@THEME@.commonTasksSection.captureCloseKey;
	domNode.captureBottomInfoKey = webui.@THEME@.commonTasksSection.captureBottomInfoKey;
        domNode.hideAll = webui.@THEME@.commonTasksSection.hideAll;
        domNode.addCommonTask = webui.@THEME@.commonTasksSection.addCommonTask;
        domNode.addInfoPanel = webui.@THEME@.commonTasksSection.addInfoPanel;
        domNode.windowResize = webui.@THEME@.commonTasksSection.windowResize;
        domNode.onclick = domNode.hideAll;
        // Set task element array.
        domNode.taskElement = new Array();
        domNode.count = 0;

        // Hide panels on resize.
        dojo.event.connect(window, 'onresize', domNode, domNode.windowResize);
    },

    // Hide all task sections.
    hideAll: function(event) {
        for (var i = 0; i < this.count; i++) {
            task = this.taskElement[i];
            if (task.infoPanel) {
               webui.@THEME@.common.setVisibleElement(task.infoPanel.info, false);
               task.infoPanel.image.src = this.pic3URL;
            }
        }
        if (webui.@THEME@.common.browser.is_ie5up) {
            window. event.cancelBubble = true;
        } else {
            event.stopPropagation();
        }
    },

    windowResize: function(event) {
        for (var i = 0; i < this.count; i++) {
            task = this.taskElement[i];
            if (task.infoPanel) {
               webui.@THEME@.common.setVisibleElement(task.infoPanel.info, false);
               task.infoPanel.image.src = this.pic3URL;
            }
        }
    },

    /**
     * This function is used to add a common task with the
     * following Object literals.
     *
     * <ul>
     *  <li>commonTaskId:</li>
     *  <li>closeId:</li>
     *  <li>spacerId:</li>
     *  <li>infoIconId:</li>
     *  <li>infoPanelVar:</li>
     *  <li>imageLinkId:</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */
    addCommonTask: function(props) {
        // Set info panel.
        var taskElement = document.getElementById(props.commonTaskId);
        taskElement.infoPanel = new this.addInfoPanel(this.id,
            props.commonTaskId, props.closeId, props.spacerId, props.infoIconId,
            props.infoPanelVar, props.imageLinkId, props.bottomInfoLink);

        // Add task element to domNode.
        this.taskElement[this.count] = taskElement;
        this.count++;
    },
    
    /**
     * Add info panel to common task section.
     */
    addInfoPanel: function(sectionId, taskId, closeId, spacerVar,
            infoIconId, infoPanelVar, imageLinkId, bottomInfoLink) {
        this.info = document.getElementById(taskId + infoPanelVar);  //id of the info panel box.
        this.image = document.getElementById(infoIconId); // id of the "i" image .
        this.imageLink = document.getElementById(imageLinkId);
        this.close = document.getElementById(closeId); // id of the close button.	
        this.spacer = taskId + ":" + spacerVar; // Just the id of the spacer image.
        this.parent = document.getElementById(sectionId);
        this.task = document.getElementById(taskId);
        if (bottomInfoLink) {
            this.bottomInfoLink = document.getElementById(bottomInfoLink);// the bottom info panel id.
        }
        var that = this;

        /**
         * Handle the keypress event for the "more" link if one is present.
         * Tabbing out of the info panel should close the info panel whereas
         * pressing escape key should close the info panel tooo
         */
        if (this.bottomInfoLink) {
            this.bottomInfoLink.onkeypress = function(event) {	    
                var evt = (event) ? event : ((window.event) ? window.event : null);  
		if (!webui.@THEME@.common.browser.is_ie5up) {
		    that.captureBottomInfoKey(event);
		}
                return false;                                 
            }

            // Only for IE
            this.bottomInfoLink.onkeydown = function(event) {
                if (webui.@THEME@.common.browser.is_ie5up) {

                    // For IE, while pressing the shift key along with the tab key
                    // the onkeydown seems to be called twice. To prevent this,
                    // check whether the shift key is the one thats being pressed
                    // before actually calling the event handling function.
                    if (!(window.event.keyCode == 16)) {
                        that.captureBottomInfoKey(window.event);
                    }
                }
                return false;
            }
        }
            
        /**
         * Handle the keypress event on the close imageHyperlink.
         * If tab key is pressed, the focus must either pass to
         * the "more" link if it is present or the infoPanel should close.
         */
        this.close.onkeypress = function(event) {           
            var evt = (event) ? event : ((window.event) ? window.event : null);         
            if (!webui.@THEME@.common.browser.is_ie5up) {
                that.captureCloseKey(evt);
            }
            // If escape key is pressed, the info panel must close.
            if (evt.keyCode == 27 || evt.keyCode == 13) {
                webui.@THEME@.common.setVisibleElement(that.info, false);
                that.image.src = that.parent.pic3URL;
            }
            return false;
        }

        /**
         * Function that gets invoked when keypress event happens on the bottom
         * portion of the info panel.
         */
        this.captureBottomInfoKey = function(evt) {
            if ((evt.keyCode == 9 && !evt.shiftKey)|| evt.keyCode == 27) {
                // need to remove the focus off the link. Otherwise there seems
                // to be problems setting focus on another element in IE.
                that.bottomInfoLink.blur();

                webui.@THEME@.common.setVisibleElement(that.info, false);
                that.image.src = that.parent.pic3URL;	
            }

            if (evt.shiftKey && evt.keyCode == 9) {
                that.close.focus();

                // If you dont do this, the info panel closes on IE
                // and the focus is set on the "i" icon.
                webui.@THEME@.common.setVisibleElement(that.info, true);
            }
        }

        /**
         * Function that is called when the key press event happens on the
         * close image of the info panel.
         */
        this.captureCloseKey = function(evt) {

            // We want to process only key press events which have the tab key pressed.
            if (evt.keyCode == 9) {

                // If this is not done IE doesnt set focus on the next available
                // element properly if the info panel closes.
                that.close.blur();     

                // If the "more" link is present, shift focus to that
                // else close the info panel on blur.
                if (that.bottomInfoLink && evt.shiftKey == false) {
                    that.bottomInfoLink.focus(); 

                    // If this is not done, the info panel closes
                    // after you tab to the element on IE
                    webui.@THEME@.common.setVisibleElement(that.info, true);
                } else {
                    that.image.src = that.parent.pic3URL;	            
                    webui.@THEME@.common.setVisibleElement(that.info, false);    
                    if (evt.shiftKey == true) {
                        that.imageLink.focus();
                    }         
                }                                      
            }
        }

        // Need to do this only on IE. "Tab" key doesnt get registered
        // for keypress on IE.
        this.close.onkeydown = function(event) {
           if (webui.@THEME@.common.browser.is_ie5up) {

           // this seems to be called once for the shift key and
           // once for the tab key. Prevent calling the capture
           // function when the shift key is pressed
           if (!(window.event.keyCode == 16)) {
              that.captureCloseKey(window.event);
           }
           return false;
           }
        }
                
        /**
         *Events which handle the closing of the div.
         */
        this.close.onclick = function(event) {     
           webui.@THEME@.common.setVisibleElement(that.info, false);
            that.image.src = that.parent.pic3URL;	
            if (webui.@THEME@.common.browser.is_ie5up) {
                window. event.cancelBubble = true;
            } else {
                event.stopPropagation();
            }
            that.task.focus();
        }

        this.info.onclick = function(event) {
            webui.@THEME@.common.setVisibleElement(that.info, true);
             if (webui.@THEME@.common.browser.is_ie5up) {
                 window. event.cancelBubble = true;
            } else {
                    event.stopPropagation();
            }
        }
        
        /**
         * Events which handle the image changes for the "i" image.
         */
        this.imageLink.onmouseover = function() {
              if (!webui.@THEME@.common.isVisibleElement(that.info)) {
                that.image.src = that.parent.pic2URL;
            } else {
                that.image.src = that.parent.pic1URL;
            }
        }

        this.imageLink.onfocus = function() {
              if (!webui.@THEME@.common.isVisibleElement(that.info)) {
                that.image.src = that.parent.pic2URL;
            } else {
                that.image.src = that.parent.pic1URL;
            }
        }

        this.imageLink.onblur = function() {
              if (!webui.@THEME@.common.isVisibleElement(that.info)) {
                that.image.src = that.parent.pic3URL;
            } else {
                that.image.src = that.parent.pic1URL;
            }
        }

        this.imageLink.onmouseout = function() {
              if (!webui.@THEME@.common.isVisibleElement(that.info)) {
                that.image.src = that.parent.pic3URL;
            } else {
                that.image.src = that.parent.pic1URL;
            }
        }

        /**
         * Toggle functionality incorporated
         */
        this.image.onclick = function(event) {
            that.showInfoPanel();
            if (webui.@THEME@.common.browser.is_ie5up) {
                window. event.cancelBubble = true;
            } else {
                event.stopPropagation();
            }
        }
        
        this.imageLink.onkeypress = function(event) {
            var evt = (event) ? event : ((window.event) ? window.event : null);            
            if(evt.keyCode == 13) {
                that.showInfoPanel();
                return false;                
            }
                if (webui.@THEME@.common.browser.is_ie5up) {
                     window.event.cancelBubble = true;
                } else {
                     event.stopPropagation();
                }
        }
        
        this.showInfoPanel = function() {
            var cts = this.parent;
            for (var i = 0; i < cts.count; i++) {
                task = cts.taskElement[i];
                if (task.infoPanel != null
                        && task.infoPanel.image.id != this.image.id) {
                    webui.@THEME@.common.setVisibleElement(task.infoPanel.info, false);
                    task.infoPanel.image.src = cts.pic3URL;
                }
            }
 
              if (!webui.@THEME@.common.isVisibleElement(this.info)) {
                webui.@THEME@.common.setVisibleElement(this.info, true);
                this.getElementPosition2(this.image.id);
                this.getElementPosition(this.spacer);        
                    this.info.style.top = (this.ttop + 12) +'px';
                    this.info.style.left =  (this.tleft - 1) + 'px'
                this.info.style.width = (this.ileft - this.tleft) + 29+'px';
                this.close.focus();
                this.image.src = cts.pic1URL;
            } else {
                this.image.src = cts.pic3URL;
                webui.@THEME@.common.setVisibleElement(this.info, false);
            }
        }  

        /*Javascript for setting the common task page's look and feel.*/

        // The prized coordinate locating function - Thank you Danny Goodman...
        this.getElementPosition = function(elemID) {
            var offsetTrail = document.getElementById(elemID);
            var offsetLeft = 0;
            var offsetTop = 0;

            while (offsetTrail) {
                offsetLeft += offsetTrail.offsetLeft;
                offsetTop += offsetTrail.offsetTop;
                offsetTrail = offsetTrail.offsetParent;
            }
            if (navigator.userAgent.indexOf("Mac") != -1 
                    && typeof document.body.leftMargin != "undefined") {
                alert("Undefined");
                offsetLeft += document.body.leftMargin;
                offsetTop += document.body.topMargin;
            }
            this.tleft=offsetLeft;
            this.ttop=offsetTop;
        }

        this.getElementPosition2 = function(elemID) {
            var offsetTrail = document.getElementById(elemID);
            var offsetLeft = 0;
            var offsetTop = 0;

            while (offsetTrail) {
                offsetLeft += offsetTrail.offsetLeft;
                offsetTop += offsetTrail.offsetTop;
                offsetTrail = offsetTrail.offsetParent;
            }
            if (navigator.userAgent.indexOf("Mac") != -1 && 
                typeof document.body.leftMargin != "undefined") {
                offsetLeft += document.body.leftMargin;
                offsetTop += document.body.topMargin;
            }
            this.ileft=offsetLeft;
        }
    }
}

