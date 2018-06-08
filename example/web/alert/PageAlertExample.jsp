<%--

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

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

--%>

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
  <webuijsf:page>
    <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
    <webuijsf:html id="html">  
      <webuijsf:head id="head" title="#{msgs.alert_fullpageTitle}">
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
                actionExpression="#{PageAlertBean.showExampleIndex}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="alertIndexLink"
                text="#{msgs.alert_title}"
                toolTip="#{msgs.alert_title}"
                actionExpression="#{PageAlertBean.showAlertIndex}" 
                onMouseOver="javascript:window.status='#{msgs.alert_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
            <webuijsf:hyperlink id="pageAlertLink" text="#{msgs.alert_fullpageTitle}" />
          </webuijsf:breadcrumbs>                            
          
          <!-- Alert -->
          <webuijsf:alert id="alert"                
              type="error"
              summary="#{msgs.alert_sumException}" 
              rendered="#{PageAlertBean.inlineAlertRendered}" />
                
          <!-- Content Page Title -->
          <webuijsf:contentPageTitle id="contentPageTitle"              
              title="#{msgs.alert_fullpageTitle}"
              helpText="#{msgs.alert_line1Text}" >
              
            <!-- OK Button -->
            <f:facet name="pageButtonsBottom">                                                       
              <webuijsf:button id="okButton"                 
                  text="#{msgs.alert_okButton}" 
                  actionExpression="#{PageAlertBean.showPageAlert}"
		  primary="true"		   
                  actionListenerExpression="#{PageAlertBean.processButtonAction}"/>
            </f:facet>
                    
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">                                                        
                <webuijsf:markup tag="br" singleton="true" />
                 
                <!-- Number Entry Field -->
                <webuijsf:textField id="numberTextField"
                    label="#{msgs.alert_guess}"
                    columns="4"
                    required="true"
                    toolTip="#{msgs.alert_textFieldTitle}"
                    text="#{PageAlertBean.fieldValue}"
                    validatorExpression="#{PageAlertBean.validateFieldEntry}" />                                              
            </webuijsf:markup>                      
            
          </webuijsf:contentPageTitle>           
        </webuijsf:form>
      </webuijsf:body>
    </webuijsf:html>  
  </webuijsf:page>
</f:view>

</jsp:root>
