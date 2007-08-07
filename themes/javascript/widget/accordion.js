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

dojo.provide("webui.@THEME@.widget.accordion");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.accordion = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * Helper function to add accordion header controls
 */
webui.@THEME@.widget.accordion.addControls = function(props) {       
    // Add expand and collapse icons only if multiple select is set to
    // true and the icons have been supplied.
    if (props.toggleControls && props.multipleSelect) {
        // Set expand all image properties.
        if (props.expandAllImage) {
            // Set properties.
            props.expandAllImage.id = this.expandAllImage.id; // Required for updateFragment().

            // Update/add fragment.
            this.widget.updateFragment(this.expandAllImgContainer, props.expandAllImage);
        }

        // Set collapse all image properties.
        if (props.collapseAllImage) {
            // Set properties.
            props.collapseAllImage.id = this.collapseAllImage.id; // Required for updateFragment().

            // Update/add fragment.
            this.widget.updateFragment(this.collapseAllImgContainer, props.collapseAllImage);
        }
    }

    // Set refresh image properties.
    if (props.refreshImage) {
        // Set properties.
        props.refreshImage.id = this.refreshImage.id; // Required for updateFragment().

        // Update/add fragment.
        this.widget.updateFragment(this.refreshImgContainer, props.refreshImage);
    }
    return true;
}

/**
 * Close all open accordions and leave the others as is.
 */
webui.@THEME@.widget.accordion.collapseAllTabs = function() {
    for (var i=0; i<this.children.length; i++) {
        var child = this.children[i];
        if (child.widgetType == "accordionTab") {
            if (child.selected) {
                child.setSelected(false);
            }
        }
    }
    return true;
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.accordion.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_accordion_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_accordion_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_accordion_event_state_begin",
        endTopic: "webui_@THEME@_widget_accordion_event_state_end"
    }
}

/**
 * Open all closed tabs and leave the others as is.
 */
webui.@THEME@.widget.accordion.expandAllTabs = function() {
    for (var i=0; i<this.children.length; i++) {
        var child = this.children[i];
        if (child.widgetType == "accordionTab") {
            if (!child.selected) {
                child.setSelected(true);
            }
        }
    }
    return true;
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
webui.@THEME@.widget.accordion.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.accordion.superclass.fillInTemplate.call(this, props, frag);

    with (this.domNode.style) {
        if (position != "absolute") {
            position = "relative";
        }
        overflow = "hidden";
    }

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
    this.collapseAllContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrCloseApp;
    this.dividerNodeContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrDivider;
    this.expandAllContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrOpenAll;
    this.refreshNodeContainer.className = webui.@THEME@.widget.props.accordionTab.accordionHdrRefresh;

    // Set events.
    var id = this.id;
    dojo.event.connect(this.collapseAllContainer, "onclick", this, "collapseAllTabs");
    dojo.event.connect(this.expandAllContainer, "onclick", this, "expandAllTabs");
    dojo.event.connect(this.refreshNodeContainer, "onclick", function() {
        var widget = dojo.widget.byId(id);
        widget.refresh(id);
    });

    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.accordion.getProps = function() {
    var props = webui.@THEME@.widget.accordion.superclass.getProps.call(this);

    // Set properties.
    if (this.loadOnSelect) { props.loadOnSelect = this.loadOnSelect; }
    if (this.toggleControls) { props.toggleControls = this.toggleControls; }
    if (this.multipleSelect) { props.multipleSelect = this.multipleSelect; }
    if (this.expandAllImage != null) { props.expandAllImage = this.expandAllImage; }
    if (this.collapseAllImage != null) { props.collapseAllImage = this.collapseAllImage; }
    if (this.refreshImage != null) { props.refreshImage = this.refreshImage; }
    if (this.style != null) { props.style = this.style; }
    if (this.styleClass != null) { props.styleClass = this.styleClass; }
    if (this.tabs != null) { props.tabs = this.tabs; }
    if (this.id) { props.id = this.id; }
    if (this.type) { props.type = this.type; }

    return props;
}

/**
 * This function selects the child tab when the user clicks
 * on its label. The actual behavior of the accordion depends on
 * whether multipleSelect is enabled or not.
 */
webui.@THEME@.widget.accordion.selectChild = function(widget) {
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
webui.@THEME@.widget.accordion.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace tabs -- do not extend.
    if (props.tabs) {
        this.tabs = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.accordion.superclass.setProps.call(this, props, notify);
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>id</li>
 *  <li>loadOnSelect</li>
 *  <li>toggleControls</li>
 *  <li>multipleSelect</li>
 *  <li>expandAllImage</li>
 *  <li>collapseAllImage</li>
 *  <li>refreshImage</li>
 *  <li>style</li>
 *  <li>styleClass</li>
 *  <li>tabs</li>
 *  <li>type</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.accordion._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // add control icons - refresh, expandall, collapseall.
    this.addControls(props); 

    // If we are coming here for
    // the first time there will be no children. The other case is when 
    // the accordion is being rerendered because of a refresh in which 
    // we want to use the latest set of children. addFragment is supposed
    // to do that.
    if (props.tabs) {
        // To Do: Before adding new children, previously created nodes should be
        // removed via the removeChildNodes(domNode) function -- see label. 
        //
        // Adding children to a specific container (other than this.domNode) 
        // would help isolate what needs to be removed. The following code works
        // because widgets of the same id are destroyed before creating new objects.
        for (var i=0; i < props.tabs.length; i++) {
            this.widget.addFragment(this.domNode, props.tabs[i], "last");
        }
    }

    // Set more properties..
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.accordion.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.accordion, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.accordion, {
    // Set private functions.
    addControls: webui.@THEME@.widget.accordion.addControls,
    collapseAllTabs: webui.@THEME@.widget.accordion.collapseAllTabs,
    expandAllTabs: webui.@THEME@.widget.accordion.expandAllTabs,
    fillInTemplate: webui.@THEME@.widget.accordion.fillInTemplate,
    getProps: webui.@THEME@.widget.accordion.getProps,
    selectChild: webui.@THEME@.widget.accordion.selectChild,
    setProps: webui.@THEME@.widget.accordion.setProps,
    _setProps: webui.@THEME@.widget.accordion._setProps,

    // Set defaults.
    duration: 250,
    event: webui.@THEME@.widget.accordion.event,
    isContainer: true,
    loadOnSelect: false,
    multipleSelect: false,
    widgetType: "accordion"
});
