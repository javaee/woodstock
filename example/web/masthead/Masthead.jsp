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
  <webuijsf:page id="page2" >
    <webuijsf:html id="html2" >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head id="head2" title="#{msgs.masthead_title}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body id="body2" >
            <webuijsf:form id="form2">
              
              <!-- Masthead with Attributes -->
              <webuijsf:masthead id="masthead" serverInfo="#{MastheadBean.server}" userInfo="#{MastheadBean.user}" 
                           dateTime="true" notificationMsg="#{msgs.masthead_notificationmsg}" jobCount="0"
                           productImageURL="/images/example_primary_masthead.png" productImageDescription="#{msgs.mastheadAltText}"
                           utilities="#{MastheadBean.links}" 
                           alarmCounts="#{MastheadBean.alarms}" >
                           
                <!-- Utility Bar Facets -->
                <f:facet name="consoleLink" >
                  <webuijsf:hyperlink id="hyp1" toolTip="#{msgs.masthead_consoleLink}"
                                actionExpression="#{MastheadBean.consolePage1Clicked}" immediate="true" />
                </f:facet>
                <f:facet name="versionLink" >
                  <webuijsf:hyperlink  id="hyp2" onClick="javascript: var versionWin = window.open('/example/faces/masthead/Version.jsp', 'VersionWindow','scrollbars,resizable,
                                 width=650,height=500,top='+((screen.height - (screen.height/1.618)) - (500/2))+',left='+((screen.width-650)/2) ); versionWin.focus();
                                 if(window.focus){versionWin.focus();} " immediate="true"
                                 toolTip="#{msgs.masthead_versionLink}" actionExpression="#{MastheadBean.versionPage1Clicked}"/>
                </f:facet> 
                <f:facet name="logoutLink">
                  <webuijsf:hyperlink id="hyp3" actionExpression="#{MastheadBean.logoutPage1Clicked}" toolTip="#{msgs.masthead_logoutLink}" immediate="true" />
                </f:facet>   
                <f:facet name="helpLink" >
                  <webuijsf:hyperlink id="hyp4" actionExpression="#{MastheadBean.helpPage1Clicked}" toolTip="#{msgs.masthead_helpLink}" immediate="true" />
                </f:facet>
              </webuijsf:masthead> 
   
              <!-- Breadcrumbs -->
              <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink id="hyp5" actionExpression="#{IndexBean.showIndex}" text="#{msgs.exampleTitle}"
                              toolTip="#{msgs.index_title}" immediate="true"
                              onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true"/>
                <webuijsf:hyperlink id="hyp6" actionExpression="#{MastheadBean.goToMastheadIndex}" 
                              text="#{msgs.masthead_title}" toolTip="#{msgs.masthead_titleToolTip}"
                              onMouseOver="javascript:window.status='#{msgs.masthead_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true" immediate="true"/>
                <webuijsf:hyperlink id="hyp7" text="#{msgs.masthead_masthead1Title}" />
              </webuijsf:breadcrumbs>
              
              <!-- Alert -->              
              <webuijsf:alert id="message" type="information" summary="#{MastheadBean.message}" rendered="#{MastheadBean.isRendered1}" />
              
              <!-- Page Title -->
              <webuijsf:contentPageTitle id="pagetitle" title="#{msgs.masthead_pageTitle1}" helpText="#{msgs.masthead_helpText1}" />
             
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}"> 
              <br/>
         
              <!-- Hyperlink to Popup window with Secondary Masthead -->
              <webuijsf:hyperlink  id="hyperlinkpopup" text="#{msgs.masthead_link}" toolTip="#{msgs.masthead_linkToolTip}" immediate="true"
                             onClick="javascript: var popupWin = window.open('/example/faces/masthead/Popup.jsp','PopUpWindow','scrollbars,resizable,width=650,
                             height=500,top='+((screen.height - (screen.height/1.618)) - (500/2))+',left='+((screen.width-650)/2) ); popupWin.focus();
                             if(window.focus){popupWin.focus();} return false;" />                           
              </webuijsf:markup>                           
              
            </webuijsf:form>
          </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>
</jsp:root>
