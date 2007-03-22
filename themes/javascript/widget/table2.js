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

dojo.provide("webui.@THEME@.widget.table2");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.table2.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.table2 = function() {
    this.widgetType = "table2";
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
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
        this.domNode.setProps = webui.@THEME@.widget.table2.setProps;

        // Set private functions (private functions/props prefixed with "_").
        // TBD...

        // Set properties.
        this.domNode.setProps({
            actions: this.actions,
            filterText: this.filterText,
            id: this.id,
            rowGroups: this.rowGroups,
            templatePath: this.templatePath,
            title: this.title,
            width: this.width
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
        return false;
    }

// To do: Remove _props variable -- see label as an example.

    // Save properties for later updates.
    if (this._props) {
        Object.extend(this._props, props); // Override existing values, if any.
    } else {
        this._props = props;
    }

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this, props);
    webui.@THEME@.widget.common.setCommonProps(this, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this, props);

    // Set container width.
    if (props.width) {
        this.style.width = this.width;
    }

    // Set widget properties.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Add title.
    if (props.title) {
        webui.@THEME@.widget.common.addFragment(widget.titleContainer, props.title);
        webui.@THEME@.common.setVisibleElement(widget.titleContainer, true);
    }

    // Add actions.
    if (props.actions) {
        webui.@THEME@.widget.common.addFragment(widget.actionsContainer, props.actions);
        webui.@THEME@.common.setVisibleElement(widget.actionsContainer, true);
    }

    // Add row groups.
    if (props.rowGroups) {
        widget.rowGroupsContainer.innerHTML = null; // Clear contents.
        for (var i = 0; i < props.rowGroups.length; i++) {
            var rowGroupsClone = widget.rowGroupsContainer;

            // Clone nodes.
            if (i + 1 < props.rowGroups.length) {
                rowGroupsClone = widget.rowGroupsContainer.cloneNode(true);
                widget.marginContainer.insertBefore(rowGroupsClone, widget.rowGroupsContainer);
            }
            webui.@THEME@.widget.common.addFragment(rowGroupsClone, props.rowGroups[i], "last");
        }
    }
    return true;
}

dojo.inherits(webui.@THEME@.widget.table2, dojo.widget.HtmlWidget);

//-->
