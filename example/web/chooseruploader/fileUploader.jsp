<?xml version="1.0" encoding="UTF-8"?>
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

<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html"/>
    <f:view>
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
                             <webuijsf:message for=":uploaderForm:upload" showDetail="true"/>
                        </webuijsf:alert>
                        
                        <!-- Page Title -->
                        <webuijsf:contentPageTitle id="uploaderContentPage" title="#{msgs.fileUploader_title}" />
                       
                         <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                         <table><tr style="height:5px"><td>
                         </td></tr>
                         <tr style="height:10px"><td></td></tr>
                                     
                         <!-- File Uploader -->
                         <tr><td>
                            <webuijsf:upload id="upload"  
                            uploadedFile = "#{FileUploaderBean.uploadedFile}"
                            required="true"
                            label="#{msgs.uploader_uploadLabel}"
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
