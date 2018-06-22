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
             
                    var dirPath=document.getElementById("folderFormPopup:folderContent:folderChooser:folderChooser_lookinField").value;
                    var selfield = document.getElementById("folderFormPopup:folderContent:folderChooser:folderChooser_selectedField").value;
                        if (selfield.length > 0) {
                            if (((dirPath.lastIndexOf('\\')) == (dirPath.length-1))){
                                     dirPath = dirPath + selfield;   

                            } else if ((dirPath.indexOf('\\')) >= 0 ) {
                                     dirPath = dirPath + "\\" + selfield;  
                            }
                            if ( (dirPath.lastIndexOf('/')) == (dirPath.length-1)){
                                     dirPath = dirPath + selfield;            
                            } else if ((dirPath.lastIndexOf('/')) >= 0) {
                                     dirPath = dirPath + "/" + selfield;
                            }
                        }
                        window.opener.document.getElementById("uploaderFormPopup:dirPath_field").value = dirPath;
                                window.close();    
                    }
               
                 </script>
                </webuijsf:head>
                <webuijsf:body>

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
                     <script>
                         document.getElementById("folderForm:folderContent:folderChooser").setChooseButton("folderFormPopup:folderContent:select_folder");
                     </script>
                    </webuijsf:form>
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
