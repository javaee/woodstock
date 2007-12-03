<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
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
    <webuijsf:page >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
      <webuijsf:html>
       <webuijsf:head title="#{msgs.accordion_indexTitle}" />
       <webuijsf:body>
         <webuijsf:form id="form1">
           <webuijsf:masthead id="masthead"
             productImageURL="/images/example_primary_masthead.png"
             productImageHeight="40"
             productImageWidth="188"
             userInfo="test_user" 
             serverInfo="test_server"
             productImageDescription="#{msgs.mastheadAltText}" />
             
           <webuijsf:breadcrumbs id="breadcrumbs">
             <webuijsf:hyperlink url="../index.jsp" text="#{msgs.index_title}"/>
             <webuijsf:hyperlink url="index.jsp" text="#{msgs.accordion_indexTitle}"/>
           </webuijsf:breadcrumbs>

           <webuijsf:contentPageTitle title="#{msgs.accordion_indexTitle}" />

           <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

             <br/><br/>
             <webuijsf:hyperlink url="example1.jsp" 
               text="#{msgs.accordion_basicTitle}"/>

             <br/><br/>
             <webuijsf:hyperlink url="example2.jsp" 
	       text="#{msgs.accordion_navTitle}"/>

             <br/><br/>
             <webuijsf:hyperlink url="example3.jsp" 
	       text="#{msgs.accordion_refreshTitle}"/>
               
             <br/><br/>
             <webuijsf:hyperlink url="a11yAccordion.jsp" 
	       text="#{msgs.accordion_a11y}"/>

           </webuijsf:markup>
         </webuijsf:form>
       </webuijsf:body> 
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
