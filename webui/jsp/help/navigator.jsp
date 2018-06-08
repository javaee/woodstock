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

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %> 
<%@taglib uri="http://www.sun.com/webui/webuijsf" prefix="webuijsf" %>

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
  <webuijsf:page>
    <webuijsf:head title="#{JavaHelpBean.navigatorHeadTitle}" />
    <webuijsf:body styleClass="#{JavaHelpBean.bodyClassName}">
      <webuijsf:form id="helpNavigatorForm">
        <webuijsf:markup tag="div" styleClass="#{JavaHelpBean.stepTabClassName}">
            <webuijsf:tabSet id="javaHelpTabSet" mini="true">
                <webuijsf:tab id="contentsTab" text="#{JavaHelpBean.contentsText}" 
                              actionExpression="#{JavaHelpBean.contentsTabClicked}" />
                <webuijsf:tab id="indexTab" text="#{JavaHelpBean.indexText}" 
                              actionExpression="#{JavaHelpBean.indexTabClicked}" />
                <webuijsf:tab id="searchTab" text="#{JavaHelpBean.searchText}" 
                              actionExpression="#{JavaHelpBean.searchTabClicked}" />
            </webuijsf:tabSet>
        </webuijsf:markup> 
        <div style="margin-left: 10px;">
        <webuijsf:tree binding="#{JavaHelpBean.contentsTree}" />        
        <webuijsf:tree binding="#{JavaHelpBean.indexTree}" />        
        </div>
        <webuijsf:panelGroup binding="#{JavaHelpBean.searchPanel}">
          <webuijsf:markup tag="div" styleClass="#{JavaHelpBean.searchClassName}">
          <webuijsf:textField id="searchText" />
          <webuijsf:button id="searchButton" text="#{JavaHelpBean.searchLabel}" 
	      actionExpression="#{JavaHelpBean.doSearch}" />
          <webuijsf:markup tag="div" styleClass="#{JavaHelpBean.inlineHelpClassName}">
              <webuijsf:hyperlink binding="#{JavaHelpBean.tipsLink}" />
          </webuijsf:markup>
          <f:verbatim>
            <table border="0" cellspacing="0" cellpadding="0" width="98%">
            <tr><td>
          </f:verbatim>
          <webuijsf:image icon="DOT" alt="" border="0" height="5" width="1" />
          <f:verbatim></td></tr><tr></f:verbatim>
          <webuijsf:markup tag="td" styleClass="#{JavaHelpBean.titleClassName}">
              <webuijsf:image icon="DOT" alt="" border="0" height="1" width="1" />
          </webuijsf:markup>
          <f:verbatim>
            </tr><tr><td>
          </f:verbatim>
          <webuijsf:image icon="DOT" alt="" border="0" height="5" width="1" />
          <f:verbatim>
            </td></tr></table>
          </f:verbatim>
          <webuijsf:panelGroup binding="#{JavaHelpBean.searchResultsPanel}" />
          </webuijsf:markup>
        </webuijsf:panelGroup>
      </webuijsf:form>      
    </webuijsf:body> 
  </webuijsf:page>
</f:view>
