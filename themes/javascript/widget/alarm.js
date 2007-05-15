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

dojo.provide("webui.@THEME@.widget.alarm");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.alarm.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.alarm = function() {
    this.widgetType = "alarm";
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.rightText.id = this.id + "_rightText";
            this.leftText.id = this.id + "_leftText";
            this.imageContainer.id = this.id + "_imageContainer";
            
        }

        // Set public functions.
        this.domNode.getProps = function() { return dojo.widget.byId(this.id).getProps(); }
        this.domNode.refresh = function(execute) { return dojo.widget.byId(this.id).refresh(execute); }
        this.domNode.setProps = function(props) { return dojo.widget.byId(this.id).setProps(props); }
                
        this.getProps = webui.@THEME@.widget.alarm.getProps;
        this.refresh = webui.@THEME@.widget.alarm.refresh.processEvent;
        this.setProps = webui.@THEME@.widget.alarm.setProps;

        // Set properties.
        return this.setProps();
    }
}

/**
 * This function is used to get widget properties. Please see
 * webui.@THEME@.widget.alarm.setProps for a list of supported
 * properties.
 */
webui.@THEME@.widget.alarm.getProps = function() {
    var props = {};

    // Set properties.
    if (this.text != null) { props.text = this.text; }
    if (this.indicators != null) { props.indicators = this.indicators; }
    if (this.textPosition != null) { props.textPosition = this.textPosition; }
    if (this.type != null) { props.type = this.type; }
            
    // Add DOM node properties.
    Object.extend(props, webui.@THEME@.widget.common.getCommonProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getCoreProps(this));
    Object.extend(props, webui.@THEME@.widget.common.getJavaScriptProps(this));
    
    return props;
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.alarm.refresh = { 
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_alarm_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_alarm_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute Comma separated string containing a list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        
    // Publish event.
        webui.@THEME@.widget.alarm.refresh.publishBeginEvent({
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
        dojo.event.topic.publish(webui.@THEME@.widget.alarm.refresh.beginEventTopic, props);
        return true;
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) { 
        dojo.event.topic.publish(webui.@THEME@.widget.alarm.refresh.endEventTopic, props);
        return true;
    }
}


/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>className</li>    
 *  <li>dir</li>
 *  <li>indicators</li>
 *  <li>id</li>
 *  <li>lang</li>
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
 *  <li>text</li>
 *  <li>textPosition</li>
 *  <li>title</li>
 *  <li>type</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.alarm.setProps = function(props) {
    // After widget has been initialized, save properties for later updates.
    if (props != null) {
        webui.@THEME@.widget.common.extend(this, props);    
    } else {
        props = this.getProps(); // Widget is being initialized.
    }

    // Set attributes.
    webui.@THEME@.widget.common.setCoreProps(this.domNode, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this.domNode, props); 

    // Do not call setCommonProps as that will result in assigning all image specific properties to
    // outermost domNode. Assign a11y properties to alarm images.
    if (props.dir) { this.domNode.dir = props.dir; }
    if (props.lang) { this.domNode.lang = props.lang; }    
    
    // Set right text.
    if (props.textPosition == "right" || props.textPosition == null && props.text != null) {
        webui.@THEME@.common.setVisibleElement(this.leftText, false);
        webui.@THEME@.widget.common.addFragment(this.rightText, props.text);
    }

    // Set left text.
    if (props.textPosition == "left" && props.text != null) {
        webui.@THEME@.common.setVisibleElement(this.rightText, false);
        webui.@THEME@.widget.common.addFragment(this.leftText, props.text);
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
            indicator.image.visible = (this.type != null && this.type == indicator.type) ?   
                                      true: false;
                               
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

dojo.inherits(webui.@THEME@.widget.alarm, dojo.widget.HtmlWidget);

//-->
