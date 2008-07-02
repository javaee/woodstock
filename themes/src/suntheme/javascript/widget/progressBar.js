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

@JS_NS@._dojo.provide("@JS_NS@.widget.progressBar");

@JS_NS@._dojo.require("@JS_NS@.widget.common");
@JS_NS@._dojo.require("@JS_NS@.widget._base.refreshBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.stateBase");
@JS_NS@._dojo.require("@JS_NS@.widget._base.widgetBase");

/**
 * This function is used to construct a progressBar widget.
 *
 * @name @JS_NS@.widget.progressBar
 * @extends @JS_NS@.widget._base.widgetBase
 * @extends @JS_NS@.widget._base.refreshBase
 * @extends @JS_NS@.widget._base.stateBase
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
@JS_NS@._dojo.declare("@JS_NS@.widget.progressBar", [
        @JS_NS@.widget._base.refreshBase, 
        @JS_NS@.widget._base.stateBase,
        @JS_NS@.widget._base.widgetBase ], {
    // Set defaults.
    constructor: function() {
        this.progress = 0;
        this.percentChar = "%";
        this.type = this.determinate;
        this.overlayAnimation = false;
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
    running: "running",
    _widgetType: "progressBar"
});

/**
 * This function handles cancel progressBar event.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.cancel = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.canceled;
    this.taskState = this.canceled;
    if (this.type == this.determinate) {
        this._innerBarContainer.style.width = "0%";
    }
    // publish cancel event
    this._publish(@JS_NS@.widget.progressBar.event.cancel.cancelTopic, [{
          id: this.id,
          taskState: this.taskState
        }]);
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
@JS_NS@.widget.progressBar.event =
        @JS_NS@.widget.progressBar.prototype.event = {
    /**
     * This closure is used to publish progress events.
     * @ignore
     */
    progress: {
        /** Progress event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_progressBar_event_progress_begin",

        /** Progress event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_progressBar_event_progress_end"
    },
    
    /**
     * This object contains pause event topics.
     * @ignore
     */
    pause: {
        /** pause topic for custom AJAX implementations to listen for. */
        pauseTopic: "@JS_NS@_widget_progressBar_event_pause"
    },
    
    /**
     * This object contains resume event topics.
     * @ignore
     */
    resume: {
        /** resume topic for custom AJAX implementations to listen for. */
        resumeTopic: "@JS_NS@_widget_progressBar_event_resume"
    },
    
    /**
     * This object contains cancel event topics.
     * @ignore
     */
    cancel: {
        /** cancel topic for custom AJAX implementations to listen for. */
        cancelTopic: "@JS_NS@_widget_progressBar_event_cancel"
    },
    
    /**
     * This object contains stop event topics.
     * @ignore
     */
    stop: {
        /** stop topic for custom AJAX implementations to listen for. */
        stopTopic: "@JS_NS@_widget_progressBar_event_stop"
    },
        
    /**
     * This object contains refresh event topics.
     * @ignore
     */
    refresh: {
        /** Refresh event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_progressBar_event_refresh_begin",

        /** Refresh event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_progressBar_event_refresh_end"
    },

    /**
     * This object contains state event topics.
     * @ignore
     */
    state: {
        /** State event topic for custom AJAX implementations to listen for. */
        beginTopic: "@JS_NS@_widget_progressBar_event_state_begin",

        /** State event topic for custom AJAX implementations to listen for. */
        endTopic: "@JS_NS@_widget_progressBar_event_state_end"
    }
};

/**
 * This function is used to get widget properties. Please see the constructor 
 * detail for a list of supported properties.
 *
 * @return {Object} Key-Value pairs of properties.
 */
@JS_NS@.widget.progressBar.prototype.getProps = function() {
    var props = this._inherited("getProps", arguments);

    // Set properties.
    if (this.height != null) { props.height = this.height; }
    if (this.width != null) { props.width = this.width; }
    if (this.bottomText != null) { props.bottomText = this.bottomText; }
    if (this.busyImage != null) { props.busyImage = this.busyImage; }
    if (this.failedStateText != null) { props.failedStateText = this.failedStateText; }
    if (this.id != null) { props.id = this.id; }
    if (this.log != null) { props.log = this.log; }
    if (this.logId != null) { props.logId = this.logId; }
    if (this.logMessage != null) { props.logMessage = this.logMessage; }
    if (this.overlayAnimation != null) { props.overlayAnimation = this.overlayAnimation; }
    if (this.percentChar != null) { props.percentChar = this.percentChar; }
    if (this.progress != null) { props.progress = this.progress; }
    if (this.progressImageUrl != null) { props.progressImageUrl = this.progressImageUrl; }
    if (this.progressControlBottom != null) { props.progressControlBottom = this.progressControlBottom; }
    if (this.progressControlRight != null) { props.progressControlRight = this.progressControlRight; }
    if (this.refreshRate != null) { props.refreshRate = this.refreshRate; }
    if (this.taskState != null) { props.taskState = this.taskState; }
    if (this.toolTip != null) { props.toolTip = this.toolTip; }
    if (this.topText != null) { props.topText = this.topText; }
    if (this.type != null) { props.type = this.type; }
    if (this.prefix != null) {props.prefix = this.prefix;}

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
@JS_NS@.widget.progressBar.prototype._getClassName = function() {
    var className = this._inherited("_getClassName", arguments);
    var key = "PROGRESSBAR"; 

    // Get theme property.
    var newClassName = this._theme.getClassName(key, "");
        
    return (className)
        ? newClassName + " " + className
        : newClassName;
};

/**
 * This method displays the Bottom Controls if it was hidden.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isBottomControlVisible = function() {
    return this._common._isVisibleElement(this._bottomControlsContainer);
};

/**
 * This method displays the failed state message and icon if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isFailedStateMessageVisible = function() {
    return this._common._isVisibleElement(this._failedStateContainer);
};

/**
 * This method displays the log message if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isLogMsgVisible = function() {
    return this._common._isVisibleElement(this._logContainer);
};

/**
 * This method displays the operation text if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isOperationTextVisible = function() {
    return this._common._isVisibleElement(this._topTextContainer);
};

/**
 * This method displays the ProgressBar Container if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isProgressBarContainerVisible = function() {
    return this._common._isVisibleElement(this._barContainer);
};

/**
 * This method displays the ProgressBar if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isProgressBarVisible = function() {
    return this._common._isVisibleElement(this); 
};

/**
 * This method displays the Right Controls if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isRightControlVisible = function() {
    return this._common._isVisibleElement(this._rightControlsContainer);
};

/**
 * This method displays the status text if it was hidden.
 *
* @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.isStatusTextVisible = function() {
    return this._common._isVisibleElement(this._bottomTextContainer);
};

/**
 * This function handles pause button event.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.pause = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.paused;
    this.taskState = this.paused;
    if (this.type == this.indeterminate) {
        this._innerBarContainer.className =
            this._theme.getClassName("PROGRESSBAR_INDETERMINATE_PAUSED");
    }
    // publish pause event
    this._publish(@JS_NS@.widget.progressBar.event.pause.pauseTopic, [{
          id: this.id,
          taskState: this.taskState
        }]);
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
@JS_NS@.widget.progressBar.prototype._postCreate = function () {
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

    // Set public functions.

    /** @ignore */
    this._domNode.cancel = function() { return @JS_NS@.widget.common.getWidget(this.id).cancel(); };
    /** @ignore */
    this._domNode.isBottomControlVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isBottomControlVisible(); };
    /** @ignore */
    this._domNode.isFailedStateMessageVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isFailedStateMessageVisible(); };
    /** @ignore */
    this._domNode.isLogMsgVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isLogMsgVisible(); };
    /** @ignore */
    this._domNode.isOperationTextVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isOperationTextVisible(); };
    /** @ignore */
    this._domNode.isProgressBarContainerVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isProgressBarContainerVisible(); };
    /** @ignore */
    this._domNode.isProgressBarVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isProgressBarVisible(); };
    /** @ignore */
    this._domNode.isRightControlVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isRightControlVisible(); };
    /** @ignore */
    this._domNode.isStatusTextVisible = function() { return @JS_NS@.widget.common.getWidget(this.id).isStatusTextVisible(); };
    /** @ignore */
    this._domNode.pause = function() { return @JS_NS@.widget.common.getWidget(this.id).pause(); };
    /** @ignore */
    this._domNode.resume = function() { return @JS_NS@.widget.common.getWidget(this.id).resume(); };
    /** @ignore */
    this._domNode.startProgress = function() { return @JS_NS@.widget.common.getWidget(this.id).startProgress(); }; 
    /** @ignore */
    this._domNode.stop = function() { return @JS_NS@.widget.common.getWidget(this.id).stop(); };     
    /** @ignore */
    this._domNode.setOnCancel = function(func) { return @JS_NS@.widget.common.getWidget(this.id).setOnCancel(func); };
    /** @ignore */
    this._domNode.setOnComplete = function(func) { return @JS_NS@.widget.common.getWidget(this.id).setOnComplete(func); };
    /** @ignore */
    this._domNode.setOnFail = function(func) { return @JS_NS@.widget.common.getWidget(this.id).setOnFail(func); };
    /** @ignore */
    this._domNode.setBottomControlVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setBottomControlVisible(show); };
    /** @ignore */
    this._domNode.setFailedStateMessageVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setFailedStateMessageVisible(show); };
    /** @ignore */
    this._domNode.setLogMsgVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setLogMsgVisible(show); };
    /** @ignore */
    this._domNode.setOperationTextVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setOperationTextVisible(show); };
    /** @ignore */
    this._domNode.setProgressBarContainerVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setProgressBarContainerVisible(show); };
    /** @ignore */
    this._domNode.setProgressBarVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setProgressBarVisible(show); };
    /** @ignore */
    this._domNode.setRightControlVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setRightControlVisible(show); };
    /** @ignore */
    this._domNode.setStatusTextVisible = function(show) { return @JS_NS@.widget.common.getWidget(this.id).setStatusTextVisible(show); };
    

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
@JS_NS@.widget.progressBar.prototype.resume = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.resumed;
    this.taskState = this.resumed;
    if (this.type == this.indeterminate) {
        this._innerBarContainer.className = 
            this._theme.getClassName("PROGRESSBAR_INDETERMINATE");            
    }
    // publish resume event
    this._publish(@JS_NS@.widget.progressBar.event.resume.resumeTopic, [{
          id: this.id,
          taskState: this.taskState
        }]);
    return this._updateProgress();
};

/**
 * This method hides the Bottom Control.
 *
 * @param {boolean} show true to show the element, false to hide the element.
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.setBottomControlVisible = function(show) {
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
@JS_NS@.widget.progressBar.prototype.setFailedStateMessageVisible = function(show) {
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
@JS_NS@.widget.progressBar.prototype.setLogMsgVisible = function(show) {
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
@JS_NS@.widget.progressBar.prototype.setOnCancel = function(func) {
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
@JS_NS@.widget.progressBar.prototype.setOnComplete = function(func) {
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
@JS_NS@.widget.progressBar.prototype.setOnFail = function(func) {
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
@JS_NS@.widget.progressBar.prototype.setOperationTextVisible = function(show) {
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
@JS_NS@.widget.progressBar.prototype.setProgress = function(props) {
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
            var progress = props.progress;
            if (progress == null || progress < 0) {
                progress = 0;
            }
            this._widget._addFragment(this._innerBarOverlayContainer, progress + "%");
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
        this._hiddenFieldNode.value = this.failed;
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
                this._theme.getClassName("PROGRESSBAR_INDETERMINATE_PAUSED");
        }
        if (this.type == this.busy) {
            this.setProgressBarContainerVisible(false);
        }
        if (this.funcComplete != null) {
           (this.funcComplete)(); 
        }
        this._hiddenFieldNode.value = this.completed;
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
@JS_NS@.widget.progressBar.prototype.setProgressBarContainerVisible = function(show) {
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
@JS_NS@.widget.progressBar.prototype.setProgressBarVisible = function(show) {
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
@JS_NS@.widget.progressBar.prototype._setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set tool tip.
    if (props.toolTip != null) {
        this._barContainer.title = props.toolTip;
    }

    // Add top text.
    if (props.topText != null) {
        this._widget._addFragment(this._topTextContainer, props.topText); 
        this._common._setVisibleElement(this._topTextContainer, true);
    }

    // Add bottom text.
    if (props.bottomText != null) {
        this._widget._addFragment(this._bottomTextContainer, props.bottomText);
        this._common._setVisibleElement(this._bottomTextContainer, true);
    }

    if (props.type == this.determinate 
            || props.type == this.indeterminate) {
        // Set style class.
        this._barContainer.className =
            this._theme.getClassName("PROGRESSBAR_CONTAINER");

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
            if (props.progressControlRight instanceof Array) {
                for (var i = 0; i < props.progressControlRight.length; i++) {
                    this._widget._addFragment(this._rightControlsContainer, props.progressControlRight[i], "last");
                }
            } else {
                this._widget._addFragment(this._rightControlsContainer, props.progressControlRight, "last");
            }             
            this._common._setVisibleElement(this._rightControlsContainer, true);
        }

        // Add bottom controls.
        if (props.progressControlBottom != null) {
            if (props.progressControlBottom instanceof Array) {
                for (var i = 0; i < props.progressControlBottom.length; i++) {
                    this._widget._addFragment(this._bottomControlsContainer, props.progressControlBottom[i], "last");
                }
            } else {
                this._widget._addFragment(this._bottomControlsContainer, props.progressControlBottom, "last");
            }            
            this._common._setVisibleElement(this._bottomControlsContainer, true);
        }
    }

    if (props.type == this.determinate) {
        // Set style class.
        this._innerBarContainer.className =
            this._theme.getClassName("PROGRESSBAR_DETERMINATE");

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
            this._theme.getClassName("PROGRESSBAR_CONTAINER");
        this._innerBarContainer.className = 
            this._theme.getClassName("PROGRESSBAR_INDETERMINATE");
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
                        @JS_NS@.widget.common._appendPrefix(props.prefix, props.progressImageUrl);               
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
@JS_NS@.widget.progressBar.prototype.setRightControlVisible = function(show) {
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
@JS_NS@.widget.progressBar.prototype.setStatusTextVisible = function(show) {
    if (show == null) {
        return false;
    }
    this._common._setVisibleElement(this._bottomTextContainer, show);
    return true;
};

/**
 * This function is used to start the progress client-side if one of the task state is
 * "notstarted", "canceled", "failed", "completed" or "stopped".
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.startProgress = function() {
    clearTimeout(this.timeoutId);
    // get the current task state
    var currentTaskState = this._hiddenFieldNode.value;    
    if (currentTaskState == this.canceled || currentTaskState == this.stopped
        || currentTaskState == this.failed || currentTaskState == this.notstarted
        || currentTaskState == this.completed ) {
        this._hiddenFieldNode.value = this.running;
        this.taskState = this.running;
        return this._updateProgress();        
    }
    return false;
};
/**
 * This function is used to "start" the widget, after the widget has been
 * instantiated.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.progressBar.prototype._startup = function () {
    if (this._started == true) {
        return false;
    }
    // Start a timer used to periodically publish progress events.
    this._updateProgress();
    return this._inherited("_startup", arguments);
};

/**
 * This function handles stop button event.
 *
 * @return {boolean} true if successful; otherwise, false.
 */
@JS_NS@.widget.progressBar.prototype.stop = function() {
    clearTimeout(this.timeoutId);

    this._hiddenFieldNode.value = this.stopped;
    this.taskState = this.stopped;
    if (this.type == this.indeterminate) {
        this.innerBarIdContainer.className =
            this._theme.getClassName("PROGRESSBAR_INDETERMINATE_PAUSED");
    }
    // publish stop event
    this._publish(@JS_NS@.widget.progressBar.event.stop.stopTopic, [{
          id: this.id,
          taskSatae: this.taskState
        }]);
    return this._updateProgress();
};

/**
 * Process progress event.
 *
 * @return {boolean} true if successful; otherwise, false.
 * @private
 */
@JS_NS@.widget.progressBar.prototype._updateProgress = function() {
    // Publish event.
    if (this.refreshRate > 0) {
        // Publish an event for custom AJAX implementations to listen for.
        this._publish(@JS_NS@.widget.progressBar.event.progress.beginTopic, [{
            id: this.id,
            taskState: this.taskState
        }]);
    }

    // Create a call back function to periodically publish progress events.
    var _id = this.id;
    this.timeoutId = setTimeout(function() {
        // New literals are created every time this function is called, and it's 
        // saved by closure magic.
        @JS_NS@.widget.common.getWidget(_id)._updateProgress();
    }, this.refreshRate);
    return true;
};
