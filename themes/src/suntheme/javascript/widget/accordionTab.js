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

dojo.provide("webui.@THEME@.widget.accordionTab");

dojo.require("dojo.html.*");
dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.accordionTab = function() {
    // Register the widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.accordionTab.event = {
    /**
     * This closure is used to load the contents of a tab.
     */
    loadContent: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_loadContent_begin",
        endTopic: "webui_@THEME@_widget_accordionTab_event_loadContent_end",

        /**
         * TabChange being refresh event.
         *
         * @param execute Comma separated string containing a list of client ids 
         * against which the execute portion of the request processing lifecycle
         * must be run.
         */
        processEvent: function(execute) {
            // Include default AJAX implementation.
            this.ajaxify();

            // Publish event.
            dojo.event.topic.publish(webui.@THEME@.widget.accordionTab.event.loadContent.beginTopic, {
                id: this.id
            });
            return true;
        }
    },

    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_accordionTab_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_state_begin",
        endTopic: "webui_@THEME@_widget_accordionTab_event_state_end"
    },

    /**
     * This closure is used to process tabChange events.
     */
    tabAction: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_accordionTab_event_tabAction_begin",
        endTopic: "webui_@THEME@_widget_accordionTab_event_tabAction_end",

        /**
         * TabChange being refresh event.
         *
         * @param execute Comma separated string containing a list of client ids 
         * against which the execute portion of the request processing lifecycle
         * must be run.
         */
        processEvent: function(execute) {
            // Include default AJAX implementation.
            this.ajaxify();

            // Publish event.
            dojo.event.topic.publish(webui.@THEME@.widget.accordionTab.event.tabAction.beginTopic,{
                id: this.id
            });
            return true;
        }
    }
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the _setProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.accordionTab.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.accordionTab.superclass.fillInTemplate.call(this, props, frag);

    // Set ids.
    if (this.id) {
        this.domNode.id = this.id;
        this.titleNode.id = this.id + "_tabTitle";
        this.turnerContainer.id = this.id + "_tabTitleTurner";
        this.menuContainer.id = this.id + "_tabMenu";
        this.hiddenFieldNode.id = this.id + ":selectedState";
        this.hiddenFieldNode.name = this.hiddenFieldNode.id;
    }

    // set style classes
    this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
    this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    this.menuContainer.className = this.theme.getClassName("HIDDEN");
    this.titleNode.className = this.theme.getClassName("ACCORDION_TABTITLE");
    this.contentNode.className = this.theme.getClassName("ACCORDION_TABCONTENT");
    
    // Set public functions.
    this.domNode.processAction = function(execute) { return dojo.widget.byId(this.id).processAction(execute); }

    // Set events.
    dojo.event.connect(this.titleContainer, "onclick", this, "onTitleClick");
    dojo.event.connect(this.titleContainer, "onmouseover", this, "onTitleMouseOver");
    dojo.event.connect(this.turnerContainer, "onmouseover", this, "onTitleMouseOver");
    dojo.event.connect(this.turnerContainer, "onclick", this, "onTitleClick");
    dojo.event.connect(this.menuContainer, "onmouseover", this, "onTitleMouseOver");
    dojo.event.connect(this.menuContainer, "onclick", this, "onMenuClick");
    dojo.event.connect(this.titleContainer, "onmouseout", this, "onTitleMouseOut");
    dojo.event.connect(this.turnerContainer, "onmouseout", this, "onTitleMouseOut");
    dojo.event.connect(this.menuContainer, "onmouseout", this, "onTitleMouseOut");

    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.accordionTab.getProps = function() {
    var props = webui.@THEME@.widget.accordionTab.superclass.getProps.call(this);

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
webui.@THEME@.widget.accordionTab.getTitleHeight = function () {
    return dojo.html.getMarginBox(this.titleContainer).height;
}

/**
 * Handle menu onClick event.
 */
webui.@THEME@.widget.accordionTab.onMenuClick = function(e) {
    // this feature will be augmented on the coming days. The API may change
    // somewhat.
    dojo.event.browser.stopEvent(e);
}

/**
 * Handle title onClick event.
 */
webui.@THEME@.widget.accordionTab.onTitleClick = function () {
    this.parent.selectChild(this);
}

/**
 * Handle title onMouseOut event.
 */
webui.@THEME@.widget.accordionTab.onTitleMouseOut = function() {
    if (this.selected) {
        this.titleContainer.className = this.theme.getClassName("ACCORDION_TABEXPANDED");
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
        return false;
    }

    this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
    this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
}

/**
 * Handle title onMouseOver event.
 */
webui.@THEME@.widget.accordionTab.onTitleMouseOver = function() {
    if (this.selected) {
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_DOWNTURNER");
    } else {
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
    }
}

/**
 * This function is used to set widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 *
 * Note: This function updates the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * Note: If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 *
 * @param props Key-Value pairs of properties.
 * @param notify Publish an event for custom AJAX implementations to listen for.
 */
webui.@THEME@.widget.accordionTab.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace contents -- do not extend.
    if (props.tabContent) {
        this.tabContent = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.accordionTab.superclass.setProps.call(this, props, notify);
}

/**
 * This function is used to obtain the outermost HTML element class name.
 *
 * Note: Selectors should be concatinated in order of precedence (e.g., the
 * user's className property is always appended last).
 */
webui.@THEME@.widget.accordionTab.getClassName = function() {
    return this.className;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>contentHeight</li>
 *  <li>Style</li>
 *  <li>className</li>
 *  <li>title</li>
 *  <li>tabContent</li>
 *  <li>visible</li>
 *  <li>hiddenField</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.accordionTab._setProps = function(props) {
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

    // Set more properties..
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.accordionTab.superclass._setProps.call(this, props);
}

/**
 * Set tab selected.
 *
 * @param isSelected true if selected.
 */
webui.@THEME@.widget.accordionTab.setSelected = function (isSelected) {
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
                this.processLoad();
            }
        }
    } else {
        this.hiddenFieldNode.value = "false";
        this.titleContainer.className = this.theme.getClassName("ACCORDION_TABCOLLAPSED");
        this.turnerContainer.className = this.theme.getClassName("ACCORDION_RIGHTTURNER");
        this.contentNode.style.display = "none";
    }
}

/**
 * Set the contents of the accordion tab.
 *
 * @param content Contents associated with the tab.
 */
webui.@THEME@.widget.accordionTab.setTabContent = function(content) {
    if (content) {
        for (var i = 0; i < content.length; i++) {
            this.widget.addFragment(this.contentNode, content[i], "last");
        }
    }
}

/**
 * Set the title associated with the accordion tab.
 *
 * @param title Title property associated with the tab.
 */
webui.@THEME@.widget.accordionTab.setTitle = function (title) {
    if (title) {
        // NOTE: If you set this value manually, text must be HTML escaped.
        this.widget.addFragment(this.titleHref, title);
    }
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.accordionTab, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.accordionTab, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.accordionTab.fillInTemplate,
    getProps: webui.@THEME@.widget.accordionTab.getProps,
    getTitleHeight: webui.@THEME@.widget.accordionTab.getTitleHeight,
    onMenuClick: webui.@THEME@.widget.accordionTab.onMenuClick,
    onTitleClick: webui.@THEME@.widget.accordionTab.onTitleClick,
    onTitleMouseOver: webui.@THEME@.widget.accordionTab.onTitleMouseOver,
    onTitleMouseOut: webui.@THEME@.widget.accordionTab.onTitleMouseOut,
    processAction: webui.@THEME@.widget.accordionTab.event.tabAction.processEvent,
    processLoad: webui.@THEME@.widget.accordionTab.event.loadContent.processEvent,
    setProps: webui.@THEME@.widget.accordionTab.setProps,
    getClassName: webui.@THEME@.widget.accordionTab.getClassName,
    _setProps: webui.@THEME@.widget.accordionTab._setProps,
    setSelected: webui.@THEME@.widget.accordionTab.setSelected,    
    setTabContent: webui.@THEME@.widget.accordionTab.setTabContent,
    setTitle: webui.@THEME@.widget.accordionTab.setTitle,

    // Set defaults.
    event: webui.@THEME@.widget.accordionTab.event,
    isContainer: true,
    selected: false,
    widgetType: "accordionTab"
});
