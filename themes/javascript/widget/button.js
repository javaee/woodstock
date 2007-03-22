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

dojo.provide("webui.@THEME@.widget.button");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.button.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.button = function() {
    // Set defaults.
    this.disabled = false;
    this.mini = false;
    this.primary = true;
    this.type = "submit";
    this.widgetType = "button";

    // Register widget.
    dojo.widget.Widget.call(this);

    /**
     * This function is used to generate a template based widget.
     */
    this.fillInTemplate = function() {
        // Set public functions.
        this.domNode.setProps = webui.@THEME@.widget.button.setProps;

        // Set private functions.
        this.getClassName = webui.@THEME@.widget.button.getClassName;
        this.getHoverClassName = webui.@THEME@.widget.button.getHoverClassName;

        // Deprecated functions from formElements.js. 
        // 
        // Note: Although we now have a setProps function to update properties,
        // the following functions must be backward compatible.
        this.domNode.isSecondary = webui.@THEME@.button.isSecondary;
        this.domNode.setSecondary = webui.@THEME@.button.setSecondary;
        this.domNode.isPrimary = webui.@THEME@.button.isPrimary;
        this.domNode.setPrimary = webui.@THEME@.button.setPrimary;
        this.domNode.isMini = webui.@THEME@.button.isMini;
        this.domNode.setMini = webui.@THEME@.button.setMini;
        this.domNode.getDisabled = webui.@THEME@.button.getDisabled;
        this.domNode.setDisabled = webui.@THEME@.button.setDisabled;
        this.domNode.getVisible = webui.@THEME@.button.getVisible;
        this.domNode.setVisible = webui.@THEME@.button.setVisible;
        this.domNode.getText = webui.@THEME@.button.getText;
        this.domNode.setText = webui.@THEME@.button.setText;
        this.domNode.doClick = webui.@THEME@.button.click;

        // Set events.
        dojo.event.connect(this.domNode, "onblur",
            webui.@THEME@.widget.button.createOnBlurCallback(this.id));
        dojo.event.connect(this.domNode, "onfocus",
            webui.@THEME@.widget.button.createOnFocusCallback(this.id));
        dojo.event.connect(this.domNode, "onmouseout",
            webui.@THEME@.widget.button.createOnMouseOutCallback(this.id));
        dojo.event.connect(this.domNode, "onmouseover",
            webui.@THEME@.widget.button.createOnMouseOverCallback(this.id));

        // Set properties.
        this.domNode.setProps(this);
        return true;
    }
}

/**
 * Helper function to create callback for onBlur event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.button.createOnBlurCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        if (widget.disabled == true) {
            return true;
        }

        // Set style class.
        widget.domNode.className = widget.getClassName();
        return true;
    };
}

/**
 * Helper function to create callback for onFocus event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.button.createOnFocusCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        if (widget.disabled == true) {
            return true;
        }

        // Set style class.
        widget.domNode.className = widget.getHoverClassName();
        return true;
    };
}

/**
 * Helper function to create callback for onMouseOut event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.button.createOnMouseOutCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        if (widget.disabled == true) {
            return true;
        }

        // Set style class.
        widget.domNode.className = widget.getClassName();
        return true;
    };
}

/**
 * Helper function to create callback for onMouseOver event.
 *
 * @param id The HTML element id used to invoke the callback.
 */
webui.@THEME@.widget.button.createOnMouseOverCallback = function(id) {
    if (id == null) {
        return null;
    }
    // New literals are created every time this function
    // is called, and it's saved by closure magic.
    return function(evt) { 
        var widget = dojo.widget.byId(id);
        if (widget == null) {
            return false;
        }
        if (widget.disabled == true) {
            return true;
        }

        // Set style class.
        widget.domNode.className = widget.getHoverClassName();
        return true;
    };
}

/**
 * Helper function to obtain widget class names.
 */
webui.@THEME@.widget.button.getClassName = function() {
    var className = null;
    if (this.mini == true && this.primary == true) {
        className = (this.disabled == true)
            ? webui.@THEME@.widget.props.button.primaryMiniDisabledClassName
            : webui.@THEME@.widget.props.button.primaryMiniClassName;
    } else if (this.mini == true) {
        className = (this.disabled == true)
            ? webui.@THEME@.widget.props.button.secondaryMiniDisabledClassName
            : webui.@THEME@.widget.props.button.secondaryMiniClassName;
    } else if (this.primary == true) {
        className = (this.disabled == true)
            ? webui.@THEME@.widget.props.button.primaryDisabledClassName
            : webui.@THEME@.widget.props.button.primaryClassName;
    } else {
        className = (this.disabled == true)
            ? webui.@THEME@.widget.props.button.secondaryDisabledClassName
            : webui.@THEME@.widget.props.button.secondaryClassName;
    }
    return (this.className) 
        ? className + " " + this.className
        : className;
}

/**
 * Helper function to obtain widget (mouse hover) class names.
 */
webui.@THEME@.widget.button.getHoverClassName = function() {
    var className = null;
    if (this.mini == true && this.primary == true) {
        className = webui.@THEME@.widget.props.button.primaryHovMiniClassName;
    } else if (this.mini == true) {
        className = webui.@THEME@.widget.props.button.secondaryHovMiniClassName;
    } else if (this.primary == true) {
        className = webui.@THEME@.widget.props.button.primaryHovClassName;
    } else {
        className = webui.@THEME@.widget.props.button.secondaryHovClassName;
    }
    return (this.className) 
        ? className + " " + this.className
        : className;
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>alt</li>
 *  <li>align</li>
 *  <li>className</li>
 *  <li>contents</li>
 *  <li>dir</li>
 *  <li>disabled</li>
 *  <li>id</li>
 *  <li>lang</li>
 *  <li>mini</li>
 *  <li>name</li>
 *  <li>onBlur</li>
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
 *  <li>primary</li>
 *  <li>style</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 *  <li>type</li>
 *  <li>value</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.button.setProps = function(props) {
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

    // Set style class before calling setCoreProps.
    props.className = widget.getClassName();

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProps(this, props);
    webui.@THEME@.widget.common.setCommonProps(this, props);
    webui.@THEME@.widget.common.setJavaScriptProps(this, props);

    if (props.alt) { this.setAttribute("alt", props.alt); }
    if (props.align) { this.setAttribute("align", props.align); }
    if (props.disabled == true) {
        this.setAttribute("disabled", "disabled");
    } else {
        this.removeAttribute("disabled");
    }
    if (props.name) { this.setAttribute("name", props.name); }
    if (props.value) { this.setAttribute("value", props.value); }
    if (props.type) { this.setAttribute("type", props.type); }

    // Set contents.
    if (props.contents) {
        this.innerHTML = ""; // Cannot be set null on IE.
//        for (var i = 0; i < props.contents.length; i++) {
            webui.@THEME@.widget.common.addFragment(this, props.contents, "last");
//        }
    }
    return true;
}

dojo.inherits(webui.@THEME@.widget.button, dojo.widget.HtmlWidget);

//-->
