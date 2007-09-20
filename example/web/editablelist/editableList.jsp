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
        <webuijsf:head id="editablelistHead" title="#{msgs.editablelist_title}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
        </webuijsf:head>
        <webuijsf:body>
          <webuijsf:form id="editListForm">

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
                actionExpression="#{EditableListBean.showExampleIndex}" 
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink id="editableLink" text="#{msgs.editablelist_title}"/>
            </webuijsf:breadcrumbs>
                       
            <!-- Alert for validator exception. -->
            <webuijsf:alert id="alertId" 
              rendered="#{EditableListBean.errorsOnPage}"
              type="error" 
              summary="#{EditableListBean.summaryMsg}"> 
              <webuijsf:message for="editListForm:editableContentPage:editList:editList_field" showDetail="true"/>
              <webuijsf:message for="editListForm:editableContentPage:editList" showDetail="true"/>
            </webuijsf:alert>
                        
            <!-- Page Title -->
            <webuijsf:contentPageTitle id="editableContentPage" title="#{msgs.editablelist_title}">
              <f:facet name="pageButtonsBottom">
                <webuijsf:button id="okButton" primary="true" 
                  actionExpression="showEditableListResult" text="#{msgs.editablelist_Okbutton}"/>
              </f:facet>

              <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

                <webuijsf:legend id="legend" text="#{msgs.editablelist_requiredLabel}" />
                <webuijsf:label id="helplabel" labelLevel="3" text="#{msgs.editablelist_labelText}"/>
                <f:verbatim><![CDATA[<br/><br/>]]></f:verbatim>
                          
                <!-- Editable List -->
                <webuijsf:editableList id="editList"
                  list="#{EditableListBean.roles}" 
                  fieldLabel="#{msgs.editablelist_fieldlabel}"
                  listLabel="#{msgs.editablelist_listlabel}"
                  listOnTop="#{EditableListBean.listTopBottom}"
                  sorted="#{EditableListBean.sortedList}"   
                  fieldValidatorExpression="#{EditableListBean.validate}"
	          listValidatorExpression="#{EditableListBean.validateList}"
                  multiple="true" required="true"
                  toolTip="#{msgs.editablelist_toolTip}">
                </webuijsf:editableList>

                <webuijsf:helpInline id="fieldHelp" type="field" text="#{msgs.editablelist_helpText}" />
                <f:verbatim><![CDATA[<br/><br/>]]></f:verbatim>

                <webuijsf:panelGroup id="pageButtonsGroupBottom" separator="&lt;br&gt;">
                  <webuijsf:checkbox id="topBottom" selected="#{EditableListBean.listTopChkBox}"
                    label="#{msgs.editablelist_chkboxList}" immediate="true"
                    valueChangeListenerExpression="#{EditableListBean.listOnToplistener}"/>
                  <webuijsf:checkbox id="sortedList" selected="#{EditableListBean.sortedChkBox}"
                    label="#{msgs.editablelist_chkboxSort}" immediate="true"
                    valueChangeListenerExpression="#{EditableListBean.sortedlistener}"/>
                </webuijsf:panelGroup>

                <f:verbatim><![CDATA[<br/><br/>]]></f:verbatim>
                <webuijsf:button id="redisplay" primary="true" immediate="true"
                  text="#{msgs.editablelist_redisplayButton}"/>

              </webuijsf:markup>
            </webuijsf:contentPageTitle>
          </webuijsf:form>
        </webuijsf:body> 
      </webuijsf:html>  
    </webuijsf:page>
  </f:view>
</jsp:root>
