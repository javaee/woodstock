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
                <webuijsf:head id="filePopupHead" title="#{msgs.fileChooserPopup_title}">
                </webuijsf:head>
                <webuijsf:body>
                    <webuijsf:form id="fileFormPopup">

                       <!-- Masthead -->
                       <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
                         productImageDescription="#{msgs.mastheadAltText}" 
                         userInfo="test_user"
                         serverInfo="test_server" secondary="true"/>     
                       
                       <br />
                       <!-- Alert for validator exception. -->
                       <webuijsf:alert id="popupFileChooserAlert" rendered="#{ChooserUploaderBean.errorsOnPage}" type="error" 
                                       summary="#{ChooserUploaderBean.summaryMsg}">                              
                             <webuijsf:message for="fileChooser" showDetail="true"/>
                       </webuijsf:alert>
                     
                       <!-- Page Title -->
                       <webuijsf:contentPageTitle id="filePopupContent" title="#{msgs.fileChooserPopup_title}">                       
                         <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">                         
                                                  
                         <!-- File Chooser -->
                         <f:verbatim><![CDATA[<table><tr><td>]]></f:verbatim>
                           <webuijsf:fileChooser id="fileChooser"
			                lookin="#{ChooserUploaderBean.fileChooserLookin}"
                                        selected="#{ChooserUploaderBean.selected}"
                                       sortField="size" descending="true" 
                                        multiple="true"  folderChooser="false"/>
                         <f:verbatim><![CDATA[</td></tr>]]></f:verbatim>
                                                 
                         <!--  Close button -->
                         <f:verbatim><![CDATA[<tr><td>]]></f:verbatim>
				<webuijsf:button id="close"
                                     text="#{msgs.fileChooserPopup_closeButtonCaption}" 
                                     toolTip="#{msgs.fileChooserPopup_closeButtonTooltip}"
                                     onClick="window.close();"/>
                         <f:verbatim><![CDATA[</td></tr></table>]]></f:verbatim>
                         </webuijsf:markup>
                      </webuijsf:contentPageTitle>
                    </webuijsf:form>
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
