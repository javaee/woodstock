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

@JS_NS@._dojo.provide("@JS_NS@._html.commonTasksSection");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@._base.common");
@JS_NS@._dojo.require("@JS_NS@._base.proto");
@JS_NS@._dojo.require("@JS_NS@.theme.common");

/** 
 * @class This class contains functions for commonTasksSection components.
 * @static
 * @private
 */
@JS_NS@._html.commonTasksSection = {
    /**
     * This function is used to initialize HTML element properties with Object 
     * literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} id The HTML element id.
     * @config {String} pic1URL Selected image.
     * @config {String} pic2URL Hover image.
     * @config {String} pic3URL Normal image.
     * @return {boolean} true if successful; otherwise, false.
     * @private
     */
    _init: function(props) {
        var message = "Cannot initialize commonTasksSection.";
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

        // Set functions.
	domNode.captureCloseKey = @JS_NS@._html.commonTasksSection.captureCloseKey;
	domNode.captureBottomInfoKey = @JS_NS@._html.commonTasksSection.captureBottomInfoKey;
        domNode.hideAll = @JS_NS@._html.commonTasksSection.hideAll;
        domNode.addCommonTask = @JS_NS@._html.commonTasksSection.addCommonTask;
        domNode.addInfoPanel = @JS_NS@._html.commonTasksSection.addInfoPanel;
        domNode.windowResize = @JS_NS@._html.commonTasksSection.windowResize;
        domNode.onclick = domNode.hideAll;

        // Set task element array.
        domNode.taskElement = new Array();
        domNode.count = 0;

        // Hide panels on resize.
        @JS_NS@._dojo.connect(window, 'onresize', domNode, domNode.windowResize);

        return true;
    },

    /**
     * Hide all task sections.
     *
     * @param {Event} event The JavaScript event.
     * @return {boolean} true if successful; otherwise, false.
     */
    hideAll: function(event) {
        for (var i = 0; i < this.count; i++) {
            task = this.taskElement[i];
            if (task.infoPanel) {
               @JS_NS@._base.common._setVisibleElement(task.infoPanel.info, false);
               task.infoPanel.image.src = this.pic3URL;
            }
        }
        if (@JS_NS@._base.browser._isIe5up()) {
            window.event.cancelBubble = true;
        } else {
            event.stopPropagation();
        }
        return true;
    },

    /**
     * This function handles window resize events.
     *
     * @param {Event} event The JavaScript event.
     * @return {boolean} true if successful; otherwise, false.
     */
    windowResize: function(event) {
        for (var i = 0; i < this.count; i++) {
            task = this.taskElement[i];
            if (task.infoPanel) {
               @JS_NS@._base.common._setVisibleElement(task.infoPanel.info, false);
               task.infoPanel.image.src = this.pic3URL;
            }
        }
        return true;
    },

    /**
     * This function is used to set common task properties using Object literals.
     *
     * @param {Object} props Key-Value pairs of properties.
     * @config {String} commonTaskId
     * @config {String} closeId
     * @config {String} spacerId
     * @config {String} infoIconId
     * @config {String} infoPanelVar
     * @config {String} imageLinkId
     * @return {boolean} true if successful; otherwise, false.
     */
    addCommonTask: function(props) {
        if (props == null) {
            return false;
        }

        // Get HTML elements.
        var info = document.getElementById(props.commonTaskId + props.infoPanelVar);  //id of the info panel box.
        var image = document.getElementById(props.infoIconId); // id of the "i" image .
        var imageLink = document.getElementById(props.imageLinkId);
        var close = document.getElementById(props.closeId); // id of the close button.	
        var parent = document.getElementById(this.id);
        var task = document.getElementById(props.commonTaskId);
        var bottomInfoLink = (props.bottomInfoLink)
            ? document.getElementById(props.bottomInfoLink) 
            : null; // The bottom info panel id.
        var spacer = props.commonTaskId + ":" + props.spacerId; // id of the spacer image.

        // HTML elements may not have been created, yet.
        if (parent == null
                || (props.bottomInfoLink && bottomInfoLink == null)
                || (props.closeId && close == null)
                || (props.commonTaskId && props.infoPanelVar && info == null)
                || (props.commonTaskId && task == null)
                || (props.infoIconId && image == null)
                || (props.imageLinkId && imageLink == null)) {
            return setTimeout(function() {
                parent.addCommonTask(props);
            }, 10);
        }

        // Set info panel.
        var taskElement = document.getElementById(props.commonTaskId);
        taskElement.infoPanel = new this.addInfoPanel(info, image, imageLink,
            close, parent, task, bottomInfoLink, spacer);

        // Add task element to domNode.
        this.taskElement[this.count] = taskElement;
        this.count++;
        return true;
    },
    
    /**
     * Add info panel to common task section.
     *
     * @param {Node} info The info panel box.
     * @param {Node} image The info panel icon
     * @param {Node} imageLink
     * @param {Node} close The close button.
     * @param {Node} parent
     * @param {Node} task
     * @param {Node} bottomInfoLink The bottom info panel link.
     * @param {String} spacer ID of the spacer image.
     * @return {boolean} true if successful; otherwise, false.
     */
    addInfoPanel: function(info, image, imageLink, close, parent, task, 
            bottomInfoLink, spacer) {
        // Set HTML elements.
        this.info = info;
        this.image = image;
        this.imageLink = imageLink;
        this.close = close;
        this.parent = parent;
        this.task = task;
        this.bottomInfoLink = bottomInfoLink;
        this.spacer = spacer;
        this._theme = @JS_NS@.theme.common;
        
        var that = this;

        // Handle the keypress event for the "more" link if one is present.
        // Tabbing out of the info panel should close the info panel whereas
        // pressing escape key should close the info panel tooo
        if (this.bottomInfoLink) {
            
            this.bottomInfoLink.setProps({
                
                // Only for IE.                
                onKeyDown:function(event) {
                    if (@JS_NS@._base.browser._isIe5up()) {

                        // For IE, while pressing the shift key along with the tab key
                        // the onkeydown seems to be called twice. To prevent this,
                        // check whether the shift key is the one thats being pressed
                        // before actually calling the event handling function.
                        if (!(window.event.keyCode == 16)) {
                            that.captureBottomInfoKey(window.event);
                        }
                    }
                    return false;
                },
                onKeyPress:function(event) {	    
                    var evt = (event) ? event : ((window.event) ? window.event : null);  
                    if (!@JS_NS@._base.browser._isIe5up()) {
                        that.captureBottomInfoKey(event);
                    }
                    return false;                                 
                }        
            });
        }
        
        // Function that gets invoked when keypress event happens on the bottom
        // portion of the info panel.
        this.captureBottomInfoKey = function(event) {
            if ((event.keyCode == 9 && !event.shiftKey)|| event.keyCode == 27) {
                // need to remove the focus off the link. Otherwise there seems
                // to be problems setting focus on another element in IE.
                that.bottomInfoLink.blur();

                @JS_NS@._base.common._setVisibleElement(that.info, false);
                that.image.src = that.parent.pic3URL;	
                that.imageLink.focus();
            }

            if (event.shiftKey && event.keyCode == 9) {
                that.close.focus();

                // If you dont do this, the info panel closes on IE
                // and the focus is set on the "i" icon.
                @JS_NS@._base.common._setVisibleElement(that.info, true);
            }
            return true;
        };

        // Function that is called when the key press event happens on the
        // close image of the info panel.
        this.captureCloseKey = function(event) {
            // We want to process only key press events which have the tab key pressed.
            if (event.keyCode == 9) {
                // If this is not done IE doesnt set focus on the next available
                // element properly if the info panel closes.
                that.close.blur();     

                // If the "more" link is present, shift focus to that
                // else close the info panel on blur.
                if (that.bottomInfoLink && event.shiftKey == false) {
                    that.bottomInfoLink.focus(); 

                    // If this is not done, the info panel closes
                    // after you tab to the element on IE
                    @JS_NS@._base.common._setVisibleElement(that.info, true);
                } else {
                    that.image.src = that.parent.pic3URL;	            
                    @JS_NS@._base.common._setVisibleElement(that.info, false);    
                    that.imageLink.focus();
                }                                      
            }
            return true;
        };
                
        // Events which handle the closing of the div.        
        this.close.setProps({
            onClick:function(event) {
                @JS_NS@._base.common._setVisibleElement(that.info, false);
                that.image.src = that.parent.pic3URL;	
                if (@JS_NS@._base.browser._isIe5up()) {
                    window. event.cancelBubble = true;
                } else {
                    event.stopPropagation();
                }
                that.task.focus();
                return false;
            },        
            
            // Need to do this only on IE. "Tab" key doesnt get registered
            // for keypress on IE.                        
            onKeyDown:function(event) {
                if (@JS_NS@._base.browser._isIe5up()) {

                    // this seems to be called once for the shift key and
                    // once for the tab key. Prevent calling the capture
                    // function when the shift key is pressed
                    if (!(window.event.keyCode == 16)) {
                        that.captureCloseKey(window.event);
                    }

                    // If escape key is pressed, the info panel must close.
                    if (window.event.keyCode == 27 || window.event.keyCode == 13) {
                        @JS_NS@._base.common._setVisibleElement(that.info, false);
                        that.image.src = that.parent.pic3URL;
                        that.imageLink.focus();
                    }                
                    return false;
                }
                return true;
            },
            
            // Handle the keypress event on the close imageHyperlink.
            // If tab key is pressed, the focus must either pass to
            // the "more" link if it is present or the infoPanel should close. 
            onKeyPress:function(event) {              
                var evt = (event) ? event : ((window.event) ? window.event : null);         
                if (!@JS_NS@._base.browser._isIe5up()) {
                    that.captureCloseKey(evt);
                }
                // If escape key is pressed, the info panel must close.
                if (evt.keyCode == 27 || evt.keyCode == 13) {
                    @JS_NS@._base.common._setVisibleElement(that.info, false);
                    that.image.src = that.parent.pic3URL;
                    that.imageLink.focus();
                }
                return false;
            }
        });
        
        this.info.onclick = function(event) {
            @JS_NS@._base.common._setVisibleElement(that.info, true);
            if (@JS_NS@._base.browser._isIe5up()) {
                window. event.cancelBubble = true;
            } else {
                event.stopPropagation();
            }
            return true;
        };
        
        // Events which handle the image changes for the "i" image.
        this.imageLink.setProps({
            onMouseOver:function() {
                if (!@JS_NS@._base.common._isVisibleElement(that.info)) {
                    that.image.src = that.parent.pic2URL;
                } else {
                    that.image.src = that.parent.pic1URL;
                }
                return true;
            },        
            onFocus:function() {
                if (!@JS_NS@._base.common._isVisibleElement(that.info)) {
                    that.image.src = that.parent.pic2URL;
                } else {
                    that.image.src = that.parent.pic1URL;
                }
                return true;
            },        
            onBlur:function() {
                  if (!@JS_NS@._base.common._isVisibleElement(that.info)) {
                    that.image.src = that.parent.pic3URL;
                } else {
                    that.image.src = that.parent.pic1URL;
                }
                return true;
            },
            onMouseOut:function() {
                if (!@JS_NS@._base.common._isVisibleElement(that.info)) {
                    that.image.src = that.parent.pic3URL;
                } else {
                    that.image.src = that.parent.pic1URL;
                }
                return true;
            },
            onKeyPress:function(event) {
                var evt = (event) ? event : ((window.event) ? window.event : null);            
                if (evt.keyCode == 13) {
                    that.showInfoPanel();
                    return false;                
                }
                if (@JS_NS@._base.browser._isIe5up()) {
                    window.event.cancelBubble = true;
                } else {
                    event.stopPropagation();
                }
                return true;
            }            
        });
        
       // Toggle functionality incorporated
        this.image.setProps({onClick:function(event){
            that.showInfoPanel();
            if (@JS_NS@._base.browser._isIe5up()) {
                window.event.cancelBubble = true;
            } else {
                event.stopPropagation();
            }
            return true;
        }});
        
        this.showInfoPanel = function() {
            var cts = this.parent;
            for (var i = 0; i < cts.count; i++) {
                task = cts.taskElement[i];
                if (task.infoPanel != null
                        && task.infoPanel.image.id != this.image.id) {
                    @JS_NS@._base.common._setVisibleElement(task.infoPanel.info, false);
                    task.infoPanel.image.src = cts.pic3URL;
                }
            }
 
            if (!@JS_NS@._base.common._isVisibleElement(this.info)) {
                @JS_NS@._base.common._setVisibleElement(this.info, true);
                this.getElementPosition2(this.image.id);
                this.getElementPosition(this.task.id);        
                this.info.style.top = (this.ttop + parseInt(this._theme.getMessage("commonTasks.infoPanelOffsetTop"))) +'px';
                this.info.style.left =  (this.tleft - 1) + 'px';
                this.info.style.width = (this.ileft - this.tleft) + 29 + 'px';
                this.close.focus();
                this.image.src = cts.pic1URL;
            } else {
                this.image.src = cts.pic3URL;
                @JS_NS@._base.common._setVisibleElement(this.info, false);
            }
            return true;
        };

        // Javascript for setting the common task page's look and feel.

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
                offsetLeft += document.body.leftMargin;
                offsetTop += document.body.topMargin;
            }
            this.tleft = offsetLeft;
            this.ttop = offsetTop;
            return true;
        };

        this.getElementPosition2 = function(elemID) {
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
                offsetLeft += document.body.leftMargin;
                offsetTop += document.body.topMargin;
            }
            this.ileft = offsetLeft;
            return true;
        };
    }
};
