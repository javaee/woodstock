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

dojo.provide("webui.@THEME@.widget.imageHyperlink");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");
dojo.require("webui.@THEME@.widget.hyperlink");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.imageHyperlink = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

webui.@THEME@.widget.imageHyperlink.addContents = function(props) {
    if (props.contents == null) {
        return false;
    }

    // Remove child nodes.
    this.removeChildNodes(this.leftContentsContainer);
    this.removeChildNodes(this.rightContentsContainer);

    // Add contents.
    for(i = 0; i <props.contents.length; i++) {
        this.addFragment(this.leftContentsContainer, props.contents[i], "last");
        this.addFragment(this.rightContentsContainer, props.contents[i], "last");
    }
    return true;
}

/**
 * This function is used to fill a template with widget properties.
 *
 * Note: Anything to be set only once should be added here; otherwise, the
 * setProps() function should be used to set properties.
 */
webui.@THEME@.widget.imageHyperlink.fillInTemplate = function() {
    // Set ids.
    if (this.id) {
        this.enabledImageContainer.id = this.id + "_enabled";
        this.disabledImageContainer.id = this.id + "_disabled";
        this.leftContentsContainer.id = this.id + "_leftContents";
        this.rightContentsContainer.id = this.id + "_rightContents";
    }

    // Set public functions.
    this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
    this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
    this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }

    // Create callback function for onClick event.
    dojo.event.connect(this.domNode, "onclick",
        webui.@THEME@.widget.hyperlink.createOnClickCallback(this.id, 
            this.formId, this.params));

    // If the href attribute does not exist, set "#" as the default value of the
    // DOM node.
    this.domNode.href = "#";

    // Set properties.
    return this.setProps();
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.hyperlink.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.imageHyperlink.getProps = function() {
    var props = webui.@THEME@.widget.imageHyperlink.superclass.getProps.call(this);

    // Set properties.
    if (this.enabledImage) { props.enabledImage = this.enabledImage; }
    if (this.disabledImage) { props.disabledImage = this.disabledImage; }
    if (this.imagePosition) { props.imagePosition = this.imagePosition; }

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.imageHyperlink.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_imageHyperlink_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_imageHyperlink_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.imageHyperlink");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.imageHyperlink.refresh.beginEventTopic, {
                id: this.id,
                execute: execute
            });
        return true;
    }
}

/**
 * This function is used to update widget properties with the
 * following Object literals.
 *
 * <ul>
 * <li>className</li>
 * <li>contents</li>
 * <li>dir</li>
 * <li>disabled</li>
 * <li>disabledImage</li>
 * <li>enabledImage</li>
 * <li>href</li>
 * <li>hrefLang</li>
 * <li>id</li>
 * <li>lang</li>
 * <li>onFocus</li>
 * <li>onBlur</li>
 * <li>onClick</li>
 * <li>onDblClick</li>
 * <li>onKeyDown</li>
 * <li>onKeyPress</li>
 * <li>onKeyUp</li>
 * <li>onMouseDown</li>
 * <li>onMouseOut</li>
 * <li>onMouseOver</li>
 * <li>onMouseUp</li>
 * <li>onMouseMove</li>
 * <li>style</li>
 * <li>imagePosition</li>
 * <li>tabIndex</li>
 * <li>title</li>
 * <li>visible</li>
 * </ul>
 */
webui.@THEME@.widget.imageHyperlink.setProps = function(props) {
    // Call super class function.
    var props = webui.@THEME@.widget.imageHyperlink.superclass.setProps.call(
        this, props);

    // Show en/disabled images.
    if (props.disabled != null) {
        var disabled = new Boolean(props.disabled).valueOf();

        // We need to hide/show images only when the disabed image is specified.
        if (this.disabledImage) { 
            webui.@THEME@.common.setVisibleElement(this.enabledImageContainer, !disabled);
            webui.@THEME@.common.setVisibleElement(this.disabledImageContainer, disabled);
        }
    }

    // Add enabled image.
    if (props.enabledImage) {
        this.addFragment(this.enabledImageContainer, props.enabledImage);
    }

    // Add disabled image.
    if (props.disabledImage) {
        this.addFragment(this.disabledImageContainer, props.disabledImage);
    }

    // Add contents.
    this.addContents(props);
    if (props.imagePosition) {
        var left = (props.imagePosition == "left") 
        webui.@THEME@.common.setVisibleElement(this.leftContentsContainer, !left);
        webui.@THEME@.common.setVisibleElement(this.rightContentsContainer, left);    
    }
    return props; // Return props for subclasses.
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.imageHyperlink, webui.@THEME@.widget.hyperlink);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.imageHyperlink, {
    // Set private functions.
    addContents: webui.@THEME@.widget.imageHyperlink.addContents,
    fillInTemplate: webui.@THEME@.widget.imageHyperlink.fillInTemplate,
    getProps: webui.@THEME@.widget.imageHyperlink.getProps, 
    refresh: webui.@THEME@.widget.imageHyperlink.refresh.processEvent,
    setProps: webui.@THEME@.widget.imageHyperlink.setProps,

    // Set defaults.
    widgetType: "imageHyperlink"
});

//-->
