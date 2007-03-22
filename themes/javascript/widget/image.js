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

dojo.provide("webui.@THEME@.widget.image");

dojo.require("dojo.widget.*");
dojo.require("dojo.uri.Uri");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.image.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.image = function() {
    // Set defaults.
    this.border = 0;
    this.widgetType = "image";

    // Register widget.
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set public functions. 
        this.domNode.setProps = webui.@THEME@.widget.image.setProps;		
            
        // Set properties.
        this.domNode.setProps(this);
	return true;		
    }
}

/**
 * This function is used to update widget properties with the
 * following Object literals. Not all properties are required.
 *
 * <ul>
 *  <li>alt</li>
 *  <li>align</li>
 *  <li>longDesc</li>
 *  <li>className</li>
 *  <li>src</li>
 *  <li>dir</li>
 *  <li>id</li>
 *  <li>lang</li>>
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
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>vspace</li>
 *  <li>hspace</li>
 *  <li>width</li>
 *  <li>height</li>
 *  <li>border</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.image.setProps = function(props){
    if (props == null) {
        return false;
    }

    // Get label widget.
    var widget = dojo.widget.byId(this.id);
    if (widget != null) {
        // Save properties for later updates.
        webui.@THEME@.widget.common.extend(widget, props);
    } else {
        // SetProps called by widget -- do not extend object.
        widget = dojo.widget.byId(props.id);
        if (widget == null) {
            return false;
        }
    }

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this, props);
    webui.@THEME@.widget.common.setCommonProps(this, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this, props);

    if (props.alt) { this.setAttribute("alt", props.alt); }
    if (props.align) { this.setAttribute("align", props.align); }
    if (props.longDesc) { this.setAttribute("longDesc", props.longDesc); }
    if (props.src) { this.setAttribute("src", new dojo.uri.Uri(props.src).toString()); }
    if (props.vspace) { this.setAttribute("vspace", props.vspace); }
    if (props.hspace) { this.setAttribute("hspace", props.hspace); }
    if (props.width) { this.setAttribute("width", props.width); }
    if (props.height) { this.setAttribute("height", props.height); }
    if (props.border != null) { this.setAttribute("border", props.border); }

    return true;            
}
        
dojo.inherits(webui.@THEME@.widget.image, dojo.widget.HtmlWidget);


