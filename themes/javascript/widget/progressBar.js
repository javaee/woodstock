//
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
 * This function is used to generate a template based widget.
 *
 * Note: This is considered a private API, do not use.
 */
webui.@THEME@.widget.progressBar = function() {
    // Register widget.
    dojo.widget.HtmlWidget.call(this);
}

/**
 * This function handles cancel button event.
 */
webui.@THEME@.widget.progressBar.cancel = function() {
    clearTimeout(this.timeoutId);

    this.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.canceled;
    if (this.type == webui.@THEME@.widget.props.progressBar.determinate) {
        this.innerBarContainer.style.width = "0%";
    }
    this.updateProgress();
}

/**
 * This function is used to fill in template properties.
 *
 * Note: This is called after the buildRendering() function. Anything to be set 
 * only once should be added here; otherwise, use the setWidgetProps() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 */
webui.@THEME@.widget.progressBar.fillInTemplate = function(props, frag) {
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
    this.domNode.cancel = function() { return dojo.widget.byId(this.id).cancel(); }
    this.domNode.isBottomControlVisible = function() { return dojo.widget.byId(this.id).isBottomControlVisible(); }
    this.domNode.isFailedStateMessageVisible = function() { return dojo.widget.byId(this.id).isFailedStateMessageVisible(); }
    this.domNode.isLogMsgVisible = function() { return dojo.widget.byId(this.id).isLogMsgVisible(); }
    this.domNode.isOperationTextVisible = function() { return dojo.widget.byId(this.id).isOperationTextVisible(); }
    this.domNode.isProgressBarContainerVisible = function() { return dojo.widget.byId(this.id).isProgressBarContainerVisible(); }
    this.domNode.isProgressBarVisible = function() { return dojo.widget.byId(this.id).isProgressBarVisible(); }
    this.domNode.isRightControlVisible = function() { return dojo.widget.byId(this.id).isRightControlVisible(); }
    this.domNode.isStatusTextVisible = function() { return dojo.widget.byId(this.id).isStatusTextVisible(); }
    this.domNode.pause = function() { return dojo.widget.byId(this.id).pause(); }
    this.domNode.resume = function() { return dojo.widget.byId(this.id).resume(); }
    this.domNode.stop = function() { return dojo.widget.byId(this.id).stop(); }
    this.domNode.setOnCancel = function(func) { return dojo.widget.byId(this.id).setOnCancel(func); }
    this.domNode.setOnComplete = function(func) { return dojo.widget.byId(this.id).setOnComplete(func); }
    this.domNode.setOnFail = function(func) { return dojo.widget.byId(this.id).setOnFail(func); }
    this.domNode.setBottomControlVisible = function(show) { return dojo.widget.byId(this.id).setBottomControlVisible(show); }
    this.domNode.setFailedStateMessageVisible = function(show) { return dojo.widget.byId(this.id).setFailedStateMessageVisible(show); }
    this.domNode.setLogMsgVisible = function(show) { return dojo.widget.byId(this.id).setLogMsgVisible(show); }
    this.domNode.setOperationTextVisible = function(show) { return dojo.widget.byId(this.id).setOperationTextVisible(show); }
    this.domNode.setProgressBarContainerVisible = function(show) { return dojo.widget.byId(this.id).setProgressBarContainerVisible(show); }
    this.domNode.setProgressBarVisible = function(show) { return dojo.widget.byId(this.id).setProgressBarVisible(show); }
    this.domNode.setRightControlVisible = function(show) { return dojo.widget.byId(this.id).setRightControlVisible(show); }
    this.domNode.setStatusTextVisible = function(show) { return dojo.widget.byId(this.id).setStatusTextVisible(show); }

    // Set common functions.
    return webui.@THEME@.widget.progressBar.superclass.fillInTemplate.call(this, props, frag);
}

/**
 * This function is used to get widget properties. Please see the 
 * setWidgetProps() function for a list of supported properties.
 */
webui.@THEME@.widget.progressBar.getProps = function() {
    var props = webui.@THEME@.widget.progressBar.superclass.getProps.call(this);

    // Set properties.
    if (this.barHeight) { props.barHeight = this.barHeight; }
    if (this.barWidth) { props.barWidth = this.barWidth; }
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

    return props;
}

/**
 * This method displays the Bottom Controls if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isBottomControlVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this.bottomControlsContainer);
}

/**
 * This method displays the failed state message and icon if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isFailedStateMessageVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this.failedStateContainer);
}

/**
 * This method displays the log message if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isLogMsgVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this.logContainer);
}

/**
 * This method displays the operation text if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isOperationTextVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this.topTextContainer);
}

/**
 * This method displays the ProgressBar Container if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isProgressBarContainerVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this.barContainer);
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
 * This method displays the Right Controls if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isRightControlVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this.rightControlsContainer);
}

/**
 * This method displays the status text if it was hidden.
 *
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.isStatusTextVisible = function() {
    return webui.@THEME@.common.isVisibleElement(this.bottomTextContainer);
}

/**
 * This function handles pause button event.
 */
webui.@THEME@.widget.progressBar.pause = function() {
    clearTimeout(this.timeoutId);

    this.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.paused;
    if (this.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        this.innerBarContainer.className =
            webui.@THEME@.widget.props.progressBar.indeterminatePausedClassName;
    }
    this.updateProgress();
}

/**
 * This function is used after the widget has been initialized and rendered.
 *
 * Note: This is called after the postInitialize() function.
 *
 * @param props Key-Value pairs of properties.
 * @param frag HTML fragment.
 * @param parent The parent of this widget.
 */
webui.@THEME@.widget.progressBar.postCreate = function (props, frag, parent) {
    // Start timer to periodically publish progress events.
    this.updateProgress();

    return webui.@THEME@.widget.progressBar.superclass.postCreate.call(this, 
        props, frag, parent);
}

/**
 * This closure is used to publish progress events.
 */
webui.@THEME@.widget.progressBar.progress = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_progressBar_progress_begin",
    endEventTopic: "webui_@THEME@_widget_progressBar_progress_end",

    /**
     * Helper function to to periodically obtain progress.
     *
     * @param id The client id used to invoke the callback.
     */
    createCallback: function(id) {
        if (id != null) {
            // New literals are created every time this function
            // is called, and it's saved by closure magic.
            return function() {
                var widget = dojo.widget.byId(id);
                if (widget == null) {
                    return null;
                } else {
                    widget.updateProgress();
                }
            };
        }
    },

    /**
     * Process progress event.
     */
    processEvent: function() {
        // Publish event.
        if (this.refreshRate > 0) {
            // Include default AJAX implementation.
            this.ajaxify("webui.@THEME@.widget.jsfx.progressBar");

            // Publish an event for custom AJAX implementations to listen for.
            dojo.event.topic.publish(
                webui.@THEME@.widget.progressBar.progress.beginEventTopic, {
                    id: this.id
                });
        }

        // Create a call back function to periodically publish progress events.
        this.timeoutId = setTimeout(
            webui.@THEME@.widget.progressBar.progress.createCallback(this.id),
            this.refreshRate);
    }
}

/**
 * This closure is used to process refresh events.
 */
webui.@THEME@.widget.progressBar.refresh = {
    /**
     * Event topics for custom AJAX implementations to listen for.
     */
    beginEventTopic: "webui_@THEME@_widget_progressBar_refresh_begin",
    endEventTopic: "webui_@THEME@_widget_progressBar_refresh_end",
 
    /**
     * Process refresh event.
     *
     * @param execute The string containing a comma separated list of client ids 
     * against which the execute portion of the request processing lifecycle
     * must be run.
     */
    processEvent: function(execute) {
        // Include default AJAX implementation.
        this.ajaxify("webui.@THEME@.widget.jsfx.progressBar");

        // Publish an event for custom AJAX implementations to listen for.
        dojo.event.topic.publish(
            webui.@THEME@.widget.progressBar.refresh.beginEventTopic, {
                id: this.id,
                execute: execute,
                endEventTopic: webui.@THEME@.widget.progressBar.refresh.endEventTopic
            });
        return true;
    }
}

/**
 * This function handles resume button event.
 */
webui.@THEME@.widget.progressBar.resume = function() {
    clearTimeout(this.timeoutId);

    this.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.resumed;
    if (this.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        this.innerBarContainer.className =
            webui.@THEME@.widget.props.progressBar.indeterminateClassName;
    }
    this.updateProgress();
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
    webui.@THEME@.common.setVisibleElement(this.bottomControlsContainer, show);
    return true;
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
    webui.@THEME@.common.setVisibleElement(this.failedStateContainer, show);
    return true;
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
    webui.@THEME@.common.setVisibleElement(this.logContainer, show);
    return true;
}

/**
 * This function invokes developer define function for cancel event.
 * 
 * @param func The JavaScript function.
 */
webui.@THEME@.widget.progressBar.setOnCancel = function(func) {
    if (func) {
        this.funcCanceled = func;
    }
}

/**
 * This function invokes developer define function for complete event.
 * 
 * @param func The JavaScript function.
 */
webui.@THEME@.widget.progressBar.setOnComplete = function(func) {
    if (func) {
        this.funcComplete = func;
    }
}

/**
 * This function invokes developer define function for failed event.
 * 
 * @param func The JavaScript function.
 */
webui.@THEME@.widget.progressBar.setOnFail = function(func) {
    if (func) {
        this.funcFailed = func;
    }
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
    webui.@THEME@.common.setVisibleElement(this.topTextContainer, show);
    return true;
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
 *  <li>type</li>
 * </ul>
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.progressBar.setProgress = function(props) {
    if (props == null) {
        return false;
    }

    // Adjust max value.
    if (props.progress > 99 
            || props.taskState == webui.@THEME@.widget.props.progressBar.completed) {
        props.progress = 100;
    }

    // Save properties for later updates.
    this.extend(this, props);    

    // Set status.
    if (props.status) {
        this.addFragment(this.bottomTextContainer, props.status);
    }

    // If top text doesnt get change, dont update.
    if (props.topText) {
        if (props.topText != this.topText) {
            this.addFragment(this.topTextContainer, props.topText);
        }
    }

    // Update log messages.
    if (this.type == webui.@THEME@.widget.props.progressBar.determinate) { 
        this.innerBarContainer.style.width = props.progress + '%';

        if (props.logMessage) {
            var field = webui.@THEME@.field.getInputElement(this.logId)
            if (field != null) {
                field.value = (field.value)
                   ? field.value + props.logMessage + "\n"
                   : props.logMessage + "\n";
            }
        }

        // Add overlay text.
        if (this.overlayAnimation == true) {
            // NOTE: If you set this value manually, text must be HTML escaped.
            this.addFragment(this.innerBarOverlayContainer, props.progress + "%");
        }
    } 

    // Failed state.
    if (props.taskState == webui.@THEME@.widget.props.progressBar.failed) {
        clearTimeout(this.timeoutId);
        this.sleep(1000);
        this.setProgressBarContainerVisible(false);
        this.setBottomControlVisible(false);
        this.setRightControlVisible(false);
        this.setLogMsgVisible(false);

        if (props.failedStateText != null) {
            // NOTE: If you set this value manually, text must be HTML escaped.
            this.addFragment(this.failedLabelContainer,
                props.failedStateText + " " + props.progress + this.percentChar);

            webui.@THEME@.common.setVisibleElement(this.failedLabelContainer, true);
            webui.@THEME@.common.setVisibleElement(this.failedStateContainer, true);
        }
        if (this.funcFailed != null) {
            (this.funcFailed)();
        }
        return;
    }

    // Cancel state.
    if (props.taskState == webui.@THEME@.widget.props.progressBar.canceled) {
        clearTimeout(this.timeoutId);
        this.sleep(1000);
        this.setOperationTextVisible(false);
        this.setStatusTextVisible(false);
        this.setProgressBarContainerVisible(false);
        this.setBottomControlVisible(false);
        this.setRightControlVisible(false);
        this.setLogMsgVisible(false);

        if (this.type == webui.@THEME@.widget.props.progressBar.determinate) {
            this.innerBarContainer.style.width = "0%";
        }
        if (this.funcCanceled != null) {
           (this.funcCanceled)(); 
        }
        return;    
    }

    // paused state
    if (props.taskState == webui.@THEME@.widget.props.progressBar.paused) {
        clearTimeout(this.timeoutId);
        return;
    }

    // stopped state
    if (props.taskState == webui.@THEME@.widget.props.progressBar.stopped) {
        clearTimeout(this.timeoutId);
        return;
    }

    if (props.progress > 99 
            || props.taskState == webui.@THEME@.widget.props.progressBar.completed) {
        clearTimeout(this.timeoutId);
        if (this.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
            this.innerBarContainer.className =
                webui.@THEME@.widget.props.progressBar.indeterminatePausedClassName;
        }
        if (this.type == webui.@THEME@.widget.props.progressBar.busy) {
            this.setProgressBarContainerVisible(false);
        }
        if (this.funcComplete != null) {
           (this.funcComplete)(); 
        }
    }

    // Set progress for A11Y.
    if (props.progress) {
        if (this.bottomTextContainer.setAttributeNS) {
            this.bottomTextContainer.setAttributeNS("http://www.w3.org/2005/07/aaa",
                "valuenow", props.progress);
        }
    }
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

    if (show == false) {
        this.barContainer.style.display = "none";
    } else {
        this.barContainer.style.display = '';
    }
    return true; 
}

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
 * This method hides the Right Control.
 *
 * @param show true to show the element, false to hide the element.
 * @return true if successful; otherwise, false.
 */
webui.@THEME@.widget.progressBar.setRightControlVisible = function(show) {
    if (show == null) {
        return false;
    }
    webui.@THEME@.common.setVisibleElement(this.rightControlsContainer, show);
    return true;
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
    webui.@THEME@.common.setVisibleElement(this.bottomTextContainer, show);
    return true;
}

/**
 * This function is used to set widget properties with the following 
 * Object literals.
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
 *  <li>toolTip</li>
 *  <li>topText</li>
 *  <li>type</li>
 *  <li>visible</li>
 * </ul>
 *
 * Note: This function should only be invoked through setProps(). Further, the
 * widget shall be updated only for the given key-value pairs.
 *
 * @param props Key-Value pairs of properties.
 */
webui.@THEME@.widget.progressBar.setWidgetProps = function(props) {
    if (props == null) {
        return false;
    }

    // Set tool tip.
    if (props.toolTip) {
        this.barContainer.title = props.toolTip;
    }

    // Add top text.
    if (props.topText) {
        this.addFragment(this.topTextContainer, props.topText); 
        webui.@THEME@.common.setVisibleElement(this.topTextContainer, true);
    }

    // Add bottom text.
    if (props.bottomText) {
        this.addFragment(this.bottomTextContainer, props.bottomText);
        webui.@THEME@.common.setVisibleElement(this.bottomTextContainer, true);
    }

    if (props.type == webui.@THEME@.widget.props.progressBar.determinate 
            || props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        // Set style class.
        this.barContainer.className =
            webui.@THEME@.widget.props.progressBar.barContainerClassName;

        // Set height.
        if (props.barHeight != null && props.barHeight > 0) {
            this.barContainer.style.height = props.barHeight + "px;"; 
            this.innerBarContainer.style.height = props.barHeight + "px;";
        }

        // Set width.
        if (props.barWidth != null && props.barWidth > 0) {
            this.barContainer.style.width = props.barWidth + "px;";
        }

        // Add right controls.
        if (props.progressControlRight != null) {
            this.addFragment(this.rightControlsContainer, props.progressControlRight);
            webui.@THEME@.common.setVisibleElement(this.rightControlsContainer, true);
        }

        // Add bottom controls.
        if (props.progressControlBottom != null) {
            this.addFragment(this.bottomControlsContainer, props.progressControlBottom);
            webui.@THEME@.common.setVisibleElement(this.bottomControlsContainer, true);
        }
    }

    if (props.type == webui.@THEME@.widget.props.progressBar.determinate) {
        // Set style class.
        this.innerBarContainer.className =
            webui.@THEME@.widget.props.progressBar.determinateClassName;

        // Set width.
        this.innerBarContainer.style.width = this.progress + '%';

        // Add overlay.
        if (props.overlayAnimation == true) {
            // NOTE: If you set this value manually, text must be HTML escaped.
            this.addFragment(this.innerBarOverlayContainer, this.progress + "%");
            webui.@THEME@.common.setVisibleElement(this.innerBarOverlayContainer, true);
        }

        // Add log.
        if (props.log != null && props.overlayAnimation == false) { 
            this.addFragment(this.logContainer, props.log);
            webui.@THEME@.common.setVisibleElement(this.logContainer, true);
        }  
    } else if (props.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        // Set style class.
        this.barContainer.className = 
            webui.@THEME@.widget.props.progressBar.barContainerClassName;
        this.innerBarContainer.className = 
            webui.@THEME@.widget.props.progressBar.indeterminateClassName;
    } else if (props.type == webui.@THEME@.widget.props.progressBar.busy) {
        // Add busy image.
        if (props.busyImage) {
            this.addFragment(this.busyImageContainer, props.busyImage);
            webui.@THEME@.common.setVisibleElement(this.busyImageContainer, true);
        }
    }

    // Set developer specified image.
    if (props.progressImageUrl != null ) {
        this.innerBarContainer.style.backgroundImage = 'url(' + props.progressImageUrl + ')';
    }

    // Set A11Y properties.
    if (props.progress != null) {
        if (this.bottomTextContainer.setAttributeNS) {
            this.bottomTextContainer.setAttributeNS(
                "http://www.w3.org/2005/07/aaa", "valuenow", this.progress);
        }
    }

    // Set more properties..
    this.setCommonProps(this.domNode, props);

    // Set core props.
    return webui.@THEME@.widget.progressBar.superclass.setWidgetProps.call(this, props);
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
 * This function handles stop button event.
 */
webui.@THEME@.widget.progressBar.stop = function() {
    clearTimeout(this.timeoutId);

    this.hiddenFieldNode.value = webui.@THEME@.widget.props.progressBar.stopped;
    if (this.type == webui.@THEME@.widget.props.progressBar.indeterminate) {
        this.innerBarIdContainer.className =
            webui.@THEME@.widget.props.progressBar.indeterminatePaused;
    }
    this.updateProgress();
}

// Inherit base widget properties.
dojo.inherits(webui.@THEME@.widget.progressBar, webui.@THEME@.widget.widgetBase);

// Override base widget by assigning properties to class prototype.
dojo.lang.extend(webui.@THEME@.widget.progressBar, {
    // Set private functions.
    cancel: webui.@THEME@.widget.progressBar.cancel,
    fillInTemplate: webui.@THEME@.widget.progressBar.fillInTemplate,
    getProps:  webui.@THEME@.widget.progressBar.getProps,
    isBottomControlVisible: webui.@THEME@.widget.progressBar.isBottomControlVisible,
    isFailedStateMessageVisible: webui.@THEME@.widget.progressBar.isFailedStateMessageVisible,
    isLogMsgVisible: webui.@THEME@.widget.progressBar.isLogMsgVisible,
    isOperationTextVisible: webui.@THEME@.widget.progressBar.isOperationTextVisible,
    isProgressBarContainerVisible: webui.@THEME@.widget.progressBar.isProgressBarContainerVisible,
    isProgressBarVisible: webui.@THEME@.widget.progressBar.isProgressBarVisible,
    isRightControlVisible: webui.@THEME@.widget.progressBar.isRightControlVisible,
    isStatusTextVisible: webui.@THEME@.widget.progressBar.isStatusTextVisible,
    pause: webui.@THEME@.widget.progressBar.pause,
    postCreate: webui.@THEME@.widget.progressBar.postCreate,
    refresh: webui.@THEME@.widget.progressBar.refresh.processEvent,
    resume: webui.@THEME@.widget.progressBar.resume,
    stop: webui.@THEME@.widget.progressBar.stop,
    setOnCancel: webui.@THEME@.widget.progressBar.setOnCancel,
    setOnComplete: webui.@THEME@.widget.progressBar.setOnComplete,
    setOnFail: webui.@THEME@.widget.progressBar.setOnFail,
    setBottomControlVisible: webui.@THEME@.widget.progressBar.setBottomControlVisible,
    setFailedStateMessageVisible: webui.@THEME@.widget.progressBar.setFailedStateMessageVisible,
    setLogMsgVisible: webui.@THEME@.widget.progressBar.setLogMsgVisible,
    setOperationTextVisible: webui.@THEME@.widget.progressBar.setOperationTextVisible,
    setProgress: webui.@THEME@.widget.progressBar.setProgress,
    setProgressBarContainerVisible: webui.@THEME@.widget.progressBar.setProgressBarContainerVisible,
    setProgressBarVisible: webui.@THEME@.widget.progressBar.setProgressBarVisible,
    setWidgetProps: webui.@THEME@.widget.progressBar.setWidgetProps,
    sleep: webui.@THEME@.widget.progressBar.sleep,
    setRightControlVisible: webui.@THEME@.widget.progressBar.setRightControlVisible,
    setStatusTextVisible: webui.@THEME@.widget.progressBar.setStatusTextVisible,
    updateProgress: webui.@THEME@.widget.progressBar.progress.processEvent,

    // Set defaults.
    percentChar: "%",
    progress: 0,
    templatePath: webui.@THEME@.theme.getTemplatePath("progressBar"),
    templateString: webui.@THEME@.theme.getTemplateString("progressBar"),
    type: webui.@THEME@.widget.props.progressBar.determinate,
    widgetType: "progressBar"
});
