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

@JS_NS@._dojo.provide("@JS_NS@.widget.textField");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.fieldBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.submitBase");

/**
 * This function is used to construct a textField widget.
 *
 * @constructor
 * @name @JS_NS@.widget.textField
 * @extends @JS_NS@.widget._base.fieldBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
 * @extends @JS_NS@.widget._base.submitBase
 * @class This class contains functions for the textField widget.
 * <p>
 * The submitForm property can be used to alter the default browser behavior on
 * form submission when Enter key is pressed within a text field. A value of 
 * true will force the form submission while a value of false will prevent it. 
 * If submitForm is not specified, the default browser behavior will take effect.
 * </p><p>
 * <h3>Example 1: Create widget</h3>
 * </p><p>
 * This example shows how to create a widget using a span tag as a place holder 
 * in the document. Minimally, the createWidget() function needs an id and 
 * widgetType properties.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is a text field",
 *       widgetType: "textField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 2: Update widget using the getProps and setProps functions</h3>
 * </p><p>
 * This example shows how to toggle the state of a widget using the
 * getProps and setProps functions. When the user clicks the checkbox, the
 * text field is either disabled or enabled.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is a text field",
 *       widgetType: "textField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Toggle Text Field State" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("field1"); // Get text field
 *       return widget.setProps({disabled: !domNode.getProps().disabled}); // Toggle state
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * <h3>Example 3a: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a widget using the refresh 
 * function. When the user clicks on the checkbox, the text field is 
 * asynchronously updated with new data.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is a text field",
 *       widgetType: "textField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Refresh Text Field" },
 *       onKeyPress="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("field1"); // Get text field
 *       return widget.refresh(); // Asynchronously refresh
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can take an optional list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,..."). When no parameter is given, 
 * the refresh function acts as a reset. That is, the widget will be redrawn 
 * using values set server-side, but not updated.
 * </p><p>
 * <h3>Example 3b: Asynchronously update widget using refresh function</h3>
 * </p><p>
 * This example shows how to asynchronously update a text field using the 
 * refresh function. The execute property of the refresh function is used to
 * define the client id which is to be submitted and updated server-side. When
 * the user clicks on the checkbox, the input value is updated server-side and 
 * the text field is updated client-side -- all without a page refresh.
 * </p><pre><code>
 * &lt;span id="sp1">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp1", {
 *       id: "field1",
 *       value: "This is a text field",
 *       widgetType: "textField"
 *     });
 *   &lt;/script>
 * &lt;/span>
 * &lt;span id="sp2">
 *   &lt;script type="text/javascript">
 *     @JS_NS@.widget.common.createWidget("sp2", {
 *       id: "cb1",
 *       label: { value: "Change Text Field Value" },
 *       onClick="setTimeout('updateWidget();', 0);",
 *       widgetType: "checkbox"
 *     });
 *     function updateWidget() {
 *       var widget = @JS_NS@.widget.common.getWidget("field1"); // Get text field
 *       return widget.refresh("cb1"); // Asynchronously refresh while submitting checkbox value
 *     }
 *   &lt;/script>
 * &lt;/span>
 * </code></pre><p>
 * Note that the refresh function can optionally take a list of elements to 
 * execute. Thus, a comma-separated list of ids can be provided to update 
 * server-side components: refresh("id1,id2,...").
 * </p><p>
 * <h3>Example 4: Subscribing to event topics</h3>
 * </p><p>
 * When a widget is manipulated, some features may publish event topics for
 * custom AJAX implementations to listen for. For example, you may listen for
 * the refresh event topic using:
 * </p><pre><code>
 * &lt;script type="text/javascript">
 *    var foo = {
 *        // Process refresh event.
 *        //
 *        // @param {Object} props Key-Value pairs of properties.
 *        processRefreshEvent: function(props) {
 *            // Get the widget id.
 *            if (props.id == "field1") { // Do something... }
 *        }
 *    }
 *    // Subscribe to refresh event.
 *    woodstock.widget.common.subscribe(woodstock.widget.textField.event.refresh.endTopic, 
 *      foo, "processRefreshEvent");
 * &lt;/script>
 * </code></pre>
 *
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
@JS_NS@._dojo.declare("@JS_NS@.widget.textField", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.submitBase,
        @JS_NS@.widget._base.fieldBase ], {
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
@JS_NS@.widget.textField.event =
        @JS_NS@.widget.textField.prototype.event = {
    /**
     * This object contains filter event topics.
     * @ignore
     */
    autoComplete: {
        /** Filter event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_textField_event_autoComplete_begin",

        /** Filter event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_textField_event_autoComplete_end"
    },

    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_textField_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_textField_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_textField_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_textField_event_state_end"
    },

    /**
     * This object contains submit event topics.
     * @ignore
     */
    submit: {
        /** Submit event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_textField_event_submit_begin",

        /** Submit event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_textField_event_submit_end"
    },

    /**
     * This object contains validation event topics.
     * @ignore
     */
    validation: {
        /** Validation event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_textField_event_validation_begin",

        /** Validation event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_textField_event_validation_end"
    }
};

/**
 * Utility function to adjust input and list widths to match
 * the one of surrounding domNode node    
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.textField.prototype._adjustListGeometry = function () {
    this._listContainer.style.width = this._fieldNode.offsetWidth +"px";
   // this._listContainer.style.left = this._fieldNode.offsetLeft +"px";
    this._listContainer.style.left = "0px";
    this._listContainer.style.top = this._fieldNode.offsetTop + this._fieldNode.offsetHeight+"px";
    this._listNode.style.width = this._fieldNode.offsetWidth+"px";
    this._listContainer.style.zIndex = "999";
    return true;
};

/**
 * Helper function to obtain HTML input element class names.
 *
 * @return {String} The HTML input element class name.
 * @private
 */
@JS_NS@.widget.textField.prototype._getInputClassName = function() {          
    // Set readOnly style.
    if (this._fieldNode.readOnly) {
        return this._theme.getClassName("TEXT_FIELD_READONLY", "");
    }

    // Apply invalid style.
    var validStyle =  (this.valid == false) 
        ? " " + this._theme.getClassName("TEXT_FIELD_INVALID", "")
        : " " + this._theme.getClassName("TEXT_FIELD_VALID", "");
    
    // Set default style.    
    return (this.disabled == true)
        ? this._theme.getClassName("TEXT_FIELD_DISABLED", "") 
        : this._theme.getClassName("TEXT_FIELD", "") + validStyle;
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.textField.prototype.getProps = function() {
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
@JS_NS@.widget.textField.prototype._postCreate = function () {
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
@JS_NS@.widget.textField.prototype._createCloseListCallback = function() {
    var _id = this.id;
    return function(event) { 
        var widget = @JS_NS@.widget.common.getWidget(_id);
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
@JS_NS@.widget.textField.prototype._filterOptions = function() {    
    // Publish the event for custom AJAX implementations to listen for.
    // The implementation of this Ajax call will retrieve the value of the filter
    // and will obtain an updated lookup list ( either locally, or by submit to the server)
    // Data returned from Ajax call will be pushed into this._listWidget.
    this._publish(@JS_NS@.widget.textField.event.autoComplete.beginTopic, [{
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
@JS_NS@.widget.textField.prototype._setProps = function(props) {
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
           onFocus: "@JS_NS@.widget.common.getWidget('" + this.id + 
                "')._processFocusEvent(this.event);", 
           onBlur: "@JS_NS@.widget.common.getWidget('" + this.id + 
                "')._processBlurEvent(this.event);",
           widgetType: "listbox"
        };

        //?? use of event registration as in following disables field processing keys 
        //onChange: "@JS_NS@.widget.common.getWidget('" + this.id + "')._processListChange(this.event);"

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
@JS_NS@.widget.textField.prototype._processBlurEvent = function(event) {    
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
@JS_NS@.widget.textField.prototype._processFieldKeyDownEvent = function(event) {
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
@JS_NS@.widget.textField.prototype._processFieldKeyUpEvent = function(event) {    
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
@JS_NS@.widget.textField.prototype._processFocusEvent = function(event) {
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
@JS_NS@.widget.textField.prototype._processListChange = function(event) {    
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
@JS_NS@.widget.textField.prototype._updateListView = function() {
    if ( this.showAutoComplete == true && this.autoCompleteOptions.length >= 1) {
        //TODO a place for optimization here - not to adjust geometry each time
        this._adjustListGeometry();    

        //optionally we could add check for this._listNode.options.length >0
        this._listNode.className = this._theme.getClassName("TEXT_FIELD_AUTO_COMPLETE_LIST", "");

        // the following is preferred way of setting class, but it does not work
        //this._listWidget.setProps({className: this._theme.getClassName("TEXT_FIELD_AUTO_COMPLETE_LIST", "")}) ;
        
        //this.autoCompleteIsOpen flag indicates whether list box is open or not
        this.autoCompleteIsOpen = true;
    } else {
        this._listNode.className = this._theme.getClassName("HIDDEN");
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
@JS_NS@.widget.textField.prototype._validate = function(event) {
    // Publish an event for custom AJAX implementations to listen for.
    this._publish(@JS_NS@.widget.textField.event.validation.beginTopic, [{
        id: this.id
    }]);
    return true;
};
