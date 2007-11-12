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
                <webuijsf:head id="folderChooserHead" title="#{msgs.folderChooser_title}">
                <script>                  
                 
                   /**
                     * This js function populates the directory field of parent screen.  
                     * 
                     */
                    function fnPopulateDir(){
             
		    // Note that explicitly referencing the subcomponent
		    // TextField input element in the file chooser is not
		    // appropriate. There is no way to guarantee that
		    // the id will be literally defined this way.
		    // The file chooser javascript must expose an
		    // interface for this field if it is required
		    // for applications.
		    //
                    var lookInField = document.getElementById("folderFormPopup:folderContent:folderChooser:folderChooser_lookinField");
                    var dirPath = lookInField.getProps().value;

                    var selectedField = document.getElementById("folderFormPopup:folderContent:folderChooser:folderChooser_selectedField");
                    var dirName = selectedField.getProps().value;
                        if (dirName.length > 0) {
                            if (((dirPath.lastIndexOf('\\')) == (dirPath.length-1))){
                                     dirPath = dirPath + dirName;   

                            } else if ((dirPath.indexOf('\\')) >= 0 ) {
                                     dirPath = dirPath + "\\" + dirName;  
                            }
                            if ( (dirPath.lastIndexOf('/')) == (dirPath.length-1)){
                                     dirPath = dirPath + dirName;            
                            } else if ((dirPath.lastIndexOf('/')) >= 0) {
                                     dirPath = dirPath + "/" + dirName;
                            }
                        }
                        var dirField = window.opener.document.getElementById("uploaderFormPopup:dirPath");
                        dirField.setProps({value: dirPath});
                        window.close();
                    }
                    function init() {
                        var domNode = document.getElementById("folderFormPopup:folderContent:folderChooser");
                        if (domNode == null || typeof domNode.setChooseButton != "function") {
                            return setTimeout('init();', 10);
                        }
                        domNode.setChooseButton("folderFormPopup:folderContent:select_folder");
                    }
                    </script>
                </webuijsf:head>
                <webuijsf:body onLoad="init();">

                    <webuijsf:form id="folderFormPopup">

                   <!-- Masthead -->
                   <webuijsf:masthead id="Masthead" productImageURL="/images/example_primary_masthead.png"
                     productImageDescription="#{msgs.mastheadAltText}" 
                     userInfo="test_user"
                     serverInfo="test_server" secondary="true"/>     

                     <br />
                     <!-- Alert for validator exception. -->
                     <webuijsf:alert id="popupFolderChooserAlert" rendered="#{ChooserUploaderBean.errorsOnPage}" type="error" 
                                     summary="#{ChooserUploaderBean.summaryMsg}">                              
                          <webuijsf:message for="folderChooser" showDetail="true"/>
                     </webuijsf:alert>
                                                
                     <!-- Page Title -->
                     <webuijsf:contentPageTitle title="#{msgs.folderChooser_title}" id="folderContent">
                       <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                            
                       <!-- Folder Chooser -->
                       <f:verbatim><![CDATA[<table><tr><td>]]></f:verbatim>
                         <webuijsf:fileChooser id="folderChooser"
                                         lookin="#{ChooserUploaderBean.lookin}"
                                         selected="#{ChooserUploaderBean.selected}"
                                         sortField="size" descending="true" 
                                         multiple="false" folderChooser="true"/>
                       <f:verbatim><![CDATA[</td></tr>]]></f:verbatim>
                                                 
                       <!--  Folder Chooser button -->
                       <f:verbatim><![CDATA[<tr><td>]]></f:verbatim>
                               <webuijsf:button id="select_folder"
                                        text="#{msgs.folderChooser_buttonCaption}" 
                                     onClick="fnPopulateDir()"
                                     toolTip="#{msgs.folderChooser_buttonTooltip}"/>
                       <f:verbatim><![CDATA[</td></tr></table>]]></f:verbatim>
                       </webuijsf:markup>
                     </webuijsf:contentPageTitle>
                    </webuijsf:form>
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
