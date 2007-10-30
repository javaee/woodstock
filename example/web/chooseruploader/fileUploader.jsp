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
        <webuijsf:page>
            <webuijsf:html>
                <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
                <webuijsf:head id="uploaderHead" title="#{msgs.fileUploader_title}">
		  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
                </webuijsf:head>
                <webuijsf:body>
                                                 
                <webuijsf:form id="uploaderForm">
                                           
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
                                          actionExpression="#{FileUploaderBean.showExampleIndex}" 
                                          onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true" />
                            <webuijsf:hyperlink id="fileExample" actionExpression="#{FileUploaderBean.showUploaderIndex}" text="#{msgs.chooserUploader_title}"
                                          onMouseOver="javascript:window.status='#{msgs.chooserUploader_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true" 
                                          toolTip="#{msgs.chooserUploader_title}" />   
                            <webuijsf:hyperlink id="uploaderLink" text="#{msgs.fileUploader_title}"/>
                        </webuijsf:breadcrumbs>
                        
                        <!-- Alert for validator exception. -->
                        <webuijsf:alert id="uploaderAlert" rendered="#{FileUploaderBean.errorsOnPage}" type="error" 
                                    summary="#{FileUploaderBean.summaryMsg}"> 
                        </webuijsf:alert>
                        
                        <!-- Page Title -->
                        <webuijsf:contentPageTitle id="uploaderContentPage" title="#{msgs.fileUploader_title}" />
                       
                         <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                         <webuijsf:legend id="legend" text="#{msgs.uploader_requiredLabel}" />
                         <table><tr style="height:5px"><td>
                         </td></tr>
                         <tr style="height:10px"><td></td></tr>

                         <tr><td>
                           <!-- Bucket for error messages.  Note that error
                             messages from the upload component itself will
                             NOT invalidate the field, and so the upload's
                             label will not show the field as invalid.  Errors
                             from the validator will however invalidate the field.
                             To make the behavior identical for all messages, you
                             would need to create a binding for the upload in the
                             FileUploaderBackingBean class and then invalidate
                             the field after trapping on the FacesMessage.
                             -->
                           <webuijsf:message for="upload" showDetail="true"/>
                         </td></tr>
                                     
                         <!-- File Uploader -->
                         <tr><td>
                            <webuijsf:upload id="upload"  
                            uploadedFile = "#{FileUploaderBean.uploadedFile}"
                            required="true"
                            label="#{msgs.uploader_uploadLabel}"
                            toolTip="#{msgs.uploader_uploadLabelTooltip}"
                            validatorExpression="#{FileUploaderBean.validateFile}"/>
                         <f:verbatim><![CDATA[ &nbsp; ]]></f:verbatim>
                         </td></tr>
                         <tr style="height:20px"><td></td></tr>
                         
                            <!-- File Uploader Button -->
                         <tr><td>
                           <webuijsf:button primary="true"  text="#{msgs.uploader_uploadButtonCaption}" id="button"  
                                       actionExpression="#{FileUploaderBean.writeFile}"
                                       toolTip="#{msgs.uploader_uploadButtonToolTip}"/>
                         </td></tr>
                        
                                <!-- Uploaded File Path -->
                         <tr><td>
                           <webuijsf:label id="folder_label" text="#{msgs.fileUploader_chooseFileText}">
                              <webuijsf:staticText
                                        id="uploaded_file"
                                        text="#{FileUploaderBean.fileName}" />
                              </webuijsf:label>
                         </td></tr></table>
                         </webuijsf:markup>
                         
                   </webuijsf:form>
               </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
