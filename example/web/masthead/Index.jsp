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
  <webuijsf:page id="page1">
    <webuijsf:html id="html1" >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head id="head1" title="#{msgs.masthead_title}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body id="body1" >
            <webuijsf:form id="form1">
            
              <!-- Masthead with Attributes -->
              <webuijsf:masthead id="masthead" serverInfo="#{MastheadBean.server}" userInfo="#{MastheadBean.user}" 
                           productImageURL="/images/example_primary_masthead.png" productImageDescription="#{msgs.mastheadAltText}"/>
                           
              <!-- Breadcrumbs -->       
              <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink id="hyplink1" actionExpression="#{IndexBean.showIndex}" text="#{msgs.exampleTitle}"
                              toolTip="#{msgs.index_title}" immediate="true"
                              onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true" />
                <webuijsf:hyperlink id="hyplink2" text="#{msgs.masthead_title}"/>
              </webuijsf:breadcrumbs>
              
              <!-- Page Title -->
              <webuijsf:contentPageTitle id="pagetitle" title="#{msgs.masthead_title}" />
                                          
              <!-- Masthead with Attributes Page Hyperlink -->
              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}"> 
              <br/> 
              <webuijsf:hyperlink id="hyp1" text="#{msgs.masthead_masthead1Title}" toolTip="#{msgs.masthead_toolTip1}"
                            actionExpression="#{MastheadBean.goToPage1}" immediate="true" />
               
              <!-- Masthead with Facets Page Hyperlink -->
              <br/> 
              <webuijsf:hyperlink id="hyp2" text="#{msgs.masthead_masthead2Title}" toolTip="#{msgs.masthead_toolTip2}"
                            actionExpression="#{MastheadBean.goToPage2}" immediate="true" />
              
              <!-- Image and ImageHyperlink Page Hyperlink -->
              <br/> 
              <webuijsf:hyperlink id="hyp3" text="#{msgs.masthead_masthead3Title}" toolTip="#{msgs.masthead_toolTip3}"
                            actionExpression="#{MastheadBean.goToPage3}" immediate="true" />
              </webuijsf:markup>
                            
            </webuijsf:form>
          </webuijsf:body> 
    </webuijsf:html>
  </webuijsf:page>
</f:view>
</jsp:root>
