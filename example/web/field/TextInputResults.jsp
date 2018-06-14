<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
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
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.field_resultsPageTitle}">                        
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
                actionExpression="#{TextInputBean.showExampleIndex}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="examplePageLink"
                text="#{msgs.field_title}"
                toolTip="#{msgs.field_title}"
                actionExpression="showTextInput"
                onMouseOver="javascript:window.status='#{msgs.field_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="resultsPageLink" text="#{msgs.field_resultsPageTitle}"/>
          </webuijsf:breadcrumbs>
          
          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle"              
              title="#{msgs.field_resultsPageTitle}"
              helpText="#{msgs.field_resultsPageHelpText}" >

            <!-- Back Button -->
            <f:facet name="pageButtonsTop">                
              <webuijsf:button id="backButton"
                  text="#{msgs.field_backButton}"   
                  immediate="true"
                  actionExpression="showTextInput" />               
            </f:facet>            
          </webuijsf:contentPageTitle> 
                    
          <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
              <br />              
              <webuijsf:staticText id="textFieldState" text="#{TextInputBean.textFieldResult}" />             
              <br />                                      
              <webuijsf:staticText id="passwordState" text="#{TextInputBean.passwordResult}" />                                           
              <br />                           
              <webuijsf:staticText id="textAreaState" text="#{TextInputBean.textAreaResult}" />                        
          </webuijsf:markup>
                  
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
 </f:view>
</jsp:root>
