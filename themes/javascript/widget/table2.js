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

dojo.provide("webui.@THEME@.widget.table2");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.table2 = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.table2.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_table2_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_table2_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_table2_event_state_begin",
        endTopic: "webui_@THEME@_widget_table2_event_state_end"
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
webui.@THEME@.widget.table2.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.table2.superclass.fillInTemplate.call(this, props, frag);

    // Set ids.
    if (this.id) {
        this.actionsContainer.id = this.id + "_actionsContainer";
        this.filterPanelContainer.id = this.id + "_filterPanelContainer";
        this.marginContainer.id = this.id + "_marginContainer";
        this.preferencesPanelContainer.id = this.id + "_preferencesPanelContainer";
        this.sortPanelContainer.id = this.id + "_sortPanelContainer";
        this.rowGroupsContainer.id = this.id + "_rowGroupsContainer";
        this.titleContainer.id = this.id + "_titleContainer";
        this.tableFooterContainer.id = this.id + "_tableFooterContainer";
    }
    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
 */
webui.@THEME@.widget.table2.getProps = function() {
    var props = webui.@THEME@.widget.table2.superclass.getProps.call(this);

    // Set properties.
    if (this.actions) { props.actions = this.actions; }
    if (this.filterText) { props.filterText = this.filterText; }
    if (this.rowGroups) { props.rowGroups = this.rowGroups; }
    if (this.width) { props.width = this.width; }

    return props;
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
webui.@THEME@.widget.table2.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Replace actions -- do not extend.
    if (props.actions) {
        this.actions = null;
    }

    // Replace rows -- do not extend.
    if (props.rowGroups) {
        this.rowGroups = null;
    }

    // Extend widget object for later updates.
    return webui.@THEME@.widget.table2.superclass.setProps.call(this, props, notify);
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>actions</li>
 *  <li>filterText</li>
 *  <li>id</li>
 *  <li>rowGroups</li>
 *  <li>title</li>
 *  <li>width</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set container width.
    if (props.width) {
        this.domNode.style.width = this.width;
    }

    // Add title.
    if (props.title) {
        this.addFragment(this.titleContainer, props.title);
        webui.@THEME@.common.setVisibleElement(this.titleContainer, true);
    }

    // Add actions.
    if (props.actions) {
        this.addFragment(this.actionsContainer, props.actions);
        webui.@THEME@.common.setVisibleElement(this.actionsContainer, true);
    }

    // Add row groups.
    if (props.rowGroups) {
        // Remove child nodes.
        this.removeChildNodes(this.rowGroupsContainer);

        // Each group must be added to separate containers for padding.
        for (var i = 0; i < props.rowGroups.length; i++) {
            // Clone node.
            var rowGroupsNodeClone = this.rowGroupsNode.cloneNode(true);
            this.rowGroupsContainer.appendChild(rowGroupsNodeClone);
            
            // Add row group.
            this.addFragment(rowGroupsNodeClone, props.rowGroups[i], "last");
        }
    }

    // Set more properties..
    this.setCommonProps(this.domNode, props);
    this.setEventProps(this.domNode, props);

    // Set remaining properties.
    return webui.@THEME@.widget.table2.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.table2, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.table2, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.table2.fillInTemplate,
    getProps: webui.@THEME@.widget.table2.getProps,
    setProps: webui.@THEME@.widget.table2.setProps,
    _setProps: webui.@THEME@.widget.table2._setProps,

    // Set defaults.
    event: webui.@THEME@.widget.table2.event,
    widgetType: "table2"
});
