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

dojo.provide("webui.@THEME@.widget.accordion");

dojo.require("dojo.widget.*");
dojo.require("dojo.html.*");
dojo.require("dojo.lfx.html");
dojo.require("dojo.html.selection");
dojo.require("dojo.widget.html.layout");
dojo.require("dojo.widget.PageContainer");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

webui.@THEME@.widget.accordion = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

dojo.inherits(webui.@THEME@.widget.accordion, webui.@THEME@.widget.widgetBase);

/**
 * This closure is used to process accordion refresh events. If ajaxify
 * is set to true the default jafx based handler will kick in to 
 * asynchronously refresh the accordion. Else the applicatiobn developer
 * has to supply custom javascript functionality that listenes to 
 * webui.@THEME@.widget.accordion.refresh.beginEventTopic and initiates
 * a refresh. At the end of the refresh process process the 
 * webui.@THEME@.widget.accordion.refresh.beginEventTopic should be
 * published.
 */
webui.@THEME@.widget.accordion.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_accordion_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_accordion_refresh_end",

    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.accordion");

        // Publish event.
        dojo.event.topic.publish(
            webui.@THEME@.widget.accordion.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.accordion.refresh.endEventTopic
        });
        return true;
    }

}

/**
 * Accordion functionality described by the properties below. By default
 * only a single tab can be selected. None of the tabs are selected by default
 * either.
 */

dojo.lang.extend(webui.@THEME@.widget.accordion, {

    disabled: false,
    widgetType: "accordion",
    multipleSelect: false,
    isContainer: true,
    duration: 250,
    
    /**
     * This function is used to generate a template based widget.
     */
    fillInTemplate: function() {
        // dojo.debug("inside fillinTemplate..");
        
        with (this.domNode.style) {
            if (position != "absolute") {
                position = "relative";
            }
            overflow = "hidden";
        }
        
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

        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        
        this.setProps();
        return true;
    },

    refresh: webui.@THEME@.widget.accordion.refresh.processEvent,
    
    /**
     * This function is used to get widget properties. Please see
     * setProps() for a list of supported properties.
     */
    getProps: function() {
        var props = {};
 
        // Set properties.
        if (this.loadOnSelect) { props.loadOnSelect = this.loadOnSelect; }
        if (this.toggleControls) { props.toggleControls = this.toggleControls; }
        if (this.multipleSelect) { props.multipleSelect = this.multipleSelect; }
        if (this.expandAllImage != null) { props.expandAllImage = this.expandAllImage; }
        if (this.collapseAllImage != null) { props.collapseAllImage = this.collapseAllImage; }
        if (this.refreshImage != null) { props.refreshImage = this.refreshImage; }
        if (this.style != null) { props.style = this.style; }
        if (this.styleClass != null) { props.styleClass = this.styleClass; }
        if (this.accordionTabs != null) { props.accordionTabs = this.accordionTabs; }
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
     *  <li>id</li>
     *  <li>templatePath</li>
     *  <li>loadOnSelect</li>
     *  <li>expandCollapse</li>
     *  <li>multipleSelect</li>
     *  <li>expandAllImage</li>
     *  <li>collapseAllImage</li>
     *  <li>refreshImage</li>
     *  <li>style</li>
     *  <li>styleClass</li>
     *  <li>accordionTabs</li>
     *  <li>type</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */

    setProps: function(props) {

        if (props != null) {
            if (this.accordionTabs) {
                this.accordionTabs = null;
            }
            this.extend(this, props);
        } else {
            props = this.getProps(); // Widget is being initialized.
        }

        
        // Set DOM node properties.
        this.setCoreProps(this.domNode, props);
        this.setCommonProps(this.domNode, props);
        this.setJavaScriptProps(this.domNode, props);

        // add control icons - refresh, expandall, collapseall
        this.addControls(props); 
        
        // Remove each accordion tab if one exists. If we are coming here for
        // the first time there will be no children. The other case is when 
        // the accordion is being rerendered because of a refresh in which 
        // we want to use the latest set of children.

        for (var i=0; i < props.accordionTabs.length; i++) {
            this.removeChildNodes(props.accordionTabs[i].id);
        }

        // Add the tabs as children of the container...
        for (var i=0; i < props.accordionTabs.length; i++) {
            this.addFragment(this.domNode, props.accordionTabs[i], "last");
        }
    },

/**
 * Helper function to add accordion header controls
 */

    addControls: function(props) {

        // add expand and collapse icons only if multiple select is set to
        // true and the icons have been supplied.

       if (props.toggleControls && props.multipleSelect) {
            this.expandAllContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrOpenAll;
            var expandAllImgWidget = dojo.widget.byId(this.expandAllImgContainer.id); 
            if (expandAllImgWidget) {
                expandAllImgWidget.setProps(props.expandAllImage);
            } else {
                this.addFragment(this.expandAllImgContainer,
                    props.expandAllImage); 
                dojo.event.connect(this.expandAllContainer, "onclick", this, "expandAllTabs");
                this.expandAllContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrOpenAll;
            }

            // add the collapseAll icon
            this.collapseAllContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrCloseApp;
            var collapseAllImgWidget = dojo.widget.byId(this.collapseAllImgContainer.id); 
            if (collapseAllImgWidget) {
                collapseAllImgWidget.setProps(props.collapseAllImage);
            } else {
                this.addFragment(this.collapseAllImgContainer,
                    props.collapseAllImage);
                dojo.event.connect(this.collapseAllContainer, "onclick", this, "collapseAllTabs");
            }
            
            
            // add the divider 
            this.dividerNodeContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrDivider; 
        }

        // add refresh icon only if it has been supplied.
        if (props.refreshImage) {
            this.refreshNodeContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrRefresh;
            var refreshImgWidget = dojo.widget.byId(this.refreshImage.id); 
            if (refreshImgWidget) {
                refreshImgWidget.setProps(props.refreshImage);
            } else {
                this.addFragment(this.refreshImgContainer,
                    props.refreshImage);
                var _this = this;
                dojo.event.connect(this.refreshNodeContainer, "onclick", function() {
                    _this.refresh("true");
                });
            }
              
        }
        return true;
    },

    // Close all open accordions and leave the others as is

    collapseAllTabs: function() {
        
        for (var i=0; i<this.children.length; i++) {
            var child = this.children[i];
            if (child.widgetType == "accordionTab") {
                if (child.selected) {
                    child.setSelected(false);
                }
            }
        }
        return true;
    },

    // Open all closed tabs and leave the others as is
    expandAllTabs: function() {
        for (var i=0; i<this.children.length; i++) {
            var child = this.children[i];
            if (child.widgetType == "accordionTab") {
                if (!child.selected) {
                    child.setSelected(true);
                }
            }
        }
        return true;
    },

    addChild: function(widget) {
        var child = this._addChild(widget);
        return child;
    },

    _addChild: function (widget) {
        
        this.addWidgetAsDirectChild(widget);
        this.registerChild(widget, this.children.length);
        return widget;
    },

    removeChildByWidget: function(widget) {
        webui.@THEME@.widget.accordion.superclass.removeChild.call(this, widget);
    },

    /*
     * This function removes the tab child whose index has been supplied.
     * If a valid index is not supplied an error is thrown
     */
    removeChildByIndex: function(index) {
        if (index < 0 || index > this.children.length) {
            throw new Error('cannot remove child, index out of range');
        } else {
            var childWidget = this.children[index];
            dojo.widget.accordion.superclass.removeChild.call(this, childWidget);
        }   
    },

    /*
     * This function selects the child tab when the user clicks
     * on its label. The actual behavior of the accordion depends on
     * whether multipleSelect is enabled or not.
     */
    selectChild: function(widget) {
        
        if (this.multipleSelect) {
            widget.setSelected(true);
        } else {
            for (var i=0; i<this.children.length; i++) {
                var child = this.children[i];
                if (child.widgetType == "accordionTab") {
                    child.setSelected(child.id == widget.id);
                }
            }
        }
    },

    /*
     * This function selects the child tab when called from another
     * script by supplying the index of the tab to be opened.
     */
    selectChildByIndex: function(index) {
      switch(index) {
         case 'number':
             if (index < 0 || index > this.children.length) {
                 throw new Error('cannot select child, index out of range');
             } else {
                 this.children[index].setSelected(true);
                 var y = 0;
                 var anims = [];
                 dojo.lang.forEach(this.children, function (child, idx) {
                   if (child.domNode.style.top != (y + "px")) {
                       anims.push(dojo.lfx.html.slideTo(child.domNode, {top:y, left:0}, this.duration));
                   }
                   y += child.selected ? dojo.html.getBorderBox(child.domNode).height : child.getLabelHeight();
                 }, this);
                 dojo.lfx.combine(anims).play();
             }
         default:
             throw new Error('index must be a number');
        }    
    }

});

//-->
