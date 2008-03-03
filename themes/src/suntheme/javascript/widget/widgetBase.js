/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

webui.@THEME@.dojo.provide("webui.@THEME@.widget.widgetBase");
 
webui.@THEME@.dojo.require("webui.@THEME@.common");
webui.@THEME@.dojo.require("webui.@THEME@.prototypejs");
webui.@THEME@.dojo.require("webui.@THEME@.theme.common");
webui.@THEME@.dojo.require("webui.@THEME@.widget.common");
webui.@THEME@.dojo.require("webui.@THEME@.widget.eventBase");

/**
 * @name webui.@THEME@.widget.widgetBase
 * @extends webui.@THEME@.widget.eventBase
 * @class This class contains functions used for base functionality in all 
 * widgets. 
 * <p>
 * The widgetBase class inherits from webui.@THEME@.dijit._Widget and 
 * webui.@THEME@.dijit._Templated. The webui.@THEME@.dijit._Widget class is 
 * responsible for calling the buildRendering() and postCreate() functions in 
 * that order. The dijit_Templated function overrides the buildRendering() 
 * functon to fill in template properties.
 * <p></p>
 * The postCreate() function is responible for initializing CSS selectors, 
 * events, and public functions. Commonly used functions (e.g., getProps(), 
 * setProps(), and refresh() are set on the outermost DOM node via the 
 * "superclass" function of widgetBase. This inherited function is also 
 * responsible for invoking the private _setProps() function. 
 * <p></p>
 * The private _setProps() function is used to set widget properties that can be
 * updated by a web app developer. This helps encapsolate functionality and 
 * brand changes while providing a common API for all widgets. In addition, the 
 * widget is selctively updated (i.e., if and only if a key-value pair has been 
 * given). Saving given properties is deferred to the public setProps() function
 * which allows _setProps() to be used during initialization.
 * <p></p>
 * The private _setProps() function is also responsible for invoking 
 * setCommonProps() and setEventProps(). These properties may not always be set 
 * on the outermost DOM node; however, core (i.e., id, class, style, etc.) 
 * properties are. Core properties are set on the DOM via the "superclass" 
 * function of widgetBase which invokes the setCoreProps() function.
 * <p></p>
 * The getClassName() function is responsible for obtaining the selector that
 * will be set on the outermost DOM node. The private _setProps() function 
 * of widgetBase ensures that the getClassName() function is called prior to 
 * invoking setCoreProps(). In most cases, this function will be overridded in
 * order to apply widget specific selectors. However, selectors should be 
 * concatinated in order of precedence (e.g., the user's className property is 
 * always appended last).
 * <p></p>
 * The public setProps() function is responsible for extending the widget class
 * with properties so they can be used during later updates. After extending the
 * widget, the private _setProps() function is called. In some cases, the public
 * setProps() function may be overridden. For example, the label clears the
 * contents property from the widget because that is something we do not want to
 * extend.
 * <p></p>
 * The startup() function is typically called after the widget has been 
 * instantiated. For example, a progressBar might start a timer to periodically
 * refresh. 
 * <p></p>
 * Warning: It's not possible to append HTML elements from script that is 
 * not a direct child of the BODY element. If there is any Javascript
 * running inside the body that is a direct child of body, IE will throw
 * an "Internet Explorer cannot open the Internet site" error. For example,
 * webui.@THEME@.dijit._Templated._createNodesFromText generates such an error by calling
 * appendChild(). Therefore, widget creation must be deferred to the
 * window.onLoad event. See http://trac.dojotoolkit.org/ticket/4631
 * </p>
 * @static
 */
webui.@THEME@.dojo.declare("webui.@THEME@.widget.widgetBase", webui.@THEME@.widget.eventBase, {
    // Note: If your class contains arrays or other objects, they should be
    // declared in the constructor function so that each instance gets it's own
    // copy. Simple types (literal strings and numbers) are fine to declare in 
    // the class directly.

    // Set defaults.
    common: webui.@THEME@.common, // Common utils.
    dojo: webui.@THEME@.dojo, // Dojo utils.
    prototypejs: webui.@THEME@.prototypejs, // Prototype utils.
    theme: webui.@THEME@.theme.common, // Theme utils.
    widget: webui.@THEME@.widget.common // Widget utils. 
});

/**
 * This function is used to render the widget from a template.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.widgetBase.prototype.buildRendering = function () {
    // Get default templates.
    if (this.templatePath == null && this.templateString == null) {
        this.templatePath = this.widget.getTemplatePath(this.widgetName);
        this.templateString = this.widget.getTemplateString(this.widgetName);
    }

    // The templatePath should have precedence. Therefore, in order for the 
    // templatePath to be used, templateString must be null.
    if (this.templatePath != null) {
        this.templateString = null;
    }

    // Template must be set prior to calling "superclass".
    return this.inherited("buildRendering", arguments);
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME@.widget.widgetBase.prototype.getClassName = function() {
    return this.className;
};

/**
 * This function is used to get common properties from the widget. Please see
 * the setCommonProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.widgetBase.prototype.getCommonProps = function() {
    var props = {};

    // Set properties.
    if (this.accessKey) { props.accessKey = this.accessKey; }
    if (this.dir) { props.dir = this.dir; }
    if (this.lang) { props.lang = this.lang; }
    if (this.tabIndex) { props.tabIndex = this.tabIndex; }
    if (this.title) { props.title = this.title; }

    return props;
};

/**
 * This function is used to get core properties from the widget. Please see
 * the setCoreProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.widgetBase.prototype.getCoreProps = function() {
    var props = {};

    // Set properties.
    if (this.className) { props.className = this.className; }
    if (this.id) { props.id = this.id; }
    if (this.style) { props.style = this.style; }
    if (this.visible != null) { props.visible = this.visible; }

    return props;
};

/**
 * This function is used to get event properties from the widget. Please
 * see the setEventProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.widgetBase.prototype.getEventProps = function() {
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
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME@.widget.widgetBase.prototype.getProps = function() {
    var props = {};

    // Set properties.
    this.prototypejs.extend(props, this.getCommonProps(), false);
    this.prototypejs.extend(props, this.getCoreProps(), false);
    this.prototypejs.extend(props, this.getEventProps(), false);

    return props;
};

/**
 * This function is used to test if widget has been initialized.
 * <p>
 * Note: It is assumed that an HTML element is used as a place holder for the
 * document fragment.
 * </p>
 * @return {boolean} true if widget is initialized.
 */
webui.@THEME@.widget.widgetBase.prototype.isInitialized = function() {
    // Testing if the outermost DOM node has been added to the document and
    // ensuring a Dojo attach point exists works fine for JSP. However, the 
    // following code always returns null for facelets.
    //
    // var domNode = document.getElementById(this.id);
    // if (domNode && domNode.getAttribute("dojoattachpoint")) {
    if (this.initialized == true) {
        return true;
    }
    return false;
};

/**
 * This is called after the buildRendering() function.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet.
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.widgetBase.prototype.postCreate = function () {
    this.inherited("postCreate", arguments);

    // In order to register widgets properly, the DOM node id must be set prior 
    // to creating any widget children. Otherwise, widgets may not be destroyed.
    this.domNode.id = this.id;

    // Since the anchor id and name must be the same on IE, we cannot obtain the
    // widget using the DOM node ID via the public functions below. Therefore, 
    // we need to set the widget id via closure magic.
    var _id = this.id;

    // Set public functions.
    this.domNode.getProps = function() { return webui.@THEME@.dijit.byId(_id).getProps(); };
    this.domNode.setProps = function(props, notify) { return webui.@THEME@.dijit.byId(_id).setProps(props, notify); };

    // Initialize public events and functions.
    this.initEvents();

    // Set properties.
    this._setProps(this.getProps());

    // All widget properties have been set.
    return this.initialized = true;
};

/**
 * This function is used to set common properties for the given domNode.
 *
 * @param {Node} domNode The DOM node to assign properties to.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accessKey Shortcut key.
 * @config {String} dir Specifies the directionality of text.
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.widgetBase.prototype.setCommonProps = function(domNode, props) {
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
};

/**
 * This function is used to set core properties for the given domNode. These
 * properties are typically set on the outermost element.
 *
 * Note: The className is typically provided by a web app developer. If 
 * the widget has a default className, it should be added to the DOM node
 * prior to calling this function. For example, the "myCSS" className would
 * be appended to the existing "Tblsun4" className (e.g., "Tbl_sun4 myCSS").
 *
 * @param {Node} domNode The DOM node to assign properties to.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} style Specify style rules inline.
 * @config {boolean} visible Hide or show element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.widgetBase.prototype.setCoreProps = function(domNode, props) {
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
        this.common.setVisibleElement(domNode, 
            new Boolean(props.visible).valueOf());
    }
    return true;
};

/**
 * This function is used to set event properties for the given domNode.
 *
 * @param {Node} domNode The DOM node to assign properties to.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} onBlur Element lost focus.
 * @config {String} onChange Element value changed.
 * @config {String} onClick Mouse button is clicked on element.
 * @config {String} onDblClick Mouse button is double-clicked on element.
 * @config {String} onFocus Element received focus.
 * @config {String} onKeyDown Key is pressed down over element.
 * @config {String} onKeyPress Key is pressed and released over element.
 * @config {String} onKeyUp Key is released over element.
 * @config {String} onMouseDown Mouse button is pressed over element.
 * @config {String} onMouseOut Mouse is moved away from element.
 * @config {String} onMouseOver Mouse is moved onto element.
 * @config {String} onMouseUp Mouse button is released over element.
 * @config {String} onMouseMove Mouse is moved while over element.
 * @config {String} onSelect Element text selected.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.widgetBase.prototype.setEventProps = function(domNode, props) {
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
};

/**
 * This function is used to set widget properties.
 *
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * Note: If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} style Specify style rules inline.
 * @config {boolean} visible Hide or show element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.widgetBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Extend widget object for later updates.
    this.prototypejs.extend(this, props);

    // Set properties.
    this._setProps(props);

    // Notify listeners state has changed.
    if (new Boolean(notify).valueOf() == true &&
            typeof this.stateChanged == "function") {
        this.stateChanged(props);
    }
    return true;
};

/**
 * This function is used to set widget properties. Please see the setProps() 
 * function for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME@.widget.widgetBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set style class -- must be set before calling setCoreProps().
    props.className = this.getClassName();

    // Set more properties.
    return this.setCoreProps(this.domNode, props);
};

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME@.widget.widgetBase.prototype.startup = function () {
    if (this._started) {
        return false;
    }
    this.inherited("startup", arguments);
    return this._started = true;
};
