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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.login");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.widgetBase");

/**
 * @name webui.@THEME_JS@.widget.login
 * @extends webui.@THEME_JS@.widget.widgetBase
 * @class This class contains functions for the login widget.
 * @constructor This function is used to construct a login widget.
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.login", webui.@THEME_JS@.widget.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.loginState = "INIT";
    },
    widgetName: "login" // Required for theme properties.    
});

/**
 * This function publishes the event that kicks off the authentication process.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.login.prototype.authenticate = function() {
    if (this.keys) {
        for (var i = 0; i < this.keys.length; i++) {
            var widget = this.widget.getWidget(this.keys[i][0]);
            var keyVal;
            if (widget) {
                var name = widget.widgetName;
                if (name == "textField" || name == "passwordField") {
                    keyVal = widget.getProps().value;
                } else if (name == "dropDown" || name == "listbox") {
                    keyVal = widget.getSelectedValue();
                }
            }
            this.keys[i][1] = keyVal;
        }
    }
                    
    // Publish an event for custom AJAX implementations to listen for.
    this._publish(webui.@THEME_JS@.widget.login.event.authenticate.beginTopic, [{
        id: this.id,
        loginState: this.loginState,
        keys: this.keys,
        endTopic: webui.@THEME_JS@.widget.login.event.authenticate.endTopic
    }]);
    return true;
};

/**
 * This function executes the steps that need to be followed when the 
 * "Login" button is clicked.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.login.prototype._buttonClicked = function() {
    return this.authenticate();
};

/**
 * This object contains event topics.
 * <p>
 * Note: Event topics must be prototyped for inherited functions. However, these
 * topics must also be available statically so that developers may subscribe to
 * events.
 * </p>
 * @ignore
 */
webui.@THEME_JS@.widget.login.event =
        webui.@THEME_JS@.widget.login.prototype.event = {
    /**
     * This closure is used to process authentication events.
     * @ignore
     */
    authenticate: {
        /**
         * Authentication begin event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME_JS@_widget_login_event_authenticate_begin",

        /**
         * Authentication end event topics for custom AJAX implementations to listen for.
         */
        endTopic: "webui_@THEME_JS@_widget_login_event_authenticate_end"

    },

    /**
     * This closure is used to process refresh events.
     * @ignore
     */
    refresh: {
        /**
         * Refresh event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME_JS@_widget_login_event_refresh_begin",

        /**
         * Refresh event topics for custom AJAX implementations to listen for.
         */
        endTopic: "webui_@THEME_JS@_widget_login_event_refresh_end"
    },

    /**
     * This closure is used to report success/failure during the authentication 
     * process.
     * @ignore
     */
    result: {
        /**
         * Successful authentication event topic for applications can listen for.
         */
        successTopic: "webui_@THEME_JS@_widget_login_event_result_success",

        /**
         * Authentication failure event topic that applications can listen for.
         */
        failureTopic: "webui_@THEME_JS@_widget_login_event_result_failure"
    },

    /**
     * This closure is used to process state change events.
     * @ignore
     */
    state: {
        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        beginTopic: "webui_@THEME_JS@_widget_login_event_state_begin",

        /**
         * Event topics for custom AJAX implementations to listen for.
         */
        endTopic: "webui_@THEME_JS@_widget_login_event_state_end"
    }
};

/**
 * This function performs error handling on the client side
 * when authentication failure occurs. Typically this involves
 * updating the alter widget with the appropriate messages
 * sent across from the server.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} id ID of the login widget.
 * @config {String} loginState State of the authentication process. Set to "INIT" at the beginning.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.login.prototype._handleFailure = function(props) {
    this._setAlert(props.alert);
    this._publish(webui.@THEME_JS@.widget.login.event.result.failureTopic, [{
        id: props.id
    }]);

    // initialize the state so that subsequent requests can start afresh
    this.loginState = "INIT";
    props.loginState = "INIT";
    return true;
};

/**
 * This function is used to obtain the outermost HTML element class name.
 * <p>
 * Note: Selectors should be concatinated in order of precedence (e.g., the
 * user's className property is always appended last).
 * </p>
 * @return {String} The outermost HTML element class name.
 */
webui.@THEME_JS@.widget.login.prototype.getClassName = function() {
    var key = "LOGIN_DIV";

    // Get theme property.
    var className = this.theme.getClassName(key);
    if (className == null || className.length == 0) {
	return this.className;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
};

/**
 * This function is used to get widget properties. Please see the 
 * setProps() function for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.login.prototype.getProps = function() {
    var props = this.inherited("getProps", arguments);

    // var props = webui.@THEME_JS@.widget.login.superclass.getProps.call(this);
    if (this.loginState) {props.loginState = this.loginState; }    
    if (this.autoStart != null) { props.autoStart = this.autoStart;}
    if (this.tabIndex != null) { props.tabIndex = this.tabIndex;}
    if (this.dotImage != null) {props.dotImage = this.dotImage;}
    if (this.loginButton != null) {props.loginButton = this.loginButton;}
    return props;
};

/**
 * This is a private function that creates a client side widget based on the 
 * widgetType and other properties supplied. 
 *
 * @param {Object} props Key-Value pairs of properties of the widget.
 * @return {boolean} the widget props if successful; false, otherwise.
 * @private
 */
webui.@THEME_JS@.widget.login.prototype._getWidgetProps = function(props) {
    if (props == null) {
        return false;
    }
        
    var type = props.type;
    if (type == null) {
        return false;
    }
        
    var _props = {};
            
    // Set default module and widget name
    _props = this.widget.getWidgetProps(type, _props); 
        
    // Add extra properties               
    this.prototypejs.extend(_props, props);
    return _props;
};

/**
 * This function handles the case where authentication is complete
 * and successful. The end result could involve storing a cookie
 * value somewhere or redirecting to some other page.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} id ID of the login widget.
 * @config {String} loginState State of the authentication process. Set to 
 * "INIT" at the beginning.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.login.prototype._handleSuccess = function(props) {
    // Publish the success event topic
    // Remove the alert message if props does not
    // contain any alert information.
    // Clear out the loginTable.
    this._setAlert(props.alert);
    this._publish(webui.@THEME_JS@.widget.login.event.result.successTopic, [{
        id: props.id
    }]);

    this.widget.removeChildNodes(this.loginTable);
    this.loginState = "INIT";
    props.loginState = "INIT";

    if (props.redirectURL != null) {
        var loc = document.location;
        var newURL = loc.protocol + "//" + loc.host;
        newURL = newURL + props.redirectURL;
        document.location.href = newURL;
    }
    return true;
};

/**
 * This function is used to fill in remaining template properties, after the
 * buildRendering() function has been processed.
 * <p>
 * Note: Unlike Dojo 0.4, the DOM nodes don't exist in the document, yet. 
 * </p>
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.login.prototype.postCreate = function () {
    if (this.tabIndex == null) {
        this.tabIndex = -1;
    }

    // If login button and dot image are empty generate them on the
    // client side.
    if (this.dotImage == null) {
	this.dotImage = this.widget.getWidgetProps("image", {
            icon: "DOT",
            id: this.id + "_dotImage"
        });
    }
    
    if (this.loginButton == null) {
        var _props = {};
	_props.id = this.id + "_loginButton";
        _props.tabIndex = this.tabIndex;
        _props.alt = this.theme.getMessage("login.buttonAlt");
        _props.value = this.theme.getMessage("login.buttonTitle");
        _props = this.widget.getWidgetProps("button", _props);
        this.loginButton = _props;
    }
    return this.inherited("postCreate", arguments);
};

/**
 * This function is used to set widget properties using Object literals.
 * <p>
 * Note: This function extends the widget object for later updates. Further, the
 * widget shall be updated only for the given key-value pairs.
 * </p><p>
 * If the notify param is true, the widget's state change event shall be
 * published. This is typically used to keep client-side state in sync with the
 * server.
 * </p>
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} id ID of the login widget.
 * @config {String} loginState State of the authentication process. Set to "INIT" at the beginning.
 * @config {boolean} autoStart Flag indicating if authentication process should start on page load.
 * @config {String} style Specify style rules inline.
 * @config {String} className CSS selector of outermost DOM node for this widget.
 * @config {String} tabIndex Attribute required to support tabbed navigation.
 * @config {Object} userData JSON object containing user data that needs to be displayed as user prompt.
 * @config {Object} keys JSON object representing the key value mapping for user data fields.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.login.prototype.setProps = function(props, notify) {
    // Extend widget object for later updates.
    return this.inherited("setProps", arguments);
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
webui.@THEME_JS@.widget.login.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Handle the success and failure and continue states here.
    // The init state is handled in the postCreate function
    // as there needs to be a minimal delay the first time this
    // widget is  being rendered - otherwise only the initial <span>
    // element representing the login widget gets transmitted causing
    // problems on the server side. 

    if (props.loginState == "SUCCESS") {
        this._handleSuccess(props);

    } else if (props.loginState == "FAILURE") {
        this._handleFailure(props);

    } else if (props.loginState == "CONTINUE") {
        this._updateLoginTable(props);
    }
    // Set remaining properties.
    return this.inherited("_setProps", arguments);
};

/**
 * This function adds or updates the alert widget displayed as part of the
 * login widget.
 *
 * @param {Object} alertProps Key-Value pairs of properties.
 * @config {String} type The alert type.
 * @config {String} summary The alert summary.
 * @config {String} detail The alert detail.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.login.prototype._setAlert = function(alertProps) {
    if (alertProps == null) {
        this.widget.removeChildNodes(this.alertContainer);
        return false;
    } else {
        var _props = {};
            
        // Set default module and widget name
        _props = this.widget.getWidgetProps("alert", _props); 
        _props.id = this.id + "_alert";

        // Add extra properties               
        if (alertProps != null) {
            this.prototypejs.extend(_props, alertProps);
        }
        if (_props.summary == null) {
            _props.summary = this.theme.getMessage("login.errorSummary");
        }
        if (_props.detail == null) {
            _props.detail = this.theme.getMessage("login.errorDetail");;
        }

        var tr = this.alertRowContainer.cloneNode(false);
        this.loginTbody.appendChild(tr);
        var td = this.alertCellContainer.cloneNode(false);
        tr.appendChild(td);
        var widget = this.widget.getWidget(_props.id);
        if (widget) {
            widget.setProps(_props);
        } else {
            this.widget.addFragment(td, _props); 
        }
    }
    return true;
};

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.login.prototype.startup = function () {
    if (this._started) {
        return false;
    }
    // If widget set to "autoStart" fire up the authentication process.
    var props = this.getProps();
    if (this.loginState == "INIT") {
        var id = this.id;
        if (this.autoStart) {
            setTimeout(function() {
                webui.@THEME_JS@.widget.common.getWidget(id).authenticate();
            }, 10);    
        }
    }
    return this.inherited("startup", arguments);
};

/**
 * This function is used to update the login section of the DOM tree with
 * the new set of widgets. This new set of widgets represents the new set
 * of prompts for entering authentication data. For example, the first set
 * of prompts could have been a textField and a password field. The second
 * set could be dropdown list containing a set of roles to choose from 
 * followed by a role password field.
 * 
 * @param {Object} props Key-Value pairs of properties.
 * @config {Object} userData JSON object containing user data that needs to be displayed as user prompt.
 * @config {Object} keys JSON object representing the key value mapping for user data fields.
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.login.prototype._updateLoginTable = function(props) {
    // Remove existing data entries before adding the new ones.
    // This involves destroying the widgets and also deleting
    // the table rows.
    this.widget.removeChildNodes(this.loginTbody);
        
    var rowNum = 1;

    // add the alert row
    var alertAdded = this._setAlert(props.alert);
        
    // set up table rows for each of the user prompts
    if (props.userData) {
        var rowCount = props.userData.length;
        var idCount = 0;

        for (var i=0; i < rowCount; i++) {
            var dataRow = props.userData[i];
            var tr = this.inputDataRowContainer.cloneNode(false);
            this.loginTbody.appendChild(tr);
            for (var j=0; j<dataRow.length; j++) {
                var td;
                var divNode;
                if (j==0) {
                    td = this.labelContainerCell.cloneNode(false);
                    tr.appendChild(td);
                    if (i+1 == rowCount) {
                        divNode = this.lastLabelContainer.cloneNode(true);
                    } else {
                        divNode = this.labelContainer.cloneNode(true);
                    }
                    td.appendChild(divNode);
                    var widgetProps = this._getWidgetProps(dataRow[j]);
                    if (widgetProps) {
                        this.widget.addFragment(divNode, widgetProps, "last"); 
                    }
                } else {
                    td = this.dataContainerCell.cloneNode(false);
                    tr.appendChild(td);
                    if (i+1 == rowCount) {
                        divNode = this.lastInputContainer.cloneNode(true);
                    } else if (dataRow[j].type == "staticText") {
                        divNode = this.stxtContainer.cloneNode(true);
                    } else if (dataRow[j].type == "textField") {
                        divNode = this.textContainer.cloneNode(true);
                    } else {
                        divNode = this.inputContainer.cloneNode(true);
                    }
                    td.appendChild(divNode);
                    var widgetProps = this._getWidgetProps(dataRow[j]);
                    if (widgetProps) {
                        this.widget.addFragment(divNode, widgetProps, "last"); 
                    }
                }
            }
            rowNum++;
        }
        // add table row for spacer image followed by the login button.
        var buttonTR = this.buttonRowContainer.cloneNode(false);
        this.loginTbody.appendChild(buttonTR);
        var td1 = this.dotImageContainer.cloneNode(false);
        buttonTR.appendChild(td1);
        if (this.dotImage) {
            this.widget.addFragment(td1, this.dotImage, "last");
        }

        var td2 = this.buttonContainer.cloneNode(false);
        buttonTR.appendChild(td2);
        var spanNode = this.loginButtonContainer.cloneNode(true);
        var _this = this;
        this.loginButton.onClick = function() {
            _this._buttonClicked(props);
            return false;
        };
        td2.appendChild(spanNode);
        this.widget.addFragment(spanNode, this.loginButton, "last");
    }
    return true;
};
