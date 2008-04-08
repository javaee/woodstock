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

webui.@THEME_JS@._dojo.provide("webui.@THEME_JS@.widget.progressBar");

webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget.common");
webui.@THEME_JS@._dojo.require("webui.@THEME_JS@.widget._base.widgetBase");

/**
 * This function is used to construct a progressBar widget.
 *
 * @name webui.@THEME_JS@.widget.progressBar
 * @extends webui.@THEME_JS@.widget._base.widgetBase
 * @class This class contains functions for the progressBar widget.
 * @constructor
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} bottomText 
 * @config {Object} busyImage 
 * @config {String} failedStateText
 * @config {String} id Uniquely identifies an element within a document.
 * @config {String} logId 
 * @config {boolean} logMessage 
 * @config {String} overlayAnimation 
 * @config {String} percentChar 
 * @config {int} progress 
 * @config {String} progressImageUrl 
 * @config {String} progressControlBottom
 * @config {String} progressControlRight 
 * @config {int} refreshRate 
 * @config {String} taskState
 * @config {String} toolTip 
 * @config {String} topText 
 * @config {String} type 
 * @config {boolean} visible Hide or show element.
 * @config {int} width 
 */
webui.@THEME_JS@._dojo.declare("webui.@THEME_JS@.widget.progressBar",
        webui.@THEME_JS@.widget._base.widgetBase, {
    // Set defaults.
    constructor: function() {
        this.progress = 0;
        this.percentChar = "%";
        this.type = this.determinate;
    },
    busy: "BUSY",
    canceled: "canceled",
    completed: "completed",
    determinate: "DETERMINATE",
    failed: "failed",
    indeterminate: "INDETERMINATE",
    notstarted: "not_started",
    paused: "paused",
    resumed: "resumed",
    stopped: "stopped",
    _widgetType: "progressBar"
});

/**
 * This function handles cancel progressBar event.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.cancel = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.canceled;
    if (this.type == this.determinate) {
        this._innerBarContainer.style.width = "0%";
    }
    return this._updateProgress();
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
webui.@THEME_JS@.widget.progressBar.event =
        webui.@THEME_JS@.widget.progressBar.prototype.event = {
    /**
     * This closure is used to publish progress events.
     * @ignore
     */
    progress: {
        /** Progress event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_progressBar_event_progress_begin",

        /** Progress event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_progressBar_event_progress_end"
    },

    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_progressBar_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_progressBar_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "webui_@THEME_JS@_widget_progressBar_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "webui_@THEME_JS@_widget_progressBar_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
webui.@THEME_JS@.widget.progressBar.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.height) { props.height = this.height; }
    if (this.width) { props.width = this.width; }
    if (this.bottomText) { props.bottomText = this.bottomText; }
    if (this.busyImage != null) { props.busyImage = this.busyImage; }
    if (this.failedStateText != null) { props.failedStateText = this.failedStateText; }
    if (this.id) { props.id = this.id; }
    if (this.log != null) { props.log = this.log; }
    if (this.logId) { props.logId = this.logId; }
    if (this.logMessage) { props.logMessage = this.logMessage; }
    if (this.overlayAnimation != null) { props.overlayAnimation = this.overlayAnimation; }
    if (this.percentChar) { props.percentChar = this.percentChar; }
    if (this.progress != null) { props.progress = this.progress; }
    if (this.progressImageUrl) { props.progressImageUrl = this.progressImageUrl; }
    if (this.progressControlBottom != null) { props.progressControlBottom = this.progressControlBottom; }
    if (this.progressControlRight != null) { props.progressControlRight = this.progressControlRight; }
    if (this.refreshRate) { props.refreshRate = this.refreshRate; }
    if (this.taskState != null) { props.taskState = this.taskState; }
    if (this.toolTip) { props.toolTip = this.toolTip; }
    if (this.topText) { props.topText = this.topText; }
    if (this.type) { props.type = this.type; }
    if (this.prefix) {props.prefix = this.prefix;}

    return props;
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
webui.@THEME_JS@.widget.progressBar.prototype._getClassName = function() {
    var key = "PROGRESSBAR"; 

    // Get theme property.
    var className = this._theme._getClassName(key);
    if (className == null || className.length == 0) {
	return this.className;
    }
    return (this.className)
        ? className + " " + this.className
        : className;
};

/**
 * This method displays the Bottom Controls if it was hidden.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isBottomControlVisible = function() {
    return this._common._isVisibleElement(this._bottomControlsContainer);
};

/**
 * This method displays the failed state message and icon if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isFailedStateMessageVisible = function() {
    return this._common._isVisibleElement(this._failedStateContainer);
};

/**
 * This method displays the log message if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isLogMsgVisible = function() {
    return this._common._isVisibleElement(this._logContainer);
};

/**
 * This method displays the operation text if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isOperationTextVisible = function() {
    return this._common._isVisibleElement(this._topTextContainer);
};

/**
 * This method displays the ProgressBar Container if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isProgressBarContainerVisible = function() {
    return this._common._isVisibleElement(this._barContainer);
};

/**
 * This method displays the ProgressBar if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isProgressBarVisible = function() {
    return this._common._isVisibleElement(this); 
};

/**
 * This method displays the Right Controls if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isRightControlVisible = function() {
    return this._common._isVisibleElement(this._rightControlsContainer);
};

/**
 * This method displays the status text if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.isStatusTextVisible = function() {
    return this._common._isVisibleElement(this._bottomTextContainer);
};

/**
 * This function handles pause button event.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.pause = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.paused;
    if (this.type == this.indeterminate) {
        this._innerBarContainer.className =
            this._theme._getClassName("PROGRESSBAR_INDETERMINATE_PAUSED");
    }
    return this._updateProgress();
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
webui.@THEME_JS@.widget.progressBar.prototype._postCreate = function () {
    // Set ids.
    if (this.id) {
        this._barContainer.id = this.id + "_barContainer";
        this._bottomControlsContainer.id = this.id + "_bottomControlsContainer";
        this._bottomTextContainer.id = this.id + "_bottomTextContainer"; 
        this._failedStateContainer.id = this.id + "_failedStateContainer";
        this._failedLabelContainer.id = this.id + "_failedLabelContainer";
        this._hiddenFieldNode.id = this.id + "_" + "controlType";
        this._hiddenFieldNode.name = this._hiddenFieldNode.id;
        this._innerBarContainer.id = this.id + "_innerBarContainer";
        this._innerBarOverlayContainer.id = this.id + "_innerBarOverlayContainer";
        this._logContainer.id = this.id + "_logContainer";
        this._rightControlsContainer.id = this.id + "_rightControlsContainer";
        this._topTextContainer.id = this.id + "_topTextContainer"; 
    }

    // Set public functions
    this._domNode.cancel = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).cancel(); };
    this._domNode.isBottomControlVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isBottomControlVisible(); };
    this._domNode.isFailedStateMessageVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isFailedStateMessageVisible(); };
    this._domNode.isLogMsgVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isLogMsgVisible(); };
    this._domNode.isOperationTextVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isOperationTextVisible(); };
    this._domNode.isProgressBarContainerVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isProgressBarContainerVisible(); };
    this._domNode.isProgressBarVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isProgressBarVisible(); };
    this._domNode.isRightControlVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isRightControlVisible(); };
    this._domNode.isStatusTextVisible = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).isStatusTextVisible(); };
    this._domNode.pause = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).pause(); };
    this._domNode.resume = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).resume(); };
    this._domNode.stop = function() { return webui.@THEME_JS@.widget.common.getWidget(this.id).stop(); };
    this._domNode.setOnCancel = function(func) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setOnCancel(func); };
    this._domNode.setOnComplete = function(func) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setOnComplete(func); };
    this._domNode.setOnFail = function(func) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setOnFail(func); };
    this._domNode.setBottomControlVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setBottomControlVisible(show); };
    this._domNode.setFailedStateMessageVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setFailedStateMessageVisible(show); };
    this._domNode.setLogMsgVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setLogMsgVisible(show); };
    this._domNode.setOperationTextVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setOperationTextVisible(show); };
    this._domNode.setProgressBarContainerVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProgressBarContainerVisible(show); };
    this._domNode.setProgressBarVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setProgressBarVisible(show); };
    this._domNode.setRightControlVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setRightControlVisible(show); };
    this._domNode.setStatusTextVisible = function(show) { return webui.@THEME_JS@.widget.common.getWidget(this.id).setStatusTextVisible(show); };

    if (this.busyImage == null) {
	this.busyImage = {
            icon: "PROGRESS_BUSY",
            id: this.id + "_busy",
            widgetType: "image"
        };
    }
    return this._inherited("_postCreate", arguments);
};

/**
 * This function handles resume button event.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.resume = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.resumed;
    if (this.type == this.indeterminate) {
        this._innerBarContainer.className = 
            this._theme._getClassName("PROGRESSBAR_INDETERMINATE");
            
    }
    return this._updateProgress();
};

/**
 * This method hides the Bottom Control.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setBottomControlVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this._bottomControlsContainer, show);
    return true;
};

/**
 * This method hides the failed state message and icon area.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setFailedStateMessageVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this._failedStateContainer, show);
    return true;
};

/**
 * This method hides the log message area.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setLogMsgVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this._logContainer, show);
    return true;
};

/**
 * This function invokes developer define function for cancel event.
 * 
 * @param {Function} func The JavaScript function.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setOnCancel = function(func) {
    if (func) {
        this.funcCanceled = func;
    }
    return true;
};

/**
 * This function invokes developer define function for complete event.
 * 
 * @param {Function} func The JavaScript function.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setOnComplete = function(func) {
    if (func) {
        this.funcComplete = func;
    }
    return true;
};

/**
 * This function invokes developer define function for failed event.
 * 
 * @param {Function} func The JavaScript function.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setOnFail = function(func) {
    if (func) {
        this.funcFailed = func;
    }
    return true;
};

/**
 * This method hides the operation text.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setOperationTextVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this._topTextContainer, show);
    return true;
};

/**
 * This function is used to set progress with Object literals.
 *
 * @param {Object} props Key-Value pairs of properties.
 * @config {String} failedStateText
 * @config {String} logMessage
 * @config {int} progress
 * @config {String} status
 * @config {String} taskState
 * @config {String} topText
 * @config {String} type
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setProgress = function(props) {
    if (props == null) {
        return false;
    }
      
    // Adjust max value.
    if (props.progress > 99 
            || props.taskState == this.completed) {
        props.progress = 100;
    }

    // Save properties for later updates.
    this._proto._extend(this, props);    

    // Set status.
    if (props.status) {
        this._widget._addFragment(this._bottomTextContainer, props.status);
    }

    // If top text doesnt get change, dont update.
    if (props.topText) {
        if (props.topText != this.topText) {
            this._widget._addFragment(this._topTextContainer, props.topText);
        }
    }

    // Update log messages.
    if (this.type == this.determinate) { 
        if (props.progress != null && props.progress >= 0 ) {
            this._innerBarContainer.style.width = props.progress + '%';
        }

        if (props.logMessage) {
            var field = this._widget.getWidget(this.logId).getInputElement();
            if (field != null) {
                field.value = (field.value)
                   ? field.value + props.logMessage + "\n"
                   : props.logMessage + "\n";
            }
        }

        // Add overlay text.
        if (this.overlayAnimation == true) {
            // NOTE: If you set this value manually, text must be HTML escaped.
            this._widget._addFragment(this._innerBarOverlayContainer, props.progress + "%");
        }
    } 

    // Failed state.
    if (props.taskState == this.failed) {
        clearTimeout(this.timeoutId);
        this._widget._sleep(1000);
        this.setProgressBarContainerVisible(false);
        this.setBottomControlVisible(false);
        this.setRightControlVisible(false);
        this.setLogMsgVisible(false);

        if (props.failedStateText != null) {
            // NOTE: If you set this value manually, text must be HTML escaped.
            this._widget._addFragment(this._failedLabelContainer,
                props.failedStateText + " " + props.progress + this.percentChar);

            this._common._setVisibleElement(this._failedLabelContainer, true);
            this._common._setVisibleElement(this._failedStateContainer, true);
        }
        if (this.funcFailed != null) {
            (this.funcFailed)();
        }
        return true;
    }

    // Cancel state.
    if (props.taskState == this.canceled) {
        clearTimeout(this.timeoutId);
        this._widget._sleep(1000);
        this.setOperationTextVisible(false);
        this.setStatusTextVisible(false);
        this.setProgressBarContainerVisible(false);
        this.setBottomControlVisible(false);
        this.setRightControlVisible(false);
        this.setLogMsgVisible(false);

        if (this.type == this.determinate) {
            this._innerBarContainer.style.width = "0%";
        }
        if (this.funcCanceled != null) {
           (this.funcCanceled)(); 
        }
        return true;    
    }

    // paused state
    if (props.taskState == this.paused) {
        clearTimeout(this.timeoutId);
        return true;
    }

    // stopped state
    if (props.taskState == this.stopped) {
        clearTimeout(this.timeoutId);
        return true;
    }

    if (props.progress > 99 
            || props.taskState == this.completed) {
        clearTimeout(this.timeoutId);
        if (this.type == this.indeterminate) {
            this._innerBarContainer.className =
                this._theme._getClassName("PROGRESSBAR_INDETERMINATE_PAUSED");
        }
        if (this.type == this.busy) {
            this.setProgressBarContainerVisible(false);
        }
        if (this.funcComplete != null) {
           (this.funcComplete)(); 
        }
    }

    // Set progress for A11Y.
    if (props.progress) {
        if (this._bottomTextContainer.setAttributeNS) {
            this._bottomTextContainer.setAttributeNS("http://www.w3.org/2005/07/aaa",
                "valuenow", props.progress);
        }
    }
    return true;
};

/**
 * This method hides the ProgressBar Container.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setProgressBarContainerVisible = function(show) {
    if (show == null) {
        return false;
    }

    if (show == false) {
        this._barContainer.style.display = "none";
    } else {
        this._barContainer.style.display = '';
    }
    return true; 
};

/**
 * This method hides the ProgressBar.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setProgressBarVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this, show);
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
webui.@THEME_JS@.widget.progressBar.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set tool tip.
    if (props.toolTip) {
        this._barContainer.title = props.toolTip;
    }

    // Add top text.
    if (props.topText) {
        this._widget._addFragment(this._topTextContainer, props.topText); 
        this._common._setVisibleElement(this._topTextContainer, true);
    }

    // Add bottom text.
    if (props.bottomText) {
        this._widget._addFragment(this._bottomTextContainer, props.bottomText);
        this._common._setVisibleElement(this._bottomTextContainer, true);
    }

    if (props.type == this.determinate 
            || props.type == this.indeterminate) {
        // Set style class.
        this._barContainer.className =
            this._theme._getClassName("PROGRESSBAR_CONTAINER");

        // Set height.
        if (props.height != null && props.height > 0) {
            this._barContainer.style.height = props.height + "px;"; 
            this._innerBarContainer.style.height = props.height + "px;";
        }

        // Set width.
        if (props.width != null && props.width > 0) {
            this._barContainer.style.width = props.width + "px;";
        }

        // Add right controls.
        if (props.progressControlRight != null) {
            this._widget._addFragment(this._rightControlsContainer, props.progressControlRight);
            this._common._setVisibleElement(this._rightControlsContainer, true);
        }

        // Add bottom controls.
        if (props.progressControlBottom != null) {
            this._widget._addFragment(this._bottomControlsContainer, props.progressControlBottom);
            this._common._setVisibleElement(this._bottomControlsContainer, true);
        }
    }

    if (props.type == this.determinate) {
        // Set style class.
        this._innerBarContainer.className =
            this._theme._getClassName("PROGRESSBAR_DETERMINATE");

        // Set width.
        if (this.progress != null && this.progress >= 0) {
            this._innerBarContainer.style.width = this.progress + '%';
        }    

        // Add overlay.
        if (props.overlayAnimation == true) {
            if (props.width != null && props.width > 0) {
                this._innerBarOverlayContainer.style.width = props.width + "px;";
            }
            // NOTE: If you set this value manually, text must be HTML escaped.            
            this._widget._addFragment(this._innerBarOverlayContainer, this.progress + "%");
            this._common._setVisibleElement(this._innerBarOverlayContainer, true);
        }

        // Add log.
        if (props.log != null && props.overlayAnimation == false) { 
            this._widget._addFragment(this._logContainer, props.log);
            this._common._setVisibleElement(this._logContainer, true);
        }  
    } else if (props.type == this.indeterminate) {
        // Set style class.
        this._barContainer.className = 
            this._theme._getClassName("PROGRESSBAR_CONTAINER");
        this._innerBarContainer.className = 
            this._theme._getClassName("PROGRESSBAR_INDETERMINATE");
    } else if (props.type == this.busy) {
        // Add busy image.
        if (props.busyImage) {
            if (props.width > 0) {
                props.busyImage.width = props.width;
            } 
            if (props.height > 0) {
                props.busyImage.height = props.height;
            }
            if (props.progressImageUrl != null ) {                                
                if (props.prefix) {               
                    props.busyImage.icon = null;     
                    props.busyImage.src = 
                        webui.@THEME_JS@.widget.common._appendPrefix(props.prefix, props.progressImageUrl);               
                }    
            }
            this._widget._addFragment(this._busyImageContainer, props.busyImage);
            this._common._setVisibleElement(this._busyImageContainer, true);
        }
    }

    // Set developer specified image.
    if (props.progressImageUrl != null && (props.type != this.busy)) {
        this._innerBarContainer.style.backgroundImage = 'url(' + props.progressImageUrl + ')';
    }

    // Set A11Y properties.
    if (props.progress != null) {
        if (this._bottomTextContainer.setAttributeNS) {
            this._bottomTextContainer.setAttributeNS(
                "http://www.w3.org/2005/07/aaa", "valuenow", this.progress);
        }
    }

    // Set more properties.
    this._setCommonProps(this._domNode, props);

    // Set remaining properties.
    return this._inherited("_setProps", arguments);
};

/**
 * This method hides the Right Control.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setRightControlVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this._rightControlsContainer, show);
    return true;
};

/**
 * This method hides the status text.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.setStatusTextVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this._bottomTextContainer, show);
    return true;
};

/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.progressBar.prototype._start = function () {
    if (typeof this._started == "undefined") {
        return false;
    }
    // Start a timer used to periodically publish progress events.
    this._updateProgress();  
    return this._inherited("_start", arguments);
};

/**
 * This function handles stop button event.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
webui.@THEME_JS@.widget.progressBar.prototype.stop = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.stopped;
    if (this.type == this.indeterminate) {
        this.innerBarIdContainer.className =
            this._theme._getClassName("PROGRESSBAR_INDETERMINATE_PAUSED");
    }
    return this._updateProgress();
};

/**
 * Process progress event.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
webui.@THEME_JS@.widget.progressBar.prototype._updateProgress = function() {
    // Publish event.
    if (this.refreshRate > 0) {
        // Publish an event for custom AJAX implementations to listen for.
        this._publish(webui.@THEME_JS@.widget.progressBar.event.progress.beginTopic, [{
            id: this.id
        }]);
    }

    // Create a call back function to periodically publish progress events.
    var _id = this.id;
    this.timeoutId = setTimeout(function() {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        webui.@THEME_JS@.widget.common.getWidget(_id)._updateProgress();
    }, this.refreshRate);
    return true;
};
