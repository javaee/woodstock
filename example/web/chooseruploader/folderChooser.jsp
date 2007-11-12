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
                <webuijsf:head id="folderHead" title="#{msgs.folderChooser_title}">
		  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
		  <f:verbatim><![CDATA[
                    <script type="text/javascript">
                        // This function is used to display a javascript alert 
                        // window when the Selected Files field is empty.
                        function checkSelectedFolder(msg) {
                            var domNode = document.getElementById("folderForm:folderContent:folderChooser:folderChooser_selectedField");
                            var selectedFile = domNode.getProps().value;
                            if (selectedFile == null || selectedFile.length == 0) {
                                alert(msg);                                
                                return false;
                            }
                            return true;
                        }
                    </script>
                  ]]></f:verbatim>
                     <webuijsf:script>
                         function init() {
                            var domNode = document.getElementById("folderForm:folderContent:folderChooser");
                            if (domNode == null || typeof domNode.setChooseButton != "function") {
                                return setTimeout('init();', 10);
                            }
                            domNode.setChooseButton("folderForm:folderContent:file_button");
                         }
                     </webuijsf:script>
                  </webuijsf:head>
                <webuijsf:body onLoad="init();">
                    <webuijsf:form id="folderForm">
                       
                      <!-- Masthead -->
                       <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
                         productImageDescription="#{msgs.mastheadAltText}" 
                         userInfo="test_user"
                         serverInfo="test_server" secondary="true"/>     
                        
                        <!-- Bread Crumb Component -->
                        <webuijsf:breadcrumbs id="breadcrumbs">
                            <webuijsf:hyperlink id="exampleIndex"
                                          text="#{msgs.index_title}"
                                          toolTip="#{msgs.index_title}"
                                          actionExpression="#{FolderChooserBean.showExampleIndex}" 
                                          onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true"/> 
                            <webuijsf:hyperlink id="chooserExample" actionExpression="#{FolderChooserBean.showUploaderIndex}" text="#{msgs.chooserUploader_title}"
                                          onMouseOver="javascript:window.status='#{msgs.chooserUploader_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true" 
                                          toolTip="#{msgs.chooserUploader_title}" />                            
                            <webuijsf:hyperlink id="folderExample" text="#{msgs.folderChooser_title}"/>
                        </webuijsf:breadcrumbs>
                  
                        <br />
                        <!-- Alert for validator exception. -->
                        <webuijsf:alert id="folderChooserAlert" rendered="#{FolderChooserBean.errorsOnPage}" type="error" 
                                        summary="#{FolderChooserBean.summaryMsg}">
                            <webuijsf:message for="folderChooser" showDetail="true"/>                                                   
                        </webuijsf:alert>                        
                        
                     <!-- Page Title -->
                     <webuijsf:contentPageTitle title="#{msgs.folderChooser_title}" id="folderContent">
                      <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">                                                                  
                                                 
                      <!-- Folder Chooser -->
                      <f:verbatim><![CDATA[<table><tr><td>]]></f:verbatim>
                              <webuijsf:fileChooser id="folderChooser"
			                      lookin="#{FolderChooserBean.lookin}"
                                              selected="#{FolderChooserBean.selected}"
                                              sortField="size" descending="true" 
		                              multiple="false" folderChooser="true"/>
                      <f:verbatim><![CDATA[</td></tr>]]></f:verbatim>
                                               
                      <!--  Folder Chooser button -->
                      <f:verbatim><![CDATA[<tr><td> &nbsp;&nbsp;]]></f:verbatim>
                        <webuijsf:label id="folder_label" text="#{msgs.folderChooser_choosefolderText}">
                           <webuijsf:staticText
                                  id="chosenFolder"
                                  text="#{FolderChooserBean.folderName}" />
                        </webuijsf:label>
                      <f:verbatim><![CDATA[</td></tr>]]></f:verbatim>
                       
                      <!-- Selected Folder Path -->                         
                      <f:verbatim><![CDATA[<tr><td>]]></f:verbatim>
                           <webuijsf:button id="file_button"
                                 primary="true" 
                                 text="#{msgs.folderChooser_chooseButtonCaption}"
                                 toolTip="#{msgs.folderChooser_chooseButtonCaptionTooltip}"
				 onClick="javascript: checkSelectedFolder('#{msgs.folderChooser_emptyFieldAlert}');"/>
                      <f:verbatim><![CDATA[</td></tr></table>]]></f:verbatim>
                      </webuijsf:markup>
                     </webuijsf:contentPageTitle>
                    </webuijsf:form>
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
