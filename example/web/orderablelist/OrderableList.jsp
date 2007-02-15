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
      <webuijsf:head id="head" title="#{msgs.orderablelist_title}" >
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
                actionExpression="showIndex"
                actionListenerExpression="#{OrderableListBean.processLinkAction}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="orderableListLink" text="#{msgs.orderablelist_title}"/>
          </webuijsf:breadcrumbs>
          
          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle"
              title="#{msgs.orderablelist_title}"
              helpText="#{msgs.orderablelist_helpTitle}" >
              
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
            <f:verbatim><![CDATA[<br /> ]]></f:verbatim>                           

              <!-- Orderable List -->
              <webuijsf:orderableList id="orderableList"                  
                  list="#{OrderableListBean.listItems}"
                  label="#{msgs.orderablelist_listHeading}"
                  multiple="true"
                  labelOnTop="true"                  
                  moveTopBottom="true" />
                  
              <webuijsf:panelGroup id="pageActionsGroup" block="true" style="padding:25px 0px 0px 10px;">                                         
                <!-- Show Ordered Results Button -->            
                <webuijsf:button id="showItems"                       
                    text="#{msgs.orderablelist_showItemsButton}"
                    actionListenerExpression="#{OrderableListBean.resetDataProvider}"
                    actionExpression="showOrderableListResults" />
                    
                <!-- Reset Order Button -->
                <webuijsf:button id="reset"
                    immediate="true"
                    text="#{msgs.orderablelist_resetButton}" 
                    actionListenerExpression="#{OrderableListBean.resetOrder}" />   
              </webuijsf:panelGroup>                   

            </webuijsf:markup>
          </webuijsf:contentPageTitle>                         
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
</f:view>
</jsp:root>
