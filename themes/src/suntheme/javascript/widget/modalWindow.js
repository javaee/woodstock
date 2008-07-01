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

@JS_NS@._dojo.provide("@JS_NS@.widget.modalWindow");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");

/**
 * This function is used to construct a modalWindow widget.
 *
 * @constructor
 * @name @JS_NS@.widget.modalWindow
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @class This class contains functions for the modalWindow widget.
 * <p>ModalWindow is a popup window that is displayed over an optional mask overlay.
 * By putting an overlay over the whole page, ModalWindow disables user's mouse interaction
 * with the rest of the page. It remains possible though that user may still able to interact 
 * with the underlying page using assigned or standard keyboard access keys. User can also
 * scroll the window to reveal different portions of the screen, while modalWindow continues to 
 * maintain its visible relative position on the page.
 * 
 * </p>
 * <p>
 * ModalWindow consists of the header, content and footer. <br>
 * Buttons: ModalWindow provides a set of default buttons: close, ok, cancel, apply. Default buttons 
 * can be specified by providing the following properties: buttonOk, buttonOk, 
 * buttonCancel, buttonApply. With the exception of the close button, the default is false ( do not show)
 * the default buttons. Close button located on the window title and is shown by default.
 * <p>
 * Also, any number of custom buttons can be created within the command container of the window, with
 * the use of custom closures.
 * </p>

 * </p>
 * <p>ModalWindow displays arbitrary content. Content can be provided as an arbitrary HTML fragment,
 * inclusion of remote content, another widget, or a combination (array) of all of the above.
 * ModalWindow can also delegate content creation to a javascript method.
 * ModalWindow will dynamically size up to accomodate all of the content up to a specified maxWidth
 * and maxHeight.
 * </p>
 * 
 * <p>When created, ModalWindow is invisible,and can be open with the use of public open method.
 * </p>
 * 
 * <p>Before ModalWindow closes ( exits), it allows to execute a pre-exit method. If such method
 * returns false, ModalWindow will NOT be closed. Any other than false return will allow window to close
 * and the return will be passed as an exit code in close event. 
 * <ul>
 * <li>Any button can have a pre-exit method associated with its onClick event ( no additional args ).
 * Such method will be called prior to the exit. 
 * <ul>
 * <li>Custom actions can provide an onClick handler as part of action closure. 
 * <li>Standard buttons are equiped with the default close handler, or can provide their own with 
 * property [buttonName]ButtonOnClick ( okButtonOnClick, applyButtonOnClick, closeButtonOnClick, cancelButtonOnClick).
 * If no onClick handler has been provided for the button,
 * a default handler will simply close the window and publish a correposnding [buttonName]OnClickEvent.
 * If custom handler is provided, window will be closed, UNLESS the return value is false.
           <code>
            "closeButtonOnClick" : "return 888;",

            "actions" : [ {
                    id: "btn1",
                    value: "Close",  
                    onClick : "doSomething(); return true; "
                }   
            ]
          </code>
 * </ul>
 * </p>
 * <p>Events:<br>
 * ModalWindow publishes the following events:
 * <ul>
 * <li>@JS_NS@.widget.modalWindow.event.refresh.beginTopic - when dynamic refresh is requested
 * <li>@JS_NS@.widget.modalWindow.event.refresh.endTopic - when dyncamic refresh is complete
 * <li>@JS_NS@.widget.modalWindow.event.open - when window is open
 * <li>@JS_NS@.widget.modalWindow.event.close - when window is closed
 * </ul>
 * </p>
 * <p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "mw1", *       
 *       widgetType: "modalWindow"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre>
 * 
 * <h3>Example 2: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. The modalWindow is asynchronously updated with new data when 
 * updateWidget() method is executed.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "mw1", *       
 *       widgetType: "modalWindow"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * ...
 *   &lt;script type="text/javascript">
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("mw1"); // Get modalWindow
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * </code></pre>
 * 
 * 
 * 
 * <h3>Example 3: Reuse widget instance to open different windows</h3>
 * </p><p>
 * This example shows how to reuse a single instance of the modalWindow with 
 * different properties over and over for different windows to be rendered.
 * It intstantiates the widget with minimal set of properties first, and then
 * uses public method to open windows for another set of properties.
 * 
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "mw1", *       
 *       widgetType: "modalWindow"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * ...
 *   &lt;script type="text/javascript">
 *     function openWindow1() {
 *       var widget = @JS_NS@.widget.common.getWidget("mw1"); // Get modalWindow
 *       //create property with standard Ok and Cancel buttons
 *        var propsDialogDefaultButtons = {
 *                        "widgetType" :"modalWindow",
 *                        "contents" :  [{html:"This window has no close button. It can be closed programmatically only."}],
 *                        "closeButton" : false //render no close button
 *                        };
 *        widget.open(propsDialogDefaultButtons);    
 *     }
 *     
 *     // openWindow2 opens window with one 'Close' button in action container
 *     function openWindow2() {
 *       var widget = @JS_NS@.widget.common.getWidget("mw1"); // Get modalWindow
 *       //create property with standard Ok and Cancel buttons
          var propsTooBigImage= {
                "title": "System Not Available",
                "widgetType" :"modalWindow",
                "contents" :  [{html:"Too big of an image: <br><img src='./images/mona-big.jpg'/>"}],
                "actions": [{
                        "id": "btn1",
                        "value": "Close"
                    }]
            };
 *        widget.open(propsTooBigImage);    
 *     }
   
 *     &lt;/script>
 * </code></pre>
 * 
 * 
 * 
 * <p>
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} maskColor color of the mask
 * @config {String} maskOpacity mask opacity
 * @config {String} top offset from the top, including units
 * @config {String} left offset from the left, including units; or "auto"
 * @config {String} maxWidth maximium width of the window contents including units, such as "200px"
 * @config {String} maxHeight maximium height of the window contents including units, such as "200px"
 * @config {String} title window title, or null for none
 * @config {Array} contents array of contents, or null for none
 * @config {Boolean} okButton true  or false to render default window ok button ( false by default)
 * @config {String} okButtonOnClick handler for ok button onClick event
 * @config {Boolean} applyButton true  or false to render default window apply button ( false by default)
 * @config {String} applyButtonOnClick handler for apply button onClick event
 * @config {Boolean} cancelButton true  or false to render default window cancel button ( false by default)
 * @config {String} cancelButtonOnClick handler for cancel button onClick event
 * @config {Boolean} closeButton true  or false to render default window close button ( true by default)
 * @config {String} closeButtonOnClick handler for close button onClick event
 * @config {Array} actions list of buttons descriptions
 * @config {boolean} visible Hide or show element.
 *
 */
@JS_NS@._dojo.declare("@JS_NS@.widget.modalWindow", [
    @JS_NS@.widget._base.widgetBase,
    @JS_NS@.widget._base.refreshBase
], {
    // Set defaults.
    constructor: function() {
        this.closeButton =  true; 
        this.okButton =  false; 
        this.applyButton =  false; 
        this.cancelButton =  false; 
        this.actions = null;
        this.contents = null;
        this.left = "auto";
        this.mask = true;
        this.maxWidth = "1000px";
        this.maxHeight = "1000px";
        this.top = "100px";
        this.title = ""; // null disables title bar, empty string display bar with no title     
    },                  
    _widgetType: "modalWindow" // Required for theme properties.
});



/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
@JS_NS@.widget.modalWindow.event =
    @JS_NS@.widget.modalWindow.prototype.event = {

    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} execute Comma separated list of IDs to be processed server
         * side along with this widget.
         * </li><li>
         * {String} id The widget ID to process the event for.
         * </li></ul>
         *
         * @id @JS_NS@.widget.modalWindow.event.refresh.beginTopic
         * @property {String} beginTopic
         */
        beginTopic: "@JS_NS@_widget_modalWindow_event_refresh_begin",

        /** 
         * Refresh event topic for custom AJAX implementations to listen for.
         * Key-Value pairs of properties to publish include:
         * <ul><li>
         * {String} id The widget ID to process the event for.
         * </li><li>
         * Please see the constructor detail for additional properties.
         * </li></ul>
         *
         * @id @JS_NS@.widget.modalWindow.event.refresh.endTopic
         * @property {String} endTopic
         */
        endTopic: "@JS_NS@_widget_modalWindow_event_refresh_end"
    },


    /**
     * okButtonOnClick event is publised when default ok button is clicked.
     * 
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     */
    okButtonOnClick: "@JS_NS@_widget_modalwindow_ok_clicked",

    /**
     * cancelButtonOnClick event is publised when default cancel button is clicked.
     * 
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     */
    cancelButtonOnClick: "@JS_NS@_widget_modalwindow_cancel_clicked",

    /**
     * applyButtonOnClick event is publised when default apply button is clicked.
     * 
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     */
    applyButtonOnClick: "@JS_NS@_widget_modalwindow_apply_clicked",

    /**
     * closeButtonOnClick event is publised when default close button is clicked.
     * 
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     */
    closeButtonOnClick: "@JS_NS@_widget_modalwindow_close_clicked",



    /**
     * Open event is publised when window is open.
     * 
     * State event topic for custom AJAX implementations to listen for.
     * Key-Value pairs of properties to publish include:
     * <ul><li>
     * {String} id The widget ID to process the event for.
     * </li></ul>
     *
     */
    open: "@JS_NS@_widget_modalwindow_event_open",

    /**
     * Close event is publised after window is closed.
     * 
     * State event topic for custom AJAX implementations to listen for.
     * Key-Value pairs of properties to publish include:
     * <ul>
     * <li>{String} id The widget ID to process the event for.
     * </ul>
     *
     */
    close: "@JS_NS@_widget_modalwindow_event_close"


};


/**
 * This function is used to fill in remaining template properties, after the
 * _buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.modalWindow.prototype._postCreate = function () {


    // Set public functions.

    /** @ignore */
    this._domNode.close = function(preExitAction) { return @JS_NS@.widget.common.getWidget(this.id).close(preExitAction); };
    /** @ignore */
    this._domNode.open = function(props) { return @JS_NS@.widget.common.getWidget(this.id).open(props); };

    // Set events.

    // The escape key should also close modalWindow.
    //this.keyConnection =  this._dojo.connect(document, "onkeydown", this, this._createKeyDownCallback());   
    
    return this._inherited("_postCreate", arguments);
};



/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.modalWindow.prototype.getProps = function() {
    
    var props = this._inherited("getProps", arguments);
     
    // Set properties.  
    if ( this.closeButton != null) { props.closeButton = this.closeButton; }
    if ( this.okButton != null) { props.okButton = this.okButton; }
    if ( this.cancelButton != null) { props.cancelButton = this.cancelButton; }
    if ( this.applyButton != null) { props.applyButton = this.applyButton; }
    
    if ( this.closeButtonOnClick != null) { props.closeButtonOnClick = this.closeButtonOnClick; }
    if ( this.okButtonOnClick != null) { props.okButtonOnClick = this.okButtonOnClick; }
    if ( this.cancelButtonOnClick != null) { props.cancelButtonOnClick = this.cancelButtonOnClick; }
    if ( this.applyButtonOnClick != null) { props.applyButtonOnClick = this.applyButtonOnClick; }

    if ( this.actions != null) { props.actions = this.actions; }    
    if ( this.contents != null) { props.contents = this.contents; }
    if ( this.left != null) { props.left = this.left; }
    if ( this.mask != null) { props.mask = this.mask; }
    if ( this.maskColor != null) { props.maskColor = this.maskColor; }
    if ( this.maskOpacity != null) { props.maskOpacity = this.maskOpacity; }
    if ( this.maxWidth != null) { props.maxWidth = this.maxWidth; }
    if ( this.maxHeight != null) { props.maxHeight = this.maxHeight; }
    if ( this.top != null) { props.top = this.top; }
    if ( this.title != null) { props.title = this.title; }
     
    return props;
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
@JS_NS@.widget.modalWindow.prototype._setProps = function(props) {

    if (props == null) {
        return false;
    }

    if (props.mask != null) {
        this._setContainerVisible(this._overlay, props.mask);   
    } 
    if (props.maskColor) { this._overlay.style.backgroundColor = props.maskColor;}
    
    if (typeof props.maskOpacity == "number") { this._overlay.style.opacity = props.maskOpacity;}

    if (props.top) { 
        this._window.style.marginTop  = props.top;
        this.marginTop = props.top;
    }
    if (props.left) { 
        this._window.style.marginLeft  = props.left;
        this.marginLeft = props.left;
    }
    
    if (props.maxHeight) {
        this._window.style.maxHeight  = props.maxHeight;
        this.maxHeight = props.maxHeight;
        
    }

    if (props.maxWidth) {
        this._window.style.maxWidth  = props.maxWidth;
        this.maxWidth = props.maxWidth;
        
    }
    
   
    
    //subclasses can overwrite population of containers
    //body is populated first, as other containers may depend on its contents
    this._setContentsProps(props);   //i.e. contents
    this._setHeaderProps(props); //i.e. title, navigation, toolbar 
    this._setFooterProps(props); //i.e. command container


    //buttons can be added or removed dynamically. Connect/disconnect events appropriately
    this._updateEventsRegistration();
    
    // Set remaining properties.
    return this._inherited("_setProps", arguments);
 
};


 
/**
 * Sets the content of the modal window from the properties.
 * Each element of contents array can be either null | Object array | javascript method .
 * If Object array is provided, each fragement will be appended incrementally to the
 * innerHTML of the _contentContainer.<br>
 * If javascript method is provided, it will be called in order to populate the _contentContainer. Such 
 * method must be of signature:
 * <code>
    methodName(node, props)
 * </code>
 * where node passed will be the one of _contentContainer, and props is the pass through properties.
 * 
 * This method will remove the current content if provided contents is null or non-empty.
 * 
 * @param {Object} props Key-Value pairs of properties  ( see constructor for a list)
 * @return true if body is to be shown, false otherwise
 * @see constructor
 */
@JS_NS@.widget.modalWindow.prototype._setContentsProps = function(props)	{
    if (!props || typeof props.contents == "undefined") {
        return false;
    }                 
    
    this.contents = props.contents;
    
    var contents = props.contents;
                
    // Remove child nodes, even if contents is null
    this._widget._removeChildNodes(this._contentContainer);

    if (contents == null) {
        return true;
    }

    if (contents) {
       
        for (var i = 0; i < contents.length; i++) {
            if (typeof contents[i] == "function") {
                contents[i](this._contentContainer);
            } else {
                this._widget._addFragment(this._contentContainer, contents[i], "last");
            }
        }
        return true;
    }
    return false;
    
};


/** 
 *  Sets the content of the header.
 *  This implementation sets window title and window controls(close button).
 *  <br>
 *  The following properties are analyzed in this method:
 *  <ul>
 *  <li>title
 *  <li>closeButton
 *  <li>closeButtonOnClick
 *  </ul>
 *  
 * @param {Object} props Key-Value pairs of properties  
 * @return true if header is created, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype._setHeaderProps  = function(props)	{
          
    if (props.title == null) {
        //do not display titlebar
        this._setContainerVisible(this._windowTitleBar, false);
    } else {
        this._setContainerVisible(this._windowTitleBar, true);        
    }

    this._widget._addFragment(this._windowTitleText, props.title);
    
    
    if (props.closeButton != null) {
        if (props.closeButton) {
            //button has been requested
            this._setContainerVisible(this._closeButton, true);
            //default close behavior
            this.closeButtonAction = new Function("return -1;"); //default exit code for close button            
        } else  {
            //request to disable button
            this._setContainerVisible(this._closeButton, false);
            this.closeButtonAction = null;
        }
    }

    if (props.closeButtonOnClick != null) {  
        //get a function for  a string or reference
        this.closeButtonAction = this._getFunction(props.closeButtonOnClick) ;                
    } 
       
    
    return true;
};



/** 
 *  Sets the content of the footer.
 *  
 *  This implementation will delegate command container creation to the method
 *  _setActionsContainerProps, then delegate footer creation to the footer function.
 *  At the end, if no command container or footer have been populated, this method 
 *  will hide the footer.
 *  
 *  <br>The following properties are analyzed in this method:
 *  <ul>
 *  <li>footer
 *  <li>all actions container props related properties ( see _setActionsContainerProps)
 *  </ul>
 *  
 * @param {Object} props Key-Value pairs of properties  
 * @return true if footer is created, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype._setFooterProps  = function(props)	{
    if (!props) {
        return false;
    }
    
    var showFooter = false;
    
    showFooter = this._setActionsContainerProps(props);
    
    var footer = props.footer;
               
    if (footer != null) {
        this._widget._addFragment(this._footerContent, footer, "last");
        showFooter = true;
    }
        
    this._setContainerVisible(this._windowFooter, showFooter);
    return showFooter;
  
    
        
};

 
/* Populates actions container buttons.
 * All previous content is removed from the _actionContainer.
 * If default buttons have been requested, they will be rendered before
 * custom actions.
 * 
 *  <br>The following properties are analyzed in this method:
 *  <ul>
 *  <li>actions
 *  <li>default buttons: okButton, cancelButton, applyButton
 *  </ul>
 *  
 * @param {Object} props Key-Value pairs of properties  
 * @return true if command container has been created and needs to be displayed, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype._setActionsContainerProps = function(props)	{
    if (!props) {
        return false;
    }

    // Remove child nodes.
    this._widget._removeChildNodes(this._actionContainer);

    var showThis = false;
    
    showThis = this._renderDefaultButton(props, "okButton", true) || showThis;
    showThis = this._renderDefaultButton(props, "applyButton", false)  || showThis;
    showThis = this._renderDefaultButton(props, "cancelButton", false)  || showThis;
    
    //render custom actions
    if (props.actions != null) {       
        //anticipated an array of controls
        for (i = 0; i < props.actions.length; i++) {            
            this._widget._addFragment(this._actionContainer, props.actions[i], "last");            
        }
        showThis = true;
    }
    
    if (showThis) {
        this._setContainerVisible(this._actionContainer, true);
        return true;
    
    } else  {
        this._setContainerVisible(this._actionContainer, false);
        return false;
    }
    return true;
     
};

/** Renders default button. This method checks for presence of 
 * button flag and onclick handler ( buttonName , buttonNameOnClick) and render default button widget
 * @param props properties being set
 * @param buttonName button name
 * @param primary true or false
 * @return true if successful
 */
@JS_NS@.widget.modalWindow.prototype._renderDefaultButton  = function(props, buttonName, primary)	{

    if (!props[buttonName]) {  
        return false;
    } else {
        this[buttonName] = props[buttonName];                
    }

    //render default buttons
    if (props[buttonName + "OnClick"] != null) {  
        //if custom handler has been provided, use it as a pre-Exit action, without any extra logic
        this[buttonName +"OnClickAction"] = this._createActionClickCallback(props[buttonName + "OnClick"]);
            //this._getFunction(props[buttonName + "OnClick"]) ;                
    } else  {
        //default pre-Exit action will publish the event
        this[buttonName +"OnClickAction"] = this._createActionClickCallback(this._createDefaultAction(buttonName));
    }
    
    var buttonProps = {
        widgetType : "button",
        value      : this._theme.getMessage("ModalWindow." + buttonName), 
        primary    : primary,
        id         : this.id + "_"+ buttonName,    
        onClick    : this[buttonName +"OnClickAction"]
    };
    this._widget._addFragment(this._actionContainer, buttonProps, "last");            
    
    return true;


};


/** Cleans up event connections
 * @return true  
 */
@JS_NS@.widget.modalWindow.prototype._cleanupEvents  = function()	{

    if (this.closeButtonConnection != null) {
        woodstock._dojo.disconnect(this.closeButtonConnection);
        this.closeButtonConnection = null;
    }

    if (this.keyConnection != null) {
        woodstock._dojo.disconnect(this.keyConnection);
        this.keyConnection = null;
    }
    return true;
};




/**
 *  Opens up the modalWindow.
 *  <br>
 *  User can supply optional properties. If properties are supplied, 
 *  the setProps(props) will be called prior to opening the widget.
 *  This enables to keep only one instance of the widget and easily re-use
 *  it without calling setProps() every time. 
 *  <br>
 *  If the properties are not specified, widget will open with the previous set of
 *  properties.
 *  
 *  @see close()
 *  
 *  @return true if successful, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype.open  = function(props)	{
    if (props) {
        this.setProps(props);
    }

    this._setContainerVisible(this._domNode, true);
    
    // Publish a visible event 
    this._publish(@JS_NS@.widget.modalWindow.event.open, [{
            id: this.id
        }]);
    
    return true;
};
 



/** Closes the modalWindow.<br>
 *  If a preExitAction function is specified (typeof preExitAction == "function"), such function
 *  will be called and its return considered for exit decision. If return is false, 
 *  window will not exit. Otherwise, return will be passed with the visible.endTopic event as an exitCode.
 *  <br>
 *  If a preExitAction is NOT a function, it is considered to be an exit code.
 *  <br>
 *  If unconditionalExit is true, window will close no matter what return code of preExitAction is.
 *  <br>
 *  
 *      
 *  @param (Object) preExitAction pre-exit method that generates an exitCode
 *  @return true if window is closed, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype.close  = function(preExitAction)	{
    
    //check for BEFORE-EXIT action
    if (typeof preExitAction == "function") {
        try {
            var allowToExit = preExitAction();
            if (allowToExit == false ) {
                //do not allow to close
                return false;
            }
        } catch (err) {
            console.debug(err.toString());
        }
    }
    
    this._setContainerVisible(this._domNode, false);
    this._cleanupEvents();
    
    // Publish close event 
    @JS_NS@.widget.common.publish(@JS_NS@.widget.modalWindow.event.close, [{
            id: this.id,
            exitCode: exitCode
        }]);

    return true;
};
 




    
/*
 * Cleans up if neccessary and updates event registration according to the 
 * current set of widget properties
 * @return true if successful, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype._updateEventsRegistration  = function()	{
    this._cleanupEvents();
    
    if (this.closeButtonAction != null && this.closeButtonConnection == null) {
        // Generate the following event ONLY when closeButton is enabled
        var onClickFunc = this._createActionClickCallback(this.closeButtonAction );
        this.closeButtonConnection =  woodstock._dojo.connect(this._closeButton, "onclick", this, onClickFunc);
    }

    return true;
};


/**
 * Helper function to create callback for keydown event.
 *
 * @return {Function} The callback function.
 * @private
@JS_NS@.widget.modalWindow.prototype._createKeyDownCallback = function() {
    var _id = this.id;
    return function(event) { 
        //check event src
        
        var widget = @JS_NS@.widget.common.getWidget(_id);
        if (widget == null) {
            return false;
        }

        //check key
        if (event && event.keyCode == 27) {
            //simulate close as if triggered by close button click 
            //thus any event associated with the close button can be invoked
            widget.close(this.closeButtonAction);     
        }
        
        
        
        return true;
    };
};
*/


/** Creates a DEFAULT callback action to be assigned to the action or window controls buttons.
 *  Such callback will additionally publish an event.
 *  
 *  @param butName {String} name of the default button
 */
@JS_NS@.widget.modalWindow.prototype._createDefaultAction  = function(butName)	{
    if (butName != "okButton" && butName != "cancelButton" && butName != "applyButton" && butName != "closeButton") {
        return new Function("return true;"); //do nothing
    }
    var _id = this.id;
    
    return function() { 
        var widget = @JS_NS@.widget.common.getWidget(_id);
        if (widget == null) {
            return false;
        }
        widget._publish(@JS_NS@.widget.modalWindow.event[butName + "OnClick"], [{
          id: _id
        }]);
    
        return true;
    };
    
};
 

/** Creates a callback function to be assigned to the action or window controls buttons.
 *  Such callback will invoke close method.
 *  @param preExitAction {Function} optional method to invoke before window is closed
 */
@JS_NS@.widget.modalWindow.prototype._createActionClickCallback  = function(preExitAction)	{
    return this._widget._hitch(this, "close", this._getFunction(preExitAction));    
};
 
/** Utility method to show/hide a container (or any node)
 * @param (Object) node node to show/hide
 * @param (Boolean) visible true/false
 * @return if successfl, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype._setContainerVisible  = function(node, visible)	{
    if (!node) {
        return false;
    }
    node.style.display = (visible == true) ?  "block" : "none";    
    return true;
};


/** Utility method that returns referenceable function from existing function
 * or a string. If argument provided is a string, an anonymous function in format
 * function(event) is created, otherwise the original reference is returned.
 * 
 * @param (String or Function) func function 
 * @return if successfl, false otherwise
 */
@JS_NS@.widget.modalWindow.prototype._getFunction  = function(func)	{

    if (func != null) {
        return (typeof func == 'string')
            ? new Function("event", func)
        : func;
    }
};


