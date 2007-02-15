<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
<jsp:directive.page contentType="text/html;charset=UTF-8" 
                    pageEncoding="UTF-8"/>
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
    <webuijsf:head title="#{msgs.commontask_title}" />
    <webuijsf:body>
    <webuijsf:form id="form1">
          <webuijsf:masthead id="masthead"
             productImageURL="/images/example_primary_masthead.png"
             productImageHeight="40"
             productImageWidth="188"
             userInfo="test_user" 
             serverInfo="test_server"
             productImageDescription="#{msgs.mastheadAltText}" />
        <webuijsf:contentPageTitle id="cpt0" title="#{msgs.commontask_sampletitle}" />          
        <webuijsf:markup tag="div" extraAttributes="align='middle'">
            <webuijsf:staticText id="primary" text="#{msgs.commontask_tasklaunch}" style="font-weight:bold"/>
            <br /><br /><br />
            <webuijsf:button id="button2" text="#{msgs.commontask_closeButton}" onClick = "window.close();return false;"/>
        </webuijsf:markup>
                                   
    </webuijsf:form>
    </webuijsf:body>
  </webuijsf:page>
</f:view>
</jsp:root>



