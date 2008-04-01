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
import com.sun.webui.jsf.component.Calendar;
import com.sun.webui.jsf.component.CalendarMonth;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.JSONUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.theme.Theme;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.MissingResourceException;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>Renders an instance of the Calendar component.</p>
 *
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.Calendar"))
public class CalendarRenderer extends FieldRenderer {
    
    private final static boolean DEBUG = false;
    
    /** Creates a new instance of CalendarRenderer. */
    public CalendarRenderer() {
    }
    
    /**
     * <p>Render the component end element.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @exception IOException if an input/output error occurs
     */
    public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException {

        if(!(component instanceof Calendar)) {
            Object[] params = { component.toString(),
                    this.getClass().getName(),
                    Calendar.class.getName() };
                    String message = MessageUtil.getMessage
                            ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                            "Renderer.component", params);              //NOI18N
                    throw new FacesException(message);
        }
        
        Calendar calendar = (Calendar)component;
        boolean readOnly = calendar.isReadOnly();
        
        ResponseWriter writer = context.getResponseWriter();
        String[] styles = getStyles(calendar, context);
        String clientId = calendar.getClientId(context);
    
        // Start a table
        renderTableStart(calendar, styles[18], styles[2], context, writer);
        
        // Table cell for the label
        UIComponent label = calendar.getLabelComponent(context, null);
        if(label != null) {
            renderCellStart(calendar, styles[6], writer);
            RenderingUtilities.renderComponent(label, context);
            renderCellEnd(writer);
        }
        
        
        // Table cell for the field and the date format string
        
        if(readOnly) {
            renderCellStart(calendar, styles[6], writer);
            UIComponent text = calendar.getReadOnlyComponent(context);
            RenderingUtilities.renderComponent(text, context);
            if(calendar.getValue() != null)  {
                renderPattern(calendar, styles[7], styles[2], context, writer);
            }
        } else {
            renderCellStart(calendar, styles[4], writer);
            renderInput(calendar, "text", clientId.concat(calendar.INPUT_ID),
                    false, styles, context, writer);
            writer.write("\n");
            renderPattern(calendar, styles[7], styles[2], context, writer);
        }
        
        renderCellEnd(writer);
        
        if(!readOnly) {
            
            // Start of table cell
            writer.startElement("td", calendar); //NOI18N
            writer.writeAttribute("valign", "top", null);//NOI18N
            writer.writeText("\n", null); //NOI18N
            
            // Create a common CSS containing block used for positioning
            writer.startElement("div", calendar); //NOI18N
	    // Use relative so that icon is considered in normal flow
            writer.writeAttribute("style", "position: relative;", null);// NOI18N
            writer.writeText("\n", null); //NOI18N
            
            // This is the span for the link
            writer.startElement("span", calendar); //NOI18N
            writer.writeAttribute("class", styles[5], null); //NOI18N
            
            
             // Search for the private facet DATE_PICKER_LINK_FACET.
             // If it exists, then use the facet.
             // If facet does not exist, create an ImageHyperlnk and use that.
             // The call to getDatePickerLink has been removed and the creation
             // of an imageHyperlnk if one did not exist in the facet map happens
             // here. The imageHyperlnk created here is not put into the facet
             // map.
            UIComponent comp = calendar.getFacet(Calendar.DATE_PICKER_LINK_FACET); 
            if (comp instanceof ImageHyperlink) {
                RenderingUtilities.renderComponent(comp, context);        
            } else {              
                ImageHyperlink datePickerLink = new ImageHyperlink();
                datePickerLink.setParent(calendar);
                StringBuilder linkId = new StringBuilder("_")
                  .append(calendar.DATE_PICKER_LINK_FACET);
                datePickerLink.setId(linkId.toString());
                datePickerLink.setAlign("middle");  //NOI18N                                    
                datePickerLink.setToolTip(styles[13]);                    

                // Set the appropriate image icon depending on whether
                // the calendar is on the enabled or disabled state.
                if (!calendar.isDisabled()) {
                    datePickerLink.setIcon(styles[14]);
                } else {
                    datePickerLink.setIcon(styles[19]);
                }
                RenderingUtilities.renderComponent(datePickerLink, context);                        
                comp = datePickerLink;
            }
            writer.write("\n"); //NOI18N
                        
            // Close link span
            writer.endElement("span"); //NOI18N
            writer.write("\n");

	    renderDatePicker(context, writer, styles, calendar, comp);
            
            // Close the remaining div and table cell 
            writer.endElement("div"); //NOI18N
            writer.writeText("\n", null); //NOI18N
            writer.endElement("td"); //NOI18N
            writer.writeText("\n", null); //NOI18N
        }

        // End the table
        renderTableEnd(writer);
        writer.writeText("\n", null); //NO18N
    }
    
    // <rave> Fix popup so that it always appears near button. eeg 2005-11-04
    private void renderDatePicker(FacesContext context, ResponseWriter writer,
	    String[] styles, Calendar calendar, UIComponent datePickerLink) throws IOException {

        // render date picker
        CalendarMonth datePicker = calendar.getDatePicker();
        Object value = calendar.getSubmittedValue();
        if(value != null) {
            try {
                Object dO =
                        ConversionUtilities.convertValueToObject(calendar,
                        (String)value,
                        context);
                datePicker.setValue(dO);
            } catch(Exception ex) {
                // do nothing
            }
        } else if(calendar.getValue() != null) {
            datePicker.setValue(calendar.getValue());
        }
        datePicker.initCalendarControls(
		calendar.getJavaScriptObjectName(context));
        RenderingUtilities.renderComponent(datePicker, context);

        //JS should be initialized by CalendarMonth, not by this component....
        renderJavaScript(context, calendar, writer, styles, datePickerLink);
    }
    // </rave>

    private void renderTableStart(Calendar calendar, String rootStyle,
	    String hiddenStyle, FacesContext context, ResponseWriter writer)
            throws IOException {
        
        writer.startElement("table", calendar);
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("cellspacing", "0", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeAttribute("title", "", null); // NOI18N
        writer.writeAttribute("id", calendar.getClientId(context) , "id"); //NOI18N
        String style = calendar.getStyle();
        if(style != null && style.length() > 0) {
            writer.writeAttribute("style", style, "style"); //NOI18N
        }
        
        style = getStyleClass(calendar, hiddenStyle);

        // Append a styleclass to top level table element so we can use it to
        // fix a pluto portal bug by restoring the initial width value
	//
        if (style == null) {
            style = rootStyle;
        } else {
            style += " " + rootStyle; //NOI18N
        }
        if(style != null) {
            writer.writeAttribute("class", style, "class"); //NOI18N
        }

        writer.writeText("\n", null);//NOI18N
        writer.startElement("tr", calendar);//NOI18N
        writer.writeText("\n", null);//NOI18N
    }
    
    private void renderCellStart(Calendar calendar, String style,
            ResponseWriter writer)
            throws IOException {
        
        writer.startElement("td", calendar);
        writer.writeAttribute("valign", "top", null);//NOI18N
        writer.writeText("\n", null);
        writer.startElement("span", calendar);
        writer.writeAttribute("class", style, null); //NOI18N
        writer.writeText("\n", null);
    }
    
    private void renderCellEnd(ResponseWriter writer)
    throws IOException {
        
        writer.writeText("\n", null);
        writer.endElement("span");
        writer.writeText("\n", null);
        writer.endElement("td");
        writer.writeText("\n", null);
    }
    
    private void renderTableEnd(ResponseWriter writer)
    throws IOException {
        
        writer.endElement("tr"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    private void renderPattern(Calendar calendar, String styleClass,
            String hiddenStyle, FacesContext context, ResponseWriter writer)
            throws IOException {
        
        String hint = calendar.getDateFormatPatternHelp();
        if(hint == null) {
            try {  
                String pattern = calendar.getDatePicker().getDateFormatPattern(); 
                hint = ThemeUtilities.getTheme(context).getMessage("calendar.".concat(pattern)); 
            }
            catch(MissingResourceException mre) { 
                hint = ((SimpleDateFormat)(calendar.getDateFormat())).toLocalizedPattern().toLowerCase();
            }
        }
        if(hint != null) {
            writer.startElement("div", calendar); //NOI18N
            String id = calendar.getClientId(context);
            id = id.concat(Calendar.PATTERN_ID);
            writer.writeAttribute("id", id, null); //NOI18N
            String style = styleClass;
            writer.writeAttribute("class", style, null); //NOI18N
            writer.writeText(hint, null);
            writer.endElement("div"); //NOI18N
        }
    }

    // Note: Other than for design-time, we're no longer maintaining the HTML
    // version of Calendar. After the calendar widget was created, the old
    // JavaScript file was unused, untested, and does not contain new features or
    // the latest bug fixes. Therefore, the old JS file has been removed in favor
    // of the new client-side widget.
    private void renderJavaScript(FacesContext context, Calendar calendar,
            ResponseWriter writer, String[] styles, UIComponent datePickerLink) throws IOException {
        if(DEBUG) log("renderJavaScript()"); //NOI18N

        // First argument is the first day of the week
        
        int firstDay = calendar.getDatePicker().getCalendar().getFirstDayOfWeek();
        String day = null;
        if(firstDay == java.util.Calendar.SUNDAY) {
            day = "0";
        } else  if(firstDay == java.util.Calendar.MONDAY) {
            day = "1";
        } else  if(firstDay == java.util.Calendar.FRIDAY) {
            day = "5";
        } else  if(firstDay == java.util.Calendar.SATURDAY) {
            day = "6";
        } else  if(firstDay == java.util.Calendar.TUESDAY) {
            day = "2";
        } else  if(firstDay == java.util.Calendar.WEDNESDAY) {
            day = "3";
        } else  if(firstDay == java.util.Calendar.THURSDAY) {
            day = "4";
        }

        try {
            // Append properties.
            StringBuffer buff = new StringBuffer(256);
            JSONObject json = new JSONObject();
            String datePickerId = calendar.getDatePicker().getClientId(context);
            json.put("id", calendar.getClientId(context))
                .put("firstDay", day)
                .put("fieldId", calendar.getClientId(context).concat(Calendar.INPUT_ID))
                .put("patternId", calendar.getClientId(context).concat(Calendar.PATTERN_ID))
                .put("calendarToggleId", datePickerLink.getClientId(context))
                .put("datePickerId", datePickerId)
                .put("monthMenuId", calendar.getDatePicker().getMonthMenu().getClientId(context))
                .put("yearMenuId", calendar.getDatePicker().getYearMenu().getClientId(context))
                .put("rowId", datePickerId + ":row5")
                .put("showButtonSrc", styles[8])
                .put("hideButtonSrc", styles[9])
                .put("dateFormat", calendar.getDatePicker().getDateFormatPattern())
                .put("dateClass", styles[10])
                .put("edgeClass", styles[11])
                .put("selectedClass", styles[15])
                .put("edgeSelectedClass", styles[16])
                .put("todayClass", styles[17])
                .put("hiddenClass", styles[2]);

            // Append JavaScript.
            buff.append(JavaScriptUtilities.getModule("_html.calendar"))
                .append("\n") // NOI18N
                .append(JavaScriptUtilities.getModuleName("_html.calendar._init")) // NOI18N
                .append("(") //NOI18N
                .append(JSONUtilities.getString(json))
                .append(");"); //NOI18N
            
            // Render JavaScript.
            JavaScriptUtilities.renderJavaScript(calendar, writer,
                buff.toString(), JavaScriptUtilities.isParseOnLoad());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    
    String[] getStyles(Calendar calendar, FacesContext context) {
        Theme theme = ThemeUtilities.getTheme(context);
        String[] styles = new String[20];
        styles[0] = theme.getStyleClass(ThemeStyles.TEXT_FIELD);
        styles[1] = theme.getStyleClass(ThemeStyles.TEXT_FIELD_DISABLED);
        styles[2] = theme.getStyleClass(ThemeStyles.HIDDEN);
        styles[3] = "";
        styles[4] = ""; // used to be CalPopFld, removed
        styles[5] = theme.getStyleClass(ThemeStyles.CALENDAR_FIELD_IMAGE);
        styles[6] = theme.getStyleClass(ThemeStyles.CALENDAR_FIELD_LABEL);
        styles[7] = theme.getStyleClass(ThemeStyles.HELP_FIELD_TEXT);
        styles[8] = theme.getImagePath(ThemeImages.CALENDAR_BUTTON);
        styles[9] = theme.getImagePath(ThemeImages.CALENDAR_BUTTON_FLIP);
        styles[10] = theme.getStyleClass(ThemeStyles.DATE_TIME_LINK);
        styles[11] = theme.getStyleClass(ThemeStyles.DATE_TIME_OTHER_LINK);
        styles[12] = null;
        styles[13] = theme.getMessage("calendar.popupImageAlt");
        styles[14] = ThemeImages.CALENDAR_BUTTON; 
        // Style for the selected date
        styles[15] = theme.getStyleClass(ThemeStyles.DATE_TIME_BOLD_LINK);
        // Style for selected date on the edge
        styles[16] = theme.getStyleClass(ThemeStyles.DATE_TIME_OTHER_BOLD_LINK);
        // Style for today's date
        styles[17] = theme.getStyleClass(ThemeStyles.DATE_TIME_TODAY_LINK);
        styles[18] = theme.getStyleClass(ThemeStyles.CALENDAR_ROOT_TABLE);
        styles[19] = ThemeImages.CALENDAR_BUTTON_DISABLED; 
        return styles;
    }    
}
