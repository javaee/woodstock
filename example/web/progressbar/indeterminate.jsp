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
        <webuijsf:page >
            <webuijsf:html>
                <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
                <webuijsf:head id="progressBarHead" title="#{msgs.progressbar_title}">
		  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
                </webuijsf:head>
                <webuijsf:body>
                    <webuijsf:form id="form1">

                       <!-- Masthead -->
                       <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
                         productImageDescription="#{msgs.mastheadAltText}" 
                         userInfo="test_user"
                         serverInfo="test_server" />     
                       
                       <!-- Bread Crumb Component -->
                       <webuijsf:breadcrumbs id="breadcrumbs">
                            <webuijsf:hyperlink id="exampleLink"
                                          text="#{msgs.index_title}"
                                          toolTip="#{msgs.index_title}"
                                          actionExpression="#{ProgressBarBean.showExampleIndex}" 
                                          onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true" />
                            <webuijsf:hyperlink id="progressBarIndex" toolTip="#{msgs.progressbar_example}"
                                                actionExpression="#{ProgressBarBean.showProgressBarIndex}"
                                                text="#{msgs.progressbar_example}"/>            
                            <webuijsf:hyperlink id="progressBarLink" text="#{msgs.progressbar_indeterminateText}"/>
                       </webuijsf:breadcrumbs>
                       
                                               
                       <!-- Page Title -->
                       <webuijsf:contentPageTitle id="progressBarContentPage" title="#{msgs.progressbar_indeterminateText}">
                          <f:facet name="pageButtonsBottom">
                            <webuijsf:button id="backButton" primary="true" 
                                             actionExpression="#{ProgressBarBean.showProgressBarIndex}" 
                                             text="#{msgs.progressbar_backButton}"/>
                          </f:facet>
                          <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                          
                              <br/><br/>
                          
                              <!-- ProgressBar Indeterminate -->
                              <webuijsf:progressBar id="pb1" 
                                                    refreshRate="2000" 
                                                    description="#{msgs.progressbar_description}"
                                                    status="#{ProgressBean.status}"
                                                    type="INDETERMINATE" >

                                <f:facet name="progressControlRight">
                                    <f:subview id="statusPanel">
                                        <webuijsf:button mini="true" id="pauseButton" text="#{msgs.progressbar_pauseButton}" onClick="pause(); return false;"/>
                                        <webuijsf:button mini="true" id="resumeButton" text="#{msgs.progressbar_resumeButton}" onClick="resume(); return false;" disabled="true"/>
                                        <webuijsf:button mini="true" id="cancelButton" style="margin-left:15px;" text="#{msgs.progressbar_cancelButton}" onClick="cancel(); return false;"/>
                                    </f:subview>
                                </f:facet>

                            </webuijsf:progressBar>
                            <webuijsf:script type="text/javascript">
                                function pause() {
                                    // Enable resume button.
                                    var resumeButton = document.getElementById('form1:progressBarContentPage:pb1:statusPanel:resumeButton');
                                    resumeButton.setProps({disabled: false});
                                    resumeButton.focus();

                                    // Disable pause button and pause progress.
                                    var pauseButton = document.getElementById('form1:progressBarContentPage:pb1:statusPanel:pauseButton');
                                    pauseButton.setProps({disabled: true});
                                    document.getElementById('form1:progressBarContentPage:pb1').pause();
                                }
                                function resume() {
                                    // Enable pause button.
                                    var pauseButton = document.getElementById('form1:progressBarContentPage:pb1:statusPanel:pauseButton');
                                    pauseButton.setProps({disabled: false});
                                    pauseButton.focus();

                                    // Disable resume button and resume progress.
                                    var resumeButton = document.getElementById('form1:progressBarContentPage:pb1:statusPanel:resumeButton');
                                    resumeButton.setProps({disabled: true});
                                    document.getElementById('form1:progressBarContentPage:pb1').resume();
                                }
                                function cancel() {
                                    // Cancel progress.
                                    document.getElementById('form1:progressBarContentPage:pb1').cancel();
                                }
                            </webuijsf:script>                 
                          </webuijsf:markup>
                       </webuijsf:contentPageTitle>
             
                    </webuijsf:form>
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
