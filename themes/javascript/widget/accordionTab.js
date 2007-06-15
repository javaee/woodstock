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

dojo.lang.extend(webui.@THEME@.widget.accordionTab, {

    widgetType: "accordionTab",
    isContainer: true,
    selected: false,
    open: false,
    loadOnSelect: false,
    submitOnChange: false,

    /**
     * This function is used to generate a template based widget.
     */
    fillInTemplate: function() {
        // dojo.debug("inside fillinTemplate for accordion Tab");

        if (this.id) {
            this.domNode.id = this.id;
            this.labelNode.id = this.id + "_tabLabel";
            this.turnerContainer.id = this.id + "_tabLabelTurner";
            this.menuContainer.id = this.id + "_tabMenu";
            this.titleContainer.id = this.id + "_tabTitle";
            // this.titleHref.id = this.id + "titleHREF";
        }

        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.processAction = function(execute) { return dojo.widget.byId(this.id).processAction(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        
        // this.processAction = webui.@THEME@.widget.accordionTab.tabAction.processEvent;
        // this.processLoad = webui.@THEME@.widget.accordionTab.loadContent.processEvent;

        this.setProps();

    },

    refresh: webui.@THEME@.widget.accordionTab.refresh.processEvent,

    processAction: webui.@THEME@.widget.accordionTab.tabAction.processEvent,

    processLoad: webui.@THEME@.widget.accordionTab.loadContent.processEvent,

    /**
     * This function is used to get widget properties. Please see
     * setProps() for a list of supported properties.
     */
    getProps: function() {
        var props = {};
 
        // Set properties.
        if (this.selected) { props.selected = this.selected; }
        if (this.label) { props.label = this.label; }
        if (this.tabContent) { props.tabContent = this.tabContent; }
        if (this.visible != null) { props.visible = this.visible; }
        if (this.actions != null) { props.actions = this.actions; }
        if (this.contentClass != null) { props.contentClass = this.contentClass; }
        if (this.labelClass != null) { props.labelClass = this.labelClass; }
        if (this.labelStyle != null) { props.labelStyle = this.labelStyle; }
        if (this.contentStyle != null) { props.contentStyle = this.contentStyle; }
        if (this.contentHeight != null) { props.contentHeight = this.contentHeight; }
        if (this.id) { props.id = this.id; }
        if (this.type) { props.type = this.type; }

        // Add DOM node properties.
        Object.extend(props, this.getCommonProps());
        Object.extend(props, this.getCoreProps());
        Object.extend(props, this.getJavaScriptProps());
        
        return props;
    },

    /**
     * This function is used to set widget properties with the
     * following Object literals.
     *
     * <ul>
     *  <li>contentHeight</li>
     *  <li>labelClass</li>
     *  <li>labelStyle</li>
     *  <li>contentClass</li>
     *  <li>contentStyle</li>
     *  <li>label</li>
     *  <li>tabContent</li>
     *  <li>visible</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */
    setProps: function(props) {

        if (props != null) {
            if (props.contents) {
                this.contents = null;
            }
            this.extend(this, props);
        } else {
            props = this.getProps(); // Widget is being initialized.
        }

        if (props.contentClass) {
            this.domNode.containerNode.className = props.contentClass;
        } else if (props.contentHeight) {
            this.containerNode.style.height = props.contentHeight;
        }

        if (props.labelClass) {
            this.domNode.labelNode.className = props.labelClass;
        }

        if (props.label) {
            this.setLabel(props.label);
        }

        if (props.tabContent) {
            this.setTabContent(props.tabContent);
        } // else {
            // in here we'd need to make a callback to get the contents
            // an onload event of available would be conntected here
            // if no onload event is available, we would make a call via
            // dynamic faces to get this information
            // this.setTabContent("Loading...");
        // }

        dojo.event.connect(this.labelNode, "onclick", this, "onLabelClick");
        dojo.event.connect(this.labelNode, "onmouseover", this, "onLabelMouseOver");
        dojo.event.connect(this.turnerContainer, "onmouseover", this, "onLabelMouseOver");
        dojo.event.connect(this.turnerContainer, "onclick", this, "onLabelClick");
        dojo.event.connect(this.menuContainer, "onmouseover", this, "onLabelMouseOver");
        dojo.event.connect(this.menuContainer, "onclick", this, "onMenuClick");
        dojo.event.connect(this.labelNode, "onmouseout", this, "onLabelMouseOut");
        dojo.event.connect(this.turnerContainer, "onmouseout", this, "onLabelMouseOut");
        dojo.event.connect(this.menuContainer, "onmouseout", this, "onLabelMouseOut");

        // Set DOM node properties.
        this.setCoreProps(this.domNode, props);
        this.setCommonProps(this.domNode, props);
        this.setJavaScriptProps(this.domNode, props);

    },

    /**
     * Set the label associated with the accordion tab.
     *
     *  @param label Label property associated with the tab.
     */
    setLabel: function (label) {
        
        if (label) {
            var labelHref = dojo.widget.byId(this.titleHref.id); 
            if (labelHref) {
                    labelHref.setProps(label);
            } else {
                this.titleHref.innerHTML = "";
                this.addFragment(this.titleHref, label, "first");
            }
        }
    },

    /**
     * Set the contents of the accordion tab.
     *
     *  @param content Contents associated with the tab.
     */
    setTabContent: function(content) {
        // dojo.debug("Props type: " + typeof content);
        if (content) {
	    this.containerNode.innerHtml = "";
	    for (var i = 0; i < content.length; i++) {

                // remove any old crufts
                var contentWidget = dojo.widget.byId(content[i].id);
                this.addFragment(this.containerNode, 
		        content[i], "last");
            }
        }
    },

    // this feature will be augmented on the coming days. The API may change
    // somewhat.
    onMenuClick: function(e) {
        // dojo.debug("menu clicked...");
        dojo.event.browser.stopEvent(e);
    },

    getLabelHeight: function () {
        return dojo.html.getMarginBox(this.labelNode).height;
    },

    onLabelClick: function () {
        this.parent.selectChild(this);
    },
    
    onLabelMouseOver: function() {
        
        if (this.selected) {
            this.turnerContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionDownTurner;
        } else {
            this.turnerContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionRightTurner;
        }
    },

    onLabelMouseOut: function() {
        if (this.selected) {
            this.labelNode.className = 
                webui.@THEME@.widget.props.accordionTab.accordionTabExpanded;
            this.turnerContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionDownTurner;
            return false;
        }

        this.labelNode.className = 
            webui.@THEME@.widget.props.accordionTab.accordionTabCollapsed;
        this.turnerContainer.className = 
            webui.@THEME@.widget.props.accordionTab.accordionRightTurner;
    },

    setSelected: function (isSelected) {
        // dojo.debug("Tab selected...");
        if (this.selected) {
            this.selected = false;
        } else {
            this.selected = isSelected;
        }
            
        if (this.selected) {
            this.labelNode.className = 
                webui.@THEME@.widget.props.accordionTab.accordionTabExpanded;
            this.turnerContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionDownTurner;
            this.containerNode.style.display = "block";
            
            // if the tab does not have content and "loadOnSelect" is set
            // to true go ahead and refresh the widget. 
            if (!this.content) {
                if (this.parent.loadOnSelect) {
                    this.processLoad();
                }
            }
        } else {
            this.labelNode.className = 
                webui.@THEME@.widget.props.accordionTab.accordionTabCollapsed;
            this.turnerContainer.className = 
                webui.@THEME@.widget.props.accordionTab.accordionRightTurner;
            this.containerNode.style.display = "none";
            
        }
    }
});

//-->
