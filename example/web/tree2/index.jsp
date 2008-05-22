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
  <webuijsf:page id="Page">
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.tree2_title}" debug="true">
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
                actionExpression="#{IndexBean.showIndex}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="alertIndexLink" text="#{msgs.tree2_title}"/>
          </webuijsf:breadcrumbs>
          
          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle" title="#{msgs.tree2_title}">
              
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">              
              <webuijsf:markup tag="br" singleton="true" />
                
              <webuijsf:markup tag="div" style="padding-bottom: 8px">              
                <!-- Dynamic Tree Link -->
                <webuijsf:hyperlink id="link1" 
                    url="/faces/tree2/example1.jsp"
                    immediate="true"                    
                    text="#{msgs.tree2_example1}"                  
                 />              
              </webuijsf:markup>               
                
              <!-- Navigation Link -->
              <webuijsf:markup tag="div" style="padding-bottom: 8px">   
              <webuijsf:hyperlink id="link2" 
                    url="/faces/tree2/example2.jsp"
                    immediate="true"                    
                    text="#{msgs.tree2_example2}"           
                 />  
               </webuijsf:markup> 
               <webuijsf:markup tag="div" style="padding-bottom: 8px">          
               <webuijsf:hyperlink id="link3" 
                    url="/faces/tree2/example3.jsp"
                    immediate="true"                    
                    text="#{msgs.tree2_example3}"                
                 /> 
               </webuijsf:markup> 
               <webuijsf:markup tag="div" style="padding-bottom: 8px">   
               <webuijsf:hyperlink id="link4" 
                    url="/faces/tree2/example4.jsp"
                    immediate="true"                    
                    text="#{msgs.tree2_example4}"                 
                 />     
               </webuijsf:markup> 
               
               <webuijsf:markup tag="div" style="padding-bottom: 8px">   
               <webuijsf:hyperlink id="link5" 
                    url="/faces/tree2/example5.jsp"
                    immediate="true"                    
                    text="#{msgs.tree2_example5}"                   
                 />     
               </webuijsf:markup> 
               
               <webuijsf:markup tag="div" style="padding-bottom: 8px">   
               <webuijsf:hyperlink id="link6" 
                    url="/faces/tree2/example6.jsp"
                    immediate="true"                    
                    text="#{msgs.tree2_example6}"                   
                 />     
               </webuijsf:markup> 
               
               <webuijsf:markup tag="div" style="padding-bottom: 8px">   
               <webuijsf:hyperlink id="link7" 
                    url="/faces/tree2/example7.jsp"
                    immediate="true"                    
                    text="#{msgs.tree2_example7}"                   
                 />     
               </webuijsf:markup> 
               
             </webuijsf:markup> 
          </webuijsf:contentPageTitle>           
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
</f:view>

</jsp:root>

