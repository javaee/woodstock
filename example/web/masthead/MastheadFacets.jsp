<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="2.0" 
	  xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html" /> 
                    
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
  <webuijsf:page id="page3" >
    <webuijsf:html id="html3" >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head id="head3" title="#{msgs.masthead_title}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body id="body3" >
            <webuijsf:form id="form3">
                        
              <!-- Masthead -->
              <webuijsf:masthead id="masthead1" serverInfo="#{MastheadBean.server}" userInfo="#{MastheadBean.user}" 
                           productImageURL="/images/example_primary_masthead.png" productImageDescription="#{msgs.mastheadAltText}" >                    
                
                 <!-- Status Area Facets -->
                  <f:facet name="notificationInfo">
                         <webuijsf:panelGroup id="notificationInfo" rendered="#{MastheadBean.cb1Selected}" >                              
                             <webuijsf:image id="notificationImage" icon="ALERT_INFO_MEDIUM" align="middle"/>
                             <f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim>
                             <webuijsf:hyperlink id="link1" styleClass="#{themeStyles.MASTHEAD_PROGRESS_LINK}" 
                             actionExpression="#{MastheadBean.notificationClicked}" >
                             <webuijsf:staticText id="text1" styleClass="#{themeStyles.MASTHEAD_TEXT}"
                             text="#{msgs.masthead_notificationmsg}"/>
                             </webuijsf:hyperlink>
                         </webuijsf:panelGroup>
                     </f:facet>
                     <f:facet name="jobsInfo">

                        <webuijsf:panelGroup id="jobStatusInfo" rendered="#{MastheadBean.cb2Selected}">                              
                             <webuijsf:image id="jobStatusimage" icon="MASTHEAD_STATUS_ICON" height="17" width="17"/>
                             <f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim>
                             <webuijsf:hyperlink id="link2" styleClass="#{themeStyles.MASTHEAD_PROGRESS_LINK}" 
                             actionExpression="#{MastheadBean.jobstatusClicked}">
                             <webuijsf:staticText id="text2" styleClass="#{themeStyles.MASTHEAD_TEXT}"
                             text="#{MastheadBean.jobStatus}"/>
                             </webuijsf:hyperlink>
                         </webuijsf:panelGroup>           
                     </f:facet>
                     
                      <f:facet name="dateTimeInfo">       
                         <webuijsf:timeStamp id="time" rendered="#{MastheadBean.cb3Selected}" />
                      </f:facet>
                      
                      <f:facet name="currentAlarmsInfo">  
                      <webuijsf:panelGroup id="alarmStatus" rendered="#{MastheadBean.cb4Selected}">
                                         
                          <webuijsf:staticText id="labelstatus" text="Current Info :" styleClass="#{themeStyles.MASTHEAD_TEXT}"/>
                          <webuijsf:panelGroup id="downAlarmsPanel">
                            <webuijsf:imageHyperlink id="downAlarmsLink" 
                                               icon="ALARM_MASTHEAD_DOWN_DIMMED" text="0" disabled="true" >                         
                              <f:param name="severity" value="down" />
                              </webuijsf:imageHyperlink>
                            <f:verbatim><![CDATA[ &nbsp;&nbsp; ]]></f:verbatim>
                          </webuijsf:panelGroup>
                       
                          <webuijsf:panelGroup id="criticalAlarmsPanel">
                            <webuijsf:imageHyperlink id="criticalAlarmsLink" styleClass="#{themeStyles.MASTHEAD_ALARM_LINK}" 
                                               icon="ALARM_MASTHEAD_CRITICAL_MEDIUM" text="1"  actionExpression="#{MastheadBean.alarmClicked}" >
                              <f:param name="severity" value="critical"/>
                            </webuijsf:imageHyperlink>
                         <f:verbatim><![CDATA[ &nbsp;&nbsp; ]]></f:verbatim>
                          </webuijsf:panelGroup>                        

                          <webuijsf:panelGroup id="majorAlarmsPanel">
                            <webuijsf:imageHyperlink id="majorAlarmsLink" styleClass="#{themeStyles.MASTHEAD_ALARM_LINK}" 
                                               icon="ALARM_MASTHEAD_MAJOR_MEDIUM" text="2" actionExpression="#{MastheadBean.alarmClicked}" >
                              <f:param name="severity" value="major"/>
                            </webuijsf:imageHyperlink>
                          <f:verbatim><![CDATA[ &nbsp;&nbsp; ]]></f:verbatim>
                          </webuijsf:panelGroup>
                          
                          <webuijsf:panelGroup id="minorAlarmsPanel">
                            <webuijsf:imageHyperlink id="minorAlarmsLink" styleClass="#{themeStyles.MASTHEAD_ALARM_LINK}" 
                                               icon="ALARM_MASTHEAD_MINOR_MEDIUM" text="3" actionExpression="#{MastheadBean.alarmClicked}" >
                              <f:param name="severity" value="minor"/>
                            </webuijsf:imageHyperlink>
                            <f:verbatim><![CDATA[ &nbsp;&nbsp; ]]></f:verbatim>
                          </webuijsf:panelGroup>
                      </webuijsf:panelGroup>

               </f:facet>              
                
                 <!-- Utility Bar Facets -->                
                <f:facet name="consoleLink" >
                  <webuijsf:hyperlink id="hyp1" actionExpression="#{MastheadBean.consolePage2Clicked}" toolTip="#{msgs.masthead_consoleLink}" />
                </f:facet>
                <f:facet name="versionLink" >
                  <webuijsf:hyperlink  id="hyp2" onClick="javascript: var versionWin = window.open('/example/faces/masthead/Version.jsp','VersionWindow','scrollbars,resizable,
                                 width=650,height=500,top='+((screen.height - (screen.height/1.618)) - (500/2))+',left='+((screen.width-650)/2) );versionWin.focus();
                                 if(window.focus){versionWin.focus();}"
                                 toolTip="#{msgs.masthead_versionLink}" actionExpression="#{MastheadBean.versionPage2Clicked}"/>
                </f:facet> 
                <f:facet name="logoutLink" >
                  <webuijsf:hyperlink id="hyp3" actionExpression="#{MastheadBean.logoutPage2Clicked}" toolTip="#{msgs.masthead_logoutLink}" />
                </f:facet>   
                <f:facet name="helpLink" >
                  <webuijsf:hyperlink id="hyp4" actionExpression="#{MastheadBean.helpPage2Clicked}" toolTip="#{msgs.masthead_helpLink}" />
                </f:facet>
                
              </webuijsf:masthead>
              
              <!-- Breadcrumbs -->
              <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink id="hyp1" actionExpression="#{IndexBean.showIndex}" text="#{msgs.exampleTitle}"
                              toolTip="#{msgs.index_title}"
                              onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true"/>
                <webuijsf:hyperlink id="hyp2" actionExpression="#{MastheadBean.goToMastheadIndex}" 
                              text="#{msgs.masthead_title}" toolTip="#{msgs.masthead_titleToolTip}"
                              onMouseOver="javascript:window.status='#{msgs.masthead_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true"/>
                <webuijsf:hyperlink id="hyp3" text="#{msgs.masthead_masthead2Title}"/>
              </webuijsf:breadcrumbs>
             
              <!-- Alert -->
              <webuijsf:alert id="message" type="information" summary="#{MastheadBean.message}" 
                        rendered="#{MastheadBean.isRendered2}" />
                        
              <!-- Page Title -->
              <webuijsf:contentPageTitle id="pagetitle" title="#{msgs.masthead_pageTitle2}" helpText="#{msgs.masthead_helpText2}" />
                                                                
              <!-- Checkboxes to enable/disable status area components in the masthead -->
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
              <br/>              
              <table border="0" ><tr><td>
              Select Masthead Options:
              </td></tr><tr><td>
              <webuijsf:checkbox id="cb1" label="#{msgs.masthead_cb1Text}" selected="#{MastheadBean.cb1Selected}"
                           immediate="true" valueChangeListenerExpression="#{MastheadBean.listener1}" />
              
              </td></tr><tr><td>
              <webuijsf:checkbox id="cb2" label="#{msgs.masthead_cb2Text}" selected="#{MastheadBean.cb2Selected}"
                           immediate="true" valueChangeListenerExpression="#{MastheadBean.listener2}" />
              
              </td></tr><tr><td>
              <webuijsf:checkbox id="cb3" label="#{msgs.masthead_cb3Text}" selected="#{MastheadBean.cb3Selected}"
                           immediate="true" valueChangeListenerExpression="#{MastheadBean.listener3}" />
              
              </td></tr><tr><td> 
              <webuijsf:checkbox id="cb4" label="#{msgs.masthead_cb4Text}" selected="#{MastheadBean.cb4Selected}"
                           immediate="true" valueChangeListenerExpression="#{MastheadBean.listener4}" />
              </td></tr><tr><td>
              
              <!-- Button to effect changes on masthead -->
              <webuijsf:button id="button1" text="#{msgs.masthead_buttonText}" actionExpression="#{MastheadBean.buttonClicked}" 
                         toolTip="#{msgs.masthead_buttonToolTip}" immediate="true" />
              </td></tr></table>
              </webuijsf:markup>
            
            </webuijsf:form>
          </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>
</jsp:root>
