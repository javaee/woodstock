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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.Scheduler;
import com.sun.webui.jsf.component.Time;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import java.util.MissingResourceException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renders a guidelines compliant Scheduler component.</p>
 *
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Scheduler"))
public class SchedulerRenderer extends javax.faces.render.Renderer {
    
    // where is this used?
    private static final String SPACE = "&nbsp;"; //NOI18N
    private static final boolean DEBUG = false;
    
    /** No-op - everything happens in encodeEnd() **/
    public void encodeBegin(FacesContext context, UIComponent component) {
        return;
    }
    
    /** This component manages its own children
     * @return true
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    /**
     * <p>While this renderer does render the Scheduler component's children, it
     * doesn't do so in encodeChildren.</p>
     *
     * @param context The FacesContext of the request
     * @param component The component associated with the
     * renderer. Must be a subclass of ListSelector.
     */
    public void encodeChildren(FacesContext context, UIComponent component)
    throws java.io.IOException {
        return;
    }
    
    /**
     * <p>Render the appropriate element start, depending on the value of the
     * <code>type</code> property.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     *
     * @exception IOException if an input/output error occurs
     */
    public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException {
        
        if(DEBUG) log("encodeEnd");
        
        if(!(component instanceof Scheduler)) {
            Object[] params = { component.toString(),
                this.getClass().getName(),
                Scheduler.class.getName() };
                String message = MessageUtil.getMessage(
                    "com.sun.webui.jsf.resources.LogMessages", //NOI18N
                    "Renderer.component", params);              //NOI18N
                throw new FacesException(message);
        }
        
        Scheduler scheduler = (Scheduler)component;
        Theme theme = ThemeUtilities.getTheme(context);
        ResponseWriter writer = context.getResponseWriter();
        String spacerPath = theme.getImagePath(ThemeImages.DOT);

        // render the outer div element.
        renderEnclosingDiv(scheduler, theme, context, writer);
        writer.writeText("\n", null); //NOI18N
       
        // open the table	
        renderOpenTable(scheduler, writer, null);
        writer.writeText("\n", null); //NOI18N
        writer.startElement("tr", scheduler);//NOI18N
        writer.writeText("\n", null);  //NOI18N
	
	// render date picker in the first column.
	renderDatePicker(scheduler, theme, spacerPath, writer, context);       

	// render the input controls in the second column.
        renderInputControls(context, scheduler, writer, theme, spacerPath);	        
        
        // close the table
        writer.endElement("td"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N       
        
        // end the enclosing div element
        writer.endElement("div"); //NOI18N
        
        renderJavaScript(context, scheduler, writer, theme);
    }
    
    private void renderEnclosingDiv(Scheduler scheduler, Theme theme,
		FacesContext context, ResponseWriter writer) 
		throws IOException {
        
        writer.startElement("div", scheduler);
        writer.writeAttribute("id", scheduler.getClientId(context), null);
        String style = scheduler.getStyle();
        if(style != null) {
            writer.writeAttribute("style", style, "style");
        }
        style = scheduler.getStyleClass();
        if(scheduler.isVisible()) {
            if(style != null) {
                writer.writeAttribute("class", style, "styleClass");
            }
        }
        else { 
            style = style + " " + theme.getStyleClass(ThemeStyles.HIDDEN);
            writer.writeAttribute("class", style, "styleClass");
        }
    }
    
    /**
     * <p>Render a layout table.</p>
     *
     * @param writer The current ResponseWriter
     * @param comp The component instance
     * @param styleClass The styleClass for this component
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderOpenTable(UIComponent comp, ResponseWriter writer,
	    String styleClass) throws IOException {

        writer.startElement("table", comp);
	if (styleClass != null) {
            writer.writeAttribute("class", styleClass, null); //NOI18N
	}
        writer.writeAttribute("border", "0", null); //NOI18N
        writer.writeAttribute("cellpadding", "0", null); //NOI18N
        writer.writeAttribute("cellspacing", "0", null); //NOI18N
        writer.writeAttribute("title", "", null); //NOI18N
    }
    
    // Helper method to render the legend row. 
    private void renderLegendRow(Scheduler scheduler, Theme theme,
            ResponseWriter writer, FacesContext context, String spacerPath)
            throws IOException {
	    
        // This row consists of the legend and nothing else. 
        writer.writeText("\n", null);  //NOI18N
        writer.startElement("tr", scheduler); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        writer.startElement("td", scheduler); //NOI18N
        renderSpacerImage(scheduler, spacerPath, 1, 30, writer);
        writer.endElement("td"); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        writer.startElement("td", scheduler); //NOI18N
        writer.writeAttribute("colspan", "3", null); //NOI18N
        writer.writeAttribute("height", "25", null); //NOI18N
        writer.writeAttribute("valign", "top", null); //NOI18N
        
        if(scheduler.isRequiredLegend()) {
            writer.writeText("\n", null);  //NOI18N
            writer.startElement("div", scheduler); //NOI18N
            writer.writeAttribute("align", "left", null); //NOI18N
            writer.writeAttribute("class", //NOI18N
                    theme.getStyleClass(ThemeStyles.LABEL_REQUIRED_DIV), null);
            writer.writeText("\n", null);  //NOI18N

            Icon icon = ThemeUtilities.getIcon(theme,
			    ThemeImages.LABEL_REQUIRED_ICON);
            icon.setId(scheduler.getId().concat(Scheduler.ICON_ID));
	    // Need to set parent to get clientID
	    //
	    icon.setParent(scheduler);
            RenderingUtilities.renderComponent(icon, context);
            writer.writeText("\n", null);  //NOI18N
            writer.write(SPACE); //N0I18N
            writer.writeText(theme.getMessage("Scheduler.requiredLegend"),
		    null);  //NOI18N
            writer.writeText("\n", null);  //NOI18N
            writer.endElement("div");   //NOI18N
        } else {
            writer.write(SPACE); //NOI18N
        }
        writer.endElement("td");    //NOI18N
        writer.writeText("\n", null);  //NOI18N
        writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null);  //NOI18N
    }
        
    // It must be possible to do this using a style on the row instead! 
    private void renderSpacerRow(UIComponent component, String path,
            int height, int width, int colspan,
	    ResponseWriter writer)
            throws IOException {
        
        writer.startElement("tr", component); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        writer.startElement("td", component); //NOI18N
        writer.writeAttribute("colspan", String.valueOf(colspan), null); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        renderSpacerImage(component, path, height, width, writer);
        writer.endElement("td");      //NOI18N
        writer.writeText("\n", null);  //NOI18N
        writer.endElement("tr");       //NOI18N
        writer.writeText("\n", null);  //NOI18N
    }

    // Helper method to render a spacer image.
    private void renderSpacerImage(UIComponent component, String path,
	    int height, int width, ResponseWriter writer) throws IOException {

        writer.startElement("img", component); //NOI18N
        writer.writeAttribute("src", path, null); //NOI18N
        writer.writeAttribute("height", String.valueOf(height), null); //NOI18N
        writer.writeAttribute("width", String.valueOf(width), null); //NOI18N
        writer.writeAttribute("alt", "", null); //NOI18N
        writer.endElement("img");      //NOI18N
    }

    // Render date picker.
    private void renderDatePicker(Scheduler scheduler, Theme theme,
	    String spacerPath, ResponseWriter writer, FacesContext context)
	    throws IOException {

	writer.startElement("td", scheduler);           	    
        writer.writeAttribute("valign", "top", null); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        RenderingUtilities.renderComponent(scheduler.getDatePicker(), context);
        writer.endElement("td"); //NOI18N
        writer.writeText("\n", null);  //NOI18N
    }	
   
    /**
     * <p>Render the Scheduler component input controls such as the start date
     * field, start and end hour / minutes, etc.</p>
     *
     * @param context The current FacesContext
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param sourcePath Path to spacer image
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderInputControls(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme, String spacerPath) 
	    throws IOException {
	    
	writer.startElement("td", scheduler);           	    
        writer.writeAttribute("valign", "top", null); //NOI18N
        writer.writeText("\n", null);  //NOI18N

        // the input controls go in another layout table
	String styleClass = theme.getStyleClass(
			ThemeStyles.DATE_TIME_FIELD_TABLE);
        renderOpenTable(scheduler, writer, styleClass);
	
	// render the legend row.
	renderLegendRow(scheduler, theme, writer, context, spacerPath);
	
	// render the start date row.
        renderStartDateRow(scheduler, spacerPath, theme, context, writer);

	if(scheduler.isStartTime()) {
	    // render start time row
            // TODO - this is one of the cases where we have to check for 
            // private vs public facets!
            UIComponent label = scheduler.getStartTimeLabelComponent(theme);
            
            renderTimeRow(scheduler, label,
                    scheduler.getStartTimeComponent(),
                    theme,
                    spacerPath,
                    context,
                    writer);
        }

	if(scheduler.isEndTime()) {
	    // render end time row 
            // TODO - this is one of the cases where we have to check for 
            // private vs public facets!
            UIComponent label = scheduler.getEndTimeLabelComponent(theme);

            renderTimeRow(scheduler, label,
                    scheduler.getEndTimeComponent(),
                    theme,
                    spacerPath,
                    context,
                    writer);
        }

	if(scheduler.isRepeating()) {
		
            // render the repeat interval
            renderSpacerRow(scheduler, spacerPath, 5, 1, 4, writer);
            renderRepeatIntervalRow(scheduler, theme, context, writer);
            
            if (scheduler.isLimitRepeating()) {
                // render the repeat limit inputs 
                renderSpacerRow(scheduler, spacerPath, 5, 1, 4, writer);
                renderRepeatLimitRow(context, scheduler, writer, theme, spacerPath);
                renderRepeatLegend(scheduler,  theme, context, writer);
            }
        }

	if (scheduler.isPreviewButton()) {
            renderSpacerRow(scheduler, spacerPath, 30, 1, 4, writer);

            // render the preview input
            renderPreviewRow(context, scheduler, writer, theme);
        }
        
        writer.writeText("\n", null); //NOI18N
        writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    private void renderTimeRow(Scheduler scheduler, UIComponent label,
            Time time,
            Theme theme,
            String spacerPath,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {
        
        renderSpacerRow(scheduler, spacerPath, 5, 1, 4, writer);
        writer.writeText("\n", null);
        
	// tr td span 
        renderInputRowStart(scheduler, writer, theme);
        RenderingUtilities.renderComponent(label, context);
        writer.writeText("\n", null);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);
        RenderingUtilities.renderComponent(time, context);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.writeText("\n", null);
    }
   
    // Helper method to render the start date row. 
    private void renderStartDateRow(Scheduler scheduler, String spacerPath,
                                    Theme theme, FacesContext context, 
                                    ResponseWriter writer)
            throws IOException {
        
        UIComponent date = scheduler.getDateComponent();
        // TODO - this is one of the cases where we have to check for 
        // private vs public facets!
        UIComponent label = scheduler.getDateLabelComponent(theme);
        
        writer.startElement("tr", scheduler); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        
        // Spacer cell
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);  //NOI18N
        renderSpacerImage(scheduler, spacerPath, 1, 30, writer);
        writer.endElement("td");       //NOI18N
        writer.writeText("\n", null);  //NOI18N
        
        // Label cell
        writer.startElement("td", scheduler);
        writer.writeAttribute("nowrap", "nowrap", null); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        RenderingUtilities.renderComponent(label, context);
        writer.endElement("td");
        writer.writeText("\n", null);  //NOI18N
        
        // Spacer cell
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);  //NOI18N
        renderSpacerImage(scheduler, spacerPath, 1, 10, writer);
        writer.endElement("td");
        writer.writeText("\n", null);  //NOI18N
        
        // Field and help text cell
        writer.startElement("td", scheduler);
        writer.writeAttribute("nowrap", "nowrap", null); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        RenderingUtilities.renderComponent(date, context);
        writer.writeText("\n", null);  //NOI18N
        writer.startElement("span", scheduler);//NOI18N
        String styleClass = theme.getStyleClass(ThemeStyles.HELP_FIELD_TEXT);
        writer.writeAttribute("class", styleClass, null); //NOI18N
        writer.write(SPACE);
        writer.write(SPACE);
        writer.writeText(getPattern(scheduler, theme), null);
        writer.endElement("span"); //NOI18N
        writer.endElement("td");
        writer.writeText("\n", null);  //NOI18N
        
        writer.endElement("tr");
        writer.writeText("\n", null);  //NOI18N
    }
    
    private String getPattern(Scheduler scheduler, Theme theme) {
   
        String hint = scheduler.getDateFormatPatternHelp();
        if(hint == null) {
            try {  
                String pattern = scheduler.getDatePicker().getDateFormatPattern(); 
                hint = theme.getMessage("calendar.".concat(pattern)); 
            }
            catch(MissingResourceException mre) { 
                hint = ((SimpleDateFormat)(scheduler.getDateFormat())).toLocalizedPattern().toLowerCase();
            }
        }
        return hint;
    }
    
    /**
     * <p>Render the "Preview in Browser" button.</p>
     *
     * @param context The current FacesContext
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderPreviewRow(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme) throws IOException {
        writer.startElement("tr", scheduler);
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        writer.writeAttribute("colspan", "3", null); //NOI18N
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        UIComponent button = scheduler.getPreviewButtonComponent();
        RenderingUtilities.renderComponent(button, context);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.endElement("tr");
        writer.writeText("\n", null);
    }
    
    private void renderRepeatIntervalRow(Scheduler scheduler, Theme theme,
            FacesContext context, ResponseWriter writer)
            throws IOException {
        
        UIComponent label = scheduler.getRepeatIntervalLabelComponent();
        DropDown menu = scheduler.getRepeatIntervalComponent();

        // Spacer and label cells 
        renderInputRowStart(scheduler, writer, theme);
        RenderingUtilities.renderComponent(label, context);
        writer.writeText("\n", null);  //NOI18N
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);  //NOI18N
        
        // Spacer cell
        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");       //NOI18N
        writer.writeText("\n", null);  //NOI18N
        
        // Field cell
        writer.startElement("td", scheduler);
        writer.writeText("\n", null);  //NOI18N
        RenderingUtilities.renderComponent(menu, context);
        writer.endElement("td");
        writer.writeText("\n", null);  //NOI18N
        
        writer.endElement("tr");
        writer.writeText("\n", null);  //NOI18N
    }
    
    /**
     * <p>Convenience function to render the start of an input control row.<p>
     *
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderInputRowStart(Scheduler scheduler, ResponseWriter writer,
            Theme theme) throws IOException {
        writer.startElement("tr", scheduler);
        writer.writeText("\n", null); //NOI18N
        
        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null); //NOI18N
        writer.startElement("td", scheduler);
        writer.writeAttribute("nowrap", "nowrap", null); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.startElement("span", scheduler);
        String styleClass =
                theme.getStyleClass(ThemeStyles.DATE_TIME_LABEL_TEXT);
        writer.writeAttribute("class", styleClass, null); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    /**
     * <p>Render the repeat limit row.</p>
     *
     * @param context The current FacesContext
     * @param scheduler The Scheduler component instance
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderRepeatLimitRow(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme, String spacerPath)
	    throws IOException {
        
        UIComponent label = scheduler.getRepeatLimitLabelComponent();
        UIComponent field = scheduler.getRepeatingFieldComponent();
        DropDown menu = scheduler.getRepeatUnitComponent();

        // Spacer and label cells 
        renderInputRowStart(scheduler, writer, theme);
        RenderingUtilities.renderComponent(label, context);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);  //NOI18N
        
        writer.startElement("td", scheduler);
        writer.write(SPACE);
        writer.endElement("td");
        writer.writeText("\n", null);
        writer.startElement("td", scheduler);
        RenderingUtilities.renderComponent(field, context);
        writer.writeText("\n", null);
        RenderingUtilities.renderComponent(menu, context);
        writer.endElement("td");
        writer.writeText("\n", null);

        writer.endElement("tr");
        writer.writeText("\n", null);
    }
    
    private void renderRepeatLegend(Scheduler scheduler,
            Theme theme,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {

        renderInputRowStart(scheduler, writer, theme);
        writer.write(SPACE);
        writer.endElement("span");
        writer.endElement("td");
        writer.writeText("\n", null);
	
        writer.startElement("td", scheduler); //NOI18N
        writer.write(SPACE);
        writer.endElement("td"); //NOI18N
        writer.write("\n"); //NOI18N
        
        writer.startElement("td", scheduler); //NOI18N
	writer.writeAttribute("nowrap", "nowrap", null);   //NOI18N 
        writer.startElement("div",  scheduler);  //NOI18N 
        writer.writeAttribute("class", //NOI18N
		       theme.getStyleClass(ThemeStyles.HELP_FIELD_TEXT), null);   
        writer.writeText(theme.getMessage("Scheduler.blankForWhat"), //NOI18N
			null);
        writer.write("\n");  //NOI18N
        writer.endElement("div");  //NOI18N
        writer.endElement("td"); //NOI18N
        writer.write("\n");  //NOI18N
	
        writer.endElement("tr"); //NOI18N
        writer.write("\n");  //NOI18N
    }
       
    private void renderJavaScript(FacesContext context, Scheduler scheduler,
            ResponseWriter writer, Theme theme) throws IOException {
        try {
            // Append properties.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            json.put("id", scheduler.getClientId(context))
                .put("datePickerId", scheduler.getDatePicker().getClientId(context))
                .put("dateFieldId", scheduler.getDateComponent().getClientId(context))
                .put("dateClass", theme.getStyleClass(ThemeStyles.DATE_TIME_LINK))
                .put("selectedClass", theme.getStyleClass(ThemeStyles.DATE_TIME_BOLD_LINK))
                .put("edgeClass", theme.getStyleClass(ThemeStyles.DATE_TIME_OTHER_LINK))
                .put("edgeSelectedClass", theme.getStyleClass(ThemeStyles.DATE_TIME_OTHER_BOLD_LINK))
                .put("todayClass", theme.getStyleClass(ThemeStyles.DATE_TIME_TODAY_LINK))
                .put("dateFormat", scheduler.getDatePicker().getDateFormatPattern());

            // Append JavaScript.
            buff.append(JavaScriptUtilities.getModule("_html.scheduler"))
                .append("\n") // NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.scheduler._init")) // NOI18N
                .append("(") //NOI18N
                .append(JSONUtilities.getString(json))
                .append(");"); //NOI18N
            
            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(scheduler, writer,
                buff.toString(), JavaScriptUtilities.isParseOnLoad());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
        return this.getClass().getName();
    }
    
    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s);
    }
}
