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

dojo.provide("webui.@THEME@.widget.progressBar");

dojo.require("dojo.widget.*");
dojo.require("webui.@THEME@.*");
dojo.require("webui.@THEME@.widget.*");

/**
 * This function will be invoked when creating a Dojo widget. Please see
 * webui.@THEME@.widget.progressBar.setProps for a list of supported
 * properties.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.progressBar = function() {
    this.widgetType = "progressBar";
    dojo.widget.Widget.call(this);

    this.fillInTemplate = function() {
        // Set ids.
        if (this.id) {
            this.barContainer.id = this.id + "_barContainer";
            this.bottomControlsContainer.id = this.id + "_bottomControlsContainer";
            this.bottomTextContainer.id = this.id + "_bottomTextContainer"; 
            this.failedStateContainer.id = this.id + "_failedStateContainer";
            this.failedLabelContainer.id = this.id + "_failedLabelContainer";
            this.hiddenFieldNode.id = this.id + "_" + "controlType";
            this.hiddenFieldNode.name = this.hiddenFieldNode.id;
            this.innerBarContainer.id = this.id + "_innerBarContainer";
            this.innerBarOverlayContainer.id = this.id + "_innerBarOverlayContainer";
            this.logContainer.id = this.id + "_logContainer";
            this.rightControlsContainer.id = this.id + "_rightControlsContainer";
            this.topTextContainer.id = this.id + "_topTextContainer"; 
        }

        // Set public functions
        this.domNode.cancel = webui.@THEME@.widget.progressBar.cancel;
        this.domNode.isBottomControlVisible =
            webui.@THEME@.widget.progressBar.isBottomControlVisible;
        this.domNode.isFailedStateMessageVisible =
            webui.@THEME@.widget.progressBar.isFailedStateMessageVisible;
        this.domNode.isLogMsgVisible =
            webui.@THEME@.widget.progressBar.isLogMsgVisible;
        this.domNode.isOperationTextVisible =
            webui.@THEME@.widget.progressBar.isOperationTextVisible;
        this.domNode.isProgressBarContainerVisible =
            webui.@THEME@.widget.progressBar.isProgressBarContainerVisible;
        this.domNode.isProgressBarVisible =
            webui.@THEME@.widget.progressBar.isProgressBarVisible;
        this.domNode.isRightControlVisible =
            webui.@THEME@.widget.progressBar.isRightControlVisible;
        this.domNode.isStatusTextVisible =
            webui.@THEME@.widget.progressBar.isStatusTextVisible;
        this.domNode.pause = webui.@THEME@.widget.progressBar.pause;
        this.domNode.resume = webui.@THEME@.widget.progressBar.resume;
        this.domNode.stop = webui.@THEME@.widget.progressBar.stop;
        this.domNode.setOnCancel = webui.@THEME@.widget.progressBar.setOnCancel;
        this.domNode.setOnComplete = webui.@THEME@.widget.progressBar.setOnComplete;
        this.domNode.setOnFail = webui.@THEME@.widget.progressBar.setOnFail;
        this.domNode.setBottomControlVisible =
            webui.@THEME@.widget.progressBar.setBottomControlVisible;
        this.domNode.setFailedStateMessageVisible =
            webui.@THEME@.widget.progressBar.setFailedStateMessageVisible;
        this.domNode.setLogMsgVisible =
            webui.@THEME@.widget.progressBar.setLogMsgVisible;
        this.domNode.setOperationTextVisible =
            webui.@THEME@.widget.progressBar.setOperationTextVisible;
        this.domNode.setProgressBarContainerVisible = 
            webui.@THEME@.widget.progressBar.setProgressBarContainerVisible;
        this.domNode.setProgressBarVisible = 
            webui.@THEME@.widget.progressBar.setProgressBarVisible;
        this.domNode.setProps = webui.@THEME@.widget.progressBar.setProps;
        this.domNode.setRightControlVisible =
            webui.@THEME@.widget.progressBar.setRightControlVisible;
        this.domNode.setStatusTextVisible =
            webui.@THEME@.widget.progressBar.setStatusTextVisible;

        // Set private functions (private functions/props prefixed with "_").
        this.domNode._refresh = webui.@THEME@.widget.progressBar.refresh.processEvent;
        this.domNode._setProgress = webui.@THEME@.widget.progressBar.setProgress;
        this.domNode._sleep = webui.@THEME@.widget.progressBar.sleep;

        // Set properties.
        this.domNode.setProps({
            barHeight: this.barHeight,
            barWidth: this.barWidth,
            bottomText: this.bottomText,
            busyImage: this.busyImage,
            failedStateText: this.failedStateText,
            id: this.id,
            log: this.log,
            logId: this.logId,
            logMessage: this.logMessage,
            overlayAnimation: this.overlayAnimation,
            percentChar: (this.percentChar) ? this.percentChar : "%",
            progress: this.progress,
            progressImageUrl: this.progressImageUrl,
            progressControlBottom: this.progressControlBottom,
            progressControlRight: this.progressControlRight,
            refreshRate: this.refreshRate,
            taskState: this.taskState,
            templatePath: this.templatePath,
            toolTip: this.toolTip,
            topText: this.topText,
            type: this.type,
            visible: this.visible
        });

        // Initiate the first refresh.
        this.domNode._refresh();
        return true;
   };

} // closing tag progressbar function

dojo.inherits(webui.@THEME@.widget.progressBar, dojo.widget.HtmlWidget);

/**
 * This method hides the ProgressBar.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setProgressBarVisible = function(show) {
    if (show == null) {
        return false;
    }
    webui.@THEME@.common.setVisibleElement(this, show);
    return true; 
}

/**
 * This method displays the ProgressBar if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isProgressBarVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this); 
}

/**
 * This method hides the ProgressBar Container.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setProgressBarContainerVisible = function(show) {
    if (show == null) {
        return false;
    }

    // FIX: Remove "display:block" from barContainer class and
    // use webui.@THEME@.common.setVisibleElement.

    var widget = dojo.widget.byId(this.id);
    if (show == false) {
        widget.barContainer.style.display = "none";
    } else {
        widget.barContainer.style.display = '';
    }
    return true; 
}

/**
 * This method displays the ProgressBar Container if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isProgressBarContainerVisible = function() {
    var widget = dojo.widget.byId(this.id);
    return webui.@THEME@.common.isVisibleElement(widget.barContainer);
}

/**
 * This method hides the Right Control.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setRightControlVisible = function(show) {
    if (show == null) {
        return false;
    }
    var widget = dojo.widget.byId(this.id);
    webui.@THEME@.common.setVisibleElement(widget.rightControlsContainer, show);
    return true;
}

/**
 * This method displays the Right Controls if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isRightControlVisible = function() {
    var widget = dojo.widget.byId(this.id);
    return webui.@THEME@.common.isVisibleElement(widget.rightControlsContainer);
}

/**
 * This method hides the Bottom Control.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setBottomControlVisible = function(show) {
    if (show == null) {
        return false;
    }
    var widget = dojo.widget.byId(this.id);
    webui.@THEME@.common.setVisibleElement(widget.bottomControlsContainer, show);
    return true;
}

/**
 * This method displays the Bottom Controls if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isBottomControlVisible = function() {
    var widget = dojo.widget.byId(this.id);
    return webui.@THEME@.common.isVisibleElement(widget.bottomControlsContainer);
}

/**
 * This method hides the status text.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setStatusTextVisible = function(show) {
    if (show == null) {
        return false;
    }
    var widget = dojo.widget.byId(this.id);
    webui.@THEME@.common.setVisibleElement(widget.bottomTextContainer, show);
    return true;
}

/**
 * This method displays the status text if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isStatusTextVisible = function() {
    var widget = dojo.widget.byId(this.id);
    return webui.@THEME@.common.isVisibleElement(widget.bottomTextContainer);
}

/**
 * This method hides the operation text.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setOperationTextVisible = function(show) {
    if (show == null) {
        return false;
    }
    var widget = dojo.widget.byId(this.id);
    webui.@THEME@.common.setVisibleElement(widget.topTextContainer, show);
    return true;
}

/**
 * This method displays the operation text if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isOperationTextVisible = function() {
    var widget = dojo.widget.byId(this.id);
    return webui.@THEME@.common.isVisibleElement(widget.topTextContainer);
}

/**
 * This method hides the log message area.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setLogMsgVisible = function(show) {
    if (show == null) {
        return false;
    }
    var widget = dojo.widget.byId(this.id);
    webui.@THEME@.common.setVisibleElement(widget.logContainer, show);
    return true;
}

/**
 * This method displays the log message if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isLogMsgVisible = function() {
    var widget = dojo.widget.byId(this.id);
    return webui.@THEME@.common.isVisibleElement(widget.logContainer);
}

/**
 * This method hides the failed state message and icon area.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setFailedStateMessageVisible = function(show) {
    if (show == null) {
        return false;
    }
    var widget = dojo.widget.byId(this.id);
    webui.@THEME@.common.setVisibleElement(widget.failedStateContainer, show);
    return true;
}

/**
 * This method displays the failed state message and icon if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isFailedStateMessageVisible = function() {
    var widget = dojo.widget.byId(this.id);
    return webui.@THEME@.common.isVisibleElement(widget.failedStateContainer);
}

/**
 * This function handles cancel button event.
 */
webui.@THEME@.widget.progressBar.cancel = function() {
    clearTimeout(this._timeoutId);

    var widget = dojo.widget.byId(this.id);
    widget.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.canceled;
    if (this._props.type == webui.@THEME@.widget.props.progressBar.determinate) {
        widget.innerBarContainer.style.width = "0%";
    }
    this._refresh();
}

/**
 * This function handles pause button event.
 */
webui.@THEME@.widget.progressBar.pause = function() {
    clearTimeout(this._timeoutId);

    var widget = dojo.widget.byId(this.id);
    widget.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.paused;
    if (this._props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        widget.innerBarContainer.className =
            webui.@THEME@.widget.props.progressBar.indeterminatePausedClassName;
    }
    this._refresh();
}

/**
 * This function handles resume button event.
 */
webui.@THEME@.widget.progressBar.resume = function() {
    clearTimeout(this._timeoutId);

    var widget = dojo.widget.byId(this.id);
    widget.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.resumed;
    if (this._props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        widget.innerBarContainer.className =
            webui.@THEME@.widget.props.progressBar.indeterminateClassName;
    }
    this._refresh();
}

/**
 * This function handles stop button event.
 */
webui.@THEME@.widget.progressBar.stop = function() {
    clearTimeout(this._timeoutId);

    var widget = dojo.widget.byId(this.id);
    widget.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.stopped;
    if (this._props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        widget.innerBarIdContainer.className =
            webui.@THEME@.widget.props.progressBar.indeterminatePaused;
    }
    this._refresh();
}

/**
 * This function invokes developer define function for complete event.
 * 
 * @param func The JavaScript function.
 */
webui.@THEME@.widget.progressBar.setOnComplete = function(func) {
    if (func) {
        this._funcComplete = func;
    }
}

/**
 * This function invokes developer define function for failed event.
 * 
 * @param func The JavaScript function.
 */
webui.@THEME@.widget.progressBar.setOnFail = function(func) {
    if (func) {
        this._funcFailed = func;
    }
}

/**
 * This function invokes developer define function for cancel event.
 * 
 * @param func The JavaScript function.
 */
webui.@THEME@.widget.progressBar.setOnCancel = function(func) {
    if (func) {
        this._funcCanceled = func;
    }
}

/**
 * This function sleeps for specified milli seconds.
 */
webui.@THEME@.widget.progressBar.sleep = function(delay) {
    var start = new Date();
    var exitTime = start.getTime() + delay;

    while(true) {
        start = new Date();
        if (start.getTime() > exitTime) {
            return;
        }
    }
}

/**
 * This closure is used to publish refresh events.
 */
webui.@THEME@.widget.progressBar.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_widget_progressBar_refresh_begin",
    endEventTopic: "webui_widget_progressBar_refresh_end",

    /**
     * Process refresh event.
     * 
     * @param evt Event generated by scroll bar.
     */
    processEvent: function(evt) {
        // Note: evt not currently implemented. For an example, see:
        // webui.@THEME@.widget.table2RowGroup.scroll.processEvent

        // publish event
        if (this._props.refreshRate > 0) {
            webui.@THEME@.widget.progressBar.refresh.publishBeginEvent(this.id);
        }

        // Create a call back function to periodically refresh the progress bar.
        this._timeoutId = setTimeout(
            webui.@THEME@.widget.progressBar.refresh.createCallback(this.id),
                this._props.refreshRate);
    },

    /**
     * Helper function to create callback to refresh server.
     *
     * @param id The HTML element id used to invoke the callback.
     */
    createCallback: function(id) {
        if (id != null) {
            // New literals are created every time this function
            // is called, and it's saved by closure magic.
            return function() {
                var domNode = document.getElementById(id);
                if (domNode == null) {
                    return null;
                } else {
                    domNode._refresh();
                }
            };
        }
    },

    /**
     * Publish an event for custom AJAX implementations to listen for.
     */
    publishBeginEvent: function(id) {
        dojo.event.topic.publish(webui.@THEME@.widget.progressBar.refresh.beginEventTopic, id);
        return true;
    },
    
    /**
     * Publish an event for custom AJAX implementations to listen for.
     *
     * @param props Key-Value pairs of properties of the widget.
     */
    publishEndEvent: function(props) {
        dojo.event.topic.publish(webui.@THEME@.widget.progressBar.refresh.endEventTopic, props);
        return true;
    }
}

/**
 * This function is used to set progress with the following Object
 * literals.
 *
 * <ul>
 *  <li>failedStateText</li>
 *  <li>logMessage</li>
 *  <li>progress</li>
 *  <li>status</li>
 *  <li>taskState</li>
 *  <li>topText</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.progressBar.setProgress = function(props) {
    if (props == null) {
        return false;
    }

    // Get widget.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Adjust max value.
    if (props.progress > 99 
            || props.taskState == webui.@THEME@.widget.props.progressBar.completed) {
        props.progress = 100;
    }

    // Save properties for later updates.
    if (this._props) {
        Object.extend(this._props, props); // Override existing values, if any.
    } else {
        this._props = props;
    }

    // Set status.
    if (props.status) {
        webui.@THEME@.widget.common.addFragment(widget.bottomTextContainer, props.status);
    }

    // If top text doesnt get change, dont update.
    if (props.topText) {
        if (props.topText != this._props.topText) {
            webui.@THEME@.widget.common.addFragment(widget.topTextContainer, props.topText);
        }
    }

    // Update log messages.
    if (this._props.type == webui.@THEME@.widget.props.progressBar.determinate) { 
        widget.innerBarContainer.style.width = props.progress + '%';

        if (props.logMessage) {
            var field = webui.@THEME@.field.getInputElement(this._props.logId)
            if (field != null) {
                field.value = (field.value)
                   ? field.value + props.logMessage + "\n"
                   : props.logMessage + "\n";
            }
        }

        // Add overlay text.
        if (this._props.overlayAnimation == true) {
            webui.@THEME@.widget.common.addFragment(widget.innerBarOverlayContainer,
                props.progress + "%");
        }
    } 

    // Failed state.
    if (props.taskState == webui.@THEME@.widget.props.progressBar.failed) {
        clearTimeout(this._timeoutId);
        this._sleep(1000);
        this.setProgressBarContainerVisible(false);
        this.setBottomControlVisible(false);
        this.setRightControlVisible(false);
        this.setLogMsgVisible(false);

        if (props.failedStateText != null) {
            var text = props.failedStateText + " " + props.progress + this._props.percentChar;
            webui.@THEME@.widget.common.addFragment(widget.failedLabelContainer, text);
            webui.@THEME@.common.setVisibleElement(widget.failedLabelContainer, true);
            webui.@THEME@.common.setVisibleElement(widget.failedStateContainer, true);
        }
        if (this._funcFailed != null) {
            (this._funcFailed)();
        }
        return;
    }

    // Cancel state.
    if (props.taskState == webui.@THEME@.widget.props.progressBar.canceled) {
        clearTimeout(this._timeoutId);
        this._sleep(1000);
        this.setOperationTextVisible(false);
        this.setStatusTextVisible(false);
        this.setProgressBarContainerVisible(false);
        this.setBottomControlVisible(false);
        this.setRightControlVisible(false);
        this.setLogMsgVisible(false);

        if (this._props.type == webui.@THEME@.widget.props.progressBar.determinate) {
            widget.innerBarContainer.style.width = "0%";
        }
        if (this._funcCanceled != null) {
           (this._funcCanceled)(); 
        }
        return;    
    }

    // paused state
    if (props.taskState == webui.@THEME@.widget.props.progressBar.paused) {
        clearTimeout(this._timeoutId);
        return;
    }

    // stopped state
    if (props.taskState == webui.@THEME@.widget.props.progressBar.stopped) {
        clearTimeout(this._timeoutId);
        return;
    }

    if (props.progress > 99 
            || props.taskState == webui.@THEME@.widget.props.progressBar.completed) {
        clearTimeout(this._timeoutId);
        if (this._props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
            widget.innerBarContainer.className =
                webui.@THEME@.widget.props.progressBar.indeterminatePausedClassName;
        }
        if (this._props.type == webui.@THEME@.widget.props.progressBar.busy) {
            this.setProgressBarContainerVisible(false);
        }
        if (this._funcComplete != null) {
           (this._funcComplete)(); 
        }
    }

    // Set progress for A11Y.
    if (props.progress) {
        if (widget.bottomTextContainer.setAttributeNS) {
            widget.bottomTextContainer.setAttributeNS("http://www.w3.org/2005/07/aaa",
                "valuenow", props.progress);
        }
    }
}

/**
 * This function is used to set widget properties with the
 * following Object literals.
 *
 * <ul>
 *  <li>barHeight</li>
 *  <li>barWidth</li>
 *  <li>bottomText</li>
 *  <li>busyImage</li>
 *  <li>failedStateText</li>
 *  <li>id</li>
 *  <li>log</li>
 *  <li>logId</li>
 *  <li>logMessage</li>
 *  <li>overlayAnimation</li>
 *  <li>percentChar</li>
 *  <li>progress</li>
 *  <li>progressImageUrl</li>
 *  <li>progressControlBottom</li>
 *  <li>progressControlRight</li>
 *  <li>refreshRate</li>
 *  <li>taskState</li>
 *  <li>templatePath</li>
 *  <li>toolTip</li>
 *  <li>topText</li>
 *  <li>type</li>
 *  <li>visible</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.progressBar.setProps = function(props) {
    if (props == null) {
        return false;
    }

    // Save properties for later updates.
    if (this._props) {
        Object.extend(this._props, props); // Override existing values, if any.
    } else {
        this._props = props;
    }

    // Set DOM node properties.
    webui.@THEME@.widget.common.setCoreProperties(this, props);
    webui.@THEME@.widget.common.setJavaScriptProperties(this, props);

    // Set widget properties.
    var widget = dojo.widget.byId(this.id);
    if (widget == null) {
        return false;
    }

    // Set tool tip.
    if (props.toolTip) {
        widget.barContainer.title = props.toolTip;
    }

    // Add top text.
    if (props.topText) {
        webui.@THEME@.widget.common.addFragment(widget.topTextContainer, props.topText); 
        webui.@THEME@.common.setVisibleElement(widget.topTextContainer, true);
    }

    // Add bottom text.
    if (props.bottomText) {
        webui.@THEME@.widget.common.addFragment(widget.bottomTextContainer, props.bottomText);
        webui.@THEME@.common.setVisibleElement(widget.bottomTextContainer, true);
    }

    if (this._props.type == webui.@THEME@.widget.props.progressBar.determinate 
            || this._props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        // Set style class.
        widget.barContainer.className =
            webui.@THEME@.widget.props.progressBar.barContainerClassName;

        // Set height.
        if (props.barHeight != null && props.barHeight > 0) {
            widget.barContainer.style.height = props.barHeight + "px;"; 
            widget.innerBarContainer.style.height = props.barHeight + "px;";
        }

        // Set width.
        if (props.barWidth != null && props.barWidth > 0) {
            widget.barContainer.style.width = props.barWidth + "px;";
        }

        // Add right controls.
        if (props.progressControlRight != null) {
            webui.@THEME@.widget.common.addFragment(widget.rightControlsContainer,
                props.progressControlRight);
            webui.@THEME@.common.setVisibleElement(widget.rightControlsContainer, true);
        }

        // Add bottom controls.
        if (props.progressControlBottom != null) {
            webui.@THEME@.widget.common.addFragment(widget.bottomControlsContainer,
                props.progressControlBottom);
            webui.@THEME@.common.setVisibleElement(widget.bottomControlsContainer, true);
        }
    }

    if (this._props.type == webui.@THEME@.widget.props.progressBar.determinate) {
        // Set style class.
        widget.innerBarContainer.className =
            webui.@THEME@.widget.props.progressBar.determinateClassName;

        // Set width.
        widget.innerBarContainer.style.width = this._props.progress + '%';

        // Add overlay.
        if (props.overlayAnimation == true) {
            webui.@THEME@.widget.common.addFragment(widget.innerBarOverlayContainer,
                this._props.progress + "%");
            webui.@THEME@.common.setVisibleElement(widget.innerBarOverlayContainer, true);
        }

        // Add log.
        if (props.log != null && props.overlayAnimation == false) { 
            webui.@THEME@.widget.common.addFragment(widget.logContainer, props.log);
            webui.@THEME@.common.setVisibleElement(widget.logContainer, true);
        }  
    } else if (this._props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        // Set style class.
        widget.barContainer.className = 
            webui.@THEME@.widget.props.progressBar.barContainerClassName;
        widget.innerBarContainer.className = 
            webui.@THEME@.widget.props.progressBar.indeterminateClassName;
    } else if (this._props.type == webui.@THEME@.widget.props.progressBar.busy) {
        // Add busy image.
        if (props.busyImage) {
            webui.@THEME@.widget.common.addFragment(widget.busyImageContainer, props.busyImage);
            webui.@THEME@.common.setVisibleElement(widget.busyImageContainer, true);
        }
    }

    // Set developer specified image.
    if (props.progressImageUrl != null ) {
        widget.innerBarContainer.style.backgroundImage = 'url(' + props.progressImageUrl + ')';
    }

    // Set A11Y properties.
    if (widget.bottomTextContainer.setAttributeNS) {
        widget.bottomTextContainer.setAttributeNS("http://www.w3.org/2005/07/aaa",
            "valuenow", this._props.progress);
    }
    return true;
}

//-->
