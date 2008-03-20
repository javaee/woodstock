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

webui.@THEME_JS@.dojo.provide("webui.@THEME_JS@.widget.accordion");

webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.accordionTab");
webui.@THEME_JS@.dojo.require("webui.@THEME_JS@.widget.widgetBase");

/**
 * @name webui.@THEME_JS@.widget.accordion
 * @extends webui.@THEME_JS@.widget.widgetBase
 * @class This class contains functions for the accordion widget.
 * @constructor This function is used to construct an accordion widget.
 */
webui.@THEME_JS@.dojo.declare("webui.@THEME_JS@.widget.accordion", webui.@THEME_JS@.widget.widgetBase, {
    // Set defaults.
    duration: 250,
    isContainer: true,
    loadOnSelect: false,
    multipleSelect: false,
    focusElement: null,
    widgetName: "accordion" // Required for theme properties.
});

/**
 * Helper function to add accordion header controls
 *
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.addControls = function(props) {       
    // Add expand and collapse icons only if multiple select is set to
    // true and the icons have been supplied.
    if (props.toggleControls && props.multipleSelect) {
        // Set expand all image properties.
        if (props.expandAllImage) {
            // Update/add fragment.
            this.widget.updateFragment(this.expandAllImgContainer, 
                this.expandAllImage.id, props.expandAllImage);
        }

        // Set collapse all image properties.
        if (props.collapseAllImage) {
            // Update/add fragment.
            this.widget.updateFragment(this.collapseAllImgContainer,
                this.collapseAllImage.id, props.collapseAllImage);
        }
        
        // a divider should only be added if expand/collapse icons exist.
        this.dividerNodeContainer.className = this.theme.getClassName("ACCORDION_HDR_DIVIDER");
    }

    // Set refresh image properties.
    if (props.isRefreshIcon && props.refreshImage) {
        // Update/add fragment.
        this.widget.updateFragment(this.refreshImgContainer,
            this.refreshImage.id, props.refreshImage);
    }
    return true;
};

/**
 * Close all open accordions and leave the others as is.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.collapseAllTabs = function(event) {
    // Iterate over all tabs.
    for (var i = 0; i < this.tabs.length; i++) {
        var widget = webui.@THEME_JS@.dijit.byId(this.tabs[i].id);
        if (widget && widget.selected) {
            widget.setSelected(false);
        }
    }
    this.updateFocus(this.collapseAllContainer);
    return true;
};

/**
 * The callback function for key press on the accordion header.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.createOnKeyDownCallBack = function() {
    var _id = this.id;
    var _widget = this.widget;
    return function(event) {
        var elem = document.getElementById(_id);
        if (elem == null) {
            return false;
        }
        var widget = webui.@THEME_JS@.dijit.byId(_id);

        event = _widget.getEvent(event);
        var keyCode = _widget.getKeyCode(event);
        
        // if onkeypress returns false, we do not traverse the accordion
        var keyPressResult = true;
        if (this._onkeypress) {
            keyPressResult = (this._onkeypress) ? this._onkeypress() : true;
        }        
       
        if (keyPressResult != false) {
            widget.traverseMenu(keyCode, event, _id);
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
webui.@THEME_JS@.widget.accordion.event = 
        webui.@THEME_JS@.widget.accordion.prototype.event = {
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_accordion_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_accordion_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_accordion_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_accordion_event_state_end"
    }
};

/**
 * Open all closed tabs and leave the others as is.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.expandAllTabs = function(event) {
    // Iterate over all tabs.
    for (var i = 0; i < this.tabs.length; i++) {
        var widget = webui.@THEME_JS@.dijit.byId(this.tabs[i].id);
        if (widget && !widget.selected) {
            widget.setSelected(true);
        }
    }
    this.updateFocus(this.expandAllContainer);
    return true;
};

/**
 * Set the appropriate focus before invoking tabSelected. This
 * function has been put in place because tabSelected is called
 * from various places but the focus does not have to be set
 * in all the cases.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.focusAndSelectTab = function(props) {
    
    // Iterate over all tabs to ensure id is valid.
    for (var i = 0; i < this.tabs.length; i++) {
        if (props.id == this.tabs[i].id) {
            this.focusElement = this.tabs[i];
            break;   
        }
    }
    this.tabSelected(props);
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.accordion.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // Set properties.
    if (this.collapseAllImage != null) { props.collapseAllImage = this.collapseAllImage; }
    if (this.expandAllImage != null) { props.expandAllImage = this.expandAllImage; }
    if (this.isRefreshIcon != null) { props.isRefreshIcon = this.isRefreshIcon; }
    if (this.loadOnSelect) { props.loadOnSelect = this.loadOnSelect; }
    if (this.multipleSelect) { props.multipleSelect = this.multipleSelect; }
    if (this.refreshImage != null) { props.refreshImage = this.refreshImage; }
    if (this.style != null) { props.style = this.style; }
    if (this.tabs != null) { props.tabs = this.tabs; }
    if (this.toggleControls) { props.toggleControls = this.toggleControls; }
    if (this.type) { props.type = this.type; }
 
    return props;
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME_JS@.widget.accordion.prototype.getClassName = function() {
    // Get theme property.
    var className = this.theme.getClassName("ACCORDION_DIV", "");
    return (this.className)
        ? className + " " + this.className
        : className;
};

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.postCreate = function () {
    // Set ids.
    if (this.id) {
        this.domNode.id = this.id;
        this.headerContainer.id = this.id + "_accHeader";
        this.expandAllContainer.id = this.id + "_expandAllNode";
        this.expandAllImgContainer.id = this.expandAllContainer.id + "_expandAllImage";
        this.collapseAllContainer.id = this.id + "_collapseAllNode";
        this.collapseAllImgContainer.id = this.collapseAllImgContainer.id + "_collapseAllImage";
        this.dividerNodeContainer.id = this.id + "_dividerNode";
        this.refreshNodeContainer.id = this.id + "_refreshNode";
    }

    // Set class names.
    this.headerContainer.className = this.theme.getClassName("ACCORDION_HDR");
    this.collapseAllContainer.className = this.theme.getClassName("ACCORDION_HDR_CLOSEALL");
    this.expandAllContainer.className = this.theme.getClassName("ACCORDION_HDR_OPENALL");
    
    // the divider should initially be hidden
    this.dividerNodeContainer.className = this.theme.getClassName("HIDDEN");
    this.refreshNodeContainer.className = this.theme.getClassName("ACCORDION_HDR_REFRESH");

    // Set events.
    var _id = this.id;
    this.dojo.connect(this.collapseAllContainer, "onclick", this, "collapseAllTabs");
    this.dojo.connect(this.expandAllContainer, "onclick", this, "expandAllTabs");
    this.dojo.connect(this.refreshNodeContainer, "onclick", function(event) {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        var widget = webui.@THEME_JS@.dijit.byId(_id);
        widget.updateFocus(this.refreshNodeContainer);
        widget.refresh(_id);
        return false;
    });
    
    // Create callback function for onkeydown event.
    this.dojo.connect(this.domNode, "onkeydown", this.createOnKeyDownCallBack());
    
    // Subscribe to the "tabSelected" event present in the accordion widget.
    this.subscribe(webui.@THEME_JS@.widget.accordionTab.event.title.selectedTopic,
        this, "focusAndSelectTab");

    // Generate the accordion header icons on the client side.
    if (this.toggleControls && this.multipleSelect) {
        if (this.expandAllImage == null) {
            this.expandAllImage = this.widget.getWidgetProps("imageHyperlink", {
                    id: this.id + "_expandImageLink",
                    onClick: "return false;",
                    enabledImage: this.widget.getWidgetProps("image", {
                        icon: "ACCORDION_EXPAND_ALL",
                        id: this.id + "_expandAll"
                    }),
                    title: this.theme.getMessage("Accordion.expandAll")            
                });
        }
        
        if (this.collapseAllImage == null) {
            this.collapseAllImage = this.widget.getWidgetProps("imageHyperlink", {
                    id: this.id + "_collapseImageLink",
                    onClick: "return false;",
                    enabledImage: this.widget.getWidgetProps("image", {
                        icon: "ACCORDION_COLLAPSE_ALL",
                        id: this.id + "_collapseAll"
                    }),
                    title: this.theme.getMessage("Accordion.collapseAll")            
                });
        }
    }

    // Set refresh image hyperlink properties.
    if (this.isRefreshIcon) {
        if (this.refreshImage == null) {
            this.refreshImage = this.widget.getWidgetProps("imageHyperlink", {
                    id: this.id + "_refreshImageLink",
                    onClick: "return false;",
                    enabledImage: this.widget.getWidgetProps("image", {
                        icon: "ACCORDION_REFRESH",
                        id: this.id + "_refresh"
                    }),
                    title: this.theme.getMessage("Accordion.refresh")            
                });
         }
    }
    
    if (this.isRefreshIcon) {
        this.refreshNodeContainer.tabIndex = this.tabIndex;
    }
    if (this.toggleControls && this.multipleSelect) {
            this.expandAllContainer.tabIndex = this.tabIndex;
            this.collapseAllContainer.tabIndex = this.tabIndex;
    }
    if (this.tabs.length > 0) {
        for (var i=0; i< this.tabs.length; i++) {
            this.tabs[i].tabIndex = this.tabIndex;
        }
    }

    with (this.domNode.style) {
        if (position != "absolute") {
            position = "relative";
        }
        overflow = "hidden";
    }
    return this.inherited("postCreate", arguments);
};

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
 * @config {Object} collapseAllImage Image associated with collapse all icon
 * @config {Object} expandAllImage Image associated with expand all icon
 * @config {String} id Uniquely identifies an element within a document.
 * @config {boolean} isRefreshIcon True if refresh icon should be set
 * @config {boolean} multipleSelect Set to true if multiple tabs can be selected
 * @config {Object} refreshImage Image associated with refresh icon.
 * @config {String} style Specify style rules inline.
 * @config {Array} tabs Tabs constituting the accordion's children
 * @config {boolean} toggleControls Set to true if expand/collapse icons should be set.
 * @config {boolean} visible Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace tabs -- do not extend.
    if (props.tabs) {
        this.tabs = null;
    }

    // Extend widget object for later updates.
    return this.inherited("setProps", arguments);
};

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
webui.@THEME_JS@.widget.accordion.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    if (props.tabIndex == null) {
        props.tabIndex = -1;
        this.tabIndex = -1;
    }

    // add control icons - refresh, expandall, collapseall.
    this.addControls(props); 

    // If we are coming here for
    // the first time there will be no children. The other case is when 
    // the accordion is being rerendered because of a refresh in which 
    // we want to use the latest set of children. addFragment is supposed
    // to do that.
    if (props.tabs) {
        // Remove child nodes.
        this.widget.removeChildNodes(this.tabsContainer);

        // set the tab focus 
        var tabFocus = true;
        
        if (props.tabs.length > 0) {
            for (var i=0; i< props.tabs.length; i++) {
                props.tabs[i].tabIndex = this.tabIndex;
            }
        }
       
        // Add tabs.
        for (var i = 0; i < props.tabs.length; i++) {
            this.widget.addFragment(this.tabsContainer, props.tabs[i], "last");
        }
    }

    // Set more properties.
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
};

/**
 * Set a different look for refresh/expand/collapse icons when focus is set
 * on them.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.setFocusStyleClass = function(nodeId) {
    if (nodeId == this.collapseAllContainer.id) {
        //set focus style on collapseNode
        this.collapseAllContainer.className = this.theme.getClassName("ACCORDION_HDR_CLOSEALL_FOCUS");
    } else if (nodeId == this.expandAllContainer.id) {
        //set focus style on expandNode
        this.expandAllContainer.className = this.theme.getClassName("ACCORDION_HDR_OPENALL_FOCUS");
    } else if (nodeId == this.refreshNodeContainer.id) {
        //set focus style on refreshNode
        this.refreshNodeContainer.className = this.theme.getClassName("ACCORDION_HDR_REFRESH_FOCUS");
    }
    return true;
};

/**
 * Reset the styles asscociated with refresh/expand/collapse icons when these 
 * icons are blurred.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.setBlurStyleClass = function(nodeId) {
    if (nodeId == this.collapseAllContainer.id) {
        //set normal className on collapseNode
        this.collapseAllContainer.className = this.theme.getClassName("ACCORDION_HDR_CLOSEALL");
    } else if (nodeId == this.expandAllContainer.id) {
        //set normal className on expandNode
        this.expandAllContainer.className = this.theme.getClassName("ACCORDION_HDR_OPENALL");
    } else if (nodeId == this.refreshNodeContainer.id) {
        //set normal className on refreshNode
        this.refreshNodeContainer.className = this.theme.getClassName("ACCORDION_HDR_REFRESH");
    }
    return true;
};

/**
 * Set appropriate styles when a tab is in focus.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.setTabFocus = function(nodeId) {
    // update the tab with the appropriate tabIndex
    var tabWidget = webui.@THEME_JS@.dijit.byId(nodeId);
    var props = {tabIndex: this.tabIndex};
    this.widget.updateFragment(this.tabsContainer, nodeId, props);

    // set the style class to indicate that the tab is in focus.
    if (tabWidget.selected) {
        tabWidget.titleContainer.className = this.theme.getClassName("ACCORDION_TABEXPANDED_FOCUS");
    } else {
        tabWidget.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED_FOCUS");
    }
    tabWidget.domNode.focus();
    return true;
};

/**
 * Set appropriate styles when a tab loses focus.
 *
 * @param {String} nodeId The node ID of the icon.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.setTabBlur = function(nodeId) {
    // update the tab with the appropriate tabIndex
    var tabWidget = webui.@THEME_JS@.dijit.byId(nodeId);
    
    if (tabWidget.selected) {
        tabWidget.titleContainer.className = this.theme.getClassName("ACCORDION_TABEXPANDED");
        tabWidget.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
    } else { 
        tabWidget.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
        tabWidget.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    }    

    if (tabWidget) {
        tabWidget.titleContainer.blur();
    }
    return true;
};

/**
 * Process tab selected events.
 *
 * @param props Key-Value pairs of properties.
 * @config {String} id The id of the selected tab.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.tabSelected = function(props) {
    var widget = null;

    // Iterate over all tabs to ensure id is valid.
    for (var i = 0; i < this.tabs.length; i++) {
        if (props.id == this.tabs[i].id) {
            widget = webui.@THEME_JS@.dijit.byId(this.tabs[i].id);
            break;   
        }
    }
    
    // Return if id was not valid.
    if (widget == null) {
            return false;
    }
    
    if (this.multipleSelect) {
        widget.setSelected(true);
    } else {
        for (var i = 0; i < this.tabs.length; i++) {
            widget = webui.@THEME_JS@.dijit.byId(this.tabs[i].id);
            if (widget) {
                widget.setSelected(props.id == this.tabs[i].id);
                this.tabs[i] = widget.getProps();
            }
        }
    }
    return true;
};

/**
 * This function traverses through the accordion depending
 * on which key is pressed. If the down or right arrow key is pressed the
 * next focusable element of the accordion is focused on. If the last element 
 * was on focus this changes to the first element. If the enter or space key
 * was pressed the onclick function associated with the focused element is 
 * activated. 
 * @param (String) keyCode The valye of the key which was pressed
 * @param (Event) event The key press event.
 * @param (String) nodeId The id of the accordion item. 
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.traverseMenu = function(keyCode, event, nodeId) {
    var savedFocusElement;

    if (this.focusElement != null) {
        savedFocusElement = this.focusElement;
    }

    // Operations to be performed if the arrow keys are pressed.
    // down or right arrow key ==> forward
    // up or left arrow key ==> backward
    // During forward traversal control should go from the refresh icon
    // to the collapseAll icon to the expandAll icon to the tabs from
    // top to bottom. If control is on the last tab, clicking a forward
    // key will cause control to come back to the first tab and not the
    // accordion control icons. A similar but opposite behavior is 
    // exhibited when the up or left keys are pressed. Once control is
    // on the first tab backward traversal will take control to the last 
    // tab in the accordion.

    if (keyCode >= 37 && keyCode <= 40) {
        var forward = true;
        if (keyCode == 37 || keyCode == 38) {
            forward = false;
        }
        var focusSet = false;
        if (forward) {  // handling the down and right arrow keys
            if (this.isRefreshIcon) {
                if (this.focusElement.id == this.refreshNodeContainer.id) {
                    if (this.toggleControls && this.multipleSelect) {
                        this.updateFocus(this.collapseAllContainer);
                        focusSet = true;
                    } else {
                        this.updateFocus(this.tabs[0]);
                        focusSet = true;
                    }
                }
            }
            
            if (!focusSet && this.toggleControls && this.multipleSelect) {
                
                if (this.focusElement.id == this.collapseAllContainer.id) {
                    this.updateFocus(this.expandAllContainer);
                    focusSet = true;

                } else if (this.focusElement.id == this.expandAllContainer.id) {
                    this.updateFocus(this.tabs[0]);
                    focusSet = true;
                
                } else {
                    for (var i = 0; i < this.tabs.length; i++) {
                        if (this.tabs[i].id == this.focusElement.id) {
                            var newIndex = (i + 1) % this.tabs.length;
                            this.updateFocus(this.tabs[newIndex]);
                            focusSet = true;
                            break;
                        }
                    }
                }
            }
            if (!focusSet) {
                for (var i = 0; i < this.tabs.length; i++) {
                    if (this.tabs[i].id == this.focusElement.id) {
                        var newIndex = (i + 1) % this.tabs.length;
                        this.updateFocus(this.tabs[newIndex]);
                        focusSet = true;
                        break;
                    }
                }
                if (this.focusElement == null) {
                    this.updateFocus(this.tabs[0]);
                }
            }

         } else {  // traverse backward

            if (this.isRefreshIcon) {
                if (this.focusElement.id == this.refreshNodeContainer.id) {
                    var index = this.tabs.length;
                    this.updateFocus(this.tabs[index-1]);
                    focusSet = true;
                }
            }
            
            if (!focusSet && this.toggleControls && this.multipleSelect) {
                if (this.focusElement.id == this.collapseAllContainer.id) {
                    if (this.isRefreshIcon) {
                        this.updateFocus(this.refreshNodeContainer);
                        focusSet = true;
                    } else {
                        var index = this.tabs.length;
                        focusSet = true;
                        this.updateFocus(this.tabs[index-1]);
                    }
 
                } else if (this.focusElement.id == this.expandAllContainer.id) {
                    this.updateFocus(this.collapseAllContainer);
                    focusSet = true;
                }
            }
            if (!focusSet) {
                for (var i = 0; i < this.tabs.length; i++) {
                    if (this.tabs[i].id == this.focusElement.id) {
                        if (i == 0) {
                            var index = this.tabs.length;
                            this.updateFocus(this.tabs[index-1]);
                            focusSet = true;
                            break;
                            
                        } else {
                            this.updateFocus(this.tabs[i-1]);
                            focusSet = true;
                        }
                        break;
                    }
                }
                if (this.focusElement == null) { //focus on the last tab
                    var index = this.tabs.length;
                    this.updateFocus(this.tabs[index - 1]);
                    focusSet = true;
                }
            }
        }

        if (webui.@THEME_JS@.browser.isIe5up()) {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        } else {
            event.stopPropagation();
            event.preventDefault();
        }
    } else if(keyCode == 13 || keyCode == 32) {  // handle enter and space bar
        var actionComplete = false;
        if (this.isRefreshIcon) {
            if (this.focusElement.id == this.refreshNodeContainer.id) {
                var accWidget = webui.@THEME_JS@.dijit.byId(nodeId);
                accWidget.refresh(nodeId);
                actionComplete = true;
            }    
        }
        if (!actionComplete && this.toggleControls && this.multipleSelect) {
            if (this.focusElement.id == this.collapseAllContainer.id) {
                this.collapseAllTabs();
                actionComplete = true;
                
            } else if (this.focusElement.id == this.expandAllContainer.id) {
                this.expandAllTabs();
                actionComplete = true;
            }
        } 
        if (!actionComplete) { // has to be an accordion tab
            if (this.focusElement) {
                var props;
                if (this.focusElement.selected) {
                    var widget = webui.@THEME_JS@.dijit.byId(this.focusElement.id);
                    widget.setSelected(false);
                } else {
                    var widget = webui.@THEME_JS@.dijit.byId(this.focusElement.id);
                    widget.setSelected(true);
                }
             
            }
        }

        if (webui.@THEME_JS@.browser.isIe5up()) {
            window.event.cancelBubble = true;
            window.event.returnValue = false;
        } else {
            event.stopPropagation();
            event.preventDefault();
        }
    } else if (keyCode == 9) {  // handle tabbing
        
        // Tabbing order:  refresh -> expand/collapse -> first tab header
        // -> first tabable element in tab content (if tab is open)
        // -> tab out of the accordion completely. Reverse tabbing order
        // is also supported.
        
        var forward = true;
        if (event.shiftKey) {
            forward = false;
        }
        if (this.focusElement) {
            var focusSet = false;
            if (this.isRefreshIcon) {
                if (this.focusElement.id == this.refreshNodeContainer.id) {
                    if (forward) {
                        if (this.toggleControls && this.multipleSelect) {
                            this.focusElement = this.collapseAllContainer;
                            this.setFocusStyleClass(this.collapseAllContainer.id);
                            this.setBlurStyleClass(this.refreshNodeContainer.id);
                            focusSet = true;
                            return true;
                        } else {
                            this.focusElement = this.tabs[0];
                            this.setBlurStyleClass(this.refreshNodeContainer.id);
                            this.setTabFocus(this.focusElement.id);
                            focusSet = true;
                            return true;
                        }
                    } else {
                        this.focusElement = null;
                        this.setBlurStyleClass(this.refreshNodeContainer.id);
                        focusSet = true;
                        return true;
                    }
                }
            }

            if (!focusSet && this.toggleControls && this.multipleSelect) {

                if (this.focusElement.id == this.collapseAllContainer.id) {
                    if (forward) {
                        this.focusElement = this.expandAllContainer;
                        this.setBlurStyleClass(this.collapseAllContainer.id);
                        this.setFocusStyleClass(this.expandAllContainer.id);
                        focusSet = true;
                    } else {
                        this.focusElement = this.refreshNodeContainer;
                        this.setFocusStyleClass(this.refreshNodeContainer.id);
                        this.setBlurStyleClass(this.collapseAllContainer.id);
                        focusSet = true;
                    }
                } else if (this.focusElement.id == this.expandAllContainer.id) {
                    if (forward) {
                        this.focusElement = this.tabs[0];
                        this.setTabFocus(this.focusElement.id);
                        this.setBlurStyleClass(this.expandAllContainer.id);
                        focusSet = true;
                    } else {
                        this.focusElement = this.collapseAllContainer;
                        this.setFocusStyleClass(this.collapseAllContainer.id);
                        this.setBlurStyleClass(this.expandAllContainer.id);
                        focusSet = true;
                    }
                } 
            }

            if (!focusSet) { // focus is on a tab
                if (forward) {  
                    var widget = webui.@THEME_JS@.dijit.byId(this.focusElement.id);
                    if ((widget.getProps().selected == false) ||
                        (widget.focusState == "end")) {
                        
                        // simply move out of the accordion if tab is closed
                        // or tab is open but user has navigated to the end of
                        // the content section.
                        
                        for (var i = 0; i < this.tabs.length; i++) {
                            if (this.tabs[i].id == this.focusElement.id) {
                                var newIndex = (i + 1);
                                if (newIndex == this.tabs.length) {
                                    this.updateFocus(null);
                                    return true;
                                }
                                this.updateFocus(this.tabs[newIndex]);
                                focusSet = true;
                                return true;
                            }
                        }

                    }
                    focusSet = true;
                    
                } else {    // move to the expand/collapse/refresh icons
                    
                    this.setTabBlur(this.focusElement.id);
                    if (this.toggleControls && this.multipleSelect) {
                        this.focusElement = this.expandAllContainer;
                        this.setFocusStyleClass(this.expandAllContainer.id);
                    } else { 
                        this.focusElement = this.refreshNodeContainer;
                        this.setFocusStyleClass(this.refreshNodeContainer.id);
                    }
                    focusSet = true;
                    return true;
                }
            }

        } else { //get focus to the first element
            var focusSet = false;
            if (this.isRefreshIcon) {
               this.focusElement = this.refreshNodeContainer;
               this.setFocusStyleClass(this.refreshNodeContainer.id);
               focusSet = true;
            }
            if (!focusSet && this.toggleControls && this.multipleSelect) {
                this.focusElement = this.collapseAllContainer;
                this.setFocusStyleClass(this.collapseAllContainer.id);
                focusSet = true;
            } 
            if (!focusSet) {
                this.focusElement = this.tabs[0];
                this.setTabFocus(this.focusElement.id);
            }
            
        } 
    } 
    return true;
};

/**
 * This function updates the focused node within the accordion.
 * The old focus element is blurred and the new focus element is
 * set to focus. Appropriate style selectors are applied. 
 * @param (String) newFocusNode The new focus node.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.accordion.prototype.updateFocus = function(newFocusNode) {

    if (this.focusElement) {
        if (this.focusElement.id == this.refreshNodeContainer.id) {
            this.setBlurStyleClass(this.refreshNodeContainer.id);
        } else if (this.focusElement.id == this.collapseAllContainer.id) {
            this.setBlurStyleClass(this.collapseAllContainer.id);
        } else if (this.focusElement.id == this.expandAllContainer.id) {
            this.setBlurStyleClass(this.expandAllContainer.id);
        } else {
            for (var i = 0; i < this.tabs.length; i++) {
                if (this.tabs[i].id == this.focusElement.id) {
                    this.setTabBlur(this.tabs[i].id);
                    break;
                }
            }
        }
    }
    
    // set the new focusElement and then the associate syles etc.
    if (newFocusNode) {
        this.focusElement = newFocusNode;
        if (this.focusElement.id == this.refreshNodeContainer.id) {
                this.setFocusStyleClass(this.refreshNodeContainer.id);
        } else if (this.focusElement.id == this.collapseAllContainer.id) {
                this.setFocusStyleClass(this.collapseAllContainer.id);
        } else if (this.focusElement.id == this.expandAllContainer.id) {
                this.setFocusStyleClass(this.expandAllContainer.id);
        } else {
            for (var i = 0; i < this.tabs.length; i++) {
                if (this.tabs[i].id == this.focusElement.id) {
                    this.setTabFocus(this.tabs[i].id);
                    break;
                }
            }
        }
    }
    return true;
};
