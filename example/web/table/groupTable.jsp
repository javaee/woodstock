<%--

    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

    Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.

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

--%>

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
        <webuijsf:head title="#{msgs.table_groupTableTitle}">
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
              <webuijsf:hyperlink text="#{msgs.table_groupTableTitle}"/>
            </webuijsf:breadcrumbs>
  
            <webuijsf:contentPageTitle title="#{msgs.table_groupTableTitle}"/>
            <webuijsf:markup tag="div" styleClass="#{themeStyles.CONTENT_MARGIN}">
                
                <!-- Messages -->
                <webuijsf:messageGroup showSummary="true" showDetail="true"/>
                <br/>
                
                <!-- Group Table -->
                <webuijsf:table id="table1"
                                clearSortButton="true"
                                deselectMultipleButton="true"
                                selectMultipleButton="true"
                                sortPanelToggleButton="true"
                                footerText="#{msgs.table_tableFooter}">
                    
                    <!-- Title -->
                    <f:facet name="title">
                        <webuijsf:staticText text="#{msgs.table_groupTableTitle}"/>
                    </f:facet>
                    
                    <webuijsf:tableRowGroup id="rowGroup1"
                                            binding="#{TableBean.groupB.tableRowGroup}"
                                            footerText="#{msgs.table_groupFooter}"
                                            headerText="#{msgs.table_groupHeader}"
                                            selected="#{TableBean.groupB.select.selectedState}"
                                            selectMultipleToggleButton="true"
                                            sourceData="#{TableBean.groupB.names}"
                                            sourceVar="name"
                                            groupToggleButton="true">
                        
                        <webuijsf:tableColumn id="col0"
                                              extraHeaderHtml="nowrap='nowrap'"
                                              extraFooterHtml="nowrap='nowrap'"
                                              extraTableFooterHtml="nowrap='nowrap'"
                                              footerText=""
                                              selectId="select"
                                              sort="#{TableBean.groupB.select.selectedState}">
                            <webuijsf:checkbox id="select"
                                               onClick="setTimeout('initAllRows()', 0)"
                                               selected="#{TableBean.groupB.select.selected}"
                                               selectedValue="#{TableBean.groupB.select.selectedValue}"/>
                        </webuijsf:tableColumn>
                        <webuijsf:tableColumn id="col1"
                                              extraHeaderHtml="nowrap='nowrap'"
                                              alignKey="last"
                                              footerText="#{msgs.table_columnFooter}"
                                              headerText="#{msgs.table_LastName}"
                                              rowHeader="true"
                                              sort="last">
                            <webuijsf:staticText text="#{name.value.last}"/>
                        </webuijsf:tableColumn>
                        <webuijsf:tableColumn id="col2"
                                              alignKey="first"
                                              footerText="#{msgs.table_columnFooter}"
                                              headerText="#{msgs.table_FirstName}"
                                              sort="first">
                            <webuijsf:staticText text="#{name.value.first}"/>
                        </webuijsf:tableColumn>
                    </webuijsf:tableRowGroup>
                    
                    <webuijsf:tableRowGroup id="rowGroup2"
                                            binding="#{TableBean.groupC.tableRowGroup}"
                                            collapsed="true"
                                            selected="#{TableBean.groupC.select.selectedState}"
                                            selectMultipleToggleButton="true"
                                            sourceData="#{TableBean.groupC.names}"
                                            sourceVar="name"
                                            groupToggleButton="true">
                        
                        <!-- Row group header -->
                        <f:facet name="header">
                          <webuijsf:panelGroup id="groupHeader">
                            <webuijsf:markup tag="span" extraAttributes="class='#{themeStyles.TABLE_GROUP_HEADER_LEFT}'">
                              <webuijsf:staticText styleClass="#{themeStyles.TABLE_GROUP_HEADER_TEXT}" text="#{msgs.table_groupHeader}"/>
                            </webuijsf:markup>
                            <webuijsf:markup tag="span" extraAttributes="class='#{themeStyles.TABLE_GROUP_HEADER_RIGHT}'">
                              <webuijsf:staticText styleClass="#{themeStyles.TABLE_GROUP_MESSAGE_TEXT}" text="#{msgs.table_alignedRight}"/>
                            </webuijsf:markup>
                          </webuijsf:panelGroup>
                        </f:facet>
                        
                        <!-- Row group footer -->
                        <f:facet name="footer">
                            <webuijsf:staticText styleClass="#{themeStyles.TABLE_GROUP_FOOTER_TEXT}" text="#{msgs.table_groupFooter}"/>
                        </f:facet>
                        
                        <webuijsf:tableColumn id="col0"
                                              extraHeaderHtml="nowrap='nowrap'"
                                              extraFooterHtml="nowrap='nowrap'"
                                              extraTableFooterHtml="nowrap='nowrap'"
                                              footerText="#{msgs.table_columnFooter}"
                                              selectId="select"
                                              sort="#{TableBean.groupC.select.selectedState}"
                                              tableFooterText="">
                            <webuijsf:checkbox id="select"
                                               onClick="setTimeout('initAllRows()', 0)"
                                               selected="#{TableBean.groupC.select.selected}"
                                               selectedValue="#{TableBean.groupC.select.selectedValue}"/>
                        </webuijsf:tableColumn>
                        <webuijsf:tableColumn id="col1"
                                              alignKey="last"
                                              footerText="#{msgs.table_columnFooter}"
                                              rowHeader="true"
                                              sort="last"
                                              tableFooterText="#{msgs.table_tableColumnFooter}">
                            <webuijsf:staticText text="#{name.value.last}"/>
                        </webuijsf:tableColumn>
                        <webuijsf:tableColumn id="col2"
                                              alignKey="first"
                                              footerText="#{msgs.table_columnFooter}"
                                              sort="first"
                                              tableFooterText="#{msgs.table_tableColumnFooter}">
                            <webuijsf:staticText text="#{name.value.first}"/>
                        </webuijsf:tableColumn>
                    </webuijsf:tableRowGroup>
                </webuijsf:table>
                
                <div><webuijsf:button/> </div>
                
                <br/>
                <webuijsf:staticText text="#{msgs.table_groupTableConcepts1}"/>
                
                <br/><br/>
                <webuijsf:staticText text="#{msgs.table_groupTableConcepts2}"/>
                
                <br/><br/>
                <webuijsf:staticText text="#{msgs.table_groupTableConcepts3}"/>
                
            </webuijsf:markup>
          </webuijsf:form>
        </webuijsf:body>
      </webuijsf:html>
    </webuijsf:page>
  </f:view>
</jsp:root>

