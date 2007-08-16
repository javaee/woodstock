<jsp:root version="2.0" 
          xmlns:f="http://java.sun.com/jsf/core" 
          xmlns:h="http://java.sun.com/jsf/html" 
          xmlns:jsp="http://java.sun.com/JSP/Page" 
          xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
          
<jsp:directive.page contentType="text/html" /> 
                    
<!-- Page Title Example -->
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
  <webuijsf:page id="page1" >
    <webuijsf:html id="html1" >
      <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
        <webuijsf:head id="head1" title="#{msgs.pagetitle_title}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body id="body1" >
            <webuijsf:form id="form1">
            
              <!-- Masthead -->
              <webuijsf:masthead id="masthead" serverInfo="test_server" userInfo="test_user" 
                           productImageURL="/images/example_primary_masthead.png" 
                           productImageDescription="#{msgs.mastheadAltText}"/>
                           
              <!-- Breadcrumbs -->             
              <webuijsf:breadcrumbs id="breadcrumbs">
                <webuijsf:hyperlink id="hyp1" actionExpression="#{PagetitleBean.showIndex}" text="#{msgs.exampleTitle}"
                              toolTip="#{msgs.index_title}" immediate="true"
                              onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true"
                              onMouseOut="javascript:window.status=''; return true"/>
                <webuijsf:hyperlink id="hyp2" text="#{msgs.pagetitle_title}"/>
              </webuijsf:breadcrumbs>
              
              <!-- Alerts -->
             <webuijsf:alert id="message" type="information" rendered="#{PagetitleBean.isRendered}"
                       summary="#{PagetitleBean.message}" detail="#{PagetitleBean.detail}" />
                        
             <webuijsf:alert id="error" type="error" rendered="#{PagetitleBean.errorsOnPage}"
                       summary="#{PagetitleBean.message}" detail="#{PagetitleBean.detail}"> 
             </webuijsf:alert>
              
              <!-- Page Title -->
              <webuijsf:contentPageTitle id="pagetitle" title="#{msgs.pagetitle_title}" helpText="#{msgs.pagetitle_helpText}">

                <!-- Page Buttons Top -->
                <f:facet name="pageButtonsTop">
                  <webuijsf:panelGroup id="pageButtonsGroupTop"> 
                    <webuijsf:button id="save1" text="#{msgs.pagetitle_save}" primary="true" actionExpression="#{PagetitleBean.saveClicked}" /> 
                    <webuijsf:button id="reset1" text="#{msgs.pagetitle_reset}" 
                               immediate="true" actionListenerExpression="#{PagetitleBean.resetClicked}" />
                  </webuijsf:panelGroup> 
                </f:facet> 
                <!-- Page Buttons Bottom -->
                <f:facet name="pageButtonsBottom">
                  <webuijsf:panelGroup id="pageButtonsGroupBottom"> 
                    <webuijsf:button id="save2" text="#{msgs.pagetitle_save}" primary="true" actionExpression="#{PagetitleBean.saveClicked}" /> 
                    <webuijsf:button id="reset2" text="#{msgs.pagetitle_reset}"  
                               immediate="true" actionListenerExpression="#{PagetitleBean.resetClicked}" />
                  </webuijsf:panelGroup> 
                </f:facet> 
                <!-- Page Actions -->
                <f:facet name="pageActions">
                  <webuijsf:hyperlink id="hyperlink1" text="#{msgs.pagetitle_hyperlink}" url="http://www.sun.com"
                                toolTip="#{msgs.pagetitle_hyperlinkToolTip}" immediate="true" />
                </f:facet> 
                <!-- Page Views DropDown -->
                <f:facet name="pageViews">
                  <webuijsf:dropDown id="dropdown" label="#{msgs.pagetitle_dropDown}" items="#{PagetitleBean.views}"
                               actionListenerExpression="#{PagetitleBean.menuChanged}" selected="#{PagetitleBean.selectedItem}" 
                               submitForm="true" immediate="true" />                              
                </f:facet>              

                <!-- Required field legend -->
                <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                  <webuijsf:legend id="legend"
                    style="margin-top:20px"
                    text="#{msgs.pageTitle_requiredLabel}" />
                </webuijsf:markup>
                
                <!-- Text fields -->
                <f:verbatim><![CDATA[<br/><table><tr><td>&nbsp;&nbsp;]]></f:verbatim>
                <webuijsf:label id="label1" for="text1" 
                  text="#{msgs.pagetitle_label1}"
                  toolTip="#{msgs.pageTitle_nameTooltip}" />
                <f:verbatim><![CDATA[</td><td>&nbsp;&nbsp;&nbsp;&nbsp;]]></f:verbatim>
                <webuijsf:textField id="text1" required="true" 
                  text="#{PagetitleBean.text1}" 
                  toolTip="#{msgs.pageTitle_nameTooltip}" />
                
                <f:verbatim><![CDATA[</tr><tr><td>&nbsp;&nbsp;]]></f:verbatim> 
                <webuijsf:label id="label2" for="text2" text="#{msgs.pagetitle_label2}" />
                <f:verbatim><![CDATA[</td><td>&nbsp;&nbsp;&nbsp;&nbsp;]]></f:verbatim> 
                <webuijsf:textField id="text2" text="#{PagetitleBean.text2}"/>
                <f:verbatim><![CDATA[</td></tr></table>]]></f:verbatim>
                
              </webuijsf:contentPageTitle>                                         
            </webuijsf:form>
          </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>
</jsp:root>
