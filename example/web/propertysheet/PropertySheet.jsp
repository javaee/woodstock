<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html"/>
    <f:view>
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
        <webuijsf:page >
            <webuijsf:html>
                <f:loadBundle basename="com.sun.webui.jsf.example.resources.Resources" var="msgs" />
                <webuijsf:head id="propertyhead" title="#{msgs.propertysheet_title}">
                  <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />        
                </webuijsf:head>
                <webuijsf:body>
                                              
                <webuijsf:form id="propertyForm">
                                           
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
                                          actionExpression="#{PropertySheetBean.showExampleIndex}" 
                                          onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                                          onMouseOut="javascript: window.status=''; return true" />
                            <webuijsf:hyperlink id="propertylink" text="#{msgs.propertysheet_title}"/>
                      </webuijsf:breadcrumbs>
                        
                      <!-- Page Title -->
                      <webuijsf:contentPageTitle id="propertyContentPage" title="#{msgs.propertysheet_title}" >
                                             
                        <!-- OK and Cancel button -->
                        <f:facet name="pageButtonsTop">
                            <webuijsf:panelGroup id="pageButtonsGroupTop">
                                <webuijsf:button id="okButton" primary="true" actionExpression="showPropertySheetResult"
                                           text="#{msgs.propertysheet_Okbutton}"  />
                                <webuijsf:button id="cancelButton" primary="true" text="#{msgs.propertysheet_CancelButton}" 
                                           immediate="true" actionExpression="#{PropertySheetBean.showExampleIndex}"/>
                            </webuijsf:panelGroup>
                        </f:facet>
                        <f:facet name="pageButtonsBottom">
                            <webuijsf:panelGroup id="pageButtonsGroupBottom">
                                <webuijsf:button id="okButtonbottom" primary="true" actionExpression="showPropertySheetResult"
                                           text="#{msgs.propertysheet_Okbutton}"  />
                                <webuijsf:button id="cancelButtonbottom" primary="true" text="#{msgs.propertysheet_CancelButton}" 
                                           immediate="true" actionExpression="#{PropertySheetBean.showExampleIndex}"/>
                            </webuijsf:panelGroup>
                        </f:facet>

                        <!-- PropertySheet Example.... -->
                        <webuijsf:propertySheet id="propertySheet" jumpLinks="#{PropertySheetBean.jumpLink}" 
                                          requiredFields="#{PropertySheetBean.requiredLabel}">
                        
                           <!-- Text Field section -->               
                          <webuijsf:propertySheetSection id="propertSectionTextField" label="#{msgs.propertysheet_textField}">
                              <webuijsf:property id="propertyTextField"  labelAlign="left" noWrap="true" overlapLabel="false"
						 label="#{msgs.propertysheet_userIdLabel}">
                               <webuijsf:textField id="textField"
                                             text="#{TextInputBean.textFieldValue}"
                                             disabled="#{TextInputBean.textFieldDisabled}"                    
                                             toolTip="#{msgs.field_textFieldTitle}" 
                                             required="true"/>      
                              </webuijsf:property>
                              <webuijsf:property id="propertyPwd"  labelAlign="left" noWrap="true" overlapLabel="false"
						 label="#{msgs.propertysheet_passwordLabel}">
                                 <webuijsf:passwordField id="password"                                        
                                            disabled="#{TextInputBean.passwordDisabled}"                    
                                            toolTip="#{msgs.field_passwordTitle}"
                                            required="true"/>    
                              </webuijsf:property>
                              <f:verbatim><![CDATA[<br><br>]]></f:verbatim>
                          </webuijsf:propertySheetSection>

                          <!-- File/Folder Chooser section-->
                          <webuijsf:propertySheetSection id="propertSectionChooser" label="#{msgs.propertysheet_chooser}">
                              <webuijsf:property id="propertyFile"  
                                           labelAlign="left" noWrap="true" overlapLabel="false">
                              
                                 <webuijsf:fileChooser id="fileChooser"
                                                lookin="#{FileChooserBean.lookin}"
                                                selected="#{FileChooserBean.selected}"
                                                sortField="size" descending="true" 
                                                multiple="true"  folderChooser="false">
                                    <f:facet name="fileChooserLabel">
                                        <webuijsf:staticText styleClass="#{themeStyles.CONTENT_FIELDSET_LEGEND_DIV}" id="filelabel" 
                                                       text="#{msgs.propertysheet_fileChoosertext}" />
                                    </f:facet>
                                 </webuijsf:fileChooser>
                              </webuijsf:property>
                              <webuijsf:property id="propertyFolder"  
                                           labelAlign="left" noWrap="true" overlapLabel="false">
                                 <webuijsf:fileChooser id="folderChooser"
                                                 lookin="#{FolderChooserBean.lookin}"
                                                 selected="#{FolderChooserBean.selected}"
                                                 sortField="size" descending="true" 
                                                 multiple="false" folderChooser="true">
                                    <f:facet name="fileChooserLabel">
                                        <webuijsf:staticText styleClass="#{themeStyles.CONTENT_FIELDSET_LEGEND_DIV}" id="folderlabel" 
                                                       text="#{msgs.propertysheet_folderChoosertext}" />
                                    </f:facet>
                                 </webuijsf:fileChooser>
                              </webuijsf:property>
                          </webuijsf:propertySheetSection>
                           
                          <!-- Orderable List section -->
                          <webuijsf:propertySheetSection id="propertSectionOrderable" label="#{msgs.propertysheet_orderable}">
                              <webuijsf:property id="propertyOrderable" labelAlign="left" noWrap="false" overlapLabel="false">
                                  <webuijsf:label labelLevel="1" id="orderLabel" text="#{msgs.propertysheet_orederableHeading}"/>
                    
                                  <!-- Orderable List -->
                                  <webuijsf:orderableList id="OrderableList"
                                                    list="#{OrderableListBean.listItems}"
                                                    multiple="true"
                                                    labelOnTop="true"                  
                                                    moveTopBottom="true" /> 
                              </webuijsf:property>
                          </webuijsf:propertySheetSection>
 
                          <!-- Add - Remove section -->
                          <webuijsf:propertySheetSection id="propertSectionAddRemove" label="#{msgs.propertysheet_addremove}">
                              <webuijsf:property id="propertyAddRemove"  labelAlign="left" noWrap="true" overlapLabel="false">
                                  <webuijsf:label labelLevel="1" id="addRemoveLabel" text="#{msgs.addremove_selectAuthors}" />
                                      
                                <!-- Add Remove -->
                                  <webuijsf:addRemove id="addRemove"
                                         moveButtons="true"
                                         selected="#{AddRemoveBean.selectedOptions}"
                                         items="#{AddRemoveBean.availableOptions}"                 
                                         availableItemsLabel="#{msgs.addremove_available}"
                                         selectedItemsLabel="#{msgs.addremove_selected}"                                    
                                         required="false"
                                         selectAll="true"                 
                                         labelOnTop="false" /> 
                              </webuijsf:property>
                          </webuijsf:propertySheetSection>
                        
                          <!-- Table section --> 
                          <webuijsf:propertySheetSection id="propertSectionBasicTable" label="#{msgs.propertysheet_table}">
                              <webuijsf:property id="propertyBasicTable"  labelAlign="left" 
                                           noWrap="true" overlapLabel="false">                        
                           
                                   <!-- Basic Table -->
                                   <webuijsf:table id="table1" title="#{msgs.table_basicTitle}">
                                      <webuijsf:tableRowGroup id="rowGroup1"
                                                        sourceData="#{TableBean.groupB.names}" sourceVar="name">
                                          <webuijsf:tableColumn id="col1"
                                                          alignKey="last" headerText="#{msgs.table_LastName}" 
                                                          rowHeader="true">
                                              <webuijsf:staticText text="#{name.value.last}"/>
                                          </webuijsf:tableColumn>
                                          <webuijsf:tableColumn id="col2" alignKey="first" headerText="#{msgs.table_FirstName}">
                                             <webuijsf:staticText text="#{name.value.first}"/>
                                          </webuijsf:tableColumn>
                                      </webuijsf:tableRowGroup>
                                   </webuijsf:table>
                              </webuijsf:property>
                          </webuijsf:propertySheetSection>
                        
                          <!-- Page Configuration Section -->
                          <webuijsf:propertySheetSection id="propertSectionPage" label="#{msgs.propertysheet_pageConfig}">
                              <webuijsf:property id="propertyPageJump" label="#{msgs.propertysheet_jumpLink}"  labelAlign="left" noWrap="true" 
                                       helpText="#{msgs.propertysheet_jumpLinkHelp}" overlapLabel="false">
                             
                                 <webuijsf:checkbox id="jumpLink" selected="#{PropertySheetBean.jumpLinkChkBox}"
                                              valueChangeListenerExpression="#{PropertySheetBean.jumpMenulistener}" 
                                              immediate="true" label="#{msgs.propertysheet_jumpChkBox}"/>
                              </webuijsf:property>
                              <webuijsf:property id="propertyPageField" label="#{msgs.propertysheet_required}"  labelAlign="left" noWrap="true" 
                                           helpText="#{msgs.propertysheet_requiredHelp}" overlapLabel="false">
                            
                                 <webuijsf:checkbox id="requiredLabel" selected="#{PropertySheetBean.requiredLabelChkBox}"
                                              label="#{msgs.propertysheet_requiredChkBox}" immediate="true"
                                              valueChangeListenerExpression="#{PropertySheetBean.requiredValuelistener}" />
                              </webuijsf:property>
                              <webuijsf:property id="redisplayButton" labelAlign="left" noWrap="true" 
                                            overlapLabel="false">
                            
                                 <webuijsf:button id="redisplay" primary="true" immediate="true"
                                            text="#{msgs.propertysheet_redisplayButton}"/>
                              </webuijsf:property>
                          </webuijsf:propertySheetSection>
                        
                        </webuijsf:propertySheet>        
                      </webuijsf:contentPageTitle>       
                        
                    </webuijsf:form>           
                </webuijsf:body> 
            </webuijsf:html>  
        </webuijsf:page>
    </f:view>
</jsp:root>
