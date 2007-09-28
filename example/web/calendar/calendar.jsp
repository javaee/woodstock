<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html"/>
<f:view>    
    <!--
      The contents of this file are subject to the terms
      of the Common Development and Distribution License
      (the License).  You may not use this file except in
      compliance with the License.
      
      You can obtain a copy of the license at
      https://woodstock.dev.java.net/public/CDDLv1.0.html.
      See the License for the specific language governing
      permissions and limitations under the License.
      
      When distributing Covered Code, include this CDDL
      Header Notice in each file and include the License file
      at https://woodstock.dev.java.net/public/CDDLv1.0.html.
      If applicable, add the following below the CDDL Header,
      with the fields enclosed by brackets [] replaced by
      you own identifying information:
      "Portions Copyrighted [year] [name of copyright owner]"
      
      Copyright 2007 Sun Microsystems, Inc. All rights reserved.
    -->
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.calendar_title}">
	<webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
      </webuijsf:head>
      <webuijsf:body id="body">
        <webuijsf:form id="form">                             
            
          <!-- Masthead -->
          <webuijsf:masthead id="masthead"
             productImageURL="/images/example_primary_masthead.png"
             productImageHeight="40"
             productImageWidth="188"
             userInfo="test_user" 
             serverInfo="test_server"
             productImageDescription="#{msgs.mastheadAltText}" />
          
          <!-- Breadcrumbs -->   
          <webuijsf:breadcrumbs id="breadcrumbs">
            <webuijsf:hyperlink id="indexPageLink"
                text="#{msgs.index_title}"
                toolTip="#{msgs.index_title}"
                actionExpression="#{CalendarBean.showExampleIndex}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="calendarLink" text="#{msgs.calendar_title}" />
          </webuijsf:breadcrumbs>
                   
          <!-- Alert -->
          <webuijsf:alert id="alert"                
              type="error"               
              summary="#{CalendarBean.alertSummary}"
              rendered="#{CalendarBean.alertRendered}" >
          </webuijsf:alert>

          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle"
              title="#{msgs.calendar_title}" >

            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

              <webuijsf:legend id="legend" text="#{msgs.calendar_requiredLabel}" />

              <br />
              <!-- Calendar validation error message -->
              <webuijsf:message id="calendarError" for="calendar" />

              <!-- Calendar -->
              <webuijsf:calendar id="calendar" label="#{msgs.calendar_label}"
                dateFormatPattern="#{CalendarBean.dateFormatPattern}"
                dateFormatPatternHelp="#{CalendarBean.dateFormatPatternHelp}"
                converter="#{CalendarBean.dateConverter}"
                toolTip="#{msgs.calendar_calendarTooltip}"
                required="true"
                selectedDate="#{CalendarBean.date}" />
                            
              <webuijsf:panelGroup id="pageActionsGroup" 
                block="true" style="padding:25px 0px 0px 0px;">

                <!-- Submit Button -->            
                <webuijsf:button id="submitButton"
                  text="#{msgs.calendar_submitButton}"
                  toolTip="#{msgs.calendar_submitToolTip}"
                  actionExpression="showCalendarResults" />                            
                <!-- Reset Button -->            
                <webuijsf:button id="resetButton"
                  immediate="true"
                  text="#{msgs.calendar_resetButton}"
                  toolTip="#{msgs.calendar_resetToolTip}"
                  actionExpression="#{CalendarBean.reset}"
                  actionListenerExpression="#{CalendarBean.resetActionListener}" />

                <!-- Date Patterns Menu -->                     
                <webuijsf:dropDown id="testCaseMenu"  
                  immediate="true"
                  submitForm="true"
                  forgetValue="true"
                  toolTip="#{msgs.calendar_dropDownToolTip}"
                  items="#{CalendarBean.datePatterns}"
                  actionExpression="#{CalendarBean.datePatternAction}"
                  actionListenerExpression="#{CalendarBean.datePatternActionListener}" />             
              </webuijsf:panelGroup>     

            </webuijsf:markup>
          </webuijsf:contentPageTitle>
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>

</jsp:root>
