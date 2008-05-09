/*
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
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */
package com.sun.webui.jsf.component;

import com.sun.faces.annotation.Component;
import com.sun.faces.annotation.Property;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

/**
 * The ProgressBar component is used to create a progress indicator.
 * <p>
 * Progress Bar component consists of three progress bar designs: "Simple
 * Determinate", "Indeterminate" and a "Busy" indicator. The "Simple 
 * Determinate" and "Indeterminate" progress bars are represented as graphical 
 * bars. The "Simple Determinate" progress bar is used to represent the 
 * percentage of given task that has been completed. The "Indeterminate" 
 * progress bar is used when estimates of task completion cannot be provided, 
 * yet the task is being performed. Progress bars may also include a textual 
 * description of the operation; a textual description of the current operation
 * status; and any related controls such as a "Pause", "Resume" and/or "Cancel"
 * buttons to halt the associated task or job. The "Busy" indicator is used when
 * space is very constrained.
 * </p>    
 * <p>In the rendered HTML page, the progressbar is created with 
 * <code>&lt;div&gt;</code> elements. The progress consists of the following 
 * areas:</p>
 * <ul>
 * <li>
 * <p>
 * An optional textual "operation description" element that describes the 
 * overall operation being monitored.
 * </p><p>
 * A dynamic, graphical "progress animation" element that updates as the 
 * operation progresses and  has a default height of 14 pixels and width of 
 * 184 pixels.
 * </p><p>
 * An optional log message textarea  that can be used to display log messages.
 * </p><p>
 * An optional control element that can be used to provide a stop control, pause
 * and resume controls, cancel control or the like (customizable by developer)
 * </p><p>
 * An optional textual "status" element that provides a dynamic, written 
 * description of the current state of the operation.
 * </p> 
 * </li>
 * </ul>
 */
@Component(type="com.sun.webui.jsf.ProgressBar", family="com.sun.webui.jsf.ProgressBar",
    displayName="ProgressBar", tagName="progressBar",
    helpKey="projrave_ui_elements_palette_wdstk-jsf1_2_progressbar",
    propertiesHelpKey="projrave_ui_elements_palette_wdstk-jsf1_2_propsheets_progress_bar_props",
    tagRendererType="com.sun.webui.jsf.widget.ProgressBar")
public class ProgressBar extends javax.faces.component.UIOutput  
        implements NamingContainer {
    
    /** The facet name for the top text. */
    public static final String TOPTEXT_FACET = "progressTextTop"; //NOI18N
    
    /** The facet name for the bottom text. */
    public static final String BOTTOMTEXT_FACET = "progressTextBottom"; //NOI18N
    
    /** The facet name for the right control. */
    public static final String RIGHTTASK_CONTROL_FACET = "progressControlRight"; //NOI18N
    
    /** The facet name for the left control. */
    public static final String BOTTOMTASK_CONTROL_FACET = "progressControlBottom"; //NOI18N
    
    /* Task states.*/
    public static String TASK_COMPLETED = "completed"; //NOI18N
    public static String TASK_PAUSED = "paused"; //NOI18N
    public static String TASK_STOPPED = "stopped"; //NOI18N
    public static String TASK_RUNNING = "running"; //NOI18N
    public static String TASK_RESUMED = "resumed"; //NOI18N
    public static String TASK_CANCELED = "canceled"; //NOI18N
    public static String TASK_FAILED = "failed"; //NOI18N
    public static String TASK_NOT_STARTED = "not_started"; //NOI18N
    
    /* Types of ProgressBar. */
    public static String DETERMINATE = "DETERMINATE"; //NOI18N
    public static String INDETERMINATE = "INDETERMINATE"; //NOI18N
    public static String BUSY_INDICATOR = "BUSY"; //NOI18N

    /** The component id for the actions separator icon. */
    public static final String BUSY_ICON_ID = "_busyImage"; //NOI18N

    /** Creates a new instance of ProgressBar */
    public ProgressBar() {
        super();
        setRendererType("com.sun.webui.jsf.widget.ProgressBar");
    }

    public FacesContext getContext() {
        return getFacesContext();
    }
    
    /**
     * <p>Return the family for this component.</p>
     */
    public String getFamily() {
        return "com.sun.webui.jsf.ProgressBar";
    }

    public String getRendererType() {
        // Ensure we have a valid Ajax request.
        if (ComponentUtilities.isAjaxRequest(getFacesContext(), this)) {
            return "com.sun.webui.jsf.ajax.ProgressBar";
        }
        return super.getRendererType();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Icon methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Get the busy icon.
     *
     * @return The busy icon.
     * @deprecated  
     */
    public UIComponent getBusyIcon() {
        Theme theme = ThemeUtilities.getTheme(getFacesContext());

        // Get child.
        Icon child = ThemeUtilities.getIcon(theme, ThemeImages.PROGRESS_BUSY);
	child.setId(BUSY_ICON_ID);
        child.setBorder(0);

        // Save facet and return child.
        getFacets().put(child.getId(), child); 
        return child;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Tag attribute methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * The component identifier for this component. This value must be unique 
     * within the closest parent component that is a naming container.
     */
    @Property(name="id") 
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * Use the rendered attribute to indicate whether the HTML code for the
     * component should be included in the rendered HTML page. If set to false,
     * the rendered HTML page does not include the HTML for the component. If
     * the component is not rendered, it is also not processed on any subsequent
     * form submission.
     */
    @Property(name="rendered") 
    public void setRendered(boolean rendered) {
        super.setRendered(rendered);
    }

    // Overwrite value annotation
    @Property(isHidden=true, isAttribute=false)
    public Object getValue() {
        return super.getValue();
    }
    
    // Hide converter
    @Property(isHidden=true, isAttribute=false)
    public Converter getConverter() {
        return super.getConverter();
    }

    /**
     * This function creates one textarea component for displaying
     * log messages.
     * @return UIComponent
     */
    public UIComponent getLogMsgComponent(ProgressBar component) {
        //textArea for running log
        TextArea textArea = new TextArea();
        textArea.setParent(component);            
        textArea.setId("logMsg");
        textArea.setRows(4);
        textArea.setColumns(62);

        return textArea;
    }

    /**
     * Alternative HTML template to be used by this component.
     */
    @Property(name="htmlTemplate", isHidden=true, isAttribute=true, displayName="HTML Template", category="Appearance")
    private String htmlTemplate = null;

    /**
     * Get alternative HTML template to be used by this component.
     */
    public String getHtmlTemplate() {
        if (this.htmlTemplate != null) {
            return this.htmlTemplate;
        }
        ValueExpression _vb = getValueExpression("htmlTemplate");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * Set alternative HTML template to be used by this component.
     */
    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    @Property(name="toolTip", displayName="toolTip Text", category="Behavior")
    private String toolTip = null;
    
    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     */
    public String getToolTip() {
        if (this.toolTip != null) {
            return this.toolTip;
        }
        ValueBinding _vb = getValueBinding("toolTip");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>Sets the value of the title attribute for the HTML element.
     * The specified text will display as a tooltip if the mouse cursor hovers 
     * over the HTML element.</p>
     * @see #getToolTip()
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
    
    /**
     * <p>Text to describe the operation that is monitored by the progress bar. </p>
     */
    @Property(name="description", displayName="description", category="Appearance")
    private String description = null;
    
    /**
     * <p>Text to describe the operation that is monitored by the progress bar. </p>
     */
    public String getDescription() {
        if (this.description != null) {
            return this.description;
        }
        ValueBinding _vb = getValueBinding("description");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>Text to describe the operation that is monitored by the progress bar. </p>
     * @see #getDescription()
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * <p>Text to be displayed along with an icon when the task fails. </p>
     */
    @Property(name="failedStateText", displayName="Failed State Text", category="Appearance")
    private String failedStateText = null;
    
    /**
     * <p>Text to be displayed along with an icon when the task fails. </p>
     */
    public String getFailedStateText() {
        ValueBinding _vb = getValueBinding("failedStateText");
        // set the default text for failed state if failedStateText is null
        if(_vb == null && this.failedStateText == null) {
          this.failedStateText =  ThemeUtilities.getTheme(FacesContext.getCurrentInstance())
                                 .getMessage("ProgressBar.failedText");
        }
        if (this.failedStateText != null) {
            return this.failedStateText;
        }
        
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>Text to be displayed along with an icon when the task fails. </p>
     * @see #getFailedStateText()
     */
    public void setFailedStateText(String failedStateText) {
        this.failedStateText = failedStateText;
    }
    
    /**
     * <p>Number of pixels for the height of the progress bar animation.
     * The default is 14. </p>
     */
    @Property(name="height", displayName="Bar Height", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int height = Integer.MIN_VALUE;
    private boolean height_set = false;
    
    /**
     * <p>Number of pixels for the height of the progress bar animation.
     * The default is 14. </p>
     */
    public int getHeight() {
        if (this.height_set) {
            return this.height;
        }
        ValueBinding _vb = getValueBinding("height");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }
    
    /**
     * <p>Number of pixels for the height of the progress bar animation.
     * The default is 14. </p>
     * @see #getHeight()
     */
    public void setHeight(int height) {
        this.height = height;
        this.height_set = true;
    }
    
    /**
     * <p>Text to be displayed in a text area at the bottom of the 
     * progress bar component. </p>
     */
    @Property(name="logMessage", displayName="Log Message", category="Appearance")
    private String logMessage = null;
    
    /**
    * <p>Text to be displayed in a text area at the bottom of the 
     * progress bar component. </p>
     */
    public String getLogMessage() {
        if (this.logMessage != null) {
            return this.logMessage;
        }
        ValueBinding _vb = getValueBinding("logMessage");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>Text to be displayed in a text area at the bottom of the 
     * progress bar component. </p>
     * @see #getLogMessage()
     */
    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
    
    /**
     * <p>Set to true to display the operation progress text superimposed on the
     * progress bar animation. </p>   
     */
    @Property(name="overlayAnimation", displayName="Is overlayAnimation", category="Advanced")
    private boolean overlayAnimation = false;
    private boolean overlayAnimation_set = false;
    
    /**
     * <p>Set to true to display the operation progress text superimposed on the
     * progress bar animation. </p>
     */
    public boolean isOverlayAnimation() {
        if (this.overlayAnimation_set) {
            return this.overlayAnimation;
        }
        ValueBinding _vb = getValueBinding("overlayAnimation");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return false;
    }
    
    /**
     * <p>Set to true to display the operation progress text superimposed on the
     * progress bar animation. </p>
     * @see #isOverlayAnimation()
     */
    public void setOverlayAnimation(boolean overlayAnimation) {
        this.overlayAnimation = overlayAnimation;
        this.overlayAnimation_set = true;
    }
    
    /**
     * <p> Set to true to start the progressbar after widget creation. </p>   
     */
    @Property(name="autoStart", displayName="Auto Start", category="Advanced")
    private boolean autoStart = false;
    private boolean autoStart_set = false;
    
    /**
     * <p>Set to true to start the progressbar after widget creation. </p>
     */
    public boolean isAutoStart() {
        if (this.autoStart_set) {
            return this.autoStart;
        }
        ValueBinding _vb = getValueBinding("autoStart");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }
    
    /**
     * <p>Set to true to start the progressbar after widget creation. </p>
     * @see #isAutoStart()
     */
    public void setautoStart(boolean autoStart) {
        this.autoStart = autoStart;
        this.autoStart_set = true;
    }
    
    /**
     * <p>An integer that indicates the completion percentage of the task.</p>
     */
    @Property(name="progress", displayName="Progress", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int progress = Integer.MIN_VALUE;
    private boolean progress_set = false;
    
    /**
     * <p>An integer that indicates the completion percentage of the task.</p>
     */
    public int getProgress() {
        if (this.progress_set) {
            return this.progress;
        }
        ValueBinding _vb = getValueBinding("progress");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }
    
    /**
     * <p>An integer that indicates the completion percentage of the task.</p>
     * @see #getProgress()
     */
    public void setProgress(int progress) {
        this.progress = progress;
        this.progress_set = true;
    }
    
    /**
     * <p>URL to an image to use instead of the default image for the progress indicator.</p>
     */
    @Property(name="progressImageUrl", displayName="Image Url", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.UrlPropertyEditor")
    private String progressImageUrl = null;
    
    /**
     * <p>URL to an image to use instead of the default image for the progress indicator.</p>
     */
    public String getProgressImageUrl() {
        if (this.progressImageUrl != null) {
            return this.progressImageUrl;
        }
        ValueBinding _vb = getValueBinding("progressImageUrl");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>URL to an image to use instead of the default image for the progress indicator.</p>
     * @see #getProgressImageUrl()
     */
    public void setProgressImageUrl(String progressImageUrl) {
        this.progressImageUrl = progressImageUrl;
    }
    
    /**
     * <p>The number of milliseconds between updates to the progress bar. </p>
     */
    @Property(name="refreshRate", displayName="Refresh Rate", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int refreshRate = Integer.MIN_VALUE;
    private boolean refreshRate_set = false;
    
    /**
     * <p>The number of milliseconds between updates to the progress bar. </p>
     */
    public int getRefreshRate() {
        ValueBinding _vb = getValueBinding("refreshRate");
        //set the default refresh rate 3000 if refreshRate < 0
        if (_vb == null && this.refreshRate < 0) {
          this.refreshRate = 3000;
        }
        if (this.refreshRate_set) {
            return this.refreshRate;
        }
        
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return this.refreshRate;
    }
    
    /**
     * <p>The number of milliseconds between updates to the progress bar. </p>
     * @see #getRefreshRate()
     */
    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
        this.refreshRate_set = true;
    }
    
    /**
     * <p>Text to be displayed at the bottom of the progress bar, for the status of the operation.</p>
     */
    @Property(name="status", displayName="Status Text", category="Appearance")
    private String status = null;
    
    /**
     * <p>Text to be displayed at the bottom of the progress bar, for the status of the operation.</p>
     */
    public String getStatus() {
        if (this.status != null) {
            return this.status;
        }
        ValueBinding _vb = getValueBinding("status");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>Text to be displayed at the bottom of the progress bar, for the status of the operation.</p>
     * @see #getStatus()
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    @Property(name="style", displayName="CSS Style(s)", category="Appearance", 
              editorClassName="com.sun.jsfcl.std.css.CssStylePropertyEditor")
    private String style = null;
    
    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueBinding _vb = getValueBinding("style");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>CSS style or styles to be applied to the outermost HTML element when this
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }
    
    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    @Property(name="styleClass", displayName="CSS Style Class(es)", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.StyleClassPropertyEditor")
    private String styleClass = null;
    
    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueBinding _vb = getValueBinding("styleClass");
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>CSS style class or classes to be applied to the outermost HTML element when this
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * <p>Position of this element in the tabbing order of the current document.
     * Tabbing order determines the sequence in which elements receive
     * focus when the tab key is pressed. The value must be an integer
     * between 0 and 32767.</p>
     */
    @Property(name="tabIndex", displayName="Tab Index", category="Accessibility", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int tabIndex = Integer.MIN_VALUE;
    private boolean tabIndex_set = false;
    
    /**
     * <p>Position of this element in the tabbing order of the current document.
     * Tabbing order determines the sequence in which elements receive
     * focus when the tab key is pressed. The value must be an integer
     * between 0 and 32767.</p>
     */
    public int getTabIndex() {
        if (this.tabIndex_set) {
            return this.tabIndex;
        }
        ValueBinding _vb = getValueBinding("tabIndex");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }
    
    /**
     * <p>Position of this element in the tabbing order of the current document.
     * Tabbing order determines the sequence in which elements receive
     * focus when the tab key is pressed. The value must be an integer
     * between 0 and 32767.</p>
     * @see #getTabIndex()
     */
    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
        this.tabIndex_set = true;
    }
    
    /**
     * <p>A string representing the state of the task associated with this progress bar.
     * Valid values are:<br>
     * <code>not_started<br>
     * running<br>
     * paused<br>
     * resumed<br>
     * stopped<br>
     * canceled<br>
     * failed<br>
     * completed</code></p>
     */
    @Property(name="taskState", displayName="Task State", category="Advanced")
    private String taskState = null;
    
       /**
     * <p>A string representing the state of the task associated with this progress bar.
     * Default value for taskState is not_started.
     * Valid values are:</p>
     * <code>not_started<br>
     * running<br>
     * paused<br>
     * resumed<br>
     * stopped<br>
     * canceled<br>
     * failed<br>
     * completed</code><br>
     */
    public String getTaskState() {
        // set default state of task as not started and also if progress
        // is greater than zero and task state is not started then set
        // it to running state. Also if progress > 99 set taskState to
        // completed
        ValueBinding _vb = getValueBinding("taskState");
        
        if (_vb != null) {
            String value = (String) _vb.getValue(getFacesContext());
            if (value != null) {
                return value;
            }
        }
        if (this.taskState == null) {
            this.taskState = ProgressBar.TASK_NOT_STARTED;
        } else if (this.taskState.equals(ProgressBar.TASK_NOT_STARTED)
                    && this.progress > 0) {
            this.taskState = ProgressBar.TASK_RUNNING;
        } else if (!(this.taskState.equals(ProgressBar.TASK_COMPLETED))
                    && this.progress > 99) {
            this.taskState = ProgressBar.TASK_COMPLETED;
        }    
        
        if (this.taskState != null) {
            return this.taskState;
        }
               
        return null;
    }
    
    /**
     * <p>A string representing the state of the task associated with this progress bar.
     * Valid values are:</p>
     * <code>not_started<br>
     * running<br>
     * paused<br>
     * resumed<br>
     * stopped<br>
     * canceled<br>
     * failed<br>
     * completed</code><br>
     * @see #getTaskState()
     */
    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }
    
    /**
     * <p>Type of progress bar. Value must be one of the following:<br>
     * "DETERMINATE" for horizontal bar showing percent complete<br>
     * "INDETERMINATE" for horizontal bar without percent complete<br>
     * "BUSY" for simple activity indicator</p>
     */
    @Property(name="type", displayName="ProgressBar Type", category="Advanced")
    private String type = null;
    
    /**
     * <p>Type of progress bar. Value must be one of the following:<br>
     * "DETERMINATE" for horizontal bar showing percent complete<br>
     * "INDETERMINATE" for horizontal bar without percent complete<br>
     * "BUSY" for simple activity indicator</p>
     */
    public String getType() {
        ValueBinding _vb = getValueBinding("type");
        //set type attribute to DETERMINATE if type is null
        if (_vb == null && this.type == null) {
           this.type = ProgressBar.DETERMINATE;
        }
        if (this.type != null) {
            return this.type;
        }
        
        if (_vb != null) {
            return (String) _vb.getValue(getFacesContext());
        }
        return null;
    }
    
    /**
     * <p>Type of progress bar. Value must be one of the following:<br>
     * "DETERMINATE" for horizontal bar showing percent complete<br>
     * "INDETERMINATE" for horizontal bar without percent complete<br>
     * "BUSY" for simple activity indicator</p>
     * @see #getType()
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    @Property(name="visible", displayName="Visible", category="Behavior")
    private boolean visible = false;
    private boolean visible_set = false;
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     */
    public boolean isVisible() {
        if (this.visible_set) {
            return this.visible;
        }
        ValueBinding _vb = getValueBinding("visible");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return false;
            } else {
                return ((Boolean) _result).booleanValue();
            }
        }
        return true;
    }
    
    /**
     * <p>Use the visible attribute to indicate whether the component should be
     * viewable by the user in the rendered HTML page. If set to false, the
     * HTML code for the component is present in the page, but the component
     * is hidden with style attributes. By default, visible is set to true, so
     * HTML for the component HTML is included and visible to the user. If the
     * component is not visible, it can still be processed on subsequent form
     * submissions because the HTML is present.</p>
     * @see #isVisible()
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.visible_set = true;
    }
    
    /**
     * <p>Number of pixels for the width of the progress bar animation. The default
     * is 184.</p>
     */
    @Property(name="width", displayName="Bar Width", category="Appearance", 
              editorClassName="com.sun.rave.propertyeditors.IntegerPropertyEditor")
    private int width = Integer.MIN_VALUE;
    private boolean width_set = false;
    
    /**
     * <p>Number of pixels for the width of the progress bar animation. The default
     * is 184.</p>
     */
    public int getWidth() {
        if (this.width_set) {
            return this.width;
        }
        ValueBinding _vb = getValueBinding("width");
        if (_vb != null) {
            Object _result = _vb.getValue(getFacesContext());
            if (_result == null) {
                return Integer.MIN_VALUE;
            } else {
                return ((Integer) _result).intValue();
            }
        }
        return Integer.MIN_VALUE;
    }
    
    /**
     * <p>Number of pixels for the width of the progress bar animation. The default
     * is 184.</p>
     * @see #getWidth()
     */
    public void setWidth(int width) {
        this.width = width;
        this.width_set = true;
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext _context,Object _state) {
        Object _values[] = (Object[]) _state;
        super.restoreState(_context, _values[0]);
        this.toolTip = (String) _values[1];
        this.description = (String) _values[2];
        this.failedStateText = (String) _values[3];
        this.height = ((Integer) _values[4]).intValue();
        this.height_set = ((Boolean) _values[5]).booleanValue();
        this.logMessage = (String) _values[6];
        this.overlayAnimation = ((Boolean) _values[7]).booleanValue();
        this.overlayAnimation_set = ((Boolean) _values[8]).booleanValue();
        this.progress = ((Integer) _values[9]).intValue();
        this.progress_set = ((Boolean) _values[10]).booleanValue();
        this.progressImageUrl = (String) _values[11];
        this.refreshRate = ((Integer) _values[12]).intValue();
        this.refreshRate_set = ((Boolean) _values[13]).booleanValue();
        this.status = (String) _values[14];
        this.style = (String) _values[15];
        this.styleClass = (String) _values[16];
        this.tabIndex = ((Integer) _values[17]).intValue();
        this.tabIndex_set = ((Boolean) _values[18]).booleanValue();
        this.taskState = (String) _values[19];
        this.type = (String) _values[20];
        this.visible = ((Boolean) _values[21]).booleanValue();
        this.visible_set = ((Boolean) _values[22]).booleanValue();
        this.width = ((Integer) _values[23]).intValue();
        this.width_set = ((Boolean) _values[24]).booleanValue();
        this.htmlTemplate = (String) _values[25];
        this.autoStart = ((Boolean) _values[26]).booleanValue();
        this.autoStart_set = ((Boolean) _values[27]).booleanValue();
    }
    
    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext _context) {
        Object _values[] = new Object[28];
        _values[0] = super.saveState(_context);
        _values[1] = this.toolTip;
        _values[2] = this.description;
        _values[3] = this.failedStateText;
        _values[4] = new Integer(this.height);
        _values[5] = this.height_set ? Boolean.TRUE : Boolean.FALSE;
        _values[6] = this.logMessage;
        _values[7] = this.overlayAnimation ? Boolean.TRUE : Boolean.FALSE;
        _values[8] = this.overlayAnimation_set ? Boolean.TRUE : Boolean.FALSE;
        _values[9] = new Integer(this.progress);
        _values[10] = this.progress_set ? Boolean.TRUE : Boolean.FALSE;
        _values[11] = this.progressImageUrl;
        _values[12] = new Integer(this.refreshRate);
        _values[13] = this.refreshRate_set ? Boolean.TRUE : Boolean.FALSE;
        _values[14] = this.status;
        _values[15] = this.style;
        _values[16] = this.styleClass;
        _values[17] = new Integer(this.tabIndex);
        _values[18] = this.tabIndex_set ? Boolean.TRUE : Boolean.FALSE;
        _values[19] = this.taskState;
        _values[20] = this.type;
        _values[21] = this.visible ? Boolean.TRUE : Boolean.FALSE;
        _values[22] = this.visible_set ? Boolean.TRUE : Boolean.FALSE;
        _values[23] = new Integer(this.width);
        _values[24] = this.width_set ? Boolean.TRUE : Boolean.FALSE;
        _values[25] = this.htmlTemplate;
        _values[26] = this.autoStart ? Boolean.TRUE : Boolean.FALSE;
        _values[27] = this.autoStart_set ? Boolean.TRUE : Boolean.FALSE;
        return _values;
    }
}
