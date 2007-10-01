// widget/accordionTab.js
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

/**
 * @name widget/accordionTab.js
 * @version @THEME_VERSION@
 * @overview This module contains classes and functions for the accordionTab widget.
 * @example The following code is used to create a accordionTab widget.
 * <p><code>
 * var widget = new webui.@THEME@.widget.accordionTab(props, domNode);
 * </code></p>
 */
dojo.provide("webui.@THEME@.widget.accordionTab");

dojo.require("webui.@THEME@.widget.widgetBase");

/**
 * This function is used to construct a template based widget.
 *
 * @name webui.@THEME@.widget.accordionTab
 * @inherits webui.@THEME@.widget.widgetBase
 * @constructor
 */
dojo.declare("webui.@THEME@.widget.accordionTab", webui.@THEME@.widget.widgetBase, {
    // Set defaults.
    isContainer: true,
    selected: false,
    widgetName: "accordionTab" // Required for theme properties.    
});

/**
 * This closure contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 *
 * @ignore
 */
webui.@THEME@.widget.accordionTab.prototype.event =
        webui.@THEME@.widget.accordionTab.event = {
    /**
     * This closure contains load event topics.
     * @ignore
     */
    load: {
        /** Load event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_load_begin",

        /** Load event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordionTab_event_load_end"
    },

    /**
     * This closure contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordionTab_event_refresh_end"
    },

    /**
     * This closure contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME@_widget_accordionTab_event_state_end"
    },

    /**
     * This closure contains title event topics.
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
 */
webui.@THEME@.widget.accordionTab.prototype.loadContent = function(execute) {
    // Include default AJAX implementation.
    this.ajaxify();

    // Publish event.
    dojo.publish(webui.@THEME@.widget.accordionTab.event.load.beginTopic, [{
        id: this.id
    }]);
    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
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
    if (this.type) { props.type = this.type; }

    return props;
}

/**
 * Get title height.
 */
webui.@THEME@.widget.accordionTab.prototype.getTitleHeight = function () {
    // Warning: This function has been made private.
    return dojo._getMarginBox(this.titleContainer).height;
}

/**
 * Handle menu onClick event.
 *
 * @param {Event} event The JavaScript event.
 */
webui.@THEME@.widget.accordionTab.prototype.onMenuClickCallback = function(event) {
    dojo.stopEvent(event);
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
 */
webui.@THEME@.widget.accordionTab.prototype.onTitleClickCallback = function (event) {
    dojo.publish(webui.@THEME@.widget.accordionTab.event.title.selectedTopic, [{
        id: this.id
    }]);
    return true;
}

/**
 * Handle title onMouseOut event.
 *
 * @param {Event} event The JavaScript event.
 */
webui.@THEME@.widget.accordionTab.prototype.onTitleMouseOutCallback = function(event) {
    if (this.selected) {
        this.titleContainer.className = this.theme.getClassName("ACCORDION_TABEXPANDED");
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
        return false;
    }
    this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
    this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    return true;
}

/**
 * Handle title onMouseOver event.
 *
 * @param {Event} event The JavaScript event.
 */
webui.@THEME@.widget.accordionTab.prototype.onTitleMouseOverCallback = function(event) {
    if (this.selected) {
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
    } else {
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    }
    return true;
}

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't yet exist. 
 * </p>
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
    dojo.connect(this.titleContainer, "onclick", this, "onTitleClickCallback");
    dojo.connect(this.titleContainer, "onmouseover", this, "onTitleMouseOverCallback");
    dojo.connect(this.turnerContainer, "onmouseover", this, "onTitleMouseOverCallback");
    dojo.connect(this.turnerContainer, "onclick", this, "onTitleClickCallback");
    dojo.connect(this.menuContainer, "onmouseover", this, "onTitleMouseOverCallback");
    dojo.connect(this.menuContainer, "onclick", this, "onMenuClickCallback");
    dojo.connect(this.titleContainer, "onmouseout", this, "onTitleMouseOutCallback");
    dojo.connect(this.turnerContainer, "onmouseout", this, "onTitleMouseOutCallback");
    dojo.connect(this.menuContainer, "onmouseout", this, "onTitleMouseOutCallback");

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
 * @config {String} [className] CSS selector.
 * @config {int} [contentHeight] CSS selector.
 * @config {String} [hiddenField] 
 * @config {String} [id] Uniquely identifies an element within a document.
 * @config {Array} [tabContent] 
 * @config {String} [style] Specify style rules inline.
 * @config {String} [title] Provides a title for element.
 * @config {boolean} [visible] Hide or show element.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
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
 * Note: This is considered a private API, do not use. This function should only
 * be invoked via setProps().
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @private
 */
webui.@THEME@.widget.accordionTab.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

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

    // Set remaining properties.
    return this.inherited("_setProps", arguments);
}

/**
 * Set tab selected.
 *
 * @param {boolean} isSelected true if selected.
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
 */
webui.@THEME@.widget.accordionTab.prototype.setTitle = function (title) {
    if (title) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.widget.addFragment(this.titleHref, title);
    }
    return true;
}
