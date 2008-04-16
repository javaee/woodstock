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

@JS_NS@._dojo.provide("@JS_NS@.widget.accordionTab");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct an accordionTab widget.
 *
 * @name @JS_NS@.widget.accordionTab
 * @extends @JS_NS@.widget._base.widgetBase
 * @class This class contains functions for the accordionTab widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {int} contentHeight CSS selector.
 * @config {String} hiddenField Field set to true if tab is selected.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {Array} tabContent The content area of the tab.
 * @config {String} style Specify style rules inline.
 * @config {String} title Provides a title for element.
 * @config {boolean} visible Hide or show element.
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.accordionTab",
        @JS_NS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.isContainer = true;
        this.selected = false;
        this.focusState = "title";
    },
    _widgetType: "accordionTab" // Required for theme properties.    
});

/**
 * The callback function for key press on the accordion tabs.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._createOnKeyDownCallBack = function() {
    var _id = this.id;
    return function(event) {
        var elem = document.getElementById(_id);
        if (elem == null) {
            return false;
        }
        var common = @JS_NS@.widget.common;
        var widget = common.getWidget(_id);

        event = common._getEvent(event);
        var keyCode = common._getKeyCode(event);

        // if onkeypress returns false, we do not traverse the menu.
        var keyPressResult = true;
        if (this._onkeypress) {
            keyPressResult = (this._onkeypress) ? this._onkeypress() : true;
        }        
       
        if (keyPressResult != false) {
            widget._traverseMenu(keyCode, event, _id);
        }
        return true;
   };
};

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.accordionTab.event =
        @JS_NS@.widget.accordionTab.prototype.event = {
    /**
     * This object contains load event topics.
     * @ignore
     */
    load: {
        /** Load event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_accordionTab_event_load_begin",

        /** Load event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_accordionTab_event_load_end"
    },

    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_accordionTab_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_accordionTab_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_accordionTab_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_accordionTab_event_state_end"
    },

    /**
     * This object contains title event topics.
     * @ignore
     */
    title: {
        /** Action event topic for custom AJAX implementations to listen for. */
        selectedTopic: "@JS_NS@_widget_accordionTab_event_tab_selected"
    }
};

/**
 * Process load event.
 *
 * @param {String} execute The string containing a comma separated list 
 * of client ids against which the execute portion of the request 
 * processing lifecycle must be run.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._loadContent = function(execute) {
    // Publish event.
    this._publish(@JS_NS@.widget.accordionTab.event.load.beginTopic, [{
        id: this.id
    }]);
    return true;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.accordionTab.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

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
};

/**
 * Handle menu onClick event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._onMenuClickCallback = function(event) {
    this._dojo.stopEvent(event);
    return true;
};

/**
 * Handle title onClick event.
 * <p>
 * This function selects the child tab when the user clicks on its label. The 
 * actual behavior of the accordion depends on multipleSelect being enabled.
 * </p>
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._onTitleClickCallback = function (event) {
    this._publish(@JS_NS@.widget.accordionTab.event.title.selectedTopic, [{
        id: this.id
    }]);
    if (this._titleContainer.focus) {
        this._titleContainer.focus();
    }
    return true;
};

/**
 * Handle title onMouseOut event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._onTitleMouseOutCallback = function(event) {
    if (this.selected) {
        this._titleContainer.className = this._theme.getClassName("ACCORDION_TABEXPANDED");
        this._turnerContainer.className = this._theme.getClassName("ACCORDION_DOWNTURNER");
        if (this._titleContainer.focus) {
            this._titleContainer.focus();
        }
        return true;
    }
    this._titleContainer.className = this._theme.getClassName("ACCORDION_TABCOLLAPSED");
    this._turnerContainer.className = this._theme.getClassName("ACCORDION_RIGHTTURNER");
    return true;
};

/**
 * Handle title onMouseOver event.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._onTitleMouseOverCallback = function(event) {
    if (this.selected) {
        this._turnerContainer.className = this._theme.getClassName("ACCORDION_DOWNTURNER");
    } else {
        this._turnerContainer.className = this._theme.getClassName("ACCORDION_RIGHTTURNER");
    }
    if (this._titleContainer.focus) {
        this._titleContainer.focus();
    }
    return true;
};

/**
 * Handle the case when the _contentNode is about to lose focus. The tabIndex for the tab 
 * and the _contentNode should be set to -1 and the focusElement should also be nulled.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._onContentEndCallback = function(event) {
    this.focusState = "end";
    return true;
};

/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._domNode.id = this.id;
        this._titleNode.id = this.id + "_tabTitle";
        this._turnerContainer.id = this.id + "_tabTitleTurner";
        this._menuContainer.id = this.id + "_tabMenu";
        this._hiddenFieldNode.id = this.id + ":selectedState";
        this._hiddenFieldNode.name = this._hiddenFieldNode.id;
        this._domNode.tabIndex = -1;
        this._contentEnd.id = this.id + "_contentEnd";
    }

    // Set style classes.
    this._titleContainer.className = this._theme.getClassName("ACCORDION_TABCOLLAPSED");
    this._turnerContainer.className = this._theme.getClassName("ACCORDION_RIGHTTURNER");
    this._menuContainer.className = this._theme.getClassName("HIDDEN");
    this._titleNode.className = this._theme.getClassName("ACCORDION_TABTITLE");
    this._contentNode.className = this._theme.getClassName("ACCORDION_TABCONTENT");

    // Set public functions.
    // TBD...

    // Set events.
    this._dojo.connect(this._titleContainer, "onclick", this, "_onTitleClickCallback");
    this._dojo.connect(this._titleContainer, "onmouseover", this, "_onTitleMouseOverCallback");
    this._dojo.connect(this._turnerContainer, "onmouseover", this, "_onTitleMouseOverCallback");
    this._dojo.connect(this._turnerContainer, "onclick", this, "_onTitleClickCallback");
    this._dojo.connect(this._menuContainer, "onmouseover", this, "_onTitleMouseOverCallback");
    this._dojo.connect(this._menuContainer, "onclick", this, "_onMenuClickCallback");
    this._dojo.connect(this._titleContainer, "onmouseout", this, "_onTitleMouseOutCallback");
    this._dojo.connect(this._turnerContainer, "onmouseout", this, "_onTitleMouseOutCallback");
    this._dojo.connect(this._menuContainer, "onmouseout", this, "_onTitleMouseOutCallback");
    this._dojo.connect(this._contentEnd, "onblur", this, "_onContentEndCallback");
    //Create callback function for onkeydown event.
    this._dojo.connect(this._domNode, "onkeydown", this._createOnKeyDownCallBack()); 
    
    return this._inherited("_postCreate", arguments);
};

/**
 * This function is used to set widget properties using Object literals. Please
 * see the constructor detail for a list of supported properties.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.accordionTab.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.tabContent) {
        this.tabContent = null;
    }

    // Extend widget object for later updates.
    return this._inherited("setProps", arguments);
};

/**
 * This function is used to set widget properties. Please see the constructor 
 * detail for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    if (props.tabIndex == null) {
        props.tabIndex = -1;
        this.tabIndex = -1;
    } else { //new code
        this._domNode.tabIndex = props.tabIndex;
        this._titleContainer.tabIndex = props.tabIndex;
        this._contentNode.tabIndex = props.tabIndex;
        this._contentEnd.tabIndex = this.tabIndex;
        this.tabIndex = props.tabIndex;
    }  // end of new code.

    // Set properties.
    if (props.contentHeight) {
        this._contentNode.style.height = props.contentHeight;
    }

    if (props.title) {
        this._setTitle(props.title);
    }

    if (props.tabContent) {
        this._setTabContent(props.tabContent);
        if (this.selected) {
            this._hiddenFieldNode.value = "true";
            this._titleContainer.className = this._theme.getClassName("ACCORDION_TABEXPANDED");
            this._turnerContainer.className = this._theme.getClassName("ACCORDION_DOWNTURNER");
            this._contentNode.style.display = "block";
        } else {
            this._hiddenFieldNode.value = "false";
            this._titleContainer.className = this._theme.getClassName("ACCORDION_TABCOLLAPSED");
            this._turnerContainer.className = this._theme.getClassName("ACCORDION_RIGHTTURNER");
            this._contentNode.style.display = "none";
        }
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);
    this._setEventProps(this._domNode, props);

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
    return this._inherited("_setProps", arguments);
};

/**
 * Set tab selected.
 *
 * @param {boolean} isSelected true if selected.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._setSelected = function (isSelected) {
    if (this.selected) {
        this.selected = false;
    } else {
        this.selected = isSelected;
    }

    if (this.selected) {
        this._hiddenFieldNode.value = "true";
        this._titleContainer.className = this._theme.getClassName("ACCORDION_TABEXPANDED");
        this._turnerContainer.className = this._theme.getClassName("ACCORDION_DOWNTURNER");
        this._contentNode.style.display = "block";

        // if the tab does not have content and "loadOnSelect" is set
        // to true go ahead and refresh the widget. 
        if (!this.tabContent) {
            if (this.parent.loadOnSelect) {
                this._loadContent();
            }
        }
    } else {
        this._hiddenFieldNode.value = "false";
        this._titleContainer.className = this._theme.getClassName("ACCORDION_TABCOLLAPSED");
        this._turnerContainer.className = this._theme.getClassName("ACCORDION_RIGHTTURNER");
        this._contentNode.style.display = "none";
    }
    return true;
};

/**
 * Set the contents of the accordion tab.
 *
 * @param {Array} content The Contents of the tab body.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._setTabContent = function(content) {
    if (content) {
        for (var i = 0; i < content.length; i++) {
            this._widget._addFragment(this._contentNode, content[i], "last");
        }
    }
    return true;
};

/**
 * Set the title associated with the accordion tab.
 *
 * @param {String} title Title property associated with the tab.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._setTitle = function (title) {
    if (title) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this._widget._addFragment(this._titleNode, {
                id: this.id + "_titleLink",
                title: title,
                onClick: "return false;",
                className: "",
                contents: [title],
                widgetType: "hyperlink"
        });
    }
    return true;
};

/**
 * This function takes care of traversing through the accordionTab depending
 * on which key is pressed. If tab is open and current focus is on the title, 
 * the focus should move to the first element in the the open tab. Else it 
 * should simply move out accordion.
 * @param (String) keyCode The value of the key which was pressed
 * @param (Event) event The key press event.
 * @param (String) nodeId The id of the accordionTab. 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.accordionTab.prototype._traverseMenu = function(keyCode, event, nodeId) {
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
            if (this._contentNode.blur) {
                this._contentNode.blur();
            }
            if (this._titleContainer.blur) {
                this._titleContainer.blur();
            }
            // this.focusId = null;
            // this._domNode.tabIndex = -1;  //new
            return true;
        }

        // If tab is open and focus is on the title section
        // shift focus to the content section, else move out of the
        // tab.
        if (this.selected) {
            if (this.focusState == "title") {
                this._contentNode.tabIndex = this.tabIndex;
                this._contentEnd.tabIndex = this.tabIndex;
                this._contentNode.focus();
                if (this.focusId == null) {
                    var fChild = this._contentNode.firstChild;
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
                this._contentNode.focus();
                if (this.focusId == null) {
                    this._contentNode.tabIndex = this.tabIndex;
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
            // this._domNode.tabIndex = -1; //new
            return true;
        }
    } else if (keyCode == 38 && event.ctrlKey) {  // handle ctrl + up

        // Move focus to the header of the accordion tab if its contents is
        // currently in focus. Else, do nothing. Also, stop event propagation.

        if (this.focusState == "content") {
            this.focusState == "title";
            if (this._contentNode.blur) {
                this._contentNode.blur();
            }
            if (this._titleContainer.focus) {
                this._titleContainer.focus();
            }
            if (@JS_NS@._base.browser._isIe5up()) {
                window. event.cancelBubble = true;
                window.event.returnValue = false;
            } else {
                event.stopPropagation();
                event.preventDefault();
            }
        }
    }
    return true;
};
