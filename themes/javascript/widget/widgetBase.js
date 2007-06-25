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

dojo.provide("webui.@THEME@.widget.widgetBase");

dojo.require("dojo.widget.*");

/**
 * This function is used as a base object when creating a Dojo widget. In
 * addition to providing commonly used functions, dojo.widget.HtmlWidget shall
 * be extended.
 */
webui.@THEME@.widget.widgetBase = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function is used to add a widget or HTML string to the given parent 
 * node.
 *
 * Note: If props is a JSON object, it must contain a widgetName value so the 
 * correct widget may be created. The newly created widget is added as a child
 * of the given parentNode. If props also contains a module property, the 
 * specified resources shall be retrieved before creating the widget. If props 
 * is a string, it will be added to the given parentNode, but position does not
 * apply.
 *
 * Note: The position argument is passed though to Dojo's createWidget function.
 * Valid values consist of "last", "first", etc. -- see Dojo docs). However, if
 * the position is null, existing child nodes are replaced by the new widget.
 * The parentNode is not removed because it may be used as a place holder.
 *
 * @param parentNode The parent node used to add widget.
 * @param props Key-Value pairs of properties.
 * @param position The position (e.g., "first", "last", etc.) to add widget.
 */
webui.@THEME@.widget.addFragment = function(parentNode, props, position) {
    if (parentNode == null || props == null) {
        return false;
    }

    // If position is null, remove existing nodes. The contents shall be
    // replaced by the newly created widget.
    if (position == null) {
        position = "last"; // Default.
        this.removeChildNodes(parentNode);
    }

    // Add fragment.
    if (typeof props == 'string') {
        // Strip script fragments, set innerHTML property, and
        // eval scripts using a timeout.
        //
        // Note that using Dojo's ContentPane widget would have
        // been more preferable than creating a new dependency
        // based on Prototype. However, as of version .4.1, Dojo
        // still does not use a timeout to eval JavaScript; thus,
        // IE generates errors with innerHTML. For example: 
        //
        // var pane = dojo.widget.createWidget("ContentPane", { 
        //     executeScripts: true,
        //     scriptSeparation: false
        // }, parentNode, position);
        // pane.setContent(props);
        //
        // "The problem has to do with the browser's poor
        // threading model. Basically, once some JavaScript code
        // is running, all other threads of execution are on hold
        // until the running thread is finished with it's
        // task. This includes whatever thread of execution that
        // updates the DOM model when new content is inserted via
        // innerHTML. For example if you do:
        //
        // foo.innerHTML = '<span id="bar">Bar</span>';
        // var bar = document.getElementById('bar');
        //
        // This code will sometimes fail because although you
        // added the content via innerHTML the DOM may not have
        // been updated and therefore the "bar" element does not
        // exist in the DOM yet. To work around this you can do:
        //
        // foo.innerHTML = '<span id="bar">Bar</span>';
        // setTimeout(function() {var bar = document.getElementById('bar');},10);
        //
        // This will work because in 10 milliseconds whatever
        // event handler is running will have finished, the DOM
        // will update, then the callback will execute and have no
        // problem accessing the DOM element."
        //
        // The full discussion on this topic can be found at:
        //
        // http://www.ruby-forum.com/topic/73990
        //
        var html = props.stripScripts();

        // Concatenating innerHTML with new strings does not always work. 
        // When adding multiple HTML elements to parentNode, we can get
        // into a situation where parentNode.innerHTML may not yet contain 
        // all the changes made to the previously added DOM node. Thus, the
        // following code may yield incorrect results. Instead, we shall 
        // wrap new strings in an HTML span element so it may be added as a 
        // child of parentNode.
        //
        // if (position && parentNode.innerHTML) {
        //     html = parentNode.innerHTML + html;
        // }
        if (parentNode.innerHTML != null && parentNode.innerHTML.length > 0) {
            var span = document.createElement('span');            
            span.innerHTML = html;
            parentNode.appendChild(span);
        } else {
            // Don't need span when innerHTML is empty.
            parentNode.innerHTML = html;
        }

        // Evaluate JavaScript.
        setTimeout(function() {props.evalScripts()}, 10);
    } else if (props.widgetName) {
        // Create widget.
        webui.@THEME@.widget.common.createWidget(props, parentNode, position);
    }
}

/**
 * This function is used to include default Ajax functionality. Before the given
 * module is included in the page, a test is performed to ensure that the 
 * default Ajax implementation is being used.
 *
 * @param module The module to include in the page.
 */
webui.@THEME@.widget.ajaxify = function(module) {
    if (webui.@THEME@.widget.jsfx && module) {
        webui.@THEME@.widget.common.require(module);
    }
}
    
/**
 * This function is used to extend the given object with Key-Value pairs of
 * properties. If a property is an object containing Key-Value pairs itself,
 * this function is called recursively to preserve data which is not
 * explicitly extended.
 *
 * Note: If all top level properties must be replaced, use Prototype's 
 * Object.extend function.
 *
 * @param obj The object to extend.
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.extend = function(obj, props) {
    if (obj == null || props == null) {
        return false;
    }
    for (var property in props) {
        if (obj[property] && typeof obj[property] == "object") {
            this.extend(obj[property], props[property]);
        } else {
            obj[property] = props[property];
        }
    }
    return true;
}

/**
 * This function is used to get common properties for the widget. Please see the
 * setCommonProps() function for a list of supported properties.
 */
webui.@THEME@.widget.getCommonProps = function() {
    var props = {};

    // Set properties.
    if (this.accessKey) { props.accessKey = this.accessKey; }
    if (this.dir) { props.dir = this.dir; }
    if (this.lang) { props.lang = this.lang; }
    if (this.tabIndex) { props.tabIndex = this.tabIndex; }
    if (this.title) { props.title = this.title; }

    return props;
}

/**
 * This function is used to get core properties for the widget. Please see the
 * setCoreProps() function for a list of supported properties.
 */
webui.@THEME@.widget.getCoreProps = function() {
    var props = {};

    // Set properties.
    if (this.className) { props.className = this.className; }
    if (this.id) { props.id = this.id; }
    if (this.style) { props.style = this.style; }
    if (this.visible != null) { props.visible = this.visible; }

    return props;
}

/**
 * This function is used to get JavaScript properties for the widget. Please see
 * the setJavaScriptProps() function for a list of supported properties.
 */
webui.@THEME@.widget.getJavaScriptProps = function() {
    var props = {};

    // Set properties.
    if (this.onBlur) { props.onBlur = this.onBlur; }
    if (this.onChange) { props.onChange = this.onChange; }
    if (this.onClick) { props.onClick = this.onClick; }
    if (this.onDblClick) { props.onDblClick = this.onDblClick; }
    if (this.onFocus) { props.onFocus = this.onFocus; }
    if (this.onKeyDown) { props.onKeyDown = this.onKeyDown; }
    if (this.onKeyPress) { props.onKeyPress = this.onKeyPress; }
    if (this.onKeyUp) { props.onKeyUp = this.onKeyUp; }
    if (this.onMouseDown) { props.onMouseDown = this.onMouseDown; }
    if (this.onMouseOut) { props.onMouseOut = this.onMouseOut; }
    if (this.onMouseOver) { props.onMouseOver = this.onMouseOver; }
    if (this.onMouseUp) { props.onMouseUp = this.onMouseUp; }
    if (this.onMouseMove) { props.onMouseMove = this.onMouseMove; }
    if (this.onSelect) { props.onSelect = this.onSelect; }

    return props;
}

/**
 * This function is used to test if widget has been initialized.
 *
 * Note: It is assumed that an HTML element is used as a place holder for the
 * document fragment.
 */
webui.@THEME@.widget.isInitialized = function() {
    var domNode = document.getElementById(this.id);
    if (domNode && domNode.getAttribute("dojoattachpoint")) {
        return true;
    }
    return false;
}

/**
 * This function is used to remove child nodes from given DOM node.
 *
 * Note: Child nodes may be cleared using the innerHTML property. However, IE
 * fails when this property is set via the widget's fillInTemplate function. In
 * this case, DOM nodes shall be removed manually using the Node APIs.
 *
 * @param domNode The DOM node to remove child nodes.
 */
webui.@THEME@.widget.removeChildNodes = function(domNode) {
    if (domNode == null) {
        return false;
    }

    // To do: Should we destroy widgets here? Unless new widgets use the same
    // id, the old widgets may never be removed.

    try {
        domNode.innerHTML = ""; // Cannot be null on IE.
    } catch (e) {
        // Iterate over child nodes.
        while (domNode.hasChildNodes()) {
            var node = domNode.childNodes[0];
            domNode.removeChild(node);
        }
    }
    return true;
}

/**
 * This function is used to set common properties for the given DOM node
 * with the following Object literals.
 *
 * <ul>
 *  <li>accessKey</li>
 *  <li>dir</li>
 *  <li>lang</li>
 *  <li>tabIndex</li>
 *  <li>title</li>
 * </ul>
 *
 * @param domNode The DOM node to assign properties to.
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.setCommonProps = function(domNode, props) {
    if (domNode == null || props == null) {
        return false;
    }
    if (props.accessKey) { 
        domNode.accessKey = props.accessKey;
    }
    if (props.dir) {
        domNode.dir = props.dir;
    }
    if (props.lang) {
        domNode.lang = props.lang;
    }
    if (props.tabIndex > -1 && props.tabIndex < 32767) {
        domNode.tabIndex = props.tabIndex;
    }
    if (props.title) {
        domNode.title = props.title;
    }
    return true;
}

/**
 * This function is used to set core properties for the given DOM
 * node with the following Object literals. These properties are typically
 * set on the outermost element.
 *
 * <ul>
 *  <li>className</li>
 *  <li>id</li>
 *  <li>style</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: The className is typically provided by a web app developer. If 
 * the widget has a default className, it should be added to the DOM node
 * prior to calling this function. For example, the "myCSS" className would
 * be appended to the existing "Tblsun4" className (e.g., "Tbl_sun4 myCSS").
 *
 * @param domNode The DOM node to assign properties to.
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.setCoreProps = function(domNode, props) {
    if (domNode == null || props == null) {
        return false;
    }
    if (props.className) {
        domNode.className = props.className;
    }
    if (props.id) { 
        domNode.id = props.id;
    }
    if (props.style) { 
        domNode.style.cssText = props.style;
    }
    if (props.visible != null) {
        webui.@THEME@.common.setVisibleElement(domNode, 
            new Boolean(props.visible).valueOf());
    }
    return true;
}

/**
 * This function is used to set JavaScript properties for the given DOM
 * node with the following Object literals.
 *
 * <ul>
 *  <li>onBlur</li>
 *  <li>onChange</li>
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
 *  <li>onSelect</li>
 * </ul>
 *
 * @param domNode The DOM node to assign properties to.
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.setJavaScriptProps = function(domNode, props) {
    if (domNode == null || props == null) {
        return false;
    }

    // Note: JSON strings are not recognized as JavaScript. In order for
    // events to work properly, an anonymous function must be created.
    if (props.onBlur) { 
        domNode.onblur = (typeof props.onBlur == 'string')
            ? new Function("event", props.onBlur)
            : props.onBlur;
    }
    if (props.onClick) {
        domNode.onclick = (typeof props.onClick == 'string')
            ? new Function("event", props.onClick)
            : props.onClick;
    }
    if (props.onChange) {
        domNode.onchange = (typeof props.onChange == 'string')
            ? new Function("event", props.onChange)
            : props.onChange;
    }
    if (props.onDblClick) {
        domNode.ondblclick = (typeof props.onDblClick == 'string')
            ? new Function("event", props.onDblClick)
            : props.onDblClick;
    }
    if (props.onFocus) {
        domNode.onfocus = (typeof props.onFocus == 'string')
            ? new Function("event", props.onFocus)
            : props.onFocus;
    }
    if (props.onKeyDown) {
        domNode.onkeydown = (typeof props.onKeyDown == 'string')
            ? new Function("event", props.onKeyDown)
            : props.onKeyDown;
    }
    if (props.onKeyPress) {
        domNode.onkeypress = (typeof props.onKeyPress == 'string')
            ? new Function("event", props.onKeyPress)
            : props.onKeyPress;
    }
    if (props.onKeyUp) {
        domNode.onkeyup = (typeof props.onKeyUp == 'string')
            ? new Function("event", props.onKeyUp)
            : props.onKeyUp;
    }
    if (props.onMouseDown) {
        domNode.onmousedown = (typeof props.onMouseDown == 'string')
            ? new Function("event", props.onMouseDown)
            : props.onMouseDown;
    }
    if (props.onMouseOut) {
        domNode.onmouseout = (typeof props.onMouseOut == 'string')
            ? new Function("event", props.onMouseOut)
            : props.onMouseOut;
    }
    if (props.onMouseOver) {
        domNode.onmouseover = (typeof props.onMouseOver == 'string')
            ? new Function("event", props.onMouseOver)
            : props.onMouseOver;
    }
    if (props.onMouseUp) {
        domNode.onmouseup = (typeof props.onMouseUp == 'string')
            ? new Function("event", props.onMouseUp)
            : props.onMouseUp;
    }
    if (props.onMouseMove) {
        domNode.onmousemove = (typeof props.onMouseMove == 'string')
            ? new Function("event", props.onMouseMove)
            : props.onMouseMove;
    }
    if (props.onSelect) {
        domNode.onselect = (typeof props.onSelect == 'string')
            ? new Function("event", props.onSelect)
            : props.onSelect;
    }
    return true;
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.widgetBase, dojo.widget.HtmlWidget);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.widgetBase, {
    // Set private functions.
    addFragment: webui.@THEME@.widget.addFragment,
    ajaxify: webui.@THEME@.widget.ajaxify,
    event: webui.@THEME@.widget.event,
    extend: webui.@THEME@.widget.extend,
    getCommonProps: webui.@THEME@.widget.getCommonProps,
    getCoreProps: webui.@THEME@.widget.getCoreProps,
    getJavaScriptProps: webui.@THEME@.widget.getJavaScriptProps,
    isInitialized: webui.@THEME@.widget.isInitialized,
    removeChildNodes: webui.@THEME@.widget.removeChildNodes,
    setCommonProps: webui.@THEME@.widget.setCommonProps,
    setCoreProps: webui.@THEME@.widget.setCoreProps,
    setJavaScriptProps: webui.@THEME@.widget.setJavaScriptProps
});

//-->
