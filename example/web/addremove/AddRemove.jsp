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
      <webuijsf:head id="head" title="#{msgs.addremove_title}">                        
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
                actionExpression="#{AddRemoveBean.showExampleIndex}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="exampleLink" text="#{msgs.addremove_title}"/>
          </webuijsf:breadcrumbs>
          
          <!-- Alert -->                
          <webuijsf:alert id="alert"                
              type="error"
              summary="#{msgs.addremove_error}"
              detail="#{AddRemoveBean.alertDetail}"              
              rendered="#{AddRemoveBean.alertRendered}" />                
          
          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle" title="#{msgs.addremove_title}">
            <webuijsf:markup id="markup1" tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
              <webuijsf:legend id="legend" text="#{msgs.addremove_requiredLabel}" />

              <webuijsf:markup id="markup2" tag="br" singleton="true" />
                            
              <!-- Label -->
              <webuijsf:label id="addremoveLabel"
                  for="addRemove"
                  hideIndicators="true"
                  toolTip="#{msgs.addremove_tooltip}"
                  text="#{AddRemoveBean.labelText}" />  

              <!-- Add Remove -->
              <webuijsf:addRemove id="addRemove"                  
                  moveButtons="true"
                  selected="#{AddRemoveBean.selectedOptions}"
                  items="#{AddRemoveBean.availableOptions}"                 
                  availableItemsLabel="#{msgs.addremove_available}"
                  selectedItemsLabel="#{msgs.addremove_selected}"
                  toolTip="#{msgs.addremove_tooltip}"
                  vertical="#{AddRemoveBean.verticalLayout}"
                  required="true"
                  selectAll="true"                 
                  labelOnTop="true" />
                  
              <webuijsf:markup id="markup3" tag="br" singleton="true" />              
              
              <!-- Show Vertical/Horizontal Orientation Link -->
              <webuijsf:hyperlink id="orientation"
                  text="#{AddRemoveBean.linkText}"
                  immediate="true"                  
                  actionExpression="#{AddRemoveBean.orientationLinkActionHandler}" />
                                   
              <webuijsf:panelGroup id="pageActionsGroup" block="true" style="padding-top:25px">                   
                <!-- Show Items Button -->            
                <webuijsf:button id="submitButton"                       
                    text="#{msgs.addremove_showItemsButton}"
                    actionListenerExpression="#{AddRemoveBean.resetDataProvider}"
                    actionExpression="#{AddRemoveBean.showItemsButtonActionHandler}" />
                    
                <!-- Preset Button -->
                <webuijsf:button id="presetButton"
                    immediate="true"
                    text="#{msgs.addremove_presetButton}" 
                    actionListenerExpression="#{AddRemoveBean.presetList}" />   
              </webuijsf:panelGroup>                    
                          
            </webuijsf:markup>          
          </webuijsf:contentPageTitle>      
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
 </f:view>
</jsp:root>
