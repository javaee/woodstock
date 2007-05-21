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

dojo.provide("webui.@THEME@.widget.label");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.label.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.label = function() {
    // Set defaults.
    this.level = 2;
    this.required = false;
    this.valid = true;
    this.widgetType = "label";

    // Register widget.
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.requiredImageContainer.id = this.id + "_requiredImageContainer";
            this.errorImageContainer.id = this.id + "_errorImageContainer";
            this.valueContainer.id = this.id + "_valueContainer";
            this.contentsContainer.id = this.id + "_contentsContainer";
        }

        // Set public functions. 
	this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }

        // Set private functions.
        this.destroy = webui.@THEME@.widget.label.destroy;
        this.getClassName = webui.@THEME@.widget.label.getClassName;
	this.getProps = webui.@THEME@.widget.label.getProps;
        this.refresh = webui.@THEME@.widget.label.refresh.processEvent;      
        this.setProps = webui.@THEME@.widget.label.setProps;
        this.validate = webui.@THEME@.widget.label.validation.processEvent;

        // Set properties.
        return this.setProps();
    }
}

/**
 * Helper function to remove all the existing widgets.
 *
 */
webui.@THEME@.widget.label.destroy = function() {    
    // Remove error image widget.
    if (this.errorImage != null) {
        var errorImageWidget = dojo.widget.byId(this.errorImage.id); 
        if (errorImageWidget) {
            errorImageWidget.destroy();                        
        }        
    }
    
    // Remove required image widget.     
    if (this.requiredImage != null) {
       var requiredImageWidget = dojo.widget.byId(this.requiredImage.id);
       if (requiredImageWidget) {
            requiredImageWidget.destroy();                  
       }
    }

    // Remove contents.
    if (this.contents != null) {	
	for (var i = 0; i < this.contents.length; i++) {
            var contentWidget = dojo.widget.byId(this.contents[i].id);
            contentWidget.destroy();
        }
    }
    
    // Remove this widget.
    dojo.widget.removeWidgetById(this.id);
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.label.getClassName = function() {
    // Set style for default label level.
    var className = webui.@THEME@.widget.props.label.levelTwoStyleClass;

    if (this.valid == false) {
        className = webui.@THEME@.widget.props.label.errorStyleClass;
    } else if (this.level == 1) {
        className = webui.@THEME@.widget.props.label.levelOneStyleClass;
    } else if (this.level == 3) {
        className = webui.@THEME@.widget.props.label.levelThreeStyleClass;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.label.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.label.getProps = function() {
    var props = {};

    // Set properties.
    if (this.contents) { props.contents = this.contents; }
    if (this.errorImage) { props.errorImage = this.errorImage; }
    if (this.htmlFor) { props.htmlFor = this.htmlFor; }
    if (this.level != null) { props.level = this.level; }
    if (this.required != null) { props.required = this.required; }
    if (this.requiredImage) { props.requiredImage = this.requiredImage; }
    if (this.valid != null) { props.valid = this.valid; }
    if (this.value) { props.value = this.value; }

    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getJavaScriptProps(this));

    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.label.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_label_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_label_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Publish event.
        webui.@THEME@.widget.label.refresh.publishBeginEvent({
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
        dojo.event.topic.publish(webui.@THEME@.widget.label.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.label.refresh.endEventTopic, props);
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>accesskey</li>
 *  <li>className</li>
 *  <li>contents</li>
 *  <li>dir</li>
 *  <li>errorImage</li>
 *  <li>htmlFor</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>level</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onFocus</li>
 *  <li>onKeyDown</li>
 *  <li>onKeyPress</li>
 *  <li>onKeyUp</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>required</li>
 *  <li>requiredImage</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>valid</li>
 *  <li>value</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.label.setProps = function(props) {
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
    
    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
    webui.@THEME@.widget.common.setCommonProps(this.domNode, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.domNode, props);

    if (props.htmlFor) { this.domNode.htmlFor = props.htmlFor; }

    // Set label value.
    if (props.value) {
        webui.@THEME@.widget.common.addFragment(this.valueContainer,
            dojo.string.escape("html", props.value)); 
    }
  
    // Set error image properties.
    if (props.errorImage || props.valid != null && this.errorImage) {
        // Ensure property exists so we can call setProps just once.
        if (props.errorImage == null) {
            props.errorImage = {};
        }

        // Show error image.
        props.errorImage.visible = (this.valid != null)
            ? !this.valid : false;

        // Update widget/add fragment.
        var errorImageWidget = dojo.widget.byId(this.errorImage.id); 
        if (errorImageWidget) {
            errorImageWidget.setProps(props.errorImage);
        } else {
            webui.@THEME@.widget.common.addFragment(this.errorImageContainer,
                props.errorImage);
        }
    }

    // Set required image properties.
    if (props.requiredImage || props.required != null && this.requiredImage) {       
        // Ensure property exists so we can call setProps just once.
        if (props.requiredImage == null) {
            props.requiredImage = {};
        }

        // Show required image.
        props.requiredImage.visible = (this.required != null)
            ? this.required : false;

        // Update widget/add fragment.
        var requiredImageWidget = dojo.widget.byId(this.requiredImage.id);
        if (requiredImageWidget) {
            requiredImageWidget.setProps(props.requiredImage);
        } else {
            webui.@THEME@.widget.common.addFragment(this.requiredImageContainer,
                props.requiredImage);
        }
    }

    // Set contents.
    if (props.contents) {
	this.contentsContainer.innerHtml = ""; // Cannot be null on IE.
	for (var i = 0; i < props.contents.length; i++) {
            webui.@THEME@.widget.common.addFragment(this.contentsContainer, 
		props.contents[i], "last");
        }
    }
    return true;
}

/**
 * This closure is used to process validation events.
 */
webui.@THEME@.widget.label.validation = {
    /**
     * This function is used to process validation events with the following
     * Object literals.
     *
     * <ul>
     *  <li>detail</li>
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
            valid: props.valid,
            errorImage: {
                title: props.detail
            }
        });
    }
}

dojo.inherits(webui.@THEME@.widget.label, dojo.widget.HtmlWidget);

//-->
