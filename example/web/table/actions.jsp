<jsp:root version="2.0" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
  <jsp:directive.page contentType="text/html" />
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
        <webuijsf:head title="#{msgs.table_actionsTitle}">
          <webuijsf:link rel="shortcut icon" url="/images/favicon.ico" type="image/x-icon" />        
          <webuijsf:script url="js/select.js"/>
          <webuijsf:script url="js/actions.js"/>
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
              <webuijsf:hyperlink text="#{msgs.table_actionsTitle}"/>
            </webuijsf:breadcrumbs>

            <webuijsf:contentPageTitle title="#{msgs.table_actionsTitle}"/>
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">

              <!-- Messages -->
              <webuijsf:messageGroup showSummary="true" showDetail="true"/>
              <webuijsf:staticText text="#{TableBean.groupA.messages.message}"/>
              <br/>

              <!-- Actions -->
              <webuijsf:table id="table1"
                deselectMultipleButton="true"
                deselectMultipleButtonOnClick="setTimeout('disableActions()', 0)"
                paginateButton="true"
                paginationControls="true"
                selectMultipleButton="true"
                selectMultipleButtonOnClick="setTimeout('disableActions()', 0)"
                title="#{msgs.table_actionsTitle}">
                <webuijsf:tableRowGroup id="rowGroup1"
                  binding="#{TableBean.groupA.tableRowGroup}"
                  rows="5"
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
                    alignKey="last" headerText="#{msgs.table_LastName}" rowHeader="true">
                    <webuijsf:staticText text="#{name.value.last}"/>
                  </webuijsf:tableColumn>
                  <webuijsf:tableColumn id="col2" alignKey="first" headerText="#{msgs.table_FirstName}">
                    <webuijsf:staticText text="#{name.value.first}"/>
                  </webuijsf:tableColumn>
                </webuijsf:tableRowGroup>

                <!-- Actions (Top) -->
                <f:facet name="actionsTop">
                  <f:subview id="actionsTop">
                    <jsp:include page="actionsTop.jsp"/>
                  </f:subview>
                </f:facet>

                <!-- Actions (Bottom) -->
                <f:facet name="actionsBottom">
                  <f:subview id="actionsBottom">
                    <jsp:include page="actionsBottom.jsp"/>
                  </f:subview>
                </f:facet>
              </webuijsf:table>

              <br/>
              <webuijsf:staticText text="#{msgs.table_actionConcepts}"/>

            </webuijsf:markup>
          </webuijsf:form>
        </webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>
