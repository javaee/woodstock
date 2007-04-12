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
      <webuijsf:head id="head" title="#{msgs.tabset_stateResultsTitle}">
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
                actionExpression="#{StateBean.showExampleIndex}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="tabsetIndexLink"
                text="#{msgs.tabset_title}"
                toolTip="#{msgs.tabset_title}"
                actionExpression="#{StateBean.showTabsetIndex}"
                onMouseOver="javascript:window.status='#{msgs.tabset_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="tabsetLink" text="#{msgs.tabset_stateResultsTitle}" />
          </webuijsf:breadcrumbs>

          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle"
              helpText="#{msgs.tabset_stateResultsHelpText}"
              title="#{msgs.tabset_stateResultsTitle}" >

              <!-- Back button -->
              <f:facet name="pageButtonsTop">
                <webuijsf:button id="backButton" primary="true"
                  text="#{msgs.tabset_backButton}"
                  immediate="true"
                  actionExpression="#{StateBean.showStateTabs}" />
               </f:facet>

            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
              <!-- Results for Textfield tab -->
              <br />              
              <webuijsf:staticText id="userIdState" 
                text="#{StateBean.userIdResult}" />
              <br />                                      
              <webuijsf:staticText id="passwordState" 
                text="#{StateBean.passwordResult}" />

              <br />                           
              <br />                           

              <!-- Results for Calendar tab -->
              <webuijsf:staticText id="calendarState"
                text="#{StateBean.calendarResult}" />

              <br />                           
              <br />                           

              <!-- Results for File Chooser tab -->
              <webuijsf:table id="chooserStateState" style="width:50%"
                title="#{msgs.tabset_chooserResult}">
                <webuijsf:tableRowGroup id="rowGroup1"
                    sourceData="#{StateBean.chooserResult.dataProvider}"
                    sourceVar="data">                    
                  <webuijsf:tableColumn id="col1">                      
                    <webuijsf:staticText text="#{data.value.value}" />
                  </webuijsf:tableColumn>                                    
                </webuijsf:tableRowGroup>
              </webuijsf:table>                                  

              <br />                           
              <br />                           

              <!-- Results for Orderable List tab -->
              <webuijsf:table id="orderableListState" style="width:50%"
                title="#{msgs.tabset_orderableListResult}">
                <webuijsf:tableRowGroup id="rowGroup1"
                    sourceData="#{StateBean.orderableListResult.dataProvider}"
                    sourceVar="data">                    
                  <webuijsf:tableColumn id="col1">                      
                    <webuijsf:staticText text="#{data.value.value}" />
                  </webuijsf:tableColumn>                                    
                </webuijsf:tableRowGroup>
              </webuijsf:table>                                  

              <br />                           
              <br />                           

              <!-- Results for AddRemove tab -->
              <webuijsf:table id="addRemoveState" style="width:50%"
                title="#{msgs.tabset_addRemoveResult}">
                <webuijsf:tableRowGroup id="rowGroup1"
                    sourceData="#{StateBean.selectedOptionsResult.dataProvider}"
                    sourceVar="data">                    
                  <webuijsf:tableColumn id="col1">                      
                    <webuijsf:staticText text="#{data.value.label}" />
                  </webuijsf:tableColumn>                                    
                </webuijsf:tableRowGroup>
              </webuijsf:table>                                  

            </webuijsf:markup>
          </webuijsf:contentPageTitle>
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>

</jsp:root>
