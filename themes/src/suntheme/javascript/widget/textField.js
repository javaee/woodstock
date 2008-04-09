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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.textField");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.fieldBase");

/**
 * This function is used to construct a textField widget.
 *
 * @name webui.@THEME_JS@.widget.textField
 * @extends webui.@THEME_JS@.widget._base.fieldBase
 * @class This class contains functions for the textField widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} accesskey 
 * @config {boolean} autoValidate
 * @config {String} className CSS selector.
 * @config {String} dir Specifies the directionality of text.
 * @config {boolean} disabled Disable element.
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} label
 * @config {String} lang Specifies the language of attribute values and content.
 * @config {int} maxLength 
 * @config {Array} notify 
 * @config {String} onBlur Element lost focus.
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
 * @config {boolean} readOnly 
 * @config {boolean} required 
 * @config {int} size 
 * @config {String} style Specify style rules inline.
 * @config {boolean} submitForm
 * @config {int} tabIndex Position in tabbing order.
 * @config {String} title Provides a title for element.
 * @config {boolean} valid
 * @config {String} value Value of input.
 * @config {boolean} visible Hide or show element.
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.textField",
        webui.@THEME_JS@.widget._base.fieldBase, {
    // Set defaults.
    constructor: function() {
        // Array of list values; may be empty; if null - then no autocomplete 
        // functionality is provided
        this.autoCompleteOptions = null; 
        this.autoCompleteSize = 15;
        this.autoCompleteCloseDelayTime = 100;
    },                  
    _widgetType: "textField" // Required for theme properties.
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
webui.@THEME_JS@.widget.textField.event =
        webui.@THEME_JS@.widget.textField.prototype.event = {
  /**
   * This object contains filter event topics.
   * @ignore
   */
    autoComplete: {
        /** Filter event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textField_event_autoComplete_begin",

        /** Filter event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textField_event_autoComplete_end"
    },
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textField_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textField_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textField_event_submit_end"
    },

    /**
     * This object contains validation event topics.
     * @ignore
     */
    validation: {
        /** Validation event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_textField_event_validation_begin",

        /** Validation event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_textField_event_validation_end"
    }
};

/**
 * Utility function to adjust input and list widths to match
 * the one of surrounding domNode node    
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._adjustListGeometry = function () {
    this._listContainer.style.width = this._fieldNode.offsetWidth;
    this._listContainer.style.left = this._fieldNode.offsetLeft;
    this._listContainer.style.top = this._fieldNode.offsetTop + this._fieldNode.offsetHeight;
    this._listNode.style.width = this._fieldNode.offsetWidth;
    this._listContainer.style.zIndex = "999";
    return true;
};

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._getInputClassName = function() {          
    // Set readOnly style.
    if (this._fieldNode.readOnly) {
        return this._theme._getClassName("TEXT_FIELD_READONLY", "");
    }

    // Apply invalid style.
    var validStyle =  (this.valid == false) 
        ? " " + this._theme._getClassName("TEXT_FIELD_INVALID", "")
        : " " + this._theme._getClassName("TEXT_FIELD_VALID", "");
    
    // Set default style.    
    return (this.disabled == true)
        ? this._theme._getClassName("TEXT_FIELD_DISABLED", "") 
        : this._theme._getClassName("TEXT_FIELD", "") + validStyle;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.textField.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.autoValidate != null) { props.autoValidate = this.autoValidate; }
    if (this.autoComplete != null) { props.autoComplete = this.autoComplete; } 
    if (this.autoCompleteOptions != null) { props.autoCompleteOptions = this.autoCompleteOptions; } //TODO clone array?
        
    return props;
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
webui.@THEME_JS@.widget.textField.prototype._postCreate = function () {
    // Set events.
    if (this.autoValidate == true) {
        // Generate the following event ONLY when 'autoValidate' == true.
        this._dojo.connect(this._fieldNode, "onblur", this, "_validate");
    }
    return this._inherited("_postCreate", arguments);
};

/**
 * Helper function to create callback for autoComplete list closing event.
 *
 * @return {Function} The callback function.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._createCloseListCallback = function() {
    var _id = this.id;
    return function(event) { 
        var widget = webui.@THEME_JS@.widget.common.getWidget(_id);
        if (widget == null) {
            return false;
        }
        widget.showAutoComplete = false;
        widget._updateListView();
        return true;
    };
};

/**
 * Publishes event to filter the options list according to specified 
 * filter string. Note that in default implementation such options will be 
 * updated remotely by Ajax call and then are automatically refreshed on the page.
 *
 * @param {String} filter
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._filterOptions = function() {    
    // Publish the event for custom AJAX implementations to listen for.
    // The implementation of this Ajax call will retrieve the value of the filter
    // and will obtain an updated lookup list ( either locally, or by submit to the server)
    // Data returned from Ajax call will be pushed into this._listWidget 
    //
    // @see javascript.widget.jsfx.autoComplete for default Ajax implementation
    this._publish(webui.@THEME_JS@.widget.textField.event.autoComplete.beginTopic, [{
        id: this.id
    }]);
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
webui.@THEME_JS@.widget.textField.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }
    
    //we can receive updated autoComplete flag from ajax call back
    if (props.autoComplete != null) { this.autoComplete = new Boolean(props.autoComplete).valueOf();  }
    
    //initialize list, if required    
    //autocomplete list may be initiated only on widget start, and cannot 
    //be introduced as part of dynamic options on setProps
    if (this.autoComplete && this._listWidget == null) {
        //create and populate props for listbox
        var listWidgetProps = {
           id: this.id + "_list",
           onFocus: "webui.@THEME_JS@.widget.common.getWidget('" + this.id + 
                "')._processFocusEvent(this.event);", 
           onBlur: "webui.@THEME_JS@.widget.common.getWidget('" + this.id + 
                "')._processBlurEvent(this.event);",
           widgetType: "listbox"
        };

        //?? use of event registration as in following disables field processing keys 
        //onChange: "webui.@THEME_JS@.widget.common.getWidget('" + this.id + "')._processListChange(this.event);"

        // Fix: Properties should be initialized via postCreate. Then, the list
        // can be updated here instead of recreating the widget each time.
        this._widget._addFragment(this._listContainer, listWidgetProps);
        
        //store reference to the list
        this._listWidget = this._widget.getWidget(listWidgetProps.id);
        this._listNode = this._listWidget.getSelectElement();
        
        //since original list box is created empty, make sure it is not shown
        this._updateListView();
  
         //disable browser autocomplete
         
         //we assume here that once the field is armed for autocomplete,
         //this mode will not get disabled. Thus there is no logic provided
         //for restoration of "autocomplete" attribute
         //
        this._fieldNode.setAttribute("autocomplete", "off");
        
        //use focus event to open the list
        this._dojo.connect(this._fieldNode, "onfocus", this, "_processFocusEvent");
 
        //use blur events to close the list
        this._dojo.connect(this._fieldNode, "onblur", this, "_processBlurEvent");        
         
        // onChange event of the list will change the content of the input field
        this._dojo.connect(this._listNode, "onchange", this, "_processListChange");
 
        //onclick will allow to reopen options list after it has been closed with ESC
        this._dojo.connect(this._fieldNode, "onclick", this, "_processFocusEvent");

        // input field changes will trigger updates to the autoComplete list options
        this._dojo.connect(this._fieldNode, "onkeyup", this, "_processFieldKeyUpEvent");
        
        //additional logic applied to ENTER, ESCAPE, ARROWs on keydown in order to cancel the event bubble
        this._dojo.connect(this._fieldNode, "onkeydown", this, "_processFieldKeyDownEvent");
    }        
    
    if (this.autoComplete && props.autoCompleteOptions != null && this._listWidget != null ) {
        //autoComplete param has changed
        
        //we can receive new options from ajax call
        this.autoCompleteOptions = props.autoCompleteOptions;
        
        // Push properties into listbox.  
        var listProps = {};
        listProps.options = props.autoCompleteOptions;
        
        //change list box size up to autoCompleteSize elem
        if (this.autoCompleteOptions.length > this.autoCompleteSize) {
            listProps.size = this.autoCompleteSize;
        } else if (this.autoCompleteOptions.length == 1) {
            //provide extra space for one-line
            listProps.size = 2;
        } else {
            listProps.size = this.autoCompleteOptions.length;
        }

        this._listWidget.setProps(listProps);
        
        /* // display list on initiation
        this.showAutoComplete = true;        
        */
        this._updateListView();
    }   

    // Set remaining properties.
    var ret = this._inherited("_setProps", arguments);
    
    if (props.autoComplete && props.autoCompleteOptions != null && this._listWidget != null ) {
        this._adjustListGeometry();  //even if autocomplete options are not defined in this set of props
    }
    return ret;
};

/**
 * Process the blur  event - activate delayed timer to close the list.
 * Such timer will be canceled if either list of text field receive 
 * the focus back within a delay period.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._processBlurEvent = function(event) {    
    //clear timeout for list closing, thereby preventing list from being closed    
    if (this.closingTimerId) {
        clearTimeout(this.closingTimerId);
        this.closingTimerId = null;
    }
    this.closingTimerId = setTimeout( 
        this._createCloseListCallback(), this.autoCompleteCloseDelayTime);   

    return true;
};

/**
 * Process keyPress events on the field, which enforces/disables 
 * submitForm behavior.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._processFieldKeyDownEvent = function(event) {
    event = this._widget._getEvent(event);
    if (event == null) {
        return false;
    }

    if (event.keyCode == this._widget._keyCodes.ENTER && this.autoCompleteIsOpen) {               
        // Disable form submission. Note that form submission is disabled
        // only once when listbox is open. If it closed, form will be submitted
        // according to submitForm flag
        if (window.event) {
            event.cancelBubble = true;
            event.returnValue = false;
        } else{
            event.preventDefault();
            event.stopPropagation();
        } 
        this._fieldNode.value = this._listWidget.getSelectedValue();

        //force close the list box
        this.showAutoComplete = false;
        this._updateListView();    
        
        return true;
    }   

    //close the list in case of ESCAPE pressed
    if (event.keyCode == this._widget._keyCodes.ESCAPE  ) {               
        this._fieldNode.value = ""; //Warning: IE empties all fields on the page on 2nd ESC independently of setting value here
        this.showAutoComplete = false;
        this._updateListView();     
        this._filterOptions();
        return true;
    }
      
    //even when the text field is in focus, we want arrow keys ( up and down)
    //navigate through options in the select list
    if (event.keyCode == this._widget._keyCodes.DOWN_ARROW 
            && this._listWidget.getSelectedIndex() < this._listNode.options.length) {               
        try {
            this.showAutoComplete = true;

            if (!this.autoCompleteIsOpen || this._listNode.options.length <=1) {
                this._filterOptions();
                this._listWidget.setSelectedIndex(0) ;
            } else {     
                //list already open
                this._listWidget.setSelectedIndex(this._listWidget.getSelectedIndex() + 1) ;
                this._updateListView();
            }
            this._processListChange(event);
            return true;
       } catch (doNothing) {}
    }
    if (event.keyCode == this._widget._keyCodes.UP_ARROW && this._listWidget.getSelectedIndex() > 0) {               
        try {
            this.showAutoComplete = true;
            this._listWidget.setSelectedIndex(this._listWidget.getSelectedIndex() - 1) ;
            this._processListChange(event);
            return true;
        } catch (doNothing) {}
    }
    return true;
};

/**
 * Process keyPress events on the filter, which chages the filter
 * and submits a request for updated list.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._processFieldKeyUpEvent = function(event) {    
    event = this._widget._getEvent(event);

    if (event != null &&
        ( event.keyCode == this._widget._keyCodes.ESCAPE ||
          event.keyCode == this._widget._keyCodes.ENTER ||
          event.keyCode == this._widget._keyCodes.DOWN_ARROW ||
          event.keyCode == this._widget._keyCodes.UP_ARROW  ||
          event.keyCode == this._widget._keyCodes.SHIFT  ||
          event.keyCode == this._widget._keyCodes.TAB
        )) {
        //these special keys are processed somewhere else - no filtering
        return false; 
    }
    this.showAutoComplete = true;
    this._filterOptions();
    return true;
};

/**
 * Process the focus event - turn on the flag for autoComplete list display
 * <p>
 * Displays autoComplete box on focus 
 * </p>
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._processFocusEvent = function(event) {
    //clear timeout for list closing, thereby preventing list from being closed
    if (this.closingTimerId) {
        clearTimeout(this.closingTimerId);
        this.closingTimerId = null;
    }
    return true;
};

/**
 * Process onChange event on the select list, which results in filter being
 * updated with the new value. Note that no data submission is initiated here.
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._processListChange = function(event) {    
    event = this._widget._getEvent(event);

    if (event.type == "change") {               
        try {
            this._fieldNode.value = this._listWidget.getSelectedValue();
            
            //close the list
            this.showAutoComplete = false;
            this._updateListView();  
            this._fieldNode.focus(); 
                           
            return true;
       } catch (doNothing) {}
    }    

    // else - usually from selection movement with the arrow keys 
    this._fieldNode.value = this._listWidget.getSelectedValue();
    this._fieldNode.focus(); //keep the focus on filter field so that user can incrementally type additional data
    return true;
};

/**
 * Helper function to update the view of the component ( what is commonly known
 * in UI world as /refresh/ - but renamed not to be confused with Ajax refresh)
 *  - if size of the list box >=1, shows autocomplete list box.
 *  - if size of the list box <1, hides autocomplete list box.
 * 
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._updateListView = function() {
    if ( this.showAutoComplete == true && this.autoCompleteOptions.length >= 1) {
        //TODO a place for optimization here - not to adjust geometry each time
        this._adjustListGeometry();    

        //optionally we could add check for this._listNode.options.length >0
        this._listNode.className = this._theme._getClassName("TEXT_FIELD_AUTO_COMPLETE_LIST", "");

        // the following is preferred way of setting class, but it does not work
        //this._listWidget.setProps({className: this._theme._getClassName("TEXT_FIELD_AUTO_COMPLETE_LIST", "")}) ;
        
        //this.autoCompleteIsOpen flag indicates whether list box is open or not
        this.autoCompleteIsOpen = true;
    } else {
        this._listNode.className = this._theme._getClassName("HIDDEN");
        //this._listWidget.setProps(visible: 'false');
        this.autoCompleteIsOpen = false;
    }
    return true;
};

/**
 * Process validation event.
 * <p>
 * This function interprets an event (one of onXXX events, such as onBlur,
 * etc) and extracts data needed for subsequent Ajax request generation. 
 * Specifically, the widget id that has generated the event. If widget
 * id is found, publishBeginEvent is called with extracted data.
 * </p>
 *
 * @param {Event} event The JavaScript event.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.textField.prototype._validate = function(event) {
    // Publish an event for custom AJAX implementations to listen for.
    this._publish(webui.@THEME_JS@.widget.textField.event.validation.beginTopic, [{
        id: this.id
    }]);
    return true;
};
