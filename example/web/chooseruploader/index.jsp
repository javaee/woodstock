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
                <webuijsf:head id="indexHead" title="#{msgs.chooserUploader_title}">
		  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
                </webuijsf:head>
                <webuijsf:body>
                                              
                <webuijsf:form id="indexForm">
                                           
                        <!-- Masthead -->
                        <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
                                    productImageDescription="#{msgs.mastheadAltText}" 
                                    userInfo="test_user"
                                    serverInfo="test_server" />     
                         
                        <!-- Bread Crumb Component -->
                        <webuijsf:breadcrumbs id="breadcrumbs">
                            <webuijsf:hyperlink id="exampleIndex"
                                          text="#{msgs.index_title}"
                                          toolTip="#{msgs.index_title}"
                                          actionExpression="#{IndexBean.showIndex}" 
                                          onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true" />
                            <webuijsf:hyperlink id="chooserIndex" text="#{msgs.chooserUploader_title}"/>
                        </webuijsf:breadcrumbs>
                        
                        <!-- Page Title -->
                        <webuijsf:contentPageTitle id="indexContentpage" title="#{msgs.chooserUploderIndex_title}" />
                            
                                <!-- Button Index-->
                                <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                                <table><tr style="height:20px"><td></td></tr><tr>
                                                                   
                                <!-- File Chooser hyperlink -->
                                <td>
                                   <webuijsf:hyperlink  text="#{msgs.chooserUploaderIndex_fileChooserhyperlink}" id="fileChooser"  
                                                  toolTip="#{msgs.chooserUploaderIndex_fileChooserTooltip}"
                                                  actionExpression="#{ChooserUploaderBean.forwardAction}"
                                                  actionListenerExpression="#{ChooserUploaderBean.goToPage}"/>
                                </td></tr><tr>
                                
                                <!-- File Uploader hyperlink -->
                                <td>
                                   <webuijsf:hyperlink  text="#{msgs.chooserUploaderIndex_fileUploaderhyperlink}" id="fileUploader"  
                                                  toolTip="#{msgs.chooserUploaderIndex_fileUploaderTooltip}"
                                                  actionExpression="#{ChooserUploaderBean.forwardAction}"
                                                  actionListenerExpression="#{ChooserUploaderBean.goToPage}"/>
                                </td></tr><tr>
                                    
                                <!--Folder Chooser hyperlink -->
                                <td>
                                   <webuijsf:hyperlink  text="#{msgs.chooserUploaderIndex_folderChooserhyperlink}" id="folderChooser"  
                                                  toolTip="#{msgs.chooserUploaderIndex_folderChooserTooltip}"
                                                  actionExpression="#{ChooserUploaderBean.forwardAction}"
                                                  actionListenerExpression="#{ChooserUploaderBean.goToPage}"/>
                                </td></tr><tr>
                                    
                                <!-- Uploader and Chooser hyperlink-->
                                <td>
                                   <webuijsf:hyperlink  text="#{msgs.chooserUploaderIndex_chooserUploaderhyperlink}" id="fileChooserUploader"  
                                                  toolTip="#{msgs.chooserUploaderIndex_chooserUploaderTooltip}"
                                                  actionExpression="#{ChooserUploaderBean.forwardAction}"
                                                  actionListenerExpression="#{ChooserUploaderBean.goToPage}"/>
                                </td></tr></table>
                                </webuijsf:markup>
                               
                    </webuijsf:form>           
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
