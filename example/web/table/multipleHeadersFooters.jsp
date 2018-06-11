<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
  <jsp:directive.page contentType="text/html" />
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
        <webuijsf:head title="#{msgs.table_nestedColumnsTitle}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />        
          <webuijsf:script url="js/select.js"/>
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
              <webuijsf:hyperlink actionExpression="#{TableBean.showExampleIndex}" text="#{msgs.exampleTitle}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink actionExpression="#{TableBean.showTableIndex}" text="#{msgs.table_title}"
                onMouseOver="javascript:window.status='#{msgs.table_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink text="#{msgs.table_nestedColumnsTitle}"/>
            </webuijsf:breadcrumbs>
  
            <webuijsf:contentPageTitle title="#{msgs.table_nestedColumnsTitle}"/>
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
  
              <!-- Messages -->
              <webuijsf:messageGroup showSummary="true" showDetail="true"/>
              <br/>
  
              <!-- Multiple Headers & Footers -->
              <webuijsf:table id="table1"
                clearSortButton="true"
                deselectMultipleButton="true"
                deselectMultipleButtonOnClick="setTimeout('disableActions()', 0)"
                footerText="#{msgs.table_tableFooter}"
                paginateButton="true"
                paginationControls="true"
                selectMultipleButton="true"
                selectMultipleButtonOnClick="setTimeout('disableActions()', 0)"
                sortPanelToggleButton="true"
                title="#{msgs.table_nestedColumnsTitle}">
                <webuijsf:tableRowGroup id="rowGroup1"
                  binding="#{TableBean.groupA.tableRowGroup}"
                  rows="#{TableBean.groupA.preferences.rows}"
                  selected="#{TableBean.groupA.select.selectedState}"
                  sourceData="#{TableBean.groupA.names}"
                  sourceVar="name">
                  <webuijsf:tableColumn id="col0"
                    selectId="select"
                    sort="#{TableBean.groupA.select.selectedState}">
                    <webuijsf:checkbox id="select"
                      onClick="setTimeout('initAllRows(); disableActions()', 0)"
                      selected="#{TableBean.groupA.select.selected}"
                      selectedValue="#{TableBean.groupA.select.selectedValue}"/>
                  </webuijsf:tableColumn>
                  <webuijsf:tableColumn id="col1"
                    alignKey="last"
                    headerText="#{msgs.table_LastName}"
                    rowHeader="true">
                    <webuijsf:staticText text="#{name.value.last}"/>
                  </webuijsf:tableColumn>
                  <webuijsf:tableColumn id="col2"
                    alignKey="first" headerText="#{msgs.table_LastName}">
                    <webuijsf:staticText text="#{name.value.first}"/>
                  </webuijsf:tableColumn>
  
                  <webuijsf:tableColumn id="col3" headerText="#{msgs.table_taskStatus}">
        
                    <!-- Nested Columns -->
                    <webuijsf:tableColumn id="col3a"
                      headerText="A"
                      footerText="#{msgs.table_columnFooter} A"
                      sort="statusA"
                      sortIcon="ALARM_CRITICAL_MEDIUM"
                      tableFooterText="#{msgs.table_tableColumnFooter} A">
                      <webuijsf:staticText text="#{name.value.statusA}"/>
                    </webuijsf:tableColumn>
                    <webuijsf:tableColumn id="col3b"
                      headerText="B"
                      footerText="#{msgs.table_columnFooter} B"
                      sort="statusB"
                      sortIcon="ALARM_MAJOR_MEDIUM"
                      tableFooterText="#{msgs.table_tableColumnFooter} B">
                      <webuijsf:staticText text="#{name.value.statusB}"/>
                    </webuijsf:tableColumn>
                    <webuijsf:tableColumn id="col3c"
                      headerText="C"
                      footerText="#{msgs.table_columnFooter} C"
                      sortIcon="ALARM_MINOR_MEDIUM"
                      tableFooterText="#{msgs.table_tableColumnFooter} C">
                      <webuijsf:staticText text="#{name.value.statusC}"/>
                    </webuijsf:tableColumn>
                    <webuijsf:tableColumn id="col3d"
                      headerText="D"
                      footerText="#{msgs.table_columnFooter} D"
                      sortIcon="ALARM_DOWN_MEDIUM"
                      tableFooterText="#{msgs.table_tableColumnFooter} D">
                      <webuijsf:staticText text="#{name.value.statusD}"/>
                    </webuijsf:tableColumn>
                  </webuijsf:tableColumn>
                </webuijsf:tableRowGroup>
              </webuijsf:table>
  
              <br/>
              <webuijsf:staticText text="#{msgs.table_nestedColumnsConcepts1}"/>
  
              <br/><br/>
              <webuijsf:staticText text="#{msgs.table_nestedColumnsConcepts2}"/>
  
            </webuijsf:markup>
          </webuijsf:form>
        </webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
