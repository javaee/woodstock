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

webui.@THEME@.dojo.provide("webui.@THEME@.widget.accordionTab");

webui.@THEME@.dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * @name webui.@THEME@.widget.accordionTab
 * @extends webui.@THEME@.widget.widgetBase
 * @class This class contains functions for the accordionTab widget.
 * @constructor This function is used to construct an accordionTab widget.
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.accordionTab", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    isContainer: true,
    selected: false,
    focusState: "title",
    widgetName: "accordionTab" // Required for theme properties.    
});


/**
 * The callback function for key press on the accordion tabs.
 *
 * @param {String} id The id of the accordion.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.createOnKeyDownCallBack = function(id) {
    if (id == null) {
        return;
    }

    var id = this.id;
    var _widget = this.widget;
    return function(event) {
        var elem = document.getElementById(id);
        if (elem == null) {
            return;
        }
        var widget = webui.@THEME@.dijit.byId(id);

        event = _widget.getEvent(event);
        var keyCode = _widget.getKeyCode(event);
        
        // if onkeypress returns false, we do not traverse the menu.
        var keyPressResult = true;
        if (this._onkeypress) {
            keyPressResult = (this._onkeypress) ? this._onkeypress() : true;
        }        
       
        if (keyPressResult != false) {
            widget.traverseMenu(keyCode, event, id);
        }
        return true;
   }
}

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME@.widget.accordionTab.event =
        webui.@THEME@.widget.accordionTab.prototype.event = {
    /**
     * This object contains load event topics.
     * @ignore
     */
    load: {
        /** Load event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_load_begin",

        /** Load event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordionTab_event_load_end"
    },

    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordionTab_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordionTab_event_state_end"
    },

    /**
     * This object contains title event topics.
     * @ignore
     */
    title: {
        /** Action event topic for custom AJAX implementations to listen for. */
        selectedTopic: "webui_@THEME@_widget_accordionTab_event_tab_selected"
    }
}

/**
 * Process load event.
 *
 * @param {String} execute The string containing a comma separated list 
 * of client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.loadContent = function(execute) {
    // Publish event.
    this.publish(webui.@THEME@.widget.accordionTab.event.load.beginTopic, [{
        id: this.id
    }]);
    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.accordionTab.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.selected) { props.selected = this.selected; }
    if (this.title) { props.title = this.title; }
    if (this.tabContent) { props.tabContent = this.tabContent; }
    if (this.visible != null) { props.visible = this.visible; }
    if (this.actions != null) { props.actions = this.actions; }
    if (this.className != null) { props.className = this.className; }
    if (this.style != null) { props.style = this.style; }
    if (this.contentHeight != null) { props.contentHeight = this.contentHeight; }
    if (this.id) { props.id = this.id; }
    if (this.tabContent) { props.tabContent = this.tabContent; }
    if (this.focusId) { props.focusId = this.focusId; }
    if (this.type) { props.type = this.type; }

    return props;
}

/**
 * Get title height.
 *
 * @return {int} The title height.
 */
webui.@THEME@.widget.accordionTab.prototype.getTitleHeight = function () {
    // Warning: This function has been made private.
    return this.dojo._getMarginBox(this.titleContainer).height;
}

/**
 * Handle menu onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.onMenuClickCallback = function(event) {
    this.dojo.stopEvent(event);
    return true;
}

/**
 * Handle title onClick event.
 * <p>
 * This function selects the child tab when the user clicks on its label. The 
 * actual behavior of the accordion depends on multipleSelect being enabled.
 * </p>
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.onTitleClickCallback = function (event) {
    this.publish(webui.@THEME@.widget.accordionTab.event.title.selectedTopic, [{
        id: this.id
    }]);
    if (this.titleContainer.focus) {
        this.titleContainer.focus();
    }
    return true;
}

/**
 * Handle title onMouseOut event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.onTitleMouseOutCallback = function(event) {
    if (this.selected) {
        this.titleContainer.className = this.theme.getClassName("ACCORDION_TABEXPANDED");
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
        if (this.titleContainer.focus) {
            this.titleContainer.focus();
        }
        return true;
    }
    this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
    this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    return true;
}

/**
 * Handle title onMouseOver event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.onTitleMouseOverCallback = function(event) {
    if (this.selected) {
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
    } else {
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    }
    if (this.titleContainer.focus) {
        this.titleContainer.focus();
    }
    return true;
}

/**
 * Handle the case when the contentNode is about to lose focus. The tabIndex for the tab 
 * and the contentNode should be set to -1 and the focusElement should also be nulled.
 * 
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.onContentEndCallback = function(event) {
    this.focusState = "end";
    return true;
}


/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.domNode.id = this.id;
        this.titleNode.id = this.id + "_tabTitle";
        this.turnerContainer.id = this.id + "_tabTitleTurner";
        this.menuContainer.id = this.id + "_tabMenu";
        this.hiddenFieldNode.id = this.id + ":selectedState";
        this.hiddenFieldNode.name = this.hiddenFieldNode.id;
        this.domNode.tabIndex = -1;
        this.contentEnd.id = this.id + "_contentEnd";
    }

    // Set style classes.
    this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
    this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    this.menuContainer.className = this.theme.getClassName("HIDDEN");
    this.titleNode.className = this.theme.getClassName("ACCORDION_TABTITLE");
    this.contentNode.className = this.theme.getClassName("ACCORDION_TABCONTENT");

    // Set public functions.
    // TBD...

    // Set events.
    this.dojo.connect(this.titleContainer, "onclick", this, "onTitleClickCallback");
    this.dojo.connect(this.titleContainer, "onmouseover", this, "onTitleMouseOverCallback");
    this.dojo.connect(this.turnerContainer, "onmouseover", this, "onTitleMouseOverCallback");
    this.dojo.connect(this.turnerContainer, "onclick", this, "onTitleClickCallback");
    this.dojo.connect(this.menuContainer, "onmouseover", this, "onTitleMouseOverCallback");
    this.dojo.connect(this.menuContainer, "onclick", this, "onMenuClickCallback");
    this.dojo.connect(this.titleContainer, "onmouseout", this, "onTitleMouseOutCallback");
    this.dojo.connect(this.turnerContainer, "onmouseout", this, "onTitleMouseOutCallback");
    this.dojo.connect(this.menuContainer, "onmouseout", this, "onTitleMouseOutCallback");
    this.dojo.connect(this.contentEnd, "onblur", this, "onContentEndCallback");
    //Create callback function for onkeydown event.
    this.dojo.connect(this.domNode, "onkeydown", 
        this.createOnKeyDownCallBack(this.id)); 
    
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
 * @config {String} className CSS selector.
 * @config {int} contentHeight CSS selector.
 * @config {String} hiddenField Field set to true if tab is selected.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {Array} tabContent The content area of the tab.
 * @config {String} style Specify style rules inline.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.tabContent) {
        this.tabContent = null;
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
webui.@THEME@.widget.accordionTab.prototype._setProps = function(props) {

    if (props == null) {
        return false;
    }

    if (props.tabIndex == null) {
        props.tabIndex = -1;
        this.tabIndex = -1;
    } else { //new code
        this.domNode.tabIndex = props.tabIndex;
        this.titleContainer.tabIndex = props.tabIndex;
        this.contentNode.tabIndex = props.tabIndex;
        this.tabIndex = props.tabIndex;
        this.contentEnd.tabIndex = this.tabIndex;
    }  // end of new code.

    // Set properties.
    if (props.contentHeight) {
        this.contentNode.style.height = props.contentHeight;
    }

    if (props.title) {
        this.setTitle(props.title);
    }

    if (props.tabContent) {
        this.setTabContent(props.tabContent);
        if (this.selected) {
            this.hiddenFieldNode.value = "true";
            this.titleContainer.className = this.theme.getClassName("ACCORDION_TABEXPANDED");
            this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
            this.contentNode.style.display = "block";
        } else {
            this.hiddenFieldNode.value = "false";
            this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
            this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
            this.contentNode.style.display = "none";
        }
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    if (props.focusId != null) {
        this.focusId = props.focusId;
        if (this.selected) {
            if (this.focusState == "content") {
                var focusElem = document.getElementById(this.focusId);
                if (focusElem.focus) {
                    focusElem.focus();
                }
            }
        } else {
            this.focusState = "title";
        }
    }

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}

/**
 * Set tab selected.
 *
 * @param {boolean} isSelected true if selected.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.setSelected = function (isSelected) {
    if (this.selected) {
        this.selected = false;
    } else {
        this.selected = isSelected;
    }

    if (this.selected) {
        this.hiddenFieldNode.value = "true";
        this.titleContainer.className = this.theme.getClassName("ACCORDION_TABEXPANDED");
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
        this.contentNode.style.display = "block";

        // if the tab does not have content and "loadOnSelect" is set
        // to true go ahead and refresh the widget. 
        if (!this.tabContent) {
            if (this.parent.loadOnSelect) {
                this.loadContent();
            }
        }
    } else {
        this.hiddenFieldNode.value = "false";
        this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
        this.contentNode.style.display = "none";
    }
    return true;
}

/**
 * Set the contents of the accordion tab.
 *
 * @param {Array} content The Contents of the tab body.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.setTabContent = function(content) {
    if (content) {
        for (var i = 0; i < content.length; i++) {
            this.widget.addFragment(this.contentNode, content[i], "last");
        }
    }
    return true;
}

/**
 * Set the title associated with the accordion tab.
 *
 * @param {String} title Title property associated with the tab.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.setTitle = function (title) {
    
    if (title) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        var titleHref = webui.@THEME@.widget.common.getWidgetProps("hyperlink", {
                id: this.id + "_titleLink",
                title: title,
                onClick: "return false;",
                className: "",
                contents: [title]
                
        });
        this.widget.addFragment(this.titleNode, titleHref);
    }
    return true;
}

/**
 * This function takes care of traversing through the accordionTab depending
 * on which key is pressed. If tab is open and current focus is on the title, 
 * the focus should move to the first element in the the open tab. Else it 
 * should simply move out accordion.
 * @param (String) keyCode The value of the key which was pressed
 * @param (Event) event The key press event.
 * @param (String) nodeId The id of the accordionTab. 
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.accordionTab.prototype.traverseMenu = function(keyCode, event, nodeId) {
    // The focus can either be on the title or the content.
    // If tab is open and current focus is on the title, the focus should move
    // to the first element in the the open tab. Else it should simply move out 
    // accordion.

    // do nothing if only shift is pressed.
    if (event.shiftKey && keyCode != 9) {  
        return true;
    }

    // handle the tab case.
    if (keyCode == 9) {         

        // shift+tab is handled by the parent accordion, simply remove focus
        // from this tab and set tabIndex to -1.
        if (event.shiftKey) {
            
            this.focusState == "title";
            if (this.contentNode.blur) {
                this.contentNode.blur();
            }
            if (this.titleContainer.blur) {
                this.titleContainer.blur();
            }
            // this.focusId = null;
            // this.domNode.tabIndex = -1;  //new
            return true;
        }

        // If tab is open and focus is on the title section
        // shift focus to the content section, else move out of the
        // tab.
        if (this.selected) {
            if (this.focusState == "title") {
                this.contentNode.tabIndex = this.tabIndex;
                this.contentEnd.tabIndex = this.tabIndex;
                this.contentNode.focus();
                if (this.focusId == null) {
                    var fChild = this.contentNode.firstChild;
                    if (fChild) {
                        if (fChild.focus) {
                            fChild.focus();
                        }
                        this.focusId = fChild;
                    }
                    return true;
                } else {
                    var focusElem = document.getElementById(this.focusId);
                    if (focusElem) {
                        if (focusElem.focus) {
                            focusElem.focus();
                        }
                    }
                }
                this.focusState = "content";
            } else if (this.focusState == "content") {
                return true;
            } else {
                this.contentNode.focus();
                if (this.focusId == null) {
                    this.contentNode.tabIndex = this.tabIndex;
                    return true;
                } else {
                    var focusElem = document.getElementById(this.focusId);
                    if (focusElem) {
                        if (focusElem.focus) {
                            focusElem.focus();
                        }
                    }
                }
                this.focusState = "content";
            }
        } else {
            // reset the tabIndex as control is moving out of the tab.
            // this.domNode.tabIndex = -1; //new
            return true;
        }
    } else if (keyCode == 38 && event.ctrlKey) {  // handle ctrl + up

        // Move focus to the header of the accordion tab if its contents is
        // currently in focus. Else, do nothing. Also, stop event propagation.

        if (this.focusState == "content") {
            this.focusState == "title";
            if (this.contentNode.blur) {
                this.contentNode.blur();
            }
            if (this.titleContainer.focus) {
                this.titleContainer.focus();
            }
            if (webui.@THEME@.browser.isIe5up()) {
                window. event.cancelBubble = true;
                window.event.returnValue = false;
            } else {
                event.stopPropagation();
                event.preventDefault();
            }
        }
    }
    return true;
}