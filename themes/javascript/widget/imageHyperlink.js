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
dojo.require("webui.@THEME@.widget.anchor");
dojo.require("webui.@THEME@.widget.hyperlink");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.ImageHyperlink.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.imageHyperlink = function() {

    // Set defaults.
    this.widgetType = "imageHyperlink";    

    // Register widget.
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {

        // Set public functions. 
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }

        // Set private functions.
        this.setProps = webui.@THEME@.widget.imageHyperlink.setProps;
        this.setSuperProps = webui.@THEME@.widget.anchor.setAnchorProps;
        this.getSuperProps = webui.@THEME@.widget.anchor.getProps;
        this.refresh = webui.@THEME@.widget.imageHyperlink.refresh.processEvent;        
        this.submit = webui.@THEME@.widget.hyperlink.submit; 
        this.addContents = webui.@THEME@.widget.imageHyperlink.addContents;
        this.getProps = webui.@THEME@.widget.imageHyperlink.getProps;
        this.getDisabledClassName = webui.@THEME@.widget.hyperlink.getDisabledClassName;

        this.getClassName = webui.@THEME@.widget.hyperlink.getClassName;
        if (this.id) {
            //set ids.
            this.enabledImageContainer.id = this.id+"_enabled";
            this.disabledImageContainer.id = this.id+"_disabled";
            this.leftContentsContainer.id = this.id+"_leftContents";
            this.rightContentsContainer.id = this.id+"_rightContents";
        }
        // Create callback function for onClick event.
        dojo.event.connect(this.domNode, "onclick",
            webui.@THEME@.widget.hyperlink.createOnClickCallback(this.id, 
                this.formId, this.params));

       // If the href attribute does not exist, make "#" value as
       // the default value of the href attribute in the dom node.
        this.domNode.href = "#";

        // Set properties.
        return this.setProps();
    }
}

webui.@THEME@.widget.imageHyperlink.addContents = function(props) {
    if (props.contents == null) {
        return false;
    }
    webui.suntheme.widget.common.removeChildNodes(this.leftContentsContainer);  
    webui.suntheme.widget.common.removeChildNodes(this.rightContentsContainer);  
    // Add contents.
    for(i = 0; i <props.contents.length; i++) {
        webui.@THEME@.widget.common.addFragment(this.leftContentsContainer, props.contents[i],
            "last");
        webui.@THEME@.widget.common.addFragment(this.rightContentsContainer, props.contents[i],
            "last");
    }

    return true;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.hyperlink.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.imageHyperlink.getProps = function() {
    var props = {}; 
    // Set properties.
    props = this.getSuperProps();
    if (this.enabledImage) { props.enabledImage = this.enabledImage; }
    if (this.disabledImage) {props.disabledImage = this.disabledImage; }
    if (this.imagePosition) {props.imagePosition = this.imagePosition; }
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
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish event.
        webui.@THEME@.widget.imageHyperlink.refresh.publishBeginEvent({
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
        dojo.event.topic.publish(webui.@THEME@.widget.imageHyperlink.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.imageHyperlink.refresh.endEventTopic, props);
        return true;
    }
}

/**
 * This function is used to update widget properties with the
 * following Object literals. Not all properties are required.
 * This function first calls the setAnchorProps present in
 * anchor widget to set all the properties common to the anchor
 * widget.
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
webui.@THEME@.widget.imageHyperlink.setProps = function(props){
    // Save properties for later updates.
    if (props != null) {
        // Replace contents -- do not extend.
        if (props.contents) {
            this.contents = null;
        }
        webui.@THEME@.widget.common.extend(this, props);
    } else {
        props = this.getProps(); // Widget is being initialized.
    }
    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();

    this.setSuperProps(props);
    if (props.disabled != null) {
        var disabled = new Boolean(props.disabled).valueOf();
        // We need to swap images only when the disabed image is specified.
        if (this.disabledImage) { 
            webui.@THEME@.common.setVisibleElement(this.enabledImageContainer, !disabled);
            webui.@THEME@.common.setVisibleElement(this.disabledImageContainer, disabled);
        }

    }
    this.addContents(props);
    if (props.enabledImage) {
        webui.suntheme.widget.common.removeChildNodes(this.enabledImageContainer);  
        webui.@THEME@.widget.common.addFragment(this.enabledImageContainer,
           props.enabledImage, "last");
    }

    if (props.disabledImage) {
        webui.suntheme.widget.common.removeChildNodes(this.disabledImageContainer);  
        webui.@THEME@.widget.common.addFragment(this.disabledImageContainer,
           props.disabledImage, "last");
    }

    if (props.imagePosition) {
        var left = (props.imagePosition == "left") 
        webui.@THEME@.common.setVisibleElement(this.leftContentsContainer, !left);
        webui.@THEME@.common.setVisibleElement(this.rightContentsContainer, left);    
    }
}

dojo.inherits(webui.@THEME@.widget.imageHyperlink, dojo.widget.HtmlWidget);

//-->
