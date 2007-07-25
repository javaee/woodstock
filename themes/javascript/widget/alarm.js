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

dojo.provide("webui.@THEME@.widget.alarm");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.alarm = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the setWidgetProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.alarm.fillInTemplate = function(props, frag) {
    // Set ids.
    if (this.id) {
        this.rightText.id = this.id + "_rightText";
        this.leftText.id = this.id + "_leftText";
        this.imageContainer.id = this.id + "_imageContainer";        
    }

    // Set common functions.
    return webui.@THEME@.widget.alarm.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to get widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
 */
webui.@THEME@.widget.alarm.getProps = function() {
    var props = webui.@THEME@.widget.alarm.superclass.getProps.call(this);

    // Set properties.
    if (this.text != null) { props.text = this.text; }
    if (this.indicators != null) { props.indicators = this.indicators; }
    if (this.textPosition != null) { props.textPosition = this.textPosition; }
    if (this.type != null) { props.type = this.type; }
    
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
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.alarm");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.alarm.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.alarm.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
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
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.alarm.setWidgetProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set properties.
    if (props.dir) { this.domNode.dir = props.dir; }
    if (props.lang) { this.domNode.lang = props.lang; }    
    
    // Set right text.
    if (props.textPosition == "right" || props.textPosition == null && props.text != null) {
        webui.@THEME@.common.setVisibleElement(this.leftText, false);
        this.addFragment(this.rightText, props.text);
    }

    // Set left text.
    if (props.textPosition == "left" && props.text != null) {
        webui.@THEME@.common.setVisibleElement(this.rightText, false);
        this.addFragment(this.leftText, props.text);
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
            indicator.image.visible = (this.type != null && this.type == indicator.type)
                ? true: false;
                               
            // Update widget/add fragment.
            var indicatorWidget = dojo.widget.byId(indicator.image.id);
            if (indicatorWidget) {
                indicatorWidget.setProps(indicator.image);
            } else { 
                this.addFragment(this.imageContainer, indicator.image, "last");
            }
        }
    }

    // Do not call setCommonProps() as that will result in assigning image 
    // specific properties to outermost domNode. 

    // Set more properties.
    this.setJavaScriptProps(this.domNode, props);

    // Set core props.
    return webui.@THEME@.widget.alarm.superclass.setWidgetProps.call(this, props);
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.alarm, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.alarm, {
    // Set private functions.
    fillInTemplate: webui.@THEME@.widget.alarm.fillInTemplate,
    getProps: webui.@THEME@.widget.alarm.getProps,
    refresh: webui.@THEME@.widget.alarm.refresh.processEvent,
    setWidgetProps: webui.@THEME@.widget.alarm.setWidgetProps,

    // Set defaults.
    widgetType: "alarm"
});
