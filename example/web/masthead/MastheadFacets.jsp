<?xml version="1.0" encoding="UTF-8"?>
<jsp:root version="2.0" 
	  xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html" /> 
                    
<f:view>
    <!--
      DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

	  Copyright (c) 2007-2018 Oracle and/or its affiliates. All rights reserved.

	  The contents of this file are subject to the terms of either the GNU
	  General Public License Version 2 only ("GPL") or the Common Development
	  and Distribution License("CDDL") (collectively, the "License").  You
	  may not use this file except in compliance with the License.  You can
	  obtain a copy of the License at
	  https://oss.oracle.com/licenses/CDDL+GPL-1.1
	  or LICENSE.txt.  See the License for the specific
	  language governing permissions and limitations under the License.

	  When distributing the software, include this License Header Notice in each
	  file and include the License file at LICENSE.txt.

	  GPL Classpath Exception:
	  Oracle designates this particular file as subject to the "Classpath"
	  exception as provided by Oracle in the GPL Version 2 section of the License
	  file that accompanied this code.

	  Modifications:
	  If applicable, add the following below the License Header, with the fields
	  enclosed by brackets [] replaced by your own identifying information:
	  "Portions Copyright [year] [name of copyright owner]"

	  Contributor(s):
	  If you wish your version of this file to be governed by only the CDDL or
	  only the GPL Version 2, indicate your decision by adding "[Contributor]
	  elects to include this software in this distribution under the [CDDL or GPL
	  Version 2] license."  If you don't indicate a single choice of license, a
	  recipient has the option to distribute your version of this file under
	  either the CDDL, the GPL Version 2 or to extend the choice of license to
	  its licensees as provided above.  However, if you add GPL Version 2 code
	  and therefore, elected the GPL Version 2 license, then the option applies
	  only if the new code is made subject to such option by the copyright
	  holder.
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
                          <webuijsf:notificationPhrase id="notify" 
                                                       text="#{msgs.masthead_notificationmsg}" 
                                                       actionExpression="#{MastheadBean.notificationClicked}" 
                                                       rendered="#{MastheadBean.cb1Selected}" />
                      </f:facet>
                      <f:facet name="jobsInfo">                       
                      <webuijsf:jobStatus id="job" numJobs="1" 
                          rendered="#{MastheadBean.cb2Selected}" 
                          actionExpression="#{MastheadBean.jobstatusClicked}"/>
                      </f:facet>             
                      <f:facet name="dateTimeInfo">       
                         <webuijsf:timeStamp id="time" rendered="#{MastheadBean.cb3Selected}" />
                      </f:facet>
                      <f:facet name="currentAlarmsInfo">  
                          <webuijsf:alarmStatus id="alarm" immediate="true" 
                              rendered="#{MastheadBean.cb4Selected}"
                              actionExpression="#{MastheadBean.alarmClicked}">
                                         
                        <!-- Alarm Status Facets -->
                        <f:facet name="downAlarms">
                          <webuijsf:panelGroup id="downAlarmsPanel">
                            <webuijsf:imageHyperlink id="downAlarmsLink" styleClass="#{themeStyles.MASTHEAD_ALARM_LINK}" style="text-decoration:none"
                                               icon="ALARM_MASTHEAD_DOWN_DIMMED" text="0" disabled="true" >                         
                              <f:param name="severity" value="down" />
                              </webuijsf:imageHyperlink>
                            <f:verbatim><![CDATA[&nbsp;&nbsp;&nbsp;]]></f:verbatim>
                          </webuijsf:panelGroup>
                        </f:facet>
                        
                        <f:facet name="criticalAlarms">
                          <webuijsf:panelGroup id="criticalAlarmsPanel">
                            <webuijsf:imageHyperlink id="criticalAlarmsLink" styleClass="#{themeStyles.MASTHEAD_ALARM_LINK}" style="text-decoration:none"
                                               icon="ALARM_MASTHEAD_CRITICAL_MEDIUM" text="1"  actionExpression="#{MastheadBean.alarmClicked}" >
                              <f:param name="severity" value="critical"/>
                            </webuijsf:imageHyperlink>
                         <f:verbatim><![CDATA[&nbsp;&nbsp;&nbsp;]]></f:verbatim>
                          </webuijsf:panelGroup>
                        </f:facet>
                        
                        <f:facet name="majorAlarms">
                          <webuijsf:panelGroup id="majorAlarmsPanel">
                            <webuijsf:imageHyperlink id="majorAlarmsLink" styleClass="#{themeStyles.MASTHEAD_ALARM_LINK}" style="text-decoration:none"
                                               icon="ALARM_MASTHEAD_MAJOR_MEDIUM" text="2" actionExpression="#{MastheadBean.alarmClicked}" >
                              <f:param name="severity" value="major"/>
                            </webuijsf:imageHyperlink>
                          <f:verbatim><![CDATA[&nbsp;&nbsp;&nbsp;]]></f:verbatim>
                          </webuijsf:panelGroup>
                        </f:facet>
                        
                        <f:facet name="minorAlarms">
                          <webuijsf:panelGroup id="minorAlarmsPanel">
                            <webuijsf:imageHyperlink id="minorAlarmsLink" styleClass="#{themeStyles.MASTHEAD_ALARM_LINK}" style="text-decoration:none"
                                               icon="ALARM_MASTHEAD_MINOR_MEDIUM" text="3" actionExpression="#{MastheadBean.alarmClicked}" >
                              <f:param name="severity" value="minor"/>
                            </webuijsf:imageHyperlink>
                            <f:verbatim><![CDATA[&nbsp;&nbsp;&nbsp;]]></f:verbatim>
                          </webuijsf:panelGroup>
                        </f:facet>
                        
                      </webuijsf:alarmStatus>
                     <webuijsf:alarmStatus numDownAlarms="0" numCriticalAlarms="1" numMajorAlarms="2" numMinorAlarms="3" />
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
