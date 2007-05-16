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

dojo.provide("webui.@THEME@.widget.alert");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.alert.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.alert = function() {
    // Set defaults.
    this.widgetType = "alert";

    // Register widget.
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.bottomLeftContainer.id = this.id + "_bottomLeftContainer";
            this.bottomMiddleContainer.id = this.id + "_bottomMiddleContainer";
            this.bottomRightContainer.id = this.id + "_bottomRightContainer";
            this.detailContainer.id = this.id + "_detailContainer";
            this.imageContainer.id = this.id + "_imageContainer";
            this.leftMiddleContainer.id = this.id + "_leftMiddleContainer";
            this.rightMiddleContainer.id = this.id + "_rightMiddleContainer";
            this.summaryContainer.id = this.id + "_summaryContainer";
            this.topLeftContainer.id = this.id + "_topLeftContainer";
            this.topMiddleContainer.id = this.id + "_topMiddleContainer";
            this.topRightContainer.id = this.id + "_topRightContainer";
            this.detailContainerLink.id = this.id + "_detailContainerLink";
        }

        // Set public functions.
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        
        // Set private functions.
        this.setProps = webui.@THEME@.widget.alert.setProps;
        this.getProps = webui.@THEME@.widget.alert.getProps;
        this.refresh = webui.@THEME@.widget.alert.refresh.processEvent;
        this.validate = webui.@THEME@.widget.alert.validation.processEvent;

        // Set properties.
        return this.setProps();
    }
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.alert.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.alert.getProps = function() {
    var props = {};

    // Set properties.
    if (this.detail != null) { props.detail = this.detail; }
    if (this.indicators != null) { props.indicators = this.indicators; }
    if (this.summary != null) { props.summary = this.summary; }
    if (this.type != null) { props.type = this.type; }
    if (this.moreInfo != null) { props.moreInfo = this.moreInfo; }
    if (this.spacerImage != null) { props.spacerImage = this.spacerImage; }
        
    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    
    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.alert.refresh = { 
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_alert_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_alert_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish event.
        webui.@THEME@.widget.alert.refresh.publishBeginEvent({
            id: this.id,
            execute: execute
        });
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishBeginEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.alert.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) { 
        dojo.event.topic.publish(webui.@THEME@.widget.alert.refresh.endEventTopic, props);
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>dir</li>
 *  <li>lang</li>
 *  <li>detail</li>
 *  <li>spacerImage</li>
 *  <li>indicators</li>
 *  <li>id</li>
 *  <li>summary</li>
 *  <li>type</li>
 *  <li>moreInfo</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.alert.setProps = function(props) {
    // After widget has been initialized, save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);    
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set attributes.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);

    // Do not call setCommonProps as that will result in assigning tabIndex to
    // outermost domNode. Assign a11y properties to alert images.
    if (props.dir) { this.domNode.dir = props.dir; }
    if (props.lang) { this.domNode.lang = props.lang; }    
    
    // Set summary.
    if (props.summary) {
        webui.@THEME@.widget.common.addFragment(this.summaryContainer, props.summary);
    }

    // Set detail.
    if (props.detail) {
        webui.@THEME@.widget.common.addFragment(this.detailContainer, props.detail);
    }

    // Set moreInfo.
    if (props.moreInfo) {
        webui.@THEME@.widget.common.addFragment(this.detailContainerLink, props.moreInfo);
    }

    // Set spacer image.
    if (props.spacerImage) {
        var containers = [
            this.bottomLeftContainer,
            this.bottomMiddleContainer,
            this.bottomRightContainer,
            this.leftMiddleContainer,
            this.rightMiddleContainer,
            this.topLeftContainer,
            this.topMiddleContainer,
            this.topRightContainer];

        // Avoid widget ID collisions.
        for (var i = 0; i < containers.length; i++) {
            if (typeof props != 'string') {
                props.spacerImage.id = this.id + "_spacerImage" + i;
            }
            // Replace container with image.
            if (!dojo.widget.byId(props.spacerImage.id)) {
                webui.@THEME@.widget.common.addFragment(containers[i], props.spacerImage);
            }
        }
    }

    // Set indicator properties.
    if (props.indicators || props.type != null && this.indicators) {
        // Iterate over each indicator.
        for (var i = 0; i < this.indicators.length; i++) {
            // Ensure property exists so we can call setProps just once.
            var indicator = this.indicators[i]; // get current indicator.
           
            if (indicator == null) {
                indicator = {};
            }
           
            // Show indicator.
            indicator.image.visible = (this.type != null && this.type == indicator.type) ? true : false;
            indicator.image.tabIndex = this.tabIndex;
           
            // Update widget/add fragment.
            var indicatorWidget = dojo.widget.byId(indicator.image.id);
            if (indicatorWidget) {
                indicatorWidget.setProps(indicator.image);
            } else {
                webui.@THEME@.widget.common.addFragment(this.imageContainer, indicator.image, "last");
            }
        }
    }
    return true;
}

/**
 * This closure is used to process validation events.
 */
webui.@THEME@.widget.alert.validation = {
    /**
     * This function is used to process validation events with the following
     * Object literals.
     *
     * <ul>
     *  <li>detail</li>
     *  <li>summary</li>
     *  <li>valid</li>
     * </ul>
     *
     * @param props Key-Value pairs of properties.
     */
    processEvent: function(props) {
        if (props == null) {
            return false;
        }
        return this.setProps({
            summary: props.summary,
            detail: props.detail,
            type: "error",
            visible: !props.valid
        });
    }
}

dojo.inherits(webui.@THEME@.widget.alert, dojo.widget.HtmlWidget);

//-->
