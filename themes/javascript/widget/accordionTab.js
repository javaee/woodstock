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

dojo.provide("webui.@THEME@.widget.accordionTab");

dojo.require("dojo.widget.*");
dojo.require("dojo.html.*");
dojo.require("dojo.lfx.html");
dojo.require("dojo.html.selection");
dojo.require("dojo.widget.html.layout");
dojo.require("dojo.widget.PageContainer");
dojo.require("dojo.widget.ContentPane");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

webui.@THEME@.widget.accordionTab = function() {
    // Register the widget.
    dojo.widget.HtmlWidget.call(this);
}

dojo.inherits(webui.@THEME@.widget.accordionTab, webui.@THEME@.widget.widgetBase);

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.accordionTab.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_accordionTab_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_accordionTab_refresh_end",

    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.accordionTab");

        // Publish event.
        dojo.event.topic.publish(webui.@THEME@.widget.accordionTab.refresh.beginEventTopic, {
            id: this.id,
            execute: execute,
            endEventTopic: webui.@THEME@.widget.accordionTab.refresh.endEventTopic
        });
        return true;
    }
}

/**
 * This closure is used to process tabChange events.
 */
webui.@THEME@.widget.accordionTab.tabAction = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_accordionTab_tabAction_begin",
    endEventTopic: "webui_@THEME@_widget_accordionTab_tabAction_end",

    /**
     * TabChange being refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.accordionTab");

        // Publish event.
        dojo.event.topic.publish(webui.@THEME@.widget.accordionTab.tabAction.beginEventTopic,{
            id: this.id,
            execute: "menuAction"
        });
        return true;
    }
}

/**
 * This closure is used to load the contents of a tab asynchronously.
 */
webui.@THEME@.widget.accordionTab.loadContent = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_accordionTab_loadContent_begin",
    endEventTopic: "webui_@THEME@_widget_accordionTab_loadContent_end",

    /**
     * TabChange being refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.accordionTab");

        // Publish event.
        dojo.event.topic.publish(webui.@THEME@.widget.accordionTab.loadContent.beginEventTopic, {
            id: this.id,
            execute: "load"
        });
        return true;
    }
}

/**
 * This function is used to generate a template based widget.
 */
webui.@THEME@.widget.accordionTab.fillInTemplate = function() {
    // dojo.debug("inside fillinTemplate for accordion Tab");

    if (this.id) {
        this.domNode.id = this.id;
        this.titleNode.id = this.id + "_tabTitle";
        this.turnerContainer.id = this.id + "_tabTitleTurner";
        this.menuContainer.id = this.id + "_tabMenu";
        this.hiddenFieldNode.id = this.id + ":selectedState";
        this.hiddenFieldNode.name = this.hiddenFieldNode.id;
    }

    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.processAction = function(execute) { return dojo.widget.byId(this.id).processAction(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }

    this.setProps();

}

/**
 * This function is used to get widget properties. Please see
 * setProps() for a list of supported properties.
 */
webui.@THEME@.widget.accordionTab.getProps = function() {
    var props = {};

    // Set properties.
    if (this.selected) { props.selected = this.selected; }
    if (this.title) { props.title = this.title; }
    if (this.tabContent) { props.tabContent = this.tabContent; }
    if (this.visible != null) { props.visible = this.visible; }
    if (this.actions != null) { props.actions = this.actions; }
    if (this.styleClass != null) { props.styleClass = this.styleClass; }
    if (this.style != null) { props.style = this.style; }
    if (this.contentHeight != null) { props.contentHeight = this.contentHeight; }
    if (this.id) { props.id = this.id; }
    if (this.type) { props.type = this.type; }

    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getJavaScriptProps());

    return props;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>contentHeight</li>
 *  <li>Style</li>
 *  <li>styleClass</li>
 *  <li>title</li>
 *  <li>tabContent</li>
 *  <li>visible</li>
 *  <li>hiddenField</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.accordionTab.setProps = function(props) {

    if (props != null) {
        if (props.tabContent) {
            this.tabContent = null;
        }
        this.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
        dojo.event.connect(this.titleContainer, "onclick", this, "onTitleClick");
        dojo.event.connect(this.titleContainer, "onmouseover", this, "onTitleMouseOver");
        dojo.event.connect(this.turnerContainer, "onmouseover", this, "onTitleMouseOver");
        dojo.event.connect(this.turnerContainer, "onclick", this, "onTitleClick");
        dojo.event.connect(this.menuContainer, "onmouseover", this, "onTitleMouseOver");
        dojo.event.connect(this.menuContainer, "onclick", this, "onMenuClick");
        dojo.event.connect(this.titleContainer, "onmouseout", this, "onTitleMouseOut");
        dojo.event.connect(this.turnerContainer, "onmouseout", this, "onTitleMouseOut");
        dojo.event.connect(this.menuContainer, "onmouseout", this, "onTitleMouseOut");
    }

    if (props.hiddenField) {
    }
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
            this.titleContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionTabExpanded;
            this.turnerContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionDownTurner;
            this.contentNode.style.display = "block";
        } else {
            this.hiddenFieldNode.value = "false";
            this.titleContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionTabCollapsed;
            this.turnerContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionRightTurner;
            this.contentNode.style.display = "none";
        }

    }


    // Set DOM node properties.
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.domNode, props);
    this.setJavaScriptProps(this.domNode, props);

}

/**
 * Set the title associated with the accordion tab.
 *
 *  @param title Title property associated with the tab.
 */
webui.@THEME@.widget.accordionTab.setTitle = function (title) {

    if (title) {
        var titleHref = dojo.widget.byId(this.titleHref.id); 
        if (titleHref) {
                titleHref.setProps(title);
        } else {
            this.addFragment(this.titleHref, title);
        }
    }
}

/**
 * Set the contents of the accordion tab.
 *
 *  @param content Contents associated with the tab.
 */
webui.@THEME@.widget.accordionTab.setTabContent = function(content) {
    if (content) {
        for (var i = 0; i < content.length; i++) {
            this.addFragment(this.contentNode, 
                    content[i], "last");
        }
    }
}

// this feature will be augmented on the coming days. The API may change
// somewhat.
webui.@THEME@.widget.accordionTab.onMenuClick = function(e) {
    // dojo.debug("menu clicked...");
    dojo.event.browser.stopEvent(e);
}

webui.@THEME@.widget.accordionTab.getTitleHeight = function () {
    return dojo.html.getMarginBox(this.titleContainer).height;
}

webui.@THEME@.widget.accordionTab.onTitleClick = function () {
    this.parent.selectChild(this);
}

webui.@THEME@.widget.accordionTab.onTitleMouseOver = function() {

    if (this.selected) {
        this.turnerContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionDownTurner;
    } else {
        this.turnerContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionRightTurner;
    }
}

webui.@THEME@.widget.accordionTab.onTitleMouseOut = function() {
    if (this.selected) {
        this.titleContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionTabExpanded;
        this.turnerContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionDownTurner;
        return false;
    }

    this.titleContainer.className = 
        webui.@THEME@.widget.props.accordionTab.accordionTabCollapsed;
    this.turnerContainer.className = 
        webui.@THEME@.widget.props.accordionTab.accordionRightTurner;
}

webui.@THEME@.widget.accordionTab.setSelected = function (isSelected) {

    if (this.selected) {
        this.selected = false;
    } else {
        this.selected = isSelected;
    }

    if (this.selected) {
        this.hiddenFieldNode.value = "true";
        this.titleContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionTabExpanded;
        this.turnerContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionDownTurner;
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
        this.titleContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionTabCollapsed;
        this.turnerContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionRightTurner;
        this.contentNode.style.display = "none";

    }
}

dojo.lang.extend(webui.@THEME@.widget.accordionTab, {

    widgetType: "accordionTab",
    isContainer: true,
    selected: false,
    open: false,
    loadOnSelect: false,
    submitOnChange: false,

    fillInTemplate: webui.@THEME@.widget.accordionTab.fillInTemplate,
    refresh: webui.@THEME@.widget.accordionTab.refresh.processEvent,
    processAction: webui.@THEME@.widget.accordionTab.tabAction.processEvent,
    processLoad: webui.@THEME@.widget.accordionTab.loadContent.processEvent,
    getProps: webui.@THEME@.widget.accordionTab.getProps,
    setProps: webui.@THEME@.widget.accordionTab.setProps,
    setTitle: webui.@THEME@.widget.accordionTab.setTitle,
    setTabContent: webui.@THEME@.widget.accordionTab.setTabContent,
    onMenuClick: webui.@THEME@.widget.accordionTab.onMenuClick,
    getTitleHeight: webui.@THEME@.widget.accordionTab.getTitleHeight,
    onTitleClick: webui.@THEME@.widget.accordionTab.onTitleClick,
    onTitleMouseOver: webui.@THEME@.widget.accordionTab.onTitleMouseOver,
    onTitleMouseOut: webui.@THEME@.widget.accordionTab.onTitleMouseOut,
    setSelected: webui.@THEME@.widget.accordionTab.setSelected 
});
//-->
