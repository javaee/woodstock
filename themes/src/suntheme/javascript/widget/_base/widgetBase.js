/**
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
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

@JS_NS@._dojo.provide("@JS_NS@.widget._base.widgetBase");

@JS_NS@._dojo.require("@JS_NS@._base.browser");
@JS_NS@._dojo.require("@JS_NS@._base.common");
@JS_NS@._dojo.require("@JS_NS@._base.proto");
@JS_NS@._dojo.require("@JS_NS@._dijit._Templated");
@JS_NS@._dojo.require("@JS_NS@._dijit._Widget"); 
@JS_NS@._dojo.require("@JS_NS@.theme.common");
@JS_NS@._dojo.require("@JS_NS@.widget.common");

/**
 * This function is used to construct a base class.
 *
 * @constructor
 * @name @JS_NS@.widget._base.widgetBase
 * @class This class contains functions used for base functionality in all 
 * widgets. 
 * <p>
 * The widgetBase class inherits from @JS_NS@._dijit._Widget and 
 * @JS_NS@._dijit._Templated. The @JS_NS@._dijit._Widget class
 * is responsible for calling the _buildRendering() and _postCreate() functions 
 * in that order. The dijit_Templated function overrides the _buildRendering() 
 * functon to fill in template properties.
 * <p></p>
 * The _postCreate() function is responible for initializing CSS selectors, 
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
 * _setCommonProps() and _setEventProps(). These properties may not always be
 * set on the outermost DOM node; however, core (i.e., id, class, style, etc.) 
 * properties are. Core properties are set on the DOM via the "superclass" 
 * function of widgetBase which invokes the _setCoreProps() function.
 * <p></p>
 * The _getClassName() function is responsible for obtaining the selector that
 * will be set on the outermost DOM node. The private _setProps() function 
 * of widgetBase ensures that the _getClassName() function is called prior to 
 * invoking _setCoreProps(). In most cases, this function will be overridded in
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
 * The start() function is typically called after the widget has been 
 * instantiated. For example, a progressBar might start a timer to periodically
 * refresh. 
 * <p></p>
 * Warning: It's not possible to append HTML elements from script that is 
 * not a direct child of the BODY element. If there is any Javascript
 * running inside the body that is a direct child of body, IE will throw
 * an "Internet Explorer cannot open the Internet site" error. For example,
 * @JS_NS@._dijit._Templated._createNodesFromText generates such an 
 * error by calling appendChild(). Therefore, widget creation must be deferred
 * to the window.onLoad event. See http://trac.dojotoolkit.org/ticket/4631
 * </p>
 */
@JS_NS@._dojo.declare("@JS_NS@.widget._base.widgetBase", [
        @JS_NS@._dijit._Widget, @JS_NS@._dijit._Templated ], {
    // Note: If your class contains arrays or other objects, they should be
    // declared in the constructor function so that each instance gets it's own
    // copy. Simple types (literal strings and numbers) are fine to declare in 
    // the class directly. Also note that superclass constructors are always 
    // called automatically, and always before the subclass constructor.
    constructor: function() {
        this._started = false;
        this._templateType = this._widgetType;
    },

    // Set defaults.
    _common: @JS_NS@._base.common, // Common utils.
    _dojo: @JS_NS@._dojo, // Dojo utils.
    _proto: @JS_NS@._base.proto, // Prototype utils.
    _theme: @JS_NS@.theme.common, // Theme utils.
    _widget: @JS_NS@.widget.common // Widget utils.
});

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@.widget._base.widgetBase.prototype.buildRendering = function () {
    // Template must be set prior to calling "superclass".
    this._buildRendering();
    return this._inherited("buildRendering", arguments);
};

/**
 * This function is used to render the widget from a template.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._buildRendering = function () {
    // Get default template path.
    if (this.templatePath == null) {
        this.templatePath = this._theme._getTemplatePath(this._templateType);
    }

    // Get default template string.
    if (this.templatePath == null && this.templateString == null) {
        var browser = @JS_NS@._base.browser;

        // Load browser specific template, if any.
        if (browser._isFirefox()) {
            this.templateString = this._theme._getTemplateString(
                this._widgetType + "_firefox");
        } else if (browser._isMozilla()) {
            this.templateString = this._theme._getTemplateString(
                this._widgetType + "_mozilla");
        } else if (browser._isIe()) {
            this.templateString = this._theme._getTemplateString(
                this._widgetType + "_ie");
        } else if (browser._isSafari()) {
            this.templateString = this._theme._getTemplateString(
                this._widgetType + "_safari");
        }
        // Get default template.
        if (this.templateString == null) {
            this.templateString = this._theme._getTemplateString(this._templateType);
        }
    }

    // The templatePath should have precedence. Therefore, in order for the 
    // templatePath to be used, templateString must be null.
    if (this.templatePath != null) {
        this.templateString = null;
    }
    return true;
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the 
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._getClassName = function() {
    // Save template selector, if any.
    if (typeof this._className == "undefined") {
        this._className = this._domNode.className;
    }
    // Append template selector to given className property.
    var newClassName = this.className;
    if (this.className && this._className) {
        newClassName += " " + this._className;
    } else if (this._className) {
        newClassName = this._className;
    }
    return newClassName;
};

/**
 * This function is used to get common properties from the widget. Please see
 * the _setCommonProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._getCommonProps = function() {
    var props = {};

    // Set properties.
    if (this.accessKey != null) { props.accessKey = this.accessKey; }
    if (this.dir != null) { props.dir = this.dir; }
    if (this.lang != null) { props.lang = this.lang; }
    if (this.tabIndex != null) { props.tabIndex = this.tabIndex; }
    if (this.title != null) { props.title = this.title; }

    return props;
};

/**
 * This function is used to get core properties from the widget. Please see
 * the _setCoreProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._getCoreProps = function() {
    var props = {};

    // Set properties.
    if (this.className != null) { props.className = this.className; }
    if (this.id != null) { props.id = this.id; }
    if (this.style != null) { props.style = this.style; }
    if (this.visible != null) { props.visible = this.visible; }

    return props;
};

/**
 * This function is used to get event properties from the widget. Please
 * see the _setEventProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._getEventProps = function() {
    var props = {};

    // Set properties.
    if (this.onBlur != null) { props.onBlur = this.onBlur; }
    if (this.onChange != null) { props.onChange = this.onChange; }
    if (this.onClick != null) { props.onClick = this.onClick; }
    if (this.onDblClick != null) { props.onDblClick = this.onDblClick; }
    if (this.onFocus != null) { props.onFocus = this.onFocus; }
    if (this.onKeyDown != null) { props.onKeyDown = this.onKeyDown; }
    if (this.onKeyPress != null) { props.onKeyPress = this.onKeyPress; }
    if (this.onKeyUp != null) { props.onKeyUp = this.onKeyUp; }
    if (this.onMouseDown != null) { props.onMouseDown = this.onMouseDown; }
    if (this.onMouseOut != null) { props.onMouseOut = this.onMouseOut; }
    if (this.onMouseOver != null) { props.onMouseOver = this.onMouseOver; }
    if (this.onMouseUp != null) { props.onMouseUp = this.onMouseUp; }
    if (this.onMouseMove != null) { props.onMouseMove = this.onMouseMove; }
    if (this.onSelect != null) { props.onSelect = this.onSelect; }

    return props;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @id @JS_NS@.widget._base.widgetBase.getProps
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget._base.widgetBase.prototype.getProps = function() {
    var props = {};

    // Set properties.
    this._proto._extend(props, this._getCommonProps(), false);
    this._proto._extend(props, this._getCoreProps(), false);
    this._proto._extend(props, this._getEventProps(), false);

    return props;
};

/**
 * The inherited function will climb up the scope chain, from superclass to 
 * superclass and through mixin classes as well, until it finds "someMethod",
 * then it will invoke that method.
 * <p>
 * Note: The argument is always literally arguments, a special Javascript array 
 * variable which holds all the arguments (like argv in C). There are a few 
 * variations to inherited() for special cases. If you have a method that was 
 * put into your object outside of declare, you need to specify the name of the
 * calling function like this:
 * </p><p><code>
 * this.inherited("someMethod", arguments);
 * </code></p><p>
 * You can also send custom parameters to the ancestor function. Just place the
 * extra arguments in array literal notation with brackets:
 * </p><p><code>
 * this.inherited(arguments, [ customArg1, customArg2 ]);
 * </code></p>
 *
 * @param {String} name The name of the inherited function.
 * @param {Object} args The function arguments.
 * @param {Object} newArgs Custom function arguments.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._inherited = function(name, args, newArgs) {
    return this.inherited(name, args, newArgs);
};

/**
 * This function is used to test if widget has been initialized.
 * <p>
 * Note: It is assumed that an HTML element is used as a place holder for the
 * document fragment.
 * </p>
 * @return {boolean} true if widget is initialized.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._isInitialized = function() {
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

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@.widget._base.widgetBase.prototype.postCreate = function () {
    return this._postCreate();
};

/**
 * This is called after the _buildRendering() function.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet.
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._postCreate = function () {
    // In order to register widgets properly, the DOM node id must be set prior 
    // to creating any widget children. Otherwise, widgets may not be destroyed.
    this._domNode.id = this.id;

    // Since the anchor id and name must be the same on IE, we cannot obtain the
    // widget using the DOM node ID via the public functions below. Therefore, 
    // we need to set the widget id via closure magic.
    var _id = this.id;

    // Set public functions.
    /** @ignore */
    this._domNode.getProps = function() { 
        return @JS_NS@.widget.common.getWidget(_id).getProps();
    };
    /** @ignore */
    this._domNode.setProps = function(props, notify) { 
        return @JS_NS@.widget.common.getWidget(_id).setProps(props, notify);
    };
    /** @ignore */
    this._domNode.subscribe = function(topic, obj, func) {
        return @JS_NS@.widget.common.subscribe(topic, obj, func);
    };

    // Initialize public functions.
    if (typeof this._initRefreshFunc == "function") {
        this._initRefreshFunc();
    }
    if (typeof this._initSubmitFunc == "function") {
        this._initSubmitFunc();
    }

    // Set event topics.
    this._domNode.event = this.event;

    // Set properties.
    this._setProps(this.getProps());

    // All widget properties have been set.
    return this.initialized = true;
};

/**
 * Publish an event topic.
 * <p>
 * Note: In order to obtain Ajax modules dynamically, this function shall be 
 * overridden by a custom AJAX implementation.
 * </p>
 * @param {String} topic The event topic to publish.
 * @param {Object} props Key-Value pairs of properties. This will be applied
 * to each topic subscriber.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._publish = function(topic, props) {
    // Obtain the Ajax module associated with this widget.
    var config = @JS_NS@._base.config;
    if (new Boolean(config.ajax.isAjax).valueOf() == true && config.ajax.module) {
        @JS_NS@._dojo.require(config.ajax.module + "." + this._widgetType);
    }
    return @JS_NS@.widget.common.publish(topic, props);
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
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._setCommonProps = function(domNode, props) {
    if (domNode == null || props == null) {
        return false;
    }
    if (props.accessKey != null) { 
        domNode.accessKey = props.accessKey;
    }
    if (props.dir != null) {
        domNode.dir = props.dir;
    }
    if (props.lang != null) {
        domNode.lang = props.lang;
    }
    if (props.tabIndex > -1 && props.tabIndex < 32767) {
        domNode.tabIndex = props.tabIndex;
    }
    if (props.title != null) {
        domNode.title = props.title;
    }
    return true;
};

/**
 * This function is used to set core properties for the given domNode. These
 * properties are typically set on the outermost element.
 * <p>
 * Note: The className is typically provided by a web app developer. If 
 * the widget has a default className, it should be added to the DOM node
 * prior to calling this function. For example, the "myCSS" className would
 * be appended to the existing "Tblsun4" className (e.g., "Tbl_sun4 myCSS").
 * </p>
 * @param {Node} domNode The DOM node to assign properties to.
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} className CSS selector.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} style Specify style rules inline.
 * @config {boolean} visible Hide or show element.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._setCoreProps = function(domNode, props) {
    if (domNode == null || props == null) {
        return false;
    }
    if (props.className != null) {
        domNode.className = props.className;
    }
    if (props.id != null) { 
        domNode.id = props.id;
    }
    if (props.style != null) { 
        domNode.style.cssText = props.style;
    }
    if (props.visible != null) {
        this._common._setVisibleElement(domNode, 
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
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._setEventProps = function(domNode, props) {
    if (domNode == null || props == null) {
        return false;
    }

    // Note: JSON strings are not recognized as JavaScript. In order for
    // events to work properly, an anonymous function must be created.
    if (props.onBlur != null) { 
        domNode.onblur = (typeof props.onBlur == 'string')
            ? new Function("event", props.onBlur)
            : props.onBlur;
    }
    if (props.onClick != null) {
        domNode.onclick = (typeof props.onClick == 'string')
            ? new Function("event", props.onClick)
            : props.onClick;
    }
    if (props.onChange != null) {
        domNode.onchange = (typeof props.onChange == 'string')
            ? new Function("event", props.onChange)
            : props.onChange;
    }
    if (props.onDblClick != null) {
        domNode.ondblclick = (typeof props.onDblClick == 'string')
            ? new Function("event", props.onDblClick)
            : props.onDblClick;
    }
    if (props.onFocus != null) {
        domNode.onfocus = (typeof props.onFocus == 'string')
            ? new Function("event", props.onFocus)
            : props.onFocus;
    }
    if (props.onKeyDown != null) {
        domNode.onkeydown = (typeof props.onKeyDown == 'string')
            ? new Function("event", props.onKeyDown)
            : props.onKeyDown;
    }
    if (props.onKeyPress != null) {
        domNode.onkeypress = (typeof props.onKeyPress == 'string')
            ? new Function("event", props.onKeyPress)
            : props.onKeyPress;
    }
    if (props.onKeyUp != null) {
        domNode.onkeyup = (typeof props.onKeyUp == 'string')
            ? new Function("event", props.onKeyUp)
            : props.onKeyUp;
    }
    if (props.onMouseDown != null) {
        domNode.onmousedown = (typeof props.onMouseDown == 'string')
            ? new Function("event", props.onMouseDown)
            : props.onMouseDown;
    }
    if (props.onMouseOut != null) {
        domNode.onmouseout = (typeof props.onMouseOut == 'string')
            ? new Function("event", props.onMouseOut)
            : props.onMouseOut;
    }
    if (props.onMouseOver != null) {
        domNode.onmouseover = (typeof props.onMouseOver == 'string')
            ? new Function("event", props.onMouseOver)
            : props.onMouseOver;
    }
    if (props.onMouseUp != null) {
        domNode.onmouseup = (typeof props.onMouseUp == 'string')
            ? new Function("event", props.onMouseUp)
            : props.onMouseUp;
    }
    if (props.onMouseMove != null) {
        domNode.onmousemove = (typeof props.onMouseMove == 'string')
            ? new Function("event", props.onMouseMove)
            : props.onMouseMove;
    }
    if (props.onSelect != null) {
        domNode.onselect = (typeof props.onSelect == 'string')
            ? new Function("event", props.onSelect)
            : props.onSelect;
    }
    return true;
};

/**
 * This function is used to set widget properties using Object literals. Please
 * see the constructor detail for a list of supported properties.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 * @id @JS_NS@.widget._base.widgetBase.setProps
 * @param {Object} props Key-Value pairs of properties.
 * @param {boolean} notify Publish an event for custom AJAX implementations to listen for.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.widgetBase.prototype.setProps = function(props, notify) {
    if (props == null) {
        return false;
    }

    // Extend widget object for later updates.
    this._proto._extend(this, props);

    // Create a clone which can be safely modified in order to update 
    // subwidgets more efficiently.
    var _props = {};
    this._proto._extend(_props, props);

    // Set properties.
    this._setProps(_props);

    // Notify listeners state has changed.
    if (new Boolean(notify).valueOf() == true &&
            typeof this._stateChanged == "function") {
        this._stateChanged(props);
    }
    return true;
};

/**
 * This function is used to set widget properties. Please see the constructor 
 * detail for a list of supported properties.
 * <p>
 * Note: This function should only be invoked through setProps().
 * </p>
 * @param {Object} props Key-Value pairs of properties.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget._base.widgetBase.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set style class -- must be set before calling _setCoreProps().
    props.className = this._getClassName();
    // The visible selector must be set otherwise, className may wipe it out.
    props.visible = (props.visible != null) ? props.visible : this.visible;

    // Set more properties.
    return this._setCoreProps(this._domNode, props);
};

// This function is not public and should not appear in the jsDoc.
/** @ignore */
@JS_NS@.widget._base.widgetBase.prototype.startup = function () {
    return this._startup();
};

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget._base.widgetBase.prototype._startup = function () {
    if (this._started == true) {
        return false;
    }
    return this._started = true;
};
