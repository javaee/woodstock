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
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.webui.jsf.component.CalendarMonth;
import com.sun.webui.jsf.component.DateManager;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.Icon;
import com.sun.webui.jsf.component.IconHyperlink;
import com.sun.webui.jsf.component.SkipHyperlink;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.model.ScheduledEvent;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.MessageUtil;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p><strong>This class needs to be rewritten. Do not release as API.</strong></p>
 */
@Renderer(@Renderer.Renders(componentFamily="com.sun.webui.jsf.CalendarMonth"))
public class CalendarMonthRenderer extends AbstractRenderer {
    
    private static final boolean DEBUG = false;
    private static final String SKIP_SECTION = "skipSection"; //NOI18N
    private static final String CURR_YEAR_ATTR = "currYear"; 
    private static final String CURR_MONTH_ATTR = "currMonth"; 

    /** Calendar close image id	*/
    private static final String CLOSE_IMAGE = "_closeImage";
        
    private void renderDateTableStart(CalendarMonth calendarMonth,
            String[] styles,
            ResponseWriter writer)
            throws IOException {
	    
        writer.startElement("div", calendarMonth);
        writer.writeAttribute("class", styles[4], null); // NOI18N
        writer.write("\n"); // NOI18N
        writer.startElement("table", calendarMonth);
        writer.writeAttribute("width", "100%", null); // NOI18N
        writer.writeAttribute("cellspacing", "1", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.write("\n"); // NOI18N
        writer.startElement("tbody", calendarMonth);
        writer.writeAttribute("class", styles[5], null); // NOI18N
        writer.write("\n"); // NOI18N
    }
    
    private void renderDayHeaderRow(CalendarMonth calendarMonth,
            String[] styles,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {
        
        writer.startElement("tr", calendarMonth);
        writer.write("\n");
        
        int firstDay = calendarMonth.getCalendar().getFirstDayOfWeek();
        
        String[] daysOfWeek = new String[8];
        daysOfWeek[Calendar.MONDAY] = styles[8];
        daysOfWeek[Calendar.TUESDAY] = styles[9];
        daysOfWeek[Calendar.WEDNESDAY] = styles[10];
        daysOfWeek[Calendar.THURSDAY] = styles[11];
        daysOfWeek[Calendar.FRIDAY] = styles[12];
        daysOfWeek[Calendar.SATURDAY] = styles[13];
        daysOfWeek[Calendar.SUNDAY] = styles[14];
        
        String styleClass = styles[15];
          
        for (int i = 0; i < 7; i++) {
            // render a table header for each day of the week
            renderWeekdayHeader(calendarMonth, writer, styleClass,
			daysOfWeek[firstDay]);
            writer.write("\n");
            firstDay++;
            if(firstDay == 8) {
                firstDay = 1;
            }
        }
        writer.endElement("tr");
        writer.write("\n");
    }
    
    /**
     * <p>Render the calendarMonth header containing the weekday table headers.</p>
     * 
     * @param calendarMonth The CalendarMonth component instance
     * @param writer The current ResponseWriter
     * @param styleClass The style class to use for the table header cell
     * @param header The contents to write for the table header cell
     * 
     * @exception IOException if an input/output error occurs
     */
    private void renderWeekdayHeader(CalendarMonth calendarMonth,
            ResponseWriter writer, String styleClass, String header)
            throws IOException {
        // render a column header with the given style and header text
        writer.startElement("th", calendarMonth);
        writer.writeAttribute("align", "center", null); //NOI18N
        writer.writeAttribute("scope", "col", null); //NOI18N
        writer.write("\n"); //NOI18N
        writer.startElement("span", calendarMonth); //NOI18N
        writer.writeAttribute("class", styleClass, null); //NOI18N
        writer.write("\n"); //NOI18N
        writer.writeText(header, null);
        writer.write("\n"); //NOI18N
        writer.endElement("span"); //NOI18N
        writer.write("\n"); //NOI18N
        writer.endElement("th"); //NOI18N
    }
    
    private void renderDays(CalendarMonth calendarMonth, String id,
            String[] styles, DateFormat dateFormat, ResponseWriter writer)
            throws IOException {
        
        // now render each week in a row with each day in a td
        Calendar monthToShow = calendarMonth.getCalendar(); 
        monthToShow.set(Calendar.YEAR,
		       calendarMonth.getCurrentYear().intValue()); 
        monthToShow.set(Calendar.MONTH,
		       calendarMonth.getCurrentMonth().intValue()-1); 
        monthToShow.set(Calendar.DAY_OF_MONTH, 1); 
            
        if(DEBUG) log("Month to show " + monthToShow.getTime().toString());
        // get the int constant repsenting the day of the week (i.e. SUNDAY)
        int weekStartDay = monthToShow.getFirstDayOfWeek();
        
        // Get the startDate
        Calendar startDate = (Calendar)(monthToShow.clone());
        while(startDate.get(Calendar.DAY_OF_WEEK) != weekStartDay) {
            startDate.add(Calendar.DATE, -1);
            startDate.getTime();
        }
        if(DEBUG) log("First day " + startDate.getTime().toString());
        
        // Get the end date
        Calendar endDate = (Calendar)(monthToShow.clone());
        endDate.add(Calendar.MONTH, 1);
        endDate.getTime();
        
        if(endDate.get(Calendar.DAY_OF_WEEK) == weekStartDay) {
            endDate.add(Calendar.DATE, -1);
            endDate.getTime();
        } else {
            while(endDate.get(Calendar.DAY_OF_WEEK) != weekStartDay) {
                endDate.add(Calendar.DATE, 1);
                endDate.getTime();
            }
            endDate.add(Calendar.DATE, -1);
            endDate.getTime();
        }
        if(DEBUG) log("Last day " + endDate.getTime().toString());
        
        String rowIdPrefix = id.concat(":row");
        String dateLinkPrefix = id.concat(":dateLink");
        boolean selected;
        boolean dayInMonth;
        int displayedMonth = monthToShow.get(Calendar.MONTH);
        Calendar todaysDate = calendarMonth.getCalendar();
        
        int dateLinkId = 0;
        int rowNum = 0;
        while(startDate.before(endDate)) {
            writer.startElement("tr", calendarMonth);
            String rowId = rowIdPrefix + rowNum++;
            writer.writeAttribute("id", rowId, null); // NOI18N
            writer.write("\n");
            
            for(int i=0; i<7; ++i) {
                 
                if(DEBUG) log("Now rendering " + startDate.getTime().toString());
                selected = calendarMonth.isDateSelected(startDate, endDate);
                
                dayInMonth = (startDate.get(Calendar.MONTH) == displayedMonth);
                
                String style = styles[17];
                
                if(selected) {
                    if(dayInMonth) {
                        style = styles[18];
                    } else {
                        style = styles[19];
                    }
                } else if(dayInMonth) {
                    if(calendarMonth.compareDate(startDate, todaysDate)) {
                        style = styles[20];
                    } else {
                        style = styles[16];
                    }
                } else if(DEBUG) log("Date is outside month and selected");
                
                renderDateLink(startDate, style,
                        dateLinkPrefix.concat(String.valueOf(dateLinkId)),
                        calendarMonth, 
			dateFormat, writer);
                
                dateLinkId++;
                startDate.add(Calendar.DAY_OF_YEAR, 1);
                startDate.getTime();
            }
            writer.endElement("tr"); //NOI18N
            writer.write("\n");
        }
        if (rowNum < 6) {
            writer.startElement("tr", calendarMonth);
            String rowId = rowIdPrefix + rowNum++;
            writer.writeAttribute("id", rowId, null); // NOI18N
            writer.writeAttribute("style", "display:none;", null); // NOI18N
            writer.write("\n");
            
            for(int i=0; i<7; ++i) {
                renderDateLink(startDate, styles[17],
                        dateLinkPrefix.concat(String.valueOf(dateLinkId)),
                        calendarMonth, dateFormat, writer);
                
                dateLinkId++;
                startDate.add(Calendar.DAY_OF_YEAR, 1);
                startDate.getTime();
            }
            writer.endElement("tr"); //NOI18N
            writer.write("\n");
        }
    }
    
    private void renderDateLink(Calendar startDate,
            String style,
            String id,
            CalendarMonth calendarMonth,
	    DateFormat dateFormat,
            ResponseWriter writer) throws IOException {
        
        writer.startElement("td", calendarMonth);//NOI18N
        writer.writeAttribute("align", "center", null); // NOI18N
        writer.writeText("\n", null);//NOI18N
        
        int day = startDate.get(Calendar.DAY_OF_MONTH);
        
        // For performance reasons, don't create a hyperlink component
        // for each date...
        writer.startElement("a", calendarMonth); //NOI18N
        writer.writeAttribute("class", style, null); //NOI18N
        writer.writeAttribute("id", id, null); //NOI18N

        String dateString = dateFormat.format(startDate.getTime());
        writer.writeAttribute("title", dateString, null); //NOI18N

        StringBuffer buffer = new StringBuffer(128);
        
        if(calendarMonth.isPopup()) {
            buffer.append(calendarMonth.getJavaScriptObjectName());
            buffer.append(".dayClicked(this); return false;");
        } else {
            buffer.append(calendarMonth.getJavaScriptObjectName());
            buffer.append(".setDateValue('");
            buffer.append(dateString);
            buffer.append("', this); return false;");
        }
        
        writer.writeAttribute("onclick", buffer.toString(), null); //NOI18N
        writer.writeAttribute("href", "#", null); //NOI18N
        writer.write(String.valueOf(day));
        writer.endElement("a");  //NOI18N
        writer.write("\n"); //NOI18N
        writer.endElement("td"); //NOI18N
        writer.write("\n");    //NOI18N
    }

    private void renderDateTableEnd(ResponseWriter writer)
            throws IOException {
	    	
        writer.endElement("tbody"); //NOI18N
        writer.write("\n"); //NOI18N
        writer.endElement("table"); //NOI18N
        writer.write("\n"); //NOI18N
        writer.endElement("div"); //NOI18N
        writer.write("\n"); //NOI18N
    }
    
    // Render the calendar header.
    private void renderCalendarHeader(CalendarMonth calendarMonth,
            String[] styles,
            FacesContext context,
            ResponseWriter writer,
	    Theme theme)
            throws IOException {

    	renderHeaderTable(calendarMonth, writer, styles, theme); 
	renderTodayDate(calendarMonth, writer, styles, theme, context);	
    }

    // Render a table to layout the rounded corners and top border of 
    // the calendar header.
    private void renderHeaderTable(CalendarMonth calendarMonth,
		ResponseWriter writer, String[] styles, Theme theme)
		throws IOException {

        writer.startElement("table", calendarMonth);
        writer.writeAttribute("border", "0", null); // NOI18N
        writer.writeAttribute("cellpadding", "0", null); // NOI18N
        writer.writeAttribute("cellspacing", "0", null); // NOI18N
	writer.writeAttribute("width", "100%", null); // NOI18N
        writer.write("\n"); // NOI18N
        writer.startElement("tr", calendarMonth);
        writer.writeText("\n", null);  // NOI18N

	// Render the header's left corner.
        renderHeaderCornerCell(calendarMonth, writer,
	    theme.getImagePath(ThemeImages.SCHEDULER_TOP_LEFT));
			
	// Render a spacer column.
	String path = theme.getImagePath(ThemeImages.DOT);
	writer.startElement("td", calendarMonth);//NOI18N
        writer.writeAttribute("class", styles[23], null); //NOI18
	writer.writeAttribute("width", "100%", null); //NOI18N
        writer.startElement("img", calendarMonth); //NOI18N
        writer.writeAttribute("src", path, null); //NOI18N
        writer.writeAttribute("height", String.valueOf(1), null); //NOI18N
        writer.writeAttribute("width", String.valueOf(10), null); //NOI18N
        writer.writeAttribute("alt", "", null); //NOI18N
        writer.endElement("img");      //NOI18N
        writer.endElement("td");      //NOI18N
        writer.writeText("\n", null);  //NOI18N

	// Render the header's right corner.
        renderHeaderCornerCell(calendarMonth, writer, 
	    theme.getImagePath(ThemeImages.SCHEDULER_TOP_RIGHT));

	// Close the table.
        writer.endElement("tr");       //NOI18N
        writer.writeText("\n", null);  //NOI18N
        writer.endElement("table"); //NOI18N
        writer.writeText("\n", null); //NOI18N	
    }

    private void renderHeaderCornerCell(CalendarMonth calendarMonth,
		ResponseWriter writer, String src) 
    		throws IOException {
		
	writer.startElement("td", calendarMonth);//NOI18N
	writer.writeAttribute("width", "6", null); //NOI18N
        writer.writeText("\n", null);  //NOI18N
        writer.startElement("img", calendarMonth); //NOI18N
        writer.writeAttribute("src", src, null); //NOI18N
        writer.writeAttribute("alt", "", null); //NOI18N
        writer.endElement("img");      //NOI18N
        writer.endElement("td");      //NOI18N
        writer.writeText("\n", null);  //NOI18N
    }
   
    // Render today's date in the header.
    private void renderTodayDate(CalendarMonth calendarMonth,
		ResponseWriter writer, String[] styles,
		Theme theme, FacesContext context) 
    		throws IOException {

	renderTodayDateBegin(calendarMonth, writer, styles);	
	renderTodayDateText(calendarMonth, writer, context, styles, theme);	
	
        if(calendarMonth.isPopup()) {
            renderCloseButton(calendarMonth, styles, context, writer, theme);
        }

	renderTodayDateEnd(writer);
    }

    // Render the beginning of the layout for today date.
    private void renderTodayDateBegin(CalendarMonth calendarMonth,
		ResponseWriter writer, String[] styles) 
    		throws IOException {
		
        writer.startElement("div", calendarMonth);//NOI18N
        writer.writeAttribute("class", styles[22], null); //NOI18N
        writer.write("\n"); 					
     }
   
     // Render today date text. 
     private void renderTodayDateText(CalendarMonth calendarMonth,
		ResponseWriter writer, FacesContext context,
		String[] styles,
		Theme theme) 
    		throws IOException {

	writer.startElement("div", calendarMonth);//NOI18N
        writer.writeAttribute("class", styles[24], null); //NOI18N
	
	DateFormat dateFormat =
                SimpleDateFormat.getDateInstance(DateFormat.MEDIUM,
                context.getViewRoot().getLocale());
        dateFormat.setTimeZone((TimeZone)(calendarMonth.getTimeZone())); 
        Date today = calendarMonth.getCalendar().getTime(); 
        if(DEBUG) log("Today is " + today.toString()); 
	String[] detailArg = {dateFormat.format(today)};
        String detailMsg = theme.getMessage("CalendarMonth.todayIs", detailArg);
        writer.writeText(detailMsg, null);
	
        writer.endElement("div");       //NOI18N
        writer.writeText("\n", null);  //NOI18N		
     }

     // Render the end of the layout for today date.
     private void renderTodayDateEnd(ResponseWriter writer) 
    		throws IOException {
		
        writer.endElement("div");       //NOI18N
        writer.writeText("\n", null);  //NOI18N
     }


    /**
     * <p>Render the calendarMonth controls: the month and year menus as well as the
     * previous and next month IconHyperlink's.</p>
     *
     * @param context The current FacesContext
     * @param calendarMonth The CalendarMonth component instance
     * @param writer The current ResponseWriter
     * @param theme The current Theme
     *
     * @exception IOException if an input/output error occurs
     */
    private void renderCalendarControls(CalendarMonth calendarMonth,
            String[] styles,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {
        
        if(DEBUG) log("renderCalendarControls");
	
        String id = calendarMonth.getClientId(context); 
        writer.startElement("div", calendarMonth); //NOI18N
        writer.writeAttribute("class", styles[3],  null); // NOI18N
        writer.writeText("\n", null); //NOI18N
        
	writer.startElement("div", calendarMonth); //NOI18N
        writer.writeAttribute("class", styles[26],  null); // NOI18N
    	    
        String pattern = calendarMonth.getDateFormatPattern();
        if(pattern.indexOf("yyyy") < pattern.indexOf("MM")) { //NOI18N	    
            renderYearControl(calendarMonth, styles, context, writer);
	    writer.endElement("div"); //NOI18N
            writer.writeText("\n", null); //NOI18N
	    writer.startElement("div", calendarMonth); //NOI18N
	    writer.writeAttribute("class", styles[27],  null); // NOI18N
            renderMonthControl(calendarMonth, styles, context, writer);
        } else {            
            renderMonthControl(calendarMonth, styles, context, writer);
            writer.endElement("div"); //NOI18N
            writer.writeText("\n", null); //NOI18N
            writer.startElement("div", calendarMonth); //NOI18N
            writer.writeAttribute("class", styles[27],  null); // NOI18N
            renderYearControl(calendarMonth, styles, context, writer);            
        }

	writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
        
        writer.endElement("div"); //NOI18N
        writer.writeText("\n", null); //NOI18N
    }
    
    
    private void renderYearControl(CalendarMonth calendarMonth,
            String[] styles,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {
        
        if(DEBUG) log("renderYearControl()"); //NOI18N
        DropDown yearDropDown = calendarMonth.getYearMenu();
        yearDropDown.setToolTip(styles[6]);
        RenderingUtilities.renderComponent(yearDropDown, context);
        writer.write("\n");
    }
    
    private void renderMonthControl(CalendarMonth calendarMonth,
            String[] styles,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {
        
        if(DEBUG) log("renderMonthControl()"); //NOI18N
        
        IconHyperlink decreaseLink = calendarMonth.getPreviousMonthLink();
        RenderingUtilities.renderComponent(decreaseLink, context);
        writer.write("\n"); //NOI18N
        if(DEBUG) log("\t rendered PreviousLink");

        if(DEBUG) log(calendarMonth.toString());
        DropDown monthDropDown = calendarMonth.getMonthMenu();
        monthDropDown.setToolTip(styles[7]);
        if(DEBUG) log("Got DropDown");
        RenderingUtilities.renderComponent(monthDropDown, context);
        writer.write("\n");  //NOI18N
        if(DEBUG) log("\t rendered Month Menu");
        
        IconHyperlink increaseLink = calendarMonth.getNextMonthLink();
        RenderingUtilities.renderComponent(increaseLink, context);
        writer.write("\n"); //NOI18N
        if(DEBUG) log("\t rendered IncreaseLink");
    }
             

    
    private void renderCloseButton(CalendarMonth calendarMonth, 
            String[] styles, 
            FacesContext context, 
            ResponseWriter writer,
	    Theme theme)
            throws IOException {
        
        RenderingUtilities.renderAnchor(SKIP_SECTION, calendarMonth, context);
         
        StringBuffer strBuffer = new StringBuffer(128);
        strBuffer.append(calendarMonth.getJavaScriptObjectName());
        strBuffer.append(".setInitialFocus(); "); 
        strBuffer.append(calendarMonth.getJavaScriptObjectName());
        strBuffer.append(".toggle(); return false;");     
        
        writer.startElement("a", calendarMonth);
        writer.writeAttribute("onclick", strBuffer.toString(), null);
        writer.writeAttribute("class", styles[25], null);
        writer.writeAttribute("href", "#", null); 
	
	Icon icon = ThemeUtilities.getIcon(theme, ThemeImages.CALENDAR_CLOSE_BUTTON);
	icon.setParent(calendarMonth);
	icon.setId(CLOSE_IMAGE);
        RenderingUtilities.renderComponent(icon, context);
	
        writer.endElement("a");
        writer.write("\n");
    }

    /** 
     * <p>Render a spacer image.</p> 
     * 
     * @param context The current FacesContext 
     * @param calendarMonth The CalendarMonth component instance 
     * @param theme The current Theme 
     * @param height The height to use for the spaer image 
     * @param width The width to use for the spacer image 
     * 
     * @exception IOException if an input/output error occurs 
     */ 
    
    // TODO - this generates a component with no id set! Has to be fixed.
    protected void renderSpacerImage(FacesContext context, CalendarMonth calendarMonth,
            Theme theme, int height, int width) throws IOException {
        Icon dot = ThemeUtilities.getIcon(theme, ThemeImages.DOT);
        dot.setWidth(width);
        dot.setHeight(height);
        dot.setId("icon");
        dot.setAlt("");
        
        RenderingUtilities.renderComponent(dot, context);
    }
    /**
     *
     * @param context {@link FacesContext} for the response we are creating
     * @param component {@link UIComponent} to be rendered
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is <code>null</code>
     */
    public void encodeEnd(FacesContext context, UIComponent component)
    throws IOException {
        
        if(DEBUG) log("encodeEnd()");
        
        if(!(component instanceof CalendarMonth)) {
            Object[] params = { component.toString(),
                    this.getClass().getName(),
                    CalendarMonth.class.getName() };
                    String message = MessageUtil.getMessage
                            ("com.sun.webui.jsf.resources.LogMessages", //NOI18N
                            "Renderer.component", params);              //NOI18N
                    throw new FacesException(message);
        }
        
        CalendarMonth calendarMonth = (CalendarMonth)component;      
	SimpleDateFormat dateFormat =
		(SimpleDateFormat)calendarMonth.getDateFormat();
        initializeChildren(calendarMonth, dateFormat, context);
            
        ResponseWriter writer = context.getResponseWriter();
        Theme theme = ThemeUtilities.getTheme(context);
        String[] styles = getStyles(calendarMonth, context, theme);
	
	//RenderingUtilities.renderComponent(calendarMonth.getDateField(), context); 
        //writer.write("\n");
        
        String id = calendarMonth.getClientId(context);
        if(calendarMonth.isPopup()) { 
            renderPopupStart(calendarMonth, id, styles, context, writer);
        } else {
	   writer.startElement("div", calendarMonth); // NOI18N
           writer.writeAttribute("id", id, null); // NOI18N
           writer.writeText("\n", null);	
	}
	
 	// render calendar header 
	renderCalendarHeader(calendarMonth, styles, context, writer, theme);

	// render the begining of the layout for calendar controls and days of the week.
	writer.startElement("div", calendarMonth); // NOI18N
        writer.writeAttribute("class", styles[4], null); //NOI18N
        writer.writeText("\n", null);
	
        renderCalendarControls(calendarMonth, styles, context, writer);
	
        renderDateTable(calendarMonth, styles, id, dateFormat, context, writer);
        
	// close the div
        writer.endElement("div"); 
        writer.write("\n");  //NOI18N

        if(calendarMonth.isPopup()) {
            renderPopupEnd(writer);
        } else {
           writer.endElement("div"); 
           writer.write("\n");  //NOI18N
	}
    }
    
    private void renderPopupStart(CalendarMonth calendarMonth,
            String id, String[] styles,
            FacesContext context,
            ResponseWriter writer)
            throws IOException {
        
        writer.startElement("div", calendarMonth); // NOI18N
        writer.writeAttribute("id", id, null); // NOI18N
        writer.writeAttribute("class", styles[0], null); // NOI18N
        writer.writeText("\n", null);
        
        writer.startElement("div", calendarMonth);
        writer.writeAttribute("class", styles[1], null); // NOI18N
        writer.writeText("\n", null);
        
        writer.startElement("div", calendarMonth);
        writer.writeAttribute("class", styles[2], null); // NOI18N
        writer.writeText("\n", null);
    }
    
    private void renderPopupEnd(ResponseWriter writer)
    throws IOException {
        
        writer.endElement("div");  //NOI18N
        writer.write("\n");  //NOI18N
        writer.endElement("div");  //NOI18N
        writer.write("\n");  //NOI18N
        writer.endElement("div");  //NOI18N
        writer.write("\n");  //NOI18N
    }
    
    private void renderDateTable(CalendarMonth calendarMonth, String[] styles,
                                 String id, DateFormat dateFormat,
				 FacesContext context, 
                                 ResponseWriter writer)
            throws IOException {

        RenderingUtilities.renderSkipLink(SKIP_SECTION, styles[21], null, 
			null, null, calendarMonth, context);
        
	renderDateTableStart(calendarMonth, styles, writer);
        renderDayHeaderRow(calendarMonth, styles, context, writer);
        renderDays(calendarMonth, id, styles, dateFormat, writer);
	renderDateTableEnd(writer);
        
        
	
        if(!calendarMonth.isPopup()) {
            RenderingUtilities.renderAnchor(SKIP_SECTION, calendarMonth,
			context);
        } 
    }
    
    private void initializeChildren(CalendarMonth cm, 
		SimpleDateFormat dateFormat, FacesContext context) {
        
        if(DEBUG) log("initializeChildren()"); //NOI18N  
         
        // This variable is used to track whether the calendar 
        // controls have to be updated based on the calculations
        // performed in this method.
        boolean updateCalendarControls = false; 
        
        // Get a calendar instance with the correct timezone and locale 
        // from the CalendarMonth component. This calendar is initialized
        // with today's date.
        Calendar calendar = cm.getCalendar();
        
        // The displayDate reflects the month that will be displayed 
        // (we only use the year and month component of the date).
        // We start by assuming that this will be the today's date
        Date displayDate = calendar.getTime(); 
             
       
      
        
        // Find out what the current year and month settings are of the 
        // CalendarMonth component (this is whatever the user has 
        // selected using the menus and the buttons on the control row). 
        // Update the calendar with this data - it will be used when 
        // calculating the dates on the display.
        // If the user hasn't made any selections yet, the values 
        // will be updated later, and will be based on today's date. 
        Integer year = cm.getCurrentYear(); 
        Integer month = cm.getCurrentMonth();         
        if(year != null && month != null) {
            if(DEBUG) log("Menus have values...");
            if(DEBUG) log("Month is " + month.toString()); 
            if(DEBUG) log("Year is " + year.toString()); 
            calendar.set(Calendar.YEAR, year.intValue());
            // Adjust for the fact that we display the months as 1 - 12, but 
            // java.util.Calendar has them as 0 to 11.  
            calendar.set(Calendar.MONTH, month.intValue() -1);
            //calendar.set(Calendar.DAY_OF_MONTH, 1); 
        }
        else { 
            updateCalendarControls = true; 
        } 
        
        // Calculate which years should be displayed, based on the 
        // settings of the of the CalendarMonth component
        // We should probably store these options as an attribute, 
        // instead of calculating them every time. 
        
         // Calculate min and max dates
        Date minDate = null; 
        Date maxDate = null;   
          
        UIComponent parent = cm.getParent(); 
        if(parent instanceof DateManager) {
            minDate = ((DateManager)parent).getFirstAvailableDate();
            maxDate = ((DateManager)parent).getLastAvailableDate();
        }   
        
        if(DEBUG) log("Min date set to  " + minDate.toString());
        if(DEBUG) log("Max date set to " + maxDate.toString());
        
        if(DEBUG) log("Date to display is " + displayDate.toString()); 
        if(displayDate.before(minDate)) {
            if(DEBUG) log("date is before mindate");
            displayDate = minDate;
            updateCalendarControls = true;
        }
        if(maxDate.before(displayDate)) {
            if(DEBUG) log("date is after maxdate");
            displayDate = maxDate;
            updateCalendarControls = true;
        }
        
        DropDown yearMenu = cm.getYearMenu();
        DropDown monthMenu = cm.getMonthMenu();
        
        if(updateCalendarControls) {
            calendar.setTime(displayDate);           
            String yearValue = String.valueOf(calendar.get(Calendar.YEAR));
            yearMenu.setSubmittedValue(new String[]{yearValue});
            if(DEBUG) log("Value of year: " + yearValue);
            
            String monthValue = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            monthMenu.setSubmittedValue(new String[]{monthValue});
            if(DEBUG) log("Value of month: " + monthValue);
        }
       
        // Calculate the years to show on the menu.
        calendar.setTime(minDate);
        int firstYear = calendar.get(Calendar.YEAR);  
        calendar.setTime(maxDate); 
        int lastYear = calendar.get(Calendar.YEAR);  
        
        int numYears = lastYear - firstYear + 1;
        Integer yearInteger = null;
        Option[] yearOptions = new Option[numYears];
        for(int i=0; i < numYears; ++i) {
            yearInteger = new Integer(firstYear + i);
            yearOptions[i] = new Option(yearInteger, yearInteger.toString());
        }
        yearMenu.setItems(yearOptions);  
        
        // Set the items of the month component
        // construct an option[] for the locale specific months
        String[] monthNames = dateFormat.getDateFormatSymbols().getMonths();
        Option[] months = new Option[12];
   
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        int monthInt;
        for (int i = 0; i < 12; i++) {
            monthInt = calendar.get(Calendar.MONTH);
            months[i] = new Option(new Integer(monthInt+1), monthNames[i]);
            calendar.add(Calendar.MONTH, 1);
        }
        if(DEBUG) log("Created the month options");       
        monthMenu.setItems(months);
        
        if(DEBUG) log("initializeChildren() - END"); //NOI18N        
    }
   
    
    /**
     * Override default behaviour - do nothing.
     *
     * @param context {@link FacesContext} for the response we are creating
     * @param component {@link UIComponent} whose children are to be rendered
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is <code>null</code>
     */
    public void encodeChildren(FacesContext context, UIComponent component)
    throws IOException {
        return;
    }
    
    /**
     *Override default behaviour - do nothing.
     *
     *
     * @param context {@link FacesContext} for the request we are processing
     * @param component {@link UIComponent} to be rendered
     *
     * @exception IOException if an input/output error occurs while rendering
     * @exception NullPointerException if <code>context</code>
     *  or <code>component</code> is null
     */
    public void encodeBegin(FacesContext context, UIComponent component)
    throws IOException {
        return;
    }
    
    /**
     * Returns a string representation of the object.
     * @return  a string representation of the object.
     */
    public String toString() {
        return this.getClass().getName();
    }
    
    /**
     * Returns true.
     * @return true
     */
    public boolean getRendersChildren() {
        return true;
    }
    
    private String[] getStyles(CalendarMonth calendarMonth, 
                               FacesContext context, Theme theme) {
        String[] styles = new String[28];
        styles[0] = theme.getStyleClass(ThemeStyles.CALENDAR_DIV_SHOW);
        styles[1] = theme.getStyleClass(ThemeStyles.CALENDAR_DIV_SHOW2);
        styles[2] = theme.getStyleClass(ThemeStyles.CALENDAR_DIV);
        styles[3] = theme.getStyleClass(ThemeStyles.DATE_TIME_SELECT_DIV);
        styles[4] = theme.getStyleClass(ThemeStyles.DATE_TIME_CALENDAR_DIV);
        styles[5] = theme.getStyleClass(ThemeStyles.DATE_TIME_CALENDAR_TABLE);
        styles[6] = theme.getMessage("CalendarMonth.selectYear");
        styles[7] = theme.getMessage("CalendarMonth.selectMonth");
        styles[8] = theme.getMessage("CalendarMonth.weekdayMon");
        styles[9] = theme.getMessage("CalendarMonth.weekdayTue");
        styles[10] = theme.getMessage("CalendarMonth.weekdayWed");
        styles[11] = theme.getMessage("CalendarMonth.weekdayThu");
        styles[12] = theme.getMessage("CalendarMonth.weekdayFri");
        styles[13] = theme.getMessage("CalendarMonth.weekdaySat");
        styles[14] = theme.getMessage("CalendarMonth.weekdaySun");
        styles[15] = theme.getStyleClass(ThemeStyles.DATE_TIME_DAY_HEADER);
        styles[16] = theme.getStyleClass(ThemeStyles.DATE_TIME_LINK);
        styles[17] = theme.getStyleClass(ThemeStyles.DATE_TIME_OTHER_LINK);
        styles[18] = theme.getStyleClass(ThemeStyles.DATE_TIME_BOLD_LINK);
        styles[19] = theme.getStyleClass(ThemeStyles.DATE_TIME_OTHER_BOLD_LINK);
        styles[20] = theme.getStyleClass(ThemeStyles.DATE_TIME_TODAY_LINK);
        styles[21] = theme.getStyleClass(ThemeStyles.SKIP_MEDIUM_GREY1);
        styles[22] = theme.getStyleClass(ThemeStyles.DATE_TIME_SELECT_CONTENT);
        styles[23] = theme.getStyleClass(ThemeStyles.DATE_TIME_SELECT_TOP_MIDDLE);
        styles[24] = theme.getStyleClass(ThemeStyles.DATE_TIME_SELECT_DATE);
        styles[25] = theme.getStyleClass(ThemeStyles.CALENDAR_CLOSE_BUTTON);
        styles[26] = theme.getStyleClass(ThemeStyles.DATE_TIME_CALENDAR_LEFT);
        styles[27] = theme.getStyleClass(ThemeStyles.DATE_TIME_CALENDAR_RIGHT);
        
	return styles;
    }
    
    private void log(String s) {
        System.out.println(this.getClass().getName() + "::" + s);
    }

    public void decode(FacesContext context, UIComponent component) {

        super.decode(context, component);
    }
}
