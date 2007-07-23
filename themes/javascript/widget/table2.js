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
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.table2.fillInTemplate = function() {
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

    // Set public functions.
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }

    // Initialize template.
    return this.setProps(this.getProps());
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.table2.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.table2.getProps = function() {
    var props = {};

    // Set properties.
    if (this.actions) { props.actions = this.actions; }
    if (this.filterText) { props.filterText = this.filterText; }
    if (this.rowGroups) { props.rowGroups = this.rowGroups; }
    if (this.width) { props.width = this.width; }

    // Add DOM node properties.
    Object.extend(props, this.getCommonProps());
    Object.extend(props, this.getCoreProps());
    Object.extend(props, this.getJavaScriptProps());

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.table2.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_table2_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_table2_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.table2");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.table.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.table.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
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
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.table2.setProps = function(props) {
    if (props == null) {
        return null;
    }

    // Save properties for later updates.
    if (this.isInitialized() == true) {
        this.extend(this, props);
    }

    // Set DOM node properties.
    this.setCoreProps(this.domNode, props);
    this.setCommonProps(this.domNode, props);
    this.setJavaScriptProps(this.domNode, props);

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
    return props; // Return props for subclasses.
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.table2, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.table2, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.table2.fillInTemplate,
    setProps: webui.@THEME@.widget.table2.setProps,
    refresh: webui.@THEME@.widget.table2.refresh.processEvent,
    getProps: webui.@THEME@.widget.table2.getProps,

    // Set defaults.
    templatePath: webui.@THEME@.theme.getTemplatePath("table2"),
    templateString: webui.@THEME@.theme.getTemplateString("table2"),
    widgetType: "table2"
});
