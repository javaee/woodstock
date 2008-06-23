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
        <webuijsf:head  title="#{msgs.table2_basicTitleFilter}" >
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
              <webuijsf:hyperlink actionExpression="#{Table2Bean.showExampleIndex}" text="#{msgs.exampleTitle}"
                onMouseOver="javascript:window.status='#{msgs.index_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink actionExpression="#{Table2Bean.showTableIndex}" text="#{msgs.table_title}"
                onMouseOver="javascript:window.status='#{msgs.table_breadcrumbMouseOver}'; return true;"
                onMouseOut="javascript: window.status=''; return true" />
              <webuijsf:hyperlink text="#{msgs.table2_basicTitleFilter}"/>
            </webuijsf:breadcrumbs>

           <webuijsf:contentPageTitle title="#{msgs.table2_basicTitleFilter}"/>
        <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

        <!-- Messages -->
        <webuijsf:messageGroup showSummary="true" showDetail="true"/>
        <webuijsf:staticText text="#{TableBean.groupA.messages.message}"/>
        <webuijsf:markup tag="br" singleton="true"/>

        <webuijsf:table2 id="table1" filterText="#{Table2Bean.groupA.filter.filterText}"
                         title="#{msgs.table2_tableTitle}" tableControls="true"
                         tableTips="true"
                         tips="#{msgs.table2_tableTips}">
              <webuijsf:table2RowGroup id="rowGroup1"
                  binding="#{Table2Bean.groupA.tableRowGroup}"
                  sourceData="#{Table2Bean.groupA.names}"
                  sourceVar="name"
                  rows="5"
                  headerText="#{msgs.table_detail}">
                    <webuijsf:table2Column id="col0" 
                        footerText="#{msgs.table2_index}"
                        headerText="#{msgs.table2_index}">
                      <webuijsf:staticText text="#{name.tableRow.rowId + 1}"/>
                    </webuijsf:table2Column>
                    <webuijsf:table2Column id="col1" 
                        footerText="#{msgs.table2_last}" 
                        headerText="#{msgs.table2_last}">
                      <webuijsf:staticText text="#{name.value.last}"/>
                    </webuijsf:table2Column>
                    <webuijsf:table2Column id="col2" 
                        footerText="#{msgs.table2_first}" 
                        headerText="#{msgs.table2_first}">
                      <webuijsf:staticText text="#{name.value.first}"/>
                    </webuijsf:table2Column>
              </webuijsf:table2RowGroup>
              <!-- Filter -->
             <f:facet name="filter">
                <webuijsf:dropDown id="filter" label="#{msgs.table2_filterLabel}"       
                    items="#{Table2Bean.groupA.filter.filterOptions}"
                    onChange="callRefresh(this.value); return false;"
                    valueChangeListenerExpression="#{Table2Bean.groupA.filter.filterType}"
                    selected="#{Table2Bean.groupA.filter.basicFilter}"/>                 
             </f:facet>
             <!-- Custom Filter Panel -->
             <f:facet name="filterPanel">
                <f:subview id="filterPanel">
                  <jsp:include page="filter.jsp"/>
                </f:subview>
             </f:facet>
        </webuijsf:table2>
<webuijsf:script>
    // custom filter
    function updateFilter() {
        var domNode = document.getElementById("form1:table1:rowGroup1");
        var dropdown = document.getElementById("form1:table1:filter_list");
        domNode.refresh("form1:table1:filterPanel:customFilter_field");     
        processEvents.doUpdate = true;
        domNode.subscribe(domNode.event.refresh.endTopic, processEvents, "update");        
    }
    // call refresh
    function callRefresh(val) {
        var dom = document.getElementById("form1:table1");
        var domNode = document.getElementById("form1:table1:rowGroup1");
        if (val == "SEARCH_RESULT") {            
            dom.toggleFilterPanel(true);            
        } else {
            dom.toggleFilterPanel(false);                                    
        }
        domNode.refresh('form1:table1:filter_list');
    }
    //close filter panel
    function closeFilterPanel() {
        var dom = document.getElementById("form1:table1");
        dom.toggleFilterPanel(false);      
        return false;
    }
    var processEvents = {
      doUpdate: false,
      update: function(props) {      
          if (this.doUpdate) {
                document.getElementById('form1:table1:filter_list').value = "SEARCH_RESULT";
          }
          this.doUpdate = false;
      }
    }
</webuijsf:script>
        </webuijsf:markup>
      </webuijsf:form>
    </webuijsf:body>
    </webuijsf:html>
  </webuijsf:page>
</f:view>
</jsp:root>
