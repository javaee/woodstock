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

dojo.provide("webui.@THEME@.widget.staticText");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.staticText.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.staticText = function() {
    this.widgetType = "staticText";
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        
        // Set public functions.
        this.domNode.setProps = webui.@THEME@.widget.staticText.setProps;
        
        // Set properties.
        this.domNode.setProps({
            
            id: this.id,
            escape: this.escape,
            className: this.className, 
            value: this.value,
            onClick: this.onClick,
            onDblClick: this.onDblClick,
            onMouseDown: this.onMouseDown,
            onMouseOut: this.onMouseOut,
            onMouseOver: this.onMouseOver,
            onMouseUp: this.onMouseUp,
            onMouseMove: this.onMouseMove,
            dir: this.dir,
            lang: this.lang,
            style: this.style,
            title: this.title,
            visible: this.visible

        });
        return true;
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>id</li>
 *  <li>escape</li>
 *  <li>className</li>
 *  <li>value</li>
 *  <li>onClick</li>
 *  <li>onDblClick</li>
 *  <li>onMouseDown</li>
 *  <li>onMouseOut</li>
 *  <li>onMouseOver</li>
 *  <li>onMouseUp</li>
 *  <li>onMouseMove</li>
 *  <li>dir</li>
 *  <li>lang</li>
 *  <li>style</li>
 *  <li>title</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.staticText.setProps = function(props) {
    if (props == null) {
        return false;
    }
    
    // Save properties for later updates.
    if (this._props) {
        Object.extend(this._props, props); // Override existing values, if any.
    } else {
        this._props = props;
    }
            
    // Set attributes.
    webui.@THEME@.widget.common.setCoreProperties(this, this._props);
    webui.@THEME@.widget.common.setJavaScriptProperties(this, this._props);
    
        
    // Set text value.
    if (this._props.value) {
        if (this._props.escape) {
            this._props.value = dojo.string.escape("html", this._props.value);
        }
        this.innerHTML = null;
        webui.@THEME@.widget.common.addFragment(this, this._props.value, "last");
    }

    
    return true;
}

dojo.inherits(webui.@THEME@.widget.staticText, dojo.widget.HtmlWidget);

//-->
