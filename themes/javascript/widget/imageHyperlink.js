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
 * This closure is used to process widget events.
 */
webui.@THEME@.widget.imageHyperlink.event = {
    /**
     * This closure is used to process refresh events.
     */
    refresh: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_imageHyperlink_event_refresh_begin",
        endTopic: "webui_@THEME@_widget_imageHyperlink_event_refresh_end"
    },

    /**
     * This closure is used to process state change events.
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME@_widget_imageHyperlink_event_state_begin",
        endTopic: "webui_@THEME@_widget_imageHyperlink_event_state_end"
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
webui.@THEME@.widget.imageHyperlink.fillInTemplate = function(props, frag) {
    webui.@THEME@.widget.imageHyperlink.superclass.fillInTemplate.call(this, props, frag);

    // Set ids.
    if (this.id) {
        this.enabledImageContainer.id = this.id + "_enabled";
        this.disabledImageContainer.id = this.id + "_disabled";
        this.leftContentsContainer.id = this.id + "_leftContents";
        this.rightContentsContainer.id = this.id + "_rightContents";
    }
    return true;
}

/**
 * This function is used to get widget properties. Please see the 
 * _setProps() function for a list of supported properties.
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
 * This function is used to set widget properties with the following 
 * Object literals.
 *
 * <ul>
 *  <li>className</li>
 *  <li>contents</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>disabledImage</li>
 *  <li>enabledImage</li>
 *  <li>href</li>
 *  <li>hrefLang</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>onFocus</li>
 *  <li>onBlur</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>style</li>
 *  <li>imagePosition</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: This is considered a private API, do not use. This function should only
 * be invoked through postInitialize() and setProps(). Further, the widget shall
 * be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.imageHyperlink._setProps = function(props) {
    if (props == null) {
        return false;
    }

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

    // Set image position.
    if (props.imagePosition) {
        var left = (props.imagePosition == "left") 
        webui.@THEME@.common.setVisibleElement(this.leftContentsContainer, !left);
        webui.@THEME@.common.setVisibleElement(this.rightContentsContainer, left);    
    }

    // Add contents.
    this.addContents(props);

    // Set remaining properties.
    return webui.@THEME@.widget.imageHyperlink.superclass._setProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.imageHyperlink, webui.@THEME@.widget.hyperlink);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.imageHyperlink, {
    // Set private functions.
    addContents: webui.@THEME@.widget.imageHyperlink.addContents,
    fillInTemplate: webui.@THEME@.widget.imageHyperlink.fillInTemplate,
    getProps: webui.@THEME@.widget.imageHyperlink.getProps,
    _setProps: webui.@THEME@.widget.imageHyperlink._setProps,

    // Set defaults.
    event: webui.@THEME@.widget.imageHyperlink.event,
    widgetType: "imageHyperlink"
});
