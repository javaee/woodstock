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
        <webuijsf:head title="#{msgs.table_title}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />
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
              <webuijsf:hyperlink actionExpression="#{IndexBean.showIndex}" text="#{msgs.exampleTitle}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink text="#{msgs.table_title}"/>
            </webuijsf:breadcrumbs>
  
            <!-- Main Example -->
            <webuijsf:contentPageTitle title="#{msgs.table_mainTitle}">
              <webuijsf:markup tag="br" singleton="true"/>
              <webuijsf:panelGroup style="margin-left:10px" id="linkGroup1" block="true" separator="&lt;br /&gt;" >
                <webuijsf:hyperlink id="table"
                  text="#{msgs.table_tableTitle}"
                  actionExpression="showTableMain"
                  onMouseOver="javascript:window.status='#{msgs.table_tableTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
              </webuijsf:panelGroup>
            </webuijsf:contentPageTitle>
            
            <!-- TLD Examples -->
            <webuijsf:contentPageTitle title="#{msgs.table_tldTitle}">
              <webuijsf:markup tag="br" singleton="true"/>
              <webuijsf:panelGroup style="margin-left:10px" id="linkGroup2" block="true" separator="&lt;br /&gt;" >
                <webuijsf:hyperlink id="actions"
                  text="#{msgs.table_actionsTitle}"
                  actionExpression="showTableActions"
                  onMouseOver="javascript:window.status='#{msgs.table_actionsTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="alarms"
                  text="#{msgs.table_alarmsTitle}"
                  actionExpression="showTableAlarms"
                  onMouseOver="javascript:window.status='#{msgs.table_alarmsTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="basicTable"
                  text="#{msgs.table_basicTitle}"
                  actionExpression="showTableBasic"
                  onMouseOver="javascript:window.status='#{msgs.table_basicTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="customTitle"
                  text="#{msgs.table_customTitle}"
                  actionExpression="showTableCustomTitle"
                  onMouseOver="javascript:window.status='#{msgs.table_customTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="dynamicGroupTable"
                  text="#{msgs.table_dynamicGroupTitle}"
                  actionExpression="showTableDynamicGroupTable"
                  onMouseOver="javascript:window.status='#{msgs.table_dynamicGroupTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="dynamicTable"
                  text="#{msgs.table_dynamicTitle}"
                  actionExpression="showTableDynamicTable"
                  onMouseOver="javascript:window.status='#{msgs.table_dynamicTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="embeddedActions"
                  text="#{msgs.table_embeddedActionsTitle}"
                  actionExpression="showTableEmbeddedActions"
                  onMouseOver="javascript:window.status='#{msgs.table_embeddedActionsTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="emptyCells"
                  text="#{msgs.table_emptyCellsTitle}"
                  actionExpression="showTableEmptyCells"
                  onMouseOver="javascript:window.status='#{msgs.table_emptyCellsTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="filter"
                  text="#{msgs.table_filterTitle}"
                  actionExpression="showTableFilter"
                  onMouseOver="javascript:window.status='#{msgs.table_filterTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="groupTable"
                  text="#{msgs.table_groupTableTitle}"
                  actionExpression="showTableGroupTable"
                  onMouseOver="javascript:window.status='#{msgs.table_groupTableTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="hiddenSelectedRows"
                  text="#{msgs.table_hiddenSelectedRowsTitle}"
                  actionExpression="showTableHiddenSelectedRows"
                  onMouseOver="javascript:window.status='#{msgs.table_hiddenSelectedRowsTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="liteTable"
                  text="#{msgs.table_liteTitle}"
                  actionExpression="showTableLiteTable"
                  onMouseOver="javascript:window.status='#{msgs.table_liteTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="nestedColumns"
                  text="#{msgs.table_nestedColumnsTitle}"
                  actionExpression="showTableMultiHeadersFooters"
                  onMouseOver="javascript:window.status='#{msgs.table_nestedColumnsTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="paginatedTable"
                  text="#{msgs.table_paginatedTableTitle}"
                  actionExpression="showTablePaginatedTable"
                  onMouseOver="javascript:window.status='#{msgs.table_paginatedTableTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="preferences"
                  text="#{msgs.table_preferencesTitle}"
                  actionExpression="showTablePreferences"
                  onMouseOver="javascript:window.status='#{msgs.table_preferencesTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="selectMultipleRows"
                  text="#{msgs.table_selectMultipleRowsTitle}"
                  actionExpression="showTableSelectMultiRows"
                  onMouseOver="javascript:window.status='#{msgs.table_selectMultipleRowsTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="selectSingleRow"
                  text="#{msgs.table_selectSingleRowTitle}"
                  actionExpression="showTableSelectSingleRow"
                  onMouseOver="javascript:window.status='#{msgs.table_selectSingleRowTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="spacerColumn"
                  text="#{msgs.table_spacerColumnTitle}"
                  actionExpression="showTableSpacerColumn"
                  onMouseOver="javascript:window.status='#{msgs.table_spacerColumnTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
                <webuijsf:hyperlink id="sortableTable"
                  text="#{msgs.table_sortableTableTitle}"
                  actionExpression="showTableSortableTable"
                  onMouseOver="javascript:window.status='#{msgs.table_sortableTableTitle}'; return true;"
                  onMouseOut="javascript: window.status=''; return true" />
              </webuijsf:panelGroup>
            </webuijsf:contentPageTitle>
          </webuijsf:form>
        </webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
